package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
final class KeyButtonRipple extends android.graphics.drawable.Drawable {
    private static final android.view.animation.Interpolator ALPHA_OUT_INTERPOLATOR = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    private static final int ANIMATION_DURATION_FADE = 450;
    private static final int ANIMATION_DURATION_SCALE = 350;
    private static final float GLOW_MAX_ALPHA = 0.2f;
    private static final float GLOW_MAX_ALPHA_DARK = 0.1f;
    private static final float GLOW_MAX_SCALE_FACTOR = 1.35f;
    private android.graphics.CanvasProperty<java.lang.Float> mBottomProp;
    private boolean mDark;
    private boolean mDelayTouchFeedback;
    private boolean mDrawingHardwareGlow;
    private boolean mLastDark;
    private android.graphics.CanvasProperty<java.lang.Float> mLeftProp;
    private int mMaxWidth;
    private final int mMaxWidthResource;
    private android.graphics.CanvasProperty<android.graphics.Paint> mPaintProp;
    private boolean mPressed;
    private android.graphics.CanvasProperty<java.lang.Float> mRightProp;
    private android.graphics.Paint mRipplePaint;
    private android.graphics.CanvasProperty<java.lang.Float> mRxProp;
    private android.graphics.CanvasProperty<java.lang.Float> mRyProp;
    private boolean mSupportHardware;
    private final android.view.View mTargetView;
    private android.graphics.CanvasProperty<java.lang.Float> mTopProp;
    private boolean mVisible;
    private float mGlowAlpha = 0.0f;
    private float mGlowScale = 1.0f;
    private final android.view.animation.Interpolator mInterpolator = new android.inputmethodservice.navigationbar.KeyButtonRipple.LogInterpolator();
    private final android.os.Handler mHandler = new android.os.Handler();
    private final java.util.HashSet<android.animation.Animator> mRunningAnimations = new java.util.HashSet<>();
    private final java.util.ArrayList<android.animation.Animator> mTmpArray = new java.util.ArrayList<>();
    private final android.inputmethodservice.navigationbar.KeyButtonRipple.TraceAnimatorListener mExitHwTraceAnimator = new android.inputmethodservice.navigationbar.KeyButtonRipple.TraceAnimatorListener("exitHardware");
    private final android.inputmethodservice.navigationbar.KeyButtonRipple.TraceAnimatorListener mEnterHwTraceAnimator = new android.inputmethodservice.navigationbar.KeyButtonRipple.TraceAnimatorListener("enterHardware");
    private android.inputmethodservice.navigationbar.KeyButtonRipple.Type mType = android.inputmethodservice.navigationbar.KeyButtonRipple.Type.ROUNDED_RECT;
    private final android.animation.AnimatorListenerAdapter mAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: android.inputmethodservice.navigationbar.KeyButtonRipple.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            android.inputmethodservice.navigationbar.KeyButtonRipple.this.mRunningAnimations.remove(animator);
            if (android.inputmethodservice.navigationbar.KeyButtonRipple.this.mRunningAnimations.isEmpty() && !android.inputmethodservice.navigationbar.KeyButtonRipple.this.mPressed) {
                android.inputmethodservice.navigationbar.KeyButtonRipple.this.mVisible = false;
                android.inputmethodservice.navigationbar.KeyButtonRipple.this.mDrawingHardwareGlow = false;
                android.inputmethodservice.navigationbar.KeyButtonRipple.this.invalidateSelf();
            }
        }
    };

    public enum Type {
        OVAL,
        ROUNDED_RECT
    }

    KeyButtonRipple(android.content.Context context, android.view.View view, int i) {
        this.mMaxWidthResource = i;
        this.mMaxWidth = context.getResources().getDimensionPixelSize(i);
        this.mTargetView = view;
    }

    public void updateResources() {
        this.mMaxWidth = this.mTargetView.getContext().getResources().getDimensionPixelSize(this.mMaxWidthResource);
        invalidateSelf();
    }

    public void setDarkIntensity(float f) {
        this.mDark = f >= 0.5f;
    }

    public void setDelayTouchFeedback(boolean z) {
        this.mDelayTouchFeedback = z;
    }

    public void setType(android.inputmethodservice.navigationbar.KeyButtonRipple.Type type) {
        this.mType = type;
    }

    private android.graphics.Paint getRipplePaint() {
        if (this.mRipplePaint == null) {
            this.mRipplePaint = new android.graphics.Paint();
            this.mRipplePaint.setAntiAlias(true);
            this.mRipplePaint.setColor(this.mLastDark ? -16777216 : -1);
        }
        return this.mRipplePaint;
    }

    private void drawSoftware(android.graphics.Canvas canvas) {
        if (this.mGlowAlpha > 0.0f) {
            android.graphics.Paint ripplePaint = getRipplePaint();
            ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            float width = getBounds().width();
            float height = getBounds().height();
            boolean z = width > height;
            float rippleSize = getRippleSize() * this.mGlowScale * 0.5f;
            float f = width * 0.5f;
            float f2 = height * 0.5f;
            float f3 = z ? rippleSize : f;
            if (z) {
                rippleSize = f2;
            }
            float f4 = z ? f2 : f;
            if (this.mType == android.inputmethodservice.navigationbar.KeyButtonRipple.Type.ROUNDED_RECT) {
                canvas.drawRoundRect(f - f3, f2 - rippleSize, f3 + f, f2 + rippleSize, f4, f4, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f, f2);
            float min = java.lang.Math.min(f3, rippleSize);
            float f5 = -min;
            canvas.drawOval(f5, f5, min, min, ripplePaint);
            canvas.restore();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        this.mSupportHardware = canvas.isHardwareAccelerated();
        if (this.mSupportHardware) {
            drawHardware((android.graphics.RecordingCanvas) canvas);
        } else {
            drawSoftware(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    private boolean isHorizontal() {
        return getBounds().width() > getBounds().height();
    }

    private void drawHardware(android.graphics.RecordingCanvas recordingCanvas) {
        if (this.mDrawingHardwareGlow) {
            if (this.mType == android.inputmethodservice.navigationbar.KeyButtonRipple.Type.ROUNDED_RECT) {
                recordingCanvas.drawRoundRect(this.mLeftProp, this.mTopProp, this.mRightProp, this.mBottomProp, this.mRxProp, this.mRyProp, this.mPaintProp);
            } else {
                recordingCanvas.drawCircle(android.graphics.CanvasProperty.createFloat(getBounds().width() / 2), android.graphics.CanvasProperty.createFloat(getBounds().height() / 2), android.graphics.CanvasProperty.createFloat((java.lang.Math.min(getBounds().width(), getBounds().height()) * 1.0f) / 2.0f), this.mPaintProp);
            }
        }
    }

    public float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    public float getGlowScale() {
        return this.mGlowScale;
    }

    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    private float getMaxGlowAlpha() {
        return this.mLastDark ? 0.1f : 0.2f;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                z = false;
                break;
            }
            if (iArr[i] != 16842919) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (z == this.mPressed) {
            return false;
        }
        setPressed(z);
        this.mPressed = z;
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        endAnimations("jumpToCurrentState", false);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    public void setPressed(boolean z) {
        if (this.mDark != this.mLastDark && z) {
            this.mRipplePaint = null;
            this.mLastDark = this.mDark;
        }
        if (this.mSupportHardware) {
            setPressedHardware(z);
        } else {
            setPressedSoftware(z);
        }
    }

    public void abortDelayedRipple() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void endAnimations(java.lang.String str, boolean z) {
        android.os.Trace.beginSection("KeyButtonRipple.endAnim: reason=" + str + " cancel=" + z);
        android.os.Trace.endSection();
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            android.animation.Animator animator = this.mTmpArray.get(i);
            if (z) {
                animator.cancel();
            } else {
                animator.end();
            }
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void setPressedSoftware(boolean z) {
        if (z) {
            if (this.mDelayTouchFeedback) {
                if (this.mRunningAnimations.isEmpty()) {
                    this.mHandler.removeCallbacksAndMessages(null);
                    this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.inputmethodservice.navigationbar.KeyButtonRipple$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.inputmethodservice.navigationbar.KeyButtonRipple.this.enterSoftware();
                        }
                    }, android.view.ViewConfiguration.getTapTimeout());
                    return;
                } else {
                    if (this.mVisible) {
                        enterSoftware();
                        return;
                    }
                    return;
                }
            }
            enterSoftware();
            return;
        }
        exitSoftware();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterSoftware() {
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        this.mGlowAlpha = getMaxGlowAlpha();
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, "glowScale", 0.0f, GLOW_MAX_SCALE_FACTOR);
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.setDuration(350L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
        if (this.mDelayTouchFeedback && !this.mPressed) {
            exitSoftware();
        }
    }

    private void exitSoftware() {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, 0.0f);
        ofFloat.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        ofFloat.setDuration(450L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
    }

    private void setPressedHardware(boolean z) {
        if (z) {
            if (this.mDelayTouchFeedback) {
                if (this.mRunningAnimations.isEmpty()) {
                    this.mHandler.removeCallbacksAndMessages(null);
                    this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.inputmethodservice.navigationbar.KeyButtonRipple$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.inputmethodservice.navigationbar.KeyButtonRipple.this.enterHardware();
                        }
                    }, android.view.ViewConfiguration.getTapTimeout());
                    return;
                } else {
                    if (this.mVisible) {
                        enterHardware();
                        return;
                    }
                    return;
                }
            }
            enterHardware();
            return;
        }
        exitHardware();
    }

    private void setExtendStart(android.graphics.CanvasProperty<java.lang.Float> canvasProperty) {
        if (isHorizontal()) {
            this.mLeftProp = canvasProperty;
        } else {
            this.mTopProp = canvasProperty;
        }
    }

    private android.graphics.CanvasProperty<java.lang.Float> getExtendStart() {
        return isHorizontal() ? this.mLeftProp : this.mTopProp;
    }

    private void setExtendEnd(android.graphics.CanvasProperty<java.lang.Float> canvasProperty) {
        if (isHorizontal()) {
            this.mRightProp = canvasProperty;
        } else {
            this.mBottomProp = canvasProperty;
        }
    }

    private android.graphics.CanvasProperty<java.lang.Float> getExtendEnd() {
        return isHorizontal() ? this.mRightProp : this.mBottomProp;
    }

    private int getExtendSize() {
        return isHorizontal() ? getBounds().width() : getBounds().height();
    }

    private int getRippleSize() {
        return java.lang.Math.min(isHorizontal() ? getBounds().width() : getBounds().height(), this.mMaxWidth);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterHardware() {
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        setExtendStart(android.graphics.CanvasProperty.createFloat(getExtendSize() / 2));
        android.view.RenderNodeAnimator renderNodeAnimator = new android.view.RenderNodeAnimator(getExtendStart(), (getExtendSize() / 2) - ((getRippleSize() * GLOW_MAX_SCALE_FACTOR) / 2.0f));
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        setExtendEnd(android.graphics.CanvasProperty.createFloat(getExtendSize() / 2));
        android.view.RenderNodeAnimator renderNodeAnimator2 = new android.view.RenderNodeAnimator(getExtendEnd(), (getExtendSize() / 2) + ((getRippleSize() * GLOW_MAX_SCALE_FACTOR) / 2.0f));
        renderNodeAnimator2.setDuration(350L);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
        renderNodeAnimator2.addListener(this.mEnterHwTraceAnimator);
        renderNodeAnimator2.setTarget(this.mTargetView);
        if (isHorizontal()) {
            this.mTopProp = android.graphics.CanvasProperty.createFloat(0.0f);
            this.mBottomProp = android.graphics.CanvasProperty.createFloat(getBounds().height());
            this.mRxProp = android.graphics.CanvasProperty.createFloat(getBounds().height() / 2);
            this.mRyProp = android.graphics.CanvasProperty.createFloat(getBounds().height() / 2);
        } else {
            this.mLeftProp = android.graphics.CanvasProperty.createFloat(0.0f);
            this.mRightProp = android.graphics.CanvasProperty.createFloat(getBounds().width());
            this.mRxProp = android.graphics.CanvasProperty.createFloat(getBounds().width() / 2);
            this.mRyProp = android.graphics.CanvasProperty.createFloat(getBounds().width() / 2);
        }
        this.mGlowScale = GLOW_MAX_SCALE_FACTOR;
        this.mGlowAlpha = getMaxGlowAlpha();
        this.mRipplePaint = getRipplePaint();
        this.mRipplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        this.mPaintProp = android.graphics.CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        invalidateSelf();
        if (this.mDelayTouchFeedback && !this.mPressed) {
            exitHardware();
        }
    }

    private void exitHardware() {
        this.mPaintProp = android.graphics.CanvasProperty.createPaint(getRipplePaint());
        android.view.RenderNodeAnimator renderNodeAnimator = new android.view.RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        renderNodeAnimator.setDuration(450L);
        renderNodeAnimator.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener(this.mExitHwTraceAnimator);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    private static final class TraceAnimatorListener extends android.animation.AnimatorListenerAdapter {
        private final java.lang.String mName;

        TraceAnimatorListener(java.lang.String str) {
            this.mName = str;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            android.os.Trace.beginSection("KeyButtonRipple.start." + this.mName);
            android.os.Trace.endSection();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            android.os.Trace.beginSection("KeyButtonRipple.cancel." + this.mName);
            android.os.Trace.endSection();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            android.os.Trace.beginSection("KeyButtonRipple.end." + this.mName);
            android.os.Trace.endSection();
        }
    }

    private static final class LogInterpolator implements android.view.animation.Interpolator {
        private LogInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return 1.0f - ((float) java.lang.Math.pow(400.0d, (-f) * 1.4d));
        }
    }
}
