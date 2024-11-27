package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowToken extends com.android.server.wm.WindowContainer<com.android.server.wm.WindowState> {
    private static final java.lang.String TAG = "WindowManager";
    private boolean mClientVisible;
    private android.view.SurfaceControl mFixedRotationTransformLeash;
    private com.android.server.wm.WindowToken.FixedRotationTransformState mFixedRotationTransformState;
    private final boolean mFromClientToken;

    @android.annotation.Nullable
    final android.os.Bundle mOptions;
    final boolean mOwnerCanManageAppTokens;
    boolean mPersistOnEmpty;
    final boolean mRoundedCornerOverlay;
    private final java.util.Comparator<com.android.server.wm.WindowState> mWindowComparator;
    boolean paused;
    java.lang.String stringName;
    final android.os.IBinder token;
    final int windowType;

    private static class FixedRotationTransformState {
        final com.android.server.wm.DisplayFrames mDisplayFrames;
        final android.view.DisplayInfo mDisplayInfo;
        final android.content.res.Configuration mRotatedOverrideConfiguration;
        final java.util.ArrayList<com.android.server.wm.WindowToken> mAssociatedTokens = new java.util.ArrayList<>(3);
        boolean mIsTransforming = true;

        FixedRotationTransformState(android.view.DisplayInfo displayInfo, com.android.server.wm.DisplayFrames displayFrames, android.content.res.Configuration configuration) {
            this.mDisplayInfo = displayInfo;
            this.mDisplayFrames = displayFrames;
            this.mRotatedOverrideConfiguration = configuration;
        }

        void transform(com.android.server.wm.WindowContainer<?> windowContainer) {
        }

        void resetTransform() {
            for (int size = this.mAssociatedTokens.size() - 1; size >= 0; size--) {
                this.mAssociatedTokens.get(size).removeFixedRotationLeash();
            }
        }

        void disassociate(com.android.server.wm.WindowToken windowToken) {
            this.mAssociatedTokens.remove(windowToken);
        }
    }

    private static class FixedRotationTransformStateLegacy extends com.android.server.wm.WindowToken.FixedRotationTransformState {
        final java.util.ArrayList<com.android.server.wm.WindowContainer<?>> mRotatedContainers;
        final com.android.server.wm.SeamlessRotator mRotator;

        FixedRotationTransformStateLegacy(android.view.DisplayInfo displayInfo, com.android.server.wm.DisplayFrames displayFrames, android.content.res.Configuration configuration, int i) {
            super(displayInfo, displayFrames, configuration);
            this.mRotatedContainers = new java.util.ArrayList<>(3);
            this.mRotator = new com.android.server.wm.SeamlessRotator(displayInfo.rotation, i, displayInfo, true);
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        void transform(com.android.server.wm.WindowContainer<?> windowContainer) {
            this.mRotator.unrotate(windowContainer.getPendingTransaction(), windowContainer);
            if (!this.mRotatedContainers.contains(windowContainer)) {
                this.mRotatedContainers.add(windowContainer);
            }
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        void resetTransform() {
            for (int size = this.mRotatedContainers.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowContainer<?> windowContainer = this.mRotatedContainers.get(size);
                if (windowContainer.getParent() != null) {
                    this.mRotator.finish(windowContainer.getPendingTransaction(), windowContainer);
                }
            }
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        void disassociate(com.android.server.wm.WindowToken windowToken) {
            super.disassociate(windowToken);
            this.mRotatedContainers.remove(windowToken);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$0(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        if (windowState.mToken != this) {
            throw new java.lang.IllegalArgumentException("newWindow=" + windowState + " is not a child of token=" + this);
        }
        if (windowState2.mToken == this) {
            return isFirstChildWindowGreaterThanSecond(windowState, windowState2) ? 1 : -1;
        }
        throw new java.lang.IllegalArgumentException("existingWindow=" + windowState2 + " is not a child of token=" + this);
    }

    protected WindowToken(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, int i, boolean z, com.android.server.wm.DisplayContent displayContent, boolean z2) {
        this(windowManagerService, iBinder, i, z, displayContent, z2, false, false, null);
    }

    protected WindowToken(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, int i, boolean z, com.android.server.wm.DisplayContent displayContent, boolean z2, boolean z3, boolean z4, @android.annotation.Nullable android.os.Bundle bundle) {
        super(windowManagerService);
        this.paused = false;
        this.mWindowComparator = new java.util.Comparator() { // from class: com.android.server.wm.WindowToken$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$0;
                lambda$new$0 = com.android.server.wm.WindowToken.this.lambda$new$0((com.android.server.wm.WindowState) obj, (com.android.server.wm.WindowState) obj2);
                return lambda$new$0;
            }
        };
        this.token = iBinder;
        this.windowType = i;
        this.mOptions = bundle;
        this.mPersistOnEmpty = z;
        this.mOwnerCanManageAppTokens = z2;
        this.mRoundedCornerOverlay = z3;
        this.mFromClientToken = z4;
        if (displayContent != null) {
            displayContent.addWindowToken(this.token, this);
        }
    }

    void removeAllWindowsIfPossible() {
        int size = this.mChildren.size() - 1;
        while (size >= 0) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT, 8174298531248485625L, 0, null, java.lang.String.valueOf(windowState));
            windowState.removeIfPossible();
            if (size > this.mChildren.size()) {
                size = this.mChildren.size();
            }
            size--;
        }
    }

    void setExiting(boolean z) {
        if (isEmpty()) {
            super.removeImmediately();
            return;
        }
        this.mPersistOnEmpty = false;
        if (!isVisible()) {
            return;
        }
        int size = this.mChildren.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            z2 |= ((com.android.server.wm.WindowState) this.mChildren.get(i)).onSetAppExiting(z);
        }
        if (z2) {
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
    }

    float getCompatScale() {
        return this.mDisplayContent.mCompatibleScreenScale;
    }

    boolean hasSizeCompatBounds() {
        return false;
    }

    protected boolean isFirstChildWindowGreaterThanSecond(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        return windowState.mBaseLayer >= windowState2.mBaseLayer;
    }

    void addWindow(com.android.server.wm.WindowState windowState) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 2740931087734487464L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        if (windowState.isChildWindow()) {
            return;
        }
        if (this.mSurfaceControl == null) {
            createSurfaceControl(true);
            reassignLayer(getSyncTransaction());
        }
        if (!this.mChildren.contains(windowState)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 2382798629637143561L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(this));
            addChild((com.android.server.wm.WindowToken) windowState, (java.util.Comparator<com.android.server.wm.WindowToken>) this.mWindowComparator);
            this.mWmService.mWindowsChanged = true;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void createSurfaceControl(boolean z) {
        if (!this.mFromClientToken || z) {
            super.createSurfaceControl(z);
        }
    }

    boolean isEmpty() {
        return this.mChildren.isEmpty();
    }

    boolean windowsCanBeWallpaperTarget() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.WindowState) this.mChildren.get(size)).hasWallpaper()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void removeImmediately() {
        if (this.mDisplayContent != null) {
            this.mDisplayContent.removeWindowToken(this.token, true);
        }
        super.removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        displayContent.reParentWindowToken(this);
        super.onDisplayChanged(displayContent);
    }

    @Override // com.android.server.wm.WindowContainer
    void assignLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mRoundedCornerOverlay) {
            super.assignLayer(transaction, 1073741826);
        } else {
            super.assignLayer(transaction, i);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.SurfaceControl.Builder makeSurface() {
        android.view.SurfaceControl.Builder makeSurface = super.makeSurface();
        if (this.mRoundedCornerOverlay) {
            makeSurface.setParent(null);
        }
        return makeSurface;
    }

    boolean isClientVisible() {
        return this.mClientVisible;
    }

    void setClientVisible(boolean z) {
        if (this.mClientVisible == z) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -7314975896738778749L, 12, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        this.mClientVisible = z;
        sendAppVisibilityToClients();
    }

    boolean hasFixedRotationTransform() {
        return this.mFixedRotationTransformState != null;
    }

    boolean hasFixedRotationTransform(com.android.server.wm.WindowToken windowToken) {
        if (this.mFixedRotationTransformState == null || windowToken == null) {
            return false;
        }
        return this == windowToken || this.mFixedRotationTransformState == windowToken.mFixedRotationTransformState;
    }

    boolean isFinishingFixedRotationTransform() {
        return (this.mFixedRotationTransformState == null || this.mFixedRotationTransformState.mIsTransforming) ? false : true;
    }

    boolean isFixedRotationTransforming() {
        return this.mFixedRotationTransformState != null && this.mFixedRotationTransformState.mIsTransforming;
    }

    android.view.DisplayInfo getFixedRotationTransformDisplayInfo() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mDisplayInfo;
        }
        return null;
    }

    com.android.server.wm.DisplayFrames getFixedRotationTransformDisplayFrames() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mDisplayFrames;
        }
        return null;
    }

    android.graphics.Rect getFixedRotationTransformMaxBounds() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mRotatedOverrideConfiguration.windowConfiguration.getMaxBounds();
        }
        return null;
    }

    android.graphics.Rect getFixedRotationTransformDisplayBounds() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mRotatedOverrideConfiguration.windowConfiguration.getBounds();
        }
        return null;
    }

    android.view.InsetsState getFixedRotationTransformInsetsState() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mDisplayFrames.mInsetsState;
        }
        return null;
    }

    void applyFixedRotationTransform(android.view.DisplayInfo displayInfo, com.android.server.wm.DisplayFrames displayFrames, android.content.res.Configuration configuration) {
        com.android.server.wm.WindowToken.FixedRotationTransformState fixedRotationTransformStateLegacy;
        if (this.mFixedRotationTransformState != null) {
            this.mFixedRotationTransformState.disassociate(this);
        }
        android.content.res.Configuration configuration2 = new android.content.res.Configuration(configuration);
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            fixedRotationTransformStateLegacy = new com.android.server.wm.WindowToken.FixedRotationTransformState(displayInfo, displayFrames, configuration2);
        } else {
            fixedRotationTransformStateLegacy = new com.android.server.wm.WindowToken.FixedRotationTransformStateLegacy(displayInfo, displayFrames, configuration2, this.mDisplayContent.getRotation());
        }
        this.mFixedRotationTransformState = fixedRotationTransformStateLegacy;
        this.mFixedRotationTransformState.mAssociatedTokens.add(this);
        this.mDisplayContent.getDisplayPolicy().simulateLayoutDisplay(displayFrames);
        onFixedRotationStatePrepared();
    }

    void linkFixedRotationTransform(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.WindowToken.FixedRotationTransformState fixedRotationTransformState = windowToken.mFixedRotationTransformState;
        if (fixedRotationTransformState == null || this.mFixedRotationTransformState == fixedRotationTransformState) {
            return;
        }
        if (this.mFixedRotationTransformState != null) {
            this.mFixedRotationTransformState.disassociate(this);
        }
        this.mFixedRotationTransformState = fixedRotationTransformState;
        fixedRotationTransformState.mAssociatedTokens.add(this);
        onFixedRotationStatePrepared();
    }

    private void onFixedRotationStatePrepared() {
        onConfigurationChanged(getParent().getConfiguration());
        com.android.server.wm.ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord != null && asActivityRecord.hasProcess()) {
            asActivityRecord.app.registerActivityConfigurationListener(asActivityRecord);
        }
    }

    boolean hasAnimatingFixedRotationTransition() {
        if (this.mFixedRotationTransformState == null) {
            return false;
        }
        for (int size = this.mFixedRotationTransformState.mAssociatedTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord asActivityRecord = this.mFixedRotationTransformState.mAssociatedTokens.get(size).asActivityRecord();
            if (asActivityRecord != null && asActivityRecord.inTransitionSelfOrParent() && !asActivityRecord.mDisplayContent.inTransition()) {
                return true;
            }
        }
        return false;
    }

    void finishFixedRotationTransform() {
        finishFixedRotationTransform(null);
    }

    void finishFixedRotationTransform(java.lang.Runnable runnable) {
        com.android.server.wm.WindowToken.FixedRotationTransformState fixedRotationTransformState = this.mFixedRotationTransformState;
        if (fixedRotationTransformState == null) {
            return;
        }
        fixedRotationTransformState.resetTransform();
        fixedRotationTransformState.mIsTransforming = false;
        if (runnable != null) {
            runnable.run();
        }
        for (int size = fixedRotationTransformState.mAssociatedTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowToken windowToken = fixedRotationTransformState.mAssociatedTokens.get(size);
            windowToken.mFixedRotationTransformState = null;
            if (runnable == null) {
                windowToken.cancelFixedRotationTransform();
            }
        }
    }

    private void cancelFixedRotationTransform() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return;
        }
        if (this.mTransitionController.isShellTransitionsEnabled() && asActivityRecord() != null && isVisible()) {
            this.mTransitionController.requestTransitionIfNeeded(6, this);
            this.mTransitionController.collectVisibleChange(this);
            this.mTransitionController.setReady(this);
        }
        int rotation = getWindowConfiguration().getRotation();
        onConfigurationChanged(parent.getConfiguration());
        onCancelFixedRotationTransform(rotation);
    }

    @android.annotation.Nullable
    android.view.SurfaceControl getOrCreateFixedRotationLeash(@android.annotation.NonNull android.view.SurfaceControl.Transaction transaction) {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return null;
        }
        int relativeDisplayRotation = getRelativeDisplayRotation();
        if (relativeDisplayRotation != 0 && this.mFixedRotationTransformLeash == null) {
            android.view.SurfaceControl build = makeSurface().setContainerLayer().setParent(getParentSurfaceControl()).setName(getSurfaceControl() + " - rotation-leash").setHidden(false).setCallsite("WindowToken.getOrCreateFixedRotationLeash").build();
            transaction.setPosition(build, (float) this.mLastSurfacePosition.x, (float) this.mLastSurfacePosition.y);
            transaction.reparent(getSurfaceControl(), build);
            getPendingTransaction().setFixedTransformHint(build, getWindowConfiguration().getDisplayRotation());
            this.mFixedRotationTransformLeash = build;
            updateSurfaceRotation(transaction, relativeDisplayRotation, this.mFixedRotationTransformLeash);
            return this.mFixedRotationTransformLeash;
        }
        return this.mFixedRotationTransformLeash;
    }

    @android.annotation.Nullable
    android.view.SurfaceControl getFixedRotationLeash() {
        return this.mFixedRotationTransformLeash;
    }

    void removeFixedRotationLeash() {
        if (this.mFixedRotationTransformLeash == null) {
            return;
        }
        android.view.SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (this.mSurfaceControl != null) {
            syncTransaction.reparent(this.mSurfaceControl, getParentSurfaceControl());
        }
        syncTransaction.remove(this.mFixedRotationTransformLeash);
        this.mFixedRotationTransformLeash = null;
    }

    void onCancelFixedRotationTransform(int i) {
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        if (isFixedRotationTransforming()) {
            getResolvedOverrideConfiguration().updateFrom(this.mFixedRotationTransformState.mRotatedOverrideConfiguration);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void updateSurfacePosition(android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord != null && asActivityRecord.isConfigurationDispatchPaused()) {
            return;
        }
        super.updateSurfacePosition(transaction);
        if (!this.mTransitionController.isShellTransitionsEnabled() && isFixedRotationTransforming()) {
            com.android.server.wm.Task rootTask = asActivityRecord != null ? asActivityRecord.getRootTask() : null;
            if (rootTask == null || !rootTask.inPinnedWindowingMode()) {
                this.mFixedRotationTransformState.transform(this);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    protected void updateSurfaceRotation(android.view.SurfaceControl.Transaction transaction, int i, android.view.SurfaceControl surfaceControl) {
        com.android.server.wm.Task rootTask;
        com.android.server.wm.ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord != null && (rootTask = asActivityRecord.getRootTask()) != null && this.mTransitionController.getWindowingModeAtStart(rootTask) == 2) {
            return;
        }
        super.updateSurfaceRotation(transaction, i, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer
    void resetSurfacePositionForAnimationLeash(android.view.SurfaceControl.Transaction transaction) {
        if (!isFixedRotationTransforming()) {
            super.resetSurfacePositionForAnimationLeash(transaction);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean prepareSync() {
        if (this.mDisplayContent != null && this.mDisplayContent.isRotationChanging() && com.android.server.wm.AsyncRotationController.canBeAsync(this)) {
            return false;
        }
        return super.prepareSync();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1120986464258L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1133871366150L, this.paused);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268039L;
    }

    @Override // com.android.server.wm.WindowContainer
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.print("windows=");
        printWriter.println(this.mChildren);
        printWriter.print(str);
        printWriter.print("windowType=");
        printWriter.print(this.windowType);
        printWriter.println();
        if (hasFixedRotationTransform()) {
            printWriter.print(str);
            printWriter.print("fixedRotationConfig=");
            printWriter.println(this.mFixedRotationTransformState.mRotatedOverrideConfiguration);
        }
    }

    public java.lang.String toString() {
        if (this.stringName == null) {
            this.stringName = "WindowToken{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " type=" + this.windowType + " " + this.token + "}";
        }
        return this.stringName;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return toString();
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.WindowToken asWindowToken() {
        return this;
    }

    int getWindowLayerFromType() {
        return this.mWmService.mPolicy.getWindowLayerFromTypeLw(this.windowType, this.mOwnerCanManageAppTokens, this.mRoundedCornerOverlay);
    }

    boolean isFromClient() {
        return this.mFromClientToken;
    }

    void setInsetsFrozen(final boolean z) {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowToken$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowToken.this.lambda$setInsetsFrozen$1(z, (com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setInsetsFrozen$1(boolean z, com.android.server.wm.WindowState windowState) {
        if (windowState.mToken == this) {
            if (z) {
                windowState.freezeInsetsState();
            } else {
                windowState.clearFrozenInsetsState();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    int getWindowType() {
        return this.windowType;
    }

    static class Builder {
        private com.android.server.wm.DisplayContent mDisplayContent;
        private boolean mFromClientToken;

        @android.annotation.Nullable
        private android.os.Bundle mOptions;
        private boolean mOwnerCanManageAppTokens;
        private boolean mPersistOnEmpty;
        private boolean mRoundedCornerOverlay;
        private final com.android.server.wm.WindowManagerService mService;
        private final android.os.IBinder mToken;
        private final int mType;

        Builder(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, int i) {
            this.mService = windowManagerService;
            this.mToken = iBinder;
            this.mType = i;
        }

        com.android.server.wm.WindowToken.Builder setPersistOnEmpty(boolean z) {
            this.mPersistOnEmpty = z;
            return this;
        }

        com.android.server.wm.WindowToken.Builder setDisplayContent(com.android.server.wm.DisplayContent displayContent) {
            this.mDisplayContent = displayContent;
            return this;
        }

        com.android.server.wm.WindowToken.Builder setOwnerCanManageAppTokens(boolean z) {
            this.mOwnerCanManageAppTokens = z;
            return this;
        }

        com.android.server.wm.WindowToken.Builder setRoundedCornerOverlay(boolean z) {
            this.mRoundedCornerOverlay = z;
            return this;
        }

        com.android.server.wm.WindowToken.Builder setFromClientToken(boolean z) {
            this.mFromClientToken = z;
            return this;
        }

        com.android.server.wm.WindowToken.Builder setOptions(android.os.Bundle bundle) {
            this.mOptions = bundle;
            return this;
        }

        com.android.server.wm.WindowToken build() {
            return new com.android.server.wm.WindowToken(this.mService, this.mToken, this.mType, this.mPersistOnEmpty, this.mDisplayContent, this.mOwnerCanManageAppTokens, this.mRoundedCornerOverlay, this.mFromClientToken, this.mOptions);
        }
    }
}
