package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class BounceInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    public BounceInterpolator() {
    }

    public BounceInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
    }

    private static float bounce(float f) {
        return f * f * 8.0f;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f * 1.1226f;
        return f2 < 0.3535f ? bounce(f2) : f2 < 0.7408f ? bounce(f2 - 0.54719f) + 0.7f : f2 < 0.9644f ? bounce(f2 - 0.8526f) + 0.9f : bounce(f2 - 1.0435f) + 0.95f;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createBounceInterpolator();
    }
}
