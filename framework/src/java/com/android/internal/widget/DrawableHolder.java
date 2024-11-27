package com.android.internal.widget;

/* loaded from: classes5.dex */
public class DrawableHolder implements android.animation.Animator.AnimatorListener {
    private static final boolean DBG = false;
    public static final android.view.animation.DecelerateInterpolator EASE_OUT_INTERPOLATOR = new android.view.animation.DecelerateInterpolator();
    private static final java.lang.String TAG = "DrawableHolder";
    private float mAlpha;
    private java.util.ArrayList<android.animation.ObjectAnimator> mAnimators;
    private android.graphics.drawable.BitmapDrawable mDrawable;
    private java.util.ArrayList<android.animation.ObjectAnimator> mNeedToStart;
    private float mScaleX;
    private float mScaleY;
    private float mX;
    private float mY;

    public DrawableHolder(android.graphics.drawable.BitmapDrawable bitmapDrawable) {
        this(bitmapDrawable, 0.0f, 0.0f);
    }

    public DrawableHolder(android.graphics.drawable.BitmapDrawable bitmapDrawable, float f, float f2) {
        this.mX = 0.0f;
        this.mY = 0.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mAlpha = 1.0f;
        this.mAnimators = new java.util.ArrayList<>();
        this.mNeedToStart = new java.util.ArrayList<>();
        this.mDrawable = bitmapDrawable;
        this.mX = f;
        this.mY = f2;
        this.mDrawable.getPaint().setAntiAlias(true);
        this.mDrawable.setBounds(0, 0, this.mDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
    }

    public android.animation.ObjectAnimator addAnimTo(long j, long j2, java.lang.String str, float f, boolean z) {
        if (z) {
            removeAnimationFor(str);
        }
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, str, f);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        ofFloat.setInterpolator(EASE_OUT_INTERPOLATOR);
        addAnimation(ofFloat, z);
        return ofFloat;
    }

    public void removeAnimationFor(java.lang.String str) {
        java.util.Iterator it = ((java.util.ArrayList) this.mAnimators.clone()).iterator();
        while (it.hasNext()) {
            android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) it.next();
            if (str.equals(objectAnimator.getPropertyName())) {
                objectAnimator.cancel();
            }
        }
    }

    public void clearAnimations() {
        java.util.Iterator<android.animation.ObjectAnimator> it = this.mAnimators.iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
        this.mAnimators.clear();
    }

    private com.android.internal.widget.DrawableHolder addAnimation(android.animation.ObjectAnimator objectAnimator, boolean z) {
        if (objectAnimator != null) {
            this.mAnimators.add(objectAnimator);
        }
        this.mNeedToStart.add(objectAnimator);
        return this;
    }

    public void draw(android.graphics.Canvas canvas) {
        if (this.mAlpha <= 0.00390625f) {
            return;
        }
        canvas.save(1);
        canvas.translate(this.mX, this.mY);
        canvas.scale(this.mScaleX, this.mScaleY);
        canvas.translate(getWidth() * (-0.5f), getHeight() * (-0.5f));
        this.mDrawable.setAlpha(java.lang.Math.round(this.mAlpha * 255.0f));
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public void startAnimations(android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        for (int i = 0; i < this.mNeedToStart.size(); i++) {
            android.animation.ObjectAnimator objectAnimator = this.mNeedToStart.get(i);
            objectAnimator.addUpdateListener(animatorUpdateListener);
            objectAnimator.addListener(this);
            objectAnimator.start();
        }
        this.mNeedToStart.clear();
    }

    public void setX(float f) {
        this.mX = f;
    }

    public void setY(float f) {
        this.mY = f;
    }

    public void setScaleX(float f) {
        this.mScaleX = f;
    }

    public void setScaleY(float f) {
        this.mScaleY = f;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public android.graphics.drawable.BitmapDrawable getDrawable() {
        return this.mDrawable;
    }

    public int getWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(android.animation.Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(android.animation.Animator animator) {
        this.mAnimators.remove(animator);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(android.animation.Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(android.animation.Animator animator) {
    }
}
