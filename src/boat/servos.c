#include "config.h"
#include <avr/io.h>
#include "servos.h"

void servos_init(void) {
	// PE7 PE6  PE5   PE4   PE3  PE2 PE1 PE0
	//  -   -  OCR3C OCR3B OCR3A  -  TX0 RX0

	// COM1A1 COM1A0 COM1B1 COM1B0 COM1C1 COM1C0 WGM11 WGM10
	//   1      0      0      0      0      0    0     0
	TCCR3A = 0xA8;
	// ICNC1 ICES1 - WGM13 WGM12 CS12 CS11 CS10
	//   0     0   0   1     0     0    1    0
	TCCR3B = 0x12;
	// FOC1A FOC1B FOC1C - - - - -
	//   0     0     0   
	TCCR3C = 0x00;
	// Timer counter value
	TCNT3 = 0x0000;
	// TOP value for 50Hz servo output
	ICR3 = 0x2400;

	// PH7 PH6  PH5   PH4   PH3  PH2 PH1 PH0
	//  -   -    -   OCR4B OCR4A  -   -   -

	// COM1A1 COM1A0 COM1B1 COM1B0 COM1C1 COM1C0 WGM11 WGM10
	//   1      0      1      0      0      0    0     0
	TCCR4A = 0xA0;
	// ICNC1 ICES1 - WGM13 WGM12 CS12 CS11 CS10
	//   0     0   0   1     0     0    1    0
	TCCR4B = 0x12;
	// FOC1A FOC1B FOC1C - - - - -
	//   0     0     0   
	TCCR4C = 0x00;
	// Timer counter value
	TCNT4 = 0x0000;
	// TOP value for 50Hz servo output
	ICR4 = 0x2400;
		// Set Servo pulse times to 1.5ms (standard middle position)

	OCR3A = config.rudder.middle_ticks;
	OCR4A = config.mainsail.middle_ticks;
	OCR4B = config.foresail.middle_ticks;

	// For PiXT
	OCR3B = config.mainsail.middle_ticks;
	OCR3C = config.foresail.middle_ticks;
}

void set_rudder(int tenth_degree) { // +-45.0° = +- 230
	if (tenth_degree > config.rudder.maximal_tenth_degree) tenth_degree = config.rudder.maximal_tenth_degree;
	if (tenth_degree < -1*config.rudder.maximal_tenth_degree) tenth_degree = -1*config.rudder.maximal_tenth_degree;

	OCR3A = ((-1*(long)tenth_degree * (long)config.rudder.maximal_ticks) / config.rudder.maximal_tenth_degree) + config.rudder.middle_ticks;
}

void set_mainsail(int tenth_degree) {
	if (tenth_degree > config.mainsail.maximal_tenth_degree) tenth_degree = config.mainsail.maximal_tenth_degree;
	if (tenth_degree < -1*config.mainsail.maximal_tenth_degree) tenth_degree = -1*config.mainsail.maximal_tenth_degree;

	OCR4A = ((-1*(long)tenth_degree * (long)config.mainsail.maximal_ticks) / config.mainsail.maximal_tenth_degree) + config.mainsail.middle_ticks;

	// For PiXT
	OCR3B = (((long)tenth_degree * (long)config.mainsail.maximal_ticks) / config.mainsail.maximal_tenth_degree) + config.mainsail.middle_ticks;
}

void set_foresail(int tenth_degree) {
	if (tenth_degree > config.foresail.maximal_tenth_degree) tenth_degree = config.foresail.maximal_tenth_degree;
	if (tenth_degree < -1*config.foresail.maximal_tenth_degree) tenth_degree = -1*config.foresail.maximal_tenth_degree;

	OCR4B = ((1*(long)tenth_degree * (long)config.foresail.maximal_ticks) / config.foresail.maximal_tenth_degree) + config.foresail.middle_ticks;

	// For PiXT
	OCR3C = ((-1*(long)tenth_degree * (long)config.foresail.maximal_ticks) / config.foresail.maximal_tenth_degree) + config.foresail.middle_ticks;
}

void servo_shutdown() {
	TCCR3A = 0x00;
	TCCR4A = 0x00;
}
