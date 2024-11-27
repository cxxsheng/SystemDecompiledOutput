package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class RadioState {
    public static final int OFF = 0;
    public static final int ON = 10;
    public static final int UNAVAILABLE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OFF";
        }
        if (i == 1) {
            return "UNAVAILABLE";
        }
        if (i == 10) {
            return "ON";
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
            arrayList.add("UNAVAILABLE");
        }
        if ((i & 10) == 10) {
            arrayList.add("ON");
            i2 |= 10;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}