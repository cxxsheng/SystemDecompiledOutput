package com.android.server.wm;

/* loaded from: classes3.dex */
final class InputMonitor {
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private int mDisplayHeight;
    private final int mDisplayId;
    private boolean mDisplayRemoved;
    private int mDisplayWidth;
    private final android.os.Handler mHandler;
    private final android.view.SurfaceControl.Transaction mInputTransaction;
    private final com.android.server.wm.WindowManagerService mService;
    private boolean mUpdateInputWindowsImmediately;
    private boolean mUpdateInputWindowsPending;
    android.os.IBinder mInputFocus = null;
    long mInputFocusRequestTimeMillis = 0;
    private boolean mUpdateInputWindowsNeeded = true;
    private final android.graphics.Region mTmpRegion = new android.graphics.Region();
    private final java.util.ArrayList<com.android.server.wm.InputConsumerImpl> mInputConsumers = new java.util.ArrayList<>();
    private java.lang.ref.WeakReference<com.android.server.wm.ActivityRecord> mActiveRecentsActivity = null;
    private java.lang.ref.WeakReference<com.android.server.wm.Task> mActiveRecentsLayerRef = null;
    private final com.android.server.wm.InputMonitor.UpdateInputWindows mUpdateInputWindows = new com.android.server.wm.InputMonitor.UpdateInputWindows();
    private final com.android.server.wm.InputMonitor.UpdateInputForAllWindowsConsumer mUpdateInputForAllWindowsConsumer = new com.android.server.wm.InputMonitor.UpdateInputForAllWindowsConsumer();

    private class UpdateInputWindows implements java.lang.Runnable {
        private UpdateInputWindows() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.InputMonitor.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.InputMonitor.this.mUpdateInputWindowsPending = false;
                    com.android.server.wm.InputMonitor.this.mUpdateInputWindowsNeeded = false;
                    if (com.android.server.wm.InputMonitor.this.mDisplayRemoved) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.InputMonitor.this.mUpdateInputForAllWindowsConsumer.updateInputWindows(com.android.server.wm.InputMonitor.this.mService.mDragDropController.dragDropActiveLocked());
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    InputMonitor(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayId = displayContent.getDisplayId();
        this.mInputTransaction = this.mService.mTransactionFactory.get();
        this.mHandler = this.mService.mAnimationHandler;
    }

    void onDisplayRemoved() {
        this.mHandler.removeCallbacks(this.mUpdateInputWindows);
        this.mService.mTransactionFactory.get().addWindowInfosReportedListener(new java.lang.Runnable() { // from class: com.android.server.wm.InputMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.InputMonitor.this.lambda$onDisplayRemoved$0();
            }
        }).apply();
        this.mDisplayRemoved = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayRemoved$0() {
        this.mService.mInputManager.onDisplayRemoved(this.mDisplayId);
    }

    private void addInputConsumer(com.android.server.wm.InputConsumerImpl inputConsumerImpl) {
        this.mInputConsumers.add(inputConsumerImpl);
        inputConsumerImpl.linkToDeathRecipient();
        inputConsumerImpl.layout(this.mInputTransaction, this.mDisplayWidth, this.mDisplayHeight);
        updateInputWindowsLw(true);
    }

    boolean destroyInputConsumer(android.os.IBinder iBinder) {
        for (int i = 0; i < this.mInputConsumers.size(); i++) {
            com.android.server.wm.InputConsumerImpl inputConsumerImpl = this.mInputConsumers.get(i);
            if (inputConsumerImpl != null && inputConsumerImpl.mToken == iBinder) {
                inputConsumerImpl.disposeChannelsLw(this.mInputTransaction);
                this.mInputConsumers.remove(inputConsumerImpl);
                updateInputWindowsLw(true);
                return true;
            }
        }
        return false;
    }

    com.android.server.wm.InputConsumerImpl getInputConsumer(java.lang.String str) {
        for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
            com.android.server.wm.InputConsumerImpl inputConsumerImpl = this.mInputConsumers.get(size);
            if (inputConsumerImpl.mName.equals(str)) {
                return inputConsumerImpl;
            }
        }
        return null;
    }

