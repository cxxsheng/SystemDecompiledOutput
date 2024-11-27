package com.android.internal.util;

/* loaded from: classes5.dex */
public class ImageUtils {
    private static final int ALPHA_TOLERANCE = 50;
    private static final int COMPACT_BITMAP_SIZE = 64;
    private static final int TOLERANCE = 20;
    private int[] mTempBuffer;
    private android.graphics.Bitmap mTempCompactBitmap;
    private android.graphics.Canvas mTempCompactBitmapCanvas;
    private android.graphics.Paint mTempCompactBitmapPaint;
    private final android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();

    public boolean isGrayscale(android.graphics.Bitmap bitmap) {
        android.graphics.Bitmap bitmap2;
        int i;
        int i2;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (height > 64 || width > 64) {
            if (this.mTempCompactBitmap == null) {
                this.mTempCompactBitmap = android.graphics.Bitmap.createBitmap(64, 64, android.graphics.Bitmap.Config.ARGB_8888);
                this.mTempCompactBitmapCanvas = new android.graphics.Canvas(this.mTempCompactBitmap);
                this.mTempCompactBitmapPaint = new android.graphics.Paint(1);
                this.mTempCompactBitmapPaint.setFilterBitmap(true);
            }
            this.mTempMatrix.reset();
            this.mTempMatrix.setScale(64.0f / width, 64.0f / height, 0.0f, 0.0f);
            this.mTempCompactBitmapCanvas.drawColor(0, android.graphics.PorterDuff.Mode.SRC);
            this.mTempCompactBitmapCanvas.drawBitmap(bitmap, this.mTempMatrix, this.mTempCompactBitmapPaint);
            bitmap2 = this.mTempCompactBitmap;
            i = 64;
            i2 = 64;
        } else {
            bitmap2 = bitmap;
            i2 = height;
            i = width;
        }
        int i3 = i2 * i;
        ensureBufferSize(i3);
        bitmap2.getPixels(this.mTempBuffer, 0, i, 0, 0, i, i2);
        for (int i4 = 0; i4 < i3; i4++) {
            if (!isGrayscale(this.mTempBuffer[i4])) {
                return false;
            }
        }
        return true;
    }

    private void ensureBufferSize(int i) {
        if (this.mTempBuffer == null || this.mTempBuffer.length < i) {
            this.mTempBuffer = new int[i];
        }
    }

    public static boolean isGrayscale(int i) {
        if (((i >> 24) & 255) < 50) {
            return true;
        }
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        return java.lang.Math.abs(i2 - i3) < 20 && java.lang.Math.abs(i2 - i4) < 20 && java.lang.Math.abs(i3 - i4) < 20;
    }

    public static android.graphics.Bitmap buildScaledBitmap(android.graphics.drawable.Drawable drawable, int i, int i2) {
        return buildScaledBitmap(drawable, i, i2, false);
    }

    public static android.graphics.Bitmap buildScaledBitmap(android.graphics.drawable.Drawable drawable, int i, int i2, boolean z) {
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= i && intrinsicHeight <= i2 && (drawable instanceof android.graphics.drawable.BitmapDrawable)) {
            return ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        }
        if (intrinsicHeight <= 0 || intrinsicWidth <= 0) {
            return null;
        }
        float f = intrinsicWidth;
        float f2 = intrinsicHeight;
        float min = java.lang.Math.min(i / f, i2 / f2);
        if (!z) {
            min = java.lang.Math.min(1.0f, min);
        }
        int i3 = (int) (f * min);
        int i4 = (int) (min * f2);
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i3, i4, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        drawable.setBounds(0, 0, i3, i4);
        drawable.draw(canvas);
        return createBitmap;
    }

    public static int calculateSampleSize(android.util.Size size, android.util.Size size2) {
        int i = 1;
        if (size.getHeight() > size2.getHeight() || size.getWidth() > size2.getWidth()) {
            int height = size.getHeight() / 2;
            int width = size.getWidth() / 2;
            while (height / i >= size2.getHeight() && width / i >= size2.getWidth()) {
                i *= 2;
            }
        }
        return i;
    }

    public static android.graphics.Bitmap loadThumbnail(android.content.ContentResolver contentResolver, final android.net.Uri uri, final android.util.Size size) throws java.io.IOException {
        final android.content.ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(uri);
        try {
            final android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.content.ContentResolver.EXTRA_SIZE, new android.graphics.Point(size.getWidth(), size.getHeight()));
            android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource((java.util.concurrent.Callable<android.content.res.AssetFileDescriptor>) new java.util.concurrent.Callable() { // from class: com.android.internal.util.ImageUtils$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    android.content.res.AssetFileDescriptor openTypedAssetFile;
                    openTypedAssetFile = android.content.ContentProviderClient.this.openTypedAssetFile(uri, com.google.android.mms.ContentType.IMAGE_UNSPECIFIED, bundle, null);
                    return openTypedAssetFile;
                }
            }), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.util.ImageUtils$$ExternalSyntheticLambda1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    com.android.internal.util.ImageUtils.lambda$loadThumbnail$1(android.util.Size.this, imageDecoder, imageInfo, source);
                }
            });
            if (acquireContentProviderClient != null) {
                acquireContentProviderClient.close();
            }
            return decodeBitmap;
        } catch (java.lang.Throwable th) {
            if (acquireContentProviderClient != null) {
                try {
                    acquireContentProviderClient.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static /* synthetic */ void lambda$loadThumbnail$1(android.util.Size size, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        int calculateSampleSize = calculateSampleSize(imageInfo.getSize(), size);
        if (calculateSampleSize > 1) {
            imageDecoder.setTargetSampleSize(calculateSampleSize);
        }
    }
}
