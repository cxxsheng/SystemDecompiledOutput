package com.android.server.wm;

/* loaded from: classes3.dex */
class SurfaceFreezer {
    private static final java.lang.String TAG = "SurfaceFreezer";

    @android.annotation.NonNull
    private final com.android.server.wm.SurfaceFreezer.Freezable mAnimatable;

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl mLeash;

    @android.annotation.NonNull
    private final com.android.server.wm.WindowManagerService mWmService;
    com.android.server.wm.SurfaceFreezer.Snapshot mSnapshot = null;
    final android.graphics.Rect mFreezeBounds = new android.graphics.Rect();

    public interface Freezable extends com.android.server.wm.SurfaceAnimator.Animatable {
        @android.annotation.Nullable
        android.view.SurfaceControl getFreezeSnapshotTarget();

        void onUnfrozen();
    }

    SurfaceFreezer(@android.annotation.NonNull com.android.server.wm.SurfaceFreezer.Freezable freezable, @android.annotation.NonNull com.android.server.wm.WindowManagerService windowManagerService) {
        this.mAnimatable = freezable;
        this.mWmService = windowManagerService;
    }

    void freeze(android.view.SurfaceControl.Transaction transaction, android.graphics.Rect rect, android.graphics.Point point, @android.annotation.Nullable android.view.SurfaceControl surfaceControl) {
        reset(transaction);
        this.mFreezeBounds.set(rect);
        this.mLeash = com.android.server.wm.SurfaceAnimator.createAnimationLeash(this.mAnimatable, this.mAnimatable.getSurfaceControl(), transaction, 2, rect.width(), rect.height(), point.x, point.y, false, this.mWmService.mTransactionFactory);
        this.mAnimatable.onAnimationLeashCreated(transaction, this.mLeash);
        if (surfaceControl == null) {
            surfaceControl = this.mAnimatable.getFreezeSnapshotTarget();
        }
        if (surfaceControl != null) {
            android.window.ScreenCapture.ScreenshotHardwareBuffer createSnapshotBufferInner = createSnapshotBufferInner(surfaceControl, rect);
            android.hardware.HardwareBuffer hardwareBuffer = createSnapshotBufferInner == null ? null : createSnapshotBufferInner.getHardwareBuffer();
            if (hardwareBuffer == null || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1) {
                android.util.Slog.w(TAG, "Failed to capture screenshot for " + this.mAnimatable);
                unfreeze(transaction);
                return;
            }
            this.mSnapshot = new com.android.server.wm.SurfaceFreezer.Snapshot(transaction, createSnapshotBufferInner, this.mLeash);
        }
    }

    android.view.SurfaceControl takeLeashForAnimation() {
        android.view.SurfaceControl surfaceControl = this.mLeash;
        this.mLeash = null;
        return surfaceControl;
    }

    @android.annotation.Nullable
    com.android.server.wm.SurfaceFreezer.Snapshot takeSnapshotForAnimation() {
        com.android.server.wm.SurfaceFreezer.Snapshot snapshot = this.mSnapshot;
        this.mSnapshot = null;
        return snapshot;
    }

    void unfreeze(android.view.SurfaceControl.Transaction transaction) {
        unfreezeInner(transaction);
        this.mAnimatable.onUnfrozen();
    }

