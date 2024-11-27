package android.graphics.drawable;

/* loaded from: classes.dex */
public class AnimatedVectorDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Animatable2 {
    private static final java.lang.String ANIMATED_VECTOR = "animated-vector";
    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;
    private static final java.lang.String LOGTAG = "AnimatedVectorDrawable";
    private static final java.lang.String TARGET = "target";
    private android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState mAnimatedVectorState;
    private java.util.ArrayList<android.graphics.drawable.Animatable2.AnimationCallback> mAnimationCallbacks;
    private android.animation.Animator.AnimatorListener mAnimatorListener;
    private android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator mAnimatorSet;
    private android.animation.AnimatorSet mAnimatorSetFromXml;
    private final android.graphics.drawable.Drawable.Callback mCallback;
    private boolean mMutated;
    private android.content.res.Resources mRes;

    private interface VectorDrawableAnimator {
        boolean canReverse();

        void end();

        long getTotalDuration();

        void init(android.animation.AnimatorSet animatorSet);

        boolean isInfinite();

        boolean isRunning();

        boolean isStarted();

        void onDraw(android.graphics.Canvas canvas);

        void pause();

        void removeListener(android.animation.Animator.AnimatorListener animatorListener);

        void reset();

        void resume();

        void reverse();

        void setListener(android.animation.Animator.AnimatorListener animatorListener);

