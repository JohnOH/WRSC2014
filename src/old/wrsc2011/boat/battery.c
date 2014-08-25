#include "battery.h"
#include "config.h"
#include "led.h"

void Battery_measure()
{
	// REFS1 REFS0 ADLAR MUX4 MUX3 MUX2 MUX1 MUX0
	// 0     1     0     0    0    1    1    1
	ADMUX = 0x06;	// Battery register selection MUX5
	ADCSRB |= 0x08;
	// ADEN ADSC ADATE ADIF ADIE ADPS2 ADPS1 ADPS0
	// 1    1    0     0    1    1     1     1
	ADCSRA = 0xCF;
}

ISR(ADC_vect)
{
	debugled_on();
    config.battery.voltage_tenth_volt = ADC*10 / config.battery.divider;
	ADCSRA &= ~(1 << ADIE);
	debugled_off();
}
