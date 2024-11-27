package com.android.server.am;

/* loaded from: classes.dex */
class BroadcastQueueModernImpl extends com.android.server.am.BroadcastQueue {
    private static final int MSG_BG_ACTIVITY_START_TIMEOUT = 3;
    private static final int MSG_CHECK_HEALTH = 4;
    private static final int MSG_CHECK_PENDING_COLD_START_VALIDITY = 5;
    private static final int MSG_DELIVERY_TIMEOUT = 2;
    private static final int MSG_DELIVERY_TIMEOUT_SOFT = 8;
    private static final int MSG_PROCESS_FREEZABLE_CHANGED = 6;
    private static final int MSG_UID_STATE_CHANGED = 7;
    private static final int MSG_UPDATE_RUNNING_LIST = 1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final com.android.server.am.BroadcastQueueModernImpl.BroadcastAnrTimer mAnrTimer;
    private final com.android.server.am.BroadcastConstants mBgConstants;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.am.BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerDeferApply;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.am.BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerDeferClear;
    private final com.android.server.am.BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerSkip;
    private final com.android.server.am.BroadcastProcessQueue.BroadcastConsumer mBroadcastConsumerSkipAndCanceled;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mCheckPendingColdStartQueued;
    private final com.android.server.am.BroadcastConstants mConstants;
    private final com.android.server.am.BroadcastConstants mFgConstants;
    private long mLastTestFailureTime;
    private final android.os.Handler.Callback mLocalCallback;
    private final android.os.Handler mLocalHandler;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final java.util.concurrent.atomic.AtomicReference<android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean>> mMatchingRecordsCache;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final android.util.SparseArray<com.android.server.am.BroadcastProcessQueue> mProcessQueues;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final java.util.concurrent.atomic.AtomicReference<android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean>> mRecordsLookupCache;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final java.util.concurrent.atomic.AtomicReference<android.util.ArraySet<com.android.server.am.BroadcastRecord>> mReplacedBroadcastsCache;
    private com.android.server.am.BroadcastProcessQueue mRunnableHead;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final com.android.server.am.BroadcastProcessQueue[] mRunning;

