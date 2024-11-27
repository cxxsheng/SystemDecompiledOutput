package android.graphics;

/* loaded from: classes.dex */
public final class CanvasProperty<T> {
    private com.android.internal.util.VirtualRefBasePtr mProperty;

    private static native long nCreateFloat(float f);

    private static native long nCreatePaint(long j);

    public static android.graphics.CanvasProperty<java.lang.Float> createFloat(float f) {
        return new android.graphics.CanvasProperty<>(nCreateFloat(f));
    }

    public static android.graphics.CanvasProperty<android.graphics.Paint> createPaint(android.graphics.Paint paint) {
        return new android.graphics.CanvasProperty<>(nCreatePaint(paint.getNativeInstance()));
    }

    private CanvasProperty(long j) {
        this.mProperty = new com.android.internal.util.VirtualRefBasePtr(j);
    }

    public long getNativeContainer() {
        return this.mProperty.get();
    }
}
