package android.graphics;

/* loaded from: classes.dex */
public class DashPathEffect extends android.graphics.PathEffect {
    private static native long nativeCreate(float[] fArr, float f);

    public DashPathEffect(float[] fArr, float f) {
        if (fArr.length < 2) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        this.native_instance = nativeCreate(fArr, f);
    }
}
