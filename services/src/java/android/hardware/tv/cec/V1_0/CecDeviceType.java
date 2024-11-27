package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class CecDeviceType {
    public static final int AUDIO_SYSTEM = 5;
    public static final int INACTIVE = -1;
    public static final int MAX = 5;
    public static final int PLAYBACK = 4;
    public static final int RECORDER = 1;
    public static final int TUNER = 3;
    public static final int TV = 0;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "INACTIVE";
        }
        if (i == 0) {
            return "TV";
        }
        if (i == 1) {
            return "RECORDER";
        }
        if (i == 3) {
            return "TUNER";
        }
        if (i == 4) {
            return "PLAYBACK";
        }
        if (i == 5) {
            return "AUDIO_SYSTEM";
        }
        if (i == 5) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("INACTIVE");
        }
        arrayList.add("TV");
        if ((i & 1) == 1) {
            arrayList.add("RECORDER");
            i2 |= 1;
        }
        if ((i & 3) == 3) {
            arrayList.add("TUNER");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("PLAYBACK");
            i2 |= 4;
        }
        int i3 = i & 5;
        if (i3 == 5) {
            arrayList.add("AUDIO_SYSTEM");
            i2 |= 5;
        }
        if (i3 == 5) {
            arrayList.add("MAX");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
