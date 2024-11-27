package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosFilterDirection {
    public static final byte BIDIRECTIONAL = 2;
    public static final byte DOWNLINK = 0;
    public static final byte UPLINK = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "DOWNLINK";
        }
        if (b == 1) {
            return "UPLINK";
        }
        if (b == 2) {
            return "BIDIRECTIONAL";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("DOWNLINK");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("UPLINK");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("BIDIRECTIONAL");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
