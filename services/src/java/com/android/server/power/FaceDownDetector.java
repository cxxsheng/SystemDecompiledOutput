package com.android.server.power;

/* loaded from: classes2.dex */
public class FaceDownDetector implements android.hardware.SensorEventListener {
    private static final boolean DEBUG = false;
    static final float DEFAULT_ACCELERATION_THRESHOLD = 0.2f;
    private static final boolean DEFAULT_FEATURE_ENABLED = true;
    private static final long DEFAULT_INTERACTION_BACKOFF = 60000;
    static final long DEFAULT_TIME_THRESHOLD_MILLIS = 1000;
    static final float DEFAULT_Z_ACCELERATION_THRESHOLD = -9.5f;
    static final java.lang.String KEY_ACCELERATION_THRESHOLD = "acceleration_threshold";
    static final java.lang.String KEY_FEATURE_ENABLED = "enable_flip_to_screen_off";
    private static final java.lang.String KEY_INTERACTION_BACKOFF = "face_down_interaction_backoff_millis";
    static final java.lang.String KEY_TIME_THRESHOLD_MILLIS = "time_threshold_millis";
    static final java.lang.String KEY_Z_ACCELERATION_THRESHOLD = "z_acceleration_threshold";
    private static final float MOVING_AVERAGE_WEIGHT = 0.5f;
    private static final int SCREEN_OFF_RESULT = 4;
    private static final java.lang.String TAG = "FaceDownDetector";
    private static final int UNFLIP = 2;
    private static final int UNKNOWN = 1;
    private static final int USER_INTERACTION = 3;
    private float mAccelerationThreshold;
    private android.hardware.Sensor mAccelerometer;
    private android.content.Context mContext;
    private final android.os.Handler mHandler;
    private boolean mIsEnabled;
    private final java.util.function.Consumer<java.lang.Boolean> mOnFlip;

    @com.android.internal.annotations.VisibleForTesting
    final android.content.BroadcastReceiver mScreenReceiver;
    private android.hardware.SensorManager mSensorManager;
    private int mSensorMaxLatencyMicros;
    private java.time.Duration mTimeThreshold;
    private final java.lang.Runnable mUserActivityRunnable;
    private long mUserInteractionBackoffMillis;
    private float mZAccelerationThreshold;
    private float mZAccelerationThresholdLenient;
    private long mLastFlipTime = 0;
    public int mPreviousResultType = 1;
    public long mPreviousResultTime = 0;
    private long mMillisSaved = 0;
    private final com.android.server.power.FaceDownDetector.ExponentialMovingAverage mCurrentXYAcceleration = new com.android.server.power.FaceDownDetector.ExponentialMovingAverage(this, 0.5f);
    private final com.android.server.power.FaceDownDetector.ExponentialMovingAverage mCurrentZAcceleration = new com.android.server.power.FaceDownDetector.ExponentialMovingAverage(this, 0.5f);
    private boolean mFaceDown = false;
    private boolean mInteractive = false;
    private boolean mActive = false;
    private float mPrevAcceleration = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private long mPrevAccelerationTime = 0;
    private boolean mZAccelerationIsFaceDown = false;
    private long mZAccelerationFaceDownTime = 0;

    public FaceDownDetector(@android.annotation.NonNull java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(consumer);
        this.mOnFlip = consumer;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mScreenReceiver = new com.android.server.power.FaceDownDetector.ScreenStateReceiver();
        this.mUserActivityRunnable = new java.lang.Runnable() { // from class: com.android.server.power.FaceDownDetector$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.FaceDownDetector.this.lambda$new$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.mFaceDown) {
            exitFaceDown(3, android.os.SystemClock.uptimeMillis() - this.mLastFlipTime);
            updateActiveState();
        }
    }

