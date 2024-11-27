package android.graphics.drawable;

/* loaded from: classes.dex */
class RippleBackground extends android.graphics.drawable.RippleComponent {
    private static final android.animation.TimeInterpolator LINEAR_INTERPOLATOR = new android.view.animation.LinearInterpolator();
    private static final android.graphics.drawable.RippleBackground.BackgroundProperty OPACITY = new android.graphics.drawable.RippleBackground.BackgroundProperty("opacity") { // from class: android.graphics.drawable.RippleBackground.1
        @Override // android.util.FloatProperty
        public void setValue(android.graphics.drawable.RippleBackground rippleBackground, float f) {
            rippleBackground.mOpacity = f;
            rippleBackground.invalidateSelf();
        }

        @Override // android.util.Property
        public java.lang.Float get(android.graphics.drawable.RippleBackground rippleBackground) {
            return java.lang.Float.valueOf(rippleBackground.mOpacity);
        }
    };
    private static final int OPACITY_DURATION = 80;
    private android.animation.ObjectAnimator mAnimator;
    private boolean mFocused;
    private boolean mHovered;
    private boolean mIsBounded;
    private float mOpacity;

    public RippleBackground(android.graphics.drawable.RippleDrawable rippleDrawable, android.graphics.Rect rect, boolean z) {
        super(rippleDrawable, rect);
        this.mOpacity = 0.0f;
        this.mFocused = false;
        this.mHovered = false;
        this.mIsBounded = z;
    }

    public boolean isVisible() {
        return this.mOpacity > 0.0f;
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        int alpha = paint.getAlpha();
        int min = java.lang.Math.min((int) ((alpha * this.mOpacity) + 0.5f), 255);
        if (min > 0) {
            paint.setAlpha(min);
            canvas.drawCircle(0.0f, 0.0f, this.mTargetRadius, paint);
            paint.setAlpha(alpha);
        }
    }

    public void setState(boolean z, boolean z2, boolean z3) {
        if (!this.mFocused) {
            z = z && !z3;
        }
        if (!this.mHovered) {
            z2 = z2 && !z3;
        }
        if (this.mHovered != z2 || this.mFocused != z) {
            this.mHovered = z2;
            this.mFocused = z;
            onStateChanged();
        }
    }

    private void onStateChanged() {
        float f = this.mFocused ? 0.6f : this.mHovered ? 0.2f : 0.0f;
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        this.mAnimator = android.animation.ObjectAnimator.ofFloat(this, OPACITY, f);
        this.mAnimator.setDuration(80L);
        this.mAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        this.mAnimator.start();
    }

    public void jumpToFinal() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator = null;
        }
    }

    private static abstract class BackgroundProperty extends android.util.FloatProperty<android.graphics.drawable.RippleBackground> {
        public BackgroundProperty(java.lang.String str) {
            super(str);
        }
    }
}
