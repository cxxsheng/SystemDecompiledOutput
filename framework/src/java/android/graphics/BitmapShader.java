package android.graphics;

/* loaded from: classes.dex */
public class BitmapShader extends android.graphics.Shader {
    public static final int FILTER_MODE_DEFAULT = 0;
    public static final int FILTER_MODE_LINEAR = 2;
    public static final int FILTER_MODE_NEAREST = 1;
    android.graphics.Bitmap mBitmap;
    private boolean mFilterFromPaint;
    private int mFilterMode;
    private boolean mIsDirectSampled;
    private int mMaxAniso;
    private android.graphics.Gainmap mOverrideGainmap;
    private boolean mRequestDirectSampling;
    private int mTileX;
    private int mTileY;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FilterMode {
    }

    private static native long nativeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, long j3);

    public BitmapShader(android.graphics.Bitmap bitmap, android.graphics.Shader.TileMode tileMode, android.graphics.Shader.TileMode tileMode2) {
        this(bitmap, tileMode.nativeInt, tileMode2.nativeInt);
    }

    private BitmapShader(android.graphics.Bitmap bitmap, int i, int i2) {
        this.mMaxAniso = 0;
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Bitmap must be non-null");
        }
        bitmap.checkRecycled("Cannot create BitmapShader for recycled bitmap");
        this.mBitmap = bitmap;
        this.mTileX = i;
        this.mTileY = i2;
        this.mFilterMode = 0;
        this.mFilterFromPaint = false;
        this.mIsDirectSampled = false;
        this.mRequestDirectSampling = false;
    }

    public int getFilterMode() {
        return this.mFilterMode;
    }

    public void setFilterMode(int i) {
        if (i != this.mFilterMode) {
            this.mFilterMode = i;
            this.mMaxAniso = 0;
            discardNativeInstance();
        }
    }

    public void setMaxAnisotropy(int i) {
        if (this.mMaxAniso != i && i > 0) {
            this.mMaxAniso = i;
            this.mFilterMode = 0;
            discardNativeInstance();
        }
    }

    public void setOverrideGainmap(android.graphics.Gainmap gainmap) {
        if (!com.android.graphics.hwui.flags.Flags.gainmapAnimations()) {
            throw new java.lang.IllegalStateException("API not available");
        }
        if (gainmap == null) {
            this.mOverrideGainmap = null;
        } else {
            this.mOverrideGainmap = new android.graphics.Gainmap(gainmap, gainmap.getGainmapContents());
        }
        discardNativeInstance();
    }

    public int getMaxAnisotropy() {
        return this.mMaxAniso;
    }

    synchronized long getNativeInstanceWithDirectSampling() {
        this.mRequestDirectSampling = true;
        return getNativeInstance();
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        boolean z2;
        this.mBitmap.checkRecycled("BitmapShader's bitmap has been recycled");
        boolean z3 = this.mFilterMode == 2;
        if (this.mFilterMode == 0) {
            this.mFilterFromPaint = z;
            z2 = this.mFilterFromPaint;
        } else {
            z2 = z3;
        }
        this.mIsDirectSampled = this.mRequestDirectSampling;
        this.mRequestDirectSampling = false;
        return nativeCreate(j, this.mBitmap.getNativeInstance(), this.mTileX, this.mTileY, this.mMaxAniso, z2, this.mIsDirectSampled, this.mOverrideGainmap != null ? this.mOverrideGainmap.mNativePtr : 0L);
    }

    @Override // android.graphics.Shader
    protected boolean shouldDiscardNativeInstance(boolean z) {
        return this.mIsDirectSampled != this.mRequestDirectSampling || (this.mFilterMode == 0 && this.mFilterFromPaint != z);
    }
}
