package android.hardware.vibrator.V1_0;

/* loaded from: classes2.dex */
public final class Status {
    public static final int BAD_VALUE = 2;
    public static final int OK = 0;
    public static final int UNKNOWN_ERROR = 1;
    public static final int UNSUPPORTED_OPERATION = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "UNKNOWN_ERROR";
        }
        if (i == 2) {
            return "BAD_VALUE";
        }
        if (i == 3) {
            return "UNSUPPORTED_OPERATION";
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
            arrayList.add("UNKNOWN_ERROR");
        }
        if ((i & 2) == 2) {
            arrayList.add("BAD_VALUE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("UNSUPPORTED_OPERATION");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
