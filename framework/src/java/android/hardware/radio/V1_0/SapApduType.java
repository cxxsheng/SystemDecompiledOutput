package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SapApduType {
    public static final int APDU = 0;
    public static final int APDU7816 = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "APDU";
        }
        if (i == 1) {
            return "APDU7816";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("APDU");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("APDU7816");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
