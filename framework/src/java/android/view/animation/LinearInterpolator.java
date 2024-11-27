package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class LinearInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    public LinearInterpolator() {
    }

    public LinearInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createLinearInterpolator();
    }
}
