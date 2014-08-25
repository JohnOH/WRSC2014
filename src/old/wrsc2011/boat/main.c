// AVR system dependencies
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

// Local dependenciess
#include "config.h"

#include "servos.h"
#include "led.h"
#include "bluetooth.h"
#include "gps.h"
#include "loop.h"
#include "i2cmaster.h"
#include "im3501.h"
#include "ai_1.h" 		// <------ CHANGE THIS ACCORDING TO YOUR AI

#define F_CPU 7372800UL

// ---- Initialize the AVR IOs ----

void init()
{
	// General port configuration
	// PA7 PA6 PA5 PA4 PA3 		   PA2     PA1 PA0
	//  -   -   -   -  BTConnected BTReset LED BLButton 
	DDRA = 0x06;
	PORTA = 0x05;

	// PB7 PB6  PB5 PB4 PB3  PB2  PB1 PB0
	//  -   -  SDCS  -  MISO MOSI SCK  - 
	//DDRB = 0x20; // TODO: Pullups
	//PORTB = 0x2E; 
	DDRB = 0x00;
	PORTB = 0x00;

	// PC combined output port
	DDRC = 0xFF;
	PORTC = 0x00; 

	// PD7 PD6 PD5 PD4  PD3 PD2 PD1 PD0
	//  -   -   -  ICP1 TX1 RX1 SDA SCL	
	DDRD = 0x00;
	PORTD = 0x00;

	// PE7 PE6  PE5   PE4   PE3  PE2 PE1 PE0
	//  -   -  OCR3C OCR3B OCR3A  -  TX0 RX0
	DDRE = 0x38;
	PORTE = 0x00;

	// Not used
	DDRF = 0x00; 
	PORTF = 0x00;

	// PG5 PG4  PG3   PG2 PG1 PG0
	//  -   -  SPI2CS  -   -   -
	DDRG = 0x10; 
	PORTG = 0x10;

	//  PH7    PH6  PH5   PH4   PH3  PH2 PH1 PH0
	// SPI1CS   -  OCR4C OCR4B OCR4A  -   -   -
	DDRH = 0x98;  // not all OCRs used, elsewise 0xB8
	PORTH = 0x80;

	// PJ7 PJ6 PJ5 PJ4 PJ3 PJ2 PJ1 PJ0
	//  -   -   -  RTS CTS  -  TX3 RX3
	DDRJ = 0x08; 
	PORTJ = 0x00;

	// PJ7 PJ6 PJ5 PJ4 PJ3 PJ2 PJ1 PJ0
	// BAT BAT           ADs
	DDRK = 0x00; 
	PORTK = 0x00;

	//  PL7..2  PL1  PL0
	// combined ICP5 ICP4  <--- DO NOT USE ICP4 !!!
	DDRL = 0xFF;
	PORTL = 0x00;

	// --- Initialize boat config ---
	if(!config_init_sd())
		config_init();

	// --- Bluetooth ---
	if (config.aux.readSensors & 0x0100) Bluetooth_Init();

	// --- GPS ---
	if (config.aux.readSensors & 0x0200) GPS_init();

	// --- I2C ---
	if (config.aux.readSensors & 0x1C00) i2c_init();

	// --- Im3501 ---
	if (config.aux.readSensors & 0x1C00) {
		im3501_set_power(0x00); // WakeUp
		im3501_set_navimode(0x00); //Navi Mode Normal
	}

	// --- AI preparation ---
	ai_init();

	// --- Servo timer 3/4 ---
	servos_init();

	// --- Message loop ---
	loop_init();

	// --- Globally enable interrupts ---
	sei();
}

// ---- Main routine ----

int main() 
{
	init();

	while(1)
	{
		// do nothing
	}
}

