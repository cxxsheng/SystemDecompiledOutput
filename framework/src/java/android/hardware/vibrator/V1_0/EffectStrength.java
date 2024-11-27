package android.hardware.vibrator.V1_0;

/* loaded from: classes2.dex */
public final class EffectStrength {
    public static final byte LIGHT = 0;
    public static final byte MEDIUM = 1;
    public static final byte STRONG = 2;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "LIGHT";
        }
        if (b == 1) {
            return "MEDIUM";
        }
        if (b == 2) {
            return "STRONG";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LIGHT");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("MEDIUM");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("STRONG");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
