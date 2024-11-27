package android.graphics.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes.dex */
public class FallbackLUTInterpolator implements android.graphics.animation.NativeInterpolator, android.animation.TimeInterpolator {
    private static final int MAX_SAMPLE_POINTS = 300;
    private final float[] mLut;
    private android.animation.TimeInterpolator mSourceInterpolator;

    public FallbackLUTInterpolator(android.animation.TimeInterpolator timeInterpolator, long j) {
        this.mSourceInterpolator = timeInterpolator;
        this.mLut = createLUT(timeInterpolator, j);
    }

    private static float[] createLUT(android.animation.TimeInterpolator timeInterpolator, long j) {
        int min = java.lang.Math.min(java.lang.Math.max(2, (int) java.lang.Math.ceil(j / ((int) (android.view.Choreographer.getInstance().getFrameIntervalNanos() / 1000000)))), 300);
        float[] fArr = new float[min];
        float f = min - 1;
        for (int i = 0; i < min; i++) {
            fArr[i] = timeInterpolator.getInterpolation(i / f);
        }
        return fArr;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createLutInterpolator(this.mLut);
    }

    public static long createNativeInterpolator(android.animation.TimeInterpolator timeInterpolator, long j) {
        return android.graphics.animation.NativeInterpolatorFactory.createLutInterpolator(createLUT(timeInterpolator, j));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return this.mSourceInterpolator.getInterpolation(f);
    }
}
