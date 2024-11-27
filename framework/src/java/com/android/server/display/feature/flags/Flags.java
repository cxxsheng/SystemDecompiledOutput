package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.server.display.feature.flags.FeatureFlags FEATURE_FLAGS = new com.android.server.display.feature.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AUTO_BRIGHTNESS_MODES = "com.android.server.display.feature.flags.auto_brightness_modes";
    public static final java.lang.String FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE = "com.android.server.display.feature.flags.back_up_smooth_display_and_force_peak_refresh_rate";
    public static final java.lang.String FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION = "com.android.server.display.feature.flags.brightness_int_range_user_perception";
    public static final java.lang.String FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER = "com.android.server.display.feature.flags.brightness_wear_bedtime_mode_clamper";
    public static final java.lang.String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_1";
    public static final java.lang.String FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2 = "com.android.server.display.feature.flags.enable_adaptive_tone_improvements_2";
    public static final java.lang.String FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING = "com.android.server.display.feature.flags.enable_connected_display_error_handling";
    public static final java.lang.String FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT = "com.android.server.display.feature.flags.enable_connected_display_management";
    public static final java.lang.String FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION = "com.android.server.display.feature.flags.enable_displays_refresh_rates_synchronization";
    public static final java.lang.String FLAG_ENABLE_DISPLAY_OFFLOAD = "com.android.server.display.feature.flags.enable_display_offload";
    public static final java.lang.String FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING = "com.android.server.display.feature.flags.enable_display_resolution_range_voting";
    public static final java.lang.String FLAG_ENABLE_EXTERNAL_VSYNC_PROXIMITY_VOTE = "com.android.server.display.feature.flags.enable_external_vsync_proximity_vote";
    public static final java.lang.String FLAG_ENABLE_HDR_CLAMPER = "com.android.server.display.feature.flags.enable_hdr_clamper";
    public static final java.lang.String FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY = "com.android.server.display.feature.flags.enable_mode_limit_for_external_display";
    public static final java.lang.String FLAG_ENABLE_NBM_CONTROLLER = "com.android.server.display.feature.flags.enable_nbm_controller";
    public static final java.lang.String FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT = "com.android.server.display.feature.flags.enable_port_in_display_layout";
    public static final java.lang.String FLAG_ENABLE_POWER_THROTTLING_CLAMPER = "com.android.server.display.feature.flags.enable_power_throttling_clamper";
    public static final java.lang.String FLAG_ENABLE_USER_PREFERRED_MODE_VOTE = "com.android.server.display.feature.flags.enable_user_preferred_mode_vote";
    public static final java.lang.String FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_light_vote";
    public static final java.lang.String FLAG_ENABLE_VSYNC_LOW_POWER_VOTE = "com.android.server.display.feature.flags.enable_vsync_low_power_vote";
    public static final java.lang.String FLAG_EVEN_DIMMER = "com.android.server.display.feature.flags.even_dimmer";
    public static final java.lang.String FLAG_FAST_HDR_TRANSITIONS = "com.android.server.display.feature.flags.fast_hdr_transitions";
    public static final java.lang.String FLAG_REFRESH_RATE_VOTING_TELEMETRY = "com.android.server.display.feature.flags.refresh_rate_voting_telemetry";
    public static final java.lang.String FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING = "com.android.server.display.feature.flags.sensor_based_brightness_throttling";

    public static boolean autoBrightnessModes() {
        return FEATURE_FLAGS.autoBrightnessModes();
    }

    public static boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return FEATURE_FLAGS.backUpSmoothDisplayAndForcePeakRefreshRate();
    }

    public static boolean brightnessIntRangeUserPerception() {
        return FEATURE_FLAGS.brightnessIntRangeUserPerception();
    }

    public static boolean brightnessWearBedtimeModeClamper() {
        return FEATURE_FLAGS.brightnessWearBedtimeModeClamper();
    }

    public static boolean enableAdaptiveToneImprovements1() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements1();
    }

    public static boolean enableAdaptiveToneImprovements2() {
        return FEATURE_FLAGS.enableAdaptiveToneImprovements2();
    }

    public static boolean enableConnectedDisplayErrorHandling() {
        return FEATURE_FLAGS.enableConnectedDisplayErrorHandling();
    }

    public static boolean enableConnectedDisplayManagement() {
        return FEATURE_FLAGS.enableConnectedDisplayManagement();
    }

    public static boolean enableDisplayOffload() {
        return FEATURE_FLAGS.enableDisplayOffload();
    }

    public static boolean enableDisplayResolutionRangeVoting() {
        return FEATURE_FLAGS.enableDisplayResolutionRangeVoting();
    }

    public static boolean enableDisplaysRefreshRatesSynchronization() {
        return FEATURE_FLAGS.enableDisplaysRefreshRatesSynchronization();
    }

    public static boolean enableExternalVsyncProximityVote() {
        return FEATURE_FLAGS.enableExternalVsyncProximityVote();
    }

    public static boolean enableHdrClamper() {
        return FEATURE_FLAGS.enableHdrClamper();
    }

    public static boolean enableModeLimitForExternalDisplay() {
        return FEATURE_FLAGS.enableModeLimitForExternalDisplay();
    }

    public static boolean enableNbmController() {
        return FEATURE_FLAGS.enableNbmController();
    }

    public static boolean enablePortInDisplayLayout() {
        return FEATURE_FLAGS.enablePortInDisplayLayout();
    }

    public static boolean enablePowerThrottlingClamper() {
        return FEATURE_FLAGS.enablePowerThrottlingClamper();
    }

    public static boolean enableUserPreferredModeVote() {
        return FEATURE_FLAGS.enableUserPreferredModeVote();
    }

    public static boolean enableVsyncLowLightVote() {
        return FEATURE_FLAGS.enableVsyncLowLightVote();
    }

    public static boolean enableVsyncLowPowerVote() {
        return FEATURE_FLAGS.enableVsyncLowPowerVote();
    }

    public static boolean evenDimmer() {
        return FEATURE_FLAGS.evenDimmer();
    }

    public static boolean fastHdrTransitions() {
        return FEATURE_FLAGS.fastHdrTransitions();
    }

    public static boolean refreshRateVotingTelemetry() {
        return FEATURE_FLAGS.refreshRateVotingTelemetry();
    }

    public static boolean sensorBasedBrightnessThrottling() {
        return FEATURE_FLAGS.sensorBasedBrightnessThrottling();
    }
}
