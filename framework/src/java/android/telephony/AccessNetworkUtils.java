package android.telephony;

/* loaded from: classes3.dex */
public class AccessNetworkUtils {
    private static final int FREQUENCY_KHZ = 1000;
    private static final int FREQUENCY_RANGE_HIGH_KHZ = 6000000;
    private static final int FREQUENCY_RANGE_LOW_KHZ = 1000000;
    private static final int FREQUENCY_RANGE_MID_KHZ = 3000000;
    public static final int INVALID_BAND = -1;
    public static final int INVALID_FREQUENCY = -1;
    private static final java.lang.String JAPAN_ISO_COUNTRY_CODE = "jp";
    private static final java.lang.String TAG = "AccessNetworkUtils";
    private static final java.util.Set<java.lang.Integer> UARFCN_NOT_GENERAL_BAND = new java.util.HashSet();

    private AccessNetworkUtils() {
    }

    static {
        UARFCN_NOT_GENERAL_BAND.add(101);
        UARFCN_NOT_GENERAL_BAND.add(102);
        UARFCN_NOT_GENERAL_BAND.add(103);
        UARFCN_NOT_GENERAL_BAND.add(104);
        UARFCN_NOT_GENERAL_BAND.add(105);
        UARFCN_NOT_GENERAL_BAND.add(106);
    }

    public static int getDuplexModeForEutranBand(int i) {
        if (i == -1 || i > 88) {
            return 0;
        }
        if (i >= 65) {
            return 1;
        }
        if (i >= 33) {
            return 2;
        }
        return i >= 1 ? 1 : 0;
    }

    public static int getOperatingBandForEarfcn(int i) {
        if (i > 70645) {
            return -1;
        }
        if (i >= 70596) {
            return 88;
        }
        if (i >= 70546) {
            return 87;
        }
        if (i >= 70366) {
            return 85;
        }
        if (i > 69465) {
            return -1;
        }
        if (i >= 69036) {
            return 74;
        }
        if (i >= 68986) {
            return 73;
        }
        if (i >= 68936) {
            return 72;
        }
        if (i >= 68586) {
            return 71;
        }
        if (i >= 68336) {
            return 70;
        }
        if (i > 67835) {
            return -1;
        }
        if (i >= 67536) {
            return 68;
        }
        if (i >= 67366) {
            return -1;
        }
        if (i >= 66436) {
            return 66;
        }
        if (i >= 65536) {
            return 65;
        }
        if (i > 60254) {
            return -1;
        }
        if (i >= 60140) {
            return 53;
        }
        if (i >= 59140) {
            return 52;
        }
        if (i >= 59090) {
            return 51;
        }
        if (i >= 58240) {
            return 50;
        }
        if (i >= 56740) {
            return 49;
        }
        if (i >= 55240) {
            return 48;
        }
        if (i >= 54540) {
            return 47;
        }
        if (i >= 46790) {
            return 46;
        }
        if (i >= 46590) {
            return 45;
        }
        if (i >= 45590) {
            return 44;
        }
        if (i >= 43590) {
            return 43;
        }
        if (i >= 41590) {
            return 42;
        }
        if (i >= 39650) {
            return 41;
        }
        if (i >= 38650) {
            return 40;
        }
        if (i >= 38250) {
            return 39;
        }
        if (i >= 37750) {
            return 38;
        }
        if (i >= 37550) {
            return 37;
        }
        if (i >= 36950) {
            return 36;
        }
        if (i >= 36350) {
            return 35;
        }
        if (i >= 36200) {
            return 34;
        }
        if (i >= 36000) {
            return 33;
        }
        if (i > 10359 || i >= 9920) {
            return -1;
        }
        if (i >= 9870) {
            return 31;
        }
        if (i >= 9770) {
            return 30;
        }
        if (i >= 9660) {
            return -1;
        }
        if (i >= 9210) {
            return 28;
        }
        if (i >= 9040) {
            return 27;
        }
        if (i >= 8690) {
            return 26;
        }
        if (i >= 8040) {
            return 25;
        }
        if (i >= 7700) {
            return 24;
        }
        if (i >= 7500) {
            return 23;
        }
        if (i >= 6600) {
            return 22;
        }
        if (i >= 6450) {
            return 21;
        }
        if (i >= 6150) {
            return 20;
        }
        if (i >= 6000) {
            return 19;
        }
        if (i >= 5850) {
            return 18;
        }
        if (i >= 5730) {
            return 17;
        }
        if (i > 5379) {
            return -1;
        }
        if (i >= 5280) {
            return 14;
        }
        if (i >= 5180) {
            return 13;
        }
        if (i >= 5010) {
            return 12;
        }
        if (i >= 4750) {
            return 11;
        }
        if (i >= 4150) {
            return 10;
        }
        if (i >= 3800) {
            return 9;
        }
        if (i >= 3450) {
            return 8;
        }
        if (i >= 2750) {
            return 7;
        }
        if (i >= 2650) {
            return 6;
        }
        if (i >= 2400) {
            return 5;
        }
        if (i >= 1950) {
            return 4;
        }
        if (i >= 1200) {
            return 3;
        }
        if (i >= 600) {
            return 2;
        }
        return i >= 0 ? 1 : -1;
    }

