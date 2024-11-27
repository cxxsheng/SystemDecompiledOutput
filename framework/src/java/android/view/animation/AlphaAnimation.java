package android.view.animation;

/* loaded from: classes4.dex */
public class AlphaAnimation extends android.view.animation.Animation {
    private float mFromAlpha;
    private float mToAlpha;

    public AlphaAnimation(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AlphaAnimation);
        this.mFromAlpha = obtainStyledAttributes.getFloat(0, 1.0f);
        this.mToAlpha = obtainStyledAttributes.getFloat(1, 1.0f);
        obtainStyledAttributes.recycle();
    }

    public AlphaAnimation(float f, float f2) {
        this.mFromAlpha = f;
        this.mToAlpha = f2;
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        float f2 = this.mFromAlpha;
        transformation.setAlpha(f2 + ((this.mToAlpha - f2) * f));
    }

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    @Override // android.view.animation.Animation
    public boolean willChangeBounds() {
        return false;
    }

    @Override // android.view.animation.Animation
    public boolean hasAlpha() {
        return true;
    }
}
