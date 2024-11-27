package com.android.server.display.mode;

/* loaded from: classes.dex */
public class DisplayModeDirector {
    private static final float FLOAT_TOLERANCE = 0.01f;
    private static final int MSG_DEFAULT_PEAK_REFRESH_RATE_CHANGED = 3;
    private static final int MSG_HIGH_BRIGHTNESS_THRESHOLDS_CHANGED = 6;
    private static final int MSG_LOW_BRIGHTNESS_THRESHOLDS_CHANGED = 2;
    private static final int MSG_REFRESH_RATE_IN_HBM_HDR_CHANGED = 8;
    private static final int MSG_REFRESH_RATE_IN_HBM_SUNLIGHT_CHANGED = 7;
    private static final int MSG_REFRESH_RATE_IN_HIGH_ZONE_CHANGED = 5;
    private static final int MSG_REFRESH_RATE_IN_LOW_ZONE_CHANGED = 4;
    private static final int MSG_REFRESH_RATE_RANGE_CHANGED = 1;
    public static final float SYNCHRONIZED_REFRESH_RATE_TARGET = 60.0f;
    public static final float SYNCHRONIZED_REFRESH_RATE_TOLERANCE = 1.0f;
    private static final java.lang.String TAG = "DisplayModeDirector";
    private boolean mAlwaysRespectAppRequest;
    private final com.android.server.display.mode.DisplayModeDirector.AppRequestObserver mAppRequestObserver;
    private com.android.server.display.mode.DisplayModeDirector.BrightnessObserver mBrightnessObserver;
    private final com.android.server.display.feature.DeviceConfigParameterProvider mConfigParameterProvider;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig mDefaultDisplayDeviceConfig;
    private android.util.SparseArray<android.view.Display.Mode> mDefaultModeByDisplay;
    private com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener mDesiredDisplayModeSpecsListener;
    private final com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
    private final com.android.server.display.mode.DisplayModeDirector.DisplayObserver mDisplayObserver;
    private final boolean mDvrrSupported;
    private final com.android.server.display.mode.DisplayModeDirector.DisplayModeDirectorHandler mHandler;
    private final com.android.server.display.mode.DisplayModeDirector.HbmObserver mHbmObserver;
    private final com.android.server.display.mode.DisplayModeDirector.Injector mInjector;
    private final boolean mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled;
    private final boolean mIsDisplayResolutionRangeVotingEnabled;
    private final boolean mIsDisplaysRefreshRatesSynchronizationEnabled;
    private final boolean mIsExternalDisplayLimitModeEnabled;
    private final boolean mIsUserPreferredModeVoteEnabled;
    private final java.lang.Object mLock;
    private boolean mLoggingEnabled;
    private int mModeSwitchingType;
    private final com.android.server.display.mode.DisplayModeDirector.SensorObserver mSensorObserver;
    private final com.android.server.display.mode.DisplayModeDirector.SettingsObserver mSettingsObserver;
    private final com.android.server.display.mode.SkinThermalStatusObserver mSkinThermalStatusObserver;
    private android.util.SparseArray<android.view.Display.Mode[]> mSupportedModesByDisplay;
    private final boolean mSupportsFrameRateOverride;
    private final com.android.server.display.mode.DisplayModeDirector.UdfpsObserver mUdfpsObserver;

    @android.annotation.Nullable
    private final com.android.server.display.mode.VotesStatsReporter mVotesStatsReporter;
    private final com.android.server.display.mode.VotesStorage mVotesStorage;

    public interface DesiredDisplayModeSpecsListener {
        void onDesiredDisplayModeSpecsChanged();
    }

    interface Injector {
        public static final android.net.Uri PEAK_REFRESH_RATE_URI = android.provider.Settings.System.getUriFor("peak_refresh_rate");
        public static final android.net.Uri MIN_REFRESH_RATE_URI = android.provider.Settings.System.getUriFor("min_refresh_rate");

        android.hardware.display.BrightnessInfo getBrightnessInfo(int i);

        @android.annotation.NonNull
        android.provider.DeviceConfigInterface getDeviceConfig();

        android.view.Display getDisplay(int i);

        boolean getDisplayInfo(int i, android.view.DisplayInfo displayInfo);

        android.hardware.display.DisplayManagerInternal getDisplayManagerInternal();

        android.view.Display[] getDisplays();

        com.android.server.sensors.SensorManagerInternal getSensorManagerInternal();

        com.android.server.statusbar.StatusBarManagerInternal getStatusBarManagerInternal();

        @android.annotation.Nullable
        com.android.server.display.mode.VotesStatsReporter getVotesStatsReporter(boolean z);

        boolean isDozeState(android.view.Display display);

        void registerDisplayListener(@android.annotation.NonNull android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler);

        void registerDisplayListener(@android.annotation.NonNull android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler, long j);

        void registerMinRefreshRateObserver(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver);

        void registerPeakRefreshRateObserver(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver);

        boolean registerThermalServiceListener(android.os.IThermalEventListener iThermalEventListener);

        boolean supportsFrameRateOverride();

        void unregisterThermalServiceListener(android.os.IThermalEventListener iThermalEventListener);
    }

    public DisplayModeDirector(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this(context, handler, new com.android.server.display.mode.DisplayModeDirector.RealInjector(context), displayManagerFlags);
    }

    public DisplayModeDirector(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.display.mode.DisplayModeDirector.Injector injector, @android.annotation.NonNull com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mLock = new java.lang.Object();
        this.mModeSwitchingType = 1;
        this.mDvrrSupported = context.getResources().getBoolean(android.R.bool.config_supportsBubble);
        this.mIsDisplayResolutionRangeVotingEnabled = displayManagerFlags.isDisplayResolutionRangeVotingEnabled();
        this.mIsUserPreferredModeVoteEnabled = displayManagerFlags.isUserPreferredModeVoteEnabled();
        this.mIsExternalDisplayLimitModeEnabled = displayManagerFlags.isExternalDisplayLimitModeEnabled();
        this.mIsDisplaysRefreshRatesSynchronizationEnabled = displayManagerFlags.isDisplaysRefreshRatesSynchronizationEnabled();
        this.mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled = displayManagerFlags.isBackUpSmoothDisplayAndForcePeakRefreshRateEnabled();
        this.mContext = context;
        this.mHandler = new com.android.server.display.mode.DisplayModeDirector.DisplayModeDirectorHandler(handler.getLooper());
        this.mInjector = injector;
        this.mVotesStatsReporter = injector.getVotesStatsReporter(displayManagerFlags.isRefreshRateVotingTelemetryEnabled());
        this.mSupportedModesByDisplay = new android.util.SparseArray<>();
        this.mDefaultModeByDisplay = new android.util.SparseArray<>();
        this.mAppRequestObserver = new com.android.server.display.mode.DisplayModeDirector.AppRequestObserver();
        this.mConfigParameterProvider = new com.android.server.display.feature.DeviceConfigParameterProvider(injector.getDeviceConfig());
        this.mDeviceConfigDisplaySettings = new com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings();
        this.mSettingsObserver = new com.android.server.display.mode.DisplayModeDirector.SettingsObserver(context, handler, this.mDvrrSupported, displayManagerFlags);
        this.mBrightnessObserver = new com.android.server.display.mode.DisplayModeDirector.BrightnessObserver(context, handler, injector, this.mDvrrSupported, displayManagerFlags);
        this.mDefaultDisplayDeviceConfig = null;
        this.mUdfpsObserver = new com.android.server.display.mode.DisplayModeDirector.UdfpsObserver();
        this.mVotesStorage = new com.android.server.display.mode.VotesStorage(new com.android.server.display.mode.VotesStorage.Listener() { // from class: com.android.server.display.mode.DisplayModeDirector$$ExternalSyntheticLambda0
            @Override // com.android.server.display.mode.VotesStorage.Listener
            public final void onChanged() {
                com.android.server.display.mode.DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
            }
        }, this.mVotesStatsReporter);
        this.mDisplayObserver = new com.android.server.display.mode.DisplayModeDirector.DisplayObserver(context, handler, this.mVotesStorage);
        this.mSensorObserver = new com.android.server.display.mode.DisplayModeDirector.SensorObserver(context, this.mVotesStorage, injector);
        this.mSkinThermalStatusObserver = new com.android.server.display.mode.SkinThermalStatusObserver(injector, this.mVotesStorage);
        this.mHbmObserver = new com.android.server.display.mode.DisplayModeDirector.HbmObserver(injector, this.mVotesStorage, com.android.internal.os.BackgroundThread.getHandler(), this.mDeviceConfigDisplaySettings);
        this.mAlwaysRespectAppRequest = false;
        this.mSupportsFrameRateOverride = injector.supportsFrameRateOverride();
    }

    public void start(android.hardware.SensorManager sensorManager) {
        this.mDisplayObserver.observe();
        this.mSettingsObserver.observe();
        this.mBrightnessObserver.observe(sensorManager);
        this.mSensorObserver.observe();
        this.mHbmObserver.observe();
        this.mSkinThermalStatusObserver.observe();
        synchronized (this.mLock) {
            notifyDesiredDisplayModeSpecsChangedLocked();
        }
    }

    public void onBootCompleted() {
        this.mUdfpsObserver.observe();
    }

