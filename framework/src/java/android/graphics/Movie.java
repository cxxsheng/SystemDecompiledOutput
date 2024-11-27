package android.graphics;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class Movie {
    private long mNativeMovie;

    public static native android.graphics.Movie decodeByteArray(byte[] bArr, int i, int i2);

    private native void nDraw(long j, float f, float f2, long j2);

    private static native android.graphics.Movie nativeDecodeAsset(long j);

    private static native android.graphics.Movie nativeDecodeStream(java.io.InputStream inputStream);

    private static native void nativeDestructor(long j);

    public native int duration();

    public native int height();

    public native boolean isOpaque();

    public native boolean setTime(int i);

    public native int width();

    private Movie(long j) {
        if (j == 0) {
            throw new java.lang.RuntimeException("native movie creation failed");
        }
        this.mNativeMovie = j;
    }

    public void draw(android.graphics.Canvas canvas, float f, float f2, android.graphics.Paint paint) {
        nDraw(canvas.getNativeCanvasWrapper(), f, f2, paint != null ? paint.getNativeInstance() : 0L);
    }

    public void draw(android.graphics.Canvas canvas, float f, float f2) {
        nDraw(canvas.getNativeCanvasWrapper(), f, f2, 0L);
    }

    public static android.graphics.Movie decodeStream(java.io.InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        if (inputStream instanceof android.content.res.AssetManager.AssetInputStream) {
            return nativeDecodeAsset(((android.content.res.AssetManager.AssetInputStream) inputStream).getNativeAsset());
        }
        return nativeDecodeStream(inputStream);
    }

    public static android.graphics.Movie decodeFile(java.lang.String str) {
        try {
            return decodeTempStream(new java.io.FileInputStream(str));
        } catch (java.io.FileNotFoundException e) {
            return null;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            nativeDestructor(this.mNativeMovie);
            this.mNativeMovie = 0L;
        } finally {
            super.finalize();
        }
    }

    private static android.graphics.Movie decodeTempStream(java.io.InputStream inputStream) {
        try {
            android.graphics.Movie decodeStream = decodeStream(inputStream);
            try {
                inputStream.close();
                return decodeStream;
            } catch (java.io.IOException e) {
                return decodeStream;
            }
        } catch (java.io.IOException e2) {
            return null;
        }
    }
}
