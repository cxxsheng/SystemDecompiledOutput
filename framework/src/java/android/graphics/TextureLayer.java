package android.graphics;

/* loaded from: classes.dex */
public final class TextureLayer implements java.lang.AutoCloseable {
    private com.android.internal.util.VirtualRefBasePtr mFinalizer;
    private android.graphics.HardwareRenderer mRenderer;

    private static native boolean nPrepare(long j, int i, int i2, boolean z);

    private static native void nSetLayerPaint(long j, long j2);

    private static native void nSetSurfaceTexture(long j, android.graphics.SurfaceTexture surfaceTexture);

    private static native void nSetTransform(long j, long j2);

    private static native void nUpdateSurfaceTexture(long j);

    private TextureLayer(android.graphics.HardwareRenderer hardwareRenderer, long j) {
        if (hardwareRenderer == null || j == 0) {
            throw new java.lang.IllegalArgumentException("Either hardware renderer: " + hardwareRenderer + " or deferredUpdater: " + j + " is invalid");
        }
        this.mRenderer = hardwareRenderer;
        this.mFinalizer = new com.android.internal.util.VirtualRefBasePtr(j);
    }

    public void setLayerPaint(android.graphics.Paint paint) {
        nSetLayerPaint(this.mFinalizer.get(), paint != null ? paint.getNativeInstance() : 0L);
        this.mRenderer.pushLayerUpdate(this);
    }

    private boolean isValid() {
        return (this.mFinalizer == null || this.mFinalizer.get() == 0) ? false : true;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!isValid()) {
            return;
        }
        this.mRenderer.onLayerDestroyed(this);
        this.mRenderer = null;
        this.mFinalizer.release();
        this.mFinalizer = null;
    }

    long getDeferredLayerUpdater() {
        return this.mFinalizer.get();
    }

    public boolean copyInto(android.graphics.Bitmap bitmap) {
        return this.mRenderer.copyLayerInto(this, bitmap);
    }

    public boolean prepare(int i, int i2, boolean z) {
        return nPrepare(this.mFinalizer.get(), i, i2, z);
    }

    public void setTransform(android.graphics.Matrix matrix) {
        nSetTransform(this.mFinalizer.get(), matrix.ni());
        this.mRenderer.pushLayerUpdate(this);
    }

    public void detachSurfaceTexture() {
        this.mRenderer.detachSurfaceTexture(this.mFinalizer.get());
    }

    long getLayerHandle() {
        return this.mFinalizer.get();
    }

    public void setSurfaceTexture(android.graphics.SurfaceTexture surfaceTexture) {
        nSetSurfaceTexture(this.mFinalizer.get(), surfaceTexture);
        this.mRenderer.pushLayerUpdate(this);
    }

    public void updateSurfaceTexture() {
        nUpdateSurfaceTexture(this.mFinalizer.get());
        this.mRenderer.pushLayerUpdate(this);
    }

    static android.graphics.TextureLayer adoptTextureLayer(android.graphics.HardwareRenderer hardwareRenderer, long j) {
        return new android.graphics.TextureLayer(hardwareRenderer, j);
    }
}
