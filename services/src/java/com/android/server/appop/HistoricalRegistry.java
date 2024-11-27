package com.android.server.appop;

/* loaded from: classes.dex */
final class HistoricalRegistry {
    private static final boolean DEBUG = false;
    private static final long DEFAULT_COMPRESSION_STEP = 10;
    private static final int DEFAULT_MODE = 1;
    private static final java.lang.String HISTORY_FILE_SUFFIX = ".xml";
    private static final int MSG_WRITE_PENDING_HISTORY = 1;
    private static final java.lang.String PARAMETER_ASSIGNMENT = "=";
    private static final java.lang.String PARAMETER_DELIMITER = ",";

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private long mBaseSnapshotInterval;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    @android.annotation.Nullable
    private android.app.AppOpsManager.HistoricalOps mCurrentHistoricalOps;

    @android.annotation.NonNull
    private volatile com.android.server.appop.DiscreteRegistry mDiscreteRegistry;

    @android.annotation.NonNull
    private final java.lang.Object mInMemoryLock;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private long mIntervalCompressionMultiplier;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private int mMode;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private long mNextPersistDueTimeMillis;
    private final java.lang.Object mOnDiskLock;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private long mPendingHistoryOffsetMillis;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> mPendingWrites;

    @com.android.internal.annotations.GuardedBy({"mOnDiskLock"})
    private com.android.server.appop.HistoricalRegistry.Persistence mPersistence;
    private static final boolean KEEP_WTF_LOG = android.os.Build.IS_DEBUGGABLE;
    private static final java.lang.String LOG_TAG = com.android.server.appop.HistoricalRegistry.class.getSimpleName();
    private static final long DEFAULT_SNAPSHOT_INTERVAL_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(15);

    HistoricalRegistry(@android.annotation.NonNull java.lang.Object obj) {
        this.mPendingWrites = new java.util.LinkedList<>();
        this.mOnDiskLock = new java.lang.Object();
        this.mMode = 1;
        this.mBaseSnapshotInterval = DEFAULT_SNAPSHOT_INTERVAL_MILLIS;
        this.mIntervalCompressionMultiplier = DEFAULT_COMPRESSION_STEP;
        this.mInMemoryLock = obj;
        this.mDiscreteRegistry = new com.android.server.appop.DiscreteRegistry(obj);
    }

    HistoricalRegistry(@android.annotation.NonNull com.android.server.appop.HistoricalRegistry historicalRegistry) {
        this(historicalRegistry.mInMemoryLock);
        this.mMode = historicalRegistry.mMode;
        this.mBaseSnapshotInterval = historicalRegistry.mBaseSnapshotInterval;
        this.mIntervalCompressionMultiplier = historicalRegistry.mIntervalCompressionMultiplier;
        this.mDiscreteRegistry = historicalRegistry.mDiscreteRegistry;
    }