    private void unfreezeInner(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSnapshot != null) {
            this.mSnapshot.cancelAnimation(transaction, false);
            this.mSnapshot = null;
        }
        if (this.mLeash == null) {
            return;
        }
        android.view.SurfaceControl surfaceControl = this.mLeash;
        this.mLeash = null;
        if (com.android.server.wm.SurfaceAnimator.removeLeash(transaction, this.mAnimatable, surfaceControl, true)) {
            this.mWmService.scheduleAnimationLocked();
        }
    }

    private void reset(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSnapshot != null) {
            this.mSnapshot.destroy(transaction);
            this.mSnapshot = null;
        }
        if (this.mLeash != null) {
            transaction.remove(this.mLeash);
            this.mLeash = null;
        }
    }

    void setLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mLeash != null) {
            transaction.setLayer(this.mLeash, i);
        }
    }

    void setRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i) {
        if (this.mLeash != null) {
            transaction.setRelativeLayer(this.mLeash, surfaceControl, i);
        }
    }

    boolean hasLeash() {
        return this.mLeash != null;
    }

    private static android.window.ScreenCapture.ScreenshotHardwareBuffer createSnapshotBuffer(@android.annotation.NonNull android.view.SurfaceControl surfaceControl, @android.annotation.Nullable android.graphics.Rect rect) {
        android.graphics.Rect rect2;
        if (rect == null) {
            rect2 = null;
        } else {
            rect2 = new android.graphics.Rect(rect);
            rect2.offsetTo(0, 0);
        }
        return android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect2).setCaptureSecureLayers(true).setAllowProtected(true).build());
    }

    @com.android.internal.annotations.VisibleForTesting
    android.window.ScreenCapture.ScreenshotHardwareBuffer createSnapshotBufferInner(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect) {
        return createSnapshotBuffer(surfaceControl, rect);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.GraphicBuffer createFromHardwareBufferInner(android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        return android.graphics.GraphicBuffer.createFromHardwareBuffer(screenshotHardwareBuffer.getHardwareBuffer());
    }

    class Snapshot {
        private com.android.server.wm.AnimationAdapter mAnimation;
        private android.view.SurfaceControl mSurfaceControl;

        Snapshot(android.view.SurfaceControl.Transaction transaction, android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, android.view.SurfaceControl surfaceControl) {
            android.graphics.GraphicBuffer createFromHardwareBufferInner = com.android.server.wm.SurfaceFreezer.this.createFromHardwareBufferInner(screenshotHardwareBuffer);
            this.mSurfaceControl = com.android.server.wm.SurfaceFreezer.this.mAnimatable.makeAnimationLeash().setName("snapshot anim: " + com.android.server.wm.SurfaceFreezer.this.mAnimatable.toString()).setFormat(-3).setParent(surfaceControl).setSecure(screenshotHardwareBuffer.containsSecureLayers()).setCallsite("SurfaceFreezer.Snapshot").setBLASTLayer().build();
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -2595923278763115975L, 0, null, java.lang.String.valueOf(this.mSurfaceControl));
            transaction.setBuffer(this.mSurfaceControl, createFromHardwareBufferInner);
            transaction.setColorSpace(this.mSurfaceControl, screenshotHardwareBuffer.getColorSpace());
            transaction.show(this.mSurfaceControl);
            transaction.setLayer(this.mSurfaceControl, Integer.MAX_VALUE);
        }

        void destroy(android.view.SurfaceControl.Transaction transaction) {
            if (this.mSurfaceControl == null) {
                return;
            }
            transaction.remove(this.mSurfaceControl);
            this.mSurfaceControl = null;
        }

        void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, int i) {
            cancelAnimation(transaction, true);
            this.mAnimation = animationAdapter;
            if (this.mSurfaceControl == null) {
                cancelAnimation(transaction, false);
            } else {
                this.mAnimation.startAnimation(this.mSurfaceControl, transaction, i, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.SurfaceFreezer$Snapshot$$ExternalSyntheticLambda0
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(int i2, com.android.server.wm.AnimationAdapter animationAdapter2) {
                        com.android.server.wm.SurfaceFreezer.Snapshot.lambda$startAnimation$0(i2, animationAdapter2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$startAnimation$0(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        }

        void cancelAnimation(android.view.SurfaceControl.Transaction transaction, boolean z) {
            android.view.SurfaceControl surfaceControl = this.mSurfaceControl;
            com.android.server.wm.AnimationAdapter animationAdapter = this.mAnimation;
            this.mAnimation = null;
            if (animationAdapter != null) {
                animationAdapter.onAnimationCancelled(surfaceControl);
            }
            if (!z) {
                destroy(transaction);
            }
        }
    }
}
