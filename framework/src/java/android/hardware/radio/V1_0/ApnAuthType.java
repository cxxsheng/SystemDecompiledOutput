package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class ApnAuthType {
    public static final int NO_PAP_CHAP = 2;
    public static final int NO_PAP_NO_CHAP = 0;
    public static final int PAP_CHAP = 3;
    public static final int PAP_NO_CHAP = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NO_PAP_NO_CHAP";
        }
        if (i == 1) {
            return "PAP_NO_CHAP";
        }
        if (i == 2) {
            return "NO_PAP_CHAP";
        }
        if (i == 3) {
            return "PAP_CHAP";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NO_PAP_NO_CHAP");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("PAP_NO_CHAP");
        }
        if ((i & 2) == 2) {
            arrayList.add("NO_PAP_CHAP");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PAP_CHAP");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
