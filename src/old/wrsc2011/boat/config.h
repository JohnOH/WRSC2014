/**************************************************************************
 * Title:	Game and Boat configuration reader/writer                     *
 * Author:	Ralf Bruder <contact@rbru.eu>								  *
 *         																  *
 * Desc:	This file contains utilities to read and write the necessary  *                        
 *			information for the boat from/to eeprom                       *
 *																		  *
 *          ALL variables which are not necessary to change are HARDCODED *
 *			ONLY necessary variables are dynamically loaded				  *
 **************************************************************************/
 


#ifndef CONFIG_H
#define CONFIG_H

#include "latlon.h"

unsigned long filepos;
unsigned long logfilepos;


typedef struct { //8Byte
	int middle_ticks;
	int maximal_ticks;
	int maximal_tenth_degree;
	int position_tenth_degree;	
} SERVO;

typedef struct { //42Byte mit double=4Byte
	unsigned long time;
	long latitude;  			// Boat coordinate in raw
	long longitude;				// Boat coordinate in raw
	FLATPOS position; 			// Boat coordinate in FLAT
	long posXlat;				// Reference coordiate
	long posXlon;				// Reference coordiate
	double factorLat;			// Reference factor		wirklich double??????????????????
	double factorLon;			// Reference factor
	int heading_tenth_degree;
	unsigned char speed;
	unsigned char flags;
	// 7 6 5 4 3 2 1 0
	// - - - - - - - PassThrough
	unsigned char options;
	unsigned char valid;
} GPS;

typedef struct { //20Byte
	int yaw_tenth_degree;
	int pitch_tenth_degree;
	int roll_tenth_degree;
	int magnetometerX;
	int magnetometerY;
	int magnetometerZ;
	int accelerometerX;
	int accelerometerY;
	int accelerometerZ;
	int variation; // Abweichung zwischen wahrem und missweisendem Kompasskurs
} COMPASS;

typedef struct { //1Byte
	// 7 6 5 4 3 2 1 	   0
	// - - - - - - AISails AIRudder
	unsigned char active; // ai parameters in active state: 
	
} AI;

typedef struct { //13Byte
	// 7 6 5 4 3 2 1 0
	// - - - - - - - TopLight
	unsigned char outputs;
	// 31   30      29  28  27    26   25  24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 0
	// Wind Compass Mag Acc Servo Batt GPS
	unsigned long sendMessages;
	// 31   30      29  28  27    26   25  24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 0
	// Wind Compass Mag Acc Servo Batt GPS
	unsigned long logMessages;
	// 15   14   13  12  11  10  9   8  7 6 5 4 3 2 1 0
	// WDir WSpd Bat Cmp Mag Acc GPS BT
	unsigned int readSensors;
	// File pointer for log file
	 struct fat_file_struct* fd;
} AUX;

typedef struct { //8Byte
	int winddir_tenth_degree;
	int offset_tenth_degree; 		// calibration value
	int speed_tenth_degree; 		// Tenth degree of turn in 20 milliseconds
	int globalwind_tenth_degree; 	//globalwind...
} WIND;

typedef struct {
	unsigned char divider;
	unsigned char voltage_tenth_volt;
	unsigned char warn_voltage_tenth_volt;
	unsigned char critical_voltage_tenth_volt;
} BATTERY;

typedef struct {
	SERVO rudder;		//8Byte
	SERVO mainsail;		//8Byte
	SERVO foresail;		//8Byte
	COMPASS compass;	//20Byte
	GPS gps;			//42Byte
	AI ai;				//1Byte
	WIND wind;			//8Byte
	AUX aux;			//13Byte
	BATTERY battery;	//4Byte
} CONFIG;				//112

extern volatile CONFIG config;

void config_init();

unsigned char config_init_sd();

void config_store_sd();

void config_write_log();

unsigned char read_gamestate_from_SD();
unsigned char read_next_waypoint_from_SD();
void write_next_waypoint_to_SD();

#endif
