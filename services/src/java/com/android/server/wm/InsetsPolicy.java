package com.android.server.wm;

/* loaded from: classes3.dex */
class InsetsPolicy {
    public static final int CONTROLLABLE_TYPES = (android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars()) | android.view.WindowInsets.Type.ime();
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private com.android.server.wm.InsetsControlTarget mFakeNavControlTarget;
    private com.android.server.wm.InsetsControlTarget mFakeStatusControlTarget;
    private com.android.server.wm.WindowState mFocusedWin;
    private int mForcedShowingTypes;
    private final boolean mHideNavBarForKeyboard;
    private final com.android.server.wm.InsetsControlTarget mPermanentControlTarget;
    private final com.android.server.wm.DisplayPolicy mPolicy;
    private int mShowingTransientTypes;
    private final com.android.server.wm.InsetsStateController mStateController;
    private final com.android.server.wm.InsetsControlTarget mTransientControlTarget;
    private final com.android.server.wm.InsetsPolicy.BarWindow mStatusBar = new com.android.server.wm.InsetsPolicy.BarWindow(1);
    private final com.android.server.wm.InsetsPolicy.BarWindow mNavBar = new com.android.server.wm.InsetsPolicy.BarWindow(2);

    InsetsPolicy(com.android.server.wm.InsetsStateController insetsStateController, com.android.server.wm.DisplayContent displayContent) {
        this.mStateController = insetsStateController;
        this.mDisplayContent = displayContent;
        this.mPolicy = displayContent.getDisplayPolicy();
        this.mHideNavBarForKeyboard = this.mPolicy.getContext().getResources().getBoolean(android.R.bool.config_hasPermanentDpad);
        this.mTransientControlTarget = new com.android.server.wm.InsetsPolicy.ControlTarget(displayContent, "TransientControlTarget");
        this.mPermanentControlTarget = new com.android.server.wm.InsetsPolicy.ControlTarget(displayContent, "PermanentControlTarget");
    }

    void updateBarControlTarget(@android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        com.android.server.wm.InsetsControlTarget insetsControlTarget;
        if (this.mFocusedWin != windowState) {
            abortTransient();
        }
        this.mFocusedWin = windowState;
        com.android.server.wm.WindowState notificationShade = this.mPolicy.getNotificationShade();
        com.android.server.wm.WindowState topFullscreenOpaqueWindow = this.mPolicy.getTopFullscreenOpaqueWindow();
        com.android.server.wm.InsetsControlTarget statusControlTarget = getStatusControlTarget(windowState, false);
        com.android.server.wm.InsetsControlTarget insetsControlTarget2 = null;
        if (statusControlTarget == this.mTransientControlTarget) {
            insetsControlTarget = getStatusControlTarget(windowState, true);
        } else if (statusControlTarget == notificationShade) {
            insetsControlTarget = getStatusControlTarget(topFullscreenOpaqueWindow, true);
        } else {
            insetsControlTarget = null;
        }
        this.mFakeStatusControlTarget = insetsControlTarget;
        com.android.server.wm.InsetsControlTarget navControlTarget = getNavControlTarget(windowState, false);
        if (navControlTarget == this.mTransientControlTarget) {
            insetsControlTarget2 = getNavControlTarget(windowState, true);
        } else if (navControlTarget == notificationShade) {
            insetsControlTarget2 = getNavControlTarget(topFullscreenOpaqueWindow, true);
        }
        this.mFakeNavControlTarget = insetsControlTarget2;
        this.mStateController.onBarControlTargetChanged(statusControlTarget, this.mFakeStatusControlTarget, navControlTarget, this.mFakeNavControlTarget);
        this.mStatusBar.updateVisibility(statusControlTarget, android.view.WindowInsets.Type.statusBars());
        this.mNavBar.updateVisibility(navControlTarget, android.view.WindowInsets.Type.navigationBars());
    }

    boolean hasHiddenSources(int i) {
        android.view.InsetsState rawInsetsState = this.mStateController.getRawInsetsState();
        for (int sourceSize = rawInsetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = rawInsetsState.sourceAt(sourceSize);
            if ((sourceAt.getType() & i) != 0 && !sourceAt.getFrame().isEmpty() && !sourceAt.isVisible()) {
                return true;
            }
        }
        return false;
    }

