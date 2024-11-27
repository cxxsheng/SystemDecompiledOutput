package android.hardware.usb.V1_0;

/* loaded from: classes.dex */
public final class PortPowerRole {
    public static final int NONE = 0;
    public static final int NUM_POWER_ROLES = 3;
    public static final int SINK = 2;
    public static final int SOURCE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NONE";
        }
        if (i == 1) {
            return "SOURCE";
        }
        if (i == 2) {
            return "SINK";
        }
        if (i == 3) {
            return "NUM_POWER_ROLES";
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
            arrayList.add("SOURCE");
        }
        if ((i & 2) == 2) {
            arrayList.add("SINK");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("NUM_POWER_ROLES");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
