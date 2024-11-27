package com.android.server.display;

/* loaded from: classes.dex */
public abstract class BrightnessMappingStrategy {
    public static final float INVALID_LUX = -1.0f;
    public static final float INVALID_NITS = -1.0f;
    private static final float LUX_GRAD_SMOOTHING = 0.25f;
    private static final float MAX_GRAD = 1.0f;
    private static final float MIN_PERMISSABLE_INCREASE = 0.004f;
    private static final float SHORT_TERM_MODEL_THRESHOLD_RATIO = 0.6f;
    protected boolean mLoggingEnabled;
    private static final java.lang.String TAG = "BrightnessMappingStrategy";
    private static final com.android.server.display.utils.Plog PLOG = com.android.server.display.utils.Plog.createSystemPlog(TAG);

    public abstract void addUserDataPoint(float f, float f2);

    public abstract void clearUserDataPoints();

    public abstract float convertToAdjustedNits(float f);

    public abstract float convertToNits(float f);

    public abstract void dump(java.io.PrintWriter printWriter, float f);

    public abstract float getAutoBrightnessAdjustment();

    public abstract float getBrightness(float f, java.lang.String str, int i);

    @android.annotation.Nullable
    public abstract android.hardware.display.BrightnessConfiguration getBrightnessConfiguration();

    public abstract float getBrightnessFromNits(float f);

    public abstract android.hardware.display.BrightnessConfiguration getDefaultConfig();

    abstract int getMode();

    public abstract long getShortTermModelTimeout();

    abstract float getUserBrightness();

    abstract float getUserLux();

    public abstract boolean hasUserDataPoints();

    public abstract boolean isDefaultConfig();

    public abstract void recalculateSplines(boolean z, float[] fArr);

    public abstract boolean setAutoBrightnessAdjustment(float f);

    public abstract boolean setBrightnessConfiguration(@android.annotation.Nullable android.hardware.display.BrightnessConfiguration brightnessConfiguration);

    @android.annotation.Nullable
    static com.android.server.display.BrightnessMappingStrategy create(android.content.Context context, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, int i, @android.annotation.Nullable com.android.server.display.whitebalance.DisplayWhiteBalanceController displayWhiteBalanceController) {
        float[] autoBrightnessBrighteningLevelsNits;
        float[] autoBrightnessBrighteningLevels;
        float[] fArr;
        int intForUser = android.provider.Settings.System.getIntForUser(context.getContentResolver(), "screen_brightness_for_als", 2, -2);
        switch (i) {
            case 0:
                autoBrightnessBrighteningLevelsNits = displayDeviceConfig.getAutoBrightnessBrighteningLevelsNits();
                float[] autoBrightnessBrighteningLevelsLux = displayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(i, intForUser);
                autoBrightnessBrighteningLevels = displayDeviceConfig.getAutoBrightnessBrighteningLevels(i, intForUser);
                fArr = autoBrightnessBrighteningLevelsLux;
                break;
            case 1:
                autoBrightnessBrighteningLevels = null;
                autoBrightnessBrighteningLevelsNits = getFloatArray(context.getResources().obtainTypedArray(android.R.array.config_autoBrightnessDisplayValuesNitsIdle));
                fArr = getLuxLevels(context.getResources().getIntArray(android.R.array.config_autoBrightnessLevelsIdle));
                break;
            case 2:
                float[] autoBrightnessBrighteningLevelsLux2 = displayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(i, intForUser);
                autoBrightnessBrighteningLevels = displayDeviceConfig.getAutoBrightnessBrighteningLevels(i, intForUser);
                fArr = autoBrightnessBrighteningLevelsLux2;
                autoBrightnessBrighteningLevelsNits = null;
                break;
            default:
                fArr = null;
                autoBrightnessBrighteningLevelsNits = null;
                autoBrightnessBrighteningLevels = null;
                break;
        }
        float fraction = context.getResources().getFraction(android.R.fraction.config_autoBrightnessAdjustmentMaxGamma, 1, 1);
        long integer = context.getResources().getInteger(android.R.integer.config_autoBrightnessShortTermModelTimeout);
        float[] nits = displayDeviceConfig.getNits();
        float[] brightness = displayDeviceConfig.getBrightness();
        if (isValidMapping(nits, brightness) && isValidMapping(fArr, autoBrightnessBrighteningLevelsNits)) {
            android.hardware.display.BrightnessConfiguration.Builder builder = new android.hardware.display.BrightnessConfiguration.Builder(fArr, autoBrightnessBrighteningLevelsNits);
            builder.setShortTermModelTimeoutMillis(integer);
            builder.setShortTermModelLowerLuxMultiplier(SHORT_TERM_MODEL_THRESHOLD_RATIO);
            builder.setShortTermModelUpperLuxMultiplier(SHORT_TERM_MODEL_THRESHOLD_RATIO);
            return new com.android.server.display.BrightnessMappingStrategy.PhysicalMappingStrategy(builder.build(), nits, brightness, fraction, i, displayWhiteBalanceController);
        }
        if (!isValidMapping(fArr, autoBrightnessBrighteningLevels)) {
            return null;
        }
        return new com.android.server.display.BrightnessMappingStrategy.SimpleMappingStrategy(fArr, autoBrightnessBrighteningLevels, fraction, integer, i);
    }

