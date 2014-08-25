#include "gps.h"
#include "led.h"

#include "bluetooth.h"

#include "config.h"

// AVR system dependencies
#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdio.h>

void GPS_init() { 
	// Initialize UART:

    // set baud rate
	unsigned int baudrate = UART_BAUD_SELECT(GPS_UART_BAUDRATE,F_CPU);
    if ( baudrate & 0x8000 ) 
    {
   		GPS_UART_STATUS = (1<<U2X0);  //Enable 2x speed 
   		baudrate &= ~0x8000;
   	}
    GPS_UART_UBRR = (unsigned char)baudrate; 

    /* enable UART receiver and transmmitter and receive complete interrupt */
    GPS_UART_CONTROL = _BV(RXCIE0)|_BV(RXEN0)|_BV(TXEN0);
    
    /* Set frame format: asynchronous, 8data, no parity, 1stop bit */
    #ifdef GPS_UART_URSEL0
    	GPS_UART_MISC = (1<<URSEL0)|(3<<UCSZ00);
    #else
    	GPS_UART_MISC = (3<<UCSZ00);
    #endif 

	// Initialize state machine
	GPS_state = 0;
}

/** @brief  UART RX Interrupt and RMC state machine
 */
ISR(GPS_UART_RECEIVE_INTERRUPT)
{
    unsigned char data = GPS_UART_DATA;
	
	// State machine
	switch(GPS_state) {

		// UBX identification header 
		case 0: 
			if (data == 0xB5)
				GPS_state = 1;
			break;
		case 1:
			if (data == 0x62)
				GPS_state = 2;
			else 
				GPS_state = 0;
			break;

		// Message Class
		case 2:
			GPS_message_class = data;
			GPS_state = 3;
			break;
		case 3:
			GPS_message_id = data;
			GPS_state = 4;
			break;

		// Read length
		case 4:
			// Read length byte 1
			*((unsigned char *)&GPS_message_length) = data;
			GPS_state = 5;
			break;
		case 5:
			// Read length byte 2
			*((unsigned char *)&GPS_message_length +1) = data;
			GPS_message_counter = 0;
			GPS_state = 6;
			break;

		// Read data
		case 6: // Data
			if (GPS_message_counter < GPS_message_length) {
				GPS_message_data[GPS_message_counter] = data;
				GPS_message_counter += 1;				
			} else 
				GPS_state = 7;// chksum1
			break;
		case 7:
			// chksum2
			GPS_state = 0;
			// Commit variables
			if ( (GPS_message_class == 0x01) && (GPS_message_id == 0x02) ) { // POS-LLH
				config.gps.time = (*(UBX_POSLLH*)&GPS_message_data).ITOW;
				config.gps.longitude = (*(UBX_POSLLH*)&GPS_message_data).LON;
				config.gps.latitude = (*(UBX_POSLLH*)&GPS_message_data).LAT;
				
				//COMPUTE FLAT
				config.gps.position.North=(config.gps.latitude-config.gps.posXlat)/config.gps.factorLat;
				config.gps.position.East=(config.gps.longitude-config.gps.posXlon)/config.gps.factorLon;

			}
			if ( (GPS_message_class == 0x01) && (GPS_message_id == 0x12) ) { // POS-NED
				config.gps.time = (*(UBX_VELNED*)&GPS_message_data).ITOW;
				config.gps.heading_tenth_degree = (*(UBX_VELNED*)&GPS_message_data).Heading/1000;
				config.gps.speed = (*(UBX_VELNED*)&GPS_message_data).GSpeed / 5; // in tenth_kt
			}	
			if ( (GPS_message_class == 0x01) && (GPS_message_id == 0x03) ) { // Status
				config.gps.time = (*(UBX_STATUS*)&GPS_message_data).ITOW;
				config.gps.valid = (*(UBX_STATUS*)&GPS_message_data).GPSFix;
			}	
			break;

		default:
			// This should never happen
			break;
	}
}
