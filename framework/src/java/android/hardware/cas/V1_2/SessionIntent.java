package android.hardware.cas.V1_2;

/* loaded from: classes.dex */
public final class SessionIntent {
    public static final int LIVE = 0;
    public static final int PLAYBACK = 1;
    public static final int RECORD = 2;
    public static final int TIMESHIFT = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "LIVE";
        }
        if (i == 1) {
            return "PLAYBACK";
        }
        if (i == 2) {
            return "RECORD";
        }
        if (i == 3) {
            return "TIMESHIFT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LIVE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("PLAYBACK");
        }
        if ((i & 2) == 2) {
            arrayList.add("RECORD");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("TIMESHIFT");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
