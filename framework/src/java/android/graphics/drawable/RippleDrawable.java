package android.graphics.drawable;

/* loaded from: classes.dex */
public class RippleDrawable extends android.graphics.drawable.LayerDrawable {
    private static final int BACKGROUND_OPACITY_DURATION = 80;
    private static final int DEFAULT_EFFECT_COLOR = -1912602625;
    private static final boolean FORCE_PATTERNED_STYLE = true;
    private static final android.view.animation.LinearInterpolator LINEAR_INTERPOLATOR = new android.view.animation.LinearInterpolator();
    private static final int MASK_CONTENT = 1;
    private static final int MASK_EXPLICIT = 2;
    private static final int MASK_NONE = 0;
    private static final int MASK_UNKNOWN = -1;
    private static final int MAX_RIPPLES = 10;
    public static final int RADIUS_AUTO = -1;
    public static final int STYLE_PATTERNED = 1;
    public static final int STYLE_SOLID = 0;
    private static final java.lang.String TAG = "RippleDrawable";
    private boolean mAddRipple;
    private android.graphics.drawable.RippleBackground mBackground;
    private android.animation.ValueAnimator mBackgroundAnimation;
    private float mBackgroundOpacity;
    private int mDensity;
    private final android.graphics.Rect mDirtyBounds;
    private final android.graphics.Rect mDrawingBounds;
    private boolean mExitingAnimation;
    private android.graphics.drawable.RippleForeground[] mExitingRipples;
    private int mExitingRipplesCount;
    private android.graphics.PorterDuffColorFilter mFocusColorFilter;
    private boolean mForceSoftware;
    private boolean mHasPending;
    private boolean mHasValidMask;
    private final android.graphics.Rect mHotspotBounds;
    private android.graphics.drawable.Drawable mMask;
    private android.graphics.Bitmap mMaskBuffer;
    private android.graphics.Canvas mMaskCanvas;
    private android.graphics.PorterDuffColorFilter mMaskColorFilter;
    private android.graphics.Matrix mMaskMatrix;
    private android.graphics.BitmapShader mMaskShader;
    private boolean mOverrideBounds;
    private float mPendingX;
    private float mPendingY;
    private android.graphics.drawable.RippleForeground mRipple;
    private boolean mRippleActive;
    private android.graphics.Paint mRipplePaint;
    private boolean mRunBackgroundAnimation;
    private java.util.ArrayList<android.graphics.drawable.RippleAnimationSession> mRunningAnimations;
    private android.graphics.drawable.RippleDrawable.RippleState mState;
    private float mTargetBackgroundOpacity;
    private final android.graphics.Rect mTempRect;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RippleStyle {
    }

    RippleDrawable() {
        this(new android.graphics.drawable.RippleDrawable.RippleState(null, null, null), null);
    }

    public RippleDrawable(android.content.res.ColorStateList colorStateList, android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        this(new android.graphics.drawable.RippleDrawable.RippleState(null, null, null), null);
        if (colorStateList == null) {
            throw new java.lang.IllegalArgumentException("RippleDrawable requires a non-null color");
        }
        if (drawable != null) {
            addLayer(drawable, null, 0, 0, 0, 0, 0);
        }
        if (drawable2 != null) {
            addLayer(drawable2, null, 16908334, 0, 0, 0, 0);
        }
        setColor(colorStateList);
        ensurePadding();
        refreshPadding();
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        if (this.mRipple != null) {
            this.mRipple.end();
        }
        if (this.mBackground != null) {
            this.mBackground.jumpToFinal();
        }
        cancelExitingRipples();
        endPatternedAnimations();
    }

    private void endPatternedAnimations() {
        for (int i = 0; i < this.mRunningAnimations.size(); i++) {
            this.mRunningAnimations.get(i).end();
        }
        this.mRunningAnimations.clear();
    }

