package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class AddressProperty {
    public static final int DEPRECATED = 32;
    public static final int NONE = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (i == 32) {
            return "DEPRECATED";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
        int i2 = 32;
        if ((i & 32) != 32) {
            i2 = 0;
        } else {
            arrayList.add("DEPRECATED");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
