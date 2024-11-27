package android.hardware.biometrics.fingerprint.V2_1;

/* loaded from: classes.dex */
public final class FingerprintMsgType {
    public static final int ACQUIRED = 1;
    public static final int AUTHENTICATED = 5;
    public static final int ERROR = -1;
    public static final int TEMPLATE_ENROLLING = 3;
    public static final int TEMPLATE_ENUMERATING = 6;
    public static final int TEMPLATE_REMOVED = 4;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "ERROR";
        }
        if (i == 1) {
            return "ACQUIRED";
        }
        if (i == 3) {
            return "TEMPLATE_ENROLLING";
        }
        if (i == 4) {
            return "TEMPLATE_REMOVED";
        }
        if (i == 5) {
            return "AUTHENTICATED";
        }
        if (i == 6) {
            return "TEMPLATE_ENUMERATING";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("ERROR");
        }
        if ((i & 1) == 1) {
            arrayList.add("ACQUIRED");
            i2 |= 1;
        }
        if ((i & 3) == 3) {
            arrayList.add("TEMPLATE_ENROLLING");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("TEMPLATE_REMOVED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("AUTHENTICATED");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("TEMPLATE_ENUMERATING");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
