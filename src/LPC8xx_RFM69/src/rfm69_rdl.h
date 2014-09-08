/**
 * C/C++ header file for RFM69
 * Auto generated by ICRDL v0.1. 
 * Do not edit. Instead edit ICRDL XML file and regenerate.
 */

/** Register RFM69_FIFO at address 0 **/
#define RFM69_FIFO (0)                            /* FIFO register address */

/** Register RFM69_OPMODE (Operation Mode) at address 0x01 **/
#define RFM69_OPMODE (0x01)                       /* OPMODE register address */
#define RFM69_OPMODE_SequencerOff (1<<7)          /*  */
#define RFM69_OPMODE_SequencerOff_MASK (0x1<<7)   /* SequencerOff bit mask */
#define RFM69_OPMODE_SequencerOff_VALUE(x) (((x)<<7)&RFM69_OPMODE_SequencerOff_MASK)
#define RFM69_OPMODE_ListenOn (1<<6)              /*  */
#define RFM69_OPMODE_ListenOn_MASK (0x1<<6)       /* ListenOn bit mask */
#define RFM69_OPMODE_ListenOn_VALUE(x) (((x)<<6)&RFM69_OPMODE_ListenOn_MASK)
#define RFM69_OPMODE_ListenOn_OFF (0)             /*  */
#define RFM69_OPMODE_ListenOn_ON (1)              /*  */
#define RFM69_OPMODE_Mode_MASK (0x7<<2)           /* Mode bit mask */
#define RFM69_OPMODE_Mode_VALUE(x) (((x)<<2)&RFM69_OPMODE_Mode_MASK)
#define RFM69_OPMODE_Mode_SLEEP (0)               /* Sleep mode */
#define RFM69_OPMODE_Mode_STDBY (1)               /* Standby mode */
#define RFM69_OPMODE_Mode_FS (2)                  /* Frequency Synthesis mode */
#define RFM69_OPMODE_Mode_TX (3)                  /* Transmitter mode */
#define RFM69_OPMODE_Mode_RX (4)                  /* Receiver mode */

/** Register RFM69_DATAMODUL (Data Modulation) at address 0x02 **/
#define RFM69_DATAMODUL (0x02)                    /* DATAMODUL register address */
#define RFM69_DATAMODUL_DataMode_MASK (0x3<<5)    /* DataMode bit mask */
#define RFM69_DATAMODUL_DataMode_VALUE(x) (((x)<<5)&RFM69_DATAMODUL_DataMode_MASK)
#define RFM69_DATAMODUL_DataMode_Packet (0)       /* Packet mode */
#define RFM69_DATAMODUL_DataMode_ContinuousBitSync (2) /* Continuous mode with bit synchronizer */
#define RFM69_DATAMODUL_DataMode_Continuous (3)   /* Continuous without bit synchronizer */
#define RFM69_DATAMODUL_ModulationType_MASK (0x3<<3) /* ModulationType bit mask */
#define RFM69_DATAMODUL_ModulationType_VALUE(x) (((x)<<3)&RFM69_DATAMODUL_ModulationType_MASK)
#define RFM69_DATAMODUL_ModulationType_FSK (0)    /* Frequency Shift Keying (FSK) */
#define RFM69_DATAMODUL_ModulationType_OOK (1)    /* On-Off Keying */
#define RFM69_DATAMODUL_ModulationShaping_MASK (0x3) /* ModulationShaping bit mask */
#define RFM69_DATAMODUL_ModulationShaping_VALUE(x) (((x)<<0)&RFM69_DATAMODUL_ModulationShaping_MASK)
#define RFM69_DATAMODUL_ModulationShaping_None (0) /* no shaping */
#define RFM69_DATAMODUL_ModulationShaping_SHAPING_OPT1 (1) /* FSK: Gaussian filter, BT=1.0; OOK: fcutoff=BR */
#define RFM69_DATAMODUL_ModulationShaping_SHAPING_OPT2 (2) /* FSK: Gaussian filter, BT=0.5; OOK: fcutoff=2BR */
#define RFM69_DATAMODUL_ModulationShaping_SHAPING_OPT3 (3) /* FSK: Gaussian filter, BT=0.3; OOK: reserved */

/** Register RFM69_BITRATEMSB (MSB of Bit Rate (Chip Rate when Manchesterencoing is enabled)) at address 0x03 **/
#define RFM69_BITRATEMSB (0x03)                   /* BITRATEMSB register address */

/** Register RFM69_BITRATELSB (LSB of Bit Rate (Chip Rate when Manchesterencoing is enabled)) at address 0x04 **/
#define RFM69_BITRATELSB (0x04)                   /* BITRATELSB register address */

/** Register RFM69_FDEVMSB (MSB of frequency deviation) at address 0x05 **/
#define RFM69_FDEVMSB (0x05)                      /* FDEVMSB register address */

