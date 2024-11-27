package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SubscriptionType {
    public static final int SUBSCRIPTION_1 = 0;
    public static final int SUBSCRIPTION_2 = 1;
    public static final int SUBSCRIPTION_3 = 2;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "SUBSCRIPTION_1";
        }
        if (i == 1) {
            return "SUBSCRIPTION_2";
        }
        if (i == 2) {
            return "SUBSCRIPTION_3";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("SUBSCRIPTION_1");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("SUBSCRIPTION_2");
        }
        if ((i & 2) == 2) {
            arrayList.add("SUBSCRIPTION_3");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