    public static int getOperatingBandForNrarfcn(int i) {
        if (i >= 422000 && i <= 434000) {
            return 1;
        }
        if (i >= 386000 && i <= 398000) {
            return 2;
        }
        if (i >= 361000 && i <= 376000) {
            return 3;
        }
        if (i >= 173800 && i <= 178800) {
            return 5;
        }
        if (i >= 524000 && i <= 538000) {
            return 7;
        }
        if (i >= 185000 && i <= 192000) {
            return 8;
        }
        if (i >= 145800 && i <= 149200) {
            return 12;
        }
        if (i >= 151600 && i <= 153600) {
            return 14;
        }
        if (i >= 172000 && i <= 175000) {
            return 18;
        }
        if (i < 158200 || i > 164200) {
            if (i >= 386000 && i <= 399000) {
                return 25;
            }
            if (i >= 171800 && i <= 178800) {
                return 26;
            }
            if (i >= 151600 && i <= 160600) {
                return 28;
            }
            if (i >= 143400 && i <= 145600) {
                return 29;
            }
            if (i >= 470000 && i <= 472000) {
                return 30;
            }
            if (i >= 402000 && i <= 405000) {
                return 34;
            }
            if (i >= 514000 && i <= 524000) {
                return 38;
            }
            if (i >= 376000 && i <= 384000) {
                return 39;
            }
            if (i >= 460000 && i <= 480000) {
                return 40;
            }
            if (i >= 499200 && i <= 537999) {
                return 41;
            }
            if (i >= 743334 && i <= 795000) {
                return 46;
            }
            if (i >= 636667 && i <= 646666) {
                return 48;
            }
            if (i >= 286400 && i <= 303400) {
                return 50;
            }
            if (i >= 285400 && i <= 286400) {
                return 51;
            }
            if (i >= 496700 && i <= 499000) {
                return 53;
            }
            if (i >= 422000 && i <= 440000) {
                return 65;
            }
            if (i >= 399000 && i <= 404000) {
                return 70;
            }
            if (i >= 123400 && i <= 130400) {
                return 71;
            }
            if (i >= 295000 && i <= 303600) {
                return 74;
            }
            if (i >= 286400 && i <= 303400) {
                return 75;
            }
            if (i >= 285400 && i <= 286400) {
                return 76;
            }
            if (i >= 620000 && i <= 680000) {
                return 77;
            }
            if (i >= 620000 && i <= 653333) {
                return 78;
            }
            if (i >= 693334 && i <= 733333) {
                return 79;
            }
            if (i >= 499200 && i <= 538000) {
                return 90;
            }
            if (i >= 285400 && i <= 286400) {
                return 91;
            }
            if (i >= 286400 && i <= 303400) {
                return 92;
            }
            if (i >= 285400 && i <= 286400) {
                return 93;
            }
            if (i >= 286400 && i <= 303400) {
                return 94;
            }
            if (i >= 795000 && i <= 875000) {
                return 96;
            }
            if (i >= 2054166 && i <= 2104165) {
                return 257;
            }
            if (i >= 2016667 && i <= 2070832) {
                return 258;
            }
            if (i >= 2229166 && i <= 2279165) {
                return 260;
            }
            if (i >= 2070833 && i <= 2084999) {
                return 261;
            }
            return -1;
        }
        return 20;
    }

