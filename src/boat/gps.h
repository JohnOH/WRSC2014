/**************************************************************************
 * Title:	Small and fast routine for interrupt based GPS UBX processing *
 * Author:	Ralf Bruder <contact@rbru.eu>								  *
 *         																  *
 * Desc:	This is a state machine for reading the GPS UBX string. All   *
 *			actions are interrupt-based. The read values are stored in    *
 *			volatile variables. 										  *                        
 **************************************************************************/

#ifndef GPS_H
#define GPS_H

#include<inttypes.h>
#include "latlon.h"

// CHANGE THIS TO CONFIGURE YOUR UART
#define GPS_UART_RECEIVE_INTERRUPT 	SIG_USART0_RECV
#define GPS_UART_STATUS   			UCSR0A
#define GPS_UART_CONTROL  			UCSR0B
#define GPS_UART_MISC	  			UCSR0C
#define GPS_UART_DATA     			UDR0
#define GPS_UART_UDRIE				UDRIE0
#define GPS_UART_UBRR				UBRR0
#define GPS_UART_BAUDRATE 			38400
// CHANGE THIS TO CONFIGURE YOUR UART

/** @brief  UART Baudrate Expression
 *  @param  xtalcpu  system clock in Mhz, e.g. 4000000L for 4Mhz          
 *  @param  baudrate baudrate in bps, e.g. 1200, 2400, 9600     
 */
#define UART_BAUD_SELECT(baudRate,xtalCpu) ((xtalCpu)/((baudRate)*16l)-1)

typedef struct {
	uint32_t		ITOW;		// ms GPS Millisecond Time of Week
	int32_t			LON;		// 1e-07 deg Longitude
	int32_t			LAT;		// 1e-07 deg Latitude
	int32_t			HEIGHT;		// mm Height above Ellipsoid
	int32_t			HMSL;		// mm Height above mean sea level
	uint32_t		Hacc;		// mm Horizontal Accuracy Estimate
	uint32_t		Vacc;		// mm Vertical Accuracy Estimate
} UBX_POSLLH;

typedef struct {
	uint32_t		ITOW;  		// ms  GPS Millisecond Time of Week
	int32_t			VEL_N; 		// cm/s  NED north velocity
	int32_t			VEL_E; 		// cm/s  NED east velocity
	int32_t			VEL_D; 		// cm/s  NED down velocity
	uint32_t		Speed; 		// cm/s  Speed (3-D)
	uint32_t		GSpeed; 	// cm/s  Ground Speed (2-D)
	int32_t			Heading; 	// 1e-05 deg  Heading 2-D
	uint32_t		SAcc;		// cm/s  Speed Accuracy Estimate
	uint32_t		CAcc; 		// deg  Course / Heading Accuracy Estimate
} UBX_VELNED;

typedef struct {
	uint32_t		ITOW;  		// ms  GPS Millisecond Time of Week
	unsigned char	GPSFix;		// 00- no fix, 02,03,04 fix
	unsigned char	Flags;
	unsigned char	DiffStat;
	unsigned char	Res;
	uint32_t		TTFF;		// time to first fix
	uint32_t		MSSS;		// time since startup
} UBX_STATUS;

/*volatile unsigned long GPS_time;
volatile long GPS_latitude;
volatile long GPS_longitude;
//volatile long GPS_height_eli;
//volatile long GPS_height_msl;
//volatile unsigned long GPS_hacc;
//volatile unsigned long GPS_vacc;
//volatile long GPS_vel_north;
//volatile long GPS_vel_east;
//volatile long GPS_vel_down;
//volatile unsigned long GPS_speed3d;
volatile unsigned long GPS_speed2d;

volatile long GPS_heading;
//volatile long GPS_cacc;
//volatile long GPS_sacc;

volatile unsigned char GPS_fix;
//volatile unsigned char GPS_flags;
//volatile unsigned char GPS_dstat;
//volatile unsigned long GPS_ttff;
//volatile unsigned long GPS_msss;

volatile unsigned int GPS_gridfactor_latitude;
volatile unsigned int GPS_gridfactor_longitude;
volatile unsigned long GPS_offset_latitude;
volatile unsigned long GPS_offset_longitude;

volatile unsigned int GPS_latitude_flat;
volatile unsigned int GPS_longitude_flat;*/

/** @brief  Initialization of the UART and state machine
 */
void GPS_init();

// State machine for PMC message
unsigned char GPS_state;
unsigned int GPS_message_counter;
unsigned int GPS_message_length;
unsigned char GPS_message_class;
unsigned char GPS_message_id;
unsigned char GPS_message_data[256]; // WARNING: 

#endif
