package com.android.server.wm;

/* loaded from: classes3.dex */
class InsetsSourceProvider {
    private static final android.graphics.Rect EMPTY_RECT = new android.graphics.Rect();

    @android.annotation.Nullable
    private com.android.server.wm.InsetsSourceProvider.ControlAdapter mAdapter;
    private boolean mClientVisible;

    @android.annotation.Nullable
    private android.view.InsetsSourceControl mControl;

    @android.annotation.Nullable
    private com.android.server.wm.InsetsControlTarget mControlTarget;
    private final boolean mControllable;
    protected final com.android.server.wm.DisplayContent mDisplayContent;
    private final android.view.InsetsSourceControl mFakeControl;

    @android.annotation.Nullable
    private com.android.server.wm.InsetsControlTarget mFakeControlTarget;
    private int mFlagsFromFrameProvider;
    private int mFlagsFromServer;
    private com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer> mFrameProvider;
    private boolean mHasPendingPosition;
    private boolean mIsLeashReadyForDispatching;
    private android.util.SparseArray<com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer>> mOverrideFrameProviders;

    @android.annotation.Nullable
    private com.android.server.wm.InsetsControlTarget mPendingControlTarget;
    private boolean mSeamlessRotating;
    private boolean mServerVisible;
    private final java.util.function.Consumer<android.view.SurfaceControl.Transaction> mSetLeashPositionConsumer;

    @android.annotation.NonNull
    protected final android.view.InsetsSource mSource;
    private final com.android.server.wm.InsetsStateController mStateController;
    protected com.android.server.wm.WindowContainer mWindowContainer;
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private final android.util.SparseArray<android.graphics.Rect> mOverrideFrames = new android.util.SparseArray<>();
    private final android.graphics.Rect mSourceFrame = new android.graphics.Rect();
    private final android.graphics.Rect mLastSourceFrame = new android.graphics.Rect();

    @android.annotation.NonNull
    private android.graphics.Insets mInsetsHint = android.graphics.Insets.NONE;
    private boolean mInsetsHintStale = true;
    private boolean mCropToProvidingInsets = false;

