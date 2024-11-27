package com.android.server.display;

/* loaded from: classes.dex */
public class AutomaticBrightnessController {
    private static final long AMBIENT_LIGHT_PREDICTION_TIME_MILLIS = 100;
    public static final int AUTO_BRIGHTNESS_DISABLED = 2;
    public static final int AUTO_BRIGHTNESS_ENABLED = 1;
    public static final int AUTO_BRIGHTNESS_MODE_DEFAULT = 0;
    public static final int AUTO_BRIGHTNESS_MODE_DOZE = 2;
    public static final int AUTO_BRIGHTNESS_MODE_IDLE = 1;
    public static final int AUTO_BRIGHTNESS_MODE_MAX = 2;
    public static final int AUTO_BRIGHTNESS_OFF_DUE_TO_DISPLAY_STATE = 3;
    private static final int BRIGHTNESS_ADJUSTMENT_SAMPLE_DEBOUNCE_MILLIS = 10000;
    private static final boolean DEBUG_PRETEND_LIGHT_SENSOR_ABSENT = false;
    private static final int MSG_BRIGHTNESS_ADJUSTMENT_SAMPLE = 2;
    private static final int MSG_INVALIDATE_CURRENT_SHORT_TERM_MODEL = 3;
    private static final int MSG_INVALIDATE_PAUSED_SHORT_TERM_MODEL = 7;
    private static final int MSG_RUN_UPDATE = 6;
    private static final int MSG_UPDATE_AMBIENT_LUX = 1;
    private static final int MSG_UPDATE_FOREGROUND_APP = 4;
    private static final int MSG_UPDATE_FOREGROUND_APP_SYNC = 5;
    private static final java.lang.String TAG = "AutomaticBrightnessController";
    private android.app.IActivityTaskManager mActivityTaskManager;
    private float mAmbientBrighteningThreshold;
    private final com.android.server.display.HysteresisLevels mAmbientBrightnessThresholds;
    private final com.android.server.display.HysteresisLevels mAmbientBrightnessThresholdsIdle;
    private float mAmbientDarkeningThreshold;
    private final int mAmbientLightHorizonLong;
    private final int mAmbientLightHorizonShort;
    private com.android.server.display.AutomaticBrightnessController.AmbientLightRingBuffer mAmbientLightRingBuffer;
    private float mAmbientLux;
    private boolean mAmbientLuxValid;
    private boolean mAutoBrightnessOneShot;
    private final long mBrighteningLightDebounceConfig;
    private final long mBrighteningLightDebounceConfigIdle;
    private float mBrightnessAdjustmentSampleOldBrightness;
    private float mBrightnessAdjustmentSampleOldLux;
    private boolean mBrightnessAdjustmentSamplePending;
    private final android.util.SparseArray<com.android.server.display.BrightnessMappingStrategy> mBrightnessMappingStrategyMap;
    private final com.android.server.display.BrightnessRangeController mBrightnessRangeController;
    private final com.android.server.display.BrightnessThrottler mBrightnessThrottler;
    private final com.android.server.display.AutomaticBrightnessController.Callbacks mCallbacks;
    private com.android.server.display.AutomaticBrightnessController.Clock mClock;
    private android.content.Context mContext;

    @android.annotation.NonNull
    private com.android.server.display.BrightnessMappingStrategy mCurrentBrightnessMapper;
    private int mCurrentLightSensorRate;
    private final long mDarkeningLightDebounceConfig;
    private final long mDarkeningLightDebounceConfigIdle;
    private int mDisplayPolicy;
    private final float mDozeScaleFactor;
    private float mFastAmbientLux;
    private int mForegroundAppCategory;
    private java.lang.String mForegroundAppPackageName;
    private com.android.server.display.AutomaticBrightnessController.AutomaticBrightnessHandler mHandler;
    private final int mInitialLightSensorRate;
    private final com.android.server.display.AutomaticBrightnessController.Injector mInjector;
    private boolean mIsBrightnessThrottled;
    private float mLastObservedLux;
    private long mLastObservedLuxTime;
    private final android.hardware.Sensor mLightSensor;
    private long mLightSensorEnableTime;
    private boolean mLightSensorEnabled;
    private final android.hardware.SensorEventListener mLightSensorListener;
    private int mLightSensorWarmUpTimeConfig;
    private boolean mLoggingEnabled;
    private final int mNormalLightSensorRate;
    private android.content.pm.PackageManager mPackageManager;
    private final com.android.server.display.AutomaticBrightnessController.ShortTermModel mPausedShortTermModel;
    private int mPendingForegroundAppCategory;
    private java.lang.String mPendingForegroundAppPackageName;
    private float mPreThresholdBrightness;
    private float mPreThresholdLux;
    private float mRawScreenAutoBrightness;
    private int mRecentLightSamples;
    private final boolean mResetAmbientLuxAfterWarmUpConfig;
    private float mScreenAutoBrightness;
    private float mScreenBrighteningThreshold;
    private final float mScreenBrightnessRangeMaximum;
    private final float mScreenBrightnessRangeMinimum;
    private final com.android.server.display.HysteresisLevels mScreenBrightnessThresholds;
    private final com.android.server.display.HysteresisLevels mScreenBrightnessThresholdsIdle;
    private float mScreenDarkeningThreshold;
    private final android.hardware.SensorManager mSensorManager;
    private final com.android.server.display.AutomaticBrightnessController.ShortTermModel mShortTermModel;
    private float mSlowAmbientLux;
    private int mState;
    private com.android.server.display.AutomaticBrightnessController.TaskStackListenerImpl mTaskStackListener;
    private final int mWeightingIntercept;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutomaticBrightnessMode {
    }

