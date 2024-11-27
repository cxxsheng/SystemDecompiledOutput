package com.android.server.companion.datatransfer.contextsync;

/* loaded from: classes.dex */
public class BitmapUtils {
    private static final int APP_ICON_BITMAP_DIMENSION = 256;

    public static byte[] renderDrawableToByteArray(android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            android.graphics.Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
            if (bitmap.getWidth() > 256 || bitmap.getHeight() > 256) {
                android.graphics.Bitmap createScaledBitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, 256, 256, true);
                byte[] renderBitmapToByteArray = renderBitmapToByteArray(createScaledBitmap);
                createScaledBitmap.recycle();
                return renderBitmapToByteArray;
            }
            return renderBitmapToByteArray(bitmap);
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(256, 256, android.graphics.Bitmap.Config.ARGB_8888);
        try {
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            drawable.setBounds(0, 0, createBitmap.getWidth(), createBitmap.getHeight());
            drawable.draw(canvas);
            return renderBitmapToByteArray(createBitmap);
        } finally {
            createBitmap.recycle();
        }
    }

    private static byte[] renderBitmapToByteArray(android.graphics.Bitmap bitmap) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