    InsetsSourceProvider(android.view.InsetsSource insetsSource, com.android.server.wm.InsetsStateController insetsStateController, com.android.server.wm.DisplayContent displayContent) {
        boolean z;
        if ((android.view.WindowInsets.Type.defaultVisible() & insetsSource.getType()) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mClientVisible = z;
        this.mSource = insetsSource;
        this.mDisplayContent = displayContent;
        this.mStateController = insetsStateController;
        this.mFakeControl = new android.view.InsetsSourceControl(insetsSource.getId(), insetsSource.getType(), (android.view.SurfaceControl) null, false, new android.graphics.Point(), android.graphics.Insets.NONE);
        this.mControllable = (insetsSource.getType() & com.android.server.wm.InsetsPolicy.CONTROLLABLE_TYPES) != 0;
        this.mSetLeashPositionConsumer = new java.util.function.Consumer() { // from class: com.android.server.wm.InsetsSourceProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.InsetsSourceProvider.this.lambda$new$0((android.view.SurfaceControl.Transaction) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.SurfaceControl.Transaction transaction) {
        android.view.SurfaceControl leash;
        if (this.mControl != null && (leash = this.mControl.getLeash()) != null) {
            android.graphics.Point surfacePosition = this.mControl.getSurfacePosition();
            transaction.setPosition(leash, surfacePosition.x, surfacePosition.y);
        }
        if (this.mHasPendingPosition) {
            this.mHasPendingPosition = false;
            if (this.mPendingControlTarget != this.mControlTarget) {
                this.mStateController.notifyControlTargetChanged(this.mPendingControlTarget, this);
            }
        }
    }

    android.view.InsetsSource getSource() {
        return this.mSource;
    }

    boolean isControllable() {
        return this.mControllable;
    }

    void setWindowContainer(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer, @android.annotation.Nullable com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer> triFunction, @android.annotation.Nullable android.util.SparseArray<com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer>> sparseArray) {
        if (this.mWindowContainer != null) {
            if (this.mControllable) {
                this.mWindowContainer.setControllableInsetProvider(null);
            }
            this.mWindowContainer.cancelAnimation();
            this.mWindowContainer.getInsetsSourceProviders().remove(this.mSource.getId());
            this.mSeamlessRotating = false;
            this.mHasPendingPosition = false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, 1522894362518893789L, 0, null, java.lang.String.valueOf(windowContainer), java.lang.String.valueOf(android.view.WindowInsets.Type.toString(this.mSource.getType())));
        this.mWindowContainer = windowContainer;
        this.mFrameProvider = triFunction;
        this.mOverrideFrames.clear();
        this.mOverrideFrameProviders = sparseArray;
        if (windowContainer == null) {
            setServerVisible(false);
            this.mSource.setVisibleFrame((android.graphics.Rect) null);
            this.mSource.setFlags(0, -1);
            this.mSourceFrame.setEmpty();
            return;
        }
        this.mWindowContainer.getInsetsSourceProviders().put(this.mSource.getId(), this);
        if (this.mControllable) {
            this.mWindowContainer.setControllableInsetProvider(this);
            if (this.mPendingControlTarget != this.mControlTarget) {
                updateControlForTarget(this.mPendingControlTarget, true);
            }
        }
    }

    boolean setFlags(int i, int i2) {
        this.mFlagsFromServer = (i & i2) | (this.mFlagsFromServer & (~i2));
        int i3 = this.mFlagsFromFrameProvider | this.mFlagsFromServer;
        if (this.mSource.getFlags() != i3) {
            this.mSource.setFlags(i3);
            return true;
        }
        return false;
    }

    void updateSourceFrame(android.graphics.Rect rect) {
        android.graphics.Rect rect2;
        if (this.mWindowContainer == null) {
            return;
        }
        com.android.server.wm.WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState == null) {
            if (this.mServerVisible) {
                this.mTmpRect.set(this.mWindowContainer.getBounds());
                if (this.mFrameProvider != null) {
                    this.mFrameProvider.apply(this.mWindowContainer.getDisplayContent().mDisplayFrames, this.mWindowContainer, this.mTmpRect);
                }
            } else {
                this.mTmpRect.setEmpty();
            }
            this.mSource.setFrame(this.mTmpRect);
            this.mSource.setVisibleFrame((android.graphics.Rect) null);
            return;
        }
        this.mSourceFrame.set(rect);
        if (this.mFrameProvider != null) {
            this.mFlagsFromFrameProvider = ((java.lang.Integer) this.mFrameProvider.apply(this.mWindowContainer.getDisplayContent().mDisplayFrames, this.mWindowContainer, this.mSourceFrame)).intValue();
            this.mSource.setFlags(this.mFlagsFromFrameProvider | this.mFlagsFromServer);
        }
        updateSourceFrameForServerVisibility();
        if (!this.mLastSourceFrame.equals(this.mSourceFrame)) {
            this.mLastSourceFrame.set(this.mSourceFrame);
            this.mInsetsHintStale = true;
        }
        if (this.mOverrideFrameProviders != null) {
            for (int size = this.mOverrideFrameProviders.size() - 1; size >= 0; size--) {
                int keyAt = this.mOverrideFrameProviders.keyAt(size);
                if (this.mOverrideFrames.contains(keyAt)) {
                    rect2 = this.mOverrideFrames.get(keyAt);
                    rect2.set(rect);
                } else {
                    rect2 = new android.graphics.Rect(rect);
                }
                if (this.mOverrideFrameProviders.get(keyAt) != null) {
                    this.mOverrideFrameProviders.get(keyAt).apply(this.mWindowContainer.getDisplayContent().mDisplayFrames, this.mWindowContainer, rect2);
                }
                this.mOverrideFrames.put(keyAt, rect2);
            }
        }
        if (asWindowState.mGivenVisibleInsets.left == 0 && asWindowState.mGivenVisibleInsets.top == 0 && asWindowState.mGivenVisibleInsets.right == 0 && asWindowState.mGivenVisibleInsets.bottom == 0) {
            this.mSource.setVisibleFrame((android.graphics.Rect) null);
            return;
        }
        this.mTmpRect.set(rect);
        this.mTmpRect.inset(asWindowState.mGivenVisibleInsets);
        this.mSource.setVisibleFrame(this.mTmpRect);
    }

    private void updateSourceFrameForServerVisibility() {
        android.graphics.Rect rect = this.mServerVisible ? this.mSourceFrame : EMPTY_RECT;
        if (this.mSource.getFrame().equals(rect)) {
            return;
        }
        this.mSource.setFrame(rect);
        if (this.mWindowContainer != null) {
            this.mSource.updateSideHint(this.mWindowContainer.getBounds());
        }
    }

    void onWindowContainerBoundsChanged() {
        this.mInsetsHintStale = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Insets getInsetsHint() {
        if (!this.mServerVisible) {
            return this.mInsetsHint;
        }
        com.android.server.wm.WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState != null && asWindowState.mGivenInsetsPending && asWindowState.mAttrs.providedInsets == null) {
            return this.mInsetsHint;
        }
        if (this.mInsetsHintStale) {
            this.mInsetsHint = this.mSource.calculateInsets(this.mWindowContainer.getBounds(), true);
            this.mInsetsHintStale = false;
        }
        return this.mInsetsHint;
    }

    android.view.InsetsSource createSimulatedSource(com.android.server.wm.DisplayFrames displayFrames, android.graphics.Rect rect) {
        android.view.InsetsSource insetsSource = new android.view.InsetsSource(this.mSource);
        this.mTmpRect.set(rect);
        if (this.mFrameProvider != null) {
            this.mFrameProvider.apply(displayFrames, this.mWindowContainer, this.mTmpRect);
        }
        insetsSource.setFrame(this.mTmpRect);
        insetsSource.setVisibleFrame((android.graphics.Rect) null);
        return insetsSource;
    }

    void onPostLayout() {
        boolean isVisibleRequested;
        com.android.server.wm.AsyncRotationController asyncRotationController;
        android.view.SurfaceControl.Transaction drawTransaction;
        if (this.mWindowContainer == null) {
            return;
        }
        com.android.server.wm.WindowState asWindowState = this.mWindowContainer.asWindowState();
        boolean z = false;
        boolean z2 = true;
        if (asWindowState != null) {
            isVisibleRequested = asWindowState.wouldBeVisibleIfPolicyIgnored() && asWindowState.isVisibleByPolicy();
        } else {
            isVisibleRequested = this.mWindowContainer.isVisibleRequested();
        }
        setServerVisible(isVisibleRequested);
        if (this.mControl != null) {
            android.graphics.Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
            if (this.mControl.setSurfacePosition(windowFrameSurfacePosition.x, windowFrameSurfacePosition.y) && this.mControlTarget != null) {
                if (asWindowState != null && asWindowState.getWindowFrames().didFrameSizeChange() && asWindowState.mWinAnimator.getShown() && this.mWindowContainer.okToDisplay()) {
                    this.mHasPendingPosition = true;
                    asWindowState.applyWithNextDraw(this.mSetLeashPositionConsumer);
                } else {
                    android.view.SurfaceControl.Transaction syncTransaction = this.mWindowContainer.getSyncTransaction();
                    if (asWindowState != null && (asyncRotationController = this.mDisplayContent.getAsyncRotationController()) != null && (drawTransaction = asyncRotationController.getDrawTransaction(asWindowState.mToken)) != null) {
                        syncTransaction = drawTransaction;
                    }
                    this.mSetLeashPositionConsumer.accept(syncTransaction);
                }
                z = true;
            }
            android.graphics.Insets insetsHint = getInsetsHint();
            if (this.mControl.getInsetsHint().equals(insetsHint)) {
                z2 = z;
            } else {
                this.mControl.setInsetsHint(insetsHint);
            }
            if (z2) {
                this.mStateController.notifyControlChanged(this.mControlTarget);
            }
        }
    }

    private android.graphics.Point getWindowFrameSurfacePosition() {
        com.android.server.wm.AsyncRotationController asyncRotationController;
        com.android.server.wm.WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState != null && this.mControl != null && (asyncRotationController = this.mDisplayContent.getAsyncRotationController()) != null && asyncRotationController.shouldFreezeInsetsPosition(asWindowState)) {
            return this.mControl.getSurfacePosition();
        }
        android.graphics.Rect frame = asWindowState != null ? asWindowState.getFrame() : this.mWindowContainer.getBounds();
        android.graphics.Point point = new android.graphics.Point();
        this.mWindowContainer.transformFrameToSurfacePosition(frame.left, frame.top, point);
        return point;
    }

