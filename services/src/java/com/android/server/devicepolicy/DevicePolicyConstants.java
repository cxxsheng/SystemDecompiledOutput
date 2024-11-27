package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class DevicePolicyConstants {
    private static final java.lang.String BATTERY_THRESHOLD_CHARGING_KEY = "battery_threshold_charging";
    private static final java.lang.String BATTERY_THRESHOLD_NOT_CHARGING_KEY = "battery_threshold_not_charging";
    private static final java.lang.String DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE_KEY = "das_died_service_reconnect_backoff_increase";
    private static final java.lang.String DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC_KEY = "das_died_service_reconnect_backoff_sec";
    private static final java.lang.String DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC_KEY = "das_died_service_reconnect_max_backoff_sec";
    private static final java.lang.String DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC_KEY = "das_died_service_stable_connection_threshold_sec";
    private static final java.lang.String TAG = "DevicePolicyManager";
    private static final java.lang.String USE_TEST_ADMIN_AS_SUPERVISION_COMPONENT_KEY = "use_test_admin_as_supervision_component";
    public final int BATTERY_THRESHOLD_CHARGING;
    public final int BATTERY_THRESHOLD_NOT_CHARGING;
    public final double DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE;
    public final long DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC;
    public final long DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC;
    public final long DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC;
    public final boolean USE_TEST_ADMIN_AS_SUPERVISION_COMPONENT;

    private DevicePolicyConstants(java.lang.String str) {
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
        } catch (java.lang.IllegalArgumentException e) {
            com.android.server.utils.Slogf.e(TAG, "Bad device policy settings: %s", str);
        }
        long j = keyValueListParser.getLong(DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC_KEY, java.util.concurrent.TimeUnit.HOURS.toSeconds(1L));
        double d = keyValueListParser.getFloat(DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE_KEY, 2.0f);
        long j2 = keyValueListParser.getLong(DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC_KEY, java.util.concurrent.TimeUnit.DAYS.toSeconds(1L));
        long j3 = keyValueListParser.getLong(DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC_KEY, java.util.concurrent.TimeUnit.MINUTES.toSeconds(2L));
        int i = keyValueListParser.getInt(BATTERY_THRESHOLD_NOT_CHARGING_KEY, 40);
        int i2 = keyValueListParser.getInt(BATTERY_THRESHOLD_CHARGING_KEY, 20);
        boolean z = keyValueListParser.getBoolean(USE_TEST_ADMIN_AS_SUPERVISION_COMPONENT_KEY, false);
        long max = java.lang.Math.max(5L, j);
        double max2 = java.lang.Math.max(1.0d, d);
        long max3 = java.lang.Math.max(max, j2);
        this.DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC = max;
        this.DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE = max2;
        this.DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC = max3;
        this.DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC = j3;
        this.BATTERY_THRESHOLD_NOT_CHARGING = i;
        this.BATTERY_THRESHOLD_CHARGING = i2;
        this.USE_TEST_ADMIN_AS_SUPERVISION_COMPONENT = z;
    }

    public static com.android.server.devicepolicy.DevicePolicyConstants loadFromString(java.lang.String str) {
        return new com.android.server.devicepolicy.DevicePolicyConstants(str);
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Constants:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC: ");
        indentingPrintWriter.println(this.DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC);
        indentingPrintWriter.print("DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE: ");
        indentingPrintWriter.println(this.DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE);
        indentingPrintWriter.print("DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC: ");
        indentingPrintWriter.println(this.DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC);
        indentingPrintWriter.print("DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC: ");
        indentingPrintWriter.println(this.DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC);
        indentingPrintWriter.decreaseIndent();
    }
}
