package android.graphics;

/* loaded from: classes.dex */
public class SumPathEffect extends android.graphics.PathEffect {
    private static native long nativeCreate(long j, long j2);

    public SumPathEffect(android.graphics.PathEffect pathEffect, android.graphics.PathEffect pathEffect2) {
        this.native_instance = nativeCreate(pathEffect.native_instance, pathEffect2.native_instance);
    }
}
