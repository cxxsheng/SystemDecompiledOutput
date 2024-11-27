package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class Clir {
    public static final int DEFAULT = 0;
    public static final int INVOCATION = 1;
    public static final int SUPPRESSION = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "DEFAULT";
        }
        if (i == 1) {
            return "INVOCATION";
        }
        if (i == 2) {
            return "SUPPRESSION";
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
            arrayList.add("INVOCATION");
        }
        if ((i & 2) == 2) {
            arrayList.add("SUPPRESSION");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}