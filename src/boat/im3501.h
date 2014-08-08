#ifndef IM3501_H
#define IM3501_H

void im3501_set_power(unsigned char power);

void im3501_read_register(unsigned char reg, unsigned char* LSB,unsigned char* MSB);

void im3501_set_navimode(unsigned char mode);

void im3501_set_calib();

int im3501_read_measurement(unsigned char reg);

#define im3501_read_azimuth() im3501_read_measurement(0x20)
#define im3501_read_pitch() im3501_read_measurement(0x22)
#define im3501_read_roll() im3501_read_measurement(0x24)

#define im3501_read_acc_x() im3501_read_measurement(0x26)
#define im3501_read_acc_y() im3501_read_measurement(0x28)
#define im3501_read_acc_z() im3501_read_measurement(0x2A)

#define im3501_read_mag_x() im3501_read_measurement(0x2C)
#define im3501_read_mag_y() im3501_read_measurement(0x2E)
#define im3501_read_mag_z() im3501_read_measurement(0x30)

#endif
