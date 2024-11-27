package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class NrDualConnectivityState {
    public static final byte DISABLE = 2;
    public static final byte DISABLE_IMMEDIATE = 3;
    public static final byte ENABLE = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 1) {
            return "ENABLE";
        }
        if (b == 2) {
            return "DISABLE";
        }
        if (b == 3) {
            return "DISABLE_IMMEDIATE";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("ENABLE");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("DISABLE");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("DISABLE_IMMEDIATE");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
