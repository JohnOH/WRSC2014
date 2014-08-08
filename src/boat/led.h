/**************************************************************************
 * Title:	LED and Taster library							              *
 * Author:	Ralf Bruder <contact@rbru.eu>								  *
 *         																  *
 * Desc:	Just a small auxiliary library for led and taster	s		  *
 **************************************************************************/
 
#ifndef LED_H
#define LED_H

void led_on();

void led_off();

void topled_on();

void topled_off();

void debugled_on();

void debugled_off();

char taster_pressed();

void blink(unsigned char number, unsigned int delay);

void siren_on();

void siren_off();

#endif