/** Register RFM69_FDEVMSB (LSB of frequency deviation) at address 0x06 **/
#define RFM69_FDEVMSB (0x06)                      /* FDEVMSB register address */

/** Register RFM69_FRFMSB (MSB of the RF carrier frequency) at address 0x07 **/
#define RFM69_FRFMSB (0x07)                       /* FRFMSB register address */

/** Register RFM69_FRFMID (Middle byte of the RF carrier frequency) at address 0x08 **/
#define RFM69_FRFMID (0x08)                       /* FRFMID register address */

/** Register RFM69_FRFLSB (LSB of the RF carrier frequency) at address 0x09 **/
#define RFM69_FRFLSB (0x09)                       /* FRFLSB register address */

/** Register RFM69_OSC1 (RS Oscillator configuration) at address 0x0a **/
#define RFM69_OSC1 (0x0a)                         /* OSC1 register address */
#define RFM69_OSC1_RcCalStart (1<<7)              /*  */
#define RFM69_OSC1_RcCalStart_MASK (0x1<<7)       /* RcCalStart bit mask */
#define RFM69_OSC1_RcCalStart_VALUE(x) (((x)<<7)&RFM69_OSC1_RcCalStart_MASK)
#define RFM69_OSC1_RcCalStart_Trig (1)            /*  */
#define RFM69_OSC1_RcCalDone (1<<6)               /*  */
#define RFM69_OSC1_RcCalDone_MASK (0x1<<6)        /* RcCalDone bit mask */
#define RFM69_OSC1_RcCalDone_VALUE(x) (((x)<<6)&RFM69_OSC1_RcCalDone_MASK)
#define RFM69_OSC1_RcCalDone_InProgress (0)       /*  */
#define RFM69_OSC1_RcCalDone_Done (1)             /*  */

/** Register RFM69_AFCCTRL (Automatic frequency control configuration) at address 0x0b **/
#define RFM69_AFCCTRL (0x0b)                      /* AFCCTRL register address */
#define RFM69_AFCCTRL_AfcLowBetaOn (1<<5)         /*  */
#define RFM69_AFCCTRL_AfcLowBetaOn_MASK (0x1<<5)  /* AfcLowBetaOn bit mask */
#define RFM69_AFCCTRL_AfcLowBetaOn_VALUE(x) (((x)<<5)&RFM69_AFCCTRL_AfcLowBetaOn_MASK)
#define RFM69_AFCCTRL_AfcLowBetaOn_Trig (1)       /*  */
#define RFM69_AFCCTRL_RcCalDone (1<<6)            /*  */
#define RFM69_AFCCTRL_RcCalDone_MASK (0x1<<6)     /* RcCalDone bit mask */
#define RFM69_AFCCTRL_RcCalDone_VALUE(x) (((x)<<6)&RFM69_AFCCTRL_RcCalDone_MASK)
#define RFM69_AFCCTRL_RcCalDone_InProgress (0)    /*  */
#define RFM69_AFCCTRL_RcCalDone_Done (1)          /*  */

/** Register RFM69_LISTEN1 (Listen mode configuration) at address 0x0d **/
#define RFM69_LISTEN1 (0x0d)                      /* LISTEN1 register address */
#define RFM69_LISTEN1_ListenResolIdle_MASK (0x3<<6) /* ListenResolIdle bit mask */
#define RFM69_LISTEN1_ListenResolIdle_VALUE(x) (((x)<<6)&RFM69_LISTEN1_ListenResolIdle_MASK)
#define RFM69_LISTEN1_ListenResolIdle_64_us (1)   /* 64 µs */
#define RFM69_LISTEN1_ListenResolIdle_4100_us (2) /* 4.1ms */
#define RFM69_LISTEN1_ListenResolIdle_262_ms (3)  /* 262ms */
#define RFM69_LISTEN1_ListenResolRx_MASK (0x3<<4) /* ListenResolRx bit mask */
#define RFM69_LISTEN1_ListenResolRx_VALUE(x) (((x)<<4)&RFM69_LISTEN1_ListenResolRx_MASK)
#define RFM69_LISTEN1_ListenResolRx_64_us (1)     /* 64 µs */
#define RFM69_LISTEN1_ListenResolRx_4100_us (2)   /* 4.1ms */
#define RFM69_LISTEN1_ListenResolRx_262_ms (3)    /* 262ms */
#define RFM69_LISTEN1_ListenCriteria (1<<3)       /*  */
#define RFM69_LISTEN1_ListenCriteria_MASK (0x1<<3) /* ListenCriteria bit mask */
#define RFM69_LISTEN1_ListenCriteria_VALUE(x) (((x)<<3)&RFM69_LISTEN1_ListenCriteria_MASK)
#define RFM69_LISTEN1_ListenCriteria_GT_RSSI_THRESH (0) /* signal strength is above  RssiThreshold  */
#define RFM69_LISTEN1_ListenCriteria_GT_RSSI_THRESH_AND_SYNCADDR (1) /* signal strength is above RssiThreshold and SyncAddress match */
#define RFM69_LISTEN1_ListenEnd_MASK (0x3<<1)     /* ListenEnd bit mask */
#define RFM69_LISTEN1_ListenEnd_VALUE(x) (((x)<<1)&RFM69_LISTEN1_ListenEnd_MASK)
#define RFM69_LISTEN1_ListenEnd_OPT0 (0)          /* stay in Rx (ref §4.3) */
#define RFM69_LISTEN1_ListenEnd_OPT1 (1)          /* stays in Rx until PayloadReady or Timeout */
#define RFM69_LISTEN1_ListenEnd_OPT2 (2)          /* stays in Rx until PayloadReady or Timeout interrupt. Listen mode then resumes in Idle state. FIFO content is lost at next Rx wakeup. */

