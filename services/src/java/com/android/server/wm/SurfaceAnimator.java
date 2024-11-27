package com.android.server.wm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes3.dex */
public class SurfaceAnimator {
    public static final int ANIMATION_TYPE_ALL = -1;
    public static final int ANIMATION_TYPE_APP_TRANSITION = 1;
    public static final int ANIMATION_TYPE_DIMMER = 4;
    public static final int ANIMATION_TYPE_INSETS_CONTROL = 32;
    public static final int ANIMATION_TYPE_NONE = 0;
    public static final int ANIMATION_TYPE_PREDICT_BACK = 256;
    public static final int ANIMATION_TYPE_RECENTS = 8;
    public static final int ANIMATION_TYPE_SCREEN_ROTATION = 2;
    public static final int ANIMATION_TYPE_STARTING_REVEAL = 128;
    public static final int ANIMATION_TYPE_TOKEN_TRANSFORM = 64;
    public static final int ANIMATION_TYPE_WINDOW_ANIMATION = 16;
    private static final java.lang.String TAG = "WindowManager";

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.SurfaceAnimator.Animatable mAnimatable;
    private com.android.server.wm.AnimationAdapter mAnimation;

    @android.annotation.Nullable
    private java.lang.Runnable mAnimationCancelledCallback;
    private boolean mAnimationFinished;
    private boolean mAnimationStartDelayed;
    private int mAnimationType;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mInnerAnimationFinishedCallback;

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl mLeash;
    private final com.android.server.wm.WindowManagerService mService;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.SurfaceFreezer.Snapshot mSnapshot;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mStaticAnimationFinishedCallback;

    @android.annotation.Nullable
    private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mSurfaceAnimationFinishedCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AnimationType {
    }

