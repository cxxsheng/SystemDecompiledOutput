package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioGainMode {
    public static final int CHANNELS = 2;
    public static final int JOINT = 1;
    public static final int RAMP = 4;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "JOINT";
        }
        if (i == 2) {
            return "CHANNELS";
        }
        if (i == 4) {
            return "RAMP";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("JOINT");
        }
        if ((i & 2) == 2) {
            arrayList.add("CHANNELS");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("RAMP");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
