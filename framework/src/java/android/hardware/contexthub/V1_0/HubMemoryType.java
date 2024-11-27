package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class HubMemoryType {
    public static final int MAIN = 0;
    public static final int SECONDARY = 1;
    public static final int TCM = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "MAIN";
        }
        if (i == 1) {
            return "SECONDARY";
        }
        if (i == 2) {
            return "TCM";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("MAIN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SECONDARY");
        }
        if ((i & 2) == 2) {
            arrayList.add("TCM");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
