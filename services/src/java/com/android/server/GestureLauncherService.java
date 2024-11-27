package com.android.server;

/* loaded from: classes.dex */
public class GestureLauncherService extends com.android.server.SystemService {

    @com.android.internal.annotations.VisibleForTesting
    static final long CAMERA_POWER_DOUBLE_TAP_MAX_TIME_MS = 300;
    private static final int CAMERA_POWER_TAP_COUNT_THRESHOLD = 2;
    private static final boolean DBG = false;
    private static final boolean DBG_CAMERA_LIFT = false;
    private static final int EMERGENCY_GESTURE_POWER_BUTTON_COOLDOWN_PERIOD_MS_DEFAULT = 3000;

    @com.android.internal.annotations.VisibleForTesting
    static final int EMERGENCY_GESTURE_POWER_BUTTON_COOLDOWN_PERIOD_MS_MAX = 5000;
    private static final int EMERGENCY_GESTURE_POWER_TAP_COUNT_THRESHOLD = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final int EMERGENCY_GESTURE_TAP_DETECTION_MIN_TIME_MS = 200;

    @com.android.internal.annotations.VisibleForTesting
    static final long POWER_SHORT_TAP_SEQUENCE_MAX_INTERVAL_MS = 500;
    private static final java.lang.String TAG = "GestureLauncherService";
    private boolean mCameraDoubleTapPowerEnabled;
    private long mCameraGestureLastEventTime;
    private long mCameraGestureOnTimeMs;
    private long mCameraGestureSensor1LastOnTimeMs;
    private long mCameraGestureSensor2LastOnTimeMs;
    private int mCameraLaunchLastEventExtra;
    private boolean mCameraLaunchRegistered;
    private android.hardware.Sensor mCameraLaunchSensor;
    private boolean mCameraLiftRegistered;
    private final com.android.server.GestureLauncherService.CameraLiftTriggerEventListener mCameraLiftTriggerListener;
    private android.hardware.Sensor mCameraLiftTriggerSensor;
    private android.content.Context mContext;
    private boolean mEmergencyGestureEnabled;
    private int mEmergencyGesturePowerButtonCooldownPeriodMs;
    private long mFirstPowerDown;
    private final com.android.server.GestureLauncherService.GestureEventListener mGestureListener;
    private boolean mHasFeatureWatch;
    private long mLastEmergencyGestureTriggered;
    private long mLastPowerDown;
    private final com.android.internal.logging.MetricsLogger mMetricsLogger;
    private int mPowerButtonConsecutiveTaps;
    private int mPowerButtonSlowConsecutiveTaps;
    private android.os.PowerManager mPowerManager;
    private final android.database.ContentObserver mSettingObserver;
    private final com.android.internal.logging.UiEventLogger mUiEventLogger;
    private int mUserId;
    private final android.content.BroadcastReceiver mUserReceiver;
    private android.os.PowerManager.WakeLock mWakeLock;
    private com.android.server.wm.WindowManagerInternal mWindowManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    public enum GestureLauncherEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        GESTURE_CAMERA_LIFT(com.android.internal.util.FrameworkStatsLog.EXPRESS_UID_HISTOGRAM_SAMPLE_REPORTED),
        GESTURE_CAMERA_WIGGLE(com.android.internal.util.FrameworkStatsLog.AUTOFILL_FIELD_CLASSIFICATION_EVENT_REPORTED),
        GESTURE_CAMERA_DOUBLE_TAP_POWER(660),
        GESTURE_EMERGENCY_TAP_POWER(661);

        private final int mId;

        GestureLauncherEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public GestureLauncherService(android.content.Context context) {
        this(context, new com.android.internal.logging.MetricsLogger(), new com.android.internal.logging.UiEventLoggerImpl());
    }

