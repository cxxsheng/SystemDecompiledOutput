package android.graphics;

/* loaded from: classes.dex */
public class PaintFlagsDrawFilter extends android.graphics.DrawFilter {
    private static native long nativeConstructor(int i, int i2);

    public PaintFlagsDrawFilter(int i, int i2) {
        this.mNativeInt = nativeConstructor(i, i2);
    }
}
