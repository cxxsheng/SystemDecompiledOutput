package android.app;

/* loaded from: classes.dex */
public abstract class SharedElementCallback {
    private static final java.lang.String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final java.lang.String BUNDLE_SNAPSHOT_COLOR_SPACE = "sharedElement:snapshot:colorSpace";
    private static final java.lang.String BUNDLE_SNAPSHOT_HARDWARE_BUFFER = "sharedElement:snapshot:hardwareBuffer";
    private static final java.lang.String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final java.lang.String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    static final android.app.SharedElementCallback NULL_CALLBACK = new android.app.SharedElementCallback() { // from class: android.app.SharedElementCallback.1
    };
    private android.graphics.Matrix mTempMatrix;

    public interface OnSharedElementsReadyListener {
        void onSharedElementsReady();
    }

    public void onSharedElementStart(java.util.List<java.lang.String> list, java.util.List<android.view.View> list2, java.util.List<android.view.View> list3) {
    }

    public void onSharedElementEnd(java.util.List<java.lang.String> list, java.util.List<android.view.View> list2, java.util.List<android.view.View> list3) {
    }

    public void onRejectSharedElements(java.util.List<android.view.View> list) {
    }

    public void onMapSharedElements(java.util.List<java.lang.String> list, java.util.Map<java.lang.String, android.view.View> map) {
    }

    public android.os.Parcelable onCaptureSharedElementSnapshot(android.view.View view, android.graphics.Matrix matrix, android.graphics.RectF rectF) {
        android.graphics.Bitmap createDrawableBitmap;
        if (view instanceof android.widget.ImageView) {
            android.widget.ImageView imageView = (android.widget.ImageView) view;
            android.graphics.drawable.Drawable drawable = imageView.getDrawable();
            android.graphics.drawable.Drawable background = imageView.getBackground();
            if (drawable != null && ((background == null || background.getAlpha() == 0) && (createDrawableBitmap = android.transition.TransitionUtils.createDrawableBitmap(drawable, imageView)) != null)) {
                android.os.Bundle bundle = new android.os.Bundle();
                if (createDrawableBitmap.getConfig() != android.graphics.Bitmap.Config.HARDWARE) {
                    bundle.putParcelable(BUNDLE_SNAPSHOT_BITMAP, createDrawableBitmap);
                } else {
                    bundle.putParcelable(BUNDLE_SNAPSHOT_HARDWARE_BUFFER, createDrawableBitmap.getHardwareBuffer());
                    android.graphics.ColorSpace colorSpace = createDrawableBitmap.getColorSpace();
                    if (colorSpace != null) {
                        bundle.putInt(BUNDLE_SNAPSHOT_COLOR_SPACE, colorSpace.getId());
                    }
                }
                bundle.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView.getScaleType().toString());
                if (imageView.getScaleType() == android.widget.ImageView.ScaleType.MATRIX) {
                    float[] fArr = new float[9];
                    imageView.getImageMatrix().getValues(fArr);
                    bundle.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, fArr);
                }
                return bundle;
            }
        }
        if (this.mTempMatrix == null) {
            this.mTempMatrix = new android.graphics.Matrix(matrix);
        } else {
            this.mTempMatrix.set(matrix);
        }
        return android.transition.TransitionUtils.createViewBitmap(view, this.mTempMatrix, rectF, (android.view.ViewGroup) view.getParent());
    }

    public android.view.View onCreateSnapshotView(android.content.Context context, android.os.Parcelable parcelable) {
        android.graphics.ColorSpace colorSpace = null;
        if (parcelable instanceof android.os.Bundle) {
            android.os.Bundle bundle = (android.os.Bundle) parcelable;
            android.hardware.HardwareBuffer hardwareBuffer = (android.hardware.HardwareBuffer) bundle.getParcelable(BUNDLE_SNAPSHOT_HARDWARE_BUFFER, android.hardware.HardwareBuffer.class);
            android.graphics.Bitmap bitmap = (android.graphics.Bitmap) bundle.getParcelable(BUNDLE_SNAPSHOT_BITMAP, android.graphics.Bitmap.class);
            if (hardwareBuffer == null && bitmap == null) {
                return null;
            }
            if (bitmap == null) {
                int i = bundle.getInt(BUNDLE_SNAPSHOT_COLOR_SPACE, 0);
                if (i >= 0 && i < android.graphics.ColorSpace.Named.values().length) {
                    colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[i]);
                }
                bitmap = android.graphics.Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace);
            }
            android.widget.ImageView imageView = new android.widget.ImageView(context);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(android.widget.ImageView.ScaleType.valueOf(bundle.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
            if (imageView.getScaleType() == android.widget.ImageView.ScaleType.MATRIX) {
                float[] floatArray = bundle.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                matrix.setValues(floatArray);
                imageView.setImageMatrix(matrix);
                return imageView;
            }
            return imageView;
        }
        if (!(parcelable instanceof android.graphics.Bitmap)) {
            return null;
        }
        android.view.View view = new android.view.View(context);
        view.setBackground(new android.graphics.drawable.BitmapDrawable(context.getResources(), (android.graphics.Bitmap) parcelable));
        return view;
    }

    public void onSharedElementsArrived(java.util.List<java.lang.String> list, java.util.List<android.view.View> list2, android.app.SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady();
    }
}
