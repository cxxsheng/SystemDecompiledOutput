package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class ScanStatus {
    public static final int COMPLETE = 2;
    public static final int PARTIAL = 1;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "PARTIAL";
        }
        if (i == 2) {
            return "COMPLETE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("PARTIAL");
        }
        if ((i & 2) == 2) {
            arrayList.add("COMPLETE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
