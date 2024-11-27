package android.chre.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static android.chre.flags.FeatureFlags FEATURE_FLAGS = new android.chre.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED = "android.chre.flags.context_hub_callback_uuid_enabled";
    public static final java.lang.String FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS = "android.chre.flags.flag_log_nanoapp_load_metrics";
    public static final java.lang.String FLAG_METRICS_REPORTER_IN_THE_DAEMON = "android.chre.flags.metrics_reporter_in_the_daemon";
    public static final java.lang.String FLAG_RELIABLE_MESSAGE = "android.chre.flags.reliable_message";
    public static final java.lang.String FLAG_RELIABLE_MESSAGE_IMPLEMENTATION = "android.chre.flags.reliable_message_implementation";
    public static final java.lang.String FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT = "android.chre.flags.remove_ap_wakeup_metric_report_limit";
    public static final java.lang.String FLAG_WAIT_FOR_PRELOADED_NANOAPP_START = "android.chre.flags.wait_for_preloaded_nanoapp_start";

    public static boolean contextHubCallbackUuidEnabled() {
        return FEATURE_FLAGS.contextHubCallbackUuidEnabled();
    }

    public static boolean flagLogNanoappLoadMetrics() {
        return FEATURE_FLAGS.flagLogNanoappLoadMetrics();
    }

    public static boolean metricsReporterInTheDaemon() {
        return FEATURE_FLAGS.metricsReporterInTheDaemon();
    }

    public static boolean reliableMessage() {
        return FEATURE_FLAGS.reliableMessage();
    }

    public static boolean reliableMessageImplementation() {
        return FEATURE_FLAGS.reliableMessageImplementation();
    }

    public static boolean removeApWakeupMetricReportLimit() {
        return FEATURE_FLAGS.removeApWakeupMetricReportLimit();
    }

    public static boolean waitForPreloadedNanoappStart() {
        return FEATURE_FLAGS.waitForPreloadedNanoappStart();
    }
}