/** Register RFM69_LISTEN2 (Listen mode configuration) at address 0x0e **/
#define RFM69_LISTEN2 (0x0e)                      /* LISTEN2 register address */
#define RFM69_LISTEN2_ListenCoefIdle_MASK (0xff)  /* ListenCoefIdle bit mask */
#define RFM69_LISTEN2_ListenCoefIdle_VALUE(x) (((x)<<0)&RFM69_LISTEN2_ListenCoefIdle_MASK)

/** Register RFM69_LISTEN3 (Listen mode configuration) at address 0x0f **/
#define RFM69_LISTEN3 (0x0f)                      /* LISTEN3 register address */
#define RFM69_LISTEN3_ListenCoefRx_MASK (0xff)    /* ListenCoefRx bit mask */
#define RFM69_LISTEN3_ListenCoefRx_VALUE(x) (((x)<<0)&RFM69_LISTEN3_ListenCoefRx_MASK)

/** Register RFM69_VERSION (Version of chip) at address 0x10 **/
#define RFM69_VERSION (0x10)                      /* VERSION register address */
#define RFM69_VERSION_Version_MASK (0xff)         /* Version bit mask */
#define RFM69_VERSION_Version_VALUE(x) (((x)<<0)&RFM69_VERSION_Version_MASK)

/** Register RFM69_PALEVEL (Power Amplifier (PA) power configuration) at address 0x11 **/
#define RFM69_PALEVEL (0x11)                      /* PALEVEL register address */
#define RFM69_PALEVEL_Pa0On (1<<7)                /* Enables PA0 */
#define RFM69_PALEVEL_Pa0On_MASK (0x1<<7)         /* Pa0On bit mask */
#define RFM69_PALEVEL_Pa0On_VALUE(x) (((x)<<7)&RFM69_PALEVEL_Pa0On_MASK)
#define RFM69_PALEVEL_Pa0On_ON (1)                /*  */
#define RFM69_PALEVEL_Pa1On (1<<6)                /* Enables PA1 */
#define RFM69_PALEVEL_Pa1On_MASK (0x1<<6)         /* Pa1On bit mask */
#define RFM69_PALEVEL_Pa1On_VALUE(x) (((x)<<6)&RFM69_PALEVEL_Pa1On_MASK)
#define RFM69_PALEVEL_Pa2On (1<<5)                /* Enables PA2 */
#define RFM69_PALEVEL_Pa2On_MASK (0x1<<5)         /* Pa2On bit mask */
#define RFM69_PALEVEL_Pa2On_VALUE(x) (((x)<<5)&RFM69_PALEVEL_Pa2On_MASK)
#define RFM69_PALEVEL_OutputPower_MASK (0x1f)     /* OutputPower bit mask */
#define RFM69_PALEVEL_OutputPower_VALUE(x) (((x)<<0)&RFM69_PALEVEL_OutputPower_MASK)

/** Register RFM69_PARAMP (Rise/fall time of ramp up/down in FSK) at address 0x12 **/
#define RFM69_PARAMP (0x12)                       /* PARAMP register address */
#define RFM69_PARAMP_MASK (0xf)                   /*  bit mask */
#define RFM69_PARAMP_VALUE(x) (((x)<<0)&RFM69_PARAMP_MASK)
#define RFM69_PARAMP_3400us (0)                   /*  */
#define RFM69_PARAMP_2000us (1)                   /*  */
#define RFM69_PARAMP_1000us (2)                   /*  */
#define RFM69_PARAMP_500us (3)                    /*  */
#define RFM69_PARAMP_250us (4)                    /*  */
#define RFM69_PARAMP_125us (5)                    /*  */
#define RFM69_PARAMP_100us (6)                    /*  */
#define RFM69_PARAMP_62us (7)                     /*  */
#define RFM69_PARAMP_50us (8)                     /*  */
#define RFM69_PARAMP_40us (9)                     /*  */
#define RFM69_PARAMP_31us (10)                    /*  */
#define RFM69_PARAMP_25us (11)                    /*  */
#define RFM69_PARAMP_20us (12)                    /*  */
#define RFM69_PARAMP_15us (13)                    /*  */
#define RFM69_PARAMP_12us (14)                    /*  */
#define RFM69_PARAMP_10us (15)                    /*  */