    @com.android.internal.annotations.VisibleForTesting
    public interface OnAnimationFinishedCallback {
        void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter);
    }

    SurfaceAnimator(com.android.server.wm.SurfaceAnimator.Animatable animatable, @android.annotation.Nullable com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, com.android.server.wm.WindowManagerService windowManagerService) {
        this.mAnimatable = animatable;
        this.mService = windowManagerService;
        this.mStaticAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mInnerAnimationFinishedCallback = getFinishedCallback(onAnimationFinishedCallback);
    }

    private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback getFinishedCallback(@android.annotation.Nullable final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        return new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
                com.android.server.wm.SurfaceAnimator.this.lambda$getFinishedCallback$1(onAnimationFinishedCallback, i, animationAdapter);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishedCallback$1(final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, final int i, final com.android.server.wm.AnimationAdapter animationAdapter) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.SurfaceAnimator remove = this.mService.mAnimationTransferMap.remove(animationAdapter);
                if (remove != null) {
                    remove.mInnerAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    if (animationAdapter != this.mAnimation) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.SurfaceAnimator.this.lambda$getFinishedCallback$0(animationAdapter, onAnimationFinishedCallback, i);
                        }
                    };
                    if (!this.mAnimatable.shouldDeferAnimationFinish(runnable) && !animationAdapter.shouldDeferAnimationFinish(runnable)) {
                        runnable.run();
                    }
                    this.mAnimationFinished = true;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishedCallback$0(com.android.server.wm.AnimationAdapter animationAdapter, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, int i) {
        if (animationAdapter != this.mAnimation) {
            return;
        }
        com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback2 = this.mSurfaceAnimationFinishedCallback;
        reset(this.mAnimatable.getSyncTransaction(), true);
        if (onAnimationFinishedCallback != null) {
            onAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
        }
        if (onAnimationFinishedCallback2 != null) {
            onAnimationFinishedCallback2.onAnimationFinished(i, animationAdapter);
        }
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, @android.annotation.Nullable com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, @android.annotation.Nullable java.lang.Runnable runnable, @android.annotation.Nullable com.android.server.wm.AnimationAdapter animationAdapter2, @android.annotation.Nullable com.android.server.wm.SurfaceFreezer surfaceFreezer) {
        cancelAnimation(transaction, true, true);
        this.mAnimation = animationAdapter;
        this.mAnimationType = i;
        this.mSurfaceAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mAnimationCancelledCallback = runnable;
        android.view.SurfaceControl surfaceControl = this.mAnimatable.getSurfaceControl();
        if (surfaceControl != null) {
            this.mLeash = surfaceFreezer != null ? surfaceFreezer.takeLeashForAnimation() : null;
            if (this.mLeash == null) {
                this.mLeash = createAnimationLeash(this.mAnimatable, surfaceControl, transaction, i, this.mAnimatable.getSurfaceWidth(), this.mAnimatable.getSurfaceHeight(), 0, 0, z, this.mService.mTransactionFactory);
                this.mAnimatable.onAnimationLeashCreated(transaction, this.mLeash);
            }
            this.mAnimatable.onLeashAnimationStarting(transaction, this.mLeash);
            if (!this.mAnimationStartDelayed) {
                this.mAnimation.startAnimation(this.mLeash, transaction, i, this.mInnerAnimationFinishedCallback);
                if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM)) {
                    java.io.StringWriter stringWriter = new java.io.StringWriter();
                    this.mAnimation.dump(new java.io.PrintWriter(stringWriter), "");
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 1371702561758591499L, 0, null, java.lang.String.valueOf(this.mAnimatable), java.lang.String.valueOf(stringWriter));
                }
                if (animationAdapter2 != null) {
                    this.mSnapshot = surfaceFreezer.takeSnapshotForAnimation();
                    if (this.mSnapshot == null) {
                        android.util.Slog.e(TAG, "No snapshot target to start animation on for " + this.mAnimatable);
                        return;
                    }
                    this.mSnapshot.startAnimation(transaction, animationAdapter2, i);
                    return;
                }
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -820649637734629482L, 0, null, java.lang.String.valueOf(this.mAnimatable));
            return;
        }
        android.util.Slog.w(TAG, "Unable to start animation, surface is null or no children.");
        cancelAnimation();
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i) {
        startAnimation(transaction, animationAdapter, z, i, null, null, null, null);
    }

    void startDelayingAnimationStart() {
        if (!isAnimating()) {
            this.mAnimationStartDelayed = true;
        }
    }

    void endDelayingAnimationStart() {
        boolean z = this.mAnimationStartDelayed;
        this.mAnimationStartDelayed = false;
        if (z && this.mAnimation != null) {
            this.mAnimation.startAnimation(this.mLeash, this.mAnimatable.getSyncTransaction(), this.mAnimationType, this.mInnerAnimationFinishedCallback);
            this.mAnimatable.commitPendingTransaction();
        }
    }

    boolean isAnimating() {
        return this.mAnimation != null;
    }

    int getAnimationType() {
        return this.mAnimationType;
    }

    com.android.server.wm.AnimationAdapter getAnimation() {
        return this.mAnimation;
    }

    void cancelAnimation() {
        cancelAnimation(this.mAnimatable.getSyncTransaction(), false, true);
        this.mAnimatable.commitPendingTransaction();
    }

    void setLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        transaction.setLayer(this.mLeash != null ? this.mLeash : this.mAnimatable.getSurfaceControl(), i);
    }

    void setRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i) {
        transaction.setRelativeLayer(this.mLeash != null ? this.mLeash : this.mAnimatable.getSurfaceControl(), surfaceControl, i);
    }

    void reparent(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.reparent(this.mLeash != null ? this.mLeash : this.mAnimatable.getSurfaceControl(), surfaceControl);
    }

    boolean hasLeash() {
        return this.mLeash != null;
    }

    void transferAnimation(com.android.server.wm.SurfaceAnimator surfaceAnimator) {
        if (surfaceAnimator.mLeash == null) {
            return;
        }
        android.view.SurfaceControl surfaceControl = this.mAnimatable.getSurfaceControl();
        android.view.SurfaceControl animationLeashParent = this.mAnimatable.getAnimationLeashParent();
        if (surfaceControl == null || animationLeashParent == null) {
            android.util.Slog.w(TAG, "Unable to transfer animation, surface or parent is null");
            cancelAnimation();
            return;
        }
        if (surfaceAnimator.mAnimationFinished) {
            android.util.Slog.w(TAG, "Unable to transfer animation, because " + surfaceAnimator + " animation is finished");
            return;
        }
        endDelayingAnimationStart();
        android.view.SurfaceControl.Transaction syncTransaction = this.mAnimatable.getSyncTransaction();
        cancelAnimation(syncTransaction, true, true);
        this.mLeash = surfaceAnimator.mLeash;
        this.mAnimation = surfaceAnimator.mAnimation;
        this.mAnimationType = surfaceAnimator.mAnimationType;
        this.mSurfaceAnimationFinishedCallback = surfaceAnimator.mSurfaceAnimationFinishedCallback;
        this.mAnimationCancelledCallback = surfaceAnimator.mAnimationCancelledCallback;
        surfaceAnimator.cancelAnimation(syncTransaction, false, false);
        syncTransaction.reparent(surfaceControl, this.mLeash);
        syncTransaction.reparent(this.mLeash, animationLeashParent);
        this.mAnimatable.onAnimationLeashCreated(syncTransaction, this.mLeash);
        this.mService.mAnimationTransferMap.put(this.mAnimation, this);
    }

    boolean isAnimationStartDelayed() {
        return this.mAnimationStartDelayed;
    }

    private void cancelAnimation(android.view.SurfaceControl.Transaction transaction, boolean z, boolean z2) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -5370506662233296228L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this.mAnimatable));
        android.view.SurfaceControl surfaceControl = this.mLeash;
        com.android.server.wm.AnimationAdapter animationAdapter = this.mAnimation;
        int i = this.mAnimationType;
        com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback = this.mSurfaceAnimationFinishedCallback;
        java.lang.Runnable runnable = this.mAnimationCancelledCallback;
        com.android.server.wm.SurfaceFreezer.Snapshot snapshot = this.mSnapshot;
        reset(transaction, false);
        if (animationAdapter != null) {
            if (!this.mAnimationStartDelayed && z2) {
                animationAdapter.onAnimationCancelled(surfaceControl);
                if (runnable != null) {
                    runnable.run();
                }
            }
            if (!z) {
                if (this.mStaticAnimationFinishedCallback != null) {
                    this.mStaticAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
                }
                if (onAnimationFinishedCallback != null) {
                    onAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
                }
            }
        }
        if (z2) {
            if (snapshot != null) {
                snapshot.cancelAnimation(transaction, false);
            }
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
                this.mService.scheduleAnimationLocked();
            }
        }
        if (!z) {
            this.mAnimationStartDelayed = false;
        }
    }

    private void reset(android.view.SurfaceControl.Transaction transaction, boolean z) {
        this.mService.mAnimationTransferMap.remove(this.mAnimation);
        this.mAnimation = null;
        this.mSurfaceAnimationFinishedCallback = null;
        this.mAnimationType = 0;
        com.android.server.wm.SurfaceFreezer.Snapshot snapshot = this.mSnapshot;
        this.mSnapshot = null;
        if (snapshot != null) {
            snapshot.cancelAnimation(transaction, !z);
        }
        if (this.mLeash == null) {
            return;
        }
        android.view.SurfaceControl surfaceControl = this.mLeash;
        this.mLeash = null;
        boolean removeLeash = removeLeash(transaction, this.mAnimatable, surfaceControl, z);
        this.mAnimationFinished = false;
        if (removeLeash) {
            this.mService.scheduleAnimationLocked();
        }
    }

    static boolean removeLeash(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.SurfaceAnimator.Animatable animatable, @android.annotation.NonNull android.view.SurfaceControl surfaceControl, boolean z) {
        android.view.SurfaceControl surfaceControl2 = animatable.getSurfaceControl();
        android.view.SurfaceControl parentSurfaceControl = animatable.getParentSurfaceControl();
        android.view.SurfaceControl animationLeash = animatable.getAnimationLeash();
        boolean z2 = false;
        boolean z3 = surfaceControl2 != null && (animationLeash == null || animationLeash.equals(surfaceControl));
        if (z3) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -3045933321063743917L, 0, null, java.lang.String.valueOf(parentSurfaceControl), java.lang.String.valueOf(animatable));
            if (surfaceControl2.isValid() && parentSurfaceControl != null && parentSurfaceControl.isValid()) {
                transaction.reparent(surfaceControl2, parentSurfaceControl);
                z2 = true;
            }
        }
        if (z) {
            transaction.remove(surfaceControl);
            z2 = true;
        }
        if (!z3) {
            return z2;
        }
        animatable.onAnimationLeashLost(transaction);
        return true;
    }

    static android.view.SurfaceControl createAnimationLeash(com.android.server.wm.SurfaceAnimator.Animatable animatable, android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, int i2, int i3, int i4, int i5, boolean z, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -855083149623806053L, 0, null, java.lang.String.valueOf(animatable));
        android.view.SurfaceControl build = animatable.makeAnimationLeash().setParent(animatable.getAnimationLeashParent()).setName(surfaceControl + " - animation-leash of " + animationTypeToString(i)).setHidden(z).setEffectLayer().setCallsite("SurfaceAnimator.createAnimationLeash").build();
        transaction.setWindowCrop(build, i2, i3);
        transaction.setPosition(build, (float) i4, (float) i5);
        transaction.show(build);
        transaction.setAlpha(build, z ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : 1.0f);
        transaction.reparent(surfaceControl, build);
        return build;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mAnimation != null) {
            this.mAnimation.dumpDebug(protoOutputStream, 1146756268035L);
        }
        if (this.mLeash != null) {
            this.mLeash.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1133871366146L, this.mAnimationStartDelayed);
        protoOutputStream.end(start);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mLeash=");
        printWriter.print(this.mLeash);
        printWriter.print(" mAnimationType=" + animationTypeToString(this.mAnimationType));
        printWriter.println(this.mAnimationStartDelayed ? " mAnimationStartDelayed=true" : "");
        printWriter.print(str);
        printWriter.print("Animation: ");
        printWriter.println(this.mAnimation);
        if (this.mAnimation != null) {
            this.mAnimation.dump(printWriter, str + "  ");
        }
    }

    static java.lang.String animationTypeToString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "app_transition";
            case 2:
                return "screen_rotation";
            case 4:
                return "dimmer";
            case 8:
                return "recents_animation";
            case 16:
                return "window_animation";
            case 32:
                return "insets_animation";
            case 64:
                return "token_transform";
            case 128:
                return "starting_reveal";
            case 256:
                return "predict_back";
            default:
                return "unknown type:" + i;
        }
    }

    interface Animatable {
        void commitPendingTransaction();

        @android.annotation.Nullable
        android.view.SurfaceControl getAnimationLeashParent();

        @android.annotation.Nullable
        android.view.SurfaceControl getParentSurfaceControl();

        @android.annotation.NonNull
        android.view.SurfaceControl.Transaction getPendingTransaction();

        @android.annotation.Nullable
        android.view.SurfaceControl getSurfaceControl();

        int getSurfaceHeight();

        int getSurfaceWidth();

        @android.annotation.NonNull
        android.view.SurfaceControl.Transaction getSyncTransaction();

        android.view.SurfaceControl.Builder makeAnimationLeash();

        void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl);

        void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction);

        default void onLeashAnimationStarting(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        }

        @android.annotation.Nullable
        default android.view.SurfaceControl getAnimationLeash() {
            return null;
        }

        default boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
            return false;
        }
    }
}
