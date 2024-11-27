package com.android.server.am;

/* loaded from: classes.dex */
public final class AppExitInfoTracker {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String APP_EXIT_INFO_FILE = "procexitinfo";
    private static final long APP_EXIT_INFO_PERSIST_INTERVAL = java.util.concurrent.TimeUnit.MINUTES.toMillis(30);
    private static final long APP_EXIT_INFO_STATSD_LOG_DEBOUNCE = java.util.concurrent.TimeUnit.SECONDS.toMillis(15);
    private static final int APP_EXIT_RAW_INFO_POOL_SIZE = 8;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String APP_EXIT_STORE_DIR = "procexitstore";
    private static final java.lang.String APP_TRACE_FILE_SUFFIX = ".gz";
    private static final int FOREACH_ACTION_NONE = 0;
    private static final int FOREACH_ACTION_REMOVE_ITEM = 1;
    private static final int FOREACH_ACTION_STOP_ITERATION = 2;
    private static final java.lang.String TAG = "ActivityManager";
    private int mAppExitInfoHistoryListSize;
    private com.android.server.am.AppExitInfoTracker.KillHandler mKillHandler;

    @com.android.internal.annotations.VisibleForTesting
    java.io.File mProcExitInfoFile;

    @com.android.internal.annotations.VisibleForTesting
    java.io.File mProcExitStoreDir;
    private com.android.server.am.ActivityManagerService mService;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Runnable mAppExitInfoPersistTask = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAppExitInfoPersistTimestamp = 0;

    @com.android.internal.annotations.VisibleForTesting
    java.util.concurrent.atomic.AtomicBoolean mAppExitInfoLoaded = new java.util.concurrent.atomic.AtomicBoolean();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.ArrayList<android.app.ApplicationExitInfo> mTmpInfoList = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.ArrayList<android.app.ApplicationExitInfo> mTmpInfoList2 = new java.util.ArrayList<>();
    final com.android.server.am.AppExitInfoTracker.IsolatedUidRecords mIsolatedUidRecords = new com.android.server.am.AppExitInfoTracker.IsolatedUidRecords();
    final com.android.server.am.AppExitInfoTracker.AppExitInfoExternalSource mAppExitInfoSourceZygote = new com.android.server.am.AppExitInfoTracker.AppExitInfoExternalSource("zygote", null);
    final com.android.server.am.AppExitInfoTracker.AppExitInfoExternalSource mAppExitInfoSourceLmkd = new com.android.server.am.AppExitInfoTracker.AppExitInfoExternalSource("lmkd", 3);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<android.util.SparseArray<byte[]>> mActiveAppStateSummary = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<android.util.SparseArray<java.io.File>> mActiveAppTraces = new android.util.SparseArray<>();
    final com.android.server.am.AppExitInfoTracker.AppTraceRetriever mAppTraceRetriever = new com.android.server.am.AppExitInfoTracker.AppTraceRetriever();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.internal.app.ProcessMap<com.android.server.am.AppExitInfoTracker.AppExitInfoContainer> mData = new com.android.internal.app.ProcessMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.Pools.SynchronizedPool<android.app.ApplicationExitInfo> mRawRecordsPool = new android.util.Pools.SynchronizedPool<>(8);

    interface LmkdKillListener {
        void onLmkdKillOccurred(int i, int i2);
    }

    AppExitInfoTracker() {
    }

    void init(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread("ActivityManager:killHandler", 10, true);
        serviceThread.start();
        this.mKillHandler = new com.android.server.am.AppExitInfoTracker.KillHandler(serviceThread.getLooper());
        this.mProcExitStoreDir = new java.io.File(com.android.server.SystemServiceManager.ensureSystemDir(), APP_EXIT_STORE_DIR);
        if (!android.os.FileUtils.createDir(this.mProcExitStoreDir)) {
            android.util.Slog.e(TAG, "Unable to create " + this.mProcExitStoreDir);
            return;
        }
        this.mProcExitInfoFile = new java.io.File(this.mProcExitStoreDir, APP_EXIT_INFO_FILE);
        this.mAppExitInfoHistoryListSize = activityManagerService.mContext.getResources().getInteger(android.R.integer.config_app_exit_info_history_list_size);
    }

    void onSystemReady() {
        registerForUserRemoval();
        registerForPackageRemoval();
        com.android.server.IoThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppExitInfoTracker.this.lambda$onSystemReady$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0() {
        android.os.SystemProperties.set("persist.sys.lmk.reportkills", java.lang.Boolean.toString(android.os.SystemProperties.getBoolean("sys.lmk.reportkills", false)));
        loadExistingProcessExitInfo();
    }

    void scheduleNoteProcessDied(com.android.server.am.ProcessRecord processRecord) {
        if (processRecord == null || processRecord.info == null || !this.mAppExitInfoLoaded.get()) {
            return;
        }
        this.mKillHandler.obtainMessage(4103, obtainRawRecord(processRecord, java.lang.System.currentTimeMillis())).sendToTarget();
    }

    void scheduleNoteAppKill(com.android.server.am.ProcessRecord processRecord, int i, int i2, java.lang.String str) {
        if (!this.mAppExitInfoLoaded.get() || processRecord == null || processRecord.info == null) {
            return;
        }
        android.app.ApplicationExitInfo obtainRawRecord = obtainRawRecord(processRecord, java.lang.System.currentTimeMillis());
        obtainRawRecord.setReason(i);
        obtainRawRecord.setSubReason(i2);
        obtainRawRecord.setDescription(str);
        this.mKillHandler.obtainMessage(4104, obtainRawRecord).sendToTarget();
    }

    void scheduleNoteAppRecoverableCrash(com.android.server.am.ProcessRecord processRecord) {
        if (!this.mAppExitInfoLoaded.get() || processRecord == null || processRecord.info == null) {
            return;
        }
        android.app.ApplicationExitInfo obtainRawRecord = obtainRawRecord(processRecord, java.lang.System.currentTimeMillis());
        obtainRawRecord.setReason(5);
        obtainRawRecord.setSubReason(0);
        obtainRawRecord.setDescription("recoverable_crash");
        this.mKillHandler.obtainMessage(4106, obtainRawRecord).sendToTarget();
    }

    void scheduleNoteAppKill(int i, int i2, int i3, int i4, java.lang.String str) {
        com.android.server.am.ProcessRecord processRecord;
        if (!this.mAppExitInfoLoaded.get()) {
            return;
        }
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord = this.mService.mPidsSelfLocked.get(i);
        }
        if (processRecord != null) {
            scheduleNoteAppKill(processRecord, i3, i4, str);
        }
    }

