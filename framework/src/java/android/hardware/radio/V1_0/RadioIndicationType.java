package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class RadioIndicationType {
    public static final int UNSOLICITED = 0;
    public static final int UNSOLICITED_ACK_EXP = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNSOLICITED";
        }
        if (i == 1) {
            return "UNSOLICITED_ACK_EXP";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNSOLICITED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("UNSOLICITED_ACK_EXP");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
