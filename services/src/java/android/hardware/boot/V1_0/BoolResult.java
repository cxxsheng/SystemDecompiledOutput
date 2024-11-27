package android.hardware.boot.V1_0;

/* loaded from: classes.dex */
public final class BoolResult {
    public static final int FALSE = 0;
    public static final int INVALID_SLOT = -1;
    public static final int TRUE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "FALSE";
        }
        if (i == 1) {
            return "TRUE";
        }
        if (i == -1) {
            return "INVALID_SLOT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("FALSE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("TRUE");
        }
        if ((i & (-1)) == -1) {
            arrayList.add("INVALID_SLOT");
            i2 = -1;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
