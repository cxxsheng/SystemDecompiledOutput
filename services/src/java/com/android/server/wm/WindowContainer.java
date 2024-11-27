package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowContainer<E extends com.android.server.wm.WindowContainer> extends com.android.server.wm.ConfigurationContainer<E> implements java.lang.Comparable<com.android.server.wm.WindowContainer>, com.android.server.wm.SurfaceAnimator.Animatable, com.android.server.wm.SurfaceFreezer.Freezable, com.android.server.wm.InsetsControlTarget {
    static final int POSITION_BOTTOM = Integer.MIN_VALUE;
    static final int POSITION_TOP = Integer.MAX_VALUE;
    public static final int SYNC_STATE_NONE = 0;
    public static final int SYNC_STATE_READY = 2;
    public static final int SYNC_STATE_WAITING_FOR_DRAW = 1;
    private static final java.lang.String TAG = "WindowManager";
    android.view.SurfaceControl mAnimationBoundsLayer;

    @android.annotation.Nullable
    private android.view.SurfaceControl mAnimationLeash;
    private boolean mCommittedReparentToAnimationLeash;

    @android.annotation.Nullable
    protected com.android.server.wm.InsetsSourceProvider mControllableInsetProvider;
    protected com.android.server.wm.DisplayContent mDisplayContent;

    @android.annotation.Nullable
    private android.util.ArrayMap<android.os.IBinder, com.android.server.wm.WindowContainer<E>.DeathRecipient> mInsetsOwnerDeathRecipientMap;
    private android.view.MagnificationSpec mLastMagnificationSpec;
    protected com.android.server.wm.WindowContainer mLastOrientationSource;
    boolean mLaunchTaskBehind;
    boolean mNeedsAnimationBoundsLayer;

    @com.android.internal.annotations.VisibleForTesting
    boolean mNeedsZBoost;
    protected com.android.server.wm.TrustedOverlayHost mOverlayHost;
    private final android.view.SurfaceControl.Transaction mPendingTransaction;
    boolean mReparenting;
    protected final com.android.server.wm.SurfaceAnimator mSurfaceAnimator;
    protected android.view.SurfaceControl mSurfaceControl;
    final com.android.server.wm.SurfaceFreezer mSurfaceFreezer;
    final android.view.SurfaceControl.Transaction mSyncTransaction;
    com.android.server.wm.WindowContainerThumbnail mThumbnail;
    int mTransit;
    int mTransitFlags;
    final com.android.server.wm.TransitionController mTransitionController;
    protected boolean mVisibleRequested;
    protected final com.android.server.wm.WindowManagerService mWmService;
    private com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> mParent = null;

    @android.annotation.Nullable
    android.util.SparseArray<android.view.InsetsSource> mLocalInsetsSources = null;
    protected android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> mInsetsSourceProviders = null;
    protected final com.android.server.wm.WindowList<E> mChildren = new com.android.server.wm.WindowList<>();
    private int mOverrideOrientation = -1;
    private final android.util.Pools.SynchronizedPool<com.android.server.wm.WindowContainer<E>.ForAllWindowsConsumerWrapper> mConsumerWrapperPool = new android.util.Pools.SynchronizedPool<>(3);
    private int mLastLayer = 0;
    private android.view.SurfaceControl mLastRelativeToLayer = null;
    final java.util.ArrayList<com.android.server.wm.WindowState> mWaitingForDrawn = new java.util.ArrayList<>();
    private final android.util.ArraySet<com.android.server.wm.WindowContainer> mSurfaceAnimationSources = new android.util.ArraySet<>();
    private final android.graphics.Point mTmpPos = new android.graphics.Point();
    protected final android.graphics.Point mLastSurfacePosition = new android.graphics.Point();
    protected int mLastDeltaRotation = 0;
    private int mTreeWeight = 1;
    private int mSyncTransactionCommitCallbackDepth = 0;
    final android.graphics.Point mTmpPoint = new android.graphics.Point();
    protected final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    final android.graphics.Rect mTmpPrevBounds = new android.graphics.Rect();
    private boolean mIsFocusable = true;
    com.android.server.wm.WindowContainer.RemoteToken mRemoteToken = null;
    com.android.server.wm.BLASTSyncEngine.SyncGroup mSyncGroup = null;

    @com.android.server.wm.WindowContainer.SyncState
    int mSyncState = 0;
    int mSyncMethodOverride = -1;
    private final java.util.List<com.android.server.wm.WindowContainerListener> mListeners = new java.util.ArrayList();
    private final java.util.LinkedList<com.android.server.wm.WindowContainer> mTmpChain1 = new java.util.LinkedList<>();
    private final java.util.LinkedList<com.android.server.wm.WindowContainer> mTmpChain2 = new java.util.LinkedList<>();

    public interface AnimationFlags {
        public static final int CHILDREN = 4;
        public static final int PARENTS = 2;
        public static final int TRANSITION = 1;
    }

    @java.lang.FunctionalInterface
    interface ConfigurationMerger {
        android.content.res.Configuration merge(android.content.res.Configuration configuration, android.content.res.Configuration configuration2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface IAnimationStarter {
        void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, @android.annotation.Nullable com.android.server.wm.AnimationAdapter animationAdapter2);
    }

    @interface SyncState {
    }

    WindowContainer(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWmService = windowManagerService;
        this.mTransitionController = this.mWmService.mAtmService.getTransitionController();
        this.mPendingTransaction = windowManagerService.mTransactionFactory.get();
        this.mSyncTransaction = windowManagerService.mTransactionFactory.get();
        this.mSurfaceAnimator = new com.android.server.wm.SurfaceAnimator(this, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda13
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
                com.android.server.wm.WindowContainer.this.onAnimationFinished(i, animationAdapter);
            }
        }, windowManagerService);
        this.mSurfaceFreezer = new com.android.server.wm.SurfaceFreezer(this, windowManagerService);
    }

    void updateAboveInsetsState(android.view.InsetsState insetsState, android.util.SparseArray<android.view.InsetsSource> sparseArray, android.util.ArraySet<com.android.server.wm.WindowState> arraySet) {
        android.util.SparseArray<android.view.InsetsSource> createMergedSparseArray = createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).updateAboveInsetsState(insetsState, createMergedSparseArray, arraySet);
        }
    }

    static <T> android.util.SparseArray<T> createMergedSparseArray(android.util.SparseArray<T> sparseArray, android.util.SparseArray<T> sparseArray2) {
        int size = sparseArray != null ? sparseArray.size() : 0;
        int size2 = sparseArray2 != null ? sparseArray2.size() : 0;
        android.util.SparseArray<T> sparseArray3 = new android.util.SparseArray<>(size + size2);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                sparseArray3.append(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }
        if (size2 > 0) {
            for (int i2 = 0; i2 < size2; i2++) {
                sparseArray3.put(sparseArray2.keyAt(i2), sparseArray2.valueAt(i2));
            }
        }
        return sparseArray3;
    }

    void addLocalInsetsFrameProvider(android.view.InsetsFrameProvider insetsFrameProvider, android.os.IBinder iBinder) {
        if (insetsFrameProvider == null || iBinder == null) {
            throw new java.lang.IllegalArgumentException("Insets provider or owner not specified.");
        }
        if (this.mDisplayContent == null) {
            android.util.Slog.w(TAG, "Can't add insets frame provider when detached. " + this);
            return;
        }
        if (this.mInsetsOwnerDeathRecipientMap == null) {
            this.mInsetsOwnerDeathRecipientMap = new android.util.ArrayMap<>();
        }
        com.android.server.wm.WindowContainer<E>.DeathRecipient deathRecipient = this.mInsetsOwnerDeathRecipientMap.get(iBinder);
        if (deathRecipient == null) {
            deathRecipient = new com.android.server.wm.WindowContainer.DeathRecipient(iBinder);
            try {
                iBinder.linkToDeath(deathRecipient, 0);
                this.mInsetsOwnerDeathRecipientMap.put(iBinder, deathRecipient);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to add source for " + insetsFrameProvider + " since the owner has died.");
                return;
            }
        }
        int id = insetsFrameProvider.getId();
        deathRecipient.addSourceId(id);
        if (this.mLocalInsetsSources == null) {
            this.mLocalInsetsSources = new android.util.SparseArray<>();
        }
        this.mLocalInsetsSources.get(id);
        android.view.InsetsSource insetsSource = new android.view.InsetsSource(id, insetsFrameProvider.getType());
        insetsSource.setFrame(insetsFrameProvider.getArbitraryRectangle()).updateSideHint(getBounds()).setBoundingRects(insetsFrameProvider.getBoundingRects());
        this.mLocalInsetsSources.put(id, insetsSource);
        this.mDisplayContent.getInsetsStateController().updateAboveInsetsState(true);
    }

    private class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mOwner;
        private final android.util.ArraySet<java.lang.Integer> mSourceIds = new android.util.ArraySet<>();

        DeathRecipient(android.os.IBinder iBinder) {
            this.mOwner = iBinder;
        }

        void addSourceId(int i) {
            this.mSourceIds.add(java.lang.Integer.valueOf(i));
        }

        void removeSourceId(int i) {
            this.mSourceIds.remove(java.lang.Integer.valueOf(i));
        }

        boolean hasSource() {
            return !this.mSourceIds.isEmpty();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowContainer.this.mWmService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    boolean z = false;
                    for (int size = this.mSourceIds.size() - 1; size >= 0; size--) {
                        z |= com.android.server.wm.WindowContainer.this.removeLocalInsetsSource(this.mSourceIds.valueAt(size).intValue());
                    }
                    this.mSourceIds.clear();
                    this.mOwner.unlinkToDeath(this, 0);
                    com.android.server.wm.WindowContainer.this.mInsetsOwnerDeathRecipientMap.remove(this.mOwner);
                    if (z && com.android.server.wm.WindowContainer.this.mDisplayContent != null) {
                        com.android.server.wm.WindowContainer.this.mDisplayContent.getInsetsStateController().updateAboveInsetsState(true);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    void removeLocalInsetsFrameProvider(android.view.InsetsFrameProvider insetsFrameProvider, android.os.IBinder iBinder) {
        com.android.server.wm.WindowContainer<E>.DeathRecipient deathRecipient;
        if (insetsFrameProvider == null || iBinder == null) {
            throw new java.lang.IllegalArgumentException("Insets provider or owner not specified.");
        }
        int id = insetsFrameProvider.getId();
        if (removeLocalInsetsSource(id) && this.mDisplayContent != null) {
            this.mDisplayContent.getInsetsStateController().updateAboveInsetsState(true);
        }
        if (this.mInsetsOwnerDeathRecipientMap == null || (deathRecipient = this.mInsetsOwnerDeathRecipientMap.get(iBinder)) == null) {
            return;
        }
        deathRecipient.removeSourceId(id);
        if (!deathRecipient.hasSource()) {
            iBinder.unlinkToDeath(deathRecipient, 0);
            this.mInsetsOwnerDeathRecipientMap.remove(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeLocalInsetsSource(int i) {
        return (this.mLocalInsetsSources == null || this.mLocalInsetsSources.removeReturnOld(i) == null) ? false : true;
    }

    void setControllableInsetProvider(com.android.server.wm.InsetsSourceProvider insetsSourceProvider) {
        this.mControllableInsetProvider = insetsSourceProvider;
    }

    com.android.server.wm.InsetsSourceProvider getControllableInsetProvider() {
        return this.mControllableInsetProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.ConfigurationContainer
    public final com.android.server.wm.WindowContainer getParent() {
        return this.mParent;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    protected int getChildCount() {
        return this.mChildren.size();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.ConfigurationContainer
    public E getChildAt(int i) {
        return this.mChildren.get(i);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSurfacePositionNonOrganized();
        scheduleAnimation();
        if (this.mOverlayHost != null) {
            this.mOverlayHost.dispatchConfigurationChanged(getConfiguration());
        }
    }

    void reparent(com.android.server.wm.WindowContainer windowContainer, int i) {
        if (windowContainer == null) {
            throw new java.lang.IllegalArgumentException("reparent: can't reparent to null " + this);
        }
        if (windowContainer == this) {
            throw new java.lang.IllegalArgumentException("Can not reparent to itself " + this);
        }
        com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> windowContainer2 = this.mParent;
        if (this.mParent == windowContainer) {
            throw new java.lang.IllegalArgumentException("WC=" + this + " already child of " + this.mParent);
        }
        this.mTransitionController.collectReparentChange(this, windowContainer);
        com.android.server.wm.DisplayContent displayContent = windowContainer2.getDisplayContent();
        com.android.server.wm.DisplayContent displayContent2 = windowContainer.getDisplayContent();
        this.mReparenting = true;
        windowContainer2.removeChild(this);
        windowContainer.addChild(this, i);
        this.mReparenting = false;
        displayContent2.setLayoutNeeded();
        if (displayContent != displayContent2) {
            onDisplayChanged(displayContent2);
            displayContent.setLayoutNeeded();
        }
        onParentChanged(windowContainer, windowContainer2);
        onSyncReparent(windowContainer2, windowContainer);
    }

    protected final void setParent(com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> windowContainer) {
        com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> windowContainer2 = this.mParent;
        this.mParent = windowContainer;
        if (this.mParent != null) {
            this.mParent.onChildAdded(this);
        } else if (this.mSurfaceAnimator.hasLeash()) {
            this.mSurfaceAnimator.cancelAnimation();
        }
        if (!this.mReparenting) {
            onSyncReparent(windowContainer2, this.mParent);
            if (this.mParent != null && this.mParent.mDisplayContent != null && this.mDisplayContent != this.mParent.mDisplayContent) {
                onDisplayChanged(this.mParent.mDisplayContent);
            }
            onParentChanged(this.mParent, windowContainer2);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        if (this.mParent == null) {
            return;
        }
        if (this.mSurfaceControl == null) {
            createSurfaceControl(false);
        } else {
            reparentSurfaceControl(getSyncTransaction(), this.mParent.mSurfaceControl);
        }
        this.mParent.assignChildLayers();
    }

    void createSurfaceControl(boolean z) {
        setInitialSurfaceControlProperties(makeSurface());
    }

    void setInitialSurfaceControlProperties(android.view.SurfaceControl.Builder builder) {
        setSurfaceControl(builder.setCallsite("WindowContainer.setInitialSurfaceControlProperties").build());
        if (showSurfaceOnCreation()) {
            getSyncTransaction().show(this.mSurfaceControl);
        }
        updateSurfacePositionNonOrganized();
        if (this.mLastMagnificationSpec != null) {
            applyMagnificationSpec(getSyncTransaction(), this.mLastMagnificationSpec);
        }
    }

    void migrateToNewSurfaceControl(android.view.SurfaceControl.Transaction transaction) {
        transaction.remove(this.mSurfaceControl);
        this.mLastSurfacePosition.set(0, 0);
        this.mLastDeltaRotation = 0;
        setInitialSurfaceControlProperties(this.mWmService.makeSurfaceBuilder(null).setContainerLayer().setName(getName()));
        transaction.reparent(this.mSurfaceControl, this.mParent != null ? this.mParent.getSurfaceControl() : null);
        if (this.mLastRelativeToLayer != null) {
            transaction.setRelativeLayer(this.mSurfaceControl, this.mLastRelativeToLayer, this.mLastLayer);
        } else {
            transaction.setLayer(this.mSurfaceControl, this.mLastLayer);
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            android.view.SurfaceControl surfaceControl = this.mChildren.get(i).getSurfaceControl();
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, this.mSurfaceControl);
            }
        }
        if (this.mOverlayHost != null) {
            this.mOverlayHost.setParent(transaction, this.mSurfaceControl);
        }
        scheduleAnimation();
    }

    protected void addChild(E e, java.util.Comparator<E> comparator) {
        int i;
        if (!e.mReparenting && e.getParent() != null) {
            throw new java.lang.IllegalArgumentException("addChild: container=" + e.getName() + " is already a child of container=" + e.getParent().getName() + " can't add to container=" + getName());
        }
        if (comparator != null) {
            int size = this.mChildren.size();
            i = 0;
            while (i < size) {
                if (comparator.compare(e, this.mChildren.get(i)) < 0) {
                    break;
                } else {
                    i++;
                }
            }
        }
        i = -1;
        if (i == -1) {
            this.mChildren.add(e);
        } else {
            this.mChildren.add(i, e);
        }
        e.setParent(this);
    }

    void addChild(E e, int i) {
        if (!e.mReparenting && e.getParent() != null) {
            throw new java.lang.IllegalArgumentException("addChild: container=" + e.getName() + " is already a child of container=" + e.getParent().getName() + " can't add to container=" + getName() + "\n callers=" + android.os.Debug.getCallers(15, "\n"));
        }
        if ((i < 0 && i != Integer.MIN_VALUE) || (i > this.mChildren.size() && i != Integer.MAX_VALUE)) {
            throw new java.lang.IllegalArgumentException("addChild: invalid position=" + i + ", children number=" + this.mChildren.size());
        }
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        this.mChildren.add(i, e);
        e.setParent(this);
    }

    private void onChildAdded(com.android.server.wm.WindowContainer windowContainer) {
        this.mTreeWeight += windowContainer.mTreeWeight;
        for (com.android.server.wm.WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.mTreeWeight += windowContainer.mTreeWeight;
        }
        onChildVisibleRequestedChanged(windowContainer);
        onChildPositionChanged(windowContainer);
    }

    void removeChild(E e) {
        if (this.mChildren.remove(e)) {
            onChildRemoved(e);
            if (!e.mReparenting) {
                e.setParent(null);
                return;
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("removeChild: container=" + e.getName() + " is not a child of container=" + getName());
    }

    private void onChildRemoved(com.android.server.wm.WindowContainer windowContainer) {
        this.mTreeWeight -= windowContainer.mTreeWeight;
        for (com.android.server.wm.WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.mTreeWeight -= windowContainer.mTreeWeight;
        }
        onChildVisibleRequestedChanged(null);
        onChildPositionChanged(windowContainer);
    }

    void removeImmediately() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.mClosingChangingContainers.remove(this);
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        while (!this.mChildren.isEmpty()) {
            E peekLast = this.mChildren.peekLast();
            peekLast.removeImmediately();
            if (this.mChildren.remove(peekLast)) {
                onChildRemoved(peekLast);
            }
        }
        if (this.mSurfaceControl != null) {
            getSyncTransaction().remove(this.mSurfaceControl);
            setSurfaceControl(null);
            this.mLastSurfacePosition.set(0, 0);
            this.mLastDeltaRotation = 0;
            scheduleAnimation();
        }
        if (this.mOverlayHost != null) {
            this.mOverlayHost.release();
            this.mOverlayHost = null;
        }
        if (this.mParent != null) {
            this.mParent.removeChild(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            this.mListeners.get(size).onRemoved();
        }
    }

    int getTreeWeight() {
        return this.mTreeWeight;
    }

    int getPrefixOrderIndex() {
        if (this.mParent == null) {
            return 0;
        }
        return this.mParent.getPrefixOrderIndex(this);
    }

    private int getPrefixOrderIndex(com.android.server.wm.WindowContainer windowContainer) {
        E e;
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size() && windowContainer != (e = this.mChildren.get(i2)); i2++) {
            i += e.mTreeWeight;
        }
        if (this.mParent != null) {
            i += this.mParent.getPrefixOrderIndex(this);
        }
        return i + 1;
    }

    void removeIfPossible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).removeIfPossible();
        }
    }

    boolean hasChild(E e) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            E e2 = this.mChildren.get(size);
            if (e2 == e || e2.hasChild(e)) {
                return true;
            }
        }
        return false;
    }

    boolean isDescendantOf(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == windowContainer) {
            return true;
        }
        return parent != null && parent.isDescendantOf(windowContainer);
    }

    void positionChildAt(int i, E e, boolean z) {
        if (e.getParent() != this) {
            throw new java.lang.IllegalArgumentException("positionChildAt: container=" + e.getName() + " is not a child of container=" + getName() + " current parent=" + e.getParent());
        }
        if (i >= this.mChildren.size() - 1) {
            i = Integer.MAX_VALUE;
        } else if (i <= 0) {
            i = Integer.MIN_VALUE;
        }
        switch (i) {
            case Integer.MIN_VALUE:
                if (this.mChildren.peekFirst() != e) {
                    this.mChildren.remove(e);
                    this.mChildren.addFirst(e);
                    onChildPositionChanged(e);
                }
                if (z && getParent() != null) {
                    getParent().positionChildAt(Integer.MIN_VALUE, this, true);
                    return;
                }
                return;
            case Integer.MAX_VALUE:
                if (this.mChildren.peekLast() != e) {
                    this.mChildren.remove(e);
                    this.mChildren.add(e);
                    onChildPositionChanged(e);
                }
                if (z && getParent() != null) {
                    getParent().positionChildAt(Integer.MAX_VALUE, this, true);
                    return;
                }
                return;
            default:
                if (this.mChildren.indexOf(e) != i) {
                    this.mChildren.remove(e);
                    this.mChildren.add(i, e);
                    onChildPositionChanged(e);
                    return;
                }
                return;
        }
    }

    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        int diffRequestedOverrideBounds = diffRequestedOverrideBounds(configuration.windowConfiguration.getBounds());
        super.onRequestedOverrideConfigurationChanged(configuration);
        if (this.mParent != null) {
            this.mParent.onDescendantOverrideConfigurationChanged();
        }
        if (diffRequestedOverrideBounds == 0) {
            return;
        }
        if ((diffRequestedOverrideBounds & 2) == 2) {
            onResize();
        } else {
            onMovedByResize();
        }
    }

    void onDescendantOverrideConfigurationChanged() {
        if (this.mParent != null) {
            this.mParent.onDescendantOverrideConfigurationChanged();
        }
    }

    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        if (this.mDisplayContent != null && this.mDisplayContent != displayContent) {
            this.mDisplayContent.mClosingChangingContainers.remove(this);
            if (this.mDisplayContent.mChangingContainers.remove(this)) {
                this.mSurfaceFreezer.unfreeze(getSyncTransaction());
            }
        }
        this.mDisplayContent = displayContent;
        if (displayContent != null && displayContent != this) {
            displayContent.getPendingTransaction().merge(this.mPendingTransaction);
        }
        if (displayContent != this && this.mLocalInsetsSources != null) {
            this.mLocalInsetsSources.clear();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).onDisplayChanged(displayContent);
        }
        for (int size2 = this.mListeners.size() - 1; size2 >= 0; size2--) {
            this.mListeners.get(size2).onDisplayChanged(displayContent);
        }
    }

    public boolean hasInsetsSourceProvider() {
        return this.mInsetsSourceProviders != null;
    }

    public android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> getInsetsSourceProviders() {
        if (this.mInsetsSourceProviders == null) {
            this.mInsetsSourceProviders = new android.util.SparseArray<>();
        }
        return this.mInsetsSourceProviders;
    }

    public final com.android.server.wm.DisplayContent getDisplayContent() {
        return this.mDisplayContent;
    }

    @android.annotation.Nullable
    com.android.server.wm.DisplayArea getDisplayArea() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getDisplayArea();
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.RootDisplayArea getRootDisplayArea() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getRootDisplayArea();
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskDisplayArea getTaskDisplayArea() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getTaskDisplayArea();
        }
        return null;
    }

    boolean isAttached() {
        com.android.server.wm.WindowContainer parent = getParent();
        return parent != null && parent.isAttached();
    }

    void onResize() {
        if (this.mControllableInsetProvider != null) {
            this.mControllableInsetProvider.onWindowContainerBoundsChanged();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).onParentResize();
        }
    }

    void onParentResize() {
        if (hasOverrideBounds()) {
            return;
        }
        onResize();
    }

    void onMovedByResize() {
        if (this.mControllableInsetProvider != null) {
            this.mControllableInsetProvider.onWindowContainerBoundsChanged();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).onMovedByResize();
        }
    }

    void resetDragResizingChangeReported() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).resetDragResizingChangeReported();
        }
    }

    boolean canCustomizeAppTransition() {
        return false;
    }

    final boolean isAnimating(int i, int i2) {
        return getAnimatingContainer(i, i2) != null;
    }

    @java.lang.Deprecated
    final boolean isAnimating(int i) {
        return isAnimating(i, -1);
    }

    boolean isWaitingForTransitionStart() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isAppTransitioning$0(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.isAnimating(3);
    }

    boolean isAppTransitioning() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isAppTransitioning$0;
                lambda$isAppTransitioning$0 = com.android.server.wm.WindowContainer.lambda$isAppTransitioning$0((com.android.server.wm.ActivityRecord) obj);
                return lambda$isAppTransitioning$0;
            }
        }) != null;
    }

    boolean inTransitionSelfOrParent() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(3, 9);
        }
        return inTransition();
    }

    final boolean isAnimating() {
        return isAnimating(0);
    }

    boolean isChangingAppTransition() {
        return this.mDisplayContent != null && this.mDisplayContent.mChangingContainers.contains(this);
    }

    boolean inTransition() {
        return this.mTransitionController.inTransition(this);
    }

    boolean isExitAnimationRunningSelfOrChild() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(5, 25);
        }
        if (this.mChildren.isEmpty() && inTransition()) {
            return true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).isExitAnimationRunningSelfOrChild()) {
                return true;
            }
        }
        return false;
    }

    void sendAppVisibilityToClients() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).sendAppVisibilityToClients();
        }
    }

    boolean hasContentToDisplay() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).hasContentToDisplay()) {
                return true;
            }
        }
        return false;
    }

    boolean isVisible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).isVisible()) {
                return true;
            }
        }
        return false;
    }

    boolean isVisibleRequested() {
        return this.mVisibleRequested;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PROTECTED)
    boolean setVisibleRequested(boolean z) {
        if (this.mVisibleRequested == z) {
            return false;
        }
        this.mVisibleRequested = z;
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibleRequestedChanged(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            this.mListeners.get(size).onVisibleRequestedChanged(this.mVisibleRequested);
        }
        return true;
    }

    protected boolean onChildVisibleRequestedChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        boolean z = false;
        boolean z2 = windowContainer != null && windowContainer.isVisibleRequested();
        boolean z3 = this.mVisibleRequested;
        if (z2 && !this.mVisibleRequested) {
            z = true;
        } else if (!z2 && this.mVisibleRequested) {
            int size = this.mChildren.size() - 1;
            while (true) {
                if (size >= 0) {
                    E e = this.mChildren.get(size);
                    if (e == windowContainer || !e.isVisibleRequested()) {
                        size--;
                    } else {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            z = z3;
        }
        return setVisibleRequested(z);
    }

    void onChildVisibilityRequested(boolean z) {
        boolean z2;
        if (!z) {
            if (asTaskFragment() == null) {
                z2 = false;
            } else {
                z2 = asTaskFragment().setClosingChangingStartBoundsIfNeeded();
            }
            if (!z2) {
                this.mSurfaceFreezer.unfreeze(getSyncTransaction());
            }
        }
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibilityRequested(z);
        }
    }

    boolean isClosingWhenResizing() {
        return this.mDisplayContent != null && this.mDisplayContent.mClosingChangingContainers.containsKey(this);
    }

    void writeIdentifierToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, com.android.server.am.ProcessList.INVALID_ADJ);
        protoOutputStream.write(1138166333443L, "WindowContainer");
        protoOutputStream.end(start);
    }

    boolean isFocusable() {
        com.android.server.wm.WindowContainer parent = getParent();
        return (parent == null || parent.isFocusable()) && this.mIsFocusable;
    }

    boolean setFocusable(boolean z) {
        if (this.mIsFocusable == z) {
            return false;
        }
        this.mIsFocusable = z;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean isOnTop() {
        com.android.server.wm.WindowContainer parent = getParent();
        return parent != 0 && parent.getTopChild() == this && parent.isOnTop();
    }

    E getTopChild() {
        return this.mChildren.peekLast();
    }

    boolean handleCompleteDeferredRemoval() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= this.mChildren.get(size).handleCompleteDeferredRemoval();
            if (!hasChild()) {
                return false;
            }
        }
        return z;
    }

    void checkAppWindowsReadyToShow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).checkAppWindowsReadyToShow();
        }
    }

    void onAppTransitionDone() {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).onAppTransitionDone();
        }
    }

    boolean onDescendantOrientationChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return false;
        }
        return parent.onDescendantOrientationChanged(windowContainer);
    }

    boolean handlesOrientationChangeFromDescendant(int i) {
        com.android.server.wm.WindowContainer parent = getParent();
        return parent != null && parent.handlesOrientationChangeFromDescendant(i);
    }

    int getRequestedConfigurationOrientation() {
        return getRequestedConfigurationOrientation(false);
    }

    int getRequestedConfigurationOrientation(boolean z) {
        return getRequestedConfigurationOrientation(z, getOverrideOrientation());
    }

    int getRequestedConfigurationOrientation(boolean z, int i) {
        com.android.server.wm.RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (z && rootDisplayArea != null && rootDisplayArea.isOrientationDifferentFromDisplay()) {
            i = android.content.pm.ActivityInfo.reverseOrientation(i);
        }
        if (i == 5) {
            if (this.mDisplayContent != null) {
                return this.mDisplayContent.getNaturalConfigurationOrientation();
            }
            return 0;
        }
        if (i == 14) {
            return getConfiguration().orientation;
        }
        if (android.content.pm.ActivityInfo.isFixedOrientationLandscape(i)) {
            return 2;
        }
        if (android.content.pm.ActivityInfo.isFixedOrientationPortrait(i)) {
            return 1;
        }
        return 0;
    }

    void setOrientation(int i) {
        setOrientation(i, null);
    }

    void setOrientation(int i, @android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        if (getOverrideOrientation() == i) {
            return;
        }
        setOverrideOrientation(i);
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            if (getConfiguration().orientation != getRequestedConfigurationOrientation() && (inMultiWindowMode() || !handlesOrientationChangeFromDescendant(i))) {
                onConfigurationChanged(parent.getConfiguration());
            }
            onDescendantOrientationChanged(windowContainer);
        }
    }

    int getOrientation() {
        return getOrientation(getOverrideOrientation());
    }

    int getOrientation(int i) {
        this.mLastOrientationSource = null;
        if (!providesOrientation()) {
            return -2;
        }
        if (getOverrideOrientation() != -2 && getOverrideOrientation() != -1) {
            this.mLastOrientationSource = this;
            return getOverrideOrientation();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            E e = this.mChildren.get(size);
            int orientation = e.getOrientation(i == 3 ? 3 : -2);
            if (orientation == 3) {
                this.mLastOrientationSource = e;
                i = orientation;
            } else if (orientation != -2 && (e.providesOrientation() || orientation != -1)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -5231580410559054259L, 4, null, java.lang.String.valueOf(e.toString()), java.lang.Long.valueOf(orientation), java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(orientation)));
                this.mLastOrientationSource = e;
                return orientation;
            }
        }
        return i;
    }

    protected int getOverrideOrientation() {
        return this.mOverrideOrientation;
    }

    protected void setOverrideOrientation(int i) {
        this.mOverrideOrientation = i;
    }

    @android.annotation.Nullable
    com.android.server.wm.WindowContainer getLastOrientationSource() {
        com.android.server.wm.WindowContainer lastOrientationSource;
        com.android.server.wm.WindowContainer<E> windowContainer = this.mLastOrientationSource;
        if (windowContainer != null && windowContainer != this && (lastOrientationSource = windowContainer.getLastOrientationSource()) != null) {
            return lastOrientationSource;
        }
        return windowContainer;
    }

    boolean providesOrientation() {
        return fillsParent();
    }

    boolean fillsParent() {
        return false;
    }

    static int computeScreenLayout(int i, int i2, int i3) {
        return android.content.res.Configuration.reduceScreenLayout(i & 63, java.lang.Math.max(i2, i3), java.lang.Math.min(i2, i3));
    }

    void switchUser(int i) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).switchUser(i);
        }
    }

    boolean showToCurrentUser() {
        return true;
    }

    void forAllWindowContainers(java.util.function.Consumer<com.android.server.wm.WindowContainer> consumer) {
        consumer.accept(this);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            this.mChildren.get(i).forAllWindowContainers(consumer);
        }
    }

    boolean forAllWindows(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (this.mChildren.get(size).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (this.mChildren.get(i).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    void forAllWindows(java.util.function.Consumer<com.android.server.wm.WindowState> consumer, boolean z) {
        com.android.server.wm.WindowContainer<E>.ForAllWindowsConsumerWrapper obtainConsumerWrapper = obtainConsumerWrapper(consumer);
        forAllWindows(obtainConsumerWrapper, z);
        obtainConsumerWrapper.release();
    }

    boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate) {
        return forAllActivities(predicate, true);
    }

    boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (this.mChildren.get(size).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (this.mChildren.get(i).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    void forAllActivities(java.util.function.Consumer<com.android.server.wm.ActivityRecord> consumer) {
        forAllActivities(consumer, true);
    }

    void forAllActivities(java.util.function.Consumer<com.android.server.wm.ActivityRecord> consumer, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                this.mChildren.get(size).forAllActivities(consumer, z);
            }
            return;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            this.mChildren.get(i).forAllActivities(consumer, z);
        }
    }

    final boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2) {
        return forAllActivities(predicate, windowContainer, z, z2, new boolean[1]);
    }

    private boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (processForAllActivitiesWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(size))) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (processForAllActivitiesWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean processForAllActivitiesWithBoundary(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, com.android.server.wm.WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer) {
            zArr[0] = true;
            if (!z) {
                return false;
            }
        }
        if (zArr[0]) {
            return windowContainer2.forAllActivities(predicate, z2);
        }
        return windowContainer2.forAllActivities(predicate, windowContainer, z, z2, zArr);
    }

    boolean hasActivity() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).hasActivity()) {
                return true;
            }
        }
        return false;
    }

    com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate) {
        return getActivity(predicate, true);
    }

    com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z) {
        return getActivity(predicate, z, null);
    }

    com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z, com.android.server.wm.ActivityRecord activityRecord) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                E e = this.mChildren.get(size);
                if (e == activityRecord) {
                    return activityRecord;
                }
                com.android.server.wm.ActivityRecord activity = e.getActivity(predicate, z, activityRecord);
                if (activity != null) {
                    return activity;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            E e2 = this.mChildren.get(i);
            if (e2 == activityRecord) {
                return activityRecord;
            }
            com.android.server.wm.ActivityRecord activity2 = e2.getActivity(predicate, z, activityRecord);
            if (activity2 != null) {
                return activity2;
            }
        }
        return null;
    }

    final com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2) {
        return getActivity(predicate, windowContainer, z, z2, new boolean[1]);
    }

    private com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityRecord processGetActivityWithBoundary = processGetActivityWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(size));
                if (processGetActivityWithBoundary != null) {
                    return processGetActivityWithBoundary;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            com.android.server.wm.ActivityRecord processGetActivityWithBoundary2 = processGetActivityWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(i));
            if (processGetActivityWithBoundary2 != null) {
                return processGetActivityWithBoundary2;
            }
        }
        return null;
    }

    int getDistanceFromTop(com.android.server.wm.WindowContainer windowContainer) {
        int indexOf = this.mChildren.indexOf(windowContainer);
        if (indexOf < 0) {
            return -1;
        }
        return (this.mChildren.size() - 1) - indexOf;
    }

    private com.android.server.wm.ActivityRecord processGetActivityWithBoundary(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, com.android.server.wm.WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer || windowContainer == null) {
            zArr[0] = true;
            if (!z) {
                return null;
            }
        }
        if (zArr[0]) {
            return windowContainer2.getActivity(predicate, z2);
        }
        return windowContainer2.getActivity(predicate, windowContainer, z, z2, zArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getActivityAbove$1(com.android.server.wm.ActivityRecord activityRecord) {
        return true;
    }

    com.android.server.wm.ActivityRecord getActivityAbove(com.android.server.wm.ActivityRecord activityRecord) {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getActivityAbove$1;
                lambda$getActivityAbove$1 = com.android.server.wm.WindowContainer.lambda$getActivityAbove$1((com.android.server.wm.ActivityRecord) obj);
                return lambda$getActivityAbove$1;
            }
        }, activityRecord, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getActivityBelow$2(com.android.server.wm.ActivityRecord activityRecord) {
        return true;
    }

    com.android.server.wm.ActivityRecord getActivityBelow(com.android.server.wm.ActivityRecord activityRecord) {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getActivityBelow$2;
                lambda$getActivityBelow$2 = com.android.server.wm.WindowContainer.lambda$getActivityBelow$2((com.android.server.wm.ActivityRecord) obj);
                return lambda$getActivityBelow$2;
            }
        }, activityRecord, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getBottomMostActivity$3(com.android.server.wm.ActivityRecord activityRecord) {
        return true;
    }

    com.android.server.wm.ActivityRecord getBottomMostActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getBottomMostActivity$3;
                lambda$getBottomMostActivity$3 = com.android.server.wm.WindowContainer.lambda$getBottomMostActivity$3((com.android.server.wm.ActivityRecord) obj);
                return lambda$getBottomMostActivity$3;
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopMostActivity$4(com.android.server.wm.ActivityRecord activityRecord) {
        return true;
    }

    com.android.server.wm.ActivityRecord getTopMostActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopMostActivity$4;
                lambda$getTopMostActivity$4 = com.android.server.wm.WindowContainer.lambda$getTopMostActivity$4((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopMostActivity$4;
            }
        }, true);
    }

    com.android.server.wm.ActivityRecord getTopActivity(boolean z, boolean z2) {
        if (z) {
            if (z2) {
                return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getTopActivity$5;
                        lambda$getTopActivity$5 = com.android.server.wm.WindowContainer.lambda$getTopActivity$5((com.android.server.wm.ActivityRecord) obj);
                        return lambda$getTopActivity$5;
                    }
                });
            }
            return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getTopActivity$6;
                    lambda$getTopActivity$6 = com.android.server.wm.WindowContainer.lambda$getTopActivity$6((com.android.server.wm.ActivityRecord) obj);
                    return lambda$getTopActivity$6;
                }
            });
        }
        if (z2) {
            return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getTopActivity$7;
                    lambda$getTopActivity$7 = com.android.server.wm.WindowContainer.lambda$getTopActivity$7((com.android.server.wm.ActivityRecord) obj);
                    return lambda$getTopActivity$7;
                }
            });
        }
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopActivity$8;
                lambda$getTopActivity$8 = com.android.server.wm.WindowContainer.lambda$getTopActivity$8((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopActivity$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopActivity$5(com.android.server.wm.ActivityRecord activityRecord) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopActivity$6(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.isTaskOverlay();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopActivity$7(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopActivity$8(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isTaskOverlay()) ? false : true;
    }

    void forAllWallpaperWindows(java.util.function.Consumer<com.android.server.wm.WallpaperWindowToken> consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).forAllWallpaperWindows(consumer);
        }
    }

    boolean forAllTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).forAllTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    boolean forAllLeafTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).forAllLeafTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    boolean forAllLeafTaskFragments(java.util.function.Predicate<com.android.server.wm.TaskFragment> predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).forAllLeafTaskFragments(predicate)) {
                return true;
            }
        }
        return false;
    }

    boolean forAllRootTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        return forAllRootTasks(predicate, true);
    }

    boolean forAllRootTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                if (this.mChildren.get(i).forAllRootTasks(predicate, z)) {
                    return true;
                }
            }
        } else {
            int i2 = 0;
            while (i2 < size) {
                if (this.mChildren.get(i2).forAllRootTasks(predicate, z)) {
                    return true;
                }
                int size2 = this.mChildren.size();
                i2 = (i2 - (size - size2)) + 1;
                size = size2;
            }
        }
        return false;
    }

    void forAllTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer) {
        forAllTasks(consumer, true);
    }

    void forAllTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                this.mChildren.get(i).forAllTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).forAllTasks(consumer, z);
        }
    }

    void forAllTaskFragments(java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer) {
        forAllTaskFragments(consumer, true);
    }

    void forAllTaskFragments(java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                this.mChildren.get(i).forAllTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).forAllTaskFragments(consumer, z);
        }
    }

    void forAllLeafTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                this.mChildren.get(i).forAllLeafTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).forAllLeafTasks(consumer, z);
        }
    }

    void forAllLeafTaskFragments(java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                this.mChildren.get(i).forAllLeafTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).forAllLeafTaskFragments(consumer, z);
        }
    }

    void forAllRootTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer) {
        forAllRootTasks(consumer, true);
    }

    void forAllRootTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                this.mChildren.get(i).forAllRootTasks(consumer, z);
            }
            return;
        }
        int i2 = 0;
        while (i2 < size) {
            this.mChildren.get(i2).forAllRootTasks(consumer, z);
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
    }

    com.android.server.wm.Task getTaskAbove(com.android.server.wm.Task task) {
        return getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTaskAbove$9;
                lambda$getTaskAbove$9 = com.android.server.wm.WindowContainer.lambda$getTaskAbove$9((com.android.server.wm.Task) obj);
                return lambda$getTaskAbove$9;
            }
        }, task, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTaskAbove$9(com.android.server.wm.Task task) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTaskBelow$10(com.android.server.wm.Task task) {
        return true;
    }

    com.android.server.wm.Task getTaskBelow(com.android.server.wm.Task task) {
        return getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTaskBelow$10;
                lambda$getTaskBelow$10 = com.android.server.wm.WindowContainer.lambda$getTaskBelow$10((com.android.server.wm.Task) obj);
                return lambda$getTaskBelow$10;
            }
        }, task, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getBottomMostTask$11(com.android.server.wm.Task task) {
        return true;
    }

    com.android.server.wm.Task getBottomMostTask() {
        return getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getBottomMostTask$11;
                lambda$getBottomMostTask$11 = com.android.server.wm.WindowContainer.lambda$getBottomMostTask$11((com.android.server.wm.Task) obj);
                return lambda$getBottomMostTask$11;
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopMostTask$12(com.android.server.wm.Task task) {
        return true;
    }

    com.android.server.wm.Task getTopMostTask() {
        return getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopMostTask$12;
                lambda$getTopMostTask$12 = com.android.server.wm.WindowContainer.lambda$getTopMostTask$12((com.android.server.wm.Task) obj);
                return lambda$getTopMostTask$12;
            }
        }, true);
    }

    com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        return getTask(predicate, true);
    }

    com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.Task task = this.mChildren.get(size).getTask(predicate, z);
                if (task != null) {
                    return task;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            com.android.server.wm.Task task2 = this.mChildren.get(i).getTask(predicate, z);
            if (task2 != null) {
                return task2;
            }
        }
        return null;
    }

    final com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2) {
        return getTask(predicate, windowContainer, z, z2, new boolean[1]);
    }

    private com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.Task processGetTaskWithBoundary = processGetTaskWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(size));
                if (processGetTaskWithBoundary != null) {
                    return processGetTaskWithBoundary;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            com.android.server.wm.Task processGetTaskWithBoundary2 = processGetTaskWithBoundary(predicate, windowContainer, z, z2, zArr, this.mChildren.get(i));
            if (processGetTaskWithBoundary2 != null) {
                return processGetTaskWithBoundary2;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        return getRootTask(predicate, true);
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                com.android.server.wm.Task rootTask = this.mChildren.get(i).getRootTask(predicate, z);
                if (rootTask != null) {
                    return rootTask;
                }
            }
            return null;
        }
        int i2 = 0;
        while (i2 < size) {
            com.android.server.wm.Task rootTask2 = this.mChildren.get(i2).getRootTask(predicate, z);
            if (rootTask2 != null) {
                return rootTask2;
            }
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
        return null;
    }

    private com.android.server.wm.Task processGetTaskWithBoundary(java.util.function.Predicate<com.android.server.wm.Task> predicate, com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, com.android.server.wm.WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer || windowContainer == null) {
            zArr[0] = true;
            if (!z) {
                return null;
            }
        }
        if (zArr[0]) {
            return windowContainer2.getTask(predicate, z2);
        }
        return windowContainer2.getTask(predicate, windowContainer, z, z2, zArr);
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getTaskFragment(java.util.function.Predicate<com.android.server.wm.TaskFragment> predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.TaskFragment taskFragment = this.mChildren.get(size).getTaskFragment(predicate);
            if (taskFragment != null) {
                return taskFragment;
            }
        }
        return null;
    }

    com.android.server.wm.WindowState getWindow(java.util.function.Predicate<com.android.server.wm.WindowState> predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState window = this.mChildren.get(size).getWindow(predicate);
            if (window != null) {
                return window;
            }
        }
        return null;
    }

    void forAllDisplayAreas(java.util.function.Consumer<com.android.server.wm.DisplayArea> consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).forAllDisplayAreas(consumer);
        }
    }

    boolean forAllTaskDisplayAreas(java.util.function.Predicate<com.android.server.wm.TaskDisplayArea> predicate, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            int i2 = 1;
            if (this.mChildren.get(i).forAllTaskDisplayAreas(predicate, z)) {
                return true;
            }
            if (z) {
                i2 = -1;
            }
            i += i2;
        }
        return false;
    }

    boolean forAllTaskDisplayAreas(java.util.function.Predicate<com.android.server.wm.TaskDisplayArea> predicate) {
        return forAllTaskDisplayAreas(predicate, true);
    }

    void forAllTaskDisplayAreas(java.util.function.Consumer<com.android.server.wm.TaskDisplayArea> consumer, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            this.mChildren.get(i).forAllTaskDisplayAreas(consumer, z);
            i += z ? -1 : 1;
        }
    }

    void forAllTaskDisplayAreas(java.util.function.Consumer<com.android.server.wm.TaskDisplayArea> consumer) {
        forAllTaskDisplayAreas(consumer, true);
    }

    @android.annotation.Nullable
    <R> R reduceOnAllTaskDisplayAreas(java.util.function.BiFunction<com.android.server.wm.TaskDisplayArea, R, R> biFunction, @android.annotation.Nullable R r, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            r = (R) this.mChildren.get(i).reduceOnAllTaskDisplayAreas(biFunction, r, z);
            i += z ? -1 : 1;
        }
        return r;
    }

    @android.annotation.Nullable
    <R> R reduceOnAllTaskDisplayAreas(java.util.function.BiFunction<com.android.server.wm.TaskDisplayArea, R, R> biFunction, @android.annotation.Nullable R r) {
        return (R) reduceOnAllTaskDisplayAreas(biFunction, r, true);
    }

    @android.annotation.Nullable
    <R> R getItemFromDisplayAreas(java.util.function.Function<com.android.server.wm.DisplayArea, R> function) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            R r = (R) this.mChildren.get(size).getItemFromDisplayAreas(function);
            if (r != null) {
                return r;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    <R> R getItemFromTaskDisplayAreas(java.util.function.Function<com.android.server.wm.TaskDisplayArea, R> function, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            R r = (R) this.mChildren.get(i).getItemFromTaskDisplayAreas(function, z);
            if (r != null) {
                return r;
            }
            i += z ? -1 : 1;
        }
        return null;
    }

    @android.annotation.Nullable
    <R> R getItemFromTaskDisplayAreas(java.util.function.Function<com.android.server.wm.TaskDisplayArea, R> function) {
        return (R) getItemFromTaskDisplayAreas(function, true);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
    
        if (r6 != r7) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        if (r6 != r8) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0074, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:
    
        r8 = r6.mChildren;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0087, code lost:
    
        if (r8.indexOf(r0.peekLast()) <= r8.indexOf(r3.peekLast())) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0089, code lost:
    
        r1 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0096, code lost:
    
        return r1;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int compareTo(com.android.server.wm.WindowContainer windowContainer) {
        if (this == windowContainer) {
            return 0;
        }
        int i = -1;
        if (this.mParent != null && this.mParent == windowContainer.mParent) {
            com.android.server.wm.WindowList<com.android.server.wm.WindowContainer> windowList = this.mParent.mChildren;
            return windowList.indexOf(this) > windowList.indexOf(windowContainer) ? 1 : -1;
        }
        java.util.LinkedList<com.android.server.wm.WindowContainer> linkedList = this.mTmpChain1;
        java.util.LinkedList<com.android.server.wm.WindowContainer> linkedList2 = this.mTmpChain2;
        try {
            getParents(linkedList);
            windowContainer.getParents(linkedList2);
            com.android.server.wm.WindowContainer peekLast = linkedList.peekLast();
            com.android.server.wm.WindowContainer windowContainer2 = null;
            for (com.android.server.wm.WindowContainer peekLast2 = linkedList2.peekLast(); peekLast != null && peekLast2 != null && peekLast == peekLast2; peekLast2 = linkedList2.peekLast()) {
                windowContainer2 = linkedList.removeLast();
                linkedList2.removeLast();
                peekLast = linkedList.peekLast();
            }
            throw new java.lang.IllegalArgumentException("No in the same hierarchy this=" + linkedList + " other=" + linkedList2);
        } finally {
            this.mTmpChain1.clear();
            this.mTmpChain2.clear();
        }
    }

    private void getParents(java.util.LinkedList<com.android.server.wm.WindowContainer> linkedList) {
        linkedList.clear();
        com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> windowContainer = this;
        do {
            linkedList.addLast(windowContainer);
            windowContainer = windowContainer.mParent;
        } while (windowContainer != null);
    }

    android.view.SurfaceControl.Builder makeSurface() {
        return getParent().makeChildSurface(this);
    }

    android.view.SurfaceControl.Builder makeChildSurface(com.android.server.wm.WindowContainer windowContainer) {
        return getParent().makeChildSurface(windowContainer).setParent(this.mSurfaceControl);
    }

    public android.view.SurfaceControl getParentSurfaceControl() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return null;
        }
        return parent.getSurfaceControl();
    }

    boolean shouldMagnify() {
        if (this.mSurfaceControl == null) {
            return false;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (!this.mChildren.get(i).shouldMagnify()) {
                return false;
            }
        }
        return true;
    }

    android.view.SurfaceSession getSession() {
        if (getParent() != null) {
            return getParent().getSession();
        }
        return null;
    }

    void assignLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mTransitionController.canAssignLayers(this)) {
            boolean z = (i == this.mLastLayer && this.mLastRelativeToLayer == null) ? false : true;
            if (this.mSurfaceControl != null && z) {
                setLayer(transaction, i);
                this.mLastLayer = i;
                this.mLastRelativeToLayer = null;
            }
        }
    }

    void assignRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i, boolean z) {
        boolean z2 = (i == this.mLastLayer && this.mLastRelativeToLayer == surfaceControl) ? false : true;
        if (this.mSurfaceControl != null) {
            if (z2 || z) {
                setRelativeLayer(transaction, surfaceControl, i);
                this.mLastLayer = i;
                this.mLastRelativeToLayer = surfaceControl;
            }
        }
    }

    void assignRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i) {
        assignRelativeLayer(transaction, surfaceControl, i, false);
    }

    protected void setLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.setLayer(transaction, i);
        } else {
            this.mSurfaceAnimator.setLayer(transaction, i);
        }
    }

    int getLastLayer() {
        return this.mLastLayer;
    }

    android.view.SurfaceControl getLastRelativeLayer() {
        return this.mLastRelativeToLayer;
    }

    protected void setRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.setRelativeLayer(transaction, surfaceControl, i);
        } else {
            this.mSurfaceAnimator.setRelativeLayer(transaction, surfaceControl, i);
        }
    }

    protected void reparentSurfaceControl(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (this.mSurfaceFreezer.hasLeash() || this.mSurfaceAnimator.hasLeash()) {
            return;
        }
        transaction.reparent(getSurfaceControl(), surfaceControl);
    }

    void assignChildLayers(android.view.SurfaceControl.Transaction transaction) {
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            E e = this.mChildren.get(i2);
            e.assignChildLayers(transaction);
            if (!e.needsZBoost()) {
                e.assignLayer(transaction, i);
                i++;
            }
        }
        for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
            E e2 = this.mChildren.get(i3);
            if (e2.needsZBoost()) {
                e2.assignLayer(transaction, i);
                i++;
            }
        }
        if (this.mOverlayHost != null) {
            this.mOverlayHost.setLayer(transaction, i);
        }
    }

    void assignChildLayers() {
        assignChildLayers(getSyncTransaction());
        scheduleAnimation();
    }

    boolean needsZBoost() {
        if (this.mNeedsZBoost) {
            return true;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (this.mChildren.get(i).needsZBoost()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i == 2 && !isVisible) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1120986464258L, this.mOverrideOrientation);
        protoOutputStream.write(1133871366147L, isVisible);
        writeIdentifierToProto(protoOutputStream, 1146756268038L);
        if (this.mSurfaceAnimator.isAnimating()) {
            this.mSurfaceAnimator.dumpDebug(protoOutputStream, 1146756268036L);
        }
        if (this.mSurfaceControl != null) {
            this.mSurfaceControl.dumpDebug(protoOutputStream, 1146756268039L);
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            long start2 = protoOutputStream.start(2246267895813L);
            E childAt = getChildAt(i2);
            childAt.dumpDebug(protoOutputStream, childAt.getProtoFieldId(), i);
            protoOutputStream.end(start2);
        }
        protoOutputStream.end(start);
    }

    long getProtoFieldId() {
        return 1146756268034L;
    }

    private com.android.server.wm.WindowContainer<E>.ForAllWindowsConsumerWrapper obtainConsumerWrapper(java.util.function.Consumer<com.android.server.wm.WindowState> consumer) {
        com.android.server.wm.WindowContainer<E>.ForAllWindowsConsumerWrapper forAllWindowsConsumerWrapper = (com.android.server.wm.WindowContainer.ForAllWindowsConsumerWrapper) this.mConsumerWrapperPool.acquire();
        if (forAllWindowsConsumerWrapper == null) {
            forAllWindowsConsumerWrapper = new com.android.server.wm.WindowContainer.ForAllWindowsConsumerWrapper();
        }
        forAllWindowsConsumerWrapper.setConsumer(consumer);
        return forAllWindowsConsumerWrapper;
    }

    private final class ForAllWindowsConsumerWrapper implements com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> {
        private java.util.function.Consumer<com.android.server.wm.WindowState> mConsumer;

        private ForAllWindowsConsumerWrapper() {
        }

        void setConsumer(java.util.function.Consumer<com.android.server.wm.WindowState> consumer) {
            this.mConsumer = consumer;
        }

        public boolean apply(com.android.server.wm.WindowState windowState) {
            this.mConsumer.accept(windowState);
            return false;
        }

        void release() {
            this.mConsumer = null;
            com.android.server.wm.WindowContainer.this.mConsumerWrapperPool.release(this);
        }
    }

    void applyMagnificationSpec(android.view.SurfaceControl.Transaction transaction, android.view.MagnificationSpec magnificationSpec) {
        if (shouldMagnify()) {
            transaction.setMatrix(this.mSurfaceControl, magnificationSpec.scale, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, magnificationSpec.scale).setPosition(this.mSurfaceControl, magnificationSpec.offsetX + this.mLastSurfacePosition.x, magnificationSpec.offsetY + this.mLastSurfacePosition.y);
            this.mLastMagnificationSpec = magnificationSpec;
            return;
        }
        clearMagnificationSpec(transaction);
        for (int i = 0; i < this.mChildren.size(); i++) {
            this.mChildren.get(i).applyMagnificationSpec(transaction, magnificationSpec);
        }
    }

    void clearMagnificationSpec(android.view.SurfaceControl.Transaction transaction) {
        if (this.mLastMagnificationSpec != null) {
            transaction.setMatrix(this.mSurfaceControl, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f).setPosition(this.mSurfaceControl, this.mLastSurfacePosition.x, this.mLastSurfacePosition.y);
        }
        this.mLastMagnificationSpec = null;
        for (int i = 0; i < this.mChildren.size(); i++) {
            this.mChildren.get(i).clearMagnificationSpec(transaction);
        }
    }

    void prepareSurfaces() {
        this.mCommittedReparentToAnimationLeash = this.mSurfaceAnimator.hasLeash();
        for (int i = 0; i < this.mChildren.size(); i++) {
            this.mChildren.get(i).prepareSurfaces();
        }
    }

    boolean hasCommittedReparentToAnimationLeash() {
        return this.mCommittedReparentToAnimationLeash;
    }

    void scheduleAnimation() {
        this.mWmService.scheduleAnimationLocked();
    }

    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public android.view.SurfaceControl.Transaction getSyncTransaction() {
        if (this.mSyncTransactionCommitCallbackDepth > 0) {
            return this.mSyncTransaction;
        }
        if (this.mSyncState != 0) {
            return this.mSyncTransaction;
        }
        return getPendingTransaction();
    }

    public android.view.SurfaceControl.Transaction getPendingTransaction() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null && displayContent != this) {
            return displayContent.getPendingTransaction();
        }
        return this.mPendingTransaction;
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, @android.annotation.Nullable com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, @android.annotation.Nullable java.lang.Runnable runnable, @android.annotation.Nullable com.android.server.wm.AnimationAdapter animationAdapter2) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 6949303417875346627L, 4, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(i), java.lang.String.valueOf(animationAdapter));
        this.mSurfaceAnimator.startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, runnable, animationAdapter2, this.mSurfaceFreezer);
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, @android.annotation.Nullable com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, null, null);
    }

    void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i) {
        startAnimation(transaction, animationAdapter, z, i, null);
    }

    void transferAnimation(com.android.server.wm.WindowContainer windowContainer) {
        this.mSurfaceAnimator.transferAnimation(windowContainer.mSurfaceAnimator);
    }

    void cancelAnimation() {
        doAnimationFinished(this.mSurfaceAnimator.getAnimationType(), this.mSurfaceAnimator.getAnimation());
        this.mSurfaceAnimator.cancelAnimation();
        this.mSurfaceFreezer.unfreeze(getSyncTransaction());
    }

    boolean canStartChangeTransition() {
        if (this.mWmService.mDisableTransitionAnimation || !okToAnimate() || this.mDisplayContent == null || getSurfaceControl() == null || !isVisible() || !isVisibleRequested() || this.mDisplayContent.inTransition()) {
            return false;
        }
        if (com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
            return true;
        }
        return (inPinnedWindowingMode() || getParent() == null || getParent().inPinnedWindowingMode()) ? false : true;
    }

    void initializeChangeTransition(android.graphics.Rect rect, @android.annotation.Nullable android.view.SurfaceControl surfaceControl) {
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            this.mDisplayContent.mTransitionController.collectVisibleChange(this);
            return;
        }
        this.mDisplayContent.prepareAppTransition(6);
        this.mDisplayContent.mChangingContainers.add(this);
        android.graphics.Rect bounds = getParent().getBounds();
        this.mTmpPoint.set(rect.left - bounds.left, rect.top - bounds.top);
        this.mSurfaceFreezer.freeze(getSyncTransaction(), rect, this.mTmpPoint, surfaceControl);
    }

    void initializeChangeTransition(android.graphics.Rect rect) {
        initializeChangeTransition(rect, null);
    }

    android.util.ArraySet<com.android.server.wm.WindowContainer> getAnimationSources() {
        return this.mSurfaceAnimationSources;
    }

    public android.view.SurfaceControl getFreezeSnapshotTarget() {
        if (!this.mDisplayContent.mAppTransition.containsTransitRequest(6) || !this.mDisplayContent.mChangingContainers.contains(this)) {
            return null;
        }
        return getSurfaceControl();
    }

    public void onUnfrozen() {
        if (this.mDisplayContent != null) {
            this.mDisplayContent.mChangingContainers.remove(this);
        }
    }

    public android.view.SurfaceControl.Builder makeAnimationLeash() {
        return makeSurface().setContainerLayer();
    }

    public android.view.SurfaceControl getAnimationLeashParent() {
        return getParentSurfaceControl();
    }

    android.graphics.Rect getAnimationBounds(int i) {
        return getBounds();
    }

    void getAnimationPosition(android.graphics.Point point) {
        getRelativePosition(point);
    }

    boolean applyAnimation(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, @android.annotation.Nullable java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        if (this.mWmService.mDisableTransitionAnimation) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8730310387200541562L, 0, null, java.lang.String.valueOf(this));
            cancelAnimation();
            return false;
        }
        try {
            android.os.Trace.traceBegin(32L, "WC#applyAnimation");
            if (okToAnimate()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 2363818604357955690L, 12, null, java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this));
                applyAnimationUnchecked(layoutParams, z, i, z2, arrayList);
            } else {
                cancelAnimation();
            }
            android.os.Trace.traceEnd(32L);
            return isAnimating();
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.util.Pair<com.android.server.wm.AnimationAdapter, com.android.server.wm.AnimationAdapter> getAnimationAdapter(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2) {
        float f;
        boolean z3;
        int i2;
        android.graphics.Rect rect;
        com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord createRemoteAnimationRecord;
        int appRootTaskClipMode = getDisplayContent().mAppTransition.getAppRootTaskClipMode();
        android.graphics.Rect animationBounds = getAnimationBounds(appRootTaskClipMode);
        this.mTmpRect.set(animationBounds);
        if (asTask() != null && com.android.server.wm.AppTransition.isTaskTransitOld(i)) {
            asTask().adjustAnimationBoundsForTransition(this.mTmpRect);
        }
        getAnimationPosition(this.mTmpPoint);
        this.mTmpRect.offsetTo(0, 0);
        com.android.server.wm.AppTransition appTransition = getDisplayContent().mAppTransition;
        com.android.server.wm.RemoteAnimationController remoteAnimationController = appTransition.getRemoteAnimationController();
        byte b = com.android.server.wm.AppTransition.isChangeTransitOld(i) && z && isChangingAppTransition();
        com.android.server.wm.LocalAnimationAdapter localAnimationAdapter = null;
        android.graphics.Rect rect2 = null;
        if (remoteAnimationController == null || this.mSurfaceAnimator.isAnimationStartDelayed()) {
            if (b == false) {
                this.mNeedsAnimationBoundsLayer = appRootTaskClipMode == 0;
                android.view.animation.Animation loadAnimation = loadAnimation(layoutParams, i, z, z2);
                if (loadAnimation != null) {
                    if (!inMultiWindowMode()) {
                        f = getDisplayContent().getWindowCornerRadius();
                    } else {
                        f = 0.0f;
                    }
                    if (asActivityRecord() != null && asActivityRecord().isNeedsLetterboxedAnimation()) {
                        asActivityRecord().getLetterboxInnerBounds(this.mTmpRect);
                    }
                    android.util.Pair<com.android.server.wm.AnimationAdapter, com.android.server.wm.AnimationAdapter> pair = new android.util.Pair<>(new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowAnimationSpec(loadAnimation, this.mTmpPoint, this.mTmpRect, getDisplayContent().mAppTransition.canSkipFirstFrame(), appRootTaskClipMode, true, f), getSurfaceAnimationRunner()), null);
                    this.mNeedsZBoost = loadAnimation.getZAdjustment() == 1 || com.android.server.wm.AppTransition.isClosingTransitOld(i);
                    this.mTransit = i;
                    this.mTransitFlags = getDisplayContent().mAppTransition.getTransitFlags();
                    return pair;
                }
                return new android.util.Pair<>(null, null);
            }
            float transitionAnimationScaleLocked = this.mWmService.getTransitionAnimationScaleLocked();
            android.view.DisplayInfo displayInfo = getDisplayContent().getDisplayInfo();
            this.mTmpRect.offsetTo(this.mTmpPoint.x, this.mTmpPoint.y);
            com.android.server.wm.LocalAnimationAdapter localAnimationAdapter2 = new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowChangeAnimationSpec(this.mSurfaceFreezer.mFreezeBounds, this.mTmpRect, displayInfo, transitionAnimationScaleLocked, true, false), getSurfaceAnimationRunner());
            if (this.mSurfaceFreezer.mSnapshot != null) {
                localAnimationAdapter = new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowChangeAnimationSpec(this.mSurfaceFreezer.mFreezeBounds, this.mTmpRect, displayInfo, transitionAnimationScaleLocked, true, true), getSurfaceAnimationRunner());
            }
            android.util.Pair<com.android.server.wm.AnimationAdapter, com.android.server.wm.AnimationAdapter> pair2 = new android.util.Pair<>(localAnimationAdapter2, localAnimationAdapter);
            this.mTransit = i;
            this.mTransitFlags = getDisplayContent().mAppTransition.getTransitFlags();
            return pair2;
        }
        if (remoteAnimationController.isFromActivityEmbedding()) {
            if (b != false) {
                z3 = getDisplayContent().mChangingContainers.size() > 1;
                i2 = appTransition.getNextAppTransitionBackgroundColor();
            } else {
                android.view.animation.Animation nextAppRequestedAnimation = appTransition.getNextAppRequestedAnimation(z);
                if (nextAppRequestedAnimation != null) {
                    boolean showBackdrop = nextAppRequestedAnimation.getShowBackdrop();
                    i2 = nextAppRequestedAnimation.getBackdropColor();
                    z3 = showBackdrop;
                }
            }
            rect = new android.graphics.Rect(this.mTmpRect);
            rect.offsetTo(this.mTmpPoint.x, this.mTmpPoint.y);
            if (b != false && !z && isClosingWhenResizing()) {
                createRemoteAnimationRecord = remoteAnimationController.createRemoteAnimationRecord(this, this.mTmpPoint, rect, animationBounds, getDisplayContent().mClosingChangingContainers.remove(this), z3, false);
            } else {
                if (b != false) {
                    rect2 = this.mSurfaceFreezer.mFreezeBounds;
                }
                createRemoteAnimationRecord = remoteAnimationController.createRemoteAnimationRecord(this, this.mTmpPoint, rect, animationBounds, rect2, z3);
            }
            if (i2 != 0) {
                createRemoteAnimationRecord.setBackDropColor(i2);
            }
            if (b == false) {
                createRemoteAnimationRecord.setMode(z ? 0 : 1);
            }
            return new android.util.Pair<>(createRemoteAnimationRecord.mAdapter, createRemoteAnimationRecord.mThumbnailAdapter);
        }
        z3 = false;
        i2 = 0;
        rect = new android.graphics.Rect(this.mTmpRect);
        rect.offsetTo(this.mTmpPoint.x, this.mTmpPoint.y);
        if (b != false) {
        }
        if (b != false) {
        }
        createRemoteAnimationRecord = remoteAnimationController.createRemoteAnimationRecord(this, this.mTmpPoint, rect, animationBounds, rect2, z3);
        if (i2 != 0) {
        }
        if (b == false) {
        }
        return new android.util.Pair<>(createRemoteAnimationRecord.mAdapter, createRemoteAnimationRecord.mThumbnailAdapter);
    }

    protected void applyAnimationUnchecked(android.view.WindowManager.LayoutParams layoutParams, boolean z, int i, boolean z2, @android.annotation.Nullable java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        com.android.server.wm.TaskFragment organizedTaskFragment;
        com.android.server.wm.Task task;
        int backgroundColor;
        com.android.server.wm.Task asTask = asTask();
        if (asTask != null && !z && !asTask.isActivityTypeHomeOrRecents()) {
            boolean z3 = false;
            com.android.server.wm.InsetsControlTarget imeTarget = this.mDisplayContent.getImeTarget(0);
            if (imeTarget != null && imeTarget.getWindow() != null && imeTarget.getWindow().getTask() == asTask) {
                z3 = true;
            }
            if (z3 && com.android.server.wm.AppTransition.isTaskCloseTransitOld(i)) {
                this.mDisplayContent.showImeScreenshot();
            }
        }
        android.util.Pair<com.android.server.wm.AnimationAdapter, com.android.server.wm.AnimationAdapter> animationAdapter = getAnimationAdapter(layoutParams, i, z, z2);
        com.android.server.wm.AnimationAdapter animationAdapter2 = (com.android.server.wm.AnimationAdapter) animationAdapter.first;
        com.android.server.wm.AnimationAdapter animationAdapter3 = (com.android.server.wm.AnimationAdapter) animationAdapter.second;
        if (animationAdapter2 != null) {
            if (arrayList != null) {
                this.mSurfaceAnimationSources.addAll(arrayList);
            }
            com.android.server.wm.WindowContainer.AnimationRunnerBuilder animationRunnerBuilder = new com.android.server.wm.WindowContainer.AnimationRunnerBuilder();
            if (com.android.server.wm.AppTransition.isTaskTransitOld(i) && getWindowingMode() == 1) {
                animationRunnerBuilder.setTaskBackgroundColor(getTaskAnimationBackgroundColor());
                if (this.mWmService.mTaskTransitionSpec != null) {
                    animationRunnerBuilder.hideInsetSourceViewOverflows();
                }
            }
            com.android.server.wm.ActivityRecord asActivityRecord = asActivityRecord();
            com.android.server.wm.TaskFragment asTaskFragment = asTaskFragment();
            if (animationAdapter2.getShowBackground() && ((asActivityRecord != null && com.android.server.wm.AppTransition.isActivityTransitOld(i)) || (asTaskFragment != null && asTaskFragment.isEmbedded() && com.android.server.wm.AppTransition.isTaskFragmentTransitOld(i)))) {
                if (animationAdapter2.getBackgroundColor() != 0) {
                    backgroundColor = animationAdapter2.getBackgroundColor();
                } else {
                    if (asActivityRecord != null) {
                        organizedTaskFragment = asActivityRecord.getOrganizedTaskFragment();
                    } else {
                        organizedTaskFragment = asTaskFragment.getOrganizedTaskFragment();
                    }
                    if (organizedTaskFragment != null && organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor() != 0) {
                        backgroundColor = organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor();
                    } else {
                        if (asActivityRecord != null) {
                            task = asActivityRecord.getTask();
                        } else {
                            task = asTaskFragment.getTask();
                        }
                        backgroundColor = task.getTaskDescription().getBackgroundColor();
                    }
                }
                animationRunnerBuilder.setTaskBackgroundColor(com.android.internal.graphics.ColorUtils.setAlphaComponent(backgroundColor, 255));
            }
            animationRunnerBuilder.build().startAnimation(getPendingTransaction(), animationAdapter2, !isVisible(), 1, animationAdapter3);
            if (animationAdapter2.getShowWallpaper()) {
                getDisplayContent().pendingLayoutChanges |= 4;
            }
        }
    }

    private int getTaskAnimationBackgroundColor() {
        android.content.Context systemUiContext = this.mDisplayContent.getDisplayPolicy().getSystemUiContext();
        android.view.TaskTransitionSpec taskTransitionSpec = this.mWmService.mTaskTransitionSpec;
        int color = systemUiContext.getColor(android.R.color.navigation_bar_compatible);
        if (taskTransitionSpec != null && taskTransitionSpec.backgroundColor != 0) {
            return taskTransitionSpec.backgroundColor;
        }
        return color;
    }

    final com.android.server.wm.SurfaceAnimationRunner getSurfaceAnimationRunner() {
        return this.mWmService.mSurfaceAnimationRunner;
    }

    private android.view.animation.Animation loadAnimation(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2) {
        boolean z3;
        if (!com.android.server.wm.AppTransitionController.isTaskViewTask(this)) {
            if (isOrganized() && getWindowingMode() != 1 && getWindowingMode() != 5 && getWindowingMode() != 6) {
                return null;
            }
            com.android.server.wm.DisplayContent displayContent = getDisplayContent();
            android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
            int i2 = displayInfo.appWidth;
            int i3 = displayInfo.appHeight;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 2262119454684034794L, 0, null, java.lang.String.valueOf(this));
            android.graphics.Rect rect = new android.graphics.Rect(0, 0, i2, i3);
            android.graphics.Rect rect2 = new android.graphics.Rect(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
            android.graphics.Rect rect3 = new android.graphics.Rect();
            android.graphics.Rect rect4 = new android.graphics.Rect();
            android.graphics.Rect rect5 = new android.graphics.Rect();
            getAnimationFrames(rect, rect3, rect4, rect5);
            if (!this.mLaunchTaskBehind) {
                z3 = z;
            } else {
                z3 = false;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5857165752965610762L, 12, null, java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(i)), java.lang.Boolean.valueOf(z3), java.lang.String.valueOf(rect), java.lang.String.valueOf(rect3), java.lang.String.valueOf(rect5));
            android.content.res.Configuration configuration = displayContent.getConfiguration();
            android.view.animation.Animation loadAnimation = getDisplayContent().mAppTransition.loadAnimation(layoutParams, i, z3, configuration.uiMode, configuration.orientation, rect, rect2, rect3, rect5, rect4, z2, inFreeformWindowingMode(), this);
            if (loadAnimation != null) {
                loadAnimation.restrictDuration(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
                if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 9017113545720281233L, 16, null, java.lang.String.valueOf(loadAnimation), java.lang.String.valueOf(this), java.lang.Long.valueOf(loadAnimation.getDuration()), java.lang.String.valueOf(android.os.Debug.getCallers(20)));
                }
                loadAnimation.initialize(rect.width(), rect.height(), i2, i3);
                loadAnimation.scaleCurrentDuration(this.mWmService.getTransitionAnimationScaleLocked());
            }
            return loadAnimation;
        }
        return null;
    }

    android.view.RemoteAnimationTarget createRemoteAnimationTarget(com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        return null;
    }

    boolean canCreateRemoteAnimationTarget() {
        return false;
    }

    boolean okToDisplay() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToDisplay();
    }

    boolean okToAnimate() {
        return okToAnimate(false, false);
    }

    boolean okToAnimate(boolean z, boolean z2) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToAnimate(z, z2);
    }

    public void commitPendingTransaction() {
        scheduleAnimation();
    }

    void transformFrameToSurfacePosition(int i, int i2, android.graphics.Point point) {
        point.set(i, i2);
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return;
        }
        android.graphics.Rect bounds = parent.getBounds();
        point.offset(-bounds.left, -bounds.top);
    }

    void reassignLayer(android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            parent.assignChildLayers(transaction);
        }
    }

    void resetSurfacePositionForAnimationLeash(android.view.SurfaceControl.Transaction transaction) {
        transaction.setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        android.view.SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (transaction != syncTransaction) {
            syncTransaction.setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        }
        this.mLastSurfacePosition.set(0, 0);
    }

    public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        this.mLastLayer = -1;
        this.mAnimationLeash = surfaceControl;
        reassignLayer(transaction);
        resetSurfacePositionForAnimationLeash(transaction);
    }

    public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        this.mLastLayer = -1;
        this.mWmService.mSurfaceAnimationRunner.onAnimationLeashLost(this.mAnimationLeash, transaction);
        this.mAnimationLeash = null;
        this.mNeedsZBoost = false;
        reassignLayer(transaction);
        updateSurfacePosition(transaction);
    }

    public android.view.SurfaceControl getAnimationLeash() {
        return this.mAnimationLeash;
    }

    private void doAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        for (int i2 = 0; i2 < this.mSurfaceAnimationSources.size(); i2++) {
            this.mSurfaceAnimationSources.valueAt(i2).onAnimationFinished(i, animationAdapter);
        }
        this.mSurfaceAnimationSources.clear();
        if (this.mDisplayContent != null) {
            this.mDisplayContent.onWindowAnimationFinished(this, i);
        }
    }

    protected void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        doAnimationFinished(i, animationAdapter);
        this.mWmService.onAnimationFinished();
        this.mNeedsZBoost = false;
    }

    com.android.server.wm.AnimationAdapter getAnimation() {
        return this.mSurfaceAnimator.getAnimation();
    }

    @android.annotation.Nullable
    com.android.server.wm.WindowContainer getAnimatingContainer(int i, int i2) {
        if (isSelfAnimating(i, i2)) {
            return this;
        }
        if ((i & 2) != 0) {
            for (com.android.server.wm.WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
                if (parent.isSelfAnimating(i, i2)) {
                    return parent;
                }
            }
        }
        if ((i & 4) != 0) {
            for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
                com.android.server.wm.WindowContainer animatingContainer = this.mChildren.get(i3).getAnimatingContainer(i & (-3), i2);
                if (animatingContainer != null) {
                    return animatingContainer;
                }
            }
            return null;
        }
        return null;
    }

    protected boolean isSelfAnimating(int i, int i2) {
        if (!this.mSurfaceAnimator.isAnimating() || (i2 & this.mSurfaceAnimator.getAnimationType()) <= 0) {
            return (i & 1) != 0 && isWaitingForTransitionStart();
        }
        return true;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    final com.android.server.wm.WindowContainer getAnimatingContainer() {
        return getAnimatingContainer(2, -1);
    }

    void startDelayingAnimationStart() {
        this.mSurfaceAnimator.startDelayingAnimationStart();
    }

    void endDelayingAnimationStart() {
        this.mSurfaceAnimator.endDelayingAnimationStart();
    }

    public int getSurfaceWidth() {
        return this.mSurfaceControl.getWidth();
    }

    public int getSurfaceHeight() {
        return this.mSurfaceControl.getHeight();
    }

    static void enforceSurfaceVisible(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        if (windowContainer.mSurfaceControl == null) {
            return;
        }
        windowContainer.getSyncTransaction().show(windowContainer.mSurfaceControl);
        com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord != null) {
            asActivityRecord.mLastSurfaceShowing = true;
        }
        for (com.android.server.wm.WindowContainer parent = windowContainer.getParent(); parent != null && parent != windowContainer.mDisplayContent; parent = parent.getParent()) {
            if (parent.mSurfaceControl != null) {
                parent.getSyncTransaction().show(parent.mSurfaceControl);
                com.android.server.wm.Task asTask = parent.asTask();
                if (asTask != null) {
                    asTask.mLastSurfaceShowing = true;
                }
            }
        }
        windowContainer.scheduleAnimation();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        if (this.mSurfaceAnimator.isAnimating()) {
            printWriter.print(str);
            printWriter.println("ContainerAnimator:");
            this.mSurfaceAnimator.dump(printWriter, str + "  ");
        }
        if (this.mLastOrientationSource != null && this == this.mDisplayContent) {
            printWriter.println(str + "mLastOrientationSource=" + this.mLastOrientationSource);
            printWriter.println(str + "deepestLastOrientationSource=" + getLastOrientationSource());
        }
        if (this.mLocalInsetsSources != null && this.mLocalInsetsSources.size() != 0) {
            printWriter.println(str + this.mLocalInsetsSources.size() + " LocalInsetsSources");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("  ");
            java.lang.String sb2 = sb.toString();
            for (int i = 0; i < this.mLocalInsetsSources.size(); i++) {
                this.mLocalInsetsSources.valueAt(i).dump(sb2, printWriter);
            }
        }
    }

    final void updateSurfacePositionNonOrganized() {
        if (isOrganized()) {
            return;
        }
        updateSurfacePosition(getSyncTransaction());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PROTECTED)
    void updateSurfacePosition(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null || this.mSurfaceAnimator.hasLeash() || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        if (isClosingWhenResizing()) {
            getRelativePosition(this.mDisplayContent.mClosingChangingContainers.get(this), this.mTmpPos);
        } else {
            getRelativePosition(this.mTmpPos);
        }
        int relativeDisplayRotation = getRelativeDisplayRotation();
        if (this.mTmpPos.equals(this.mLastSurfacePosition) && relativeDisplayRotation == this.mLastDeltaRotation) {
            return;
        }
        transaction.setPosition(this.mSurfaceControl, this.mTmpPos.x, this.mTmpPos.y);
        this.mLastSurfacePosition.set(this.mTmpPos.x, this.mTmpPos.y);
        if (this.mTransitionController.isShellTransitionsEnabled() && !this.mTransitionController.useShellTransitionsRotation()) {
            if (relativeDisplayRotation != 0) {
                updateSurfaceRotation(transaction, relativeDisplayRotation, null);
                getPendingTransaction().setFixedTransformHint(this.mSurfaceControl, getWindowConfiguration().getDisplayRotation());
            } else if (relativeDisplayRotation != this.mLastDeltaRotation) {
                transaction.setMatrix(this.mSurfaceControl, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
                getPendingTransaction().unsetFixedTransformHint(this.mSurfaceControl);
            }
        }
        this.mLastDeltaRotation = relativeDisplayRotation;
    }

    protected void updateSurfaceRotation(android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.Nullable android.view.SurfaceControl surfaceControl) {
        android.util.RotationUtils.rotateSurface(transaction, this.mSurfaceControl, i);
        this.mTmpPos.set(this.mLastSurfacePosition.x, this.mLastSurfacePosition.y);
        android.graphics.Rect bounds = getParent().getBounds();
        boolean z = i % 2 != 0;
        android.util.RotationUtils.rotatePoint(this.mTmpPos, i, z ? bounds.height() : bounds.width(), z ? bounds.width() : bounds.height());
        if (surfaceControl == null) {
            surfaceControl = this.mSurfaceControl;
        }
        transaction.setPosition(surfaceControl, this.mTmpPos.x, this.mTmpPos.y);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Point getLastSurfacePosition() {
        return this.mLastSurfacePosition;
    }

    void getAnimationFrames(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        android.view.DisplayInfo displayInfo = getDisplayContent().getDisplayInfo();
        rect.set(0, 0, displayInfo.appWidth, displayInfo.appHeight);
        rect2.setEmpty();
        rect3.setEmpty();
        rect4.setEmpty();
    }

    void getRelativePosition(android.graphics.Point point) {
        getRelativePosition(getBounds(), point);
    }

    void getRelativePosition(android.graphics.Rect rect, android.graphics.Point point) {
        point.set(rect.left, rect.top);
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            android.graphics.Rect bounds = parent.getBounds();
            point.offset(-bounds.left, -bounds.top);
        }
    }

    int getRelativeDisplayRotation() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return 0;
        }
        return android.util.RotationUtils.deltaRotation(getWindowConfiguration().getDisplayRotation(), parent.getWindowConfiguration().getDisplayRotation());
    }

    void waitForAllWindowsDrawn() {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowContainer.this.lambda$waitForAllWindowsDrawn$13((com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitForAllWindowsDrawn$13(com.android.server.wm.WindowState windowState) {
        windowState.requestDrawIfNeeded(this.mWaitingForDrawn);
    }

    com.android.server.wm.Dimmer getDimmer() {
        if (this.mParent == null) {
            return null;
        }
        return this.mParent.getDimmer();
    }

    void setSurfaceControl(android.view.SurfaceControl surfaceControl) {
        this.mSurfaceControl = surfaceControl;
    }

    android.view.RemoteAnimationDefinition getRemoteAnimationDefinition() {
        return null;
    }

    com.android.server.wm.Task asTask() {
        return null;
    }

    com.android.server.wm.TaskFragment asTaskFragment() {
        return null;
    }

    com.android.server.wm.WindowToken asWindowToken() {
        return null;
    }

    com.android.server.wm.WindowState asWindowState() {
        return null;
    }

    com.android.server.wm.ActivityRecord asActivityRecord() {
        return null;
    }

    com.android.server.wm.WallpaperWindowToken asWallpaperToken() {
        return null;
    }

    com.android.server.wm.DisplayArea asDisplayArea() {
        return null;
    }

    com.android.server.wm.RootDisplayArea asRootDisplayArea() {
        return null;
    }

    com.android.server.wm.TaskDisplayArea asTaskDisplayArea() {
        return null;
    }

    com.android.server.wm.DisplayContent asDisplayContent() {
        return null;
    }

    boolean isOrganized() {
        return false;
    }

    boolean isEmbedded() {
        return false;
    }

    boolean showSurfaceOnCreation() {
        return true;
    }

    boolean showWallpaper() {
        if (!isVisibleRequested() || inMultiWindowMode()) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (this.mChildren.get(size).showWallpaper()) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.Nullable
    static com.android.server.wm.WindowContainer fromBinder(android.os.IBinder iBinder) {
        return com.android.server.wm.WindowContainer.RemoteToken.fromBinder(iBinder).getContainer();
    }

    static class RemoteToken extends android.window.IWindowContainerToken.Stub {
        final java.lang.ref.WeakReference<com.android.server.wm.WindowContainer> mWeakRef;
        private android.window.WindowContainerToken mWindowContainerToken;

        RemoteToken(com.android.server.wm.WindowContainer windowContainer) {
            this.mWeakRef = new java.lang.ref.WeakReference<>(windowContainer);
        }

        @android.annotation.Nullable
        com.android.server.wm.WindowContainer getContainer() {
            return this.mWeakRef.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        static com.android.server.wm.WindowContainer.RemoteToken fromBinder(android.os.IBinder iBinder) {
            return (com.android.server.wm.WindowContainer.RemoteToken) iBinder;
        }

        android.window.WindowContainerToken toWindowContainerToken() {
            if (this.mWindowContainerToken == null) {
                this.mWindowContainerToken = new android.window.WindowContainerToken(this);
            }
            return this.mWindowContainerToken;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("RemoteToken{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            sb.append(this.mWeakRef.get());
            sb.append('}');
            return sb.toString();
        }
    }

    boolean onSyncFinishedDrawing() {
        if (this.mSyncState == 0) {
            return false;
        }
        this.mSyncState = 2;
        this.mSyncMethodOverride = -1;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 5272307326252759722L, 0, null, java.lang.String.valueOf(this));
        return true;
    }

    void setSyncGroup(@android.annotation.NonNull com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -8311909671193661340L, 1, null, java.lang.Long.valueOf(syncGroup.mSyncId), java.lang.String.valueOf(this));
        if (this.mSyncGroup != null && this.mSyncGroup != syncGroup) {
            throw new java.lang.IllegalStateException("Can't sync on 2 groups simultaneously currentSyncId=" + this.mSyncGroup.mSyncId + " newSyncId=" + syncGroup.mSyncId + " wc=" + this);
        }
        this.mSyncGroup = syncGroup;
    }

    @android.annotation.Nullable
    com.android.server.wm.BLASTSyncEngine.SyncGroup getSyncGroup() {
        if (this.mSyncGroup != null) {
            return this.mSyncGroup;
        }
        for (com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> windowContainer = this.mParent; windowContainer != null; windowContainer = windowContainer.mParent) {
            if (windowContainer.mSyncGroup != null) {
                return windowContainer.mSyncGroup;
            }
        }
        return null;
    }

    boolean prepareSync() {
        if (this.mSyncState != 0) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).prepareSync();
        }
        this.mSyncState = 2;
        return true;
    }

    boolean syncNextBuffer() {
        return this.mSyncState != 0;
    }

    void finishSync(android.view.SurfaceControl.Transaction transaction, @android.annotation.Nullable com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        if (this.mSyncState == 0) {
            return;
        }
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup2 = getSyncGroup();
        if (syncGroup2 == null || syncGroup == syncGroup2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -3871009616397322067L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this));
            transaction.merge(this.mSyncTransaction);
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                this.mChildren.get(size).finishSync(transaction, syncGroup, z);
            }
            if (z && this.mSyncGroup != null) {
                this.mSyncGroup.onCancelSync(this);
            }
            this.mSyncState = 0;
            this.mSyncMethodOverride = -1;
            this.mSyncGroup = null;
        }
    }

    boolean isSyncFinished(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState == 0 && getSyncGroup() != null) {
            android.util.Slog.i(TAG, "prepareSync in isSyncFinished: " + this);
            prepareSync();
        }
        if (this.mSyncState == 1) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            E e = this.mChildren.get(size);
            boolean z = syncGroup.isIgnoring(e) || e.isSyncFinished(syncGroup);
            if (z && e.isVisibleRequested() && e.fillsParent()) {
                return true;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    boolean allSyncFinished() {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState != 2) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (!this.mChildren.get(size).allSyncFinished()) {
                return false;
            }
        }
        return true;
    }

    private void onSyncReparent(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.WindowContainer windowContainer2) {
        if (this.mSyncState != 0 && windowContainer != null && windowContainer2 != null && windowContainer.getDisplayContent() != null && windowContainer2.getDisplayContent() != null && windowContainer.getDisplayContent() != windowContainer2.getDisplayContent()) {
            this.mTransitionController.setReady(windowContainer.getDisplayContent());
        }
        if (windowContainer2 == null || windowContainer2.mSyncState == 0) {
            if (this.mSyncState == 0) {
                return;
            }
            if (windowContainer2 == null) {
                com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
                if (windowContainer.mSyncState != 0) {
                    finishSync(windowContainer.mSyncTransaction, syncGroup, true);
                    return;
                }
                if (syncGroup != null) {
                    finishSync(syncGroup.getOrphanTransaction(), syncGroup, true);
                    return;
                }
                android.util.Slog.wtf(TAG, this + " is in sync mode without a sync group");
                finishSync(getPendingTransaction(), null, true);
                return;
            }
            if (this.mSyncGroup == null) {
                finishSync(getPendingTransaction(), getSyncGroup(), true);
                return;
            }
        }
        if (windowContainer != null && windowContainer2 != null && !shouldUpdateSyncOnReparent()) {
            return;
        }
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            this.mSyncState = 0;
            this.mSyncMethodOverride = -1;
        }
        prepareSync();
    }

    protected boolean shouldUpdateSyncOnReparent() {
        return true;
    }

    void registerWindowContainerListener(com.android.server.wm.WindowContainerListener windowContainerListener) {
        registerWindowContainerListener(windowContainerListener, true);
    }

    void registerWindowContainerListener(com.android.server.wm.WindowContainerListener windowContainerListener, boolean z) {
        if (this.mListeners.contains(windowContainerListener)) {
            return;
        }
        this.mListeners.add(windowContainerListener);
        registerConfigurationChangeListener(windowContainerListener, z);
        if (z) {
            windowContainerListener.onDisplayChanged(getDisplayContent());
        }
    }

    void unregisterWindowContainerListener(com.android.server.wm.WindowContainerListener windowContainerListener) {
        this.mListeners.remove(windowContainerListener);
        unregisterConfigurationChangeListener(windowContainerListener);
    }

    static void overrideConfigurationPropagation(com.android.server.wm.WindowContainer<?> windowContainer, com.android.server.wm.WindowContainer<?> windowContainer2) {
        overrideConfigurationPropagation(windowContainer, windowContainer2, null);
    }

    static com.android.server.wm.WindowContainerListener overrideConfigurationPropagation(final com.android.server.wm.WindowContainer<?> windowContainer, final com.android.server.wm.WindowContainer<?> windowContainer2, @android.annotation.Nullable final com.android.server.wm.WindowContainer.ConfigurationMerger configurationMerger) {
        final com.android.server.wm.ConfigurationContainerListener configurationContainerListener = new com.android.server.wm.ConfigurationContainerListener() { // from class: com.android.server.wm.WindowContainer.1
            @Override // com.android.server.wm.ConfigurationContainerListener
            public void onMergedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
                android.content.res.Configuration configuration2;
                if (com.android.server.wm.WindowContainer.ConfigurationMerger.this != null) {
                    configuration2 = com.android.server.wm.WindowContainer.ConfigurationMerger.this.merge(configuration, windowContainer.getRequestedOverrideConfiguration());
                } else {
                    configuration2 = windowContainer2.getConfiguration();
                }
                windowContainer.onRequestedOverrideConfigurationChanged(configuration2);
            }
        };
        windowContainer2.registerConfigurationChangeListener(configurationContainerListener);
        com.android.server.wm.WindowContainerListener windowContainerListener = new com.android.server.wm.WindowContainerListener() { // from class: com.android.server.wm.WindowContainer.2
            @Override // com.android.server.wm.WindowContainerListener
            public void onRemoved() {
                com.android.server.wm.WindowContainer.this.unregisterWindowContainerListener(this);
                windowContainer2.unregisterConfigurationChangeListener(configurationContainerListener);
            }
        };
        windowContainer.registerWindowContainerListener(windowContainerListener);
        return windowContainerListener;
    }

    int getWindowType() {
        return -1;
    }

    boolean setCanScreenshot(android.view.SurfaceControl.Transaction transaction, boolean z) {
        if (this.mSurfaceControl == null) {
            return false;
        }
        transaction.setSecure(this.mSurfaceControl, !z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AnimationRunnerBuilder {
        private final java.util.List<java.lang.Runnable> mOnAnimationCancelled;
        private final java.util.List<java.lang.Runnable> mOnAnimationFinished;

        private AnimationRunnerBuilder() {
            this.mOnAnimationFinished = new java.util.LinkedList();
            this.mOnAnimationCancelled = new java.util.LinkedList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTaskBackgroundColor(int i) {
            final com.android.server.wm.TaskDisplayArea taskDisplayArea = com.android.server.wm.WindowContainer.this.getTaskDisplayArea();
            if (taskDisplayArea != null && i != 0) {
                taskDisplayArea.setBackgroundColor(i);
                final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.WindowContainer.AnimationRunnerBuilder.lambda$setTaskBackgroundColor$0(atomicInteger, taskDisplayArea);
                    }
                };
                this.mOnAnimationFinished.add(runnable);
                this.mOnAnimationCancelled.add(runnable);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setTaskBackgroundColor$0(java.util.concurrent.atomic.AtomicInteger atomicInteger, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
            if (atomicInteger.getAndIncrement() == 0) {
                taskDisplayArea.clearBackgroundColor();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void hideInsetSourceViewOverflows() {
            android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> sourceProviders = com.android.server.wm.WindowContainer.this.getDisplayContent().getInsetsStateController().getSourceProviders();
            for (int size = sourceProviders.size(); size >= 0; size--) {
                final com.android.server.wm.InsetsSourceProvider valueAt = sourceProviders.valueAt(size);
                if (!valueAt.getSource().hasFlags(2)) {
                    return;
                }
                valueAt.setCropToProvidingInsetsBounds(com.android.server.wm.WindowContainer.this.getPendingTransaction());
                this.mOnAnimationFinished.add(new java.lang.Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.WindowContainer.AnimationRunnerBuilder.this.lambda$hideInsetSourceViewOverflows$1(valueAt);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hideInsetSourceViewOverflows$1(com.android.server.wm.InsetsSourceProvider insetsSourceProvider) {
            insetsSourceProvider.removeCropToProvidingInsetsBounds(com.android.server.wm.WindowContainer.this.getPendingTransaction());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.WindowContainer.IAnimationStarter build() {
            return new com.android.server.wm.WindowContainer.IAnimationStarter() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda2
                @Override // com.android.server.wm.WindowContainer.IAnimationStarter
                public final void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, com.android.server.wm.AnimationAdapter animationAdapter2) {
                    com.android.server.wm.WindowContainer.AnimationRunnerBuilder.this.lambda$build$4(transaction, animationAdapter, z, i, animationAdapter2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$4(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i, com.android.server.wm.AnimationAdapter animationAdapter2) {
            com.android.server.wm.WindowContainer.this.startAnimation(com.android.server.wm.WindowContainer.this.getPendingTransaction(), animationAdapter, !com.android.server.wm.WindowContainer.this.isVisible(), i, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i2, com.android.server.wm.AnimationAdapter animationAdapter3) {
                    com.android.server.wm.WindowContainer.AnimationRunnerBuilder.this.lambda$build$2(i2, animationAdapter3);
                }
            }, new java.lang.Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowContainer.AnimationRunnerBuilder.this.lambda$build$3();
                }
            }, animationAdapter2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$2(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
            this.mOnAnimationFinished.forEach(new com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda3());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$3() {
            this.mOnAnimationCancelled.forEach(new com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda3());
        }
    }

    void addTrustedOverlay(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, @android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        if (this.mOverlayHost == null) {
            this.mOverlayHost = new com.android.server.wm.TrustedOverlayHost(this.mWmService);
        }
        this.mOverlayHost.addOverlay(surfacePackage, this.mSurfaceControl);
        try {
            surfacePackage.getRemoteInterface().onConfigurationChanged(getConfiguration());
        } catch (java.lang.Exception e) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -4267530270533009730L, 0, null, null);
            removeTrustedOverlay(surfacePackage);
        }
        if (windowState != null) {
            try {
                surfacePackage.getRemoteInterface().onInsetsChanged(windowState.getInsetsState(), getBounds());
            } catch (java.lang.Exception e2) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 5179630990780610966L, 0, null, null);
                removeTrustedOverlay(surfacePackage);
            }
        }
    }

    void removeTrustedOverlay(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
        if (this.mOverlayHost != null && !this.mOverlayHost.removeOverlay(surfacePackage)) {
            this.mOverlayHost.release();
            this.mOverlayHost = null;
        }
    }

    void updateOverlayInsetsState(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(windowState);
        }
    }

    void waitForSyncTransactionCommit(android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet) {
        if (arraySet.contains(this)) {
            return;
        }
        this.mSyncTransactionCommitCallbackDepth++;
        arraySet.add(this);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            this.mChildren.get(size).waitForSyncTransactionCommit(arraySet);
        }
    }

    void onSyncTransactionCommitted(android.view.SurfaceControl.Transaction transaction) {
        this.mSyncTransactionCommitCallbackDepth--;
        if (this.mSyncTransactionCommitCallbackDepth > 0 || this.mSyncState != 0) {
            return;
        }
        transaction.merge(this.mSyncTransaction);
    }
}
