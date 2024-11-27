package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class DataThrottlingAction {
    public static final byte HOLD = 3;
    public static final byte NO_DATA_THROTTLING = 0;
    public static final byte THROTTLE_ANCHOR_CARRIER = 2;
    public static final byte THROTTLE_SECONDARY_CARRIER = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "NO_DATA_THROTTLING";
        }
        if (b == 1) {
            return "THROTTLE_SECONDARY_CARRIER";
        }
        if (b == 2) {
            return "THROTTLE_ANCHOR_CARRIER";
        }
        if (b == 3) {
            return "HOLD";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NO_DATA_THROTTLING");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("THROTTLE_SECONDARY_CARRIER");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("THROTTLE_ANCHOR_CARRIER");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("HOLD");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
