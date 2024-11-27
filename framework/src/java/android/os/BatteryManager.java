package android.os;

/* loaded from: classes3.dex */
public class BatteryManager {
    public static final java.lang.String ACTION_CHARGING = "android.os.action.CHARGING";
    public static final java.lang.String ACTION_DISCHARGING = "android.os.action.DISCHARGING";
    public static final int BATTERY_HEALTH_COLD = 7;
    public static final int BATTERY_HEALTH_DEAD = 4;
    public static final int BATTERY_HEALTH_GOOD = 2;
    public static final int BATTERY_HEALTH_OVERHEAT = 3;
    public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;
    public static final int BATTERY_HEALTH_UNKNOWN = 1;
    public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
    public static final int BATTERY_PLUGGED_AC = 1;
    public static final int BATTERY_PLUGGED_ANY = 15;
    public static final int BATTERY_PLUGGED_DOCK = 8;
    public static final int BATTERY_PLUGGED_MOD = 8;
    public static final int BATTERY_PLUGGED_USB = 2;
    public static final int BATTERY_PLUGGED_WIRELESS = 4;
    public static final int BATTERY_PROPERTY_CAPACITY = 4;
    public static final int BATTERY_PROPERTY_CHARGE_COUNTER = 1;
    public static final int BATTERY_PROPERTY_CHARGE_FULL = 101;

    @android.annotation.SystemApi
    public static final int BATTERY_PROPERTY_CHARGING_POLICY = 9;
    public static final int BATTERY_PROPERTY_CURRENT_AVERAGE = 3;
    public static final int BATTERY_PROPERTY_CURRENT_NOW = 2;
    public static final int BATTERY_PROPERTY_ENERGY_COUNTER = 5;

    @android.annotation.SystemApi
    public static final int BATTERY_PROPERTY_FIRST_USAGE_DATE = 8;

    @android.annotation.SystemApi
    public static final int BATTERY_PROPERTY_MANUFACTURING_DATE = 7;
    public static final int BATTERY_PROPERTY_MOD_CHARGE_FULL = 100;

    @android.annotation.SystemApi
    public static final int BATTERY_PROPERTY_PART_STATUS = 12;

    @android.annotation.SystemApi
    public static final int BATTERY_PROPERTY_SERIAL_NUMBER = 11;
    public static final int BATTERY_PROPERTY_STATE_OF_HEALTH = 10;
    public static final int BATTERY_PROPERTY_STATUS = 6;
    public static final int BATTERY_STATUS_CHARGING = 2;
    public static final int BATTERY_STATUS_DISCHARGING = 3;
    public static final int BATTERY_STATUS_FULL = 5;
    public static final int BATTERY_STATUS_NOT_CHARGING = 4;
    public static final int BATTERY_STATUS_UNKNOWN = 1;

    @android.annotation.SystemApi
    public static final int CHARGING_POLICY_ADAPTIVE_AC = 3;

    @android.annotation.SystemApi
    public static final int CHARGING_POLICY_ADAPTIVE_AON = 2;

    @android.annotation.SystemApi
    public static final int CHARGING_POLICY_ADAPTIVE_LONGLIFE = 4;

