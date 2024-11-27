package com.android.server.wm;

/* loaded from: classes3.dex */
public class FadeAnimationController {
    protected final android.content.Context mContext;
    protected final com.android.server.wm.DisplayContent mDisplayContent;

    public FadeAnimationController(com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mContext = displayContent.mWmService.mContext;
    }

    public android.view.animation.Animation getFadeInAnimation() {
        return android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.fade_in);
    }

    public android.view.animation.Animation getFadeOutAnimation() {
        return android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.fade_out);
    }

    public void fadeWindowToken(boolean z, com.android.server.wm.WindowToken windowToken, int i) {
        fadeWindowToken(z, windowToken, i, null);
    }

    public void fadeWindowToken(boolean z, com.android.server.wm.WindowToken windowToken, int i, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        if (windowToken == null || windowToken.getParent() == null) {
            return;
        }
        android.view.animation.Animation fadeInAnimation = z ? getFadeInAnimation() : getFadeOutAnimation();
        com.android.server.wm.FadeAnimationController.FadeAnimationAdapter createAdapter = fadeInAnimation != null ? createAdapter(createAnimationSpec(fadeInAnimation), z, windowToken) : null;
        if (createAdapter == null) {
            return;
        }
        windowToken.startAnimation(windowToken.getPendingTransaction(), createAdapter, z, i, onAnimationFinishedCallback);
    }

    protected com.android.server.wm.FadeAnimationController.FadeAnimationAdapter createAdapter(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, boolean z, com.android.server.wm.WindowToken windowToken) {
        return new com.android.server.wm.FadeAnimationController.FadeAnimationAdapter(animationSpec, windowToken.getSurfaceAnimationRunner(), z, windowToken);
    }

    protected com.android.server.wm.LocalAnimationAdapter.AnimationSpec createAnimationSpec(@android.annotation.NonNull final android.view.animation.Animation animation) {
        return new com.android.server.wm.LocalAnimationAdapter.AnimationSpec() { // from class: com.android.server.wm.FadeAnimationController.1
            final android.view.animation.Transformation mTransformation = new android.view.animation.Transformation();

            @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
            public boolean getShowWallpaper() {
                return true;
            }

            @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
            public long getDuration() {
                return animation.getDuration();
            }

            @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
            public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
                this.mTransformation.clear();
                animation.getTransformation(j, this.mTransformation);
                transaction.setAlpha(surfaceControl, this.mTransformation.getAlpha());
            }

            @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
            public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                printWriter.print(str);
                printWriter.println(animation);
            }

            @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
            public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
                long start = protoOutputStream.start(1146756268033L);
                protoOutputStream.write(1138166333441L, animation.toString());
                protoOutputStream.end(start);
            }
        };
    }

    protected static class FadeAnimationAdapter extends com.android.server.wm.LocalAnimationAdapter {
        protected final boolean mShow;
        protected final com.android.server.wm.WindowToken mToken;

        FadeAnimationAdapter(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner, boolean z, com.android.server.wm.WindowToken windowToken) {
            super(animationSpec, surfaceAnimationRunner);
            this.mShow = z;
            this.mToken = windowToken;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
            return !this.mShow;
        }
    }
}
