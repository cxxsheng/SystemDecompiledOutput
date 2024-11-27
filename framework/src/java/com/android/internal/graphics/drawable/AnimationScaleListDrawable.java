package com.android.internal.graphics.drawable;

/* loaded from: classes4.dex */
public class AnimationScaleListDrawable extends android.graphics.drawable.DrawableContainer implements android.graphics.drawable.Animatable {
    private static final java.lang.String TAG = "AnimationScaleListDrawable";
    private com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState mAnimationScaleListState;
    private boolean mMutated;

    public AnimationScaleListDrawable() {
        this(null, null);
    }

    private AnimationScaleListDrawable(com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState animationScaleListState, android.content.res.Resources resources) {
        setConstantState(new com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState(animationScaleListState, this, resources));
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return selectDrawable(this.mAnimationScaleListState.getCurrentDrawableIndexBasedOnScale()) || super.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimationScaleListDrawable);
        updateDensity(resources);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onStateChange(getState());
    }

    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState animationScaleListState = this.mAnimationScaleListState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next2 != 3) {
                    if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimationScaleListDrawableItem);
                        android.graphics.drawable.Drawable drawable = obtainAttributes.getDrawable(0);
                        obtainAttributes.recycle();
                        if (drawable == null) {
                            do {
                                next = xmlPullParser.next();
                            } while (next == 4);
                            if (next != 2) {
                                throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                            }
                            drawable = android.graphics.drawable.Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                        }
                        animationScaleListState.addDrawable(drawable);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationScaleListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        java.lang.Object current = getCurrent();
        if (current != null && (current instanceof android.graphics.drawable.Animatable)) {
            ((android.graphics.drawable.Animatable) current).start();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        java.lang.Object current = getCurrent();
        if (current != null && (current instanceof android.graphics.drawable.Animatable)) {
            ((android.graphics.drawable.Animatable) current).stop();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        java.lang.Object current = getCurrent();
        if (current != null && (current instanceof android.graphics.drawable.Animatable)) {
            return ((android.graphics.drawable.Animatable) current).isRunning();
        }
        return false;
    }

    static class AnimationScaleListState extends android.graphics.drawable.DrawableContainer.DrawableContainerState {
        int mAnimatableDrawableIndex;
        int mStaticDrawableIndex;
        int[] mThemeAttrs;

        AnimationScaleListState(com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState animationScaleListState, com.android.internal.graphics.drawable.AnimationScaleListDrawable animationScaleListDrawable, android.content.res.Resources resources) {
            super(animationScaleListState, animationScaleListDrawable, resources);
            this.mThemeAttrs = null;
            this.mStaticDrawableIndex = -1;
            this.mAnimatableDrawableIndex = -1;
            if (animationScaleListState != null) {
                this.mThemeAttrs = animationScaleListState.mThemeAttrs;
                this.mStaticDrawableIndex = animationScaleListState.mStaticDrawableIndex;
                this.mAnimatableDrawableIndex = animationScaleListState.mAnimatableDrawableIndex;
            }
        }

        void mutate() {
            this.mThemeAttrs = this.mThemeAttrs != null ? (int[]) this.mThemeAttrs.clone() : null;
        }

        int addDrawable(android.graphics.drawable.Drawable drawable) {
            int addChild = addChild(drawable);
            if (drawable instanceof android.graphics.drawable.Animatable) {
                this.mAnimatableDrawableIndex = addChild;
            } else {
                this.mStaticDrawableIndex = addChild;
            }
            return addChild;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new com.android.internal.graphics.drawable.AnimationScaleListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new com.android.internal.graphics.drawable.AnimationScaleListDrawable(this, resources);
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || super.canApplyTheme();
        }

        public int getCurrentDrawableIndexBasedOnScale() {
            if (android.animation.ValueAnimator.getDurationScale() == 0.0f) {
                return this.mStaticDrawableIndex;
            }
            return this.mAnimatableDrawableIndex;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(android.graphics.drawable.DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState) {
            this.mAnimationScaleListState = (com.android.internal.graphics.drawable.AnimationScaleListDrawable.AnimationScaleListState) drawableContainerState;
        }
    }
}
