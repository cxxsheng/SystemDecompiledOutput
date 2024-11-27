package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class AbortReason {
    public static final int CANNOT_PROVIDE_SOURCE = 2;
    public static final int INVALID_OPERAND = 3;
    public static final int NOT_IN_CORRECT_MODE = 1;
    public static final int REFUSED = 4;
    public static final int UNABLE_TO_DETERMINE = 5;
    public static final int UNRECOGNIZED_MODE = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNRECOGNIZED_MODE";
        }
        if (i == 1) {
            return "NOT_IN_CORRECT_MODE";
        }
        if (i == 2) {
            return "CANNOT_PROVIDE_SOURCE";
        }
        if (i == 3) {
            return "INVALID_OPERAND";
        }
        if (i == 4) {
            return "REFUSED";
        }
        if (i == 5) {
            return "UNABLE_TO_DETERMINE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNRECOGNIZED_MODE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("NOT_IN_CORRECT_MODE");
        }
        if ((i & 2) == 2) {
            arrayList.add("CANNOT_PROVIDE_SOURCE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("INVALID_OPERAND");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("REFUSED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("UNABLE_TO_DETERMINE");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
