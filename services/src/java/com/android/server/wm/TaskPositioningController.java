package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskPositioningController {
    private android.view.SurfaceControl mInputSurface;
    private com.android.server.wm.DisplayContent mPositioningDisplay;
    private final com.android.server.wm.WindowManagerService mService;

    @android.annotation.Nullable
    private com.android.server.wm.TaskPositioner mTaskPositioner;
    private final android.graphics.Rect mTmpClipRect = new android.graphics.Rect();
    final android.view.SurfaceControl.Transaction mTransaction;

    boolean isPositioningLocked() {
        return this.mTaskPositioner != null;
    }

    android.view.InputWindowHandle getDragWindowHandleLocked() {
        if (this.mTaskPositioner != null) {
            return this.mTaskPositioner.mDragWindowHandle;
        }
        return null;
    }

    TaskPositioningController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mTransaction = windowManagerService.mTransactionFactory.get();
    }

    void hideInputSurface(int i) {
        if (this.mPositioningDisplay != null && this.mPositioningDisplay.getDisplayId() == i && this.mInputSurface != null) {
            this.mTransaction.hide(this.mInputSurface).apply();
        }
    }

    java.util.concurrent.CompletableFuture<java.lang.Void> showInputSurface(int i) {
        if (this.mPositioningDisplay == null || this.mPositioningDisplay.getDisplayId() != i) {
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }
        com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
        if (this.mInputSurface == null) {
            this.mInputSurface = this.mService.makeSurfaceBuilder(displayContent.getSession()).setContainerLayer().setName("Drag and Drop Input Consumer").setCallsite("TaskPositioningController.showInputSurface").setParent(displayContent.getOverlayLayer()).build();
        }
        android.view.InputWindowHandle dragWindowHandleLocked = getDragWindowHandleLocked();
        if (dragWindowHandleLocked == null) {
            android.util.Slog.w("WindowManager", "Drag is in progress but there is no drag window handle.");
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }
        android.view.Display display = displayContent.getDisplay();
        android.graphics.Point point = new android.graphics.Point();
        display.getRealSize(point);
        this.mTmpClipRect.set(0, 0, point.x, point.y);
        final java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture = new java.util.concurrent.CompletableFuture<>();
        this.mTransaction.show(this.mInputSurface).setInputWindowInfo(this.mInputSurface, dragWindowHandleLocked).setLayer(this.mInputSurface, Integer.MAX_VALUE).setPosition(this.mInputSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).setCrop(this.mInputSurface, this.mTmpClipRect).addWindowInfosReportedListener(new java.lang.Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                completableFuture.complete(null);
            }
        }).apply();
        return completableFuture;
    }

    boolean startMovingTask(android.view.IWindow iWindow, float f, float f2) {
        com.android.server.wm.WindowState windowForClientLocked;
        java.util.concurrent.CompletableFuture<java.lang.Boolean> startPositioningLocked;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowForClientLocked = this.mService.windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                startPositioningLocked = startPositioningLocked(windowForClientLocked, false, false, f, f2);
            } finally {
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        try {
            if (!startPositioningLocked.get().booleanValue()) {
                return false;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    this.mService.mAtmService.setFocusedTask(windowForClientLocked.getTask().mTaskId);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.e("WindowManager", "Exception thrown while waiting for startPositionLocked future", e);
            return false;
        }
    }

    void handleTapOutsideTask(final com.android.server.wm.DisplayContent displayContent, final int i, final int i2) {
        this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TaskPositioningController.this.lambda$handleTapOutsideTask$1(displayContent, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleTapOutsideTask$1(com.android.server.wm.DisplayContent displayContent, int i, int i2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task findTaskForResizePoint = displayContent.findTaskForResizePoint(i, i2);
                if (findTaskForResizePoint == null || !findTaskForResizePoint.isResizeable()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                java.util.concurrent.CompletableFuture<java.lang.Boolean> startPositioningLocked = startPositioningLocked(findTaskForResizePoint.getTopVisibleAppMainWindow(), true, findTaskForResizePoint.preserveOrientationOnResize(), i, i2);
                try {
                    if (!startPositioningLocked.get().booleanValue()) {
                        return;
                    }
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            this.mService.mAtmService.setFocusedTask(findTaskForResizePoint.mTaskId);
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Exception e) {
                    android.util.Slog.e("WindowManager", "Exception thrown while waiting for startPositionLocked future", e);
                }
            } finally {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    private java.util.concurrent.CompletableFuture<java.lang.Boolean> startPositioningLocked(final com.android.server.wm.WindowState windowState, final boolean z, final boolean z2, final float f, final float f2) {
        if (windowState == null || windowState.mActivityRecord == null) {
            android.util.Slog.w("WindowManager", "startPositioningLocked: Bad window " + windowState);
            return java.util.concurrent.CompletableFuture.completedFuture(false);
        }
        if (windowState.mInputChannel == null) {
            android.util.Slog.wtf("WindowManager", "startPositioningLocked: " + windowState + " has no input channel,  probably being removed");
            return java.util.concurrent.CompletableFuture.completedFuture(false);
        }
        final com.android.server.wm.DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null) {
            android.util.Slog.w("WindowManager", "startPositioningLocked: Invalid display content " + windowState);
            return java.util.concurrent.CompletableFuture.completedFuture(false);
        }
        this.mPositioningDisplay = displayContent;
        this.mTaskPositioner = com.android.server.wm.TaskPositioner.create(this.mService);
        return this.mTaskPositioner.register(displayContent, windowState).thenApply(new java.util.function.Function() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$startPositioningLocked$2;
                lambda$startPositioningLocked$2 = com.android.server.wm.TaskPositioningController.this.lambda$startPositioningLocked$2(windowState, displayContent, z, z2, f, f2, (java.lang.Void) obj);
                return lambda$startPositioningLocked$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$startPositioningLocked$2(com.android.server.wm.WindowState windowState, com.android.server.wm.DisplayContent displayContent, boolean z, boolean z2, float f, float f2, java.lang.Void r9) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (displayContent.mCurrentFocus != null && displayContent.mCurrentFocus != windowState && displayContent.mCurrentFocus.mActivityRecord == windowState.mActivityRecord) {
                    windowState = displayContent.mCurrentFocus;
                }
                if (!this.mService.mInputManager.transferTouchGesture(windowState.mInputChannel.getToken(), this.mTaskPositioner.mClientChannel.getToken())) {
                    android.util.Slog.e("WindowManager", "startPositioningLocked: Unable to transfer touch focus");
                    cleanUpTaskPositioner();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                this.mTaskPositioner.startDrag(z, z2, f, f2);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void finishTaskPositioning(android.view.IWindow iWindow) {
        if (this.mTaskPositioner != null && this.mTaskPositioner.mClientCallback == iWindow.asBinder()) {
            finishTaskPositioning();
        }
    }

    void finishTaskPositioning() {
        this.mService.mAnimationHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TaskPositioningController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TaskPositioningController.this.lambda$finishTaskPositioning$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishTaskPositioning$3() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                cleanUpTaskPositioner();
                this.mPositioningDisplay = null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private void cleanUpTaskPositioner() {
        com.android.server.wm.TaskPositioner taskPositioner = this.mTaskPositioner;
        if (taskPositioner == null) {
            return;
        }
        this.mTaskPositioner = null;
        taskPositioner.unregister();
    }
}
