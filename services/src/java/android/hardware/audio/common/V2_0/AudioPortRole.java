package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioPortRole {
    public static final int NONE = 0;
    public static final int SINK = 2;
    public static final int SOURCE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NONE";
        }
        if (i == 1) {
            return "SOURCE";
        }
        if (i == 2) {
            return "SINK";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NONE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SOURCE");
        }
        if ((i & 2) == 2) {
            arrayList.add("SINK");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
