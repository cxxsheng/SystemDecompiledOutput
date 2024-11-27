package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class DataConnActiveStatus {
    public static final int ACTIVE = 2;
    public static final int DORMANT = 1;
    public static final int INACTIVE = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "INACTIVE";
        }
        if (i == 1) {
            return "DORMANT";
        }
        if (i == 2) {
            return "ACTIVE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("INACTIVE");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("DORMANT");
        }
        if ((i & 2) == 2) {
            arrayList.add("ACTIVE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
