package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class ApnTypes {
    public static final int ALL = 1023;
    public static final int CBS = 128;
    public static final int DEFAULT = 1;
    public static final int DUN = 8;
    public static final int EMERGENCY = 512;
    public static final int FOTA = 32;
    public static final int HIPRI = 16;
    public static final int IA = 256;
    public static final int IMS = 64;
    public static final int MCX = 1024;
    public static final int MMS = 2;
    public static final int NONE = 0;
    public static final int SUPL = 4;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "DEFAULT";
        }
        if (i == 2) {
            return "MMS";
        }
        if (i == 4) {
            return "SUPL";
        }
        if (i == 8) {
            return "DUN";
        }
        if (i == 16) {
            return "HIPRI";
        }
        if (i == 32) {
            return "FOTA";
        }
        if (i == 64) {
            return "IMS";
        }
        if (i == 128) {
            return "CBS";
        }
        if (i == 256) {
            return "IA";
        }
        if (i == 512) {
            return "EMERGENCY";
        }
        if (i == 1023) {
            return "ALL";
        }
        if (i == 1024) {
            return "MCX";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("DEFAULT");
        }
        if ((i & 2) == 2) {
            arrayList.add("MMS");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("SUPL");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("DUN");
            i2 |= 8;
        }
        if ((i & 16) == 16) {
            arrayList.add("HIPRI");
            i2 |= 16;
        }
        if ((i & 32) == 32) {
            arrayList.add("FOTA");
            i2 |= 32;
        }
        if ((i & 64) == 64) {
            arrayList.add("IMS");
            i2 |= 64;
        }
        if ((i & 128) == 128) {
            arrayList.add("CBS");
            i2 |= 128;
        }
        if ((i & 256) == 256) {
            arrayList.add("IA");
            i2 |= 256;
        }
        if ((i & 512) == 512) {
            arrayList.add("EMERGENCY");
            i2 |= 512;
        }
        if ((i & 1023) == 1023) {
            arrayList.add("ALL");
            i2 = 1023;
        }
        if ((i & 1024) == 1024) {
            arrayList.add("MCX");
            i2 |= 1024;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
