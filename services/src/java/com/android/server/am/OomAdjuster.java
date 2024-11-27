package com.android.server.am;

/* loaded from: classes.dex */
public class OomAdjuster {
    static final long CAMERA_MICROPHONE_CAPABILITY_CHANGE_ID = 136219221;
    static final long PROCESS_CAPABILITY_CHANGE_ID = 136274596;
    static final java.lang.String TAG = "OomAdjuster";
    static final long USE_SHORT_FGS_USAGE_INTERACTION_TIME = 183972877;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    com.android.server.am.ActiveUids mActiveUids;
    int mAdjSeq;
    com.android.server.am.CacheOomRanker mCacheOomRanker;
    com.android.server.am.CachedAppOptimizer mCachedAppOptimizer;
    com.android.server.am.ActivityManagerConstants mConstants;
    private double mLastFreeSwapPercent;
    int mNewNumAServiceProcs;
    int mNewNumServiceProcs;
    private long mNextNoKillDebugMessageTime;
    int mNumCachedHiddenProcs;
    int mNumNonCachedProcs;
    int mNumServiceProcs;
    private final int mNumSlots;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mOomAdjUpdateOngoing;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mPendingFullOomAdjUpdate;
    protected final android.util.ArraySet<com.android.server.am.ProcessRecord> mPendingProcessSet;
    final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final android.os.Handler mProcessGroupHandler;
    final com.android.server.am.ProcessList mProcessList;
    protected final android.util.ArraySet<com.android.server.am.ProcessRecord> mProcessesInCycle;
    final com.android.server.am.ActivityManagerService mService;
    protected final java.util.ArrayList<com.android.server.am.UidRecord> mTmpBecameIdle;
    protected final com.android.server.am.OomAdjuster.ComputeOomAdjWindowCallback mTmpComputeOomAdjWindowCallback;
    final long[] mTmpLong;
    protected final java.util.ArrayList<com.android.server.am.ProcessRecord> mTmpProcessList;
    protected final java.util.ArrayList<com.android.server.am.ProcessRecord> mTmpProcessList2;
    protected final android.util.ArraySet<com.android.server.am.ProcessRecord> mTmpProcessSet;
    protected final java.util.ArrayDeque<com.android.server.am.ProcessRecord> mTmpQueue;
    protected final int[] mTmpSchedGroup;
    protected final com.android.server.am.ActiveUids mTmpUidRecords;

