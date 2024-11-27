package android.content.pm;

/* loaded from: classes.dex */
public final class ArchivedActivityInfo {
    private static final java.lang.String TAG = "ArchivedActivityInfo";
    private android.content.ComponentName mComponentName;
    private android.graphics.drawable.Drawable mIcon;
    private java.lang.CharSequence mLabel;
    private android.graphics.drawable.Drawable mMonochromeIcon;

    public ArchivedActivityInfo(java.lang.CharSequence charSequence, android.content.ComponentName componentName) {
        java.util.Objects.requireNonNull(charSequence);
        java.util.Objects.requireNonNull(componentName);
        this.mLabel = charSequence;
        this.mComponentName = componentName;
    }

    ArchivedActivityInfo(android.content.pm.ArchivedActivityParcel archivedActivityParcel) {
        this.mLabel = archivedActivityParcel.title;
        this.mComponentName = archivedActivityParcel.originalComponentName;
        this.mIcon = drawableFromCompressedBitmap(archivedActivityParcel.iconBitmap);
        this.mMonochromeIcon = drawableFromCompressedBitmap(archivedActivityParcel.monochromeIconBitmap);
    }

    android.content.pm.ArchivedActivityParcel getParcel() {
        android.content.pm.ArchivedActivityParcel archivedActivityParcel = new android.content.pm.ArchivedActivityParcel();
        archivedActivityParcel.title = this.mLabel.toString();
        archivedActivityParcel.originalComponentName = this.mComponentName;
        archivedActivityParcel.iconBitmap = this.mIcon == null ? null : bytesFromBitmap(drawableToBitmap(this.mIcon));
        archivedActivityParcel.monochromeIconBitmap = this.mMonochromeIcon != null ? bytesFromBitmap(drawableToBitmap(this.mMonochromeIcon)) : null;
        return archivedActivityParcel;
    }

    public static android.graphics.Bitmap drawableToBitmap(android.graphics.drawable.Drawable drawable) {
        return drawableToBitmap(drawable, 0);
    }

    public static android.graphics.Bitmap drawableToBitmap(android.graphics.drawable.Drawable drawable, int i) {
        android.graphics.Bitmap createBitmap;
        android.graphics.Bitmap bitmap;
        int i2;
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                createBitmap = android.graphics.Bitmap.createBitmap(1, 1, android.graphics.Bitmap.Config.ARGB_8888);
            } else {
                createBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
            }
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            bitmap = createBitmap;
        }
        if (i <= 0) {
            return bitmap;
        }
        if (bitmap.getWidth() < i || bitmap.getHeight() < i || bitmap.getWidth() > (i2 = i * 2) || bitmap.getHeight() > i2) {
            android.graphics.Bitmap createScaledBitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, i, i, true);
            if (createScaledBitmap != bitmap) {
                bitmap.recycle();
            }
            return createScaledBitmap;
        }
        return bitmap;
    }

    public static byte[] bytesFromBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(bitmap.getByteCount());
            try {
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to compress bitmap", e);
            return null;
        }
    }

    private static android.graphics.drawable.Drawable drawableFromCompressedBitmap(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new android.graphics.drawable.BitmapDrawable((android.content.res.Resources) null, new java.io.ByteArrayInputStream(bArr));
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.graphics.drawable.Drawable getIcon() {
        return this.mIcon;
    }

    public android.graphics.drawable.Drawable getMonochromeIcon() {
        return this.mMonochromeIcon;
    }

    public android.content.pm.ArchivedActivityInfo setLabel(java.lang.CharSequence charSequence) {
        this.mLabel = charSequence;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLabel);
        return this;
    }

    public android.content.pm.ArchivedActivityInfo setComponentName(android.content.ComponentName componentName) {
        this.mComponentName = componentName;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mComponentName);
        return this;
    }

    public android.content.pm.ArchivedActivityInfo setIcon(android.graphics.drawable.Drawable drawable) {
        this.mIcon = drawable;
        return this;
    }

    public android.content.pm.ArchivedActivityInfo setMonochromeIcon(android.graphics.drawable.Drawable drawable) {
        this.mMonochromeIcon = drawable;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
