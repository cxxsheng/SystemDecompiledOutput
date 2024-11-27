package android.graphics;

/* loaded from: classes.dex */
public class NinePatch {
    private final android.graphics.Bitmap mBitmap;
    public long mNativeChunk;
    private android.graphics.Paint mPaint;
    private java.lang.String mSrcName;

    public static native boolean isNinePatchChunk(byte[] bArr);

    private static native void nativeFinalize(long j);

    private static native long nativeGetTransparentRegion(long j, long j2, android.graphics.Rect rect);

    private static native long validateNinePatchChunk(byte[] bArr);

    public static class InsetStruct {
        public final android.graphics.Rect opticalRect;
        public final float outlineAlpha;
        public final float outlineRadius;
        public final android.graphics.Rect outlineRect;

        InsetStruct(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f, int i9, float f2) {
            this.opticalRect = new android.graphics.Rect(i, i2, i3, i4);
            this.opticalRect.scale(f2);
            this.outlineRect = scaleInsets(i5, i6, i7, i8, f2);
            this.outlineRadius = f * f2;
            this.outlineAlpha = i9 / 255.0f;
        }

        public static android.graphics.Rect scaleInsets(int i, int i2, int i3, int i4, float f) {
            if (f == 1.0f) {
                return new android.graphics.Rect(i, i2, i3, i4);
            }
            android.graphics.Rect rect = new android.graphics.Rect();
            rect.left = (int) java.lang.Math.ceil(i * f);
            rect.top = (int) java.lang.Math.ceil(i2 * f);
            rect.right = (int) java.lang.Math.ceil(i3 * f);
            rect.bottom = (int) java.lang.Math.ceil(i4 * f);
            return rect;
        }
    }

    public NinePatch(android.graphics.Bitmap bitmap, byte[] bArr) {
        this(bitmap, bArr, null);
    }

    public NinePatch(android.graphics.Bitmap bitmap, byte[] bArr, java.lang.String str) {
        this.mBitmap = bitmap;
        this.mSrcName = str;
        this.mNativeChunk = validateNinePatchChunk(bArr);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mNativeChunk != 0) {
                nativeFinalize(this.mNativeChunk);
                this.mNativeChunk = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public java.lang.String getName() {
        return this.mSrcName;
    }

    public android.graphics.Paint getPaint() {
        return this.mPaint;
    }

    public void setPaint(android.graphics.Paint paint) {
        this.mPaint = paint;
    }

    public android.graphics.Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.RectF rectF) {
        canvas.drawPatch(this, rectF, this.mPaint);
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.Rect rect) {
        canvas.drawPatch(this, rect, this.mPaint);
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.Rect rect, android.graphics.Paint paint) {
        canvas.drawPatch(this, rect, paint);
    }

    public int getDensity() {
        return this.mBitmap.mDensity;
    }

    public int getWidth() {
        return this.mBitmap.getWidth();
    }

    public int getHeight() {
        return this.mBitmap.getHeight();
    }

    public final boolean hasAlpha() {
        return this.mBitmap.hasAlpha();
    }

    public final android.graphics.Region getTransparentRegion(android.graphics.Rect rect) {
        long nativeGetTransparentRegion = nativeGetTransparentRegion(this.mBitmap.getNativeInstance(), this.mNativeChunk, rect);
        if (nativeGetTransparentRegion != 0) {
            return new android.graphics.Region(nativeGetTransparentRegion);
        }
        return null;
    }
}
