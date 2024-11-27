package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class IndicationFilter {
    public static final int ALL = 7;
    public static final int DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int FULL_NETWORK_STATE = 2;
    public static final int NONE = 0;
    public static final int SIGNAL_STRENGTH = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "SIGNAL_STRENGTH";
        }
        if (i == 2) {
            return "FULL_NETWORK_STATE";
        }
        if (i == 4) {
            return "DATA_CALL_DORMANCY_CHANGED";
        }
        if (i == 7) {
            return "ALL";
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
            arrayList.add("SIGNAL_STRENGTH");
        }
        if ((i & 2) == 2) {
            arrayList.add("FULL_NETWORK_STATE");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("DATA_CALL_DORMANCY_CHANGED");
            i2 |= 4;
        }
        if ((i & 7) == 7) {
            arrayList.add("ALL");
            i2 = 7;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
