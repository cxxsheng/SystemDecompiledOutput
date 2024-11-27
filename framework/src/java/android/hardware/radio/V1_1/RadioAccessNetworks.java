package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class RadioAccessNetworks {
    public static final int EUTRAN = 3;
    public static final int GERAN = 1;
    public static final int UTRAN = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "GERAN";
        }
        if (i == 2) {
            return "UTRAN";
        }
        if (i == 3) {
            return "EUTRAN";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("GERAN");
        }
        if ((i & 2) == 2) {
            arrayList.add("UTRAN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("EUTRAN");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
