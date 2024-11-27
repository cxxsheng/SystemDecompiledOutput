package android.graphics.drawable;

/* loaded from: classes.dex */
public class AnimationDrawable extends android.graphics.drawable.DrawableContainer implements java.lang.Runnable, android.graphics.drawable.Animatable {
    private boolean mAnimating;
    private android.graphics.drawable.AnimationDrawable.AnimationState mAnimationState;
    private int mCurFrame;
    private boolean mMutated;
    private boolean mRunning;

    public AnimationDrawable() {
        this(null, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (z) {
            if (z2 || visible) {
                setFrame(z2 || ((!this.mRunning && !this.mAnimationState.mOneShot) || this.mCurFrame >= this.mAnimationState.getChildCount()) ? 0 : this.mCurFrame, true, this.mAnimating);
            }
        } else {
            unscheduleSelf(this);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        boolean z = true;
        this.mAnimating = true;
        if (!isRunning()) {
            if (this.mAnimationState.getChildCount() <= 1 && this.mAnimationState.mOneShot) {
                z = false;
            }
            setFrame(0, false, z);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimating = false;
        if (isRunning()) {
            this.mCurFrame = 0;
            unscheduleSelf(this);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // java.lang.Runnable
    public void run() {
        nextFrame(false);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(java.lang.Runnable runnable) {
        this.mRunning = false;
        super.unscheduleSelf(runnable);
    }

    public int getNumberOfFrames() {
        return this.mAnimationState.getChildCount();
    }

    public android.graphics.drawable.Drawable getFrame(int i) {
        return this.mAnimationState.getChild(i);
    }

    public int getDuration(int i) {
        return this.mAnimationState.mDurations[i];
    }

    public boolean isOneShot() {
        return this.mAnimationState.mOneShot;
    }

    public void setOneShot(boolean z) {
        this.mAnimationState.mOneShot = z;
    }

    public void addFrame(android.graphics.drawable.Drawable drawable, int i) {
        this.mAnimationState.addFrame(drawable, i);
        if (!this.mRunning) {
            setFrame(0, true, false);
        }
    }

    private void nextFrame(boolean z) {
        int i = this.mCurFrame + 1;
        int childCount = this.mAnimationState.getChildCount();
        boolean z2 = this.mAnimationState.mOneShot && i >= childCount + (-1);
        if (!this.mAnimationState.mOneShot && i >= childCount) {
            i = 0;
        }
        setFrame(i, z, true ^ z2);
    }

    private void setFrame(int i, boolean z, boolean z2) {
        if (i >= this.mAnimationState.getChildCount()) {
            return;
        }
        this.mAnimating = z2;
        this.mCurFrame = i;
        selectDrawable(i);
        if (z || z2) {
            unscheduleSelf(this);
        }
        if (z2) {
            this.mCurFrame = i;
            this.mRunning = true;
            scheduleSelf(this, android.os.SystemClock.uptimeMillis() + this.mAnimationState.mDurations[i]);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimationDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, obtainAttributes, 0);
        updateStateFromTypedArray(obtainAttributes);
        updateDensity(resources);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        setFrame(0, true, false);
    }

    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next2 != 3) {
                    if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimationDrawableItem);
                        int i = obtainAttributes.getInt(0, -1);
                        if (i < 0) {
                            throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'duration' attribute");
                        }
                        android.graphics.drawable.Drawable drawable = obtainAttributes.getDrawable(1);
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
                        this.mAnimationState.addFrame(drawable, i);
                        if (drawable != null) {
                            drawable.setCallback(this);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        this.mAnimationState.mVariablePadding = typedArray.getBoolean(1, this.mAnimationState.mVariablePadding);
        this.mAnimationState.mOneShot = typedArray.getBoolean(2, this.mAnimationState.mOneShot);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimationState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.DrawableContainer
    public android.graphics.drawable.AnimationDrawable.AnimationState cloneConstantState() {
        return new android.graphics.drawable.AnimationDrawable.AnimationState(this.mAnimationState, this, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    private static final class AnimationState extends android.graphics.drawable.DrawableContainer.DrawableContainerState {
        private int[] mDurations;
        private boolean mOneShot;

        AnimationState(android.graphics.drawable.AnimationDrawable.AnimationState animationState, android.graphics.drawable.AnimationDrawable animationDrawable, android.content.res.Resources resources) {
            super(animationState, animationDrawable, resources);
            this.mOneShot = false;
            if (animationState != null) {
                this.mDurations = animationState.mDurations;
                this.mOneShot = animationState.mOneShot;
            } else {
                this.mDurations = new int[getCapacity()];
                this.mOneShot = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mutate() {
            this.mDurations = (int[]) this.mDurations.clone();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.AnimationDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.AnimationDrawable(this, resources);
        }

        public void addFrame(android.graphics.drawable.Drawable drawable, int i) {
            this.mDurations[super.addChild(drawable)] = i;
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[] iArr = new int[i2];
            java.lang.System.arraycopy(this.mDurations, 0, iArr, 0, i);
            this.mDurations = iArr;
        }

        public long getTotalDuration() {
            if (this.mDurations != null) {
                int i = 0;
                for (int i2 : this.mDurations) {
                    i += i2;
                }
                return i;
            }
            return 0L;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(android.graphics.drawable.DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof android.graphics.drawable.AnimationDrawable.AnimationState) {
            this.mAnimationState = (android.graphics.drawable.AnimationDrawable.AnimationState) drawableContainerState;
        }
    }

    public long getTotalDuration() {
        return this.mAnimationState.getTotalDuration();
    }

    private AnimationDrawable(android.graphics.drawable.AnimationDrawable.AnimationState animationState, android.content.res.Resources resources) {
        this.mCurFrame = 0;
        setConstantState(new android.graphics.drawable.AnimationDrawable.AnimationState(animationState, this, resources));
        if (animationState != null) {
            setFrame(0, true, false);
        }
    }
}
