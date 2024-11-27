package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class BatteryExternalStatsWorker implements com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync {
    private static final boolean DEBUG = false;
    private static final long EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS = 2000;
    private static final long MAX_WIFI_STATS_SAMPLE_ERROR_MILLIS = 750;
    private static final java.lang.String TAG = "BatteryExternalStatsWorker";
    public static final int UID_FINAL_REMOVAL_AFTER_USER_REMOVAL_DELAY_MILLIS = 10000;
    public static final int UID_QUICK_REMOVAL_AFTER_USER_REMOVAL_DELAY_MILLIS = 2000;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> mBatteryLevelSync;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> mCurrentFuture;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.lang.String mCurrentReason;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @android.annotation.Nullable
    private com.android.server.power.stats.EnergyConsumerSnapshot mEnergyConsumerSnapshot;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @android.annotation.Nullable
    private android.util.SparseArray<int[]> mEnergyConsumerTypeToIdMap;
    private final java.util.concurrent.ScheduledExecutorService mExecutorService;
    final com.android.server.power.stats.BatteryExternalStatsWorker.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastCollectionTimeStamp;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private android.os.connectivity.WifiActivityEnergyInfo mLastWifiInfo;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mOnBattery;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mOnBatteryScreenOff;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int[] mPerDisplayScreenStates;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private android.power.PowerStatsInternal mPowerStatsInternal;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> mProcessStateSync;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mScreenState;

    @com.android.internal.annotations.GuardedBy({"mStats"})
    private final com.android.server.power.stats.BatteryStatsImpl mStats;
    private final java.lang.Runnable mSyncTask;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private android.telephony.TelephonyManager mTelephony;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mUpdateFlags;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mUseLatestStates;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> mWakelockChangesUpdate;

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private android.net.wifi.WifiManager mWifiManager;
    private final java.lang.Object mWorkerLock;
    private final java.lang.Runnable mWriteTask;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Thread lambda$new$1(final java.lang.Runnable runnable) {
        java.lang.Thread thread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryExternalStatsWorker.lambda$new$0(runnable);
            }
        }, "batterystats-worker");
        thread.setPriority(5);
        return thread;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(java.lang.Runnable runnable) {
        android.os.ThreadLocalWorkSource.setUid(android.os.Process.myUid());
        runnable.run();
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        private final android.content.Context mContext;

        Injector(android.content.Context context) {
            this.mContext = context;
        }

        public <T> T getSystemService(java.lang.Class<T> cls) {
            return (T) this.mContext.getSystemService(cls);
        }

        public <T> T getLocalService(java.lang.Class<T> cls) {
            return (T) com.android.server.LocalServices.getService(cls);
        }
    }

    public BatteryExternalStatsWorker(android.content.Context context, com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl) {
        this(new com.android.server.power.stats.BatteryExternalStatsWorker.Injector(context), batteryStatsImpl);
    }

    @com.android.internal.annotations.VisibleForTesting
    BatteryExternalStatsWorker(com.android.server.power.stats.BatteryExternalStatsWorker.Injector injector, com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl) {
        this.mExecutorService = java.util.concurrent.Executors.newSingleThreadScheduledExecutor(new java.util.concurrent.ThreadFactory() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final java.lang.Thread newThread(java.lang.Runnable runnable) {
                java.lang.Thread lambda$new$1;
                lambda$new$1 = com.android.server.power.stats.BatteryExternalStatsWorker.lambda$new$1(runnable);
                return lambda$new$1;
            }
        });
        this.mUpdateFlags = 0;
        this.mCurrentFuture = null;
        this.mCurrentReason = null;
        this.mPerDisplayScreenStates = null;
        this.mUseLatestStates = true;
        this.mWorkerLock = new java.lang.Object();
        this.mWifiManager = null;
        this.mTelephony = null;
        this.mPowerStatsInternal = null;
        this.mLastWifiInfo = new android.os.connectivity.WifiActivityEnergyInfo(0L, 0, 0L, 0L, 0L, 0L);
        this.mEnergyConsumerTypeToIdMap = null;
        this.mEnergyConsumerSnapshot = null;
        this.mSyncTask = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.1
            @Override // java.lang.Runnable
            public void run() {
                int i;
                java.lang.String str;
                boolean z;
                boolean z2;
                int i2;
                int[] iArr;
                boolean z3;
                int i3;
                int i4;
                synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this) {
                    try {
                        i = com.android.server.power.stats.BatteryExternalStatsWorker.this.mUpdateFlags;
                        str = com.android.server.power.stats.BatteryExternalStatsWorker.this.mCurrentReason;
                        z = com.android.server.power.stats.BatteryExternalStatsWorker.this.mOnBattery;
                        z2 = com.android.server.power.stats.BatteryExternalStatsWorker.this.mOnBatteryScreenOff;
                        i2 = com.android.server.power.stats.BatteryExternalStatsWorker.this.mScreenState;
                        iArr = com.android.server.power.stats.BatteryExternalStatsWorker.this.mPerDisplayScreenStates;
                        z3 = com.android.server.power.stats.BatteryExternalStatsWorker.this.mUseLatestStates;
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mUpdateFlags = 0;
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mCurrentReason = null;
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mCurrentFuture = null;
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mUseLatestStates = true;
                        i3 = i & 127;
                        if (i3 == 127) {
                            com.android.server.power.stats.BatteryExternalStatsWorker.this.cancelSyncDueToBatteryLevelChangeLocked();
                        }
                        i4 = i & 1;
                        if (i4 != 0) {
                            com.android.server.power.stats.BatteryExternalStatsWorker.this.cancelCpuSyncDueToWakelockChange();
                        }
                        if ((i & 14) == 14) {
                            com.android.server.power.stats.BatteryExternalStatsWorker.this.cancelSyncDueToProcessStateChange();
                        }
                    } finally {
                    }
                }
                try {
                    synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this.mWorkerLock) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.updateExternalStatsLocked(str, i, z, z2, i2, iArr, z3);
                    }
                    if (i4 != 0) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mStats.updateCpuTimesForAllUids();
                    }
                    synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this.mStats) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mStats.clearPendingRemovedUidsLocked();
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(com.android.server.power.stats.BatteryExternalStatsWorker.TAG, "Error updating external stats: ", e);
                }
                if ((i & 128) != 0) {
                    synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mLastCollectionTimeStamp = 0L;
                    }
                } else if (i3 == 127) {
                    synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.this.mLastCollectionTimeStamp = android.os.SystemClock.elapsedRealtime();
                    }
                }
            }
        };
        this.mWriteTask = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.power.stats.BatteryExternalStatsWorker.this.mStats) {
                    com.android.server.power.stats.BatteryExternalStatsWorker.this.mStats.writeAsyncLocked();
                }
            }
        };
        this.mInjector = injector;
        this.mStats = batteryStatsImpl;
    }

    public void systemServicesReady() {
        int batteryVoltageMvLocked;
        java.lang.String[] strArr;
        boolean[] zArr;
        android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> populateEnergyConsumerSubsystemMapsLocked;
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.mInjector.getSystemService(android.net.wifi.WifiManager.class);
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mInjector.getSystemService(android.telephony.TelephonyManager.class);
        android.power.PowerStatsInternal powerStatsInternal = (android.power.PowerStatsInternal) this.mInjector.getLocalService(android.power.PowerStatsInternal.class);
        synchronized (this.mStats) {
            batteryVoltageMvLocked = this.mStats.getBatteryVoltageMvLocked();
        }
        synchronized (this.mWorkerLock) {
            this.mWifiManager = wifiManager;
            this.mTelephony = telephonyManager;
            this.mPowerStatsInternal = powerStatsInternal;
            if (this.mPowerStatsInternal != null && (populateEnergyConsumerSubsystemMapsLocked = populateEnergyConsumerSubsystemMapsLocked()) != null) {
                this.mEnergyConsumerSnapshot = new com.android.server.power.stats.EnergyConsumerSnapshot(populateEnergyConsumerSubsystemMapsLocked);
                try {
                    this.mEnergyConsumerSnapshot.updateAndGetDelta(getEnergyConsumptionData().get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS), batteryVoltageMvLocked);
                } catch (java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
                    android.util.Slog.w(TAG, "timeout or interrupt reading initial getEnergyConsumedAsync: " + e);
                } catch (java.util.concurrent.ExecutionException e2) {
                    android.util.Slog.wtf(TAG, "exception reading initial getEnergyConsumedAsync: " + e2.getCause());
                }
                strArr = this.mEnergyConsumerSnapshot.getOtherOrdinalNames();
                zArr = getSupportedEnergyBuckets(populateEnergyConsumerSubsystemMapsLocked);
            } else {
                strArr = null;
                zArr = null;
            }
            synchronized (this.mStats) {
                this.mStats.initEnergyConsumerStatsLocked(zArr, strArr);
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public synchronized java.util.concurrent.Future<?> scheduleSync(java.lang.String str, int i) {
        return scheduleSyncLocked(str, i);
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public synchronized java.util.concurrent.Future<?> scheduleCpuSyncDueToRemovedUid(int i) {
        return scheduleSyncLocked("remove-uid", 1);
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public java.util.concurrent.Future<?> scheduleSyncDueToScreenStateChange(int i, boolean z, boolean z2, int i2, int[] iArr) {
        java.util.concurrent.Future<?> scheduleSyncLocked;
        synchronized (this) {
            try {
                if (this.mCurrentFuture == null || (this.mUpdateFlags & 1) == 0) {
                    this.mOnBattery = z;
                    this.mOnBatteryScreenOff = z2;
                    this.mUseLatestStates = false;
                }
                this.mScreenState = i2;
                this.mPerDisplayScreenStates = iArr;
                scheduleSyncLocked = scheduleSyncLocked("screen-state", i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return scheduleSyncLocked;
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public java.util.concurrent.Future<?> scheduleCpuSyncDueToWakelockChange(long j) {
        java.util.concurrent.Future<?> future;
        synchronized (this) {
            this.mWakelockChangesUpdate = scheduleDelayedSyncLocked(this.mWakelockChangesUpdate, new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleCpuSyncDueToWakelockChange$3();
                }
            }, j);
            future = this.mWakelockChangesUpdate;
        }
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCpuSyncDueToWakelockChange$3() {
        scheduleSync("wakelock-change", 1);
        scheduleRunnable(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleCpuSyncDueToWakelockChange$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCpuSyncDueToWakelockChange$2() {
        this.mStats.postBatteryNeedsCpuUpdateMsg();
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public void cancelCpuSyncDueToWakelockChange() {
        synchronized (this) {
            try {
                if (this.mWakelockChangesUpdate != null) {
                    this.mWakelockChangesUpdate.cancel(false);
                    this.mWakelockChangesUpdate = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public java.util.concurrent.Future<?> scheduleSyncDueToBatteryLevelChange(long j) {
        java.util.concurrent.Future<?> future;
        synchronized (this) {
            this.mBatteryLevelSync = scheduleDelayedSyncLocked(this.mBatteryLevelSync, new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleSyncDueToBatteryLevelChange$4();
                }
            }, j);
            future = this.mBatteryLevelSync;
        }
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSyncDueToBatteryLevelChange$4() {
        scheduleSync("battery-level", 127);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void cancelSyncDueToBatteryLevelChangeLocked() {
        if (this.mBatteryLevelSync != null) {
            this.mBatteryLevelSync.cancel(false);
            this.mBatteryLevelSync = null;
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public void scheduleSyncDueToProcessStateChange(final int i, long j) {
        synchronized (this) {
            this.mProcessStateSync = scheduleDelayedSyncLocked(this.mProcessStateSync, new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleSyncDueToProcessStateChange$5(i);
                }
            }, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSyncDueToProcessStateChange$5(int i) {
        scheduleSync("procstate-change", i);
    }

    public void cancelSyncDueToProcessStateChange() {
        synchronized (this) {
            try {
                if (this.mProcessStateSync != null) {
                    this.mProcessStateSync.cancel(false);
                    this.mProcessStateSync = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync
    public java.util.concurrent.Future<?> scheduleCleanupDueToRemovedUser(final int i) {
        java.util.concurrent.ScheduledFuture<?> schedule;
        synchronized (this) {
            try {
                try {
                    this.mExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleCleanupDueToRemovedUser$6(i);
                        }
                    }, EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
                    schedule = this.mExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.stats.BatteryExternalStatsWorker.this.lambda$scheduleCleanupDueToRemovedUser$7(i);
                        }
                    }, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (java.util.concurrent.RejectedExecutionException e) {
                    return java.util.concurrent.CompletableFuture.failedFuture(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return schedule;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCleanupDueToRemovedUser$6(int i) {
        synchronized (this.mStats) {
            this.mStats.clearRemovedUserUidsLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCleanupDueToRemovedUser$7(int i) {
        synchronized (this.mStats) {
            this.mStats.clearRemovedUserUidsLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> scheduleDelayedSyncLocked(java.util.concurrent.Future<?> future, java.lang.Runnable runnable, long j) {
        if (this.mExecutorService.isShutdown()) {
            return java.util.concurrent.CompletableFuture.failedFuture(new java.lang.IllegalStateException("worker shutdown"));
        }
        if (future != null) {
            if (j == 0) {
                future.cancel(false);
            } else {
                return future;
            }
        }
        try {
            return this.mExecutorService.schedule(runnable, j, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.util.concurrent.RejectedExecutionException e) {
            return java.util.concurrent.CompletableFuture.failedFuture(e);
        }
    }

    public synchronized java.util.concurrent.Future<?> scheduleWrite() {
        if (this.mExecutorService.isShutdown()) {
            return java.util.concurrent.CompletableFuture.failedFuture(new java.lang.IllegalStateException("worker shutdown"));
        }
        scheduleSyncLocked("write", 127);
        try {
            return this.mExecutorService.submit(this.mWriteTask);
        } catch (java.util.concurrent.RejectedExecutionException e) {
            return java.util.concurrent.CompletableFuture.failedFuture(e);
        }
    }

    public synchronized void scheduleRunnable(java.lang.Runnable runnable) {
        try {
            this.mExecutorService.submit(runnable);
        } catch (java.util.concurrent.RejectedExecutionException e) {
            android.util.Slog.e(TAG, "Couldn't schedule " + runnable, e);
        }
    }

    public void shutdown() {
        this.mExecutorService.shutdownNow();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.Future<?> scheduleSyncLocked(java.lang.String str, int i) {
        if (this.mExecutorService.isShutdown()) {
            return java.util.concurrent.CompletableFuture.failedFuture(new java.lang.IllegalStateException("worker shutdown"));
        }
        if (this.mCurrentFuture == null) {
            this.mUpdateFlags = i;
            this.mCurrentReason = str;
            try {
                this.mCurrentFuture = this.mExecutorService.submit(this.mSyncTask);
            } catch (java.util.concurrent.RejectedExecutionException e) {
                return java.util.concurrent.CompletableFuture.failedFuture(e);
            }
        }
        this.mUpdateFlags |= i;
        return this.mCurrentFuture;
    }

    public long getLastCollectionTimeStamp() {
        long j;
        synchronized (this) {
            j = this.mLastCollectionTimeStamp;
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0164 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateExternalStatsLocked(java.lang.String str, int i, boolean z, boolean z2, int i2, int[] iArr, boolean z3) {
        final android.os.SynchronousResultReceiver synchronousResultReceiver;
        boolean z4;
        final android.os.SynchronousResultReceiver synchronousResultReceiver2;
        android.telephony.ModemActivityInfo modemActivityInfo;
        com.android.server.power.stats.EnergyConsumerSnapshot.EnergyConsumerDeltaData energyConsumerDeltaData;
        int i3;
        long j;
        long j2;
        long j3;
        boolean z5;
        boolean z6;
        int batteryVoltageMvLocked;
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr;
        android.bluetooth.BluetoothAdapter defaultAdapter;
        long[] jArr = null;
        final java.util.concurrent.CompletableFuture completedFuture = java.util.concurrent.CompletableFuture.completedFuture(null);
        java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> energyConsumersLocked = getEnergyConsumersLocked(i);
        if ((i & 2) == 0) {
            synchronousResultReceiver = null;
            z4 = false;
        } else {
            if (this.mWifiManager != null && this.mWifiManager.isEnhancedPowerReportingSupported()) {
                synchronousResultReceiver = new android.os.SynchronousResultReceiver("wifi");
                this.mWifiManager.getWifiActivityEnergyInfoAsync(new java.util.concurrent.Executor() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.3
                    @Override // java.util.concurrent.Executor
                    public void execute(java.lang.Runnable runnable) {
                        runnable.run();
                    }
                }, new android.net.wifi.WifiManager.OnWifiActivityEnergyInfoListener() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker$$ExternalSyntheticLambda4
                    public final void onWifiActivityEnergyInfo(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
                        com.android.server.power.stats.BatteryExternalStatsWorker.lambda$updateExternalStatsLocked$8(synchronousResultReceiver, wifiActivityEnergyInfo);
                    }
                });
            } else {
                synchronousResultReceiver = null;
            }
            synchronized (this.mStats) {
                this.mStats.updateRailStatsLocked();
            }
            z4 = true;
        }
        if ((i & 8) != 0 && (defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter()) != null) {
            synchronousResultReceiver2 = new android.os.SynchronousResultReceiver("bluetooth");
            defaultAdapter.requestControllerActivityEnergyInfo(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.bluetooth.BluetoothAdapter.OnBluetoothActivityEnergyInfoCallback() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.4
                public void onBluetoothActivityEnergyInfoAvailable(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable("controller_activity", bluetoothActivityEnergyInfo);
                    synchronousResultReceiver2.send(0, bundle);
                }

                public void onBluetoothActivityEnergyInfoError(int i4) {
                    android.util.Slog.w(com.android.server.power.stats.BatteryExternalStatsWorker.TAG, "error reading Bluetooth stats: " + i4);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable("controller_activity", null);
                    synchronousResultReceiver2.send(0, bundle);
                }
            });
        } else {
            synchronousResultReceiver2 = null;
        }
        if ((i & 4) != 0) {
            if (this.mTelephony != null) {
                completedFuture = new java.util.concurrent.CompletableFuture();
                this.mTelephony.requestModemActivityInfo(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.os.OutcomeReceiver<android.telephony.ModemActivityInfo, android.telephony.TelephonyManager.ModemActivityInfoException>() { // from class: com.android.server.power.stats.BatteryExternalStatsWorker.5
                    @Override // android.os.OutcomeReceiver
                    public void onResult(android.telephony.ModemActivityInfo modemActivityInfo2) {
                        completedFuture.complete(modemActivityInfo2);
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(android.telephony.TelephonyManager.ModemActivityInfoException modemActivityInfoException) {
                        android.util.Slog.w(com.android.server.power.stats.BatteryExternalStatsWorker.TAG, "error reading modem stats:" + modemActivityInfoException);
                        completedFuture.complete(null);
                    }
                });
            }
            if (!z4) {
                synchronized (this.mStats) {
                    this.mStats.updateRailStatsLocked();
                }
            }
        }
        int i4 = i & 16;
        if (i4 != 0) {
            this.mStats.fillLowPowerStats();
        }
        android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo = (android.os.connectivity.WifiActivityEnergyInfo) awaitControllerInfo(synchronousResultReceiver);
        android.bluetooth.BluetoothActivityEnergyInfo awaitControllerInfo = awaitControllerInfo(synchronousResultReceiver2);
        try {
            modemActivityInfo = (android.telephony.ModemActivityInfo) completedFuture.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "timeout or interrupt reading modem stats: " + e);
            modemActivityInfo = null;
            if (this.mEnergyConsumerSnapshot != null) {
            }
            energyConsumerDeltaData = null;
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j4 = 1000 * elapsedRealtime;
            synchronized (this.mStats) {
            }
        } catch (java.util.concurrent.ExecutionException e2) {
            android.util.Slog.w(TAG, "exception reading modem stats: " + e2.getCause());
            modemActivityInfo = null;
            if (this.mEnergyConsumerSnapshot != null) {
            }
            energyConsumerDeltaData = null;
            long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
            long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
            long j42 = 1000 * elapsedRealtime2;
            synchronized (this.mStats) {
            }
        }
        if (this.mEnergyConsumerSnapshot != null || energyConsumersLocked == null) {
            energyConsumerDeltaData = null;
        } else {
            synchronized (this.mStats) {
                batteryVoltageMvLocked = this.mStats.getBatteryVoltageMvLocked();
            }
            try {
                energyConsumerResultArr = energyConsumersLocked.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException | java.util.concurrent.TimeoutException e3) {
                android.util.Slog.w(TAG, "timeout or interrupt reading getEnergyConsumedAsync: " + e3);
                energyConsumerResultArr = null;
            } catch (java.util.concurrent.ExecutionException e4) {
                android.util.Slog.wtf(TAG, "exception reading getEnergyConsumedAsync: " + e4.getCause());
                energyConsumerResultArr = null;
            }
            energyConsumerDeltaData = this.mEnergyConsumerSnapshot.updateAndGetDelta(energyConsumerResultArr, batteryVoltageMvLocked);
        }
        long elapsedRealtime22 = android.os.SystemClock.elapsedRealtime();
        long uptimeMillis22 = android.os.SystemClock.uptimeMillis();
        long j422 = 1000 * elapsedRealtime22;
        synchronized (this.mStats) {
            try {
                this.mStats.recordHistoryEventLocked(elapsedRealtime22, uptimeMillis22, 14, str, 0);
                if ((i & 1) != 0) {
                    if (!z3) {
                        z5 = z;
                        z6 = z2;
                    } else {
                        z5 = this.mStats.isOnBatteryLocked();
                        z6 = this.mStats.isOnBatteryScreenOffLocked();
                    }
                    if (energyConsumerDeltaData != null) {
                        jArr = energyConsumerDeltaData.cpuClusterChargeUC;
                    }
                    this.mStats.updateCpuTimeLocked(z5, z6, jArr);
                }
                i3 = i & 127;
                if (i3 == 127) {
                    this.mStats.updateKernelWakelocksLocked(j422);
                    this.mStats.updateKernelMemoryBandwidthLocked(j422);
                }
                if (i4 != 0) {
                    this.mStats.updateRpmStatsLocked(j422);
                }
                if (energyConsumerDeltaData != null) {
                    long[] jArr2 = energyConsumerDeltaData.displayChargeUC;
                    if (jArr2 != null && jArr2.length > 0) {
                        this.mStats.updateDisplayEnergyConsumerStatsLocked(jArr2, iArr, elapsedRealtime22);
                    }
                    long j5 = energyConsumerDeltaData.gnssChargeUC;
                    if (j5 != -1) {
                        this.mStats.updateGnssEnergyConsumerStatsLocked(j5, elapsedRealtime22);
                    }
                    long j6 = energyConsumerDeltaData.cameraChargeUC;
                    if (j6 != -1) {
                        this.mStats.updateCameraEnergyConsumerStatsLocked(j6, elapsedRealtime22);
                    }
                }
                if (energyConsumerDeltaData != null && energyConsumerDeltaData.otherTotalChargeUC != null) {
                    for (int i5 = 0; i5 < energyConsumerDeltaData.otherTotalChargeUC.length; i5++) {
                        this.mStats.updateCustomEnergyConsumerStatsLocked(i5, energyConsumerDeltaData.otherTotalChargeUC[i5], energyConsumerDeltaData.otherUidChargesUC[i5]);
                    }
                }
                if (awaitControllerInfo == null) {
                    j = elapsedRealtime22;
                } else if (awaitControllerInfo.isValid()) {
                    if (energyConsumerDeltaData != null) {
                        j3 = energyConsumerDeltaData.bluetoothChargeUC;
                    } else {
                        j3 = -1;
                    }
                    j = elapsedRealtime22;
                    this.mStats.updateBluetoothStateLocked(awaitControllerInfo, j3, elapsedRealtime22, uptimeMillis22);
                } else {
                    j = elapsedRealtime22;
                    android.util.Slog.w(TAG, "bluetooth info is invalid: " + awaitControllerInfo);
                }
            } finally {
            }
        }
        if (wifiActivityEnergyInfo != null) {
            if (wifiActivityEnergyInfo.isValid()) {
                this.mStats.updateWifiState(extractDeltaLocked(wifiActivityEnergyInfo), energyConsumerDeltaData != null ? energyConsumerDeltaData.wifiChargeUC : -1L, j, uptimeMillis22, (android.app.usage.NetworkStatsManager) this.mInjector.getSystemService(android.app.usage.NetworkStatsManager.class));
            } else {
                android.util.Slog.w(TAG, "wifi info is invalid: " + wifiActivityEnergyInfo);
            }
        }
        if (modemActivityInfo != null) {
            if (energyConsumerDeltaData == null) {
                j2 = -1;
            } else {
                j2 = energyConsumerDeltaData.mobileRadioChargeUC;
            }
            this.mStats.noteModemControllerActivity(modemActivityInfo, j2, j, uptimeMillis22, (android.app.usage.NetworkStatsManager) this.mInjector.getSystemService(android.app.usage.NetworkStatsManager.class));
        }
        if (i3 == 127) {
            this.mStats.informThatAllExternalStatsAreFlushed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateExternalStatsLocked$8(android.os.SynchronousResultReceiver synchronousResultReceiver, android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("controller_activity", wifiActivityEnergyInfo);
        synchronousResultReceiver.send(0, bundle);
    }

    private static <T extends android.os.Parcelable> T awaitControllerInfo(@android.annotation.Nullable android.os.SynchronousResultReceiver synchronousResultReceiver) {
        if (synchronousResultReceiver == null) {
            return null;
        }
        try {
            android.os.SynchronousResultReceiver.Result awaitResult = synchronousResultReceiver.awaitResult(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS);
            if (awaitResult.bundle != null) {
                awaitResult.bundle.setDefusable(true);
                T t = (T) awaitResult.bundle.getParcelable("controller_activity");
                if (t != null) {
                    return t;
                }
            }
        } catch (java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "timeout reading " + synchronousResultReceiver.getName() + " stats");
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private android.os.connectivity.WifiActivityEnergyInfo extractDeltaLocked(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        boolean z;
        long timeSinceBootMillis = wifiActivityEnergyInfo.getTimeSinceBootMillis() - this.mLastWifiInfo.getTimeSinceBootMillis();
        long controllerScanDurationMillis = this.mLastWifiInfo.getControllerScanDurationMillis();
        long controllerIdleDurationMillis = this.mLastWifiInfo.getControllerIdleDurationMillis();
        long controllerTxDurationMillis = this.mLastWifiInfo.getControllerTxDurationMillis();
        long controllerRxDurationMillis = this.mLastWifiInfo.getControllerRxDurationMillis();
        long controllerEnergyUsedMicroJoules = this.mLastWifiInfo.getControllerEnergyUsedMicroJoules();
        long timeSinceBootMillis2 = wifiActivityEnergyInfo.getTimeSinceBootMillis();
        int stackState = wifiActivityEnergyInfo.getStackState();
        long controllerTxDurationMillis2 = wifiActivityEnergyInfo.getControllerTxDurationMillis() - controllerTxDurationMillis;
        long controllerRxDurationMillis2 = wifiActivityEnergyInfo.getControllerRxDurationMillis() - controllerRxDurationMillis;
        long controllerIdleDurationMillis2 = wifiActivityEnergyInfo.getControllerIdleDurationMillis() - controllerIdleDurationMillis;
        long controllerScanDurationMillis2 = wifiActivityEnergyInfo.getControllerScanDurationMillis() - controllerScanDurationMillis;
        long j9 = 0;
        if (controllerTxDurationMillis2 < 0 || controllerRxDurationMillis2 < 0 || controllerScanDurationMillis2 < 0 || controllerIdleDurationMillis2 < 0) {
            if (wifiActivityEnergyInfo.getControllerTxDurationMillis() + wifiActivityEnergyInfo.getControllerRxDurationMillis() + wifiActivityEnergyInfo.getControllerIdleDurationMillis() <= timeSinceBootMillis + MAX_WIFI_STATS_SAMPLE_ERROR_MILLIS) {
                long controllerEnergyUsedMicroJoules2 = wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules();
                j = wifiActivityEnergyInfo.getControllerRxDurationMillis();
                long controllerTxDurationMillis3 = wifiActivityEnergyInfo.getControllerTxDurationMillis();
                j3 = wifiActivityEnergyInfo.getControllerIdleDurationMillis();
                j4 = wifiActivityEnergyInfo.getControllerScanDurationMillis();
                j9 = controllerTxDurationMillis3;
                j2 = controllerEnergyUsedMicroJoules2;
            } else {
                j = 0;
                j2 = 0;
                j3 = 0;
                j4 = 0;
            }
            j5 = j;
            controllerTxDurationMillis2 = j9;
            j6 = j2;
            j7 = j3;
            j8 = j4;
            z = true;
        } else {
            z = false;
            j6 = java.lang.Math.max(0L, wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules() - controllerEnergyUsedMicroJoules);
            j8 = controllerScanDurationMillis2;
            j5 = controllerRxDurationMillis2;
            j7 = controllerIdleDurationMillis2;
        }
        this.mLastWifiInfo = wifiActivityEnergyInfo;
        android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo2 = new android.os.connectivity.WifiActivityEnergyInfo(timeSinceBootMillis2, stackState, controllerTxDurationMillis2, j5, j8, j7, j6);
        if (z) {
            android.util.Slog.v(TAG, "WiFi energy data was reset, new WiFi energy data is " + wifiActivityEnergyInfo2);
        }
        return wifiActivityEnergyInfo2;
    }

    @android.annotation.Nullable
    private static boolean[] getSupportedEnergyBuckets(android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> sparseArray) {
        if (sparseArray == null) {
            return null;
        }
        boolean[] zArr = new boolean[10];
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            switch (sparseArray.valueAt(i).type) {
                case 1:
                    zArr[5] = true;
                    break;
                case 2:
                    zArr[3] = true;
                    break;
                case 3:
                    zArr[0] = true;
                    zArr[1] = true;
                    zArr[2] = true;
                    break;
                case 4:
                    zArr[6] = true;
                    break;
                case 5:
                    zArr[7] = true;
                    zArr[9] = true;
                    break;
                case 6:
                    zArr[4] = true;
                    break;
                case 7:
                    zArr[8] = true;
                    break;
            }
        }
        return zArr;
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @android.annotation.Nullable
    private java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> getEnergyConsumptionData() {
        return getEnergyConsumptionData(new int[0]);
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @android.annotation.Nullable
    private java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> getEnergyConsumptionData(int[] iArr) {
        return this.mPowerStatsInternal.getEnergyConsumedAsync(iArr);
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> getEnergyConsumersLocked(int i) {
        if (this.mEnergyConsumerSnapshot == null || this.mPowerStatsInternal == null) {
            return null;
        }
        if (i == 127) {
            return getEnergyConsumptionData();
        }
        android.util.IntArray intArray = new android.util.IntArray();
        if ((i & 8) != 0) {
            addEnergyConsumerIdLocked(intArray, 1);
        }
        if ((i & 1) != 0) {
            addEnergyConsumerIdLocked(intArray, 2);
        }
        if ((i & 32) != 0) {
            addEnergyConsumerIdLocked(intArray, 3);
        }
        if ((i & 4) != 0) {
            addEnergyConsumerIdLocked(intArray, 5);
        }
        if ((i & 2) != 0) {
            addEnergyConsumerIdLocked(intArray, 6);
        }
        if ((i & 64) != 0) {
            addEnergyConsumerIdLocked(intArray, 7);
        }
        if (intArray.size() == 0) {
            return null;
        }
        return getEnergyConsumptionData(intArray.toArray());
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    private void addEnergyConsumerIdLocked(android.util.IntArray intArray, @android.hardware.power.stats.EnergyConsumerType int i) {
        int[] iArr = this.mEnergyConsumerTypeToIdMap.get(i);
        if (iArr == null) {
            return;
        }
        intArray.addAll(iArr);
    }

    @com.android.internal.annotations.GuardedBy({"mWorkerLock"})
    @android.annotation.Nullable
    private android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> populateEnergyConsumerSubsystemMapsLocked() {
        android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo;
        if (this.mPowerStatsInternal == null || (energyConsumerInfo = this.mPowerStatsInternal.getEnergyConsumerInfo()) == null || energyConsumerInfo.length == 0) {
            return null;
        }
        android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> sparseArray = new android.util.SparseArray<>(energyConsumerInfo.length);
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        for (android.hardware.power.stats.EnergyConsumer energyConsumer : energyConsumerInfo) {
            if (energyConsumer.ordinal != 0) {
                switch (energyConsumer.type) {
                    case 0:
                    case 2:
                    case 3:
                        break;
                    case 1:
                    default:
                        android.util.Slog.w(TAG, "EnergyConsumer '" + energyConsumer.name + "' has unexpected ordinal " + energyConsumer.ordinal + " for type " + ((int) energyConsumer.type));
                        break;
                }
            }
            sparseArray.put(energyConsumer.id, energyConsumer);
            android.util.IntArray intArray = (android.util.IntArray) sparseArray2.get(energyConsumer.type);
            if (intArray == null) {
                intArray = new android.util.IntArray();
                sparseArray2.put(energyConsumer.type, intArray);
            }
            intArray.add(energyConsumer.id);
        }
        this.mEnergyConsumerTypeToIdMap = new android.util.SparseArray<>(sparseArray2.size());
        int size = sparseArray2.size();
        for (int i = 0; i < size; i++) {
            this.mEnergyConsumerTypeToIdMap.put(sparseArray2.keyAt(i), ((android.util.IntArray) sparseArray2.valueAt(i)).toArray());
        }
        return sparseArray;
    }
}
