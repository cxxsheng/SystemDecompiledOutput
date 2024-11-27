package android.hardware.weaver.V1_0;

/* loaded from: classes.dex */
public final class WeaverReadStatus {
    public static final int FAILED = 1;
    public static final int INCORRECT_KEY = 2;
    public static final int OK = 0;
    public static final int THROTTLE = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "FAILED";
        }
        if (i == 2) {
            return "INCORRECT_KEY";
        }
        if (i == 3) {
            return "THROTTLE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("OK");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("FAILED");
        }
        if ((i & 2) == 2) {
            arrayList.add("INCORRECT_KEY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("THROTTLE");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
