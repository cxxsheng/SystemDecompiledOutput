package com.android.server.display;

/* loaded from: classes.dex */
final class DisplayPowerController implements com.android.server.display.AutomaticBrightnessController.Callbacks, com.android.server.display.whitebalance.DisplayWhiteBalanceController.Callbacks, com.android.server.display.DisplayPowerControllerInterface {
    private static final int BRIGHTNESS_CHANGE_STATSD_REPORT_INTERVAL_MS = 500;
    private static final int COLOR_FADE_OFF_ANIMATION_DURATION_MILLIS = 400;
    private static final int COLOR_FADE_ON_ANIMATION_DURATION_MILLIS = 250;
    private static final int MSG_BOOT_COMPLETED = 13;
    private static final int MSG_BRIGHTNESS_RAMP_DONE = 10;
    private static final int MSG_CONFIGURE_BRIGHTNESS = 4;
    private static final int MSG_OFFLOADING_SCREEN_ON_UNBLOCKED = 18;
    private static final int MSG_SCREEN_OFF_UNBLOCKED = 3;
    private static final int MSG_SCREEN_ON_UNBLOCKED = 2;
    private static final int MSG_SET_BRIGHTNESS_FROM_OFFLOAD = 17;
    private static final int MSG_SET_DWBC_COLOR_OVERRIDE = 15;
    private static final int MSG_SET_DWBC_LOGGING_ENABLED = 16;
    private static final int MSG_SET_DWBC_STRONG_MODE = 14;
    private static final int MSG_SET_TEMPORARY_AUTO_BRIGHTNESS_ADJUSTMENT = 6;
    private static final int MSG_SET_TEMPORARY_BRIGHTNESS = 5;
    private static final int MSG_STATSD_HBM_BRIGHTNESS = 11;
    private static final int MSG_STOP = 7;
    private static final int MSG_SWITCH_USER = 12;
    private static final int MSG_UPDATE_BRIGHTNESS = 8;
    private static final int MSG_UPDATE_POWER_STATE = 1;
    private static final int MSG_UPDATE_RBC = 9;
    private static final int RAMP_STATE_SKIP_AUTOBRIGHT = 2;
    private static final int RAMP_STATE_SKIP_INITIAL = 1;
    private static final int RAMP_STATE_SKIP_NONE = 0;
    private static final int REPORTED_TO_POLICY_SCREEN_OFF = 0;
    private static final int REPORTED_TO_POLICY_SCREEN_ON = 2;
    private static final int REPORTED_TO_POLICY_SCREEN_TURNING_OFF = 3;
    private static final int REPORTED_TO_POLICY_SCREEN_TURNING_ON = 1;
    private static final int REPORTED_TO_POLICY_UNREPORTED = -1;
    private static final int RINGBUFFER_MAX = 100;
    private static final int RINGBUFFER_RBC_MAX = 20;
    private static final float SCREEN_ANIMATION_RATE_MINIMUM = 0.0f;
    private static final java.lang.String SCREEN_OFF_BLOCKED_TRACE_NAME = "Screen off blocked";
    private static final java.lang.String SCREEN_ON_BLOCKED_BY_DISPLAYOFFLOAD_TRACE_NAME = "Screen on blocked by displayoffload";
    private static final java.lang.String SCREEN_ON_BLOCKED_TRACE_NAME = "Screen on blocked";
    private static final boolean USE_COLOR_FADE_ON_ANIMATION = false;
    private boolean mAppliedDimming;
    private boolean mAppliedThrottling;

    @android.annotation.Nullable
    private com.android.server.display.AutomaticBrightnessController mAutomaticBrightnessController;
    private final com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy mAutomaticBrightnessStrategy;

    @android.annotation.Nullable
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private final com.android.server.display.DisplayBlanker mBlanker;
    private boolean mBootCompleted;
    private final boolean mBrightnessBucketsInDozeConfig;
    private final com.android.server.display.brightness.clamper.BrightnessClamperController mBrightnessClamperController;
    private com.android.internal.util.RingBuffer<com.android.server.display.brightness.BrightnessEvent> mBrightnessEventRingBuffer;
    private long mBrightnessRampDecreaseMaxTimeIdleMillis;
    private long mBrightnessRampDecreaseMaxTimeMillis;
    private long mBrightnessRampIncreaseMaxTimeIdleMillis;
    private long mBrightnessRampIncreaseMaxTimeMillis;
    private float mBrightnessRampRateFastDecrease;
    private float mBrightnessRampRateFastIncrease;
    private float mBrightnessRampRateSlowDecrease;
    private float mBrightnessRampRateSlowDecreaseIdle;
    private float mBrightnessRampRateSlowIncrease;
    private float mBrightnessRampRateSlowIncreaseIdle;
    private final com.android.server.display.BrightnessRangeController mBrightnessRangeController;
    private final com.android.server.display.BrightnessThrottler mBrightnessThrottler;

    @android.annotation.Nullable
    private final com.android.server.display.BrightnessTracker mBrightnessTracker;

    @android.annotation.Nullable
    private final com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal mCdsi;
    private final com.android.server.display.DisplayPowerController.Clock mClock;
    private final boolean mColorFadeEnabled;
    private final boolean mColorFadeFadesConfig;
    private android.animation.ObjectAnimator mColorFadeOffAnimator;
    private android.animation.ObjectAnimator mColorFadeOnAnimator;
    private final android.content.Context mContext;
    private final boolean mDisplayBlanksAfterDozeConfig;
    private final com.android.server.display.brightness.DisplayBrightnessController mDisplayBrightnessController;
    private com.android.server.display.DisplayDevice mDisplayDevice;
    private com.android.server.display.DisplayDeviceConfig mDisplayDeviceConfig;
    private final int mDisplayId;
    private android.hardware.display.DisplayManagerInternal.DisplayOffloadSession mDisplayOffloadSession;
    private final com.android.server.display.DisplayPowerProximityStateController mDisplayPowerProximityStateController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDisplayReadyLocked;
    private final com.android.server.display.state.DisplayStateController mDisplayStateController;
    private int mDisplayStatsId;

    @android.annotation.Nullable
    private final com.android.server.display.whitebalance.DisplayWhiteBalanceController mDisplayWhiteBalanceController;

    @android.annotation.Nullable
    private final com.android.server.display.whitebalance.DisplayWhiteBalanceSettings mDisplayWhiteBalanceSettings;
    private boolean mDozing;
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;
    private final com.android.server.display.DisplayPowerController.DisplayControllerHandler mHandler;
    private float mInitialAutoBrightness;
    private final com.android.server.display.DisplayPowerController.Injector mInjector;
    private boolean mIsDisplayInternal;
    private boolean mIsEnabled;
    private boolean mIsInTransition;
    private boolean mIsRbcActive;
    private final com.android.server.display.brightness.BrightnessEvent mLastBrightnessEvent;
    private android.hardware.Sensor mLightSensor;
    private final com.android.server.lights.LightsManager mLights;
    private final com.android.server.display.LogicalDisplay mLogicalDisplay;
    private float[] mNitsRange;
    private final java.lang.Runnable mOnBrightnessChangeRunnable;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPendingRequestChangedLocked;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.display.DisplayManagerInternal.DisplayPowerRequest mPendingRequestLocked;
    private boolean mPendingScreenOff;
    private com.android.server.display.DisplayPowerController.ScreenOffUnblocker mPendingScreenOffUnblocker;
    private com.android.server.display.DisplayPowerController.ScreenOnUnblocker mPendingScreenOnUnblocker;
    private java.lang.Runnable mPendingScreenOnUnblockerByDisplayOffload;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPendingUpdatePowerStateLocked;
    private android.hardware.display.DisplayManagerInternal.DisplayPowerRequest mPowerRequest;
    private com.android.server.display.DisplayPowerState mPowerState;
    private final float mScreenBrightnessDozeConfig;
    private com.android.server.display.RampAnimator.DualRampAnimator<com.android.server.display.DisplayPowerState> mScreenBrightnessRampAnimator;
    private long mScreenOffBlockStartRealTime;
    private android.hardware.Sensor mScreenOffBrightnessSensor;

    @android.annotation.Nullable
    private com.android.server.display.ScreenOffBrightnessSensorController mScreenOffBrightnessSensorController;
    private long mScreenOnBlockByDisplayOffloadStartRealTime;
    private long mScreenOnBlockStartRealTime;
    private boolean mScreenTurningOnWasBlockedByDisplayOffload;
    private final android.hardware.SensorManager mSensorManager;
    private final com.android.server.display.DisplayPowerController.SettingsObserver mSettingsObserver;
    private final boolean mSkipScreenOnBrightnessRamp;
    private boolean mStopped;
    private final java.lang.String mTag;
    private final com.android.server.display.brightness.BrightnessEvent mTempBrightnessEvent;
    private java.lang.String mThermalBrightnessThrottlingDataId;
    private java.lang.String mUniqueDisplayId;
    private boolean mUseSoftwareAutoBrightnessConfig;
    private final com.android.server.display.WakelockController mWakelockController;
    private final com.android.server.policy.WindowManagerPolicy mWindowManagerPolicy;
    private static final java.lang.String TAG = "DisplayPowerController2";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static final float[] BRIGHTNESS_RANGE_BOUNDARIES = {0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 60.0f, 70.0f, 80.0f, 90.0f, 100.0f, 200.0f, 300.0f, 400.0f, 500.0f, 600.0f, 700.0f, 800.0f, 900.0f, 1000.0f, 1200.0f, 1400.0f, 1600.0f, 1800.0f, 2000.0f, 2250.0f, 2500.0f, 2750.0f, 3000.0f};
    private static final int[] BRIGHTNESS_RANGE_INDEX = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
    private final java.lang.Object mLock = new java.lang.Object();
    private int mLeadDisplayId = -1;

