package android.hardware.soundtrigger.V2_3;

/* loaded from: classes.dex */
public final class AudioCapabilities {
    public static final int ECHO_CANCELLATION = 1;
    public static final int NOISE_SUPPRESSION = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "ECHO_CANCELLATION";
        }
        if (i == 2) {
            return "NOISE_SUPPRESSION";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ECHO_CANCELLATION");
        }
        if ((i & 2) == 2) {
            arrayList.add("NOISE_SUPPRESSION");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
