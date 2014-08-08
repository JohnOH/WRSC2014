#ifndef SERVOS_H
#define SERVOS_H

void servos_init(void);

void set_rudder(int tenth_degree);

void set_mainsail(int tenth_degree);

void set_foresail(int tenth_degree);

void servo_shutdown();

#endif
