package android.graphics.drawable;

/* loaded from: classes.dex */
public class ColorDrawable extends android.graphics.drawable.Drawable {
    private android.graphics.BlendModeColorFilter mBlendModeColorFilter;

    @android.view.ViewDebug.ExportedProperty(deepExport = true, prefix = "state_")
    private android.graphics.drawable.ColorDrawable.ColorState mColorState;
    private boolean mMutated;
    private final android.graphics.Paint mPaint;

    public ColorDrawable() {
        this.mPaint = new android.graphics.Paint(1);
        this.mColorState = new android.graphics.drawable.ColorDrawable.ColorState();
    }

    public ColorDrawable(int i) {
        this.mPaint = new android.graphics.Paint(1);
        this.mColorState = new android.graphics.drawable.ColorDrawable.ColorState();
        setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mColorState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mColorState = new android.graphics.drawable.ColorDrawable.ColorState(this.mColorState);
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
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.ColorFilter colorFilter = this.mPaint.getColorFilter();
        if ((this.mColorState.mUseColor >>> 24) != 0 || colorFilter != null || this.mBlendModeColorFilter != null) {
            if (colorFilter == null) {
                this.mPaint.setColorFilter(this.mBlendModeColorFilter);
            }
            this.mPaint.setColor(this.mColorState.mUseColor);
            canvas.drawRect(getBounds(), this.mPaint);
            this.mPaint.setColorFilter(colorFilter);
        }
    }

    public int getColor() {
        return this.mColorState.mUseColor;
    }

    public void setColor(int i) {
        if (this.mColorState.mBaseColor != i || this.mColorState.mUseColor != i) {
            android.graphics.drawable.ColorDrawable.ColorState colorState = this.mColorState;
            this.mColorState.mUseColor = i;
            colorState.mBaseColor = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mColorState.mUseColor >>> 24;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        int i2 = ((((this.mColorState.mBaseColor >>> 24) * (i + (i >> 7))) >> 8) << 24) | ((this.mColorState.mBaseColor << 8) >>> 8);
        if (this.mColorState.mUseColor != i2) {
            this.mColorState.mUseColor = i2;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mColorState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mColorState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mColorState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mColorState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        android.graphics.drawable.ColorDrawable.ColorState colorState = this.mColorState;
        if (colorState.mTint != null && colorState.mBlendMode != null) {
            this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorState.mTint, colorState.mBlendMode);
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mColorState.mTint != null && this.mColorState.mTint.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mColorState.mTint != null && this.mColorState.mTint.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(android.graphics.Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public android.graphics.Xfermode getXfermode() {
        return this.mPaint.getXfermode();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mBlendModeColorFilter != null || this.mPaint.getColorFilter() != null) {
            return -3;
        }
        switch (this.mColorState.mUseColor >>> 24) {
            case 0:
                break;
            case 255:
                break;
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(getAlpha() / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.ColorDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState(resources);
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.ColorDrawable.ColorState colorState = this.mColorState;
        colorState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        colorState.mThemeAttrs = typedArray.extractThemeAttrs();
        colorState.mBaseColor = typedArray.getColor(0, colorState.mBaseColor);
        colorState.mUseColor = colorState.mBaseColor;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mColorState.canApplyTheme() || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.ColorDrawable.ColorState colorState = this.mColorState;
        if (colorState == null) {
            return;
        }
        if (colorState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(colorState.mThemeAttrs, com.android.internal.R.styleable.ColorDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (colorState.mTint != null && colorState.mTint.canApplyTheme()) {
            colorState.mTint = colorState.mTint.obtainForTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        return this.mColorState;
    }

    static final class ColorState extends android.graphics.drawable.Drawable.ConstantState {
        int mBaseColor;
        android.graphics.BlendMode mBlendMode;
        int mChangingConfigurations;
        int[] mThemeAttrs;
        android.content.res.ColorStateList mTint;

        @android.view.ViewDebug.ExportedProperty
        int mUseColor;

        ColorState() {
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
        }

        ColorState(android.graphics.drawable.ColorDrawable.ColorState colorState) {
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mThemeAttrs = colorState.mThemeAttrs;
            this.mBaseColor = colorState.mBaseColor;
            this.mUseColor = colorState.mUseColor;
            this.mChangingConfigurations = colorState.mChangingConfigurations;
            this.mTint = colorState.mTint;
            this.mBlendMode = colorState.mBlendMode;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mTint != null && this.mTint.canApplyTheme());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.ColorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.ColorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }
    }

    private ColorDrawable(android.graphics.drawable.ColorDrawable.ColorState colorState, android.content.res.Resources resources) {
        this.mPaint = new android.graphics.Paint(1);
        this.mColorState = colorState;
        updateLocalState(resources);
    }

    private void updateLocalState(android.content.res.Resources resources) {
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mColorState.mTint, this.mColorState.mBlendMode);
    }
}
