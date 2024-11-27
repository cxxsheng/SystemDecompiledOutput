package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public class PrefetchController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;
    private static final int MSG_PROCESS_TOP_STATE_CHANGE = 2;
    private static final int MSG_PROCESS_UPDATED_ESTIMATED_LAUNCH_TIME = 1;
    private static final int MSG_RETRIEVE_ESTIMATED_LAUNCH_TIME = 0;
    private static final java.lang.String TAG = "JobScheduler.Prefetch";
    private android.appwidget.AppWidgetManager mAppWidgetManager;
    private final android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener mEstimatedLaunchTimeChangedListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Long> mEstimatedLaunchTimes;
    private final com.android.server.job.controllers.PrefetchController.PcHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLaunchTimeAllowanceMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLaunchTimeThresholdMs;
    private final com.android.server.job.controllers.PrefetchController.PcConstants mPcConstants;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.PrefetchController.PrefetchChangedListener> mPrefetchChangedListeners;
    private final com.android.server.job.controllers.PrefetchController.ThresholdAlarmListener mThresholdAlarmListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mTrackedJobs;
    private final android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;

    public interface PrefetchChangedListener {
        void onPrefetchCacheUpdated(android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet, int i, java.lang.String str, long j, long j2, long j3);
    }

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public PrefetchController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedJobs = new android.util.SparseArrayMap<>();
        this.mEstimatedLaunchTimes = new android.util.SparseArrayMap<>();
        this.mPrefetchChangedListeners = new android.util.ArraySet<>();
        this.mLaunchTimeThresholdMs = 3600000L;
        this.mLaunchTimeAllowanceMs = 1800000L;
        this.mEstimatedLaunchTimeChangedListener = new android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener() { // from class: com.android.server.job.controllers.PrefetchController.1
            @Override // android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener
            public void onEstimatedLaunchTimeChanged(int i, @android.annotation.NonNull java.lang.String str, long j) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.argi1 = i;
                obtain.argl1 = j;
                com.android.server.job.controllers.PrefetchController.this.mHandler.obtainMessage(1, obtain).sendToTarget();
            }
        };
        this.mPcConstants = new com.android.server.job.controllers.PrefetchController.PcConstants();
        this.mHandler = new com.android.server.job.controllers.PrefetchController.PcHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mThresholdAlarmListener = new com.android.server.job.controllers.PrefetchController.ThresholdAlarmListener(this.mContext, com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        this.mUsageStatsManagerInternal.registerLaunchTimeChangedListener(this.mEstimatedLaunchTimeChangedListener);
    }

    @Override // com.android.server.job.controllers.StateController
    public void onSystemServicesReady() {
        this.mAppWidgetManager = (android.appwidget.AppWidgetManager) this.mContext.getSystemService(android.appwidget.AppWidgetManager.class);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.getJob().isPrefetch()) {
            int sourceUserId = jobStatus.getSourceUserId();
            java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
            android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(sourceUserId, sourcePackageName);
            if (arraySet == null) {
                arraySet = new android.util.ArraySet();
                this.mTrackedJobs.add(sourceUserId, sourcePackageName, arraySet);
            }
            long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
            long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (arraySet.add(jobStatus) && arraySet.size() == 1 && !willBeLaunchedSoonLocked(sourceUserId, sourcePackageName, millis)) {
                updateThresholdAlarmLocked(sourceUserId, sourcePackageName, millis, millis2);
            }
            updateConstraintLocked(jobStatus, millis, millis2);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        int sourceUserId = jobStatus.getSourceUserId();
        java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(sourceUserId, sourcePackageName);
        if (arraySet != null && arraySet.remove(jobStatus) && arraySet.size() == 0) {
            this.mThresholdAlarmListener.removeAlarmForKey(android.content.pm.UserPackage.of(sourceUserId, sourcePackageName));
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onAppRemovedLocked(java.lang.String str, int i) {
        if (str == null) {
            android.util.Slog.wtf(TAG, "Told app removed but given null package name.");
            return;
        }
        int userId = android.os.UserHandle.getUserId(i);
        this.mTrackedJobs.delete(userId, str);
        this.mEstimatedLaunchTimes.delete(userId, str);
        this.mThresholdAlarmListener.removeAlarmForKey(android.content.pm.UserPackage.of(userId, str));
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        this.mTrackedJobs.delete(i);
        this.mEstimatedLaunchTimes.delete(i);
        this.mThresholdAlarmListener.removeAlarmsForUserId(i);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUidBiasChangedLocked(int i, int i2, int i3) {
        if ((i3 == 40) != (i2 == 40)) {
            this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public long getNextEstimatedLaunchTimeLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return getNextEstimatedLaunchTimeLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), com.android.server.job.JobSchedulerService.sSystemClock.millis());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getNextEstimatedLaunchTimeLocked(int i, @android.annotation.NonNull java.lang.String str, long j) {
        java.lang.Long l = (java.lang.Long) this.mEstimatedLaunchTimes.get(i, str);
        if (l == null || l.longValue() < j - this.mLaunchTimeAllowanceMs) {
            this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
            this.mEstimatedLaunchTimes.add(i, str, java.lang.Long.valueOf(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME));
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return l.longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean maybeUpdateConstraintForPkgLocked(long j, long j2, int i, java.lang.String str) {
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
        if (arraySet == null) {
            return false;
        }
        boolean z = false;
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            z |= updateConstraintLocked((com.android.server.job.controllers.JobStatus) arraySet.valueAt(i2), j, j2);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeUpdateConstraintForUid(int i) {
        android.util.ArraySet<java.lang.String> arraySet;
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.String> packagesForUidLocked = this.mService.getPackagesForUidLocked(i);
                if (packagesForUidLocked == null) {
                    return;
                }
                int userId = android.os.UserHandle.getUserId(i);
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
                long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
                long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                int size = packagesForUidLocked.size() - 1;
                while (size >= 0) {
                    android.util.ArraySet arraySet3 = (android.util.ArraySet) this.mTrackedJobs.get(userId, packagesForUidLocked.valueAt(size));
                    if (arraySet3 == null) {
                        arraySet = packagesForUidLocked;
                    } else {
                        int i2 = 0;
                        while (i2 < arraySet3.size()) {
                            com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet3.valueAt(i2);
                            android.util.ArraySet<java.lang.String> arraySet4 = packagesForUidLocked;
                            android.util.ArraySet arraySet5 = arraySet3;
                            int i3 = i2;
                            if (updateConstraintLocked(jobStatus, millis, millis2)) {
                                arraySet2.add(jobStatus);
                            }
                            i2 = i3 + 1;
                            packagesForUidLocked = arraySet4;
                            arraySet3 = arraySet5;
                        }
                        arraySet = packagesForUidLocked;
                    }
                    size--;
                    packagesForUidLocked = arraySet;
                }
                if (arraySet2.size() > 0) {
                    this.mStateChangedListener.onControllerStateChanged(arraySet2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processUpdatedEstimatedLaunchTime(int i, @android.annotation.NonNull java.lang.String str, long j) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Estimated launch time for " + com.android.server.job.controllers.StateController.packageToString(i, str) + " changed to " + j + " (" + android.util.TimeUtils.formatDuration(j - com.android.server.job.JobSchedulerService.sSystemClock.millis()) + " from now)");
        }
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
                if (arraySet == null) {
                    if (DEBUG) {
                        android.util.Slog.i(TAG, "Not caching launch time since we haven't seen any prefetch jobs for " + com.android.server.job.controllers.StateController.packageToString(i, str));
                    }
                } else {
                    long longValue = ((java.lang.Long) this.mEstimatedLaunchTimes.get(i, str)).longValue();
                    this.mEstimatedLaunchTimes.add(i, str, java.lang.Long.valueOf(j));
                    if (!arraySet.isEmpty()) {
                        long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
                        long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                        updateThresholdAlarmLocked(i, str, millis, millis2);
                        int i2 = 0;
                        while (i2 < this.mPrefetchChangedListeners.size()) {
                            this.mPrefetchChangedListeners.valueAt(i2).onPrefetchCacheUpdated(arraySet, i, str, longValue, j, millis2);
                            i2++;
                            arraySet = arraySet;
                        }
                        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = arraySet;
                        if (maybeUpdateConstraintForPkgLocked(millis, millis2, i, str)) {
                            this.mStateChangedListener.onControllerStateChanged(arraySet2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updateConstraintLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, long j, long j2) {
        boolean z = false;
        if (!(this.mService.getUidBias(jobStatus.getSourceUid()) == 40)) {
            int sourceUserId = jobStatus.getSourceUserId();
            java.lang.String sourcePackageName = jobStatus.getSourcePackageName();
            if (willBeLaunchedSoonLocked(sourceUserId, sourcePackageName, j) || (this.mAppWidgetManager != null && this.mAppWidgetManager.isBoundWidgetPackage(sourcePackageName, sourceUserId))) {
                z = true;
            }
        } else {
            z = this.mService.isCurrentlyRunningLocked(jobStatus);
        }
        return jobStatus.setPrefetchConstraintSatisfied(j2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateThresholdAlarmLocked(int i, @android.annotation.NonNull java.lang.String str, long j, long j2) {
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mTrackedJobs.get(i, str);
        if (arraySet == null || arraySet.size() == 0) {
            this.mThresholdAlarmListener.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
            return;
        }
        long nextEstimatedLaunchTimeLocked = getNextEstimatedLaunchTimeLocked(i, str, j);
        if (nextEstimatedLaunchTimeLocked != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME && nextEstimatedLaunchTimeLocked - j > this.mLaunchTimeThresholdMs) {
            this.mThresholdAlarmListener.addAlarm(android.content.pm.UserPackage.of(i, str), j2 + (nextEstimatedLaunchTimeLocked - (j + this.mLaunchTimeThresholdMs)));
        } else {
            this.mThresholdAlarmListener.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean willBeLaunchedSoonLocked(int i, @android.annotation.NonNull java.lang.String str, long j) {
        return getNextEstimatedLaunchTimeLocked(i, str, j) <= (j + this.mLaunchTimeThresholdMs) - this.mLaunchTimeAllowanceMs;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForUpdatedConstantsLocked() {
        this.mPcConstants.mShouldReevaluateConstraints = false;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void processConstantLocked(android.provider.DeviceConfig.Properties properties, java.lang.String str) {
        this.mPcConstants.processConstantLocked(properties, str);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onConstantsUpdatedLocked() {
        if (this.mPcConstants.mShouldReevaluateConstraints) {
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.PrefetchController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.PrefetchController.this.lambda$onConstantsUpdatedLocked$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$0() {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
        synchronized (this.mLock) {
            try {
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                long millis2 = com.android.server.job.JobSchedulerService.sSystemClock.millis();
                for (int i = 0; i < this.mTrackedJobs.numMaps(); i++) {
                    int keyAt = this.mTrackedJobs.keyAt(i);
                    int i2 = 0;
                    while (i2 < this.mTrackedJobs.numElementsForKey(keyAt)) {
                        java.lang.String str = (java.lang.String) this.mTrackedJobs.keyAt(i, i2);
                        int i3 = i2;
                        long j = millis;
                        int i4 = keyAt;
                        if (maybeUpdateConstraintForPkgLocked(millis2, millis, keyAt, str)) {
                            arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) this.mTrackedJobs.valueAt(i, i3));
                        }
                        if (!willBeLaunchedSoonLocked(i4, str, millis2)) {
                            updateThresholdAlarmLocked(i4, str, millis2, j);
                        }
                        i2 = i3 + 1;
                        keyAt = i4;
                        millis = j;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arraySet.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
    }

    private class ThresholdAlarmListener extends com.android.server.utils.AlarmQueue<android.content.pm.UserPackage> {
        private ThresholdAlarmListener(android.content.Context context, android.os.Looper looper) {
            super(context, looper, "*job.prefetch*", "Prefetch threshold", false, 360000L);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull android.content.pm.UserPackage userPackage, int i) {
            return userPackage.userId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<android.content.pm.UserPackage> arraySet) {
            long j;
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
            synchronized (com.android.server.job.controllers.PrefetchController.this.mLock) {
                try {
                    long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
                    long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    int i = 0;
                    while (i < arraySet.size()) {
                        android.content.pm.UserPackage valueAt = arraySet.valueAt(i);
                        if (!com.android.server.job.controllers.PrefetchController.this.willBeLaunchedSoonLocked(valueAt.userId, valueAt.packageName, millis)) {
                            android.util.Slog.e(com.android.server.job.controllers.PrefetchController.TAG, "Alarm expired for " + com.android.server.job.controllers.StateController.packageToString(valueAt.userId, valueAt.packageName) + " at the wrong time");
                            com.android.server.job.controllers.PrefetchController.this.updateThresholdAlarmLocked(valueAt.userId, valueAt.packageName, millis, millis2);
                            j = millis;
                        } else {
                            j = millis;
                            if (com.android.server.job.controllers.PrefetchController.this.maybeUpdateConstraintForPkgLocked(millis, millis2, valueAt.userId, valueAt.packageName)) {
                                arraySet2.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) com.android.server.job.controllers.PrefetchController.this.mTrackedJobs.get(valueAt.userId, valueAt.packageName));
                            }
                        }
                        i++;
                        millis = j;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (arraySet2.size() > 0) {
                com.android.server.job.controllers.PrefetchController.this.mStateChangedListener.onControllerStateChanged(arraySet2);
            }
        }
    }

    void registerPrefetchChangedListener(com.android.server.job.controllers.PrefetchController.PrefetchChangedListener prefetchChangedListener) {
        synchronized (this.mLock) {
            this.mPrefetchChangedListeners.add(prefetchChangedListener);
        }
    }

    void unRegisterPrefetchChangedListener(com.android.server.job.controllers.PrefetchController.PrefetchChangedListener prefetchChangedListener) {
        synchronized (this.mLock) {
            this.mPrefetchChangedListeners.remove(prefetchChangedListener);
        }
    }

    private class PcHandler extends android.os.Handler {
        PcHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    int i = message.arg1;
                    java.lang.String str = (java.lang.String) message.obj;
                    long estimatedPackageLaunchTime = com.android.server.job.controllers.PrefetchController.this.mUsageStatsManagerInternal.getEstimatedPackageLaunchTime(str, i);
                    if (com.android.server.job.controllers.PrefetchController.DEBUG) {
                        android.util.Slog.d(com.android.server.job.controllers.PrefetchController.TAG, "Retrieved launch time for " + com.android.server.job.controllers.StateController.packageToString(i, str) + " of " + estimatedPackageLaunchTime + " (" + android.util.TimeUtils.formatDuration(estimatedPackageLaunchTime - com.android.server.job.JobSchedulerService.sSystemClock.millis()) + " from now)");
                    }
                    synchronized (com.android.server.job.controllers.PrefetchController.this.mLock) {
                        try {
                            java.lang.Long l = (java.lang.Long) com.android.server.job.controllers.PrefetchController.this.mEstimatedLaunchTimes.get(i, str);
                            if (l != null) {
                                if (estimatedPackageLaunchTime != l.longValue()) {
                                }
                            }
                            com.android.server.job.controllers.PrefetchController.this.processUpdatedEstimatedLaunchTime(i, str, estimatedPackageLaunchTime);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    return;
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.job.controllers.PrefetchController.this.processUpdatedEstimatedLaunchTime(someArgs.argi1, (java.lang.String) someArgs.arg1, someArgs.argl1);
                    someArgs.recycle();
                    return;
                case 2:
                    com.android.server.job.controllers.PrefetchController.this.maybeUpdateConstraintForUid(message.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class PcConstants {
        private static final long DEFAULT_LAUNCH_TIME_ALLOWANCE_MS = 1800000;
        private static final long DEFAULT_LAUNCH_TIME_THRESHOLD_MS = 3600000;

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_LAUNCH_TIME_ALLOWANCE_MS = "pc_launch_time_allowance_ms";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_LAUNCH_TIME_THRESHOLD_MS = "pc_launch_time_threshold_ms";
        private static final java.lang.String PC_CONSTANT_PREFIX = "pc_";
        private boolean mShouldReevaluateConstraints = false;
        public long LAUNCH_TIME_THRESHOLD_MS = 3600000;
        public long LAUNCH_TIME_ALLOWANCE_MS = 1800000;

        PcConstants() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case 1521894047:
                    if (str.equals(KEY_LAUNCH_TIME_ALLOWANCE_MS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1566126444:
                    if (str.equals(KEY_LAUNCH_TIME_THRESHOLD_MS)) {
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
                    this.LAUNCH_TIME_ALLOWANCE_MS = properties.getLong(str, 1800000L);
                    long min = java.lang.Math.min(com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT, java.lang.Math.max(0L, this.LAUNCH_TIME_ALLOWANCE_MS));
                    if (com.android.server.job.controllers.PrefetchController.this.mLaunchTimeAllowanceMs != min) {
                        com.android.server.job.controllers.PrefetchController.this.mLaunchTimeAllowanceMs = min;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 1:
                    this.LAUNCH_TIME_THRESHOLD_MS = properties.getLong(str, 3600000L);
                    long min2 = java.lang.Math.min(86400000L, java.lang.Math.max(3600000L, this.LAUNCH_TIME_THRESHOLD_MS));
                    if (com.android.server.job.controllers.PrefetchController.this.mLaunchTimeThresholdMs != min2) {
                        com.android.server.job.controllers.PrefetchController.this.mLaunchTimeThresholdMs = min2;
                        this.mShouldReevaluateConstraints = true;
                        com.android.server.job.controllers.PrefetchController.this.mThresholdAlarmListener.setMinTimeBetweenAlarmsMs(com.android.server.job.controllers.PrefetchController.this.mLaunchTimeThresholdMs / 10);
                        break;
                    }
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println();
            indentingPrintWriter.print(com.android.server.job.controllers.PrefetchController.class.getSimpleName());
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_LAUNCH_TIME_THRESHOLD_MS, java.lang.Long.valueOf(this.LAUNCH_TIME_THRESHOLD_MS)).println();
            indentingPrintWriter.print(KEY_LAUNCH_TIME_ALLOWANCE_MS, java.lang.Long.valueOf(this.LAUNCH_TIME_ALLOWANCE_MS)).println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long getLaunchTimeAllowanceMs() {
        return this.mLaunchTimeAllowanceMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getLaunchTimeThresholdMs() {
        return this.mLaunchTimeThresholdMs;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.PrefetchController.PcConstants getPcConstants() {
        return this.mPcConstants;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, final java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
        indentingPrintWriter.println("Cached launch times:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mEstimatedLaunchTimes.numMaps(); i++) {
            int keyAt = this.mEstimatedLaunchTimes.keyAt(i);
            for (int i2 = 0; i2 < this.mEstimatedLaunchTimes.numElementsForKey(keyAt); i2++) {
                java.lang.String str = (java.lang.String) this.mEstimatedLaunchTimes.keyAt(i, i2);
                long longValue = ((java.lang.Long) this.mEstimatedLaunchTimes.valueAt(i, i2)).longValue();
                indentingPrintWriter.print(com.android.server.job.controllers.StateController.packageToString(keyAt, str));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print(longValue);
                indentingPrintWriter.print(" (");
                android.util.TimeUtils.formatDuration(longValue - millis, indentingPrintWriter, 19);
                indentingPrintWriter.println(" from now)");
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mTrackedJobs.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.controllers.PrefetchController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.PrefetchController.lambda$dumpControllerStateLocked$1(predicate, indentingPrintWriter, (android.util.ArraySet) obj);
            }
        });
        indentingPrintWriter.println();
        this.mThresholdAlarmListener.dump(indentingPrintWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpControllerStateLocked$1(java.util.function.Predicate predicate, android.util.IndentingPrintWriter indentingPrintWriter, android.util.ArraySet arraySet) {
        for (int i = 0; i < arraySet.size(); i++) {
            com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(i);
            if (predicate.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mPcConstants.dump(indentingPrintWriter);
    }
}
