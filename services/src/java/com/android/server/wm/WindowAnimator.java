package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowAnimator {
    private static final java.lang.String TAG = "WindowManager";
    final android.view.Choreographer.FrameCallback mAnimationFrameCallback;
    private boolean mAnimationFrameCallbackScheduled;
    private android.view.Choreographer mChoreographer;
    final android.content.Context mContext;
    long mCurrentTime;
    private boolean mInExecuteAfterPrepareSurfacesRunnables;
    private boolean mLastRootAnimating;
    java.lang.Object mLastWindowFreezeSource;
    final com.android.server.policy.WindowManagerPolicy mPolicy;
    private boolean mRunningExpensiveAnimations;
    final com.android.server.wm.WindowManagerService mService;
    private final android.view.SurfaceControl.Transaction mTransaction;
    int mBulkUpdateParams = 0;
    private boolean mInitialized = false;
    boolean mNotifyWhenNoAnimation = false;
    private final java.util.ArrayList<java.lang.Runnable> mAfterPrepareSurfacesRunnables = new java.util.ArrayList<>();

    WindowAnimator(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mContext = windowManagerService.mContext;
        this.mPolicy = windowManagerService.mPolicy;
        this.mTransaction = windowManagerService.mTransactionFactory.get();
        windowManagerService.mAnimationHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowAnimator.this.lambda$new$0();
            }
        }, 0L);
        this.mAnimationFrameCallback = new android.view.Choreographer.FrameCallback() { // from class: com.android.server.wm.WindowAnimator$$ExternalSyntheticLambda1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                com.android.server.wm.WindowAnimator.this.lambda$new$1(j);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mChoreographer = android.view.Choreographer.getSfInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(long j) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAnimationFrameCallbackScheduled = false;
                animate(j);
                if (this.mNotifyWhenNoAnimation && !this.mLastRootAnimating) {
                    this.mService.mGlobalLock.notifyAll();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void ready() {
        this.mInitialized = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00c9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void animate(long j) {
        boolean z;
        boolean hasPendingLayoutChanges;
        if (this.mInitialized) {
            scheduleAnimation();
            com.android.server.wm.RootWindowContainer rootWindowContainer = this.mService.mRoot;
            boolean isShellTransitionsEnabled = rootWindowContainer.mTransitionController.isShellTransitionsEnabled();
            int i = isShellTransitionsEnabled ? 4 : 5;
            this.mCurrentTime = j / 1000000;
            this.mBulkUpdateParams = 0;
            rootWindowContainer.mOrientationChangeComplete = true;
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -5360147928134631656L, 0, null, null);
            try {
                rootWindowContainer.handleCompleteDeferredRemoval();
                com.android.server.wm.AccessibilityController accessibilityController = this.mService.mAccessibilityController;
                int childCount = rootWindowContainer.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    com.android.server.wm.DisplayContent childAt = rootWindowContainer.getChildAt(i2);
                    childAt.updateWindowsForAnimator();
                    childAt.prepareSurfaces();
                }
                z = false;
                for (int i3 = 0; i3 < childCount; i3++) {
                    try {
                        com.android.server.wm.DisplayContent childAt2 = rootWindowContainer.getChildAt(i3);
                        if (!isShellTransitionsEnabled) {
                            childAt2.checkAppWindowsReadyToShow();
                        }
                        if (accessibilityController.hasCallbacks()) {
                            accessibilityController.drawMagnifiedRegionBorderIfNeeded(childAt2.mDisplayId);
                        }
                        if (childAt2.isAnimating(i, -1)) {
                            try {
                                if (!childAt2.mLastContainsRunningSurfaceAnimator) {
                                    childAt2.mLastContainsRunningSurfaceAnimator = true;
                                    childAt2.enableHighFrameRate(true);
                                }
                                z = true;
                            } catch (java.lang.RuntimeException e) {
                                e = e;
                                z = true;
                                android.util.Slog.wtf(TAG, "Unhandled exception in Window Manager", e);
                                hasPendingLayoutChanges = rootWindowContainer.hasPendingLayoutChanges(this);
                                if (this.mBulkUpdateParams != 0) {
                                    if (!hasPendingLayoutChanges) {
                                    }
                                    this.mService.mWindowPlacerLocked.requestTraversal();
                                    if (z) {
                                        android.os.Trace.asyncTraceBegin(32L, "animating", 0);
                                    }
                                    if (!z) {
                                        this.mService.mWindowPlacerLocked.requestTraversal();
                                        android.os.Trace.asyncTraceEnd(32L, "animating", 0);
                                    }
                                    this.mLastRootAnimating = z;
                                    if (!isShellTransitionsEnabled) {
                                    }
                                    android.os.Trace.traceBegin(32L, "applyTransaction");
                                    this.mTransaction.apply();
                                    android.os.Trace.traceEnd(32L);
                                    this.mService.mWindowTracing.logState("WindowAnimator");
                                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -3993586364046165922L, 0, null, null);
                                    this.mService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
                                    executeAfterPrepareSurfacesRunnables();
                                }
                                if (!hasPendingLayoutChanges) {
                                }
                                this.mService.mWindowPlacerLocked.requestTraversal();
                                if (z) {
                                }
                                if (!z) {
                                }
                                this.mLastRootAnimating = z;
                                if (!isShellTransitionsEnabled) {
                                }
                                android.os.Trace.traceBegin(32L, "applyTransaction");
                                this.mTransaction.apply();
                                android.os.Trace.traceEnd(32L);
                                this.mService.mWindowTracing.logState("WindowAnimator");
                                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -3993586364046165922L, 0, null, null);
                                this.mService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
                                executeAfterPrepareSurfacesRunnables();
                            }
                        } else if (childAt2.mLastContainsRunningSurfaceAnimator) {
                            childAt2.mLastContainsRunningSurfaceAnimator = false;
                            childAt2.enableHighFrameRate(false);
                        }
                        this.mTransaction.merge(childAt2.getPendingTransaction());
                    } catch (java.lang.RuntimeException e2) {
                        e = e2;
                    }
                }
                cancelAnimation();
                if (this.mService.mWatermark != null) {
                    this.mService.mWatermark.drawIfNeeded();
                }
            } catch (java.lang.RuntimeException e3) {
                e = e3;
                z = false;
            }
            hasPendingLayoutChanges = rootWindowContainer.hasPendingLayoutChanges(this);
            boolean z2 = (this.mBulkUpdateParams != 0 || rootWindowContainer.mOrientationChangeComplete) && rootWindowContainer.copyAnimToLayoutParams();
            if (!hasPendingLayoutChanges || z2) {
                this.mService.mWindowPlacerLocked.requestTraversal();
            }
            if (z && !this.mLastRootAnimating) {
                android.os.Trace.asyncTraceBegin(32L, "animating", 0);
            }
            if (!z && this.mLastRootAnimating) {
                this.mService.mWindowPlacerLocked.requestTraversal();
                android.os.Trace.asyncTraceEnd(32L, "animating", 0);
            }
            this.mLastRootAnimating = z;
            if (!isShellTransitionsEnabled) {
                updateRunningExpensiveAnimationsLegacy();
            }
            android.os.Trace.traceBegin(32L, "applyTransaction");
            this.mTransaction.apply();
            android.os.Trace.traceEnd(32L);
            this.mService.mWindowTracing.logState("WindowAnimator");
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -3993586364046165922L, 0, null, null);
            this.mService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
            executeAfterPrepareSurfacesRunnables();
        }
    }

    private void updateRunningExpensiveAnimationsLegacy() {
        boolean isAnimating = this.mService.mRoot.isAnimating(5, 11);
        if (isAnimating && !this.mRunningExpensiveAnimations) {
            this.mService.mSnapshotController.setPause(true);
            this.mTransaction.setEarlyWakeupStart();
        } else if (!isAnimating && this.mRunningExpensiveAnimations) {
            this.mService.mSnapshotController.setPause(false);
            this.mTransaction.setEarlyWakeupEnd();
        }
        this.mRunningExpensiveAnimations = isAnimating;
    }

    private static java.lang.String bulkUpdateParamsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        if ((i & 1) != 0) {
            sb.append(" UPDATE_ROTATION");
        }
        if ((i & 2) != 0) {
            sb.append(" SET_WALLPAPER_ACTION_PENDING");
        }
        return sb.toString();
    }

    public void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        java.lang.String str2 = "  " + str;
        for (int i = 0; i < this.mService.mRoot.getChildCount(); i++) {
            com.android.server.wm.DisplayContent childAt = this.mService.mRoot.getChildAt(i);
            printWriter.print(str);
            printWriter.print(childAt);
            printWriter.println(":");
            childAt.dumpWindowAnimators(printWriter, str2);
            printWriter.println();
        }
        printWriter.println();
        if (z) {
            printWriter.print(str);
            printWriter.print("mCurrentTime=");
            printWriter.println(android.util.TimeUtils.formatUptime(this.mCurrentTime));
        }
        if (this.mBulkUpdateParams != 0) {
            printWriter.print(str);
            printWriter.print("mBulkUpdateParams=0x");
            printWriter.print(java.lang.Integer.toHexString(this.mBulkUpdateParams));
            printWriter.println(bulkUpdateParamsToString(this.mBulkUpdateParams));
        }
    }

    void scheduleAnimation() {
        if (!this.mAnimationFrameCallbackScheduled) {
            this.mAnimationFrameCallbackScheduled = true;
            this.mChoreographer.postFrameCallback(this.mAnimationFrameCallback);
        }
    }

    private void cancelAnimation() {
        if (this.mAnimationFrameCallbackScheduled) {
            this.mAnimationFrameCallbackScheduled = false;
            this.mChoreographer.removeFrameCallback(this.mAnimationFrameCallback);
        }
    }

    boolean isAnimationScheduled() {
        return this.mAnimationFrameCallbackScheduled;
    }

    android.view.Choreographer getChoreographer() {
        return this.mChoreographer;
    }

    void addAfterPrepareSurfacesRunnable(java.lang.Runnable runnable) {
        if (this.mInExecuteAfterPrepareSurfacesRunnables) {
            runnable.run();
        } else {
            this.mAfterPrepareSurfacesRunnables.add(runnable);
            scheduleAnimation();
        }
    }

    void executeAfterPrepareSurfacesRunnables() {
        if (this.mInExecuteAfterPrepareSurfacesRunnables) {
            return;
        }
        this.mInExecuteAfterPrepareSurfacesRunnables = true;
        int size = this.mAfterPrepareSurfacesRunnables.size();
        for (int i = 0; i < size; i++) {
            this.mAfterPrepareSurfacesRunnables.get(i).run();
        }
        this.mAfterPrepareSurfacesRunnables.clear();
        this.mInExecuteAfterPrepareSurfacesRunnables = false;
    }
}
