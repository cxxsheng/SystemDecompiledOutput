package com.android.server.display.feature.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean autoBrightnessModes();

    boolean backUpSmoothDisplayAndForcePeakRefreshRate();

    boolean brightnessIntRangeUserPerception();

    boolean brightnessWearBedtimeModeClamper();

    boolean enableAdaptiveToneImprovements1();

    boolean enableAdaptiveToneImprovements2();

    boolean enableConnectedDisplayErrorHandling();

    boolean enableConnectedDisplayManagement();

    boolean enableDisplayOffload();

    boolean enableDisplayResolutionRangeVoting();

    boolean enableDisplaysRefreshRatesSynchronization();

    boolean enableExternalVsyncProximityVote();

    boolean enableHdrClamper();

    boolean enableModeLimitForExternalDisplay();

    boolean enableNbmController();

    boolean enablePortInDisplayLayout();

    boolean enablePowerThrottlingClamper();

    boolean enableUserPreferredModeVote();

    boolean enableVsyncLowLightVote();

    boolean enableVsyncLowPowerVote();

    boolean evenDimmer();

    boolean fastHdrTransitions();

    boolean refreshRateVotingTelemetry();

    boolean sensorBasedBrightnessThrottling();
}
