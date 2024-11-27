package android.hardware.usb.V1_2;

/* loaded from: classes.dex */
public final class ContaminantDetectionStatus {
    public static final int DETECTED = 3;
    public static final int DISABLED = 1;
    public static final int NOT_DETECTED = 2;
    public static final int NOT_SUPPORTED = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NOT_SUPPORTED";
        }
        if (i == 1) {
            return "DISABLED";
        }
        if (i == 2) {
            return "NOT_DETECTED";
        }
        if (i == 3) {
            return "DETECTED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NOT_SUPPORTED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("DISABLED");
        }
        if ((i & 2) == 2) {
            arrayList.add("NOT_DETECTED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("DETECTED");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
