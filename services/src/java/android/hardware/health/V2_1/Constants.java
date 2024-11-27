package android.hardware.health.V2_1;

/* loaded from: classes.dex */
public final class Constants {
    public static final long BATTERY_CHARGE_TIME_TO_FULL_NOW_SECONDS_UNSUPPORTED = -1;

    public static final java.lang.String toString(long j) {
        if (j == -1) {
            return "BATTERY_CHARGE_TIME_TO_FULL_NOW_SECONDS_UNSUPPORTED";
        }
        return "0x" + java.lang.Long.toHexString(j);
    }

    public static final java.lang.String dumpBitfield(long j) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long j2 = -1;
        if ((j & (-1)) != -1) {
            j2 = 0;
        } else {
            arrayList.add("BATTERY_CHARGE_TIME_TO_FULL_NOW_SECONDS_UNSUPPORTED");
        }
        if (j != j2) {
            arrayList.add("0x" + java.lang.Long.toHexString(j & (~j2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
