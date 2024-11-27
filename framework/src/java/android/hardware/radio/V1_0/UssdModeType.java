package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class UssdModeType {
    public static final int LOCAL_CLIENT = 3;
    public static final int NOTIFY = 0;
    public static final int NOT_SUPPORTED = 4;
    public static final int NW_RELEASE = 2;
    public static final int NW_TIMEOUT = 5;
    public static final int REQUEST = 1;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "NOTIFY";
        }
        if (i == 1) {
            return "REQUEST";
        }
        if (i == 2) {
            return "NW_RELEASE";
        }
        if (i == 3) {
            return "LOCAL_CLIENT";
        }
        if (i == 4) {
            return "NOT_SUPPORTED";
        }
        if (i == 5) {
            return "NW_TIMEOUT";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("NOTIFY");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("REQUEST");
        }
        if ((i & 2) == 2) {
            arrayList.add("NW_RELEASE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("LOCAL_CLIENT");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("NOT_SUPPORTED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("NW_TIMEOUT");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