    void systemReady(@android.annotation.NonNull final android.content.ContentResolver contentResolver) {
        this.mDiscreteRegistry.systemReady();
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("appop_history_parameters"), false, new android.database.ContentObserver(com.android.server.FgThread.getHandler()) { // from class: com.android.server.appop.HistoricalRegistry.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.appop.HistoricalRegistry.this.updateParametersFromSetting(contentResolver);
            }
        });
        updateParametersFromSetting(contentResolver);
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    if (this.mMode != 0) {
                        if (!isPersistenceInitializedMLocked()) {
                            this.mPersistence = new com.android.server.appop.HistoricalRegistry.Persistence(this.mBaseSnapshotInterval, this.mIntervalCompressionMultiplier);
                        }
                        long lastPersistTimeMillisDLocked = this.mPersistence.getLastPersistTimeMillisDLocked();
                        if (lastPersistTimeMillisDLocked > 0) {
                            this.mPendingHistoryOffsetMillis = java.lang.System.currentTimeMillis() - lastPersistTimeMillisDLocked;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private boolean isPersistenceInitializedMLocked() {
        return this.mPersistence != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0051, code lost:
    
        if (r9.equals("intervalMultiplier") != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateParametersFromSetting(@android.annotation.NonNull android.content.ContentResolver contentResolver) {
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, "appop_history_parameters");
        if (string == null) {
            return;
        }
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        for (java.lang.String str4 : string.split(PARAMETER_DELIMITER)) {
            java.lang.String[] split = str4.split(PARAMETER_ASSIGNMENT);
            char c = 2;
            if (split.length == 2) {
                java.lang.String trim = split[0].trim();
                switch (trim.hashCode()) {
                    case -190198682:
                        break;
                    case 3357091:
                        if (trim.equals(com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 245634204:
                        if (trim.equals("baseIntervalMillis")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str = split[1].trim();
                        break;
                    case 1:
                        str2 = split[1].trim();
                        break;
                    case 2:
                        str3 = split[1].trim();
                        break;
                    default:
                        android.util.Slog.w(LOG_TAG, "Unknown parameter: " + str4);
                        break;
                }
            }
        }
        if (str != null && str2 != null && str3 != null) {
            try {
                setHistoryParameters(android.app.AppOpsManager.parseHistoricalMode(str), java.lang.Long.parseLong(str2), java.lang.Integer.parseInt(str3));
                return;
            } catch (java.lang.NumberFormatException e) {
            }
        }
        android.util.Slog.w(LOG_TAG, "Bad value forappop_history_parameters=" + string + " resetting!");
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i2, int i3) {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                printWriter.println();
                printWriter.print(str);
                printWriter.print("History:");
                printWriter.print("  mode=");
                printWriter.println(android.app.AppOpsManager.historicalModeToString(this.mMode));
                com.android.server.appop.HistoricalRegistry.StringDumpVisitor stringDumpVisitor = new com.android.server.appop.HistoricalRegistry.StringDumpVisitor(str + "  ", printWriter, i, str2, str3, i2, i3);
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                android.app.AppOpsManager.HistoricalOps updatedPendingHistoricalOpsMLocked = getUpdatedPendingHistoricalOpsMLocked(currentTimeMillis);
                makeRelativeToEpochStart(updatedPendingHistoricalOpsMLocked, currentTimeMillis);
                updatedPendingHistoricalOpsMLocked.accept(stringDumpVisitor);
                if (!isPersistenceInitializedMLocked()) {
                    android.util.Slog.e(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryDLocked = this.mPersistence.readHistoryDLocked();
                if (readHistoryDLocked != null) {
                    long j = (this.mNextPersistDueTimeMillis - currentTimeMillis) - this.mBaseSnapshotInterval;
                    int size = readHistoryDLocked.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        android.app.AppOpsManager.HistoricalOps historicalOps = readHistoryDLocked.get(i4);
                        historicalOps.offsetBeginAndEndTime(j);
                        makeRelativeToEpochStart(historicalOps, currentTimeMillis);
                        historicalOps.accept(stringDumpVisitor);
                    }
                } else {
                    printWriter.println("  Empty");
                }
            }
        }
    }

    void dumpDiscreteData(@android.annotation.NonNull java.io.PrintWriter printWriter, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str3, int i4) {
        this.mDiscreteRegistry.dump(printWriter, i, str, str2, i2, i3, simpleDateFormat, date, str3, i4);
    }

    int getMode() {
        int i;
        synchronized (this.mInMemoryLock) {
            i = this.mMode;
        }
        return i;
    }

    void getHistoricalOpsFromDiskRaw(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, long j, long j2, int i4, java.lang.String[] strArr2, @android.annotation.NonNull android.os.RemoteCallback remoteCallback) {
        android.app.AppOpsManager.HistoricalOps historicalOps = new android.app.AppOpsManager.HistoricalOps(j, j2);
        if ((i2 & 1) != 0) {
            synchronized (this.mOnDiskLock) {
                try {
                    try {
                        synchronized (this.mInMemoryLock) {
                            try {
                                if (!isPersistenceInitializedMLocked()) {
                                    android.util.Slog.e(LOG_TAG, "Interaction before persistence initialized");
                                    remoteCallback.sendResult(new android.os.Bundle());
                                    return;
                                }
                                this.mPersistence.collectHistoricalOpsDLocked(historicalOps, i, str, str2, strArr, i3, j, j2, i4);
                            } catch (java.lang.Throwable th) {
                                th = th;
                                while (true) {
                                    try {
                                        throw th;
                                    } catch (java.lang.Throwable th2) {
                                        th = th2;
                                    }
                                }
                            }
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
        }
        if ((i2 & 2) != 0) {
            this.mDiscreteRegistry.addFilteredDiscreteOpsToHistoricalOps(historicalOps, j, j2, i3, i, str, strArr, str2, i4, new android.util.ArraySet(strArr2));
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("historical_ops", historicalOps);
        remoteCallback.sendResult(bundle);
    }

    void getHistoricalOps(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, long j, long j2, int i4, @android.annotation.Nullable java.lang.String[] strArr2, @android.annotation.NonNull android.os.RemoteCallback remoteCallback) {
        long j3;
        long j4;
        long j5;
        android.app.AppOpsManager.HistoricalOps historicalOps;
        android.os.Bundle bundle;
        long j6;
        long j7;
        android.os.RemoteCallback remoteCallback2;
        android.app.AppOpsManager.HistoricalOps historicalOps2;
        long j8;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (j2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            j3 = j2;
        } else {
            j3 = currentTimeMillis;
        }
        android.os.Bundle bundle2 = new android.os.Bundle();
        long max = java.lang.Math.max(currentTimeMillis - j3, 0L);
        long max2 = java.lang.Math.max(currentTimeMillis - j, 0L);
        android.app.AppOpsManager.HistoricalOps historicalOps3 = new android.app.AppOpsManager.HistoricalOps(max, max2);
        if ((i2 & 2) == 0) {
            j4 = max2;
            j5 = max;
            historicalOps = historicalOps3;
            bundle = bundle2;
            j6 = j3;
            j7 = currentTimeMillis;
            remoteCallback2 = remoteCallback;
        } else {
            com.android.server.appop.DiscreteRegistry discreteRegistry = this.mDiscreteRegistry;
            j4 = max2;
            android.util.ArraySet arraySet = new android.util.ArraySet(strArr2);
            j5 = max;
            j6 = j3;
            historicalOps = historicalOps3;
            bundle = bundle2;
            j7 = currentTimeMillis;
            remoteCallback2 = remoteCallback;
            discreteRegistry.addFilteredDiscreteOpsToHistoricalOps(historicalOps3, j, j3, i3, i, str, strArr, str2, i4, arraySet);
        }
        if ((i2 & 1) == 0) {
            historicalOps2 = historicalOps;
        } else {
            synchronized (this.mOnDiskLock) {
                synchronized (this.mInMemoryLock) {
                    if (!isPersistenceInitializedMLocked()) {
                        android.util.Slog.e(LOG_TAG, "Interaction before persistence initialized");
                        remoteCallback2.sendResult(new android.os.Bundle());
                        return;
                    }
                    long j9 = j7;
                    android.app.AppOpsManager.HistoricalOps updatedPendingHistoricalOpsMLocked = getUpdatedPendingHistoricalOpsMLocked(j9);
                    if (j5 >= updatedPendingHistoricalOpsMLocked.getEndTimeMillis()) {
                        historicalOps2 = historicalOps;
                        j8 = j9;
                    } else if (j4 <= updatedPendingHistoricalOpsMLocked.getBeginTimeMillis()) {
                        historicalOps2 = historicalOps;
                        j8 = j9;
                    } else {
                        android.app.AppOpsManager.HistoricalOps historicalOps4 = new android.app.AppOpsManager.HistoricalOps(updatedPendingHistoricalOpsMLocked);
                        j8 = j9;
                        historicalOps4.filter(i, str, str2, strArr, i2, i3, j5, j4);
                        historicalOps2 = historicalOps;
                        historicalOps2.merge(historicalOps4);
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList(this.mPendingWrites);
                    this.mPendingWrites.clear();
                    boolean z = j4 > updatedPendingHistoricalOpsMLocked.getEndTimeMillis();
                    if (z) {
                        persistPendingHistory(arrayList);
                        long j10 = (j8 - this.mNextPersistDueTimeMillis) + this.mBaseSnapshotInterval;
                        this.mPersistence.collectHistoricalOpsDLocked(historicalOps2, i, str, str2, strArr, i3, java.lang.Math.max(j5 - j10, 0L), java.lang.Math.max(j4 - j10, 0L), i4);
                    }
                }
            }
        }
        historicalOps2.setBeginAndEndTime(j, j6);
        android.os.Bundle bundle3 = bundle;
        bundle3.putParcelable("historical_ops", historicalOps2);
        remoteCallback.sendResult(bundle3);
    }

    void incrementOpAccessedCount(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, long j, int i5, int i6) {
        synchronized (this.mInMemoryLock) {
            try {
                if (this.mMode == 1) {
                    if (!isPersistenceInitializedMLocked()) {
                        android.util.Slog.v(LOG_TAG, "Interaction before persistence initialized");
                    } else {
                        getUpdatedPendingHistoricalOpsMLocked(java.lang.System.currentTimeMillis()).increaseAccessCount(i, i2, str, str2, i3, i4, 1L);
                        this.mDiscreteRegistry.recordDiscreteAccess(i2, str, i, str2, i4, i3, j, -1L, i5, i6);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void incrementOpRejected(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4) {
        synchronized (this.mInMemoryLock) {
            try {
                if (this.mMode == 1) {
                    if (!isPersistenceInitializedMLocked()) {
                        android.util.Slog.v(LOG_TAG, "Interaction before persistence initialized");
                        return;
                    }
                    getUpdatedPendingHistoricalOpsMLocked(java.lang.System.currentTimeMillis()).increaseRejectCount(i, i2, str, str2, i3, i4, 1L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void increaseOpAccessDuration(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, long j, long j2, int i5, int i6) {
        synchronized (this.mInMemoryLock) {
            try {
                if (this.mMode == 1) {
                    if (!isPersistenceInitializedMLocked()) {
                        android.util.Slog.v(LOG_TAG, "Interaction before persistence initialized");
                    } else {
                        getUpdatedPendingHistoricalOpsMLocked(java.lang.System.currentTimeMillis()).increaseAccessDuration(i, i2, str, str2, i3, i4, j2);
                        this.mDiscreteRegistry.recordDiscreteAccess(i2, str, i, str2, i4, i3, j, j2, i5, i6);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setHistoryParameters(int i, long j, long j2) {
        boolean z;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    android.util.Slog.i(LOG_TAG, "New history parameters: mode:" + android.app.AppOpsManager.historicalModeToString(i) + " baseSnapshotInterval:" + j + " intervalCompressionMultiplier:" + j2);
                    boolean z2 = true;
                    if (this.mMode != i) {
                        this.mMode = i;
                        if (this.mMode == 0) {
                            clearHistoryOnDiskDLocked();
                        }
                        if (this.mMode == 2) {
                            this.mDiscreteRegistry.setDebugMode(true);
                        }
                    }
                    if (this.mBaseSnapshotInterval == j) {
                        z = false;
                    } else {
                        this.mBaseSnapshotInterval = j;
                        z = true;
                    }
                    if (this.mIntervalCompressionMultiplier == j2) {
                        z2 = z;
                    } else {
                        this.mIntervalCompressionMultiplier = j2;
                    }
                    if (z2) {
                        resampleHistoryOnDiskInMemoryDMLocked(0L);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void offsetHistory(long j) {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                if (!isPersistenceInitializedMLocked()) {
                    android.util.Slog.e(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryDLocked = this.mPersistence.readHistoryDLocked();
                clearHistoricalRegistry();
                if (readHistoryDLocked != null) {
                    int size = readHistoryDLocked.size();
                    for (int i = 0; i < size; i++) {
                        readHistoryDLocked.get(i).offsetBeginAndEndTime(j);
                    }
                    if (j < 0) {
                        pruneFutureOps(readHistoryDLocked);
                    }
                    this.mPersistence.persistHistoricalOpsDLocked(readHistoryDLocked);
                }
            }
        }
    }

    void offsetDiscreteHistory(long j) {
        this.mDiscreteRegistry.offsetHistory(j);
    }

    void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) {
        synchronized (this.mInMemoryLock) {
            try {
                if (!isPersistenceInitializedMLocked()) {
                    android.util.Slog.d(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                historicalOps.offsetBeginAndEndTime(this.mBaseSnapshotInterval);
                this.mPendingWrites.offerFirst(historicalOps);
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mPendingWrites);
                this.mPendingWrites.clear();
                persistPendingHistory(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void resampleHistoryOnDiskInMemoryDMLocked(long j) {
        this.mPersistence = new com.android.server.appop.HistoricalRegistry.Persistence(this.mBaseSnapshotInterval, this.mIntervalCompressionMultiplier);
        offsetHistory(j);
    }

    void resetHistoryParameters() {
        if (!isPersistenceInitializedMLocked()) {
            android.util.Slog.d(LOG_TAG, "Interaction before persistence initialized");
        } else {
            setHistoryParameters(1, DEFAULT_SNAPSHOT_INTERVAL_MILLIS, DEFAULT_COMPRESSION_STEP);
            this.mDiscreteRegistry.setDebugMode(false);
        }
    }

    void clearHistory(int i, java.lang.String str) {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                if (!isPersistenceInitializedMLocked()) {
                    android.util.Slog.d(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                if (this.mMode != 1) {
                    return;
                }
                for (int i2 = 0; i2 < this.mPendingWrites.size(); i2++) {
                    this.mPendingWrites.get(i2).clearHistory(i, str);
                }
                getUpdatedPendingHistoricalOpsMLocked(java.lang.System.currentTimeMillis()).clearHistory(i, str);
                this.mPersistence.clearHistoryDLocked(i, str);
                this.mDiscreteRegistry.clearHistory(i, str);
            }
        }
    }

    void writeAndClearDiscreteHistory() {
        this.mDiscreteRegistry.writeAndClearAccessHistory();
    }

    void clearAllHistory() {
        clearHistoricalRegistry();
        this.mDiscreteRegistry.clearHistory();
    }

    void clearHistoricalRegistry() {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                if (!isPersistenceInitializedMLocked()) {
                    android.util.Slog.d(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                clearHistoryOnDiskDLocked();
                this.mNextPersistDueTimeMillis = 0L;
                this.mPendingHistoryOffsetMillis = 0L;
                this.mCurrentHistoricalOps = null;
            }
        }
    }

    private void clearHistoryOnDiskDLocked() {
        com.android.internal.os.BackgroundThread.getHandler().removeMessages(1);
        synchronized (this.mInMemoryLock) {
            this.mCurrentHistoricalOps = null;
            this.mNextPersistDueTimeMillis = java.lang.System.currentTimeMillis();
            this.mPendingWrites.clear();
        }
        com.android.server.appop.HistoricalRegistry.Persistence.clearHistoryDLocked();
    }

    @android.annotation.NonNull
    private android.app.AppOpsManager.HistoricalOps getUpdatedPendingHistoricalOpsMLocked(long j) {
        if (this.mCurrentHistoricalOps != null) {
            long j2 = this.mNextPersistDueTimeMillis - j;
            if (j2 > this.mBaseSnapshotInterval) {
                this.mPendingHistoryOffsetMillis = j2 - this.mBaseSnapshotInterval;
            }
            this.mCurrentHistoricalOps.setEndTime(this.mBaseSnapshotInterval - j2);
            if (j2 > 0) {
                return this.mCurrentHistoricalOps;
            }
            if (this.mCurrentHistoricalOps.isEmpty()) {
                this.mCurrentHistoricalOps.setBeginAndEndTime(0L, 0L);
                this.mNextPersistDueTimeMillis = j + this.mBaseSnapshotInterval;
                return this.mCurrentHistoricalOps;
            }
            this.mCurrentHistoricalOps.offsetBeginAndEndTime(this.mBaseSnapshotInterval);
            this.mCurrentHistoricalOps.setBeginTime(this.mCurrentHistoricalOps.getEndTimeMillis() - this.mBaseSnapshotInterval);
            this.mCurrentHistoricalOps.offsetBeginAndEndTime(java.lang.Math.abs(j2));
            schedulePersistHistoricalOpsMLocked(this.mCurrentHistoricalOps);
        }
        this.mCurrentHistoricalOps = new android.app.AppOpsManager.HistoricalOps(0L, 0L);
        this.mNextPersistDueTimeMillis = j + this.mBaseSnapshotInterval;
        return this.mCurrentHistoricalOps;
    }

    void shutdown() {
        synchronized (this.mInMemoryLock) {
            try {
                if (this.mMode == 0) {
                    return;
                }
                persistPendingHistory();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void persistPendingHistory() {
        java.util.ArrayList arrayList;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    arrayList = new java.util.ArrayList(this.mPendingWrites);
                    this.mPendingWrites.clear();
                    if (this.mPendingHistoryOffsetMillis != 0) {
                        resampleHistoryOnDiskInMemoryDMLocked(this.mPendingHistoryOffsetMillis);
                        this.mPendingHistoryOffsetMillis = 0L;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            persistPendingHistory(arrayList);
        }
        this.mDiscreteRegistry.writeAndClearAccessHistory();
    }

    private void persistPendingHistory(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list) {
        synchronized (this.mOnDiskLock) {
            try {
                com.android.internal.os.BackgroundThread.getHandler().removeMessages(1);
                if (list.isEmpty()) {
                    return;
                }
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    android.app.AppOpsManager.HistoricalOps historicalOps = list.get(i);
                    if (i > 0) {
                        historicalOps.offsetBeginAndEndTime(list.get(i - 1).getBeginTimeMillis());
                    }
                }
                this.mPersistence.persistHistoricalOpsDLocked(list);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void schedulePersistHistoricalOpsMLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps) {
        android.os.Message obtainMessage = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.appop.HistoricalRegistry$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.appop.HistoricalRegistry) obj).persistPendingHistory();
            }
        }, this);
        obtainMessage.what = 1;
        com.android.internal.os.BackgroundThread.getHandler().sendMessage(obtainMessage);
        this.mPendingWrites.offerFirst(historicalOps);
    }

    private static void makeRelativeToEpochStart(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps, long j) {
        historicalOps.setBeginAndEndTime(j - historicalOps.getEndTimeMillis(), j - historicalOps.getBeginTimeMillis());
    }

    private void pruneFutureOps(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            android.app.AppOpsManager.HistoricalOps historicalOps = list.get(size);
            if (historicalOps.getEndTimeMillis() <= this.mBaseSnapshotInterval) {
                list.remove(size);
            } else if (historicalOps.getBeginTimeMillis() < this.mBaseSnapshotInterval) {
                com.android.server.appop.HistoricalRegistry.Persistence.spliceFromBeginning(historicalOps, (historicalOps.getEndTimeMillis() - this.mBaseSnapshotInterval) / historicalOps.getDurationMillis());
            }
        }
    }

    private static final class Persistence {
        private static final java.lang.String ATTR_ACCESS_COUNT = "ac";
        private static final java.lang.String ATTR_ACCESS_DURATION = "du";
        private static final java.lang.String ATTR_BEGIN_TIME = "beg";
        private static final java.lang.String ATTR_END_TIME = "end";
        private static final java.lang.String ATTR_NAME = "na";
        private static final java.lang.String ATTR_OVERFLOW = "ov";
        private static final java.lang.String ATTR_REJECT_COUNT = "rc";
        private static final java.lang.String ATTR_VERSION = "ver";
        private static final int CURRENT_VERSION = 2;
        private static final boolean DEBUG = false;
        private static final java.lang.String TAG_ATTRIBUTION = "ftr";
        private static final java.lang.String TAG_OP = "op";
        private static final java.lang.String TAG_OPS = "ops";
        private static final java.lang.String TAG_PACKAGE = "pkg";
        private static final java.lang.String TAG_STATE = "st";
        private static final java.lang.String TAG_UID = "uid";
        private final long mBaseSnapshotInterval;
        private final long mIntervalCompressionMultiplier;
        private static final java.lang.String LOG_TAG = com.android.server.appop.HistoricalRegistry.Persistence.class.getSimpleName();
        private static final java.lang.String TAG_HISTORY = "history";
        private static final com.android.internal.os.AtomicDirectory sHistoricalAppOpsDir = new com.android.internal.os.AtomicDirectory(new java.io.File(new java.io.File(android.os.Environment.getDataSystemDirectory(), "appops"), TAG_HISTORY));

        Persistence(long j, long j2) {
            this.mBaseSnapshotInterval = j;
            this.mIntervalCompressionMultiplier = j2;
        }

        private java.io.File generateFile(@android.annotation.NonNull java.io.File file, int i) {
            return new java.io.File(file, java.lang.Long.toString(computeGlobalIntervalBeginMillis(i)) + com.android.server.appop.HistoricalRegistry.HISTORY_FILE_SUFFIX);
        }

        void clearHistoryDLocked(int i, java.lang.String str) {
            java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryDLocked = readHistoryDLocked();
            if (readHistoryDLocked == null) {
                return;
            }
            for (int i2 = 0; i2 < readHistoryDLocked.size(); i2++) {
                readHistoryDLocked.get(i2).clearHistory(i, str);
            }
            clearHistoryDLocked();
            persistHistoricalOpsDLocked(readHistoryDLocked);
        }

        static void clearHistoryDLocked() {
            sHistoricalAppOpsDir.delete();
        }

        void persistHistoricalOpsDLocked(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list) {
            try {
                java.io.File startWrite = sHistoricalAppOpsDir.startWrite();
                java.io.File backupDirectory = sHistoricalAppOpsDir.getBackupDirectory();
                handlePersistHistoricalOpsRecursiveDLocked(startWrite, backupDirectory, list, getHistoricalFileNames(backupDirectory), 0);
                sHistoricalAppOpsDir.finishWrite();
            } catch (java.lang.Throwable th) {
                com.android.server.appop.HistoricalRegistry.wtf("Failed to write historical app ops, restoring backup", th, null);
                sHistoricalAppOpsDir.failWrite();
            }
        }

        @android.annotation.Nullable
        java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryRawDLocked() {
            return collectHistoricalOpsBaseDLocked(-1, null, null, null, 0, 0L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 31);
        }

        @android.annotation.Nullable
        java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryDLocked() {
            java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoryRawDLocked = readHistoryRawDLocked();
            if (readHistoryRawDLocked != null) {
                int size = readHistoryRawDLocked.size();
                for (int i = 0; i < size; i++) {
                    readHistoryRawDLocked.get(i).offsetBeginAndEndTime(this.mBaseSnapshotInterval);
                }
            }
            return readHistoryRawDLocked;
        }

        long getLastPersistTimeMillisDLocked() {
            java.io.File file;
            java.lang.Throwable th;
            java.io.File[] listFiles;
            java.io.File file2 = null;
            try {
                file = sHistoricalAppOpsDir.startRead();
            } catch (java.lang.Throwable th2) {
                file = null;
                th = th2;
            }
            try {
                listFiles = file.listFiles();
            } catch (java.lang.Throwable th3) {
                th = th3;
                com.android.server.appop.HistoricalRegistry.wtf("Error reading historical app ops. Deleting history.", th, file);
                sHistoricalAppOpsDir.delete();
                return 0L;
            }
            if (listFiles != null && listFiles.length > 0) {
                for (java.io.File file3 : listFiles) {
                    java.lang.String name = file3.getName();
                    if (name.endsWith(com.android.server.appop.HistoricalRegistry.HISTORY_FILE_SUFFIX)) {
                        if (file2 != null && name.length() >= file2.getName().length()) {
                        }
                        file2 = file3;
                    }
                }
                if (file2 == null) {
                    return 0L;
                }
                return file2.lastModified();
            }
            sHistoricalAppOpsDir.finishRead();
            return 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void collectHistoricalOpsDLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j, long j2, int i3) {
            java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> collectHistoricalOpsBaseDLocked = collectHistoricalOpsBaseDLocked(i, str, str2, strArr, i2, j, j2, i3);
            if (collectHistoricalOpsBaseDLocked != null) {
                int size = collectHistoricalOpsBaseDLocked.size();
                for (int i4 = 0; i4 < size; i4++) {
                    historicalOps.merge(collectHistoricalOpsBaseDLocked.get(i4));
                }
            }
        }

        @android.annotation.Nullable
        private java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> collectHistoricalOpsBaseDLocked(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j, long j2, int i3) {
            java.io.File file;
            java.io.File startRead;
            try {
                startRead = sHistoricalAppOpsDir.startRead();
            } catch (java.lang.Throwable th) {
                th = th;
                file = null;
            }
            try {
                java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> collectHistoricalOpsRecursiveDLocked = collectHistoricalOpsRecursiveDLocked(startRead, i, str, str2, strArr, i2, j, j2, i3, new long[]{0}, null, 0, getHistoricalFileNames(startRead));
                sHistoricalAppOpsDir.finishRead();
                return collectHistoricalOpsRecursiveDLocked;
            } catch (java.lang.Throwable th2) {
                th = th2;
                file = startRead;
                com.android.server.appop.HistoricalRegistry.wtf("Error reading historical app ops. Deleting history.", th, file);
                sHistoricalAppOpsDir.delete();
                return null;
            }
        }

        @android.annotation.Nullable
        private java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> collectHistoricalOpsRecursiveDLocked(@android.annotation.NonNull java.io.File file, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j, long j2, int i3, @android.annotation.NonNull long[] jArr, @android.annotation.Nullable java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> linkedList, int i4, @android.annotation.NonNull java.util.Set<java.lang.String> set) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            long pow = ((long) java.lang.Math.pow(this.mIntervalCompressionMultiplier, i4)) * this.mBaseSnapshotInterval;
            int i5 = i4 + 1;
            long pow2 = this.mBaseSnapshotInterval * ((long) java.lang.Math.pow(this.mIntervalCompressionMultiplier, i5));
            long max = java.lang.Math.max(j - pow, 0L);
            long j3 = j2 - pow;
            java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoricalOpsLocked = readHistoricalOpsLocked(file, pow, pow2, i, str, str2, strArr, i2, max, j3, i3, jArr, i4, set);
            if (readHistoricalOpsLocked != null && readHistoricalOpsLocked.isEmpty()) {
                return linkedList;
            }
            java.util.LinkedList<android.app.AppOpsManager.HistoricalOps> collectHistoricalOpsRecursiveDLocked = collectHistoricalOpsRecursiveDLocked(file, i, str, str2, strArr, i2, max, j3, i3, jArr, linkedList, i5, set);
            if (collectHistoricalOpsRecursiveDLocked != null) {
                int size = collectHistoricalOpsRecursiveDLocked.size();
                for (int i6 = 0; i6 < size; i6++) {
                    collectHistoricalOpsRecursiveDLocked.get(i6).offsetBeginAndEndTime(pow2);
                }
            }
            if (readHistoricalOpsLocked != null) {
                if (collectHistoricalOpsRecursiveDLocked == null) {
                    collectHistoricalOpsRecursiveDLocked = new java.util.LinkedList<>();
                }
                for (int size2 = readHistoricalOpsLocked.size() - 1; size2 >= 0; size2--) {
                    collectHistoricalOpsRecursiveDLocked.offerFirst(readHistoricalOpsLocked.get(size2));
                }
            }
            return collectHistoricalOpsRecursiveDLocked;
        }

        private void handlePersistHistoricalOpsRecursiveDLocked(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.io.File file2, @android.annotation.Nullable java.util.List<android.app.AppOpsManager.HistoricalOps> list, @android.annotation.NonNull java.util.Set<java.lang.String> set, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int i2;
            java.util.Set<java.lang.String> set2;
            com.android.server.appop.HistoricalRegistry.Persistence persistence;
            int i3;
            java.io.File file3;
            java.util.List<android.app.AppOpsManager.HistoricalOps> list2;
            long j;
            android.app.AppOpsManager.HistoricalOps historicalOps;
            long pow = ((long) java.lang.Math.pow(this.mIntervalCompressionMultiplier, i)) * this.mBaseSnapshotInterval;
            int i4 = i + 1;
            long pow2 = ((long) java.lang.Math.pow(this.mIntervalCompressionMultiplier, i4)) * this.mBaseSnapshotInterval;
            if (list == null) {
                i2 = i4;
                set2 = set;
                persistence = this;
                i3 = i;
                file3 = file;
            } else if (list.isEmpty()) {
                i2 = i4;
                set2 = set;
                persistence = this;
                i3 = i;
                file3 = file;
            } else {
                int size = list.size();
                for (int i5 = 0; i5 < size; i5++) {
                    list.get(i5).offsetBeginAndEndTime(-pow);
                }
                long j2 = pow;
                java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoricalOpsLocked = readHistoricalOpsLocked(file2, j2, pow2, -1, null, null, null, 0, Long.MIN_VALUE, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 31, null, i, null);
                if (readHistoricalOpsLocked == null) {
                    list2 = list;
                } else {
                    int size2 = readHistoricalOpsLocked.size();
                    if (size2 <= 0) {
                        list2 = list;
                    } else {
                        list2 = list;
                        long endTimeMillis = list2.get(list.size() - 1).getEndTimeMillis();
                        for (int i6 = 0; i6 < size2; i6++) {
                            readHistoricalOpsLocked.get(i6).offsetBeginAndEndTime(endTimeMillis);
                        }
                    }
                }
                java.util.LinkedList linkedList = new java.util.LinkedList(list2);
                if (readHistoricalOpsLocked != null) {
                    linkedList.addAll(readHistoricalOpsLocked);
                }
                int size3 = linkedList.size();
                long j3 = 0;
                java.util.ArrayList arrayList = null;
                java.util.ArrayList arrayList2 = null;
                int i7 = 0;
                while (i7 < size3) {
                    android.app.AppOpsManager.HistoricalOps historicalOps2 = (android.app.AppOpsManager.HistoricalOps) linkedList.get(i7);
                    if (historicalOps2.getEndTimeMillis() <= pow2) {
                        historicalOps = null;
                        j = j2;
                    } else if (historicalOps2.getBeginTimeMillis() < pow2) {
                        j3 = historicalOps2.getEndTimeMillis() - pow2;
                        j = j2;
                        if (j3 > j) {
                            historicalOps = spliceFromEnd(historicalOps2, j3 / historicalOps2.getDurationMillis());
                            j3 = historicalOps2.getEndTimeMillis() - pow2;
                        } else {
                            historicalOps = null;
                        }
                    } else {
                        j = j2;
                        historicalOps = historicalOps2;
                        historicalOps2 = null;
                    }
                    if (historicalOps2 != null) {
                        if (arrayList2 == null) {
                            arrayList2 = new java.util.ArrayList();
                        }
                        arrayList2.add(historicalOps2);
                    }
                    if (historicalOps != null) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(historicalOps);
                    }
                    i7++;
                    j2 = j;
                }
                long j4 = j2;
                java.io.File generateFile = generateFile(file, i);
                set.remove(generateFile.getName());
                if (arrayList2 != null) {
                    normalizeSnapshotForSlotDuration(arrayList2, j4);
                    writeHistoricalOpsDLocked(arrayList2, j3, generateFile);
                }
                handlePersistHistoricalOpsRecursiveDLocked(file, file2, arrayList, set, i4);
                return;
            }
            if (!set.isEmpty()) {
                java.io.File generateFile2 = persistence.generateFile(file2, i3);
                if (set2.remove(generateFile2.getName())) {
                    java.nio.file.Files.createLink(persistence.generateFile(file3, i3).toPath(), generateFile2.toPath());
                }
                handlePersistHistoricalOpsRecursiveDLocked(file, file2, list, set, i2);
            }
        }

        @android.annotation.Nullable
        private java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoricalOpsLocked(java.io.File file, long j, long j2, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j3, long j4, int i3, @android.annotation.Nullable long[] jArr, int i4, @android.annotation.NonNull java.util.Set<java.lang.String> set) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.io.File generateFile = generateFile(file, i4);
            if (set != null) {
                set.remove(generateFile.getName());
            }
            if (j3 >= j4 || j4 < j) {
                return java.util.Collections.emptyList();
            }
            if (j3 >= j2 + ((j2 - j) / this.mIntervalCompressionMultiplier) + (jArr != null ? jArr[0] : 0L) || !generateFile.exists()) {
                if (set == null || set.isEmpty()) {
                    return java.util.Collections.emptyList();
                }
                return null;
            }
            return readHistoricalOpsLocked(generateFile, i, str, str2, strArr, i2, j3, j4, i3, jArr);
        }

        @android.annotation.Nullable
        private java.util.List<android.app.AppOpsManager.HistoricalOps> readHistoricalOpsLocked(@android.annotation.NonNull java.io.File file, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j, long j2, int i3, @android.annotation.Nullable long[] jArr) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int i4;
            java.util.ArrayList arrayList;
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, TAG_HISTORY);
                    if (resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VERSION) < 2) {
                        throw new java.lang.IllegalStateException("Dropping unsupported history version 1 for file:" + file);
                    }
                    long attributeLong = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_OVERFLOW, 0L);
                    int depth = resolvePullParser.getDepth();
                    java.util.ArrayList arrayList2 = null;
                    while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                        if (!TAG_OPS.equals(resolvePullParser.getName())) {
                            i4 = depth;
                        } else {
                            i4 = depth;
                            android.app.AppOpsManager.HistoricalOps readeHistoricalOpsDLocked = readeHistoricalOpsDLocked(resolvePullParser, i, str, str2, strArr, i2, j, j2, i3, jArr);
                            if (readeHistoricalOpsDLocked != null) {
                                if (readeHistoricalOpsDLocked.isEmpty()) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                                } else {
                                    if (arrayList2 != null) {
                                        arrayList = arrayList2;
                                    } else {
                                        arrayList = new java.util.ArrayList();
                                    }
                                    arrayList.add(readeHistoricalOpsDLocked);
                                    arrayList2 = arrayList;
                                    depth = i4;
                                }
                            }
                        }
                        depth = i4;
                    }
                    if (jArr != null) {
                        jArr[0] = jArr[0] + attributeLong;
                    }
                    fileInputStream.close();
                    return arrayList2;
                } finally {
                }
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.i(LOG_TAG, "No history file: " + file.getName());
                return java.util.Collections.emptyList();
            }
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readeHistoricalOpsDLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, long j, long j2, int i3, @android.annotation.Nullable long[] jArr) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            com.android.modules.utils.TypedXmlPullParser typedXmlPullParser2 = typedXmlPullParser;
            long attributeLong = typedXmlPullParser2.getAttributeLong((java.lang.String) null, ATTR_BEGIN_TIME, 0L) + (jArr != null ? jArr[0] : 0L);
            long attributeLong2 = typedXmlPullParser2.getAttributeLong((java.lang.String) null, ATTR_END_TIME, 0L) + (jArr != null ? jArr[0] : 0L);
            if (j2 < attributeLong) {
                return null;
            }
            if (j > attributeLong2) {
                return new android.app.AppOpsManager.HistoricalOps(0L, 0L);
            }
            long max = java.lang.Math.max(attributeLong, j);
            long min = java.lang.Math.min(attributeLong2, j2);
            double d = (min - max) / (attributeLong2 - attributeLong);
            int depth = typedXmlPullParser.getDepth();
            android.app.AppOpsManager.HistoricalOps historicalOps = null;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser2, depth)) {
                if ("uid".equals(typedXmlPullParser.getName())) {
                    long j3 = min;
                    int i4 = depth;
                    android.app.AppOpsManager.HistoricalOps historicalOps2 = historicalOps;
                    android.app.AppOpsManager.HistoricalOps readHistoricalUidOpsDLocked = readHistoricalUidOpsDLocked(historicalOps, typedXmlPullParser, i, str, str2, strArr, i2, i3, d);
                    if (historicalOps2 != null) {
                        historicalOps = historicalOps2;
                    } else {
                        historicalOps = readHistoricalUidOpsDLocked;
                    }
                    typedXmlPullParser2 = typedXmlPullParser;
                    min = j3;
                    depth = i4;
                } else {
                    typedXmlPullParser2 = typedXmlPullParser;
                }
            }
            long j4 = min;
            android.app.AppOpsManager.HistoricalOps historicalOps3 = historicalOps;
            if (historicalOps3 != null) {
                historicalOps3.setBeginAndEndTime(max, j4);
            }
            return historicalOps3;
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readHistoricalUidOpsDLocked(@android.annotation.Nullable android.app.AppOpsManager.HistoricalOps historicalOps, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, double d) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_NAME);
            if ((i2 & 1) != 0 && i != attributeInt) {
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                return null;
            }
            int depth = typedXmlPullParser.getDepth();
            android.app.AppOpsManager.HistoricalOps historicalOps2 = historicalOps;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (TAG_PACKAGE.equals(typedXmlPullParser.getName())) {
                    android.app.AppOpsManager.HistoricalOps readHistoricalPackageOpsDLocked = readHistoricalPackageOpsDLocked(historicalOps2, attributeInt, typedXmlPullParser, str, str2, strArr, i2, i3, d);
                    if (historicalOps2 == null) {
                        historicalOps2 = readHistoricalPackageOpsDLocked;
                    }
                }
            }
            return historicalOps2;
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readHistoricalPackageOpsDLocked(@android.annotation.Nullable android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, double d) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_NAME);
            if ((i2 & 2) != 0 && !str.equals(readStringAttribute)) {
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                return null;
            }
            int depth = typedXmlPullParser.getDepth();
            android.app.AppOpsManager.HistoricalOps historicalOps2 = historicalOps;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (TAG_ATTRIBUTION.equals(typedXmlPullParser.getName())) {
                    android.app.AppOpsManager.HistoricalOps readHistoricalAttributionOpsDLocked = readHistoricalAttributionOpsDLocked(historicalOps2, i, readStringAttribute, typedXmlPullParser, str2, strArr, i2, i3, d);
                    if (historicalOps2 == null) {
                        historicalOps2 = readHistoricalAttributionOpsDLocked;
                    }
                }
            }
            return historicalOps2;
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readHistoricalAttributionOpsDLocked(@android.annotation.Nullable android.app.AppOpsManager.HistoricalOps historicalOps, int i, java.lang.String str, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, double d) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_NAME);
            if ((i2 & 4) != 0 && !java.util.Objects.equals(str2, readStringAttribute)) {
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                return null;
            }
            int depth = typedXmlPullParser.getDepth();
            android.app.AppOpsManager.HistoricalOps historicalOps2 = historicalOps;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (TAG_OP.equals(typedXmlPullParser.getName())) {
                    android.app.AppOpsManager.HistoricalOps readHistoricalOpDLocked = readHistoricalOpDLocked(historicalOps2, i, str, readStringAttribute, typedXmlPullParser, strArr, i2, i3, d);
                    if (historicalOps2 == null) {
                        historicalOps2 = readHistoricalOpDLocked;
                    }
                }
            }
            return historicalOps2;
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readHistoricalOpDLocked(@android.annotation.Nullable android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.Nullable java.lang.String[] strArr, int i2, int i3, double d) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_NAME);
            if ((i2 & 8) != 0 && !com.android.internal.util.ArrayUtils.contains(strArr, android.app.AppOpsManager.opToPublicName(attributeInt))) {
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                return null;
            }
            int depth = typedXmlPullParser.getDepth();
            android.app.AppOpsManager.HistoricalOps historicalOps2 = historicalOps;
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (TAG_STATE.equals(typedXmlPullParser.getName())) {
                    android.app.AppOpsManager.HistoricalOps readStateDLocked = readStateDLocked(historicalOps2, i, str, str2, attributeInt, typedXmlPullParser, i3, d);
                    if (historicalOps2 == null) {
                        historicalOps2 = readStateDLocked;
                    }
                }
            }
            return historicalOps2;
        }

        @android.annotation.Nullable
        private android.app.AppOpsManager.HistoricalOps readStateDLocked(@android.annotation.Nullable android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i3, double d) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            android.app.AppOpsManager.HistoricalOps historicalOps2;
            long j;
            long j2;
            long j3;
            long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_NAME);
            int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(attributeLong) & i3;
            if (extractFlagsFromKey == 0) {
                return null;
            }
            int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(attributeLong);
            long attributeLong2 = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_ACCESS_COUNT, 0L);
            if (attributeLong2 <= 0) {
                historicalOps2 = historicalOps;
            } else {
                if (java.lang.Double.isNaN(d)) {
                    j3 = attributeLong2;
                } else {
                    j3 = (long) android.app.AppOpsManager.HistoricalOps.round(attributeLong2 * d);
                }
                if (historicalOps != null) {
                    historicalOps2 = historicalOps;
                } else {
                    historicalOps2 = new android.app.AppOpsManager.HistoricalOps(0L, 0L);
                }
                historicalOps2.increaseAccessCount(i2, i, str, str2, extractUidStateFromKey, extractFlagsFromKey, j3);
            }
            long attributeLong3 = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_REJECT_COUNT, 0L);
            if (attributeLong3 > 0) {
                if (java.lang.Double.isNaN(d)) {
                    j2 = attributeLong3;
                } else {
                    j2 = (long) android.app.AppOpsManager.HistoricalOps.round(attributeLong3 * d);
                }
                if (historicalOps2 == null) {
                    historicalOps2 = new android.app.AppOpsManager.HistoricalOps(0L, 0L);
                }
                historicalOps2.increaseRejectCount(i2, i, str, str2, extractUidStateFromKey, extractFlagsFromKey, j2);
            }
            long attributeLong4 = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_ACCESS_DURATION, 0L);
            if (attributeLong4 > 0) {
                if (java.lang.Double.isNaN(d)) {
                    j = attributeLong4;
                } else {
                    j = (long) android.app.AppOpsManager.HistoricalOps.round(attributeLong4 * d);
                }
                if (historicalOps2 == null) {
                    historicalOps2 = new android.app.AppOpsManager.HistoricalOps(0L, 0L);
                }
                historicalOps2.increaseAccessDuration(i2, i, str, str2, extractUidStateFromKey, extractFlagsFromKey, j);
            }
            return historicalOps2;
        }

        private void writeHistoricalOpsDLocked(@android.annotation.Nullable java.util.List<android.app.AppOpsManager.HistoricalOps> list, long j, @android.annotation.NonNull java.io.File file) throws java.io.IOException {
            java.io.FileOutputStream openWrite = sHistoricalAppOpsDir.openWrite(file);
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(openWrite);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, TAG_HISTORY);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_VERSION, 2);
                if (j != 0) {
                    resolveSerializer.attributeLong((java.lang.String) null, ATTR_OVERFLOW, j);
                }
                if (list != null) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        writeHistoricalOpDLocked(list.get(i), resolveSerializer);
                    }
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_HISTORY);
                resolveSerializer.endDocument();
                sHistoricalAppOpsDir.closeWrite(openWrite);
            } catch (java.io.IOException e) {
                sHistoricalAppOpsDir.failWrite(openWrite);
                throw e;
            }
        }

        private void writeHistoricalOpDLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_OPS);
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_BEGIN_TIME, historicalOps.getBeginTimeMillis());
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_END_TIME, historicalOps.getEndTimeMillis());
            int uidCount = historicalOps.getUidCount();
            for (int i = 0; i < uidCount; i++) {
                writeHistoricalUidOpsDLocked(historicalOps.getUidOpsAt(i), typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_OPS);
        }

        private void writeHistoricalUidOpsDLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalUidOps historicalUidOps, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, "uid");
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_NAME, historicalUidOps.getUid());
            int packageCount = historicalUidOps.getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                writeHistoricalPackageOpsDLocked(historicalUidOps.getPackageOpsAt(i), typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, "uid");
        }

        private void writeHistoricalPackageOpsDLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_PACKAGE);
            typedXmlSerializer.attributeInterned((java.lang.String) null, ATTR_NAME, historicalPackageOps.getPackageName());
            int attributedOpsCount = historicalPackageOps.getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                writeHistoricalAttributionOpsDLocked(historicalPackageOps.getAttributedOpsAt(i), typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_PACKAGE);
        }

        private void writeHistoricalAttributionOpsDLocked(@android.annotation.NonNull android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ATTRIBUTION);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_NAME, attributedHistoricalOps.getTag());
            int opCount = attributedHistoricalOps.getOpCount();
            for (int i = 0; i < opCount; i++) {
                writeHistoricalOpDLocked(attributedHistoricalOps.getOpAt(i), typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ATTRIBUTION);
        }

        private void writeHistoricalOpDLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOp historicalOp, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            android.util.LongSparseArray collectKeys = historicalOp.collectKeys();
            if (collectKeys == null || collectKeys.size() <= 0) {
                return;
            }
            typedXmlSerializer.startTag((java.lang.String) null, TAG_OP);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_NAME, historicalOp.getOpCode());
            int size = collectKeys.size();
            for (int i = 0; i < size; i++) {
                writeStateOnLocked(historicalOp, collectKeys.keyAt(i), typedXmlSerializer);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_OP);
        }

        private void writeStateOnLocked(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOp historicalOp, long j, @android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(j);
            int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(j);
            long accessCount = historicalOp.getAccessCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            long rejectCount = historicalOp.getRejectCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            long accessDuration = historicalOp.getAccessDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            if (accessCount <= 0 && rejectCount <= 0 && accessDuration <= 0) {
                return;
            }
            typedXmlSerializer.startTag((java.lang.String) null, TAG_STATE);
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_NAME, j);
            if (accessCount > 0) {
                typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_ACCESS_COUNT, accessCount);
            }
            if (rejectCount > 0) {
                typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_REJECT_COUNT, rejectCount);
            }
            if (accessDuration > 0) {
                typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_ACCESS_DURATION, accessDuration);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_STATE);
        }

        private static void enforceOpsWellFormed(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list) {
            if (list == null) {
                return;
            }
            int size = list.size();
            android.app.AppOpsManager.HistoricalOps historicalOps = null;
            int i = 0;
            while (i < size) {
                android.app.AppOpsManager.HistoricalOps historicalOps2 = list.get(i);
                if (historicalOps2.isEmpty()) {
                    throw new java.lang.IllegalStateException("Empty ops:\n" + opsToDebugString(list));
                }
                if (historicalOps2.getEndTimeMillis() < historicalOps2.getBeginTimeMillis()) {
                    throw new java.lang.IllegalStateException("Begin after end:\n" + opsToDebugString(list));
                }
                if (historicalOps != null) {
                    if (historicalOps.getEndTimeMillis() > historicalOps2.getBeginTimeMillis()) {
                        throw new java.lang.IllegalStateException("Intersecting ops:\n" + opsToDebugString(list));
                    }
                    if (historicalOps.getBeginTimeMillis() > historicalOps2.getBeginTimeMillis()) {
                        throw new java.lang.IllegalStateException("Non increasing ops:\n" + opsToDebugString(list));
                    }
                }
                i++;
                historicalOps = historicalOps2;
            }
        }

        private long computeGlobalIntervalBeginMillis(int i) {
            long j = 0;
            for (int i2 = 0; i2 < i + 1; i2++) {
                j = (long) (j + java.lang.Math.pow(this.mIntervalCompressionMultiplier, i2));
            }
            return j * this.mBaseSnapshotInterval;
        }

        @android.annotation.NonNull
        private static android.app.AppOpsManager.HistoricalOps spliceFromEnd(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps, double d) {
            return historicalOps.spliceFromEnd(d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public static android.app.AppOpsManager.HistoricalOps spliceFromBeginning(@android.annotation.NonNull android.app.AppOpsManager.HistoricalOps historicalOps, double d) {
            return historicalOps.spliceFromBeginning(d);
        }

        private static void normalizeSnapshotForSlotDuration(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list, long j) {
            int size = list.size() - 1;
            while (size >= 0) {
                android.app.AppOpsManager.HistoricalOps historicalOps = list.get(size);
                long max = java.lang.Math.max(historicalOps.getEndTimeMillis() - j, 0L);
                for (int i = size - 1; i >= 0; i--) {
                    android.app.AppOpsManager.HistoricalOps historicalOps2 = list.get(i);
                    long endTimeMillis = historicalOps2.getEndTimeMillis() - java.lang.Math.min(max, historicalOps.getBeginTimeMillis());
                    if (endTimeMillis <= 0) {
                        break;
                    }
                    float durationMillis = endTimeMillis / historicalOps2.getDurationMillis();
                    if (java.lang.Float.compare(durationMillis, 1.0f) >= 0) {
                        list.remove(i);
                        size--;
                        historicalOps.merge(historicalOps2);
                    } else {
                        android.app.AppOpsManager.HistoricalOps spliceFromEnd = spliceFromEnd(historicalOps2, durationMillis);
                        if (spliceFromEnd != null) {
                            historicalOps.merge(spliceFromEnd);
                        }
                        if (historicalOps2.isEmpty()) {
                            list.remove(i);
                            size--;
                        }
                    }
                }
                size--;
            }
        }

        @android.annotation.NonNull
        private static java.lang.String opsToDebugString(@android.annotation.NonNull java.util.List<android.app.AppOpsManager.HistoricalOps> list) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                sb.append("  ");
                sb.append(list.get(i));
                if (i < size - 1) {
                    sb.append('\n');
                }
            }
            return sb.toString();
        }

        private static java.util.Set<java.lang.String> getHistoricalFileNames(@android.annotation.NonNull java.io.File file) {
            java.io.File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return java.util.Collections.emptySet();
            }
            android.util.ArraySet arraySet = new android.util.ArraySet(listFiles.length);
            for (java.io.File file2 : listFiles) {
                arraySet.add(file2.getName());
            }
            return arraySet;
        }
    }

    private static class HistoricalFilesInvariant {

        @android.annotation.NonNull
        private final java.util.List<java.io.File> mBeginFiles = new java.util.ArrayList();

        private HistoricalFilesInvariant() {
        }

        public void startTracking(@android.annotation.NonNull java.io.File file) {
            java.io.File[] listFiles = file.listFiles();
            if (listFiles != null) {
                java.util.Collections.addAll(this.mBeginFiles, listFiles);
            }
        }

        public void stopTracking(@android.annotation.NonNull java.io.File file) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.io.File[] listFiles = file.listFiles();
            if (listFiles != null) {
                java.util.Collections.addAll(arrayList, listFiles);
            }
            if (getOldestFileOffsetMillis(arrayList) < getOldestFileOffsetMillis(this.mBeginFiles)) {
                java.lang.String str = "History loss detected!\nold files: " + this.mBeginFiles;
                com.android.server.appop.HistoricalRegistry.wtf(str, null, file);
                throw new java.lang.IllegalStateException(str);
            }
        }

        private static long getOldestFileOffsetMillis(@android.annotation.NonNull java.util.List<java.io.File> list) {
            if (list.isEmpty()) {
                return 0L;
            }
            java.lang.String name = list.get(0).getName();
            int size = list.size();
            for (int i = 1; i < size; i++) {
                java.io.File file = list.get(i);
                if (file.getName().length() > name.length()) {
                    name = file.getName();
                }
            }
            return java.lang.Long.parseLong(name.replace(com.android.server.appop.HistoricalRegistry.HISTORY_FILE_SUFFIX, ""));
        }
    }

    private final class StringDumpVisitor implements android.app.AppOpsManager.HistoricalOpsVisitor {

        @android.annotation.NonNull
        private final java.lang.String mAttributionPrefix;

        @android.annotation.NonNull
        private final java.lang.String mEntryPrefix;
        private final int mFilter;
        private final java.lang.String mFilterAttributionTag;
        private final int mFilterOp;
        private final java.lang.String mFilterPackage;
        private final int mFilterUid;

        @android.annotation.NonNull
        private final java.lang.String mOpsPrefix;

        @android.annotation.NonNull
        private final java.lang.String mPackagePrefix;

        @android.annotation.NonNull
        private final java.lang.String mUidPrefix;

        @android.annotation.NonNull
        private final java.lang.String mUidStatePrefix;

        @android.annotation.NonNull
        private final java.io.PrintWriter mWriter;
        private final long mNow = java.lang.System.currentTimeMillis();
        private final java.text.SimpleDateFormat mDateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private final java.util.Date mDate = new java.util.Date();

        StringDumpVisitor(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i2, int i3) {
            this.mOpsPrefix = str + "  ";
            this.mUidPrefix = this.mOpsPrefix + "  ";
            this.mPackagePrefix = this.mUidPrefix + "  ";
            this.mAttributionPrefix = this.mPackagePrefix + "  ";
            this.mEntryPrefix = this.mAttributionPrefix + "  ";
            this.mUidStatePrefix = this.mEntryPrefix + "  ";
            this.mWriter = printWriter;
            this.mFilterUid = i;
            this.mFilterPackage = str2;
            this.mFilterAttributionTag = str3;
            this.mFilterOp = i2;
            this.mFilter = i3;
        }

        public void visitHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) {
            this.mWriter.println();
            this.mWriter.print(this.mOpsPrefix);
            this.mWriter.println("snapshot:");
            this.mWriter.print(this.mUidPrefix);
            this.mWriter.print("begin = ");
            this.mDate.setTime(historicalOps.getBeginTimeMillis());
            this.mWriter.print(this.mDateFormatter.format(this.mDate));
            this.mWriter.print("  (");
            android.util.TimeUtils.formatDuration(historicalOps.getBeginTimeMillis() - this.mNow, this.mWriter);
            this.mWriter.println(")");
            this.mWriter.print(this.mUidPrefix);
            this.mWriter.print("end = ");
            this.mDate.setTime(historicalOps.getEndTimeMillis());
            this.mWriter.print(this.mDateFormatter.format(this.mDate));
            this.mWriter.print("  (");
            android.util.TimeUtils.formatDuration(historicalOps.getEndTimeMillis() - this.mNow, this.mWriter);
            this.mWriter.println(")");
        }

        public void visitHistoricalUidOps(android.app.AppOpsManager.HistoricalUidOps historicalUidOps) {
            if ((this.mFilter & 1) != 0 && this.mFilterUid != historicalUidOps.getUid()) {
                return;
            }
            this.mWriter.println();
            this.mWriter.print(this.mUidPrefix);
            this.mWriter.print("Uid ");
            android.os.UserHandle.formatUid(this.mWriter, historicalUidOps.getUid());
            this.mWriter.println(":");
        }

        public void visitHistoricalPackageOps(android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps) {
            if ((this.mFilter & 2) != 0 && !this.mFilterPackage.equals(historicalPackageOps.getPackageName())) {
                return;
            }
            this.mWriter.print(this.mPackagePrefix);
            this.mWriter.print("Package ");
            this.mWriter.print(historicalPackageOps.getPackageName());
            this.mWriter.println(":");
        }

        public void visitHistoricalAttributionOps(android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
            if ((this.mFilter & 4) != 0 && !java.util.Objects.equals(this.mFilterPackage, attributedHistoricalOps.getTag())) {
                return;
            }
            this.mWriter.print(this.mAttributionPrefix);
            this.mWriter.print("Attribution ");
            this.mWriter.print(attributedHistoricalOps.getTag());
            this.mWriter.println(":");
        }

        public void visitHistoricalOp(android.app.AppOpsManager.HistoricalOp historicalOp) {
            boolean z;
            if ((this.mFilter & 8) == 0 || this.mFilterOp == historicalOp.getOpCode()) {
                this.mWriter.print(this.mEntryPrefix);
                this.mWriter.print(android.app.AppOpsManager.opToName(historicalOp.getOpCode()));
                this.mWriter.println(":");
                android.util.LongSparseArray collectKeys = historicalOp.collectKeys();
                int size = collectKeys.size();
                for (int i = 0; i < size; i++) {
                    long keyAt = collectKeys.keyAt(i);
                    int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(keyAt);
                    int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(keyAt);
                    long accessCount = historicalOp.getAccessCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    boolean z2 = true;
                    if (accessCount > 0) {
                        this.mWriter.print(this.mUidStatePrefix);
                        this.mWriter.print(android.app.AppOpsManager.keyToString(keyAt));
                        this.mWriter.print(" = ");
                        this.mWriter.print("access=");
                        this.mWriter.print(accessCount);
                        z = true;
                    } else {
                        z = false;
                    }
                    long rejectCount = historicalOp.getRejectCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    if (rejectCount > 0) {
                        if (!z) {
                            this.mWriter.print(this.mUidStatePrefix);
                            this.mWriter.print(android.app.AppOpsManager.keyToString(keyAt));
                            this.mWriter.print(" = ");
                            z = true;
                        } else {
                            this.mWriter.print(", ");
                        }
                        this.mWriter.print("reject=");
                        this.mWriter.print(rejectCount);
                    }
                    long accessDuration = historicalOp.getAccessDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    if (accessDuration > 0) {
                        if (!z) {
                            this.mWriter.print(this.mUidStatePrefix);
                            this.mWriter.print(android.app.AppOpsManager.keyToString(keyAt));
                            this.mWriter.print(" = ");
                        } else {
                            this.mWriter.print(", ");
                            z2 = z;
                        }
                        this.mWriter.print("duration=");
                        android.util.TimeUtils.formatDuration(accessDuration, this.mWriter);
                        z = z2;
                    }
                    if (z) {
                        this.mWriter.println("");
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void wtf(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Throwable th, @android.annotation.Nullable java.io.File file) {
        android.util.Slog.wtf(LOG_TAG, str, th);
        if (KEEP_WTF_LOG) {
            try {
                java.io.File file2 = new java.io.File(new java.io.File(android.os.Environment.getDataSystemDirectory(), "appops"), "wtf" + android.util.TimeUtils.formatForLogging(java.lang.System.currentTimeMillis()));
                if (file2.createNewFile()) {
                    java.io.PrintWriter printWriter = new java.io.PrintWriter(file2);
                    if (th != null) {
                        try {
                            printWriter.append('\n').append((java.lang.CharSequence) th.toString());
                        } finally {
                        }
                    }
                    printWriter.append('\n').append((java.lang.CharSequence) android.os.Debug.getCallers(10));
                    if (file != null) {
                        printWriter.append((java.lang.CharSequence) ("\nfiles: " + java.util.Arrays.toString(file.listFiles())));
                    } else {
                        printWriter.append((java.lang.CharSequence) "\nfiles: none");
                    }
                    printWriter.close();
                }
            } catch (java.io.IOException e) {
            }
        }
    }
}
