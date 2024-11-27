package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class Result {
    public static final int BAD_PARAMS = 2;
    public static final int NOT_INIT = 3;
    public static final int OK = 0;
    public static final int TRANSACTION_FAILED = 4;
    public static final int TRANSACTION_PENDING = 5;
    public static final int UNKNOWN_FAILURE = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "UNKNOWN_FAILURE";
        }
        if (i == 2) {
            return "BAD_PARAMS";
        }
        if (i == 3) {
            return "NOT_INIT";
        }
        if (i == 4) {
            return "TRANSACTION_FAILED";
        }
        if (i == 5) {
            return "TRANSACTION_PENDING";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("OK");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("UNKNOWN_FAILURE");
        }
        if ((i & 2) == 2) {
            arrayList.add("BAD_PARAMS");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("NOT_INIT");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("TRANSACTION_FAILED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("TRANSACTION_PENDING");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
