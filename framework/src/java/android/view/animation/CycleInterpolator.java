package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class CycleInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    private float mCycles;

    public CycleInterpolator(float f) {
        this.mCycles = f;
    }

    public CycleInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public CycleInterpolator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CycleInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.CycleInterpolator);
        }
        this.mCycles = obtainAttributes.getFloat(0, 1.0f);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) java.lang.Math.sin(this.mCycles * 2.0f * 3.141592653589793d * f);
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createCycleInterpolator(this.mCycles);
    }
}
