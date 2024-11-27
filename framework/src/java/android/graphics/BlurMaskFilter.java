package android.graphics;

/* loaded from: classes.dex */
public class BlurMaskFilter extends android.graphics.MaskFilter {
    private static native long nativeConstructor(float f, int i);

    public enum Blur {
        NORMAL(0),
        SOLID(1),
        OUTER(2),
        INNER(3);

        final int native_int;

        Blur(int i) {
            this.native_int = i;
        }
    }

    public BlurMaskFilter(float f, android.graphics.BlurMaskFilter.Blur blur) {
        this.native_instance = nativeConstructor(f, blur.native_int);
    }
}
