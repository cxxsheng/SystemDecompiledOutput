package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioMixLatencyClass {
    public static final int LOW = 0;
    public static final int NORMAL = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "LOW";
        }
        if (i == 1) {
            return com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL;
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("LOW");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add(com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL);
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
