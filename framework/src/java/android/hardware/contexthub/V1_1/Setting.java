package android.hardware.contexthub.V1_1;

/* loaded from: classes.dex */
public final class Setting {
    public static final byte LOCATION = 0;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "LOCATION";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LOCATION");
        if (b != 0) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (-1)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