    @com.android.internal.annotations.GuardedBy({"mService"})
    @android.annotation.Nullable
    private com.android.server.am.BroadcastProcessQueue mRunningColdStart;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final android.util.SparseBooleanArray mUidForeground;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private final java.util.ArrayList<android.util.Pair<java.util.function.BooleanSupplier, java.util.concurrent.CountDownLatch>> mWaitingFor;
    private static final java.util.function.Predicate<com.android.server.am.BroadcastProcessQueue> QUEUE_PREDICATE_ANY = new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda6
        @Override // java.util.function.Predicate
        public final boolean test(java.lang.Object obj) {
            boolean lambda$static$8;
            lambda$static$8 = com.android.server.am.BroadcastQueueModernImpl.lambda$static$8((com.android.server.am.BroadcastProcessQueue) obj);
            return lambda$static$8;
        }
    };
    private static final com.android.server.am.BroadcastProcessQueue.BroadcastPredicate BROADCAST_PREDICATE_ANY = new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda7
        @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
        public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
            boolean lambda$static$9;
            lambda$static$9 = com.android.server.am.BroadcastQueueModernImpl.lambda$static$9(broadcastRecord, i);
            return lambda$static$9;
        }
    };

    BroadcastQueueModernImpl(com.android.server.am.ActivityManagerService activityManagerService, android.os.Handler handler, com.android.server.am.BroadcastConstants broadcastConstants, com.android.server.am.BroadcastConstants broadcastConstants2) {
        this(activityManagerService, handler, broadcastConstants, broadcastConstants2, new com.android.server.am.BroadcastSkipPolicy(activityManagerService), new com.android.server.am.BroadcastHistory(broadcastConstants));
    }

    BroadcastQueueModernImpl(com.android.server.am.ActivityManagerService activityManagerService, android.os.Handler handler, com.android.server.am.BroadcastConstants broadcastConstants, com.android.server.am.BroadcastConstants broadcastConstants2, com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy, com.android.server.am.BroadcastHistory broadcastHistory) {
        super(activityManagerService, handler, "modern", broadcastSkipPolicy, broadcastHistory);
        this.mProcessQueues = new android.util.SparseArray<>();
        this.mRunnableHead = null;
        this.mWaitingFor = new java.util.ArrayList<>();
        this.mReplacedBroadcastsCache = new java.util.concurrent.atomic.AtomicReference<>();
        this.mRecordsLookupCache = new java.util.concurrent.atomic.AtomicReference<>();
        this.mMatchingRecordsCache = new java.util.concurrent.atomic.AtomicReference<>();
        this.mUidForeground = new android.util.SparseBooleanArray();
        this.mLocalCallback = new android.os.Handler.Callback() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda15
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.am.BroadcastQueueModernImpl.this.lambda$new$0(message);
                return lambda$new$0;
            }
        };
        this.mBroadcastConsumerSkip = new com.android.server.am.BroadcastProcessQueue.BroadcastConsumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda16
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
            public final void accept(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                com.android.server.am.BroadcastQueueModernImpl.this.lambda$new$10(broadcastRecord, i);
            }
        };
        this.mBroadcastConsumerSkipAndCanceled = new com.android.server.am.BroadcastProcessQueue.BroadcastConsumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda17
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
            public final void accept(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                com.android.server.am.BroadcastQueueModernImpl.this.lambda$new$11(broadcastRecord, i);
            }
        };
        this.mBroadcastConsumerDeferApply = new com.android.server.am.BroadcastProcessQueue.BroadcastConsumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda18
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
            public final void accept(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                com.android.server.am.BroadcastQueueModernImpl.this.lambda$new$12(broadcastRecord, i);
            }
        };
        this.mBroadcastConsumerDeferClear = new com.android.server.am.BroadcastProcessQueue.BroadcastConsumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda19
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
            public final void accept(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                com.android.server.am.BroadcastQueueModernImpl.this.lambda$new$13(broadcastRecord, i);
            }
        };
        java.util.Objects.requireNonNull(broadcastConstants);
        this.mConstants = broadcastConstants;
        java.util.Objects.requireNonNull(broadcastConstants);
        this.mFgConstants = broadcastConstants;
        java.util.Objects.requireNonNull(broadcastConstants2);
        this.mBgConstants = broadcastConstants2;
        this.mLocalHandler = new android.os.Handler(handler.getLooper(), this.mLocalCallback);
        this.mRunning = new com.android.server.am.BroadcastProcessQueue[this.mConstants.getMaxRunningQueues()];
        this.mAnrTimer = new com.android.server.am.BroadcastQueueModernImpl.BroadcastAnrTimer(this.mLocalHandler);
    }

    private void enqueueUpdateRunningList() {
        this.mLocalHandler.removeMessages(1);
        this.mLocalHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(android.os.Message message) {
        switch (message.what) {
            case 1:
                updateRunningList();
                return true;
            case 2:
                deliveryTimeout((com.android.server.am.BroadcastProcessQueue) message.obj);
                return true;
            case 3:
                com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) someArgs.arg1;
                        com.android.server.am.BroadcastRecord broadcastRecord = (com.android.server.am.BroadcastRecord) someArgs.arg2;
                        someArgs.recycle();
                        processRecord.removeBackgroundStartPrivileges(broadcastRecord);
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            case 4:
                checkHealth();
                return true;
            case 5:
                com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        this.mCheckPendingColdStartQueued = false;
                        checkPendingColdStartValidityLocked();
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            case 6:
                com.android.server.am.ActivityManagerService activityManagerService3 = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService3) {
                    try {
                        refreshProcessQueueLocked((com.android.server.am.ProcessRecord) message.obj);
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            case 7:
                int intValue = ((java.lang.Integer) message.obj).intValue();
                int i = message.arg1;
                com.android.server.am.ActivityManagerService activityManagerService4 = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService4) {
                    try {
                        if (i == 2) {
                            this.mUidForeground.put(intValue, true);
                        } else {
                            this.mUidForeground.delete(intValue);
                        }
                        refreshProcessQueuesLocked(intValue);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            case 8:
                com.android.server.am.ActivityManagerService activityManagerService5 = this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService5) {
                    try {
                        deliveryTimeoutSoftLocked((com.android.server.am.BroadcastProcessQueue) message.obj, message.arg1);
                    } finally {
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            default:
                return false;
        }
    }

    private int getRunningSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.mRunning.length; i2++) {
            if (this.mRunning[i2] != null) {
                i++;
            }
        }
        return i;
    }

    private int getRunningUrgentCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.mRunning.length; i2++) {
            if (this.mRunning[i2] != null && this.mRunning[i2].getActive().isUrgent()) {
                i++;
            }
        }
        return i;
    }

    private int getRunningIndexOf(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        for (int i = 0; i < this.mRunning.length; i++) {
            if (this.mRunning[i] == broadcastProcessQueue) {
                return i;
            }
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void updateRunnableList(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        if (getRunningIndexOf(broadcastProcessQueue) >= 0) {
            return;
        }
        broadcastProcessQueue.updateDeferredStates(this.mBroadcastConsumerDeferApply, this.mBroadcastConsumerDeferClear);
        broadcastProcessQueue.updateRunnableAt();
        boolean isRunnable = broadcastProcessQueue.isRunnable();
        boolean z = (broadcastProcessQueue != this.mRunnableHead && broadcastProcessQueue.runnableAtPrev == null && broadcastProcessQueue.runnableAtNext == null) ? false : true;
        if (isRunnable) {
            if (z) {
                boolean z2 = broadcastProcessQueue.runnableAtPrev == null || broadcastProcessQueue.runnableAtPrev.getRunnableAt() <= broadcastProcessQueue.getRunnableAt();
                boolean z3 = broadcastProcessQueue.runnableAtNext == null || broadcastProcessQueue.runnableAtNext.getRunnableAt() >= broadcastProcessQueue.getRunnableAt();
                if (!z2 || !z3) {
                    this.mRunnableHead = com.android.server.am.BroadcastProcessQueue.removeFromRunnableList(this.mRunnableHead, broadcastProcessQueue);
                    this.mRunnableHead = com.android.server.am.BroadcastProcessQueue.insertIntoRunnableList(this.mRunnableHead, broadcastProcessQueue);
                }
            } else {
                this.mRunnableHead = com.android.server.am.BroadcastProcessQueue.insertIntoRunnableList(this.mRunnableHead, broadcastProcessQueue);
            }
        } else if (z) {
            this.mRunnableHead = com.android.server.am.BroadcastProcessQueue.removeFromRunnableList(this.mRunnableHead, broadcastProcessQueue);
        }
        if (broadcastProcessQueue.isEmpty() && !broadcastProcessQueue.isActive() && !broadcastProcessQueue.isProcessWarm()) {
            removeProcessQueue(broadcastProcessQueue.processName, broadcastProcessQueue.uid);
        }
    }

    private void updateRunningList() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                updateRunningListLocked();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b1  */
    @com.android.internal.annotations.GuardedBy({"mService"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateRunningListLocked() {
        boolean scheduleReceiverColdLocked;
        int length = (this.mRunning.length - getRunningSize()) - java.lang.Math.min(getRunningUrgentCount(), this.mConstants.EXTRA_RUNNING_URGENT_PROCESS_QUEUES);
        if (length == 0) {
            return;
        }
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("updateRunningList");
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        boolean z = !this.mWaitingFor.isEmpty();
        this.mLocalHandler.removeMessages(1);
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mRunnableHead;
        boolean z2 = false;
        while (true) {
            if (broadcastProcessQueue == null || length <= 0) {
                break;
            }
            com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2 = broadcastProcessQueue.runnableAtNext;
            long runnableAt = broadcastProcessQueue.getRunnableAt();
            if (broadcastProcessQueue.isRunnable() && (getRunningSize() < this.mConstants.MAX_RUNNING_PROCESS_QUEUES || broadcastProcessQueue.isPendingUrgent())) {
                if (runnableAt > uptimeMillis && !z) {
                    this.mLocalHandler.sendEmptyMessageAtTime(1, runnableAt);
                    break;
                }
                broadcastProcessQueue.clearDeferredStates(this.mBroadcastConsumerDeferClear);
                updateWarmProcess(broadcastProcessQueue);
                boolean isProcessWarm = broadcastProcessQueue.isProcessWarm();
                if (isProcessWarm) {
                    this.mService.mOomAdjuster.unfreezeTemporarily(broadcastProcessQueue.app, 3);
                    if (!broadcastProcessQueue.isProcessWarm()) {
                        enqueueUpdateRunningList();
                    } else {
                        promoteToRunningLocked(broadcastProcessQueue);
                        if (!isProcessWarm) {
                            z2 |= broadcastProcessQueue.runningOomAdjusted;
                            try {
                                scheduleReceiverColdLocked = scheduleReceiverWarmLocked(broadcastProcessQueue);
                            } catch (com.android.server.am.BroadcastRetryException e) {
                                finishOrReEnqueueActiveBroadcast(broadcastProcessQueue);
                                scheduleReceiverColdLocked = true;
                            }
                        } else {
                            scheduleReceiverColdLocked = scheduleReceiverColdLocked(broadcastProcessQueue);
                        }
                        if (scheduleReceiverColdLocked) {
                            demoteFromRunningLocked(broadcastProcessQueue);
                        }
                        length--;
                        broadcastProcessQueue = broadcastProcessQueue2;
                    }
                } else {
                    if (this.mRunningColdStart == null) {
                        this.mRunningColdStart = broadcastProcessQueue;
                    } else if (!isPendingColdStartValid()) {
                        clearInvalidPendingColdStart();
                        this.mRunningColdStart = broadcastProcessQueue;
                    }
                    promoteToRunningLocked(broadcastProcessQueue);
                    if (!isProcessWarm) {
                    }
                    if (scheduleReceiverColdLocked) {
                    }
                    length--;
                    broadcastProcessQueue = broadcastProcessQueue2;
                }
            }
            broadcastProcessQueue = broadcastProcessQueue2;
        }
        if (z2) {
            this.mService.updateOomAdjPendingTargetsLocked(3);
        }
        checkPendingColdStartValidityLocked();
        checkAndRemoveWaitingFor();
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    private boolean isPendingColdStartValid() {
        if (this.mRunningColdStart.app.getPid() > 0) {
            return !this.mRunningColdStart.app.isKilled();
        }
        return this.mRunningColdStart.app.isPendingStart();
    }

    private void clearInvalidPendingColdStart() {
        com.android.server.am.BroadcastQueue.logw("Clearing invalid pending cold start: " + this.mRunningColdStart);
        if (this.mRunningColdStart.wasActiveBroadcastReEnqueued()) {
            finishReceiverActiveLocked(this.mRunningColdStart, 5, "invalid start with re-enqueued broadcast");
        } else {
            this.mRunningColdStart.reEnqueueActiveBroadcast();
        }
        demoteFromRunningLocked(this.mRunningColdStart);
        clearRunningColdStart();
        enqueueUpdateRunningList();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void checkPendingColdStartValidityLocked() {
        if (this.mRunningColdStart == null) {
            return;
        }
        if (isPendingColdStartValid()) {
            if (!this.mCheckPendingColdStartQueued) {
                this.mLocalHandler.sendEmptyMessageDelayed(5, this.mConstants.PENDING_COLD_START_CHECK_INTERVAL_MILLIS);
                this.mCheckPendingColdStartQueued = true;
                return;
            }
            return;
        }
        clearInvalidPendingColdStart();
    }

    private void finishOrReEnqueueActiveBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.isActive(), "isActive");
        if (broadcastProcessQueue.wasActiveBroadcastReEnqueued()) {
            finishReceiverActiveLocked(broadcastProcessQueue, 5, "re-enqueued broadcast delivery failed");
            return;
        }
        com.android.server.am.BroadcastRecord active = broadcastProcessQueue.getActive();
        int activeIndex = broadcastProcessQueue.getActiveIndex();
        setDeliveryState(broadcastProcessQueue, broadcastProcessQueue.app, active, activeIndex, active.receivers.get(activeIndex), 0, "reEnqueueActiveBroadcast");
        broadcastProcessQueue.reEnqueueActiveBroadcast();
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean onApplicationAttachedLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) throws com.android.server.am.BroadcastRetryException {
        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (processQueue != null) {
            setQueueProcess(processQueue, processRecord);
        }
        if (this.mRunningColdStart != null && this.mRunningColdStart == processQueue) {
            this.mRunningColdStart = null;
            notifyStartedRunning(processQueue);
            this.mService.updateOomAdjPendingTargetsLocked(3);
            processQueue.traceProcessEnd();
            processQueue.traceProcessRunningBegin();
            try {
                if (scheduleReceiverWarmLocked(processQueue)) {
                    demoteFromRunningLocked(processQueue);
                }
                enqueueUpdateRunningList();
                return true;
            } catch (com.android.server.am.BroadcastRetryException e) {
                finishOrReEnqueueActiveBroadcast(processQueue);
                demoteFromRunningLocked(processQueue);
                throw e;
            }
        }
        return false;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationTimeoutLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        onApplicationCleanupLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationProblemLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        onApplicationCleanupLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationCleanupLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (this.mRunningColdStart != null && this.mRunningColdStart == processQueue && this.mRunningColdStart.app == processRecord) {
            clearRunningColdStart();
        }
        if (processQueue != null && processQueue.app == processRecord) {
            setQueueProcess(processQueue, null);
            if (processQueue.isActive()) {
                finishReceiverActiveLocked(processQueue, 5, "onApplicationCleanupLocked");
                demoteFromRunningLocked(processQueue);
            }
            if (processQueue.forEachMatchingBroadcast(new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda8
                @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                    boolean lambda$onApplicationCleanupLocked$1;
                    lambda$onApplicationCleanupLocked$1 = com.android.server.am.BroadcastQueueModernImpl.lambda$onApplicationCleanupLocked$1(broadcastRecord, i);
                    return lambda$onApplicationCleanupLocked$1;
                }
            }, this.mBroadcastConsumerSkip, true) || processQueue.isEmpty()) {
                updateRunnableList(processQueue);
                enqueueUpdateRunningList();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onApplicationCleanupLocked$1(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return broadcastRecord.receivers.get(i) instanceof com.android.server.am.BroadcastFilter;
    }

    private void clearRunningColdStart() {
        this.mRunningColdStart.traceProcessEnd();
        this.mRunningColdStart = null;
        enqueueUpdateRunningList();
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onProcessFreezableChangedLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        this.mLocalHandler.removeMessages(6, processRecord);
        this.mLocalHandler.obtainMessage(6, processRecord).sendToTarget();
    }

    @Override // com.android.server.am.BroadcastQueue
    public int getPreferredSchedulingGroupLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (processQueue != null && getRunningIndexOf(processQueue) >= 0) {
            return processQueue.getPreferredSchedulingGroupLocked();
        }
        return Integer.MIN_VALUE;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void enqueueBroadcastLocked(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("enqueueBroadcast");
        broadcastRecord.applySingletonPolicy(this.mService);
        applyDeliveryGroupPolicy(broadcastRecord);
        broadcastRecord.enqueueTime = android.os.SystemClock.uptimeMillis();
        broadcastRecord.enqueueRealTime = android.os.SystemClock.elapsedRealtime();
        broadcastRecord.enqueueClockTime = java.lang.System.currentTimeMillis();
        this.mHistory.onBroadcastEnqueuedLocked(broadcastRecord);
        android.util.ArraySet<com.android.server.am.BroadcastRecord> andSet = this.mReplacedBroadcastsCache.getAndSet(null);
        if (andSet == null) {
            andSet = new android.util.ArraySet<>();
        }
        android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> andSet2 = this.mMatchingRecordsCache.getAndSet(null);
        if (andSet2 == null) {
            andSet2 = new android.util.ArrayMap<>();
        }
        broadcastRecord.setMatchingRecordsCache(andSet2);
        boolean z = false;
        for (int i = 0; i < broadcastRecord.receivers.size(); i++) {
            java.lang.Object obj = broadcastRecord.receivers.get(i);
            com.android.server.am.BroadcastProcessQueue orCreateProcessQueue = getOrCreateProcessQueue(com.android.server.am.BroadcastRecord.getReceiverProcessName(obj), com.android.server.am.BroadcastRecord.getReceiverUid(obj));
            java.lang.String shouldSkipMessage = this.mSkipPolicy.shouldSkipMessage(broadcastRecord, obj);
            if (shouldSkipMessage != null) {
                setDeliveryState(null, null, broadcastRecord, i, obj, 2, "skipped by policy at enqueue: " + shouldSkipMessage);
            } else {
                com.android.server.am.BroadcastRecord enqueueOrReplaceBroadcast = orCreateProcessQueue.enqueueOrReplaceBroadcast(broadcastRecord, i, this.mBroadcastConsumerDeferApply);
                if (enqueueOrReplaceBroadcast != null) {
                    andSet.add(enqueueOrReplaceBroadcast);
                }
                updateRunnableList(orCreateProcessQueue);
                enqueueUpdateRunningList();
                z = true;
            }
        }
        skipAndCancelReplacedBroadcasts(andSet);
        andSet.clear();
        this.mReplacedBroadcastsCache.compareAndSet(null, andSet);
        andSet2.clear();
        broadcastRecord.clearMatchingRecordsCache();
        this.mMatchingRecordsCache.compareAndSet(null, andSet2);
        if (broadcastRecord.receivers.isEmpty() || !z) {
            scheduleResultTo(broadcastRecord);
            notifyFinishBroadcast(broadcastRecord);
        }
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    private void skipAndCancelReplacedBroadcasts(android.util.ArraySet<com.android.server.am.BroadcastRecord> arraySet) {
        for (int i = 0; i < arraySet.size(); i++) {
            com.android.server.am.BroadcastRecord valueAt = arraySet.valueAt(i);
            for (int i2 = 0; i2 < valueAt.receivers.size(); i2++) {
                if (!com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(valueAt.getDeliveryState(i2))) {
                    this.mBroadcastConsumerSkipAndCanceled.accept(valueAt, i2);
                }
            }
        }
    }

    private void applyDeliveryGroupPolicy(@android.annotation.NonNull final com.android.server.am.BroadcastRecord broadcastRecord) {
        com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer;
        final android.os.BundleMerger deliveryGroupExtrasMerger;
        if (this.mService.shouldIgnoreDeliveryGroupPolicy(broadcastRecord.intent.getAction())) {
            return;
        }
        int deliveryGroupPolicy = broadcastRecord.getDeliveryGroupPolicy();
        switch (deliveryGroupPolicy) {
            case 0:
                return;
            case 1:
                broadcastConsumer = this.mBroadcastConsumerSkipAndCanceled;
                break;
            case 2:
                if (broadcastRecord.receivers.size() > 1 || (deliveryGroupExtrasMerger = broadcastRecord.options.getDeliveryGroupExtrasMerger()) == null) {
                    return;
                }
                broadcastConsumer = new com.android.server.am.BroadcastProcessQueue.BroadcastConsumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda20
                    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
                    public final void accept(com.android.server.am.BroadcastRecord broadcastRecord2, int i) {
                        com.android.server.am.BroadcastQueueModernImpl.this.lambda$applyDeliveryGroupPolicy$2(broadcastRecord, deliveryGroupExtrasMerger, broadcastRecord2, i);
                    }
                };
                break;
                break;
            default:
                com.android.server.am.BroadcastQueue.logw("Unknown delivery group policy: " + deliveryGroupPolicy);
                return;
        }
        final android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> recordsLookupCache = getRecordsLookupCache();
        forEachMatchingBroadcast(QUEUE_PREDICATE_ANY, new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda21
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
            public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord2, int i) {
                boolean lambda$applyDeliveryGroupPolicy$3;
                lambda$applyDeliveryGroupPolicy$3 = com.android.server.am.BroadcastQueueModernImpl.this.lambda$applyDeliveryGroupPolicy$3(broadcastRecord, recordsLookupCache, broadcastRecord2, i);
                return lambda$applyDeliveryGroupPolicy$3;
            }
        }, broadcastConsumer, true);
        recordsLookupCache.clear();
        this.mRecordsLookupCache.compareAndSet(null, recordsLookupCache);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyDeliveryGroupPolicy$2(com.android.server.am.BroadcastRecord broadcastRecord, android.os.BundleMerger bundleMerger, com.android.server.am.BroadcastRecord broadcastRecord2, int i) {
        broadcastRecord.intent.mergeExtras(broadcastRecord2.intent, bundleMerger);
        this.mBroadcastConsumerSkipAndCanceled.accept(broadcastRecord2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$applyDeliveryGroupPolicy$3(com.android.server.am.BroadcastRecord broadcastRecord, android.util.ArrayMap arrayMap, com.android.server.am.BroadcastRecord broadcastRecord2, int i) {
        if (com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(broadcastRecord2.getDeliveryState(i)) || broadcastRecord.callingUid != broadcastRecord2.callingUid || broadcastRecord.userId != broadcastRecord2.userId || !broadcastRecord.matchesDeliveryGroup(broadcastRecord2)) {
            return false;
        }
        if (broadcastRecord2.ordered || broadcastRecord2.prioritized) {
            return containsAllReceivers(broadcastRecord, broadcastRecord2, arrayMap);
        }
        if (broadcastRecord2.resultTo != null) {
            if (broadcastRecord2.getDeliveryState(i) == 6) {
                return broadcastRecord.containsReceiver(broadcastRecord2.receivers.get(i));
            }
            return containsAllReceivers(broadcastRecord, broadcastRecord2, arrayMap);
        }
        return broadcastRecord.containsReceiver(broadcastRecord2.receivers.get(i));
    }

    @android.annotation.NonNull
    private android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> getRecordsLookupCache() {
        android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> andSet = this.mRecordsLookupCache.getAndSet(null);
        if (andSet == null) {
            return new android.util.ArrayMap<>();
        }
        return andSet;
    }

    private boolean containsAllReceivers(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord2, @android.annotation.NonNull android.util.ArrayMap<com.android.server.am.BroadcastRecord, java.lang.Boolean> arrayMap) {
        int indexOfKey = arrayMap.indexOfKey(broadcastRecord2);
        if (indexOfKey > 0) {
            return arrayMap.valueAt(indexOfKey).booleanValue();
        }
        boolean containsAllReceivers = broadcastRecord.containsAllReceivers(broadcastRecord2.receivers);
        arrayMap.put(broadcastRecord2, java.lang.Boolean.valueOf(containsAllReceivers));
        return containsAllReceivers;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean scheduleReceiverColdLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        int i;
        com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.isActive(), "isActive");
        broadcastProcessQueue.setActiveViaColdStart(true);
        com.android.server.am.BroadcastRecord active = broadcastProcessQueue.getActive();
        int activeIndex = broadcastProcessQueue.getActiveIndex();
        java.lang.Object obj = active.receivers.get(activeIndex);
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            this.mRunningColdStart = null;
            finishReceiverActiveLocked(broadcastProcessQueue, 2, "BroadcastFilter for cold app");
            return true;
        }
        java.lang.String shouldSkipReceiver = shouldSkipReceiver(broadcastProcessQueue, active, activeIndex);
        if (shouldSkipReceiver != null) {
            this.mRunningColdStart = null;
            finishReceiverActiveLocked(broadcastProcessQueue, 2, shouldSkipReceiver);
            return true;
        }
        android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) obj;
        android.content.pm.ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
        android.content.ComponentName componentName = resolveInfo.activityInfo.getComponentName();
        if ((applicationInfo.flags & 2097152) != 0) {
            broadcastProcessQueue.setActiveWasStopped(true);
        }
        int flags = active.intent.getFlags() | 4;
        com.android.server.am.HostingRecord hostingRecord = new com.android.server.am.HostingRecord("broadcast", componentName, active.intent.getAction(), active.getHostingRecordTriggerType());
        if (active.options != null && active.options.getTemporaryAppAllowlistDuration() > 0) {
            i = 1;
        } else {
            i = 0;
        }
        broadcastProcessQueue.app = this.mService.startProcessLocked(broadcastProcessQueue.processName, applicationInfo, true, flags, hostingRecord, i, (active.intent.getFlags() & 33554432) != 0, false);
        if (broadcastProcessQueue.app != null) {
            return false;
        }
        this.mRunningColdStart = null;
        finishReceiverActiveLocked(broadcastProcessQueue, 5, "startProcessLocked failed");
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean scheduleReceiverWarmLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) throws com.android.server.am.BroadcastRetryException {
        com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.isActive(), "isActive");
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("scheduleReceiverWarmLocked");
        while (broadcastProcessQueue.isActive()) {
            com.android.server.am.BroadcastRecord active = broadcastProcessQueue.getActive();
            int activeIndex = broadcastProcessQueue.getActiveIndex();
            if (active.terminalCount == 0) {
                active.dispatchTime = android.os.SystemClock.uptimeMillis();
                active.dispatchRealTime = android.os.SystemClock.elapsedRealtime();
                active.dispatchClockTime = java.lang.System.currentTimeMillis();
            }
            java.lang.String shouldSkipReceiver = shouldSkipReceiver(broadcastProcessQueue, active, activeIndex);
            if (shouldSkipReceiver == null) {
                if (dispatchReceivers(broadcastProcessQueue, active, activeIndex)) {
                    com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
                    return false;
                }
            } else {
                finishReceiverActiveLocked(broadcastProcessQueue, 2, shouldSkipReceiver);
            }
            if (shouldRetire(broadcastProcessQueue)) {
                break;
            }
            broadcastProcessQueue.makeActiveNextPending();
        }
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
        return true;
    }

    private java.lang.String shouldSkipReceiver(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        int deliveryState = getDeliveryState(broadcastRecord, i);
        com.android.server.am.ProcessRecord processRecord = broadcastProcessQueue.app;
        java.lang.Object obj = broadcastRecord.receivers.get(i);
        if (com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(deliveryState)) {
            return "already terminal state";
        }
        if (processRecord != null && processRecord.isInFullBackup()) {
            return "isInFullBackup";
        }
        java.lang.String shouldSkipMessage = this.mSkipPolicy.shouldSkipMessage(broadcastRecord, obj);
        if (shouldSkipMessage != null) {
            return shouldSkipMessage;
        }
        if (broadcastRecord.getReceiverIntent(obj) == null) {
            return "getReceiverIntent";
        }
        if ((obj instanceof com.android.server.am.BroadcastFilter) && ((com.android.server.am.BroadcastFilter) obj).receiverList.pid != processRecord.getPid()) {
            return "BroadcastFilter for mismatched PID";
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0218  */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.os.Binder, com.android.server.am.BroadcastRecord, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.server.am.ProcessRecord, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.android.server.am.CachedAppOptimizer] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r29v0, types: [com.android.server.am.BroadcastQueue, com.android.server.am.BroadcastQueueModernImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean dispatchReceivers(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) throws com.android.server.am.BroadcastRetryException {
        com.android.server.am.BroadcastRecord broadcastRecord2;
        java.lang.Object obj;
        boolean z;
        boolean z2;
        java.lang.Object obj2;
        android.content.IIntentReceiver iIntentReceiver;
        int i2;
        java.lang.String str;
        android.os.Bundle bundle;
        boolean z3;
        boolean z4;
        int i3;
        int i4;
        ?? r11 = broadcastRecord;
        ?? r12 = broadcastProcessQueue.app;
        java.lang.Object obj3 = r11.receivers.get(i);
        boolean isAssumedDelivered = broadcastRecord.isAssumedDelivered(i);
        if (!this.mService.mProcessesReady || r11.timeoutExempt || isAssumedDelivered) {
            broadcastProcessQueue.setTimeoutScheduled(false);
        } else {
            broadcastProcessQueue.setTimeoutScheduled(true);
            startDeliveryTimeoutLocked(broadcastProcessQueue, (int) (broadcastRecord.isForeground() ? this.mFgConstants.TIMEOUT : this.mBgConstants.TIMEOUT));
        }
        if (r11.mBackgroundStartPrivileges.allowsAny()) {
            r12.addOrUpdateBackgroundStartPrivileges(r11, r11.mBackgroundStartPrivileges);
            long j = broadcastRecord.isForeground() ? this.mFgConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT : this.mBgConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT;
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = r12;
            obtain.arg2 = r11;
            this.mLocalHandler.sendMessageDelayed(android.os.Message.obtain(this.mLocalHandler, 3, obtain), j);
        }
        if (r11.options != null && r11.options.getTemporaryAppAllowlistDuration() > 0) {
            if (r11.options.getTemporaryAppAllowlistType() == 4) {
                this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(r12, 3, r11.options.getTemporaryAppAllowlistDuration());
            } else {
                this.mService.tempAllowlistUidLocked(broadcastProcessQueue.uid, r11.options.getTemporaryAppAllowlistDuration(), r11.options.getTemporaryAppAllowlistReasonCode(), broadcastRecord.toShortString(), r11.options.getTemporaryAppAllowlistType(), r11.callingUid);
            }
        }
        setDeliveryState(broadcastProcessQueue, r12, broadcastRecord, i, obj3, 4, "scheduleReceiverWarmLocked");
        android.content.Intent receiverIntent = r11.getReceiverIntent(obj3);
        android.app.IApplicationThread onewayThread = r12.getOnewayThread();
        if (onewayThread == null) {
            finishReceiverActiveLocked(broadcastProcessQueue, 5, "missing IApplicationThread");
            return false;
        }
        try {
            if (r11.shareIdentity) {
                this.mService.mPackageManagerInt.grantImplicitAccess(r11.userId, r11.intent, android.os.UserHandle.getAppId(r12.uid), r11.callingUid, true);
            }
            broadcastProcessQueue.lastProcessState = r12.mState.getCurProcState();
            try {
                if (obj3 instanceof com.android.server.am.BroadcastFilter) {
                    try {
                        notifyScheduleRegisteredReceiver(r12, r11, (com.android.server.am.BroadcastFilter) obj3);
                        iIntentReceiver = ((com.android.server.am.BroadcastFilter) obj3).receiverList.receiver;
                        i2 = r11.resultCode;
                        str = r11.resultData;
                        bundle = r11.resultExtras;
                        z3 = r11.ordered;
                        z4 = r11.initialSticky;
                        obj = obj3;
                        try {
                            i3 = r11.userId;
                        } catch (android.os.RemoteException e) {
                            e = e;
                            r11 = 1;
                            z2 = false;
                            broadcastRecord2 = broadcastRecord;
                            z = r11;
                            ?? sb = new java.lang.StringBuilder();
                            sb.append("Failed to schedule ");
                            sb.append(broadcastRecord2);
                            sb.append(" to ");
                            obj2 = obj;
                            sb.append(obj2);
                            sb.append(" via ");
                            sb.append(r12);
                            sb.append(": ");
                            sb.append(e);
                            com.android.server.am.BroadcastQueue.logw(sb.toString());
                            r12.killLocked("Can't deliver broadcast", 13, 26, z);
                            if (obj2 instanceof android.content.pm.ResolveInfo) {
                            }
                        }
                    } catch (android.os.RemoteException e2) {
                        e = e2;
                        obj = obj3;
                    }
                    try {
                        int reportedProcState = r12.mState.getReportedProcState();
                        if (r11.shareIdentity) {
                            try {
                                i4 = r11.callingUid;
                            } catch (android.os.RemoteException e3) {
                                e = e3;
                                broadcastRecord2 = r11;
                                z2 = false;
                                z = true;
                            }
                        } else {
                            i4 = -1;
                        }
                        onewayThread.scheduleRegisteredReceiver(iIntentReceiver, receiverIntent, i2, str, bundle, z3, z4, isAssumedDelivered, i3, reportedProcState, i4, r11.shareIdentity ? r11.callerPackage : null);
                        if (!isAssumedDelivered) {
                            return true;
                        }
                        finishReceiverActiveLocked(broadcastProcessQueue, 1, "assuming delivered");
                        return false;
                    } catch (android.os.RemoteException e4) {
                        e = e4;
                        z2 = false;
                        r11 = 1;
                        broadcastRecord2 = broadcastRecord;
                        z = r11;
                        ?? sb2 = new java.lang.StringBuilder();
                        sb2.append("Failed to schedule ");
                        sb2.append(broadcastRecord2);
                        sb2.append(" to ");
                        obj2 = obj;
                        sb2.append(obj2);
                        sb2.append(" via ");
                        sb2.append(r12);
                        sb2.append(": ");
                        sb2.append(e);
                        com.android.server.am.BroadcastQueue.logw(sb2.toString());
                        r12.killLocked("Can't deliver broadcast", 13, 26, z);
                        if (obj2 instanceof android.content.pm.ResolveInfo) {
                        }
                    }
                } else {
                    obj = obj3;
                    z2 = false;
                    broadcastRecord2 = broadcastRecord;
                    z = true;
                    try {
                        notifyScheduleReceiver(r12, broadcastRecord2, (android.content.pm.ResolveInfo) obj);
                        onewayThread.scheduleReceiver(receiverIntent, ((android.content.pm.ResolveInfo) obj).activityInfo, (android.content.res.CompatibilityInfo) null, broadcastRecord2.resultCode, broadcastRecord2.resultData, broadcastRecord2.resultExtras, broadcastRecord2.ordered, isAssumedDelivered, broadcastRecord2.userId, r12.mState.getReportedProcState(), broadcastRecord2.shareIdentity ? broadcastRecord2.callingUid : -1, broadcastRecord2.shareIdentity ? broadcastRecord2.callerPackage : null);
                        return true;
                    } catch (android.os.RemoteException e5) {
                        e = e5;
                    }
                }
            } catch (android.os.RemoteException e6) {
                e = e6;
            }
        } catch (android.os.RemoteException e7) {
            e = e7;
            broadcastRecord2 = r11;
            obj = obj3;
            z = true;
            z2 = false;
        }
        ?? sb22 = new java.lang.StringBuilder();
        sb22.append("Failed to schedule ");
        sb22.append(broadcastRecord2);
        sb22.append(" to ");
        obj2 = obj;
        sb22.append(obj2);
        sb22.append(" via ");
        sb22.append(r12);
        sb22.append(": ");
        sb22.append(e);
        com.android.server.am.BroadcastQueue.logw(sb22.toString());
        r12.killLocked("Can't deliver broadcast", 13, 26, z);
        if (obj2 instanceof android.content.pm.ResolveInfo) {
            cancelDeliveryTimeoutLocked(broadcastProcessQueue);
            throw new com.android.server.am.BroadcastRetryException(e);
        }
        finishReceiverActiveLocked(broadcastProcessQueue, 5, "remote app");
        return z2;
    }

    private void scheduleResultTo(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.resultTo == null) {
            return;
        }
        com.android.server.am.ProcessRecord processRecord = broadcastRecord.resultToApp;
        android.app.IApplicationThread onewayThread = processRecord != null ? processRecord.getOnewayThread() : null;
        if (onewayThread != null) {
            this.mService.mOomAdjuster.unfreezeTemporarily(processRecord, 2);
            if (broadcastRecord.shareIdentity && processRecord.uid != broadcastRecord.callingUid) {
                this.mService.mPackageManagerInt.grantImplicitAccess(broadcastRecord.userId, broadcastRecord.intent, android.os.UserHandle.getAppId(processRecord.uid), broadcastRecord.callingUid, true);
            }
            try {
                onewayThread.scheduleRegisteredReceiver(broadcastRecord.resultTo, broadcastRecord.intent, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, false, broadcastRecord.initialSticky, true, broadcastRecord.userId, processRecord.mState.getReportedProcState(), broadcastRecord.shareIdentity ? broadcastRecord.callingUid : -1, broadcastRecord.shareIdentity ? broadcastRecord.callerPackage : null);
            } catch (android.os.RemoteException e) {
                com.android.server.am.BroadcastQueue.logw("Failed to schedule result of " + broadcastRecord + " via " + processRecord + ": " + e);
                processRecord.killLocked("Can't deliver broadcast", 13, 26, true);
            }
        }
        broadcastRecord.resultTo = null;
    }

    private void startDeliveryTimeoutLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, int i) {
        if (this.mAnrTimer.serviceEnabled()) {
            this.mAnrTimer.start(broadcastProcessQueue, i);
        } else {
            broadcastProcessQueue.lastCpuDelayTime = broadcastProcessQueue.app.getCpuDelayTime();
            this.mLocalHandler.sendMessageDelayed(android.os.Message.obtain(this.mLocalHandler, 8, i, 0, broadcastProcessQueue), i);
        }
    }

    private void cancelDeliveryTimeoutLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        this.mAnrTimer.cancel(broadcastProcessQueue);
        if (!this.mAnrTimer.serviceEnabled()) {
            this.mLocalHandler.removeMessages(8, broadcastProcessQueue);
        }
    }

    private void deliveryTimeoutSoftLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, int i) {
        if (broadcastProcessQueue.app != null) {
            this.mAnrTimer.start(broadcastProcessQueue, android.util.MathUtils.constrain(broadcastProcessQueue.app.getCpuDelayTime() - broadcastProcessQueue.lastCpuDelayTime, 0L, i));
        } else {
            deliveryTimeoutLocked(broadcastProcessQueue);
        }
    }

    private void deliveryTimeout(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("deliveryTimeout");
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                deliveryTimeoutLocked(broadcastProcessQueue);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    private void deliveryTimeoutLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        finishReceiverActiveLocked(broadcastProcessQueue, 3, "deliveryTimeoutLocked");
        demoteFromRunningLocked(broadcastProcessQueue);
    }

    private class BroadcastAnrTimer extends com.android.server.utils.AnrTimer<com.android.server.am.BroadcastProcessQueue> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        BroadcastAnrTimer(@android.annotation.NonNull android.os.Handler handler) {
            super(handler, 2, "BROADCAST_TIMEOUT", true);
            java.util.Objects.requireNonNull(handler);
        }

        void start(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, long j) {
            start(broadcastProcessQueue, broadcastProcessQueue.app.getPid(), broadcastProcessQueue.app.uid, j);
        }
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean finishReceiverLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle, boolean z, boolean z2) {
        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(processRecord);
        if (processQueue == null || !processQueue.isActive()) {
            com.android.server.am.BroadcastQueue.logw("Ignoring finishReceiverLocked; no active broadcast for " + processQueue);
            return false;
        }
        com.android.server.am.BroadcastRecord active = processQueue.getActive();
        int activeIndex = processQueue.getActiveIndex();
        if (active.ordered) {
            active.resultCode = i;
            active.resultData = str;
            active.resultExtras = bundle;
            if (!active.isNoAbort()) {
                active.resultAbort = z;
            }
        }
        finishReceiverActiveLocked(processQueue, 1, "remote app");
        if (active.resultAbort) {
            for (int i2 = activeIndex + 1; i2 < active.receivers.size(); i2++) {
                setDeliveryState(null, null, active, i2, active.receivers.get(i2), 2, "resultAbort");
            }
        }
        if (shouldRetire(processQueue)) {
            demoteFromRunningLocked(processQueue);
            return true;
        }
        processQueue.makeActiveNextPending();
        try {
            if (!scheduleReceiverWarmLocked(processQueue)) {
                return false;
            }
            demoteFromRunningLocked(processQueue);
            return true;
        } catch (com.android.server.am.BroadcastRetryException e) {
            finishOrReEnqueueActiveBroadcast(processQueue);
            demoteFromRunningLocked(processQueue);
            return true;
        }
    }

    private boolean shouldRetire(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        boolean z;
        if (android.os.UserHandle.isCore(broadcastProcessQueue.uid)) {
            z = broadcastProcessQueue.getActiveCountSinceIdle() - broadcastProcessQueue.getActiveAssumedDeliveryCountSinceIdle() >= this.mConstants.MAX_CORE_RUNNING_BLOCKING_BROADCASTS || broadcastProcessQueue.getActiveAssumedDeliveryCountSinceIdle() >= this.mConstants.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS;
        } else {
            z = broadcastProcessQueue.getActiveCountSinceIdle() >= this.mConstants.MAX_RUNNING_ACTIVE_BROADCASTS;
        }
        return (broadcastProcessQueue.isRunnable() && broadcastProcessQueue.isProcessWarm() && !z) ? false : true;
    }

    private void finishReceiverActiveLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, int i, @android.annotation.NonNull java.lang.String str) {
        if (!broadcastProcessQueue.isActive()) {
            com.android.server.am.BroadcastQueue.logw("Ignoring finishReceiverActiveLocked; no active broadcast for " + broadcastProcessQueue);
            return;
        }
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("finishReceiver");
        com.android.server.am.ProcessRecord processRecord = broadcastProcessQueue.app;
        com.android.server.am.BroadcastRecord active = broadcastProcessQueue.getActive();
        int activeIndex = broadcastProcessQueue.getActiveIndex();
        java.lang.Object obj = active.receivers.get(activeIndex);
        setDeliveryState(broadcastProcessQueue, processRecord, active, activeIndex, obj, i, str);
        if (i == 3) {
            active.anrCount++;
            if (processRecord != null && !processRecord.isDebugging()) {
                this.mAnrTimer.accept(broadcastProcessQueue);
                this.mService.appNotResponding(broadcastProcessQueue.app, com.android.internal.os.TimeoutRecord.forBroadcastReceiver(active.intent, com.android.server.am.BroadcastRecord.getReceiverPackageName(obj), com.android.server.am.BroadcastRecord.getReceiverClassName(obj)));
            } else {
                this.mAnrTimer.discard(broadcastProcessQueue);
            }
        } else if (broadcastProcessQueue.timeoutScheduled()) {
            cancelDeliveryTimeoutLocked(broadcastProcessQueue);
        }
        checkAndRemoveWaitingFor();
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void promoteToRunningLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        int runningIndexOf = getRunningIndexOf(null);
        this.mRunning[runningIndexOf] = broadcastProcessQueue;
        this.mRunnableHead = com.android.server.am.BroadcastProcessQueue.removeFromRunnableList(this.mRunnableHead, broadcastProcessQueue);
        broadcastProcessQueue.runningTraceTrackName = "BroadcastQueue.mRunning[" + runningIndexOf + "]";
        broadcastProcessQueue.runningOomAdjusted = broadcastProcessQueue.isPendingManifest() || broadcastProcessQueue.isPendingOrdered() || broadcastProcessQueue.isPendingResultTo();
        boolean isProcessWarm = broadcastProcessQueue.isProcessWarm();
        if (isProcessWarm) {
            notifyStartedRunning(broadcastProcessQueue);
        }
        broadcastProcessQueue.makeActiveNextPending();
        if (isProcessWarm) {
            broadcastProcessQueue.traceProcessRunningBegin();
        } else {
            broadcastProcessQueue.traceProcessStartingBegin();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void demoteFromRunningLocked(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        if (!broadcastProcessQueue.isActive()) {
            com.android.server.am.BroadcastQueue.logw("Ignoring demoteFromRunning; no active broadcast for " + broadcastProcessQueue);
            return;
        }
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("demoteFromRunning");
        broadcastProcessQueue.makeActiveIdle();
        broadcastProcessQueue.traceProcessEnd();
        this.mRunning[getRunningIndexOf(broadcastProcessQueue)] = null;
        updateRunnableList(broadcastProcessQueue);
        enqueueUpdateRunningList();
        notifyStoppedRunning(broadcastProcessQueue);
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    private void setDeliveryState(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i, @android.annotation.NonNull java.lang.Object obj, int i2, @android.annotation.NonNull java.lang.String str) {
        int traceBegin = com.android.server.am.BroadcastQueue.traceBegin("setDeliveryState");
        int deliveryState = getDeliveryState(broadcastRecord, i);
        boolean deliveryState2 = broadcastRecord.setDeliveryState(i, i2, str);
        if (broadcastProcessQueue != null) {
            if (i2 == 4) {
                broadcastProcessQueue.traceActiveBegin();
            } else if (deliveryState == 4 && com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(i2)) {
                broadcastProcessQueue.traceActiveEnd();
            }
        }
        if (!com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(deliveryState) && com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(i2)) {
            notifyFinishReceiver(broadcastProcessQueue, processRecord, broadcastRecord, i, obj);
        }
        if (deliveryState2) {
            if (broadcastRecord.beyondCount == broadcastRecord.receivers.size()) {
                scheduleResultTo(broadcastRecord);
            }
            if (broadcastRecord.ordered || broadcastRecord.prioritized) {
                for (int i3 = 0; i3 < broadcastRecord.receivers.size(); i3++) {
                    if (!com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(getDeliveryState(broadcastRecord, i3)) || i3 == i) {
                        java.lang.Object obj2 = broadcastRecord.receivers.get(i3);
                        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(com.android.server.am.BroadcastRecord.getReceiverProcessName(obj2), com.android.server.am.BroadcastRecord.getReceiverUid(obj2));
                        if (processQueue != null) {
                            processQueue.invalidateRunnableAt();
                            updateRunnableList(processQueue);
                        }
                    }
                }
                enqueueUpdateRunningList();
            }
        }
        com.android.server.am.BroadcastQueue.traceEnd(traceBegin);
    }

    private int getDeliveryState(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return broadcastRecord.getDeliveryState(i);
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean cleanupDisabledPackageReceiversLocked(@android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.util.Set<java.lang.String> set, final int i) {
        java.util.function.Predicate<com.android.server.am.BroadcastProcessQueue> predicate;
        com.android.server.am.BroadcastProcessQueue.BroadcastPredicate broadcastPredicate;
        if (str != null) {
            final int packageUid = this.mService.mPackageManagerInt.getPackageUid(str, 8192L, i);
            predicate = new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$cleanupDisabledPackageReceiversLocked$4;
                    lambda$cleanupDisabledPackageReceiversLocked$4 = com.android.server.am.BroadcastQueueModernImpl.lambda$cleanupDisabledPackageReceiversLocked$4(packageUid, (com.android.server.am.BroadcastProcessQueue) obj);
                    return lambda$cleanupDisabledPackageReceiversLocked$4;
                }
            };
            if (set != null) {
                broadcastPredicate = new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda3
                    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                    public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i2) {
                        boolean lambda$cleanupDisabledPackageReceiversLocked$5;
                        lambda$cleanupDisabledPackageReceiversLocked$5 = com.android.server.am.BroadcastQueueModernImpl.lambda$cleanupDisabledPackageReceiversLocked$5(str, set, broadcastRecord, i2);
                        return lambda$cleanupDisabledPackageReceiversLocked$5;
                    }
                };
            } else {
                broadcastPredicate = new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda4
                    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                    public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i2) {
                        boolean lambda$cleanupDisabledPackageReceiversLocked$6;
                        lambda$cleanupDisabledPackageReceiversLocked$6 = com.android.server.am.BroadcastQueueModernImpl.lambda$cleanupDisabledPackageReceiversLocked$6(str, broadcastRecord, i2);
                        return lambda$cleanupDisabledPackageReceiversLocked$6;
                    }
                };
            }
        } else {
            predicate = new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$cleanupDisabledPackageReceiversLocked$7;
                    lambda$cleanupDisabledPackageReceiversLocked$7 = com.android.server.am.BroadcastQueueModernImpl.lambda$cleanupDisabledPackageReceiversLocked$7(i, (com.android.server.am.BroadcastProcessQueue) obj);
                    return lambda$cleanupDisabledPackageReceiversLocked$7;
                }
            };
            com.android.server.am.BroadcastProcessQueue.BroadcastPredicate broadcastPredicate2 = BROADCAST_PREDICATE_ANY;
            cleanupUserStateLocked(this.mUidForeground, i);
            broadcastPredicate = broadcastPredicate2;
        }
        return forEachMatchingBroadcast(predicate, broadcastPredicate, this.mBroadcastConsumerSkip, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanupDisabledPackageReceiversLocked$4(int i, com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return broadcastProcessQueue.uid == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanupDisabledPackageReceiversLocked$5(java.lang.String str, java.util.Set set, com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        java.lang.Object obj = broadcastRecord.receivers.get(i);
        if (!(obj instanceof android.content.pm.ResolveInfo)) {
            return false;
        }
        android.content.pm.ActivityInfo activityInfo = ((android.content.pm.ResolveInfo) obj).activityInfo;
        return str.equals(activityInfo.packageName) && set.contains(activityInfo.name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanupDisabledPackageReceiversLocked$6(java.lang.String str, com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return str.equals(com.android.server.am.BroadcastRecord.getReceiverPackageName(broadcastRecord.receivers.get(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanupDisabledPackageReceiversLocked$7(int i, com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return android.os.UserHandle.getUserId(broadcastProcessQueue.uid) == i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void cleanupUserStateLocked(@android.annotation.NonNull android.util.SparseBooleanArray sparseBooleanArray, int i) {
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            if (android.os.UserHandle.getUserId(sparseBooleanArray.keyAt(size)) == i) {
                sparseBooleanArray.removeAt(size);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$8(com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$9(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 2, "mBroadcastConsumerSkip");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$11(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 2, "mBroadcastConsumerSkipAndCanceled");
        broadcastRecord.resultCode = 0;
        broadcastRecord.resultData = null;
        broadcastRecord.resultExtras = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 6, "mBroadcastConsumerDeferApply");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$13(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 0, "mBroadcastConsumerDeferClear");
    }

    private boolean testAllProcessQueues(@android.annotation.NonNull java.util.function.Predicate<com.android.server.am.BroadcastProcessQueue> predicate, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        for (int i = 0; i < this.mProcessQueues.size(); i++) {
            for (com.android.server.am.BroadcastProcessQueue valueAt = this.mProcessQueues.valueAt(i); valueAt != null; valueAt = valueAt.processNameNext) {
                if (!predicate.test(valueAt)) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    if (uptimeMillis > this.mLastTestFailureTime + 1000) {
                        this.mLastTestFailureTime = uptimeMillis;
                        printWriter.println("Test " + str + " failed due to " + valueAt.toShortString() + " " + valueAt.describeStateLocked());
                        printWriter.flush();
                    }
                    return false;
                }
            }
        }
        printWriter.println("Test " + str + " passed");
        printWriter.flush();
        return true;
    }

    private boolean forEachMatchingBroadcast(@android.annotation.NonNull java.util.function.Predicate<com.android.server.am.BroadcastProcessQueue> predicate, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastPredicate broadcastPredicate, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer, boolean z) {
        boolean z2 = false;
        for (int size = this.mProcessQueues.size() - 1; size >= 0; size--) {
            for (com.android.server.am.BroadcastProcessQueue valueAt = this.mProcessQueues.valueAt(size); valueAt != null; valueAt = valueAt.processNameNext) {
                if (predicate.test(valueAt) && valueAt.forEachMatchingBroadcast(broadcastPredicate, broadcastConsumer, z)) {
                    updateRunnableList(valueAt);
                    z2 = true;
                }
            }
        }
        if (z2) {
            enqueueUpdateRunningList();
        }
        return z2;
    }

    private boolean forEachMatchingQueue(@android.annotation.NonNull java.util.function.Predicate<com.android.server.am.BroadcastProcessQueue> predicate, @android.annotation.NonNull java.util.function.Consumer<com.android.server.am.BroadcastProcessQueue> consumer) {
        boolean z = false;
        for (int size = this.mProcessQueues.size() - 1; size >= 0; size--) {
            for (com.android.server.am.BroadcastProcessQueue valueAt = this.mProcessQueues.valueAt(size); valueAt != null; valueAt = valueAt.processNameNext) {
                if (predicate.test(valueAt)) {
                    consumer.accept(valueAt);
                    updateRunnableList(valueAt);
                    z = true;
                }
            }
        }
        if (z) {
            enqueueUpdateRunningList();
        }
        return z;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void start(@android.annotation.NonNull android.content.ContentResolver contentResolver) {
        this.mFgConstants.startObserving(this.mHandler, contentResolver);
        this.mBgConstants.startObserving(this.mHandler, contentResolver);
        this.mService.registerUidObserver(new android.app.UidObserver() { // from class: com.android.server.am.BroadcastQueueModernImpl.1
            public void onUidStateChanged(int i, int i2, long j, int i3) {
                com.android.server.am.BroadcastQueueModernImpl.this.mLocalHandler.removeMessages(7, java.lang.Integer.valueOf(i));
                com.android.server.am.BroadcastQueueModernImpl.this.mLocalHandler.obtainMessage(7, i2, 0, java.lang.Integer.valueOf(i)).sendToTarget();
            }
        }, 1, 2, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        this.mLocalHandler.sendEmptyMessage(4);
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isIdleLocked */
    public boolean lambda$waitForIdle$1() {
        return lambda$waitForIdle$17(com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO);
    }

    /* renamed from: isIdleLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForIdle$17(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        return testAllProcessQueues(new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isIdle;
                isIdle = ((com.android.server.am.BroadcastProcessQueue) obj).isIdle();
                return isIdle;
            }
        }, "idle", printWriter);
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isBeyondBarrierLocked */
    public boolean lambda$waitForBarrier$2(long j) {
        return lambda$waitForBarrier$19(j, com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isBeyondBarrierLocked$15(long j, com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return broadcastProcessQueue.isBeyondBarrierLocked(j);
    }

    /* renamed from: isBeyondBarrierLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForBarrier$19(final long j, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        return testAllProcessQueues(new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isBeyondBarrierLocked$15;
                lambda$isBeyondBarrierLocked$15 = com.android.server.am.BroadcastQueueModernImpl.lambda$isBeyondBarrierLocked$15(j, (com.android.server.am.BroadcastProcessQueue) obj);
                return lambda$isBeyondBarrierLocked$15;
            }
        }, "barrier", printWriter);
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isDispatchedLocked */
    public boolean lambda$waitForDispatched$3(@android.annotation.NonNull android.content.Intent intent) {
        return lambda$waitForDispatched$21(intent, com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isDispatchedLocked$16(android.content.Intent intent, com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return broadcastProcessQueue.isDispatched(intent);
    }

    /* renamed from: isDispatchedLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForDispatched$21(@android.annotation.NonNull final android.content.Intent intent, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        return testAllProcessQueues(new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isDispatchedLocked$16;
                lambda$isDispatchedLocked$16 = com.android.server.am.BroadcastQueueModernImpl.lambda$isDispatchedLocked$16(intent, (com.android.server.am.BroadcastProcessQueue) obj);
                return lambda$isDispatchedLocked$16;
            }
        }, "dispatch of " + intent, printWriter);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForIdle(@android.annotation.NonNull final java.io.PrintWriter printWriter) {
        waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForIdle$17;
                lambda$waitForIdle$17 = com.android.server.am.BroadcastQueueModernImpl.this.lambda$waitForIdle$17(printWriter);
                return lambda$waitForIdle$17;
            }
        });
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForBarrier(@android.annotation.NonNull final java.io.PrintWriter printWriter) {
        final long uptimeMillis = android.os.SystemClock.uptimeMillis();
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forEachMatchingQueue(QUEUE_PREDICATE_ANY, new java.util.function.Consumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda22
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.am.BroadcastProcessQueue) obj).addPrioritizeEarliestRequest();
                    }
                });
            } finally {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        try {
            waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda23
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    boolean lambda$waitForBarrier$19;
                    lambda$waitForBarrier$19 = com.android.server.am.BroadcastQueueModernImpl.this.lambda$waitForBarrier$19(uptimeMillis, printWriter);
                    return lambda$waitForBarrier$19;
                }
            });
            com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService2) {
                try {
                    forEachMatchingQueue(QUEUE_PREDICATE_ANY, new java.util.function.Consumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda24
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.am.BroadcastProcessQueue) obj).removePrioritizeEarliestRequest();
                        }
                    });
                } finally {
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        } catch (java.lang.Throwable th) {
            com.android.server.am.ActivityManagerService activityManagerService3 = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService3) {
                try {
                    forEachMatchingQueue(QUEUE_PREDICATE_ANY, new java.util.function.Consumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda24
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.am.BroadcastProcessQueue) obj).removePrioritizeEarliestRequest();
                        }
                    });
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForDispatched(@android.annotation.NonNull final android.content.Intent intent, @android.annotation.NonNull final java.io.PrintWriter printWriter) {
        waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda10
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForDispatched$21;
                lambda$waitForDispatched$21 = com.android.server.am.BroadcastQueueModernImpl.this.lambda$waitForDispatched$21(intent, printWriter);
                return lambda$waitForDispatched$21;
            }
        });
    }

    private void waitFor(@android.annotation.NonNull java.util.function.BooleanSupplier booleanSupplier) {
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mWaitingFor.add(android.util.Pair.create(booleanSupplier, countDownLatch));
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        enqueueUpdateRunningList();
        try {
            countDownLatch.await();
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void checkAndRemoveWaitingFor() {
        if (!this.mWaitingFor.isEmpty()) {
            this.mWaitingFor.removeIf(new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkAndRemoveWaitingFor$22;
                    lambda$checkAndRemoveWaitingFor$22 = com.android.server.am.BroadcastQueueModernImpl.lambda$checkAndRemoveWaitingFor$22((android.util.Pair) obj);
                    return lambda$checkAndRemoveWaitingFor$22;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkAndRemoveWaitingFor$22(android.util.Pair pair) {
        if (((java.util.function.BooleanSupplier) pair.first).getAsBoolean()) {
            ((java.util.concurrent.CountDownLatch) pair.second).countDown();
            return true;
        }
        return false;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void forceDelayBroadcastDelivery(@android.annotation.NonNull final java.lang.String str, final long j) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forEachMatchingQueue(new java.util.function.Predicate() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$forceDelayBroadcastDelivery$23;
                        lambda$forceDelayBroadcastDelivery$23 = com.android.server.am.BroadcastQueueModernImpl.lambda$forceDelayBroadcastDelivery$23(str, (com.android.server.am.BroadcastProcessQueue) obj);
                        return lambda$forceDelayBroadcastDelivery$23;
                    }
                }, new java.util.function.Consumer() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.am.BroadcastProcessQueue) obj).forceDelayBroadcastDelivery(j);
                    }
                });
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$forceDelayBroadcastDelivery$23(java.lang.String str, com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        return str.equals(broadcastProcessQueue.getPackageName());
    }

    @Override // com.android.server.am.BroadcastQueue
    public java.lang.String describeStateLocked() {
        return getRunningSize() + " running";
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean isDelayBehindServices() {
        return false;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void backgroundServicesFinishedLocked(int i) {
    }

    private void checkHealth() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                checkHealthLocked();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    private void checkHealthLocked() {
        try {
            assertHealthLocked();
            this.mLocalHandler.sendEmptyMessageDelayed(4, 60000L);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(com.android.server.am.BroadcastQueue.TAG, e);
            dumpToDropBoxLocked(e.toString());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void assertHealthLocked() {
        int i;
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mRunnableHead;
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2 = null;
        while (true) {
            if (broadcastProcessQueue == null) {
                break;
            }
            com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.runnableAtPrev == broadcastProcessQueue2, "runnableAtPrev");
            com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.isRunnable(), "isRunnable " + broadcastProcessQueue);
            if (broadcastProcessQueue2 != null) {
                com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue.getRunnableAt() >= broadcastProcessQueue2.getRunnableAt(), "getRunnableAt " + broadcastProcessQueue + " vs " + broadcastProcessQueue2);
            }
            broadcastProcessQueue2 = broadcastProcessQueue;
            broadcastProcessQueue = broadcastProcessQueue.runnableAtNext;
        }
        for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue3 : this.mRunning) {
            if (broadcastProcessQueue3 != null) {
                com.android.server.am.BroadcastQueue.checkState(broadcastProcessQueue3.isActive(), "isActive " + broadcastProcessQueue3);
            }
        }
        if (this.mRunningColdStart != null) {
            com.android.server.am.BroadcastQueue.checkState(getRunningIndexOf(this.mRunningColdStart) >= 0, "isOrphaned " + this.mRunningColdStart);
        }
        for (i = 0; i < this.mProcessQueues.size(); i++) {
            for (com.android.server.am.BroadcastProcessQueue valueAt = this.mProcessQueues.valueAt(i); valueAt != null; valueAt = valueAt.processNameNext) {
                valueAt.assertHealthLocked();
            }
        }
    }

    private void updateWarmProcess(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        if (!broadcastProcessQueue.isProcessWarm()) {
            com.android.server.am.ProcessRecord processRecordLocked = this.mService.getProcessRecordLocked(broadcastProcessQueue.processName, broadcastProcessQueue.uid);
            broadcastProcessQueue.setProcessAndUidState(processRecordLocked, this.mUidForeground.get(broadcastProcessQueue.uid, false), isProcessFreezable(processRecordLocked));
        }
    }

    private void setQueueProcess(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord) {
        if (broadcastProcessQueue.setProcessAndUidState(processRecord, this.mUidForeground.get(broadcastProcessQueue.uid, false), isProcessFreezable(processRecord))) {
            updateRunnableList(broadcastProcessQueue);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean isProcessFreezable(@android.annotation.Nullable com.android.server.am.ProcessRecord processRecord) {
        boolean z = false;
        if (processRecord == null) {
            return false;
        }
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (processRecord.mOptRecord.isPendingFreeze() || processRecord.mOptRecord.isFrozen()) {
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void refreshProcessQueuesLocked(int i) {
        for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mProcessQueues.get(i); broadcastProcessQueue != null; broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
            setQueueProcess(broadcastProcessQueue, broadcastProcessQueue.app);
        }
        enqueueUpdateRunningList();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void refreshProcessQueueLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastProcessQueue processQueue = getProcessQueue(processRecord.processName, processRecord.uid);
        if (processQueue == null || processQueue.app == null || processQueue.app.getPid() != processRecord.getPid()) {
            return;
        }
        setQueueProcess(processQueue, processQueue.app);
        enqueueUpdateRunningList();
    }

    private void notifyStartedRunning(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        if (broadcastProcessQueue.app != null) {
            broadcastProcessQueue.app.mReceivers.incrementCurReceivers();
            if (this.mService.mInternal.getRestrictionLevel(broadcastProcessQueue.uid) < 40) {
                this.mService.updateLruProcessLocked(broadcastProcessQueue.app, false, null);
            }
            this.mService.mOomAdjuster.unfreezeTemporarily(broadcastProcessQueue.app, 3);
            if (broadcastProcessQueue.runningOomAdjusted) {
                broadcastProcessQueue.app.mState.forceProcessStateUpTo(11);
                this.mService.enqueueOomAdjTargetLocked(broadcastProcessQueue.app);
            }
        }
    }

    private void notifyStoppedRunning(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue) {
        if (broadcastProcessQueue.app != null) {
            broadcastProcessQueue.app.mReceivers.decrementCurReceivers();
            if (broadcastProcessQueue.runningOomAdjusted) {
                this.mService.enqueueOomAdjTargetLocked(broadcastProcessQueue.app);
            }
        }
    }

    private void notifyScheduleRegisteredReceiver(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull com.android.server.am.BroadcastFilter broadcastFilter) {
        reportUsageStatsBroadcastDispatched(processRecord, broadcastRecord);
    }

    private void notifyScheduleReceiver(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        reportUsageStatsBroadcastDispatched(processRecord, broadcastRecord);
        java.lang.String str = resolveInfo.activityInfo.packageName;
        processRecord.addPackage(str, resolveInfo.activityInfo.applicationInfo.longVersionCode, this.mService.mProcessStats);
        boolean z = broadcastRecord.intent.getComponent() != null;
        boolean equals = java.util.Objects.equals(broadcastRecord.callerPackage, str);
        if (z && !equals) {
            this.mService.mUsageStatsService.reportEvent(str, broadcastRecord.userId, 31);
        }
        this.mService.notifyPackageUse(str, 3);
        this.mService.mPackageManagerInt.notifyComponentUsed(str, broadcastRecord.userId, broadcastRecord.callerPackage, broadcastRecord.toString());
    }

    private void reportUsageStatsBroadcastDispatched(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        java.lang.String str;
        long idForResponseEvent = broadcastRecord.options != null ? broadcastRecord.options.getIdForResponseEvent() : 0L;
        if (idForResponseEvent <= 0) {
            return;
        }
        if (broadcastRecord.intent.getPackage() != null) {
            str = broadcastRecord.intent.getPackage();
        } else if (broadcastRecord.intent.getComponent() != null) {
            str = broadcastRecord.intent.getComponent().getPackageName();
        } else {
            str = null;
        }
        if (str == null) {
            return;
        }
        this.mService.mUsageStatsService.reportBroadcastDispatched(broadcastRecord.callingUid, str, android.os.UserHandle.of(broadcastRecord.userId), idForResponseEvent, android.os.SystemClock.elapsedRealtime(), this.mService.getUidStateLocked(processRecord.uid));
    }

    private void notifyFinishReceiver(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i, @android.annotation.NonNull java.lang.Object obj) {
        if (broadcastRecord.wasDeliveryAttempted(i)) {
            logBroadcastDeliveryEventReported(broadcastProcessQueue, processRecord, broadcastRecord, i, obj);
        }
        if (broadcastRecord.terminalCount == broadcastRecord.receivers.size()) {
            notifyFinishBroadcast(broadcastRecord);
        }
    }

    private void logBroadcastDeliveryEventReported(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i, @android.annotation.NonNull java.lang.Object obj) {
        int i2;
        int i3;
        int i4;
        int i5;
        int receiverUid = com.android.server.am.BroadcastRecord.getReceiverUid(obj);
        int i6 = broadcastRecord.callingUid == -1 ? 1000 : broadcastRecord.callingUid;
        java.lang.String action = broadcastRecord.intent.getAction();
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            i2 = 1;
        } else {
            i2 = 2;
        }
        if (broadcastProcessQueue == null) {
            i3 = -1;
            i4 = 0;
        } else if (broadcastProcessQueue.getActiveViaColdStart()) {
            i4 = 3;
            i3 = 20;
        } else {
            i3 = broadcastProcessQueue.lastProcessState;
            i4 = 1;
        }
        long j = broadcastRecord.scheduledTime[i] - broadcastRecord.enqueueTime;
        long j2 = broadcastRecord.terminalTime[i] - broadcastRecord.scheduledTime[i];
        if (broadcastProcessQueue != null) {
            if (broadcastProcessQueue.getActiveWasStopped()) {
                i5 = 2;
            } else {
                i5 = 1;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED, receiverUid, i6, action, i2, i4, j, 0L, j2, i5, processRecord != null ? processRecord.info.packageName : null, broadcastRecord.callerPackage, broadcastRecord.calculateTypeForLogging(), broadcastRecord.getDeliveryGroupPolicy(), broadcastRecord.intent.getFlags(), com.android.server.am.BroadcastRecord.getReceiverPriority(obj), broadcastRecord.callerProcState, i3);
        }
    }

    private void notifyFinishBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        this.mService.notifyBroadcastFinishedLocked(broadcastRecord);
        broadcastRecord.finishTime = android.os.SystemClock.uptimeMillis();
        broadcastRecord.nextReceiver = broadcastRecord.receivers.size();
        this.mHistory.onBroadcastFinishedLocked(broadcastRecord);
        com.android.server.am.BroadcastQueueImpl.logBootCompletedBroadcastCompletionLatencyIfPossible(broadcastRecord);
        if (broadcastRecord.intent.getComponent() == null && broadcastRecord.intent.getPackage() == null && (broadcastRecord.intent.getFlags() & 1073741824) == 0) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < broadcastRecord.receivers.size(); i3++) {
                if (broadcastRecord.receivers.get(i3) instanceof android.content.pm.ResolveInfo) {
                    i++;
                    if (broadcastRecord.delivery[i3] == 2) {
                        i2++;
                    }
                }
            }
            this.mService.addBroadcastStatLocked(broadcastRecord.intent.getAction(), broadcastRecord.callerPackage, i, i2, android.os.SystemClock.uptimeMillis() - broadcastRecord.enqueueTime);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.BroadcastProcessQueue getOrCreateProcessQueue(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return getOrCreateProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.BroadcastProcessQueue getOrCreateProcessQueue(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mProcessQueues.get(i);
        while (broadcastProcessQueue != null) {
            if (java.util.Objects.equals(broadcastProcessQueue.processName, str)) {
                return broadcastProcessQueue;
            }
            if (broadcastProcessQueue.processNameNext == null) {
                break;
            }
            broadcastProcessQueue = broadcastProcessQueue.processNameNext;
        }
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2 = new com.android.server.am.BroadcastProcessQueue(this.mConstants, str, i);
        setQueueProcess(broadcastProcessQueue2, this.mService.getProcessRecordLocked(str, i));
        if (broadcastProcessQueue == null) {
            this.mProcessQueues.put(i, broadcastProcessQueue2);
        } else {
            broadcastProcessQueue.processNameNext = broadcastProcessQueue2;
        }
        return broadcastProcessQueue2;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue getProcessQueue(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return getProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue getProcessQueue(@android.annotation.NonNull java.lang.String str, int i) {
        for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mProcessQueues.get(i); broadcastProcessQueue != null; broadcastProcessQueue = broadcastProcessQueue.processNameNext) {
            if (java.util.Objects.equals(broadcastProcessQueue.processName, str)) {
                return broadcastProcessQueue;
            }
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue removeProcessQueue(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return removeProcessQueue(processRecord.processName, processRecord.info.uid);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue removeProcessQueue(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = null;
        for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2 = this.mProcessQueues.get(i); broadcastProcessQueue2 != null; broadcastProcessQueue2 = broadcastProcessQueue2.processNameNext) {
            if (java.util.Objects.equals(broadcastProcessQueue2.processName, str)) {
                if (broadcastProcessQueue != null) {
                    broadcastProcessQueue.processNameNext = broadcastProcessQueue2.processNameNext;
                } else if (broadcastProcessQueue2.processNameNext != null) {
                    this.mProcessQueues.put(i, broadcastProcessQueue2.processNameNext);
                } else {
                    this.mProcessQueues.remove(i);
                }
                return broadcastProcessQueue2;
            }
            broadcastProcessQueue = broadcastProcessQueue2;
        }
        return null;
    }

    @Override // com.android.server.am.BroadcastQueue
    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mQueueName);
        this.mHistory.dumpDebug(protoOutputStream);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.am.BroadcastQueue
    @dalvik.annotation.optimization.NeverCompile
    public boolean dumpLocked(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String[] strArr, int i, boolean z, boolean z2, boolean z3, @android.annotation.Nullable java.lang.String str, boolean z4) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("📋 Per-process queues:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mProcessQueues.size(); i2++) {
            for (com.android.server.am.BroadcastProcessQueue valueAt = this.mProcessQueues.valueAt(i2); valueAt != null; valueAt = valueAt.processNameNext) {
                valueAt.dumpLocked(uptimeMillis, indentingPrintWriter);
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("🧍 Runnable:");
        indentingPrintWriter.increaseIndent();
        if (this.mRunnableHead == null) {
            indentingPrintWriter.println("(none)");
        } else {
            for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue = this.mRunnableHead; broadcastProcessQueue != null; broadcastProcessQueue = broadcastProcessQueue.runnableAtNext) {
                android.util.TimeUtils.formatDuration(broadcastProcessQueue.getRunnableAt(), uptimeMillis, indentingPrintWriter);
                indentingPrintWriter.print(' ');
                indentingPrintWriter.print(com.android.server.am.BroadcastProcessQueue.reasonToString(broadcastProcessQueue.getRunnableAtReason()));
                indentingPrintWriter.print(' ');
                indentingPrintWriter.print(broadcastProcessQueue.toShortString());
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("🏃 Running:");
        indentingPrintWriter.increaseIndent();
        for (com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2 : this.mRunning) {
            if (broadcastProcessQueue2 != null && broadcastProcessQueue2 == this.mRunningColdStart) {
                indentingPrintWriter.print("🥶 ");
            } else {
                indentingPrintWriter.print("\u3000 ");
            }
            if (broadcastProcessQueue2 != null) {
                indentingPrintWriter.println(broadcastProcessQueue2.toShortString());
            } else {
                indentingPrintWriter.println("(none)");
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Broadcasts with ignored delivery group policies:");
        indentingPrintWriter.increaseIndent();
        this.mService.dumpDeliveryGroupPolicyIgnoredActions(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Foreground UIDs:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(this.mUidForeground);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        if (z) {
            this.mConstants.dump(indentingPrintWriter);
        }
        if (!z2) {
            return z4;
        }
        return this.mHistory.dumpLocked(indentingPrintWriter, str, this.mQueueName, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"), z3, z4);
    }
}
