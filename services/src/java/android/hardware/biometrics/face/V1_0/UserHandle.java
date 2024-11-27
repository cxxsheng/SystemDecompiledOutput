package android.hardware.biometrics.face.V1_0;

/* loaded from: classes.dex */
public final class UserHandle {
    public static final int NONE = -1;

    public static final java.lang.String toString(int i) {
        if (i == -1) {
            return "NONE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = -1;
        if ((i & (-1)) != -1) {
            i2 = 0;
        } else {
            arrayList.add("NONE");
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
