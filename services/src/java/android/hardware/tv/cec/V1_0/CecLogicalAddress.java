package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class CecLogicalAddress {
    public static final int AUDIO_SYSTEM = 5;
    public static final int BROADCAST = 15;
    public static final int FREE_USE = 14;
    public static final int PLAYBACK_1 = 4;
    public static final int PLAYBACK_2 = 8;
    public static final int PLAYBACK_3 = 11;
    public static final int RECORDER_1 = 1;
    public static final int RECORDER_2 = 2;
    public static final int RECORDER_3 = 9;
    public static final int TUNER_1 = 3;
    public static final int TUNER_2 = 6;
    public static final int TUNER_3 = 7;
    public static final int TUNER_4 = 10;
    public static final int TV = 0;
    public static final int UNREGISTERED = 15;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "TV";
        }
        if (i == 1) {
            return "RECORDER_1";
        }
        if (i == 2) {
            return "RECORDER_2";
        }
        if (i == 3) {
            return "TUNER_1";
        }
        if (i == 4) {
            return "PLAYBACK_1";
        }
        if (i == 5) {
            return "AUDIO_SYSTEM";
        }
        if (i == 6) {
            return "TUNER_2";
        }
        if (i == 7) {
            return "TUNER_3";
        }
        if (i == 8) {
            return "PLAYBACK_2";
        }
        if (i == 9) {
            return "RECORDER_3";
        }
        if (i == 10) {
            return "TUNER_4";
        }
        if (i == 11) {
            return "PLAYBACK_3";
        }
        if (i == 14) {
            return "FREE_USE";
        }
        if (i == 15) {
            return "UNREGISTERED";
        }
        if (i == 15) {
            return "BROADCAST";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("TV");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("RECORDER_1");
        }
        if ((i & 2) == 2) {
            arrayList.add("RECORDER_2");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("TUNER_1");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("PLAYBACK_1");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("AUDIO_SYSTEM");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("TUNER_2");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("TUNER_3");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("PLAYBACK_2");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("RECORDER_3");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("TUNER_4");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("PLAYBACK_3");
            i2 |= 11;
        }
        if ((i & 14) == 14) {
            arrayList.add("FREE_USE");
            i2 |= 14;
        }
        int i3 = i & 15;
        int i4 = 15;
        if (i3 == 15) {
            arrayList.add("UNREGISTERED");
            i2 = 15;
        }
        if (i3 != 15) {
            i4 = i2;
        } else {
            arrayList.add("BROADCAST");
        }
        if (i != i4) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i4)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
