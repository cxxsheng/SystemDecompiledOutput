package com.android.server.wm;

/* loaded from: classes3.dex */
class NonAppWindowAnimationAdapter implements com.android.server.wm.AnimationAdapter {
    private android.view.SurfaceControl mCapturedLeash;
    private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mCapturedLeashFinishCallback;
    private long mDurationHint;
    private int mLastAnimationType;
    private long mStatusBarTransitionDelay;
    private android.view.RemoteAnimationTarget mTarget;
    private final com.android.server.wm.WindowContainer mWindowContainer;

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
        return false;
    }

    NonAppWindowAnimationAdapter(com.android.server.wm.WindowContainer windowContainer, long j, long j2) {
        this.mWindowContainer = windowContainer;
        this.mDurationHint = j;
        this.mStatusBarTransitionDelay = j2;
    }

    static android.view.RemoteAnimationTarget[] startNonAppWindowAnimations(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, int i, long j, long j2, java.util.ArrayList<com.android.server.wm.NonAppWindowAnimationAdapter> arrayList) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        if (shouldStartNonAppWindowAnimationsForKeyguardExit(i)) {
            startNonAppWindowAnimationsForKeyguardExit(windowManagerService, j, j2, arrayList2, arrayList);
        } else if (shouldAttachNavBarToApp(windowManagerService, displayContent, i)) {
            startNavigationBarWindowAnimation(displayContent, j, j2, arrayList2, arrayList);
        }
        return (android.view.RemoteAnimationTarget[]) arrayList2.toArray(new android.view.RemoteAnimationTarget[arrayList2.size()]);
    }

    static boolean shouldStartNonAppWindowAnimationsForKeyguardExit(int i) {
        return i == 20 || i == 21;
    }

    static boolean shouldAttachNavBarToApp(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, int i) {
        return (i == 8 || i == 10 || i == 12) && displayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() && windowManagerService.getRecentsAnimationController() == null && displayContent.getAsyncRotationController() == null;
    }

    private static void startNonAppWindowAnimationsForKeyguardExit(final com.android.server.wm.WindowManagerService windowManagerService, final long j, final long j2, final java.util.ArrayList<android.view.RemoteAnimationTarget> arrayList, final java.util.ArrayList<com.android.server.wm.NonAppWindowAnimationAdapter> arrayList2) {
        com.android.server.policy.WindowManagerPolicy windowManagerPolicy = windowManagerService.mPolicy;
        windowManagerService.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.NonAppWindowAnimationAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.NonAppWindowAnimationAdapter.lambda$startNonAppWindowAnimationsForKeyguardExit$0(com.android.server.wm.WindowManagerService.this, j, j2, arrayList2, arrayList, (com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startNonAppWindowAnimationsForKeyguardExit$0(com.android.server.wm.WindowManagerService windowManagerService, long j, long j2, java.util.ArrayList arrayList, java.util.ArrayList arrayList2, com.android.server.wm.WindowState windowState) {
        if (windowState.mActivityRecord == null && windowState.canBeHiddenByKeyguard() && windowState.wouldBeVisibleIfPolicyIgnored() && !windowState.isVisible() && windowState != windowManagerService.mRoot.getCurrentInputMethodWindow()) {
            com.android.server.wm.NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter = new com.android.server.wm.NonAppWindowAnimationAdapter(windowState, j, j2);
            arrayList.add(nonAppWindowAnimationAdapter);
            windowState.startAnimation(windowState.getPendingTransaction(), nonAppWindowAnimationAdapter, false, 16);
            arrayList2.add(nonAppWindowAnimationAdapter.createRemoteAnimationTarget());
        }
    }

    private static void startNavigationBarWindowAnimation(com.android.server.wm.DisplayContent displayContent, long j, long j2, java.util.ArrayList<android.view.RemoteAnimationTarget> arrayList, java.util.ArrayList<com.android.server.wm.NonAppWindowAnimationAdapter> arrayList2) {
        com.android.server.wm.WindowState navigationBar = displayContent.getDisplayPolicy().getNavigationBar();
        com.android.server.wm.NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter = new com.android.server.wm.NonAppWindowAnimationAdapter(navigationBar.mToken, j, j2);
        arrayList2.add(nonAppWindowAnimationAdapter);
        navigationBar.mToken.startAnimation(navigationBar.mToken.getPendingTransaction(), nonAppWindowAnimationAdapter, false, 16);
        arrayList.add(nonAppWindowAnimationAdapter.createRemoteAnimationTarget());
    }

    android.view.RemoteAnimationTarget createRemoteAnimationTarget() {
        this.mTarget = new android.view.RemoteAnimationTarget(-1, -1, getLeash(), false, new android.graphics.Rect(), (android.graphics.Rect) null, this.mWindowContainer.getPrefixOrderIndex(), this.mWindowContainer.getLastSurfacePosition(), this.mWindowContainer.getBounds(), (android.graphics.Rect) null, this.mWindowContainer.getWindowConfiguration(), true, (android.view.SurfaceControl) null, (android.graphics.Rect) null, (android.app.ActivityManager.RunningTaskInfo) null, false, this.mWindowContainer.getWindowType());
        return this.mTarget;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 3788905348567806832L, 0, null, null);
        this.mCapturedLeash = surfaceControl;
        this.mCapturedLeashFinishCallback = onAnimationFinishedCallback;
        this.mLastAnimationType = i;
    }

    com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback getLeashFinishedCallback() {
        return this.mCapturedLeashFinishCallback;
    }

    int getLastAnimationType() {
        return this.mLastAnimationType;
    }

    com.android.server.wm.WindowContainer getWindowContainer() {
        return this.mWindowContainer;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
        return this.mDurationHint;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
        return android.os.SystemClock.uptimeMillis() + this.mStatusBarTransitionDelay;
    }

    android.view.SurfaceControl getLeash() {
        return this.mCapturedLeash;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 705955074330737483L, 0, null, null);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("windowContainer=");
        printWriter.println(this.mWindowContainer);
        if (this.mTarget != null) {
            printWriter.print(str);
            printWriter.println("Target:");
            this.mTarget.dump(printWriter, str + "  ");
            return;
        }
        printWriter.print(str);
        printWriter.println("Target: null");
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268034L);
        if (this.mTarget != null) {
            this.mTarget.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.end(start);
    }
}
