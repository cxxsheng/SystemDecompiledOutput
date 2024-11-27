package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaCallWaitingNumberType {
    public static final int INTERNATIONAL = 1;
    public static final int NATIONAL = 2;
    public static final int NETWORK_SPECIFIC = 3;
    public static final int SUBSCRIBER = 4;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "INTERNATIONAL";
        }
        if (i == 2) {
            return "NATIONAL";
        }
        if (i == 3) {
            return "NETWORK_SPECIFIC";
        }
        if (i == 4) {
            return "SUBSCRIBER";
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
            arrayList.add("INTERNATIONAL");
        }
        if ((i & 2) == 2) {
            arrayList.add("NATIONAL");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("NETWORK_SPECIFIC");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SUBSCRIBER");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
