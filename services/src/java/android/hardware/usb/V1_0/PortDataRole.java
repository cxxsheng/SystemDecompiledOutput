package android.hardware.usb.V1_0;

/* loaded from: classes.dex */
public final class PortDataRole {
    public static final int DEVICE = 2;
    public static final int HOST = 1;
    public static final int NONE = 0;
    public static final int NUM_DATA_ROLES = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NONE";
        }
        if (i == 1) {
            return "HOST";
        }
        if (i == 2) {
            return "DEVICE";
        }
        if (i == 3) {
            return "NUM_DATA_ROLES";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NONE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("HOST");
        }
        if ((i & 2) == 2) {
            arrayList.add("DEVICE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("NUM_DATA_ROLES");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
