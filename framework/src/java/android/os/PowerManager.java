package android.os;

/* loaded from: classes3.dex */
public final class PowerManager {

    @java.lang.Deprecated
    public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
    public static final java.lang.String ACTION_DEVICE_IDLE_MODE_CHANGED = "android.os.action.DEVICE_IDLE_MODE_CHANGED";
    public static final java.lang.String ACTION_DEVICE_LIGHT_IDLE_MODE_CHANGED = "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
    public static final java.lang.String ACTION_ENHANCED_DISCHARGE_PREDICTION_CHANGED = "android.os.action.ENHANCED_DISCHARGE_PREDICTION_CHANGED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_LIGHT_DEVICE_IDLE_MODE_CHANGED = "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
    public static final java.lang.String ACTION_LOW_POWER_STANDBY_ENABLED_CHANGED = "android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED";
    public static final java.lang.String ACTION_LOW_POWER_STANDBY_POLICY_CHANGED = "android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_LOW_POWER_STANDBY_PORTS_CHANGED = "android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED";
    public static final java.lang.String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";
    public static final java.lang.String ACTION_POWER_SAVE_MODE_CHANGED_INTERNAL = "android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL";
    public static final java.lang.String ACTION_POWER_SAVE_TEMP_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED";
    public static final java.lang.String ACTION_POWER_SAVE_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_WHITELIST_CHANGED";
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DEFAULT = 2;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DEFAULT_BUTTON = 8;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DEFAULT_KEYBOARD = 9;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DIM = 3;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DOZE = 4;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_MAXIMUM = 1;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_MINIMUM = 0;
    public static final int BRIGHTNESS_DEFAULT = -1;
    public static final int BRIGHTNESS_INVALID = -1;
    public static final float BRIGHTNESS_INVALID_FLOAT = Float.NaN;
    public static final float BRIGHTNESS_MAX = 1.0f;
    public static final float BRIGHTNESS_MIN = 0.0f;
    public static final int BRIGHTNESS_OFF = 0;
    public static final float BRIGHTNESS_OFF_FLOAT = -1.0f;
    public static final int BRIGHTNESS_ON = 255;
    private static final java.lang.String CACHE_KEY_IS_INTERACTIVE_PROPERTY = "cache_key.is_interactive";
    private static final java.lang.String CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY = "cache_key.is_power_save_mode";
    public static final int DOZE_WAKE_LOCK = 64;
    public static final int DRAW_WAKE_LOCK = 128;
    public static final java.lang.String FEATURE_WAKE_ON_LAN_IN_LOW_POWER_STANDBY = "com.android.lowpowerstandby.WAKE_ON_LAN";

    @java.lang.Deprecated
    public static final int FULL_WAKE_LOCK = 26;
    public static final int GO_TO_SLEEP_FLAG_NO_DOZE = 1;
    public static final int GO_TO_SLEEP_FLAG_SOFT_SLEEP = 2;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = 0;
    public static final int GO_TO_SLEEP_REASON_DEVICE_ADMIN = 1;
    public static final int GO_TO_SLEEP_REASON_DEVICE_FOLD = 13;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUPS_TURNED_OFF = 12;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUP_REMOVED = 11;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_INATTENTIVE = 9;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_MAX = 13;
    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int LOCATION_MODE_ALL_DISABLED_WHEN_SCREEN_OFF = 2;
    public static final int LOCATION_MODE_FOREGROUND_ONLY = 3;
    public static final int LOCATION_MODE_GPS_DISABLED_WHEN_SCREEN_OFF = 1;
    public static final int LOCATION_MODE_NO_CHANGE = 0;
    public static final int LOCATION_MODE_THROTTLE_REQUESTS_WHEN_SCREEN_OFF = 4;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_ONGOING_CALL = 4;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_TEMP_POWER_SAVE_ALLOWLIST = 2;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_VOICE_INTERACTION = 1;
    private static final int MAX_CACHE_ENTRIES = 1;
    public static final int MAX_LOCATION_MODE = 4;
    public static final int MAX_SOUND_TRIGGER_MODE = 2;
    private static final int MINIMUM_HEADROOM_TIME_MILLIS = 500;
    public static final int MIN_LOCATION_MODE = 0;
    public static final int MIN_SOUND_TRIGGER_MODE = 0;
    public static final int ON_AFTER_RELEASE = 536870912;
    public static final int PARTIAL_WAKE_LOCK = 1;

    @android.annotation.SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_DYNAMIC = 1;

    @android.annotation.SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_PERCENTAGE = 0;
    public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final java.lang.String REBOOT_BOOTLOADER = "bootloader";
    public static final java.lang.String REBOOT_DOWNLOAD = "download";
    public static final java.lang.String REBOOT_FASTBOOT = "fastboot";
    public static final java.lang.String REBOOT_QUIESCENT = "quiescent";
    public static final java.lang.String REBOOT_RECOVERY = "recovery";
    public static final java.lang.String REBOOT_RECOVERY_UPDATE = "recovery-update";
    public static final java.lang.String REBOOT_REQUESTED_BY_DEVICE_OWNER = "deviceowner";
    public static final java.lang.String REBOOT_SAFE_MODE = "safemode";

    @android.annotation.SystemApi
    public static final java.lang.String REBOOT_USERSPACE = "userspace";
    public static final int RELEASE_FLAG_TIMEOUT = 65536;
    public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;

    @java.lang.Deprecated
    public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;

    @java.lang.Deprecated
    public static final int SCREEN_DIM_WAKE_LOCK = 6;
    public static final java.lang.String SHUTDOWN_BATTERY_THERMAL_STATE = "thermal,battery";
    public static final java.lang.String SHUTDOWN_LOW_BATTERY = "battery";
    public static final int SHUTDOWN_REASON_BATTERY_THERMAL = 6;
    public static final int SHUTDOWN_REASON_LOW_BATTERY = 5;
    public static final int SHUTDOWN_REASON_REBOOT = 2;
    public static final int SHUTDOWN_REASON_SHUTDOWN = 1;
    public static final int SHUTDOWN_REASON_THERMAL_SHUTDOWN = 4;
    public static final int SHUTDOWN_REASON_UNKNOWN = 0;
    public static final int SHUTDOWN_REASON_USER_REQUESTED = 3;
    public static final java.lang.String SHUTDOWN_THERMAL_STATE = "thermal";
    public static final java.lang.String SHUTDOWN_USER_REQUESTED = "userrequested";

