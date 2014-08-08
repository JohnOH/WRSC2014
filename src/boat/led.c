#include "led.h"
#include<avr/io.h>
#include<util/delay.h>

inline void led_on() { PORTA |= (1 << PA1); }

inline void led_off() { PORTA &= ~(1 << PA1); }

inline char taster_pressed() { return !(PINA & (1 << PA0)); }

inline void topled_on() { PORTC = 0xFF; }

inline void topled_off() { PORTC = 0x00; }

inline void debugled_on() {}

inline void debugled_off() {}

inline void siren_on() { PORTL = 0xFF;} // TODO set!

inline void siren_off() { PORTL = 0x00;}

void blink(unsigned char number, unsigned int delay)
{
	// TODO: PORTS VORHER SETZEN
	int i = 0;
	int j = 0;

	for(j = 0; j < number; j++) {
		led_on();
		for (i = 0; i < delay / 20; i++)
			_delay_ms(20);
		led_off();
		for (i = 0; i < delay / 20; i++)
			_delay_ms(20);
	}
}
