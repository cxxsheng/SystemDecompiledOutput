package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class Result {
    public static final int INTERNAL_ERROR = 2;
    public static final int INVALID_ARGUMENTS = 3;
    public static final int INVALID_STATE = 4;
    public static final int NOT_SUPPORTED = 5;
    public static final int OK = 0;
    public static final int TIMEOUT = 6;
    public static final int UNKNOWN_ERROR = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "UNKNOWN_ERROR";
        }
        if (i == 2) {
            return "INTERNAL_ERROR";
        }
        if (i == 3) {
            return "INVALID_ARGUMENTS";
        }
        if (i == 4) {
            return "INVALID_STATE";
        }
        if (i == 5) {
            return "NOT_SUPPORTED";
        }
        if (i == 6) {
            return "TIMEOUT";
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
            arrayList.add("INTERNAL_ERROR");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("INVALID_ARGUMENTS");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("INVALID_STATE");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("NOT_SUPPORTED");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("TIMEOUT");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
