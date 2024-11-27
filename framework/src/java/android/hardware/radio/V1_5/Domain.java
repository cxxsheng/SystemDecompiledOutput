package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class Domain {
    public static final int CS = 1;
    public static final int PS = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "CS";
        }
        if (i == 2) {
            return "PS";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("CS");
        }
        if ((i & 2) == 2) {
            arrayList.add("PS");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