    private void cancelExitingRipples() {
        int i = this.mExitingRipplesCount;
        android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].end();
        }
        if (rippleForegroundArr != null) {
            java.util.Arrays.fill(rippleForegroundArr, 0, i, (java.lang.Object) null);
        }
        this.mExitingRipplesCount = 0;
        invalidateSelf(false);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        for (int i : iArr) {
            if (i == 16842910) {
                z2 = true;
            } else if (i == 16842908) {
                z4 = true;
            } else if (i == 16842919) {
                z3 = true;
            } else if (i == 16843623) {
                z5 = true;
            } else if (i == 16842909) {
                z6 = true;
            }
        }
        if (z2 && z3) {
            z = true;
        }
        setRippleActive(z);
        setBackgroundActive(z5, z4, z3, z6);
        return onStateChange;
    }

    private void setRippleActive(boolean z) {
        if (this.mRippleActive != z) {
            this.mRippleActive = z;
            if (this.mState.mRippleStyle == 0) {
                if (z) {
                    tryRippleEnter();
                    return;
                } else {
                    tryRippleExit();
                    return;
                }
            }
            if (z) {
                startPatternedAnimation();
            } else {
                exitPatternedAnimation();
            }
        }
    }

    public void setBackgroundActive(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mState.mRippleStyle == 0) {
            if (this.mBackground == null && (z || z2)) {
                this.mBackground = new android.graphics.drawable.RippleBackground(this, this.mHotspotBounds, isBounded());
                this.mBackground.setup(this.mState.mMaxRadius, this.mDensity);
            }
            if (this.mBackground != null) {
                this.mBackground.setState(z2, z, z3);
                return;
            }
            return;
        }
        if (z2 || z) {
            if (!z3) {
                enterPatternedBackgroundAnimation(z2, z, z4);
                return;
            }
            return;
        }
        exitPatternedBackgroundAnimation();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        if (!this.mOverrideBounds) {
            this.mHotspotBounds.set(rect);
            onHotspotBoundsChanged();
        }
        int i = this.mExitingRipplesCount;
        android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].onBoundsChange();
        }
        if (this.mBackground != null) {
            this.mBackground.onBoundsChange();
        }
        if (this.mRipple != null) {
            this.mRipple.onBoundsChange();
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!z) {
            clearHotspots();
        } else if (visible) {
            if (this.mRippleActive) {
                if (this.mState.mRippleStyle == 0) {
                    tryRippleEnter();
                } else {
                    invalidateSelf();
                }
            }
            jumpToCurrentState();
        }
        return visible;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isProjected() {
        if (isBounded()) {
            return false;
        }
        int i = this.mState.mMaxRadius;
        android.graphics.Rect bounds = getBounds();
        android.graphics.Rect rect = this.mHotspotBounds;
        if (i == -1 || i > rect.width() / 2 || i > rect.height() / 2) {
            return true;
        }
        return (bounds.equals(rect) || bounds.contains(rect)) ? false : true;
    }

    private boolean isBounded() {
        return getNumberOfLayers() > 0;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    public void setColor(android.content.res.ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new java.lang.IllegalArgumentException("color cannot be null");
        }
        this.mState.mColor = colorStateList;
        invalidateSelf(false);
    }

    public void setEffectColor(android.content.res.ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new java.lang.IllegalArgumentException("color cannot be null");
        }
        this.mState.mEffectColor = colorStateList;
        invalidateSelf(false);
    }

    public android.content.res.ColorStateList getEffectColor() {
        return this.mState.mEffectColor;
    }

    public void setRadius(int i) {
        this.mState.mMaxRadius = i;
        invalidateSelf(false);
    }

    public int getRadius() {
        return this.mState.mMaxRadius;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.RippleDrawable);
        setPaddingMode(1);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes);
        verifyRequiredAttributes(obtainAttributes);
        obtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable
    public boolean setDrawableByLayerId(int i, android.graphics.drawable.Drawable drawable) {
        if (!super.setDrawableByLayerId(i, drawable)) {
            return false;
        }
        if (i == 16908334) {
            this.mMask = drawable;
            this.mHasValidMask = false;
            return true;
        }
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable
    public void setPaddingMode(int i) {
        super.setPaddingMode(i);
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        android.graphics.drawable.RippleDrawable.RippleState rippleState = this.mState;
        rippleState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        rippleState.mTouchThemeAttrs = typedArray.extractThemeAttrs();
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(0);
        if (colorStateList != null) {
            this.mState.mColor = colorStateList;
        }
        android.content.res.ColorStateList colorStateList2 = typedArray.getColorStateList(2);
        if (colorStateList2 != null) {
            this.mState.mEffectColor = colorStateList2;
        }
        this.mState.mMaxRadius = typedArray.getDimensionPixelSize(1, this.mState.mMaxRadius);
    }

    private void verifyRequiredAttributes(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        if (this.mState.mColor == null) {
            if (this.mState.mTouchThemeAttrs == null || this.mState.mTouchThemeAttrs[0] == 0) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <ripple> requires a valid color attribute");
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.RippleDrawable.RippleState rippleState = this.mState;
        if (rippleState == null) {
            return;
        }
        if (rippleState.mTouchThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(rippleState.mTouchThemeAttrs, com.android.internal.R.styleable.RippleDrawable);
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
        if (rippleState.mColor != null && rippleState.mColor.canApplyTheme()) {
            rippleState.mColor = rippleState.mColor.obtainForTheme(theme);
        }
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return (this.mState != null && this.mState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.mPendingX = f;
        this.mPendingY = f2;
        if (this.mRipple == null || this.mBackground == null) {
            this.mHasPending = true;
        }
        if (this.mRipple != null) {
            this.mRipple.move(f, f2);
        }
    }

    private void tryRippleEnter() {
        float exactCenterX;
        float exactCenterY;
        if (this.mExitingRipplesCount >= 10) {
            return;
        }
        if (this.mRipple == null) {
            if (this.mHasPending) {
                this.mHasPending = false;
                exactCenterX = this.mPendingX;
                exactCenterY = this.mPendingY;
            } else {
                exactCenterX = this.mHotspotBounds.exactCenterX();
                exactCenterY = this.mHotspotBounds.exactCenterY();
            }
            this.mRipple = new android.graphics.drawable.RippleForeground(this, this.mHotspotBounds, exactCenterX, exactCenterY, this.mForceSoftware);
        }
        this.mRipple.setup(this.mState.mMaxRadius, this.mDensity);
        this.mRipple.enter();
    }

    private void tryRippleExit() {
        if (this.mRipple != null) {
            if (this.mExitingRipples == null) {
                this.mExitingRipples = new android.graphics.drawable.RippleForeground[10];
            }
            android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
            int i = this.mExitingRipplesCount;
            this.mExitingRipplesCount = i + 1;
            rippleForegroundArr[i] = this.mRipple;
            this.mRipple.exit();
            this.mRipple = null;
        }
    }

    private void clearHotspots() {
        if (this.mRipple != null) {
            this.mRipple.end();
            this.mRipple = null;
            this.mRippleActive = false;
        }
        if (this.mBackground != null) {
            this.mBackground.setState(false, false, false);
        }
        cancelExitingRipples();
        endPatternedAnimations();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.mOverrideBounds = true;
        this.mHotspotBounds.set(i, i2, i3, i4);
        onHotspotBoundsChanged();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void getHotspotBounds(android.graphics.Rect rect) {
        rect.set(this.mHotspotBounds);
    }

    private void onHotspotBoundsChanged() {
        int i = this.mExitingRipplesCount;
        android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].onHotspotBoundsChanged();
        }
        if (this.mRipple != null) {
            this.mRipple.onHotspotBoundsChanged();
        }
        if (this.mBackground != null) {
            this.mBackground.onHotspotBoundsChanged();
        }
        float computedRadius = getComputedRadius();
        for (int i3 = 0; i3 < this.mRunningAnimations.size(); i3++) {
            android.graphics.drawable.RippleAnimationSession rippleAnimationSession = this.mRunningAnimations.get(i3);
            rippleAnimationSession.setRadius(computedRadius);
            rippleAnimationSession.getProperties().getShader().setResolution(this.mHotspotBounds.width(), this.mHotspotBounds.height());
            float centerX = this.mHotspotBounds.centerX();
            float centerY = this.mHotspotBounds.centerY();
            rippleAnimationSession.getProperties().getShader().setOrigin(centerX, centerY);
            rippleAnimationSession.getProperties().setOrigin(java.lang.Float.valueOf(centerX), java.lang.Float.valueOf(centerY));
            if (!rippleAnimationSession.isForceSoftware()) {
                rippleAnimationSession.getCanvasProperties().setOrigin(android.graphics.CanvasProperty.createFloat(centerX), android.graphics.CanvasProperty.createFloat(centerY));
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
        int i = layerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mId != 16908334) {
                childDrawableArr[i2].mDrawable.getOutline(outline);
                if (!outline.isEmpty()) {
                    return;
                }
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (this.mState.mRippleStyle == 0) {
            drawSolid(canvas);
        } else {
            drawPatterned(canvas);
        }
    }

    private void drawSolid(android.graphics.Canvas canvas) {
        pruneRipples();
        android.graphics.Rect dirtyBounds = getDirtyBounds();
        int save = canvas.save(2);
        if (isBounded()) {
            canvas.clipRect(dirtyBounds);
        }
        drawContent(canvas);
        drawBackgroundAndRipples(canvas);
        canvas.restoreToCount(save);
    }

    private void exitPatternedBackgroundAnimation() {
        this.mTargetBackgroundOpacity = 0.0f;
        if (this.mBackgroundAnimation != null) {
            this.mBackgroundAnimation.cancel();
        }
        this.mRunBackgroundAnimation = true;
        invalidateSelf(false);
    }

    private void startPatternedAnimation() {
        this.mAddRipple = true;
        invalidateSelf(false);
    }

    private void exitPatternedAnimation() {
        this.mExitingAnimation = true;
        invalidateSelf(false);
    }

    public float getTargetBackgroundOpacity() {
        return this.mTargetBackgroundOpacity;
    }

    private void enterPatternedBackgroundAnimation(boolean z, boolean z2, boolean z3) {
        float f = 0.0f;
        this.mBackgroundOpacity = 0.0f;
        if (z) {
            this.mTargetBackgroundOpacity = z3 ? 0.6f : 0.2f;
        } else {
            if (z2) {
                f = 0.2f;
            }
            this.mTargetBackgroundOpacity = f;
        }
        if (this.mBackgroundAnimation != null) {
            this.mBackgroundAnimation.cancel();
        }
        this.mRunBackgroundAnimation = true;
        invalidateSelf(false);
    }

    private void startBackgroundAnimation() {
        this.mRunBackgroundAnimation = false;
        if (android.os.Looper.myLooper() == null) {
            android.util.Log.w(TAG, "Thread doesn't have a looper. Skipping animation.");
            return;
        }
        this.mBackgroundAnimation = android.animation.ValueAnimator.ofFloat(this.mBackgroundOpacity, this.mTargetBackgroundOpacity);
        this.mBackgroundAnimation.setInterpolator(LINEAR_INTERPOLATOR);
        this.mBackgroundAnimation.setDuration(80L);
        this.mBackgroundAnimation.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.graphics.drawable.RippleDrawable.this.lambda$startBackgroundAnimation$0(valueAnimator);
            }
        });
        this.mBackgroundAnimation.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startBackgroundAnimation$0(android.animation.ValueAnimator valueAnimator) {
        this.mBackgroundOpacity = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf(false);
    }

    private void drawPatterned(android.graphics.Canvas canvas) {
        float exactCenterX;
        float exactCenterY;
        android.graphics.Rect rect = this.mHotspotBounds;
        int save = canvas.save(2);
        boolean z = !this.mForceSoftware;
        if (isBounded()) {
            canvas.clipRect(getDirtyBounds());
        }
        boolean z2 = this.mAddRipple;
        float centerX = rect.centerX();
        float centerY = rect.centerY();
        boolean z3 = this.mExitingAnimation;
        int i = 0;
        this.mExitingAnimation = false;
        this.mAddRipple = false;
        if (this.mRunningAnimations.size() > 0 && !z2) {
            updateRipplePaint();
        }
        drawContent(canvas);
        drawPatternedBackground(canvas, centerX, centerY);
        if (z2 && this.mRunningAnimations.size() <= 10) {
            if (this.mHasPending) {
                float f = this.mPendingX;
                float f2 = this.mPendingY;
                this.mHasPending = false;
                exactCenterX = f;
                exactCenterY = f2;
            } else {
                exactCenterX = rect.exactCenterX();
                exactCenterY = rect.exactCenterY();
            }
            this.mRunningAnimations.add(new android.graphics.drawable.RippleAnimationSession(createAnimationProperties(exactCenterX, exactCenterY, centerX, centerY, rect.width(), rect.height()), !z).setOnAnimationUpdated(new java.lang.Runnable() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.graphics.drawable.RippleDrawable.this.lambda$drawPatterned$1();
                }
            }).setOnSessionEnd(new java.util.function.Consumer() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.graphics.drawable.RippleDrawable.this.lambda$drawPatterned$2((android.graphics.drawable.RippleAnimationSession) obj);
                }
            }).setForceSoftwareAnimation(!z).enter(canvas));
        }
        if (z3) {
            for (int i2 = 0; i2 < this.mRunningAnimations.size(); i2++) {
                this.mRunningAnimations.get(i2).exit(canvas);
            }
        }
        while (true) {
            if (i >= this.mRunningAnimations.size()) {
                break;
            }
            android.graphics.drawable.RippleAnimationSession rippleAnimationSession = this.mRunningAnimations.get(i);
            if (!canvas.isHardwareAccelerated()) {
                android.util.Log.e(TAG, "The RippleDrawable.STYLE_PATTERNED animation is not supported for a non-hardware accelerated Canvas. Skipping animation.");
                break;
            }
            if (z) {
                android.graphics.drawable.RippleAnimationSession.AnimationProperties<android.graphics.CanvasProperty<java.lang.Float>, android.graphics.CanvasProperty<android.graphics.Paint>> canvasProperties = rippleAnimationSession.getCanvasProperties();
                ((android.graphics.RecordingCanvas) canvas).drawRipple(canvasProperties.getX(), canvasProperties.getY(), canvasProperties.getMaxRadius(), canvasProperties.getPaint(), canvasProperties.getProgress(), canvasProperties.getNoisePhase(), canvasProperties.getColor(), canvasProperties.getShader());
            } else {
                android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> properties = rippleAnimationSession.getProperties();
                canvas.drawCircle(properties.getX().floatValue(), properties.getY().floatValue(), properties.getMaxRadius().floatValue(), properties.getPaint());
            }
            i++;
        }
        canvas.restoreToCount(save);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawPatterned$1() {
        invalidateSelf(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawPatterned$2(android.graphics.drawable.RippleAnimationSession rippleAnimationSession) {
        this.mRunningAnimations.remove(rippleAnimationSession);
    }

    private void drawPatternedBackground(android.graphics.Canvas canvas, float f, float f2) {
        if (this.mRunBackgroundAnimation) {
            startBackgroundAnimation();
        }
        if (this.mBackgroundOpacity == 0.0f) {
            return;
        }
        android.graphics.Paint updateRipplePaint = updateRipplePaint();
        float f3 = this.mBackgroundOpacity;
        int alpha = updateRipplePaint.getAlpha();
        int min = java.lang.Math.min((int) ((alpha * f3) + 0.5f), 255);
        if (min > 0) {
            android.graphics.ColorFilter colorFilter = updateRipplePaint.getColorFilter();
            updateRipplePaint.setColorFilter(this.mFocusColorFilter);
            updateRipplePaint.setAlpha(min);
            canvas.drawCircle(f, f2, getComputedRadius(), updateRipplePaint);
            updateRipplePaint.setAlpha(alpha);
            updateRipplePaint.setColorFilter(colorFilter);
        }
    }

    private float computeRadius() {
        float width = this.mHotspotBounds.width() / 2.0f;
        float height = this.mHotspotBounds.height() / 2.0f;
        return (float) java.lang.Math.sqrt((width * width) + (height * height));
    }

    private int getComputedRadius() {
        return this.mState.mMaxRadius >= 0 ? this.mState.mMaxRadius : (int) computeRadius();
    }

    private android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> createAnimationProperties(float f, float f2, float f3, float f4, float f5, float f6) {
        int color;
        android.graphics.Paint paint = new android.graphics.Paint(updateRipplePaint());
        float computedRadius = getComputedRadius();
        android.graphics.drawable.RippleShader rippleShader = new android.graphics.drawable.RippleShader();
        if (this.mMaskColorFilter == null) {
            color = this.mState.mColor.getColorForState(getState(), -16777216);
        } else {
            color = this.mMaskColorFilter.getColor();
        }
        int colorForState = this.mState.mEffectColor.getColorForState(getState(), android.graphics.Color.MAGENTA);
        float currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        rippleShader.setColor(color, colorForState);
        rippleShader.setOrigin(f3, f4);
        rippleShader.setTouch(f, f2);
        rippleShader.setResolution(f5, f6);
        rippleShader.setNoisePhase(currentAnimationTimeMillis);
        rippleShader.setRadius(computedRadius);
        rippleShader.setProgress(0.0f);
        android.graphics.drawable.RippleAnimationSession.AnimationProperties<java.lang.Float, android.graphics.Paint> animationProperties = new android.graphics.drawable.RippleAnimationSession.AnimationProperties<>(java.lang.Float.valueOf(f3), java.lang.Float.valueOf(f4), java.lang.Float.valueOf(computedRadius), java.lang.Float.valueOf(currentAnimationTimeMillis), paint, java.lang.Float.valueOf(0.0f), color, rippleShader);
        if (this.mMaskShader == null) {
            rippleShader.setShader(null);
        } else {
            rippleShader.setShader(this.mMaskShader);
        }
        paint.setShader(rippleShader);
        paint.setColorFilter(null);
        paint.setColor(-16777216);
        return animationProperties;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        invalidateSelf(true);
    }

    void invalidateSelf(boolean z) {
        super.invalidateSelf();
        if (z) {
            this.mHasValidMask = false;
        }
    }

    private void pruneRipples() {
        android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        int i = this.mExitingRipplesCount;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (!rippleForegroundArr[i3].hasFinishedExit()) {
                rippleForegroundArr[i2] = rippleForegroundArr[i3];
                i2++;
            }
        }
        for (int i4 = i2; i4 < i; i4++) {
            rippleForegroundArr[i4] = null;
        }
        this.mExitingRipplesCount = i2;
    }

    private void updateMaskShaderIfNeeded() {
        int maskType;
        if (this.mHasValidMask || (maskType = getMaskType()) == -1) {
            return;
        }
        this.mHasValidMask = true;
        android.graphics.Rect bounds = getBounds();
        if (maskType == 0 || bounds.isEmpty()) {
            if (this.mMaskBuffer != null) {
                this.mMaskBuffer.recycle();
                this.mMaskBuffer = null;
                this.mMaskShader = null;
                this.mMaskCanvas = null;
            }
            this.mMaskMatrix = null;
            this.mMaskColorFilter = null;
            return;
        }
        if (this.mMaskBuffer == null || this.mMaskBuffer.getWidth() != bounds.width() || this.mMaskBuffer.getHeight() != bounds.height()) {
            if (this.mMaskBuffer != null) {
                this.mMaskBuffer.recycle();
            }
            this.mMaskBuffer = android.graphics.Bitmap.createBitmap(bounds.width(), bounds.height(), android.graphics.Bitmap.Config.ALPHA_8);
            this.mMaskShader = new android.graphics.BitmapShader(this.mMaskBuffer, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP);
            this.mMaskCanvas = new android.graphics.Canvas(this.mMaskBuffer);
        } else {
            this.mMaskBuffer.eraseColor(0);
        }
        if (this.mMaskMatrix == null) {
            this.mMaskMatrix = new android.graphics.Matrix();
        } else {
            this.mMaskMatrix.reset();
        }
        if (this.mMaskColorFilter == null) {
            this.mMaskColorFilter = new android.graphics.PorterDuffColorFilter(0, android.graphics.PorterDuff.Mode.SRC_IN);
            this.mFocusColorFilter = new android.graphics.PorterDuffColorFilter(0, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        int save = this.mMaskCanvas.save();
        this.mMaskCanvas.translate(-bounds.left, -bounds.top);
        if (maskType == 2) {
            drawMask(this.mMaskCanvas);
        } else if (maskType == 1) {
            drawContent(this.mMaskCanvas);
        }
        this.mMaskCanvas.restoreToCount(save);
    }

    private int getMaskType() {
        if (this.mRipple == null && this.mExitingRipplesCount <= 0 && ((this.mBackground == null || !this.mBackground.isVisible()) && this.mState.mRippleStyle == 0)) {
            return -1;
        }
        if (this.mMask != null) {
            return this.mMask.getOpacity() == -1 ? 0 : 2;
        }
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mDrawable.getOpacity() != -1) {
                return 1;
            }
        }
        return 0;
    }

    private void drawContent(android.graphics.Canvas canvas) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mId != 16908334) {
                childDrawableArr[i2].mDrawable.draw(canvas);
            }
        }
    }

    private void drawBackgroundAndRipples(android.graphics.Canvas canvas) {
        android.graphics.drawable.RippleForeground rippleForeground = this.mRipple;
        android.graphics.drawable.RippleBackground rippleBackground = this.mBackground;
        int i = this.mExitingRipplesCount;
        if (rippleForeground == null && i <= 0 && (rippleBackground == null || !rippleBackground.isVisible())) {
            return;
        }
        float exactCenterX = this.mHotspotBounds.exactCenterX();
        float exactCenterY = this.mHotspotBounds.exactCenterY();
        canvas.translate(exactCenterX, exactCenterY);
        android.graphics.Paint updateRipplePaint = updateRipplePaint();
        if (rippleBackground != null && rippleBackground.isVisible()) {
            rippleBackground.draw(canvas, updateRipplePaint);
        }
        if (i > 0) {
            android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
            for (int i2 = 0; i2 < i; i2++) {
                rippleForegroundArr[i2].draw(canvas, updateRipplePaint);
            }
        }
        if (rippleForeground != null) {
            rippleForeground.draw(canvas, updateRipplePaint);
        }
        canvas.translate(-exactCenterX, -exactCenterY);
    }

    private void drawMask(android.graphics.Canvas canvas) {
        this.mMask.draw(canvas);
    }

    android.graphics.Paint updateRipplePaint() {
        if (this.mRipplePaint == null) {
            this.mRipplePaint = new android.graphics.Paint();
            this.mRipplePaint.setAntiAlias(true);
            this.mRipplePaint.setStyle(android.graphics.Paint.Style.FILL);
        }
        float exactCenterX = this.mHotspotBounds.exactCenterX();
        float exactCenterY = this.mHotspotBounds.exactCenterY();
        updateMaskShaderIfNeeded();
        if (this.mMaskShader != null) {
            android.graphics.Rect bounds = getBounds();
            if (this.mState.mRippleStyle == 1) {
                this.mMaskMatrix.setTranslate(bounds.left, bounds.top);
            } else {
                this.mMaskMatrix.setTranslate(bounds.left - exactCenterX, bounds.top - exactCenterY);
            }
            this.mMaskShader.setLocalMatrix(this.mMaskMatrix);
            if (this.mState.mRippleStyle == 1) {
                for (int i = 0; i < this.mRunningAnimations.size(); i++) {
                    this.mRunningAnimations.get(i).getProperties().getShader().setShader(this.mMaskShader);
                }
            }
        }
        int colorForState = this.mState.mColor.getColorForState(getState(), -16777216);
        android.graphics.Paint paint = this.mRipplePaint;
        if (this.mMaskColorFilter != null) {
            int i2 = this.mState.mRippleStyle == 1 ? colorForState : colorForState | (-16777216);
            if (this.mMaskColorFilter.getColor() != i2) {
                this.mMaskColorFilter = new android.graphics.PorterDuffColorFilter(i2, this.mMaskColorFilter.getMode());
                this.mFocusColorFilter = new android.graphics.PorterDuffColorFilter(colorForState | (-16777216), this.mFocusColorFilter.getMode());
            }
            paint.setColor(colorForState & (-16777216));
            paint.setColorFilter(this.mMaskColorFilter);
            paint.setShader(this.mMaskShader);
        } else {
            paint.setColor(colorForState);
            paint.setColorFilter(null);
            paint.setShader(null);
        }
        return paint;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Rect getDirtyBounds() {
        if (!isBounded()) {
            android.graphics.Rect rect = this.mDrawingBounds;
            android.graphics.Rect rect2 = this.mDirtyBounds;
            rect2.set(rect);
            rect.setEmpty();
            int exactCenterX = (int) this.mHotspotBounds.exactCenterX();
            int exactCenterY = (int) this.mHotspotBounds.exactCenterY();
            android.graphics.Rect rect3 = this.mTempRect;
            android.graphics.drawable.RippleForeground[] rippleForegroundArr = this.mExitingRipples;
            int i = this.mExitingRipplesCount;
            for (int i2 = 0; i2 < i; i2++) {
                rippleForegroundArr[i2].getBounds(rect3);
                rect3.offset(exactCenterX, exactCenterY);
                rect.union(rect3);
            }
            android.graphics.drawable.RippleBackground rippleBackground = this.mBackground;
            if (rippleBackground != null) {
                rippleBackground.getBounds(rect3);
                rect3.offset(exactCenterX, exactCenterY);
                rect.union(rect3);
            }
            rect2.union(rect);
            rect2.union(super.getDirtyBounds());
            return rect2;
        }
        return getBounds();
    }

    public void setForceSoftware(boolean z) {
        this.mForceSoftware = z;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        super.mutate();
        this.mState = (android.graphics.drawable.RippleDrawable.RippleState) this.mLayerState;
        this.mMask = findDrawableByLayerId(16908334);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.LayerDrawable
    public android.graphics.drawable.RippleDrawable.RippleState createConstantState(android.graphics.drawable.LayerDrawable.LayerState layerState, android.content.res.Resources resources) {
        return new android.graphics.drawable.RippleDrawable.RippleState(layerState, this, resources);
    }

    static class RippleState extends android.graphics.drawable.LayerDrawable.LayerState {
        android.content.res.ColorStateList mColor;
        android.content.res.ColorStateList mEffectColor;
        int mMaxRadius;
        int mRippleStyle;
        int[] mTouchThemeAttrs;

        public RippleState(android.graphics.drawable.LayerDrawable.LayerState layerState, android.graphics.drawable.RippleDrawable rippleDrawable, android.content.res.Resources resources) {
            super(layerState, rippleDrawable, resources);
            this.mColor = android.content.res.ColorStateList.valueOf(android.graphics.Color.MAGENTA);
            this.mEffectColor = android.content.res.ColorStateList.valueOf(android.graphics.drawable.RippleDrawable.DEFAULT_EFFECT_COLOR);
            this.mMaxRadius = -1;
            this.mRippleStyle = 1;
            if (layerState != null && (layerState instanceof android.graphics.drawable.RippleDrawable.RippleState)) {
                android.graphics.drawable.RippleDrawable.RippleState rippleState = (android.graphics.drawable.RippleDrawable.RippleState) layerState;
                this.mTouchThemeAttrs = rippleState.mTouchThemeAttrs;
                this.mColor = rippleState.mColor;
                this.mMaxRadius = rippleState.mMaxRadius;
                this.mRippleStyle = rippleState.mRippleStyle;
                this.mEffectColor = rippleState.mEffectColor;
                if (rippleState.mDensity != this.mDensity) {
                    applyDensityScaling(layerState.mDensity, this.mDensity);
                }
            }
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState
        protected void onDensityChanged(int i, int i2) {
            super.onDensityChanged(i, i2);
            applyDensityScaling(i, i2);
        }

        private void applyDensityScaling(int i, int i2) {
            if (this.mMaxRadius != -1) {
                this.mMaxRadius = android.graphics.drawable.Drawable.scaleFromDensity(this.mMaxRadius, i, i2, true);
            }
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mTouchThemeAttrs != null || (this.mColor != null && this.mColor.canApplyTheme()) || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.RippleDrawable(this, (android.content.res.Resources) null);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.RippleDrawable(this, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return super.getChangingConfigurations() | (this.mColor != null ? this.mColor.getChangingConfigurations() : 0);
        }
    }

    private RippleDrawable(android.graphics.drawable.RippleDrawable.RippleState rippleState, android.content.res.Resources resources) {
        this.mTempRect = new android.graphics.Rect();
        this.mHotspotBounds = new android.graphics.Rect();
        this.mDrawingBounds = new android.graphics.Rect();
        this.mDirtyBounds = new android.graphics.Rect();
        this.mExitingRipplesCount = 0;
        this.mAddRipple = false;
        this.mRunningAnimations = new java.util.ArrayList<>();
        this.mState = new android.graphics.drawable.RippleDrawable.RippleState(rippleState, this, resources);
        this.mLayerState = this.mState;
        this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, this.mState.mDensity);
        if (this.mState.mNumChildren > 0) {
            ensurePadding();
            refreshPadding();
        }
        updateLocalState();
    }

    private void updateLocalState() {
        this.mMask = findDrawableByLayerId(16908334);
    }
}
