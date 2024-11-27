package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioInterleave {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "LEFT";
        }
        if (i == 1) {
            return "RIGHT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LEFT");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("RIGHT");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
