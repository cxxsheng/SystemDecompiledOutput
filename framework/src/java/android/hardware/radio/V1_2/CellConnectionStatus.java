package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class CellConnectionStatus {
    public static final int NONE = 0;
    public static final int PRIMARY_SERVING = 1;
    public static final int SECONDARY_SERVING = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "PRIMARY_SERVING";
        }
        if (i == 2) {
            return "SECONDARY_SERVING";
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
            arrayList.add("PRIMARY_SERVING");
        }
        if ((i & 2) == 2) {
            arrayList.add("SECONDARY_SERVING");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
