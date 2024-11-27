package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.server.display.feature.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_AUTO_BRIGHTNESS_MODES, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_OFFLOAD, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_EXTERNAL_VSYNC_PROXIMITY_VOTE, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_HDR_CLAMPER, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_NBM_CONTROLLER, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_EVEN_DIMMER, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_FAST_HDR_TRANSITIONS, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY, false), java.util.Map.entry(com.android.server.display.feature.flags.Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.display.feature.flags.Flags.FLAG_AUTO_BRIGHTNESS_MODES, com.android.server.display.feature.flags.Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE, com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION, com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_OFFLOAD, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_EXTERNAL_VSYNC_PROXIMITY_VOTE, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_HDR_CLAMPER, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_NBM_CONTROLLER, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE, com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE, com.android.server.display.feature.flags.Flags.FLAG_EVEN_DIMMER, com.android.server.display.feature.flags.Flags.FLAG_FAST_HDR_TRANSITIONS, com.android.server.display.feature.flags.Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY, com.android.server.display.feature.flags.Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModes() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_AUTO_BRIGHTNESS_MODES);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_BACK_UP_SMOOTH_DISPLAY_AND_FORCE_PEAK_REFRESH_RATE);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessIntRangeUserPerception() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_INT_RANGE_USER_PERCEPTION);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessWearBedtimeModeClamper() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_BRIGHTNESS_WEAR_BEDTIME_MODE_CLAMPER);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements1() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_1);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements2() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_ADAPTIVE_TONE_IMPROVEMENTS_2);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayErrorHandling() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_ERROR_HANDLING);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayManagement() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_CONNECTED_DISPLAY_MANAGEMENT);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayOffload() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_OFFLOAD);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayResolutionRangeVoting() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAY_RESOLUTION_RANGE_VOTING);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplaysRefreshRatesSynchronization() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_DISPLAYS_REFRESH_RATES_SYNCHRONIZATION);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableExternalVsyncProximityVote() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_EXTERNAL_VSYNC_PROXIMITY_VOTE);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHdrClamper() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_HDR_CLAMPER);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableModeLimitForExternalDisplay() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_MODE_LIMIT_FOR_EXTERNAL_DISPLAY);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableNbmController() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_NBM_CONTROLLER);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePortInDisplayLayout() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_PORT_IN_DISPLAY_LAYOUT);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePowerThrottlingClamper() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_POWER_THROTTLING_CLAMPER);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserPreferredModeVote() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_USER_PREFERRED_MODE_VOTE);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowLightVote() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_LIGHT_VOTE);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowPowerVote() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_ENABLE_VSYNC_LOW_POWER_VOTE);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean evenDimmer() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_EVEN_DIMMER);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean fastHdrTransitions() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_FAST_HDR_TRANSITIONS);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refreshRateVotingTelemetry() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_REFRESH_RATE_VOTING_TELEMETRY);
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean sensorBasedBrightnessThrottling() {
        return getValue(com.android.server.display.feature.flags.Flags.FLAG_SENSOR_BASED_BRIGHTNESS_THROTTLING);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
