package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class DataProfileInfoType {
    public static final int COMMON = 0;
    public static final int THREE_GPP = 1;
    public static final int THREE_GPP2 = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "COMMON";
        }
        if (i == 1) {
            return "THREE_GPP";
        }
        if (i == 2) {
            return "THREE_GPP2";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("COMMON");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("THREE_GPP");
        }
        if ((i & 2) == 2) {
            arrayList.add("THREE_GPP2");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
