package android.graphics.drawable;

/* loaded from: classes.dex */
class RippleForeground extends android.graphics.drawable.RippleComponent {
    private static final int OPACITY_ENTER_DURATION = 75;
    private static final int OPACITY_EXIT_DURATION = 150;
    private static final int OPACITY_HOLD_DURATION = 225;
    private static final int RIPPLE_ENTER_DURATION = 225;
    private static final int RIPPLE_ORIGIN_DURATION = 225;
    private final android.animation.AnimatorListenerAdapter mAnimationListener;
    private float mClampedStartingX;
    private float mClampedStartingY;
    private long mEnterStartedAtMillis;
    private final boolean mForceSoftware;
    private boolean mHasFinishedExit;
    private float mOpacity;
    private java.util.ArrayList<android.graphics.animation.RenderNodeAnimator> mPendingHwAnimators;
    private android.graphics.CanvasProperty<android.graphics.Paint> mPropPaint;
    private android.graphics.CanvasProperty<java.lang.Float> mPropRadius;
    private android.graphics.CanvasProperty<java.lang.Float> mPropX;
    private android.graphics.CanvasProperty<java.lang.Float> mPropY;
    private java.util.ArrayList<android.graphics.animation.RenderNodeAnimator> mRunningHwAnimators;
    private java.util.ArrayList<android.animation.Animator> mRunningSwAnimators;
    private float mStartRadius;
    private float mStartingX;
    private float mStartingY;
    private float mTargetX;
    private float mTargetY;
    private float mTweenRadius;
    private float mTweenX;
    private float mTweenY;
    private boolean mUsingProperties;
    private static final android.animation.TimeInterpolator LINEAR_INTERPOLATOR = new android.view.animation.LinearInterpolator();
    private static final android.animation.TimeInterpolator DECELERATE_INTERPOLATOR = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final android.util.FloatProperty<android.graphics.drawable.RippleForeground> TWEEN_RADIUS = new android.util.FloatProperty<android.graphics.drawable.RippleForeground>("tweenRadius") { // from class: android.graphics.drawable.RippleForeground.2
        @Override // android.util.FloatProperty
        public void setValue(android.graphics.drawable.RippleForeground rippleForeground, float f) {
            rippleForeground.mTweenRadius = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public java.lang.Float get(android.graphics.drawable.RippleForeground rippleForeground) {
            return java.lang.Float.valueOf(rippleForeground.mTweenRadius);
        }
    };
    private static final android.util.FloatProperty<android.graphics.drawable.RippleForeground> TWEEN_ORIGIN = new android.util.FloatProperty<android.graphics.drawable.RippleForeground>("tweenOrigin") { // from class: android.graphics.drawable.RippleForeground.3
        @Override // android.util.FloatProperty
        public void setValue(android.graphics.drawable.RippleForeground rippleForeground, float f) {
            rippleForeground.mTweenX = f;
            rippleForeground.mTweenY = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public java.lang.Float get(android.graphics.drawable.RippleForeground rippleForeground) {
            return java.lang.Float.valueOf(rippleForeground.mTweenX);
        }
    };
    private static final android.util.FloatProperty<android.graphics.drawable.RippleForeground> OPACITY = new android.util.FloatProperty<android.graphics.drawable.RippleForeground>("opacity") { // from class: android.graphics.drawable.RippleForeground.4
        @Override // android.util.FloatProperty
        public void setValue(android.graphics.drawable.RippleForeground rippleForeground, float f) {
            rippleForeground.mOpacity = f;
            rippleForeground.onAnimationPropertyChanged();
        }

        @Override // android.util.Property
        public java.lang.Float get(android.graphics.drawable.RippleForeground rippleForeground) {
            return java.lang.Float.valueOf(rippleForeground.mOpacity);
        }
    };

    public RippleForeground(android.graphics.drawable.RippleDrawable rippleDrawable, android.graphics.Rect rect, float f, float f2, boolean z) {
        super(rippleDrawable, rect);
        this.mTargetX = 0.0f;
        this.mTargetY = 0.0f;
        this.mOpacity = 0.0f;
        this.mTweenRadius = 0.0f;
        this.mTweenX = 0.0f;
        this.mTweenY = 0.0f;
        this.mPendingHwAnimators = new java.util.ArrayList<>();
        this.mRunningHwAnimators = new java.util.ArrayList<>();
        this.mRunningSwAnimators = new java.util.ArrayList<>();
        this.mStartRadius = 0.0f;
        this.mAnimationListener = new android.animation.AnimatorListenerAdapter() { // from class: android.graphics.drawable.RippleForeground.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.graphics.drawable.RippleForeground.this.mHasFinishedExit = true;
                android.graphics.drawable.RippleForeground.this.pruneHwFinished();
                android.graphics.drawable.RippleForeground.this.pruneSwFinished();
                if (android.graphics.drawable.RippleForeground.this.mRunningHwAnimators.isEmpty()) {
                    android.graphics.drawable.RippleForeground.this.clearHwProps();
                }
            }
        };
        this.mForceSoftware = z;
        this.mStartingX = f;
        this.mStartingY = f2;
        this.mStartRadius = java.lang.Math.max(rect.width(), rect.height()) * 0.3f;
        clampStartingPosition();
    }

    @Override // android.graphics.drawable.RippleComponent
    protected void onTargetRadiusChanged(float f) {
        clampStartingPosition();
        switchToUiThreadAnimation();
    }

    private void drawSoftware(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        int alpha = paint.getAlpha();
        int i = (int) ((alpha * this.mOpacity) + 0.5f);
        float currentRadius = getCurrentRadius();
        if (i > 0 && currentRadius > 0.0f) {
            float currentX = getCurrentX();
            float currentY = getCurrentY();
            paint.setAlpha(i);
            canvas.drawCircle(currentX, currentY, currentRadius, paint);
            paint.setAlpha(alpha);
        }
    }

    private void startPending(android.graphics.RecordingCanvas recordingCanvas) {
        if (!this.mPendingHwAnimators.isEmpty()) {
            for (int i = 0; i < this.mPendingHwAnimators.size(); i++) {
                android.graphics.animation.RenderNodeAnimator renderNodeAnimator = this.mPendingHwAnimators.get(i);
                renderNodeAnimator.setTarget(recordingCanvas);
                renderNodeAnimator.start();
                this.mRunningHwAnimators.add(renderNodeAnimator);
            }
            this.mPendingHwAnimators.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pruneHwFinished() {
        if (!this.mRunningHwAnimators.isEmpty()) {
            for (int size = this.mRunningHwAnimators.size() - 1; size >= 0; size--) {
                if (!this.mRunningHwAnimators.get(size).isRunning()) {
                    this.mRunningHwAnimators.remove(size);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pruneSwFinished() {
        if (!this.mRunningSwAnimators.isEmpty()) {
            for (int size = this.mRunningSwAnimators.size() - 1; size >= 0; size--) {
                if (!this.mRunningSwAnimators.get(size).isRunning()) {
                    this.mRunningSwAnimators.remove(size);
                }
            }
        }
    }

    private void drawHardware(android.graphics.RecordingCanvas recordingCanvas, android.graphics.Paint paint) {
        startPending(recordingCanvas);
        pruneHwFinished();
        if (this.mPropPaint != null) {
            this.mUsingProperties = true;
            recordingCanvas.drawCircle(this.mPropX, this.mPropY, this.mPropRadius, this.mPropPaint);
        } else {
            this.mUsingProperties = false;
            drawSoftware(recordingCanvas, paint);
        }
    }

    @Override // android.graphics.drawable.RippleComponent
    public void getBounds(android.graphics.Rect rect) {
        int i = (int) this.mTargetX;
        int i2 = (int) this.mTargetY;
        int i3 = ((int) this.mTargetRadius) + 1;
        rect.set(i - i3, i2 - i3, i + i3, i2 + i3);
    }

    public void move(float f, float f2) {
        this.mStartingX = f;
        this.mStartingY = f2;
        clampStartingPosition();
    }

    public boolean hasFinishedExit() {
        return this.mHasFinishedExit;
    }

    private long computeFadeOutDelay() {
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mEnterStartedAtMillis;
        if (currentAnimationTimeMillis <= 0 || currentAnimationTimeMillis >= 225) {
            return 0L;
        }
        return 225 - currentAnimationTimeMillis;
    }

    private void startSoftwareEnter() {
        for (int i = 0; i < this.mRunningSwAnimators.size(); i++) {
            this.mRunningSwAnimators.get(i).cancel();
        }
        this.mRunningSwAnimators.clear();
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, TWEEN_RADIUS, 1.0f);
        ofFloat.setDuration(225L);
        ofFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        ofFloat.start();
        this.mRunningSwAnimators.add(ofFloat);
        android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(this, TWEEN_ORIGIN, 1.0f);
        ofFloat2.setDuration(225L);
        ofFloat2.setInterpolator(DECELERATE_INTERPOLATOR);
        ofFloat2.start();
        this.mRunningSwAnimators.add(ofFloat2);
        android.animation.ObjectAnimator ofFloat3 = android.animation.ObjectAnimator.ofFloat(this, OPACITY, 1.0f);
        ofFloat3.setDuration(75L);
        ofFloat3.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat3.start();
        this.mRunningSwAnimators.add(ofFloat3);
    }

    private void startSoftwareExit() {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, OPACITY, 0.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat.addListener(this.mAnimationListener);
        ofFloat.setStartDelay(computeFadeOutDelay());
        ofFloat.start();
        this.mRunningSwAnimators.add(ofFloat);
    }

    private void startHardwareEnter() {
        if (this.mForceSoftware) {
            return;
        }
        this.mPropX = android.graphics.CanvasProperty.createFloat(getCurrentX());
        this.mPropY = android.graphics.CanvasProperty.createFloat(getCurrentY());
        this.mPropRadius = android.graphics.CanvasProperty.createFloat(getCurrentRadius());
        this.mPropPaint = android.graphics.CanvasProperty.createPaint(this.mOwner.updateRipplePaint());
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator = new android.graphics.animation.RenderNodeAnimator(this.mPropRadius, this.mTargetRadius);
        renderNodeAnimator.setDuration(225L);
        renderNodeAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator);
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator2 = new android.graphics.animation.RenderNodeAnimator(this.mPropX, this.mTargetX);
        renderNodeAnimator2.setDuration(225L);
        renderNodeAnimator2.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator2);
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator3 = new android.graphics.animation.RenderNodeAnimator(this.mPropY, this.mTargetY);
        renderNodeAnimator3.setDuration(225L);
        renderNodeAnimator3.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator3);
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator4 = new android.graphics.animation.RenderNodeAnimator(this.mPropPaint, 1, r0.getAlpha());
        renderNodeAnimator4.setDuration(75L);
        renderNodeAnimator4.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator4.setStartValue(0.0f);
        this.mPendingHwAnimators.add(renderNodeAnimator4);
        invalidateSelf();
    }

    private void startHardwareExit() {
        if (this.mForceSoftware || this.mPropPaint == null) {
            return;
        }
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator = new android.graphics.animation.RenderNodeAnimator(this.mPropPaint, 1, 0.0f);
        renderNodeAnimator.setDuration(150L);
        renderNodeAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimationListener);
        renderNodeAnimator.setStartDelay(computeFadeOutDelay());
        renderNodeAnimator.setStartValue(this.mOwner.updateRipplePaint().getAlpha());
        this.mPendingHwAnimators.add(renderNodeAnimator);
        invalidateSelf();
    }

    public final void enter() {
        this.mEnterStartedAtMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        startSoftwareEnter();
        startHardwareEnter();
    }

    public final void exit() {
        startSoftwareExit();
        startHardwareExit();
    }

    private float getCurrentX() {
        return android.util.MathUtils.lerp(this.mClampedStartingX - this.mBounds.exactCenterX(), this.mTargetX, this.mTweenX);
    }

    private float getCurrentY() {
        return android.util.MathUtils.lerp(this.mClampedStartingY - this.mBounds.exactCenterY(), this.mTargetY, this.mTweenY);
    }

    private float getCurrentRadius() {
        return android.util.MathUtils.lerp(this.mStartRadius, this.mTargetRadius, this.mTweenRadius);
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        boolean z = !this.mForceSoftware && (canvas instanceof android.graphics.RecordingCanvas);
        pruneSwFinished();
        if (z) {
            drawHardware((android.graphics.RecordingCanvas) canvas, paint);
        } else {
            drawSoftware(canvas, paint);
        }
    }

    private void clampStartingPosition() {
        float exactCenterX = this.mBounds.exactCenterX();
        float exactCenterY = this.mBounds.exactCenterY();
        float f = this.mStartingX - exactCenterX;
        float f2 = this.mStartingY - exactCenterY;
        float f3 = this.mTargetRadius - this.mStartRadius;
        if ((f * f) + (f2 * f2) > f3 * f3) {
            double atan2 = java.lang.Math.atan2(f2, f);
            double d = f3;
            this.mClampedStartingX = exactCenterX + ((float) (java.lang.Math.cos(atan2) * d));
            this.mClampedStartingY = exactCenterY + ((float) (java.lang.Math.sin(atan2) * d));
            return;
        }
        this.mClampedStartingX = this.mStartingX;
        this.mClampedStartingY = this.mStartingY;
    }

    public void end() {
        for (int i = 0; i < this.mRunningSwAnimators.size(); i++) {
            this.mRunningSwAnimators.get(i).end();
        }
        this.mRunningSwAnimators.clear();
        for (int i2 = 0; i2 < this.mRunningHwAnimators.size(); i2++) {
            this.mRunningHwAnimators.get(i2).end();
        }
        this.mRunningHwAnimators.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationPropertyChanged() {
        if (!this.mUsingProperties) {
            invalidateSelf();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearHwProps() {
        this.mPropPaint = null;
        this.mPropRadius = null;
        this.mPropX = null;
        this.mPropY = null;
        this.mUsingProperties = false;
    }

    private void switchToUiThreadAnimation() {
        for (int i = 0; i < this.mRunningHwAnimators.size(); i++) {
            android.graphics.animation.RenderNodeAnimator renderNodeAnimator = this.mRunningHwAnimators.get(i);
            renderNodeAnimator.removeListener(this.mAnimationListener);
            renderNodeAnimator.end();
        }
        this.mRunningHwAnimators.clear();
        clearHwProps();
        invalidateSelf();
    }
}
