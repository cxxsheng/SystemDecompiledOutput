package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class LceStatus {
    public static final int ACTIVE = 2;
    public static final int NOT_SUPPORTED = 0;
    public static final int STOPPED = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NOT_SUPPORTED";
        }
        if (i == 1) {
            return "STOPPED";
        }
        if (i == 2) {
            return "ACTIVE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NOT_SUPPORTED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("STOPPED");
        }
        if ((i & 2) == 2) {
            arrayList.add("ACTIVE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
