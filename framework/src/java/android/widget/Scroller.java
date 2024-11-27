package android.widget;

/* loaded from: classes4.dex */
public class Scroller {
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 1.0f;
    private static final int FLING_MODE = 1;
    private static final float INFLEXION = 0.35f;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175f;
    private static final float P2 = 0.35000002f;
    private static final int SCROLL_MODE = 0;
    private static final float START_TENSION = 0.5f;
    private float mCurrVelocity;
    private int mCurrX;
    private int mCurrY;
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDistance;
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private float mFlingFriction;
    private boolean mFlywheel;
    private final android.view.animation.Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    private float mPhysicalCoeff;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;
    private static float DECELERATION_RATE = (float) (java.lang.Math.log(0.78d) / java.lang.Math.log(0.9d));
    private static final float[] SPLINE_POSITION = new float[101];
    private static final float[] SPLINE_TIME = new float[101];

    static {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11 = 0.0f;
        float f12 = 0.0f;
        for (int i = 0; i < 100; i++) {
            float f13 = i / 100.0f;
            float f14 = 1.0f;
            while (true) {
                f = 2.0f;
                f2 = ((f14 - f11) / 2.0f) + f11;
                f3 = 3.0f;
                f4 = 1.0f - f2;
                f5 = f2 * 3.0f * f4;
                f6 = f2 * f2 * f2;
                float f15 = (((f4 * P1) + (f2 * P2)) * f5) + f6;
                if (java.lang.Math.abs(f15 - f13) < 1.0E-5d) {
                    break;
                } else if (f15 > f13) {
                    f14 = f2;
                } else {
                    f11 = f2;
                }
            }
            SPLINE_POSITION[i] = (f5 * ((f4 * 0.5f) + f2)) + f6;
            float f16 = 1.0f;
            while (true) {
                f7 = ((f16 - f12) / f) + f12;
                f8 = 1.0f - f7;
                f9 = f7 * f3 * f8;
                f10 = f7 * f7 * f7;
                float f17 = (((f8 * 0.5f) + f7) * f9) + f10;
                if (java.lang.Math.abs(f17 - f13) < 1.0E-5d) {
                    break;
                }
                if (f17 > f13) {
                    f16 = f7;
                    f = 2.0f;
                    f3 = 3.0f;
                } else {
                    f12 = f7;
                    f = 2.0f;
                    f3 = 3.0f;
                }
            }
            SPLINE_TIME[i] = (f9 * ((f8 * P1) + (f7 * P2))) + f10;
        }
        float[] fArr = SPLINE_POSITION;
        SPLINE_TIME[100] = 1.0f;
        fArr[100] = 1.0f;
    }

    public Scroller(android.content.Context context) {
        this(context, null);
    }

