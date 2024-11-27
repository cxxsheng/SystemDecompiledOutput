package android.location.flags;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.location.flags.FeatureFlags FEATURE_FLAGS = new android.location.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_FIX_SERVICE_WATCHER = "android.location.flags.fix_service_watcher";
    public static final java.lang.String FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL = "android.location.flags.geoid_heights_via_altitude_hal";
    public static final java.lang.String FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE = "android.location.flags.gnss_api_measurement_request_work_source";
    public static final java.lang.String FLAG_GNSS_API_NAVIC_L1 = "android.location.flags.gnss_api_navic_l1";
    public static final java.lang.String FLAG_GNSS_CALL_STOP_BEFORE_SET_POSITION_MODE = "android.location.flags.gnss_call_stop_before_set_position_mode";
    public static final java.lang.String FLAG_GNSS_CONFIGURATION_FROM_RESOURCE = "android.location.flags.gnss_configuration_from_resource";
    public static final java.lang.String FLAG_LOCATION_BYPASS = "android.location.flags.location_bypass";
    public static final java.lang.String FLAG_LOCATION_VALIDATION = "android.location.flags.location_validation";
    public static final java.lang.String FLAG_NEW_GEOCODER = "android.location.flags.new_geocoder";
    public static final java.lang.String FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT = "android.location.flags.release_supl_connection_on_timeout";
    public static final java.lang.String FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI = "android.location.flags.replace_future_elapsed_realtime_jni";

    public static boolean fixServiceWatcher() {
        return FEATURE_FLAGS.fixServiceWatcher();
    }

    public static boolean geoidHeightsViaAltitudeHal() {
        return FEATURE_FLAGS.geoidHeightsViaAltitudeHal();
    }

    public static boolean gnssApiMeasurementRequestWorkSource() {
        return FEATURE_FLAGS.gnssApiMeasurementRequestWorkSource();
    }

    public static boolean gnssApiNavicL1() {
        return FEATURE_FLAGS.gnssApiNavicL1();
    }

    public static boolean gnssCallStopBeforeSetPositionMode() {
        return FEATURE_FLAGS.gnssCallStopBeforeSetPositionMode();
    }

    public static boolean gnssConfigurationFromResource() {
        return FEATURE_FLAGS.gnssConfigurationFromResource();
    }

    public static boolean locationBypass() {
        return FEATURE_FLAGS.locationBypass();
    }

    public static boolean locationValidation() {
        return FEATURE_FLAGS.locationValidation();
    }

    public static boolean newGeocoder() {
        return FEATURE_FLAGS.newGeocoder();
    }

    public static boolean releaseSuplConnectionOnTimeout() {
        return FEATURE_FLAGS.releaseSuplConnectionOnTimeout();
    }

    public static boolean replaceFutureElapsedRealtimeJni() {
        return FEATURE_FLAGS.replaceFutureElapsedRealtimeJni();
    }
}
