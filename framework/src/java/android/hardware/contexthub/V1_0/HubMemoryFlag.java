package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class HubMemoryFlag {
    public static final int EXEC = 4;
    public static final int READ = 1;
    public static final int WRITE = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "READ";
        }
        if (i == 2) {
            return "WRITE";
        }
        if (i == 4) {
            return "EXEC";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("READ");
        }
        if ((i & 2) == 2) {
            arrayList.add("WRITE");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("EXEC");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
