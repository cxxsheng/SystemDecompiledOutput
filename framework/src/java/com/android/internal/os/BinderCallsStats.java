package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderCallsStats implements com.android.internal.os.BinderInternal.Observer {
    private static final int CALL_SESSIONS_POOL_SIZE = 100;
    private static final int CALL_STATS_OBSERVER_DEBOUNCE_MILLIS = 5000;
    private static final java.lang.String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_COLLECT_LATENCY_DATA = true;
    public static final boolean DEFAULT_IGNORE_BATTERY_STATUS = false;
    public static final boolean DEFAULT_TRACK_DIRECT_CALLING_UID = true;
    public static final boolean DEFAULT_TRACK_SCREEN_INTERACTIVE = false;
    public static final boolean DETAILED_TRACKING_DEFAULT = true;
    public static final boolean ENABLED_DEFAULT = true;
    private static final java.lang.String EXCEPTION_COUNT_OVERFLOW_NAME = "overflow";
    public static final int MAX_BINDER_CALL_STATS_COUNT_DEFAULT = 1500;
    private static final int MAX_EXCEPTION_COUNT_SIZE = 50;
    private static final java.lang.Class<? extends android.os.Binder> OVERFLOW_BINDER = com.android.internal.os.BinderCallsStats.OverflowBinder.class;
    private static final int OVERFLOW_DIRECT_CALLING_UID = -1;
    private static final boolean OVERFLOW_SCREEN_INTERACTIVE = false;
    private static final int OVERFLOW_TRANSACTION_CODE = -1;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 1000;
    public static final int SHARDING_MODULO_DEFAULT = 1;
    private static final java.lang.String TAG = "BinderCallsStats";
    private boolean mAddDebugEntries;
    private com.android.internal.os.CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private final java.util.Queue<com.android.internal.os.BinderInternal.CallSession> mCallSessionsPool;
    private long mCallStatsCount;
    private com.android.internal.os.BinderInternal.CallStatsObserver mCallStatsObserver;
    private final android.os.Handler mCallStatsObserverHandler;
    private java.lang.Runnable mCallStatsObserverRunnable;
    private boolean mCollectLatencyData;
    private boolean mDetailedTracking;
    private com.android.internal.os.CachedDeviceState.Readonly mDeviceState;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mExceptionCounts;
    private boolean mIgnoreBatteryStatus;
    private com.android.internal.os.BinderLatencyObserver mLatencyObserver;
    private final java.lang.Object mLock;
    private int mMaxBinderCallStatsCount;
    private volatile android.util.IntArray mNativeTids;
    private final java.lang.Object mNativeTidsLock;
    private int mPeriodicSamplingInterval;
    private final java.util.Random mRandom;
    private boolean mRecordingAllTransactionsForUid;
    private android.util.ArraySet<java.lang.Integer> mSendUidsToObserver;
    private int mShardingModulo;
    private int mShardingOffset;
    private long mStartCurrentTime;
    private long mStartElapsedTime;
    private boolean mTrackDirectCallingUid;
    private boolean mTrackScreenInteractive;
    private final android.util.SparseArray<com.android.internal.os.BinderCallsStats.UidEntry> mUidEntries;

    public static class ExportedCallStat {
        java.lang.Class<? extends android.os.Binder> binderClass;
        public long callCount;
        public int callingUid;
        public java.lang.String className;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public java.lang.String methodName;
        public long recordedCallCount;
        public boolean screenInteractive;
        int transactionCode;
        public int workSourceUid;
    }

    private static class OverflowBinder extends android.os.Binder {
        private OverflowBinder() {
        }
    }

    public static class Injector {
        public java.util.Random getRandomGenerator() {
            return new java.util.Random();
        }

        public android.os.Handler getHandler() {
            return new android.os.Handler(android.os.Looper.getMainLooper());
        }

        public com.android.internal.os.BinderLatencyObserver getLatencyObserver(int i) {
            return new com.android.internal.os.BinderLatencyObserver(new com.android.internal.os.BinderLatencyObserver.Injector(), i);
        }
    }

    public BinderCallsStats(com.android.internal.os.BinderCallsStats.Injector injector) {
        this(injector, 1);
    }

    public BinderCallsStats(com.android.internal.os.BinderCallsStats.Injector injector, int i) {
        this.mDetailedTracking = true;
        this.mPeriodicSamplingInterval = 1000;
        this.mMaxBinderCallStatsCount = 1500;
        this.mUidEntries = new android.util.SparseArray<>();
        this.mExceptionCounts = new android.util.ArrayMap<>();
        this.mCallSessionsPool = new java.util.concurrent.ConcurrentLinkedQueue();
        this.mLock = new java.lang.Object();
        this.mStartCurrentTime = java.lang.System.currentTimeMillis();
        this.mStartElapsedTime = android.os.SystemClock.elapsedRealtime();
        this.mCallStatsCount = 0L;
        this.mAddDebugEntries = false;
        this.mTrackDirectCallingUid = true;
        this.mTrackScreenInteractive = false;
        this.mIgnoreBatteryStatus = false;
        this.mCollectLatencyData = true;
        this.mShardingModulo = 1;
        this.mSendUidsToObserver = new android.util.ArraySet<>(32);
        this.mCallStatsObserverRunnable = new java.lang.Runnable() { // from class: com.android.internal.os.BinderCallsStats.1
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.internal.os.BinderCallsStats.this.mCallStatsObserver == null) {
                    return;
                }
                com.android.internal.os.BinderCallsStats.this.noteCallsStatsDelayed();
                synchronized (com.android.internal.os.BinderCallsStats.this.mLock) {
                    int size = com.android.internal.os.BinderCallsStats.this.mSendUidsToObserver.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.internal.os.BinderCallsStats.UidEntry uidEntry = (com.android.internal.os.BinderCallsStats.UidEntry) com.android.internal.os.BinderCallsStats.this.mUidEntries.get(((java.lang.Integer) com.android.internal.os.BinderCallsStats.this.mSendUidsToObserver.valueAt(i2)).intValue());
                        if (uidEntry != null) {
                            android.util.ArrayMap arrayMap = uidEntry.mCallStats;
                            int size2 = arrayMap.size();
                            java.util.ArrayList arrayList = new java.util.ArrayList(size2);
                            for (int i3 = 0; i3 < size2; i3++) {
                                arrayList.add(((com.android.internal.os.BinderCallsStats.CallStat) arrayMap.valueAt(i3)).m6968clone());
                            }
                            com.android.internal.os.BinderCallsStats.this.mCallStatsObserver.noteCallStats(uidEntry.workSourceUid, uidEntry.incrementalCallCount, arrayList);
                            uidEntry.incrementalCallCount = 0L;
                            for (int size3 = arrayMap.size() - 1; size3 >= 0; size3--) {
                                ((com.android.internal.os.BinderCallsStats.CallStat) arrayMap.valueAt(size3)).incrementalCallCount = 0L;
                            }
                        }
                    }
                    com.android.internal.os.BinderCallsStats.this.mSendUidsToObserver.clear();
                }
            }
        };
        this.mNativeTidsLock = new java.lang.Object();
        this.mNativeTids = new android.util.IntArray(0);
        this.mRandom = injector.getRandomGenerator();
        this.mCallStatsObserverHandler = injector.getHandler();
        this.mLatencyObserver = injector.getLatencyObserver(i);
        this.mShardingOffset = this.mRandom.nextInt(this.mShardingModulo);
    }

    public void setDeviceState(com.android.internal.os.CachedDeviceState.Readonly readonly) {
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    public void setCallStatsObserver(com.android.internal.os.BinderInternal.CallStatsObserver callStatsObserver) {
        this.mCallStatsObserver = callStatsObserver;
        noteBinderThreadNativeIds();
        noteCallsStatsDelayed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteCallsStatsDelayed() {
        this.mCallStatsObserverHandler.removeCallbacks(this.mCallStatsObserverRunnable);
        if (this.mCallStatsObserver != null) {
            this.mCallStatsObserverHandler.postDelayed(this.mCallStatsObserverRunnable, 5000L);
        }
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public com.android.internal.os.BinderInternal.CallSession callStarted(android.os.Binder binder, int i, int i2) {
        noteNativeThreadId();
        boolean canCollect = canCollect();
        if (!this.mCollectLatencyData && !canCollect) {
            return null;
        }
        com.android.internal.os.BinderInternal.CallSession obtainCallSession = obtainCallSession();
        obtainCallSession.binderClass = binder.getClass();
        obtainCallSession.transactionCode = i;
        obtainCallSession.exceptionThrown = false;
        obtainCallSession.cpuTimeStarted = -1L;
        obtainCallSession.timeStarted = -1L;
        obtainCallSession.recordedCall = shouldRecordDetailedData();
        if (canCollect && (this.mRecordingAllTransactionsForUid || obtainCallSession.recordedCall)) {
            obtainCallSession.cpuTimeStarted = getThreadTimeMicro();
            obtainCallSession.timeStarted = getElapsedRealtimeMicro();
        } else if (this.mCollectLatencyData) {
            obtainCallSession.timeStarted = getElapsedRealtimeMicro();
        }
        return obtainCallSession;
    }

    private com.android.internal.os.BinderInternal.CallSession obtainCallSession() {
        com.android.internal.os.BinderInternal.CallSession poll = this.mCallSessionsPool.poll();
        return poll == null ? new com.android.internal.os.BinderInternal.CallSession() : poll;
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public void callEnded(com.android.internal.os.BinderInternal.CallSession callSession, int i, int i2, int i3) {
        if (callSession == null) {
            return;
        }
        processCallEnded(callSession, i, i2, i3);
        if (this.mCallSessionsPool.size() < 100) {
            this.mCallSessionsPool.add(callSession);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void processCallEnded(com.android.internal.os.BinderInternal.CallSession callSession, int i, int i2, int i3) {
        boolean z;
        long j;
        long j2;
        boolean z2;
        int i4;
        java.lang.Object obj;
        com.android.internal.os.BinderInternal.CallSession callSession2 = callSession;
        if (this.mCollectLatencyData) {
            this.mLatencyObserver.callEnded(callSession2);
        }
        if (!canCollect()) {
            return;
        }
        com.android.internal.os.BinderCallsStats.UidEntry uidEntry = null;
        if (callSession2.recordedCall) {
            z = true;
        } else if (this.mRecordingAllTransactionsForUid) {
            uidEntry = getUidEntry(i3);
            z = uidEntry.recordAllTransactions;
        } else {
            z = false;
        }
        if (z) {
            j = getThreadTimeMicro() - callSession2.cpuTimeStarted;
            j2 = getElapsedRealtimeMicro() - callSession2.timeStarted;
        } else {
            j = 0;
            j2 = 0;
        }
        if (this.mTrackScreenInteractive) {
            z2 = this.mDeviceState.isScreenInteractive();
        } else {
            z2 = false;
        }
        if (this.mTrackDirectCallingUid) {
            i4 = getCallingUid();
        } else {
            i4 = -1;
        }
        java.lang.Object obj2 = this.mLock;
        synchronized (obj2) {
            try {
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                if (canCollect()) {
                    if (uidEntry == null) {
                        uidEntry = getUidEntry(i3);
                    }
                    uidEntry.callCount++;
                    uidEntry.incrementalCallCount++;
                    if (z) {
                        uidEntry.cpuTimeMicros += j;
                        uidEntry.recordedCallCount++;
                        java.lang.Class<? extends android.os.Binder> cls = callSession2.binderClass;
                        int i5 = callSession2.transactionCode;
                        obj = obj2;
                        com.android.internal.os.BinderCallsStats.CallStat orCreate = uidEntry.getOrCreate(i4, cls, i5, z2, this.mCallStatsCount >= ((long) this.mMaxBinderCallStatsCount));
                        if (orCreate.callCount == 0) {
                            this.mCallStatsCount++;
                        }
                        orCreate.callCount++;
                        orCreate.incrementalCallCount++;
                        orCreate.recordedCallCount++;
                        orCreate.cpuTimeMicros += j;
                        orCreate.maxCpuTimeMicros = java.lang.Math.max(orCreate.maxCpuTimeMicros, j);
                        orCreate.latencyMicros += j2;
                        orCreate.maxLatencyMicros = java.lang.Math.max(orCreate.maxLatencyMicros, j2);
                        if (this.mDetailedTracking) {
                            orCreate.exceptionCount += callSession.exceptionThrown ? 1L : 0L;
                            orCreate.maxRequestSizeBytes = java.lang.Math.max(orCreate.maxRequestSizeBytes, i);
                            orCreate.maxReplySizeBytes = java.lang.Math.max(orCreate.maxReplySizeBytes, i2);
                        }
                    } else {
                        obj = obj2;
                        com.android.internal.os.BinderCallsStats.CallStat callStat = uidEntry.get(i4, callSession2.binderClass, callSession2.transactionCode, z2);
                        if (callStat != null) {
                            callStat.callCount++;
                            callStat.incrementalCallCount++;
                        }
                    }
                    if (this.mCallStatsObserver != null && !android.os.UserHandle.isCore(i3)) {
                        this.mSendUidsToObserver.add(java.lang.Integer.valueOf(i3));
                    }
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                callSession2 = obj2;
                throw th;
            }
        }
    }

    private boolean shouldExport(com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat, boolean z) {
        if (z) {
            return (((((((exportedCallStat.binderClass.hashCode() * 31) + exportedCallStat.transactionCode) * 31) + exportedCallStat.callingUid) * 31) + (exportedCallStat.screenInteractive ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT)) + this.mShardingOffset) % this.mShardingModulo == 0;
        }
        return true;
    }

    private com.android.internal.os.BinderCallsStats.UidEntry getUidEntry(int i) {
        com.android.internal.os.BinderCallsStats.UidEntry uidEntry = this.mUidEntries.get(i);
        if (uidEntry == null) {
            com.android.internal.os.BinderCallsStats.UidEntry uidEntry2 = new com.android.internal.os.BinderCallsStats.UidEntry(i);
            this.mUidEntries.put(i, uidEntry2);
            return uidEntry2;
        }
        return uidEntry;
    }

    @Override // com.android.internal.os.BinderInternal.Observer
    public void callThrewException(com.android.internal.os.BinderInternal.CallSession callSession, java.lang.Exception exc) {
        if (callSession == null) {
            return;
        }
        int i = 1;
        callSession.exceptionThrown = true;
        try {
            java.lang.String name = exc.getClass().getName();
            synchronized (this.mLock) {
                if (this.mExceptionCounts.size() >= 50) {
                    name = EXCEPTION_COUNT_OVERFLOW_NAME;
                }
                java.lang.Integer num = this.mExceptionCounts.get(name);
                android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = this.mExceptionCounts;
                if (num != null) {
                    i = 1 + num.intValue();
                }
                arrayMap.put(name, java.lang.Integer.valueOf(i));
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.wtf(TAG, "Unexpected exception while updating mExceptionCounts");
        }
    }

    private void noteNativeThreadId() {
        int nativeTid = getNativeTid();
        if (this.mNativeTids.binarySearch(nativeTid) >= 0) {
            return;
        }
        synchronized (this.mNativeTidsLock) {
            android.util.IntArray intArray = this.mNativeTids;
            if (intArray.binarySearch(nativeTid) < 0) {
                android.util.IntArray intArray2 = new android.util.IntArray(intArray.size() + 1);
                intArray2.addAll(intArray);
                intArray2.add((-r3) - 1, nativeTid);
                this.mNativeTids = intArray2;
            }
        }
        noteBinderThreadNativeIds();
    }

    private void noteBinderThreadNativeIds() {
        if (this.mCallStatsObserver == null) {
            return;
        }
        this.mCallStatsObserver.noteBinderThreadNativeIds(getNativeTids());
    }

    private boolean canCollect() {
        if (this.mRecordingAllTransactionsForUid || this.mIgnoreBatteryStatus) {
            return true;
        }
        return (this.mDeviceState == null || this.mDeviceState.isCharging()) ? false : true;
    }

    public java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> getExportedCallStats() {
        return getExportedCallStats(false);
    }

    public java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> getExportedCallStats(boolean z) {
        if (!this.mDetailedTracking) {
            return new java.util.ArrayList<>();
        }
        java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            int size = this.mUidEntries.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.os.BinderCallsStats.UidEntry valueAt = this.mUidEntries.valueAt(i);
                java.util.Iterator<com.android.internal.os.BinderCallsStats.CallStat> it = valueAt.getCallStatsList().iterator();
                while (it.hasNext()) {
                    com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat = getExportedCallStat(valueAt.workSourceUid, it.next());
                    if (shouldExport(exportedCallStat, z)) {
                        arrayList.add(exportedCallStat);
                    }
                }
            }
        }
        resolveBinderMethodNames(arrayList);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(createDebugEntry("end_time_millis", android.os.SystemClock.elapsedRealtime()));
            arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(createDebugEntry(com.android.internal.os.BinderCallsStats.SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mPeriodicSamplingInterval));
            arrayList.add(createDebugEntry(com.android.internal.os.BinderCallsStats.SettingsObserver.SETTINGS_SHARDING_MODULO_KEY, this.mShardingModulo));
        }
        return arrayList;
    }

    public java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> getExportedCallStats(int i) {
        return getExportedCallStats(i, false);
    }

    public java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> getExportedCallStats(int i, boolean z) {
        java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            java.util.Iterator<com.android.internal.os.BinderCallsStats.CallStat> it = getUidEntry(i).getCallStatsList().iterator();
            while (it.hasNext()) {
                com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat = getExportedCallStat(i, it.next());
                if (shouldExport(exportedCallStat, z)) {
                    arrayList.add(exportedCallStat);
                }
            }
        }
        resolveBinderMethodNames(arrayList);
        return arrayList;
    }

    private com.android.internal.os.BinderCallsStats.ExportedCallStat getExportedCallStat(int i, com.android.internal.os.BinderCallsStats.CallStat callStat) {
        com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat = new com.android.internal.os.BinderCallsStats.ExportedCallStat();
        exportedCallStat.workSourceUid = i;
        exportedCallStat.callingUid = callStat.callingUid;
        exportedCallStat.className = callStat.binderClass.getName();
        exportedCallStat.binderClass = callStat.binderClass;
        exportedCallStat.transactionCode = callStat.transactionCode;
        exportedCallStat.screenInteractive = callStat.screenInteractive;
        exportedCallStat.cpuTimeMicros = callStat.cpuTimeMicros;
        exportedCallStat.maxCpuTimeMicros = callStat.maxCpuTimeMicros;
        exportedCallStat.latencyMicros = callStat.latencyMicros;
        exportedCallStat.maxLatencyMicros = callStat.maxLatencyMicros;
        exportedCallStat.recordedCallCount = callStat.recordedCallCount;
        exportedCallStat.callCount = callStat.callCount;
        exportedCallStat.maxRequestSizeBytes = callStat.maxRequestSizeBytes;
        exportedCallStat.maxReplySizeBytes = callStat.maxReplySizeBytes;
        exportedCallStat.exceptionCount = callStat.exceptionCount;
        return exportedCallStat;
    }

    private void resolveBinderMethodNames(java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> arrayList) {
        arrayList.sort(new java.util.Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compareByBinderClassAndCode;
                compareByBinderClassAndCode = com.android.internal.os.BinderCallsStats.compareByBinderClassAndCode((com.android.internal.os.BinderCallsStats.ExportedCallStat) obj, (com.android.internal.os.BinderCallsStats.ExportedCallStat) obj2);
                return compareByBinderClassAndCode;
            }
        });
        com.android.internal.os.BinderTransactionNameResolver binderTransactionNameResolver = new com.android.internal.os.BinderTransactionNameResolver();
        java.util.Iterator<com.android.internal.os.BinderCallsStats.ExportedCallStat> it = arrayList.iterator();
        com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat = null;
        java.lang.String str = null;
        while (it.hasNext()) {
            com.android.internal.os.BinderCallsStats.ExportedCallStat next = it.next();
            boolean z = exportedCallStat == null || !exportedCallStat.className.equals(next.className);
            boolean z2 = exportedCallStat == null || exportedCallStat.transactionCode != next.transactionCode;
            if (z || z2) {
                str = binderTransactionNameResolver.getMethodName(next.binderClass, next.transactionCode);
            }
            next.methodName = str;
            exportedCallStat = next;
        }
    }

    private com.android.internal.os.BinderCallsStats.ExportedCallStat createDebugEntry(java.lang.String str, long j) {
        int myUid = android.os.Process.myUid();
        com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat = new com.android.internal.os.BinderCallsStats.ExportedCallStat();
        exportedCallStat.className = "";
        exportedCallStat.workSourceUid = myUid;
        exportedCallStat.callingUid = myUid;
        exportedCallStat.recordedCallCount = 1L;
        exportedCallStat.callCount = 1L;
        exportedCallStat.methodName = "__DEBUG_" + str;
        exportedCallStat.latencyMicros = j;
        return exportedCallStat;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Integer> getExportedExceptionStats() {
        android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap<>(this.mExceptionCounts);
        }
        return arrayMap;
    }

    public void dump(java.io.PrintWriter printWriter, com.android.internal.os.AppIdToPackageMap appIdToPackageMap, int i, boolean z) {
        synchronized (this.mLock) {
            dumpLocked(printWriter, appIdToPackageMap, i, z);
        }
    }

    private void dumpLocked(java.io.PrintWriter printWriter, com.android.internal.os.AppIdToPackageMap appIdToPackageMap, int i, boolean z) {
        boolean z2;
        java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> exportedCallStats;
        boolean z3;
        long j;
        long j2;
        long j3;
        java.lang.String str;
        if (i == -1) {
            z2 = z;
        } else {
            z2 = true;
        }
        printWriter.print("Start time: ");
        printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mStartCurrentTime));
        printWriter.print("On battery time (ms): ");
        printWriter.println(this.mBatteryStopwatch != null ? this.mBatteryStopwatch.getMillis() : 0L);
        printWriter.println("Sampling interval period: " + this.mPeriodicSamplingInterval);
        printWriter.println("Sharding modulo: " + this.mShardingModulo);
        java.lang.String str2 = z2 ? "" : "(top 90% by cpu time) ";
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        printWriter.println("Per-UID raw data " + str2 + "(package/uid, worksource, call_desc, screen_interactive, cpu_time_micros, max_cpu_time_micros, latency_time_micros, max_latency_time_micros, exception_count, max_request_size_bytes, max_reply_size_bytes, recorded_call_count, call_count):");
        if (i != -1) {
            exportedCallStats = getExportedCallStats(i, true);
        } else {
            exportedCallStats = getExportedCallStats(true);
        }
        exportedCallStats.sort(new java.util.Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compareByCpuDesc;
                compareByCpuDesc = com.android.internal.os.BinderCallsStats.compareByCpuDesc((com.android.internal.os.BinderCallsStats.ExportedCallStat) obj, (com.android.internal.os.BinderCallsStats.ExportedCallStat) obj2);
                return compareByCpuDesc;
            }
        });
        for (com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat : exportedCallStats) {
            if (exportedCallStat.methodName == null || !exportedCallStat.methodName.startsWith("__DEBUG_")) {
                sb.setLength(0);
                sb.append("    ").append(appIdToPackageMap.mapUid(exportedCallStat.callingUid)).append(',').append(appIdToPackageMap.mapUid(exportedCallStat.workSourceUid)).append(',').append(exportedCallStat.className).append('#').append(exportedCallStat.methodName).append(',').append(exportedCallStat.screenInteractive).append(',').append(exportedCallStat.cpuTimeMicros).append(',').append(exportedCallStat.maxCpuTimeMicros).append(',').append(exportedCallStat.latencyMicros).append(',').append(exportedCallStat.maxLatencyMicros).append(',').append(this.mDetailedTracking ? exportedCallStat.exceptionCount : 95L).append(',').append(this.mDetailedTracking ? exportedCallStat.maxRequestSizeBytes : 95L).append(',').append(this.mDetailedTracking ? exportedCallStat.maxReplySizeBytes : 95L).append(',').append(exportedCallStat.recordedCallCount).append(',').append(exportedCallStat.callCount);
                printWriter.println(sb);
            }
        }
        printWriter.println();
        java.util.List<com.android.internal.os.BinderCallsStats.UidEntry> arrayList = new java.util.ArrayList();
        if (i != -1) {
            com.android.internal.os.BinderCallsStats.UidEntry uidEntry = getUidEntry(i);
            arrayList.add(uidEntry);
            j3 = uidEntry.cpuTimeMicros + 0;
            j2 = uidEntry.recordedCallCount + 0;
            j = uidEntry.callCount + 0;
            z3 = z2;
        } else {
            long j4 = 0;
            int size = this.mUidEntries.size();
            int i2 = 0;
            long j5 = 0;
            long j6 = 0;
            while (i2 < size) {
                com.android.internal.os.BinderCallsStats.UidEntry valueAt = this.mUidEntries.valueAt(i2);
                arrayList.add(valueAt);
                j4 += valueAt.cpuTimeMicros;
                j5 += valueAt.recordedCallCount;
                j6 += valueAt.callCount;
                i2++;
                z2 = z2;
            }
            z3 = z2;
            arrayList.sort(java.util.Comparator.comparingDouble(new java.util.function.ToDoubleFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda2
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
                      (r0v0 double) from 0x0006: RETURN (r0v0 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r3) {
                    /*
                        r2 = this;
                        com.android.internal.os.BinderCallsStats$UidEntry r3 = (com.android.internal.os.BinderCallsStats.UidEntry) r3
                        double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$0(r3)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda2.applyAsDouble(java.lang.Object):double");
                }
            }).reversed());
            j = j6;
            j2 = j5;
            j3 = j4;
        }
        printWriter.println("Per-UID Summary " + str2 + "(cpu_time, % of total cpu_time, recorded_call_count, call_count, package/uid):");
        if (z3) {
            str = "";
        } else {
            str = "";
            arrayList = getHighestValues(arrayList, new java.util.function.ToDoubleFunction() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda3
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
                      (r0v0 double) from 0x0006: RETURN (r0v0 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r3) {
                    /*
                        r2 = this;
                        com.android.internal.os.BinderCallsStats$UidEntry r3 = (com.android.internal.os.BinderCallsStats.UidEntry) r3
                        double r0 = com.android.internal.os.BinderCallsStats.lambda$dumpLocked$1(r3)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda3.applyAsDouble(java.lang.Object):double");
                }
            }, 0.9d);
        }
        for (com.android.internal.os.BinderCallsStats.UidEntry uidEntry2 : arrayList) {
            printWriter.println(java.lang.String.format("  %10d %3.0f%% %8d %8d %s", java.lang.Long.valueOf(uidEntry2.cpuTimeMicros), java.lang.Double.valueOf((uidEntry2.cpuTimeMicros * 100.0d) / j3), java.lang.Long.valueOf(uidEntry2.recordedCallCount), java.lang.Long.valueOf(uidEntry2.callCount), appIdToPackageMap.mapUid(uidEntry2.workSourceUid)));
            j2 = j2;
            j = j;
        }
        long j7 = j;
        long j8 = j2;
        printWriter.println();
        if (i == -1) {
            printWriter.println(java.lang.String.format("  Summary: total_cpu_time=%d, calls_count=%d, avg_call_cpu_time=%.0f", java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j7), java.lang.Double.valueOf(j3 / j8)));
            printWriter.println();
        }
        printWriter.println("Exceptions thrown (exception_count, class_name):");
        final java.util.ArrayList<android.util.Pair> arrayList2 = new java.util.ArrayList();
        this.mExceptionCounts.entrySet().iterator().forEachRemaining(new java.util.function.Consumer() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList2.add(android.util.Pair.create((java.lang.String) r2.getKey(), (java.lang.Integer) ((java.util.Map.Entry) obj).getValue()));
            }
        });
        arrayList2.sort(new java.util.Comparator() { // from class: com.android.internal.os.BinderCallsStats$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compare;
                compare = java.lang.Integer.compare(((java.lang.Integer) ((android.util.Pair) obj2).second).intValue(), ((java.lang.Integer) ((android.util.Pair) obj).second).intValue());
                return compare;
            }
        });
        for (android.util.Pair pair : arrayList2) {
            printWriter.println(java.lang.String.format("  %6d %s", pair.second, pair.first));
        }
        if (this.mPeriodicSamplingInterval != 1) {
            printWriter.println(str);
            printWriter.println("/!\\ Displayed data is sampled. See sampling interval at the top.");
        }
    }

    protected long getThreadTimeMicro() {
        return android.os.SystemClock.currentThreadTimeMicro();
    }

    protected int getCallingUid() {
        return android.os.Binder.getCallingUid();
    }

    protected int getNativeTid() {
        return android.os.Process.myTid();
    }

    public int[] getNativeTids() {
        return this.mNativeTids.toArray();
    }

    protected long getElapsedRealtimeMicro() {
        return android.os.SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected boolean shouldRecordDetailedData() {
        return this.mRandom.nextInt(this.mPeriodicSamplingInterval) == 0;
    }

    public void setDetailedTracking(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mDetailedTracking) {
                this.mDetailedTracking = z;
                reset();
            }
        }
    }

    public void setTrackScreenInteractive(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mTrackScreenInteractive) {
                this.mTrackScreenInteractive = z;
                reset();
            }
        }
    }

    public void setTrackDirectCallerUid(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mTrackDirectCallingUid) {
                this.mTrackDirectCallingUid = z;
                reset();
            }
        }
    }

    public void setIgnoreBatteryStatus(boolean z) {
        synchronized (this.mLock) {
            if (z != this.mIgnoreBatteryStatus) {
                this.mIgnoreBatteryStatus = z;
                reset();
            }
        }
    }

    public void recordAllCallsForWorkSourceUid(int i) {
        setDetailedTracking(true);
        android.util.Slog.i(TAG, "Recording all Binder calls for UID: " + i);
        getUidEntry(i).recordAllTransactions = true;
        this.mRecordingAllTransactionsForUid = true;
    }

    public void setAddDebugEntries(boolean z) {
        this.mAddDebugEntries = z;
    }

    public void setMaxBinderCallStats(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid max value (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mMaxBinderCallStatsCount) {
                this.mMaxBinderCallStatsCount = i;
                reset();
            }
        }
    }

    public void setSamplingInterval(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mPeriodicSamplingInterval) {
                this.mPeriodicSamplingInterval = i;
                reset();
            }
        }
    }

    public void setShardingModulo(int i) {
        if (i <= 0) {
            android.util.Slog.w(TAG, "Ignored invalid sharding modulo (value must be positive): " + i);
            return;
        }
        synchronized (this.mLock) {
            if (i != this.mShardingModulo) {
                this.mShardingModulo = i;
                this.mShardingOffset = this.mRandom.nextInt(i);
                reset();
            }
        }
    }

    public void setCollectLatencyData(boolean z) {
        this.mCollectLatencyData = z;
    }

    public boolean getCollectLatencyData() {
        return this.mCollectLatencyData;
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mCallStatsCount = 0L;
            this.mUidEntries.clear();
            this.mExceptionCounts.clear();
            this.mStartCurrentTime = java.lang.System.currentTimeMillis();
            this.mStartElapsedTime = android.os.SystemClock.elapsedRealtime();
            if (this.mBatteryStopwatch != null) {
                this.mBatteryStopwatch.reset();
            }
            this.mRecordingAllTransactionsForUid = false;
        }
    }

    public static class CallStat {
        public final java.lang.Class<? extends android.os.Binder> binderClass;
        public long callCount;
        public final int callingUid;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long incrementalCallCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public long recordedCallCount;
        public final boolean screenInteractive;
        public final int transactionCode;

        public CallStat(int i, java.lang.Class<? extends android.os.Binder> cls, int i2, boolean z) {
            this.callingUid = i;
            this.binderClass = cls;
            this.transactionCode = i2;
            this.screenInteractive = z;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public com.android.internal.os.BinderCallsStats.CallStat m6968clone() {
            com.android.internal.os.BinderCallsStats.CallStat callStat = new com.android.internal.os.BinderCallsStats.CallStat(this.callingUid, this.binderClass, this.transactionCode, this.screenInteractive);
            callStat.recordedCallCount = this.recordedCallCount;
            callStat.callCount = this.callCount;
            callStat.cpuTimeMicros = this.cpuTimeMicros;
            callStat.maxCpuTimeMicros = this.maxCpuTimeMicros;
            callStat.latencyMicros = this.latencyMicros;
            callStat.maxLatencyMicros = this.maxLatencyMicros;
            callStat.maxRequestSizeBytes = this.maxRequestSizeBytes;
            callStat.maxReplySizeBytes = this.maxReplySizeBytes;
            callStat.exceptionCount = this.exceptionCount;
            callStat.incrementalCallCount = this.incrementalCallCount;
            return callStat;
        }

        public java.lang.String toString() {
            return "CallStat{callingUid=" + this.callingUid + ", transaction=" + this.binderClass.getSimpleName() + '.' + new com.android.internal.os.BinderTransactionNameResolver().getMethodName(this.binderClass, this.transactionCode) + ", callCount=" + this.callCount + ", incrementalCallCount=" + this.incrementalCallCount + ", recordedCallCount=" + this.recordedCallCount + ", cpuTimeMicros=" + this.cpuTimeMicros + ", latencyMicros=" + this.latencyMicros + '}';
        }
    }

    public static class CallStatKey {
        public java.lang.Class<? extends android.os.Binder> binderClass;
        public int callingUid;
        private boolean screenInteractive;
        public int transactionCode;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            com.android.internal.os.BinderCallsStats.CallStatKey callStatKey = (com.android.internal.os.BinderCallsStats.CallStatKey) obj;
            return this.callingUid == callStatKey.callingUid && this.transactionCode == callStatKey.transactionCode && this.screenInteractive == callStatKey.screenInteractive && this.binderClass.equals(callStatKey.binderClass);
        }

        public int hashCode() {
            return (((((this.binderClass.hashCode() * 31) + this.transactionCode) * 31) + this.callingUid) * 31) + (this.screenInteractive ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
        }
    }

    public static class UidEntry {
        public long callCount;
        public long cpuTimeMicros;
        public long incrementalCallCount;
        private android.util.ArrayMap<com.android.internal.os.BinderCallsStats.CallStatKey, com.android.internal.os.BinderCallsStats.CallStat> mCallStats = new android.util.ArrayMap<>();
        private com.android.internal.os.BinderCallsStats.CallStatKey mTempKey = new com.android.internal.os.BinderCallsStats.CallStatKey();
        public boolean recordAllTransactions;
        public long recordedCallCount;
        public int workSourceUid;

        UidEntry(int i) {
            this.workSourceUid = i;
        }

        com.android.internal.os.BinderCallsStats.CallStat get(int i, java.lang.Class<? extends android.os.Binder> cls, int i2, boolean z) {
            this.mTempKey.callingUid = i;
            this.mTempKey.binderClass = cls;
            this.mTempKey.transactionCode = i2;
            this.mTempKey.screenInteractive = z;
            return this.mCallStats.get(this.mTempKey);
        }

        com.android.internal.os.BinderCallsStats.CallStat getOrCreate(int i, java.lang.Class<? extends android.os.Binder> cls, int i2, boolean z, boolean z2) {
            com.android.internal.os.BinderCallsStats.CallStat callStat = get(i, cls, i2, z);
            if (callStat == null) {
                if (z2) {
                    z = false;
                    com.android.internal.os.BinderCallsStats.CallStat callStat2 = get(-1, com.android.internal.os.BinderCallsStats.OVERFLOW_BINDER, -1, false);
                    if (callStat2 != null) {
                        return callStat2;
                    }
                    i2 = -1;
                    cls = com.android.internal.os.BinderCallsStats.OVERFLOW_BINDER;
                    i = -1;
                }
                com.android.internal.os.BinderCallsStats.CallStat callStat3 = new com.android.internal.os.BinderCallsStats.CallStat(i, cls, i2, z);
                com.android.internal.os.BinderCallsStats.CallStatKey callStatKey = new com.android.internal.os.BinderCallsStats.CallStatKey();
                callStatKey.callingUid = i;
                callStatKey.binderClass = cls;
                callStatKey.transactionCode = i2;
                callStatKey.screenInteractive = z;
                this.mCallStats.put(callStatKey, callStat3);
                return callStat3;
            }
            return callStat;
        }

        public java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> getCallStatsList() {
            return this.mCallStats.values();
        }

        public java.lang.String toString() {
            return "UidEntry{cpuTimeMicros=" + this.cpuTimeMicros + ", callCount=" + this.callCount + ", mCallStats=" + this.mCallStats + '}';
        }

        public boolean equals(java.lang.Object obj) {
            return this == obj || this.workSourceUid == ((com.android.internal.os.BinderCallsStats.UidEntry) obj).workSourceUid;
        }

        public int hashCode() {
            return this.workSourceUid;
        }
    }

    public android.util.SparseArray<com.android.internal.os.BinderCallsStats.UidEntry> getUidEntries() {
        return this.mUidEntries;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Integer> getExceptionCounts() {
        return this.mExceptionCounts;
    }

    public com.android.internal.os.BinderLatencyObserver getLatencyObserver() {
        return this.mLatencyObserver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> java.util.List<T> getHighestValues(java.util.List<T> list, java.util.function.ToDoubleFunction<T> toDoubleFunction, double d) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        arrayList.sort(java.util.Comparator.comparingDouble(toDoubleFunction).reversed());
        java.util.Iterator<T> it = list.iterator();
        double d2 = 0.0d;
        double d3 = 0.0d;
        while (it.hasNext()) {
            d3 += toDoubleFunction.applyAsDouble(it.next());
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (java.lang.Object obj : arrayList) {
            if (d2 > d * d3) {
                break;
            }
            arrayList2.add(obj);
            d2 += toDoubleFunction.applyAsDouble(obj);
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByCpuDesc(com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat, com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat2) {
        return java.lang.Long.compare(exportedCallStat2.cpuTimeMicros, exportedCallStat.cpuTimeMicros);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareByBinderClassAndCode(com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat, com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat2) {
        int compareTo = exportedCallStat.className.compareTo(exportedCallStat2.className);
        if (compareTo != 0) {
            return compareTo;
        }
        return java.lang.Integer.compare(exportedCallStat.transactionCode, exportedCallStat2.transactionCode);
    }

    public static void startForBluetooth(android.content.Context context) {
        new com.android.internal.os.BinderCallsStats.SettingsObserver(context, new com.android.internal.os.BinderCallsStats(new com.android.internal.os.BinderCallsStats.Injector(), 3));
    }

    public static class SettingsObserver extends android.database.ContentObserver {
        public static final java.lang.String SETTINGS_COLLECT_LATENCY_DATA_KEY = "collect_latency_data";
        public static final java.lang.String SETTINGS_DETAILED_TRACKING_KEY = "detailed_tracking";
        public static final java.lang.String SETTINGS_ENABLED_KEY = "enabled";
        public static final java.lang.String SETTINGS_IGNORE_BATTERY_STATUS_KEY = "ignore_battery_status";
        public static final java.lang.String SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY = "latency_histogram_bucket_count";
        public static final java.lang.String SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY = "latency_histogram_bucket_scale_factor";
        public static final java.lang.String SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY = "latency_histogram_first_bucket_size";
        public static final java.lang.String SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY = "latency_observer_push_interval_minutes";
        public static final java.lang.String SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY = "latency_observer_sampling_interval";
        public static final java.lang.String SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY = "latency_observer_sharding_modulo";
        public static final java.lang.String SETTINGS_MAX_CALL_STATS_KEY = "max_call_stats_count";
        public static final java.lang.String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
        public static final java.lang.String SETTINGS_SHARDING_MODULO_KEY = "sharding_modulo";
        public static final java.lang.String SETTINGS_TRACK_DIRECT_CALLING_UID_KEY = "track_calling_uid";
        public static final java.lang.String SETTINGS_TRACK_SCREEN_INTERACTIVE_KEY = "track_screen_state";
        public static final java.lang.String SETTINGS_UPLOAD_DATA_KEY = "upload_data";
        private final com.android.internal.os.BinderCallsStats mBinderCallsStats;
        private final android.content.Context mContext;
        private boolean mEnabled;
        private final android.util.KeyValueListParser mParser;
        private final android.net.Uri mUri;

        public SettingsObserver(android.content.Context context, com.android.internal.os.BinderCallsStats binderCallsStats) {
            super(com.android.internal.os.BackgroundThread.getHandler());
            this.mUri = android.provider.Settings.Global.getUriFor(android.provider.Settings.Global.BINDER_CALLS_STATS);
            this.mParser = new android.util.KeyValueListParser(',');
            this.mContext = context;
            context.getContentResolver().registerContentObserver(this.mUri, false, this);
            this.mBinderCallsStats = binderCallsStats;
            onChange();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        void onChange() {
            try {
                this.mParser.setString(android.provider.Settings.Global.getString(this.mContext.getContentResolver(), android.provider.Settings.Global.BINDER_CALLS_STATS));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(com.android.internal.os.BinderCallsStats.TAG, "Bad binder call stats settings", e);
            }
            this.mBinderCallsStats.setDetailedTracking(false);
            this.mBinderCallsStats.setTrackScreenInteractive(false);
            this.mBinderCallsStats.setTrackDirectCallerUid(false);
            this.mBinderCallsStats.setIgnoreBatteryStatus(this.mParser.getBoolean(SETTINGS_IGNORE_BATTERY_STATUS_KEY, false));
            this.mBinderCallsStats.setCollectLatencyData(this.mParser.getBoolean(SETTINGS_COLLECT_LATENCY_DATA_KEY, true));
            configureLatencyObserver(this.mParser, this.mBinderCallsStats.getLatencyObserver());
            boolean z = this.mParser.getBoolean("enabled", true);
            if (this.mEnabled != z) {
                if (z) {
                    android.os.Binder.setObserver(this.mBinderCallsStats);
                } else {
                    android.os.Binder.setObserver(null);
                }
                this.mEnabled = z;
                this.mBinderCallsStats.reset();
                this.mBinderCallsStats.setAddDebugEntries(z);
                this.mBinderCallsStats.getLatencyObserver().reset();
            }
        }

        public static void configureLatencyObserver(android.util.KeyValueListParser keyValueListParser, com.android.internal.os.BinderLatencyObserver binderLatencyObserver) {
            binderLatencyObserver.setSamplingInterval(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_SAMPLING_INTERVAL_KEY, 10));
            binderLatencyObserver.setShardingModulo(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_SHARDING_MODULO_KEY, 1));
            binderLatencyObserver.setHistogramBucketsParams(keyValueListParser.getInt(SETTINGS_LATENCY_HISTOGRAM_BUCKET_COUNT_KEY, 100), keyValueListParser.getInt(SETTINGS_LATENCY_HISTOGRAM_FIRST_BUCKET_SIZE_KEY, 5), keyValueListParser.getFloat(SETTINGS_LATENCY_HISTOGRAM_BUCKET_SCALE_FACTOR_KEY, 1.125f));
            binderLatencyObserver.setPushInterval(keyValueListParser.getInt(SETTINGS_LATENCY_OBSERVER_PUSH_INTERVAL_MINUTES_KEY, 360));
        }
    }
}
