package android.graphics.drawable;

/* loaded from: classes.dex */
public class BitmapDrawable extends android.graphics.drawable.Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_DISABLED = -1;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private static final int TILE_MODE_UNDEFINED = -2;
    private int mBitmapHeight;
    private android.graphics.drawable.BitmapDrawable.BitmapState mBitmapState;
    private int mBitmapWidth;
    private android.graphics.BlendModeColorFilter mBlendModeFilter;
    private final android.graphics.Rect mDstRect;
    private boolean mDstRectAndInsetsDirty;
    private android.graphics.Matrix mMirrorMatrix;
    private boolean mMutated;
    private android.graphics.Insets mOpticalInsets;
    private int mTargetDensity;

    @java.lang.Deprecated
    public BitmapDrawable() {
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), null);
    }

    @java.lang.Deprecated
    public BitmapDrawable(android.content.res.Resources resources) {
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), resources);
    }

    @java.lang.Deprecated
    public BitmapDrawable(android.graphics.Bitmap bitmap) {
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        init(new android.graphics.drawable.BitmapDrawable.BitmapState(bitmap), null);
    }

    public BitmapDrawable(android.content.res.Resources resources, android.graphics.Bitmap bitmap) {
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        init(new android.graphics.drawable.BitmapDrawable.BitmapState(bitmap), resources);
    }

    @java.lang.Deprecated
    public BitmapDrawable(java.lang.String str) {
        this((android.content.res.Resources) null, str);
    }

    public BitmapDrawable(android.content.res.Resources resources, java.lang.String str) {
        java.lang.StringBuilder sb;
        java.io.FileInputStream fileInputStream;
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        try {
            fileInputStream = new java.io.FileInputStream(str);
        } catch (java.lang.Exception e) {
            init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                sb = new java.lang.StringBuilder();
            } else {
                return;
            }
        } catch (java.lang.Throwable th) {
            init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                android.util.Log.w("BitmapDrawable", "BitmapDrawable cannot decode " + str);
            }
            throw th;
        }
        try {
            android.graphics.Bitmap decodeBitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(resources, fileInputStream), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            });
            fileInputStream.close();
            init(new android.graphics.drawable.BitmapDrawable.BitmapState(decodeBitmap), resources);
            if (this.mBitmapState.mBitmap == null) {
                sb = new java.lang.StringBuilder();
                android.util.Log.w("BitmapDrawable", sb.append("BitmapDrawable cannot decode ").append(str).toString());
            }
        } catch (java.lang.Throwable th2) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th3) {
                th2.addSuppressed(th3);
            }
            throw th2;
        }
    }

    @java.lang.Deprecated
    public BitmapDrawable(java.io.InputStream inputStream) {
        this((android.content.res.Resources) null, inputStream);
    }

    public BitmapDrawable(android.content.res.Resources resources, java.io.InputStream inputStream) {
        java.lang.StringBuilder sb;
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        try {
            init(new android.graphics.drawable.BitmapDrawable.BitmapState(android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(resources, inputStream), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda2
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            })), resources);
        } catch (java.lang.Exception e) {
            init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                sb = new java.lang.StringBuilder();
            } else {
                return;
            }
        } catch (java.lang.Throwable th) {
            init(new android.graphics.drawable.BitmapDrawable.BitmapState((android.graphics.Bitmap) null), resources);
            if (this.mBitmapState.mBitmap == null) {
                android.util.Log.w("BitmapDrawable", "BitmapDrawable cannot decode " + inputStream);
            }
            throw th;
        }
        if (this.mBitmapState.mBitmap == null) {
            sb = new java.lang.StringBuilder();
            android.util.Log.w("BitmapDrawable", sb.append("BitmapDrawable cannot decode ").append(inputStream).toString());
        }
    }

    public final android.graphics.Paint getPaint() {
        return this.mBitmapState.mPaint;
    }

    public final android.graphics.Bitmap getBitmap() {
        return this.mBitmapState.mBitmap;
    }

    private void computeBitmapSize() {
        android.graphics.Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap != null) {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    public void setBitmap(android.graphics.Bitmap bitmap) {
        if (this.mBitmapState.mBitmap != bitmap) {
            this.mBitmapState.mBitmap = bitmap;
            computeBitmapSize();
            invalidateSelf();
        }
    }

    public void setTargetDensity(android.graphics.Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(android.util.DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
            if (this.mBitmapState.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public int getGravity() {
        return this.mBitmapState.mGravity;
    }

    public void setGravity(int i) {
        if (this.mBitmapState.mGravity != i) {
            this.mBitmapState.mGravity = i;
            this.mDstRectAndInsetsDirty = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        if (this.mBitmapState.mBitmap != null) {
            this.mBitmapState.mBitmap.setHasMipMap(z);
            invalidateSelf();
        }
    }

    public boolean hasMipMap() {
        return this.mBitmapState.mBitmap != null && this.mBitmapState.mBitmap.hasMipMap();
    }

    public void setAntiAlias(boolean z) {
        this.mBitmapState.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.mBitmapState.mPaint.isAntiAlias();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mBitmapState.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return this.mBitmapState.mPaint.isFilterBitmap();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mBitmapState.mPaint.setDither(z);
        invalidateSelf();
    }

    public android.graphics.Shader.TileMode getTileModeX() {
        return this.mBitmapState.mTileModeX;
    }

    public android.graphics.Shader.TileMode getTileModeY() {
        return this.mBitmapState.mTileModeY;
    }

    public void setTileModeX(android.graphics.Shader.TileMode tileMode) {
        setTileModeXY(tileMode, this.mBitmapState.mTileModeY);
    }

    public final void setTileModeY(android.graphics.Shader.TileMode tileMode) {
        setTileModeXY(this.mBitmapState.mTileModeX, tileMode);
    }

    public void setTileModeXY(android.graphics.Shader.TileMode tileMode, android.graphics.Shader.TileMode tileMode2) {
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTileModeX != tileMode || bitmapState.mTileModeY != tileMode2) {
            bitmapState.mTileModeX = tileMode;
            bitmapState.mTileModeY = tileMode2;
            bitmapState.mRebuildShader = true;
            this.mDstRectAndInsetsDirty = true;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mBitmapState.mAutoMirrored != z) {
            this.mBitmapState.mAutoMirrored = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        return this.mBitmapState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mBitmapState.getChangingConfigurations();
    }

    private boolean needMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        this.mDstRectAndInsetsDirty = true;
        android.graphics.Bitmap bitmap = this.mBitmapState.mBitmap;
        android.graphics.Shader shader = this.mBitmapState.mPaint.getShader();
        if (bitmap != null && shader != null) {
            updateShaderMatrix(bitmap, this.mBitmapState.mPaint, shader, needMirroring());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        int i;
        android.graphics.Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap == null) {
            return;
        }
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        android.graphics.Paint paint = bitmapState.mPaint;
        boolean z = false;
        if (bitmapState.mRebuildShader) {
            android.graphics.Shader.TileMode tileMode = bitmapState.mTileModeX;
            android.graphics.Shader.TileMode tileMode2 = bitmapState.mTileModeY;
            if (tileMode == null && tileMode2 == null) {
                paint.setShader(null);
            } else {
                if (tileMode == null) {
                    tileMode = android.graphics.Shader.TileMode.CLAMP;
                }
                if (tileMode2 == null) {
                    tileMode2 = android.graphics.Shader.TileMode.CLAMP;
                }
                paint.setShader(new android.graphics.BitmapShader(bitmap, tileMode, tileMode2));
            }
            bitmapState.mRebuildShader = false;
        }
        if (bitmapState.mBaseAlpha != 1.0f) {
            android.graphics.Paint paint2 = getPaint();
            i = paint2.getAlpha();
            paint2.setAlpha((int) ((i * bitmapState.mBaseAlpha) + 0.5f));
        } else {
            i = -1;
        }
        if (this.mBlendModeFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(this.mBlendModeFilter);
            z = true;
        }
        updateDstRectAndInsetsIfDirty();
        android.graphics.Shader shader = paint.getShader();
        boolean needMirroring = needMirroring();
        if (shader == null) {
            if (needMirroring) {
                canvas.save();
                canvas.translate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            canvas.drawBitmap(bitmap, (android.graphics.Rect) null, this.mDstRect, paint);
            if (needMirroring) {
                canvas.restore();
            }
        } else {
            updateShaderMatrix(bitmap, paint, shader, needMirroring);
            canvas.drawRect(this.mDstRect, paint);
        }
        if (z) {
            paint.setColorFilter(null);
        }
        if (i >= 0) {
            paint.setAlpha(i);
        }
    }

    private void updateShaderMatrix(android.graphics.Bitmap bitmap, android.graphics.Paint paint, android.graphics.Shader shader, boolean z) {
        int density = bitmap.getDensity();
        int i = this.mTargetDensity;
        boolean z2 = (density == 0 || density == i) ? false : true;
        if (z2 || z) {
            android.graphics.Matrix orCreateMirrorMatrix = getOrCreateMirrorMatrix();
            orCreateMirrorMatrix.reset();
            if (z) {
                orCreateMirrorMatrix.setTranslate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                orCreateMirrorMatrix.setScale(-1.0f, 1.0f);
            }
            if (z2) {
                float f = i / density;
                orCreateMirrorMatrix.postScale(f, f);
            }
            shader.setLocalMatrix(orCreateMirrorMatrix);
        } else {
            this.mMirrorMatrix = null;
            shader.setLocalMatrix(android.graphics.Matrix.IDENTITY_MATRIX);
        }
        paint.setShader(shader);
    }

    private android.graphics.Matrix getOrCreateMirrorMatrix() {
        if (this.mMirrorMatrix == null) {
            this.mMirrorMatrix = new android.graphics.Matrix();
        }
        return this.mMirrorMatrix;
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mDstRectAndInsetsDirty) {
            if (this.mBitmapState.mTileModeX == null && this.mBitmapState.mTileModeY == null) {
                android.graphics.Rect bounds = getBounds();
                android.view.Gravity.apply(this.mBitmapState.mGravity, this.mBitmapWidth, this.mBitmapHeight, bounds, this.mDstRect, getLayoutDirection());
                this.mOpticalInsets = android.graphics.Insets.of(this.mDstRect.left - bounds.left, this.mDstRect.top - bounds.top, bounds.right - this.mDstRect.right, bounds.bottom - this.mDstRect.bottom);
            } else {
                copyBounds(this.mDstRect);
                this.mOpticalInsets = android.graphics.Insets.NONE;
            }
        }
        this.mDstRectAndInsetsDirty = false;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        updateDstRectAndInsetsIfDirty();
        return this.mOpticalInsets;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        updateDstRectAndInsetsIfDirty();
        outline.setRect(this.mDstRect);
        outline.setAlpha(this.mBitmapState.mBitmap != null && !this.mBitmapState.mBitmap.hasAlpha() ? getAlpha() / 255.0f : 0.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mBitmapState.mPaint.getAlpha()) {
            this.mBitmapState.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mBitmapState.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mBitmapState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mBitmapState.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTint != colorStateList) {
            bitmapState.mTint = colorStateList;
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, colorStateList, this.mBitmapState.mBlendMode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mBlendMode != blendMode) {
            bitmapState.mBlendMode = blendMode;
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, blendMode);
            invalidateSelf();
        }
    }

    private android.content.res.ColorStateList getTint() {
        return this.mBitmapState.mTint;
    }

    private android.graphics.PorterDuff.Mode getTintMode() {
        return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mBitmapState.mBlendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(android.graphics.Xfermode xfermode) {
        this.mBitmapState.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mBitmapState = new android.graphics.drawable.BitmapDrawable.BitmapState(this.mBitmapState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTint != null && bitmapState.mBlendMode != null) {
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, bitmapState.mTint, bitmapState.mBlendMode);
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.mBitmapState.mTint != null && this.mBitmapState.mTint.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mBitmapState.mTint != null && this.mBitmapState.mTint.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.BitmapDrawable);
        updateStateFromTypedArray(obtainAttributes, this.mSrcDensityOverride);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void verifyRequiredAttributes(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mBitmap == null) {
            if (bitmapState.mThemeAttrs == null || bitmapState.mThemeAttrs[1] == 0) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <bitmap> requires a valid 'src' attribute");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray, int i) throws org.xmlpull.v1.XmlPullParserException {
        int i2;
        android.content.res.Resources resources = typedArray.getResources();
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        bitmapState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        bitmapState.mThemeAttrs = typedArray.extractThemeAttrs();
        bitmapState.mSrcDensityOverride = i;
        bitmapState.mTargetDensity = android.graphics.drawable.Drawable.resolveDensity(resources, 0);
        int resourceId = typedArray.getResourceId(1, 0);
        if (resourceId != 0) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            resources.getValueForDensity(resourceId, i, typedValue, true);
            if (i > 0 && typedValue.density > 0 && typedValue.density != 65535) {
                if (typedValue.density == i) {
                    typedValue.density = resources.getDisplayMetrics().densityDpi;
                } else {
                    typedValue.density = (typedValue.density * resources.getDisplayMetrics().densityDpi) / i;
                }
            }
            if (typedValue.density != 0) {
                if (typedValue.density == 65535) {
                    i2 = 0;
                } else {
                    i2 = typedValue.density;
                }
            } else {
                i2 = 160;
            }
            android.graphics.Bitmap bitmap = null;
            try {
                java.io.InputStream openRawResource = resources.openRawResource(resourceId, typedValue);
                try {
                    bitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(resources, openRawResource, i2), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.BitmapDrawable$$ExternalSyntheticLambda1
                        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                        public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                            imageDecoder.setAllocator(1);
                        }
                    });
                    if (openRawResource != null) {
                        openRawResource.close();
                    }
                } finally {
                }
            } catch (java.lang.Exception e) {
            }
            if (bitmap == null) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <bitmap> requires a valid 'src' attribute");
            }
            bitmapState.mBitmap = bitmap;
        }
        setMipMap(typedArray.getBoolean(8, bitmapState.mBitmap != null ? bitmapState.mBitmap.hasMipMap() : false));
        bitmapState.mAutoMirrored = typedArray.getBoolean(9, bitmapState.mAutoMirrored);
        bitmapState.mBaseAlpha = typedArray.getFloat(7, bitmapState.mBaseAlpha);
        int i3 = typedArray.getInt(10, -1);
        if (i3 != -1) {
            bitmapState.mBlendMode = android.graphics.drawable.Drawable.parseBlendMode(i3, android.graphics.BlendMode.SRC_IN);
        }
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(5);
        if (colorStateList != null) {
            bitmapState.mTint = colorStateList;
        }
        android.graphics.Paint paint = this.mBitmapState.mPaint;
        paint.setAntiAlias(typedArray.getBoolean(2, paint.isAntiAlias()));
        paint.setFilterBitmap(typedArray.getBoolean(3, paint.isFilterBitmap()));
        paint.setDither(typedArray.getBoolean(4, paint.isDither()));
        setGravity(typedArray.getInt(0, bitmapState.mGravity));
        int i4 = typedArray.getInt(6, -2);
        if (i4 != -2) {
            android.graphics.Shader.TileMode parseTileMode = parseTileMode(i4);
            setTileModeXY(parseTileMode, parseTileMode);
        }
        int i5 = typedArray.getInt(11, -2);
        if (i5 != -2) {
            setTileModeX(parseTileMode(i5));
        }
        int i6 = typedArray.getInt(12, -2);
        if (i6 != -2) {
            setTileModeY(parseTileMode(i6));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.BitmapDrawable.BitmapState bitmapState = this.mBitmapState;
        if (bitmapState == null) {
            return;
        }
        if (bitmapState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(bitmapState.mThemeAttrs, com.android.internal.R.styleable.BitmapDrawable);
            try {
                try {
                    updateStateFromTypedArray(resolveAttributes, bitmapState.mSrcDensityOverride);
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                resolveAttributes.recycle();
            }
        }
        if (bitmapState.mTint != null && bitmapState.mTint.canApplyTheme()) {
            bitmapState.mTint = bitmapState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    private static android.graphics.Shader.TileMode parseTileMode(int i) {
        switch (i) {
            case 0:
                return android.graphics.Shader.TileMode.CLAMP;
            case 1:
                return android.graphics.Shader.TileMode.REPEAT;
            case 2:
                return android.graphics.Shader.TileMode.MIRROR;
            default:
                return null;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mBitmapState != null && this.mBitmapState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        android.graphics.Bitmap bitmap;
        return (this.mBitmapState.mGravity == 119 && (bitmap = this.mBitmapState.mBitmap) != null && !bitmap.hasAlpha() && this.mBitmapState.mPaint.getAlpha() >= 255) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mBitmapState.mChangingConfigurations |= getChangingConfigurations();
        return this.mBitmapState;
    }

    static final class BitmapState extends android.graphics.drawable.Drawable.ConstantState {
        boolean mAutoMirrored;
        float mBaseAlpha;
        android.graphics.Bitmap mBitmap;
        android.graphics.BlendMode mBlendMode;
        int mChangingConfigurations;
        int mGravity;
        final android.graphics.Paint mPaint;
        boolean mRebuildShader;
        int mSrcDensityOverride;
        int mTargetDensity;
        int[] mThemeAttrs;
        android.graphics.Shader.TileMode mTileModeX;
        android.graphics.Shader.TileMode mTileModeY;
        android.content.res.ColorStateList mTint;

        BitmapState(android.graphics.Bitmap bitmap) {
            this.mThemeAttrs = null;
            this.mBitmap = null;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mGravity = 119;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mSrcDensityOverride = 0;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mBitmap = bitmap;
            this.mPaint = new android.graphics.Paint(6);
        }

        BitmapState(android.graphics.drawable.BitmapDrawable.BitmapState bitmapState) {
            this.mThemeAttrs = null;
            this.mBitmap = null;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mGravity = 119;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mSrcDensityOverride = 0;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mBitmap = bitmapState.mBitmap;
            this.mTint = bitmapState.mTint;
            this.mBlendMode = bitmapState.mBlendMode;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mGravity = bitmapState.mGravity;
            this.mTileModeX = bitmapState.mTileModeX;
            this.mTileModeY = bitmapState.mTileModeY;
            this.mSrcDensityOverride = bitmapState.mSrcDensityOverride;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new android.graphics.Paint(bitmapState.mPaint);
            this.mRebuildShader = bitmapState.mRebuildShader;
            this.mAutoMirrored = bitmapState.mAutoMirrored;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mTint != null && this.mTint.canApplyTheme());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.BitmapDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.BitmapDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }
    }

    private BitmapDrawable(android.graphics.drawable.BitmapDrawable.BitmapState bitmapState, android.content.res.Resources resources) {
        this.mDstRect = new android.graphics.Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mOpticalInsets = android.graphics.Insets.NONE;
        init(bitmapState, resources);
    }

    private void init(android.graphics.drawable.BitmapDrawable.BitmapState bitmapState, android.content.res.Resources resources) {
        this.mBitmapState = bitmapState;
        updateLocalState(resources);
        if (this.mBitmapState != null && resources != null) {
            this.mBitmapState.mTargetDensity = this.mTargetDensity;
        }
    }

    private void updateLocalState(android.content.res.Resources resources) {
        this.mTargetDensity = resolveDensity(resources, this.mBitmapState.mTargetDensity);
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, this.mBitmapState.mBlendMode);
        computeBitmapSize();
    }
}
