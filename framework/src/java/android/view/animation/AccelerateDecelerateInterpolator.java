package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class AccelerateDecelerateInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    public AccelerateDecelerateInterpolator() {
    }

    public AccelerateDecelerateInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return ((float) (java.lang.Math.cos((f + 1.0f) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createAccelerateDecelerateInterpolator();
    }
}
