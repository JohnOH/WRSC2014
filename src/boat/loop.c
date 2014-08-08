// AVR system dependencies
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdio.h>

#include "loop.h"
#include "config.h"
#include "led.h"
#include "bluetooth.h"
#include "icp.h"
#include "servos.h"
#include "battery.h"
#include "im3501.h"
#include "ai_1.h"		// <------ CHANGE THIS ACCORDING TO YOUR AI
#include "gps.h"
#include "filter.h"

void loop_init() {
	// -- CommandQueue: Timer 0 init
	// COM0A1 COM0A0 COM0B1 COM0B0 – – WGM01 WGM00 
	//    0      0      0      0   0 0   0     0
	TCCR0A = 0x00;
	// FOC0A FOC0B – – WGM02 CS02 CS01 CS00 
	 //  0     0   0 0   0     1    0    1 --> 1/1024
	TCCR0B = 0x05;
	// Timer value
	// 200Hz 0xDC
	// 100Hz 0xB8
	//  50Hz 0x70 <--
	//  40Hz 0x4C
	//  30Hz 0x10 <-- 
	TCNT0 = 0x70;
	// – – – – – OCIE0B OCIE0A TOIE0 
	// 0 0 0 0 0    0      0     1
	TIMSK0 = 0x01;
}

unsigned char timer0_divider = 0;
unsigned char command_queue_divider = 0;

#include "trigo.h"

void command_queue()
{
	topled_on();

	// Acquire sensor data
	if (config.aux.readSensors & 0x1000) { // Compass
		config.compass.yaw_tenth_degree = im3501_read_azimuth() * 10;
		config.compass.pitch_tenth_degree = im3501_read_pitch() * 10;
		config.compass.roll_tenth_degree = im3501_read_roll() * 10;
	}
	if (config.aux.readSensors & 0x0800) { // Magnetometer
		config.compass.magnetometerX = im3501_read_mag_x();
		config.compass.magnetometerY = im3501_read_mag_y();
		config.compass.magnetometerZ = im3501_read_mag_z();
	}
	if (config.aux.readSensors & 0x0400) { // Accelerometer
		config.compass.accelerometerX = im3501_read_acc_x();
		config.compass.accelerometerY = im3501_read_acc_y();
		config.compass.accelerometerZ = im3501_read_acc_z();
	}

	// Artificial intelligence ;-)
	// This check is for security reasons to ensure that it is REALLY turned off
	if (config.ai.active != 0) {
		led_on();
		ai_exec();
		led_off();
	}

	//kalmanTest();

	if (++command_queue_divider % 50 == 0) // 2 => 5 Hz oder 50 => 0.2 Hz
	{
	
		// Bluetooth message generation
		if ( Bluetooth_connected() ) 
		{
			char msg[25];
			if (config.aux.sendMessages & 0x40000000) // Compass
			{
				msg[0] = 0x01;
				*((int*)(msg+1)) = config.compass.yaw_tenth_degree;
				*((int*)(msg+3)) = config.compass.pitch_tenth_degree;
				*((int*)(msg+5)) = config.compass.roll_tenth_degree;
				Bluetooth_send_message(msg,7);
			}
			if (config.aux.sendMessages & 0x20000000) // Magnetometer
			{
				msg[0] = 0x02;
				*((int*)(msg+1)) = config.compass.magnetometerX;
				*((int*)(msg+3)) = config.compass.magnetometerY;
				*((int*)(msg+5)) = config.compass.magnetometerZ;
				Bluetooth_send_message(msg,7);
			}
			if (config.aux.sendMessages & 0x10000000) // Accelerometer
			{
				msg[0] = 0x03;
				*((int*)(msg+1)) = config.compass.accelerometerX;
				*((int*)(msg+3)) = config.compass.accelerometerY;
				*((int*)(msg+5)) = config.compass.accelerometerZ;
				Bluetooth_send_message(msg,7);
			}
			if (config.aux.sendMessages & 0x80000000) // Wind
			{
				msg[0] = 0x05;
				*((int*)(msg+1)) = config.wind.winddir_tenth_degree;
				*((int*)(msg+3)) = config.wind.speed_tenth_degree;
				Bluetooth_send_message(msg,5);
			}
			if (config.aux.sendMessages & 0x04000000) // Battery
			{
				msg[0] = 0x08;
				msg[1] = config.battery.voltage_tenth_volt;
				Bluetooth_send_message(msg,2);
			}
			if (config.aux.sendMessages & 0x02000000) // GPS Position
			{
				msg[0] = 0x09;
				//*((long*)(msg+1)) = config.gps.time;
				*((long*)(msg+1)) = config.gps.latitude;
				*((long*)(msg+5)) = config.gps.longitude;
				*((int*)(msg+9)) = config.gps.heading_tenth_degree;
				*((unsigned char*)(msg+11)) = config.gps.speed;
				*((unsigned char*)(msg+12)) = config.gps.flags;
				Bluetooth_send_message(msg,13);
			}
			// TODO: 0x0A GPS RAW
			if (config.aux.sendMessages & 0x08000000) // ServoPosition
			{
				msg[0] = 0x0B;
				*((unsigned int*)(msg+1)) = config.rudder.position_tenth_degree;
				*((unsigned int*)(msg+3)) = config.mainsail.position_tenth_degree;
				*((unsigned int*)(msg+5)) = config.foresail.position_tenth_degree;
				Bluetooth_send_message(msg,7);
			}

		} else {
			// ???
			// AI mode, game, ...
		}
		
	}
	
	if (command_queue_divider % 10 == 5) // 2 => 5 Hz oder 50 => 0.2 Hz
	{
	//	config_write_log();
	}

	// Set servo values from config object
	(config.aux.outputs&0x01)?siren_on():siren_off();
	set_rudder(config.rudder.position_tenth_degree);
	set_mainsail(config.mainsail.position_tenth_degree);
	set_foresail(config.foresail.position_tenth_degree);

	// Notabschaltung MUSS drinbleiben
	if (config.battery.voltage_tenth_volt < 70) { // 3.5V Zellenspannung
		if (timer0_divider % 10 == 0) siren_on();
		if (timer0_divider % 10 == 5) siren_off();
		if (config.battery.voltage_tenth_volt < 64) { // 3.2V Zellenspannung
			cli();
			siren_on();
			Bluetooth_Shutdown(); 
			servo_shutdown();
			while(1);
		}
	} 
	
	// Sensor acquisition (all interrupts AFTER timer execution)
	if (config.aux.readSensors & 0x8000) Winddir_measure();
	if (config.aux.readSensors & 0x4000) Windspeed_measure();
	if (config.aux.readSensors & 0x2000) Battery_measure();

	topled_off();
}

// CommandQueue: Timer 0 NON-BLOCKING
//void TIMER0_OVF_vect(void) __attribute__((interrupt));
//void TIMER0_OVF_vect(void)
unsigned char lock = 0;
ISR(TIMER0_OVF_vect, ISR_NOBLOCK)
{
	// Reinitialize at 50Hz
	TCNT0 = 0x70; 
	++timer0_divider;
	if (timer0_divider == 5) { // 10Hz
		if (!lock) {
			lock = 1;
			command_queue();
			lock = 0;
		} else {
			Bluetooth_error(17);
		}
		timer0_divider = 0;
	}
	// This is safe for variable overflows

}
