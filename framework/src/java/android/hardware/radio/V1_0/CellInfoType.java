package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CellInfoType {
    public static final int CDMA = 2;
    public static final int GSM = 1;
    public static final int LTE = 3;
    public static final int NONE = 0;
    public static final int TD_SCDMA = 5;
    public static final int WCDMA = 4;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "GSM";
        }
        if (i == 2) {
            return "CDMA";
        }
        if (i == 3) {
            return com.android.internal.telephony.DctConstants.RAT_NAME_LTE;
        }
        if (i == 4) {
            return "WCDMA";
        }
        if (i == 5) {
            return "TD_SCDMA";
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
            arrayList.add("GSM");
        }
        if ((i & 2) == 2) {
            arrayList.add("CDMA");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add(com.android.internal.telephony.DctConstants.RAT_NAME_LTE);
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("WCDMA");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("TD_SCDMA");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
