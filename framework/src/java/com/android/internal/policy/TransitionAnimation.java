package com.android.internal.policy;

/* loaded from: classes5.dex */
public class TransitionAnimation {
    private static final int CLIP_REVEAL_TRANSLATION_Y_DP = 8;
    public static final int DEFAULT_APP_TRANSITION_DURATION = 336;
    private static final java.lang.String DEFAULT_PACKAGE = "android";
    private static final int MAX_CLIP_REVEAL_TRANSITION_DURATION = 420;
    private static final float RECENTS_THUMBNAIL_FADEIN_FRACTION = 0.5f;
    private static final float RECENTS_THUMBNAIL_FADEOUT_FRACTION = 0.5f;
    private static final int THUMBNAIL_APP_TRANSITION_DURATION = 336;
    private static final int THUMBNAIL_TRANSITION_ENTER_SCALE_DOWN = 2;
    private static final int THUMBNAIL_TRANSITION_ENTER_SCALE_UP = 0;
    private static final int THUMBNAIL_TRANSITION_EXIT_SCALE_DOWN = 3;
    private static final int THUMBNAIL_TRANSITION_EXIT_SCALE_UP = 1;
    static final android.view.animation.Interpolator TOUCH_RESPONSE_INTERPOLATOR = new android.view.animation.PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
    public static final int WALLPAPER_TRANSITION_CLOSE = 2;
    public static final int WALLPAPER_TRANSITION_INTRA_CLOSE = 4;
    public static final int WALLPAPER_TRANSITION_INTRA_OPEN = 3;
    public static final int WALLPAPER_TRANSITION_NONE = 0;
    public static final int WALLPAPER_TRANSITION_OPEN = 1;
    private final int mClipRevealTranslationY;
    private final int mConfigShortAnimTime;
    private final android.content.Context mContext;
    private final boolean mDebug;
    private final android.view.animation.Interpolator mDecelerateInterpolator;
    private final int mDefaultWindowAnimationStyleResId;
    private final android.view.animation.Interpolator mFastOutLinearInInterpolator;
    private final android.view.animation.Interpolator mLinearOutSlowInInterpolator;
    private final java.lang.String mTag;
    private final com.android.internal.policy.LogDecelerateInterpolator mInterpolator = new com.android.internal.policy.LogDecelerateInterpolator(100, 0);
    private final android.view.animation.Interpolator mTouchResponseInterpolator = new android.view.animation.PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
    private final android.view.animation.Interpolator mClipHorizontalInterpolator = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.4f, 1.0f);
    private final android.graphics.Rect mTmpFromClipRect = new android.graphics.Rect();
    private final android.graphics.Rect mTmpToClipRect = new android.graphics.Rect();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private final android.view.animation.Interpolator mThumbnailFadeInInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.policy.TransitionAnimation$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float lambda$new$0;
            lambda$new$0 = com.android.internal.policy.TransitionAnimation.this.lambda$new$0(f);
            return lambda$new$0;
        }
    };
    private final android.view.animation.Interpolator mThumbnailFadeOutInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.policy.TransitionAnimation$$ExternalSyntheticLambda1
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float lambda$new$1;
            lambda$new$1 = com.android.internal.policy.TransitionAnimation.this.lambda$new$1(f);
            return lambda$new$1;
        }
    };
    private final boolean mGridLayoutRecentsEnabled = android.os.SystemProperties.getBoolean("ro.recents.grid", false);
    private final boolean mLowRamRecentsEnabled = android.app.ActivityManager.isLowRamDeviceStatic();

    public TransitionAnimation(android.content.Context context, boolean z, java.lang.String str) {
        this.mContext = context;
        this.mDebug = z;
        this.mTag = str;
        this.mDecelerateInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563651);
        this.mFastOutLinearInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563663);
        this.mLinearOutSlowInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563662);
        this.mClipRevealTranslationY = (int) (this.mContext.getResources().getDisplayMetrics().density * 8.0f);
        this.mConfigShortAnimTime = context.getResources().getInteger(17694720);
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(com.android.internal.R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ float lambda$new$0(float f) {
        if (f < 0.5f) {
            return 0.0f;
        }
        return this.mFastOutLinearInInterpolator.getInterpolation((f - 0.5f) / 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ float lambda$new$1(float f) {
        if (f < 0.5f) {
            return this.mLinearOutSlowInInterpolator.getInterpolation(f / 0.5f);
        }
        return 1.0f;
    }

    public android.view.animation.Animation loadKeyguardExitAnimation(int i, boolean z) {
        if ((i & 2) != 0) {
            return null;
        }
        return createHiddenByKeyguardExit(this.mContext, this.mInterpolator, z, (i & 1) != 0, (i & 8) != 0);
    }

    public android.view.animation.Animation loadKeyguardUnoccludeAnimation() {
        return loadDefaultAnimationRes(com.android.internal.R.anim.wallpaper_open_exit);
    }

    public android.view.animation.Animation loadVoiceActivityOpenAnimation(boolean z) {
        int i;
        if (z) {
            i = com.android.internal.R.anim.voice_activity_open_enter;
        } else {
            i = com.android.internal.R.anim.voice_activity_open_exit;
        }
        return loadDefaultAnimationRes(i);
    }

    public android.view.animation.Animation loadVoiceActivityExitAnimation(boolean z) {
        int i;
        if (z) {
            i = com.android.internal.R.anim.voice_activity_close_enter;
        } else {
            i = com.android.internal.R.anim.voice_activity_close_exit;
        }
        return loadDefaultAnimationRes(i);
    }

    public android.view.animation.Animation loadAppTransitionAnimation(java.lang.String str, int i) {
        return loadAnimationRes(str, i);
    }

    public android.view.animation.Animation loadCrossProfileAppEnterAnimation() {
        return loadAnimationRes("android", com.android.internal.R.anim.task_open_enter_cross_profile_apps);
    }

    public android.view.animation.Animation loadCrossProfileAppThumbnailEnterAnimation() {
        return loadAnimationRes("android", com.android.internal.R.anim.cross_profile_apps_thumbnail_enter);
    }

    public android.view.animation.Animation createCrossProfileAppsThumbnailAnimationLocked(android.graphics.Rect rect) {
        return prepareThumbnailAnimationWithDuration(loadCrossProfileAppThumbnailEnterAnimation(), rect.width(), rect.height(), 0L, null);
    }

    public android.view.animation.Animation loadAnimationRes(java.lang.String str, int i) {
        com.android.internal.policy.AttributeCache.Entry cachedAnimations;
        if (android.content.res.ResourceId.isValid(i) && (cachedAnimations = getCachedAnimations(str, i)) != null) {
            return loadAnimationSafely(cachedAnimations.context, i, this.mTag);
        }
        return null;
    }

    public android.view.animation.Animation loadDefaultAnimationRes(int i) {
        return loadAnimationRes("android", i);
    }

    public android.view.animation.Animation loadAnimationAttr(android.view.WindowManager.LayoutParams layoutParams, int i, int i2) {
        com.android.internal.policy.AttributeCache.Entry cachedAnimations;
        android.content.Context context = this.mContext;
        int i3 = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations(layoutParams)) != null) {
            context = cachedAnimations.context;
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        int updateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(i3, i2);
        if (android.content.res.ResourceId.isValid(updateToTranslucentAnimIfNeeded)) {
            return loadAnimationSafely(context, updateToTranslucentAnimIfNeeded, this.mTag);
        }
        return null;
    }

    public int getAnimationResId(android.view.WindowManager.LayoutParams layoutParams, int i, int i2) {
        com.android.internal.policy.AttributeCache.Entry cachedAnimations;
        int i3 = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations(layoutParams)) != null) {
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        return updateToTranslucentAnimIfNeeded(i3, i2);
    }

    public int getDefaultAnimationResId(int i, int i2) {
        com.android.internal.policy.AttributeCache.Entry cachedAnimations;
        int i3 = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations("android", this.mDefaultWindowAnimationStyleResId)) != null) {
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        return updateToTranslucentAnimIfNeeded(i3, i2);
    }

    private android.view.animation.Animation loadAnimationAttr(java.lang.String str, int i, int i2, boolean z, int i3) {
        if (i == 0) {
            return null;
        }
        android.content.Context context = this.mContext;
        int i4 = 0;
        if (i2 >= 0) {
            if (str == null) {
                str = "android";
            }
            com.android.internal.policy.AttributeCache.Entry cachedAnimations = getCachedAnimations(str, i);
            if (cachedAnimations != null) {
                context = cachedAnimations.context;
                i4 = cachedAnimations.array.getResourceId(i2, 0);
            }
        }
        if (z) {
            i4 = updateToTranslucentAnimIfNeeded(i4);
        } else if (i3 != -1) {
            i4 = updateToTranslucentAnimIfNeeded(i4, i3);
        }
        if (!android.content.res.ResourceId.isValid(i4)) {
            return null;
        }
        return loadAnimationSafely(context, i4, this.mTag);
    }

    public android.view.animation.Animation loadAnimationAttr(java.lang.String str, int i, int i2, boolean z) {
        return loadAnimationAttr(str, i, i2, z, -1);
    }

    public android.view.animation.Animation loadDefaultAnimationAttr(int i, boolean z) {
        return loadAnimationAttr("android", this.mDefaultWindowAnimationStyleResId, i, z);
    }

    public android.view.animation.Animation loadDefaultAnimationAttr(int i, int i2) {
        return loadAnimationAttr("android", this.mDefaultWindowAnimationStyleResId, i, false, i2);
    }

    private com.android.internal.policy.AttributeCache.Entry getCachedAnimations(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "Loading animations: layout params pkg=" + (layoutParams != null ? layoutParams.packageName : null) + " resId=0x" + (layoutParams != null ? java.lang.Integer.toHexString(layoutParams.windowAnimations) : null));
        }
        if (layoutParams == null || layoutParams.windowAnimations == 0) {
            return null;
        }
        java.lang.String str = layoutParams.packageName != null ? layoutParams.packageName : "android";
        int animationStyleResId = getAnimationStyleResId(layoutParams);
        java.lang.String str2 = ((-16777216) & animationStyleResId) != 16777216 ? str : "android";
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "Loading animations: picked package=" + str2);
        }
        return com.android.internal.policy.AttributeCache.instance().get(str2, animationStyleResId, com.android.internal.R.styleable.WindowAnimation);
    }

    private com.android.internal.policy.AttributeCache.Entry getCachedAnimations(java.lang.String str, int i) {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "Loading animations: package=" + str + " resId=0x" + java.lang.Integer.toHexString(i));
        }
        if (str != null) {
            if (((-16777216) & i) == 16777216) {
                str = "android";
            }
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Loading animations: picked package=" + str);
            }
            return com.android.internal.policy.AttributeCache.instance().get(str, i, com.android.internal.R.styleable.WindowAnimation);
        }
        return null;
    }

    public int getAnimationStyleResId(android.view.WindowManager.LayoutParams layoutParams) {
        int i = layoutParams.windowAnimations;
        if (layoutParams.type == 3) {
            return this.mDefaultWindowAnimationStyleResId;
        }
        return i;
    }

    public android.view.animation.Animation createRelaunchAnimation(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        setupDefaultNextAppTransitionStartRect(rect3, this.mTmpFromClipRect);
        this.mTmpFromClipRect.offset(-this.mTmpFromClipRect.left, -this.mTmpFromClipRect.top);
        int i = 0;
        this.mTmpToClipRect.set(0, 0, rect.width(), rect.height());
        android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
        float width = this.mTmpFromClipRect.width();
        float width2 = this.mTmpToClipRect.width();
        float height = this.mTmpFromClipRect.height();
        float height2 = (this.mTmpToClipRect.height() - rect2.top) - rect2.bottom;
        if (width <= width2 && height <= height2) {
            animationSet.addAnimation(new android.view.animation.ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect));
        } else {
            animationSet.addAnimation(new android.view.animation.ScaleAnimation(width / width2, 1.0f, height / height2, 1.0f));
            i = (int) ((rect2.top * height) / height2);
        }
        animationSet.addAnimation(new android.view.animation.TranslateAnimation(r12 - rect.left, 0.0f, (r0 - rect.top) - i, 0.0f));
        animationSet.setDuration(336L);
        animationSet.setZAdjustment(1);
        return animationSet;
    }

    private void setupDefaultNextAppTransitionStartRect(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect == null) {
            android.util.Slog.e(this.mTag, "Starting rect for app requested, but none available", new java.lang.Throwable());
            rect2.setEmpty();
        } else {
            rect2.set(rect);
        }
    }

    public android.view.animation.Animation createClipRevealAnimationLocked(int i, int i2, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        return createClipRevealAnimationLockedCompat(getTransitCompatType(i, i2), z, rect, rect2, rect3);
    }

    public android.view.animation.Animation createClipRevealAnimationLockedCompat(int i, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        long j;
        float f;
        boolean z2;
        android.view.animation.AlphaAnimation alphaAnimation;
        float f2;
        int i2;
        boolean z3;
        int i3;
        android.view.animation.Interpolator interpolator;
        if (z) {
            int width = rect.width();
            int height = rect.height();
            setupDefaultNextAppTransitionStartRect(rect3, this.mTmpRect);
            if (height <= 0) {
                f2 = 0.0f;
            } else {
                f2 = this.mTmpRect.top / rect2.height();
            }
            int height2 = this.mClipRevealTranslationY + ((int) ((rect2.height() / 7.0f) * f2));
            int centerX = this.mTmpRect.centerX();
            int centerY = this.mTmpRect.centerY();
            int width2 = this.mTmpRect.width() / 2;
            int height3 = this.mTmpRect.height() / 2;
            int i4 = centerX - width2;
            int i5 = i4 - rect.left;
            int i6 = centerY - height3;
            int i7 = i6 - rect.top;
            if (rect.top <= i6) {
                i2 = height2;
                z3 = false;
            } else {
                height2 = i6 - rect.top;
                i7 = 0;
                i2 = 0;
                z3 = true;
            }
            if (rect.left <= i4) {
                i3 = 0;
            } else {
                i3 = i4 - rect.left;
                i5 = 0;
                z3 = true;
            }
            int i8 = centerX + width2;
            if (rect.right < i8) {
                i3 = i8 - rect.right;
                i5 = width - this.mTmpRect.width();
                z3 = true;
            }
            float f3 = i3;
            float f4 = height2;
            long calculateClipRevealTransitionDuration = calculateClipRevealTransitionDuration(z3, f3, f4, rect2);
            com.android.internal.policy.ClipRectLRAnimation clipRectLRAnimation = new com.android.internal.policy.ClipRectLRAnimation(i5, this.mTmpRect.width() + i5, 0, width);
            clipRectLRAnimation.setInterpolator(this.mClipHorizontalInterpolator);
            clipRectLRAnimation.setDuration((long) (calculateClipRevealTransitionDuration / 2.5f));
            android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(f3, 0.0f, f4, 0.0f);
            if (z3) {
                interpolator = this.mTouchResponseInterpolator;
            } else {
                interpolator = this.mLinearOutSlowInInterpolator;
            }
            translateAnimation.setInterpolator(interpolator);
            translateAnimation.setDuration(calculateClipRevealTransitionDuration);
            com.android.internal.policy.ClipRectTBAnimation clipRectTBAnimation = new com.android.internal.policy.ClipRectTBAnimation(i7, i7 + this.mTmpRect.height(), 0, height, i2, 0, this.mLinearOutSlowInInterpolator);
            clipRectTBAnimation.setInterpolator(this.mTouchResponseInterpolator);
            clipRectTBAnimation.setDuration(calculateClipRevealTransitionDuration);
            android.view.animation.AlphaAnimation alphaAnimation2 = new android.view.animation.AlphaAnimation(0.5f, 1.0f);
            alphaAnimation2.setDuration(calculateClipRevealTransitionDuration / 4);
            alphaAnimation2.setInterpolator(this.mLinearOutSlowInInterpolator);
            android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(false);
            animationSet.addAnimation(clipRectLRAnimation);
            animationSet.addAnimation(clipRectTBAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation2);
            animationSet.setZAdjustment(1);
            animationSet.initialize(width, height, width, height);
            return animationSet;
        }
        switch (i) {
            case 6:
            case 7:
                j = this.mConfigShortAnimTime;
                break;
            default:
                j = 336;
                break;
        }
        if (i == 14) {
            f = 1.0f;
        } else {
            if (i != 15) {
                alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                z2 = true;
                alphaAnimation.setInterpolator(this.mDecelerateInterpolator);
                alphaAnimation.setDuration(j);
                alphaAnimation.setFillAfter(z2);
                return alphaAnimation;
            }
            f = 1.0f;
        }
        alphaAnimation = new android.view.animation.AlphaAnimation(f, 0.0f);
        z2 = true;
        alphaAnimation.setDetachWallpaper(true);
        alphaAnimation.setInterpolator(this.mDecelerateInterpolator);
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillAfter(z2);
        return alphaAnimation;
    }

    public android.view.animation.Animation createScaleUpAnimationLocked(int i, int i2, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return createScaleUpAnimationLockedCompat(getTransitCompatType(i, i2), z, rect, rect2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public android.view.animation.Animation createScaleUpAnimationLockedCompat(int i, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2) {
        android.view.animation.AlphaAnimation alphaAnimation;
        long j;
        setupDefaultNextAppTransitionStartRect(rect2, this.mTmpRect);
        int width = rect.width();
        int height = rect.height();
        if (z) {
            float width2 = this.mTmpRect.width() / width;
            float height2 = this.mTmpRect.height() / height;
            android.view.animation.ScaleAnimation scaleAnimation = new android.view.animation.ScaleAnimation(width2, 1.0f, height2, 1.0f, computePivot(this.mTmpRect.left, width2), computePivot(this.mTmpRect.top, height2));
            scaleAnimation.setInterpolator(this.mDecelerateInterpolator);
            android.view.animation.AlphaAnimation alphaAnimation2 = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
            alphaAnimation2.setInterpolator(this.mThumbnailFadeOutInterpolator);
            android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation2);
            animationSet.setDetachWallpaper(true);
            alphaAnimation = animationSet;
        } else if (i == 14 || i == 15) {
            android.view.animation.AlphaAnimation alphaAnimation3 = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
            alphaAnimation3.setDetachWallpaper(true);
            alphaAnimation = alphaAnimation3;
        } else {
            alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
        }
        switch (i) {
            case 6:
            case 7:
                j = this.mConfigShortAnimTime;
                break;
            default:
                j = 336;
                break;
        }
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(this.mDecelerateInterpolator);
        alphaAnimation.initialize(width, height, width, height);
        return alphaAnimation;
    }

    public android.view.animation.Animation createThumbnailEnterExitAnimationLocked(boolean z, boolean z2, android.graphics.Rect rect, int i, int i2, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect2) {
        return createThumbnailEnterExitAnimationLockedCompat(z, z2, rect, getTransitCompatType(i, i2), hardwareBuffer, rect2);
    }

    public android.view.animation.Animation createThumbnailEnterExitAnimationLockedCompat(boolean z, boolean z2, android.graphics.Rect rect, int i, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect2) {
        android.view.animation.Animation alphaAnimation;
        int width = rect.width();
        int height = rect.height();
        setupDefaultNextAppTransitionStartRect(rect2, this.mTmpRect);
        int width2 = hardwareBuffer != null ? hardwareBuffer.getWidth() : width;
        float f = width2 > 0 ? width2 : 1.0f;
        int height2 = hardwareBuffer != null ? hardwareBuffer.getHeight() : height;
        float f2 = height2 > 0 ? height2 : 1.0f;
        switch (getThumbnailTransitionState(z, z2)) {
            case 0:
                float f3 = f / width;
                float f4 = f2 / height;
                alphaAnimation = new android.view.animation.ScaleAnimation(f3, 1.0f, f4, 1.0f, computePivot(this.mTmpRect.left, f3), computePivot(this.mTmpRect.top, f4));
                break;
            case 1:
                if (i == 14) {
                    alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
                    break;
                } else {
                    alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                    break;
                }
            case 2:
                alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                break;
            case 3:
                float f5 = f / width;
                float f6 = f2 / height;
                android.view.animation.ScaleAnimation scaleAnimation = new android.view.animation.ScaleAnimation(1.0f, f5, 1.0f, f6, computePivot(this.mTmpRect.left, f5), computePivot(this.mTmpRect.top, f6));
                android.view.animation.AlphaAnimation alphaAnimation2 = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
                android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation2);
                animationSet.setZAdjustment(1);
                alphaAnimation = animationSet;
                break;
            default:
                throw new java.lang.RuntimeException("Invalid thumbnail transition state");
        }
        return prepareThumbnailAnimation(alphaAnimation, width, height, i);
    }

    public android.view.animation.Animation createAspectScaledThumbnailEnterExitAnimationLocked(boolean z, boolean z2, int i, int i2, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4, boolean z3, android.graphics.Rect rect5, android.graphics.Rect rect6) {
        android.view.animation.ClipRectAnimation clipRectAnimation;
        android.view.animation.ClipRectAnimation clipRectAnimation2;
        android.view.animation.Animation createCurvedMotion;
        android.view.animation.Animation animation;
        int width = rect.width();
        int height = rect.height();
        setupDefaultNextAppTransitionStartRect(rect6, this.mTmpRect);
        int width2 = this.mTmpRect.width();
        float f = width2 > 0 ? width2 : 1.0f;
        int height2 = this.mTmpRect.height();
        float f2 = height2 > 0 ? height2 : 1.0f;
        int i3 = (this.mTmpRect.left - rect.left) - rect2.left;
        int i4 = this.mTmpRect.top - rect.top;
        switch (getThumbnailTransitionState(z, z2)) {
            case 0:
            case 3:
                if (z3 && z2) {
                    animation = createAspectScaledThumbnailEnterFreeformAnimationLocked(rect, rect3, rect5, rect6);
                    break;
                } else if (z3) {
                    animation = createAspectScaledThumbnailExitFreeformAnimationLocked(rect, rect3, rect5, rect6);
                    break;
                } else {
                    android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
                    this.mTmpFromClipRect.set(rect);
                    this.mTmpToClipRect.set(rect);
                    this.mTmpFromClipRect.offsetTo(0, 0);
                    this.mTmpToClipRect.offsetTo(0, 0);
                    this.mTmpFromClipRect.inset(rect2);
                    if (!shouldScaleDownThumbnailTransition(i)) {
                        this.mTmpFromClipRect.bottom = this.mTmpFromClipRect.top + height2;
                        this.mTmpFromClipRect.right = this.mTmpFromClipRect.left + width2;
                        if (z2) {
                            clipRectAnimation = new android.view.animation.ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                        } else {
                            clipRectAnimation = new android.view.animation.ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                        }
                        android.view.animation.Animation createCurvedMotion2 = z2 ? createCurvedMotion(i3, 0.0f, i4 - rect2.top, 0.0f) : createCurvedMotion(0.0f, i3, 0.0f, i4 - rect2.top);
                        animationSet.addAnimation(clipRectAnimation);
                        animationSet.addAnimation(createCurvedMotion2);
                    } else {
                        float f3 = f / ((width - rect2.left) - rect2.right);
                        if (!this.mGridLayoutRecentsEnabled) {
                            this.mTmpFromClipRect.bottom = this.mTmpFromClipRect.top + ((int) (f2 / f3));
                        }
                        android.view.animation.ScaleAnimation scaleAnimation = new android.view.animation.ScaleAnimation(z2 ? f3 : 1.0f, z2 ? 1.0f : f3, z2 ? f3 : 1.0f, z2 ? 1.0f : f3, rect.width() / 2.0f, (rect.height() / 2.0f) + rect2.top);
                        float f4 = this.mTmpRect.left - rect.left;
                        float width3 = (rect.width() / 2.0f) - ((rect.width() / 2.0f) * f3);
                        float f5 = this.mTmpRect.top - rect.top;
                        float height3 = (rect.height() / 2.0f) - ((rect.height() / 2.0f) * f3);
                        if (this.mLowRamRecentsEnabled && rect2.top == 0 && z2) {
                            this.mTmpFromClipRect.top += rect4.top;
                            height3 += rect4.top;
                        }
                        float f6 = f4 - width3;
                        float f7 = f5 - height3;
                        if (z2) {
                            clipRectAnimation2 = new android.view.animation.ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                        } else {
                            clipRectAnimation2 = new android.view.animation.ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                        }
                        if (z2) {
                            createCurvedMotion = createCurvedMotion(f6, 0.0f, f7 - rect2.top, 0.0f);
                        } else {
                            createCurvedMotion = createCurvedMotion(0.0f, f6, 0.0f, f7 - rect2.top);
                        }
                        animationSet.addAnimation(clipRectAnimation2);
                        animationSet.addAnimation(scaleAnimation);
                        animationSet.addAnimation(createCurvedMotion);
                    }
                    animationSet.setZAdjustment(1);
                    animation = animationSet;
                    break;
                }
            case 1:
                if (i2 == 14) {
                    animation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
                    break;
                } else {
                    animation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                    break;
                }
            case 2:
                if (i2 == 14) {
                    animation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
                    break;
                } else {
                    animation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                    break;
                }
            default:
                throw new java.lang.RuntimeException("Invalid thumbnail transition state");
        }
        return prepareThumbnailAnimationWithDuration(animation, width, height, 336L, this.mTouchResponseInterpolator);
    }

    public android.view.animation.Animation createThumbnailAspectScaleAnimationLocked(android.graphics.Rect rect, android.graphics.Rect rect2, android.hardware.HardwareBuffer hardwareBuffer, int i, android.graphics.Rect rect3, android.graphics.Rect rect4, boolean z) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        int i2;
        android.view.animation.AnimationSet animationSet;
        int width = hardwareBuffer.getWidth();
        float f7 = width > 0 ? width : 1.0f;
        int height = hardwareBuffer.getHeight();
        int width2 = rect.width();
        float f8 = width2 / f7;
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        if (shouldScaleDownThumbnailTransition(i)) {
            f = this.mTmpRect.left;
            float f9 = this.mTmpRect.top;
            float width3 = ((this.mTmpRect.width() / 2) * (f8 - 1.0f)) + rect.left;
            float height2 = ((rect.height() / 2) * (1.0f - (1.0f / f8))) + rect.top;
            float width4 = this.mTmpRect.width() / 2;
            float height3 = (rect.height() / 2) / f8;
            if (!this.mGridLayoutRecentsEnabled) {
                f2 = f9;
                f5 = width4;
                f6 = height3;
                f3 = width3;
                f4 = height2;
            } else {
                float f10 = height;
                float f11 = height2 - (f10 * f8);
                f2 = f9 - f10;
                f5 = width4;
                f6 = height3;
                f3 = width3;
                f4 = f11;
            }
        } else {
            f = this.mTmpRect.left;
            f2 = this.mTmpRect.top;
            f3 = rect.left;
            f4 = rect.top;
            f5 = 0.0f;
            f6 = 0.0f;
        }
        if (z) {
            i2 = width2;
            android.view.animation.ScaleAnimation scaleAnimation = new android.view.animation.ScaleAnimation(1.0f, f8, 1.0f, f8, f5, f6);
            scaleAnimation.setInterpolator(TOUCH_RESPONSE_INTERPOLATOR);
            scaleAnimation.setDuration(336L);
            android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(this.mThumbnailFadeOutInterpolator);
            alphaAnimation.setDuration(336L);
            android.view.animation.Animation createCurvedMotion = createCurvedMotion(f, f3, f2, f4);
            createCurvedMotion.setInterpolator(TOUCH_RESPONSE_INTERPOLATOR);
            createCurvedMotion.setDuration(336L);
            this.mTmpFromClipRect.set(0, 0, width, height);
            this.mTmpToClipRect.set(rect);
            this.mTmpToClipRect.offsetTo(0, 0);
            this.mTmpToClipRect.right = (int) (this.mTmpToClipRect.right / f8);
            this.mTmpToClipRect.bottom = (int) (this.mTmpToClipRect.bottom / f8);
            if (rect2 != null) {
                this.mTmpToClipRect.inset((int) ((-rect2.left) * f8), (int) ((-rect2.top) * f8), (int) ((-rect2.right) * f8), (int) ((-rect2.bottom) * f8));
            }
            android.view.animation.ClipRectAnimation clipRectAnimation = new android.view.animation.ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
            clipRectAnimation.setInterpolator(TOUCH_RESPONSE_INTERPOLATOR);
            clipRectAnimation.setDuration(336L);
            animationSet = new android.view.animation.AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            if (!this.mGridLayoutRecentsEnabled) {
                animationSet.addAnimation(alphaAnimation);
            }
            animationSet.addAnimation(createCurvedMotion);
            animationSet.addAnimation(clipRectAnimation);
        } else {
            i2 = width2;
            android.view.animation.ScaleAnimation scaleAnimation2 = new android.view.animation.ScaleAnimation(f8, 1.0f, f8, 1.0f, f5, f6);
            scaleAnimation2.setInterpolator(TOUCH_RESPONSE_INTERPOLATOR);
            scaleAnimation2.setDuration(336L);
            android.view.animation.AlphaAnimation alphaAnimation2 = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
            alphaAnimation2.setInterpolator(this.mThumbnailFadeInInterpolator);
            alphaAnimation2.setDuration(336L);
            android.view.animation.Animation createCurvedMotion2 = createCurvedMotion(f3, f, f4, f2);
            createCurvedMotion2.setInterpolator(TOUCH_RESPONSE_INTERPOLATOR);
            createCurvedMotion2.setDuration(336L);
            android.view.animation.AnimationSet animationSet2 = new android.view.animation.AnimationSet(false);
            animationSet2.addAnimation(scaleAnimation2);
            if (!this.mGridLayoutRecentsEnabled) {
                animationSet2.addAnimation(alphaAnimation2);
            }
            animationSet2.addAnimation(createCurvedMotion2);
            animationSet = animationSet2;
        }
        return prepareThumbnailAnimationWithDuration(animationSet, i2, rect.height(), 0L, null);
    }

    public android.hardware.HardwareBuffer createCrossProfileAppsThumbnail(android.graphics.drawable.Drawable drawable, android.graphics.Rect rect) {
        int width = rect.width();
        int height = rect.height();
        android.graphics.Picture picture = new android.graphics.Picture();
        android.graphics.Canvas beginRecording = picture.beginRecording(width, height);
        beginRecording.drawColor(android.graphics.Color.argb(0.6f, 0.0f, 0.0f, 0.0f));
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.cross_profile_apps_thumbnail_size);
        drawable.setBounds((width - dimensionPixelSize) / 2, (height - dimensionPixelSize) / 2, (width + dimensionPixelSize) / 2, (height + dimensionPixelSize) / 2);
        drawable.setTint(this.mContext.getColor(17170443));
        drawable.draw(beginRecording);
        picture.endRecording();
        return android.graphics.Bitmap.createBitmap(picture).getHardwareBuffer();
    }

    private android.view.animation.Animation prepareThumbnailAnimation(android.view.animation.Animation animation, int i, int i2, int i3) {
        int i4;
        switch (i3) {
            case 6:
            case 7:
                i4 = this.mConfigShortAnimTime;
                break;
            default:
                i4 = 336;
                break;
        }
        return prepareThumbnailAnimationWithDuration(animation, i, i2, i4, this.mDecelerateInterpolator);
    }

    private android.view.animation.Animation createAspectScaledThumbnailEnterFreeformAnimationLocked(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        return createAspectScaledThumbnailFreeformAnimationLocked(this.mTmpRect, rect, rect2, true);
    }

    private android.view.animation.Animation createAspectScaledThumbnailExitFreeformAnimationLocked(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        return createAspectScaledThumbnailFreeformAnimationLocked(rect, this.mTmpRect, rect2, false);
    }

    private void getNextAppTransitionStartRect(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (rect == null && rect2 == null) {
            android.util.Slog.e(this.mTag, "Starting rect for container not available", new java.lang.Throwable());
            rect3.setEmpty();
        } else {
            if (rect == null) {
                rect = rect2;
            }
            rect3.set(rect);
        }
    }

    private android.view.animation.AnimationSet createAspectScaledThumbnailFreeformAnimationLocked(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, boolean z) {
        android.view.animation.ScaleAnimation scaleAnimation;
        android.view.animation.TranslateAnimation translateAnimation;
        float width = rect.width();
        float height = rect.height();
        float width2 = rect2.width();
        float height2 = rect2.height();
        float f = z ? width / width2 : width2 / width;
        float f2 = z ? height / height2 : height2 / height;
        android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
        int i = rect3 == null ? 0 : rect3.left + rect3.right;
        int i2 = rect3 != null ? rect3.top + rect3.bottom : 0;
        if (z) {
            width = width2;
        }
        float f3 = (width + i) / 2.0f;
        if (z) {
            height = height2;
        }
        float f4 = (height + i2) / 2.0f;
        if (z) {
            scaleAnimation = new android.view.animation.ScaleAnimation(f, 1.0f, f2, 1.0f, f3, f4);
        } else {
            scaleAnimation = new android.view.animation.ScaleAnimation(1.0f, f, 1.0f, f2, f3, f4);
        }
        int width3 = rect.left + (rect.width() / 2);
        int height3 = rect.top + (rect.height() / 2);
        int width4 = rect2.left + (rect2.width() / 2);
        int height4 = rect2.top + (rect2.height() / 2);
        int i3 = z ? width3 - width4 : width4 - width3;
        int i4 = z ? height3 - height4 : height4 - height3;
        if (z) {
            translateAnimation = new android.view.animation.TranslateAnimation(i3, 0.0f, i4, 0.0f);
        } else {
            translateAnimation = new android.view.animation.TranslateAnimation(0.0f, i3, 0.0f, i4);
        }
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private boolean shouldScaleDownThumbnailTransition(int i) {
        return this.mGridLayoutRecentsEnabled || i == 1;
    }

    private static int updateToTranslucentAnimIfNeeded(int i, int i2) {
        if (i2 == 24 && i == 17432591) {
            return com.android.internal.R.anim.activity_translucent_open_enter;
        }
        if (i2 == 25 && i == 17432590) {
            return com.android.internal.R.anim.activity_translucent_close_exit;
        }
        return i;
    }

    private static int updateToTranslucentAnimIfNeeded(int i) {
        if (i == 17432591) {
            return com.android.internal.R.anim.activity_translucent_open_enter;
        }
        if (i == 17432590) {
            return com.android.internal.R.anim.activity_translucent_close_exit;
        }
        return i;
    }

    private static int getTransitCompatType(int i, int i2) {
        if (i2 == 3) {
            return 14;
        }
        if (i2 == 4) {
            return 15;
        }
        if (i == 1) {
            return 6;
        }
        if (i == 2) {
            return 7;
        }
        return 0;
    }

    private static long calculateClipRevealTransitionDuration(boolean z, float f, float f2, android.graphics.Rect rect) {
        if (!z) {
            return 336L;
        }
        return (long) ((java.lang.Math.max(java.lang.Math.abs(f) / rect.width(), java.lang.Math.abs(f2) / rect.height()) * 84.0f) + 336.0f);
    }

    private int getThumbnailTransitionState(boolean z, boolean z2) {
        if (z) {
            if (z2) {
                return 0;
            }
            return 2;
        }
        if (z2) {
            return 1;
        }
        return 3;
    }

    public static android.view.animation.Animation prepareThumbnailAnimationWithDuration(android.view.animation.Animation animation, int i, int i2, long j, android.view.animation.Interpolator interpolator) {
        if (animation == null) {
            return null;
        }
        if (j > 0) {
            animation.setDuration(j);
        }
        animation.setFillAfter(true);
        if (interpolator != null) {
            animation.setInterpolator(interpolator);
        }
        animation.initialize(i, i2, i, i2);
        return animation;
    }

    private static android.view.animation.Animation createCurvedMotion(float f, float f2, float f3, float f4) {
        return new android.view.animation.TranslateAnimation(f, f2, f3, f4);
    }

    public static float computePivot(int i, float f) {
        float f2 = f - 1.0f;
        if (java.lang.Math.abs(f2) < 1.0E-4f) {
            return i;
        }
        return (-i) / f2;
    }

    public static android.view.animation.Animation loadAnimationSafely(android.content.Context context, int i, java.lang.String str) {
        try {
            return android.view.animation.AnimationUtils.loadAnimation(context, i);
        } catch (android.content.res.Resources.NotFoundException | android.view.InflateException e) {
            android.util.Slog.w(str, "Unable to load animation resource", e);
            return null;
        }
    }

    public static android.view.animation.Animation createHiddenByKeyguardExit(android.content.Context context, com.android.internal.policy.LogDecelerateInterpolator logDecelerateInterpolator, boolean z, boolean z2, boolean z3) {
        int i;
        if (z2) {
            return android.view.animation.AnimationUtils.loadAnimation(context, com.android.internal.R.anim.lock_screen_behind_enter_fade_in);
        }
        if (z3) {
            i = com.android.internal.R.anim.lock_screen_behind_enter_subtle;
        } else if (z) {
            i = com.android.internal.R.anim.lock_screen_behind_enter_wallpaper;
        } else {
            i = com.android.internal.R.anim.lock_screen_behind_enter;
        }
        android.view.animation.AnimationSet animationSet = (android.view.animation.AnimationSet) android.view.animation.AnimationUtils.loadAnimation(context, i);
        java.util.List<android.view.animation.Animation> animations = animationSet.getAnimations();
        for (int size = animations.size() - 1; size >= 0; size--) {
            animations.get(size).setInterpolator(logDecelerateInterpolator);
        }
        return animationSet;
    }

    public static void configureScreenshotLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        transaction.setBuffer(surfaceControl, screenshotHardwareBuffer.getHardwareBuffer());
        transaction.setDataSpace(surfaceControl, screenshotHardwareBuffer.getColorSpace().getDataSpace());
        if (screenshotHardwareBuffer.containsHdrLayers()) {
            transaction.setDimmingEnabled(surfaceControl, false);
        }
    }

    public static boolean hasProtectedContent(android.hardware.HardwareBuffer hardwareBuffer) {
        return (hardwareBuffer.getUsage() & 16384) == 16384;
    }

    public static float getBorderLuma(android.view.SurfaceControl surfaceControl, int i, int i2) {
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(surfaceControl, new android.graphics.Rect(0, 0, i, i2), 1.0f);
        if (captureLayers == null) {
            return 0.0f;
        }
        android.hardware.HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
        float borderLuma = getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
        if (hardwareBuffer != null) {
            hardwareBuffer.close();
        }
        return borderLuma;
    }

    public static float getBorderLuma(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace) {
        int format;
        if (hardwareBuffer == null || (format = hardwareBuffer.getFormat()) != 1 || hasProtectedContent(hardwareBuffer)) {
            return 0.0f;
        }
        android.media.ImageReader newInstance = android.media.ImageReader.newInstance(hardwareBuffer.getWidth(), hardwareBuffer.getHeight(), format, 1);
        newInstance.getSurface().attachAndQueueBufferWithColorSpace(hardwareBuffer, colorSpace);
        android.media.Image acquireLatestImage = newInstance.acquireLatestImage();
        if (acquireLatestImage == null || acquireLatestImage.getPlaneCount() < 1) {
            return 0.0f;
        }
        android.media.Image.Plane plane = acquireLatestImage.getPlanes()[0];
        java.nio.ByteBuffer buffer = plane.getBuffer();
        int width = acquireLatestImage.getWidth();
        int height = acquireLatestImage.getHeight();
        int pixelStride = plane.getPixelStride();
        int rowStride = plane.getRowStride();
        int i = ((width + height) * 2) / 10;
        int[] iArr = new int[i];
        int i2 = width - 10;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4 += 10) {
            int i5 = i3 + 1;
            iArr[i3] = getPixelLuminance(buffer, i4, 0, pixelStride, rowStride);
            i3 = i5 + 1;
            iArr[i5] = getPixelLuminance(buffer, i4, height - 1, pixelStride, rowStride);
        }
        int i6 = height - 10;
        for (int i7 = 0; i7 < i6; i7 += 10) {
            int i8 = i3 + 1;
            iArr[i3] = getPixelLuminance(buffer, 0, i7, pixelStride, rowStride);
            i3 = i8 + 1;
            iArr[i8] = getPixelLuminance(buffer, width - 1, i7, pixelStride, rowStride);
        }
        newInstance.close();
        int[] iArr2 = new int[256];
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < i; i11++) {
            int i12 = iArr[i11];
            int i13 = iArr2[i12] + 1;
            iArr2[i12] = i13;
            if (i13 > i10) {
                i9 = i12;
                i10 = i13;
            }
        }
        return i9 / 255.0f;
    }

    private static int getPixelLuminance(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int i5 = byteBuffer.getInt((i2 * i4) + (i * i3));
        return ((((i5 & 255) * 8) + (((i5 >> 8) & 255) * 22)) + (((i5 >> 16) & 255) * 2)) >> 5;
    }

    public static void initAttributeCache(android.content.Context context, android.os.Handler handler) {
        com.android.internal.policy.AttributeCache.init(context);
        com.android.internal.policy.AttributeCache.instance().monitorPackageRemove(handler);
    }
}