    void showTransient(int i, boolean z) {
        int i2 = this.mShowingTransientTypes;
        android.view.InsetsState rawInsetsState = this.mStateController.getRawInsetsState();
        for (int sourceSize = rawInsetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = rawInsetsState.sourceAt(sourceSize);
            if (!sourceAt.isVisible()) {
                int type = sourceAt.getType();
                if ((sourceAt.getType() & i) != 0) {
                    i2 |= type;
                }
            }
        }
        if (this.mShowingTransientTypes != i2) {
            this.mShowingTransientTypes = i2;
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.showTransient(this.mDisplayContent.getDisplayId(), i2, z);
            }
            updateBarControlTarget(this.mFocusedWin);
            dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, (i2 & (android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars())) != 0, z);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.InsetsControlTarget getTransientControlTarget() {
        return this.mTransientControlTarget;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.InsetsControlTarget getPermanentControlTarget() {
        return this.mPermanentControlTarget;
    }

    void hideTransient() {
        if (this.mShowingTransientTypes == 0) {
            return;
        }
        dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, false, false);
        this.mShowingTransientTypes = 0;
        updateBarControlTarget(this.mFocusedWin);
    }

    boolean isTransient(int i) {
        return (i & this.mShowingTransientTypes) != 0;
    }

    android.view.InsetsState adjustInsetsForWindow(com.android.server.wm.WindowState windowState, android.view.InsetsState insetsState, boolean z) {
        android.view.InsetsState insetsState2;
        if (!z) {
            insetsState2 = adjustVisibilityForFakeControllingSources(insetsState);
        } else {
            insetsState2 = insetsState;
        }
        android.view.InsetsState adjustVisibilityForIme = adjustVisibilityForIme(windowState, insetsState2, insetsState2 == insetsState);
        return adjustInsetsForRoundedCorners(windowState.mToken, adjustVisibilityForIme, adjustVisibilityForIme == insetsState);
    }

    android.view.InsetsState adjustInsetsForWindow(com.android.server.wm.WindowState windowState, android.view.InsetsState insetsState) {
        return adjustInsetsForWindow(windowState, insetsState, false);
    }

