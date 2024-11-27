package android.graphics.drawable;

/* loaded from: classes.dex */
public class InsetDrawable extends android.graphics.drawable.DrawableWrapper {
    private android.graphics.drawable.InsetDrawable.InsetState mState;
    private final android.graphics.Rect mTmpInsetRect;
    private final android.graphics.Rect mTmpRect;

    InsetDrawable() {
        this(new android.graphics.drawable.InsetDrawable.InsetState(null, null), (android.content.res.Resources) null);
    }

    public InsetDrawable(android.graphics.drawable.Drawable drawable, int i) {
        this(drawable, i, i, i, i);
    }

    public InsetDrawable(android.graphics.drawable.Drawable drawable, float f) {
        this(drawable, f, f, f, f);
    }

    public InsetDrawable(android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4) {
        this(new android.graphics.drawable.InsetDrawable.InsetState(null, null), (android.content.res.Resources) null);
        this.mState.mInsetLeft = new android.graphics.drawable.InsetDrawable.InsetValue(0.0f, i);
        this.mState.mInsetTop = new android.graphics.drawable.InsetDrawable.InsetValue(0.0f, i2);
        this.mState.mInsetRight = new android.graphics.drawable.InsetDrawable.InsetValue(0.0f, i3);
        this.mState.mInsetBottom = new android.graphics.drawable.InsetDrawable.InsetValue(0.0f, i4);
        setDrawable(drawable);
    }

    public InsetDrawable(android.graphics.drawable.Drawable drawable, float f, float f2, float f3, float f4) {
        this(new android.graphics.drawable.InsetDrawable.InsetState(null, null), (android.content.res.Resources) null);
        this.mState.mInsetLeft = new android.graphics.drawable.InsetDrawable.InsetValue(f, 0);
        this.mState.mInsetTop = new android.graphics.drawable.InsetDrawable.InsetValue(f2, 0);
        this.mState.mInsetRight = new android.graphics.drawable.InsetDrawable.InsetValue(f3, 0);
        this.mState.mInsetBottom = new android.graphics.drawable.InsetDrawable.InsetValue(f4, 0);
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.InsetDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.InsetDrawable.InsetState insetState = this.mState;
        if (insetState != null && insetState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(insetState.mThemeAttrs, com.android.internal.R.styleable.InsetDrawable);
            try {
                try {
                    updateStateFromTypedArray(resolveAttributes);
                    verifyRequiredAttributes(resolveAttributes);
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                resolveAttributes.recycle();
            }
        }
    }

    private void verifyRequiredAttributes(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <inset> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.InsetDrawable.InsetState insetState = this.mState;
        if (insetState == null) {
            return;
        }
        insetState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        insetState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(6)) {
            android.graphics.drawable.InsetDrawable.InsetValue inset = getInset(typedArray, 6, new android.graphics.drawable.InsetDrawable.InsetValue());
            insetState.mInsetLeft = inset;
            insetState.mInsetTop = inset;
            insetState.mInsetRight = inset;
            insetState.mInsetBottom = inset;
        }
        insetState.mInsetLeft = getInset(typedArray, 2, insetState.mInsetLeft);
        insetState.mInsetTop = getInset(typedArray, 4, insetState.mInsetTop);
        insetState.mInsetRight = getInset(typedArray, 3, insetState.mInsetRight);
        insetState.mInsetBottom = getInset(typedArray, 5, insetState.mInsetBottom);
    }

    private android.graphics.drawable.InsetDrawable.InsetValue getInset(android.content.res.TypedArray typedArray, int i, android.graphics.drawable.InsetDrawable.InsetValue insetValue) {
        if (typedArray.hasValue(i)) {
            android.util.TypedValue peekValue = typedArray.peekValue(i);
            if (peekValue.type == 6) {
                float fraction = peekValue.getFraction(1.0f, 1.0f);
                if (fraction >= 1.0f) {
                    throw new java.lang.IllegalStateException("Fraction cannot be larger than 1");
                }
                return new android.graphics.drawable.InsetDrawable.InsetValue(fraction, 0);
            }
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(i, 0);
            if (dimensionPixelOffset != 0) {
                return new android.graphics.drawable.InsetDrawable.InsetValue(0.0f, dimensionPixelOffset);
            }
        }
        return insetValue;
    }