    void layoutInputConsumers(int i, int i2) {
        if (this.mDisplayWidth == i && this.mDisplayHeight == i2) {
            return;
        }
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        try {
            android.os.Trace.traceBegin(32L, "layoutInputConsumer");
            for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
                this.mInputConsumers.get(size).layout(this.mInputTransaction, i, i2);
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    void resetInputConsumers(android.view.SurfaceControl.Transaction transaction) {
        for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
            this.mInputConsumers.get(size).hide(transaction);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    void createInputConsumer(android.os.IBinder iBinder, java.lang.String str, android.view.InputChannel inputChannel, int i, android.os.UserHandle userHandle) {
        char c;
        com.android.server.wm.InputConsumerImpl inputConsumer = getInputConsumer(str);
        if (inputConsumer != null && inputConsumer.mClientUser.equals(userHandle)) {
            throw new java.lang.IllegalStateException("Existing input consumer found with name: " + str + ", display: " + this.mDisplayId + ", user: " + userHandle);
        }
        com.android.server.wm.InputConsumerImpl inputConsumerImpl = new com.android.server.wm.InputConsumerImpl(this.mService, iBinder, str, inputChannel, i, userHandle, this.mDisplayId, this.mInputTransaction);
        switch (str.hashCode()) {
            case -1525776435:
                if (str.equals("recents_animation_input_consumer")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1024719987:
                if (str.equals("pip_input_consumer")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1415830696:
                if (str.equals("wallpaper_input_consumer")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                inputConsumerImpl.mWindowHandle.inputConfig |= 32;
                break;
            case 1:
                break;
            case 2:
                inputConsumerImpl.mWindowHandle.inputConfig &= -5;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Illegal input consumer : " + str + ", display: " + this.mDisplayId);
        }
        addInputConsumer(inputConsumerImpl);
    }

    @com.android.internal.annotations.VisibleForTesting
    void populateInputWindowHandle(com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper, com.android.server.wm.WindowState windowState) {
        com.android.server.wm.TaskFragment taskFragment;
        android.view.SurfaceControl surfaceControl = null;
        inputWindowHandleWrapper.setInputApplicationHandle(windowState.mActivityRecord != null ? windowState.mActivityRecord.getInputApplicationHandle(false) : null);
        inputWindowHandleWrapper.setToken(windowState.mInputChannelToken);
        inputWindowHandleWrapper.setDispatchingTimeoutMillis(windowState.getInputDispatchingTimeoutMillis());
        inputWindowHandleWrapper.setTouchOcclusionMode(windowState.getTouchOcclusionMode());
        inputWindowHandleWrapper.setPaused(windowState.mActivityRecord != null && windowState.mActivityRecord.paused);
        inputWindowHandleWrapper.setWindowToken(windowState.mClient.asBinder());
        inputWindowHandleWrapper.setName(windowState.getName());
        int i = windowState.mAttrs.flags;
        if (windowState.mAttrs.isModal()) {
            i |= 32;
        }
        inputWindowHandleWrapper.setLayoutParamsFlags(i);
        inputWindowHandleWrapper.setInputConfigMasked(com.android.server.wm.InputConfigAdapter.getInputConfigFromWindowParams(windowState.mAttrs.type, i, windowState.mAttrs.inputFeatures), com.android.server.wm.InputConfigAdapter.getMask());
        inputWindowHandleWrapper.setFocusable(windowState.canReceiveKeys() && (this.mDisplayContent.hasOwnFocus() || this.mDisplayContent.isOnTop()));
        inputWindowHandleWrapper.setHasWallpaper(this.mDisplayContent.mWallpaperController.isWallpaperTarget(windowState) && !this.mService.mPolicy.isKeyguardShowing() && windowState.mAttrs.areWallpaperTouchEventsEnabled());
        inputWindowHandleWrapper.setSurfaceInset(windowState.mAttrs.surfaceInsets.left);
        inputWindowHandleWrapper.setScaleFactor(windowState.mGlobalScale != 1.0f ? 1.0f / windowState.mGlobalScale : 1.0f);
        com.android.server.wm.Task task = windowState.getTask();
        if (task != null) {
            if (task.isOrganized() && task.getWindowingMode() != 1) {
                r2 = windowState.mTouchableInsets != 3;
                if (windowState.mAttrs.isModal() && (taskFragment = windowState.getTaskFragment()) != null) {
                    surfaceControl = taskFragment.getSurfaceControl();
                }
            } else if (task.cropWindowsToRootTaskBounds() && !windowState.inFreeformWindowingMode()) {
                surfaceControl = task.getRootTask().getSurfaceControl();
            }
        }
        inputWindowHandleWrapper.setReplaceTouchableRegionWithCrop(r2);
        inputWindowHandleWrapper.setTouchableRegionCrop(surfaceControl);
        if (!r2) {
            windowState.getSurfaceTouchableRegion(this.mTmpRegion, windowState.mAttrs);
            inputWindowHandleWrapper.setTouchableRegion(this.mTmpRegion);
        }
    }

    void setUpdateInputWindowsNeededLw() {
        this.mUpdateInputWindowsNeeded = true;
    }

    void updateInputWindowsLw(boolean z) {
        if (!z && !this.mUpdateInputWindowsNeeded) {
            return;
        }
        scheduleUpdateInputWindows();
    }

    private void scheduleUpdateInputWindows() {
        if (!this.mDisplayRemoved && !this.mUpdateInputWindowsPending) {
            this.mUpdateInputWindowsPending = true;
            this.mHandler.post(this.mUpdateInputWindows);
        }
    }

    void updateInputWindowsImmediately(android.view.SurfaceControl.Transaction transaction) {
        this.mHandler.removeCallbacks(this.mUpdateInputWindows);
        this.mUpdateInputWindowsImmediately = true;
        this.mUpdateInputWindows.run();
        this.mUpdateInputWindowsImmediately = false;
        transaction.merge(this.mInputTransaction);
    }

    void setInputFocusLw(com.android.server.wm.WindowState windowState, boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -8553129529717081823L, 4, null, java.lang.String.valueOf(windowState), java.lang.Long.valueOf(this.mDisplayId));
        if ((windowState != null ? windowState.mInputChannelToken : null) == this.mInputFocus) {
            return;
        }
        if (windowState != null && windowState.canReceiveKeys()) {
            windowState.mToken.paused = false;
        }
        setUpdateInputWindowsNeededLw();
        if (z) {
            updateInputWindowsLw(false);
        }
    }

    void setActiveRecents(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task) {
        boolean z = false;
        boolean z2 = activityRecord == null;
        if (this.mActiveRecentsActivity != null && this.mActiveRecentsLayerRef != null) {
            z = true;
        }
        this.mActiveRecentsActivity = z2 ? null : new java.lang.ref.WeakReference<>(activityRecord);
        this.mActiveRecentsLayerRef = z2 ? null : new java.lang.ref.WeakReference<>(task);
        if (z2 && z) {
            setUpdateInputWindowsNeededLw();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T getWeak(java.lang.ref.WeakReference<T> weakReference) {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputFocusRequest(com.android.server.wm.InputConsumerImpl inputConsumerImpl) {
        com.android.server.wm.WindowState windowState = this.mDisplayContent.mCurrentFocus;
        com.android.server.wm.ActivityRecord activityRecord = null;
        if (inputConsumerImpl != null && windowState != null) {
            com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
            if ((recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.mActivityRecord)) || (getWeak(this.mActiveRecentsActivity) != null && windowState.inTransition() && windowState.isActivityTypeHomeOrRecents())) {
                if (this.mInputFocus != inputConsumerImpl.mWindowHandle.token) {
                    requestFocus(inputConsumerImpl.mWindowHandle.token, inputConsumerImpl.mName);
                }
                if (this.mDisplayContent.mInputMethodWindow != null && this.mDisplayContent.mInputMethodWindow.isVisible()) {
                    if (!this.mDisplayContent.isImeAttachedToApp()) {
                        com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal = (com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class);
                        if (inputMethodManagerInternal != null) {
                            inputMethodManagerInternal.hideAllInputMethods(19, this.mDisplayContent.getDisplayId());
                        }
                        if (this.mDisplayContent.getImeInputTarget() != null) {
                            activityRecord = this.mDisplayContent.getImeInputTarget().getActivityRecord();
                        }
                        if (activityRecord != null) {
                            this.mDisplayContent.removeImeSurfaceImmediately();
                            if (activityRecord.getTask() != null) {
                                this.mDisplayContent.mAtmService.takeTaskSnapshot(activityRecord.getTask().mTaskId, true);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    com.android.server.inputmethod.InputMethodManagerInternal.get().updateImeWindowStatus(true, this.mDisplayContent.getDisplayId());
                    return;
                }
                return;
            }
        }
        android.os.IBinder iBinder = windowState != null ? windowState.mInputChannelToken : null;
        if (iBinder == null) {
            if (inputConsumerImpl != null && inputConsumerImpl.mWindowHandle != null && this.mInputFocus == inputConsumerImpl.mWindowHandle.token) {
                return;
            }
            if (this.mDisplayContent.mFocusedApp != null && this.mInputFocus != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 4027486077547983902L, 0, null, java.lang.String.valueOf(this.mDisplayContent.mFocusedApp.getName()));
                android.util.EventLog.writeEvent(62001, "Requesting to set focus to null window", "reason=UpdateInputWindows");
                this.mInputTransaction.removeCurrentInputFocus(this.mDisplayId);
            }
            this.mInputFocus = null;
            return;
        }
        if (!windowState.mWinAnimator.hasSurface() || !windowState.mInputWindowHandle.isFocusable()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -8537908614386667236L, 0, null, java.lang.String.valueOf(windowState));
            this.mInputFocus = null;
        } else {
            requestFocus(iBinder, windowState.getName());
        }
    }

    private void requestFocus(android.os.IBinder iBinder, java.lang.String str) {
        if (iBinder == this.mInputFocus) {
            return;
        }
        this.mInputFocus = iBinder;
        this.mInputFocusRequestTimeMillis = android.os.SystemClock.uptimeMillis();
        this.mInputTransaction.setFocusedWindow(this.mInputFocus, str, this.mDisplayId);
        android.util.EventLog.writeEvent(62001, "Focus request " + str, "reason=UpdateInputWindows");
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -6346673514571615151L, 0, null, java.lang.String.valueOf(str));
    }

    void setFocusedAppLw(com.android.server.wm.ActivityRecord activityRecord) {
        this.mService.mInputManager.setFocusedApplication(this.mDisplayId, activityRecord != null ? activityRecord.getInputApplicationHandle(true) : null);
    }

    public void pauseDispatchingLw(com.android.server.wm.WindowToken windowToken) {
        if (!windowToken.paused) {
            windowToken.paused = true;
            updateInputWindowsLw(true);
        }
    }

    public void resumeDispatchingLw(com.android.server.wm.WindowToken windowToken) {
        if (windowToken.paused) {
            windowToken.paused = false;
            updateInputWindowsLw(true);
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        if (!this.mInputConsumers.isEmpty()) {
            printWriter.println(str + "InputConsumers:");
            for (int i = 0; i < this.mInputConsumers.size(); i++) {
                com.android.server.wm.InputConsumerImpl inputConsumerImpl = this.mInputConsumers.get(i);
                inputConsumerImpl.dump(printWriter, inputConsumerImpl.mName, str);
            }
        }
    }

    private final class UpdateInputForAllWindowsConsumer implements java.util.function.Consumer<com.android.server.wm.WindowState> {
        private boolean mAddPipInputConsumerHandle;
        private boolean mAddRecentsAnimationInputConsumerHandle;
        private boolean mAddWallpaperInputConsumerHandle;
        private boolean mInDrag;
        com.android.server.wm.InputConsumerImpl mPipInputConsumer;
        com.android.server.wm.InputConsumerImpl mRecentsAnimationInputConsumer;
        private final android.graphics.Rect mTmpRect;
        com.android.server.wm.InputConsumerImpl mWallpaperInputConsumer;

        private UpdateInputForAllWindowsConsumer() {
            this.mTmpRect = new android.graphics.Rect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateInputWindows(boolean z) {
            android.os.Trace.traceBegin(32L, "updateInputWindows");
            this.mPipInputConsumer = com.android.server.wm.InputMonitor.this.getInputConsumer("pip_input_consumer");
            this.mWallpaperInputConsumer = com.android.server.wm.InputMonitor.this.getInputConsumer("wallpaper_input_consumer");
            this.mRecentsAnimationInputConsumer = com.android.server.wm.InputMonitor.this.getInputConsumer("recents_animation_input_consumer");
            this.mAddPipInputConsumerHandle = this.mPipInputConsumer != null;
            this.mAddWallpaperInputConsumerHandle = this.mWallpaperInputConsumer != null;
            this.mAddRecentsAnimationInputConsumerHandle = this.mRecentsAnimationInputConsumer != null;
            this.mInDrag = z;
            com.android.server.wm.InputMonitor.this.resetInputConsumers(com.android.server.wm.InputMonitor.this.mInputTransaction);
            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) com.android.server.wm.InputMonitor.getWeak(com.android.server.wm.InputMonitor.this.mActiveRecentsActivity);
            if (this.mAddRecentsAnimationInputConsumerHandle && activityRecord != null && activityRecord.getSurfaceControl() != null) {
                com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) com.android.server.wm.InputMonitor.getWeak(com.android.server.wm.InputMonitor.this.mActiveRecentsLayerRef);
                if (windowContainer == null) {
                    windowContainer = activityRecord;
                }
                if (windowContainer.getSurfaceControl() != null) {
                    com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow();
                    if (findMainWindow != null) {
                        findMainWindow.getBounds(this.mTmpRect);
                        this.mRecentsAnimationInputConsumer.mWindowHandle.touchableRegion.set(this.mTmpRect);
                    }
                    this.mRecentsAnimationInputConsumer.show(com.android.server.wm.InputMonitor.this.mInputTransaction, windowContainer);
                    this.mAddRecentsAnimationInputConsumerHandle = false;
                }
            }
            com.android.server.wm.InputMonitor.this.mDisplayContent.forAllWindows((java.util.function.Consumer<com.android.server.wm.WindowState>) this, true);
            com.android.server.wm.InputMonitor.this.updateInputFocusRequest(this.mRecentsAnimationInputConsumer);
            if (!com.android.server.wm.InputMonitor.this.mUpdateInputWindowsImmediately) {
                com.android.server.wm.InputMonitor.this.mDisplayContent.getPendingTransaction().merge(com.android.server.wm.InputMonitor.this.mInputTransaction);
                com.android.server.wm.InputMonitor.this.mDisplayContent.scheduleAnimation();
            }
            android.os.Trace.traceEnd(32L);
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.wm.WindowState windowState) {
            com.android.server.wm.DisplayArea targetAppDisplayArea;
            com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper = windowState.mInputWindowHandle;
            if (windowState.mInputChannelToken == null || windowState.mRemoved || !windowState.canReceiveTouchInput()) {
                if (windowState.mWinAnimator.hasSurface()) {
                    com.android.server.wm.InputMonitor.populateOverlayInputInfo(inputWindowHandleWrapper, windowState);
                    com.android.server.wm.InputMonitor.setInputWindowInfoIfNeeded(com.android.server.wm.InputMonitor.this.mInputTransaction, windowState.mWinAnimator.mSurfaceController.mSurfaceControl, inputWindowHandleWrapper);
                    return;
                }
                return;
            }
            com.android.server.wm.RecentsAnimationController recentsAnimationController = com.android.server.wm.InputMonitor.this.mService.getRecentsAnimationController();
            boolean z = recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.mActivityRecord);
            if (this.mAddRecentsAnimationInputConsumerHandle && z && recentsAnimationController.updateInputConsumerForApp(this.mRecentsAnimationInputConsumer.mWindowHandle) && (targetAppDisplayArea = recentsAnimationController.getTargetAppDisplayArea()) != null) {
                this.mRecentsAnimationInputConsumer.reparent(com.android.server.wm.InputMonitor.this.mInputTransaction, targetAppDisplayArea);
                this.mRecentsAnimationInputConsumer.show(com.android.server.wm.InputMonitor.this.mInputTransaction, 2147483645);
                this.mAddRecentsAnimationInputConsumerHandle = false;
            }
            if (windowState.inPinnedWindowingMode() && this.mAddPipInputConsumerHandle) {
                com.android.server.wm.Task rootTask = windowState.getTask().getRootTask();
                this.mPipInputConsumer.mWindowHandle.replaceTouchableRegionWithCrop(rootTask.getSurfaceControl());
                com.android.server.wm.TaskDisplayArea displayArea = rootTask.getDisplayArea();
                if (displayArea != null) {
                    this.mPipInputConsumer.layout(com.android.server.wm.InputMonitor.this.mInputTransaction, rootTask.getBounds());
                    this.mPipInputConsumer.reparent(com.android.server.wm.InputMonitor.this.mInputTransaction, displayArea);
                    this.mPipInputConsumer.show(com.android.server.wm.InputMonitor.this.mInputTransaction, 2147483646);
                    this.mAddPipInputConsumerHandle = false;
                }
            }
            if (this.mAddWallpaperInputConsumerHandle && windowState.mAttrs.type == 2013 && windowState.isVisible()) {
                this.mWallpaperInputConsumer.mWindowHandle.replaceTouchableRegionWithCrop((android.view.SurfaceControl) null);
                this.mWallpaperInputConsumer.show(com.android.server.wm.InputMonitor.this.mInputTransaction, windowState);
                this.mAddWallpaperInputConsumerHandle = false;
            }
            if (this.mInDrag && windowState.isVisible() && windowState.getDisplayContent().isDefaultDisplay) {
                com.android.server.wm.InputMonitor.this.mService.mDragDropController.sendDragStartedIfNeededLocked(windowState);
            }
            com.android.server.wm.InputMonitor.this.mService.mKeyInterceptionInfoForToken.put(windowState.mInputChannelToken, windowState.getKeyInterceptionInfo());
            if (windowState.mWinAnimator.hasSurface()) {
                com.android.server.wm.InputMonitor.this.populateInputWindowHandle(inputWindowHandleWrapper, windowState);
                com.android.server.wm.InputMonitor.setInputWindowInfoIfNeeded(com.android.server.wm.InputMonitor.this.mInputTransaction, windowState.mWinAnimator.mSurfaceController.mSurfaceControl, inputWindowHandleWrapper);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static void setInputWindowInfoIfNeeded(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper) {
        if (inputWindowHandleWrapper.isChanged()) {
            inputWindowHandleWrapper.applyChangesToSurface(transaction, surfaceControl);
        }
    }

    static void populateOverlayInputInfo(com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper, com.android.server.wm.WindowState windowState) {
        populateOverlayInputInfo(inputWindowHandleWrapper);
        inputWindowHandleWrapper.setTouchOcclusionMode(windowState.getTouchOcclusionMode());
    }

    @com.android.internal.annotations.VisibleForTesting
    static void populateOverlayInputInfo(com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper) {
        inputWindowHandleWrapper.setDispatchingTimeoutMillis(0L);
        inputWindowHandleWrapper.setFocusable(false);
        inputWindowHandleWrapper.setToken(null);
        inputWindowHandleWrapper.setScaleFactor(1.0f);
        inputWindowHandleWrapper.setLayoutParamsType(2);
        inputWindowHandleWrapper.setInputConfigMasked(com.android.server.wm.InputConfigAdapter.getInputConfigFromWindowParams(2, 16, 1), com.android.server.wm.InputConfigAdapter.getMask());
        inputWindowHandleWrapper.clearTouchableRegion();
        inputWindowHandleWrapper.setTouchableRegionCrop(null);
    }

    static void setTrustedOverlayInputInfo(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, java.lang.String str) {
        com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper = new com.android.server.wm.InputWindowHandleWrapper(new android.view.InputWindowHandle((android.view.InputApplicationHandle) null, i));
        inputWindowHandleWrapper.setName(str);
        inputWindowHandleWrapper.setLayoutParamsType(2015);
        inputWindowHandleWrapper.setTrustedOverlay(transaction, surfaceControl, true);
        populateOverlayInputInfo(inputWindowHandleWrapper);
        setInputWindowInfoIfNeeded(transaction, surfaceControl, inputWindowHandleWrapper);
    }

    static boolean isTrustedOverlay(int i) {
        return i == 2039 || i == 2011 || i == 2012 || i == 2027 || i == 2000 || i == 2040 || i == 2019 || i == 2024 || i == 2015 || i == 2034 || i == 2032 || i == 2022 || i == 2031 || i == 2041;
    }
}
