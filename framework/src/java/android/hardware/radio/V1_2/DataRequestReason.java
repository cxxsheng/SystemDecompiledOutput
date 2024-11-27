package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class DataRequestReason {
    public static final int HANDOVER = 3;
    public static final int NORMAL = 1;
    public static final int SHUTDOWN = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL;
        }
        if (i == 2) {
            return "SHUTDOWN";
        }
        if (i == 3) {
            return "HANDOVER";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add(android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL);
        }
        if ((i & 2) == 2) {
            arrayList.add("SHUTDOWN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("HANDOVER");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
