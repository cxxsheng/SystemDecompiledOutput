package android.hardware.soundtrigger.V2_0;

/* loaded from: classes.dex */
public final class SoundModelType {
    public static final int GENERIC = 1;
    public static final int KEYPHRASE = 0;
    public static final int UNKNOWN = -1;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "KEYPHRASE";
        }
        if (i == 1) {
            return "GENERIC";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("UNKNOWN");
        }
        arrayList.add("KEYPHRASE");
        if ((i & 1) == 1) {
            arrayList.add("GENERIC");
            i2 |= 1;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
