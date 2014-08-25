#include "config.h"
#include "fat/fat.h"
#include "fat/fat_config.h"
#include "fat/partition.h"
#include "fat/sd_raw.h"
#include "fat/sd_raw_config.h"
#include "gamestate.h"
#include "led.h"
#include <stdio.h>
 
#include <string.h>

volatile CONFIG config;

void config_init() {
	// Configuration
	// This part will be modified to read values from SD card or EEPROM
 	config.rudder.middle_ticks = 0x02B2;
	config.rudder.maximal_ticks = -0xA0;
	config.rudder.maximal_tenth_degree = 450;
	config.rudder.position_tenth_degree = 0;

	// For MMs ...
	config.mainsail.middle_ticks = 0x0282;
	config.mainsail.maximal_ticks = -0xE6;
	config.mainsail.maximal_tenth_degree = 650;
	config.mainsail.position_tenth_degree = 0;

	// For MMs
	config.foresail.middle_ticks = 0x0282;
	config.foresail.maximal_ticks = 0xA8;
	config.foresail.maximal_tenth_degree = 900;
	config.foresail.position_tenth_degree = 0;
/*
 	config.rudder.middle_ticks = 0x02B2;
	config.rudder.maximal_ticks = 0xE6;
	config.rudder.maximal_tenth_degree = 450;
	config.rudder.position_tenth_degree = 0;

	// For MMs ...
	config.mainsail.middle_ticks = 0x0282;
	config.mainsail.maximal_ticks = 0xE6;
	config.mainsail.maximal_tenth_degree = 900;
	config.mainsail.position_tenth_degree = 0;

	// For MMs
	config.foresail.middle_ticks = 0x02B2;
	config.foresail.maximal_ticks = 0xE6;
	config.foresail.maximal_tenth_degree = 900;
	config.foresail.position_tenth_degree = 0;
*/	
	config.gps.options = 0;
	config.gps.valid = 0;	

	config.ai.active = 0;
	
	//war mal 200
	config.wind.offset_tenth_degree = 1000;

	config.aux.sendMessages = 0xFFFFFFFF;
//	config.aux.sendMessages = 0x00000000;

	//config.aux.readSensors = 0xFFFF & ~0x1C00; // Turn off im3501
	config.aux.readSensors = 0xFFFF; // Turn on im3501

	config.battery.divider = 124;
	config.battery.voltage_tenth_volt = 85;
}

uint8_t find_file_in_dir(struct fat_fs_struct* fs, struct fat_dir_struct* dd, const char* name, struct fat_dir_entry_struct* dir_entry)
{
    while(fat_read_dir(dd, dir_entry))
    {
        if(strcmp(dir_entry->long_name, name) == 0)
        {
            fat_reset_dir(dd);
            return 1;
        }
    }

    return 0;
}

struct fat_file_struct* open_file_in_dir(struct fat_fs_struct* fs, struct fat_dir_struct* dd, const char* name)
{
    struct fat_dir_entry_struct file_entry;
    if(!find_file_in_dir(fs, dd, name, &file_entry))
        return 0;

    return fat_open_file(fs, &file_entry);
}

unsigned char config_init_sd() 
{
	unsigned char result = 0;
	if (!sd_raw_init())
		return 0;
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );

	if(!partition)
		return 0;

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){
	

    	// open root directory
    	struct fat_dir_entry_struct directory;
    	fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
		  
		    // search file in current directory and open it
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "config.mm");
		    if(fd){
	
				// Read file to config variable (i like pointers ;-)
				result = (fat_read_file(fd, (unsigned char*)&config, sizeof(config)) == sizeof(config));

				// close file
			    fat_close_file(fd);
				}

			// close directory 
		    fat_close_dir(dd); 
			}

	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);

	return result;
}

void config_store_sd() {
	if (!sd_raw_init())
		return;
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );
	if(!partition)
		return;

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){

	    // open root directory
	    struct fat_dir_entry_struct directory;
	    fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
			
			// Try to create file (if not already created)
			struct fat_dir_entry_struct file_entry;
		    fat_create_file(dd, "config.mm", &file_entry);

			// search file in current directory and open it 
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "config.mm");
		    if(fd){

				// write text to file (i like pointers ;-)
				fat_write_file(fd, (unsigned char*)&config, sizeof(config));

				// close file
			    fat_close_file(fd);
			}

			// -----> WICHTIG <-----
			sd_raw_sync();

			// close directory 
		    fat_close_dir(dd);
		}
	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);

}



