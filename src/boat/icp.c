#include <avr/io.h>
#include <avr/interrupt.h>

#include "icp.h"
#include "config.h"
#include "led.h"

/**
 *	icp_duty_compute()
 *
 *	This function computes the value of (ICP_SCALE*pulsewidth)/period.
 *	It is effectively a divide function, but uses a successive-approximation
 *	(moral equivalent of long division) method which:
 *	1) Doesn't require 32-bit arithmetic (the numerator is 24+ bits nominal).
 *	2) For an 8-bit result, only needs 8 loops (instead of 16 for general 16/16).
 *	3) Compiles nicely from C.
 *	We get away with this because we know that pulsewidth <= period, so
 *	no more than log2(ICP_SCALE) bits are relevant.
 */
inline static unsigned int icp_duty_compute(unsigned int pulsewidth, unsigned int period)
{
	unsigned int r, mask;

	mask = 1024 >> 1;
	r = 0;
	do
	{
		period >>= 1;
		if (pulsewidth >= period)
		{
			r |= mask;
			pulsewidth -= period;
		}
		mask >>= 1;
	} while (pulsewidth != 0 && mask != 0);
	return(r);
}

unsigned char winddir_icp_mode = 0;
unsigned int winddir_icp_start_period = 0;
unsigned int winddir_icp_end_period = 1;

// Activate winddir measurement
void Winddir_measure() 
{
	WINDDIR_TCNT = 0x0000;
	WINDDIR_TIFR = (1 << ICF1);
	// TCCR1B
	// ICNC1 ICES1 – WGM13 WGM12 CS12 CS11 CS10
	WINDDIR_TCCRB = (1 << ICES1) | (0 << ICNC1) | WINDDIR_PRESCALE;
	winddir_icp_mode = 0;
	WINDDIR_TIMSK = (1 << ICIE1);
}

// ICP capture interrupt.
void WINDDIR_CAPT_VECT(void) __attribute__((interrupt));
void WINDDIR_CAPT_VECT(void) 
{
	debugled_on();
	switch (winddir_icp_mode) {
		case 0: 
			winddir_icp_start_period = WINDDIR_ICR;			// Get rising edge and start of period
			++winddir_icp_mode;
			break;
		case 1:
			winddir_icp_end_period = WINDDIR_ICR;			// Get rising edge and end of period
			WINDDIR_TCCRB &= ~(1 << ICES1); 				// Configure for falling edge to measure on-time
			++winddir_icp_mode;
			// Check if falling edge arrived while configuring and without interrupt
			if ((!(WINDDIR_PIN & (1 << WINDDIR_BIT))) && (!(WINDDIR_TIFR & (1 << ICF1)))) 
			{
				config.wind.winddir_tenth_degree = 3600 - config.wind.offset_tenth_degree;
				WINDDIR_TCCRB |= (1 << ICES1); 				// Configure for rising edge to restart
				WINDDIR_TIFR = (1 << ICF1);					// Clear in case of race
				winddir_icp_mode = 0;
				WINDDIR_TIMSK &= ~(1 << ICIE1); 			// Deactivate CAPT interrupt
			}
			break;
		case 2:
			config.wind.winddir_tenth_degree = (3600 - config.wind.offset_tenth_degree + 
				((WINDDIR_ICR - winddir_icp_end_period)*(unsigned long)3600)/
				(winddir_icp_end_period - winddir_icp_start_period)) % 3600;
			WINDDIR_TCCRB |= (1 << ICES1); 					// Configure for rising edge to restart
			WINDDIR_TIFR = (1 << ICF1);						// Clear in case of race
			winddir_icp_mode = 0;
			WINDDIR_TIMSK &= ~(1 << ICIE1);					// Deactivate CAPT interrupt
			break;
	} // switch
	debugled_off();
}

unsigned char windspeed_icp_mode = 0;
unsigned int windspeed_icp_start_period = 0;
unsigned int windspeed_icp_end_period = 1;
unsigned int windspeed_last_position = 0;

void Windspeed_measure() 
{
	WINDSPEED_TCNT = 0x0000;
	WINDSPEED_TIFR = (1 << ICF5);
	// TCCR1B
	// ICNC1 ICES1 – WGM13 WGM12 CS12 CS11 CS10
	WINDSPEED_TCCRB = (1 << ICES5) | (0 << ICNC5) | WINDSPEED_PRESCALE;
	// Enable both the Input Capture and the Output Capture interrupts.
	windspeed_icp_mode = 0;
	WINDSPEED_TIMSK = (1 << ICIE5);
}

// ICP capture interrupt.
void WINDSPEED_CAPT_VECT(void) __attribute__((interrupt));
void WINDSPEED_CAPT_VECT(void) 
{
	debugled_on();
	switch (windspeed_icp_mode) {
		case 0: 
			windspeed_icp_start_period = WINDSPEED_ICR;			// Get rising edge and start of period
			++windspeed_icp_mode;
			break;
		case 1:
			windspeed_icp_end_period = WINDSPEED_ICR;			// Get rising edge and end of period
			WINDSPEED_TCCRB &= ~(1 << ICES5); 					// Configure for falling edge to measure on-time
			++windspeed_icp_mode;
			// Check if falling edge arrived while configuring and without interrupt
			if ((!(WINDSPEED_PIN & (1 << WINDSPEED_BIT))) && (!(WINDSPEED_TIFR & (1 << ICF5)))) 
			{
				config.wind.speed_tenth_degree = 3600 - windspeed_last_position;
				windspeed_last_position = 0;
				WINDSPEED_TCCRB |= (1 << ICES5); 				// Configure for rising edge to restart
				WINDSPEED_TIFR = (1 << ICF5);					// Clear in case of race
				windspeed_icp_mode = 0;
				WINDSPEED_TIMSK &= ~(1 << ICIE5); 				// Deactivate CAPT interrupt
			}
			break;
		case 2:
			// Measure rotor position at 50 Hz, calculate difference as windspeed
			;int position = ((WINDSPEED_ICR - windspeed_icp_end_period)*(unsigned long)3600)/
				(windspeed_icp_start_period - windspeed_icp_end_period);
			//if (position > windspeed_last_position) {
			//	config.wind.speed_tenth_degree = position - windspeed_last_position;
			//} else {
				int spd = (position + 3600 - windspeed_last_position) % 3600;
				if (spd > 3100) spd = 0; // No negative wind
				config.wind.speed_tenth_degree = spd;
			//}
			windspeed_last_position = position;
			WINDSPEED_TCCRB |= (1 << ICES5); 					// Configure for rising edge to restart
			WINDSPEED_TIFR = (1 << ICF5);						// Clear in case of race
			windspeed_icp_mode = 0;
			WINDSPEED_TIMSK &= ~(1 << ICIE5);					// Deactivate CAPT interrupt
			break;
	} // switch
	debugled_off();
}
