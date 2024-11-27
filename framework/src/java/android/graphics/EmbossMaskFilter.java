package android.graphics;

/* loaded from: classes.dex */
public class EmbossMaskFilter extends android.graphics.MaskFilter {
    private static native long nativeConstructor(float[] fArr, float f, float f2, float f3);

    @java.lang.Deprecated
    public EmbossMaskFilter(float[] fArr, float f, float f2, float f3) {
        if (fArr.length < 3) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        this.native_instance = nativeConstructor(fArr, f, f2, f3);
    }
}
