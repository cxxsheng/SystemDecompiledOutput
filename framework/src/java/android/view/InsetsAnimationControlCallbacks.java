package android.view;

/* loaded from: classes4.dex */
public interface InsetsAnimationControlCallbacks {
    void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

    void notifyFinished(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z);

    void releaseSurfaceControlFromRt(android.view.SurfaceControl surfaceControl);

    void reportPerceptible(int i, boolean z);

    void scheduleApplyChangeInsets(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner);

    <T extends android.view.InsetsAnimationControlRunner & android.view.InternalInsetsAnimationController> void startAnimation(T t, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int i, android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds);
}
