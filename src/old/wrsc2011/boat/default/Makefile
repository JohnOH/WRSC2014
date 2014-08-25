###############################################################################
# Makefile for the project NewMM
###############################################################################

## General Flags
PROJECT = NewMM
MCU = atmega2560
TARGET = NewMM.elf
CC = avr-gcc

CPP = avr-g++

## Options common to compile, link and assembly rules
COMMON = -mmcu=$(MCU)

## Compile options common for all C compilation units.
CFLAGS = $(COMMON)
CFLAGS += -Wall -gdwarf-2 -std=gnu99                -DF_CPU=7372800UL -O2 -funsigned-char -funsigned-bitfields -fpack-struct -fshort-enums -fgnu89-inline
CFLAGS += -MD -MP -MT $(*F).o -MF dep/$(@F).d 

## Assembly specific flags
ASMFLAGS = $(COMMON)
ASMFLAGS += $(CFLAGS)
ASMFLAGS += -x assembler-with-cpp -Wa,-gdwarf2

## Linker flags
LDFLAGS = $(COMMON)
LDFLAGS += -lm  -lc


## Intel Hex file production flags
HEX_FLASH_FLAGS = -R .eeprom -R .fuse -R .lock -R .signature

HEX_EEPROM_FLAGS = -j .eeprom
HEX_EEPROM_FLAGS += --set-section-flags=.eeprom="alloc,load"
HEX_EEPROM_FLAGS += --change-section-lma .eeprom=0 --no-change-warnings


## Libraries
LIBS = -lc -lm 

## Objects that must be built in order to link
OBJECTS = bluetooth.o config.o main.o servos.o gps.o led.o loop.o icp.o battery.o im3501.o i2cmaster.o byteordering.o fat.o partition.o sd_raw.o ai.o 

## Objects explicitly added by the user
LINKONLYOBJECTS = 

## Build
all: $(TARGET) NewMM.hex NewMM.eep size

## Compile
bluetooth.o: ../bluetooth.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

config.o: ../config.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

main.o: ../main.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

servos.o: ../servos.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

gps.o: ../gps.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

led.o: ../led.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

loop.o: ../loop.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

icp.o: ../icp.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

battery.o: ../battery.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

im3501.o: ../im3501.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

i2cmaster.o: ../i2cmaster.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

byteordering.o: ../fat/byteordering.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

fat.o: ../fat/fat.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

partition.o: ../fat/partition.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

sd_raw.o: ../fat/sd_raw.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

ai.o: ../ai.c
	$(CC) $(INCLUDES) $(CFLAGS) -c  $<

##Link
$(TARGET): $(OBJECTS)
	 $(CC) $(LDFLAGS) $(OBJECTS) $(LINKONLYOBJECTS) $(LIBDIRS) $(LIBS) -o $(TARGET)

%.hex: $(TARGET)
	avr-objcopy -O ihex $(HEX_FLASH_FLAGS)  $< $@

%.eep: $(TARGET)
	-avr-objcopy $(HEX_EEPROM_FLAGS) -O ihex $< $@ || exit 0

%.lss: $(TARGET)
	avr-objdump -h -S $< > $@

size: ${TARGET}
	@echo
	@avr-size -C --mcu=${MCU} ${TARGET}

## Clean target
.PHONY: clean
clean:
	-rm -rf $(OBJECTS) NewMM.elf dep/* NewMM.hex NewMM.eep

## Other dependencies
-include $(shell mkdir dep 2>/dev/null) $(wildcard dep/*)

