package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class PinState {
    public static final int DISABLED = 3;
    public static final int ENABLED_BLOCKED = 4;
    public static final int ENABLED_NOT_VERIFIED = 1;
    public static final int ENABLED_PERM_BLOCKED = 5;
    public static final int ENABLED_VERIFIED = 2;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "ENABLED_NOT_VERIFIED";
        }
        if (i == 2) {
            return "ENABLED_VERIFIED";
        }
        if (i == 3) {
            return "DISABLED";
        }
        if (i == 4) {
            return "ENABLED_BLOCKED";
        }
        if (i == 5) {
            return "ENABLED_PERM_BLOCKED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ENABLED_NOT_VERIFIED");
        }
        if ((i & 2) == 2) {
            arrayList.add("ENABLED_VERIFIED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("DISABLED");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("ENABLED_BLOCKED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("ENABLED_PERM_BLOCKED");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
