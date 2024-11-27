package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class HandoverFailureMode {
    public static final byte DO_FALLBACK = 1;
    public static final byte LEGACY = 0;
    public static final byte NO_FALLBACK_RETRY_HANDOVER = 2;
    public static final byte NO_FALLBACK_RETRY_SETUP_NORMAL = 3;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "LEGACY";
        }
        if (b == 1) {
            return "DO_FALLBACK";
        }
        if (b == 2) {
            return "NO_FALLBACK_RETRY_HANDOVER";
        }
        if (b == 3) {
            return "NO_FALLBACK_RETRY_SETUP_NORMAL";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LEGACY");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("DO_FALLBACK");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("NO_FALLBACK_RETRY_HANDOVER");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("NO_FALLBACK_RETRY_SETUP_NORMAL");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
