package android.hardware.health.V1_0;

/* loaded from: classes.dex */
public final class BatteryHealth {
    public static final int COLD = 7;
    public static final int DEAD = 4;
    public static final int GOOD = 2;
    public static final int OVERHEAT = 3;
    public static final int OVER_VOLTAGE = 5;
    public static final int UNKNOWN = 1;
    public static final int UNSPECIFIED_FAILURE = 6;

    public static final java.lang.String toString(int i) {
        if (i == 1) {
            return "UNKNOWN";
        }
        if (i == 2) {
            return "GOOD";
        }
        if (i == 3) {
            return "OVERHEAT";
        }
        if (i == 4) {
            return "DEAD";
        }
        if (i == 5) {
            return "OVER_VOLTAGE";
        }
        if (i == 6) {
            return "UNSPECIFIED_FAILURE";
        }
        if (i == 7) {
            return "COLD";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("UNKNOWN");
        }
        if ((i & 2) == 2) {
            arrayList.add("GOOD");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("OVERHEAT");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("DEAD");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("OVER_VOLTAGE");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("UNSPECIFIED_FAILURE");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("COLD");
            i2 = 7;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
