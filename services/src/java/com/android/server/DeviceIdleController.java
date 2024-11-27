package com.android.server;

/* loaded from: classes.dex */
public class DeviceIdleController extends com.android.server.SystemService implements com.android.server.AnyMotionDetector.DeviceIdleCallback {
    private static final int ACTIVE_REASON_ALARM = 7;
    private static final int ACTIVE_REASON_CHARGING = 3;
    private static final int ACTIVE_REASON_EMERGENCY_CALL = 8;
    private static final int ACTIVE_REASON_FORCED = 6;
    private static final int ACTIVE_REASON_FROM_BINDER_CALL = 5;
    private static final int ACTIVE_REASON_MODE_MANAGER = 9;
    private static final int ACTIVE_REASON_MOTION = 1;
    private static final int ACTIVE_REASON_ONBODY = 10;
    private static final int ACTIVE_REASON_SCREEN = 2;
    private static final int ACTIVE_REASON_UNKNOWN = 0;
    private static final int ACTIVE_REASON_UNLOCKED = 4;
    private static final boolean COMPRESS_TIME = false;
    private static final boolean DEBUG = false;
    private static final int EVENT_BUFFER_SIZE = 100;
    private static final int EVENT_DEEP_IDLE = 4;
    private static final int EVENT_DEEP_MAINTENANCE = 5;
    private static final int EVENT_LIGHT_IDLE = 2;
    private static final int EVENT_LIGHT_MAINTENANCE = 3;
    private static final int EVENT_NORMAL = 1;
    private static final int EVENT_NULL = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_ACTIVE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_IDLE = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_IDLE_MAINTENANCE = 6;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_INACTIVE = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_OVERRIDE = 7;

    @com.android.internal.annotations.VisibleForTesting
    static final int LIGHT_STATE_WAITING_FOR_NETWORK = 5;
    private static final int MSG_FINISH_IDLE_OP = 8;
    private static final int MSG_REPORT_ACTIVE = 5;
    private static final int MSG_REPORT_IDLE_OFF = 4;
    private static final int MSG_REPORT_IDLE_ON = 2;
    private static final int MSG_REPORT_IDLE_ON_LIGHT = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final int MSG_REPORT_STATIONARY_STATUS = 7;
    private static final int MSG_REPORT_TEMP_APP_WHITELIST_ADDED_TO_NPMS = 14;
    private static final int MSG_REPORT_TEMP_APP_WHITELIST_CHANGED = 13;
    private static final int MSG_REPORT_TEMP_APP_WHITELIST_REMOVED_TO_NPMS = 15;
    private static final int MSG_SEND_CONSTRAINT_MONITORING = 10;
    private static final int MSG_TEMP_APP_WHITELIST_TIMEOUT = 6;
    private static final int MSG_WRITE_CONFIG = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_ACTIVE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_IDLE = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_IDLE_MAINTENANCE = 6;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_IDLE_PENDING = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_INACTIVE = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_LOCATING = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_QUICK_DOZE_DELAY = 7;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_SENSING = 3;
    private static final java.lang.String TAG = "DeviceIdleController";
    private static final java.lang.String USER_ALLOWLIST_ADDITION_METRIC_ID = "battery.value_app_added_to_power_allowlist";
    private static final java.lang.String USER_ALLOWLIST_REMOVAL_METRIC_ID = "battery.value_app_removed_from_power_allowlist";

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mActiveIdleOpCount;
    private android.os.PowerManager.WakeLock mActiveIdleWakeLock;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mActiveReason;
    private android.app.AlarmManager mAlarmManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mAlarmsActive;
    private com.android.server.AnyMotionDetector mAnyMotionDetector;
    private final com.android.server.AppStateTrackerImpl mAppStateTracker;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mBatterySaverEnabled;
    private com.android.internal.app.IBatteryStats mBatteryStats;
    com.android.server.DeviceIdleController.BinderService mBinderService;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mCharging;
    public final android.util.AtomicFile mConfigFile;
    private com.android.server.DeviceIdleController.Constants mConstants;
    private com.android.server.deviceidle.ConstraintController mConstraintController;
    private final android.util.ArrayMap<com.android.server.deviceidle.IDeviceIdleConstraint, com.android.server.deviceidle.DeviceIdleConstraintTracker> mConstraints;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mCurLightIdleBudget;

    @com.android.internal.annotations.VisibleForTesting
    final android.app.AlarmManager.OnAlarmListener mDeepAlarmListener;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mDeepEnabled;
    private final com.android.server.DeviceIdleController.EmergencyCallListener mEmergencyCallListener;
    private final int[] mEventCmds;
    private final java.lang.String[] mEventReasons;
    private final long[] mEventTimes;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mForceIdle;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mForceModeManagerOffBodyState;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mForceModeManagerQuickDozeRequest;
    private final android.location.LocationListener mGenericLocationListener;
    private android.os.PowerManager.WakeLock mGoingIdleWakeLock;
    private final android.location.LocationListener mGpsLocationListener;
    final com.android.server.DeviceIdleController.MyHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mHasFusedLocation;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mHasGps;
    private android.content.Intent mIdleIntent;
    private android.os.Bundle mIdleIntentOptions;
    private final android.content.IIntentReceiver mIdleStartedDoneReceiver;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mInactiveTimeout;
    private final com.android.server.DeviceIdleController.Injector mInjector;
    private final android.content.BroadcastReceiver mInteractivityReceiver;
    private final boolean mIsLocationPrefetchEnabled;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mIsOffBody;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mJobsActive;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.location.Location mLastGenericLocation;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.location.Location mLastGpsLocation;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastMotionEventElapsed;
    private final android.app.AlarmManager.OnAlarmListener mLightAlarmListener;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mLightEnabled;
    private android.content.Intent mLightIdleIntent;
    private android.os.Bundle mLightIdleIntentOptions;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mLightState;
    private android.app.ActivityManagerInternal mLocalActivityManager;
    private com.android.server.wm.ActivityTaskManagerInternal mLocalActivityTaskManager;
    private com.android.server.AlarmManagerInternal mLocalAlarmManager;
    private android.os.PowerManagerInternal mLocalPowerManager;
    private com.android.server.DeviceIdleInternal mLocalService;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mLocated;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mLocating;

