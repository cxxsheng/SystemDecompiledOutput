package android.hardware.configstore.V1_1;

/* loaded from: classes.dex */
public final class DisplayOrientation {
    public static final byte ORIENTATION_0 = 0;
    public static final byte ORIENTATION_180 = 2;
    public static final byte ORIENTATION_270 = 3;
    public static final byte ORIENTATION_90 = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "ORIENTATION_0";
        }
        if (b == 1) {
            return "ORIENTATION_90";
        }
        if (b == 2) {
            return "ORIENTATION_180";
        }
        if (b == 3) {
            return "ORIENTATION_270";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ORIENTATION_0");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("ORIENTATION_90");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("ORIENTATION_180");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("ORIENTATION_270");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
