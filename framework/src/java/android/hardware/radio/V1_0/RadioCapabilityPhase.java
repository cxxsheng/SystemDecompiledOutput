package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class RadioCapabilityPhase {
    public static final int APPLY = 2;
    public static final int CONFIGURED = 0;
    public static final int FINISH = 4;
    public static final int START = 1;
    public static final int UNSOL_RSP = 3;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "CONFIGURED";
        }
        if (i == 1) {
            return "START";
        }
        if (i == 2) {
            return "APPLY";
        }
        if (i == 3) {
            return "UNSOL_RSP";
        }
        if (i == 4) {
            return "FINISH";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("CONFIGURED");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("START");
        }
        if ((i & 2) == 2) {
            arrayList.add("APPLY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("UNSOL_RSP");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("FINISH");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