    @android.annotation.SystemApi
    public static final int SOUND_TRIGGER_MODE_ALL_DISABLED = 2;

    @android.annotation.SystemApi
    public static final int SOUND_TRIGGER_MODE_ALL_ENABLED = 0;

    @android.annotation.SystemApi
    public static final int SOUND_TRIGGER_MODE_CRITICAL_ONLY = 1;
    public static final int SYSTEM_WAKELOCK = Integer.MIN_VALUE;
    private static final java.lang.String TAG = "PowerManager";
    public static final int THERMAL_STATUS_CRITICAL = 4;
    public static final int THERMAL_STATUS_EMERGENCY = 5;
    public static final int THERMAL_STATUS_LIGHT = 1;
    public static final int THERMAL_STATUS_MODERATE = 2;
    public static final int THERMAL_STATUS_NONE = 0;
    public static final int THERMAL_STATUS_SEVERE = 3;
    public static final int THERMAL_STATUS_SHUTDOWN = 6;
    public static final int UNIMPORTANT_FOR_LOGGING = 1073741824;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_EVENT_ACCESSIBILITY = 3;
    public static final int USER_ACTIVITY_EVENT_ATTENTION = 4;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_EVENT_BUTTON = 1;
    public static final int USER_ACTIVITY_EVENT_DEVICE_STATE = 6;
    public static final int USER_ACTIVITY_EVENT_FACE_DOWN = 5;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_EVENT_OTHER = 0;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_EVENT_TOUCH = 2;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_FLAG_INDIRECT = 2;
    public static final int USER_ACTIVITY_FLAG_NO_BUTTON_LIGHTS = 4;

