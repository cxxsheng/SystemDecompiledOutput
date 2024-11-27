package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioSessionConsts {
    public static final int ALLOCATE = 0;
    public static final int NONE = 0;
    public static final int OUTPUT_MIX = 0;
    public static final int OUTPUT_STAGE = -1;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "OUTPUT_STAGE";
        }
        if (i == 0) {
            return "OUTPUT_MIX";
        }
        if (i == 0) {
            return "ALLOCATE";
        }
        if (i == 0) {
            return "NONE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("OUTPUT_STAGE");
        }
        arrayList.add("OUTPUT_MIX");
        arrayList.add("ALLOCATE");
        arrayList.add("NONE");
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
