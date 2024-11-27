package android.app.timezonedetector;

/* loaded from: classes.dex */
public interface TimeZoneDetector {
    public static final java.lang.String SHELL_COMMAND_CONFIRM_TIME_ZONE = "confirm_time_zone";
    public static final java.lang.String SHELL_COMMAND_DUMP_METRICS = "dump_metrics";
    public static final java.lang.String SHELL_COMMAND_ENABLE_TELEPHONY_FALLBACK = "enable_telephony_fallback";
    public static final java.lang.String SHELL_COMMAND_GET_TIME_ZONE_STATE = "get_time_zone_state";
    public static final java.lang.String SHELL_COMMAND_HANDLE_LOCATION_ALGORITHM_EVENT = "handle_location_algorithm_event";
    public static final java.lang.String SHELL_COMMAND_IS_AUTO_DETECTION_ENABLED = "is_auto_detection_enabled";
    public static final java.lang.String SHELL_COMMAND_IS_GEO_DETECTION_ENABLED = "is_geo_detection_enabled";
    public static final java.lang.String SHELL_COMMAND_IS_GEO_DETECTION_SUPPORTED = "is_geo_detection_supported";
    public static final java.lang.String SHELL_COMMAND_IS_TELEPHONY_DETECTION_SUPPORTED = "is_telephony_detection_supported";
    public static final java.lang.String SHELL_COMMAND_SERVICE_NAME = "time_zone_detector";
    public static final java.lang.String SHELL_COMMAND_SET_AUTO_DETECTION_ENABLED = "set_auto_detection_enabled";
    public static final java.lang.String SHELL_COMMAND_SET_GEO_DETECTION_ENABLED = "set_geo_detection_enabled";
    public static final java.lang.String SHELL_COMMAND_SET_TIME_ZONE_STATE = "set_time_zone_state_for_tests";
    public static final java.lang.String SHELL_COMMAND_SUGGEST_MANUAL_TIME_ZONE = "suggest_manual_time_zone";
    public static final java.lang.String SHELL_COMMAND_SUGGEST_TELEPHONY_TIME_ZONE = "suggest_telephony_time_zone";

    boolean suggestManualTimeZone(android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion);

    void suggestTelephonyTimeZone(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion);

    static android.app.timezonedetector.ManualTimeZoneSuggestion createManualTimeZoneSuggestion(java.lang.String str, java.lang.String str2) {
        android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion = new android.app.timezonedetector.ManualTimeZoneSuggestion(str);
        manualTimeZoneSuggestion.addDebugInfo(str2);
        return manualTimeZoneSuggestion;
    }
}
