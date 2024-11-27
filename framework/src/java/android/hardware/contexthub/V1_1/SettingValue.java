package android.hardware.contexthub.V1_1;

/* loaded from: classes.dex */
public final class SettingValue {
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "DISABLED";
        }
        if (b == 1) {
            return "ENABLED";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("DISABLED");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("ENABLED");
            b2 = (byte) 1;
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
