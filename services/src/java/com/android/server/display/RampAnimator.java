package com.android.server.display;

/* loaded from: classes.dex */
class RampAnimator<T> {
    private float mAnimatedValue;
    private boolean mAnimating;
    private float mAnimationDecreaseMaxTimeSecs;
    private float mAnimationIncreaseMaxTimeSecs;
    private final com.android.server.display.RampAnimator.Clock mClock;
    private float mCurrentValue;
    private boolean mFirstTime;
    private long mLastFrameTimeNanos;
    private final T mObject;
    private final android.util.FloatProperty<T> mProperty;
    private float mRate;
    private float mTargetHlgValue;
    private float mTargetLinearValue;

    interface Clock {
        long nanoTime();
    }

    public interface Listener {
        void onAnimationEnd();
    }

    RampAnimator(T t, android.util.FloatProperty<T> floatProperty) {
        this(t, floatProperty, new com.android.server.display.RampAnimator.Clock() { // from class: com.android.server.display.RampAnimator$$ExternalSyntheticLambda0
            @Override // com.android.server.display.RampAnimator.Clock
            public final long nanoTime() {
                return java.lang.System.nanoTime();
            }
        });
    }

    RampAnimator(T t, android.util.FloatProperty<T> floatProperty, com.android.server.display.RampAnimator.Clock clock) {
        this.mFirstTime = true;
        this.mObject = t;
        this.mProperty = floatProperty;
        this.mClock = clock;
    }

    void setAnimationTimeLimits(long j, long j2) {
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mAnimationIncreaseMaxTimeSecs = j > 0 ? j / 1000.0f : 0.0f;
        if (j2 > 0) {
            f = j2 / 1000.0f;
        }
        this.mAnimationDecreaseMaxTimeSecs = f;
    }

    boolean setAnimationTarget(float f, float f2, boolean z) {
        float f3 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        float f4 = z ? 0.0f : this.mAnimationIncreaseMaxTimeSecs;
        if (!z) {
            f3 = this.mAnimationDecreaseMaxTimeSecs;
        }
        return setAnimationTarget(f, f2, f4, f3);
    }

    private boolean setAnimationTarget(float f, float f2, float f3, float f4) {
        float convertLinearToGamma = com.android.internal.display.BrightnessUtils.convertLinearToGamma(f);
        if (this.mFirstTime || f2 <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            if (!this.mFirstTime && convertLinearToGamma == this.mCurrentValue) {
                return false;
            }
            this.mFirstTime = false;
            this.mRate = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mTargetHlgValue = convertLinearToGamma;
            this.mTargetLinearValue = f;
            this.mCurrentValue = convertLinearToGamma;
            setPropertyValue(convertLinearToGamma);
            this.mAnimating = false;
            return true;
        }
        if (convertLinearToGamma > this.mCurrentValue && f3 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && (convertLinearToGamma - this.mCurrentValue) / f2 > f3) {
            f2 = (convertLinearToGamma - this.mCurrentValue) / f3;
        } else if (convertLinearToGamma < this.mCurrentValue && f4 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && (this.mCurrentValue - convertLinearToGamma) / f2 > f4) {
            f2 = (this.mCurrentValue - convertLinearToGamma) / f4;
        }
        if (!this.mAnimating || f2 > this.mRate || ((convertLinearToGamma <= this.mCurrentValue && this.mCurrentValue <= this.mTargetHlgValue) || (this.mTargetHlgValue <= this.mCurrentValue && this.mCurrentValue <= convertLinearToGamma))) {
            this.mRate = f2;
        }
        boolean z = this.mTargetHlgValue != convertLinearToGamma;
        this.mTargetHlgValue = convertLinearToGamma;
        this.mTargetLinearValue = f;
        if (!this.mAnimating && convertLinearToGamma != this.mCurrentValue) {
            this.mAnimating = true;
            this.mAnimatedValue = this.mCurrentValue;
            this.mLastFrameTimeNanos = this.mClock.nanoTime();
        }
        return z;
    }

    boolean isAnimating() {
        return this.mAnimating;
    }

    private void setPropertyValue(float f) {
        this.mProperty.setValue(this.mObject, f == this.mTargetHlgValue ? this.mTargetLinearValue : com.android.internal.display.BrightnessUtils.convertGammaToLinear(f));
    }

