package android.view;

/* loaded from: classes4.dex */
public interface SurfaceHolder {

    @java.lang.Deprecated
    public static final int SURFACE_TYPE_GPU = 2;

    @java.lang.Deprecated
    public static final int SURFACE_TYPE_HARDWARE = 1;

    @java.lang.Deprecated
    public static final int SURFACE_TYPE_NORMAL = 0;

    @java.lang.Deprecated
    public static final int SURFACE_TYPE_PUSH_BUFFERS = 3;

    public interface Callback {
        void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3);

        void surfaceCreated(android.view.SurfaceHolder surfaceHolder);

        void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder);
    }

    void addCallback(android.view.SurfaceHolder.Callback callback);

    android.view.Surface getSurface();

    android.graphics.Rect getSurfaceFrame();

    boolean isCreating();

    android.graphics.Canvas lockCanvas();

    android.graphics.Canvas lockCanvas(android.graphics.Rect rect);

    void removeCallback(android.view.SurfaceHolder.Callback callback);

    void setFixedSize(int i, int i2);

    void setFormat(int i);

    void setKeepScreenOn(boolean z);

    void setSizeFromLayout();

    @java.lang.Deprecated
    void setType(int i);

    void unlockCanvasAndPost(android.graphics.Canvas canvas);

    public static class BadSurfaceTypeException extends java.lang.RuntimeException {
        public BadSurfaceTypeException() {
        }

        public BadSurfaceTypeException(java.lang.String str) {
            super(str);
        }
    }

    public interface Callback2 extends android.view.SurfaceHolder.Callback {
        void surfaceRedrawNeeded(android.view.SurfaceHolder surfaceHolder);

        default void surfaceRedrawNeededAsync(android.view.SurfaceHolder surfaceHolder, java.lang.Runnable runnable) {
            surfaceRedrawNeeded(surfaceHolder);
            runnable.run();
        }
    }

    default android.graphics.Canvas lockHardwareCanvas() {
        throw new java.lang.IllegalStateException("This SurfaceHolder doesn't support lockHardwareCanvas");
    }
}
