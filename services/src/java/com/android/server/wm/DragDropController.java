package com.android.server.wm;

/* loaded from: classes3.dex */
class DragDropController {
    private static final int A11Y_DRAG_TIMEOUT_DEFAULT_MS = 60000;
    private static final float DRAG_SHADOW_ALPHA_TRANSPARENT = 0.7071f;
    static final long DRAG_TIMEOUT_MS = 5000;
    static final int MSG_ANIMATION_END = 2;
    static final int MSG_DRAG_END_TIMEOUT = 0;
    static final int MSG_REMOVE_DRAG_SURFACE_TIMEOUT = 3;
    static final int MSG_TEAR_DOWN_DRAG_AND_DROP_INPUT = 1;
    static final int MSG_UNHANDLED_DROP_LISTENER_TIMEOUT = 4;
    private com.android.server.wm.DragState mDragState;
    private android.window.IGlobalDragListener mGlobalDragListener;
    private final android.os.Handler mHandler;
    private com.android.server.wm.WindowManagerService mService;
    private final android.os.IBinder.DeathRecipient mGlobalDragListenerDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.DragDropController.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.DragDropController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.DragDropController.this.hasPendingUnhandledDropCallback()) {
                        com.android.server.wm.DragDropController.this.onUnhandledDropCallback(false);
                    }
                    com.android.server.wm.DragDropController.this.setGlobalDragListener(null);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    };

    @android.annotation.NonNull
    private java.util.concurrent.atomic.AtomicReference<com.android.server.wm.WindowManagerInternal.IDragDropCallback> mCallback = new java.util.concurrent.atomic.AtomicReference<>(new com.android.server.wm.WindowManagerInternal.IDragDropCallback() { // from class: com.android.server.wm.DragDropController.2
    });

    DragDropController(com.android.server.wm.WindowManagerService windowManagerService, android.os.Looper looper) {
        this.mService = windowManagerService;
        this.mHandler = new com.android.server.wm.DragDropController.DragHandler(windowManagerService, looper);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    boolean dragDropActiveLocked() {
        return (this.mDragState == null || this.mDragState.isClosing()) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean dragSurfaceRelinquishedToDropTarget() {
        return this.mDragState != null && this.mDragState.mRelinquishDragSurfaceToDropTarget;
    }

    void registerCallback(com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback) {
        java.util.Objects.requireNonNull(iDragDropCallback);
        this.mCallback.set(iDragDropCallback);
    }

    public void setGlobalDragListener(android.window.IGlobalDragListener iGlobalDragListener) {
        if (this.mGlobalDragListener != null && this.mGlobalDragListener.asBinder() != null) {
            this.mGlobalDragListener.asBinder().unlinkToDeath(this.mGlobalDragListenerDeathRecipient, 0);
        }
        this.mGlobalDragListener = iGlobalDragListener;
        if (iGlobalDragListener != null && iGlobalDragListener.asBinder() != null) {
            try {
                this.mGlobalDragListener.asBinder().linkToDeath(this.mGlobalDragListenerDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
                this.mGlobalDragListener = null;
            }
        }
    }

    void sendDragStartedIfNeededLocked(com.android.server.wm.WindowState windowState) {
        this.mDragState.sendDragStartedIfNeededLocked(windowState);
    }

    /* JADX WARN: Finally extract failed */
    android.os.IBinder performDrag(int i, int i2, android.view.IWindow iWindow, int i3, android.view.SurfaceControl surfaceControl, int i4, int i5, int i6, float f, float f2, float f3, float f4, android.content.ClipData clipData) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        android.view.SurfaceControl surfaceControl2;
        java.util.concurrent.atomic.AtomicReference<com.android.server.wm.WindowManagerInternal.IDragDropCallback> atomicReference;
        com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback;
        com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback2;
        boolean z;
        android.util.Slog.d("WindowManager", "perform drag: win=" + iWindow + " surface=" + surfaceControl + " flags=" + java.lang.Integer.toHexString(i3) + " data=" + clipData + " touch(" + f + "," + f2 + ") thumb center(" + f3 + "," + f4 + ")");
        android.os.Binder binder = new android.os.Binder();
        boolean prePerformDrag = this.mCallback.get().prePerformDrag(iWindow, binder, i4, f, f2, f3, f4, clipData);
        try {
            windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        } finally {
        }
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    if (!prePerformDrag) {
                        android.util.Slog.w("WindowManager", "IDragDropCallback rejects the performDrag request");
                        if (surfaceControl != null) {
                            android.view.SurfaceControl.Transaction transaction = this.mService.mTransactionFactory.get();
                            try {
                                transaction.remove(surfaceControl);
                                transaction.apply();
                                transaction.close();
                            } catch (java.lang.Throwable th) {
                                if (transaction == null) {
                                    throw th;
                                }
                                try {
                                    transaction.close();
                                    throw th;
                                } catch (java.lang.Throwable th2) {
                                    th.addSuppressed(th2);
                                    throw th;
                                }
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (dragDropActiveLocked()) {
                        android.util.Slog.w("WindowManager", "Drag already in progress");
                        if (surfaceControl != null) {
                            android.view.SurfaceControl.Transaction transaction2 = this.mService.mTransactionFactory.get();
                            try {
                                transaction2.remove(surfaceControl);
                                transaction2.apply();
                                transaction2.close();
                            } catch (java.lang.Throwable th3) {
                                if (transaction2 == null) {
                                    throw th3;
                                }
                                try {
                                    transaction2.close();
                                    throw th3;
                                } catch (java.lang.Throwable th4) {
                                    th3.addSuppressed(th4);
                                    throw th3;
                                }
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                    if (windowForClientLocked != null) {
                        try {
                            if (windowForClientLocked.canReceiveTouchInput()) {
                                com.android.server.wm.DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                                if (displayContent == null) {
                                    android.util.Slog.w("WindowManager", "display content is null");
                                    if (surfaceControl != null) {
                                        android.view.SurfaceControl.Transaction transaction3 = this.mService.mTransactionFactory.get();
                                        try {
                                            transaction3.remove(surfaceControl);
                                            transaction3.apply();
                                            transaction3.close();
                                        } catch (java.lang.Throwable th5) {
                                            if (transaction3 == null) {
                                                throw th5;
                                            }
                                            try {
                                                transaction3.close();
                                                throw th5;
                                            } catch (java.lang.Throwable th6) {
                                                th5.addSuppressed(th6);
                                                throw th5;
                                            }
                                        }
                                    }
                                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                    return null;
                                }
                                float f5 = (i3 & 512) == 0 ? DRAG_SHADOW_ALPHA_TRANSPARENT : 1.0f;
                                this.mDragState = new com.android.server.wm.DragState(this.mService, this, new android.os.Binder(), surfaceControl, i3, iWindow.asBinder());
                                try {
                                    this.mDragState.mPid = i;
                                    this.mDragState.mUid = i2;
                                    this.mDragState.mOriginalAlpha = f5;
                                    this.mDragState.mAnimatedScale = windowForClientLocked.mGlobalScale;
                                    this.mDragState.mToken = binder;
                                    this.mDragState.mDisplayContent = displayContent;
                                    this.mDragState.mData = clipData;
                                    if ((i3 & 1024) != 0) {
                                        this.mDragState.broadcastDragStartedLocked(f, f2);
                                        sendTimeoutMessage(0, windowForClientLocked.mClient.asBinder(), getAccessibilityManager().getRecommendedTimeoutMillis(60000, 4));
                                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                        return binder;
                                    }
                                    java.util.concurrent.CompletableFuture<java.lang.Boolean> registerInputChannel = this.mCallback.get().registerInputChannel(this.mDragState, displayContent.getDisplay(), this.mService.mInputManager, windowForClientLocked.mInputChannel);
                                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                    try {
                                        z = registerInputChannel.get(DRAG_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS).booleanValue();
                                    } catch (java.lang.Exception e) {
                                        android.util.Slog.e("WindowManager", "Exception thrown while waiting for touch focus transfer", e);
                                        z = false;
                                    }
                                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                                    synchronized (windowManagerGlobalLock2) {
                                        try {
                                            if (!z) {
                                                android.util.Slog.e("WindowManager", "Unable to transfer touch focus");
                                                this.mDragState.closeLocked();
                                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                                this.mCallback.get().postPerformDrag();
                                                return null;
                                            }
                                            android.view.SurfaceControl surfaceControl3 = this.mDragState.mSurfaceControl;
                                            this.mDragState.broadcastDragStartedLocked(f, f2);
                                            if (!com.android.input.flags.Flags.enablePointerChoreographer()) {
                                                this.mDragState.overridePointerIconLocked(i4);
                                            } else if ((i4 & com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1) == 8194) {
                                                android.hardware.input.InputManagerGlobal.getInstance().setPointerIcon(android.view.PointerIcon.getSystemIcon(this.mService.mContext, 1021), this.mDragState.mDisplayContent.getDisplayId(), i5, i6, this.mDragState.getInputToken());
                                            }
                                            this.mDragState.mThumbOffsetX = f3;
                                            this.mDragState.mThumbOffsetY = f4;
                                            android.view.SurfaceControl.Transaction transaction4 = this.mDragState.mTransaction;
                                            transaction4.setAlpha(surfaceControl3, this.mDragState.mOriginalAlpha);
                                            transaction4.show(surfaceControl3);
                                            displayContent.reparentToOverlay(transaction4, surfaceControl3);
                                            this.mDragState.updateDragSurfaceLocked(true, f, f2);
                                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                            return binder;
                                        } catch (java.lang.Throwable th7) {
                                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                            throw th7;
                                        }
                                    }
                                } catch (java.lang.Throwable th8) {
                                    th = th8;
                                    surfaceControl2 = null;
                                    if (surfaceControl2 != null) {
                                        android.view.SurfaceControl.Transaction transaction5 = this.mService.mTransactionFactory.get();
                                        try {
                                            transaction5.remove(surfaceControl2);
                                            transaction5.apply();
                                            transaction5.close();
                                        } catch (java.lang.Throwable th9) {
                                            if (transaction5 == null) {
                                                throw th9;
                                            }
                                            try {
                                                transaction5.close();
                                                throw th9;
                                            } catch (java.lang.Throwable th10) {
                                                th9.addSuppressed(th10);
                                                throw th9;
                                            }
                                        }
                                    }
                                    throw th;
                                }
                                this.mCallback.get().postPerformDrag();
                            }
                        } catch (java.lang.Throwable th11) {
                            th = th11;
                            surfaceControl2 = surfaceControl;
                        }
                    }
                    android.util.Slog.w("WindowManager", "Bad requesting window " + iWindow);
                    if (surfaceControl != null) {
                        android.view.SurfaceControl.Transaction transaction6 = this.mService.mTransactionFactory.get();
                        try {
                            transaction6.remove(surfaceControl);
                            transaction6.apply();
                            transaction6.close();
                        } catch (java.lang.Throwable th12) {
                            if (transaction6 == null) {
                                throw th12;
                            }
                            try {
                                transaction6.close();
                                throw th12;
                            } catch (java.lang.Throwable th13) {
                                th12.addSuppressed(th13);
                                throw th12;
                            }
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    this.mCallback.get().postPerformDrag();
                    return null;
                } catch (java.lang.Throwable th14) {
                    th = th14;
                    surfaceControl2 = surfaceControl;
                }
            } catch (java.lang.Throwable th15) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th15;
            }
        }
    }

    void reportDropResult(android.view.IWindow iWindow, boolean z) {
        java.util.concurrent.atomic.AtomicReference<com.android.server.wm.WindowManagerInternal.IDragDropCallback> atomicReference;
        com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback;
        com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback2;
        android.os.IBinder asBinder = iWindow.asBinder();
        android.util.Slog.d("WindowManager", "Drop result=" + z + " reported by " + asBinder);
        this.mCallback.get().preReportDropResult(iWindow, z);
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mDragState == null) {
                        android.util.Slog.w("WindowManager", "Drop result given but no drag in progress");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (this.mDragState.mToken != asBinder) {
                        android.util.Slog.w("WindowManager", "Invalid drop-result claim by " + iWindow);
                        throw new java.lang.IllegalStateException("reportDropResult() by non-recipient");
                    }
                    this.mHandler.removeMessages(0, iWindow.asBinder());
                    com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                    if (windowForClientLocked == null) {
                        android.util.Slog.w("WindowManager", "Bad result-reporting window " + iWindow);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (!z && notifyUnhandledDrop(this.mDragState.mUnhandledDropEvent, "window-drop")) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    boolean z2 = z && this.mDragState.targetInterceptsGlobalDrag(windowForClientLocked);
                    boolean z3 = this.mDragState.mLocalWin.equals(asBinder) ? false : true;
                    this.mDragState.endDragLocked(z, z2);
                    com.android.server.wm.Task task = windowForClientLocked.getTask();
                    if (com.android.window.flags.Flags.delegateUnhandledDrags() && this.mGlobalDragListener != null && task != null && z && z3) {
                        try {
                            this.mGlobalDragListener.onCrossWindowDrop(task.getTaskInfo());
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e("WindowManager", "Failed to call global drag listener for cross-window drop", e);
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            this.mCallback.get().postReportDropResult();
        }
    }

    boolean notifyUnhandledDrop(android.view.DragEvent dragEvent, java.lang.String str) {
        boolean z = (this.mDragState.mFlags & 4352) == 0;
        boolean z2 = (this.mDragState.mFlags & 8192) != 0;
        if (!com.android.window.flags.Flags.delegateUnhandledDrags() || this.mGlobalDragListener == null || !z2 || z) {
            android.util.Slog.d("WindowManager", "Skipping unhandled listener (listener=" + this.mGlobalDragListener + ", flags=" + this.mDragState.mFlags + ")");
            return false;
        }
        android.util.Slog.d("WindowManager", "Sending DROP to unhandled listener (" + str + ")");
        try {
            sendTimeoutMessage(4, null, DRAG_TIMEOUT_MS);
            this.mGlobalDragListener.onUnhandledDrop(dragEvent, new android.window.IUnhandledDragCallback.Stub() { // from class: com.android.server.wm.DragDropController.3
                public void notifyUnhandledDropComplete(boolean z3) {
                    android.util.Slog.d("WindowManager", "Unhandled listener finished handling DROP");
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.DragDropController.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.DragDropController.this.onUnhandledDropCallback(z3);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e("WindowManager", "Failed to call global drag listener for unhandled drop", e);
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUnhandledDropCallback(boolean z) {
        this.mHandler.removeMessages(4, null);
        this.mDragState.mDragResult = z;
        this.mDragState.mRelinquishDragSurfaceToDropTarget = z;
        this.mDragState.closeLocked();
    }

    boolean hasPendingUnhandledDropCallback() {
        return this.mHandler.hasMessages(4);
    }

    void cancelDragAndDrop(android.os.IBinder iBinder, boolean z) {
        android.util.Slog.d("WindowManager", "cancelDragAndDrop");
        this.mCallback.get().preCancelDragAndDrop(iBinder);
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mDragState == null) {
                        android.util.Slog.w("WindowManager", "cancelDragAndDrop() without prepareDrag()");
                        throw new java.lang.IllegalStateException("cancelDragAndDrop() without prepareDrag()");
                    }
                    if (this.mDragState.mToken != iBinder) {
                        android.util.Slog.w("WindowManager", "cancelDragAndDrop() does not match prepareDrag()");
                        throw new java.lang.IllegalStateException("cancelDragAndDrop() does not match prepareDrag()");
                    }
                    this.mDragState.mDragResult = false;
                    this.mDragState.cancelDragLocked(z);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            this.mCallback.get().postCancelDragAndDrop();
        }
    }

    void handleMotionEvent(boolean z, float f, float f2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!dragDropActiveLocked()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mDragState.updateDragSurfaceLocked(z, f, f2);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void dragRecipientEntered(android.view.IWindow iWindow) {
        android.util.Slog.d("WindowManager", "Drag into new candidate view @ " + iWindow.asBinder());
        this.mCallback.get().dragRecipientEntered(iWindow);
    }

    void dragRecipientExited(android.view.IWindow iWindow) {
        android.util.Slog.d("WindowManager", "Drag from old candidate view @ " + iWindow.asBinder());
        this.mCallback.get().dragRecipientExited(iWindow);
    }

    void sendHandlerMessage(int i, java.lang.Object obj) {
        this.mHandler.obtainMessage(i, obj).sendToTarget();
    }

    void sendTimeoutMessage(int i, java.lang.Object obj, long j) {
        this.mHandler.removeMessages(i, obj);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i, obj), j);
    }

    void onDragStateClosedLocked(com.android.server.wm.DragState dragState) {
        if (this.mDragState != dragState) {
            android.util.Slog.wtf("WindowManager", "Unknown drag state is closed");
        } else {
            this.mDragState = null;
        }
    }

    void reportDropWindow(android.os.IBinder iBinder, float f, float f2) {
        if (this.mDragState == null) {
            android.util.Slog.w("WindowManager", "Drag state is closed.");
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDragState.reportDropWindowLock(iBinder, f, f2);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean dropForAccessibility(android.view.IWindow iWindow, float f, float f2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean isEnabled = getAccessibilityManager().isEnabled();
                if (!dragDropActiveLocked()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (!this.mDragState.isAccessibilityDragDrop() || !isEnabled) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.WindowState windowForClientLocked = this.mService.windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                if (!this.mDragState.isWindowNotified(windowForClientLocked)) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean reportDropWindowLock = this.mDragState.reportDropWindowLock(windowForClientLocked.mInputChannelToken, f, f2);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return reportDropWindowLock;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    android.view.accessibility.AccessibilityManager getAccessibilityManager() {
        return (android.view.accessibility.AccessibilityManager) this.mService.mContext.getSystemService("accessibility");
    }

    private class DragHandler extends android.os.Handler {
        private final com.android.server.wm.WindowManagerService mService;

        DragHandler(com.android.server.wm.WindowManagerService windowManagerService, android.os.Looper looper) {
            super(looper);
            this.mService = windowManagerService;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    android.util.Slog.w("WindowManager", "Timeout ending drag to win " + ((android.os.IBinder) message.obj));
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (com.android.server.wm.DragDropController.this.mDragState != null) {
                                com.android.server.wm.DragDropController.this.mDragState.endDragLocked(false, false);
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 1:
                    android.util.Slog.d("WindowManager", "Drag ending; tearing down input channel");
                    com.android.server.wm.DragState.InputInterceptor inputInterceptor = (com.android.server.wm.DragState.InputInterceptor) message.obj;
                    if (inputInterceptor == null) {
                        return;
                    }
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            inputInterceptor.tearDown();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 2:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                        try {
                            if (com.android.server.wm.DragDropController.this.mDragState == null) {
                                android.util.Slog.wtf("WindowManager", "mDragState unexpectedly became null while playing animation");
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            } else {
                                com.android.server.wm.DragDropController.this.mDragState.closeLocked();
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                case 3:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock4 = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock4) {
                        try {
                            this.mService.mTransactionFactory.get().remove((android.view.SurfaceControl) message.obj).apply();
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 4:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock5 = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock5) {
                        try {
                            com.android.server.wm.DragDropController.this.onUnhandledDropCallback(false);
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }
    }
}
