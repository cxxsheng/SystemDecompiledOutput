package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class MaxSearchTimeRange {
    public static final int MAX = 3600;
    public static final int MIN = 60;

    public static final java.lang.String toString(int i) {
        if (i == 60) {
            return "MIN";
        }
        if (i == 3600) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 60;
        if ((i & 60) != 60) {
            i2 = 0;
        } else {
            arrayList.add("MIN");
        }
        if ((i & 3600) == 3600) {
            arrayList.add("MAX");
            i2 |= 3600;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
