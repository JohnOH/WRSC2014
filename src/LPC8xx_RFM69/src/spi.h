#ifndef __SPI_H
#define __SPI_H

void spi_init_ssp0(void);
void spi_init_bitbang(void);

void spi_assert_ss(void);
void spi_deassert_ss(void);
void spi_send(uint8_t *buf, int length);
void spi_receive(uint8_t *buf, int length);
void spi_flush();

#endif /* end __SPI_H */
