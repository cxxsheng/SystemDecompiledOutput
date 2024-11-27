package com.android.internal.policy;

/* loaded from: classes5.dex */
public class ClipRectTBAnimation extends android.view.animation.ClipRectAnimation {
    private final int mFromTranslateY;
    private float mNormalizedTime;
    private final int mToTranslateY;
    private final android.view.animation.Interpolator mTranslateInterpolator;

    public ClipRectTBAnimation(int i, int i2, int i3, int i4, int i5, int i6, android.view.animation.Interpolator interpolator) {
        super(0, i, 0, i2, 0, i3, 0, i4);
        this.mFromTranslateY = i5;
        this.mToTranslateY = i6;
        this.mTranslateInterpolator = interpolator;
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long j, android.view.animation.Transformation transformation) {
        float f;
        long startOffset = getStartOffset();
        long duration = getDuration();
        if (duration != 0) {
            f = (j - (getStartTime() + startOffset)) / duration;
        } else {
            f = j < getStartTime() ? 0.0f : 1.0f;
        }
        this.mNormalizedTime = f;
        return super.getTransformation(j, transformation);
    }

    @Override // android.view.animation.ClipRectAnimation, android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        int interpolation = (int) (this.mFromTranslateY + ((this.mToTranslateY - this.mFromTranslateY) * this.mTranslateInterpolator.getInterpolation(this.mNormalizedTime)));
        android.graphics.Rect clipRect = transformation.getClipRect();
        transformation.setClipRect(clipRect.left, (this.mFromRect.top - interpolation) + ((int) ((this.mToRect.top - this.mFromRect.top) * f)), clipRect.right, (this.mFromRect.bottom - interpolation) + ((int) ((this.mToRect.bottom - this.mFromRect.bottom) * f)));
    }
}
