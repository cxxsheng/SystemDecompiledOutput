package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class ScanIntervalRange {
    public static final int MAX = 300;
    public static final int MIN = 5;

    public static final java.lang.String toString(int i) {
        if (i == 5) {
            return "MIN";
        }
        if (i == 300) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 5;
        if ((i & 5) != 5) {
            i2 = 0;
        } else {
            arrayList.add("MIN");
        }
        if ((i & 300) == 300) {
            arrayList.add("MAX");
            i2 |= 300;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
