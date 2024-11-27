package com.android.server.wm;

/* loaded from: classes3.dex */
final class ImeInsetsSourceProvider extends com.android.server.wm.InsetsSourceProvider {
    private boolean mFrozen;
    private com.android.server.wm.InsetsControlTarget mImeRequester;

    @android.annotation.Nullable
    private android.view.inputmethod.ImeTracker.Token mImeRequesterStatsToken;
    private boolean mImeShowing;
    private boolean mIsImeLayoutDrawn;
    private final android.view.InsetsSource mLastSource;
    private boolean mServerVisible;
    private java.lang.Runnable mShowImeRunner;

    ImeInsetsSourceProvider(android.view.InsetsSource insetsSource, com.android.server.wm.InsetsStateController insetsStateController, com.android.server.wm.DisplayContent displayContent) {
        super(insetsSource, insetsStateController, displayContent);
        this.mLastSource = new android.view.InsetsSource(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime());
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    android.view.InsetsSourceControl getControl(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        android.window.TaskSnapshot taskSnapshot;
        android.view.InsetsSourceControl control = super.getControl(insetsControlTarget);
        if (control != null && insetsControlTarget != null && insetsControlTarget.getWindow() != null) {
            com.android.server.wm.WindowState window = insetsControlTarget.getWindow();
            boolean z = false;
            if (window.getRootTask() != null) {
                taskSnapshot = window.mWmService.getTaskSnapshot(window.getRootTask().mTaskId, 0, false, false);
            } else {
                taskSnapshot = null;
            }
            if (window.mActivityRecord != null && window.mActivityRecord.hasStartingWindow() && taskSnapshot != null && taskSnapshot.hasImeSurface()) {
                z = true;
            }
            control.setSkipAnimationOnce(z);
        }
        return control;
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    void setClientVisible(boolean z) {
        com.android.server.wm.InsetsControlTarget controlTarget;
        boolean isClientVisible = isClientVisible();
        super.setClientVisible(z);
        if (!isClientVisible && isClientVisible() && (controlTarget = getControlTarget()) != null && controlTarget.getWindow() != null && controlTarget.getWindow().mActivityRecord == null) {
            this.mDisplayContent.assignWindowLayers(false);
        }
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    void setServerVisible(boolean z) {
        this.mServerVisible = z;
        if (!this.mFrozen) {
            super.setServerVisible(z);
        }
    }

    void setFrozen(boolean z) {
        if (this.mFrozen == z) {
            return;
        }
        this.mFrozen = z;
        if (!z) {
            super.setServerVisible(this.mServerVisible);
        }
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    void updateSourceFrame(android.graphics.Rect rect) {
        super.updateSourceFrame(rect);
        onSourceChanged();
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    protected void updateVisibility() {
        super.updateVisibility();
        onSourceChanged();
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    void updateControlForTarget(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, boolean z) {
        if (insetsControlTarget != null && insetsControlTarget.getWindow() != null) {
            insetsControlTarget = insetsControlTarget.getWindow().getImeControlTarget();
        }
        super.updateControlForTarget(insetsControlTarget, z);
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    protected boolean updateClientVisibility(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget != getControlTarget()) {
            return false;
        }
        boolean updateClientVisibility = super.updateClientVisibility(insetsControlTarget);
        if (updateClientVisibility && insetsControlTarget.isRequestedVisible(this.mSource.getType())) {
            reportImeDrawnForOrganizerIfNeeded(insetsControlTarget);
        }
        return this.mDisplayContent.onImeInsetsClientVisibilityUpdate() | updateClientVisibility;
    }

    private void reportImeDrawnForOrganizerIfNeeded(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget.getWindow() == null) {
            return;
        }
        com.android.server.wm.WindowToken windowToken = this.mWindowContainer.asWindowState() != null ? this.mWindowContainer.asWindowState().mToken : null;
        if (this.mDisplayContent.getAsyncRotationController() != null && this.mDisplayContent.getAsyncRotationController().isTargetToken(windowToken)) {
            return;
        }
        reportImeDrawnForOrganizer(insetsControlTarget);
    }

    private void reportImeDrawnForOrganizer(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        com.android.server.wm.WindowState window = insetsControlTarget.getWindow();
        if (window != null && window.getTask() != null && window.getTask().isOrganized()) {
            this.mWindowContainer.mWmService.mAtmService.mTaskOrganizerController.reportImeDrawnOnTask(insetsControlTarget.getWindow().getTask());
        }
    }

    void reportImeDrawnForOrganizer() {
        com.android.server.wm.InsetsControlTarget controlTarget = getControlTarget();
        if (controlTarget != null) {
            reportImeDrawnForOrganizer(controlTarget);
        }
    }

    private void onSourceChanged() {
        if (this.mLastSource.equals(this.mSource)) {
            return;
        }
        this.mLastSource.set(this.mSource);
        this.mDisplayContent.mWmService.mH.obtainMessage(41, this.mDisplayContent).sendToTarget();
    }

    void scheduleShowImePostLayout(com.android.server.wm.InsetsControlTarget insetsControlTarget, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
        boolean isTargetChangedWithinActivity = isTargetChangedWithinActivity(insetsControlTarget);
        this.mImeRequester = insetsControlTarget;
        android.view.inputmethod.ImeTracker.forLogging().onCancelled(this.mImeRequesterStatsToken, 18);
        this.mImeRequesterStatsToken = token;
        if (isTargetChangedWithinActivity) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -786355099910065121L, 0, null, null);
            checkShowImePostLayout();
        } else {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 2634707843050913730L, 0, null, java.lang.String.valueOf(this.mImeRequester.getWindow() == null ? this.mImeRequester : this.mImeRequester.getWindow().getName()));
            this.mShowImeRunner = new java.lang.Runnable() { // from class: com.android.server.wm.ImeInsetsSourceProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.ImeInsetsSourceProvider.this.lambda$scheduleShowImePostLayout$0();
                }
            };
            this.mDisplayContent.mWmService.requestTraversal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleShowImePostLayout$0() {
        android.view.inputmethod.ImeTracker.forLogging().onProgress(this.mImeRequesterStatsToken, 18);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 8923821958256605927L, 0, null, null);
        if (isReadyToShowIme()) {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(this.mImeRequesterStatsToken, 19);
            com.android.server.wm.InsetsControlTarget imeTarget = this.mDisplayContent.getImeTarget(2);
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -3529253275087521638L, 0, null, java.lang.String.valueOf(imeTarget.getWindow() != null ? imeTarget.getWindow().getName() : ""));
            setImeShowing(true);
            imeTarget.showInsets(android.view.WindowInsets.Type.ime(), true, this.mImeRequesterStatsToken);
            android.os.Trace.asyncTraceEnd(32L, "WMS.showImePostLayout", 0);
            if (imeTarget != this.mImeRequester && this.mImeRequester != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 7927729210300708186L, 0, null, java.lang.String.valueOf(this.mImeRequester.getWindow() != null ? this.mImeRequester.getWindow().getName() : ""));
            }
        } else {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(this.mImeRequesterStatsToken, 19);
        }
        this.mImeRequesterStatsToken = null;
        abortShowImePostLayout();
    }