    void getInsetsForWindowMetrics(@android.annotation.Nullable com.android.server.wm.WindowToken windowToken, @android.annotation.NonNull android.view.InsetsState insetsState) {
        android.view.InsetsState rawInsetsState;
        if (windowToken != null && windowToken.isFixedRotationTransforming()) {
            rawInsetsState = windowToken.getFixedRotationTransformInsetsState();
        } else {
            rawInsetsState = this.mStateController.getRawInsetsState();
        }
        insetsState.set(rawInsetsState, true);
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if (isTransient(sourceAt.getType())) {
                sourceAt.setVisible(false);
            }
        }
        adjustInsetsForRoundedCorners(windowToken, insetsState, false);
        if (windowToken != null && windowToken.hasSizeCompatBounds()) {
            insetsState.scale(1.0f / windowToken.getCompatScale());
        }
    }

    android.view.InsetsState enforceInsetsPolicyForTarget(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, android.view.InsetsState insetsState) {
        android.view.InsetsState insetsState2;
        if (layoutParams.type == 2011) {
            insetsState2 = new android.view.InsetsState(insetsState);
            insetsState2.removeSource(android.view.InsetsSource.ID_IME);
        } else if (layoutParams.providedInsets == null) {
            insetsState2 = insetsState;
        } else {
            android.view.InsetsState insetsState3 = insetsState;
            for (android.view.InsetsFrameProvider insetsFrameProvider : layoutParams.providedInsets) {
                if ((insetsFrameProvider.getType() & android.view.WindowInsets.Type.systemBars()) != 0) {
                    if (insetsState3 == insetsState) {
                        insetsState3 = new android.view.InsetsState(insetsState3);
                    }
                    insetsState3.removeSource(insetsFrameProvider.getId());
                }
            }
            insetsState2 = insetsState3;
        }
        if (!layoutParams.isFullscreen() || layoutParams.getFitInsetsTypes() != 0) {
            if (insetsState2 == insetsState) {
                insetsState2 = new android.view.InsetsState(insetsState);
            }
            for (int sourceSize = insetsState2.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                if (insetsState2.sourceAt(sourceSize).getType() == android.view.WindowInsets.Type.captionBar()) {
                    insetsState2.removeSourceAt(sourceSize);
                }
            }
        }
        android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> sourceProviders = this.mStateController.getSourceProviders();
        int i2 = layoutParams.type;
        for (int size = sourceProviders.size() - 1; size >= 0; size--) {
            com.android.server.wm.InsetsSourceProvider valueAt = sourceProviders.valueAt(size);
            if (valueAt.overridesFrame(i2)) {
                if (insetsState2 == insetsState) {
                    insetsState2 = new android.view.InsetsState(insetsState2);
                }
                android.view.InsetsSource insetsSource = new android.view.InsetsSource(valueAt.getSource());
                insetsSource.setFrame(valueAt.getOverriddenFrame(i2));
                insetsState2.addSource(insetsSource);
            }
        }
        if (android.app.WindowConfiguration.isFloating(i) || (i == 6 && z)) {
            int captionBar = android.view.WindowInsets.Type.captionBar();
            if (i != 2) {
                captionBar |= android.view.WindowInsets.Type.ime();
            }
            android.view.InsetsState insetsState4 = new android.view.InsetsState();
            insetsState4.set(insetsState2, captionBar);
            return insetsState4;
        }
        return insetsState2;
    }

    private android.view.InsetsState adjustVisibilityForFakeControllingSources(android.view.InsetsState insetsState) {
        if (this.mFakeStatusControlTarget == null && this.mFakeNavControlTarget == null) {
            return insetsState;
        }
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            insetsState = adjustVisibilityForFakeControllingSource(adjustVisibilityForFakeControllingSource(insetsState, android.view.WindowInsets.Type.statusBars(), sourceAt, this.mFakeStatusControlTarget), android.view.WindowInsets.Type.navigationBars(), sourceAt, this.mFakeNavControlTarget);
        }
        return insetsState;
    }

    private static android.view.InsetsState adjustVisibilityForFakeControllingSource(android.view.InsetsState insetsState, int i, android.view.InsetsSource insetsSource, com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsSource.getType() != i || insetsControlTarget == null) {
            return insetsState;
        }
        boolean isRequestedVisible = insetsControlTarget.isRequestedVisible(i);
        if (insetsSource.isVisible() == isRequestedVisible) {
            return insetsState;
        }
        android.view.InsetsState insetsState2 = new android.view.InsetsState(insetsState);
        android.view.InsetsSource insetsSource2 = new android.view.InsetsSource(insetsSource);
        insetsSource2.setVisible(isRequestedVisible);
        insetsState2.addSource(insetsSource2);
        return insetsState2;
    }

    private android.view.InsetsState adjustVisibilityForIme(com.android.server.wm.WindowState windowState, android.view.InsetsState insetsState, boolean z) {
        android.view.InsetsSource peekSource;
        if (windowState.mIsImWindow) {
            boolean z2 = !this.mHideNavBarForKeyboard;
            android.view.InsetsState insetsState2 = insetsState;
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
                if (sourceAt.getType() == android.view.WindowInsets.Type.navigationBars() && sourceAt.isVisible() != z2) {
                    if (insetsState2 == insetsState && z) {
                        insetsState2 = new android.view.InsetsState(insetsState);
                    }
                    android.view.InsetsSource insetsSource = new android.view.InsetsSource(sourceAt);
                    insetsSource.setVisible(z2);
                    insetsState2.addSource(insetsSource);
                }
            }
            return insetsState2;
        }
        if (windowState.mActivityRecord != null && windowState.mActivityRecord.mImeInsetsFrozenUntilStartInput) {
            android.view.InsetsSource peekSource2 = insetsState.peekSource(android.view.InsetsSource.ID_IME);
            if (peekSource2 != null) {
                boolean isRequestedVisible = windowState.isRequestedVisible(android.view.WindowInsets.Type.ime());
                if (z) {
                    insetsState = new android.view.InsetsState(insetsState);
                }
                android.view.InsetsSource insetsSource2 = new android.view.InsetsSource(peekSource2);
                insetsSource2.setVisible(isRequestedVisible);
                insetsState.addSource(insetsSource2);
                return insetsState;
            }
        } else if (windowState.mImeInsetsConsumed && (peekSource = insetsState.peekSource(android.view.InsetsSource.ID_IME)) != null && peekSource.isVisible()) {
            if (z) {
                insetsState = new android.view.InsetsState(insetsState);
            }
            android.view.InsetsSource insetsSource3 = new android.view.InsetsSource(peekSource);
            insetsSource3.setVisible(false);
            insetsState.addSource(insetsSource3);
            return insetsState;
        }
        return insetsState;
    }

    private android.view.InsetsState adjustInsetsForRoundedCorners(com.android.server.wm.WindowToken windowToken, android.view.InsetsState insetsState, boolean z) {
        if (windowToken != null) {
            com.android.server.wm.ActivityRecord asActivityRecord = windowToken.asActivityRecord();
            com.android.server.wm.Task task = asActivityRecord != null ? asActivityRecord.getTask() : null;
            if (task != null && !task.getWindowConfiguration().tasksAreFloating()) {
                if (z) {
                    insetsState = new android.view.InsetsState(insetsState);
                }
                insetsState.setRoundedCornerFrame(task.getBounds());
                return insetsState;
            }
        }
        return insetsState;
    }

    void onRequestedVisibleTypesChanged(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        this.mStateController.onRequestedVisibleTypesChanged(insetsControlTarget);
        checkAbortTransient(insetsControlTarget);
        updateBarControlTarget(this.mFocusedWin);
    }

    private void checkAbortTransient(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (this.mShowingTransientTypes == 0) {
            return;
        }
        int requestedVisibleTypes = (insetsControlTarget.getRequestedVisibleTypes() & this.mStateController.getFakeControllingTypes(insetsControlTarget)) | (this.mStateController.getImeSourceProvider().isClientVisible() ? android.view.WindowInsets.Type.navigationBars() : 0);
        this.mShowingTransientTypes &= ~requestedVisibleTypes;
        if (requestedVisibleTypes != 0) {
            this.mDisplayContent.setLayoutNeeded();
            this.mDisplayContent.mWmService.requestTraversal();
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.abortTransient(this.mDisplayContent.getDisplayId(), requestedVisibleTypes);
            }
        }
    }

    private void abortTransient() {
        if (this.mShowingTransientTypes == 0) {
            return;
        }
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.abortTransient(this.mDisplayContent.getDisplayId(), this.mShowingTransientTypes);
        }
        this.mShowingTransientTypes = 0;
        this.mDisplayContent.setLayoutNeeded();
        this.mDisplayContent.mWmService.requestTraversal();
        dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, false, false);
    }

    @android.annotation.Nullable
    private com.android.server.wm.InsetsControlTarget getStatusControlTarget(@android.annotation.Nullable com.android.server.wm.WindowState windowState, boolean z) {
        if (!z && isTransient(android.view.WindowInsets.Type.statusBars())) {
            return this.mTransientControlTarget;
        }
        com.android.server.wm.WindowState notificationShade = this.mPolicy.getNotificationShade();
        if (windowState == notificationShade) {
            return windowState;
        }
        if (remoteInsetsControllerControlsSystemBars(windowState)) {
            this.mDisplayContent.mRemoteInsetsControlTarget.topFocusedWindowChanged(windowState.mActivityRecord != null ? windowState.mActivityRecord.mActivityComponent : null, windowState.getRequestedVisibleTypes());
            return this.mDisplayContent.mRemoteInsetsControlTarget;
        }
        if (areTypesForciblyShowing(android.view.WindowInsets.Type.statusBars())) {
            return this.mPermanentControlTarget;
        }
        if (this.mPolicy.areTypesForciblyShownTransiently(android.view.WindowInsets.Type.statusBars()) && !z) {
            return this.mTransientControlTarget;
        }
        if (!canBeTopFullscreenOpaqueWindow(windowState) && this.mPolicy.topAppHidesSystemBar(android.view.WindowInsets.Type.statusBars()) && (notificationShade == null || !notificationShade.canReceiveKeys())) {
            return this.mPolicy.getTopFullscreenOpaqueWindow();
        }
        return windowState;
    }

    private static boolean canBeTopFullscreenOpaqueWindow(@android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        return (windowState != null && windowState.mAttrs.type >= 1 && windowState.mAttrs.type <= 99) && windowState.mAttrs.isFullscreen() && !windowState.isFullyTransparent() && !windowState.inMultiWindowMode();
    }

    @android.annotation.Nullable
    private com.android.server.wm.InsetsControlTarget getNavControlTarget(@android.annotation.Nullable com.android.server.wm.WindowState windowState, boolean z) {
        com.android.server.wm.InsetsSourceProvider controllableInsetProvider;
        com.android.server.wm.WindowState windowState2 = this.mDisplayContent.mInputMethodWindow;
        if (windowState2 != null && windowState2.isVisible() && !this.mHideNavBarForKeyboard) {
            return this.mPermanentControlTarget;
        }
        if (!z && isTransient(android.view.WindowInsets.Type.navigationBars())) {
            return this.mTransientControlTarget;
        }
        if (windowState == this.mPolicy.getNotificationShade()) {
            return windowState;
        }
        if (windowState != null && (controllableInsetProvider = windowState.getControllableInsetProvider()) != null && controllableInsetProvider.getSource().getType() == android.view.WindowInsets.Type.navigationBars()) {
            return windowState;
        }
        if (remoteInsetsControllerControlsSystemBars(windowState)) {
            this.mDisplayContent.mRemoteInsetsControlTarget.topFocusedWindowChanged(windowState.mActivityRecord != null ? windowState.mActivityRecord.mActivityComponent : null, windowState.getRequestedVisibleTypes());
            return this.mDisplayContent.mRemoteInsetsControlTarget;
        }
        if (areTypesForciblyShowing(android.view.WindowInsets.Type.navigationBars())) {
            return this.mPermanentControlTarget;
        }
        if (this.mPolicy.areTypesForciblyShownTransiently(android.view.WindowInsets.Type.navigationBars()) && !z) {
            return this.mTransientControlTarget;
        }
        com.android.server.wm.WindowState notificationShade = this.mPolicy.getNotificationShade();
        if (!canBeTopFullscreenOpaqueWindow(windowState) && this.mPolicy.topAppHidesSystemBar(android.view.WindowInsets.Type.navigationBars()) && (notificationShade == null || !notificationShade.canReceiveKeys())) {
            return this.mPolicy.getTopFullscreenOpaqueWindow();
        }
        return windowState;
    }

    boolean areTypesForciblyShowing(int i) {
        return (this.mForcedShowingTypes & i) == i;
    }

    void updateSystemBars(com.android.server.wm.WindowState windowState, boolean z, boolean z2) {
        int statusBars;
        int i = 0;
        if (z || z2) {
            statusBars = android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars();
        } else if (forceShowingNavigationBars(windowState)) {
            statusBars = android.view.WindowInsets.Type.navigationBars();
        } else {
            statusBars = 0;
        }
        this.mForcedShowingTypes = statusBars;
        com.android.server.wm.InsetsStateController insetsStateController = this.mStateController;
        int i2 = this.mForcedShowingTypes;
        if (remoteInsetsControllerControlsSystemBars(windowState)) {
            i = android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars();
        }
        insetsStateController.setForcedConsumingTypes(i2 | i);
        updateBarControlTarget(windowState);
    }

    private boolean forceShowingNavigationBars(com.android.server.wm.WindowState windowState) {
        return this.mPolicy.isForceShowNavigationBarEnabled() && windowState != null && windowState.getActivityType() == 1;
    }

    boolean remoteInsetsControllerControlsSystemBars(@android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        return windowState != null && this.mPolicy.isRemoteInsetsControllerControllingSystemBars() && this.mDisplayContent != null && this.mDisplayContent.mRemoteInsetsControlTarget != null && windowState.getAttrs().type >= 1 && windowState.getAttrs().type <= 99;
    }

    private void dispatchTransientSystemBarsVisibilityChanged(@android.annotation.Nullable com.android.server.wm.WindowState windowState, boolean z, boolean z2) {
        com.android.server.wm.Task task;
        if (windowState == null || (task = windowState.getTask()) == null) {
            return;
        }
        int i = task.mTaskId;
        if (!(i != -1)) {
            return;
        }
        this.mDisplayContent.mWmService.mTaskSystemBarsListenerController.dispatchTransientSystemBarVisibilityChanged(i, z, z2);
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "InsetsPolicy");
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "status: " + android.app.StatusBarManager.windowStateToString(this.mStatusBar.mState));
        printWriter.println(str2 + "nav: " + android.app.StatusBarManager.windowStateToString(this.mNavBar.mState));
        if (this.mShowingTransientTypes != 0) {
            printWriter.println(str2 + "mShowingTransientTypes=" + android.view.WindowInsets.Type.toString(this.mShowingTransientTypes));
        }
        if (this.mForcedShowingTypes != 0) {
            printWriter.println(str2 + "mForcedShowingTypes=" + android.view.WindowInsets.Type.toString(this.mForcedShowingTypes));
        }
    }

    private class BarWindow {
        private final int mId;
        private int mState = 0;

        BarWindow(int i) {
            this.mId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateVisibility(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, int i) {
            setVisible(insetsControlTarget == null || insetsControlTarget.isRequestedVisible(i));
        }

        private void setVisible(boolean z) {
            int i = z ? 0 : 2;
            if (this.mState != i) {
                this.mState = i;
                com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.wm.InsetsPolicy.this.mPolicy.getStatusBarManagerInternal();
                if (statusBarManagerInternal != null) {
                    statusBarManagerInternal.setWindowState(com.android.server.wm.InsetsPolicy.this.mDisplayContent.getDisplayId(), this.mId, i);
                }
            }
        }
    }

    private static class ControlTarget implements com.android.server.wm.InsetsControlTarget, java.lang.Runnable {
        private final java.lang.Object mGlobalLock;
        private final android.os.Handler mHandler;
        private final android.view.InsetsController mInsetsController;
        private final java.lang.String mName;
        private final android.view.InsetsState mState = new android.view.InsetsState();
        private final com.android.server.wm.InsetsStateController mStateController;

        ControlTarget(com.android.server.wm.DisplayContent displayContent, java.lang.String str) {
            this.mHandler = displayContent.mWmService.mH;
            this.mGlobalLock = displayContent.mWmService.mGlobalLock;
            this.mStateController = displayContent.getInsetsStateController();
            this.mInsetsController = new android.view.InsetsController(new com.android.server.wm.InsetsPolicy.Host(this.mHandler, str));
            this.mName = str;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public void notifyInsetsControlChanged(int i) {
            this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.mGlobalLock) {
                this.mState.set(this.mStateController.getRawInsetsState(), true);
                this.mInsetsController.onStateChanged(this.mState);
                this.mInsetsController.onControlsChanged(this.mStateController.getControlsForDispatch(this));
            }
        }

        public java.lang.String toString() {
            return this.mName;
        }
    }

    private static class Host implements android.view.InsetsController.Host {
        private final android.os.Handler mHandler;
        private boolean mInsetsAnimationRunning;
        private final java.lang.String mName;
        private final float[] mTmpFloat9 = new float[9];

        Host(android.os.Handler handler, java.lang.String str) {
            this.mHandler = handler;
            this.mName = str;
        }

        public android.os.Handler getHandler() {
            return this.mHandler;
        }

        public void notifyInsetsChanged() {
        }

        public void dispatchWindowInsetsAnimationPrepare(@android.annotation.NonNull android.view.WindowInsetsAnimation windowInsetsAnimation) {
        }

        public android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(@android.annotation.NonNull android.view.WindowInsetsAnimation windowInsetsAnimation, @android.annotation.NonNull android.view.WindowInsetsAnimation.Bounds bounds) {
            return bounds;
        }

        public android.view.WindowInsets dispatchWindowInsetsAnimationProgress(@android.annotation.NonNull android.view.WindowInsets windowInsets, @android.annotation.NonNull java.util.List<android.view.WindowInsetsAnimation> list) {
            return windowInsets;
        }

        public void dispatchWindowInsetsAnimationEnd(@android.annotation.NonNull android.view.WindowInsetsAnimation windowInsetsAnimation) {
        }

        public void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
                android.view.SyncRtSurfaceTransactionApplier.applyParams(transaction, surfaceParamsArr[length], this.mTmpFloat9);
            }
            transaction.apply();
            transaction.close();
        }

        public void updateRequestedVisibleTypes(int i) {
        }

        public boolean hasAnimationCallbacks() {
            return false;
        }

        public void setSystemBarsAppearance(int i, int i2) {
        }

        public int getSystemBarsAppearance() {
            return 0;
        }

        public void setSystemBarsBehavior(int i) {
        }

        public int getSystemBarsBehavior() {
            return 2;
        }

        public void releaseSurfaceControlFromRt(android.view.SurfaceControl surfaceControl) {
            surfaceControl.release();
        }

        public void addOnPreDrawRunnable(java.lang.Runnable runnable) {
        }

        public void postInsetsAnimationCallback(java.lang.Runnable runnable) {
        }

        public android.view.inputmethod.InputMethodManager getInputMethodManager() {
            return null;
        }

        @android.annotation.Nullable
        public java.lang.String getRootViewTitle() {
            return this.mName;
        }

        public int dipToPx(int i) {
            return 0;
        }

        @android.annotation.Nullable
        public android.os.IBinder getWindowToken() {
            return null;
        }

        public void notifyAnimationRunningStateChanged(boolean z) {
            this.mInsetsAnimationRunning = z;
        }
    }
}
