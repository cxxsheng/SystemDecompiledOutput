package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class AnticipateOvershootInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    private final float mTension;

    public AnticipateOvershootInterpolator() {
        this.mTension = 3.0f;
    }

    public AnticipateOvershootInterpolator(float f) {
        this.mTension = f * 1.5f;
    }

    public AnticipateOvershootInterpolator(float f, float f2) {
        this.mTension = f * f2;
    }

    public AnticipateOvershootInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AnticipateOvershootInterpolator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AnticipateOvershootInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AnticipateOvershootInterpolator);
        }
        this.mTension = obtainAttributes.getFloat(0, 2.0f) * obtainAttributes.getFloat(1, 1.5f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    private static float a(float f, float f2) {
        return f * f * (((1.0f + f2) * f) - f2);
    }

    private static float o(float f, float f2) {
        return f * f * (((1.0f + f2) * f) + f2);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f < 0.5f ? a(f * 2.0f, this.mTension) * 0.5f : (o((f * 2.0f) - 2.0f, this.mTension) + 2.0f) * 0.5f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createAnticipateOvershootInterpolator(this.mTension);
    }
}
