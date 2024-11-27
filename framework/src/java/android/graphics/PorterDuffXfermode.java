package android.graphics;

/* loaded from: classes.dex */
public class PorterDuffXfermode extends android.graphics.Xfermode {
    public PorterDuffXfermode(android.graphics.PorterDuff.Mode mode) {
        this.porterDuffMode = mode.nativeInt;
    }
}
