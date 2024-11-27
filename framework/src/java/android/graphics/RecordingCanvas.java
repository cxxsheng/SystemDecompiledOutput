package android.graphics;

/* loaded from: classes.dex */
public final class RecordingCanvas extends android.graphics.BaseRecordingCanvas {
    private static final int POOL_LIMIT = 25;
    private int mHeight;
    public android.graphics.RenderNode mNode;
    private int mWidth;
    public static final int MAX_BITMAP_SIZE = getPanelFrameSize();
    private static final android.util.Pools.SynchronizedPool<android.graphics.RecordingCanvas> sPool = new android.util.Pools.SynchronizedPool<>(25);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nCreateDisplayListCanvas(long j, int i, int i2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawCircle(long j, long j2, long j3, long j4, long j5);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawRenderNode(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawRipple(long j, long j2, long j3, long j4, long j5, long j6, long j7, int i, long j8);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawRoundRect(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawTextureLayer(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDrawWebViewFunctor(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nEnableZ(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nFinishRecording(long j, long j2);

    private static native int nGetMaximumTextureHeight();

    private static native int nGetMaximumTextureWidth();

    @dalvik.annotation.optimization.CriticalNative
    private static native void nResetDisplayListCanvas(long j, long j2, int i, int i2);

    private static int getPanelFrameSize() {
        return java.lang.Math.max(android.os.SystemProperties.getInt("ro.hwui.max_texture_allocation_size", 104857600), 104857600);
    }

    static android.graphics.RecordingCanvas obtain(android.graphics.RenderNode renderNode, int i, int i2) {
        if (renderNode == null) {
            throw new java.lang.IllegalArgumentException("node cannot be null");
        }
        android.graphics.RecordingCanvas acquire = sPool.acquire();
        if (acquire == null) {
            acquire = new android.graphics.RecordingCanvas(renderNode, i, i2);
        } else {
            nResetDisplayListCanvas(acquire.mNativeCanvasWrapper, renderNode.mNativeRenderNode, i, i2);
        }
        acquire.mNode = renderNode;
        acquire.mWidth = i;
        acquire.mHeight = i2;
        return acquire;
    }

    void recycle() {
        this.mNode = null;
        sPool.release(this);
    }

    void finishRecording(android.graphics.RenderNode renderNode) {
        nFinishRecording(this.mNativeCanvasWrapper, renderNode.mNativeRenderNode);
    }

    private RecordingCanvas(android.graphics.RenderNode renderNode, int i, int i2) {
        super(nCreateDisplayListCanvas(renderNode.mNativeRenderNode, i, i2));
        this.mDensity = 0;
    }

    @Override // android.graphics.Canvas
    public void setDensity(int i) {
    }

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public boolean isHardwareAccelerated() {
        return true;
    }

    @Override // android.graphics.Canvas
    public void setBitmap(android.graphics.Bitmap bitmap) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // android.graphics.Canvas
    public boolean isOpaque() {
        return false;
    }

    @Override // android.graphics.Canvas
    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.Canvas
    public int getHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.Canvas
    public int getMaximumBitmapWidth() {
        return nGetMaximumTextureWidth();
    }

    @Override // android.graphics.Canvas
    public int getMaximumBitmapHeight() {
        return nGetMaximumTextureHeight();
    }

    @Override // android.graphics.Canvas
    public void enableZ() {
        nEnableZ(this.mNativeCanvasWrapper, true);
    }

    @Override // android.graphics.Canvas
    public void disableZ() {
        nEnableZ(this.mNativeCanvasWrapper, false);
    }

    public void drawWebViewFunctor(int i) {
        nDrawWebViewFunctor(this.mNativeCanvasWrapper, i);
    }

    @Override // android.graphics.Canvas
    public void drawRenderNode(android.graphics.RenderNode renderNode) {
        nDrawRenderNode(this.mNativeCanvasWrapper, renderNode.mNativeRenderNode);
    }

    public void drawTextureLayer(android.graphics.TextureLayer textureLayer) {
        nDrawTextureLayer(this.mNativeCanvasWrapper, textureLayer.getLayerHandle());
    }

    public void drawCircle(android.graphics.CanvasProperty<java.lang.Float> canvasProperty, android.graphics.CanvasProperty<java.lang.Float> canvasProperty2, android.graphics.CanvasProperty<java.lang.Float> canvasProperty3, android.graphics.CanvasProperty<android.graphics.Paint> canvasProperty4) {
        nDrawCircle(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer());
    }

    public void drawRipple(android.graphics.CanvasProperty<java.lang.Float> canvasProperty, android.graphics.CanvasProperty<java.lang.Float> canvasProperty2, android.graphics.CanvasProperty<java.lang.Float> canvasProperty3, android.graphics.CanvasProperty<android.graphics.Paint> canvasProperty4, android.graphics.CanvasProperty<java.lang.Float> canvasProperty5, android.graphics.CanvasProperty<java.lang.Float> canvasProperty6, int i, android.graphics.RuntimeShader runtimeShader) {
        nDrawRipple(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer(), canvasProperty5.getNativeContainer(), canvasProperty6.getNativeContainer(), i, runtimeShader.getNativeShaderBuilder());
    }

    public void drawRoundRect(android.graphics.CanvasProperty<java.lang.Float> canvasProperty, android.graphics.CanvasProperty<java.lang.Float> canvasProperty2, android.graphics.CanvasProperty<java.lang.Float> canvasProperty3, android.graphics.CanvasProperty<java.lang.Float> canvasProperty4, android.graphics.CanvasProperty<java.lang.Float> canvasProperty5, android.graphics.CanvasProperty<java.lang.Float> canvasProperty6, android.graphics.CanvasProperty<android.graphics.Paint> canvasProperty7) {
        nDrawRoundRect(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer(), canvasProperty5.getNativeContainer(), canvasProperty6.getNativeContainer(), canvasProperty7.getNativeContainer());
    }

    @Override // android.graphics.BaseCanvas
    protected void throwIfCannotDraw(android.graphics.Bitmap bitmap) {
        super.throwIfCannotDraw(bitmap);
        int byteCount = bitmap.getByteCount();
        if (byteCount > MAX_BITMAP_SIZE) {
            throw new java.lang.RuntimeException("Canvas: trying to draw too large(" + byteCount + "bytes) bitmap.");
        }
    }
}
