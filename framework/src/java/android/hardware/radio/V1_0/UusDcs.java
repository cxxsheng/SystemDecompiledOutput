package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class UusDcs {
    public static final int IA5C = 4;
    public static final int OSIHLP = 1;
    public static final int RMCF = 3;
    public static final int USP = 0;
    public static final int X244 = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "USP";
        }
        if (i == 1) {
            return "OSIHLP";
        }
        if (i == 2) {
            return "X244";
        }
        if (i == 3) {
            return "RMCF";
        }
        if (i == 4) {
            return "IA5C";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("USP");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("OSIHLP");
        }
        if ((i & 2) == 2) {
            arrayList.add("X244");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("RMCF");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("IA5C");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
