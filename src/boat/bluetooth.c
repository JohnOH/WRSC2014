#include "config.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include <util/delay.h>

#include "bluetooth.h"
#include "led.h"
#include "servos.h"
#include "gamestate.h"

inline char Bluetooth_connected() 
{ 
	return PINA & (1 << PA3); 
}

void Bluetooth_Init() {
	// Reset bluetooth before initializing uart
	Bluetooth_Reset();

	BT_UART_TxHead = 0;
    BT_UART_TxTail = 0;
    BT_UART_RxHead = 0;
    BT_UART_RxTail = 0;

    BT_UBRR = ((F_CPU)/((38400)*16l)-1);

    // Enable USART receiver and transmitter and receive complete interrupt 
    BT_UART_CONTROL = _BV(RXCIE0)|(1<<RXEN0)|(1<<TXEN0);
    
    // Set frame format: asynchronous, 8data, no parity, 1stop bit 
    #ifdef URSEL0
    	BT_UCSRC = (1<<URSEL0)|(3<<UCSZ00);
    #else
    	BT_UCSRC = (3<<UCSZ00);
    #endif 
}

void Bluetooth_Shutdown() {
	BT_RESET_PORT &= ~(1 << BT_RESET_PIN);			// Reset bluetooth
}

void Bluetooth_Reset() {
	led_on();
	int i;
	BT_RESET_PORT &= ~(1 << BT_RESET_PIN);			// Reset bluetooth
	for(i=0;i<20;i++) 		// Wait for Bluetooth
		_delay_ms(50);
	BT_RESET_PORT |= (1 << BT_RESET_PIN);			// Disable Bluetooth reset
	for(i=0;i<20;i++) 		// Wait for Bluetooth
		_delay_ms(50);	
	led_off();
}

void Bluetooth_putc(unsigned char data)
{
    unsigned char tmphead  = (BT_UART_TxHead + 1) & BT_UART_TX_BUFFER_MASK;
    
    while (tmphead == BT_UART_TxTail)
	{
        // wait for free space in buffer
		// THIS IS USUALLY THE DEADLOCK
    }
    
    BT_UART_TxBuf[tmphead] = data;
    BT_UART_TxHead = tmphead;

    // enable UDRE interrupt 
    BT_UART_CONTROL    |= _BV(BT_UART_UDRIE);
}

void Bluetooth_puts(const char *s )
{
    while (*s) 
      Bluetooth_putc(*s++);
}

void Bluetooth_puts_p(const char *progmem_s )
{
    register char c;
    while ( (c = pgm_read_byte(progmem_s++)) ) 
      Bluetooth_putc(c);
}

