package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class SliceServiceType {
    public static final byte EMBB = 1;
    public static final byte MIOT = 3;
    public static final byte NONE = 0;
    public static final byte URLLC = 2;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (b == 1) {
            return "EMBB";
        }
        if (b == 2) {
            return "URLLC";
        }
        if (b == 3) {
            return "MIOT";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("EMBB");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("URLLC");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("MIOT");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
