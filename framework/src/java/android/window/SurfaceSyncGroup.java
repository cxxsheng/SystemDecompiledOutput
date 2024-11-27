package android.window;

/* loaded from: classes4.dex */
public final class SurfaceSyncGroup {
    private static final boolean DEBUG = false;
    private static final int MAX_COUNT = 100;
    private static final java.lang.String TAG = "SurfaceSyncGroup";
    private static android.os.HandlerThread sHandlerThread;
    private java.lang.Runnable mAddedToSyncListener;
    private boolean mFinished;
    private android.os.Handler mHandler;
    private boolean mHasWMSync;
    public final android.window.ISurfaceSyncGroup mISurfaceSyncGroup;
    private final java.lang.Object mLock;
    private final java.lang.String mName;
    private android.window.ISurfaceSyncGroup mParentSyncGroup;
    private final android.util.ArraySet<android.window.ITransactionReadyCallback> mPendingSyncs;
    private android.window.ISurfaceSyncGroupCompletedListener mSurfaceSyncGroupCompletedListener;
    private final android.util.ArraySet<android.util.Pair<java.util.concurrent.Executor, java.lang.Runnable>> mSyncCompleteCallbacks;
    private boolean mSyncReady;
    private boolean mTimeoutAdded;
    private boolean mTimeoutDisabled;
    private final android.os.Binder mToken;
    private final java.lang.String mTrackName;
    private final android.view.SurfaceControl.Transaction mTransaction;
    private java.util.function.Consumer<android.view.SurfaceControl.Transaction> mTransactionReadyConsumer;
    private static final java.util.concurrent.atomic.AtomicInteger sCounter = new java.util.concurrent.atomic.AtomicInteger(0);
    public static final int TRANSACTION_READY_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 1000;
    private static java.util.function.Supplier<android.view.SurfaceControl.Transaction> sTransactionFactory = new android.view.InsetsController$$ExternalSyntheticLambda12();
    private static final java.lang.Object sHandlerThreadLock = new java.lang.Object();

    public interface SurfaceViewFrameCallback {
        void onFrameStarted();
    }

    private static boolean isLocalBinder(android.os.IBinder iBinder) {
        return !(iBinder instanceof android.os.BinderProxy);
    }

    private static android.window.SurfaceSyncGroup getSurfaceSyncGroup(android.window.ISurfaceSyncGroup iSurfaceSyncGroup) {
        if (iSurfaceSyncGroup instanceof android.window.SurfaceSyncGroup.ISurfaceSyncGroupImpl) {
            return ((android.window.SurfaceSyncGroup.ISurfaceSyncGroupImpl) iSurfaceSyncGroup).getSurfaceSyncGroup();
        }
        return null;
    }

    public static void setTransactionFactory(java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier) {
        sTransactionFactory = supplier;
    }

