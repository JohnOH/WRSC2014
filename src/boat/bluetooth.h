/**************************************************************************
 * Title:	Library for UART Bluetooth interface to the boat              *
 * Author:	Ralf Bruder <contact@rbru.eu>								  *
 *         																  *
 * Desc:	This file contains the message processing from and to the     *
 *			boat with all necessary hardware initialization such as UART  *
 *			and Bluetooth.                        						  *
 **************************************************************************/
 
#ifndef BLUETOOTH_H
#define BLUETOOTH_H

#define BT_RESET_PORT	PORTA
#define BT_RESET_PIN	PA2

#define BT_UART_RECEIVE_INTERRUPT   SIG_USART3_RECV
#define BT_UART_TRANSMIT_INTERRUPT  SIG_USART3_DATA
#define BT_UART_STATUS   UCSR3A
#define BT_UART_CONTROL  UCSR3B
#define BT_UART_DATA     UDR3
#define BT_UART_UDRIE    UDRIE3
#define BT_UBRR			 UBRR3
#define BT_UCSRC		 UCSR3C

#define BT_UART_RX_BUFFER_SIZE 256
#define BT_UART_TX_BUFFER_SIZE 256

#define BT_UART_RX_BUFFER_MASK ( BT_UART_RX_BUFFER_SIZE - 1)
#define BT_UART_TX_BUFFER_MASK ( BT_UART_TX_BUFFER_SIZE - 1)

static volatile unsigned char BT_UART_TxBuf[BT_UART_TX_BUFFER_SIZE];
static volatile unsigned char BT_UART_RxBuf[BT_UART_RX_BUFFER_SIZE];
static volatile unsigned char BT_UART_TxHead;
static volatile unsigned char BT_UART_TxTail;
static volatile unsigned char BT_UART_RxHead;
static volatile unsigned char BT_UART_RxTail;
static volatile unsigned char BT_UART_LastRxError;

// Sets uart port, resets bluetooth, etc.
void Bluetooth_Init();

// Resets bluetooth
void Bluetooth_Reset();

// end message via bluetooth
void Bluetooth_send_message(char* msg, unsigned char length) ;


// send debug message via bluetooth
void Bluetooth_debug(char* msg, unsigned char length);



// Send error message via bluetooth
void Bluetooth_error(unsigned int error);

// Send data via bluetooth
void Bluetooth_putc(unsigned char data);
void Bluetooth_puts(const char *s );
void Bluetooth_puts_p(const char *progmem_s );
#define Bluetooth_puts_P(__s)       uart_puts_p(PSTR(__s))

char Bluetooth_connected();

// Maximal power saving
void Bluetooth_Shutdown(); 

#endif
