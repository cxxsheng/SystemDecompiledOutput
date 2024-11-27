package android.graphics.drawable;

/* loaded from: classes.dex */
public class NinePatchDrawable extends android.graphics.drawable.Drawable {
    private static final boolean DEFAULT_DITHER = false;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private android.graphics.BlendModeColorFilter mBlendModeFilter;
    private boolean mMutated;
    private android.graphics.drawable.NinePatchDrawable.NinePatchState mNinePatchState;
    private android.graphics.Insets mOpticalInsets;
    private android.graphics.Rect mOutlineInsets;
    private float mOutlineRadius;
    private android.graphics.Rect mPadding;
    private android.graphics.Paint mPaint;
    private int mTargetDensity;
    private android.graphics.Rect mTempRect;

    NinePatchDrawable() {
        this.mOpticalInsets = android.graphics.Insets.NONE;
        this.mTargetDensity = 160;
        this.mBitmapWidth = -1;
        this.mBitmapHeight = -1;
        this.mNinePatchState = new android.graphics.drawable.NinePatchDrawable.NinePatchState();
    }

    @java.lang.Deprecated
    public NinePatchDrawable(android.graphics.Bitmap bitmap, byte[] bArr, android.graphics.Rect rect, java.lang.String str) {
        this(new android.graphics.drawable.NinePatchDrawable.NinePatchState(new android.graphics.NinePatch(bitmap, bArr, str), rect), (android.content.res.Resources) null);
    }

    public NinePatchDrawable(android.content.res.Resources resources, android.graphics.Bitmap bitmap, byte[] bArr, android.graphics.Rect rect, java.lang.String str) {
        this(new android.graphics.drawable.NinePatchDrawable.NinePatchState(new android.graphics.NinePatch(bitmap, bArr, str), rect), resources);
    }

    public NinePatchDrawable(android.content.res.Resources resources, android.graphics.Bitmap bitmap, byte[] bArr, android.graphics.Rect rect, android.graphics.Rect rect2, java.lang.String str) {
        this(new android.graphics.drawable.NinePatchDrawable.NinePatchState(new android.graphics.NinePatch(bitmap, bArr, str), rect, rect2), resources);
    }

    @java.lang.Deprecated
    public NinePatchDrawable(android.graphics.NinePatch ninePatch) {
        this(new android.graphics.drawable.NinePatchDrawable.NinePatchState(ninePatch, new android.graphics.Rect()), (android.content.res.Resources) null);
    }

    public NinePatchDrawable(android.content.res.Resources resources, android.graphics.NinePatch ninePatch) {
        this(new android.graphics.drawable.NinePatchDrawable.NinePatchState(ninePatch, new android.graphics.Rect()), resources);
    }

