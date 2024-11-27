package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class HardwareConfigType {
    public static final int MODEM = 0;
    public static final int SIM = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "MODEM";
        }
        if (i == 1) {
            return "SIM";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("MODEM");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SIM");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
