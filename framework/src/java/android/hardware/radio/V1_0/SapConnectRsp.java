package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SapConnectRsp {
    public static final int CONNECT_FAILURE = 1;
    public static final int CONNECT_OK_CALL_ONGOING = 4;
    public static final int MSG_SIZE_TOO_LARGE = 2;
    public static final int MSG_SIZE_TOO_SMALL = 3;
    public static final int SUCCESS = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 1) {
            return "CONNECT_FAILURE";
        }
        if (i == 2) {
            return "MSG_SIZE_TOO_LARGE";
        }
        if (i == 3) {
            return "MSG_SIZE_TOO_SMALL";
        }
        if (i == 4) {
            return "CONNECT_OK_CALL_ONGOING";
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
            arrayList.add("CONNECT_FAILURE");
        }
        if ((i & 2) == 2) {
            arrayList.add("MSG_SIZE_TOO_LARGE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("MSG_SIZE_TOO_SMALL");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CONNECT_OK_CALL_ONGOING");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