    @android.annotation.Nullable
    private android.location.LocationRequest mLocationRequest;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mMaintenanceStartTime;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.DeviceIdleController.ModeManagerOffBodyStateConsumer mModeManagerOffBodyStateConsumer;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.DeviceIdleController.ModeManagerQuickDozeRequestConsumer mModeManagerQuickDozeRequestConsumer;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mModeManagerRequestedQuickDoze;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.DeviceIdleController.MotionListener mMotionListener;
    private final android.app.AlarmManager.OnAlarmListener mMotionRegistrationAlarmListener;
    private android.hardware.Sensor mMotionSensor;
    private final android.app.AlarmManager.OnAlarmListener mMotionTimeoutAlarmListener;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mNetworkConnected;
    private android.net.INetworkPolicyManager mNetworkPolicyManager;
    private com.android.server.net.NetworkPolicyManagerInternal mNetworkPolicyManagerInternal;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextAlarmTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextIdleDelay;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextIdlePendingDelay;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextLightAlarmTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextLightIdleDelay;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextLightIdleDelayFlex;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNextSensingTimeoutAlarmTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mNotMoving;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mNumBlockingConstraints;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private android.os.PowerManager mPowerManager;
    private android.os.Bundle mPowerSaveTempWhilelistChangedOptions;
    private android.content.Intent mPowerSaveTempWhitelistChangedIntent;
    private int[] mPowerSaveWhitelistAllAppIdArray;
    private final android.util.SparseBooleanArray mPowerSaveWhitelistAllAppIds;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPowerSaveWhitelistApps;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPowerSaveWhitelistAppsExceptIdle;
    private android.content.Intent mPowerSaveWhitelistChangedIntent;
    private android.os.Bundle mPowerSaveWhitelistChangedOptions;
    private int[] mPowerSaveWhitelistExceptIdleAppIdArray;
    private final android.util.SparseBooleanArray mPowerSaveWhitelistExceptIdleAppIds;
    private int[] mPowerSaveWhitelistSystemAppIdArray;
    private final android.util.SparseBooleanArray mPowerSaveWhitelistSystemAppIds;
    private final android.util.SparseBooleanArray mPowerSaveWhitelistSystemAppIdsExceptIdle;
    private int[] mPowerSaveWhitelistUserAppIdArray;
    private final android.util.SparseBooleanArray mPowerSaveWhitelistUserAppIds;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPowerSaveWhitelistUserApps;
    private final android.util.ArraySet<java.lang.String> mPowerSaveWhitelistUserAppsExceptIdle;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mQuickDozeActivated;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mQuickDozeActivatedWhileIdling;
    private final android.content.BroadcastReceiver mReceiver;
    private android.util.ArrayMap<java.lang.String, java.lang.Integer> mRemovedFromSystemWhitelistApps;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mScreenLocked;
    private com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver mScreenObserver;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mScreenOn;
    private final android.app.AlarmManager.OnAlarmListener mSensingTimeoutAlarmListener;
    private android.hardware.SensorManager mSensorManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mState;
    private final android.util.ArraySet<com.android.server.DeviceIdleInternal.StationaryListener> mStationaryListeners;
    private final android.util.ArraySet<com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener> mTempAllowlistChangeListeners;
    private int[] mTempWhitelistAppIdArray;
    private final android.util.SparseArray<android.util.Pair<android.util.MutableLong, java.lang.String>> mTempWhitelistAppIdEndTimes;
    private final boolean mUseMotionSensor;

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "INACTIVE";
            case 2:
                return "IDLE_PENDING";
            case 3:
                return "SENSING";
            case 4:
                return "LOCATING";
            case 5:
                return "IDLE";
            case 6:
                return "IDLE_MAINTENANCE";
            case 7:
                return "QUICK_DOZE_DELAY";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String lightStateToString(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "INACTIVE";
            case 2:
            case 3:
            default:
                return java.lang.Integer.toString(i);
            case 4:
                return "IDLE";
            case 5:
                return "WAITING_FOR_NETWORK";
            case 6:
                return "IDLE_MAINTENANCE";
            case 7:
                return "OVERRIDE";
        }
    }

    private void addEvent(int i, java.lang.String str) {
        if (this.mEventCmds[0] != i) {
            java.lang.System.arraycopy(this.mEventCmds, 0, this.mEventCmds, 1, 99);
            java.lang.System.arraycopy(this.mEventTimes, 0, this.mEventTimes, 1, 99);
            java.lang.System.arraycopy(this.mEventReasons, 0, this.mEventReasons, 1, 99);
            this.mEventCmds[0] = i;
            this.mEventTimes[0] = android.os.SystemClock.elapsedRealtime();
            this.mEventReasons[0] = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this) {
            stepLightIdleStateLocked("s:alarm");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        synchronized (this) {
            try {
                if (this.mStationaryListeners.size() > 0) {
                    startMonitoringMotionLocked();
                    scheduleMotionTimeoutAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        synchronized (this) {
            try {
                if (!isStationaryLocked()) {
                    android.util.Slog.w(TAG, "motion timeout went off and device isn't stationary");
                } else {
                    postStationaryStatusUpdated();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void postStationaryStatus(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
        this.mHandler.obtainMessage(7, stationaryListener).sendToTarget();
    }

    private void postStationaryStatusUpdated() {
        this.mHandler.sendEmptyMessage(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean isStationaryLocked() {
        return this.mMotionListener.active && this.mInjector.getElapsedRealtime() - java.lang.Math.max(this.mMotionListener.activatedTimeElapsed, this.mLastMotionEventElapsed) >= this.mConstants.MOTION_INACTIVE_TIMEOUT;
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
        synchronized (this) {
            try {
                if (this.mStationaryListeners.add(stationaryListener)) {
                    postStationaryStatus(stationaryListener);
                    if (this.mMotionListener.active) {
                        if (!isStationaryLocked() && this.mStationaryListeners.size() == 1) {
                            scheduleMotionTimeoutAlarmLocked();
                        }
                    } else {
                        startMonitoringMotionLocked();
                        scheduleMotionTimeoutAlarmLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
        synchronized (this) {
            try {
                if (this.mStationaryListeners.remove(stationaryListener) && this.mStationaryListeners.size() == 0 && (this.mState == 0 || this.mState == 1 || this.mQuickDozeActivated)) {
                    maybeStopMonitoringMotionLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerTempAllowlistChangeListener(@android.annotation.NonNull com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
        synchronized (this) {
            this.mTempAllowlistChangeListeners.add(tempAllowlistChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterTempAllowlistChangeListener(@android.annotation.NonNull com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
        synchronized (this) {
            this.mTempAllowlistChangeListeners.remove(tempAllowlistChangeListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class ModeManagerQuickDozeRequestConsumer implements java.util.function.Consumer<java.lang.Boolean> {
        ModeManagerQuickDozeRequestConsumer() {
        }

        @Override // java.util.function.Consumer
        public void accept(java.lang.Boolean bool) {
            android.util.Slog.i(com.android.server.DeviceIdleController.TAG, "Mode manager quick doze request: " + bool);
            synchronized (com.android.server.DeviceIdleController.this) {
                try {
                    if (!com.android.server.DeviceIdleController.this.mForceModeManagerQuickDozeRequest && com.android.server.DeviceIdleController.this.mModeManagerRequestedQuickDoze != bool.booleanValue()) {
                        com.android.server.DeviceIdleController.this.mModeManagerRequestedQuickDoze = bool.booleanValue();
                        onModeManagerRequestChangedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"DeviceIdleController.this"})
        public void onModeManagerRequestChangedLocked() {
            com.android.server.DeviceIdleController.this.maybeBecomeActiveOnModeManagerEventsLocked();
            com.android.server.DeviceIdleController.this.updateQuickDozeFlagLocked();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class ModeManagerOffBodyStateConsumer implements java.util.function.Consumer<java.lang.Boolean> {
        ModeManagerOffBodyStateConsumer() {
        }

        @Override // java.util.function.Consumer
        public void accept(java.lang.Boolean bool) {
            android.util.Slog.i(com.android.server.DeviceIdleController.TAG, "Offbody event from mode manager: " + bool);
            synchronized (com.android.server.DeviceIdleController.this) {
                try {
                    if (!com.android.server.DeviceIdleController.this.mForceModeManagerOffBodyState && com.android.server.DeviceIdleController.this.mIsOffBody != bool.booleanValue()) {
                        com.android.server.DeviceIdleController.this.mIsOffBody = bool.booleanValue();
                        onModeManagerOffBodyChangedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"DeviceIdleController.this"})
        public void onModeManagerOffBodyChangedLocked() {
            com.android.server.DeviceIdleController.this.maybeBecomeActiveOnModeManagerEventsLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"DeviceIdleController.this"})
    public void maybeBecomeActiveOnModeManagerEventsLocked() {
        synchronized (this) {
            try {
                if (this.mQuickDozeActivated) {
                    return;
                }
                if (!this.mIsOffBody && !this.mForceIdle) {
                    this.mActiveReason = 10;
                    becomeActiveLocked("on_body", android.os.Process.myUid());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class MotionListener extends android.hardware.TriggerEventListener implements android.hardware.SensorEventListener {
        long activatedTimeElapsed;
        boolean active = false;

        MotionListener() {
        }

        public boolean isActive() {
            return this.active;
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(android.hardware.TriggerEvent triggerEvent) {
            synchronized (com.android.server.DeviceIdleController.this) {
                this.active = false;
                com.android.server.DeviceIdleController.this.motionLocked();
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            synchronized (com.android.server.DeviceIdleController.this) {
                com.android.server.DeviceIdleController.this.mSensorManager.unregisterListener(this, com.android.server.DeviceIdleController.this.mMotionSensor);
                this.active = false;
                com.android.server.DeviceIdleController.this.motionLocked();
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }

        public boolean registerLocked() {
            boolean registerListener;
            if (com.android.server.DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                registerListener = com.android.server.DeviceIdleController.this.mSensorManager.requestTriggerSensor(com.android.server.DeviceIdleController.this.mMotionListener, com.android.server.DeviceIdleController.this.mMotionSensor);
            } else {
                registerListener = com.android.server.DeviceIdleController.this.mSensorManager.registerListener(com.android.server.DeviceIdleController.this.mMotionListener, com.android.server.DeviceIdleController.this.mMotionSensor, 3);
            }
            if (registerListener) {
                this.active = true;
                this.activatedTimeElapsed = com.android.server.DeviceIdleController.this.mInjector.getElapsedRealtime();
            } else {
                android.util.Slog.e(com.android.server.DeviceIdleController.TAG, "Unable to register for " + com.android.server.DeviceIdleController.this.mMotionSensor);
            }
            return registerListener;
        }

        public void unregisterLocked() {
            if (com.android.server.DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                com.android.server.DeviceIdleController.this.mSensorManager.cancelTriggerSensor(com.android.server.DeviceIdleController.this.mMotionListener, com.android.server.DeviceIdleController.this.mMotionSensor);
            } else {
                com.android.server.DeviceIdleController.this.mSensorManager.unregisterListener(com.android.server.DeviceIdleController.this.mMotionListener);
            }
            this.active = false;
        }
    }

    public final class Constants extends android.database.ContentObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private static final long DEFAULT_IDLE_AFTER_INACTIVE_TIMEOUT_SMALL_BATTERY = 60000;
        private static final long DEFAULT_INACTIVE_TIMEOUT_SMALL_BATTERY = 60000;
        private static final java.lang.String KEY_FLEX_TIME_SHORT = "flex_time_short";
        private static final java.lang.String KEY_IDLE_AFTER_INACTIVE_TIMEOUT = "idle_after_inactive_to";
        private static final java.lang.String KEY_IDLE_FACTOR = "idle_factor";
        private static final java.lang.String KEY_IDLE_PENDING_FACTOR = "idle_pending_factor";
        private static final java.lang.String KEY_IDLE_PENDING_TIMEOUT = "idle_pending_to";
        private static final java.lang.String KEY_IDLE_TIMEOUT = "idle_to";
        private static final java.lang.String KEY_INACTIVE_TIMEOUT = "inactive_to";
        private static final java.lang.String KEY_LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = "light_after_inactive_to";
        private static final java.lang.String KEY_LIGHT_IDLE_FACTOR = "light_idle_factor";
        private static final java.lang.String KEY_LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = "light_idle_flex_linear_increase_factor_ms";
        private static final java.lang.String KEY_LIGHT_IDLE_INCREASE_LINEARLY = "light_idle_increase_linearly";
        private static final java.lang.String KEY_LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = "light_idle_linear_increase_factor_ms";
        private static final java.lang.String KEY_LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = "light_idle_maintenance_max_budget";
        private static final java.lang.String KEY_LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = "light_idle_maintenance_min_budget";
        private static final java.lang.String KEY_LIGHT_IDLE_TIMEOUT = "light_idle_to";
        private static final java.lang.String KEY_LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = "light_idle_to_initial_flex";
        private static final java.lang.String KEY_LIGHT_IDLE_TIMEOUT_MAX_FLEX = "light_max_idle_to_flex";
        private static final java.lang.String KEY_LIGHT_MAX_IDLE_TIMEOUT = "light_max_idle_to";
        private static final java.lang.String KEY_LOCATING_TIMEOUT = "locating_to";
        private static final java.lang.String KEY_LOCATION_ACCURACY = "location_accuracy";
        private static final java.lang.String KEY_MAX_IDLE_PENDING_TIMEOUT = "max_idle_pending_to";
        private static final java.lang.String KEY_MAX_IDLE_TIMEOUT = "max_idle_to";
        private static final java.lang.String KEY_MAX_TEMP_APP_ALLOWLIST_DURATION_MS = "max_temp_app_allowlist_duration_ms";
        private static final java.lang.String KEY_MIN_DEEP_MAINTENANCE_TIME = "min_deep_maintenance_time";
        private static final java.lang.String KEY_MIN_LIGHT_MAINTENANCE_TIME = "min_light_maintenance_time";
        private static final java.lang.String KEY_MIN_TIME_TO_ALARM = "min_time_to_alarm";
        private static final java.lang.String KEY_MMS_TEMP_APP_ALLOWLIST_DURATION_MS = "mms_temp_app_allowlist_duration_ms";
        private static final java.lang.String KEY_MOTION_INACTIVE_TIMEOUT = "motion_inactive_to";
        private static final java.lang.String KEY_MOTION_INACTIVE_TIMEOUT_FLEX = "motion_inactive_to_flex";
        private static final java.lang.String KEY_NOTIFICATION_ALLOWLIST_DURATION_MS = "notification_allowlist_duration_ms";
        private static final java.lang.String KEY_QUICK_DOZE_DELAY_TIMEOUT = "quick_doze_delay_to";
        private static final java.lang.String KEY_SENSING_TIMEOUT = "sensing_to";
        private static final java.lang.String KEY_SMS_TEMP_APP_ALLOWLIST_DURATION_MS = "sms_temp_app_allowlist_duration_ms";
        private static final java.lang.String KEY_USE_MODE_MANAGER = "use_mode_manager";
        private static final java.lang.String KEY_USE_WINDOW_ALARMS = "use_window_alarms";
        private static final java.lang.String KEY_WAIT_FOR_UNLOCK = "wait_for_unlock";
        public long FLEX_TIME_SHORT;
        public long IDLE_AFTER_INACTIVE_TIMEOUT;
        public float IDLE_FACTOR;
        public float IDLE_PENDING_FACTOR;
        public long IDLE_PENDING_TIMEOUT;
        public long IDLE_TIMEOUT;
        public long INACTIVE_TIMEOUT;
        public long LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT;
        public float LIGHT_IDLE_FACTOR;
        public long LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS;
        public boolean LIGHT_IDLE_INCREASE_LINEARLY;
        public long LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS;
        public long LIGHT_IDLE_MAINTENANCE_MAX_BUDGET;
        public long LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
        public long LIGHT_IDLE_TIMEOUT;
        public long LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
        public long LIGHT_IDLE_TIMEOUT_MAX_FLEX;
        public long LIGHT_MAX_IDLE_TIMEOUT;
        public long LOCATING_TIMEOUT;
        public float LOCATION_ACCURACY;
        public long MAX_IDLE_PENDING_TIMEOUT;
        public long MAX_IDLE_TIMEOUT;
        public long MAX_TEMP_APP_ALLOWLIST_DURATION_MS;
        public long MIN_DEEP_MAINTENANCE_TIME;
        public long MIN_LIGHT_MAINTENANCE_TIME;
        public long MIN_TIME_TO_ALARM;
        public long MMS_TEMP_APP_ALLOWLIST_DURATION_MS;
        public long MOTION_INACTIVE_TIMEOUT;
        public long MOTION_INACTIVE_TIMEOUT_FLEX;
        public long NOTIFICATION_ALLOWLIST_DURATION_MS;
        public long QUICK_DOZE_DELAY_TIMEOUT;
        public long SENSING_TIMEOUT;
        public long SMS_TEMP_APP_ALLOWLIST_DURATION_MS;
        public boolean USE_MODE_MANAGER;
        public boolean USE_WINDOW_ALARMS;
        public boolean WAIT_FOR_UNLOCK;
        private long mDefaultFlexTimeShort;
        private long mDefaultIdleAfterInactiveTimeout;
        private float mDefaultIdleFactor;
        private float mDefaultIdlePendingFactor;
        private long mDefaultIdlePendingTimeout;
        private long mDefaultIdleTimeout;
        private long mDefaultInactiveTimeout;
        private long mDefaultLightIdleAfterInactiveTimeout;
        private float mDefaultLightIdleFactor;
        private long mDefaultLightIdleFlexLinearIncreaseFactorMs;
        private boolean mDefaultLightIdleIncreaseLinearly;
        private long mDefaultLightIdleLinearIncreaseFactorMs;
        private long mDefaultLightIdleMaintenanceMaxBudget;
        private long mDefaultLightIdleMaintenanceMinBudget;
        private long mDefaultLightIdleTimeout;
        private long mDefaultLightIdleTimeoutInitialFlex;
        private long mDefaultLightIdleTimeoutMaxFlex;
        private long mDefaultLightMaxIdleTimeout;
        private long mDefaultLocatingTimeout;
        private float mDefaultLocationAccuracy;
        private long mDefaultMaxIdlePendingTimeout;
        private long mDefaultMaxIdleTimeout;
        private long mDefaultMaxTempAppAllowlistDurationMs;
        private long mDefaultMinDeepMaintenanceTime;
        private long mDefaultMinLightMaintenanceTime;
        private long mDefaultMinTimeToAlarm;
        private long mDefaultMmsTempAppAllowlistDurationMs;
        private long mDefaultMotionInactiveTimeout;
        private long mDefaultMotionInactiveTimeoutFlex;
        private long mDefaultNotificationAllowlistDurationMs;
        private long mDefaultQuickDozeDelayTimeout;
        private long mDefaultSensingTimeout;
        private long mDefaultSmsTempAppAllowlistDurationMs;
        private boolean mDefaultUseModeManager;
        private boolean mDefaultUseWindowAlarms;
        private boolean mDefaultWaitForUnlock;
        private final android.content.ContentResolver mResolver;
        private final boolean mSmallBatteryDevice;
        private final com.android.server.utils.UserSettingDeviceConfigMediator mUserSettingDeviceConfigMediator;

        public Constants(android.os.Handler handler, android.content.ContentResolver contentResolver) {
            super(handler);
            this.mDefaultFlexTimeShort = 60000L;
            this.mDefaultLightIdleAfterInactiveTimeout = 240000L;
            this.mDefaultLightIdleTimeout = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.mDefaultLightIdleTimeoutInitialFlex = 60000L;
            this.mDefaultLightIdleTimeoutMaxFlex = 900000L;
            this.mDefaultLightIdleFactor = 2.0f;
            this.mDefaultLightIdleLinearIncreaseFactorMs = this.mDefaultLightIdleTimeout;
            this.mDefaultLightIdleFlexLinearIncreaseFactorMs = this.mDefaultLightIdleTimeoutInitialFlex;
            this.mDefaultLightMaxIdleTimeout = 900000L;
            this.mDefaultLightIdleMaintenanceMinBudget = 60000L;
            this.mDefaultLightIdleMaintenanceMaxBudget = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.mDefaultMinLightMaintenanceTime = 5000L;
            this.mDefaultMinDeepMaintenanceTime = 30000L;
            this.mDefaultInactiveTimeout = 1800000L;
            this.mDefaultSensingTimeout = 240000L;
            this.mDefaultLocatingTimeout = 30000L;
            this.mDefaultLocationAccuracy = 20.0f;
            this.mDefaultMotionInactiveTimeout = 600000L;
            this.mDefaultMotionInactiveTimeoutFlex = 60000L;
            this.mDefaultIdleAfterInactiveTimeout = 1800000L;
            this.mDefaultIdlePendingTimeout = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.mDefaultMaxIdlePendingTimeout = 600000L;
            this.mDefaultIdlePendingFactor = 2.0f;
            this.mDefaultQuickDozeDelayTimeout = 60000L;
            this.mDefaultIdleTimeout = 3600000L;
            this.mDefaultMaxIdleTimeout = 21600000L;
            this.mDefaultIdleFactor = 2.0f;
            this.mDefaultMinTimeToAlarm = 1800000L;
            this.mDefaultMaxTempAppAllowlistDurationMs = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.mDefaultMmsTempAppAllowlistDurationMs = 60000L;
            this.mDefaultSmsTempAppAllowlistDurationMs = 20000L;
            this.mDefaultNotificationAllowlistDurationMs = 30000L;
            this.mDefaultWaitForUnlock = true;
            this.mDefaultUseWindowAlarms = true;
            this.mDefaultUseModeManager = false;
            this.FLEX_TIME_SHORT = this.mDefaultFlexTimeShort;
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultLightIdleAfterInactiveTimeout;
            this.LIGHT_IDLE_TIMEOUT = this.mDefaultLightIdleTimeout;
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mDefaultLightIdleTimeoutInitialFlex;
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mDefaultLightIdleTimeoutMaxFlex;
            this.LIGHT_IDLE_FACTOR = this.mDefaultLightIdleFactor;
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mDefaultLightIdleIncreaseLinearly;
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleLinearIncreaseFactorMs;
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleFlexLinearIncreaseFactorMs;
            this.LIGHT_MAX_IDLE_TIMEOUT = this.mDefaultLightMaxIdleTimeout;
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = this.mDefaultLightIdleMaintenanceMinBudget;
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = this.mDefaultLightIdleMaintenanceMaxBudget;
            this.MIN_LIGHT_MAINTENANCE_TIME = this.mDefaultMinLightMaintenanceTime;
            this.MIN_DEEP_MAINTENANCE_TIME = this.mDefaultMinDeepMaintenanceTime;
            this.INACTIVE_TIMEOUT = this.mDefaultInactiveTimeout;
            this.SENSING_TIMEOUT = this.mDefaultSensingTimeout;
            this.LOCATING_TIMEOUT = this.mDefaultLocatingTimeout;
            this.LOCATION_ACCURACY = this.mDefaultLocationAccuracy;
            this.MOTION_INACTIVE_TIMEOUT = this.mDefaultMotionInactiveTimeout;
            this.MOTION_INACTIVE_TIMEOUT_FLEX = this.mDefaultMotionInactiveTimeoutFlex;
            this.IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultIdleAfterInactiveTimeout;
            this.IDLE_PENDING_TIMEOUT = this.mDefaultIdlePendingTimeout;
            this.MAX_IDLE_PENDING_TIMEOUT = this.mDefaultMaxIdlePendingTimeout;
            this.IDLE_PENDING_FACTOR = this.mDefaultIdlePendingFactor;
            this.QUICK_DOZE_DELAY_TIMEOUT = this.mDefaultQuickDozeDelayTimeout;
            this.IDLE_TIMEOUT = this.mDefaultIdleTimeout;
            this.MAX_IDLE_TIMEOUT = this.mDefaultMaxIdleTimeout;
            this.IDLE_FACTOR = this.mDefaultIdleFactor;
            this.MIN_TIME_TO_ALARM = this.mDefaultMinTimeToAlarm;
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMaxTempAppAllowlistDurationMs;
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMmsTempAppAllowlistDurationMs;
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultSmsTempAppAllowlistDurationMs;
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = this.mDefaultNotificationAllowlistDurationMs;
            this.WAIT_FOR_UNLOCK = this.mDefaultWaitForUnlock;
            this.USE_WINDOW_ALARMS = this.mDefaultUseWindowAlarms;
            this.USE_MODE_MANAGER = this.mDefaultUseModeManager;
            this.mUserSettingDeviceConfigMediator = new com.android.server.utils.UserSettingDeviceConfigMediator.SettingsOverridesIndividualMediator(',');
            this.mResolver = contentResolver;
            initDefault();
            this.mSmallBatteryDevice = android.app.ActivityManager.isSmallBatteryDevice();
            if (this.mSmallBatteryDevice) {
                this.INACTIVE_TIMEOUT = 60000L;
                this.IDLE_AFTER_INACTIVE_TIMEOUT = 60000L;
            }
            android.provider.DeviceConfig.addOnPropertiesChangedListener("device_idle", com.android.server.AppSchedulingModuleThread.getExecutor(), this);
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("device_idle_constants"), false, this);
            updateSettingsConstantLocked();
            this.mUserSettingDeviceConfigMediator.setDeviceConfigProperties(android.provider.DeviceConfig.getProperties("device_idle", new java.lang.String[0]));
            updateConstantsLocked();
        }

        private void initDefault() {
            android.content.res.Resources resources = com.android.server.DeviceIdleController.this.getContext().getResources();
            this.mDefaultFlexTimeShort = getTimeout(resources.getInteger(android.R.integer.date_picker_mode), this.mDefaultFlexTimeShort);
            this.mDefaultLightIdleAfterInactiveTimeout = getTimeout(resources.getInteger(android.R.integer.default_data_warning_level_mb), this.mDefaultLightIdleAfterInactiveTimeout);
            this.mDefaultLightIdleTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_inactive_to_ms), this.mDefaultLightIdleTimeout);
            this.mDefaultLightIdleTimeoutInitialFlex = getTimeout(resources.getInteger(android.R.integer.device_idle_idle_pending_to_ms), this.mDefaultLightIdleTimeoutInitialFlex);
            this.mDefaultLightIdleTimeoutMaxFlex = getTimeout(resources.getInteger(android.R.integer.device_idle_idle_to_ms), this.mDefaultLightIdleTimeoutMaxFlex);
            this.mDefaultLightIdleFactor = resources.getFloat(android.R.integer.default_reserved_data_coding_scheme);
            this.mDefaultLightIdleIncreaseLinearly = resources.getBoolean(android.R.bool.config_windowShowCircularMask);
            this.mDefaultLightIdleLinearIncreaseFactorMs = getTimeout(resources.getInteger(android.R.integer.device_idle_idle_after_inactive_to_ms), this.mDefaultLightIdleLinearIncreaseFactorMs);
            this.mDefaultLightIdleFlexLinearIncreaseFactorMs = getTimeout(resources.getInteger(android.R.integer.device_idle_flex_time_short_ms), this.mDefaultLightIdleFlexLinearIncreaseFactorMs);
            this.mDefaultLightMaxIdleTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_light_after_inactive_to_ms), this.mDefaultLightMaxIdleTimeout);
            this.mDefaultLightIdleMaintenanceMinBudget = getTimeout(resources.getInteger(android.R.integer.device_idle_idle_pending_factor), this.mDefaultLightIdleMaintenanceMinBudget);
            this.mDefaultLightIdleMaintenanceMaxBudget = getTimeout(resources.getInteger(android.R.integer.device_idle_idle_factor), this.mDefaultLightIdleMaintenanceMaxBudget);
            this.mDefaultMinLightMaintenanceTime = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_to_max_flex_ms), this.mDefaultMinLightMaintenanceTime);
            this.mDefaultMinDeepMaintenanceTime = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_to_init_flex_ms), this.mDefaultMinDeepMaintenanceTime);
            this.mDefaultInactiveTimeout = getTimeout(resources.getInteger(android.R.integer.db_wal_truncate_size), this.mDefaultInactiveTimeout);
            this.mDefaultSensingTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_max_temp_app_allowlist_duration_ms), this.mDefaultSensingTimeout);
            this.mDefaultLocatingTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_factor), this.mDefaultLocatingTimeout);
            this.mDefaultLocationAccuracy = resources.getFloat(android.R.integer.device_idle_light_idle_flex_linear_increase_factor_ms);
            this.mDefaultMotionInactiveTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_location_accuracy), this.mDefaultMotionInactiveTimeout);
            this.mDefaultMotionInactiveTimeoutFlex = getTimeout(resources.getInteger(android.R.integer.device_idle_locating_to_ms), this.mDefaultMotionInactiveTimeoutFlex);
            this.mDefaultIdleAfterInactiveTimeout = getTimeout(resources.getInteger(android.R.integer.date_picker_mode_material), this.mDefaultIdleAfterInactiveTimeout);
            this.mDefaultIdlePendingTimeout = getTimeout(resources.getInteger(android.R.integer.db_journal_size_limit), this.mDefaultIdlePendingTimeout);
            this.mDefaultMaxIdlePendingTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_linear_increase_factor_ms), this.mDefaultMaxIdlePendingTimeout);
            this.mDefaultIdlePendingFactor = resources.getFloat(android.R.integer.db_default_idle_connection_timeout);
            this.mDefaultQuickDozeDelayTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_max_idle_to_ms), this.mDefaultQuickDozeDelayTimeout);
            this.mDefaultIdleTimeout = getTimeout(resources.getInteger(android.R.integer.db_wal_autocheckpoint), this.mDefaultIdleTimeout);
            this.mDefaultMaxIdleTimeout = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_maintenance_max_budget_ms), this.mDefaultMaxIdleTimeout);
            this.mDefaultIdleFactor = resources.getFloat(android.R.integer.db_connection_pool_size);
            this.mDefaultMinTimeToAlarm = getTimeout(resources.getInteger(android.R.integer.device_idle_light_idle_to_ms), this.mDefaultMinTimeToAlarm);
            this.mDefaultMaxTempAppAllowlistDurationMs = resources.getInteger(android.R.integer.device_idle_light_idle_maintenance_min_budget_ms);
            this.mDefaultMmsTempAppAllowlistDurationMs = resources.getInteger(android.R.integer.device_idle_light_max_idle_to_ms);
            this.mDefaultSmsTempAppAllowlistDurationMs = resources.getInteger(android.R.integer.device_idle_min_deep_maintenance_time_ms);
            this.mDefaultNotificationAllowlistDurationMs = resources.getInteger(android.R.integer.device_idle_max_idle_pending_to_ms);
            this.mDefaultWaitForUnlock = resources.getBoolean(android.R.bool.config_zramWriteback);
            this.mDefaultUseWindowAlarms = resources.getBoolean(android.R.bool.config_wlan_data_service_conn_persistence_on_restart);
            this.mDefaultUseModeManager = resources.getBoolean(android.R.bool.config_wirelessConsentRequired);
            this.FLEX_TIME_SHORT = this.mDefaultFlexTimeShort;
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultLightIdleAfterInactiveTimeout;
            this.LIGHT_IDLE_TIMEOUT = this.mDefaultLightIdleTimeout;
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mDefaultLightIdleTimeoutInitialFlex;
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mDefaultLightIdleTimeoutMaxFlex;
            this.LIGHT_IDLE_FACTOR = this.mDefaultLightIdleFactor;
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mDefaultLightIdleIncreaseLinearly;
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleLinearIncreaseFactorMs;
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleFlexLinearIncreaseFactorMs;
            this.LIGHT_MAX_IDLE_TIMEOUT = this.mDefaultLightMaxIdleTimeout;
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = this.mDefaultLightIdleMaintenanceMinBudget;
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = this.mDefaultLightIdleMaintenanceMaxBudget;
            this.MIN_LIGHT_MAINTENANCE_TIME = this.mDefaultMinLightMaintenanceTime;
            this.MIN_DEEP_MAINTENANCE_TIME = this.mDefaultMinDeepMaintenanceTime;
            this.INACTIVE_TIMEOUT = this.mDefaultInactiveTimeout;
            this.SENSING_TIMEOUT = this.mDefaultSensingTimeout;
            this.LOCATING_TIMEOUT = this.mDefaultLocatingTimeout;
            this.LOCATION_ACCURACY = this.mDefaultLocationAccuracy;
            this.MOTION_INACTIVE_TIMEOUT = this.mDefaultMotionInactiveTimeout;
            this.MOTION_INACTIVE_TIMEOUT_FLEX = this.mDefaultMotionInactiveTimeoutFlex;
            this.IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultIdleAfterInactiveTimeout;
            this.IDLE_PENDING_TIMEOUT = this.mDefaultIdlePendingTimeout;
            this.MAX_IDLE_PENDING_TIMEOUT = this.mDefaultMaxIdlePendingTimeout;
            this.IDLE_PENDING_FACTOR = this.mDefaultIdlePendingFactor;
            this.QUICK_DOZE_DELAY_TIMEOUT = this.mDefaultQuickDozeDelayTimeout;
            this.IDLE_TIMEOUT = this.mDefaultIdleTimeout;
            this.MAX_IDLE_TIMEOUT = this.mDefaultMaxIdleTimeout;
            this.IDLE_FACTOR = this.mDefaultIdleFactor;
            this.MIN_TIME_TO_ALARM = this.mDefaultMinTimeToAlarm;
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMaxTempAppAllowlistDurationMs;
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMmsTempAppAllowlistDurationMs;
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultSmsTempAppAllowlistDurationMs;
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = this.mDefaultNotificationAllowlistDurationMs;
            this.WAIT_FOR_UNLOCK = this.mDefaultWaitForUnlock;
            this.USE_WINDOW_ALARMS = this.mDefaultUseWindowAlarms;
            this.USE_MODE_MANAGER = this.mDefaultUseModeManager;
        }

        private long getTimeout(long j, long j2) {
            return j;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            synchronized (com.android.server.DeviceIdleController.this) {
                updateSettingsConstantLocked();
                updateConstantsLocked();
            }
        }

        private void updateSettingsConstantLocked() {
            try {
                this.mUserSettingDeviceConfigMediator.setSettingsString(android.provider.Settings.Global.getString(this.mResolver, "device_idle_constants"));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(com.android.server.DeviceIdleController.TAG, "Bad device idle settings", e);
            }
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            synchronized (com.android.server.DeviceIdleController.this) {
                this.mUserSettingDeviceConfigMediator.setDeviceConfigProperties(properties);
                updateConstantsLocked();
            }
        }

        private void updateConstantsLocked() {
            long j;
            if (this.mSmallBatteryDevice) {
                return;
            }
            this.FLEX_TIME_SHORT = this.mUserSettingDeviceConfigMediator.getLong(KEY_FLEX_TIME_SHORT, this.mDefaultFlexTimeShort);
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, this.mDefaultLightIdleAfterInactiveTimeout);
            this.LIGHT_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_TIMEOUT, this.mDefaultLightIdleTimeout);
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_TIMEOUT_INITIAL_FLEX, this.mDefaultLightIdleTimeoutInitialFlex);
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_TIMEOUT_MAX_FLEX, this.mDefaultLightIdleTimeoutMaxFlex);
            this.LIGHT_IDLE_FACTOR = java.lang.Math.max(1.0f, this.mUserSettingDeviceConfigMediator.getFloat(KEY_LIGHT_IDLE_FACTOR, this.mDefaultLightIdleFactor));
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mUserSettingDeviceConfigMediator.getBoolean(KEY_LIGHT_IDLE_INCREASE_LINEARLY, this.mDefaultLightIdleIncreaseLinearly);
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS, this.mDefaultLightIdleLinearIncreaseFactorMs);
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS, this.mDefaultLightIdleFlexLinearIncreaseFactorMs);
            this.LIGHT_MAX_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_MAX_IDLE_TIMEOUT, this.mDefaultLightMaxIdleTimeout);
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_MAINTENANCE_MIN_BUDGET, this.mDefaultLightIdleMaintenanceMinBudget);
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = this.mUserSettingDeviceConfigMediator.getLong(KEY_LIGHT_IDLE_MAINTENANCE_MAX_BUDGET, this.mDefaultLightIdleMaintenanceMaxBudget);
            this.MIN_LIGHT_MAINTENANCE_TIME = this.mUserSettingDeviceConfigMediator.getLong(KEY_MIN_LIGHT_MAINTENANCE_TIME, this.mDefaultMinLightMaintenanceTime);
            this.MIN_DEEP_MAINTENANCE_TIME = this.mUserSettingDeviceConfigMediator.getLong(KEY_MIN_DEEP_MAINTENANCE_TIME, this.mDefaultMinDeepMaintenanceTime);
            if (this.mSmallBatteryDevice) {
                j = 60000;
            } else {
                j = this.mDefaultInactiveTimeout;
            }
            this.INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_INACTIVE_TIMEOUT, j);
            this.SENSING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_SENSING_TIMEOUT, this.mDefaultSensingTimeout);
            this.LOCATING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_LOCATING_TIMEOUT, this.mDefaultLocatingTimeout);
            this.LOCATION_ACCURACY = this.mUserSettingDeviceConfigMediator.getFloat(KEY_LOCATION_ACCURACY, this.mDefaultLocationAccuracy);
            this.MOTION_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_MOTION_INACTIVE_TIMEOUT, this.mDefaultMotionInactiveTimeout);
            this.MOTION_INACTIVE_TIMEOUT_FLEX = this.mUserSettingDeviceConfigMediator.getLong(KEY_MOTION_INACTIVE_TIMEOUT_FLEX, this.mDefaultMotionInactiveTimeoutFlex);
            this.IDLE_AFTER_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_IDLE_AFTER_INACTIVE_TIMEOUT, this.mSmallBatteryDevice ? 60000L : this.mDefaultIdleAfterInactiveTimeout);
            this.IDLE_PENDING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_IDLE_PENDING_TIMEOUT, this.mDefaultIdlePendingTimeout);
            this.MAX_IDLE_PENDING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_MAX_IDLE_PENDING_TIMEOUT, this.mDefaultMaxIdlePendingTimeout);
            this.IDLE_PENDING_FACTOR = this.mUserSettingDeviceConfigMediator.getFloat(KEY_IDLE_PENDING_FACTOR, this.mDefaultIdlePendingFactor);
            this.QUICK_DOZE_DELAY_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_QUICK_DOZE_DELAY_TIMEOUT, this.mDefaultQuickDozeDelayTimeout);
            this.IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_IDLE_TIMEOUT, this.mDefaultIdleTimeout);
            this.MAX_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(KEY_MAX_IDLE_TIMEOUT, this.mDefaultMaxIdleTimeout);
            this.IDLE_FACTOR = this.mUserSettingDeviceConfigMediator.getFloat(KEY_IDLE_FACTOR, this.mDefaultIdleFactor);
            this.MIN_TIME_TO_ALARM = this.mUserSettingDeviceConfigMediator.getLong(KEY_MIN_TIME_TO_ALARM, this.mDefaultMinTimeToAlarm);
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_MAX_TEMP_APP_ALLOWLIST_DURATION_MS, this.mDefaultMaxTempAppAllowlistDurationMs);
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_MMS_TEMP_APP_ALLOWLIST_DURATION_MS, this.mDefaultMmsTempAppAllowlistDurationMs);
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_SMS_TEMP_APP_ALLOWLIST_DURATION_MS, this.mDefaultSmsTempAppAllowlistDurationMs);
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(KEY_NOTIFICATION_ALLOWLIST_DURATION_MS, this.mDefaultNotificationAllowlistDurationMs);
            this.WAIT_FOR_UNLOCK = this.mUserSettingDeviceConfigMediator.getBoolean(KEY_WAIT_FOR_UNLOCK, this.mDefaultWaitForUnlock);
            this.USE_WINDOW_ALARMS = this.mUserSettingDeviceConfigMediator.getBoolean(KEY_USE_WINDOW_ALARMS, this.mDefaultUseWindowAlarms);
            this.USE_MODE_MANAGER = this.mUserSettingDeviceConfigMediator.getBoolean(KEY_USE_MODE_MANAGER, this.mDefaultUseModeManager);
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  Settings:");
            printWriter.print("    ");
            printWriter.print(KEY_FLEX_TIME_SHORT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.FLEX_TIME_SHORT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_TIMEOUT_INITIAL_FLEX);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_TIMEOUT_MAX_FLEX);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT_MAX_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_FACTOR);
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_FACTOR);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_INCREASE_LINEARLY);
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_INCREASE_LINEARLY);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS);
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS);
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_MAX_IDLE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_MAX_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_MAINTENANCE_MIN_BUDGET);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LIGHT_IDLE_MAINTENANCE_MAX_BUDGET);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MIN_LIGHT_MAINTENANCE_TIME);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_LIGHT_MAINTENANCE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MIN_DEEP_MAINTENANCE_TIME);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_DEEP_MAINTENANCE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_INACTIVE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_SENSING_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.SENSING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LOCATING_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LOCATING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_LOCATION_ACCURACY);
            printWriter.print("=");
            printWriter.print(this.LOCATION_ACCURACY);
            printWriter.print("m");
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MOTION_INACTIVE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MOTION_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MOTION_INACTIVE_TIMEOUT_FLEX);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MOTION_INACTIVE_TIMEOUT_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_IDLE_AFTER_INACTIVE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.IDLE_AFTER_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_IDLE_PENDING_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.IDLE_PENDING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MAX_IDLE_PENDING_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MAX_IDLE_PENDING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_IDLE_PENDING_FACTOR);
            printWriter.print("=");
            printWriter.println(this.IDLE_PENDING_FACTOR);
            printWriter.print("    ");
            printWriter.print(KEY_QUICK_DOZE_DELAY_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.QUICK_DOZE_DELAY_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_IDLE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MAX_IDLE_TIMEOUT);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MAX_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_IDLE_FACTOR);
            printWriter.print("=");
            printWriter.println(this.IDLE_FACTOR);
            printWriter.print("    ");
            printWriter.print(KEY_MIN_TIME_TO_ALARM);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_TIME_TO_ALARM, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MAX_TEMP_APP_ALLOWLIST_DURATION_MS);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_MMS_TEMP_APP_ALLOWLIST_DURATION_MS);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_SMS_TEMP_APP_ALLOWLIST_DURATION_MS);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_NOTIFICATION_ALLOWLIST_DURATION_MS);
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.NOTIFICATION_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print(KEY_WAIT_FOR_UNLOCK);
            printWriter.print("=");
            printWriter.println(this.WAIT_FOR_UNLOCK);
            printWriter.print("    ");
            printWriter.print(KEY_USE_WINDOW_ALARMS);
            printWriter.print("=");
            printWriter.println(this.USE_WINDOW_ALARMS);
            printWriter.print("    ");
            printWriter.print(KEY_USE_MODE_MANAGER);
            printWriter.print("=");
            printWriter.println(this.USE_MODE_MANAGER);
        }
    }

    @Override // com.android.server.AnyMotionDetector.DeviceIdleCallback
    public void onAnyMotionResult(int i) {
        synchronized (this) {
            if (i != -1) {
                try {
                    cancelSensingTimeoutAlarmLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (i == 1 || i == -1) {
                handleMotionDetectedLocked(this.mConstants.INACTIVE_TIMEOUT, "non_stationary");
            } else if (i == 0) {
                if (this.mState == 3) {
                    this.mNotMoving = true;
                    stepIdleStateLocked("s:stationary");
                } else if (this.mState == 4) {
                    this.mNotMoving = true;
                    if (this.mLocated) {
                        stepIdleStateLocked("s:stationary");
                    }
                }
            }
        }
    }

    final class MyHandler extends android.os.Handler {
        MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            boolean deviceIdleMode;
            boolean lightDeviceIdleMode;
            boolean isStationaryLocked;
            com.android.server.DeviceIdleInternal.StationaryListener[] stationaryListenerArr;
            com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener[] tempAllowlistChangeListenerArr;
            int i = 0;
            switch (message.what) {
                case 1:
                    com.android.server.DeviceIdleController.this.handleWriteConfigFile();
                    return;
                case 2:
                case 3:
                    com.android.server.EventLogTags.writeDeviceIdleOnStart();
                    if (message.what == 2) {
                        deviceIdleMode = com.android.server.DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(true);
                        lightDeviceIdleMode = com.android.server.DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    } else {
                        deviceIdleMode = com.android.server.DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                        lightDeviceIdleMode = com.android.server.DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(true);
                    }
                    try {
                        com.android.server.DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(true);
                        com.android.server.DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(message.what == 2 ? 2 : 1, (java.lang.String) null, android.os.Process.myUid());
                    } catch (android.os.RemoteException e) {
                    }
                    if (deviceIdleMode) {
                        com.android.server.DeviceIdleController.this.getContext().sendBroadcastAsUser(com.android.server.DeviceIdleController.this.mIdleIntent, android.os.UserHandle.ALL, null, com.android.server.DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode) {
                        com.android.server.DeviceIdleController.this.getContext().sendBroadcastAsUser(com.android.server.DeviceIdleController.this.mLightIdleIntent, android.os.UserHandle.ALL, null, com.android.server.DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    com.android.server.EventLogTags.writeDeviceIdleOnComplete();
                    com.android.server.DeviceIdleController.this.mGoingIdleWakeLock.release();
                    return;
                case 4:
                    com.android.server.EventLogTags.writeDeviceIdleOffStart("unknown");
                    boolean deviceIdleMode2 = com.android.server.DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode2 = com.android.server.DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        com.android.server.DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        com.android.server.DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, (java.lang.String) null, android.os.Process.myUid());
                    } catch (android.os.RemoteException e2) {
                    }
                    if (deviceIdleMode2) {
                        com.android.server.DeviceIdleController.this.incActiveIdleOps();
                        com.android.server.DeviceIdleController.this.mLocalActivityManager.broadcastIntentWithCallback(com.android.server.DeviceIdleController.this.mIdleIntent, com.android.server.DeviceIdleController.this.mIdleStartedDoneReceiver, (java.lang.String[]) null, -1, (int[]) null, (java.util.function.BiFunction) null, com.android.server.DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode2) {
                        com.android.server.DeviceIdleController.this.incActiveIdleOps();
                        com.android.server.DeviceIdleController.this.mLocalActivityManager.broadcastIntentWithCallback(com.android.server.DeviceIdleController.this.mLightIdleIntent, com.android.server.DeviceIdleController.this.mIdleStartedDoneReceiver, (java.lang.String[]) null, -1, (int[]) null, (java.util.function.BiFunction) null, com.android.server.DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    com.android.server.DeviceIdleController.this.decActiveIdleOps();
                    com.android.server.EventLogTags.writeDeviceIdleOffComplete();
                    return;
                case 5:
                    java.lang.String str = (java.lang.String) message.obj;
                    int i2 = message.arg1;
                    com.android.server.EventLogTags.writeDeviceIdleOffStart(str != null ? str : "unknown");
                    boolean deviceIdleMode3 = com.android.server.DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode3 = com.android.server.DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        com.android.server.DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        com.android.server.DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, str, i2);
                    } catch (android.os.RemoteException e3) {
                    }
                    if (deviceIdleMode3) {
                        com.android.server.DeviceIdleController.this.getContext().sendBroadcastAsUser(com.android.server.DeviceIdleController.this.mIdleIntent, android.os.UserHandle.ALL, null, com.android.server.DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode3) {
                        com.android.server.DeviceIdleController.this.getContext().sendBroadcastAsUser(com.android.server.DeviceIdleController.this.mLightIdleIntent, android.os.UserHandle.ALL, null, com.android.server.DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    com.android.server.EventLogTags.writeDeviceIdleOffComplete();
                    return;
                case 6:
                    com.android.server.DeviceIdleController.this.checkTempAppWhitelistTimeout(message.arg1);
                    return;
                case 7:
                    com.android.server.DeviceIdleInternal.StationaryListener stationaryListener = (com.android.server.DeviceIdleInternal.StationaryListener) message.obj;
                    synchronized (com.android.server.DeviceIdleController.this) {
                        try {
                            isStationaryLocked = com.android.server.DeviceIdleController.this.isStationaryLocked();
                            stationaryListenerArr = stationaryListener == null ? (com.android.server.DeviceIdleInternal.StationaryListener[]) com.android.server.DeviceIdleController.this.mStationaryListeners.toArray(new com.android.server.DeviceIdleInternal.StationaryListener[com.android.server.DeviceIdleController.this.mStationaryListeners.size()]) : null;
                        } finally {
                        }
                    }
                    if (stationaryListenerArr != null) {
                        int length = stationaryListenerArr.length;
                        while (i < length) {
                            stationaryListenerArr[i].onDeviceStationaryChanged(isStationaryLocked);
                            i++;
                        }
                    }
                    if (stationaryListener != null) {
                        stationaryListener.onDeviceStationaryChanged(isStationaryLocked);
                        return;
                    }
                    return;
                case 8:
                    com.android.server.DeviceIdleController.this.decActiveIdleOps();
                    return;
                case 9:
                case 11:
                case 12:
                default:
                    return;
                case 10:
                    com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint = (com.android.server.deviceidle.IDeviceIdleConstraint) message.obj;
                    if ((message.arg1 != 1 ? 0 : 1) != 0) {
                        iDeviceIdleConstraint.startMonitoring();
                        return;
                    } else {
                        iDeviceIdleConstraint.stopMonitoring();
                        return;
                    }
                case 13:
                    int i3 = message.arg1;
                    int i4 = message.arg2 != 1 ? 0 : 1;
                    synchronized (com.android.server.DeviceIdleController.this) {
                        tempAllowlistChangeListenerArr = (com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener[]) com.android.server.DeviceIdleController.this.mTempAllowlistChangeListeners.toArray(new com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener[com.android.server.DeviceIdleController.this.mTempAllowlistChangeListeners.size()]);
                    }
                    int length2 = tempAllowlistChangeListenerArr.length;
                    while (i < length2) {
                        com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener = tempAllowlistChangeListenerArr[i];
                        if (i4 != 0) {
                            tempAllowlistChangeListener.onAppAdded(i3);
                        } else {
                            tempAllowlistChangeListener.onAppRemoved(i3);
                        }
                        i++;
                    }
                    return;
                case 14:
                    com.android.server.DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, true, message.arg2, (java.lang.String) message.obj);
                    return;
                case 15:
                    com.android.server.DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, false, 0, null);
                    return;
            }
        }
    }

    private final class BinderService extends android.os.IDeviceIdleController.Stub {
        private BinderService() {
        }

        public void addPowerSaveWhitelistApp(java.lang.String str) {
            addPowerSaveWhitelistApps(java.util.Collections.singletonList(str));
        }

        public int addPowerSaveWhitelistApps(java.util.List<java.lang.String> list) {
            com.android.server.DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.DeviceIdleController.this.addPowerSaveWhitelistAppsInternal(list);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removePowerSaveWhitelistApp(java.lang.String str) {
            com.android.server.DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.DeviceIdleController.this.removePowerSaveWhitelistAppInternal(str) && com.android.server.DeviceIdleController.this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str)) {
                    throw new java.lang.UnsupportedOperationException("Cannot remove system whitelisted app");
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeSystemPowerWhitelistApp(java.lang.String str) {
            com.android.server.DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.DeviceIdleController.this.removeSystemPowerWhitelistAppInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void restoreSystemPowerWhitelistApp(java.lang.String str) {
            com.android.server.DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.DeviceIdleController.this.restoreSystemPowerWhitelistAppInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.lang.String[] getRemovedSystemPowerWhitelistApps() {
            return com.android.server.DeviceIdleController.this.getRemovedSystemPowerWhitelistAppsInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public java.lang.String[] getSystemPowerWhitelistExceptIdle() {
            return com.android.server.DeviceIdleController.this.getSystemPowerWhitelistExceptIdleInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public java.lang.String[] getSystemPowerWhitelist() {
            return com.android.server.DeviceIdleController.this.getSystemPowerWhitelistInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public java.lang.String[] getUserPowerWhitelist() {
            return com.android.server.DeviceIdleController.this.getUserPowerWhitelistInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public java.lang.String[] getFullPowerWhitelistExceptIdle() {
            return com.android.server.DeviceIdleController.this.getFullPowerWhitelistExceptIdleInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public java.lang.String[] getFullPowerWhitelist() {
            return com.android.server.DeviceIdleController.this.getFullPowerWhitelistInternal(android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
        }

        public int[] getAppIdWhitelistExceptIdle() {
            return com.android.server.DeviceIdleController.this.getAppIdWhitelistExceptIdleInternal();
        }

        public int[] getAppIdWhitelist() {
            return com.android.server.DeviceIdleController.this.getAppIdWhitelistInternal();
        }

        public int[] getAppIdUserWhitelist() {
            return com.android.server.DeviceIdleController.this.getAppIdUserWhitelistInternal();
        }

        public int[] getAppIdTempWhitelist() {
            return com.android.server.DeviceIdleController.this.getAppIdTempWhitelistInternal();
        }

        public boolean isPowerSaveWhitelistExceptIdleApp(java.lang.String str) {
            if (com.android.server.DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(str, android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId())) {
                return false;
            }
            return com.android.server.DeviceIdleController.this.isPowerSaveWhitelistExceptIdleAppInternal(str);
        }

        public boolean isPowerSaveWhitelistApp(java.lang.String str) {
            if (com.android.server.DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(str, android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId())) {
                return false;
            }
            return com.android.server.DeviceIdleController.this.isPowerSaveWhitelistAppInternal(str);
        }

        public long whitelistAppTemporarily(java.lang.String str, int i, int i2, @android.annotation.Nullable java.lang.String str2) throws android.os.RemoteException {
            long max = java.lang.Math.max(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, com.android.server.DeviceIdleController.this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS / 2);
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, max, i, i2, str2);
            return max;
        }

        public void addPowerSaveTempWhitelistApp(java.lang.String str, long j, int i, int i2, @android.annotation.Nullable java.lang.String str2) throws android.os.RemoteException {
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
        }

        public long addPowerSaveTempWhitelistAppForMms(java.lang.String str, int i, int i2, @android.annotation.Nullable java.lang.String str2) throws android.os.RemoteException {
            long j = com.android.server.DeviceIdleController.this.mConstants.MMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
            return j;
        }

        public long addPowerSaveTempWhitelistAppForSms(java.lang.String str, int i, int i2, @android.annotation.Nullable java.lang.String str2) throws android.os.RemoteException {
            long j = com.android.server.DeviceIdleController.this.mConstants.SMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
            return j;
        }

        @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
        public void exitIdle(java.lang.String str) {
            exitIdle_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.DeviceIdleController.this.exitIdleInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            com.android.server.DeviceIdleController.this.dump(fileDescriptor, printWriter, strArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            com.android.server.DeviceIdleController.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    private class LocalService implements com.android.server.DeviceIdleInternal {
        private LocalService() {
        }

        public void onConstraintStateChanged(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
            synchronized (com.android.server.DeviceIdleController.this) {
                com.android.server.DeviceIdleController.this.onConstraintStateChangedLocked(iDeviceIdleConstraint, z);
            }
        }

        public void registerDeviceIdleConstraint(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, java.lang.String str, int i) {
            com.android.server.DeviceIdleController.this.registerDeviceIdleConstraintInternal(iDeviceIdleConstraint, str, i);
        }

        public void unregisterDeviceIdleConstraint(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint) {
            com.android.server.DeviceIdleController.this.unregisterDeviceIdleConstraintInternal(iDeviceIdleConstraint);
        }

        public void exitIdle(java.lang.String str) {
            com.android.server.DeviceIdleController.this.exitIdleInternal(str);
        }

        public void addPowerSaveTempWhitelistApp(int i, java.lang.String str, long j, int i2, boolean z, int i3, @android.annotation.Nullable java.lang.String str2) {
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, 0, i2, z, i3, str2);
        }

        public void addPowerSaveTempWhitelistApp(int i, java.lang.String str, long j, int i2, int i3, boolean z, int i4, @android.annotation.Nullable java.lang.String str2) {
            com.android.server.DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, i2, i3, z, i4, str2);
        }

        public void addPowerSaveTempWhitelistAppDirect(int i, long j, int i2, boolean z, int i3, @android.annotation.Nullable java.lang.String str, int i4) {
            com.android.server.DeviceIdleController.this.addPowerSaveTempWhitelistAppDirectInternal(i4, i, j, i2, z, i3, str);
        }

        public long getNotificationAllowlistDuration() {
            return com.android.server.DeviceIdleController.this.mConstants.NOTIFICATION_ALLOWLIST_DURATION_MS;
        }

        public void setJobsActive(boolean z) {
            com.android.server.DeviceIdleController.this.setJobsActive(z);
        }

        public void setAlarmsActive(boolean z) {
            com.android.server.DeviceIdleController.this.setAlarmsActive(z);
        }

        public boolean isAppOnWhitelist(int i) {
            return com.android.server.DeviceIdleController.this.isAppOnWhitelistInternal(i);
        }

        public java.lang.String[] getFullPowerWhitelistExceptIdle() {
            return com.android.server.DeviceIdleController.this.getFullPowerWhitelistInternalUnchecked();
        }

        public int[] getPowerSaveWhitelistSystemAppIds() {
            return com.android.server.DeviceIdleController.this.getPowerSaveWhitelistSystemAppIds();
        }

        public int[] getPowerSaveWhitelistUserAppIds() {
            return com.android.server.DeviceIdleController.this.getPowerSaveWhitelistUserAppIds();
        }

        public int[] getPowerSaveTempWhitelistAppIds() {
            return com.android.server.DeviceIdleController.this.getAppIdTempWhitelistInternal();
        }

        public void registerStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
            com.android.server.DeviceIdleController.this.registerStationaryListener(stationaryListener);
        }

        public void unregisterStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
            com.android.server.DeviceIdleController.this.unregisterStationaryListener(stationaryListener);
        }

        public int getTempAllowListType(int i, int i2) {
            return com.android.server.DeviceIdleController.this.getTempAllowListType(i, i2);
        }
    }

    private class LocalPowerAllowlistService implements com.android.server.PowerAllowlistInternal {
        private LocalPowerAllowlistService() {
        }

        public void registerTempAllowlistChangeListener(@android.annotation.NonNull com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            com.android.server.DeviceIdleController.this.registerTempAllowlistChangeListener(tempAllowlistChangeListener);
        }

        public void unregisterTempAllowlistChangeListener(@android.annotation.NonNull com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            com.android.server.DeviceIdleController.this.unregisterTempAllowlistChangeListener(tempAllowlistChangeListener);
        }
    }

    private class EmergencyCallListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.OutgoingEmergencyCallListener, android.telephony.TelephonyCallback.CallStateListener {
        private volatile boolean mIsEmergencyCallActive;

        private EmergencyCallListener() {
        }

        public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) {
            this.mIsEmergencyCallActive = true;
            synchronized (com.android.server.DeviceIdleController.this) {
                com.android.server.DeviceIdleController.this.mActiveReason = 8;
                com.android.server.DeviceIdleController.this.becomeActiveLocked("emergency call", android.os.Process.myUid());
            }
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            if (i == 0 && this.mIsEmergencyCallActive) {
                this.mIsEmergencyCallActive = false;
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.becomeInactiveIfAppropriateLocked();
                }
            }
        }

        boolean isEmergencyCallActive() {
            return this.mIsEmergencyCallActive;
        }
    }

    static class Injector {
        private android.net.ConnectivityManager mConnectivityManager;
        private com.android.server.DeviceIdleController.Constants mConstants;
        private final android.content.Context mContext;
        private android.location.LocationManager mLocationManager;

        Injector(android.content.Context context) {
            this.mContext = context.createAttributionContext(com.android.server.DeviceIdleController.TAG);
        }

        android.app.AlarmManager getAlarmManager() {
            return (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        }

        com.android.server.AnyMotionDetector getAnyMotionDetector(android.os.Handler handler, android.hardware.SensorManager sensorManager, com.android.server.AnyMotionDetector.DeviceIdleCallback deviceIdleCallback, float f) {
            return new com.android.server.AnyMotionDetector(getPowerManager(), handler, sensorManager, deviceIdleCallback, f);
        }

        com.android.server.AppStateTrackerImpl getAppStateTracker(android.content.Context context, android.os.Looper looper) {
            return new com.android.server.AppStateTrackerImpl(context, looper);
        }

        android.net.ConnectivityManager getConnectivityManager() {
            if (this.mConnectivityManager == null) {
                this.mConnectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
            }
            return this.mConnectivityManager;
        }

        com.android.server.DeviceIdleController.Constants getConstants(com.android.server.DeviceIdleController deviceIdleController, android.os.Handler handler, android.content.ContentResolver contentResolver) {
            if (this.mConstants == null) {
                java.util.Objects.requireNonNull(deviceIdleController);
                this.mConstants = deviceIdleController.new Constants(handler, contentResolver);
            }
            return this.mConstants;
        }

        long getElapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        android.location.LocationManager getLocationManager() {
            if (this.mLocationManager == null) {
                this.mLocationManager = (android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class);
            }
            return this.mLocationManager;
        }

        com.android.server.DeviceIdleController.MyHandler getHandler(com.android.server.DeviceIdleController deviceIdleController) {
            java.util.Objects.requireNonNull(deviceIdleController);
            return deviceIdleController.new MyHandler(com.android.server.AppSchedulingModuleThread.getHandler().getLooper());
        }

        android.hardware.Sensor getMotionSensor() {
            android.hardware.Sensor sensor;
            android.hardware.SensorManager sensorManager = getSensorManager();
            int integer = this.mContext.getResources().getInteger(android.R.integer.config_autoPowerModeAnyMotionSensor);
            if (integer <= 0) {
                sensor = null;
            } else {
                sensor = sensorManager.getDefaultSensor(integer, true);
            }
            if (sensor == null && this.mContext.getResources().getBoolean(android.R.bool.config_autoPowerModePreferWristTilt)) {
                sensor = sensorManager.getDefaultSensor(26, true);
            }
            if (sensor == null) {
                return sensorManager.getDefaultSensor(17, true);
            }
            return sensor;
        }

        android.os.PowerManager getPowerManager() {
            return (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        }

        android.hardware.SensorManager getSensorManager() {
            return (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        }

        android.telephony.TelephonyManager getTelephonyManager() {
            return (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        }

        com.android.server.deviceidle.ConstraintController getConstraintController(android.os.Handler handler, com.android.server.DeviceIdleInternal deviceIdleInternal) {
            if (this.mContext.getPackageManager().hasSystemFeature("android.software.leanback_only")) {
                return new com.android.server.deviceidle.TvConstraintController(this.mContext, handler);
            }
            return null;
        }

        boolean isLocationPrefetchEnabled() {
            return this.mContext.getResources().getBoolean(android.R.bool.config_autoPowerModePrefetchLocation);
        }

        boolean useMotionSensor() {
            return this.mContext.getResources().getBoolean(android.R.bool.config_autoPowerModeUseMotionSensor);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    DeviceIdleController(android.content.Context context, com.android.server.DeviceIdleController.Injector injector) {
        super(context);
        this.mNumBlockingConstraints = 0;
        this.mConstraints = new android.util.ArrayMap<>();
        this.mPowerSaveWhitelistAppsExceptIdle = new android.util.ArrayMap<>();
        this.mPowerSaveWhitelistUserAppsExceptIdle = new android.util.ArraySet<>();
        this.mPowerSaveWhitelistApps = new android.util.ArrayMap<>();
        this.mPowerSaveWhitelistUserApps = new android.util.ArrayMap<>();
        this.mPowerSaveWhitelistSystemAppIdsExceptIdle = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistSystemAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistSystemAppIdArray = new int[0];
        this.mPowerSaveWhitelistExceptIdleAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistExceptIdleAppIdArray = new int[0];
        this.mPowerSaveWhitelistAllAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistAllAppIdArray = new int[0];
        this.mPowerSaveWhitelistUserAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistUserAppIdArray = new int[0];
        this.mTempWhitelistAppIdEndTimes = new android.util.SparseArray<>();
        this.mTempWhitelistAppIdArray = new int[0];
        this.mRemovedFromSystemWhitelistApps = new android.util.ArrayMap<>();
        this.mStationaryListeners = new android.util.ArraySet<>();
        this.mTempAllowlistChangeListeners = new android.util.ArraySet<>();
        this.mEventCmds = new int[100];
        this.mEventTimes = new long[100];
        this.mEventReasons = new java.lang.String[100];
        this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.DeviceIdleController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                android.net.Uri data;
                java.lang.String schemeSpecificPart;
                java.lang.String action = intent.getAction();
                boolean z = true;
                switch (action.hashCode()) {
                    case -1538406691:
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1172645946:
                        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            c = 2;
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
                        com.android.server.DeviceIdleController.this.updateConnectivityState(intent);
                        return;
                    case 1:
                        boolean booleanExtra = intent.getBooleanExtra("present", true);
                        boolean z2 = intent.getIntExtra("plugged", 0) != 0;
                        synchronized (com.android.server.DeviceIdleController.this) {
                            com.android.server.DeviceIdleController deviceIdleController = com.android.server.DeviceIdleController.this;
                            if (!booleanExtra || !z2) {
                                z = false;
                            }
                            deviceIdleController.updateChargingLocked(z);
                        }
                        return;
                    case 2:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false) && (data = intent.getData()) != null && (schemeSpecificPart = data.getSchemeSpecificPart()) != null) {
                            com.android.server.DeviceIdleController.this.removePowerSaveWhitelistAppInternal(schemeSpecificPart);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mLightAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.DeviceIdleController.this.lambda$new$0();
            }
        };
        this.mMotionRegistrationAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda3
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.DeviceIdleController.this.lambda$new$1();
            }
        };
        this.mMotionTimeoutAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda4
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.DeviceIdleController.this.lambda$new$2();
            }
        };
        this.mSensingTimeoutAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController.2
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                synchronized (com.android.server.DeviceIdleController.this) {
                    try {
                        if (com.android.server.DeviceIdleController.this.mState == 3) {
                            com.android.server.DeviceIdleController.this.becomeInactiveIfAppropriateLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mDeepAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController.3
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.stepIdleStateLocked("s:alarm");
                }
            }
        };
        this.mIdleStartedDoneReceiver = new android.content.IIntentReceiver.Stub() { // from class: com.android.server.DeviceIdleController.4
            public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
                if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(intent.getAction())) {
                    com.android.server.DeviceIdleController.this.mHandler.sendEmptyMessageDelayed(8, com.android.server.DeviceIdleController.this.mConstants.MIN_DEEP_MAINTENANCE_TIME);
                } else {
                    com.android.server.DeviceIdleController.this.mHandler.sendEmptyMessageDelayed(8, com.android.server.DeviceIdleController.this.mConstants.MIN_LIGHT_MAINTENANCE_TIME);
                }
            }
        };
        this.mInteractivityReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.DeviceIdleController.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.updateInteractivityLocked();
                }
            }
        };
        this.mEmergencyCallListener = new com.android.server.DeviceIdleController.EmergencyCallListener();
        this.mModeManagerQuickDozeRequestConsumer = new com.android.server.DeviceIdleController.ModeManagerQuickDozeRequestConsumer();
        this.mModeManagerOffBodyStateConsumer = new com.android.server.DeviceIdleController.ModeManagerOffBodyStateConsumer();
        this.mMotionListener = new com.android.server.DeviceIdleController.MotionListener();
        this.mGenericLocationListener = new android.location.LocationListener() { // from class: com.android.server.DeviceIdleController.6
            @Override // android.location.LocationListener
            public void onLocationChanged(android.location.Location location) {
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.receivedGenericLocationLocked(location);
                }
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(java.lang.String str, int i, android.os.Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(java.lang.String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderDisabled(java.lang.String str) {
            }
        };
        this.mGpsLocationListener = new android.location.LocationListener() { // from class: com.android.server.DeviceIdleController.7
            @Override // android.location.LocationListener
            public void onLocationChanged(android.location.Location location) {
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.receivedGpsLocationLocked(location);
                }
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(java.lang.String str, int i, android.os.Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(java.lang.String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderDisabled(java.lang.String str) {
            }
        };
        this.mScreenObserver = new com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.DeviceIdleController.8
            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public void onAwakeStateChanged(boolean z) {
            }

            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public void onKeyguardStateChanged(boolean z) {
                synchronized (com.android.server.DeviceIdleController.this) {
                    com.android.server.DeviceIdleController.this.keyguardShowingLocked(z);
                }
            }
        };
        this.mInjector = injector;
        this.mConfigFile = new android.util.AtomicFile(new java.io.File(getSystemDir(), "deviceidle.xml"));
        this.mHandler = this.mInjector.getHandler(this);
        this.mAppStateTracker = this.mInjector.getAppStateTracker(context, com.android.server.AppSchedulingModuleThread.get().getLooper());
        com.android.server.LocalServices.addService(com.android.server.AppStateTracker.class, this.mAppStateTracker);
        this.mIsLocationPrefetchEnabled = this.mInjector.isLocationPrefetchEnabled();
        this.mUseMotionSensor = this.mInjector.useMotionSensor();
    }

    public DeviceIdleController(android.content.Context context) {
        this(context, new com.android.server.DeviceIdleController.Injector(context));
    }

    boolean isAppOnWhitelistInternal(int i) {
        boolean z;
        synchronized (this) {
            z = java.util.Arrays.binarySearch(this.mPowerSaveWhitelistAllAppIdArray, i) >= 0;
        }
        return z;
    }

    int[] getPowerSaveWhitelistSystemAppIds() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistSystemAppIdArray;
        }
        return iArr;
    }

    int[] getPowerSaveWhitelistUserAppIds() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistUserAppIdArray;
        }
        return iArr;
    }

    private static java.io.File getSystemDir() {
        return new java.io.File(android.os.Environment.getDataDirectory(), "system");
    }

    private static int[] keysToIntArray(android.util.SparseBooleanArray sparseBooleanArray) {
        int size = sparseBooleanArray.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = sparseBooleanArray.keyAt(i);
        }
        return iArr;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        synchronized (this) {
            boolean z = getContext().getResources().getBoolean(android.R.bool.config_enableAppWidgetService);
            this.mDeepEnabled = z;
            this.mLightEnabled = z;
            com.android.server.SystemConfig systemConfig = com.android.server.SystemConfig.getInstance();
            android.util.ArraySet<java.lang.String> allowInPowerSaveExceptIdle = systemConfig.getAllowInPowerSaveExceptIdle();
            for (int i = 0; i < allowInPowerSaveExceptIdle.size(); i++) {
                try {
                    android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(allowInPowerSaveExceptIdle.valueAt(i), 1048576);
                    int appId = android.os.UserHandle.getAppId(applicationInfo.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo.packageName, java.lang.Integer.valueOf(appId));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId, true);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
            android.util.ArraySet<java.lang.String> allowInPowerSave = systemConfig.getAllowInPowerSave();
            for (int i2 = 0; i2 < allowInPowerSave.size(); i2++) {
                try {
                    android.content.pm.ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo(allowInPowerSave.valueAt(i2), 1048576);
                    int appId2 = android.os.UserHandle.getAppId(applicationInfo2.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo2.packageName, java.lang.Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId2, true);
                    this.mPowerSaveWhitelistApps.put(applicationInfo2.packageName, java.lang.Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIds.put(appId2, true);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
            }
            this.mPowerSaveWhitelistSystemAppIdArray = keysToIntArray(this.mPowerSaveWhitelistSystemAppIds);
            this.mConstants = this.mInjector.getConstants(this, this.mHandler, getContext().getContentResolver());
            readConfigFileLocked();
            updateWhitelistAppIdsLocked();
            this.mNetworkConnected = true;
            this.mScreenOn = true;
            this.mScreenLocked = false;
            this.mCharging = true;
            this.mActiveReason = 0;
            moveToStateLocked(0, "boot");
            moveToLightStateLocked(0, "boot");
            this.mInactiveTimeout = this.mConstants.INACTIVE_TIMEOUT;
        }
        this.mBinderService = new com.android.server.DeviceIdleController.BinderService();
        publishBinderService("deviceidle", this.mBinderService);
        this.mLocalService = new com.android.server.DeviceIdleController.LocalService();
        publishLocalService(com.android.server.DeviceIdleInternal.class, this.mLocalService);
        publishLocalService(com.android.server.PowerAllowlistInternal.class, new com.android.server.DeviceIdleController.LocalPowerAllowlistService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        android.os.WearModeManagerInternal wearModeManagerInternal;
        if (i == 500) {
            synchronized (this) {
                try {
                    this.mAlarmManager = this.mInjector.getAlarmManager();
                    this.mLocalAlarmManager = (com.android.server.AlarmManagerInternal) getLocalService(com.android.server.AlarmManagerInternal.class);
                    this.mBatteryStats = com.android.server.am.BatteryStatsService.getService();
                    this.mLocalActivityManager = (android.app.ActivityManagerInternal) getLocalService(android.app.ActivityManagerInternal.class);
                    this.mLocalActivityTaskManager = (com.android.server.wm.ActivityTaskManagerInternal) getLocalService(com.android.server.wm.ActivityTaskManagerInternal.class);
                    this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) getLocalService(android.content.pm.PackageManagerInternal.class);
                    this.mLocalPowerManager = (android.os.PowerManagerInternal) getLocalService(android.os.PowerManagerInternal.class);
                    this.mPowerManager = this.mInjector.getPowerManager();
                    this.mActiveIdleWakeLock = this.mPowerManager.newWakeLock(1, "deviceidle_maint");
                    this.mActiveIdleWakeLock.setReferenceCounted(false);
                    this.mGoingIdleWakeLock = this.mPowerManager.newWakeLock(1, "deviceidle_going_idle");
                    this.mGoingIdleWakeLock.setReferenceCounted(true);
                    this.mNetworkPolicyManager = android.net.INetworkPolicyManager.Stub.asInterface(android.os.ServiceManager.getService("netpolicy"));
                    this.mNetworkPolicyManagerInternal = (com.android.server.net.NetworkPolicyManagerInternal) getLocalService(com.android.server.net.NetworkPolicyManagerInternal.class);
                    this.mSensorManager = this.mInjector.getSensorManager();
                    if (this.mUseMotionSensor) {
                        this.mMotionSensor = this.mInjector.getMotionSensor();
                    }
                    if (this.mIsLocationPrefetchEnabled) {
                        this.mLocationRequest = new android.location.LocationRequest.Builder(0L).setQuality(100).setMaxUpdates(1).build();
                    }
                    this.mConstraintController = this.mInjector.getConstraintController(this.mHandler, (com.android.server.DeviceIdleInternal) getLocalService(com.android.server.DeviceIdleController.LocalService.class));
                    if (this.mConstraintController != null) {
                        this.mConstraintController.start();
                    }
                    this.mAnyMotionDetector = this.mInjector.getAnyMotionDetector(this.mHandler, this.mSensorManager, this, getContext().getResources().getInteger(android.R.integer.config_autoPowerModeThresholdAngle) / 100.0f);
                    this.mAppStateTracker.onSystemServicesReady();
                    android.os.Bundle bundle = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
                    this.mIdleIntent = new android.content.Intent("android.os.action.DEVICE_IDLE_MODE_CHANGED");
                    this.mIdleIntent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
                    this.mLightIdleIntent = new android.content.Intent("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
                    this.mLightIdleIntent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
                    this.mLightIdleIntentOptions = bundle;
                    this.mIdleIntentOptions = bundle;
                    this.mPowerSaveWhitelistChangedIntent = new android.content.Intent("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
                    this.mPowerSaveWhitelistChangedIntent.addFlags(1073741824);
                    this.mPowerSaveTempWhitelistChangedIntent = new android.content.Intent("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED");
                    this.mPowerSaveTempWhitelistChangedIntent.addFlags(1073741824);
                    this.mPowerSaveWhitelistChangedOptions = bundle;
                    this.mPowerSaveTempWhilelistChangedOptions = bundle;
                    android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                    intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                    getContext().registerReceiver(this.mReceiver, intentFilter);
                    android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
                    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
                    getContext().registerReceiver(this.mReceiver, intentFilter2);
                    android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
                    intentFilter3.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    getContext().registerReceiver(this.mReceiver, intentFilter3);
                    android.content.IntentFilter intentFilter4 = new android.content.IntentFilter();
                    intentFilter4.addAction("android.intent.action.SCREEN_OFF");
                    intentFilter4.addAction("android.intent.action.SCREEN_ON");
                    getContext().registerReceiver(this.mInteractivityReceiver, intentFilter4);
                    this.mLocalActivityManager.setDeviceIdleAllowlist(this.mPowerSaveWhitelistAllAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray);
                    this.mLocalPowerManager.setDeviceIdleWhitelist(this.mPowerSaveWhitelistAllAppIdArray);
                    if (this.mConstants.USE_MODE_MANAGER && (wearModeManagerInternal = (android.os.WearModeManagerInternal) com.android.server.LocalServices.getService(android.os.WearModeManagerInternal.class)) != null) {
                        wearModeManagerInternal.addActiveStateChangeListener("quick_doze_request", com.android.server.AppSchedulingModuleThread.getExecutor(), this.mModeManagerQuickDozeRequestConsumer);
                        wearModeManagerInternal.addActiveStateChangeListener("off_body", com.android.server.AppSchedulingModuleThread.getExecutor(), this.mModeManagerOffBodyStateConsumer);
                    }
                    this.mLocalPowerManager.registerLowPowerModeObserver(15, new java.util.function.Consumer() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.DeviceIdleController.this.lambda$onBootPhase$3((android.os.PowerSaveState) obj);
                        }
                    });
                    this.mBatterySaverEnabled = this.mLocalPowerManager.getLowPowerState(15).batterySaverEnabled;
                    updateQuickDozeFlagLocked();
                    this.mLocalActivityTaskManager.registerScreenObserver(this.mScreenObserver);
                    this.mInjector.getTelephonyManager().registerTelephonyCallback(com.android.server.AppSchedulingModuleThread.getExecutor(), this.mEmergencyCallListener);
                    passWhiteListsToForceAppStandbyTrackerLocked();
                    updateInteractivityLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            updateConnectivityState(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$3(android.os.PowerSaveState powerSaveState) {
        synchronized (this) {
            this.mBatterySaverEnabled = powerSaveState.batterySaverEnabled;
            updateQuickDozeFlagLocked();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasMotionSensor() {
        return this.mUseMotionSensor && this.mMotionSensor != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDeviceIdleConstraintInternal(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, java.lang.String str, int i) {
        int i2;
        switch (i) {
            case 0:
                i2 = 0;
                break;
            case 1:
                i2 = 3;
                break;
            default:
                android.util.Slog.wtf(TAG, "Registering device-idle constraint with invalid type: " + i);
                return;
        }
        synchronized (this) {
            try {
                if (this.mConstraints.containsKey(iDeviceIdleConstraint)) {
                    android.util.Slog.e(TAG, "Re-registering device-idle constraint: " + iDeviceIdleConstraint + ".");
                    return;
                }
                this.mConstraints.put(iDeviceIdleConstraint, new com.android.server.deviceidle.DeviceIdleConstraintTracker(str, i2));
                updateActiveConstraintsLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDeviceIdleConstraintInternal(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint) {
        synchronized (this) {
            onConstraintStateChangedLocked(iDeviceIdleConstraint, false);
            setConstraintMonitoringLocked(iDeviceIdleConstraint, false);
            this.mConstraints.remove(iDeviceIdleConstraint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void onConstraintStateChangedLocked(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
        com.android.server.deviceidle.DeviceIdleConstraintTracker deviceIdleConstraintTracker = this.mConstraints.get(iDeviceIdleConstraint);
        if (deviceIdleConstraintTracker == null) {
            android.util.Slog.e(TAG, "device-idle constraint " + iDeviceIdleConstraint + " has not been registered.");
            return;
        }
        if (z != deviceIdleConstraintTracker.active && deviceIdleConstraintTracker.monitoring) {
            deviceIdleConstraintTracker.active = z;
            this.mNumBlockingConstraints += deviceIdleConstraintTracker.active ? 1 : -1;
            if (this.mNumBlockingConstraints == 0) {
                if (this.mState == 0) {
                    becomeInactiveIfAppropriateLocked();
                    return;
                }
                if (this.mNextAlarmTime == 0 || this.mNextAlarmTime < android.os.SystemClock.elapsedRealtime()) {
                    stepIdleStateLocked("s:" + deviceIdleConstraintTracker.name);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void setConstraintMonitoringLocked(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
        com.android.server.deviceidle.DeviceIdleConstraintTracker deviceIdleConstraintTracker = this.mConstraints.get(iDeviceIdleConstraint);
        if (deviceIdleConstraintTracker.monitoring != z) {
            deviceIdleConstraintTracker.monitoring = z;
            updateActiveConstraintsLocked();
            this.mHandler.obtainMessage(10, z ? 1 : 0, -1, iDeviceIdleConstraint).sendToTarget();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateActiveConstraintsLocked() {
        this.mNumBlockingConstraints = 0;
        for (int i = 0; i < this.mConstraints.size(); i++) {
            com.android.server.deviceidle.IDeviceIdleConstraint keyAt = this.mConstraints.keyAt(i);
            com.android.server.deviceidle.DeviceIdleConstraintTracker valueAt = this.mConstraints.valueAt(i);
            boolean z = valueAt.minState == this.mState;
            if (z != valueAt.monitoring) {
                setConstraintMonitoringLocked(keyAt, z);
                valueAt.active = z;
            }
            if (valueAt.monitoring && valueAt.active) {
                this.mNumBlockingConstraints++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int addPowerSaveWhitelistAppsInternal(java.util.List<java.lang.String> list) {
        int i;
        synchronized (this) {
            int i2 = 0;
            i = 0;
            for (int size = list.size() - 1; size >= 0; size--) {
                java.lang.String str = list.get(size);
                if (str == null) {
                    i++;
                } else {
                    try {
                        if (this.mPowerSaveWhitelistUserApps.put(str, java.lang.Integer.valueOf(android.os.UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 4194304).uid))) == null) {
                            i2++;
                            com.android.modules.expresslog.Counter.logIncrement(USER_ALLOWLIST_ADDITION_METRIC_ID);
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.e(TAG, "Tried to add unknown package to power save whitelist: " + str);
                        i++;
                    }
                }
            }
            if (i2 > 0) {
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
            }
        }
        return list.size() - i;
    }

    public boolean removePowerSaveWhitelistAppInternal(java.lang.String str) {
        synchronized (this) {
            try {
                if (this.mPowerSaveWhitelistUserApps.remove(str) != null) {
                    reportPowerSaveWhitelistChangedLocked();
                    updateWhitelistAppIdsLocked();
                    writeConfigFileLocked();
                    com.android.modules.expresslog.Counter.logIncrement(USER_ALLOWLIST_REMOVAL_METRIC_ID);
                    return true;
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean getPowerSaveWhitelistAppInternal(java.lang.String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistUserApps.containsKey(str);
        }
        return containsKey;
    }

    void resetSystemPowerWhitelistInternal() {
        synchronized (this) {
            this.mPowerSaveWhitelistApps.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Integer>) this.mRemovedFromSystemWhitelistApps);
            this.mRemovedFromSystemWhitelistApps.clear();
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
        }
    }

    public boolean restoreSystemPowerWhitelistAppInternal(java.lang.String str) {
        synchronized (this) {
            try {
                if (!this.mRemovedFromSystemWhitelistApps.containsKey(str)) {
                    return false;
                }
                this.mPowerSaveWhitelistApps.put(str, this.mRemovedFromSystemWhitelistApps.remove(str));
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean removeSystemPowerWhitelistAppInternal(java.lang.String str) {
        synchronized (this) {
            try {
                if (!this.mPowerSaveWhitelistApps.containsKey(str)) {
                    return false;
                }
                this.mRemovedFromSystemWhitelistApps.put(str, this.mPowerSaveWhitelistApps.remove(str));
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean addPowerSaveWhitelistExceptIdleInternal(java.lang.String str) {
        synchronized (this) {
            try {
                if (this.mPowerSaveWhitelistAppsExceptIdle.put(str, java.lang.Integer.valueOf(android.os.UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 4194304).uid))) == null) {
                    this.mPowerSaveWhitelistUserAppsExceptIdle.add(str);
                    reportPowerSaveWhitelistChangedLocked();
                    this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
                    passWhiteListsToForceAppStandbyTrackerLocked();
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return true;
    }

    public void resetPowerSaveWhitelistExceptIdleInternal() {
        synchronized (this) {
            try {
                if (this.mPowerSaveWhitelistAppsExceptIdle.removeAll(this.mPowerSaveWhitelistUserAppsExceptIdle)) {
                    reportPowerSaveWhitelistChangedLocked();
                    this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
                    this.mPowerSaveWhitelistUserAppsExceptIdle.clear();
                    passWhiteListsToForceAppStandbyTrackerLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean getPowerSaveWhitelistExceptIdleInternal(java.lang.String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str);
        }
        return containsKey;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getSystemPowerWhitelistExceptIdleInternal(final int i, final int i2) {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                int size = this.mPowerSaveWhitelistAppsExceptIdle.size();
                strArr = new java.lang.String[size];
                for (int i3 = 0; i3 < size; i3++) {
                    strArr[i3] = this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(strArr, new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i4) {
                java.lang.String[] lambda$getSystemPowerWhitelistExceptIdleInternal$4;
                lambda$getSystemPowerWhitelistExceptIdleInternal$4 = com.android.server.DeviceIdleController.lambda$getSystemPowerWhitelistExceptIdleInternal$4(i4);
                return lambda$getSystemPowerWhitelistExceptIdleInternal$4;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getSystemPowerWhitelistExceptIdleInternal$5;
                lambda$getSystemPowerWhitelistExceptIdleInternal$5 = com.android.server.DeviceIdleController.this.lambda$getSystemPowerWhitelistExceptIdleInternal$5(i, i2, (java.lang.String) obj);
                return lambda$getSystemPowerWhitelistExceptIdleInternal$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getSystemPowerWhitelistExceptIdleInternal$4(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSystemPowerWhitelistExceptIdleInternal$5(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getSystemPowerWhitelistInternal(final int i, final int i2) {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                int size = this.mPowerSaveWhitelistApps.size();
                strArr = new java.lang.String[size];
                for (int i3 = 0; i3 < size; i3++) {
                    strArr[i3] = this.mPowerSaveWhitelistApps.keyAt(i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(strArr, new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda9
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i4) {
                java.lang.String[] lambda$getSystemPowerWhitelistInternal$6;
                lambda$getSystemPowerWhitelistInternal$6 = com.android.server.DeviceIdleController.lambda$getSystemPowerWhitelistInternal$6(i4);
                return lambda$getSystemPowerWhitelistInternal$6;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getSystemPowerWhitelistInternal$7;
                lambda$getSystemPowerWhitelistInternal$7 = com.android.server.DeviceIdleController.this.lambda$getSystemPowerWhitelistInternal$7(i, i2, (java.lang.String) obj);
                return lambda$getSystemPowerWhitelistInternal$7;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getSystemPowerWhitelistInternal$6(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSystemPowerWhitelistInternal$7(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getRemovedSystemPowerWhitelistAppsInternal(final int i, final int i2) {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                int size = this.mRemovedFromSystemWhitelistApps.size();
                strArr = new java.lang.String[size];
                for (int i3 = 0; i3 < size; i3++) {
                    strArr[i3] = this.mRemovedFromSystemWhitelistApps.keyAt(i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(strArr, new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda7
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i4) {
                java.lang.String[] lambda$getRemovedSystemPowerWhitelistAppsInternal$8;
                lambda$getRemovedSystemPowerWhitelistAppsInternal$8 = com.android.server.DeviceIdleController.lambda$getRemovedSystemPowerWhitelistAppsInternal$8(i4);
                return lambda$getRemovedSystemPowerWhitelistAppsInternal$8;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getRemovedSystemPowerWhitelistAppsInternal$9;
                lambda$getRemovedSystemPowerWhitelistAppsInternal$9 = com.android.server.DeviceIdleController.this.lambda$getRemovedSystemPowerWhitelistAppsInternal$9(i, i2, (java.lang.String) obj);
                return lambda$getRemovedSystemPowerWhitelistAppsInternal$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getRemovedSystemPowerWhitelistAppsInternal$8(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getRemovedSystemPowerWhitelistAppsInternal$9(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getUserPowerWhitelistInternal(final int i, final int i2) {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                strArr = new java.lang.String[this.mPowerSaveWhitelistUserApps.size()];
                for (int i3 = 0; i3 < this.mPowerSaveWhitelistUserApps.size(); i3++) {
                    strArr[i3] = this.mPowerSaveWhitelistUserApps.keyAt(i3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(strArr, new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda11
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i4) {
                java.lang.String[] lambda$getUserPowerWhitelistInternal$10;
                lambda$getUserPowerWhitelistInternal$10 = com.android.server.DeviceIdleController.lambda$getUserPowerWhitelistInternal$10(i4);
                return lambda$getUserPowerWhitelistInternal$10;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getUserPowerWhitelistInternal$11;
                lambda$getUserPowerWhitelistInternal$11 = com.android.server.DeviceIdleController.this.lambda$getUserPowerWhitelistInternal$11(i, i2, (java.lang.String) obj);
                return lambda$getUserPowerWhitelistInternal$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getUserPowerWhitelistInternal$10(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getUserPowerWhitelistInternal$11(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getFullPowerWhitelistExceptIdleInternal(final int i, final int i2) {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                strArr = new java.lang.String[this.mPowerSaveWhitelistAppsExceptIdle.size() + this.mPowerSaveWhitelistUserApps.size()];
                int i3 = 0;
                for (int i4 = 0; i4 < this.mPowerSaveWhitelistAppsExceptIdle.size(); i4++) {
                    strArr[i3] = this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i4);
                    i3++;
                }
                for (int i5 = 0; i5 < this.mPowerSaveWhitelistUserApps.size(); i5++) {
                    strArr[i3] = this.mPowerSaveWhitelistUserApps.keyAt(i5);
                    i3++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(strArr, new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda5
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i6) {
                java.lang.String[] lambda$getFullPowerWhitelistExceptIdleInternal$12;
                lambda$getFullPowerWhitelistExceptIdleInternal$12 = com.android.server.DeviceIdleController.lambda$getFullPowerWhitelistExceptIdleInternal$12(i6);
                return lambda$getFullPowerWhitelistExceptIdleInternal$12;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getFullPowerWhitelistExceptIdleInternal$13;
                lambda$getFullPowerWhitelistExceptIdleInternal$13 = com.android.server.DeviceIdleController.this.lambda$getFullPowerWhitelistExceptIdleInternal$13(i, i2, (java.lang.String) obj);
                return lambda$getFullPowerWhitelistExceptIdleInternal$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getFullPowerWhitelistExceptIdleInternal$12(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getFullPowerWhitelistExceptIdleInternal$13(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getFullPowerWhitelistInternal(final int i, final int i2) {
        return (java.lang.String[]) com.android.internal.util.jobs.ArrayUtils.filter(getFullPowerWhitelistInternalUnchecked(), new java.util.function.IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda14
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i3) {
                java.lang.String[] lambda$getFullPowerWhitelistInternal$14;
                lambda$getFullPowerWhitelistInternal$14 = com.android.server.DeviceIdleController.lambda$getFullPowerWhitelistInternal$14(i3);
                return lambda$getFullPowerWhitelistInternal$14;
            }
        }, new java.util.function.Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getFullPowerWhitelistInternal$15;
                lambda$getFullPowerWhitelistInternal$15 = com.android.server.DeviceIdleController.this.lambda$getFullPowerWhitelistInternal$15(i, i2, (java.lang.String) obj);
                return lambda$getFullPowerWhitelistInternal$15;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$getFullPowerWhitelistInternal$14(int i) {
        return new java.lang.String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getFullPowerWhitelistInternal$15(int i, int i2, java.lang.String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getFullPowerWhitelistInternalUnchecked() {
        java.lang.String[] strArr;
        synchronized (this) {
            try {
                strArr = new java.lang.String[this.mPowerSaveWhitelistApps.size() + this.mPowerSaveWhitelistUserApps.size()];
                int i = 0;
                for (int i2 = 0; i2 < this.mPowerSaveWhitelistApps.size(); i2++) {
                    strArr[i] = this.mPowerSaveWhitelistApps.keyAt(i2);
                    i++;
                }
                for (int i3 = 0; i3 < this.mPowerSaveWhitelistUserApps.size(); i3++) {
                    strArr[i] = this.mPowerSaveWhitelistUserApps.keyAt(i3);
                    i++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    public boolean isPowerSaveWhitelistExceptIdleAppInternal(java.lang.String str) {
        boolean z;
        synchronized (this) {
            try {
                z = this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str) || this.mPowerSaveWhitelistUserApps.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    public boolean isPowerSaveWhitelistAppInternal(java.lang.String str) {
        boolean z;
        synchronized (this) {
            try {
                z = this.mPowerSaveWhitelistApps.containsKey(str) || this.mPowerSaveWhitelistUserApps.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    public int[] getAppIdWhitelistExceptIdleInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistExceptIdleAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistAllAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdUserWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistUserAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdTempWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mTempWhitelistAppIdArray;
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTempAllowListType(int i, int i2) {
        switch (i) {
            case -1:
                return -1;
            case 102:
                return this.mLocalActivityManager.getPushMessagingOverQuotaBehavior();
            default:
                return i2;
        }
    }

    void addPowerSaveTempAllowlistAppChecked(java.lang.String str, long j, int i, int i2, @android.annotation.Nullable java.lang.String str2) throws android.os.RemoteException {
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int callingUid = android.os.Binder.getCallingUid();
        int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "addPowerSaveTempWhitelistApp", (java.lang.String) null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int tempAllowListType = getTempAllowListType(i2, 0);
            if (tempAllowListType != -1) {
                addPowerSaveTempAllowlistAppInternal(callingUid, str, j, tempAllowListType, handleIncomingUser, true, i2, str2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void removePowerSaveTempAllowlistAppChecked(java.lang.String str, int i) throws android.os.RemoteException {
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "removePowerSaveTempWhitelistApp", (java.lang.String) null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removePowerSaveTempAllowlistAppInternal(str, handleIncomingUser);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void addPowerSaveTempAllowlistAppInternal(int i, java.lang.String str, long j, int i2, int i3, boolean z, int i4, @android.annotation.Nullable java.lang.String str2) {
        try {
            addPowerSaveTempWhitelistAppDirectInternal(i, getContext().getPackageManager().getPackageUidAsUser(str, i3), j, i2, z, i4, str2);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    void addPowerSaveTempWhitelistAppDirectInternal(int i, int i2, long j, int i3, boolean z, int i4, @android.annotation.Nullable java.lang.String str) {
        boolean z2;
        boolean z3;
        int i5;
        java.lang.String str2;
        int i6;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int appId = android.os.UserHandle.getAppId(i2);
        synchronized (this) {
            try {
                long min = java.lang.Math.min(j, this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS);
                android.util.Pair<android.util.MutableLong, java.lang.String> pair = this.mTempWhitelistAppIdEndTimes.get(appId);
                z2 = false;
                boolean z4 = pair == null;
                if (z4) {
                    pair = new android.util.Pair<>(new android.util.MutableLong(0L), str);
                    this.mTempWhitelistAppIdEndTimes.put(appId, pair);
                }
                ((android.util.MutableLong) pair.first).value = elapsedRealtime + min;
                if (z4) {
                    try {
                        this.mBatteryStats.noteEvent(32785, str, i2);
                    } catch (android.os.RemoteException e) {
                    }
                    postTempActiveTimeoutMessage(i2, min);
                    updateTempWhitelistAppIdsLocked(i2, true, min, i3, i4, str, i);
                    if (z) {
                        z2 = true;
                    } else {
                        this.mHandler.obtainMessage(14, appId, i4, str).sendToTarget();
                    }
                    reportTempWhitelistChangedLocked(i2, true);
                    z3 = true;
                    i5 = appId;
                    str2 = str;
                    i6 = i4;
                } else if (this.mLocalActivityManager != null) {
                    z3 = true;
                    i5 = appId;
                    str2 = str;
                    i6 = i4;
                    this.mLocalActivityManager.updateDeviceIdleTempAllowlist((int[]) null, i2, true, min, i3, i4, str, i);
                } else {
                    z3 = true;
                    i5 = appId;
                    str2 = str;
                    i6 = i4;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z2) {
            this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(i5, z3, i6, str2);
        }
    }

    private void removePowerSaveTempAllowlistAppInternal(java.lang.String str, int i) {
        try {
            removePowerSaveTempWhitelistAppDirectInternal(getContext().getPackageManager().getPackageUidAsUser(str, i));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    private void removePowerSaveTempWhitelistAppDirectInternal(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        synchronized (this) {
            try {
                int indexOfKey = this.mTempWhitelistAppIdEndTimes.indexOfKey(appId);
                if (indexOfKey < 0) {
                    return;
                }
                java.lang.String str = (java.lang.String) this.mTempWhitelistAppIdEndTimes.valueAt(indexOfKey).second;
                this.mTempWhitelistAppIdEndTimes.removeAt(indexOfKey);
                onAppRemovedFromTempWhitelistLocked(i, str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void postTempActiveTimeoutMessage(int i, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(6, i, 0), j);
    }

    void checkTempAppWhitelistTimeout(int i) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int appId = android.os.UserHandle.getAppId(i);
        synchronized (this) {
            try {
                android.util.Pair<android.util.MutableLong, java.lang.String> pair = this.mTempWhitelistAppIdEndTimes.get(appId);
                if (pair == null) {
                    return;
                }
                if (elapsedRealtime >= ((android.util.MutableLong) pair.first).value) {
                    this.mTempWhitelistAppIdEndTimes.delete(appId);
                    onAppRemovedFromTempWhitelistLocked(i, (java.lang.String) pair.second);
                } else {
                    postTempActiveTimeoutMessage(i, ((android.util.MutableLong) pair.first).value - elapsedRealtime);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void onAppRemovedFromTempWhitelistLocked(int i, @android.annotation.Nullable java.lang.String str) {
        int appId = android.os.UserHandle.getAppId(i);
        updateTempWhitelistAppIdsLocked(i, false, 0L, 0, 0, str, -1);
        this.mHandler.obtainMessage(15, appId, 0).sendToTarget();
        reportTempWhitelistChangedLocked(i, false);
        try {
            this.mBatteryStats.noteEvent(16401, str, appId);
        } catch (android.os.RemoteException e) {
        }
    }

    public void exitIdleInternal(java.lang.String str) {
        synchronized (this) {
            this.mActiveReason = 5;
            becomeActiveLocked(str, android.os.Binder.getCallingUid());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isNetworkConnected() {
        boolean z;
        synchronized (this) {
            z = this.mNetworkConnected;
        }
        return z;
    }

    void updateConnectivityState(android.content.Intent intent) {
        android.net.ConnectivityManager connectivityManager;
        synchronized (this) {
            connectivityManager = this.mInjector.getConnectivityManager();
        }
        if (connectivityManager == null) {
            return;
        }
        android.net.NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        synchronized (this) {
            boolean z = false;
            try {
                if (activeNetworkInfo != null) {
                    if (intent == null) {
                        z = activeNetworkInfo.isConnected();
                    } else {
                        if (activeNetworkInfo.getType() != intent.getIntExtra("networkType", -1)) {
                            return;
                        } else {
                            z = !intent.getBooleanExtra("noConnectivity", false);
                        }
                    }
                }
                if (z != this.mNetworkConnected) {
                    this.mNetworkConnected = z;
                    if (z && this.mLightState == 5) {
                        stepLightIdleStateLocked("network");
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isScreenOn() {
        boolean z;
        synchronized (this) {
            z = this.mScreenOn;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void updateInteractivityLocked() {
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (!isInteractive && this.mScreenOn) {
            this.mScreenOn = false;
            if (!this.mForceIdle) {
                becomeInactiveIfAppropriateLocked();
                return;
            }
            return;
        }
        if (isInteractive) {
            this.mScreenOn = true;
            if (this.mForceIdle) {
                return;
            }
            if (!this.mScreenLocked || !this.mConstants.WAIT_FOR_UNLOCK) {
                this.mActiveReason = 2;
                becomeActiveLocked("screen", android.os.Process.myUid());
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isCharging() {
        boolean z;
        synchronized (this) {
            z = this.mCharging;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void updateChargingLocked(boolean z) {
        if (!z && this.mCharging) {
            this.mCharging = false;
            if (!this.mForceIdle) {
                becomeInactiveIfAppropriateLocked();
                return;
            }
            return;
        }
        if (z) {
            this.mCharging = z;
            if (!this.mForceIdle) {
                this.mActiveReason = 3;
                becomeActiveLocked("charging", android.os.Process.myUid());
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isQuickDozeEnabled() {
        boolean z;
        synchronized (this) {
            z = this.mQuickDozeActivated;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateQuickDozeFlagLocked() {
        if (this.mConstants.USE_MODE_MANAGER) {
            updateQuickDozeFlagLocked(this.mModeManagerRequestedQuickDoze || this.mBatterySaverEnabled);
        } else {
            updateQuickDozeFlagLocked(this.mBatterySaverEnabled);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    void updateQuickDozeFlagLocked(boolean z) {
        this.mQuickDozeActivated = z;
        this.mQuickDozeActivatedWhileIdling = this.mQuickDozeActivated && (this.mState == 5 || this.mState == 6);
        if (z) {
            becomeInactiveIfAppropriateLocked();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isKeyguardShowing() {
        boolean z;
        synchronized (this) {
            z = this.mScreenLocked;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    void keyguardShowingLocked(boolean z) {
        if (this.mScreenLocked != z) {
            this.mScreenLocked = z;
            if (this.mScreenOn && !this.mForceIdle && !this.mScreenLocked) {
                this.mActiveReason = 4;
                becomeActiveLocked("unlocked", android.os.Process.myUid());
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    void scheduleReportActiveLocked(java.lang.String str, int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i, 0, str));
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void becomeActiveLocked(java.lang.String str, int i) {
        becomeActiveLocked(str, i, this.mConstants.INACTIVE_TIMEOUT, true);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void becomeActiveLocked(java.lang.String str, int i, long j, boolean z) {
        if (this.mState != 0 || this.mLightState != 0) {
            moveToStateLocked(0, str);
            this.mInactiveTimeout = j;
            resetIdleManagementLocked();
            if (this.mLightState != 6) {
                this.mMaintenanceStartTime = 0L;
            }
            if (z) {
                moveToLightStateLocked(0, str);
                resetLightIdleManagementLocked();
                scheduleReportActiveLocked(str, i);
                addEvent(1, str);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDeepEnabledForTest(boolean z) {
        synchronized (this) {
            this.mDeepEnabled = z;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLightEnabledForTest(boolean z) {
        synchronized (this) {
            this.mLightEnabled = z;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void verifyAlarmStateLocked() {
        if (this.mState == 0 && this.mNextAlarmTime != 0) {
            android.util.Slog.wtf(TAG, "mState=ACTIVE but mNextAlarmTime=" + this.mNextAlarmTime);
        }
        if (this.mState != 5 && this.mLocalAlarmManager.isIdling()) {
            android.util.Slog.wtf(TAG, "mState=" + stateToString(this.mState) + " but AlarmManager is idling");
        }
        if (this.mState == 5 && !this.mLocalAlarmManager.isIdling()) {
            android.util.Slog.wtf(TAG, "mState=IDLE but AlarmManager is not idling");
        }
        if (this.mLightState == 0 && this.mNextLightAlarmTime != 0) {
            android.util.Slog.wtf(TAG, "mLightState=ACTIVE but mNextLightAlarmTime is " + android.util.TimeUtils.formatDuration(this.mNextLightAlarmTime - android.os.SystemClock.elapsedRealtime()) + " from now");
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void becomeInactiveIfAppropriateLocked() {
        verifyAlarmStateLocked();
        boolean z = this.mScreenOn && !(this.mConstants.WAIT_FOR_UNLOCK && this.mScreenLocked);
        boolean isEmergencyCallActive = this.mEmergencyCallListener.isEmergencyCallActive();
        if (!this.mForceIdle && (this.mCharging || z || isEmergencyCallActive)) {
            return;
        }
        if (this.mDeepEnabled) {
            if (this.mQuickDozeActivated) {
                if (this.mState == 7 || this.mState == 5 || this.mState == 6) {
                    return;
                }
                moveToStateLocked(7, "no activity");
                resetIdleManagementLocked();
                if (isUpcomingAlarmClock()) {
                    scheduleAlarmLocked((this.mAlarmManager.getNextWakeFromIdleTime() - this.mInjector.getElapsedRealtime()) + this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                } else {
                    scheduleAlarmLocked(this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                }
            } else if (this.mState == 0) {
                moveToStateLocked(1, "no activity");
                resetIdleManagementLocked();
                long j = this.mInactiveTimeout;
                if (isUpcomingAlarmClock()) {
                    scheduleAlarmLocked((this.mAlarmManager.getNextWakeFromIdleTime() - this.mInjector.getElapsedRealtime()) + j);
                } else {
                    scheduleAlarmLocked(j);
                }
            }
        }
        if (this.mLightState == 0 && this.mLightEnabled) {
            moveToLightStateLocked(1, "no activity");
            resetLightIdleManagementLocked();
            scheduleLightAlarmLocked(this.mConstants.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, this.mConstants.FLEX_TIME_SHORT, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void resetIdleManagementLocked() {
        this.mNextIdlePendingDelay = 0L;
        this.mNextIdleDelay = 0L;
        this.mQuickDozeActivatedWhileIdling = false;
        cancelAlarmLocked();
        cancelSensingTimeoutAlarmLocked();
        cancelLocatingLocked();
        maybeStopMonitoringMotionLocked();
        this.mAnyMotionDetector.stop();
        updateActiveConstraintsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void resetLightIdleManagementLocked() {
        this.mNextLightIdleDelay = this.mConstants.LIGHT_IDLE_TIMEOUT;
        this.mMaintenanceStartTime = 0L;
        this.mNextLightIdleDelayFlex = this.mConstants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
        this.mCurLightIdleBudget = this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
        cancelLightAlarmLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void exitForceIdleLocked() {
        if (this.mForceIdle) {
            this.mForceIdle = false;
            if (this.mScreenOn || this.mCharging) {
                this.mActiveReason = 6;
                becomeActiveLocked("exit-force", android.os.Process.myUid());
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLightStateForTest(int i) {
        synchronized (this) {
            this.mLightState = i;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getLightState() {
        int i;
        synchronized (this) {
            i = this.mLightState;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.SuppressLint({"WakelockTimeout"})
    void stepLightIdleStateLocked(java.lang.String str) {
        if (this.mLightState == 0 || this.mLightState == 7) {
            return;
        }
        com.android.server.EventLogTags.writeDeviceIdleLightStep();
        if (!this.mEmergencyCallListener.isEmergencyCallActive()) {
            switch (this.mLightState) {
                case 1:
                    this.mCurLightIdleBudget = this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
                    this.mNextLightIdleDelay = this.mConstants.LIGHT_IDLE_TIMEOUT;
                    this.mNextLightIdleDelayFlex = this.mConstants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
                    this.mMaintenanceStartTime = 0L;
                    break;
                case 2:
                case 3:
                default:
                    return;
                case 4:
                case 5:
                    if (this.mNetworkConnected || this.mLightState == 5) {
                        this.mActiveIdleOpCount = 1;
                        this.mActiveIdleWakeLock.acquire();
                        this.mMaintenanceStartTime = android.os.SystemClock.elapsedRealtime();
                        if (this.mCurLightIdleBudget < this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET) {
                            this.mCurLightIdleBudget = this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
                        } else if (this.mCurLightIdleBudget > this.mConstants.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET) {
                            this.mCurLightIdleBudget = this.mConstants.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET;
                        }
                        scheduleLightAlarmLocked(this.mCurLightIdleBudget, this.mConstants.FLEX_TIME_SHORT, true);
                        moveToLightStateLocked(6, str);
                        addEvent(3, null);
                        this.mHandler.sendEmptyMessage(4);
                        return;
                    }
                    scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex / 2, true);
                    moveToLightStateLocked(5, str);
                    return;
                case 6:
                    break;
            }
            if (this.mMaintenanceStartTime != 0) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mMaintenanceStartTime;
                if (elapsedRealtime < this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET) {
                    this.mCurLightIdleBudget += this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET - elapsedRealtime;
                } else {
                    this.mCurLightIdleBudget -= elapsedRealtime - this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
                }
            }
            this.mMaintenanceStartTime = 0L;
            scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex, true);
            if (!this.mConstants.LIGHT_IDLE_INCREASE_LINEARLY) {
                this.mNextLightIdleDelay = java.lang.Math.min(this.mConstants.LIGHT_MAX_IDLE_TIMEOUT, (long) (this.mNextLightIdleDelay * this.mConstants.LIGHT_IDLE_FACTOR));
                this.mNextLightIdleDelayFlex = java.lang.Math.min(this.mConstants.LIGHT_IDLE_TIMEOUT_MAX_FLEX, (long) (this.mNextLightIdleDelayFlex * this.mConstants.LIGHT_IDLE_FACTOR));
            } else {
                this.mNextLightIdleDelay = java.lang.Math.min(this.mConstants.LIGHT_MAX_IDLE_TIMEOUT, this.mNextLightIdleDelay + this.mConstants.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS);
                this.mNextLightIdleDelayFlex = java.lang.Math.min(this.mConstants.LIGHT_IDLE_TIMEOUT_MAX_FLEX, this.mNextLightIdleDelayFlex + this.mConstants.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS);
            }
            moveToLightStateLocked(4, str);
            addEvent(2, null);
            this.mGoingIdleWakeLock.acquire();
            this.mHandler.sendEmptyMessage(3);
            return;
        }
        android.util.Slog.wtf(TAG, "stepLightIdleStateLocked called when emergency call is active");
        if (this.mLightState != 0) {
            this.mActiveReason = 8;
            becomeActiveLocked("emergency", android.os.Process.myUid());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getState() {
        int i;
        synchronized (this) {
            i = this.mState;
        }
        return i;
    }

    private boolean isUpcomingAlarmClock() {
        return this.mInjector.getElapsedRealtime() + this.mConstants.MIN_TIME_TO_ALARM >= this.mAlarmManager.getNextWakeFromIdleTime();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x017b  */
    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void stepIdleStateLocked(java.lang.String str) {
        com.android.server.EventLogTags.writeDeviceIdleStep();
        if (this.mEmergencyCallListener.isEmergencyCallActive()) {
            android.util.Slog.wtf(TAG, "stepIdleStateLocked called when emergency call is active");
            if (this.mState != 0) {
                this.mActiveReason = 8;
                becomeActiveLocked("emergency", android.os.Process.myUid());
            }
            return;
        }
        if (isUpcomingAlarmClock()) {
            if (this.mState != 0) {
                this.mActiveReason = 7;
                becomeActiveLocked(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM, android.os.Process.myUid());
                becomeInactiveIfAppropriateLocked();
                return;
            }
            return;
        }
        if (this.mNumBlockingConstraints != 0 && !this.mForceIdle) {
            return;
        }
        switch (this.mState) {
            case 1:
                startMonitoringMotionLocked();
                scheduleAlarmLocked(this.mConstants.IDLE_AFTER_INACTIVE_TIMEOUT);
                moveToStateLocked(2, str);
                break;
            case 2:
                cancelLocatingLocked();
                this.mLocated = false;
                this.mLastGenericLocation = null;
                this.mLastGpsLocation = null;
                moveToStateLocked(3, str);
                if (this.mUseMotionSensor && this.mAnyMotionDetector.hasSensor()) {
                    scheduleSensingTimeoutAlarmLocked(this.mConstants.SENSING_TIMEOUT);
                    this.mNotMoving = false;
                    this.mAnyMotionDetector.checkForAnyMotion();
                    break;
                } else if (this.mNumBlockingConstraints != 0) {
                    cancelAlarmLocked();
                    break;
                } else {
                    this.mNotMoving = true;
                    cancelSensingTimeoutAlarmLocked();
                    moveToStateLocked(4, str);
                    if (!this.mIsLocationPrefetchEnabled) {
                        scheduleAlarmLocked(this.mConstants.LOCATING_TIMEOUT);
                        android.location.LocationManager locationManager = this.mInjector.getLocationManager();
                        if (locationManager != null && locationManager.getProvider("fused") != null) {
                            this.mHasFusedLocation = true;
                            locationManager.requestLocationUpdates("fused", this.mLocationRequest, com.android.server.AppSchedulingModuleThread.getExecutor(), this.mGenericLocationListener);
                            this.mLocating = true;
                        } else {
                            this.mHasFusedLocation = false;
                        }
                        if (locationManager != null && locationManager.getProvider("gps") != null) {
                            this.mHasGps = true;
                            locationManager.requestLocationUpdates("gps", 1000L, 5.0f, this.mGpsLocationListener, this.mHandler.getLooper());
                            this.mLocating = true;
                        } else {
                            this.mHasGps = false;
                        }
                        if (this.mLocating) {
                        }
                    } else {
                        this.mLocating = false;
                    }
                    cancelAlarmLocked();
                    cancelLocatingLocked();
                    this.mAnyMotionDetector.stop();
                    this.mNextIdlePendingDelay = this.mConstants.IDLE_PENDING_TIMEOUT;
                    this.mNextIdleDelay = this.mConstants.IDLE_TIMEOUT;
                    moveToStateLocked(5, str);
                    scheduleAlarmLocked(this.mNextIdleDelay);
                    this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                    this.mNextIdleDelay = java.lang.Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                    if (this.mNextIdleDelay < this.mConstants.IDLE_TIMEOUT) {
                        this.mNextIdleDelay = this.mConstants.IDLE_TIMEOUT;
                    }
                    if (this.mLightState != 7) {
                        moveToLightStateLocked(7, "deep");
                        cancelLightAlarmLocked();
                    }
                    addEvent(4, null);
                    this.mGoingIdleWakeLock.acquire();
                    this.mHandler.sendEmptyMessage(2);
                    break;
                }
                break;
            case 3:
                cancelSensingTimeoutAlarmLocked();
                moveToStateLocked(4, str);
                if (!this.mIsLocationPrefetchEnabled) {
                }
                cancelAlarmLocked();
                cancelLocatingLocked();
                this.mAnyMotionDetector.stop();
                this.mNextIdlePendingDelay = this.mConstants.IDLE_PENDING_TIMEOUT;
                this.mNextIdleDelay = this.mConstants.IDLE_TIMEOUT;
                moveToStateLocked(5, str);
                scheduleAlarmLocked(this.mNextIdleDelay);
                this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                this.mNextIdleDelay = java.lang.Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                if (this.mNextIdleDelay < this.mConstants.IDLE_TIMEOUT) {
                }
                if (this.mLightState != 7) {
                }
                addEvent(4, null);
                this.mGoingIdleWakeLock.acquire();
                this.mHandler.sendEmptyMessage(2);
                break;
            case 4:
                cancelAlarmLocked();
                cancelLocatingLocked();
                this.mAnyMotionDetector.stop();
                this.mNextIdlePendingDelay = this.mConstants.IDLE_PENDING_TIMEOUT;
                this.mNextIdleDelay = this.mConstants.IDLE_TIMEOUT;
                moveToStateLocked(5, str);
                scheduleAlarmLocked(this.mNextIdleDelay);
                this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                this.mNextIdleDelay = java.lang.Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                if (this.mNextIdleDelay < this.mConstants.IDLE_TIMEOUT) {
                }
                if (this.mLightState != 7) {
                }
                addEvent(4, null);
                this.mGoingIdleWakeLock.acquire();
                this.mHandler.sendEmptyMessage(2);
                break;
            case 5:
                this.mActiveIdleOpCount = 1;
                this.mActiveIdleWakeLock.acquire();
                moveToStateLocked(6, str);
                scheduleAlarmLocked(this.mNextIdlePendingDelay);
                this.mMaintenanceStartTime = android.os.SystemClock.elapsedRealtime();
                this.mNextIdlePendingDelay = java.lang.Math.min(this.mConstants.MAX_IDLE_PENDING_TIMEOUT, (long) (this.mNextIdlePendingDelay * this.mConstants.IDLE_PENDING_FACTOR));
                if (this.mNextIdlePendingDelay < this.mConstants.IDLE_PENDING_TIMEOUT) {
                    this.mNextIdlePendingDelay = this.mConstants.IDLE_PENDING_TIMEOUT;
                }
                addEvent(5, null);
                this.mHandler.sendEmptyMessage(4);
                break;
            case 6:
                moveToStateLocked(5, str);
                scheduleAlarmLocked(this.mNextIdleDelay);
                this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                this.mNextIdleDelay = java.lang.Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                if (this.mNextIdleDelay < this.mConstants.IDLE_TIMEOUT) {
                }
                if (this.mLightState != 7) {
                }
                addEvent(4, null);
                this.mGoingIdleWakeLock.acquire();
                this.mHandler.sendEmptyMessage(2);
                break;
            case 7:
                this.mNextIdlePendingDelay = this.mConstants.IDLE_PENDING_TIMEOUT;
                this.mNextIdleDelay = this.mConstants.IDLE_TIMEOUT;
                moveToStateLocked(5, str);
                scheduleAlarmLocked(this.mNextIdleDelay);
                this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                this.mNextIdleDelay = java.lang.Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                if (this.mNextIdleDelay < this.mConstants.IDLE_TIMEOUT) {
                }
                if (this.mLightState != 7) {
                }
                addEvent(4, null);
                this.mGoingIdleWakeLock.acquire();
                this.mHandler.sendEmptyMessage(2);
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void moveToLightStateLocked(int i, java.lang.String str) {
        this.mLightState = i;
        com.android.server.EventLogTags.writeDeviceIdleLight(this.mLightState, str);
        android.os.Trace.traceCounter(524288L, "DozeLightState", i);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void moveToStateLocked(int i, java.lang.String str) {
        this.mState = i;
        com.android.server.EventLogTags.writeDeviceIdle(this.mState, str);
        android.os.Trace.traceCounter(524288L, "DozeDeepState", i);
        updateActiveConstraintsLocked();
    }

    void incActiveIdleOps() {
        synchronized (this) {
            this.mActiveIdleOpCount++;
        }
    }

    void decActiveIdleOps() {
        synchronized (this) {
            try {
                this.mActiveIdleOpCount--;
                if (this.mActiveIdleOpCount <= 0) {
                    exitMaintenanceEarlyIfNeededLocked();
                    this.mActiveIdleWakeLock.release();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setActiveIdleOpsForTest(int i) {
        synchronized (this) {
            this.mActiveIdleOpCount = i;
        }
    }

    void setJobsActive(boolean z) {
        synchronized (this) {
            try {
                this.mJobsActive = z;
                if (!z) {
                    exitMaintenanceEarlyIfNeededLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAlarmsActive(boolean z) {
        synchronized (this) {
            try {
                this.mAlarmsActive = z;
                if (!z) {
                    exitMaintenanceEarlyIfNeededLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long getNextAlarmTime() {
        long j;
        synchronized (this) {
            j = this.mNextAlarmTime;
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isEmergencyCallActive() {
        return this.mEmergencyCallListener.isEmergencyCallActive();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    boolean isOpsInactiveLocked() {
        return (this.mActiveIdleOpCount > 0 || this.mJobsActive || this.mAlarmsActive) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void exitMaintenanceEarlyIfNeededLocked() {
        if ((this.mState == 6 || this.mLightState == 6) && isOpsInactiveLocked()) {
            android.os.SystemClock.elapsedRealtime();
            if (this.mState == 6) {
                stepIdleStateLocked("s:early");
            } else {
                stepLightIdleStateLocked("s:early");
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void motionLocked() {
        this.mLastMotionEventElapsed = this.mInjector.getElapsedRealtime();
        handleMotionDetectedLocked(this.mConstants.MOTION_INACTIVE_TIMEOUT, "motion");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void handleMotionDetectedLocked(long j, java.lang.String str) {
        if (this.mStationaryListeners.size() > 0) {
            postStationaryStatusUpdated();
            cancelMotionTimeoutAlarmLocked();
            scheduleMotionRegistrationAlarmLocked();
        }
        if (this.mQuickDozeActivated && !this.mQuickDozeActivatedWhileIdling) {
            return;
        }
        maybeStopMonitoringMotionLocked();
        boolean z = this.mState != 0 || this.mLightState == 7;
        becomeActiveLocked(str, android.os.Process.myUid(), j, this.mLightState == 7);
        if (z) {
            becomeInactiveIfAppropriateLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void receivedGenericLocationLocked(android.location.Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        this.mLastGenericLocation = new android.location.Location(location);
        if (location.getAccuracy() > this.mConstants.LOCATION_ACCURACY && this.mHasGps) {
            return;
        }
        this.mLocated = true;
        if (this.mNotMoving) {
            stepIdleStateLocked("s:location");
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void receivedGpsLocationLocked(android.location.Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        this.mLastGpsLocation = new android.location.Location(location);
        if (location.getAccuracy() > this.mConstants.LOCATION_ACCURACY) {
            return;
        }
        this.mLocated = true;
        if (this.mNotMoving) {
            stepIdleStateLocked("s:gps");
        }
    }

    void startMonitoringMotionLocked() {
        if (this.mMotionSensor != null && !this.mMotionListener.active) {
            this.mMotionListener.registerLocked();
        }
    }

    private void maybeStopMonitoringMotionLocked() {
        if (this.mMotionSensor != null && this.mStationaryListeners.size() == 0) {
            if (this.mMotionListener.active) {
                this.mMotionListener.unregisterLocked();
                cancelMotionTimeoutAlarmLocked();
            }
            cancelMotionRegistrationAlarmLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void cancelAlarmLocked() {
        if (this.mNextAlarmTime != 0) {
            this.mNextAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mDeepAlarmListener);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void cancelLightAlarmLocked() {
        if (this.mNextLightAlarmTime != 0) {
            this.mNextLightAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mLightAlarmListener);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void cancelLocatingLocked() {
        if (this.mLocating) {
            android.location.LocationManager locationManager = this.mInjector.getLocationManager();
            locationManager.removeUpdates(this.mGenericLocationListener);
            locationManager.removeUpdates(this.mGpsLocationListener);
            this.mLocating = false;
        }
    }

    private void cancelMotionTimeoutAlarmLocked() {
        this.mAlarmManager.cancel(this.mMotionTimeoutAlarmListener);
    }

    private void cancelMotionRegistrationAlarmLocked() {
        this.mAlarmManager.cancel(this.mMotionRegistrationAlarmListener);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void cancelSensingTimeoutAlarmLocked() {
        if (this.mNextSensingTimeoutAlarmTime != 0) {
            this.mNextSensingTimeoutAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mSensingTimeoutAlarmListener);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    void scheduleAlarmLocked(long j) {
        if (!this.mUseMotionSensor || this.mMotionSensor != null || this.mState == 7 || this.mState == 5 || this.mState == 6) {
            this.mNextAlarmTime = android.os.SystemClock.elapsedRealtime() + j;
            if (this.mState == 5) {
                this.mAlarmManager.setIdleUntil(2, this.mNextAlarmTime, "DeviceIdleController.deep", this.mDeepAlarmListener, this.mHandler);
                return;
            }
            if (this.mState == 4) {
                this.mAlarmManager.setExact(2, this.mNextAlarmTime, "DeviceIdleController.deep", this.mDeepAlarmListener, this.mHandler);
            } else if (this.mConstants.USE_WINDOW_ALARMS) {
                this.mAlarmManager.setWindow(2, this.mNextAlarmTime, this.mConstants.FLEX_TIME_SHORT, "DeviceIdleController.deep", this.mDeepAlarmListener, this.mHandler);
            } else {
                this.mAlarmManager.set(2, this.mNextAlarmTime, "DeviceIdleController.deep", this.mDeepAlarmListener, this.mHandler);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void scheduleLightAlarmLocked(long j, long j2, boolean z) {
        this.mNextLightAlarmTime = this.mInjector.getElapsedRealtime() + j;
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(z ? 2 : 3, this.mNextLightAlarmTime, j2, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(z ? 2 : 3, this.mNextLightAlarmTime, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long getNextLightAlarmTimeForTesting() {
        long j;
        synchronized (this) {
            j = this.mNextLightAlarmTime;
        }
        return j;
    }

    private void scheduleMotionRegistrationAlarmLocked() {
        long elapsedRealtime = this.mInjector.getElapsedRealtime() + (this.mConstants.MOTION_INACTIVE_TIMEOUT / 2);
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, elapsedRealtime, this.mConstants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, elapsedRealtime, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
        }
    }

    private void scheduleMotionTimeoutAlarmLocked() {
        long elapsedRealtime = this.mInjector.getElapsedRealtime() + this.mConstants.MOTION_INACTIVE_TIMEOUT;
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, elapsedRealtime, this.mConstants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, elapsedRealtime, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void scheduleSensingTimeoutAlarmLocked(long j) {
        this.mNextSensingTimeoutAlarmTime = android.os.SystemClock.elapsedRealtime() + j;
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, this.mNextSensingTimeoutAlarmTime, this.mConstants.FLEX_TIME_SHORT, "DeviceIdleController.sensing", this.mSensingTimeoutAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, this.mNextSensingTimeoutAlarmTime, "DeviceIdleController.sensing", this.mSensingTimeoutAlarmListener, this.mHandler);
        }
    }

    private static int[] buildAppIdArray(android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap2, android.util.SparseBooleanArray sparseBooleanArray) {
        sparseBooleanArray.clear();
        if (arrayMap != null) {
            for (int i = 0; i < arrayMap.size(); i++) {
                sparseBooleanArray.put(arrayMap.valueAt(i).intValue(), true);
            }
        }
        if (arrayMap2 != null) {
            for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
                sparseBooleanArray.put(arrayMap2.valueAt(i2).intValue(), true);
            }
        }
        int size = sparseBooleanArray.size();
        int[] iArr = new int[size];
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = sparseBooleanArray.keyAt(i3);
        }
        return iArr;
    }

    private void updateWhitelistAppIdsLocked() {
        this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
        this.mPowerSaveWhitelistAllAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistApps, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistAllAppIds);
        this.mPowerSaveWhitelistUserAppIdArray = buildAppIdArray(null, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistUserAppIds);
        if (this.mLocalActivityManager != null) {
            this.mLocalActivityManager.setDeviceIdleAllowlist(this.mPowerSaveWhitelistAllAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray);
        }
        if (this.mLocalPowerManager != null) {
            this.mLocalPowerManager.setDeviceIdleWhitelist(this.mPowerSaveWhitelistAllAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateTempWhitelistAppIdsLocked(int i, boolean z, long j, int i2, int i3, @android.annotation.Nullable java.lang.String str, int i4) {
        int size = this.mTempWhitelistAppIdEndTimes.size();
        if (this.mTempWhitelistAppIdArray.length != size) {
            this.mTempWhitelistAppIdArray = new int[size];
        }
        for (int i5 = 0; i5 < size; i5++) {
            this.mTempWhitelistAppIdArray[i5] = this.mTempWhitelistAppIdEndTimes.keyAt(i5);
        }
        if (this.mLocalActivityManager != null) {
            this.mLocalActivityManager.updateDeviceIdleTempAllowlist(this.mTempWhitelistAppIdArray, i, z, j, i2, i3, str, i4);
        }
        if (this.mLocalPowerManager != null) {
            this.mLocalPowerManager.setDeviceIdleTempWhitelist(this.mTempWhitelistAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    private void reportPowerSaveWhitelistChangedLocked() {
        getContext().sendBroadcastAsUser(this.mPowerSaveWhitelistChangedIntent, android.os.UserHandle.SYSTEM, null, this.mPowerSaveWhitelistChangedOptions);
    }

    private void reportTempWhitelistChangedLocked(int i, boolean z) {
        this.mHandler.obtainMessage(13, i, z ? 1 : 0).sendToTarget();
        getContext().sendBroadcastAsUser(this.mPowerSaveTempWhitelistChangedIntent, android.os.UserHandle.SYSTEM, null, this.mPowerSaveTempWhilelistChangedOptions);
    }

    private void passWhiteListsToForceAppStandbyTrackerLocked() {
        this.mAppStateTracker.setPowerSaveExemptionListAppIds(this.mPowerSaveWhitelistSystemAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray, this.mPowerSaveWhitelistUserAppIdArray, this.mTempWhitelistAppIdArray);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void readConfigFileLocked() {
        this.mPowerSaveWhitelistUserApps.clear();
        try {
            try {
                java.io.FileInputStream openRead = this.mConfigFile.openRead();
                try {
                    org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                    newPullParser.setInput(openRead, java.nio.charset.StandardCharsets.UTF_8.name());
                    readConfigFileLocked(newPullParser);
                    openRead.close();
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    openRead.close();
                } catch (java.lang.Throwable th) {
                    try {
                        openRead.close();
                    } catch (java.io.IOException e2) {
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
            }
        } catch (java.io.FileNotFoundException e4) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0045, code lost:
    
        if (r4 != 4) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0048, code lost:
    
        r4 = r9.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
    
        switch(r4.hashCode()) {
            case 3797: goto L39;
            case 111376009: goto L36;
            default: goto L35;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006a, code lost:
    
        r4 = 65535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006f, code lost:
    
        switch(r4) {
            case 0: goto L78;
            case 1: goto L77;
            default: goto L80;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:
    
        r4 = r9.getAttributeValue(null, "n");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0098, code lost:
    
        if (r8.mPowerSaveWhitelistApps.containsKey(r4) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009a, code lost:
    
        r8.mRemovedFromSystemWhitelistApps.put(r4, r8.mPowerSaveWhitelistApps.remove(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a8, code lost:
    
        r4 = r9.getAttributeValue(null, "n");
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ac, code lost:
    
        if (r4 == null) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b0, code lost:
    
        r4 = r2.getApplicationInfo(r4, 4194304);
        r8.mPowerSaveWhitelistUserApps.put(r4.packageName, java.lang.Integer.valueOf(android.os.UserHandle.getAppId(r4.uid)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0072, code lost:
    
        android.util.Slog.w(com.android.server.DeviceIdleController.TAG, "Unknown element under <config>: " + r9.getName());
        com.android.internal.util.jobs.XmlUtils.skipCurrentTag(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x005b, code lost:
    
        if (r4.equals("un-wl") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x005d, code lost:
    
        r4 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0066, code lost:
    
        if (r4.equals("wl") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0068, code lost:
    
        r4 = 0;
     */
    @com.android.internal.annotations.GuardedBy({"this"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readConfigFileLocked(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        int next;
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed parsing config " + e);
                return;
            } catch (java.lang.IllegalStateException e2) {
                android.util.Slog.w(TAG, "Failed parsing config " + e2);
                return;
            } catch (java.lang.IndexOutOfBoundsException e3) {
                android.util.Slog.w(TAG, "Failed parsing config " + e3);
                return;
            } catch (java.lang.NullPointerException e4) {
                android.util.Slog.w(TAG, "Failed parsing config " + e4);
                return;
            } catch (java.lang.NumberFormatException e5) {
                android.util.Slog.w(TAG, "Failed parsing config " + e5);
                return;
            } catch (org.xmlpull.v1.XmlPullParserException e6) {
                android.util.Slog.w(TAG, "Failed parsing config " + e6);
                return;
            }
        } while (next != 1);
        if (next != 2) {
            throw new java.lang.IllegalStateException("no start tag found");
        }
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                if (next2 == 3 && xmlPullParser.getDepth() <= depth) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    void writeConfigFileLocked() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 5000L);
    }

    void handleWriteConfigFile() {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            synchronized (this) {
                org.xmlpull.v1.XmlSerializer fastXmlSerializer = new com.android.internal.util.jobs.FastXmlSerializer();
                fastXmlSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                writeConfigFileLocked(fastXmlSerializer);
            }
        } catch (java.io.IOException e) {
        }
        synchronized (this.mConfigFile) {
            java.io.FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = this.mConfigFile.startWrite();
                byteArrayOutputStream.writeTo(fileOutputStream);
                this.mConfigFile.finishWrite(fileOutputStream);
            } catch (java.io.IOException e2) {
                android.util.Slog.w(TAG, "Error writing config file", e2);
                this.mConfigFile.failWrite(fileOutputStream);
            }
        }
    }

    void writeConfigFileLocked(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startDocument(null, true);
        xmlSerializer.startTag(null, "config");
        for (int i = 0; i < this.mPowerSaveWhitelistUserApps.size(); i++) {
            java.lang.String keyAt = this.mPowerSaveWhitelistUserApps.keyAt(i);
            xmlSerializer.startTag(null, "wl");
            xmlSerializer.attribute(null, "n", keyAt);
            xmlSerializer.endTag(null, "wl");
        }
        for (int i2 = 0; i2 < this.mRemovedFromSystemWhitelistApps.size(); i2++) {
            xmlSerializer.startTag(null, "un-wl");
            xmlSerializer.attribute(null, "n", this.mRemovedFromSystemWhitelistApps.keyAt(i2));
            xmlSerializer.endTag(null, "un-wl");
        }
        xmlSerializer.endTag(null, "config");
        xmlSerializer.endDocument();
    }

    static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Device idle controller (deviceidle) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  step [light|deep]");
        printWriter.println("    Immediately step to next state, without waiting for alarm.");
        printWriter.println("  force-idle [light|deep]");
        printWriter.println("    Force directly into idle mode, regardless of other device state.");
        printWriter.println("  force-inactive");
        printWriter.println("    Force to be inactive, ready to freely step idle states.");
        printWriter.println("  unforce");
        printWriter.println("    Resume normal functioning after force-idle or force-inactive or force-modemanager-quickdoze.");
        printWriter.println("  get [light|deep|force|screen|charging|network|offbody|forceoffbody]");
        printWriter.println("    Retrieve the current given state.");
        printWriter.println("  disable [light|deep|all]");
        printWriter.println("    Completely disable device idle mode.");
        printWriter.println("  enable [light|deep|all]");
        printWriter.println("    Re-enable device idle mode after it had previously been disabled.");
        printWriter.println("  enabled [light|deep|all]");
        printWriter.println("    Print 1 if device idle mode is currently enabled, else 0.");
        printWriter.println("  whitelist");
        printWriter.println("    Print currently whitelisted apps.");
        printWriter.println("  whitelist [package ...]");
        printWriter.println("    Add (prefix with +) or remove (prefix with -) packages.");
        printWriter.println("  sys-whitelist [package ...|reset]");
        printWriter.println("    Prefix the package with '-' to remove it from the system whitelist or '+' to put it back in the system whitelist.");
        printWriter.println("    Note that only packages that were earlier removed from the system whitelist can be added back.");
        printWriter.println("    reset will reset the whitelist to the original state");
        printWriter.println("    Prints the system whitelist if no arguments are specified");
        printWriter.println("  except-idle-whitelist [package ...|reset]");
        printWriter.println("    Prefix the package with '+' to add it to whitelist or '=' to check if it is already whitelisted");
        printWriter.println("    [reset] will reset the whitelist to it's original state");
        printWriter.println("    Note that unlike <whitelist> cmd, changes made using this won't be persisted across boots");
        printWriter.println("  tempwhitelist");
        printWriter.println("    Print packages that are temporarily whitelisted.");
        printWriter.println("  tempwhitelist [-u USER] [-d DURATION] [-r] [package]");
        printWriter.println("    Temporarily place package in whitelist for DURATION milliseconds.");
        printWriter.println("    If no DURATION is specified, 10 seconds is used");
        printWriter.println("    If [-r] option is used, then the package is removed from temp whitelist and any [-d] is ignored");
        printWriter.println("  motion");
        printWriter.println("    Simulate a motion event to bring the device out of deep doze");
        printWriter.println("  force-modemanager-quickdoze [true|false]");
        printWriter.println("    Simulate mode manager request to enable (true) or disable (false) quick doze. Mode manager changes will be ignored until unforce is called.");
        printWriter.println("  force-modemanager-offbody [true|false]");
        printWriter.println("    Force mode manager offbody state, this can be used to simulate device being off-body (true) or on-body (false). Mode manager changes will be ignored until unforce is called.");
    }

    class Shell extends android.os.ShellCommand {
        int userId = 0;

        Shell() {
        }

        public int onCommand(java.lang.String str) {
            return com.android.server.DeviceIdleController.this.onShellCommand(this, str);
        }

        public void onHelp() {
            com.android.server.DeviceIdleController.dumpHelp(getOutPrintWriter());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x037a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x038a, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x04d3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x04db, code lost:
    
        throw r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:232:0x03fc A[Catch: all -> 0x03bd, Merged into TryCatch #8 {all -> 0x0439, all -> 0x03bd, blocks: (B:219:0x039d, B:244:0x0432, B:245:0x0436, B:242:0x043c, B:243:0x043f, B:254:0x03a9, B:256:0x03b1, B:226:0x03d5, B:228:0x03de, B:232:0x03fc, B:235:0x0409, B:238:0x041e, B:248:0x03e9, B:250:0x03ee, B:222:0x03c1, B:224:0x03c6), top: B:218:0x039d }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x041e A[Catch: all -> 0x03bd, Merged into TryCatch #8 {all -> 0x0439, all -> 0x03bd, blocks: (B:219:0x039d, B:244:0x0432, B:245:0x0436, B:242:0x043c, B:243:0x043f, B:254:0x03a9, B:256:0x03b1, B:226:0x03d5, B:228:0x03de, B:232:0x03fc, B:235:0x0409, B:238:0x041e, B:248:0x03e9, B:250:0x03ee, B:222:0x03c1, B:224:0x03c6), top: B:218:0x039d }, TRY_LEAVE] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x03ee A[Catch: all -> 0x03bd, Merged into TryCatch #8 {all -> 0x0439, all -> 0x03bd, blocks: (B:219:0x039d, B:244:0x0432, B:245:0x0436, B:242:0x043c, B:243:0x043f, B:254:0x03a9, B:256:0x03b1, B:226:0x03d5, B:228:0x03de, B:232:0x03fc, B:235:0x0409, B:238:0x041e, B:248:0x03e9, B:250:0x03ee, B:222:0x03c1, B:224:0x03c6), top: B:218:0x039d }] */
    /* JADX WARN: Removed duplicated region for block: B:251:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x04b3 A[Catch: all -> 0x0474, Merged into TryCatch #24 {all -> 0x04d3, all -> 0x0474, blocks: (B:268:0x0454, B:289:0x04cc, B:290:0x04d0, B:287:0x04d6, B:288:0x04d9, B:299:0x0460, B:301:0x0468, B:275:0x048c, B:277:0x0495, B:281:0x04b3, B:283:0x04b8, B:293:0x04a0, B:295:0x04a5, B:271:0x0478, B:273:0x047d), top: B:267:0x0454 }] */
    /* JADX WARN: Removed duplicated region for block: B:283:0x04b8 A[Catch: all -> 0x0474, Merged into TryCatch #24 {all -> 0x04d3, all -> 0x0474, blocks: (B:268:0x0454, B:289:0x04cc, B:290:0x04d0, B:287:0x04d6, B:288:0x04d9, B:299:0x0460, B:301:0x0468, B:275:0x048c, B:277:0x0495, B:281:0x04b3, B:283:0x04b8, B:293:0x04a0, B:295:0x04a5, B:271:0x0478, B:273:0x047d), top: B:267:0x0454 }, TRY_LEAVE] */
    /* JADX WARN: Removed duplicated region for block: B:295:0x04a5 A[Catch: all -> 0x0474, Merged into TryCatch #24 {all -> 0x04d3, all -> 0x0474, blocks: (B:268:0x0454, B:289:0x04cc, B:290:0x04d0, B:287:0x04d6, B:288:0x04d9, B:299:0x0460, B:301:0x0468, B:275:0x048c, B:277:0x0495, B:281:0x04b3, B:283:0x04b8, B:293:0x04a0, B:295:0x04a5, B:271:0x0478, B:273:0x047d), top: B:267:0x0454 }] */
    /* JADX WARN: Removed duplicated region for block: B:296:0x04af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int onShellCommand(com.android.server.DeviceIdleController.Shell shell, java.lang.String str) {
        long clearCallingIdentity;
        char c;
        boolean z;
        char c2;
        boolean z2;
        java.io.PrintWriter outPrintWriter = shell.getOutPrintWriter();
        if ("step".equals(str)) {
            getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (this) {
                try {
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    java.lang.String nextArg = shell.getNextArg();
                    if (nextArg != null) {
                        if (!"deep".equals(nextArg)) {
                            if ("light".equals(nextArg)) {
                                stepLightIdleStateLocked("s:shell");
                                outPrintWriter.print("Stepped to light: ");
                                outPrintWriter.println(lightStateToString(this.mLightState));
                            } else {
                                outPrintWriter.println("Unknown idle mode: " + nextArg);
                            }
                        }
                    }
                    stepIdleStateLocked("s:shell");
                    outPrintWriter.print("Stepped to deep: ");
                    outPrintWriter.println(stateToString(this.mState));
                } catch (java.lang.Throwable th) {
                    throw th;
                } finally {
                }
            }
        } else {
            char c3 = 1;
            if ("force-active".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mForceIdle = true;
                            becomeActiveLocked("force-active", android.os.Process.myUid());
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                        } finally {
                        }
                    } finally {
                    }
                }
            } else if ("force-idle".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    java.lang.String nextArg2 = shell.getNextArg();
                    if (nextArg2 != null) {
                        try {
                            if (!"deep".equals(nextArg2)) {
                                if ("light".equals(nextArg2)) {
                                    this.mForceIdle = true;
                                    becomeInactiveIfAppropriateLocked();
                                    int i = this.mLightState;
                                    while (i != 4) {
                                        stepLightIdleStateLocked("s:shell");
                                        if (i == this.mLightState) {
                                            outPrintWriter.print("Unable to go light idle; stopped at ");
                                            outPrintWriter.println(lightStateToString(this.mLightState));
                                            exitForceIdleLocked();
                                            return -1;
                                        }
                                        i = this.mLightState;
                                    }
                                    outPrintWriter.println("Now forced in to light idle mode");
                                } else {
                                    outPrintWriter.println("Unknown idle mode: " + nextArg2);
                                }
                            }
                        } finally {
                        }
                    }
                    if (!this.mDeepEnabled) {
                        outPrintWriter.println("Unable to go deep idle; not enabled");
                        return -1;
                    }
                    this.mForceIdle = true;
                    becomeInactiveIfAppropriateLocked();
                    int i2 = this.mState;
                    while (i2 != 5) {
                        stepIdleStateLocked("s:shell");
                        if (i2 == this.mState) {
                            outPrintWriter.print("Unable to go deep idle; stopped at ");
                            outPrintWriter.println(stateToString(this.mState));
                            exitForceIdleLocked();
                            return -1;
                        }
                        i2 = this.mState;
                    }
                    outPrintWriter.println("Now forced in to deep idle mode");
                }
            } else if ("force-inactive".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mForceIdle = true;
                            becomeInactiveIfAppropriateLocked();
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                        } finally {
                        }
                    } finally {
                    }
                }
            } else if ("unforce".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            exitForceIdleLocked();
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                            this.mForceModeManagerQuickDozeRequest = false;
                            outPrintWriter.println("mForceModeManagerQuickDozeRequest: " + this.mForceModeManagerQuickDozeRequest);
                            this.mForceModeManagerOffBodyState = false;
                            outPrintWriter.println("mForceModeManagerOffBodyState: " + this.mForceModeManagerOffBodyState);
                        } finally {
                        }
                    } finally {
                    }
                }
            } else if ("get".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        java.lang.String nextArg3 = shell.getNextArg();
                        if (nextArg3 != null) {
                            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            switch (nextArg3.hashCode()) {
                                case -2127754130:
                                    if (nextArg3.equals("forcemodemanagerquick")) {
                                        c3 = '\b';
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -1614760061:
                                    if (nextArg3.equals("modemanagerquick")) {
                                        c3 = 7;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -1548904559:
                                    if (nextArg3.equals("offbody")) {
                                        c3 = '\t';
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case -907689876:
                                    if (nextArg3.equals("screen")) {
                                        c3 = 4;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 3079404:
                                    if (nextArg3.equals("deep")) {
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 97618667:
                                    if (nextArg3.equals("force")) {
                                        c3 = 2;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 102970646:
                                    if (nextArg3.equals("light")) {
                                        c3 = 0;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 107947501:
                                    if (nextArg3.equals("quick")) {
                                        c3 = 3;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 1436115569:
                                    if (nextArg3.equals("charging")) {
                                        c3 = 5;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 1587609158:
                                    if (nextArg3.equals("forceoffbody")) {
                                        c3 = '\n';
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                case 1843485230:
                                    if (nextArg3.equals("network")) {
                                        c3 = 6;
                                        break;
                                    }
                                    c3 = 65535;
                                    break;
                                default:
                                    c3 = 65535;
                                    break;
                            }
                            switch (c3) {
                                case 0:
                                    outPrintWriter.println(lightStateToString(this.mLightState));
                                    break;
                                case 1:
                                    outPrintWriter.println(stateToString(this.mState));
                                    break;
                                case 2:
                                    outPrintWriter.println(this.mForceIdle);
                                    break;
                                case 3:
                                    outPrintWriter.println(this.mQuickDozeActivated);
                                    break;
                                case 4:
                                    outPrintWriter.println(this.mScreenOn);
                                    break;
                                case 5:
                                    outPrintWriter.println(this.mCharging);
                                    break;
                                case 6:
                                    outPrintWriter.println(this.mNetworkConnected);
                                    break;
                                case 7:
                                    outPrintWriter.println(this.mModeManagerRequestedQuickDoze);
                                    break;
                                case '\b':
                                    outPrintWriter.println(this.mForceModeManagerQuickDozeRequest);
                                    break;
                                case '\t':
                                    outPrintWriter.println(this.mIsOffBody);
                                    break;
                                case '\n':
                                    outPrintWriter.println(this.mForceModeManagerOffBodyState);
                                    break;
                                default:
                                    outPrintWriter.println("Unknown get option: " + nextArg3);
                                    break;
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } else {
                            outPrintWriter.println("Argument required");
                        }
                    } finally {
                    }
                }
            } else if ("disable".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        java.lang.String nextArg4 = shell.getNextArg();
                        if (nextArg4 != null) {
                            if (!"deep".equals(nextArg4) && !"all".equals(nextArg4)) {
                                c2 = 0;
                                z2 = false;
                                if (nextArg4 == null && !"light".equals(nextArg4) && !"all".equals(nextArg4)) {
                                    c3 = c2;
                                } else if (this.mLightEnabled) {
                                    z2 = true;
                                    c3 = c2;
                                } else {
                                    this.mLightEnabled = false;
                                    outPrintWriter.println("Light idle mode disabled");
                                    z2 = true;
                                }
                                if (c3 != 0) {
                                    this.mActiveReason = 6;
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    sb.append(nextArg4 == null ? "all" : nextArg4);
                                    sb.append("-disabled");
                                    becomeActiveLocked(sb.toString(), android.os.Process.myUid());
                                }
                                if (!z2) {
                                    outPrintWriter.println("Unknown idle mode: " + nextArg4);
                                }
                            }
                        }
                        if (this.mDeepEnabled) {
                            this.mDeepEnabled = false;
                            outPrintWriter.println("Deep idle mode disabled");
                            c2 = 1;
                            z2 = true;
                        } else {
                            z2 = true;
                            c2 = 0;
                        }
                        if (nextArg4 == null) {
                        }
                        if (this.mLightEnabled) {
                        }
                        if (c3 != 0) {
                        }
                        if (!z2) {
                        }
                    } catch (java.lang.Throwable th2) {
                        throw th2;
                    } finally {
                    }
                }
            } else if ("enable".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        java.lang.String nextArg5 = shell.getNextArg();
                        if (nextArg5 != null) {
                            if (!"deep".equals(nextArg5) && !"all".equals(nextArg5)) {
                                c = 0;
                                z = false;
                                if (nextArg5 == null && !"light".equals(nextArg5) && !"all".equals(nextArg5)) {
                                    c3 = c;
                                } else if (this.mLightEnabled) {
                                    this.mLightEnabled = true;
                                    outPrintWriter.println("Light idle mode enable");
                                    z = true;
                                } else {
                                    z = true;
                                    c3 = c;
                                }
                                if (c3 != 0) {
                                    becomeInactiveIfAppropriateLocked();
                                }
                                if (!z) {
                                    outPrintWriter.println("Unknown idle mode: " + nextArg5);
                                }
                            }
                        }
                        if (this.mDeepEnabled) {
                            z = true;
                            c = 0;
                        } else {
                            this.mDeepEnabled = true;
                            outPrintWriter.println("Deep idle mode enabled");
                            c = 1;
                            z = true;
                        }
                        if (nextArg5 == null) {
                        }
                        if (this.mLightEnabled) {
                        }
                        if (c3 != 0) {
                        }
                        if (!z) {
                        }
                    } finally {
                    }
                }
            } else if (com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED.equals(str)) {
                synchronized (this) {
                    try {
                        java.lang.String nextArg6 = shell.getNextArg();
                        if (nextArg6 == null || "all".equals(nextArg6)) {
                            if (this.mDeepEnabled && this.mLightEnabled) {
                                r6 = "1";
                            }
                            outPrintWriter.println(r6);
                        } else if ("deep".equals(nextArg6)) {
                            outPrintWriter.println(this.mDeepEnabled ? "1" : 0);
                        } else if ("light".equals(nextArg6)) {
                            outPrintWriter.println(this.mLightEnabled ? "1" : 0);
                        } else {
                            outPrintWriter.println("Unknown idle mode: " + nextArg6);
                        }
                    } finally {
                    }
                }
            } else if ("whitelist".equals(str)) {
                java.lang.String nextArg7 = shell.getNextArg();
                if (nextArg7 != null) {
                    getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    do {
                        try {
                            if (nextArg7.length() >= 1 && (nextArg7.charAt(0) == '-' || nextArg7.charAt(0) == '+' || nextArg7.charAt(0) == '=')) {
                                char charAt = nextArg7.charAt(0);
                                java.lang.String substring = nextArg7.substring(1);
                                if (charAt == '+') {
                                    if (addPowerSaveWhitelistAppsInternal(java.util.Collections.singletonList(substring)) == 1) {
                                        outPrintWriter.println("Added: " + substring);
                                    } else {
                                        outPrintWriter.println("Unknown package: " + substring);
                                    }
                                } else if (charAt != '-') {
                                    outPrintWriter.println(getPowerSaveWhitelistAppInternal(substring));
                                } else if (removePowerSaveWhitelistAppInternal(substring)) {
                                    outPrintWriter.println("Removed: " + substring);
                                }
                                nextArg7 = shell.getNextArg();
                            }
                            outPrintWriter.println("Package must be prefixed with +, -, or =: " + nextArg7);
                            return -1;
                        } finally {
                        }
                    } while (nextArg7 != null);
                }
                if (!com.android.internal.util.jobs.DumpUtils.checkDumpPermission(getContext(), TAG, outPrintWriter)) {
                    return -1;
                }
                synchronized (this) {
                    for (int i3 = 0; i3 < this.mPowerSaveWhitelistAppsExceptIdle.size(); i3++) {
                        try {
                            outPrintWriter.print("system-excidle,");
                            outPrintWriter.print(this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i3));
                            outPrintWriter.print(",");
                            outPrintWriter.println(this.mPowerSaveWhitelistAppsExceptIdle.valueAt(i3));
                        } finally {
                        }
                    }
                    for (int i4 = 0; i4 < this.mPowerSaveWhitelistApps.size(); i4++) {
                        outPrintWriter.print("system,");
                        outPrintWriter.print(this.mPowerSaveWhitelistApps.keyAt(i4));
                        outPrintWriter.print(",");
                        outPrintWriter.println(this.mPowerSaveWhitelistApps.valueAt(i4));
                    }
                    for (int i5 = 0; i5 < this.mPowerSaveWhitelistUserApps.size(); i5++) {
                        outPrintWriter.print("user,");
                        outPrintWriter.print(this.mPowerSaveWhitelistUserApps.keyAt(i5));
                        outPrintWriter.print(",");
                        outPrintWriter.println(this.mPowerSaveWhitelistUserApps.valueAt(i5));
                    }
                }
            } else if ("tempwhitelist".equals(str)) {
                long j = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
                boolean z3 = false;
                while (true) {
                    java.lang.String nextOption = shell.getNextOption();
                    if (nextOption == null) {
                        java.lang.String nextArg8 = shell.getNextArg();
                        if (nextArg8 != null) {
                            try {
                                if (z3) {
                                    removePowerSaveTempAllowlistAppChecked(nextArg8, shell.userId);
                                } else {
                                    addPowerSaveTempAllowlistAppChecked(nextArg8, j, shell.userId, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SHELL, "shell");
                                }
                            } catch (java.lang.Exception e) {
                                outPrintWriter.println("Failed: " + e);
                                return -1;
                            }
                        } else {
                            if (z3) {
                                outPrintWriter.println("[-r] requires a package name");
                                return -1;
                            }
                            if (!com.android.internal.util.jobs.DumpUtils.checkDumpPermission(getContext(), TAG, outPrintWriter)) {
                                return -1;
                            }
                            dumpTempWhitelistSchedule(outPrintWriter, false);
                        }
                    } else if ("-u".equals(nextOption)) {
                        java.lang.String nextArg9 = shell.getNextArg();
                        if (nextArg9 == null) {
                            outPrintWriter.println("-u requires a user number");
                            return -1;
                        }
                        shell.userId = java.lang.Integer.parseInt(nextArg9);
                    } else if ("-d".equals(nextOption)) {
                        java.lang.String nextArg10 = shell.getNextArg();
                        if (nextArg10 == null) {
                            outPrintWriter.println("-d requires a duration");
                            return -1;
                        }
                        j = java.lang.Long.parseLong(nextArg10);
                    } else if ("-r".equals(nextOption)) {
                        z3 = true;
                    }
                }
            } else if ("except-idle-whitelist".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String nextArg11 = shell.getNextArg();
                    if (nextArg11 == null) {
                        outPrintWriter.println("No arguments given");
                        return -1;
                    }
                    if (!"reset".equals(nextArg11)) {
                        while (nextArg11.length() >= 1 && (nextArg11.charAt(0) == '-' || nextArg11.charAt(0) == '+' || nextArg11.charAt(0) == '=')) {
                            char charAt2 = nextArg11.charAt(0);
                            java.lang.String substring2 = nextArg11.substring(1);
                            if (charAt2 != '+') {
                                if (charAt2 != '=') {
                                    outPrintWriter.println("Unknown argument: " + nextArg11);
                                    return -1;
                                }
                                outPrintWriter.println(getPowerSaveWhitelistExceptIdleInternal(substring2));
                            } else if (addPowerSaveWhitelistExceptIdleInternal(substring2)) {
                                outPrintWriter.println("Added: " + substring2);
                            } else {
                                outPrintWriter.println("Unknown package: " + substring2);
                            }
                            nextArg11 = shell.getNextArg();
                            if (nextArg11 == null) {
                            }
                        }
                        outPrintWriter.println("Package must be prefixed with +, -, or =: " + nextArg11);
                        return -1;
                    }
                    resetPowerSaveWhitelistExceptIdleInternal();
                } finally {
                }
            } else if ("sys-whitelist".equals(str)) {
                java.lang.String nextArg12 = shell.getNextArg();
                if (nextArg12 != null) {
                    getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!"reset".equals(nextArg12)) {
                            while (nextArg12.length() >= 1 && (nextArg12.charAt(0) == '-' || nextArg12.charAt(0) == '+')) {
                                char charAt3 = nextArg12.charAt(0);
                                java.lang.String substring3 = nextArg12.substring(1);
                                switch (charAt3) {
                                    case '+':
                                        if (restoreSystemPowerWhitelistAppInternal(substring3)) {
                                            outPrintWriter.println("Restored " + substring3);
                                            break;
                                        }
                                        break;
                                    case '-':
                                        if (removeSystemPowerWhitelistAppInternal(substring3)) {
                                            outPrintWriter.println("Removed " + substring3);
                                            break;
                                        }
                                        break;
                                }
                                nextArg12 = shell.getNextArg();
                                if (nextArg12 == null) {
                                }
                            }
                            outPrintWriter.println("Package must be prefixed with + or - " + nextArg12);
                            return -1;
                        }
                        resetSystemPowerWhitelistInternal();
                    } finally {
                    }
                } else {
                    if (!com.android.internal.util.jobs.DumpUtils.checkDumpPermission(getContext(), TAG, outPrintWriter)) {
                        return -1;
                    }
                    synchronized (this) {
                        for (int i6 = 0; i6 < this.mPowerSaveWhitelistApps.size(); i6++) {
                            try {
                                outPrintWriter.print(this.mPowerSaveWhitelistApps.keyAt(i6));
                                outPrintWriter.print(",");
                                outPrintWriter.println(this.mPowerSaveWhitelistApps.valueAt(i6));
                            } finally {
                            }
                        }
                    }
                }
            } else if ("motion".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            motionLocked();
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                        } finally {
                        }
                    } finally {
                    }
                }
            } else if ("force-modemanager-quickdoze".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                java.lang.String nextArg13 = shell.getNextArg();
                if (!"true".equalsIgnoreCase(nextArg13) && !"false".equalsIgnoreCase(nextArg13)) {
                    outPrintWriter.println("Provide true or false argument after force-modemanager-quickdoze");
                    return -1;
                }
                boolean parseBoolean = java.lang.Boolean.parseBoolean(nextArg13);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mForceModeManagerQuickDozeRequest = true;
                            outPrintWriter.println("mForceModeManagerQuickDozeRequest: " + this.mForceModeManagerQuickDozeRequest);
                            this.mModeManagerRequestedQuickDoze = parseBoolean;
                            outPrintWriter.println("mModeManagerRequestedQuickDoze: " + this.mModeManagerRequestedQuickDoze);
                            this.mModeManagerQuickDozeRequestConsumer.onModeManagerRequestChangedLocked();
                        } finally {
                        }
                    } finally {
                    }
                }
            } else {
                if (!"force-modemanager-offbody".equals(str)) {
                    return shell.handleDefaultCommands(str);
                }
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                java.lang.String nextArg14 = shell.getNextArg();
                if (!"true".equalsIgnoreCase(nextArg14) && !"false".equalsIgnoreCase(nextArg14)) {
                    outPrintWriter.println("Provide true or false argument after force-modemanager-offbody");
                    return -1;
                }
                boolean parseBoolean2 = java.lang.Boolean.parseBoolean(nextArg14);
                synchronized (this) {
                    try {
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mForceModeManagerOffBodyState = true;
                            outPrintWriter.println("mForceModeManagerOffBodyState: " + this.mForceModeManagerOffBodyState);
                            this.mIsOffBody = parseBoolean2;
                            outPrintWriter.println("mIsOffBody: " + this.mIsOffBody);
                            this.mModeManagerOffBodyStateConsumer.onModeManagerOffBodyChangedLocked();
                        } finally {
                        }
                    } finally {
                    }
                }
            }
        }
        return 0;
    }

    void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str;
        if (com.android.internal.util.jobs.DumpUtils.checkDumpPermission(getContext(), TAG, printWriter)) {
            if (strArr != null) {
                int i = 0;
                int i2 = 0;
                while (i < strArr.length) {
                    java.lang.String str2 = strArr[i];
                    if ("-h".equals(str2)) {
                        dumpHelp(printWriter);
                        return;
                    }
                    if ("-u".equals(str2)) {
                        i++;
                        if (i < strArr.length) {
                            i2 = java.lang.Integer.parseInt(strArr[i]);
                        }
                    } else if (!"-a".equals(str2)) {
                        if (str2.length() > 0 && str2.charAt(0) == '-') {
                            printWriter.println("Unknown option: " + str2);
                            return;
                        }
                        com.android.server.DeviceIdleController.Shell shell = new com.android.server.DeviceIdleController.Shell();
                        shell.userId = i2;
                        java.lang.String[] strArr2 = new java.lang.String[strArr.length - i];
                        java.lang.System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
                        shell.exec(this.mBinderService, (java.io.FileDescriptor) null, fileDescriptor, (java.io.FileDescriptor) null, strArr2, (android.os.ShellCallback) null, new android.os.ResultReceiver(null));
                        return;
                    }
                    i++;
                }
            }
            synchronized (this) {
                try {
                    this.mConstants.dump(printWriter);
                    if (this.mEventCmds[0] != 0) {
                        printWriter.println("  Idling history:");
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        for (int i3 = 99; i3 >= 0; i3--) {
                            if (this.mEventCmds[i3] != 0) {
                                switch (this.mEventCmds[i3]) {
                                    case 1:
                                        str = "     normal";
                                        break;
                                    case 2:
                                        str = " light-idle";
                                        break;
                                    case 3:
                                        str = "light-maint";
                                        break;
                                    case 4:
                                        str = "  deep-idle";
                                        break;
                                    case 5:
                                        str = " deep-maint";
                                        break;
                                    default:
                                        str = "         ??";
                                        break;
                                }
                                printWriter.print("    ");
                                printWriter.print(str);
                                printWriter.print(": ");
                                android.util.TimeUtils.formatDuration(this.mEventTimes[i3], elapsedRealtime, printWriter);
                                if (this.mEventReasons[i3] != null) {
                                    printWriter.print(" (");
                                    printWriter.print(this.mEventReasons[i3]);
                                    printWriter.print(")");
                                }
                                printWriter.println();
                            }
                        }
                    }
                    int size = this.mPowerSaveWhitelistAppsExceptIdle.size();
                    if (size > 0) {
                        printWriter.println("  Whitelist (except idle) system apps:");
                        for (int i4 = 0; i4 < size; i4++) {
                            printWriter.print("    ");
                            printWriter.println(this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i4));
                        }
                    }
                    int size2 = this.mPowerSaveWhitelistApps.size();
                    if (size2 > 0) {
                        printWriter.println("  Whitelist system apps:");
                        for (int i5 = 0; i5 < size2; i5++) {
                            printWriter.print("    ");
                            printWriter.println(this.mPowerSaveWhitelistApps.keyAt(i5));
                        }
                    }
                    int size3 = this.mRemovedFromSystemWhitelistApps.size();
                    if (size3 > 0) {
                        printWriter.println("  Removed from whitelist system apps:");
                        for (int i6 = 0; i6 < size3; i6++) {
                            printWriter.print("    ");
                            printWriter.println(this.mRemovedFromSystemWhitelistApps.keyAt(i6));
                        }
                    }
                    int size4 = this.mPowerSaveWhitelistUserApps.size();
                    if (size4 > 0) {
                        printWriter.println("  Whitelist user apps:");
                        for (int i7 = 0; i7 < size4; i7++) {
                            printWriter.print("    ");
                            printWriter.println(this.mPowerSaveWhitelistUserApps.keyAt(i7));
                        }
                    }
                    int size5 = this.mPowerSaveWhitelistExceptIdleAppIds.size();
                    if (size5 > 0) {
                        printWriter.println("  Whitelist (except idle) all app ids:");
                        for (int i8 = 0; i8 < size5; i8++) {
                            printWriter.print("    ");
                            printWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i8));
                            printWriter.println();
                        }
                    }
                    int size6 = this.mPowerSaveWhitelistUserAppIds.size();
                    if (size6 > 0) {
                        printWriter.println("  Whitelist user app ids:");
                        for (int i9 = 0; i9 < size6; i9++) {
                            printWriter.print("    ");
                            printWriter.print(this.mPowerSaveWhitelistUserAppIds.keyAt(i9));
                            printWriter.println();
                        }
                    }
                    int size7 = this.mPowerSaveWhitelistAllAppIds.size();
                    if (size7 > 0) {
                        printWriter.println("  Whitelist all app ids:");
                        for (int i10 = 0; i10 < size7; i10++) {
                            printWriter.print("    ");
                            printWriter.print(this.mPowerSaveWhitelistAllAppIds.keyAt(i10));
                            printWriter.println();
                        }
                    }
                    dumpTempWhitelistSchedule(printWriter, true);
                    int length = this.mTempWhitelistAppIdArray != null ? this.mTempWhitelistAppIdArray.length : 0;
                    if (length > 0) {
                        printWriter.println("  Temp whitelist app ids:");
                        for (int i11 = 0; i11 < length; i11++) {
                            printWriter.print("    ");
                            printWriter.print(this.mTempWhitelistAppIdArray[i11]);
                            printWriter.println();
                        }
                    }
                    printWriter.print("  mLightEnabled=");
                    printWriter.print(this.mLightEnabled);
                    printWriter.print("  mDeepEnabled=");
                    printWriter.println(this.mDeepEnabled);
                    printWriter.print("  mForceIdle=");
                    printWriter.println(this.mForceIdle);
                    printWriter.print("  mUseMotionSensor=");
                    printWriter.print(this.mUseMotionSensor);
                    if (this.mUseMotionSensor) {
                        printWriter.print(" mMotionSensor=");
                        printWriter.println(this.mMotionSensor);
                    } else {
                        printWriter.println();
                    }
                    printWriter.print("  mScreenOn=");
                    printWriter.println(this.mScreenOn);
                    printWriter.print("  mScreenLocked=");
                    printWriter.println(this.mScreenLocked);
                    printWriter.print("  mNetworkConnected=");
                    printWriter.println(this.mNetworkConnected);
                    printWriter.print("  mCharging=");
                    printWriter.println(this.mCharging);
                    printWriter.print("  activeEmergencyCall=");
                    printWriter.println(this.mEmergencyCallListener.isEmergencyCallActive());
                    if (this.mConstraints.size() != 0) {
                        printWriter.println("  mConstraints={");
                        for (int i12 = 0; i12 < this.mConstraints.size(); i12++) {
                            com.android.server.deviceidle.DeviceIdleConstraintTracker valueAt = this.mConstraints.valueAt(i12);
                            printWriter.print("    \"");
                            printWriter.print(valueAt.name);
                            printWriter.print("\"=");
                            if (valueAt.minState == this.mState) {
                                printWriter.println(valueAt.active);
                            } else {
                                printWriter.print("ignored <mMinState=");
                                printWriter.print(stateToString(valueAt.minState));
                                printWriter.println(">");
                            }
                        }
                        printWriter.println("  }");
                    }
                    if (this.mUseMotionSensor || this.mStationaryListeners.size() > 0) {
                        printWriter.print("  mMotionActive=");
                        printWriter.println(this.mMotionListener.active);
                        printWriter.print("  mNotMoving=");
                        printWriter.println(this.mNotMoving);
                        printWriter.print("  mMotionListener.activatedTimeElapsed=");
                        printWriter.println(this.mMotionListener.activatedTimeElapsed);
                        printWriter.print("  mLastMotionEventElapsed=");
                        printWriter.println(this.mLastMotionEventElapsed);
                        printWriter.print("  ");
                        printWriter.print(this.mStationaryListeners.size());
                        printWriter.println(" stationary listeners registered");
                    }
                    if (this.mIsLocationPrefetchEnabled) {
                        printWriter.print("  mLocating=");
                        printWriter.print(this.mLocating);
                        printWriter.print(" mHasGps=");
                        printWriter.print(this.mHasGps);
                        printWriter.print(" mHasFused=");
                        printWriter.print(this.mHasFusedLocation);
                        printWriter.print(" mLocated=");
                        printWriter.println(this.mLocated);
                        if (this.mLastGenericLocation != null) {
                            printWriter.print("  mLastGenericLocation=");
                            printWriter.println(this.mLastGenericLocation);
                        }
                        if (this.mLastGpsLocation != null) {
                            printWriter.print("  mLastGpsLocation=");
                            printWriter.println(this.mLastGpsLocation);
                        }
                    } else {
                        printWriter.println("  Location prefetching disabled");
                    }
                    printWriter.print("  mState=");
                    printWriter.print(stateToString(this.mState));
                    printWriter.print(" mLightState=");
                    printWriter.println(lightStateToString(this.mLightState));
                    printWriter.print("  mInactiveTimeout=");
                    android.util.TimeUtils.formatDuration(this.mInactiveTimeout, printWriter);
                    printWriter.println();
                    if (this.mActiveIdleOpCount != 0) {
                        printWriter.print("  mActiveIdleOpCount=");
                        printWriter.println(this.mActiveIdleOpCount);
                    }
                    if (this.mNextAlarmTime != 0) {
                        printWriter.print("  mNextAlarmTime=");
                        android.util.TimeUtils.formatDuration(this.mNextAlarmTime, android.os.SystemClock.elapsedRealtime(), printWriter);
                        printWriter.println();
                    }
                    if (this.mNextIdlePendingDelay != 0) {
                        printWriter.print("  mNextIdlePendingDelay=");
                        android.util.TimeUtils.formatDuration(this.mNextIdlePendingDelay, printWriter);
                        printWriter.println();
                    }
                    if (this.mNextIdleDelay != 0) {
                        printWriter.print("  mNextIdleDelay=");
                        android.util.TimeUtils.formatDuration(this.mNextIdleDelay, printWriter);
                        printWriter.println();
                    }
                    if (this.mNextLightIdleDelay != 0) {
                        printWriter.print("  mNextLightIdleDelay=");
                        android.util.TimeUtils.formatDuration(this.mNextLightIdleDelay, printWriter);
                        if (this.mConstants.USE_WINDOW_ALARMS) {
                            printWriter.print(" (flex=");
                            android.util.TimeUtils.formatDuration(this.mNextLightIdleDelayFlex, printWriter);
                            printWriter.println(")");
                        } else {
                            printWriter.println();
                        }
                    }
                    if (this.mNextLightAlarmTime != 0) {
                        printWriter.print("  mNextLightAlarmTime=");
                        android.util.TimeUtils.formatDuration(this.mNextLightAlarmTime, android.os.SystemClock.elapsedRealtime(), printWriter);
                        printWriter.println();
                    }
                    if (this.mCurLightIdleBudget != 0) {
                        printWriter.print("  mCurLightIdleBudget=");
                        android.util.TimeUtils.formatDuration(this.mCurLightIdleBudget, printWriter);
                        printWriter.println();
                    }
                    if (this.mMaintenanceStartTime != 0) {
                        printWriter.print("  mMaintenanceStartTime=");
                        android.util.TimeUtils.formatDuration(this.mMaintenanceStartTime, android.os.SystemClock.elapsedRealtime(), printWriter);
                        printWriter.println();
                    }
                    if (this.mJobsActive) {
                        printWriter.print("  mJobsActive=");
                        printWriter.println(this.mJobsActive);
                    }
                    if (this.mAlarmsActive) {
                        printWriter.print("  mAlarmsActive=");
                        printWriter.println(this.mAlarmsActive);
                    }
                    if (this.mConstants.USE_MODE_MANAGER) {
                        printWriter.print("  mModeManagerRequestedQuickDoze=");
                        printWriter.println(this.mModeManagerRequestedQuickDoze);
                        printWriter.print("  mIsOffBody=");
                        printWriter.println(this.mIsOffBody);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void dumpTempWhitelistSchedule(java.io.PrintWriter printWriter, boolean z) {
        java.lang.String str;
        int size = this.mTempWhitelistAppIdEndTimes.size();
        if (size > 0) {
            if (!z) {
                str = "";
            } else {
                printWriter.println("  Temp whitelist schedule:");
                str = "    ";
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("UID=");
                printWriter.print(this.mTempWhitelistAppIdEndTimes.keyAt(i));
                printWriter.print(": ");
                android.util.Pair<android.util.MutableLong, java.lang.String> valueAt = this.mTempWhitelistAppIdEndTimes.valueAt(i);
                android.util.TimeUtils.formatDuration(((android.util.MutableLong) valueAt.first).value, elapsedRealtime, printWriter);
                printWriter.print(" - ");
                printWriter.println((java.lang.String) valueAt.second);
            }
        }
    }
}
