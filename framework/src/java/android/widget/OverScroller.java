package android.widget;

/* loaded from: classes4.dex */
public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final int SCROLL_MODE = 0;
    private final boolean mFlywheel;
    private android.view.animation.Interpolator mInterpolator;
    private int mMode;
    private final android.widget.OverScroller.SplineOverScroller mScrollerX;
    private final android.widget.OverScroller.SplineOverScroller mScrollerY;

    public OverScroller(android.content.Context context) {
        this(context, null);
    }

    public OverScroller(android.content.Context context, android.view.animation.Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScroller(android.content.Context context, android.view.animation.Interpolator interpolator, boolean z) {
        if (interpolator == null) {
            this.mInterpolator = new android.widget.Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mFlywheel = z;
        this.mScrollerX = new android.widget.OverScroller.SplineOverScroller(context);
        this.mScrollerY = new android.widget.OverScroller.SplineOverScroller(context);
    }

    @java.lang.Deprecated
    public OverScroller(android.content.Context context, android.view.animation.Interpolator interpolator, float f, float f2) {
        this(context, interpolator, true);
    }

    @java.lang.Deprecated
    public OverScroller(android.content.Context context, android.view.animation.Interpolator interpolator, float f, float f2, boolean z) {
        this(context, interpolator, z);
    }

    void setInterpolator(android.view.animation.Interpolator interpolator) {
        if (interpolator == null) {
            this.mInterpolator = new android.widget.Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
    }

    public final void setFriction(float f) {
        this.mScrollerX.setFriction(f);
        this.mScrollerY.setFriction(f);
    }

    public final boolean isFinished() {
        return this.mScrollerX.mFinished && this.mScrollerY.mFinished;
    }

    public final void forceFinished(boolean z) {
        android.widget.OverScroller.SplineOverScroller splineOverScroller = this.mScrollerX;
        this.mScrollerY.mFinished = z;
        splineOverScroller.mFinished = z;
    }

    public final int getCurrX() {
        return this.mScrollerX.mCurrentPosition;
    }

    public final int getCurrY() {
        return this.mScrollerY.mCurrentPosition;
    }

    public float getCurrVelocity() {
        return (float) java.lang.Math.hypot(this.mScrollerX.mCurrVelocity, this.mScrollerY.mCurrVelocity);
    }

    public final int getStartX() {
        return this.mScrollerX.mStart;
    }

    public final int getStartY() {
        return this.mScrollerY.mStart;
    }

    public final int getFinalX() {
        return this.mScrollerX.mFinal;
    }

    public final int getFinalY() {
        return this.mScrollerY.mFinal;
    }

    public final int getDuration() {
        return java.lang.Math.max(this.mScrollerX.mDuration, this.mScrollerY.mDuration);
    }

    public void extendDuration(int i) {
        this.mScrollerX.extendDuration(i);
        this.mScrollerY.extendDuration(i);
    }

    public void setFinalX(int i) {
        this.mScrollerX.setFinalPosition(i);
    }

    public void setFinalY(int i) {
        this.mScrollerY.setFinalPosition(i);
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        switch (this.mMode) {
            case 0:
                long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.mStartTime;
                int i = this.mScrollerX.mDuration;
                if (currentAnimationTimeMillis < i) {
                    float interpolation = this.mInterpolator.getInterpolation(currentAnimationTimeMillis / i);
                    this.mScrollerX.updateScroll(interpolation);
                    this.mScrollerY.updateScroll(interpolation);
                    return true;
                }
                abortAnimation();
                return true;
            case 1:
                if (!this.mScrollerX.mFinished && !this.mScrollerX.update() && !this.mScrollerX.continueWhenFinished()) {
                    this.mScrollerX.finish();
                }
                if (!this.mScrollerY.mFinished && !this.mScrollerY.update() && !this.mScrollerY.continueWhenFinished()) {
                    this.mScrollerY.finish();
                    return true;
                }
                return true;
            default:
                return true;
        }
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, 250);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mScrollerX.startScroll(i, i3, i5);
        this.mScrollerY.startScroll(i2, i4, i5);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMode = 1;
        return this.mScrollerX.springback(i, i3, i4) || this.mScrollerY.springback(i2, i5, i6);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        int i11;
        int i12;
        int i13;
        int i14;
        if (!this.mFlywheel || isFinished()) {
            i11 = i3;
            i12 = i4;
        } else {
            float f = this.mScrollerX.mCurrVelocity;
            float f2 = this.mScrollerY.mCurrVelocity;
            i11 = i3;
            float f3 = i11;
            if (java.lang.Math.signum(f3) == java.lang.Math.signum(f)) {
                i12 = i4;
                float f4 = i12;
                if (java.lang.Math.signum(f4) == java.lang.Math.signum(f2)) {
                    i13 = (int) (f4 + f2);
                    i14 = (int) (f3 + f);
                    this.mMode = 1;
                    this.mScrollerX.fling(i, i14, i5, i6, i9);
                    this.mScrollerY.fling(i2, i13, i7, i8, i10);
                }
            } else {
                i12 = i4;
            }
        }
        i13 = i12;
        i14 = i11;
        this.mMode = 1;
        this.mScrollerX.fling(i, i14, i5, i6, i9);
        this.mScrollerY.fling(i2, i13, i7, i8, i10);
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mScrollerX.notifyEdgeReached(i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mScrollerY.notifyEdgeReached(i, i2, i3);
    }

    public boolean isOverScrolled() {
        return ((this.mScrollerX.mFinished || this.mScrollerX.mState == 0) && (this.mScrollerY.mFinished || this.mScrollerY.mState == 0)) ? false : true;
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public int timePassed() {
        return (int) (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - java.lang.Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime));
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !isFinished() && java.lang.Math.signum(f) == java.lang.Math.signum((float) (this.mScrollerX.mFinal - this.mScrollerX.mStart)) && java.lang.Math.signum(f2) == java.lang.Math.signum((float) (this.mScrollerY.mFinal - this.mScrollerY.mStart));
    }

    double getSplineFlingDistance(int i) {
        return this.mScrollerY.getSplineFlingDistance(i);
    }

    static class SplineOverScroller {
        private static final int BALLISTIC = 2;
        private static final int CUBIC = 1;
        private static final float END_TENSION = 1.0f;
        private static final float GRAVITY = 2000.0f;
        private static final float INFLEXION = 0.35f;
        private static final int NB_SAMPLES = 100;
        private static final float P1 = 0.175f;
        private static final float P2 = 0.35000002f;
        private static final int SPLINE = 0;
        private static final float START_TENSION = 0.5f;
        private float mCurrVelocity;
        private int mCurrentPosition;
        private float mDeceleration;
        private int mDuration;
        private int mFinal;
        private int mOver;
        private float mPhysicalCoeff;
        private int mSplineDistance;
        private int mSplineDuration;
        private int mStart;
        private long mStartTime;
        private int mVelocity;
        private static float DECELERATION_RATE = (float) (java.lang.Math.log(0.78d) / java.lang.Math.log(0.9d));
        private static final float[] SPLINE_POSITION = new float[101];
        private static final float[] SPLINE_TIME = new float[101];
        private float mFlingFriction = android.view.ViewConfiguration.getScrollFriction();
        private int mState = 0;
        private boolean mFinished = true;

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

        void setFriction(float f) {
            this.mFlingFriction = f;
        }

        SplineOverScroller(android.content.Context context) {
            this.mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        }

        void updateScroll(float f) {
            this.mCurrentPosition = this.mStart + java.lang.Math.round(f * (this.mFinal - this.mStart));
        }

        private static float getDeceleration(int i) {
            if (i > 0) {
                return -2000.0f;
            }
            return GRAVITY;
        }

        private void adjustDuration(int i, int i2, int i3) {
            float abs = java.lang.Math.abs((i3 - i) / (i2 - i));
            int i4 = (int) (abs * 100.0f);
            if (i4 < 100) {
                float f = i4 / 100.0f;
                int i5 = i4 + 1;
                float f2 = SPLINE_TIME[i4];
                this.mDuration = (int) (this.mDuration * (f2 + (((abs - f) / ((i5 / 100.0f) - f)) * (SPLINE_TIME[i5] - f2))));
            }
        }

        void startScroll(int i, int i2, int i3) {
            this.mFinished = false;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mFinal = i + i2;
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = i3;
            this.mDeceleration = 0.0f;
            this.mVelocity = 0;
        }

        void finish() {
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        void setFinalPosition(int i) {
            this.mFinal = i;
            this.mSplineDistance = this.mFinal - this.mStart;
            this.mFinished = false;
        }

        void extendDuration(int i) {
            int currentAnimationTimeMillis = ((int) (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) + i;
            this.mSplineDuration = currentAnimationTimeMillis;
            this.mDuration = currentAnimationTimeMillis;
            this.mFinished = false;
        }

        boolean springback(int i, int i2, int i3) {
            this.mFinished = true;
            this.mFinal = i;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mVelocity = 0;
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 0;
            if (i < i2) {
                startSpringback(i, i2, 0);
            } else if (i > i3) {
                startSpringback(i, i3, 0);
            }
            return !this.mFinished;
        }

        private void startSpringback(int i, int i2, int i3) {
            this.mFinished = false;
            this.mState = 1;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mFinal = i2;
            int i4 = i - i2;
            this.mDeceleration = getDeceleration(i4);
            this.mVelocity = -i4;
            this.mOver = java.lang.Math.abs(i4);
            this.mDuration = (int) (java.lang.Math.sqrt((i4 * (-2.0d)) / this.mDeceleration) * 1000.0d);
        }

        void fling(int i, int i2, int i3, int i4, int i5) {
            double d;
            this.mOver = i5;
            this.mFinished = false;
            this.mVelocity = i2;
            this.mCurrVelocity = i2;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mStart = i;
            this.mCurrentPosition = i;
            if (i > i4 || i < i3) {
                startAfterEdge(i, i3, i4, i2);
                return;
            }
            this.mState = 0;
            if (i2 == 0) {
                d = 0.0d;
            } else {
                int splineFlingDuration = getSplineFlingDuration(i2);
                this.mSplineDuration = splineFlingDuration;
                this.mDuration = splineFlingDuration;
                d = getSplineFlingDistance(i2);
            }
            this.mSplineDistance = (int) (d * java.lang.Math.signum(r0));
            this.mFinal = i + this.mSplineDistance;
            if (this.mFinal < i3) {
                adjustDuration(this.mStart, this.mFinal, i3);
                this.mFinal = i3;
            }
            if (this.mFinal > i4) {
                adjustDuration(this.mStart, this.mFinal, i4);
                this.mFinal = i4;
            }
        }

        private double getSplineDeceleration(int i) {
            return java.lang.Math.log((java.lang.Math.abs(i) * INFLEXION) / (this.mFlingFriction * this.mPhysicalCoeff));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double getSplineFlingDistance(int i) {
            return this.mFlingFriction * this.mPhysicalCoeff * java.lang.Math.exp((DECELERATION_RATE / (DECELERATION_RATE - 1.0d)) * getSplineDeceleration(i));
        }

        private int getSplineFlingDuration(int i) {
            return (int) (java.lang.Math.exp(getSplineDeceleration(i) / (DECELERATION_RATE - 1.0d)) * 1000.0d);
        }

        private void fitOnBounceCurve(int i, int i2, int i3) {
            float f = (-i3) / this.mDeceleration;
            float f2 = i3;
            float sqrt = (float) java.lang.Math.sqrt((((((f2 * f2) / 2.0f) / java.lang.Math.abs(this.mDeceleration)) + java.lang.Math.abs(i2 - i)) * 2.0d) / java.lang.Math.abs(this.mDeceleration));
            this.mStartTime -= (int) ((sqrt - f) * 1000.0f);
            this.mStart = i2;
            this.mCurrentPosition = i2;
            this.mVelocity = (int) ((-this.mDeceleration) * sqrt);
        }

        private void startBounceAfterEdge(int i, int i2, int i3) {
            this.mDeceleration = getDeceleration(i3 == 0 ? i - i2 : i3);
            fitOnBounceCurve(i, i2, i3);
            onEdgeReached();
        }

        private void startAfterEdge(int i, int i2, int i3, int i4) {
            boolean z;
            boolean z2 = true;
            if (i > i2 && i < i3) {
                android.util.Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.mFinished = true;
                return;
            }
            if (i > i3) {
                z = true;
            } else {
                z = false;
            }
            int i5 = z ? i3 : i2;
            if ((i - i5) * i4 < 0) {
                z2 = false;
            }
            if (z2) {
                startBounceAfterEdge(i, i5, i4);
            } else if (getSplineFlingDistance(i4) > java.lang.Math.abs(r4)) {
                fling(i, i4, z ? i2 : i, z ? i : i3, this.mOver);
            } else {
                startSpringback(i, i5, i4);
            }
        }

        void notifyEdgeReached(int i, int i2, int i3) {
            if (this.mState == 0) {
                this.mOver = i3;
                this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                startAfterEdge(i, i2, i2, (int) this.mCurrVelocity);
            }
        }

        private void onEdgeReached() {
            float f = this.mVelocity * this.mVelocity;
            float abs = f / (java.lang.Math.abs(this.mDeceleration) * 2.0f);
            float signum = java.lang.Math.signum(this.mVelocity);
            if (abs > this.mOver) {
                this.mDeceleration = ((-signum) * f) / (this.mOver * 2.0f);
                abs = this.mOver;
            }
            this.mOver = (int) abs;
            this.mState = 2;
            int i = this.mStart;
            if (this.mVelocity <= 0) {
                abs = -abs;
            }
            this.mFinal = i + ((int) abs);
            this.mDuration = -((int) ((this.mVelocity * 1000.0f) / this.mDeceleration));
        }

        boolean continueWhenFinished() {
            switch (this.mState) {
                case 0:
                    if (this.mDuration >= this.mSplineDuration) {
                        return false;
                    }
                    int i = this.mFinal;
                    this.mStart = i;
                    this.mCurrentPosition = i;
                    this.mVelocity = (int) this.mCurrVelocity;
                    this.mDeceleration = getDeceleration(this.mVelocity);
                    this.mStartTime += this.mDuration;
                    onEdgeReached();
                    break;
                case 1:
                    return false;
                case 2:
                    this.mStartTime += this.mDuration;
                    startSpringback(this.mFinal, this.mStart, 0);
                    break;
            }
            update();
            return true;
        }

        boolean update() {
            float f;
            float f2;
            double d;
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
            if (currentAnimationTimeMillis == 0) {
                return this.mDuration > 0;
            }
            if (currentAnimationTimeMillis > this.mDuration) {
                return false;
            }
            switch (this.mState) {
                case 0:
                    float f3 = currentAnimationTimeMillis / this.mSplineDuration;
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
                    d = f * this.mSplineDistance;
                    this.mCurrVelocity = ((f2 * this.mSplineDistance) / this.mSplineDuration) * 1000.0f;
                    break;
                case 1:
                    float f6 = currentAnimationTimeMillis / this.mDuration;
                    float f7 = f6 * f6;
                    float signum = java.lang.Math.signum(this.mVelocity);
                    this.mCurrVelocity = signum * this.mOver * 6.0f * ((-f6) + f7);
                    d = this.mOver * signum * ((3.0f * f7) - ((2.0f * f6) * f7));
                    break;
                case 2:
                    float f8 = currentAnimationTimeMillis / 1000.0f;
                    this.mCurrVelocity = this.mVelocity + (this.mDeceleration * f8);
                    d = (this.mVelocity * f8) + (((this.mDeceleration * f8) * f8) / 2.0f);
                    break;
                default:
                    d = 0.0d;
                    break;
            }
            this.mCurrentPosition = this.mStart + ((int) java.lang.Math.round(d));
            return true;
        }
    }
}
