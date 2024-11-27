package android.telephony;

/* loaded from: classes3.dex */
public class RadioAccessFamily implements android.os.Parcelable {
    private static final int CDMA = 72;
    public static final android.os.Parcelable.Creator<android.telephony.RadioAccessFamily> CREATOR = new android.os.Parcelable.Creator<android.telephony.RadioAccessFamily>() { // from class: android.telephony.RadioAccessFamily.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.RadioAccessFamily createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.RadioAccessFamily(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.RadioAccessFamily[] newArray(int i) {
            return new android.telephony.RadioAccessFamily[i];
        }
    };
    private static final int EVDO = 10288;
    private static final int GSM = 32771;
    private static final int HS = 17280;
    private static final int LTE = 266240;
    private static final int NR = 524288;
    public static final int RAF_1xRTT = 64;
    public static final int RAF_EDGE = 2;
    public static final int RAF_EHRPD = 8192;
    public static final int RAF_EVDO_0 = 16;
    public static final int RAF_EVDO_A = 32;
    public static final int RAF_EVDO_B = 2048;
    public static final int RAF_GPRS = 1;
    public static final int RAF_GSM = 32768;
    public static final int RAF_HSDPA = 128;
    public static final int RAF_HSPA = 512;
    public static final int RAF_HSPAP = 16384;
    public static final int RAF_HSUPA = 256;
    public static final int RAF_IS95A = 8;
    public static final int RAF_IS95B = 8;
    public static final int RAF_LTE = 4096;
    public static final int RAF_LTE_CA = 262144;
    public static final int RAF_NR = 524288;
    public static final int RAF_TD_SCDMA = 65536;
    public static final int RAF_UMTS = 4;
    public static final int RAF_UNKNOWN = 0;
    private static final int WCDMA = 17284;
    private int mPhoneId;
    private int mRadioAccessFamily;

    public RadioAccessFamily(int i, int i2) {
        this.mPhoneId = i;
        this.mRadioAccessFamily = i2;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public int getRadioAccessFamily() {
        return this.mRadioAccessFamily;
    }

    public java.lang.String toString() {
        return "{ mPhoneId = " + this.mPhoneId + ", mRadioAccessFamily = " + this.mRadioAccessFamily + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPhoneId);
        parcel.writeInt(this.mRadioAccessFamily);
    }

    public static int getRafFromNetworkType(int i) {
        switch (i) {
        }
        return 50055;
    }

    private static int getAdjustedRaf(int i) {
        if ((i & 32771) > 0) {
            i |= 32771;
        }
        if ((i & WCDMA) > 0) {
            i |= WCDMA;
        }
        if ((i & 72) > 0) {
            i |= 72;
        }
        if ((i & EVDO) > 0) {
            i |= EVDO;
        }
        if ((i & 266240) > 0) {
            i |= 266240;
        }
        if ((i & 524288) > 0) {
            return i | 524288;
        }
        return i;
    }

    public static int getNetworkTypeFromRaf(int i) {
        switch (getAdjustedRaf(i)) {
            case 72:
                return 5;
            case EVDO /* 10288 */:
                return 6;
            case 10360:
                return 4;
            case WCDMA /* 17284 */:
                return 2;
            case 32771:
                return 1;
            case 50055:
                return 0;
            case 60415:
                return 7;
            case 65536:
                return 13;
            case 82820:
                return 14;
            case 98307:
                return 16;
            case 115591:
                return 18;
            case 125951:
                return 21;
            case 266240:
                return 11;
            case 276600:
                return 8;
            case 283524:
                return 12;
            case 316295:
                return 9;
            case 326655:
                return 10;
            case 331776:
                return 15;
            case 349060:
                return 19;
            case 364547:
                return 17;
            case 381831:
                return 20;
            case 392191:
                return 22;
            case 524288:
                return 23;
            case 790528:
                return 24;
            case 800888:
                return 25;
            case 807812:
                return 28;
            case 840583:
                return 26;
            case 850943:
                return 27;
            case 856064:
                return 29;
            case 873348:
                return 31;
            case 888835:
                return 30;
            case 906119:
                return 32;
            case 916479:
                return 33;
            default:
                return com.android.internal.telephony.RILConstants.PREFERRED_NETWORK_MODE;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int singleRafTypeFromString(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2039427040:
                if (str.equals("LTE_CA")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -908593671:
                if (str.equals("TD_SCDMA")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 2315:
                if (str.equals("HS")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 2500:
                if (str.equals("NR")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 70881:
                if (str.equals("GSM")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 75709:
                if (str.equals(com.android.internal.telephony.DctConstants.RAT_NAME_LTE)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 2063797:
                if (str.equals("CDMA")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 2123197:
                if (str.equals("EDGE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2140412:
                if (str.equals("EVDO")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 2194666:
                if (str.equals("GPRS")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2227260:
                if (str.equals("HSPA")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 2608919:
                if (str.equals("UMTS")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 47955627:
                if (str.equals("1XRTT")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 65949251:
                if (str.equals("EHRPD")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 69034058:
                if (str.equals("HSDPA")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 69045140:
                if (str.equals("HSPAP")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 69050395:
                if (str.equals("HSUPA")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 69946171:
                if (str.equals("IS95A")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 69946172:
                if (str.equals("IS95B")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 82410124:
                if (str.equals("WCDMA")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 2056938925:
                if (str.equals("EVDO_0")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2056938942:
                if (str.equals("EVDO_A")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2056938943:
                if (str.equals("EVDO_B")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 8;
            case 5:
                return 64;
            case 6:
                return 16;
            case 7:
                return 32;
            case '\b':
                return 128;
            case '\t':
                return 256;
            case '\n':
                return 512;
            case 11:
                return 2048;
            case '\f':
                return 8192;
            case '\r':
                return 4096;
            case 14:
                return 16384;
            case 15:
                return 32768;
            case 16:
                return 65536;
            case 17:
                return HS;
            case 18:
                return 72;
            case 19:
                return EVDO;
            case 20:
                return WCDMA;
            case 21:
                return 262144;
            case 22:
                return 524288;
            default:
                return 0;
        }
    }

    public static int rafTypeFromString(java.lang.String str) {
        int i = 0;
        for (java.lang.String str2 : str.toUpperCase(java.util.Locale.ROOT).split("\\|")) {
            int singleRafTypeFromString = singleRafTypeFromString(str2.trim());
            if (singleRafTypeFromString == 0) {
                return singleRafTypeFromString;
            }
            i |= singleRafTypeFromString;
        }
        return i;
    }

    public static int compare(long j, long j2) {
        long[] jArr = {524288, android.telephony.TelephonyManager.NETWORK_CLASS_BITMASK_4G, android.telephony.TelephonyManager.NETWORK_CLASS_BITMASK_3G, android.telephony.TelephonyManager.NETWORK_CLASS_BITMASK_2G};
        long j3 = (~j2) & j;
        long j4 = (~j) & j2;
        for (int i = 0; i < 4; i++) {
            long j5 = jArr[i];
            int i2 = (j3 & j5) != 0 ? 1 : 0;
            if ((j5 & j4) != 0) {
                i2--;
            }
            if (i2 != 0) {
                return i2;
            }
        }
        return java.lang.Long.bitCount(j) - java.lang.Long.bitCount(j2);
    }
}