    public void setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return;
        }
        this.mLoggingEnabled = z;
        this.mBrightnessObserver.setLoggingEnabled(z);
        this.mSkinThermalStatusObserver.setLoggingEnabled(z);
        this.mVotesStorage.setLoggingEnabled(z);
    }

    @android.annotation.NonNull
    public com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(int i) {
        int i2;
        int i3;
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<com.android.server.display.mode.Vote> votes = this.mVotesStorage.getVotes(i);
                android.view.Display.Mode[] modeArr = this.mSupportedModesByDisplay.get(i);
                android.view.Display.Mode mode = this.mDefaultModeByDisplay.get(i);
                if (modeArr == null || mode == null) {
                    android.util.Slog.e(TAG, "Asked about unknown display, returning empty display mode specs!(id=" + i + ")");
                    return new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs();
                }
                java.util.List<android.view.Display.Mode> arrayList = new java.util.ArrayList<>();
                arrayList.add(mode);
                com.android.server.display.mode.VoteSummary voteSummary = new com.android.server.display.mode.VoteSummary(this.mIsDisplayResolutionRangeVotingEnabled, this.mDvrrSupported, this.mLoggingEnabled, this.mSupportsFrameRateOverride);
                if (!this.mAlwaysRespectAppRequest) {
                    i2 = 17;
                    i3 = 0;
                } else {
                    i2 = 7;
                    i3 = 5;
                }
                while (true) {
                    if (i3 > i2) {
                        break;
                    }
                    voteSummary.applyVotes(votes, i3, i2);
                    voteSummary.adjustSize(mode, modeArr);
                    arrayList = voteSummary.filterModes(modeArr);
                    if (!arrayList.isEmpty()) {
                        if (this.mLoggingEnabled) {
                            android.util.Slog.w(TAG, "Found available modes=" + arrayList + " with lowest priority considered " + com.android.server.display.mode.Vote.priorityToString(i3) + " and summary: " + voteSummary);
                        }
                    } else {
                        if (this.mLoggingEnabled) {
                            android.util.Slog.w(TAG, "Couldn't find available modes with lowest priority set to " + com.android.server.display.mode.Vote.priorityToString(i3) + " and with the following summary: " + voteSummary);
                        }
                        i3++;
                    }
                }
                com.android.server.display.mode.VoteSummary voteSummary2 = new com.android.server.display.mode.VoteSummary(this.mIsDisplayResolutionRangeVotingEnabled, this.mDvrrSupported, this.mLoggingEnabled, this.mSupportsFrameRateOverride);
                voteSummary2.applyVotes(votes, 5, 17);
                voteSummary2.limitRefreshRanges(voteSummary);
                android.view.Display.Mode selectBaseMode = voteSummary.selectBaseMode(arrayList, mode);
                if (this.mVotesStatsReporter != null) {
                    this.mVotesStatsReporter.reportVotesActivated(i, i3, selectBaseMode, votes);
                }
                if (selectBaseMode == null) {
                    android.util.Slog.w(TAG, "Can't find a set of allowed modes which satisfies the votes. Falling back to the default mode. Display = " + i + ", votes = " + votes + ", supported modes = " + java.util.Arrays.toString(modeArr));
                    float refreshRate = mode.getRefreshRate();
                    android.view.SurfaceControl.RefreshRateRange refreshRateRange = new android.view.SurfaceControl.RefreshRateRange(refreshRate, refreshRate);
                    android.view.SurfaceControl.RefreshRateRanges refreshRateRanges = new android.view.SurfaceControl.RefreshRateRanges(refreshRateRange, refreshRateRange);
                    return new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs(mode.getModeId(), false, refreshRateRanges, refreshRateRanges);
                }
                boolean z = this.mModeSwitchingType == 0 || this.mModeSwitchingType == 3;
                if (z || voteSummary.disableRefreshRateSwitching) {
                    float refreshRate2 = selectBaseMode.getRefreshRate();
                    voteSummary.disableModeSwitching(refreshRate2);
                    if (z) {
                        voteSummary2.disableModeSwitching(refreshRate2);
                        voteSummary.disableRenderRateSwitching(refreshRate2);
                        if (this.mModeSwitchingType == 0) {
                            voteSummary2.disableRenderRateSwitching(refreshRate2);
                        }
                    }
                }
                boolean z2 = this.mModeSwitchingType == 2;
                if (this.mDisplayObserver.isExternalDisplayLocked(i)) {
                    voteSummary.maxRenderFrameRate = java.lang.Math.max(selectBaseMode.getRefreshRate(), voteSummary.maxRenderFrameRate);
                    voteSummary2.maxRenderFrameRate = java.lang.Math.max(selectBaseMode.getRefreshRate(), voteSummary2.maxRenderFrameRate);
                }
                return new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs(selectBaseMode.getModeId(), z2, new android.view.SurfaceControl.RefreshRateRanges(new android.view.SurfaceControl.RefreshRateRange(voteSummary.minPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate), new android.view.SurfaceControl.RefreshRateRange(voteSummary.minRenderFrameRate, voteSummary.maxRenderFrameRate)), new android.view.SurfaceControl.RefreshRateRanges(new android.view.SurfaceControl.RefreshRateRange(voteSummary2.minPhysicalRefreshRate, voteSummary2.maxPhysicalRefreshRate), new android.view.SurfaceControl.RefreshRateRange(voteSummary2.minRenderFrameRate, voteSummary2.maxRenderFrameRate)));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public com.android.server.display.mode.DisplayModeDirector.AppRequestObserver getAppRequestObserver() {
        return this.mAppRequestObserver;
    }

    public void setDesiredDisplayModeSpecsListener(@android.annotation.Nullable com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener desiredDisplayModeSpecsListener) {
        synchronized (this.mLock) {
            this.mDesiredDisplayModeSpecsListener = desiredDisplayModeSpecsListener;
        }
    }

    public void defaultDisplayDeviceUpdated(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        synchronized (this.mLock) {
            this.mDefaultDisplayDeviceConfig = displayDeviceConfig;
            this.mSettingsObserver.setRefreshRates(displayDeviceConfig, true);
            this.mBrightnessObserver.updateBlockingZoneThresholds(displayDeviceConfig, true);
            this.mBrightnessObserver.reloadLightSensor(displayDeviceConfig);
            this.mHbmObserver.setupHdrRefreshRates(displayDeviceConfig);
        }
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mAlwaysRespectAppRequest != z) {
                    this.mAlwaysRespectAppRequest = z;
                    notifyDesiredDisplayModeSpecsChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAlwaysRespectAppRequest;
        }
        return z;
    }

    public void setModeSwitchingType(int i) {
        synchronized (this.mLock) {
            try {
                if (i != this.mModeSwitchingType) {
                    this.mModeSwitchingType = i;
                    notifyDesiredDisplayModeSpecsChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getModeSwitchingType() {
        int i;
        synchronized (this.mLock) {
            i = this.mModeSwitchingType;
        }
        return i;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.display.mode.Vote getVote(int i, int i2) {
        return this.mVotesStorage.getVotes(i).get(i2);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println(TAG);
        synchronized (this.mLock) {
            try {
                printWriter.println("  mSupportedModesByDisplay:");
                for (int i = 0; i < this.mSupportedModesByDisplay.size(); i++) {
                    printWriter.println("    " + this.mSupportedModesByDisplay.keyAt(i) + " -> " + java.util.Arrays.toString(this.mSupportedModesByDisplay.valueAt(i)));
                }
                printWriter.println("  mDefaultModeByDisplay:");
                for (int i2 = 0; i2 < this.mDefaultModeByDisplay.size(); i2++) {
                    printWriter.println("    " + this.mDefaultModeByDisplay.keyAt(i2) + " -> " + this.mDefaultModeByDisplay.valueAt(i2));
                }
                printWriter.println("  mModeSwitchingType: " + switchingTypeToString(this.mModeSwitchingType));
                printWriter.println("  mAlwaysRespectAppRequest: " + this.mAlwaysRespectAppRequest);
                this.mSettingsObserver.dumpLocked(printWriter);
                this.mAppRequestObserver.dumpLocked(printWriter);
                this.mBrightnessObserver.dumpLocked(printWriter);
                this.mUdfpsObserver.dumpLocked(printWriter);
                this.mHbmObserver.dumpLocked(printWriter);
                this.mSkinThermalStatusObserver.dumpLocked(printWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mVotesStorage.dump(printWriter);
        this.mSensorObserver.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public float getMaxRefreshRateLocked(int i) {
        android.view.Display.Mode[] modeArr = this.mSupportedModesByDisplay.get(i);
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        for (android.view.Display.Mode mode : modeArr) {
            if (mode.getRefreshRate() > f) {
                f = mode.getRefreshRate();
            }
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDesiredDisplayModeSpecsChangedLocked() {
        if (this.mDesiredDisplayModeSpecsListener != null && !this.mHandler.hasMessages(1)) {
            this.mHandler.obtainMessage(1, this.mDesiredDisplayModeSpecsListener).sendToTarget();
        }
    }

    private static java.lang.String switchingTypeToString(int i) {
        switch (i) {
            case 0:
                return "SWITCHING_TYPE_NONE";
            case 1:
                return "SWITCHING_TYPE_WITHIN_GROUPS";
            case 2:
                return "SWITCHING_TYPE_ACROSS_AND_WITHIN_GROUPS";
            case 3:
                return "SWITCHING_TYPE_RENDER_FRAME_RATE_ONLY";
            default:
                return "Unknown SwitchingType " + i;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectSupportedModesByDisplay(android.util.SparseArray<android.view.Display.Mode[]> sparseArray) {
        this.mSupportedModesByDisplay = sparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectDefaultModeByDisplay(android.util.SparseArray<android.view.Display.Mode> sparseArray) {
        this.mDefaultModeByDisplay = sparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectVotesByDisplay(android.util.SparseArray<android.util.SparseArray<com.android.server.display.mode.Vote>> sparseArray) {
        this.mVotesStorage.injectVotesByDisplay(sparseArray);
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectBrightnessObserver(com.android.server.display.mode.DisplayModeDirector.BrightnessObserver brightnessObserver) {
        this.mBrightnessObserver = brightnessObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver getBrightnessObserver() {
        return this.mBrightnessObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.SettingsObserver getSettingsObserver() {
        return this.mSettingsObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.UdfpsObserver getUdpfsObserver() {
        return this.mUdfpsObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.HbmObserver getHbmObserver() {
        return this.mHbmObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.DisplayObserver getDisplayObserver() {
        return this.mDisplayObserver;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs getDesiredDisplayModeSpecsWithInjectedFpsSettings(float f, float f2, float f3) {
        com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs;
        synchronized (this.mLock) {
            this.mSettingsObserver.updateRefreshRateSettingLocked(f, f2, f3, 0);
            desiredDisplayModeSpecs = getDesiredDisplayModeSpecs(0);
        }
        return desiredDisplayModeSpecs;
    }

    private final class DisplayModeDirectorHandler extends android.os.Handler {
        DisplayModeDirectorHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    ((com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener) message.obj).onDesiredDisplayModeSpecsChanged();
                    break;
                case 2:
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigLowBrightnessThresholdsChanged((float[]) pair.first, (float[]) pair.second);
                    break;
                case 3:
                    com.android.server.display.mode.DisplayModeDirector.this.mSettingsObserver.onDeviceConfigDefaultPeakRefreshRateChanged((java.lang.Float) message.obj);
                    break;
                case 4:
                    com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigRefreshRateInLowZoneChanged(message.arg1);
                    break;
                case 5:
                    com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigRefreshRateInHighZoneChanged(message.arg1);
                    break;
                case 6:
                    android.util.Pair pair2 = (android.util.Pair) message.obj;
                    com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onDeviceConfigHighBrightnessThresholdsChanged((float[]) pair2.first, (float[]) pair2.second);
                    break;
                case 7:
                    com.android.server.display.mode.DisplayModeDirector.this.mHbmObserver.onDeviceConfigRefreshRateInHbmSunlightChanged(message.arg1);
                    break;
                case 8:
                    com.android.server.display.mode.DisplayModeDirector.this.mHbmObserver.onDeviceConfigRefreshRateInHbmHdrChanged(message.arg1);
                    break;
            }
        }
    }

    public static final class DesiredDisplayModeSpecs {
        public boolean allowGroupSwitching;
        public final android.view.SurfaceControl.RefreshRateRanges appRequest;
        public int baseModeId;
        public final android.view.SurfaceControl.RefreshRateRanges primary;

        public DesiredDisplayModeSpecs() {
            this.primary = new android.view.SurfaceControl.RefreshRateRanges();
            this.appRequest = new android.view.SurfaceControl.RefreshRateRanges();
        }

        public DesiredDisplayModeSpecs(int i, boolean z, @android.annotation.NonNull android.view.SurfaceControl.RefreshRateRanges refreshRateRanges, @android.annotation.NonNull android.view.SurfaceControl.RefreshRateRanges refreshRateRanges2) {
            this.baseModeId = i;
            this.allowGroupSwitching = z;
            this.primary = refreshRateRanges;
            this.appRequest = refreshRateRanges2;
        }

        public java.lang.String toString() {
            return java.lang.String.format("baseModeId=%d allowGroupSwitching=%b primary=%s appRequest=%s", java.lang.Integer.valueOf(this.baseModeId), java.lang.Boolean.valueOf(this.allowGroupSwitching), this.primary.toString(), this.appRequest.toString());
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs)) {
                return false;
            }
            com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs = (com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs) obj;
            return this.baseModeId == desiredDisplayModeSpecs.baseModeId && this.allowGroupSwitching == desiredDisplayModeSpecs.allowGroupSwitching && this.primary.equals(desiredDisplayModeSpecs.primary) && this.appRequest.equals(desiredDisplayModeSpecs.appRequest);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.baseModeId), java.lang.Boolean.valueOf(this.allowGroupSwitching), this.primary, this.appRequest);
        }

        public void copyFrom(com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            this.baseModeId = desiredDisplayModeSpecs.baseModeId;
            this.allowGroupSwitching = desiredDisplayModeSpecs.allowGroupSwitching;
            this.primary.physical.min = desiredDisplayModeSpecs.primary.physical.min;
            this.primary.physical.max = desiredDisplayModeSpecs.primary.physical.max;
            this.primary.render.min = desiredDisplayModeSpecs.primary.render.min;
            this.primary.render.max = desiredDisplayModeSpecs.primary.render.max;
            this.appRequest.physical.min = desiredDisplayModeSpecs.appRequest.physical.min;
            this.appRequest.physical.max = desiredDisplayModeSpecs.appRequest.physical.max;
            this.appRequest.render.min = desiredDisplayModeSpecs.appRequest.render.min;
            this.appRequest.render.max = desiredDisplayModeSpecs.appRequest.render.max;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class SettingsObserver extends android.database.ContentObserver {
        private final android.content.Context mContext;
        private float mDefaultPeakRefreshRate;
        private float mDefaultRefreshRate;
        private final android.net.Uri mLowPowerModeSetting;
        private final android.net.Uri mMatchContentFrameRateSetting;
        private final android.net.Uri mMinRefreshRateSetting;
        private final android.net.Uri mPeakRefreshRateSetting;
        private final boolean mVsynLowPowerVoteEnabled;

        SettingsObserver(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            super(handler);
            this.mPeakRefreshRateSetting = android.provider.Settings.System.getUriFor("peak_refresh_rate");
            this.mMinRefreshRateSetting = android.provider.Settings.System.getUriFor("min_refresh_rate");
            this.mLowPowerModeSetting = android.provider.Settings.Global.getUriFor("low_power");
            this.mMatchContentFrameRateSetting = android.provider.Settings.Secure.getUriFor("match_content_frame_rate");
            this.mContext = context;
            this.mVsynLowPowerVoteEnabled = z && displayManagerFlags.isVsyncLowPowerVoteEnabled();
            setRefreshRates(null, false);
        }

        public void setRefreshRates(com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            setDefaultPeakRefreshRate(displayDeviceConfig, z);
            this.mDefaultRefreshRate = displayDeviceConfig == null ? this.mContext.getResources().getInteger(android.R.integer.config_defaultPictureInPictureGravity) : displayDeviceConfig.getDefaultRefreshRate();
        }

        public void observe() {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            com.android.server.display.mode.DisplayModeDirector.this.mInjector.registerPeakRefreshRateObserver(contentResolver, this);
            com.android.server.display.mode.DisplayModeDirector.this.mInjector.registerMinRefreshRateObserver(contentResolver, this);
            contentResolver.registerContentObserver(this.mLowPowerModeSetting, false, this, 0);
            contentResolver.registerContentObserver(this.mMatchContentFrameRateSetting, false, this);
            float peakRefreshRateDefault = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getPeakRefreshRateDefault();
            if (peakRefreshRateDefault != -1.0f) {
                this.mDefaultPeakRefreshRate = peakRefreshRateDefault;
            }
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                updateRefreshRateSettingLocked();
                updateLowPowerModeSettingLocked();
                updateModeSwitchingTypeSettingLocked();
            }
        }

        public void setDefaultRefreshRate(float f) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                this.mDefaultRefreshRate = f;
                updateRefreshRateSettingLocked();
            }
        }

        public void onDeviceConfigDefaultPeakRefreshRateChanged(java.lang.Float f) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    if (f == null) {
                        setDefaultPeakRefreshRate(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
                    } else if (this.mDefaultPeakRefreshRate != f.floatValue()) {
                        this.mDefaultPeakRefreshRate = f.floatValue();
                    }
                    updateRefreshRateSettingLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    if (this.mPeakRefreshRateSetting.equals(uri) || this.mMinRefreshRateSetting.equals(uri)) {
                        updateRefreshRateSettingLocked();
                    } else if (this.mLowPowerModeSetting.equals(uri)) {
                        updateLowPowerModeSettingLocked();
                    } else if (this.mMatchContentFrameRateSetting.equals(uri)) {
                        updateModeSwitchingTypeSettingLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        float getDefaultRefreshRate() {
            return this.mDefaultRefreshRate;
        }

        @com.android.internal.annotations.VisibleForTesting
        float getDefaultPeakRefreshRate() {
            return this.mDefaultPeakRefreshRate;
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0016  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void setDefaultPeakRefreshRate(com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            float peakRefreshRateDefault;
            if (z) {
                try {
                    peakRefreshRateDefault = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getPeakRefreshRateDefault();
                } catch (java.lang.Exception e) {
                }
                if (peakRefreshRateDefault == -1.0f) {
                    peakRefreshRateDefault = displayDeviceConfig == null ? this.mContext.getResources().getInteger(android.R.integer.config_defaultNotificationLedOn) : displayDeviceConfig.getDefaultPeakRefreshRate();
                }
                this.mDefaultPeakRefreshRate = peakRefreshRateDefault;
            }
            peakRefreshRateDefault = -1.0f;
            if (peakRefreshRateDefault == -1.0f) {
            }
            this.mDefaultPeakRefreshRate = peakRefreshRateDefault;
        }

        private void updateLowPowerModeSettingLocked() {
            com.android.server.display.mode.Vote forRenderFrameRates;
            boolean z = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "low_power", 0) != 0;
            if (z && this.mVsynLowPowerVoteEnabled) {
                forRenderFrameRates = com.android.server.display.mode.Vote.forSupportedModes(java.util.List.of(new com.android.server.display.mode.SupportedModesVote.SupportedMode(60.0f, 240.0f), new com.android.server.display.mode.SupportedModesVote.SupportedMode(60.0f, 60.0f)));
            } else {
                forRenderFrameRates = z ? com.android.server.display.mode.Vote.forRenderFrameRates(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 60.0f) : null;
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(13, forRenderFrameRates);
            com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onLowPowerModeEnabledLocked(z);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void updateRefreshRateSettingLocked() {
            for (int i = 0; i < com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.size(); i++) {
                updateRefreshRateSettingLocked(com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.keyAt(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateRefreshRateSettingLocked(int i) {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.contains(i)) {
                android.util.Slog.e(com.android.server.display.mode.DisplayModeDirector.TAG, "Cannot update refresh rate setting: no supported modes for display " + i);
                return;
            }
            float maxRefreshRateLocked = com.android.server.display.mode.DisplayModeDirector.this.getMaxRefreshRateLocked(i);
            float floatForUser = android.provider.Settings.System.getFloatForUser(contentResolver, "min_refresh_rate", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, contentResolver.getUserId());
            if (java.lang.Float.isInfinite(floatForUser)) {
                if (!com.android.server.display.mode.DisplayModeDirector.this.mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled && i == 0) {
                    android.provider.Settings.System.putFloatForUser(contentResolver, "min_refresh_rate", maxRefreshRateLocked, contentResolver.getUserId());
                }
                floatForUser = maxRefreshRateLocked;
            } else if (com.android.server.display.mode.DisplayModeDirector.this.mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled && i == 0 && java.lang.Math.round(floatForUser) == java.lang.Math.round(maxRefreshRateLocked)) {
                android.provider.Settings.System.putFloatForUser(contentResolver, "min_refresh_rate", Float.POSITIVE_INFINITY, contentResolver.getUserId());
            }
            float floatForUser2 = android.provider.Settings.System.getFloatForUser(contentResolver, "peak_refresh_rate", this.mDefaultPeakRefreshRate, contentResolver.getUserId());
            if (java.lang.Float.isInfinite(floatForUser2)) {
                if (!com.android.server.display.mode.DisplayModeDirector.this.mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled && i == 0) {
                    android.provider.Settings.System.putFloatForUser(contentResolver, "peak_refresh_rate", maxRefreshRateLocked, contentResolver.getUserId());
                }
            } else {
                if (com.android.server.display.mode.DisplayModeDirector.this.mIsBackUpSmoothDisplayAndForcePeakRefreshRateEnabled && i == 0 && java.lang.Math.round(floatForUser2) == java.lang.Math.round(maxRefreshRateLocked)) {
                    android.provider.Settings.System.putFloatForUser(contentResolver, "peak_refresh_rate", Float.POSITIVE_INFINITY, contentResolver.getUserId());
                }
                maxRefreshRateLocked = floatForUser2;
            }
            updateRefreshRateSettingLocked(floatForUser, maxRefreshRateLocked, this.mDefaultRefreshRate, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateRefreshRateSettingLocked(float f, float f2, float f3, int i) {
            com.android.server.display.mode.Vote forRenderFrameRates;
            if (f2 != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                forRenderFrameRates = com.android.server.display.mode.Vote.forRenderFrameRates(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, java.lang.Math.max(f, f2));
            } else {
                forRenderFrameRates = null;
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 8, forRenderFrameRates);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 3, com.android.server.display.mode.Vote.forRenderFrameRates(f, Float.POSITIVE_INFINITY));
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(0, f3 != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? com.android.server.display.mode.Vote.forRenderFrameRates(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f3) : null);
            if (f2 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f3 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                android.util.Slog.e(com.android.server.display.mode.DisplayModeDirector.TAG, "Default and peak refresh rates are both 0. One of them should be set to a valid value.");
                f2 = f;
            } else if (f2 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                f2 = f3;
            } else if (f3 != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                f2 = java.lang.Math.min(f3, f2);
            }
            if (i == 0) {
                com.android.server.display.mode.DisplayModeDirector.this.mBrightnessObserver.onRefreshRateSettingChangedLocked(f, f2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeRefreshRateSetting(int i) {
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 8, null);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 3, null);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 0, null);
        }

        private void updateModeSwitchingTypeSettingLocked() {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            int intForUser = android.provider.Settings.Secure.getIntForUser(contentResolver, "match_content_frame_rate", com.android.server.display.mode.DisplayModeDirector.this.mModeSwitchingType, contentResolver.getUserId());
            if (intForUser != com.android.server.display.mode.DisplayModeDirector.this.mModeSwitchingType) {
                com.android.server.display.mode.DisplayModeDirector.this.mModeSwitchingType = intForUser;
                com.android.server.display.mode.DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
            }
        }

        public void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("  SettingsObserver");
            printWriter.println("    mDefaultRefreshRate: " + this.mDefaultRefreshRate);
            printWriter.println("    mDefaultPeakRefreshRate: " + this.mDefaultPeakRefreshRate);
        }
    }

    public final class AppRequestObserver {
        private final android.util.SparseArray<android.view.Display.Mode> mAppRequestedModeByDisplay = new android.util.SparseArray<>();
        private final android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> mAppPreferredRefreshRateRangeByDisplay = new android.util.SparseArray<>();

        AppRequestObserver() {
        }

        public void setAppRequest(int i, int i2, float f, float f2) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                setAppRequestedModeLocked(i, i2);
                setAppPreferredRefreshRateRangeLocked(i, f, f2);
            }
        }

        private void setAppRequestedModeLocked(int i, int i2) {
            com.android.server.display.mode.Vote vote;
            com.android.server.display.mode.Vote vote2;
            android.view.Display.Mode findModeByIdLocked = findModeByIdLocked(i, i2);
            if (java.util.Objects.equals(findModeByIdLocked, this.mAppRequestedModeByDisplay.get(i))) {
                return;
            }
            if (findModeByIdLocked != null) {
                this.mAppRequestedModeByDisplay.put(i, findModeByIdLocked);
                vote = com.android.server.display.mode.Vote.forBaseModeRefreshRate(findModeByIdLocked.getRefreshRate());
                vote2 = com.android.server.display.mode.Vote.forSize(findModeByIdLocked.getPhysicalWidth(), findModeByIdLocked.getPhysicalHeight());
            } else {
                this.mAppRequestedModeByDisplay.remove(i);
                vote = null;
                vote2 = null;
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 6, vote);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 7, vote2);
        }

        private void setAppPreferredRefreshRateRangeLocked(int i, float f, float f2) {
            android.view.SurfaceControl.RefreshRateRange refreshRateRange;
            com.android.server.display.mode.Vote vote = null;
            if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f2 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                if (f2 <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    f2 = Float.POSITIVE_INFINITY;
                }
                refreshRateRange = new android.view.SurfaceControl.RefreshRateRange(f, f2);
                if (refreshRateRange.min == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && refreshRateRange.max == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    refreshRateRange = null;
                }
            } else {
                refreshRateRange = null;
            }
            if (java.util.Objects.equals(refreshRateRange, this.mAppPreferredRefreshRateRangeByDisplay.get(i))) {
                return;
            }
            if (refreshRateRange != null) {
                this.mAppPreferredRefreshRateRangeByDisplay.put(i, refreshRateRange);
                vote = com.android.server.display.mode.Vote.forRenderFrameRates(refreshRateRange.min, refreshRateRange.max);
            } else {
                this.mAppPreferredRefreshRateRangeByDisplay.remove(i);
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, 5, vote);
        }

        private android.view.Display.Mode findModeByIdLocked(int i, int i2) {
            android.view.Display.Mode[] modeArr = (android.view.Display.Mode[]) com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.get(i);
            if (modeArr == null) {
                return null;
            }
            for (android.view.Display.Mode mode : modeArr) {
                if (mode.getModeId() == i2) {
                    return mode;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("  AppRequestObserver");
            printWriter.println("    mAppRequestedModeByDisplay:");
            for (int i = 0; i < this.mAppRequestedModeByDisplay.size(); i++) {
                printWriter.println("    " + this.mAppRequestedModeByDisplay.keyAt(i) + " -> " + this.mAppRequestedModeByDisplay.valueAt(i));
            }
            printWriter.println("    mAppPreferredRefreshRateRangeByDisplay:");
            for (int i2 = 0; i2 < this.mAppPreferredRefreshRateRangeByDisplay.size(); i2++) {
                printWriter.println("    " + this.mAppPreferredRefreshRateRangeByDisplay.keyAt(i2) + " -> " + this.mAppPreferredRefreshRateRangeByDisplay.valueAt(i2));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public final class DisplayObserver implements android.hardware.display.DisplayManager.DisplayListener {
        private final android.content.Context mContext;
        private int mExternalDisplayPeakHeight;
        private int mExternalDisplayPeakRefreshRate;
        private int mExternalDisplayPeakWidth;
        private final java.util.Set<java.lang.Integer> mExternalDisplaysConnected = new java.util.HashSet();
        private final android.os.Handler mHandler;
        private final boolean mRefreshRateSynchronizationEnabled;
        private final com.android.server.display.mode.VotesStorage mVotesStorage;

        DisplayObserver(android.content.Context context, android.os.Handler handler, com.android.server.display.mode.VotesStorage votesStorage) {
            this.mContext = context;
            this.mHandler = handler;
            this.mVotesStorage = votesStorage;
            this.mExternalDisplayPeakRefreshRate = this.mContext.getResources().getInteger(android.R.integer.config_dynamicPowerSavingsDefaultDisableThreshold);
            this.mExternalDisplayPeakWidth = this.mContext.getResources().getInteger(android.R.integer.config_emergency_call_wait_for_connection_timeout_millis);
            this.mExternalDisplayPeakHeight = this.mContext.getResources().getInteger(android.R.integer.config_dropboxLowPriorityBroadcastRateLimitPeriod);
            this.mRefreshRateSynchronizationEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_pulseOnNotificationsAvailable);
        }

        private boolean isExternalDisplayLimitModeEnabled() {
            return this.mExternalDisplayPeakWidth > 0 && this.mExternalDisplayPeakHeight > 0 && this.mExternalDisplayPeakRefreshRate > 0 && com.android.server.display.mode.DisplayModeDirector.this.mIsExternalDisplayLimitModeEnabled && com.android.server.display.mode.DisplayModeDirector.this.mIsDisplayResolutionRangeVotingEnabled && com.android.server.display.mode.DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled;
        }

        private boolean isRefreshRateSynchronizationEnabled() {
            return this.mRefreshRateSynchronizationEnabled && com.android.server.display.mode.DisplayModeDirector.this.mIsDisplaysRefreshRatesSynchronizationEnabled;
        }

        public void observe() {
            com.android.server.display.mode.DisplayModeDirector.this.mInjector.registerDisplayListener(this, this.mHandler);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            for (android.view.Display display : com.android.server.display.mode.DisplayModeDirector.this.mInjector.getDisplays()) {
                int displayId = display.getDisplayId();
                android.view.DisplayInfo displayInfo = getDisplayInfo(displayId);
                sparseArray.put(displayId, displayInfo.supportedModes);
                sparseArray2.put(displayId, displayInfo.getDefaultMode());
            }
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    int size = sparseArray.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.put(sparseArray.keyAt(i), (android.view.Display.Mode[]) sparseArray.valueAt(i));
                        com.android.server.display.mode.DisplayModeDirector.this.mDefaultModeByDisplay.put(sparseArray2.keyAt(i), (android.view.Display.Mode) sparseArray2.valueAt(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            android.view.DisplayInfo displayInfo = getDisplayInfo(i);
            updateDisplayModes(i, displayInfo);
            updateLayoutLimitedFrameRate(i, displayInfo);
            updateUserSettingDisplayPreferredSize(displayInfo);
            updateDisplaysPeakRefreshRateAndResolution(displayInfo);
            addDisplaysSynchronizedPeakRefreshRate(displayInfo);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.remove(i);
                com.android.server.display.mode.DisplayModeDirector.this.mDefaultModeByDisplay.remove(i);
                com.android.server.display.mode.DisplayModeDirector.this.mSettingsObserver.removeRefreshRateSetting(i);
            }
            updateLayoutLimitedFrameRate(i, null);
            removeUserSettingDisplayPreferredSize(i);
            removeDisplaysPeakRefreshRateAndResolution(i);
            removeDisplaysSynchronizedPeakRefreshRate(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            android.view.DisplayInfo displayInfo = getDisplayInfo(i);
            updateDisplayModes(i, displayInfo);
            updateLayoutLimitedFrameRate(i, displayInfo);
            updateUserSettingDisplayPreferredSize(displayInfo);
        }

        boolean isExternalDisplayLocked(int i) {
            return this.mExternalDisplaysConnected.contains(java.lang.Integer.valueOf(i));
        }

        @android.annotation.Nullable
        private android.view.DisplayInfo getDisplayInfo(int i) {
            android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
            if (com.android.server.display.mode.DisplayModeDirector.this.mInjector.getDisplayInfo(i, displayInfo)) {
                return displayInfo;
            }
            return null;
        }

        private void updateLayoutLimitedFrameRate(int i, @android.annotation.Nullable android.view.DisplayInfo displayInfo) {
            com.android.server.display.mode.Vote vote;
            if (displayInfo != null && displayInfo.layoutLimitedRefreshRate != null) {
                vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(displayInfo.layoutLimitedRefreshRate.min, displayInfo.layoutLimitedRefreshRate.max);
            } else {
                vote = null;
            }
            this.mVotesStorage.updateVote(i, 12, vote);
        }

        private void removeUserSettingDisplayPreferredSize(int i) {
            if (!com.android.server.display.mode.DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled) {
                return;
            }
            this.mVotesStorage.updateVote(i, 4, null);
        }

        private void updateUserSettingDisplayPreferredSize(@android.annotation.Nullable android.view.DisplayInfo displayInfo) {
            if (displayInfo == null || !com.android.server.display.mode.DisplayModeDirector.this.mIsUserPreferredModeVoteEnabled) {
                return;
            }
            android.view.Display.Mode findDisplayPreferredMode = findDisplayPreferredMode(displayInfo);
            if (findDisplayPreferredMode == null) {
                removeUserSettingDisplayPreferredSize(displayInfo.displayId);
            } else {
                this.mVotesStorage.updateVote(displayInfo.displayId, 4, com.android.server.display.mode.Vote.forSize(findDisplayPreferredMode.getPhysicalWidth(), findDisplayPreferredMode.getPhysicalHeight()));
            }
        }

        @android.annotation.Nullable
        private android.view.Display.Mode findDisplayPreferredMode(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
            if (displayInfo.userPreferredModeId == -1) {
                return null;
            }
            for (android.view.Display.Mode mode : displayInfo.supportedModes) {
                if (mode.getModeId() == displayInfo.userPreferredModeId) {
                    return mode;
                }
            }
            return null;
        }

        private void removeDisplaysPeakRefreshRateAndResolution(int i) {
            if (!isExternalDisplayLimitModeEnabled()) {
                return;
            }
            this.mVotesStorage.updateVote(i, 10, null);
        }

        private void updateDisplaysPeakRefreshRateAndResolution(@android.annotation.Nullable android.view.DisplayInfo displayInfo) {
            if (displayInfo == null || displayInfo.type != 2 || !isExternalDisplayLimitModeEnabled()) {
                return;
            }
            this.mVotesStorage.updateVote(displayInfo.displayId, 10, com.android.server.display.mode.Vote.forSizeAndPhysicalRefreshRatesRange(0, 0, this.mExternalDisplayPeakWidth, this.mExternalDisplayPeakHeight, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mExternalDisplayPeakRefreshRate));
        }

        private void addDisplaysSynchronizedPeakRefreshRate(@android.annotation.Nullable android.view.DisplayInfo displayInfo) {
            if (displayInfo == null || displayInfo.type != 2 || !isRefreshRateSynchronizationEnabled()) {
                return;
            }
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    this.mExternalDisplaysConnected.add(java.lang.Integer.valueOf(displayInfo.displayId));
                    if (this.mExternalDisplaysConnected.size() != 1) {
                        return;
                    }
                    this.mVotesStorage.updateGlobalVote(9, com.android.server.display.mode.Vote.forPhysicalRefreshRates(59.0f, 61.0f));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void removeDisplaysSynchronizedPeakRefreshRate(int i) {
            if (!isRefreshRateSynchronizationEnabled()) {
                return;
            }
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    if (isExternalDisplayLocked(i)) {
                        this.mExternalDisplaysConnected.remove(java.lang.Integer.valueOf(i));
                        if (this.mExternalDisplaysConnected.size() != 0) {
                            return;
                        }
                        this.mVotesStorage.updateGlobalVote(9, null);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void updateDisplayModes(int i, @android.annotation.Nullable android.view.DisplayInfo displayInfo) {
            boolean z;
            if (displayInfo == null) {
                return;
            }
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                try {
                    boolean z2 = true;
                    if (java.util.Arrays.equals((java.lang.Object[]) com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.get(i), displayInfo.supportedModes)) {
                        z = false;
                    } else {
                        com.android.server.display.mode.DisplayModeDirector.this.mSupportedModesByDisplay.put(i, displayInfo.supportedModes);
                        z = true;
                    }
                    if (java.util.Objects.equals(com.android.server.display.mode.DisplayModeDirector.this.mDefaultModeByDisplay.get(i), displayInfo.getDefaultMode())) {
                        z2 = z;
                    } else {
                        com.android.server.display.mode.DisplayModeDirector.this.mDefaultModeByDisplay.put(i, displayInfo.getDefaultMode());
                    }
                    if (z2) {
                        com.android.server.display.mode.DisplayModeDirector.this.notifyDesiredDisplayModeSpecsChangedLocked();
                        com.android.server.display.mode.DisplayModeDirector.this.mSettingsObserver.updateRefreshRateSettingLocked(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public class BrightnessObserver implements android.hardware.display.DisplayManager.DisplayListener {
        private static final int LIGHT_SENSOR_RATE_MS = 250;
        private com.android.server.display.utils.AmbientFilter mAmbientFilter;
        private final android.content.Context mContext;
        private final android.os.Handler mHandler;
        private float[] mHighAmbientBrightnessThresholds;
        private float[] mHighDisplayBrightnessThresholds;

        @android.annotation.Nullable
        private android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> mHighZoneRefreshRateForThermals;
        private final com.android.server.display.mode.DisplayModeDirector.Injector mInjector;
        private android.hardware.Sensor mLightSensor;
        private java.lang.String mLightSensorName;
        private java.lang.String mLightSensorType;
        private boolean mLoggingEnabled;
        private float[] mLowAmbientBrightnessThresholds;
        private float[] mLowDisplayBrightnessThresholds;

        @android.annotation.Nullable
        private android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> mLowZoneRefreshRateForThermals;
        private int mRefreshRateInHighZone;
        private int mRefreshRateInLowZone;
        private android.hardware.Sensor mRegisteredLightSensor;
        private android.hardware.SensorManager mSensorManager;
        private boolean mShouldObserveAmbientHighChange;
        private boolean mShouldObserveAmbientLowChange;
        private boolean mShouldObserveDisplayHighChange;
        private boolean mShouldObserveDisplayLowChange;
        private boolean mThermalRegistered;
        private final boolean mVsyncLowLightBlockingVoteEnabled;
        private final com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener mLightSensorListener = new com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener();
        private float mAmbientLux = -1.0f;
        private float mBrightness = Float.NaN;
        private final android.os.IThermalEventListener.Stub mThermalListener = new android.os.IThermalEventListener.Stub() { // from class: com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.1
            public void notifyThrottling(android.os.Temperature temperature) {
                int status = temperature.getStatus();
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    try {
                        if (com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mThermalStatus != status) {
                            com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mThermalStatus = status;
                        }
                        com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.onBrightnessChangedLocked();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        private int mDefaultDisplayState = 0;
        private boolean mRefreshRateChangeable = false;
        private boolean mLowPowerModeEnabled = false;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private int mThermalStatus = 0;

        BrightnessObserver(android.content.Context context, android.os.Handler handler, com.android.server.display.mode.DisplayModeDirector.Injector injector, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            boolean z2 = false;
            this.mContext = context;
            this.mHandler = handler;
            this.mInjector = injector;
            updateBlockingZoneThresholds(null, false);
            this.mRefreshRateInHighZone = context.getResources().getInteger(android.R.integer.config_extraFreeKbytesAbsolute);
            if (z && displayManagerFlags.isVsyncLowLightVoteEnabled()) {
                z2 = true;
            }
            this.mVsyncLowLightBlockingVoteEnabled = z2;
        }

        public void updateBlockingZoneThresholds(@android.annotation.Nullable com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            loadLowBrightnessThresholds(displayDeviceConfig, z);
            loadHighBrightnessThresholds(displayDeviceConfig, z);
        }

        @com.android.internal.annotations.VisibleForTesting
        float[] getLowDisplayBrightnessThresholds() {
            return this.mLowDisplayBrightnessThresholds;
        }

        @com.android.internal.annotations.VisibleForTesting
        float[] getLowAmbientBrightnessThresholds() {
            return this.mLowAmbientBrightnessThresholds;
        }

        @com.android.internal.annotations.VisibleForTesting
        float[] getHighDisplayBrightnessThresholds() {
            return this.mHighDisplayBrightnessThresholds;
        }

        @com.android.internal.annotations.VisibleForTesting
        float[] getHighAmbientBrightnessThresholds() {
            return this.mHighAmbientBrightnessThresholds;
        }

        @com.android.internal.annotations.VisibleForTesting
        int getRefreshRateInHighZone() {
            return this.mRefreshRateInHighZone;
        }

        @com.android.internal.annotations.VisibleForTesting
        int getRefreshRateInLowZone() {
            return this.mRefreshRateInLowZone;
        }

        private void loadLowBrightnessThresholds(@android.annotation.Nullable final com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            loadRefreshRateInHighZone(displayDeviceConfig, z);
            loadRefreshRateInLowZone(displayDeviceConfig, z);
            this.mLowDisplayBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lambda$loadLowBrightnessThresholds$0;
                    lambda$loadLowBrightnessThresholds$0 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$loadLowBrightnessThresholds$0();
                    return lambda$loadLowBrightnessThresholds$0;
                }
            }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda7
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lowDisplayBrightnessThresholds;
                    lowDisplayBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getLowDisplayBrightnessThresholds();
                    return lowDisplayBrightnessThresholds;
                }
            }, android.R.array.config_brightnessThresholdsOfPeakRefreshRate, displayDeviceConfig, z, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2());
            this.mLowAmbientBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda8
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lambda$loadLowBrightnessThresholds$2;
                    lambda$loadLowBrightnessThresholds$2 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$loadLowBrightnessThresholds$2();
                    return lambda$loadLowBrightnessThresholds$2;
                }
            }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda9
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lowAmbientBrightnessThresholds;
                    lowAmbientBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getLowAmbientBrightnessThresholds();
                    return lowAmbientBrightnessThresholds;
                }
            }, android.R.array.config_ambientThresholdsOfPeakRefreshRate, displayDeviceConfig, z, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda5());
            if (this.mLowDisplayBrightnessThresholds.length != this.mLowAmbientBrightnessThresholds.length) {
                throw new java.lang.RuntimeException("display low brightness threshold array and ambient brightness threshold array have different length: displayBrightnessThresholds=" + java.util.Arrays.toString(this.mLowDisplayBrightnessThresholds) + ", ambientBrightnessThresholds=" + java.util.Arrays.toString(this.mLowAmbientBrightnessThresholds));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$loadLowBrightnessThresholds$0() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowDisplayBrightnessThresholds();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$loadLowBrightnessThresholds$2() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowAmbientBrightnessThresholds();
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:5:0x0013  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void loadRefreshRateInLowZone(com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            int refreshRateInLowZone;
            if (z) {
                try {
                    refreshRateInLowZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInLowZone();
                } catch (java.lang.Exception e) {
                }
                if (refreshRateInLowZone == -1) {
                    if (displayDeviceConfig == null) {
                        refreshRateInLowZone = this.mContext.getResources().getInteger(android.R.integer.config_defaultPowerStatsThrottlePeriodWifi);
                    } else {
                        refreshRateInLowZone = displayDeviceConfig.getDefaultLowBlockingZoneRefreshRate();
                    }
                }
                this.mLowZoneRefreshRateForThermals = displayDeviceConfig != null ? null : displayDeviceConfig.getLowBlockingZoneThermalMap();
                this.mRefreshRateInLowZone = refreshRateInLowZone;
            }
            refreshRateInLowZone = -1;
            if (refreshRateInLowZone == -1) {
            }
            this.mLowZoneRefreshRateForThermals = displayDeviceConfig != null ? null : displayDeviceConfig.getLowBlockingZoneThermalMap();
            this.mRefreshRateInLowZone = refreshRateInLowZone;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:5:0x0013  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void loadRefreshRateInHighZone(com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            int refreshRateInHighZone;
            if (z) {
                try {
                    refreshRateInHighZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInHighZone();
                } catch (java.lang.Exception e) {
                }
                if (refreshRateInHighZone == -1) {
                    if (displayDeviceConfig == null) {
                        refreshRateInHighZone = this.mContext.getResources().getInteger(android.R.integer.config_extraFreeKbytesAbsolute);
                    } else {
                        refreshRateInHighZone = displayDeviceConfig.getDefaultHighBlockingZoneRefreshRate();
                    }
                }
                this.mHighZoneRefreshRateForThermals = displayDeviceConfig != null ? null : displayDeviceConfig.getHighBlockingZoneThermalMap();
                this.mRefreshRateInHighZone = refreshRateInHighZone;
            }
            refreshRateInHighZone = -1;
            if (refreshRateInHighZone == -1) {
            }
            this.mHighZoneRefreshRateForThermals = displayDeviceConfig != null ? null : displayDeviceConfig.getHighBlockingZoneThermalMap();
            this.mRefreshRateInHighZone = refreshRateInHighZone;
        }

        private void loadHighBrightnessThresholds(final com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z) {
            this.mHighDisplayBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda10
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lambda$loadHighBrightnessThresholds$4;
                    lambda$loadHighBrightnessThresholds$4 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$loadHighBrightnessThresholds$4();
                    return lambda$loadHighBrightnessThresholds$4;
                }
            }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda11
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] highDisplayBrightnessThresholds;
                    highDisplayBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getHighDisplayBrightnessThresholds();
                    return highDisplayBrightnessThresholds;
                }
            }, android.R.array.config_healthConnectRestoreKnownSigners, displayDeviceConfig, z, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2());
            this.mHighAmbientBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda12
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] lambda$loadHighBrightnessThresholds$6;
                    lambda$loadHighBrightnessThresholds$6 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$loadHighBrightnessThresholds$6();
                    return lambda$loadHighBrightnessThresholds$6;
                }
            }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda13
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    float[] highAmbientBrightnessThresholds;
                    highAmbientBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getHighAmbientBrightnessThresholds();
                    return highAmbientBrightnessThresholds;
                }
            }, android.R.array.config_healthConnectMigrationKnownSigners, displayDeviceConfig, z, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda5());
            if (this.mHighDisplayBrightnessThresholds.length != this.mHighAmbientBrightnessThresholds.length) {
                throw new java.lang.RuntimeException("display high brightness threshold array and ambient brightness threshold array have different length: displayBrightnessThresholds=" + java.util.Arrays.toString(this.mHighDisplayBrightnessThresholds) + ", ambientBrightnessThresholds=" + java.util.Arrays.toString(this.mHighAmbientBrightnessThresholds));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$loadHighBrightnessThresholds$4() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighDisplayBrightnessThresholds();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$loadHighBrightnessThresholds$6() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighAmbientBrightnessThresholds();
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x000e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private float[] loadBrightnessThresholds(java.util.concurrent.Callable<float[]> callable, java.util.concurrent.Callable<float[]> callable2, int i, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, boolean z, java.util.function.Function<int[], float[]> function) {
            float[] call;
            if (z) {
                try {
                    call = callable.call();
                } catch (java.lang.Exception e) {
                }
                if (call == null) {
                    try {
                        if (displayDeviceConfig == null) {
                            call = function.apply(this.mContext.getResources().getIntArray(i));
                        } else {
                            call = callable2.call();
                        }
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e(com.android.server.display.mode.DisplayModeDirector.TAG, "Unexpectedly failed to load display brightness threshold");
                        e2.printStackTrace();
                    }
                }
                return call;
            }
            call = null;
            if (call == null) {
            }
            return call;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void observe(android.hardware.SensorManager sensorManager) {
            this.mSensorManager = sensorManager;
            this.mBrightness = getBrightness(0);
            float[] lowDisplayBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowDisplayBrightnessThresholds();
            float[] lowAmbientBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowAmbientBrightnessThresholds();
            if (lowDisplayBrightnessThresholds != null && lowAmbientBrightnessThresholds != null && lowDisplayBrightnessThresholds.length == lowAmbientBrightnessThresholds.length) {
                this.mLowDisplayBrightnessThresholds = lowDisplayBrightnessThresholds;
                this.mLowAmbientBrightnessThresholds = lowAmbientBrightnessThresholds;
            }
            float[] highDisplayBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighDisplayBrightnessThresholds();
            float[] highAmbientBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighAmbientBrightnessThresholds();
            if (highDisplayBrightnessThresholds != null && highAmbientBrightnessThresholds != null && highDisplayBrightnessThresholds.length == highAmbientBrightnessThresholds.length) {
                this.mHighDisplayBrightnessThresholds = highDisplayBrightnessThresholds;
                this.mHighAmbientBrightnessThresholds = highAmbientBrightnessThresholds;
            }
            int refreshRateInLowZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInLowZone();
            if (refreshRateInLowZone != -1) {
                this.mRefreshRateInLowZone = refreshRateInLowZone;
            }
            int refreshRateInHighZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInHighZone();
            if (refreshRateInHighZone != -1) {
                this.mRefreshRateInHighZone = refreshRateInHighZone;
            }
            restartObserver();
            com.android.server.display.mode.DisplayModeDirector.this.mDeviceConfigDisplaySettings.startListening();
            this.mInjector.registerDisplayListener(this, this.mHandler, 12L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLoggingEnabled(boolean z) {
            if (this.mLoggingEnabled == z) {
                return;
            }
            this.mLoggingEnabled = z;
            this.mLightSensorListener.setLoggingEnabled(z);
        }

        @com.android.internal.annotations.VisibleForTesting
        public void onRefreshRateSettingChangedLocked(float f, float f2) {
            boolean z = f2 - f > 1.0f && f2 > 60.0f;
            if (this.mRefreshRateChangeable != z) {
                this.mRefreshRateChangeable = z;
                updateSensorStatus();
                if (!z) {
                    removeFlickerRefreshRateVotes();
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void onLowPowerModeEnabledLocked(boolean z) {
            if (this.mLowPowerModeEnabled != z) {
                this.mLowPowerModeEnabled = z;
                updateSensorStatus();
                if (z) {
                    removeFlickerRefreshRateVotes();
                }
            }
        }

        private void removeFlickerRefreshRateVotes() {
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(1, null);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(14, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDeviceConfigLowBrightnessThresholdsChanged(float[] fArr, float[] fArr2) {
            final com.android.server.display.DisplayDeviceConfig displayDeviceConfig;
            if (fArr != null && fArr2 != null && fArr.length == fArr2.length) {
                this.mLowDisplayBrightnessThresholds = fArr;
                this.mLowAmbientBrightnessThresholds = fArr2;
            } else {
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    displayDeviceConfig = com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                }
                this.mLowDisplayBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda14
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$8;
                        lambda$onDeviceConfigLowBrightnessThresholdsChanged$8 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$onDeviceConfigLowBrightnessThresholdsChanged$8();
                        return lambda$onDeviceConfigLowBrightnessThresholdsChanged$8;
                    }
                }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda15
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lowDisplayBrightnessThresholds;
                        lowDisplayBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getLowDisplayBrightnessThresholds();
                        return lowDisplayBrightnessThresholds;
                    }
                }, android.R.array.config_brightnessThresholdsOfPeakRefreshRate, displayDeviceConfig, false, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2());
                this.mLowAmbientBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda16
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$10;
                        lambda$onDeviceConfigLowBrightnessThresholdsChanged$10 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$onDeviceConfigLowBrightnessThresholdsChanged$10();
                        return lambda$onDeviceConfigLowBrightnessThresholdsChanged$10;
                    }
                }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda17
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lowAmbientBrightnessThresholds;
                        lowAmbientBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getLowAmbientBrightnessThresholds();
                        return lowAmbientBrightnessThresholds;
                    }
                }, android.R.array.config_ambientThresholdsOfPeakRefreshRate, displayDeviceConfig, false, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda5());
            }
            restartObserver();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$8() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowDisplayBrightnessThresholds();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$onDeviceConfigLowBrightnessThresholdsChanged$10() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowAmbientBrightnessThresholds();
        }

        public void onDeviceConfigRefreshRateInLowZoneChanged(int i) {
            if (i == -1) {
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    loadRefreshRateInLowZone(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
                }
                restartObserver();
                return;
            }
            if (i != this.mRefreshRateInLowZone) {
                this.mRefreshRateInLowZone = i;
                restartObserver();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDeviceConfigHighBrightnessThresholdsChanged(float[] fArr, float[] fArr2) {
            final com.android.server.display.DisplayDeviceConfig displayDeviceConfig;
            if (fArr != null && fArr2 != null && fArr.length == fArr2.length) {
                this.mHighDisplayBrightnessThresholds = fArr;
                this.mHighAmbientBrightnessThresholds = fArr2;
            } else {
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    displayDeviceConfig = com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig;
                }
                this.mHighDisplayBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$12;
                        lambda$onDeviceConfigHighBrightnessThresholdsChanged$12 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$onDeviceConfigHighBrightnessThresholdsChanged$12();
                        return lambda$onDeviceConfigHighBrightnessThresholdsChanged$12;
                    }
                }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] highDisplayBrightnessThresholds;
                        highDisplayBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getHighDisplayBrightnessThresholds();
                        return highDisplayBrightnessThresholds;
                    }
                }, android.R.array.config_healthConnectRestoreKnownSigners, displayDeviceConfig, false, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2());
                this.mHighAmbientBrightnessThresholds = loadBrightnessThresholds(new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda3
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$14;
                        lambda$onDeviceConfigHighBrightnessThresholdsChanged$14 = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.lambda$onDeviceConfigHighBrightnessThresholdsChanged$14();
                        return lambda$onDeviceConfigHighBrightnessThresholdsChanged$14;
                    }
                }, new java.util.concurrent.Callable() { // from class: com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda4
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        float[] highAmbientBrightnessThresholds;
                        highAmbientBrightnessThresholds = com.android.server.display.DisplayDeviceConfig.this.getHighAmbientBrightnessThresholds();
                        return highAmbientBrightnessThresholds;
                    }
                }, android.R.array.config_healthConnectMigrationKnownSigners, displayDeviceConfig, false, new com.android.server.display.mode.DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda5());
            }
            restartObserver();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$12() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowDisplayBrightnessThresholds();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ float[] lambda$onDeviceConfigHighBrightnessThresholdsChanged$14() throws java.lang.Exception {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighAmbientBrightnessThresholds();
        }

        public void onDeviceConfigRefreshRateInHighZoneChanged(int i) {
            if (i == -1) {
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    loadRefreshRateInHighZone(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig, false);
                }
                restartObserver();
                return;
            }
            if (i != this.mRefreshRateInHighZone) {
                this.mRefreshRateInHighZone = i;
                restartObserver();
            }
        }

        void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("  BrightnessObserver");
            printWriter.println("    mAmbientLux: " + this.mAmbientLux);
            printWriter.println("    mBrightness: " + this.mBrightness);
            printWriter.println("    mDefaultDisplayState: " + this.mDefaultDisplayState);
            printWriter.println("    mLowPowerModeEnabled: " + this.mLowPowerModeEnabled);
            printWriter.println("    mRefreshRateChangeable: " + this.mRefreshRateChangeable);
            printWriter.println("    mShouldObserveDisplayLowChange: " + this.mShouldObserveDisplayLowChange);
            printWriter.println("    mShouldObserveAmbientLowChange: " + this.mShouldObserveAmbientLowChange);
            printWriter.println("    mRefreshRateInLowZone: " + this.mRefreshRateInLowZone);
            for (float f : this.mLowDisplayBrightnessThresholds) {
                printWriter.println("    mDisplayLowBrightnessThreshold: " + f);
            }
            for (float f2 : this.mLowAmbientBrightnessThresholds) {
                printWriter.println("    mAmbientLowBrightnessThreshold: " + f2);
            }
            printWriter.println("    mShouldObserveDisplayHighChange: " + this.mShouldObserveDisplayHighChange);
            printWriter.println("    mShouldObserveAmbientHighChange: " + this.mShouldObserveAmbientHighChange);
            printWriter.println("    mRefreshRateInHighZone: " + this.mRefreshRateInHighZone);
            float[] fArr = this.mHighDisplayBrightnessThresholds;
            int length = fArr.length;
            for (int i = 0; i < length; i++) {
                printWriter.println("    mDisplayHighBrightnessThresholds: " + fArr[i]);
            }
            for (float f3 : this.mHighAmbientBrightnessThresholds) {
                printWriter.println("    mAmbientHighBrightnessThresholds: " + f3);
            }
            printWriter.println("    mRegisteredLightSensor: " + this.mRegisteredLightSensor);
            printWriter.println("    mLightSensor: " + this.mLightSensor);
            printWriter.println("    mLightSensorName: " + this.mLightSensorName);
            printWriter.println("    mLightSensorType: " + this.mLightSensorType);
            this.mLightSensorListener.dumpLocked(printWriter);
            if (this.mAmbientFilter != null) {
                this.mAmbientFilter.dump(new android.util.IndentingPrintWriter(printWriter, "    "));
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == 0) {
                updateDefaultDisplayState();
                float brightness = getBrightness(i);
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    try {
                        if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(brightness, this.mBrightness)) {
                            this.mBrightness = brightness;
                            onBrightnessChangedLocked();
                        }
                    } finally {
                    }
                }
            }
        }

        private void restartObserver() {
            if (this.mRefreshRateInLowZone > 0) {
                this.mShouldObserveDisplayLowChange = hasValidThreshold(this.mLowDisplayBrightnessThresholds);
                this.mShouldObserveAmbientLowChange = hasValidThreshold(this.mLowAmbientBrightnessThresholds);
            } else {
                this.mShouldObserveDisplayLowChange = false;
                this.mShouldObserveAmbientLowChange = false;
            }
            if (this.mRefreshRateInHighZone > 0) {
                this.mShouldObserveDisplayHighChange = hasValidThreshold(this.mHighDisplayBrightnessThresholds);
                this.mShouldObserveAmbientHighChange = hasValidThreshold(this.mHighAmbientBrightnessThresholds);
            } else {
                this.mShouldObserveDisplayHighChange = false;
                this.mShouldObserveAmbientHighChange = false;
            }
            if (this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange) {
                android.hardware.Sensor lightSensor = getLightSensor();
                if (lightSensor != null && lightSensor != this.mLightSensor) {
                    this.mAmbientFilter = com.android.server.display.utils.AmbientFilterFactory.createBrightnessFilter(com.android.server.display.mode.DisplayModeDirector.TAG, this.mContext.getResources());
                    this.mLightSensor = lightSensor;
                }
            } else {
                this.mAmbientFilter = null;
                this.mLightSensor = null;
            }
            updateSensorStatus();
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                onBrightnessChangedLocked();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadLightSensor(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            reloadLightSensorData(displayDeviceConfig);
            restartObserver();
        }

        private void reloadLightSensorData(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            if (displayDeviceConfig != null && displayDeviceConfig.getAmbientLightSensor() != null) {
                this.mLightSensorType = displayDeviceConfig.getAmbientLightSensor().type;
                this.mLightSensorName = displayDeviceConfig.getAmbientLightSensor().name;
            } else if (this.mLightSensorName == null && this.mLightSensorType == null) {
                this.mLightSensorType = this.mContext.getResources().getString(android.R.string.config_default_dns_server);
                this.mLightSensorName = "";
            }
        }

        private android.hardware.Sensor getLightSensor() {
            return com.android.server.display.utils.SensorUtils.findSensor(this.mSensorManager, this.mLightSensorType, this.mLightSensorName, 5);
        }

        private boolean hasValidThreshold(float[] fArr) {
            for (float f : fArr) {
                if (f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInsideLowZone(float f, float f2) {
            for (int i = 0; i < this.mLowDisplayBrightnessThresholds.length; i++) {
                float f3 = this.mLowDisplayBrightnessThresholds[i];
                float f4 = this.mLowAmbientBrightnessThresholds[i];
                if (f3 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f4 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    if (f3 >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        if (f <= f3) {
                            return true;
                        }
                    } else if (f4 >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f2 <= f4) {
                        return true;
                    }
                } else if (f <= f3 && f2 <= f4) {
                    return true;
                }
            }
            return false;
        }

        private boolean isInsideHighZone(float f, float f2) {
            for (int i = 0; i < this.mHighDisplayBrightnessThresholds.length; i++) {
                float f3 = this.mHighDisplayBrightnessThresholds[i];
                float f4 = this.mHighAmbientBrightnessThresholds[i];
                if (f3 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f4 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    if (f3 >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        if (f >= f3) {
                            return true;
                        }
                    } else if (f4 >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f2 >= f4) {
                        return true;
                    }
                } else if (f >= f3 && f2 >= f4) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onBrightnessChangedLocked() {
            com.android.server.display.mode.Vote vote;
            com.android.server.display.mode.Vote vote2;
            android.view.SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange;
            android.view.SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange2;
            if (!this.mRefreshRateChangeable || this.mLowPowerModeEnabled || java.lang.Float.isNaN(this.mBrightness)) {
                return;
            }
            boolean z = false;
            if (!(hasValidLowZone() && isInsideLowZone(this.mBrightness, this.mAmbientLux))) {
                vote = null;
                vote2 = null;
            } else {
                vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(this.mRefreshRateInLowZone, this.mRefreshRateInLowZone);
                if (this.mLowZoneRefreshRateForThermals != null && (findBestMatchingRefreshRateRange2 = com.android.server.display.mode.SkinThermalStatusObserver.findBestMatchingRefreshRateRange(this.mThermalStatus, this.mLowZoneRefreshRateForThermals)) != null) {
                    vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(findBestMatchingRefreshRateRange2.min, findBestMatchingRefreshRateRange2.max);
                }
                if (this.mVsyncLowLightBlockingVoteEnabled) {
                    vote2 = com.android.server.display.mode.Vote.forSupportedModesAndDisableRefreshRateSwitching(java.util.List.of(new com.android.server.display.mode.SupportedModesVote.SupportedMode(60.0f, 60.0f), new com.android.server.display.mode.SupportedModesVote.SupportedMode(120.0f, 120.0f)));
                } else {
                    vote2 = com.android.server.display.mode.Vote.forDisableRefreshRateSwitching();
                }
            }
            if (hasValidHighZone() && isInsideHighZone(this.mBrightness, this.mAmbientLux)) {
                z = true;
            }
            if (z) {
                vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(this.mRefreshRateInHighZone, this.mRefreshRateInHighZone);
                if (this.mHighZoneRefreshRateForThermals != null && (findBestMatchingRefreshRateRange = com.android.server.display.mode.SkinThermalStatusObserver.findBestMatchingRefreshRateRange(this.mThermalStatus, this.mHighZoneRefreshRateForThermals)) != null) {
                    vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(findBestMatchingRefreshRateRange.min, findBestMatchingRefreshRateRange.max);
                }
                vote2 = com.android.server.display.mode.Vote.forDisableRefreshRateSwitching();
            }
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "Display brightness " + this.mBrightness + ", ambient lux " + this.mAmbientLux + ", Vote " + vote);
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(1, vote);
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateGlobalVote(14, vote2);
        }

        private boolean hasValidLowZone() {
            return this.mRefreshRateInLowZone > 0 && (this.mShouldObserveDisplayLowChange || this.mShouldObserveAmbientLowChange);
        }

        private boolean hasValidHighZone() {
            return this.mRefreshRateInHighZone > 0 && (this.mShouldObserveDisplayHighChange || this.mShouldObserveAmbientHighChange);
        }

        private void updateDefaultDisplayState() {
            android.view.Display display = this.mInjector.getDisplay(0);
            if (display == null) {
                return;
            }
            setDefaultDisplayState(display.getState());
        }

        @com.android.internal.annotations.VisibleForTesting
        void setDefaultDisplayState(int i) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "setDefaultDisplayState: mDefaultDisplayState = " + this.mDefaultDisplayState + ", state = " + i);
            }
            if (this.mDefaultDisplayState != i) {
                this.mDefaultDisplayState = i;
                updateSensorStatus();
            }
        }

        private void updateSensorStatus() {
            boolean z;
            if (this.mSensorManager == null || this.mLightSensorListener == null) {
                return;
            }
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "updateSensorStatus: mShouldObserveAmbientLowChange = " + this.mShouldObserveAmbientLowChange + ", mShouldObserveAmbientHighChange = " + this.mShouldObserveAmbientHighChange);
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "updateSensorStatus: mLowPowerModeEnabled = " + this.mLowPowerModeEnabled + ", mRefreshRateChangeable = " + this.mRefreshRateChangeable);
            }
            if ((this.mShouldObserveAmbientLowChange || this.mShouldObserveAmbientHighChange) && isDeviceActive() && !this.mLowPowerModeEnabled && this.mRefreshRateChangeable) {
                registerLightSensor();
                z = (this.mLowZoneRefreshRateForThermals == null && this.mHighZoneRefreshRateForThermals == null) ? false : true;
            } else {
                unregisterSensorListener();
                z = false;
            }
            if (z && !this.mThermalRegistered) {
                this.mThermalRegistered = this.mInjector.registerThermalServiceListener(this.mThermalListener);
                return;
            }
            if (!z && this.mThermalRegistered) {
                this.mInjector.unregisterThermalServiceListener(this.mThermalListener);
                this.mThermalRegistered = false;
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    this.mThermalStatus = 0;
                }
            }
        }

        private void registerLightSensor() {
            if (this.mRegisteredLightSensor == this.mLightSensor) {
                return;
            }
            if (this.mRegisteredLightSensor != null) {
                unregisterSensorListener();
            }
            this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, 250000, this.mHandler);
            this.mRegisteredLightSensor = this.mLightSensor;
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "updateSensorStatus: registerListener");
            }
        }

        private void unregisterSensorListener() {
            this.mLightSensorListener.removeCallbacks();
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
            this.mRegisteredLightSensor = null;
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "updateSensorStatus: unregisterListener");
            }
        }

        private boolean isDeviceActive() {
            return this.mDefaultDisplayState == 2;
        }

        private float getBrightness(int i) {
            android.hardware.display.BrightnessInfo brightnessInfo = this.mInjector.getBrightnessInfo(i);
            if (brightnessInfo != null) {
                return brightnessInfo.adjustedBrightness;
            }
            return Float.NaN;
        }

        private final class LightSensorEventListener implements android.hardware.SensorEventListener {
            private static final int INJECT_EVENTS_INTERVAL_MS = 250;
            private final java.lang.Runnable mInjectSensorEventRunnable;
            private float mLastSensorData;
            private boolean mLoggingEnabled;
            private long mTimestamp;

            private LightSensorEventListener() {
                this.mInjectSensorEventRunnable = new java.lang.Runnable() { // from class: com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.processSensorData(android.os.SystemClock.uptimeMillis());
                        if (com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.isDifferentZone(com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.mLastSensorData, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mLowAmbientBrightnessThresholds) || com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.isDifferentZone(com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.mLastSensorData, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHighAmbientBrightnessThresholds)) {
                            com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHandler.postDelayed(com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.LightSensorEventListener.this.mInjectSensorEventRunnable, 250L);
                        }
                    }
                };
            }

            public void dumpLocked(java.io.PrintWriter printWriter) {
                printWriter.println("    mLastSensorData: " + this.mLastSensorData);
                printWriter.println("    mTimestamp: " + formatTimestamp(this.mTimestamp));
            }

            public void setLoggingEnabled(boolean z) {
                if (this.mLoggingEnabled == z) {
                    return;
                }
                this.mLoggingEnabled = z;
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
                this.mLastSensorData = sensorEvent.values[0];
                if (this.mLoggingEnabled) {
                    android.util.Slog.d(com.android.server.display.mode.DisplayModeDirector.TAG, "On sensor changed: " + this.mLastSensorData);
                }
                boolean isDifferentZone = isDifferentZone(this.mLastSensorData, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mLowAmbientBrightnessThresholds);
                boolean isDifferentZone2 = isDifferentZone(this.mLastSensorData, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux, com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHighAmbientBrightnessThresholds);
                if (((isDifferentZone && this.mLastSensorData < com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux) || (isDifferentZone2 && this.mLastSensorData > com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux)) && com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter != null) {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter.clear();
                }
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                this.mTimestamp = java.lang.System.currentTimeMillis();
                if (com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter != null) {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter.addValue(uptimeMillis, this.mLastSensorData);
                }
                com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHandler.removeCallbacks(this.mInjectSensorEventRunnable);
                processSensorData(uptimeMillis);
                if ((isDifferentZone && this.mLastSensorData > com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux) || (isDifferentZone2 && this.mLastSensorData < com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux)) {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHandler.postDelayed(this.mInjectSensorEventRunnable, 250L);
                }
            }

            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
            }

            public void removeCallbacks() {
                com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mHandler.removeCallbacks(this.mInjectSensorEventRunnable);
            }

            private java.lang.String formatTimestamp(long j) {
                return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).format(new java.util.Date(j));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void processSensorData(long j) {
                if (com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter != null) {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux = com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientFilter.getEstimate(j);
                } else {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.mAmbientLux = this.mLastSensorData;
                }
                synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                    com.android.server.display.mode.DisplayModeDirector.BrightnessObserver.this.onBrightnessChangedLocked();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isDifferentZone(float f, float f2, float[] fArr) {
                for (float f3 : fArr) {
                    if (f <= f3 && f2 > f3) {
                        return true;
                    }
                    if (f > f3 && f2 <= f3) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    private class UdfpsObserver extends android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.Stub {
        private final android.util.SparseBooleanArray mAuthenticationPossible;
        private final android.util.SparseBooleanArray mUdfpsRefreshRateEnabled;

        private UdfpsObserver() {
            this.mUdfpsRefreshRateEnabled = new android.util.SparseBooleanArray();
            this.mAuthenticationPossible = new android.util.SparseBooleanArray();
        }

        public void observe() {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.display.mode.DisplayModeDirector.this.mInjector.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null && !com.android.server.display.mode.DisplayModeDirector.this.mContext.getResources().getBoolean(android.R.bool.config_hideNavBarForKeyboard)) {
                statusBarManagerInternal.setUdfpsRefreshRateCallback(this);
            }
        }

        public void onRequestEnabled(int i) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                this.mUdfpsRefreshRateEnabled.put(i, true);
                updateVoteLocked(i, true, 17);
            }
        }

        public void onRequestDisabled(int i) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                this.mUdfpsRefreshRateEnabled.put(i, false);
                updateVoteLocked(i, false, 17);
            }
        }

        public void onAuthenticationPossible(int i, boolean z) {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                this.mAuthenticationPossible.put(i, z);
                updateVoteLocked(i, z, 11);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void updateVoteLocked(int i, boolean z, int i2) {
            com.android.server.display.mode.Vote vote;
            if (z) {
                float maxRefreshRateLocked = com.android.server.display.mode.DisplayModeDirector.this.getMaxRefreshRateLocked(i);
                vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(maxRefreshRateLocked, maxRefreshRateLocked);
            } else {
                vote = null;
            }
            com.android.server.display.mode.DisplayModeDirector.this.mVotesStorage.updateVote(i, i2, vote);
        }

        void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("  UdfpsObserver");
            printWriter.println("    mUdfpsRefreshRateEnabled: ");
            for (int i = 0; i < this.mUdfpsRefreshRateEnabled.size(); i++) {
                printWriter.println("      Display " + this.mUdfpsRefreshRateEnabled.keyAt(i) + ": " + (this.mUdfpsRefreshRateEnabled.valueAt(i) ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED));
            }
            printWriter.println("    mAuthenticationPossible: ");
            for (int i2 = 0; i2 < this.mAuthenticationPossible.size(); i2++) {
                printWriter.println("      Display " + this.mAuthenticationPossible.keyAt(i2) + ": " + (this.mAuthenticationPossible.valueAt(i2) ? "possible" : "impossible"));
            }
        }
    }

    protected static final class SensorObserver implements com.android.server.sensors.SensorManagerInternal.ProximityActiveListener, android.hardware.display.DisplayManager.DisplayListener {
        private final android.content.Context mContext;
        private android.hardware.display.DisplayManager mDisplayManager;
        private android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
        private final com.android.server.display.mode.DisplayModeDirector.Injector mInjector;
        private final com.android.server.display.mode.VotesStorage mVotesStorage;
        private final java.lang.String mProximitySensorName = null;
        private final java.lang.String mProximitySensorType = "android.sensor.proximity";

        @com.android.internal.annotations.GuardedBy({"mSensorObserverLock"})
        private final android.util.SparseBooleanArray mDozeStateByDisplay = new android.util.SparseBooleanArray();
        private final java.lang.Object mSensorObserverLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mSensorObserverLock"})
        private boolean mIsProxActive = false;

        SensorObserver(android.content.Context context, com.android.server.display.mode.VotesStorage votesStorage, com.android.server.display.mode.DisplayModeDirector.Injector injector) {
            this.mContext = context;
            this.mVotesStorage = votesStorage;
            this.mInjector = injector;
        }

        @Override // com.android.server.sensors.SensorManagerInternal.ProximityActiveListener
        public void onProximityActive(boolean z) {
            synchronized (this.mSensorObserverLock) {
                try {
                    if (this.mIsProxActive != z) {
                        this.mIsProxActive = z;
                        recalculateVotesLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void observe() {
            this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
            this.mDisplayManagerInternal = this.mInjector.getDisplayManagerInternal();
            this.mInjector.getSensorManagerInternal().addProximityActiveListener(com.android.internal.os.BackgroundThread.getExecutor(), this);
            synchronized (this.mSensorObserverLock) {
                try {
                    for (android.view.Display display : this.mInjector.getDisplays()) {
                        this.mDozeStateByDisplay.put(display.getDisplayId(), this.mInjector.isDozeState(display));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mInjector.registerDisplayListener(this, com.android.internal.os.BackgroundThread.getHandler(), 7L);
        }

        private void recalculateVotesLocked() {
            com.android.server.display.mode.Vote vote;
            android.view.SurfaceControl.RefreshRateRange refreshRateForDisplayAndSensor;
            for (android.view.Display display : this.mInjector.getDisplays()) {
                int displayId = display.getDisplayId();
                if (this.mIsProxActive && !this.mDozeStateByDisplay.get(displayId) && (refreshRateForDisplayAndSensor = this.mDisplayManagerInternal.getRefreshRateForDisplayAndSensor(displayId, this.mProximitySensorName, "android.sensor.proximity")) != null) {
                    vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(refreshRateForDisplayAndSensor.min, refreshRateForDisplayAndSensor.max);
                } else {
                    vote = null;
                }
                this.mVotesStorage.updateVote(displayId, 16, vote);
            }
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  SensorObserver");
            synchronized (this.mSensorObserverLock) {
                try {
                    printWriter.println("    mIsProxActive=" + this.mIsProxActive);
                    printWriter.println("    mDozeStateByDisplay:");
                    for (int i = 0; i < this.mDozeStateByDisplay.size(); i++) {
                        printWriter.println("      " + this.mDozeStateByDisplay.keyAt(i) + " -> " + this.mDozeStateByDisplay.valueAt(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            boolean isDozeState = this.mInjector.isDozeState(this.mInjector.getDisplay(i));
            synchronized (this.mSensorObserverLock) {
                this.mDozeStateByDisplay.put(i, isDozeState);
                recalculateVotesLocked();
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            boolean z = this.mDozeStateByDisplay.get(i);
            synchronized (this.mSensorObserverLock) {
                try {
                    this.mDozeStateByDisplay.put(i, this.mInjector.isDozeState(this.mInjector.getDisplay(i)));
                    if (z != this.mDozeStateByDisplay.get(i)) {
                        recalculateVotesLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            synchronized (this.mSensorObserverLock) {
                this.mDozeStateByDisplay.delete(i);
                recalculateVotesLocked();
            }
        }
    }

    public class HbmObserver implements android.hardware.display.DisplayManager.DisplayListener {
        private final com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings mDeviceConfigDisplaySettings;
        private android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
        private final android.os.Handler mHandler;
        private final com.android.server.display.mode.DisplayModeDirector.Injector mInjector;
        private int mRefreshRateInHbmHdr;
        private int mRefreshRateInHbmSunlight;
        private final com.android.server.display.mode.VotesStorage mVotesStorage;
        private final android.util.SparseIntArray mHbmMode = new android.util.SparseIntArray();
        private final android.util.SparseBooleanArray mHbmActive = new android.util.SparseBooleanArray();

        HbmObserver(com.android.server.display.mode.DisplayModeDirector.Injector injector, com.android.server.display.mode.VotesStorage votesStorage, android.os.Handler handler, com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings deviceConfigDisplaySettings) {
            this.mInjector = injector;
            this.mVotesStorage = votesStorage;
            this.mHandler = handler;
            this.mDeviceConfigDisplaySettings = deviceConfigDisplaySettings;
        }

        public void setupHdrRefreshRates(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            this.mRefreshRateInHbmHdr = this.mDeviceConfigDisplaySettings.getRefreshRateInHbmHdr(displayDeviceConfig);
            this.mRefreshRateInHbmSunlight = this.mDeviceConfigDisplaySettings.getRefreshRateInHbmSunlight(displayDeviceConfig);
        }

        public void observe() {
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                setupHdrRefreshRates(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig);
            }
            this.mDisplayManagerInternal = this.mInjector.getDisplayManagerInternal();
            this.mInjector.registerDisplayListener(this, this.mHandler, 10L);
        }

        @com.android.internal.annotations.VisibleForTesting
        int getRefreshRateInHbmSunlight() {
            return this.mRefreshRateInHbmSunlight;
        }

        @com.android.internal.annotations.VisibleForTesting
        int getRefreshRateInHbmHdr() {
            return this.mRefreshRateInHbmHdr;
        }

        public void onDeviceConfigRefreshRateInHbmSunlightChanged(int i) {
            if (i != this.mRefreshRateInHbmSunlight) {
                this.mRefreshRateInHbmSunlight = i;
                onDeviceConfigRefreshRateInHbmChanged();
            }
        }

        public void onDeviceConfigRefreshRateInHbmHdrChanged(int i) {
            if (i != this.mRefreshRateInHbmHdr) {
                this.mRefreshRateInHbmHdr = i;
                onDeviceConfigRefreshRateInHbmChanged();
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            this.mVotesStorage.updateVote(i, 2, null);
            this.mHbmMode.delete(i);
            this.mHbmActive.delete(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            android.hardware.display.BrightnessInfo brightnessInfo = this.mInjector.getBrightnessInfo(i);
            if (brightnessInfo == null) {
                return;
            }
            int i2 = brightnessInfo.highBrightnessMode;
            boolean z = i2 != 0 && brightnessInfo.adjustedBrightness > brightnessInfo.highBrightnessTransitionPoint;
            if (i2 == this.mHbmMode.get(i) && z == this.mHbmActive.get(i)) {
                return;
            }
            this.mHbmMode.put(i, i2);
            this.mHbmActive.put(i, z);
            recalculateVotesForDisplay(i);
        }

        private void onDeviceConfigRefreshRateInHbmChanged() {
            int[] copyKeys = this.mHbmMode.copyKeys();
            if (copyKeys != null) {
                for (int i : copyKeys) {
                    recalculateVotesForDisplay(i);
                }
            }
        }

        private void recalculateVotesForDisplay(int i) {
            int i2 = 0;
            com.android.server.display.mode.Vote vote = null;
            if (this.mHbmActive.get(i, false)) {
                int i3 = this.mHbmMode.get(i, 0);
                if (i3 == 1) {
                    if (this.mRefreshRateInHbmSunlight > 0) {
                        vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(this.mRefreshRateInHbmSunlight, this.mRefreshRateInHbmSunlight);
                    } else {
                        java.util.List refreshRateLimitations = this.mDisplayManagerInternal.getRefreshRateLimitations(i);
                        while (true) {
                            if (refreshRateLimitations == null || i2 >= refreshRateLimitations.size()) {
                                break;
                            }
                            android.hardware.display.DisplayManagerInternal.RefreshRateLimitation refreshRateLimitation = (android.hardware.display.DisplayManagerInternal.RefreshRateLimitation) refreshRateLimitations.get(i2);
                            if (refreshRateLimitation.type != 1) {
                                i2++;
                            } else {
                                vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(refreshRateLimitation.range.min, refreshRateLimitation.range.max);
                                break;
                            }
                        }
                    }
                } else if (i3 == 2 && this.mRefreshRateInHbmHdr > 0) {
                    vote = com.android.server.display.mode.Vote.forPhysicalRefreshRates(this.mRefreshRateInHbmHdr, this.mRefreshRateInHbmHdr);
                } else {
                    android.util.Slog.w(com.android.server.display.mode.DisplayModeDirector.TAG, "Unexpected HBM mode " + i3 + " for display ID " + i);
                }
            }
            this.mVotesStorage.updateVote(i, 2, vote);
        }

        void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.println("   HbmObserver");
            printWriter.println("     mHbmMode: " + this.mHbmMode);
            printWriter.println("     mHbmActive: " + this.mHbmActive);
            printWriter.println("     mRefreshRateInHbmSunlight: " + this.mRefreshRateInHbmSunlight);
            printWriter.println("     mRefreshRateInHbmHdr: " + this.mRefreshRateInHbmHdr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeviceConfigDisplaySettings implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private DeviceConfigDisplaySettings() {
        }

        public void startListening() {
            com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.addOnPropertiesChangedListener(com.android.internal.os.BackgroundThread.getExecutor(), this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getRefreshRateInHbmHdr(final com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            return getRefreshRate(new java.util.function.IntSupplier() { // from class: com.android.server.display.mode.DisplayModeDirector$DeviceConfigDisplaySettings$$ExternalSyntheticLambda0
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int lambda$getRefreshRateInHbmHdr$0;
                    lambda$getRefreshRateInHbmHdr$0 = com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings.this.lambda$getRefreshRateInHbmHdr$0();
                    return lambda$getRefreshRateInHbmHdr$0;
                }
            }, new java.util.function.IntSupplier() { // from class: com.android.server.display.mode.DisplayModeDirector$DeviceConfigDisplaySettings$$ExternalSyntheticLambda1
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int defaultRefreshRateInHbmHdr;
                    defaultRefreshRateInHbmHdr = com.android.server.display.DisplayDeviceConfig.this.getDefaultRefreshRateInHbmHdr();
                    return defaultRefreshRateInHbmHdr;
                }
            }, android.R.integer.config_defaultPowerStatsThrottlePeriodCpu, displayDeviceConfig);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getRefreshRateInHbmHdr$0() {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInHbmHdr();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getRefreshRateInHbmSunlight(final com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            return getRefreshRate(new java.util.function.IntSupplier() { // from class: com.android.server.display.mode.DisplayModeDirector$DeviceConfigDisplaySettings$$ExternalSyntheticLambda2
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int lambda$getRefreshRateInHbmSunlight$2;
                    lambda$getRefreshRateInHbmSunlight$2 = com.android.server.display.mode.DisplayModeDirector.DeviceConfigDisplaySettings.this.lambda$getRefreshRateInHbmSunlight$2();
                    return lambda$getRefreshRateInHbmSunlight$2;
                }
            }, new java.util.function.IntSupplier() { // from class: com.android.server.display.mode.DisplayModeDirector$DeviceConfigDisplaySettings$$ExternalSyntheticLambda3
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int defaultRefreshRateInHbmSunlight;
                    defaultRefreshRateInHbmSunlight = com.android.server.display.DisplayDeviceConfig.this.getDefaultRefreshRateInHbmSunlight();
                    return defaultRefreshRateInHbmSunlight;
                }
            }, android.R.integer.config_defaultPowerStatsThrottlePeriodMobileRadio, displayDeviceConfig);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getRefreshRateInHbmSunlight$2() {
            return com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInHbmSunlight();
        }

        private int getRefreshRate(java.util.function.IntSupplier intSupplier, java.util.function.IntSupplier intSupplier2, int i, com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            int i2;
            try {
                i2 = intSupplier.getAsInt();
            } catch (java.lang.NullPointerException e) {
                i2 = -1;
            }
            if (i2 == -1) {
                if (displayDeviceConfig == null) {
                    return com.android.server.display.mode.DisplayModeDirector.this.mContext.getResources().getInteger(i);
                }
                return intSupplier2.getAsInt();
            }
            return i2;
        }

        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            float peakRefreshRateDefault = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getPeakRefreshRateDefault();
            com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(3, peakRefreshRateDefault == -1.0f ? null : java.lang.Float.valueOf(peakRefreshRateDefault)).sendToTarget();
            float[] lowDisplayBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowDisplayBrightnessThresholds();
            float[] lowAmbientBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getLowAmbientBrightnessThresholds();
            int refreshRateInLowZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInLowZone();
            com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(2, new android.util.Pair(lowDisplayBrightnessThresholds, lowAmbientBrightnessThresholds)).sendToTarget();
            com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(4, refreshRateInLowZone, 0).sendToTarget();
            float[] highDisplayBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighDisplayBrightnessThresholds();
            float[] highAmbientBrightnessThresholds = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getHighAmbientBrightnessThresholds();
            int refreshRateInHighZone = com.android.server.display.mode.DisplayModeDirector.this.mConfigParameterProvider.getRefreshRateInHighZone();
            com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(6, new android.util.Pair(highDisplayBrightnessThresholds, highAmbientBrightnessThresholds)).sendToTarget();
            com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(5, refreshRateInHighZone, 0).sendToTarget();
            synchronized (com.android.server.display.mode.DisplayModeDirector.this.mLock) {
                com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(7, getRefreshRateInHbmSunlight(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig), 0).sendToTarget();
                com.android.server.display.mode.DisplayModeDirector.this.mHandler.obtainMessage(8, getRefreshRateInHbmHdr(com.android.server.display.mode.DisplayModeDirector.this.mDefaultDisplayDeviceConfig), 0).sendToTarget();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class RealInjector implements com.android.server.display.mode.DisplayModeDirector.Injector {
        private final android.content.Context mContext;
        private android.hardware.display.DisplayManager mDisplayManager;

        RealInjector(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        @android.annotation.NonNull
        public android.provider.DeviceConfigInterface getDeviceConfig() {
            return android.provider.DeviceConfigInterface.REAL;
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public void registerPeakRefreshRateObserver(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
            contentResolver.registerContentObserver(com.android.server.display.mode.DisplayModeDirector.Injector.PEAK_REFRESH_RATE_URI, false, contentObserver, 0);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public void registerMinRefreshRateObserver(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
            contentResolver.registerContentObserver(com.android.server.display.mode.DisplayModeDirector.Injector.MIN_REFRESH_RATE_URI, false, contentObserver, 0);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler) {
            getDisplayManager().registerDisplayListener(displayListener, handler);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler, long j) {
            getDisplayManager().registerDisplayListener(displayListener, handler, j);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public android.view.Display getDisplay(int i) {
            return getDisplayManager().getDisplay(i);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public android.view.Display[] getDisplays() {
            return getDisplayManager().getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public boolean getDisplayInfo(int i, android.view.DisplayInfo displayInfo) {
            android.view.Display display = getDisplayManager().getDisplay(i);
            if (display == null) {
                return false;
            }
            return display.getDisplayInfo(displayInfo);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public android.hardware.display.BrightnessInfo getBrightnessInfo(int i) {
            android.view.Display display = getDisplayManager().getDisplay(i);
            if (display != null) {
                return display.getBrightnessInfo();
            }
            return null;
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public boolean isDozeState(android.view.Display display) {
            if (display == null) {
                return false;
            }
            return android.view.Display.isDozeState(display.getState());
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public boolean registerThermalServiceListener(android.os.IThermalEventListener iThermalEventListener) {
            android.os.IThermalService thermalService = getThermalService();
            if (thermalService == null) {
                android.util.Slog.w(com.android.server.display.mode.DisplayModeDirector.TAG, "Could not observe thermal status. Service not available");
                return false;
            }
            try {
                thermalService.registerThermalEventListenerWithType(iThermalEventListener, 3);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.display.mode.DisplayModeDirector.TAG, "Failed to register thermal status listener", e);
                return false;
            }
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public void unregisterThermalServiceListener(android.os.IThermalEventListener iThermalEventListener) {
            android.os.IThermalService thermalService = getThermalService();
            if (thermalService == null) {
                android.util.Slog.w(com.android.server.display.mode.DisplayModeDirector.TAG, "Could not unregister thermal status. Service not available");
            }
            try {
                thermalService.unregisterThermalEventListener(iThermalEventListener);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.display.mode.DisplayModeDirector.TAG, "Failed to unregister thermal status listener", e);
            }
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public boolean supportsFrameRateOverride() {
            return ((java.lang.Boolean) android.sysprop.SurfaceFlingerProperties.enable_frame_rate_override().orElse(true)).booleanValue();
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public android.hardware.display.DisplayManagerInternal getDisplayManagerInternal() {
            return (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public com.android.server.statusbar.StatusBarManagerInternal getStatusBarManagerInternal() {
            return (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public com.android.server.sensors.SensorManagerInternal getSensorManagerInternal() {
            return (com.android.server.sensors.SensorManagerInternal) com.android.server.LocalServices.getService(com.android.server.sensors.SensorManagerInternal.class);
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.Injector
        public com.android.server.display.mode.VotesStatsReporter getVotesStatsReporter(boolean z) {
            return new com.android.server.display.mode.VotesStatsReporter(supportsFrameRateOverride(), z);
        }

        private android.hardware.display.DisplayManager getDisplayManager() {
            if (this.mDisplayManager == null) {
                this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
            }
            return this.mDisplayManager;
        }

        private android.os.IThermalService getThermalService() {
            return android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
        }
    }
}
