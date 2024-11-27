package android.hardware.boot.V1_1;

/* loaded from: classes.dex */
public final class MergeStatus {
    public static final int CANCELLED = 4;
    public static final int MERGING = 3;
    public static final int NONE = 0;
    public static final int SNAPSHOTTED = 2;
    public static final int UNKNOWN = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NONE";
        }
        if (i == 1) {
            return "UNKNOWN";
        }
        if (i == 2) {
            return "SNAPSHOTTED";
        }
        if (i == 3) {
            return "MERGING";
        }
        if (i == 4) {
            return "CANCELLED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NONE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("UNKNOWN");
        }
        if ((i & 2) == 2) {
            arrayList.add("SNAPSHOTTED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("MERGING");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CANCELLED");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
