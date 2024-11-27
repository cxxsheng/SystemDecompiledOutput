package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class IncrementalResultsPeriodicityRange {
    public static final int MAX = 10;
    public static final int MIN = 1;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "MIN";
        }
        if (i == 10) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("MIN");
        }
        if ((i & 10) == 10) {
            arrayList.add("MAX");
            i2 |= 10;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