    public void systemReady(android.content.Context context) {
        this.mContext = context;
        this.mSensorManager = (android.hardware.SensorManager) context.getSystemService(android.hardware.SensorManager.class);
        this.mAccelerometer = this.mSensorManager.getDefaultSensor(1);
        readValuesFromDeviceConfig();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.FaceDownDetector$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.power.FaceDownDetector.this.lambda$systemReady$1(properties);
            }
        });
        updateActiveState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$1(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    private void registerScreenReceiver(android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.setPriority(1000);
        context.registerReceiver(this.mScreenReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveState() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        boolean z = this.mInteractive && this.mIsEnabled && !(this.mPreviousResultType == 3 && ((uptimeMillis - this.mPreviousResultTime) > this.mUserInteractionBackoffMillis ? 1 : ((uptimeMillis - this.mPreviousResultTime) == this.mUserInteractionBackoffMillis ? 0 : -1)) < 0);
        if (this.mActive != z) {
            if (z) {
                this.mSensorManager.registerListener(this, this.mAccelerometer, 3, this.mSensorMaxLatencyMicros);
                if (this.mPreviousResultType == 4) {
                    logScreenOff();
                }
            } else {
                if (this.mFaceDown && !this.mInteractive) {
                    this.mPreviousResultType = 4;
                    this.mPreviousResultTime = uptimeMillis;
                }
                this.mSensorManager.unregisterListener(this);
                this.mFaceDown = false;
                this.mOnFlip.accept(false);
            }
            this.mActive = z;
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("FaceDownDetector:");
        printWriter.println("  mFaceDown=" + this.mFaceDown);
        printWriter.println("  mActive=" + this.mActive);
        printWriter.println("  mLastFlipTime=" + this.mLastFlipTime);
        printWriter.println("  mSensorMaxLatencyMicros=" + this.mSensorMaxLatencyMicros);
        printWriter.println("  mUserInteractionBackoffMillis=" + this.mUserInteractionBackoffMillis);
        printWriter.println("  mPreviousResultTime=" + this.mPreviousResultTime);
        printWriter.println("  mPreviousResultType=" + this.mPreviousResultType);
        printWriter.println("  mMillisSaved=" + this.mMillisSaved);
        printWriter.println("  mZAccelerationThreshold=" + this.mZAccelerationThreshold);
        printWriter.println("  mAccelerationThreshold=" + this.mAccelerationThreshold);
        printWriter.println("  mTimeThreshold=" + this.mTimeThreshold);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != 1 || !this.mActive || !this.mIsEnabled) {
            return;
        }
        float f = sensorEvent.values[0];
        float f2 = sensorEvent.values[1];
        this.mCurrentXYAcceleration.updateMovingAverage((f * f) + (f2 * f2));
        this.mCurrentZAcceleration.updateMovingAverage(sensorEvent.values[2]);
        long j = sensorEvent.timestamp;
        if (java.lang.Math.abs(this.mCurrentXYAcceleration.mMovingAverage - this.mPrevAcceleration) > this.mAccelerationThreshold) {
            this.mPrevAcceleration = this.mCurrentXYAcceleration.mMovingAverage;
            this.mPrevAccelerationTime = j;
        }
        boolean z = j - this.mPrevAccelerationTime <= this.mTimeThreshold.toNanos();
        boolean z2 = this.mCurrentZAcceleration.mMovingAverage < (this.mFaceDown ? this.mZAccelerationThresholdLenient : this.mZAccelerationThreshold);
        boolean z3 = z2 && this.mZAccelerationIsFaceDown && j - this.mZAccelerationFaceDownTime > this.mTimeThreshold.toNanos();
        if (z2 && !this.mZAccelerationIsFaceDown) {
            this.mZAccelerationFaceDownTime = j;
            this.mZAccelerationIsFaceDown = true;
        } else if (!z2) {
            this.mZAccelerationIsFaceDown = false;
        }
        if (!z && z3 && !this.mFaceDown) {
            faceDownDetected();
        } else if (!z3 && this.mFaceDown) {
            unFlipDetected();
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    private void faceDownDetected() {
        this.mLastFlipTime = android.os.SystemClock.uptimeMillis();
        this.mFaceDown = true;
        this.mOnFlip.accept(true);
    }

    private void unFlipDetected() {
        exitFaceDown(2, android.os.SystemClock.uptimeMillis() - this.mLastFlipTime);
    }

    public void userActivity(int i) {
        if (i != 5) {
            this.mHandler.post(this.mUserActivityRunnable);
        }
    }

    private void exitFaceDown(int i, long j) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.FACE_DOWN_REPORTED, i, j, 0L, 0L);
        this.mFaceDown = false;
        this.mLastFlipTime = 0L;
        this.mPreviousResultType = i;
        this.mPreviousResultTime = android.os.SystemClock.uptimeMillis();
        this.mOnFlip.accept(false);
    }

    private void logScreenOff() {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.FACE_DOWN_REPORTED, 4, this.mPreviousResultTime - this.mLastFlipTime, this.mMillisSaved, android.os.SystemClock.uptimeMillis() - this.mPreviousResultTime);
        this.mPreviousResultType = 1;
    }

    private boolean isEnabled() {
        return android.provider.DeviceConfig.getBoolean("attention_manager_service", KEY_FEATURE_ENABLED, true) && this.mContext.getResources().getBoolean(android.R.bool.config_fillMainBuiltInDisplayCutout);
    }

    private float getAccelerationThreshold() {
        return getFloatFlagValue(KEY_ACCELERATION_THRESHOLD, DEFAULT_ACCELERATION_THRESHOLD, -2.0f, 2.0f);
    }

    private float getZAccelerationThreshold() {
        return getFloatFlagValue(KEY_Z_ACCELERATION_THRESHOLD, DEFAULT_Z_ACCELERATION_THRESHOLD, -15.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    private long getUserInteractionBackoffMillis() {
        return getLongFlagValue(KEY_INTERACTION_BACKOFF, 60000L, 0L, 3600000L);
    }

    private int getSensorMaxLatencyMicros() {
        return this.mContext.getResources().getInteger(android.R.integer.config_extraFreeKbytesAdjust);
    }

    private float getFloatFlagValue(java.lang.String str, float f, float f2, float f3) {
        float f4 = android.provider.DeviceConfig.getFloat("attention_manager_service", str, f);
        if (f4 < f2 || f4 > f3) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: " + str);
            return f;
        }
        return f4;
    }

    private long getLongFlagValue(java.lang.String str, long j, long j2, long j3) {
        long j4 = android.provider.DeviceConfig.getLong("attention_manager_service", str, j);
        if (j4 < j2 || j4 > j3) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: " + str);
            return j;
        }
        return j4;
    }

    private java.time.Duration getTimeThreshold() {
        long j = android.provider.DeviceConfig.getLong("attention_manager_service", KEY_TIME_THRESHOLD_MILLIS, 1000L);
        if (j < 0 || j > 15000) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: time_threshold_millis");
            return java.time.Duration.ofMillis(1000L);
        }
        return java.time.Duration.ofMillis(j);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        char c;
        for (java.lang.String str : set) {
            switch (str.hashCode()) {
                case -1974380596:
                    if (str.equals(KEY_TIME_THRESHOLD_MILLIS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1762356372:
                    if (str.equals(KEY_ACCELERATION_THRESHOLD)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1566292150:
                    if (str.equals(KEY_FEATURE_ENABLED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 941263057:
                    if (str.equals(KEY_Z_ACCELERATION_THRESHOLD)) {
                        c = 1;
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
                case 1:
                case 2:
                case 3:
                    readValuesFromDeviceConfig();
                    updateActiveState();
                    return;
                default:
                    android.util.Slog.i(TAG, "Ignoring change on " + str);
            }
        }
    }

    private void readValuesFromDeviceConfig() {
        this.mAccelerationThreshold = getAccelerationThreshold();
        this.mZAccelerationThreshold = getZAccelerationThreshold();
        this.mZAccelerationThresholdLenient = this.mZAccelerationThreshold + 1.0f;
        this.mTimeThreshold = getTimeThreshold();
        this.mSensorMaxLatencyMicros = getSensorMaxLatencyMicros();
        this.mUserInteractionBackoffMillis = getUserInteractionBackoffMillis();
        boolean z = this.mIsEnabled;
        this.mIsEnabled = isEnabled();
        if (z != this.mIsEnabled) {
            if (!this.mIsEnabled) {
                this.mContext.unregisterReceiver(this.mScreenReceiver);
                this.mInteractive = false;
            } else {
                registerScreenReceiver(this.mContext);
                this.mInteractive = ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).isInteractive();
            }
        }
        android.util.Slog.i(TAG, "readValuesFromDeviceConfig():\nmAccelerationThreshold=" + this.mAccelerationThreshold + "\nmZAccelerationThreshold=" + this.mZAccelerationThreshold + "\nmTimeThreshold=" + this.mTimeThreshold + "\nmIsEnabled=" + this.mIsEnabled);
    }

    public void setMillisSaved(long j) {
        this.mMillisSaved = j;
    }

    private final class ScreenStateReceiver extends android.content.BroadcastReceiver {
        private ScreenStateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                com.android.server.power.FaceDownDetector.this.mInteractive = false;
                com.android.server.power.FaceDownDetector.this.updateActiveState();
            } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                com.android.server.power.FaceDownDetector.this.mInteractive = true;
                com.android.server.power.FaceDownDetector.this.updateActiveState();
            }
        }
    }

    private final class ExponentialMovingAverage {
        private final float mAlpha;
        private final float mInitialAverage;
        private float mMovingAverage;

        ExponentialMovingAverage(com.android.server.power.FaceDownDetector faceDownDetector, float f) {
            this(f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        }

        ExponentialMovingAverage(float f, float f2) {
            this.mAlpha = f;
            this.mInitialAverage = f2;
            this.mMovingAverage = f2;
        }

        void updateMovingAverage(float f) {
            this.mMovingAverage = f + (this.mAlpha * (this.mMovingAverage - f));
        }

        void reset() {
            this.mMovingAverage = this.mInitialAverage;
        }
    }
}
