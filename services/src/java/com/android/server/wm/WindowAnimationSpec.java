package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowAnimationSpec implements com.android.server.wm.LocalAnimationAdapter.AnimationSpec {
    private android.view.animation.Animation mAnimation;
    private final boolean mCanSkipFirstFrame;
    private final boolean mIsAppAnimation;
    private final android.graphics.Point mPosition;
    private final android.graphics.Rect mRootTaskBounds;
    private int mRootTaskClipMode;
    private final java.lang.ThreadLocal<com.android.server.wm.WindowAnimationSpec.TmpValues> mThreadLocalTmps;
    private final android.graphics.Rect mTmpRect;
    private final float mWindowCornerRadius;

    /* renamed from: $r8$lambda$O4wc4-tRjiP9nCMbsYU_dS1zsf4, reason: not valid java name */
    public static /* synthetic */ com.android.server.wm.WindowAnimationSpec.TmpValues m9508$r8$lambda$O4wc4tRjiP9nCMbsYU_dS1zsf4() {
        return new com.android.server.wm.WindowAnimationSpec.TmpValues();
    }

    public WindowAnimationSpec(android.view.animation.Animation animation, android.graphics.Point point, boolean z, float f) {
        this(animation, point, null, z, 1, false, f);
    }

    public WindowAnimationSpec(android.view.animation.Animation animation, android.graphics.Point point, android.graphics.Rect rect, boolean z, int i, boolean z2, float f) {
        this.mPosition = new android.graphics.Point();
        this.mThreadLocalTmps = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.server.wm.WindowAnimationSpec$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.wm.WindowAnimationSpec.m9508$r8$lambda$O4wc4tRjiP9nCMbsYU_dS1zsf4();
            }
        });
        this.mRootTaskBounds = new android.graphics.Rect();
        this.mTmpRect = new android.graphics.Rect();
        this.mAnimation = animation;
        if (point != null) {
            this.mPosition.set(point.x, point.y);
        }
        this.mWindowCornerRadius = f;
        this.mCanSkipFirstFrame = z;
        this.mIsAppAnimation = z2;
        this.mRootTaskClipMode = i;
        if (rect != null) {
            this.mRootTaskBounds.set(rect);
        }
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public com.android.server.wm.WindowAnimationSpec asWindowAnimationSpec() {
        return this;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean getShowWallpaper() {
        return this.mAnimation.getShowWallpaper();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean getShowBackground() {
        return this.mAnimation.getShowBackdrop();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public int getBackgroundColor() {
        return this.mAnimation.getBackdropColor();
    }

    public boolean hasExtension() {
        return this.mAnimation.hasExtension();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public long getDuration() {
        return this.mAnimation.computeDurationHint();
    }

    public android.graphics.Rect getRootTaskBounds() {
        return this.mRootTaskBounds;
    }

    public android.view.animation.Animation getAnimation() {
        return this.mAnimation;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
        com.android.server.wm.WindowAnimationSpec.TmpValues tmpValues = this.mThreadLocalTmps.get();
        tmpValues.transformation.clear();
        this.mAnimation.getTransformation(j, tmpValues.transformation);
        tmpValues.transformation.getMatrix().postTranslate(this.mPosition.x, this.mPosition.y);
        transaction.setMatrix(surfaceControl, tmpValues.transformation.getMatrix(), tmpValues.floats);
        transaction.setAlpha(surfaceControl, tmpValues.transformation.getAlpha());
        boolean z = true;
        if (this.mRootTaskClipMode == 1) {
            if (!tmpValues.transformation.hasClipRect()) {
                z = false;
            } else {
                android.graphics.Rect clipRect = tmpValues.transformation.getClipRect();
                accountForExtension(tmpValues.transformation, clipRect);
                transaction.setWindowCrop(surfaceControl, clipRect);
            }
        } else {
            this.mTmpRect.set(this.mRootTaskBounds);
            if (tmpValues.transformation.hasClipRect()) {
                this.mTmpRect.intersect(tmpValues.transformation.getClipRect());
            }
            accountForExtension(tmpValues.transformation, this.mTmpRect);
            transaction.setWindowCrop(surfaceControl, this.mTmpRect);
        }
        if (z && this.mAnimation.hasRoundedCorners() && this.mWindowCornerRadius > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            transaction.setCornerRadius(surfaceControl, this.mWindowCornerRadius);
        }
    }

    private void accountForExtension(android.view.animation.Transformation transformation, android.graphics.Rect rect) {
        android.graphics.Insets min = android.graphics.Insets.min(transformation.getInsets(), android.graphics.Insets.NONE);
        if (!min.equals(android.graphics.Insets.NONE)) {
            rect.inset(min);
        }
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public long calculateStatusBarTransitionStartTime() {
        android.view.animation.TranslateAnimation findTranslateAnimation = findTranslateAnimation(this.mAnimation);
        if (findTranslateAnimation != null) {
            if (findTranslateAnimation.isXAxisTransition() && findTranslateAnimation.isFullWidthTranslate()) {
                return ((android.os.SystemClock.uptimeMillis() + findTranslateAnimation.getStartOffset()) + ((long) (findTranslateAnimation.getDuration() * findMiddleOfTranslationFraction(findTranslateAnimation.getInterpolator())))) - 60;
            }
            return ((android.os.SystemClock.uptimeMillis() + findTranslateAnimation.getStartOffset()) + ((long) (findTranslateAnimation.getDuration() * findAlmostThereFraction(findTranslateAnimation.getInterpolator())))) - 120;
        }
        return android.os.SystemClock.uptimeMillis();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean canSkipFirstFrame() {
        return this.mCanSkipFirstFrame;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public boolean needsEarlyWakeup() {
        return this.mIsAppAnimation;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println(this.mAnimation);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, this.mAnimation.toString());
        protoOutputStream.end(start);
    }

    private static android.view.animation.TranslateAnimation findTranslateAnimation(android.view.animation.Animation animation) {
        if (animation instanceof android.view.animation.TranslateAnimation) {
            return (android.view.animation.TranslateAnimation) animation;
        }
        if (animation instanceof android.view.animation.AnimationSet) {
            android.view.animation.AnimationSet animationSet = (android.view.animation.AnimationSet) animation;
            for (int i = 0; i < animationSet.getAnimations().size(); i++) {
                android.view.animation.Animation animation2 = animationSet.getAnimations().get(i);
                if (animation2 instanceof android.view.animation.TranslateAnimation) {
                    return (android.view.animation.TranslateAnimation) animation2;
                }
            }
            return null;
        }
        return null;
    }

    private static float findAlmostThereFraction(android.view.animation.Interpolator interpolator) {
        return findInterpolationAdjustedTargetFraction(interpolator, 0.99f, 0.01f);
    }

    private float findMiddleOfTranslationFraction(android.view.animation.Interpolator interpolator) {
        return findInterpolationAdjustedTargetFraction(interpolator, 0.5f, 0.01f);
    }

    private static float findInterpolationAdjustedTargetFraction(android.view.animation.Interpolator interpolator, float f, float f2) {
        float f3 = 0.5f;
        for (float f4 = 0.25f; f4 >= f2; f4 /= 2.0f) {
            if (interpolator.getInterpolation(f3) < f) {
                f3 += f4;
            } else {
                f3 -= f4;
            }
        }
        return f3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TmpValues {
        final float[] floats;
        final android.view.animation.Transformation transformation;

        private TmpValues() {
            this.transformation = new android.view.animation.Transformation();
            this.floats = new float[9];
        }
    }
}
