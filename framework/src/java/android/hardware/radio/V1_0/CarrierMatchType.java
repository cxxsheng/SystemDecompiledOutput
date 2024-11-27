package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CarrierMatchType {
    public static final int ALL = 0;
    public static final int GID1 = 3;
    public static final int GID2 = 4;
    public static final int IMSI_PREFIX = 2;
    public static final int SPN = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "ALL";
        }
        if (i == 1) {
            return "SPN";
        }
        if (i == 2) {
            return "IMSI_PREFIX";
        }
        if (i == 3) {
            return "GID1";
        }
        if (i == 4) {
            return "GID2";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ALL");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SPN");
        }
        if ((i & 2) == 2) {
            arrayList.add("IMSI_PREFIX");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("GID1");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("GID2");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
