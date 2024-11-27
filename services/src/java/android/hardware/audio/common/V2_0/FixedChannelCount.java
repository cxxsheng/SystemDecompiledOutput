package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class FixedChannelCount {
    public static final int FCC_2 = 2;
    public static final int FCC_8 = 8;

    public static final java.lang.String toString(int i) {
        if (i == 2) {
            return "FCC_2";
        }
        if (i == 8) {
            return "FCC_8";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 2;
        if ((i & 2) != 2) {
            i2 = 0;
        } else {
            arrayList.add("FCC_2");
        }
        if ((i & 8) == 8) {
            arrayList.add("FCC_8");
            i2 |= 8;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
