package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class TtyMode {
    public static final int FULL = 1;
    public static final int HCO = 2;
    public static final int OFF = 0;
    public static final int VCO = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OFF";
        }
        if (i == 1) {
            return "FULL";
        }
        if (i == 2) {
            return "HCO";
        }
        if (i == 3) {
            return "VCO";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("OFF");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("FULL");
        }
        if ((i & 2) == 2) {
            arrayList.add("HCO");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("VCO");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