    @com.android.internal.annotations.GuardedBy({"mCachedBrightnessInfo"})
    private final com.android.server.display.DisplayPowerController.CachedBrightnessInfo mCachedBrightnessInfo = new com.android.server.display.DisplayPowerController.CachedBrightnessInfo();
    private int mReportedScreenStateToPolicy = -1;
    private final com.android.server.display.brightness.BrightnessReason mBrightnessReason = new com.android.server.display.brightness.BrightnessReason();
    private final com.android.server.display.brightness.BrightnessReason mBrightnessReasonTemp = new com.android.server.display.brightness.BrightnessReason();
    private float mLastStatsBrightness = 0.0f;
    private final com.android.internal.util.RingBuffer<com.android.server.display.brightness.BrightnessEvent> mRbcEventRingBuffer = new com.android.internal.util.RingBuffer<>(com.android.server.display.brightness.BrightnessEvent.class, 20);
    private int mSkipRampState = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<com.android.server.display.DisplayPowerControllerInterface> mDisplayBrightnessFollowers = new android.util.SparseArray<>();
    private final android.animation.Animator.AnimatorListener mAnimatorListener = new android.animation.Animator.AnimatorListener() { // from class: com.android.server.display.DisplayPowerController.2
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            com.android.server.display.DisplayPowerController.this.sendUpdatePowerState();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
        }
    };
    private final com.android.server.display.RampAnimator.Listener mRampAnimatorListener = new com.android.server.display.RampAnimator.Listener() { // from class: com.android.server.display.DisplayPowerController.3
        @Override // com.android.server.display.RampAnimator.Listener
        public void onAnimationEnd() {
            com.android.server.display.DisplayPowerController.this.sendUpdatePowerState();
            com.android.server.display.DisplayPowerController.this.mHandler.sendMessageAtTime(com.android.server.display.DisplayPowerController.this.mHandler.obtainMessage(10), com.android.server.display.DisplayPowerController.this.mClock.uptimeMillis());
        }
    };
    private final java.lang.Runnable mCleanListener = new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda7
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.display.DisplayPowerController.this.sendUpdatePowerState();
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long uptimeMillis();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0292  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    DisplayPowerController(android.content.Context context, com.android.server.display.DisplayPowerController.Injector injector, android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, android.os.Handler handler, android.hardware.SensorManager sensorManager, com.android.server.display.DisplayBlanker displayBlanker, com.android.server.display.LogicalDisplay logicalDisplay, com.android.server.display.BrightnessTracker brightnessTracker, com.android.server.display.BrightnessSetting brightnessSetting, java.lang.Runnable runnable, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.whitebalance.DisplayWhiteBalanceController displayWhiteBalanceController;
        com.android.server.display.whitebalance.DisplayWhiteBalanceSettings displayWhiteBalanceSettings;
        this.mFlags = displayManagerFlags;
        this.mInjector = injector != null ? injector : new com.android.server.display.DisplayPowerController.Injector();
        this.mClock = this.mInjector.getClock();
        this.mLogicalDisplay = logicalDisplay;
        this.mDisplayId = this.mLogicalDisplay.getDisplayIdLocked();
        this.mSensorManager = sensorManager;
        this.mHandler = new com.android.server.display.DisplayPowerController.DisplayControllerHandler(handler.getLooper());
        this.mDisplayDeviceConfig = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig();
        this.mIsEnabled = logicalDisplay.isEnabledLocked();
        this.mIsInTransition = logicalDisplay.isInTransitionLocked();
        this.mIsDisplayInternal = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().type == 1;
        this.mWakelockController = this.mInjector.getWakelockController(this.mDisplayId, displayPowerCallbacks);
        this.mDisplayPowerProximityStateController = this.mInjector.getDisplayPowerProximityStateController(this.mWakelockController, this.mDisplayDeviceConfig, this.mHandler.getLooper(), new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$new$0();
            }
        }, this.mDisplayId, this.mSensorManager);
        this.mDisplayStateController = new com.android.server.display.state.DisplayStateController(this.mDisplayPowerProximityStateController);
        this.mTag = "DisplayPowerController2[" + this.mDisplayId + "]";
        this.mThermalBrightnessThrottlingDataId = logicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        this.mLights = (com.android.server.lights.LightsManager) com.android.server.LocalServices.getService(com.android.server.lights.LightsManager.class);
        this.mDisplayDevice = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked();
        this.mUniqueDisplayId = logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        this.mDisplayStatsId = this.mUniqueDisplayId.hashCode();
        this.mLastBrightnessEvent = new com.android.server.display.brightness.BrightnessEvent(this.mDisplayId);
        this.mTempBrightnessEvent = new com.android.server.display.brightness.BrightnessEvent(this.mDisplayId);
        if (this.mDisplayId == 0) {
            this.mBatteryStats = com.android.server.am.BatteryStatsService.getService();
        } else {
            this.mBatteryStats = null;
        }
        this.mSettingsObserver = new com.android.server.display.DisplayPowerController.SettingsObserver(this.mHandler);
        this.mWindowManagerPolicy = (com.android.server.policy.WindowManagerPolicy) com.android.server.LocalServices.getService(com.android.server.policy.WindowManagerPolicy.class);
        this.mBlanker = displayBlanker;
        this.mContext = context;
        this.mBrightnessTracker = brightnessTracker;
        this.mOnBrightnessChangeRunnable = runnable;
        android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        android.content.res.Resources resources = context.getResources();
        this.mScreenBrightnessDozeConfig = com.android.server.display.brightness.BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(4));
        loadBrightnessRampRates();
        this.mSkipScreenOnBrightnessRamp = resources.getBoolean(android.R.bool.config_single_volume);
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$new$1();
            }
        };
        com.android.server.display.HighBrightnessModeController createHbmControllerLocked = createHbmControllerLocked(highBrightnessModeMetadata, runnable2);
        this.mBrightnessThrottler = createBrightnessThrottlerLocked();
        this.mBrightnessRangeController = this.mInjector.getBrightnessRangeController(createHbmControllerLocked, runnable2, this.mDisplayDeviceConfig, this.mHandler, displayManagerFlags, this.mDisplayDevice.getDisplayTokenLocked(), this.mDisplayDevice.getDisplayDeviceInfoLocked());
        this.mDisplayBrightnessController = new com.android.server.display.brightness.DisplayBrightnessController(context, null, this.mDisplayId, this.mLogicalDisplay.getDisplayInfoLocked().brightnessDefault, brightnessSetting, new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$new$2();
            }
        }, new android.os.HandlerExecutor(this.mHandler), displayManagerFlags);
        com.android.server.display.DisplayPowerController.Injector injector2 = this.mInjector;
        com.android.server.display.DisplayPowerController.DisplayControllerHandler displayControllerHandler = this.mHandler;
        java.util.Objects.requireNonNull(runnable2);
        this.mBrightnessClamperController = injector2.getBrightnessClamperController(displayControllerHandler, new com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda5(runnable2), new com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData(this.mUniqueDisplayId, this.mThermalBrightnessThrottlingDataId, logicalDisplay.getPowerThrottlingDataIdLocked(), this.mDisplayDeviceConfig), this.mContext, displayManagerFlags);
        saveBrightnessInfo(getScreenBrightnessSetting());
        this.mAutomaticBrightnessStrategy = this.mDisplayBrightnessController.getAutomaticBrightnessStrategy();
        if (this.mDisplayId == 0) {
            try {
                displayWhiteBalanceController = this.mInjector.getDisplayWhiteBalanceController(this.mHandler, this.mSensorManager, resources);
                try {
                    displayWhiteBalanceSettings = new com.android.server.display.whitebalance.DisplayWhiteBalanceSettings(this.mContext, this.mHandler);
                    try {
                        displayWhiteBalanceSettings.setCallbacks(this);
                        displayWhiteBalanceController.setCallbacks(this);
                    } catch (java.lang.Exception e) {
                        e = e;
                        android.util.Slog.e(this.mTag, "failed to set up display white-balance: " + e);
                        this.mDisplayWhiteBalanceSettings = displayWhiteBalanceSettings;
                        this.mDisplayWhiteBalanceController = displayWhiteBalanceController;
                        loadNitsRange(resources);
                        if (this.mDisplayId != 0) {
                        }
                        setUpAutoBrightness(context, handler);
                        this.mColorFadeEnabled = (this.mInjector.isColorFadeEnabled() || resources.getBoolean(android.R.bool.config_displayBrightnessBucketsInDoze)) ? false : true;
                        this.mColorFadeFadesConfig = resources.getBoolean(android.R.bool.config_animateScreenLights);
                        this.mDisplayBlanksAfterDozeConfig = resources.getBoolean(android.R.bool.config_dismissDreamOnActivityStart);
                        this.mBrightnessBucketsInDozeConfig = resources.getBoolean(android.R.bool.config_displayBlanksAfterDoze);
                        this.mBootCompleted = z;
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    displayWhiteBalanceSettings = null;
                }
            } catch (java.lang.Exception e3) {
                e = e3;
                displayWhiteBalanceController = null;
                displayWhiteBalanceSettings = null;
            }
        } else {
            displayWhiteBalanceController = null;
            displayWhiteBalanceSettings = null;
        }
        this.mDisplayWhiteBalanceSettings = displayWhiteBalanceSettings;
        this.mDisplayWhiteBalanceController = displayWhiteBalanceController;
        loadNitsRange(resources);
        if (this.mDisplayId != 0) {
            this.mCdsi = (com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal) com.android.server.LocalServices.getService(com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal.class);
            if (this.mCdsi != null && this.mCdsi.setReduceBrightColorsListener(new com.android.server.display.color.ColorDisplayService.ReduceBrightColorsListener() { // from class: com.android.server.display.DisplayPowerController.1
                @Override // com.android.server.display.color.ColorDisplayService.ReduceBrightColorsListener
                public void onReduceBrightColorsActivationChanged(boolean z2, boolean z3) {
                    com.android.server.display.DisplayPowerController.this.applyReduceBrightColorsSplineAdjustment();
                }

                @Override // com.android.server.display.color.ColorDisplayService.ReduceBrightColorsListener
                public void onReduceBrightColorsStrengthChanged(int i) {
                    com.android.server.display.DisplayPowerController.this.applyReduceBrightColorsSplineAdjustment();
                }
            })) {
                applyReduceBrightColorsSplineAdjustment();
            }
        } else {
            this.mCdsi = null;
        }
        setUpAutoBrightness(context, handler);
        this.mColorFadeEnabled = (this.mInjector.isColorFadeEnabled() || resources.getBoolean(android.R.bool.config_displayBrightnessBucketsInDoze)) ? false : true;
        this.mColorFadeFadesConfig = resources.getBoolean(android.R.bool.config_animateScreenLights);
        this.mDisplayBlanksAfterDozeConfig = resources.getBoolean(android.R.bool.config_dismissDreamOnActivityStart);
        this.mBrightnessBucketsInDozeConfig = resources.getBoolean(android.R.bool.config_displayBlanksAfterDoze);
        this.mBootCompleted = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        sendUpdatePowerState();
        lambda$new$2();
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyReduceBrightColorsSplineAdjustment() {
        this.mHandler.obtainMessage(9).sendToTarget();
        sendUpdatePowerState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRbcChanged() {
        if (this.mAutomaticBrightnessController == null) {
            return;
        }
        float[] fArr = new float[this.mNitsRange.length];
        for (int i = 0; i < this.mNitsRange.length; i++) {
            fArr[i] = this.mCdsi.getReduceBrightColorsAdjustedBrightnessNits(this.mNitsRange[i]);
        }
        this.mIsRbcActive = this.mCdsi.isReduceBrightColorsActivated();
        this.mAutomaticBrightnessController.recalculateSplines(this.mIsRbcActive, fArr);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public boolean isProximitySensorAvailable() {
        return this.mDisplayPowerProximityStateController.isProximitySensorAvailable();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    @android.annotation.Nullable
    public android.content.pm.ParceledListSlice<android.hardware.display.BrightnessChangeEvent> getBrightnessEvents(int i, boolean z) {
        if (this.mBrightnessTracker == null) {
            return null;
        }
        return this.mBrightnessTracker.getEvents(i, z);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onSwitchUser(int i, int i2, float f) {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(12, i, i2, java.lang.Float.valueOf(f)), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnSwitchUser(int i, int i2, float f) {
        android.util.Slog.i(this.mTag, "Switching user newUserId=" + i + " userSerial=" + i2 + " newBrightness=" + f);
        handleBrightnessModeChange();
        if (this.mBrightnessTracker != null) {
            this.mBrightnessTracker.onSwitchUser(i);
        }
        setBrightness(f, i2);
        this.mDisplayBrightnessController.setAndNotifyCurrentScreenBrightness(f);
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.resetShortTermModel();
        }
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    @android.annotation.Nullable
    public android.content.pm.ParceledListSlice<android.hardware.display.AmbientBrightnessDayStats> getAmbientBrightnessStats(int i) {
        if (this.mBrightnessTracker == null) {
            return null;
        }
        return this.mBrightnessTracker.getAmbientBrightnessStats(i);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void persistBrightnessTrackerState() {
        if (this.mBrightnessTracker != null) {
            this.mBrightnessTracker.persistBrightnessTrackerState();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public boolean requestPowerState(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(this.mTag, "requestPowerState: " + displayPowerRequest + ", waitForNegativeProximity=" + z);
        }
        synchronized (this.mLock) {
            try {
                if (this.mStopped) {
                    return true;
                }
                boolean pendingWaitForNegativeProximityLocked = this.mDisplayPowerProximityStateController.setPendingWaitForNegativeProximityLocked(z);
                if (this.mPendingRequestLocked == null) {
                    this.mPendingRequestLocked = new android.hardware.display.DisplayManagerInternal.DisplayPowerRequest(displayPowerRequest);
                    pendingWaitForNegativeProximityLocked = true;
                } else if (!this.mPendingRequestLocked.equals(displayPowerRequest)) {
                    this.mPendingRequestLocked.copyFrom(displayPowerRequest);
                    pendingWaitForNegativeProximityLocked = true;
                }
                if (pendingWaitForNegativeProximityLocked) {
                    this.mDisplayReadyLocked = false;
                    if (!this.mPendingRequestChangedLocked) {
                        this.mPendingRequestChangedLocked = true;
                        sendUpdatePowerStateLocked();
                    }
                }
                return this.mDisplayReadyLocked;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void overrideDozeScreenState(final int i) {
        this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$overrideDozeScreenState$3(i);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$overrideDozeScreenState$3(int i) {
        if (this.mDisplayOffloadSession != null) {
            if (!android.hardware.display.DisplayManagerInternal.DisplayOffloadSession.isSupportedOffloadState(i) && i != 0) {
                return;
            }
            this.mDisplayStateController.overrideDozeScreenState(i);
            sendUpdatePowerState();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setDisplayOffloadSession(android.hardware.display.DisplayManagerInternal.DisplayOffloadSession displayOffloadSession) {
        if (displayOffloadSession == this.mDisplayOffloadSession) {
            return;
        }
        unblockScreenOnByDisplayOffload();
        this.mDisplayOffloadSession = displayOffloadSession;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() {
        if (this.mAutomaticBrightnessController == null) {
            return null;
        }
        return this.mAutomaticBrightnessController.getDefaultConfig();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onDisplayChanged(final com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, int i) {
        this.mLeadDisplayId = i;
        final com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked();
        if (primaryDisplayDeviceLocked == null) {
            android.util.Slog.wtf(this.mTag, "Display Device is null in DisplayPowerController2 for display: " + this.mLogicalDisplay.getDisplayIdLocked());
            return;
        }
        final java.lang.String uniqueId = primaryDisplayDeviceLocked.getUniqueId();
        final com.android.server.display.DisplayDeviceConfig displayDeviceConfig = primaryDisplayDeviceLocked.getDisplayDeviceConfig();
        final android.os.IBinder displayTokenLocked = primaryDisplayDeviceLocked.getDisplayTokenLocked();
        final com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
        final boolean isEnabledLocked = this.mLogicalDisplay.isEnabledLocked();
        final boolean isInTransitionLocked = this.mLogicalDisplay.isInTransitionLocked();
        final boolean z = this.mLogicalDisplay.getPrimaryDisplayDeviceLocked() != null && this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().type == 1;
        final java.lang.String str = this.mLogicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId;
        final java.lang.String powerThrottlingDataIdLocked = this.mLogicalDisplay.getPowerThrottlingDataIdLocked();
        this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$onDisplayChanged$4(primaryDisplayDeviceLocked, uniqueId, displayDeviceConfig, str, displayTokenLocked, displayDeviceInfoLocked, highBrightnessModeMetadata, isEnabledLocked, isInTransitionLocked, z, powerThrottlingDataIdLocked);
            }
        }, this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayChanged$4(com.android.server.display.DisplayDevice displayDevice, java.lang.String str, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, java.lang.String str2, android.os.IBinder iBinder, com.android.server.display.DisplayDeviceInfo displayDeviceInfo, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, boolean z, boolean z2, boolean z3, java.lang.String str3) {
        boolean z4;
        boolean z5 = true;
        if (this.mDisplayDevice != displayDevice) {
            this.mDisplayDevice = displayDevice;
            this.mUniqueDisplayId = str;
            this.mDisplayStatsId = this.mUniqueDisplayId.hashCode();
            this.mDisplayDeviceConfig = displayDeviceConfig;
            this.mThermalBrightnessThrottlingDataId = str2;
            loadFromDisplayDeviceConfig(iBinder, displayDeviceInfo, highBrightnessModeMetadata);
            this.mDisplayPowerProximityStateController.notifyDisplayDeviceChanged(displayDeviceConfig);
            this.mPowerState.resetScreenState();
            z4 = true;
        } else if (java.util.Objects.equals(this.mThermalBrightnessThrottlingDataId, str2)) {
            z4 = false;
        } else {
            this.mThermalBrightnessThrottlingDataId = str2;
            this.mBrightnessThrottler.loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(displayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId(), displayDeviceConfig.getTempSensor(), this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
            z4 = true;
        }
        if (this.mIsEnabled == z && this.mIsInTransition == z2) {
            z5 = z4;
        } else {
            this.mIsEnabled = z;
            this.mIsInTransition = z2;
        }
        this.mIsDisplayInternal = z3;
        this.mBrightnessClamperController.onDisplayChanged(new com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData(str, str2, str3, displayDeviceConfig));
        if (z5) {
            lambda$new$0();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void stop() {
        synchronized (this.mLock) {
            try {
                clearDisplayBrightnessFollowersLocked();
                this.mStopped = true;
                this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(7), this.mClock.uptimeMillis());
                if (this.mAutomaticBrightnessController != null) {
                    this.mAutomaticBrightnessController.stop();
                }
                this.mDisplayBrightnessController.stop();
                this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void loadFromDisplayDeviceConfig(android.os.IBinder iBinder, com.android.server.display.DisplayDeviceInfo displayDeviceInfo, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata) {
        loadBrightnessRampRates();
        loadNitsRange(this.mContext.getResources());
        setUpAutoBrightness(this.mContext, this.mHandler);
        reloadReduceBrightColours();
        setAnimatorRampSpeeds(false);
        this.mBrightnessRangeController.loadFromConfig(highBrightnessModeMetadata, iBinder, displayDeviceInfo, this.mDisplayDeviceConfig);
        this.mBrightnessThrottler.loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(this.mDisplayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId(), this.mDisplayDeviceConfig.getTempSensor(), this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdatePowerState() {
        synchronized (this.mLock) {
            sendUpdatePowerStateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void sendUpdatePowerStateLocked() {
        if (!this.mStopped && !this.mPendingUpdatePowerStateLocked) {
            this.mPendingUpdatePowerStateLocked = true;
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1), this.mClock.uptimeMillis());
        }
    }

    private void initialize(int i) {
        this.mPowerState = this.mInjector.getDisplayPowerState(this.mBlanker, this.mColorFadeEnabled ? new com.android.server.display.ColorFade(this.mDisplayId) : null, this.mDisplayId, i);
        if (this.mColorFadeEnabled) {
            this.mColorFadeOnAnimator = android.animation.ObjectAnimator.ofFloat(this.mPowerState, com.android.server.display.DisplayPowerState.COLOR_FADE_LEVEL, 0.0f, 1.0f);
            this.mColorFadeOnAnimator.setDuration(250L);
            this.mColorFadeOnAnimator.addListener(this.mAnimatorListener);
            this.mColorFadeOffAnimator = android.animation.ObjectAnimator.ofFloat(this.mPowerState, com.android.server.display.DisplayPowerState.COLOR_FADE_LEVEL, 1.0f, 0.0f);
            this.mColorFadeOffAnimator.setDuration(400L);
            this.mColorFadeOffAnimator.addListener(this.mAnimatorListener);
        }
        this.mScreenBrightnessRampAnimator = this.mInjector.getDualRampAnimator(this.mPowerState, com.android.server.display.DisplayPowerState.SCREEN_BRIGHTNESS_FLOAT, com.android.server.display.DisplayPowerState.SCREEN_SDR_BRIGHTNESS_FLOAT);
        setAnimatorRampSpeeds(this.mAutomaticBrightnessController != null && this.mAutomaticBrightnessController.isInIdleMode());
        this.mScreenBrightnessRampAnimator.setListener(this.mRampAnimatorListener);
        noteScreenState(this.mPowerState.getScreenState());
        noteScreenBrightness(this.mPowerState.getScreenBrightness());
        float convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(this.mPowerState.getScreenBrightness());
        if (this.mBrightnessTracker != null && convertToAdjustedNits >= 0.0f) {
            this.mBrightnessTracker.start(convertToAdjustedNits);
        }
        this.mDisplayBrightnessController.registerBrightnessSettingChangeListener(new com.android.server.display.BrightnessSetting.BrightnessSettingListener() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda1
            @Override // com.android.server.display.BrightnessSetting.BrightnessSettingListener
            public final void onBrightnessChanged(float f) {
                com.android.server.display.DisplayPowerController.this.lambda$initialize$5(f);
            }
        });
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
        if (this.mFlags.areAutoBrightnessModesEnabled()) {
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("screen_brightness_for_als"), false, this.mSettingsObserver, -2);
        }
        this.mContext.getContentResolver().registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("auto_brightness_one_shot"), false, this.mSettingsObserver, -1);
        handleBrightnessModeChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialize$5(float f) {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(8, java.lang.Float.valueOf(f)), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpAutoBrightness(android.content.Context context, android.os.Handler handler) {
        float f;
        float f2;
        int i;
        com.android.server.display.BrightnessMappingStrategy create;
        this.mUseSoftwareAutoBrightnessConfig = this.mDisplayDeviceConfig.isAutoBrightnessAvailable();
        if (!this.mUseSoftwareAutoBrightnessConfig) {
            return;
        }
        android.util.SparseArray<com.android.server.display.BrightnessMappingStrategy> sparseArray = new android.util.SparseArray<>();
        com.android.server.display.BrightnessMappingStrategy defaultModeBrightnessMapper = this.mInjector.getDefaultModeBrightnessMapper(context, this.mDisplayDeviceConfig, this.mDisplayWhiteBalanceController);
        sparseArray.append(0, defaultModeBrightnessMapper);
        if (context.getResources().getBoolean(android.R.bool.config_enableGeolocationTimeZoneDetection) && (create = com.android.server.display.BrightnessMappingStrategy.create(context, this.mDisplayDeviceConfig, 1, this.mDisplayWhiteBalanceController)) != null) {
            sparseArray.append(1, create);
        }
        com.android.server.display.BrightnessMappingStrategy create2 = com.android.server.display.BrightnessMappingStrategy.create(context, this.mDisplayDeviceConfig, 2, this.mDisplayWhiteBalanceController);
        if (this.mFlags.areAutoBrightnessModesEnabled() && create2 != null) {
            sparseArray.put(2, create2);
        }
        if (this.mAutomaticBrightnessController == null) {
            f = -1.0f;
            f2 = -1.0f;
        } else {
            f = this.mAutomaticBrightnessController.getUserLux();
            f2 = this.mAutomaticBrightnessController.getUserNits();
        }
        if (defaultModeBrightnessMapper != null) {
            float fraction = context.getResources().getFraction(android.R.fraction.config_screenAutoBrightnessDozeScaleFactor, 1, 1);
            com.android.server.display.HysteresisLevels hysteresisLevels = this.mInjector.getHysteresisLevels(this.mDisplayDeviceConfig.getAmbientBrighteningPercentages(), this.mDisplayDeviceConfig.getAmbientDarkeningPercentages(), this.mDisplayDeviceConfig.getAmbientBrighteningLevels(), this.mDisplayDeviceConfig.getAmbientDarkeningLevels(), this.mDisplayDeviceConfig.getAmbientLuxDarkeningMinThreshold(), this.mDisplayDeviceConfig.getAmbientLuxBrighteningMinThreshold());
            com.android.server.display.HysteresisLevels hysteresisLevels2 = this.mInjector.getHysteresisLevels(this.mDisplayDeviceConfig.getScreenBrighteningPercentages(), this.mDisplayDeviceConfig.getScreenDarkeningPercentages(), this.mDisplayDeviceConfig.getScreenBrighteningLevels(), this.mDisplayDeviceConfig.getScreenDarkeningLevels(), this.mDisplayDeviceConfig.getScreenDarkeningMinThreshold(), this.mDisplayDeviceConfig.getScreenBrighteningMinThreshold(), true);
            com.android.server.display.HysteresisLevels hysteresisLevels3 = this.mInjector.getHysteresisLevels(this.mDisplayDeviceConfig.getAmbientBrighteningPercentagesIdle(), this.mDisplayDeviceConfig.getAmbientDarkeningPercentagesIdle(), this.mDisplayDeviceConfig.getAmbientBrighteningLevelsIdle(), this.mDisplayDeviceConfig.getAmbientDarkeningLevelsIdle(), this.mDisplayDeviceConfig.getAmbientLuxDarkeningMinThresholdIdle(), this.mDisplayDeviceConfig.getAmbientLuxBrighteningMinThresholdIdle());
            com.android.server.display.HysteresisLevels hysteresisLevels4 = this.mInjector.getHysteresisLevels(this.mDisplayDeviceConfig.getScreenBrighteningPercentagesIdle(), this.mDisplayDeviceConfig.getScreenDarkeningPercentagesIdle(), this.mDisplayDeviceConfig.getScreenBrighteningLevelsIdle(), this.mDisplayDeviceConfig.getScreenDarkeningLevelsIdle(), this.mDisplayDeviceConfig.getScreenDarkeningMinThresholdIdle(), this.mDisplayDeviceConfig.getScreenBrighteningMinThresholdIdle());
            long autoBrightnessBrighteningLightDebounce = this.mDisplayDeviceConfig.getAutoBrightnessBrighteningLightDebounce();
            long autoBrightnessDarkeningLightDebounce = this.mDisplayDeviceConfig.getAutoBrightnessDarkeningLightDebounce();
            long autoBrightnessBrighteningLightDebounceIdle = this.mDisplayDeviceConfig.getAutoBrightnessBrighteningLightDebounceIdle();
            long autoBrightnessDarkeningLightDebounceIdle = this.mDisplayDeviceConfig.getAutoBrightnessDarkeningLightDebounceIdle();
            boolean z = context.getResources().getBoolean(android.R.bool.config_autoBrightnessResetAmbientLuxAfterWarmUp);
            int integer = context.getResources().getInteger(android.R.integer.config_letterboxDefaultPositionForVerticalReachability);
            int integer2 = context.getResources().getInteger(android.R.integer.config_autoBrightnessLightSensorRate);
            int integer3 = context.getResources().getInteger(android.R.integer.config_autoBrightnessInitialLightSensorRate);
            if (integer3 == -1) {
                i = integer2;
            } else {
                if (integer3 > integer2) {
                    android.util.Slog.w(this.mTag, "Expected config_autoBrightnessInitialLightSensorRate (" + integer3 + ") to be less than or equal to config_autoBrightnessLightSensorRate (" + integer2 + ").");
                }
                i = integer3;
            }
            loadAmbientLightSensor();
            if (this.mBrightnessTracker != null && this.mDisplayId == 0) {
                this.mBrightnessTracker.setLightSensor(this.mLightSensor);
            }
            if (this.mAutomaticBrightnessController != null) {
                this.mAutomaticBrightnessController.stop();
            }
            this.mAutomaticBrightnessController = this.mInjector.getAutomaticBrightnessController(this, handler.getLooper(), this.mSensorManager, this.mLightSensor, sparseArray, integer, 0.0f, 1.0f, fraction, integer2, i, autoBrightnessBrighteningLightDebounce, autoBrightnessDarkeningLightDebounce, autoBrightnessBrighteningLightDebounceIdle, autoBrightnessDarkeningLightDebounceIdle, z, hysteresisLevels, hysteresisLevels2, hysteresisLevels3, hysteresisLevels4, this.mContext, this.mBrightnessRangeController, this.mBrightnessThrottler, this.mDisplayDeviceConfig.getAmbientHorizonShort(), this.mDisplayDeviceConfig.getAmbientHorizonLong(), f, f2);
            this.mDisplayBrightnessController.setAutomaticBrightnessController(this.mAutomaticBrightnessController);
            this.mAutomaticBrightnessStrategy.setAutomaticBrightnessController(this.mAutomaticBrightnessController);
            this.mBrightnessEventRingBuffer = new com.android.internal.util.RingBuffer<>(com.android.server.display.brightness.BrightnessEvent.class, 100);
            if (this.mScreenOffBrightnessSensorController != null) {
                this.mScreenOffBrightnessSensorController.stop();
                this.mScreenOffBrightnessSensorController = null;
            }
            loadScreenOffBrightnessSensor();
            int[] screenOffBrightnessSensorValueToLux = this.mDisplayDeviceConfig.getScreenOffBrightnessSensorValueToLux();
            if (this.mScreenOffBrightnessSensor != null && screenOffBrightnessSensorValueToLux != null) {
                this.mScreenOffBrightnessSensorController = this.mInjector.getScreenOffBrightnessSensorController(this.mSensorManager, this.mScreenOffBrightnessSensor, this.mHandler, new com.android.server.display.ScreenOffBrightnessSensorController.Clock() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda2
                    @Override // com.android.server.display.ScreenOffBrightnessSensorController.Clock
                    public final long uptimeMillis() {
                        return android.os.SystemClock.uptimeMillis();
                    }
                }, screenOffBrightnessSensorValueToLux, defaultModeBrightnessMapper);
                return;
            }
            return;
        }
        this.mUseSoftwareAutoBrightnessConfig = false;
    }

    private void loadBrightnessRampRates() {
        this.mBrightnessRampRateFastDecrease = this.mDisplayDeviceConfig.getBrightnessRampFastDecrease();
        this.mBrightnessRampRateFastIncrease = this.mDisplayDeviceConfig.getBrightnessRampFastIncrease();
        this.mBrightnessRampRateSlowDecrease = this.mDisplayDeviceConfig.getBrightnessRampSlowDecrease();
        this.mBrightnessRampRateSlowIncrease = this.mDisplayDeviceConfig.getBrightnessRampSlowIncrease();
        this.mBrightnessRampRateSlowDecreaseIdle = this.mDisplayDeviceConfig.getBrightnessRampSlowDecreaseIdle();
        this.mBrightnessRampRateSlowIncreaseIdle = this.mDisplayDeviceConfig.getBrightnessRampSlowIncreaseIdle();
        this.mBrightnessRampDecreaseMaxTimeMillis = this.mDisplayDeviceConfig.getBrightnessRampDecreaseMaxMillis();
        this.mBrightnessRampIncreaseMaxTimeMillis = this.mDisplayDeviceConfig.getBrightnessRampIncreaseMaxMillis();
        this.mBrightnessRampDecreaseMaxTimeIdleMillis = this.mDisplayDeviceConfig.getBrightnessRampDecreaseMaxIdleMillis();
        this.mBrightnessRampIncreaseMaxTimeIdleMillis = this.mDisplayDeviceConfig.getBrightnessRampIncreaseMaxIdleMillis();
    }

    private void loadNitsRange(android.content.res.Resources resources) {
        if (this.mDisplayDeviceConfig != null && this.mDisplayDeviceConfig.getNits() != null) {
            this.mNitsRange = this.mDisplayDeviceConfig.getNits();
        } else {
            android.util.Slog.w(this.mTag, "Screen brightness nits configuration is unavailable; falling back");
            this.mNitsRange = com.android.server.display.BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(android.R.array.config_satellite_providers));
        }
    }

    private void reloadReduceBrightColours() {
        if (this.mCdsi != null && this.mCdsi.isReduceBrightColorsActivated()) {
            applyReduceBrightColorsSplineAdjustment();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v3 */
    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAutomaticScreenBrightnessMode(int i) {
        ?? r0 = i != 1 ? 0 : 1;
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.switchMode(i);
            setAnimatorRampSpeeds(r0);
        }
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 14;
        obtainMessage.arg1 = r0;
        this.mHandler.sendMessageAtTime(obtainMessage, this.mClock.uptimeMillis());
    }

    private void setAnimatorRampSpeeds(boolean z) {
        if (this.mScreenBrightnessRampAnimator == null) {
            return;
        }
        if (this.mFlags.isAdaptiveTone1Enabled() && z) {
            this.mScreenBrightnessRampAnimator.setAnimationTimeLimits(this.mBrightnessRampIncreaseMaxTimeIdleMillis, this.mBrightnessRampDecreaseMaxTimeIdleMillis);
        } else {
            this.mScreenBrightnessRampAnimator.setAnimationTimeLimits(this.mBrightnessRampIncreaseMaxTimeMillis, this.mBrightnessRampDecreaseMaxTimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupHandlerThreadAfterStop() {
        float f;
        this.mDisplayPowerProximityStateController.cleanup();
        this.mBrightnessRangeController.stop();
        this.mBrightnessThrottler.stop();
        this.mBrightnessClamperController.stop();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mWakelockController.releaseAll();
        if (this.mPowerState != null) {
            f = this.mPowerState.getScreenBrightness();
        } else {
            f = 0.0f;
        }
        reportStats(f);
        if (this.mPowerState != null) {
            this.mPowerState.stop();
            this.mPowerState = null;
        }
        if (this.mScreenOffBrightnessSensorController != null) {
            this.mScreenOffBrightnessSensorController.stop();
        }
        if (this.mDisplayWhiteBalanceController != null) {
            this.mDisplayWhiteBalanceController.setEnabled(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updatePowerState, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        android.os.Trace.traceBegin(131072L, "DisplayPowerController#updatePowerState");
        updatePowerStateInternal();
        android.os.Trace.traceEnd(131072L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updatePowerStateInternal() {
        int i;
        byte b;
        int i2;
        int i3;
        boolean z;
        float f;
        boolean z2;
        float f2;
        int i4;
        byte b2;
        int i5;
        boolean saveBrightnessInfo;
        boolean z3;
        boolean z4;
        float f3;
        float f4;
        float f5;
        int i6;
        int i7;
        this.mBrightnessReasonTemp.set(null);
        this.mTempBrightnessEvent.reset();
        synchronized (this.mLock) {
            try {
                if (this.mStopped) {
                    return;
                }
                this.mPendingUpdatePowerStateLocked = false;
                if (this.mPendingRequestLocked == null) {
                    return;
                }
                if (this.mPowerRequest == null) {
                    this.mPowerRequest = new android.hardware.display.DisplayManagerInternal.DisplayPowerRequest(this.mPendingRequestLocked);
                    this.mDisplayPowerProximityStateController.updatePendingProximityRequestsLocked();
                    this.mPendingRequestChangedLocked = false;
                    i = 3;
                    b = true;
                } else if (this.mPendingRequestChangedLocked) {
                    int i8 = this.mPowerRequest.policy;
                    this.mPowerRequest.copyFrom(this.mPendingRequestLocked);
                    this.mDisplayPowerProximityStateController.updatePendingProximityRequestsLocked();
                    this.mPendingRequestChangedLocked = false;
                    this.mDisplayReadyLocked = false;
                    i = i8;
                    b = false;
                } else {
                    i = this.mPowerRequest.policy;
                    b = false;
                }
                byte b3 = !this.mDisplayReadyLocked;
                android.util.SparseArray<com.android.server.display.DisplayPowerControllerInterface> clone = this.mDisplayBrightnessFollowers.clone();
                int updateDisplayState = this.mDisplayStateController.updateDisplayState(this.mPowerRequest, this.mIsEnabled, this.mIsInTransition);
                if (b != false) {
                    initialize(readyToUpdateDisplayState() ? updateDisplayState : 0);
                }
                animateScreenStateChange(updateDisplayState, this.mDisplayStateController.shouldPerformScreenOffTransition());
                int screenState = this.mPowerState.getScreenState();
                if (this.mFlags.areAutoBrightnessModesEnabled() && this.mAutomaticBrightnessController != null && !this.mAutomaticBrightnessController.isInIdleMode()) {
                    com.android.server.display.AutomaticBrightnessController automaticBrightnessController = this.mAutomaticBrightnessController;
                    if (this.mPowerRequest.policy != 1) {
                        i7 = 0;
                    } else {
                        i7 = 2;
                    }
                    automaticBrightnessController.switchMode(i7);
                }
                boolean updateUserSetScreenBrightness = this.mDisplayBrightnessController.updateUserSetScreenBrightness();
                if (screenState == 1 || screenState == 3 || screenState == 4) {
                    com.android.server.lights.LogicalLight light = this.mLights.getLight(2);
                    if (light != null) {
                        light.setBrightness(-1.0f);
                    }
                    com.android.server.lights.LogicalLight light2 = this.mLights.getLight(1);
                    if (light2 != null) {
                        light2.setBrightness(-1.0f);
                    }
                }
                com.android.server.display.DisplayBrightnessState updateBrightness = this.mDisplayBrightnessController.updateBrightness(this.mPowerRequest, screenState);
                float brightness = updateBrightness.getBrightness();
                float brightness2 = updateBrightness.getBrightness();
                this.mBrightnessReasonTemp.set(updateBrightness.getBrightnessReason());
                boolean isSlowChange = updateBrightness.isSlowChange();
                float customAnimationRate = updateBrightness.getCustomAnimationRate();
                if (this.mScreenOffBrightnessSensorController != null) {
                    this.mScreenOffBrightnessSensorController.setLightSensorEnabled(updateBrightness.getShouldUseAutoBrightness() && this.mIsEnabled && (screenState == 1 || (screenState == 3 && !this.mDisplayBrightnessController.isAllowAutoBrightnessWhileDozingConfig())) && this.mLeadDisplayId == -1);
                }
                boolean isShortTermModelActive = this.mAutomaticBrightnessStrategy.isShortTermModelActive();
                this.mAutomaticBrightnessStrategy.setAutoBrightnessState(screenState, this.mDisplayBrightnessController.isAllowAutoBrightnessWhileDozingConfig(), this.mBrightnessReasonTemp.getReason(), this.mPowerRequest.policy, this.mDisplayBrightnessController.getLastUserSetScreenBrightness(), updateUserSetScreenBrightness);
                boolean z5 = java.lang.Float.isNaN(brightness) && (this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentChanged() || updateUserSetScreenBrightness);
                com.android.server.display.BrightnessRangeController brightnessRangeController = this.mBrightnessRangeController;
                if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                    i2 = 1;
                } else if (this.mAutomaticBrightnessStrategy.isAutoBrightnessDisabledDueToDisplayOff()) {
                    i2 = 3;
                } else {
                    i2 = 2;
                }
                brightnessRangeController.setAutoBrightnessEnabled(i2);
                boolean shouldUpdateScreenBrightnessSetting = updateBrightness.shouldUpdateScreenBrightnessSetting();
                float currentBrightness = this.mDisplayBrightnessController.getCurrentBrightness();
                if (java.lang.Float.isNaN(brightness)) {
                    if (this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled()) {
                        brightness = this.mAutomaticBrightnessStrategy.getAutomaticScreenBrightness(this.mTempBrightnessEvent);
                        if (!com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(brightness) && brightness != -1.0f) {
                            this.mAutomaticBrightnessStrategy.setAutoBrightnessApplied(false);
                            i3 = 0;
                            z = isSlowChange;
                        } else {
                            brightness2 = this.mAutomaticBrightnessController.getRawAutomaticScreenBrightness();
                            brightness = clampScreenBrightness(brightness);
                            boolean z6 = this.mAutomaticBrightnessStrategy.hasAppliedAutoBrightness() && !this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentChanged();
                            int autoBrightnessAdjustmentReasonsFlags = this.mAutomaticBrightnessStrategy.getAutoBrightnessAdjustmentReasonsFlags();
                            boolean z7 = currentBrightness != brightness;
                            this.mAutomaticBrightnessStrategy.setAutoBrightnessApplied(true);
                            this.mBrightnessReasonTemp.setReason(4);
                            if (this.mScreenOffBrightnessSensorController != null) {
                                this.mScreenOffBrightnessSensorController.setLightSensorEnabled(false);
                            }
                            z = z6;
                            boolean z8 = z7;
                            i3 = autoBrightnessAdjustmentReasonsFlags;
                            shouldUpdateScreenBrightnessSetting = z8;
                        }
                    } else {
                        i3 = 0;
                        z = isSlowChange;
                    }
                } else {
                    brightness = clampScreenBrightness(brightness);
                    this.mAutomaticBrightnessStrategy.setAutoBrightnessApplied(false);
                    i3 = 0;
                    z = isSlowChange;
                }
                if (java.lang.Float.isNaN(brightness) && this.mFlags.areAutoBrightnessModesEnabled() && this.mFlags.isDisplayOffloadEnabled() && this.mPowerRequest.policy == 1 && this.mDisplayOffloadSession != null && this.mAutomaticBrightnessController != null && this.mAutomaticBrightnessStrategy.shouldUseAutoBrightness()) {
                    float automaticScreenBrightnessBasedOnLastObservedLux = this.mAutomaticBrightnessController.getAutomaticScreenBrightnessBasedOnLastObservedLux(this.mTempBrightnessEvent);
                    if (!com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(automaticScreenBrightnessBasedOnLastObservedLux)) {
                        brightness2 = automaticScreenBrightnessBasedOnLastObservedLux;
                    } else {
                        brightness = clampScreenBrightness(automaticScreenBrightnessBasedOnLastObservedLux);
                        this.mBrightnessReasonTemp.setReason(12);
                        brightness2 = automaticScreenBrightnessBasedOnLastObservedLux;
                    }
                }
                if (java.lang.Float.isNaN(brightness) && this.mPowerRequest.policy == 1) {
                    float f6 = this.mScreenBrightnessDozeConfig;
                    brightness = clampScreenBrightness(f6);
                    this.mBrightnessReasonTemp.setReason(3);
                    brightness2 = f6;
                }
                if (java.lang.Float.isNaN(brightness) && this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled() && this.mScreenOffBrightnessSensorController != null) {
                    brightness = this.mScreenOffBrightnessSensorController.getAutomaticScreenBrightness();
                    if (!com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(brightness)) {
                        brightness2 = brightness;
                    } else {
                        float clampScreenBrightness = clampScreenBrightness(brightness);
                        shouldUpdateScreenBrightnessSetting = this.mDisplayBrightnessController.getCurrentBrightness() != clampScreenBrightness;
                        this.mBrightnessReasonTemp.setReason(9);
                        brightness2 = brightness;
                        brightness = clampScreenBrightness;
                    }
                }
                if (!java.lang.Float.isNaN(brightness)) {
                    f = brightness;
                    currentBrightness = brightness2;
                } else {
                    float clampScreenBrightness2 = clampScreenBrightness(currentBrightness);
                    if (clampScreenBrightness2 != currentBrightness) {
                        shouldUpdateScreenBrightnessSetting = true;
                    }
                    this.mBrightnessReasonTemp.setReason(1);
                    f = clampScreenBrightness2;
                }
                float ambientLux = this.mAutomaticBrightnessController == null ? 0.0f : this.mAutomaticBrightnessController.getAmbientLux();
                for (int i9 = 0; i9 < clone.size(); i9++) {
                    clone.valueAt(i9).setBrightnessToFollow(currentBrightness, this.mDisplayBrightnessController.convertToNits(currentBrightness), ambientLux, z);
                }
                com.android.server.display.DisplayBrightnessState clamp = this.mBrightnessClamperController.clamp(this.mPowerRequest, f, z);
                float brightness3 = clamp.getBrightness();
                boolean isSlowChange2 = clamp.isSlowChange();
                float max = java.lang.Math.max(customAnimationRate, clamp.getCustomAnimationRate());
                this.mBrightnessReasonTemp.addModifier(clamp.getBrightnessReason().getModifier());
                if (shouldUpdateScreenBrightnessSetting) {
                    this.mDisplayBrightnessController.updateScreenBrightnessSetting(android.util.MathUtils.constrain(f, clamp.getMinBrightness(), clamp.getMaxBrightness()));
                }
                this.mBrightnessRangeController.onBrightnessChanged(brightness3, f, this.mBrightnessClamperController.getBrightnessMaxReason());
                boolean z9 = this.mBrightnessReasonTemp.getReason() == 7 || this.mAutomaticBrightnessStrategy.isTemporaryAutoBrightnessAdjustmentApplied();
                if (!this.mPendingScreenOff) {
                    if (this.mSkipScreenOnBrightnessRamp) {
                        if (screenState != 2) {
                            this.mSkipRampState = 0;
                        } else if (this.mSkipRampState != 0 || !this.mDozing) {
                            if (this.mSkipRampState != 1 || !this.mUseSoftwareAutoBrightnessConfig) {
                                i6 = 2;
                            } else if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(brightness3, this.mInitialAutoBrightness)) {
                                this.mSkipRampState = 2;
                            } else {
                                i6 = 2;
                            }
                            if (this.mSkipRampState == i6) {
                                this.mSkipRampState = 0;
                            }
                        } else {
                            this.mInitialAutoBrightness = brightness3;
                            this.mSkipRampState = 1;
                        }
                    }
                    boolean z10 = (screenState == 2 && this.mSkipRampState != 0) || this.mDisplayPowerProximityStateController.shouldSkipRampBecauseOfProximityChangeToNegative();
                    boolean z11 = android.view.Display.isDozeState(screenState) && this.mBrightnessBucketsInDozeConfig;
                    boolean z12 = this.mColorFadeEnabled && this.mPowerState.getColorFadeLevel() == 1.0f;
                    boolean z13 = isSlowChange2;
                    float clampScreenBrightness3 = clampScreenBrightness(brightness3);
                    if (this.mBrightnessRangeController.getHighBrightnessMode() == 2 && (this.mBrightnessReasonTemp.getModifier() & 1) == 0 && (this.mBrightnessReasonTemp.getModifier() & 2) == 0) {
                        float hdrBrightnessValue = this.mBrightnessRangeController.getHdrBrightnessValue();
                        max = java.lang.Math.max(max, this.mBrightnessRangeController.getHdrTransitionRate());
                        this.mBrightnessReasonTemp.addModifier(4);
                        f3 = hdrBrightnessValue;
                    } else {
                        f3 = clampScreenBrightness3;
                    }
                    float f7 = max;
                    if (this.mPowerRequest.policy == 1 && (this.mPowerRequest.dozeScreenState == 0 || this.mPowerRequest.dozeScreenState == 4 || this.mPowerRequest.dozeScreenState == 6)) {
                        f4 = -1.0f;
                        z13 = false;
                    } else {
                        f4 = f7;
                    }
                    float screenBrightness = this.mPowerState.getScreenBrightness();
                    i4 = i;
                    float sdrScreenBrightness = this.mPowerState.getSdrScreenBrightness();
                    if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(f3) && (f3 != screenBrightness || clampScreenBrightness3 != sdrScreenBrightness)) {
                        boolean z14 = z10 || z11 || !z12 || z9;
                        boolean floatEquals = com.android.internal.display.BrightnessSynchronizer.floatEquals(clampScreenBrightness3, sdrScreenBrightness);
                        if (this.mFlags.isFastHdrTransitionsEnabled() && !z14 && floatEquals) {
                            z13 = false;
                        }
                        if (z14) {
                            animateScreenBrightness(f3, clampScreenBrightness3, 0.0f);
                        } else if (f4 > 0.0f) {
                            animateScreenBrightness(f3, clampScreenBrightness3, f4, true);
                        } else {
                            boolean z15 = f3 > screenBrightness;
                            boolean z16 = this.mAutomaticBrightnessController != null && this.mAutomaticBrightnessController.isInIdleMode();
                            if (z15 && z13) {
                                if (z16) {
                                    f5 = this.mBrightnessRampRateSlowIncreaseIdle;
                                } else {
                                    f5 = this.mBrightnessRampRateSlowIncrease;
                                }
                            } else if (z15 && !z13) {
                                f5 = this.mBrightnessRampRateFastIncrease;
                            } else if (!z15 && z13) {
                                f5 = z16 ? this.mBrightnessRampRateSlowDecreaseIdle : this.mBrightnessRampRateSlowDecrease;
                            } else {
                                f5 = this.mBrightnessRampRateFastDecrease;
                            }
                            animateScreenBrightness(f3, clampScreenBrightness3, f5);
                        }
                    }
                    z2 = isShortTermModelActive;
                    b2 = b3;
                    f2 = f;
                    i5 = screenState;
                    notifyBrightnessTrackerChanged(brightness3, z5, z2, this.mAutomaticBrightnessStrategy.isAutoBrightnessEnabled(), z9, updateBrightness.getShouldUseAutoBrightness());
                    saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), f3, clamp);
                } else {
                    z2 = isShortTermModelActive;
                    f2 = f;
                    i4 = i;
                    b2 = b3;
                    i5 = screenState;
                    saveBrightnessInfo = saveBrightnessInfo(getScreenBrightnessSetting(), clamp);
                }
                if (saveBrightnessInfo && !z9) {
                    lambda$new$2();
                }
                if (!this.mBrightnessReasonTemp.equals(this.mBrightnessReason) || i3 != 0) {
                    android.util.Slog.v(this.mTag, "Brightness [" + brightness3 + "] reason changing to: '" + this.mBrightnessReasonTemp.toString(i3) + "', previous reason: '" + this.mBrightnessReason + "'.");
                    this.mBrightnessReason.set(this.mBrightnessReasonTemp);
                } else if (this.mBrightnessReasonTemp.getReason() == 1 && updateUserSetScreenBrightness) {
                    android.util.Slog.v(this.mTag, "Brightness [" + brightness3 + "] manual adjustment.");
                }
                this.mTempBrightnessEvent.setTime(java.lang.System.currentTimeMillis());
                this.mTempBrightnessEvent.setBrightness(brightness3);
                this.mTempBrightnessEvent.setPhysicalDisplayId(this.mUniqueDisplayId);
                this.mTempBrightnessEvent.setDisplayState(i5);
                this.mTempBrightnessEvent.setDisplayPolicy(this.mPowerRequest.policy);
                this.mTempBrightnessEvent.setReason(this.mBrightnessReason);
                this.mTempBrightnessEvent.setHbmMax(this.mBrightnessRangeController.getCurrentBrightnessMax());
                this.mTempBrightnessEvent.setHbmMode(this.mBrightnessRangeController.getHighBrightnessMode());
                this.mTempBrightnessEvent.setFlags(this.mTempBrightnessEvent.getFlags() | (this.mIsRbcActive ? 1 : 0) | (this.mPowerRequest.lowPowerMode ? 32 : 0));
                this.mTempBrightnessEvent.setRbcStrength(this.mCdsi != null ? this.mCdsi.getReduceBrightColorsStrength() : -1);
                this.mTempBrightnessEvent.setPowerFactor(this.mPowerRequest.screenLowPowerBrightnessFactor);
                this.mTempBrightnessEvent.setWasShortTermModelActive(z2);
                this.mTempBrightnessEvent.setDisplayBrightnessStrategyName(updateBrightness.getDisplayBrightnessStrategyName());
                this.mTempBrightnessEvent.setAutomaticBrightnessEnabled(updateBrightness.getShouldUseAutoBrightness());
                boolean z17 = this.mTempBrightnessEvent.getReason().getReason() == 7 && this.mLastBrightnessEvent.getReason().getReason() == 7;
                boolean z18 = this.mLastBrightnessEvent.isRbcEnabled() != this.mTempBrightnessEvent.isRbcEnabled();
                if ((!this.mTempBrightnessEvent.equalsMainData(this.mLastBrightnessEvent) && !z17) || i3 != 0) {
                    this.mTempBrightnessEvent.setInitialBrightness(this.mLastBrightnessEvent.getBrightness());
                    this.mLastBrightnessEvent.copyFrom(this.mTempBrightnessEvent);
                    com.android.server.display.brightness.BrightnessEvent brightnessEvent = new com.android.server.display.brightness.BrightnessEvent(this.mTempBrightnessEvent);
                    brightnessEvent.setAdjustmentFlags(i3);
                    brightnessEvent.setFlags(brightnessEvent.getFlags() | (updateUserSetScreenBrightness ? 8 : 0));
                    android.util.Slog.i(this.mTag, brightnessEvent.toString(false));
                    if (updateUserSetScreenBrightness || brightnessEvent.getReason().getReason() != 7) {
                        logBrightnessEvent(brightnessEvent, f2);
                    }
                    if (this.mBrightnessEventRingBuffer != null) {
                        this.mBrightnessEventRingBuffer.append(brightnessEvent);
                    }
                    if (z18) {
                        this.mRbcEventRingBuffer.append(brightnessEvent);
                    }
                }
                if (this.mDisplayWhiteBalanceController == null) {
                    z3 = false;
                } else if (i5 == 2 && this.mDisplayWhiteBalanceSettings.isEnabled()) {
                    this.mDisplayWhiteBalanceController.setEnabled(true);
                    this.mDisplayWhiteBalanceController.updateDisplayColorTemperature();
                    z3 = false;
                } else {
                    z3 = false;
                    this.mDisplayWhiteBalanceController.setEnabled(false);
                }
                boolean z19 = (this.mPendingScreenOnUnblocker == null && this.mPendingScreenOnUnblockerByDisplayOffload == null && (!this.mColorFadeEnabled || (!this.mColorFadeOnAnimator.isStarted() && !this.mColorFadeOffAnimator.isStarted())) && this.mPowerState.waitUntilClean(this.mCleanListener)) ? true : z3;
                boolean z20 = (!z19 || this.mScreenBrightnessRampAnimator.isAnimating()) ? z3 : true;
                if (z19 && i5 != 1 && this.mReportedScreenStateToPolicy == 1) {
                    setReportedScreenState(2);
                    this.mWindowManagerPolicy.screenTurnedOn(this.mDisplayId);
                }
                if (!z20) {
                    this.mWakelockController.acquireWakelock(5);
                }
                if (!z19 || b2 == false) {
                    z4 = true;
                } else {
                    synchronized (this.mLock) {
                        try {
                            if (this.mPendingRequestChangedLocked) {
                                z4 = true;
                            } else {
                                z4 = true;
                                this.mDisplayReadyLocked = true;
                                if (DEBUG) {
                                    android.util.Slog.d(this.mTag, "Display ready!");
                                }
                            }
                        } finally {
                        }
                    }
                    sendOnStateChangedWithWakelock();
                }
                if (z20) {
                    this.mWakelockController.releaseWakelock(5);
                }
                this.mDozing = i5 != 2 ? z4 : z3;
                if (i4 != this.mPowerRequest.policy) {
                    logDisplayPolicyChanged(this.mPowerRequest.policy);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDwbcOverride(float f) {
        if (this.mDisplayWhiteBalanceController != null) {
            this.mDisplayWhiteBalanceController.setAmbientColorTemperatureOverride(f);
            lambda$new$0();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDwbcStrongMode(int i) {
        if (this.mDisplayWhiteBalanceController != null) {
            this.mDisplayWhiteBalanceController.setStrongModeEnabled(i == 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDwbcLoggingEnabled(int i) {
        if (this.mDisplayWhiteBalanceController != null) {
            boolean z = i == 1;
            this.mDisplayWhiteBalanceController.setLoggingEnabled(z);
            this.mDisplayWhiteBalanceSettings.setLoggingEnabled(z);
        }
    }

    @Override // com.android.server.display.AutomaticBrightnessController.Callbacks
    public void updateBrightness() {
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void ignoreProximitySensorUntilChanged() {
        this.mDisplayPowerProximityStateController.ignoreProximitySensorUntilChanged();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration, boolean z) {
        this.mHandler.obtainMessage(4, z ? 1 : 0, 0, brightnessConfiguration).sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryBrightness(float f) {
        this.mHandler.obtainMessage(5, java.lang.Float.floatToIntBits(f), 0).sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mHandler.obtainMessage(6, java.lang.Float.floatToIntBits(f), 0).sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessFromOffload(float f) {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(17, java.lang.Float.floatToIntBits(f), 0), this.mClock.uptimeMillis());
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float[] getAutoBrightnessLevels(int i) {
        return this.mDisplayDeviceConfig.getAutoBrightnessBrighteningLevels(i, android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_for_als", 2, -2));
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float[] getAutoBrightnessLuxLevels(int i) {
        return this.mDisplayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(i, android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_for_als", 2, -2));
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public android.hardware.display.BrightnessInfo getBrightnessInfo() {
        android.hardware.display.BrightnessInfo brightnessInfo;
        synchronized (this.mCachedBrightnessInfo) {
            brightnessInfo = new android.hardware.display.BrightnessInfo(this.mCachedBrightnessInfo.brightness.value, this.mCachedBrightnessInfo.adjustedBrightness.value, this.mCachedBrightnessInfo.brightnessMin.value, this.mCachedBrightnessInfo.brightnessMax.value, this.mCachedBrightnessInfo.hbmMode.value, this.mCachedBrightnessInfo.hbmTransitionPoint.value, this.mCachedBrightnessInfo.brightnessMaxReason.value);
        }
        return brightnessInfo;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void onBootCompleted() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(13), this.mClock.uptimeMillis());
    }

    private boolean saveBrightnessInfo(float f) {
        return saveBrightnessInfo(f, null);
    }

    private boolean saveBrightnessInfo(float f, @android.annotation.Nullable com.android.server.display.DisplayBrightnessState displayBrightnessState) {
        return saveBrightnessInfo(f, f, displayBrightnessState);
    }

    private boolean saveBrightnessInfo(float f, float f2, @android.annotation.Nullable com.android.server.display.DisplayBrightnessState displayBrightnessState) {
        float maxBrightness;
        boolean checkAndSetFloat;
        synchronized (this.mCachedBrightnessInfo) {
            if (displayBrightnessState == null) {
                maxBrightness = 1.0f;
            } else {
                try {
                    maxBrightness = displayBrightnessState.getMaxBrightness();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            checkAndSetFloat = this.mCachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.brightness, f) | false | this.mCachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.adjustedBrightness, f2) | this.mCachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.brightnessMin, java.lang.Math.max(displayBrightnessState != null ? displayBrightnessState.getMinBrightness() : 1.0f, java.lang.Math.min(this.mBrightnessRangeController.getCurrentBrightnessMin(), maxBrightness))) | this.mCachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.brightnessMax, java.lang.Math.min(this.mBrightnessRangeController.getCurrentBrightnessMax(), maxBrightness)) | this.mCachedBrightnessInfo.checkAndSetInt(this.mCachedBrightnessInfo.hbmMode, this.mBrightnessRangeController.getHighBrightnessMode()) | this.mCachedBrightnessInfo.checkAndSetFloat(this.mCachedBrightnessInfo.hbmTransitionPoint, this.mBrightnessRangeController.getTransitionPoint()) | this.mCachedBrightnessInfo.checkAndSetInt(this.mCachedBrightnessInfo.brightnessMaxReason, this.mBrightnessClamperController.getBrightnessMaxReason());
        }
        return checkAndSetFloat;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: postBrightnessChangeRunnable, reason: merged with bridge method [inline-methods] */
    public void lambda$new$2() {
        if (!this.mHandler.hasCallbacks(this.mOnBrightnessChangeRunnable)) {
            this.mHandler.post(this.mOnBrightnessChangeRunnable);
        }
    }

    private com.android.server.display.HighBrightnessModeController createHbmControllerLocked(com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, java.lang.Runnable runnable) {
        com.android.server.display.DisplayDeviceConfig displayDeviceConfig = this.mDisplayDevice.getDisplayDeviceConfig();
        android.os.IBinder displayTokenLocked = this.mDisplayDevice.getDisplayTokenLocked();
        java.lang.String uniqueId = this.mDisplayDevice.getUniqueId();
        com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig != null ? displayDeviceConfig.getHighBrightnessModeData() : null;
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = this.mDisplayDevice.getDisplayDeviceInfoLocked();
        return this.mInjector.getHighBrightnessModeController(this.mHandler, displayDeviceInfoLocked.width, displayDeviceInfoLocked.height, displayTokenLocked, uniqueId, 0.0f, 1.0f, highBrightnessModeData, new com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda4
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public final float getHdrBrightnessFromSdr(float f, float f2) {
                float lambda$createHbmControllerLocked$6;
                lambda$createHbmControllerLocked$6 = com.android.server.display.DisplayPowerController.this.lambda$createHbmControllerLocked$6(f, f2);
                return lambda$createHbmControllerLocked$6;
            }
        }, runnable, highBrightnessModeMetadata, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ float lambda$createHbmControllerLocked$6(float f, float f2) {
        return this.mDisplayDeviceConfig.getHdrBrightnessFromSdr(f, f2);
    }

    private com.android.server.display.BrightnessThrottler createBrightnessThrottlerLocked() {
        return new com.android.server.display.BrightnessThrottler(this.mHandler, new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$createBrightnessThrottlerLocked$7();
            }
        }, this.mUniqueDisplayId, this.mLogicalDisplay.getDisplayInfoLocked().thermalBrightnessThrottlingDataId, this.mLogicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createBrightnessThrottlerLocked$7() {
        sendUpdatePowerState();
        lambda$new$2();
    }

    private void blockScreenOn() {
        if (this.mPendingScreenOnUnblocker == null) {
            android.os.Trace.asyncTraceBegin(131072L, SCREEN_ON_BLOCKED_TRACE_NAME, 0);
            this.mPendingScreenOnUnblocker = new com.android.server.display.DisplayPowerController.ScreenOnUnblocker();
            this.mScreenOnBlockStartRealTime = android.os.SystemClock.elapsedRealtime();
            android.util.Slog.i(this.mTag, "Blocking screen on until initial contents have been drawn.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unblockScreenOn() {
        if (this.mPendingScreenOnUnblocker != null) {
            this.mPendingScreenOnUnblocker = null;
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mScreenOnBlockStartRealTime;
            android.util.Slog.i(this.mTag, "Unblocked screen on after " + elapsedRealtime + " ms");
            android.os.Trace.asyncTraceEnd(131072L, SCREEN_ON_BLOCKED_TRACE_NAME, 0);
        }
    }

    private void blockScreenOff() {
        if (this.mPendingScreenOffUnblocker == null) {
            android.os.Trace.asyncTraceBegin(131072L, SCREEN_OFF_BLOCKED_TRACE_NAME, 0);
            this.mPendingScreenOffUnblocker = new com.android.server.display.DisplayPowerController.ScreenOffUnblocker();
            this.mScreenOffBlockStartRealTime = android.os.SystemClock.elapsedRealtime();
            android.util.Slog.i(this.mTag, "Blocking screen off");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unblockScreenOff() {
        if (this.mPendingScreenOffUnblocker != null) {
            this.mPendingScreenOffUnblocker = null;
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mScreenOffBlockStartRealTime;
            android.util.Slog.i(this.mTag, "Unblocked screen off after " + elapsedRealtime + " ms");
            android.os.Trace.asyncTraceEnd(131072L, SCREEN_OFF_BLOCKED_TRACE_NAME, 0);
        }
    }

    private void blockScreenOnByDisplayOffload(final android.hardware.display.DisplayManagerInternal.DisplayOffloadSession displayOffloadSession) {
        if (this.mPendingScreenOnUnblockerByDisplayOffload != null || displayOffloadSession == null) {
            return;
        }
        this.mScreenTurningOnWasBlockedByDisplayOffload = true;
        android.os.Trace.asyncTraceBegin(131072L, SCREEN_ON_BLOCKED_BY_DISPLAYOFFLOAD_TRACE_NAME, 0);
        this.mScreenOnBlockByDisplayOffloadStartRealTime = android.os.SystemClock.elapsedRealtime();
        this.mPendingScreenOnUnblockerByDisplayOffload = new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$blockScreenOnByDisplayOffload$8(displayOffloadSession);
            }
        };
        if (!displayOffloadSession.blockScreenOn(this.mPendingScreenOnUnblockerByDisplayOffload)) {
            this.mPendingScreenOnUnblockerByDisplayOffload = null;
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mScreenOnBlockByDisplayOffloadStartRealTime;
            android.util.Slog.w(this.mTag, "Tried blocking screen on for offloading but failed. So, end trace after " + elapsedRealtime + " ms.");
            android.os.Trace.asyncTraceEnd(131072L, SCREEN_ON_BLOCKED_BY_DISPLAYOFFLOAD_TRACE_NAME, 0);
            return;
        }
        android.util.Slog.i(this.mTag, "Blocking screen on for offloading.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onDisplayOffloadUnblockScreenOn, reason: merged with bridge method [inline-methods] */
    public void lambda$blockScreenOnByDisplayOffload$8(android.hardware.display.DisplayManagerInternal.DisplayOffloadSession displayOffloadSession) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(18, displayOffloadSession));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unblockScreenOnByDisplayOffload() {
        if (this.mPendingScreenOnUnblockerByDisplayOffload == null) {
            return;
        }
        this.mPendingScreenOnUnblockerByDisplayOffload = null;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mScreenOnBlockByDisplayOffloadStartRealTime;
        android.util.Slog.i(this.mTag, "Unblocked screen on for offloading after " + elapsedRealtime + " ms");
        android.os.Trace.asyncTraceEnd(131072L, SCREEN_ON_BLOCKED_BY_DISPLAYOFFLOAD_TRACE_NAME, 0);
    }

    private boolean setScreenState(int i) {
        return setScreenState(i, false);
    }

    private boolean setScreenState(int i, boolean z) {
        boolean z2 = i == 1;
        boolean z3 = i == 2;
        boolean z4 = this.mPowerState.getScreenState() != i;
        if (z3 && z4 && !this.mScreenTurningOnWasBlockedByDisplayOffload) {
            blockScreenOnByDisplayOffload(this.mDisplayOffloadSession);
        } else if (!z3 && this.mScreenTurningOnWasBlockedByDisplayOffload) {
            unblockScreenOnByDisplayOffload();
            this.mScreenTurningOnWasBlockedByDisplayOffload = false;
        }
        if (z4 || this.mReportedScreenStateToPolicy == -1) {
            if (z2 && !this.mDisplayPowerProximityStateController.isScreenOffBecauseOfProximity()) {
                if (this.mReportedScreenStateToPolicy == 2 || this.mReportedScreenStateToPolicy == -1) {
                    setReportedScreenState(3);
                    blockScreenOff();
                    this.mWindowManagerPolicy.screenTurningOff(this.mDisplayId, this.mPendingScreenOffUnblocker);
                    unblockScreenOff();
                } else if (this.mPendingScreenOffUnblocker != null) {
                    return false;
                }
            }
            if (!z && z4 && readyToUpdateDisplayState() && this.mPendingScreenOffUnblocker == null && this.mPendingScreenOnUnblockerByDisplayOffload == null) {
                android.os.Trace.traceCounter(131072L, "ScreenState", i);
                java.lang.String valueOf = java.lang.String.valueOf(i);
                try {
                    android.os.SystemProperties.set("debug.tracing.screen_state", valueOf);
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.e(this.mTag, "Failed to set a system property: key=debug.tracing.screen_state value=" + valueOf + " " + e.getMessage());
                }
                this.mPowerState.setScreenState(i);
                noteScreenState(i);
            }
        }
        if (z2 && this.mReportedScreenStateToPolicy != 0 && !this.mDisplayPowerProximityStateController.isScreenOffBecauseOfProximity()) {
            setReportedScreenState(0);
            unblockScreenOn();
            this.mWindowManagerPolicy.screenTurnedOff(this.mDisplayId, this.mIsInTransition);
        } else if (!z2 && this.mReportedScreenStateToPolicy == 3) {
            unblockScreenOff();
            this.mWindowManagerPolicy.screenTurnedOff(this.mDisplayId, this.mIsInTransition);
            setReportedScreenState(0);
        }
        if (!z2 && (this.mReportedScreenStateToPolicy == 0 || this.mReportedScreenStateToPolicy == -1)) {
            setReportedScreenState(1);
            if (this.mPowerState.getColorFadeLevel() == 0.0f) {
                blockScreenOn();
            } else {
                unblockScreenOn();
            }
            this.mWindowManagerPolicy.screenTurningOn(this.mDisplayId, this.mPendingScreenOnUnblocker);
        }
        return this.mPendingScreenOnUnblocker == null && this.mPendingScreenOnUnblockerByDisplayOffload == null;
    }

    private void setReportedScreenState(int i) {
        android.os.Trace.traceCounter(131072L, "ReportedScreenStateToPolicy", i);
        this.mReportedScreenStateToPolicy = i;
        if (i == 2) {
            this.mScreenTurningOnWasBlockedByDisplayOffload = false;
        }
    }

    private void loadAmbientLightSensor() {
        this.mLightSensor = com.android.server.display.utils.SensorUtils.findSensor(this.mSensorManager, this.mDisplayDeviceConfig.getAmbientLightSensor(), this.mDisplayId == 0 ? 5 : 0);
    }

    private void loadScreenOffBrightnessSensor() {
        this.mScreenOffBrightnessSensor = com.android.server.display.utils.SensorUtils.findSensor(this.mSensorManager, this.mDisplayDeviceConfig.getScreenOffBrightnessSensor(), 0);
    }

    private float clampScreenBrightness(float f) {
        if (java.lang.Float.isNaN(f)) {
            f = 0.0f;
        }
        return android.util.MathUtils.constrain(f, this.mBrightnessRangeController.getCurrentBrightnessMin(), this.mBrightnessRangeController.getCurrentBrightnessMax());
    }

    private void animateScreenBrightness(float f, float f2, float f3) {
        animateScreenBrightness(f, f2, f3, false);
    }

    private void animateScreenBrightness(float f, float f2, float f3, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(this.mTag, "Animating brightness: target=" + f + ", sdrTarget=" + f2 + ", rate=" + f3);
        }
        if (this.mScreenBrightnessRampAnimator.animateTo(f, f2, f3, z)) {
            android.os.Trace.traceCounter(131072L, "TargetScreenBrightness", (int) f);
            java.lang.String valueOf = java.lang.String.valueOf(f);
            try {
                android.os.SystemProperties.set("debug.tracing.screen_brightness", valueOf);
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(this.mTag, "Failed to set a system property: key=debug.tracing.screen_brightness value=" + valueOf + " " + e.getMessage());
            }
            noteScreenBrightness(f);
        }
    }

    private void animateScreenStateChange(int i, boolean z) {
        if (this.mColorFadeEnabled && (this.mColorFadeOnAnimator.isStarted() || this.mColorFadeOffAnimator.isStarted())) {
            if (i != 2) {
                return;
            } else {
                this.mPendingScreenOff = false;
            }
        }
        if (this.mDisplayBlanksAfterDozeConfig && android.view.Display.isDozeState(this.mPowerState.getScreenState()) && !android.view.Display.isDozeState(i)) {
            this.mPowerState.prepareColorFade(this.mContext, this.mColorFadeFadesConfig ? 2 : 0);
            if (this.mColorFadeOffAnimator != null) {
                this.mColorFadeOffAnimator.end();
            }
            setScreenState(1, i != 1);
        }
        if (this.mPendingScreenOff && i != 1) {
            setScreenState(1);
            this.mPendingScreenOff = false;
            this.mPowerState.dismissColorFadeResources();
        }
        if (i == 2) {
            if (setScreenState(2)) {
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 3) {
            if ((!this.mScreenBrightnessRampAnimator.isAnimating() || this.mPowerState.getScreenState() != 2) && setScreenState(3)) {
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 4) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating() || this.mPowerState.getScreenState() == 4) {
                if (this.mPowerState.getScreenState() != 4) {
                    if (!setScreenState(3)) {
                        return;
                    } else {
                        setScreenState(4);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        if (i == 6) {
            if (!this.mScreenBrightnessRampAnimator.isAnimating() || this.mPowerState.getScreenState() == 6) {
                if (this.mPowerState.getScreenState() != 6) {
                    if (!setScreenState(2)) {
                        return;
                    } else {
                        setScreenState(6);
                    }
                }
                this.mPowerState.setColorFadeLevel(1.0f);
                this.mPowerState.dismissColorFade();
                return;
            }
            return;
        }
        this.mPendingScreenOff = true;
        if (!this.mColorFadeEnabled) {
            this.mPowerState.setColorFadeLevel(0.0f);
        }
        if (this.mPowerState.getColorFadeLevel() == 0.0f) {
            setScreenState(1);
            this.mPendingScreenOff = false;
            this.mPowerState.dismissColorFadeResources();
        } else {
            if (z) {
                if (this.mPowerState.prepareColorFade(this.mContext, this.mColorFadeFadesConfig ? 2 : 1) && this.mPowerState.getScreenState() != 1) {
                    this.mColorFadeOffAnimator.start();
                    return;
                }
            }
            this.mColorFadeOffAnimator.end();
        }
    }

    private void sendOnStateChangedWithWakelock() {
        if (this.mWakelockController.acquireWakelock(4)) {
            this.mHandler.post(this.mWakelockController.getOnStateChangedRunnable());
        }
    }

    private void logDisplayPolicyChanged(int i) {
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1696);
        logMaker.setType(6);
        logMaker.setSubtype(i);
        com.android.internal.logging.MetricsLogger.action(logMaker);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSettingsChange() {
        this.mDisplayBrightnessController.setPendingScreenBrightness(this.mDisplayBrightnessController.getScreenBrightnessSetting());
        this.mAutomaticBrightnessStrategy.updatePendingAutoBrightnessAdjustments();
        this.mAutomaticBrightnessStrategy.setAutoBrightnessOneShotEnabled(getAutoBrightnessOneShotSetting());
        sendUpdatePowerState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBrightnessModeChange() {
        this.mAutomaticBrightnessStrategy.setUseAutoBrightness(android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2) == 1);
    }

    private boolean getAutoBrightnessOneShotSetting() {
        return lineageos.providers.LineageSettings.System.getIntForUser(this.mContext.getContentResolver(), "auto_brightness_one_shot", 0, -2) == 1;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public float getScreenBrightnessSetting() {
        return this.mDisplayBrightnessController.getScreenBrightnessSetting();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightness(float f) {
        this.mDisplayBrightnessController.setBrightness(clampScreenBrightness(f));
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightness(float f, int i) {
        this.mDisplayBrightnessController.setBrightness(clampScreenBrightness(f), i);
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public int getLeadDisplayId() {
        return this.mLeadDisplayId;
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setBrightnessToFollow(float f, float f2, float f3, boolean z) {
        this.mBrightnessRangeController.onAmbientLuxChange(f3);
        if (f2 == -1.0f) {
            this.mDisplayBrightnessController.setBrightnessToFollow(f, z);
        } else {
            float brightnessFromNits = this.mDisplayBrightnessController.getBrightnessFromNits(f2);
            if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(brightnessFromNits)) {
                this.mDisplayBrightnessController.setBrightnessToFollow(brightnessFromNits, z);
            } else {
                this.mDisplayBrightnessController.setBrightnessToFollow(f, z);
            }
        }
        sendUpdatePowerState();
    }

    private void notifyBrightnessTrackerChanged(float f, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6;
        float f2;
        float convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(f);
        if (!z4 && this.mAutomaticBrightnessController != null && !this.mAutomaticBrightnessController.isInIdleMode() && z3 && this.mBrightnessTracker != null && z5 && convertToAdjustedNits >= 0.0f) {
            if (z && (this.mAutomaticBrightnessController == null || !this.mAutomaticBrightnessController.hasValidAmbientLux())) {
                z6 = false;
            } else {
                z6 = z;
            }
            if (this.mPowerRequest.lowPowerMode) {
                f2 = this.mPowerRequest.screenLowPowerBrightnessFactor;
            } else {
                f2 = 1.0f;
            }
            this.mBrightnessTracker.notifyBrightnessChanged(convertToAdjustedNits, z6, f2, z2, this.mAutomaticBrightnessController.isDefaultConfig(), this.mUniqueDisplayId, this.mAutomaticBrightnessController.getLastSensorValues(), this.mAutomaticBrightnessController.getLastSensorTimestamps());
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void addDisplayBrightnessFollower(com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface) {
        synchronized (this.mLock) {
            this.mDisplayBrightnessFollowers.append(displayPowerControllerInterface.getDisplayId(), displayPowerControllerInterface);
            sendUpdatePowerStateLocked();
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void removeDisplayBrightnessFollower(final com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface) {
        synchronized (this.mLock) {
            this.mDisplayBrightnessFollowers.remove(displayPowerControllerInterface.getDisplayId());
            this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.DisplayPowerControllerInterface.this.setBrightnessToFollow(Float.NaN, -1.0f, 0.0f, false);
                }
            }, this.mClock.uptimeMillis());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void clearDisplayBrightnessFollowersLocked() {
        for (int i = 0; i < this.mDisplayBrightnessFollowers.size(); i++) {
            final com.android.server.display.DisplayPowerControllerInterface valueAt = this.mDisplayBrightnessFollowers.valueAt(i);
            this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.DisplayPowerControllerInterface.this.setBrightnessToFollow(Float.NaN, -1.0f, 0.0f, false);
                }
            }, this.mClock.uptimeMillis());
        }
        this.mDisplayBrightnessFollowers.clear();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void dump(final java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println();
            printWriter.println("Display Power Controller:");
            printWriter.println("  mDisplayId=" + this.mDisplayId);
            printWriter.println("  mLeadDisplayId=" + this.mLeadDisplayId);
            printWriter.println("  mLightSensor=" + this.mLightSensor);
            printWriter.println("  mDisplayBrightnessFollowers=" + this.mDisplayBrightnessFollowers);
            printWriter.println();
            printWriter.println("Display Power Controller Locked State:");
            printWriter.println("  mDisplayReadyLocked=" + this.mDisplayReadyLocked);
            printWriter.println("  mPendingRequestLocked=" + this.mPendingRequestLocked);
            printWriter.println("  mPendingRequestChangedLocked=" + this.mPendingRequestChangedLocked);
            printWriter.println("  mPendingUpdatePowerStateLocked=" + this.mPendingUpdatePowerStateLocked);
        }
        printWriter.println();
        printWriter.println("Display Power Controller Configuration:");
        printWriter.println("  mScreenBrightnessDozeConfig=" + this.mScreenBrightnessDozeConfig);
        printWriter.println("  mUseSoftwareAutoBrightnessConfig=" + this.mUseSoftwareAutoBrightnessConfig);
        printWriter.println("  mSkipScreenOnBrightnessRamp=" + this.mSkipScreenOnBrightnessRamp);
        printWriter.println("  mColorFadeFadesConfig=" + this.mColorFadeFadesConfig);
        printWriter.println("  mColorFadeEnabled=" + this.mColorFadeEnabled);
        printWriter.println("  mIsDisplayInternal=" + this.mIsDisplayInternal);
        synchronized (this.mCachedBrightnessInfo) {
            printWriter.println("  mCachedBrightnessInfo.brightness=" + this.mCachedBrightnessInfo.brightness.value);
            printWriter.println("  mCachedBrightnessInfo.adjustedBrightness=" + this.mCachedBrightnessInfo.adjustedBrightness.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMin=" + this.mCachedBrightnessInfo.brightnessMin.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMax=" + this.mCachedBrightnessInfo.brightnessMax.value);
            printWriter.println("  mCachedBrightnessInfo.hbmMode=" + this.mCachedBrightnessInfo.hbmMode.value);
            printWriter.println("  mCachedBrightnessInfo.hbmTransitionPoint=" + this.mCachedBrightnessInfo.hbmTransitionPoint.value);
            printWriter.println("  mCachedBrightnessInfo.brightnessMaxReason =" + this.mCachedBrightnessInfo.brightnessMaxReason.value);
        }
        printWriter.println("  mDisplayBlanksAfterDozeConfig=" + this.mDisplayBlanksAfterDozeConfig);
        printWriter.println("  mBrightnessBucketsInDozeConfig=" + this.mBrightnessBucketsInDozeConfig);
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayPowerController.this.lambda$dump$11(printWriter);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public void lambda$dump$11(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Display Power Controller Thread State:");
        printWriter.println("  mPowerRequest=" + this.mPowerRequest);
        printWriter.println("  mBrightnessReason=" + this.mBrightnessReason);
        printWriter.println("  mAppliedDimming=" + this.mAppliedDimming);
        printWriter.println("  mAppliedThrottling=" + this.mAppliedThrottling);
        printWriter.println("  mDozing=" + this.mDozing);
        printWriter.println("  mSkipRampState=" + skipRampStateToString(this.mSkipRampState));
        printWriter.println("  mScreenOnBlockStartRealTime=" + this.mScreenOnBlockStartRealTime);
        printWriter.println("  mScreenOffBlockStartRealTime=" + this.mScreenOffBlockStartRealTime);
        printWriter.println("  mPendingScreenOnUnblocker=" + this.mPendingScreenOnUnblocker);
        printWriter.println("  mPendingScreenOffUnblocker=" + this.mPendingScreenOffUnblocker);
        printWriter.println("  mPendingScreenOff=" + this.mPendingScreenOff);
        printWriter.println("  mReportedToPolicy=" + reportedToPolicyToString(this.mReportedScreenStateToPolicy));
        printWriter.println("  mIsRbcActive=" + this.mIsRbcActive);
        java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
        this.mAutomaticBrightnessStrategy.dump(indentingPrintWriter);
        if (this.mScreenBrightnessRampAnimator != null) {
            printWriter.println("  mScreenBrightnessRampAnimator.isAnimating()=" + this.mScreenBrightnessRampAnimator.isAnimating());
        }
        if (this.mColorFadeOnAnimator != null) {
            printWriter.println("  mColorFadeOnAnimator.isStarted()=" + this.mColorFadeOnAnimator.isStarted());
        }
        if (this.mColorFadeOffAnimator != null) {
            printWriter.println("  mColorFadeOffAnimator.isStarted()=" + this.mColorFadeOffAnimator.isStarted());
        }
        if (this.mPowerState != null) {
            this.mPowerState.dump(printWriter);
        }
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.dump(printWriter);
            dumpBrightnessEvents(printWriter);
        }
        dumpRbcEvents(printWriter);
        if (this.mScreenOffBrightnessSensorController != null) {
            this.mScreenOffBrightnessSensorController.dump(printWriter);
        }
        if (this.mBrightnessRangeController != null) {
            this.mBrightnessRangeController.dump(printWriter);
        }
        if (this.mBrightnessThrottler != null) {
            this.mBrightnessThrottler.dump(printWriter);
        }
        printWriter.println();
        if (this.mDisplayWhiteBalanceController != null) {
            this.mDisplayWhiteBalanceController.dump(printWriter);
            this.mDisplayWhiteBalanceSettings.dump(printWriter);
        }
        printWriter.println();
        if (this.mWakelockController != null) {
            this.mWakelockController.dumpLocal(printWriter);
        }
        printWriter.println();
        if (this.mDisplayBrightnessController != null) {
            this.mDisplayBrightnessController.dump(printWriter);
        }
        printWriter.println();
        if (this.mDisplayStateController != null) {
            this.mDisplayStateController.dumpsys(printWriter);
        }
        printWriter.println();
        if (this.mBrightnessClamperController != null) {
            this.mBrightnessClamperController.dump(indentingPrintWriter);
        }
    }

    private static java.lang.String reportedToPolicyToString(int i) {
        switch (i) {
            case 0:
                return "REPORTED_TO_POLICY_SCREEN_OFF";
            case 1:
                return "REPORTED_TO_POLICY_SCREEN_TURNING_ON";
            case 2:
                return "REPORTED_TO_POLICY_SCREEN_ON";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String skipRampStateToString(int i) {
        switch (i) {
            case 0:
                return "RAMP_STATE_SKIP_NONE";
            case 1:
                return "RAMP_STATE_SKIP_INITIAL";
            case 2:
                return "RAMP_STATE_SKIP_AUTOBRIGHT";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private void dumpBrightnessEvents(java.io.PrintWriter printWriter) {
        int size = this.mBrightnessEventRingBuffer.size();
        if (size < 1) {
            printWriter.println("No Automatic Brightness Adjustments");
            return;
        }
        printWriter.println("Automatic Brightness Adjustments Last " + size + " Events: ");
        com.android.server.display.brightness.BrightnessEvent[] brightnessEventArr = (com.android.server.display.brightness.BrightnessEvent[]) this.mBrightnessEventRingBuffer.toArray();
        for (int i = 0; i < this.mBrightnessEventRingBuffer.size(); i++) {
            printWriter.println("  " + brightnessEventArr[i].toString());
        }
    }

    private void dumpRbcEvents(java.io.PrintWriter printWriter) {
        int size = this.mRbcEventRingBuffer.size();
        if (size < 1) {
            printWriter.println("No Reduce Bright Colors Adjustments");
            return;
        }
        printWriter.println("Reduce Bright Colors Adjustments Last " + size + " Events: ");
        com.android.server.display.brightness.BrightnessEvent[] brightnessEventArr = (com.android.server.display.brightness.BrightnessEvent[]) this.mRbcEventRingBuffer.toArray();
        for (int i = 0; i < this.mRbcEventRingBuffer.size(); i++) {
            printWriter.println("  " + brightnessEventArr[i]);
        }
    }

    private void noteScreenState(int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SCREEN_STATE_CHANGED_V2, i, this.mDisplayStatsId);
        if (this.mBatteryStats != null) {
            try {
                this.mBatteryStats.noteScreenState(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private void noteScreenBrightness(float f) {
        int brightnessFloatToInt;
        if (this.mBatteryStats != null) {
            try {
                if (this.mFlags.isBrightnessIntRangeUserPerceptionEnabled()) {
                    brightnessFloatToInt = com.android.internal.display.BrightnessSynchronizer.brightnessFloatToIntSetting(this.mContext, f);
                } else {
                    brightnessFloatToInt = com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(f);
                }
                this.mBatteryStats.noteScreenBrightness(brightnessFloatToInt);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportStats(float f) {
        if (this.mLastStatsBrightness == f) {
            return;
        }
        synchronized (this.mCachedBrightnessInfo) {
            try {
                if (this.mCachedBrightnessInfo.hbmTransitionPoint == null) {
                    return;
                }
                float f2 = this.mCachedBrightnessInfo.hbmTransitionPoint.value;
                boolean z = f > f2;
                boolean z2 = this.mLastStatsBrightness > f2;
                if (z || z2) {
                    this.mLastStatsBrightness = f;
                    this.mHandler.removeMessages(11);
                    if (z != z2) {
                        logHbmBrightnessStats(f, this.mDisplayStatsId);
                        return;
                    }
                    android.os.Message obtainMessage = this.mHandler.obtainMessage();
                    obtainMessage.what = 11;
                    obtainMessage.arg1 = java.lang.Float.floatToIntBits(f);
                    obtainMessage.arg2 = this.mDisplayStatsId;
                    this.mHandler.sendMessageAtTime(obtainMessage, this.mClock.uptimeMillis() + 500);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logHbmBrightnessStats(float f, int i) {
        synchronized (this.mHandler) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_BRIGHTNESS_CHANGED, i, f);
        }
    }

    private int nitsToRangeIndex(float f) {
        for (int i = 0; i < BRIGHTNESS_RANGE_BOUNDARIES.length; i++) {
            if (f < BRIGHTNESS_RANGE_BOUNDARIES[i]) {
                return BRIGHTNESS_RANGE_INDEX[i];
            }
        }
        return 38;
    }

    private int convertBrightnessReasonToStatsEnum(int i) {
        switch (i) {
        }
        return 0;
    }

    private void logBrightnessEvent(com.android.server.display.brightness.BrightnessEvent brightnessEvent, float f) {
        float convertToAdjustedNits;
        float convertToAdjustedNits2;
        int modifier = brightnessEvent.getReason().getModifier();
        int flags = brightnessEvent.getFlags();
        boolean z = f == brightnessEvent.getHbmMax();
        float convertToAdjustedNits3 = this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getBrightness());
        float powerFactor = brightnessEvent.isLowPowerModeSet() ? brightnessEvent.getPowerFactor() : -1.0f;
        int rbcStrength = brightnessEvent.isRbcEnabled() ? brightnessEvent.getRbcStrength() : -1;
        if (brightnessEvent.getHbmMode() != 0) {
            convertToAdjustedNits = this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getHbmMax());
        } else {
            convertToAdjustedNits = -1.0f;
        }
        if (brightnessEvent.getThermalMax() != 1.0f) {
            convertToAdjustedNits2 = this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getThermalMax());
        } else {
            convertToAdjustedNits2 = -1.0f;
        }
        if (this.mIsDisplayInternal) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_BRIGHTNESS_CHANGED, this.mDisplayBrightnessController.convertToAdjustedNits(brightnessEvent.getInitialBrightness()), convertToAdjustedNits3, brightnessEvent.getLux(), brightnessEvent.getPhysicalDisplayId(), brightnessEvent.wasShortTermModelActive(), powerFactor, rbcStrength, convertToAdjustedNits, convertToAdjustedNits2, brightnessEvent.isAutomaticBrightnessEnabled(), 1, convertBrightnessReasonToStatsEnum(brightnessEvent.getReason().getReason()), nitsToRangeIndex(convertToAdjustedNits3), z, brightnessEvent.getHbmMode() == 1, brightnessEvent.getHbmMode() == 2, (modifier & 2) > 0, this.mBrightnessClamperController.getBrightnessMaxReason(), (modifier & 1) > 0, brightnessEvent.isRbcEnabled(), (flags & 2) > 0, (flags & 4) > 0, (flags & 8) > 0, brightnessEvent.getAutoBrightnessMode() == 1, (flags & 32) > 0);
        }
    }

    private boolean readyToUpdateDisplayState() {
        return this.mDisplayId == 0 || this.mBootCompleted;
    }

    private final class DisplayControllerHandler extends android.os.Handler {
        DisplayControllerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 2:
                    if (com.android.server.display.DisplayPowerController.this.mPendingScreenOnUnblocker == message.obj) {
                        com.android.server.display.DisplayPowerController.this.unblockScreenOn();
                        com.android.server.display.DisplayPowerController.this.lambda$new$0();
                        break;
                    }
                    break;
                case 3:
                    if (com.android.server.display.DisplayPowerController.this.mPendingScreenOffUnblocker == message.obj) {
                        com.android.server.display.DisplayPowerController.this.unblockScreenOff();
                        com.android.server.display.DisplayPowerController.this.lambda$new$0();
                        break;
                    }
                    break;
                case 4:
                    android.hardware.display.BrightnessConfiguration brightnessConfiguration = (android.hardware.display.BrightnessConfiguration) message.obj;
                    com.android.server.display.DisplayPowerController.this.mAutomaticBrightnessStrategy.setBrightnessConfiguration(brightnessConfiguration, message.arg1 == 1);
                    if (com.android.server.display.DisplayPowerController.this.mBrightnessTracker != null) {
                        com.android.server.display.DisplayPowerController.this.mBrightnessTracker.setShouldCollectColorSample(brightnessConfiguration != null && brightnessConfiguration.shouldCollectColorSamples());
                    }
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 5:
                    com.android.server.display.DisplayPowerController.this.mDisplayBrightnessController.setTemporaryBrightness(java.lang.Float.valueOf(java.lang.Float.intBitsToFloat(message.arg1)));
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 6:
                    com.android.server.display.DisplayPowerController.this.mAutomaticBrightnessStrategy.setTemporaryAutoBrightnessAdjustment(java.lang.Float.intBitsToFloat(message.arg1));
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 7:
                    com.android.server.display.DisplayPowerController.this.cleanupHandlerThreadAfterStop();
                    break;
                case 8:
                    if (!com.android.server.display.DisplayPowerController.this.mStopped) {
                        com.android.server.display.DisplayPowerController.this.handleSettingsChange();
                        break;
                    }
                    break;
                case 9:
                    com.android.server.display.DisplayPowerController.this.handleRbcChanged();
                    break;
                case 10:
                    if (com.android.server.display.DisplayPowerController.this.mPowerState != null) {
                        com.android.server.display.DisplayPowerController.this.reportStats(com.android.server.display.DisplayPowerController.this.mPowerState.getScreenBrightness());
                        break;
                    }
                    break;
                case 11:
                    com.android.server.display.DisplayPowerController.this.logHbmBrightnessStats(java.lang.Float.intBitsToFloat(message.arg1), message.arg2);
                    break;
                case 12:
                    com.android.server.display.DisplayPowerController.this.handleOnSwitchUser(message.arg1, message.arg2, message.obj instanceof java.lang.Float ? ((java.lang.Float) message.obj).floatValue() : Float.NaN);
                    break;
                case 13:
                    com.android.server.display.DisplayPowerController.this.mBootCompleted = true;
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 14:
                    com.android.server.display.DisplayPowerController.this.setDwbcStrongMode(message.arg1);
                    break;
                case 15:
                    com.android.server.display.DisplayPowerController.this.setDwbcOverride(java.lang.Float.intBitsToFloat(message.arg1));
                    break;
                case 16:
                    com.android.server.display.DisplayPowerController.this.setDwbcLoggingEnabled(message.arg1);
                    break;
                case 17:
                    com.android.server.display.DisplayPowerController.this.mDisplayBrightnessController.setBrightnessFromOffload(java.lang.Float.intBitsToFloat(message.arg1));
                    com.android.server.display.DisplayPowerController.this.lambda$new$0();
                    break;
                case 18:
                    if (com.android.server.display.DisplayPowerController.this.mDisplayOffloadSession == message.obj) {
                        com.android.server.display.DisplayPowerController.this.unblockScreenOnByDisplayOffload();
                        com.android.server.display.DisplayPowerController.this.lambda$new$0();
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri.equals(android.provider.Settings.System.getUriFor("screen_brightness_mode"))) {
                com.android.server.display.DisplayPowerController.this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerController$SettingsObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.display.DisplayPowerController.SettingsObserver.this.lambda$onChange$0();
                    }
                }, com.android.server.display.DisplayPowerController.this.mClock.uptimeMillis());
                return;
            }
            if (uri.equals(android.provider.Settings.System.getUriFor("screen_brightness_for_als"))) {
                int intForUser = android.provider.Settings.System.getIntForUser(com.android.server.display.DisplayPowerController.this.mContext.getContentResolver(), "screen_brightness_for_als", 2, -2);
                android.util.Slog.i(com.android.server.display.DisplayPowerController.this.mTag, "Setting up auto-brightness for preset " + com.android.server.display.config.DisplayBrightnessMappingConfig.autoBrightnessPresetToString(intForUser));
                com.android.server.display.DisplayPowerController.this.setUpAutoBrightness(com.android.server.display.DisplayPowerController.this.mContext, com.android.server.display.DisplayPowerController.this.mHandler);
                com.android.server.display.DisplayPowerController.this.sendUpdatePowerState();
                return;
            }
            com.android.server.display.DisplayPowerController.this.handleSettingsChange();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0() {
            com.android.server.display.DisplayPowerController.this.handleBrightnessModeChange();
            com.android.server.display.DisplayPowerController.this.lambda$new$0();
        }
    }

    private final class ScreenOnUnblocker implements com.android.server.policy.WindowManagerPolicy.ScreenOnListener {
        private ScreenOnUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOnListener
        public void onScreenOn() {
            com.android.server.display.DisplayPowerController.this.mHandler.sendMessageAtTime(com.android.server.display.DisplayPowerController.this.mHandler.obtainMessage(2, this), com.android.server.display.DisplayPowerController.this.mClock.uptimeMillis());
        }
    }

    private final class ScreenOffUnblocker implements com.android.server.policy.WindowManagerPolicy.ScreenOffListener {
        private ScreenOffUnblocker() {
        }

        @Override // com.android.server.policy.WindowManagerPolicy.ScreenOffListener
        public void onScreenOff() {
            com.android.server.display.DisplayPowerController.this.mHandler.sendMessageAtTime(com.android.server.display.DisplayPowerController.this.mHandler.obtainMessage(3, this), com.android.server.display.DisplayPowerController.this.mClock.uptimeMillis());
        }
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAutoBrightnessLoggingEnabled(boolean z) {
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.setLoggingEnabled(z);
        }
    }

    @Override // com.android.server.display.whitebalance.DisplayWhiteBalanceController.Callbacks
    public void updateWhiteBalance() {
        sendUpdatePowerState();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setDisplayWhiteBalanceLoggingEnabled(boolean z) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 16;
        obtainMessage.arg1 = z ? 1 : 0;
        obtainMessage.sendToTarget();
    }

    @Override // com.android.server.display.DisplayPowerControllerInterface
    public void setAmbientColorTemperatureOverride(float f) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 15;
        obtainMessage.arg1 = java.lang.Float.floatToIntBits(f);
        obtainMessage.sendToTarget();
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.DisplayPowerController.Clock getClock() {
            return new com.android.server.display.DisplayPowerController.Clock() { // from class: com.android.server.display.DisplayPowerController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayPowerController.Clock
                public final long uptimeMillis() {
                    return android.os.SystemClock.uptimeMillis();
                }
            };
        }

        com.android.server.display.DisplayPowerState getDisplayPowerState(com.android.server.display.DisplayBlanker displayBlanker, com.android.server.display.ColorFade colorFade, int i, int i2) {
            return new com.android.server.display.DisplayPowerState(displayBlanker, colorFade, i, i2);
        }

        com.android.server.display.RampAnimator.DualRampAnimator<com.android.server.display.DisplayPowerState> getDualRampAnimator(com.android.server.display.DisplayPowerState displayPowerState, android.util.FloatProperty<com.android.server.display.DisplayPowerState> floatProperty, android.util.FloatProperty<com.android.server.display.DisplayPowerState> floatProperty2) {
            return new com.android.server.display.RampAnimator.DualRampAnimator<>(displayPowerState, floatProperty, floatProperty2);
        }

        com.android.server.display.WakelockController getWakelockController(int i, android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks) {
            return new com.android.server.display.WakelockController(i, displayPowerCallbacks);
        }

        com.android.server.display.DisplayPowerProximityStateController getDisplayPowerProximityStateController(com.android.server.display.WakelockController wakelockController, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, android.os.Looper looper, java.lang.Runnable runnable, int i, android.hardware.SensorManager sensorManager) {
            return new com.android.server.display.DisplayPowerProximityStateController(wakelockController, displayDeviceConfig, looper, runnable, i, sensorManager, null);
        }

        com.android.server.display.AutomaticBrightnessController getAutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController.Callbacks callbacks, android.os.Looper looper, android.hardware.SensorManager sensorManager, android.hardware.Sensor sensor, android.util.SparseArray<com.android.server.display.BrightnessMappingStrategy> sparseArray, int i, float f, float f2, float f3, int i2, int i3, long j, long j2, long j3, long j4, boolean z, com.android.server.display.HysteresisLevels hysteresisLevels, com.android.server.display.HysteresisLevels hysteresisLevels2, com.android.server.display.HysteresisLevels hysteresisLevels3, com.android.server.display.HysteresisLevels hysteresisLevels4, android.content.Context context, com.android.server.display.BrightnessRangeController brightnessRangeController, com.android.server.display.BrightnessThrottler brightnessThrottler, int i4, int i5, float f4, float f5) {
            return new com.android.server.display.AutomaticBrightnessController(callbacks, looper, sensorManager, sensor, sparseArray, i, f, f2, f3, i2, i3, j, j2, j3, j4, z, hysteresisLevels, hysteresisLevels2, hysteresisLevels3, hysteresisLevels4, context, brightnessRangeController, brightnessThrottler, i4, i5, f4, f5);
        }

        com.android.server.display.BrightnessMappingStrategy getDefaultModeBrightnessMapper(android.content.Context context, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, com.android.server.display.whitebalance.DisplayWhiteBalanceController displayWhiteBalanceController) {
            return com.android.server.display.BrightnessMappingStrategy.create(context, displayDeviceConfig, 0, displayWhiteBalanceController);
        }

        com.android.server.display.HysteresisLevels getHysteresisLevels(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2) {
            return new com.android.server.display.HysteresisLevels(fArr, fArr2, fArr3, fArr4, f, f2);
        }

        com.android.server.display.HysteresisLevels getHysteresisLevels(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2, boolean z) {
            return new com.android.server.display.HysteresisLevels(fArr, fArr2, fArr3, fArr4, f, f2, z);
        }

        com.android.server.display.ScreenOffBrightnessSensorController getScreenOffBrightnessSensorController(android.hardware.SensorManager sensorManager, android.hardware.Sensor sensor, android.os.Handler handler, com.android.server.display.ScreenOffBrightnessSensorController.Clock clock, int[] iArr, com.android.server.display.BrightnessMappingStrategy brightnessMappingStrategy) {
            return new com.android.server.display.ScreenOffBrightnessSensorController(sensorManager, sensor, handler, clock, iArr, brightnessMappingStrategy);
        }

        com.android.server.display.HighBrightnessModeController getHighBrightnessModeController(android.os.Handler handler, int i, int i2, android.os.IBinder iBinder, java.lang.String str, float f, float f2, com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig, java.lang.Runnable runnable, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, android.content.Context context) {
            return new com.android.server.display.HighBrightnessModeController(handler, i, i2, iBinder, str, f, f2, highBrightnessModeData, hdrBrightnessDeviceConfig, runnable, highBrightnessModeMetadata, context);
        }

        com.android.server.display.BrightnessRangeController getBrightnessRangeController(com.android.server.display.HighBrightnessModeController highBrightnessModeController, java.lang.Runnable runnable, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, android.os.Handler handler, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.os.IBinder iBinder, com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
            return new com.android.server.display.BrightnessRangeController(highBrightnessModeController, runnable, displayDeviceConfig, handler, displayManagerFlags, iBinder, displayDeviceInfo);
        }

        com.android.server.display.brightness.clamper.BrightnessClamperController getBrightnessClamperController(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData displayDeviceData, android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            return new com.android.server.display.brightness.clamper.BrightnessClamperController(handler, clamperChangeListener, displayDeviceData, context, displayManagerFlags);
        }

        com.android.server.display.whitebalance.DisplayWhiteBalanceController getDisplayWhiteBalanceController(android.os.Handler handler, android.hardware.SensorManager sensorManager, android.content.res.Resources resources) {
            return com.android.server.display.whitebalance.DisplayWhiteBalanceFactory.create(handler, sensorManager, resources);
        }

        boolean isColorFadeEnabled() {
            return !android.app.ActivityManager.isLowRamDeviceStatic();
        }
    }

    static class CachedBrightnessInfo {
        public android.util.MutableFloat brightness = new android.util.MutableFloat(Float.NaN);
        public android.util.MutableFloat adjustedBrightness = new android.util.MutableFloat(Float.NaN);
        public android.util.MutableFloat brightnessMin = new android.util.MutableFloat(Float.NaN);
        public android.util.MutableFloat brightnessMax = new android.util.MutableFloat(Float.NaN);
        public android.util.MutableInt hbmMode = new android.util.MutableInt(0);
        public android.util.MutableFloat hbmTransitionPoint = new android.util.MutableFloat(Float.POSITIVE_INFINITY);
        public android.util.MutableInt brightnessMaxReason = new android.util.MutableInt(0);

        CachedBrightnessInfo() {
        }

        public boolean checkAndSetFloat(android.util.MutableFloat mutableFloat, float f) {
            if (mutableFloat.value != f) {
                mutableFloat.value = f;
                return true;
            }
            return false;
        }

        public boolean checkAndSetInt(android.util.MutableInt mutableInt, int i) {
            if (mutableInt.value != i) {
                mutableInt.value = i;
                return true;
            }
            return false;
        }
    }
}
