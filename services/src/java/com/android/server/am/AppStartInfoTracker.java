package com.android.server.am;

/* loaded from: classes.dex */
public final class AppStartInfoTracker {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String APP_START_INFO_FILE = "procstartinfo";

    @com.android.internal.annotations.VisibleForTesting
    static final int APP_START_INFO_HISTORY_LIST_SIZE = 16;
    private static final long APP_START_INFO_PERSIST_INTERVAL = java.util.concurrent.TimeUnit.MINUTES.toMillis(30);

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String APP_START_STORE_DIR = "procstartstore";
    private static final int FOREACH_ACTION_NONE = 0;
    private static final int FOREACH_ACTION_REMOVE_ITEM = 1;
    private static final int FOREACH_ACTION_STOP_ITERATION = 2;
    private static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.VisibleForTesting
    int mAppStartInfoHistoryListSize;
    private android.os.Handler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    java.io.File mProcStartInfoFile;

    @com.android.internal.annotations.VisibleForTesting
    java.io.File mProcStartStoreDir;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.VisibleForTesting
    final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    boolean mEnabled = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Runnable mAppStartInfoPersistTask = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAppStartInfoPersistTimestamp = 0;

    @com.android.internal.annotations.VisibleForTesting
    java.util.concurrent.atomic.AtomicBoolean mAppStartInfoLoaded = new java.util.concurrent.atomic.AtomicBoolean();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.ArrayList<android.app.ApplicationStartInfo> mTmpStartInfoList = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<java.lang.Long, android.app.ApplicationStartInfo> mInProgRecords = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback>> mCallbacks = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.internal.app.ProcessMap<com.android.server.am.AppStartInfoTracker.AppStartInfoContainer> mData = new com.android.internal.app.ProcessMap<>();

    AppStartInfoTracker() {
    }

    void init(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread("ActivityManager:handler", 10, true);
        serviceThread.start();
        this.mHandler = new android.os.Handler(serviceThread.getLooper());
        this.mProcStartStoreDir = new java.io.File(com.android.server.SystemServiceManager.ensureSystemDir(), APP_START_STORE_DIR);
        if (!android.os.FileUtils.createDir(this.mProcStartStoreDir)) {
            android.util.Slog.e(TAG, "Unable to create " + this.mProcStartStoreDir);
            return;
        }
        this.mProcStartInfoFile = new java.io.File(this.mProcStartStoreDir, APP_START_INFO_FILE);
        this.mAppStartInfoHistoryListSize = 16;
    }

