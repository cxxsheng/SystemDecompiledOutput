package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosFlowIdRange {
    public static final byte MAX = 63;
    public static final byte MIN = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 1) {
            return "MIN";
        }
        if (b == 63) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("MIN");
            b2 = (byte) 1;
        }
        if ((b & 63) == 63) {
            arrayList.add("MAX");
            b2 = (byte) (b2 | 63);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
