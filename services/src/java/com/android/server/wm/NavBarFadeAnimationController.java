package com.android.server.wm;

/* loaded from: classes3.dex */
public class NavBarFadeAnimationController extends com.android.server.wm.FadeAnimationController {
    private static final int FADE_IN_DURATION = 266;
    private static final int FADE_OUT_DURATION = 133;
    private android.view.animation.Animation mFadeInAnimation;
    private android.view.SurfaceControl mFadeInParent;
    private android.view.animation.Animation mFadeOutAnimation;
    private android.view.SurfaceControl mFadeOutParent;
    private final com.android.server.wm.WindowState mNavigationBar;
    private boolean mPlaySequentially;
    private static final android.view.animation.Interpolator FADE_IN_INTERPOLATOR = new android.view.animation.PathInterpolator(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
    private static final android.view.animation.Interpolator FADE_OUT_INTERPOLATOR = new android.view.animation.PathInterpolator(0.2f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, 1.0f);

    public NavBarFadeAnimationController(com.android.server.wm.DisplayContent displayContent) {
        super(displayContent);
        this.mPlaySequentially = false;
        this.mNavigationBar = displayContent.getDisplayPolicy().getNavigationBar();
        this.mFadeInAnimation = new android.view.animation.AlphaAnimation(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
        this.mFadeInAnimation.setDuration(266L);
        this.mFadeInAnimation.setInterpolator(FADE_IN_INTERPOLATOR);
        this.mFadeOutAnimation = new android.view.animation.AlphaAnimation(1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        this.mFadeOutAnimation.setDuration(133L);
        this.mFadeOutAnimation.setInterpolator(FADE_OUT_INTERPOLATOR);
    }

    @Override // com.android.server.wm.FadeAnimationController
    public android.view.animation.Animation getFadeInAnimation() {
        return this.mFadeInAnimation;
    }

    @Override // com.android.server.wm.FadeAnimationController
    public android.view.animation.Animation getFadeOutAnimation() {
        return this.mFadeOutAnimation;
    }

    @Override // com.android.server.wm.FadeAnimationController
    protected com.android.server.wm.FadeAnimationController.FadeAnimationAdapter createAdapter(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, boolean z, com.android.server.wm.WindowToken windowToken) {
        return new com.android.server.wm.NavBarFadeAnimationController.NavFadeAnimationAdapter(animationSpec, windowToken.getSurfaceAnimationRunner(), z, windowToken, z ? this.mFadeInParent : this.mFadeOutParent);
    }

    public void fadeWindowToken(final boolean z) {
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mDisplayContent.getAsyncRotationController();
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.NavBarFadeAnimationController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.NavBarFadeAnimationController.this.lambda$fadeWindowToken$0(z);
            }
        };
        if (asyncRotationController == null) {
            runnable.run();
        } else if (!asyncRotationController.hasFadeOperation(this.mNavigationBar.mToken)) {
            if (z) {
                asyncRotationController.setOnShowRunnable(runnable);
            } else {
                runnable.run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeWindowToken$0(boolean z) {
        fadeWindowToken(z, this.mNavigationBar.mToken, 64);
    }

    void fadeOutAndInSequentially(long j, android.view.SurfaceControl surfaceControl, android.view.SurfaceControl surfaceControl2) {
        this.mPlaySequentially = true;
        if (j > 0) {
            long j2 = (2 * j) / 3;
            this.mFadeOutAnimation.setDuration(j - j2);
            this.mFadeInAnimation.setDuration(j2);
        }
        this.mFadeOutParent = surfaceControl;
        this.mFadeInParent = surfaceControl2;
        fadeWindowToken(false);
    }

    protected class NavFadeAnimationAdapter extends com.android.server.wm.FadeAnimationController.FadeAnimationAdapter {
        private android.view.SurfaceControl mParent;

        NavFadeAnimationAdapter(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner, boolean z, com.android.server.wm.WindowToken windowToken, android.view.SurfaceControl surfaceControl) {
            super(animationSpec, surfaceAnimationRunner, z, windowToken);
            this.mParent = surfaceControl;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter, com.android.server.wm.AnimationAdapter
        public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            super.startAnimation(surfaceControl, transaction, i, onAnimationFinishedCallback);
            if (this.mParent != null && this.mParent.isValid()) {
                transaction.reparent(surfaceControl, this.mParent);
                transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
            }
        }

        @Override // com.android.server.wm.FadeAnimationController.FadeAnimationAdapter, com.android.server.wm.AnimationAdapter
        public boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
            if (com.android.server.wm.NavBarFadeAnimationController.this.mPlaySequentially) {
                if (!this.mShow) {
                    com.android.server.wm.NavBarFadeAnimationController.this.fadeWindowToken(true);
                    return false;
                }
                return false;
            }
            return super.shouldDeferAnimationFinish(runnable);
        }
    }
}
