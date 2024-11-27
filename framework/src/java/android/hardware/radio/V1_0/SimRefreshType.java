package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SimRefreshType {
    public static final int SIM_FILE_UPDATE = 0;
    public static final int SIM_INIT = 1;
    public static final int SIM_RESET = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "SIM_FILE_UPDATE";
        }
        if (i == 1) {
            return "SIM_INIT";
        }
        if (i == 2) {
            return "SIM_RESET";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("SIM_FILE_UPDATE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SIM_INIT");
        }
        if ((i & 2) == 2) {
            arrayList.add("SIM_RESET");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
