package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class DeviceStateType {
    public static final int CHARGING_STATE = 1;
    public static final int LOW_DATA_EXPECTED = 2;
    public static final int POWER_SAVE_MODE = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "POWER_SAVE_MODE";
        }
        if (i == 1) {
            return "CHARGING_STATE";
        }
        if (i == 2) {
            return "LOW_DATA_EXPECTED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("POWER_SAVE_MODE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("CHARGING_STATE");
        }
        if ((i & 2) == 2) {
            arrayList.add("LOW_DATA_EXPECTED");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
