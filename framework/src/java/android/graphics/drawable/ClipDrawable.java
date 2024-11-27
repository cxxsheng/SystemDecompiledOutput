package android.graphics.drawable;

/* loaded from: classes.dex */
public class ClipDrawable extends android.graphics.drawable.DrawableWrapper {
    public static final int HORIZONTAL = 1;
    private static final int MAX_LEVEL = 10000;
    public static final int VERTICAL = 2;
    private android.graphics.drawable.ClipDrawable.ClipState mState;
    private final android.graphics.Rect mTmpRect;

    ClipDrawable() {
        this(new android.graphics.drawable.ClipDrawable.ClipState(null, null), null);
    }

    public ClipDrawable(android.graphics.drawable.Drawable drawable, int i, int i2) {
        this(new android.graphics.drawable.ClipDrawable.ClipState(null, null), null);
        this.mState.mGravity = i;
        this.mState.mOrientation = i2;
        setDrawable(drawable);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.ClipDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.ClipDrawable.ClipState clipState = this.mState;
        if (clipState != null && clipState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(clipState.mThemeAttrs, com.android.internal.R.styleable.ClipDrawable);
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
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <clip> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.ClipDrawable.ClipState clipState = this.mState;
        if (clipState == null) {
            return;
        }
        clipState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        clipState.mThemeAttrs = typedArray.extractThemeAttrs();
        clipState.mOrientation = typedArray.getInt(2, clipState.mOrientation);
        clipState.mGravity = typedArray.getInt(0, clipState.mGravity);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable.getOpacity() == -2 || drawable.getLevel() == 0) {
            return -2;
        }
        if (getLevel() >= 10000) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        int i;
        int i2;
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable.getLevel() == 0) {
            return;
        }
        android.graphics.Rect rect = this.mTmpRect;
        android.graphics.Rect bounds = getBounds();
        int level = getLevel();
        int width = bounds.width();
        if ((this.mState.mOrientation & 1) == 0) {
            i = width;
        } else {
            i = width - (((width + 0) * (10000 - level)) / 10000);
        }
        int height = bounds.height();
        if ((this.mState.mOrientation & 2) == 0) {
            i2 = height;
        } else {
            i2 = height - (((height + 0) * (10000 - level)) / 10000);
        }
        android.view.Gravity.apply(this.mState.mGravity, i, i2, bounds, rect, getLayoutDirection());
        if (i > 0 && i2 > 0) {
            canvas.save();
            canvas.clipRect(rect);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper
    android.graphics.drawable.DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new android.graphics.drawable.ClipDrawable.ClipState(this.mState, null);
        return this.mState;
    }

    static final class ClipState extends android.graphics.drawable.DrawableWrapper.DrawableWrapperState {
        int mGravity;
        int mOrientation;
        private int[] mThemeAttrs;

        ClipState(android.graphics.drawable.ClipDrawable.ClipState clipState, android.content.res.Resources resources) {
            super(clipState, resources);
            this.mOrientation = 1;
            this.mGravity = 3;
            if (clipState != null) {
                this.mOrientation = clipState.mOrientation;
                this.mGravity = clipState.mGravity;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.ClipDrawable(this, resources);
        }
    }

    private ClipDrawable(android.graphics.drawable.ClipDrawable.ClipState clipState, android.content.res.Resources resources) {
        super(clipState, resources);
        this.mTmpRect = new android.graphics.Rect();
        this.mState = clipState;
    }
}
