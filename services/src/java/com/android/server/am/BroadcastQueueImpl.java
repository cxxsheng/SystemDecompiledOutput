package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastQueueImpl extends com.android.server.am.BroadcastQueue {
    static final int BROADCAST_INTENT_MSG = 200;
    static final int BROADCAST_TIMEOUT_MSG = 201;
    private static final java.lang.String TAG_BROADCAST = "BroadcastQueue";
    private static final java.lang.String TAG_MU = "BroadcastQueue_MU";
    boolean mBroadcastsScheduled;
    final com.android.server.am.BroadcastConstants mConstants;
    final boolean mDelayBehindServices;
    final com.android.server.am.BroadcastDispatcher mDispatcher;
    final com.android.server.am.BroadcastQueueImpl.BroadcastHandler mHandler;
    boolean mLogLatencyMetrics;
    private int mNextToken;
    final java.util.ArrayList<com.android.server.am.BroadcastRecord> mParallelBroadcasts;
    com.android.server.am.BroadcastRecord mPendingBroadcast;
    int mPendingBroadcastRecvIndex;
    boolean mPendingBroadcastTimeoutMessage;
    final int mSchedGroup;
    final android.util.SparseIntArray mSplitRefcounts;

    private final class BroadcastHandler extends android.os.Handler {
        public BroadcastHandler(android.os.Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 200:
                    com.android.server.am.BroadcastQueueImpl.this.processNextBroadcast(true);
                    return;
                case 201:
                    com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.BroadcastQueueImpl.this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            com.android.server.am.BroadcastQueueImpl.this.broadcastTimeoutLocked(true);
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }
    }

    BroadcastQueueImpl(com.android.server.am.ActivityManagerService activityManagerService, android.os.Handler handler, java.lang.String str, com.android.server.am.BroadcastConstants broadcastConstants, boolean z, int i) {
        this(activityManagerService, handler, str, broadcastConstants, new com.android.server.am.BroadcastSkipPolicy(activityManagerService), new com.android.server.am.BroadcastHistory(broadcastConstants), z, i);
    }

    BroadcastQueueImpl(com.android.server.am.ActivityManagerService activityManagerService, android.os.Handler handler, java.lang.String str, com.android.server.am.BroadcastConstants broadcastConstants, com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy, com.android.server.am.BroadcastHistory broadcastHistory, boolean z, int i) {
        super(activityManagerService, handler, str, broadcastSkipPolicy, broadcastHistory);
        this.mParallelBroadcasts = new java.util.ArrayList<>();
        this.mSplitRefcounts = new android.util.SparseIntArray();
        this.mNextToken = 0;
        this.mBroadcastsScheduled = false;
        this.mPendingBroadcast = null;
        this.mLogLatencyMetrics = true;
        this.mHandler = new com.android.server.am.BroadcastQueueImpl.BroadcastHandler(handler.getLooper());
        this.mConstants = broadcastConstants;
        this.mDelayBehindServices = z;
        this.mSchedGroup = i;
        this.mDispatcher = new com.android.server.am.BroadcastDispatcher(this, this.mConstants, this.mHandler, this.mService);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void start(android.content.ContentResolver contentResolver) {
        this.mDispatcher.start();
        this.mConstants.startObserving(this.mHandler, contentResolver);
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean isDelayBehindServices() {
        return this.mDelayBehindServices;
    }

    public com.android.server.am.BroadcastRecord getPendingBroadcastLocked() {
        return this.mPendingBroadcast;
    }

    public com.android.server.am.BroadcastRecord getActiveBroadcastLocked() {
        return this.mDispatcher.getActiveBroadcastLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    public int getPreferredSchedulingGroupLocked(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastRecord activeBroadcastLocked = getActiveBroadcastLocked();
        if (activeBroadcastLocked != null && activeBroadcastLocked.curApp == processRecord) {
            return this.mSchedGroup;
        }
        com.android.server.am.BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked != null && pendingBroadcastLocked.curApp == processRecord) {
            return this.mSchedGroup;
        }
        return Integer.MIN_VALUE;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void enqueueBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        com.android.server.am.BroadcastRecord broadcastRecord2;
        com.android.server.am.ProcessRecord processRecord;
        android.content.IIntentReceiver iIntentReceiver;
        android.content.Intent intent;
        boolean z;
        int i;
        int i2;
        int i3;
        java.lang.String str;
        long uptimeMillis;
        int i4;
        broadcastRecord.applySingletonPolicy(this.mService);
        boolean z2 = false;
        boolean z3 = (broadcastRecord.intent.getFlags() & 536870912) != 0;
        boolean z4 = broadcastRecord.ordered;
        if (!z4) {
            int size = broadcastRecord.receivers != null ? broadcastRecord.receivers.size() : 0;
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    break;
                }
                if (!(broadcastRecord.receivers.get(i5) instanceof android.content.pm.ResolveInfo)) {
                    i5++;
                } else {
                    z4 = true;
                    break;
                }
            }
        }
        if (z4) {
            com.android.server.am.BroadcastRecord replaceOrderedBroadcastLocked = z3 ? replaceOrderedBroadcastLocked(broadcastRecord) : null;
            if (replaceOrderedBroadcastLocked != null) {
                if (replaceOrderedBroadcastLocked.resultTo != null) {
                    try {
                        replaceOrderedBroadcastLocked.mIsReceiverAppRunning = true;
                        processRecord = replaceOrderedBroadcastLocked.resultToApp;
                        iIntentReceiver = replaceOrderedBroadcastLocked.resultTo;
                        intent = replaceOrderedBroadcastLocked.intent;
                        z = replaceOrderedBroadcastLocked.shareIdentity;
                        i = replaceOrderedBroadcastLocked.userId;
                        i2 = replaceOrderedBroadcastLocked.callingUid;
                        i3 = broadcastRecord.callingUid;
                        str = broadcastRecord.callerPackage;
                        uptimeMillis = android.os.SystemClock.uptimeMillis() - replaceOrderedBroadcastLocked.enqueueTime;
                        if (replaceOrderedBroadcastLocked.resultToApp != null) {
                            i4 = replaceOrderedBroadcastLocked.resultToApp.mState.getCurProcState();
                        } else {
                            i4 = -1;
                        }
                        broadcastRecord2 = replaceOrderedBroadcastLocked;
                    } catch (android.os.RemoteException e) {
                        e = e;
                        broadcastRecord2 = replaceOrderedBroadcastLocked;
                    }
                    try {
                        performReceiveLocked(replaceOrderedBroadcastLocked, processRecord, iIntentReceiver, intent, 0, null, null, false, false, z, i, i2, i3, str, uptimeMillis, 0L, 0, i4);
                        return;
                    } catch (android.os.RemoteException e2) {
                        e = e2;
                        android.util.Slog.w("BroadcastQueue", "Failure [" + this.mQueueName + "] sending broadcast result of " + broadcastRecord2.intent, e);
                        return;
                    }
                }
                return;
            }
            enqueueOrderedBroadcastLocked(broadcastRecord);
            scheduleBroadcastsLocked();
            return;
        }
        if (z3 && replaceParallelBroadcastLocked(broadcastRecord) != null) {
            z2 = true;
        }
        if (!z2) {
            enqueueParallelBroadcastLocked(broadcastRecord);
            scheduleBroadcastsLocked();
        }
    }

    public void enqueueParallelBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        broadcastRecord.enqueueClockTime = java.lang.System.currentTimeMillis();
        broadcastRecord.enqueueTime = android.os.SystemClock.uptimeMillis();
        broadcastRecord.enqueueRealTime = android.os.SystemClock.elapsedRealtime();
        this.mParallelBroadcasts.add(broadcastRecord);
        enqueueBroadcastHelper(broadcastRecord);
    }

    public void enqueueOrderedBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        broadcastRecord.enqueueClockTime = java.lang.System.currentTimeMillis();
        broadcastRecord.enqueueTime = android.os.SystemClock.uptimeMillis();
        broadcastRecord.enqueueRealTime = android.os.SystemClock.elapsedRealtime();
        this.mDispatcher.enqueueOrderedBroadcastLocked(broadcastRecord);
        enqueueBroadcastHelper(broadcastRecord);
    }

    private void enqueueBroadcastHelper(com.android.server.am.BroadcastRecord broadcastRecord) {
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.asyncTraceBegin(64L, createBroadcastTraceTitle(broadcastRecord, 0), java.lang.System.identityHashCode(broadcastRecord));
        }
    }

    public final com.android.server.am.BroadcastRecord replaceParallelBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        return replaceBroadcastLocked(this.mParallelBroadcasts, broadcastRecord, "PARALLEL");
    }

    public final com.android.server.am.BroadcastRecord replaceOrderedBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        return this.mDispatcher.replaceBroadcastLocked(broadcastRecord, "ORDERED");
    }

    private com.android.server.am.BroadcastRecord replaceBroadcastLocked(java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList, com.android.server.am.BroadcastRecord broadcastRecord, java.lang.String str) {
        android.content.Intent intent = broadcastRecord.intent;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.am.BroadcastRecord broadcastRecord2 = arrayList.get(size);
            if (broadcastRecord2.userId == broadcastRecord.userId && intent.filterEquals(broadcastRecord2.intent)) {
                arrayList.set(size, broadcastRecord);
                return broadcastRecord2;
            }
        }
        return null;
    }

    private final void processCurBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord, com.android.server.am.ProcessRecord processRecord) throws android.os.RemoteException {
        com.android.server.am.ProcessReceiverRecord processReceiverRecord;
        android.app.IApplicationThread thread = processRecord.getThread();
        if (thread == null) {
            throw new android.os.RemoteException();
        }
        if (processRecord.isInFullBackup()) {
            skipReceiverLocked(broadcastRecord);
            return;
        }
        broadcastRecord.curApp = processRecord;
        broadcastRecord.curAppLastProcessState = processRecord.mState.getCurProcState();
        com.android.server.am.ProcessReceiverRecord processReceiverRecord2 = processRecord.mReceivers;
        processReceiverRecord2.addCurReceiver(broadcastRecord);
        processRecord.mState.forceProcessStateUpTo(11);
        if (this.mService.mInternal.getRestrictionLevel(processRecord.info.packageName, processRecord.userId) < 40) {
            this.mService.updateLruProcessLocked(processRecord, false, null);
        }
        this.mService.enqueueOomAdjTargetLocked(processRecord);
        this.mService.updateOomAdjPendingTargetsLocked(3);
        maybeReportBroadcastDispatchedEventLocked(broadcastRecord, broadcastRecord.curReceiver.applicationInfo.uid);
        broadcastRecord.intent.setComponent(broadcastRecord.curComponent);
        if (broadcastRecord.options != null && broadcastRecord.options.getTemporaryAppAllowlistDuration() > 0 && broadcastRecord.options.getTemporaryAppAllowlistType() == 4) {
            this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeTemporarily(processRecord, 3, broadcastRecord.options.getTemporaryAppAllowlistDuration());
        }
        try {
            this.mService.notifyPackageUse(broadcastRecord.intent.getComponent().getPackageName(), 3);
            processReceiverRecord = processReceiverRecord2;
            try {
                thread.scheduleReceiver(prepareReceiverIntent(broadcastRecord.intent, broadcastRecord.curFilteredExtras), broadcastRecord.curReceiver, (android.content.res.CompatibilityInfo) null, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.ordered, false, broadcastRecord.userId, broadcastRecord.shareIdentity ? broadcastRecord.callingUid : -1, processRecord.mState.getReportedProcState(), broadcastRecord.shareIdentity ? broadcastRecord.callerPackage : null);
                if (processRecord.isKilled()) {
                    throw new android.os.RemoteException("app gets killed during broadcasting");
                }
            } catch (java.lang.Throwable th) {
                th = th;
                broadcastRecord.curApp = null;
                broadcastRecord.curAppLastProcessState = -1;
                processReceiverRecord.removeCurReceiver(broadcastRecord);
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            processReceiverRecord = processReceiverRecord2;
        }
    }

    public void updateUidReadyForBootCompletedBroadcastLocked(int i) {
        this.mDispatcher.updateUidReadyForBootCompletedBroadcastLocked(i);
        scheduleBroadcastsLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean onApplicationAttachedLocked(com.android.server.am.ProcessRecord processRecord) throws com.android.server.am.BroadcastDeliveryFailedException {
        updateUidReadyForBootCompletedBroadcastLocked(processRecord.uid);
        if (this.mPendingBroadcast != null && this.mPendingBroadcast.curApp == processRecord) {
            return sendPendingBroadcastsLocked(processRecord);
        }
        return false;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationTimeoutLocked(com.android.server.am.ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationProblemLocked(com.android.server.am.ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onApplicationCleanupLocked(com.android.server.am.ProcessRecord processRecord) {
        skipCurrentOrPendingReceiverLocked(processRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void onProcessFreezableChangedLocked(com.android.server.am.ProcessRecord processRecord) {
    }

    public boolean sendPendingBroadcastsLocked(com.android.server.am.ProcessRecord processRecord) throws com.android.server.am.BroadcastDeliveryFailedException {
        com.android.server.am.BroadcastRecord broadcastRecord = this.mPendingBroadcast;
        if (broadcastRecord == null || broadcastRecord.curApp.getPid() <= 0 || broadcastRecord.curApp.getPid() != processRecord.getPid()) {
            return false;
        }
        if (broadcastRecord.curApp != processRecord) {
            android.util.Slog.e("BroadcastQueue", "App mismatch when sending pending broadcast to " + processRecord.processName + ", intended target is " + broadcastRecord.curApp.processName);
            return false;
        }
        try {
            this.mPendingBroadcast = null;
            broadcastRecord.mIsReceiverAppRunning = false;
            processCurBroadcastLocked(broadcastRecord, processRecord);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.w("BroadcastQueue", "Exception in new application when starting receiver " + broadcastRecord.curComponent.flattenToShortString(), e);
            logBroadcastReceiverDiscardLocked(broadcastRecord);
            finishReceiverLocked(broadcastRecord, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.resultAbort, false);
            scheduleBroadcastsLocked();
            broadcastRecord.state = 0;
            throw new com.android.server.am.BroadcastDeliveryFailedException(e);
        }
    }

    public boolean skipCurrentOrPendingReceiverLocked(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked == null || activeBroadcastLocked.curApp != processRecord) {
            activeBroadcastLocked = null;
        }
        if (activeBroadcastLocked == null && this.mPendingBroadcast != null && this.mPendingBroadcast.curApp == processRecord) {
            activeBroadcastLocked = this.mPendingBroadcast;
        }
        if (activeBroadcastLocked != null) {
            skipReceiverLocked(activeBroadcastLocked);
            return true;
        }
        return false;
    }

    private void skipReceiverLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        logBroadcastReceiverDiscardLocked(broadcastRecord);
        finishReceiverLocked(broadcastRecord, broadcastRecord.resultCode, broadcastRecord.resultData, broadcastRecord.resultExtras, broadcastRecord.resultAbort, false);
        scheduleBroadcastsLocked();
    }

    public void scheduleBroadcastsLocked() {
        if (this.mBroadcastsScheduled) {
            return;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(200, this));
        this.mBroadcastsScheduled = true;
    }

    public com.android.server.am.BroadcastRecord getMatchingOrderedReceiver(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked == null) {
            android.util.Slog.w("BroadcastQueue", "getMatchingOrderedReceiver [" + this.mQueueName + "] no active broadcast");
            return null;
        }
        if (activeBroadcastLocked.curApp != processRecord) {
            android.util.Slog.w("BroadcastQueue", "getMatchingOrderedReceiver [" + this.mQueueName + "] active broadcast " + activeBroadcastLocked.curApp + " doesn't match " + processRecord);
            return null;
        }
        return activeBroadcastLocked;
    }

    private int nextSplitTokenLocked() {
        int i = this.mNextToken + 1;
        int i2 = i > 0 ? i : 1;
        this.mNextToken = i2;
        return i2;
    }

    private void postActivityStartTokenRemoval(final com.android.server.am.ProcessRecord processRecord, final com.android.server.am.BroadcastRecord broadcastRecord) {
        java.lang.String intern = (processRecord.toShortString() + broadcastRecord.toString()).intern();
        this.mHandler.removeCallbacksAndMessages(intern);
        this.mHandler.postAtTime(new java.lang.Runnable() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.BroadcastQueueImpl.this.lambda$postActivityStartTokenRemoval$0(processRecord, broadcastRecord);
            }
        }, intern, broadcastRecord.receiverTime + this.mConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postActivityStartTokenRemoval$0(com.android.server.am.ProcessRecord processRecord, com.android.server.am.BroadcastRecord broadcastRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                processRecord.removeBackgroundStartPrivileges(broadcastRecord);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean finishReceiverLocked(com.android.server.am.ProcessRecord processRecord, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2) {
        com.android.server.am.BroadcastRecord matchingOrderedReceiver = getMatchingOrderedReceiver(processRecord);
        if (matchingOrderedReceiver != null) {
            return finishReceiverLocked(matchingOrderedReceiver, i, str, bundle, z, z2);
        }
        return false;
    }

    public boolean finishReceiverLocked(com.android.server.am.BroadcastRecord broadcastRecord, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2) {
        int i2;
        int i3;
        android.content.pm.ActivityInfo activityInfo;
        int i4;
        int i5;
        int i6 = broadcastRecord.state;
        android.content.pm.ActivityInfo activityInfo2 = broadcastRecord.curReceiver;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long j = uptimeMillis - broadcastRecord.receiverTime;
        broadcastRecord.state = 0;
        int i7 = broadcastRecord.nextReceiver - 1;
        if (broadcastRecord.mWasReceiverAppStopped) {
            i2 = 2;
        } else {
            i2 = 1;
        }
        if (i7 < 0 || i7 >= broadcastRecord.receivers.size() || broadcastRecord.curApp == null) {
            i3 = -1;
        } else {
            java.lang.Object obj = broadcastRecord.receivers.get(i7);
            int i8 = broadcastRecord.curApp.uid;
            int i9 = broadcastRecord.callingUid == -1 ? 1000 : broadcastRecord.callingUid;
            java.lang.String action = broadcastRecord.intent.getAction();
            if (obj instanceof com.android.server.am.BroadcastFilter) {
                i4 = 1;
            } else {
                i4 = 2;
            }
            if (broadcastRecord.mIsReceiverAppRunning) {
                i5 = 1;
            } else {
                i5 = 3;
            }
            i3 = -1;
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED, i8, i9, action, i4, i5, broadcastRecord.dispatchTime - broadcastRecord.enqueueTime, broadcastRecord.receiverTime - broadcastRecord.dispatchTime, uptimeMillis - broadcastRecord.receiverTime, i2, broadcastRecord.curApp.info.packageName, broadcastRecord.callerPackage, broadcastRecord.calculateTypeForLogging(), broadcastRecord.getDeliveryGroupPolicy(), broadcastRecord.intent.getFlags(), com.android.server.am.BroadcastRecord.getReceiverPriority(obj), broadcastRecord.callerProcState, broadcastRecord.curAppLastProcessState);
        }
        if (i6 == 0) {
            android.util.Slog.w("BroadcastQueue", "finishReceiver [" + this.mQueueName + "] called but state is IDLE");
        }
        if (broadcastRecord.mBackgroundStartPrivileges.allowsAny() && broadcastRecord.curApp != null) {
            if (j <= this.mConstants.ALLOW_BG_ACTIVITY_START_TIMEOUT) {
                postActivityStartTokenRemoval(broadcastRecord.curApp, broadcastRecord);
            } else {
                broadcastRecord.curApp.removeBackgroundStartPrivileges(broadcastRecord);
            }
        }
        if (broadcastRecord.nextReceiver > 0) {
            broadcastRecord.terminalTime[broadcastRecord.nextReceiver - 1] = uptimeMillis;
        }
        if (!broadcastRecord.timeoutExempt && broadcastRecord.curApp != null && this.mConstants.SLOW_TIME > 0 && j > this.mConstants.SLOW_TIME && !android.os.UserHandle.isCore(broadcastRecord.curApp.uid)) {
            this.mDispatcher.startDeferring(broadcastRecord.curApp.uid);
        }
        broadcastRecord.intent.setComponent(null);
        if (broadcastRecord.curApp != null && broadcastRecord.curApp.mReceivers.hasCurReceiver(broadcastRecord)) {
            broadcastRecord.curApp.mReceivers.removeCurReceiver(broadcastRecord);
            this.mService.enqueueOomAdjTargetLocked(broadcastRecord.curApp);
        }
        if (broadcastRecord.curFilter != null) {
            broadcastRecord.curFilter.receiverList.curBroadcast = null;
        }
        broadcastRecord.curFilter = null;
        broadcastRecord.curReceiver = null;
        broadcastRecord.curApp = null;
        broadcastRecord.curAppLastProcessState = i3;
        broadcastRecord.curFilteredExtras = null;
        broadcastRecord.mWasReceiverAppStopped = false;
        this.mPendingBroadcast = null;
        broadcastRecord.resultCode = i;
        broadcastRecord.resultData = str;
        broadcastRecord.resultExtras = bundle;
        if (z && (broadcastRecord.intent.getFlags() & 134217728) == 0) {
            broadcastRecord.resultAbort = z;
        } else {
            broadcastRecord.resultAbort = false;
        }
        if (z2 && broadcastRecord.curComponent != null && broadcastRecord.queue.isDelayBehindServices() && ((com.android.server.am.BroadcastQueueImpl) broadcastRecord.queue).getActiveBroadcastLocked() == broadcastRecord) {
            if (broadcastRecord.nextReceiver < broadcastRecord.receivers.size()) {
                java.lang.Object obj2 = broadcastRecord.receivers.get(broadcastRecord.nextReceiver);
                activityInfo = obj2 instanceof android.content.pm.ActivityInfo ? (android.content.pm.ActivityInfo) obj2 : null;
            } else {
                activityInfo = null;
            }
            if ((activityInfo2 == null || activityInfo == null || activityInfo2.applicationInfo.uid != activityInfo.applicationInfo.uid || !activityInfo2.processName.equals(activityInfo.processName)) && this.mService.mServices.hasBackgroundServicesLocked(broadcastRecord.userId)) {
                android.util.Slog.i("BroadcastQueue", "Delay finish: " + broadcastRecord.curComponent.flattenToShortString());
                broadcastRecord.state = 4;
                return false;
            }
        }
        broadcastRecord.curComponent = null;
        boolean z3 = i6 == 1 || i6 == 3;
        if (z3) {
            processNextBroadcastLocked(false, true);
        }
        return z3;
    }

    @Override // com.android.server.am.BroadcastQueue
    public void backgroundServicesFinishedLocked(int i) {
        com.android.server.am.BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
        if (activeBroadcastLocked != null && activeBroadcastLocked.userId == i && activeBroadcastLocked.state == 4) {
            android.util.Slog.i("BroadcastQueue", "Resuming delayed broadcast");
            activeBroadcastLocked.curComponent = null;
            activeBroadcastLocked.state = 0;
            processNextBroadcastLocked(false, false);
        }
    }

    public void performReceiveLocked(com.android.server.am.BroadcastRecord broadcastRecord, com.android.server.am.ProcessRecord processRecord, android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2, long j, long j2, int i5, int i6) throws android.os.RemoteException {
        int i7;
        int i8;
        if (z3) {
            this.mService.mPackageManagerInt.grantImplicitAccess(i2, intent, android.os.UserHandle.getAppId(i3), i4, true);
        }
        if (processRecord != null) {
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null) {
                try {
                    i7 = -1;
                    thread.scheduleRegisteredReceiver(iIntentReceiver, intent, i, str, bundle, z, z2, !z, i2, processRecord.mState.getReportedProcState(), z3 ? i4 : -1, z3 ? str2 : null);
                } catch (android.os.RemoteException e) {
                    com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            android.util.Slog.w("BroadcastQueue", "Failed to schedule " + intent + " to " + iIntentReceiver + " via " + processRecord + ": " + e);
                            processRecord.killLocked("Can't deliver broadcast", 13, 26, true);
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw e;
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } else {
                throw new android.os.RemoteException("app.thread must not be null");
            }
        } else {
            i7 = -1;
            iIntentReceiver.performReceive(intent, i, str, bundle, z, z2, i2);
        }
        if (!z) {
            int i9 = i3 == i7 ? 1000 : i3;
            if (i4 == i7) {
                i8 = 1000;
            } else {
                i8 = i4;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BROADCAST_DELIVERY_EVENT_REPORTED, i9, i8, intent.getAction(), 1, 1, j, j2, 0L, 1, processRecord != null ? processRecord.info.packageName : null, str2, broadcastRecord.calculateTypeForLogging(), broadcastRecord.getDeliveryGroupPolicy(), broadcastRecord.intent.getFlags(), i5, broadcastRecord.callerProcState, i6);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0190 A[Catch: RemoteException -> 0x0164, TRY_LEAVE, TryCatch #4 {RemoteException -> 0x0164, blocks: (B:36:0x018b, B:38:0x0190, B:68:0x0160), top: B:31:0x00b1 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v29, types: [com.android.server.am.BroadcastFilter] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.os.Bundle] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.server.am.BroadcastRecord] */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.server.am.BroadcastRecord] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29, types: [com.android.server.am.BroadcastQueueImpl] */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v34 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void deliverToRegisteredReceiverLocked(com.android.server.am.BroadcastRecord broadcastRecord, com.android.server.am.BroadcastFilter broadcastFilter, boolean z, int i) {
        ?? r2;
        com.android.server.am.BroadcastFilter broadcastFilter2;
        com.android.server.am.BroadcastRecord broadcastRecord2;
        com.android.server.am.BroadcastQueueImpl broadcastQueueImpl;
        com.android.server.am.BroadcastQueueImpl broadcastQueueImpl2;
        com.android.server.am.BroadcastFilter broadcastFilter3;
        android.os.Bundle extras;
        com.android.server.am.BroadcastFilter broadcastFilter4 = broadcastFilter;
        boolean shouldSkip = this.mSkipPolicy.shouldSkip(broadcastRecord, broadcastFilter4);
        ?? r1 = 1;
        if (shouldSkip || broadcastRecord.filterExtrasForReceiver == null || (extras = broadcastRecord.intent.getExtras()) == null) {
            r2 = 0;
        } else {
            android.os.Bundle apply = broadcastRecord.filterExtrasForReceiver.apply(java.lang.Integer.valueOf(broadcastFilter4.receiverList.uid), extras);
            r2 = apply;
            if (apply == null) {
                shouldSkip = true;
                r2 = apply;
            }
        }
        if (shouldSkip) {
            broadcastRecord.delivery[i] = 2;
            return;
        }
        broadcastRecord.delivery[i] = 1;
        if (z) {
            broadcastRecord.curFilter = broadcastFilter4;
            broadcastFilter4.receiverList.curBroadcast = broadcastRecord;
            broadcastRecord.state = 2;
            if (broadcastFilter4.receiverList.app != null) {
                broadcastRecord.curApp = broadcastFilter4.receiverList.app;
                broadcastRecord.curAppLastProcessState = broadcastRecord.curApp.mState.getCurProcState();
                broadcastFilter4.receiverList.app.mReceivers.addCurReceiver(broadcastRecord);
                this.mService.enqueueOomAdjTargetLocked(broadcastRecord.curApp);
                this.mService.updateOomAdjPendingTargetsLocked(3);
            }
        } else if (broadcastFilter4.receiverList.app != null) {
            this.mService.mOomAdjuster.unfreezeTemporarily(broadcastFilter4.receiverList.app, 3);
        }
        try {
            ?? r3 = (broadcastFilter4.receiverList.app == null || !broadcastFilter4.receiverList.app.isInFullBackup()) ? 0 : 1;
            if (broadcastFilter4.receiverList.app == null || !broadcastFilter4.receiverList.app.isKilled()) {
                r1 = 0;
            }
            try {
                if (r3 != 0) {
                    r1 = broadcastFilter4;
                    r2 = broadcastRecord;
                    r3 = this;
                } else if (r1 != 0) {
                    r1 = broadcastFilter4;
                    r2 = broadcastRecord;
                    r3 = this;
                } else {
                    broadcastRecord.receiverTime = android.os.SystemClock.uptimeMillis();
                    broadcastRecord.scheduledTime[i] = broadcastRecord.receiverTime;
                    maybeAddBackgroundStartPrivileges(broadcastFilter4.receiverList.app, broadcastRecord);
                    maybeScheduleTempAllowlistLocked(broadcastFilter4.owningUid, broadcastRecord, broadcastRecord.options);
                    maybeReportBroadcastDispatchedEventLocked(broadcastRecord, broadcastFilter4.owningUid);
                    com.android.server.am.ProcessRecord processRecord = broadcastFilter4.receiverList.app;
                    android.content.IIntentReceiver iIntentReceiver = broadcastFilter4.receiverList.receiver;
                    android.content.Intent prepareReceiverIntent = prepareReceiverIntent(broadcastRecord.intent, r2);
                    int i2 = broadcastRecord.resultCode;
                    java.lang.String str = broadcastRecord.resultData;
                    android.os.Bundle bundle = broadcastRecord.resultExtras;
                    boolean z2 = broadcastRecord.ordered;
                    boolean z3 = broadcastRecord.initialSticky;
                    boolean z4 = broadcastRecord.shareIdentity;
                    int i3 = broadcastRecord.userId;
                    int i4 = broadcastFilter4.receiverList.uid;
                    try {
                    } catch (android.os.RemoteException e) {
                        e = e;
                        broadcastQueueImpl2 = this;
                        broadcastFilter3 = broadcastFilter4;
                    }
                    try {
                        broadcastFilter4 = broadcastFilter;
                        try {
                            performReceiveLocked(broadcastRecord, processRecord, iIntentReceiver, prepareReceiverIntent, i2, str, bundle, z2, z3, z4, i3, i4, broadcastRecord.callingUid, broadcastRecord.callerPackage, broadcastRecord.dispatchTime - broadcastRecord.enqueueTime, broadcastRecord.receiverTime - broadcastRecord.dispatchTime, broadcastFilter.getPriority(), broadcastFilter4.receiverList.app != null ? broadcastFilter4.receiverList.app.mState.getCurProcState() : -1);
                            r1 = broadcastFilter;
                            try {
                                if (r1.receiverList.app != null) {
                                    r2 = broadcastRecord;
                                    try {
                                        if (!r2.mBackgroundStartPrivileges.allowsAny() || r2.ordered) {
                                            r3 = this;
                                        } else {
                                            r3 = this;
                                            r3.postActivityStartTokenRemoval(r1.receiverList.app, r2);
                                        }
                                    } catch (android.os.RemoteException e2) {
                                        e = e2;
                                        broadcastQueueImpl = this;
                                        broadcastFilter2 = r1;
                                        broadcastRecord2 = r2;
                                        android.util.Slog.w("BroadcastQueue", "Failure sending broadcast " + broadcastRecord2.intent, e);
                                        if (broadcastFilter2.receiverList.app != null) {
                                        }
                                        if (z) {
                                        }
                                    }
                                } else {
                                    r3 = this;
                                    r2 = broadcastRecord;
                                }
                                if (z) {
                                    return;
                                }
                                r2.state = 3;
                                return;
                            } catch (android.os.RemoteException e3) {
                                e = e3;
                                broadcastQueueImpl = this;
                                broadcastRecord2 = broadcastRecord;
                                broadcastFilter2 = r1;
                            }
                        } catch (android.os.RemoteException e4) {
                            e = e4;
                            broadcastQueueImpl = this;
                            broadcastRecord2 = broadcastRecord;
                            broadcastFilter2 = broadcastFilter;
                        }
                    } catch (android.os.RemoteException e5) {
                        e = e5;
                        broadcastQueueImpl2 = this;
                        broadcastFilter3 = broadcastFilter;
                        broadcastRecord2 = broadcastRecord;
                        broadcastFilter2 = broadcastFilter3;
                        broadcastQueueImpl = broadcastQueueImpl2;
                        android.util.Slog.w("BroadcastQueue", "Failure sending broadcast " + broadcastRecord2.intent, e);
                        if (broadcastFilter2.receiverList.app != null) {
                            broadcastFilter2.receiverList.app.removeBackgroundStartPrivileges(broadcastRecord2);
                            if (z) {
                                broadcastFilter2.receiverList.app.mReceivers.removeCurReceiver(broadcastRecord2);
                                broadcastQueueImpl.mService.enqueueOomAdjTargetLocked(broadcastRecord2.curApp);
                            }
                        }
                        if (z) {
                            return;
                        }
                        broadcastRecord2.curFilter = null;
                        broadcastFilter2.receiverList.curBroadcast = null;
                        return;
                    }
                }
                if (z) {
                    skipReceiverLocked(broadcastRecord);
                }
                if (z) {
                }
            } catch (android.os.RemoteException e6) {
                e = e6;
                broadcastFilter2 = r1;
                broadcastRecord2 = r2;
                broadcastQueueImpl = r3;
            }
        } catch (android.os.RemoteException e7) {
            e = e7;
            broadcastFilter2 = broadcastFilter4;
            broadcastRecord2 = broadcastRecord;
            broadcastQueueImpl = this;
        }
    }

    void maybeScheduleTempAllowlistLocked(int i, com.android.server.am.BroadcastRecord broadcastRecord, @android.annotation.Nullable android.app.BroadcastOptions broadcastOptions) {
        long j;
        if (broadcastOptions == null || broadcastOptions.getTemporaryAppAllowlistDuration() <= 0) {
            return;
        }
        long temporaryAppAllowlistDuration = broadcastOptions.getTemporaryAppAllowlistDuration();
        int temporaryAppAllowlistType = broadcastOptions.getTemporaryAppAllowlistType();
        int temporaryAppAllowlistReasonCode = broadcastOptions.getTemporaryAppAllowlistReasonCode();
        java.lang.String temporaryAppAllowlistReason = broadcastOptions.getTemporaryAppAllowlistReason();
        if (temporaryAppAllowlistDuration <= 2147483647L) {
            j = temporaryAppAllowlistDuration;
        } else {
            j = 2147483647L;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("broadcast:");
        android.os.UserHandle.formatUid(sb, broadcastRecord.callingUid);
        sb.append(":");
        if (broadcastRecord.intent.getAction() != null) {
            sb.append(broadcastRecord.intent.getAction());
        } else if (broadcastRecord.intent.getComponent() != null) {
            broadcastRecord.intent.getComponent().appendShortString(sb);
        } else if (broadcastRecord.intent.getData() != null) {
            sb.append(broadcastRecord.intent.getData());
        }
        sb.append(",reason:");
        sb.append(temporaryAppAllowlistReason);
        if (temporaryAppAllowlistType != 4) {
            this.mService.tempAllowlistUidLocked(i, j, temporaryAppAllowlistReasonCode, sb.toString(), temporaryAppAllowlistType, broadcastRecord.callingUid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNextBroadcast(boolean z) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                processNextBroadcastLocked(z, false);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    private static android.content.Intent prepareReceiverIntent(@android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable android.os.Bundle bundle) {
        android.content.Intent intent2 = new android.content.Intent(intent);
        if (bundle != null) {
            intent2.replaceExtras(bundle);
        }
        return intent2;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public void processNextBroadcastLocked(boolean r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 1650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.BroadcastQueueImpl.processNextBroadcastLocked(boolean, boolean):void");
    }

    @android.annotation.Nullable
    private java.lang.String getTargetPackage(com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.intent == null) {
            return null;
        }
        if (broadcastRecord.intent.getPackage() != null) {
            return broadcastRecord.intent.getPackage();
        }
        if (broadcastRecord.intent.getComponent() != null) {
            return broadcastRecord.intent.getComponent().getPackageName();
        }
        return null;
    }

    static void logBootCompletedBroadcastCompletionLatencyIfPossible(com.android.server.am.BroadcastRecord broadcastRecord) {
        int i;
        int i2;
        int size = broadcastRecord.receivers != null ? broadcastRecord.receivers.size() : 0;
        if (broadcastRecord.nextReceiver < size) {
            return;
        }
        java.lang.String action = broadcastRecord.intent.getAction();
        if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(action)) {
            i = 1;
        } else if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
            i = 0;
        } else {
            i = 2;
        }
        if (i != 0) {
            int i3 = (int) (broadcastRecord.dispatchTime - broadcastRecord.enqueueTime);
            int uptimeMillis = (int) (android.os.SystemClock.uptimeMillis() - broadcastRecord.enqueueTime);
            int i4 = (int) (broadcastRecord.dispatchRealTime - broadcastRecord.enqueueRealTime);
            int elapsedRealtime = (int) (android.os.SystemClock.elapsedRealtime() - broadcastRecord.enqueueRealTime);
            com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            android.content.pm.UserInfo userInfo = userManagerInternal != null ? userManagerInternal.getUserInfo(broadcastRecord.userId) : null;
            if (userInfo == null) {
                i2 = 0;
            } else {
                i2 = com.android.server.pm.UserJourneyLogger.getUserTypeForStatsd(userInfo.userType);
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED action:");
            sb.append(action);
            sb.append(" dispatchLatency:");
            sb.append(i3);
            sb.append(" completeLatency:");
            sb.append(uptimeMillis);
            sb.append(" dispatchRealLatency:");
            sb.append(i4);
            sb.append(" completeRealLatency:");
            sb.append(elapsedRealtime);
            sb.append(" receiversSize:");
            sb.append(size);
            sb.append(" userId:");
            sb.append(broadcastRecord.userId);
            sb.append(" userType:");
            sb.append(userInfo != null ? userInfo.userType : null);
            android.util.Slog.i("BroadcastQueue", sb.toString());
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_COMPLETED_BROADCAST_COMPLETION_LATENCY_REPORTED, i, i3, uptimeMillis, i4, elapsedRealtime, broadcastRecord.userId, i2);
        }
    }

    private void maybeReportBroadcastDispatchedEventLocked(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        java.lang.String targetPackage;
        if (broadcastRecord.options == null || broadcastRecord.options.getIdForResponseEvent() <= 0 || (targetPackage = getTargetPackage(broadcastRecord)) == null) {
            return;
        }
        this.mService.mUsageStatsService.reportBroadcastDispatched(broadcastRecord.callingUid, targetPackage, android.os.UserHandle.of(broadcastRecord.userId), broadcastRecord.options.getIdForResponseEvent(), android.os.SystemClock.elapsedRealtime(), this.mService.getUidStateLocked(i));
    }

    private void maybeAddBackgroundStartPrivileges(com.android.server.am.ProcessRecord processRecord, com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord == null || processRecord == null || !broadcastRecord.mBackgroundStartPrivileges.allowsAny()) {
            return;
        }
        this.mHandler.removeCallbacksAndMessages((processRecord.toShortString() + broadcastRecord.toString()).intern());
        processRecord.addOrUpdateBackgroundStartPrivileges(broadcastRecord, broadcastRecord.mBackgroundStartPrivileges);
    }

    final void setBroadcastTimeoutLocked(long j) {
        if (!this.mPendingBroadcastTimeoutMessage) {
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(201, this), j);
            this.mPendingBroadcastTimeoutMessage = true;
        }
    }

    final void cancelBroadcastTimeoutLocked() {
        if (this.mPendingBroadcastTimeoutMessage) {
            this.mHandler.removeMessages(201, this);
            this.mPendingBroadcastTimeoutMessage = false;
        }
    }

    final void broadcastTimeoutLocked(boolean z) {
        java.lang.Object obj;
        com.android.server.am.ProcessRecord processRecord;
        boolean z2 = false;
        if (z) {
            this.mPendingBroadcastTimeoutMessage = false;
        }
        if (this.mDispatcher.isEmpty() || this.mDispatcher.getActiveBroadcastLocked() == null) {
            return;
        }
        android.os.Trace.traceBegin(64L, "broadcastTimeoutLocked()");
        try {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            com.android.server.am.BroadcastRecord activeBroadcastLocked = this.mDispatcher.getActiveBroadcastLocked();
            if (z) {
                if (!this.mService.mProcessesReady) {
                    return;
                }
                if (activeBroadcastLocked.timeoutExempt) {
                    return;
                }
                long j = activeBroadcastLocked.receiverTime + this.mConstants.TIMEOUT;
                if (j > uptimeMillis) {
                    setBroadcastTimeoutLocked(j);
                    return;
                }
            }
            if (activeBroadcastLocked.state == 4) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Waited long enough for: ");
                sb.append(activeBroadcastLocked.curComponent != null ? activeBroadcastLocked.curComponent.flattenToShortString() : "(null)");
                android.util.Slog.i("BroadcastQueue", sb.toString());
                activeBroadcastLocked.curComponent = null;
                activeBroadcastLocked.state = 0;
                processNextBroadcastLocked(false, false);
                return;
            }
            if (activeBroadcastLocked.curApp != null && activeBroadcastLocked.curApp.isDebugging()) {
                z2 = true;
            }
            long j2 = uptimeMillis - activeBroadcastLocked.receiverTime;
            android.util.Slog.w("BroadcastQueue", "Timeout of broadcast " + activeBroadcastLocked + " - curFilter=" + activeBroadcastLocked.curFilter + " curReceiver=" + activeBroadcastLocked.curReceiver + ", started " + j2 + "ms ago");
            activeBroadcastLocked.receiverTime = uptimeMillis;
            if (!z2) {
                activeBroadcastLocked.anrCount++;
            }
            if (activeBroadcastLocked.nextReceiver > 0) {
                obj = activeBroadcastLocked.receivers.get(activeBroadcastLocked.nextReceiver - 1);
                activeBroadcastLocked.delivery[activeBroadcastLocked.nextReceiver - 1] = 3;
            } else {
                obj = activeBroadcastLocked.curReceiver;
            }
            android.util.Slog.w("BroadcastQueue", "Receiver during timeout of " + activeBroadcastLocked + " : " + obj);
            logBroadcastReceiverDiscardLocked(activeBroadcastLocked);
            com.android.internal.os.TimeoutRecord forBroadcastReceiver = com.android.internal.os.TimeoutRecord.forBroadcastReceiver(activeBroadcastLocked.intent, j2);
            if (obj == null || !(obj instanceof com.android.server.am.BroadcastFilter)) {
                processRecord = activeBroadcastLocked.curApp;
            } else {
                com.android.server.am.BroadcastFilter broadcastFilter = (com.android.server.am.BroadcastFilter) obj;
                if (broadcastFilter.receiverList.pid == 0 || broadcastFilter.receiverList.pid == com.android.server.am.ActivityManagerService.MY_PID) {
                    processRecord = null;
                } else {
                    forBroadcastReceiver.mLatencyTracker.waitingOnPidLockStarted();
                    synchronized (this.mService.mPidsSelfLocked) {
                        forBroadcastReceiver.mLatencyTracker.waitingOnPidLockEnded();
                        processRecord = this.mService.mPidsSelfLocked.get(broadcastFilter.receiverList.pid);
                    }
                }
            }
            if (this.mPendingBroadcast == activeBroadcastLocked) {
                this.mPendingBroadcast = null;
            }
            finishReceiverLocked(activeBroadcastLocked, activeBroadcastLocked.resultCode, activeBroadcastLocked.resultData, activeBroadcastLocked.resultExtras, activeBroadcastLocked.resultAbort, false);
            scheduleBroadcastsLocked();
            if (!z2 && processRecord != null) {
                this.mService.appNotResponding(processRecord, forBroadcastReceiver);
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    private final void addBroadcastToHistoryLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.callingUid < 0) {
            return;
        }
        broadcastRecord.finishTime = android.os.SystemClock.uptimeMillis();
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.asyncTraceEnd(64L, createBroadcastTraceTitle(broadcastRecord, 1), java.lang.System.identityHashCode(broadcastRecord));
        }
        this.mService.notifyBroadcastFinishedLocked(broadcastRecord);
        this.mHistory.addBroadcastToHistoryLocked(broadcastRecord);
    }

    @Override // com.android.server.am.BroadcastQueue
    public boolean cleanupDisabledPackageReceiversLocked(java.lang.String str, java.util.Set<java.lang.String> set, int i) {
        boolean z = false;
        for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
            z |= this.mParallelBroadcasts.get(size).cleanupDisabledPackageReceiversLocked(str, set, i, true);
        }
        return this.mDispatcher.cleanupDisabledPackageReceiversLocked(str, set, i, true) | z;
    }

    final void logBroadcastReceiverDiscardLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        int i = broadcastRecord.nextReceiver - 1;
        if (i >= 0 && i < broadcastRecord.receivers.size()) {
            java.lang.Object obj = broadcastRecord.receivers.get(i);
            if (obj instanceof com.android.server.am.BroadcastFilter) {
                com.android.server.am.BroadcastFilter broadcastFilter = (com.android.server.am.BroadcastFilter) obj;
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_BROADCAST_DISCARD_FILTER, java.lang.Integer.valueOf(broadcastFilter.owningUserId), java.lang.Integer.valueOf(java.lang.System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(java.lang.System.identityHashCode(broadcastFilter)));
                return;
            } else {
                android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) obj;
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_BROADCAST_DISCARD_APP, java.lang.Integer.valueOf(android.os.UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid)), java.lang.Integer.valueOf(java.lang.System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), java.lang.Integer.valueOf(i), resolveInfo.toString());
                return;
            }
        }
        if (i < 0) {
            android.util.Slog.w("BroadcastQueue", "Discarding broadcast before first receiver is invoked: " + broadcastRecord);
        }
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_BROADCAST_DISCARD_APP, -1, java.lang.Integer.valueOf(java.lang.System.identityHashCode(broadcastRecord)), broadcastRecord.intent.getAction(), java.lang.Integer.valueOf(broadcastRecord.nextReceiver), "NONE");
    }

    private java.lang.String createBroadcastTraceTitle(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return android.text.TextUtils.formatSimple("Broadcast %s from %s (%s) %s", new java.lang.Object[]{i == 0 ? "in queue" : "dispatched", broadcastRecord.callerPackage == null ? "" : broadcastRecord.callerPackage, broadcastRecord.callerApp == null ? "process unknown" : broadcastRecord.callerApp.toShortString(), broadcastRecord.intent != null ? broadcastRecord.intent.getAction() : ""});
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isIdleLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForIdle$1() {
        return this.mParallelBroadcasts.isEmpty() && this.mDispatcher.isIdle() && this.mPendingBroadcast == null;
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isBeyondBarrierLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForBarrier$2(long j) {
        if (lambda$waitForIdle$1()) {
            return true;
        }
        for (int i = 0; i < this.mParallelBroadcasts.size(); i++) {
            if (this.mParallelBroadcasts.get(i).enqueueTime <= j) {
                return false;
            }
        }
        com.android.server.am.BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked != null && pendingBroadcastLocked.enqueueTime <= j) {
            return false;
        }
        return this.mDispatcher.isBeyondBarrier(j);
    }

    @Override // com.android.server.am.BroadcastQueue
    /* renamed from: isDispatchedLocked, reason: merged with bridge method [inline-methods] */
    public boolean lambda$waitForDispatched$3(android.content.Intent intent) {
        if (lambda$waitForIdle$1()) {
            return true;
        }
        for (int i = 0; i < this.mParallelBroadcasts.size(); i++) {
            if (intent.filterEquals(this.mParallelBroadcasts.get(i).intent)) {
                return false;
            }
        }
        com.android.server.am.BroadcastRecord pendingBroadcastLocked = getPendingBroadcastLocked();
        if (pendingBroadcastLocked != null && intent.filterEquals(pendingBroadcastLocked.intent)) {
            return false;
        }
        return this.mDispatcher.isDispatched(intent);
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForIdle(java.io.PrintWriter printWriter) {
        waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForIdle$1;
                lambda$waitForIdle$1 = com.android.server.am.BroadcastQueueImpl.this.lambda$waitForIdle$1();
                return lambda$waitForIdle$1;
            }
        }, printWriter, "idle");
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForBarrier(java.io.PrintWriter printWriter) {
        final long uptimeMillis = android.os.SystemClock.uptimeMillis();
        waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForBarrier$2;
                lambda$waitForBarrier$2 = com.android.server.am.BroadcastQueueImpl.this.lambda$waitForBarrier$2(uptimeMillis);
                return lambda$waitForBarrier$2;
            }
        }, printWriter, "barrier");
    }

    @Override // com.android.server.am.BroadcastQueue
    public void waitForDispatched(final android.content.Intent intent, java.io.PrintWriter printWriter) {
        waitFor(new java.util.function.BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$waitForDispatched$3;
                lambda$waitForDispatched$3 = com.android.server.am.BroadcastQueueImpl.this.lambda$waitForDispatched$3(intent);
                return lambda$waitForDispatched$3;
            }
        }, printWriter, "dispatch");
    }

    private void waitFor(java.util.function.BooleanSupplier booleanSupplier, java.io.PrintWriter printWriter, java.lang.String str) {
        long j = 0;
        while (true) {
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (booleanSupplier.getAsBoolean()) {
                        break;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis >= 1000 + j) {
                java.lang.String str2 = "Queue [" + this.mQueueName + "] waiting for " + str + " condition; state is " + describeStateLocked();
                android.util.Slog.v("BroadcastQueue", str2);
                if (printWriter != null) {
                    printWriter.println(str2);
                    printWriter.flush();
                }
                j = uptimeMillis;
            }
            cancelDeferrals();
            android.os.SystemClock.sleep(100L);
        }
        java.lang.String str3 = "Queue [" + this.mQueueName + "] reached " + str + " condition";
        android.util.Slog.v("BroadcastQueue", str3);
        if (printWriter != null) {
            printWriter.println(str3);
            printWriter.flush();
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void cancelDeferrals() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mDispatcher.cancelDeferralsLocked();
                scheduleBroadcastsLocked();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.am.BroadcastQueue
    public java.lang.String describeStateLocked() {
        return this.mParallelBroadcasts.size() + " parallel; " + this.mDispatcher.describeStateLocked();
    }

    @Override // com.android.server.am.BroadcastQueue
    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mQueueName);
        for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
            this.mParallelBroadcasts.get(size).dumpDebug(protoOutputStream, 2246267895810L);
        }
        this.mDispatcher.dumpDebug(protoOutputStream, 2246267895811L);
        if (this.mPendingBroadcast != null) {
            this.mPendingBroadcast.dumpDebug(protoOutputStream, 1146756268036L);
        }
        this.mHistory.dumpDebug(protoOutputStream);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.am.BroadcastQueue
    @dalvik.annotation.optimization.NeverCompile
    public boolean dumpLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, boolean z2, boolean z3, java.lang.String str, boolean z4) {
        boolean z5;
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (this.mParallelBroadcasts.isEmpty() && this.mDispatcher.isEmpty() && this.mPendingBroadcast == null) {
            z5 = z4;
        } else {
            boolean z6 = false;
            boolean z7 = z4;
            for (int size = this.mParallelBroadcasts.size() - 1; size >= 0; size--) {
                com.android.server.am.BroadcastRecord broadcastRecord = this.mParallelBroadcasts.get(size);
                if (str == null || str.equals(broadcastRecord.callerPackage)) {
                    if (!z6) {
                        if (z7) {
                            printWriter.println();
                        }
                        printWriter.println("  Active broadcasts [" + this.mQueueName + "]:");
                        z7 = true;
                        z6 = true;
                    }
                    printWriter.println("  Active Broadcast " + this.mQueueName + " #" + size + ":");
                    broadcastRecord.dump(printWriter, "    ", simpleDateFormat);
                }
            }
            this.mDispatcher.dumpLocked(printWriter, str, this.mQueueName, simpleDateFormat);
            if (str == null || (this.mPendingBroadcast != null && str.equals(this.mPendingBroadcast.callerPackage))) {
                printWriter.println();
                printWriter.println("  Pending broadcast [" + this.mQueueName + "]:");
                if (this.mPendingBroadcast != null) {
                    this.mPendingBroadcast.dump(printWriter, "    ", simpleDateFormat);
                } else {
                    printWriter.println("    (null)");
                }
                z5 = true;
            } else {
                z5 = z7;
            }
        }
        if (z) {
            this.mConstants.dump(new android.util.IndentingPrintWriter(printWriter));
        }
        if (z2) {
            return this.mHistory.dumpLocked(printWriter, str, this.mQueueName, simpleDateFormat, z3, z5);
        }
        return z5;
    }
}