    public static int getOperatingBandForArfcn(int i) {
        if (i >= 0 && i <= 124) {
            return 10;
        }
        if (i >= 128 && i <= 251) {
            return 8;
        }
        if (i >= 259 && i <= 293) {
            return 3;
        }
        if (i >= 306 && i <= 340) {
            return 4;
        }
        if (i >= 438 && i <= 511) {
            return 6;
        }
        if (i >= 512 && i <= 885) {
            return 12;
        }
        if (i >= 940 && i <= 974) {
            return 14;
        }
        if (i >= 975 && i <= 1023) {
            return 10;
        }
        return -1;
    }

    public static int getOperatingBandForUarfcn(int i) {
        int[] iArr = {412, 437, 462, 487, 512, 537, 562, 587, 612, 637, 662, 687};
        int[] iArr2 = {1887, android.app.settings.SettingsEnums.ACCESSIBILITY_TEXT_READING_OPTIONS, 1937, 1962, android.app.settings.SettingsEnums.DIALOG_SPECIFIC_DDS_SIM_PICKER, 2012, 2037, 2062, 2087};
        int[] iArr3 = {1007, 1012, 1032, 1037, 1062, 1087};
        int[] iArr4 = {1037, 1062};
        int[] iArr5 = {2587, 2612, 2637, 2662, 2687, 2712, 2737, 2762, 2787, 2812, 2837, 2862, 2887, 2912};
        int[] iArr6 = {3412, 3437, 3462, 3487, 3512, 3537, 3562, 3587, 3612, 3637, 3662, 3687};
        int[] iArr7 = {3932, 3957, 3962, 3987, 3992};
        int[] iArr8 = {4067, 4092};
        int[] iArr9 = {4167, 4192};
        int[] iArr10 = {787, 812, com.android.internal.logging.nano.MetricsProto.MetricsEvent.WIFI_NETWORK_RECOMMENDATION_CONNECTION_SUCCESS};
        int[] iArr11 = {6292, 6317, 6342, 6367, android.telephony.ServiceState.RIL_RADIO_CDMA_TECHNOLOGY_BITMASK, 6417, 6442, 6467, 6492, 6517, 6542, 6567, 6592};
        int[] iArr12 = {5937, 5962, 5987, 5992, 6012, 6017, 6037, 6042, 6062, 6067, 6087};
        if (i < 10562 || i > 10838) {
            if ((i < 9662 || i > 9938) && java.util.Arrays.binarySearch(iArr, i) < 0) {
                if (i < 1162 || i > 1513) {
                    if ((i < 1537 || i > 1738) && java.util.Arrays.binarySearch(iArr2, i) < 0) {
                        if (i >= 4387 && i <= 4413) {
                            return JAPAN_ISO_COUNTRY_CODE.compareToIgnoreCase(android.telephony.TelephonyManager.getDefault().getNetworkCountryIso()) == 0 ? 6 : 5;
                        }
                        if ((i >= 4357 && i <= 4458) || java.util.Arrays.binarySearch(iArr3, i) >= 0) {
                            return 5;
                        }
                        if (java.util.Arrays.binarySearch(iArr4, i) < 0) {
                            if ((i < 2237 || i > 2563) && java.util.Arrays.binarySearch(iArr5, i) < 0) {
                                if (i < 2937 || i > 3088) {
                                    if (i < 9237 || i > 9387) {
                                        if ((i < 3112 || i > 3388) && java.util.Arrays.binarySearch(iArr6, i) < 0) {
                                            if (i < 3712 || i > 3787) {
                                                if ((i < 3842 || i > 3903) && java.util.Arrays.binarySearch(iArr7, i) < 0) {
                                                    if ((i < 4017 || i > 4043) && java.util.Arrays.binarySearch(iArr8, i) < 0) {
                                                        if ((i < 4117 || i > 4143) && java.util.Arrays.binarySearch(iArr9, i) < 0) {
                                                            if ((i < 712 || i > 763) && java.util.Arrays.binarySearch(iArr10, i) < 0) {
                                                                if (i < 4512 || i > 4638) {
                                                                    if (i < 862 || i > 912) {
                                                                        if (i < 4662 || i > 5038) {
                                                                            if ((i < 5112 || i > 5413) && java.util.Arrays.binarySearch(iArr11, i) < 0) {
                                                                                if ((i >= 5762 && i <= 5913) || java.util.Arrays.binarySearch(iArr12, i) >= 0) {
                                                                                    return 26;
                                                                                }
                                                                                return -1;
                                                                            }
                                                                            return 25;
                                                                        }
                                                                        return 22;
                                                                    }
                                                                    return 21;
                                                                }
                                                                return 20;
                                                            }
                                                            return 19;
                                                        }
                                                        return 14;
                                                    }
                                                    return 13;
                                                }
                                                return 12;
                                            }
                                            return 11;
                                        }
                                        return 10;
                                    }
                                    return 9;
                                }
                                return 8;
                            }
                            return 7;
                        }
                        return 6;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static int getFrequencyRangeGroupFromGeranBand(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
                return 1;
            case 12:
            case 13:
                return 2;
            default:
                return 0;
        }
    }

    public static int getFrequencyRangeGroupFromUtranBand(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 9:
            case 10:
            case 11:
            case 21:
            case 25:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
                return 2;
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 14:
            case 19:
            case 20:
            case 26:
                return 1;
            case 22:
                return 3;
            default:
                return 0;
        }
    }

    public static int getFrequencyRangeGroupFromEutranBand(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 9:
            case 10:
            case 11:
            case 21:
            case 23:
            case 24:
            case 25:
            case 30:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 45:
            case 53:
            case 65:
            case 66:
            case 70:
            case 74:
                return 2;
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            case 20:
            case 26:
            case 27:
            case 28:
            case 31:
            case 44:
            case 50:
            case 51:
            case 68:
            case 71:
            case 72:
            case 73:
            case 85:
            case 87:
            case 88:
                return 1;
            case 15:
            case 16:
            case 29:
            case 32:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 67:
            case 69:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 86:
            default:
                return 0;
            case 22:
            case 42:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 52:
                return 3;
        }
    }

    public static int getFrequencyRangeGroupFromNrBand(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 7:
            case 25:
            case 30:
            case 34:
            case 38:
            case 39:
            case 40:
            case 41:
            case 50:
            case 51:
            case 53:
            case 65:
            case 66:
            case 70:
            case 74:
            case 75:
            case 76:
            case 80:
            case 84:
            case 86:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
                return 2;
            case 5:
            case 8:
            case 12:
            case 14:
            case 18:
            case 20:
            case 26:
            case 28:
            case 29:
            case 71:
            case 81:
            case 82:
            case 83:
            case 89:
                return 1;
            case 46:
            case 48:
            case 77:
            case 78:
            case 79:
                return 3;
            case 96:
            case 257:
            case 258:
            case 260:
            case 261:
                return 4;
            default:
                return 0;
        }
    }

    public static int getFrequencyFromNrArfcn(int i) {
        int i2;
        int i3;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        android.telephony.AccessNetworkConstants.NgranArfcnFrequency[] values = android.telephony.AccessNetworkConstants.NgranArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i2 = 0;
                i3 = 0;
                break;
            }
            android.telephony.AccessNetworkConstants.NgranArfcnFrequency ngranArfcnFrequency = values[i5];
            if (i < ngranArfcnFrequency.rangeFirst || i > ngranArfcnFrequency.rangeLast) {
                i5++;
            } else {
                int i6 = ngranArfcnFrequency.globalKhz;
                int i7 = ngranArfcnFrequency.rangeOffset;
                i3 = ngranArfcnFrequency.arfcnOffset;
                i4 = i7;
                i2 = i6;
                break;
            }
        }
        return i4 + (i2 * (i - i3));
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
    
