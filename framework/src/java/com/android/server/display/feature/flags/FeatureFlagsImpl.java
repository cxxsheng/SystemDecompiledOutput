package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.server.display.feature.flags.FeatureFlags {
    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean autoBrightnessModes() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean backUpSmoothDisplayAndForcePeakRefreshRate() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessIntRangeUserPerception() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean brightnessWearBedtimeModeClamper() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements1() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableAdaptiveToneImprovements2() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayErrorHandling() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableConnectedDisplayManagement() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayOffload() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplayResolutionRangeVoting() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableDisplaysRefreshRatesSynchronization() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableExternalVsyncProximityVote() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableHdrClamper() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableModeLimitForExternalDisplay() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableNbmController() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePortInDisplayLayout() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enablePowerThrottlingClamper() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableUserPreferredModeVote() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowLightVote() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean enableVsyncLowPowerVote() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean evenDimmer() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean fastHdrTransitions() {
        return false;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean refreshRateVotingTelemetry() {
        return true;
    }

    @Override // com.android.server.display.feature.flags.FeatureFlags
    public boolean sensorBasedBrightnessThrottling() {
        return false;
    }
}
