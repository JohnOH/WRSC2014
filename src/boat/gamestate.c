#include "gamestate.h"
#include "config.h"

volatile GAMESTATE gamestate;


void setWaypointReached(){//neuen Punkt laden!!!!!!!!!!!!!!!!!!!!!
	if(gamestate.waypointCounter<gamestate.num_of_waypoints){
		read_next_waypoint_from_SD();
	}
}

// Gets current target waypoint
WAYPOINT nextWaypoint(){return gamestate.currentWaypoint;} 

// Gets last and already passed waypoint
FLATPOS lastWaypoint(){return gamestate.currentWaypoint.positionLastPoint;} 

