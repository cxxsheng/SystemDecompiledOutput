package com.android.server.wm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class InputManagerCallback implements com.android.server.input.InputManagerService.WindowManagerCallbacks {
    private static final java.lang.String TAG = "WindowManager";
    private boolean mInputDevicesReady;
    private boolean mInputDispatchEnabled;
    private boolean mInputDispatchFrozen;
    private final com.android.server.wm.WindowManagerService mService;
    private final java.lang.Object mInputDevicesReadyMonitor = new java.lang.Object();
    private java.lang.String mInputFreezeReason = null;

    public InputManagerCallback(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyInputChannelBroken(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowState = this.mService.mInputToWindowMap.get(iBinder);
                if (windowState != null) {
                    android.util.Slog.i(TAG, "WINDOW DIED " + windowState);
                    windowState.removeIfPossible();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyNoFocusedWindowAnr(@android.annotation.NonNull android.view.InputApplicationHandle inputApplicationHandle) {
        this.mService.mAnrController.notifyAppUnresponsive(inputApplicationHandle, com.android.internal.os.TimeoutRecord.forInputDispatchNoFocusedWindow(timeoutMessage(java.util.OptionalInt.empty(), "Application does not have a focused window")));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyWindowUnresponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt, java.lang.String str) {
        this.mService.mAnrController.notifyWindowUnresponsive(iBinder, optionalInt, com.android.internal.os.TimeoutRecord.forInputDispatchWindowUnresponsive(timeoutMessage(optionalInt, str)));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyWindowResponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt) {
        this.mService.mAnrController.notifyWindowResponsive(iBinder, optionalInt);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyConfigurationChanged() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wm.DisplayContent) obj).sendNewConfiguration();
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (this.mInputDevicesReadyMonitor) {
            try {
                if (!this.mInputDevicesReady) {
                    this.mInputDevicesReady = true;
                    this.mInputDevicesReadyMonitor.notifyAll();
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyPointerLocationChanged(boolean z) {
        if (this.mService.mPointerLocationEnabled == z) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.mPointerLocationEnabled = z;
                this.mService.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.InputManagerCallback.this.lambda$notifyPointerLocationChanged$0((com.android.server.wm.DisplayPolicy) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPointerLocationChanged$0(com.android.server.wm.DisplayPolicy displayPolicy) {
        displayPolicy.setPointerLocationEnabled(this.mService.mPointerLocationEnabled);
    }

    @Override // com.android.server.input.InputManagerInternal.LidSwitchCallback
    public void notifyLidSwitchChanged(long j, boolean z) {
        this.mService.mPolicy.notifyLidSwitchChanged(j, z);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        this.mService.mPolicy.notifyCameraLensCoverSwitchChanged(j, z);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int interceptKeyBeforeQueueing(android.view.KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.interceptKeyBeforeQueueing(keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int interceptMotionBeforeQueueingNonInteractive(int i, int i2, int i3, long j, int i4) {
        return this.mService.mPolicy.interceptMotionBeforeQueueingNonInteractive(i, i2, i3, j, i4);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public long interceptKeyBeforeDispatching(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.interceptKeyBeforeDispatching(iBinder, keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public android.view.KeyEvent dispatchUnhandledKey(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        return this.mService.mPolicy.dispatchUnhandledKey(iBinder, keyEvent, i);
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int getPointerLayer() {
        return (this.mService.mPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 1000;
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public int getPointerDisplayId() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int i = 0;
                if (!this.mService.mForceDesktopModeOnExternalDisplays) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                for (int size = this.mService.mRoot.mChildren.size() - 1; size >= 0; size--) {
                    com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mService.mRoot.mChildren.get(size);
                    if (displayContent.getDisplayInfo().state != 1) {
                        if (displayContent.getWindowingMode() == 5) {
                            int displayId = displayContent.getDisplayId();
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return displayId;
                        }
                        if (i == 0 && displayContent.getDisplayId() != 0) {
                            i = displayContent.getDisplayId();
                        }
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void onPointerDownOutsideFocus(android.os.IBinder iBinder) {
        this.mService.mH.obtainMessage(62, iBinder).sendToTarget();
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyFocusChanged(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        com.android.server.wm.WindowManagerService.H h = this.mService.mH;
        final com.android.server.wm.WindowManagerService windowManagerService = this.mService;
        java.util.Objects.requireNonNull(windowManagerService);
        h.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.wm.WindowManagerService.this.reportFocusChanged((android.os.IBinder) obj, (android.os.IBinder) obj2);
            }
        }, iBinder, iBinder2));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyDropWindow(android.os.IBinder iBinder, float f, float f2) {
        com.android.server.wm.WindowManagerService.H h = this.mService.mH;
        final com.android.server.wm.DragDropController dragDropController = this.mService.mDragDropController;
        java.util.Objects.requireNonNull(dragDropController);
        h.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda3
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                com.android.server.wm.DragDropController.this.reportDropWindow((android.os.IBinder) obj, ((java.lang.Float) obj2).floatValue(), ((java.lang.Float) obj3).floatValue());
            }
        }, iBinder, java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2)));
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public android.view.SurfaceControl getParentSurfaceForPointers(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.e(TAG, "Failed to get parent surface for pointers on display " + i + " - DisplayContent not found.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.view.SurfaceControl overlayLayer = displayContent.getOverlayLayer();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return overlayLayer;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    @android.annotation.Nullable
    public android.view.SurfaceControl createSurfaceForGestureMonitor(java.lang.String str, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.e(TAG, "Failed to create a gesture monitor on display: " + i + " - DisplayContent not found.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.view.SurfaceControl inputOverlayLayer = displayContent.getInputOverlayLayer();
                if (inputOverlayLayer == null) {
                    android.util.Slog.e(TAG, "Failed to create a gesture monitor on display: " + i + " - Input overlay layer is not initialized.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.view.SurfaceControl build = this.mService.makeSurfaceBuilder(displayContent.getSession()).setContainerLayer().setName(str).setCallsite("createSurfaceForGestureMonitor").setParent(inputOverlayLayer).build();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return build;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerService.WindowManagerCallbacks
    public void notifyPointerDisplayIdChanged(int i, float f, float f2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.setMousePointerDisplayId(i);
                if (i == -1) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.wtf(TAG, "The mouse pointer was moved to display " + i + " that does not have a valid DisplayContent.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mService.restorePointerIconLocked(displayContent, f, f2);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean waitForInputDevicesReady(long j) {
        boolean z;
        synchronized (this.mInputDevicesReadyMonitor) {
            if (!this.mInputDevicesReady) {
                try {
                    this.mInputDevicesReadyMonitor.wait(j);
                } catch (java.lang.InterruptedException e) {
                }
            }
            z = this.mInputDevicesReady;
        }
        return z;
    }

    public void freezeInputDispatchingLw() {
        if (!this.mInputDispatchFrozen) {
            this.mInputDispatchFrozen = true;
            updateInputDispatchModeLw();
        }
    }

    public void thawInputDispatchingLw() {
        if (this.mInputDispatchFrozen) {
            this.mInputDispatchFrozen = false;
            this.mInputFreezeReason = null;
            updateInputDispatchModeLw();
        }
    }

    public void setEventDispatchingLw(boolean z) {
        if (this.mInputDispatchEnabled != z) {
            this.mInputDispatchEnabled = z;
            updateInputDispatchModeLw();
        }
    }

    private void updateInputDispatchModeLw() {
        this.mService.mInputManager.setInputDispatchMode(this.mInputDispatchEnabled, this.mInputDispatchFrozen);
    }

    private java.lang.String timeoutMessage(java.util.OptionalInt optionalInt, java.lang.String str) {
        java.lang.String format = str == null ? "Input dispatching timed out." : java.lang.String.format("Input dispatching timed out (%s).", str);
        if (optionalInt.isEmpty()) {
            return format;
        }
        android.gui.StalledTransactionInfo stalledTransactionInfo = android.view.SurfaceControl.getStalledTransactionInfo(optionalInt.getAsInt());
        if (stalledTransactionInfo == null) {
            return format;
        }
        return java.lang.String.format("%s Buffer processing for the associated surface is stuck due to an unsignaled fence (window=%s, bufferId=0x%016X, frameNumber=%s). This potentially indicates a GPU hang.", format, stalledTransactionInfo.layerName, java.lang.Long.valueOf(stalledTransactionInfo.bufferId), java.lang.Long.valueOf(stalledTransactionInfo.frameNumber));
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        if (this.mInputFreezeReason != null) {
            printWriter.println(str + "mInputFreezeReason=" + this.mInputFreezeReason);
        }
    }
}
