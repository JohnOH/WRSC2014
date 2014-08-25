#include "im3501.h"
#include "i2cmaster.h"
#include <util/delay.h>

void im3501_set_power(unsigned char power) 
{
	// 0: (Magnetic sensor ON, Accelerometer ON, MICOM ON)
	// 1: (Magnetic sensor OFF, Accelerometer OFF, MICOM OFF)
	// 2: (Magnetic sensor OFF, Accelerometer ON, MICOM Idle)
	i2c_start(0x50);
	i2c_write(0x14);
	i2c_write(power);
	i2c_stop();
	_delay_ms(1);
}

void im3501_read_register(unsigned char reg, unsigned char* LSB,unsigned char* MSB)
{
	i2c_start(0x50);
	i2c_write(reg);
	i2c_rep_start(0x51);
	*MSB = i2c_readAck();
	*LSB = i2c_readNak();
	i2c_stop();
}

void im3501_set_navimode(unsigned char mode)
{
	// 0 Normal Mode (X axis : Direction of Azimuth)
	// 1 Navigation Mode (Z axis : Direction of Azimuth)
	i2c_start(0x50);
	i2c_write(0x50);
	i2c_write(mode);
	i2c_stop();
}

void im3501_set_calib() 
{
	i2c_start(0x50);
	i2c_write(0xAA);
	i2c_write(0x55);
	i2c_stop();
}

int im3501_read_measurement(unsigned char reg)
{
	int result = 0;
	i2c_start(0x50);
	i2c_write(reg);
	i2c_rep_start(0x51);
	*((unsigned char*)&result+1) = i2c_readAck();
	*((unsigned char*)&result+0) = i2c_readNak();
	i2c_stop();
	return result;
}
