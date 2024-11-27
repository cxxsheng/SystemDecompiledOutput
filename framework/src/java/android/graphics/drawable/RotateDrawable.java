package android.graphics.drawable;

/* loaded from: classes.dex */
public class RotateDrawable extends android.graphics.drawable.DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    private android.graphics.drawable.RotateDrawable.RotateState mState;

    public RotateDrawable() {
        this(new android.graphics.drawable.RotateDrawable.RotateState(null, null), null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.RotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.RotateDrawable.RotateState rotateState = this.mState;
        if (rotateState != null && rotateState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(rotateState.mThemeAttrs, com.android.internal.R.styleable.RotateDrawable);
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
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.RotateDrawable.RotateState rotateState = this.mState;
        if (rotateState == null) {
            return;
        }
        rotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        rotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(4)) {
            android.util.TypedValue peekValue = typedArray.peekValue(4);
            rotateState.mPivotXRel = peekValue.type == 6;
            rotateState.mPivotX = rotateState.mPivotXRel ? peekValue.getFraction(1.0f, 1.0f) : peekValue.getFloat();
        }
        if (typedArray.hasValue(5)) {
            android.util.TypedValue peekValue2 = typedArray.peekValue(5);
            rotateState.mPivotYRel = peekValue2.type == 6;
            rotateState.mPivotY = rotateState.mPivotYRel ? peekValue2.getFraction(1.0f, 1.0f) : peekValue2.getFloat();
        }
        rotateState.mFromDegrees = typedArray.getFloat(2, rotateState.mFromDegrees);
        rotateState.mToDegrees = typedArray.getFloat(3, rotateState.mToDegrees);
        rotateState.mCurrentDegrees = rotateState.mFromDegrees;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = getDrawable();
        android.graphics.Rect bounds = drawable.getBounds();
        int i = bounds.right - bounds.left;
        int i2 = bounds.bottom - bounds.top;
        android.graphics.drawable.RotateDrawable.RotateState rotateState = this.mState;
        float f = rotateState.mPivotXRel ? i * rotateState.mPivotX : rotateState.mPivotX;
        float f2 = rotateState.mPivotYRel ? i2 * rotateState.mPivotY : rotateState.mPivotY;
        int save = canvas.save();
        canvas.rotate(rotateState.mCurrentDegrees, f + bounds.left, f2 + bounds.top);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    public void setFromDegrees(float f) {
        if (this.mState.mFromDegrees != f) {
            this.mState.mFromDegrees = f;
            invalidateSelf();
        }
    }

    public float getFromDegrees() {
        return this.mState.mFromDegrees;
    }

    public void setToDegrees(float f) {
        if (this.mState.mToDegrees != f) {
            this.mState.mToDegrees = f;
            invalidateSelf();
        }
    }

    public float getToDegrees() {
        return this.mState.mToDegrees;
    }

    public void setPivotX(float f) {
        if (this.mState.mPivotX != f) {
            this.mState.mPivotX = f;
            invalidateSelf();
        }
    }

    public float getPivotX() {
        return this.mState.mPivotX;
    }

    public void setPivotXRelative(boolean z) {
        if (this.mState.mPivotXRel != z) {
            this.mState.mPivotXRel = z;
            invalidateSelf();
        }
    }

    public boolean isPivotXRelative() {
        return this.mState.mPivotXRel;
    }

    public void setPivotY(float f) {
        if (this.mState.mPivotY != f) {
            this.mState.mPivotY = f;
            invalidateSelf();
        }
    }

    public float getPivotY() {
        return this.mState.mPivotY;
    }

    public void setPivotYRelative(boolean z) {
        if (this.mState.mPivotYRel != z) {
            this.mState.mPivotYRel = z;
            invalidateSelf();
        }
    }

    public boolean isPivotYRelative() {
        return this.mState.mPivotYRel;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        this.mState.mCurrentDegrees = android.util.MathUtils.lerp(this.mState.mFromDegrees, this.mState.mToDegrees, i / 10000.0f);
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper
    android.graphics.drawable.DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new android.graphics.drawable.RotateDrawable.RotateState(this.mState, null);
        return this.mState;
    }

    static final class RotateState extends android.graphics.drawable.DrawableWrapper.DrawableWrapperState {
        float mCurrentDegrees;
        float mFromDegrees;
        float mPivotX;
        boolean mPivotXRel;
        float mPivotY;
        boolean mPivotYRel;
        private int[] mThemeAttrs;
        float mToDegrees;

        RotateState(android.graphics.drawable.RotateDrawable.RotateState rotateState, android.content.res.Resources resources) {
            super(rotateState, resources);
            this.mPivotXRel = true;
            this.mPivotX = 0.5f;
            this.mPivotYRel = true;
            this.mPivotY = 0.5f;
            this.mFromDegrees = 0.0f;
            this.mToDegrees = 360.0f;
            this.mCurrentDegrees = 0.0f;
            if (rotateState != null) {
                this.mPivotXRel = rotateState.mPivotXRel;
                this.mPivotX = rotateState.mPivotX;
                this.mPivotYRel = rotateState.mPivotYRel;
                this.mPivotY = rotateState.mPivotY;
                this.mFromDegrees = rotateState.mFromDegrees;
                this.mToDegrees = rotateState.mToDegrees;
                this.mCurrentDegrees = rotateState.mCurrentDegrees;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.RotateDrawable(this, resources);
        }
    }

    private RotateDrawable(android.graphics.drawable.RotateDrawable.RotateState rotateState, android.content.res.Resources resources) {
        super(rotateState, resources);
        this.mState = rotateState;
    }
}
