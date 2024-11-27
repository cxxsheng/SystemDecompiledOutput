package com.android.server.display;

/* loaded from: classes.dex */
public class DisplayDeviceConfig {
    private static final int AMBIENT_LIGHT_LONG_HORIZON_MILLIS = 10000;
    private static final int AMBIENT_LIGHT_SHORT_HORIZON_MILLIS = 2000;

    @com.android.internal.annotations.VisibleForTesting
    static final float BRIGHTNESS_DEFAULT = 0.5f;
    private static final java.lang.String CONFIG_FILE_FORMAT = "display_%s.xml";
    private static final java.lang.String DEFAULT_CONFIG_FILE = "default.xml";
    private static final java.lang.String DEFAULT_CONFIG_FILE_WITH_UIMODE_FORMAT = "default_%s.xml";
    private static final int DEFAULT_HIGH_REFRESH_RATE = 0;
    public static final java.lang.String DEFAULT_ID = "default";
    public static final int DEFAULT_LOW_REFRESH_RATE = 60;
    private static final int DEFAULT_PEAK_REFRESH_RATE = 0;
    private static final int DEFAULT_REFRESH_RATE = 60;
    private static final int DEFAULT_REFRESH_RATE_IN_HBM = 0;
    private static final java.lang.String DISPLAY_CONFIG_DIR = "displayconfig";
    private static final java.lang.String ETC_DIR = "etc";

    @com.android.internal.annotations.VisibleForTesting
    static final float HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT = 0.5f;
    public static final float HIGH_BRIGHTNESS_MODE_UNSUPPORTED = Float.NaN;
    private static final int INTERPOLATION_DEFAULT = 0;
    private static final int INTERPOLATION_LINEAR = 1;
    private static final int INVALID_AUTO_BRIGHTNESS_LIGHT_DEBOUNCE = -1;
    private static final float INVALID_BRIGHTNESS_IN_CONFIG = -2.0f;
    private static final java.lang.String NO_SUFFIX_FORMAT = "%d";
    private static final java.lang.String PORT_SUFFIX_FORMAT = "port_%d";
    public static final java.lang.String QUIRK_CAN_SET_BRIGHTNESS_VIA_HWC = "canSetBrightnessViaHwc";
    private static final long STABLE_FLAG = 4611686018427387904L;
    private static final java.lang.String STABLE_ID_SUFFIX_FORMAT = "id_%d";
    private com.android.server.display.config.SensorData mAmbientLightSensor;
    private float[] mBacklight;
    private android.util.Spline mBacklightToBrightnessSpline;
    private android.util.Spline mBacklightToNitsSpline;
    private float[] mBrightness;
    private float mBrightnessCapForWearBedtimeMode;
    private android.util.Spline mBrightnessToBacklightSpline;
    private final android.content.Context mContext;
    private com.android.server.display.DensityMapping mDensityMapping;

    @android.annotation.Nullable
    private com.android.server.display.config.DisplayBrightnessMappingConfig mDisplayBrightnessMapping;
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;
    private com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData mHbmData;

    @android.annotation.Nullable
    private com.android.server.display.config.HdrBrightnessData mHdrBrightnessData;

    @android.annotation.Nullable
    private android.hardware.input.HostUsiVersion mHostUsiVersion;
    private int mInterpolationType;

    @android.annotation.Nullable
    private java.lang.String mName;
    private float[] mNits;
    private android.util.Spline mNitsToBacklightSpline;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData mPowerThrottlingConfigData;

    @android.annotation.Nullable
    private com.android.server.display.config.SensorData mProximitySensor;
    private java.util.List<java.lang.String> mQuirks;
    private float[] mRawBacklight;
    private float[] mRawNits;
    private com.android.server.display.config.SensorData mScreenOffBrightnessSensor;
    private int[] mScreenOffBrightnessSensorValueToLux;
    private android.util.Spline mSdrToHdrRatioSpline;

    @android.annotation.NonNull
    private com.android.server.display.config.SensorData mTempSensor;
    private static final java.lang.String TAG = "DisplayDeviceConfig";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static final float[] DEFAULT_BRIGHTNESS_THRESHOLDS = new float[0];
    private static final float[] DEFAULT_AMBIENT_THRESHOLD_LEVELS = {com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE};
    private static final float[] DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS = {100.0f};
    private static final float[] DEFAULT_AMBIENT_DARKENING_THRESHOLDS = {200.0f};
    private static final float[] DEFAULT_SCREEN_THRESHOLD_LEVELS = {com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE};
    private static final float[] DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS = {100.0f};
    private static final float[] DEFAULT_SCREEN_DARKENING_THRESHOLDS = {200.0f};
    private final java.util.List<android.hardware.display.DisplayManagerInternal.RefreshRateLimitation> mRefreshRateLimitations = new java.util.ArrayList(2);
    private float mBacklightMinimum = Float.NaN;
    private float mBacklightMaximum = Float.NaN;
    private float mBrightnessDefault = Float.NaN;
    private float mBrightnessRampFastDecrease = Float.NaN;
    private float mBrightnessRampFastIncrease = Float.NaN;
    private float mBrightnessRampSlowDecrease = Float.NaN;
    private float mBrightnessRampSlowIncrease = Float.NaN;
    private float mBrightnessRampSlowDecreaseIdle = Float.NaN;
    private float mBrightnessRampSlowIncreaseIdle = Float.NaN;
    private long mBrightnessRampDecreaseMaxMillis = 0;
    private long mBrightnessRampIncreaseMaxMillis = 0;
    private long mBrightnessRampDecreaseMaxIdleMillis = 0;
    private long mBrightnessRampIncreaseMaxIdleMillis = 0;
    private int mAmbientHorizonLong = 10000;
    private int mAmbientHorizonShort = 2000;
    private float mScreenBrighteningMinThreshold = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mScreenBrighteningMinThresholdIdle = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mScreenDarkeningMinThreshold = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mScreenDarkeningMinThresholdIdle = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mAmbientLuxBrighteningMinThreshold = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mAmbientLuxBrighteningMinThresholdIdle = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mAmbientLuxDarkeningMinThreshold = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mAmbientLuxDarkeningMinThresholdIdle = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float[] mScreenBrighteningLevels = DEFAULT_SCREEN_THRESHOLD_LEVELS;
    private float[] mScreenBrighteningPercentages = DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS;
    private float[] mScreenDarkeningLevels = DEFAULT_SCREEN_THRESHOLD_LEVELS;
    private float[] mScreenDarkeningPercentages = DEFAULT_SCREEN_DARKENING_THRESHOLDS;
    private float[] mScreenBrighteningLevelsIdle = DEFAULT_SCREEN_THRESHOLD_LEVELS;
    private float[] mScreenBrighteningPercentagesIdle = DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS;
    private float[] mScreenDarkeningLevelsIdle = DEFAULT_SCREEN_THRESHOLD_LEVELS;
    private float[] mScreenDarkeningPercentagesIdle = DEFAULT_SCREEN_DARKENING_THRESHOLDS;
    private float[] mAmbientBrighteningLevels = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
    private float[] mAmbientBrighteningPercentages = DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS;
    private float[] mAmbientDarkeningLevels = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
    private float[] mAmbientDarkeningPercentages = DEFAULT_AMBIENT_DARKENING_THRESHOLDS;
    private float[] mAmbientBrighteningLevelsIdle = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
    private float[] mAmbientBrighteningPercentagesIdle = DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS;
    private float[] mAmbientDarkeningLevelsIdle = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
    private float[] mAmbientDarkeningPercentagesIdle = DEFAULT_AMBIENT_DARKENING_THRESHOLDS;
    private boolean mIsHighBrightnessModeEnabled = false;
    private java.lang.String mLoadedFrom = null;
    private long mAutoBrightnessBrighteningLightDebounce = -1;
    private long mAutoBrightnessDarkeningLightDebounce = -1;
    private long mAutoBrightnessBrighteningLightDebounceIdle = -1;
    private long mAutoBrightnessDarkeningLightDebounceIdle = -1;
    private boolean mAutoBrightnessAvailable = false;
    private boolean mDdcAutoBrightnessAvailable = true;
    private int mDefaultPeakRefreshRate = 0;
    private int mDefaultRefreshRate = 60;
    private int mDefaultRefreshRateInHbmHdr = 0;
    private int mDefaultRefreshRateInHbmSunlight = 0;
    private int mDefaultHighBlockingZoneRefreshRate = 0;
    private int mDefaultLowBlockingZoneRefreshRate = 60;
    private final java.util.Map<java.lang.String, android.view.SurfaceControl.RefreshRateRange> mRefreshRateZoneProfiles = new java.util.HashMap();
    private float[] mLowDisplayBrightnessThresholds = DEFAULT_BRIGHTNESS_THRESHOLDS;
    private float[] mLowAmbientBrightnessThresholds = DEFAULT_BRIGHTNESS_THRESHOLDS;
    private float[] mHighDisplayBrightnessThresholds = DEFAULT_BRIGHTNESS_THRESHOLDS;
    private float[] mHighAmbientBrightnessThresholds = DEFAULT_BRIGHTNESS_THRESHOLDS;
    private java.lang.String mLowBlockingZoneThermalMapId = null;
    private java.lang.String mHighBlockingZoneThermalMapId = null;
    private final java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> mThermalBrightnessThrottlingDataMapByThrottlingId = new java.util.HashMap();
    private final java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData> mPowerThrottlingDataMapByThrottlingId = new java.util.HashMap();
    private final java.util.Map<java.lang.String, android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange>> mRefreshRateThrottlingMap = new java.util.HashMap();
    private final java.util.Map<com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType, java.util.Map<java.lang.Float, java.lang.Float>> mLuxThrottlingData = new java.util.HashMap();

    @com.android.internal.annotations.VisibleForTesting
    DisplayDeviceConfig(android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mContext = context;
        this.mFlags = displayManagerFlags;
    }

    public static com.android.server.display.DisplayDeviceConfig create(android.content.Context context, long j, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.DisplayDeviceConfig createWithoutDefaultValues = createWithoutDefaultValues(context, j, z, displayManagerFlags);
        createWithoutDefaultValues.copyUninitializedValuesFromSecondaryConfig(loadDefaultConfigurationXml(context));
        return createWithoutDefaultValues;
    }

    public static com.android.server.display.DisplayDeviceConfig create(android.content.Context context, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        if (z) {
            return getConfigFromGlobalXml(context, displayManagerFlags);
        }
        return getConfigFromPmValues(context, displayManagerFlags);
    }

    private static com.android.server.display.DisplayDeviceConfig createWithoutDefaultValues(android.content.Context context, long j, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.DisplayDeviceConfig loadConfigFromDirectory = loadConfigFromDirectory(context, android.os.Environment.getProductDirectory(), j, displayManagerFlags);
        if (loadConfigFromDirectory != null) {
            return loadConfigFromDirectory;
        }
        com.android.server.display.DisplayDeviceConfig loadConfigFromDirectory2 = loadConfigFromDirectory(context, android.os.Environment.getVendorDirectory(), j, displayManagerFlags);
        if (loadConfigFromDirectory2 != null) {
            return loadConfigFromDirectory2;
        }
        return create(context, z, displayManagerFlags);
    }

