/**
 * RFM69 library functions that need to be customized for a particular hardware
 * platform (mosty SPI interface).
 *
 * This is for LPC1114.
 */

#ifdef __USE_CMSIS
#include "LPC11xx.h"
#endif

rfm69_init() {

	/* Use PIO0_1 as NSS. Condition PIO0_1 for GPIO output */
	LPC_IOCON->PIO0_1 = 0xC0;
	LPC_GPIO0->DIR |= 1<<1;
	LPC_GPIO0->DATA |= (1<<1);

	//spi_init_ssp0();
	spi_init_bitbang();

}

/**
 * Assert NSS line (bring low)
 */
void rfm69_nss_assert() {
	LPC_GPIO0->DATA &= ~(1<<1);
}

/**
 * Deassert NSS (slave select) line. Must be careful to ensure that SPI FIFO
 * has drained before doing this.
 */
void rfm69_nss_deassert() {
	spi_flush();
	LPC_GPIO0->DATA |= (1<<1);
}

uint8_t rfm69_spi_transfer_byte(uint8_t b) {
	return spi_transfer_byte(b);
}
