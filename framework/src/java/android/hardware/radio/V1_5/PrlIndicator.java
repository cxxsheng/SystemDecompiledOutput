package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class PrlIndicator {
    public static final int IN_PRL = 1;
    public static final int NOT_IN_PRL = 0;
    public static final int NOT_REGISTERED = -1;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "NOT_REGISTERED";
        }
        if (i == 0) {
            return "NOT_IN_PRL";
        }
        if (i == 1) {
            return "IN_PRL";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("NOT_REGISTERED");
        }
        arrayList.add("NOT_IN_PRL");
        if ((i & 1) == 1) {
            arrayList.add("IN_PRL");
            i2 |= 1;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