    private static com.android.server.display.config.DisplayConfiguration loadDefaultConfigurationXml(android.content.Context context) {
        java.io.BufferedInputStream bufferedInputStream;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.os.Environment.buildPath(android.os.Environment.getProductDirectory(), new java.lang.String[]{ETC_DIR, DISPLAY_CONFIG_DIR, DEFAULT_CONFIG_FILE}));
        arrayList.add(android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{ETC_DIR, DISPLAY_CONFIG_DIR, DEFAULT_CONFIG_FILE}));
        java.lang.String uiModeTypeString = android.content.res.Configuration.getUiModeTypeString(context.getResources().getInteger(android.R.integer.config_defaultRefreshRateInHbmHdr));
        if (uiModeTypeString != null) {
            arrayList.add(android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{ETC_DIR, DISPLAY_CONFIG_DIR, java.lang.String.format(DEFAULT_CONFIG_FILE_WITH_UIMODE_FORMAT, uiModeTypeString)}));
        }
        arrayList.add(android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{ETC_DIR, DISPLAY_CONFIG_DIR, DEFAULT_CONFIG_FILE}));
        java.io.File firstExistingFile = getFirstExistingFile(arrayList);
        com.android.server.display.config.DisplayConfiguration displayConfiguration = null;
        if (firstExistingFile == null) {
            return null;
        }
        try {
            bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(firstExistingFile));
        } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Encountered an error while reading/parsing display config file: " + firstExistingFile, e);
        }
        try {
            displayConfiguration = com.android.server.display.config.XmlParser.read(bufferedInputStream);
            if (displayConfiguration == null) {
                android.util.Slog.i(TAG, "Default DisplayDeviceConfig file is null");
            }
            bufferedInputStream.close();
            return displayConfiguration;
        } catch (java.lang.Throwable th) {
            try {
                bufferedInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static java.io.File getFirstExistingFile(java.util.Collection<java.io.File> collection) {
        for (java.io.File file : collection) {
            if (file.exists() && file.isFile()) {
                return file;
            }
        }
        return null;
    }

    private static com.android.server.display.DisplayDeviceConfig loadConfigFromDirectory(android.content.Context context, java.io.File file, long j, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.DisplayDeviceConfig configFromSuffix = getConfigFromSuffix(context, file, STABLE_ID_SUFFIX_FORMAT, j, displayManagerFlags);
        if (configFromSuffix != null) {
            return configFromSuffix;
        }
        com.android.server.display.DisplayDeviceConfig configFromSuffix2 = getConfigFromSuffix(context, file, NO_SUFFIX_FORMAT, j & (-4611686018427387905L), displayManagerFlags);
        if (configFromSuffix2 != null) {
            return configFromSuffix2;
        }
        return getConfigFromSuffix(context, file, PORT_SUFFIX_FORMAT, android.view.DisplayAddress.fromPhysicalDisplayId(j).getPort(), displayManagerFlags);
    }

    @android.annotation.Nullable
    public java.lang.String getName() {
        return this.mName;
    }

    public float[] getNits() {
        return this.mNits;
    }

    public float[] getBacklight() {
        return this.mBacklight;
    }

    public float getBacklightFromBrightness(float f) {
        return this.mBrightnessToBacklightSpline.interpolate(f);
    }

    public float getNitsFromBacklight(float f) {
        if (this.mBacklightToNitsSpline == null) {
            return -1.0f;
        }
        return this.mBacklightToNitsSpline.interpolate(java.lang.Math.max(f, this.mBacklightMinimum));
    }

    public boolean hasSdrToHdrRatioSpline() {
        return this.mSdrToHdrRatioSpline != null;
    }

    public float getHdrBrightnessFromSdr(float f, float f2) {
        if (this.mSdrToHdrRatioSpline == null) {
            return -1.0f;
        }
        float backlightFromBrightness = getBacklightFromBrightness(f);
        float nitsFromBacklight = getNitsFromBacklight(backlightFromBrightness);
        if (nitsFromBacklight == -1.0f) {
            return -1.0f;
        }
        float min = java.lang.Math.min(this.mSdrToHdrRatioSpline.interpolate(nitsFromBacklight), f2);
        float f3 = nitsFromBacklight * min;
        if (this.mNitsToBacklightSpline == null) {
            return -1.0f;
        }
        float max = java.lang.Math.max(this.mBacklightMinimum, java.lang.Math.min(this.mBacklightMaximum, this.mNitsToBacklightSpline.interpolate(f3)));
        float interpolate = this.mBacklightToBrightnessSpline.interpolate(max);
        if (DEBUG) {
            android.util.Slog.d(TAG, "getHdrBrightnessFromSdr: sdr brightness " + f + " backlight " + backlightFromBrightness + " nits " + nitsFromBacklight + " ratio " + min + " hdrNits " + f3 + " hdrBacklight " + max + " hdrBrightness " + interpolate);
        }
        return interpolate;
    }

    public float[] getBrightness() {
        return this.mBrightness;
    }

    public float getBrightnessDefault() {
        return this.mBrightnessDefault;
    }

    public float getBrightnessRampFastDecrease() {
        return this.mBrightnessRampFastDecrease;
    }

    public float getBrightnessRampFastIncrease() {
        return this.mBrightnessRampFastIncrease;
    }

    public float getBrightnessRampSlowDecrease() {
        return this.mBrightnessRampSlowDecrease;
    }

    public float getBrightnessRampSlowIncrease() {
        return this.mBrightnessRampSlowIncrease;
    }

    public float getBrightnessRampSlowDecreaseIdle() {
        return this.mBrightnessRampSlowDecreaseIdle;
    }

    public float getBrightnessRampSlowIncreaseIdle() {
        return this.mBrightnessRampSlowIncreaseIdle;
    }

    public long getBrightnessRampDecreaseMaxMillis() {
        return this.mBrightnessRampDecreaseMaxMillis;
    }

    public long getBrightnessRampIncreaseMaxMillis() {
        return this.mBrightnessRampIncreaseMaxMillis;
    }

    public long getBrightnessRampDecreaseMaxIdleMillis() {
        return this.mBrightnessRampDecreaseMaxIdleMillis;
    }

    public long getBrightnessRampIncreaseMaxIdleMillis() {
        return this.mBrightnessRampIncreaseMaxIdleMillis;
    }

    public int getAmbientHorizonLong() {
        return this.mAmbientHorizonLong;
    }

    public int getAmbientHorizonShort() {
        return this.mAmbientHorizonShort;
    }

    public float getScreenBrighteningMinThreshold() {
        return this.mScreenBrighteningMinThreshold;
    }

    public float getScreenDarkeningMinThreshold() {
        return this.mScreenDarkeningMinThreshold;
    }

    public float getScreenBrighteningMinThresholdIdle() {
        return this.mScreenBrighteningMinThresholdIdle;
    }

    public float getScreenDarkeningMinThresholdIdle() {
        return this.mScreenDarkeningMinThresholdIdle;
    }

    public float getAmbientLuxBrighteningMinThreshold() {
        return this.mAmbientLuxBrighteningMinThreshold;
    }

    public float getAmbientLuxDarkeningMinThreshold() {
        return this.mAmbientLuxDarkeningMinThreshold;
    }

    public float getAmbientLuxBrighteningMinThresholdIdle() {
        return this.mAmbientLuxBrighteningMinThresholdIdle;
    }

    public float getAmbientLuxDarkeningMinThresholdIdle() {
        return this.mAmbientLuxDarkeningMinThresholdIdle;
    }

    public float[] getScreenBrighteningLevels() {
        return this.mScreenBrighteningLevels;
    }

    public float[] getScreenBrighteningPercentages() {
        return this.mScreenBrighteningPercentages;
    }

    public float[] getScreenDarkeningLevels() {
        return this.mScreenDarkeningLevels;
    }

    public float[] getScreenDarkeningPercentages() {
        return this.mScreenDarkeningPercentages;
    }

    public float[] getAmbientBrighteningLevels() {
        return this.mAmbientBrighteningLevels;
    }

    public float[] getAmbientBrighteningPercentages() {
        return this.mAmbientBrighteningPercentages;
    }

    public float[] getAmbientDarkeningLevels() {
        return this.mAmbientDarkeningLevels;
    }

    public float[] getAmbientDarkeningPercentages() {
        return this.mAmbientDarkeningPercentages;
    }

    public float[] getScreenBrighteningLevelsIdle() {
        return this.mScreenBrighteningLevelsIdle;
    }

    public float[] getScreenBrighteningPercentagesIdle() {
        return this.mScreenBrighteningPercentagesIdle;
    }

    public float[] getScreenDarkeningLevelsIdle() {
        return this.mScreenDarkeningLevelsIdle;
    }

    public float[] getScreenDarkeningPercentagesIdle() {
        return this.mScreenDarkeningPercentagesIdle;
    }

    public float[] getAmbientBrighteningLevelsIdle() {
        return this.mAmbientBrighteningLevelsIdle;
    }

    public float[] getAmbientBrighteningPercentagesIdle() {
        return this.mAmbientBrighteningPercentagesIdle;
    }

    public float[] getAmbientDarkeningLevelsIdle() {
        return this.mAmbientDarkeningLevelsIdle;
    }

    public float[] getAmbientDarkeningPercentagesIdle() {
        return this.mAmbientDarkeningPercentagesIdle;
    }

    public com.android.server.display.config.SensorData getAmbientLightSensor() {
        return this.mAmbientLightSensor;
    }

    com.android.server.display.config.SensorData getScreenOffBrightnessSensor() {
        return this.mScreenOffBrightnessSensor;
    }

    @android.annotation.Nullable
    public com.android.server.display.config.SensorData getProximitySensor() {
        return this.mProximitySensor;
    }

    public com.android.server.display.config.SensorData getTempSensor() {
        return this.mTempSensor;
    }

    boolean isAutoBrightnessAvailable() {
        return this.mAutoBrightnessAvailable;
    }

    public boolean hasQuirk(java.lang.String str) {
        return this.mQuirks != null && this.mQuirks.contains(str);
    }

    public com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData getHighBrightnessModeData() {
        if (!this.mIsHighBrightnessModeEnabled || this.mHbmData == null) {
            return null;
        }
        com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = new com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData();
        this.mHbmData.copyTo(highBrightnessModeData);
        return highBrightnessModeData;
    }

    @android.annotation.Nullable
    public com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData getPowerThrottlingConfigData() {
        return this.mPowerThrottlingConfigData;
    }

    @android.annotation.NonNull
    public java.util.Map<com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType, java.util.Map<java.lang.Float, java.lang.Float>> getLuxThrottlingData() {
        return this.mLuxThrottlingData;
    }

    public java.util.List<android.hardware.display.DisplayManagerInternal.RefreshRateLimitation> getRefreshRateLimitations() {
        return this.mRefreshRateLimitations;
    }

    public com.android.server.display.DensityMapping getDensityMapping() {
        return this.mDensityMapping;
    }

    public java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> getThermalBrightnessThrottlingDataMapByThrottlingId() {
        return this.mThermalBrightnessThrottlingDataMapByThrottlingId;
    }

    @android.annotation.Nullable
    public android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> getThermalRefreshRateThrottlingData(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            str = "default";
        }
        return this.mRefreshRateThrottlingMap.get(str);
    }

    public java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData> getPowerThrottlingDataMapByThrottlingId() {
        return this.mPowerThrottlingDataMapByThrottlingId;
    }

    public long getAutoBrightnessDarkeningLightDebounce() {
        return this.mAutoBrightnessDarkeningLightDebounce;
    }

    public long getAutoBrightnessBrighteningLightDebounce() {
        return this.mAutoBrightnessBrighteningLightDebounce;
    }

    public long getAutoBrightnessDarkeningLightDebounceIdle() {
        return this.mAutoBrightnessDarkeningLightDebounceIdle;
    }

    public long getAutoBrightnessBrighteningLightDebounceIdle() {
        return this.mAutoBrightnessBrighteningLightDebounceIdle;
    }

    public float[] getAutoBrightnessBrighteningLevelsLux(int i, int i2) {
        if (this.mDisplayBrightnessMapping == null) {
            return null;
        }
        return this.mDisplayBrightnessMapping.getLuxArray(i, i2);
    }

    public float[] getAutoBrightnessBrighteningLevelsNits() {
        if (this.mDisplayBrightnessMapping == null) {
            return null;
        }
        return this.mDisplayBrightnessMapping.getNitsArray();
    }

    public float[] getAutoBrightnessBrighteningLevels(int i, int i2) {
        if (this.mDisplayBrightnessMapping == null) {
            return null;
        }
        return this.mDisplayBrightnessMapping.getBrightnessArray(i, i2);
    }

    public int getDefaultPeakRefreshRate() {
        return this.mDefaultPeakRefreshRate;
    }

    public int getDefaultRefreshRate() {
        return this.mDefaultRefreshRate;
    }

    public int getDefaultRefreshRateInHbmHdr() {
        return this.mDefaultRefreshRateInHbmHdr;
    }

    public int getDefaultRefreshRateInHbmSunlight() {
        return this.mDefaultRefreshRateInHbmSunlight;
    }

    public int getDefaultHighBlockingZoneRefreshRate() {
        return this.mDefaultHighBlockingZoneRefreshRate;
    }

    public int getDefaultLowBlockingZoneRefreshRate() {
        return this.mDefaultLowBlockingZoneRefreshRate;
    }

    @android.annotation.Nullable
    public com.android.server.display.config.HdrBrightnessData getHdrBrightnessData() {
        return this.mHdrBrightnessData;
    }

    @android.annotation.Nullable
    public android.view.SurfaceControl.RefreshRateRange getRefreshRange(@android.annotation.Nullable java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mRefreshRateZoneProfiles.get(str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.Map<java.lang.String, android.view.SurfaceControl.RefreshRateRange> getRefreshRangeProfiles() {
        return this.mRefreshRateZoneProfiles;
    }

    public float[] getLowDisplayBrightnessThresholds() {
        return this.mLowDisplayBrightnessThresholds;
    }

    public float[] getLowAmbientBrightnessThresholds() {
        return this.mLowAmbientBrightnessThresholds;
    }

    public android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> getLowBlockingZoneThermalMap() {
        return getThermalRefreshRateThrottlingData(this.mLowBlockingZoneThermalMapId);
    }

    public float[] getHighDisplayBrightnessThresholds() {
        return this.mHighDisplayBrightnessThresholds;
    }

    public float[] getHighAmbientBrightnessThresholds() {
        return this.mHighAmbientBrightnessThresholds;
    }

    public android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> getHighBlockingZoneThermalMap() {
        return getThermalRefreshRateThrottlingData(this.mHighBlockingZoneThermalMapId);
    }

    public int[] getScreenOffBrightnessSensorValueToLux() {
        return this.mScreenOffBrightnessSensorValueToLux;
    }

    @android.annotation.Nullable
    public android.hardware.input.HostUsiVersion getHostUsiVersion() {
        return this.mHostUsiVersion;
    }

    public float getBrightnessCapForWearBedtimeMode() {
        return this.mBrightnessCapForWearBedtimeMode;
    }

    public java.lang.String toString() {
        return "DisplayDeviceConfig{mLoadedFrom=" + this.mLoadedFrom + "\nmBacklight=" + java.util.Arrays.toString(this.mBacklight) + ", mNits=" + java.util.Arrays.toString(this.mNits) + ", mRawBacklight=" + java.util.Arrays.toString(this.mRawBacklight) + ", mRawNits=" + java.util.Arrays.toString(this.mRawNits) + ", mInterpolationType=" + this.mInterpolationType + "mBrightness=" + java.util.Arrays.toString(this.mBrightness) + "\nmBrightnessToBacklightSpline=" + this.mBrightnessToBacklightSpline + ", mBacklightToBrightnessSpline=" + this.mBacklightToBrightnessSpline + ", mNitsToBacklightSpline=" + this.mNitsToBacklightSpline + ", mBacklightMinimum=" + this.mBacklightMinimum + ", mBacklightMaximum=" + this.mBacklightMaximum + ", mBrightnessDefault=" + this.mBrightnessDefault + ", mQuirks=" + this.mQuirks + ", mIsHighBrightnessModeEnabled=" + this.mIsHighBrightnessModeEnabled + "\nmLuxThrottlingData=" + this.mLuxThrottlingData + ", mHbmData=" + this.mHbmData + ", mSdrToHdrRatioSpline=" + this.mSdrToHdrRatioSpline + ", mThermalBrightnessThrottlingDataMapByThrottlingId=" + this.mThermalBrightnessThrottlingDataMapByThrottlingId + "\n, mPowerThrottlingDataMapByThrottlingId=" + this.mPowerThrottlingDataMapByThrottlingId + "\nmBrightnessRampFastDecrease=" + this.mBrightnessRampFastDecrease + ", mBrightnessRampFastIncrease=" + this.mBrightnessRampFastIncrease + ", mBrightnessRampSlowDecrease=" + this.mBrightnessRampSlowDecrease + ", mBrightnessRampSlowIncrease=" + this.mBrightnessRampSlowIncrease + ", mBrightnessRampSlowDecreaseIdle=" + this.mBrightnessRampSlowDecreaseIdle + ", mBrightnessRampSlowIncreaseIdle=" + this.mBrightnessRampSlowIncreaseIdle + ", mBrightnessRampDecreaseMaxMillis=" + this.mBrightnessRampDecreaseMaxMillis + ", mBrightnessRampIncreaseMaxMillis=" + this.mBrightnessRampIncreaseMaxMillis + ", mBrightnessRampDecreaseMaxIdleMillis=" + this.mBrightnessRampDecreaseMaxIdleMillis + ", mBrightnessRampIncreaseMaxIdleMillis=" + this.mBrightnessRampIncreaseMaxIdleMillis + "\nmAmbientHorizonLong=" + this.mAmbientHorizonLong + ", mAmbientHorizonShort=" + this.mAmbientHorizonShort + "\nmScreenDarkeningMinThreshold=" + this.mScreenDarkeningMinThreshold + ", mScreenDarkeningMinThresholdIdle=" + this.mScreenDarkeningMinThresholdIdle + ", mScreenBrighteningMinThreshold=" + this.mScreenBrighteningMinThreshold + ", mScreenBrighteningMinThresholdIdle=" + this.mScreenBrighteningMinThresholdIdle + ", mAmbientLuxDarkeningMinThreshold=" + this.mAmbientLuxDarkeningMinThreshold + ", mAmbientLuxDarkeningMinThresholdIdle=" + this.mAmbientLuxDarkeningMinThresholdIdle + ", mAmbientLuxBrighteningMinThreshold=" + this.mAmbientLuxBrighteningMinThreshold + ", mAmbientLuxBrighteningMinThresholdIdle=" + this.mAmbientLuxBrighteningMinThresholdIdle + "\nmScreenBrighteningLevels=" + java.util.Arrays.toString(this.mScreenBrighteningLevels) + ", mScreenBrighteningPercentages=" + java.util.Arrays.toString(this.mScreenBrighteningPercentages) + ", mScreenDarkeningLevels=" + java.util.Arrays.toString(this.mScreenDarkeningLevels) + ", mScreenDarkeningPercentages=" + java.util.Arrays.toString(this.mScreenDarkeningPercentages) + ", mAmbientBrighteningLevels=" + java.util.Arrays.toString(this.mAmbientBrighteningLevels) + ", mAmbientBrighteningPercentages=" + java.util.Arrays.toString(this.mAmbientBrighteningPercentages) + ", mAmbientDarkeningLevels=" + java.util.Arrays.toString(this.mAmbientDarkeningLevels) + ", mAmbientDarkeningPercentages=" + java.util.Arrays.toString(this.mAmbientDarkeningPercentages) + "\nmAmbientBrighteningLevelsIdle=" + java.util.Arrays.toString(this.mAmbientBrighteningLevelsIdle) + ", mAmbientBrighteningPercentagesIdle=" + java.util.Arrays.toString(this.mAmbientBrighteningPercentagesIdle) + ", mAmbientDarkeningLevelsIdle=" + java.util.Arrays.toString(this.mAmbientDarkeningLevelsIdle) + ", mAmbientDarkeningPercentagesIdle=" + java.util.Arrays.toString(this.mAmbientDarkeningPercentagesIdle) + ", mScreenBrighteningLevelsIdle=" + java.util.Arrays.toString(this.mScreenBrighteningLevelsIdle) + ", mScreenBrighteningPercentagesIdle=" + java.util.Arrays.toString(this.mScreenBrighteningPercentagesIdle) + ", mScreenDarkeningLevelsIdle=" + java.util.Arrays.toString(this.mScreenDarkeningLevelsIdle) + ", mScreenDarkeningPercentagesIdle=" + java.util.Arrays.toString(this.mScreenDarkeningPercentagesIdle) + "\nmAmbientLightSensor=" + this.mAmbientLightSensor + ", mScreenOffBrightnessSensor=" + this.mScreenOffBrightnessSensor + ", mProximitySensor=" + this.mProximitySensor + ", mTempSensor=" + this.mTempSensor + ", mRefreshRateLimitations= " + java.util.Arrays.toString(this.mRefreshRateLimitations.toArray()) + ", mDensityMapping= " + this.mDensityMapping + ", mAutoBrightnessBrighteningLightDebounce= " + this.mAutoBrightnessBrighteningLightDebounce + ", mAutoBrightnessDarkeningLightDebounce= " + this.mAutoBrightnessDarkeningLightDebounce + ", mAutoBrightnessBrighteningLightDebounceIdle= " + this.mAutoBrightnessBrighteningLightDebounceIdle + ", mAutoBrightnessDarkeningLightDebounceIdle= " + this.mAutoBrightnessDarkeningLightDebounceIdle + ", mDisplayBrightnessMapping= " + this.mDisplayBrightnessMapping + ", mDdcAutoBrightnessAvailable= " + this.mDdcAutoBrightnessAvailable + ", mAutoBrightnessAvailable= " + this.mAutoBrightnessAvailable + "\nmDefaultLowBlockingZoneRefreshRate= " + this.mDefaultLowBlockingZoneRefreshRate + ", mDefaultHighBlockingZoneRefreshRate= " + this.mDefaultHighBlockingZoneRefreshRate + ", mDefaultPeakRefreshRate= " + this.mDefaultPeakRefreshRate + ", mDefaultRefreshRate= " + this.mDefaultRefreshRate + ", mRefreshRateZoneProfiles= " + this.mRefreshRateZoneProfiles + ", mDefaultRefreshRateInHbmHdr= " + this.mDefaultRefreshRateInHbmHdr + ", mDefaultRefreshRateInHbmSunlight= " + this.mDefaultRefreshRateInHbmSunlight + ", mRefreshRateThrottlingMap= " + this.mRefreshRateThrottlingMap + ", mLowBlockingZoneThermalMapId= " + this.mLowBlockingZoneThermalMapId + ", mHighBlockingZoneThermalMapId= " + this.mHighBlockingZoneThermalMapId + "\nmLowDisplayBrightnessThresholds= " + java.util.Arrays.toString(this.mLowDisplayBrightnessThresholds) + ", mLowAmbientBrightnessThresholds= " + java.util.Arrays.toString(this.mLowAmbientBrightnessThresholds) + ", mHighDisplayBrightnessThresholds= " + java.util.Arrays.toString(this.mHighDisplayBrightnessThresholds) + ", mHighAmbientBrightnessThresholds= " + java.util.Arrays.toString(this.mHighAmbientBrightnessThresholds) + "\nmScreenOffBrightnessSensorValueToLux= " + java.util.Arrays.toString(this.mScreenOffBrightnessSensorValueToLux) + "\nmUsiVersion= " + this.mHostUsiVersion + "\nmHdrBrightnessData= " + this.mHdrBrightnessData + "\nmBrightnessCapForWearBedtimeMode= " + this.mBrightnessCapForWearBedtimeMode + "}";
    }

    private static com.android.server.display.DisplayDeviceConfig getConfigFromSuffix(android.content.Context context, java.io.File file, java.lang.String str, long j, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        java.io.File buildPath = android.os.Environment.buildPath(file, new java.lang.String[]{ETC_DIR, DISPLAY_CONFIG_DIR, java.lang.String.format(java.util.Locale.ROOT, CONFIG_FILE_FORMAT, java.lang.String.format(java.util.Locale.ROOT, str, java.lang.Long.valueOf(j)))});
        com.android.server.display.DisplayDeviceConfig displayDeviceConfig = new com.android.server.display.DisplayDeviceConfig(context, displayManagerFlags);
        if (displayDeviceConfig.initFromFile(buildPath)) {
            return displayDeviceConfig;
        }
        return null;
    }

    private static com.android.server.display.DisplayDeviceConfig getConfigFromGlobalXml(android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.DisplayDeviceConfig displayDeviceConfig = new com.android.server.display.DisplayDeviceConfig(context, displayManagerFlags);
        displayDeviceConfig.initFromGlobalXml();
        return displayDeviceConfig;
    }

    private static com.android.server.display.DisplayDeviceConfig getConfigFromPmValues(android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        com.android.server.display.DisplayDeviceConfig displayDeviceConfig = new com.android.server.display.DisplayDeviceConfig(context, displayManagerFlags);
        displayDeviceConfig.initFromDefaultValues();
        return displayDeviceConfig;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean initFromFile(java.io.File file) {
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            android.util.Slog.e(TAG, "Display configuration is not a file: " + file + ", skipping");
            return false;
        }
        try {
            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
            try {
                com.android.server.display.config.DisplayConfiguration read = com.android.server.display.config.XmlParser.read(bufferedInputStream);
                if (read != null) {
                    loadName(read);
                    loadDensityMapping(read);
                    loadBrightnessDefaultFromDdcXml(read);
                    loadBrightnessConstraintsFromConfigXml();
                    loadBrightnessMap(read);
                    loadThermalThrottlingConfig(read);
                    loadPowerThrottlingConfigData(read);
                    loadHighBrightnessModeData(read);
                    loadLuxThrottling(read);
                    loadQuirks(read);
                    loadBrightnessRamps(read);
                    this.mAmbientLightSensor = com.android.server.display.config.SensorData.loadAmbientLightSensorConfig(read, this.mContext.getResources());
                    this.mScreenOffBrightnessSensor = com.android.server.display.config.SensorData.loadScreenOffBrightnessSensorConfig(read);
                    this.mProximitySensor = com.android.server.display.config.SensorData.loadProxSensorConfig(read);
                    this.mTempSensor = com.android.server.display.config.SensorData.loadTempSensorConfig(this.mFlags, read);
                    loadAmbientHorizonFromDdc(read);
                    loadBrightnessChangeThresholds(read);
                    loadAutoBrightnessConfigValues(read);
                    loadRefreshRateSetting(read);
                    loadScreenOffBrightnessSensorValueToLuxFromDdc(read);
                    loadUsiVersion(read);
                    this.mHdrBrightnessData = com.android.server.display.config.HdrBrightnessData.loadConfig(read);
                    loadBrightnessCapForWearBedtimeMode(read);
                } else {
                    android.util.Slog.w(TAG, "DisplayDeviceConfig file is null");
                }
                bufferedInputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Encountered an error while reading/parsing display config file: " + file, e);
        }
        this.mLoadedFrom = file.toString();
        return true;
    }

    private void initFromGlobalXml() {
        loadBrightnessDefaultFromConfigXml();
        loadBrightnessConstraintsFromConfigXml();
        loadBrightnessMapFromConfigXml();
        loadBrightnessRampsFromConfigXml();
        this.mAmbientLightSensor = com.android.server.display.config.SensorData.loadAmbientLightSensorConfig(this.mContext.getResources());
        this.mProximitySensor = com.android.server.display.config.SensorData.loadSensorUnspecifiedConfig();
        this.mTempSensor = com.android.server.display.config.SensorData.loadTempSensorUnspecifiedConfig();
        loadBrightnessChangeThresholdsFromXml();
        loadAutoBrightnessConfigsFromConfigXml();
        loadAutoBrightnessAvailableFromConfigXml();
        loadRefreshRateSetting(null);
        loadBrightnessCapForWearBedtimeModeFromConfigXml();
        this.mLoadedFrom = "<config.xml>";
    }

    private void initFromDefaultValues() {
        this.mLoadedFrom = "Static values";
        this.mBacklightMinimum = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mBacklightMaximum = 1.0f;
        this.mBrightnessDefault = 0.5f;
        this.mBrightnessRampFastDecrease = 1.0f;
        this.mBrightnessRampFastIncrease = 1.0f;
        this.mBrightnessRampSlowDecrease = 1.0f;
        this.mBrightnessRampSlowIncrease = 1.0f;
        this.mBrightnessRampSlowDecreaseIdle = 1.0f;
        this.mBrightnessRampSlowIncreaseIdle = 1.0f;
        this.mBrightnessRampDecreaseMaxMillis = 0L;
        this.mBrightnessRampIncreaseMaxMillis = 0L;
        this.mBrightnessRampDecreaseMaxIdleMillis = 0L;
        this.mBrightnessRampIncreaseMaxIdleMillis = 0L;
        setSimpleMappingStrategyValues();
        this.mAmbientLightSensor = com.android.server.display.config.SensorData.loadAmbientLightSensorConfig(this.mContext.getResources());
        this.mProximitySensor = com.android.server.display.config.SensorData.loadSensorUnspecifiedConfig();
        this.mTempSensor = com.android.server.display.config.SensorData.loadTempSensorUnspecifiedConfig();
        loadAutoBrightnessAvailableFromConfigXml();
    }

    private void copyUninitializedValuesFromSecondaryConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        if (displayConfiguration != null && this.mDensityMapping == null) {
            loadDensityMapping(displayConfiguration);
        }
    }

    private void loadName(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        this.mName = displayConfiguration.getName();
    }

    private void loadDensityMapping(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        if (displayConfiguration.getDensityMapping() == null) {
            return;
        }
        java.util.List<com.android.server.display.config.Density> density = displayConfiguration.getDensityMapping().getDensity();
        com.android.server.display.DensityMapping.Entry[] entryArr = new com.android.server.display.DensityMapping.Entry[density.size()];
        for (int i = 0; i < density.size(); i++) {
            com.android.server.display.config.Density density2 = density.get(i);
            entryArr[i] = new com.android.server.display.DensityMapping.Entry(density2.getWidth().intValue(), density2.getHeight().intValue(), density2.getDensity().intValue());
        }
        this.mDensityMapping = com.android.server.display.DensityMapping.createByOwning(entryArr);
    }

    private void loadBrightnessDefaultFromDdcXml(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        if (displayConfiguration != null) {
            java.math.BigDecimal screenBrightnessDefault = displayConfiguration.getScreenBrightnessDefault();
            if (screenBrightnessDefault != null) {
                this.mBrightnessDefault = screenBrightnessDefault.floatValue();
            } else {
                loadBrightnessDefaultFromConfigXml();
            }
        }
    }

    private void loadBrightnessDefaultFromConfigXml() {
        float f = this.mContext.getResources().getFloat(android.R.dimen.config_pictureInPictureMinAspectRatio);
        if (f == INVALID_BRIGHTNESS_IN_CONFIG) {
            this.mBrightnessDefault = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_nb_iot_inactivity_timeout_millis));
        } else {
            this.mBrightnessDefault = f;
        }
    }

    private void loadBrightnessConstraintsFromConfigXml() {
        float f = this.mContext.getResources().getFloat(android.R.dimen.config_preferredHyphenationFrequency);
        float f2 = this.mContext.getResources().getFloat(android.R.dimen.config_prefDialogWidth);
        if (f == INVALID_BRIGHTNESS_IN_CONFIG || f2 == INVALID_BRIGHTNESS_IN_CONFIG) {
            this.mBacklightMinimum = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_stay_at_listening_from_sending_millis));
            this.mBacklightMaximum = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_satellite_stay_at_listening_from_receiving_millis));
        } else {
            this.mBacklightMinimum = f;
            this.mBacklightMaximum = f2;
        }
    }

    private void loadBrightnessMap(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.NitsMap screenBrightnessMap = displayConfiguration.getScreenBrightnessMap();
        if (screenBrightnessMap == null) {
            loadBrightnessMapFromConfigXml();
            return;
        }
        java.util.List<com.android.server.display.config.Point> point = screenBrightnessMap.getPoint();
        int size = point.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        this.mInterpolationType = convertInterpolationType(screenBrightnessMap.getInterpolation());
        int i = 0;
        for (com.android.server.display.config.Point point2 : point) {
            fArr[i] = point2.getNits().floatValue();
            fArr2[i] = point2.getValue().floatValue();
            if (i > 0) {
                int i2 = i - 1;
                if (fArr[i] < fArr[i2]) {
                    android.util.Slog.e(TAG, "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Nits: " + fArr[i] + " < " + fArr[i2]);
                    return;
                }
                if (fArr2[i] < fArr2[i2]) {
                    android.util.Slog.e(TAG, "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Value: " + fArr2[i] + " < " + fArr2[i2]);
                    return;
                }
            }
            i++;
        }
        this.mRawNits = fArr;
        this.mRawBacklight = fArr2;
        constrainNitsAndBacklightArrays();
    }

    private android.util.Spline loadSdrHdrRatioMap(com.android.server.display.config.HighBrightnessMode highBrightnessMode) {
        java.util.List<com.android.server.display.config.SdrHdrRatioPoint> point;
        int size;
        com.android.server.display.config.SdrHdrRatioMap sdrHdrRatioMap_all = highBrightnessMode.getSdrHdrRatioMap_all();
        if (sdrHdrRatioMap_all == null || (size = (point = sdrHdrRatioMap_all.getPoint()).size()) == 0) {
            return null;
        }
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        int i = 0;
        for (com.android.server.display.config.SdrHdrRatioPoint sdrHdrRatioPoint : point) {
            fArr[i] = sdrHdrRatioPoint.getSdrNits().floatValue();
            if (i > 0) {
                int i2 = i - 1;
                if (fArr[i] < fArr[i2]) {
                    android.util.Slog.e(TAG, "sdrHdrRatioMap must be non-decreasing, ignoring rest  of configuration. nits: " + fArr[i] + " < " + fArr[i2]);
                    return null;
                }
            }
            fArr2[i] = sdrHdrRatioPoint.getHdrRatio().floatValue();
            i++;
        }
        return android.util.Spline.createSpline(fArr, fArr2);
    }

    private void loadThermalThrottlingConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.ThermalThrottling thermalThrottling = displayConfiguration.getThermalThrottling();
        if (thermalThrottling == null) {
            android.util.Slog.i(TAG, "No thermal throttling config found");
        } else {
            loadThermalBrightnessThrottlingMaps(thermalThrottling);
            loadThermalRefreshRateThrottlingMap(thermalThrottling);
        }
    }

    private void loadThermalBrightnessThrottlingMaps(com.android.server.display.config.ThermalThrottling thermalThrottling) {
        boolean z;
        java.util.List<com.android.server.display.config.BrightnessThrottlingMap> brightnessThrottlingMap = thermalThrottling.getBrightnessThrottlingMap();
        if (brightnessThrottlingMap == null || brightnessThrottlingMap.isEmpty()) {
            android.util.Slog.i(TAG, "No brightness throttling map found");
            return;
        }
        for (com.android.server.display.config.BrightnessThrottlingMap brightnessThrottlingMap2 : brightnessThrottlingMap) {
            java.util.List<com.android.server.display.config.BrightnessThrottlingPoint> brightnessThrottlingPoint = brightnessThrottlingMap2.getBrightnessThrottlingPoint();
            java.util.ArrayList arrayList = new java.util.ArrayList(brightnessThrottlingPoint.size());
            java.util.Iterator<com.android.server.display.config.BrightnessThrottlingPoint> it = brightnessThrottlingPoint.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                com.android.server.display.config.BrightnessThrottlingPoint next = it.next();
                com.android.server.display.config.ThermalStatus thermalStatus = next.getThermalStatus();
                if (!thermalStatusIsValid(thermalStatus)) {
                    z = true;
                    break;
                }
                arrayList.add(new com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(convertThermalStatus(thermalStatus), next.getBrightness().floatValue()));
            }
            if (!z) {
                java.lang.String id = brightnessThrottlingMap2.getId() == null ? "default" : brightnessThrottlingMap2.getId();
                if (this.mThermalBrightnessThrottlingDataMapByThrottlingId.containsKey(id)) {
                    throw new java.lang.RuntimeException("Brightness throttling data with ID " + id + " already exists");
                }
                this.mThermalBrightnessThrottlingDataMapByThrottlingId.put(id, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.create(arrayList));
            }
        }
    }

    private void loadThermalRefreshRateThrottlingMap(com.android.server.display.config.ThermalThrottling thermalThrottling) {
        java.util.List<com.android.server.display.config.RefreshRateThrottlingMap> refreshRateThrottlingMap = thermalThrottling.getRefreshRateThrottlingMap();
        if (refreshRateThrottlingMap == null || refreshRateThrottlingMap.isEmpty()) {
            android.util.Slog.w(TAG, "RefreshRateThrottling: map not found");
            return;
        }
        for (com.android.server.display.config.RefreshRateThrottlingMap refreshRateThrottlingMap2 : refreshRateThrottlingMap) {
            java.util.List<com.android.server.display.config.RefreshRateThrottlingPoint> refreshRateThrottlingPoint = refreshRateThrottlingMap2.getRefreshRateThrottlingPoint();
            java.lang.String id = refreshRateThrottlingMap2.getId() == null ? "default" : refreshRateThrottlingMap2.getId();
            if (refreshRateThrottlingPoint == null || refreshRateThrottlingPoint.isEmpty()) {
                android.util.Slog.w(TAG, "RefreshRateThrottling: points not found for mapId=" + id);
            } else if (this.mRefreshRateThrottlingMap.containsKey(id)) {
                android.util.Slog.wtf(TAG, "RefreshRateThrottling: map already exists, mapId=" + id);
            } else {
                android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray = new android.util.SparseArray<>();
                for (com.android.server.display.config.RefreshRateThrottlingPoint refreshRateThrottlingPoint2 : refreshRateThrottlingPoint) {
                    com.android.server.display.config.ThermalStatus thermalStatus = refreshRateThrottlingPoint2.getThermalStatus();
                    if (!thermalStatusIsValid(thermalStatus)) {
                        android.util.Slog.wtf(TAG, "RefreshRateThrottling: Invalid thermalStatus=" + thermalStatus.getRawName() + ",mapId=" + id);
                    } else {
                        int convertThermalStatus = convertThermalStatus(thermalStatus);
                        if (sparseArray.contains(convertThermalStatus)) {
                            android.util.Slog.wtf(TAG, "RefreshRateThrottling: thermalStatus=" + thermalStatus.getRawName() + " is already in the map, mapId=" + id);
                        } else {
                            sparseArray.put(convertThermalStatus, new android.view.SurfaceControl.RefreshRateRange(refreshRateThrottlingPoint2.getRefreshRateRange().getMinimum().floatValue(), refreshRateThrottlingPoint2.getRefreshRateRange().getMaximum().floatValue()));
                        }
                    }
                }
                if (sparseArray.size() == 0) {
                    android.util.Slog.w(TAG, "RefreshRateThrottling: no valid throttling points found for map, mapId=" + id);
                } else {
                    this.mRefreshRateThrottlingMap.put(id, sparseArray);
                }
            }
        }
    }

    private boolean loadPowerThrottlingMaps(com.android.server.display.config.PowerThrottlingConfig powerThrottlingConfig) {
        java.util.List<com.android.server.display.config.PowerThrottlingMap> powerThrottlingMap = powerThrottlingConfig.getPowerThrottlingMap();
        if (powerThrottlingMap == null || powerThrottlingMap.isEmpty()) {
            android.util.Slog.i(TAG, "No power throttling map found");
            return false;
        }
        java.util.Iterator<com.android.server.display.config.PowerThrottlingMap> it = powerThrottlingMap.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                return true;
            }
            com.android.server.display.config.PowerThrottlingMap next = it.next();
            java.util.List<com.android.server.display.config.PowerThrottlingPoint> powerThrottlingPoint = next.getPowerThrottlingPoint();
            java.util.ArrayList arrayList = new java.util.ArrayList(powerThrottlingPoint.size());
            java.util.Iterator<com.android.server.display.config.PowerThrottlingPoint> it2 = powerThrottlingPoint.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                com.android.server.display.config.PowerThrottlingPoint next2 = it2.next();
                com.android.server.display.config.ThermalStatus thermalStatus = next2.getThermalStatus();
                if (!thermalStatusIsValid(thermalStatus)) {
                    break;
                }
                arrayList.add(new com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel(convertThermalStatus(thermalStatus), next2.getPowerQuotaMilliWatts().floatValue()));
            }
            if (!z) {
                java.lang.String id = next.getId() == null ? "default" : next.getId();
                if (this.mPowerThrottlingDataMapByThrottlingId.containsKey(id)) {
                    throw new java.lang.RuntimeException("Power throttling data with ID " + id + " already exists");
                }
                this.mPowerThrottlingDataMapByThrottlingId.put(id, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.create(arrayList));
            }
        }
    }

    private void loadPowerThrottlingConfigData(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.PowerThrottlingConfig powerThrottlingConfig = displayConfiguration.getPowerThrottlingConfig();
        if (powerThrottlingConfig == null || !loadPowerThrottlingMaps(powerThrottlingConfig)) {
            return;
        }
        this.mPowerThrottlingConfigData = new com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData(powerThrottlingConfig.getBrightnessLowestCapAllowed().floatValue(), powerThrottlingConfig.getPollingWindowMillis().intValue());
    }

    private void loadRefreshRateSetting(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.BlockingZoneConfig lowerBlockingZoneConfigs;
        com.android.server.display.config.BlockingZoneConfig blockingZoneConfig = null;
        com.android.server.display.config.RefreshRateConfigs refreshRate = displayConfiguration == null ? null : displayConfiguration.getRefreshRate();
        if (refreshRate == null) {
            lowerBlockingZoneConfigs = null;
        } else {
            lowerBlockingZoneConfigs = refreshRate.getLowerBlockingZoneConfigs();
        }
        if (refreshRate != null) {
            blockingZoneConfig = refreshRate.getHigherBlockingZoneConfigs();
        }
        loadPeakDefaultRefreshRate(refreshRate);
        loadDefaultRefreshRate(refreshRate);
        loadDefaultRefreshRateInHbm(refreshRate);
        loadLowerRefreshRateBlockingZones(lowerBlockingZoneConfigs);
        loadHigherRefreshRateBlockingZones(blockingZoneConfig);
        loadRefreshRateZoneProfiles(refreshRate);
    }

    private void loadPeakDefaultRefreshRate(com.android.server.display.config.RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null || refreshRateConfigs.getDefaultPeakRefreshRate() == null) {
            this.mDefaultPeakRefreshRate = this.mContext.getResources().getInteger(android.R.integer.config_defaultNotificationLedOn);
        } else {
            this.mDefaultPeakRefreshRate = refreshRateConfigs.getDefaultPeakRefreshRate().intValue();
        }
    }

    private void loadDefaultRefreshRate(com.android.server.display.config.RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null || refreshRateConfigs.getDefaultRefreshRate() == null) {
            this.mDefaultRefreshRate = this.mContext.getResources().getInteger(android.R.integer.config_defaultPictureInPictureGravity);
        } else {
            this.mDefaultRefreshRate = refreshRateConfigs.getDefaultRefreshRate().intValue();
        }
    }

    private void loadRefreshRateZoneProfiles(com.android.server.display.config.RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null || refreshRateConfigs.getRefreshRateZoneProfiles() == null) {
            return;
        }
        for (com.android.server.display.config.RefreshRateZone refreshRateZone : refreshRateConfigs.getRefreshRateZoneProfiles().getRefreshRateZoneProfile()) {
            com.android.server.display.config.RefreshRateRange refreshRateRange = refreshRateZone.getRefreshRateRange();
            this.mRefreshRateZoneProfiles.put(refreshRateZone.getId(), new android.view.SurfaceControl.RefreshRateRange(refreshRateRange.getMinimum().floatValue(), refreshRateRange.getMaximum().floatValue()));
        }
    }

    private void loadDefaultRefreshRateInHbm(com.android.server.display.config.RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs != null && refreshRateConfigs.getDefaultRefreshRateInHbmHdr() != null) {
            this.mDefaultRefreshRateInHbmHdr = refreshRateConfigs.getDefaultRefreshRateInHbmHdr().intValue();
        } else {
            this.mDefaultRefreshRateInHbmHdr = this.mContext.getResources().getInteger(android.R.integer.config_defaultPowerStatsThrottlePeriodCpu);
        }
        if (refreshRateConfigs != null && refreshRateConfigs.getDefaultRefreshRateInHbmSunlight() != null) {
            this.mDefaultRefreshRateInHbmSunlight = refreshRateConfigs.getDefaultRefreshRateInHbmSunlight().intValue();
        } else {
            this.mDefaultRefreshRateInHbmSunlight = this.mContext.getResources().getInteger(android.R.integer.config_defaultPowerStatsThrottlePeriodMobileRadio);
        }
    }

    private void loadLowerRefreshRateBlockingZones(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig != null) {
            this.mLowBlockingZoneThermalMapId = blockingZoneConfig.getRefreshRateThermalThrottlingId();
        }
        loadLowerBlockingZoneDefaultRefreshRate(blockingZoneConfig);
        loadLowerBrightnessThresholds(blockingZoneConfig);
    }

    private void loadHigherRefreshRateBlockingZones(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig != null) {
            this.mHighBlockingZoneThermalMapId = blockingZoneConfig.getRefreshRateThermalThrottlingId();
        }
        loadHigherBlockingZoneDefaultRefreshRate(blockingZoneConfig);
        loadHigherBrightnessThresholds(blockingZoneConfig);
    }

    private void loadHigherBlockingZoneDefaultRefreshRate(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            this.mDefaultHighBlockingZoneRefreshRate = this.mContext.getResources().getInteger(android.R.integer.config_extraFreeKbytesAbsolute);
        } else {
            this.mDefaultHighBlockingZoneRefreshRate = blockingZoneConfig.getDefaultRefreshRate().intValue();
        }
    }

    private void loadLowerBlockingZoneDefaultRefreshRate(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            this.mDefaultLowBlockingZoneRefreshRate = this.mContext.getResources().getInteger(android.R.integer.config_defaultPowerStatsThrottlePeriodWifi);
        } else {
            this.mDefaultLowBlockingZoneRefreshRate = blockingZoneConfig.getDefaultRefreshRate().intValue();
        }
    }

    private void loadLowerBrightnessThresholds(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            int[] intArray = this.mContext.getResources().getIntArray(android.R.array.config_brightnessThresholdsOfPeakRefreshRate);
            int[] intArray2 = this.mContext.getResources().getIntArray(android.R.array.config_ambientThresholdsOfPeakRefreshRate);
            if (intArray == null || intArray2 == null || intArray.length != intArray2.length) {
                throw new java.lang.RuntimeException("display low brightness threshold array and ambient brightness threshold array have different length: lowDisplayBrightnessThresholdsInt=" + java.util.Arrays.toString(intArray) + ", lowAmbientBrightnessThresholdsInt=" + java.util.Arrays.toString(intArray2));
            }
            this.mLowDisplayBrightnessThresholds = com.android.server.display.utils.DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(intArray);
            this.mLowAmbientBrightnessThresholds = com.android.server.display.utils.DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(intArray2);
            return;
        }
        java.util.List<com.android.server.display.config.DisplayBrightnessPoint> displayBrightnessPoint = blockingZoneConfig.getBlockingZoneThreshold().getDisplayBrightnessPoint();
        int size = displayBrightnessPoint.size();
        this.mLowDisplayBrightnessThresholds = new float[size];
        this.mLowAmbientBrightnessThresholds = new float[size];
        for (int i = 0; i < size; i++) {
            float floatValue = displayBrightnessPoint.get(i).getNits().floatValue();
            if (floatValue < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mLowDisplayBrightnessThresholds[i] = floatValue;
            } else {
                this.mLowDisplayBrightnessThresholds[i] = this.mBacklightToBrightnessSpline.interpolate(this.mNitsToBacklightSpline.interpolate(floatValue));
            }
            this.mLowAmbientBrightnessThresholds[i] = displayBrightnessPoint.get(i).getLux().floatValue();
        }
    }

    private void loadHigherBrightnessThresholds(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            int[] intArray = this.mContext.getResources().getIntArray(android.R.array.config_healthConnectRestoreKnownSigners);
            int[] intArray2 = this.mContext.getResources().getIntArray(android.R.array.config_healthConnectMigrationKnownSigners);
            if (intArray == null || intArray2 == null || intArray.length != intArray2.length) {
                throw new java.lang.RuntimeException("display high brightness threshold array and ambient brightness threshold array have different length: highDisplayBrightnessThresholdsInt=" + java.util.Arrays.toString(intArray) + ", highAmbientBrightnessThresholdsInt=" + java.util.Arrays.toString(intArray2));
            }
            this.mHighDisplayBrightnessThresholds = com.android.server.display.utils.DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(intArray);
            this.mHighAmbientBrightnessThresholds = com.android.server.display.utils.DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(intArray2);
            return;
        }
        java.util.List<com.android.server.display.config.DisplayBrightnessPoint> displayBrightnessPoint = blockingZoneConfig.getBlockingZoneThreshold().getDisplayBrightnessPoint();
        int size = displayBrightnessPoint.size();
        this.mHighDisplayBrightnessThresholds = new float[size];
        this.mHighAmbientBrightnessThresholds = new float[size];
        for (int i = 0; i < size; i++) {
            float floatValue = displayBrightnessPoint.get(i).getNits().floatValue();
            if (floatValue < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mHighDisplayBrightnessThresholds[i] = floatValue;
            } else {
                this.mHighDisplayBrightnessThresholds[i] = this.mBacklightToBrightnessSpline.interpolate(this.mNitsToBacklightSpline.interpolate(floatValue));
            }
            this.mHighAmbientBrightnessThresholds[i] = displayBrightnessPoint.get(i).getLux().floatValue();
        }
    }

    private void loadAutoBrightnessConfigValues(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.AutoBrightness autoBrightness = displayConfiguration.getAutoBrightness();
        loadAutoBrightnessBrighteningLightDebounce(autoBrightness);
        loadAutoBrightnessDarkeningLightDebounce(autoBrightness);
        loadAutoBrightnessBrighteningLightDebounceIdle(autoBrightness);
        loadAutoBrightnessDarkeningLightDebounceIdle(autoBrightness);
        this.mDisplayBrightnessMapping = new com.android.server.display.config.DisplayBrightnessMappingConfig(this.mContext, this.mFlags, autoBrightness, this.mBacklightToBrightnessSpline);
        loadEnableAutoBrightness(autoBrightness);
    }

    private void loadAutoBrightnessBrighteningLightDebounce(com.android.server.display.config.AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getBrighteningLightDebounceMillis() == null) {
            this.mAutoBrightnessBrighteningLightDebounce = this.mContext.getResources().getInteger(android.R.integer.config_autoBrightnessBrighteningLightDebounce);
        } else {
            this.mAutoBrightnessBrighteningLightDebounce = autoBrightness.getBrighteningLightDebounceMillis().intValue();
        }
    }

    private void loadAutoBrightnessDarkeningLightDebounce(com.android.server.display.config.AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getDarkeningLightDebounceMillis() == null) {
            this.mAutoBrightnessDarkeningLightDebounce = this.mContext.getResources().getInteger(android.R.integer.config_autoBrightnessDarkeningLightDebounce);
        } else {
            this.mAutoBrightnessDarkeningLightDebounce = autoBrightness.getDarkeningLightDebounceMillis().intValue();
        }
    }

    private void loadAutoBrightnessBrighteningLightDebounceIdle(com.android.server.display.config.AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getBrighteningLightDebounceIdleMillis() == null) {
            this.mAutoBrightnessBrighteningLightDebounceIdle = this.mAutoBrightnessBrighteningLightDebounce;
        } else {
            this.mAutoBrightnessBrighteningLightDebounceIdle = autoBrightness.getBrighteningLightDebounceIdleMillis().intValue();
        }
    }

    private void loadAutoBrightnessDarkeningLightDebounceIdle(com.android.server.display.config.AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getDarkeningLightDebounceIdleMillis() == null) {
            this.mAutoBrightnessDarkeningLightDebounceIdle = this.mAutoBrightnessDarkeningLightDebounce;
        } else {
            this.mAutoBrightnessDarkeningLightDebounceIdle = autoBrightness.getDarkeningLightDebounceIdleMillis().intValue();
        }
    }

    private void loadAutoBrightnessAvailableFromConfigXml() {
        this.mAutoBrightnessAvailable = this.mContext.getResources().getBoolean(android.R.bool.config_automatic_brightness_available);
    }

    private void loadBrightnessMapFromConfigXml() {
        android.content.res.Resources resources = this.mContext.getResources();
        float[] floatArray = com.android.server.display.BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(android.R.array.config_satellite_providers));
        int[] intArray = resources.getIntArray(android.R.array.config_safeModeEnabledVibePattern);
        int length = intArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < intArray.length; i++) {
            fArr[i] = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(intArray[i]);
        }
        if (length == 0 || floatArray.length == 0) {
            setSimpleMappingStrategyValues();
            return;
        }
        this.mRawNits = floatArray;
        this.mRawBacklight = fArr;
        constrainNitsAndBacklightArrays();
    }

    private void setSimpleMappingStrategyValues() {
        this.mNits = null;
        this.mBacklight = null;
        float[] fArr = {com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f};
        this.mBrightnessToBacklightSpline = android.util.Spline.createSpline(fArr, fArr);
        this.mBacklightToBrightnessSpline = android.util.Spline.createSpline(fArr, fArr);
    }

    private void constrainNitsAndBacklightArrays() {
        float f;
        float f2;
        if (this.mRawBacklight[0] > this.mBacklightMinimum || this.mRawBacklight[this.mRawBacklight.length - 1] < this.mBacklightMaximum || this.mBacklightMinimum > this.mBacklightMaximum) {
            throw new java.lang.IllegalStateException("Min or max values are invalid; raw min=" + this.mRawBacklight[0] + "; raw max=" + this.mRawBacklight[this.mRawBacklight.length - 1] + "; backlight min=" + this.mBacklightMinimum + "; backlight max=" + this.mBacklightMaximum);
        }
        float[] fArr = new float[this.mRawBacklight.length];
        float[] fArr2 = new float[this.mRawBacklight.length];
        int i = 0;
        while (true) {
            if (i >= this.mRawBacklight.length - 1) {
                i = 0;
                break;
            }
            int i2 = i + 1;
            if (this.mRawBacklight[i2] > this.mBacklightMinimum) {
                break;
            } else {
                i = i2;
            }
        }
        boolean z = false;
        int i3 = 0;
        int i4 = i;
        while (i4 < this.mRawBacklight.length && !z) {
            i3 = i4 - i;
            z = this.mRawBacklight[i4] >= this.mBacklightMaximum || i4 >= this.mRawBacklight.length - 1;
            if (i3 == 0) {
                f = android.util.MathUtils.max(this.mRawBacklight[i4], this.mBacklightMinimum);
                f2 = rawBacklightToNits(i4, f);
            } else if (z) {
                f = android.util.MathUtils.min(this.mRawBacklight[i4], this.mBacklightMaximum);
                f2 = rawBacklightToNits(i4 - 1, f);
            } else {
                f = this.mRawBacklight[i4];
                f2 = this.mRawNits[i4];
            }
            fArr2[i3] = f;
            fArr[i3] = f2;
            i4++;
        }
        int i5 = i3 + 1;
        this.mBacklight = java.util.Arrays.copyOf(fArr2, i5);
        this.mNits = java.util.Arrays.copyOf(fArr, i5);
        createBacklightConversionSplines();
    }

    private float rawBacklightToNits(int i, float f) {
        int i2 = i + 1;
        return android.util.MathUtils.map(this.mRawBacklight[i], this.mRawBacklight[i2], this.mRawNits[i], this.mRawNits[i2], f);
    }

    private void createBacklightConversionSplines() {
        android.util.Spline createSpline;
        android.util.Spline createSpline2;
        android.util.Spline createSpline3;
        android.util.Spline createSpline4;
        this.mBrightness = new float[this.mBacklight.length];
        for (int i = 0; i < this.mBrightness.length; i++) {
            this.mBrightness[i] = android.util.MathUtils.map(this.mBacklight[0], this.mBacklight[this.mBacklight.length - 1], com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, this.mBacklight[i]);
        }
        if (this.mInterpolationType == 1) {
            createSpline = android.util.Spline.createLinearSpline(this.mBrightness, this.mBacklight);
        } else {
            createSpline = android.util.Spline.createSpline(this.mBrightness, this.mBacklight);
        }
        this.mBrightnessToBacklightSpline = createSpline;
        if (this.mInterpolationType == 1) {
            createSpline2 = android.util.Spline.createLinearSpline(this.mBacklight, this.mBrightness);
        } else {
            createSpline2 = android.util.Spline.createSpline(this.mBacklight, this.mBrightness);
        }
        this.mBacklightToBrightnessSpline = createSpline2;
        if (this.mInterpolationType == 1) {
            createSpline3 = android.util.Spline.createLinearSpline(this.mBacklight, this.mNits);
        } else {
            createSpline3 = android.util.Spline.createSpline(this.mBacklight, this.mNits);
        }
        this.mBacklightToNitsSpline = createSpline3;
        if (this.mInterpolationType == 1) {
            createSpline4 = android.util.Spline.createLinearSpline(this.mNits, this.mBacklight);
        } else {
            createSpline4 = android.util.Spline.createSpline(this.mNits, this.mBacklight);
        }
        this.mNitsToBacklightSpline = createSpline4;
    }

    private void loadQuirks(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.DisplayQuirks quirks = displayConfiguration.getQuirks();
        if (quirks != null) {
            this.mQuirks = new java.util.ArrayList(quirks.getQuirk());
        }
    }

    private void loadHighBrightnessModeData(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.HighBrightnessMode highBrightnessMode = displayConfiguration.getHighBrightnessMode();
        if (highBrightnessMode != null) {
            this.mIsHighBrightnessModeEnabled = highBrightnessMode.getEnabled();
            this.mHbmData = new com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData();
            this.mHbmData.minimumLux = highBrightnessMode.getMinimumLux_all().floatValue();
            float floatValue = highBrightnessMode.getTransitionPoint_all().floatValue();
            if (floatValue >= this.mBacklightMaximum) {
                throw new java.lang.IllegalArgumentException("HBM transition point invalid. " + this.mHbmData.transitionPoint + " is not less than " + this.mBacklightMaximum);
            }
            this.mHbmData.transitionPoint = this.mBacklightToBrightnessSpline.interpolate(floatValue);
            com.android.server.display.config.HbmTiming timing_all = highBrightnessMode.getTiming_all();
            this.mHbmData.timeWindowMillis = timing_all.getTimeWindowSecs_all().longValue() * 1000;
            this.mHbmData.timeMaxMillis = timing_all.getTimeMaxSecs_all().longValue() * 1000;
            this.mHbmData.timeMinMillis = timing_all.getTimeMinSecs_all().longValue() * 1000;
            this.mHbmData.allowInLowPowerMode = highBrightnessMode.getAllowInLowPowerMode_all();
            com.android.server.display.config.RefreshRateRange refreshRate_all = highBrightnessMode.getRefreshRate_all();
            if (refreshRate_all != null) {
                this.mRefreshRateLimitations.add(new android.hardware.display.DisplayManagerInternal.RefreshRateLimitation(1, refreshRate_all.getMinimum().floatValue(), refreshRate_all.getMaximum().floatValue()));
            }
            java.math.BigDecimal minimumHdrPercentOfScreen_all = highBrightnessMode.getMinimumHdrPercentOfScreen_all();
            if (minimumHdrPercentOfScreen_all == null) {
                this.mHbmData.minimumHdrPercentOfScreen = 0.5f;
            } else {
                this.mHbmData.minimumHdrPercentOfScreen = minimumHdrPercentOfScreen_all.floatValue();
                if (this.mHbmData.minimumHdrPercentOfScreen > 1.0f || this.mHbmData.minimumHdrPercentOfScreen < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    android.util.Slog.w(TAG, "Invalid minimum HDR percent of screen: " + java.lang.String.valueOf(this.mHbmData.minimumHdrPercentOfScreen));
                    this.mHbmData.minimumHdrPercentOfScreen = 0.5f;
                }
            }
            this.mSdrToHdrRatioSpline = loadSdrHdrRatioMap(highBrightnessMode);
        }
    }

    private void loadLuxThrottling(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.LuxThrottling luxThrottling = displayConfiguration.getLuxThrottling();
        if (luxThrottling != null) {
            com.android.server.display.config.HighBrightnessMode highBrightnessMode = displayConfiguration.getHighBrightnessMode();
            float floatValue = highBrightnessMode != null ? highBrightnessMode.getTransitionPoint_all().floatValue() : 1.0f;
            for (com.android.server.display.config.BrightnessLimitMap brightnessLimitMap : luxThrottling.getBrightnessLimitMap()) {
                com.android.server.display.config.PredefinedBrightnessLimitNames type = brightnessLimitMap.getType();
                com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType convert = com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType.convert(type);
                if (convert == null) {
                    android.util.Slog.wtf(TAG, "Invalid NBM config: unsupported map type=" + type);
                } else if (this.mLuxThrottlingData.containsKey(convert)) {
                    android.util.Slog.wtf(TAG, "Invalid NBM config: duplicate map type=" + convert);
                } else {
                    java.util.HashMap hashMap = new java.util.HashMap();
                    for (com.android.server.display.config.NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint : brightnessLimitMap.getMap().getPoint()) {
                        float floatValue2 = nonNegativeFloatToFloatPoint.getFirst().floatValue();
                        float floatValue3 = nonNegativeFloatToFloatPoint.getSecond().floatValue();
                        if (floatValue3 > floatValue) {
                            android.util.Slog.wtf(TAG, "Invalid NBM config: maxBrightness is greater than hbm.transitionPoint. type=" + type + "; lux=" + floatValue2 + "; maxBrightness=" + floatValue3);
                        } else if (hashMap.containsKey(java.lang.Float.valueOf(floatValue2))) {
                            android.util.Slog.wtf(TAG, "Invalid NBM config: duplicate lux key. type=" + type + "; lux=" + floatValue2);
                        } else {
                            hashMap.put(java.lang.Float.valueOf(floatValue2), java.lang.Float.valueOf(this.mBacklightToBrightnessSpline.interpolate(floatValue3)));
                        }
                    }
                    if (!hashMap.isEmpty()) {
                        this.mLuxThrottlingData.put(convert, hashMap);
                    }
                }
            }
        }
    }

    private void loadBrightnessRamps(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        loadBrightnessRampsInteractive(displayConfiguration);
        loadBrightnessRampsIdle(displayConfiguration);
    }

    private void loadBrightnessRampsInteractive(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        java.math.BigDecimal screenBrightnessRampFastDecrease = displayConfiguration.getScreenBrightnessRampFastDecrease();
        java.math.BigDecimal screenBrightnessRampFastIncrease = displayConfiguration.getScreenBrightnessRampFastIncrease();
        java.math.BigDecimal screenBrightnessRampSlowDecrease = displayConfiguration.getScreenBrightnessRampSlowDecrease();
        java.math.BigDecimal screenBrightnessRampSlowIncrease = displayConfiguration.getScreenBrightnessRampSlowIncrease();
        if (screenBrightnessRampFastDecrease != null && screenBrightnessRampFastIncrease != null && screenBrightnessRampSlowDecrease != null && screenBrightnessRampSlowIncrease != null) {
            this.mBrightnessRampFastDecrease = screenBrightnessRampFastDecrease.floatValue();
            this.mBrightnessRampFastIncrease = screenBrightnessRampFastIncrease.floatValue();
            this.mBrightnessRampSlowDecrease = screenBrightnessRampSlowDecrease.floatValue();
            this.mBrightnessRampSlowIncrease = screenBrightnessRampSlowIncrease.floatValue();
        } else {
            if (screenBrightnessRampFastDecrease != null || screenBrightnessRampFastIncrease != null || screenBrightnessRampSlowDecrease != null || screenBrightnessRampSlowIncrease != null) {
                android.util.Slog.w(TAG, "Per display brightness ramp values ignored because not all values are present in display device config");
            }
            loadBrightnessRampsFromConfigXml();
        }
        if (displayConfiguration.getScreenBrightnessRampIncreaseMaxMillis() != null) {
            this.mBrightnessRampIncreaseMaxMillis = r0.intValue();
        }
        if (displayConfiguration.getScreenBrightnessRampDecreaseMaxMillis() != null) {
            this.mBrightnessRampDecreaseMaxMillis = r5.intValue();
        }
    }

    private void loadBrightnessRampsIdle(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        java.math.BigDecimal screenBrightnessRampSlowDecreaseIdle = displayConfiguration.getScreenBrightnessRampSlowDecreaseIdle();
        java.math.BigDecimal screenBrightnessRampSlowIncreaseIdle = displayConfiguration.getScreenBrightnessRampSlowIncreaseIdle();
        if (screenBrightnessRampSlowDecreaseIdle != null && screenBrightnessRampSlowIncreaseIdle != null) {
            this.mBrightnessRampSlowDecreaseIdle = screenBrightnessRampSlowDecreaseIdle.floatValue();
            this.mBrightnessRampSlowIncreaseIdle = screenBrightnessRampSlowIncreaseIdle.floatValue();
        } else {
            if (screenBrightnessRampSlowDecreaseIdle != null || screenBrightnessRampSlowIncreaseIdle != null) {
                android.util.Slog.w(TAG, "Per display idle brightness ramp values ignored because not all values are present in display device config");
            }
            this.mBrightnessRampSlowDecreaseIdle = this.mBrightnessRampSlowDecrease;
            this.mBrightnessRampSlowIncreaseIdle = this.mBrightnessRampSlowIncrease;
        }
        if (displayConfiguration.getScreenBrightnessRampIncreaseMaxIdleMillis() != null) {
            this.mBrightnessRampIncreaseMaxIdleMillis = r0.intValue();
        } else {
            this.mBrightnessRampIncreaseMaxIdleMillis = this.mBrightnessRampIncreaseMaxMillis;
        }
        if (displayConfiguration.getScreenBrightnessRampDecreaseMaxIdleMillis() != null) {
            this.mBrightnessRampDecreaseMaxIdleMillis = r3.intValue();
        } else {
            this.mBrightnessRampDecreaseMaxIdleMillis = this.mBrightnessRampDecreaseMaxMillis;
        }
    }

    private void loadBrightnessRampsFromConfigXml() {
        this.mBrightnessRampFastIncrease = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_bluetooth_tx_cur_ma));
        this.mBrightnessRampSlowIncrease = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_brightness_ramp_rate_fast));
        this.mBrightnessRampFastDecrease = this.mBrightnessRampFastIncrease;
        this.mBrightnessRampSlowDecrease = this.mBrightnessRampSlowIncrease;
    }

    private void loadAutoBrightnessConfigsFromConfigXml() {
        this.mDisplayBrightnessMapping = new com.android.server.display.config.DisplayBrightnessMappingConfig(this.mContext, this.mFlags, null, this.mBacklightToBrightnessSpline);
    }

    private void loadBrightnessChangeThresholdsFromXml() {
        loadBrightnessChangeThresholds(null);
    }

    private void loadBrightnessChangeThresholds(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        loadDisplayBrightnessThresholds(displayConfiguration);
        loadAmbientBrightnessThresholds(displayConfiguration);
        loadDisplayBrightnessThresholdsIdle(displayConfiguration);
        loadAmbientBrightnessThresholdsIdle(displayConfiguration);
    }

    private void loadDisplayBrightnessThresholds(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.BrightnessThresholds brightnessThresholds;
        com.android.server.display.config.BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration != null && displayConfiguration.getDisplayBrightnessChangeThresholds() != null) {
            com.android.server.display.config.BrightnessThresholds brighteningThresholds = displayConfiguration.getDisplayBrightnessChangeThresholds().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getDisplayBrightnessChangeThresholds().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        } else {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        }
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, android.R.array.config_screenBrightnessBacklight, android.R.array.config_roundedCornerTopRadiusArray, DEFAULT_SCREEN_THRESHOLD_LEVELS, DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS, true);
        this.mScreenBrighteningLevels = (float[]) brightnessLevelAndPercentage.first;
        this.mScreenBrighteningPercentages = (float[]) brightnessLevelAndPercentage.second;
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, android.R.array.config_screenBrightnessBacklight, android.R.array.config_screenBrighteningThresholds, DEFAULT_SCREEN_THRESHOLD_LEVELS, DEFAULT_SCREEN_DARKENING_THRESHOLDS, true);
        this.mScreenDarkeningLevels = (float[]) brightnessLevelAndPercentage2.first;
        this.mScreenDarkeningPercentages = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mScreenBrighteningMinThreshold = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds != null && brightnessThresholds.getMinimum() != null) {
            this.mScreenDarkeningMinThreshold = brightnessThresholds.getMinimum().floatValue();
        }
    }

    private void loadAmbientBrightnessThresholds(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.BrightnessThresholds brightnessThresholds;
        com.android.server.display.config.BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration != null && displayConfiguration.getAmbientBrightnessChangeThresholds() != null) {
            com.android.server.display.config.BrightnessThresholds brighteningThresholds = displayConfiguration.getAmbientBrightnessChangeThresholds().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getAmbientBrightnessChangeThresholds().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        } else {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        }
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, android.R.array.config_ambientThresholdLevels, android.R.array.config_ambientBrighteningThresholds, DEFAULT_AMBIENT_THRESHOLD_LEVELS, DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS);
        this.mAmbientBrighteningLevels = (float[]) brightnessLevelAndPercentage.first;
        this.mAmbientBrighteningPercentages = (float[]) brightnessLevelAndPercentage.second;
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, android.R.array.config_ambientThresholdLevels, android.R.array.config_ambientDarkeningThresholds, DEFAULT_AMBIENT_THRESHOLD_LEVELS, DEFAULT_AMBIENT_DARKENING_THRESHOLDS);
        this.mAmbientDarkeningLevels = (float[]) brightnessLevelAndPercentage2.first;
        this.mAmbientDarkeningPercentages = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mAmbientLuxBrighteningMinThreshold = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds != null && brightnessThresholds.getMinimum() != null) {
            this.mAmbientLuxDarkeningMinThreshold = brightnessThresholds.getMinimum().floatValue();
        }
    }

    private void loadDisplayBrightnessThresholdsIdle(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.BrightnessThresholds brightnessThresholds;
        com.android.server.display.config.BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration != null && displayConfiguration.getDisplayBrightnessChangeThresholdsIdle() != null) {
            com.android.server.display.config.BrightnessThresholds brighteningThresholds = displayConfiguration.getDisplayBrightnessChangeThresholdsIdle().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getDisplayBrightnessChangeThresholdsIdle().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        } else {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        }
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, android.R.array.config_screenBrightnessBacklight, android.R.array.config_roundedCornerTopRadiusArray, DEFAULT_SCREEN_THRESHOLD_LEVELS, DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS, true);
        this.mScreenBrighteningLevelsIdle = (float[]) brightnessLevelAndPercentage.first;
        this.mScreenBrighteningPercentagesIdle = (float[]) brightnessLevelAndPercentage.second;
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, android.R.array.config_screenBrightnessBacklight, android.R.array.config_screenBrighteningThresholds, DEFAULT_SCREEN_THRESHOLD_LEVELS, DEFAULT_SCREEN_DARKENING_THRESHOLDS, true);
        this.mScreenDarkeningLevelsIdle = (float[]) brightnessLevelAndPercentage2.first;
        this.mScreenDarkeningPercentagesIdle = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mScreenBrighteningMinThresholdIdle = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds != null && brightnessThresholds.getMinimum() != null) {
            this.mScreenDarkeningMinThresholdIdle = brightnessThresholds.getMinimum().floatValue();
        }
    }

    private void loadAmbientBrightnessThresholdsIdle(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.BrightnessThresholds brightnessThresholds;
        com.android.server.display.config.BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration != null && displayConfiguration.getAmbientBrightnessChangeThresholdsIdle() != null) {
            com.android.server.display.config.BrightnessThresholds brighteningThresholds = displayConfiguration.getAmbientBrightnessChangeThresholdsIdle().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getAmbientBrightnessChangeThresholdsIdle().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        } else {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        }
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, android.R.array.config_ambientThresholdLevels, android.R.array.config_ambientBrighteningThresholds, DEFAULT_AMBIENT_THRESHOLD_LEVELS, DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS);
        this.mAmbientBrighteningLevelsIdle = (float[]) brightnessLevelAndPercentage.first;
        this.mAmbientBrighteningPercentagesIdle = (float[]) brightnessLevelAndPercentage.second;
        android.util.Pair<float[], float[]> brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, android.R.array.config_ambientThresholdLevels, android.R.array.config_ambientDarkeningThresholds, DEFAULT_AMBIENT_THRESHOLD_LEVELS, DEFAULT_AMBIENT_DARKENING_THRESHOLDS);
        this.mAmbientDarkeningLevelsIdle = (float[]) brightnessLevelAndPercentage2.first;
        this.mAmbientDarkeningPercentagesIdle = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mAmbientLuxBrighteningMinThresholdIdle = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds != null && brightnessThresholds.getMinimum() != null) {
            this.mAmbientLuxDarkeningMinThresholdIdle = brightnessThresholds.getMinimum().floatValue();
        }
    }

    private android.util.Pair<float[], float[]> getBrightnessLevelAndPercentage(com.android.server.display.config.BrightnessThresholds brightnessThresholds, int i, int i2, float[] fArr, float[] fArr2) {
        return getBrightnessLevelAndPercentage(brightnessThresholds, i, i2, fArr, fArr2, false);
    }

    private android.util.Pair<float[], float[]> getBrightnessLevelAndPercentage(com.android.server.display.config.BrightnessThresholds brightnessThresholds, int i, int i2, float[] fArr, float[] fArr2, boolean z) {
        int i3;
        int i4 = 0;
        if (brightnessThresholds != null && brightnessThresholds.getBrightnessThresholdPoints() != null && brightnessThresholds.getBrightnessThresholdPoints().getBrightnessThresholdPoint().size() != 0) {
            java.util.List<com.android.server.display.config.ThresholdPoint> brightnessThresholdPoint = brightnessThresholds.getBrightnessThresholdPoints().getBrightnessThresholdPoint();
            int size = brightnessThresholdPoint.size();
            float[] fArr3 = new float[size];
            float[] fArr4 = new float[size];
            for (com.android.server.display.config.ThresholdPoint thresholdPoint : brightnessThresholdPoint) {
                fArr3[i4] = thresholdPoint.getThreshold().floatValue();
                fArr4[i4] = thresholdPoint.getPercentage().floatValue();
                i4++;
            }
            return new android.util.Pair<>(fArr3, fArr4);
        }
        int[] intArray = this.mContext.getResources().getIntArray(i);
        if (intArray == null || intArray.length == 0) {
            i3 = 1;
        } else {
            i3 = intArray.length + 1;
        }
        int[] intArray2 = this.mContext.getResources().getIntArray(i2);
        boolean z2 = intArray2 == null || intArray2.length == 0;
        if (z2 && i3 == 1) {
            return new android.util.Pair<>(fArr, fArr2);
        }
        if (z2 || intArray2.length != i3) {
            throw new java.lang.IllegalArgumentException("Brightness threshold arrays do not align in length");
        }
        float[] fArr5 = new float[i3];
        for (int i5 = 1; i5 < i3; i5++) {
            fArr5[i5] = intArray[i5 - 1];
        }
        if (z) {
            fArr5 = constraintInRangeIfNeeded(fArr5);
        }
        float[] fArr6 = new float[i3];
        while (i4 < intArray2.length) {
            fArr6[i4] = intArray2[i4] / 10.0f;
            i4++;
        }
        return new android.util.Pair<>(fArr5, fArr6);
    }

    private float[] constraintInRangeIfNeeded(float[] fArr) {
        if (isAllInRange(fArr, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f)) {
            return fArr;
        }
        android.util.Slog.w(TAG, "Detected screen thresholdLevels on a deprecated brightness scale");
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; fArr.length > i; i++) {
            fArr2[i] = fArr[i] / 255.0f;
        }
        return fArr2;
    }

    private boolean isAllInRange(float[] fArr, float f, float f2) {
        for (float f3 : fArr) {
            if (f3 < f || f3 > f2) {
                return false;
            }
        }
        return true;
    }

    private boolean thermalStatusIsValid(com.android.server.display.config.ThermalStatus thermalStatus) {
        if (thermalStatus == null) {
            return false;
        }
        switch (com.android.server.display.DisplayDeviceConfig.AnonymousClass1.$SwitchMap$com$android$server$display$config$ThermalStatus[thermalStatus.ordinal()]) {
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int convertThermalStatus(com.android.server.display.config.ThermalStatus thermalStatus) {
        if (thermalStatus == null) {
            return 0;
        }
        switch (com.android.server.display.DisplayDeviceConfig.AnonymousClass1.$SwitchMap$com$android$server$display$config$ThermalStatus[thermalStatus.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                android.util.Slog.wtf(TAG, "Unexpected Thermal Status: " + thermalStatus);
                break;
        }
        return 0;
    }

    private int convertInterpolationType(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        if ("linear".equals(str)) {
            return 1;
        }
        android.util.Slog.wtf(TAG, "Unexpected Interpolation Type: " + str);
        return 0;
    }

    private void loadAmbientHorizonFromDdc(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        java.math.BigInteger ambientLightHorizonLong = displayConfiguration.getAmbientLightHorizonLong();
        if (ambientLightHorizonLong != null) {
            this.mAmbientHorizonLong = ambientLightHorizonLong.intValue();
        }
        java.math.BigInteger ambientLightHorizonShort = displayConfiguration.getAmbientLightHorizonShort();
        if (ambientLightHorizonShort != null) {
            this.mAmbientHorizonShort = ambientLightHorizonShort.intValue();
        }
    }

    public static float[] getFloatArray(android.content.res.TypedArray typedArray, float f) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, f);
        }
        typedArray.recycle();
        return fArr;
    }

    public static float[] getLuxLevels(int[] iArr) {
        float[] fArr = new float[iArr.length + 1];
        int i = 0;
        while (i < iArr.length) {
            int i2 = i + 1;
            fArr[i2] = iArr[i];
            i = i2;
        }
        return fArr;
    }

    private void loadEnableAutoBrightness(com.android.server.display.config.AutoBrightness autoBrightness) {
        this.mDdcAutoBrightnessAvailable = true;
        if (autoBrightness != null) {
            this.mDdcAutoBrightnessAvailable = autoBrightness.getEnabled();
        }
        this.mAutoBrightnessAvailable = this.mContext.getResources().getBoolean(android.R.bool.config_automatic_brightness_available) && this.mDdcAutoBrightnessAvailable;
    }

    private void loadScreenOffBrightnessSensorValueToLuxFromDdc(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.IntegerArray screenOffBrightnessSensorValueToLux = displayConfiguration.getScreenOffBrightnessSensorValueToLux();
        if (screenOffBrightnessSensorValueToLux == null) {
            return;
        }
        java.util.List<java.math.BigInteger> item = screenOffBrightnessSensorValueToLux.getItem();
        this.mScreenOffBrightnessSensorValueToLux = new int[item.size()];
        for (int i = 0; i < item.size(); i++) {
            this.mScreenOffBrightnessSensorValueToLux[i] = item.get(i).intValue();
        }
    }

    private void loadUsiVersion(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        android.hardware.input.HostUsiVersion hostUsiVersion;
        com.android.server.display.config.UsiVersion usiVersion = displayConfiguration.getUsiVersion();
        if (usiVersion != null) {
            hostUsiVersion = new android.hardware.input.HostUsiVersion(usiVersion.getMajorVersion().intValue(), usiVersion.getMinorVersion().intValue());
        } else {
            hostUsiVersion = null;
        }
        this.mHostUsiVersion = hostUsiVersion;
    }

    private void loadBrightnessCapForWearBedtimeMode(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        if (displayConfiguration != null) {
            java.math.BigDecimal screenBrightnessCapForWearBedtimeMode = displayConfiguration.getScreenBrightnessCapForWearBedtimeMode();
            if (screenBrightnessCapForWearBedtimeMode != null) {
                this.mBrightnessCapForWearBedtimeMode = screenBrightnessCapForWearBedtimeMode.floatValue();
            } else {
                loadBrightnessCapForWearBedtimeModeFromConfigXml();
            }
        }
    }

    private void loadBrightnessCapForWearBedtimeModeFromConfigXml() {
        this.mBrightnessCapForWearBedtimeMode = com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(android.R.integer.config_safe_media_volume_index));
    }

    static class HighBrightnessModeData {
        public boolean allowInLowPowerMode;
        public float minimumHdrPercentOfScreen;
        public float minimumLux;
        public long timeMaxMillis;
        public long timeMinMillis;
        public long timeWindowMillis;
        public float transitionPoint;

        HighBrightnessModeData() {
        }

        HighBrightnessModeData(float f, float f2, long j, long j2, long j3, boolean z, float f3) {
            this.minimumLux = f;
            this.transitionPoint = f2;
            this.timeWindowMillis = j;
            this.timeMaxMillis = j2;
            this.timeMinMillis = j3;
            this.allowInLowPowerMode = z;
            this.minimumHdrPercentOfScreen = f3;
        }

        public void copyTo(@android.annotation.NonNull com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData) {
            highBrightnessModeData.minimumLux = this.minimumLux;
            highBrightnessModeData.timeWindowMillis = this.timeWindowMillis;
            highBrightnessModeData.timeMaxMillis = this.timeMaxMillis;
            highBrightnessModeData.timeMinMillis = this.timeMinMillis;
            highBrightnessModeData.transitionPoint = this.transitionPoint;
            highBrightnessModeData.allowInLowPowerMode = this.allowInLowPowerMode;
            highBrightnessModeData.minimumHdrPercentOfScreen = this.minimumHdrPercentOfScreen;
        }

        public java.lang.String toString() {
            return "HBM{minLux: " + this.minimumLux + ", transition: " + this.transitionPoint + ", timeWindow: " + this.timeWindowMillis + "ms, timeMax: " + this.timeMaxMillis + "ms, timeMin: " + this.timeMinMillis + "ms, allowInLowPowerMode: " + this.allowInLowPowerMode + ", minimumHdrPercentOfScreen: " + this.minimumHdrPercentOfScreen + "} ";
        }
    }

    public static class PowerThrottlingConfigData {
        public final float brightnessLowestCapAllowed;
        public final int pollingWindowMillis;

        public PowerThrottlingConfigData(float f, int i) {
            this.brightnessLowestCapAllowed = f;
            this.pollingWindowMillis = i;
        }

        public java.lang.String toString() {
            return "PowerThrottlingConfigData{brightnessLowestCapAllowed: " + this.brightnessLowestCapAllowed + ", pollingWindowMillis: " + this.pollingWindowMillis + "} ";
        }
    }

    public static class PowerThrottlingData {
        public java.util.List<com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel> throttlingLevels;

        public static class ThrottlingLevel {
            public float powerQuotaMilliWatts;
            public int thermalStatus;

            public ThrottlingLevel(int i, float f) {
                this.thermalStatus = i;
                this.powerQuotaMilliWatts = f;
            }

            public java.lang.String toString() {
                return "[" + this.thermalStatus + "," + this.powerQuotaMilliWatts + "]";
            }

            public boolean equals(java.lang.Object obj) {
                if (!(obj instanceof com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel)) {
                    return false;
                }
                com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel = (com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel) obj;
                return throttlingLevel.thermalStatus == this.thermalStatus && throttlingLevel.powerQuotaMilliWatts == this.powerQuotaMilliWatts;
            }

            public int hashCode() {
                return ((this.thermalStatus + 31) * 31) + java.lang.Float.hashCode(this.powerQuotaMilliWatts);
            }
        }

        public static com.android.server.display.DisplayDeviceConfig.PowerThrottlingData create(java.util.List<com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel> list) {
            if (list == null || list.size() == 0) {
                android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "PowerThrottlingData received null or empty throttling levels");
                return null;
            }
            com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel = list.get(0);
            int size = list.size();
            int i = 1;
            while (i < size) {
                com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel2 = list.get(i);
                if (throttlingLevel2.thermalStatus <= throttlingLevel.thermalStatus) {
                    android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "powerThrottlingMap must be strictly increasing, ignoring configuration. ThermalStatus " + throttlingLevel2.thermalStatus + " <= " + throttlingLevel.thermalStatus);
                    return null;
                }
                if (throttlingLevel2.powerQuotaMilliWatts < throttlingLevel.powerQuotaMilliWatts) {
                    i++;
                    throttlingLevel = throttlingLevel2;
                } else {
                    android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "powerThrottlingMap must be strictly decreasing, ignoring configuration. powerQuotaMilliWatts " + throttlingLevel2.powerQuotaMilliWatts + " >= " + throttlingLevel.powerQuotaMilliWatts);
                    return null;
                }
            }
            return new com.android.server.display.DisplayDeviceConfig.PowerThrottlingData(list);
        }

        public java.lang.String toString() {
            return "PowerThrottlingData{throttlingLevels:" + this.throttlingLevels + "} ";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.display.DisplayDeviceConfig.PowerThrottlingData)) {
                return false;
            }
            return this.throttlingLevels.equals(((com.android.server.display.DisplayDeviceConfig.PowerThrottlingData) obj).throttlingLevels);
        }

        public int hashCode() {
            return this.throttlingLevels.hashCode();
        }

        @com.android.internal.annotations.VisibleForTesting
        PowerThrottlingData(java.util.List<com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel> list) {
            this.throttlingLevels = new java.util.ArrayList(list.size());
            for (com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel : list) {
                this.throttlingLevels.add(new com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel(throttlingLevel.thermalStatus, throttlingLevel.powerQuotaMilliWatts));
            }
        }
    }

    public static class ThermalBrightnessThrottlingData {
        public java.util.List<com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel> throttlingLevels;

        public static class ThrottlingLevel {
            public float brightness;
            public int thermalStatus;

            public ThrottlingLevel(int i, float f) {
                this.thermalStatus = i;
                this.brightness = f;
            }

            public java.lang.String toString() {
                return "[" + this.thermalStatus + "," + this.brightness + "]";
            }

            public boolean equals(java.lang.Object obj) {
                if (!(obj instanceof com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel)) {
                    return false;
                }
                com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel = (com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel) obj;
                return throttlingLevel.thermalStatus == this.thermalStatus && throttlingLevel.brightness == this.brightness;
            }

            public int hashCode() {
                return ((this.thermalStatus + 31) * 31) + java.lang.Float.hashCode(this.brightness);
            }
        }

        public static com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData create(java.util.List<com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel> list) {
            if (list == null || list.size() == 0) {
                android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "BrightnessThrottlingData received null or empty throttling levels");
                return null;
            }
            com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel = list.get(0);
            int size = list.size();
            int i = 1;
            while (i < size) {
                com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel2 = list.get(i);
                if (throttlingLevel2.thermalStatus <= throttlingLevel.thermalStatus) {
                    android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "brightnessThrottlingMap must be strictly increasing, ignoring configuration. ThermalStatus " + throttlingLevel2.thermalStatus + " <= " + throttlingLevel.thermalStatus);
                    return null;
                }
                if (throttlingLevel2.brightness >= throttlingLevel.brightness) {
                    android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "brightnessThrottlingMap must be strictly decreasing, ignoring configuration. Brightness " + throttlingLevel2.brightness + " >= " + throttlingLevel2.brightness);
                    return null;
                }
                i++;
                throttlingLevel = throttlingLevel2;
            }
            for (com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel3 : list) {
                if (throttlingLevel3.brightness > 1.0f) {
                    android.util.Slog.e(com.android.server.display.DisplayDeviceConfig.TAG, "brightnessThrottlingMap contains a brightness value exceeding system max. Brightness " + throttlingLevel3.brightness + " > 1.0");
                    return null;
                }
            }
            return new com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData(list);
        }

        public java.lang.String toString() {
            return "ThermalBrightnessThrottlingData{throttlingLevels:" + this.throttlingLevels + "} ";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData)) {
                return false;
            }
            return this.throttlingLevels.equals(((com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData) obj).throttlingLevels);
        }

        public int hashCode() {
            return this.throttlingLevels.hashCode();
        }

        @com.android.internal.annotations.VisibleForTesting
        ThermalBrightnessThrottlingData(java.util.List<com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel> list) {
            this.throttlingLevels = new java.util.ArrayList(list.size());
            for (com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel : list) {
                this.throttlingLevels.add(new com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(throttlingLevel.thermalStatus, throttlingLevel.brightness));
            }
        }
    }

    /* renamed from: com.android.server.display.DisplayDeviceConfig$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$display$config$PredefinedBrightnessLimitNames = new int[com.android.server.display.config.PredefinedBrightnessLimitNames.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$android$server$display$config$ThermalStatus;

        static {
            try {
                $SwitchMap$com$android$server$display$config$PredefinedBrightnessLimitNames[com.android.server.display.config.PredefinedBrightnessLimitNames._default.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$display$config$PredefinedBrightnessLimitNames[com.android.server.display.config.PredefinedBrightnessLimitNames.adaptive.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            $SwitchMap$com$android$server$display$config$ThermalStatus = new int[com.android.server.display.config.ThermalStatus.values().length];
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.none.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.light.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.moderate.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.severe.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.critical.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.emergency.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[com.android.server.display.config.ThermalStatus.shutdown.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e9) {
            }
        }
    }

    public enum BrightnessLimitMapType {
        DEFAULT,
        ADAPTIVE;

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public static com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType convert(com.android.server.display.config.PredefinedBrightnessLimitNames predefinedBrightnessLimitNames) {
            switch (com.android.server.display.DisplayDeviceConfig.AnonymousClass1.$SwitchMap$com$android$server$display$config$PredefinedBrightnessLimitNames[predefinedBrightnessLimitNames.ordinal()]) {
                case 1:
                    return DEFAULT;
                case 2:
                    return ADAPTIVE;
                default:
                    return null;
            }
        }
    }
}
