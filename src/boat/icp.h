/**************************************************************************
 * Title:	PWM sensor library for winddir and windspeed                  *
 * Author:	Ralf Bruder <contact@rbru.eu>								  *
 *         																  *
 * WARNING: This is not the standard implementation from AVR Note!!!	  *
 * It assumes a continuous signal with small changes which does NOT 	  *
 * need to be captured entirely.										  *
 *																		  *
 * BUT: It is safe(r) as long as there is always a rising and falling 	  *
 * edge / no 100% on/off duty-cycle AND far more resource saving.		  *
 **************************************************************************/

#ifndef ICP_H
#define ICP_H

// --------------------------------------------
// --- Definitions for the WINDSPEED sensor ---
// --------------------------------------------

// Definitions for the ICP pin; for this example we use timer 1
#define	WINDSPEED_PIN		PINL		// ICP1 GPIO value	
#define	WINDSPEED_PORT		PORTL		// ICP1 GPIO port	
#define	WINDSPEED_DDR		DDRL		// ICP1 GPIO DDR	
#define	WINDSPEED_BIT		PL1			// ICP1 GPIO pin	

// Definitions for ICP timer (1) setup.
#define WINDSPEED_CAPT_VECT	TIMER5_CAPT_vect
#define	WINDSPEED_TCCRA		TCCR5A		// ICP1 timer control				
#define	WINDSPEED_TCCRB		TCCR5B		// ICP1 interrupt control			
#define	WINDSPEED_PRESCALE ((0 << CS12) | (0 << CS11) | (1 << CS10))	//prescale /1 
#define	WINDSPEED_TIFR		TIFR5
#define WINDSPEED_TIMSK		TIMSK5		// ICP1 TIMSK
#define	WINDSPEED_ICR		ICR5
#define WINDSPEED_TCNT		TCNT5

void Windspeed_measure(void);

// ------------------------------------------
// --- Definitions for the WINDDIR sensor ---
// ------------------------------------------

// Definitions for the ICP pin; for this example we use timer 1
#define	WINDDIR_PIN			PIND		// ICP1 GPIO value	
#define	WINDDIR_PORT		PORTD		// ICP1 GPIO port	
#define	WINDDIR_DDR			DDRD		// ICP1 GPIO DDR	
#define	WINDDIR_BIT			PD4			// ICP1 GPIO pin	

// Definitions for ICP timer (1) setup.
#define WINDDIR_CAPT_VECT	TIMER1_CAPT_vect
#define	WINDDIR_TCCRA		TCCR1A		// ICP1 timer control				
#define	WINDDIR_TCCRB		TCCR1B		// ICP1 interrupt control			
#define	WINDDIR_PRESCALE ((0 << CS12) | (0 << CS11) | (1 << CS10))	//prescale /1   --> /8
#define	WINDDIR_TIFR		TIFR1
#define WINDDIR_TIMSK		TIMSK1		// ICP1 TIMSK
#define	WINDDIR_ICR			ICR1
#define WINDDIR_TCNT		TCNT1

void	Winddir_measure(void);

#endif	