    @com.android.internal.annotations.VisibleForTesting
    GestureLauncherService(android.content.Context context, com.android.internal.logging.MetricsLogger metricsLogger, com.android.internal.logging.UiEventLogger uiEventLogger) {
        super(context);
        this.mGestureListener = new com.android.server.GestureLauncherService.GestureEventListener();
        this.mCameraLiftTriggerListener = new com.android.server.GestureLauncherService.CameraLiftTriggerEventListener();
        this.mCameraGestureOnTimeMs = 0L;
        this.mCameraGestureLastEventTime = 0L;
        this.mCameraGestureSensor1LastOnTimeMs = 0L;
        this.mCameraGestureSensor2LastOnTimeMs = 0L;
        this.mCameraLaunchLastEventExtra = 0;
        this.mUserReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.GestureLauncherService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    com.android.server.GestureLauncherService.this.mUserId = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    com.android.server.GestureLauncherService.this.mContext.getContentResolver().unregisterContentObserver(com.android.server.GestureLauncherService.this.mSettingObserver);
                    com.android.server.GestureLauncherService.this.registerContentObservers();
                    com.android.server.GestureLauncherService.this.updateCameraRegistered();
                    com.android.server.GestureLauncherService.this.updateCameraDoubleTapPowerEnabled();
                    com.android.server.GestureLauncherService.this.updateEmergencyGestureEnabled();
                    com.android.server.GestureLauncherService.this.updateEmergencyGesturePowerButtonCooldownPeriodMs();
                }
            }
        };
        this.mSettingObserver = new android.database.ContentObserver(new android.os.Handler()) { // from class: com.android.server.GestureLauncherService.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri, int i) {
                if (i == com.android.server.GestureLauncherService.this.mUserId) {
                    com.android.server.GestureLauncherService.this.updateCameraRegistered();
                    com.android.server.GestureLauncherService.this.updateCameraDoubleTapPowerEnabled();
                    com.android.server.GestureLauncherService.this.updateEmergencyGestureEnabled();
                    com.android.server.GestureLauncherService.this.updateEmergencyGesturePowerButtonCooldownPeriodMs();
                }
            }
        };
        this.mContext = context;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.LocalServices.addService(com.android.server.GestureLauncherService.class, this);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i != 600 || !isGestureLauncherEnabled(this.mContext.getResources())) {
            return;
        }
        this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
        this.mWakeLock = this.mPowerManager.newWakeLock(1, TAG);
        updateCameraRegistered();
        updateCameraDoubleTapPowerEnabled();
        updateEmergencyGestureEnabled();
        updateEmergencyGesturePowerButtonCooldownPeriodMs();
        this.mUserId = android.app.ActivityManager.getCurrentUser();
        this.mContext.registerReceiver(this.mUserReceiver, new android.content.IntentFilter("android.intent.action.USER_SWITCHED"));
        registerContentObservers();
        this.mHasFeatureWatch = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerContentObservers() {
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("camera_gesture_disabled"), false, this.mSettingObserver, this.mUserId);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("camera_double_tap_power_gesture_disabled"), false, this.mSettingObserver, this.mUserId);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("camera_lift_trigger_enabled"), false, this.mSettingObserver, this.mUserId);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("emergency_gesture_enabled"), false, this.mSettingObserver, this.mUserId);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("emergency_gesture_power_button_cooldown_period_ms"), false, this.mSettingObserver, this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCameraRegistered() {
        android.content.res.Resources resources = this.mContext.getResources();
        if (isCameraLaunchSettingEnabled(this.mContext, this.mUserId)) {
            registerCameraLaunchGesture(resources);
        } else {
            unregisterCameraLaunchGesture();
        }
        if (isCameraLiftTriggerSettingEnabled(this.mContext, this.mUserId)) {
            registerCameraLiftTrigger(resources);
        } else {
            unregisterCameraLiftTrigger();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateCameraDoubleTapPowerEnabled() {
        boolean isCameraDoubleTapPowerSettingEnabled = isCameraDoubleTapPowerSettingEnabled(this.mContext, this.mUserId);
        synchronized (this) {
            this.mCameraDoubleTapPowerEnabled = isCameraDoubleTapPowerSettingEnabled;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateEmergencyGestureEnabled() {
        boolean isEmergencyGestureSettingEnabled = isEmergencyGestureSettingEnabled(this.mContext, this.mUserId);
        synchronized (this) {
            this.mEmergencyGestureEnabled = isEmergencyGestureSettingEnabled;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateEmergencyGesturePowerButtonCooldownPeriodMs() {
        int emergencyGesturePowerButtonCooldownPeriodMs = getEmergencyGesturePowerButtonCooldownPeriodMs(this.mContext, this.mUserId);
        synchronized (this) {
            this.mEmergencyGesturePowerButtonCooldownPeriodMs = emergencyGesturePowerButtonCooldownPeriodMs;
        }
    }

    private void unregisterCameraLaunchGesture() {
        if (this.mCameraLaunchRegistered) {
            this.mCameraLaunchRegistered = false;
            this.mCameraGestureOnTimeMs = 0L;
            this.mCameraGestureLastEventTime = 0L;
            this.mCameraGestureSensor1LastOnTimeMs = 0L;
            this.mCameraGestureSensor2LastOnTimeMs = 0L;
            this.mCameraLaunchLastEventExtra = 0;
            ((android.hardware.SensorManager) this.mContext.getSystemService("sensor")).unregisterListener(this.mGestureListener);
        }
    }

    private void registerCameraLaunchGesture(android.content.res.Resources resources) {
        if (this.mCameraLaunchRegistered) {
            return;
        }
        this.mCameraGestureOnTimeMs = android.os.SystemClock.elapsedRealtime();
        this.mCameraGestureLastEventTime = this.mCameraGestureOnTimeMs;
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService("sensor");
        int integer = resources.getInteger(android.R.integer.config_burnInProtectionMinVerticalOffset);
        if (integer != -1) {
            this.mCameraLaunchRegistered = false;
            java.lang.String string = resources.getString(android.R.string.config_batterymeterBoltPath);
            this.mCameraLaunchSensor = sensorManager.getDefaultSensor(integer, true);
            if (this.mCameraLaunchSensor != null) {
                if (string.equals(this.mCameraLaunchSensor.getStringType())) {
                    this.mCameraLaunchRegistered = sensorManager.registerListener(this.mGestureListener, this.mCameraLaunchSensor, 0);
                    return;
                }
                throw new java.lang.RuntimeException(java.lang.String.format("Wrong configuration. Sensor type and sensor string type don't match: %s in resources, %s in the sensor.", string, this.mCameraLaunchSensor.getStringType()));
            }
        }
    }

    private void unregisterCameraLiftTrigger() {
        if (this.mCameraLiftRegistered) {
            this.mCameraLiftRegistered = false;
            ((android.hardware.SensorManager) this.mContext.getSystemService("sensor")).cancelTriggerSensor(this.mCameraLiftTriggerListener, this.mCameraLiftTriggerSensor);
        }
    }

    private void registerCameraLiftTrigger(android.content.res.Resources resources) {
        if (this.mCameraLiftRegistered) {
            return;
        }
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService("sensor");
        int integer = resources.getInteger(android.R.integer.config_cameraLaunchGestureSensorType);
        if (integer != -1) {
            this.mCameraLiftRegistered = false;
            java.lang.String string = resources.getString(android.R.string.config_batterymeterErrorPerimeterPath);
            this.mCameraLiftTriggerSensor = sensorManager.getDefaultSensor(integer, true);
            if (this.mCameraLiftTriggerSensor != null) {
                if (string.equals(this.mCameraLiftTriggerSensor.getStringType())) {
                    this.mCameraLiftRegistered = sensorManager.requestTriggerSensor(this.mCameraLiftTriggerListener, this.mCameraLiftTriggerSensor);
                    return;
                }
                throw new java.lang.RuntimeException(java.lang.String.format("Wrong configuration. Sensor type and sensor string type don't match: %s in resources, %s in the sensor.", string, this.mCameraLiftTriggerSensor.getStringType()));
            }
        }
    }

    public static boolean isCameraLaunchSettingEnabled(android.content.Context context, int i) {
        return isCameraLaunchEnabled(context.getResources()) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "camera_gesture_disabled", 0, i) == 0;
    }

    public static boolean isCameraDoubleTapPowerSettingEnabled(android.content.Context context, int i) {
        return isCameraDoubleTapPowerEnabled(context.getResources()) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "camera_double_tap_power_gesture_disabled", 0, i) == 0;
    }

    public static boolean isCameraLiftTriggerSettingEnabled(android.content.Context context, int i) {
        return isCameraLiftTriggerEnabled(context.getResources()) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "camera_lift_trigger_enabled", 1, i) != 0;
    }

    public static boolean isEmergencyGestureSettingEnabled(android.content.Context context, int i) {
        return isEmergencyGestureEnabled(context.getResources()) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "emergency_gesture_enabled", isDefaultEmergencyGestureEnabled(context.getResources()) ? 1 : 0, i) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getEmergencyGesturePowerButtonCooldownPeriodMs(android.content.Context context, int i) {
        return java.lang.Math.min(android.provider.Settings.Global.getInt(context.getContentResolver(), "emergency_gesture_power_button_cooldown_period_ms", 3000), 5000);
    }

    private static boolean isCameraLaunchEnabled(android.content.res.Resources resources) {
        return (resources.getInteger(android.R.integer.config_burnInProtectionMinVerticalOffset) != -1) && !android.os.SystemProperties.getBoolean("gesture.disable_camera_launch", false);
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isCameraDoubleTapPowerEnabled(android.content.res.Resources resources) {
        return resources.getBoolean(android.R.bool.config_cameraDoubleTapPowerGestureEnabled);
    }

    private static boolean isCameraLiftTriggerEnabled(android.content.res.Resources resources) {
        return resources.getInteger(android.R.integer.config_cameraLaunchGestureSensorType) != -1;
    }

    private static boolean isEmergencyGestureEnabled(android.content.res.Resources resources) {
        return resources.getBoolean(android.R.bool.config_earcFeatureEnabled_default);
    }

    private static boolean isDefaultEmergencyGestureEnabled(android.content.res.Resources resources) {
        return resources.getBoolean(android.R.bool.config_defaultEmergencyGestureEnabled);
    }

    public static boolean isGestureLauncherEnabled(android.content.res.Resources resources) {
        return isCameraLaunchEnabled(resources) || isCameraDoubleTapPowerEnabled(resources) || isCameraLiftTriggerEnabled(resources) || isEmergencyGestureEnabled(resources);
    }

    public boolean interceptPowerKeyDown(android.view.KeyEvent keyEvent, boolean z, android.util.MutableBoolean mutableBoolean) {
        long eventTime;
        boolean z2;
        boolean z3;
        boolean z4;
        if (this.mEmergencyGestureEnabled && this.mEmergencyGesturePowerButtonCooldownPeriodMs >= 0 && keyEvent.getEventTime() - this.mLastEmergencyGestureTriggered < this.mEmergencyGesturePowerButtonCooldownPeriodMs) {
            android.util.Slog.i(TAG, java.lang.String.format("Suppressing power button: within %dms cooldown period after Emergency Gesture. Begin=%dms, end=%dms.", java.lang.Integer.valueOf(this.mEmergencyGesturePowerButtonCooldownPeriodMs), java.lang.Long.valueOf(this.mLastEmergencyGestureTriggered), java.lang.Long.valueOf(this.mLastEmergencyGestureTriggered + this.mEmergencyGesturePowerButtonCooldownPeriodMs)));
            mutableBoolean.value = false;
            return true;
        }
        if (keyEvent.isLongPress()) {
            mutableBoolean.value = false;
            return false;
        }
        synchronized (this) {
            try {
                eventTime = keyEvent.getEventTime() - this.mLastPowerDown;
                this.mLastPowerDown = keyEvent.getEventTime();
                if (eventTime >= 500) {
                    this.mFirstPowerDown = keyEvent.getEventTime();
                    this.mPowerButtonConsecutiveTaps = 1;
                    this.mPowerButtonSlowConsecutiveTaps = 1;
                } else if (eventTime >= CAMERA_POWER_DOUBLE_TAP_MAX_TIME_MS) {
                    this.mFirstPowerDown = keyEvent.getEventTime();
                    this.mPowerButtonConsecutiveTaps = 1;
                    this.mPowerButtonSlowConsecutiveTaps++;
                } else {
                    this.mPowerButtonConsecutiveTaps++;
                    this.mPowerButtonSlowConsecutiveTaps++;
                }
                if (!this.mEmergencyGestureEnabled) {
                    z2 = false;
                    z3 = false;
                } else {
                    if (this.mPowerButtonConsecutiveTaps <= (this.mHasFeatureWatch ? 5 : 1)) {
                        z2 = false;
                    } else {
                        z2 = z;
                    }
                    if (this.mPowerButtonConsecutiveTaps == 5) {
                        long eventTime2 = keyEvent.getEventTime() - this.mFirstPowerDown;
                        if (eventTime2 <= android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "emergency_gesture_tap_detection_min_time_ms", 200)) {
                            android.util.Slog.i(TAG, "Emergency gesture detected but it's too fast. Gesture time: " + eventTime2 + " ms");
                            this.mFirstPowerDown = keyEvent.getEventTime();
                            this.mPowerButtonConsecutiveTaps = 1;
                            this.mPowerButtonSlowConsecutiveTaps = 1;
                        } else {
                            android.util.Slog.i(TAG, "Emergency gesture detected. Gesture time: " + eventTime2 + " ms");
                            this.mMetricsLogger.histogram("emergency_gesture_spent_time", (int) eventTime2);
                            z3 = true;
                        }
                    }
                    z3 = false;
                }
                if (this.mCameraDoubleTapPowerEnabled && eventTime < CAMERA_POWER_DOUBLE_TAP_MAX_TIME_MS && this.mPowerButtonConsecutiveTaps == 2) {
                    z4 = true;
                } else {
                    z = z2;
                    z4 = false;
                }
            } finally {
            }
        }
        if (this.mPowerButtonConsecutiveTaps > 1 || this.mPowerButtonSlowConsecutiveTaps > 1) {
            android.util.Slog.i(TAG, java.lang.Long.valueOf(this.mPowerButtonConsecutiveTaps) + " consecutive power button taps detected, " + java.lang.Long.valueOf(this.mPowerButtonSlowConsecutiveTaps) + " consecutive slow power button taps detected");
        }
        if (z4) {
            android.util.Slog.i(TAG, "Power button double tap gesture detected, launching camera. Interval=" + eventTime + "ms");
            z4 = handleCameraGesture(false, 1);
            if (z4) {
                this.mMetricsLogger.action(255, (int) eventTime);
                this.mUiEventLogger.log(com.android.server.GestureLauncherService.GestureLauncherEvent.GESTURE_CAMERA_DOUBLE_TAP_POWER);
            }
        } else if (z3) {
            android.util.Slog.i(TAG, "Emergency gesture detected, launching.");
            z3 = handleEmergencyGesture();
            this.mUiEventLogger.log(com.android.server.GestureLauncherService.GestureLauncherEvent.GESTURE_EMERGENCY_TAP_POWER);
            if (z3) {
                synchronized (this) {
                    this.mLastEmergencyGestureTriggered = keyEvent.getEventTime();
                }
            }
        }
        this.mMetricsLogger.histogram("power_consecutive_short_tap_count", this.mPowerButtonSlowConsecutiveTaps);
        this.mMetricsLogger.histogram("power_double_tap_interval", (int) eventTime);
        mutableBoolean.value = z4 || z3;
        return z && isUserSetupComplete();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean handleCameraGesture(boolean z, int i) {
        android.os.Trace.traceBegin(64L, "GestureLauncher:handleCameraGesture");
        try {
            if (!isUserSetupComplete()) {
                android.os.Trace.traceEnd(64L);
                return false;
            }
            if (z) {
                this.mWakeLock.acquire(500L);
            }
            ((com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class)).onCameraLaunchGestureDetected(i);
            android.os.Trace.traceEnd(64L);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean handleEmergencyGesture() {
        android.os.Trace.traceBegin(64L, "GestureLauncher:handleEmergencyGesture");
        try {
            if (isUserSetupComplete()) {
                ((com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class)).onEmergencyActionLaunchGestureDetected();
                android.os.Trace.traceEnd(64L);
                return true;
            }
            android.os.Trace.traceEnd(64L);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            throw th;
        }
    }

    private boolean isUserSetupComplete() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) != 0;
    }

    private final class GestureEventListener implements android.hardware.SensorEventListener {
        private GestureEventListener() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            if (com.android.server.GestureLauncherService.this.mCameraLaunchRegistered && sensorEvent.sensor == com.android.server.GestureLauncherService.this.mCameraLaunchSensor && com.android.server.GestureLauncherService.this.handleCameraGesture(true, 0)) {
                com.android.server.GestureLauncherService.this.mMetricsLogger.action(256);
                com.android.server.GestureLauncherService.this.mUiEventLogger.log(com.android.server.GestureLauncherService.GestureLauncherEvent.GESTURE_CAMERA_WIGGLE);
                trackCameraLaunchEvent(sensorEvent);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }

        private void trackCameraLaunchEvent(android.hardware.SensorEvent sensorEvent) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = elapsedRealtime - com.android.server.GestureLauncherService.this.mCameraGestureOnTimeMs;
            double d = j;
            long j2 = (long) (r5[0] * d);
            long j3 = (long) (d * r5[1]);
            int i = (int) sensorEvent.values[2];
            long j4 = elapsedRealtime - com.android.server.GestureLauncherService.this.mCameraGestureLastEventTime;
            long j5 = j2 - com.android.server.GestureLauncherService.this.mCameraGestureSensor1LastOnTimeMs;
            long j6 = j3 - com.android.server.GestureLauncherService.this.mCameraGestureSensor2LastOnTimeMs;
            int i2 = i - com.android.server.GestureLauncherService.this.mCameraLaunchLastEventExtra;
            if (j4 < 0 || j5 < 0 || j6 < 0) {
                return;
            }
            com.android.server.EventLogTags.writeCameraGestureTriggered(j4, j5, j6, i2);
            com.android.server.GestureLauncherService.this.mCameraGestureLastEventTime = elapsedRealtime;
            com.android.server.GestureLauncherService.this.mCameraGestureSensor1LastOnTimeMs = j2;
            com.android.server.GestureLauncherService.this.mCameraGestureSensor2LastOnTimeMs = j3;
            com.android.server.GestureLauncherService.this.mCameraLaunchLastEventExtra = i;
        }
    }

    private final class CameraLiftTriggerEventListener extends android.hardware.TriggerEventListener {
        private CameraLiftTriggerEventListener() {
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(android.hardware.TriggerEvent triggerEvent) {
            if (com.android.server.GestureLauncherService.this.mCameraLiftRegistered && triggerEvent.sensor == com.android.server.GestureLauncherService.this.mCameraLiftTriggerSensor) {
                com.android.server.GestureLauncherService.this.mContext.getResources();
                android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) com.android.server.GestureLauncherService.this.mContext.getSystemService("sensor");
                boolean isKeyguardShowingAndNotOccluded = com.android.server.GestureLauncherService.this.mWindowManagerInternal.isKeyguardShowingAndNotOccluded();
                boolean isInteractive = com.android.server.GestureLauncherService.this.mPowerManager.isInteractive();
                if ((isKeyguardShowingAndNotOccluded || !isInteractive) && com.android.server.GestureLauncherService.this.handleCameraGesture(true, 2)) {
                    com.android.internal.logging.MetricsLogger.action(com.android.server.GestureLauncherService.this.mContext, 989);
                    com.android.server.GestureLauncherService.this.mUiEventLogger.log(com.android.server.GestureLauncherService.GestureLauncherEvent.GESTURE_CAMERA_LIFT);
                }
                com.android.server.GestureLauncherService.this.mCameraLiftRegistered = sensorManager.requestTriggerSensor(com.android.server.GestureLauncherService.this.mCameraLiftTriggerListener, com.android.server.GestureLauncherService.this.mCameraLiftTriggerSensor);
            }
        }
    }
}
