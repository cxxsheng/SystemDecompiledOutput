package android.hardware.soundtrigger.V2_0;

/* loaded from: classes.dex */
public final class RecognitionMode {
    public static final int GENERIC_TRIGGER = 8;
    public static final int USER_AUTHENTICATION = 4;
    public static final int USER_IDENTIFICATION = 2;
    public static final int VOICE_TRIGGER = 1;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "VOICE_TRIGGER";
        }
        if (i == 2) {
            return "USER_IDENTIFICATION";
        }
        if (i == 4) {
            return "USER_AUTHENTICATION";
        }
        if (i == 8) {
            return "GENERIC_TRIGGER";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("VOICE_TRIGGER");
        }
        if ((i & 2) == 2) {
            arrayList.add("USER_IDENTIFICATION");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("USER_AUTHENTICATION");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("GENERIC_TRIGGER");
            i2 |= 8;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