/** Register RFM69_OCP (Over current protection (OCP) for the PA configuration) at address 0x13 **/
#define RFM69_OCP (0x13)                          /* OCP register address */
#define RFM69_OCP_OcpOn (1<<4)                    /* Enable OCP */
#define RFM69_OCP_OcpOn_MASK (0x1<<4)             /* OcpOn bit mask */
#define RFM69_OCP_OcpOn_VALUE(x) (((x)<<4)&RFM69_OCP_OcpOn_MASK)
#define RFM69_OCP_OcpTrim_MASK (0xf)              /* OcpTrim bit mask */
#define RFM69_OCP_OcpTrim_VALUE(x) (((x)<<0)&RFM69_OCP_OcpTrim_MASK)

/** Register RFM69_LNA (Low Noise Amplifier (LNA) configuration) at address 0x18 **/
#define RFM69_LNA (0x18)                          /* LNA register address */
#define RFM69_LNA_LnaZin (1<<7)                   /* LNA input impedence 0=50ohms; 1=200ohms */
#define RFM69_LNA_LnaZin_MASK (0x1<<7)            /* LnaZin bit mask */
#define RFM69_LNA_LnaZin_VALUE(x) (((x)<<7)&RFM69_LNA_LnaZin_MASK)
#define RFM69_LNA_LnaCurrentGain_MASK (0x7<<3)    /* LnaCurrentGain bit mask */
#define RFM69_LNA_LnaCurrentGain_VALUE(x) (((x)<<3)&RFM69_LNA_LnaCurrentGain_MASK)
#define RFM69_LNA_LnaGainSelect_MASK (0x7)        /* LnaGainSelect bit mask */
#define RFM69_LNA_LnaGainSelect_VALUE(x) (((x)<<0)&RFM69_LNA_LnaGainSelect_MASK)
#define RFM69_LNA_LnaGainSelect_AGC (0)           /* Set by AGC loop */
#define RFM69_LNA_LnaGainSelect_G1 (1)            /* higest gain */
#define RFM69_LNA_LnaGainSelect_G2 (2)            /* higest gain - 6dB */
#define RFM69_LNA_LnaGainSelect_G3 (3)            /* higest gain - 12dB */
#define RFM69_LNA_LnaGainSelect_G4 (4)            /* higest gain - 23dB */
#define RFM69_LNA_LnaGainSelect_G5 (5)            /* higest gain - 36dB */
#define RFM69_LNA_LnaGainSelect_G6 (6)            /* higest gain - 48dB */

/** Register RFM69_RXBW at address 0x19 **/
#define RFM69_RXBW (0x19)                         /* RXBW register address */
#define RFM69_RXBW_DccFreq_MASK (0x7<<5)          /* DccFreq bit mask */
#define RFM69_RXBW_DccFreq_VALUE(x) (((x)<<5)&RFM69_RXBW_DccFreq_MASK)
#define RFM69_RXBW_RxBwMant_MASK (0x3<<3)         /* RxBwMant bit mask */
#define RFM69_RXBW_RxBwMant_VALUE(x) (((x)<<3)&RFM69_RXBW_RxBwMant_MASK)
#define RFM69_RXBW_RxBwExp_MASK (0x7)             /* RxBwExp bit mask */
#define RFM69_RXBW_RxBwExp_VALUE(x) (((x)<<0)&RFM69_RXBW_RxBwExp_MASK)

/** Register RFM69_AFCBW at address 0x1A **/
#define RFM69_AFCBW (0x1A)                        /* AFCBW register address */
#define RFM69_AFCBW_DccFreqAfc_MASK (0x7<<5)      /* DccFreqAfc bit mask */
#define RFM69_AFCBW_DccFreqAfc_VALUE(x) (((x)<<5)&RFM69_AFCBW_DccFreqAfc_MASK)
#define RFM69_AFCBW_RxBwMantAfc_MASK (0x3<<3)     /* RxBwMantAfc bit mask */
#define RFM69_AFCBW_RxBwMantAfc_VALUE(x) (((x)<<3)&RFM69_AFCBW_RxBwMantAfc_MASK)
#define RFM69_AFCBW_RxBwExpAfc_MASK (0x7)         /* RxBwExpAfc bit mask */
#define RFM69_AFCBW_RxBwExpAfc_VALUE(x) (((x)<<0)&RFM69_AFCBW_RxBwExpAfc_MASK)

