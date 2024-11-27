package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class HostEndPoint {
    public static final short BROADCAST = -1;
    public static final short UNSPECIFIED = -2;

    public static final java.lang.String toString(short s) {
        if (s == -1) {
            return "BROADCAST";
        }
        if (s == -2) {
            return "UNSPECIFIED";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt(s));
    }

    public static final java.lang.String dumpBitfield(short s) {
        short s2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((s & (-1)) != -1) {
            s2 = 0;
        } else {
            arrayList.add("BROADCAST");
            s2 = (short) (-1);
        }
        if ((s & (-2)) == -2) {
            arrayList.add("UNSPECIFIED");
            s2 = (short) (s2 | (-2));
        }
        if (s != s2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
