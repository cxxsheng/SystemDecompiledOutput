package com.android.server.wm;

/* loaded from: classes3.dex */
class RemoteAnimationController implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "WindowManager";
    private static final long TIMEOUT_MS = 10000;
    private boolean mCanceled;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private com.android.server.wm.RemoteAnimationController.FinishedCallback mFinishedCallback;
    private final android.os.Handler mHandler;
    private final boolean mIsActivityEmbedding;
    private boolean mIsFinishing;
    private boolean mLinkedToDeathOfRunner;

    @android.annotation.Nullable
    private java.lang.Runnable mOnRemoteAnimationReady;
    private final android.view.RemoteAnimationAdapter mRemoteAnimationAdapter;
    private final com.android.server.wm.WindowManagerService mService;
    private final java.util.ArrayList<com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord> mPendingAnimations = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.WallpaperAnimationAdapter> mPendingWallpaperAnimations = new java.util.ArrayList<>();

    @com.android.internal.annotations.VisibleForTesting
    final java.util.ArrayList<com.android.server.wm.NonAppWindowAnimationAdapter> mPendingNonAppAnimations = new java.util.ArrayList<>();
    private final java.lang.Runnable mTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.RemoteAnimationController.this.lambda$new$0();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        cancelAnimation("timeoutRunnable");
    }

    RemoteAnimationController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.os.Handler handler, boolean z) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mRemoteAnimationAdapter = remoteAnimationAdapter;
        this.mHandler = handler;
        this.mIsActivityEmbedding = z;
    }

    com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord createRemoteAnimationRecord(com.android.server.wm.WindowContainer windowContainer, android.graphics.Point point, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, boolean z) {
        return createRemoteAnimationRecord(windowContainer, point, rect, rect2, rect3, z, rect3 != null);
    }

    com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord createRemoteAnimationRecord(com.android.server.wm.WindowContainer windowContainer, android.graphics.Point point, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, boolean z, boolean z2) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -5444412205083968021L, 0, null, java.lang.String.valueOf(windowContainer));
        com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord = new com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord(windowContainer, point, rect, rect2, rect3, z, z2);
        this.mPendingAnimations.add(remoteAnimationRecord);
        return remoteAnimationRecord;
    }

    void setOnRemoteAnimationReady(@android.annotation.Nullable java.lang.Runnable runnable) {
        this.mOnRemoteAnimationReady = runnable;
    }

    public boolean isFromActivityEmbedding() {
        return this.mIsActivityEmbedding;
    }

    void goodToGo(final int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6986037643494242400L, 0, null, null);
        if (this.mCanceled) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -1902984034737899928L, 0, null, null);
            onAnimationFinished();
            invokeAnimationCancelled("already_cancelled");
            return;
        }
        this.mHandler.postDelayed(this.mTimeoutRunnable, (long) (this.mService.getCurrentAnimatorScale() * 10000.0f));
        this.mFinishedCallback = new com.android.server.wm.RemoteAnimationController.FinishedCallback(this);
        final android.view.RemoteAnimationTarget[] createAppAnimations = createAppAnimations();
        if (createAppAnimations.length == 0 && !com.android.server.wm.AppTransition.isKeyguardOccludeTransitOld(i)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6727618365838540075L, 1, null, java.lang.Long.valueOf(this.mPendingAnimations.size()));
            onAnimationFinished();
            invokeAnimationCancelled("no_app_targets");
            return;
        }
        if (this.mOnRemoteAnimationReady != null) {
            this.mOnRemoteAnimationReady.run();
            this.mOnRemoteAnimationReady = null;
        }
        final android.view.RemoteAnimationTarget[] createWallpaperAnimations = createWallpaperAnimations();
        final android.view.RemoteAnimationTarget[] createNonAppWindowAnimations = createNonAppWindowAnimations(i);
        this.mService.mAnimator.addAfterPrepareSurfacesRunnable(new java.lang.Runnable() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.RemoteAnimationController.this.lambda$goodToGo$1(i, createAppAnimations, createWallpaperAnimations, createNonAppWindowAnimations);
            }
        });
        setRunningRemoteAnimation(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goodToGo$1(int i, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3) {
        try {
            linkToDeathOfRunner();
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -2525509826755873433L, 84, null, java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(i)), java.lang.Long.valueOf(remoteAnimationTargetArr.length), java.lang.Long.valueOf(remoteAnimationTargetArr2.length), java.lang.Long.valueOf(remoteAnimationTargetArr3.length));
            if (com.android.server.wm.AppTransition.isKeyguardOccludeTransitOld(i)) {
                com.android.server.wm.EventLogTags.writeWmSetKeyguardOccluded(i == 23 ? 0 : 1, 1, i, "onAnimationStart");
            }
            this.mRemoteAnimationAdapter.getRunner().onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, this.mFinishedCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to start remote animation", e);
            onAnimationFinished();
        }
        if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -1148281153370899511L, 0, null, null);
            writeStartDebugStatement();
        }
    }

    void cancelAnimation(java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7501495587927045391L, 0, null, java.lang.String.valueOf(str));
        synchronized (this.mService.getWindowManagerLock()) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mCanceled = true;
                onAnimationFinished();
                invokeAnimationCancelled(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void writeStartDebugStatement() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -1424368765415574722L, 0, null, null);
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter);
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            this.mPendingAnimations.get(size).mAdapter.dump(fastPrintWriter, "");
        }
        fastPrintWriter.close();
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -2676700429940607853L, 0, null, java.lang.String.valueOf(stringWriter.toString()));
    }

    private android.view.RemoteAnimationTarget[] createAppAnimations() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7094394833775573933L, 0, null, null);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord = this.mPendingAnimations.get(size);
            android.view.RemoteAnimationTarget createRemoteAnimationTarget = remoteAnimationRecord.createRemoteAnimationTarget();
            if (createRemoteAnimationTarget != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -4411070227420990074L, 0, null, java.lang.String.valueOf(remoteAnimationRecord.mWindowContainer));
                arrayList.add(createRemoteAnimationTarget);
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -4411631520586057580L, 0, null, java.lang.String.valueOf(remoteAnimationRecord.mWindowContainer));
                if (remoteAnimationRecord.mAdapter != null && remoteAnimationRecord.mAdapter.mCapturedFinishCallback != null) {
                    remoteAnimationRecord.mAdapter.mCapturedFinishCallback.onAnimationFinished(remoteAnimationRecord.mAdapter.mAnimationType, remoteAnimationRecord.mAdapter);
                }
                if (remoteAnimationRecord.mThumbnailAdapter != null && remoteAnimationRecord.mThumbnailAdapter.mCapturedFinishCallback != null) {
                    remoteAnimationRecord.mThumbnailAdapter.mCapturedFinishCallback.onAnimationFinished(remoteAnimationRecord.mThumbnailAdapter.mAnimationType, remoteAnimationRecord.mThumbnailAdapter);
                }
                this.mPendingAnimations.remove(size);
            }
        }
        return (android.view.RemoteAnimationTarget[]) arrayList.toArray(new android.view.RemoteAnimationTarget[arrayList.size()]);
    }

    private android.view.RemoteAnimationTarget[] createWallpaperAnimations() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -7002230949892506736L, 0, null, null);
        return com.android.server.wm.WallpaperAnimationAdapter.startWallpaperAnimations(this.mDisplayContent, this.mRemoteAnimationAdapter.getDuration(), this.mRemoteAnimationAdapter.getStatusBarTransitionDelay(), new java.util.function.Consumer() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RemoteAnimationController.this.lambda$createWallpaperAnimations$2((com.android.server.wm.WallpaperAnimationAdapter) obj);
            }
        }, this.mPendingWallpaperAnimations);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createWallpaperAnimations$2(com.android.server.wm.WallpaperAnimationAdapter wallpaperAnimationAdapter) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPendingWallpaperAnimations.remove(wallpaperAnimationAdapter);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private android.view.RemoteAnimationTarget[] createNonAppWindowAnimations(int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8743612568733301175L, 0, null, null);
        return com.android.server.wm.NonAppWindowAnimationAdapter.startNonAppWindowAnimations(this.mService, this.mDisplayContent, i, this.mRemoteAnimationAdapter.getDuration(), this.mRemoteAnimationAdapter.getStatusBarTransitionDelay(), this.mPendingNonAppAnimations);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationFinished() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -2716313493239418198L, 1, null, java.lang.Long.valueOf(this.mPendingAnimations.size()));
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mIsFinishing = true;
                unlinkToDeathOfRunner();
                releaseFinishedCallback();
                try {
                    try {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7221400292415257709L, 0, null, null);
                        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
                            com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord = this.mPendingAnimations.get(size);
                            if (remoteAnimationRecord.mAdapter != null) {
                                remoteAnimationRecord.mAdapter.mCapturedFinishCallback.onAnimationFinished(remoteAnimationRecord.mAdapter.mAnimationType, remoteAnimationRecord.mAdapter);
                            }
                            if (remoteAnimationRecord.mThumbnailAdapter != null) {
                                remoteAnimationRecord.mThumbnailAdapter.mCapturedFinishCallback.onAnimationFinished(remoteAnimationRecord.mThumbnailAdapter.mAnimationType, remoteAnimationRecord.mThumbnailAdapter);
                            }
                            this.mPendingAnimations.remove(size);
                            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7483194715776694698L, 0, null, java.lang.String.valueOf(remoteAnimationRecord.mWindowContainer));
                        }
                        for (int size2 = this.mPendingWallpaperAnimations.size() - 1; size2 >= 0; size2--) {
                            com.android.server.wm.WallpaperAnimationAdapter wallpaperAnimationAdapter = this.mPendingWallpaperAnimations.get(size2);
                            wallpaperAnimationAdapter.getLeashFinishedCallback().onAnimationFinished(wallpaperAnimationAdapter.getLastAnimationType(), wallpaperAnimationAdapter);
                            this.mPendingWallpaperAnimations.remove(size2);
                            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6697982664439247822L, 0, null, java.lang.String.valueOf(wallpaperAnimationAdapter.getToken()));
                        }
                        for (int size3 = this.mPendingNonAppAnimations.size() - 1; size3 >= 0; size3--) {
                            com.android.server.wm.NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter = this.mPendingNonAppAnimations.get(size3);
                            nonAppWindowAnimationAdapter.getLeashFinishedCallback().onAnimationFinished(nonAppWindowAnimationAdapter.getLastAnimationType(), nonAppWindowAnimationAdapter);
                            this.mPendingNonAppAnimations.remove(size3);
                            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 6938838346517131964L, 0, null, java.lang.String.valueOf(nonAppWindowAnimationAdapter.getWindowContainer()));
                        }
                        this.mIsFinishing = false;
                        this.mDisplayContent.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                ((com.android.server.wm.ActivityRecord) obj).setDropInputForAnimation(false);
                            }
                        });
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Failed to finish remote animation", e);
                        throw e;
                    }
                } catch (java.lang.Throwable th) {
                    this.mIsFinishing = false;
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        setRunningRemoteAnimation(false);
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -3880290251819699866L, 0, null, null);
    }

    private void invokeAnimationCancelled(java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7501495587927045391L, 0, null, java.lang.String.valueOf(str));
        try {
            this.mRemoteAnimationAdapter.getRunner().onAnimationCancelled();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to notify cancel", e);
        }
        this.mOnRemoteAnimationReady = null;
    }

    private void releaseFinishedCallback() {
        if (this.mFinishedCallback != null) {
            this.mFinishedCallback.release();
            this.mFinishedCallback = null;
        }
    }

    private void setRunningRemoteAnimation(boolean z) {
        int callingPid = this.mRemoteAnimationAdapter.getCallingPid();
        int callingUid = this.mRemoteAnimationAdapter.getCallingUid();
        if (callingPid == 0) {
            throw new java.lang.RuntimeException("Calling pid of remote animation was null");
        }
        com.android.server.wm.WindowProcessController processController = this.mService.mAtmService.getProcessController(callingPid, callingUid);
        if (processController == null) {
            android.util.Slog.w(TAG, "Unable to find process with pid=" + callingPid + " uid=" + callingUid);
            return;
        }
        processController.setRunningRemoteAnimation(z);
    }

    private void linkToDeathOfRunner() throws android.os.RemoteException {
        if (!this.mLinkedToDeathOfRunner) {
            this.mRemoteAnimationAdapter.getRunner().asBinder().linkToDeath(this, 0);
            this.mLinkedToDeathOfRunner = true;
        }
    }

    private void unlinkToDeathOfRunner() {
        if (this.mLinkedToDeathOfRunner) {
            this.mRemoteAnimationAdapter.getRunner().asBinder().unlinkToDeath(this, 0);
            this.mLinkedToDeathOfRunner = false;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        cancelAnimation("binderDied");
    }

    private static final class FinishedCallback extends android.view.IRemoteAnimationFinishedCallback.Stub {
        com.android.server.wm.RemoteAnimationController mOuter;

        FinishedCallback(com.android.server.wm.RemoteAnimationController remoteAnimationController) {
            this.mOuter = remoteAnimationController;
        }

        public void onAnimationFinished() throws android.os.RemoteException {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -7169244688499657832L, 0, null, java.lang.String.valueOf(this.mOuter));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mOuter != null) {
                    this.mOuter.onAnimationFinished();
                    this.mOuter = null;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        void release() {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 3923111589554171989L, 0, null, java.lang.String.valueOf(this.mOuter));
            this.mOuter = null;
        }
    }

    public class RemoteAnimationRecord {
        com.android.server.wm.RemoteAnimationController.RemoteAnimationAdapterWrapper mAdapter;
        int mBackdropColor = 0;
        private int mMode = 2;
        final boolean mShowBackdrop;
        final android.graphics.Rect mStartBounds;
        android.view.RemoteAnimationTarget mTarget;
        com.android.server.wm.RemoteAnimationController.RemoteAnimationAdapterWrapper mThumbnailAdapter;
        final com.android.server.wm.WindowContainer mWindowContainer;

        RemoteAnimationRecord(com.android.server.wm.WindowContainer windowContainer, android.graphics.Point point, android.graphics.Rect rect, android.graphics.Rect rect2, @android.annotation.Nullable android.graphics.Rect rect3, boolean z, boolean z2) {
            this.mThumbnailAdapter = null;
            this.mWindowContainer = windowContainer;
            this.mShowBackdrop = z;
            if (rect3 != null) {
                this.mStartBounds = new android.graphics.Rect(rect3);
                this.mAdapter = com.android.server.wm.RemoteAnimationController.this.new RemoteAnimationAdapterWrapper(this, point, rect, rect2, this.mStartBounds, this.mShowBackdrop);
                if (z2 && com.android.server.wm.RemoteAnimationController.this.mRemoteAnimationAdapter.getChangeNeedsSnapshot()) {
                    android.graphics.Rect rect4 = new android.graphics.Rect(rect3);
                    rect4.offsetTo(0, 0);
                    this.mThumbnailAdapter = com.android.server.wm.RemoteAnimationController.this.new RemoteAnimationAdapterWrapper(this, new android.graphics.Point(0, 0), rect4, rect3, new android.graphics.Rect(), this.mShowBackdrop);
                    return;
                }
                return;
            }
            this.mAdapter = com.android.server.wm.RemoteAnimationController.this.new RemoteAnimationAdapterWrapper(this, point, rect, rect2, new android.graphics.Rect(), this.mShowBackdrop);
            this.mStartBounds = null;
        }

        void setBackDropColor(int i) {
            this.mBackdropColor = i;
        }

        android.view.RemoteAnimationTarget createRemoteAnimationTarget() {
            if (this.mAdapter == null || this.mAdapter.mCapturedFinishCallback == null || this.mAdapter.mCapturedLeash == null) {
                return null;
            }
            this.mTarget = this.mWindowContainer.createRemoteAnimationTarget(this);
            return this.mTarget;
        }

        void setMode(int i) {
            this.mMode = i;
        }

        int getMode() {
            return this.mMode;
        }

        boolean hasAnimatingParent() {
            for (int size = com.android.server.wm.RemoteAnimationController.this.mDisplayContent.mChangingContainers.size() - 1; size >= 0; size--) {
                if (this.mWindowContainer.isDescendantOf(com.android.server.wm.RemoteAnimationController.this.mDisplayContent.mChangingContainers.valueAt(size))) {
                    return true;
                }
            }
            return false;
        }
    }

    class RemoteAnimationAdapterWrapper implements com.android.server.wm.AnimationAdapter {
        private int mAnimationType;
        private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mCapturedFinishCallback;
        android.view.SurfaceControl mCapturedLeash;
        final android.graphics.Rect mLocalBounds;
        private final com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord mRecord;
        final boolean mShowBackdrop;
        final android.graphics.Point mPosition = new android.graphics.Point();
        final android.graphics.Rect mEndBounds = new android.graphics.Rect();
        final android.graphics.Rect mStartBounds = new android.graphics.Rect();

        RemoteAnimationAdapterWrapper(com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord, android.graphics.Point point, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, boolean z) {
            this.mRecord = remoteAnimationRecord;
            this.mPosition.set(point.x, point.y);
            this.mLocalBounds = rect;
            this.mEndBounds.set(rect2);
            this.mStartBounds.set(rect3);
            this.mShowBackdrop = z;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public int getBackgroundColor() {
            return this.mRecord.mBackdropColor;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean getShowBackground() {
            return this.mShowBackdrop;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8918152561092803537L, 0, null, null);
            if (this.mStartBounds.isEmpty()) {
                transaction.setPosition(surfaceControl, this.mPosition.x, this.mPosition.y);
                transaction.setWindowCrop(surfaceControl, this.mEndBounds.width(), this.mEndBounds.height());
            } else {
                transaction.setPosition(surfaceControl, (this.mPosition.x + this.mStartBounds.left) - this.mEndBounds.left, (this.mPosition.y + this.mStartBounds.top) - this.mEndBounds.top);
                transaction.setWindowCrop(surfaceControl, this.mStartBounds.width(), this.mStartBounds.height());
            }
            this.mCapturedLeash = surfaceControl;
            this.mCapturedFinishCallback = onAnimationFinishedCallback;
            this.mAnimationType = i;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
            if (com.android.server.wm.RemoteAnimationController.this.mIsFinishing) {
                return;
            }
            if (this.mRecord.mAdapter == this) {
                this.mRecord.mAdapter = null;
            } else {
                this.mRecord.mThumbnailAdapter = null;
            }
            if (this.mRecord.mAdapter == null && this.mRecord.mThumbnailAdapter == null) {
                com.android.server.wm.RemoteAnimationController.this.mPendingAnimations.remove(this.mRecord);
            }
            if (com.android.server.wm.RemoteAnimationController.this.mPendingAnimations.isEmpty()) {
                com.android.server.wm.RemoteAnimationController.this.cancelAnimation("allAppAnimationsCanceled");
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getDurationHint() {
            return com.android.server.wm.RemoteAnimationController.this.mRemoteAnimationAdapter.getDuration();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getStatusBarTransitionsStartTime() {
            return android.os.SystemClock.uptimeMillis() + com.android.server.wm.RemoteAnimationController.this.mRemoteAnimationAdapter.getStatusBarTransitionDelay();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print("container=");
            printWriter.println(this.mRecord.mWindowContainer);
            if (this.mRecord.mTarget != null) {
                printWriter.print(str);
                printWriter.println("Target:");
                this.mRecord.mTarget.dump(printWriter, str + "  ");
                return;
            }
            printWriter.print(str);
            printWriter.println("Target: null");
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            if (this.mRecord.mTarget != null) {
                this.mRecord.mTarget.dumpDebug(protoOutputStream, 1146756268033L);
            }
            protoOutputStream.end(start);
        }
    }
}
