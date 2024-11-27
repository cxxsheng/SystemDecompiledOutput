package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskPositioner implements android.os.IBinder.DeathRecipient {
    private static final boolean DEBUG_ORIENTATION_VIOLATIONS = false;
    public static final float RESIZING_HINT_ALPHA = 0.5f;
    public static final int RESIZING_HINT_DURATION_MS = 0;
    private static final java.lang.String TAG = "WindowManager";
    private static final java.lang.String TAG_LOCAL = "TaskPositioner";
    private static com.android.server.wm.TaskPositioner.Factory sFactory;
    android.os.IBinder mClientCallback;
    android.view.InputChannel mClientChannel;
    private com.android.server.wm.DisplayContent mDisplayContent;
    android.view.InputApplicationHandle mDragApplicationHandle;

    @com.android.internal.annotations.VisibleForTesting
    boolean mDragEnded;
    android.view.InputWindowHandle mDragWindowHandle;
    private android.view.InputEventReceiver mInputEventReceiver;
    private int mMinVisibleHeight;
    private int mMinVisibleWidth;
    private boolean mPreserveOrientation;
    private boolean mResizing;
    private final com.android.server.wm.WindowManagerService mService;
    private float mStartDragX;
    private float mStartDragY;
    private boolean mStartOrientationWasLandscape;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.Task mTask;
    com.android.server.wm.WindowState mWindow;
    private android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private final android.graphics.Rect mWindowOriginalBounds = new android.graphics.Rect();
    private final android.graphics.Rect mWindowDragBounds = new android.graphics.Rect();
    private final android.graphics.Point mMaxVisibleSize = new android.graphics.Point();
    private int mCtrlType = 0;

    @com.android.internal.annotations.VisibleForTesting
    TaskPositioner(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onInputEvent(android.view.InputEvent inputEvent) {
        if (!(inputEvent instanceof android.view.MotionEvent) || (inputEvent.getSource() & 2) == 0) {
            return false;
        }
        android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
        if (this.mDragEnded) {
            return true;
        }
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        switch (motionEvent.getAction()) {
            case 1:
                this.mDragEnded = true;
                break;
            case 2:
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        this.mDragEnded = notifyMoveLocked(rawX, rawY);
                        this.mTask.getDimBounds(this.mTmpRect);
                    } finally {
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                if (!this.mTmpRect.equals(this.mWindowDragBounds)) {
                    android.os.Trace.traceBegin(32L, "wm.TaskPositioner.resizeTask");
                    this.mService.mAtmService.resizeTask(this.mTask.mTaskId, this.mWindowDragBounds, 1);
                    android.os.Trace.traceEnd(32L);
                    break;
                }
                break;
            case 3:
                this.mDragEnded = true;
                break;
        }
        if (this.mDragEnded) {
            boolean z = this.mResizing;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    endDragLocked();
                    this.mTask.getDimBounds(this.mTmpRect);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            if (z && !this.mTmpRect.equals(this.mWindowDragBounds)) {
                this.mService.mAtmService.resizeTask(this.mTask.mTaskId, this.mWindowDragBounds, 3);
            }
            this.mService.mTaskPositioningController.finishTaskPositioning();
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Rect getWindowDragBounds() {
        return this.mWindowDragBounds;
    }

    java.util.concurrent.CompletableFuture<java.lang.Void> register(final com.android.server.wm.DisplayContent displayContent, @android.annotation.NonNull final com.android.server.wm.WindowState windowState) {
        if (this.mClientChannel != null) {
            android.util.Slog.e(TAG, "Task positioner already registered");
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }
        this.mDisplayContent = displayContent;
        this.mClientChannel = this.mService.mInputManager.createInputChannel(TAG);
        this.mInputEventReceiver = new android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver(this.mClientChannel, this.mService.mAnimationHandler.getLooper(), this.mService.mAnimator.getChoreographer(), new android.view.BatchedInputEventReceiver.SimpleBatchedInputEventReceiver.InputEventListener() { // from class: com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda0
            public final boolean onInputEvent(android.view.InputEvent inputEvent) {
                boolean onInputEvent;
                onInputEvent = com.android.server.wm.TaskPositioner.this.onInputEvent(inputEvent);
                return onInputEvent;
            }
        });
        this.mDragApplicationHandle = new android.view.InputApplicationHandle(new android.os.Binder(), TAG, android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
        this.mDragWindowHandle = new android.view.InputWindowHandle(this.mDragApplicationHandle, displayContent.getDisplayId());
        this.mDragWindowHandle.name = TAG;
        this.mDragWindowHandle.token = this.mClientChannel.getToken();
        this.mDragWindowHandle.layoutParamsType = 2016;
        this.mDragWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        this.mDragWindowHandle.ownerPid = com.android.server.wm.WindowManagerService.MY_PID;
        this.mDragWindowHandle.ownerUid = com.android.server.wm.WindowManagerService.MY_UID;
        this.mDragWindowHandle.scaleFactor = 1.0f;
        this.mDragWindowHandle.inputConfig = 4;
        this.mDragWindowHandle.touchableRegion.setEmpty();
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 3007492640459931179L, 0, null, null);
        this.mDisplayContent.getDisplayRotation().pause();
        return this.mService.mTaskPositioningController.showInputSurface(windowState.getDisplayId()).thenRun(new java.lang.Runnable() { // from class: com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TaskPositioner.this.lambda$register$0(displayContent, windowState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$register$0(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.graphics.Rect rect = this.mTmpRect;
                displayContent.getBounds(rect);
                android.util.DisplayMetrics displayMetrics = displayContent.getDisplayMetrics();
                this.mMinVisibleWidth = com.android.server.wm.WindowManagerService.dipToPixel(48, displayMetrics);
                this.mMinVisibleHeight = com.android.server.wm.WindowManagerService.dipToPixel(32, displayMetrics);
                this.mMaxVisibleSize.set(rect.width(), rect.height());
                this.mDragEnded = false;
                try {
                    this.mClientCallback = windowState.mClient.asBinder();
                    this.mClientCallback.linkToDeath(this, 0);
                    this.mWindow = windowState;
                    this.mTask = windowState.getTask();
                } catch (android.os.RemoteException e) {
                    this.mService.mTaskPositioningController.finishTaskPositioning();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void unregister() {
        if (this.mClientChannel == null) {
            android.util.Slog.e(TAG, "Task positioner not registered");
            return;
        }
        this.mService.mTaskPositioningController.hideInputSurface(this.mDisplayContent.getDisplayId());
        this.mService.mInputManager.removeInputChannel(this.mClientChannel.getToken());
        this.mInputEventReceiver.dispose();
        this.mInputEventReceiver = null;
        this.mClientChannel.dispose();
        this.mClientChannel = null;
        this.mDragWindowHandle = null;
        this.mDragApplicationHandle = null;
        this.mDragEnded = true;
        this.mDisplayContent.getInputMonitor().updateInputWindowsLw(true);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 5478864901888225320L, 0, null, null);
        this.mDisplayContent.getDisplayRotation().resume();
        this.mDisplayContent = null;
        if (this.mClientCallback != null) {
            this.mClientCallback.unlinkToDeath(this, 0);
        }
        this.mWindow = null;
    }

    void startDrag(boolean z, boolean z2, float f, float f2) {
        boolean z3;
        final android.graphics.Rect rect = this.mTmpRect;
        this.mTask.getBounds(rect);
        boolean z4 = false;
        this.mCtrlType = 0;
        this.mStartDragX = f;
        this.mStartDragY = f2;
        this.mPreserveOrientation = z2;
        if (z) {
            if (f < rect.left) {
                this.mCtrlType |= 1;
            }
            if (f > rect.right) {
                this.mCtrlType |= 2;
            }
            if (f2 < rect.top) {
                this.mCtrlType |= 4;
            }
            if (f2 > rect.bottom) {
                this.mCtrlType |= 8;
            }
            if (this.mCtrlType == 0) {
                z3 = false;
            } else {
                z3 = true;
            }
            this.mResizing = z3;
        }
        if (rect.width() >= rect.height()) {
            z4 = true;
        }
        this.mStartOrientationWasLandscape = z4;
        this.mWindowOriginalBounds.set(rect);
        if (this.mResizing) {
            notifyMoveLocked(f, f2);
            this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TaskPositioner.this.lambda$startDrag$1(rect);
                }
            });
        }
        this.mWindowDragBounds.set(rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDrag$1(android.graphics.Rect rect) {
        this.mService.mAtmService.resizeTask(this.mTask.mTaskId, rect, 3);
    }

    private void endDragLocked() {
        this.mResizing = false;
        this.mTask.setDragResizing(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean notifyMoveLocked(float f, float f2) {
        if (this.mCtrlType != 0) {
            resizeDrag(f, f2);
            this.mTask.setDragResizing(true);
            return false;
        }
        this.mDisplayContent.getStableRect(this.mTmpRect);
        this.mTmpRect.intersect(this.mTask.getRootTask().getParent().getBounds());
        int i = (int) f;
        int i2 = (int) f2;
        if (!this.mTmpRect.contains(i, i2)) {
            i = java.lang.Math.min(java.lang.Math.max(i, this.mTmpRect.left), this.mTmpRect.right);
            i2 = java.lang.Math.min(java.lang.Math.max(i2, this.mTmpRect.top), this.mTmpRect.bottom);
        }
        updateWindowDragBounds(i, i2, this.mTmpRect);
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    void resizeDrag(float f, float f2) {
        updateDraggedBounds(com.android.internal.policy.TaskResizingAlgorithm.resizeDrag(f, f2, this.mStartDragX, this.mStartDragY, this.mWindowOriginalBounds, this.mCtrlType, this.mMinVisibleWidth, this.mMinVisibleHeight, this.mMaxVisibleSize, this.mPreserveOrientation, this.mStartOrientationWasLandscape));
    }

    private void updateDraggedBounds(android.graphics.Rect rect) {
        this.mWindowDragBounds.set(rect);
        checkBoundsForOrientationViolations(this.mWindowDragBounds);
    }

    private void checkBoundsForOrientationViolations(android.graphics.Rect rect) {
    }

    private void updateWindowDragBounds(int i, int i2, android.graphics.Rect rect) {
        int round = java.lang.Math.round(i - this.mStartDragX);
        int round2 = java.lang.Math.round(i2 - this.mStartDragY);
        this.mWindowDragBounds.set(this.mWindowOriginalBounds);
        this.mWindowDragBounds.offsetTo(java.lang.Math.min(java.lang.Math.max(this.mWindowOriginalBounds.left + round, (rect.left + this.mMinVisibleWidth) - this.mWindowOriginalBounds.width()), rect.right - this.mMinVisibleWidth), java.lang.Math.min(java.lang.Math.max(this.mWindowOriginalBounds.top + round2, rect.top), rect.bottom - this.mMinVisibleHeight));
    }

    public java.lang.String toShortString() {
        return TAG;
    }

    static void setFactory(com.android.server.wm.TaskPositioner.Factory factory) {
        sFactory = factory;
    }

    static com.android.server.wm.TaskPositioner create(com.android.server.wm.WindowManagerService windowManagerService) {
        if (sFactory == null) {
            sFactory = new com.android.server.wm.TaskPositioner.Factory() { // from class: com.android.server.wm.TaskPositioner.1
            };
        }
        return sFactory.create(windowManagerService);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        this.mService.mTaskPositioningController.finishTaskPositioning();
    }

    interface Factory {
        default com.android.server.wm.TaskPositioner create(com.android.server.wm.WindowManagerService windowManagerService) {
            return new com.android.server.wm.TaskPositioner(windowManagerService);
        }
    }
}
