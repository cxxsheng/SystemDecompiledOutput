package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class SimLockMultiSimPolicy {
    public static final int NO_MULTISIM_POLICY = 0;
    public static final int ONE_VALID_SIM_MUST_BE_PRESENT = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NO_MULTISIM_POLICY";
        }
        if (i == 1) {
            return "ONE_VALID_SIM_MUST_BE_PRESENT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NO_MULTISIM_POLICY");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ONE_VALID_SIM_MUST_BE_PRESENT");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
