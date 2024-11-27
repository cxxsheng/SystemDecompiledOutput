package android.graphics.drawable;

/* loaded from: classes.dex */
public final class RippleAnimationSession {
    private static final int ENTER_ANIM_DURATION = 450;
    private static final int EXIT_ANIM_DURATION = 375;
    private static final long MAX_NOISE_PHASE = 32;
    private static final long NOISE_ANIMATION_DURATION = 7000;
    private static final java.lang.String TAG = "RippleAnimationSession";
    private android.graphics.drawable.RippleAnimationSession.AnimationProperties<android.graphics.CanvasProperty<java.lang.Float>, android.graphics.CanvasProperty<android.graphics.Paint>> mCanvasProperties;
    private android.animation.Animator mCurrentAnimation;
    private boolean mForceSoftware;
    private android.animation.Animator mLoopAnimation;
    private java.util.function.Consumer<android.graphics.drawable.RippleAnimationSession> mOnSessionEnd;
    private java.lang.Runnable mOnUpdate;
    private final android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> mProperties;
    private long mStartTime;
    private static final android.animation.TimeInterpolator LINEAR_INTERPOLATOR = new android.view.animation.LinearInterpolator();
    private static final android.view.animation.Interpolator FAST_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);

    RippleAnimationSession(android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> animationProperties, boolean z) {
        this.mProperties = animationProperties;
        this.mForceSoftware = z;
    }

    boolean isForceSoftware() {
        return this.mForceSoftware;
    }

    android.graphics.drawable.RippleAnimationSession enter(android.graphics.Canvas canvas) {
        this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        if (useRTAnimations(canvas)) {
            enterHardware((android.graphics.RecordingCanvas) canvas);
        } else {
            enterSoftware();
        }
        return this;
    }

    void end() {
        if (this.mCurrentAnimation != null) {
            this.mCurrentAnimation.end();
        }
    }

    android.graphics.drawable.RippleAnimationSession exit(android.graphics.Canvas canvas) {
        if (useRTAnimations(canvas)) {
            exitHardware((android.graphics.RecordingCanvas) canvas);
        } else {
            exitSoftware();
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationEnd(android.animation.Animator animator) {
        notifyUpdate();
    }

    android.graphics.drawable.RippleAnimationSession setOnSessionEnd(java.util.function.Consumer<android.graphics.drawable.RippleAnimationSession> consumer) {
        this.mOnSessionEnd = consumer;
        return this;
    }

    android.graphics.drawable.RippleAnimationSession setOnAnimationUpdated(java.lang.Runnable runnable) {
        this.mOnUpdate = runnable;
        return this;
    }

    private boolean useRTAnimations(android.graphics.Canvas canvas) {
        if (this.mForceSoftware || !canvas.isHardwareAccelerated()) {
            return false;
        }
        android.graphics.RecordingCanvas recordingCanvas = (android.graphics.RecordingCanvas) canvas;
        return recordingCanvas.mNode != null && recordingCanvas.mNode.isAttached();
    }

    private void exitSoftware() {
        final android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.5f, 1.0f);
        ofFloat.setDuration(375L);
        ofFloat.setStartDelay(computeDelay());
        ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.graphics.drawable.RippleAnimationSession.this.lambda$exitSoftware$0(ofFloat, valueAnimator);
            }
        });
        ofFloat.addListener(new android.graphics.drawable.RippleAnimationSession.AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.1
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                super.onAnimationEnd(animator);
                if (android.graphics.drawable.RippleAnimationSession.this.mLoopAnimation != null) {
                    android.graphics.drawable.RippleAnimationSession.this.mLoopAnimation.cancel();
                }
                java.util.function.Consumer consumer = android.graphics.drawable.RippleAnimationSession.this.mOnSessionEnd;
                if (consumer != null) {
                    consumer.accept(android.graphics.drawable.RippleAnimationSession.this);
                }
                if (android.graphics.drawable.RippleAnimationSession.this.mCurrentAnimation == ofFloat) {
                    android.graphics.drawable.RippleAnimationSession.this.mCurrentAnimation = null;
                }
            }
        });
        ofFloat.setInterpolator(LINEAR_INTERPOLATOR);
        ofFloat.start();
        this.mCurrentAnimation = ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$exitSoftware$0(android.animation.ValueAnimator valueAnimator, android.animation.ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setProgress(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private long computeDelay() {
        return java.lang.Math.max(450 - (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mStartTime), 0L);
    }

    private void notifyUpdate() {
        if (this.mOnUpdate != null) {
            this.mOnUpdate.run();
        }
    }

    android.graphics.drawable.RippleAnimationSession setForceSoftwareAnimation(boolean z) {
        this.mForceSoftware = z;
        return this;
    }

    private void exitHardware(android.graphics.RecordingCanvas recordingCanvas) {
        final android.graphics.animation.RenderNodeAnimator renderNodeAnimator = new android.graphics.animation.RenderNodeAnimator(getCanvasProperties().getProgress(), 1.0f);
        renderNodeAnimator.setDuration(375L);
        renderNodeAnimator.addListener(new android.graphics.drawable.RippleAnimationSession.AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.2
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                super.onAnimationEnd(animator);
                if (android.graphics.drawable.RippleAnimationSession.this.mLoopAnimation != null) {
                    android.graphics.drawable.RippleAnimationSession.this.mLoopAnimation.cancel();
                }
                java.util.function.Consumer consumer = android.graphics.drawable.RippleAnimationSession.this.mOnSessionEnd;
                if (consumer != null) {
                    consumer.accept(android.graphics.drawable.RippleAnimationSession.this);
                }
                if (android.graphics.drawable.RippleAnimationSession.this.mCurrentAnimation == renderNodeAnimator) {
                    android.graphics.drawable.RippleAnimationSession.this.mCurrentAnimation = null;
                }
            }
        });
        renderNodeAnimator.setTarget(recordingCanvas);
        renderNodeAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        renderNodeAnimator.setStartDelay(computeDelay());
        renderNodeAnimator.start();
        this.mCurrentAnimation = renderNodeAnimator;
    }

    private void enterHardware(android.graphics.RecordingCanvas recordingCanvas) {
        android.graphics.drawable.RippleAnimationSession.AnimationProperties<android.graphics.CanvasProperty<java.lang.Float>, android.graphics.CanvasProperty<android.graphics.Paint>> canvasProperties = getCanvasProperties();
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator = new android.graphics.animation.RenderNodeAnimator(canvasProperties.getProgress(), 0.5f);
        renderNodeAnimator.setTarget(recordingCanvas);
        android.graphics.animation.RenderNodeAnimator renderNodeAnimator2 = new android.graphics.animation.RenderNodeAnimator(canvasProperties.getNoisePhase(), this.mStartTime + 32);
        renderNodeAnimator2.setTarget(recordingCanvas);
        startAnimation(renderNodeAnimator, renderNodeAnimator2);
        this.mCurrentAnimation = renderNodeAnimator;
    }

    private void startAnimation(android.animation.Animator animator, android.animation.Animator animator2) {
        animator.setDuration(450L);
        animator.addListener(new android.graphics.drawable.RippleAnimationSession.AnimatorListener(this));
        animator.setInterpolator(FAST_OUT_SLOW_IN);
        animator.start();
        animator2.setDuration(NOISE_ANIMATION_DURATION);
        animator2.addListener(new android.graphics.drawable.RippleAnimationSession.AnimatorListener(this) { // from class: android.graphics.drawable.RippleAnimationSession.3
            @Override // android.graphics.drawable.RippleAnimationSession.AnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator3) {
                super.onAnimationEnd(animator3);
                android.graphics.drawable.RippleAnimationSession.this.mLoopAnimation = null;
            }
        });
        animator2.setInterpolator(LINEAR_INTERPOLATOR);
        animator2.start();
        if (this.mLoopAnimation != null) {
            this.mLoopAnimation.cancel();
        }
        this.mLoopAnimation = animator2;
    }

    private void enterSoftware() {
        final android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 0.5f);
        ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.graphics.drawable.RippleAnimationSession.this.lambda$enterSoftware$1(ofFloat, valueAnimator);
            }
        });
        final android.animation.ValueAnimator ofFloat2 = android.animation.ValueAnimator.ofFloat(this.mStartTime, this.mStartTime + 32);
        ofFloat2.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleAnimationSession$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.graphics.drawable.RippleAnimationSession.this.lambda$enterSoftware$2(ofFloat2, valueAnimator);
            }
        });
        startAnimation(ofFloat, ofFloat2);
        this.mCurrentAnimation = ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterSoftware$1(android.animation.ValueAnimator valueAnimator, android.animation.ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setProgress(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterSoftware$2(android.animation.ValueAnimator valueAnimator, android.animation.ValueAnimator valueAnimator2) {
        notifyUpdate();
        this.mProperties.getShader().setNoisePhase(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    void setRadius(float f) {
        this.mProperties.setRadius(java.lang.Float.valueOf(f));
        this.mProperties.getShader().setRadius(f);
        if (this.mCanvasProperties != null) {
            this.mCanvasProperties.setRadius(android.graphics.CanvasProperty.createFloat(f));
            this.mCanvasProperties.getShader().setRadius(f);
        }
    }

    android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> getProperties() {
        return this.mProperties;
    }

    android.graphics.drawable.RippleAnimationSession.AnimationProperties<android.graphics.CanvasProperty<java.lang.Float>, android.graphics.CanvasProperty<android.graphics.Paint>> getCanvasProperties() {
        if (this.mCanvasProperties == null) {
            this.mCanvasProperties = new android.graphics.drawable.RippleAnimationSession.AnimationProperties<>(android.graphics.CanvasProperty.createFloat(this.mProperties.getX().floatValue()), android.graphics.CanvasProperty.createFloat(this.mProperties.getY().floatValue()), android.graphics.CanvasProperty.createFloat(this.mProperties.getMaxRadius().floatValue()), android.graphics.CanvasProperty.createFloat(this.mProperties.getNoisePhase().floatValue()), android.graphics.CanvasProperty.createPaint(this.mProperties.getPaint()), android.graphics.CanvasProperty.createFloat(this.mProperties.getProgress().floatValue()), this.mProperties.getColor(), this.mProperties.getShader());
        }
        return this.mCanvasProperties;
    }

    private static class AnimatorListener implements android.animation.Animator.AnimatorListener {
        private final android.graphics.drawable.RippleAnimationSession mSession;

        AnimatorListener(android.graphics.drawable.RippleAnimationSession rippleAnimationSession) {
            this.mSession = rippleAnimationSession;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            this.mSession.onAnimationEnd(animator);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }
    }

    static class AnimationProperties<FloatType, PaintType> {
        private final int mColor;
        private FloatType mMaxRadius;
        private final FloatType mNoisePhase;
        private final PaintType mPaint;
        private final FloatType mProgress;
        private final android.graphics.drawable.RippleShader mShader;
        private FloatType mX;
        private FloatType mY;

        AnimationProperties(FloatType floattype, FloatType floattype2, FloatType floattype3, FloatType floattype4, PaintType painttype, FloatType floattype5, int i, android.graphics.drawable.RippleShader rippleShader) {
            this.mY = floattype2;
            this.mX = floattype;
            this.mMaxRadius = floattype3;
            this.mNoisePhase = floattype4;
            this.mPaint = painttype;
            this.mShader = rippleShader;
            this.mProgress = floattype5;
            this.mColor = i;
        }

        FloatType getProgress() {
            return this.mProgress;
        }

        void setRadius(FloatType floattype) {
            this.mMaxRadius = floattype;
        }

        void setOrigin(FloatType floattype, FloatType floattype2) {
            this.mX = floattype;
            this.mY = floattype2;
        }

        FloatType getX() {
            return this.mX;
        }

        FloatType getY() {
            return this.mY;
        }

        FloatType getMaxRadius() {
            return this.mMaxRadius;
        }

        PaintType getPaint() {
            return this.mPaint;
        }

        android.graphics.drawable.RippleShader getShader() {
            return this.mShader;
        }

        FloatType getNoisePhase() {
            return this.mNoisePhase;
        }

        int getColor() {
            return this.mColor;
        }
    }
}