    void onSystemReady() {
        this.mEnabled = android.app.Flags.appStartInfo();
        if (!this.mEnabled) {
            return;
        }
        registerForUserRemoval();
        registerForPackageRemoval();
        com.android.server.IoThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppStartInfoTracker.this.lambda$onSystemReady$0();
            }
        });
    }

    void onIntentStarted(@android.annotation.NonNull android.content.Intent intent, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.setIntent(intent);
                    applicationStartInfo.setStartType(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    if (intent != null && intent.getCategories() != null && intent.getCategories().contains("android.intent.category.LAUNCHER")) {
                        applicationStartInfo.setReason(6);
                    } else {
                        applicationStartInfo.setReason(11);
                    }
                    this.mInProgRecords.put(java.lang.Long.valueOf(j), applicationStartInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onIntentFailed(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mInProgRecords.containsKey(java.lang.Long.valueOf(j))) {
                        this.mInProgRecords.get(java.lang.Long.valueOf(j)).setStartupState(1);
                        this.mInProgRecords.remove(java.lang.Long.valueOf(j));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onActivityLaunched(long j, android.content.ComponentName componentName, long j2, com.android.server.am.ProcessRecord processRecord) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mInProgRecords.containsKey(java.lang.Long.valueOf(j))) {
                        if (processRecord != null) {
                            android.app.ApplicationStartInfo applicationStartInfo = this.mInProgRecords.get(java.lang.Long.valueOf(j));
                            applicationStartInfo.setStartType((int) j2);
                            addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                            this.mInProgRecords.put(java.lang.Long.valueOf(j), addStartInfoLocked(applicationStartInfo));
                        } else {
                            this.mInProgRecords.remove(java.lang.Long.valueOf(j));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onActivityLaunchCancelled(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mInProgRecords.containsKey(java.lang.Long.valueOf(j))) {
                        this.mInProgRecords.get(java.lang.Long.valueOf(j)).setStartupState(1);
                        this.mInProgRecords.remove(java.lang.Long.valueOf(j));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onActivityLaunchFinished(long j, android.content.ComponentName componentName, long j2, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mInProgRecords.containsKey(java.lang.Long.valueOf(j))) {
                        android.app.ApplicationStartInfo applicationStartInfo = this.mInProgRecords.get(java.lang.Long.valueOf(j));
                        applicationStartInfo.setStartupState(2);
                        applicationStartInfo.setLaunchMode(i);
                        checkCompletenessAndCallback(applicationStartInfo);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onReportFullyDrawn(long j, long j2) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mInProgRecords.containsKey(java.lang.Long.valueOf(j))) {
                        this.mInProgRecords.get(java.lang.Long.valueOf(j)).addStartupTimestamp(5, j2);
                        this.mInProgRecords.remove(java.lang.Long.valueOf(j));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void handleProcessServiceStart(long j, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    applicationStartInfo.setStartType(z ? 1 : 2);
                    if (serviceRecord.permission != null && serviceRecord.permission.contains("android.permission.BIND_JOB_SERVICE")) {
                        i = 5;
                    } else {
                        i = 10;
                    }
                    applicationStartInfo.setReason(i);
                    if (serviceRecord.intent != null) {
                        applicationStartInfo.setIntent(serviceRecord.intent.getIntent());
                    }
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void handleProcessBroadcastStart(long j, com.android.server.am.ProcessRecord processRecord, com.android.server.am.BroadcastRecord broadcastRecord, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    applicationStartInfo.setStartType(z ? 1 : 2);
                    if (broadcastRecord == null) {
                        applicationStartInfo.setReason(3);
                    } else if (broadcastRecord.alarm) {
                        applicationStartInfo.setReason(0);
                    } else if (broadcastRecord.pushMessage || broadcastRecord.pushMessageOverQuota) {
                        applicationStartInfo.setReason(9);
                    } else if ("android.intent.action.BOOT_COMPLETED".equals(broadcastRecord.intent.getAction())) {
                        applicationStartInfo.setReason(2);
                    } else {
                        applicationStartInfo.setReason(3);
                    }
                    applicationStartInfo.setIntent(broadcastRecord != null ? broadcastRecord.intent : null);
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void handleProcessContentProviderStart(long j, com.android.server.am.ProcessRecord processRecord, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    applicationStartInfo.setStartType(z ? 1 : 2);
                    applicationStartInfo.setReason(4);
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void handleProcessBackupStart(long j, com.android.server.am.ProcessRecord processRecord, com.android.server.am.BackupRecord backupRecord, boolean z) {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    if (z) {
                        i = 1;
                    } else {
                        i = 2;
                    }
                    applicationStartInfo.setStartType(i);
                    applicationStartInfo.setReason(1);
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void addBaseFieldsFromProcessRecord(android.app.ApplicationStartInfo applicationStartInfo, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord == null) {
            return;
        }
        int definingUid = processRecord.getHostingRecord() != null ? processRecord.getHostingRecord().getDefiningUid() : 0;
        applicationStartInfo.setPid(processRecord.getPid());
        applicationStartInfo.setRealUid(processRecord.uid);
        applicationStartInfo.setPackageUid(processRecord.info.uid);
        if (definingUid <= 0) {
            definingUid = processRecord.info.uid;
        }
        applicationStartInfo.setDefiningUid(definingUid);
        applicationStartInfo.setProcessName(processRecord.processName);
        applicationStartInfo.setPackageName(processRecord.info.packageName);
        if (android.content.pm.Flags.stayStopped()) {
            applicationStartInfo.setForceStopped(processRecord.wasForceStopped());
        }
    }

    void reportApplicationOnCreateTimeNanos(com.android.server.am.ProcessRecord processRecord, long j) {
        if (!this.mEnabled) {
            return;
        }
        addTimestampToStart(processRecord, j, 2);
    }

    public void reportBindApplicationTimeNanos(com.android.server.am.ProcessRecord processRecord, long j) {
        addTimestampToStart(processRecord, j, 3);
    }

    void reportFirstFrameTimeNanos(com.android.server.am.ProcessRecord processRecord, long j) {
        if (!this.mEnabled) {
            return;
        }
        addTimestampToStart(processRecord, j, 4);
    }

    void reportFullyDrawnTimeNanos(com.android.server.am.ProcessRecord processRecord, long j) {
        if (!this.mEnabled) {
            return;
        }
        addTimestampToStart(processRecord, j, 5);
    }

    void reportFullyDrawnTimeNanos(java.lang.String str, int i, long j) {
        if (!this.mEnabled) {
            return;
        }
        addTimestampToStart(str, i, j, 5);
    }

    private void addTimestampToStart(com.android.server.am.ProcessRecord processRecord, long j, int i) {
        addTimestampToStart(processRecord.info.packageName, processRecord.uid, j, i);
    }

    private void addTimestampToStart(java.lang.String str, int i, long j, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.am.AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = (com.android.server.am.AppStartInfoTracker.AppStartInfoContainer) this.mData.get(str, i);
                if (appStartInfoContainer == null) {
                    return;
                }
                appStartInfoContainer.addTimestampToStartLocked(i2, j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.ApplicationStartInfo addStartInfoLocked(android.app.ApplicationStartInfo applicationStartInfo) {
        if (!this.mAppStartInfoLoaded.get()) {
            android.util.Slog.w(TAG, "Skipping saving the start info due to ongoing loading from storage");
            return null;
        }
        android.app.ApplicationStartInfo applicationStartInfo2 = new android.app.ApplicationStartInfo(applicationStartInfo);
        com.android.server.am.AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = (com.android.server.am.AppStartInfoTracker.AppStartInfoContainer) this.mData.get(applicationStartInfo.getPackageName(), applicationStartInfo.getRealUid());
        if (appStartInfoContainer == null) {
            appStartInfoContainer = new com.android.server.am.AppStartInfoTracker.AppStartInfoContainer(this.mAppStartInfoHistoryListSize);
            appStartInfoContainer.mUid = applicationStartInfo2.getRealUid();
            this.mData.put(applicationStartInfo.getPackageName(), applicationStartInfo.getRealUid(), appStartInfoContainer);
        }
        appStartInfoContainer.addStartInfoLocked(applicationStartInfo2);
        schedulePersistProcessStartInfo(false);
        return applicationStartInfo2;
    }

    private void checkCompletenessAndCallback(android.app.ApplicationStartInfo applicationStartInfo) {
        synchronized (this.mLock) {
            try {
                if (applicationStartInfo.getStartupState() == 2) {
                    java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback> arrayList = this.mCallbacks.get(applicationStartInfo.getRealUid());
                    if (arrayList == null) {
                        return;
                    }
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        if (arrayList.get(i) != null) {
                            arrayList.get(i).onApplicationStartInfoComplete(applicationStartInfo);
                        }
                    }
                    this.mCallbacks.remove(applicationStartInfo.getRealUid());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void getStartInfo(java.lang.String str, final int i, int i2, int i3, java.util.ArrayList<android.app.ApplicationStartInfo> arrayList) {
        if (!this.mEnabled) {
            return;
        }
        if (i3 == 0) {
            i3 = 16;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    if (!android.text.TextUtils.isEmpty(str)) {
                        com.android.server.am.AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = (com.android.server.am.AppStartInfoTracker.AppStartInfoContainer) this.mData.get(str, i);
                        if (appStartInfoContainer != null) {
                            appStartInfoContainer.getStartInfoLocked(i2, i3, arrayList);
                        }
                    } else {
                        final java.util.ArrayList<android.app.ApplicationStartInfo> arrayList2 = this.mTmpStartInfoList;
                        arrayList2.clear();
                        forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiFunction
                            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                                java.lang.Integer lambda$getStartInfo$1;
                                lambda$getStartInfo$1 = com.android.server.am.AppStartInfoTracker.lambda$getStartInfo$1(i, arrayList2, (java.lang.String) obj, (android.util.SparseArray) obj2);
                                return lambda$getStartInfo$1;
                            }
                        });
                        java.util.Collections.sort(arrayList2, new java.util.Comparator() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda1
                            @Override // java.util.Comparator
                            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                                int lambda$getStartInfo$2;
                                lambda$getStartInfo$2 = com.android.server.am.AppStartInfoTracker.lambda$getStartInfo$2((android.app.ApplicationStartInfo) obj, (android.app.ApplicationStartInfo) obj2);
                                return lambda$getStartInfo$2;
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
    public static /* synthetic */ java.lang.Integer lambda$getStartInfo$1(int i, java.util.ArrayList arrayList, java.lang.String str, android.util.SparseArray sparseArray) {
        com.android.server.am.AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = (com.android.server.am.AppStartInfoTracker.AppStartInfoContainer) sparseArray.get(i);
        if (appStartInfoContainer != null) {
            arrayList.addAll(appStartInfoContainer.mInfos);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getStartInfo$2(android.app.ApplicationStartInfo applicationStartInfo, android.app.ApplicationStartInfo applicationStartInfo2) {
        return java.lang.Long.compare(getStartTimestamp(applicationStartInfo2), getStartTimestamp(applicationStartInfo));
    }

    final class ApplicationStartInfoCompleteCallback implements android.os.IBinder.DeathRecipient {
        private final android.app.IApplicationStartInfoCompleteListener mCallback;
        private final int mUid;

        ApplicationStartInfoCompleteCallback(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) {
            this.mCallback = iApplicationStartInfoCompleteListener;
            this.mUid = i;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        void onApplicationStartInfoComplete(android.app.ApplicationStartInfo applicationStartInfo) {
            try {
                this.mCallback.onApplicationStartInfoComplete(applicationStartInfo);
            } catch (android.os.RemoteException e) {
            }
        }

        void unlinkToDeath() {
            this.mCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.am.AppStartInfoTracker.this.removeStartInfoCompleteListener(this.mCallback, this.mUid, false);
        }
    }

    void addStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback> arrayList = this.mCallbacks.get(i);
                    if (arrayList == null) {
                        android.util.SparseArray<java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback>> sparseArray = this.mCallbacks;
                        java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback> arrayList2 = new java.util.ArrayList<>();
                        sparseArray.set(i, arrayList2);
                        arrayList = arrayList2;
                    }
                    arrayList.add(new com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback(iApplicationStartInfoCompleteListener, i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    java.util.ArrayList<com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback> arrayList = this.mCallbacks.get(i);
                    if (arrayList == null) {
                        return;
                    }
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        com.android.server.am.AppStartInfoTracker.ApplicationStartInfoCompleteCallback applicationStartInfoCompleteCallback = arrayList.get(i2);
                        if (applicationStartInfoCompleteCallback.mCallback != iApplicationStartInfoCompleteListener) {
                            i2++;
                        } else if (z) {
                            applicationStartInfoCompleteCallback.unlinkToDeath();
                        }
                    }
                    if (i2 < size) {
                        arrayList.remove(i2);
                    }
                    if (arrayList.isEmpty()) {
                        this.mCallbacks.remove(i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void forEachPackageLocked(java.util.function.BiFunction<java.lang.String, android.util.SparseArray<com.android.server.am.AppStartInfoTracker.AppStartInfoContainer>, java.lang.Integer> biFunction) {
        if (biFunction != null) {
            android.util.ArrayMap map = this.mData.getMap();
            int size = map.size() - 1;
            while (size >= 0) {
                switch (biFunction.apply((java.lang.String) map.keyAt(size), (android.util.SparseArray) map.valueAt(size)).intValue()) {
                    case 1:
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
        android.util.ArrayMap map = this.mData.getMap();
        android.util.SparseArray sparseArray = (android.util.SparseArray) map.get(str);
        if (sparseArray == null) {
            return;
        }
        if (i2 == -1) {
            this.mData.getMap().remove(str);
            return;
        }
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (android.os.UserHandle.getUserId(sparseArray.keyAt(size)) != i2) {
                size--;
            } else {
                sparseArray.removeAt(size);
                break;
            }
        }
        if (sparseArray.size() == 0) {
            map.remove(str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeByUserIdLocked(final int i) {
        if (i == -1) {
            this.mData.getMap().clear();
        } else {
            forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda3
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    java.lang.Integer lambda$removeByUserIdLocked$3;
                    lambda$removeByUserIdLocked$3 = com.android.server.am.AppStartInfoTracker.lambda$removeByUserIdLocked$3(i, (java.lang.String) obj, (android.util.SparseArray) obj2);
                    return lambda$removeByUserIdLocked$3;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$removeByUserIdLocked$3(int i, java.lang.String str, android.util.SparseArray sparseArray) {
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (android.os.UserHandle.getUserId(sparseArray.keyAt(size)) != i) {
                size--;
            } else {
                sparseArray.removeAt(size);
                break;
            }
        }
        return java.lang.Integer.valueOf(sparseArray.size() != 0 ? 0 : 1);
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    removeByUserIdLocked(i);
                    schedulePersistProcessStartInfo(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onPackageRemoved(java.lang.String str, int i, boolean z) {
        if (this.mEnabled && str != null) {
            boolean isEmpty = android.text.TextUtils.isEmpty(this.mService.mPackageManagerInt.getNameForUid(i));
            synchronized (this.mLock) {
                removePackageLocked(str, i, isEmpty, z ? -1 : android.os.UserHandle.getUserId(i));
                schedulePersistProcessStartInfo(true);
            }
        }
    }

    private void registerForUserRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mService.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppStartInfoTracker.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra < 1) {
                    return;
                }
                com.android.server.am.AppStartInfoTracker.this.onUserRemoved(intExtra);
            }
        }, intentFilter, null, this.mHandler);
    }

    private void registerForPackageRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mService.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppStartInfoTracker.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    return;
                }
                com.android.server.am.AppStartInfoTracker.this.onPackageRemoved(intent.getData().getSchemeSpecificPart(), intent.getIntExtra("android.intent.extra.UID", com.android.server.am.ProcessList.INVALID_ADJ), intent.getBooleanExtra("android.intent.extra.REMOVED_FOR_ALL_USERS", false));
            }
        }, intentFilter, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.VisibleForTesting
    /* renamed from: loadExistingProcessStartInfo, reason: merged with bridge method [inline-methods] */
    public void lambda$onSystemReady$0() {
        if (this.mEnabled) {
            if (!this.mProcStartInfoFile.canRead()) {
                this.mAppStartInfoLoaded.set(true);
                return;
            }
            java.io.FileInputStream fileInputStream = null;
            try {
                try {
                    try {
                        fileInputStream = new android.util.AtomicFile(this.mProcStartInfoFile).openRead();
                        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(fileInputStream);
                        for (int nextField = protoInputStream.nextField(); nextField != -1; nextField = protoInputStream.nextField()) {
                            switch (nextField) {
                                case 1:
                                    synchronized (this.mLock) {
                                        this.mLastAppStartInfoPersistTimestamp = protoInputStream.readLong(1112396529665L);
                                    }
                                case 2:
                                    loadPackagesFromProto(protoInputStream, nextField);
                                default:
                            }
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (java.io.IOException e) {
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException | java.lang.IllegalArgumentException | android.util.proto.WireTypeMismatchException | java.lang.ClassNotFoundException e2) {
                    android.util.Slog.w(TAG, "Error in loading historical app start info from persistent storage: " + e2);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
            } catch (java.io.IOException e3) {
            }
            this.mAppStartInfoLoaded.set(true);
        }
    }

    private void loadPackagesFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException, java.lang.ClassNotFoundException {
        long start = protoInputStream.start(j);
        java.lang.String str = "";
        int nextField = protoInputStream.nextField();
        while (nextField != -1) {
            switch (nextField) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    com.android.server.am.AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = new com.android.server.am.AppStartInfoTracker.AppStartInfoContainer(this.mAppStartInfoHistoryListSize);
                    int readFromProto = appStartInfoContainer.readFromProto(protoInputStream, 2246267895810L);
                    synchronized (this.mLock) {
                        this.mData.put(str, readFromProto, appStartInfoContainer);
                    }
                    break;
            }
            nextField = protoInputStream.nextField();
        }
        protoInputStream.end(start);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void persistProcessStartInfo() {
        java.io.FileOutputStream fileOutputStream;
        if (!this.mEnabled) {
            return;
        }
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mProcStartInfoFile);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            fileOutputStream = atomicFile.startWrite();
        } catch (java.io.IOException e) {
            e = e;
            fileOutputStream = null;
        }
        try {
            final android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileOutputStream);
            protoOutputStream.write(1112396529665L, currentTimeMillis);
            synchronized (this.mLock) {
                forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiFunction
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                        java.lang.Integer lambda$persistProcessStartInfo$4;
                        lambda$persistProcessStartInfo$4 = com.android.server.am.AppStartInfoTracker.lambda$persistProcessStartInfo$4(protoOutputStream, (java.lang.String) obj, (android.util.SparseArray) obj2);
                        return lambda$persistProcessStartInfo$4;
                    }
                });
                this.mLastAppStartInfoPersistTimestamp = currentTimeMillis;
            }
            protoOutputStream.flush();
            atomicFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e2) {
            e = e2;
            android.util.Slog.w(TAG, "Unable to write historical app start info into persistent storage: " + e);
            atomicFile.failWrite(fileOutputStream);
            synchronized (this.mLock) {
            }
        }
        synchronized (this.mLock) {
            this.mAppStartInfoPersistTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$persistProcessStartInfo$4(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, android.util.SparseArray sparseArray) {
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1138166333441L, str);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            try {
                ((com.android.server.am.AppStartInfoTracker.AppStartInfoContainer) sparseArray.valueAt(i)).writeToProto(protoOutputStream, 2246267895810L);
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Unable to write app start info into persistentstorage: " + e);
            }
        }
        protoOutputStream.end(start);
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    void schedulePersistProcessStartInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mAppStartInfoPersistTask == null || z) {
                        if (this.mAppStartInfoPersistTask != null) {
                            com.android.server.IoThread.getHandler().removeCallbacks(this.mAppStartInfoPersistTask);
                        }
                        this.mAppStartInfoPersistTask = new java.lang.Runnable() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.am.AppStartInfoTracker.this.persistProcessStartInfo();
                            }
                        };
                        com.android.server.IoThread.getHandler().postDelayed(this.mAppStartInfoPersistTask, z ? 0L : APP_START_INFO_PERSIST_INTERVAL);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearProcessStartInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mAppStartInfoPersistTask != null) {
                        com.android.server.IoThread.getHandler().removeCallbacks(this.mAppStartInfoPersistTask);
                        this.mAppStartInfoPersistTask = null;
                    }
                    if (z && this.mProcStartInfoFile != null) {
                        this.mProcStartInfoFile.delete();
                    }
                    this.mData.getMap().clear();
                    this.mInProgRecords.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearHistoryProcessStartInfo(java.lang.String str, int i) {
        if (!this.mEnabled) {
            return;
        }
        java.util.Optional.empty();
        if (android.text.TextUtils.isEmpty(str)) {
            synchronized (this.mLock) {
                removeByUserIdLocked(i);
            }
        } else {
            int packageUid = this.mService.mPackageManagerInt.getPackageUid(str, 131072L, i);
            java.util.Optional.of(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageUid)));
            synchronized (this.mLock) {
                removePackageLocked(str, packageUid, true, i);
            }
        }
        schedulePersistProcessStartInfo(true);
    }

    void dumpHistoryProcessStartInfo(final java.io.PrintWriter printWriter, java.lang.String str) {
        if (!this.mEnabled) {
            return;
        }
        printWriter.println("ACTIVITY MANAGER LRU PROCESSES (dumpsys activity start-info)");
        final android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat();
        synchronized (this.mLock) {
            try {
                printWriter.println("Last Timestamp of Persistence Into Persistent Storage: " + simpleDateFormat.format(new java.util.Date(this.mLastAppStartInfoPersistTimestamp)));
                if (android.text.TextUtils.isEmpty(str)) {
                    forEachPackageLocked(new java.util.function.BiFunction() { // from class: com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda6
                        @Override // java.util.function.BiFunction
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                            java.lang.Integer lambda$dumpHistoryProcessStartInfo$5;
                            lambda$dumpHistoryProcessStartInfo$5 = com.android.server.am.AppStartInfoTracker.this.lambda$dumpHistoryProcessStartInfo$5(printWriter, simpleDateFormat, (java.lang.String) obj, (android.util.SparseArray) obj2);
                            return lambda$dumpHistoryProcessStartInfo$5;
                        }
                    });
                } else {
                    android.util.SparseArray<com.android.server.am.AppStartInfoTracker.AppStartInfoContainer> sparseArray = (android.util.SparseArray) this.mData.getMap().get(str);
                    if (sparseArray != null) {
                        dumpHistoryProcessStartInfoLocked(printWriter, "  ", str, sparseArray, simpleDateFormat);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$dumpHistoryProcessStartInfo$5(java.io.PrintWriter printWriter, android.icu.text.SimpleDateFormat simpleDateFormat, java.lang.String str, android.util.SparseArray sparseArray) {
        dumpHistoryProcessStartInfoLocked(printWriter, "  ", str, sparseArray, simpleDateFormat);
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpHistoryProcessStartInfoLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.util.SparseArray<com.android.server.am.AppStartInfoTracker.AppStartInfoContainer> sparseArray, android.icu.text.SimpleDateFormat simpleDateFormat) {
        printWriter.println(str + "package: " + str2);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.println(str + "  Historical Process Start for userId=" + sparseArray.keyAt(i));
            sparseArray.valueAt(i).dumpLocked(printWriter, str + "    ", simpleDateFormat);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getStartTimestamp(android.app.ApplicationStartInfo applicationStartInfo) {
        if (applicationStartInfo.getStartupTimestamps() == null || !applicationStartInfo.getStartupTimestamps().containsKey(0)) {
            return -1L;
        }
        return ((java.lang.Long) applicationStartInfo.getStartupTimestamps().get(0)).longValue();
    }

    final class AppStartInfoContainer {
        private java.util.List<android.app.ApplicationStartInfo> mInfos = new java.util.ArrayList();
        private int mMaxCapacity;
        private int mUid;

        AppStartInfoContainer(int i) {
            this.mMaxCapacity = i;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void getStartInfoLocked(int i, int i2, java.util.ArrayList<android.app.ApplicationStartInfo> arrayList) {
            arrayList.addAll(this.mInfos.size() <= i2 ? 0 : this.mInfos.size() - i2, this.mInfos);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addStartInfoLocked(android.app.ApplicationStartInfo applicationStartInfo) {
            int size = this.mInfos.size();
            if (size >= this.mMaxCapacity) {
                int i = -1;
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                for (int i2 = 0; i2 < size; i2++) {
                    android.app.ApplicationStartInfo applicationStartInfo2 = this.mInfos.get(i2);
                    if (com.android.server.am.AppStartInfoTracker.getStartTimestamp(applicationStartInfo2) < j) {
                        j = com.android.server.am.AppStartInfoTracker.getStartTimestamp(applicationStartInfo2);
                        i = i2;
                    }
                }
                if (i >= 0) {
                    this.mInfos.remove(i);
                }
            }
            this.mInfos.add(applicationStartInfo);
            java.util.Collections.sort(this.mInfos, new java.util.Comparator() { // from class: com.android.server.am.AppStartInfoTracker$AppStartInfoContainer$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$addStartInfoLocked$0;
                    lambda$addStartInfoLocked$0 = com.android.server.am.AppStartInfoTracker.AppStartInfoContainer.lambda$addStartInfoLocked$0((android.app.ApplicationStartInfo) obj, (android.app.ApplicationStartInfo) obj2);
                    return lambda$addStartInfoLocked$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$addStartInfoLocked$0(android.app.ApplicationStartInfo applicationStartInfo, android.app.ApplicationStartInfo applicationStartInfo2) {
            return java.lang.Long.compare(com.android.server.am.AppStartInfoTracker.getStartTimestamp(applicationStartInfo2), com.android.server.am.AppStartInfoTracker.getStartTimestamp(applicationStartInfo));
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addTimestampToStartLocked(int i, long j) {
            int size = this.mInfos.size() - 1;
            if (this.mInfos.get(size).getStartupState() == 0 || i == 5) {
                this.mInfos.get(size).addStartupTimestamp(i, j);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str, android.icu.text.SimpleDateFormat simpleDateFormat) {
            int size = this.mInfos.size();
            for (int i = 0; i < size; i++) {
                this.mInfos.get(i).dump(printWriter, str + "  ", "#" + i, simpleDateFormat);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void writeToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) throws java.io.IOException {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            int size = this.mInfos.size();
            for (int i = 0; i < size; i++) {
                this.mInfos.get(i).writeToProto(protoOutputStream, 2246267895810L);
            }
            protoOutputStream.end(start);
        }

        int readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException, java.lang.ClassNotFoundException {
            long start = protoInputStream.start(j);
            int nextField = protoInputStream.nextField();
            while (nextField != -1) {
                switch (nextField) {
                    case 1:
                        this.mUid = protoInputStream.readInt(1120986464257L);
                        break;
                    case 2:
                        android.app.ApplicationStartInfo applicationStartInfo = new android.app.ApplicationStartInfo();
                        applicationStartInfo.readFromProto(protoInputStream, 2246267895810L);
                        this.mInfos.add(applicationStartInfo);
                        break;
                }
                nextField = protoInputStream.nextField();
            }
            protoInputStream.end(start);
            return this.mUid;
        }
    }
}