    public void setTargetDensity(android.graphics.Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(android.util.DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (i == 0) {
            i = 160;
        }
        if (this.mTargetDensity != i) {
            this.mTargetDensity = i;
            computeBitmapSize();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        boolean z;
        int i;
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        android.graphics.Rect bounds = getBounds();
        if (this.mBlendModeFilter != null && getPaint().getColorFilter() == null) {
            this.mPaint.setColorFilter(this.mBlendModeFilter);
            z = true;
        } else {
            z = false;
        }
        int i2 = -1;
        if (ninePatchState.mBaseAlpha != 1.0f) {
            i = getPaint().getAlpha();
            this.mPaint.setAlpha((int) ((i * ninePatchState.mBaseAlpha) + 0.5f));
        } else {
            i = -1;
        }
        if (canvas.getDensity() == 0 && ninePatchState.mNinePatch.getDensity() != 0) {
            i2 = canvas.save();
            float density = this.mTargetDensity / ninePatchState.mNinePatch.getDensity();
            canvas.scale(density, density, bounds.left, bounds.top);
            if (this.mTempRect == null) {
                this.mTempRect = new android.graphics.Rect();
            }
            android.graphics.Rect rect = this.mTempRect;
            rect.left = bounds.left;
            rect.top = bounds.top;
            rect.right = bounds.left + java.lang.Math.round(bounds.width() / density);
            rect.bottom = bounds.top + java.lang.Math.round(bounds.height() / density);
            bounds = rect;
        }
        if (needsMirroring()) {
            if (i2 < 0) {
                i2 = canvas.save();
            }
            canvas.scale(-1.0f, 1.0f, (bounds.left + bounds.right) / 2.0f, (bounds.top + bounds.bottom) / 2.0f);
        }
        ninePatchState.mNinePatch.draw(canvas, bounds, this.mPaint);
        if (i2 >= 0) {
            canvas.restoreToCount(i2);
        }
        if (z) {
            this.mPaint.setColorFilter(null);
        }
        if (i >= 0) {
            this.mPaint.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mNinePatchState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(android.graphics.Rect rect) {
        if (this.mPadding != null) {
            rect.set(this.mPadding);
            return (rect.bottom | ((rect.left | rect.top) | rect.right)) != 0;
        }
        return super.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        android.graphics.NinePatch.InsetStruct ninePatchInsets;
        android.graphics.Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        if (this.mNinePatchState != null && this.mOutlineInsets != null && (ninePatchInsets = this.mNinePatchState.mNinePatch.getBitmap().getNinePatchInsets()) != null) {
            outline.setRoundRect(bounds.left + this.mOutlineInsets.left, bounds.top + this.mOutlineInsets.top, bounds.right - this.mOutlineInsets.right, bounds.bottom - this.mOutlineInsets.bottom, this.mOutlineRadius);
            outline.setAlpha(ninePatchInsets.outlineAlpha * (getAlpha() / 255.0f));
        } else {
            super.getOutline(outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        android.graphics.Insets insets = this.mOpticalInsets;
        if (needsMirroring()) {
            return android.graphics.Insets.of(insets.right, insets.top, insets.left, insets.bottom);
        }
        return insets;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mPaint == null && i == 255) {
            return;
        }
        getPaint().setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        if (this.mPaint == null) {
            return 255;
        }
        return getPaint().getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        if (this.mPaint == null && colorFilter == null) {
            return;
        }
        getPaint().setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mNinePatchState.mTint = colorStateList;
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, colorStateList, this.mNinePatchState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mNinePatchState.mBlendMode = blendMode;
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, this.mNinePatchState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        if (this.mPaint == null && !z) {
            return;
        }
        getPaint().setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mNinePatchState.mAutoMirrored = z;
    }

    private boolean needsMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mNinePatchState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        getPaint().setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return this.mPaint != null && getPaint().isFilterBitmap();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.NinePatchDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        android.content.res.Resources resources = typedArray.getResources();
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        ninePatchState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        ninePatchState.mThemeAttrs = typedArray.extractThemeAttrs();
        ninePatchState.mDither = typedArray.getBoolean(1, ninePatchState.mDither);
        int i = 0;
        int resourceId = typedArray.getResourceId(0, 0);
        if (resourceId != 0) {
            final android.graphics.Rect rect = new android.graphics.Rect();
            android.graphics.Rect rect2 = new android.graphics.Rect();
            android.graphics.Bitmap bitmap = null;
            try {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                java.io.InputStream openRawResource = resources.openRawResource(resourceId, typedValue);
                if (typedValue.density == 0) {
                    i = 160;
                } else if (typedValue.density != 65535) {
                    i = typedValue.density;
                }
                bitmap = android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(resources, openRawResource, i), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.NinePatchDrawable$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                        android.graphics.drawable.NinePatchDrawable.lambda$updateStateFromTypedArray$0(android.graphics.Rect.this, imageDecoder, imageInfo, source);
                    }
                });
                openRawResource.close();
            } catch (java.io.IOException e) {
            }
            if (bitmap == null) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <nine-patch> requires a valid src attribute");
            }
            if (bitmap.getNinePatchChunk() == null) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <nine-patch> requires a valid 9-patch source image");
            }
            bitmap.getOpticalInsets(rect2);
            ninePatchState.mNinePatch = new android.graphics.NinePatch(bitmap, bitmap.getNinePatchChunk());
            ninePatchState.mPadding = rect;
            ninePatchState.mOpticalInsets = android.graphics.Insets.of(rect2);
        }
        ninePatchState.mAutoMirrored = typedArray.getBoolean(4, ninePatchState.mAutoMirrored);
        ninePatchState.mBaseAlpha = typedArray.getFloat(3, ninePatchState.mBaseAlpha);
        int i2 = typedArray.getInt(5, -1);
        if (i2 != -1) {
            ninePatchState.mBlendMode = android.graphics.drawable.Drawable.parseBlendMode(i2, android.graphics.BlendMode.SRC_IN);
        }
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(2);
        if (colorStateList != null) {
            ninePatchState.mTint = colorStateList;
        }
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$0(android.graphics.Rect rect, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        imageDecoder.setOutPaddingRect(rect);
        imageDecoder.setAllocator(1);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState == null) {
            return;
        }
        if (ninePatchState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(ninePatchState.mThemeAttrs, com.android.internal.R.styleable.NinePatchDrawable);
            try {
                try {
                    updateStateFromTypedArray(resolveAttributes);
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                resolveAttributes.recycle();
            }
        }
        if (ninePatchState.mTint != null && ninePatchState.mTint.canApplyTheme()) {
            ninePatchState.mTint = ninePatchState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mNinePatchState != null && this.mNinePatchState.canApplyTheme();
    }

    public android.graphics.Paint getPaint() {
        if (this.mPaint == null) {
            this.mPaint = new android.graphics.Paint();
            this.mPaint.setDither(false);
        }
        return this.mPaint;
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
        return (this.mNinePatchState.mNinePatch.hasAlpha() || (this.mPaint != null && this.mPaint.getAlpha() < 255)) ? -3 : -1;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Region getTransparentRegion() {
        return this.mNinePatchState.mNinePatch.getTransparentRegion(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mNinePatchState.mChangingConfigurations = getChangingConfigurations();
        return this.mNinePatchState;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mNinePatchState = new android.graphics.drawable.NinePatchDrawable.NinePatchState(this.mNinePatchState);
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
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState.mTint != null && ninePatchState.mBlendMode != null) {
            this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, ninePatchState.mBlendMode);
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        return super.isStateful() || (ninePatchState.mTint != null && ninePatchState.mTint.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mNinePatchState.mTint != null && this.mNinePatchState.mTint.hasFocusStateSpecified();
    }

    static final class NinePatchState extends android.graphics.drawable.Drawable.ConstantState {
        boolean mAutoMirrored;
        float mBaseAlpha;
        android.graphics.BlendMode mBlendMode;
        int mChangingConfigurations;
        boolean mDither;
        android.graphics.NinePatch mNinePatch;
        android.graphics.Insets mOpticalInsets;
        android.graphics.Rect mPadding;
        int[] mThemeAttrs;
        android.content.res.ColorStateList mTint;

        NinePatchState() {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
        }

        NinePatchState(android.graphics.NinePatch ninePatch, android.graphics.Rect rect) {
            this(ninePatch, rect, null, false, false);
        }

        NinePatchState(android.graphics.NinePatch ninePatch, android.graphics.Rect rect, android.graphics.Rect rect2) {
            this(ninePatch, rect, rect2, false, false);
        }

        NinePatchState(android.graphics.NinePatch ninePatch, android.graphics.Rect rect, android.graphics.Rect rect2, boolean z, boolean z2) {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
            this.mNinePatch = ninePatch;
            this.mPadding = rect;
            this.mOpticalInsets = android.graphics.Insets.of(rect2);
            this.mDither = z;
            this.mAutoMirrored = z2;
        }

        NinePatchState(android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState) {
            this.mNinePatch = null;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mPadding = null;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mBaseAlpha = 1.0f;
            this.mDither = false;
            this.mAutoMirrored = false;
            this.mChangingConfigurations = ninePatchState.mChangingConfigurations;
            this.mNinePatch = ninePatchState.mNinePatch;
            this.mTint = ninePatchState.mTint;
            this.mBlendMode = ninePatchState.mBlendMode;
            this.mPadding = ninePatchState.mPadding;
            this.mOpticalInsets = ninePatchState.mOpticalInsets;
            this.mBaseAlpha = ninePatchState.mBaseAlpha;
            this.mDither = ninePatchState.mDither;
            this.mAutoMirrored = ninePatchState.mAutoMirrored;
            this.mThemeAttrs = ninePatchState.mThemeAttrs;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mTint != null && this.mTint.canApplyTheme()) || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.NinePatchDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.NinePatchDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }
    }

    private void computeBitmapSize() {
        android.graphics.NinePatch ninePatch = this.mNinePatchState.mNinePatch;
        if (ninePatch == null) {
            return;
        }
        int i = this.mTargetDensity;
        int density = ninePatch.getDensity() == 0 ? i : ninePatch.getDensity();
        android.graphics.Insets insets = this.mNinePatchState.mOpticalInsets;
        if (insets != android.graphics.Insets.NONE) {
            this.mOpticalInsets = android.graphics.Insets.of(android.graphics.drawable.Drawable.scaleFromDensity(insets.left, density, i, true), android.graphics.drawable.Drawable.scaleFromDensity(insets.top, density, i, true), android.graphics.drawable.Drawable.scaleFromDensity(insets.right, density, i, true), android.graphics.drawable.Drawable.scaleFromDensity(insets.bottom, density, i, true));
        } else {
            this.mOpticalInsets = android.graphics.Insets.NONE;
        }
        android.graphics.Rect rect = this.mNinePatchState.mPadding;
        if (rect != null) {
            if (this.mPadding == null) {
                this.mPadding = new android.graphics.Rect();
            }
            this.mPadding.left = android.graphics.drawable.Drawable.scaleFromDensity(rect.left, density, i, true);
            this.mPadding.top = android.graphics.drawable.Drawable.scaleFromDensity(rect.top, density, i, true);
            this.mPadding.right = android.graphics.drawable.Drawable.scaleFromDensity(rect.right, density, i, true);
            this.mPadding.bottom = android.graphics.drawable.Drawable.scaleFromDensity(rect.bottom, density, i, true);
        } else {
            this.mPadding = null;
        }
        this.mBitmapHeight = android.graphics.drawable.Drawable.scaleFromDensity(ninePatch.getHeight(), density, i, true);
        this.mBitmapWidth = android.graphics.drawable.Drawable.scaleFromDensity(ninePatch.getWidth(), density, i, true);
        android.graphics.NinePatch.InsetStruct ninePatchInsets = ninePatch.getBitmap().getNinePatchInsets();
        if (ninePatchInsets != null) {
            android.graphics.Rect rect2 = ninePatchInsets.outlineRect;
            this.mOutlineInsets = android.graphics.NinePatch.InsetStruct.scaleInsets(rect2.left, rect2.top, rect2.right, rect2.bottom, i / density);
            this.mOutlineRadius = android.graphics.drawable.Drawable.scaleFromDensity(ninePatchInsets.outlineRadius, density, i);
            return;
        }
        this.mOutlineInsets = null;
    }

    private NinePatchDrawable(android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState, android.content.res.Resources resources) {
        this.mOpticalInsets = android.graphics.Insets.NONE;
        this.mTargetDensity = 160;
        this.mBitmapWidth = -1;
        this.mBitmapHeight = -1;
        this.mNinePatchState = ninePatchState;
        updateLocalState(resources);
    }

    private void updateLocalState(android.content.res.Resources resources) {
        android.graphics.drawable.NinePatchDrawable.NinePatchState ninePatchState = this.mNinePatchState;
        if (ninePatchState.mDither) {
            setDither(ninePatchState.mDither);
        }
        if (resources == null && ninePatchState.mNinePatch != null) {
            this.mTargetDensity = ninePatchState.mNinePatch.getDensity();
        } else {
            this.mTargetDensity = android.graphics.drawable.Drawable.resolveDensity(resources, this.mTargetDensity);
        }
        this.mBlendModeFilter = updateBlendModeFilter(this.mBlendModeFilter, ninePatchState.mTint, ninePatchState.mBlendMode);
        computeBitmapSize();
    }
}
