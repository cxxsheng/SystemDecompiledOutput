package com.android.server.wm;

/* loaded from: classes3.dex */
class BLASTSyncEngine {
    public static final int METHOD_BLAST = 1;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_UNDEFINED = -1;
    private static final java.lang.String TAG = "BLASTSyncEngine";
    private final java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.SyncGroup> mActiveSyncs;
    private final android.os.Handler mHandler;
    private int mNextSyncId;
    private final java.util.ArrayList<java.lang.Runnable> mOnIdleListeners;
    private final java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.PendingSyncSet> mPendingSyncSets;
    private final java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.SyncGroup> mTmpFinishQueue;
    private final java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.SyncGroup> mTmpFringe;
    private final com.android.server.wm.WindowManagerService mWm;

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingSyncSet {
        private java.lang.Runnable mApplySync;
        private java.lang.Runnable mStartSync;

        private PendingSyncSet() {
        }
    }

    interface TransactionReadyListener {
        void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction);

        default void onTransactionCommitTimeout() {
        }
    }

    class SyncGroup {
        private static final java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.SyncGroup> NO_DEPENDENCIES = new java.util.ArrayList<>();

        @android.annotation.NonNull
        java.util.ArrayList<com.android.server.wm.BLASTSyncEngine.SyncGroup> mDependencies;
        boolean mIgnoreIndirectMembers;
        final com.android.server.wm.BLASTSyncEngine.TransactionReadyListener mListener;
        final java.lang.Runnable mOnTimeout;
        private android.view.SurfaceControl.Transaction mOrphanTransaction;
        boolean mReady;
        final android.util.ArraySet<com.android.server.wm.WindowContainer> mRootMembers;
        final int mSyncId;
        int mSyncMethod;
        private java.lang.String mTraceName;

        private SyncGroup(com.android.server.wm.BLASTSyncEngine.TransactionReadyListener transactionReadyListener, int i, java.lang.String str) {
            this.mSyncMethod = 1;
            this.mReady = false;
            this.mRootMembers = new android.util.ArraySet<>();
            this.mOrphanTransaction = null;
            this.mIgnoreIndirectMembers = false;
            this.mDependencies = NO_DEPENDENCIES;
            this.mSyncId = i;
            this.mListener = transactionReadyListener;
            this.mOnTimeout = new java.lang.Runnable() { // from class: com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.BLASTSyncEngine.SyncGroup.this.lambda$new$0();
                }
            };
            if (android.os.Trace.isTagEnabled(32L)) {
                this.mTraceName = str + "SyncGroupReady";
                android.os.Trace.asyncTraceBegin(32L, this.mTraceName, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            android.util.Slog.w(com.android.server.wm.BLASTSyncEngine.TAG, "Sync group " + this.mSyncId + " timeout");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BLASTSyncEngine.this.mWm.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    onTimeout();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @android.annotation.NonNull
        android.view.SurfaceControl.Transaction getOrphanTransaction() {
            if (this.mOrphanTransaction == null) {
                this.mOrphanTransaction = com.android.server.wm.BLASTSyncEngine.this.mWm.mTransactionFactory.get();
            }
            return this.mOrphanTransaction;
        }

        boolean isIgnoring(com.android.server.wm.WindowContainer windowContainer) {
            return this.mIgnoreIndirectMembers && windowContainer.asWindowState() == null && windowContainer.mSyncGroup != this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean tryFinish() {
            if (!this.mReady) {
                return false;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 495867940519492701L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(this.mRootMembers));
            if (!this.mDependencies.isEmpty()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 8452501904614439940L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(this.mDependencies));
                return false;
            }
            for (int size = this.mRootMembers.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowContainer valueAt = this.mRootMembers.valueAt(size);
                if (!valueAt.isSyncFinished(this)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 616739530932040800L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(valueAt));
                    return false;
                }
            }
            finishNow();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void finishNow() {
            if (this.mTraceName != null) {
                android.os.Trace.asyncTraceEnd(32L, this.mTraceName, this.mSyncId);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 6649777898123506907L, 1, null, java.lang.Long.valueOf(this.mSyncId));
            android.view.SurfaceControl.Transaction transaction = com.android.server.wm.BLASTSyncEngine.this.mWm.mTransactionFactory.get();
            if (this.mOrphanTransaction != null) {
                transaction.merge(this.mOrphanTransaction);
            }
            java.util.Iterator<com.android.server.wm.WindowContainer> it = this.mRootMembers.iterator();
            while (it.hasNext()) {
                it.next().finishSync(transaction, this, false);
            }
            android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet = new android.util.ArraySet<>();
            java.util.Iterator<com.android.server.wm.WindowContainer> it2 = this.mRootMembers.iterator();
            while (it2.hasNext()) {
                it2.next().waitForSyncTransactionCommit(arraySet);
            }
            final com.android.server.wm.BLASTSyncEngine.SyncGroup.C1CommitCallback c1CommitCallback = new com.android.server.wm.BLASTSyncEngine.SyncGroup.C1CommitCallback(arraySet, transaction);
            transaction.addTransactionCommittedListener(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.view.SurfaceControl.TransactionCommittedListener() { // from class: com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda0
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    com.android.server.wm.BLASTSyncEngine.SyncGroup.lambda$finishNow$1(com.android.server.wm.BLASTSyncEngine.SyncGroup.C1CommitCallback.this);
                }
            });
            com.android.server.wm.BLASTSyncEngine.this.mHandler.postDelayed(c1CommitCallback, 5000L);
            android.os.Trace.traceBegin(32L, "onTransactionReady");
            this.mListener.onTransactionReady(this.mSyncId, transaction);
            android.os.Trace.traceEnd(32L);
            com.android.server.wm.BLASTSyncEngine.this.mActiveSyncs.remove(this);
            com.android.server.wm.BLASTSyncEngine.this.mHandler.removeCallbacks(this.mOnTimeout);
            if (com.android.server.wm.BLASTSyncEngine.this.mActiveSyncs.size() == 0 && !com.android.server.wm.BLASTSyncEngine.this.mPendingSyncSets.isEmpty()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 4174320302463990554L, 0, null, null);
                final com.android.server.wm.BLASTSyncEngine.PendingSyncSet pendingSyncSet = (com.android.server.wm.BLASTSyncEngine.PendingSyncSet) com.android.server.wm.BLASTSyncEngine.this.mPendingSyncSets.remove(0);
                pendingSyncSet.mStartSync.run();
                if (com.android.server.wm.BLASTSyncEngine.this.mActiveSyncs.size() == 0) {
                    throw new java.lang.IllegalStateException("Pending Sync Set didn't start a sync.");
                }
                com.android.server.wm.BLASTSyncEngine.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.BLASTSyncEngine.SyncGroup.this.lambda$finishNow$2(pendingSyncSet);
                    }
                });
            }
            for (int size = com.android.server.wm.BLASTSyncEngine.this.mOnIdleListeners.size() - 1; size >= 0 && com.android.server.wm.BLASTSyncEngine.this.mActiveSyncs.size() <= 0; size--) {
                ((java.lang.Runnable) com.android.server.wm.BLASTSyncEngine.this.mOnIdleListeners.get(size)).run();
            }
        }

        /* renamed from: com.android.server.wm.BLASTSyncEngine$SyncGroup$1CommitCallback, reason: invalid class name */
        class C1CommitCallback implements java.lang.Runnable {
            boolean ran = false;
            final /* synthetic */ android.view.SurfaceControl.Transaction val$merged;
            final /* synthetic */ android.util.ArraySet val$wcAwaitingCommit;

            C1CommitCallback(android.util.ArraySet arraySet, android.view.SurfaceControl.Transaction transaction) {
                this.val$wcAwaitingCommit = arraySet;
                this.val$merged = transaction;
            }

            public void onCommitted(android.view.SurfaceControl.Transaction transaction) {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BLASTSyncEngine.this.mWm.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (this.ran) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        com.android.server.wm.BLASTSyncEngine.this.mHandler.removeCallbacks(this);
                        this.ran = true;
                        java.util.Iterator it = this.val$wcAwaitingCommit.iterator();
                        while (it.hasNext()) {
                            ((com.android.server.wm.WindowContainer) it.next()).onSyncTransactionCommitted(transaction);
                        }
                        transaction.apply();
                        this.val$wcAwaitingCommit.clear();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                android.os.Trace.traceBegin(32L, "onTransactionCommitTimeout");
                android.util.Slog.e(com.android.server.wm.BLASTSyncEngine.TAG, "WM sent Transaction to organized, but never received commit callback. Application ANR likely to follow.");
                android.os.Trace.traceEnd(32L);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BLASTSyncEngine.this.mWm.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.BLASTSyncEngine.SyncGroup.this.mListener.onTransactionCommitTimeout();
                        onCommitted(this.val$merged.mNativeObject != 0 ? this.val$merged : com.android.server.wm.BLASTSyncEngine.this.mWm.mTransactionFactory.get());
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$finishNow$1(com.android.server.wm.BLASTSyncEngine.SyncGroup.C1CommitCallback c1CommitCallback) {
            c1CommitCallback.onCommitted(new android.view.SurfaceControl.Transaction());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$finishNow$2(com.android.server.wm.BLASTSyncEngine.PendingSyncSet pendingSyncSet) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BLASTSyncEngine.this.mWm.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    pendingSyncSet.mApplySync.run();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setReady(boolean z) {
            if (this.mReady == z) {
                return false;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 6310906192788668020L, 13, null, java.lang.Long.valueOf(this.mSyncId), java.lang.Boolean.valueOf(z));
            this.mReady = z;
            if (z) {
                com.android.server.wm.BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
                return true;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addToSync(com.android.server.wm.WindowContainer windowContainer) {
            if (this.mRootMembers.contains(windowContainer)) {
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -476337038362199951L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(windowContainer));
            com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = windowContainer.getSyncGroup();
            if (syncGroup != null && syncGroup != this && !syncGroup.isIgnoring(windowContainer)) {
                android.util.Slog.w(com.android.server.wm.BLASTSyncEngine.TAG, "SyncGroup " + this.mSyncId + " conflicts with " + syncGroup.mSyncId + ": Making " + this.mSyncId + " depend on " + syncGroup.mSyncId);
                if (!this.mDependencies.contains(syncGroup)) {
                    if (syncGroup.dependsOn(this)) {
                        android.util.Slog.w(com.android.server.wm.BLASTSyncEngine.TAG, " Detected dependency cycle between " + this.mSyncId + " and " + syncGroup.mSyncId + ": Moving " + windowContainer + " to " + this.mSyncId);
                        if (windowContainer.mSyncGroup == null) {
                            windowContainer.setSyncGroup(this);
                        } else {
                            windowContainer.mSyncGroup.mRootMembers.remove(windowContainer);
                            this.mRootMembers.add(windowContainer);
                            windowContainer.mSyncGroup = this;
                        }
                    } else {
                        if (this.mDependencies == NO_DEPENDENCIES) {
                            this.mDependencies = new java.util.ArrayList<>();
                        }
                        this.mDependencies.add(syncGroup);
                    }
                }
            } else {
                this.mRootMembers.add(windowContainer);
                windowContainer.setSyncGroup(this);
            }
            windowContainer.prepareSync();
            if (this.mReady) {
                com.android.server.wm.BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
            }
        }

        private boolean dependsOn(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
            if (this.mDependencies.isEmpty()) {
                return false;
            }
            java.util.ArrayList arrayList = com.android.server.wm.BLASTSyncEngine.this.mTmpFringe;
            arrayList.clear();
            arrayList.add(this);
            for (int i = 0; i < arrayList.size(); i++) {
                com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup2 = (com.android.server.wm.BLASTSyncEngine.SyncGroup) arrayList.get(i);
                if (syncGroup2 == syncGroup) {
                    arrayList.clear();
                    return true;
                }
                for (int i2 = 0; i2 < syncGroup2.mDependencies.size(); i2++) {
                    if (!arrayList.contains(syncGroup2.mDependencies.get(i2))) {
                        arrayList.add(syncGroup2.mDependencies.get(i2));
                    }
                }
            }
            arrayList.clear();
            return false;
        }

        void onCancelSync(com.android.server.wm.WindowContainer windowContainer) {
            this.mRootMembers.remove(windowContainer);
        }

        private void onTimeout() {
            if (com.android.server.wm.BLASTSyncEngine.this.mActiveSyncs.contains(this)) {
                boolean z = true;
                for (int size = this.mRootMembers.size() - 1; size >= 0; size--) {
                    com.android.server.wm.WindowContainer valueAt = this.mRootMembers.valueAt(size);
                    if (!valueAt.isSyncFinished(this)) {
                        android.util.Slog.i(com.android.server.wm.BLASTSyncEngine.TAG, "Unfinished container: " + valueAt);
                        valueAt.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.wm.BLASTSyncEngine.SyncGroup.lambda$onTimeout$4((com.android.server.wm.ActivityRecord) obj);
                            }
                        });
                        z = false;
                    }
                }
                int size2 = this.mDependencies.size() - 1;
                while (size2 >= 0) {
                    android.util.Slog.i(com.android.server.wm.BLASTSyncEngine.TAG, "Unfinished dependency: " + this.mDependencies.get(size2).mSyncId);
                    size2 += -1;
                    z = false;
                }
                if (z && !this.mReady) {
                    android.util.Slog.w(com.android.server.wm.BLASTSyncEngine.TAG, "Sync group " + this.mSyncId + " timed-out because not ready. If you see this, please file a bug.");
                }
                finishNow();
                com.android.server.wm.BLASTSyncEngine.this.removeFromDependencies(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onTimeout$4(com.android.server.wm.ActivityRecord activityRecord) {
            if (activityRecord.isVisibleRequested()) {
                if (activityRecord.isRelaunching()) {
                    android.util.Slog.i(com.android.server.wm.BLASTSyncEngine.TAG, "  " + activityRecord + " is relaunching");
                }
                activityRecord.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.BLASTSyncEngine.SyncGroup.lambda$onTimeout$3((com.android.server.wm.WindowState) obj);
                    }
                }, true);
                return;
            }
            if (activityRecord.mDisplayContent != null && !activityRecord.mDisplayContent.mUnknownAppVisibilityController.allResolved()) {
                android.util.Slog.i(com.android.server.wm.BLASTSyncEngine.TAG, "  UnknownAppVisibility: " + activityRecord.mDisplayContent.mUnknownAppVisibilityController.getDebugMessage());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onTimeout$3(com.android.server.wm.WindowState windowState) {
            android.util.Slog.i(com.android.server.wm.BLASTSyncEngine.TAG, "  " + windowState + " " + windowState.mWinAnimator.drawStateToString());
        }
    }

    BLASTSyncEngine(com.android.server.wm.WindowManagerService windowManagerService) {
        this(windowManagerService, windowManagerService.mH);
    }

    @com.android.internal.annotations.VisibleForTesting
    BLASTSyncEngine(com.android.server.wm.WindowManagerService windowManagerService, android.os.Handler handler) {
        this.mNextSyncId = 0;
        this.mActiveSyncs = new java.util.ArrayList<>();
        this.mPendingSyncSets = new java.util.ArrayList<>();
        this.mOnIdleListeners = new java.util.ArrayList<>();
        this.mTmpFinishQueue = new java.util.ArrayList<>();
        this.mTmpFringe = new java.util.ArrayList<>();
        this.mWm = windowManagerService;
        this.mHandler = handler;
    }

    com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncSet(com.android.server.wm.BLASTSyncEngine.TransactionReadyListener transactionReadyListener, java.lang.String str) {
        int i = this.mNextSyncId;
        this.mNextSyncId = i + 1;
        return new com.android.server.wm.BLASTSyncEngine.SyncGroup(transactionReadyListener, i, str);
    }

    int startSyncSet(com.android.server.wm.BLASTSyncEngine.TransactionReadyListener transactionReadyListener, long j, java.lang.String str, boolean z) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncSet = prepareSyncSet(transactionReadyListener, str);
        startSyncSet(prepareSyncSet, j, z);
        return prepareSyncSet.mSyncId;
    }

    void startSyncSet(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        startSyncSet(syncGroup, 5000L, false);
    }

    void startSyncSet(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, long j, boolean z) {
        boolean z2 = this.mActiveSyncs.size() > 0;
        if (!z && z2) {
            android.util.Slog.e(TAG, "SyncGroup " + syncGroup.mSyncId + ": Started when there is other active SyncGroup");
        }
        this.mActiveSyncs.add(syncGroup);
        syncGroup.mIgnoreIndirectMembers = z;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -2978812352001196863L, 1, null, java.lang.Long.valueOf(syncGroup.mSyncId), java.lang.String.valueOf((z && z2) ? "(in parallel) " : ""), java.lang.String.valueOf(syncGroup.mListener));
        scheduleTimeout(syncGroup, j);
    }

    @android.annotation.Nullable
    com.android.server.wm.BLASTSyncEngine.SyncGroup getSyncSet(int i) {
        for (int i2 = 0; i2 < this.mActiveSyncs.size(); i2++) {
            if (this.mActiveSyncs.get(i2).mSyncId == i) {
                return this.mActiveSyncs.get(i2);
            }
        }
        return null;
    }

    boolean hasActiveSync() {
        return this.mActiveSyncs.size() != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    void scheduleTimeout(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, long j) {
        this.mHandler.postDelayed(syncGroup.mOnTimeout, j);
    }

    void addToSyncSet(int i, com.android.server.wm.WindowContainer windowContainer) {
        getSyncGroup(i).addToSync(windowContainer);
    }

    void setSyncMethod(int i, int i2) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup(i);
        if (!syncGroup.mRootMembers.isEmpty()) {
            throw new java.lang.IllegalStateException("Not allow to change sync method after adding group member, id=" + i);
        }
        syncGroup.mSyncMethod = i2;
    }

    boolean setReady(int i, boolean z) {
        return getSyncGroup(i).setReady(z);
    }

    void setReady(int i) {
        setReady(i, true);
    }

    boolean isReady(int i) {
        return getSyncGroup(i).mReady;
    }

    void abort(int i) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup(i);
        syncGroup.finishNow();
        removeFromDependencies(syncGroup);
    }

    private com.android.server.wm.BLASTSyncEngine.SyncGroup getSyncGroup(int i) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncSet = getSyncSet(i);
        if (syncSet == null) {
            throw new java.lang.IllegalStateException("SyncGroup is not started yet id=" + i);
        }
        return syncSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromDependencies(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        boolean z = false;
        for (int i = 0; i < this.mActiveSyncs.size(); i++) {
            com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup2 = this.mActiveSyncs.get(i);
            if (syncGroup2.mDependencies.remove(syncGroup) && syncGroup2.mDependencies.isEmpty()) {
                z = true;
            }
        }
        if (z) {
            this.mWm.mWindowPlacerLocked.requestTraversal();
        }
    }

    void onSurfacePlacement() {
        if (this.mActiveSyncs.isEmpty()) {
            return;
        }
        this.mTmpFinishQueue.addAll(this.mActiveSyncs);
        int size = ((this.mActiveSyncs.size() + 1) * this.mActiveSyncs.size()) / 2;
        while (!this.mTmpFinishQueue.isEmpty()) {
            if (size <= 0) {
                android.util.Slog.e(TAG, "Trying to finish more syncs than theoretically possible. This should never happen. Most likely a dependency cycle wasn't detected.");
            }
            size--;
            com.android.server.wm.BLASTSyncEngine.SyncGroup remove = this.mTmpFinishQueue.remove(0);
            int indexOf = this.mActiveSyncs.indexOf(remove);
            if (indexOf >= 0 && remove.tryFinish()) {
                int i = 0;
                for (int i2 = 0; i2 < this.mActiveSyncs.size(); i2++) {
                    com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = this.mActiveSyncs.get(i2);
                    if (syncGroup.mDependencies.remove(remove) && i2 < indexOf && syncGroup.mDependencies.isEmpty()) {
                        this.mTmpFinishQueue.add(i, this.mActiveSyncs.get(i2));
                        i++;
                    }
                }
            }
        }
    }

    void tryFinishForTest(int i) {
        getSyncSet(i).tryFinish();
    }

    void queueSyncSet(@android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull java.lang.Runnable runnable2) {
        com.android.server.wm.BLASTSyncEngine.PendingSyncSet pendingSyncSet = new com.android.server.wm.BLASTSyncEngine.PendingSyncSet();
        pendingSyncSet.mStartSync = runnable;
        pendingSyncSet.mApplySync = runnable2;
        this.mPendingSyncSets.add(pendingSyncSet);
    }

    boolean hasPendingSyncSets() {
        return !this.mPendingSyncSets.isEmpty();
    }

    void addOnIdleListener(java.lang.Runnable runnable) {
        this.mOnIdleListeners.add(runnable);
    }
}
