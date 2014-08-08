// long = 31 bit = 2.480.000.000
// int = 15 bit = 32.768
// unsigned int = 16 bit = 65.536
// Einheit: cm

#include "latlon.h"

typedef struct { // 26 bytes
	FLATPOS position;
	long	position_lat;
	long	position_lon;

	unsigned long innerRadius;
	unsigned long outerRadius;
	unsigned char force; // force in inner radius, 255 = full, 0 = outerRadius = null
	// 7 6 5 4 3 2 1 0
	//				 reverse
	unsigned char flags;
} OBSTACLE;


typedef struct { // 461 Byte
	FLATPOS center;
	FLATPOS position;
	unsigned long radius;
	unsigned char flags;
	unsigned long distanceFromCenter;
	// 0: nix
	// 1: startZone
	// 2: endZone
	// 3: boye clockwise
	// 4: boye anti-clockwise
	// 5: repeat start
	// 6: repeat end
} STARTPOINT;

typedef struct { // 462 Byte
	FLATPOS positionCurrentPoint;
	FLATPOS positionLastPoint;

	long positionCurrentPoint_lat;
	long positionCurrentPoint_lon;
	long positionLastPoint_lat;
	long positionLastPoint_lon;
	
	unsigned long radius;
	unsigned long rightBorder;
	unsigned long leftBorder;
	unsigned char flags;
	// 0: nix
	// 1: startZone
	// 2: endZone
	// 3: boye clockwise
	// 4: boye anti-clockwise
	// 5: repeat start
	// 6: repeat end
	OBSTACLE obstacles[16];
	unsigned char num_of_obstacles; 
} WAYPOINT;

typedef struct {
	int direction;			//2
	unsigned char speed;	//1
} WINDTOTIME; //3

typedef struct { 
// wie genau aufgebaut?
	unsigned char boatSpeedToWind[72];	//72
	unsigned char windStatistics[18];	//18
	WINDTOTIME windToTime[24];			//72
} WEATHER; //162

typedef struct {
	FLATPOS vertices_flat[4];
	unsigned char insideRectagle;
} STATIONKEEPING;

typedef struct {
	WAYPOINT currentWaypoint;
	unsigned int num_of_waypoints;
	unsigned char current_waypoint;
	unsigned char last_waypoint;
	WEATHER weather;
	unsigned char reserved[32];//32Byte fuer irgendwas reserviert...
	unsigned int waypointCounter;
	WAYPOINT overrideWaypoint;
	int overrideCourse;
	STARTPOINT startPoints[2];
	int startPointIndex;
	STATIONKEEPING stationkeeping;

	// Stationkeeping
	int inStation;
	long stationTimer;

} GAMESTATE;


// sets next waypoint, 
// assures correct behavior for repeating waypoints
void setWaypointReached();

// Gets current target waypoint
WAYPOINT nextWaypoint();

// Gets last and already passed waypoint
FLATPOS lastWaypoint();

volatile GAMESTATE gamestate;
