package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaRoamingType {
    public static final int AFFILIATED_ROAM = 1;
    public static final int ANY_ROAM = 2;
    public static final int HOME_NETWORK = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "HOME_NETWORK";
        }
        if (i == 1) {
            return "AFFILIATED_ROAM";
        }
        if (i == 2) {
            return "ANY_ROAM";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("HOME_NETWORK");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("AFFILIATED_ROAM");
        }
        if ((i & 2) == 2) {
            arrayList.add("ANY_ROAM");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
