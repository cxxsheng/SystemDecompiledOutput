package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public final class GnssMax {
    public static final int SVS_COUNT = 64;

    public static final java.lang.String toString(int i) {
        if (i == 64) {
            return "SVS_COUNT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 64;
        if ((i & 64) != 64) {
            i2 = 0;
        } else {
            arrayList.add("SVS_COUNT");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
