package com.android.server.power;

/* loaded from: classes2.dex */
public final class PowerManagerService extends com.android.server.SystemService implements com.android.server.Watchdog.Monitor {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_SPEW = false;
    private static final int DEFAULT_BUTTON_ON_DURATION = 5000;
    private static final int DEFAULT_DOUBLE_TAP_TO_WAKE = 0;
    private static final int DEFAULT_SCREEN_OFF_TIMEOUT = 15000;
    private static final int DEFAULT_SLEEP_TIMEOUT = -1;
    private static final int DIRTY_ACTUAL_DISPLAY_POWER_STATE_UPDATED = 8;
    private static final int DIRTY_ATTENTIVE = 16384;
    private static final int DIRTY_BATTERY_STATE = 256;
    private static final int DIRTY_BOOT_COMPLETED = 16;
    private static final int DIRTY_DISPLAY_GROUP_WAKEFULNESS = 65536;
    private static final int DIRTY_DOCK_STATE = 1024;
    private static final int DIRTY_IS_POWERED = 64;
    private static final int DIRTY_PROXIMITY_POSITIVE = 512;
    private static final int DIRTY_QUIESCENT = 4096;
    private static final int DIRTY_SCREEN_BRIGHTNESS_BOOST = 2048;
    private static final int DIRTY_SETTINGS = 32;
    private static final int DIRTY_STAY_ON = 128;
    private static final int DIRTY_USER_ACTIVITY = 4;
    private static final int DIRTY_WAKEFULNESS = 2;
    private static final int DIRTY_WAKE_LOCKS = 1;
    private static final long ENHANCED_DISCHARGE_PREDICTION_BROADCAST_MIN_DELAY_MS = 60000;
    private static final long ENHANCED_DISCHARGE_PREDICTION_TIMEOUT_MS = 1800000;
    private static final int HALT_MODE_REBOOT = 1;
    private static final int HALT_MODE_REBOOT_SAFE_MODE = 2;
    private static final int HALT_MODE_SHUTDOWN = 0;
    private static final java.lang.String HOLDING_DISPLAY_SUSPEND_BLOCKER = "holding display";
    private static final float INVALID_BRIGHTNESS_IN_CONFIG = -2.0f;
    static final long MIN_LONG_WAKE_CHECK_INTERVAL = 60000;
    private static final int MSG_ATTENTIVE_TIMEOUT = 5;
    private static final int MSG_CHECK_FOR_LONG_WAKELOCKS = 4;
    private static final int MSG_SANDMAN = 2;
    private static final int MSG_SCREEN_BRIGHTNESS_BOOST_TIMEOUT = 3;
    private static final int MSG_USER_ACTIVITY_TIMEOUT = 1;
    private static final int MSG_WAKE_UP = 6;
    private static final float PROXIMITY_NEAR_THRESHOLD = 5.0f;
    private static final java.lang.String REASON_BATTERY_THERMAL_STATE = "shutdown,thermal,battery";
    private static final java.lang.String REASON_LOW_BATTERY = "shutdown,battery";
    private static final java.lang.String REASON_REBOOT = "reboot";
    private static final java.lang.String REASON_SHUTDOWN = "shutdown";
    private static final java.lang.String REASON_THERMAL_SHUTDOWN = "shutdown,thermal";
    private static final java.lang.String REASON_USERREQUESTED = "shutdown,userrequested";
    public static final long REQUIRE_TURN_SCREEN_ON_PERMISSION = 216114297;
    private static final int SCREEN_BRIGHTNESS_BOOST_TIMEOUT = 5000;
    private static final int SCREEN_ON_LATENCY_WARNING_MS = 200;
    private static final java.lang.String SYSTEM_PROPERTY_QUIESCENT = "ro.boot.quiescent";
    private static final java.lang.String SYSTEM_PROPERTY_REBOOT_REASON = "sys.boot.reason";
    private static final java.lang.String SYSTEM_PROPERTY_RETAIL_DEMO_ENABLED = "sys.retaildemo.enabled";
    private static final java.lang.String TAG = "PowerManagerService";
    static final java.lang.String TRACE_SCREEN_ON = "Screen turning on";
    static final int USER_ACTIVITY_SCREEN_BRIGHT = 1;
    static final int USER_ACTIVITY_SCREEN_DIM = 2;
    static final int USER_ACTIVITY_SCREEN_DREAM = 4;
    static final int WAKE_LOCK_BUTTON_BRIGHT = 8;
    static final int WAKE_LOCK_CPU = 1;
    static final int WAKE_LOCK_DOZE = 64;
    static final int WAKE_LOCK_DRAW = 128;
    static final int WAKE_LOCK_PROXIMITY_SCREEN_OFF = 16;
    static final int WAKE_LOCK_SCREEN_BRIGHT = 2;
    static final int WAKE_LOCK_SCREEN_DIM = 4;
    static final int WAKE_LOCK_STAY_AWAKE = 32;
    private static boolean sQuiescent;
    private boolean mAlwaysOnEnabled;
    private final android.hardware.display.AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    private final com.android.server.power.AmbientDisplaySuppressionController mAmbientDisplaySuppressionController;
    private final com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback mAmbientSuppressionChangedCallback;
    private final com.android.server.power.AttentionDetector mAttentionDetector;
    private com.android.server.lights.LogicalLight mAttentionLight;
    private int mAttentiveTimeoutConfig;
    private long mAttentiveTimeoutSetting;
    private long mAttentiveWarningDurationConfig;
    private int mBatteryLevel;
    private boolean mBatteryLevelLow;
    private android.os.BatteryManagerInternal mBatteryManagerInternal;

    @android.annotation.Nullable
    private final com.android.server.power.batterysaver.BatterySaverStateMachine mBatterySaverStateMachine;
    private final boolean mBatterySaverSupported;
    private com.android.internal.app.IBatteryStats mBatteryStats;
    private final com.android.server.power.PowerManagerService.BinderService mBinderService;
    private boolean mBootCompleted;
    private final com.android.server.power.SuspendBlocker mBootingSuspendBlocker;
    private boolean mBrightWhenDozingConfig;
    private float mButtonBrightness;
    public final float mButtonBrightnessDefault;
    private float mButtonBrightnessOverrideFromWindowManager;
    private boolean mButtonLightOnKeypressOnly;
    private int mButtonTimeout;
    private com.android.server.lights.LogicalLight mButtonsLight;
    private final com.android.server.power.PowerManagerService.Clock mClock;
    final com.android.server.power.PowerManagerService.Constants mConstants;
    private final android.content.Context mContext;
    private boolean mDecoupleHalAutoSuspendModeFromDisplayConfig;
    private boolean mDecoupleHalInteractiveModeFromDisplayConfig;
    private final com.android.server.display.feature.DeviceConfigParameterProvider mDeviceConfigProvider;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDeviceIdleMode;
    int[] mDeviceIdleTempWhitelist;
    int[] mDeviceIdleWhitelist;
    private int mDirty;
    private boolean mDisableScreenWakeLocksWhileCached;
    private android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private final android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
    private final com.android.server.power.SuspendBlocker mDisplaySuspendBlocker;
    private int mDockState;
    private boolean mDoubleTapWakeEnabled;
    private boolean mDozeAfterScreenOff;
    private int mDozeScreenBrightnessOverrideFromDreamManager;
    private float mDozeScreenBrightnessOverrideFromDreamManagerFloat;
    private int mDozeScreenStateOverrideFromDreamManager;
    private boolean mDozeStartInProgress;
    private boolean mDrawWakeLockOverrideFromSidekick;
    private android.service.dreams.DreamManagerInternal mDreamManager;
    private boolean mDreamsActivateOnDockSetting;
    private boolean mDreamsActivateOnSleepSetting;
    private boolean mDreamsActivatedOnDockByDefaultConfig;
    private boolean mDreamsActivatedOnSleepByDefaultConfig;
    private int mDreamsBatteryLevelDrain;
    private int mDreamsBatteryLevelDrainCutoffConfig;
    private int mDreamsBatteryLevelMinimumWhenNotPoweredConfig;
    private int mDreamsBatteryLevelMinimumWhenPoweredConfig;
    private boolean mDreamsDisabledByAmbientModeSuppressionConfig;
    private boolean mDreamsEnabledByDefaultConfig;
    private boolean mDreamsEnabledOnBatteryConfig;
    private boolean mDreamsEnabledSetting;
    private boolean mDreamsSupportedConfig;

    @com.android.internal.annotations.GuardedBy({"mEnhancedDischargeTimeLock"})
    private boolean mEnhancedDischargePredictionIsPersonalized;

    @com.android.internal.annotations.GuardedBy({"mEnhancedDischargeTimeLock"})
    private long mEnhancedDischargeTimeElapsed;
    private final java.lang.Object mEnhancedDischargeTimeLock;
    private final com.android.server.power.FaceDownDetector mFaceDownDetector;
    private final com.android.server.power.feature.PowerManagerFlags mFeatureFlags;
    private final com.android.internal.foldables.FoldGracePeriodProvider mFoldGracePeriodProvider;
    private boolean mForceNavbar;
    private boolean mForceSuspendActive;
    private int mForegroundProfile;
    private boolean mHalAutoSuspendModeEnabled;
    private boolean mHalInteractiveModeEnabled;
    private final android.os.Handler mHandler;
    private final com.android.server.ServiceThread mHandlerThread;
    private boolean mHoldingBootingSuspendBlocker;
    private boolean mHoldingDisplaySuspendBlocker;
    private boolean mHoldingWakeLockSuspendBlocker;
    private final com.android.server.power.InattentiveSleepWarningController mInattentiveSleepWarningOverlayController;
    private final com.android.server.power.PowerManagerService.Injector mInjector;
    private boolean mInterceptedPowerKeyForProximity;
    private boolean mIsFaceDown;
    private boolean mIsPowered;
    private boolean mKeepDreamingWhenUnplugging;
    private float mKeyboardBrightness;
    public final float mKeyboardBrightnessDefault;
    private com.android.server.lights.LogicalLight mKeyboardLight;
    private boolean mKeyboardVisible;

    @com.android.internal.annotations.GuardedBy({"mEnhancedDischargeTimeLock"})
    private long mLastEnhancedDischargeTimeUpdatedElapsed;
    private long mLastFlipTime;
    private int mLastGlobalSleepReason;
    private long mLastGlobalSleepTime;
    private long mLastGlobalSleepTimeRealtime;
    private int mLastGlobalWakeReason;
    private long mLastGlobalWakeTime;
    private long mLastGlobalWakeTimeRealtime;
    private long mLastInteractivePowerHintTime;
    private long mLastScreenBrightnessBoostTime;
    private long mLastWarningAboutUserActivityPermission;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mLightDeviceIdleMode;
    private com.android.server.lights.LightsManager mLightsManager;
    private final com.android.server.power.PowerManagerService.LocalService mLocalService;
    private final java.lang.Object mLock;
    private boolean mLowPowerStandbyActive;
    int[] mLowPowerStandbyAllowlist;
    private final com.android.server.power.LowPowerStandbyController mLowPowerStandbyController;
    private long mMaximumScreenDimDurationConfig;
    private float mMaximumScreenDimRatioConfig;
    private long mMaximumScreenOffTimeoutFromDeviceAdmin;
    private long mMinimumScreenOffTimeoutConfig;
    private final com.android.server.power.PowerManagerService.NativeWrapper mNativeWrapper;
    private com.android.server.power.Notifier mNotifier;
    private long mNotifyLongDispatched;
    private long mNotifyLongNextCheck;
    private long mNotifyLongScheduled;
    private long mOverriddenTimeout;
    private final com.android.server.power.PowerManagerService.PermissionCheckerWrapper mPermissionCheckerWrapper;
    private int mPlugType;
    private com.android.server.policy.WindowManagerPolicy mPolicy;
    private final com.android.server.power.PowerManagerService.PowerGroupWakefulnessChangeListener mPowerGroupWakefulnessChangeListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.power.PowerGroup> mPowerGroups;
    private final com.android.server.power.PowerManagerService.PowerPropertiesWrapper mPowerPropertiesWrapper;
    private final android.util.SparseArray<com.android.server.power.PowerManagerService.ProfilePowerState> mProfilePowerState;
    private android.hardware.SensorEventListener mProximityListener;
    private boolean mProximityPositive;
    private android.hardware.Sensor mProximitySensor;
    private int mProximityTimeOut;
    private boolean mProximityWakeEnabled;
    private boolean mProximityWakeEnabledByDefaultConfig;
    private android.os.PowerManager.WakeLock mProximityWakeLock;
    private boolean mProximityWakeSupported;
    private boolean mRequestWaitForNegativeProximity;
    private boolean mSandmanScheduled;
    private boolean mScreenBrightnessBoostInProgress;
    public final float mScreenBrightnessDefault;
    public final float mScreenBrightnessDim;
    public final float mScreenBrightnessDoze;
    public final float mScreenBrightnessMaximum;
    public final float mScreenBrightnessMinimum;
    private float mScreenBrightnessOverrideFromWindowManager;
    private long mScreenOffTimeoutSetting;
    private final com.android.server.power.ScreenUndimDetector mScreenUndimDetector;
    private android.hardware.SensorManager mSensorManager;
    private com.android.server.power.PowerManagerService.SettingsObserver mSettingsObserver;
    private long mSleepTimeoutSetting;
    private boolean mStayOn;
    private int mStayOnWhilePluggedInSetting;
    private boolean mSupportsDoubleTapWakeConfig;
    private final java.util.ArrayList<com.android.server.power.SuspendBlocker> mSuspendBlockers;
    private boolean mSuspendWhenScreenOffDueToProximityConfig;
    private final com.android.server.power.SystemPropertiesWrapper mSystemProperties;
    private boolean mSystemReady;
    private boolean mTheaterModeEnabled;
    private final android.util.SparseArray<com.android.server.power.PowerManagerService.UidState> mUidState;
    private boolean mUidsChanged;
    private boolean mUidsChanging;
    private boolean mUpdatePowerStateInProgress;
    private long mUserActivityTimeoutOverrideFromWindowManager;
    private int mUserId;
    private boolean mUserInactiveOverrideFromWindowManager;
    private int mWakeLockSummary;
    private final com.android.server.power.SuspendBlocker mWakeLockSuspendBlocker;
    private final java.util.ArrayList<com.android.server.power.PowerManagerService.WakeLock> mWakeLocks;
    private boolean mWakeUpWhenPluggedOrUnpluggedConfig;
    private boolean mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig;
    private boolean mWakeUpWhenPluggedOrUnpluggedSetting;
    private boolean mWakefulnessChanging;
    private int mWakefulnessRaw;
    private com.android.server.power.WirelessChargerDetector mWirelessChargerDetector;
    private static final java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static final android.util.IntArray DEFAULT_DISPLAY_GROUP_IDS = android.util.IntArray.wrap(new int[]{0});

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long elapsedRealtime();

        long uptimeMillis();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HaltMode {
    }

    @com.android.internal.annotations.VisibleForTesting
    interface PermissionCheckerWrapper {
        int checkPermissionForDataDelivery(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable java.lang.String str2);
    }

    @com.android.internal.annotations.VisibleForTesting
    interface PowerPropertiesWrapper {
        boolean permissionless_turn_screen_on();

