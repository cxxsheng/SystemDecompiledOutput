package com.android.server.display.feature;

/* loaded from: classes.dex */
public class DisplayManagerFlags {
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mAdaptiveToneImprovements1;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mAdaptiveToneImprovements2;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mAutoBrightnessModesFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mBrightnessIntRangeUserPerceptionFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mBrightnessWearBedtimeModeClamperFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mConnectedDisplayErrorHandlingFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mConnectedDisplayManagementFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mDisplayOffloadFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mEvenDimmerFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mExternalDisplayLimitModeState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mFastHdrTransitions;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mHdrClamperFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mNbmControllerFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mPortInDisplayLayoutFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mPowerThrottlingClamperFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mRefreshRateVotingTelemetry;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mSensorBasedBrightnessThrottling;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mSmallAreaDetectionFlagState;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mVsyncLowLightVote;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mVsyncLowPowerVote;
    private final com.android.server.display.feature.DisplayManagerFlags.FlagState mVsyncProximityVote;
    private static final java.lang.String TAG = "DisplayManagerFlags";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    public DisplayManagerFlags() {
        this.mPortInDisplayLayoutFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_port_in_display_layout", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enablePortInDisplayLayout());
            }
        });
        this.mConnectedDisplayManagementFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_connected_display_management", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableConnectedDisplayManagement());
            }
        });
        this.mNbmControllerFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_nbm_controller", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableNbmController());
            }
        });
        this.mHdrClamperFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_hdr_clamper", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableHdrClamper());
            }
        });
        this.mAdaptiveToneImprovements1 = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_adaptive_tone_improvements_1", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableAdaptiveToneImprovements1());
            }
        });
        this.mAdaptiveToneImprovements2 = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_adaptive_tone_improvements_2", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableAdaptiveToneImprovements2());
            }
        });
        this.mDisplayOffloadFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_display_offload", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableDisplayOffload());
            }
        });
        this.mExternalDisplayLimitModeState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_mode_limit_for_external_display", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableModeLimitForExternalDisplay());
            }
        });
        this.mConnectedDisplayErrorHandlingFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_connected_display_error_handling", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableConnectedDisplayErrorHandling());
            }
        });
        this.mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.back_up_smooth_display_and_force_peak_refresh_rate", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.backUpSmoothDisplayAndForcePeakRefreshRate());
            }
        });
        this.mPowerThrottlingClamperFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_power_throttling_clamper", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enablePowerThrottlingClamper());
            }
        });
        this.mEvenDimmerFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.even_dimmer", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.evenDimmer());
            }
        });
        this.mSmallAreaDetectionFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.graphics.surfaceflinger.flags.enable_small_area_detection", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.graphics.surfaceflinger.flags.Flags.enableSmallAreaDetection());
            }
        });
        this.mBrightnessIntRangeUserPerceptionFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.brightness_int_range_user_perception", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.brightnessIntRangeUserPerception());
            }
        });
        this.mVsyncProximityVote = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_external_vsync_proximity_vote", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableExternalVsyncProximityVote());
            }
        });
        this.mVsyncLowPowerVote = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_vsync_low_power_vote", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableVsyncLowPowerVote());
            }
        });
        this.mVsyncLowLightVote = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.enable_vsync_low_light_vote", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.enableVsyncLowLightVote());
            }
        });
        this.mBrightnessWearBedtimeModeClamperFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.brightness_wear_bedtime_mode_clamper", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.brightnessWearBedtimeModeClamper());
            }
        });
        this.mAutoBrightnessModesFlagState = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.auto_brightness_modes", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.autoBrightnessModes());
            }
        });
        this.mFastHdrTransitions = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.fast_hdr_transitions", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.fastHdrTransitions());
            }
        });
        this.mRefreshRateVotingTelemetry = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.refresh_rate_voting_telemetry", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.refreshRateVotingTelemetry());
            }
        });
        this.mSensorBasedBrightnessThrottling = new com.android.server.display.feature.DisplayManagerFlags.FlagState("com.android.server.display.feature.flags.sensor_based_brightness_throttling", new java.util.function.Supplier() { // from class: com.android.server.display.feature.DisplayManagerFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Boolean.valueOf(com.android.server.display.feature.flags.Flags.sensorBasedBrightnessThrottling());
            }
        });
    }

    public boolean isPortInDisplayLayoutEnabled() {
        return this.mPortInDisplayLayoutFlagState.isEnabled();
    }

    public boolean isConnectedDisplayManagementEnabled() {
        return this.mConnectedDisplayManagementFlagState.isEnabled();
    }

    public boolean isNbmControllerEnabled() {
        return this.mNbmControllerFlagState.isEnabled();
    }

    public boolean isHdrClamperEnabled() {
        return this.mHdrClamperFlagState.isEnabled();
    }

    public boolean isPowerThrottlingClamperEnabled() {
        return this.mPowerThrottlingClamperFlagState.isEnabled();
    }

    public boolean isAdaptiveTone1Enabled() {
        return this.mAdaptiveToneImprovements1.isEnabled();
    }

    public boolean isAdaptiveTone2Enabled() {
        return this.mAdaptiveToneImprovements2.isEnabled();
    }

    public boolean isDisplayResolutionRangeVotingEnabled() {
        return isExternalDisplayLimitModeEnabled();
    }

    public boolean isUserPreferredModeVoteEnabled() {
        return isExternalDisplayLimitModeEnabled();
    }

    public boolean isExternalDisplayLimitModeEnabled() {
        return this.mExternalDisplayLimitModeState.isEnabled();
    }

    public boolean isDisplaysRefreshRatesSynchronizationEnabled() {
        return isExternalDisplayLimitModeEnabled();
    }

    public boolean isDisplayOffloadEnabled() {
        return this.mDisplayOffloadFlagState.isEnabled();
    }

    public boolean isConnectedDisplayErrorHandlingEnabled() {
        return this.mConnectedDisplayErrorHandlingFlagState.isEnabled();
    }

    public boolean isBackUpSmoothDisplayAndForcePeakRefreshRateEnabled() {
        return this.mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState.isEnabled();
    }

    public boolean isEvenDimmerEnabled() {
        return this.mEvenDimmerFlagState.isEnabled();
    }

    public boolean isSmallAreaDetectionEnabled() {
        return this.mSmallAreaDetectionFlagState.isEnabled();
    }

    public boolean isBrightnessIntRangeUserPerceptionEnabled() {
        return this.mBrightnessIntRangeUserPerceptionFlagState.isEnabled();
    }

    public boolean isVsyncProximityVoteEnabled() {
        return this.mVsyncProximityVote.isEnabled();
    }

    public boolean isVsyncLowPowerVoteEnabled() {
        return this.mVsyncLowPowerVote.isEnabled();
    }

    public boolean isVsyncLowLightVoteEnabled() {
        return this.mVsyncLowLightVote.isEnabled();
    }

    public boolean isBrightnessWearBedtimeModeClamperEnabled() {
        return this.mBrightnessWearBedtimeModeClamperFlagState.isEnabled();
    }

    public boolean areAutoBrightnessModesEnabled() {
        return this.mAutoBrightnessModesFlagState.isEnabled();
    }

    public boolean isFastHdrTransitionsEnabled() {
        return this.mFastHdrTransitions.isEnabled();
    }

    public boolean isRefreshRateVotingTelemetryEnabled() {
        return this.mRefreshRateVotingTelemetry.isEnabled();
    }

    public boolean isSensorBasedBrightnessThrottlingEnabled() {
        return this.mSensorBasedBrightnessThrottling.isEnabled();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("DisplayManagerFlags:");
        printWriter.println(" " + this.mAdaptiveToneImprovements1);
        printWriter.println(" " + this.mAdaptiveToneImprovements2);
        printWriter.println(" " + this.mBackUpSmoothDisplayAndForcePeakRefreshRateFlagState);
        printWriter.println(" " + this.mConnectedDisplayErrorHandlingFlagState);
        printWriter.println(" " + this.mConnectedDisplayManagementFlagState);
        printWriter.println(" " + this.mDisplayOffloadFlagState);
        printWriter.println(" " + this.mExternalDisplayLimitModeState);
        printWriter.println(" " + this.mHdrClamperFlagState);
        printWriter.println(" " + this.mNbmControllerFlagState);
        printWriter.println(" " + this.mPowerThrottlingClamperFlagState);
        printWriter.println(" " + this.mSmallAreaDetectionFlagState);
        printWriter.println(" " + this.mBrightnessIntRangeUserPerceptionFlagState);
        printWriter.println(" " + this.mVsyncProximityVote);
        printWriter.println(" " + this.mBrightnessWearBedtimeModeClamperFlagState);
        printWriter.println(" " + this.mAutoBrightnessModesFlagState);
        printWriter.println(" " + this.mFastHdrTransitions);
        printWriter.println(" " + this.mRefreshRateVotingTelemetry);
        printWriter.println(" " + this.mSensorBasedBrightnessThrottling);
    }

    private static class FlagState {
        private boolean mEnabled;
        private boolean mEnabledSet;
        private final java.util.function.Supplier<java.lang.Boolean> mFlagFunction;
        private final java.lang.String mName;

        private FlagState(java.lang.String str, java.util.function.Supplier<java.lang.Boolean> supplier) {
            this.mName = str;
            this.mFlagFunction = supplier;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabled() {
            if (this.mEnabledSet) {
                if (com.android.server.display.feature.DisplayManagerFlags.DEBUG) {
                    android.util.Slog.d(com.android.server.display.feature.DisplayManagerFlags.TAG, this.mName + ": mEnabled. Recall = " + this.mEnabled);
                }
                return this.mEnabled;
            }
            this.mEnabled = flagOrSystemProperty(this.mFlagFunction, this.mName);
            if (com.android.server.display.feature.DisplayManagerFlags.DEBUG) {
                android.util.Slog.d(com.android.server.display.feature.DisplayManagerFlags.TAG, this.mName + ": mEnabled. Flag value = " + this.mEnabled);
            }
            this.mEnabledSet = true;
            return this.mEnabled;
        }

        private boolean flagOrSystemProperty(java.util.function.Supplier<java.lang.Boolean> supplier, java.lang.String str) {
            boolean booleanValue = supplier.get().booleanValue();
            if (android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) {
                return android.os.SystemProperties.getBoolean("persist.sys." + str + "-override", booleanValue);
            }
            return booleanValue;
        }

        public java.lang.String toString() {
            int length = this.mName.length();
            return android.text.TextUtils.substring(this.mName, 41, length) + ": " + android.text.TextUtils.formatSimple("%" + (93 - length) + "s%s", new java.lang.Object[]{" ", java.lang.Boolean.valueOf(isEnabled())}) + " (def:" + this.mFlagFunction.get() + ")";
        }
    }
}