    void performNextAnimationStep(long j) {
        float f = (j - this.mLastFrameTimeNanos) * 1.0E-9f;
        this.mLastFrameTimeNanos = j;
        float durationScale = android.animation.ValueAnimator.getDurationScale();
        if (durationScale == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            this.mAnimatedValue = this.mTargetHlgValue;
        } else {
            float f2 = (f * this.mRate) / durationScale;
            if (this.mTargetHlgValue > this.mCurrentValue) {
                this.mAnimatedValue = java.lang.Math.min(this.mAnimatedValue + f2, this.mTargetHlgValue);
            } else {
                this.mAnimatedValue = java.lang.Math.max(this.mAnimatedValue - f2, this.mTargetHlgValue);
            }
        }
        float f3 = this.mCurrentValue;
        this.mCurrentValue = this.mAnimatedValue;
        if (f3 != this.mCurrentValue) {
            setPropertyValue(this.mCurrentValue);
        }
        if (this.mTargetHlgValue == this.mCurrentValue) {
            this.mAnimating = false;
        }
    }

    static class DualRampAnimator<T> {
        private boolean mAwaitingCallback;
        private final com.android.server.display.RampAnimator<T> mFirst;
        private com.android.server.display.RampAnimator.Listener mListener;
        private final com.android.server.display.RampAnimator<T> mSecond;
        private final java.lang.Runnable mAnimationCallback = new java.lang.Runnable() { // from class: com.android.server.display.RampAnimator.DualRampAnimator.1
            @Override // java.lang.Runnable
            public void run() {
                long frameTimeNanos = com.android.server.display.RampAnimator.DualRampAnimator.this.mChoreographer.getFrameTimeNanos();
                com.android.server.display.RampAnimator.DualRampAnimator.this.mFirst.performNextAnimationStep(frameTimeNanos);
                com.android.server.display.RampAnimator.DualRampAnimator.this.mSecond.performNextAnimationStep(frameTimeNanos);
                if (com.android.server.display.RampAnimator.DualRampAnimator.this.isAnimating()) {
                    com.android.server.display.RampAnimator.DualRampAnimator.this.postAnimationCallback();
                    return;
                }
                if (com.android.server.display.RampAnimator.DualRampAnimator.this.mListener != null) {
                    com.android.server.display.RampAnimator.DualRampAnimator.this.mListener.onAnimationEnd();
                }
                com.android.server.display.RampAnimator.DualRampAnimator.this.mAwaitingCallback = false;
            }
        };
        private final android.view.Choreographer mChoreographer = android.view.Choreographer.getInstance();

        DualRampAnimator(T t, android.util.FloatProperty<T> floatProperty, android.util.FloatProperty<T> floatProperty2) {
            this.mFirst = new com.android.server.display.RampAnimator<>(t, floatProperty);
            this.mSecond = new com.android.server.display.RampAnimator<>(t, floatProperty2);
        }

        public void setAnimationTimeLimits(long j, long j2) {
            this.mFirst.setAnimationTimeLimits(j, j2);
            this.mSecond.setAnimationTimeLimits(j, j2);
        }

        public boolean animateTo(float f, float f2, float f3, boolean z) {
            boolean animationTarget = this.mFirst.setAnimationTarget(f, f3, z) | this.mSecond.setAnimationTarget(f2, f3, z);
            boolean isAnimating = isAnimating();
            if (isAnimating != this.mAwaitingCallback) {
                if (isAnimating) {
                    this.mAwaitingCallback = true;
                    postAnimationCallback();
                } else if (this.mAwaitingCallback) {
                    this.mChoreographer.removeCallbacks(1, this.mAnimationCallback, null);
                    this.mAwaitingCallback = false;
                }
            }
            return animationTarget;
        }

        public void setListener(com.android.server.display.RampAnimator.Listener listener) {
            this.mListener = listener;
        }

        public boolean isAnimating() {
            return this.mFirst.isAnimating() || this.mSecond.isAnimating();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postAnimationCallback() {
            this.mChoreographer.postCallback(1, this.mAnimationCallback, null);
        }
    }
}
