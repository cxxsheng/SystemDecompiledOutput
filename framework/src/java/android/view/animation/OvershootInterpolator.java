package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class OvershootInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    private final float mTension;

    public OvershootInterpolator() {
        this.mTension = 2.0f;
    }

    public OvershootInterpolator(float f) {
        this.mTension = f;
    }

    public OvershootInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public OvershootInterpolator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.OvershootInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.OvershootInterpolator);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * (((this.mTension + 1.0f) * f2) + this.mTension)) + 1.0f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createOvershootInterpolator(this.mTension);
    }
}