    private static float[] getLuxLevels(int[] iArr) {
        float[] fArr = new float[iArr.length + 1];
        int i = 0;
        while (i < iArr.length) {
            int i2 = i + 1;
            fArr[i2] = iArr[i];
            i = i2;
        }
        return fArr;
    }

    public static float[] getFloatArray(android.content.res.TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, -1.0f);
        }
        typedArray.recycle();
        return fArr;
    }

    private static boolean isValidMapping(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null || fArr.length == 0 || fArr2.length == 0 || fArr.length != fArr2.length) {
            return false;
        }
        int length = fArr.length;
        float f = fArr[0];
        float f2 = fArr2[0];
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f2 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || java.lang.Float.isNaN(f) || java.lang.Float.isNaN(f2)) {
            return false;
        }
        for (int i = 1; i < length; i++) {
            if (f >= fArr[i] || f2 > fArr2[i] || java.lang.Float.isNaN(fArr[i]) || java.lang.Float.isNaN(fArr2[i])) {
                return false;
            }
            f = fArr[i];
            f2 = fArr2[i];
        }
        return true;
    }

    private static boolean isValidMapping(float[] fArr, int[] iArr) {
        if (fArr == null || iArr == null || fArr.length == 0 || iArr.length == 0 || fArr.length != iArr.length) {
            return false;
        }
        int length = fArr.length;
        float f = fArr[0];
        int i = iArr[0];
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || i < 0 || java.lang.Float.isNaN(f)) {
            return false;
        }
        for (int i2 = 1; i2 < length; i2++) {
            if (f >= fArr[i2] || i > iArr[i2] || java.lang.Float.isNaN(fArr[i2])) {
                return false;
            }
            f = fArr[i2];
            i = iArr[i2];
        }
        return true;
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        this.mLoggingEnabled = z;
        return true;
    }

    public float getBrightness(float f) {
        return getBrightness(f, null, -1);
    }

    public boolean shouldResetShortTermModel(float f, float f2) {
        float f3;
        float f4;
        android.hardware.display.BrightnessConfiguration brightnessConfiguration = getBrightnessConfiguration();
        float f5 = SHORT_TERM_MODEL_THRESHOLD_RATIO;
        if (brightnessConfiguration == null) {
            f3 = 0.6f;
        } else {
            if (java.lang.Float.isNaN(brightnessConfiguration.getShortTermModelLowerLuxMultiplier())) {
                f4 = 0.6f;
            } else {
                f4 = brightnessConfiguration.getShortTermModelLowerLuxMultiplier();
            }
            if (java.lang.Float.isNaN(brightnessConfiguration.getShortTermModelUpperLuxMultiplier())) {
                f3 = 0.6f;
                f5 = f4;
            } else {
                f3 = brightnessConfiguration.getShortTermModelUpperLuxMultiplier();
                f5 = f4;
            }
        }
        float f6 = f2 - (f5 * f2);
        float f7 = f2 + (f3 * f2);
        if (f6 < f && f <= f7) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "ShortTermModel: re-validate user data, ambient lux is " + f6 + " < " + f + " < " + f7);
                return false;
            }
            return false;
        }
        android.util.Slog.d(TAG, "ShortTermModel: reset data, ambient lux is " + f + "(" + f6 + ", " + f7 + ")");
        return true;
    }

    private android.util.Pair<float[], float[]> insertControlPoint(float[] fArr, float[] fArr2, float f, float f2) {
        float[] fArr3;
        float[] fArr4;
        int findInsertionPoint = findInsertionPoint(fArr, f);
        if (findInsertionPoint == fArr.length) {
            fArr4 = java.util.Arrays.copyOf(fArr, fArr.length + 1);
            fArr3 = java.util.Arrays.copyOf(fArr2, fArr2.length + 1);
            fArr4[findInsertionPoint] = f;
            fArr3[findInsertionPoint] = f2;
        } else if (fArr[findInsertionPoint] == f) {
            fArr4 = java.util.Arrays.copyOf(fArr, fArr.length);
            fArr3 = java.util.Arrays.copyOf(fArr2, fArr2.length);
            fArr3[findInsertionPoint] = f2;
        } else {
            float[] copyOf = java.util.Arrays.copyOf(fArr, fArr.length + 1);
            int i = findInsertionPoint + 1;
            java.lang.System.arraycopy(copyOf, findInsertionPoint, copyOf, i, fArr.length - findInsertionPoint);
            copyOf[findInsertionPoint] = f;
            float[] copyOf2 = java.util.Arrays.copyOf(fArr2, fArr2.length + 1);
            java.lang.System.arraycopy(copyOf2, findInsertionPoint, copyOf2, i, fArr2.length - findInsertionPoint);
            copyOf2[findInsertionPoint] = f2;
            fArr3 = copyOf2;
            fArr4 = copyOf;
        }
        smoothCurve(fArr4, fArr3, findInsertionPoint);
        return android.util.Pair.create(fArr4, fArr3);
    }

    private int findInsertionPoint(float[] fArr, float f) {
        for (int i = 0; i < fArr.length; i++) {
            if (f <= fArr[i]) {
                return i;
            }
        }
        return fArr.length;
    }

    private void smoothCurve(float[] fArr, float[] fArr2, int i) {
        if (this.mLoggingEnabled) {
            PLOG.logCurve("unsmoothed curve", fArr, fArr2);
        }
        float f = fArr[i];
        float f2 = fArr2[i];
        int i2 = i + 1;
        while (i2 < fArr.length) {
            float f3 = fArr[i2];
            float f4 = fArr2[i2];
            f2 = android.util.MathUtils.constrain(f4, f2, android.util.MathUtils.max(permissibleRatio(f3, f) * f2, MIN_PERMISSABLE_INCREASE + f2));
            if (f2 == f4) {
                break;
            }
            fArr2[i2] = f2;
            i2++;
            f = f3;
        }
        float f5 = fArr[i];
        float f6 = fArr2[i];
        int i3 = i - 1;
        while (i3 >= 0) {
            float f7 = fArr[i3];
            float f8 = fArr2[i3];
            f6 = android.util.MathUtils.constrain(f8, permissibleRatio(f7, f5) * f6, f6);
            if (f6 == f8) {
                break;
            }
            fArr2[i3] = f6;
            i3--;
            f5 = f7;
        }
        if (this.mLoggingEnabled) {
            PLOG.logCurve("smoothed curve", fArr, fArr2);
        }
    }

    private float permissibleRatio(float f, float f2) {
        return android.util.MathUtils.pow((f + LUX_GRAD_SMOOTHING) / (f2 + LUX_GRAD_SMOOTHING), 1.0f);
    }

    protected float inferAutoBrightnessAdjustment(float f, float f2, float f3) {
        float f4;
        float f5 = Float.NaN;
        if (f3 <= 0.1f || f3 >= 0.9f) {
            f4 = f2 - f3;
        } else if (f2 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f4 = -1.0f;
        } else if (f2 == 1.0f) {
            f4 = 1.0f;
        } else {
            f5 = android.util.MathUtils.log(f2) / android.util.MathUtils.log(f3);
            f4 = (-android.util.MathUtils.log(f5)) / android.util.MathUtils.log(f);
        }
        float constrain = android.util.MathUtils.constrain(f4, -1.0f, 1.0f);
        if (this.mLoggingEnabled) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("inferAutoBrightnessAdjustment: ");
            sb.append(f);
            sb.append("^");
            float f6 = -constrain;
            sb.append(f6);
            sb.append("=");
            sb.append(android.util.MathUtils.pow(f, f6));
            sb.append(" == ");
            sb.append(f5);
            android.util.Slog.d(TAG, sb.toString());
            android.util.Slog.d(TAG, "inferAutoBrightnessAdjustment: " + f3 + "^" + f5 + "=" + android.util.MathUtils.pow(f3, f5) + " == " + f2);
        }
        return constrain;
    }

    protected android.util.Pair<float[], float[]> getAdjustedCurve(float[] fArr, float[] fArr2, float f, float f2, float f3, float f4) {
        float[] copyOf = java.util.Arrays.copyOf(fArr2, fArr2.length);
        if (this.mLoggingEnabled) {
            PLOG.logCurve("unadjusted curve", fArr, copyOf);
        }
        float f5 = -android.util.MathUtils.constrain(f3, -1.0f, 1.0f);
        float pow = android.util.MathUtils.pow(f4, f5);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "getAdjustedCurve: " + f4 + "^" + f5 + "=" + android.util.MathUtils.pow(f4, f5) + " == " + pow);
        }
        if (pow != 1.0f) {
            for (int i = 0; i < copyOf.length; i++) {
                copyOf[i] = android.util.MathUtils.pow(copyOf[i], pow);
            }
        }
        if (this.mLoggingEnabled) {
            PLOG.logCurve("gamma adjusted curve", fArr, copyOf);
        }
        if (f != -1.0f) {
            android.util.Pair<float[], float[]> insertControlPoint = insertControlPoint(fArr, copyOf, f, f2);
            float[] fArr3 = (float[]) insertControlPoint.first;
            copyOf = (float[]) insertControlPoint.second;
            if (this.mLoggingEnabled) {
                PLOG.logCurve("gamma and user adjusted curve", fArr3, copyOf);
                android.util.Pair<float[], float[]> insertControlPoint2 = insertControlPoint(fArr, fArr2, f, f2);
                PLOG.logCurve("user adjusted curve", (float[]) insertControlPoint2.first, (float[]) insertControlPoint2.second);
            }
            fArr = fArr3;
        }
        return android.util.Pair.create(fArr, copyOf);
    }

    private static class SimpleMappingStrategy extends com.android.server.display.BrightnessMappingStrategy {
        private float mAutoBrightnessAdjustment;
        private final float[] mBrightness;
        private final float[] mLux;
        private float mMaxGamma;
        private final int mMode;
        private long mShortTermModelTimeout;
        private android.util.Spline mSpline;
        private float mUserBrightness;
        private float mUserLux;

        private SimpleMappingStrategy(float[] fArr, float[] fArr2, float f, long j, int i) {
            com.android.internal.util.Preconditions.checkArgument((fArr.length == 0 || fArr2.length == 0) ? false : true, "Lux and brightness arrays must not be empty!");
            com.android.internal.util.Preconditions.checkArgument(fArr.length == fArr2.length, "Lux and brightness arrays must be the same length!");
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, Float.MAX_VALUE, "lux");
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 2.1474836E9f, "brightness");
            int length = fArr2.length;
            this.mLux = new float[length];
            this.mBrightness = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.mLux[i2] = fArr[i2];
                this.mBrightness[i2] = fArr2[i2];
            }
            this.mMaxGamma = f;
            this.mAutoBrightnessAdjustment = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mUserLux = -1.0f;
            this.mUserBrightness = Float.NaN;
            if (this.mLoggingEnabled) {
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("simple mapping strategy");
            }
            computeSpline();
            this.mShortTermModelTimeout = j;
            this.mMode = i;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public long getShortTermModelTimeout() {
            return this.mShortTermModelTimeout;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean setBrightnessConfiguration(@android.annotation.Nullable android.hardware.display.BrightnessConfiguration brightnessConfiguration) {
            return false;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration() {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getBrightness(float f, java.lang.String str, int i) {
            return this.mSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getAutoBrightnessAdjustment() {
            return this.mAutoBrightnessAdjustment;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean setAutoBrightnessAdjustment(float f) {
            float constrain = android.util.MathUtils.constrain(f, -1.0f, 1.0f);
            if (constrain == this.mAutoBrightnessAdjustment) {
                return false;
            }
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "setAutoBrightnessAdjustment: " + this.mAutoBrightnessAdjustment + " => " + constrain);
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
            }
            this.mAutoBrightnessAdjustment = constrain;
            computeSpline();
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float convertToNits(float f) {
            return -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float convertToAdjustedNits(float f) {
            return -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getBrightnessFromNits(float f) {
            return Float.NaN;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void addUserDataPoint(float f, float f2) {
            float unadjustedBrightness = getUnadjustedBrightness(f);
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "addUserDataPoint: (" + f + "," + f2 + ")");
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("add user data point").logPoint("user data point", f, f2).logPoint("current brightness", f, unadjustedBrightness);
            }
            float inferAutoBrightnessAdjustment = inferAutoBrightnessAdjustment(this.mMaxGamma, f2, unadjustedBrightness);
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "addUserDataPoint: " + this.mAutoBrightnessAdjustment + " => " + inferAutoBrightnessAdjustment);
            }
            this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
            this.mUserLux = f;
            this.mUserBrightness = f2;
            computeSpline();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void clearUserDataPoints() {
            if (this.mUserLux != -1.0f) {
                if (this.mLoggingEnabled) {
                    android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
                    com.android.server.display.BrightnessMappingStrategy.PLOG.start("clear user data points").logPoint("user data point", this.mUserLux, this.mUserBrightness);
                }
                this.mAutoBrightnessAdjustment = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                this.mUserLux = -1.0f;
                this.mUserBrightness = Float.NaN;
                computeSpline();
            }
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean hasUserDataPoints() {
            return this.mUserLux != -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean isDefaultConfig() {
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public android.hardware.display.BrightnessConfiguration getDefaultConfig() {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void recalculateSplines(boolean z, float[] fArr) {
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void dump(java.io.PrintWriter printWriter, float f) {
            printWriter.println("SimpleMappingStrategy");
            printWriter.println("  mSpline=" + this.mSpline);
            printWriter.println("  mMaxGamma=" + this.mMaxGamma);
            printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
            printWriter.println("  mUserLux=" + this.mUserLux);
            printWriter.println("  mUserBrightness=" + this.mUserBrightness);
            printWriter.println("  mShortTermModelTimeout=" + this.mShortTermModelTimeout);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        int getMode() {
            return this.mMode;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        float getUserLux() {
            return this.mUserLux;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        float getUserBrightness() {
            return this.mUserBrightness;
        }

        private void computeSpline() {
            android.util.Pair<float[], float[]> adjustedCurve = getAdjustedCurve(this.mLux, this.mBrightness, this.mUserLux, this.mUserBrightness, this.mAutoBrightnessAdjustment, this.mMaxGamma);
            this.mSpline = android.util.Spline.createSpline((float[]) adjustedCurve.first, (float[]) adjustedCurve.second);
        }

        private float getUnadjustedBrightness(float f) {
            return android.util.Spline.createSpline(this.mLux, this.mBrightness).interpolate(f);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class PhysicalMappingStrategy extends com.android.server.display.BrightnessMappingStrategy {
        private static final java.text.SimpleDateFormat FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        private static final int NO_OF_PREVIOUS_CONFIGS_TO_LOG = 5;
        private android.util.Spline mAdjustedNitsToBrightnessSpline;
        private float mAutoBrightnessAdjustment;
        private final float[] mBrightness;
        private boolean mBrightnessRangeAdjustmentApplied;
        private android.util.Spline mBrightnessSpline;
        private android.util.Spline mBrightnessToAdjustedNitsSpline;
        private android.util.Spline mBrightnessToNitsSpline;
        private android.hardware.display.BrightnessConfiguration mConfig;
        private final android.hardware.display.BrightnessConfiguration mDefaultConfig;

        @android.annotation.Nullable
        private final com.android.server.display.whitebalance.DisplayWhiteBalanceController mDisplayWhiteBalanceController;
        private final float mMaxGamma;
        private final int mMode;
        private final float[] mNits;
        private android.util.Spline mNitsToBrightnessSpline;
        private float mUserBrightness;
        private float mUserLux;
        private java.util.List<android.util.Spline> mPreviousBrightnessSplines = new java.util.ArrayList();
        private android.util.LongArray mBrightnessSplineChangeTimes = new android.util.LongArray();

        public PhysicalMappingStrategy(android.hardware.display.BrightnessConfiguration brightnessConfiguration, float[] fArr, float[] fArr2, float f, int i, @android.annotation.Nullable com.android.server.display.whitebalance.DisplayWhiteBalanceController displayWhiteBalanceController) {
            com.android.internal.util.Preconditions.checkArgument((fArr.length == 0 || fArr2.length == 0) ? false : true, "Nits and brightness arrays must not be empty!");
            com.android.internal.util.Preconditions.checkArgument(fArr.length == fArr2.length, "Nits and brightness arrays must be the same length!");
            java.util.Objects.requireNonNull(brightnessConfiguration);
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, Float.MAX_VALUE, "nits");
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, "brightness");
            this.mMode = i;
            this.mMaxGamma = f;
            this.mAutoBrightnessAdjustment = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mUserLux = -1.0f;
            this.mUserBrightness = Float.NaN;
            this.mDisplayWhiteBalanceController = displayWhiteBalanceController;
            this.mNits = fArr;
            this.mBrightness = fArr2;
            computeNitsBrightnessSplines(this.mNits);
            this.mAdjustedNitsToBrightnessSpline = this.mNitsToBrightnessSpline;
            this.mBrightnessToAdjustedNitsSpline = this.mBrightnessToNitsSpline;
            this.mDefaultConfig = brightnessConfiguration;
            if (this.mLoggingEnabled) {
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("physical mapping strategy");
            }
            this.mConfig = brightnessConfiguration;
            computeSpline();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public long getShortTermModelTimeout() {
            if (this.mConfig.getShortTermModelTimeoutMillis() >= 0) {
                return this.mConfig.getShortTermModelTimeoutMillis();
            }
            return this.mDefaultConfig.getShortTermModelTimeoutMillis();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean setBrightnessConfiguration(@android.annotation.Nullable android.hardware.display.BrightnessConfiguration brightnessConfiguration) {
            if (brightnessConfiguration == null) {
                brightnessConfiguration = this.mDefaultConfig;
            }
            if (brightnessConfiguration.equals(this.mConfig)) {
                return false;
            }
            if (this.mLoggingEnabled) {
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("brightness configuration");
            }
            this.mConfig = brightnessConfiguration;
            computeSpline();
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration() {
            return this.mConfig;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getBrightness(float f, java.lang.String str, int i) {
            float interpolate = this.mBrightnessSpline.interpolate(f);
            if (this.mDisplayWhiteBalanceController != null) {
                interpolate = this.mDisplayWhiteBalanceController.calculateAdjustedBrightnessNits(interpolate);
            }
            float interpolate2 = this.mAdjustedNitsToBrightnessSpline.interpolate(interpolate);
            if (this.mUserLux == -1.0f) {
                return correctBrightness(interpolate2, str, i);
            }
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "user point set, correction not applied");
                return interpolate2;
            }
            return interpolate2;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getAutoBrightnessAdjustment() {
            return this.mAutoBrightnessAdjustment;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean setAutoBrightnessAdjustment(float f) {
            float constrain = android.util.MathUtils.constrain(f, -1.0f, 1.0f);
            if (constrain == this.mAutoBrightnessAdjustment) {
                return false;
            }
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "setAutoBrightnessAdjustment: " + this.mAutoBrightnessAdjustment + " => " + constrain);
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
            }
            this.mAutoBrightnessAdjustment = constrain;
            computeSpline();
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float convertToNits(float f) {
            return this.mBrightnessToNitsSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float convertToAdjustedNits(float f) {
            return this.mBrightnessToAdjustedNitsSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public float getBrightnessFromNits(float f) {
            return this.mNitsToBrightnessSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void addUserDataPoint(float f, float f2) {
            float unadjustedBrightness = getUnadjustedBrightness(f);
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "addUserDataPoint: (" + f + "," + f2 + ")");
                com.android.server.display.BrightnessMappingStrategy.PLOG.start("add user data point").logPoint("user data point", f, f2).logPoint("current brightness", f, unadjustedBrightness);
            }
            float inferAutoBrightnessAdjustment = inferAutoBrightnessAdjustment(this.mMaxGamma, f2, unadjustedBrightness);
            if (this.mLoggingEnabled) {
                android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "addUserDataPoint: " + this.mAutoBrightnessAdjustment + " => " + inferAutoBrightnessAdjustment);
            }
            this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
            this.mUserLux = f;
            this.mUserBrightness = f2;
            computeSpline();
            if (this.mPreviousBrightnessSplines.size() == 5) {
                this.mPreviousBrightnessSplines.remove(0);
                this.mBrightnessSplineChangeTimes.remove(0);
            }
            this.mPreviousBrightnessSplines.add(this.mBrightnessSpline);
            this.mBrightnessSplineChangeTimes.add(java.lang.System.currentTimeMillis());
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void clearUserDataPoints() {
            if (this.mUserLux != -1.0f) {
                if (this.mLoggingEnabled) {
                    android.util.Slog.d(com.android.server.display.BrightnessMappingStrategy.TAG, "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
                    com.android.server.display.BrightnessMappingStrategy.PLOG.start("clear user data points").logPoint("user data point", this.mUserLux, this.mUserBrightness);
                }
                this.mAutoBrightnessAdjustment = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                this.mUserLux = -1.0f;
                this.mUserBrightness = Float.NaN;
                computeSpline();
            }
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean hasUserDataPoints() {
            return this.mUserLux != -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public boolean isDefaultConfig() {
            return this.mDefaultConfig.equals(this.mConfig);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public android.hardware.display.BrightnessConfiguration getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void recalculateSplines(boolean z, float[] fArr) {
            this.mBrightnessRangeAdjustmentApplied = z;
            if (z) {
                this.mAdjustedNitsToBrightnessSpline = android.util.Spline.createSpline(fArr, this.mBrightness);
                this.mBrightnessToAdjustedNitsSpline = android.util.Spline.createSpline(this.mBrightness, fArr);
            } else {
                this.mAdjustedNitsToBrightnessSpline = this.mNitsToBrightnessSpline;
                this.mBrightnessToAdjustedNitsSpline = this.mBrightnessToNitsSpline;
            }
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public void dump(java.io.PrintWriter printWriter, float f) {
            printWriter.println("PhysicalMappingStrategy");
            printWriter.println("  mConfig=" + this.mConfig);
            printWriter.println("  mBrightnessSpline=" + this.mBrightnessSpline);
            printWriter.println("  mNitsToBrightnessSpline=" + this.mNitsToBrightnessSpline);
            printWriter.println("  mBrightnessToNitsSpline=" + this.mBrightnessToNitsSpline);
            printWriter.println("  mAdjustedNitsToBrightnessSpline=" + this.mAdjustedNitsToBrightnessSpline);
            printWriter.println("  mAdjustedBrightnessToNitsSpline=" + this.mBrightnessToAdjustedNitsSpline);
            printWriter.println("  mMaxGamma=" + this.mMaxGamma);
            printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
            printWriter.println("  mUserLux=" + this.mUserLux);
            printWriter.println("  mUserBrightness=" + this.mUserBrightness);
            printWriter.println("  mDefaultConfig=" + this.mDefaultConfig);
            printWriter.println("  mBrightnessRangeAdjustmentApplied=" + this.mBrightnessRangeAdjustmentApplied);
            printWriter.println("  shortTermModelTimeout=" + getShortTermModelTimeout());
            printWriter.println("  Previous short-term models (oldest to newest): ");
            for (int i = 0; i < this.mPreviousBrightnessSplines.size(); i++) {
                printWriter.println("  Computed at " + FORMAT.format(new java.util.Date(this.mBrightnessSplineChangeTimes.get(i))) + ": ");
                dumpConfigDiff(printWriter, f, this.mPreviousBrightnessSplines.get(i), true);
            }
            printWriter.println("  Difference between current config and default: ");
            dumpConfigDiff(printWriter, f, this.mBrightnessSpline, false);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        int getMode() {
            return this.mMode;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        float getUserLux() {
            return this.mUserLux;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        float getUserBrightness() {
            return this.mUserBrightness;
        }

        private void dumpConfigDiff(java.io.PrintWriter printWriter, float f, android.util.Spline spline, boolean z) {
            int i;
            boolean z2;
            java.io.PrintWriter printWriter2;
            java.lang.String str;
            java.lang.String str2;
            com.android.server.display.BrightnessMappingStrategy.PhysicalMappingStrategy physicalMappingStrategy = this;
            android.util.Pair curve = physicalMappingStrategy.mConfig.getCurve();
            android.util.Spline createSpline = android.util.Spline.createSpline((float[]) curve.first, (float[]) curve.second);
            android.util.Pair curve2 = physicalMappingStrategy.mDefaultConfig.getCurve();
            android.util.Spline createSpline2 = android.util.Spline.createSpline((float[]) curve2.first, (float[]) curve2.second);
            float[] fArr = (float[]) curve.first;
            if (physicalMappingStrategy.mUserLux >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                fArr = java.util.Arrays.copyOf((float[]) curve.first, ((float[]) curve.first).length + 1);
                fArr[fArr.length - 1] = physicalMappingStrategy.mUserLux;
                java.util.Arrays.sort(fArr);
            }
            java.lang.String str3 = "";
            java.lang.StringBuilder sb = null;
            java.lang.String str4 = "";
            boolean z3 = true;
            java.lang.StringBuilder sb2 = null;
            java.lang.StringBuilder sb3 = null;
            java.lang.StringBuilder sb4 = null;
            java.lang.StringBuilder sb5 = null;
            java.lang.StringBuilder sb6 = null;
            java.lang.StringBuilder sb7 = null;
            int i2 = 0;
            while (i2 < fArr.length) {
                float f2 = fArr[i2];
                if (z3) {
                    java.lang.StringBuilder sb8 = new java.lang.StringBuilder("            lux: ");
                    sb2 = new java.lang.StringBuilder("        default: ");
                    sb3 = new java.lang.StringBuilder("      long-term: ");
                    sb4 = new java.lang.StringBuilder("        current: ");
                    sb5 = new java.lang.StringBuilder("    current(bl): ");
                    sb6 = new java.lang.StringBuilder("     current(%): ");
                    sb7 = new java.lang.StringBuilder("  current(hbm%): ");
                    sb = sb8;
                    z3 = false;
                }
                java.lang.String str5 = str3;
                float interpolate = createSpline2.interpolate(f2);
                android.util.Spline spline2 = createSpline2;
                float interpolate2 = createSpline.interpolate(f2);
                android.util.Spline spline3 = createSpline;
                float interpolate3 = spline.interpolate(f2);
                float interpolate4 = physicalMappingStrategy.mAdjustedNitsToBrightnessSpline.interpolate(interpolate3);
                int i3 = i2;
                float[] fArr2 = fArr;
                java.lang.String str6 = (f2 == physicalMappingStrategy.mUserLux ? "^" : str5) + physicalMappingStrategy.toStrFloatForDump(f2);
                java.lang.String strFloatForDump = physicalMappingStrategy.toStrFloatForDump(interpolate);
                java.lang.String strFloatForDump2 = physicalMappingStrategy.toStrFloatForDump(interpolate2);
                java.lang.String strFloatForDump3 = physicalMappingStrategy.toStrFloatForDump(interpolate3);
                java.lang.String strFloatForDump4 = physicalMappingStrategy.toStrFloatForDump(interpolate4);
                java.lang.String valueOf = java.lang.String.valueOf(java.lang.Math.round(com.android.internal.display.BrightnessUtils.convertLinearToGamma(interpolate4 / f) * 100.0f));
                java.lang.String valueOf2 = java.lang.String.valueOf(java.lang.Math.round(com.android.internal.display.BrightnessUtils.convertLinearToGamma(interpolate4) * 100.0f));
                java.lang.String str7 = str4 + "%" + java.lang.Math.max(str6.length(), java.lang.Math.max(strFloatForDump.length(), java.lang.Math.max(strFloatForDump4.length(), java.lang.Math.max(valueOf.length(), java.lang.Math.max(valueOf2.length(), java.lang.Math.max(strFloatForDump2.length(), strFloatForDump3.length())))))) + "s";
                sb.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{str6}));
                sb2.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{strFloatForDump}));
                sb3.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{strFloatForDump2}));
                sb4.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{strFloatForDump3}));
                sb5 = sb5;
                sb5.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{strFloatForDump4}));
                sb6 = sb6;
                sb6.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{valueOf}));
                sb7 = sb7;
                sb7.append(android.text.TextUtils.formatSimple(str7, new java.lang.Object[]{valueOf2}));
                if (sb.length() <= 80) {
                    fArr = fArr2;
                    z2 = true;
                    i = i3;
                    if (i != fArr.length - 1) {
                        str2 = ", ";
                        str = str5;
                        printWriter2 = printWriter;
                        i2 = i + 1;
                        str3 = str;
                        str4 = str2;
                        createSpline2 = spline2;
                        createSpline = spline3;
                        physicalMappingStrategy = this;
                    }
                } else {
                    i = i3;
                    fArr = fArr2;
                    z2 = true;
                }
                printWriter2 = printWriter;
                printWriter2.println(sb);
                if (!z) {
                    printWriter2.println(sb2);
                    printWriter2.println(sb3);
                }
                printWriter2.println(sb4);
                printWriter2.println(sb5);
                printWriter2.println(sb6);
                if (f < 1.0f) {
                    printWriter2.println(sb7);
                }
                str = str5;
                printWriter2.println(str);
                z3 = z2;
                str2 = str;
                i2 = i + 1;
                str3 = str;
                str4 = str2;
                createSpline2 = spline2;
                createSpline = spline3;
                physicalMappingStrategy = this;
            }
        }

        private java.lang.String toStrFloatForDump(float f) {
            if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return "0";
            }
            if (f < 0.1f) {
                return java.lang.String.format(java.util.Locale.US, "%.3f", java.lang.Float.valueOf(f));
            }
            if (f < 1.0f) {
                return java.lang.String.format(java.util.Locale.US, "%.2f", java.lang.Float.valueOf(f));
            }
            if (f < 10.0f) {
                return java.lang.String.format(java.util.Locale.US, "%.1f", java.lang.Float.valueOf(f));
            }
            return android.text.TextUtils.formatSimple("%d", new java.lang.Object[]{java.lang.Integer.valueOf(java.lang.Math.round(f))});
        }

        private void computeNitsBrightnessSplines(float[] fArr) {
            this.mNitsToBrightnessSpline = android.util.Spline.createSpline(fArr, this.mBrightness);
            this.mBrightnessToNitsSpline = android.util.Spline.createSpline(this.mBrightness, fArr);
        }

        private void computeSpline() {
            android.util.Pair curve = this.mConfig.getCurve();
            float[] fArr = (float[]) curve.first;
            float[] fArr2 = (float[]) curve.second;
            int length = fArr2.length;
            float[] fArr3 = new float[length];
            for (int i = 0; i < length; i++) {
                fArr3[i] = this.mAdjustedNitsToBrightnessSpline.interpolate(fArr2[i]);
            }
            android.util.Pair<float[], float[]> adjustedCurve = getAdjustedCurve(fArr, fArr3, this.mUserLux, this.mUserBrightness, this.mAutoBrightnessAdjustment, this.mMaxGamma);
            float[] fArr4 = (float[]) adjustedCurve.first;
            float[] fArr5 = (float[]) adjustedCurve.second;
            int length2 = fArr5.length;
            float[] fArr6 = new float[length2];
            for (int i2 = 0; i2 < length2; i2++) {
                fArr6[i2] = this.mBrightnessToAdjustedNitsSpline.interpolate(fArr5[i2]);
            }
            this.mBrightnessSpline = android.util.Spline.createSpline(fArr4, fArr6);
        }

        private float getUnadjustedBrightness(float f) {
            android.util.Pair curve = this.mConfig.getCurve();
            return this.mAdjustedNitsToBrightnessSpline.interpolate(android.util.Spline.createSpline((float[]) curve.first, (float[]) curve.second).interpolate(f));
        }

        private float correctBrightness(float f, java.lang.String str, int i) {
            android.hardware.display.BrightnessCorrection correctionByCategory;
            android.hardware.display.BrightnessCorrection correctionByPackageName;
            if (str != null && (correctionByPackageName = this.mConfig.getCorrectionByPackageName(str)) != null) {
                return correctionByPackageName.apply(f);
            }
            if (i != -1 && (correctionByCategory = this.mConfig.getCorrectionByCategory(i)) != null) {
                return correctionByCategory.apply(f);
            }
            return f;
        }
    }
}
