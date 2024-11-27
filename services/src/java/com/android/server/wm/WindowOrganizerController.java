package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowOrganizerController extends android.window.IWindowOrganizerController.Stub implements com.android.server.wm.BLASTSyncEngine.TransactionReadyListener {
    static final int CONTROLLABLE_CONFIGS = 536886272;
    static final int CONTROLLABLE_WINDOW_CONFIGS = 3;
    private static final java.lang.String TAG = "WindowOrganizerController";
    private static final int TRANSACT_EFFECTS_CLIENT_CONFIG = 1;
    private static final int TRANSACT_EFFECTS_LIFECYCLE = 2;
    private static final int TRANSACT_EFFECTS_NONE = 0;
    final com.android.server.wm.DisplayAreaOrganizerController mDisplayAreaOrganizerController;
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    final com.android.server.wm.TaskFragmentOrganizerController mTaskFragmentOrganizerController;
    final com.android.server.wm.TaskOrganizerController mTaskOrganizerController;
    final com.android.server.wm.TransitionController mTransitionController;
    private final java.util.HashMap<java.lang.Integer, android.window.IWindowContainerTransactionCallback> mTransactionCallbacksByPendingSyncId = new java.util.HashMap<>();

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.TaskFragment> mLaunchTaskFragments = new android.util.ArrayMap<>();
    private final android.graphics.Rect mTmpBounds0 = new android.graphics.Rect();
    private final android.graphics.Rect mTmpBounds1 = new android.graphics.Rect();

    WindowOrganizerController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mTaskOrganizerController = new com.android.server.wm.TaskOrganizerController(this.mService);
        this.mDisplayAreaOrganizerController = new com.android.server.wm.DisplayAreaOrganizerController(this.mService);
        this.mTaskFragmentOrganizerController = new com.android.server.wm.TaskFragmentOrganizerController(activityTaskManagerService, this);
        this.mTransitionController = new com.android.server.wm.TransitionController(activityTaskManagerService);
    }

    com.android.server.wm.TransitionController getTransitionController() {
        return this.mTransitionController;
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            throw com.android.server.wm.ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(TAG, e);
        }
    }

    public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction == null) {
            throw new java.lang.IllegalArgumentException("Null transaction passed to applyTransaction");
        }
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("applyTransaction()");
        com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int applySyncTransaction(final android.window.WindowContainerTransaction windowContainerTransaction, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        if (windowContainerTransaction == null) {
            throw new java.lang.IllegalArgumentException("Null transaction passed to applySyncTransaction");
        }
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("applySyncTransaction()");
        final com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (iWindowContainerTransactionCallback == null) {
                        applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    final com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer = prepareSyncWithOrganizer(iWindowContainerTransactionCallback);
                    final int i = prepareSyncWithOrganizer.mSyncId;
                    if (this.mTransitionController.isShellTransitionsEnabled()) {
                        this.mTransitionController.startLegacySyncOrQueue(prepareSyncWithOrganizer, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda7
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.wm.WindowOrganizerController.this.lambda$applySyncTransaction$0(windowContainerTransaction, i, callerInfo, (java.lang.Boolean) obj);
                            }
                        });
                    } else if (!this.mService.mWindowManager.mSyncEngine.hasActiveSync()) {
                        this.mService.mWindowManager.mSyncEngine.startSyncSet(prepareSyncWithOrganizer);
                        applyTransaction(windowContainerTransaction, i, null, callerInfo);
                        setSyncReady(i);
                    } else {
                        this.mService.mWindowManager.mSyncEngine.queueSyncSet(new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.WindowOrganizerController.this.lambda$applySyncTransaction$1(prepareSyncWithOrganizer);
                            }
                        }, new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.WindowOrganizerController.this.lambda$applySyncTransaction$2(windowContainerTransaction, i, callerInfo);
                            }
                        });
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$0(android.window.WindowContainerTransaction windowContainerTransaction, int i, com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, java.lang.Boolean bool) {
        applyTransaction(windowContainerTransaction, i, (com.android.server.wm.Transition) null, callerInfo, bool.booleanValue());
        setSyncReady(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$1(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        this.mService.mWindowManager.mSyncEngine.startSyncSet(syncGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$2(android.window.WindowContainerTransaction windowContainerTransaction, int i, com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo) {
        applyTransaction(windowContainerTransaction, i, null, callerInfo);
        setSyncReady(i);
    }

    public android.os.IBinder startNewTransition(int i, @android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        return startTransition(i, null, windowContainerTransaction);
    }

    public void startTransition(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        startTransition(-1, iBinder, windowContainerTransaction);
    }

    private android.os.IBinder startTransition(int i, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        final com.android.server.wm.Transition.ReadyCondition readyCondition;
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("startTransition()");
        final com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final com.android.server.wm.Transition fromBinder = com.android.server.wm.Transition.fromBinder(iBinder);
                    if (this.mTransitionController.getTransitionPlayer() == null && fromBinder == null) {
                        android.util.Slog.w(TAG, "Using shell transitions API for legacy transitions.");
                        if (windowContainerTransaction == null) {
                            throw new java.lang.IllegalArgumentException("Can't use legacy transitions in compatibility mode with no WCT.");
                        }
                        applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    final android.window.WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new android.window.WindowContainerTransaction();
                    if (fromBinder == null) {
                        if (i < 0) {
                            throw new java.lang.IllegalArgumentException("Can't create transition with no type");
                        }
                        final com.android.server.wm.Transition.ReadyCondition readyCondition2 = new com.android.server.wm.Transition.ReadyCondition("start WCT applied");
                        final boolean z = windowContainerTransaction != null;
                        final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
                        transition.mReadyTracker.add(readyCondition2);
                        transition.calcParallelCollectType(windowContainerTransaction2);
                        this.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda11
                            @Override // com.android.server.wm.TransitionController.OnStartCollect
                            public final void onCollectStarted(boolean z2) {
                                com.android.server.wm.WindowOrganizerController.this.lambda$startTransition$3(transition, windowContainerTransaction2, callerInfo, readyCondition2, z, z2);
                            }
                        });
                        android.os.IBinder token = transition.getToken();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return token;
                    }
                    if (windowContainerTransaction != null) {
                        com.android.server.wm.Transition.ReadyCondition readyCondition3 = new com.android.server.wm.Transition.ReadyCondition("start WCT applied");
                        fromBinder.mReadyTracker.add(readyCondition3);
                        readyCondition = readyCondition3;
                    } else {
                        readyCondition = null;
                    }
                    if (!fromBinder.isCollecting() && !fromBinder.isForcePlaying()) {
                        android.util.Slog.e(TAG, "Trying to start a transition that isn't collecting. This probably means Shell took too long to respond to a request. WM State may be incorrect now, please file a bug");
                        applyTransaction(windowContainerTransaction2, -1, null, callerInfo);
                        if (readyCondition != null) {
                            readyCondition.meet();
                        }
                        android.os.IBinder token2 = fromBinder.getToken();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return token2;
                    }
                    fromBinder.mLogger.mStartWCT = windowContainerTransaction2;
                    if (fromBinder.shouldApplyOnDisplayThread()) {
                        this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda12
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.WindowOrganizerController.this.lambda$startTransition$4(fromBinder, windowContainerTransaction2, callerInfo, readyCondition);
                            }
                        });
                    } else {
                        fromBinder.start();
                        applyTransaction(windowContainerTransaction2, -1, fromBinder, callerInfo);
                        if (readyCondition != null) {
                            readyCondition.meet();
                        }
                    }
                    android.os.IBinder token3 = fromBinder.getToken();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return token3;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startTransition$3(com.android.server.wm.Transition transition, android.window.WindowContainerTransaction windowContainerTransaction, com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, com.android.server.wm.Transition.ReadyCondition readyCondition, boolean z, boolean z2) {
        transition.start();
        transition.mLogger.mStartWCT = windowContainerTransaction;
        applyTransaction(windowContainerTransaction, -1, transition, callerInfo, z2);
        readyCondition.meet();
        if (z) {
            setAllReadyIfNeeded(transition, windowContainerTransaction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startTransition$4(com.android.server.wm.Transition transition, android.window.WindowContainerTransaction windowContainerTransaction, com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, com.android.server.wm.Transition.ReadyCondition readyCondition) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                transition.start();
                applyTransaction(windowContainerTransaction, -1, transition, callerInfo);
                if (readyCondition != null) {
                    readyCondition.meet();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private static boolean hasActivityLaunch(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        for (int i = 0; i < windowContainerTransaction.getHierarchyOps().size(); i++) {
            if (((android.window.WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i)).getType() == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean isCreatedTaskFragmentReady(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        com.android.server.wm.TaskFragment taskFragment;
        for (int i = 0; i < windowContainerTransaction.getHierarchyOps().size(); i++) {
            android.window.WindowContainerTransaction.HierarchyOp hierarchyOp = (android.window.WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i);
            if (hierarchyOp.getType() == 17 && hierarchyOp.getTaskFragmentOperation().getOpType() == 0 && (taskFragment = getTaskFragment(hierarchyOp.getTaskFragmentOperation().getTaskFragmentCreationParams().getFragmentToken())) != null && !taskFragment.isReadyToTransit()) {
                return false;
            }
        }
        return true;
    }

    private void setAllReadyIfNeeded(@android.annotation.NonNull com.android.server.wm.Transition transition, @android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        if ((hasActivityLaunch(windowContainerTransaction) && !this.mService.mRootWindowContainer.allPausedActivitiesComplete()) || !isCreatedTaskFragmentReady(windowContainerTransaction)) {
            return;
        }
        transition.setAllReady();
    }

    /* JADX WARN: Finally extract failed */
    public int startLegacyTransition(int i, @android.annotation.NonNull android.view.RemoteAnimationAdapter remoteAnimationAdapter, @android.annotation.NonNull android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback, @android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("startLegacyTransition()");
        com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (i < 0) {
                        throw new java.lang.IllegalArgumentException("Can't create transition with no type");
                    }
                    if (this.mTransitionController.getTransitionPlayer() != null) {
                        throw new java.lang.IllegalArgumentException("Can't use legacy transitions in when shell transitions are enabled.");
                    }
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(0);
                    if (displayContent.mAppTransition.isTransitionSet()) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                    }
                    remoteAnimationAdapter.setCallingPidUid(callerInfo.mPid, callerInfo.mUid);
                    displayContent.prepareAppTransition(i);
                    displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, true, false);
                    int startSyncWithOrganizer = startSyncWithOrganizer(iWindowContainerTransactionCallback);
                    applyTransaction(windowContainerTransaction, startSyncWithOrganizer, null, callerInfo);
                    setSyncReady(startSyncWithOrganizer);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return startSyncWithOrganizer;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public void finishTransition(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("finishTransition()");
        com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Transition fromBinder = com.android.server.wm.Transition.fromBinder(iBinder);
                    if (windowContainerTransaction != null) {
                        this.mTransitionController.mFinishingTransition = fromBinder;
                        applyTransaction(windowContainerTransaction, -1, (com.android.server.wm.Transition) null, callerInfo, fromBinder);
                    }
                    this.mTransitionController.finishTransition(fromBinder);
                    this.mTransitionController.mFinishingTransition = null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void applyTaskFragmentTransactionLocked(@android.annotation.NonNull final android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z, @android.annotation.Nullable final android.window.RemoteTransition remoteTransition) {
        android.window.ITaskFragmentOrganizer taskFragmentOrganizer = windowContainerTransaction.getTaskFragmentOrganizer();
        java.util.Objects.requireNonNull(taskFragmentOrganizer);
        java.util.Objects.requireNonNull(windowContainerTransaction);
        enforceTaskFragmentOrganizerPermission("applyTaskFragmentTransaction()", taskFragmentOrganizer, windowContainerTransaction);
        if (remoteTransition != null && !this.mTaskFragmentOrganizerController.isSystemOrganizer(windowContainerTransaction.getTaskFragmentOrganizer().asBinder())) {
            throw new java.lang.SecurityException("Only a system organizer is allowed to use remote transition!");
        }
        final com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo = new com.android.server.wm.WindowOrganizerController.CallerInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mTransitionController.getTransitionPlayer() == null) {
                applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                return;
            }
            if (!this.mService.mWindowManager.mSyncEngine.hasActiveSync() || z) {
                final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
                this.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda10
                    @Override // com.android.server.wm.TransitionController.OnStartCollect
                    public final void onCollectStarted(boolean z2) {
                        com.android.server.wm.WindowOrganizerController.this.lambda$applyTaskFragmentTransactionLocked$5(windowContainerTransaction, transition, callerInfo, remoteTransition, z2);
                    }
                });
            } else {
                com.android.server.wm.Transition collectingTransition = this.mTransitionController.getCollectingTransition();
                if (collectingTransition == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 6110791601270766802L, 0, null, null);
                }
                applyTransaction(windowContainerTransaction, -1, collectingTransition, callerInfo);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTaskFragmentTransactionLocked$5(android.window.WindowContainerTransaction windowContainerTransaction, com.android.server.wm.Transition transition, com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, android.window.RemoteTransition remoteTransition, boolean z) {
        if (z && !this.mTaskFragmentOrganizerController.isValidTransaction(windowContainerTransaction)) {
            transition.abort();
        } else if (applyTransaction(windowContainerTransaction, -1, transition, callerInfo, z) == 0 && transition.mParticipants.isEmpty()) {
            transition.abort();
        } else {
            this.mTransitionController.requestStartTransition(transition, null, remoteTransition, null);
            setAllReadyIfNeeded(transition, windowContainerTransaction);
        }
    }

    private int applyTransaction(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction, int i, @android.annotation.Nullable com.android.server.wm.Transition transition, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo) {
        return applyTransaction(windowContainerTransaction, i, transition, callerInfo, (com.android.server.wm.Transition) null);
    }

    private int applyTransaction(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction, int i, @android.annotation.Nullable com.android.server.wm.Transition transition, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, boolean z) {
        if (z) {
            try {
                return applyTransaction(windowContainerTransaction, i, transition, callerInfo);
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(TAG, "Failed to execute deferred applyTransaction", e);
                return 0;
            }
        }
        return applyTransaction(windowContainerTransaction, i, transition, callerInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01eb A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004b A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x029e A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02d8 A[Catch: all -> 0x003d, TRY_LEAVE, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02b5 A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009f A[Catch: all -> 0x003d, TRY_ENTER, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x017c A[EDGE_INSN: B:90:0x017c->B:91:0x017c BREAK  A[LOOP:1: B:33:0x0094->B:44:0x0094], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x017e A[Catch: all -> 0x003d, TryCatch #0 {all -> 0x003d, blocks: (B:3:0x002b, B:5:0x0032, B:8:0x0041, B:10:0x004b, B:11:0x0057, B:13:0x005e, B:16:0x0071, B:18:0x007d, B:25:0x0084, B:32:0x0088, B:33:0x0094, B:36:0x009f, B:38:0x00b1, B:47:0x00bb, B:49:0x00c0, B:50:0x00c3, B:53:0x00d3, B:55:0x00d9, B:56:0x00dc, B:58:0x00e3, B:60:0x00e9, B:64:0x00fa, B:68:0x011c, B:69:0x0107, B:73:0x0117, B:77:0x0123, B:78:0x013d, B:80:0x014e, B:81:0x0157, B:83:0x015b, B:85:0x015f, B:42:0x0166, B:92:0x017e, B:94:0x0188, B:97:0x01d9, B:98:0x01e5, B:100:0x01eb, B:102:0x01fd, B:110:0x0205, B:113:0x0215, B:118:0x021e, B:120:0x0224, B:122:0x022a, B:124:0x0236, B:128:0x023b, B:129:0x0251, B:133:0x0252, B:135:0x026a, B:136:0x0279, B:138:0x026e, B:106:0x027e, B:146:0x029a, B:148:0x029e, B:150:0x02d8, B:153:0x02b5, B:155:0x02b9, B:157:0x02c1), top: B:2:0x002b }] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.server.wm.TaskFragment] */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.android.server.wm.Task] */
    /* JADX WARN: Type inference failed for: r6v21, types: [int] */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int applyTransaction(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction, int i, @android.annotation.Nullable com.android.server.wm.Transition transition, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, @android.annotation.Nullable com.android.server.wm.Transition transition2) {
        int i2;
        int size;
        java.util.Iterator it;
        boolean hasNext;
        java.lang.String str;
        java.lang.String str2;
        android.graphics.Rect rect;
        android.graphics.Rect rect2;
        java.lang.String str3;
        java.lang.String str4;
        android.util.ArraySet<com.android.server.wm.WindowContainer<?>> arraySet;
        ?? r7;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 9200403125156001641L, 1, null, java.lang.Long.valueOf(i));
        this.mService.deferWindowLayout();
        ?? r14 = 1;
        this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(true);
        boolean z = false;
        try {
            android.util.ArraySet<com.android.server.wm.WindowContainer<?>> arraySet2 = new android.util.ArraySet<>();
            if (transition != null) {
                transition.applyDisplayChangeIfNeeded(arraySet2);
                if (!arraySet2.isEmpty()) {
                    i2 = 1;
                    ?? hierarchyOps = windowContainerTransaction.getHierarchyOps();
                    size = hierarchyOps.size();
                    if (transition != null) {
                        for (java.util.Map.Entry entry : windowContainerTransaction.getChanges().entrySet()) {
                            if (((android.window.WindowContainerTransaction.Change) entry.getValue()).getConfigAtTransitionEnd()) {
                                com.android.server.wm.WindowContainer<?> fromBinder = com.android.server.wm.WindowContainer.fromBinder((android.os.IBinder) entry.getKey());
                                if (fromBinder != null && fromBinder.isAttached()) {
                                    transition.setConfigAtEnd(fromBinder);
                                }
                            }
                        }
                    }
                    it = windowContainerTransaction.getChanges().entrySet().iterator();
                    while (true) {
                        hasNext = it.hasNext();
                        str = "Attempt to operate on detached container: ";
                        str2 = TAG;
                        rect = null;
                        if (hasNext) {
                            break;
                        }
                        java.util.Map.Entry entry2 = (java.util.Map.Entry) it.next();
                        com.android.server.wm.WindowContainer<?> fromBinder2 = com.android.server.wm.WindowContainer.fromBinder((android.os.IBinder) entry2.getKey());
                        if (fromBinder2 == null || !fromBinder2.isAttached()) {
                            android.util.Slog.e(TAG, "Attempt to operate on detached container: " + fromBinder2);
                            r14 = 1;
                            z = false;
                        } else {
                            if (i >= 0) {
                                addToSyncSet(i, fromBinder2);
                            }
                            if (transition != null) {
                                transition.collect(fromBinder2);
                            }
                            if ((((android.window.WindowContainerTransaction.Change) entry2.getValue()).getChangeMask() & 64) != 0) {
                                if (transition2 != null) {
                                    transition2.setCanPipOnFinish(z);
                                } else if (transition != null) {
                                    transition.setCanPipOnFinish(z);
                                }
                            }
                            if (fromBinder2.asTask() != null && fromBinder2.inPinnedWindowingMode() && ((android.window.WindowContainerTransaction.Change) entry2.getValue()).getWindowingMode() != 2) {
                                boolean z2 = z;
                                r7 = z2;
                                for (?? r6 = z2; r6 < size; r6++) {
                                    android.window.WindowContainerTransaction.HierarchyOp hierarchyOp = (android.window.WindowContainerTransaction.HierarchyOp) hierarchyOps.get(r6);
                                    if (hierarchyOp.getType() == r14 && fromBinder2.equals(com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer()))) {
                                        r7 = (hierarchyOp.getToTop() ? 1 : 0) ^ r14;
                                    }
                                }
                            } else {
                                r7 = 0;
                            }
                            if (r7 != 0) {
                                fromBinder2.asTask().setForceHidden(r14, r14);
                                fromBinder2.asTask().ensureActivitiesVisible(null);
                                fromBinder2.asTask().mTaskSupervisor.processStoppingAndFinishingActivities(null, false, "force-stop-on-removing-pip");
                            }
                            int applyWindowContainerChange = applyWindowContainerChange(fromBinder2, (android.window.WindowContainerTransaction.Change) entry2.getValue(), windowContainerTransaction.getErrorCallbackToken());
                            i2 |= applyWindowContainerChange;
                            if (r7 != 0) {
                                fromBinder2.asTask().setForceHidden(1, false);
                            }
                            if ((i2 & 2) == 0 && (applyWindowContainerChange & 1) != 0) {
                                arraySet2.add(fromBinder2);
                            }
                            r14 = 1;
                            z = false;
                        }
                    }
                    if (size > 0) {
                        rect2 = null;
                        str3 = TAG;
                        str4 = "Attempt to operate on detached container: ";
                        arraySet = arraySet2;
                    } else {
                        boolean isInLockTaskMode = this.mService.isInLockTaskMode();
                        int i3 = i2;
                        int i4 = 0;
                        java.util.List list = hierarchyOps;
                        while (i4 < size) {
                            i3 |= applyHierarchyOp((android.window.WindowContainerTransaction.HierarchyOp) list.get(i4), i3, i, transition, isInLockTaskMode, callerInfo, windowContainerTransaction.getErrorCallbackToken(), windowContainerTransaction.getTaskFragmentOrganizer(), transition2);
                            i4++;
                            rect = rect;
                            list = list;
                            arraySet2 = arraySet2;
                            str2 = str2;
                            str = str;
                            size = size;
                        }
                        rect2 = rect;
                        str3 = str2;
                        str4 = str;
                        arraySet = arraySet2;
                        i2 = i3;
                    }
                    for (java.util.Map.Entry entry3 : windowContainerTransaction.getChanges().entrySet()) {
                        com.android.server.wm.WindowContainer fromBinder3 = com.android.server.wm.WindowContainer.fromBinder((android.os.IBinder) entry3.getKey());
                        if (fromBinder3 == null || !fromBinder3.isAttached()) {
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            java.lang.String str5 = str4;
                            sb.append(str5);
                            sb.append(fromBinder3);
                            java.lang.String str6 = str3;
                            android.util.Slog.e(str6, sb.toString());
                            str3 = str6;
                            str4 = str5;
                        } else {
                            com.android.server.wm.Task asTask = fromBinder3.asTask();
                            android.graphics.Rect boundsChangeSurfaceBounds = ((android.window.WindowContainerTransaction.Change) entry3.getValue()).getBoundsChangeSurfaceBounds();
                            if (asTask != null && asTask.isAttached() && boundsChangeSurfaceBounds != null) {
                                if (!asTask.isOrganized()) {
                                    ?? asTask2 = asTask.getParent() != null ? asTask.getParent().asTask() : rect2;
                                    if (asTask2 == 0 || !asTask2.mCreatedByOrganizer) {
                                        throw new java.lang.IllegalArgumentException("Can't manipulate non-organized task surface " + asTask);
                                    }
                                }
                                android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
                                android.view.SurfaceControl surfaceControl = asTask.getSurfaceControl();
                                transaction.setPosition(surfaceControl, boundsChangeSurfaceBounds.left, boundsChangeSurfaceBounds.top);
                                if (boundsChangeSurfaceBounds.isEmpty()) {
                                    transaction.setWindowCrop(surfaceControl, rect2);
                                } else {
                                    transaction.setWindowCrop(surfaceControl, boundsChangeSurfaceBounds.width(), boundsChangeSurfaceBounds.height());
                                }
                                asTask.setMainWindowSizeChangeTransaction(transaction);
                            }
                        }
                    }
                    if ((i2 & 2) == 0) {
                        this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                        this.mService.mRootWindowContainer.ensureActivitiesVisible();
                        this.mService.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    } else if ((i2 & 1) != 0) {
                        int size2 = arraySet.size() - 1;
                        while (size2 >= 0) {
                            android.util.ArraySet<com.android.server.wm.WindowContainer<?>> arraySet3 = arraySet;
                            arraySet3.valueAt(size2).forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.wm.WindowOrganizerController.lambda$applyTransaction$6((com.android.server.wm.ActivityRecord) obj);
                                }
                            });
                            size2--;
                            arraySet = arraySet3;
                        }
                    }
                    if (i2 != 0) {
                        this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
                    }
                    this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                    this.mService.continueWindowLayout();
                    return i2;
                }
            }
            i2 = 0;
            ?? hierarchyOps2 = windowContainerTransaction.getHierarchyOps();
            size = hierarchyOps2.size();
            if (transition != null) {
            }
            it = windowContainerTransaction.getChanges().entrySet().iterator();
            while (true) {
                hasNext = it.hasNext();
                str = "Attempt to operate on detached container: ";
                str2 = TAG;
                rect = null;
                if (hasNext) {
                }
            }
            if (size > 0) {
            }
            while (r2.hasNext()) {
            }
            if ((i2 & 2) == 0) {
            }
            if (i2 != 0) {
            }
            this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
            this.mService.continueWindowLayout();
            return i2;
        } catch (java.lang.Throwable th) {
            this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
            this.mService.continueWindowLayout();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyTransaction$6(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.isVisibleRequested()) {
            activityRecord.ensureActivityConfiguration(true);
        }
    }

    private int applyChanges(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, @android.annotation.NonNull android.window.WindowContainerTransaction.Change change) {
        int configSetMask = change.getConfigSetMask() & CONTROLLABLE_CONFIGS;
        int i = 3;
        int windowSetMask = change.getWindowSetMask() & 3;
        int windowingMode = change.getWindowingMode();
        if (configSetMask == 0) {
            i = 0;
        } else {
            if (windowingMode > -1 && windowingMode != windowContainer.getWindowingMode()) {
                windowContainer.getRequestedOverrideConfiguration().setTo(change.getConfiguration(), configSetMask, windowSetMask);
            } else {
                android.content.res.Configuration configuration = new android.content.res.Configuration(windowContainer.getRequestedOverrideConfiguration());
                configuration.setTo(change.getConfiguration(), configSetMask, windowSetMask);
                windowContainer.onRequestedOverrideConfigurationChanged(configuration);
            }
            if (windowSetMask == 0 || !windowContainer.isEmbedded()) {
                i = 1;
            }
        }
        if ((change.getChangeMask() & 1) != 0 && windowContainer.setFocusable(change.getFocusable())) {
            i |= 2;
        }
        if (windowingMode > -1) {
            if (this.mService.isInLockTaskMode() && android.app.WindowConfiguration.inMultiWindowMode(windowingMode)) {
                android.util.Slog.w(TAG, "Dropping unsupported request to set multi-window windowing mode during locked task mode.");
                return i;
            }
            if (windowingMode == 2) {
                return i;
            }
            int requestedOverrideWindowingMode = windowContainer.getRequestedOverrideWindowingMode();
            windowContainer.setWindowingMode(windowingMode);
            if (requestedOverrideWindowingMode != windowContainer.getWindowingMode()) {
                return i | 2;
            }
            return i;
        }
        return i;
    }

    private int applyTaskChanges(com.android.server.wm.Task task, android.window.WindowContainerTransaction.Change change) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        int applyChanges = applyChanges(task, change);
        android.view.SurfaceControl.Transaction boundsChangeTransaction = change.getBoundsChangeTransaction();
        if ((change.getChangeMask() & 8) != 0 && task.setForceHidden(2, change.getHidden())) {
            applyChanges = 2;
        }
        if ((change.getChangeMask() & 128) != 0) {
            task.setForceTranslucent(change.getForceTranslucent());
            applyChanges = 2;
        }
        if ((change.getChangeMask() & 256) != 0) {
            task.setDragResizing(change.getDragResizing());
        }
        final int activityWindowingMode = change.getActivityWindowingMode();
        if (activityWindowingMode > -1) {
            task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.ActivityRecord) obj).setWindowingMode(activityWindowingMode);
                }
            });
        }
        if (boundsChangeTransaction != null) {
            task.setMainWindowSizeChangeTransaction(boundsChangeTransaction);
        }
        android.graphics.Rect enterPipBounds = change.getEnterPipBounds();
        if (enterPipBounds != null) {
            task.mDisplayContent.mPinnedTaskController.setEnterPipBounds(enterPipBounds);
        }
        if (change.getWindowingMode() == 2 && !task.inPinnedWindowingMode() && (topNonFinishingActivity = task.getTopNonFinishingActivity()) != null) {
            boolean z = topNonFinishingActivity.supportsEnterPipOnTaskSwitch;
            topNonFinishingActivity.supportsEnterPipOnTaskSwitch = true;
            boolean checkEnterPictureInPictureState = topNonFinishingActivity.checkEnterPictureInPictureState("applyTaskChanges", true);
            if (checkEnterPictureInPictureState) {
                checkEnterPictureInPictureState = this.mService.mActivityClientController.requestPictureInPictureMode(topNonFinishingActivity);
            }
            if (!checkEnterPictureInPictureState) {
                topNonFinishingActivity.supportsEnterPipOnTaskSwitch = z;
            }
        }
        return applyChanges;
    }

    private int applyDisplayAreaChanges(com.android.server.wm.DisplayArea displayArea, final android.window.WindowContainerTransaction.Change change) {
        final int[] iArr = {applyChanges(displayArea, change)};
        if ((change.getChangeMask() & 32) != 0 && displayArea.setIgnoreOrientationRequest(change.getIgnoreOrientationRequest())) {
            iArr[0] = iArr[0] | 2;
        }
        displayArea.forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowOrganizerController.lambda$applyDisplayAreaChanges$8(change, iArr, obj);
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyDisplayAreaChanges$8(android.window.WindowContainerTransaction.Change change, int[] iArr, java.lang.Object obj) {
        com.android.server.wm.Task task = (com.android.server.wm.Task) obj;
        if ((change.getChangeMask() & 8) != 0 && task.setForceHidden(2, change.getHidden())) {
            iArr[0] = iArr[0] | 2;
        }
    }

    private int applyTaskFragmentChanges(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, @android.annotation.NonNull android.window.WindowContainerTransaction.Change change, @android.annotation.Nullable android.os.IBinder iBinder) {
        int i = 0;
        if (taskFragment.isEmbeddedTaskFragmentInPip()) {
            return 0;
        }
        this.mTmpBounds0.set(taskFragment.getBounds());
        this.mTmpBounds1.set(taskFragment.getRelativeEmbeddedBounds());
        taskFragment.deferOrganizedTaskFragmentSurfaceUpdate();
        android.graphics.Rect relativeBounds = change.getRelativeBounds();
        if (relativeBounds != null) {
            adjustTaskFragmentRelativeBoundsForMinDimensionsIfNeeded(taskFragment, relativeBounds, iBinder);
            change.getConfiguration().windowConfiguration.setBounds(taskFragment.translateRelativeBoundsToAbsoluteBounds(relativeBounds, taskFragment.getParent().getBounds()));
            taskFragment.setRelativeEmbeddedBounds(relativeBounds);
        }
        int i2 = 2;
        if ((change.getChangeMask() & 8) != 0 && taskFragment.setForceHidden(4, change.getHidden())) {
            i = 2;
        }
        if ((change.getChangeMask() & 128) == 0) {
            i2 = i;
        } else {
            taskFragment.setForceTranslucent(change.getForceTranslucent());
        }
        int applyChanges = applyChanges(taskFragment, change) | i2;
        if (taskFragment.shouldStartChangeTransition(this.mTmpBounds0, this.mTmpBounds1)) {
            taskFragment.initializeChangeTransition(this.mTmpBounds0);
        }
        taskFragment.continueOrganizedTaskFragmentSurfaceUpdate();
        return applyChanges;
    }

    private void adjustTaskFragmentRelativeBoundsForMinDimensionsIfNeeded(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, @android.annotation.NonNull android.graphics.Rect rect, @android.annotation.Nullable android.os.IBinder iBinder) {
        if (rect.isEmpty()) {
            return;
        }
        android.graphics.Point calculateMinDimension = taskFragment.calculateMinDimension();
        if (rect.width() < calculateMinDimension.x || rect.height() < calculateMinDimension.y) {
            sendTaskFragmentOperationFailure(taskFragment.getTaskFragmentOrganizer(), iBinder, taskFragment, 9, new java.lang.SecurityException("The requested relative bounds:" + rect + " does not satisfy minimum dimensions:" + calculateMinDimension));
            rect.setEmpty();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x04d1  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int applyHierarchyOp(final android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, int i, int i2, @android.annotation.Nullable com.android.server.wm.Transition transition, boolean z, @android.annotation.NonNull final com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.Nullable com.android.server.wm.Transition transition2) {
        int sanitizeAndApplyHierarchyOp;
        final java.lang.String str;
        com.android.server.wm.WindowContainer fromBinder;
        com.android.server.wm.Task transientLaunchRestoreTarget;
        int type = hierarchyOp.getType();
        switch (type) {
            case 0:
            case 1:
                com.android.server.wm.WindowContainer fromBinder2 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                if (fromBinder2 == null || !fromBinder2.isAttached()) {
                    android.util.Slog.e(TAG, "Attempt to operate on detached container: " + fromBinder2);
                } else if (z && type == 0) {
                    android.util.Slog.w(TAG, "Skip applying hierarchy operation " + hierarchyOp + " while in lock task mode");
                } else if (!isLockTaskModeViolation(fromBinder2.getParent(), fromBinder2.asTask(), z)) {
                    if (i2 >= 0) {
                        addToSyncSet(i2, fromBinder2);
                    }
                    if (transition != null) {
                        transition.collect(fromBinder2);
                        if (hierarchyOp.isReparent()) {
                            if (fromBinder2.getParent() != null) {
                                transition.collect(fromBinder2.getParent());
                            }
                            if (hierarchyOp.getNewParent() != null) {
                                com.android.server.wm.WindowContainer fromBinder3 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getNewParent());
                                if (fromBinder3 == null) {
                                    android.util.Slog.e(TAG, "Can't resolve parent window from token");
                                } else {
                                    transition.collect(fromBinder3);
                                }
                            }
                        }
                    }
                    sanitizeAndApplyHierarchyOp = i | sanitizeAndApplyHierarchyOp(fromBinder2, hierarchyOp);
                    switch (type) {
                        case 8:
                            android.os.Bundle launchOptions = hierarchyOp.getLaunchOptions();
                            java.lang.String string = launchOptions.getString("android:transaction.hop.shortcut_calling_package");
                            launchOptions.remove("android:transaction.hop.shortcut_calling_package");
                            if (((com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal.class)).startShortcut(callerInfo.mUid, callerInfo.mPid, string, hierarchyOp.getShortcutInfo().getPackage(), null, hierarchyOp.getShortcutInfo().getId(), null, launchOptions, hierarchyOp.getShortcutInfo().getUserId())) {
                                return sanitizeAndApplyHierarchyOp | 2;
                            }
                            return sanitizeAndApplyHierarchyOp;
                        case 9:
                            if (transition2 != null && (fromBinder = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer())) != null) {
                                com.android.server.wm.Task task = fromBinder.asActivityRecord() != null ? fromBinder.asActivityRecord().getTask() : fromBinder.asTask();
                                if (task != null && (transientLaunchRestoreTarget = transition2.getTransientLaunchRestoreTarget(fromBinder)) != null) {
                                    task.getTaskDisplayArea().moveRootTaskBehindRootTask(task.getRootTask(), transientLaunchRestoreTarget);
                                    return sanitizeAndApplyHierarchyOp;
                                }
                                return sanitizeAndApplyHierarchyOp;
                            }
                            return sanitizeAndApplyHierarchyOp;
                        case 10:
                            com.android.server.wm.WindowContainer fromBinder4 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                            if (fromBinder4 == null) {
                                android.util.Slog.e(TAG, "Attempt to add local insets source provider on unknown: " + fromBinder4);
                                return sanitizeAndApplyHierarchyOp;
                            }
                            fromBinder4.addLocalInsetsFrameProvider(hierarchyOp.getInsetsFrameProvider(), hierarchyOp.getInsetsFrameOwner());
                            return sanitizeAndApplyHierarchyOp;
                        case 11:
                            com.android.server.wm.WindowContainer fromBinder5 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                            if (fromBinder5 == null) {
                                android.util.Slog.e(TAG, "Attempt to remove local insets source provider from unknown: " + fromBinder5);
                                return sanitizeAndApplyHierarchyOp;
                            }
                            fromBinder5.removeLocalInsetsFrameProvider(hierarchyOp.getInsetsFrameProvider(), hierarchyOp.getInsetsFrameOwner());
                            return sanitizeAndApplyHierarchyOp;
                        case 12:
                            com.android.server.wm.WindowContainer fromBinder6 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                            if (fromBinder6 == null || !fromBinder6.isAttached()) {
                                android.util.Slog.e(TAG, "Attempt to operate on unknown or detached container: " + fromBinder6);
                                return sanitizeAndApplyHierarchyOp;
                            }
                            if (fromBinder6.asTask() == null && fromBinder6.asDisplayArea() == null) {
                                android.util.Slog.e(TAG, "Cannot set always-on-top on non-task or non-display area: " + fromBinder6);
                                return sanitizeAndApplyHierarchyOp;
                            }
                            fromBinder6.setAlwaysOnTop(hierarchyOp.isAlwaysOnTop());
                            return sanitizeAndApplyHierarchyOp | 2;
                        case 13:
                        case 14:
                        case 15:
                        case 17:
                        default:
                            return sanitizeAndApplyHierarchyOp;
                        case 16:
                            com.android.server.wm.WindowContainer fromBinder7 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                            com.android.server.wm.Task asTask = fromBinder7 != null ? fromBinder7.asTask() : null;
                            if (asTask == null || !asTask.isAttached()) {
                                android.util.Slog.e(TAG, "Attempt to operate on unknown or detached container: " + fromBinder7);
                                return sanitizeAndApplyHierarchyOp;
                            }
                            if (!asTask.mCreatedByOrganizer) {
                                throw new java.lang.UnsupportedOperationException("Cannot set reparent leaf task flag on non-organized task : " + asTask);
                            }
                            if (!asTask.isRootTask()) {
                                throw new java.lang.UnsupportedOperationException("Cannot set reparent leaf task flag on non-root task : " + asTask);
                            }
                            asTask.setReparentLeafTaskIfRelaunch(hierarchyOp.isReparentLeafTaskIfRelaunch());
                            return sanitizeAndApplyHierarchyOp;
                        case 18:
                            com.android.server.wm.Task asTask2 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer()).asTask();
                            if (asTask2 != null) {
                                com.android.server.wm.ActivityRecord activity = asTask2.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(java.lang.Object obj) {
                                        boolean lambda$applyHierarchyOp$11;
                                        lambda$applyHierarchyOp$11 = com.android.server.wm.WindowOrganizerController.lambda$applyHierarchyOp$11((com.android.server.wm.ActivityRecord) obj);
                                        return lambda$applyHierarchyOp$11;
                                    }
                                });
                                this.mService.mRootWindowContainer.moveActivityToPinnedRootTask(activity, null, "moveActivityToPinnedRootTask", null, hierarchyOp.getBounds());
                                if (activity.isState(com.android.server.wm.ActivityRecord.State.PAUSING) && activity.mPauseSchedulePendingForPip) {
                                    activity.getTask().schedulePauseActivity(activity, false, false, true, "auto-pip");
                                }
                                return sanitizeAndApplyHierarchyOp | 2;
                            }
                            return sanitizeAndApplyHierarchyOp;
                    }
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 2:
                sanitizeAndApplyHierarchyOp = i | reparentChildrenTasksHierarchyOp(hierarchyOp, transition, i2, z);
                switch (type) {
                }
            case 3:
                com.android.server.wm.WindowContainer fromBinder8 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                if (fromBinder8 == null || !fromBinder8.isAttached()) {
                    android.util.Slog.e(TAG, "Attempt to set launch root to a detached container: " + fromBinder8);
                } else {
                    com.android.server.wm.Task asTask3 = fromBinder8.asTask();
                    if (asTask3 == null) {
                        throw new java.lang.IllegalArgumentException("Cannot set non-task as launch root: " + fromBinder8);
                    }
                    if (asTask3.getTaskDisplayArea() == null) {
                        throw new java.lang.IllegalArgumentException("Cannot set a task without display area as launch root: " + fromBinder8);
                    }
                    asTask3.getDisplayArea().setLaunchRootTask(asTask3, hierarchyOp.getWindowingModes(), hierarchyOp.getActivityTypes());
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 4:
                sanitizeAndApplyHierarchyOp = i | setAdjacentRootsHierarchyOp(hierarchyOp);
                switch (type) {
                }
            case 5:
                this.mService.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "launchTask HierarchyOp");
                android.os.Bundle launchOptions2 = hierarchyOp.getLaunchOptions();
                final int i3 = launchOptions2.getInt("android:transaction.hop.taskId");
                launchOptions2.remove("android:transaction.hop.taskId");
                final com.android.server.wm.SafeActivityOptions fromBundle = com.android.server.wm.SafeActivityOptions.fromBundle(launchOptions2, callerInfo.mPid, callerInfo.mUid);
                if (transition != null) {
                    transition.deferTransitionReady();
                }
                waitAsyncStart(new java.util.function.IntSupplier() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntSupplier
                    public final int getAsInt() {
                        int lambda$applyHierarchyOp$9;
                        lambda$applyHierarchyOp$9 = com.android.server.wm.WindowOrganizerController.this.lambda$applyHierarchyOp$9(callerInfo, i3, fromBundle);
                        return lambda$applyHierarchyOp$9;
                    }
                });
                if (transition != null) {
                    transition.continueTransitionReady();
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 6:
                com.android.server.wm.WindowContainer fromBinder9 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                if (fromBinder9 == null || !fromBinder9.isAttached()) {
                    android.util.Slog.e(TAG, "Attempt to set launch adjacent to a detached container: " + fromBinder9);
                } else {
                    com.android.server.wm.Task asTask4 = fromBinder9.asTask();
                    boolean toTop = hierarchyOp.getToTop();
                    if (asTask4 == null) {
                        throw new java.lang.IllegalArgumentException("Cannot set non-task as launch root: " + fromBinder9);
                    }
                    if (!asTask4.mCreatedByOrganizer) {
                        throw new java.lang.UnsupportedOperationException("Cannot set non-organized task as adjacent flag root: " + fromBinder9);
                    }
                    if (asTask4.getAdjacentTaskFragment() == null && !toTop) {
                        throw new java.lang.UnsupportedOperationException("Cannot set non-adjacent task as adjacent flag root: " + fromBinder9);
                    }
                    com.android.server.wm.TaskDisplayArea displayArea = asTask4.getDisplayArea();
                    if (toTop) {
                        asTask4 = null;
                    }
                    displayArea.setLaunchAdjacentFlagRootTask(asTask4);
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 7:
                android.os.Bundle launchOptions3 = hierarchyOp.getLaunchOptions();
                android.app.ActivityOptions activityOptions = launchOptions3 != null ? new android.app.ActivityOptions(launchOptions3) : null;
                if (activityOptions == null || !activityOptions.getTransientLaunch() || !this.mService.isCallerRecents(hierarchyOp.getPendingIntent().getCreatorUid()) || !this.mService.getActivityStartController().startExistingRecentsIfPossible(hierarchyOp.getActivityIntent(), activityOptions)) {
                    if (hierarchyOp.getActivityIntent() != null) {
                        str = hierarchyOp.getActivityIntent().resolveTypeIfNeeded(this.mService.mContext.getContentResolver());
                    } else {
                        str = null;
                    }
                    if (hierarchyOp.getPendingIntent().isActivity()) {
                        if (activityOptions == null) {
                            activityOptions = android.app.ActivityOptions.makeBasic();
                        }
                        activityOptions.setCallerDisplayId(0);
                    }
                    final android.os.Bundle bundle = activityOptions != null ? activityOptions.toBundle() : null;
                    if (transition != null) {
                        transition.deferTransitionReady();
                    }
                    int waitAsyncStart = waitAsyncStart(new java.util.function.IntSupplier() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntSupplier
                        public final int getAsInt() {
                            int lambda$applyHierarchyOp$10;
                            lambda$applyHierarchyOp$10 = com.android.server.wm.WindowOrganizerController.this.lambda$applyHierarchyOp$10(hierarchyOp, str, bundle);
                            return lambda$applyHierarchyOp$10;
                        }
                    });
                    if (transition != null) {
                        transition.continueTransitionReady();
                    }
                    if (android.app.ActivityManager.isStartResultSuccessful(waitAsyncStart)) {
                        sanitizeAndApplyHierarchyOp = i | 2;
                        switch (type) {
                        }
                    }
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 16:
            default:
                if (z) {
                    android.util.Slog.w(TAG, "Skip applying hierarchy operation " + hierarchyOp + " while in lock task mode");
                    return i;
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 13:
                com.android.server.wm.WindowContainer fromBinder10 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
                if (fromBinder10 == null || fromBinder10.asTask() == null || !fromBinder10.isAttached()) {
                    android.util.Slog.e(TAG, "Attempt to remove invalid task: " + fromBinder10);
                } else {
                    com.android.server.wm.Task asTask5 = fromBinder10.asTask();
                    if (asTask5.isLeafTask()) {
                        this.mService.mTaskSupervisor.removeTask(asTask5, true, true, "remove-task-through-hierarchyOp");
                    } else {
                        this.mService.mTaskSupervisor.removeRootTask(asTask5);
                    }
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 14:
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(hierarchyOp.getContainer());
                if (forTokenLocked != null && !forTokenLocked.finishing) {
                    if (forTokenLocked.isVisible() || forTokenLocked.isVisibleRequested()) {
                        forTokenLocked.finishIfPossible("finish-activity-op", false);
                    } else {
                        forTokenLocked.destroyIfPossible("finish-activity-op");
                    }
                }
                sanitizeAndApplyHierarchyOp = i;
                switch (type) {
                }
            case 15:
                sanitizeAndApplyHierarchyOp = i | clearAdjacentRootsHierarchyOp(hierarchyOp);
                switch (type) {
                }
            case 17:
                sanitizeAndApplyHierarchyOp = i | applyTaskFragmentOperation(hierarchyOp, transition, z, callerInfo, iBinder, iTaskFragmentOrganizer);
                switch (type) {
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$applyHierarchyOp$9(com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, int i, com.android.server.wm.SafeActivityOptions safeActivityOptions) {
        return this.mService.mTaskSupervisor.startActivityFromRecents(callerInfo.mPid, callerInfo.mUid, i, safeActivityOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$applyHierarchyOp$10(android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, java.lang.String str, android.os.Bundle bundle) {
        return this.mService.mAmInternal.sendIntentSender(hierarchyOp.getPendingIntent().getTarget(), hierarchyOp.getPendingIntent().getWhitelistToken(), 0, hierarchyOp.getActivityIntent(), str, (android.content.IIntentReceiver) null, (java.lang.String) null, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyHierarchyOp$11(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.pictureInPictureArgs != null;
    }

    private int applyTaskFragmentOperation(@android.annotation.NonNull android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, @android.annotation.Nullable com.android.server.wm.Transition transition, boolean z, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        com.android.server.wm.ActivityRecord activity;
        com.android.server.wm.TaskFragment taskFragment;
        com.android.server.wm.TaskFragment taskFragment2;
        boolean z2 = false;
        if (!validateTaskFragmentOperation(hierarchyOp, iBinder, iTaskFragmentOrganizer)) {
            return 0;
        }
        com.android.server.wm.TaskFragment taskFragment3 = this.mLaunchTaskFragments.get(hierarchyOp.getContainer());
        android.window.TaskFragmentOperation taskFragmentOperation = hierarchyOp.getTaskFragmentOperation();
        int opType = taskFragmentOperation.getOpType();
        int i = 1;
        int i2 = 2;
        switch (opType) {
            case 0:
                android.window.TaskFragmentCreationParams taskFragmentCreationParams = taskFragmentOperation.getTaskFragmentCreationParams();
                if (taskFragmentCreationParams == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.IllegalArgumentException("TaskFragmentCreationParams must be non-null"));
                    break;
                } else {
                    createTaskFragment(taskFragmentCreationParams, iBinder, callerInfo, transition);
                    break;
                }
            case 1:
                if (!z || (activity = taskFragment3.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$applyTaskFragmentOperation$12;
                        lambda$applyTaskFragmentOperation$12 = com.android.server.wm.WindowOrganizerController.lambda$applyTaskFragmentOperation$12((com.android.server.wm.ActivityRecord) obj);
                        return lambda$applyTaskFragmentOperation$12;
                    }
                }, false)) == null || !this.mService.getLockTaskController().activityBlockedFromFinish(activity)) {
                    break;
                } else {
                    android.util.Slog.w(TAG, "Skip removing TaskFragment due in lock task mode.");
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.IllegalStateException("Not allow to delete task fragment in lock task mode."));
                    break;
                }
                break;
            case 2:
                android.os.IBinder activityToken = taskFragmentOperation.getActivityToken();
                android.content.Intent activityIntent = taskFragmentOperation.getActivityIntent();
                int startActivityInTaskFragment = this.mService.getActivityStartController().startActivityInTaskFragment(taskFragment3, activityIntent, taskFragmentOperation.getBundle(), activityToken, callerInfo.mUid, callerInfo.mPid, iBinder);
                if (!android.app.ActivityManager.isStartResultSuccessful(startActivityInTaskFragment)) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, convertStartFailureToThrowable(startActivityInTaskFragment, activityIntent));
                    break;
                } else {
                    break;
                }
            case 3:
                android.os.IBinder activityToken2 = taskFragmentOperation.getActivityToken();
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(activityToken2);
                com.android.server.wm.ActivityRecord activityRecord = forTokenLocked;
                if (forTokenLocked == null) {
                    activityRecord = this.mTaskFragmentOrganizerController.getReparentActivityFromTemporaryToken(iTaskFragmentOrganizer, activityToken2);
                }
                if (activityRecord == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.IllegalArgumentException("Not allowed to operate with invalid activity."));
                    break;
                } else if (taskFragment3.isAllowedToEmbedActivity(activityRecord) != 0) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.SecurityException("The task fragment is not allowed to embed the given activity."));
                    break;
                } else if (taskFragment3.getTask() != activityRecord.getTask()) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.SecurityException("The reparented activity is not in the same Task as the target TaskFragment."));
                    break;
                } else {
                    if (transition != null) {
                        transition.collect(activityRecord);
                        if (activityRecord.getParent() != null) {
                            transition.collect(activityRecord.getParent());
                        }
                        transition.collect(taskFragment3);
                    }
                    activityRecord.reparent(taskFragment3, Integer.MAX_VALUE);
                    break;
                }
            case 4:
                com.android.server.wm.TaskFragment taskFragment4 = this.mLaunchTaskFragments.get(taskFragmentOperation.getSecondaryFragmentToken());
                if (taskFragment4 == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.IllegalArgumentException("SecondaryFragmentToken must be set for setAdjacentTaskFragments."));
                    break;
                } else {
                    if (taskFragment3.getAdjacentTaskFragment() == taskFragment4) {
                        i2 = 0;
                    } else {
                        taskFragment3.setAdjacentTaskFragment(taskFragment4);
                    }
                    android.os.Bundle launchOptions = hierarchyOp.getLaunchOptions();
                    com.android.server.wm.TaskFragment taskFragmentAdjacentParams = launchOptions != null ? new android.window.WindowContainerTransaction.TaskFragmentAdjacentParams(launchOptions) : null;
                    taskFragment3.setDelayLastActivityRemoval(taskFragmentAdjacentParams != null && taskFragmentAdjacentParams.shouldDelayPrimaryLastActivityRemoval());
                    if (taskFragmentAdjacentParams != null && taskFragmentAdjacentParams.shouldDelaySecondaryLastActivityRemoval()) {
                        z2 = true;
                    }
                    taskFragment4.setDelayLastActivityRemoval(z2);
                    break;
                }
                break;
            case 5:
                com.android.server.wm.TaskFragment adjacentTaskFragment = taskFragment3.getAdjacentTaskFragment();
                if (adjacentTaskFragment != null) {
                    taskFragment3.resetAdjacentTaskFragment();
                    com.android.server.wm.ActivityRecord activityRecord2 = taskFragment3.getDisplayContent().mFocusedApp;
                    if (activityRecord2 != null) {
                        taskFragment = activityRecord2.getTaskFragment();
                    } else {
                        taskFragment = null;
                    }
                    if ((taskFragment == taskFragment3 || taskFragment == adjacentTaskFragment) && !taskFragment.shouldBeVisible(null)) {
                        taskFragment.getDisplayContent().setFocusedApp(null);
                    }
                    break;
                }
                break;
            case 6:
                com.android.server.wm.ActivityRecord activityRecord3 = taskFragment3.getDisplayContent().mFocusedApp;
                if (activityRecord3 != null && activityRecord3.getTaskFragment() == taskFragment3) {
                    android.util.Slog.d(TAG, "The requested TaskFragment already has the focus.");
                    break;
                } else if (activityRecord3 != null && activityRecord3.getTask() != taskFragment3.getTask()) {
                    android.util.Slog.d(TAG, "The Task of the requested TaskFragment doesn't have focus.");
                    break;
                } else {
                    com.android.server.wm.ActivityRecord topResumedActivity = taskFragment3.getTopResumedActivity();
                    if (topResumedActivity == null) {
                        android.util.Slog.d(TAG, "There is no resumed activity in the requested TaskFragment.");
                        break;
                    } else {
                        taskFragment3.getDisplayContent().setFocusedApp(topResumedActivity);
                        break;
                    }
                }
                break;
            case 7:
                android.os.IBinder secondaryFragmentToken = taskFragmentOperation.getSecondaryFragmentToken();
                taskFragment3.setCompanionTaskFragment(secondaryFragmentToken != null ? this.mLaunchTaskFragments.get(secondaryFragmentToken) : null);
                break;
            case 8:
                android.window.TaskFragmentAnimationParams animationParams = taskFragmentOperation.getAnimationParams();
                if (animationParams == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment3, opType, new java.lang.IllegalArgumentException("TaskFragmentAnimationParams must be non-null"));
                    break;
                } else {
                    taskFragment3.setAnimationParams(animationParams);
                    break;
                }
            case 10:
                com.android.server.wm.Task task = taskFragment3.getTask();
                if (task != null && (taskFragment2 = task.getTaskFragment(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$applyTaskFragmentOperation$13;
                        lambda$applyTaskFragmentOperation$13 = com.android.server.wm.WindowOrganizerController.lambda$applyTaskFragmentOperation$13((com.android.server.wm.TaskFragment) obj);
                        return lambda$applyTaskFragmentOperation$13;
                    }
                })) != null && taskFragment2 != taskFragment3) {
                    int indexOf = task.mChildren.indexOf(taskFragment2);
                    task.mChildren.remove(taskFragment3);
                    task.mChildren.add(indexOf, taskFragment3);
                    if (!taskFragment3.hasChild()) {
                        task.assignChildLayers();
                    }
                    break;
                }
                break;
            case 11:
                taskFragment3.setIsolatedNav(taskFragmentOperation.isIsolatedNav());
                break;
            case 12:
                com.android.server.wm.Task task2 = taskFragment3.getTask();
                if (task2 != null) {
                    task2.mChildren.remove(taskFragment3);
                    task2.mChildren.add(0, taskFragment3);
                    if (!taskFragment3.hasChild()) {
                        task2.assignChildLayers();
                    }
                    break;
                }
                break;
            case 13:
                com.android.server.wm.Task task3 = taskFragment3.getTask();
                if (task3 != null) {
                    task3.mChildren.remove(taskFragment3);
                    task3.mChildren.add(taskFragment3);
                    if (!taskFragment3.hasChild()) {
                        task3.assignChildLayers();
                    }
                    break;
                }
                break;
            case 14:
                taskFragment3.getTask().moveOrCreateDecorSurfaceFor(taskFragment3);
                break;
            case 15:
                taskFragment3.getTask().removeDecorSurface();
                break;
            case 16:
                if (!taskFragmentOperation.isDimOnTask()) {
                    i = 0;
                }
                taskFragment3.setEmbeddedDimArea(i);
                break;
            case 17:
                taskFragment3.setMoveToBottomIfClearWhenLaunch(taskFragmentOperation.isMoveToBottomIfClearWhenLaunch());
                break;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyTaskFragmentOperation$12(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyTaskFragmentOperation$13(com.android.server.wm.TaskFragment taskFragment) {
        return taskFragment.asTask() == null;
    }

    private boolean validateTaskFragmentOperation(@android.annotation.NonNull android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        android.window.TaskFragmentOperation taskFragmentOperation = hierarchyOp.getTaskFragmentOperation();
        com.android.server.wm.TaskFragment taskFragment = this.mLaunchTaskFragments.get(hierarchyOp.getContainer());
        if (taskFragmentOperation == null) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, -1, new java.lang.IllegalArgumentException("TaskFragmentOperation must be non-null"));
            return false;
        }
        int opType = taskFragmentOperation.getOpType();
        if (opType == 0) {
            return true;
        }
        if (!validateTaskFragment(taskFragment, opType, iBinder, iTaskFragmentOrganizer)) {
            return false;
        }
        if ((opType == 12 || opType == 13) && !this.mTaskFragmentOrganizerController.isSystemOrganizer(iTaskFragmentOrganizer.asBinder())) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new java.lang.SecurityException("Only a system organizer can perform OP_TYPE_REORDER_TO_BOTTOM_OF_TASK or OP_TYPE_REORDER_TO_TOP_OF_TASK."));
            return false;
        }
        if ((opType == 14 || opType == 15) && !this.mTaskFragmentOrganizerController.isSystemOrganizer(iTaskFragmentOrganizer.asBinder())) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new java.lang.SecurityException("Only a system organizer can perform OP_TYPE_CREATE_TASK_FRAGMENT_DECOR_SURFACE or OP_TYPE_REMOVE_TASK_FRAGMENT_DECOR_SURFACE."));
            return false;
        }
        if (opType == 17 && !this.mTaskFragmentOrganizerController.isSystemOrganizer(iTaskFragmentOrganizer.asBinder())) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new java.lang.SecurityException("Only a system organizer can perform OP_TYPE_SET_MOVE_TO_BOTTOM_IF_CLEAR_WHEN_LAUNCH."));
            return false;
        }
        android.os.IBinder secondaryFragmentToken = taskFragmentOperation.getSecondaryFragmentToken();
        if (secondaryFragmentToken != null && !validateTaskFragment(this.mLaunchTaskFragments.get(secondaryFragmentToken), opType, iBinder, iTaskFragmentOrganizer)) {
            return false;
        }
        return true;
    }

    private boolean validateTaskFragment(@android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, int i, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        if (taskFragment == null || !taskFragment.isAttached()) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new java.lang.IllegalArgumentException("Not allowed to apply operation on invalid fragment tokens opType=" + i));
            return false;
        }
        if (!taskFragment.isEmbeddedTaskFragmentInPip() || (i == 1 && taskFragment.getTopNonFinishingActivity() == null)) {
            return true;
        }
        sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new java.lang.IllegalArgumentException("Not allowed to apply operation on PIP TaskFragment"));
        return false;
    }

    private int waitAsyncStart(final java.util.function.IntSupplier intSupplier) {
        android.os.Handler handler;
        final java.lang.Integer[] numArr = {null};
        if (android.os.Looper.myLooper() == this.mService.mH.getLooper()) {
            handler = this.mService.mWindowManager.mAnimationHandler;
        } else {
            handler = this.mService.mH;
        }
        handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowOrganizerController.this.lambda$waitAsyncStart$14(numArr, intSupplier);
            }
        });
        while (numArr[0] == null) {
            try {
                this.mGlobalLock.wait();
            } catch (java.lang.InterruptedException e) {
            }
        }
        return numArr[0].intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitAsyncStart$14(java.lang.Integer[] numArr, java.util.function.IntSupplier intSupplier) {
        try {
            numArr[0] = java.lang.Integer.valueOf(intSupplier.getAsInt());
        } catch (java.lang.Throwable th) {
            numArr[0] = -96;
            android.util.Slog.w(TAG, th);
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mGlobalLock.notifyAll();
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private int sanitizeAndApplyHierarchyOp(com.android.server.wm.WindowContainer windowContainer, android.window.WindowContainerTransaction.HierarchyOp hierarchyOp) {
        com.android.server.wm.WindowContainer fromBinder;
        com.android.server.wm.Task asTask = windowContainer.asTask();
        if (asTask == null) {
            throw new java.lang.IllegalArgumentException("Invalid container in hierarchy op");
        }
        com.android.server.wm.DisplayContent displayContent = asTask.getDisplayContent();
        if (displayContent == null) {
            android.util.Slog.w(TAG, "Container is no longer attached: " + asTask);
            return 0;
        }
        if (hierarchyOp.isReparent()) {
            if (asTask.isRootTask() || asTask.getParent().asTask().mCreatedByOrganizer) {
                if (hierarchyOp.getNewParent() == null) {
                    fromBinder = displayContent.getDefaultTaskDisplayArea();
                } else {
                    fromBinder = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getNewParent());
                }
                if (fromBinder == null) {
                    android.util.Slog.e(TAG, "Can't resolve parent window from token");
                    return 0;
                }
                if (asTask.getParent() != fromBinder) {
                    if (fromBinder.asTaskDisplayArea() != null) {
                        asTask.reparent(fromBinder.asTaskDisplayArea(), hierarchyOp.getToTop());
                        return 2;
                    }
                    if (fromBinder.asTask() != null) {
                        if (fromBinder.inMultiWindowMode() && asTask.isLeafTask()) {
                            if (fromBinder.inPinnedWindowingMode()) {
                                android.util.Slog.w(TAG, "Can't support moving a task to another PIP window... newParent=" + fromBinder + " task=" + asTask);
                                return 0;
                            }
                            if (!asTask.supportsMultiWindowInDisplayArea(fromBinder.asTask().getDisplayArea())) {
                                android.util.Slog.w(TAG, "Can't support task that doesn't support multi-window mode in multi-window mode... newParent=" + fromBinder + " task=" + asTask);
                                return 0;
                            }
                        }
                        asTask.reparent((com.android.server.wm.Task) fromBinder, hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, false, "sanitizeAndApplyHierarchyOp");
                        return 2;
                    }
                    throw new java.lang.RuntimeException("Can only reparent task to another task or taskDisplayArea, but not " + fromBinder);
                }
                if (fromBinder instanceof com.android.server.wm.TaskDisplayArea) {
                    fromBinder = asTask.getRootTask();
                }
                asTask.getDisplayArea().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, (com.android.server.wm.Task) fromBinder, false);
                return 2;
            }
            throw new java.lang.RuntimeException("Reparenting leaf Tasks is not supported now. " + asTask);
        }
        asTask.getParent().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, asTask, false);
        return 2;
    }

    private boolean isLockTaskModeViolation(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.Task task, boolean z) {
        if (!z || windowContainer == null || task == null) {
            return false;
        }
        com.android.server.wm.LockTaskController lockTaskController = this.mService.getLockTaskController();
        boolean isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(task);
        if (!isLockTaskModeViolation && windowContainer.asTask() != null) {
            isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(windowContainer.asTask());
        }
        if (isLockTaskModeViolation) {
            android.util.Slog.w(TAG, "Can't support the operation since in lock task mode violation.  Task: " + task + " Parent : " + windowContainer);
        }
        return isLockTaskModeViolation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r16v0, types: [com.android.server.wm.WindowOrganizerController] */
    /* JADX WARN: Type inference failed for: r18v0, types: [com.android.server.wm.Transition] */
    private int reparentChildrenTasksHierarchyOp(final android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, @android.annotation.Nullable com.android.server.wm.Transition transition, int i, final boolean z) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        com.android.server.wm.TaskDisplayArea taskDisplayArea2;
        final com.android.server.wm.TaskDisplayArea asTaskDisplayArea;
        com.android.server.wm.TaskDisplayArea fromBinder = hierarchyOp.getContainer() != null ? com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer()) : null;
        com.android.server.wm.WindowContainer fromBinder2 = hierarchyOp.getNewParent() != null ? com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getNewParent()) : null;
        if (fromBinder == null && fromBinder2 == null) {
            throw new java.lang.IllegalArgumentException("reparentChildrenTasksHierarchyOp: " + hierarchyOp);
        }
        if (fromBinder == null) {
            taskDisplayArea = fromBinder2.asTask().getDisplayContent().getDefaultTaskDisplayArea();
            taskDisplayArea2 = fromBinder2;
        } else if (fromBinder2 != null) {
            taskDisplayArea = fromBinder;
            taskDisplayArea2 = fromBinder2;
        } else {
            taskDisplayArea = fromBinder;
            taskDisplayArea2 = fromBinder.asTask().getDisplayContent().getDefaultTaskDisplayArea();
        }
        if (taskDisplayArea == taskDisplayArea2) {
            android.util.Slog.e(TAG, "reparentChildrenTasksHierarchyOp parent not changing: " + hierarchyOp);
            return 0;
        }
        if (!taskDisplayArea.isAttached()) {
            android.util.Slog.e(TAG, "reparentChildrenTasksHierarchyOp currentParent detached=" + taskDisplayArea + " hop=" + hierarchyOp);
            return 0;
        }
        if (!taskDisplayArea2.isAttached()) {
            android.util.Slog.e(TAG, "reparentChildrenTasksHierarchyOp newParent detached=" + taskDisplayArea2 + " hop=" + hierarchyOp);
            return 0;
        }
        if (taskDisplayArea2.inPinnedWindowingMode()) {
            android.util.Slog.e(TAG, "reparentChildrenTasksHierarchyOp newParent in PIP=" + taskDisplayArea2 + " hop=" + hierarchyOp);
            return 0;
        }
        final boolean inMultiWindowMode = taskDisplayArea2.inMultiWindowMode();
        if (taskDisplayArea2.asTask() != null) {
            asTaskDisplayArea = taskDisplayArea2.asTask().getDisplayArea();
        } else {
            asTaskDisplayArea = taskDisplayArea2.asTaskDisplayArea();
        }
        android.util.Slog.i(TAG, "reparentChildrenTasksHierarchyOp currentParent=" + taskDisplayArea + " newParent=" + taskDisplayArea2 + " hop=" + hierarchyOp);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        final com.android.server.wm.TaskDisplayArea taskDisplayArea3 = taskDisplayArea;
        final com.android.server.wm.TaskDisplayArea taskDisplayArea4 = taskDisplayArea2;
        java.util.ArrayList arrayList2 = arrayList;
        taskDisplayArea.forAllTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$reparentChildrenTasksHierarchyOp$15;
                lambda$reparentChildrenTasksHierarchyOp$15 = com.android.server.wm.WindowOrganizerController.this.lambda$reparentChildrenTasksHierarchyOp$15(taskDisplayArea3, inMultiWindowMode, asTaskDisplayArea, hierarchyOp, taskDisplayArea4, z, arrayList, (com.android.server.wm.Task) obj);
                return lambda$reparentChildrenTasksHierarchyOp$15;
            }
        });
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            java.util.ArrayList arrayList3 = arrayList2;
            com.android.server.wm.Task task = (com.android.server.wm.Task) arrayList3.get(i2);
            int windowingMode = task.getWindowingMode();
            if (i >= 0) {
                addToSyncSet(i, task);
            }
            if (transition != 0) {
                transition.collect(task);
            }
            if (taskDisplayArea2 instanceof com.android.server.wm.TaskDisplayArea) {
                task.reparent(taskDisplayArea2, hierarchyOp.getToTop());
            } else {
                task.reparent((com.android.server.wm.Task) taskDisplayArea2, hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, false, "processChildrenTaskReparentHierarchyOp");
            }
            if (windowingMode != task.getWindowingMode()) {
                this.mService.mTaskSupervisor.mRecentTasks.removeCompatibleRecentTask(task);
            }
            i2++;
            arrayList2 = arrayList3;
        }
        if (transition != 0) {
            transition.collect(taskDisplayArea2);
            return 2;
        }
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reparentChildrenTasksHierarchyOp$15(com.android.server.wm.WindowContainer windowContainer, boolean z, com.android.server.wm.TaskDisplayArea taskDisplayArea, android.window.WindowContainerTransaction.HierarchyOp hierarchyOp, com.android.server.wm.WindowContainer windowContainer2, boolean z2, java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        android.util.Slog.i(TAG, " Processing task=" + task);
        if (task.mCreatedByOrganizer || task.getParent() != windowContainer) {
            return false;
        }
        if (z && !task.supportsMultiWindowInDisplayArea(taskDisplayArea)) {
            android.util.Slog.e(TAG, "reparentChildrenTasksHierarchyOp non-resizeable task to multi window, task=" + task);
            return false;
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(hierarchyOp.getActivityTypes()) && !com.android.internal.util.ArrayUtils.contains(hierarchyOp.getActivityTypes(), task.getActivityType())) {
            return false;
        }
        if ((!com.android.internal.util.ArrayUtils.isEmpty(hierarchyOp.getWindowingModes()) && !com.android.internal.util.ArrayUtils.contains(hierarchyOp.getWindowingModes(), task.getWindowingMode())) || isLockTaskModeViolation(windowContainer2, task, z2)) {
            return false;
        }
        if (hierarchyOp.getToTop()) {
            arrayList.add(0, task);
        } else {
            arrayList.add(task);
        }
        return hierarchyOp.getReparentTopOnly() && arrayList.size() == 1;
    }

    private int setAdjacentRootsHierarchyOp(android.window.WindowContainerTransaction.HierarchyOp hierarchyOp) {
        com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
        if (fromBinder == null || !fromBinder.isAttached()) {
            android.util.Slog.e(TAG, "Attempt to operate on unknown or detached container: " + fromBinder);
            return 0;
        }
        com.android.server.wm.TaskFragment asTaskFragment = fromBinder.asTaskFragment();
        com.android.server.wm.WindowContainer fromBinder2 = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getAdjacentRoot());
        if (fromBinder2 == null || !fromBinder2.isAttached()) {
            android.util.Slog.e(TAG, "Attempt to operate on unknown or detached container: " + fromBinder2);
            return 0;
        }
        com.android.server.wm.TaskFragment asTaskFragment2 = fromBinder2.asTaskFragment();
        if (!asTaskFragment.mCreatedByOrganizer || !asTaskFragment2.mCreatedByOrganizer) {
            throw new java.lang.IllegalArgumentException("setAdjacentRootsHierarchyOp: Not created by organizer root1=" + asTaskFragment + " root2=" + asTaskFragment2);
        }
        if (asTaskFragment.getAdjacentTaskFragment() == asTaskFragment2) {
            return 0;
        }
        asTaskFragment.setAdjacentTaskFragment(asTaskFragment2);
        return 2;
    }

    private int clearAdjacentRootsHierarchyOp(android.window.WindowContainerTransaction.HierarchyOp hierarchyOp) {
        com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(hierarchyOp.getContainer());
        if (fromBinder == null || !fromBinder.isAttached()) {
            android.util.Slog.e(TAG, "Attempt to operate on unknown or detached container: " + fromBinder);
            return 0;
        }
        com.android.server.wm.TaskFragment asTaskFragment = fromBinder.asTaskFragment();
        if (!asTaskFragment.mCreatedByOrganizer) {
            throw new java.lang.IllegalArgumentException("clearAdjacentRootsHierarchyOp: Not created by organizer root=" + asTaskFragment);
        }
        if (asTaskFragment.getAdjacentTaskFragment() == null) {
            return 0;
        }
        asTaskFragment.resetAdjacentTaskFragment();
        return 2;
    }

    private void sanitizeWindowContainer(com.android.server.wm.WindowContainer windowContainer) {
        if (!(windowContainer instanceof com.android.server.wm.TaskFragment) && !(windowContainer instanceof com.android.server.wm.DisplayArea)) {
            throw new java.lang.RuntimeException("Invalid token in task fragment or displayArea transaction");
        }
    }

    private int applyWindowContainerChange(com.android.server.wm.WindowContainer windowContainer, android.window.WindowContainerTransaction.Change change, @android.annotation.Nullable android.os.IBinder iBinder) {
        sanitizeWindowContainer(windowContainer);
        if (windowContainer.asDisplayArea() != null) {
            return applyDisplayAreaChanges(windowContainer.asDisplayArea(), change);
        }
        if (windowContainer.asTask() != null) {
            return applyTaskChanges(windowContainer.asTask(), change);
        }
        if (windowContainer.asTaskFragment() != null && windowContainer.asTaskFragment().isEmbedded()) {
            return applyTaskFragmentChanges(windowContainer.asTaskFragment(), change, iBinder);
        }
        return applyChanges(windowContainer, change);
    }

    public android.window.ITaskOrganizerController getTaskOrganizerController() {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getTaskOrganizerController()");
        return this.mTaskOrganizerController;
    }

    public android.window.IDisplayAreaOrganizerController getDisplayAreaOrganizerController() {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getDisplayAreaOrganizerController()");
        return this.mDisplayAreaOrganizerController;
    }

    public android.window.ITaskFragmentOrganizerController getTaskFragmentOrganizerController() {
        return this.mTaskFragmentOrganizerController;
    }

    private com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer(android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncSet = this.mService.mWindowManager.mSyncEngine.prepareSyncSet(this, "Organizer");
        this.mTransactionCallbacksByPendingSyncId.put(java.lang.Integer.valueOf(prepareSyncSet.mSyncId), iWindowContainerTransactionCallback);
        return prepareSyncSet;
    }

    @com.android.internal.annotations.VisibleForTesting
    int startSyncWithOrganizer(android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer = prepareSyncWithOrganizer(iWindowContainerTransactionCallback);
        this.mService.mWindowManager.mSyncEngine.startSyncSet(prepareSyncWithOrganizer);
        return prepareSyncWithOrganizer.mSyncId;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSyncReady(int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 433446585990132440L, 1, null, java.lang.Long.valueOf(i));
        this.mService.mWindowManager.mSyncEngine.setReady(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void addToSyncSet(int i, com.android.server.wm.WindowContainer windowContainer) {
        this.mService.mWindowManager.mSyncEngine.addToSyncSet(i, windowContainer);
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 6552038620140878489L, 1, null, java.lang.Long.valueOf(i));
        try {
            this.mTransactionCallbacksByPendingSyncId.get(java.lang.Integer.valueOf(i)).onTransactionReady(i, transaction);
        } catch (android.os.RemoteException e) {
            transaction.apply();
        }
        this.mTransactionCallbacksByPendingSyncId.remove(java.lang.Integer.valueOf(i));
    }

    public void registerTransitionPlayer(android.window.ITransitionPlayer iTransitionPlayer) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("registerTransitionPlayer()");
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mTransitionController.registerTransitionPlayer(iTransitionPlayer, this.mService.getProcessController(callingPid, callingUid));
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.window.ITransitionMetricsReporter getTransitionMetricsReporter() {
        return this.mTransitionController.mTransitionMetricsReporter;
    }

    public android.os.IBinder getApplyToken() {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getApplyToken()");
        return android.view.SurfaceControl.Transaction.getDefaultApplyToken();
    }

    static boolean configurationsAreEqualForOrganizer(android.content.res.Configuration configuration, @android.annotation.Nullable android.content.res.Configuration configuration2) {
        int i;
        if (configuration2 == null) {
            return false;
        }
        int diff = configuration.diff(configuration2);
        if ((536870912 & diff) != 0) {
            i = (int) configuration.windowConfiguration.diff(configuration2.windowConfiguration, true);
        } else {
            i = 0;
        }
        if ((i & 3) == 0) {
            diff &= -536870913;
        }
        return (CONTROLLABLE_CONFIGS & diff) == 0;
    }

    private void enforceTaskFragmentOrganizerPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        for (java.util.Map.Entry entry : windowContainerTransaction.getChanges().entrySet()) {
            enforceTaskFragmentConfigChangeAllowed(str, com.android.server.wm.WindowContainer.fromBinder((android.os.IBinder) entry.getKey()), (android.window.WindowContainerTransaction.Change) entry.getValue(), iTaskFragmentOrganizer);
        }
        java.util.List hierarchyOps = windowContainerTransaction.getHierarchyOps();
        for (int size = hierarchyOps.size() - 1; size >= 0; size--) {
            android.window.WindowContainerTransaction.HierarchyOp hierarchyOp = (android.window.WindowContainerTransaction.HierarchyOp) hierarchyOps.get(size);
            switch (hierarchyOp.getType()) {
                case 14:
                    break;
                case 17:
                    enforceTaskFragmentOrganized(str, hierarchyOp.getContainer(), iTaskFragmentOrganizer);
                    if (hierarchyOp.getTaskFragmentOperation() != null && hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken() != null) {
                        enforceTaskFragmentOrganized(str, hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken(), iTaskFragmentOrganizer);
                        break;
                    }
                    break;
                default:
                    java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " trying to apply a hierarchy change that is not allowed for TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
                    android.util.Slog.w(TAG, str2);
                    throw new java.lang.SecurityException(str2);
            }
        }
    }

    private void enforceTaskFragmentOrganized(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        java.util.Objects.requireNonNull(iBinder);
        com.android.server.wm.TaskFragment taskFragment = this.mLaunchTaskFragments.get(iBinder);
        if (taskFragment != null && !taskFragment.hasTaskFragmentOrganizer(iTaskFragmentOrganizer)) {
            java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " trying to modify TaskFragment not belonging to the TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
            android.util.Slog.w(TAG, str2);
            throw new java.lang.SecurityException(str2);
        }
    }

    private void enforceTaskFragmentConfigChangeAllowed(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull android.window.WindowContainerTransaction.Change change, @android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        int i;
        int i2;
        int i3;
        if (windowContainer == null) {
            android.util.Slog.e(TAG, "Attempt to operate on task fragment that no longer exists");
            return;
        }
        com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment == null || !asTaskFragment.hasTaskFragmentOrganizer(iTaskFragmentOrganizer)) {
            java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " trying to modify window container not belonging to the TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
            android.util.Slog.w(TAG, str2);
            throw new java.lang.SecurityException(str2);
        }
        int changeMask = change.getChangeMask();
        int configSetMask = change.getConfigSetMask();
        int windowSetMask = change.getWindowSetMask();
        if (!this.mTaskFragmentOrganizerController.isSystemOrganizer(iTaskFragmentOrganizer.asBinder())) {
            i = changeMask;
        } else {
            i = changeMask & (-9) & (-2) & (-129);
        }
        if ((i & 512) != 0 && (536870912 & configSetMask) != 0 && (windowSetMask & 1) != 0) {
            i &= -513;
            i3 = (-536870913) & configSetMask;
            i2 = windowSetMask & (-2);
        } else {
            i2 = windowSetMask;
            i3 = configSetMask;
        }
        if (i == 0 && i3 == 0 && i2 == 0) {
            return;
        }
        java.lang.String str3 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " trying to apply changes of changeMask=" + changeMask + " configSetMask=" + configSetMask + " windowSetMask=" + windowSetMask + " to TaskFragment=" + asTaskFragment + " TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
        android.util.Slog.w(TAG, str3);
        throw new java.lang.SecurityException(str3);
    }

    private void createTaskFragment(@android.annotation.NonNull android.window.TaskFragmentCreationParams taskFragmentCreationParams, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController.CallerInfo callerInfo, @android.annotation.Nullable com.android.server.wm.Transition transition) {
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(taskFragmentCreationParams.getOwnerToken());
        android.window.ITaskFragmentOrganizer asInterface = android.window.ITaskFragmentOrganizer.Stub.asInterface(taskFragmentCreationParams.getOrganizer().asBinder());
        if (this.mLaunchTaskFragments.containsKey(taskFragmentCreationParams.getFragmentToken())) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new java.lang.IllegalArgumentException("TaskFragment token must be unique"));
            return;
        }
        if (forTokenLocked == null || forTokenLocked.getTask() == null) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new java.lang.IllegalArgumentException("Not allowed to operate with invalid ownerToken"));
            return;
        }
        if (!forTokenLocked.isResizeable()) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new java.lang.IllegalArgumentException("Not allowed to operate with non-resizable owner Activity"));
            return;
        }
        com.android.server.wm.Task task = forTokenLocked.getTask();
        if (task.effectiveUid != forTokenLocked.getUid() || task.effectiveUid != callerInfo.mUid) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new java.lang.SecurityException("Not allowed to operate with the ownerToken while the root activity of the target task belong to the different app"));
            return;
        }
        if (task.inPinnedWindowingMode()) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new java.lang.IllegalArgumentException("Not allowed to create TaskFragment in PIP Task"));
            return;
        }
        com.android.server.wm.TaskFragment taskFragment = new com.android.server.wm.TaskFragment(this.mService, taskFragmentCreationParams.getFragmentToken(), true);
        taskFragment.setAllowTransitionWhenEmpty(taskFragmentCreationParams.getAllowTransitionWhenEmpty());
        taskFragment.setTaskFragmentOrganizer(taskFragmentCreationParams.getOrganizer(), forTokenLocked.getUid(), forTokenLocked.info.processName);
        int i = Integer.MAX_VALUE;
        if (taskFragmentCreationParams.getPairedPrimaryFragmentToken() != null) {
            int indexOf = task.mChildren.indexOf(getTaskFragment(taskFragmentCreationParams.getPairedPrimaryFragmentToken()));
            if (indexOf != -1) {
                i = indexOf + 1;
            }
        } else if (taskFragmentCreationParams.getPairedActivityToken() != null) {
            int indexOf2 = task.mChildren.indexOf(com.android.server.wm.ActivityRecord.forTokenLocked(taskFragmentCreationParams.getPairedActivityToken()));
            if (indexOf2 != -1) {
                i = indexOf2 + 1;
            }
        }
        task.addChild(taskFragment, i);
        taskFragment.setWindowingMode(taskFragmentCreationParams.getWindowingMode());
        if (!taskFragmentCreationParams.getInitialRelativeBounds().isEmpty()) {
            if (transition != null) {
                addToSyncSet(transition.getSyncId(), taskFragment);
            }
            taskFragment.setRelativeEmbeddedBounds(taskFragmentCreationParams.getInitialRelativeBounds());
            taskFragment.recomputeConfiguration();
        }
        this.mLaunchTaskFragments.put(taskFragmentCreationParams.getFragmentToken(), taskFragment);
        if (transition != null) {
            transition.collectExistenceChange(taskFragment);
        }
    }

    private int deleteTaskFragment(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, @android.annotation.Nullable com.android.server.wm.Transition transition) {
        if (transition != null) {
            transition.collectExistenceChange(taskFragment);
        }
        this.mLaunchTaskFragments.remove(taskFragment.getFragmentToken());
        taskFragment.remove(true, "deleteTaskFragment");
        return 2;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getTaskFragment(android.os.IBinder iBinder) {
        return this.mLaunchTaskFragments.get(iBinder);
    }

    void cleanUpEmbeddedTaskFragment(com.android.server.wm.TaskFragment taskFragment) {
        this.mLaunchTaskFragments.remove(taskFragment.getFragmentToken());
    }

    static class CallerInfo {
        final int mPid = android.os.Binder.getCallingPid();
        final int mUid = android.os.Binder.getCallingUid();

        CallerInfo() {
        }
    }

    void sendTaskFragmentOperationFailure(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, int i, @android.annotation.NonNull java.lang.Throwable th) {
        if (iTaskFragmentOrganizer == null) {
            throw new java.lang.IllegalArgumentException("Not allowed to operate with invalid organizer");
        }
        this.mService.mTaskFragmentOrganizerController.onTaskFragmentError(iTaskFragmentOrganizer, iBinder, taskFragment, i, th);
    }

    private java.lang.Throwable convertStartFailureToThrowable(int i, android.content.Intent intent) {
        switch (i) {
            case -96:
                return new android.util.AndroidRuntimeException("Activity could not be started for " + intent + " with error code : " + i);
            case -95:
            case -93:
            default:
                return new android.util.AndroidRuntimeException("Start activity failed with error code : " + i + " when starting " + intent);
            case -94:
                return new java.lang.SecurityException("Permission denied and not allowed to start activity " + intent);
            case -92:
            case -91:
                return new android.content.ActivityNotFoundException("No Activity found to handle " + intent);
        }
    }
}
