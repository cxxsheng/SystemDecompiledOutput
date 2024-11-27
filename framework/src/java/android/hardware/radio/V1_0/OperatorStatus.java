package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class OperatorStatus {
    public static final int AVAILABLE = 1;
    public static final int CURRENT = 2;
    public static final int FORBIDDEN = 3;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "AVAILABLE";
        }
        if (i == 2) {
            return "CURRENT";
        }
        if (i == 3) {
            return "FORBIDDEN";
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
            arrayList.add("AVAILABLE");
        }
        if ((i & 2) == 2) {
            arrayList.add("CURRENT");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("FORBIDDEN");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