    private void getInsets(android.graphics.Rect rect) {
        android.graphics.Rect bounds = getBounds();
        rect.left = this.mState.mInsetLeft.getDimension(bounds.width());
        rect.right = this.mState.mInsetRight.getDimension(bounds.width());
        rect.top = this.mState.mInsetTop.getDimension(bounds.height());
        rect.bottom = this.mState.mInsetBottom.getDimension(bounds.height());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean getPadding(android.graphics.Rect rect) {
        boolean padding = super.getPadding(rect);
        getInsets(this.mTmpInsetRect);
        rect.left += this.mTmpInsetRect.left;
        rect.right += this.mTmpInsetRect.right;
        rect.top += this.mTmpInsetRect.top;
        rect.bottom += this.mTmpInsetRect.bottom;
        return padding || (((this.mTmpInsetRect.left | this.mTmpInsetRect.right) | this.mTmpInsetRect.top) | this.mTmpInsetRect.bottom) != 0;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        android.graphics.Insets opticalInsets = super.getOpticalInsets();
        getInsets(this.mTmpInsetRect);
        return android.graphics.Insets.of(opticalInsets.left + this.mTmpInsetRect.left, opticalInsets.top + this.mTmpInsetRect.top, opticalInsets.right + this.mTmpInsetRect.right, opticalInsets.bottom + this.mTmpInsetRect.bottom);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        int opacity = getDrawable().getOpacity();
        getInsets(this.mTmpInsetRect);
        if (opacity == -1 && (this.mTmpInsetRect.left > 0 || this.mTmpInsetRect.top > 0 || this.mTmpInsetRect.right > 0 || this.mTmpInsetRect.bottom > 0)) {
            return -3;
        }
        return opacity;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        android.graphics.Rect rect2 = this.mTmpRect;
        rect2.set(rect);
        rect2.left += this.mState.mInsetLeft.getDimension(rect.width());
        rect2.top += this.mState.mInsetTop.getDimension(rect.height());
        rect2.right -= this.mState.mInsetRight.getDimension(rect.width());
        rect2.bottom -= this.mState.mInsetBottom.getDimension(rect.height());
        super.onBoundsChange(rect2);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        int intrinsicWidth = getDrawable().getIntrinsicWidth();
        float f = this.mState.mInsetLeft.mFraction + this.mState.mInsetRight.mFraction;
        if (intrinsicWidth < 0 || f >= 1.0f) {
            return -1;
        }
        return ((int) (intrinsicWidth / (1.0f - f))) + this.mState.mInsetLeft.mDimension + this.mState.mInsetRight.mDimension;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        int intrinsicHeight = getDrawable().getIntrinsicHeight();
        float f = this.mState.mInsetTop.mFraction + this.mState.mInsetBottom.mFraction;
        if (intrinsicHeight < 0 || f >= 1.0f) {
            return -1;
        }
        return ((int) (intrinsicHeight / (1.0f - f))) + this.mState.mInsetTop.mDimension + this.mState.mInsetBottom.mDimension;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        getDrawable().getOutline(outline);
    }

    @Override // android.graphics.drawable.DrawableWrapper
    android.graphics.drawable.DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new android.graphics.drawable.InsetDrawable.InsetState(this.mState, null);
        return this.mState;
    }

    static final class InsetState extends android.graphics.drawable.DrawableWrapper.DrawableWrapperState {
        android.graphics.drawable.InsetDrawable.InsetValue mInsetBottom;
        android.graphics.drawable.InsetDrawable.InsetValue mInsetLeft;
        android.graphics.drawable.InsetDrawable.InsetValue mInsetRight;
        android.graphics.drawable.InsetDrawable.InsetValue mInsetTop;
        private int[] mThemeAttrs;

        InsetState(android.graphics.drawable.InsetDrawable.InsetState insetState, android.content.res.Resources resources) {
            super(insetState, resources);
            if (insetState != null) {
                this.mInsetLeft = insetState.mInsetLeft.m1191clone();
                this.mInsetTop = insetState.mInsetTop.m1191clone();
                this.mInsetRight = insetState.mInsetRight.m1191clone();
                this.mInsetBottom = insetState.mInsetBottom.m1191clone();
                if (insetState.mDensity != this.mDensity) {
                    applyDensityScaling(insetState.mDensity, this.mDensity);
                    return;
                }
                return;
            }
            this.mInsetLeft = new android.graphics.drawable.InsetDrawable.InsetValue();
            this.mInsetTop = new android.graphics.drawable.InsetDrawable.InsetValue();
            this.mInsetRight = new android.graphics.drawable.InsetDrawable.InsetValue();
            this.mInsetBottom = new android.graphics.drawable.InsetDrawable.InsetValue();
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState
        void onDensityChanged(int i, int i2) {
            super.onDensityChanged(i, i2);
            applyDensityScaling(i, i2);
        }

        private void applyDensityScaling(int i, int i2) {
            this.mInsetLeft.scaleFromDensity(i, i2);
            this.mInsetTop.scaleFromDensity(i, i2);
            this.mInsetRight.scaleFromDensity(i, i2);
            this.mInsetBottom.scaleFromDensity(i, i2);
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            android.graphics.drawable.InsetDrawable.InsetState insetState;
            if (resources != null) {
                int i = resources.getDisplayMetrics().densityDpi;
                if (i == 0) {
                    i = 160;
                }
                if (i != this.mDensity) {
                    insetState = new android.graphics.drawable.InsetDrawable.InsetState(this, resources);
                } else {
                    insetState = this;
                }
            } else {
                insetState = this;
            }
            return new android.graphics.drawable.InsetDrawable(insetState, resources);
        }
    }

    static final class InsetValue implements java.lang.Cloneable {
        int mDimension;
        final float mFraction;

        public InsetValue() {
            this(0.0f, 0);
        }

        public InsetValue(float f, int i) {
            this.mFraction = f;
            this.mDimension = i;
        }

        int getDimension(int i) {
            return ((int) (i * this.mFraction)) + this.mDimension;
        }

        void scaleFromDensity(int i, int i2) {
            if (this.mDimension != 0) {
                this.mDimension = android.graphics.Bitmap.scaleFromDensity(this.mDimension, i, i2);
            }
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.graphics.drawable.InsetDrawable.InsetValue m1191clone() {
            return new android.graphics.drawable.InsetDrawable.InsetValue(this.mFraction, this.mDimension);
        }
    }

    private InsetDrawable(android.graphics.drawable.InsetDrawable.InsetState insetState, android.content.res.Resources resources) {
        super(insetState, resources);
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpInsetRect = new android.graphics.Rect();
        this.mState = insetState;
    }
}
