package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosPortRange {
    public static final short MAX = -1;
    public static final short MIN = 20;

    public static final java.lang.String toString(short s) {
        if (s == 20) {
            return "MIN";
        }
        if (s == -1) {
            return "MAX";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt(s));
    }

    public static final java.lang.String dumpBitfield(short s) {
        short s2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((s & 20) != 20) {
            s2 = 0;
        } else {
            arrayList.add("MIN");
            s2 = (short) 20;
        }
        if ((s & (-1)) == -1) {
            arrayList.add("MAX");
            s2 = (short) (-1);
        }
        if (s != s2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
