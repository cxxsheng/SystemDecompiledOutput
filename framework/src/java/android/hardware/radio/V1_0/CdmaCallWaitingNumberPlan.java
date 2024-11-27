package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaCallWaitingNumberPlan {
    public static final int DATA = 3;
    public static final int ISDN = 1;
    public static final int NATIONAL = 8;
    public static final int PRIVATE = 9;
    public static final int TELEX = 4;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "ISDN";
        }
        if (i == 3) {
            return "DATA";
        }
        if (i == 4) {
            return "TELEX";
        }
        if (i == 8) {
            return "NATIONAL";
        }
        if (i == 9) {
            return "PRIVATE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ISDN");
        }
        if ((i & 3) == 3) {
            arrayList.add("DATA");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("TELEX");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("NATIONAL");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("PRIVATE");
            i2 |= 9;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
