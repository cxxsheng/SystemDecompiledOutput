package android.hardware.thermal.V1_0;

/* loaded from: classes2.dex */
public final class CoolingType {
    public static final int FAN_RPM = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "FAN_RPM";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("FAN_RPM");
        if (i != 0) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (-1)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
