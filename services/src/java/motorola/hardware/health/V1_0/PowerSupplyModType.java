package motorola.hardware.health.V1_0;

/* loaded from: classes3.dex */
public final class PowerSupplyModType {
    public static final int POWER_SUPPLY_MOD_TYPE_EMERGENCY = 3;
    public static final int POWER_SUPPLY_MOD_TYPE_REMOTE = 1;
    public static final int POWER_SUPPLY_MOD_TYPE_SUPPLEMENTAL = 2;
    public static final int POWER_SUPPLY_MOD_TYPE_UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "POWER_SUPPLY_MOD_TYPE_UNKNOWN";
        }
        if (i == 1) {
            return "POWER_SUPPLY_MOD_TYPE_REMOTE";
        }
        if (i == 2) {
            return "POWER_SUPPLY_MOD_TYPE_SUPPLEMENTAL";
        }
        if (i == 3) {
            return "POWER_SUPPLY_MOD_TYPE_EMERGENCY";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("POWER_SUPPLY_MOD_TYPE_UNKNOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("POWER_SUPPLY_MOD_TYPE_REMOTE");
        }
        if ((i & 2) == 2) {
            arrayList.add("POWER_SUPPLY_MOD_TYPE_SUPPLEMENTAL");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("POWER_SUPPLY_MOD_TYPE_EMERGENCY");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
