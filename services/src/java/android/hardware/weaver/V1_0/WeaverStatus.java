package android.hardware.weaver.V1_0;

/* loaded from: classes.dex */
public final class WeaverStatus {
    public static final int FAILED = 1;
    public static final int OK = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "OK";
        }
        if (i == 1) {
            return "FAILED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("OK");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("FAILED");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