        return convertEarfcnToFrequency(r2, r7, r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getFrequencyFromEarfcn(int i, int i2, boolean z) {
        int i3;
        android.telephony.AccessNetworkConstants.EutranBandArfcnFrequency[] values = android.telephony.AccessNetworkConstants.EutranBandArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i3 = 0;
                break;
            }
            android.telephony.AccessNetworkConstants.EutranBandArfcnFrequency eutranBandArfcnFrequency = values[i5];
            if (i != eutranBandArfcnFrequency.band) {
                i5++;
            } else if (isInEarfcnRange(i2, eutranBandArfcnFrequency, z)) {
                i4 = z ? eutranBandArfcnFrequency.uplinkLowKhz : eutranBandArfcnFrequency.downlinkLowKhz;
                i3 = z ? eutranBandArfcnFrequency.uplinkOffset : eutranBandArfcnFrequency.downlinkOffset;
            } else {
                android.telephony.Rlog.w(TAG, "Band and the range of EARFCN are not consistent: band = " + i + " ,earfcn = " + i2 + " ,isUplink = " + z);
                return -1;
            }
        }
    }

    private static int convertEarfcnToFrequency(int i, int i2, int i3) {
        return i + ((i2 - i3) * 100);
    }

    private static boolean isInEarfcnRange(int i, android.telephony.AccessNetworkConstants.EutranBandArfcnFrequency eutranBandArfcnFrequency, boolean z) {
        return z ? i >= eutranBandArfcnFrequency.uplinkOffset && i <= eutranBandArfcnFrequency.uplinkRange : i >= eutranBandArfcnFrequency.downlinkOffset && i <= eutranBandArfcnFrequency.downlinkRange;
    }

    public static int getFrequencyFromUarfcn(int i, int i2, boolean z) {
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        android.telephony.AccessNetworkConstants.UtranBandArfcnFrequency[] values = android.telephony.AccessNetworkConstants.UtranBandArfcnFrequency.values();
        int length = values.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            android.telephony.AccessNetworkConstants.UtranBandArfcnFrequency utranBandArfcnFrequency = values[i4];
            if (i != utranBandArfcnFrequency.band) {
                i4++;
            } else if (isInUarfcnRange(i2, utranBandArfcnFrequency, z)) {
                i3 = z ? utranBandArfcnFrequency.uplinkOffset : utranBandArfcnFrequency.downlinkOffset;
            } else {
                android.telephony.Rlog.w(TAG, "Band and the range of UARFCN are not consistent: band = " + i + " ,uarfcn = " + i2 + " ,isUplink = " + z);
                return -1;
            }
        }
        if (!UARFCN_NOT_GENERAL_BAND.contains(java.lang.Integer.valueOf(i))) {
            return convertUarfcnToFrequency(i3, i2);
        }
        return convertUarfcnTddToFrequency(i, i2);
    }

    private static int convertUarfcnToFrequency(int i, int i2) {
        return i + (i2 * 200);
    }

    private static int convertUarfcnTddToFrequency(int i, int i2) {
        if (i != 104) {
            return i2 * 5 * 1000;
        }
        return ((i2 * 1000) - 2150100) * 5;
    }

    private static boolean isInUarfcnRange(int i, android.telephony.AccessNetworkConstants.UtranBandArfcnFrequency utranBandArfcnFrequency, boolean z) {
        if (z) {
            return i >= utranBandArfcnFrequency.uplinkRangeFirst && i <= utranBandArfcnFrequency.uplinkRangeLast;
        }
        if (utranBandArfcnFrequency.downlinkRangeFirst == 0 || utranBandArfcnFrequency.downlinkRangeLast == 0) {
            return true;
        }
        return i >= utranBandArfcnFrequency.downlinkRangeFirst && i <= utranBandArfcnFrequency.downlinkRangeLast;
    }

    public static int getFrequencyFromArfcn(int i, int i2, boolean z) {
        int i3;
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        android.telephony.AccessNetworkConstants.GeranBandArfcnFrequency[] values = android.telephony.AccessNetworkConstants.GeranBandArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i3 = 0;
                break;
            }
            android.telephony.AccessNetworkConstants.GeranBandArfcnFrequency geranBandArfcnFrequency = values[i5];
            if (i != geranBandArfcnFrequency.band) {
                i5++;
            } else if (i2 >= geranBandArfcnFrequency.arfcnRangeFirst && i2 <= geranBandArfcnFrequency.arfcnRangeLast) {
                int i6 = geranBandArfcnFrequency.uplinkFrequencyFirst;
                int i7 = geranBandArfcnFrequency.downlinkOffset;
                i4 = convertArfcnToFrequency(i2, i6, geranBandArfcnFrequency.arfcnOffset);
                i3 = i7;
            } else {
                android.telephony.Rlog.w(TAG, "Band and the range of ARFCN are not consistent: band = " + i + " ,arfcn = " + i2 + " ,isUplink = " + z);
                return -1;
            }
        }
        return z ? i4 : i4 + i3;
    }

    private static int convertArfcnToFrequency(int i, int i2, int i3) {
        return i2 + ((i - i3) * 200);
    }

    public static int getFrequencyRangeFromArfcn(int i) {
        if (i < 1000000) {
            return 1;
        }
        if (i < FREQUENCY_RANGE_MID_KHZ && i >= 1000000) {
            return 2;
        }
        if (i < FREQUENCY_RANGE_HIGH_KHZ && i >= FREQUENCY_RANGE_MID_KHZ) {
            return 3;
        }
        return 4;
    }
}
