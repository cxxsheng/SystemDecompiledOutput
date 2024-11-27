package android.hardware.soundtrigger.V2_3;

/* loaded from: classes.dex */
public final class ModelParameter {
    public static final int INVALID = -1;
    public static final int THRESHOLD_FACTOR = 0;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "INVALID";
        }
        if (i == 0) {
            return "THRESHOLD_FACTOR";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("INVALID");
        }
        arrayList.add("THRESHOLD_FACTOR");
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