        void start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nAddAnimator(long j, long j2, long j3, long j4, long j5, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nCreateAnimatorSet();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateGroupPropertyHolder(long j, int i, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreatePathColorPropertyHolder(long j, int i, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreatePathDataPropertyHolder(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreatePathPropertyHolder(long j, int i, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateRootAlphaPropertyHolder(long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nReset(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nReverse(long j, android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPropertyHolderData(long j, float[] fArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPropertyHolderData(long j, int[] iArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetVectorDrawableTarget(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nStart(long j, android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int i);

    public AnimatedVectorDrawable() {
        this(null, null);
    }

    private AnimatedVectorDrawable(android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState animatedVectorDrawableState, android.content.res.Resources resources) {
        this.mAnimatorSetFromXml = null;
        this.mAnimationCallbacks = null;
        this.mAnimatorListener = null;
        this.mCallback = new android.graphics.drawable.Drawable.Callback() { // from class: android.graphics.drawable.AnimatedVectorDrawable.1
            @Override // android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
                android.graphics.drawable.AnimatedVectorDrawable.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
                android.graphics.drawable.AnimatedVectorDrawable.this.scheduleSelf(runnable, j);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
                android.graphics.drawable.AnimatedVectorDrawable.this.unscheduleSelf(runnable);
            }
        };
        this.mAnimatedVectorState = new android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState(animatedVectorDrawableState, this.mCallback, resources);
        this.mAnimatorSet = new android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT(this);
        this.mRes = resources;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimatedVectorState = new android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState(this.mAnimatedVectorState, this.mCallback, this.mRes);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        if (this.mAnimatedVectorState.mVectorDrawable != null) {
            this.mAnimatedVectorState.mVectorDrawable.clearMutated();
        }
        this.mMutated = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldIgnoreInvalidAnimation() {
        return android.graphics.Compatibility.getTargetSdkVersion() < 24;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mAnimatedVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mAnimatedVectorState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mAnimatedVectorState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (!canvas.isHardwareAccelerated() && (this.mAnimatorSet instanceof android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) && !this.mAnimatorSet.isRunning() && ((android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) this.mAnimatorSet).mPendingAnimationActions.size() > 0) {
            fallbackOntoUI();
        }
        this.mAnimatorSet.onDraw(canvas);
        this.mAnimatedVectorState.mVectorDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        this.mAnimatedVectorState.mVectorDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return this.mAnimatedVectorState.mVectorDrawable.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        return this.mAnimatedVectorState.mVectorDrawable.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        return this.mAnimatedVectorState.mVectorDrawable.setLayoutDirection(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAnimatedVectorState.mVectorDrawable.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAnimatedVectorState.mVectorDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mAnimatedVectorState.mVectorDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mAnimatedVectorState.mVectorDrawable.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mAnimatedVectorState.mVectorDrawable.setTintList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspot(f, f2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mAnimatedVectorState.mVectorDrawable.setTintBlendMode(blendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (this.mAnimatorSet.isInfinite() && this.mAnimatorSet.isStarted()) {
            if (z) {
                this.mAnimatorSet.resume();
            } else {
                this.mAnimatorSet.pause();
            }
        }
        this.mAnimatedVectorState.mVectorDrawable.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mAnimatedVectorState.mVectorDrawable.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        this.mAnimatedVectorState.mVectorDrawable.getOutline(outline);
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        return this.mAnimatedVectorState.mVectorDrawable.getOpticalInsets();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        float f = 1.0f;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (ANIMATED_VECTOR.equals(name)) {
                    android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimatedVectorDrawable);
                    int resourceId = obtainAttributes.getResourceId(0, 0);
                    if (resourceId != 0) {
                        android.graphics.drawable.VectorDrawable vectorDrawable = (android.graphics.drawable.VectorDrawable) resources.getDrawable(resourceId, theme).mutate();
                        vectorDrawable.setAllowCaching(false);
                        vectorDrawable.setCallback(this.mCallback);
                        float pixelSize = vectorDrawable.getPixelSize();
                        if (animatedVectorDrawableState.mVectorDrawable != null) {
                            animatedVectorDrawableState.mVectorDrawable.setCallback(null);
                        }
                        animatedVectorDrawableState.mVectorDrawable = vectorDrawable;
                        f = pixelSize;
                    }
                    obtainAttributes.recycle();
                } else if (TARGET.equals(name)) {
                    android.content.res.TypedArray obtainAttributes2 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimatedVectorDrawableTarget);
                    java.lang.String string = obtainAttributes2.getString(0);
                    int resourceId2 = obtainAttributes2.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        if (theme != null) {
                            android.animation.Animator loadAnimator = android.animation.AnimatorInflater.loadAnimator(resources, theme, resourceId2, f);
                            updateAnimatorProperty(loadAnimator, string, animatedVectorDrawableState.mVectorDrawable, animatedVectorDrawableState.mShouldIgnoreInvalidAnim);
                            animatedVectorDrawableState.addTargetAnimator(string, loadAnimator);
                        } else {
                            animatedVectorDrawableState.addPendingAnimator(resourceId2, f, string);
                        }
                    }
                    obtainAttributes2.recycle();
                }
            }
            eventType = xmlPullParser.next();
        }
        if (animatedVectorDrawableState.mPendingAnims == null) {
            resources = null;
        }
        this.mRes = resources;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateAnimatorProperty(android.animation.Animator animator, java.lang.String str, android.graphics.drawable.VectorDrawable vectorDrawable, boolean z) {
        android.util.Property property;
        if (animator instanceof android.animation.ObjectAnimator) {
            for (android.animation.PropertyValuesHolder propertyValuesHolder : ((android.animation.ObjectAnimator) animator).getValues()) {
                java.lang.String propertyName = propertyValuesHolder.getPropertyName();
                java.lang.Object targetByName = vectorDrawable.getTargetByName(str);
                if (targetByName instanceof android.graphics.drawable.VectorDrawable.VObject) {
                    property = ((android.graphics.drawable.VectorDrawable.VObject) targetByName).getProperty(propertyName);
                } else if (!(targetByName instanceof android.graphics.drawable.VectorDrawable.VectorDrawableState)) {
                    property = null;
                } else {
                    property = ((android.graphics.drawable.VectorDrawable.VectorDrawableState) targetByName).getProperty(propertyName);
                }
                if (property != null) {
                    if (containsSameValueType(propertyValuesHolder, property)) {
                        propertyValuesHolder.setProperty(property);
                    } else if (!z) {
                        throw new java.lang.RuntimeException("Wrong valueType for Property: " + propertyName + ".  Expected type: " + property.getType().toString() + ". Actual type defined in resources: " + propertyValuesHolder.getValueType().toString());
                    }
                }
            }
            return;
        }
        if (animator instanceof android.animation.AnimatorSet) {
            java.util.Iterator<android.animation.Animator> it = ((android.animation.AnimatorSet) animator).getChildAnimations().iterator();
            while (it.hasNext()) {
                updateAnimatorProperty(it.next(), str, vectorDrawable, z);
            }
        }
    }

    private static boolean containsSameValueType(android.animation.PropertyValuesHolder propertyValuesHolder, android.util.Property property) {
        java.lang.Class valueType = propertyValuesHolder.getValueType();
        java.lang.Class type = property.getType();
        return (valueType == java.lang.Float.TYPE || valueType == java.lang.Float.class) ? type == java.lang.Float.TYPE || type == java.lang.Float.class : (valueType == java.lang.Integer.TYPE || valueType == java.lang.Integer.class) ? type == java.lang.Integer.TYPE || type == java.lang.Integer.class : valueType == type;
    }

    public void forceAnimationOnUI() {
        if (this.mAnimatorSet instanceof android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) {
            if (((android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) this.mAnimatorSet).isRunning()) {
                throw new java.lang.UnsupportedOperationException("Cannot force Animated Vector Drawable to run on UI thread when the animation has started on RenderThread.");
            }
            fallbackOntoUI();
        }
    }

    private void fallbackOntoUI() {
        if (this.mAnimatorSet instanceof android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) {
            android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT vectorDrawableAnimatorRT = (android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT) this.mAnimatorSet;
            this.mAnimatorSet = new android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorUI(this);
            if (this.mAnimatorSetFromXml != null) {
                this.mAnimatorSet.init(this.mAnimatorSetFromXml);
            }
            if (vectorDrawableAnimatorRT.mListener != null) {
                this.mAnimatorSet.setListener(vectorDrawableAnimatorRT.mListener);
            }
            vectorDrawableAnimatorRT.transferPendingActions(this.mAnimatorSet);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return (this.mAnimatedVectorState != null && this.mAnimatedVectorState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.VectorDrawable vectorDrawable = this.mAnimatedVectorState.mVectorDrawable;
        if (vectorDrawable != null && vectorDrawable.canApplyTheme()) {
            vectorDrawable.applyTheme(theme);
        }
        if (theme != null) {
            this.mAnimatedVectorState.inflatePendingAnimators(theme.getResources(), theme);
        }
        if (this.mAnimatedVectorState.mPendingAnims == null) {
            this.mRes = null;
        }
    }

    public long getTotalDuration() {
        return this.mAnimatorSet.getTotalDuration();
    }

    private static class AnimatedVectorDrawableState extends android.graphics.drawable.Drawable.ConstantState {
        java.util.ArrayList<android.animation.Animator> mAnimators;
        int mChangingConfigurations;
        java.util.ArrayList<android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState.PendingAnimator> mPendingAnims;
        private final boolean mShouldIgnoreInvalidAnim = android.graphics.drawable.AnimatedVectorDrawable.shouldIgnoreInvalidAnimation();
        android.util.ArrayMap<android.animation.Animator, java.lang.String> mTargetNameMap;
        android.graphics.drawable.VectorDrawable mVectorDrawable;

        public AnimatedVectorDrawableState(android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState animatedVectorDrawableState, android.graphics.drawable.Drawable.Callback callback, android.content.res.Resources resources) {
            if (animatedVectorDrawableState != null) {
                this.mChangingConfigurations = animatedVectorDrawableState.mChangingConfigurations;
                if (animatedVectorDrawableState.mVectorDrawable != null) {
                    android.graphics.drawable.Drawable.ConstantState constantState = animatedVectorDrawableState.mVectorDrawable.getConstantState();
                    if (resources != null) {
                        this.mVectorDrawable = (android.graphics.drawable.VectorDrawable) constantState.newDrawable(resources);
                    } else {
                        this.mVectorDrawable = (android.graphics.drawable.VectorDrawable) constantState.newDrawable();
                    }
                    this.mVectorDrawable = (android.graphics.drawable.VectorDrawable) this.mVectorDrawable.mutate();
                    this.mVectorDrawable.setCallback(callback);
                    this.mVectorDrawable.setLayoutDirection(animatedVectorDrawableState.mVectorDrawable.getLayoutDirection());
                    this.mVectorDrawable.setBounds(animatedVectorDrawableState.mVectorDrawable.getBounds());
                    this.mVectorDrawable.setAllowCaching(false);
                }
                if (animatedVectorDrawableState.mAnimators != null) {
                    this.mAnimators = new java.util.ArrayList<>(animatedVectorDrawableState.mAnimators);
                }
                if (animatedVectorDrawableState.mTargetNameMap != null) {
                    this.mTargetNameMap = new android.util.ArrayMap<>(animatedVectorDrawableState.mTargetNameMap);
                }
                if (animatedVectorDrawableState.mPendingAnims != null) {
                    this.mPendingAnims = new java.util.ArrayList<>(animatedVectorDrawableState.mPendingAnims);
                    return;
                }
                return;
            }
            this.mVectorDrawable = new android.graphics.drawable.VectorDrawable();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return (this.mVectorDrawable != null && this.mVectorDrawable.canApplyTheme()) || this.mPendingAnims != null || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.AnimatedVectorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.AnimatedVectorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public void addPendingAnimator(int i, float f, java.lang.String str) {
            if (this.mPendingAnims == null) {
                this.mPendingAnims = new java.util.ArrayList<>(1);
            }
            this.mPendingAnims.add(new android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState.PendingAnimator(i, f, str));
        }

        public void addTargetAnimator(java.lang.String str, android.animation.Animator animator) {
            if (this.mAnimators == null) {
                this.mAnimators = new java.util.ArrayList<>(1);
                this.mTargetNameMap = new android.util.ArrayMap<>(1);
            }
            this.mAnimators.add(animator);
            this.mTargetNameMap.put(animator, str);
        }

        public void prepareLocalAnimators(android.animation.AnimatorSet animatorSet, android.content.res.Resources resources) {
            if (this.mPendingAnims != null) {
                if (resources != null) {
                    inflatePendingAnimators(resources, null);
                } else {
                    android.util.Log.e(android.graphics.drawable.AnimatedVectorDrawable.LOGTAG, "Failed to load animators. Either the AnimatedVectorDrawable must be created using a Resources object or applyTheme() must be called with a non-null Theme object.");
                }
                this.mPendingAnims = null;
            }
            int size = this.mAnimators == null ? 0 : this.mAnimators.size();
            if (size > 0) {
                android.animation.AnimatorSet.Builder play = animatorSet.play(prepareLocalAnimator(0));
                for (int i = 1; i < size; i++) {
                    play.with(prepareLocalAnimator(i));
                }
            }
        }

        private android.animation.Animator prepareLocalAnimator(int i) {
            android.animation.Animator animator = this.mAnimators.get(i);
            android.animation.Animator mo77clone = animator.mo77clone();
            java.lang.String str = this.mTargetNameMap.get(animator);
            java.lang.Object targetByName = this.mVectorDrawable.getTargetByName(str);
            if (!this.mShouldIgnoreInvalidAnim) {
                if (targetByName == null) {
                    throw new java.lang.IllegalStateException("Target with the name \"" + str + "\" cannot be found in the VectorDrawable to be animated.");
                }
                if (!(targetByName instanceof android.graphics.drawable.VectorDrawable.VectorDrawableState) && !(targetByName instanceof android.graphics.drawable.VectorDrawable.VObject)) {
                    throw new java.lang.UnsupportedOperationException("Target should be either VGroup, VPath, or ConstantState, " + targetByName.getClass() + " is not supported");
                }
            }
            mo77clone.setTarget(targetByName);
            return mo77clone;
        }

        public void inflatePendingAnimators(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
            java.util.ArrayList<android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState.PendingAnimator> arrayList = this.mPendingAnims;
            if (arrayList != null) {
                this.mPendingAnims = null;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    android.graphics.drawable.AnimatedVectorDrawable.AnimatedVectorDrawableState.PendingAnimator pendingAnimator = arrayList.get(i);
                    android.animation.Animator newInstance = pendingAnimator.newInstance(resources, theme);
                    android.graphics.drawable.AnimatedVectorDrawable.updateAnimatorProperty(newInstance, pendingAnimator.target, this.mVectorDrawable, this.mShouldIgnoreInvalidAnim);
                    addTargetAnimator(pendingAnimator.target, newInstance);
                }
            }
        }

        private static class PendingAnimator {
            public final int animResId;
            public final float pathErrorScale;
            public final java.lang.String target;

            public PendingAnimator(int i, float f, java.lang.String str) {
                this.animResId = i;
                this.pathErrorScale = f;
                this.target = str;
            }

            public android.animation.Animator newInstance(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
                return android.animation.AnimatorInflater.loadAnimator(resources, theme, this.animResId, this.pathErrorScale);
            }
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mAnimatorSet.isRunning();
    }

    public void reset() {
        ensureAnimatorSet();
        this.mAnimatorSet.reset();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        ensureAnimatorSet();
        this.mAnimatorSet.start();
    }

    private void ensureAnimatorSet() {
        if (this.mAnimatorSetFromXml == null) {
            this.mAnimatorSetFromXml = new android.animation.AnimatorSet();
            this.mAnimatedVectorState.prepareLocalAnimators(this.mAnimatorSetFromXml, this.mRes);
            this.mAnimatorSet.init(this.mAnimatorSetFromXml);
            this.mRes = null;
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimatorSet.end();
    }

    public void reverse() {
        ensureAnimatorSet();
        if (!canReverse()) {
            android.util.Log.w(LOGTAG, "AnimatedVectorDrawable can't reverse()");
        } else {
            this.mAnimatorSet.reverse();
        }
    }

    public boolean canReverse() {
        return this.mAnimatorSet.canReverse();
    }

    @Override // android.graphics.drawable.Animatable2
    public void registerAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null) {
            return;
        }
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new java.util.ArrayList<>();
        }
        this.mAnimationCallbacks.add(animationCallback);
        if (this.mAnimatorListener == null) {
            this.mAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: android.graphics.drawable.AnimatedVectorDrawable.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator) {
                    java.util.ArrayList arrayList = new java.util.ArrayList(android.graphics.drawable.AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        ((android.graphics.drawable.Animatable2.AnimationCallback) arrayList.get(i)).onAnimationStart(android.graphics.drawable.AnimatedVectorDrawable.this);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    java.util.ArrayList arrayList = new java.util.ArrayList(android.graphics.drawable.AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        ((android.graphics.drawable.Animatable2.AnimationCallback) arrayList.get(i)).onAnimationEnd(android.graphics.drawable.AnimatedVectorDrawable.this);
                    }
                }
            };
        }
        this.mAnimatorSet.setListener(this.mAnimatorListener);
    }

    private void removeAnimatorSetListener() {
        if (this.mAnimatorListener != null) {
            this.mAnimatorSet.removeListener(this.mAnimatorListener);
            this.mAnimatorListener = null;
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public boolean unregisterAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback) {
        if (this.mAnimationCallbacks == null || animationCallback == null) {
            return false;
        }
        boolean remove = this.mAnimationCallbacks.remove(animationCallback);
        if (this.mAnimationCallbacks.size() == 0) {
            removeAnimatorSetListener();
        }
        return remove;
    }

    @Override // android.graphics.drawable.Animatable2
    public void clearAnimationCallbacks() {
        removeAnimatorSetListener();
        if (this.mAnimationCallbacks == null) {
            return;
        }
        this.mAnimationCallbacks.clear();
    }

    private static class VectorDrawableAnimatorUI implements android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator {
        private final android.graphics.drawable.Drawable mDrawable;
        private long mTotalDuration;
        private android.animation.AnimatorSet mSet = null;
        private java.util.ArrayList<android.animation.Animator.AnimatorListener> mListenerArray = null;
        private boolean mIsInfinite = false;

        VectorDrawableAnimatorUI(android.graphics.drawable.AnimatedVectorDrawable animatedVectorDrawable) {
            this.mDrawable = animatedVectorDrawable;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void init(android.animation.AnimatorSet animatorSet) {
            if (this.mSet != null) {
                throw new java.lang.UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
            }
            this.mSet = animatorSet.mo77clone();
            this.mTotalDuration = this.mSet.getTotalDuration();
            this.mIsInfinite = this.mTotalDuration == -1;
            if (this.mListenerArray != null && !this.mListenerArray.isEmpty()) {
                for (int i = 0; i < this.mListenerArray.size(); i++) {
                    this.mSet.addListener(this.mListenerArray.get(i));
                }
                this.mListenerArray.clear();
                this.mListenerArray = null;
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void start() {
            if (this.mSet == null || this.mSet.isStarted()) {
                return;
            }
            this.mSet.start();
            invalidateOwningView();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void end() {
            if (this.mSet == null) {
                return;
            }
            this.mSet.end();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reset() {
            if (this.mSet == null) {
                return;
            }
            start();
            this.mSet.cancel();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reverse() {
            if (this.mSet == null) {
                return;
            }
            this.mSet.reverse();
            invalidateOwningView();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean canReverse() {
            return this.mSet != null && this.mSet.canReverse();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void setListener(android.animation.Animator.AnimatorListener animatorListener) {
            if (this.mSet == null) {
                if (this.mListenerArray == null) {
                    this.mListenerArray = new java.util.ArrayList<>();
                }
                this.mListenerArray.add(animatorListener);
                return;
            }
            this.mSet.addListener(animatorListener);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeListener(android.animation.Animator.AnimatorListener animatorListener) {
            if (this.mSet == null) {
                if (this.mListenerArray == null) {
                    return;
                }
                this.mListenerArray.remove(animatorListener);
                return;
            }
            this.mSet.removeListener(animatorListener);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void onDraw(android.graphics.Canvas canvas) {
            if (this.mSet != null && this.mSet.isStarted()) {
                invalidateOwningView();
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isStarted() {
            return this.mSet != null && this.mSet.isStarted();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isRunning() {
            return this.mSet != null && this.mSet.isRunning();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void pause() {
            if (this.mSet == null) {
                return;
            }
            this.mSet.pause();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void resume() {
            if (this.mSet == null) {
                return;
            }
            this.mSet.resume();
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public long getTotalDuration() {
            return this.mTotalDuration;
        }
    }

    public static class VectorDrawableAnimatorRT implements android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator, android.view.NativeVectorDrawableAnimator {
        private static final int END_ANIMATION = 4;
        private static final int MAX_SAMPLE_POINTS = 300;
        private static final int RESET_ANIMATION = 3;
        private static final int REVERSE_ANIMATION = 2;
        private static final int START_ANIMATION = 1;
        private final android.graphics.drawable.AnimatedVectorDrawable mDrawable;
        private android.os.Handler mHandler;
        private long mSetPtr;
        private final com.android.internal.util.VirtualRefBasePtr mSetRefBasePtr;
        private long mTotalDuration;
        private android.animation.Animator.AnimatorListener mListener = null;
        private final android.util.LongArray mStartDelays = new android.util.LongArray();
        private android.animation.PropertyValuesHolder.PropertyValues mTmpValues = new android.animation.PropertyValuesHolder.PropertyValues();
        private boolean mContainsSequentialAnimators = false;
        private boolean mStarted = false;
        private boolean mInitialized = false;
        private boolean mIsReversible = false;
        private boolean mIsInfinite = false;
        private java.lang.ref.WeakReference<android.graphics.RenderNode> mLastSeenTarget = null;
        private int mLastListenerId = 0;
        private final android.util.IntArray mPendingAnimationActions = new android.util.IntArray();

        VectorDrawableAnimatorRT(android.graphics.drawable.AnimatedVectorDrawable animatedVectorDrawable) {
            this.mSetPtr = 0L;
            this.mDrawable = animatedVectorDrawable;
            this.mSetPtr = android.graphics.drawable.AnimatedVectorDrawable.nCreateAnimatorSet();
            this.mSetRefBasePtr = new com.android.internal.util.VirtualRefBasePtr(this.mSetPtr);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void init(android.animation.AnimatorSet animatorSet) {
            if (this.mInitialized) {
                throw new java.lang.UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
            }
            parseAnimatorSet(animatorSet, 0L);
            android.graphics.drawable.AnimatedVectorDrawable.nSetVectorDrawableTarget(this.mSetPtr, this.mDrawable.mAnimatedVectorState.mVectorDrawable.getNativeTree());
            this.mInitialized = true;
            this.mTotalDuration = animatorSet.getTotalDuration();
            this.mIsInfinite = this.mTotalDuration == -1;
            this.mIsReversible = true;
            if (this.mContainsSequentialAnimators) {
                this.mIsReversible = false;
                return;
            }
            for (int i = 0; i < this.mStartDelays.size(); i++) {
                if (this.mStartDelays.get(i) > 0) {
                    this.mIsReversible = false;
                    return;
                }
            }
        }

        private void parseAnimatorSet(android.animation.AnimatorSet animatorSet, long j) {
            java.util.ArrayList<android.animation.Animator> childAnimations = animatorSet.getChildAnimations();
            boolean shouldPlayTogether = animatorSet.shouldPlayTogether();
            for (int i = 0; i < childAnimations.size(); i++) {
                android.animation.Animator animator = childAnimations.get(i);
                if (animator instanceof android.animation.AnimatorSet) {
                    parseAnimatorSet((android.animation.AnimatorSet) animator, j);
                } else if (animator instanceof android.animation.ObjectAnimator) {
                    createRTAnimator((android.animation.ObjectAnimator) animator, j);
                }
                if (!shouldPlayTogether) {
                    j += animator.getTotalDuration();
                    this.mContainsSequentialAnimators = true;
                }
            }
        }

        private void createRTAnimator(android.animation.ObjectAnimator objectAnimator, long j) {
            android.animation.PropertyValuesHolder[] values = objectAnimator.getValues();
            java.lang.Object target = objectAnimator.getTarget();
            if (target instanceof android.graphics.drawable.VectorDrawable.VGroup) {
                createRTAnimatorForGroup(values, objectAnimator, (android.graphics.drawable.VectorDrawable.VGroup) target, j);
                return;
            }
            if (target instanceof android.graphics.drawable.VectorDrawable.VPath) {
                for (android.animation.PropertyValuesHolder propertyValuesHolder : values) {
                    propertyValuesHolder.getPropertyValues(this.mTmpValues);
                    if ((this.mTmpValues.endValue instanceof android.util.PathParser.PathData) && this.mTmpValues.propertyName.equals("pathData")) {
                        createRTAnimatorForPath(objectAnimator, (android.graphics.drawable.VectorDrawable.VPath) target, j);
                    } else if (target instanceof android.graphics.drawable.VectorDrawable.VFullPath) {
                        createRTAnimatorForFullPath(objectAnimator, (android.graphics.drawable.VectorDrawable.VFullPath) target, j);
                    } else if (!this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        throw new java.lang.IllegalArgumentException("ClipPath only supports PathData property");
                    }
                }
                return;
            }
            if (target instanceof android.graphics.drawable.VectorDrawable.VectorDrawableState) {
                createRTAnimatorForRootGroup(values, objectAnimator, (android.graphics.drawable.VectorDrawable.VectorDrawableState) target, j);
            }
        }

        private void createRTAnimatorForGroup(android.animation.PropertyValuesHolder[] propertyValuesHolderArr, android.animation.ObjectAnimator objectAnimator, android.graphics.drawable.VectorDrawable.VGroup vGroup, long j) {
            long nativePtr = vGroup.getNativePtr();
            for (android.animation.PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
                propertyValuesHolder.getPropertyValues(this.mTmpValues);
                int propertyIndex = android.graphics.drawable.VectorDrawable.VGroup.getPropertyIndex(this.mTmpValues.propertyName);
                if ((this.mTmpValues.type == java.lang.Float.class || this.mTmpValues.type == java.lang.Float.TYPE) && propertyIndex >= 0) {
                    long nCreateGroupPropertyHolder = android.graphics.drawable.AnimatedVectorDrawable.nCreateGroupPropertyHolder(nativePtr, propertyIndex, ((java.lang.Float) this.mTmpValues.startValue).floatValue(), ((java.lang.Float) this.mTmpValues.endValue).floatValue());
                    if (this.mTmpValues.dataSource != null) {
                        float[] createFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                        android.graphics.drawable.AnimatedVectorDrawable.nSetPropertyHolderData(nCreateGroupPropertyHolder, createFloatDataPoints, createFloatDataPoints.length);
                    }
                    createNativeChildAnimator(nCreateGroupPropertyHolder, j, objectAnimator);
                }
            }
        }

        private void createRTAnimatorForPath(android.animation.ObjectAnimator objectAnimator, android.graphics.drawable.VectorDrawable.VPath vPath, long j) {
            createNativeChildAnimator(android.graphics.drawable.AnimatedVectorDrawable.nCreatePathDataPropertyHolder(vPath.getNativePtr(), ((android.util.PathParser.PathData) this.mTmpValues.startValue).getNativePtr(), ((android.util.PathParser.PathData) this.mTmpValues.endValue).getNativePtr()), j, objectAnimator);
        }

        private void createRTAnimatorForFullPath(android.animation.ObjectAnimator objectAnimator, android.graphics.drawable.VectorDrawable.VFullPath vFullPath, long j) {
            long nCreatePathPropertyHolder;
            int propertyIndex = vFullPath.getPropertyIndex(this.mTmpValues.propertyName);
            long nativePtr = vFullPath.getNativePtr();
            if (this.mTmpValues.type == java.lang.Float.class || this.mTmpValues.type == java.lang.Float.TYPE) {
                if (propertyIndex < 0) {
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        return;
                    } else {
                        throw new java.lang.IllegalArgumentException("Property: " + this.mTmpValues.propertyName + " is not supported for FullPath");
                    }
                } else {
                    nCreatePathPropertyHolder = android.graphics.drawable.AnimatedVectorDrawable.nCreatePathPropertyHolder(nativePtr, propertyIndex, ((java.lang.Float) this.mTmpValues.startValue).floatValue(), ((java.lang.Float) this.mTmpValues.endValue).floatValue());
                    if (this.mTmpValues.dataSource != null) {
                        float[] createFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                        android.graphics.drawable.AnimatedVectorDrawable.nSetPropertyHolderData(nCreatePathPropertyHolder, createFloatDataPoints, createFloatDataPoints.length);
                    }
                }
            } else if (this.mTmpValues.type == java.lang.Integer.class || this.mTmpValues.type == java.lang.Integer.TYPE) {
                nCreatePathPropertyHolder = android.graphics.drawable.AnimatedVectorDrawable.nCreatePathColorPropertyHolder(nativePtr, propertyIndex, ((java.lang.Integer) this.mTmpValues.startValue).intValue(), ((java.lang.Integer) this.mTmpValues.endValue).intValue());
                if (this.mTmpValues.dataSource != null) {
                    int[] createIntDataPoints = createIntDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                    android.graphics.drawable.AnimatedVectorDrawable.nSetPropertyHolderData(nCreatePathPropertyHolder, createIntDataPoints, createIntDataPoints.length);
                }
            } else if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                return;
            } else {
                throw new java.lang.UnsupportedOperationException("Unsupported type: " + this.mTmpValues.type + ". Only float, int or PathData value is supported for Paths.");
            }
            createNativeChildAnimator(nCreatePathPropertyHolder, j, objectAnimator);
        }

        private void createRTAnimatorForRootGroup(android.animation.PropertyValuesHolder[] propertyValuesHolderArr, android.animation.ObjectAnimator objectAnimator, android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState, long j) {
            java.lang.Float f;
            java.lang.Float f2;
            long nativeRenderer = vectorDrawableState.getNativeRenderer();
            if (!objectAnimator.getPropertyName().equals("alpha")) {
                if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    return;
                } else {
                    throw new java.lang.UnsupportedOperationException("Only alpha is supported for root group");
                }
            }
            int i = 0;
            while (true) {
                if (i >= propertyValuesHolderArr.length) {
                    f = null;
                    f2 = null;
                    break;
                }
                propertyValuesHolderArr[i].getPropertyValues(this.mTmpValues);
                if (!this.mTmpValues.propertyName.equals("alpha")) {
                    i++;
                } else {
                    f = (java.lang.Float) this.mTmpValues.startValue;
                    f2 = (java.lang.Float) this.mTmpValues.endValue;
                    break;
                }
            }
            if (f == null && f2 == null) {
                if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    return;
                } else {
                    throw new java.lang.UnsupportedOperationException("No alpha values are specified");
                }
            }
            long nCreateRootAlphaPropertyHolder = android.graphics.drawable.AnimatedVectorDrawable.nCreateRootAlphaPropertyHolder(nativeRenderer, f.floatValue(), f2.floatValue());
            if (this.mTmpValues.dataSource != null) {
                float[] createFloatDataPoints = createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                android.graphics.drawable.AnimatedVectorDrawable.nSetPropertyHolderData(nCreateRootAlphaPropertyHolder, createFloatDataPoints, createFloatDataPoints.length);
            }
            createNativeChildAnimator(nCreateRootAlphaPropertyHolder, j, objectAnimator);
        }

        private static int getFrameCount(long j) {
            int max = java.lang.Math.max(2, (int) java.lang.Math.ceil(j / ((int) (android.view.Choreographer.getInstance().getFrameIntervalNanos() / 1000000))));
            if (max > 300) {
                android.util.Log.w(android.graphics.drawable.AnimatedVectorDrawable.LOGTAG, "Duration for the animation is too long :" + j + ", the animation will subsample the keyframe or path data.");
                return 300;
            }
            return max;
        }

        private static float[] createFloatDataPoints(android.animation.PropertyValuesHolder.PropertyValues.DataSource dataSource, long j) {
            int frameCount = getFrameCount(j);
            float[] fArr = new float[frameCount];
            float f = frameCount - 1;
            for (int i = 0; i < frameCount; i++) {
                fArr[i] = ((java.lang.Float) dataSource.getValueAtFraction(i / f)).floatValue();
            }
            return fArr;
        }

        private static int[] createIntDataPoints(android.animation.PropertyValuesHolder.PropertyValues.DataSource dataSource, long j) {
            int frameCount = getFrameCount(j);
            int[] iArr = new int[frameCount];
            float f = frameCount - 1;
            for (int i = 0; i < frameCount; i++) {
                iArr[i] = ((java.lang.Integer) dataSource.getValueAtFraction(i / f)).intValue();
            }
            return iArr;
        }

        private void createNativeChildAnimator(long j, long j2, android.animation.ObjectAnimator objectAnimator) {
            long duration = objectAnimator.getDuration();
            int repeatCount = objectAnimator.getRepeatCount();
            long startDelay = j2 + objectAnimator.getStartDelay();
            long createNativeInterpolator = android.graphics.animation.NativeInterpolatorFactory.createNativeInterpolator(objectAnimator.getInterpolator(), duration);
            long durationScale = (long) (startDelay * android.animation.ValueAnimator.getDurationScale());
            long durationScale2 = (long) (duration * android.animation.ValueAnimator.getDurationScale());
            this.mStartDelays.add(durationScale);
            android.graphics.drawable.AnimatedVectorDrawable.nAddAnimator(this.mSetPtr, j, createNativeInterpolator, durationScale, durationScale2, repeatCount, objectAnimator.getRepeatMode());
        }

        protected void recordLastSeenTarget(android.graphics.RecordingCanvas recordingCanvas) {
            android.graphics.RenderNode renderNode = recordingCanvas.mNode;
            this.mLastSeenTarget = new java.lang.ref.WeakReference<>(renderNode);
            if ((this.mInitialized || this.mPendingAnimationActions.size() > 0) && useTarget(renderNode)) {
                for (int i = 0; i < this.mPendingAnimationActions.size(); i++) {
                    handlePendingAction(this.mPendingAnimationActions.get(i));
                }
                this.mPendingAnimationActions.clear();
            }
        }

        private void handlePendingAction(int i) {
            if (i == 1) {
                startAnimation();
                return;
            }
            if (i == 2) {
                reverseAnimation();
            } else if (i == 3) {
                resetAnimation();
            } else {
                if (i == 4) {
                    endAnimation();
                    return;
                }
                throw new java.lang.UnsupportedOperationException("Animation action " + i + "is not supported");
            }
        }

        private boolean useLastSeenTarget() {
            if (this.mLastSeenTarget != null) {
                return useTarget(this.mLastSeenTarget.get());
            }
            return false;
        }

        private boolean useTarget(android.graphics.RenderNode renderNode) {
            if (renderNode != null && renderNode.isAttached()) {
                renderNode.registerVectorDrawableAnimator(this);
                return true;
            }
            return false;
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        private void addPendingAction(int i) {
            invalidateOwningView();
            this.mPendingAnimationActions.add(i);
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void start() {
            if (!this.mInitialized) {
                return;
            }
            if (useLastSeenTarget()) {
                startAnimation();
            } else {
                addPendingAction(1);
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void end() {
            if (!this.mInitialized) {
                return;
            }
            if (useLastSeenTarget()) {
                endAnimation();
            } else {
                addPendingAction(4);
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reset() {
            if (!this.mInitialized) {
                return;
            }
            if (useLastSeenTarget()) {
                resetAnimation();
            } else {
                addPendingAction(3);
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void reverse() {
            if (!this.mIsReversible || !this.mInitialized) {
                return;
            }
            if (useLastSeenTarget()) {
                reverseAnimation();
            } else {
                addPendingAction(2);
            }
        }

        private void startAnimation() {
            this.mStarted = true;
            if (this.mHandler == null) {
                this.mHandler = new android.os.Handler();
            }
            long j = this.mSetPtr;
            int i = this.mLastListenerId + 1;
            this.mLastListenerId = i;
            android.graphics.drawable.AnimatedVectorDrawable.nStart(j, this, i);
            invalidateOwningView();
            if (this.mListener != null) {
                this.mListener.onAnimationStart(null);
            }
        }

        private void endAnimation() {
            android.graphics.drawable.AnimatedVectorDrawable.nEnd(this.mSetPtr);
            invalidateOwningView();
        }

        private void resetAnimation() {
            android.graphics.drawable.AnimatedVectorDrawable.nReset(this.mSetPtr);
            invalidateOwningView();
        }

        private void reverseAnimation() {
            this.mStarted = true;
            long j = this.mSetPtr;
            int i = this.mLastListenerId + 1;
            this.mLastListenerId = i;
            android.graphics.drawable.AnimatedVectorDrawable.nReverse(j, this, i);
            invalidateOwningView();
            if (this.mListener != null) {
                this.mListener.onAnimationStart(null);
            }
        }

        @Override // android.view.NativeVectorDrawableAnimator
        public long getAnimatorNativePtr() {
            return this.mSetPtr;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean canReverse() {
            return this.mIsReversible;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isStarted() {
            return this.mStarted;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isRunning() {
            if (!this.mInitialized) {
                return false;
            }
            return this.mStarted;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void setListener(android.animation.Animator.AnimatorListener animatorListener) {
            this.mListener = animatorListener;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void removeListener(android.animation.Animator.AnimatorListener animatorListener) {
            this.mListener = null;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void onDraw(android.graphics.Canvas canvas) {
            if (canvas.isHardwareAccelerated()) {
                recordLastSeenTarget((android.graphics.RecordingCanvas) canvas);
            }
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void pause() {
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public void resume() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAnimationEnd(int i) {
            if (i != this.mLastListenerId) {
                return;
            }
            this.mStarted = false;
            invalidateOwningView();
            if (this.mListener != null) {
                this.mListener.onAnimationEnd(null);
            }
        }

        private static void callOnFinished(final android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT vectorDrawableAnimatorRT, final int i) {
            vectorDrawableAnimatorRT.mHandler.post(new java.lang.Runnable() { // from class: android.graphics.drawable.AnimatedVectorDrawable$VectorDrawableAnimatorRT$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimatorRT.this.onAnimationEnd(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void transferPendingActions(android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator vectorDrawableAnimator) {
            for (int i = 0; i < this.mPendingAnimationActions.size(); i++) {
                int i2 = this.mPendingAnimationActions.get(i);
                if (i2 == 1) {
                    vectorDrawableAnimator.start();
                } else if (i2 == 4) {
                    vectorDrawableAnimator.end();
                } else if (i2 == 2) {
                    vectorDrawableAnimator.reverse();
                } else if (i2 == 3) {
                    vectorDrawableAnimator.reset();
                } else {
                    throw new java.lang.UnsupportedOperationException("Animation action " + i2 + "is not supported");
                }
            }
            this.mPendingAnimationActions.clear();
        }

        @Override // android.graphics.drawable.AnimatedVectorDrawable.VectorDrawableAnimator
        public long getTotalDuration() {
            return this.mTotalDuration;
        }
    }
}
