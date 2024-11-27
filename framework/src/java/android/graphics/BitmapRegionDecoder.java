package android.graphics;

/* loaded from: classes.dex */
public final class BitmapRegionDecoder {
    private long mNativeBitmapRegionDecoder;
    private final java.lang.Object mNativeLock = new java.lang.Object();
    private boolean mRecycled = false;

    private static native void nativeClean(long j);

    private static native android.graphics.Bitmap nativeDecodeRegion(long j, int i, int i2, int i3, int i4, android.graphics.BitmapFactory.Options options, long j2, long j3);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native android.graphics.BitmapRegionDecoder nativeNewInstance(long j);

    private static native android.graphics.BitmapRegionDecoder nativeNewInstance(java.io.FileDescriptor fileDescriptor);

    private static native android.graphics.BitmapRegionDecoder nativeNewInstance(java.io.InputStream inputStream, byte[] bArr);

    private static native android.graphics.BitmapRegionDecoder nativeNewInstance(byte[] bArr, int i, int i2);

    @java.lang.Deprecated
    public static android.graphics.BitmapRegionDecoder newInstance(byte[] bArr, int i, int i2, boolean z) throws java.io.IOException {
        return newInstance(bArr, i, i2);
    }

    public static android.graphics.BitmapRegionDecoder newInstance(byte[] bArr, int i, int i2) throws java.io.IOException {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        return nativeNewInstance(bArr, i, i2);
    }

    @java.lang.Deprecated
    public static android.graphics.BitmapRegionDecoder newInstance(java.io.FileDescriptor fileDescriptor, boolean z) throws java.io.IOException {
        return nativeNewInstance(fileDescriptor);
    }

    public static android.graphics.BitmapRegionDecoder newInstance(android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        return nativeNewInstance(parcelFileDescriptor.getFileDescriptor());
    }

    @java.lang.Deprecated
    public static android.graphics.BitmapRegionDecoder newInstance(java.io.InputStream inputStream, boolean z) throws java.io.IOException {
        return newInstance(inputStream);
    }

    public static android.graphics.BitmapRegionDecoder newInstance(java.io.InputStream inputStream) throws java.io.IOException {
        if (inputStream instanceof android.content.res.AssetManager.AssetInputStream) {
            return nativeNewInstance(((android.content.res.AssetManager.AssetInputStream) inputStream).getNativeAsset());
        }
        return nativeNewInstance(inputStream, new byte[16384]);
    }

    @java.lang.Deprecated
    public static android.graphics.BitmapRegionDecoder newInstance(java.lang.String str, boolean z) throws java.io.IOException {
        return newInstance(str);
    }

    public static android.graphics.BitmapRegionDecoder newInstance(java.lang.String str) throws java.io.IOException {
        java.io.FileInputStream fileInputStream;
        java.io.FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new java.io.FileInputStream(str);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            android.graphics.BitmapRegionDecoder newInstance = newInstance(fileInputStream);
            try {
                fileInputStream.close();
            } catch (java.io.IOException e) {
            }
            return newInstance;
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (java.io.IOException e2) {
                }
            }
            throw th;
        }
    }

    private BitmapRegionDecoder(long j) {
        this.mNativeBitmapRegionDecoder = j;
    }

    public android.graphics.Bitmap decodeRegion(android.graphics.Rect rect, android.graphics.BitmapFactory.Options options) {
        android.graphics.Bitmap nativeDecodeRegion;
        android.graphics.BitmapFactory.Options.validate(options);
        synchronized (this.mNativeLock) {
            checkRecycled("decodeRegion called on recycled region decoder");
            if (rect.right <= 0 || rect.bottom <= 0 || rect.left >= getWidth() || rect.top >= getHeight()) {
                throw new java.lang.IllegalArgumentException("rectangle is outside the image");
            }
            nativeDecodeRegion = nativeDecodeRegion(this.mNativeBitmapRegionDecoder, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options, android.graphics.BitmapFactory.Options.nativeInBitmap(options), android.graphics.BitmapFactory.Options.nativeColorSpace(options));
        }
        return nativeDecodeRegion;
    }

    public int getWidth() {
        int nativeGetWidth;
        synchronized (this.mNativeLock) {
            checkRecycled("getWidth called on recycled region decoder");
            nativeGetWidth = nativeGetWidth(this.mNativeBitmapRegionDecoder);
        }
        return nativeGetWidth;
    }

    public int getHeight() {
        int nativeGetHeight;
        synchronized (this.mNativeLock) {
            checkRecycled("getHeight called on recycled region decoder");
            nativeGetHeight = nativeGetHeight(this.mNativeBitmapRegionDecoder);
        }
        return nativeGetHeight;
    }

    public void recycle() {
        synchronized (this.mNativeLock) {
            if (!this.mRecycled) {
                nativeClean(this.mNativeBitmapRegionDecoder);
                this.mRecycled = true;
            }
        }
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    private void checkRecycled(java.lang.String str) {
        if (this.mRecycled) {
            throw new java.lang.IllegalStateException(str);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }
}
