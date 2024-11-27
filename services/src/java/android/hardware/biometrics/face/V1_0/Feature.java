package android.hardware.biometrics.face.V1_0;

/* loaded from: classes.dex */
public final class Feature {
    public static final int REQUIRE_ATTENTION = 1;
    public static final int REQUIRE_DIVERSITY = 2;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "REQUIRE_ATTENTION";
        }
        if (i == 2) {
            return "REQUIRE_DIVERSITY";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("REQUIRE_ATTENTION");
        }
        if ((i & 2) == 2) {
            arrayList.add("REQUIRE_DIVERSITY");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
