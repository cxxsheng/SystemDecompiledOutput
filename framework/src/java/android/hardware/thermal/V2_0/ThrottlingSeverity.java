package android.hardware.thermal.V2_0;

/* loaded from: classes2.dex */
public final class ThrottlingSeverity {
    public static final int CRITICAL = 4;
    public static final int EMERGENCY = 5;
    public static final int LIGHT = 1;
    public static final int MODERATE = 2;
    public static final int NONE = 0;
    public static final int SEVERE = 3;
    public static final int SHUTDOWN = 6;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "LIGHT";
        }
        if (i == 2) {
            return "MODERATE";
        }
        if (i == 3) {
            return "SEVERE";
        }
        if (i == 4) {
            return "CRITICAL";
        }
        if (i == 5) {
            return "EMERGENCY";
        }
        if (i == 6) {
            return "SHUTDOWN";
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
            arrayList.add("LIGHT");
        }
        if ((i & 2) == 2) {
            arrayList.add("MODERATE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SEVERE");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CRITICAL");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("EMERGENCY");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("SHUTDOWN");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