    public SurfaceSyncGroup(java.lang.String str) {
        this(str, new java.util.function.Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.window.SurfaceSyncGroup.lambda$new$0((android.view.SurfaceControl.Transaction) obj);
            }
        });
    }

    static /* synthetic */ void lambda$new$0(android.view.SurfaceControl.Transaction transaction) {
        if (transaction != null) {
            transaction.apply();
        }
    }

    public SurfaceSyncGroup(java.lang.String str, final java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
        this.mLock = new java.lang.Object();
        this.mPendingSyncs = new android.util.ArraySet<>();
        this.mTransaction = sTransactionFactory.get();
        this.mSyncCompleteCallbacks = new android.util.ArraySet<>();
        this.mISurfaceSyncGroup = new android.window.SurfaceSyncGroup.ISurfaceSyncGroupImpl();
        this.mToken = new android.os.Binder();
        if (sCounter.get() >= 100) {
            sCounter.set(0);
        }
        this.mName = str + "#" + sCounter.getAndIncrement();
        this.mTrackName = "SurfaceSyncGroup " + str;
        this.mTransactionReadyConsumer = new java.util.function.Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.window.SurfaceSyncGroup.this.lambda$new$1(consumer, (android.view.SurfaceControl.Transaction) obj);
            }
        };
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, this.mName, hashCode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(java.util.function.Consumer consumer, android.view.SurfaceControl.Transaction transaction) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instantForTrack(8L, this.mTrackName, "Final TransactionCallback with " + transaction);
        }
        android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        consumer.accept(transaction);
        synchronized (this.mLock) {
            if (this.mSurfaceSyncGroupCompletedListener == null) {
                invokeSyncCompleteCallbacks();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeSyncCompleteCallbacks() {
        this.mSyncCompleteCallbacks.forEach(new java.util.function.Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((java.util.concurrent.Executor) r1.first).execute((java.lang.Runnable) ((android.util.Pair) obj).second);
            }
        });
    }

    public void addSyncCompleteCallback(java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mFinished) {
                executor.execute(runnable);
            } else {
                this.mSyncCompleteCallbacks.add(new android.util.Pair<>(executor, runnable));
            }
        }
    }

    public void markSyncReady() {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instantForTrack(8L, this.mTrackName, "markSyncReady");
        }
        synchronized (this.mLock) {
            if (this.mHasWMSync) {
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().markSurfaceSyncGroupReady(this.mToken);
                } catch (android.os.RemoteException e) {
                }
            }
            this.mSyncReady = true;
            checkIfSyncIsComplete();
        }
    }

    public boolean add(final android.view.SurfaceView surfaceView, java.util.function.Consumer<android.window.SurfaceSyncGroup.SurfaceViewFrameCallback> consumer) {
        final android.window.SurfaceSyncGroup surfaceSyncGroup = new android.window.SurfaceSyncGroup(surfaceView.getName());
        if (!add(surfaceSyncGroup.mISurfaceSyncGroup, false, null)) {
            return false;
        }
        consumer.accept(new android.window.SurfaceSyncGroup.SurfaceViewFrameCallback() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda0
            @Override // android.window.SurfaceSyncGroup.SurfaceViewFrameCallback
            public final void onFrameStarted() {
                android.view.SurfaceView.this.syncNextFrame(new java.util.function.Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.window.SurfaceSyncGroup.lambda$add$3(android.window.SurfaceSyncGroup.this, (android.view.SurfaceControl.Transaction) obj);
                    }
                });
            }
        });
        return true;
    }

    static /* synthetic */ void lambda$add$3(android.window.SurfaceSyncGroup surfaceSyncGroup, android.view.SurfaceControl.Transaction transaction) {
        surfaceSyncGroup.addTransaction(transaction);
        surfaceSyncGroup.markSyncReady();
    }

    public boolean add(android.view.AttachedSurfaceControl attachedSurfaceControl, java.lang.Runnable runnable) {
        android.window.SurfaceSyncGroup orCreateSurfaceSyncGroup;
        if (attachedSurfaceControl == null || (orCreateSurfaceSyncGroup = attachedSurfaceControl.getOrCreateSurfaceSyncGroup()) == null) {
            return false;
        }
        return add(orCreateSurfaceSyncGroup, runnable);
    }

    public boolean add(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage, java.lang.Runnable runnable) {
        try {
            android.window.ISurfaceSyncGroup surfaceSyncGroup = surfacePackage.getRemoteInterface().getSurfaceSyncGroup();
            if (surfaceSyncGroup == null) {
                android.util.Log.e(TAG, "Failed to add SurfaceControlViewHost to SurfaceSyncGroup. SCVH returned null SurfaceSyncGroup");
                return false;
            }
            return add(surfaceSyncGroup, false, runnable);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to add SurfaceControlViewHost to SurfaceSyncGroup");
            return false;
        }
    }

    public boolean add(android.window.SurfaceSyncGroup surfaceSyncGroup, java.lang.Runnable runnable) {
        return add(surfaceSyncGroup.mISurfaceSyncGroup, false, runnable);
    }

    public boolean add(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z, java.lang.Runnable runnable) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addToSync token=" + this.mToken.hashCode(), hashCode());
        }
        synchronized (this.mLock) {
            if (this.mSyncReady) {
                android.util.Log.w(TAG, "Trying to add to sync when already marked as ready " + this.mName);
                if (android.os.Trace.isTagEnabled(8L)) {
                    android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return false;
            }
            if (runnable != null) {
                runnable.run();
            }
            if (isLocalBinder(iSurfaceSyncGroup.asBinder())) {
                boolean addLocalSync = addLocalSync(iSurfaceSyncGroup, z);
                if (android.os.Trace.isTagEnabled(8L)) {
                    android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return addLocalSync;
            }
            synchronized (this.mLock) {
                if (!this.mHasWMSync) {
                    this.mSurfaceSyncGroupCompletedListener = new android.window.ISurfaceSyncGroupCompletedListener.Stub() { // from class: android.window.SurfaceSyncGroup.1
                        @Override // android.window.ISurfaceSyncGroupCompletedListener
                        public void onSurfaceSyncGroupComplete() {
                            synchronized (android.window.SurfaceSyncGroup.this.mLock) {
                                android.window.SurfaceSyncGroup.this.invokeSyncCompleteCallbacks();
                            }
                        }
                    };
                    if (!addSyncToWm(this.mToken, false, this.mSurfaceSyncGroupCompletedListener)) {
                        this.mSurfaceSyncGroupCompletedListener = null;
                        if (android.os.Trace.isTagEnabled(8L)) {
                            android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                        }
                        return false;
                    }
                    this.mHasWMSync = true;
                }
                try {
                    iSurfaceSyncGroup.onAddedToSyncGroup(this.mToken, z);
                    if (android.os.Trace.isTagEnabled(8L)) {
                        android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                    }
                    return true;
                } catch (android.os.RemoteException e) {
                    if (android.os.Trace.isTagEnabled(8L)) {
                        android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                    }
                    return false;
                }
            }
        }
    }

    public void addTransaction(android.view.SurfaceControl.Transaction transaction) {
        synchronized (this.mLock) {
            if (this.mFinished) {
                android.util.Log.w(TAG, "Adding transaction to a completed SurfaceSyncGroup(" + this.mName + ").  Applying immediately");
                transaction.apply();
            } else {
                this.mTransaction.merge(transaction);
            }
        }
    }

    public void setAddedToSyncListener(java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            this.mAddedToSyncListener = runnable;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addSyncToWm(android.os.IBinder iBinder, boolean z, android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener) {
        try {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addSyncToWm=" + iBinder.hashCode(), hashCode());
            }
            android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new android.window.AddToSurfaceSyncGroupResult();
            if (!android.view.WindowManagerGlobal.getWindowManagerService().addToSurfaceSyncGroup(iBinder, z, iSurfaceSyncGroupCompletedListener, addToSurfaceSyncGroupResult)) {
                if (android.os.Trace.isTagEnabled(8L)) {
                    android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return false;
            }
            setTransactionCallbackFromParent(addToSurfaceSyncGroupResult.mParentSyncGroup, addToSurfaceSyncGroupResult.mTransactionReadyCallback);
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                return true;
            }
            return true;
        } catch (android.os.RemoteException e) {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            }
            return false;
        }
    }

    private boolean addLocalSync(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) {
        android.window.SurfaceSyncGroup surfaceSyncGroup = getSurfaceSyncGroup(iSurfaceSyncGroup);
        if (surfaceSyncGroup == null) {
            android.util.Log.e(TAG, "Trying to add a local sync that's either not valid or not from the local process=" + iSurfaceSyncGroup);
            return false;
        }
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addLocalSync=" + surfaceSyncGroup.mName, hashCode());
        }
        android.window.ITransactionReadyCallback createTransactionReadyCallback = createTransactionReadyCallback(z);
        if (createTransactionReadyCallback == null) {
            return false;
        }
        surfaceSyncGroup.setTransactionCallbackFromParent(this.mISurfaceSyncGroup, createTransactionReadyCallback);
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            return true;
        }
        return true;
    }

    private void setTransactionCallbackFromParent(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, final android.window.ITransactionReadyCallback iTransactionReadyCallback) {
        boolean z;
        java.lang.Runnable runnable;
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "setTransactionCallbackFromParent " + this.mName + " callback=" + iTransactionReadyCallback.hashCode(), hashCode());
        }
        addTimeout();
        synchronized (this.mLock) {
            z = true;
            if (this.mFinished) {
                runnable = null;
            } else {
                if (this.mParentSyncGroup != null && this.mParentSyncGroup != iSurfaceSyncGroup) {
                    try {
                        iSurfaceSyncGroup.addToSync(this.mParentSyncGroup, true);
                    } catch (android.os.RemoteException e) {
                    }
                }
                final java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer = this.mTransactionReadyConsumer;
                this.mParentSyncGroup = iSurfaceSyncGroup;
                this.mTransactionReadyConsumer = new java.util.function.Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.window.SurfaceSyncGroup.this.lambda$setTransactionCallbackFromParent$5(iTransactionReadyCallback, consumer, (android.view.SurfaceControl.Transaction) obj);
                    }
                };
                runnable = this.mAddedToSyncListener;
                z = false;
            }
        }
        if (z) {
            try {
                iTransactionReadyCallback.onTransactionReady(null);
            } catch (android.os.RemoteException e2) {
            }
        } else if (runnable != null) {
            runnable.run();
        }
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTransactionCallbackFromParent$5(android.window.ITransactionReadyCallback iTransactionReadyCallback, java.util.function.Consumer consumer, android.view.SurfaceControl.Transaction transaction) {
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "Invoke transactionReadyCallback=" + iTransactionReadyCallback.hashCode(), hashCode());
        }
        consumer.accept(null);
        try {
            iTransactionReadyCallback.onTransactionReady(transaction);
        } catch (android.os.RemoteException e) {
            transaction.apply();
        }
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    public java.lang.String getName() {
        return this.mName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIfSyncIsComplete() {
        if (this.mFinished) {
            this.mTransaction.apply();
            return;
        }
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.instantForTrack(8L, this.mTrackName, "checkIfSyncIsComplete mSyncReady=" + this.mSyncReady + " mPendingSyncs=" + this.mPendingSyncs.size());
        }
        if (!this.mSyncReady || !this.mPendingSyncs.isEmpty()) {
            return;
        }
        this.mTransactionReadyConsumer.accept(this.mTransaction);
        this.mFinished = true;
        if (this.mTimeoutAdded) {
            this.mHandler.removeCallbacksAndMessages(this);
        }
    }

    public android.window.ITransactionReadyCallback createTransactionReadyCallback(final boolean z) {
        android.window.ITransactionReadyCallback.Stub stub = new android.window.ITransactionReadyCallback.Stub() { // from class: android.window.SurfaceSyncGroup.2
            @Override // android.window.ITransactionReadyCallback
            public void onTransactionReady(android.view.SurfaceControl.Transaction transaction) {
                synchronized (android.window.SurfaceSyncGroup.this.mLock) {
                    if (transaction != null) {
                        transaction.sanitize(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
                        if (z) {
                            transaction.merge(android.window.SurfaceSyncGroup.this.mTransaction);
                        }
                        android.window.SurfaceSyncGroup.this.mTransaction.merge(transaction);
                    }
                    android.window.SurfaceSyncGroup.this.mPendingSyncs.remove(this);
                    if (android.os.Trace.isTagEnabled(8L)) {
                        android.os.Trace.instantForTrack(8L, android.window.SurfaceSyncGroup.this.mTrackName, "onTransactionReady callback=" + hashCode());
                    }
                    android.window.SurfaceSyncGroup.this.checkIfSyncIsComplete();
                }
            }
        };
        synchronized (this.mLock) {
            if (this.mSyncReady) {
                android.util.Log.e(TAG, "Sync " + this.mName + " was already marked as ready. No more SurfaceSyncGroups can be added.");
                return null;
            }
            this.mPendingSyncs.add(stub);
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.instantForTrack(8L, this.mTrackName, "createTransactionReadyCallback mPendingSyncs=" + this.mPendingSyncs.size() + " transactionReady=" + stub.hashCode());
            }
            addTimeout();
            return stub;
        }
    }

    private class ISurfaceSyncGroupImpl extends android.window.ISurfaceSyncGroup.Stub {
        private ISurfaceSyncGroupImpl() {
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean onAddedToSyncGroup(android.os.IBinder iBinder, boolean z) {
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.asyncTraceForTrackBegin(8L, android.window.SurfaceSyncGroup.this.mTrackName, "onAddedToSyncGroup token=" + iBinder.hashCode(), hashCode());
            }
            boolean addSyncToWm = android.window.SurfaceSyncGroup.this.addSyncToWm(iBinder, z, null);
            if (android.os.Trace.isTagEnabled(8L)) {
                android.os.Trace.asyncTraceForTrackEnd(8L, android.window.SurfaceSyncGroup.this.mTrackName, hashCode());
            }
            return addSyncToWm;
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean addToSync(android.window.ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) {
            return android.window.SurfaceSyncGroup.this.add(iSurfaceSyncGroup, z, null);
        }

        android.window.SurfaceSyncGroup getSurfaceSyncGroup() {
            return android.window.SurfaceSyncGroup.this;
        }
    }

    public void toggleTimeout(boolean z) {
        synchronized (this.mLock) {
            this.mTimeoutDisabled = !z;
            if (this.mTimeoutAdded && !z) {
                this.mHandler.removeCallbacksAndMessages(this);
                this.mTimeoutAdded = false;
            } else if (!this.mTimeoutAdded && z) {
                addTimeout();
            }
        }
    }

    private void addTimeout() {
        android.os.Looper looper;
        synchronized (sHandlerThreadLock) {
            if (sHandlerThread == null) {
                sHandlerThread = new android.os.HandlerThread("SurfaceSyncGroupTimer");
                sHandlerThread.start();
            }
            looper = sHandlerThread.getLooper();
        }
        synchronized (this.mLock) {
            if (!this.mTimeoutAdded && !this.mTimeoutDisabled && looper != null) {
                if (this.mHandler == null) {
                    this.mHandler = new android.os.Handler(looper);
                }
                this.mTimeoutAdded = true;
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.window.SurfaceSyncGroup.this.lambda$addTimeout$6();
                    }
                }, this, TRANSACTION_READY_TIMEOUT);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addTimeout$6() {
        android.util.Log.e(TAG, "Failed to receive transaction ready in " + TRANSACTION_READY_TIMEOUT + "ms. Marking SurfaceSyncGroup(" + this.mName + ") as ready");
        synchronized (this.mLock) {
            this.mPendingSyncs.clear();
        }
        markSyncReady();
    }
}