    @android.annotation.SystemApi
    public static final int USER_ACTIVITY_FLAG_NO_CHANGE_LIGHTS = 1;
    public static final int WAKE_LOCK_LEVEL_MASK = 65535;
    public static final int WAKE_REASON_APPLICATION = 2;
    public static final int WAKE_REASON_BIOMETRIC = 17;
    public static final int WAKE_REASON_CAMERA_LAUNCH = 5;
    public static final int WAKE_REASON_DISPLAY_GROUP_ADDED = 10;
    public static final int WAKE_REASON_DISPLAY_GROUP_TURNED_ON = 11;
    public static final int WAKE_REASON_DREAM_FINISHED = 13;
    public static final int WAKE_REASON_GESTURE = 4;
    public static final int WAKE_REASON_HDMI = 8;
    public static final int WAKE_REASON_LID = 9;
    public static final int WAKE_REASON_LIFT = 16;
    public static final int WAKE_REASON_PLUGGED_IN = 3;
    public static final int WAKE_REASON_POWER_BUTTON = 1;
    public static final int WAKE_REASON_TAP = 15;
    public static final int WAKE_REASON_TILT = 14;
    public static final int WAKE_REASON_UNFOLD_DEVICE = 12;
    public static final int WAKE_REASON_UNKNOWN = 0;
    public static final int WAKE_REASON_WAKE_KEY = 6;
    public static final int WAKE_REASON_WAKE_MOTION = 7;
    final android.content.Context mContext;
    final android.os.Handler mHandler;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean> mInteractiveCache;
    private android.os.PowerExemptionManager mPowerExemptionManager;
    private final android.app.PropertyInvalidatedCache<java.lang.Void, java.lang.Boolean> mPowerSaveModeCache;
    final android.os.IPowerManager mService;
    final android.os.IThermalService mThermalService;
    private final android.util.ArrayMap<android.os.PowerManager.OnThermalStatusChangedListener, android.os.IThermalStatusListener> mListenerMap = new android.util.ArrayMap<>();
    private final java.lang.Object mThermalHeadroomThresholdsLock = new java.lang.Object();
    private float[] mThermalHeadroomThresholds = null;
    private final java.util.concurrent.atomic.AtomicLong mLastHeadroomUpdate = new java.util.concurrent.atomic.AtomicLong(0);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutoPowerSaveModeTriggers {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BrightnessConstraint {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GoToSleepReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LocationPowerSaveMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LowPowerStandbyAllowedReason {
    }

    public interface OnThermalStatusChangedListener {
        void onThermalStatusChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceType {
        public static final int ANIMATION = 3;
        public static final int AOD = 14;
        public static final int BATTERY_STATS = 9;
        public static final int DATA_SAVER = 10;
        public static final int FORCE_ALL_APPS_STANDBY = 11;
        public static final int FORCE_BACKGROUND_CHECK = 12;
        public static final int FULL_BACKUP = 4;
        public static final int KEYVALUE_BACKUP = 5;
        public static final int LOCATION = 1;
        public static final int NETWORK_FIREWALL = 6;
        public static final int NIGHT_MODE = 16;
        public static final int NULL = 0;
        public static final int OPTIONAL_SENSORS = 13;
        public static final int QUICK_DOZE = 15;
        public static final int SCREEN_BRIGHTNESS = 7;
        public static final int SOUND = 8;
        public static final int VIBRATION = 2;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShutdownReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SoundTriggerPowerSaveMode {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ThermalStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserActivityEvent {
    }

    public interface WakeLockStateListener {
        void onStateChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WakeReason {
    }

    public static java.lang.String userActivityEventToString(int i) {
        switch (i) {
            case 0:
                return "other";
            case 1:
                return "button";
            case 2:
                return "touch";
            case 3:
                return android.content.Context.ACCESSIBILITY_SERVICE;
            case 4:
                return android.content.Context.ATTENTION_SERVICE;
            case 5:
                return "faceDown";
            case 6:
                return "deviceState";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String sleepReasonToString(int i) {
        switch (i) {
            case 0:
                return "application";
            case 1:
                return "device_admin";
            case 2:
                return "timeout";
            case 3:
                return "lid_switch";
            case 4:
                return "power_button";
            case 5:
                return "hdmi";
            case 6:
                return "sleep_button";
            case 7:
                return android.content.Context.ACCESSIBILITY_SERVICE;
            case 8:
                return "force_suspend";
            case 9:
                return "inattentive";
            case 10:
                return REBOOT_QUIESCENT;
            case 11:
                return "display_group_removed";
            case 12:
                return "display_groups_turned_off";
            case 13:
                return "device_folded";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String wakeReasonToString(int i) {
        switch (i) {
            case 0:
                return "WAKE_REASON_UNKNOWN";
            case 1:
                return "WAKE_REASON_POWER_BUTTON";
            case 2:
                return "WAKE_REASON_APPLICATION";
            case 3:
                return "WAKE_REASON_PLUGGED_IN";
            case 4:
                return "WAKE_REASON_GESTURE";
            case 5:
                return "WAKE_REASON_CAMERA_LAUNCH";
            case 6:
                return "WAKE_REASON_WAKE_KEY";
            case 7:
                return "WAKE_REASON_WAKE_MOTION";
            case 8:
                return "WAKE_REASON_HDMI";
            case 9:
                return "WAKE_REASON_LID";
            case 10:
                return "WAKE_REASON_DISPLAY_GROUP_ADDED";
            case 11:
                return "WAKE_REASON_DISPLAY_GROUP_TURNED_ON";
            case 12:
                return "WAKE_REASON_UNFOLD_DEVICE";
            case 13:
                return "WAKE_REASON_DREAM_FINISHED";
            case 14:
                return "WAKE_REASON_TILT";
            case 15:
                return "WAKE_REASON_TAP";
            case 16:
                return "WAKE_REASON_LIFT";
            case 17:
                return "WAKE_REASON_BIOMETRIC";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static class WakeData {
        public final long sleepDurationRealtime;
        public final int wakeReason;
        public final long wakeTime;

        public WakeData(long j, int i, long j2) {
            this.wakeTime = j;
            this.wakeReason = i;
            this.sleepDurationRealtime = j2;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.os.PowerManager.WakeData)) {
                return false;
            }
            android.os.PowerManager.WakeData wakeData = (android.os.PowerManager.WakeData) obj;
            return this.wakeTime == wakeData.wakeTime && this.wakeReason == wakeData.wakeReason && this.sleepDurationRealtime == wakeData.sleepDurationRealtime;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Long.valueOf(this.wakeTime), java.lang.Integer.valueOf(this.wakeReason), java.lang.Long.valueOf(this.sleepDurationRealtime));
        }
    }

    public static class SleepData {
        public final int goToSleepReason;
        public final long goToSleepUptimeMillis;

        public SleepData(long j, int i) {
            this.goToSleepUptimeMillis = j;
            this.goToSleepReason = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.os.PowerManager.SleepData)) {
                return false;
            }
            android.os.PowerManager.SleepData sleepData = (android.os.PowerManager.SleepData) obj;
            return this.goToSleepUptimeMillis == sleepData.goToSleepUptimeMillis && this.goToSleepReason == sleepData.goToSleepReason;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Long.valueOf(this.goToSleepUptimeMillis), java.lang.Integer.valueOf(this.goToSleepReason));
        }
    }

    public static java.lang.String locationPowerSaveModeToString(int i) {
        switch (i) {
            case 0:
                return "NO_CHANGE";
            case 1:
                return "GPS_DISABLED_WHEN_SCREEN_OFF";
            case 2:
                return "ALL_DISABLED_WHEN_SCREEN_OFF";
            case 3:
                return "FOREGROUND_ONLY";
            case 4:
                return "THROTTLE_REQUESTS_WHEN_SCREEN_OFF";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public PowerManager(android.content.Context context, android.os.IPowerManager iPowerManager, android.os.IThermalService iThermalService, android.os.Handler handler) {
        int i = 1;
        this.mPowerSaveModeCache = new android.app.PropertyInvalidatedCache<java.lang.Void, java.lang.Boolean>(i, CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY) { // from class: android.os.PowerManager.1
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Boolean recompute(java.lang.Void r1) {
                try {
                    return java.lang.Boolean.valueOf(android.os.PowerManager.this.mService.isPowerSaveMode());
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mInteractiveCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Boolean>(i, CACHE_KEY_IS_INTERACTIVE_PROPERTY) { // from class: android.os.PowerManager.2
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Boolean recompute(java.lang.Integer num) {
                try {
                    if (num == null) {
                        return java.lang.Boolean.valueOf(android.os.PowerManager.this.mService.isInteractive());
                    }
                    return java.lang.Boolean.valueOf(android.os.PowerManager.this.mService.isDisplayInteractive(num.intValue()));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mContext = context;
        this.mService = iPowerManager;
        this.mThermalService = iThermalService;
        this.mHandler = handler;
    }

    private android.os.PowerExemptionManager getPowerExemptionManager() {
        if (this.mPowerExemptionManager == null) {
            this.mPowerExemptionManager = (android.os.PowerExemptionManager) this.mContext.getSystemService(android.os.PowerExemptionManager.class);
        }
        return this.mPowerExemptionManager;
    }

    public int getMinimumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMinimum);
    }

    public int getMaximumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingMaximum);
    }

    public int getDefaultScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(com.android.internal.R.integer.config_screenBrightnessSettingDefault);
    }

    public float getBrightnessConstraint(int i) {
        try {
            return this.mService.getBrightnessConstraint(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.PowerManager.WakeLock newWakeLock(int i, java.lang.String str) {
        validateWakeLockParameters(i, str);
        return new android.os.PowerManager.WakeLock(i, str, this.mContext.getOpPackageName(), -1);
    }

    public android.os.PowerManager.WakeLock newWakeLock(int i, java.lang.String str, int i2) {
        validateWakeLockParameters(i, str);
        return new android.os.PowerManager.WakeLock(i, str, this.mContext.getOpPackageName(), i2);
    }

    public static void validateWakeLockParameters(int i, java.lang.String str) {
        switch (i & 65535) {
            case 1:
            case 6:
            case 10:
            case 26:
            case 32:
            case 64:
            case 128:
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("The tag must not be null.");
                }
                return;
            default:
                throw new java.lang.IllegalArgumentException("Must specify a valid wake lock level.");
        }
    }

    @java.lang.Deprecated
    public void userActivity(long j, boolean z) {
        userActivity(j, 0, z ? 1 : 0);
    }

    @android.annotation.SystemApi
    public void userActivity(long j, int i, int i2) {
        try {
            this.mService.userActivity(this.mContext.getDisplayId(), j, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void goToSleep(long j) {
        goToSleep(j, 0, 0);
    }

    public void goToSleep(long j, int i, int i2) {
        try {
            this.mService.goToSleep(j, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void goToSleep(int i, long j, int i2, int i3) {
        try {
            this.mService.goToSleepWithDisplayId(i, j, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void wakeUp(long j) {
        wakeUp(j, 0, "wakeUp");
    }

    @java.lang.Deprecated
    public void wakeUp(long j, java.lang.String str) {
        wakeUp(j, 0, str);
    }

    public void wakeUp(long j, int i, java.lang.String str) {
        try {
            this.mService.wakeUp(j, i, str, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wakeUpWithProximityCheck(long j, int i, java.lang.String str) {
        try {
            this.mService.wakeUpWithProximityCheck(j, i, str, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void nap(long j) {
        try {
            this.mService.nap(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void dream(long j) {
        android.service.dreams.Sandman.startDreamByUserRequest(this.mContext);
    }

    public void boostScreenBrightness(long j) {
        try {
            this.mService.boostScreenBrightness(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWakeLockLevelSupported(int i) {
        try {
            return this.mService.isWakeLockLevelSupported(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isScreenOn() {
        return isInteractive();
    }

    public boolean isInteractive() {
        return this.mInteractiveCache.query(null).booleanValue();
    }

    public boolean isInteractive(int i) {
        return this.mInteractiveCache.query(java.lang.Integer.valueOf(i)).booleanValue();
    }

    public static boolean isRebootingUserspaceSupportedImpl() {
        return false;
    }

    public boolean isRebootingUserspaceSupported() {
        return isRebootingUserspaceSupportedImpl();
    }

    public void reboot(java.lang.String str) {
        if (REBOOT_USERSPACE.equals(str) && !isRebootingUserspaceSupported()) {
            throw new java.lang.UnsupportedOperationException("Attempted userspace reboot on a device that doesn't support it");
        }
        try {
            this.mService.reboot(false, str, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rebootCustom(java.lang.String str) {
        try {
            this.mService.rebootCustom(false, str, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rebootSafeMode() {
        try {
            this.mService.rebootSafeMode(false, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areAutoPowerSaveModesEnabled() {
        try {
            return this.mService.areAutoPowerSaveModesEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPowerSaveMode() {
        return this.mPowerSaveModeCache.query(null).booleanValue();
    }

    @android.annotation.SystemApi
    public boolean setPowerSaveModeEnabled(boolean z) {
        try {
            return this.mService.setPowerSaveModeEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBatterySaverSupported() {
        try {
            return this.mService.isBatterySaverSupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.BatterySaverPolicyConfig getFullPowerSavePolicy() {
        try {
            return this.mService.getFullPowerSavePolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setFullPowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
        try {
            return this.mService.setFullPowerSavePolicy(batterySaverPolicyConfig);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setDynamicPowerSaveHint(boolean z, int i) {
        try {
            return this.mService.setDynamicPowerSaveHint(z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setAdaptivePowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
        try {
            return this.mService.setAdaptivePowerSavePolicy(batterySaverPolicyConfig);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setAdaptivePowerSaveEnabled(boolean z) {
        try {
            return this.mService.setAdaptivePowerSaveEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getPowerSaveModeTrigger() {
        try {
            return this.mService.getPowerSaveModeTrigger();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setBatteryDischargePrediction(java.time.Duration duration, boolean z) {
        if (duration == null) {
            throw new java.lang.IllegalArgumentException("time remaining must not be null");
        }
        try {
            this.mService.setBatteryDischargePrediction(new android.os.ParcelDuration(duration), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.time.Duration getBatteryDischargePrediction() {
        try {
            android.os.ParcelDuration batteryDischargePrediction = this.mService.getBatteryDischargePrediction();
            if (batteryDischargePrediction == null) {
                return null;
            }
            return batteryDischargePrediction.getDuration();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBatteryDischargePredictionPersonalized() {
        try {
            return this.mService.isBatteryDischargePredictionPersonalized();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.PowerSaveState getPowerSaveState(int i) {
        try {
            return this.mService.getPowerSaveState(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLocationPowerSaveMode() {
        android.os.PowerSaveState powerSaveState = getPowerSaveState(1);
        if (!powerSaveState.batterySaverEnabled) {
            return 0;
        }
        return powerSaveState.locationMode;
    }

    public int getSoundTriggerPowerSaveMode() {
        android.os.PowerSaveState powerSaveState = getPowerSaveState(8);
        if (!powerSaveState.batterySaverEnabled) {
            return 0;
        }
        return powerSaveState.soundTriggerMode;
    }

    public boolean isDeviceIdleMode() {
        try {
            return this.mService.isDeviceIdleMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceLightIdleMode() {
        try {
            return this.mService.isLightDeviceIdleMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isLightDeviceIdleMode() {
        return isDeviceLightIdleMode();
    }

    @android.annotation.SystemApi
    public boolean isLowPowerStandbySupported() {
        try {
            return this.mService.isLowPowerStandbySupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLowPowerStandbyEnabled() {
        try {
            return this.mService.isLowPowerStandbyEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setLowPowerStandbyEnabled(boolean z) {
        try {
            this.mService.setLowPowerStandbyEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setLowPowerStandbyActiveDuringMaintenance(boolean z) {
        try {
            this.mService.setLowPowerStandbyActiveDuringMaintenance(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceLowPowerStandbyActive(boolean z) {
        try {
            this.mService.forceLowPowerStandbyActive(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setLowPowerStandbyPolicy(android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        try {
            this.mService.setLowPowerStandbyPolicy(android.os.PowerManager.LowPowerStandbyPolicy.toParcelable(lowPowerStandbyPolicy));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.PowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() {
        try {
            return android.os.PowerManager.LowPowerStandbyPolicy.fromParcelable(this.mService.getLowPowerStandbyPolicy());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isExemptFromLowPowerStandby() {
        try {
            return this.mService.isExemptFromLowPowerStandby();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAllowedInLowPowerStandby(int i) {
        try {
            return this.mService.isReasonAllowedInLowPowerStandby(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAllowedInLowPowerStandby(java.lang.String str) {
        try {
            return this.mService.isFeatureAllowedInLowPowerStandby(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.PowerManager.LowPowerStandbyPortsLock newLowPowerStandbyPortsLock(java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
        return new android.os.PowerManager.LowPowerStandbyPortsLock(list);
    }

    @android.annotation.SystemApi
    public java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() {
        try {
            return android.os.PowerManager.LowPowerStandbyPortDescription.fromParcelable(this.mService.getActiveLowPowerStandbyPorts());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isIgnoringBatteryOptimizations(java.lang.String str) {
        return getPowerExemptionManager().isAllowListed(str, true);
    }

    public void shutdown(boolean z, java.lang.String str, boolean z2) {
        try {
            this.mService.shutdown(z, str, z2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSustainedPerformanceModeSupported() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_sustainedPerformanceModeSupported);
    }

    public int getCurrentThermalStatus() {
        try {
            return this.mThermalService.getCurrentThermalStatus();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addThermalStatusListener(android.os.PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener) {
        java.util.Objects.requireNonNull(onThermalStatusChangedListener, "listener cannot be null");
        addThermalStatusListener(this.mContext.getMainExecutor(), onThermalStatusChangedListener);
    }

    public void addThermalStatusListener(java.util.concurrent.Executor executor, android.os.PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener) {
        java.util.Objects.requireNonNull(onThermalStatusChangedListener, "listener cannot be null");
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        com.android.internal.util.Preconditions.checkArgument(!this.mListenerMap.containsKey(onThermalStatusChangedListener), "Listener already registered: %s", onThermalStatusChangedListener);
        android.os.PowerManager.AnonymousClass3 anonymousClass3 = new android.os.PowerManager.AnonymousClass3(executor, onThermalStatusChangedListener);
        try {
            if (this.mThermalService.registerThermalStatusListener(anonymousClass3)) {
                this.mListenerMap.put(onThermalStatusChangedListener, anonymousClass3);
                return;
            }
            throw new java.lang.RuntimeException("Listener failed to set");
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.os.PowerManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.os.IThermalStatusListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.os.PowerManager.OnThermalStatusChangedListener val$listener;

        AnonymousClass3(java.util.concurrent.Executor executor, android.os.PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener) {
            this.val$executor = executor;
            this.val$listener = onThermalStatusChangedListener;
        }

        @Override // android.os.IThermalStatusListener
        public void onStatusChange(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.os.PowerManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.PowerManager.OnThermalStatusChangedListener.this.onThermalStatusChanged(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeThermalStatusListener(android.os.PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener) {
        java.util.Objects.requireNonNull(onThermalStatusChangedListener, "listener cannot be null");
        android.os.IThermalStatusListener iThermalStatusListener = this.mListenerMap.get(onThermalStatusChangedListener);
        com.android.internal.util.Preconditions.checkArgument(iThermalStatusListener != null, "Listener was not added");
        try {
            if (this.mThermalService.unregisterThermalStatusListener(iThermalStatusListener)) {
                this.mListenerMap.remove(onThermalStatusChangedListener);
                return;
            }
            throw new java.lang.RuntimeException("Listener failed to remove");
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getThermalHeadroom(int i) {
        if (android.os.SystemClock.elapsedRealtime() - this.mLastHeadroomUpdate.get() < 500) {
            return Float.NaN;
        }
        try {
            float thermalHeadroom = this.mThermalService.getThermalHeadroom(i);
            this.mLastHeadroomUpdate.set(android.os.SystemClock.elapsedRealtime());
            return thermalHeadroom;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<java.lang.Integer, java.lang.Float> getThermalHeadroomThresholds() {
        android.util.ArrayMap arrayMap;
        try {
            synchronized (this.mThermalHeadroomThresholdsLock) {
                if (this.mThermalHeadroomThresholds == null) {
                    this.mThermalHeadroomThresholds = this.mThermalService.getThermalHeadroomThresholds();
                }
                arrayMap = new android.util.ArrayMap(6);
                for (int i = 1; i <= 6; i++) {
                    if (!java.lang.Float.isNaN(this.mThermalHeadroomThresholds[i])) {
                        arrayMap.put(java.lang.Integer.valueOf(i), java.lang.Float.valueOf(this.mThermalHeadroomThresholds[i]));
                    }
                }
            }
            return arrayMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeyboardVisibility(boolean z) {
        try {
            if (this.mService != null) {
                this.mService.setKeyboardVisibility(z);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void setDozeAfterScreenOff(boolean z) {
        try {
            this.mService.setDozeAfterScreenOff(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isAmbientDisplayAvailable() {
        try {
            return this.mService.isAmbientDisplayAvailable();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void suppressAmbientDisplay(java.lang.String str, boolean z) {
        try {
            this.mService.suppressAmbientDisplay(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isAmbientDisplaySuppressedForToken(java.lang.String str) {
        try {
            return this.mService.isAmbientDisplaySuppressedForToken(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isAmbientDisplaySuppressed() {
        try {
            return this.mService.isAmbientDisplaySuppressed();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAmbientDisplaySuppressedForTokenByApp(java.lang.String str, int i) {
        try {
            return this.mService.isAmbientDisplaySuppressedForTokenByApp(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastShutdownReason() {
        try {
            return this.mService.getLastShutdownReason();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastSleepReason() {
        try {
            return this.mService.getLastSleepReason();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean forceSuspend() {
        try {
            return this.mService.forceSuspend();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String lowPowerStandbyAllowedReasonsToString(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("ALLOWED_REASON_VOICE_INTERACTION");
            i &= -2;
        }
        if ((i & 2) != 0) {
            arrayList.add("ALLOWED_REASON_TEMP_POWER_SAVE_ALLOWLIST");
            i &= -3;
        }
        if ((i & 4) != 0) {
            arrayList.add("ALLOWED_REASON_ONGOING_CALL");
            i &= -5;
        }
        if (i != 0) {
            arrayList.add(java.lang.String.valueOf(i));
        }
        return java.lang.String.join(",", arrayList);
    }

    @android.annotation.SystemApi
    public static final class LowPowerStandbyPolicy {
        private final java.util.Set<java.lang.String> mAllowedFeatures;
        private final int mAllowedReasons;
        private final java.util.Set<java.lang.String> mExemptPackages;
        private final java.lang.String mIdentifier;

        public LowPowerStandbyPolicy(java.lang.String str, java.util.Set<java.lang.String> set, int i, java.util.Set<java.lang.String> set2) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(set);
            java.util.Objects.requireNonNull(set2);
            this.mIdentifier = str;
            this.mExemptPackages = java.util.Collections.unmodifiableSet(set);
            this.mAllowedReasons = i;
            this.mAllowedFeatures = java.util.Collections.unmodifiableSet(set2);
        }

        public java.lang.String getIdentifier() {
            return this.mIdentifier;
        }

        public java.util.Set<java.lang.String> getExemptPackages() {
            return this.mExemptPackages;
        }

        public int getAllowedReasons() {
            return this.mAllowedReasons;
        }

        public java.util.Set<java.lang.String> getAllowedFeatures() {
            return this.mAllowedFeatures;
        }

        public java.lang.String toString() {
            return "Policy{mIdentifier='" + this.mIdentifier + android.text.format.DateFormat.QUOTE + ", mExemptPackages=" + java.lang.String.join(",", this.mExemptPackages) + ", mAllowedReasons=" + android.os.PowerManager.lowPowerStandbyAllowedReasonsToString(this.mAllowedReasons) + ", mAllowedFeatures=" + java.lang.String.join(",", this.mAllowedFeatures) + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.PowerManager.LowPowerStandbyPolicy)) {
                return false;
            }
            android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = (android.os.PowerManager.LowPowerStandbyPolicy) obj;
            return this.mAllowedReasons == lowPowerStandbyPolicy.mAllowedReasons && java.util.Objects.equals(this.mIdentifier, lowPowerStandbyPolicy.mIdentifier) && java.util.Objects.equals(this.mExemptPackages, lowPowerStandbyPolicy.mExemptPackages) && java.util.Objects.equals(this.mAllowedFeatures, lowPowerStandbyPolicy.mAllowedFeatures);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mIdentifier, this.mExemptPackages, java.lang.Integer.valueOf(this.mAllowedReasons), this.mAllowedFeatures);
        }

        public static android.os.IPowerManager.LowPowerStandbyPolicy toParcelable(android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (lowPowerStandbyPolicy == null) {
                return null;
            }
            android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2 = new android.os.IPowerManager.LowPowerStandbyPolicy();
            lowPowerStandbyPolicy2.identifier = lowPowerStandbyPolicy.mIdentifier;
            lowPowerStandbyPolicy2.exemptPackages = new java.util.ArrayList(lowPowerStandbyPolicy.mExemptPackages);
            lowPowerStandbyPolicy2.allowedReasons = lowPowerStandbyPolicy.mAllowedReasons;
            lowPowerStandbyPolicy2.allowedFeatures = new java.util.ArrayList(lowPowerStandbyPolicy.mAllowedFeatures);
            return lowPowerStandbyPolicy2;
        }

        public static android.os.PowerManager.LowPowerStandbyPolicy fromParcelable(android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (lowPowerStandbyPolicy == null) {
                return null;
            }
            return new android.os.PowerManager.LowPowerStandbyPolicy(lowPowerStandbyPolicy.identifier, new android.util.ArraySet(lowPowerStandbyPolicy.exemptPackages), lowPowerStandbyPolicy.allowedReasons, new android.util.ArraySet(lowPowerStandbyPolicy.allowedFeatures));
        }
    }

    @android.annotation.SystemApi
    public static final class LowPowerStandbyPortDescription {
        public static final int MATCH_PORT_LOCAL = 1;
        public static final int MATCH_PORT_REMOTE = 2;
        public static final int PROTOCOL_TCP = 6;
        public static final int PROTOCOL_UDP = 17;
        private final java.net.InetAddress mLocalAddress;
        private final int mPortMatcher;
        private final int mPortNumber;
        private final int mProtocol;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PortMatcher {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Protocol {
        }

        public LowPowerStandbyPortDescription(int i, int i2, int i3) {
            this.mProtocol = i;
            this.mPortMatcher = i2;
            this.mPortNumber = i3;
            this.mLocalAddress = null;
        }

        public LowPowerStandbyPortDescription(int i, int i2, int i3, java.net.InetAddress inetAddress) {
            this.mProtocol = i;
            this.mPortMatcher = i2;
            this.mPortNumber = i3;
            this.mLocalAddress = inetAddress;
        }

        private java.lang.String protocolToString(int i) {
            switch (i) {
                case 6:
                    return android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_TCP;
                case 17:
                    return android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_UDP;
                default:
                    return java.lang.String.valueOf(i);
            }
        }

        private java.lang.String portMatcherToString(int i) {
            switch (i) {
                case 1:
                    return "MATCH_PORT_LOCAL";
                case 2:
                    return "MATCH_PORT_REMOTE";
                default:
                    return java.lang.String.valueOf(i);
            }
        }

        public int getProtocol() {
            return this.mProtocol;
        }

        public int getPortMatcher() {
            return this.mPortMatcher;
        }

        public int getPortNumber() {
            return this.mPortNumber;
        }

        public java.net.InetAddress getLocalAddress() {
            return this.mLocalAddress;
        }

        public java.lang.String toString() {
            return "PortDescription{mProtocol=" + protocolToString(this.mProtocol) + ", mPortMatcher=" + portMatcherToString(this.mPortMatcher) + ", mPortNumber=" + this.mPortNumber + ", mLocalAddress=" + this.mLocalAddress + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.PowerManager.LowPowerStandbyPortDescription)) {
                return false;
            }
            android.os.PowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription = (android.os.PowerManager.LowPowerStandbyPortDescription) obj;
            return this.mProtocol == lowPowerStandbyPortDescription.mProtocol && this.mPortMatcher == lowPowerStandbyPortDescription.mPortMatcher && this.mPortNumber == lowPowerStandbyPortDescription.mPortNumber && java.util.Objects.equals(this.mLocalAddress, lowPowerStandbyPortDescription.mLocalAddress);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mProtocol), java.lang.Integer.valueOf(this.mPortMatcher), java.lang.Integer.valueOf(this.mPortNumber), this.mLocalAddress);
        }

        public static android.os.IPowerManager.LowPowerStandbyPortDescription toParcelable(android.os.PowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription) {
            if (lowPowerStandbyPortDescription == null) {
                return null;
            }
            android.os.IPowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription2 = new android.os.IPowerManager.LowPowerStandbyPortDescription();
            lowPowerStandbyPortDescription2.protocol = lowPowerStandbyPortDescription.mProtocol;
            lowPowerStandbyPortDescription2.portMatcher = lowPowerStandbyPortDescription.mPortMatcher;
            lowPowerStandbyPortDescription2.portNumber = lowPowerStandbyPortDescription.mPortNumber;
            if (lowPowerStandbyPortDescription.mLocalAddress != null) {
                lowPowerStandbyPortDescription2.localAddress = lowPowerStandbyPortDescription.mLocalAddress.getAddress();
            }
            return lowPowerStandbyPortDescription2;
        }

        public static java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> toParcelable(java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
            if (list == null) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.os.PowerManager.LowPowerStandbyPortDescription> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toParcelable(it.next()));
            }
            return arrayList;
        }

        public static android.os.PowerManager.LowPowerStandbyPortDescription fromParcelable(android.os.IPowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription) {
            java.net.InetAddress inetAddress = null;
            if (lowPowerStandbyPortDescription == null) {
                return null;
            }
            if (lowPowerStandbyPortDescription.localAddress != null) {
                try {
                    inetAddress = java.net.InetAddress.getByAddress(lowPowerStandbyPortDescription.localAddress);
                } catch (java.net.UnknownHostException e) {
                    android.util.Log.w(android.os.PowerManager.TAG, "Address has invalid length", e);
                }
            }
            return new android.os.PowerManager.LowPowerStandbyPortDescription(lowPowerStandbyPortDescription.protocol, lowPowerStandbyPortDescription.portMatcher, lowPowerStandbyPortDescription.portNumber, inetAddress);
        }

        public static java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> fromParcelable(java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> list) {
            if (list == null) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.os.IPowerManager.LowPowerStandbyPortDescription> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromParcelable(it.next()));
            }
            return arrayList;
        }
    }

    @android.annotation.SystemApi
    public final class LowPowerStandbyPortsLock {
        private boolean mHeld;
        private final java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> mPorts;
        private final android.os.IBinder mToken = new android.os.Binder();

        LowPowerStandbyPortsLock(java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
            this.mPorts = list;
        }

        public void acquire() {
            synchronized (this.mToken) {
                try {
                    try {
                        android.os.PowerManager.this.mService.acquireLowPowerStandbyPorts(this.mToken, android.os.PowerManager.LowPowerStandbyPortDescription.toParcelable(this.mPorts));
                        this.mHeld = true;
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void release() {
            synchronized (this.mToken) {
                try {
                    try {
                        android.os.PowerManager.this.mService.releaseLowPowerStandbyPorts(this.mToken);
                        this.mHeld = false;
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        protected void finalize() {
            synchronized (this.mToken) {
                if (this.mHeld) {
                    android.util.Log.wtf(android.os.PowerManager.TAG, "LowPowerStandbyPorts finalized while still held");
                    release();
                }
            }
        }
    }

    public final class WakeLock {
        private android.os.IWakeLockCallback mCallback;
        private final int mDisplayId;
        private int mExternalCount;
        private int mFlags;
        private boolean mHeld;
        private java.lang.String mHistoryTag;
        private int mInternalCount;
        private android.os.PowerManager.WakeLockStateListener mListener;
        private final java.lang.String mPackageName;
        private java.lang.String mTag;
        private int mTagHash;
        private android.os.WorkSource mWorkSource;
        private boolean mRefCounted = true;
        private final java.lang.Runnable mReleaser = new java.lang.Runnable() { // from class: android.os.PowerManager$WakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.os.PowerManager.WakeLock.this.lambda$new$0();
            }
        };
        private final android.os.IBinder mToken = new android.os.Binder();

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            release(65536);
        }

        WakeLock(int i, java.lang.String str, java.lang.String str2, int i2) {
            this.mFlags = i;
            this.mTag = str;
            this.mTagHash = this.mTag.hashCode();
            this.mPackageName = str2;
            this.mDisplayId = i2;
        }

        protected void finalize() throws java.lang.Throwable {
            synchronized (this.mToken) {
                if (this.mHeld) {
                    android.util.Log.wtf(android.os.PowerManager.TAG, "WakeLock finalized while still held: " + this.mTag);
                    android.os.Trace.asyncTraceForTrackEnd(131072L, "WakeLocks", this.mTagHash);
                    try {
                        android.os.PowerManager.this.mService.releaseWakeLock(this.mToken, 0);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }

        public void setReferenceCounted(boolean z) {
            synchronized (this.mToken) {
                this.mRefCounted = z;
            }
        }

        public void acquire() {
            synchronized (this.mToken) {
                acquireLocked();
            }
        }

        public void acquire(long j) {
            synchronized (this.mToken) {
                acquireLocked();
                android.os.PowerManager.this.mHandler.postDelayed(this.mReleaser, j);
            }
        }

        private void acquireLocked() {
            this.mInternalCount++;
            this.mExternalCount++;
            if (!this.mRefCounted || this.mInternalCount == 1) {
                android.os.PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
                android.os.Trace.asyncTraceForTrackBegin(131072L, "WakeLocks", this.mTag, this.mTagHash);
                try {
                    android.os.PowerManager.this.mService.acquireWakeLock(this.mToken, this.mFlags, this.mTag, this.mPackageName, this.mWorkSource, this.mHistoryTag, this.mDisplayId, this.mCallback);
                    this.mHeld = true;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void release() {
            release(0);
        }

        public void release(int i) {
            synchronized (this.mToken) {
                if (this.mInternalCount > 0) {
                    this.mInternalCount--;
                }
                if ((65536 & i) == 0) {
                    this.mExternalCount--;
                }
                if (!this.mRefCounted || this.mInternalCount == 0) {
                    android.os.PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
                    if (this.mHeld) {
                        android.os.Trace.asyncTraceForTrackEnd(131072L, "WakeLocks", this.mTagHash);
                        try {
                            android.os.PowerManager.this.mService.releaseWakeLock(this.mToken, i);
                            this.mHeld = false;
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
                if (this.mRefCounted && this.mExternalCount < 0) {
                    throw new java.lang.RuntimeException("WakeLock under-locked " + this.mTag);
                }
            }
        }

        public boolean isHeld() {
            boolean z;
            synchronized (this.mToken) {
                z = this.mHeld;
            }
            return z;
        }

        public void setWorkSource(android.os.WorkSource workSource) {
            synchronized (this.mToken) {
                if (workSource != null) {
                    try {
                        if (workSource.isEmpty()) {
                            workSource = null;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                boolean z = true;
                if (workSource == null) {
                    if (this.mWorkSource == null) {
                        z = false;
                    }
                    this.mWorkSource = null;
                } else if (this.mWorkSource == null) {
                    this.mWorkSource = new android.os.WorkSource(workSource);
                } else {
                    z = true ^ this.mWorkSource.equals(workSource);
                    if (z) {
                        this.mWorkSource.set(workSource);
                    }
                }
                if (z && this.mHeld) {
                    try {
                        android.os.PowerManager.this.mService.updateWakeLockWorkSource(this.mToken, this.mWorkSource, this.mHistoryTag);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }

        public void setTag(java.lang.String str) {
            this.mTag = str;
            this.mTagHash = this.mTag.hashCode();
        }

        public java.lang.String getTag() {
            return this.mTag;
        }

        public void setHistoryTag(java.lang.String str) {
            this.mHistoryTag = str;
        }

        public void setUnimportantForLogging(boolean z) {
            if (!z) {
                this.mFlags &= -1073741825;
            } else {
                this.mFlags |= 1073741824;
            }
        }

        public java.lang.String toString() {
            java.lang.String str;
            synchronized (this.mToken) {
                str = "WakeLock{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " held=" + this.mHeld + ", refCount=" + this.mInternalCount + "}";
            }
            return str;
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            synchronized (this.mToken) {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1138166333441L, this.mTag);
                protoOutputStream.write(1138166333442L, this.mPackageName);
                protoOutputStream.write(1133871366147L, this.mHeld);
                protoOutputStream.write(1120986464260L, this.mInternalCount);
                if (this.mWorkSource != null) {
                    this.mWorkSource.dumpDebug(protoOutputStream, 1146756268037L);
                }
                protoOutputStream.end(start);
            }
        }

        public java.lang.Runnable wrap(final java.lang.Runnable runnable) {
            acquire();
            return new java.lang.Runnable() { // from class: android.os.PowerManager$WakeLock$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.PowerManager.WakeLock.this.lambda$wrap$1(runnable);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$1(java.lang.Runnable runnable) {
            try {
                runnable.run();
            } finally {
                release();
            }
        }

        public void setStateListener(java.util.concurrent.Executor executor, android.os.PowerManager.WakeLockStateListener wakeLockStateListener) {
            com.android.internal.util.Preconditions.checkNotNull(executor, "executor cannot be null");
            synchronized (this.mToken) {
                if (wakeLockStateListener != this.mListener) {
                    this.mListener = wakeLockStateListener;
                    if (wakeLockStateListener != null) {
                        this.mCallback = new android.os.PowerManager.WakeLock.AnonymousClass1(executor, wakeLockStateListener);
                    } else {
                        this.mCallback = null;
                    }
                    if (this.mHeld) {
                        try {
                            android.os.PowerManager.this.mService.updateWakeLockCallback(this.mToken, this.mCallback);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }

        /* renamed from: android.os.PowerManager$WakeLock$1, reason: invalid class name */
        class AnonymousClass1 extends android.os.IWakeLockCallback.Stub {
            final /* synthetic */ java.util.concurrent.Executor val$executor;
            final /* synthetic */ android.os.PowerManager.WakeLockStateListener val$listener;

            AnonymousClass1(java.util.concurrent.Executor executor, android.os.PowerManager.WakeLockStateListener wakeLockStateListener) {
                this.val$executor = executor;
                this.val$listener = wakeLockStateListener;
            }

            @Override // android.os.IWakeLockCallback
            public void onStateChanged(final boolean z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.PowerManager.WakeLockStateListener wakeLockStateListener = this.val$listener;
                    executor.execute(new java.lang.Runnable() { // from class: android.os.PowerManager$WakeLock$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.PowerManager.WakeLockStateListener.this.onStateChanged(z);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public static void invalidatePowerSaveModeCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY);
    }

    public static void invalidateIsInteractiveCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_IS_INTERACTIVE_PROPERTY);
    }
}
