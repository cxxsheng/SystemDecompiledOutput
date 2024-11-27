package android.graphics.animation;

/* loaded from: classes.dex */
public final class NativeInterpolatorFactory {
    public static native long createAccelerateDecelerateInterpolator();

    public static native long createAccelerateInterpolator(float f);

    public static native long createAnticipateInterpolator(float f);

    public static native long createAnticipateOvershootInterpolator(float f);

    public static native long createBounceInterpolator();

    public static native long createCycleInterpolator(float f);

    public static native long createDecelerateInterpolator(float f);

    public static native long createLinearInterpolator();

    public static native long createLutInterpolator(float[] fArr);

    public static native long createOvershootInterpolator(float f);

    public static native long createPathInterpolator(float[] fArr, float[] fArr2);

    private NativeInterpolatorFactory() {
    }

    public static long createNativeInterpolator(android.animation.TimeInterpolator timeInterpolator, long j) {
        if (timeInterpolator == null) {
            return createLinearInterpolator();
        }
        if (android.graphics.animation.RenderNodeAnimator.isNativeInterpolator(timeInterpolator)) {
            return ((android.graphics.animation.NativeInterpolator) timeInterpolator).createNativeInterpolator();
        }
        return android.graphics.animation.FallbackLUTInterpolator.createNativeInterpolator(timeInterpolator, j);
    }
}
