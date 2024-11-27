package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class VopsIndicator {
    public static final byte VOPS_NOT_SUPPORTED = 0;
    public static final byte VOPS_OVER_3GPP = 1;
    public static final byte VOPS_OVER_NON_3GPP = 2;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "VOPS_NOT_SUPPORTED";
        }
        if (b == 1) {
            return "VOPS_OVER_3GPP";
        }
        if (b == 2) {
            return "VOPS_OVER_NON_3GPP";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("VOPS_NOT_SUPPORTED");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("VOPS_OVER_3GPP");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("VOPS_OVER_NON_3GPP");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