/** Register RFM69_RSSICONFIG at address 0x23 **/
#define RFM69_RSSICONFIG (0x23)                   /* RSSICONFIG register address */
#define RFM69_RSSICONFIG_RssiDone (1<<1)          /*  */
#define RFM69_RSSICONFIG_RssiDone_MASK (0x1<<1)   /* RssiDone bit mask */
#define RFM69_RSSICONFIG_RssiDone_VALUE(x) (((x)<<1)&RFM69_RSSICONFIG_RssiDone_MASK)
#define RFM69_RSSICONFIG_RssiDone_RssiDone (1)    /*  */
#define RFM69_RSSICONFIG_RssiStart (1<<0)         /*  */
#define RFM69_RSSICONFIG_RssiStart_MASK (0x1)     /* RssiStart bit mask */
#define RFM69_RSSICONFIG_RssiStart_VALUE(x) (((x)<<0)&RFM69_RSSICONFIG_RssiStart_MASK)

/** Register RFM69_RSSIVALUE (RSSI) at address 0x24 **/
#define RFM69_RSSIVALUE (0x24)                    /* RSSIVALUE register address */
#define RFM69_RSSIVALUE_RssiValue_MASK (0xff)     /* RssiValue bit mask */
#define RFM69_RSSIVALUE_RssiValue_VALUE(x) (((x)<<0)&RFM69_RSSIVALUE_RssiValue_MASK)

/** Register RFM69_IRQFLAGS1 (IRQ Flags 1) at address 0x27 **/
#define RFM69_IRQFLAGS1 (0x27)                    /* IRQFLAGS1 register address */
#define RFM69_IRQFLAGS1_ModeReady (1<<7)          /* Mode switch complete flag */
#define RFM69_IRQFLAGS1_ModeReady_MASK (0x1<<7)   /* ModeReady bit mask */
#define RFM69_IRQFLAGS1_ModeReady_VALUE(x) (((x)<<7)&RFM69_IRQFLAGS1_ModeReady_MASK)
#define RFM69_IRQFLAGS1_RxReady (1<<6)            /* Rx mode ready (RSSI, AGC, AFC complete) */
#define RFM69_IRQFLAGS1_RxReady_MASK (0x1<<6)     /* RxReady bit mask */
#define RFM69_IRQFLAGS1_RxReady_VALUE(x) (((x)<<6)&RFM69_IRQFLAGS1_RxReady_MASK)
#define RFM69_IRQFLAGS1_TxReady (1<<5)            /* Tx mode ready (PA ramped up) */
#define RFM69_IRQFLAGS1_TxReady_MASK (0x1<<5)     /* TxReady bit mask */
#define RFM69_IRQFLAGS1_TxReady_VALUE(x) (((x)<<5)&RFM69_IRQFLAGS1_TxReady_MASK)
#define RFM69_IRQFLAGS1_PllLock (1<<4)            /* PLL locked */
#define RFM69_IRQFLAGS1_PllLock_MASK (0x1<<4)     /* PllLock bit mask */
#define RFM69_IRQFLAGS1_PllLock_VALUE(x) (((x)<<4)&RFM69_IRQFLAGS1_PllLock_MASK)
#define RFM69_IRQFLAGS1_Rssi (1<<3)               /* RSSI exceeds threshold */
#define RFM69_IRQFLAGS1_Rssi_MASK (0x1<<3)        /* Rssi bit mask */
#define RFM69_IRQFLAGS1_Rssi_VALUE(x) (((x)<<3)&RFM69_IRQFLAGS1_Rssi_MASK)
#define RFM69_IRQFLAGS1_Timeout (1<<2)            /* Timeout */
#define RFM69_IRQFLAGS1_Timeout_MASK (0x1<<2)     /* Timeout bit mask */
#define RFM69_IRQFLAGS1_Timeout_VALUE(x) (((x)<<2)&RFM69_IRQFLAGS1_Timeout_MASK)
#define RFM69_IRQFLAGS1_AutoMode (1<<1)           /* In intermediate mode state */
#define RFM69_IRQFLAGS1_AutoMode_MASK (0x1<<1)    /* AutoMode bit mask */
#define RFM69_IRQFLAGS1_AutoMode_VALUE(x) (((x)<<1)&RFM69_IRQFLAGS1_AutoMode_MASK)
#define RFM69_IRQFLAGS1_SyncAddressMatch (1<<0)   /* Sync and Address match */
#define RFM69_IRQFLAGS1_SyncAddressMatch_MASK (0x1) /* SyncAddressMatch bit mask */
#define RFM69_IRQFLAGS1_SyncAddressMatch_VALUE(x) (((x)<<0)&RFM69_IRQFLAGS1_SyncAddressMatch_MASK)

