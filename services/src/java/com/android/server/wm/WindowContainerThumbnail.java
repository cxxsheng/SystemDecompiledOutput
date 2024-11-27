package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowContainerThumbnail implements com.android.server.wm.SurfaceAnimator.Animatable {
    private static final java.lang.String TAG = "WindowManager";
    private final int mHeight;
    private final com.android.server.wm.SurfaceAnimator mSurfaceAnimator;
    private android.view.SurfaceControl mSurfaceControl;
    private final int mWidth;
    private final com.android.server.wm.WindowContainer mWindowContainer;

    WindowContainerThumbnail(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer, android.hardware.HardwareBuffer hardwareBuffer) {
        this(transaction, windowContainer, hardwareBuffer, null);
    }

    WindowContainerThumbnail(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer, android.hardware.HardwareBuffer hardwareBuffer, com.android.server.wm.SurfaceAnimator surfaceAnimator) {
        this.mWindowContainer = windowContainer;
        if (surfaceAnimator != null) {
            this.mSurfaceAnimator = surfaceAnimator;
        } else {
            this.mSurfaceAnimator = new com.android.server.wm.SurfaceAnimator(this, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainerThumbnail$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
                    com.android.server.wm.WindowContainerThumbnail.this.onAnimationFinished(i, animationAdapter);
                }
            }, windowContainer.mWmService);
        }
        this.mWidth = hardwareBuffer.getWidth();
        this.mHeight = hardwareBuffer.getHeight();
        this.mSurfaceControl = this.mWindowContainer.makeChildSurface(this.mWindowContainer.getTopChild()).setName("thumbnail anim: " + this.mWindowContainer.toString()).setBLASTLayer().setFormat(-3).setMetadata(2, this.mWindowContainer.getWindowingMode()).setMetadata(1, com.android.server.wm.WindowManagerService.MY_UID).setCallsite("WindowContainerThumbnail").build();
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -131600102855790053L, 0, null, java.lang.String.valueOf(this.mSurfaceControl));
        transaction.setBuffer(this.mSurfaceControl, android.graphics.GraphicBuffer.createFromHardwareBuffer(hardwareBuffer));
        transaction.setColorSpace(this.mSurfaceControl, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
        transaction.show(this.mSurfaceControl);
        transaction.setLayer(this.mSurfaceControl, Integer.MAX_VALUE);
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, android.view.animation.Animation animation) {
        startAnimation(transaction, animation, (android.graphics.Point) null);
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, android.view.animation.Animation animation, android.graphics.Point point) {
        animation.restrictDuration(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        animation.scaleCurrentDuration(this.mWindowContainer.mWmService.getTransitionAnimationScaleLocked());
        this.mSurfaceAnimator.startAnimation(transaction, new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowAnimationSpec(animation, point, this.mWindowContainer.getDisplayContent().mAppTransition.canSkipFirstFrame(), this.mWindowContainer.getDisplayContent().getWindowCornerRadius()), this.mWindowContainer.mWmService.mSurfaceAnimationRunner), false, 8);
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z) {
        this.mSurfaceAnimator.startAnimation(transaction, animationAdapter, z, 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
    }

    void setShowing(android.view.SurfaceControl.Transaction transaction, boolean z) {
        if (z) {
            transaction.show(this.mSurfaceControl);
        } else {
            transaction.hide(this.mSurfaceControl);
        }
    }

    void destroy() {
        this.mSurfaceAnimator.cancelAnimation();
        getPendingTransaction().remove(this.mSurfaceControl);
        this.mSurfaceControl = null;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mWidth);
        protoOutputStream.write(1120986464258L, this.mHeight);
        if (this.mSurfaceAnimator.isAnimating()) {
            this.mSurfaceAnimator.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Transaction getSyncTransaction() {
        return this.mWindowContainer.getSyncTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Transaction getPendingTransaction() {
        return this.mWindowContainer.getPendingTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void commitPendingTransaction() {
        this.mWindowContainer.commitPendingTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        transaction.hide(this.mSurfaceControl);
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Builder makeAnimationLeash() {
        return this.mWindowContainer.makeChildSurface(this.mWindowContainer.getTopChild());
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getAnimationLeashParent() {
        return this.mWindowContainer.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getParentSurfaceControl() {
        return this.mWindowContainer.getParentSurfaceControl();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceWidth() {
        return this.mWidth;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceHeight() {
        return this.mHeight;
    }
}
