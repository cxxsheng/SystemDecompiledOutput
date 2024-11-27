package com.android.server.wm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes3.dex */
public interface AnimationAdapter {
    public static final long STATUS_BAR_TRANSITION_DURATION = 120;

    void dump(java.io.PrintWriter printWriter, java.lang.String str);

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream);

    long getDurationHint();

    boolean getShowWallpaper();

    long getStatusBarTransitionsStartTime();

    void onAnimationCancelled(android.view.SurfaceControl surfaceControl);

    void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback);

    default boolean getShowBackground() {
        return false;
    }

    default int getBackgroundColor() {
        return 0;
    }

    default void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        dumpDebug(protoOutputStream);
        protoOutputStream.end(start);
    }

    default boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
        return false;
    }
}
