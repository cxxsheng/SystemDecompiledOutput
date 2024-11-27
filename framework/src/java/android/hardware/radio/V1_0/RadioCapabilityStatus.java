package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class RadioCapabilityStatus {
    public static final int FAIL = 2;
    public static final int NONE = 0;
    public static final int SUCCESS = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 2) {
            return "FAIL";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add(android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY);
        }
        if ((i & 2) == 2) {
            arrayList.add("FAIL");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
