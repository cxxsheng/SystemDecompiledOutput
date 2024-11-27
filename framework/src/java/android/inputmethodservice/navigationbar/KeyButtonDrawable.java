package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
final class KeyButtonDrawable extends android.graphics.drawable.Drawable {
    public static final android.util.FloatProperty<android.inputmethodservice.navigationbar.KeyButtonDrawable> KEY_DRAWABLE_ROTATE = new android.util.FloatProperty<android.inputmethodservice.navigationbar.KeyButtonDrawable>("KeyButtonRotation") { // from class: android.inputmethodservice.navigationbar.KeyButtonDrawable.1
        @Override // android.util.FloatProperty
        public void setValue(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable, float f) {
            keyButtonDrawable.setRotation(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable) {
            return java.lang.Float.valueOf(keyButtonDrawable.getRotation());
        }
    };
    public static final android.util.FloatProperty<android.inputmethodservice.navigationbar.KeyButtonDrawable> KEY_DRAWABLE_TRANSLATE_Y = new android.util.FloatProperty<android.inputmethodservice.navigationbar.KeyButtonDrawable>("KeyButtonTranslateY") { // from class: android.inputmethodservice.navigationbar.KeyButtonDrawable.2
        @Override // android.util.FloatProperty
        public void setValue(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable, float f) {
            keyButtonDrawable.setTranslationY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable) {
            return java.lang.Float.valueOf(keyButtonDrawable.getTranslationY());
        }
    };
    private android.graphics.drawable.AnimatedVectorDrawable mAnimatedDrawable;
    private final android.graphics.drawable.Drawable.Callback mAnimatedDrawableCallback;
    private final android.graphics.Paint mIconPaint;
    private final android.graphics.Paint mShadowPaint;
    private final android.inputmethodservice.navigationbar.KeyButtonDrawable.ShadowDrawableState mState;

    KeyButtonDrawable(android.graphics.drawable.Drawable drawable, int i, int i2, boolean z, android.graphics.Color color) {
        this(drawable, new android.inputmethodservice.navigationbar.KeyButtonDrawable.ShadowDrawableState(i, i2, drawable instanceof android.graphics.drawable.AnimatedVectorDrawable, z, color));
    }

    private KeyButtonDrawable(android.graphics.drawable.Drawable drawable, android.inputmethodservice.navigationbar.KeyButtonDrawable.ShadowDrawableState shadowDrawableState) {
        this.mIconPaint = new android.graphics.Paint(3);
        this.mShadowPaint = new android.graphics.Paint(3);
        this.mAnimatedDrawableCallback = new android.graphics.drawable.Drawable.Callback() { // from class: android.inputmethodservice.navigationbar.KeyButtonDrawable.3
            @Override // android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(android.graphics.drawable.Drawable drawable2) {
                android.inputmethodservice.navigationbar.KeyButtonDrawable.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void scheduleDrawable(android.graphics.drawable.Drawable drawable2, java.lang.Runnable runnable, long j) {
                android.inputmethodservice.navigationbar.KeyButtonDrawable.this.scheduleSelf(runnable, j);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void unscheduleDrawable(android.graphics.drawable.Drawable drawable2, java.lang.Runnable runnable) {
                android.inputmethodservice.navigationbar.KeyButtonDrawable.this.unscheduleSelf(runnable);
            }
        };
        this.mState = shadowDrawableState;
        if (drawable != null) {
            this.mState.mBaseHeight = drawable.getIntrinsicHeight();
            this.mState.mBaseWidth = drawable.getIntrinsicWidth();
            this.mState.mChangingConfigurations = drawable.getChangingConfigurations();
            this.mState.mChildState = drawable.getConstantState();
        }
        if (canAnimate()) {
            this.mAnimatedDrawable = (android.graphics.drawable.AnimatedVectorDrawable) this.mState.mChildState.newDrawable().mutate();
            this.mAnimatedDrawable.setCallback(this.mAnimatedDrawableCallback);
            setDrawableBounds(this.mAnimatedDrawable);
        }
    }

    public void setDarkIntensity(float f) {
        this.mState.mDarkIntensity = f;
        int intValue = ((java.lang.Integer) android.animation.ArgbEvaluator.getInstance().evaluate(f, java.lang.Integer.valueOf(this.mState.mLightColor), java.lang.Integer.valueOf(this.mState.mDarkColor))).intValue();
        updateShadowAlpha();
        setColorFilter(new android.graphics.PorterDuffColorFilter(intValue, android.graphics.PorterDuff.Mode.SRC_ATOP));
    }

    public void setRotation(float f) {
        if (!canAnimate() && this.mState.mRotateDegrees != f) {
            this.mState.mRotateDegrees = f;
            invalidateSelf();
        }
    }

    public void setTranslationX(float f) {
        setTranslation(f, this.mState.mTranslationY);
    }

    public void setTranslationY(float f) {
        setTranslation(this.mState.mTranslationX, f);
    }

    public void setTranslation(float f, float f2) {
        if (this.mState.mTranslationX != f || this.mState.mTranslationY != f2) {
            this.mState.mTranslationX = f;
            this.mState.mTranslationY = f2;
            invalidateSelf();
        }
    }

    public void setShadowProperties(int i, int i2, int i3, int i4) {
        if (canAnimate()) {
            return;
        }
        if (this.mState.mShadowOffsetX != i || this.mState.mShadowOffsetY != i2 || this.mState.mShadowSize != i3 || this.mState.mShadowColor != i4) {
            this.mState.mShadowOffsetX = i;
            this.mState.mShadowOffsetY = i2;
            this.mState.mShadowSize = i3;
            this.mState.mShadowColor = i4;
            this.mShadowPaint.setColorFilter(new android.graphics.PorterDuffColorFilter(this.mState.mShadowColor, android.graphics.PorterDuff.Mode.SRC_ATOP));
            updateShadowAlpha();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        if (this.mAnimatedDrawable != null) {
            this.mAnimatedDrawable.jumpToCurrentState();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mState.mAlpha = i;
        this.mIconPaint.setAlpha(i);
        updateShadowAlpha();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mIconPaint.setColorFilter(colorFilter);
        if (this.mAnimatedDrawable != null) {
            if (hasOvalBg()) {
                this.mAnimatedDrawable.setColorFilter(new android.graphics.PorterDuffColorFilter(this.mState.mLightColor, android.graphics.PorterDuff.Mode.SRC_IN));
            } else {
                this.mAnimatedDrawable.setColorFilter(colorFilter);
            }
        }
        invalidateSelf();
    }

    public float getDarkIntensity() {
        return this.mState.mDarkIntensity;
    }

    public float getRotation() {
        return this.mState.mRotateDegrees;
    }

    public float getTranslationX() {
        return this.mState.mTranslationX;
    }

    public float getTranslationY() {
        return this.mState.mTranslationY;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mState.mBaseHeight + ((this.mState.mShadowSize + java.lang.Math.abs(this.mState.mShadowOffsetY)) * 2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mState.mBaseWidth + ((this.mState.mShadowSize + java.lang.Math.abs(this.mState.mShadowOffsetX)) * 2);
    }

    public boolean canAnimate() {
        return this.mState.mSupportsAnimation;
    }

    public void startAnimation() {
        if (this.mAnimatedDrawable != null) {
            this.mAnimatedDrawable.start();
        }
    }

    public void resetAnimation() {
        if (this.mAnimatedDrawable != null) {
            this.mAnimatedDrawable.reset();
        }
    }

    public void clearAnimationCallbacks() {
        if (this.mAnimatedDrawable != null) {
            this.mAnimatedDrawable.clearAnimationCallbacks();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        if (this.mAnimatedDrawable != null) {
            this.mAnimatedDrawable.draw(canvas);
            return;
        }
        boolean z = this.mState.mIsHardwareBitmap != canvas.isHardwareAccelerated();
        if (z) {
            this.mState.mIsHardwareBitmap = canvas.isHardwareAccelerated();
        }
        if (this.mState.mLastDrawnIcon == null || z) {
            regenerateBitmapIconCache();
        }
        canvas.save();
        canvas.translate(this.mState.mTranslationX, this.mState.mTranslationY);
        canvas.rotate(this.mState.mRotateDegrees, getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
        if (this.mState.mShadowSize > 0) {
            if (this.mState.mLastDrawnShadow == null || z) {
                regenerateBitmapShadowCache();
            }
            double d = (float) ((this.mState.mRotateDegrees * 3.141592653589793d) / 180.0d);
            canvas.drawBitmap(this.mState.mLastDrawnShadow, ((float) ((java.lang.Math.sin(d) * this.mState.mShadowOffsetY) + (java.lang.Math.cos(d) * this.mState.mShadowOffsetX))) - this.mState.mTranslationX, ((float) ((java.lang.Math.cos(d) * this.mState.mShadowOffsetY) - (java.lang.Math.sin(d) * this.mState.mShadowOffsetX))) - this.mState.mTranslationY, this.mShadowPaint);
        }
        canvas.drawBitmap(this.mState.mLastDrawnIcon, (android.graphics.Rect) null, bounds, this.mIconPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mState.canApplyTheme();
    }

    int getDrawableBackgroundColor() {
        return this.mState.mOvalBackgroundColor.toArgb();
    }

    boolean hasOvalBg() {
        return this.mState.mOvalBackgroundColor != null;
    }

    private void regenerateBitmapIconCache() {
        int intrinsicWidth = getIntrinsicWidth();
        int intrinsicHeight = getIntrinsicHeight();
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.drawable.Drawable mutate = this.mState.mChildState.newDrawable().mutate();
        setDrawableBounds(mutate);
        canvas.save();
        if (this.mState.mHorizontalFlip) {
            canvas.scale(-1.0f, 1.0f, intrinsicWidth * 0.5f, intrinsicHeight * 0.5f);
        }
        mutate.draw(canvas);
        canvas.restore();
        if (this.mState.mIsHardwareBitmap) {
            createBitmap = createBitmap.copy(android.graphics.Bitmap.Config.HARDWARE, false);
        }
        this.mState.mLastDrawnIcon = createBitmap;
    }

    private void regenerateBitmapShadowCache() {
        if (this.mState.mShadowSize == 0) {
            this.mState.mLastDrawnIcon = null;
            return;
        }
        int intrinsicWidth = getIntrinsicWidth();
        int intrinsicHeight = getIntrinsicHeight();
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.drawable.Drawable mutate = this.mState.mChildState.newDrawable().mutate();
        setDrawableBounds(mutate);
        canvas.save();
        if (this.mState.mHorizontalFlip) {
            canvas.scale(-1.0f, 1.0f, intrinsicWidth * 0.5f, intrinsicHeight * 0.5f);
        }
        mutate.draw(canvas);
        canvas.restore();
        android.graphics.Paint paint = new android.graphics.Paint(3);
        paint.setMaskFilter(new android.graphics.BlurMaskFilter(this.mState.mShadowSize, android.graphics.BlurMaskFilter.Blur.NORMAL));
        android.graphics.Bitmap extractAlpha = createBitmap.extractAlpha(paint, new int[2]);
        paint.setMaskFilter(null);
        createBitmap.eraseColor(0);
        canvas.drawBitmap(extractAlpha, r2[0], r2[1], paint);
        if (this.mState.mIsHardwareBitmap) {
            createBitmap = createBitmap.copy(android.graphics.Bitmap.Config.HARDWARE, false);
        }
        this.mState.mLastDrawnShadow = createBitmap;
    }

    private void updateShadowAlpha() {
        this.mShadowPaint.setAlpha(java.lang.Math.round(android.graphics.Color.alpha(this.mState.mShadowColor) * (this.mState.mAlpha / 255.0f) * (1.0f - this.mState.mDarkIntensity)));
    }

    private void setDrawableBounds(android.graphics.drawable.Drawable drawable) {
        int abs = this.mState.mShadowSize + java.lang.Math.abs(this.mState.mShadowOffsetX);
        int abs2 = this.mState.mShadowSize + java.lang.Math.abs(this.mState.mShadowOffsetY);
        drawable.setBounds(abs, abs2, getIntrinsicWidth() - abs, getIntrinsicHeight() - abs2);
    }

    private static class ShadowDrawableState extends android.graphics.drawable.Drawable.ConstantState {
        int mAlpha = 255;
        int mBaseHeight;
        int mBaseWidth;
        int mChangingConfigurations;
        android.graphics.drawable.Drawable.ConstantState mChildState;
        final int mDarkColor;
        float mDarkIntensity;
        boolean mHorizontalFlip;
        boolean mIsHardwareBitmap;
        android.graphics.Bitmap mLastDrawnIcon;
        android.graphics.Bitmap mLastDrawnShadow;
        final int mLightColor;
        final android.graphics.Color mOvalBackgroundColor;
        float mRotateDegrees;
        int mShadowColor;
        int mShadowOffsetX;
        int mShadowOffsetY;
        int mShadowSize;
        final boolean mSupportsAnimation;
        float mTranslationX;
        float mTranslationY;

        ShadowDrawableState(int i, int i2, boolean z, boolean z2, android.graphics.Color color) {
            this.mLightColor = i;
            this.mDarkColor = i2;
            this.mSupportsAnimation = z;
            this.mHorizontalFlip = z2;
            this.mOvalBackgroundColor = color;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.inputmethodservice.navigationbar.KeyButtonDrawable(null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }
    }

    public static android.inputmethodservice.navigationbar.KeyButtonDrawable create(android.content.Context context, int i, int i2, int i3, boolean z, android.graphics.Color color) {
        android.content.res.Resources resources = context.getResources();
        boolean z2 = resources.getConfiguration().getLayoutDirection() == 1;
        android.graphics.drawable.Drawable drawable = context.getDrawable(i3);
        android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable = new android.inputmethodservice.navigationbar.KeyButtonDrawable(drawable, i, i2, z2 && drawable.isAutoMirrored(), color);
        if (z) {
            keyButtonDrawable.setShadowProperties(android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(0.0f, resources), android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(1.0f, resources), android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(0.5f, resources), 805306368);
        }
        return keyButtonDrawable;
    }
}
