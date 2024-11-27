package android.graphics.drawable;

/* loaded from: classes.dex */
public class ColorStateListDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
    private android.graphics.drawable.ColorDrawable mColorDrawable;
    private boolean mMutated;
    private android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState mState;

    public ColorStateListDrawable() {
        this.mMutated = false;
        this.mState = new android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState();
        initializeColorDrawable();
    }

    public ColorStateListDrawable(android.content.res.ColorStateList colorStateList) {
        this.mMutated = false;
        this.mState = new android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState();
        initializeColorDrawable();
        setColorStateList(colorStateList);
    }

    private ColorStateListDrawable(android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState colorStateListDrawableState) {
        this.mMutated = false;
        this.mState = colorStateListDrawableState;
        initializeColorDrawable();
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        this.mColorDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mColorDrawable.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable getCurrent() {
        return this.mColorDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        if (this.mState.mColor != null) {
            setColorStateList(this.mState.mColor.obtainForTheme(theme));
        }
        if (this.mState.mTint != null) {
            setTintList(this.mState.mTint.obtainForTheme(theme));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return super.canApplyTheme() || this.mState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mState.mAlpha = i;
        onStateChange(getState());
    }

    public void clearAlpha() {
        this.mState.mAlpha = -1;
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.mColorDrawable.setTintList(colorStateList);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mState.mBlendMode = blendMode;
        this.mColorDrawable.setTintBlendMode(blendMode);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorDrawable.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mColorDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mColorDrawable.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        this.mColorDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        if (this.mState.mColor == null) {
            return false;
        }
        int colorForState = this.mState.mColor.getColorForState(iArr, this.mState.mColor.getDefaultColor());
        if (this.mState.mAlpha != -1) {
            colorForState = (colorForState & 16777215) | (android.util.MathUtils.constrain(this.mState.mAlpha, 0, 255) << 24);
        }
        if (colorForState != this.mColorDrawable.getColor()) {
            this.mColorDrawable.setColor(colorForState);
            this.mColorDrawable.setState(iArr);
            return true;
        }
        return this.mColorDrawable.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable == this.mColorDrawable && getCallback() != null) {
            getCallback().invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
        if (drawable == this.mColorDrawable && getCallback() != null) {
            getCallback().scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
        if (drawable == this.mColorDrawable && getCallback() != null) {
            getCallback().unscheduleDrawable(this, runnable);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations |= getChangingConfigurations() & (~this.mState.getChangingConfigurations());
        return this.mState;
    }

    public android.content.res.ColorStateList getColorStateList() {
        if (this.mState.mColor == null) {
            return android.content.res.ColorStateList.valueOf(this.mColorDrawable.getColor());
        }
        return this.mState.mColor;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    public void setColorStateList(android.content.res.ColorStateList colorStateList) {
        this.mState.mColor = colorStateList;
        onStateChange(getState());
    }

    static final class ColorStateListDrawableState extends android.graphics.drawable.Drawable.ConstantState {
        int mAlpha;
        android.graphics.BlendMode mBlendMode;
        int mChangingConfigurations;
        android.content.res.ColorStateList mColor;
        android.content.res.ColorStateList mTint;

        ColorStateListDrawableState() {
            this.mColor = null;
            this.mTint = null;
            this.mAlpha = -1;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mChangingConfigurations = 0;
        }

        ColorStateListDrawableState(android.graphics.drawable.ColorStateListDrawable.ColorStateListDrawableState colorStateListDrawableState) {
            this.mColor = null;
            this.mTint = null;
            this.mAlpha = -1;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mChangingConfigurations = 0;
            this.mColor = colorStateListDrawableState.mColor;
            this.mTint = colorStateListDrawableState.mTint;
            this.mAlpha = colorStateListDrawableState.mAlpha;
            this.mBlendMode = colorStateListDrawableState.mBlendMode;
            this.mChangingConfigurations = colorStateListDrawableState.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.ColorStateListDrawable(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mColor != null ? this.mColor.getChangingConfigurations() : 0) | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }

        public boolean isStateful() {
            return (this.mColor != null && this.mColor.isStateful()) || (this.mTint != null && this.mTint.isStateful());
        }

        public boolean hasFocusStateSpecified() {
            return (this.mColor != null && this.mColor.hasFocusStateSpecified()) || (this.mTint != null && this.mTint.hasFocusStateSpecified());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return (this.mColor != null && this.mColor.canApplyTheme()) || (this.mTint != null && this.mTint.canApplyTheme());
        }
    }

    private void initializeColorDrawable() {
        this.mColorDrawable = new android.graphics.drawable.ColorDrawable();
        this.mColorDrawable.setCallback(this);
        if (this.mState.mTint != null) {
            this.mColorDrawable.setTintList(this.mState.mTint);
        }
        if (this.mState.mBlendMode != DEFAULT_BLEND_MODE) {
            this.mColorDrawable.setTintBlendMode(this.mState.mBlendMode);
        }
    }
}
