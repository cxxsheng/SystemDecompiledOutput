package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class ResetNvType {
    public static final int ERASE = 1;
    public static final int FACTORY_RESET = 2;
    public static final int RELOAD = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "RELOAD";
        }
        if (i == 1) {
            return "ERASE";
        }
        if (i == 2) {
            return "FACTORY_RESET";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("RELOAD");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ERASE");
        }
        if ((i & 2) == 2) {
            arrayList.add("FACTORY_RESET");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
