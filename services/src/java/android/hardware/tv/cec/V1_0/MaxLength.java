package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class MaxLength {
    public static final int MESSAGE_BODY = 15;

    public static final java.lang.String toString(int i) {
        if (i == 15) {
            return "MESSAGE_BODY";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 15;
        if ((i & 15) != 15) {
            i2 = 0;
        } else {
            arrayList.add("MESSAGE_BODY");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
