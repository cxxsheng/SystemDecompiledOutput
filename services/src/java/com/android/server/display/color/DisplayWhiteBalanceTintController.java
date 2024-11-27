package com.android.server.display.color;

/* loaded from: classes.dex */
final class DisplayWhiteBalanceTintController extends com.android.server.display.color.ColorTemperatureTintController {
    private static final int COLORSPACE_MATRIX_LENGTH = 9;
    private static final int NUM_DISPLAY_PRIMARIES_VALS = 12;
    private static final int NUM_VALUES_PER_PRIMARY = 3;
    private int mAppliedCct;
    private com.android.server.display.color.CctEvaluator mCctEvaluator;
    private float[] mChromaticAdaptationMatrix;

    @com.android.internal.annotations.VisibleForTesting
    int mCurrentColorTemperature;
    private float[] mCurrentColorTemperatureXYZ;

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.ColorSpace.Rgb mDisplayColorSpaceRGB;
    private final com.android.server.display.feature.DisplayManagerFlags mDisplayManagerFlags;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private int mDisplayNominalWhiteCct;
    private java.lang.Boolean mIsAvailable;
    private int mTargetCct;
    private int mTemperatureDefault;

    @com.android.internal.annotations.VisibleForTesting
    int mTemperatureMax;

    @com.android.internal.annotations.VisibleForTesting
    int mTemperatureMin;
    private long mTransitionDuration;
    private long mTransitionDurationDecrease;
    private long mTransitionDurationIncrease;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    float[] mDisplayNominalWhiteXYZ = new float[3];

    @com.android.internal.annotations.VisibleForTesting
    boolean mSetUp = false;
    private final float[] mMatrixDisplayWhiteBalance = new float[16];
    private boolean mIsAllowed = true;

    DisplayWhiteBalanceTintController(android.hardware.display.DisplayManagerInternal displayManagerInternal, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mDisplayManagerFlags = displayManagerFlags;
    }

    @Override // com.android.server.display.color.TintController
    public void setUp(android.content.Context context, boolean z) {
        this.mSetUp = false;
        android.content.res.Resources resources = context.getResources();
        setAllowed(resources.getBoolean(android.R.bool.config_displayWhiteBalanceEnabledDefault));
        android.graphics.ColorSpace.Rgb displayColorSpaceFromSurfaceControl = getDisplayColorSpaceFromSurfaceControl();
        if (displayColorSpaceFromSurfaceControl == null) {
            android.util.Slog.w("ColorDisplayService", "Failed to get display color space from SurfaceControl, trying res");
            displayColorSpaceFromSurfaceControl = getDisplayColorSpaceFromResources(resources);
            if (displayColorSpaceFromSurfaceControl == null) {
                android.util.Slog.e("ColorDisplayService", "Failed to get display color space from resources");
                return;
            }
        }
        if (!isColorMatrixValid(displayColorSpaceFromSurfaceControl.getTransform())) {
            android.util.Slog.e("ColorDisplayService", "Invalid display color space RGB-to-XYZ transform");
            return;
        }
        if (!isColorMatrixValid(displayColorSpaceFromSurfaceControl.getInverseTransform())) {
            android.util.Slog.e("ColorDisplayService", "Invalid display color space XYZ-to-RGB transform");
            return;
        }
        java.lang.String[] stringArray = resources.getStringArray(android.R.array.config_displayWhiteBalanceDecreaseThresholds);
        float[] fArr = new float[3];
        for (int i = 0; i < stringArray.length; i++) {
            fArr[i] = java.lang.Float.parseFloat(stringArray[i]);
        }
        int integer = resources.getInteger(android.R.integer.config_displayWhiteBalanceColorTemperatureMax);
        int integer2 = resources.getInteger(android.R.integer.config_displayWhiteBalanceBrightnessSensorRate);
        if (integer2 <= 0) {
            android.util.Slog.e("ColorDisplayService", "Display white balance minimum temperature must be greater than 0");
            return;
        }
        int integer3 = resources.getInteger(android.R.integer.config_displayWhiteBalanceBrightnessFilterHorizon);
        if (integer3 < integer2) {
            android.util.Slog.e("ColorDisplayService", "Display white balance max temp must be greater or equal to min");
            return;
        }
        int integer4 = resources.getInteger(android.R.integer.config_deviceStateConcurrentRearDisplay);
        this.mTransitionDuration = resources.getInteger(android.R.integer.config_displayWhiteBalanceColorTemperatureSensorRate);
        if (!this.mDisplayManagerFlags.isAdaptiveTone2Enabled()) {
            this.mTransitionDurationDecrease = this.mTransitionDuration;
            this.mTransitionDurationIncrease = this.mTransitionDuration;
        } else {
            this.mTransitionDurationIncrease = resources.getInteger(android.R.integer.config_displayWhiteBalanceDisplayNominalWhiteCct);
            this.mTransitionDurationDecrease = resources.getInteger(android.R.integer.config_displayWhiteBalanceDecreaseDebounce);
        }
        int[] intArray = resources.getIntArray(android.R.array.config_displayWhiteBalanceDisplayNominalWhite);
        int[] intArray2 = resources.getIntArray(android.R.array.config_displayWhiteBalanceDisplayPrimaries);
        synchronized (this.mLock) {
            this.mDisplayColorSpaceRGB = displayColorSpaceFromSurfaceControl;
            this.mDisplayNominalWhiteXYZ = fArr;
            this.mDisplayNominalWhiteCct = integer;
            this.mTargetCct = this.mDisplayNominalWhiteCct;
            this.mAppliedCct = this.mDisplayNominalWhiteCct;
            this.mTemperatureMin = integer2;
            this.mTemperatureMax = integer3;
            this.mTemperatureDefault = integer4;
            this.mSetUp = true;
            this.mCctEvaluator = new com.android.server.display.color.CctEvaluator(this.mTemperatureMin, this.mTemperatureMax, intArray, intArray2);
        }
        setMatrix(this.mTemperatureDefault);
    }

