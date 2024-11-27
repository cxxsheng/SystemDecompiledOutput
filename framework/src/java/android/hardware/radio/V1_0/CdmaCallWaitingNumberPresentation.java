package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaCallWaitingNumberPresentation {
    public static final int ALLOWED = 0;
    public static final int RESTRICTED = 1;
    public static final int UNKNOWN = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "ALLOWED";
        }
        if (i == 1) {
            return "RESTRICTED";
        }
        if (i == 2) {
            return "UNKNOWN";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ALLOWED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("RESTRICTED");
        }
        if ((i & 2) == 2) {
            arrayList.add("UNKNOWN");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}