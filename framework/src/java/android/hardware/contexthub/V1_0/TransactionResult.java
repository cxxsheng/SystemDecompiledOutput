package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class TransactionResult {
    public static final int FAILURE = 1;
    public static final int SUCCESS = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 1) {
            return "FAILURE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY);
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("FAILURE");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