    interface Callbacks {
        void updateBrightness();
    }

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long uptimeMillis();
    }

    AutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController.Callbacks callbacks, android.os.Looper looper, android.hardware.SensorManager sensorManager, android.hardware.Sensor sensor, android.util.SparseArray<com.android.server.display.BrightnessMappingStrategy> sparseArray, int i, float f, float f2, float f3, int i2, int i3, long j, long j2, long j3, long j4, boolean z, com.android.server.display.HysteresisLevels hysteresisLevels, com.android.server.display.HysteresisLevels hysteresisLevels2, com.android.server.display.HysteresisLevels hysteresisLevels3, com.android.server.display.HysteresisLevels hysteresisLevels4, android.content.Context context, com.android.server.display.BrightnessRangeController brightnessRangeController, com.android.server.display.BrightnessThrottler brightnessThrottler, int i4, int i5, float f4, float f5) {
        this(new com.android.server.display.AutomaticBrightnessController.Injector(), callbacks, looper, sensorManager, sensor, sparseArray, i, f, f2, f3, i2, i3, j, j2, j3, j4, z, hysteresisLevels, hysteresisLevels2, hysteresisLevels3, hysteresisLevels4, context, brightnessRangeController, brightnessThrottler, i4, i5, f4, f5);
    }

    @com.android.internal.annotations.VisibleForTesting
    AutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController.Injector injector, com.android.server.display.AutomaticBrightnessController.Callbacks callbacks, android.os.Looper looper, android.hardware.SensorManager sensorManager, android.hardware.Sensor sensor, android.util.SparseArray<com.android.server.display.BrightnessMappingStrategy> sparseArray, int i, float f, float f2, float f3, int i2, int i3, long j, long j2, long j3, long j4, boolean z, com.android.server.display.HysteresisLevels hysteresisLevels, com.android.server.display.HysteresisLevels hysteresisLevels2, com.android.server.display.HysteresisLevels hysteresisLevels3, com.android.server.display.HysteresisLevels hysteresisLevels4, android.content.Context context, com.android.server.display.BrightnessRangeController brightnessRangeController, com.android.server.display.BrightnessThrottler brightnessThrottler, int i4, int i5, float f4, float f5) {
        this.mLastObservedLux = -1.0f;
        this.mScreenAutoBrightness = Float.NaN;
        this.mRawScreenAutoBrightness = Float.NaN;
        this.mDisplayPolicy = 0;
        this.mState = 2;
        this.mLightSensorListener = new android.hardware.SensorEventListener() { // from class: com.android.server.display.AutomaticBrightnessController.2
            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
                if (com.android.server.display.AutomaticBrightnessController.this.mLightSensorEnabled) {
                    com.android.server.display.AutomaticBrightnessController.this.handleLightSensorEvent(com.android.server.display.AutomaticBrightnessController.this.mClock.uptimeMillis(), sensorEvent.values[0]);
                }
            }

            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(android.hardware.Sensor sensor2, int i6) {
            }
        };
        this.mInjector = injector;
        this.mClock = injector.createClock();
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mSensorManager = sensorManager;
        this.mCurrentBrightnessMapper = sparseArray.get(0);
        this.mScreenBrightnessRangeMinimum = f;
        this.mScreenBrightnessRangeMaximum = f2;
        this.mLightSensorWarmUpTimeConfig = i;
        this.mDozeScaleFactor = f3;
        this.mNormalLightSensorRate = i2;
        this.mInitialLightSensorRate = i3;
        this.mCurrentLightSensorRate = -1;
        this.mBrighteningLightDebounceConfig = j;
        this.mDarkeningLightDebounceConfig = j2;
        this.mBrighteningLightDebounceConfigIdle = j3;
        this.mDarkeningLightDebounceConfigIdle = j4;
        this.mResetAmbientLuxAfterWarmUpConfig = z;
        this.mAmbientLightHorizonLong = i5;
        this.mAmbientLightHorizonShort = i4;
        this.mWeightingIntercept = i5;
        this.mAmbientBrightnessThresholds = hysteresisLevels;
        this.mAmbientBrightnessThresholdsIdle = hysteresisLevels3;
        this.mScreenBrightnessThresholds = hysteresisLevels2;
        this.mScreenBrightnessThresholdsIdle = hysteresisLevels4;
        this.mShortTermModel = new com.android.server.display.AutomaticBrightnessController.ShortTermModel();
        this.mPausedShortTermModel = new com.android.server.display.AutomaticBrightnessController.ShortTermModel();
        this.mHandler = new com.android.server.display.AutomaticBrightnessController.AutomaticBrightnessHandler(looper);
        this.mAmbientLightRingBuffer = new com.android.server.display.AutomaticBrightnessController.AmbientLightRingBuffer(this.mNormalLightSensorRate, this.mAmbientLightHorizonLong, this.mClock);
        this.mLightSensor = sensor;
        this.mActivityTaskManager = android.app.ActivityTaskManager.getService();
        this.mPackageManager = this.mContext.getPackageManager();
        this.mTaskStackListener = new com.android.server.display.AutomaticBrightnessController.TaskStackListenerImpl();
        this.mForegroundAppPackageName = null;
        this.mPendingForegroundAppPackageName = null;
        this.mForegroundAppCategory = -1;
        this.mPendingForegroundAppCategory = -1;
        this.mBrightnessRangeController = brightnessRangeController;
        this.mBrightnessThrottler = brightnessThrottler;
        this.mBrightnessMappingStrategyMap = sparseArray;
        if (f5 != -1.0f) {
            setScreenBrightnessByUser(f4, getBrightnessFromNits(f5));
        }
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        for (int i = 0; i < this.mBrightnessMappingStrategyMap.size(); i++) {
            this.mBrightnessMappingStrategyMap.valueAt(i).setLoggingEnabled(z);
        }
        this.mLoggingEnabled = z;
        return true;
    }

    public float getAutomaticScreenBrightness() {
        return getAutomaticScreenBrightness(null);
    }

    public float getAutomaticScreenBrightness(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        if (brightnessEvent != null) {
            brightnessEvent.setLux(this.mAmbientLuxValid ? this.mAmbientLux : Float.NaN);
            brightnessEvent.setPreThresholdLux(this.mPreThresholdLux);
            brightnessEvent.setPreThresholdBrightness(this.mPreThresholdBrightness);
            brightnessEvent.setRecommendedBrightness(this.mScreenAutoBrightness);
            brightnessEvent.setFlags(brightnessEvent.getFlags() | (!this.mAmbientLuxValid ? 2 : 0) | (this.mDisplayPolicy == 1 ? 4 : 0));
            brightnessEvent.setAutoBrightnessMode(getMode());
        }
        if (!this.mAmbientLuxValid) {
            return Float.NaN;
        }
        if (this.mDisplayPolicy == 1) {
            return this.mScreenAutoBrightness * this.mDozeScaleFactor;
        }
        return this.mScreenAutoBrightness;
    }

    float getRawAutomaticScreenBrightness() {
        return this.mRawScreenAutoBrightness;
    }

    public float getAutomaticScreenBrightnessBasedOnLastObservedLux(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        if (this.mLastObservedLux == -1.0f) {
            return Float.NaN;
        }
        float brightness = this.mCurrentBrightnessMapper.getBrightness(this.mLastObservedLux, this.mForegroundAppPackageName, this.mForegroundAppCategory);
        if (this.mDisplayPolicy == 1) {
            brightness *= this.mDozeScaleFactor;
        }
        if (brightnessEvent != null) {
            brightnessEvent.setLux(this.mLastObservedLux);
            brightnessEvent.setRecommendedBrightness(brightness);
            brightnessEvent.setFlags((this.mLastObservedLux == -1.0f ? 2 : 0) | brightnessEvent.getFlags() | (this.mDisplayPolicy == 1 ? 4 : 0));
            brightnessEvent.setAutoBrightnessMode(getMode());
        }
        return brightness;
    }

    public boolean hasValidAmbientLux() {
        return this.mAmbientLuxValid;
    }

    public float getAutomaticScreenBrightnessAdjustment() {
        return this.mCurrentBrightnessMapper.getAutoBrightnessAdjustment();
    }

    public void configure(int i, @android.annotation.Nullable android.hardware.display.BrightnessConfiguration brightnessConfiguration, float f, boolean z, float f2, boolean z2, int i2, boolean z3, boolean z4) {
        this.mState = i;
        boolean brightnessConfiguration2 = setBrightnessConfiguration(brightnessConfiguration, z3) | setDisplayPolicy(i2);
        if (z2) {
            brightnessConfiguration2 |= setAutoBrightnessAdjustment(f2);
        }
        boolean z5 = true;
        boolean z6 = this.mState == 1;
        if (z && z6) {
            brightnessConfiguration2 |= setScreenBrightnessByUser(f);
        }
        boolean z7 = z || z2;
        if (z7 && z6) {
            prepareBrightnessAdjustmentSample();
        }
        boolean lightSensorEnabled = brightnessConfiguration2 | setLightSensorEnabled(z6);
        if (this.mIsBrightnessThrottled == this.mBrightnessThrottler.isThrottled()) {
            z5 = lightSensorEnabled;
        } else {
            this.mIsBrightnessThrottled = this.mBrightnessThrottler.isThrottled();
        }
        if (z5) {
            updateAutoBrightness(false, z7);
        } else {
            handleSettingsChange(z4);
        }
    }

    public void stop() {
        setLightSensorEnabled(false);
    }

    public boolean hasUserDataPoints() {
        return this.mCurrentBrightnessMapper.hasUserDataPoints();
    }

    public boolean isDefaultConfig() {
        return this.mCurrentBrightnessMapper.getMode() == 0 && this.mCurrentBrightnessMapper.isDefaultConfig();
    }

    public android.hardware.display.BrightnessConfiguration getDefaultConfig() {
        return this.mBrightnessMappingStrategyMap.get(0).getDefaultConfig();
    }

    public void update() {
        this.mHandler.sendEmptyMessage(6);
    }

    float getAmbientLux() {
        return this.mAmbientLux;
    }

    float getSlowAmbientLux() {
        return this.mSlowAmbientLux;
    }

    float getFastAmbientLux() {
        return this.mFastAmbientLux;
    }

    private void handleSettingsChange(boolean z) {
        if (this.mAutoBrightnessOneShot && !z) {
            this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, this.mCurrentLightSensorRate * 1000, this.mHandler);
        } else if (!this.mAutoBrightnessOneShot && z) {
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
        }
        this.mAutoBrightnessOneShot = z;
    }

    private boolean setDisplayPolicy(int i) {
        if (this.mDisplayPolicy == i) {
            return false;
        }
        int i2 = this.mDisplayPolicy;
        this.mDisplayPolicy = i;
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Display policy transitioning from " + i2 + " to " + i);
        }
        if (!isInteractivePolicy(i) && isInteractivePolicy(i2) && !isInIdleMode()) {
            this.mHandler.sendEmptyMessageDelayed(3, this.mCurrentBrightnessMapper.getShortTermModelTimeout());
            return true;
        }
        if (isInteractivePolicy(i) && !isInteractivePolicy(i2)) {
            this.mHandler.removeMessages(3);
            return true;
        }
        return true;
    }

    private static boolean isInteractivePolicy(int i) {
        return i == 3 || i == 2;
    }

    private boolean setScreenBrightnessByUser(float f) {
        if (!this.mAmbientLuxValid) {
            return false;
        }
        return setScreenBrightnessByUser(this.mAmbientLux, f);
    }

    private boolean setScreenBrightnessByUser(float f, float f2) {
        if (f == -1.0f || java.lang.Float.isNaN(f2)) {
            return false;
        }
        this.mCurrentBrightnessMapper.addUserDataPoint(f, f2);
        this.mShortTermModel.setUserBrightness(f, f2);
        return true;
    }

    public void resetShortTermModel() {
        this.mCurrentBrightnessMapper.clearUserDataPoints();
        this.mShortTermModel.reset();
    }

    public boolean setBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration, boolean z) {
        if (!this.mBrightnessMappingStrategyMap.get(0).setBrightnessConfiguration(brightnessConfiguration)) {
            return false;
        }
        if (!isInIdleMode() && z) {
            resetShortTermModel();
            return true;
        }
        return true;
    }

    public int getMode() {
        return this.mCurrentBrightnessMapper.getMode();
    }

    public boolean isInIdleMode() {
        return this.mCurrentBrightnessMapper.getMode() == 1;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Automatic Brightness Controller Configuration:");
        printWriter.println("  mState=" + configStateToString(this.mState));
        printWriter.println("  mScreenBrightnessRangeMinimum=" + this.mScreenBrightnessRangeMinimum);
        printWriter.println("  mScreenBrightnessRangeMaximum=" + this.mScreenBrightnessRangeMaximum);
        printWriter.println("  mDozeScaleFactor=" + this.mDozeScaleFactor);
        printWriter.println("  mInitialLightSensorRate=" + this.mInitialLightSensorRate);
        printWriter.println("  mNormalLightSensorRate=" + this.mNormalLightSensorRate);
        printWriter.println("  mLightSensorWarmUpTimeConfig=" + this.mLightSensorWarmUpTimeConfig);
        printWriter.println("  mBrighteningLightDebounceConfig=" + this.mBrighteningLightDebounceConfig);
        printWriter.println("  mDarkeningLightDebounceConfig=" + this.mDarkeningLightDebounceConfig);
        printWriter.println("  mBrighteningLightDebounceConfigIdle=" + this.mBrighteningLightDebounceConfigIdle);
        printWriter.println("  mDarkeningLightDebounceConfigIdle=" + this.mDarkeningLightDebounceConfigIdle);
        printWriter.println("  mResetAmbientLuxAfterWarmUpConfig=" + this.mResetAmbientLuxAfterWarmUpConfig);
        printWriter.println("  mAmbientLightHorizonLong=" + this.mAmbientLightHorizonLong);
        printWriter.println("  mAmbientLightHorizonShort=" + this.mAmbientLightHorizonShort);
        printWriter.println("  mWeightingIntercept=" + this.mWeightingIntercept);
        printWriter.println();
        printWriter.println("Automatic Brightness Controller State:");
        printWriter.println("  mLightSensor=" + this.mLightSensor);
        printWriter.println("  mLightSensorEnabled=" + this.mLightSensorEnabled);
        printWriter.println("  mLightSensorEnableTime=" + android.util.TimeUtils.formatUptime(this.mLightSensorEnableTime));
        printWriter.println("  mCurrentLightSensorRate=" + this.mCurrentLightSensorRate);
        printWriter.println("  mAmbientLux=" + this.mAmbientLux);
        printWriter.println("  mAmbientLuxValid=" + this.mAmbientLuxValid);
        printWriter.println("  mPreThresholdLux=" + this.mPreThresholdLux);
        printWriter.println("  mPreThresholdBrightness=" + this.mPreThresholdBrightness);
        printWriter.println("  mAmbientBrighteningThreshold=" + this.mAmbientBrighteningThreshold);
        printWriter.println("  mAmbientDarkeningThreshold=" + this.mAmbientDarkeningThreshold);
        printWriter.println("  mScreenBrighteningThreshold=" + this.mScreenBrighteningThreshold);
        printWriter.println("  mScreenDarkeningThreshold=" + this.mScreenDarkeningThreshold);
        printWriter.println("  mLastObservedLux=" + this.mLastObservedLux);
        printWriter.println("  mLastObservedLuxTime=" + android.util.TimeUtils.formatUptime(this.mLastObservedLuxTime));
        printWriter.println("  mRecentLightSamples=" + this.mRecentLightSamples);
        printWriter.println("  mAmbientLightRingBuffer=" + this.mAmbientLightRingBuffer);
        printWriter.println("  mScreenAutoBrightness=" + this.mScreenAutoBrightness);
        printWriter.println("  mDisplayPolicy=" + android.hardware.display.DisplayManagerInternal.DisplayPowerRequest.policyToString(this.mDisplayPolicy));
        printWriter.println("  mShortTermModel=");
        this.mShortTermModel.dump(printWriter);
        printWriter.println("  mPausedShortTermModel=");
        this.mPausedShortTermModel.dump(printWriter);
        printWriter.println();
        printWriter.println("  mBrightnessAdjustmentSamplePending=" + this.mBrightnessAdjustmentSamplePending);
        printWriter.println("  mBrightnessAdjustmentSampleOldLux=" + this.mBrightnessAdjustmentSampleOldLux);
        printWriter.println("  mBrightnessAdjustmentSampleOldBrightness=" + this.mBrightnessAdjustmentSampleOldBrightness);
        printWriter.println("  mForegroundAppPackageName=" + this.mForegroundAppPackageName);
        printWriter.println("  mPendingForegroundAppPackageName=" + this.mPendingForegroundAppPackageName);
        printWriter.println("  mForegroundAppCategory=" + this.mForegroundAppCategory);
        printWriter.println("  mPendingForegroundAppCategory=" + this.mPendingForegroundAppCategory);
        printWriter.println("  Current mode=" + com.android.server.display.config.DisplayBrightnessMappingConfig.autoBrightnessModeToString(this.mCurrentBrightnessMapper.getMode()));
        for (int i = 0; i < this.mBrightnessMappingStrategyMap.size(); i++) {
            printWriter.println();
            printWriter.println("  Mapper for mode " + com.android.server.display.config.DisplayBrightnessMappingConfig.autoBrightnessModeToString(this.mBrightnessMappingStrategyMap.keyAt(i)) + ":");
            this.mBrightnessMappingStrategyMap.valueAt(i).dump(printWriter, this.mBrightnessRangeController.getNormalBrightnessMax());
        }
        printWriter.println();
        printWriter.println("  mAmbientBrightnessThresholds=");
        this.mAmbientBrightnessThresholds.dump(printWriter);
        printWriter.println("  mScreenBrightnessThresholds=");
        this.mScreenBrightnessThresholds.dump(printWriter);
        printWriter.println("  mScreenBrightnessThresholdsIdle=");
        this.mScreenBrightnessThresholdsIdle.dump(printWriter);
        printWriter.println("  mAmbientBrightnessThresholdsIdle=");
        this.mAmbientBrightnessThresholdsIdle.dump(printWriter);
    }

    public float[] getLastSensorValues() {
        return this.mAmbientLightRingBuffer.getAllLuxValues();
    }

    public long[] getLastSensorTimestamps() {
        return this.mAmbientLightRingBuffer.getAllTimestamps();
    }

    private java.lang.String configStateToString(int i) {
        switch (i) {
            case 1:
                return "AUTO_BRIGHTNESS_ENABLED";
            case 2:
                return "AUTO_BRIGHTNESS_DISABLED";
            case 3:
                return "AUTO_BRIGHTNESS_OFF_DUE_TO_DISPLAY_STATE";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    private boolean setLightSensorEnabled(boolean z) {
        if (z) {
            if (!this.mLightSensorEnabled) {
                this.mLightSensorEnabled = true;
                this.mLightSensorEnableTime = this.mClock.uptimeMillis();
                this.mCurrentLightSensorRate = this.mInitialLightSensorRate;
                registerForegroundAppUpdater();
                this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, this.mCurrentLightSensorRate * 1000, this.mHandler);
                return true;
            }
        } else if (this.mLightSensorEnabled) {
            this.mLightSensorEnabled = false;
            this.mAmbientLuxValid = !this.mResetAmbientLuxAfterWarmUpConfig;
            if (!this.mAmbientLuxValid) {
                this.mPreThresholdLux = Float.NaN;
            }
            this.mScreenAutoBrightness = Float.NaN;
            this.mRawScreenAutoBrightness = Float.NaN;
            this.mPreThresholdBrightness = Float.NaN;
            this.mRecentLightSamples = 0;
            this.mAmbientLightRingBuffer.clear();
            this.mCurrentLightSensorRate = -1;
            this.mHandler.removeMessages(1);
            unregisterForegroundAppUpdater();
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLightSensorEvent(long j, float f) {
        android.os.Trace.traceCounter(131072L, "ALS", (int) f);
        this.mHandler.removeMessages(1);
        if (this.mAmbientLightRingBuffer.size() == 0) {
            adjustLightSensorRate(this.mNormalLightSensorRate);
        }
        applyLightSensorMeasurement(j, f);
        updateAmbientLux(j);
    }

    private void applyLightSensorMeasurement(long j, float f) {
        this.mRecentLightSamples++;
        this.mAmbientLightRingBuffer.prune(j - this.mAmbientLightHorizonLong);
        this.mAmbientLightRingBuffer.push(j, f);
        this.mLastObservedLux = f;
        this.mLastObservedLuxTime = j;
    }

    private void adjustLightSensorRate(int i) {
        if (i != this.mCurrentLightSensorRate) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "adjustLightSensorRate: previousRate=" + this.mCurrentLightSensorRate + ", currentRate=" + i);
            }
            this.mCurrentLightSensorRate = i;
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
            this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, i * 1000, this.mHandler);
        }
    }

    private boolean setAutoBrightnessAdjustment(float f) {
        return this.mCurrentBrightnessMapper.setAutoBrightnessAdjustment(f);
    }

    private void setAmbientLux(float f) {
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "setAmbientLux(" + f + ")");
        }
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            android.util.Slog.w(TAG, "Ambient lux was negative, ignoring and setting to 0");
            f = 0.0f;
        }
        this.mAmbientLux = f;
        if (isInIdleMode()) {
            this.mAmbientBrighteningThreshold = this.mAmbientBrightnessThresholdsIdle.getBrighteningThreshold(f);
            this.mAmbientDarkeningThreshold = this.mAmbientBrightnessThresholdsIdle.getDarkeningThreshold(f);
        } else {
            this.mAmbientBrighteningThreshold = this.mAmbientBrightnessThresholds.getBrighteningThreshold(f);
            this.mAmbientDarkeningThreshold = this.mAmbientBrightnessThresholds.getDarkeningThreshold(f);
        }
        this.mBrightnessRangeController.onAmbientLuxChange(this.mAmbientLux);
        this.mShortTermModel.maybeReset(this.mAmbientLux);
    }

    private float calculateAmbientLux(long j, long j2) {
        int i;
        long j3;
        long j4 = j;
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "calculateAmbientLux(" + j4 + ", " + j2 + ")");
        }
        int size = this.mAmbientLightRingBuffer.size();
        if (size == 0) {
            android.util.Slog.e(TAG, "calculateAmbientLux: No ambient light readings available");
            return -1.0f;
        }
        long j5 = j4 - j2;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            i2++;
            if (this.mAmbientLightRingBuffer.getTime(i2) > j5) {
                break;
            }
            i3++;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "calculateAmbientLux: selected endIndex=" + i3 + ", point=(" + this.mAmbientLightRingBuffer.getTime(i3) + ", " + this.mAmbientLightRingBuffer.getLux(i3) + ")");
        }
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        long j6 = AMBIENT_LIGHT_PREDICTION_TIME_MILLIS;
        float f2 = 0.0f;
        while (i >= i3) {
            long time = this.mAmbientLightRingBuffer.getTime(i);
            if (i == i3 && time < j5) {
                time = j5;
            }
            long j7 = time - j4;
            float calculateWeight = calculateWeight(j7, j6);
            float lux = this.mAmbientLightRingBuffer.getLux(i);
            if (!this.mLoggingEnabled) {
                j3 = j5;
            } else {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                j3 = j5;
                sb.append("calculateAmbientLux: [");
                sb.append(j7);
                sb.append(", ");
                sb.append(j6);
                sb.append("]: lux=");
                sb.append(lux);
                sb.append(", weight=");
                sb.append(calculateWeight);
                android.util.Slog.d(TAG, sb.toString());
            }
            f += calculateWeight;
            f2 += lux * calculateWeight;
            i--;
            j4 = j;
            j5 = j3;
            j6 = j7;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "calculateAmbientLux: totalWeight=" + f + ", newAmbientLux=" + (f2 / f));
        }
        return f2 / f;
    }

    private float calculateWeight(long j, long j2) {
        return weightIntegral(j2) - weightIntegral(j);
    }

    private float weightIntegral(long j) {
        float f = j;
        return f * ((0.5f * f) + this.mWeightingIntercept);
    }

    private long nextAmbientLightBrighteningTransition(long j) {
        for (int size = this.mAmbientLightRingBuffer.size() - 1; size >= 0 && this.mAmbientLightRingBuffer.getLux(size) > this.mAmbientBrighteningThreshold; size--) {
            j = this.mAmbientLightRingBuffer.getTime(size);
        }
        return j + (isInIdleMode() ? this.mBrighteningLightDebounceConfigIdle : this.mBrighteningLightDebounceConfig);
    }

    private long nextAmbientLightDarkeningTransition(long j) {
        for (int size = this.mAmbientLightRingBuffer.size() - 1; size >= 0 && this.mAmbientLightRingBuffer.getLux(size) < this.mAmbientDarkeningThreshold; size--) {
            j = this.mAmbientLightRingBuffer.getTime(size);
        }
        return j + (isInIdleMode() ? this.mDarkeningLightDebounceConfigIdle : this.mDarkeningLightDebounceConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAmbientLux() {
        long uptimeMillis = this.mClock.uptimeMillis();
        this.mAmbientLightRingBuffer.prune(uptimeMillis - this.mAmbientLightHorizonLong);
        updateAmbientLux(uptimeMillis);
    }

    private void updateAmbientLux(long j) {
        if (!this.mAmbientLuxValid) {
            long j2 = this.mLightSensorWarmUpTimeConfig + this.mLightSensorEnableTime;
            if (j < j2) {
                if (this.mLoggingEnabled) {
                    android.util.Slog.d(TAG, "updateAmbientLux: Sensor not ready yet: time=" + j + ", timeWhenSensorWarmedUp=" + j2);
                }
                this.mHandler.sendEmptyMessageAtTime(1, j2);
                return;
            }
            setAmbientLux(calculateAmbientLux(j, this.mAmbientLightHorizonShort));
            this.mAmbientLuxValid = true;
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "updateAmbientLux: Initializing: mAmbientLightRingBuffer=" + this.mAmbientLightRingBuffer + ", mAmbientLux=" + this.mAmbientLux);
            }
            updateAutoBrightness(true, false);
        }
        long nextAmbientLightBrighteningTransition = nextAmbientLightBrighteningTransition(j);
        long nextAmbientLightDarkeningTransition = nextAmbientLightDarkeningTransition(j);
        this.mSlowAmbientLux = calculateAmbientLux(j, this.mAmbientLightHorizonLong);
        this.mFastAmbientLux = calculateAmbientLux(j, this.mAmbientLightHorizonShort);
        if ((this.mSlowAmbientLux >= this.mAmbientBrighteningThreshold && this.mFastAmbientLux >= this.mAmbientBrighteningThreshold && nextAmbientLightBrighteningTransition <= j) || (this.mSlowAmbientLux <= this.mAmbientDarkeningThreshold && this.mFastAmbientLux <= this.mAmbientDarkeningThreshold && nextAmbientLightDarkeningTransition <= j)) {
            this.mPreThresholdLux = this.mAmbientLux;
            setAmbientLux(this.mFastAmbientLux);
            if (this.mLoggingEnabled) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("updateAmbientLux: ");
                sb.append(this.mFastAmbientLux > this.mAmbientLux ? "Brightened" : "Darkened");
                sb.append(": mAmbientBrighteningThreshold=");
                sb.append(this.mAmbientBrighteningThreshold);
                sb.append(", mAmbientDarkeningThreshold=");
                sb.append(this.mAmbientDarkeningThreshold);
                sb.append(", mAmbientLightRingBuffer=");
                sb.append(this.mAmbientLightRingBuffer);
                sb.append(", mAmbientLux=");
                sb.append(this.mAmbientLux);
                android.util.Slog.d(TAG, sb.toString());
            }
            updateAutoBrightness(true, false);
            nextAmbientLightBrighteningTransition = nextAmbientLightBrighteningTransition(j);
            nextAmbientLightDarkeningTransition = nextAmbientLightDarkeningTransition(j);
        }
        long min = java.lang.Math.min(nextAmbientLightDarkeningTransition, nextAmbientLightBrighteningTransition);
        if (min <= j) {
            min = this.mNormalLightSensorRate + j;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "updateAmbientLux: Scheduling ambient lux update for " + min + android.util.TimeUtils.formatUptime(min));
        }
        this.mHandler.sendEmptyMessageAtTime(1, min);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAutoBrightness(boolean z, boolean z2) {
        if (!this.mAmbientLuxValid) {
            return;
        }
        float brightness = this.mCurrentBrightnessMapper.getBrightness(this.mAmbientLux, this.mForegroundAppPackageName, this.mForegroundAppCategory);
        this.mRawScreenAutoBrightness = brightness;
        float clampScreenBrightness = clampScreenBrightness(brightness);
        boolean floatEquals = com.android.internal.display.BrightnessSynchronizer.floatEquals(this.mScreenAutoBrightness, clampScreenBrightness(this.mScreenAutoBrightness));
        boolean z3 = !java.lang.Float.isNaN(this.mScreenAutoBrightness) && clampScreenBrightness > this.mScreenDarkeningThreshold && clampScreenBrightness < this.mScreenBrighteningThreshold;
        if (z3 && !z2 && floatEquals) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "ignoring newScreenAutoBrightness: " + this.mScreenDarkeningThreshold + " < " + clampScreenBrightness + " < " + this.mScreenBrighteningThreshold);
                return;
            }
            return;
        }
        if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(this.mScreenAutoBrightness, clampScreenBrightness)) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "updateAutoBrightness: mScreenAutoBrightness=" + this.mScreenAutoBrightness + ", newScreenAutoBrightness=" + clampScreenBrightness);
            }
            if (!z3) {
                this.mPreThresholdBrightness = this.mScreenAutoBrightness;
            }
            this.mScreenAutoBrightness = clampScreenBrightness;
            if (isInIdleMode()) {
                this.mScreenBrighteningThreshold = clampScreenBrightness(this.mScreenBrightnessThresholdsIdle.getBrighteningThreshold(clampScreenBrightness));
                this.mScreenDarkeningThreshold = clampScreenBrightness(this.mScreenBrightnessThresholdsIdle.getDarkeningThreshold(clampScreenBrightness));
            } else {
                this.mScreenBrighteningThreshold = clampScreenBrightness(this.mScreenBrightnessThresholds.getBrighteningThreshold(clampScreenBrightness));
                this.mScreenDarkeningThreshold = clampScreenBrightness(this.mScreenBrightnessThresholds.getDarkeningThreshold(clampScreenBrightness));
            }
            if (z) {
                this.mCallbacks.updateBrightness();
            }
        }
        if (this.mAutoBrightnessOneShot) {
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
        }
    }

    private float clampScreenBrightness(float f) {
        return android.util.MathUtils.constrain(f, java.lang.Math.min(this.mBrightnessRangeController.getCurrentBrightnessMin(), this.mBrightnessThrottler.getBrightnessCap()), java.lang.Math.min(this.mBrightnessRangeController.getCurrentBrightnessMax(), this.mBrightnessThrottler.getBrightnessCap()));
    }

    private void prepareBrightnessAdjustmentSample() {
        if (!this.mBrightnessAdjustmentSamplePending) {
            this.mBrightnessAdjustmentSamplePending = true;
            this.mBrightnessAdjustmentSampleOldLux = this.mAmbientLuxValid ? this.mAmbientLux : -1.0f;
            this.mBrightnessAdjustmentSampleOldBrightness = this.mScreenAutoBrightness;
        } else {
            this.mHandler.removeMessages(2);
        }
        this.mHandler.sendEmptyMessageDelayed(2, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
    }

    private void cancelBrightnessAdjustmentSample() {
        if (this.mBrightnessAdjustmentSamplePending) {
            this.mBrightnessAdjustmentSamplePending = false;
            this.mHandler.removeMessages(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectBrightnessAdjustmentSample() {
        if (this.mBrightnessAdjustmentSamplePending) {
            this.mBrightnessAdjustmentSamplePending = false;
            if (this.mAmbientLuxValid) {
                if (this.mScreenAutoBrightness >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mScreenAutoBrightness == -1.0f) {
                    if (this.mLoggingEnabled) {
                        android.util.Slog.d(TAG, "Auto-brightness adjustment changed by user: lux=" + this.mAmbientLux + ", brightness=" + this.mScreenAutoBrightness + ", ring=" + this.mAmbientLightRingBuffer);
                    }
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.AUTO_BRIGHTNESS_ADJ, java.lang.Float.valueOf(this.mBrightnessAdjustmentSampleOldLux), java.lang.Float.valueOf(this.mBrightnessAdjustmentSampleOldBrightness), java.lang.Float.valueOf(this.mAmbientLux), java.lang.Float.valueOf(this.mScreenAutoBrightness));
                }
            }
        }
    }

    private void registerForegroundAppUpdater() {
        try {
            this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
            updateForegroundApp();
        } catch (android.os.RemoteException e) {
            if (this.mLoggingEnabled) {
                android.util.Slog.e(TAG, "Failed to register foreground app updater: " + e);
            }
        }
    }

    private void unregisterForegroundAppUpdater() {
        try {
            this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
        } catch (android.os.RemoteException e) {
        }
        this.mForegroundAppPackageName = null;
        this.mForegroundAppCategory = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForegroundApp() {
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Attempting to update foreground app");
        }
        this.mInjector.getBackgroundThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.AutomaticBrightnessController.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    android.app.ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = com.android.server.display.AutomaticBrightnessController.this.mActivityTaskManager.getFocusedRootTaskInfo();
                    if (focusedRootTaskInfo == null || focusedRootTaskInfo.topActivity == null) {
                        return;
                    }
                    java.lang.String packageName = focusedRootTaskInfo.topActivity.getPackageName();
                    java.lang.String str = com.android.server.display.AutomaticBrightnessController.this.mForegroundAppPackageName;
                    if (str != null && str.equals(packageName)) {
                        return;
                    }
                    com.android.server.display.AutomaticBrightnessController.this.mPendingForegroundAppPackageName = packageName;
                    com.android.server.display.AutomaticBrightnessController.this.mPendingForegroundAppCategory = -1;
                    try {
                        android.content.pm.ApplicationInfo applicationInfo = com.android.server.display.AutomaticBrightnessController.this.mPackageManager.getApplicationInfo(packageName, 4194304);
                        com.android.server.display.AutomaticBrightnessController.this.mPendingForegroundAppCategory = applicationInfo.category;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    }
                    com.android.server.display.AutomaticBrightnessController.this.mHandler.sendEmptyMessage(5);
                } catch (android.os.RemoteException e2) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForegroundAppSync() {
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Updating foreground app: packageName=" + this.mPendingForegroundAppPackageName + ", category=" + this.mPendingForegroundAppCategory);
        }
        this.mForegroundAppPackageName = this.mPendingForegroundAppPackageName;
        this.mPendingForegroundAppPackageName = null;
        this.mForegroundAppCategory = this.mPendingForegroundAppCategory;
        this.mPendingForegroundAppCategory = -1;
        updateAutoBrightness(true, false);
    }

    private void switchModeAndShortTermModels(int i) {
        com.android.server.display.AutomaticBrightnessController.ShortTermModel shortTermModel = new com.android.server.display.AutomaticBrightnessController.ShortTermModel();
        shortTermModel.set(this.mCurrentBrightnessMapper.getUserLux(), this.mCurrentBrightnessMapper.getUserBrightness(), true);
        this.mHandler.removeMessages(7);
        this.mHandler.sendEmptyMessageAtTime(7, this.mClock.uptimeMillis() + this.mCurrentBrightnessMapper.getShortTermModelTimeout());
        android.util.Slog.i(TAG, "mPreviousShortTermModel: " + this.mPausedShortTermModel);
        this.mCurrentBrightnessMapper = this.mBrightnessMappingStrategyMap.get(i);
        if (this.mPausedShortTermModel != null) {
            if (!this.mPausedShortTermModel.maybeReset(this.mAmbientLux)) {
                setScreenBrightnessByUser(this.mPausedShortTermModel.mAnchor, this.mPausedShortTermModel.mBrightness);
            }
            this.mPausedShortTermModel.copyFrom(shortTermModel);
        }
        update();
    }

    void switchMode(int i) {
        if (!this.mBrightnessMappingStrategyMap.contains(i) || this.mCurrentBrightnessMapper.getMode() == i) {
            return;
        }
        android.util.Slog.i(TAG, "Switching to mode " + com.android.server.display.config.DisplayBrightnessMappingConfig.autoBrightnessModeToString(i));
        if (i == 1 || this.mCurrentBrightnessMapper.getMode() == 1) {
            switchModeAndShortTermModels(i);
        } else {
            resetShortTermModel();
            this.mCurrentBrightnessMapper = this.mBrightnessMappingStrategyMap.get(i);
        }
    }

    float getUserLux() {
        return this.mCurrentBrightnessMapper.getUserLux();
    }

    float getUserNits() {
        return convertToNits(this.mCurrentBrightnessMapper.getUserBrightness());
    }

    public float convertToNits(float f) {
        return this.mCurrentBrightnessMapper.convertToNits(f);
    }

    public float convertToAdjustedNits(float f) {
        return this.mCurrentBrightnessMapper.convertToAdjustedNits(f);
    }

    public float getBrightnessFromNits(float f) {
        return this.mCurrentBrightnessMapper.getBrightnessFromNits(f);
    }

    public void recalculateSplines(boolean z, float[] fArr) {
        this.mCurrentBrightnessMapper.recalculateSplines(z, fArr);
        resetShortTermModel();
        if (z) {
            setScreenBrightnessByUser(getAutomaticScreenBrightness());
        }
    }

    private class ShortTermModel {
        private float mAnchor;
        private float mBrightness;
        private boolean mIsValid;

        private ShortTermModel() {
            this.mAnchor = -1.0f;
            this.mBrightness = Float.NaN;
            this.mIsValid = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mAnchor = -1.0f;
            this.mBrightness = Float.NaN;
            this.mIsValid = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidate() {
            this.mIsValid = false;
            if (com.android.server.display.AutomaticBrightnessController.this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.AutomaticBrightnessController.TAG, "ShortTermModel: invalidate user data");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUserBrightness(float f, float f2) {
            this.mAnchor = f;
            this.mBrightness = f2;
            this.mIsValid = true;
            if (com.android.server.display.AutomaticBrightnessController.this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.AutomaticBrightnessController.TAG, "ShortTermModel: anchor=" + this.mAnchor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean maybeReset(float f) {
            if (!this.mIsValid && this.mAnchor != -1.0f) {
                if (com.android.server.display.AutomaticBrightnessController.this.mCurrentBrightnessMapper.shouldResetShortTermModel(f, this.mAnchor)) {
                    com.android.server.display.AutomaticBrightnessController.this.resetShortTermModel();
                } else {
                    this.mIsValid = true;
                }
                return this.mIsValid;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void set(float f, float f2, boolean z) {
            this.mAnchor = f;
            this.mBrightness = f2;
            this.mIsValid = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyFrom(com.android.server.display.AutomaticBrightnessController.ShortTermModel shortTermModel) {
            this.mAnchor = shortTermModel.mAnchor;
            this.mBrightness = shortTermModel.mBrightness;
            this.mIsValid = shortTermModel.mIsValid;
        }

        public java.lang.String toString() {
            return "mAnchor: " + this.mAnchor + "\n mBrightness: " + this.mBrightness + "\n mIsValid: " + this.mIsValid;
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println(this);
        }
    }

    private final class AutomaticBrightnessHandler extends android.os.Handler {
        public AutomaticBrightnessHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.display.AutomaticBrightnessController.this.updateAmbientLux();
                    break;
                case 2:
                    com.android.server.display.AutomaticBrightnessController.this.collectBrightnessAdjustmentSample();
                    break;
                case 3:
                    com.android.server.display.AutomaticBrightnessController.this.mShortTermModel.invalidate();
                    break;
                case 4:
                    com.android.server.display.AutomaticBrightnessController.this.updateForegroundApp();
                    break;
                case 5:
                    com.android.server.display.AutomaticBrightnessController.this.updateForegroundAppSync();
                    break;
                case 6:
                    com.android.server.display.AutomaticBrightnessController.this.updateAutoBrightness(true, false);
                    break;
                case 7:
                    com.android.server.display.AutomaticBrightnessController.this.mPausedShortTermModel.invalidate();
                    break;
            }
        }
    }

    class TaskStackListenerImpl extends android.app.TaskStackListener {
        TaskStackListenerImpl() {
        }

        public void onTaskStackChanged() {
            com.android.server.display.AutomaticBrightnessController.this.mHandler.sendEmptyMessage(4);
        }
    }

    private static final class AmbientLightRingBuffer {
        private static final float BUFFER_SLACK = 1.5f;
        private int mCapacity;
        com.android.server.display.AutomaticBrightnessController.Clock mClock;
        private int mCount;
        private int mEnd;
        private float[] mRingLux;
        private long[] mRingTime;
        private int mStart;

        public AmbientLightRingBuffer(long j, int i, com.android.server.display.AutomaticBrightnessController.Clock clock) {
            if (j <= 0) {
                throw new java.lang.IllegalArgumentException("lightSensorRate must be above 0");
            }
            this.mCapacity = (int) java.lang.Math.ceil((i * BUFFER_SLACK) / j);
            this.mRingLux = new float[this.mCapacity];
            this.mRingTime = new long[this.mCapacity];
            this.mClock = clock;
        }

        public float getLux(int i) {
            return this.mRingLux[offsetOf(i)];
        }

        public float[] getAllLuxValues() {
            float[] fArr = new float[this.mCount];
            if (this.mCount == 0) {
                return fArr;
            }
            if (this.mStart < this.mEnd) {
                java.lang.System.arraycopy(this.mRingLux, this.mStart, fArr, 0, this.mCount);
            } else {
                java.lang.System.arraycopy(this.mRingLux, this.mStart, fArr, 0, this.mCapacity - this.mStart);
                java.lang.System.arraycopy(this.mRingLux, 0, fArr, this.mCapacity - this.mStart, this.mEnd);
            }
            return fArr;
        }

        public long getTime(int i) {
            return this.mRingTime[offsetOf(i)];
        }

        public long[] getAllTimestamps() {
            long[] jArr = new long[this.mCount];
            if (this.mCount == 0) {
                return jArr;
            }
            if (this.mStart < this.mEnd) {
                java.lang.System.arraycopy(this.mRingTime, this.mStart, jArr, 0, this.mCount);
            } else {
                java.lang.System.arraycopy(this.mRingTime, this.mStart, jArr, 0, this.mCapacity - this.mStart);
                java.lang.System.arraycopy(this.mRingTime, 0, jArr, this.mCapacity - this.mStart, this.mEnd);
            }
            return jArr;
        }

        public void push(long j, float f) {
            int i = this.mEnd;
            if (this.mCount == this.mCapacity) {
                int i2 = this.mCapacity * 2;
                float[] fArr = new float[i2];
                long[] jArr = new long[i2];
                int i3 = this.mCapacity - this.mStart;
                java.lang.System.arraycopy(this.mRingLux, this.mStart, fArr, 0, i3);
                java.lang.System.arraycopy(this.mRingTime, this.mStart, jArr, 0, i3);
                if (this.mStart != 0) {
                    java.lang.System.arraycopy(this.mRingLux, 0, fArr, i3, this.mStart);
                    java.lang.System.arraycopy(this.mRingTime, 0, jArr, i3, this.mStart);
                }
                this.mRingLux = fArr;
                this.mRingTime = jArr;
                int i4 = this.mCapacity;
                this.mCapacity = i2;
                this.mStart = 0;
                i = i4;
            }
            this.mRingTime[i] = j;
            this.mRingLux[i] = f;
            this.mEnd = i + 1;
            if (this.mEnd == this.mCapacity) {
                this.mEnd = 0;
            }
            this.mCount++;
        }

        public void prune(long j) {
            if (this.mCount == 0) {
                return;
            }
            while (this.mCount > 1) {
                int i = this.mStart + 1;
                if (i >= this.mCapacity) {
                    i -= this.mCapacity;
                }
                if (this.mRingTime[i] > j) {
                    break;
                }
                this.mStart = i;
                this.mCount--;
            }
            if (this.mRingTime[this.mStart] < j) {
                this.mRingTime[this.mStart] = j;
            }
        }

        public int size() {
            return this.mCount;
        }

        public void clear() {
            this.mStart = 0;
            this.mEnd = 0;
            this.mCount = 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append('[');
            int i = 0;
            while (i < this.mCount) {
                int i2 = i + 1;
                long time = i2 < this.mCount ? getTime(i2) : this.mClock.uptimeMillis();
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(getLux(i));
                sb.append(" / ");
                sb.append(time - getTime(i));
                sb.append("ms");
                i = i2;
            }
            sb.append(']');
            return sb.toString();
        }

        private int offsetOf(int i) {
            if (i >= this.mCount || i < 0) {
                throw new java.lang.ArrayIndexOutOfBoundsException(i);
            }
            int i2 = i + this.mStart;
            if (i2 >= this.mCapacity) {
                return i2 - this.mCapacity;
            }
            return i2;
        }
    }

    public static class Injector {
        public android.os.Handler getBackgroundThreadHandler() {
            return com.android.internal.os.BackgroundThread.getHandler();
        }

        com.android.server.display.AutomaticBrightnessController.Clock createClock() {
            return new com.android.server.display.AutomaticBrightnessController.Clock() { // from class: com.android.server.display.AutomaticBrightnessController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.AutomaticBrightnessController.Clock
                public final long uptimeMillis() {
                    return android.os.SystemClock.uptimeMillis();
                }
            };
        }
    }
}
