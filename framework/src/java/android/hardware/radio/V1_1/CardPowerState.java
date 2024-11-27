package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class CardPowerState {
    public static final int POWER_DOWN = 0;
    public static final int POWER_UP = 1;
    public static final int POWER_UP_PASS_THROUGH = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "POWER_DOWN";
        }
        if (i == 1) {
            return "POWER_UP";
        }
        if (i == 2) {
            return "POWER_UP_PASS_THROUGH";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("POWER_DOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("POWER_UP");
        }
        if ((i & 2) == 2) {
            arrayList.add("POWER_UP_PASS_THROUGH");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