    void setLmkdKillListener(final com.android.server.am.AppExitInfoTracker.LmkdKillListener lmkdKillListener) {
        synchronized (this.mLock) {
            this.mAppExitInfoSourceLmkd.setOnProcDiedListener(new java.util.function.BiConsumer() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.am.AppExitInfoTracker.lambda$setLmkdKillListener$1(com.android.server.am.AppExitInfoTracker.LmkdKillListener.this, (java.lang.Integer) obj, (java.lang.Integer) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setLmkdKillListener$1(com.android.server.am.AppExitInfoTracker.LmkdKillListener lmkdKillListener, java.lang.Integer num, java.lang.Integer num2) {
        lmkdKillListener.onLmkdKillOccurred(num.intValue(), num2.intValue());
    }

    void scheduleNoteLmkdProcKilled(int i, int i2) {
        this.mKillHandler.obtainMessage(4101, i, i2).sendToTarget();
    }

    private void scheduleChildProcDied(int i, int i2, int i3) {
        this.mKillHandler.obtainMessage(4102, i, i2, java.lang.Integer.valueOf(i3)).sendToTarget();
    }

    void handleZygoteSigChld(int i, int i2, int i3) {
        scheduleChildProcDied(i, i2, i3);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void handleNoteProcessDiedLocked(android.app.ApplicationExitInfo applicationExitInfo) {
        if (applicationExitInfo != null) {
            android.app.ApplicationExitInfo exitInfoLocked = getExitInfoLocked(applicationExitInfo.getPackageName(), applicationExitInfo.getPackageUid(), applicationExitInfo.getPid());
            android.util.Pair<java.lang.Long, java.lang.Object> remove = this.mAppExitInfoSourceZygote.remove(applicationExitInfo.getPid(), applicationExitInfo.getRealUid());
            android.util.Pair<java.lang.Long, java.lang.Object> remove2 = this.mAppExitInfoSourceLmkd.remove(applicationExitInfo.getPid(), applicationExitInfo.getRealUid());
            this.mIsolatedUidRecords.removeIsolatedUidLocked(applicationExitInfo.getRealUid());
            if (exitInfoLocked == null) {
                exitInfoLocked = addExitInfoLocked(applicationExitInfo);
            }
            if (remove2 != null) {
                updateExistingExitInfoRecordLocked(exitInfoLocked, null, 3);
            } else if (remove != null) {
                updateExistingExitInfoRecordLocked(exitInfoLocked, (java.lang.Integer) remove.second, null);
            } else {
                scheduleLogToStatsdLocked(exitInfoLocked, false);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void handleNoteAppKillLocked(android.app.ApplicationExitInfo applicationExitInfo) {
        android.app.ApplicationExitInfo exitInfoLocked = getExitInfoLocked(applicationExitInfo.getPackageName(), applicationExitInfo.getPackageUid(), applicationExitInfo.getPid());
        if (exitInfoLocked == null) {
            exitInfoLocked = addExitInfoLocked(applicationExitInfo);
        } else {
            exitInfoLocked.setReason(applicationExitInfo.getReason());
            exitInfoLocked.setSubReason(applicationExitInfo.getSubReason());
            exitInfoLocked.setStatus(0);
            exitInfoLocked.setTimestamp(java.lang.System.currentTimeMillis());
            exitInfoLocked.setDescription(applicationExitInfo.getDescription());
        }
        scheduleLogToStatsdLocked(exitInfoLocked, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void handleNoteAppRecoverableCrashLocked(android.app.ApplicationExitInfo applicationExitInfo) {
        addExitInfoLocked(applicationExitInfo, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.ApplicationExitInfo addExitInfoLocked(android.app.ApplicationExitInfo applicationExitInfo) {
        return addExitInfoLocked(applicationExitInfo, false);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.ApplicationExitInfo addExitInfoLocked(android.app.ApplicationExitInfo applicationExitInfo, boolean z) {
        java.lang.Integer uidByIsolatedUid;
        if (!this.mAppExitInfoLoaded.get()) {
            android.util.Slog.w(TAG, "Skipping saving the exit info due to ongoing loading from storage");
            return null;
        }
        android.app.ApplicationExitInfo applicationExitInfo2 = new android.app.ApplicationExitInfo(applicationExitInfo);
        java.lang.String[] packageList = applicationExitInfo.getPackageList();
        int realUid = applicationExitInfo.getRealUid();
        if (android.os.UserHandle.isIsolated(realUid) && (uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(realUid)) != null) {
            realUid = uidByIsolatedUid.intValue();
        }
        for (java.lang.String str : packageList) {
            addExitInfoInnerLocked(str, realUid, applicationExitInfo2, z);
        }
        if (android.os.Process.isSdkSandboxUid(realUid)) {
            for (java.lang.String str2 : packageList) {
                addExitInfoInnerLocked(str2, applicationExitInfo.getPackageUid(), applicationExitInfo2, z);
            }
        }
        schedulePersistProcessExitInfo(false);
        return applicationExitInfo2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x006f, code lost:
    
        if (r6.intValue() == 3) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0063  */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateExistingExitInfoRecordLocked(android.app.ApplicationExitInfo applicationExitInfo, java.lang.Integer num, java.lang.Integer num2) {
        boolean z;
        if (applicationExitInfo == null || !isFresh(applicationExitInfo.getTimestamp())) {
            return;
        }
        boolean z2 = true;
        if (num != null) {
            if (android.system.OsConstants.WIFEXITED(num.intValue())) {
                applicationExitInfo.setReason(1);
                applicationExitInfo.setStatus(android.system.OsConstants.WEXITSTATUS(num.intValue()));
                z = true;
            } else if (android.system.OsConstants.WIFSIGNALED(num.intValue())) {
                if (applicationExitInfo.getReason() == 0) {
                    applicationExitInfo.setReason(2);
                    applicationExitInfo.setStatus(android.system.OsConstants.WTERMSIG(num.intValue()));
                } else if (applicationExitInfo.getReason() == 5) {
                    applicationExitInfo.setStatus(android.system.OsConstants.WTERMSIG(num.intValue()));
                    z = true;
                }
            }
            if (num2 != null) {
                applicationExitInfo.setReason(num2.intValue());
            }
            z2 = z;
            scheduleLogToStatsdLocked(applicationExitInfo, z2);
        }
        z = false;
        if (num2 != null) {
        }
        z2 = z;
        scheduleLogToStatsdLocked(applicationExitInfo, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean updateExitInfoIfNecessaryLocked(final int i, int i2, final java.lang.Integer num, final java.lang.Integer num2) {
        final int i3;
        java.lang.Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
        if (uidByIsolatedUid == null) {
            i3 = i2;
        } else {
            i3 = uidByIsolatedUid.intValue();
        }
        final java.util.ArrayList<android.app.ApplicationExitInfo> arrayList = this.mTmpInfoList;
        arrayList.clear();
        forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda18
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Integer lambda$updateExitInfoIfNecessaryLocked$2;
                lambda$updateExitInfoIfNecessaryLocked$2 = com.android.server.am.AppExitInfoTracker.this.lambda$updateExitInfoIfNecessaryLocked$2(i3, arrayList, i, num, num2, (java.lang.String) obj, (android.util.SparseArray) obj2);
                return lambda$updateExitInfoIfNecessaryLocked$2;
            }
        });
        return arrayList.size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$updateExitInfoIfNecessaryLocked$2(int i, java.util.ArrayList arrayList, int i2, java.lang.Integer num, java.lang.Integer num2, java.lang.String str, android.util.SparseArray sparseArray) {
        com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.get(i);
        if (appExitInfoContainer == null) {
            return 0;
        }
        arrayList.clear();
        appExitInfoContainer.getExitInfoLocked(i2, 1, arrayList);
        if (arrayList.size() == 0) {
            return 0;
        }
        android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) arrayList.get(0);
        if (applicationExitInfo.getRealUid() != i) {
            arrayList.clear();
            return 0;
        }
        updateExistingExitInfoRecordLocked(applicationExitInfo, num, num2);
        return 2;
    }

    @com.android.internal.annotations.VisibleForTesting
    void getExitInfo(java.lang.String str, final int i, final int i2, int i3, java.util.ArrayList<android.app.ApplicationExitInfo> arrayList) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    if (!android.text.TextUtils.isEmpty(str)) {
                        com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) this.mData.get(str, i);
                        if (appExitInfoContainer != null) {
                            appExitInfoContainer.getExitInfoLocked(i2, i3, arrayList);
                        }
                    } else {
                        final java.util.ArrayList<android.app.ApplicationExitInfo> arrayList2 = this.mTmpInfoList2;
                        arrayList2.clear();
                        forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiFunction
                            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                                java.lang.Integer lambda$getExitInfo$3;
                                lambda$getExitInfo$3 = com.android.server.am.AppExitInfoTracker.this.lambda$getExitInfo$3(i, arrayList2, i2, (java.lang.String) obj, (android.util.SparseArray) obj2);
                                return lambda$getExitInfo$3;
                            }
                        });
                        java.util.Collections.sort(arrayList2, new java.util.Comparator() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda3
                            @Override // java.util.Comparator
                            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                                int lambda$getExitInfo$4;
                                lambda$getExitInfo$4 = com.android.server.am.AppExitInfoTracker.lambda$getExitInfo$4((android.app.ApplicationExitInfo) obj, (android.app.ApplicationExitInfo) obj2);
                                return lambda$getExitInfo$4;
                            }
                        });
                        int size = arrayList2.size();
                        if (i3 > 0) {
                            size = java.lang.Math.min(size, i3);
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            arrayList.add(arrayList2.get(i4));
                        }
                        arrayList2.clear();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getExitInfo$3(int i, java.util.ArrayList arrayList, int i2, java.lang.String str, android.util.SparseArray sparseArray) {
        com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.get(i);
        if (appExitInfoContainer != null) {
            this.mTmpInfoList.clear();
            arrayList.addAll(appExitInfoContainer.toListLocked(this.mTmpInfoList, i2));
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getExitInfo$4(android.app.ApplicationExitInfo applicationExitInfo, android.app.ApplicationExitInfo applicationExitInfo2) {
        return java.lang.Long.compare(applicationExitInfo2.getTimestamp(), applicationExitInfo.getTimestamp());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.app.ApplicationExitInfo getExitInfoLocked(java.lang.String str, int i, int i2) {
        java.util.ArrayList<android.app.ApplicationExitInfo> arrayList = this.mTmpInfoList;
        arrayList.clear();
        getExitInfo(str, i, i2, 1, arrayList);
        android.app.ApplicationExitInfo applicationExitInfo = arrayList.size() > 0 ? arrayList.get(0) : null;
        arrayList.clear();
        return applicationExitInfo;
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserRemoved(int i) {
        this.mAppExitInfoSourceZygote.removeByUserId(i);
        this.mAppExitInfoSourceLmkd.removeByUserId(i);
        this.mIsolatedUidRecords.removeByUserId(i);
        synchronized (this.mLock) {
            removeByUserIdLocked(i);
            schedulePersistProcessExitInfo(true);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onPackageRemoved(java.lang.String str, int i, boolean z) {
        if (str != null) {
            boolean isEmpty = android.text.TextUtils.isEmpty(this.mService.mPackageManagerInt.getNameForUid(i));
            synchronized (this.mLock) {
                if (isEmpty) {
                    try {
                        this.mAppExitInfoSourceZygote.removeByUidLocked(i, z);
                        this.mAppExitInfoSourceLmkd.removeByUidLocked(i, z);
                        this.mIsolatedUidRecords.removeAppUid(i, z);
                    } finally {
                    }
                }
                removePackageLocked(str, i, isEmpty, z ? -1 : android.os.UserHandle.getUserId(i));
                schedulePersistProcessExitInfo(true);
            }
        }
    }

    private void registerForUserRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mService.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppExitInfoTracker.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra < 1) {
                    return;
                }
                com.android.server.am.AppExitInfoTracker.this.onUserRemoved(intExtra);
            }
        }, intentFilter, null, this.mKillHandler);
    }

    private void registerForPackageRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mService.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppExitInfoTracker.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    return;
                }
                com.android.server.am.AppExitInfoTracker.this.onPackageRemoved(intent.getData().getSchemeSpecificPart(), intent.getIntExtra("android.intent.extra.UID", com.android.server.am.ProcessList.INVALID_ADJ), intent.getBooleanExtra("android.intent.extra.REMOVED_FOR_ALL_USERS", false));
            }
        }, intentFilter, null, this.mKillHandler);
    }

    @com.android.internal.annotations.VisibleForTesting
    void loadExistingProcessExitInfo() {
        if (!this.mProcExitInfoFile.canRead()) {
            this.mAppExitInfoLoaded.set(true);
            return;
        }
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = new android.util.AtomicFile(this.mProcExitInfoFile).openRead();
                android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(fileInputStream);
                for (int nextField = protoInputStream.nextField(); nextField != -1; nextField = protoInputStream.nextField()) {
                    switch (nextField) {
                        case 1:
                            synchronized (this.mLock) {
                                this.mLastAppExitInfoPersistTimestamp = protoInputStream.readLong(1112396529665L);
                            }
                        case 2:
                            loadPackagesFromProto(protoInputStream, nextField);
                        default:
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Error in loading historical app exit info from persistent storage: " + e);
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            synchronized (this.mLock) {
                pruneAnrTracesIfNecessaryLocked();
                this.mAppExitInfoLoaded.set(true);
            }
        } catch (java.lang.Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (java.io.IOException e2) {
                }
            }
            throw th;
        }
    }

    private void loadPackagesFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException {
        long start = protoInputStream.start(j);
        java.lang.String str = "";
        int nextField = protoInputStream.nextField();
        while (nextField != -1) {
            switch (nextField) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = new com.android.server.am.AppExitInfoTracker.AppExitInfoContainer(this.mAppExitInfoHistoryListSize);
                    int readFromProto = appExitInfoContainer.readFromProto(protoInputStream, 2246267895810L);
                    synchronized (this.mLock) {
                        this.mData.put(str, readFromProto, appExitInfoContainer);
                    }
                    break;
            }
            nextField = protoInputStream.nextField();
        }
        protoInputStream.end(start);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void persistProcessExitInfo() {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mProcExitInfoFile);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            fileOutputStream = atomicFile.startWrite();
            try {
                final android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileOutputStream);
                protoOutputStream.write(1112396529665L, currentTimeMillis);
                synchronized (this.mLock) {
                    forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda12
                        @Override // java.util.function.BiFunction
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                            java.lang.Integer lambda$persistProcessExitInfo$5;
                            lambda$persistProcessExitInfo$5 = com.android.server.am.AppExitInfoTracker.lambda$persistProcessExitInfo$5(protoOutputStream, (java.lang.String) obj, (android.util.SparseArray) obj2);
                            return lambda$persistProcessExitInfo$5;
                        }
                    });
                    this.mLastAppExitInfoPersistTimestamp = currentTimeMillis;
                }
                protoOutputStream.flush();
                atomicFile.finishWrite(fileOutputStream);
            } catch (java.io.IOException e) {
                e = e;
                android.util.Slog.w(TAG, "Unable to write historical app exit info into persistent storage: " + e);
                atomicFile.failWrite(fileOutputStream);
                synchronized (this.mLock) {
                }
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
        synchronized (this.mLock) {
            this.mAppExitInfoPersistTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$persistProcessExitInfo$5(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, android.util.SparseArray sparseArray) {
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1138166333441L, str);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(i)).writeToProto(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start);
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    void schedulePersistProcessExitInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mAppExitInfoPersistTask == null || z) {
                    if (this.mAppExitInfoPersistTask != null) {
                        com.android.server.IoThread.getHandler().removeCallbacks(this.mAppExitInfoPersistTask);
                    }
                    this.mAppExitInfoPersistTask = new java.lang.Runnable() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.AppExitInfoTracker.this.persistProcessExitInfo();
                        }
                    };
                    com.android.server.IoThread.getHandler().postDelayed(this.mAppExitInfoPersistTask, z ? 0L : APP_EXIT_INFO_PERSIST_INTERVAL);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearProcessExitInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mAppExitInfoPersistTask != null) {
                    com.android.server.IoThread.getHandler().removeCallbacks(this.mAppExitInfoPersistTask);
                    this.mAppExitInfoPersistTask = null;
                }
                if (z && this.mProcExitInfoFile != null) {
                    this.mProcExitInfoFile.delete();
                }
                this.mData.getMap().clear();
                this.mActiveAppStateSummary.clear();
                this.mActiveAppTraces.clear();
                pruneAnrTracesIfNecessaryLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearHistoryProcessExitInfo(java.lang.String str, int i) {
        com.android.server.os.NativeTombstoneManager nativeTombstoneManager = (com.android.server.os.NativeTombstoneManager) com.android.server.LocalServices.getService(com.android.server.os.NativeTombstoneManager.class);
        java.util.Optional<java.lang.Integer> empty = java.util.Optional.empty();
        if (android.text.TextUtils.isEmpty(str)) {
            synchronized (this.mLock) {
                removeByUserIdLocked(i);
            }
        } else {
            int packageUid = this.mService.mPackageManagerInt.getPackageUid(str, 131072L, i);
            java.util.Optional<java.lang.Integer> of = java.util.Optional.of(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageUid)));
            synchronized (this.mLock) {
                removePackageLocked(str, packageUid, true, i);
            }
            empty = of;
        }
        nativeTombstoneManager.purge(java.util.Optional.of(java.lang.Integer.valueOf(i)), empty);
        schedulePersistProcessExitInfo(true);
    }

    void dumpHistoryProcessExitInfo(final java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println("ACTIVITY MANAGER PROCESS EXIT INFO (dumpsys activity exit-info)");
        final android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        synchronized (this.mLock) {
            try {
                printWriter.println("Last Timestamp of Persistence Into Persistent Storage: " + simpleDateFormat.format(new java.util.Date(this.mLastAppExitInfoPersistTimestamp)));
                if (android.text.TextUtils.isEmpty(str)) {
                    forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda16
                        @Override // java.util.function.BiFunction
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                            java.lang.Integer lambda$dumpHistoryProcessExitInfo$6;
                            lambda$dumpHistoryProcessExitInfo$6 = com.android.server.am.AppExitInfoTracker.this.lambda$dumpHistoryProcessExitInfo$6(printWriter, simpleDateFormat, (java.lang.String) obj, (android.util.SparseArray) obj2);
                            return lambda$dumpHistoryProcessExitInfo$6;
                        }
                    });
                } else {
                    android.util.SparseArray<com.android.server.am.AppExitInfoTracker.AppExitInfoContainer> sparseArray = (android.util.SparseArray) this.mData.getMap().get(str);
                    if (sparseArray != null) {
                        dumpHistoryProcessExitInfoLocked(printWriter, "  ", str, sparseArray, simpleDateFormat);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$dumpHistoryProcessExitInfo$6(java.io.PrintWriter printWriter, android.icu.text.SimpleDateFormat simpleDateFormat, java.lang.String str, android.util.SparseArray sparseArray) {
        dumpHistoryProcessExitInfoLocked(printWriter, "  ", str, sparseArray, simpleDateFormat);
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpHistoryProcessExitInfoLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.util.SparseArray<com.android.server.am.AppExitInfoTracker.AppExitInfoContainer> sparseArray, android.icu.text.SimpleDateFormat simpleDateFormat) {
        printWriter.println(str + "package: " + str2);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.println(str + "  Historical Process Exit for uid=" + sparseArray.keyAt(i));
            sparseArray.valueAt(i).dumpLocked(printWriter, str + "    ", simpleDateFormat);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addExitInfoInnerLocked(java.lang.String str, int i, android.app.ApplicationExitInfo applicationExitInfo, boolean z) {
        com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) this.mData.get(str, i);
        if (appExitInfoContainer == null) {
            appExitInfoContainer = new com.android.server.am.AppExitInfoTracker.AppExitInfoContainer(this.mAppExitInfoHistoryListSize);
            if (android.os.UserHandle.isIsolated(applicationExitInfo.getRealUid())) {
                java.lang.Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(applicationExitInfo.getRealUid());
                if (uidByIsolatedUid != null) {
                    appExitInfoContainer.mUid = uidByIsolatedUid.intValue();
                }
            } else {
                appExitInfoContainer.mUid = applicationExitInfo.getRealUid();
            }
            this.mData.put(str, i, appExitInfoContainer);
        }
        if (z) {
            appExitInfoContainer.addRecoverableCrashLocked(applicationExitInfo);
        } else {
            appExitInfoContainer.addExitInfoLocked(applicationExitInfo);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleLogToStatsdLocked(android.app.ApplicationExitInfo applicationExitInfo, boolean z) {
        if (applicationExitInfo.isLoggedInStatsd()) {
            return;
        }
        if (z) {
            this.mKillHandler.removeMessages(4105, applicationExitInfo);
            performLogToStatsdLocked(applicationExitInfo);
        } else if (!this.mKillHandler.hasMessages(4105, applicationExitInfo)) {
            this.mKillHandler.sendMessageDelayed(this.mKillHandler.obtainMessage(4105, applicationExitInfo), APP_EXIT_INFO_STATSD_LOG_DEBOUNCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void performLogToStatsdLocked(android.app.ApplicationExitInfo applicationExitInfo) {
        java.lang.String str;
        if (applicationExitInfo.isLoggedInStatsd()) {
            return;
        }
        applicationExitInfo.setLoggedInStatsd(true);
        java.lang.String packageName = applicationExitInfo.getPackageName();
        java.lang.String processName = applicationExitInfo.getProcessName();
        if (android.text.TextUtils.equals(packageName, processName)) {
            str = null;
        } else if (processName != null && packageName != null && processName.startsWith(packageName)) {
            str = processName.substring(packageName.length());
        } else {
            str = processName;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_PROCESS_DIED, applicationExitInfo.getPackageUid(), str, applicationExitInfo.getReason(), applicationExitInfo.getSubReason(), applicationExitInfo.getImportance(), (int) applicationExitInfo.getPss(), (int) applicationExitInfo.getRss(), applicationExitInfo.hasForegroundServices());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void forEachPackageLocked(java.util.function.BiFunction<java.lang.String, android.util.SparseArray<com.android.server.am.AppExitInfoTracker.AppExitInfoContainer>, java.lang.Integer> biFunction) {
        if (biFunction != null) {
            android.util.ArrayMap map = this.mData.getMap();
            int size = map.size() - 1;
            while (size >= 0) {
                switch (biFunction.apply((java.lang.String) map.keyAt(size), (android.util.SparseArray) map.valueAt(size)).intValue()) {
                    case 1:
                        android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(size);
                        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                            ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(size2)).destroyLocked();
                        }
                        map.removeAt(size);
                        break;
                    case 2:
                        size = 0;
                        break;
                }
                size--;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removePackageLocked(java.lang.String str, int i, boolean z, int i2) {
        if (z) {
            this.mActiveAppStateSummary.remove(i);
            int indexOfKey = this.mActiveAppTraces.indexOfKey(i);
            if (indexOfKey >= 0) {
                android.util.SparseArray<java.io.File> valueAt = this.mActiveAppTraces.valueAt(indexOfKey);
                for (int size = valueAt.size() - 1; size >= 0; size--) {
                    valueAt.valueAt(size).delete();
                }
                this.mActiveAppTraces.removeAt(indexOfKey);
            }
        }
        android.util.ArrayMap map = this.mData.getMap();
        android.util.SparseArray sparseArray = (android.util.SparseArray) map.get(str);
        if (sparseArray == null) {
            return;
        }
        if (i2 == -1) {
            for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(size2)).destroyLocked();
            }
            this.mData.getMap().remove(str);
            return;
        }
        int size3 = sparseArray.size() - 1;
        while (true) {
            if (size3 < 0) {
                break;
            }
            if (android.os.UserHandle.getUserId(sparseArray.keyAt(size3)) == i2) {
                ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(size3)).destroyLocked();
                sparseArray.removeAt(size3);
                break;
            }
            size3--;
        }
        if (sparseArray.size() == 0) {
            map.remove(str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeByUserIdLocked(final int i) {
        if (i == -1) {
            this.mData.getMap().clear();
            this.mActiveAppStateSummary.clear();
            this.mActiveAppTraces.clear();
            pruneAnrTracesIfNecessaryLocked();
            return;
        }
        removeFromSparse2dArray(this.mActiveAppStateSummary, new java.util.function.Predicate() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeByUserIdLocked$7;
                lambda$removeByUserIdLocked$7 = com.android.server.am.AppExitInfoTracker.lambda$removeByUserIdLocked$7(i, (java.lang.Integer) obj);
                return lambda$removeByUserIdLocked$7;
            }
        }, null, null);
        removeFromSparse2dArray(this.mActiveAppTraces, new java.util.function.Predicate() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeByUserIdLocked$8;
                lambda$removeByUserIdLocked$8 = com.android.server.am.AppExitInfoTracker.lambda$removeByUserIdLocked$8(i, (java.lang.Integer) obj);
                return lambda$removeByUserIdLocked$8;
            }
        }, null, new java.util.function.Consumer() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((java.io.File) obj).delete();
            }
        });
        forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Integer lambda$removeByUserIdLocked$10;
                lambda$removeByUserIdLocked$10 = com.android.server.am.AppExitInfoTracker.lambda$removeByUserIdLocked$10(i, (java.lang.String) obj, (android.util.SparseArray) obj2);
                return lambda$removeByUserIdLocked$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeByUserIdLocked$7(int i, java.lang.Integer num) {
        return android.os.UserHandle.getUserId(num.intValue()) == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeByUserIdLocked$8(int i, java.lang.Integer num) {
        return android.os.UserHandle.getUserId(num.intValue()) == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$removeByUserIdLocked$10(int i, java.lang.String str, android.util.SparseArray sparseArray) {
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (android.os.UserHandle.getUserId(sparseArray.keyAt(size)) != i) {
                size--;
            } else {
                ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(size)).destroyLocked();
                sparseArray.removeAt(size);
                break;
            }
        }
        return java.lang.Integer.valueOf(sparseArray.size() != 0 ? 0 : 1);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.ApplicationExitInfo obtainRawRecord(com.android.server.am.ProcessRecord processRecord, long j) {
        android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) this.mRawRecordsPool.acquire();
        if (applicationExitInfo == null) {
            applicationExitInfo = new android.app.ApplicationExitInfo();
        }
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                int definingUid = processRecord.getHostingRecord() != null ? processRecord.getHostingRecord().getDefiningUid() : 0;
                applicationExitInfo.setPid(processRecord.getPid());
                applicationExitInfo.setRealUid(processRecord.uid);
                applicationExitInfo.setPackageUid(processRecord.info.uid);
                if (definingUid <= 0) {
                    definingUid = processRecord.info.uid;
                }
                applicationExitInfo.setDefiningUid(definingUid);
                applicationExitInfo.setProcessName(processRecord.processName);
                applicationExitInfo.setConnectionGroup(processRecord.mServices.getConnectionGroup());
                applicationExitInfo.setPackageName(processRecord.info.packageName);
                applicationExitInfo.setPackageList(processRecord.getPackageList());
                applicationExitInfo.setReason(0);
                applicationExitInfo.setSubReason(0);
                applicationExitInfo.setStatus(0);
                applicationExitInfo.setImportance(android.app.ActivityManager.RunningAppProcessInfo.procStateToImportance(processRecord.mState.getReportedProcState()));
                applicationExitInfo.setPss(processRecord.mProfile.getLastPss());
                applicationExitInfo.setRss(processRecord.mProfile.getLastRss());
                applicationExitInfo.setTimestamp(j);
                applicationExitInfo.setHasForegroundServices(processRecord.mServices.hasReportedForegroundServices());
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return applicationExitInfo;
    }

    @com.android.internal.annotations.VisibleForTesting
    void recycleRawRecord(android.app.ApplicationExitInfo applicationExitInfo) {
        applicationExitInfo.setProcessName(null);
        applicationExitInfo.setDescription(null);
        applicationExitInfo.setPackageList(null);
        this.mRawRecordsPool.release(applicationExitInfo);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setProcessStateSummary(int i, int i2, byte[] bArr) {
        int i3;
        synchronized (this.mLock) {
            try {
                java.lang.Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i);
                if (uidByIsolatedUid == null) {
                    i3 = i;
                } else {
                    i3 = uidByIsolatedUid.intValue();
                }
                putToSparse2dArray(this.mActiveAppStateSummary, i3, i2, bArr, new com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda9(), null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    byte[] getProcessStateSummary(int i, int i2) {
        synchronized (this.mLock) {
            try {
                java.lang.Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i);
                if (uidByIsolatedUid != null) {
                    i = uidByIsolatedUid.intValue();
                }
                int indexOfKey = this.mActiveAppStateSummary.indexOfKey(i);
                if (indexOfKey < 0) {
                    return null;
                }
                return this.mActiveAppStateSummary.valueAt(indexOfKey).get(i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void scheduleLogAnrTrace(int i, int i2, java.lang.String[] strArr, java.io.File file, long j, long j2) {
        this.mKillHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda11
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                com.android.server.am.AppExitInfoTracker.this.handleLogAnrTrace(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), (java.lang.String[]) obj3, (java.io.File) obj4, ((java.lang.Long) obj5).longValue(), ((java.lang.Long) obj6).longValue());
            }
        }, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), strArr, file, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2)));
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleLogAnrTrace(int i, int i2, java.lang.String[] strArr, java.io.File file, long j, long j2) {
        int i3;
        if (!file.exists() || com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            return;
        }
        long length = file.length();
        long j3 = j2 - j;
        if (j < length && j2 <= length && j3 > 0) {
            java.io.File file2 = new java.io.File(this.mProcExitStoreDir, file.getName() + ".gz");
            if (copyToGzFile(file, file2, j, j3)) {
                synchronized (this.mLock) {
                    try {
                        java.lang.Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
                        if (uidByIsolatedUid == null) {
                            i3 = i2;
                        } else {
                            i3 = uidByIsolatedUid.intValue();
                        }
                        boolean z = true;
                        for (java.lang.String str : strArr) {
                            com.android.server.am.AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer = (com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) this.mData.get(str, i3);
                            if (appExitInfoContainer != null && appExitInfoContainer.appendTraceIfNecessaryLocked(i, file2)) {
                                z = false;
                            }
                        }
                        if (z) {
                            putToSparse2dArray(this.mActiveAppTraces, i3, i, file2, new com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda9(), new java.util.function.Consumer() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda17
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    ((java.io.File) obj).delete();
                                }
                            });
                        }
                    } finally {
                    }
                }
            }
        }
    }

    private static boolean copyToGzFile(java.io.File file, java.io.File file2, long j, long j2) {
        try {
            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
            try {
                java.util.zip.GZIPOutputStream gZIPOutputStream = new java.util.zip.GZIPOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(file2)));
                try {
                    byte[] bArr = new byte[8192];
                    bufferedInputStream.skip(j);
                    while (j2 > 0) {
                        int read = bufferedInputStream.read(bArr, 0, (int) java.lang.Math.min(8192, j2));
                        if (read < 0) {
                            break;
                        }
                        gZIPOutputStream.write(bArr, 0, read);
                        j2 -= read;
                    }
                    gZIPOutputStream.close();
                    bufferedInputStream.close();
                    return j2 == 0 && file2.exists();
                } finally {
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void pruneAnrTracesIfNecessaryLocked() {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        if (com.android.internal.util.ArrayUtils.isEmpty(this.mProcExitStoreDir.listFiles(new java.io.FileFilter() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda13
            @Override // java.io.FileFilter
            public final boolean accept(java.io.File file) {
                boolean lambda$pruneAnrTracesIfNecessaryLocked$12;
                lambda$pruneAnrTracesIfNecessaryLocked$12 = com.android.server.am.AppExitInfoTracker.lambda$pruneAnrTracesIfNecessaryLocked$12(arraySet, file);
                return lambda$pruneAnrTracesIfNecessaryLocked$12;
            }
        }))) {
            return;
        }
        forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda14
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Integer lambda$pruneAnrTracesIfNecessaryLocked$14;
                lambda$pruneAnrTracesIfNecessaryLocked$14 = com.android.server.am.AppExitInfoTracker.lambda$pruneAnrTracesIfNecessaryLocked$14(arraySet, (java.lang.String) obj, (android.util.SparseArray) obj2);
                return lambda$pruneAnrTracesIfNecessaryLocked$14;
            }
        });
        forEachSparse2dArray(this.mActiveAppTraces, new java.util.function.Consumer() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.AppExitInfoTracker.lambda$pruneAnrTracesIfNecessaryLocked$15(arraySet, (java.io.File) obj);
            }
        });
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            new java.io.File(this.mProcExitStoreDir, (java.lang.String) arraySet.valueAt(size)).delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$pruneAnrTracesIfNecessaryLocked$12(android.util.ArraySet arraySet, java.io.File file) {
        java.lang.String name = file.getName();
        boolean z = name.startsWith("anr_") && name.endsWith(".gz");
        if (z) {
            arraySet.add(name);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$pruneAnrTracesIfNecessaryLocked$14(final android.util.ArraySet arraySet, java.lang.String str, android.util.SparseArray sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ((com.android.server.am.AppExitInfoTracker.AppExitInfoContainer) sparseArray.valueAt(size)).forEachRecordLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda10
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    java.lang.Integer lambda$pruneAnrTracesIfNecessaryLocked$13;
                    lambda$pruneAnrTracesIfNecessaryLocked$13 = com.android.server.am.AppExitInfoTracker.lambda$pruneAnrTracesIfNecessaryLocked$13(arraySet, (java.lang.Integer) obj, (android.app.ApplicationExitInfo) obj2);
                    return lambda$pruneAnrTracesIfNecessaryLocked$13;
                }
            });
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$pruneAnrTracesIfNecessaryLocked$13(android.util.ArraySet arraySet, java.lang.Integer num, android.app.ApplicationExitInfo applicationExitInfo) {
        java.io.File traceFile = applicationExitInfo.getTraceFile();
        if (traceFile != null) {
            arraySet.remove(traceFile.getName());
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pruneAnrTracesIfNecessaryLocked$15(android.util.ArraySet arraySet, java.io.File file) {
        arraySet.remove(file.getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T extends android.util.SparseArray<U>, U> void putToSparse2dArray(android.util.SparseArray<T> sparseArray, int i, int i2, U u, java.util.function.Supplier<T> supplier, java.util.function.Consumer<U> consumer) {
        T valueAt;
        int indexOfKey = sparseArray.indexOfKey(i);
        if (indexOfKey < 0) {
            valueAt = supplier.get();
            sparseArray.put(i, valueAt);
        } else {
            valueAt = sparseArray.valueAt(indexOfKey);
        }
        int indexOfKey2 = valueAt.indexOfKey(i2);
        if (indexOfKey2 >= 0) {
            if (consumer != 0) {
                consumer.accept(valueAt.valueAt(indexOfKey2));
            }
            valueAt.setValueAt(indexOfKey2, u);
            return;
        }
        valueAt.put(i2, u);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T extends android.util.SparseArray<U>, U> void forEachSparse2dArray(android.util.SparseArray<T> sparseArray, java.util.function.Consumer<U> consumer) {
        if (consumer != 0) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                T valueAt = sparseArray.valueAt(size);
                if (valueAt != null) {
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        consumer.accept(valueAt.valueAt(size2));
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T extends android.util.SparseArray<U>, U> void removeFromSparse2dArray(android.util.SparseArray<T> sparseArray, java.util.function.Predicate<java.lang.Integer> predicate, java.util.function.Predicate<java.lang.Integer> predicate2, java.util.function.Consumer<U> consumer) {
        T valueAt;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            if ((predicate == null || predicate.test(java.lang.Integer.valueOf(sparseArray.keyAt(size)))) && (valueAt = sparseArray.valueAt(size)) != null) {
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    if (predicate2 == null || predicate2.test(java.lang.Integer.valueOf(valueAt.keyAt(size2)))) {
                        if (consumer != 0) {
                            consumer.accept(valueAt.valueAt(size2));
                        }
                        valueAt.removeAt(size2);
                    }
                }
                if (valueAt.size() == 0) {
                    sparseArray.removeAt(size);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends android.util.SparseArray<U>, U> U findAndRemoveFromSparse2dArray(android.util.SparseArray<T> sparseArray, int i, int i2) {
        T valueAt;
        int indexOfKey;
        int indexOfKey2 = sparseArray.indexOfKey(i);
        if (indexOfKey2 < 0 || (valueAt = sparseArray.valueAt(indexOfKey2)) == null || (indexOfKey = valueAt.indexOfKey(i2)) < 0) {
            return null;
        }
        U u = (U) valueAt.valueAt(indexOfKey);
        valueAt.removeAt(indexOfKey);
        if (valueAt.size() == 0) {
            sparseArray.removeAt(indexOfKey2);
        }
        return u;
    }

    final class AppExitInfoContainer {
        private int mMaxCapacity;
        private int mUid;
        private android.util.SparseArray<android.app.ApplicationExitInfo> mInfos = new android.util.SparseArray<>();
        private android.util.SparseArray<android.app.ApplicationExitInfo> mRecoverableCrashes = new android.util.SparseArray<>();

        AppExitInfoContainer(int i) {
            this.mMaxCapacity = i;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void getInfosLocked(android.util.SparseArray<android.app.ApplicationExitInfo> sparseArray, int i, int i2, java.util.ArrayList<android.app.ApplicationExitInfo> arrayList) {
            if (i > 0) {
                android.app.ApplicationExitInfo applicationExitInfo = sparseArray.get(i);
                if (applicationExitInfo != null) {
                    arrayList.add(applicationExitInfo);
                    return;
                }
                return;
            }
            int size = sparseArray.size();
            int i3 = 0;
            if (i2 <= 0 || size <= i2) {
                while (i3 < size) {
                    arrayList.add(sparseArray.valueAt(i3));
                    i3++;
                }
                java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.server.am.AppExitInfoTracker$AppExitInfoContainer$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        int lambda$getInfosLocked$0;
                        lambda$getInfosLocked$0 = com.android.server.am.AppExitInfoTracker.AppExitInfoContainer.lambda$getInfosLocked$0((android.app.ApplicationExitInfo) obj, (android.app.ApplicationExitInfo) obj2);
                        return lambda$getInfosLocked$0;
                    }
                });
                return;
            }
            if (i2 == 1) {
                android.app.ApplicationExitInfo valueAt = sparseArray.valueAt(0);
                for (int i4 = 1; i4 < size; i4++) {
                    android.app.ApplicationExitInfo valueAt2 = sparseArray.valueAt(i4);
                    if (valueAt.getTimestamp() < valueAt2.getTimestamp()) {
                        valueAt = valueAt2;
                    }
                }
                arrayList.add(valueAt);
                return;
            }
            java.util.ArrayList<android.app.ApplicationExitInfo> arrayList2 = com.android.server.am.AppExitInfoTracker.this.mTmpInfoList2;
            arrayList2.clear();
            for (int i5 = 0; i5 < size; i5++) {
                arrayList2.add(sparseArray.valueAt(i5));
            }
            java.util.Collections.sort(arrayList2, new java.util.Comparator() { // from class: com.android.server.am.AppExitInfoTracker$AppExitInfoContainer$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$getInfosLocked$1;
                    lambda$getInfosLocked$1 = com.android.server.am.AppExitInfoTracker.AppExitInfoContainer.lambda$getInfosLocked$1((android.app.ApplicationExitInfo) obj, (android.app.ApplicationExitInfo) obj2);
                    return lambda$getInfosLocked$1;
                }
            });
            while (i3 < i2) {
                arrayList.add(arrayList2.get(i3));
                i3++;
            }
            arrayList2.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$getInfosLocked$0(android.app.ApplicationExitInfo applicationExitInfo, android.app.ApplicationExitInfo applicationExitInfo2) {
            return java.lang.Long.compare(applicationExitInfo2.getTimestamp(), applicationExitInfo.getTimestamp());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$getInfosLocked$1(android.app.ApplicationExitInfo applicationExitInfo, android.app.ApplicationExitInfo applicationExitInfo2) {
            return java.lang.Long.compare(applicationExitInfo2.getTimestamp(), applicationExitInfo.getTimestamp());
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void getExitInfoLocked(int i, int i2, java.util.ArrayList<android.app.ApplicationExitInfo> arrayList) {
            getInfosLocked(this.mInfos, i, i2, arrayList);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addInfoLocked(android.util.SparseArray<android.app.ApplicationExitInfo> sparseArray, android.app.ApplicationExitInfo applicationExitInfo) {
            int size = sparseArray.size();
            if (size >= this.mMaxCapacity) {
                int i = -1;
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.ApplicationExitInfo valueAt = sparseArray.valueAt(i2);
                    if (valueAt.getTimestamp() < j) {
                        j = valueAt.getTimestamp();
                        i = i2;
                    }
                }
                if (i >= 0) {
                    java.io.File traceFile = sparseArray.valueAt(i).getTraceFile();
                    if (traceFile != null) {
                        traceFile.delete();
                    }
                    sparseArray.removeAt(i);
                }
            }
            int packageUid = applicationExitInfo.getPackageUid();
            if (android.os.Process.isSdkSandboxUid(applicationExitInfo.getRealUid())) {
                packageUid = applicationExitInfo.getRealUid();
            }
            int pid = applicationExitInfo.getPid();
            if (applicationExitInfo.getProcessStateSummary() == null) {
                applicationExitInfo.setProcessStateSummary((byte[]) com.android.server.am.AppExitInfoTracker.findAndRemoveFromSparse2dArray(com.android.server.am.AppExitInfoTracker.this.mActiveAppStateSummary, packageUid, pid));
            }
            if (applicationExitInfo.getTraceFile() == null) {
                applicationExitInfo.setTraceFile((java.io.File) com.android.server.am.AppExitInfoTracker.findAndRemoveFromSparse2dArray(com.android.server.am.AppExitInfoTracker.this.mActiveAppTraces, packageUid, pid));
            }
            applicationExitInfo.setAppTraceRetriever(com.android.server.am.AppExitInfoTracker.this.mAppTraceRetriever);
            sparseArray.append(pid, applicationExitInfo);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addExitInfoLocked(android.app.ApplicationExitInfo applicationExitInfo) {
            addInfoLocked(this.mInfos, applicationExitInfo);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addRecoverableCrashLocked(android.app.ApplicationExitInfo applicationExitInfo) {
            addInfoLocked(this.mRecoverableCrashes, applicationExitInfo);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean appendTraceIfNecessaryLocked(int i, java.io.File file) {
            android.app.ApplicationExitInfo applicationExitInfo = this.mInfos.get(i);
            if (applicationExitInfo != null) {
                applicationExitInfo.setTraceFile(file);
                applicationExitInfo.setAppTraceRetriever(com.android.server.am.AppExitInfoTracker.this.mAppTraceRetriever);
                return true;
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void destroyLocked(android.util.SparseArray<android.app.ApplicationExitInfo> sparseArray) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                android.app.ApplicationExitInfo valueAt = sparseArray.valueAt(size);
                java.io.File traceFile = valueAt.getTraceFile();
                if (traceFile != null) {
                    traceFile.delete();
                }
                valueAt.setTraceFile(null);
                valueAt.setAppTraceRetriever(null);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void destroyLocked() {
            destroyLocked(this.mInfos);
            destroyLocked(this.mRecoverableCrashes);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void forEachRecordLocked(java.util.function.BiFunction<java.lang.Integer, android.app.ApplicationExitInfo, java.lang.Integer> biFunction) {
            if (biFunction == null) {
                return;
            }
            for (int size = this.mInfos.size() - 1; size >= 0; size--) {
                switch (biFunction.apply(java.lang.Integer.valueOf(this.mInfos.keyAt(size)), this.mInfos.valueAt(size)).intValue()) {
                    case 1:
                        java.io.File traceFile = this.mInfos.valueAt(size).getTraceFile();
                        if (traceFile != null) {
                            traceFile.delete();
                        }
                        this.mInfos.removeAt(size);
                        break;
                    case 2:
                        return;
                }
            }
            for (int size2 = this.mRecoverableCrashes.size() - 1; size2 >= 0; size2--) {
                switch (biFunction.apply(java.lang.Integer.valueOf(this.mRecoverableCrashes.keyAt(size2)), this.mRecoverableCrashes.valueAt(size2)).intValue()) {
                    case 1:
                        java.io.File traceFile2 = this.mRecoverableCrashes.valueAt(size2).getTraceFile();
                        if (traceFile2 != null) {
                            traceFile2.delete();
                        }
                        this.mRecoverableCrashes.removeAt(size2);
                        break;
                    case 2:
                        return;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str, android.icu.text.SimpleDateFormat simpleDateFormat) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int size = this.mInfos.size() - 1; size >= 0; size--) {
                arrayList.add(this.mInfos.valueAt(size));
            }
            for (int size2 = this.mRecoverableCrashes.size() - 1; size2 >= 0; size2--) {
                arrayList.add(this.mRecoverableCrashes.valueAt(size2));
            }
            java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.server.am.AppExitInfoTracker$AppExitInfoContainer$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$dumpLocked$2;
                    lambda$dumpLocked$2 = com.android.server.am.AppExitInfoTracker.AppExitInfoContainer.lambda$dumpLocked$2((android.app.ApplicationExitInfo) obj, (android.app.ApplicationExitInfo) obj2);
                    return lambda$dumpLocked$2;
                }
            });
            int size3 = arrayList.size();
            for (int i = 0; i < size3; i++) {
                ((android.app.ApplicationExitInfo) arrayList.get(i)).dump(printWriter, str + "  ", "#" + i, simpleDateFormat);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$dumpLocked$2(android.app.ApplicationExitInfo applicationExitInfo, android.app.ApplicationExitInfo applicationExitInfo2) {
            return java.lang.Long.compare(applicationExitInfo2.getTimestamp(), applicationExitInfo.getTimestamp());
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void writeToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            for (int i = 0; i < this.mInfos.size(); i++) {
                this.mInfos.valueAt(i).writeToProto(protoOutputStream, 2246267895810L);
            }
            for (int i2 = 0; i2 < this.mRecoverableCrashes.size(); i2++) {
                this.mRecoverableCrashes.valueAt(i2).writeToProto(protoOutputStream, 2246267895811L);
            }
            protoOutputStream.end(start);
        }

        int readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException {
            long start = protoInputStream.start(j);
            int nextField = protoInputStream.nextField();
            while (nextField != -1) {
                switch (nextField) {
                    case 1:
                        this.mUid = protoInputStream.readInt(1120986464257L);
                        break;
                    case 2:
                        android.app.ApplicationExitInfo applicationExitInfo = new android.app.ApplicationExitInfo();
                        applicationExitInfo.readFromProto(protoInputStream, 2246267895810L);
                        this.mInfos.put(applicationExitInfo.getPid(), applicationExitInfo);
                        break;
                    case 3:
                        android.app.ApplicationExitInfo applicationExitInfo2 = new android.app.ApplicationExitInfo();
                        applicationExitInfo2.readFromProto(protoInputStream, 2246267895811L);
                        this.mRecoverableCrashes.put(applicationExitInfo2.getPid(), applicationExitInfo2);
                        break;
                }
                nextField = protoInputStream.nextField();
            }
            protoInputStream.end(start);
            return this.mUid;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        java.util.List<android.app.ApplicationExitInfo> toListLocked(java.util.List<android.app.ApplicationExitInfo> list, int i) {
            if (list == null) {
                list = new java.util.ArrayList<>();
            }
            for (int size = this.mInfos.size() - 1; size >= 0; size--) {
                if (i == 0 || i == this.mInfos.keyAt(size)) {
                    list.add(this.mInfos.valueAt(size));
                }
            }
            for (int size2 = this.mRecoverableCrashes.size() - 1; size2 >= 0; size2--) {
                if (i == 0 || i == this.mRecoverableCrashes.keyAt(size2)) {
                    list.add(this.mRecoverableCrashes.valueAt(size2));
                }
            }
            return list;
        }
    }

    final class IsolatedUidRecords {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<android.util.ArraySet<java.lang.Integer>> mUidToIsolatedUidMap = new android.util.SparseArray<>();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<java.lang.Integer> mIsolatedUidToUidMap = new android.util.SparseArray<>();

        IsolatedUidRecords() {
        }

        void addIsolatedUid(int i, int i2) {
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    android.util.ArraySet<java.lang.Integer> arraySet = this.mUidToIsolatedUidMap.get(i2);
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet<>();
                        this.mUidToIsolatedUidMap.put(i2, arraySet);
                    }
                    arraySet.add(java.lang.Integer.valueOf(i));
                    this.mIsolatedUidToUidMap.put(i, java.lang.Integer.valueOf(i2));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void removeIsolatedUid(int i, int i2) {
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    int indexOfKey = this.mUidToIsolatedUidMap.indexOfKey(i2);
                    if (indexOfKey >= 0) {
                        android.util.ArraySet<java.lang.Integer> valueAt = this.mUidToIsolatedUidMap.valueAt(indexOfKey);
                        valueAt.remove(java.lang.Integer.valueOf(i));
                        if (valueAt.isEmpty()) {
                            this.mUidToIsolatedUidMap.removeAt(indexOfKey);
                        }
                    }
                    this.mIsolatedUidToUidMap.remove(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        java.lang.Integer getUidByIsolatedUid(int i) {
            java.lang.Integer num;
            if (android.os.UserHandle.isIsolated(i)) {
                synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                    num = this.mIsolatedUidToUidMap.get(i);
                }
                return num;
            }
            return java.lang.Integer.valueOf(i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void removeAppUidLocked(int i) {
            android.util.ArraySet<java.lang.Integer> arraySet = this.mUidToIsolatedUidMap.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    this.mIsolatedUidToUidMap.remove(arraySet.removeAt(size).intValue());
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void removeAppUid(int i, boolean z) {
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    if (z) {
                        int appId = android.os.UserHandle.getAppId(i);
                        for (int size = this.mUidToIsolatedUidMap.size() - 1; size >= 0; size--) {
                            int keyAt = this.mUidToIsolatedUidMap.keyAt(size);
                            if (appId == android.os.UserHandle.getAppId(keyAt)) {
                                removeAppUidLocked(keyAt);
                            }
                            this.mUidToIsolatedUidMap.removeAt(size);
                        }
                    } else {
                        removeAppUidLocked(i);
                        this.mUidToIsolatedUidMap.remove(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        int removeIsolatedUidLocked(int i) {
            if (!android.os.UserHandle.isIsolated(i)) {
                return i;
            }
            int intValue = this.mIsolatedUidToUidMap.get(i, -1).intValue();
            if (intValue == -1) {
                return i;
            }
            this.mIsolatedUidToUidMap.remove(i);
            android.util.ArraySet<java.lang.Integer> arraySet = this.mUidToIsolatedUidMap.get(intValue);
            if (arraySet != null) {
                arraySet.remove(java.lang.Integer.valueOf(i));
            }
            return intValue;
        }

        void removeByUserId(int i) {
            if (i == -2) {
                i = com.android.server.am.AppExitInfoTracker.this.mService.mUserController.getCurrentUserId();
            }
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    if (i == -1) {
                        this.mIsolatedUidToUidMap.clear();
                        this.mUidToIsolatedUidMap.clear();
                        return;
                    }
                    for (int size = this.mIsolatedUidToUidMap.size() - 1; size >= 0; size--) {
                        this.mIsolatedUidToUidMap.keyAt(size);
                        int intValue = this.mIsolatedUidToUidMap.valueAt(size).intValue();
                        if (android.os.UserHandle.getUserId(intValue) == i) {
                            this.mIsolatedUidToUidMap.removeAt(size);
                            this.mUidToIsolatedUidMap.remove(intValue);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class KillHandler extends android.os.Handler {
        static final int MSG_APP_KILL = 4104;
        static final int MSG_APP_RECOVERABLE_CRASH = 4106;
        static final int MSG_CHILD_PROC_DIED = 4102;
        static final int MSG_LMKD_PROC_KILLED = 4101;
        static final int MSG_PROC_DIED = 4103;
        static final int MSG_STATSD_LOG = 4105;

        KillHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case MSG_LMKD_PROC_KILLED /* 4101 */:
                    com.android.server.am.AppExitInfoTracker.this.mAppExitInfoSourceLmkd.onProcDied(message.arg1, message.arg2, null);
                    return;
                case MSG_CHILD_PROC_DIED /* 4102 */:
                    com.android.server.am.AppExitInfoTracker.this.mAppExitInfoSourceZygote.onProcDied(message.arg1, message.arg2, (java.lang.Integer) message.obj);
                    return;
                case MSG_PROC_DIED /* 4103 */:
                    android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) message.obj;
                    synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                        com.android.server.am.AppExitInfoTracker.this.handleNoteProcessDiedLocked(applicationExitInfo);
                    }
                    com.android.server.am.AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo);
                    return;
                case MSG_APP_KILL /* 4104 */:
                    android.app.ApplicationExitInfo applicationExitInfo2 = (android.app.ApplicationExitInfo) message.obj;
                    synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                        com.android.server.am.AppExitInfoTracker.this.handleNoteAppKillLocked(applicationExitInfo2);
                    }
                    com.android.server.am.AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo2);
                    return;
                case MSG_STATSD_LOG /* 4105 */:
                    synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                        com.android.server.am.AppExitInfoTracker.this.performLogToStatsdLocked((android.app.ApplicationExitInfo) message.obj);
                    }
                    return;
                case MSG_APP_RECOVERABLE_CRASH /* 4106 */:
                    android.app.ApplicationExitInfo applicationExitInfo3 = (android.app.ApplicationExitInfo) message.obj;
                    synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                        com.android.server.am.AppExitInfoTracker.this.handleNoteAppRecoverableCrashLocked(applicationExitInfo3);
                    }
                    com.android.server.am.AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo3);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isFresh(long j) {
        return j + com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS >= java.lang.System.currentTimeMillis();
    }

    final class AppExitInfoExternalSource {
        private static final long APP_EXIT_INFO_FRESHNESS_MS = 300000;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<android.util.SparseArray<android.util.Pair<java.lang.Long, java.lang.Object>>> mData = new android.util.SparseArray<>();
        private final java.lang.Integer mPresetReason;
        private java.util.function.BiConsumer<java.lang.Integer, java.lang.Integer> mProcDiedListener;
        private final java.lang.String mTag;

        AppExitInfoExternalSource(java.lang.String str, java.lang.Integer num) {
            this.mTag = str;
            this.mPresetReason = num;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void addLocked(int i, int i2, java.lang.Object obj) {
            java.lang.Integer uidByIsolatedUid = com.android.server.am.AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
            if (uidByIsolatedUid != null) {
                i2 = uidByIsolatedUid.intValue();
            }
            android.util.SparseArray<android.util.Pair<java.lang.Long, java.lang.Object>> sparseArray = this.mData.get(i2);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mData.put(i2, sparseArray);
            }
            sparseArray.put(i, new android.util.Pair<>(java.lang.Long.valueOf(java.lang.System.currentTimeMillis()), obj));
        }

        @com.android.internal.annotations.VisibleForTesting
        android.util.Pair<java.lang.Long, java.lang.Object> remove(int i, int i2) {
            android.util.Pair<java.lang.Long, java.lang.Object> pair;
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    java.lang.Integer uidByIsolatedUid = com.android.server.am.AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
                    if (uidByIsolatedUid != null) {
                        i2 = uidByIsolatedUid.intValue();
                    }
                    android.util.SparseArray<android.util.Pair<java.lang.Long, java.lang.Object>> sparseArray = this.mData.get(i2);
                    if (sparseArray == null || (pair = sparseArray.get(i)) == null) {
                        return null;
                    }
                    sparseArray.remove(i);
                    return com.android.server.am.AppExitInfoTracker.this.isFresh(((java.lang.Long) pair.first).longValue()) ? pair : null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void removeByUserId(int i) {
            if (i == -2) {
                i = com.android.server.am.AppExitInfoTracker.this.mService.mUserController.getCurrentUserId();
            }
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    if (i == -1) {
                        this.mData.clear();
                        return;
                    }
                    for (int size = this.mData.size() - 1; size >= 0; size--) {
                        if (android.os.UserHandle.getUserId(this.mData.keyAt(size)) == i) {
                            this.mData.removeAt(size);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void removeByUidLocked(int i, boolean z) {
            java.lang.Integer uidByIsolatedUid;
            if (android.os.UserHandle.isIsolated(i) && (uidByIsolatedUid = com.android.server.am.AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(i)) != null) {
                i = uidByIsolatedUid.intValue();
            }
            if (z) {
                int appId = android.os.UserHandle.getAppId(i);
                for (int size = this.mData.size() - 1; size >= 0; size--) {
                    if (android.os.UserHandle.getAppId(this.mData.keyAt(size)) == appId) {
                        this.mData.removeAt(size);
                    }
                }
                return;
            }
            this.mData.remove(i);
        }

        void setOnProcDiedListener(java.util.function.BiConsumer<java.lang.Integer, java.lang.Integer> biConsumer) {
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                this.mProcDiedListener = biConsumer;
            }
        }

        void onProcDied(final int i, final int i2, java.lang.Integer num) {
            if (com.android.server.am.AppExitInfoTracker.this.mService == null) {
                return;
            }
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    if (!com.android.server.am.AppExitInfoTracker.this.updateExitInfoIfNecessaryLocked(i, i2, num, this.mPresetReason)) {
                        addLocked(i, i2, num);
                    }
                    final java.util.function.BiConsumer<java.lang.Integer, java.lang.Integer> biConsumer = this.mProcDiedListener;
                    if (biConsumer != null) {
                        com.android.server.am.AppExitInfoTracker.this.mService.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.AppExitInfoTracker$AppExitInfoExternalSource$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.am.AppExitInfoTracker.AppExitInfoExternalSource.lambda$onProcDied$0(biConsumer, i, i2);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onProcDied$0(java.util.function.BiConsumer biConsumer, int i, int i2) {
            biConsumer.accept(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class AppTraceRetriever extends android.app.IAppTraceRetriever.Stub {
        AppTraceRetriever() {
        }

        public android.os.ParcelFileDescriptor getTraceFileDescriptor(java.lang.String str, int i, int i2) {
            com.android.server.am.AppExitInfoTracker.this.mService.enforceNotIsolatedCaller("getTraceFileDescriptor");
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("Invalid package name");
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(i);
            com.android.server.am.AppExitInfoTracker.this.mService.mUserController.handleIncomingUser(callingPid, callingUid, userId, true, 0, "getTraceFileDescriptor", null);
            int enforceDumpPermissionForPackage = com.android.server.am.AppExitInfoTracker.this.mService.enforceDumpPermissionForPackage(str, userId, callingUid, "getTraceFileDescriptor");
            if (enforceDumpPermissionForPackage == -1) {
                return null;
            }
            synchronized (com.android.server.am.AppExitInfoTracker.this.mLock) {
                try {
                    android.app.ApplicationExitInfo exitInfoLocked = com.android.server.am.AppExitInfoTracker.this.getExitInfoLocked(str, enforceDumpPermissionForPackage, i2);
                    if (exitInfoLocked == null) {
                        return null;
                    }
                    java.io.File traceFile = exitInfoLocked.getTraceFile();
                    if (traceFile == null) {
                        return null;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            return android.os.ParcelFileDescriptor.open(traceFile, 268435456);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.io.FileNotFoundException e) {
                        return null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
