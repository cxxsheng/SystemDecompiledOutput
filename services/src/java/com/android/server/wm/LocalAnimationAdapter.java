package com.android.server.wm;

/* loaded from: classes3.dex */
class LocalAnimationAdapter implements com.android.server.wm.AnimationAdapter {
    private final com.android.server.wm.SurfaceAnimationRunner mAnimator;
    private final com.android.server.wm.LocalAnimationAdapter.AnimationSpec mSpec;

    LocalAnimationAdapter(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner) {
        this.mSpec = animationSpec;
        this.mAnimator = surfaceAnimationRunner;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
        return this.mSpec.getShowWallpaper();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowBackground() {
        return this.mSpec.getShowBackground();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public int getBackgroundColor() {
        return this.mSpec.getBackgroundColor();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, final int i, @android.annotation.NonNull final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        this.mAnimator.startAnimation(this.mSpec, surfaceControl, transaction, new java.lang.Runnable() { // from class: com.android.server.wm.LocalAnimationAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.LocalAnimationAdapter.this.lambda$startAnimation$0(onAnimationFinishedCallback, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$0(com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, int i) {
        onAnimationFinishedCallback.onAnimationFinished(i, this);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
        this.mAnimator.onAnimationCancelled(surfaceControl);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
        return this.mSpec.getDuration();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
        return this.mSpec.calculateStatusBarTransitionStartTime();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        this.mSpec.dump(printWriter, str);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        this.mSpec.dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.end(start);
    }

    interface AnimationSpec {
        void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j);

        void dump(java.io.PrintWriter printWriter, java.lang.String str);

        void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream);

        long getDuration();

        default boolean getShowWallpaper() {
            return false;
        }

        default boolean getShowBackground() {
            return false;
        }

        default int getBackgroundColor() {
            return 0;
        }

        default long calculateStatusBarTransitionStartTime() {
            return android.os.SystemClock.uptimeMillis();
        }

        default boolean canSkipFirstFrame() {
            return false;
        }

        default boolean needsEarlyWakeup() {
            return false;
        }

        default float getFraction(float f) {
            float duration = getDuration();
            if (duration > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return f / duration;
            }
            return 1.0f;
        }

        default void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            dumpDebugInner(protoOutputStream);
            protoOutputStream.end(start);
        }

        default com.android.server.wm.WindowAnimationSpec asWindowAnimationSpec() {
            return null;
        }
    }
}
