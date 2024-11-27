package android.hardware.cas.V1_2;

/* loaded from: classes.dex */
public final class StatusEvent {
    public static final byte PLUGIN_PHYSICAL_MODULE_CHANGED = 0;
    public static final byte PLUGIN_SESSION_NUMBER_CHANGED = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "PLUGIN_PHYSICAL_MODULE_CHANGED";
        }
        if (b == 1) {
            return "PLUGIN_SESSION_NUMBER_CHANGED";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("PLUGIN_PHYSICAL_MODULE_CHANGED");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("PLUGIN_SESSION_NUMBER_CHANGED");
            b2 = (byte) 1;
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