/** Register RFM69_IRQFLAGS2 (IRQ Flags 2) at address 0x28 **/
#define RFM69_IRQFLAGS2 (0x28)                    /* IRQFLAGS2 register address */
#define RFM69_IRQFLAGS2_FifoFull (1<<7)           /* Set when FIFO is full (ie contains 66 bytes) */
#define RFM69_IRQFLAGS2_FifoFull_MASK (0x1<<7)    /* FifoFull bit mask */
#define RFM69_IRQFLAGS2_FifoFull_VALUE(x) (((x)<<7)&RFM69_IRQFLAGS2_FifoFull_MASK)
#define RFM69_IRQFLAGS2_FifoNotEmpty (1<<6)       /* Set when FIFO contains at least one byte */
#define RFM69_IRQFLAGS2_FifoNotEmpty_MASK (0x1<<6) /* FifoNotEmpty bit mask */
#define RFM69_IRQFLAGS2_FifoNotEmpty_VALUE(x) (((x)<<6)&RFM69_IRQFLAGS2_FifoNotEmpty_MASK)
#define RFM69_IRQFLAGS2_FifoLevel (1<<5)          /* Set when the number of bytes in the FIFO exceeds FifoThreshold */
#define RFM69_IRQFLAGS2_FifoLevel_MASK (0x1<<5)   /* FifoLevel bit mask */
#define RFM69_IRQFLAGS2_FifoLevel_VALUE(x) (((x)<<5)&RFM69_IRQFLAGS2_FifoLevel_MASK)
#define RFM69_IRQFLAGS2_FifoOverrun (1<<4)        /* Set when FIFO overrun occurs. Flags and FIFO are cleared when this bit is set. */
#define RFM69_IRQFLAGS2_FifoOverrun_MASK (0x1<<4) /* FifoOverrun bit mask */
#define RFM69_IRQFLAGS2_FifoOverrun_VALUE(x) (((x)<<4)&RFM69_IRQFLAGS2_FifoOverrun_MASK)
#define RFM69_IRQFLAGS2_PacketSent (1<<3)         /* Set when packet has been sent when in Tx mode. */
#define RFM69_IRQFLAGS2_PacketSent_MASK (0x1<<3)  /* PacketSent bit mask */
#define RFM69_IRQFLAGS2_PacketSent_VALUE(x) (((x)<<3)&RFM69_IRQFLAGS2_PacketSent_MASK)
#define RFM69_IRQFLAGS2_PayloadReady (1<<2)       /* Set when packet read to read in Rx mode. */
#define RFM69_IRQFLAGS2_PayloadReady_MASK (0x1<<2) /* PayloadReady bit mask */
#define RFM69_IRQFLAGS2_PayloadReady_VALUE(x) (((x)<<2)&RFM69_IRQFLAGS2_PayloadReady_MASK)
#define RFM69_IRQFLAGS2_CrcOk (1<<1)              /* Set when CRC has passed in Rx mode. */
#define RFM69_IRQFLAGS2_CrcOk_MASK (0x1<<1)       /* CrcOk bit mask */
#define RFM69_IRQFLAGS2_CrcOk_VALUE(x) (((x)<<1)&RFM69_IRQFLAGS2_CrcOk_MASK)

/** Register RFM69_RSSITHRESH (RSSI trigger level for Rssi interrupt) at address 0x29 **/
#define RFM69_RSSITHRESH (0x29)                   /* RSSITHRESH register address */
#define RFM69_RSSITHRESH_RssiThreshold_MASK (0xff) /* RssiThreshold bit mask */
#define RFM69_RSSITHRESH_RssiThreshold_VALUE(x) (((x)<<0)&RFM69_RSSITHRESH_RssiThreshold_MASK)

/** Register RFM69_RXTIMEOUT1 at address 0x2A **/
#define RFM69_RXTIMEOUT1 (0x2A)                   /* RXTIMEOUT1 register address */
#define RFM69_RXTIMEOUT1_TimeoutRxStart_MASK (0xff) /* TimeoutRxStart bit mask */
#define RFM69_RXTIMEOUT1_TimeoutRxStart_VALUE(x) (((x)<<0)&RFM69_RXTIMEOUT1_TimeoutRxStart_MASK)

/** Register RFM69_RXTIMEOUT2 at address 0x2B **/
#define RFM69_RXTIMEOUT2 (0x2B)                   /* RXTIMEOUT2 register address */
#define RFM69_RXTIMEOUT2_TimeoutRssiThresh_MASK (0xff) /* TimeoutRssiThresh bit mask */
#define RFM69_RXTIMEOUT2_TimeoutRssiThresh_VALUE(x) (((x)<<0)&RFM69_RXTIMEOUT2_TimeoutRssiThresh_MASK)

