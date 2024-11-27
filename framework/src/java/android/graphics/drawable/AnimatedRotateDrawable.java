package android.graphics.drawable;

/* loaded from: classes.dex */
public class AnimatedRotateDrawable extends android.graphics.drawable.DrawableWrapper implements android.graphics.drawable.Animatable {
    private float mCurrentDegrees;
    private float mIncrement;
    private final java.lang.Runnable mNextFrame;
    private boolean mRunning;
    private android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState mState;

    public AnimatedRotateDrawable() {
        this(new android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState(null, null), null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = getDrawable();
        android.graphics.Rect bounds = drawable.getBounds();
        int i = bounds.right - bounds.left;
        int i2 = bounds.bottom - bounds.top;
        android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState animatedRotateState = this.mState;
        float f = animatedRotateState.mPivotXRel ? i * animatedRotateState.mPivotX : animatedRotateState.mPivotX;
        float f2 = animatedRotateState.mPivotYRel ? i2 * animatedRotateState.mPivotY : animatedRotateState.mPivotY;
        int save = canvas.save();
        canvas.rotate(this.mCurrentDegrees, f + bounds.left, f2 + bounds.top);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (!this.mRunning) {
            this.mRunning = true;
            nextFrame();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mRunning = false;
        unscheduleSelf(this.mNextFrame);
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nextFrame() {
        unscheduleSelf(this.mNextFrame);
        scheduleSelf(this.mNextFrame, android.os.SystemClock.uptimeMillis() + this.mState.mFrameDuration);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (z) {
            if (visible || z2) {
                this.mCurrentDegrees = 0.0f;
                nextFrame();
            }
        } else {
            unscheduleSelf(this.mNextFrame);
        }
        return visible;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimatedRotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState animatedRotateState = this.mState;
        if (animatedRotateState == null) {
            return;
        }
        if (animatedRotateState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(animatedRotateState.mThemeAttrs, com.android.internal.R.styleable.AnimatedRotateDrawable);
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
            if (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <animated-rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState animatedRotateState = this.mState;
        if (animatedRotateState == null) {
            return;
        }
        animatedRotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        animatedRotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (typedArray.hasValue(2)) {
            android.util.TypedValue peekValue = typedArray.peekValue(2);
            animatedRotateState.mPivotXRel = peekValue.type == 6;
            animatedRotateState.mPivotX = animatedRotateState.mPivotXRel ? peekValue.getFraction(1.0f, 1.0f) : peekValue.getFloat();
        }
        if (typedArray.hasValue(3)) {
            android.util.TypedValue peekValue2 = typedArray.peekValue(3);
            animatedRotateState.mPivotYRel = peekValue2.type == 6;
            animatedRotateState.mPivotY = animatedRotateState.mPivotYRel ? peekValue2.getFraction(1.0f, 1.0f) : peekValue2.getFloat();
        }
        setFramesCount(typedArray.getInt(5, animatedRotateState.mFramesCount));
        setFramesDuration(typedArray.getInt(4, animatedRotateState.mFrameDuration));
    }

    public void setFramesCount(int i) {
        this.mState.mFramesCount = i;
        this.mIncrement = 360.0f / this.mState.mFramesCount;
    }

    public void setFramesDuration(int i) {
        this.mState.mFrameDuration = i;
    }

    @Override // android.graphics.drawable.DrawableWrapper
    android.graphics.drawable.DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState(this.mState, null);
        return this.mState;
    }

    static final class AnimatedRotateState extends android.graphics.drawable.DrawableWrapper.DrawableWrapperState {
        int mFrameDuration;
        int mFramesCount;
        float mPivotX;
        boolean mPivotXRel;
        float mPivotY;
        boolean mPivotYRel;
        private int[] mThemeAttrs;

        public AnimatedRotateState(android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState animatedRotateState, android.content.res.Resources resources) {
            super(animatedRotateState, resources);
            this.mPivotXRel = false;
            this.mPivotX = 0.0f;
            this.mPivotYRel = false;
            this.mPivotY = 0.0f;
            this.mFrameDuration = 150;
            this.mFramesCount = 12;
            if (animatedRotateState != null) {
                this.mPivotXRel = animatedRotateState.mPivotXRel;
                this.mPivotX = animatedRotateState.mPivotX;
                this.mPivotYRel = animatedRotateState.mPivotYRel;
                this.mPivotY = animatedRotateState.mPivotY;
                this.mFramesCount = animatedRotateState.mFramesCount;
                this.mFrameDuration = animatedRotateState.mFrameDuration;
            }
        }

        @Override // android.graphics.drawable.DrawableWrapper.DrawableWrapperState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.AnimatedRotateDrawable(this, resources);
        }
    }

    private AnimatedRotateDrawable(android.graphics.drawable.AnimatedRotateDrawable.AnimatedRotateState animatedRotateState, android.content.res.Resources resources) {
        super(animatedRotateState, resources);
        this.mNextFrame = new java.lang.Runnable() { // from class: android.graphics.drawable.AnimatedRotateDrawable.1
            @Override // java.lang.Runnable
            public void run() {
                android.graphics.drawable.AnimatedRotateDrawable.this.mCurrentDegrees += android.graphics.drawable.AnimatedRotateDrawable.this.mIncrement;
                if (android.graphics.drawable.AnimatedRotateDrawable.this.mCurrentDegrees > 360.0f - android.graphics.drawable.AnimatedRotateDrawable.this.mIncrement) {
                    android.graphics.drawable.AnimatedRotateDrawable.this.mCurrentDegrees = 0.0f;
                }
                android.graphics.drawable.AnimatedRotateDrawable.this.invalidateSelf();
                android.graphics.drawable.AnimatedRotateDrawable.this.nextFrame();
            }
        };
        this.mState = animatedRotateState;
        updateLocalState();
    }

    private void updateLocalState() {
        this.mIncrement = 360.0f / this.mState.mFramesCount;
        android.graphics.drawable.Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setFilterBitmap(true);
            if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
                ((android.graphics.drawable.BitmapDrawable) drawable).setAntiAlias(true);
            }
        }
    }
}
