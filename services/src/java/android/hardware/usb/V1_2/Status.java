package android.hardware.usb.V1_2;

/* loaded from: classes.dex */
public final class Status {
    public static final int ERROR = 1;
    public static final int INVALID_ARGUMENT = 2;
    public static final int NOT_SUPPORTED = 4;
    public static final int SUCCESS = 0;
    public static final int UNRECOGNIZED_ROLE = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "SUCCESS";
        }
        if (i == 1) {
            return "ERROR";
        }
        if (i == 2) {
            return "INVALID_ARGUMENT";
        }
        if (i == 3) {
            return "UNRECOGNIZED_ROLE";
        }
        if (i == 4) {
            return "NOT_SUPPORTED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("SUCCESS");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ERROR");
        }
        if ((i & 2) == 2) {
            arrayList.add("INVALID_ARGUMENT");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("UNRECOGNIZED_ROLE");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("NOT_SUPPORTED");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
