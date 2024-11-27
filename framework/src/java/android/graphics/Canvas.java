package android.graphics;

/* loaded from: classes.dex */
public class Canvas extends android.graphics.BaseCanvas {
    public static final int ALL_SAVE_FLAG = 31;
    public static final int CLIP_SAVE_FLAG = 2;
    public static final int CLIP_TO_LAYER_SAVE_FLAG = 16;
    public static final int FULL_COLOR_LAYER_SAVE_FLAG = 8;
    public static final int HAS_ALPHA_LAYER_SAVE_FLAG = 4;
    public static final int MATRIX_SAVE_FLAG = 1;
    private static final int MAXMIMUM_BITMAP_SIZE = 32766;
    private android.graphics.Bitmap mBitmap;
    private android.graphics.DrawFilter mDrawFilter;
    private java.lang.Runnable mFinalizer;
    private static int sCompatiblityVersion = 0;
    private static boolean sCompatibilityRestore = false;
    private static boolean sCompatibilitySetBitmap = false;

    public enum EdgeType {
        BW,
        AA
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Saveflags {
    }

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nClipPath(long j, long j2, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nClipRect(long j, float f, float f2, float f3, float f4, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nClipShader(long j, long j2, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nConcat(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native void nConcat(long j, float[] fArr);

    private static native void nFreeCaches();

    private static native void nFreeTextLayoutCaches();

    @dalvik.annotation.optimization.FastNative
    private static native boolean nGetClipBounds(long j, android.graphics.Rect rect);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetHeight(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nGetMatrix(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetSaveCount(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetWidth(long j);

    @dalvik.annotation.optimization.FastNative
    private static native long nInitRaster(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsHighContrastText(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsOpaque(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nQuickReject(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nQuickReject(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nRestore(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nRestoreToCount(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nRestoreUnclippedLayer(long j, int i, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nRotate(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nSave(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nSaveLayer(long j, float f, float f2, float f3, float f4, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nSaveLayerAlpha(long j, float f, float f2, float f3, float f4, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nSaveUnclippedLayer(long j, int i, int i2, int i3, int i4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nScale(long j, float f, float f2);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetBitmap(long j, long j2);

    private static native void nSetCompatibilityVersion(int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetDrawFilter(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSkew(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nTranslate(long j, float f, float f2);

    public long getNativeCanvasWrapper() {
        return this.mNativeCanvasWrapper;
    }

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Canvas.class.getClassLoader(), android.graphics.Canvas.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public Canvas() {
        if (!isHardwareAccelerated()) {
            this.mNativeCanvasWrapper = nInitRaster(0L);
            this.mFinalizer = android.graphics.Canvas.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        } else {
            this.mFinalizer = null;
        }
    }

    public Canvas(android.graphics.Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            throw new java.lang.IllegalStateException("Immutable bitmap passed to Canvas constructor");
        }
        throwIfCannotDraw(bitmap);
        bitmap.setGainmap(null);
        this.mNativeCanvasWrapper = nInitRaster(bitmap.getNativeInstance());
        this.mFinalizer = android.graphics.Canvas.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        this.mBitmap = bitmap;
        this.mDensity = bitmap.mDensity;
    }

    public Canvas(long j) {
        if (j == 0) {
            throw new java.lang.IllegalStateException();
        }
        this.mNativeCanvasWrapper = j;
        this.mFinalizer = android.graphics.Canvas.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeCanvasWrapper);
        this.mDensity = android.graphics.Bitmap.getDefaultDensity();
    }

    @Override // android.graphics.BaseCanvas
    public boolean isHardwareAccelerated() {
        return false;
    }

    public boolean isHighContrastTextEnabled() {
        return nIsHighContrastText(this.mNativeCanvasWrapper);
    }

    public void setBitmap(android.graphics.Bitmap bitmap) {
        android.graphics.Matrix matrix;
        if (isHardwareAccelerated()) {
            throw new java.lang.RuntimeException("Can't set a bitmap device on a HW accelerated canvas");
        }
        if (bitmap != null && sCompatibilitySetBitmap) {
            matrix = getMatrix();
        } else {
            matrix = null;
        }
        if (bitmap == null) {
            nSetBitmap(this.mNativeCanvasWrapper, 0L);
            this.mDensity = 0;
        } else {
            if (!bitmap.isMutable()) {
                throw new java.lang.IllegalStateException();
            }
            throwIfCannotDraw(bitmap);
            bitmap.setGainmap(null);
            nSetBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance());
            this.mDensity = bitmap.mDensity;
        }
        if (matrix != null) {
            setMatrix(matrix);
        }
        this.mBitmap = bitmap;
    }

    public void enableZ() {
    }

    public void disableZ() {
    }

    public boolean isOpaque() {
        return nIsOpaque(this.mNativeCanvasWrapper);
    }

    public int getWidth() {
        return nGetWidth(this.mNativeCanvasWrapper);
    }

    public int getHeight() {
        return nGetHeight(this.mNativeCanvasWrapper);
    }

    public int getDensity() {
        return this.mDensity;
    }

    public void setDensity(int i) {
        if (this.mBitmap != null) {
            this.mBitmap.setDensity(i);
        }
        this.mDensity = i;
    }

    public void setScreenDensity(int i) {
        this.mScreenDensity = i;
    }

    public int getMaximumBitmapWidth() {
        return MAXMIMUM_BITMAP_SIZE;
    }

    public int getMaximumBitmapHeight() {
        return MAXMIMUM_BITMAP_SIZE;
    }

    private static void checkValidSaveFlags(int i) {
        if (sCompatiblityVersion >= 28 && i != 31) {
            throw new java.lang.IllegalArgumentException("Invalid Layer Save Flag - only ALL_SAVE_FLAGS is allowed");
        }
    }

    public int save() {
        return nSave(this.mNativeCanvasWrapper, 3);
    }

    public int save(int i) {
        return nSave(this.mNativeCanvasWrapper, i);
    }

    public int saveLayer(android.graphics.RectF rectF, android.graphics.Paint paint, int i) {
        if (rectF == null) {
            rectF = new android.graphics.RectF(getClipBounds());
        }
        checkValidSaveFlags(i);
        return saveLayer(rectF.left, rectF.top, rectF.right, rectF.bottom, paint, 31);
    }

    public int saveLayer(android.graphics.RectF rectF, android.graphics.Paint paint) {
        return saveLayer(rectF, paint, 31);
    }

    public int saveUnclippedLayer(int i, int i2, int i3, int i4) {
        return nSaveUnclippedLayer(this.mNativeCanvasWrapper, i, i2, i3, i4);
    }

    public void restoreUnclippedLayer(int i, android.graphics.Paint paint) {
        nRestoreUnclippedLayer(this.mNativeCanvasWrapper, i, paint.getNativeInstance());
    }

    public int saveLayer(float f, float f2, float f3, float f4, android.graphics.Paint paint, int i) {
        checkValidSaveFlags(i);
        return nSaveLayer(this.mNativeCanvasWrapper, f, f2, f3, f4, paint != null ? paint.getNativeInstance() : 0L);
    }

    public int saveLayer(float f, float f2, float f3, float f4, android.graphics.Paint paint) {
        return saveLayer(f, f2, f3, f4, paint, 31);
    }

    public int saveLayerAlpha(android.graphics.RectF rectF, int i, int i2) {
        if (rectF == null) {
            rectF = new android.graphics.RectF(getClipBounds());
        }
        checkValidSaveFlags(i2);
        return saveLayerAlpha(rectF.left, rectF.top, rectF.right, rectF.bottom, i, 31);
    }

    public int saveLayerAlpha(android.graphics.RectF rectF, int i) {
        return saveLayerAlpha(rectF, i, 31);
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int i, int i2) {
        checkValidSaveFlags(i2);
        return nSaveLayerAlpha(this.mNativeCanvasWrapper, f, f2, f3, f4, java.lang.Math.min(255, java.lang.Math.max(0, i)));
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int i) {
        return saveLayerAlpha(f, f2, f3, f4, i, 31);
    }

    public void restore() {
        if (!nRestore(this.mNativeCanvasWrapper)) {
            if (!sCompatibilityRestore || !isHardwareAccelerated()) {
                throw new java.lang.IllegalStateException("Underflow in restore - more restores than saves");
            }
        }
    }

    public int getSaveCount() {
        return nGetSaveCount(this.mNativeCanvasWrapper);
    }

    public void restoreToCount(int i) {
        if (i < 1) {
            if (!sCompatibilityRestore || !isHardwareAccelerated()) {
                throw new java.lang.IllegalArgumentException("Underflow in restoreToCount - more restores than saves");
            }
            i = 1;
        }
        nRestoreToCount(this.mNativeCanvasWrapper, i);
    }

    public void translate(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        nTranslate(this.mNativeCanvasWrapper, f, f2);
    }

    public void scale(float f, float f2) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        nScale(this.mNativeCanvasWrapper, f, f2);
    }

    public final void scale(float f, float f2, float f3, float f4) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        translate(f3, f4);
        scale(f, f2);
        translate(-f3, -f4);
    }

    public void rotate(float f) {
        if (f == 0.0f) {
            return;
        }
        nRotate(this.mNativeCanvasWrapper, f);
    }

    public final void rotate(float f, float f2, float f3) {
        if (f == 0.0f) {
            return;
        }
        translate(f2, f3);
        rotate(f);
        translate(-f2, -f3);
    }

    public void skew(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        nSkew(this.mNativeCanvasWrapper, f, f2);
    }

    public void concat(android.graphics.Matrix matrix) {
        if (matrix != null) {
            nConcat(this.mNativeCanvasWrapper, matrix.ni());
        }
    }

    public void concat44(android.graphics.Matrix44 matrix44) {
        if (matrix44 != null) {
            nConcat(this.mNativeCanvasWrapper, matrix44.mBackingArray);
        }
    }

    public void setMatrix(android.graphics.Matrix matrix) {
        nSetMatrix(this.mNativeCanvasWrapper, matrix == null ? 0L : matrix.ni());
    }

    @java.lang.Deprecated
    public void getMatrix(android.graphics.Matrix matrix) {
        nGetMatrix(this.mNativeCanvasWrapper, matrix.ni());
    }

    @java.lang.Deprecated
    public final android.graphics.Matrix getMatrix() {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        getMatrix(matrix);
        return matrix;
    }

    private static void checkValidClipOp(android.graphics.Region.Op op) {
        if (sCompatiblityVersion >= 28 && op != android.graphics.Region.Op.INTERSECT && op != android.graphics.Region.Op.DIFFERENCE) {
            throw new java.lang.IllegalArgumentException("Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed");
        }
    }

    @java.lang.Deprecated
    public boolean clipRect(android.graphics.RectF rectF, android.graphics.Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, op.nativeInt);
    }

    @java.lang.Deprecated
    public boolean clipRect(android.graphics.Rect rect, android.graphics.Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, op.nativeInt);
    }

    public boolean clipRectUnion(android.graphics.Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, android.graphics.Region.Op.UNION.nativeInt);
    }

    public boolean clipRect(android.graphics.RectF rectF) {
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, android.graphics.Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(android.graphics.RectF rectF) {
        return nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, android.graphics.Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipRect(android.graphics.Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, android.graphics.Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(android.graphics.Rect rect) {
        return nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, android.graphics.Region.Op.DIFFERENCE.nativeInt);
    }

    @java.lang.Deprecated
    public boolean clipRect(float f, float f2, float f3, float f4, android.graphics.Region.Op op) {
        checkValidClipOp(op);
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, op.nativeInt);
    }

    public boolean clipRect(float f, float f2, float f3, float f4) {
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, android.graphics.Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(float f, float f2, float f3, float f4) {
        return nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, android.graphics.Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipRect(int i, int i2, int i3, int i4) {
        return nClipRect(this.mNativeCanvasWrapper, i, i2, i3, i4, android.graphics.Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipOutRect(int i, int i2, int i3, int i4) {
        return nClipRect(this.mNativeCanvasWrapper, i, i2, i3, i4, android.graphics.Region.Op.DIFFERENCE.nativeInt);
    }

    @java.lang.Deprecated
    public boolean clipPath(android.graphics.Path path, android.graphics.Region.Op op) {
        checkValidClipOp(op);
        return nClipPath(this.mNativeCanvasWrapper, path.readOnlyNI(), op.nativeInt);
    }

    public boolean clipPath(android.graphics.Path path) {
        return clipPath(path, android.graphics.Region.Op.INTERSECT);
    }

    public boolean clipOutPath(android.graphics.Path path) {
        return clipPath(path, android.graphics.Region.Op.DIFFERENCE);
    }

    @java.lang.Deprecated
    public boolean clipRegion(android.graphics.Region region, android.graphics.Region.Op op) {
        return false;
    }

    @java.lang.Deprecated
    public boolean clipRegion(android.graphics.Region region) {
        return false;
    }

    public void clipShader(android.graphics.Shader shader) {
        nClipShader(this.mNativeCanvasWrapper, shader.getNativeInstance(), android.graphics.Region.Op.INTERSECT.nativeInt);
    }

    public void clipOutShader(android.graphics.Shader shader) {
        nClipShader(this.mNativeCanvasWrapper, shader.getNativeInstance(), android.graphics.Region.Op.DIFFERENCE.nativeInt);
    }

    public android.graphics.DrawFilter getDrawFilter() {
        return this.mDrawFilter;
    }

    public void setDrawFilter(android.graphics.DrawFilter drawFilter) {
        long j;
        if (drawFilter == null) {
            j = 0;
        } else {
            j = drawFilter.mNativeInt;
        }
        this.mDrawFilter = drawFilter;
        nSetDrawFilter(this.mNativeCanvasWrapper, j);
    }

    @java.lang.Deprecated
    public boolean quickReject(android.graphics.RectF rectF, android.graphics.Canvas.EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public boolean quickReject(android.graphics.RectF rectF) {
        return nQuickReject(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @java.lang.Deprecated
    public boolean quickReject(android.graphics.Path path, android.graphics.Canvas.EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, path.readOnlyNI());
    }

    public boolean quickReject(android.graphics.Path path) {
        return nQuickReject(this.mNativeCanvasWrapper, path.readOnlyNI());
    }

    @java.lang.Deprecated
    public boolean quickReject(float f, float f2, float f3, float f4, android.graphics.Canvas.EdgeType edgeType) {
        return nQuickReject(this.mNativeCanvasWrapper, f, f2, f3, f4);
    }

    public boolean quickReject(float f, float f2, float f3, float f4) {
        return nQuickReject(this.mNativeCanvasWrapper, f, f2, f3, f4);
    }

    public boolean getClipBounds(android.graphics.Rect rect) {
        return nGetClipBounds(this.mNativeCanvasWrapper, rect);
    }

    public final android.graphics.Rect getClipBounds() {
        android.graphics.Rect rect = new android.graphics.Rect();
        getClipBounds(rect);
        return rect;
    }

    public void drawPicture(android.graphics.Picture picture) {
        picture.endRecording();
        int save = save();
        picture.draw(this);
        restoreToCount(save);
    }

    public void drawPicture(android.graphics.Picture picture, android.graphics.RectF rectF) {
        save();
        translate(rectF.left, rectF.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rectF.width() / picture.getWidth(), rectF.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    public void drawPicture(android.graphics.Picture picture, android.graphics.Rect rect) {
        save();
        translate(rect.left, rect.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            scale(rect.width() / picture.getWidth(), rect.height() / picture.getHeight());
        }
        drawPicture(picture);
        restore();
    }

    public enum VertexMode {
        TRIANGLES(0),
        TRIANGLE_STRIP(1),
        TRIANGLE_FAN(2);

        final int nativeInt;

        VertexMode(int i) {
            this.nativeInt = i;
        }
    }

    public void release() {
        this.mNativeCanvasWrapper = 0L;
        if (this.mFinalizer != null) {
            this.mFinalizer.run();
            this.mFinalizer = null;
        }
    }

    public static void freeCaches() {
        nFreeCaches();
    }

    public static void freeTextLayoutCaches() {
        nFreeTextLayoutCaches();
    }

    static void setCompatibilityVersion(int i) {
        sCompatiblityVersion = i;
        sCompatibilityRestore = i < 23;
        sCompatibilitySetBitmap = i < 26;
        nSetCompatibilityVersion(i);
    }

    @Override // android.graphics.BaseCanvas
    public void drawArc(android.graphics.RectF rectF, float f, float f2, boolean z, android.graphics.Paint paint) {
        super.drawArc(rectF, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean z, android.graphics.Paint paint) {
        super.drawArc(f, f2, f3, f4, f5, f6, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawARGB(int i, int i2, int i3, int i4) {
        super.drawARGB(i, i2, i3, i4);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(android.graphics.Bitmap bitmap, float f, float f2, android.graphics.Paint paint) {
        super.drawBitmap(bitmap, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, android.graphics.RectF rectF, android.graphics.Paint paint) {
        super.drawBitmap(bitmap, rect, rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(android.graphics.Bitmap bitmap, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Paint paint) {
        super.drawBitmap(bitmap, rect, rect2, paint);
    }

    @Override // android.graphics.BaseCanvas
    @java.lang.Deprecated
    public void drawBitmap(int[] iArr, int i, int i2, float f, float f2, int i3, int i4, boolean z, android.graphics.Paint paint) {
        super.drawBitmap(iArr, i, i2, f, f2, i3, i4, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    @java.lang.Deprecated
    public void drawBitmap(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, android.graphics.Paint paint) {
        super.drawBitmap(iArr, i, i2, i3, i4, i5, i6, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmap(android.graphics.Bitmap bitmap, android.graphics.Matrix matrix, android.graphics.Paint paint) {
        super.drawBitmap(bitmap, matrix, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawBitmapMesh(android.graphics.Bitmap bitmap, int i, int i2, float[] fArr, int i3, int[] iArr, int i4, android.graphics.Paint paint) {
        super.drawBitmapMesh(bitmap, i, i2, fArr, i3, iArr, i4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawCircle(float f, float f2, float f3, android.graphics.Paint paint) {
        super.drawCircle(f, f2, f3, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i) {
        super.drawColor(i);
    }

    public void drawColor(long j) {
        super.drawColor(j, android.graphics.BlendMode.SRC_OVER);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i, android.graphics.PorterDuff.Mode mode) {
        super.drawColor(i, mode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(int i, android.graphics.BlendMode blendMode) {
        super.drawColor(i, blendMode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawColor(long j, android.graphics.BlendMode blendMode) {
        super.drawColor(j, blendMode);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLine(float f, float f2, float f3, float f4, android.graphics.Paint paint) {
        super.drawLine(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLines(float[] fArr, int i, int i2, android.graphics.Paint paint) {
        super.drawLines(fArr, i, i2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawLines(float[] fArr, android.graphics.Paint paint) {
        super.drawLines(fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawOval(android.graphics.RectF rectF, android.graphics.Paint paint) {
        super.drawOval(rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawOval(float f, float f2, float f3, float f4, android.graphics.Paint paint) {
        super.drawOval(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPaint(android.graphics.Paint paint) {
        super.drawPaint(paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPatch(android.graphics.NinePatch ninePatch, android.graphics.Rect rect, android.graphics.Paint paint) {
        super.drawPatch(ninePatch, rect, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPatch(android.graphics.NinePatch ninePatch, android.graphics.RectF rectF, android.graphics.Paint paint) {
        super.drawPatch(ninePatch, rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPath(android.graphics.Path path, android.graphics.Paint paint) {
        super.drawPath(path, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoint(float f, float f2, android.graphics.Paint paint) {
        super.drawPoint(f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoints(float[] fArr, int i, int i2, android.graphics.Paint paint) {
        super.drawPoints(fArr, i, i2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawPoints(float[] fArr, android.graphics.Paint paint) {
        super.drawPoints(fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    @java.lang.Deprecated
    public void drawPosText(char[] cArr, int i, int i2, float[] fArr, android.graphics.Paint paint) {
        super.drawPosText(cArr, i, i2, fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    @java.lang.Deprecated
    public void drawPosText(java.lang.String str, float[] fArr, android.graphics.Paint paint) {
        super.drawPosText(str, fArr, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(android.graphics.RectF rectF, android.graphics.Paint paint) {
        super.drawRect(rectF, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(android.graphics.Rect rect, android.graphics.Paint paint) {
        super.drawRect(rect, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRect(float f, float f2, float f3, float f4, android.graphics.Paint paint) {
        super.drawRect(f, f2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRGB(int i, int i2, int i3) {
        super.drawRGB(i, i2, i3);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRoundRect(android.graphics.RectF rectF, float f, float f2, android.graphics.Paint paint) {
        super.drawRoundRect(rectF, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, android.graphics.Paint paint) {
        super.drawRoundRect(f, f2, f3, f4, f5, f6, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawDoubleRoundRect(android.graphics.RectF rectF, float f, float f2, android.graphics.RectF rectF2, float f3, float f4, android.graphics.Paint paint) {
        super.drawDoubleRoundRect(rectF, f, f2, rectF2, f3, f4, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawDoubleRoundRect(android.graphics.RectF rectF, float[] fArr, android.graphics.RectF rectF2, float[] fArr2, android.graphics.Paint paint) {
        super.drawDoubleRoundRect(rectF, fArr, rectF2, fArr2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawGlyphs(int[] iArr, int i, float[] fArr, int i2, int i3, android.graphics.fonts.Font font, android.graphics.Paint paint) {
        super.drawGlyphs(iArr, i, fArr, i2, i3, font, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(char[] cArr, int i, int i2, float f, float f2, android.graphics.Paint paint) {
        super.drawText(cArr, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(java.lang.String str, float f, float f2, android.graphics.Paint paint) {
        super.drawText(str, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(java.lang.String str, int i, int i2, float f, float f2, android.graphics.Paint paint) {
        super.drawText(str, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawText(java.lang.CharSequence charSequence, int i, int i2, float f, float f2, android.graphics.Paint paint) {
        super.drawText(charSequence, i, i2, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextOnPath(char[] cArr, int i, int i2, android.graphics.Path path, float f, float f2, android.graphics.Paint paint) {
        super.drawTextOnPath(cArr, i, i2, path, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextOnPath(java.lang.String str, android.graphics.Path path, float f, float f2, android.graphics.Paint paint) {
        super.drawTextOnPath(str, path, f, f2, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        super.drawTextRun(cArr, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        super.drawTextRun(charSequence, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawTextRun(android.graphics.text.MeasuredText measuredText, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        super.drawTextRun(measuredText, i, i2, i3, i4, f, f2, z, paint);
    }

    @Override // android.graphics.BaseCanvas
    public void drawVertices(android.graphics.Canvas.VertexMode vertexMode, int i, float[] fArr, int i2, float[] fArr2, int i3, int[] iArr, int i4, short[] sArr, int i5, int i6, android.graphics.Paint paint) {
        super.drawVertices(vertexMode, i, fArr, i2, fArr2, i3, iArr, i4, sArr, i5, i6, paint);
    }

    public void drawRenderNode(android.graphics.RenderNode renderNode) {
        throw new java.lang.IllegalArgumentException("Software rendering doesn't support drawRenderNode");
    }
}