/** Register RFM69_SYNCCONFIG at address 0x2e **/
#define RFM69_SYNCCONFIG (0x2e)                   /* SYNCCONFIG register address */
#define RFM69_SYNCCONFIG_SyncOn (1<<7)            /* Enables Sync word generation and detection */
#define RFM69_SYNCCONFIG_SyncOn_MASK (0x1<<7)     /* SyncOn bit mask */
#define RFM69_SYNCCONFIG_SyncOn_VALUE(x) (((x)<<7)&RFM69_SYNCCONFIG_SyncOn_MASK)
#define RFM69_SYNCCONFIG_FifoFillCondition (1<<6) /* If 0 fills only if SyncAddress interrupt occurs */
#define RFM69_SYNCCONFIG_FifoFillCondition_MASK (0x1<<6) /* FifoFillCondition bit mask */
#define RFM69_SYNCCONFIG_FifoFillCondition_VALUE(x) (((x)<<6)&RFM69_SYNCCONFIG_FifoFillCondition_MASK)
#define RFM69_SYNCCONFIG_SyncSize_MASK (0x7<<3)   /* SyncSize bit mask */
#define RFM69_SYNCCONFIG_SyncSize_VALUE(x) (((x)<<3)&RFM69_SYNCCONFIG_SyncSize_MASK)
#define RFM69_SYNCCONFIG_SyncTol_MASK (0x7)       /* SyncTol bit mask */
#define RFM69_SYNCCONFIG_SyncTol_VALUE(x) (((x)<<0)&RFM69_SYNCCONFIG_SyncTol_MASK)

/** Register RFM69_SYNCVALUE1 at address 0x2f **/
#define RFM69_SYNCVALUE1 (0x2f)                   /* SYNCVALUE1 register address */

/** Register RFM69_SYNCVALUE2 at address 0x30 **/
#define RFM69_SYNCVALUE2 (0x30)                   /* SYNCVALUE2 register address */

/** Register RFM69_SYNCVALUE3 at address 0x31 **/
#define RFM69_SYNCVALUE3 (0x31)                   /* SYNCVALUE3 register address */

/** Register RFM69_SYNCVALUE4 at address 0x32 **/
#define RFM69_SYNCVALUE4 (0x32)                   /* SYNCVALUE4 register address */

/** Register RFM69_SYNCVALUE5 at address 0x33 **/
#define RFM69_SYNCVALUE5 (0x33)                   /* SYNCVALUE5 register address */

/** Register RFM69_SYNCVALUE6 at address 0x34 **/
#define RFM69_SYNCVALUE6 (0x34)                   /* SYNCVALUE6 register address */

/** Register RFM69_SYNCVALUE7 at address 0x35 **/
#define RFM69_SYNCVALUE7 (0x35)                   /* SYNCVALUE7 register address */

/** Register RFM69_SYNCVALUE8 at address 0x36 **/
#define RFM69_SYNCVALUE8 (0x36)                   /* SYNCVALUE8 register address */

/** Register RFM69_PACKETCONFIG1 (Packet Configuration 1) at address 0x37 **/
#define RFM69_PACKETCONFIG1 (0x37)                /* PACKETCONFIG1 register address */
#define RFM69_PACKETCONFIG1_PacketFormat (1<<7)   /* Defines the packet format used 0:fixed length; 1:variable length */
#define RFM69_PACKETCONFIG1_PacketFormat_MASK (0x1<<7) /* PacketFormat bit mask */
#define RFM69_PACKETCONFIG1_PacketFormat_VALUE(x) (((x)<<7)&RFM69_PACKETCONFIG1_PacketFormat_MASK)
#define RFM69_PACKETCONFIG1_PacketFormat_Fixed (0) /* Fixed length packets */
#define RFM69_PACKETCONFIG1_PacketFormat_Variable (1) /* Variable length packets */
#define RFM69_PACKETCONFIG1_DcFree_MASK (0x3<<5)  /* DcFree bit mask */
#define RFM69_PACKETCONFIG1_DcFree_VALUE(x) (((x)<<5)&RFM69_PACKETCONFIG1_DcFree_MASK)
#define RFM69_PACKETCONFIG1_DcFree_none (0)       /*  */
#define RFM69_PACKETCONFIG1_DcFree_Manchester (1) /*  */
#define RFM69_PACKETCONFIG1_DcFree_Whitening (2)  /*  */
#define RFM69_PACKETCONFIG1_CrcOn (1<<4)          /*  */
#define RFM69_PACKETCONFIG1_CrcOn_MASK (0x1<<4)   /* CrcOn bit mask */
#define RFM69_PACKETCONFIG1_CrcOn_VALUE(x) (((x)<<4)&RFM69_PACKETCONFIG1_CrcOn_MASK)
#define RFM69_PACKETCONFIG1_CrcAutoClearOff (1<<3) /*  */
#define RFM69_PACKETCONFIG1_CrcAutoClearOff_MASK (0x1<<3) /* CrcAutoClearOff bit mask */
#define RFM69_PACKETCONFIG1_CrcAutoClearOff_VALUE(x) (((x)<<3)&RFM69_PACKETCONFIG1_CrcAutoClearOff_MASK)
#define RFM69_PACKETCONFIG1_AddressFiltering_MASK (0x3<<1) /* AddressFiltering bit mask */
#define RFM69_PACKETCONFIG1_AddressFiltering_VALUE(x) (((x)<<1)&RFM69_PACKETCONFIG1_AddressFiltering_MASK)
#define RFM69_PACKETCONFIG1_AddressFiltering_none (0) /*  */
#define RFM69_PACKETCONFIG1_AddressFiltering_NodeOnly (1) /* Address must match NodeAddress */
#define RFM69_PACKETCONFIG1_AddressFiltering_NodeOrBroadcast (2) /* Address must match NodeAddress OR BroadcastAddress */

