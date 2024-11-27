package android.content.res;

/* loaded from: classes.dex */
public class CompatibilityInfo implements android.os.Parcelable {
    private static final int ALWAYS_NEEDS_COMPAT = 2;
    public static final int DEFAULT_NORMAL_SHORT_DIMENSION = 320;
    private static final int HAS_OVERRIDE_SCALING = 32;
    public static final float MAXIMUM_ASPECT_RATIO = 1.7791667f;
    private static final int NEEDS_COMPAT_RES = 16;
    private static final int NEEDS_SCREEN_COMPAT = 8;
    private static final int NEVER_NEEDS_COMPAT = 4;
    private static final int SCALING_REQUIRED = 1;
    public final int applicationDensity;
    public final float applicationDensityInvertedScale;
    public final float applicationDensityScale;
    public final float applicationInvertedScale;
    public final float applicationScale;
    private final int mCompatibilityFlags;
    public static final android.content.res.CompatibilityInfo DEFAULT_COMPATIBILITY_INFO = new android.content.res.CompatibilityInfo() { // from class: android.content.res.CompatibilityInfo.1
    };
    private static float sOverrideInvertedScale = 1.0f;
    private static float sOverrideDensityInvertScale = 1.0f;
    public static final android.os.Parcelable.Creator<android.content.res.CompatibilityInfo> CREATOR = new android.os.Parcelable.Creator<android.content.res.CompatibilityInfo>() { // from class: android.content.res.CompatibilityInfo.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.CompatibilityInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.res.CompatibilityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.CompatibilityInfo[] newArray(int i) {
            return new android.content.res.CompatibilityInfo[i];
        }
    };

    @java.lang.Deprecated
    public CompatibilityInfo(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, boolean z) {
        this(applicationInfo, i, i2, z, 1.0f);
    }

    public CompatibilityInfo(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, boolean z, float f) {
        this(applicationInfo, i, i2, z, f, f);
    }

    public CompatibilityInfo(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, boolean z, float f, float f2) {
        int i3;
        int i4;
        int i5;
        boolean z2;
        int i6 = 0;
        if (applicationInfo.targetSdkVersion >= 26) {
            i3 = 0;
        } else {
            i3 = 16;
        }
        if (f != 1.0f || f2 != 1.0f) {
            this.applicationScale = f;
            this.applicationInvertedScale = 1.0f / f;
            this.applicationDensityScale = f2;
            this.applicationDensityInvertedScale = 1.0f / f2;
            this.applicationDensity = (int) ((android.util.DisplayMetrics.DENSITY_DEVICE_STABLE * this.applicationDensityInvertedScale) + 0.5f);
            this.mCompatibilityFlags = 36;
            return;
        }
        if (applicationInfo.requiresSmallestWidthDp != 0 || applicationInfo.compatibleWidthLimitDp != 0 || applicationInfo.largestWidthLimitDp != 0) {
            if (applicationInfo.requiresSmallestWidthDp != 0) {
                i4 = applicationInfo.requiresSmallestWidthDp;
            } else {
                i4 = applicationInfo.compatibleWidthLimitDp;
            }
            i4 = i4 == 0 ? applicationInfo.largestWidthLimitDp : i4;
            int i7 = applicationInfo.compatibleWidthLimitDp != 0 ? applicationInfo.compatibleWidthLimitDp : i4;
            i7 = i7 < i4 ? i4 : i7;
            int i8 = applicationInfo.largestWidthLimitDp;
            if (i4 > 320) {
                i3 |= 4;
            } else if (i8 != 0 && i2 > i8) {
                i3 |= 10;
            } else if (i7 >= i2) {
                i3 |= 4;
            } else if (z) {
                i3 |= 8;
            }
            this.applicationDensity = android.util.DisplayMetrics.DENSITY_DEVICE;
            this.applicationScale = 1.0f;
            this.applicationInvertedScale = 1.0f;
            this.applicationDensityScale = 1.0f;
            this.applicationDensityInvertedScale = 1.0f;
            i5 = i3;
        } else {
            boolean z3 = true;
            if ((applicationInfo.flags & 2048) == 0) {
                z2 = false;
            } else if (z) {
                z2 = true;
                i6 = 8;
            } else {
                i6 = 42;
                z2 = true;
            }
            if ((applicationInfo.flags & 524288) != 0) {
                if (z) {
                    z2 = true;
                } else {
                    i6 |= 34;
                    z2 = true;
                }
            }
            if ((applicationInfo.flags & 4096) == 0) {
                z3 = z2;
            } else {
                i6 |= 2;
            }
            i6 = z ? i6 & (-3) : i6;
            i5 = i3 | 8;
            switch (i & 15) {
                case 3:
                    i5 = (i6 & 8) != 0 ? i5 & (-9) : i5;
                    if ((applicationInfo.flags & 2048) != 0) {
                        i5 |= 4;
                        break;
                    }
                    break;
                case 4:
                    i5 = (i6 & 32) != 0 ? i5 & (-9) : i5;
                    if ((applicationInfo.flags & 524288) != 0) {
                        i5 |= 4;
                        break;
                    }
                    break;
            }
            if ((i & 268435456) != 0) {
                if ((i6 & 2) != 0) {
                    i5 &= -9;
                } else if (!z3) {
                    i5 |= 2;
                }
            } else {
                i5 = (i5 & (-9)) | 4;
            }
            if ((applicationInfo.flags & 8192) != 0) {
                this.applicationDensity = android.util.DisplayMetrics.DENSITY_DEVICE;
                this.applicationScale = 1.0f;
                this.applicationInvertedScale = 1.0f;
                this.applicationDensityScale = 1.0f;
                this.applicationDensityInvertedScale = 1.0f;
            } else {
                this.applicationDensity = 160;
                this.applicationScale = android.util.DisplayMetrics.DENSITY_DEVICE / 160.0f;
                this.applicationInvertedScale = 1.0f / this.applicationScale;
                this.applicationDensityScale = android.util.DisplayMetrics.DENSITY_DEVICE / 160.0f;
                this.applicationDensityInvertedScale = 1.0f / this.applicationDensityScale;
                i5 |= 1;
            }
        }
        this.mCompatibilityFlags = i5;
    }

    private CompatibilityInfo(int i, int i2, float f, float f2) {
        this.mCompatibilityFlags = i;
        this.applicationDensity = i2;
        this.applicationScale = f;
        this.applicationInvertedScale = f2;
        this.applicationDensityScale = android.util.DisplayMetrics.DENSITY_DEVICE_STABLE / i2;
        this.applicationDensityInvertedScale = 1.0f / this.applicationDensityScale;
    }

    private CompatibilityInfo() {
        this(4, android.util.DisplayMetrics.DENSITY_DEVICE, 1.0f, 1.0f);
    }

    public boolean isScalingRequired() {
        return (this.mCompatibilityFlags & 1) != 0;
    }

    public boolean hasOverrideScaling() {
        return (this.mCompatibilityFlags & 32) != 0;
    }

    public boolean supportsScreen() {
        return (this.mCompatibilityFlags & 8) == 0;
    }

    public boolean neverSupportsScreen() {
        return (this.mCompatibilityFlags & 2) != 0;
    }

    public boolean alwaysSupportsScreen() {
        return (this.mCompatibilityFlags & 4) != 0;
    }

    public boolean needsCompatResources() {
        return (this.mCompatibilityFlags & 16) != 0;
    }

    public android.content.res.CompatibilityInfo.Translator getTranslator() {
        if ((this.mCompatibilityFlags & 1) != 0) {
            return new android.content.res.CompatibilityInfo.Translator(this);
        }
        return null;
    }

    public class Translator {
        public final float applicationInvertedScale;
        public final float applicationScale;
        private android.graphics.Rect mContentInsetsBuffer;
        private android.graphics.Region mTouchableAreaBuffer;
        private android.graphics.Rect mVisibleInsetsBuffer;

        Translator(float f, float f2) {
            this.mContentInsetsBuffer = null;
            this.mVisibleInsetsBuffer = null;
            this.mTouchableAreaBuffer = null;
            this.applicationScale = f;
            this.applicationInvertedScale = f2;
        }

        Translator(android.content.res.CompatibilityInfo compatibilityInfo) {
            this(compatibilityInfo.applicationScale, compatibilityInfo.applicationInvertedScale);
        }

        public void translateRegionInWindowToScreen(android.graphics.Region region) {
            region.scale(this.applicationScale);
        }

        public void translateCanvas(android.graphics.Canvas canvas) {
            if (this.applicationScale == 1.5f) {
                canvas.translate(0.0026143792f, 0.0026143792f);
            }
            canvas.scale(this.applicationScale, this.applicationScale);
        }

        public void translateEventInScreenToAppWindow(android.view.MotionEvent motionEvent) {
            motionEvent.scale(this.applicationInvertedScale);
        }

        public void translateWindowLayout(android.view.WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }

        public float translateLengthInAppWindowToScreen(float f) {
            return f * this.applicationScale;
        }

        public void translateRectInAppWindowToScreen(android.graphics.Rect rect) {
            rect.scale(this.applicationScale);
        }

        public void translateRectInScreenToAppWindow(android.graphics.Rect rect) {
            if (rect == null) {
                return;
            }
            rect.scale(this.applicationInvertedScale);
        }

        public void translateInsetsStateInScreenToAppWindow(android.view.InsetsState insetsState) {
            insetsState.scale(this.applicationInvertedScale);
        }

        public void translateSourceControlsInScreenToAppWindow(android.view.InsetsSourceControl[] insetsSourceControlArr) {
            if (insetsSourceControlArr == null) {
                return;
            }
            float f = this.applicationInvertedScale;
            if (f == 1.0f) {
                return;
            }
            for (android.view.InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    android.graphics.Insets insetsHint = insetsSourceControl.getInsetsHint();
                    insetsSourceControl.setInsetsHint((int) (insetsHint.left * f), (int) (insetsHint.top * f), (int) (insetsHint.right * f), (int) (insetsHint.bottom * f));
                }
            }
        }

        public void translatePointInScreenToAppWindow(android.graphics.PointF pointF) {
            float f = this.applicationInvertedScale;
            if (f != 1.0f) {
                pointF.x *= f;
                pointF.y *= f;
            }
        }

        public void translateLayoutParamsInAppWindowToScreen(android.view.WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }

        public android.graphics.Rect getTranslatedContentInsets(android.graphics.Rect rect) {
            if (this.mContentInsetsBuffer == null) {
                this.mContentInsetsBuffer = new android.graphics.Rect();
            }
            this.mContentInsetsBuffer.set(rect);
            translateRectInAppWindowToScreen(this.mContentInsetsBuffer);
            return this.mContentInsetsBuffer;
        }

        public android.graphics.Rect getTranslatedVisibleInsets(android.graphics.Rect rect) {
            if (this.mVisibleInsetsBuffer == null) {
                this.mVisibleInsetsBuffer = new android.graphics.Rect();
            }
            this.mVisibleInsetsBuffer.set(rect);
            translateRectInAppWindowToScreen(this.mVisibleInsetsBuffer);
            return this.mVisibleInsetsBuffer;
        }

        public android.graphics.Region getTranslatedTouchableArea(android.graphics.Region region) {
            if (this.mTouchableAreaBuffer == null) {
                this.mTouchableAreaBuffer = new android.graphics.Region();
            }
            this.mTouchableAreaBuffer.set(region);
            this.mTouchableAreaBuffer.scale(this.applicationScale);
            return this.mTouchableAreaBuffer;
        }
    }

    public void applyDisplayMetricsIfNeeded(android.util.DisplayMetrics displayMetrics, boolean z) {
        if (hasOverrideScale()) {
            scaleDisplayMetrics(sOverrideInvertedScale, sOverrideDensityInvertScale, displayMetrics, z);
        } else if (!equals(DEFAULT_COMPATIBILITY_INFO)) {
            applyToDisplayMetrics(displayMetrics);
        }
    }

    public void applyToDisplayMetrics(android.util.DisplayMetrics displayMetrics) {
        if (hasOverrideScale()) {
            return;
        }
        if (!supportsScreen()) {
            computeCompatibleScaling(displayMetrics, displayMetrics);
        } else {
            displayMetrics.widthPixels = displayMetrics.noncompatWidthPixels;
            displayMetrics.heightPixels = displayMetrics.noncompatHeightPixels;
        }
        if (isScalingRequired()) {
            scaleDisplayMetrics(this.applicationInvertedScale, this.applicationDensityInvertedScale, displayMetrics, true);
        }
    }

    private static void scaleDisplayMetrics(float f, float f2, android.util.DisplayMetrics displayMetrics, boolean z) {
        displayMetrics.density = displayMetrics.noncompatDensity * f2;
        displayMetrics.densityDpi = (int) ((displayMetrics.noncompatDensityDpi * f2) + 0.5f);
        displayMetrics.scaledDensity = displayMetrics.noncompatScaledDensity * f2;
        displayMetrics.xdpi = displayMetrics.noncompatXdpi * f2;
        displayMetrics.ydpi = displayMetrics.noncompatYdpi * f2;
        if (z) {
            displayMetrics.widthPixels = (int) ((displayMetrics.widthPixels * f) + 0.5f);
            displayMetrics.heightPixels = (int) ((displayMetrics.heightPixels * f) + 0.5f);
        }
    }

    public void applyToConfiguration(int i, android.content.res.Configuration configuration) {
        if (hasOverrideScale()) {
            return;
        }
        if (!supportsScreen()) {
            configuration.screenLayout = (configuration.screenLayout & (-16)) | 2;
            configuration.screenWidthDp = configuration.compatScreenWidthDp;
            configuration.screenHeightDp = configuration.compatScreenHeightDp;
            configuration.smallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        }
        configuration.densityDpi = i;
        if (isScalingRequired()) {
            scaleConfiguration(this.applicationInvertedScale, this.applicationDensityInvertedScale, configuration);
        }
    }

    public static void scaleConfiguration(float f, android.content.res.Configuration configuration) {
        scaleConfiguration(f, f, configuration);
    }

    public static void scaleConfiguration(float f, float f2, android.content.res.Configuration configuration) {
        configuration.densityDpi = (int) ((configuration.densityDpi * f2) + 0.5f);
        configuration.windowConfiguration.scale(f);
    }

    public static void applyOverrideScaleIfNeeded(android.content.res.Configuration configuration) {
        if (hasOverrideScale()) {
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, configuration);
        }
    }

    public static void applyOverrideScaleIfNeeded(android.util.MergedConfiguration mergedConfiguration) {
        if (hasOverrideScale()) {
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getGlobalConfiguration());
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getOverrideConfiguration());
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getMergedConfiguration());
        }
    }

    private static boolean hasOverrideScale() {
        return (sOverrideInvertedScale == 1.0f && sOverrideDensityInvertScale == 1.0f) ? false : true;
    }

    public static void setOverrideInvertedScale(float f) {
        setOverrideInvertedScale(f, f);
    }

    public static void setOverrideInvertedScale(float f, float f2) {
        sOverrideInvertedScale = f;
        sOverrideDensityInvertScale = f2;
    }

    public static float getOverrideInvertedScale() {
        return sOverrideInvertedScale;
    }

    public static float getOverrideDensityInvertedScale() {
        return sOverrideDensityInvertScale;
    }

    public static float computeCompatibleScaling(android.util.DisplayMetrics displayMetrics, android.util.DisplayMetrics displayMetrics2) {
        int i;
        int i2;
        int i3 = displayMetrics.noncompatWidthPixels;
        int i4 = displayMetrics.noncompatHeightPixels;
        if (i3 < i4) {
            i2 = i3;
            i = i4;
        } else {
            i = i3;
            i2 = i4;
        }
        int i5 = (int) ((displayMetrics.density * 320.0f) + 0.5f);
        float f = i / i2;
        if (f > 1.7791667f) {
            f = 1.7791667f;
        }
        int i6 = (int) ((i5 * f) + 0.5f);
        if (i3 >= i4) {
            i6 = i5;
            i5 = i6;
        }
        float f2 = i3 / i5;
        float f3 = i4 / i6;
        if (f2 >= f3) {
            f2 = f3;
        }
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        if (displayMetrics2 != null) {
            displayMetrics2.widthPixels = i5;
            displayMetrics2.heightPixels = i6;
        }
        return f2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            android.content.res.CompatibilityInfo compatibilityInfo = (android.content.res.CompatibilityInfo) obj;
            if (this.mCompatibilityFlags != compatibilityInfo.mCompatibilityFlags || this.applicationDensity != compatibilityInfo.applicationDensity || this.applicationScale != compatibilityInfo.applicationScale || this.applicationInvertedScale != compatibilityInfo.applicationInvertedScale || this.applicationDensityScale != compatibilityInfo.applicationDensityScale) {
                return false;
            }
            if (this.applicationDensityInvertedScale == compatibilityInfo.applicationDensityInvertedScale) {
                return true;
            }
            return false;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("{");
        sb.append(this.applicationDensity);
        sb.append("dpi");
        if (isScalingRequired()) {
            sb.append(" ");
            sb.append(this.applicationScale);
            sb.append("x");
        }
        if (hasOverrideScaling()) {
            sb.append(" overrideInvScale=");
            sb.append(this.applicationInvertedScale);
            sb.append(" overrideDensityInvScale=");
            sb.append(this.applicationDensityInvertedScale);
        }
        if (!supportsScreen()) {
            sb.append(" resizing");
        }
        if (neverSupportsScreen()) {
            sb.append(" never-compat");
        }
        if (alwaysSupportsScreen()) {
            sb.append(" always-compat");
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return ((((((((((527 + this.mCompatibilityFlags) * 31) + this.applicationDensity) * 31) + java.lang.Float.floatToIntBits(this.applicationScale)) * 31) + java.lang.Float.floatToIntBits(this.applicationInvertedScale)) * 31) + java.lang.Float.floatToIntBits(this.applicationDensityScale)) * 31) + java.lang.Float.floatToIntBits(this.applicationDensityInvertedScale);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCompatibilityFlags);
        parcel.writeInt(this.applicationDensity);
        parcel.writeFloat(this.applicationScale);
        parcel.writeFloat(this.applicationInvertedScale);
        parcel.writeFloat(this.applicationDensityScale);
        parcel.writeFloat(this.applicationDensityInvertedScale);
    }

    private CompatibilityInfo(android.os.Parcel parcel) {
        this.mCompatibilityFlags = parcel.readInt();
        this.applicationDensity = parcel.readInt();
        this.applicationScale = parcel.readFloat();
        this.applicationInvertedScale = parcel.readFloat();
        this.applicationDensityScale = parcel.readFloat();
        this.applicationDensityInvertedScale = parcel.readFloat();
    }

    public static final class CompatScale {
        public final float mDensityScaleFactor;
        public final float mScaleFactor;

        public CompatScale(float f) {
            this(f, f);
        }

        public CompatScale(float f, float f2) {
            this.mScaleFactor = f;
            this.mDensityScaleFactor = f2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.content.res.CompatibilityInfo.CompatScale)) {
                return false;
            }
            try {
                android.content.res.CompatibilityInfo.CompatScale compatScale = (android.content.res.CompatibilityInfo.CompatScale) obj;
                if (this.mScaleFactor != compatScale.mScaleFactor) {
                    return false;
                }
                return this.mDensityScaleFactor == compatScale.mDensityScaleFactor;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("mScaleFactor= ");
            sb.append(this.mScaleFactor);
            sb.append(" mDensityScaleFactor= ");
            sb.append(this.mDensityScaleFactor);
            return sb.toString();
        }

        public int hashCode() {
            return ((527 + java.lang.Float.floatToIntBits(this.mScaleFactor)) * 31) + java.lang.Float.floatToIntBits(this.mDensityScaleFactor);
        }
    }
}