    public Scroller(android.content.Context context, android.view.animation.Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public Scroller(android.content.Context context, android.view.animation.Interpolator interpolator, boolean z) {
        this.mFlingFriction = android.view.ViewConfiguration.getScrollFriction();
        this.mFinished = true;
        if (interpolator == null) {
            this.mInterpolator = new android.widget.Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = computeDeceleration(android.view.ViewConfiguration.getScrollFriction());
        this.mFlywheel = z;
        this.mPhysicalCoeff = computeDeceleration(0.84f);
    }

    public final void setFriction(float f) {
        this.mDeceleration = computeDeceleration(f);
        this.mFlingFriction = f;
    }

    private float computeDeceleration(float f) {
        return this.mPpi * 386.0878f * f;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void forceFinished(boolean z) {
        this.mFinished = z;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public float getCurrVelocity() {
        return this.mMode == 1 ? this.mCurrVelocity : this.mVelocity - ((this.mDeceleration * timePassed()) / 2000.0f);
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean computeScrollOffset() {
        float f;
        float f2;
        if (this.mFinished) {
            return false;
        }
        int currentAnimationTimeMillis = (int) (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (currentAnimationTimeMillis < this.mDuration) {
            switch (this.mMode) {
                case 0:
                    float interpolation = this.mInterpolator.getInterpolation(currentAnimationTimeMillis * this.mDurationReciprocal);
                    this.mCurrX = this.mStartX + java.lang.Math.round(this.mDeltaX * interpolation);
                    this.mCurrY = this.mStartY + java.lang.Math.round(interpolation * this.mDeltaY);
                    break;
                case 1:
                    float f3 = currentAnimationTimeMillis / this.mDuration;
                    int i = (int) (f3 * 100.0f);
                    if (i < 100) {
                        float f4 = i / 100.0f;
                        int i2 = i + 1;
                        float f5 = SPLINE_POSITION[i];
                        f2 = (SPLINE_POSITION[i2] - f5) / ((i2 / 100.0f) - f4);
                        f = f5 + ((f3 - f4) * f2);
                    } else {
                        f = 1.0f;
                        f2 = 0.0f;
                    }
                    this.mCurrVelocity = ((f2 * this.mDistance) / this.mDuration) * 1000.0f;
                    this.mCurrX = this.mStartX + java.lang.Math.round((this.mFinalX - this.mStartX) * f);
                    this.mCurrX = java.lang.Math.min(this.mCurrX, this.mMaxX);
                    this.mCurrX = java.lang.Math.max(this.mCurrX, this.mMinX);
                    this.mCurrY = this.mStartY + java.lang.Math.round(f * (this.mFinalY - this.mStartY));
                    this.mCurrY = java.lang.Math.min(this.mCurrY, this.mMaxY);
                    this.mCurrY = java.lang.Math.max(this.mCurrY, this.mMinY);
                    if (this.mCurrX == this.mFinalX && this.mCurrY == this.mFinalY) {
                        this.mFinished = true;
                        break;
                    }
                    break;
            }
        } else {
            this.mCurrX = this.mFinalX;
            this.mCurrY = this.mFinalY;
            this.mFinished = true;
        }
        return true;
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, 250);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = i5;
        this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        this.mFinalX = i + i3;
        this.mFinalY = i2 + i4;
        this.mDeltaX = i3;
        this.mDeltaY = i4;
        this.mDurationReciprocal = 1.0f / this.mDuration;
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mFlywheel && !this.mFinished) {
            float currVelocity = getCurrVelocity();
            float f = this.mFinalX - this.mStartX;
            float f2 = this.mFinalY - this.mStartY;
            float hypot = (float) java.lang.Math.hypot(f, f2);
            float f3 = (f / hypot) * currVelocity;
            float f4 = (f2 / hypot) * currVelocity;
            float f5 = i3;
            if (java.lang.Math.signum(f5) == java.lang.Math.signum(f3)) {
                float f6 = i4;
                if (java.lang.Math.signum(f6) == java.lang.Math.signum(f4)) {
                    i3 = (int) (f5 + f3);
                    i4 = (int) (f6 + f4);
                }
            }
        }
        this.mMode = 1;
        this.mFinished = false;
        float hypot2 = (float) java.lang.Math.hypot(i3, i4);
        this.mVelocity = hypot2;
        this.mDuration = getSplineFlingDuration(hypot2);
        this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        float f7 = hypot2 == 0.0f ? 1.0f : i3 / hypot2;
        float f8 = hypot2 != 0.0f ? i4 / hypot2 : 1.0f;
        double splineFlingDistance = getSplineFlingDistance(hypot2);
        this.mDistance = (int) (java.lang.Math.signum(hypot2) * splineFlingDistance);
        this.mMinX = i5;
        this.mMaxX = i6;
        this.mMinY = i7;
        this.mMaxY = i8;
        this.mFinalX = i + ((int) java.lang.Math.round(f7 * splineFlingDistance));
        this.mFinalX = java.lang.Math.min(this.mFinalX, this.mMaxX);
        this.mFinalX = java.lang.Math.max(this.mFinalX, this.mMinX);
        this.mFinalY = i2 + ((int) java.lang.Math.round(splineFlingDistance * f8));
        this.mFinalY = java.lang.Math.min(this.mFinalY, this.mMaxY);
        this.mFinalY = java.lang.Math.max(this.mFinalY, this.mMinY);
    }

    private double getSplineDeceleration(float f) {
        return java.lang.Math.log((java.lang.Math.abs(f) * INFLEXION) / (this.mFlingFriction * this.mPhysicalCoeff));
    }

    private int getSplineFlingDuration(float f) {
        return (int) (java.lang.Math.exp(getSplineDeceleration(f) / (DECELERATION_RATE - 1.0d)) * 1000.0d);
    }

    private double getSplineFlingDistance(float f) {
        return this.mFlingFriction * this.mPhysicalCoeff * java.lang.Math.exp((DECELERATION_RATE / (DECELERATION_RATE - 1.0d)) * getSplineDeceleration(f));
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public void extendDuration(int i) {
        this.mDuration = timePassed() + i;
        this.mDurationReciprocal = 1.0f / this.mDuration;
        this.mFinished = false;
    }

    public int timePassed() {
        return (int) (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public void setFinalX(int i) {
        this.mFinalX = i;
        this.mDeltaX = this.mFinalX - this.mStartX;
        this.mFinished = false;
    }

    public void setFinalY(int i) {
        this.mFinalY = i;
        this.mDeltaY = this.mFinalY - this.mStartY;
        this.mFinished = false;
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !this.mFinished && java.lang.Math.signum(f) == java.lang.Math.signum((float) (this.mFinalX - this.mStartX)) && java.lang.Math.signum(f2) == java.lang.Math.signum((float) (this.mFinalY - this.mStartY));
    }

    static class ViscousFluidInterpolator implements android.view.animation.Interpolator {
        private static final float VISCOUS_FLUID_NORMALIZE = 1.0f / viscousFluid(1.0f);
        private static final float VISCOUS_FLUID_OFFSET = 1.0f - (VISCOUS_FLUID_NORMALIZE * viscousFluid(1.0f));
        private static final float VISCOUS_FLUID_SCALE = 8.0f;

        ViscousFluidInterpolator() {
        }

        private static float viscousFluid(float f) {
            float f2 = f * 8.0f;
            if (f2 < 1.0f) {
                return f2 - (1.0f - ((float) java.lang.Math.exp(-f2)));
            }
            return 0.36787945f + ((1.0f - ((float) java.lang.Math.exp(1.0f - f2))) * 0.63212055f);
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float viscousFluid = VISCOUS_FLUID_NORMALIZE * viscousFluid(f);
            if (viscousFluid > 0.0f) {
                return viscousFluid + VISCOUS_FLUID_OFFSET;
            }
            return viscousFluid;
        }
    }
}