    void updateFakeControlTarget(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget == this.mFakeControlTarget) {
            return;
        }
        this.mFakeControlTarget = insetsControlTarget;
    }

    void setCropToProvidingInsetsBounds(android.view.SurfaceControl.Transaction transaction) {
        this.mCropToProvidingInsets = true;
        if (this.mWindowContainer != null && this.mWindowContainer.mSurfaceAnimator.hasLeash()) {
            transaction.setWindowCrop(this.mWindowContainer.mSurfaceAnimator.mLeash, getProvidingInsetsBoundsCropRect());
        }
    }

    void removeCropToProvidingInsetsBounds(android.view.SurfaceControl.Transaction transaction) {
        this.mCropToProvidingInsets = false;
        if (this.mWindowContainer != null && this.mWindowContainer.mSurfaceAnimator.hasLeash()) {
            transaction.setWindowCrop(this.mWindowContainer.mSurfaceAnimator.mLeash, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.graphics.Rect getProvidingInsetsBoundsCropRect() {
        android.graphics.Rect bounds;
        if (this.mWindowContainer.asWindowState() != null) {
            bounds = this.mWindowContainer.asWindowState().getFrame();
        } else {
            bounds = this.mWindowContainer.getBounds();
        }
        android.graphics.Rect frame = getSource().getFrame();
        return new android.graphics.Rect(frame.left - bounds.left, frame.top - bounds.top, frame.right - bounds.left, frame.bottom - bounds.top);
    }

    void updateControlForTarget(@android.annotation.Nullable com.android.server.wm.InsetsControlTarget insetsControlTarget, boolean z) {
        if (this.mSeamlessRotating) {
            return;
        }
        this.mPendingControlTarget = insetsControlTarget;
        if (this.mWindowContainer != null && this.mWindowContainer.getSurfaceControl() == null) {
            setWindowContainer(null, null, null);
        }
        if (this.mWindowContainer == null) {
            return;
        }
        if ((insetsControlTarget != this.mControlTarget || z) && !this.mHasPendingPosition) {
            if (insetsControlTarget == null) {
                this.mWindowContainer.cancelAnimation();
                setClientVisible((android.view.WindowInsets.Type.defaultVisible() & this.mSource.getType()) != 0);
                return;
            }
            android.graphics.Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
            this.mAdapter = new com.android.server.wm.InsetsSourceProvider.ControlAdapter(windowFrameSurfacePosition);
            if (this.mSource.getType() == android.view.WindowInsets.Type.ime()) {
                setClientVisible(insetsControlTarget.isRequestedVisible(android.view.WindowInsets.Type.ime()));
            }
            this.mWindowContainer.startAnimation(this.mWindowContainer.getSyncTransaction(), this.mAdapter, true ^ this.mClientVisible, 32);
            this.mIsLeashReadyForDispatching = false;
            android.view.SurfaceControl surfaceControl = this.mAdapter.mCapturedLeash;
            this.mControlTarget = insetsControlTarget;
            updateVisibility();
            this.mControl = new android.view.InsetsSourceControl(this.mSource.getId(), this.mSource.getType(), surfaceControl, this.mClientVisible, windowFrameSurfacePosition, getInsetsHint());
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, 6243049416211184258L, 0, null, java.lang.String.valueOf(this.mControl), java.lang.String.valueOf(this.mControlTarget));
        }
    }

    void startSeamlessRotation() {
        if (!this.mSeamlessRotating) {
            this.mSeamlessRotating = true;
            this.mWindowContainer.cancelAnimation();
        }
    }

    void finishSeamlessRotation() {
        this.mSeamlessRotating = false;
    }

    boolean updateClientVisibility(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        boolean isRequestedVisible = insetsControlTarget.isRequestedVisible(this.mSource.getType());
        if (insetsControlTarget != this.mControlTarget || isRequestedVisible == this.mClientVisible) {
            return false;
        }
        setClientVisible(isRequestedVisible);
        return true;
    }

    void onSurfaceTransactionApplied() {
        this.mIsLeashReadyForDispatching = true;
    }

    void setClientVisible(boolean z) {
        if (this.mClientVisible == z) {
            return;
        }
        this.mClientVisible = z;
        updateVisibility();
        this.mDisplayContent.setLayoutNeeded();
        this.mDisplayContent.mWmService.mWindowPlacerLocked.requestTraversal();
    }

    @com.android.internal.annotations.VisibleForTesting
    void setServerVisible(boolean z) {
        this.mServerVisible = z;
        updateSourceFrameForServerVisibility();
        updateVisibility();
    }

    protected void updateVisibility() {
        this.mSource.setVisible(this.mServerVisible && this.mClientVisible);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, -8234068212532234206L, 0, null, java.lang.String.valueOf(android.view.WindowInsets.Type.toString(this.mSource.getType())), java.lang.String.valueOf(this.mServerVisible), java.lang.String.valueOf(this.mClientVisible));
    }

    android.view.InsetsSourceControl getControl(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget == this.mControlTarget) {
            if (!this.mIsLeashReadyForDispatching && this.mControl != null) {
                return new android.view.InsetsSourceControl(this.mControl.getId(), this.mControl.getType(), (android.view.SurfaceControl) null, this.mControl.isInitiallyVisible(), this.mControl.getSurfacePosition(), this.mControl.getInsetsHint());
            }
            return this.mControl;
        }
        if (insetsControlTarget == this.mFakeControlTarget) {
            return this.mFakeControl;
        }
        return null;
    }

    com.android.server.wm.InsetsControlTarget getControlTarget() {
        return this.mControlTarget;
    }

    com.android.server.wm.InsetsControlTarget getFakeControlTarget() {
        return this.mFakeControlTarget;
    }

    boolean isClientVisible() {
        return this.mClientVisible;
    }

    boolean overridesFrame(int i) {
        return this.mOverrideFrames.contains(i);
    }

    android.graphics.Rect getOverriddenFrame(int i) {
        return this.mOverrideFrames.get(i);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + getClass().getSimpleName());
        java.lang.String str2 = str + "  ";
        printWriter.print(str2 + "mSource=");
        this.mSource.dump("", printWriter);
        printWriter.print(str2 + "mSourceFrame=");
        printWriter.println(this.mSourceFrame);
        if (this.mOverrideFrames.size() > 0) {
            printWriter.print(str2 + "mOverrideFrames=");
            printWriter.println(this.mOverrideFrames);
        }
        if (this.mControl != null) {
            printWriter.print(str2 + "mControl=");
            this.mControl.dump("", printWriter);
        }
        if (this.mControllable) {
            printWriter.print(str2 + "mInsetsHint=");
            printWriter.print(this.mInsetsHint);
            if (this.mInsetsHintStale) {
                printWriter.print(" stale");
            }
            printWriter.println();
        }
        printWriter.print(str2);
        printWriter.print("mIsLeashReadyForDispatching=");
        printWriter.print(this.mIsLeashReadyForDispatching);
        printWriter.print(" mHasPendingPosition=");
        printWriter.print(this.mHasPendingPosition);
        printWriter.println();
        if (this.mWindowContainer != null) {
            printWriter.print(str2 + "mWindowContainer=");
            printWriter.println(this.mWindowContainer);
        }
        if (this.mAdapter != null) {
            printWriter.print(str2 + "mAdapter=");
            this.mAdapter.dump(printWriter, "");
        }
        if (this.mControlTarget != null) {
            printWriter.print(str2 + "mControlTarget=");
            printWriter.println(this.mControlTarget);
        }
        if (this.mPendingControlTarget != this.mControlTarget) {
            printWriter.print(str2 + "mPendingControlTarget=");
            printWriter.println(this.mPendingControlTarget);
        }
        if (this.mFakeControlTarget != null) {
            printWriter.print(str2 + "mFakeControlTarget=");
            printWriter.println(this.mFakeControlTarget);
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        this.mSource.dumpDebug(protoOutputStream, 1146756268033L);
        this.mTmpRect.dumpDebug(protoOutputStream, 1146756268034L);
        this.mFakeControl.dumpDebug(protoOutputStream, 1146756268035L);
        if (this.mControl != null) {
            this.mControl.dumpDebug(protoOutputStream, 1146756268036L);
        }
        if (this.mControlTarget != null && this.mControlTarget.getWindow() != null) {
            this.mControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268037L, i);
        }
        if (this.mPendingControlTarget != null && this.mPendingControlTarget != this.mControlTarget && this.mPendingControlTarget.getWindow() != null) {
            this.mPendingControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268038L, i);
        }
        if (this.mFakeControlTarget != null && this.mFakeControlTarget.getWindow() != null) {
            this.mFakeControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268039L, i);
        }
        if (this.mAdapter != null && this.mAdapter.mCapturedLeash != null) {
            this.mAdapter.mCapturedLeash.dumpDebug(protoOutputStream, 1146756268040L);
        }
        protoOutputStream.write(1133871366154L, this.mIsLeashReadyForDispatching);
        protoOutputStream.write(1133871366155L, this.mClientVisible);
        protoOutputStream.write(1133871366156L, this.mServerVisible);
        protoOutputStream.write(1133871366157L, this.mSeamlessRotating);
        protoOutputStream.write(1133871366159L, this.mControllable);
        if (this.mWindowContainer != null && this.mWindowContainer.asWindowState() != null) {
            this.mWindowContainer.asWindowState().dumpDebug(protoOutputStream, 1146756268048L, i);
        }
        protoOutputStream.end(start);
    }

    private class ControlAdapter implements com.android.server.wm.AnimationAdapter {
        private android.view.SurfaceControl mCapturedLeash;
        private final android.graphics.Point mSurfacePosition;

        ControlAdapter(android.graphics.Point point) {
            this.mSurfacePosition = point;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            if (com.android.server.wm.InsetsSourceProvider.this.mSource.getType() == android.view.WindowInsets.Type.ime()) {
                transaction.setAlpha(surfaceControl, 1.0f);
                transaction.hide(surfaceControl);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, -8601070090234611338L, 0, null, java.lang.String.valueOf(com.android.server.wm.InsetsSourceProvider.this.mSource), java.lang.String.valueOf(com.android.server.wm.InsetsSourceProvider.this.mControlTarget));
            this.mCapturedLeash = surfaceControl;
            transaction.setPosition(this.mCapturedLeash, this.mSurfacePosition.x, this.mSurfacePosition.y);
            if (com.android.server.wm.InsetsSourceProvider.this.mCropToProvidingInsets) {
                transaction.setWindowCrop(this.mCapturedLeash, com.android.server.wm.InsetsSourceProvider.this.getProvidingInsetsBoundsCropRect());
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
            if (com.android.server.wm.InsetsSourceProvider.this.mAdapter == this) {
                com.android.server.wm.InsetsSourceProvider.this.mStateController.notifyControlRevoked(com.android.server.wm.InsetsSourceProvider.this.mControlTarget, com.android.server.wm.InsetsSourceProvider.this);
                com.android.server.wm.InsetsSourceProvider.this.mControl = null;
                com.android.server.wm.InsetsSourceProvider.this.mControlTarget = null;
                com.android.server.wm.InsetsSourceProvider.this.mAdapter = null;
                com.android.server.wm.InsetsSourceProvider.this.setClientVisible((android.view.WindowInsets.Type.defaultVisible() & com.android.server.wm.InsetsSourceProvider.this.mSource.getType()) != 0);
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, -6857870589074001153L, 0, null, java.lang.String.valueOf(com.android.server.wm.InsetsSourceProvider.this.mSource), java.lang.String.valueOf(com.android.server.wm.InsetsSourceProvider.this.mControlTarget));
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getStatusBarTransitionsStartTime() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str + "ControlAdapter mCapturedLeash=");
            printWriter.print(this.mCapturedLeash);
            printWriter.println();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        }
    }
}