    void checkShowImePostLayout() {
        if (this.mWindowContainer == null) {
            return;
        }
        com.android.server.wm.WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState == null) {
            throw new java.lang.IllegalArgumentException("IME insets must be provided by a window.");
        }
        if (this.mIsImeLayoutDrawn || (isReadyToShowIme() && asWindowState.isDrawn() && !asWindowState.mGivenInsetsPending)) {
            this.mIsImeLayoutDrawn = true;
            if (this.mShowImeRunner != null) {
                this.mShowImeRunner.run();
            }
        }
    }

    void abortShowImePostLayout() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -6529782994356455131L, 0, null, null);
        this.mImeRequester = null;
        this.mIsImeLayoutDrawn = false;
        this.mShowImeRunner = null;
        android.view.inputmethod.ImeTracker.forLogging().onFailed(this.mImeRequesterStatsToken, 43);
        this.mImeRequesterStatsToken = null;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isReadyToShowIme() {
        com.android.server.wm.InsetsControlTarget imeTarget = this.mDisplayContent.getImeTarget(0);
        if (imeTarget == null || this.mImeRequester == null || this.mDisplayContent.getImeTarget(2) == null) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -6629998049460863403L, 0, null, java.lang.String.valueOf(imeTarget.getWindow().getName()), java.lang.String.valueOf(this.mImeRequester.getWindow() == null ? this.mImeRequester : this.mImeRequester.getWindow().getName()));
        return isImeLayeringTarget(this.mImeRequester, imeTarget) || isAboveImeLayeringTarget(this.mImeRequester, imeTarget) || isImeFallbackTarget(this.mImeRequester) || isImeInputTarget(this.mImeRequester) || sameAsImeControlTarget();
    }

    private static boolean isImeLayeringTarget(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget, @android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget2) {
        return !isImeTargetWindowClosing(insetsControlTarget2.getWindow()) && insetsControlTarget == insetsControlTarget2;
    }

    private static boolean isAboveImeLayeringTarget(@android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget, @android.annotation.NonNull com.android.server.wm.InsetsControlTarget insetsControlTarget2) {
        return insetsControlTarget.getWindow() != null && insetsControlTarget2.getWindow().getParentWindow() == insetsControlTarget && insetsControlTarget2.getWindow().mSubLayer > insetsControlTarget.getWindow().mSubLayer;
    }

    private boolean isImeFallbackTarget(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        return insetsControlTarget == this.mDisplayContent.getImeFallback();
    }

    private boolean isImeInputTarget(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        return insetsControlTarget == this.mDisplayContent.getImeInputTarget();
    }

    private boolean sameAsImeControlTarget() {
        return this.mDisplayContent.getImeTarget(2) == this.mImeRequester && (this.mImeRequester.getWindow() == null || !isImeTargetWindowClosing(this.mImeRequester.getWindow()));
    }

    private static boolean isImeTargetWindowClosing(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        return windowState.mAnimatingExit || (windowState.mActivityRecord != null && ((windowState.mActivityRecord.isInTransition() && !windowState.mActivityRecord.isVisibleRequested()) || windowState.mActivityRecord.willCloseOrEnterPip()));
    }

    private boolean isTargetChangedWithinActivity(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        return (insetsControlTarget == null || insetsControlTarget.getWindow() == null || this.mImeRequester == insetsControlTarget || this.mImeRequester == null || this.mShowImeRunner == null || this.mImeRequester.getWindow() == null || this.mImeRequester.getWindow().mActivityRecord != insetsControlTarget.getWindow().mActivityRecord) ? false : true;
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        super.dump(printWriter, str);
        java.lang.String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mImeShowing=");
        printWriter.print(this.mImeShowing);
        if (this.mImeRequester != null) {
            printWriter.print(str2);
            printWriter.print("showImePostLayout pending for mImeRequester=");
            printWriter.print(this.mImeRequester);
            printWriter.println();
        }
        printWriter.println();
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        com.android.server.wm.WindowState window = this.mImeRequester != null ? this.mImeRequester.getWindow() : null;
        if (window != null) {
            window.dumpDebug(protoOutputStream, 1146756268034L, i);
        }
        protoOutputStream.write(1133871366147L, this.mIsImeLayoutDrawn);
        protoOutputStream.end(start);
    }

    public void setImeShowing(boolean z) {
        this.mImeShowing = z;
    }

    public boolean isImeShowing() {
        return this.mImeShowing;
    }
}
