package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class RadioResponseType {
    public static final int SOLICITED = 0;
    public static final int SOLICITED_ACK = 1;
    public static final int SOLICITED_ACK_EXP = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "SOLICITED";
        }
        if (i == 1) {
            return "SOLICITED_ACK";
        }
        if (i == 2) {
            return "SOLICITED_ACK_EXP";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("SOLICITED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SOLICITED_ACK");
        }
        if ((i & 2) == 2) {
            arrayList.add("SOLICITED_ACK_EXP");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
