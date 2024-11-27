package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class EmergencyCallRouting {
    public static final int EMERGENCY = 1;
    public static final int NORMAL = 2;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "EMERGENCY";
        }
        if (i == 2) {
            return android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL;
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("EMERGENCY");
        }
        if ((i & 2) == 2) {
            arrayList.add(android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL);
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
