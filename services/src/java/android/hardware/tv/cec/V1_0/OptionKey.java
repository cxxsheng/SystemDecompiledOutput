package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class OptionKey {
    public static final int ENABLE_CEC = 2;
    public static final int SYSTEM_CEC_CONTROL = 3;
    public static final int WAKEUP = 1;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "WAKEUP";
        }
        if (i == 2) {
            return "ENABLE_CEC";
        }
        if (i == 3) {
            return "SYSTEM_CEC_CONTROL";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("WAKEUP");
        }
        if ((i & 2) == 2) {
            arrayList.add("ENABLE_CEC");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SYSTEM_CEC_CONTROL");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
