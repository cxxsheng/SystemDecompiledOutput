package android.hardware.health.V2_0;

/* loaded from: classes.dex */
public final class Result {
    public static final int CALLBACK_DIED = 4;
    public static final int NOT_FOUND = 3;
    public static final int NOT_SUPPORTED = 1;
    public static final int SUCCESS = 0;
    public static final int UNKNOWN = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "SUCCESS";
        }
        if (i == 1) {
            return "NOT_SUPPORTED";
        }
        if (i == 2) {
            return "UNKNOWN";
        }
        if (i == 3) {
            return "NOT_FOUND";
        }
        if (i == 4) {
            return "CALLBACK_DIED";
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
            arrayList.add("NOT_SUPPORTED");
        }
        if ((i & 2) == 2) {
            arrayList.add("UNKNOWN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("NOT_FOUND");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CALLBACK_DIED");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
