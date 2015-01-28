This is a C RFM69(HW) radio module library intended for real time boat position reporting for WRSC2014.
This is a pre-release. An update in a few days will have more extensive documentation.

Files are:

rfm69.c: Main library file. This should be platform neutural and not require any editing.
rfm69.h: Header file defining public functions.
rfm69_rdl.h : RFM69 registers and values (auto-generated from IC Register Description Language [*])
rfm69_spi.c : SPI interface routines. This will need to be modified for whatever hardware platform is used.
rfm69_config.c : Application specific configuration.
rfm69_debug.c : Debugging functions (with dependencies on UART code etc). Disable for normal use.
rfm69_rdl.xml :  RFM69 registers in IC Register Description Language

[*] ICRDL is a exploritory project to describe ICs using a markup language with the purpose of auto-generating
header files, documentation etc. https://github.com/jdesbonnet/ICRDL

Joe Desbonnet
jdesbonnet@gmail.com
4 Sep 2014.