void config_write_log() {

if (!sd_raw_init())
		return;
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );
	if(!partition)
		return;

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){

	    // open root directory
	    struct fat_dir_entry_struct directory;
	    fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
			
			// Try to create file (if not already created)
			// struct fat_dir_entry_struct file_entry;
		    //fat_create_file(dd, "log.txt", &file_entry);

			// search file in current directory and open it 
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "log.txt");
		    if(fd){
				
				setFilePos(fd,logfilepos);
				// write text to file (i like pointers ;-)
				char data[256];
				int length = sprintf(data,"%lu,%li,%li,%li,%li,%i,%d,%i,%i,%i,%i.%i,%i,%i,%i,%d\r\n",
				config.gps.time,
				config.gps.latitude,
				config.gps.longitude,
				config.gps.position.North,
				config.gps.position.East,
				config.gps.heading_tenth_degree,
				config.gps.speed,
				config.compass.yaw_tenth_degree,
				config.compass.pitch_tenth_degree,
				config.compass.roll_tenth_degree,
				config.wind.winddir_tenth_degree,
				config.wind.speed_tenth_degree,
				config.rudder.position_tenth_degree,	
				config.mainsail.position_tenth_degree,	
				config.foresail.position_tenth_degree,
				config.battery.voltage_tenth_volt
				);
				fat_write_file(fd, (unsigned char*)data, length);

				logfilepos = getFilePos(fd);

				// close file
			    fat_close_file(fd);
			}

			// -----> WICHTIG <-----
			sd_raw_sync();

			// close directory 
		    fat_close_dir(dd);
		}
	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);
}

unsigned char read_gamestate_from_SD(){
	unsigned char result = 0;
	if (!sd_raw_init())
		return 0;
		blink(1,100);
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );

	if(!partition)
		return 0;
	blink(1,100);

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){
	

    	// open root directory
    	struct fat_dir_entry_struct directory;
    	fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
		  blink(1,100);
		    // search file in current directory and open it
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "game.mm");
		    if(fd){
				blink(1,100);
				//32Byte
				result = (fat_read_file(fd, (unsigned char*)&gamestate.reserved, sizeof(gamestate.reserved)));
				
				gamestate.waypointCounter = *(unsigned int*)(&gamestate.reserved);
				filepos=212+462*gamestate.waypointCounter;
				
				//4Byte
				result |= (fat_read_file(fd, (unsigned char*)&config.gps.posXlat, sizeof(config.gps.posXlat)));
				//4Byte
				result |= (fat_read_file(fd, (unsigned char*)&config.gps.posXlon, sizeof(config.gps.posXlon)));
				//4Byte
				result |= (fat_read_file(fd, (unsigned char*)&config.gps.factorLat, sizeof(config.gps.factorLat)));
				//4Byte
				result |= (fat_read_file(fd, (unsigned char*)&config.gps.factorLon, sizeof(config.gps.factorLon)));
				//162
				result |= (fat_read_file(fd, (unsigned char*)&gamestate.weather, sizeof(gamestate.weather)));
				//210Byte
				result |= (fat_read_file(fd, (unsigned char*)&gamestate.num_of_waypoints, sizeof(gamestate.num_of_waypoints)));
				//212Byte
			 	
				//filepos = getFilePos(fd);

					// close file
			    fat_close_file(fd);
				gamestate.waypointCounter++;
				}

			// close directory 
		    fat_close_dir(dd); 
			}

	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);

	return result;


}

unsigned char read_next_waypoint_from_SD(){
	unsigned char result = 0;
	if (!sd_raw_init())
		return 0;
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );

	if(!partition)
		return 0;

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){
	

    	// open root directory
    	struct fat_dir_entry_struct directory;
    	fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
		  
		    // search file in current directory and open it
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "game.mm");
		    if(fd){
    
				setFilePos(fd,filepos);
				result = (fat_read_file(fd, (unsigned char*)&gamestate.currentWaypoint, sizeof(gamestate.currentWaypoint)));
				filepos = filepos+462; // 462 Byte
				// close file
			    fat_close_file(fd);
				}

			// close directory 
		    fat_close_dir(dd); 
			}

	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);

	//neuen Punkt in gamestate speichern
	write_next_waypoint_to_SD();

	return result;


}
void write_next_waypoint_to_SD(){
	if (!sd_raw_init())
		return;
    // open first partition
    struct partition_struct* partition = partition_open(sd_raw_read,
                                                        sd_raw_read_interval,
                                                        sd_raw_write,
                                                        sd_raw_write_interval,
                                                        0
                                                       );
	if(!partition)
		return;

    // open file system
    struct fat_fs_struct* fs = fat_open(partition);
    if(fs){

	    // open root directory
	    struct fat_dir_entry_struct directory;
	    fat_get_dir_entry_of_path(fs, "/", &directory);

	    struct fat_dir_struct* dd = fat_open_dir(fs, &directory);
	    if(dd){
			
			// Try to create file (if not already created)
			struct fat_dir_entry_struct file_entry;
		    fat_create_file(dd, "game.mm", &file_entry);

			// search file in current directory and open it 
		    struct fat_file_struct* fd = open_file_in_dir(fs, dd, "game.mm");
		    if(fd){

				// write text to file (i like pointers ;-)
				fat_write_file(fd, (unsigned char*)&gamestate.waypointCounter, sizeof(gamestate.waypointCounter));
				// 462 Byte

				// close file
			    fat_close_file(fd);
			}

			// -----> WICHTIG <-----
			sd_raw_sync();

			// close directory 
		    fat_close_dir(dd);
		}
	    // close file system 
	    fat_close(fs);
	}

    // close partition 
    partition_close(partition);
}
