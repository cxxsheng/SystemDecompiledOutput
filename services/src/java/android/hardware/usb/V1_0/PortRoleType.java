package android.hardware.usb.V1_0;

/* loaded from: classes.dex */
public final class PortRoleType {
    public static final int DATA_ROLE = 0;
    public static final int MODE = 2;
    public static final int POWER_ROLE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "DATA_ROLE";
        }
        if (i == 1) {
            return "POWER_ROLE";
        }
        if (i == 2) {
            return "MODE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("DATA_ROLE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("POWER_ROLE");
        }
        if ((i & 2) == 2) {
            arrayList.add("MODE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