    @Override // com.android.server.display.color.TintController
    public float[] getMatrix() {
        if (!this.mSetUp || !isActivated()) {
            return com.android.server.display.color.ColorDisplayService.MATRIX_IDENTITY;
        }
        computeMatrixForCct(this.mAppliedCct);
        return this.mMatrixDisplayWhiteBalance;
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    public int getTargetCct() {
        return this.mTargetCct;
    }

    @android.annotation.NonNull
    private static float[] mul3x3(@android.annotation.NonNull float[] fArr, @android.annotation.NonNull float[] fArr2) {
        return new float[]{(fArr[0] * fArr2[0]) + (fArr[3] * fArr2[1]) + (fArr[6] * fArr2[2]), (fArr[1] * fArr2[0]) + (fArr[4] * fArr2[1]) + (fArr[7] * fArr2[2]), (fArr[2] * fArr2[0]) + (fArr[5] * fArr2[1]) + (fArr[8] * fArr2[2]), (fArr[0] * fArr2[3]) + (fArr[3] * fArr2[4]) + (fArr[6] * fArr2[5]), (fArr[1] * fArr2[3]) + (fArr[4] * fArr2[4]) + (fArr[7] * fArr2[5]), (fArr[2] * fArr2[3]) + (fArr[5] * fArr2[4]) + (fArr[8] * fArr2[5]), (fArr[0] * fArr2[6]) + (fArr[3] * fArr2[7]) + (fArr[6] * fArr2[8]), (fArr[1] * fArr2[6]) + (fArr[4] * fArr2[7]) + (fArr[7] * fArr2[8]), (fArr[2] * fArr2[6]) + (fArr[5] * fArr2[7]) + (fArr[8] * fArr2[8])};
    }

    @Override // com.android.server.display.color.TintController
    public void setMatrix(int i) {
        setTargetCct(i);
        computeMatrixForCct(this.mTargetCct);
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    public void setTargetCct(int i) {
        if (!this.mSetUp) {
            android.util.Slog.w("ColorDisplayService", "Can't set display white balance temperature: uninitialized");
            return;
        }
        if (i < this.mTemperatureMin) {
            android.util.Slog.w("ColorDisplayService", "Requested display color temperature is below allowed minimum");
            this.mTargetCct = this.mTemperatureMin;
        } else if (i > this.mTemperatureMax) {
            android.util.Slog.w("ColorDisplayService", "Requested display color temperature is above allowed maximum");
            this.mTargetCct = this.mTemperatureMax;
        } else {
            this.mTargetCct = i;
        }
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    public int getDisabledCct() {
        return this.mDisplayNominalWhiteCct;
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    public float[] computeMatrixForCct(int i) {
        float[] fArr;
        if (!this.mSetUp || i == 0) {
            android.util.Slog.w("ColorDisplayService", "Couldn't compute matrix for cct=" + i);
            return com.android.server.display.color.ColorDisplayService.MATRIX_IDENTITY;
        }
        synchronized (this.mLock) {
            try {
                this.mCurrentColorTemperature = i;
                if (i == this.mDisplayNominalWhiteCct && !isActivated()) {
                    android.opengl.Matrix.setIdentityM(this.mMatrixDisplayWhiteBalance, 0);
                } else {
                    computeMatrixForCctLocked(i);
                }
                android.util.Slog.d("ColorDisplayService", "computeDisplayWhiteBalanceMatrix: cct =" + i + " matrix =" + com.android.server.display.color.TintController.matrixToString(this.mMatrixDisplayWhiteBalance, 16));
                fArr = this.mMatrixDisplayWhiteBalance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return fArr;
    }

    private void computeMatrixForCctLocked(int i) {
        this.mCurrentColorTemperatureXYZ = android.graphics.ColorSpace.cctToXyz(i);
        this.mChromaticAdaptationMatrix = android.graphics.ColorSpace.chromaticAdaptation(android.graphics.ColorSpace.Adaptation.BRADFORD, this.mDisplayNominalWhiteXYZ, this.mCurrentColorTemperatureXYZ);
        float[] mul3x3 = mul3x3(this.mDisplayColorSpaceRGB.getInverseTransform(), mul3x3(this.mChromaticAdaptationMatrix, this.mDisplayColorSpaceRGB.getTransform()));
        float max = java.lang.Math.max(java.lang.Math.max(mul3x3[0] + mul3x3[3] + mul3x3[6], mul3x3[1] + mul3x3[4] + mul3x3[7]), mul3x3[2] + mul3x3[5] + mul3x3[8]);
        android.opengl.Matrix.setIdentityM(this.mMatrixDisplayWhiteBalance, 0);
        for (int i2 = 0; i2 < mul3x3.length; i2++) {
            mul3x3[i2] = mul3x3[i2] / max;
            if (!isColorMatrixCoeffValid(mul3x3[i2])) {
                android.util.Slog.e("ColorDisplayService", "Invalid DWB color matrix");
                return;
            }
        }
        java.lang.System.arraycopy(mul3x3, 0, this.mMatrixDisplayWhiteBalance, 0, 3);
        java.lang.System.arraycopy(mul3x3, 3, this.mMatrixDisplayWhiteBalance, 4, 3);
        java.lang.System.arraycopy(mul3x3, 6, this.mMatrixDisplayWhiteBalance, 8, 3);
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    int getAppliedCct() {
        return this.mAppliedCct;
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    void setAppliedCct(int i) {
        this.mAppliedCct = i;
    }

    @Override // com.android.server.display.color.ColorTemperatureTintController
    @android.annotation.Nullable
    com.android.server.display.color.CctEvaluator getEvaluator() {
        return this.mCctEvaluator;
    }

    @Override // com.android.server.display.color.TintController
    public int getLevel() {
        return 125;
    }

    @Override // com.android.server.display.color.TintController
    public boolean isAvailable(android.content.Context context) {
        if (this.mIsAvailable == null) {
            this.mIsAvailable = java.lang.Boolean.valueOf(android.hardware.display.ColorDisplayManager.isDisplayWhiteBalanceAvailable(context));
        }
        return this.mIsAvailable.booleanValue();
    }

    @Override // com.android.server.display.color.TintController
    public long getTransitionDurationMilliseconds() {
        return this.mTransitionDuration;
    }

    @Override // com.android.server.display.color.TintController
    public long getTransitionDurationMilliseconds(boolean z) {
        return z ? this.mTransitionDurationIncrease : this.mTransitionDurationDecrease;
    }

    @Override // com.android.server.display.color.TintController
    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("    mSetUp = " + this.mSetUp);
                if (this.mSetUp) {
                    printWriter.println("    mTemperatureMin = " + this.mTemperatureMin);
                    printWriter.println("    mTemperatureMax = " + this.mTemperatureMax);
                    printWriter.println("    mTemperatureDefault = " + this.mTemperatureDefault);
                    printWriter.println("    mDisplayNominalWhiteCct = " + this.mDisplayNominalWhiteCct);
                    printWriter.println("    mCurrentColorTemperature = " + this.mCurrentColorTemperature);
                    printWriter.println("    mTargetCct = " + this.mTargetCct);
                    printWriter.println("    mAppliedCct = " + this.mAppliedCct);
                    printWriter.println("    mCurrentColorTemperatureXYZ = " + com.android.server.display.color.TintController.matrixToString(this.mCurrentColorTemperatureXYZ, 3));
                    printWriter.println("    mDisplayColorSpaceRGB RGB-to-XYZ = " + com.android.server.display.color.TintController.matrixToString(this.mDisplayColorSpaceRGB.getTransform(), 3));
                    printWriter.println("    mChromaticAdaptationMatrix = " + com.android.server.display.color.TintController.matrixToString(this.mChromaticAdaptationMatrix, 3));
                    printWriter.println("    mDisplayColorSpaceRGB XYZ-to-RGB = " + com.android.server.display.color.TintController.matrixToString(this.mDisplayColorSpaceRGB.getInverseTransform(), 3));
                    printWriter.println("    mMatrixDisplayWhiteBalance = " + com.android.server.display.color.TintController.matrixToString(this.mMatrixDisplayWhiteBalance, 4));
                    printWriter.println("    mIsAllowed = " + this.mIsAllowed);
                    printWriter.println("    mTransitionDuration = " + this.mTransitionDuration);
                    printWriter.println("    mTransitionDurationIncrease = " + this.mTransitionDurationIncrease);
                    printWriter.println("    mTransitionDurationDecrease = " + this.mTransitionDurationDecrease);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getLuminance() {
        synchronized (this.mLock) {
            try {
                if (this.mChromaticAdaptationMatrix == null || this.mChromaticAdaptationMatrix.length != 9) {
                    return -1.0f;
                }
                return 1.0f / ((this.mChromaticAdaptationMatrix[1] + this.mChromaticAdaptationMatrix[4]) + this.mChromaticAdaptationMatrix[7]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAllowed(boolean z) {
        this.mIsAllowed = z;
    }

    public boolean isAllowed() {
        return this.mIsAllowed;
    }

    private android.graphics.ColorSpace.Rgb makeRgbColorSpaceFromXYZ(float[] fArr, float[] fArr2) {
        return new android.graphics.ColorSpace.Rgb("Display Color Space", fArr, fArr2, 2.200000047683716d);
    }

    private android.graphics.ColorSpace.Rgb getDisplayColorSpaceFromSurfaceControl() {
        android.view.SurfaceControl.DisplayPrimaries displayNativePrimaries = this.mDisplayManagerInternal.getDisplayNativePrimaries(0);
        if (displayNativePrimaries == null || displayNativePrimaries.red == null || displayNativePrimaries.green == null || displayNativePrimaries.blue == null || displayNativePrimaries.white == null) {
            return null;
        }
        return makeRgbColorSpaceFromXYZ(new float[]{displayNativePrimaries.red.X, displayNativePrimaries.red.Y, displayNativePrimaries.red.Z, displayNativePrimaries.green.X, displayNativePrimaries.green.Y, displayNativePrimaries.green.Z, displayNativePrimaries.blue.X, displayNativePrimaries.blue.Y, displayNativePrimaries.blue.Z}, new float[]{displayNativePrimaries.white.X, displayNativePrimaries.white.Y, displayNativePrimaries.white.Z});
    }

    private android.graphics.ColorSpace.Rgb getDisplayColorSpaceFromResources(android.content.res.Resources resources) {
        java.lang.String[] stringArray = resources.getStringArray(android.R.array.config_displayWhiteBalanceDisplayColorTemperatures);
        float[] fArr = new float[9];
        float[] fArr2 = new float[3];
        for (int i = 0; i < 9; i++) {
            fArr[i] = java.lang.Float.parseFloat(stringArray[i]);
        }
        for (int i2 = 0; i2 < 3; i2++) {
            fArr2[i2] = java.lang.Float.parseFloat(stringArray[9 + i2]);
        }
        return makeRgbColorSpaceFromXYZ(fArr, fArr2);
    }

    private boolean isColorMatrixCoeffValid(float f) {
        return (java.lang.Float.isNaN(f) || java.lang.Float.isInfinite(f)) ? false : true;
    }

    private boolean isColorMatrixValid(float[] fArr) {
        if (fArr == null || fArr.length != 9) {
            return false;
        }
        for (float f : fArr) {
            if (!isColorMatrixCoeffValid(f)) {
                return false;
            }
        }
        return true;
    }
}
