package android.graphics;

/* loaded from: classes.dex */
public class CornerPathEffect extends android.graphics.PathEffect {
    private static native long nativeCreate(float f);

    public CornerPathEffect(float f) {
        this.native_instance = nativeCreate(f);
    }
}
