package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class DataProfileId {
    public static final int CBS = 4;
    public static final int DEFAULT = 0;
    public static final int FOTA = 3;
    public static final int IMS = 2;
    public static final int INVALID = -1;
    public static final int OEM_BASE = 1000;
    public static final int TETHERED = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "DEFAULT";
        }
        if (i == 1) {
            return "TETHERED";
        }
        if (i == 2) {
            return "IMS";
        }
        if (i == 3) {
            return "FOTA";
        }
        if (i == 4) {
            return "CBS";
        }
        if (i == 1000) {
            return "OEM_BASE";
        }
        if (i == -1) {
            return "INVALID";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("DEFAULT");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("TETHERED");
        }
        if ((i & 2) == 2) {
            arrayList.add("IMS");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("FOTA");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CBS");
            i2 |= 4;
        }
        if ((i & 1000) == 1000) {
            arrayList.add("OEM_BASE");
            i2 |= 1000;
        }
        if ((i & (-1)) == -1) {
            arrayList.add("INVALID");
            i2 = -1;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
