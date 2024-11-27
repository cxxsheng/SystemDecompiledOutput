package com.android.server.wm;

/* loaded from: classes3.dex */
class WallpaperAnimationAdapter implements com.android.server.wm.AnimationAdapter {
    private static final java.lang.String TAG = "WallpaperAnimationAdapter";
    private java.util.function.Consumer<com.android.server.wm.WallpaperAnimationAdapter> mAnimationCanceledRunnable;
    private android.view.SurfaceControl mCapturedLeash;
    private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mCapturedLeashFinishCallback;
    private long mDurationHint;
    private int mLastAnimationType;
    private long mStatusBarTransitionDelay;
    private android.view.RemoteAnimationTarget mTarget;
    private final com.android.server.wm.WallpaperWindowToken mWallpaperToken;

    WallpaperAnimationAdapter(com.android.server.wm.WallpaperWindowToken wallpaperWindowToken, long j, long j2, java.util.function.Consumer<com.android.server.wm.WallpaperAnimationAdapter> consumer) {
        this.mWallpaperToken = wallpaperWindowToken;
        this.mDurationHint = j;
        this.mStatusBarTransitionDelay = j2;
        this.mAnimationCanceledRunnable = consumer;
    }

    public static android.view.RemoteAnimationTarget[] startWallpaperAnimations(com.android.server.wm.DisplayContent displayContent, final long j, final long j2, final java.util.function.Consumer<com.android.server.wm.WallpaperAnimationAdapter> consumer, final java.util.ArrayList<com.android.server.wm.WallpaperAnimationAdapter> arrayList) {
        if (!shouldStartWallpaperAnimation(displayContent)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 1964980935866463086L, 0, null, java.lang.String.valueOf(displayContent));
            return new android.view.RemoteAnimationTarget[0];
        }
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        displayContent.forAllWallpaperWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WallpaperAnimationAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WallpaperAnimationAdapter.lambda$startWallpaperAnimations$0(j, j2, consumer, arrayList2, arrayList, (com.android.server.wm.WallpaperWindowToken) obj);
            }
        });
        return (android.view.RemoteAnimationTarget[]) arrayList2.toArray(new android.view.RemoteAnimationTarget[arrayList2.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startWallpaperAnimations$0(long j, long j2, java.util.function.Consumer consumer, java.util.ArrayList arrayList, java.util.ArrayList arrayList2, com.android.server.wm.WallpaperWindowToken wallpaperWindowToken) {
        com.android.server.wm.WallpaperAnimationAdapter wallpaperAnimationAdapter = new com.android.server.wm.WallpaperAnimationAdapter(wallpaperWindowToken, j, j2, consumer);
        wallpaperWindowToken.startAnimation(wallpaperWindowToken.getPendingTransaction(), wallpaperAnimationAdapter, false, 16);
        arrayList.add(wallpaperAnimationAdapter.createRemoteAnimationTarget());
        arrayList2.add(wallpaperAnimationAdapter);
    }

    static boolean shouldStartWallpaperAnimation(com.android.server.wm.DisplayContent displayContent) {
        return displayContent.mWallpaperController.isWallpaperVisible();
    }

    android.view.RemoteAnimationTarget createRemoteAnimationTarget() {
        this.mTarget = new android.view.RemoteAnimationTarget(-1, -1, getLeash(), false, (android.graphics.Rect) null, (android.graphics.Rect) null, this.mWallpaperToken.getPrefixOrderIndex(), new android.graphics.Point(), (android.graphics.Rect) null, (android.graphics.Rect) null, this.mWallpaperToken.getWindowConfiguration(), true, (android.view.SurfaceControl) null, (android.graphics.Rect) null, (android.app.ActivityManager.RunningTaskInfo) null, false);
        return this.mTarget;
    }

    android.view.SurfaceControl getLeash() {
        return this.mCapturedLeash;
    }

    com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback getLeashFinishedCallback() {
        return this.mCapturedLeashFinishCallback;
    }

    int getLastAnimationType() {
        return this.mLastAnimationType;
    }

    com.android.server.wm.WallpaperWindowToken getToken() {
        return this.mWallpaperToken;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
        return false;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8131665298937888044L, 0, null, null);
        transaction.setLayer(surfaceControl, this.mWallpaperToken.getPrefixOrderIndex());
        this.mCapturedLeash = surfaceControl;
        this.mCapturedLeashFinishCallback = onAnimationFinishedCallback;
        this.mLastAnimationType = i;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8030745595351281943L, 0, null, null);
        this.mAnimationCanceledRunnable.accept(this);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
        return this.mDurationHint;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
        return android.os.SystemClock.uptimeMillis() + this.mStatusBarTransitionDelay;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("token=");
        printWriter.println(this.mWallpaperToken);
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