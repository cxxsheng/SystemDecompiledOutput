package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioPortConfigMask {
    public static final int ALL = 15;
    public static final int CHANNEL_MASK = 2;
    public static final int FORMAT = 4;
    public static final int GAIN = 8;
    public static final int SAMPLE_RATE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "SAMPLE_RATE";
        }
        if (i == 2) {
            return "CHANNEL_MASK";
        }
        if (i == 4) {
            return "FORMAT";
        }
        if (i == 8) {
            return "GAIN";
        }
        if (i == 15) {
            return "ALL";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SAMPLE_RATE");
        }
        if ((i & 2) == 2) {
            arrayList.add("CHANNEL_MASK");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("FORMAT");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("GAIN");
            i2 |= 8;
        }
        if ((i & 15) == 15) {
            arrayList.add("ALL");
            i2 = 15;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