    public static final int oomAdjReasonToProto(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            case 11:
                return 11;
            case 12:
                return 12;
            case 13:
                return 13;
            case 14:
                return 14;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            case 21:
                return 21;
            case 22:
                return 22;
            default:
                return -1;
        }
    }

    public static final java.lang.String oomAdjReasonToString(int i) {
        switch (i) {
            case 0:
                return "updateOomAdj_meh";
            case 1:
                return "updateOomAdj_activityChange";
            case 2:
                return "updateOomAdj_finishReceiver";
            case 3:
                return "updateOomAdj_startReceiver";
            case 4:
                return "updateOomAdj_bindService";
            case 5:
                return "updateOomAdj_unbindService";
            case 6:
                return "updateOomAdj_startService";
            case 7:
                return "updateOomAdj_getProvider";
            case 8:
                return "updateOomAdj_removeProvider";
            case 9:
                return "updateOomAdj_uiVisibility";
            case 10:
                return "updateOomAdj_allowlistChange";
            case 11:
                return "updateOomAdj_processBegin";
            case 12:
                return "updateOomAdj_processEnd";
            case 13:
                return "updateOomAdj_shortFgs";
            case 14:
                return "updateOomAdj_systemInit";
            case 15:
                return "updateOomAdj_backup";
            case 16:
                return "updateOomAdj_shell";
            case 17:
                return "updateOomAdj_removeTask";
            case 18:
                return "updateOomAdj_uidIdle";
            case 19:
                return "updateOomAdj_stopService";
            case 20:
                return "updateOomAdj_executingService";
            case 21:
                return "updateOomAdj_restrictionChange";
            case 22:
                return "updateOomAdj_componentDisabled";
            default:
                return "_unknown";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isChangeEnabled(int i, android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        com.android.server.am.PlatformCompatCache.getInstance();
        return com.android.server.am.PlatformCompatCache.isChangeEnabled(i, applicationInfo, z);
    }

    OomAdjuster(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.ProcessList processList, com.android.server.am.ActiveUids activeUids) {
        this(activityManagerService, processList, activeUids, createAdjusterThread());
    }

    static com.android.server.ServiceThread createAdjusterThread() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, -10, false);
        serviceThread.start();
        return serviceThread;
    }

    OomAdjuster(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.ProcessList processList, com.android.server.am.ActiveUids activeUids, com.android.server.ServiceThread serviceThread) {
        this.mTmpLong = new long[3];
        this.mAdjSeq = 0;
        this.mNumServiceProcs = 0;
        this.mNewNumAServiceProcs = 0;
        this.mNewNumServiceProcs = 0;
        this.mNumNonCachedProcs = 0;
        this.mNumCachedHiddenProcs = 0;
        this.mTmpSchedGroup = new int[1];
        this.mTmpProcessList = new java.util.ArrayList<>();
        this.mTmpProcessList2 = new java.util.ArrayList<>();
        this.mTmpBecameIdle = new java.util.ArrayList<>();
        this.mTmpProcessSet = new android.util.ArraySet<>();
        this.mPendingProcessSet = new android.util.ArraySet<>();
        this.mProcessesInCycle = new android.util.ArraySet<>();
        this.mOomAdjUpdateOngoing = false;
        this.mPendingFullOomAdjUpdate = false;
        this.mLastFreeSwapPercent = 1.0d;
        this.mTmpComputeOomAdjWindowCallback = new com.android.server.am.OomAdjuster.ComputeOomAdjWindowCallback();
        this.mService = activityManagerService;
        this.mProcessList = processList;
        this.mProcLock = activityManagerService.mProcLock;
        this.mActiveUids = activeUids;
        this.mConstants = this.mService.mConstants;
        this.mCachedAppOptimizer = new com.android.server.am.CachedAppOptimizer(this.mService);
        this.mCacheOomRanker = new com.android.server.am.CacheOomRanker(activityManagerService);
        this.mProcessGroupHandler = new android.os.Handler(serviceThread.getLooper(), new android.os.Handler.Callback() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.am.OomAdjuster.lambda$new$0(message);
                return lambda$new$0;
            }
        });
        this.mTmpUidRecords = new com.android.server.am.ActiveUids(activityManagerService, false);
        this.mTmpQueue = new java.util.ArrayDeque<>(this.mConstants.CUR_MAX_CACHED_PROCESSES << 1);
        this.mNumSlots = 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(android.os.Message message) {
        int i = message.arg1;
        int i2 = message.arg2;
        if (i == com.android.server.am.ActivityManagerService.MY_PID) {
            return true;
        }
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.traceBegin(64L, "setProcessGroup " + message.obj + " to " + i2);
        }
        try {
            android.os.Process.setProcessGroup(i, i2);
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            throw th;
        }
        android.os.Trace.traceEnd(64L);
        return true;
    }

    void initSettings() {
        this.mCachedAppOptimizer.init();
        this.mCacheOomRanker.init(android.app.ActivityThread.currentApplication().getMainExecutor());
        if (this.mService.mConstants.KEEP_WARMING_SERVICES.size() > 0) {
            this.mService.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.am.OomAdjuster.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.OomAdjuster.this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            com.android.server.am.OomAdjuster.this.handleUserSwitchedLocked();
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }, new android.content.IntentFilter("android.intent.action.USER_SWITCHED"), null, this.mService.mHandler);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    @com.android.internal.annotations.VisibleForTesting
    void handleUserSwitchedLocked() {
        this.mProcessList.forEachLruProcessesLOSP(false, new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.OomAdjuster.this.updateKeepWarmIfNecessaryForProcessLocked((com.android.server.am.ProcessRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mService"})
    public void updateKeepWarmIfNecessaryForProcessLocked(com.android.server.am.ProcessRecord processRecord) {
        boolean z;
        android.util.ArraySet<android.content.ComponentName> arraySet = this.mService.mConstants.KEEP_WARMING_SERVICES;
        com.android.server.am.PackageList pkgList = processRecord.getPkgList();
        int size = arraySet.size() - 1;
        while (true) {
            if (size >= 0) {
                if (pkgList.containsKey(arraySet.valueAt(size).getPackageName())) {
                    z = true;
                    break;
                }
                size--;
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            return;
        }
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            processServiceRecord.getRunningServiceAt(numberOfRunningServices).updateKeepWarmLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private boolean performUpdateOomAdjLSP(com.android.server.am.ProcessRecord processRecord, int i, com.android.server.am.ProcessRecord processRecord2, long j, int i2) {
        if (processRecord.getThread() == null) {
            return false;
        }
        processRecord.mState.resetCachedInfo();
        processRecord.mState.setCurBoundByNonBgRestrictedApp(false);
        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null) {
            uidRecord.reset();
        }
        this.mPendingProcessSet.remove(processRecord);
        this.mProcessesInCycle.clear();
        computeOomAdjLSP(processRecord, i, processRecord2, false, j, false, true, i2, true);
        if (!this.mProcessesInCycle.isEmpty()) {
            for (int size = this.mProcessesInCycle.size() - 1; size >= 0; size--) {
                this.mProcessesInCycle.valueAt(size).mState.setCompletedAdjSeq(this.mAdjSeq - 1);
            }
            return true;
        }
        if (uidRecord != null) {
            uidRecord.forEachProcess(new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.OomAdjuster.this.updateAppUidRecIfNecessaryLSP((com.android.server.am.ProcessRecord) obj);
                }
            });
            if (uidRecord.getCurProcState() != 20 && (uidRecord.getSetProcState() != uidRecord.getCurProcState() || uidRecord.getSetCapability() != uidRecord.getCurCapability() || uidRecord.isSetAllowListed() != uidRecord.isCurAllowListed())) {
                com.android.server.am.ActiveUids activeUids = this.mTmpUidRecords;
                activeUids.clear();
                activeUids.put(uidRecord.getUid(), uidRecord);
                updateUidsLSP(activeUids, android.os.SystemClock.elapsedRealtime());
            }
        }
        return applyOomAdjLSP(processRecord, false, j, android.os.SystemClock.elapsedRealtime(), i2);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateOomAdjLocked(int i) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjLSP(i);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void updateOomAdjLSP(int i) {
        if (checkAndEnqueueOomAdjTargetLocked(null)) {
            return;
        }
        try {
            this.mOomAdjUpdateOngoing = true;
            performUpdateOomAdjLSP(i);
        } finally {
            this.mOomAdjUpdateOngoing = false;
            updateOomAdjPendingTargetsLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void performUpdateOomAdjLSP(int i) {
        com.android.server.am.ProcessRecord topApp = this.mService.getTopApp();
        this.mPendingProcessSet.clear();
        com.android.server.am.AppProfiler appProfiler = this.mService.mAppProfiler;
        this.mService.mAppProfiler.mHasHomeProcess = false;
        appProfiler.mHasPreviousProcess = false;
        updateOomAdjInnerLSP(i, topApp, null, null, true, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean updateOomAdjLocked(com.android.server.am.ProcessRecord processRecord, int i) {
        boolean updateOomAdjLSP;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjLSP = updateOomAdjLSP(processRecord, i);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return updateOomAdjLSP;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private boolean updateOomAdjLSP(com.android.server.am.ProcessRecord processRecord, int i) {
        if (processRecord == null || !this.mConstants.OOMADJ_UPDATE_QUICK) {
            updateOomAdjLSP(i);
            return true;
        }
        if (checkAndEnqueueOomAdjTargetLocked(processRecord)) {
            return true;
        }
        try {
            this.mOomAdjUpdateOngoing = true;
            return performUpdateOomAdjLSP(processRecord, i);
        } finally {
            this.mOomAdjUpdateOngoing = false;
            updateOomAdjPendingTargetsLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected boolean performUpdateOomAdjLSP(com.android.server.am.ProcessRecord processRecord, int i) {
        com.android.server.am.ProcessRecord topApp = this.mService.getTopApp();
        android.os.Trace.traceBegin(64L, oomAdjReasonToString(i));
        this.mService.mOomAdjProfiler.oomAdjStarted();
        this.mAdjSeq++;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        boolean isCached = processStateRecord.isCached();
        int curRawAdj = processStateRecord.getCurRawAdj();
        int i2 = curRawAdj >= 900 ? curRawAdj : 1001;
        boolean isProcStateBackground = android.app.ActivityManager.isProcStateBackground(processStateRecord.getSetProcState());
        int setCapability = processStateRecord.getSetCapability();
        processStateRecord.setContainsCycle(false);
        processStateRecord.setProcStateChanged(false);
        processStateRecord.resetCachedInfo();
        processStateRecord.setCurBoundByNonBgRestrictedApp(false);
        this.mPendingProcessSet.remove(processRecord);
        processRecord.mOptRecord.setLastOomAdjChangeReason(i);
        boolean performUpdateOomAdjLSP = performUpdateOomAdjLSP(processRecord, i2, topApp, android.os.SystemClock.uptimeMillis(), i);
        if (!performUpdateOomAdjLSP || (isCached == processStateRecord.isCached() && curRawAdj != -10000 && this.mProcessesInCycle.isEmpty() && setCapability == processStateRecord.getCurCapability() && isProcStateBackground == android.app.ActivityManager.isProcStateBackground(processStateRecord.getSetProcState()))) {
            this.mProcessesInCycle.clear();
            this.mService.mOomAdjProfiler.oomAdjEnded();
            android.os.Trace.traceEnd(64L);
            return performUpdateOomAdjLSP;
        }
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mTmpProcessList;
        com.android.server.am.ActiveUids activeUids = this.mTmpUidRecords;
        this.mPendingProcessSet.add(processRecord);
        for (int size = this.mProcessesInCycle.size() - 1; size >= 0; size--) {
            this.mPendingProcessSet.add(this.mProcessesInCycle.valueAt(size));
        }
        this.mProcessesInCycle.clear();
        boolean collectReachableProcessesLocked = collectReachableProcessesLocked(this.mPendingProcessSet, arrayList, activeUids);
        this.mPendingProcessSet.clear();
        if (!collectReachableProcessesLocked) {
            arrayList.remove(processRecord);
        }
        if (arrayList.size() > 0) {
            this.mAdjSeq--;
            updateOomAdjInnerLSP(i, topApp, arrayList, activeUids, collectReachableProcessesLocked, false);
        } else if (processStateRecord.getCurRawAdj() == 1001) {
            arrayList.add(processRecord);
            assignCachedAdjIfNecessary(arrayList);
            applyOomAdjLSP(processRecord, false, android.os.SystemClock.uptimeMillis(), android.os.SystemClock.elapsedRealtime(), i);
        }
        this.mTmpProcessList.clear();
        this.mService.mOomAdjProfiler.oomAdjEnded();
        android.os.Trace.traceEnd(64L);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected boolean collectReachableProcessesLocked(android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet, java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList, com.android.server.am.ActiveUids activeUids) {
        java.util.ArrayDeque<com.android.server.am.ProcessRecord> arrayDeque = this.mTmpQueue;
        arrayDeque.clear();
        arrayList.clear();
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.ProcessRecord valueAt = arraySet.valueAt(i);
            valueAt.mState.setReachable(true);
            arrayDeque.offer(valueAt);
        }
        activeUids.clear();
        boolean z = false;
        for (com.android.server.am.ProcessRecord poll = arrayDeque.poll(); poll != null; poll = arrayDeque.poll()) {
            arrayList.add(poll);
            com.android.server.am.UidRecord uidRecord = poll.getUidRecord();
            if (uidRecord != null) {
                activeUids.put(uidRecord.getUid(), uidRecord);
            }
            com.android.server.am.ProcessServiceRecord processServiceRecord = poll.mServices;
            for (int numberOfConnections = processServiceRecord.numberOfConnections() - 1; numberOfConnections >= 0; numberOfConnections--) {
                com.android.server.am.ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(numberOfConnections);
                com.android.server.am.ProcessRecord processRecord = connectionAt.hasFlag(2) ? connectionAt.binding.service.isolationHostProc : connectionAt.binding.service.app;
                if (processRecord != null && processRecord != poll && (processRecord.mState.getMaxAdj() < -900 || processRecord.mState.getMaxAdj() >= 0)) {
                    z |= processRecord.mState.isReachable();
                    if (!processRecord.mState.isReachable() && (!connectionAt.hasFlag(32) || !connectionAt.notHasFlag(134217856))) {
                        arrayDeque.offer(processRecord);
                        processRecord.mState.setReachable(true);
                    }
                }
            }
            com.android.server.am.ProcessProviderRecord processProviderRecord = poll.mProviders;
            for (int numberOfProviderConnections = processProviderRecord.numberOfProviderConnections() - 1; numberOfProviderConnections >= 0; numberOfProviderConnections--) {
                com.android.server.am.ProcessRecord processRecord2 = processProviderRecord.getProviderConnectionAt(numberOfProviderConnections).provider.proc;
                if (processRecord2 != null && processRecord2 != poll && (processRecord2.mState.getMaxAdj() < -900 || processRecord2.mState.getMaxAdj() >= 0)) {
                    z |= processRecord2.mState.isReachable();
                    if (!processRecord2.mState.isReachable()) {
                        arrayDeque.offer(processRecord2);
                        processRecord2.mState.setReachable(true);
                    }
                }
            }
            java.util.List<com.android.server.am.ProcessRecord> sdkSandboxProcessesForAppLocked = this.mProcessList.getSdkSandboxProcessesForAppLocked(poll.uid);
            for (int size2 = (sdkSandboxProcessesForAppLocked != null ? sdkSandboxProcessesForAppLocked.size() : 0) - 1; size2 >= 0; size2--) {
                com.android.server.am.ProcessRecord processRecord3 = sdkSandboxProcessesForAppLocked.get(size2);
                z |= processRecord3.mState.isReachable();
                if (!processRecord3.mState.isReachable()) {
                    arrayDeque.offer(processRecord3);
                    processRecord3.mState.setReachable(true);
                }
            }
            if (poll.isSdkSandbox) {
                for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
                    android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = processServiceRecord.getRunningServiceAt(numberOfRunningServices).getConnections();
                    for (int size3 = connections.size() - 1; size3 >= 0; size3--) {
                        java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt2 = connections.valueAt(size3);
                        for (int size4 = valueAt2.size() - 1; size4 >= 0; size4--) {
                            com.android.server.am.ProcessRecord processRecord4 = valueAt2.get(size4).binding.attributedClient;
                            if (processRecord4 != null && processRecord4 != poll && ((processRecord4.mState.getMaxAdj() < -900 || processRecord4.mState.getMaxAdj() >= 0) && !processRecord4.mState.isReachable())) {
                                arrayDeque.offer(processRecord4);
                                processRecord4.mState.setReachable(true);
                            }
                        }
                    }
                }
            }
        }
        int size5 = arrayList.size();
        if (size5 > 0) {
            int i2 = 0;
            for (int i3 = size5 - 1; i2 < i3; i3--) {
                com.android.server.am.ProcessRecord processRecord5 = arrayList.get(i2);
                com.android.server.am.ProcessRecord processRecord6 = arrayList.get(i3);
                processRecord5.mState.setReachable(false);
                processRecord6.mState.setReachable(false);
                arrayList.set(i2, processRecord6);
                arrayList.set(i3, processRecord5);
                i2++;
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void enqueueOomAdjTargetLocked(com.android.server.am.ProcessRecord processRecord) {
        if (processRecord != null && processRecord.mState.getMaxAdj() > 0) {
            this.mPendingProcessSet.add(processRecord);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void removeOomAdjTargetLocked(com.android.server.am.ProcessRecord processRecord, boolean z) {
        if (processRecord != null) {
            this.mPendingProcessSet.remove(processRecord);
            if (z) {
                com.android.server.am.PlatformCompatCache.getInstance().invalidate(processRecord.info);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean checkAndEnqueueOomAdjTargetLocked(@android.annotation.Nullable com.android.server.am.ProcessRecord processRecord) {
        if (!this.mOomAdjUpdateOngoing) {
            return false;
        }
        if (processRecord != null) {
            this.mPendingProcessSet.add(processRecord);
        } else {
            this.mPendingFullOomAdjUpdate = true;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateOomAdjPendingTargetsLocked(int i) {
        if (this.mPendingFullOomAdjUpdate) {
            this.mPendingFullOomAdjUpdate = false;
            this.mPendingProcessSet.clear();
            updateOomAdjLocked(i);
        } else {
            if (this.mPendingProcessSet.isEmpty() || this.mOomAdjUpdateOngoing) {
                return;
            }
            try {
                this.mOomAdjUpdateOngoing = true;
                performUpdateOomAdjPendingTargetsLocked(i);
            } finally {
                this.mOomAdjUpdateOngoing = false;
                updateOomAdjPendingTargetsLocked(i);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void performUpdateOomAdjPendingTargetsLocked(int i) {
        com.android.server.am.ProcessRecord topApp = this.mService.getTopApp();
        android.os.Trace.traceBegin(64L, oomAdjReasonToString(i));
        this.mService.mOomAdjProfiler.oomAdjStarted();
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mTmpProcessList;
        com.android.server.am.ActiveUids activeUids = this.mTmpUidRecords;
        collectReachableProcessesLocked(this.mPendingProcessSet, arrayList, activeUids);
        this.mPendingProcessSet.clear();
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateOomAdjInnerLSP(i, topApp, arrayList, activeUids, true, false);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        arrayList.clear();
        this.mService.mOomAdjProfiler.oomAdjEnded();
        android.os.Trace.traceEnd(64L);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void updateOomAdjInnerLSP(int i, com.android.server.am.ProcessRecord processRecord, java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList, com.android.server.am.ActiveUids activeUids, boolean z, boolean z2) {
        com.android.server.am.ActiveUids activeUids2;
        int i2;
        int i3;
        int i4;
        long j;
        com.android.server.am.ActiveUids activeUids3;
        boolean z3 = arrayList == null;
        java.util.ArrayList<com.android.server.am.ProcessRecord> lruProcessesLOSP = z3 ? this.mProcessList.getLruProcessesLOSP() : arrayList;
        if (activeUids != null) {
            activeUids2 = activeUids;
        } else {
            int size = this.mActiveUids.size();
            com.android.server.am.ActiveUids activeUids4 = this.mTmpUidRecords;
            activeUids4.clear();
            for (int i5 = 0; i5 < size; i5++) {
                com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i5);
                activeUids4.put(valueAt.getUid(), valueAt);
            }
            activeUids2 = activeUids4;
        }
        long j2 = 64;
        if (z2) {
            android.os.Trace.traceBegin(64L, oomAdjReasonToString(i));
            this.mService.mOomAdjProfiler.oomAdjStarted();
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j3 = uptimeMillis - this.mConstants.mMaxEmptyTimeMillis;
        int size2 = lruProcessesLOSP.size();
        this.mAdjSeq++;
        if (z3) {
            this.mNewNumServiceProcs = 0;
            this.mNewNumAServiceProcs = 0;
        }
        resetUidRecordsLsp(activeUids2);
        boolean z4 = z3 || z;
        int i6 = size2 - 1;
        for (int i7 = i6; i7 >= 0; i7--) {
            com.android.server.am.ProcessStateRecord processStateRecord = lruProcessesLOSP.get(i7).mState;
            processStateRecord.setReachable(false);
            if (processStateRecord.getAdjSeq() != this.mAdjSeq) {
                processStateRecord.setContainsCycle(false);
                processStateRecord.setCurRawProcState(19);
                processStateRecord.setCurRawAdj(1001);
                processStateRecord.setSetCapability(0);
                processStateRecord.resetCachedInfo();
                processStateRecord.setCurBoundByNonBgRestrictedApp(false);
            }
        }
        this.mProcessesInCycle.clear();
        int i8 = i6;
        boolean z5 = false;
        while (i8 >= 0) {
            com.android.server.am.ProcessRecord processRecord2 = lruProcessesLOSP.get(i8);
            com.android.server.am.ProcessStateRecord processStateRecord2 = processRecord2.mState;
            if (processRecord2.isKilledByAm() || processRecord2.getThread() == null) {
                i3 = i8;
                i4 = size2;
                j = j2;
                activeUids3 = activeUids2;
            } else {
                processStateRecord2.setProcStateChanged(false);
                processRecord2.mOptRecord.setLastOomAdjChangeReason(i);
                i3 = i8;
                i4 = size2;
                j = j2;
                activeUids3 = activeUids2;
                computeOomAdjLSP(processRecord2, 1001, processRecord, z3, uptimeMillis, false, z4, i, true);
                z5 |= processStateRecord2.containsCycle();
                processStateRecord2.setCompletedAdjSeq(this.mAdjSeq);
            }
            i8 = i3 - 1;
            size2 = i4;
            activeUids2 = activeUids3;
            j2 = j;
        }
        int i9 = size2;
        long j4 = j2;
        com.android.server.am.ActiveUids activeUids5 = activeUids2;
        if (this.mCacheOomRanker.useOomReranking()) {
            this.mCacheOomRanker.reRankLruCachedAppsLSP(this.mProcessList.getLruProcessesLSP(), this.mProcessList.getLruProcessServiceStartLOSP());
        }
        if (z4) {
            int i10 = 0;
            while (z5 && i10 < 10) {
                int i11 = i10 + 1;
                for (int i12 = 0; i12 < i9; i12++) {
                    com.android.server.am.ProcessRecord processRecord3 = lruProcessesLOSP.get(i12);
                    com.android.server.am.ProcessStateRecord processStateRecord3 = processRecord3.mState;
                    if (!processRecord3.isKilledByAm() && processRecord3.getThread() != null && processStateRecord3.containsCycle()) {
                        processStateRecord3.decAdjSeq();
                        processStateRecord3.decCompletedAdjSeq();
                    }
                }
                int i13 = 0;
                z5 = false;
                while (i13 < i9) {
                    com.android.server.am.ProcessRecord processRecord4 = lruProcessesLOSP.get(i13);
                    com.android.server.am.ProcessStateRecord processStateRecord4 = processRecord4.mState;
                    if (processRecord4.isKilledByAm() || processRecord4.getThread() == null || !processStateRecord4.containsCycle()) {
                        i2 = i13;
                    } else {
                        i2 = i13;
                        if (computeOomAdjLSP(processRecord4, 1001, processRecord, true, uptimeMillis, true, true, i, true)) {
                            z5 = true;
                        }
                    }
                    i13 = i2 + 1;
                }
                i10 = i11;
            }
        }
        this.mProcessesInCycle.clear();
        assignCachedAdjIfNecessary(this.mProcessList.getLruProcessesLOSP());
        postUpdateOomAdjInnerLSP(i, activeUids5, uptimeMillis, elapsedRealtime, j3);
        if (z2) {
            this.mService.mOomAdjProfiler.oomAdjEnded();
            android.os.Trace.traceEnd(j4);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void resetUidRecordsLsp(@android.annotation.NonNull com.android.server.am.ActiveUids activeUids) {
        for (int size = activeUids.size() - 1; size >= 0; size--) {
            activeUids.valueAt(size).reset();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void postUpdateOomAdjInnerLSP(int i, com.android.server.am.ActiveUids activeUids, long j, long j2, long j3) {
        this.mNumNonCachedProcs = 0;
        this.mNumCachedHiddenProcs = 0;
        boolean updateAndTrimProcessLSP = updateAndTrimProcessLSP(j, j2, j3, activeUids, i);
        this.mNumServiceProcs = this.mNewNumServiceProcs;
        if (this.mService.mAlwaysFinishActivities) {
            this.mService.mAtmInternal.scheduleDestroyAllActivities("always-finish");
        }
        if (updateAndTrimProcessLSP) {
            this.mService.mAppProfiler.requestPssAllProcsLPr(j, false, this.mService.mProcessStats.isMemFactorLowered());
        }
        updateUidsLSP(activeUids, j2);
        synchronized (this.mService.mProcessStats.mLock) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mService.mProcessStats.shouldWriteNowLocked(uptimeMillis)) {
                    this.mService.mHandler.post(new com.android.server.am.ActivityManagerService.ProcStatsRunnable(this.mService, this.mService.mProcessStats));
                }
                this.mService.mProcessStats.updateTrackingAssociationsLocked(this.mAdjSeq, uptimeMillis);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void assignCachedAdjIfNecessary(java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList) {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4;
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList2 = arrayList;
        int size = arrayList.size();
        int i5 = 1001;
        if (this.mConstants.USE_TIERED_CACHED_ADJ) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            for (int i6 = size - 1; i6 >= 0; i6--) {
                com.android.server.am.ProcessRecord processRecord = arrayList2.get(i6);
                com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
                com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
                if (!processRecord.isKilledByAm() && processRecord.getThread() != null && processStateRecord.getCurAdj() >= 1001) {
                    com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processCachedOptimizerRecord != null && processCachedOptimizerRecord.isFreezeExempt()) {
                        i4 = 900;
                    } else if (processStateRecord.getSetAdj() >= 900 && processStateRecord.getLastStateTime() + this.mConstants.TIERED_CACHED_ADJ_DECAY_TIME < uptimeMillis) {
                        i4 = com.android.server.am.ProcessList.CACHED_APP_LMK_FIRST_ADJ;
                    } else {
                        i4 = 910;
                    }
                    processStateRecord.setCurRawAdj(i4);
                    processStateRecord.setCurAdj(processServiceRecord.modifyRawOomAdj(i4));
                }
            }
            return;
        }
        int i7 = this.mConstants.CUR_MAX_CACHED_PROCESSES - this.mConstants.CUR_MAX_EMPTY_PROCESSES;
        int i8 = (size - this.mNumNonCachedProcs) - this.mNumCachedHiddenProcs;
        if (i8 <= i7) {
            i7 = i8;
        }
        int i9 = (this.mNumCachedHiddenProcs > 0 ? (this.mNumCachedHiddenProcs + this.mNumSlots) - 1 : 1) / this.mNumSlots;
        if (i9 < 1) {
            i9 = 1;
        }
        int i10 = ((i7 + this.mNumSlots) - 1) / this.mNumSlots;
        if (i10 < 1) {
            i10 = 1;
        }
        int i11 = -1;
        int i12 = 915;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 905;
        int i18 = 900;
        int i19 = 910;
        int i20 = size - 1;
        int i21 = -1;
        while (i20 >= 0) {
            com.android.server.am.ProcessRecord processRecord2 = arrayList2.get(i20);
            com.android.server.am.ProcessStateRecord processStateRecord2 = processRecord2.mState;
            if (processRecord2.isKilledByAm() || processRecord2.getThread() == null || processStateRecord2.getCurAdj() < i5) {
                i = i10;
                i11 = i11;
            } else {
                com.android.server.am.ProcessServiceRecord processServiceRecord2 = processRecord2.mServices;
                switch (processStateRecord2.getCurProcState()) {
                    case 16:
                    case 17:
                    case 18:
                        int connectionGroup = processServiceRecord2.getConnectionGroup();
                        if (connectionGroup == 0) {
                            i2 = i11;
                            i = i10;
                            z = false;
                        } else {
                            i2 = i11;
                            int connectionImportance = processServiceRecord2.getConnectionImportance();
                            i = i10;
                            if (i13 == processRecord2.uid && i14 == connectionGroup) {
                                if (connectionImportance > i15) {
                                    if (i18 < i19 && i18 < 999) {
                                        i16++;
                                        i15 = connectionImportance;
                                    } else {
                                        i15 = connectionImportance;
                                    }
                                }
                                z = true;
                            } else {
                                i13 = processRecord2.uid;
                                i15 = connectionImportance;
                                i14 = connectionGroup;
                                z = false;
                            }
                        }
                        if (!z && i18 != i19) {
                            i21++;
                            if (i21 < i9) {
                                i3 = i19;
                                i19 = i18;
                                i16 = 0;
                            } else {
                                i3 = i19 + 10;
                                if (i3 <= 999) {
                                    i21 = 0;
                                    i16 = 0;
                                } else {
                                    i3 = 999;
                                    i21 = 0;
                                    i16 = 0;
                                }
                            }
                        } else {
                            i3 = i19;
                            i19 = i18;
                        }
                        int i22 = i19 + i16;
                        processStateRecord2.setCurRawAdj(i22);
                        processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i22));
                        i18 = i19;
                        i19 = i3;
                        i11 = i2;
                        break;
                    default:
                        if (i17 != i12 && (i11 = i11 + 1) >= i10) {
                            int i23 = i12 + 10;
                            if (i23 <= 999) {
                                i17 = i12;
                                i12 = i23;
                                i11 = 0;
                            } else {
                                i17 = i12;
                                i11 = 0;
                                i12 = 999;
                            }
                        }
                        processStateRecord2.setCurRawAdj(i17);
                        processStateRecord2.setCurAdj(processServiceRecord2.modifyRawOomAdj(i17));
                        i = i10;
                        break;
                }
            }
            i20--;
            arrayList2 = arrayList;
            i10 = i;
            i5 = 1001;
        }
    }

    private static double getFreeSwapPercent() {
        return com.android.server.am.CachedAppOptimizer.getFreeSwapPercent();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private boolean updateAndTrimProcessLSP(long j, long j2, long j3, com.android.server.am.ActiveUids activeUids, int i) {
        int i2;
        double d;
        int i3;
        double d2;
        double d3;
        int i4;
        int i5;
        boolean z;
        com.android.server.am.ProcessRecord processRecord;
        int i6;
        int i7;
        int i8;
        java.util.ArrayList<com.android.server.am.ProcessRecord> lruProcessesLOSP = this.mProcessList.getLruProcessesLOSP();
        int size = lruProcessesLOSP.size();
        boolean shouldKillExcessiveProcesses = shouldKillExcessiveProcesses(j);
        if (!shouldKillExcessiveProcesses && this.mNextNoKillDebugMessageTime < j) {
            android.util.Slog.d(TAG, "Not killing cached processes");
            this.mNextNoKillDebugMessageTime = j + 5000;
        }
        int i9 = Integer.MAX_VALUE;
        if (!shouldKillExcessiveProcesses) {
            i2 = Integer.MAX_VALUE;
        } else {
            i2 = this.mConstants.CUR_MAX_EMPTY_PROCESSES;
        }
        if (shouldKillExcessiveProcesses) {
            i9 = this.mConstants.CUR_MAX_CACHED_PROCESSES - i2;
        }
        int i10 = i9;
        boolean z2 = com.android.server.am.ActivityManagerConstants.PROACTIVE_KILLS_ENABLED;
        double d4 = com.android.server.am.ActivityManagerConstants.LOW_SWAP_THRESHOLD_PERCENT;
        double freeSwapPercent = z2 ? getFreeSwapPercent() : 1.0d;
        boolean z3 = true;
        int i11 = size - 1;
        com.android.server.am.ProcessRecord processRecord2 = null;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (true) {
            com.android.server.am.ProcessRecord processRecord3 = processRecord2;
            if (i11 >= 0) {
                com.android.server.am.ProcessRecord processRecord4 = lruProcessesLOSP.get(i11);
                java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = lruProcessesLOSP;
                com.android.server.am.ProcessStateRecord processStateRecord = processRecord4.mState;
                if (!processRecord4.isKilledByAm() && processRecord4.getThread() != null) {
                    int i18 = i12;
                    if (processStateRecord.getCompletedAdjSeq() != this.mAdjSeq) {
                        processRecord = processRecord4;
                        i6 = i14;
                        i3 = i11;
                        d2 = freeSwapPercent;
                        d3 = d4;
                        i7 = i18;
                        z = true;
                        i5 = i13;
                    } else {
                        i7 = i18;
                        d3 = d4;
                        i5 = i13;
                        processRecord = processRecord4;
                        i3 = i11;
                        d2 = freeSwapPercent;
                        i6 = i14;
                        z = true;
                        applyOomAdjLSP(processRecord4, true, j, j2, i);
                    }
                    if (processRecord.isPendingFinishAttach()) {
                        i4 = i7;
                        i14 = i6;
                    } else {
                        com.android.server.am.ProcessRecord processRecord5 = processRecord;
                        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord5.mServices;
                        switch (processStateRecord.getCurProcState()) {
                            case 16:
                            case 17:
                                i14 = i6;
                                this.mNumCachedHiddenProcs += z ? 1 : 0;
                                int i19 = i15 + 1;
                                int connectionGroup = processServiceRecord.getConnectionGroup();
                                if (connectionGroup != 0) {
                                    if (i5 == processRecord5.info.uid && (i8 = i7) == connectionGroup) {
                                        i17++;
                                        connectionGroup = i8;
                                    } else {
                                        i5 = processRecord5.info.uid;
                                    }
                                } else {
                                    connectionGroup = 0;
                                    i5 = 0;
                                }
                                if (i19 - i17 > i10) {
                                    processRecord5.killLocked("cached #" + i19, "too many cached", 13, 2, true);
                                } else if (z2) {
                                    i15 = i19;
                                    i7 = connectionGroup;
                                    processRecord2 = processRecord5;
                                    break;
                                }
                                i15 = i19;
                                i7 = connectionGroup;
                                processRecord2 = processRecord3;
                                break;
                            case 18:
                            default:
                                this.mNumNonCachedProcs += z ? 1 : 0;
                                i14 = i6;
                                processRecord2 = processRecord3;
                                break;
                            case 19:
                                i14 = i6;
                                if (i14 > this.mConstants.CUR_TRIM_EMPTY_PROCESSES && processRecord5.getLastActivityTime() < j3) {
                                    processRecord5.killLocked("empty for " + ((j - processRecord5.getLastActivityTime()) / 1000) + "s", "empty for too long", 13, 4, true);
                                } else {
                                    i14++;
                                    if (i14 > i2) {
                                        processRecord5.killLocked("empty #" + i14, "too many empty", 13, 3, true);
                                    } else if (z2) {
                                        processRecord2 = processRecord5;
                                        break;
                                    }
                                }
                                processRecord2 = processRecord3;
                                break;
                        }
                        if (processRecord5.isolated && processServiceRecord.numberOfRunningServices() <= 0 && processRecord5.getIsolatedEntryPoint() == null) {
                            processRecord5.killLocked("isolated not needed", 13, 17, z);
                        } else if (processRecord5.isSdkSandbox && processServiceRecord.numberOfRunningServices() <= 0 && processRecord5.getActiveInstrumentation() == null) {
                            processRecord5.killLocked("sandbox not needed", 13, 28, z);
                        } else {
                            updateAppUidRecLSP(processRecord5);
                        }
                        if (processStateRecord.getCurProcState() < 14 || processRecord5.isKilledByAm()) {
                            i13 = i5;
                            i12 = i7;
                        } else {
                            i16++;
                            i13 = i5;
                            i12 = i7;
                        }
                        i11 = i3 - 1;
                        z3 = z;
                        lruProcessesLOSP = arrayList;
                        d4 = d3;
                        freeSwapPercent = d2;
                    }
                } else {
                    i3 = i11;
                    d2 = freeSwapPercent;
                    d3 = d4;
                    i4 = i12;
                    i5 = i13;
                    z = z3;
                }
                processRecord2 = processRecord3;
                i12 = i4;
                i13 = i5;
                i11 = i3 - 1;
                z3 = z;
                lruProcessesLOSP = arrayList;
                d4 = d3;
                freeSwapPercent = d2;
            } else {
                double d5 = freeSwapPercent;
                double d6 = d4;
                boolean z4 = z3;
                if (z2 && shouldKillExcessiveProcesses) {
                    d = d5;
                    if (d < d6 && processRecord3 != null && d < this.mLastFreeSwapPercent) {
                        processRecord3.killLocked("swap low and too many cached", 13, 2, z4);
                    }
                } else {
                    d = d5;
                }
                this.mLastFreeSwapPercent = d;
                return this.mService.mAppProfiler.updateLowMemStateLSP(i15, i14, i16, j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void updateAppUidRecIfNecessaryLSP(com.android.server.am.ProcessRecord processRecord) {
        if (!processRecord.isKilledByAm() && processRecord.getThread() != null) {
            if (!processRecord.isolated || processRecord.mServices.numberOfRunningServices() > 0 || processRecord.getIsolatedEntryPoint() != null) {
                updateAppUidRecLSP(processRecord);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void updateAppUidRecLSP(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
        if (uidRecord != null) {
            com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
            uidRecord.setEphemeral(processRecord.info.isInstantApp());
            if (uidRecord.getCurProcState() > processStateRecord.getCurProcState()) {
                uidRecord.setCurProcState(processStateRecord.getCurProcState());
            }
            if (processRecord.mServices.hasForegroundServices()) {
                uidRecord.setForegroundServices(true);
            }
            uidRecord.setCurCapability(uidRecord.getCurCapability() | processStateRecord.getCurCapability());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void updateUidsLSP(com.android.server.am.ActiveUids activeUids, long j) {
        int i;
        this.mProcessList.incrementProcStateSeqAndNotifyAppsLOSP(activeUids);
        java.util.ArrayList<com.android.server.am.UidRecord> arrayList = this.mTmpBecameIdle;
        arrayList.clear();
        if (this.mService.mLocalPowerManager != null) {
            this.mService.mLocalPowerManager.startUidChanges();
        }
        for (int size = activeUids.size() - 1; size >= 0; size--) {
            com.android.server.am.UidRecord valueAt = activeUids.valueAt(size);
            if (valueAt.getCurProcState() != 20 && (valueAt.getSetProcState() != valueAt.getCurProcState() || valueAt.getSetCapability() != valueAt.getCurCapability() || valueAt.isSetAllowListed() != valueAt.isCurAllowListed() || valueAt.getProcAdjChanged())) {
                if (android.app.ActivityManager.isProcStateBackground(valueAt.getCurProcState()) && !valueAt.isCurAllowListed()) {
                    if (!android.app.ActivityManager.isProcStateBackground(valueAt.getSetProcState()) || valueAt.isSetAllowListed() || valueAt.getLastBackgroundTime() == 0) {
                        valueAt.setLastBackgroundTime(j);
                        if (this.mService.mDeterministicUidIdle || !this.mService.mHandler.hasMessages(58)) {
                            this.mService.mHandler.sendEmptyMessageDelayed(58, this.mConstants.BACKGROUND_SETTLE_TIME);
                        }
                    }
                    if (!valueAt.isIdle() || valueAt.isSetIdle()) {
                        i = 0;
                    } else {
                        if (valueAt.getSetProcState() != 20) {
                            arrayList.add(valueAt);
                        }
                        i = 2;
                    }
                } else {
                    if (!valueAt.isIdle()) {
                        i = 0;
                    } else {
                        com.android.server.am.EventLogTags.writeAmUidActive(valueAt.getUid());
                        valueAt.setIdle(false);
                        i = 4;
                    }
                    valueAt.setLastBackgroundTime(0L);
                    valueAt.setLastIdleTime(0L);
                }
                boolean z = valueAt.getSetProcState() > 11;
                boolean z2 = valueAt.getCurProcState() > 11;
                if (z != z2 || valueAt.getSetProcState() == 20) {
                    i |= z2 ? 8 : 16;
                }
                if (valueAt.getSetCapability() != valueAt.getCurCapability()) {
                    i |= 32;
                }
                if (valueAt.getSetProcState() != valueAt.getCurProcState()) {
                    i |= Integer.MIN_VALUE;
                }
                if (valueAt.getProcAdjChanged()) {
                    i |= 64;
                }
                valueAt.setSetProcState(valueAt.getCurProcState());
                valueAt.setSetCapability(valueAt.getCurCapability());
                valueAt.setSetAllowListed(valueAt.isCurAllowListed());
                valueAt.setSetIdle(valueAt.isIdle());
                valueAt.clearProcAdjChanged();
                int i2 = i & Integer.MIN_VALUE;
                if (i2 != 0 || (i & 32) != 0) {
                    this.mService.mAtmInternal.onUidProcStateChanged(valueAt.getUid(), valueAt.getSetProcState());
                }
                if (i != 0) {
                    this.mService.enqueueUidChangeLocked(valueAt, -1, i);
                }
                if (i2 != 0 || (i & 32) != 0) {
                    this.mService.noteUidProcessState(valueAt.getUid(), valueAt.getCurProcState(), valueAt.getCurCapability());
                }
                if (valueAt.hasForegroundServices()) {
                    this.mService.mServices.foregroundServiceProcStateChangedLocked(valueAt);
                }
            }
            this.mService.mInternal.deletePendingTopUid(valueAt.getUid(), j);
        }
        if (this.mService.mLocalPowerManager != null) {
            this.mService.mLocalPowerManager.finishUidChanges();
        }
        int size2 = arrayList.size();
        if (size2 > 0) {
            for (int i3 = size2 - 1; i3 >= 0; i3--) {
                this.mService.mServices.stopInBackgroundLocked(arrayList.get(i3).getUid());
            }
        }
    }

    private boolean shouldKillExcessiveProcesses(long j) {
        long lastUserUnlockingUptime = this.mService.mUserController.getLastUserUnlockingUptime();
        if (lastUserUnlockingUptime == 0) {
            return !this.mConstants.mNoKillCachedProcessesUntilBootCompleted;
        }
        return lastUserUnlockingUptime + this.mConstants.mNoKillCachedProcessesPostBootCompletedDurationMillis <= j;
    }

    final class ComputeOomAdjWindowCallback implements com.android.server.wm.WindowProcessController.ComputeOomAdjCallback {
        int adj;
        com.android.server.am.ProcessRecord app;
        int appUid;
        boolean foregroundActivities;
        int logUid;
        java.lang.String mAdjType;
        boolean mHasVisibleActivities;
        com.android.server.am.ProcessStateRecord mState;
        int procState;
        int processStateCurTop;
        int schedGroup;

        ComputeOomAdjWindowCallback() {
        }

        void initialize(com.android.server.am.ProcessRecord processRecord, int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, int i6) {
            this.app = processRecord;
            this.adj = i;
            this.foregroundActivities = z;
            this.mHasVisibleActivities = z2;
            this.procState = i2;
            this.schedGroup = i3;
            this.appUid = i4;
            this.logUid = i5;
            this.processStateCurTop = i6;
            this.mAdjType = processRecord.mState.getAdjType();
            this.mState = processRecord.mState;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onVisibleActivity() {
            if (this.adj > 100) {
                this.adj = 100;
                this.mAdjType = "vis-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to vis-activity: " + this.app);
                }
            }
            if (this.procState > this.processStateCurTop) {
                this.procState = this.processStateCurTop;
                this.mAdjType = "vis-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to vis-activity (top): " + this.app);
                }
            }
            if (this.schedGroup < 2) {
                this.schedGroup = 2;
            }
            this.foregroundActivities = true;
            this.mHasVisibleActivities = true;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onPausedActivity() {
            if (this.adj > 200) {
                this.adj = 200;
                this.mAdjType = "pause-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to pause-activity: " + this.app);
                }
            }
            if (this.procState > this.processStateCurTop) {
                this.procState = this.processStateCurTop;
                this.mAdjType = "pause-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to pause-activity (top): " + this.app);
                }
            }
            if (this.schedGroup < 2) {
                this.schedGroup = 2;
            }
            this.foregroundActivities = true;
            this.mHasVisibleActivities = false;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onStoppingActivity(boolean z) {
            if (this.adj > 200) {
                this.adj = 200;
                this.mAdjType = "stop-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise adj to stop-activity: " + this.app);
                }
            }
            if (!z && this.procState > 15) {
                this.procState = 15;
                this.mAdjType = "stop-activity";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to stop-activity: " + this.app);
                }
            }
            this.foregroundActivities = true;
            this.mHasVisibleActivities = false;
        }

        @Override // com.android.server.wm.WindowProcessController.ComputeOomAdjCallback
        public void onOtherActivity() {
            if (this.procState > 16) {
                this.procState = 16;
                this.mAdjType = "cch-act";
                if (this.logUid == this.appUid) {
                    com.android.server.am.OomAdjuster.this.reportOomAdjMessageLocked("ActivityManager", "Raise procstate to cached activity: " + this.app);
                }
            }
            this.mHasVisibleActivities = false;
        }
    }

    private boolean isScreenOnOrAnimatingLocked(com.android.server.am.ProcessStateRecord processStateRecord) {
        return this.mService.mWakefulness.get() == 1 || processStateRecord.isRunningRemoteAnimation();
    }

    /* JADX WARN: Code restructure failed: missing block: B:403:0x0724, code lost:
    
        if (r5 < (r11.lastActivity + r14.mConstants.MAX_SERVICE_INACTIVITY)) goto L302;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x04a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0525  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x05ff  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0634  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x063d  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x069e  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0989  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0aea  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0b41  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0b62  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0bec  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0bfd  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0c16  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0c1f  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0c27  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0c38  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0bf8  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0bdc  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0b38  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0ad0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:418:0x0961 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:422:0x05b8  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x05db  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x05fb  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x03ac A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:443:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0380  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0462  */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean computeOomAdjLSP(com.android.server.am.ProcessRecord processRecord, int i, com.android.server.am.ProcessRecord processRecord2, boolean z, long j, boolean z2, boolean z3, int i2, boolean z4) {
        int i3;
        int i4;
        int i5;
        boolean z5;
        boolean z6;
        int i6;
        int i7;
        com.android.server.am.ProcessServiceRecord processServiceRecord;
        int i8;
        int i9;
        int i10;
        int i11;
        java.lang.String str;
        java.lang.String str2;
        int i12;
        int i13;
        boolean z7;
        boolean z8;
        int i14;
        boolean hasForegroundServices;
        long j2;
        boolean z9;
        com.android.server.am.ProcessServiceRecord processServiceRecord2;
        boolean z10;
        int i15;
        java.lang.String str3;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        com.android.server.am.BackupRecord backupRecord;
        int numberOfRunningServices;
        int i21;
        com.android.server.am.ProcessServiceRecord processServiceRecord3;
        int i22;
        boolean z11;
        int i23;
        com.android.server.am.ProcessStateRecord processStateRecord;
        java.lang.String str4;
        boolean z12;
        int i24;
        int i25;
        int i26;
        int i27;
        com.android.server.am.ProcessProviderRecord processProviderRecord;
        int i28;
        int numberOfProviders;
        com.android.server.am.OomAdjuster oomAdjuster;
        int i29;
        com.android.server.am.ProcessProviderRecord processProviderRecord2;
        int i30;
        com.android.server.am.ProcessStateRecord processStateRecord2;
        int i31;
        java.lang.String str5;
        int i32;
        int i33;
        int i34;
        com.android.server.am.ProcessServiceRecord processServiceRecord4;
        int modifyRawOomAdj;
        int i35;
        int i36;
        com.android.server.am.ContentProviderRecord contentProviderRecord;
        int i37;
        com.android.server.am.ProcessProviderRecord processProviderRecord3;
        int i38;
        int i39;
        int i40;
        int i41;
        com.android.server.am.ProcessStateRecord processStateRecord3;
        int i42;
        java.lang.String str6;
        com.android.server.am.OomAdjuster oomAdjuster2;
        int i43;
        boolean z13;
        int i44;
        com.android.server.am.ProcessServiceRecord processServiceRecord5;
        int i45;
        com.android.server.am.ProcessStateRecord processStateRecord4;
        java.lang.String str7;
        boolean z14;
        int i46;
        int i47;
        boolean z15;
        int i48;
        int i49;
        int i50;
        com.android.server.am.ProcessStateRecord processStateRecord5;
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> arrayMap;
        java.lang.String str8;
        boolean z16;
        int i51;
        boolean z17;
        com.android.server.am.ProcessServiceRecord processServiceRecord6;
        com.android.server.am.ProcessServiceRecord processServiceRecord7;
        int i52;
        int i53;
        int i54;
        boolean z18;
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList;
        int i55;
        int i56;
        com.android.server.am.ProcessStateRecord processStateRecord6;
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> arrayMap2;
        boolean z19;
        java.lang.String str9;
        int i57;
        int i58;
        int i59;
        boolean z20;
        com.android.server.am.OomAdjuster oomAdjuster3 = this;
        com.android.server.am.ProcessStateRecord processStateRecord7 = processRecord.mState;
        if (z4 && oomAdjuster3.mAdjSeq == processStateRecord7.getAdjSeq()) {
            if (processStateRecord7.getAdjSeq() == processStateRecord7.getCompletedAdjSeq()) {
                return false;
            }
            processStateRecord7.setContainsCycle(true);
            oomAdjuster3.mProcessesInCycle.add(processRecord);
            return false;
        }
        int initialAdj = getInitialAdj(processRecord);
        int initialProcState = getInitialProcState(processRecord);
        int initialCapability = getInitialCapability(processRecord);
        if (processRecord.getThread() == null) {
            processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
            processStateRecord7.setCurrentSchedulingGroup(0);
            processStateRecord7.setCurProcState(19);
            processStateRecord7.setCurAdj(999);
            processStateRecord7.setCurRawAdj(999);
            processStateRecord7.setCompletedAdjSeq(processStateRecord7.getAdjSeq());
            processStateRecord7.setCurCapability(0);
            oomAdjuster3.onProcessStateChanged(processRecord, initialProcState);
            oomAdjuster3.onProcessOomAdjChanged(processRecord, initialAdj);
            return false;
        }
        processStateRecord7.setAdjTypeCode(0);
        processStateRecord7.setAdjSource(null);
        processStateRecord7.setAdjTarget(null);
        if (!z4 || !z2) {
            processStateRecord7.setNoKillOnBgRestrictedAndIdle(false);
            com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
            processRecord.mOptRecord.setShouldNotFreeze(uidRecord != null && uidRecord.isCurAllowListed());
        }
        int i60 = processRecord.info.uid;
        int i61 = oomAdjuster3.mService.mCurOomAdjUid;
        com.android.server.am.ProcessServiceRecord processServiceRecord8 = processRecord.mServices;
        if (processStateRecord7.getMaxAdj() <= 0) {
            if (i61 == i60) {
                oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making fixed: " + processRecord);
            }
            processStateRecord7.setAdjType("fixed");
            processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
            processStateRecord7.setCurRawAdj(processStateRecord7.getMaxAdj());
            processStateRecord7.setHasForegroundActivities(false);
            processStateRecord7.setCurrentSchedulingGroup(2);
            processStateRecord7.setCurCapability(127);
            processStateRecord7.setCurProcState(0);
            processStateRecord7.setSystemNoUi(true);
            if (processRecord == processRecord2) {
                processStateRecord7.setSystemNoUi(false);
                processStateRecord7.setCurrentSchedulingGroup(3);
                processStateRecord7.setAdjType("pers-top-activity");
            } else if (processStateRecord7.hasTopUi()) {
                processStateRecord7.setSystemNoUi(false);
                processStateRecord7.setAdjType("pers-top-ui");
            } else if (processStateRecord7.getCachedHasVisibleActivities()) {
                processStateRecord7.setSystemNoUi(false);
            }
            if (processStateRecord7.isSystemNoUi()) {
                z20 = true;
            } else if (oomAdjuster3.isScreenOnOrAnimatingLocked(processStateRecord7)) {
                z20 = true;
                processStateRecord7.setCurProcState(1);
                processStateRecord7.setCurrentSchedulingGroup(3);
            } else {
                z20 = true;
                if (!processRecord.getWindowProcessController().isShowingUiWhileDozing()) {
                    processStateRecord7.setCurProcState(5);
                    processStateRecord7.setCurrentSchedulingGroup(1);
                }
            }
            processStateRecord7.setCurRawProcState(processStateRecord7.getCurProcState());
            processStateRecord7.setCurAdj(processStateRecord7.getMaxAdj());
            processStateRecord7.setCompletedAdjSeq(processStateRecord7.getAdjSeq());
            oomAdjuster3.onProcessStateChanged(processRecord, initialProcState);
            oomAdjuster3.onProcessOomAdjChanged(processRecord, initialAdj);
            if (processStateRecord7.getCurAdj() < initialAdj || processStateRecord7.getCurProcState() < initialProcState) {
                return z20;
            }
            return false;
        }
        processStateRecord7.setSystemNoUi(false);
        int topProcessState = oomAdjuster3.mService.mAtmInternal.getTopProcessState();
        int initialCapability2 = z2 ? getInitialCapability(processRecord) : 0;
        if (processRecord == processRecord2) {
            i3 = 2;
            if (topProcessState == 2) {
                if (oomAdjuster3.mService.mAtmInternal.useTopSchedGroupForTopProcess()) {
                    processStateRecord7.setAdjType(com.android.server.am.HostingRecord.HOSTING_TYPE_TOP_ACTIVITY);
                    i59 = 3;
                } else {
                    processStateRecord7.setAdjType("intermediate-top-activity");
                    i59 = 2;
                }
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making top: " + processRecord);
                }
                i7 = i59;
                z5 = true;
                z6 = true;
                i5 = initialCapability2;
                i4 = 0;
                i6 = 2;
                if (z5 && processStateRecord7.getCachedHasActivities()) {
                    processServiceRecord = processServiceRecord8;
                    i8 = i61;
                    i9 = i60;
                    str2 = null;
                    i10 = initialProcState;
                    i11 = initialAdj;
                    str = "ActivityManager";
                    processStateRecord7.computeOomAdjFromActivitiesIfNecessary(oomAdjuster3.mTmpComputeOomAdjWindowCallback, i4, z5, z6, i6, i7, i9, i8, topProcessState);
                    i4 = processStateRecord7.getCachedAdj();
                    boolean cachedForegroundActivities = processStateRecord7.getCachedForegroundActivities();
                    boolean cachedHasVisibleActivities = processStateRecord7.getCachedHasVisibleActivities();
                    int cachedProcState = processStateRecord7.getCachedProcState();
                    int cachedSchedGroup = processStateRecord7.getCachedSchedGroup();
                    processStateRecord7.setAdjType(processStateRecord7.getCachedAdjType());
                    i7 = cachedSchedGroup;
                    i12 = initialCapability;
                    z7 = cachedForegroundActivities;
                    z8 = cachedHasVisibleActivities;
                    i13 = cachedProcState;
                } else {
                    processServiceRecord = processServiceRecord8;
                    i8 = i61;
                    i9 = i60;
                    i10 = initialProcState;
                    i11 = initialAdj;
                    str = "ActivityManager";
                    str2 = null;
                    i12 = initialCapability;
                    i13 = i6;
                    boolean z21 = z6;
                    z7 = z5;
                    z8 = z21;
                }
                if (i13 > 18 || !processStateRecord7.getCachedHasRecentTasks()) {
                    i14 = i8;
                } else {
                    processStateRecord7.setAdjType("cch-rec");
                    i14 = i8;
                    if (i14 == i9) {
                        oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to cached recent: " + processRecord);
                    }
                    i13 = 18;
                }
                hasForegroundServices = processServiceRecord.hasForegroundServices();
                boolean hasNonShortForegroundServices = processServiceRecord.hasNonShortForegroundServices();
                if (hasForegroundServices) {
                    j2 = j;
                    z9 = z7;
                    processServiceRecord2 = processServiceRecord;
                    if (!processServiceRecord2.areAllShortForegroundServicesProcstateTimedOut(j2)) {
                        z10 = true;
                        if (i4 > 200) {
                            i15 = 4;
                            if (i13 <= 4) {
                                i18 = 0;
                                if (processServiceRecord2.hasForegroundServices() && i4 > 50 && (processStateRecord7.getLastTopTime() + oomAdjuster3.mConstants.TOP_TO_FGS_GRACE_DURATION > j2 || processStateRecord7.getSetProcState() <= 2)) {
                                    if (processServiceRecord2.hasNonShortForegroundServices()) {
                                        processStateRecord7.setAdjType("fg-service-short-act");
                                        i4 = 51;
                                    } else {
                                        processStateRecord7.setAdjType("fg-service-act");
                                        i4 = 50;
                                    }
                                    if (i14 == i9) {
                                        oomAdjuster3.reportOomAdjMessageLocked(str, "Raise to recent fg: " + processRecord);
                                    }
                                }
                                if (processServiceRecord2.hasTopStartedAlmostPerceptibleServices() && i4 > 52 && (processStateRecord7.getLastTopTime() + oomAdjuster3.mConstants.TOP_TO_ALMOST_PERCEPTIBLE_GRACE_DURATION > j2 || processStateRecord7.getSetProcState() <= 2)) {
                                    processStateRecord7.setAdjType("top-ej-act");
                                    if (i14 == i9) {
                                        oomAdjuster3.reportOomAdjMessageLocked(str, "Raise to recent fg for EJ: " + processRecord);
                                    }
                                    i4 = 52;
                                }
                                i19 = 200;
                                if ((i4 <= 200 || i13 > 8) && processStateRecord7.getForcingToImportant() != null) {
                                    processStateRecord7.setAdjType("force-imp");
                                    processStateRecord7.setAdjSource(processStateRecord7.getForcingToImportant());
                                    if (i14 == i9) {
                                        oomAdjuster3.reportOomAdjMessageLocked(str, "Raise to force imp: " + processRecord);
                                    }
                                    i13 = 8;
                                    i7 = 2;
                                } else {
                                    i19 = i4;
                                }
                                if (processStateRecord7.getCachedIsHeavyWeight()) {
                                    if (i19 > 400) {
                                        processStateRecord7.setAdjType("heavy");
                                        if (i14 == i9) {
                                            oomAdjuster3.reportOomAdjMessageLocked(str, "Raise adj to heavy: " + processRecord);
                                        }
                                        i19 = 400;
                                        i7 = 0;
                                    }
                                    if (i13 > 13) {
                                        processStateRecord7.setAdjType("heavy");
                                        if (i14 == i9) {
                                            oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to heavy: " + processRecord);
                                        }
                                        i13 = 13;
                                    }
                                }
                                if (processStateRecord7.getCachedIsHomeProcess()) {
                                    if (i19 > 600) {
                                        processStateRecord7.setAdjType("home");
                                        if (i14 == i9) {
                                            oomAdjuster3.reportOomAdjMessageLocked(str, "Raise adj to home: " + processRecord);
                                        }
                                        i19 = 600;
                                        i7 = 0;
                                    }
                                    if (i13 > 14) {
                                        processStateRecord7.setAdjType("home");
                                        if (i14 == i9) {
                                            oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to home: " + processRecord);
                                        }
                                        i13 = 14;
                                    }
                                }
                                if (processStateRecord7.getCachedIsPreviousProcess() && processStateRecord7.getCachedHasActivities()) {
                                    if (i13 >= 15 || processStateRecord7.getSetProcState() != 15 || processStateRecord7.getLastStateTime() + com.android.server.am.ActivityManagerConstants.MAX_PREVIOUS_TIME >= j2) {
                                        if (i19 > 700) {
                                            processStateRecord7.setAdjType("previous");
                                            if (i14 == i9) {
                                                oomAdjuster3.reportOomAdjMessageLocked(str, "Raise adj to prev: " + processRecord);
                                            }
                                            i19 = com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
                                            i7 = 0;
                                        }
                                        if (i13 <= 15) {
                                            processStateRecord7.setAdjType("previous");
                                            if (i14 == i9) {
                                                oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to prev: " + processRecord);
                                            }
                                            i13 = 15;
                                        } else {
                                            i20 = i7;
                                        }
                                    } else {
                                        processStateRecord7.setAdjType("previous-expired");
                                        if (i14 == i9) {
                                            oomAdjuster3.reportOomAdjMessageLocked(str, "Expire prev adj: " + processRecord);
                                        }
                                        i19 = com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ;
                                        i13 = 15;
                                        i20 = 0;
                                    }
                                    if (z2) {
                                        i13 = java.lang.Math.min(i13, processStateRecord7.getCurRawProcState());
                                        i19 = java.lang.Math.min(i19, processStateRecord7.getCurRawAdj());
                                        i20 = java.lang.Math.max(i20, processStateRecord7.getCurrentSchedulingGroup());
                                    }
                                    processStateRecord7.setCurRawAdj(i19);
                                    processStateRecord7.setCurRawProcState(i13);
                                    processStateRecord7.setHasStartedServices(false);
                                    processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
                                    backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
                                    if (backupRecord != null && processRecord == backupRecord.app) {
                                        if (i19 > 300) {
                                            int i62 = i13 <= 8 ? i13 : 8;
                                            processStateRecord7.setAdjType(com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP);
                                            if (i14 == i9) {
                                                oomAdjuster3.reportOomAdjMessageLocked(str, "Raise adj to backup: " + processRecord);
                                            }
                                            i19 = 300;
                                            i13 = i62;
                                        }
                                        if (i13 > 9) {
                                            processStateRecord7.setAdjType(com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP);
                                            if (i14 == i9) {
                                                oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to backup: " + processRecord);
                                            }
                                            i13 = 9;
                                        }
                                    }
                                    processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
                                    processStateRecord7.setScheduleLikeTopApp(false);
                                    int i63 = i5;
                                    int i64 = i13;
                                    int i65 = i20;
                                    numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
                                    while (true) {
                                        if (numberOfRunningServices >= 0) {
                                            i21 = i65;
                                            processServiceRecord3 = processServiceRecord2;
                                            i22 = i14;
                                            z11 = z8;
                                            i23 = i12;
                                            processStateRecord = processStateRecord7;
                                            str4 = str;
                                            z12 = z9;
                                            i24 = i63;
                                            i25 = 500;
                                            i26 = 2;
                                            i27 = i9;
                                            break;
                                        }
                                        if (i19 <= 0 && i65 != 0 && i64 <= 2) {
                                            i21 = i65;
                                            processServiceRecord3 = processServiceRecord2;
                                            i22 = i14;
                                            z11 = z8;
                                            i23 = i12;
                                            processStateRecord = processStateRecord7;
                                            str4 = str;
                                            z12 = z9;
                                            i24 = i63;
                                            i25 = 500;
                                            i26 = 2;
                                            i27 = i9;
                                            break;
                                        }
                                        com.android.server.am.ServiceRecord runningServiceAt = processServiceRecord2.getRunningServiceAt(numberOfRunningServices);
                                        int i66 = numberOfRunningServices;
                                        if (runningServiceAt.startRequested) {
                                            processStateRecord7.setHasStartedServices(true);
                                            if (i64 > 10) {
                                                processStateRecord7.setAdjType("started-services");
                                                if (i14 == i9) {
                                                    oomAdjuster3.reportOomAdjMessageLocked(str, "Raise procstate to started service: " + processRecord);
                                                }
                                                i64 = 10;
                                            }
                                            if (runningServiceAt.mKeepWarming || !processStateRecord7.hasShownUi() || processStateRecord7.getCachedIsHomeProcess()) {
                                                if (runningServiceAt.mKeepWarming) {
                                                    i43 = i65;
                                                    i58 = i64;
                                                    z13 = z8;
                                                    i44 = i12;
                                                } else {
                                                    z13 = z8;
                                                    i44 = i12;
                                                    i43 = i65;
                                                    i58 = i64;
                                                }
                                                if (!processRecord.isSdkSandbox && i19 > 500) {
                                                    processStateRecord7.setAdjType("started-services");
                                                    if (i14 == i9) {
                                                        oomAdjuster3.reportOomAdjMessageLocked(str, "Raise adj to started service: " + processRecord);
                                                    }
                                                    i19 = 500;
                                                }
                                                if (i19 > 500) {
                                                    processStateRecord7.setAdjType("cch-started-services");
                                                }
                                                i64 = i58;
                                            } else {
                                                if (i19 > 500) {
                                                    processStateRecord7.setAdjType("cch-started-ui-services");
                                                }
                                                i43 = i65;
                                                z13 = z8;
                                                i44 = i12;
                                            }
                                        } else {
                                            i43 = i65;
                                            z13 = z8;
                                            i44 = i12;
                                        }
                                        if (runningServiceAt.isForeground) {
                                            int i67 = runningServiceAt.foregroundServiceType;
                                            if (runningServiceAt.isFgsAllowedWiu_forCapabilities()) {
                                                int i68 = i18 | ((i67 & 8) != 0 ? 1 : 0);
                                                if (android.media.audio.Flags.foregroundAudioControl()) {
                                                    i68 |= (processServiceRecord2.getForegroundServiceTypes() & com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_USB_DATA_SIGNALING) != 0 ? 64 : 0;
                                                }
                                                if (processStateRecord7.getCachedCompatChange(1)) {
                                                    i18 = i68 | ((i67 & 64) != 0 ? 2 : 0) | ((i67 & 128) != 0 ? 4 : 0);
                                                } else {
                                                    i18 = i68 | 6;
                                                }
                                            }
                                        }
                                        if (z4) {
                                            processStateRecord7.setCurRawAdj(i19);
                                            processStateRecord7.setCurRawProcState(i64);
                                            int i69 = i43;
                                            processStateRecord7.setCurrentSchedulingGroup(i69);
                                            processStateRecord7.setCurCapability(i63);
                                            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = runningServiceAt.getConnections();
                                            i65 = i69;
                                            int size = connections.size() - 1;
                                            while (true) {
                                                if (size < 0) {
                                                    processServiceRecord5 = processServiceRecord2;
                                                    i45 = i14;
                                                    processStateRecord4 = processStateRecord7;
                                                    str7 = str;
                                                    z14 = z9;
                                                    i46 = i66;
                                                    i47 = i44;
                                                    z15 = z13;
                                                    i48 = i9;
                                                    break;
                                                }
                                                if (i19 <= 0 && i65 != 0 && i64 <= 2) {
                                                    processServiceRecord5 = processServiceRecord2;
                                                    i45 = i14;
                                                    processStateRecord4 = processStateRecord7;
                                                    str7 = str;
                                                    z14 = z9;
                                                    i46 = i66;
                                                    i47 = i44;
                                                    z15 = z13;
                                                    i48 = i9;
                                                    break;
                                                }
                                                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
                                                int i70 = 0;
                                                while (true) {
                                                    com.android.server.am.ProcessServiceRecord processServiceRecord9 = processServiceRecord2;
                                                    if (i70 >= valueAt.size()) {
                                                        i49 = i65;
                                                        i50 = i14;
                                                        processStateRecord5 = processStateRecord7;
                                                        arrayMap = connections;
                                                        str8 = str;
                                                        z16 = z9;
                                                        i51 = i44;
                                                        z17 = z13;
                                                        processServiceRecord6 = processServiceRecord9;
                                                        break;
                                                    }
                                                    if (i19 <= 0 && i65 != 0) {
                                                        if (i64 <= 2) {
                                                            i49 = i65;
                                                            i50 = i14;
                                                            processStateRecord5 = processStateRecord7;
                                                            arrayMap = connections;
                                                            str8 = str;
                                                            z16 = z9;
                                                            i51 = i44;
                                                            z17 = z13;
                                                            processServiceRecord6 = processServiceRecord9;
                                                            break;
                                                        }
                                                    }
                                                    com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i70);
                                                    int i71 = i65;
                                                    if (connectionRecord.binding.client == processRecord) {
                                                        i52 = i70;
                                                        i54 = i14;
                                                        processStateRecord6 = processStateRecord7;
                                                        arrayMap2 = connections;
                                                        str9 = str;
                                                        z19 = z9;
                                                        i65 = i71;
                                                        i55 = i44;
                                                        z18 = z13;
                                                        processServiceRecord7 = processServiceRecord9;
                                                        arrayList = valueAt;
                                                        i56 = size;
                                                        i57 = i9;
                                                        i53 = i66;
                                                    } else {
                                                        processServiceRecord7 = processServiceRecord9;
                                                        i52 = i70;
                                                        i53 = i66;
                                                        i54 = i14;
                                                        z18 = z13;
                                                        arrayList = valueAt;
                                                        i55 = i44;
                                                        i56 = size;
                                                        processStateRecord6 = processStateRecord7;
                                                        arrayMap2 = connections;
                                                        z19 = z9;
                                                        str9 = str;
                                                        i57 = i9;
                                                        computeServiceHostOomAdjLSP(connectionRecord, processRecord, connectionRecord.binding.client, j, processRecord2, z, z2, z3, i2, i, true, false);
                                                        i19 = processStateRecord6.getCurRawAdj();
                                                        i64 = processStateRecord6.getCurRawProcState();
                                                        i65 = processStateRecord6.getCurrentSchedulingGroup();
                                                        i63 = processStateRecord6.getCurCapability();
                                                    }
                                                    i70 = i52 + 1;
                                                    i9 = i57;
                                                    connections = arrayMap2;
                                                    processServiceRecord2 = processServiceRecord7;
                                                    size = i56;
                                                    valueAt = arrayList;
                                                    i66 = i53;
                                                    processStateRecord7 = processStateRecord6;
                                                    i14 = i54;
                                                    z13 = z18;
                                                    i44 = i55;
                                                    z9 = z19;
                                                    str = str9;
                                                }
                                                size--;
                                                i9 = i9;
                                                i65 = i49;
                                                connections = arrayMap;
                                                processServiceRecord2 = processServiceRecord6;
                                                i66 = i66;
                                                processStateRecord7 = processStateRecord5;
                                                i14 = i50;
                                                z13 = z17;
                                                i44 = i51;
                                                z9 = z16;
                                                str = str8;
                                            }
                                        } else {
                                            processServiceRecord5 = processServiceRecord2;
                                            i45 = i14;
                                            processStateRecord4 = processStateRecord7;
                                            str7 = str;
                                            z14 = z9;
                                            i47 = i44;
                                            z15 = z13;
                                            i65 = i43;
                                            i48 = i9;
                                            i46 = i66;
                                        }
                                        numberOfRunningServices = i46 - 1;
                                        oomAdjuster3 = this;
                                        j2 = j;
                                        i9 = i48;
                                        processServiceRecord2 = processServiceRecord5;
                                        processStateRecord7 = processStateRecord4;
                                        i14 = i45;
                                        z8 = z15;
                                        i12 = i47;
                                        z9 = z14;
                                        str = str7;
                                    }
                                    processProviderRecord = processRecord.mProviders;
                                    i28 = 1;
                                    numberOfProviders = processProviderRecord.numberOfProviders() - 1;
                                    int i72 = i21;
                                    int i73 = i19;
                                    int i74 = i24;
                                    int i75 = i64;
                                    int i76 = i73;
                                    while (true) {
                                        if (numberOfProviders >= 0) {
                                            oomAdjuster = this;
                                            i29 = i25;
                                            processProviderRecord2 = processProviderRecord;
                                            i30 = i27;
                                            processStateRecord2 = processStateRecord;
                                            i31 = i22;
                                            str5 = str4;
                                            break;
                                        }
                                        if (i76 <= 0 && i72 != 0 && i75 <= i26) {
                                            oomAdjuster = this;
                                            i29 = i25;
                                            processProviderRecord2 = processProviderRecord;
                                            i30 = i27;
                                            processStateRecord2 = processStateRecord;
                                            i31 = i22;
                                            str5 = str4;
                                            break;
                                        }
                                        com.android.server.am.ContentProviderRecord providerAt = processProviderRecord.getProviderAt(numberOfProviders);
                                        if (z4) {
                                            com.android.server.am.ProcessStateRecord processStateRecord8 = processStateRecord;
                                            processStateRecord8.setCurRawAdj(i76);
                                            processStateRecord8.setCurRawProcState(i75);
                                            processStateRecord8.setCurrentSchedulingGroup(i72);
                                            processStateRecord8.setCurCapability(i74);
                                            int i77 = i72;
                                            int size2 = providerAt.connections.size() - i28;
                                            int i78 = i77;
                                            while (true) {
                                                if (size2 < 0) {
                                                    processStateRecord = processStateRecord8;
                                                    i36 = i25;
                                                    contentProviderRecord = providerAt;
                                                    i37 = numberOfProviders;
                                                    processProviderRecord3 = processProviderRecord;
                                                    break;
                                                }
                                                if (i76 <= 0 && i78 != 0 && i75 <= i26) {
                                                    processStateRecord = processStateRecord8;
                                                    i36 = i25;
                                                    contentProviderRecord = providerAt;
                                                    i37 = numberOfProviders;
                                                    processProviderRecord3 = processProviderRecord;
                                                    break;
                                                }
                                                com.android.server.am.ContentProviderConnection contentProviderConnection = providerAt.connections.get(size2);
                                                com.android.server.am.ProcessStateRecord processStateRecord9 = processStateRecord8;
                                                computeProviderHostOomAdjLSP(contentProviderConnection, processRecord, contentProviderConnection.client, j, processRecord2, z, z2, z3, i2, i, true, false);
                                                i76 = processStateRecord9.getCurRawAdj();
                                                i75 = processStateRecord9.getCurRawProcState();
                                                i78 = processStateRecord9.getCurrentSchedulingGroup();
                                                i74 = processStateRecord9.getCurCapability();
                                                size2--;
                                                i25 = i25;
                                                processProviderRecord = processProviderRecord;
                                                numberOfProviders = numberOfProviders;
                                                providerAt = providerAt;
                                                processStateRecord8 = processStateRecord9;
                                                i26 = 2;
                                            }
                                            i38 = i75;
                                            i40 = i76;
                                            i39 = i78;
                                        } else {
                                            i36 = i25;
                                            contentProviderRecord = providerAt;
                                            i37 = numberOfProviders;
                                            processProviderRecord3 = processProviderRecord;
                                            i38 = i75;
                                            i39 = i72;
                                            i40 = i76;
                                        }
                                        if (contentProviderRecord.hasExternalProcessHandles()) {
                                            if (i40 > 0) {
                                                processStateRecord3 = processStateRecord;
                                                processStateRecord3.setCurRawAdj(0);
                                                processStateRecord3.setAdjType("ext-provider");
                                                processStateRecord3.setAdjTarget(contentProviderRecord.name);
                                                int i79 = i27;
                                                i42 = i22;
                                                if (i42 == i79) {
                                                    oomAdjuster2 = this;
                                                    i41 = i79;
                                                    str6 = str4;
                                                    oomAdjuster2.reportOomAdjMessageLocked(str6, "Raise adj to external provider: " + processRecord);
                                                } else {
                                                    oomAdjuster2 = this;
                                                    i41 = i79;
                                                    str6 = str4;
                                                }
                                                i40 = 0;
                                                i39 = 2;
                                            } else {
                                                oomAdjuster2 = this;
                                                i41 = i27;
                                                processStateRecord3 = processStateRecord;
                                                i42 = i22;
                                                str6 = str4;
                                            }
                                            if (i38 > 6) {
                                                processStateRecord3.setCurRawProcState(6);
                                                if (i42 == i41) {
                                                    oomAdjuster2.reportOomAdjMessageLocked(str6, "Raise procstate to external provider: " + processRecord);
                                                }
                                                i76 = i40;
                                                i72 = i39;
                                                i75 = 6;
                                            } else {
                                                i76 = i40;
                                                i72 = i39;
                                                i75 = i38;
                                            }
                                        } else {
                                            i41 = i27;
                                            processStateRecord3 = processStateRecord;
                                            i42 = i22;
                                            str6 = str4;
                                            i76 = i40;
                                            i72 = i39;
                                            i75 = i38;
                                        }
                                        numberOfProviders = i37 - 1;
                                        processStateRecord = processStateRecord3;
                                        i22 = i42;
                                        i27 = i41;
                                        str4 = str6;
                                        i25 = i36;
                                        processProviderRecord = processProviderRecord3;
                                        i28 = 1;
                                        i26 = 2;
                                    }
                                    if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
                                        if (i76 > 700) {
                                            processStateRecord2.setAdjType("recent-provider");
                                            if (i31 == i30) {
                                                oomAdjuster.reportOomAdjMessageLocked(str5, "Raise adj to recent provider: " + processRecord);
                                            }
                                            i76 = 700;
                                            i72 = 0;
                                        }
                                        if (i75 > 15) {
                                            processStateRecord2.setAdjType("recent-provider");
                                            if (i31 == i30) {
                                                oomAdjuster.reportOomAdjMessageLocked(str5, "Raise procstate to recent provider: " + processRecord);
                                            }
                                            i33 = i76;
                                            i32 = 15;
                                        } else {
                                            int i80 = i76;
                                            i32 = i75;
                                            i33 = i80;
                                        }
                                    } else {
                                        int i81 = i76;
                                        i32 = i75;
                                        i33 = i81;
                                    }
                                    if (i32 >= 19) {
                                        if (processServiceRecord3.hasClientActivities()) {
                                            processStateRecord2.setAdjType("cch-client-act");
                                            i32 = 17;
                                        } else if (processServiceRecord3.isTreatedLikeActivity()) {
                                            processStateRecord2.setAdjType("cch-as-act");
                                            i32 = 16;
                                        }
                                    }
                                    if (i33 != i29) {
                                        if (!z || z2) {
                                            i34 = 1;
                                        } else {
                                            processStateRecord2.setServiceB(oomAdjuster.mNewNumAServiceProcs > oomAdjuster.mNumServiceProcs / 3);
                                            i34 = 1;
                                            oomAdjuster.mNewNumServiceProcs++;
                                            if (processStateRecord2.isServiceB()) {
                                                processStateRecord2.setServiceHighRam(false);
                                            } else {
                                                long lastPss = oomAdjuster.mService.mAppProfiler.isProfilingPss() ? processRecord.mProfile.getLastPss() : processRecord.mProfile.getLastRss();
                                                double cachedRestoreThresholdKb = oomAdjuster.mProcessList.getCachedRestoreThresholdKb() * (oomAdjuster.mService.mAppProfiler.isProfilingPss() ? 1.0d : oomAdjuster.mConstants.PSS_TO_RSS_THRESHOLD_MODIFIER);
                                                if (oomAdjuster.mService.mAppProfiler.isLastMemoryLevelNormal() || lastPss < cachedRestoreThresholdKb) {
                                                    oomAdjuster.mNewNumAServiceProcs++;
                                                } else {
                                                    processStateRecord2.setServiceHighRam(true);
                                                    processStateRecord2.setServiceB(true);
                                                }
                                            }
                                        }
                                        if (processStateRecord2.isServiceB()) {
                                            i33 = com.android.server.am.ProcessList.SERVICE_B_ADJ;
                                        }
                                    } else {
                                        i34 = 1;
                                    }
                                    processStateRecord2.setCurRawAdj(i33);
                                    processServiceRecord4 = processServiceRecord3;
                                    modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
                                    if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
                                        modifyRawOomAdj = processStateRecord2.getMaxAdj();
                                        i35 = modifyRawOomAdj <= 250 ? 2 : i72;
                                    } else {
                                        i35 = i72;
                                    }
                                    if (i32 >= 5 && oomAdjuster.mService.mWakefulness.get() != i34 && !processStateRecord2.shouldScheduleLikeTopApp() && i35 > i34) {
                                        i35 = i34;
                                    }
                                    if (processServiceRecord4.hasForegroundServices()) {
                                        i74 |= i18;
                                    }
                                    int defaultCapability = i74 | oomAdjuster.getDefaultCapability(processRecord, i32);
                                    if (i32 > 5) {
                                        defaultCapability &= -17;
                                    }
                                    if (!processRecord.isPendingFinishAttach()) {
                                        setAttachingProcessStatesLSP(processRecord);
                                        processStateRecord2.setAdjSeq(oomAdjuster.mAdjSeq);
                                        processStateRecord2.setCompletedAdjSeq(processStateRecord2.getAdjSeq());
                                        return false;
                                    }
                                    processStateRecord2.setCurCapability(defaultCapability);
                                    processStateRecord2.updateLastInvisibleTime(z11);
                                    processStateRecord2.setHasForegroundActivities(z12);
                                    processStateRecord2.setCompletedAdjSeq(oomAdjuster.mAdjSeq);
                                    int i82 = i11;
                                    int intermediateAdjLSP = oomAdjuster.setIntermediateAdjLSP(processRecord, modifyRawOomAdj, i82, i35);
                                    int i83 = i10;
                                    oomAdjuster.setIntermediateProcStateLSP(processRecord, i32, i83);
                                    oomAdjuster.setIntermediateSchedGroupLSP(processStateRecord2, intermediateAdjLSP);
                                    if (processStateRecord2.getCurAdj() < i82 || processStateRecord2.getCurProcState() < i83 || processStateRecord2.getCurCapability() != i23) {
                                        return i34;
                                    }
                                    return false;
                                }
                                i20 = i7;
                                if (z2) {
                                }
                                processStateRecord7.setCurRawAdj(i19);
                                processStateRecord7.setCurRawProcState(i13);
                                processStateRecord7.setHasStartedServices(false);
                                processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
                                backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
                                if (backupRecord != null) {
                                    if (i19 > 300) {
                                    }
                                    if (i13 > 9) {
                                    }
                                }
                                processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
                                processStateRecord7.setScheduleLikeTopApp(false);
                                int i632 = i5;
                                int i642 = i13;
                                int i652 = i20;
                                numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
                                while (true) {
                                    if (numberOfRunningServices >= 0) {
                                    }
                                    numberOfRunningServices = i46 - 1;
                                    oomAdjuster3 = this;
                                    j2 = j;
                                    i9 = i48;
                                    processServiceRecord2 = processServiceRecord5;
                                    processStateRecord7 = processStateRecord4;
                                    i14 = i45;
                                    z8 = z15;
                                    i12 = i47;
                                    z9 = z14;
                                    str = str7;
                                }
                                processProviderRecord = processRecord.mProviders;
                                i28 = 1;
                                numberOfProviders = processProviderRecord.numberOfProviders() - 1;
                                int i722 = i21;
                                int i732 = i19;
                                int i742 = i24;
                                int i752 = i642;
                                int i762 = i732;
                                while (true) {
                                    if (numberOfProviders >= 0) {
                                    }
                                    numberOfProviders = i37 - 1;
                                    processStateRecord = processStateRecord3;
                                    i22 = i42;
                                    i27 = i41;
                                    str4 = str6;
                                    i25 = i36;
                                    processProviderRecord = processProviderRecord3;
                                    i28 = 1;
                                    i26 = 2;
                                }
                                if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
                                }
                                if (i32 >= 19) {
                                }
                                if (i33 != i29) {
                                }
                                processStateRecord2.setCurRawAdj(i33);
                                processServiceRecord4 = processServiceRecord3;
                                modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
                                if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
                                }
                                if (i32 >= 5) {
                                    i35 = i34;
                                }
                                if (processServiceRecord4.hasForegroundServices()) {
                                }
                                int defaultCapability2 = i742 | oomAdjuster.getDefaultCapability(processRecord, i32);
                                if (i32 > 5) {
                                }
                                if (!processRecord.isPendingFinishAttach()) {
                                }
                            }
                        } else {
                            i15 = 4;
                        }
                        if (!hasForegroundServices && hasNonShortForegroundServices) {
                            str3 = "fg-service";
                            i17 = i15;
                            i18 = 16;
                            i16 = 200;
                        } else if (!z10) {
                            str3 = "fg-service-short";
                            i16 = 226;
                            i17 = i15;
                            i18 = 0;
                        } else if (processStateRecord7.hasOverlayUi()) {
                            str3 = "has-overlay-ui";
                            i16 = 200;
                            i17 = 6;
                            i18 = 0;
                        } else {
                            str3 = str2;
                            i16 = 0;
                            i17 = 0;
                            i18 = 0;
                        }
                        if (str3 != null) {
                            processStateRecord7.setAdjType(str3);
                            if (i14 == i9) {
                                oomAdjuster3.reportOomAdjMessageLocked(str, "Raise to " + str3 + ": " + processRecord + " ");
                            }
                            i4 = i16;
                            i13 = i17;
                            i7 = 2;
                        }
                        if (processServiceRecord2.hasForegroundServices()) {
                            if (processServiceRecord2.hasNonShortForegroundServices()) {
                            }
                            if (i14 == i9) {
                            }
                        }
                        if (processServiceRecord2.hasTopStartedAlmostPerceptibleServices()) {
                            processStateRecord7.setAdjType("top-ej-act");
                            if (i14 == i9) {
                            }
                            i4 = 52;
                        }
                        i19 = 200;
                        if (i4 <= 200) {
                        }
                        processStateRecord7.setAdjType("force-imp");
                        processStateRecord7.setAdjSource(processStateRecord7.getForcingToImportant());
                        if (i14 == i9) {
                        }
                        i13 = 8;
                        i7 = 2;
                        if (processStateRecord7.getCachedIsHeavyWeight()) {
                        }
                        if (processStateRecord7.getCachedIsHomeProcess()) {
                        }
                        if (processStateRecord7.getCachedIsPreviousProcess()) {
                            if (i13 >= 15) {
                            }
                            if (i19 > 700) {
                            }
                            if (i13 <= 15) {
                            }
                        }
                        i20 = i7;
                        if (z2) {
                        }
                        processStateRecord7.setCurRawAdj(i19);
                        processStateRecord7.setCurRawProcState(i13);
                        processStateRecord7.setHasStartedServices(false);
                        processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
                        backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
                        if (backupRecord != null) {
                        }
                        processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
                        processStateRecord7.setScheduleLikeTopApp(false);
                        int i6322 = i5;
                        int i6422 = i13;
                        int i6522 = i20;
                        numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
                        while (true) {
                            if (numberOfRunningServices >= 0) {
                            }
                            numberOfRunningServices = i46 - 1;
                            oomAdjuster3 = this;
                            j2 = j;
                            i9 = i48;
                            processServiceRecord2 = processServiceRecord5;
                            processStateRecord7 = processStateRecord4;
                            i14 = i45;
                            z8 = z15;
                            i12 = i47;
                            z9 = z14;
                            str = str7;
                        }
                        processProviderRecord = processRecord.mProviders;
                        i28 = 1;
                        numberOfProviders = processProviderRecord.numberOfProviders() - 1;
                        int i7222 = i21;
                        int i7322 = i19;
                        int i7422 = i24;
                        int i7522 = i6422;
                        int i7622 = i7322;
                        while (true) {
                            if (numberOfProviders >= 0) {
                            }
                            numberOfProviders = i37 - 1;
                            processStateRecord = processStateRecord3;
                            i22 = i42;
                            i27 = i41;
                            str4 = str6;
                            i25 = i36;
                            processProviderRecord = processProviderRecord3;
                            i28 = 1;
                            i26 = 2;
                        }
                        if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
                        }
                        if (i32 >= 19) {
                        }
                        if (i33 != i29) {
                        }
                        processStateRecord2.setCurRawAdj(i33);
                        processServiceRecord4 = processServiceRecord3;
                        modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
                        if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
                        }
                        if (i32 >= 5) {
                        }
                        if (processServiceRecord4.hasForegroundServices()) {
                        }
                        int defaultCapability22 = i7422 | oomAdjuster.getDefaultCapability(processRecord, i32);
                        if (i32 > 5) {
                        }
                        if (!processRecord.isPendingFinishAttach()) {
                        }
                    }
                } else {
                    j2 = j;
                    z9 = z7;
                    processServiceRecord2 = processServiceRecord;
                }
                z10 = false;
                if (i4 > 200) {
                }
                if (!hasForegroundServices) {
                }
                if (!z10) {
                }
                if (str3 != null) {
                }
                if (processServiceRecord2.hasForegroundServices()) {
                }
                if (processServiceRecord2.hasTopStartedAlmostPerceptibleServices()) {
                }
                i19 = 200;
                if (i4 <= 200) {
                }
                processStateRecord7.setAdjType("force-imp");
                processStateRecord7.setAdjSource(processStateRecord7.getForcingToImportant());
                if (i14 == i9) {
                }
                i13 = 8;
                i7 = 2;
                if (processStateRecord7.getCachedIsHeavyWeight()) {
                }
                if (processStateRecord7.getCachedIsHomeProcess()) {
                }
                if (processStateRecord7.getCachedIsPreviousProcess()) {
                }
                i20 = i7;
                if (z2) {
                }
                processStateRecord7.setCurRawAdj(i19);
                processStateRecord7.setCurRawProcState(i13);
                processStateRecord7.setHasStartedServices(false);
                processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
                backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
                if (backupRecord != null) {
                }
                processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
                processStateRecord7.setScheduleLikeTopApp(false);
                int i63222 = i5;
                int i64222 = i13;
                int i65222 = i20;
                numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
                while (true) {
                    if (numberOfRunningServices >= 0) {
                    }
                    numberOfRunningServices = i46 - 1;
                    oomAdjuster3 = this;
                    j2 = j;
                    i9 = i48;
                    processServiceRecord2 = processServiceRecord5;
                    processStateRecord7 = processStateRecord4;
                    i14 = i45;
                    z8 = z15;
                    i12 = i47;
                    z9 = z14;
                    str = str7;
                }
                processProviderRecord = processRecord.mProviders;
                i28 = 1;
                numberOfProviders = processProviderRecord.numberOfProviders() - 1;
                int i72222 = i21;
                int i73222 = i19;
                int i74222 = i24;
                int i75222 = i64222;
                int i76222 = i73222;
                while (true) {
                    if (numberOfProviders >= 0) {
                    }
                    numberOfProviders = i37 - 1;
                    processStateRecord = processStateRecord3;
                    i22 = i42;
                    i27 = i41;
                    str4 = str6;
                    i25 = i36;
                    processProviderRecord = processProviderRecord3;
                    i28 = 1;
                    i26 = 2;
                }
                if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
                }
                if (i32 >= 19) {
                }
                if (i33 != i29) {
                }
                processStateRecord2.setCurRawAdj(i33);
                processServiceRecord4 = processServiceRecord3;
                modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
                if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
                }
                if (i32 >= 5) {
                }
                if (processServiceRecord4.hasForegroundServices()) {
                }
                int defaultCapability222 = i74222 | oomAdjuster.getDefaultCapability(processRecord, i32);
                if (i32 > 5) {
                }
                if (!processRecord.isPendingFinishAttach()) {
                }
            }
        } else {
            i3 = 2;
        }
        if (processStateRecord7.isRunningRemoteAnimation()) {
            processStateRecord7.setAdjType("running-remote-anim");
            if (i61 == i60) {
                oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making running remote anim: " + processRecord);
            }
            i4 = 100;
            i7 = 3;
            i5 = initialCapability2;
            z5 = false;
            z6 = false;
        } else {
            if (processRecord.getActiveInstrumentation() != null) {
                processStateRecord7.setAdjType("instrumentation");
                int i84 = initialCapability2 | 16;
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making instrumentation: " + processRecord);
                }
                i7 = i3;
                i5 = i84;
                i4 = 0;
                z5 = false;
                z6 = false;
                i6 = 4;
            } else if (processStateRecord7.getCachedIsReceivingBroadcast(oomAdjuster3.mTmpSchedGroup)) {
                int i85 = oomAdjuster3.mTmpSchedGroup[0];
                processStateRecord7.setAdjType("broadcast");
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making broadcast: " + processRecord);
                }
                i7 = i85;
                i5 = initialCapability2;
                i4 = 0;
                z6 = false;
                i6 = 11;
                z5 = false;
            } else if (processServiceRecord8.numberOfExecutingServices() > 0) {
                int i86 = processServiceRecord8.shouldExecServicesFg() ? i3 : 0;
                processStateRecord7.setAdjType("exec-service");
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making exec-service: " + processRecord);
                }
                i7 = i86;
                i5 = initialCapability2;
                i4 = 0;
                z5 = false;
                z6 = false;
                i6 = 10;
            } else if (processRecord == processRecord2) {
                processStateRecord7.setAdjType("top-sleeping");
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making top (sleeping): " + processRecord);
                }
                z5 = true;
                i5 = initialCapability2;
                i4 = 0;
                z6 = false;
                i7 = 0;
            } else {
                if (!z4 || !processStateRecord7.containsCycle()) {
                    processStateRecord7.setAdjType("cch-empty");
                }
                if (i61 == i60) {
                    oomAdjuster3.reportOomAdjMessageLocked("ActivityManager", "Making empty: " + processRecord);
                }
                i4 = i;
                i5 = initialCapability2;
                z5 = false;
                z6 = false;
                i6 = 19;
                i7 = 0;
            }
            if (z5) {
            }
            processServiceRecord = processServiceRecord8;
            i8 = i61;
            i9 = i60;
            i10 = initialProcState;
            i11 = initialAdj;
            str = "ActivityManager";
            str2 = null;
            i12 = initialCapability;
            i13 = i6;
            boolean z212 = z6;
            z7 = z5;
            z8 = z212;
            if (i13 > 18) {
            }
            i14 = i8;
            hasForegroundServices = processServiceRecord.hasForegroundServices();
            boolean hasNonShortForegroundServices2 = processServiceRecord.hasNonShortForegroundServices();
            if (hasForegroundServices) {
            }
            z10 = false;
            if (i4 > 200) {
            }
            if (!hasForegroundServices) {
            }
            if (!z10) {
            }
            if (str3 != null) {
            }
            if (processServiceRecord2.hasForegroundServices()) {
            }
            if (processServiceRecord2.hasTopStartedAlmostPerceptibleServices()) {
            }
            i19 = 200;
            if (i4 <= 200) {
            }
            processStateRecord7.setAdjType("force-imp");
            processStateRecord7.setAdjSource(processStateRecord7.getForcingToImportant());
            if (i14 == i9) {
            }
            i13 = 8;
            i7 = 2;
            if (processStateRecord7.getCachedIsHeavyWeight()) {
            }
            if (processStateRecord7.getCachedIsHomeProcess()) {
            }
            if (processStateRecord7.getCachedIsPreviousProcess()) {
            }
            i20 = i7;
            if (z2) {
            }
            processStateRecord7.setCurRawAdj(i19);
            processStateRecord7.setCurRawProcState(i13);
            processStateRecord7.setHasStartedServices(false);
            processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
            backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
            if (backupRecord != null) {
            }
            processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
            processStateRecord7.setScheduleLikeTopApp(false);
            int i632222 = i5;
            int i642222 = i13;
            int i652222 = i20;
            numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
            while (true) {
                if (numberOfRunningServices >= 0) {
                }
                numberOfRunningServices = i46 - 1;
                oomAdjuster3 = this;
                j2 = j;
                i9 = i48;
                processServiceRecord2 = processServiceRecord5;
                processStateRecord7 = processStateRecord4;
                i14 = i45;
                z8 = z15;
                i12 = i47;
                z9 = z14;
                str = str7;
            }
            processProviderRecord = processRecord.mProviders;
            i28 = 1;
            numberOfProviders = processProviderRecord.numberOfProviders() - 1;
            int i722222 = i21;
            int i732222 = i19;
            int i742222 = i24;
            int i752222 = i642222;
            int i762222 = i732222;
            while (true) {
                if (numberOfProviders >= 0) {
                }
                numberOfProviders = i37 - 1;
                processStateRecord = processStateRecord3;
                i22 = i42;
                i27 = i41;
                str4 = str6;
                i25 = i36;
                processProviderRecord = processProviderRecord3;
                i28 = 1;
                i26 = 2;
            }
            if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
            }
            if (i32 >= 19) {
            }
            if (i33 != i29) {
            }
            processStateRecord2.setCurRawAdj(i33);
            processServiceRecord4 = processServiceRecord3;
            modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
            if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
            }
            if (i32 >= 5) {
            }
            if (processServiceRecord4.hasForegroundServices()) {
            }
            int defaultCapability2222 = i742222 | oomAdjuster.getDefaultCapability(processRecord, i32);
            if (i32 > 5) {
            }
            if (!processRecord.isPendingFinishAttach()) {
            }
        }
        i6 = topProcessState;
        if (z5) {
        }
        processServiceRecord = processServiceRecord8;
        i8 = i61;
        i9 = i60;
        i10 = initialProcState;
        i11 = initialAdj;
        str = "ActivityManager";
        str2 = null;
        i12 = initialCapability;
        i13 = i6;
        boolean z2122 = z6;
        z7 = z5;
        z8 = z2122;
        if (i13 > 18) {
        }
        i14 = i8;
        hasForegroundServices = processServiceRecord.hasForegroundServices();
        boolean hasNonShortForegroundServices22 = processServiceRecord.hasNonShortForegroundServices();
        if (hasForegroundServices) {
        }
        z10 = false;
        if (i4 > 200) {
        }
        if (!hasForegroundServices) {
        }
        if (!z10) {
        }
        if (str3 != null) {
        }
        if (processServiceRecord2.hasForegroundServices()) {
        }
        if (processServiceRecord2.hasTopStartedAlmostPerceptibleServices()) {
        }
        i19 = 200;
        if (i4 <= 200) {
        }
        processStateRecord7.setAdjType("force-imp");
        processStateRecord7.setAdjSource(processStateRecord7.getForcingToImportant());
        if (i14 == i9) {
        }
        i13 = 8;
        i7 = 2;
        if (processStateRecord7.getCachedIsHeavyWeight()) {
        }
        if (processStateRecord7.getCachedIsHomeProcess()) {
        }
        if (processStateRecord7.getCachedIsPreviousProcess()) {
        }
        i20 = i7;
        if (z2) {
        }
        processStateRecord7.setCurRawAdj(i19);
        processStateRecord7.setCurRawProcState(i13);
        processStateRecord7.setHasStartedServices(false);
        processStateRecord7.setAdjSeq(oomAdjuster3.mAdjSeq);
        backupRecord = oomAdjuster3.mService.mBackupTargets.get(processRecord.userId);
        if (backupRecord != null) {
        }
        processStateRecord7.setCurBoundByNonBgRestrictedApp(getInitialIsCurBoundByNonBgRestrictedApp(processRecord));
        processStateRecord7.setScheduleLikeTopApp(false);
        int i6322222 = i5;
        int i6422222 = i13;
        int i6522222 = i20;
        numberOfRunningServices = processServiceRecord2.numberOfRunningServices() - 1;
        while (true) {
            if (numberOfRunningServices >= 0) {
            }
            numberOfRunningServices = i46 - 1;
            oomAdjuster3 = this;
            j2 = j;
            i9 = i48;
            processServiceRecord2 = processServiceRecord5;
            processStateRecord7 = processStateRecord4;
            i14 = i45;
            z8 = z15;
            i12 = i47;
            z9 = z14;
            str = str7;
        }
        processProviderRecord = processRecord.mProviders;
        i28 = 1;
        numberOfProviders = processProviderRecord.numberOfProviders() - 1;
        int i7222222 = i21;
        int i7322222 = i19;
        int i7422222 = i24;
        int i7522222 = i6422222;
        int i7622222 = i7322222;
        while (true) {
            if (numberOfProviders >= 0) {
            }
            numberOfProviders = i37 - 1;
            processStateRecord = processStateRecord3;
            i22 = i42;
            i27 = i41;
            str4 = str6;
            i25 = i36;
            processProviderRecord = processProviderRecord3;
            i28 = 1;
            i26 = 2;
        }
        if (processProviderRecord2.getLastProviderTime() + oomAdjuster.mConstants.CONTENT_PROVIDER_RETAIN_TIME <= j) {
        }
        if (i32 >= 19) {
        }
        if (i33 != i29) {
        }
        processStateRecord2.setCurRawAdj(i33);
        processServiceRecord4 = processServiceRecord3;
        modifyRawOomAdj = processServiceRecord4.modifyRawOomAdj(i33);
        if (modifyRawOomAdj <= processStateRecord2.getMaxAdj()) {
        }
        if (i32 >= 5) {
        }
        if (processServiceRecord4.hasForegroundServices()) {
        }
        int defaultCapability22222 = i7422222 | oomAdjuster.getDefaultCapability(processRecord, i32);
        if (i32 > 5) {
        }
        if (!processRecord.isPendingFinishAttach()) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected int setIntermediateAdjLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2, int i3) {
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        processStateRecord.setCurRawAdj(i);
        int modifyRawOomAdj = processRecord.mServices.modifyRawOomAdj(i);
        if (modifyRawOomAdj > processStateRecord.getMaxAdj() && (modifyRawOomAdj = processStateRecord.getMaxAdj()) <= 250) {
            i3 = 2;
        }
        processStateRecord.setCurAdj(modifyRawOomAdj);
        return i3;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void setIntermediateProcStateLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2) {
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        processStateRecord.setCurProcState(i);
        processStateRecord.setCurRawProcState(i);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void setIntermediateSchedGroupLSP(com.android.server.am.ProcessStateRecord processStateRecord, int i) {
        if (processStateRecord.getCurProcState() >= 5 && this.mService.mWakefulness.get() != 1 && !processStateRecord.shouldScheduleLikeTopApp() && i > 1) {
            i = 1;
        }
        processStateRecord.setCurrentSchedulingGroup(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:170:0x04c7, code lost:
    
        if ((r4 & r7) != r7) goto L307;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x034f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0364 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0391 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x04bb  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x04d0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x04d1  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02ca  */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean computeServiceHostOomAdjLSP(com.android.server.am.ConnectionRecord connectionRecord, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, long j, com.android.server.am.ProcessRecord processRecord3, boolean z, boolean z2, boolean z3, int i, int i2, boolean z4, boolean z5) {
        com.android.server.am.ProcessStateRecord processStateRecord;
        com.android.server.am.ProcessRecord processRecord4;
        com.android.server.am.ProcessStateRecord processStateRecord2;
        com.android.server.am.ProcessStateRecord processStateRecord3;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean z6;
        boolean z7;
        int i15;
        int i16;
        com.android.server.am.ProcessStateRecord processStateRecord4;
        int i17;
        int i18;
        int i19;
        boolean z8;
        java.lang.String str;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        boolean z9;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        int i32;
        int i33;
        boolean z10;
        com.android.server.am.ProcessRecord processRecord5;
        com.android.server.am.ProcessStateRecord processStateRecord5;
        com.android.server.am.ProcessStateRecord processStateRecord6;
        if (processRecord.isPendingFinishAttach()) {
            return false;
        }
        com.android.server.am.ProcessStateRecord processStateRecord7 = processRecord.mState;
        com.android.server.am.ProcessStateRecord processStateRecord8 = processRecord2.mState;
        if (!z4) {
            processStateRecord = processStateRecord7;
            processRecord4 = processRecord2;
            processStateRecord2 = processStateRecord8;
        } else {
            if (processRecord.isSdkSandbox && connectionRecord.binding.attributedClient != null) {
                com.android.server.am.ProcessRecord processRecord6 = connectionRecord.binding.attributedClient;
                processRecord5 = processRecord6;
                processStateRecord5 = processRecord6.mState;
            } else {
                processRecord5 = processRecord2;
                processStateRecord5 = processStateRecord8;
            }
            if (z3) {
                processStateRecord = processStateRecord7;
                computeOomAdjLSP(processRecord5, i2, processRecord3, z, j, z2, true, i, true);
                processStateRecord6 = processStateRecord5;
            } else {
                com.android.server.am.ProcessStateRecord processStateRecord9 = processStateRecord5;
                processStateRecord = processStateRecord7;
                processStateRecord6 = processStateRecord9;
                processStateRecord6.setCurRawAdj(processStateRecord9.getCurAdj());
                processStateRecord6.setCurRawProcState(processStateRecord6.getCurProcState());
            }
            processStateRecord2 = processStateRecord6;
            processRecord4 = processRecord5;
        }
        int curRawAdj = processStateRecord2.getCurRawAdj();
        int curRawProcState = processStateRecord2.getCurRawProcState();
        boolean z11 = curRawProcState < 2;
        int curRawAdj2 = processStateRecord.getCurRawAdj();
        int curRawProcState2 = processStateRecord.getCurRawProcState();
        int currentSchedulingGroup = processStateRecord.getCurrentSchedulingGroup();
        int curCapability = processStateRecord.getCurCapability();
        int i34 = processRecord.info.uid;
        int i35 = this.mService.mCurOomAdjUid;
        if (z5) {
            processStateRecord3 = processStateRecord;
        } else {
            processStateRecord3 = processStateRecord;
            processStateRecord3.setCurBoundByNonBgRestrictedApp(processStateRecord.isCurBoundByNonBgRestrictedApp() || processStateRecord2.isCurBoundByNonBgRestrictedApp() || curRawProcState <= 3 || (curRawProcState == 4 && !processStateRecord2.isBackgroundRestricted()));
        }
        if (!processRecord4.mOptRecord.shouldNotFreeze()) {
            i3 = i35;
        } else {
            i3 = i35;
            if (processRecord.mOptRecord.setShouldNotFreeze(true, z5)) {
                return true;
            }
        }
        int bfslCapabilityFromClient = getBfslCapabilityFromClient(processRecord4) | curCapability;
        if (connectionRecord.notHasFlag(32)) {
            if (connectionRecord.hasFlag(4096)) {
                bfslCapabilityFromClient |= processStateRecord2.getCurCapability();
            }
            if ((processStateRecord2.getCurCapability() & 8) != 0) {
                if (curRawProcState <= 5) {
                    if (connectionRecord.hasFlag(131072)) {
                        bfslCapabilityFromClient |= 8;
                    }
                } else {
                    bfslCapabilityFromClient |= 8;
                }
            }
            com.android.server.am.ProcessRecord processRecord7 = processRecord4;
            if ((processStateRecord2.getCurCapability() & 32) == 0) {
                processStateRecord4 = processStateRecord2;
            } else if (curRawProcState > 6) {
                processStateRecord4 = processStateRecord2;
            } else {
                processStateRecord4 = processStateRecord2;
                if (connectionRecord.hasFlag(4294967296L)) {
                    i17 = bfslCapabilityFromClient | 32;
                    if (z4) {
                        i18 = i34;
                        i4 = curCapability;
                        i5 = currentSchedulingGroup;
                        i6 = curRawProcState2;
                        i19 = curRawAdj2;
                        z8 = true;
                    } else {
                        i5 = currentSchedulingGroup;
                        i6 = curRawProcState2;
                        i19 = curRawAdj2;
                        i4 = curCapability;
                        i18 = i34;
                        z8 = true;
                        if (shouldSkipDueToCycle(processRecord, processStateRecord4, curRawProcState2, curRawAdj2, z2)) {
                            return false;
                        }
                    }
                    if (curRawProcState >= 16) {
                        curRawProcState = 19;
                    }
                    if (connectionRecord.hasFlag(16)) {
                        i20 = curRawAdj;
                        i7 = i19;
                    } else {
                        if (curRawAdj < 900 && processRecord.mOptRecord.setShouldNotFreeze(z8, z5)) {
                            return z8;
                        }
                        if (!processStateRecord3.hasShownUi() || processStateRecord3.getCachedIsHomeProcess()) {
                            i7 = i19;
                            if (j < connectionRecord.binding.service.lastActivity + this.mConstants.MAX_SERVICE_INACTIVITY) {
                                i20 = curRawAdj;
                            } else {
                                str = i7 > curRawAdj ? "cch-bound-services" : null;
                                i21 = i7;
                                i22 = curRawProcState;
                            }
                        } else {
                            i7 = i19;
                            str = i7 > curRawAdj ? "cch-bound-ui-services" : null;
                            if (processStateRecord3.isCached() && z5) {
                                return z8;
                            }
                            i22 = i6;
                            i21 = i7;
                        }
                        if (i7 > i21) {
                            if (processStateRecord3.hasShownUi() && !processStateRecord3.getCachedIsHomeProcess() && i21 > 200) {
                                if (i7 >= 900) {
                                    i23 = i6;
                                    str = "cch-bound-ui-services";
                                    i14 = i7;
                                    i24 = i5;
                                    z9 = false;
                                }
                            } else if (connectionRecord.hasFlag(72)) {
                                if (i21 < -700) {
                                    if (!z5) {
                                        connectionRecord.trackProcState(0, this.mAdjSeq);
                                    }
                                    i21 = -700;
                                    i33 = 2;
                                    i32 = 0;
                                    z10 = true;
                                    if (processStateRecord4.isCached()) {
                                    }
                                    if (i7 <= i21) {
                                    }
                                } else {
                                    i32 = i6;
                                    i33 = i5;
                                    z10 = false;
                                    if (processStateRecord4.isCached() && processStateRecord3.isCached() && z5) {
                                        return true;
                                    }
                                    if (i7 <= i21) {
                                        i23 = i32;
                                        i24 = i33;
                                        z9 = z10;
                                        i14 = i7;
                                    } else {
                                        processStateRecord3.setCurRawAdj(i21, z5);
                                        str = com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE;
                                        int i36 = i32;
                                        i24 = i33;
                                        z9 = z10;
                                        i14 = i21;
                                        i23 = i36;
                                    }
                                }
                            } else {
                                if (!connectionRecord.hasFlag(256) || i21 > 200) {
                                    i29 = 100;
                                } else {
                                    i29 = 250;
                                    if (i7 >= 250) {
                                        i32 = i6;
                                        i21 = 250;
                                        i33 = i5;
                                        z10 = false;
                                        if (processStateRecord4.isCached()) {
                                        }
                                        if (i7 <= i21) {
                                        }
                                    }
                                }
                                if (!connectionRecord.hasFlag(65536)) {
                                    i30 = 4;
                                } else {
                                    i30 = 4;
                                    if (connectionRecord.notHasFlag(4) && i21 < 200) {
                                        if (i7 < 200) {
                                            i29 = 200;
                                        } else {
                                            i32 = i6;
                                            i21 = 201;
                                            i33 = i5;
                                            z10 = false;
                                            if (processStateRecord4.isCached()) {
                                            }
                                            if (i7 <= i21) {
                                            }
                                        }
                                    }
                                }
                                if (connectionRecord.hasFlag(65536) && connectionRecord.hasFlag(i30) && i21 < 200) {
                                    i29 = com.android.internal.util.FrameworkStatsLog.CAMERA_ACTION_EVENT;
                                    if (i7 >= 227) {
                                        i32 = i6;
                                        i21 = 227;
                                        i33 = i5;
                                        z10 = false;
                                        if (processStateRecord4.isCached()) {
                                        }
                                        if (i7 <= i21) {
                                        }
                                    }
                                }
                                if (connectionRecord.hasFlag(1073741824) && i21 < 200) {
                                    if (i7 < 200) {
                                        i29 = 200;
                                    } else {
                                        i21 = 200;
                                        i33 = i5;
                                        z10 = false;
                                        i32 = i6;
                                        if (processStateRecord4.isCached()) {
                                        }
                                        if (i7 <= i21) {
                                        }
                                    }
                                }
                                if (i21 < 200) {
                                    if (connectionRecord.hasFlag(268435456)) {
                                        i31 = 100;
                                        if (i21 <= 100 && i7 > 100) {
                                            i21 = 100;
                                            i33 = i5;
                                            z10 = false;
                                            i32 = i6;
                                            if (processStateRecord4.isCached()) {
                                            }
                                            if (i7 <= i21) {
                                            }
                                        }
                                    } else {
                                        i31 = 100;
                                    }
                                    if (i7 > i31) {
                                        i32 = i6;
                                        i21 = java.lang.Math.max(i21, i29);
                                        i33 = i5;
                                        z10 = false;
                                    } else {
                                        i32 = i6;
                                        i21 = i7;
                                        i33 = i5;
                                        z10 = false;
                                    }
                                    if (processStateRecord4.isCached()) {
                                    }
                                    if (i7 <= i21) {
                                    }
                                }
                                i32 = i6;
                                i33 = i5;
                                z10 = false;
                                if (processStateRecord4.isCached()) {
                                }
                                if (i7 <= i21) {
                                }
                            }
                            if (!connectionRecord.notHasFlag(8388612)) {
                                int currentSchedulingGroup2 = processStateRecord4.getCurrentSchedulingGroup();
                                if (currentSchedulingGroup2 <= i24) {
                                    currentSchedulingGroup2 = i24;
                                } else if (!connectionRecord.hasFlag(64)) {
                                    currentSchedulingGroup2 = 2;
                                }
                                if (i22 < 2) {
                                    if (connectionRecord.hasFlag(268435456)) {
                                        i22 = 4;
                                    } else if (connectionRecord.hasFlag(67108864)) {
                                        i22 = 5;
                                    } else if (this.mService.mWakefulness.get() == 1 && connectionRecord.hasFlag(33554432)) {
                                        i22 = 5;
                                    } else {
                                        i22 = 6;
                                    }
                                } else if (i22 == 2) {
                                    com.android.server.am.ProcessStateRecord processStateRecord10 = processStateRecord4;
                                    if (processStateRecord10.getCachedCompatChange(0)) {
                                        if (!connectionRecord.hasFlag(4096)) {
                                            i22 = 3;
                                        } else {
                                            i17 |= processStateRecord10.getCurCapability();
                                            i22 = 3;
                                        }
                                    } else {
                                        i17 |= processStateRecord10.getCurCapability();
                                        i22 = 3;
                                    }
                                }
                                i24 = currentSchedulingGroup2;
                                i25 = i17;
                            } else if (connectionRecord.notHasFlag(8388608)) {
                                if (i22 < 8) {
                                    i22 = 8;
                                    i25 = i17;
                                }
                                i25 = i17;
                            } else {
                                if (i22 < 7) {
                                    i22 = 7;
                                    i25 = i17;
                                }
                                i25 = i17;
                            }
                            if (!connectionRecord.hasFlag(524288) && z11) {
                                if (z5) {
                                    i26 = 3;
                                    if (i5 < 3) {
                                        return true;
                                    }
                                } else {
                                    i26 = 3;
                                    processStateRecord3.setScheduleLikeTopApp(true);
                                }
                            } else {
                                i26 = i24;
                            }
                            if (!z9 && !z5) {
                                connectionRecord.trackProcState(i22, this.mAdjSeq);
                            }
                            if (i23 > i22) {
                                if (processStateRecord3.setCurRawProcState(i22, z5)) {
                                    return true;
                                }
                                if (str != null) {
                                    i23 = i22;
                                } else {
                                    str = com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE;
                                    i23 = i22;
                                }
                            }
                            if (i23 < 7 && connectionRecord.hasFlag(536870912) && !z5) {
                                processRecord.setPendingUiClean(true);
                            }
                            if (str != null || z5) {
                                i27 = i22;
                                i28 = i25;
                                i8 = i3;
                                i9 = i18;
                            } else {
                                processStateRecord3.setAdjType(str);
                                processStateRecord3.setAdjTypeCode(2);
                                processStateRecord3.setAdjSource(processRecord7);
                                processStateRecord3.setAdjSourceProcState(i22);
                                processStateRecord3.setAdjTarget(connectionRecord.binding.service.instanceName);
                                i8 = i3;
                                i9 = i18;
                                if (i8 != i9) {
                                    i27 = i22;
                                    i28 = i25;
                                } else {
                                    i27 = i22;
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    i28 = i25;
                                    sb.append("Raise to ");
                                    sb.append(str);
                                    sb.append(": ");
                                    sb.append(processRecord);
                                    sb.append(", due to ");
                                    sb.append(processRecord7);
                                    sb.append(" adj=");
                                    sb.append(i14);
                                    sb.append(" procState=");
                                    sb.append(com.android.server.am.ProcessList.makeProcStateString(i23));
                                    reportOomAdjMessageLocked("ActivityManager", sb.toString());
                                }
                            }
                            i11 = i28;
                            i13 = i26;
                            i10 = i23;
                            i12 = i27;
                        }
                        i23 = i6;
                        i14 = i7;
                        i24 = i5;
                        z9 = false;
                        if (!connectionRecord.notHasFlag(8388612)) {
                        }
                        if (!connectionRecord.hasFlag(524288)) {
                        }
                        i26 = i24;
                        if (!z9) {
                            connectionRecord.trackProcState(i22, this.mAdjSeq);
                        }
                        if (i23 > i22) {
                        }
                        if (i23 < 7) {
                            processRecord.setPendingUiClean(true);
                        }
                        if (str != null) {
                        }
                        i27 = i22;
                        i28 = i25;
                        i8 = i3;
                        i9 = i18;
                        i11 = i28;
                        i13 = i26;
                        i10 = i23;
                        i12 = i27;
                    }
                    i21 = i20;
                    i22 = curRawProcState;
                    if (i7 > i21) {
                    }
                    i23 = i6;
                    i14 = i7;
                    i24 = i5;
                    z9 = false;
                    if (!connectionRecord.notHasFlag(8388612)) {
                    }
                    if (!connectionRecord.hasFlag(524288)) {
                    }
                    i26 = i24;
                    if (!z9) {
                    }
                    if (i23 > i22) {
                    }
                    if (i23 < 7) {
                    }
                    if (str != null) {
                    }
                    i27 = i22;
                    i28 = i25;
                    i8 = i3;
                    i9 = i18;
                    i11 = i28;
                    i13 = i26;
                    i10 = i23;
                    i12 = i27;
                }
            }
            i17 = bfslCapabilityFromClient;
            if (z4) {
            }
            if (curRawProcState >= 16) {
            }
            if (connectionRecord.hasFlag(16)) {
            }
            i21 = i20;
            i22 = curRawProcState;
            if (i7 > i21) {
            }
            i23 = i6;
            i14 = i7;
            i24 = i5;
            z9 = false;
            if (!connectionRecord.notHasFlag(8388612)) {
            }
            if (!connectionRecord.hasFlag(524288)) {
            }
            i26 = i24;
            if (!z9) {
            }
            if (i23 > i22) {
            }
            if (i23 < 7) {
            }
            if (str != null) {
            }
            i27 = i22;
            i28 = i25;
            i8 = i3;
            i9 = i18;
            i11 = i28;
            i13 = i26;
            i10 = i23;
            i12 = i27;
        } else {
            i4 = curCapability;
            i5 = currentSchedulingGroup;
            i6 = curRawProcState2;
            i7 = curRawAdj2;
            i8 = i3;
            i9 = i34;
            if (curRawAdj < 900 && processRecord.mOptRecord.setShouldNotFreeze(true, z5)) {
                return true;
            }
            i10 = i6;
            i11 = bfslCapabilityFromClient;
            i12 = curRawProcState;
            i13 = i5;
            i14 = i7;
        }
        if (connectionRecord.hasFlag(134217728)) {
            if (!z5) {
                processRecord.mServices.setTreatLikeActivity(true);
            }
            if (i12 <= 16 && i10 > 16) {
                processStateRecord3.setAdjType("cch-as-act");
                i10 = 16;
            }
        }
        com.android.server.wm.ActivityServiceConnectionsHolder<com.android.server.am.ConnectionRecord> activityServiceConnectionsHolder = connectionRecord.activity;
        if (!connectionRecord.hasFlag(128)) {
            z6 = false;
            z7 = true;
        } else if (activityServiceConnectionsHolder == null || i14 <= 0) {
            z6 = false;
            z7 = true;
        } else if (!activityServiceConnectionsHolder.isActivityVisible()) {
            z6 = false;
            z7 = true;
        } else {
            z6 = false;
            if (processStateRecord3.setCurRawAdj(0, z5)) {
                return true;
            }
            z7 = true;
            if (connectionRecord.notHasFlag(4)) {
                if (connectionRecord.hasFlag(64)) {
                    i13 = 4;
                } else {
                    i13 = 2;
                }
            }
            if (!z5) {
                processStateRecord3.setAdjType(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE);
                processStateRecord3.setAdjTypeCode(2);
                processStateRecord3.setAdjSource(activityServiceConnectionsHolder);
                processStateRecord3.setAdjSourceProcState(i10);
                processStateRecord3.setAdjTarget(connectionRecord.binding.service.instanceName);
                if (i8 == i9) {
                    reportOomAdjMessageLocked("ActivityManager", "Raise to service w/activity: " + processRecord);
                }
            }
            i15 = 0;
            int defaultCapability = getDefaultCapability(processRecord, i10) | i11;
            if (i10 > 5) {
                defaultCapability &= -17;
            }
            if (i15 < i7) {
                i16 = i6;
                if (i10 >= i16) {
                    if (i13 <= i5) {
                        int i37 = i4;
                        if (defaultCapability != i37) {
                        }
                        if (z5) {
                            return z6;
                        }
                        if (i15 < i7) {
                            i13 = setIntermediateAdjLSP(processRecord, i15, i7, i13);
                        }
                        if (i10 < i16) {
                            setIntermediateProcStateLSP(processRecord, i10, i16);
                        }
                        if (i13 > i5) {
                            setIntermediateSchedGroupLSP(processStateRecord3, i13);
                        }
                        processStateRecord3.setCurCapability(defaultCapability);
                        return z6;
                    }
                }
            } else {
                i16 = i6;
            }
            z6 = z7;
            if (z5) {
            }
        }
        i15 = i14;
        int defaultCapability2 = getDefaultCapability(processRecord, i10) | i11;
        if (i10 > 5) {
        }
        if (i15 < i7) {
        }
        z6 = z7;
        if (z5) {
        }
    }

    protected boolean computeProviderHostOomAdjLSP(com.android.server.am.ContentProviderConnection contentProviderConnection, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, long j, com.android.server.am.ProcessRecord processRecord3, boolean z, boolean z2, boolean z3, int i, int i2, boolean z4, boolean z5) {
        com.android.server.am.ProcessStateRecord processStateRecord;
        com.android.server.am.ProcessStateRecord processStateRecord2;
        com.android.server.am.ProcessStateRecord processStateRecord3;
        java.lang.String str;
        java.lang.String str2;
        int i3;
        int i4;
        boolean z6;
        com.android.server.am.ContentProviderConnection contentProviderConnection2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z7;
        if (processRecord.isPendingFinishAttach()) {
            return false;
        }
        com.android.server.am.ProcessStateRecord processStateRecord4 = processRecord.mState;
        com.android.server.am.ProcessStateRecord processStateRecord5 = processRecord2.mState;
        if (processRecord2 == processRecord) {
            return false;
        }
        if (!z4) {
            processStateRecord = processStateRecord5;
            processStateRecord2 = processStateRecord4;
        } else {
            if (z3) {
                processStateRecord2 = processStateRecord4;
                z7 = false;
                computeOomAdjLSP(processRecord2, i2, processRecord3, z, j, z2, true, i, true);
                processStateRecord = processStateRecord5;
            } else {
                processStateRecord2 = processStateRecord4;
                z7 = false;
                if (z4) {
                    processStateRecord = processStateRecord5;
                    processStateRecord.setCurRawAdj(processStateRecord5.getCurAdj());
                    processStateRecord.setCurRawProcState(processStateRecord.getCurProcState());
                } else {
                    processStateRecord = processStateRecord5;
                }
            }
            if (shouldSkipDueToCycle(processRecord, processStateRecord, processStateRecord2.getCurRawProcState(), processStateRecord2.getCurRawAdj(), z2)) {
                return z7;
            }
        }
        int curRawAdj = processStateRecord.getCurRawAdj();
        int curRawProcState = processStateRecord.getCurRawProcState();
        int curRawAdj2 = processStateRecord2.getCurRawAdj();
        int curRawProcState2 = processStateRecord2.getCurRawProcState();
        int currentSchedulingGroup = processStateRecord2.getCurrentSchedulingGroup();
        int curCapability = processStateRecord2.getCurCapability();
        int i11 = processRecord.info.uid;
        int i12 = this.mService.mCurOomAdjUid;
        int bfslCapabilityFromClient = getBfslCapabilityFromClient(processRecord2) | curCapability;
        if (curRawProcState >= 16) {
            curRawProcState = 19;
        }
        if (processRecord2.mOptRecord.shouldNotFreeze() && processRecord.mOptRecord.setShouldNotFreeze(true, z5)) {
            return true;
        }
        if (z5) {
            processStateRecord3 = processStateRecord2;
        } else {
            processStateRecord3 = processStateRecord2;
            processStateRecord3.setCurBoundByNonBgRestrictedApp(processStateRecord2.isCurBoundByNonBgRestrictedApp() || processStateRecord.isCurBoundByNonBgRestrictedApp() || curRawProcState <= 3 || (curRawProcState == 4 && !processStateRecord.isBackgroundRestricted()));
        }
        if (curRawAdj2 <= curRawAdj) {
            str = "provider";
            str2 = null;
            i3 = curRawAdj2;
        } else {
            if (!processStateRecord3.hasShownUi() || processStateRecord3.getCachedIsHomeProcess()) {
                str = "provider";
            } else {
                str = "provider";
                if (curRawAdj > 200) {
                    str2 = "cch-ui-provider";
                    i10 = curRawAdj2;
                    if (!processStateRecord3.isCached() && !processStateRecord.isCached() && z5) {
                        return true;
                    }
                    i3 = i10;
                }
            }
            int max = java.lang.Math.max(curRawAdj, 0);
            if (processStateRecord3.setCurRawAdj(max, z5)) {
                return true;
            }
            i10 = max;
            str2 = str;
            if (!processStateRecord3.isCached()) {
            }
            i3 = i10;
        }
        if (curRawProcState <= 4) {
            if (str2 == null) {
                str2 = str;
            }
            if (curRawProcState == 2) {
                curRawProcState = 3;
            } else {
                curRawProcState = 5;
            }
        }
        if (z5) {
            i4 = curRawAdj2;
            z6 = false;
            contentProviderConnection2 = contentProviderConnection;
        } else {
            i4 = curRawAdj2;
            z6 = false;
            contentProviderConnection2 = contentProviderConnection;
            contentProviderConnection2.trackProcState(curRawProcState, this.mAdjSeq);
        }
        if (curRawProcState2 <= curRawProcState) {
            i5 = curRawProcState2;
        } else if (!processStateRecord3.setCurRawProcState(curRawProcState, z5)) {
            i5 = curRawProcState;
        } else {
            return true;
        }
        if (processStateRecord.getCurrentSchedulingGroup() <= currentSchedulingGroup) {
            i6 = currentSchedulingGroup;
        } else {
            i6 = 2;
        }
        if (str2 == null || z5) {
            i7 = currentSchedulingGroup;
        } else {
            processStateRecord3.setAdjType(str2);
            i7 = currentSchedulingGroup;
            processStateRecord3.setAdjTypeCode(1);
            processStateRecord3.setAdjSource(processRecord2);
            processStateRecord3.setAdjSourceProcState(curRawProcState);
            processStateRecord3.setAdjTarget(contentProviderConnection2.provider.name);
            if (i12 == i11) {
                reportOomAdjMessageLocked("ActivityManager", "Raise to " + str2 + ": " + processRecord + ", due to " + processRecord2 + " adj=" + i3 + " procState=" + com.android.server.am.ProcessList.makeProcStateString(i5));
            }
        }
        if (i5 > 5) {
            bfslCapabilityFromClient &= -17;
        }
        if (z5) {
            i9 = i4;
            if (i3 >= i9 && i5 >= curRawProcState2 && i6 <= (i8 = i7)) {
                if (bfslCapabilityFromClient != curCapability && (bfslCapabilityFromClient & curCapability) == curCapability) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            i8 = i7;
            i9 = i4;
        }
        if (i3 < i9) {
            i6 = setIntermediateAdjLSP(processRecord, i3, i9, i6);
        }
        if (i5 < curRawProcState2) {
            setIntermediateProcStateLSP(processRecord, i5, curRawProcState2);
        }
        if (i6 > i8) {
            setIntermediateSchedGroupLSP(processStateRecord3, i6);
        }
        processStateRecord3.setCurCapability(bfslCapabilityFromClient);
        return z6;
    }

    protected int getDefaultCapability(com.android.server.am.ProcessRecord processRecord, int i) {
        int defaultProcessNetworkCapabilities = android.net.NetworkPolicyManager.getDefaultProcessNetworkCapabilities(i);
        int i2 = 0;
        switch (i) {
            case 0:
            case 1:
            case 2:
                i2 = 127;
                break;
            case 3:
                i2 = 16;
                break;
            case 4:
                if (processRecord.getActiveInstrumentation() != null) {
                    i2 = 6;
                    break;
                }
                break;
        }
        return i2 | defaultProcessNetworkCapabilities;
    }

    protected int getBfslCapabilityFromClient(com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.mState.getCurProcState() < 4) {
            return 16;
        }
        return processRecord.mState.getCurCapability() & 16;
    }

    private boolean shouldSkipDueToCycle(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessStateRecord processStateRecord, int i, int i2, boolean z) {
        if (processStateRecord.containsCycle()) {
            processRecord.mState.setContainsCycle(true);
            this.mProcessesInCycle.add(processRecord);
            if (processStateRecord.getCompletedAdjSeq() < this.mAdjSeq) {
                if (z) {
                    return processStateRecord.getCurRawProcState() >= i && processStateRecord.getCurRawAdj() >= i2;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected void reportOomAdjMessageLocked(java.lang.String str, java.lang.String str2) {
        android.util.Slog.d(str, str2);
        synchronized (this.mService.mOomAdjObserverLock) {
            try {
                if (this.mService.mCurOomAdjObserver != null) {
                    this.mService.mUiHandler.obtainMessage(70, str2).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onWakefulnessChanged(int i) {
        this.mCachedAppOptimizer.onWakefulnessChanged(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x024b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean applyOomAdjLSP(final com.android.server.am.ProcessRecord processRecord, boolean z, long j, long j2, int i) {
        boolean z2;
        int i2;
        boolean z3;
        long j3;
        long j4;
        int i3;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
        if (processStateRecord.getCurRawAdj() != processStateRecord.getSetRawAdj()) {
            processStateRecord.setSetRawAdj(processStateRecord.getCurRawAdj());
        }
        if (processStateRecord.getCurAdj() != processStateRecord.getSetAdj()) {
            this.mCachedAppOptimizer.onOomAdjustChanged(processStateRecord.getSetAdj(), processStateRecord.getCurAdj(), processRecord);
        }
        if (processStateRecord.getCurAdj() != processStateRecord.getSetAdj()) {
            com.android.server.am.ProcessList.setOomAdj(processRecord.getPid(), processRecord.uid, processStateRecord.getCurAdj());
            if (this.mService.mCurOomAdjUid == processRecord.info.uid) {
                reportOomAdjMessageLocked("ActivityManager", "Set " + processRecord.getPid() + " " + processRecord.processName + " adj " + processStateRecord.getCurAdj() + ": " + processStateRecord.getAdjType());
            }
            processStateRecord.setSetAdj(processStateRecord.getCurAdj());
            if (uidRecord != null) {
                uidRecord.noteProcAdjChanged();
            }
            processStateRecord.setVerifiedAdj(com.android.server.am.ProcessList.INVALID_ADJ);
        }
        int currentSchedulingGroup = processStateRecord.getCurrentSchedulingGroup();
        if (processStateRecord.getSetSchedGroup() != currentSchedulingGroup) {
            int setSchedGroup = processStateRecord.getSetSchedGroup();
            processStateRecord.setSetSchedGroup(currentSchedulingGroup);
            if (this.mService.mCurOomAdjUid == processRecord.uid) {
                reportOomAdjMessageLocked("ActivityManager", "Setting sched group of " + processRecord.processName + " to " + currentSchedulingGroup + ": " + processStateRecord.getAdjType());
            }
            if (processRecord.getWaitingToKill() != null && processRecord.mReceivers.numberOfCurReceivers() == 0 && android.app.ActivityManager.isProcStateBackground(processStateRecord.getSetProcState()) && !processStateRecord.hasStartedServices()) {
                processRecord.killLocked(processRecord.getWaitingToKill(), 10, 22, true);
                z2 = false;
                if (processStateRecord.hasRepForegroundActivities() != processStateRecord.hasForegroundActivities()) {
                    i2 = 0;
                } else {
                    processStateRecord.setRepForegroundActivities(processStateRecord.hasForegroundActivities());
                    i2 = 1;
                }
                updateAppFreezeStateLSP(processRecord, i, false);
                if (processStateRecord.getReportedProcState() != processStateRecord.getCurProcState()) {
                    processStateRecord.setReportedProcState(processStateRecord.getCurProcState());
                    if (processRecord.getThread() != null) {
                        try {
                            processRecord.getThread().setProcessState(processStateRecord.getReportedProcState());
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
                if (processStateRecord.getSetProcState() != 20 || com.android.server.am.ProcessList.procStatesDifferForMem(processStateRecord.getCurProcState(), processStateRecord.getSetProcState())) {
                    processStateRecord.setLastStateTime(j);
                    z3 = true;
                } else {
                    z3 = false;
                }
                synchronized (this.mService.mAppProfiler.mProfilerLock) {
                    try {
                        try {
                            processRecord.mProfile.updateProcState(processRecord.mState);
                            this.mService.mAppProfiler.updateNextPssTimeLPf(processStateRecord.getCurProcState(), processRecord.mProfile, j, z3);
                            if (processStateRecord.getSetProcState() != processStateRecord.getCurProcState()) {
                                if (this.mService.mCurOomAdjUid == processRecord.uid) {
                                    reportOomAdjMessageLocked("ActivityManager", "Proc state change of " + processRecord.processName + " to " + com.android.server.am.ProcessList.makeProcStateString(processStateRecord.getCurProcState()) + " (" + processStateRecord.getCurProcState() + "): " + processStateRecord.getAdjType());
                                }
                                boolean z4 = processStateRecord.getSetProcState() < 10;
                                boolean z5 = processStateRecord.getCurProcState() < 10;
                                if (z4 && !z5) {
                                    processStateRecord.setWhenUnimportant(j);
                                    processRecord.mProfile.mLastCpuTime.set(0L);
                                }
                                maybeUpdateUsageStatsLSP(processRecord, j2);
                                maybeUpdateLastTopTime(processStateRecord, j);
                                processStateRecord.setSetProcState(processStateRecord.getCurProcState());
                                if (processStateRecord.getSetProcState() >= 14) {
                                    processStateRecord.setNotCachedSinceIdle(false);
                                }
                                if (!z) {
                                    synchronized (this.mService.mProcessStats.mLock) {
                                        this.mService.setProcessTrackerStateLOSP(processRecord, this.mService.mProcessStats.getMemFactorLocked());
                                    }
                                } else {
                                    processStateRecord.setProcStateChanged(true);
                                }
                            } else if (processStateRecord.hasReportedInteraction()) {
                                if (processStateRecord.getCachedCompatChange(2)) {
                                    j4 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_POST_S;
                                } else {
                                    j4 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_PRE_S;
                                }
                                if (j2 - processStateRecord.getInteractionEventTime() > j4) {
                                    maybeUpdateUsageStatsLSP(processRecord, j2);
                                }
                            } else {
                                if (processStateRecord.getCachedCompatChange(2)) {
                                    j3 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_POST_S;
                                } else {
                                    j3 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_PRE_S;
                                }
                                if (j2 - processStateRecord.getFgInteractionTime() > j3) {
                                    maybeUpdateUsageStatsLSP(processRecord, j2);
                                }
                            }
                            if (processStateRecord.getCurCapability() != processStateRecord.getSetCapability()) {
                                processStateRecord.setSetCapability(processStateRecord.getCurCapability());
                            }
                            boolean isCurBoundByNonBgRestrictedApp = processStateRecord.isCurBoundByNonBgRestrictedApp();
                            if (isCurBoundByNonBgRestrictedApp != processStateRecord.isSetBoundByNonBgRestrictedApp()) {
                                processStateRecord.setSetBoundByNonBgRestrictedApp(isCurBoundByNonBgRestrictedApp);
                                if (!isCurBoundByNonBgRestrictedApp && processStateRecord.isBackgroundRestricted()) {
                                    this.mService.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.OomAdjuster$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            com.android.server.am.OomAdjuster.this.lambda$applyOomAdjLSP$1(processRecord);
                                        }
                                    });
                                }
                            }
                            if (i2 != 0) {
                                com.android.server.am.ActivityManagerService.ProcessChangeItem enqueueProcessChangeItemLocked = this.mProcessList.enqueueProcessChangeItemLocked(processRecord.getPid(), processRecord.info.uid);
                                enqueueProcessChangeItemLocked.changes |= i2;
                                enqueueProcessChangeItemLocked.foregroundActivities = processStateRecord.hasRepForegroundActivities();
                            }
                            if (processStateRecord.isCached() && !processStateRecord.shouldNotKillOnBgRestrictedAndIdle() && (!processStateRecord.isSetCached() || processStateRecord.isSetNoKillOnBgRestrictedAndIdle())) {
                                processStateRecord.setLastCanKillOnBgRestrictedAndIdleTime(j2);
                                if (this.mService.mDeterministicUidIdle || !this.mService.mHandler.hasMessages(58)) {
                                    this.mService.mHandler.sendEmptyMessageDelayed(58, this.mConstants.mKillBgRestrictedAndCachedIdleSettleTimeMs);
                                }
                            }
                            processStateRecord.setSetCached(processStateRecord.isCached());
                            processStateRecord.setSetNoKillOnBgRestrictedAndIdle(processStateRecord.shouldNotKillOnBgRestrictedAndIdle());
                            return z2;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            throw th;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
            }
            switch (currentSchedulingGroup) {
                case 0:
                    i3 = 0;
                    break;
                case 1:
                    i3 = 7;
                    break;
                case 2:
                default:
                    i3 = -1;
                    break;
                case 3:
                case 4:
                    i3 = 5;
                    break;
            }
            this.mProcessGroupHandler.sendMessage(this.mProcessGroupHandler.obtainMessage(0, processRecord.getPid(), i3, processRecord.processName));
            try {
                int renderThreadTid = processRecord.getRenderThreadTid();
                if (currentSchedulingGroup == 3) {
                    if (setSchedGroup != 3) {
                        processRecord.getWindowProcessController().onTopProcChanged();
                        if (this.mService.mUseFifoUiScheduling) {
                            processStateRecord.setSavedPriority(android.os.Process.getThreadPriority(processRecord.getPid()));
                            com.android.server.am.ActivityManagerService.scheduleAsFifoPriority(processRecord.getPid(), true);
                            if (renderThreadTid != 0) {
                                com.android.server.am.ActivityManagerService.scheduleAsFifoPriority(renderThreadTid, true);
                            }
                        } else {
                            android.os.Process.setThreadPriority(processRecord.getPid(), -10);
                            if (renderThreadTid != 0) {
                                try {
                                    android.os.Process.setThreadPriority(renderThreadTid, -10);
                                } catch (java.lang.IllegalArgumentException e2) {
                                }
                            }
                        }
                    }
                } else if (setSchedGroup == 3 && currentSchedulingGroup != 3) {
                    processRecord.getWindowProcessController().onTopProcChanged();
                    if (this.mService.mUseFifoUiScheduling) {
                        try {
                            android.os.Process.setThreadScheduler(processRecord.getPid(), 0, 0);
                            android.os.Process.setThreadPriority(processRecord.getPid(), processStateRecord.getSavedPriority());
                            if (renderThreadTid != 0) {
                                android.os.Process.setThreadScheduler(renderThreadTid, 0, 0);
                            }
                        } catch (java.lang.IllegalArgumentException e3) {
                            android.util.Slog.w(TAG, "Failed to set scheduling policy, thread does not exist:\n" + e3);
                        } catch (java.lang.SecurityException e4) {
                            android.util.Slog.w(TAG, "Failed to set scheduling policy, not allowed:\n" + e4);
                        }
                    } else {
                        android.os.Process.setThreadPriority(processRecord.getPid(), 0);
                    }
                    if (renderThreadTid != 0) {
                        android.os.Process.setThreadPriority(renderThreadTid, -4);
                    }
                }
            } catch (java.lang.Exception e5) {
            }
        }
        z2 = true;
        if (processStateRecord.hasRepForegroundActivities() != processStateRecord.hasForegroundActivities()) {
        }
        updateAppFreezeStateLSP(processRecord, i, false);
        if (processStateRecord.getReportedProcState() != processStateRecord.getCurProcState()) {
        }
        if (processStateRecord.getSetProcState() != 20) {
        }
        processStateRecord.setLastStateTime(j);
        z3 = true;
        synchronized (this.mService.mAppProfiler.mProfilerLock) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyOomAdjLSP$1(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mServices.stopAllForegroundServicesLocked(processRecord.uid, processRecord.info.packageName);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAttachingProcessStatesLSP(com.android.server.am.ProcessRecord processRecord) {
        int i;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        int curRawProcState = processStateRecord.getCurRawProcState();
        int curRawAdj = processStateRecord.getCurRawAdj();
        int i2 = 2;
        int i3 = 19;
        if (processStateRecord.hasForegroundActivities()) {
            try {
                processRecord.getWindowProcessController().onTopProcChanged();
                if (this.mService.mUseFifoUiScheduling) {
                    com.android.server.am.ActivityManagerService.scheduleAsFifoPriority(processRecord.getPid(), true);
                } else {
                    android.os.Process.setThreadPriority(processRecord.getPid(), -10);
                }
                if (isScreenOnOrAnimatingLocked(processStateRecord)) {
                    i3 = 2;
                    i2 = 3;
                }
                i = 127;
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Failed to pre-set top priority to " + processRecord + " " + e);
            }
            processStateRecord.setCurrentSchedulingGroup(i2);
            processStateRecord.setCurProcState(i3);
            processStateRecord.setCurRawProcState(i3);
            processStateRecord.setCurCapability(i);
            processStateRecord.setCurAdj(0);
            processStateRecord.setCurRawAdj(0);
            processStateRecord.setForcingToImportant(null);
            processStateRecord.setHasShownUi(false);
            onProcessStateChanged(processRecord, curRawProcState);
            onProcessOomAdjChanged(processRecord, curRawAdj);
        }
        i = 0;
        processStateRecord.setCurrentSchedulingGroup(i2);
        processStateRecord.setCurProcState(i3);
        processStateRecord.setCurRawProcState(i3);
        processStateRecord.setCurCapability(i);
        processStateRecord.setCurAdj(0);
        processStateRecord.setCurRawAdj(0);
        processStateRecord.setForcingToImportant(null);
        processStateRecord.setHasShownUi(false);
        onProcessStateChanged(processRecord, curRawProcState);
        onProcessOomAdjChanged(processRecord, curRawAdj);
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeUpdateUsageStats(com.android.server.am.ProcessRecord processRecord, long j) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        maybeUpdateUsageStatsLSP(processRecord, j);
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void maybeUpdateUsageStatsLSP(com.android.server.am.ProcessRecord processRecord, long j) {
        long j2;
        long j3;
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if (this.mService.mUsageStatsService == null) {
            return;
        }
        boolean cachedCompatChange = processStateRecord.getCachedCompatChange(2);
        if (android.app.ActivityManager.isProcStateConsideredInteraction(processStateRecord.getCurProcState())) {
            processStateRecord.setFgInteractionTime(0L);
        } else if (processStateRecord.getCurProcState() > 4) {
            r7 = processStateRecord.getCurProcState() <= 6;
            processStateRecord.setFgInteractionTime(0L);
        } else if (processStateRecord.getFgInteractionTime() == 0) {
            processStateRecord.setFgInteractionTime(j);
            r7 = false;
        } else {
            if (cachedCompatChange) {
                j2 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_POST_S;
            } else {
                j2 = this.mConstants.SERVICE_USAGE_INTERACTION_TIME_PRE_S;
            }
            if (j <= processStateRecord.getFgInteractionTime() + j2) {
                r7 = false;
            }
        }
        if (cachedCompatChange) {
            j3 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_POST_S;
        } else {
            j3 = this.mConstants.USAGE_STATS_INTERACTION_INTERVAL_PRE_S;
        }
        if (r7 && (!processStateRecord.hasReportedInteraction() || j - processStateRecord.getInteractionEventTime() > j3)) {
            processStateRecord.setInteractionEventTime(j);
            java.lang.String[] packageList = processRecord.getPackageList();
            if (packageList != null) {
                for (java.lang.String str : packageList) {
                    this.mService.mUsageStatsService.reportEvent(str, processRecord.userId, 6);
                }
            }
        }
        processStateRecord.setReportedInteraction(r7);
        if (!r7) {
            processStateRecord.setInteractionEventTime(0L);
        }
    }

    private void maybeUpdateLastTopTime(com.android.server.am.ProcessStateRecord processStateRecord, long j) {
        if (processStateRecord.getSetProcState() <= 2 && processStateRecord.getCurProcState() > 2) {
            processStateRecord.setLastTopTime(j);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void idleUidsLocked() {
        int size = this.mActiveUids.size();
        this.mService.mHandler.removeMessages(58);
        if (size <= 0) {
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = elapsedRealtime - this.mConstants.BACKGROUND_SETTLE_TIME;
        if (this.mService.mLocalPowerManager != null) {
            this.mService.mLocalPowerManager.startUidChanges();
        }
        long j2 = 0;
        for (int i = size - 1; i >= 0; i--) {
            com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i);
            long lastBackgroundTime = valueAt.getLastBackgroundTime();
            long lastIdleTime = valueAt.getLastIdleTime();
            if (lastBackgroundTime > 0 && (!valueAt.isIdle() || lastIdleTime == 0)) {
                if (lastBackgroundTime <= j) {
                    com.android.server.am.EventLogTags.writeAmUidIdle(valueAt.getUid());
                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerGlobalLock) {
                        try {
                            valueAt.setIdle(true);
                            valueAt.setSetIdle(true);
                            valueAt.setLastIdleTime(elapsedRealtime);
                        } catch (java.lang.Throwable th) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    this.mService.doStopUidLocked(valueAt.getUid(), valueAt);
                } else if (j2 == 0 || j2 > lastBackgroundTime) {
                    j2 = lastBackgroundTime;
                }
            }
        }
        if (this.mService.mLocalPowerManager != null) {
            this.mService.mLocalPowerManager.finishUidChanges();
        }
        if (this.mService.mConstants.mKillBgRestrictedAndCachedIdle) {
            android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet = this.mProcessList.mAppsInBackgroundRestricted;
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long lambda$killAppIfBgRestrictedAndCachedIdleLocked$5 = this.mProcessList.lambda$killAppIfBgRestrictedAndCachedIdleLocked$5(arraySet.valueAt(i2), elapsedRealtime) - this.mConstants.BACKGROUND_SETTLE_TIME;
                if (lambda$killAppIfBgRestrictedAndCachedIdleLocked$5 > 0 && (j2 == 0 || j2 > lambda$killAppIfBgRestrictedAndCachedIdleLocked$5)) {
                    j2 = lambda$killAppIfBgRestrictedAndCachedIdleLocked$5;
                }
            }
        }
        if (j2 > 0) {
            this.mService.mHandler.sendEmptyMessageDelayed(58, (j2 + this.mConstants.BACKGROUND_SETTLE_TIME) - elapsedRealtime);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAppIdTempAllowlistStateLSP(int i, boolean z) {
        boolean z2 = false;
        for (int size = this.mActiveUids.size() - 1; size >= 0; size--) {
            com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(size);
            if (valueAt.getUid() == i && valueAt.isCurAllowListed() != z) {
                valueAt.setCurAllowListed(z);
                z2 = true;
            }
        }
        if (z2) {
            updateOomAdjLSP(10);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setUidTempAllowlistStateLSP(int i, boolean z) {
        com.android.server.am.UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord != null && uidRecord.isCurAllowListed() != z) {
            uidRecord.setCurAllowListed(z);
            updateOomAdjLSP(10);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpProcessListVariablesLocked(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464305L, this.mAdjSeq);
        protoOutputStream.write(1120986464306L, this.mProcessList.getLruSeqLOSP());
        protoOutputStream.write(1120986464307L, this.mNumNonCachedProcs);
        protoOutputStream.write(1120986464309L, this.mNumServiceProcs);
        protoOutputStream.write(1120986464310L, this.mNewNumServiceProcs);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpSequenceNumbersLocked(java.io.PrintWriter printWriter) {
        printWriter.println("  mAdjSeq=" + this.mAdjSeq + " mLruSeq=" + this.mProcessList.getLruSeqLOSP());
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpProcCountsLocked(java.io.PrintWriter printWriter) {
        printWriter.println("  mNumNonCachedProcs=" + this.mNumNonCachedProcs + " (" + this.mProcessList.getLruSizeLOSP() + " total) mNumCachedHiddenProcs=" + this.mNumCachedHiddenProcs + " mNumServiceProcs=" + this.mNumServiceProcs + " mNewNumServiceProcs=" + this.mNewNumServiceProcs);
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void dumpCachedAppOptimizerSettings(java.io.PrintWriter printWriter) {
        this.mCachedAppOptimizer.dump(printWriter);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpCacheOomRankerSettings(java.io.PrintWriter printWriter) {
        this.mCacheOomRanker.dump(printWriter);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void updateAppFreezeStateLSP(com.android.server.am.ProcessRecord processRecord, int i, boolean z) {
        if (!this.mCachedAppOptimizer.useFreezer() || processRecord.mOptRecord.isFreezeExempt()) {
            return;
        }
        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (processCachedOptimizerRecord.isFrozen() && processCachedOptimizerRecord.shouldNotFreeze()) {
            this.mCachedAppOptimizer.unfreezeAppLSP(processRecord, com.android.server.am.CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i));
            return;
        }
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if (processStateRecord.getCurAdj() >= 900 && !processCachedOptimizerRecord.isFrozen() && !processCachedOptimizerRecord.shouldNotFreeze()) {
            if (!z) {
                this.mCachedAppOptimizer.freezeAppAsyncLSP(processRecord);
                return;
            } else {
                this.mCachedAppOptimizer.freezeAppAsyncAtEarliestLSP(processRecord);
                return;
            }
        }
        if (processStateRecord.getSetAdj() < 900) {
            this.mCachedAppOptimizer.unfreezeAppLSP(processRecord, com.android.server.am.CachedAppOptimizer.getUnfreezeReasonCodeFromOomAdjReason(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void unfreezeTemporarily(com.android.server.am.ProcessRecord processRecord, int i) {
        if (!this.mCachedAppOptimizer.useFreezer()) {
            return;
        }
        com.android.server.am.ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord.mOptRecord;
        if (!processCachedOptimizerRecord.isFrozen() && !processCachedOptimizerRecord.isPendingFreeze()) {
            return;
        }
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mTmpProcessList;
        com.android.server.am.ActiveUids activeUids = this.mTmpUidRecords;
        this.mTmpProcessSet.add(processRecord);
        collectReachableProcessesLocked(this.mTmpProcessSet, arrayList, activeUids);
        this.mTmpProcessSet.clear();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mCachedAppOptimizer.unfreezeTemporarily(arrayList.get(i2), i);
        }
        arrayList.clear();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessEndLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessStateChanged(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i) {
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessOomAdjChanged(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i) {
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetInternal() {
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialAdj(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return processRecord.mState.getCurAdj();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialProcState(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return processRecord.mState.getCurProcState();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialCapability(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return processRecord.mState.getCurCapability();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    protected boolean getInitialIsCurBoundByNonBgRestrictedApp(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return processRecord.mState.isCurBoundByNonBgRestrictedApp();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean evaluateServiceConnectionAdd(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, com.android.server.am.ConnectionRecord connectionRecord) {
        if (evaluateConnectionPrelude(processRecord, processRecord2)) {
            return true;
        }
        if (processRecord2.getSetAdj() <= processRecord.getSetAdj() && processRecord2.getSetProcState() <= processRecord.getSetProcState() && ((processRecord2.getSetCapability() & processRecord.getSetCapability()) == processRecord.getSetCapability() || connectionRecord.notHasFlag(4294971392L))) {
            return false;
        }
        return computeServiceHostOomAdjLSP(connectionRecord, processRecord2, processRecord, android.os.SystemClock.uptimeMillis(), this.mService.getTopApp(), false, false, false, 0, com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ, false, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean evaluateServiceConnectionRemoval(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, com.android.server.am.ConnectionRecord connectionRecord) {
        if (!evaluateConnectionPrelude(processRecord, processRecord2) && processRecord2.getSetAdj() < processRecord.getSetAdj() && processRecord2.getSetProcState() < processRecord.getSetProcState()) {
            if ((processRecord.getSetCapability() & processRecord2.getSetCapability()) == 0 || connectionRecord.notHasFlag(4294971392L)) {
                return false;
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean evaluateProviderConnectionAdd(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2) {
        if (evaluateConnectionPrelude(processRecord, processRecord2)) {
            return true;
        }
        if (processRecord2.getSetAdj() <= processRecord.getSetAdj() && processRecord2.getSetProcState() <= processRecord.getSetProcState()) {
            return false;
        }
        return computeProviderHostOomAdjLSP(null, processRecord2, processRecord, android.os.SystemClock.uptimeMillis(), this.mService.getTopApp(), false, false, false, 0, com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ, false, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean evaluateProviderConnectionRemoval(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2) {
        return evaluateConnectionPrelude(processRecord, processRecord2) || processRecord2.getSetAdj() >= processRecord.getSetAdj() || processRecord2.getSetProcState() >= processRecord.getSetProcState();
    }

    private boolean evaluateConnectionPrelude(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2) {
        if (processRecord == null || processRecord2 == null || processRecord2.isSdkSandbox || processRecord2.isolated || processRecord2.isKilledByAm() || processRecord2.isKilled()) {
            return true;
        }
        return false;
    }
}