/** Register RFM69_PAYLOADLENGTH at address 0x38 **/
#define RFM69_PAYLOADLENGTH (0x38)                /* PAYLOADLENGTH register address */
#define RFM69_PAYLOADLENGTH_PayloadLength_MASK (0xff) /* PayloadLength bit mask */
#define RFM69_PAYLOADLENGTH_PayloadLength_VALUE(x) (((x)<<0)&RFM69_PAYLOADLENGTH_PayloadLength_MASK)

/** Register RFM69_NODEADRS (Node address) at address 0x39 **/
#define RFM69_NODEADRS (0x39)                     /* NODEADRS register address */

/** Register RFM69_BROADCASTADRS at address 0x3a **/
#define RFM69_BROADCASTADRS (0x3a)                /* BROADCASTADRS register address */

/** Register RFM69_FIFOTHRESH at address 0x3C **/
#define RFM69_FIFOTHRESH (0x3C)                   /* FIFOTHRESH register address */
#define RFM69_FIFOTHRESH_TxStartCondition (1<<7)  /*  */
#define RFM69_FIFOTHRESH_TxStartCondition_MASK (0x1<<7) /* TxStartCondition bit mask */
#define RFM69_FIFOTHRESH_TxStartCondition_VALUE(x) (((x)<<7)&RFM69_FIFOTHRESH_TxStartCondition_MASK)
#define RFM69_FIFOTHRESH_FifoThreshold_MASK (0x7f) /* FifoThreshold bit mask */
#define RFM69_FIFOTHRESH_FifoThreshold_VALUE(x) (((x)<<0)&RFM69_FIFOTHRESH_FifoThreshold_MASK)

/** Register RFM69_PACKETCONFIG2 (Packet Configuration 2) at address 0x3d **/
#define RFM69_PACKETCONFIG2 (0x3d)                /* PACKETCONFIG2 register address */
#define RFM69_PACKETCONFIG2_InterPacketRxDelay_MASK (0xf<<4) /* InterPacketRxDelay bit mask */
#define RFM69_PACKETCONFIG2_InterPacketRxDelay_VALUE(x) (((x)<<4)&RFM69_PACKETCONFIG2_InterPacketRxDelay_MASK)
#define RFM69_PACKETCONFIG2_RestartRx (1<<2)      /*  */
#define RFM69_PACKETCONFIG2_RestartRx_MASK (0x1<<2) /* RestartRx bit mask */
#define RFM69_PACKETCONFIG2_RestartRx_VALUE(x) (((x)<<2)&RFM69_PACKETCONFIG2_RestartRx_MASK)
#define RFM69_PACKETCONFIG2_AutoRxRestartOn (1<<1) /* Enable automatic Rx restart after Payloadready and FIFO read. */
#define RFM69_PACKETCONFIG2_AutoRxRestartOn_MASK (0x1<<1) /* AutoRxRestartOn bit mask */
#define RFM69_PACKETCONFIG2_AutoRxRestartOn_VALUE(x) (((x)<<1)&RFM69_PACKETCONFIG2_AutoRxRestartOn_MASK)
#define RFM69_PACKETCONFIG2_AesOn (1<<0)          /* Enable AES encryption */
#define RFM69_PACKETCONFIG2_AesOn_MASK (0x1)      /* AesOn bit mask */
#define RFM69_PACKETCONFIG2_AesOn_VALUE(x) (((x)<<0)&RFM69_PACKETCONFIG2_AesOn_MASK)

/** Register RFM69_TESTDAGC (Fading Margin Improvement, ref §3.4.4) at address 0x6f **/
#define RFM69_TESTDAGC (0x6f)                     /* TESTDAGC register address */
#define RFM69_TESTDAGC_ContinuousDagc_MASK (0xff) /* ContinuousDagc bit mask */
#define RFM69_TESTDAGC_ContinuousDagc_VALUE(x) (((x)<<0)&RFM69_TESTDAGC_ContinuousDagc_MASK)
#define RFM69_TESTDAGC_ContinuousDagc_Normal (0)  /*  */
#define RFM69_TESTDAGC_ContinuousDagc_LowBetaOn (0x20) /* Use if AfcLowBetaOn=1 */
#define RFM69_TESTDAGC_ContinuousDagc_LowBetaOff (0x30) /* Use if AfcLowBetaOn=0 */


