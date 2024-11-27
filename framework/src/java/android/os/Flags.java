package android.os;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.os.FeatureFlags FEATURE_FLAGS = new android.os.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION = "android.os.adpf_gpu_report_actual_work_duration";
    public static final java.lang.String FLAG_ADPF_PREFER_POWER_EFFICIENCY = "android.os.adpf_prefer_power_efficiency";
    public static final java.lang.String FLAG_ADPF_USE_FMQ_CHANNEL = "android.os.adpf_use_fmq_channel";
    public static final java.lang.String FLAG_ALLOW_PRIVATE_PROFILE = "android.os.allow_private_profile";
    public static final java.lang.String FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS = "android.os.allow_thermal_headroom_thresholds";
    public static final java.lang.String FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM = "android.os.android_os_build_vanilla_ice_cream";
    public static final java.lang.String FLAG_BATTERY_PART_STATUS_API = "android.os.battery_part_status_api";
    public static final java.lang.String FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API = "android.os.battery_saver_supported_check_api";
    public static final java.lang.String FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND = "android.os.battery_service_support_current_adb_command";
    public static final java.lang.String FLAG_BUGREPORT_MODE_MAX_VALUE = "android.os.bugreport_mode_max_value";
    public static final java.lang.String FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION = "android.os.disallow_cellular_null_ciphers_restriction";
    public static final java.lang.String FLAG_MESSAGE_QUEUE_TAIL_TRACKING = "android.os.message_queue_tail_tracking";
    public static final java.lang.String FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION = "android.os.remove_app_profiler_pss_collection";
    public static final java.lang.String FLAG_SECURITY_STATE_SERVICE = "android.os.security_state_service";
    public static final java.lang.String FLAG_STATE_OF_HEALTH_PUBLIC = "android.os.state_of_health_public";
    public static final java.lang.String FLAG_STORAGE_LIFETIME_API = "android.os.storage_lifetime_api";
    public static final java.lang.String FLAG_STRICT_MODE_RESTRICTED_NETWORK = "android.os.strict_mode_restricted_network";
    public static final java.lang.String FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION = "android.os.telemetry_apis_framework_initialization";

    public static boolean adpfGpuReportActualWorkDuration() {
        return FEATURE_FLAGS.adpfGpuReportActualWorkDuration();
    }

    public static boolean adpfPreferPowerEfficiency() {
        return FEATURE_FLAGS.adpfPreferPowerEfficiency();
    }

    public static boolean adpfUseFmqChannel() {
        return FEATURE_FLAGS.adpfUseFmqChannel();
    }

    public static boolean allowPrivateProfile() {
        return FEATURE_FLAGS.allowPrivateProfile();
    }

    public static boolean allowThermalHeadroomThresholds() {
        return FEATURE_FLAGS.allowThermalHeadroomThresholds();
    }

    public static boolean androidOsBuildVanillaIceCream() {
        return FEATURE_FLAGS.androidOsBuildVanillaIceCream();
    }

    public static boolean batteryPartStatusApi() {
        return FEATURE_FLAGS.batteryPartStatusApi();
    }

    public static boolean batterySaverSupportedCheckApi() {
        return FEATURE_FLAGS.batterySaverSupportedCheckApi();
    }

    public static boolean batteryServiceSupportCurrentAdbCommand() {
        return FEATURE_FLAGS.batteryServiceSupportCurrentAdbCommand();
    }

    public static boolean bugreportModeMaxValue() {
        return FEATURE_FLAGS.bugreportModeMaxValue();
    }

    public static boolean disallowCellularNullCiphersRestriction() {
        return FEATURE_FLAGS.disallowCellularNullCiphersRestriction();
    }

    public static boolean messageQueueTailTracking() {
        return FEATURE_FLAGS.messageQueueTailTracking();
    }

    public static boolean removeAppProfilerPssCollection() {
        return FEATURE_FLAGS.removeAppProfilerPssCollection();
    }

    public static boolean securityStateService() {
        return FEATURE_FLAGS.securityStateService();
    }

    public static boolean stateOfHealthPublic() {
        return FEATURE_FLAGS.stateOfHealthPublic();
    }

    public static boolean storageLifetimeApi() {
        return FEATURE_FLAGS.storageLifetimeApi();
    }

    public static boolean strictModeRestrictedNetwork() {
        return FEATURE_FLAGS.strictModeRestrictedNetwork();
    }

    public static boolean telemetryApisFrameworkInitialization() {
        return FEATURE_FLAGS.telemetryApisFrameworkInitialization();
    }
}
