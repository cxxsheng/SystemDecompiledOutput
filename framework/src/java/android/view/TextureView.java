package android.view;

/* loaded from: classes4.dex */
public class TextureView extends android.view.View {
    private static final java.lang.String LOG_TAG = "TextureView";
    private android.graphics.Canvas mCanvas;
    private boolean mHadSurface;
    private android.graphics.TextureLayer mLayer;
    private android.view.TextureView.SurfaceTextureListener mListener;
    private final java.lang.Object[] mLock;
    private final android.graphics.Matrix mMatrix;
    private boolean mMatrixChanged;
    private long mNativeWindow;
    private final java.lang.Object[] mNativeWindowLock;
    private boolean mOpaque;
    private int mSaveCount;
    private android.graphics.SurfaceTexture mSurface;
    private boolean mUpdateLayer;
    private final android.graphics.SurfaceTexture.OnFrameAvailableListener mUpdateListener;
    private boolean mUpdateSurface;

    public interface SurfaceTextureListener {
        void onSurfaceTextureAvailable(android.graphics.SurfaceTexture surfaceTexture, int i, int i2);

        boolean onSurfaceTextureDestroyed(android.graphics.SurfaceTexture surfaceTexture);

        void onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture surfaceTexture, int i, int i2);

        void onSurfaceTextureUpdated(android.graphics.SurfaceTexture surfaceTexture);
    }

    private native void nCreateNativeWindow(android.graphics.SurfaceTexture surfaceTexture);

    private native void nDestroyNativeWindow();

    private static native boolean nLockCanvas(long j, android.graphics.Canvas canvas, android.graphics.Rect rect);

    private static native void nUnlockCanvasAndPost(long j, android.graphics.Canvas canvas);

    public TextureView(android.content.Context context) {
        super(context);
        this.mOpaque = true;
        this.mMatrix = new android.graphics.Matrix();
        this.mLock = new java.lang.Object[0];
        this.mNativeWindowLock = new java.lang.Object[0];
        this.mUpdateListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.view.TextureView$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                android.view.TextureView.this.lambda$new$1(surfaceTexture);
            }
        };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOpaque = true;
        this.mMatrix = new android.graphics.Matrix();
        this.mLock = new java.lang.Object[0];
        this.mNativeWindowLock = new java.lang.Object[0];
        this.mUpdateListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.view.TextureView$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                android.view.TextureView.this.lambda$new$1(surfaceTexture);
            }
        };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOpaque = true;
        this.mMatrix = new android.graphics.Matrix();
        this.mLock = new java.lang.Object[0];
        this.mNativeWindowLock = new java.lang.Object[0];
        this.mUpdateListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.view.TextureView$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                android.view.TextureView.this.lambda$new$1(surfaceTexture);
            }
        };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOpaque = true;
        this.mMatrix = new android.graphics.Matrix();
        this.mLock = new java.lang.Object[0];
        this.mNativeWindowLock = new java.lang.Object[0];
        this.mUpdateListener = new android.graphics.SurfaceTexture.OnFrameAvailableListener() { // from class: android.view.TextureView$$ExternalSyntheticLambda0
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture) {
                android.view.TextureView.this.lambda$new$1(surfaceTexture);
            }
        };
        this.mRenderNode.setIsTextureView();
    }

    @Override // android.view.View
    public boolean isOpaque() {
        return this.mOpaque;
    }

    public void setOpaque(boolean z) {
        if (z != this.mOpaque) {
            this.mOpaque = z;
            if (this.mLayer != null) {
                updateLayerAndInvalidate();
            }
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isHardwareAccelerated()) {
            android.util.Log.w(LOG_TAG, "A TextureView or a subclass can only be used with hardware acceleration enabled.");
        }
        if (this.mHadSurface) {
            invalidate(true);
            this.mHadSurface = false;
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        destroyHardwareLayer();
        releaseSurfaceTexture();
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        destroyHardwareLayer();
    }

    private void destroyHardwareLayer() {
        if (this.mLayer != null) {
            this.mLayer.detachSurfaceTexture();
            this.mLayer.close();
            this.mLayer = null;
            this.mMatrixChanged = true;
        }
    }

    private void releaseSurfaceTexture() {
        boolean z;
        if (this.mSurface != null) {
            if (this.mListener == null) {
                z = true;
            } else {
                z = this.mListener.onSurfaceTextureDestroyed(this.mSurface);
            }
            synchronized (this.mNativeWindowLock) {
                nDestroyNativeWindow();
            }
            if (z) {
                this.mSurface.release();
            }
            this.mSurface = null;
            this.mHadSurface = true;
        }
    }

    @Override // android.view.View
    public void setLayerType(int i, android.graphics.Paint paint) {
        setLayerPaint(paint);
    }

    @Override // android.view.View
    public void setLayerPaint(android.graphics.Paint paint) {
        if (paint != this.mLayerPaint) {
            this.mLayerPaint = paint;
            invalidate();
        }
    }

    @Override // android.view.View
    public int getLayerType() {
        return 2;
    }

    @Override // android.view.View
    public void buildLayer() {
    }

    @Override // android.view.View
    public void setForeground(android.graphics.drawable.Drawable drawable) {
        if (drawable != null && !sTextureViewIgnoresDrawableSetters) {
            throw new java.lang.UnsupportedOperationException("TextureView doesn't support displaying a foreground drawable");
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable != null && !sTextureViewIgnoresDrawableSetters) {
            throw new java.lang.UnsupportedOperationException("TextureView doesn't support displaying a background drawable");
        }
    }

    @Override // android.view.View
    public final void draw(android.graphics.Canvas canvas) {
        this.mPrivateFlags = (this.mPrivateFlags & (-2097153)) | 32;
        if (canvas.isHardwareAccelerated()) {
            android.graphics.RecordingCanvas recordingCanvas = (android.graphics.RecordingCanvas) canvas;
            android.graphics.TextureLayer textureLayer = getTextureLayer();
            if (textureLayer != null) {
                android.os.Trace.traceBegin(8L, "TextureView#draw()");
                applyUpdate();
                applyTransformMatrix();
                this.mLayer.setLayerPaint(this.mLayerPaint);
                recordingCanvas.drawTextureLayer(textureLayer);
                android.os.Trace.traceEnd(8L);
            }
        }
    }

    @Override // android.view.View
    protected final void onDraw(android.graphics.Canvas canvas) {
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mSurface != null) {
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
            updateLayer();
            if (this.mListener != null) {
                this.mListener.onSurfaceTextureSizeChanged(this.mSurface, getWidth(), getHeight());
            }
        }
    }

    android.graphics.TextureLayer getTextureLayer() {
        if (this.mLayer == null) {
            if (this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null) {
                return null;
            }
            this.mLayer = this.mAttachInfo.mThreadedRenderer.createTextureLayer();
            boolean z = this.mSurface == null;
            if (z) {
                this.mSurface = new android.graphics.SurfaceTexture(false);
                nCreateNativeWindow(this.mSurface);
            }
            this.mLayer.setSurfaceTexture(this.mSurface);
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
            this.mSurface.setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
            if (android.view.flags.Flags.toolkitSetFrameRateReadOnly()) {
                this.mSurface.setOnSetFrameRateListener(new android.graphics.SurfaceTexture.OnSetFrameRateListener() { // from class: android.view.TextureView$$ExternalSyntheticLambda1
                    @Override // android.graphics.SurfaceTexture.OnSetFrameRateListener
                    public final void onSetFrameRate(android.graphics.SurfaceTexture surfaceTexture, float f, int i, int i2) {
                        android.view.TextureView.this.lambda$getTextureLayer$0(surfaceTexture, f, i, i2);
                    }
                }, this.mAttachInfo.mHandler);
            }
            if (this.mListener != null && z) {
                this.mListener.onSurfaceTextureAvailable(this.mSurface, getWidth(), getHeight());
            }
            this.mLayer.setLayerPaint(this.mLayerPaint);
        }
        if (this.mUpdateSurface) {
            this.mUpdateSurface = false;
            updateLayer();
            this.mMatrixChanged = true;
            this.mLayer.setSurfaceTexture(this.mSurface);
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
        }
        return this.mLayer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTextureLayer$0(android.graphics.SurfaceTexture surfaceTexture, float f, int i, int i2) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instant(8L, "setFrameRate: " + f);
        }
        setRequestedFrameRate(f);
        this.mFrameRateCompatibility = i;
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mSurface != null) {
            if (i == 0) {
                if (this.mLayer != null) {
                    this.mSurface.setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
                }
                updateLayerAndInvalidate();
                return;
            }
            this.mSurface.setOnFrameAvailableListener(null);
        }
    }

    private void updateLayer() {
        synchronized (this.mLock) {
            this.mUpdateLayer = true;
        }
    }

    private void updateLayerAndInvalidate() {
        synchronized (this.mLock) {
            this.mUpdateLayer = true;
        }
        invalidate();
    }

    private void applyUpdate() {
        if (this.mLayer == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mUpdateLayer) {
                this.mUpdateLayer = false;
                this.mLayer.prepare(getWidth(), getHeight(), this.mOpaque);
                this.mLayer.updateSurfaceTexture();
                if (this.mListener != null) {
                    this.mListener.onSurfaceTextureUpdated(this.mSurface);
                }
            }
        }
    }

    public void setTransform(android.graphics.Matrix matrix) {
        this.mMatrix.set(matrix);
        this.mMatrixChanged = true;
        invalidateParentIfNeeded();
    }

    public android.graphics.Matrix getTransform(android.graphics.Matrix matrix) {
        if (matrix == null) {
            matrix = new android.graphics.Matrix();
        }
        matrix.set(this.mMatrix);
        return matrix;
    }

    private void applyTransformMatrix() {
        if (this.mMatrixChanged && this.mLayer != null) {
            this.mLayer.setTransform(this.mMatrix);
            this.mMatrixChanged = false;
        }
    }

    public android.graphics.Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }

    public android.graphics.Bitmap getBitmap(int i, int i2) {
        if (isAvailable() && i > 0 && i2 > 0) {
            return getBitmap(android.graphics.Bitmap.createBitmap(getResources().getDisplayMetrics(), i, i2, android.graphics.Bitmap.Config.ARGB_8888));
        }
        return null;
    }

    public android.graphics.Bitmap getBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap != null && isAvailable()) {
            applyUpdate();
            applyTransformMatrix();
            if (this.mLayer == null && this.mUpdateSurface) {
                getTextureLayer();
            }
            if (this.mLayer != null) {
                this.mLayer.copyInto(bitmap);
            }
        }
        return bitmap;
    }

    public boolean isAvailable() {
        return this.mSurface != null;
    }

    public android.graphics.Canvas lockCanvas() {
        return lockCanvas(null);
    }

    public android.graphics.Canvas lockCanvas(android.graphics.Rect rect) {
        if (!isAvailable()) {
            return null;
        }
        if (this.mCanvas == null) {
            this.mCanvas = new android.graphics.Canvas();
        }
        synchronized (this.mNativeWindowLock) {
            if (!nLockCanvas(this.mNativeWindow, this.mCanvas, rect)) {
                return null;
            }
            this.mSaveCount = this.mCanvas.save();
            return this.mCanvas;
        }
    }

    public void unlockCanvasAndPost(android.graphics.Canvas canvas) {
        if (this.mCanvas != null && canvas == this.mCanvas) {
            canvas.restoreToCount(this.mSaveCount);
            this.mSaveCount = 0;
            synchronized (this.mNativeWindowLock) {
                nUnlockCanvasAndPost(this.mNativeWindow, this.mCanvas);
            }
        }
    }

    public android.graphics.SurfaceTexture getSurfaceTexture() {
        return this.mSurface;
    }

    public void setSurfaceTexture(android.graphics.SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new java.lang.NullPointerException("surfaceTexture must not be null");
        }
        if (surfaceTexture == this.mSurface) {
            throw new java.lang.IllegalArgumentException("Trying to setSurfaceTexture to the same SurfaceTexture that's already set.");
        }
        if (surfaceTexture.isReleased()) {
            throw new java.lang.IllegalArgumentException("Cannot setSurfaceTexture to a released SurfaceTexture");
        }
        if (this.mSurface != null) {
            nDestroyNativeWindow();
            this.mSurface.release();
        }
        this.mSurface = surfaceTexture;
        nCreateNativeWindow(this.mSurface);
        if ((this.mViewFlags & 12) == 0 && this.mLayer != null) {
            this.mSurface.setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
        }
        this.mUpdateSurface = true;
        invalidateParentIfNeeded();
    }

    public android.view.TextureView.SurfaceTextureListener getSurfaceTextureListener() {
        return this.mListener;
    }

    public void setSurfaceTextureListener(android.view.TextureView.SurfaceTextureListener surfaceTextureListener) {
        this.mListener = surfaceTextureListener;
    }

    @Override // android.view.View
    protected int calculateFrameRateCategory(float f) {
        if (this.mMinusTwoFrameIntervalMillis > 15 && this.mMinusOneFrameIntervalMillis > 15) {
            return 3;
        }
        return super.calculateFrameRateCategory(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.graphics.SurfaceTexture surfaceTexture) {
        updateLayer();
        invalidate();
    }
}
