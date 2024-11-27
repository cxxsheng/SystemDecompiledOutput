package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public final class ElapsedRealtimeFlags {
    public static final short HAS_TIMESTAMP_NS = 1;
    public static final short HAS_TIME_UNCERTAINTY_NS = 2;

    public static final java.lang.String toString(short s) {
        if (s == 1) {
            return "HAS_TIMESTAMP_NS";
        }
        if (s == 2) {
            return "HAS_TIME_UNCERTAINTY_NS";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt(s));
    }

    public static final java.lang.String dumpBitfield(short s) {
        short s2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((s & 1) != 1) {
            s2 = 0;
        } else {
            arrayList.add("HAS_TIMESTAMP_NS");
            s2 = (short) 1;
        }
        if ((s & 2) == 2) {
            arrayList.add("HAS_TIME_UNCERTAINTY_NS");
            s2 = (short) (s2 | 2);
        }
        if (s != s2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