void Bluetooth_process_message(unsigned char MsgTail, unsigned char length) 
{
	int rudder, mainsail, foresail;
	switch (BT_UART_RxBuf[(MsgTail) & BT_UART_RX_BUFFER_MASK]) {
		case 0xFE: // Ping
			//;char ping = 0xFE;
			//Bluetooth_send_message(&ping,1); // NOT ALLOWED BECAUSE NOT THREAD-SAFE
			break;
		case 0x01: // Servoss
			// Servo message turns off AI rudder and sails
			config.ai.active &= ~0x03;
			*((unsigned char*)&rudder+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&rudder+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			config.rudder.position_tenth_degree = rudder;
			//set_rudder(rudder);
			*((unsigned char*)&mainsail+0) = BT_UART_RxBuf[(MsgTail + 3) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&mainsail+1) = BT_UART_RxBuf[(MsgTail + 4) & BT_UART_RX_BUFFER_MASK];
			config.mainsail.position_tenth_degree = mainsail;
			//set_mainsail(mainsail);
			*((unsigned char*)&foresail+0) = BT_UART_RxBuf[(MsgTail + 5) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&foresail+1) = BT_UART_RxBuf[(MsgTail + 6) & BT_UART_RX_BUFFER_MASK];		
			config.foresail.position_tenth_degree = foresail;
			//set_foresail(foresail);
			break;
		case 0x02: // Only rudder servo
			// Servo message turns off AI rudder
			config.ai.active &= ~0x01;
			*((unsigned char*)&rudder+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&rudder+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			config.rudder.position_tenth_degree = rudder;
			break;
		case 0x0F:
			config.aux.outputs = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			break;
		case 0xA0: // communicate with AI
			config.ai.active = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			break;
		case 0xA1: // get Course Override for AI
			;int course;
			*((unsigned char*)&course+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&course+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			gamestate.overrideCourse = course;
			break;
		case 0xA2: // get Waypoint Override for AI
			;long north, east, lat, lon, radius;
			*((unsigned char*)&north+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&north+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&north+2) = BT_UART_RxBuf[(MsgTail + 3) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&north+3) = BT_UART_RxBuf[(MsgTail + 4) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&east+0) = BT_UART_RxBuf[(MsgTail + 5) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&east+1) = BT_UART_RxBuf[(MsgTail + 6) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&east+2) = BT_UART_RxBuf[(MsgTail + 7) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&east+3) = BT_UART_RxBuf[(MsgTail + 8) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lat+0) = BT_UART_RxBuf[(MsgTail + 9) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lat+1) = BT_UART_RxBuf[(MsgTail + 10) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lat+2) = BT_UART_RxBuf[(MsgTail + 11) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lat+3) = BT_UART_RxBuf[(MsgTail + 12) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lon+0) = BT_UART_RxBuf[(MsgTail + 13) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lon+1) = BT_UART_RxBuf[(MsgTail + 14) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lon+2) = BT_UART_RxBuf[(MsgTail + 15) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&lon+3) = BT_UART_RxBuf[(MsgTail + 16) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&radius+0) = BT_UART_RxBuf[(MsgTail + 17) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&radius+1) = BT_UART_RxBuf[(MsgTail + 18) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&radius+2) = BT_UART_RxBuf[(MsgTail + 19) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&radius+3) = BT_UART_RxBuf[(MsgTail + 20) & BT_UART_RX_BUFFER_MASK];
			gamestate.overrideWaypoint.positionCurrentPoint.North = north;
			gamestate.overrideWaypoint.positionCurrentPoint.East = east;
			gamestate.overrideWaypoint.positionLastPoint = gamestate.overrideWaypoint.positionCurrentPoint;
			gamestate.overrideWaypoint.positionCurrentPoint_lat = lat;
			gamestate.overrideWaypoint.positionCurrentPoint_lon = lon;
			gamestate.overrideWaypoint.positionLastPoint_lat = lat;
			gamestate.overrideWaypoint.positionLastPoint_lon = lon;
			gamestate.overrideWaypoint.leftBorder = 0;
			gamestate.overrideWaypoint.rightBorder = 0;
			gamestate.overrideWaypoint.num_of_obstacles = 0;
			gamestate.overrideWaypoint.radius = radius;
			gamestate.overrideWaypoint.flags =  BT_UART_RxBuf[(MsgTail + 21) & BT_UART_RX_BUFFER_MASK];
			break;
		case 0xB0: // SendMessageTable
			;unsigned long sendMessages;
			*((unsigned char*)&sendMessages+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&sendMessages+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&sendMessages+2) = BT_UART_RxBuf[(MsgTail + 3) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&sendMessages+3) = BT_UART_RxBuf[(MsgTail + 4) & BT_UART_RX_BUFFER_MASK];
			config.aux.sendMessages = sendMessages;
			break;
		case 0xB1: // ReadSensorTable
			;unsigned int readSensors;
			*((unsigned char*)&readSensors+0) = BT_UART_RxBuf[(MsgTail + 1) & BT_UART_RX_BUFFER_MASK];
			*((unsigned char*)&readSensors+1) = BT_UART_RxBuf[(MsgTail + 2) & BT_UART_RX_BUFFER_MASK];
			config.aux.readSensors = readSensors;
			break;
		default:
			// No vaild message id
			Bluetooth_error(3);
			break;
	}
}

void Bluetooth_send_message(char* msg, unsigned char length) 
{
	Bluetooth_putc(length);
	unsigned char i;
	unsigned char chksum = 0x55;
	for (i = 0; i < length; i++) {
		chksum += *(msg+i);
		Bluetooth_putc(*(msg+i));
	}
	//uart_putc(chksum);
	Bluetooth_putc(0x52);
}

void Bluetooth_error(unsigned int error) 
{
	Bluetooth_putc(0x03);
	Bluetooth_putc(0xFF);
	Bluetooth_putc(error / 256);
	Bluetooth_putc(error % 256);
	//Bluetooth_putc((256 - 0x55 - 0x03 - 0xFF - (unsigned char)(error / 256) - (error % 256)) % 256);
	Bluetooth_putc(0x52);
}

void Bluetooth_debug(char* msg,unsigned char length){
	char x[length+1];
	int i;
	x[0] = 0xF0;
	for (i= 0;i < length; i++) {
		x[i+1]=msg[i];
	}
	Bluetooth_send_message(x,length+1);

}

//SIGNAL(BT_UART_RECEIVE_INTERRUPT)
ISR(BT_UART_RECEIVE_INTERRUPT)
{
    // read UART status register 
    char usr  = BT_UART_STATUS;
	if ((usr & (_BV(FE0)|_BV(DOR0)) ) != 0) {
		// UART Error occured
		Bluetooth_error(1);
	}

	// Read data from uart
	BT_UART_RxBuf[BT_UART_RxHead] = BT_UART_DATA;
	BT_UART_RxHead = (BT_UART_RxHead + 1) & BT_UART_RX_BUFFER_MASK;

	while (BT_UART_RxTail != BT_UART_RxHead)
    {
        if ((BT_UART_RxBuf[BT_UART_RxTail] + 2) > (BT_UART_RX_BUFFER_SIZE - 1))
        {
            Bluetooth_error(2);
            BT_UART_RxTail = (BT_UART_RxTail + 1) & BT_UART_RX_BUFFER_MASK;
            continue;
			
        }

        if (((BT_UART_RxHead + BT_UART_RX_BUFFER_SIZE - BT_UART_RxTail) & BT_UART_RX_BUFFER_MASK) < (BT_UART_RxBuf[BT_UART_RxTail] + 2))
        {
			
            // Wait for message to fully arrive
            break;
        }
        else
        {
			// Checksum
			//unsigned char chk = 0x55;
			//unsigned char i;
            //for (i = 0; i < (UART_RxBuf[UART_RxTail] + 2); i++)
			//{
            //    chk += UART_RxBuf[(UART_RxTail + i) & UART_RX_BUFFER_MASK];
			//}
            //if (chk == 0)
			if (BT_UART_RxBuf[(BT_UART_RxTail + (BT_UART_RxBuf[BT_UART_RxTail] + 1)) & BT_UART_RX_BUFFER_MASK] == 0x52)
            {
				// Analyze message
				Bluetooth_process_message(BT_UART_RxTail + 1,BT_UART_RxTail);
                BT_UART_RxTail = (BT_UART_RxTail + BT_UART_RxBuf[BT_UART_RxTail] + 2) & BT_UART_RX_BUFFER_MASK;
            }
            else
            {
                // Checksum error
                Bluetooth_error(4);
                BT_UART_RxTail = (BT_UART_RxTail + 1)  & BT_UART_RX_BUFFER_MASK;				
            }
        } // else
    } // while
}


//SIGNAL(BT_UART_TRANSMIT_INTERRUPT)
ISR(BT_UART_TRANSMIT_INTERRUPT)
{
    unsigned char tmptail;
    if ( BT_UART_TxHead != BT_UART_TxTail) 
	{
        // calculate and store new buffer index 
        tmptail = (BT_UART_TxTail + 1) & BT_UART_TX_BUFFER_MASK;
        BT_UART_TxTail = tmptail;
        // get one byte from buffer and write it to UART 
        BT_UART_DATA = BT_UART_TxBuf[tmptail];
    } else {
        // tx buffer empty, disable UDRE interrupt
        BT_UART_CONTROL &= ~_BV(BT_UART_UDRIE);
    }
}