    @android.annotation.SystemApi
    public static final int CHARGING_POLICY_DEFAULT = 1;
    public static final java.lang.String EXTRA_BATTERY_LOW = "battery_low";
    public static final java.lang.String EXTRA_CHARGE_COUNTER = "charge_counter";
    public static final java.lang.String EXTRA_CHARGING_STATUS = "android.os.extra.CHARGING_STATUS";
    public static final java.lang.String EXTRA_CYCLE_COUNT = "android.os.extra.CYCLE_COUNT";
    public static final java.lang.String EXTRA_DESIGN_CAPACITY = "android.os.extra.DESIGN_CAPACITY";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_EVENTS = "android.os.extra.EVENTS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_EVENT_TIMESTAMP = "android.os.extra.EVENT_TIMESTAMP";
    public static final java.lang.String EXTRA_HEALTH = "health";
    public static final java.lang.String EXTRA_ICON_SMALL = "icon-small";
    public static final java.lang.String EXTRA_INVALID_CHARGER = "invalid_charger";
    public static final java.lang.String EXTRA_LEVEL = "level";
    public static final java.lang.String EXTRA_MAXIMUM_CAPACITY = "android.os.extra.MAXIMUM_CAPACITY";
    public static final java.lang.String EXTRA_MAX_CHARGING_CURRENT = "max_charging_current";
    public static final java.lang.String EXTRA_MAX_CHARGING_VOLTAGE = "max_charging_voltage";
    public static final java.lang.String EXTRA_MOD_FLAG = "mod_flag";
    public static final java.lang.String EXTRA_MOD_LEVEL = "mod_level";
    public static final java.lang.String EXTRA_MOD_POWER_SOURCE = "mod_psrc";
    public static final java.lang.String EXTRA_MOD_STATUS = "mod_status";
    public static final java.lang.String EXTRA_MOD_TYPE = "mod_type";
    public static final java.lang.String EXTRA_PLUGGED = "plugged";
    public static final java.lang.String EXTRA_PLUGGED_RAW = "plugged_raw";
    public static final java.lang.String EXTRA_PRESENT = "present";
    public static final java.lang.String EXTRA_SCALE = "scale";
    public static final java.lang.String EXTRA_SEQUENCE = "seq";
    public static final java.lang.String EXTRA_STATUS = "status";
    public static final java.lang.String EXTRA_TECHNOLOGY = "technology";
    public static final java.lang.String EXTRA_TEMPERATURE = "temperature";
    public static final java.lang.String EXTRA_VOLTAGE = "voltage";

    @android.annotation.SystemApi
    public static final int PART_STATUS_ORIGINAL = 1;

    @android.annotation.SystemApi
    public static final int PART_STATUS_REPLACED = 2;

    @android.annotation.SystemApi
    public static final int PART_STATUS_UNSUPPORTED = 0;
    private final android.os.IBatteryPropertiesRegistrar mBatteryPropertiesRegistrar;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private final android.content.Context mContext;

    public static boolean isAdaptiveChargingPolicy(int i) {
        return i == 3 || i == 2 || i == 4;
    }

    public BatteryManager() {
        this.mContext = null;
        this.mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        this.mBatteryPropertiesRegistrar = android.os.IBatteryPropertiesRegistrar.Stub.asInterface(android.os.ServiceManager.getService("batteryproperties"));
    }

    public BatteryManager(android.content.Context context, com.android.internal.app.IBatteryStats iBatteryStats, android.os.IBatteryPropertiesRegistrar iBatteryPropertiesRegistrar) {
        this.mContext = context;
        this.mBatteryStats = iBatteryStats;
        this.mBatteryPropertiesRegistrar = iBatteryPropertiesRegistrar;
    }

    public boolean isCharging() {
        try {
            return this.mBatteryStats.isCharging();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private long queryProperty(int i) {
        if (this.mBatteryPropertiesRegistrar == null) {
            return Long.MIN_VALUE;
        }
        try {
            android.os.BatteryProperty batteryProperty = new android.os.BatteryProperty();
            if (this.mBatteryPropertiesRegistrar.getProperty(i, batteryProperty) == 0) {
                return batteryProperty.getLong();
            }
            return Long.MIN_VALUE;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.lang.String queryStringProperty(int i) {
        if (this.mBatteryPropertiesRegistrar == null) {
            return null;
        }
        try {
            android.os.BatteryProperty batteryProperty = new android.os.BatteryProperty();
            if (this.mBatteryPropertiesRegistrar.getProperty(i, batteryProperty) == 0) {
                return batteryProperty.getString();
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIntProperty(int i) {
        long queryProperty = queryProperty(i);
        if (queryProperty == Long.MIN_VALUE && this.mContext != null && this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
            return Integer.MIN_VALUE;
        }
        return (int) queryProperty;
    }

    public long getLongProperty(int i) {
        return queryProperty(i);
    }

    public java.lang.String getStringProperty(int i) {
        return queryStringProperty(i);
    }

    public static boolean isPlugWired(int i) {
        return i == 2 || i == 1;
    }

    public long computeChargeTimeRemaining() {
        try {
            return this.mBatteryStats.computeChargeTimeRemaining();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setChargingStateUpdateDelayMillis(int i) {
        try {
            return this.mBatteryStats.setChargingStateUpdateDelayMillis(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
