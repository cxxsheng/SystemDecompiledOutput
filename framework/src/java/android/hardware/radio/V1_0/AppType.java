package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class AppType {
    public static final int CSIM = 4;
    public static final int ISIM = 5;
    public static final int RUIM = 3;
    public static final int SIM = 1;
    public static final int UNKNOWN = 0;
    public static final int USIM = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "SIM";
        }
        if (i == 2) {
            return "USIM";
        }
        if (i == 3) {
            return "RUIM";
        }
        if (i == 4) {
            return "CSIM";
        }
        if (i == 5) {
            return "ISIM";
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
            arrayList.add("SIM");
        }
        if ((i & 2) == 2) {
            arrayList.add("USIM");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("RUIM");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CSIM");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("ISIM");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