        boolean waive_target_sdk_check_for_turn_screen_on();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAcquireSuspendBlocker(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeForceSuspend();

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeInit();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReleaseSuspendBlocker(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAutoSuspend(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPowerBoost(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeSetPowerMode(int i, boolean z);

    private final class DreamManagerStateListener implements android.service.dreams.DreamManagerInternal.DreamManagerStateListener {
        private DreamManagerStateListener() {
        }

        public void onKeepDreamingWhenUnpluggingChanged(boolean z) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.mKeepDreamingWhenUnplugging = z;
            }
        }
    }

    private final class PowerGroupWakefulnessChangeListener implements com.android.server.power.PowerGroup.PowerGroupListener {
        private PowerGroupWakefulnessChangeListener() {
        }

        @Override // com.android.server.power.PowerGroup.PowerGroupListener
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onWakefulnessChangedLocked(int i, int i2, long j, int i3, int i4, int i5, java.lang.String str, java.lang.String str2) {
            com.android.server.power.PowerManagerService.this.mWakefulnessChanging = true;
            com.android.server.power.PowerManagerService.this.mDirty |= 2;
            if (i2 == 1) {
                com.android.server.power.PowerManagerService.this.userActivityNoUpdateLocked((com.android.server.power.PowerGroup) com.android.server.power.PowerManagerService.this.mPowerGroups.get(i), j, 0, i3 != 13 ? 0 : 1, i4);
            }
            com.android.server.power.PowerManagerService.this.mDirty |= 65536;
            com.android.server.power.PowerManagerService.this.mNotifier.onGroupWakefulnessChangeStarted(i, i2, i3, j);
            com.android.server.power.PowerManagerService.this.updateGlobalWakefulnessLocked(j, i3, i4, i5, str, str2);
            com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
        }
    }

    private final class DisplayGroupPowerChangeListener implements android.hardware.display.DisplayManagerInternal.DisplayGroupListener {
        static final int DISPLAY_GROUP_ADDED = 0;
        static final int DISPLAY_GROUP_CHANGED = 2;
        static final int DISPLAY_GROUP_REMOVED = 1;

        private DisplayGroupPowerChangeListener() {
        }

        public void onDisplayGroupAdded(int i) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    if (com.android.server.power.PowerManagerService.this.mPowerGroups.contains(i)) {
                        android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Tried to add already existing group:" + i);
                        return;
                    }
                    com.android.server.power.PowerGroup powerGroup = new com.android.server.power.PowerGroup(i, com.android.server.power.PowerManagerService.this.mPowerGroupWakefulnessChangeListener, com.android.server.power.PowerManagerService.this.mNotifier, com.android.server.power.PowerManagerService.this.mDisplayManagerInternal, 1, false, i == 0, com.android.server.power.PowerManagerService.this.mClock.uptimeMillis());
                    com.android.server.power.PowerManagerService.this.mPowerGroups.append(i, powerGroup);
                    com.android.server.power.PowerManagerService.this.onPowerGroupEventLocked(0, powerGroup);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onDisplayGroupRemoved(int i) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    if (i == 0) {
                        android.util.Slog.wtf(com.android.server.power.PowerManagerService.TAG, "Tried to remove default display group: " + i);
                        return;
                    }
                    if (!com.android.server.power.PowerManagerService.this.mPowerGroups.contains(i)) {
                        android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Tried to remove non-existent group:" + i);
                        return;
                    }
                    com.android.server.power.PowerManagerService.this.onPowerGroupEventLocked(1, (com.android.server.power.PowerGroup) com.android.server.power.PowerManagerService.this.mPowerGroups.get(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onDisplayGroupChanged(int i) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    if (!com.android.server.power.PowerManagerService.this.mPowerGroups.contains(i)) {
                        android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Tried to change non-existent group: " + i);
                        return;
                    }
                    com.android.server.power.PowerManagerService.this.onPowerGroupEventLocked(2, (com.android.server.power.PowerGroup) com.android.server.power.PowerManagerService.this.mPowerGroups.get(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class ForegroundProfileObserver extends android.app.SynchronousUserSwitchObserver {
        private ForegroundProfileObserver() {
        }

        public void onUserSwitching(int i) throws android.os.RemoteException {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.mUserId = i;
            }
        }

        public void onForegroundProfileSwitch(int i) throws android.os.RemoteException {
            long uptimeMillis = com.android.server.power.PowerManagerService.this.mClock.uptimeMillis();
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.mForegroundProfile = i;
                com.android.server.power.PowerManagerService.this.maybeUpdateForegroundProfileLastActivityLocked(uptimeMillis);
            }
        }
    }

    private static final class ProfilePowerState {
        long mLastUserActivityTime;
        boolean mLockingNotified;
        long mScreenOffTimeout;
        final int mUserId;
        int mWakeLockSummary;

        public ProfilePowerState(int i, long j, long j2) {
            this.mUserId = i;
            this.mScreenOffTimeout = j;
            this.mLastUserActivityTime = j2;
        }
    }

    private final class Constants extends android.database.ContentObserver {
        private static final boolean DEFAULT_NO_CACHED_WAKE_LOCKS = true;
        private static final java.lang.String KEY_NO_CACHED_WAKE_LOCKS = "no_cached_wake_locks";
        public boolean NO_CACHED_WAKE_LOCKS;
        private final android.util.KeyValueListParser mParser;
        private android.content.ContentResolver mResolver;

        public Constants(android.os.Handler handler) {
            super(handler);
            this.NO_CACHED_WAKE_LOCKS = true;
            this.mParser = new android.util.KeyValueListParser(',');
        }

        public void start(android.content.ContentResolver contentResolver) {
            this.mResolver = contentResolver;
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_manager_constants"), false, this);
            updateConstants();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            updateConstants();
        }

        private void updateConstants() {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    this.mParser.setString(android.provider.Settings.Global.getString(this.mResolver, "power_manager_constants"));
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Bad alarm manager settings", e);
                }
                this.NO_CACHED_WAKE_LOCKS = this.mParser.getBoolean(KEY_NO_CACHED_WAKE_LOCKS, true);
            }
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  Settings power_manager_constants:");
            printWriter.print("    ");
            printWriter.print(KEY_NO_CACHED_WAKE_LOCKS);
            printWriter.print("=");
            printWriter.println(this.NO_CACHED_WAKE_LOCKS);
        }

        void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1133871366145L, this.NO_CACHED_WAKE_LOCKS);
            protoOutputStream.end(start);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class NativeWrapper {
        public void nativeInit(com.android.server.power.PowerManagerService powerManagerService) {
            powerManagerService.nativeInit();
        }

        public void nativeAcquireSuspendBlocker(java.lang.String str) {
            com.android.server.power.PowerManagerService.nativeAcquireSuspendBlocker(str);
        }

        public void nativeReleaseSuspendBlocker(java.lang.String str) {
            com.android.server.power.PowerManagerService.nativeReleaseSuspendBlocker(str);
        }

        public void nativeSetAutoSuspend(boolean z) {
            com.android.server.power.PowerManagerService.nativeSetAutoSuspend(z);
        }

        public void nativeSetPowerBoost(int i, int i2) {
            com.android.server.power.PowerManagerService.nativeSetPowerBoost(i, i2);
        }

        public boolean nativeSetPowerMode(int i, boolean z) {
            return com.android.server.power.PowerManagerService.nativeSetPowerMode(i, z);
        }

        public boolean nativeForceSuspend() {
            return com.android.server.power.PowerManagerService.nativeForceSuspend();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.power.Notifier createNotifier(android.os.Looper looper, android.content.Context context, com.android.internal.app.IBatteryStats iBatteryStats, com.android.server.power.SuspendBlocker suspendBlocker, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, com.android.server.power.FaceDownDetector faceDownDetector, com.android.server.power.ScreenUndimDetector screenUndimDetector, java.util.concurrent.Executor executor) {
            return new com.android.server.power.Notifier(looper, context, iBatteryStats, suspendBlocker, windowManagerPolicy, faceDownDetector, screenUndimDetector, executor);
        }

        com.android.server.power.SuspendBlocker createSuspendBlocker(com.android.server.power.PowerManagerService powerManagerService, java.lang.String str) {
            java.util.Objects.requireNonNull(powerManagerService);
            com.android.server.power.PowerManagerService.SuspendBlockerImpl suspendBlockerImpl = powerManagerService.new SuspendBlockerImpl(str);
            powerManagerService.mSuspendBlockers.add(suspendBlockerImpl);
            return suspendBlockerImpl;
        }

        com.android.server.power.batterysaver.BatterySaverStateMachine createBatterySaverStateMachine(java.lang.Object obj, android.content.Context context) {
            com.android.server.power.batterysaver.BatterySavingStats batterySavingStats = new com.android.server.power.batterysaver.BatterySavingStats(obj);
            return new com.android.server.power.batterysaver.BatterySaverStateMachine(obj, context, new com.android.server.power.batterysaver.BatterySaverController(obj, context, com.android.internal.os.BackgroundThread.get().getLooper(), new com.android.server.power.batterysaver.BatterySaverPolicy(obj, context, batterySavingStats), batterySavingStats));
        }

        com.android.server.power.PowerManagerService.NativeWrapper createNativeWrapper() {
            return new com.android.server.power.PowerManagerService.NativeWrapper();
        }

        com.android.server.power.WirelessChargerDetector createWirelessChargerDetector(android.hardware.SensorManager sensorManager, com.android.server.power.SuspendBlocker suspendBlocker, android.os.Handler handler) {
            return new com.android.server.power.WirelessChargerDetector(sensorManager, suspendBlocker, handler);
        }

        android.hardware.display.AmbientDisplayConfiguration createAmbientDisplayConfiguration(android.content.Context context) {
            return new android.hardware.display.AmbientDisplayConfiguration(context);
        }

        com.android.server.power.AmbientDisplaySuppressionController createAmbientDisplaySuppressionController(@android.annotation.NonNull com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback ambientDisplaySuppressionChangedCallback) {
            return new com.android.server.power.AmbientDisplaySuppressionController(ambientDisplaySuppressionChangedCallback);
        }

        com.android.server.power.InattentiveSleepWarningController createInattentiveSleepWarningController() {
            return new com.android.server.power.InattentiveSleepWarningController();
        }

        com.android.internal.foldables.FoldGracePeriodProvider createFoldGracePeriodProvider() {
            return new com.android.internal.foldables.FoldGracePeriodProvider();
        }

        public com.android.server.power.SystemPropertiesWrapper createSystemPropertiesWrapper() {
            return new com.android.server.power.SystemPropertiesWrapper() { // from class: com.android.server.power.PowerManagerService.Injector.1
                @Override // com.android.server.power.SystemPropertiesWrapper
                public java.lang.String get(java.lang.String str, java.lang.String str2) {
                    return android.os.SystemProperties.get(str, str2);
                }

                @Override // com.android.server.power.SystemPropertiesWrapper
                public void set(java.lang.String str, java.lang.String str2) {
                    android.os.SystemProperties.set(str, str2);
                }
            };
        }

        com.android.server.power.PowerManagerService.Clock createClock() {
            return new com.android.server.power.PowerManagerService.Clock() { // from class: com.android.server.power.PowerManagerService.Injector.2
                @Override // com.android.server.power.PowerManagerService.Clock
                public long uptimeMillis() {
                    return android.os.SystemClock.uptimeMillis();
                }

                @Override // com.android.server.power.PowerManagerService.Clock
                public long elapsedRealtime() {
                    return android.os.SystemClock.elapsedRealtime();
                }
            };
        }

        android.os.Handler createHandler(android.os.Looper looper, android.os.Handler.Callback callback) {
            return new android.os.Handler(looper, callback, true);
        }

        void invalidateIsInteractiveCaches() {
            android.os.PowerManager.invalidateIsInteractiveCaches();
        }

        com.android.server.power.LowPowerStandbyController createLowPowerStandbyController(android.content.Context context, android.os.Looper looper) {
            return new com.android.server.power.LowPowerStandbyController(context, looper);
        }

        com.android.server.power.PowerManagerService.PermissionCheckerWrapper createPermissionCheckerWrapper() {
            return new com.android.server.power.PowerManagerService.PermissionCheckerWrapper() { // from class: com.android.server.power.PowerManagerService$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.power.PowerManagerService.PermissionCheckerWrapper
                public final int checkPermissionForDataDelivery(android.content.Context context, java.lang.String str, int i, android.content.AttributionSource attributionSource, java.lang.String str2) {
                    return android.content.PermissionChecker.checkPermissionForDataDelivery(context, str, i, attributionSource, str2);
                }
            };
        }

        com.android.server.power.PowerManagerService.PowerPropertiesWrapper createPowerPropertiesWrapper() {
            return new com.android.server.power.PowerManagerService.PowerPropertiesWrapper() { // from class: com.android.server.power.PowerManagerService.Injector.3
                @Override // com.android.server.power.PowerManagerService.PowerPropertiesWrapper
                public boolean waive_target_sdk_check_for_turn_screen_on() {
                    return ((java.lang.Boolean) android.sysprop.PowerProperties.waive_target_sdk_check_for_turn_screen_on().orElse(false)).booleanValue();
                }

                @Override // com.android.server.power.PowerManagerService.PowerPropertiesWrapper
                public boolean permissionless_turn_screen_on() {
                    return ((java.lang.Boolean) android.sysprop.PowerProperties.permissionless_turn_screen_on().orElse(false)).booleanValue();
                }
            };
        }

        com.android.server.display.feature.DeviceConfigParameterProvider createDeviceConfigParameterProvider() {
            return new com.android.server.display.feature.DeviceConfigParameterProvider(android.provider.DeviceConfigInterface.REAL);
        }

        com.android.server.power.feature.PowerManagerFlags getFlags() {
            return new com.android.server.power.feature.PowerManagerFlags();
        }
    }

    public PowerManagerService(android.content.Context context) {
        this(context, new com.android.server.power.PowerManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    PowerManagerService(android.content.Context context, com.android.server.power.PowerManagerService.Injector injector) {
        super(context);
        boolean z;
        this.mLock = com.android.server.LockGuard.installNewLock(1);
        this.mSuspendBlockers = new java.util.ArrayList<>();
        this.mWakeLocks = new java.util.ArrayList<>();
        this.mEnhancedDischargeTimeLock = new java.lang.Object();
        this.mDockState = 0;
        this.mMaximumScreenOffTimeoutFromDeviceAdmin = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        this.mIsFaceDown = false;
        this.mLastFlipTime = 0L;
        this.mButtonBrightnessOverrideFromWindowManager = Float.NaN;
        this.mScreenBrightnessOverrideFromWindowManager = Float.NaN;
        this.mOverriddenTimeout = -1L;
        this.mUserActivityTimeoutOverrideFromWindowManager = -1L;
        this.mDozeScreenStateOverrideFromDreamManager = 0;
        this.mDozeScreenBrightnessOverrideFromDreamManager = -1;
        this.mDozeScreenBrightnessOverrideFromDreamManagerFloat = Float.NaN;
        this.mLastWarningAboutUserActivityPermission = Long.MIN_VALUE;
        this.mDeviceIdleWhitelist = new int[0];
        this.mDeviceIdleTempWhitelist = new int[0];
        this.mLowPowerStandbyAllowlist = new int[0];
        this.mUidState = new android.util.SparseArray<>();
        this.mPowerGroups = new android.util.SparseArray<>();
        this.mProfilePowerState = new android.util.SparseArray<>();
        this.mDisplayPowerCallbacks = new android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks() { // from class: com.android.server.power.PowerManagerService.1
            public void onStateChanged() {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    com.android.server.power.PowerManagerService.this.mDirty |= 8;
                    com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                }
            }

            public void onProximityPositive() {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    com.android.server.power.PowerManagerService.this.mProximityPositive = true;
                    com.android.server.power.PowerManagerService.this.mDirty |= 512;
                    com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                }
            }

            public void onProximityNegative() {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    com.android.server.power.PowerManagerService.this.mProximityPositive = false;
                    com.android.server.power.PowerManagerService.this.mInterceptedPowerKeyForProximity = false;
                    com.android.server.power.PowerManagerService.this.mDirty |= 512;
                    com.android.server.power.PowerManagerService.this.userActivityNoUpdateLocked((com.android.server.power.PowerGroup) com.android.server.power.PowerManagerService.this.mPowerGroups.get(0), com.android.server.power.PowerManagerService.this.mClock.uptimeMillis(), 0, 0, 1000);
                    com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                }
            }

            public void onDisplayStateChange(boolean z2, boolean z3) {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    try {
                        com.android.server.power.PowerManagerService.this.setPowerModeInternal(9, z2);
                        if (z3) {
                            if (!com.android.server.power.PowerManagerService.this.mDecoupleHalInteractiveModeFromDisplayConfig) {
                                com.android.server.power.PowerManagerService.this.setHalInteractiveModeLocked(false);
                            }
                            if (!com.android.server.power.PowerManagerService.this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                                com.android.server.power.PowerManagerService.this.setHalAutoSuspendModeLocked(true);
                            }
                        } else {
                            if (!com.android.server.power.PowerManagerService.this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
                                com.android.server.power.PowerManagerService.this.setHalAutoSuspendModeLocked(false);
                            }
                            if (!com.android.server.power.PowerManagerService.this.mDecoupleHalInteractiveModeFromDisplayConfig) {
                                com.android.server.power.PowerManagerService.this.setHalInteractiveModeLocked(true);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void acquireSuspendBlocker(java.lang.String str) {
                com.android.server.power.PowerManagerService.this.mDisplaySuspendBlocker.acquire(str);
            }

            public void releaseSuspendBlocker(java.lang.String str) {
                com.android.server.power.PowerManagerService.this.mDisplaySuspendBlocker.release(str);
            }
        };
        this.mAmbientSuppressionChangedCallback = new com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback() { // from class: com.android.server.power.PowerManagerService.4
            @Override // com.android.server.power.AmbientDisplaySuppressionController.AmbientDisplaySuppressionChangedCallback
            public void onSuppressionChanged(boolean z2) {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    com.android.server.power.PowerManagerService.this.onDreamSuppressionChangedLocked(z2);
                }
            }
        };
        this.mContext = context;
        this.mBinderService = new com.android.server.power.PowerManagerService.BinderService(this.mContext);
        this.mLocalService = new com.android.server.power.PowerManagerService.LocalService();
        this.mNativeWrapper = injector.createNativeWrapper();
        this.mSystemProperties = injector.createSystemPropertiesWrapper();
        this.mClock = injector.createClock();
        this.mFeatureFlags = injector.getFlags();
        this.mInjector = injector;
        this.mHandlerThread = new com.android.server.ServiceThread(TAG, -4, false);
        this.mHandlerThread.start();
        this.mHandler = injector.createHandler(this.mHandlerThread.getLooper(), new com.android.server.power.PowerManagerService.PowerManagerHandlerCallback());
        this.mConstants = new com.android.server.power.PowerManagerService.Constants(this.mHandler);
        this.mFoldGracePeriodProvider = injector.createFoldGracePeriodProvider();
        this.mAmbientDisplayConfiguration = this.mInjector.createAmbientDisplayConfiguration(context);
        this.mAmbientDisplaySuppressionController = this.mInjector.createAmbientDisplaySuppressionController(this.mAmbientSuppressionChangedCallback);
        this.mAttentionDetector = new com.android.server.power.AttentionDetector(new java.lang.Runnable() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.PowerManagerService.this.onUserAttention();
            }
        }, this.mLock);
        this.mFaceDownDetector = new com.android.server.power.FaceDownDetector(new java.util.function.Consumer() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.PowerManagerService.this.onFlip(((java.lang.Boolean) obj).booleanValue());
            }
        });
        this.mScreenUndimDetector = new com.android.server.power.ScreenUndimDetector();
        this.mBatterySaverSupported = this.mContext.getResources().getBoolean(android.R.bool.config_batterySaverSupported);
        this.mBatterySaverStateMachine = this.mBatterySaverSupported ? this.mInjector.createBatterySaverStateMachine(this.mLock, this.mContext) : null;
        this.mLowPowerStandbyController = this.mInjector.createLowPowerStandbyController(this.mContext, android.os.Looper.getMainLooper());
        this.mInattentiveSleepWarningOverlayController = this.mInjector.createInattentiveSleepWarningController();
        this.mPermissionCheckerWrapper = this.mInjector.createPermissionCheckerWrapper();
        this.mPowerPropertiesWrapper = this.mInjector.createPowerPropertiesWrapper();
        this.mDeviceConfigProvider = this.mInjector.createDeviceConfigParameterProvider();
        this.mPowerGroupWakefulnessChangeListener = new com.android.server.power.PowerManagerService.PowerGroupWakefulnessChangeListener();
        this.mButtonBrightnessDefault = this.mContext.getResources().getFloat(1057292288);
        this.mKeyboardBrightnessDefault = this.mContext.getResources().getFloat(1057292289);
        float f = this.mContext.getResources().getFloat(android.R.dimen.config_preferredHyphenationFrequency);
        float f2 = this.mContext.getResources().getFloat(android.R.dimen.config_prefDialogWidth);
        float f3 = this.mContext.getResources().getFloat(android.R.dimen.config_pictureInPictureMinAspectRatio);
        float f4 = this.mContext.getResources().getFloat(android.R.dimen.config_pictureInPictureExpandedVerticalWidth);
        float f5 = this.mContext.getResources().getFloat(android.R.dimen.config_pictureInPictureExpandedHorizontalHeight);
        if (f == INVALID_BRIGHTNESS_IN_CONFIG || f2 == INVALID_BRIGHTNESS_IN_CONFIG || f3 == INVALID_BRIGHTNESS_IN_CONFIG) {
            this.mScreenBrightnessMinimum = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_stay_at_listening_from_sending_millis));
            this.mScreenBrightnessMaximum = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_stay_at_listening_from_receiving_millis));
            this.mScreenBrightnessDefault = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_nb_iot_inactivity_timeout_millis));
        } else {
            this.mScreenBrightnessMinimum = f;
            this.mScreenBrightnessMaximum = f2;
            this.mScreenBrightnessDefault = f3;
        }
        if (f4 == INVALID_BRIGHTNESS_IN_CONFIG) {
            this.mScreenBrightnessDoze = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_modem_image_switching_duration_millis));
        } else {
            this.mScreenBrightnessDoze = f4;
        }
        if (f5 == INVALID_BRIGHTNESS_IN_CONFIG) {
            this.mScreenBrightnessDim = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_demo_mode_nb_iot_inactivity_timeout_millis));
        } else {
            this.mScreenBrightnessDim = f5;
        }
        synchronized (this.mLock) {
            try {
                this.mBootingSuspendBlocker = this.mInjector.createSuspendBlocker(this, "PowerManagerService.Booting");
                this.mWakeLockSuspendBlocker = this.mInjector.createSuspendBlocker(this, "PowerManagerService.WakeLocks");
                this.mDisplaySuspendBlocker = this.mInjector.createSuspendBlocker(this, "PowerManagerService.Display");
                if (this.mBootingSuspendBlocker != null) {
                    this.mBootingSuspendBlocker.acquire();
                    this.mHoldingBootingSuspendBlocker = true;
                }
                if (this.mDisplaySuspendBlocker != null) {
                    this.mDisplaySuspendBlocker.acquire(HOLDING_DISPLAY_SUSPEND_BLOCKER);
                    this.mHoldingDisplaySuspendBlocker = true;
                }
                this.mHalAutoSuspendModeEnabled = false;
                this.mHalInteractiveModeEnabled = true;
                this.mWakefulnessRaw = 1;
                if (!this.mSystemProperties.get(SYSTEM_PROPERTY_QUIESCENT, "0").equals("1") && !((java.lang.Boolean) android.sysprop.InitProperties.userspace_reboot_in_progress().orElse(false)).booleanValue()) {
                    z = false;
                    sQuiescent = z;
                    this.mNativeWrapper.nativeInit(this);
                    this.mNativeWrapper.nativeSetAutoSuspend(false);
                    this.mNativeWrapper.nativeSetPowerMode(7, true);
                    this.mNativeWrapper.nativeSetPowerMode(0, false);
                    this.mInjector.invalidateIsInteractiveCaches();
                }
                z = true;
                sQuiescent = z;
                this.mNativeWrapper.nativeInit(this);
                this.mNativeWrapper.nativeSetAutoSuspend(false);
                this.mNativeWrapper.nativeSetPowerMode(7, true);
                this.mNativeWrapper.nativeSetPowerMode(0, false);
                this.mInjector.invalidateIsInteractiveCaches();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFlip(boolean z) {
        long j;
        synchronized (this.mLock) {
            try {
                if (this.mBootCompleted) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("onFlip(): Face ");
                    sb.append(z ? "down." : "up.");
                    android.util.Slog.i(TAG, sb.toString());
                    this.mIsFaceDown = z;
                    if (!z) {
                        j = 0;
                    } else {
                        long uptimeMillis = this.mClock.uptimeMillis();
                        this.mLastFlipTime = uptimeMillis;
                        j = (this.mPowerGroups.get(0).getLastUserActivityTimeLocked() + getScreenOffTimeoutLocked(getSleepTimeoutLocked(-1L), -1L)) - uptimeMillis;
                        userActivityInternal(0, uptimeMillis, 5, 1, 1000);
                    }
                    if (z) {
                        this.mFaceDownDetector.setMillisSaved(j);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("power", this.mBinderService, false, 1);
        publishLocalService(android.os.PowerManagerInternal.class, this.mLocalService);
        com.android.server.Watchdog.getInstance().addMonitor(this);
        com.android.server.Watchdog.getInstance().addThread(this.mHandler);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            systemReady();
            return;
        }
        if (i == 600) {
            incrementBootCount();
            return;
        }
        if (i == 1000) {
            synchronized (this.mLock) {
                try {
                    long uptimeMillis = this.mClock.uptimeMillis();
                    this.mBootCompleted = true;
                    this.mDirty |= 16;
                    if (this.mBatterySaverSupported) {
                        this.mBatterySaverStateMachine.onBootCompleted();
                    }
                    userActivityNoUpdateLocked(uptimeMillis, 0, 0, 1000);
                    updatePowerStateLocked();
                    if (sQuiescent) {
                        sleepPowerGroupLocked(this.mPowerGroups.get(0), this.mClock.uptimeMillis(), 10, 1000);
                    }
                    ((android.hardware.devicestate.DeviceStateManager) this.mContext.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(this.mHandler), new com.android.server.power.PowerManagerService.DeviceStateListener());
                } finally {
                }
            }
        }
    }

    private void systemReady() {
        byte b;
        byte b2;
        synchronized (this.mLock) {
            this.mSystemReady = true;
            this.mDreamManager = (android.service.dreams.DreamManagerInternal) getLocalService(android.service.dreams.DreamManagerInternal.class);
            this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) getLocalService(android.hardware.display.DisplayManagerInternal.class);
            this.mPolicy = (com.android.server.policy.WindowManagerPolicy) getLocalService(com.android.server.policy.WindowManagerPolicy.class);
            this.mBatteryManagerInternal = (android.os.BatteryManagerInternal) getLocalService(android.os.BatteryManagerInternal.class);
            this.mAttentionDetector.systemReady(this.mContext);
            android.hardware.SensorManager systemSensorManager = new android.hardware.SystemSensorManager(this.mContext, this.mHandler.getLooper());
            this.mBatteryStats = com.android.server.am.BatteryStatsService.getService();
            this.mNotifier = this.mInjector.createNotifier(android.os.Looper.getMainLooper(), this.mContext, this.mBatteryStats, this.mInjector.createSuspendBlocker(this, "PowerManagerService.Broadcasts"), this.mPolicy, this.mFaceDownDetector, this.mScreenUndimDetector, com.android.internal.os.BackgroundThread.getExecutor());
            this.mPowerGroups.append(0, new com.android.server.power.PowerGroup(1, this.mPowerGroupWakefulnessChangeListener, this.mNotifier, this.mDisplayManagerInternal, this.mClock.uptimeMillis()));
            b2 = 0;
            b = 0;
            byte b3 = 0;
            this.mDisplayManagerInternal.registerDisplayGroupListener(new com.android.server.power.PowerManagerService.DisplayGroupPowerChangeListener());
            this.mDreamManager.registerDreamManagerStateListener(new com.android.server.power.PowerManagerService.DreamManagerStateListener());
            this.mWirelessChargerDetector = this.mInjector.createWirelessChargerDetector(systemSensorManager, this.mInjector.createSuspendBlocker(this, "PowerManagerService.WirelessChargerDetector"), this.mHandler);
            this.mSettingsObserver = new com.android.server.power.PowerManagerService.SettingsObserver(this.mHandler);
            this.mLightsManager = (com.android.server.lights.LightsManager) getLocalService(com.android.server.lights.LightsManager.class);
            this.mAttentionLight = this.mLightsManager.getLight(5);
            this.mButtonsLight = this.mLightsManager.getLight(2);
            this.mKeyboardLight = this.mLightsManager.getLight(1);
            updateDeviceConfigLocked();
            this.mDeviceConfigProvider.addOnPropertiesChangedListener(com.android.internal.os.BackgroundThread.getExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.PowerManagerService$$ExternalSyntheticLambda2
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.power.PowerManagerService.this.lambda$systemReady$0(properties);
                }
            });
            this.mDisplayManagerInternal.initPowerManagement(this.mDisplayPowerCallbacks, this.mHandler, systemSensorManager);
            addPowerGroupsForNonDefaultDisplayGroupLocked();
            try {
                android.app.ActivityManager.getService().registerUserSwitchObserver(new com.android.server.power.PowerManagerService.ForegroundProfileObserver(), TAG);
            } catch (android.os.RemoteException e) {
            }
            this.mSensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
            this.mProximitySensor = this.mSensorManager.getDefaultSensor(8);
            this.mLowPowerStandbyController.systemReady();
            readConfigurationLocked();
            updateSettingsLocked();
            this.mDirty |= 256;
            updatePowerStateLocked();
        }
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mConstants.start(contentResolver);
        if (this.mBatterySaverSupported) {
            this.mBatterySaverStateMachine.systemReady();
        }
        this.mFaceDownDetector.systemReady(this.mContext);
        this.mScreenUndimDetector.systemReady(this.mContext);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_enabled"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_activate_on_sleep"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_activate_on_dock"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("screen_off_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("sleep_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("attentive_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("stay_on_while_plugged_in"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("theater_mode_on"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("doze_always_on"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("double_tap_to_wake"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("device_demo_mode"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("proximity_on_wake"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Global.getUriFor("wake_when_plugged_or_unplugged"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Secure.getUriFor("button_brightness"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Secure.getUriFor("button_backlight_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("button_backlight_only_when_pressed"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Secure.getUriFor("keyboard_brightness"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("force_show_navbar"), false, this.mSettingsObserver, -1);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiver(new com.android.server.power.PowerManagerService.BatteryReceiver(), intentFilter, null, this.mHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.DREAMING_STARTED");
        intentFilter2.addAction("android.intent.action.DREAMING_STOPPED");
        this.mContext.registerReceiver(new com.android.server.power.PowerManagerService.DreamReceiver(), intentFilter2, null, this.mHandler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiver(new com.android.server.power.PowerManagerService.UserSwitchedReceiver(), intentFilter3, null, this.mHandler);
        android.content.IntentFilter intentFilter4 = new android.content.IntentFilter();
        intentFilter4.addAction("android.intent.action.DOCK_EVENT");
        this.mContext.registerReceiver(new com.android.server.power.PowerManagerService.DockReceiver(), intentFilter4, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0(android.provider.DeviceConfig.Properties properties) {
        synchronized (this.mLock) {
            updateDeviceConfigLocked();
            updateWakeLockDisabledStatesLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void readConfigurationLocked() {
        android.content.res.Resources resources = this.mContext.getResources();
        this.mDecoupleHalAutoSuspendModeFromDisplayConfig = resources.getBoolean(android.R.bool.config_permissionsIndividuallyControlled);
        this.mDecoupleHalInteractiveModeFromDisplayConfig = resources.getBoolean(android.R.bool.config_persistBrightnessNitsForDefaultDisplay);
        this.mWakeUpWhenPluggedOrUnpluggedConfig = resources.getBoolean(android.R.bool.config_unfoldTransitionEnabled);
        this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromUnplug);
        this.mSuspendWhenScreenOffDueToProximityConfig = resources.getBoolean(android.R.bool.config_supportsSplitScreenMultiWindow);
        this.mAttentiveTimeoutConfig = resources.getInteger(android.R.integer.config_attentiveTimeout);
        this.mAttentiveWarningDurationConfig = resources.getInteger(android.R.integer.config_attentiveWarningDuration);
        this.mDreamsSupportedConfig = resources.getBoolean(android.R.bool.config_dreamsOnlyEnabledForDockUser);
        this.mDreamsEnabledByDefaultConfig = resources.getBoolean(android.R.bool.config_dreamsDisabledByAmbientModeSuppressionConfig);
        this.mDreamsActivatedOnSleepByDefaultConfig = resources.getBoolean(android.R.bool.config_dreamsActivatedOnDockByDefault);
        this.mDreamsActivatedOnDockByDefaultConfig = resources.getBoolean(android.R.bool.config_dozeWakeLockScreenSensorAvailable);
        this.mDreamsEnabledOnBatteryConfig = resources.getBoolean(android.R.bool.config_dreamsEnabledByDefault);
        this.mDreamsBatteryLevelMinimumWhenPoweredConfig = resources.getInteger(android.R.integer.config_dreamOverlayMaxReconnectAttempts);
        this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig = resources.getInteger(android.R.integer.config_dreamOpenAnimationDuration);
        this.mDreamsBatteryLevelDrainCutoffConfig = resources.getInteger(android.R.integer.config_dreamCloseAnimationDuration);
        this.mDreamsDisabledByAmbientModeSuppressionConfig = resources.getBoolean(android.R.bool.config_dreamsActivatedOnSleepByDefault);
        this.mDozeAfterScreenOff = resources.getBoolean(android.R.bool.config_dontPreferApn);
        this.mBrightWhenDozingConfig = resources.getBoolean(android.R.bool.config_brightWhenDozing);
        this.mMinimumScreenOffTimeoutConfig = resources.getInteger(android.R.integer.config_minMillisBetweenInputUserActivityEvents);
        this.mMaximumScreenDimDurationConfig = resources.getInteger(android.R.integer.config_maxScanTasksForHomeVisibility);
        this.mMaximumScreenDimRatioConfig = resources.getFraction(android.R.fraction.config_maximumScreenDimRatio, 1, 1);
        this.mSupportsDoubleTapWakeConfig = resources.getBoolean(android.R.bool.config_subscription_database_async_update);
        this.mProximityWakeSupported = resources.getBoolean(1057161233);
        this.mProximityWakeEnabledByDefaultConfig = resources.getBoolean(1057161234);
        this.mProximityTimeOut = resources.getInteger(1057423384);
        if (this.mProximityWakeSupported) {
            this.mProximityWakeLock = ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).newWakeLock(1, "ProximityWakeLock");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateSettingsLocked() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDreamsEnabledSetting = android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_enabled", this.mDreamsEnabledByDefaultConfig ? 1 : 0, -2) != 0;
        this.mDreamsActivateOnSleepSetting = android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_sleep", this.mDreamsActivatedOnSleepByDefaultConfig ? 1 : 0, -2) != 0;
        this.mDreamsActivateOnDockSetting = android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_dock", this.mDreamsActivatedOnDockByDefaultConfig ? 1 : 0, -2) != 0;
        this.mScreenOffTimeoutSetting = android.provider.Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 15000, -2);
        this.mSleepTimeoutSetting = android.provider.Settings.Secure.getIntForUser(contentResolver, "sleep_timeout", -1, -2);
        this.mAttentiveTimeoutSetting = android.provider.Settings.Secure.getIntForUser(contentResolver, "attentive_timeout", this.mAttentiveTimeoutConfig, -2);
        this.mStayOnWhilePluggedInSetting = android.provider.Settings.Global.getInt(contentResolver, "stay_on_while_plugged_in", 1);
        this.mTheaterModeEnabled = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1;
        this.mWakeUpWhenPluggedOrUnpluggedSetting = lineageos.providers.LineageSettings.Global.getInt(contentResolver, "wake_when_plugged_or_unplugged", this.mWakeUpWhenPluggedOrUnpluggedConfig ? 1 : 0) == 1;
        this.mAlwaysOnEnabled = this.mAmbientDisplayConfiguration.alwaysOnEnabled(-2);
        if (this.mSupportsDoubleTapWakeConfig) {
            boolean z = android.provider.Settings.Secure.getIntForUser(contentResolver, "double_tap_to_wake", 0, -2) != 0;
            if (z != this.mDoubleTapWakeEnabled) {
                this.mDoubleTapWakeEnabled = z;
                this.mNativeWrapper.nativeSetPowerMode(0, this.mDoubleTapWakeEnabled);
            }
        }
        java.lang.String str = android.os.UserManager.isDeviceInDemoMode(this.mContext) ? "1" : "0";
        if (!str.equals(this.mSystemProperties.get(SYSTEM_PROPERTY_RETAIL_DEMO_ENABLED, null))) {
            this.mSystemProperties.set(SYSTEM_PROPERTY_RETAIL_DEMO_ENABLED, str);
        }
        this.mProximityWakeEnabled = lineageos.providers.LineageSettings.System.getInt(contentResolver, "proximity_on_wake", this.mProximityWakeEnabledByDefaultConfig ? 1 : 0) == 1;
        this.mButtonTimeout = lineageos.providers.LineageSettings.Secure.getIntForUser(contentResolver, "button_backlight_timeout", 5000, -2);
        this.mButtonBrightness = lineageos.providers.LineageSettings.Secure.getFloatForUser(contentResolver, "button_brightness", this.mButtonBrightnessDefault, -2);
        this.mButtonLightOnKeypressOnly = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "button_backlight_only_when_pressed", 0, -2) == 1;
        this.mKeyboardBrightness = lineageos.providers.LineageSettings.Secure.getFloatForUser(contentResolver, "keyboard_brightness", this.mKeyboardBrightnessDefault, -2);
        this.mForceNavbar = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "force_show_navbar", 0, -2) == 1;
        this.mDirty |= 32;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void handleSettingsChangedLocked() {
        updateSettingsLocked();
        updatePowerStateLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateDeviceConfigLocked() {
        this.mDisableScreenWakeLocksWhileCached = this.mDeviceConfigProvider.isDisableScreenWakeLocksWhileCachedFeatureEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission(conditional = true, value = "android.permission.TURN_SCREEN_ON")
    public void acquireWakeLockInternal(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i3, int i4, @android.annotation.Nullable android.os.IWakeLockCallback iWakeLockCallback) {
        android.view.DisplayInfo displayInfo;
        com.android.server.power.PowerManagerService.UidState uidState;
        com.android.server.power.PowerManagerService.WakeLock wakeLock;
        boolean z;
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mSystemReady) {
                        displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                    } else {
                        displayInfo = null;
                    }
                    if (displayInfo == null) {
                        android.util.Slog.wtf(TAG, "Tried to acquire wake lock for invalid display: " + i);
                        return;
                    }
                    if (!displayInfo.hasAccess(i3)) {
                        throw new java.lang.SecurityException("Caller does not have access to display");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
            if (findWakeLockIndexLocked >= 0) {
                wakeLock = this.mWakeLocks.get(findWakeLockIndexLocked);
                if (!wakeLock.hasSameProperties(i2, str, workSource, i3, i4, iWakeLockCallback)) {
                    notifyWakeLockChangingLocked(wakeLock, i2, str, str2, i3, i4, workSource, str3, iWakeLockCallback);
                    wakeLock.updateProperties(i2, str, str2, workSource, str3, i3, i4, iWakeLockCallback);
                }
                z = false;
            } else {
                com.android.server.power.PowerManagerService.UidState uidState2 = this.mUidState.get(i3);
                if (uidState2 != null) {
                    uidState = uidState2;
                } else {
                    com.android.server.power.PowerManagerService.UidState uidState3 = new com.android.server.power.PowerManagerService.UidState(i3);
                    uidState3.mProcState = 20;
                    this.mUidState.put(i3, uidState3);
                    uidState = uidState3;
                }
                uidState.mNumWakeLocks++;
                com.android.server.power.PowerManagerService.WakeLock wakeLock2 = new com.android.server.power.PowerManagerService.WakeLock(iBinder, i, i2, str, str2, workSource, str3, i3, i4, uidState, iWakeLockCallback);
                this.mWakeLocks.add(wakeLock2);
                setWakeLockDisabledStateLocked(wakeLock2);
                wakeLock = wakeLock2;
                z = true;
            }
            applyWakeLockFlagsOnAcquireLocked(wakeLock);
            this.mDirty |= 1;
            updatePowerStateLocked();
            if (z) {
                notifyWakeLockAcquiredLocked(wakeLock);
            }
        }
    }

    private static boolean isScreenLock(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        switch (wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) {
            case 6:
            case 10:
            case 26:
                return true;
            default:
                return false;
        }
    }

    private static android.os.WorkSource.WorkChain getFirstNonEmptyWorkChain(android.os.WorkSource workSource) {
        if (workSource.getWorkChains() == null) {
            return null;
        }
        for (android.os.WorkSource.WorkChain workChain : workSource.getWorkChains()) {
            if (workChain.getSize() > 0) {
                return workChain;
            }
        }
        return null;
    }

    @android.annotation.RequiresPermission(conditional = true, value = "android.permission.TURN_SCREEN_ON")
    private boolean isAcquireCausesWakeupFlagAllowed(java.lang.String str, int i, int i2) {
        if (str == null) {
            return false;
        }
        if (this.mPermissionCheckerWrapper.checkPermissionForDataDelivery(this.mContext, "android.permission.TURN_SCREEN_ON", i2, new android.content.AttributionSource(i, str, null), "ACQUIRE_CAUSES_WAKEUP for " + str) == 0) {
            android.util.Slog.i(TAG, "Allowing device wake-up from app " + str);
            return true;
        }
        if (!android.app.compat.CompatChanges.isChangeEnabled(REQUIRE_TURN_SCREEN_ON_PERMISSION, i) && !this.mPowerPropertiesWrapper.waive_target_sdk_check_for_turn_screen_on()) {
            android.util.Slog.i(TAG, "Allowing device wake-up without android.permission.TURN_SCREEN_ON for " + str);
            return true;
        }
        if (this.mPowerPropertiesWrapper.permissionless_turn_screen_on()) {
            android.util.Slog.d(TAG, "Device wake-up allowed by debug.power.permissionless_turn_screen_on");
            return true;
        }
        android.util.Slog.w(TAG, "Not allowing device wake-up for " + str);
        return false;
    }

    @android.annotation.RequiresPermission(conditional = true, value = "android.permission.TURN_SCREEN_ON")
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void applyWakeLockFlagsOnAcquireLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        int i;
        java.lang.String str;
        int i2;
        int uid;
        java.lang.String str2;
        if ((wakeLock.mFlags & 268435456) != 0 && isScreenLock(wakeLock)) {
            if (wakeLock.mWorkSource != null && !wakeLock.mWorkSource.isEmpty()) {
                android.os.WorkSource workSource = wakeLock.mWorkSource;
                android.os.WorkSource.WorkChain firstNonEmptyWorkChain = getFirstNonEmptyWorkChain(workSource);
                if (firstNonEmptyWorkChain != null) {
                    str2 = firstNonEmptyWorkChain.getAttributionTag();
                    uid = firstNonEmptyWorkChain.getAttributionUid();
                } else {
                    java.lang.String packageName = workSource.getPackageName(0) != null ? workSource.getPackageName(0) : wakeLock.mPackageName;
                    uid = workSource.getUid(0);
                    str2 = packageName;
                }
                str = str2;
                i = -1;
                i2 = uid;
            } else {
                java.lang.String str3 = wakeLock.mPackageName;
                int i3 = wakeLock.mOwnerUid;
                i = wakeLock.mOwnerPid;
                str = str3;
                i2 = i3;
            }
            java.lang.Integer powerGroupId = wakeLock.getPowerGroupId();
            if (powerGroupId != null && isAcquireCausesWakeupFlagAllowed(str, i2, i)) {
                if (powerGroupId.intValue() == -1) {
                    for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
                        wakePowerGroupLocked(this.mPowerGroups.valueAt(i4), this.mClock.uptimeMillis(), 2, wakeLock.mTag, i2, str, i2);
                    }
                    return;
                }
                if (this.mPowerGroups.contains(powerGroupId.intValue())) {
                    wakePowerGroupLocked(this.mPowerGroups.get(powerGroupId.intValue()), this.mClock.uptimeMillis(), 2, wakeLock.mTag, i2, str, i2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWakeLockInternal(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    return;
                }
                com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(findWakeLockIndexLocked);
                if ((i & 1) != 0) {
                    this.mRequestWaitForNegativeProximity = true;
                }
                wakeLock.unlinkToDeath();
                wakeLock.setDisabled(true);
                removeWakeLockLocked(wakeLock, findWakeLockIndexLocked);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWakeLockDeath(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        synchronized (this.mLock) {
            try {
                int indexOf = this.mWakeLocks.indexOf(wakeLock);
                if (indexOf < 0) {
                    return;
                }
                removeWakeLockLocked(wakeLock, indexOf);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeWakeLockLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock, int i) {
        this.mWakeLocks.remove(i);
        com.android.server.power.PowerManagerService.UidState uidState = wakeLock.mUidState;
        uidState.mNumWakeLocks--;
        if (uidState.mNumWakeLocks <= 0 && uidState.mProcState == 20) {
            this.mUidState.remove(uidState.mUid);
        }
        notifyWakeLockReleasedLocked(wakeLock);
        applyWakeLockFlagsOnReleaseLocked(wakeLock);
        this.mDirty |= 1;
        updatePowerStateLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void applyWakeLockFlagsOnReleaseLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if ((wakeLock.mFlags & 536870912) != 0 && isScreenLock(wakeLock)) {
            userActivityNoUpdateLocked(this.mClock.uptimeMillis(), 0, 1, wakeLock.mOwnerUid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWakeLockWorkSourceInternal(android.os.IBinder iBinder, android.os.WorkSource workSource, java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    throw new java.lang.IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
                }
                com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(findWakeLockIndexLocked);
                if (!wakeLock.hasSameWorkSource(workSource)) {
                    notifyWakeLockChangingLocked(wakeLock, wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, workSource, str, null);
                    wakeLock.mHistoryTag = str;
                    wakeLock.updateWorkSource(workSource);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWakeLockCallbackInternal(android.os.IBinder iBinder, android.os.IWakeLockCallback iWakeLockCallback, int i) {
        synchronized (this.mLock) {
            try {
                int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
                if (findWakeLockIndexLocked < 0) {
                    throw new java.lang.IllegalArgumentException("Wake lock not active: " + iBinder + " from uid " + i);
                }
                com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(findWakeLockIndexLocked);
                if (!isSameCallback(iWakeLockCallback, wakeLock.mCallback)) {
                    notifyWakeLockChangingLocked(wakeLock, wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, iWakeLockCallback);
                    wakeLock.mCallback = iWakeLockCallback;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int findWakeLockIndexLocked(android.os.IBinder iBinder) {
        int size = this.mWakeLocks.size();
        for (int i = 0; i < size; i++) {
            if (this.mWakeLocks.get(i).mLock == iBinder) {
                return i;
            }
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.power.PowerManagerService.WakeLock findWakeLockLocked(android.os.IBinder iBinder) {
        int findWakeLockIndexLocked = findWakeLockIndexLocked(iBinder);
        if (findWakeLockIndexLocked == -1) {
            return null;
        }
        return this.mWakeLocks.get(findWakeLockIndexLocked);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyWakeLockAcquiredLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if (this.mSystemReady && !wakeLock.mDisabled) {
            wakeLock.mNotifiedAcquired = true;
            this.mNotifier.onWakeLockAcquired(wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, wakeLock.mCallback);
            restartNofifyLongTimerLocked(wakeLock);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enqueueNotifyLongMsgLocked(long j) {
        this.mNotifyLongScheduled = j;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(obtainMessage, j);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void restartNofifyLongTimerLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        wakeLock.mAcquireTime = this.mClock.uptimeMillis();
        if ((wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) == 1 && this.mNotifyLongScheduled == 0) {
            enqueueNotifyLongMsgLocked(wakeLock.mAcquireTime + 60000);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyWakeLockLongStartedLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if (this.mSystemReady && !wakeLock.mDisabled) {
            wakeLock.mNotifiedLong = true;
            this.mNotifier.onLongPartialWakeLockStart(wakeLock.mTag, wakeLock.mOwnerUid, wakeLock.mWorkSource, wakeLock.mHistoryTag);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyWakeLockLongFinishedLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if (wakeLock.mNotifiedLong) {
            wakeLock.mNotifiedLong = false;
            this.mNotifier.onLongPartialWakeLockFinish(wakeLock.mTag, wakeLock.mOwnerUid, wakeLock.mWorkSource, wakeLock.mHistoryTag);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyWakeLockChangingLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock, int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.WorkSource workSource, java.lang.String str3, android.os.IWakeLockCallback iWakeLockCallback) {
        if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
            this.mNotifier.onWakeLockChanging(wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, wakeLock.mCallback, i, str, str2, i2, i3, workSource, str3, iWakeLockCallback);
            notifyWakeLockLongFinishedLocked(wakeLock);
            restartNofifyLongTimerLocked(wakeLock);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyWakeLockReleasedLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if (this.mSystemReady && wakeLock.mNotifiedAcquired) {
            wakeLock.mNotifiedAcquired = false;
            wakeLock.mAcquireTime = 0L;
            this.mNotifier.onWakeLockReleased(wakeLock.mFlags, wakeLock.mTag, wakeLock.mPackageName, wakeLock.mOwnerUid, wakeLock.mOwnerPid, wakeLock.mWorkSource, wakeLock.mHistoryTag, wakeLock.mCallback);
            notifyWakeLockLongFinishedLocked(wakeLock);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWakeLockLevelSupportedInternal(int i) {
        synchronized (this.mLock) {
            boolean z = true;
            try {
                switch (i) {
                    case 1:
                    case 6:
                    case 10:
                    case 26:
                    case 64:
                    case 128:
                        return true;
                    case 32:
                        if (!this.mSystemReady || !this.mDisplayManagerInternal.isProximitySensorAvailable()) {
                            z = false;
                        }
                        return z;
                    default:
                        return false;
                }
            } finally {
            }
        }
    }

    private void userActivityFromNative(long j, int i, int i2, int i3) {
        userActivityInternal(i2, j, i, i3, 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void userActivityInternal(int i, long j, int i2, int i3, int i4) {
        synchronized (this.mLock) {
            try {
                if (i == -1) {
                    if (userActivityNoUpdateLocked(j, i2, i3, i4)) {
                        updatePowerStateLocked();
                    }
                    return;
                }
                android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                if (displayInfo == null) {
                    return;
                }
                int i5 = displayInfo.displayGroupId;
                if (i5 == -1) {
                    return;
                }
                if (userActivityNoUpdateLocked(this.mPowerGroups.get(i5), j, i2, i3, i4)) {
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void napInternal(long j, int i, boolean z) {
        synchronized (this.mLock) {
            dreamPowerGroupLocked(this.mPowerGroups.get(0), j, i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserAttention() {
        synchronized (this.mLock) {
            try {
                if (userActivityNoUpdateLocked(this.mPowerGroups.get(0), this.mClock.uptimeMillis(), 4, 0, 1000)) {
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean userActivityNoUpdateLocked(long j, int i, int i2, int i3) {
        boolean z = false;
        for (int i4 = 0; i4 < this.mPowerGroups.size(); i4++) {
            if (userActivityNoUpdateLocked(this.mPowerGroups.valueAt(i4), j, i, i2, i3)) {
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean userActivityNoUpdateLocked(com.android.server.power.PowerGroup powerGroup, long j, int i, int i2, int i3) {
        powerGroup.getGroupId();
        if (j < powerGroup.getLastSleepTimeLocked() || j < powerGroup.getLastWakeTimeLocked() || !this.mSystemReady) {
            return false;
        }
        android.os.Trace.traceBegin(131072L, "userActivity");
        try {
            if (j > this.mLastInteractivePowerHintTime) {
                setPowerBoostInternal(0, 0);
                this.mLastInteractivePowerHintTime = j;
            }
            this.mNotifier.onUserActivity(powerGroup.getGroupId(), i, i3);
            this.mAttentionDetector.onUserActivity(j, i);
            if (this.mUserInactiveOverrideFromWindowManager) {
                this.mUserInactiveOverrideFromWindowManager = false;
                this.mOverriddenTimeout = -1L;
            }
            int wakefulnessLocked = powerGroup.getWakefulnessLocked();
            if (wakefulnessLocked == 0 || wakefulnessLocked == 3 || (i2 & 2) != 0) {
                return false;
            }
            maybeUpdateForegroundProfileLastActivityLocked(j);
            if ((i2 & 1) != 0) {
                if (j > powerGroup.getLastUserActivityTimeNoChangeLightsLocked() && j > powerGroup.getLastUserActivityTimeLocked()) {
                    powerGroup.setLastUserActivityTimeNoChangeLightsLocked(j, i);
                    this.mDirty |= 4;
                    if (i == 1) {
                        this.mDirty |= 4096;
                    }
                    android.os.Trace.traceEnd(131072L);
                    return true;
                }
            } else if (j > powerGroup.getLastUserActivityTimeLocked()) {
                powerGroup.setButtonPressedLocked(i == 1);
                if (j == powerGroup.getLastWakeTimeLocked() || (this.mButtonLightOnKeypressOnly && powerGroup.getButtonPressedLocked() && (i2 & 4) == 0)) {
                    powerGroup.setButtonPressedLocked(true);
                    powerGroup.setLastButtonActivityTimeLocked(j);
                }
                powerGroup.setLastUserActivityTimeLocked(j, i);
                this.mDirty |= 4;
                if (i == 1) {
                    this.mDirty |= 4096;
                }
                android.os.Trace.traceEnd(131072L);
                return true;
            }
            android.os.Trace.traceEnd(131072L);
            return false;
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeUpdateForegroundProfileLastActivityLocked(long j) {
        com.android.server.power.PowerManagerService.ProfilePowerState profilePowerState = this.mProfilePowerState.get(this.mForegroundProfile);
        if (profilePowerState != null && j > profilePowerState.mLastUserActivityTime) {
            profilePowerState.mLastUserActivityTime = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void wakePowerGroupLocked(com.android.server.power.PowerGroup powerGroup, long j, int i, java.lang.String str, int i2, java.lang.String str2, int i3) {
        if (this.mForceSuspendActive || !this.mSystemReady) {
            return;
        }
        powerGroup.wakeUpLocked(j, i, str, i2, str2, i3, com.android.internal.util.LatencyTracker.getInstance(this.mContext));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean dreamPowerGroupLocked(com.android.server.power.PowerGroup powerGroup, long j, int i, boolean z) {
        if (!this.mBootCompleted || !this.mSystemReady) {
            return false;
        }
        return powerGroup.dreamLocked(j, i, z);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean dozePowerGroupLocked(com.android.server.power.PowerGroup powerGroup, long j, int i, int i2) {
        if (!this.mSystemReady || !this.mBootCompleted) {
            return false;
        }
        return powerGroup.dozeLocked(j, i2, i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean sleepPowerGroupLocked(com.android.server.power.PowerGroup powerGroup, long j, int i, int i2) {
        if (!this.mBootCompleted || !this.mSystemReady) {
            return false;
        }
        return powerGroup.sleepLocked(j, i2, i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void setWakefulnessLocked(int i, int i2, long j, int i3, int i4, int i5, java.lang.String str, java.lang.String str2) {
        this.mPowerGroups.get(i).setWakefulnessLocked(i2, j, i3, i4, i5, str, str2);
        this.mInjector.invalidateIsInteractiveCaches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateGlobalWakefulnessLocked(long j, int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
        int globalWakefulnessLocked = getGlobalWakefulnessLocked();
        if (globalWakefulnessLocked == recalculateGlobalWakefulnessLocked) {
            return;
        }
        boolean z = true;
        switch (recalculateGlobalWakefulnessLocked) {
            case 0:
                android.util.Slog.i(TAG, "Sleeping (uid " + i2 + ")...");
                if (globalWakefulnessLocked != 3) {
                    this.mLastGlobalSleepTime = j;
                    this.mLastGlobalSleepReason = i;
                }
                str3 = "reallyGoToSleep";
                break;
            case 1:
                android.util.Slog.i(TAG, "Waking up from " + android.os.PowerManagerInternal.wakefulnessToString(globalWakefulnessLocked) + " (uid=" + i2 + ", reason=" + android.os.PowerManager.wakeReasonToString(i) + ", details=" + str2 + ")...");
                this.mLastGlobalWakeTime = j;
                this.mLastGlobalWakeReason = i;
                this.mLastGlobalWakeTimeRealtime = this.mClock.elapsedRealtime();
                str3 = "wakeUp";
                break;
            case 2:
                android.util.Slog.i(TAG, "Nap time (uid " + i2 + ")...");
                str3 = "nap";
                break;
            case 3:
                android.util.Slog.i(TAG, "Going to sleep due to " + android.os.PowerManager.sleepReasonToString(i) + " (uid " + i2 + ", screenOffTimeout=" + this.mScreenOffTimeoutSetting + ", activityTimeoutWM=" + this.mUserActivityTimeoutOverrideFromWindowManager + ", maxDimRatio=" + this.mMaximumScreenDimRatioConfig + ", maxDimDur=" + this.mMaximumScreenDimDurationConfig + ")...");
                this.mLastGlobalSleepTime = j;
                this.mLastGlobalSleepReason = i;
                this.mLastGlobalSleepTimeRealtime = this.mClock.elapsedRealtime();
                this.mDozeStartInProgress = true;
                str3 = "goToSleep";
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unexpected wakefulness: " + recalculateGlobalWakefulnessLocked);
        }
        android.os.Trace.traceBegin(131072L, str3);
        try {
            this.mInjector.invalidateIsInteractiveCaches();
            this.mWakefulnessRaw = recalculateGlobalWakefulnessLocked;
            this.mWakefulnessChanging = true;
            this.mDirty |= 2;
            boolean z2 = this.mDozeStartInProgress;
            if (recalculateGlobalWakefulnessLocked != 3) {
                z = false;
            }
            this.mDozeStartInProgress = z2 & z;
            if (this.mNotifier != null) {
                this.mNotifier.onGlobalWakefulnessChangeStarted(recalculateGlobalWakefulnessLocked, i, j);
            }
            this.mAttentionDetector.onWakefulnessChangeStarted(recalculateGlobalWakefulnessLocked);
            switch (recalculateGlobalWakefulnessLocked) {
                case 0:
                case 3:
                    if (android.os.PowerManagerInternal.isInteractive(globalWakefulnessLocked)) {
                        int size = this.mWakeLocks.size();
                        int i4 = 0;
                        for (int i5 = 0; i5 < size; i5++) {
                            switch (this.mWakeLocks.get(i5).mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) {
                                case 6:
                                case 10:
                                case 26:
                                    i4++;
                                    break;
                            }
                        }
                        com.android.server.EventLogTags.writePowerSleepRequested(i4);
                        break;
                    } else {
                        break;
                    }
                    break;
                case 1:
                    this.mNotifier.onWakeUp(i, str2, i2, str, i3);
                    if (sQuiescent) {
                        this.mDirty |= 4096;
                        break;
                    }
                    break;
            }
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getGlobalWakefulnessLocked() {
        return this.mWakefulnessRaw;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getWakefulnessLocked(int i) {
        return this.mPowerGroups.get(i).getWakefulnessLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int recalculateGlobalWakefulnessLocked() {
        int i = 0;
        for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
            int wakefulnessLocked = this.mPowerGroups.valueAt(i2).getWakefulnessLocked();
            if (wakefulnessLocked == 1) {
                return 1;
            }
            if (wakefulnessLocked == 2 && (i == 0 || i == 3)) {
                i = 2;
            } else if (wakefulnessLocked == 3 && i == 0) {
                i = 3;
            }
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onPowerGroupEventLocked(int i, com.android.server.power.PowerGroup powerGroup) {
        int i2;
        this.mWakefulnessChanging = true;
        this.mDirty |= 2;
        int groupId = powerGroup.getGroupId();
        if (i == 1) {
            this.mPowerGroups.delete(groupId);
        }
        int globalWakefulnessLocked = getGlobalWakefulnessLocked();
        int recalculateGlobalWakefulnessLocked = recalculateGlobalWakefulnessLocked();
        if (i == 0 && recalculateGlobalWakefulnessLocked == 1) {
            userActivityNoUpdateLocked(powerGroup, this.mClock.uptimeMillis(), 0, 0, 1000);
            this.mNotifier.onGroupWakefulnessChangeStarted(groupId, powerGroup.getWakefulnessLocked(), 10, this.mClock.uptimeMillis());
        } else if (i == 1) {
            this.mNotifier.onGroupRemoved(groupId);
        }
        if (globalWakefulnessLocked != recalculateGlobalWakefulnessLocked) {
            int i3 = 11;
            switch (recalculateGlobalWakefulnessLocked) {
                case 1:
                    if (i == 0) {
                        i3 = 10;
                    }
                    i2 = i3;
                    break;
                case 2:
                default:
                    i2 = 0;
                    break;
                case 3:
                    if (i != 1) {
                        i3 = 12;
                    }
                    i2 = i3;
                    break;
            }
            updateGlobalWakefulnessLocked(this.mClock.uptimeMillis(), i2, 1000, 1000, this.mContext.getOpPackageName(), "groupId: " + groupId);
        }
        this.mDirty |= 65536;
        updatePowerStateLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logSleepTimeoutRecapturedLocked() {
        long uptimeMillis = this.mOverriddenTimeout - this.mClock.uptimeMillis();
        if (uptimeMillis >= 0) {
            com.android.server.EventLogTags.writePowerSoftSleepRequested(uptimeMillis);
            this.mOverriddenTimeout = -1L;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void finishWakefulnessChangeIfNeededLocked() {
        if (this.mWakefulnessChanging && areAllPowerGroupsReadyLocked()) {
            if (getGlobalWakefulnessLocked() == 3 && (this.mWakeLockSummary & 64) == 0) {
                return;
            }
            this.mDozeStartInProgress = false;
            if (getGlobalWakefulnessLocked() == 3 || getGlobalWakefulnessLocked() == 0) {
                logSleepTimeoutRecapturedLocked();
            }
            this.mWakefulnessChanging = false;
            this.mNotifier.onWakefulnessChangeFinished();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean areAllPowerGroupsReadyLocked() {
        int size = this.mPowerGroups.size();
        for (int i = 0; i < size; i++) {
            if (!this.mPowerGroups.valueAt(i).isReadyLocked()) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updatePowerStateLocked() {
        int i;
        if (!this.mSystemReady || this.mDirty == 0 || this.mUpdatePowerStateInProgress) {
            return;
        }
        if (!java.lang.Thread.holdsLock(this.mLock)) {
            android.util.Slog.wtf(TAG, "Power manager lock was not held when calling updatePowerStateLocked");
        }
        android.os.Trace.traceBegin(131072L, "updatePowerState");
        this.mUpdatePowerStateInProgress = true;
        try {
            updateIsPoweredLocked(this.mDirty);
            updateStayOnLocked(this.mDirty);
            updateScreenBrightnessBoostLocked(this.mDirty);
            long uptimeMillis = this.mClock.uptimeMillis();
            int i2 = 0;
            do {
                i = this.mDirty;
                i2 |= i;
                this.mDirty = 0;
                updateWakeLockSummaryLocked(i);
                updateUserActivitySummaryLocked(uptimeMillis, i);
                updateAttentiveStateLocked(uptimeMillis, i);
            } while (updateWakefulnessLocked(i));
            updateProfilesLocked(uptimeMillis);
            updateDreamLocked(i2, updatePowerGroupsLocked(i2));
            finishWakefulnessChangeIfNeededLocked();
            updateSuspendBlockerLocked();
        } finally {
            android.os.Trace.traceEnd(131072L);
            this.mUpdatePowerStateInProgress = false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateProfilesLocked(long j) {
        int size = this.mProfilePowerState.size();
        for (int i = 0; i < size; i++) {
            com.android.server.power.PowerManagerService.ProfilePowerState valueAt = this.mProfilePowerState.valueAt(i);
            if (isProfileBeingKeptAwakeLocked(valueAt, j)) {
                valueAt.mLockingNotified = false;
            } else if (!valueAt.mLockingNotified) {
                valueAt.mLockingNotified = true;
                this.mNotifier.onProfileTimeout(valueAt.mUserId);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isProfileBeingKeptAwakeLocked(com.android.server.power.PowerManagerService.ProfilePowerState profilePowerState, long j) {
        return profilePowerState.mLastUserActivityTime + profilePowerState.mScreenOffTimeout > j || (profilePowerState.mWakeLockSummary & 32) != 0 || (this.mProximityPositive && (profilePowerState.mWakeLockSummary & 16) != 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateIsPoweredLocked(int i) {
        if ((i & 256) != 0) {
            boolean z = this.mIsPowered;
            int i2 = this.mPlugType;
            this.mIsPowered = this.mBatteryManagerInternal.isPowered(15);
            this.mPlugType = this.mBatteryManagerInternal.getPlugType();
            int i3 = this.mBatteryLevel;
            this.mBatteryLevel = this.mBatteryManagerInternal.getBatteryLevel();
            this.mBatteryLevelLow = this.mBatteryManagerInternal.getBatteryLevelLow();
            if (!(this.mBatteryManagerInternal.getBatteryHealth() == 3) && i3 > 0 && getGlobalWakefulnessLocked() == 2) {
                this.mDreamsBatteryLevelDrain += i3 - this.mBatteryLevel;
            }
            if (z != this.mIsPowered || i2 != this.mPlugType) {
                this.mDirty |= 64;
                boolean update = this.mWirelessChargerDetector.update(this.mIsPowered, this.mPlugType);
                long uptimeMillis = this.mClock.uptimeMillis();
                if (shouldWakeUpWhenPluggedOrUnpluggedLocked(z, i2, update)) {
                    wakePowerGroupLocked(this.mPowerGroups.get(0), uptimeMillis, 3, "android.server.power:PLUGGED:" + this.mIsPowered, 1000, this.mContext.getOpPackageName(), 1000);
                }
                userActivityNoUpdateLocked(this.mPowerGroups.get(0), uptimeMillis, 0, 0, 1000);
                if (this.mBootCompleted) {
                    if (this.mIsPowered && !android.os.BatteryManager.isPlugWired(i2) && android.os.BatteryManager.isPlugWired(this.mPlugType)) {
                        this.mNotifier.onWiredChargingStarted(this.mUserId);
                    } else if (z && !this.mIsPowered) {
                        if (i2 == 4) {
                            this.mNotifier.onWirelessChargingInterrupted(this.mUserId);
                        } else {
                            this.mNotifier.onWiredChargingDisconnected(this.mUserId);
                        }
                    } else if (update) {
                        this.mNotifier.onWirelessChargingStarted(this.mBatteryLevel, this.mUserId);
                    }
                }
            }
            if (this.mBatterySaverSupported) {
                this.mBatterySaverStateMachine.setBatteryStatus(this.mIsPowered, this.mBatteryLevel, this.mBatteryLevelLow);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldWakeUpWhenPluggedOrUnpluggedLocked(boolean z, int i, boolean z2) {
        if (!this.mWakeUpWhenPluggedOrUnpluggedSetting) {
            return false;
        }
        if (this.mKeepDreamingWhenUnplugging && getGlobalWakefulnessLocked() == 2 && z && !this.mIsPowered) {
            return false;
        }
        if (z && !this.mIsPowered && i == 4) {
            return false;
        }
        if (!z && this.mIsPowered && this.mPlugType == 4 && !z2) {
            return false;
        }
        if (this.mIsPowered && getGlobalWakefulnessLocked() == 2) {
            return false;
        }
        if (!this.mTheaterModeEnabled || this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig) {
            return (this.mAlwaysOnEnabled && getGlobalWakefulnessLocked() == 3) ? false : true;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateStayOnLocked(int i) {
        if ((i & 288) != 0) {
            boolean z = this.mStayOn;
            if (this.mStayOnWhilePluggedInSetting != 0 && !isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
                this.mStayOn = this.mBatteryManagerInternal.isPowered(this.mStayOnWhilePluggedInSetting);
            } else {
                this.mStayOn = false;
            }
            if (this.mStayOn != z) {
                this.mDirty |= 128;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateWakeLockSummaryLocked(int i) {
        if ((i & 65539) != 0) {
            this.mWakeLockSummary = 0;
            int size = this.mProfilePowerState.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mProfilePowerState.valueAt(i2).mWakeLockSummary = 0;
            }
            for (int i3 = 0; i3 < this.mPowerGroups.size(); i3++) {
                this.mPowerGroups.valueAt(i3).setWakeLockSummaryLocked(0);
            }
            int size2 = this.mWakeLocks.size();
            int i4 = 0;
            for (int i5 = 0; i5 < size2; i5++) {
                com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(i5);
                java.lang.Integer powerGroupId = wakeLock.getPowerGroupId();
                if (powerGroupId != null && (powerGroupId.intValue() == -1 || this.mPowerGroups.contains(powerGroupId.intValue()))) {
                    com.android.server.power.PowerGroup powerGroup = this.mPowerGroups.get(powerGroupId.intValue());
                    int wakeLockSummaryFlags = getWakeLockSummaryFlags(wakeLock);
                    this.mWakeLockSummary |= wakeLockSummaryFlags;
                    if (powerGroupId.intValue() != -1) {
                        powerGroup.setWakeLockSummaryLocked(powerGroup.getWakeLockSummaryLocked() | wakeLockSummaryFlags);
                    } else {
                        i4 |= wakeLockSummaryFlags;
                    }
                    for (int i6 = 0; i6 < size; i6++) {
                        com.android.server.power.PowerManagerService.ProfilePowerState valueAt = this.mProfilePowerState.valueAt(i6);
                        if (wakeLockAffectsUser(wakeLock, valueAt.mUserId)) {
                            valueAt.mWakeLockSummary |= wakeLockSummaryFlags;
                        }
                    }
                }
            }
            for (int i7 = 0; i7 < this.mPowerGroups.size(); i7++) {
                com.android.server.power.PowerGroup valueAt2 = this.mPowerGroups.valueAt(i7);
                valueAt2.setWakeLockSummaryLocked(adjustWakeLockSummary(valueAt2.getWakefulnessLocked(), valueAt2.getWakeLockSummaryLocked() | i4));
            }
            this.mWakeLockSummary = adjustWakeLockSummary(getGlobalWakefulnessLocked(), this.mWakeLockSummary);
            for (int i8 = 0; i8 < size; i8++) {
                com.android.server.power.PowerManagerService.ProfilePowerState valueAt3 = this.mProfilePowerState.valueAt(i8);
                valueAt3.mWakeLockSummary = adjustWakeLockSummary(getGlobalWakefulnessLocked(), valueAt3.mWakeLockSummary);
            }
        }
    }

    private static int adjustWakeLockSummary(int i, int i2) {
        if (i != 3) {
            i2 &= -193;
        }
        if (i == 0 || (i2 & 64) != 0) {
            i2 &= -15;
            if (i == 0) {
                i2 &= -17;
            }
        }
        if ((i2 & 6) != 0) {
            if (i == 1) {
                i2 |= 33;
            } else if (i == 2) {
                i2 |= 1;
            }
        }
        if ((i2 & 128) != 0) {
            return i2 | 1;
        }
        return i2;
    }

    private int getWakeLockSummaryFlags(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        if (wakeLock.mDisabled) {
            return 0;
        }
        switch (wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) {
            case 1:
                break;
            case 6:
                break;
            case 10:
                break;
            case 26:
                break;
            case 32:
                break;
            case 64:
                break;
            case 128:
                break;
        }
        return 0;
    }

    private boolean wakeLockAffectsUser(com.android.server.power.PowerManagerService.WakeLock wakeLock, int i) {
        if (wakeLock.mWorkSource != null) {
            for (int i2 = 0; i2 < wakeLock.mWorkSource.size(); i2++) {
                if (i == android.os.UserHandle.getUserId(wakeLock.mWorkSource.getUid(i2))) {
                    return true;
                }
            }
            java.util.List workChains = wakeLock.mWorkSource.getWorkChains();
            if (workChains != null) {
                for (int i3 = 0; i3 < workChains.size(); i3++) {
                    if (i == android.os.UserHandle.getUserId(((android.os.WorkSource.WorkChain) workChains.get(i3)).getAttributionUid())) {
                        return true;
                    }
                }
            }
        }
        return i == android.os.UserHandle.getUserId(wakeLock.mOwnerUid);
    }

    void checkForLongWakeLocks() {
        synchronized (this.mLock) {
            try {
                long uptimeMillis = this.mClock.uptimeMillis();
                this.mNotifyLongDispatched = uptimeMillis;
                long j = uptimeMillis - 60000;
                int size = this.mWakeLocks.size();
                long j2 = Long.MAX_VALUE;
                for (int i = 0; i < size; i++) {
                    com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(i);
                    if ((wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) == 1 && wakeLock.mNotifiedAcquired && !wakeLock.mNotifiedLong) {
                        if (wakeLock.mAcquireTime >= j) {
                            long j3 = wakeLock.mAcquireTime + 60000;
                            if (j3 < j2) {
                                j2 = j3;
                            }
                        } else {
                            notifyWakeLockLongStartedLocked(wakeLock);
                        }
                    }
                }
                this.mNotifyLongScheduled = 0L;
                this.mHandler.removeMessages(4);
                if (j2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    this.mNotifyLongNextCheck = j2;
                    enqueueNotifyLongMsgLocked(j2);
                } else {
                    this.mNotifyLongNextCheck = 0L;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x0195, code lost:
    
        if (r15.isPolicyDimLocked() != false) goto L104;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020b  */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateUserActivitySummaryLocked(long j, int i) {
        long j2;
        long j3;
        long j4;
        int i2;
        com.android.server.power.PowerGroup powerGroup;
        int i3;
        long j5;
        int i4;
        int i5;
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        float f;
        float f2;
        long lastButtonActivityTimeLocked;
        if ((i & 81959) == 0) {
            return;
        }
        this.mHandler.removeMessages(1);
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
        long screenOffTimeoutLocked = getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
        long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
        long screenOffTimeoutWithFaceDownLocked = getScreenOffTimeoutWithFaceDownLocked(screenOffTimeoutLocked, screenDimDurationLocked);
        boolean z = this.mUserInactiveOverrideFromWindowManager;
        long j11 = -1;
        int i6 = 0;
        boolean z2 = false;
        while (i6 < this.mPowerGroups.size()) {
            com.android.server.power.PowerGroup valueAt = this.mPowerGroups.valueAt(i6);
            int wakefulnessLocked = valueAt.getWakefulnessLocked();
            if (wakefulnessLocked == 0) {
                j2 = screenOffTimeoutWithFaceDownLocked;
                j3 = sleepTimeoutLocked;
                j4 = screenDimDurationLocked;
                i2 = i6;
                powerGroup = valueAt;
                i3 = 0;
            } else {
                j2 = screenOffTimeoutWithFaceDownLocked;
                long lastUserActivityTimeLocked = valueAt.getLastUserActivityTimeLocked();
                j3 = sleepTimeoutLocked;
                long lastUserActivityTimeNoChangeLightsLocked = valueAt.getLastUserActivityTimeNoChangeLightsLocked();
                int i7 = 2;
                if (lastUserActivityTimeLocked < valueAt.getLastWakeTimeLocked()) {
                    j5 = screenDimDurationLocked;
                    i2 = i6;
                    i4 = 1;
                    i5 = 0;
                    j6 = 0;
                } else {
                    j6 = lastUserActivityTimeLocked + j2;
                    long j12 = j6 - screenDimDurationLocked;
                    i2 = i6;
                    if (j < j12) {
                        if (wakefulnessLocked != 1) {
                            j5 = screenDimDurationLocked;
                            j6 = j12;
                            i4 = 1;
                            i5 = 1;
                        } else {
                            if (this.mButtonsLight == null) {
                                j5 = screenDimDurationLocked;
                            } else {
                                if (!this.mForceNavbar) {
                                    if (isValidBrightness(this.mButtonBrightnessOverrideFromWindowManager)) {
                                        if (this.mButtonBrightnessOverrideFromWindowManager > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                                            f2 = this.mButtonBrightnessOverrideFromWindowManager;
                                            if (!this.mButtonLightOnKeypressOnly) {
                                                valueAt.setLastButtonActivityTimeLocked(lastUserActivityTimeLocked);
                                            }
                                            j5 = screenDimDurationLocked;
                                            lastButtonActivityTimeLocked = this.mButtonTimeout + valueAt.getLastButtonActivityTimeLocked();
                                            if (this.mButtonTimeout == 0 && j > lastButtonActivityTimeLocked) {
                                                this.mButtonsLight.setBrightness(-1.0f);
                                                valueAt.setButtonOnLocked(false);
                                            } else if (this.mProximityPositive && (!this.mButtonLightOnKeypressOnly || valueAt.getButtonPressedLocked())) {
                                                this.mButtonsLight.setBrightness(f2);
                                                valueAt.setButtonPressedLocked(false);
                                                if (f2 != -1.0f && this.mButtonTimeout != 0) {
                                                    valueAt.setButtonOnLocked(true);
                                                    if (this.mButtonTimeout + j < j11) {
                                                        j12 = this.mButtonTimeout + j;
                                                    }
                                                }
                                            } else if (this.mButtonLightOnKeypressOnly && lastButtonActivityTimeLocked < j11 && valueAt.getButtonOnLocked()) {
                                                j12 = lastButtonActivityTimeLocked;
                                            }
                                        }
                                    } else if (isValidButtonBrightness(this.mButtonBrightness)) {
                                        f2 = this.mButtonBrightness;
                                        if (!this.mButtonLightOnKeypressOnly) {
                                        }
                                        j5 = screenDimDurationLocked;
                                        lastButtonActivityTimeLocked = this.mButtonTimeout + valueAt.getLastButtonActivityTimeLocked();
                                        if (this.mButtonTimeout == 0) {
                                        }
                                        if (this.mProximityPositive) {
                                        }
                                        if (this.mButtonLightOnKeypressOnly) {
                                            j12 = lastButtonActivityTimeLocked;
                                        }
                                    }
                                }
                                f2 = -1.0f;
                                if (!this.mButtonLightOnKeypressOnly) {
                                }
                                j5 = screenDimDurationLocked;
                                lastButtonActivityTimeLocked = this.mButtonTimeout + valueAt.getLastButtonActivityTimeLocked();
                                if (this.mButtonTimeout == 0) {
                                }
                                if (this.mProximityPositive) {
                                }
                                if (this.mButtonLightOnKeypressOnly) {
                                }
                            }
                            if (this.mKeyboardLight != null) {
                                if (!isValidBrightness(this.mButtonBrightnessOverrideFromWindowManager)) {
                                    if (isValidKeyboardBrightness(this.mKeyboardBrightness)) {
                                        f = this.mKeyboardBrightness;
                                        this.mKeyboardLight.setBrightness(this.mKeyboardVisible ? f : -1.0f);
                                    }
                                    f = -1.0f;
                                    this.mKeyboardLight.setBrightness(this.mKeyboardVisible ? f : -1.0f);
                                } else {
                                    if (this.mButtonBrightnessOverrideFromWindowManager > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                                        f = this.mButtonBrightnessOverrideFromWindowManager;
                                        this.mKeyboardLight.setBrightness(this.mKeyboardVisible ? f : -1.0f);
                                    }
                                    f = -1.0f;
                                    this.mKeyboardLight.setBrightness(this.mKeyboardVisible ? f : -1.0f);
                                }
                            }
                            j6 = j12;
                            i4 = 1;
                            i5 = 1;
                        }
                    } else {
                        j5 = screenDimDurationLocked;
                        if (j >= j6) {
                            i4 = 1;
                            i5 = 0;
                        } else {
                            i4 = 1;
                            if (wakefulnessLocked == 1) {
                                if (this.mButtonsLight != null) {
                                    this.mButtonsLight.setBrightness(-1.0f);
                                    valueAt.setButtonOnLocked(false);
                                }
                                if (this.mKeyboardLight != null) {
                                    this.mKeyboardLight.setBrightness(-1.0f);
                                }
                            }
                            i5 = 2;
                        }
                    }
                }
                if (i5 != 0 || lastUserActivityTimeNoChangeLightsLocked < valueAt.getLastWakeTimeLocked()) {
                    i7 = i5;
                    j7 = j6;
                } else {
                    j7 = lastUserActivityTimeNoChangeLightsLocked + j2;
                    if (j < j7) {
                        if (valueAt.isPolicyBrightLocked()) {
                            i7 = i4;
                        }
                    }
                    i7 = i5;
                }
                if (i7 != 0) {
                    i3 = i7;
                } else if (j3 >= 0) {
                    long max = java.lang.Math.max(lastUserActivityTimeLocked, lastUserActivityTimeNoChangeLightsLocked);
                    if (max < valueAt.getLastWakeTimeLocked()) {
                        j10 = j7;
                    } else {
                        j10 = max + j3;
                        if (j < j10) {
                            i7 = 4;
                        }
                    }
                    j7 = j10;
                    i3 = i7;
                } else {
                    i3 = 4;
                    j7 = -1;
                }
                if (i3 == 4 || !z) {
                    powerGroup = valueAt;
                } else {
                    if ((i3 & 3) != 0) {
                        powerGroup = valueAt;
                        if (this.mOverriddenTimeout == -1) {
                            this.mOverriddenTimeout = j7;
                        }
                    } else {
                        powerGroup = valueAt;
                    }
                    i3 = 4;
                    j7 = -1;
                }
                if ((i3 & 1) == 0) {
                    j4 = j5;
                } else if ((powerGroup.getWakeLockSummaryLocked() & 32) != 0) {
                    j4 = j5;
                } else {
                    j4 = j5;
                    j8 = this.mAttentionDetector.updateUserActivity(j7, j4);
                    if (isAttentiveTimeoutExpired(powerGroup, j)) {
                        j9 = j8;
                    } else {
                        i3 = 0;
                        j9 = -1;
                    }
                    z2 |= i3 == 0;
                    if (j11 != -1) {
                        j11 = j9;
                    } else if (j9 != -1) {
                        j11 = java.lang.Math.min(j11, j9);
                    }
                }
                j8 = j7;
                if (isAttentiveTimeoutExpired(powerGroup, j)) {
                }
                z2 |= i3 == 0;
                if (j11 != -1) {
                }
            }
            powerGroup.setUserActivitySummaryLocked(i3);
            i6 = i2 + 1;
            screenDimDurationLocked = j4;
            screenOffTimeoutWithFaceDownLocked = j2;
            sleepTimeoutLocked = j3;
        }
        long nextProfileTimeoutLocked = getNextProfileTimeoutLocked(j);
        if (nextProfileTimeoutLocked > 0) {
            j11 = java.lang.Math.min(j11, nextProfileTimeoutLocked);
        }
        if (z2 && j11 >= 0) {
            scheduleUserInactivityTimeout(j11);
        }
    }

    private void scheduleUserInactivityTimeout(long j) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(1);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(obtainMessage, j);
    }

    private void scheduleAttentiveTimeout(long j) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(5);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessageAtTime(obtainMessage, j);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getNextProfileTimeoutLocked(long j) {
        int size = this.mProfilePowerState.size();
        long j2 = -1;
        for (int i = 0; i < size; i++) {
            com.android.server.power.PowerManagerService.ProfilePowerState valueAt = this.mProfilePowerState.valueAt(i);
            long j3 = valueAt.mLastUserActivityTime + valueAt.mScreenOffTimeout;
            if (j3 > j && (j2 == -1 || j3 < j2)) {
                j2 = j3;
            }
        }
        return j2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateAttentiveStateLocked(long j, int i) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        long lastUserActivityTimeLocked = this.mPowerGroups.get(0).getLastUserActivityTimeLocked() + attentiveTimeoutLocked;
        long j2 = lastUserActivityTimeLocked - this.mAttentiveWarningDurationConfig;
        boolean maybeHideInattentiveSleepWarningLocked = maybeHideInattentiveSleepWarningLocked(j, j2);
        if (attentiveTimeoutLocked >= 0) {
            if (maybeHideInattentiveSleepWarningLocked || (i & 19122) != 0) {
                this.mHandler.removeMessages(5);
                if (getGlobalWakefulnessLocked() == 0 || isBeingKeptFromInattentiveSleepLocked()) {
                    return;
                }
                if (j < j2) {
                    lastUserActivityTimeLocked = j2;
                } else if (j >= lastUserActivityTimeLocked) {
                    lastUserActivityTimeLocked = -1;
                } else {
                    this.mInattentiveSleepWarningOverlayController.show();
                }
                if (lastUserActivityTimeLocked >= 0) {
                    scheduleAttentiveTimeout(lastUserActivityTimeLocked);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean maybeHideInattentiveSleepWarningLocked(long j, long j2) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        if (!this.mInattentiveSleepWarningOverlayController.isShown()) {
            return false;
        }
        if (getGlobalWakefulnessLocked() == 0) {
            this.mInattentiveSleepWarningOverlayController.dismiss(false);
            return true;
        }
        if (attentiveTimeoutLocked >= 0 && !isBeingKeptFromInattentiveSleepLocked() && j >= j2) {
            return false;
        }
        this.mInattentiveSleepWarningOverlayController.dismiss(true);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isAttentiveTimeoutExpired(com.android.server.power.PowerGroup powerGroup, long j) {
        long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
        return powerGroup.getGroupId() == 0 && attentiveTimeoutLocked >= 0 && j >= powerGroup.getLastUserActivityTimeLocked() + attentiveTimeoutLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserActivityTimeout() {
        synchronized (this.mLock) {
            this.mDirty |= 4;
            updatePowerStateLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAttentiveTimeout() {
        synchronized (this.mLock) {
            this.mDirty |= 16384;
            updatePowerStateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getAttentiveTimeoutLocked() {
        long j = this.mAttentiveTimeoutSetting;
        if (j <= 0) {
            return -1L;
        }
        return java.lang.Math.max(j, this.mMinimumScreenOffTimeoutConfig);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getSleepTimeoutLocked(long j) {
        long j2 = this.mSleepTimeoutSetting;
        if (j2 <= 0) {
            return -1L;
        }
        if (j >= 0) {
            j2 = java.lang.Math.min(j2, j);
        }
        return java.lang.Math.max(j2, this.mMinimumScreenOffTimeoutConfig);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getScreenOffTimeoutLocked(long j, long j2) {
        long j3 = this.mScreenOffTimeoutSetting;
        if (isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked()) {
            j3 = java.lang.Math.min(j3, this.mMaximumScreenOffTimeoutFromDeviceAdmin);
        }
        if (this.mUserActivityTimeoutOverrideFromWindowManager >= 0) {
            j3 = java.lang.Math.min(j3, this.mUserActivityTimeoutOverrideFromWindowManager);
        }
        if (j >= 0) {
            j3 = java.lang.Math.min(j3, j);
        }
        if (j2 >= 0) {
            j3 = java.lang.Math.min(j3, j2);
        }
        return java.lang.Math.max(j3, this.mMinimumScreenOffTimeoutConfig);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getScreenDimDurationLocked(long j) {
        return java.lang.Math.min(this.mMaximumScreenDimDurationConfig, (long) (j * this.mMaximumScreenDimRatioConfig));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getScreenOffTimeoutWithFaceDownLocked(long j, long j2) {
        if (this.mIsFaceDown) {
            return java.lang.Math.min(j2, j);
        }
        return j;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updateWakefulnessLocked(int i) {
        if ((i & 20151) == 0) {
            return false;
        }
        long uptimeMillis = this.mClock.uptimeMillis();
        boolean z = false;
        for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
            com.android.server.power.PowerGroup valueAt = this.mPowerGroups.valueAt(i2);
            if (valueAt.getWakefulnessLocked() == 1 && isItBedTimeYetLocked(valueAt)) {
                if (isAttentiveTimeoutExpired(valueAt, uptimeMillis)) {
                    z = sleepPowerGroupLocked(valueAt, uptimeMillis, 9, 1000);
                } else if (shouldNapAtBedTimeLocked()) {
                    z = dreamPowerGroupLocked(valueAt, uptimeMillis, 1000, false);
                } else {
                    z = dozePowerGroupLocked(valueAt, uptimeMillis, 2, 1000);
                }
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldNapAtBedTimeLocked() {
        return this.mDreamsActivateOnSleepSetting || (this.mDreamsActivateOnDockSetting && this.mDockState != 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isItBedTimeYetLocked(com.android.server.power.PowerGroup powerGroup) {
        if (!this.mBootCompleted) {
            return false;
        }
        if (isAttentiveTimeoutExpired(powerGroup, this.mClock.uptimeMillis())) {
            return !isBeingKeptFromInattentiveSleepLocked();
        }
        return !isBeingKeptAwakeLocked(powerGroup);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isBeingKeptAwakeLocked(com.android.server.power.PowerGroup powerGroup) {
        return this.mStayOn || this.mProximityPositive || (powerGroup.getWakeLockSummaryLocked() & 32) != 0 || (powerGroup.getUserActivitySummaryLocked() & 3) != 0 || this.mScreenBrightnessBoostInProgress;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isBeingKeptFromInattentiveSleepLocked() {
        return this.mStayOn || this.mScreenBrightnessBoostInProgress || this.mProximityPositive || !this.mBootCompleted;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateDreamLocked(int i, boolean z) {
        if (((i & 17407) != 0 || z) && areAllPowerGroupsReadyLocked()) {
            scheduleSandmanLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void scheduleSandmanLocked() {
        if (!this.mSandmanScheduled) {
            this.mSandmanScheduled = true;
            for (int i = 0; i < this.mPowerGroups.size(); i++) {
                com.android.server.power.PowerGroup valueAt = this.mPowerGroups.valueAt(i);
                if (valueAt.supportsSandmanLocked()) {
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(2);
                    obtainMessage.arg1 = valueAt.getGroupId();
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageAtTime(obtainMessage, this.mClock.uptimeMillis());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSandman(int i) {
        boolean z;
        boolean z2;
        synchronized (this.mLock) {
            try {
                this.mSandmanScheduled = false;
                if (this.mPowerGroups.contains(i)) {
                    com.android.server.power.PowerGroup powerGroup = this.mPowerGroups.get(i);
                    int wakefulnessLocked = powerGroup.getWakefulnessLocked();
                    if (powerGroup.isSandmanSummonedLocked() && powerGroup.isReadyLocked()) {
                        z = canDreamLocked(powerGroup) || canDozeLocked(powerGroup);
                        powerGroup.setSandmanSummonedLocked(false);
                    } else {
                        z = false;
                    }
                    if (this.mDreamManager != null) {
                        if (z) {
                            this.mDreamManager.stopDream(false, "power manager request before starting dream");
                            this.mDreamManager.startDream(wakefulnessLocked == 3, "power manager request");
                        }
                        z2 = this.mDreamManager.isDreaming();
                    } else {
                        z2 = false;
                    }
                    this.mDozeStartInProgress = false;
                    synchronized (this.mLock) {
                        try {
                            if (this.mPowerGroups.contains(i)) {
                                if (z && z2) {
                                    this.mDreamsBatteryLevelDrain = 0;
                                    if (wakefulnessLocked == 3) {
                                        android.util.Slog.i(TAG, "Dozing...");
                                    } else {
                                        android.util.Slog.i(TAG, "Dreaming...");
                                    }
                                }
                                com.android.server.power.PowerGroup powerGroup2 = this.mPowerGroups.get(i);
                                if (!powerGroup2.isSandmanSummonedLocked() && powerGroup2.getWakefulnessLocked() == wakefulnessLocked) {
                                    long uptimeMillis = this.mClock.uptimeMillis();
                                    if (wakefulnessLocked == 2) {
                                        if (z2 && canDreamLocked(powerGroup2)) {
                                            if (this.mDreamsBatteryLevelDrainCutoffConfig < 0 || this.mDreamsBatteryLevelDrain <= this.mDreamsBatteryLevelDrainCutoffConfig || isBeingKeptAwakeLocked(powerGroup2)) {
                                                return;
                                            }
                                            android.util.Slog.i(TAG, "Stopping dream because the battery appears to be draining faster than it is charging.  Battery level drained while dreaming: " + this.mDreamsBatteryLevelDrain + "%.  Battery level now: " + this.mBatteryLevel + "%.");
                                        }
                                        if (isItBedTimeYetLocked(powerGroup2)) {
                                            if (isAttentiveTimeoutExpired(powerGroup2, uptimeMillis)) {
                                                sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                            } else {
                                                dozePowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                            }
                                        } else {
                                            wakePowerGroupLocked(powerGroup2, uptimeMillis, 13, "android.server.power:DREAM_FINISHED", 1000, this.mContext.getOpPackageName(), 1000);
                                        }
                                    } else if (wakefulnessLocked == 3) {
                                        if (z2) {
                                            return;
                                        } else {
                                            sleepPowerGroupLocked(powerGroup2, uptimeMillis, 2, 1000);
                                        }
                                    }
                                    if (z2) {
                                        this.mDreamManager.stopDream(false, "power manager request");
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDreamSuppressionChangedLocked(boolean z) {
        if (!this.mDreamsDisabledByAmbientModeSuppressionConfig) {
            return;
        }
        if (!z && this.mIsPowered && this.mDreamsSupportedConfig && this.mDreamsEnabledSetting && shouldNapAtBedTimeLocked() && isItBedTimeYetLocked(this.mPowerGroups.get(0))) {
            napInternal(android.os.SystemClock.uptimeMillis(), 1000, true);
        } else if (z) {
            this.mDirty |= 32;
            updatePowerStateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean canDreamLocked(com.android.server.power.PowerGroup powerGroup) {
        boolean z = this.mDreamsDisabledByAmbientModeSuppressionConfig && this.mAmbientDisplaySuppressionController.isSuppressed();
        if (!this.mBootCompleted || z || getGlobalWakefulnessLocked() != 2 || !this.mDreamsSupportedConfig || !this.mDreamsEnabledSetting || !powerGroup.isBrightOrDimLocked() || (powerGroup.getUserActivitySummaryLocked() & 7) == 0) {
            return false;
        }
        if (isBeingKeptAwakeLocked(powerGroup)) {
            return true;
        }
        if (!this.mIsPowered && !this.mDreamsEnabledOnBatteryConfig) {
            return false;
        }
        if (this.mIsPowered || this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig < 0 || this.mBatteryLevel >= this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig) {
            return !this.mIsPowered || this.mDreamsBatteryLevelMinimumWhenPoweredConfig < 0 || this.mBatteryLevel >= this.mDreamsBatteryLevelMinimumWhenPoweredConfig;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean canDozeLocked(com.android.server.power.PowerGroup powerGroup) {
        return powerGroup.supportsSandmanLocked() && powerGroup.getWakefulnessLocked() == 3;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updatePowerGroupsLocked(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        float f;
        android.os.PowerSaveState build;
        boolean areAllPowerGroupsReadyLocked = areAllPowerGroupsReadyLocked();
        if ((71743 & i) == 0) {
            z = areAllPowerGroupsReadyLocked;
            z2 = false;
            z3 = true;
        } else {
            if ((i & 4096) != 0) {
                if (!areAllPowerGroupsReadyLocked()) {
                    this.mDirty |= 4096;
                } else {
                    sQuiescent = false;
                }
            }
            int i2 = 0;
            while (i2 < this.mPowerGroups.size()) {
                com.android.server.power.PowerGroup valueAt = this.mPowerGroups.valueAt(i2);
                int groupId = valueAt.getGroupId();
                if (!this.mBootCompleted) {
                    f = this.mScreenBrightnessDefault;
                } else if (isValidBrightness(this.mScreenBrightnessOverrideFromWindowManager)) {
                    f = this.mScreenBrightnessOverrideFromWindowManager;
                } else {
                    f = Float.NaN;
                }
                boolean shouldUseProximitySensorLocked = shouldUseProximitySensorLocked();
                boolean shouldBoostScreenBrightness = shouldBoostScreenBrightness();
                int i3 = this.mDozeScreenStateOverrideFromDreamManager;
                float f2 = this.mDozeScreenBrightnessOverrideFromDreamManagerFloat;
                boolean z4 = this.mDrawWakeLockOverrideFromSidekick;
                if (this.mBatterySaverSupported) {
                    build = this.mBatterySaverStateMachine.getBatterySaverPolicy().getBatterySaverPolicy(7);
                } else {
                    build = new android.os.PowerSaveState.Builder().build();
                }
                boolean z5 = areAllPowerGroupsReadyLocked;
                int i4 = i2;
                boolean updateLocked = valueAt.updateLocked(f, shouldUseProximitySensorLocked, shouldBoostScreenBrightness, i3, f2, z4, build, sQuiescent, this.mDozeAfterScreenOff, this.mBootCompleted, this.mScreenBrightnessBoostInProgress, this.mRequestWaitForNegativeProximity, this.mBrightWhenDozingConfig);
                int wakefulnessLocked = valueAt.getWakefulnessLocked();
                boolean readyLocked = valueAt.setReadyLocked(updateLocked);
                boolean isPoweringOnLocked = valueAt.isPoweringOnLocked();
                if (updateLocked && readyLocked && isPoweringOnLocked) {
                    if (wakefulnessLocked == 1) {
                        valueAt.setIsPoweringOnLocked(false);
                        com.android.internal.util.LatencyTracker.getInstance(this.mContext).onActionEnd(5);
                        android.os.Trace.asyncTraceEnd(131072L, TRACE_SCREEN_ON, groupId);
                        int uptimeMillis = (int) (this.mClock.uptimeMillis() - valueAt.getLastPowerOnTimeLocked());
                        if (uptimeMillis >= 200) {
                            android.util.Slog.w(TAG, "Screen on took " + uptimeMillis + " ms");
                        }
                    }
                }
                i2 = i4 + 1;
                areAllPowerGroupsReadyLocked = z5;
            }
            z = areAllPowerGroupsReadyLocked;
            z3 = true;
            z2 = false;
            this.mRequestWaitForNegativeProximity = false;
        }
        return (!areAllPowerGroupsReadyLocked() || z) ? z2 : z3;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateScreenBrightnessBoostLocked(int i) {
        if ((i & 2048) != 0 && this.mScreenBrightnessBoostInProgress) {
            long uptimeMillis = this.mClock.uptimeMillis();
            this.mHandler.removeMessages(3);
            if (this.mLastScreenBrightnessBoostTime > this.mLastGlobalSleepTime) {
                long j = this.mLastScreenBrightnessBoostTime + 5000;
                if (j > uptimeMillis) {
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(3);
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageAtTime(obtainMessage, j);
                    return;
                }
            }
            this.mScreenBrightnessBoostInProgress = false;
            userActivityNoUpdateLocked(uptimeMillis, 0, 0, 1000);
        }
    }

    private boolean shouldBoostScreenBrightness() {
        return this.mScreenBrightnessBoostInProgress;
    }

    private static boolean isValidBrightness(float f) {
        return f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 1.0f;
    }

    private static boolean isValidButtonBrightness(float f) {
        return f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 1.0f;
    }

    private static boolean isValidKeyboardBrightness(float f) {
        return f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 1.0f;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getDesiredScreenPolicyLocked(int i) {
        return this.mPowerGroups.get(i).getDesiredScreenPolicyLocked(sQuiescent, this.mDozeAfterScreenOff, this.mBootCompleted, this.mScreenBrightnessBoostInProgress, this.mBrightWhenDozingConfig);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getDreamsBatteryLevelDrain() {
        return this.mDreamsBatteryLevelDrain;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldUseProximitySensorLocked() {
        return (this.mPowerGroups.get(0).getWakeLockSummaryLocked() & 16) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateSuspendBlockerLocked() {
        boolean z = (this.mWakeLockSummary & 1) != 0;
        boolean needSuspendBlockerLocked = needSuspendBlockerLocked();
        boolean z2 = !needSuspendBlockerLocked;
        boolean z3 = false;
        for (int i = 0; i < this.mPowerGroups.size() && !z3; i++) {
            z3 = this.mPowerGroups.valueAt(i).isBrightOrDimLocked();
        }
        if (!z2 && this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
            setHalAutoSuspendModeLocked(false);
        }
        if (!this.mBootCompleted && !this.mHoldingBootingSuspendBlocker) {
            this.mBootingSuspendBlocker.acquire();
            this.mHoldingBootingSuspendBlocker = true;
        }
        if (z && !this.mHoldingWakeLockSuspendBlocker) {
            this.mWakeLockSuspendBlocker.acquire();
            this.mHoldingWakeLockSuspendBlocker = true;
        }
        if (needSuspendBlockerLocked && !this.mHoldingDisplaySuspendBlocker) {
            this.mDisplaySuspendBlocker.acquire(HOLDING_DISPLAY_SUSPEND_BLOCKER);
            this.mHoldingDisplaySuspendBlocker = true;
        }
        if (this.mDecoupleHalInteractiveModeFromDisplayConfig && (z3 || areAllPowerGroupsReadyLocked())) {
            setHalInteractiveModeLocked(z3);
        }
        if (this.mBootCompleted && this.mHoldingBootingSuspendBlocker) {
            this.mBootingSuspendBlocker.release();
            this.mHoldingBootingSuspendBlocker = false;
        }
        if (!z && this.mHoldingWakeLockSuspendBlocker) {
            this.mWakeLockSuspendBlocker.release();
            this.mHoldingWakeLockSuspendBlocker = false;
        }
        if (!needSuspendBlockerLocked && this.mHoldingDisplaySuspendBlocker) {
            this.mDisplaySuspendBlocker.release(HOLDING_DISPLAY_SUSPEND_BLOCKER);
            this.mHoldingDisplaySuspendBlocker = false;
        }
        if (z2 && this.mDecoupleHalAutoSuspendModeFromDisplayConfig) {
            setHalAutoSuspendModeLocked(true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean needSuspendBlockerLocked() {
        if (!areAllPowerGroupsReadyLocked() || this.mScreenBrightnessBoostInProgress) {
            return true;
        }
        if (getGlobalWakefulnessLocked() == 3 && this.mDozeStartInProgress) {
            return true;
        }
        for (int i = 0; i < this.mPowerGroups.size(); i++) {
            if (this.mPowerGroups.valueAt(i).needSuspendBlockerLocked(this.mProximityPositive, this.mSuspendWhenScreenOffDueToProximityConfig)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setHalAutoSuspendModeLocked(boolean z) {
        if (z != this.mHalAutoSuspendModeEnabled) {
            this.mHalAutoSuspendModeEnabled = z;
            android.os.Trace.traceBegin(131072L, "setHalAutoSuspend(" + z + ")");
            try {
                this.mNativeWrapper.nativeSetAutoSuspend(z);
            } finally {
                android.os.Trace.traceEnd(131072L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setHalInteractiveModeLocked(boolean z) {
        if (z != this.mHalInteractiveModeEnabled) {
            this.mHalInteractiveModeEnabled = z;
            android.os.Trace.traceBegin(131072L, "setHalInteractive(" + z + ")");
            try {
                this.mNativeWrapper.nativeSetPowerMode(7, z);
            } finally {
                android.os.Trace.traceEnd(131072L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isGloballyInteractiveInternal() {
        boolean isInteractive;
        synchronized (this.mLock) {
            isInteractive = android.os.PowerManagerInternal.isInteractive(getGlobalWakefulnessLocked());
        }
        return isInteractive;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInteractiveInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                if (displayInfo == null) {
                    android.util.Slog.w(TAG, "Did not find DisplayInfo for displayId " + i);
                    return false;
                }
                if (!displayInfo.hasAccess(i2)) {
                    throw new java.lang.SecurityException("uid " + i2 + " does not have access to display " + i);
                }
                com.android.server.power.PowerGroup powerGroup = this.mPowerGroups.get(displayInfo.displayGroupId);
                if (powerGroup == null) {
                    android.util.Slog.w(TAG, "Did not find PowerGroup for displayId " + i);
                    return false;
                }
                return android.os.PowerManagerInternal.isInteractive(powerGroup.getWakefulnessLocked());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLowPowerModeInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mIsPowered || !this.mBatterySaverSupported) {
                    return false;
                }
                this.mBatterySaverStateMachine.setBatterySaverEnabledManually(z);
                return true;
            } finally {
            }
        }
    }

    boolean isDeviceIdleModeInternal() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDeviceIdleMode;
        }
        return z;
    }

    boolean isLightDeviceIdleModeInternal() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mLightDeviceIdleMode;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handleBatteryStateChangedLocked() {
        this.mDirty |= 256;
        updatePowerStateLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdownOrRebootInternal(final int i, final boolean z, @android.annotation.Nullable final java.lang.String str, boolean z2, final boolean z3) {
        if ("userspace".equals(str)) {
            if (!android.os.PowerManager.isRebootingUserspaceSupportedImpl()) {
                throw new java.lang.UnsupportedOperationException("Attempted userspace reboot on a device that doesn't support it");
            }
            com.android.server.UserspaceRebootLogger.noteUserspaceRebootWasRequested();
        }
        if (this.mHandler == null || !this.mSystemReady) {
            if (com.android.server.RescueParty.isAttemptingFactoryReset()) {
                lowLevelReboot(str);
            } else {
                throw new java.lang.IllegalStateException("Too early to call shutdown() or reboot()");
            }
        }
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.power.PowerManagerService.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    try {
                        if (i == 2) {
                            com.android.server.power.ShutdownThread.rebootSafeMode(com.android.server.power.PowerManagerService.this.getUiContext(), z);
                        } else if (i == 1) {
                            if (z3) {
                                com.android.server.power.ShutdownThread.rebootCustom(com.android.server.power.PowerManagerService.this.getUiContext(), str, z);
                            } else {
                                com.android.server.power.ShutdownThread.reboot(com.android.server.power.PowerManagerService.this.getUiContext(), str, z);
                            }
                        } else {
                            com.android.server.power.ShutdownThread.shutdown(com.android.server.power.PowerManagerService.this.getUiContext(), str, z);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        android.os.Message obtain = android.os.Message.obtain(com.android.server.UiThread.getHandler(), runnable);
        obtain.setAsynchronous(true);
        com.android.server.UiThread.getHandler().sendMessage(obtain);
        if (z2) {
            synchronized (runnable) {
                while (true) {
                    try {
                        runnable.wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void crashInternal(final java.lang.String str) {
        java.lang.Thread thread = new java.lang.Thread("PowerManagerService.crash()") { // from class: com.android.server.power.PowerManagerService.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                throw new java.lang.RuntimeException(str);
            }
        };
        try {
            thread.start();
            thread.join();
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    void setStayOnSettingInternal(int i) {
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "stay_on_while_plugged_in", i);
    }

    void setMaximumScreenOffTimeoutFromDeviceAdminInternal(int i, long j) {
        if (i < 0) {
            android.util.Slog.wtf(TAG, "Attempt to set screen off timeout for invalid user: " + i);
            return;
        }
        synchronized (this.mLock) {
            try {
                if (i == 0) {
                    this.mMaximumScreenOffTimeoutFromDeviceAdmin = j;
                } else if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME || j == 0) {
                    this.mProfilePowerState.delete(i);
                } else {
                    com.android.server.power.PowerManagerService.ProfilePowerState profilePowerState = this.mProfilePowerState.get(i);
                    if (profilePowerState != null) {
                        profilePowerState.mScreenOffTimeout = j;
                    } else {
                        this.mProfilePowerState.put(i, new com.android.server.power.PowerManagerService.ProfilePowerState(i, j, this.mClock.uptimeMillis()));
                        this.mDirty |= 1;
                    }
                }
                this.mDirty |= 32;
                updatePowerStateLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean setDeviceIdleModeInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mDeviceIdleMode == z) {
                    return false;
                }
                this.mDeviceIdleMode = z;
                updateWakeLockDisabledStatesLocked();
                setPowerModeInternal(8, this.mDeviceIdleMode || this.mLightDeviceIdleMode);
                if (z) {
                    com.android.server.EventLogTags.writeDeviceIdleOnPhase("power");
                } else {
                    com.android.server.EventLogTags.writeDeviceIdleOffPhase("power");
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean setLightDeviceIdleModeInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mLightDeviceIdleMode == z) {
                    return false;
                }
                this.mLightDeviceIdleMode = z;
                if (!this.mDeviceIdleMode) {
                    com.android.server.deviceidle.Flags.disableWakelocksInLightIdle();
                }
                setPowerModeInternal(8, this.mDeviceIdleMode || this.mLightDeviceIdleMode);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDeviceIdleWhitelistInternal(int[] iArr) {
        synchronized (this.mLock) {
            try {
                this.mDeviceIdleWhitelist = iArr;
                if (doesIdleStateBlockWakeLocksLocked()) {
                    updateWakeLockDisabledStatesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDeviceIdleTempWhitelistInternal(int[] iArr) {
        synchronized (this.mLock) {
            try {
                this.mDeviceIdleTempWhitelist = iArr;
                if (doesIdleStateBlockWakeLocksLocked()) {
                    updateWakeLockDisabledStatesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setLowPowerStandbyAllowlistInternal(int[] iArr) {
        synchronized (this.mLock) {
            try {
                this.mLowPowerStandbyAllowlist = iArr;
                if (this.mLowPowerStandbyActive) {
                    updateWakeLockDisabledStatesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setLowPowerStandbyActiveInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mLowPowerStandbyActive != z) {
                    this.mLowPowerStandbyActive = z;
                    updateWakeLockDisabledStatesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void startUidChangesInternal() {
        synchronized (this.mLock) {
            this.mUidsChanging = true;
        }
    }

    void finishUidChangesInternal() {
        synchronized (this.mLock) {
            try {
                this.mUidsChanging = false;
                if (this.mUidsChanged) {
                    updateWakeLockDisabledStatesLocked();
                    this.mUidsChanged = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleUidStateChangeLocked() {
        if (this.mUidsChanging) {
            this.mUidsChanged = true;
        } else {
            updateWakeLockDisabledStatesLocked();
        }
    }

    void updateUidProcStateInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.PowerManagerService.UidState uidState = this.mUidState.get(i);
                if (uidState == null) {
                    uidState = new com.android.server.power.PowerManagerService.UidState(i);
                    this.mUidState.put(i, uidState);
                }
                boolean z = uidState.mProcState <= 11;
                uidState.mProcState = i2;
                if (uidState.mNumWakeLocks > 0) {
                    if (doesIdleStateBlockWakeLocksLocked() || this.mLowPowerStandbyActive) {
                        handleUidStateChangeLocked();
                    } else if (!uidState.mActive) {
                        if (z != (i2 <= 11)) {
                            handleUidStateChangeLocked();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidGoneInternal(int i) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mUidState.indexOfKey(i);
                if (indexOfKey >= 0) {
                    com.android.server.power.PowerManagerService.UidState valueAt = this.mUidState.valueAt(indexOfKey);
                    valueAt.mProcState = 20;
                    valueAt.mActive = false;
                    this.mUidState.removeAt(indexOfKey);
                    if ((doesIdleStateBlockWakeLocksLocked() || this.mLowPowerStandbyActive) && valueAt.mNumWakeLocks > 0) {
                        handleUidStateChangeLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidActiveInternal(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.PowerManagerService.UidState uidState = this.mUidState.get(i);
                if (uidState == null) {
                    uidState = new com.android.server.power.PowerManagerService.UidState(i);
                    uidState.mProcState = 19;
                    this.mUidState.put(i, uidState);
                }
                uidState.mActive = true;
                if (uidState.mNumWakeLocks > 0) {
                    handleUidStateChangeLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void uidIdleInternal(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.PowerManagerService.UidState uidState = this.mUidState.get(i);
                if (uidState != null) {
                    uidState.mActive = false;
                    if (uidState.mNumWakeLocks > 0) {
                        handleUidStateChangeLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean doesIdleStateBlockWakeLocksLocked() {
        if (this.mDeviceIdleMode) {
            return true;
        }
        if (this.mLightDeviceIdleMode) {
            com.android.server.deviceidle.Flags.disableWakelocksInLightIdle();
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateWakeLockDisabledStatesLocked() {
        int size = this.mWakeLocks.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            com.android.server.power.PowerManagerService.WakeLock wakeLock = this.mWakeLocks.get(i);
            if (((wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) == 1 || isScreenLock(wakeLock)) && setWakeLockDisabledStateLocked(wakeLock)) {
                if (wakeLock.mDisabled) {
                    notifyWakeLockReleasedLocked(wakeLock);
                } else {
                    notifyWakeLockAcquiredLocked(wakeLock);
                }
                z = true;
            }
        }
        if (z) {
            this.mDirty |= 1;
            updatePowerStateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean setWakeLockDisabledStateLocked(com.android.server.power.PowerManagerService.WakeLock wakeLock) {
        boolean z = false;
        if ((wakeLock.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) == 1) {
            int appId = android.os.UserHandle.getAppId(wakeLock.mOwnerUid);
            if (appId >= 10000) {
                if (this.mConstants.NO_CACHED_WAKE_LOCKS && (this.mForceSuspendActive || (!wakeLock.mUidState.mActive && wakeLock.mUidState.mProcState != 20 && wakeLock.mUidState.mProcState > 11))) {
                    z = true;
                }
                if (doesIdleStateBlockWakeLocksLocked()) {
                    com.android.server.power.PowerManagerService.UidState uidState = wakeLock.mUidState;
                    if (java.util.Arrays.binarySearch(this.mDeviceIdleWhitelist, appId) < 0 && java.util.Arrays.binarySearch(this.mDeviceIdleTempWhitelist, appId) < 0 && uidState.mProcState != 20 && uidState.mProcState > 5) {
                        z = true;
                    }
                }
                if (this.mLowPowerStandbyActive) {
                    com.android.server.power.PowerManagerService.UidState uidState2 = wakeLock.mUidState;
                    if (java.util.Arrays.binarySearch(this.mLowPowerStandbyAllowlist, wakeLock.mOwnerUid) < 0 && uidState2.mProcState != 20 && uidState2.mProcState > 3) {
                        z = true;
                    }
                }
            }
            return wakeLock.setDisabled(z);
        }
        if (!this.mDisableScreenWakeLocksWhileCached || !isScreenLock(wakeLock)) {
            return false;
        }
        int appId2 = android.os.UserHandle.getAppId(wakeLock.mOwnerUid);
        com.android.server.power.PowerManagerService.UidState uidState3 = wakeLock.mUidState;
        if (this.mConstants.NO_CACHED_WAKE_LOCKS && appId2 >= 10000 && !uidState3.mActive && uidState3.mProcState != 20 && uidState3.mProcState >= 12) {
            z = true;
        }
        return wakeLock.setDisabled(z);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked() {
        return this.mMaximumScreenOffTimeoutFromDeviceAdmin >= 0 && this.mMaximumScreenOffTimeoutFromDeviceAdmin < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAttentionLightInternal(boolean z, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mSystemReady) {
                    com.android.server.lights.LogicalLight logicalLight = this.mAttentionLight;
                    if (logicalLight != null) {
                        logicalLight.setFlashing(i, 2, z ? 3 : 0, 0);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDozeAfterScreenOffInternal(boolean z) {
        synchronized (this.mLock) {
            this.mDozeAfterScreenOff = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void boostScreenBrightnessInternal(long j, int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mSystemReady || getGlobalWakefulnessLocked() == 0 || j < this.mLastScreenBrightnessBoostTime) {
                    return;
                }
                android.util.Slog.i(TAG, "Brightness boost activated (uid " + i + ")...");
                this.mLastScreenBrightnessBoostTime = j;
                this.mScreenBrightnessBoostInProgress = true;
                this.mDirty = this.mDirty | 2048;
                userActivityNoUpdateLocked(this.mPowerGroups.get(0), j, 0, 0, i);
                updatePowerStateLocked();
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isScreenBrightnessBoostedInternal() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mScreenBrightnessBoostInProgress;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScreenBrightnessBoostTimeout() {
        synchronized (this.mLock) {
            this.mDirty |= 2048;
            updatePowerStateLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setButtonBrightnessOverrideFromWindowManagerInternal(float f) {
        synchronized (this.mLock) {
            try {
                if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(this.mButtonBrightnessOverrideFromWindowManager, f)) {
                    this.mButtonBrightnessOverrideFromWindowManager = f;
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenBrightnessOverrideFromWindowManagerInternal(float f) {
        synchronized (this.mLock) {
            try {
                if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(this.mScreenBrightnessOverrideFromWindowManager, f)) {
                    this.mScreenBrightnessOverrideFromWindowManager = f;
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUserInactiveOverrideFromWindowManagerInternal() {
        synchronized (this.mLock) {
            this.mUserInactiveOverrideFromWindowManager = true;
            this.mDirty |= 4;
            updatePowerStateLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUserActivityTimeoutOverrideFromWindowManagerInternal(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mUserActivityTimeoutOverrideFromWindowManager != j) {
                    this.mUserActivityTimeoutOverrideFromWindowManager = j;
                    com.android.server.EventLogTags.writeUserActivityTimeoutOverride(j);
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDozeOverrideFromDreamManagerInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mDozeScreenStateOverrideFromDreamManager != i || this.mDozeScreenBrightnessOverrideFromDreamManager != i2) {
                    this.mDozeScreenStateOverrideFromDreamManager = i;
                    this.mDozeScreenBrightnessOverrideFromDreamManager = i2;
                    this.mDozeScreenBrightnessOverrideFromDreamManagerFloat = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mDozeScreenBrightnessOverrideFromDreamManager);
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDrawWakeLockOverrideFromSidekickInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mDrawWakeLockOverrideFromSidekick != z) {
                    this.mDrawWakeLockOverrideFromSidekick = z;
                    this.mDirty |= 32;
                    updatePowerStateLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPowerBoostInternal(int i, int i2) {
        this.mNativeWrapper.nativeSetPowerBoost(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setPowerModeInternal(int i, boolean z) {
        if (i == 5 && z && this.mBatterySaverStateMachine != null && this.mBatterySaverStateMachine.getBatterySaverController().isLaunchBoostDisabled()) {
            return false;
        }
        return this.mNativeWrapper.nativeSetPowerMode(i, z);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean wasDeviceIdleForInternal(long j) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPowerGroups.get(0).getLastUserActivityTimeLocked() + j < this.mClock.uptimeMillis();
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserActivity() {
        synchronized (this.mLock) {
            this.mPowerGroups.get(0).setLastUserActivityTimeLocked(this.mClock.uptimeMillis(), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean forceSuspendInternal(int i) {
        boolean nativeForceSuspend;
        synchronized (this.mLock) {
            try {
                try {
                    this.mForceSuspendActive = true;
                    for (int i2 = 0; i2 < this.mPowerGroups.size(); i2++) {
                        sleepPowerGroupLocked(this.mPowerGroups.valueAt(i2), this.mClock.uptimeMillis(), 8, i);
                    }
                    updateWakeLockDisabledStatesLocked();
                    android.util.Slog.i(TAG, "Force-Suspending (uid " + i + ")...");
                    nativeForceSuspend = this.mNativeWrapper.nativeForceSuspend();
                    if (!nativeForceSuspend) {
                        android.util.Slog.i(TAG, "Force-Suspending failed in native.");
                    }
                    this.mForceSuspendActive = false;
                    updateWakeLockDisabledStatesLocked();
                } catch (java.lang.Throwable th) {
                    this.mForceSuspendActive = false;
                    updateWakeLockDisabledStatesLocked();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        return nativeForceSuspend;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addPowerGroupsForNonDefaultDisplayGroupLocked() {
        android.util.IntArray displayGroupIds = this.mDisplayManagerInternal.getDisplayGroupIds();
        if (displayGroupIds == null) {
            return;
        }
        for (int i = 0; i < displayGroupIds.size(); i++) {
            int i2 = displayGroupIds.get(i);
            if (i2 != 0) {
                if (this.mPowerGroups.contains(i2)) {
                    android.util.Slog.e(TAG, "Tried to add already existing group:" + i2);
                } else {
                    this.mPowerGroups.append(i2, new com.android.server.power.PowerGroup(i2, this.mPowerGroupWakefulnessChangeListener, this.mNotifier, this.mDisplayManagerInternal, 1, false, false, this.mClock.uptimeMillis()));
                }
            }
        }
        this.mDirty |= 65536;
    }

    public static void lowLevelShutdown(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        android.os.SystemProperties.set("sys.powerctl", "shutdown," + str);
    }

    public static void lowLevelReboot(java.lang.String str) {
        if (str == null) {
            str = "";
        }
        if (str.equals("quiescent")) {
            sQuiescent = true;
            str = "";
        } else if (str.endsWith(",quiescent")) {
            sQuiescent = true;
            str = str.substring(0, (str.length() - "quiescent".length()) - 1);
        }
        if (str.equals("recovery") || str.equals("recovery-update")) {
            str = "recovery";
        }
        if (sQuiescent) {
            if (!"".equals(str)) {
                str = str + ",";
            }
            str = str + "quiescent";
        }
        android.os.SystemProperties.set("sys.powerctl", "reboot," + str);
        try {
            java.lang.Thread.sleep(20000L);
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
        }
        android.util.Slog.wtf(TAG, "Unexpected return from lowLevelReboot!");
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.NeverCompile
    public void dumpInternal(java.io.PrintWriter printWriter) {
        com.android.server.power.WirelessChargerDetector wirelessChargerDetector;
        printWriter.println("POWER MANAGER (dumpsys power)\n");
        synchronized (this.mLock) {
            try {
                printWriter.println("Power Manager State:");
                this.mConstants.dump(printWriter);
                printWriter.println("  mDirty=0x" + java.lang.Integer.toHexString(this.mDirty));
                printWriter.println("  mWakefulness=" + android.os.PowerManagerInternal.wakefulnessToString(getGlobalWakefulnessLocked()));
                printWriter.println("  mWakefulnessChanging=" + this.mWakefulnessChanging);
                printWriter.println("  mIsPowered=" + this.mIsPowered);
                printWriter.println("  mPlugType=" + this.mPlugType);
                printWriter.println("  mBatteryLevel=" + this.mBatteryLevel);
                printWriter.println("  mDreamsBatteryLevelDrain=" + this.mDreamsBatteryLevelDrain);
                printWriter.println("  mDockState=" + this.mDockState);
                printWriter.println("  mStayOn=" + this.mStayOn);
                printWriter.println("  mProximityPositive=" + this.mProximityPositive);
                printWriter.println("  mBootCompleted=" + this.mBootCompleted);
                printWriter.println("  mSystemReady=" + this.mSystemReady);
                synchronized (this.mEnhancedDischargeTimeLock) {
                    printWriter.println("  mEnhancedDischargeTimeElapsed=" + this.mEnhancedDischargeTimeElapsed);
                    printWriter.println("  mLastEnhancedDischargeTimeUpdatedElapsed=" + this.mLastEnhancedDischargeTimeUpdatedElapsed);
                    printWriter.println("  mEnhancedDischargePredictionIsPersonalized=" + this.mEnhancedDischargePredictionIsPersonalized);
                }
                printWriter.println("  mHalAutoSuspendModeEnabled=" + this.mHalAutoSuspendModeEnabled);
                printWriter.println("  mHalInteractiveModeEnabled=" + this.mHalInteractiveModeEnabled);
                printWriter.println("  mWakeLockSummary=0x" + java.lang.Integer.toHexString(this.mWakeLockSummary));
                printWriter.print("  mNotifyLongScheduled=");
                if (this.mNotifyLongScheduled == 0) {
                    printWriter.print("(none)");
                } else {
                    android.util.TimeUtils.formatDuration(this.mNotifyLongScheduled, this.mClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print("  mNotifyLongDispatched=");
                if (this.mNotifyLongDispatched == 0) {
                    printWriter.print("(none)");
                } else {
                    android.util.TimeUtils.formatDuration(this.mNotifyLongDispatched, this.mClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print("  mNotifyLongNextCheck=");
                if (this.mNotifyLongNextCheck == 0) {
                    printWriter.print("(none)");
                } else {
                    android.util.TimeUtils.formatDuration(this.mNotifyLongNextCheck, this.mClock.uptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.println("  mRequestWaitForNegativeProximity=" + this.mRequestWaitForNegativeProximity);
                printWriter.println("  mInterceptedPowerKeyForProximity=" + this.mInterceptedPowerKeyForProximity);
                printWriter.println("  mSandmanScheduled=" + this.mSandmanScheduled);
                printWriter.println("  mBatteryLevelLow=" + this.mBatteryLevelLow);
                printWriter.println("  mLightDeviceIdleMode=" + this.mLightDeviceIdleMode);
                printWriter.println("  mDeviceIdleMode=" + this.mDeviceIdleMode);
                printWriter.println("  mDeviceIdleWhitelist=" + java.util.Arrays.toString(this.mDeviceIdleWhitelist));
                printWriter.println("  mDeviceIdleTempWhitelist=" + java.util.Arrays.toString(this.mDeviceIdleTempWhitelist));
                printWriter.println("  mLowPowerStandbyActive=" + this.mLowPowerStandbyActive);
                printWriter.println("  mLastWakeTime=" + android.util.TimeUtils.formatUptime(this.mLastGlobalWakeTime));
                printWriter.println("  mLastSleepTime=" + android.util.TimeUtils.formatUptime(this.mLastGlobalSleepTime));
                printWriter.println("  mLastSleepReason=" + android.os.PowerManager.sleepReasonToString(this.mLastGlobalSleepReason));
                printWriter.println("  mLastGlobalWakeTimeRealtime=" + android.util.TimeUtils.formatUptime(this.mLastGlobalWakeTimeRealtime));
                printWriter.println("  mLastGlobalSleepTimeRealtime=" + android.util.TimeUtils.formatUptime(this.mLastGlobalSleepTimeRealtime));
                printWriter.println("  mLastInteractivePowerHintTime=" + android.util.TimeUtils.formatUptime(this.mLastInteractivePowerHintTime));
                printWriter.println("  mLastScreenBrightnessBoostTime=" + android.util.TimeUtils.formatUptime(this.mLastScreenBrightnessBoostTime));
                printWriter.println("  mScreenBrightnessBoostInProgress=" + this.mScreenBrightnessBoostInProgress);
                printWriter.println("  mHoldingWakeLockSuspendBlocker=" + this.mHoldingWakeLockSuspendBlocker);
                printWriter.println("  mHoldingDisplaySuspendBlocker=" + this.mHoldingDisplaySuspendBlocker);
                printWriter.println("  mLastFlipTime=" + this.mLastFlipTime);
                printWriter.println("  mIsFaceDown=" + this.mIsFaceDown);
                printWriter.println();
                printWriter.println("Settings and Configuration:");
                printWriter.println("  mDecoupleHalAutoSuspendModeFromDisplayConfig=" + this.mDecoupleHalAutoSuspendModeFromDisplayConfig);
                printWriter.println("  mDecoupleHalInteractiveModeFromDisplayConfig=" + this.mDecoupleHalInteractiveModeFromDisplayConfig);
                printWriter.println("  mWakeUpWhenPluggedOrUnpluggedConfig=" + this.mWakeUpWhenPluggedOrUnpluggedConfig);
                printWriter.println("  mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig=" + this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
                printWriter.println("  mTheaterModeEnabled=" + this.mTheaterModeEnabled);
                printWriter.println("  mKeepDreamingWhenUnplugging=" + this.mKeepDreamingWhenUnplugging);
                printWriter.println("  mSuspendWhenScreenOffDueToProximityConfig=" + this.mSuspendWhenScreenOffDueToProximityConfig);
                printWriter.println("  mDreamsSupportedConfig=" + this.mDreamsSupportedConfig);
                printWriter.println("  mDreamsEnabledByDefaultConfig=" + this.mDreamsEnabledByDefaultConfig);
                printWriter.println("  mDreamsActivatedOnSleepByDefaultConfig=" + this.mDreamsActivatedOnSleepByDefaultConfig);
                printWriter.println("  mDreamsActivatedOnDockByDefaultConfig=" + this.mDreamsActivatedOnDockByDefaultConfig);
                printWriter.println("  mDreamsEnabledOnBatteryConfig=" + this.mDreamsEnabledOnBatteryConfig);
                printWriter.println("  mDreamsBatteryLevelMinimumWhenPoweredConfig=" + this.mDreamsBatteryLevelMinimumWhenPoweredConfig);
                printWriter.println("  mDreamsBatteryLevelMinimumWhenNotPoweredConfig=" + this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
                printWriter.println("  mDreamsBatteryLevelDrainCutoffConfig=" + this.mDreamsBatteryLevelDrainCutoffConfig);
                printWriter.println("  mDreamsEnabledSetting=" + this.mDreamsEnabledSetting);
                printWriter.println("  mDreamsActivateOnSleepSetting=" + this.mDreamsActivateOnSleepSetting);
                printWriter.println("  mDreamsActivateOnDockSetting=" + this.mDreamsActivateOnDockSetting);
                printWriter.println("  mDozeAfterScreenOff=" + this.mDozeAfterScreenOff);
                printWriter.println("  mBrightWhenDozingConfig=" + this.mBrightWhenDozingConfig);
                printWriter.println("  mMinimumScreenOffTimeoutConfig=" + this.mMinimumScreenOffTimeoutConfig);
                printWriter.println("  mMaximumScreenDimDurationConfig=" + this.mMaximumScreenDimDurationConfig);
                printWriter.println("  mMaximumScreenDimRatioConfig=" + this.mMaximumScreenDimRatioConfig);
                printWriter.println("  mAttentiveTimeoutConfig=" + this.mAttentiveTimeoutConfig);
                printWriter.println("  mAttentiveTimeoutSetting=" + this.mAttentiveTimeoutSetting);
                printWriter.println("  mAttentiveWarningDurationConfig=" + this.mAttentiveWarningDurationConfig);
                printWriter.println("  mScreenOffTimeoutSetting=" + this.mScreenOffTimeoutSetting);
                printWriter.println("  mSleepTimeoutSetting=" + this.mSleepTimeoutSetting);
                printWriter.println("  mMaximumScreenOffTimeoutFromDeviceAdmin=" + this.mMaximumScreenOffTimeoutFromDeviceAdmin + " (enforced=" + isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked() + ")");
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  mStayOnWhilePluggedInSetting=");
                sb.append(this.mStayOnWhilePluggedInSetting);
                printWriter.println(sb.toString());
                printWriter.println("  mButtonTimeout=" + this.mButtonTimeout);
                printWriter.println("  mButtonBrightness=" + this.mButtonBrightness);
                printWriter.println("  mButtonBrightnessOverrideFromWindowManager=" + this.mButtonBrightnessOverrideFromWindowManager);
                printWriter.println("  mKeyboardBrightness=" + this.mKeyboardBrightness);
                printWriter.println("  mScreenBrightnessOverrideFromWindowManager=" + this.mScreenBrightnessOverrideFromWindowManager);
                printWriter.println("  mUserActivityTimeoutOverrideFromWindowManager=" + this.mUserActivityTimeoutOverrideFromWindowManager);
                printWriter.println("  mUserInactiveOverrideFromWindowManager=" + this.mUserInactiveOverrideFromWindowManager);
                printWriter.println("  mDozeScreenStateOverrideFromDreamManager=" + this.mDozeScreenStateOverrideFromDreamManager);
                printWriter.println("  mDrawWakeLockOverrideFromSidekick=" + this.mDrawWakeLockOverrideFromSidekick);
                printWriter.println("  mDozeScreenBrightnessOverrideFromDreamManager=" + this.mDozeScreenBrightnessOverrideFromDreamManager);
                printWriter.println("  mScreenBrightnessMinimum=" + this.mScreenBrightnessMinimum);
                printWriter.println("  mScreenBrightnessMaximum=" + this.mScreenBrightnessMaximum);
                printWriter.println("  mScreenBrightnessDefault=" + this.mScreenBrightnessDefault);
                printWriter.println("  mDoubleTapWakeEnabled=" + this.mDoubleTapWakeEnabled);
                printWriter.println("  mForegroundProfile=" + this.mForegroundProfile);
                printWriter.println("  mUserId=" + this.mUserId);
                long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
                long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
                long screenOffTimeoutLocked = getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
                long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
                printWriter.println();
                printWriter.println("Attentive timeout: " + attentiveTimeoutLocked + " ms");
                printWriter.println("Sleep timeout: " + sleepTimeoutLocked + " ms");
                printWriter.println("Screen off timeout: " + screenOffTimeoutLocked + " ms");
                printWriter.println("Screen dim duration: " + screenDimDurationLocked + " ms");
                printWriter.println();
                printWriter.print("UID states (changing=");
                printWriter.print(this.mUidsChanging);
                printWriter.print(" changed=");
                printWriter.print(this.mUidsChanged);
                printWriter.println("):");
                for (int i = 0; i < this.mUidState.size(); i++) {
                    com.android.server.power.PowerManagerService.UidState valueAt = this.mUidState.valueAt(i);
                    printWriter.print("  UID ");
                    android.os.UserHandle.formatUid(printWriter, this.mUidState.keyAt(i));
                    printWriter.print(": ");
                    if (valueAt.mActive) {
                        printWriter.print("  ACTIVE ");
                    } else {
                        printWriter.print("INACTIVE ");
                    }
                    printWriter.print(" count=");
                    printWriter.print(valueAt.mNumWakeLocks);
                    printWriter.print(" state=");
                    printWriter.println(valueAt.mProcState);
                }
                printWriter.println();
                printWriter.println("Looper state:");
                this.mHandler.getLooper().dump(new android.util.PrintWriterPrinter(printWriter), "  ");
                printWriter.println();
                printWriter.println("Wake Locks: size=" + this.mWakeLocks.size());
                java.util.Iterator<com.android.server.power.PowerManagerService.WakeLock> it = this.mWakeLocks.iterator();
                while (it.hasNext()) {
                    printWriter.println("  " + it.next());
                }
                printWriter.println();
                printWriter.println("Suspend Blockers: size=" + this.mSuspendBlockers.size());
                java.util.Iterator<com.android.server.power.SuspendBlocker> it2 = this.mSuspendBlockers.iterator();
                while (it2.hasNext()) {
                    printWriter.println("  " + it2.next());
                }
                printWriter.println();
                printWriter.println("Display Power: " + this.mDisplayPowerCallbacks);
                if (this.mBatterySaverSupported) {
                    this.mBatterySaverStateMachine.getBatterySaverPolicy().dump(printWriter);
                    this.mBatterySaverStateMachine.dump(printWriter);
                } else {
                    printWriter.println("Battery Saver: DISABLED");
                }
                this.mAttentionDetector.dump(printWriter);
                printWriter.println();
                int size = this.mProfilePowerState.size();
                printWriter.println("Profile power states: size=" + size);
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.power.PowerManagerService.ProfilePowerState valueAt2 = this.mProfilePowerState.valueAt(i2);
                    printWriter.print("  mUserId=");
                    printWriter.print(valueAt2.mUserId);
                    printWriter.print(" mScreenOffTimeout=");
                    printWriter.print(valueAt2.mScreenOffTimeout);
                    printWriter.print(" mWakeLockSummary=");
                    printWriter.print(valueAt2.mWakeLockSummary);
                    printWriter.print(" mLastUserActivityTime=");
                    printWriter.print(valueAt2.mLastUserActivityTime);
                    printWriter.print(" mLockingNotified=");
                    printWriter.println(valueAt2.mLockingNotified);
                }
                printWriter.println("Display Group User Activity:");
                for (int i3 = 0; i3 < this.mPowerGroups.size(); i3++) {
                    com.android.server.power.PowerGroup valueAt3 = this.mPowerGroups.valueAt(i3);
                    printWriter.println("  displayGroupId=" + valueAt3.getGroupId());
                    printWriter.println("  userActivitySummary=0x" + java.lang.Integer.toHexString(valueAt3.getUserActivitySummaryLocked()));
                    printWriter.println("  lastUserActivityTime=" + android.util.TimeUtils.formatUptime(valueAt3.getLastUserActivityTimeLocked()));
                    printWriter.println("  lastUserActivityTimeNoChangeLights=" + android.util.TimeUtils.formatUptime(valueAt3.getLastUserActivityTimeNoChangeLightsLocked()));
                    printWriter.println("  mWakeLockSummary=0x" + java.lang.Integer.toHexString(valueAt3.getWakeLockSummaryLocked()));
                }
                wirelessChargerDetector = this.mWirelessChargerDetector;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (wirelessChargerDetector != null) {
            wirelessChargerDetector.dump(printWriter);
        }
        if (this.mNotifier != null) {
            this.mNotifier.dump(printWriter);
        }
        this.mFaceDownDetector.dump(printWriter);
        this.mAmbientDisplaySuppressionController.dump(printWriter);
        this.mLowPowerStandbyController.dump(printWriter);
        this.mFeatureFlags.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpProto(java.io.FileDescriptor fileDescriptor) {
        com.android.server.power.WirelessChargerDetector wirelessChargerDetector;
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            try {
                this.mConstants.dumpProto(protoOutputStream);
                protoOutputStream.write(1120986464258L, this.mDirty);
                protoOutputStream.write(1159641169923L, getGlobalWakefulnessLocked());
                protoOutputStream.write(1133871366148L, this.mWakefulnessChanging);
                protoOutputStream.write(1133871366149L, this.mIsPowered);
                protoOutputStream.write(1159641169926L, this.mPlugType);
                protoOutputStream.write(1120986464263L, this.mBatteryLevel);
                protoOutputStream.write(1120986464313L, this.mDreamsBatteryLevelDrain);
                protoOutputStream.write(1159641169929L, this.mDockState);
                protoOutputStream.write(1133871366154L, this.mStayOn);
                protoOutputStream.write(1133871366155L, this.mProximityPositive);
                protoOutputStream.write(1133871366156L, this.mBootCompleted);
                protoOutputStream.write(1133871366157L, this.mSystemReady);
                synchronized (this.mEnhancedDischargeTimeLock) {
                    protoOutputStream.write(1112396529716L, this.mEnhancedDischargeTimeElapsed);
                    protoOutputStream.write(1112396529717L, this.mLastEnhancedDischargeTimeUpdatedElapsed);
                    protoOutputStream.write(1133871366198L, this.mEnhancedDischargePredictionIsPersonalized);
                }
                protoOutputStream.write(1133871366158L, this.mHalAutoSuspendModeEnabled);
                protoOutputStream.write(1133871366159L, this.mHalInteractiveModeEnabled);
                long start = protoOutputStream.start(1146756268048L);
                protoOutputStream.write(1133871366145L, (this.mWakeLockSummary & 1) != 0);
                protoOutputStream.write(1133871366146L, (this.mWakeLockSummary & 2) != 0);
                long j = 1133871366147L;
                protoOutputStream.write(1133871366147L, (this.mWakeLockSummary & 4) != 0);
                protoOutputStream.write(1133871366148L, (this.mWakeLockSummary & 8) != 0);
                protoOutputStream.write(1133871366149L, (this.mWakeLockSummary & 16) != 0);
                protoOutputStream.write(1133871366150L, (this.mWakeLockSummary & 32) != 0);
                protoOutputStream.write(1133871366151L, (this.mWakeLockSummary & 64) != 0);
                protoOutputStream.write(1133871366152L, (this.mWakeLockSummary & 128) != 0);
                protoOutputStream.end(start);
                protoOutputStream.write(1112396529681L, this.mNotifyLongScheduled);
                protoOutputStream.write(1112396529682L, this.mNotifyLongDispatched);
                protoOutputStream.write(1112396529683L, this.mNotifyLongNextCheck);
                int i = 0;
                while (i < this.mPowerGroups.size()) {
                    com.android.server.power.PowerGroup valueAt = this.mPowerGroups.valueAt(i);
                    long start2 = protoOutputStream.start(2246267895828L);
                    protoOutputStream.write(1120986464262L, valueAt.getGroupId());
                    long userActivitySummaryLocked = valueAt.getUserActivitySummaryLocked();
                    protoOutputStream.write(1133871366145L, (userActivitySummaryLocked & 1) != 0);
                    protoOutputStream.write(1133871366146L, (userActivitySummaryLocked & 2) != 0);
                    protoOutputStream.write(j, (userActivitySummaryLocked & 4) != 0);
                    protoOutputStream.write(1112396529668L, valueAt.getLastUserActivityTimeLocked());
                    protoOutputStream.write(1112396529669L, valueAt.getLastUserActivityTimeNoChangeLightsLocked());
                    protoOutputStream.end(start2);
                    i++;
                    j = 1133871366147L;
                }
                protoOutputStream.write(1133871366165L, this.mRequestWaitForNegativeProximity);
                protoOutputStream.write(1133871366166L, this.mSandmanScheduled);
                protoOutputStream.write(1133871366168L, this.mBatteryLevelLow);
                protoOutputStream.write(1133871366169L, this.mLightDeviceIdleMode);
                protoOutputStream.write(1133871366170L, this.mDeviceIdleMode);
                for (int i2 : this.mDeviceIdleWhitelist) {
                    protoOutputStream.write(2220498092059L, i2);
                }
                for (int i3 : this.mDeviceIdleTempWhitelist) {
                    protoOutputStream.write(2220498092060L, i3);
                }
                protoOutputStream.write(1133871366199L, this.mLowPowerStandbyActive);
                protoOutputStream.write(1112396529693L, this.mLastGlobalWakeTime);
                protoOutputStream.write(1112396529694L, this.mLastGlobalSleepTime);
                protoOutputStream.write(1112396529697L, this.mLastInteractivePowerHintTime);
                protoOutputStream.write(1112396529698L, this.mLastScreenBrightnessBoostTime);
                protoOutputStream.write(1133871366179L, this.mScreenBrightnessBoostInProgress);
                protoOutputStream.write(1133871366181L, this.mHoldingWakeLockSuspendBlocker);
                protoOutputStream.write(1133871366182L, this.mHoldingDisplaySuspendBlocker);
                long start3 = protoOutputStream.start(1146756268071L);
                protoOutputStream.write(1133871366145L, this.mDecoupleHalAutoSuspendModeFromDisplayConfig);
                protoOutputStream.write(1133871366146L, this.mDecoupleHalInteractiveModeFromDisplayConfig);
                protoOutputStream.write(1133871366147L, this.mWakeUpWhenPluggedOrUnpluggedConfig);
                protoOutputStream.write(1133871366148L, this.mWakeUpWhenPluggedOrUnpluggedInTheaterModeConfig);
                protoOutputStream.write(1133871366149L, this.mTheaterModeEnabled);
                protoOutputStream.write(1133871366150L, this.mSuspendWhenScreenOffDueToProximityConfig);
                protoOutputStream.write(1133871366151L, this.mDreamsSupportedConfig);
                protoOutputStream.write(1133871366152L, this.mDreamsEnabledByDefaultConfig);
                protoOutputStream.write(1133871366153L, this.mDreamsActivatedOnSleepByDefaultConfig);
                protoOutputStream.write(1133871366154L, this.mDreamsActivatedOnDockByDefaultConfig);
                protoOutputStream.write(1133871366155L, this.mDreamsEnabledOnBatteryConfig);
                protoOutputStream.write(1172526071820L, this.mDreamsBatteryLevelMinimumWhenPoweredConfig);
                protoOutputStream.write(1172526071821L, this.mDreamsBatteryLevelMinimumWhenNotPoweredConfig);
                protoOutputStream.write(1172526071822L, this.mDreamsBatteryLevelDrainCutoffConfig);
                protoOutputStream.write(1133871366159L, this.mDreamsEnabledSetting);
                protoOutputStream.write(1133871366160L, this.mDreamsActivateOnSleepSetting);
                protoOutputStream.write(1133871366161L, this.mDreamsActivateOnDockSetting);
                protoOutputStream.write(1133871366162L, this.mDozeAfterScreenOff);
                protoOutputStream.write(1120986464275L, this.mMinimumScreenOffTimeoutConfig);
                protoOutputStream.write(1120986464276L, this.mMaximumScreenDimDurationConfig);
                protoOutputStream.write(1108101562389L, this.mMaximumScreenDimRatioConfig);
                protoOutputStream.write(1120986464278L, this.mScreenOffTimeoutSetting);
                protoOutputStream.write(1172526071831L, this.mSleepTimeoutSetting);
                protoOutputStream.write(1172526071845L, this.mAttentiveTimeoutSetting);
                protoOutputStream.write(1172526071846L, this.mAttentiveTimeoutConfig);
                protoOutputStream.write(1172526071847L, this.mAttentiveWarningDurationConfig);
                protoOutputStream.write(1120986464280L, java.lang.Math.min(this.mMaximumScreenOffTimeoutFromDeviceAdmin, 2147483647L));
                protoOutputStream.write(1133871366169L, isMaximumScreenOffTimeoutFromDeviceAdminEnforcedLocked());
                long start4 = protoOutputStream.start(1146756268058L);
                protoOutputStream.write(1133871366145L, (this.mStayOnWhilePluggedInSetting & 1) != 0);
                protoOutputStream.write(1133871366146L, (this.mStayOnWhilePluggedInSetting & 2) != 0);
                protoOutputStream.write(1133871366147L, (this.mStayOnWhilePluggedInSetting & 4) != 0);
                protoOutputStream.write(1133871366148L, (this.mStayOnWhilePluggedInSetting & 8) != 0);
                protoOutputStream.end(start4);
                protoOutputStream.write(1172526071836L, this.mScreenBrightnessOverrideFromWindowManager);
                protoOutputStream.write(1176821039133L, this.mUserActivityTimeoutOverrideFromWindowManager);
                protoOutputStream.write(1133871366174L, this.mUserInactiveOverrideFromWindowManager);
                protoOutputStream.write(1159641169951L, this.mDozeScreenStateOverrideFromDreamManager);
                protoOutputStream.write(1133871366180L, this.mDrawWakeLockOverrideFromSidekick);
                protoOutputStream.write(1108101562400L, this.mDozeScreenBrightnessOverrideFromDreamManager);
                long start5 = protoOutputStream.start(1146756268065L);
                protoOutputStream.write(1108101562372L, this.mScreenBrightnessMinimum);
                protoOutputStream.write(1108101562373L, this.mScreenBrightnessMaximum);
                protoOutputStream.write(1108101562374L, this.mScreenBrightnessDefault);
                protoOutputStream.end(start5);
                protoOutputStream.write(1133871366178L, this.mDoubleTapWakeEnabled);
                protoOutputStream.end(start3);
                long attentiveTimeoutLocked = getAttentiveTimeoutLocked();
                long sleepTimeoutLocked = getSleepTimeoutLocked(attentiveTimeoutLocked);
                long screenOffTimeoutLocked = getScreenOffTimeoutLocked(sleepTimeoutLocked, attentiveTimeoutLocked);
                long screenDimDurationLocked = getScreenDimDurationLocked(screenOffTimeoutLocked);
                protoOutputStream.write(1172526071859L, attentiveTimeoutLocked);
                protoOutputStream.write(1172526071848L, sleepTimeoutLocked);
                protoOutputStream.write(1120986464297L, screenOffTimeoutLocked);
                protoOutputStream.write(1120986464298L, screenDimDurationLocked);
                protoOutputStream.write(1133871366187L, this.mUidsChanging);
                protoOutputStream.write(1133871366188L, this.mUidsChanged);
                for (int i4 = 0; i4 < this.mUidState.size(); i4++) {
                    com.android.server.power.PowerManagerService.UidState valueAt2 = this.mUidState.valueAt(i4);
                    long start6 = protoOutputStream.start(2246267895853L);
                    int keyAt = this.mUidState.keyAt(i4);
                    protoOutputStream.write(1120986464257L, keyAt);
                    protoOutputStream.write(1138166333442L, android.os.UserHandle.formatUid(keyAt));
                    protoOutputStream.write(1133871366147L, valueAt2.mActive);
                    protoOutputStream.write(1120986464260L, valueAt2.mNumWakeLocks);
                    protoOutputStream.write(1159641169925L, android.app.ActivityManager.processStateAmToProto(valueAt2.mProcState));
                    protoOutputStream.end(start6);
                }
                if (this.mBatterySaverSupported) {
                    this.mBatterySaverStateMachine.dumpProto(protoOutputStream, 1146756268082L);
                }
                this.mHandler.getLooper().dumpDebug(protoOutputStream, 1146756268078L);
                java.util.Iterator<com.android.server.power.PowerManagerService.WakeLock> it = this.mWakeLocks.iterator();
                while (it.hasNext()) {
                    it.next().dumpDebug(protoOutputStream, 2246267895855L);
                }
                java.util.Iterator<com.android.server.power.SuspendBlocker> it2 = this.mSuspendBlockers.iterator();
                while (it2.hasNext()) {
                    it2.next().dumpDebug(protoOutputStream, 2246267895856L);
                }
                wirelessChargerDetector = this.mWirelessChargerDetector;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (wirelessChargerDetector != null) {
            wirelessChargerDetector.dumpDebug(protoOutputStream, 1146756268081L);
        }
        this.mLowPowerStandbyController.dumpProto(protoOutputStream, 1146756268088L);
        protoOutputStream.flush();
    }

    private void incrementBootCount() {
        int i;
        synchronized (this.mLock) {
            try {
                i = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "boot_count");
            } catch (android.provider.Settings.SettingNotFoundException e) {
                i = 0;
            }
            android.provider.Settings.Global.putInt(getContext().getContentResolver(), "boot_count", i + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.WorkSource copyWorkSource(android.os.WorkSource workSource) {
        if (workSource != null) {
            return new android.os.WorkSource(workSource);
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BatteryReceiver extends android.content.BroadcastReceiver {
        BatteryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.handleBatteryStateChangedLocked();
            }
        }
    }

    private final class DreamReceiver extends android.content.BroadcastReceiver {
        private DreamReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.scheduleSandmanLocked();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class UserSwitchedReceiver extends android.content.BroadcastReceiver {
        UserSwitchedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.handleSettingsChangedLocked();
            }
        }
    }

    private final class DockReceiver extends android.content.BroadcastReceiver {
        private DockReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                    if (com.android.server.power.PowerManagerService.this.mDockState != intExtra) {
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DOCK_STATE_CHANGED, intExtra);
                        com.android.server.power.PowerManagerService.this.mDockState = intExtra;
                        com.android.server.power.PowerManagerService.this.mDirty |= 1024;
                        com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                com.android.server.power.PowerManagerService.this.handleSettingsChangedLocked();
            }
        }
    }

    private final class PowerManagerHandlerCallback implements android.os.Handler.Callback {
        private PowerManagerHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.power.PowerManagerService.this.handleUserActivityTimeout();
                    break;
                case 2:
                    com.android.server.power.PowerManagerService.this.handleSandman(message.arg1);
                    break;
                case 3:
                    com.android.server.power.PowerManagerService.this.handleScreenBrightnessBoostTimeout();
                    break;
                case 4:
                    com.android.server.power.PowerManagerService.this.checkForLongWakeLocks();
                    break;
                case 5:
                    com.android.server.power.PowerManagerService.this.handleAttentiveTimeout();
                    break;
                case 6:
                    com.android.server.power.PowerManagerService.this.cleanupProximity();
                    ((java.lang.Runnable) message.obj).run();
                    break;
            }
            return true;
        }
    }

    final class WakeLock implements android.os.IBinder.DeathRecipient {
        public long mAcquireTime;
        public android.os.IWakeLockCallback mCallback;
        public boolean mDisabled;
        public final int mDisplayId;
        public int mFlags;
        public java.lang.String mHistoryTag;
        public final android.os.IBinder mLock;
        public boolean mNotifiedAcquired;
        public boolean mNotifiedLong;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public final java.lang.String mPackageName;
        public java.lang.String mTag;
        public final com.android.server.power.PowerManagerService.UidState mUidState;
        public android.os.WorkSource mWorkSource;

        public WakeLock(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i3, int i4, com.android.server.power.PowerManagerService.UidState uidState, @android.annotation.Nullable android.os.IWakeLockCallback iWakeLockCallback) {
            this.mLock = iBinder;
            this.mDisplayId = i;
            this.mFlags = i2;
            this.mTag = str;
            this.mPackageName = str2;
            this.mWorkSource = com.android.server.power.PowerManagerService.copyWorkSource(workSource);
            this.mHistoryTag = str3;
            this.mOwnerUid = i3;
            this.mOwnerPid = i4;
            this.mUidState = uidState;
            this.mCallback = iWakeLockCallback;
            linkToDeath();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            unlinkToDeath();
            com.android.server.power.PowerManagerService.this.handleWakeLockDeath(this);
        }

        private void linkToDeath() {
            try {
                this.mLock.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalArgumentException("Wakelock.mLock is already dead.");
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void unlinkToDeath() {
            try {
                this.mLock.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Slog.wtf(com.android.server.power.PowerManagerService.TAG, "Failed to unlink Wakelock.mLock", e);
            }
        }

        public boolean setDisabled(boolean z) {
            if (this.mDisabled != z) {
                this.mDisabled = z;
                return true;
            }
            return false;
        }

        public boolean hasSameProperties(int i, java.lang.String str, android.os.WorkSource workSource, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) {
            return this.mFlags == i && this.mTag.equals(str) && hasSameWorkSource(workSource) && this.mOwnerUid == i2 && this.mOwnerPid == i3;
        }

        public void updateProperties(int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) {
            if (!this.mPackageName.equals(str2)) {
                throw new java.lang.IllegalStateException("Existing wake lock package name changed: " + this.mPackageName + " to " + str2);
            }
            if (this.mOwnerUid != i2) {
                throw new java.lang.IllegalStateException("Existing wake lock uid changed: " + this.mOwnerUid + " to " + i2);
            }
            if (this.mOwnerPid != i3) {
                throw new java.lang.IllegalStateException("Existing wake lock pid changed: " + this.mOwnerPid + " to " + i3);
            }
            this.mFlags = i;
            this.mTag = str;
            updateWorkSource(workSource);
            this.mHistoryTag = str3;
            this.mCallback = iWakeLockCallback;
        }

        public boolean hasSameWorkSource(android.os.WorkSource workSource) {
            return java.util.Objects.equals(this.mWorkSource, workSource);
        }

        public void updateWorkSource(android.os.WorkSource workSource) {
            this.mWorkSource = com.android.server.power.PowerManagerService.copyWorkSource(workSource);
        }

        public java.lang.Integer getPowerGroupId() {
            if (!com.android.server.power.PowerManagerService.this.mSystemReady || this.mDisplayId == -1) {
                return -1;
            }
            android.view.DisplayInfo displayInfo = com.android.server.power.PowerManagerService.this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
            if (displayInfo != null) {
                return java.lang.Integer.valueOf(displayInfo.displayGroupId);
            }
            return null;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(getLockLevelString());
            sb.append(" '");
            sb.append(this.mTag);
            sb.append("'");
            sb.append(getLockFlagsString());
            if (this.mDisabled) {
                sb.append(" DISABLED");
            }
            if (this.mNotifiedAcquired) {
                sb.append(" ACQ=");
                android.util.TimeUtils.formatDuration(this.mAcquireTime - com.android.server.power.PowerManagerService.this.mClock.uptimeMillis(), sb);
            }
            if (this.mNotifiedLong) {
                sb.append(" LONG");
            }
            sb.append(" (uid=");
            sb.append(this.mOwnerUid);
            if (this.mOwnerPid != 0) {
                sb.append(" pid=");
                sb.append(this.mOwnerPid);
            }
            if (this.mWorkSource != null) {
                sb.append(" ws=");
                sb.append(this.mWorkSource);
            }
            sb.append(")");
            return sb.toString();
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1159641169921L, this.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
            protoOutputStream.write(1138166333442L, this.mTag);
            long start2 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1133871366145L, (this.mFlags & 268435456) != 0);
            protoOutputStream.write(1133871366146L, (this.mFlags & 536870912) != 0);
            protoOutputStream.write(1133871366147L, (this.mFlags & Integer.MIN_VALUE) != 0);
            protoOutputStream.end(start2);
            protoOutputStream.write(1133871366148L, this.mDisabled);
            if (this.mNotifiedAcquired) {
                protoOutputStream.write(1112396529669L, this.mAcquireTime);
            }
            protoOutputStream.write(1133871366150L, this.mNotifiedLong);
            protoOutputStream.write(1120986464263L, this.mOwnerUid);
            protoOutputStream.write(1120986464264L, this.mOwnerPid);
            if (this.mWorkSource != null) {
                this.mWorkSource.dumpDebug(protoOutputStream, 1146756268041L);
            }
            protoOutputStream.end(start);
        }

        private java.lang.String getLockLevelString() {
            switch (this.mFlags & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) {
                case 1:
                    return "PARTIAL_WAKE_LOCK             ";
                case 6:
                    return "SCREEN_DIM_WAKE_LOCK          ";
                case 10:
                    return "SCREEN_BRIGHT_WAKE_LOCK       ";
                case 26:
                    return "FULL_WAKE_LOCK                ";
                case 32:
                    return "PROXIMITY_SCREEN_OFF_WAKE_LOCK";
                case 64:
                    return "DOZE_WAKE_LOCK                ";
                case 128:
                    return "DRAW_WAKE_LOCK                ";
                default:
                    return "???                           ";
            }
        }

        private java.lang.String getLockFlagsString() {
            java.lang.String str = "";
            if ((this.mFlags & 268435456) != 0) {
                str = " ACQUIRE_CAUSES_WAKEUP";
            }
            if ((this.mFlags & 536870912) != 0) {
                str = str + " ON_AFTER_RELEASE";
            }
            if ((this.mFlags & Integer.MIN_VALUE) != 0) {
                return str + " SYSTEM_WAKELOCK";
            }
            return str;
        }
    }

    private final class SuspendBlockerImpl implements com.android.server.power.SuspendBlocker {
        private static final java.lang.String UNKNOWN_ID = "unknown";
        private final java.lang.String mName;
        private final int mNameHash;
        private final android.util.ArrayMap<java.lang.String, android.util.LongArray> mOpenReferenceTimes = new android.util.ArrayMap<>();
        private int mReferenceCount;

        public SuspendBlockerImpl(java.lang.String str) {
            this.mName = str;
            this.mNameHash = this.mName.hashCode();
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                if (this.mReferenceCount != 0) {
                    android.util.Slog.wtf(com.android.server.power.PowerManagerService.TAG, "Suspend blocker \"" + this.mName + "\" was finalized without being released!");
                    this.mReferenceCount = 0;
                    com.android.server.power.PowerManagerService.this.mNativeWrapper.nativeReleaseSuspendBlocker(this.mName);
                    android.os.Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
                }
            } finally {
                super.finalize();
            }
        }

        @Override // com.android.server.power.SuspendBlocker
        public void acquire() {
            acquire("unknown");
        }

        @Override // com.android.server.power.SuspendBlocker
        public void acquire(java.lang.String str) {
            synchronized (this) {
                try {
                    recordReferenceLocked(str);
                    this.mReferenceCount++;
                    if (this.mReferenceCount == 1) {
                        android.os.Trace.asyncTraceForTrackBegin(131072L, "SuspendBlockers", this.mName, this.mNameHash);
                        com.android.server.power.PowerManagerService.this.mNativeWrapper.nativeAcquireSuspendBlocker(this.mName);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.power.SuspendBlocker
        public void release() {
            release("unknown");
        }

        @Override // com.android.server.power.SuspendBlocker
        public void release(java.lang.String str) {
            synchronized (this) {
                try {
                    removeReferenceLocked(str);
                    this.mReferenceCount--;
                    if (this.mReferenceCount == 0) {
                        com.android.server.power.PowerManagerService.this.mNativeWrapper.nativeReleaseSuspendBlocker(this.mName);
                        if (android.os.Trace.isTagEnabled(131072L)) {
                            android.os.Trace.asyncTraceForTrackEnd(131072L, "SuspendBlockers", this.mNameHash);
                        }
                    } else if (this.mReferenceCount < 0) {
                        android.util.Slog.wtf(com.android.server.power.PowerManagerService.TAG, "Suspend blocker \"" + this.mName + "\" was released without being acquired!", new java.lang.Throwable());
                        this.mReferenceCount = 0;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.lang.String toString() {
            java.lang.String sb;
            synchronized (this) {
                try {
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append(this.mName);
                    sb2.append(": ref count=");
                    sb2.append(this.mReferenceCount);
                    sb2.append(" [");
                    int size = this.mOpenReferenceTimes.size();
                    for (int i = 0; i < size; i++) {
                        java.lang.String keyAt = this.mOpenReferenceTimes.keyAt(i);
                        android.util.LongArray valueAt = this.mOpenReferenceTimes.valueAt(i);
                        if (valueAt != null && valueAt.size() != 0) {
                            if (i > 0) {
                                sb2.append(", ");
                            }
                            sb2.append(keyAt);
                            sb2.append(": (");
                            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                                if (i2 > 0) {
                                    sb2.append(", ");
                                }
                                sb2.append(com.android.server.power.PowerManagerService.DATE_FORMAT.format(new java.util.Date(valueAt.get(i2))));
                            }
                            sb2.append(")");
                        }
                    }
                    sb2.append("]");
                    sb = sb2.toString();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return sb;
        }

        @Override // com.android.server.power.SuspendBlocker
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            synchronized (this) {
                protoOutputStream.write(1138166333441L, this.mName);
                protoOutputStream.write(1120986464258L, this.mReferenceCount);
            }
            protoOutputStream.end(start);
        }

        private void recordReferenceLocked(java.lang.String str) {
            android.util.LongArray longArray = this.mOpenReferenceTimes.get(str);
            if (longArray == null) {
                longArray = new android.util.LongArray(2);
                this.mOpenReferenceTimes.put(str, longArray);
            }
            longArray.add(java.lang.System.currentTimeMillis());
        }

        private void removeReferenceLocked(java.lang.String str) {
            android.util.LongArray longArray = this.mOpenReferenceTimes.get(str);
            if (longArray != null && longArray.size() > 0) {
                longArray.remove(longArray.size() - 1);
                if (longArray.size() == 0) {
                    this.mOpenReferenceTimes.remove(str);
                }
            }
        }
    }

    static final class UidState {
        boolean mActive;
        int mNumWakeLocks;
        int mProcState;
        final int mUid;

        UidState(int i) {
            this.mUid = i;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BinderService extends android.os.IPowerManager.Stub {
        private final com.android.server.power.PowerManagerShellCommand mShellCommand;

        BinderService(android.content.Context context) {
            this.mShellCommand = new com.android.server.power.PowerManagerShellCommand(context, this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            this.mShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void acquireWakeLockWithUid(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) {
            int i4;
            if (i2 >= 0) {
                i4 = i2;
            } else {
                i4 = android.os.Binder.getCallingUid();
            }
            acquireWakeLock(iBinder, i, str, str2, new android.os.WorkSource(i4), null, i3, iWakeLockCallback);
        }

        public void setPowerBoost(int i, int i2) {
            if (!com.android.server.power.PowerManagerService.this.mSystemReady) {
                return;
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            com.android.server.power.PowerManagerService.this.setPowerBoostInternal(i, i2);
        }

        public void setPowerMode(int i, boolean z) {
            if (!com.android.server.power.PowerManagerService.this.mSystemReady) {
                return;
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            com.android.server.power.PowerManagerService.this.setPowerModeInternal(i, z);
        }

        public boolean setPowerModeChecked(int i, boolean z) {
            if (!com.android.server.power.PowerManagerService.this.mSystemReady) {
                return false;
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            return com.android.server.power.PowerManagerService.this.setPowerModeInternal(i, z);
        }

        @android.annotation.RequiresPermission(conditional = true, value = "android.permission.TURN_SCREEN_ON")
        public void acquireWakeLock(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i2, @android.annotation.Nullable android.os.IWakeLockCallback iWakeLockCallback) {
            android.os.WorkSource workSource2;
            int i3;
            int i4;
            android.os.WorkSource workSource3;
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("lock must not be null");
            }
            if (str2 == null) {
                throw new java.lang.IllegalArgumentException("packageName must not be null");
            }
            android.os.PowerManager.validateWakeLockParameters(i, str);
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            if ((i & 64) != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            }
            if (workSource != null && !workSource.isEmpty()) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", null);
                workSource2 = workSource;
            } else {
                workSource2 = null;
            }
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            if ((Integer.MIN_VALUE & i) == 0) {
                i3 = callingUid;
                i4 = callingPid;
                workSource3 = workSource2;
            } else {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                android.os.WorkSource workSource4 = new android.os.WorkSource(android.os.Binder.getCallingUid(), str2);
                if (workSource2 != null && !workSource2.isEmpty()) {
                    workSource4.add(workSource2);
                }
                i3 = android.os.Process.myUid();
                workSource3 = workSource4;
                i4 = android.os.Process.myPid();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.acquireWakeLockInternal(iBinder, i2, i, str, str2, workSource3, str3, i3, i4, iWakeLockCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void acquireWakeLockAsync(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3) {
            acquireWakeLock(iBinder, i, str, str2, workSource, str3, -1, null);
        }

        public void releaseWakeLock(android.os.IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("lock must not be null");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.releaseWakeLockInternal(iBinder, i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseWakeLockAsync(android.os.IBinder iBinder, int i) {
            releaseWakeLock(iBinder, i);
        }

        public void updateWakeLockUids(android.os.IBinder iBinder, int[] iArr) {
            android.os.WorkSource workSource;
            if (iArr == null) {
                workSource = null;
            } else {
                workSource = new android.os.WorkSource();
                for (int i : iArr) {
                    workSource.add(i);
                }
            }
            updateWakeLockWorkSource(iBinder, workSource, null);
        }

        public void updateWakeLockUidsAsync(android.os.IBinder iBinder, int[] iArr) {
            updateWakeLockUids(iBinder, iArr);
        }

        public void updateWakeLockWorkSource(android.os.IBinder iBinder, android.os.WorkSource workSource, java.lang.String str) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("lock must not be null");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            if (workSource != null && !workSource.isEmpty()) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", null);
            } else {
                workSource = null;
            }
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.updateWakeLockWorkSourceInternal(iBinder, workSource, str, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void updateWakeLockCallback(android.os.IBinder iBinder, android.os.IWakeLockCallback iWakeLockCallback) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("lock must not be null");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WAKE_LOCK", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.updateWakeLockCallbackInternal(iBinder, iWakeLockCallback, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isWakeLockLevelSupported(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isWakeLockLevelSupportedInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void userActivity(int i, long j, int i2, int i3) {
            long uptimeMillis = com.android.server.power.PowerManagerService.this.mClock.uptimeMillis();
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0 && com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.USER_ACTIVITY") != 0) {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    try {
                        if (uptimeMillis >= com.android.server.power.PowerManagerService.this.mLastWarningAboutUserActivityPermission + com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
                            com.android.server.power.PowerManagerService.this.mLastWarningAboutUserActivityPermission = uptimeMillis;
                            android.util.Slog.w(com.android.server.power.PowerManagerService.TAG, "Ignoring call to PowerManager.userActivity() because the caller does not have DEVICE_POWER or USER_ACTIVITY permission.  Please fix your app!   pid=" + android.os.Binder.getCallingPid() + " uid=" + android.os.Binder.getCallingUid());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return;
            }
            if (j > uptimeMillis) {
                android.util.Slog.wtf(com.android.server.power.PowerManagerService.TAG, "Event cannot be newer than the current time (now=" + uptimeMillis + ", eventTime=" + j + ", displayId=" + i + ", event=" + android.os.PowerManager.userActivityEventToString(i2) + ", flags=" + i3 + ")");
                return;
            }
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.userActivityInternal(i, j, i2, i3, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void wakeUp(long j, int i, java.lang.String str, java.lang.String str2) {
            wakeUp(j, i, str, str2, false);
        }

        public void wakeUpWithProximityCheck(long j, int i, java.lang.String str, java.lang.String str2) {
            wakeUp(j, i, str, str2, true);
        }

        public void wakeUp(final long j, final int i, final java.lang.String str, final java.lang.String str2, boolean z) {
            long uptimeMillis = com.android.server.power.PowerManagerService.this.mClock.uptimeMillis();
            if (j > uptimeMillis) {
                android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Event time " + j + " cannot be newer than " + uptimeMillis);
                throw new java.lang.IllegalArgumentException("event time must not be in the future");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            final int callingUid = android.os.Binder.getCallingUid();
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.power.PowerManagerService$BinderService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.PowerManagerService.BinderService.this.lambda$wakeUp$0(j, i, str, callingUid, str2);
                }
            };
            if (z) {
                com.android.server.power.PowerManagerService.this.runWithProximityCheck(runnable);
            } else {
                runnable.run();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wakeUp$0(long j, int i, java.lang.String str, int i2, java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    if (com.android.server.power.PowerManagerService.this.mBootCompleted || !com.android.server.power.PowerManagerService.sQuiescent) {
                        com.android.server.power.PowerManagerService.this.wakePowerGroupLocked((com.android.server.power.PowerGroup) com.android.server.power.PowerManagerService.this.mPowerGroups.get(0), j, i, str, i2, str2, i2);
                        return;
                    }
                    com.android.server.power.PowerManagerService.this.mDirty |= 4096;
                    com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission("android.permission.DEVICE_POWER")
        public void goToSleep(long j, int i, int i2) {
            com.android.server.power.PowerManagerService.this.goToSleepInternal(com.android.server.power.PowerManagerService.DEFAULT_DISPLAY_GROUP_IDS, j, i, i2);
        }

        @android.annotation.RequiresPermission("android.permission.DEVICE_POWER")
        public void goToSleepWithDisplayId(int i, long j, int i2, int i3) {
            android.util.IntArray wrap;
            if (i == -1) {
                wrap = com.android.server.power.PowerManagerService.this.mDisplayManagerInternal.getDisplayGroupIds();
            } else {
                android.view.DisplayInfo displayInfo = com.android.server.power.PowerManagerService.this.mDisplayManagerInternal.getDisplayInfo(i);
                com.android.internal.util.Preconditions.checkArgument(displayInfo != null, "display ID(%d) doesn't exist", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
                int i4 = displayInfo.displayGroupId;
                if (i4 == -1) {
                    throw new java.lang.IllegalArgumentException("invalid display group ID");
                }
                wrap = android.util.IntArray.wrap(new int[]{i4});
            }
            com.android.server.power.PowerManagerService.this.goToSleepInternal(wrap, j, i2, i3);
        }

        public void nap(long j) {
            long uptimeMillis = com.android.server.power.PowerManagerService.this.mClock.uptimeMillis();
            if (j > uptimeMillis) {
                android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Event time " + j + " cannot be newer than " + uptimeMillis);
                throw new java.lang.IllegalArgumentException("event time must not be in the future");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.napInternal(j, callingUid, false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public float getBrightnessConstraint(int i) {
            switch (i) {
                case 0:
                    return com.android.server.power.PowerManagerService.this.mScreenBrightnessMinimum;
                case 1:
                    return com.android.server.power.PowerManagerService.this.mScreenBrightnessMaximum;
                case 2:
                    return com.android.server.power.PowerManagerService.this.mScreenBrightnessDefault;
                case 3:
                    return com.android.server.power.PowerManagerService.this.mScreenBrightnessDim;
                case 4:
                    return com.android.server.power.PowerManagerService.this.mScreenBrightnessDoze;
                case 5:
                case 6:
                case 7:
                default:
                    return Float.NaN;
                case 8:
                    return com.android.server.power.PowerManagerService.this.mButtonBrightnessDefault;
                case 9:
                    return com.android.server.power.PowerManagerService.this.mKeyboardBrightnessDefault;
            }
        }

        public boolean isInteractive() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isGloballyInteractiveInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDisplayInteractive(int i) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isInteractiveInternal(i, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean areAutoPowerSaveModesEnabled() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mContext.getResources().getBoolean(android.R.bool.config_enableAppWidgetService);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isPowerSaveMode() {
            boolean z;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    if (com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.getBatterySaverController().isEnabled()) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.os.PowerSaveState getPowerSaveState(int i) {
            android.os.PowerSaveState build;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    build = com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.getBatterySaverPolicy().getBatterySaverPolicy(i);
                } else {
                    build = new android.os.PowerSaveState.Builder().build();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return build;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean setPowerSaveModeEnabled(boolean z) {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.setLowPowerModeInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isBatterySaverSupported() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mBatterySaverSupported;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.os.BatterySaverPolicyConfig getFullPowerSavePolicy() {
            android.os.BatterySaverPolicyConfig build;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    build = com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.getFullBatterySaverPolicy();
                } else {
                    build = new android.os.BatterySaverPolicyConfig.Builder().build();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return build;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean setFullPowerSavePolicy(@android.annotation.NonNull android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
            boolean z;
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setFullPowerSavePolicy");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    if (com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.setFullBatterySaverPolicy(batterySaverPolicyConfig)) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setDynamicPowerSaveHint(boolean z, int i) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.POWER_SAVER", "updateDynamicPowerSavings");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.ContentResolver contentResolver = com.android.server.power.PowerManagerService.this.mContext.getContentResolver();
                boolean putInt = android.provider.Settings.Global.putInt(contentResolver, "dynamic_power_savings_disable_threshold", i);
                if (putInt) {
                    putInt &= android.provider.Settings.Global.putInt(contentResolver, "dynamic_power_savings_enabled", z ? 1 : 0);
                }
                return putInt;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setAdaptivePowerSavePolicy(@android.annotation.NonNull android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
            boolean z;
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setAdaptivePowerSavePolicy");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    if (com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.setAdaptiveBatterySaverPolicy(batterySaverPolicyConfig)) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setAdaptivePowerSaveEnabled(boolean z) {
            boolean z2;
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.POWER_SAVER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setAdaptivePowerSaveEnabled");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                    if (com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.setAdaptiveBatterySaverEnabled(z)) {
                        z2 = true;
                        return z2;
                    }
                }
                z2 = false;
                return z2;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getPowerSaveModeTrigger() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.Settings.Global.getInt(com.android.server.power.PowerManagerService.this.mContext.getContentResolver(), "automatic_power_save_mode", 0);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setBatteryDischargePrediction(@android.annotation.NonNull android.os.ParcelDuration parcelDuration, boolean z) {
            long elapsedRealtime = com.android.server.power.PowerManagerService.this.mClock.elapsedRealtime();
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.BATTERY_PREDICTION") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", "setBatteryDischargePrediction");
            }
            long millis = parcelDuration.getDuration().toMillis();
            com.android.internal.util.Preconditions.checkArgumentPositive(millis, "Given time remaining is not positive: " + millis);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    if (com.android.server.power.PowerManagerService.this.mIsPowered) {
                        throw new java.lang.IllegalStateException("Discharge prediction can't be set while the device is charging");
                    }
                }
                synchronized (com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeLock) {
                    if (com.android.server.power.PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed > elapsedRealtime) {
                        return;
                    }
                    long max = java.lang.Math.max(0L, 60000 - (elapsedRealtime - com.android.server.power.PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed));
                    com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeElapsed = millis + elapsedRealtime;
                    com.android.server.power.PowerManagerService.this.mEnhancedDischargePredictionIsPersonalized = z;
                    com.android.server.power.PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed = elapsedRealtime;
                    com.android.server.power.PowerManagerService.this.mNotifier.postEnhancedDischargePredictionBroadcast(max);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @com.android.internal.annotations.GuardedBy({"PowerManagerService.this.mEnhancedDischargeTimeLock"})
        private boolean isEnhancedDischargePredictionValidLocked(long j) {
            return com.android.server.power.PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed > 0 && j < com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeElapsed && j - com.android.server.power.PowerManagerService.this.mLastEnhancedDischargeTimeUpdatedElapsed < 1800000;
        }

        public android.os.ParcelDuration getBatteryDischargePrediction() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                    if (com.android.server.power.PowerManagerService.this.mIsPowered) {
                        return null;
                    }
                    synchronized (com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeLock) {
                        long elapsedRealtime = com.android.server.power.PowerManagerService.this.mClock.elapsedRealtime();
                        if (!isEnhancedDischargePredictionValidLocked(elapsedRealtime)) {
                            return new android.os.ParcelDuration(com.android.server.power.PowerManagerService.this.mBatteryStats.computeBatteryTimeRemaining());
                        }
                        return new android.os.ParcelDuration(com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeElapsed - elapsedRealtime);
                    }
                }
            } catch (android.os.RemoteException e) {
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isBatteryDischargePredictionPersonalized() {
            boolean z;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.power.PowerManagerService.this.mEnhancedDischargeTimeLock) {
                    try {
                        z = isEnhancedDischargePredictionValidLocked(com.android.server.power.PowerManagerService.this.mClock.elapsedRealtime()) && com.android.server.power.PowerManagerService.this.mEnhancedDischargePredictionIsPersonalized;
                    } finally {
                    }
                }
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDeviceIdleMode() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isDeviceIdleModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isLightDeviceIdleMode() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isLightDeviceIdleModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public boolean isLowPowerStandbySupported() {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "isLowPowerStandbySupported");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.isSupported();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isLowPowerStandbyEnabled() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.isEnabled();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public void setLowPowerStandbyEnabled(boolean z) {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyEnabled");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.setEnabled(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public void setLowPowerStandbyActiveDuringMaintenance(boolean z) {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyActiveDuringMaintenance");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.setActiveDuringMaintenance(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public void forceLowPowerStandbyActive(boolean z) {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "forceLowPowerStandbyActive");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.forceActive(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public void setLowPowerStandbyPolicy(@android.annotation.Nullable android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "setLowPowerStandbyPolicy");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.setPolicy(android.os.PowerManager.LowPowerStandbyPolicy.fromParcelable(lowPowerStandbyPolicy));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public android.os.IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "getLowPowerStandbyPolicy");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.os.PowerManager.LowPowerStandbyPolicy.toParcelable(com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.getPolicy());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isExemptFromLowPowerStandby() {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.isPackageExempt(callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isReasonAllowedInLowPowerStandby(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.isAllowed(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isFeatureAllowedInLowPowerStandby(java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.isAllowed(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS")
        public void acquireLowPowerStandbyPorts(android.os.IBinder iBinder, java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> list) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS", "acquireLowPowerStandbyPorts");
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.acquireStandbyPorts(iBinder, callingUid, android.os.PowerManager.LowPowerStandbyPortDescription.fromParcelable(list));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS")
        public void releaseLowPowerStandbyPorts(android.os.IBinder iBinder) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SET_LOW_POWER_STANDBY_PORTS", "releaseLowPowerStandbyPorts");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.releaseStandbyPorts(iBinder);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_LOW_POWER_STANDBY", "android.permission.DEVICE_POWER"})
        public java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() {
            if (com.android.server.power.PowerManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_LOW_POWER_STANDBY", "getActiveLowPowerStandbyPorts");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.os.PowerManager.LowPowerStandbyPortDescription.toParcelable(com.android.server.power.PowerManagerService.this.mLowPowerStandbyController.getActiveStandbyPorts());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getLastShutdownReason() {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.getLastShutdownReasonInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getLastSleepReason() {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.getLastSleepReasonInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void reboot(boolean z, @android.annotation.Nullable java.lang.String str, boolean z2) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            if ("recovery".equals(str) || "recovery-update".equals(str)) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
            }
            com.android.server.power.ShutdownCheckPoints.recordCheckPoint(android.os.Binder.getCallingPid(), str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.shutdownOrRebootInternal(1, z, str, z2, false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void rebootSafeMode(boolean z, boolean z2) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            com.android.server.power.ShutdownCheckPoints.recordCheckPoint(android.os.Binder.getCallingPid(), "safemode");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.shutdownOrRebootInternal(2, z, "safemode", z2, false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void rebootCustom(boolean z, java.lang.String str, boolean z2) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            if ("recovery".equals(str)) {
                com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.shutdownOrRebootInternal(1, z, str, z2, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void shutdown(boolean z, java.lang.String str, boolean z2) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            com.android.server.power.ShutdownCheckPoints.recordCheckPoint(android.os.Binder.getCallingPid(), str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.shutdownOrRebootInternal(0, z, str, z2, false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void crash(java.lang.String str) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REBOOT", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.crashInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setStayOnSetting(int i) {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 0 && !android.provider.Settings.checkAndNoteWriteSettingsOperation(com.android.server.power.PowerManagerService.this.mContext, callingUid, android.provider.Settings.getPackageNameForUid(com.android.server.power.PowerManagerService.this.mContext, callingUid), null, true)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.setStayOnSettingInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setAttentionLight(boolean z, int i) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.setAttentionLightInternal(z, i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setKeyboardVisibility(boolean z) {
            synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                try {
                    if (com.android.server.power.PowerManagerService.this.mKeyboardVisible != z) {
                        com.android.server.power.PowerManagerService.this.mKeyboardVisible = z;
                        synchronized (com.android.server.power.PowerManagerService.this.mLock) {
                            com.android.server.power.PowerManagerService.this.mDirty |= 4;
                            com.android.server.power.PowerManagerService.this.updatePowerStateLocked();
                        }
                    }
                } finally {
                }
            }
        }

        public void setDozeAfterScreenOff(boolean z) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.setDozeAfterScreenOffInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAmbientDisplayAvailable() {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mAmbientDisplayConfiguration.ambientDisplayAvailable();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void suppressAmbientDisplay(@android.annotation.NonNull java.lang.String str, boolean z) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_DREAM_STATE", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.suppress(str, callingUid, z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAmbientDisplaySuppressedForToken(@android.annotation.NonNull java.lang.String str) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed(str, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAmbientDisplaySuppressedForTokenByApp(@android.annotation.NonNull java.lang.String str, int i) {
            boolean z;
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_SUPPRESSION", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (isAmbientDisplayAvailable()) {
                    if (com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed(str, i)) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAmbientDisplaySuppressed() {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_DREAM_STATE", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void boostScreenBrightness(long j) {
            long uptimeMillis = com.android.server.power.PowerManagerService.this.mClock.uptimeMillis();
            if (j > com.android.server.power.PowerManagerService.this.mClock.uptimeMillis()) {
                android.util.Slog.e(com.android.server.power.PowerManagerService.TAG, "Event time " + j + " cannot be newer than " + uptimeMillis);
                throw new java.lang.IllegalArgumentException("event time must not be in the future");
            }
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.boostScreenBrightnessInternal(j, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isScreenBrightnessBoosted() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.isScreenBrightnessBoostedInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean forceSuspend() {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.forceSuspendInternal(callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.power.PowerManagerService.this.mContext, com.android.server.power.PowerManagerService.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                boolean z = false;
                for (java.lang.String str : strArr) {
                    if (str.equals("--proto")) {
                        z = true;
                        break;
                    }
                }
                try {
                    if (z) {
                        com.android.server.power.PowerManagerService.this.dumpProto(fileDescriptor);
                    } else {
                        com.android.server.power.PowerManagerService.this.dumpInternal(printWriter);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public java.util.List<java.lang.String> getAmbientDisplaySuppressionTokens() {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.getSuppressionTokens(callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.power.PowerManagerService.BinderService getBinderServiceInstance() {
        return this.mBinderService;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.power.PowerManagerService.LocalService getLocalServiceInstance() {
        return this.mLocalService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.VisibleForTesting
    int getLastShutdownReasonInternal() {
        char c;
        java.lang.String str = this.mSystemProperties.get(SYSTEM_PROPERTY_REBOOT_REASON, null);
        switch (str.hashCode()) {
            case -2117951935:
                if (str.equals(REASON_THERMAL_SHUTDOWN)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1099647817:
                if (str.equals(REASON_LOW_BATTERY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -934938715:
                if (str.equals(REASON_REBOOT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -852189395:
                if (str.equals(REASON_USERREQUESTED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -169343402:
                if (str.equals(REASON_SHUTDOWN)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1218064802:
                if (str.equals(REASON_BATTERY_THERMAL_STATE)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            default:
                return 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getPowerGroupSize() {
        int size;
        synchronized (this.mLock) {
            size = this.mPowerGroups.size();
        }
        return size;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLastSleepReasonInternal() {
        int i;
        synchronized (this.mLock) {
            i = this.mLastGlobalSleepReason;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.PowerManager.WakeData getLastWakeupInternal() {
        android.os.PowerManager.WakeData wakeData;
        synchronized (this.mLock) {
            wakeData = new android.os.PowerManager.WakeData(this.mLastGlobalWakeTime, this.mLastGlobalWakeReason, this.mLastGlobalWakeTimeRealtime - this.mLastGlobalSleepTimeRealtime);
        }
        return wakeData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.PowerManager.SleepData getLastGoToSleepInternal() {
        android.os.PowerManager.SleepData sleepData;
        synchronized (this.mLock) {
            sleepData = new android.os.PowerManager.SleepData(this.mLastGlobalSleepTime, this.mLastGlobalSleepReason);
        }
        return sleepData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interceptPowerKeyDownInternal(android.view.KeyEvent keyEvent) {
        synchronized (this.mLock) {
            try {
                if (this.mProximityPositive && !this.mInterceptedPowerKeyForProximity) {
                    this.mDisplayManagerInternal.ignoreProximitySensorUntilChanged();
                    this.mInterceptedPowerKeyForProximity = true;
                    return true;
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.DEVICE_POWER")
    public void goToSleepInternal(android.util.IntArray intArray, long j, int i, int i2) {
        long uptimeMillis = this.mClock.uptimeMillis();
        if (j > uptimeMillis) {
            android.util.Slog.e(TAG, "Event time " + j + " cannot be newer than " + uptimeMillis);
            throw new java.lang.IllegalArgumentException("event time must not be in the future");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        boolean z = (i2 & 1) != 0;
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                for (int i3 = 0; i3 < intArray.size(); i3++) {
                    try {
                        int i4 = intArray.get(i3);
                        com.android.server.power.PowerGroup powerGroup = this.mPowerGroups.get(i4);
                        if (powerGroup == null) {
                            throw new java.lang.IllegalArgumentException("power group(" + i4 + ") doesn't exist");
                        }
                        if ((i2 & 2) != 0) {
                            if (this.mFoldGracePeriodProvider.isEnabled()) {
                                if (!powerGroup.hasWakeLockKeepingScreenOnLocked()) {
                                    this.mNotifier.showDismissibleKeyguard();
                                }
                            } else if (powerGroup.hasWakeLockKeepingScreenOnLocked()) {
                            }
                        }
                        if (z) {
                            sleepPowerGroupLocked(powerGroup, j, i, callingUid);
                        } else {
                            dozePowerGroupLocked(powerGroup, j, i, callingUid);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class LocalService extends android.os.PowerManagerInternal {
        LocalService() {
        }

        public void setButtonBrightnessOverrideFromWindowManager(float f) {
            com.android.server.power.PowerManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.power.PowerManagerService.this.setButtonBrightnessOverrideFromWindowManagerInternal(f);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setScreenBrightnessOverrideFromWindowManager(float f) {
            if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f > 1.0f) {
                f = Float.NaN;
            }
            com.android.server.power.PowerManagerService.this.setScreenBrightnessOverrideFromWindowManagerInternal(f);
        }

        public void setDozeOverrideFromDreamManager(int i, int i2) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 6:
                    break;
                case 5:
                default:
                    i = 0;
                    break;
            }
            if (i2 < -1 || i2 > 255) {
                i2 = -1;
            }
            com.android.server.power.PowerManagerService.this.setDozeOverrideFromDreamManagerInternal(i, i2);
        }

        public void setUserInactiveOverrideFromWindowManager() {
            com.android.server.power.PowerManagerService.this.setUserInactiveOverrideFromWindowManagerInternal();
        }

        public void setUserActivityTimeoutOverrideFromWindowManager(long j) {
            com.android.server.power.PowerManagerService.this.setUserActivityTimeoutOverrideFromWindowManagerInternal(j);
        }

        public void setDrawWakeLockOverrideFromSidekick(boolean z) {
            com.android.server.power.PowerManagerService.this.setDrawWakeLockOverrideFromSidekickInternal(z);
        }

        public void setMaximumScreenOffTimeoutFromDeviceAdmin(int i, long j) {
            com.android.server.power.PowerManagerService.this.setMaximumScreenOffTimeoutFromDeviceAdminInternal(i, j);
        }

        public android.os.PowerSaveState getLowPowerState(int i) {
            if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                return com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.getBatterySaverPolicy().getBatterySaverPolicy(i);
            }
            return new android.os.PowerSaveState.Builder().build();
        }

        public void registerLowPowerModeObserver(android.os.PowerManagerInternal.LowPowerModeListener lowPowerModeListener) {
            if (com.android.server.power.PowerManagerService.this.mBatterySaverSupported) {
                com.android.server.power.PowerManagerService.this.mBatterySaverStateMachine.getBatterySaverController().addListener(lowPowerModeListener);
            } else {
                android.util.Slog.w(com.android.server.power.PowerManagerService.TAG, "Battery saver is not supported, no low power mode observer registered");
            }
        }

        public boolean setDeviceIdleMode(boolean z) {
            return com.android.server.power.PowerManagerService.this.setDeviceIdleModeInternal(z);
        }

        public boolean setLightDeviceIdleMode(boolean z) {
            return com.android.server.power.PowerManagerService.this.setLightDeviceIdleModeInternal(z);
        }

        public void setDeviceIdleWhitelist(int[] iArr) {
            com.android.server.power.PowerManagerService.this.setDeviceIdleWhitelistInternal(iArr);
        }

        public void setDeviceIdleTempWhitelist(int[] iArr) {
            com.android.server.power.PowerManagerService.this.setDeviceIdleTempWhitelistInternal(iArr);
        }

        public void setLowPowerStandbyAllowlist(int[] iArr) {
            com.android.server.power.PowerManagerService.this.setLowPowerStandbyAllowlistInternal(iArr);
        }

        public void setLowPowerStandbyActive(boolean z) {
            com.android.server.power.PowerManagerService.this.setLowPowerStandbyActiveInternal(z);
        }

        public void startUidChanges() {
            com.android.server.power.PowerManagerService.this.startUidChangesInternal();
        }

        public void finishUidChanges() {
            com.android.server.power.PowerManagerService.this.finishUidChangesInternal();
        }

        public void updateUidProcState(int i, int i2) {
            com.android.server.power.PowerManagerService.this.updateUidProcStateInternal(i, i2);
        }

        public void uidGone(int i) {
            com.android.server.power.PowerManagerService.this.uidGoneInternal(i);
        }

        public void uidActive(int i) {
            com.android.server.power.PowerManagerService.this.uidActiveInternal(i);
        }

        public void uidIdle(int i) {
            com.android.server.power.PowerManagerService.this.uidIdleInternal(i);
        }

        public void setPowerBoost(int i, int i2) {
            com.android.server.power.PowerManagerService.this.setPowerBoostInternal(i, i2);
        }

        public void setPowerMode(int i, boolean z) {
            com.android.server.power.PowerManagerService.this.setPowerModeInternal(i, z);
        }

        public boolean wasDeviceIdleFor(long j) {
            return com.android.server.power.PowerManagerService.this.wasDeviceIdleForInternal(j);
        }

        public android.os.PowerManager.WakeData getLastWakeup() {
            return com.android.server.power.PowerManagerService.this.getLastWakeupInternal();
        }

        public android.os.PowerManager.SleepData getLastGoToSleep() {
            return com.android.server.power.PowerManagerService.this.getLastGoToSleepInternal();
        }

        public boolean interceptPowerKeyDown(android.view.KeyEvent keyEvent) {
            return com.android.server.power.PowerManagerService.this.interceptPowerKeyDownInternal(keyEvent);
        }

        public void nap(long j, boolean z) {
            com.android.server.power.PowerManagerService.this.napInternal(j, 1000, z);
        }

        public boolean isAmbientDisplaySuppressed() {
            return com.android.server.power.PowerManagerService.this.mAmbientDisplaySuppressionController.isSuppressed();
        }
    }

    class DeviceStateListener implements android.hardware.devicestate.DeviceStateManager.DeviceStateCallback {
        private int mDeviceState = -1;

        DeviceStateListener() {
        }

        public void onStateChanged(int i) {
            if (this.mDeviceState != i) {
                this.mDeviceState = i;
                com.android.server.power.PowerManagerService.this.userActivityInternal(0, com.android.server.power.PowerManagerService.this.mClock.uptimeMillis(), 6, 0, 1000);
            }
        }
    }

    static boolean isSameCallback(android.os.IWakeLockCallback iWakeLockCallback, android.os.IWakeLockCallback iWakeLockCallback2) {
        if (iWakeLockCallback == iWakeLockCallback2) {
            return true;
        }
        if (iWakeLockCallback != null && iWakeLockCallback2 != null && iWakeLockCallback.asBinder() == iWakeLockCallback2.asBinder()) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupProximity() {
        synchronized (this.mProximityWakeLock) {
            cleanupProximityLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupProximityLocked() {
        if (this.mProximityWakeLock.isHeld()) {
            this.mProximityWakeLock.release();
        }
        if (this.mProximityListener != null) {
            this.mSensorManager.unregisterListener(this.mProximityListener);
            this.mProximityListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runWithProximityCheck(java.lang.Runnable runnable) {
        if (this.mHandler.hasMessages(6)) {
            return;
        }
        boolean z = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).getCallState() == 1;
        if (this.mProximityWakeSupported && this.mProximityWakeEnabled && this.mProximitySensor != null && !z) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(6);
            obtainMessage.obj = runnable;
            this.mHandler.sendMessageDelayed(obtainMessage, this.mProximityTimeOut);
            runPostProximityCheck(runnable);
            return;
        }
        runnable.run();
    }

    private void runPostProximityCheck(final java.lang.Runnable runnable) {
        if (this.mSensorManager == null) {
            runnable.run();
            return;
        }
        synchronized (this.mProximityWakeLock) {
            this.mProximityWakeLock.acquire();
            this.mProximityListener = new android.hardware.SensorEventListener() { // from class: com.android.server.power.PowerManagerService.5
                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
                    com.android.server.power.PowerManagerService.this.cleanupProximityLocked();
                    if (!com.android.server.power.PowerManagerService.this.mHandler.hasMessages(6)) {
                        android.util.Slog.w(com.android.server.power.PowerManagerService.TAG, "Proximity sensor took too long, wake event already triggered!");
                        return;
                    }
                    com.android.server.power.PowerManagerService.this.mHandler.removeMessages(6);
                    float f = sensorEvent.values[0];
                    if (f >= com.android.server.power.PowerManagerService.PROXIMITY_NEAR_THRESHOLD || f >= com.android.server.power.PowerManagerService.this.mProximitySensor.getMaximumRange()) {
                        runnable.run();
                    } else {
                        android.util.Slog.w(com.android.server.power.PowerManagerService.TAG, "Not waking up. Proximity sensor is blocked.");
                    }
                }

                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
                }
            };
            this.mSensorManager.registerListener(this.mProximityListener, this.mProximitySensor, 0);
        }
    }
}
