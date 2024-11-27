package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class Deemphasis {
    public static final byte D50 = 1;
    public static final byte D75 = 2;

    public static final java.lang.String toString(byte b) {
        if (b == 1) {
            return "D50";
        }
        if (b == 2) {
            return "D75";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("D50");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("D75");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
