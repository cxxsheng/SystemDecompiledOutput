package android.graphics;

/* loaded from: classes.dex */
public class Picture {
    private static final int WORKING_STREAM_STORAGE = 16384;
    private long mNativePicture;
    private android.graphics.Picture.PictureCanvas mRecordingCanvas;
    private boolean mRequiresHwAcceleration;

    private static native long nativeBeginRecording(long j, int i, int i2);

    private static native long nativeConstructor(long j);

    private static native long nativeCreateFromStream(java.io.InputStream inputStream, byte[] bArr);

    private static native void nativeDestructor(long j);

    private static native void nativeDraw(long j, long j2);

    private static native void nativeEndRecording(long j);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeWriteToStream(long j, java.io.OutputStream outputStream, byte[] bArr);

    public Picture() {
        this(nativeConstructor(0L));
    }

    public Picture(android.graphics.Picture picture) {
        this(nativeConstructor(picture != null ? picture.mNativePicture : 0L));
    }

    public Picture(long j) {
        if (j == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mNativePicture = j;
    }

    public void close() {
        if (this.mNativePicture != 0) {
            nativeDestructor(this.mNativePicture);
            this.mNativePicture = 0L;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void verifyValid() {
        if (this.mNativePicture == 0) {
            throw new java.lang.IllegalStateException("Picture is destroyed");
        }
    }

    public android.graphics.Canvas beginRecording(int i, int i2) {
        verifyValid();
        if (this.mRecordingCanvas != null) {
            throw new java.lang.IllegalStateException("Picture already recording, must call #endRecording()");
        }
        this.mRecordingCanvas = new android.graphics.Picture.PictureCanvas(this, nativeBeginRecording(this.mNativePicture, i, i2));
        this.mRequiresHwAcceleration = false;
        return this.mRecordingCanvas;
    }

    public void endRecording() {
        verifyValid();
        if (this.mRecordingCanvas != null) {
            this.mRequiresHwAcceleration = this.mRecordingCanvas.mUsesHwFeature;
            this.mRecordingCanvas = null;
            nativeEndRecording(this.mNativePicture);
        }
    }

    public int getWidth() {
        verifyValid();
        return nativeGetWidth(this.mNativePicture);
    }

    public int getHeight() {
        verifyValid();
        return nativeGetHeight(this.mNativePicture);
    }

    public boolean requiresHardwareAcceleration() {
        verifyValid();
        return this.mRequiresHwAcceleration;
    }

    public void draw(android.graphics.Canvas canvas) {
        verifyValid();
        if (this.mRecordingCanvas != null) {
            endRecording();
        }
        if (this.mRequiresHwAcceleration && !canvas.isHardwareAccelerated() && canvas.onHwFeatureInSwMode()) {
            throw new java.lang.IllegalArgumentException("Software rendering not supported for Pictures that require hardware acceleration");
        }
        nativeDraw(canvas.getNativeCanvasWrapper(), this.mNativePicture);
    }

    @java.lang.Deprecated
    public static android.graphics.Picture createFromStream(java.io.InputStream inputStream) {
        return new android.graphics.Picture(nativeCreateFromStream(inputStream, new byte[16384]));
    }

    @java.lang.Deprecated
    public void writeToStream(java.io.OutputStream outputStream) {
        verifyValid();
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException("stream cannot be null");
        }
        if (!nativeWriteToStream(this.mNativePicture, outputStream, new byte[16384])) {
            throw new java.lang.RuntimeException();
        }
    }

    private static class PictureCanvas extends android.graphics.Canvas {
        private final android.graphics.Picture mPicture;
        boolean mUsesHwFeature;

        public PictureCanvas(android.graphics.Picture picture, long j) {
            super(j);
            this.mPicture = picture;
            this.mDensity = 0;
        }

        @Override // android.graphics.Canvas
        public void setBitmap(android.graphics.Bitmap bitmap) {
            throw new java.lang.RuntimeException("Cannot call setBitmap on a picture canvas");
        }

        @Override // android.graphics.Canvas
        public void drawPicture(android.graphics.Picture picture) {
            if (this.mPicture == picture) {
                throw new java.lang.RuntimeException("Cannot draw a picture into its recording canvas");
            }
            super.drawPicture(picture);
        }

        @Override // android.graphics.BaseCanvas
        protected boolean onHwFeatureInSwMode() {
            this.mUsesHwFeature = true;
            return false;
        }
    }
}
