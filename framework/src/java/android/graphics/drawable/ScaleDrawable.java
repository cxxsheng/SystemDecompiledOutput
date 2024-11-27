package android.graphics.drawable;

/* loaded from: classes.dex */
public class ScaleDrawable extends android.graphics.drawable.DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    private android.graphics.drawable.ScaleDrawable.ScaleState mState;
    private final android.graphics.Rect mTmpRect;

    ScaleDrawable() {
        this(new android.graphics.drawable.ScaleDrawable.ScaleState(null, null), null);
    }

    public ScaleDrawable(android.graphics.drawable.Drawable drawable, int i, float f, float f2) {
        this(new android.graphics.drawable.ScaleDrawable.ScaleState(null, null), null);
        this.mState.mGravity = i;
        this.mState.mScaleWidth = f;
        this.mState.mScaleHeight = f2;
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.ScaleDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.ScaleDrawable.ScaleState scaleState = this.mState;
        if (scaleState == null) {
            return;
        }
        if (scaleState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(scaleState.mThemeAttrs, com.android.internal.R.styleable.ScaleDrawable);
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
        updateLocalState();
    }

    private void verifyRequiredAttributes(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        if (getDrawable() == null) {
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[0] == 0) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <scale> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.ScaleDrawable.ScaleState scaleState = this.mState;
        if (scaleState == null) {
            return;
        }
        scaleState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        scaleState.mThemeAttrs = typedArray.extractThemeAttrs();
        scaleState.mScaleWidth = getPercent(typedArray, 1, scaleState.mScaleWidth);
        scaleState.mScaleHeight = getPercent(typedArray, 2, scaleState.mScaleHeight);
        scaleState.mGravity = typedArray.getInt(3, scaleState.mGravity);
        scaleState.mUseIntrinsicSizeAsMin = typedArray.getBoolean(4, scaleState.mUseIntrinsicSizeAsMin);
        scaleState.mInitialLevel = typedArray.getInt(5, scaleState.mInitialLevel);
    }

    private static float getPercent(android.content.res.TypedArray typedArray, int i, float f) {
        int type = typedArray.getType(i);
        if (type == 6 || type == 0) {
            return typedArray.getFraction(i, 1, 1, f);
        }
        java.lang.String string = typedArray.getString(i);
        if (string != null && string.endsWith("%")) {
            return java.lang.Float.parseFloat(string.substring(0, string.length() - 1)) / 100.0f;
        }
        return f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable != null && drawable.getLevel() != 0) {
            drawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable.getLevel() == 0) {
            return -2;
        }
        int opacity = drawable.getOpacity();
        if (opacity == -1 && drawable.getLevel() < 10000) {
            return -3;
        }
        return opacity;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        onBoundsChange(getBounds());
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        int i;
        int i2;
        android.graphics.drawable.Drawable drawable = getDrawable();
        android.graphics.Rect rect2 = this.mTmpRect;
        boolean z = this.mState.mUseIntrinsicSizeAsMin;
        int level = getLevel();
        int width = rect.width();
        if (this.mState.mScaleWidth <= 0.0f) {
            i = width;
        } else {
            i = width - ((int) ((((width - (z ? drawable.getIntrinsicWidth() : 0)) * (10000 - level)) * this.mState.mScaleWidth) / 10000.0f));
        }
        int height = rect.height();
        if (this.mState.mScaleHeight <= 0.0f) {
            i2 = height;
        } else {
            i2 = height - ((int) ((((height - (z ? drawable.getIntrinsicHeight() : 0)) * (10000 - level)) * this.mState.mScaleHeight) / 10000.0f));
        }
        android.view.Gravity.apply(this.mState.mGravity, i, i2, rect, rect2, getLayoutDirection());
        if (i > 0 && i2 > 0) {
            drawable.setBounds(rect2.left, rect2.top, rect2.right, rect2.bottom);
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper
    android.graphics.drawable.DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new android.graphics.drawable.ScaleDrawable.ScaleState(this.mState, null);
        return this.mState;
    }

    static final class ScaleState extends android.graphics.drawable.DrawableWrapper.DrawableWrapperState {
        private static final float DO_NOT_SCALE = -1.0f;
        int mGravity;
        int mInitialLevel;
        float mScaleHeight;
        float mScaleWidth;
        private int[] mThemeAttrs;
        boolean mUseIntrinsicSizeAsMin;

        ScaleState(android.graphics.drawable.ScaleDrawable.ScaleState scaleState, android.content.res.Resources resources) {
            super(scaleState, resources);
            this.mScaleWidth = -1.0f;
            this.mScaleHeight = -1.0f;
            this.mGravity = 3;
            this.mUseIntrinsicSizeAsMin = false;
            this.mInitialLevel = 0;
            if (scaleState != null) {
                this.mScaleWidth = scaleState.mScaleWidth;
                this.mScaleHeight = scaleState.mScaleHeight;
                this.mGravity = scaleState.mGravity;
                this.mUseIntrinsicSizeAsMin = scaleState.mUseIntrinsicSizeAsMin;
                this.mInitialLevel = scaleState.mInitialLevel;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.ScaleDrawable(this, resources);
        }
    }

    private ScaleDrawable(android.graphics.drawable.ScaleDrawable.ScaleState scaleState, android.content.res.Resources resources) {
        super(scaleState, resources);
        this.mTmpRect = new android.graphics.Rect();
        this.mState = scaleState;
        updateLocalState();
    }

    private void updateLocalState() {
        setLevel(this.mState.mInitialLevel);
    }
}
