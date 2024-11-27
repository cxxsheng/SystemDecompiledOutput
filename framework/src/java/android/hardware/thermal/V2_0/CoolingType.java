package android.hardware.thermal.V2_0;

/* loaded from: classes2.dex */
public final class CoolingType {
    public static final int BATTERY = 1;
    public static final int COMPONENT = 6;
    public static final int CPU = 2;
    public static final int FAN = 0;
    public static final int GPU = 3;
    public static final int MODEM = 4;
    public static final int NPU = 5;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "FAN";
        }
        if (i == 1) {
            return "BATTERY";
        }
        if (i == 2) {
            return "CPU";
        }
        if (i == 3) {
            return "GPU";
        }
        if (i == 4) {
            return "MODEM";
        }
        if (i == 5) {
            return "NPU";
        }
        if (i == 6) {
            return "COMPONENT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("FAN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("BATTERY");
        }
        if ((i & 2) == 2) {
            arrayList.add("CPU");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("GPU");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("MODEM");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("NPU");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("COMPONENT");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
