package com.android.server.job;

/* loaded from: classes2.dex */
public class JobSchedulerService extends com.android.server.SystemService implements com.android.server.job.StateChangedListener, com.android.server.job.JobCompletedListener {
    public static final int ACTIVE_INDEX = 0;
    public static final boolean DEBUG_STANDBY;
    public static final int EXEMPTED_INDEX = 6;
    public static final int FREQUENT_INDEX = 2;
    public static final long MAX_ALLOWED_PERIOD_MS = 31536000000L;
    private static final int MAX_JOBS_PER_APP = 150;
    static final int MSG_CHECK_CHANGED_JOB_LIST = 8;
    static final int MSG_CHECK_INDIVIDUAL_JOB = 0;
    static final int MSG_CHECK_JOB = 1;
    static final int MSG_CHECK_JOB_GREEDY = 3;
    static final int MSG_CHECK_MEDIA_EXEMPTION = 9;
    static final int MSG_INFORM_OBSERVERS_OF_USER_VISIBLE_JOB_CHANGE = 11;
    static final int MSG_INFORM_OBSERVER_OF_ALL_USER_VISIBLE_JOBS = 10;
    static final int MSG_STOP_JOB = 2;
    static final int MSG_UID_ACTIVE = 6;
    static final int MSG_UID_GONE = 5;
    static final int MSG_UID_IDLE = 7;
    static final int MSG_UID_STATE_CHANGED = 4;
    public static final int NEVER_INDEX = 4;
    private static final int NUM_COMPLETED_JOB_HISTORY = 20;
    private static final long PERIODIC_JOB_WINDOW_BUFFER = 1800000;
    private static final java.lang.String QUOTA_TRACKER_ANR_TAG = "anr";
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_ANR;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_DISABLED;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_TIMEOUT_REG;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL;
    private static final com.android.server.utils.quota.Category QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ;
    private static final java.lang.String QUOTA_TRACKER_SCHEDULE_LOGGED = ".schedulePersisted out-of-quota logged";
    private static final java.lang.String QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG = ".schedulePersisted()";
    private static final java.lang.String QUOTA_TRACKER_TIMEOUT_EJ_TAG = "timeout-ej";
    private static final java.lang.String QUOTA_TRACKER_TIMEOUT_REG_TAG = "timeout-reg";
    private static final java.lang.String QUOTA_TRACKER_TIMEOUT_TOTAL_TAG = "timeout-total";
    private static final java.lang.String QUOTA_TRACKER_TIMEOUT_UIJ_TAG = "timeout-uij";
    public static final int RARE_INDEX = 3;
    private static final long REQUIRE_NETWORK_CONSTRAINT_FOR_NETWORK_JOB_WORK_ITEMS = 241104082;
    static final long REQUIRE_NETWORK_PERMISSIONS_FOR_CONNECTIVITY_JOBS = 271850009;
    public static final int RESTRICTED_INDEX = 5;
    public static final long THROW_ON_UNSUPPORTED_BIAS_USAGE = 300477393;
    public static final int WORKING_INDEX = 1;
    public static java.time.Clock sElapsedRealtimeClock;
    private static final com.android.modules.expresslog.Histogram sEnqueuedJwiHighWaterMarkLogger;
    private static final com.android.modules.expresslog.Histogram sInitialJobEstimatedNetworkDownloadKBLogger;
    private static final com.android.modules.expresslog.Histogram sInitialJobEstimatedNetworkUploadKBLogger;
    private static final com.android.modules.expresslog.Histogram sInitialJwiEstimatedNetworkDownloadKBLogger;
    private static final com.android.modules.expresslog.Histogram sInitialJwiEstimatedNetworkUploadKBLogger;
    private static final com.android.modules.expresslog.Histogram sJobMinimumChunkKBLogger;
    private static final com.android.modules.expresslog.Histogram sJwiMinimumChunkKBLogger;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public static java.time.Clock sSystemClock;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public static java.time.Clock sUptimeMillisClock;

    @com.android.internal.annotations.VisibleForTesting
    public static android.app.usage.UsageStatsManagerInternal sUsageStatsManagerInternal;
    android.app.ActivityManagerInternal mActivityManagerInternal;
    private final com.android.server.usage.AppStandbyInternal mAppStandbyInternal;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.AppStateTrackerImpl mAppStateTracker;
    private final android.util.SparseBooleanArray mBackingUpUids;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.JobSchedulerService.BatteryStateTracker mBatteryStateTracker;
    private final android.os.BatteryStatsInternal mBatteryStatsInternal;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final java.util.function.Consumer<com.android.server.job.controllers.JobStatus> mCancelJobDueToUserRemovalConsumer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mChangedJobList;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.String> mCloudMediaProviderPackages;
    final com.android.server.job.JobConcurrencyManager mConcurrencyManager;
    private final com.android.server.job.controllers.ConnectivityController mConnectivityController;
    final com.android.server.job.JobSchedulerService.Constants mConstants;
    final com.android.server.job.JobSchedulerService.ConstantsObserver mConstantsObserver;
    final java.util.List<com.android.server.job.controllers.StateController> mControllers;
    final android.util.ArrayMap<java.lang.String, java.lang.Boolean> mDebuggableApps;
    private final com.android.server.job.controllers.DeviceIdleJobsController mDeviceIdleJobsController;
    final com.android.server.job.JobSchedulerService.JobHandler mHandler;
    private final java.util.function.Predicate<java.lang.Integer> mIsUidActivePredicate;
    final com.android.server.job.JobPackageTracker mJobPackageTracker;
    private final java.util.List<com.android.server.job.restrictions.JobRestriction> mJobRestrictions;
    final com.android.server.job.JobSchedulerService.JobSchedulerStub mJobSchedulerStub;
    private final java.util.concurrent.CountDownLatch mJobStoreLoadedLatch;
    private final java.lang.Runnable mJobTimeUpdater;
    final com.android.server.job.JobStore mJobs;
    private int mLastCancelledJobIndex;
    private final long[] mLastCancelledJobTimeElapsed;
    private final com.android.server.job.controllers.JobStatus[] mLastCancelledJobs;
    private int mLastCompletedJobIndex;
    private final long[] mLastCompletedJobTimeElapsed;
    private final com.android.server.job.controllers.JobStatus[] mLastCompletedJobs;
    com.android.server.DeviceIdleInternal mLocalDeviceIdleController;
    android.content.pm.PackageManagerInternal mLocalPM;
    final java.lang.Object mLock;
    private final com.android.server.job.JobSchedulerService.MaybeReadyJobQueueFunctor mMaybeQueueFunctor;
    private final com.android.server.job.PendingJobQueue mPendingJobQueue;

    @com.android.internal.annotations.GuardedBy({"mPendingJobReasonCache"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.SparseIntArray> mPendingJobReasonCache;

    @com.android.internal.annotations.GuardedBy({"mPermissionCache"})
    private final android.util.SparseArray<android.util.SparseArrayMap<java.lang.String, java.lang.Boolean>> mPermissionCache;
    private final com.android.server.job.controllers.PrefetchController mPrefetchController;
    private final com.android.server.job.controllers.QuotaController mQuotaController;
    private final com.android.server.utils.quota.CountQuotaTracker mQuotaTracker;
    private final com.android.server.job.JobSchedulerService.ReadyJobQueueFunctor mReadyQueueFunctor;
    boolean mReadyToRock;
    boolean mReportedActive;
    private final java.util.List<com.android.server.job.controllers.RestrictingController> mRestrictiveControllers;
    final com.android.server.job.JobSchedulerService.StandbyTracker mStandbyTracker;
    private final java.util.concurrent.CountDownLatch mStartControllerTrackingLatch;
    int[] mStartedUsers;
    private final com.android.server.job.controllers.StorageController mStorageController;
    private final com.android.server.job.controllers.TareController mTareController;
    private final android.content.BroadcastReceiver mTimeSetReceiver;
    final android.util.SparseIntArray mUidBiasOverride;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mUidCapabilities;
    private final android.app.IUidObserver mUidObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mUidProcStates;
    private final android.util.SparseSetArray<java.lang.String> mUidToPackageCache;
    private final android.os.RemoteCallbackList<android.app.job.IUserVisibleJobObserver> mUserVisibleJobObservers;
    public static final java.lang.String TAG = "JobScheduler";
    public static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    static {
        DEBUG_STANDBY = DEBUG;
        sSystemClock = java.time.Clock.systemUTC();
        sUptimeMillisClock = new com.android.server.job.JobSchedulerService.MySimpleClock(java.time.ZoneOffset.UTC) { // from class: com.android.server.job.JobSchedulerService.1
            @Override // com.android.server.job.JobSchedulerService.MySimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SystemClock.uptimeMillis();
            }
        };
        sElapsedRealtimeClock = new com.android.server.job.JobSchedulerService.MySimpleClock(java.time.ZoneOffset.UTC) { // from class: com.android.server.job.JobSchedulerService.2
            @Override // com.android.server.job.JobSchedulerService.MySimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SystemClock.elapsedRealtime();
            }
        };
        QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED = new com.android.server.utils.quota.Category(QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG);
        QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED = new com.android.server.utils.quota.Category(QUOTA_TRACKER_SCHEDULE_LOGGED);
        QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ = new com.android.server.utils.quota.Category(QUOTA_TRACKER_TIMEOUT_UIJ_TAG);
        QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ = new com.android.server.utils.quota.Category(QUOTA_TRACKER_TIMEOUT_EJ_TAG);
        QUOTA_TRACKER_CATEGORY_TIMEOUT_REG = new com.android.server.utils.quota.Category(QUOTA_TRACKER_TIMEOUT_REG_TAG);
        QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL = new com.android.server.utils.quota.Category(QUOTA_TRACKER_TIMEOUT_TOTAL_TAG);
        QUOTA_TRACKER_CATEGORY_ANR = new com.android.server.utils.quota.Category(QUOTA_TRACKER_ANR_TAG);
        QUOTA_TRACKER_CATEGORY_DISABLED = new com.android.server.utils.quota.Category(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        sEnqueuedJwiHighWaterMarkLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_w_uid_enqueued_work_items_high_water_mark", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.4f));
        sInitialJobEstimatedNetworkDownloadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_initial_job_estimated_network_download_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJwiEstimatedNetworkDownloadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_initial_jwi_estimated_network_download_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJobEstimatedNetworkUploadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_initial_job_estimated_network_upload_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sInitialJwiEstimatedNetworkUploadKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_initial_jwi_estimated_network_upload_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sJobMinimumChunkKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_w_uid_job_minimum_chunk_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.76f));
        sJwiMinimumChunkKBLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_w_uid_jwi_minimum_chunk_kilobytes", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(25, 0, 5.0f, 1.76f));
    }

    private static abstract class MySimpleClock extends java.time.Clock {
        private final java.time.ZoneId mZoneId;

        @Override // java.time.Clock, java.time.InstantSource
        public abstract long millis();

        MySimpleClock(java.time.ZoneId zoneId) {
            this.mZoneId = zoneId;
        }

        @Override // java.time.Clock
        public java.time.ZoneId getZone() {
            return this.mZoneId;
        }

        @Override // java.time.Clock, java.time.InstantSource
        public java.time.Clock withZone(java.time.ZoneId zoneId) {
            return new com.android.server.job.JobSchedulerService.MySimpleClock(zoneId) { // from class: com.android.server.job.JobSchedulerService.MySimpleClock.1
                @Override // com.android.server.job.JobSchedulerService.MySimpleClock, java.time.Clock, java.time.InstantSource
                public long millis() {
                    return com.android.server.job.JobSchedulerService.MySimpleClock.this.millis();
                }
            };
        }

        @Override // java.time.Clock, java.time.InstantSource
        public java.time.Instant instant() {
            return java.time.Instant.ofEpochMilli(millis());
        }
    }

    private class ConstantsObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener, com.android.server.tare.EconomyManagerInternal.TareStateChangeListener {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mCacheConfigChanges;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private android.provider.DeviceConfig.Properties mLastPropertiesPulled;

        private ConstantsObserver() {
            this.mCacheConfigChanges = false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        public java.lang.String getValueLocked(java.lang.String str) {
            if (this.mLastPropertiesPulled == null) {
                return null;
            }
            return this.mLastPropertiesPulled.getString(str, (java.lang.String) null);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void setCacheConfigChangesLocked(boolean z) {
            if (z && !this.mCacheConfigChanges) {
                this.mLastPropertiesPulled = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[0]);
            } else {
                this.mLastPropertiesPulled = null;
            }
            this.mCacheConfigChanges = z;
        }

        public void start() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("jobscheduler", com.android.server.AppSchedulingModuleThread.getExecutor(), this);
            com.android.server.tare.EconomyManagerInternal economyManagerInternal = (com.android.server.tare.EconomyManagerInternal) com.android.server.LocalServices.getService(com.android.server.tare.EconomyManagerInternal.class);
            economyManagerInternal.registerTareStateChangeListener(this, 536870912);
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                com.android.server.job.JobSchedulerService.this.mConstants.updateTareSettingsLocked(economyManagerInternal.getEnabledMode(536870912));
            }
            onPropertiesChanged(android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[0]));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            char c;
            for (int i = 0; i < com.android.server.job.JobSchedulerService.this.mControllers.size(); i++) {
                com.android.server.job.JobSchedulerService.this.mControllers.get(i).prepareForUpdatedConstantsLocked();
            }
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    if (this.mCacheConfigChanges) {
                        this.mLastPropertiesPulled = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[0]);
                    }
                    boolean z = false;
                    boolean z2 = false;
                    boolean z3 = false;
                    boolean z4 = false;
                    for (java.lang.String str : properties.getKeyset()) {
                        if (str != null) {
                            if (com.android.server.job.JobSchedulerService.DEBUG || this.mCacheConfigChanges) {
                                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "DeviceConfig " + str + " changed to " + properties.getString(str, (java.lang.String) null));
                            }
                            switch (str.hashCode()) {
                                case -1847335205:
                                    if (str.equals("es_u_timeout_total_count")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1787939498:
                                    if (str.equals("aq_schedule_count")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1644162308:
                                    if (str.equals("enable_api_quotas")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1470844605:
                                    if (str.equals("runtime_min_ej_guarantee_ms")) {
                                        c = ' ';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1313417082:
                                    if (str.equals("conn_use_cell_signal_strength")) {
                                        c = 27;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1272362358:
                                    if (str.equals("prefetch_force_batch_relax_threshold_ms")) {
                                        c = 29;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1215861621:
                                    if (str.equals("conn_update_all_jobs_min_interval_ms")) {
                                        c = 28;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1194025741:
                                    if (str.equals("enable_execution_safeguards_udc")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1062323940:
                                    if (str.equals("aq_schedule_window_ms")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -953747139:
                                    if (str.equals("conn_transport_batch_threshold")) {
                                        c = 26;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -941023983:
                                    if (str.equals("runtime_min_guarantee_ms")) {
                                        c = 31;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -885226119:
                                    if (str.equals("min_ready_cpu_only_jobs_count")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -803030002:
                                    if (str.equals("runtime_ui_limit_ms")) {
                                        c = '\"';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -722508861:
                                    if (str.equals("moderate_use_factor")) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -687279910:
                                    if (str.equals("es_u_anr_count")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -544478093:
                                    if (str.equals("runtime_min_ui_data_transfer_guarantee_ms")) {
                                        c = '$';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -492250078:
                                    if (str.equals("conn_low_signal_strength_relax_frac")) {
                                        c = 24;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -376691020:
                                    if (str.equals("max_num_persisted_job_work_items")) {
                                        c = '\'';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -138964320:
                                    if (str.equals("es_u_anr_window_ms")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -109453036:
                                    if (str.equals("aq_schedule_return_failure")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -57293457:
                                    if (str.equals("conn_congestion_delay_frac")) {
                                        c = 22;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -45782187:
                                    if (str.equals("max_non_active_job_batch_delay_ms")) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 213091160:
                                    if (str.equals("runtime_use_data_estimates_for_limits")) {
                                        c = '&';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 232730669:
                                    if (str.equals("es_u_timeout_uij_count")) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 263198386:
                                    if (str.equals("min_exp_backoff_time_ms")) {
                                        c = 20;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 289418623:
                                    if (str.equals("heavy_use_factor")) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 316308971:
                                    if (str.equals("es_u_timeout_reg_count")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 322281628:
                                    if (str.equals("es_u_timeout_window_ms")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 408648654:
                                    if (str.equals("es_u_timeout_ej_count")) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 709194164:
                                    if (str.equals("min_linear_backoff_time_ms")) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 811737328:
                                    if (str.equals("runtime_cumulative_ui_limit_ms")) {
                                        c = '%';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 963672867:
                                    if (str.equals("conn_max_connectivity_job_batch_delay_ms")) {
                                        c = 25;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1004645316:
                                    if (str.equals("min_ready_non_active_jobs_count")) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1185412831:
                                    if (str.equals("system_stop_to_failure_ratio")) {
                                        c = 21;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1185743293:
                                    if (str.equals("aq_schedule_throw_exception")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1302735555:
                                    if (str.equals("persist_in_split_files")) {
                                        c = '(';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1317782410:
                                    if (str.equals("max_cpu_only_job_batch_delay_ms")) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1396959553:
                                    if (str.equals("runtime_min_ui_data_transfer_guarantee_buffer_factor")) {
                                        c = '#';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1470808280:
                                    if (str.equals("runtime_free_quota_max_limit_ms")) {
                                        c = 30;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1680706484:
                                    if (str.equals("runtime_min_ui_guarantee_ms")) {
                                        c = '!';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1692637170:
                                    if (str.equals("conn_prefetch_relax_frac")) {
                                        c = 23;
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
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case '\b':
                                case '\t':
                                case '\n':
                                case 11:
                                case '\f':
                                    if (!z) {
                                        com.android.server.job.JobSchedulerService.this.mConstants.updateApiQuotaConstantsLocked();
                                        com.android.server.job.JobSchedulerService.this.updateQuotaTracker();
                                        z = true;
                                        break;
                                    }
                                    break;
                                case '\r':
                                case 14:
                                case 15:
                                case 16:
                                    com.android.server.job.JobSchedulerService.this.mConstants.updateBatchingConstantsLocked();
                                    break;
                                case 17:
                                case 18:
                                    com.android.server.job.JobSchedulerService.this.mConstants.updateUseFactorConstantsLocked();
                                    break;
                                case 19:
                                case 20:
                                case 21:
                                    com.android.server.job.JobSchedulerService.this.mConstants.updateBackoffConstantsLocked();
                                    break;
                                case 22:
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                case 28:
                                    com.android.server.job.JobSchedulerService.this.mConstants.updateConnectivityConstantsLocked();
                                    break;
                                case 29:
                                    com.android.server.job.JobSchedulerService.this.mConstants.updatePrefetchConstantsLocked();
                                    break;
                                case 30:
                                case 31:
                                case ' ':
                                case '!':
                                case '\"':
                                case '#':
                                case '$':
                                case '%':
                                case '&':
                                    if (!z4) {
                                        com.android.server.job.JobSchedulerService.this.mConstants.updateRuntimeConstantsLocked();
                                        z4 = true;
                                        break;
                                    }
                                    break;
                                case '\'':
                                case '(':
                                    if (!z3) {
                                        com.android.server.job.JobSchedulerService.this.mConstants.updatePersistingConstantsLocked();
                                        com.android.server.job.JobSchedulerService.this.mJobs.setUseSplitFiles(com.android.server.job.JobSchedulerService.this.mConstants.PERSIST_IN_SPLIT_FILES);
                                        z3 = true;
                                        break;
                                    }
                                    break;
                                default:
                                    if (str.startsWith("concurrency_") && !z2) {
                                        com.android.server.job.JobSchedulerService.this.mConcurrencyManager.updateConfigLocked();
                                        z2 = true;
                                        break;
                                    } else {
                                        for (int i2 = 0; i2 < com.android.server.job.JobSchedulerService.this.mControllers.size(); i2++) {
                                            com.android.server.job.JobSchedulerService.this.mControllers.get(i2).processConstantLocked(properties, str);
                                        }
                                        break;
                                    }
                            }
                        }
                    }
                    for (int i3 = 0; i3 < com.android.server.job.JobSchedulerService.this.mControllers.size(); i3++) {
                        com.android.server.job.JobSchedulerService.this.mControllers.get(i3).onConstantsUpdatedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.job.JobSchedulerService.this.mHandler.sendEmptyMessage(1);
        }

        @Override // com.android.server.tare.EconomyManagerInternal.TareStateChangeListener
        public void onTareEnabledModeChanged(int i) {
            if (com.android.server.job.JobSchedulerService.this.mConstants.updateTareSettingsLocked(i)) {
                for (int i2 = 0; i2 < com.android.server.job.JobSchedulerService.this.mControllers.size(); i2++) {
                    com.android.server.job.JobSchedulerService.this.mControllers.get(i2).onConstantsUpdatedLocked();
                }
                com.android.server.job.JobSchedulerService.this.onControllerStateChanged(null);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateQuotaTracker() {
        this.mQuotaTracker.setEnabled(this.mConstants.ENABLE_API_QUOTAS || this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED, this.mConstants.API_QUOTA_SCHEDULE_COUNT, this.mConstants.API_QUOTA_SCHEDULE_WINDOW_MS);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_TIMEOUT_REG, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT, this.mConstants.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_ANR, this.mConstants.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT, this.mConstants.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS);
    }

    public static class Constants {
        private static final int DEFAULT_API_QUOTA_SCHEDULE_COUNT = 250;
        private static final boolean DEFAULT_API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = false;
        private static final boolean DEFAULT_API_QUOTA_SCHEDULE_THROW_EXCEPTION = true;
        private static final long DEFAULT_API_QUOTA_SCHEDULE_WINDOW_MS = 60000;
        private static final float DEFAULT_CONN_CONGESTION_DELAY_FRAC = 0.5f;
        private static final float DEFAULT_CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = 0.5f;
        private static final long DEFAULT_CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = 1860000;
        private static final float DEFAULT_CONN_PREFETCH_RELAX_FRAC = 0.5f;
        private static final long DEFAULT_CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = 60000;
        private static final boolean DEFAULT_CONN_USE_CELL_SIGNAL_STRENGTH = true;
        private static final boolean DEFAULT_ENABLE_API_QUOTAS = true;
        private static final boolean DEFAULT_ENABLE_EXECUTION_SAFEGUARDS_UDC = true;
        private static final int DEFAULT_EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = 3;
        private static final long DEFAULT_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = 21600000;
        private static final int DEFAULT_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = 5;
        private static final int DEFAULT_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = 3;
        private static final int DEFAULT_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = 10;
        private static final int DEFAULT_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = 2;
        private static final long DEFAULT_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = 86400000;
        private static final float DEFAULT_HEAVY_USE_FACTOR = 0.9f;
        private static final long DEFAULT_MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = 1860000;
        private static final long DEFAULT_MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = 1860000;
        static final int DEFAULT_MAX_NUM_PERSISTED_JOB_WORK_ITEMS = 100000;
        private static final long DEFAULT_MIN_EXP_BACKOFF_TIME_MS = 10000;
        private static final long DEFAULT_MIN_LINEAR_BACKOFF_TIME_MS = 10000;
        private static final float DEFAULT_MODERATE_USE_FACTOR = 0.5f;
        static final boolean DEFAULT_PERSIST_IN_SPLIT_FILES = true;
        private static final long DEFAULT_PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS = 3600000;
        public static final long DEFAULT_RUNTIME_CUMULATIVE_UI_LIMIT_MS = 86400000;

        @com.android.internal.annotations.VisibleForTesting
        public static final long DEFAULT_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = 1800000;

        @com.android.internal.annotations.VisibleForTesting
        public static final long DEFAULT_RUNTIME_MIN_EJ_GUARANTEE_MS = 180000;

        @com.android.internal.annotations.VisibleForTesting
        public static final long DEFAULT_RUNTIME_MIN_GUARANTEE_MS = 600000;
        public static final float DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = 1.35f;
        public static final long DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS;
        public static final long DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS;
        public static final long DEFAULT_RUNTIME_UI_LIMIT_MS;
        public static final boolean DEFAULT_RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = false;
        private static final int DEFAULT_SYSTEM_STOP_TO_FAILURE_RATIO = 3;
        private static final java.lang.String KEY_API_QUOTA_SCHEDULE_COUNT = "aq_schedule_count";
        private static final java.lang.String KEY_API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = "aq_schedule_return_failure";
        private static final java.lang.String KEY_API_QUOTA_SCHEDULE_THROW_EXCEPTION = "aq_schedule_throw_exception";
        private static final java.lang.String KEY_API_QUOTA_SCHEDULE_WINDOW_MS = "aq_schedule_window_ms";
        private static final java.lang.String KEY_CONN_CONGESTION_DELAY_FRAC = "conn_congestion_delay_frac";
        private static final java.lang.String KEY_CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = "conn_low_signal_strength_relax_frac";
        private static final java.lang.String KEY_CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = "conn_max_connectivity_job_batch_delay_ms";
        private static final java.lang.String KEY_CONN_PREFETCH_RELAX_FRAC = "conn_prefetch_relax_frac";
        private static final java.lang.String KEY_CONN_TRANSPORT_BATCH_THRESHOLD = "conn_transport_batch_threshold";
        private static final java.lang.String KEY_CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = "conn_update_all_jobs_min_interval_ms";
        private static final java.lang.String KEY_CONN_USE_CELL_SIGNAL_STRENGTH = "conn_use_cell_signal_strength";
        private static final java.lang.String KEY_ENABLE_API_QUOTAS = "enable_api_quotas";
        private static final java.lang.String KEY_ENABLE_EXECUTION_SAFEGUARDS_UDC = "enable_execution_safeguards_udc";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = "es_u_anr_count";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = "es_u_anr_window_ms";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = "es_u_timeout_ej_count";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = "es_u_timeout_reg_count";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = "es_u_timeout_total_count";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = "es_u_timeout_uij_count";
        private static final java.lang.String KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = "es_u_timeout_window_ms";
        private static final java.lang.String KEY_HEAVY_USE_FACTOR = "heavy_use_factor";
        private static final java.lang.String KEY_MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = "max_cpu_only_job_batch_delay_ms";
        private static final java.lang.String KEY_MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = "max_non_active_job_batch_delay_ms";
        private static final java.lang.String KEY_MAX_NUM_PERSISTED_JOB_WORK_ITEMS = "max_num_persisted_job_work_items";
        private static final java.lang.String KEY_MIN_EXP_BACKOFF_TIME_MS = "min_exp_backoff_time_ms";
        private static final java.lang.String KEY_MIN_LINEAR_BACKOFF_TIME_MS = "min_linear_backoff_time_ms";
        private static final java.lang.String KEY_MIN_READY_CPU_ONLY_JOBS_COUNT = "min_ready_cpu_only_jobs_count";
        private static final java.lang.String KEY_MIN_READY_NON_ACTIVE_JOBS_COUNT = "min_ready_non_active_jobs_count";
        private static final java.lang.String KEY_MODERATE_USE_FACTOR = "moderate_use_factor";
        private static final java.lang.String KEY_PERSIST_IN_SPLIT_FILES = "persist_in_split_files";
        private static final java.lang.String KEY_PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS = "prefetch_force_batch_relax_threshold_ms";
        private static final java.lang.String KEY_RUNTIME_CUMULATIVE_UI_LIMIT_MS = "runtime_cumulative_ui_limit_ms";
        private static final java.lang.String KEY_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = "runtime_free_quota_max_limit_ms";
        private static final java.lang.String KEY_RUNTIME_MIN_EJ_GUARANTEE_MS = "runtime_min_ej_guarantee_ms";
        private static final java.lang.String KEY_RUNTIME_MIN_GUARANTEE_MS = "runtime_min_guarantee_ms";
        private static final java.lang.String KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = "runtime_min_ui_data_transfer_guarantee_buffer_factor";
        private static final java.lang.String KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = "runtime_min_ui_data_transfer_guarantee_ms";
        private static final java.lang.String KEY_RUNTIME_MIN_UI_GUARANTEE_MS = "runtime_min_ui_guarantee_ms";
        private static final java.lang.String KEY_RUNTIME_UI_LIMIT_MS = "runtime_ui_limit_ms";
        private static final java.lang.String KEY_RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = "runtime_use_data_estimates_for_limits";
        private static final java.lang.String KEY_SYSTEM_STOP_TO_FAILURE_RATIO = "system_stop_to_failure_ratio";
        private static final int DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT = java.lang.Math.min(3, com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3);
        private static final int DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT = java.lang.Math.min(5, com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3);
        private static final android.util.SparseIntArray DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD = new android.util.SparseIntArray();
        int MIN_READY_CPU_ONLY_JOBS_COUNT = DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT;
        int MIN_READY_NON_ACTIVE_JOBS_COUNT = DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT;
        long MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = 1860000;
        long MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = 1860000;
        float HEAVY_USE_FACTOR = DEFAULT_HEAVY_USE_FACTOR;
        float MODERATE_USE_FACTOR = 0.5f;
        long MIN_LINEAR_BACKOFF_TIME_MS = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        long MIN_EXP_BACKOFF_TIME_MS = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        int SYSTEM_STOP_TO_FAILURE_RATIO = 3;
        public float CONN_CONGESTION_DELAY_FRAC = 0.5f;
        public float CONN_PREFETCH_RELAX_FRAC = 0.5f;
        public boolean CONN_USE_CELL_SIGNAL_STRENGTH = true;
        public long CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = 60000;
        public float CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = 0.5f;
        public android.util.SparseIntArray CONN_TRANSPORT_BATCH_THRESHOLD = new android.util.SparseIntArray();
        public long CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = 1860000;
        public long PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS = 3600000;
        public boolean ENABLE_API_QUOTAS = true;
        public int API_QUOTA_SCHEDULE_COUNT = 250;
        public long API_QUOTA_SCHEDULE_WINDOW_MS = 60000;
        public boolean API_QUOTA_SCHEDULE_THROW_EXCEPTION = true;
        public boolean API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = false;
        public boolean ENABLE_EXECUTION_SAFEGUARDS_UDC = true;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = 2;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = 5;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = 3;
        public int EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = 10;
        public long EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = 86400000;
        public int EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = 3;
        public long EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = DEFAULT_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS;
        public long RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = 1800000;
        public long RUNTIME_MIN_GUARANTEE_MS = 600000;
        public long RUNTIME_MIN_EJ_GUARANTEE_MS = 180000;
        public long RUNTIME_MIN_UI_GUARANTEE_MS = DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS;
        public long RUNTIME_UI_LIMIT_MS = DEFAULT_RUNTIME_UI_LIMIT_MS;
        public float RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = 1.35f;
        public long RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS;
        public long RUNTIME_CUMULATIVE_UI_LIMIT_MS = 86400000;
        public boolean RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = false;
        public boolean PERSIST_IN_SPLIT_FILES = true;
        public int MAX_NUM_PERSISTED_JOB_WORK_ITEMS = DEFAULT_MAX_NUM_PERSISTED_JOB_WORK_ITEMS;
        public boolean USE_TARE_POLICY = false;

        static {
            DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD.put(0, java.lang.Math.min(3, com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3));
            DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS = java.lang.Math.max(DEFAULT_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS, 600000L);
            DEFAULT_RUNTIME_UI_LIMIT_MS = java.lang.Math.max(43200000L, 1800000L);
            DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = java.lang.Math.max(600000L, DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS);
        }

        public Constants() {
            copyTransportBatchThresholdDefaults();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateBatchingConstantsLocked() {
            this.MIN_READY_CPU_ONLY_JOBS_COUNT = java.lang.Math.max(0, java.lang.Math.min(com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3, android.provider.DeviceConfig.getInt("jobscheduler", KEY_MIN_READY_CPU_ONLY_JOBS_COUNT, DEFAULT_MIN_READY_CPU_ONLY_JOBS_COUNT)));
            this.MIN_READY_NON_ACTIVE_JOBS_COUNT = java.lang.Math.max(0, java.lang.Math.min(com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3, android.provider.DeviceConfig.getInt("jobscheduler", KEY_MIN_READY_NON_ACTIVE_JOBS_COUNT, DEFAULT_MIN_READY_NON_ACTIVE_JOBS_COUNT)));
            this.MAX_CPU_ONLY_JOB_BATCH_DELAY_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_MAX_CPU_ONLY_JOB_BATCH_DELAY_MS, 1860000L);
            this.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS, 1860000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateUseFactorConstantsLocked() {
            this.HEAVY_USE_FACTOR = android.provider.DeviceConfig.getFloat("jobscheduler", KEY_HEAVY_USE_FACTOR, DEFAULT_HEAVY_USE_FACTOR);
            this.MODERATE_USE_FACTOR = android.provider.DeviceConfig.getFloat("jobscheduler", KEY_MODERATE_USE_FACTOR, 0.5f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateBackoffConstantsLocked() {
            this.MIN_LINEAR_BACKOFF_TIME_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_MIN_LINEAR_BACKOFF_TIME_MS, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            this.MIN_EXP_BACKOFF_TIME_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_MIN_EXP_BACKOFF_TIME_MS, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            this.SYSTEM_STOP_TO_FAILURE_RATIO = android.provider.DeviceConfig.getInt("jobscheduler", KEY_SYSTEM_STOP_TO_FAILURE_RATIO, 3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateConnectivityConstantsLocked() {
            this.CONN_CONGESTION_DELAY_FRAC = android.provider.DeviceConfig.getFloat("jobscheduler", KEY_CONN_CONGESTION_DELAY_FRAC, 0.5f);
            this.CONN_PREFETCH_RELAX_FRAC = android.provider.DeviceConfig.getFloat("jobscheduler", KEY_CONN_PREFETCH_RELAX_FRAC, 0.5f);
            this.CONN_USE_CELL_SIGNAL_STRENGTH = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_CONN_USE_CELL_SIGNAL_STRENGTH, true);
            this.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS, 60000L);
            this.CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC = android.provider.DeviceConfig.getFloat("jobscheduler", KEY_CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC, 0.5f);
            java.lang.String string = android.provider.DeviceConfig.getString("jobscheduler", KEY_CONN_TRANSPORT_BATCH_THRESHOLD, (java.lang.String) null);
            android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
            this.CONN_TRANSPORT_BATCH_THRESHOLD.clear();
            try {
                keyValueListParser.setString(string);
                for (int size = keyValueListParser.size() - 1; size >= 0; size--) {
                    java.lang.String keyAt = keyValueListParser.keyAt(size);
                    try {
                        this.CONN_TRANSPORT_BATCH_THRESHOLD.put(java.lang.Integer.parseInt(keyAt), java.lang.Math.max(0, java.lang.Math.min(com.android.server.job.JobConcurrencyManager.DEFAULT_CONCURRENCY_LIMIT / 3, keyValueListParser.getInt(keyAt, 1))));
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "Bad transport string", e);
                    }
                }
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.wtf(com.android.server.job.JobSchedulerService.TAG, "Bad string for conn_transport_batch_threshold", e2);
                copyTransportBatchThresholdDefaults();
            }
            this.CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS = java.lang.Math.max(0L, java.lang.Math.min(86400000L, android.provider.DeviceConfig.getLong("jobscheduler", KEY_CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS, 1860000L)));
        }

        private void copyTransportBatchThresholdDefaults() {
            for (int size = DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD.size() - 1; size >= 0; size--) {
                this.CONN_TRANSPORT_BATCH_THRESHOLD.put(DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD.keyAt(size), DEFAULT_CONN_TRANSPORT_BATCH_THRESHOLD.valueAt(size));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePersistingConstantsLocked() {
            this.PERSIST_IN_SPLIT_FILES = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_PERSIST_IN_SPLIT_FILES, true);
            this.MAX_NUM_PERSISTED_JOB_WORK_ITEMS = android.provider.DeviceConfig.getInt("jobscheduler", KEY_MAX_NUM_PERSISTED_JOB_WORK_ITEMS, DEFAULT_MAX_NUM_PERSISTED_JOB_WORK_ITEMS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePrefetchConstantsLocked() {
            this.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS, 3600000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateApiQuotaConstantsLocked() {
            this.ENABLE_API_QUOTAS = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_ENABLE_API_QUOTAS, true);
            this.ENABLE_EXECUTION_SAFEGUARDS_UDC = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_ENABLE_EXECUTION_SAFEGUARDS_UDC, true);
            this.API_QUOTA_SCHEDULE_COUNT = java.lang.Math.max(250, android.provider.DeviceConfig.getInt("jobscheduler", KEY_API_QUOTA_SCHEDULE_COUNT, 250));
            this.API_QUOTA_SCHEDULE_WINDOW_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_API_QUOTA_SCHEDULE_WINDOW_MS, 60000L);
            this.API_QUOTA_SCHEDULE_THROW_EXCEPTION = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_API_QUOTA_SCHEDULE_THROW_EXCEPTION, true);
            this.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT = android.provider.DeviceConfig.getBoolean("jobscheduler", KEY_API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT, false);
            this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT = java.lang.Math.max(2, android.provider.DeviceConfig.getInt("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, 2));
            this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT = java.lang.Math.max(2, android.provider.DeviceConfig.getInt("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, 5));
            this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT = java.lang.Math.max(2, android.provider.DeviceConfig.getInt("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT, 3));
            this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT = java.lang.Math.max(java.lang.Math.max(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, java.lang.Math.max(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT)), android.provider.DeviceConfig.getInt("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT, 10));
            this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS, 86400000L);
            this.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT = java.lang.Math.max(1, android.provider.DeviceConfig.getInt("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_ANR_COUNT, 3));
            this.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS = android.provider.DeviceConfig.getLong("jobscheduler", KEY_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS, DEFAULT_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateRuntimeConstantsLocked() {
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[]{KEY_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, KEY_RUNTIME_MIN_GUARANTEE_MS, KEY_RUNTIME_MIN_EJ_GUARANTEE_MS, KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR, KEY_RUNTIME_MIN_UI_GUARANTEE_MS, KEY_RUNTIME_UI_LIMIT_MS, KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS, KEY_RUNTIME_CUMULATIVE_UI_LIMIT_MS, KEY_RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS});
            this.RUNTIME_MIN_GUARANTEE_MS = java.lang.Math.max(600000L, properties.getLong(KEY_RUNTIME_MIN_GUARANTEE_MS, 600000L));
            this.RUNTIME_MIN_EJ_GUARANTEE_MS = java.lang.Math.max(60000L, properties.getLong(KEY_RUNTIME_MIN_EJ_GUARANTEE_MS, 180000L));
            this.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS = java.lang.Math.max(this.RUNTIME_MIN_GUARANTEE_MS, properties.getLong(KEY_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, 1800000L));
            this.RUNTIME_MIN_UI_GUARANTEE_MS = java.lang.Math.max(this.RUNTIME_MIN_GUARANTEE_MS, properties.getLong(KEY_RUNTIME_MIN_UI_GUARANTEE_MS, DEFAULT_RUNTIME_MIN_UI_GUARANTEE_MS));
            this.RUNTIME_UI_LIMIT_MS = java.lang.Math.max(this.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, java.lang.Math.max(this.RUNTIME_MIN_UI_GUARANTEE_MS, properties.getLong(KEY_RUNTIME_UI_LIMIT_MS, DEFAULT_RUNTIME_UI_LIMIT_MS)));
            this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR = java.lang.Math.max(1.0f, properties.getFloat(KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR, 1.35f));
            this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS = java.lang.Math.max(this.RUNTIME_MIN_UI_GUARANTEE_MS, properties.getLong(KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS, DEFAULT_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS));
            this.RUNTIME_CUMULATIVE_UI_LIMIT_MS = java.lang.Math.max(this.RUNTIME_UI_LIMIT_MS, properties.getLong(KEY_RUNTIME_CUMULATIVE_UI_LIMIT_MS, 86400000L));
            this.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS = properties.getBoolean(KEY_RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean updateTareSettingsLocked(int i) {
            boolean z = i == 1;
            if (this.USE_TARE_POLICY == z) {
                return false;
            }
            this.USE_TARE_POLICY = z;
            return true;
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Settings:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_MIN_READY_CPU_ONLY_JOBS_COUNT, java.lang.Integer.valueOf(this.MIN_READY_CPU_ONLY_JOBS_COUNT)).println();
            indentingPrintWriter.print(KEY_MIN_READY_NON_ACTIVE_JOBS_COUNT, java.lang.Integer.valueOf(this.MIN_READY_NON_ACTIVE_JOBS_COUNT)).println();
            indentingPrintWriter.print(KEY_MAX_CPU_ONLY_JOB_BATCH_DELAY_MS, java.lang.Long.valueOf(this.MAX_CPU_ONLY_JOB_BATCH_DELAY_MS)).println();
            indentingPrintWriter.print(KEY_MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS, java.lang.Long.valueOf(this.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS)).println();
            indentingPrintWriter.print(KEY_HEAVY_USE_FACTOR, java.lang.Float.valueOf(this.HEAVY_USE_FACTOR)).println();
            indentingPrintWriter.print(KEY_MODERATE_USE_FACTOR, java.lang.Float.valueOf(this.MODERATE_USE_FACTOR)).println();
            indentingPrintWriter.print(KEY_MIN_LINEAR_BACKOFF_TIME_MS, java.lang.Long.valueOf(this.MIN_LINEAR_BACKOFF_TIME_MS)).println();
            indentingPrintWriter.print(KEY_MIN_EXP_BACKOFF_TIME_MS, java.lang.Long.valueOf(this.MIN_EXP_BACKOFF_TIME_MS)).println();
            indentingPrintWriter.print(KEY_SYSTEM_STOP_TO_FAILURE_RATIO, java.lang.Integer.valueOf(this.SYSTEM_STOP_TO_FAILURE_RATIO)).println();
            indentingPrintWriter.print(KEY_CONN_CONGESTION_DELAY_FRAC, java.lang.Float.valueOf(this.CONN_CONGESTION_DELAY_FRAC)).println();
            indentingPrintWriter.print(KEY_CONN_PREFETCH_RELAX_FRAC, java.lang.Float.valueOf(this.CONN_PREFETCH_RELAX_FRAC)).println();
            indentingPrintWriter.print(KEY_CONN_USE_CELL_SIGNAL_STRENGTH, java.lang.Boolean.valueOf(this.CONN_USE_CELL_SIGNAL_STRENGTH)).println();
            indentingPrintWriter.print(KEY_CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS, java.lang.Long.valueOf(this.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS)).println();
            indentingPrintWriter.print(KEY_CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC, java.lang.Float.valueOf(this.CONN_LOW_SIGNAL_STRENGTH_RELAX_FRAC)).println();
            indentingPrintWriter.print(KEY_CONN_TRANSPORT_BATCH_THRESHOLD, this.CONN_TRANSPORT_BATCH_THRESHOLD.toString()).println();
            indentingPrintWriter.print(KEY_CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS, java.lang.Long.valueOf(this.CONN_MAX_CONNECTIVITY_JOB_BATCH_DELAY_MS)).println();
            indentingPrintWriter.print(KEY_PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS, java.lang.Long.valueOf(this.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS)).println();
            indentingPrintWriter.print(KEY_ENABLE_API_QUOTAS, java.lang.Boolean.valueOf(this.ENABLE_API_QUOTAS)).println();
            indentingPrintWriter.print(KEY_API_QUOTA_SCHEDULE_COUNT, java.lang.Integer.valueOf(this.API_QUOTA_SCHEDULE_COUNT)).println();
            indentingPrintWriter.print(KEY_API_QUOTA_SCHEDULE_WINDOW_MS, java.lang.Long.valueOf(this.API_QUOTA_SCHEDULE_WINDOW_MS)).println();
            indentingPrintWriter.print(KEY_API_QUOTA_SCHEDULE_THROW_EXCEPTION, java.lang.Boolean.valueOf(this.API_QUOTA_SCHEDULE_THROW_EXCEPTION)).println();
            indentingPrintWriter.print(KEY_API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT, java.lang.Boolean.valueOf(this.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT)).println();
            indentingPrintWriter.print(KEY_ENABLE_EXECUTION_SAFEGUARDS_UDC, java.lang.Boolean.valueOf(this.ENABLE_EXECUTION_SAFEGUARDS_UDC)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT, java.lang.Integer.valueOf(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_UIJ_COUNT)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT, java.lang.Integer.valueOf(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_EJ_COUNT)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT, java.lang.Integer.valueOf(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_REG_COUNT)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT, java.lang.Integer.valueOf(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_TOTAL_COUNT)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS, java.lang.Long.valueOf(this.EXECUTION_SAFEGUARDS_UDC_TIMEOUT_WINDOW_MS)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_ANR_COUNT, java.lang.Integer.valueOf(this.EXECUTION_SAFEGUARDS_UDC_ANR_COUNT)).println();
            indentingPrintWriter.print(KEY_EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS, java.lang.Long.valueOf(this.EXECUTION_SAFEGUARDS_UDC_ANR_WINDOW_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_MIN_GUARANTEE_MS, java.lang.Long.valueOf(this.RUNTIME_MIN_GUARANTEE_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_MIN_EJ_GUARANTEE_MS, java.lang.Long.valueOf(this.RUNTIME_MIN_EJ_GUARANTEE_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_FREE_QUOTA_MAX_LIMIT_MS, java.lang.Long.valueOf(this.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_MIN_UI_GUARANTEE_MS, java.lang.Long.valueOf(this.RUNTIME_MIN_UI_GUARANTEE_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_UI_LIMIT_MS, java.lang.Long.valueOf(this.RUNTIME_UI_LIMIT_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR, java.lang.Float.valueOf(this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR)).println();
            indentingPrintWriter.print(KEY_RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS, java.lang.Long.valueOf(this.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_CUMULATIVE_UI_LIMIT_MS, java.lang.Long.valueOf(this.RUNTIME_CUMULATIVE_UI_LIMIT_MS)).println();
            indentingPrintWriter.print(KEY_RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS, java.lang.Boolean.valueOf(this.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS)).println();
            indentingPrintWriter.print(KEY_PERSIST_IN_SPLIT_FILES, java.lang.Boolean.valueOf(this.PERSIST_IN_SPLIT_FILES)).println();
            indentingPrintWriter.print(KEY_MAX_NUM_PERSISTED_JOB_WORK_ITEMS, java.lang.Integer.valueOf(this.MAX_NUM_PERSISTED_JOB_WORK_ITEMS)).println();
            indentingPrintWriter.print("enable_tare", java.lang.Boolean.valueOf(this.USE_TARE_POLICY)).println();
            indentingPrintWriter.decreaseIndent();
        }

        void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
            protoOutputStream.write(1120986464285L, this.MIN_READY_NON_ACTIVE_JOBS_COUNT);
            protoOutputStream.write(1112396529694L, this.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS);
            protoOutputStream.write(1103806595080L, this.HEAVY_USE_FACTOR);
            protoOutputStream.write(1103806595081L, this.MODERATE_USE_FACTOR);
            protoOutputStream.write(1112396529681L, this.MIN_LINEAR_BACKOFF_TIME_MS);
            protoOutputStream.write(1112396529682L, this.MIN_EXP_BACKOFF_TIME_MS);
            protoOutputStream.write(1103806595093L, this.CONN_CONGESTION_DELAY_FRAC);
            protoOutputStream.write(1103806595094L, this.CONN_PREFETCH_RELAX_FRAC);
            protoOutputStream.write(1133871366175L, this.ENABLE_API_QUOTAS);
            protoOutputStream.write(1120986464288L, this.API_QUOTA_SCHEDULE_COUNT);
            protoOutputStream.write(1112396529697L, this.API_QUOTA_SCHEDULE_WINDOW_MS);
            protoOutputStream.write(1133871366178L, this.API_QUOTA_SCHEDULE_THROW_EXCEPTION);
            protoOutputStream.write(1133871366179L, this.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT);
        }
    }

    @android.annotation.Nullable
    public static java.lang.String getPackageName(android.content.Intent intent) {
        android.net.Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }

    public android.content.Context getTestableContext() {
        return getContext();
    }

    public java.lang.Object getLock() {
        return this.mLock;
    }

    public com.android.server.job.JobStore getJobStore() {
        return this.mJobs;
    }

    public com.android.server.job.JobSchedulerService.Constants getConstants() {
        return this.mConstants;
    }

    @android.annotation.NonNull
    com.android.server.job.PendingJobQueue getPendingJobQueue() {
        return this.mPendingJobQueue;
    }

    @android.annotation.NonNull
    public android.os.WorkSource deriveWorkSource(int i, @android.annotation.Nullable java.lang.String str) {
        if (!android.os.WorkSource.isChainedBatteryAttributionEnabled(getContext())) {
            return str == null ? new android.os.WorkSource(i) : new android.os.WorkSource(i, str);
        }
        android.os.WorkSource workSource = new android.os.WorkSource();
        workSource.createWorkChain().addNode(i, str).addNode(1000, TAG);
        return workSource;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public android.util.ArraySet<java.lang.String> getPackagesForUidLocked(int i) {
        android.util.ArraySet<java.lang.String> arraySet = this.mUidToPackageCache.get(i);
        if (arraySet == null) {
            try {
                java.lang.String[] packagesForUid = android.app.AppGlobals.getPackageManager().getPackagesForUid(i);
                if (packagesForUid != null) {
                    for (java.lang.String str : packagesForUid) {
                        this.mUidToPackageCache.add(i, str);
                    }
                    return this.mUidToPackageCache.get(i);
                }
                return arraySet;
            } catch (android.os.RemoteException e) {
                return arraySet;
            }
        }
        return arraySet;
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mStartedUsers = com.android.internal.util.jobs.ArrayUtils.appendInt(this.mStartedUsers, targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserCompletedEvent(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.UserCompletedEventType userCompletedEventType) {
        if (userCompletedEventType.includesOnUserStarting() || userCompletedEventType.includesOnUserUnlocked()) {
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mStartedUsers = com.android.internal.util.jobs.ArrayUtils.removeInt(this.mStartedUsers, targetUser.getUserIdentifier());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUidActive(int i) {
        return this.mAppStateTracker.isUidActiveSynced(i);
    }

    public int scheduleAsPackage(android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem, int i, java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        java.lang.String[] strArr;
        java.lang.String packageName = jobInfo.getService().getPackageName();
        if (jobInfo.isPersisted() && (str == null || str.equals(packageName))) {
            java.lang.String str4 = str == null ? packageName : str;
            if (!this.mQuotaTracker.isWithinQuota(i2, str4, QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG)) {
                if (this.mQuotaTracker.isWithinQuota(i2, str4, QUOTA_TRACKER_SCHEDULE_LOGGED)) {
                    android.util.Slog.wtf(TAG, i2 + "-" + str4 + " has called schedule() too many times");
                    this.mQuotaTracker.noteEvent(i2, str4, QUOTA_TRACKER_SCHEDULE_LOGGED);
                }
                this.mAppStandbyInternal.restrictApp(str4, i2, 4);
                if (this.mConstants.API_QUOTA_SCHEDULE_THROW_EXCEPTION) {
                    synchronized (this.mLock) {
                        if (!this.mDebuggableApps.containsKey(str)) {
                            try {
                                android.content.pm.ApplicationInfo applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(str4, 0L, i2);
                                if (applicationInfo == null) {
                                    return 0;
                                }
                                this.mDebuggableApps.put(str, java.lang.Boolean.valueOf((applicationInfo.flags & 2) != 0));
                            } catch (android.os.RemoteException e) {
                                throw new java.lang.RuntimeException(e);
                            }
                        }
                        boolean booleanValue = this.mDebuggableApps.get(str).booleanValue();
                        if (booleanValue) {
                            throw new android.os.LimitExceededException("schedule()/enqueue() called more than " + this.mQuotaTracker.getLimit(QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED) + " times in the past " + this.mQuotaTracker.getWindowSizeMs(QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED) + "ms. See the documentation for more information.");
                        }
                    }
                }
                if (this.mConstants.API_QUOTA_SCHEDULE_RETURN_FAILURE_RESULT) {
                    return 0;
                }
            }
            this.mQuotaTracker.noteEvent(i2, str4, QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG);
        }
        if (this.mActivityManagerInternal.isAppStartModeDisabled(i, packageName)) {
            android.util.Slog.w(TAG, "Not scheduling job for " + i + ":" + jobInfo.toString() + " -- package not allowed to start");
            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_app_start_mode_disabled", i);
            return 0;
        }
        if (jobInfo.getRequiredNetwork() != null) {
            sInitialJobEstimatedNetworkDownloadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobInfo.getEstimatedNetworkDownloadBytes()));
            sInitialJobEstimatedNetworkUploadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobInfo.getEstimatedNetworkUploadBytes()));
            sJobMinimumChunkKBLogger.logSampleWithUid(i, safelyScaleBytesToKBForHistogram(jobInfo.getMinimumNetworkChunkBytes()));
            if (jobWorkItem != null) {
                sInitialJwiEstimatedNetworkDownloadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobWorkItem.getEstimatedNetworkDownloadBytes()));
                sInitialJwiEstimatedNetworkUploadKBLogger.logSample(safelyScaleBytesToKBForHistogram(jobWorkItem.getEstimatedNetworkUploadBytes()));
                sJwiMinimumChunkKBLogger.logSampleWithUid(i, safelyScaleBytesToKBForHistogram(jobWorkItem.getMinimumNetworkChunkBytes()));
            }
        }
        if (jobWorkItem != null) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_job_work_items_enqueued", i);
        }
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(i, str2, jobInfo.getId());
                if (jobWorkItem != null && jobByUidAndJobId != null) {
                    if (jobByUidAndJobId.getJob().equals(jobInfo)) {
                        if (jobByUidAndJobId.getWorkCount() >= this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS && jobByUidAndJobId.isPersisted()) {
                            android.util.Slog.w(TAG, "Too many JWIs for uid " + i);
                            throw new java.lang.IllegalStateException("Apps may not persist more than " + this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS + " JobWorkItems per job");
                        }
                        jobByUidAndJobId.enqueueWorkLocked(jobWorkItem);
                        if (jobByUidAndJobId.getJob().isUserInitiated()) {
                            jobByUidAndJobId.removeInternalFlags(6);
                        }
                        this.mJobs.touchJob(jobByUidAndJobId);
                        sEnqueuedJwiHighWaterMarkLogger.logSampleWithUid(i, jobByUidAndJobId.getWorkCount());
                        jobByUidAndJobId.maybeAddForegroundExemption(this.mIsUidActivePredicate);
                        return 1;
                    }
                }
                com.android.server.job.controllers.JobStatus createFromJobInfo = com.android.server.job.controllers.JobStatus.createFromJobInfo(jobInfo, i, str, i2, str2, str3);
                if (createFromJobInfo.isRequestedExpeditedJob() && ((this.mConstants.USE_TARE_POLICY && !this.mTareController.canScheduleEJ(createFromJobInfo)) || (!this.mConstants.USE_TARE_POLICY && !this.mQuotaController.isWithinEJQuotaLocked(createFromJobInfo)))) {
                    com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_ej_out_of_quota", i);
                    return 0;
                }
                createFromJobInfo.maybeAddForegroundExemption(this.mIsUidActivePredicate);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "SCHEDULE: " + createFromJobInfo.toShortString());
                }
                if (str == null && this.mJobs.countJobsForUid(i) > 150) {
                    android.util.Slog.w(TAG, "Too many jobs for uid " + i);
                    com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_max_scheduling_limit_hit", i);
                    throw new java.lang.IllegalStateException("Apps may not schedule more than 150 distinct jobs");
                }
                createFromJobInfo.prepareLocked();
                if (jobByUidAndJobId != null) {
                    if (jobWorkItem != null && jobByUidAndJobId.isPersisted() && jobByUidAndJobId.getWorkCount() >= this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS) {
                        android.util.Slog.w(TAG, "Too many JWIs for uid " + i);
                        throw new java.lang.IllegalStateException("Apps may not persist more than " + this.mConstants.MAX_NUM_PERSISTED_JOB_WORK_ITEMS + " JobWorkItems per job");
                    }
                    cancelJobImplLocked(jobByUidAndJobId, createFromJobInfo, 1, 0, "job rescheduled by app");
                } else {
                    startTrackingJobLocked(createFromJobInfo, null);
                }
                if (jobWorkItem != null) {
                    createFromJobInfo.enqueueWorkLocked(jobWorkItem);
                    sEnqueuedJwiHighWaterMarkLogger.logSampleWithUid(i, createFromJobInfo.getWorkCount());
                }
                int sourceUid = createFromJobInfo.getSourceUid();
                int[] iArr = createFromJobInfo.isProxyJob() ? new int[]{sourceUid, i} : new int[]{sourceUid};
                if (createFromJobInfo.isProxyJob()) {
                    strArr = new java.lang.String[]{null, createFromJobInfo.getSourceTag()};
                } else {
                    strArr = new java.lang.String[]{createFromJobInfo.getSourceTag()};
                }
                com.android.internal.util.FrameworkStatsLog.write(8, iArr, strArr, createFromJobInfo.getBatteryName(), 2, -1, createFromJobInfo.getStandbyBucket(), createFromJobInfo.getLoggingJobId(), createFromJobInfo.hasChargingConstraint(), createFromJobInfo.hasBatteryNotLowConstraint(), createFromJobInfo.hasStorageNotLowConstraint(), createFromJobInfo.hasTimingDelayConstraint(), createFromJobInfo.hasDeadlineConstraint(), createFromJobInfo.hasIdleConstraint(), createFromJobInfo.hasConnectivityConstraint(), createFromJobInfo.hasContentTriggerConstraint(), createFromJobInfo.isRequestedExpeditedJob(), false, 0, createFromJobInfo.getJob().isPrefetch(), createFromJobInfo.getJob().getPriority(), createFromJobInfo.getEffectivePriority(), createFromJobInfo.getNumPreviousAttempts(), createFromJobInfo.getJob().getMaxExecutionDelayMillis(), false, false, false, false, false, false, false, false, 0L, createFromJobInfo.getJob().isUserInitiated(), false, createFromJobInfo.getJob().isPeriodic(), createFromJobInfo.getJob().getMinLatencyMillis(), createFromJobInfo.getEstimatedNetworkDownloadBytes(), createFromJobInfo.getEstimatedNetworkUploadBytes(), createFromJobInfo.getWorkCount(), android.app.ActivityManager.processStateAmToProto(this.mUidProcStates.get(createFromJobInfo.getUid())), createFromJobInfo.getNamespaceHash(), 0L, 0L, 0L, 0L, createFromJobInfo.getJob().getIntervalMillis(), createFromJobInfo.getJob().getFlexMillis(), createFromJobInfo.hasFlexibilityConstraint(), false, createFromJobInfo.canApplyTransportAffinities(), createFromJobInfo.getNumAppliedFlexibleConstraints(), createFromJobInfo.getNumDroppedFlexibleConstraints(), createFromJobInfo.getFilteredTraceTag(), createFromJobInfo.getFilteredDebugTags());
                if (isReadyToBeExecutedLocked(createFromJobInfo)) {
                    this.mJobPackageTracker.notePending(createFromJobInfo);
                    this.mPendingJobQueue.add(createFromJobInfo);
                    maybeRunPendingJobsLocked();
                }
                return 1;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.ArrayMap<java.lang.String, java.util.List<android.app.job.JobInfo>> getPendingJobs(int i) {
        android.util.ArrayMap<java.lang.String, java.util.List<android.app.job.JobInfo>> arrayMap = new android.util.ArrayMap<>();
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByUid = this.mJobs.getJobsByUid(i);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = jobsByUid.valueAt(size);
                    java.util.List<android.app.job.JobInfo> list = arrayMap.get(valueAt.getNamespace());
                    if (list == null) {
                        list = new java.util.ArrayList<>();
                        arrayMap.put(valueAt.getNamespace(), list);
                    }
                    list.add(valueAt.getJob());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.app.job.JobInfo> getPendingJobsInNamespace(int i, @android.annotation.Nullable java.lang.String str) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByUid = this.mJobs.getJobsByUid(i);
                arrayList = new java.util.ArrayList();
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = jobsByUid.valueAt(size);
                    if (java.util.Objects.equals(str, valueAt.getNamespace())) {
                        arrayList.add(valueAt.getJob());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPendingJobReason(int i, java.lang.String str, int i2) {
        int pendingJobReasonLocked;
        int i3;
        synchronized (this.mPendingJobReasonCache) {
            try {
                android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) this.mPendingJobReasonCache.get(i, str);
                if (sparseIntArray != null && (i3 = sparseIntArray.get(i2, 0)) != 0) {
                    return i3;
                }
                synchronized (this.mLock) {
                    try {
                        pendingJobReasonLocked = getPendingJobReasonLocked(i, str, i2);
                        if (DEBUG) {
                            android.util.Slog.v(TAG, "getPendingJobReason(" + i + "," + str + "," + i2 + ")=" + pendingJobReasonLocked);
                        }
                    } finally {
                    }
                }
                synchronized (this.mPendingJobReasonCache) {
                    try {
                        android.util.SparseIntArray sparseIntArray2 = (android.util.SparseIntArray) this.mPendingJobReasonCache.get(i, str);
                        if (sparseIntArray2 == null) {
                            sparseIntArray2 = new android.util.SparseIntArray();
                            this.mPendingJobReasonCache.add(i, str, sparseIntArray2);
                        }
                        sparseIntArray2.put(i2, pendingJobReasonLocked);
                    } finally {
                    }
                }
                return pendingJobReasonLocked;
            } finally {
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getPendingJobReason(com.android.server.job.controllers.JobStatus jobStatus) {
        return getPendingJobReason(jobStatus.getUid(), jobStatus.getNamespace(), jobStatus.getJobId());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getPendingJobReasonLocked(int i, java.lang.String str, int i2) {
        com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(i, str, i2);
        if (jobByUidAndJobId == null) {
            return -2;
        }
        if (isCurrentlyRunningLocked(jobByUidAndJobId)) {
            return -1;
        }
        boolean isReady = jobByUidAndJobId.isReady();
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " ready=" + isReady);
        }
        if (!isReady) {
            return jobByUidAndJobId.getPendingJobReason();
        }
        boolean areUsersStartedLocked = areUsersStartedLocked(jobByUidAndJobId);
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " userStarted=" + areUsersStartedLocked);
        }
        if (!areUsersStartedLocked) {
            return 15;
        }
        boolean z = this.mBackingUpUids.get(jobByUidAndJobId.getSourceUid());
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " backingUp=" + z);
        }
        if (z) {
            return 1;
        }
        com.android.server.job.restrictions.JobRestriction checkIfRestricted = checkIfRestricted(jobByUidAndJobId);
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " restriction=" + checkIfRestricted);
        }
        if (checkIfRestricted != null) {
            return checkIfRestricted.getPendingReason();
        }
        boolean contains = this.mPendingJobQueue.contains(jobByUidAndJobId);
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " pending=" + contains);
        }
        if (contains) {
            return 12;
        }
        boolean isJobRunningLocked = this.mConcurrencyManager.isJobRunningLocked(jobByUidAndJobId);
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " active=" + isJobRunningLocked);
        }
        if (isJobRunningLocked) {
            return 0;
        }
        boolean isComponentUsable = isComponentUsable(jobByUidAndJobId);
        if (DEBUG) {
            android.util.Slog.v(TAG, "getPendingJobReasonLocked: " + jobByUidAndJobId.toShortString() + " componentUsable=" + isComponentUsable);
        }
        if (!isComponentUsable) {
            return 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.job.JobInfo getPendingJob(int i, @android.annotation.Nullable java.lang.String str, int i2) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByUid = this.mJobs.getJobsByUid(i);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = jobsByUid.valueAt(size);
                    if (valueAt.getJobId() == i2 && java.util.Objects.equals(str, valueAt.getNamespace())) {
                        return valueAt.getJob();
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void notePendingUserRequestedAppStopInternal(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2) {
        int packageUid = this.mLocalPM.getPackageUid(str, 0L, i);
        if (packageUid < 0) {
            android.util.Slog.wtf(TAG, "Asked to stop jobs of an unknown package");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mConcurrencyManager.markJobsForUserStopLocked(i, str, str2);
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByUid = this.mJobs.getJobsByUid(packageUid);
                for (int size = jobsByUid.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = jobsByUid.valueAt(size);
                    valueAt.addInternalFlags(2);
                    if (this.mPendingJobQueue.remove(valueAt)) {
                        synchronized (this.mPendingJobReasonCache) {
                            try {
                                android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) this.mPendingJobReasonCache.get(valueAt.getUid(), valueAt.getNamespace());
                                if (sparseIntArray == null) {
                                    sparseIntArray = new android.util.SparseIntArray();
                                    this.mPendingJobReasonCache.add(valueAt.getUid(), valueAt.getNamespace(), sparseIntArray);
                                }
                                sparseIntArray.put(valueAt.getJobId(), 15);
                            } finally {
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.job.controllers.JobStatus jobStatus) {
        cancelJobImplLocked(jobStatus, null, 13, 7, "user removed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelJobsForUserLocked(final int i) {
        this.mJobs.forEachJob(new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$cancelJobsForUserLocked$1;
                lambda$cancelJobsForUserLocked$1 = com.android.server.job.JobSchedulerService.lambda$cancelJobsForUserLocked$1(i, (com.android.server.job.controllers.JobStatus) obj);
                return lambda$cancelJobsForUserLocked$1;
            }
        }, this.mCancelJobDueToUserRemovalConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cancelJobsForUserLocked$1(int i, com.android.server.job.controllers.JobStatus jobStatus) {
        return jobStatus.getUserId() == i || jobStatus.getSourceUserId() == i;
    }

    private void cancelJobsForNonExistentUsers() {
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        synchronized (this.mLock) {
            this.mJobs.removeJobsOfUnlistedUsers(userManagerInternal.getUserIds());
        }
        synchronized (this.mPendingJobReasonCache) {
            this.mPendingJobReasonCache.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelJobsForPackageAndUidLocked(java.lang.String str, int i, boolean z, boolean z2, int i2, int i3, java.lang.String str2) {
        boolean z3;
        if (!z && !z2) {
            android.util.Slog.wtfStack(TAG, "Didn't indicate whether to cancel jobs for scheduling and/or source app");
            z3 = true;
        } else {
            z3 = z2;
        }
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
            android.util.Slog.wtfStack(TAG, "Can't cancel all jobs for system package");
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (z) {
            this.mJobs.getJobsByUid(i, arraySet);
        }
        if (z3) {
            this.mJobs.getJobsBySourceUid(i, arraySet);
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(size);
            if ((z && jobStatus.getServiceComponent().getPackageName().equals(str)) || (z3 && jobStatus.getSourcePackageName().equals(str))) {
                cancelJobImplLocked(jobStatus, null, i2, i3, str2);
            }
        }
    }

    public boolean cancelJobsForUid(int i, boolean z, int i2, int i3, java.lang.String str) {
        return cancelJobsForUid(i, z, false, null, i2, i3, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean cancelJobsForUid(int i, boolean z, boolean z2, @android.annotation.Nullable java.lang.String str, int i2, int i3, java.lang.String str2) {
        boolean z3 = false;
        if (i == 1000 && (!z2 || str == null)) {
            android.util.Slog.wtfStack(TAG, "Can't cancel all jobs for system uid");
            return false;
        }
        synchronized (this.mLock) {
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                this.mJobs.getJobsByUid(i, arraySet);
                if (z) {
                    this.mJobs.getJobsBySourceUid(i, arraySet);
                }
                for (int i4 = 0; i4 < arraySet.size(); i4++) {
                    com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) arraySet.valueAt(i4);
                    if (!z2 || java.util.Objects.equals(str, jobStatus.getNamespace())) {
                        cancelJobImplLocked(jobStatus, null, i2, i3, str2);
                        z3 = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean cancelJob(int i, java.lang.String str, int i2, int i3, int i4) {
        boolean z;
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(i, str, i2);
                if (jobByUidAndJobId != null) {
                    cancelJobImplLocked(jobByUidAndJobId, null, i4, 0, "cancel() called by app, callingUid=" + i3 + " uid=" + i + " jobId=" + i2);
                }
                z = jobByUidAndJobId != null;
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cancelJobImplLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2, int i, int i2, java.lang.String str) {
        java.lang.String str2;
        int[] iArr;
        java.lang.String[] strArr;
        if (DEBUG) {
            android.util.Slog.d(TAG, "CANCEL: " + jobStatus.toShortString());
        }
        jobStatus.unprepareLocked();
        stopTrackingJobLocked(jobStatus, jobStatus2, true);
        if (this.mPendingJobQueue.remove(jobStatus)) {
            this.mJobPackageTracker.noteNonpending(jobStatus);
        }
        this.mChangedJobList.remove(jobStatus);
        if (this.mConcurrencyManager.stopJobOnServiceContextLocked(jobStatus, i, i2, str)) {
            str2 = TAG;
        } else {
            int sourceUid = jobStatus.getSourceUid();
            if (!jobStatus.isProxyJob()) {
                iArr = new int[]{sourceUid};
            } else {
                iArr = new int[]{sourceUid, jobStatus.getUid()};
            }
            int[] iArr2 = iArr;
            if (jobStatus.isProxyJob()) {
                strArr = new java.lang.String[]{null, jobStatus.getSourceTag()};
            } else {
                strArr = new java.lang.String[]{jobStatus.getSourceTag()};
            }
            java.lang.String batteryName = jobStatus.getBatteryName();
            int standbyBucket = jobStatus.getStandbyBucket();
            long loggingJobId = jobStatus.getLoggingJobId();
            boolean hasChargingConstraint = jobStatus.hasChargingConstraint();
            boolean hasBatteryNotLowConstraint = jobStatus.hasBatteryNotLowConstraint();
            boolean hasStorageNotLowConstraint = jobStatus.hasStorageNotLowConstraint();
            boolean hasTimingDelayConstraint = jobStatus.hasTimingDelayConstraint();
            boolean hasDeadlineConstraint = jobStatus.hasDeadlineConstraint();
            boolean hasIdleConstraint = jobStatus.hasIdleConstraint();
            boolean hasConnectivityConstraint = jobStatus.hasConnectivityConstraint();
            boolean hasContentTriggerConstraint = jobStatus.hasContentTriggerConstraint();
            boolean isRequestedExpeditedJob = jobStatus.isRequestedExpeditedJob();
            boolean isPrefetch = jobStatus.getJob().isPrefetch();
            int priority = jobStatus.getJob().getPriority();
            int effectivePriority = jobStatus.getEffectivePriority();
            int numPreviousAttempts = jobStatus.getNumPreviousAttempts();
            long maxExecutionDelayMillis = jobStatus.getJob().getMaxExecutionDelayMillis();
            boolean isConstraintSatisfied = jobStatus.isConstraintSatisfied(1073741824);
            boolean isConstraintSatisfied2 = jobStatus.isConstraintSatisfied(1);
            boolean isConstraintSatisfied3 = jobStatus.isConstraintSatisfied(2);
            boolean isConstraintSatisfied4 = jobStatus.isConstraintSatisfied(8);
            boolean isConstraintSatisfied5 = jobStatus.isConstraintSatisfied(Integer.MIN_VALUE);
            boolean isConstraintSatisfied6 = jobStatus.isConstraintSatisfied(4);
            boolean isConstraintSatisfied7 = jobStatus.isConstraintSatisfied(268435456);
            boolean isConstraintSatisfied8 = jobStatus.isConstraintSatisfied(67108864);
            boolean isUserInitiated = jobStatus.getJob().isUserInitiated();
            boolean isPeriodic = jobStatus.getJob().isPeriodic();
            long minLatencyMillis = jobStatus.getJob().getMinLatencyMillis();
            long estimatedNetworkDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
            long estimatedNetworkUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
            int workCount = jobStatus.getWorkCount();
            int processStateAmToProto = android.app.ActivityManager.processStateAmToProto(this.mUidProcStates.get(jobStatus.getUid()));
            java.lang.String namespaceHash = jobStatus.getNamespaceHash();
            long intervalMillis = jobStatus.getJob().getIntervalMillis();
            long flexMillis = jobStatus.getJob().getFlexMillis();
            boolean hasFlexibilityConstraint = jobStatus.hasFlexibilityConstraint();
            boolean isConstraintSatisfied9 = jobStatus.isConstraintSatisfied(2097152);
            boolean canApplyTransportAffinities = jobStatus.canApplyTransportAffinities();
            int numAppliedFlexibleConstraints = jobStatus.getNumAppliedFlexibleConstraints();
            int numDroppedFlexibleConstraints = jobStatus.getNumDroppedFlexibleConstraints();
            java.lang.String filteredTraceTag = jobStatus.getFilteredTraceTag();
            java.lang.String[] filteredDebugTags = jobStatus.getFilteredDebugTags();
            str2 = TAG;
            com.android.internal.util.FrameworkStatsLog.write(8, iArr2, strArr, batteryName, 3, i2, standbyBucket, loggingJobId, hasChargingConstraint, hasBatteryNotLowConstraint, hasStorageNotLowConstraint, hasTimingDelayConstraint, hasDeadlineConstraint, hasIdleConstraint, hasConnectivityConstraint, hasContentTriggerConstraint, isRequestedExpeditedJob, false, i, isPrefetch, priority, effectivePriority, numPreviousAttempts, maxExecutionDelayMillis, isConstraintSatisfied, isConstraintSatisfied2, isConstraintSatisfied3, isConstraintSatisfied4, isConstraintSatisfied5, isConstraintSatisfied6, isConstraintSatisfied7, isConstraintSatisfied8, 0L, isUserInitiated, false, isPeriodic, minLatencyMillis, estimatedNetworkDownloadBytes, estimatedNetworkUploadBytes, workCount, processStateAmToProto, namespaceHash, 0L, 0L, 0L, 0L, intervalMillis, flexMillis, hasFlexibilityConstraint, isConstraintSatisfied9, canApplyTransportAffinities, numAppliedFlexibleConstraints, numDroppedFlexibleConstraints, filteredTraceTag, filteredDebugTags);
        }
        if (jobStatus2 != null) {
            if (DEBUG) {
                android.util.Slog.i(str2, "Tracking replacement job " + jobStatus2.toShortString());
            }
            startTrackingJobLocked(jobStatus2, jobStatus);
        }
        reportActiveLocked();
        if (this.mLastCancelledJobs.length > 0 && i2 == 0) {
            this.mLastCancelledJobs[this.mLastCancelledJobIndex] = jobStatus;
            this.mLastCancelledJobTimeElapsed[this.mLastCancelledJobIndex] = sElapsedRealtimeClock.millis();
            this.mLastCancelledJobIndex = (this.mLastCancelledJobIndex + 1) % this.mLastCancelledJobs.length;
        }
    }

    void updateUidState(int i, int i2, int i3) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "UID " + i + " proc state changed to " + android.app.ActivityManager.procStateToString(i2) + " with capabilities=" + android.app.ActivityManager.getCapabilitiesSummary(i3));
        }
        synchronized (this.mLock) {
            try {
                this.mUidProcStates.put(i, i2);
                int i4 = this.mUidBiasOverride.get(i, 0);
                if (i2 == 2) {
                    this.mUidBiasOverride.put(i, 40);
                } else if (i2 <= 4) {
                    this.mUidBiasOverride.put(i, 35);
                } else if (i2 <= 5) {
                    this.mUidBiasOverride.put(i, 30);
                } else {
                    this.mUidBiasOverride.delete(i);
                }
                if (i3 == 0 || i2 == 20) {
                    this.mUidCapabilities.delete(i);
                } else {
                    this.mUidCapabilities.put(i, i3);
                }
                int i5 = this.mUidBiasOverride.get(i, 0);
                if (i4 != i5) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "UID " + i + " bias changed from " + i4 + " to " + i5);
                    }
                    for (int i6 = 0; i6 < this.mControllers.size(); i6++) {
                        this.mControllers.get(i6).onUidBiasChangedLocked(i, i4, i5);
                    }
                    this.mConcurrencyManager.onUidBiasChangedLocked(i4, i5);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getUidBias(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidBiasOverride.get(i, 0);
        }
        return i2;
    }

    public int getUidCapabilities(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidCapabilities.get(i, 0);
        }
        return i2;
    }

    public int getUidProcState(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidProcStates.get(i, -1);
        }
        return i2;
    }

    @Override // com.android.server.job.StateChangedListener
    public void onDeviceIdleStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Doze state changed: " + z);
                }
                if (!z && this.mReadyToRock) {
                    if (this.mLocalDeviceIdleController != null && !this.mReportedActive) {
                        this.mReportedActive = true;
                        this.mLocalDeviceIdleController.setJobsActive(true);
                    }
                    this.mHandler.obtainMessage(1).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.StateChangedListener
    public void onNetworkChanged(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network) {
        synchronized (this.mLock) {
            try {
                com.android.server.job.JobServiceContext runningJobServiceContextLocked = this.mConcurrencyManager.getRunningJobServiceContextLocked(jobStatus);
                if (runningJobServiceContextLocked != null) {
                    runningJobServiceContextLocked.informOfNetworkChangeLocked(network);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.StateChangedListener
    public void onRestrictedBucketChanged(java.util.List<com.android.server.job.controllers.JobStatus> list) {
        int size = list.size();
        if (size == 0) {
            android.util.Slog.wtf(TAG, "onRestrictedBucketChanged called with no jobs");
            return;
        }
        synchronized (this.mLock) {
            for (int i = 0; i < size; i++) {
                try {
                    com.android.server.job.controllers.JobStatus jobStatus = list.get(i);
                    for (int size2 = this.mRestrictiveControllers.size() - 1; size2 >= 0; size2--) {
                        if (jobStatus.getStandbyBucket() == 5) {
                            this.mRestrictiveControllers.get(size2).startTrackingRestrictedJobLocked(jobStatus);
                        } else {
                            this.mRestrictiveControllers.get(size2).stopTrackingRestrictedJobLocked(jobStatus);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    void reportActiveLocked() {
        boolean z = true;
        boolean z2 = this.mPendingJobQueue.size() > 0;
        if (!z2) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> runningJobsLocked = this.mConcurrencyManager.getRunningJobsLocked();
            for (int size = runningJobsLocked.size() - 1; size >= 0; size--) {
                if (!runningJobsLocked.valueAt(size).canRunInDoze()) {
                    break;
                }
            }
        }
        z = z2;
        if (this.mReportedActive != z) {
            this.mReportedActive = z;
            if (this.mLocalDeviceIdleController != null) {
                this.mLocalDeviceIdleController.setJobsActive(z);
            }
        }
    }

    void reportAppUsage(java.lang.String str, int i) {
    }

    public JobSchedulerService(android.content.Context context) {
        super(context);
        int i;
        this.mLock = new java.lang.Object();
        this.mJobPackageTracker = new com.android.server.job.JobPackageTracker();
        this.mCloudMediaProviderPackages = new android.util.SparseArray<>();
        this.mUserVisibleJobObservers = new android.os.RemoteCallbackList<>();
        this.mPermissionCache = new android.util.SparseArray<>();
        this.mPendingJobQueue = new com.android.server.job.PendingJobQueue();
        this.mStartedUsers = libcore.util.EmptyArray.INT;
        this.mLastCompletedJobIndex = 0;
        this.mLastCompletedJobs = new com.android.server.job.controllers.JobStatus[20];
        this.mLastCompletedJobTimeElapsed = new long[20];
        this.mLastCancelledJobIndex = 0;
        if (!DEBUG) {
            i = 0;
        } else {
            i = 20;
        }
        this.mLastCancelledJobs = new com.android.server.job.controllers.JobStatus[i];
        this.mLastCancelledJobTimeElapsed = new long[DEBUG ? 20 : 0];
        this.mUidBiasOverride = new android.util.SparseIntArray();
        this.mUidCapabilities = new android.util.SparseIntArray();
        this.mUidProcStates = new android.util.SparseIntArray();
        this.mBackingUpUids = new android.util.SparseBooleanArray();
        this.mDebuggableApps = new android.util.ArrayMap<>();
        this.mUidToPackageCache = new android.util.SparseSetArray<>();
        this.mChangedJobList = new android.util.ArraySet<>();
        this.mPendingJobReasonCache = new android.util.SparseArrayMap<>();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.JobSchedulerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByUid;
                java.lang.String action = intent.getAction();
                if (com.android.server.job.JobSchedulerService.DEBUG) {
                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Receieved: " + action);
                }
                java.lang.String packageName = com.android.server.job.JobSchedulerService.getPackageName(intent);
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                int i2 = 0;
                if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                    synchronized (com.android.server.job.JobSchedulerService.this.mPermissionCache) {
                        com.android.server.job.JobSchedulerService.this.mPermissionCache.remove(intExtra);
                    }
                    if (packageName != null && intExtra != -1) {
                        java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                        if (stringArrayExtra != null) {
                            int length = stringArrayExtra.length;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                if (!stringArrayExtra[i2].equals(packageName)) {
                                    i2++;
                                } else {
                                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Package state change: " + packageName);
                                    }
                                    try {
                                        int userId = android.os.UserHandle.getUserId(intExtra);
                                        int applicationEnabledSetting = android.app.AppGlobals.getPackageManager().getApplicationEnabledSetting(packageName, userId);
                                        if (applicationEnabledSetting == 2 || applicationEnabledSetting == 3) {
                                            if (com.android.server.job.JobSchedulerService.DEBUG) {
                                                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Removing jobs for package " + packageName + " in user " + userId);
                                            }
                                            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                                com.android.server.job.JobSchedulerService.this.cancelJobsForPackageAndUidLocked(packageName, intExtra, true, true, 13, 7, "app disabled");
                                            }
                                        }
                                    } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                                    }
                                }
                            }
                            if (com.android.server.job.JobSchedulerService.DEBUG) {
                                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Something in " + packageName + " changed. Reevaluating controller states.");
                            }
                            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                try {
                                    for (int size = com.android.server.job.JobSchedulerService.this.mControllers.size() - 1; size >= 0; size--) {
                                        com.android.server.job.JobSchedulerService.this.mControllers.get(size).reevaluateStateLocked(intExtra);
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                    android.util.Slog.w(com.android.server.job.JobSchedulerService.TAG, "PACKAGE_CHANGED for " + packageName + " / uid " + intExtra);
                    return;
                }
                if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                    synchronized (com.android.server.job.JobSchedulerService.this.mPermissionCache) {
                        com.android.server.job.JobSchedulerService.this.mPermissionCache.remove(intExtra);
                    }
                    if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                            com.android.server.job.JobSchedulerService.this.mUidToPackageCache.remove(intExtra);
                        }
                        return;
                    }
                    return;
                }
                if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                    synchronized (com.android.server.job.JobSchedulerService.this.mPermissionCache) {
                        com.android.server.job.JobSchedulerService.this.mPermissionCache.remove(intExtra);
                    }
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Removing jobs for " + packageName + " (uid=" + intExtra + ")");
                    }
                    synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                        try {
                            com.android.server.job.JobSchedulerService.this.mUidToPackageCache.remove(intExtra);
                            com.android.server.job.JobSchedulerService.this.cancelJobsForPackageAndUidLocked(packageName, intExtra, true, true, 13, 7, "app uninstalled");
                            while (i2 < com.android.server.job.JobSchedulerService.this.mControllers.size()) {
                                com.android.server.job.JobSchedulerService.this.mControllers.get(i2).onAppRemovedLocked(packageName, intExtra);
                                i2++;
                            }
                            com.android.server.job.JobSchedulerService.this.mDebuggableApps.remove(packageName);
                            com.android.server.job.JobSchedulerService.this.mConcurrencyManager.onAppRemovedLocked(packageName, intExtra);
                        } finally {
                        }
                    }
                    return;
                }
                if ("android.intent.action.UID_REMOVED".equals(action)) {
                    if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                            com.android.server.job.JobSchedulerService.this.mUidBiasOverride.delete(intExtra);
                            com.android.server.job.JobSchedulerService.this.mUidCapabilities.delete(intExtra);
                            com.android.server.job.JobSchedulerService.this.mUidProcStates.delete(intExtra);
                        }
                        return;
                    }
                    return;
                }
                if ("android.intent.action.USER_ADDED".equals(action)) {
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                        while (i2 < com.android.server.job.JobSchedulerService.this.mControllers.size()) {
                            try {
                                com.android.server.job.JobSchedulerService.this.mControllers.get(i2).onUserAddedLocked(intExtra2);
                                i2++;
                            } finally {
                            }
                        }
                    }
                    return;
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Removing jobs for user: " + intExtra3);
                    }
                    synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                        try {
                            com.android.server.job.JobSchedulerService.this.mUidToPackageCache.clear();
                            com.android.server.job.JobSchedulerService.this.cancelJobsForUserLocked(intExtra3);
                            while (i2 < com.android.server.job.JobSchedulerService.this.mControllers.size()) {
                                com.android.server.job.JobSchedulerService.this.mControllers.get(i2).onUserRemovedLocked(intExtra3);
                                i2++;
                            }
                        } finally {
                        }
                    }
                    com.android.server.job.JobSchedulerService.this.mConcurrencyManager.onUserRemoved(intExtra3);
                    synchronized (com.android.server.job.JobSchedulerService.this.mPermissionCache) {
                        try {
                            for (int size2 = com.android.server.job.JobSchedulerService.this.mPermissionCache.size() - 1; size2 >= 0; size2--) {
                                if (intExtra3 == android.os.UserHandle.getUserId(com.android.server.job.JobSchedulerService.this.mPermissionCache.keyAt(size2))) {
                                    com.android.server.job.JobSchedulerService.this.mPermissionCache.removeAt(size2);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if ("android.intent.action.QUERY_PACKAGE_RESTART".equals(action)) {
                    if (intExtra != -1) {
                        synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                            jobsByUid = com.android.server.job.JobSchedulerService.this.mJobs.getJobsByUid(intExtra);
                        }
                        for (int size3 = jobsByUid.size() - 1; size3 >= 0; size3--) {
                            if (jobsByUid.valueAt(size3).getSourcePackageName().equals(packageName)) {
                                if (com.android.server.job.JobSchedulerService.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Restart query: package " + packageName + " at uid " + intExtra + " has jobs");
                                }
                                setResultCode(-1);
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                if ("android.intent.action.PACKAGE_RESTARTED".equals(action) && intExtra != -1) {
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Removing jobs for pkg " + packageName + " at uid " + intExtra);
                    }
                    synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                        com.android.server.job.JobSchedulerService.this.cancelJobsForPackageAndUidLocked(packageName, intExtra, true, false, 13, 0, "app force stopped");
                    }
                }
            }
        };
        this.mUidObserver = new android.app.UidObserver() { // from class: com.android.server.job.JobSchedulerService.4
            public void onUidStateChanged(int i2, int i3, long j, int i4) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.argi1 = i2;
                obtain.argi2 = i3;
                obtain.argi3 = i4;
                com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(4, obtain).sendToTarget();
            }

            public void onUidGone(int i2, boolean z) {
                com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(5, i2, z ? 1 : 0).sendToTarget();
            }

            public void onUidActive(int i2) {
                com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(6, i2, 0).sendToTarget();
            }

            public void onUidIdle(int i2, boolean z) {
                com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(7, i2, z ? 1 : 0).sendToTarget();
            }
        };
        this.mIsUidActivePredicate = new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isUidActive;
                isUidActive = com.android.server.job.JobSchedulerService.this.isUidActive(((java.lang.Integer) obj).intValue());
                return isUidActive;
            }
        };
        this.mCancelJobDueToUserRemovalConsumer = new java.util.function.Consumer() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.JobSchedulerService.this.lambda$new$0((com.android.server.job.controllers.JobStatus) obj);
            }
        };
        this.mTimeSetReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.JobSchedulerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.TIME_SET".equals(intent.getAction()) && com.android.server.job.JobSchedulerService.this.mJobs.clockNowValidToInflate(com.android.server.job.JobSchedulerService.sSystemClock.millis())) {
                    android.util.Slog.i(com.android.server.job.JobSchedulerService.TAG, "RTC now valid; recalculating persisted job windows");
                    context2.unregisterReceiver(this);
                    com.android.server.job.JobSchedulerService.this.mJobs.runWorkAsync(com.android.server.job.JobSchedulerService.this.mJobTimeUpdater);
                }
            }
        };
        this.mJobTimeUpdater = new java.lang.Runnable() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.JobSchedulerService.this.lambda$new$3();
            }
        };
        this.mReadyQueueFunctor = new com.android.server.job.JobSchedulerService.ReadyJobQueueFunctor();
        this.mMaybeQueueFunctor = new com.android.server.job.JobSchedulerService.MaybeReadyJobQueueFunctor();
        this.mLocalPM = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        this.mHandler = new com.android.server.job.JobSchedulerService.JobHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mConstants = new com.android.server.job.JobSchedulerService.Constants();
        this.mConstantsObserver = new com.android.server.job.JobSchedulerService.ConstantsObserver();
        this.mJobSchedulerStub = new com.android.server.job.JobSchedulerService.JobSchedulerStub();
        this.mConcurrencyManager = new com.android.server.job.JobConcurrencyManager(this);
        this.mStandbyTracker = new com.android.server.job.JobSchedulerService.StandbyTracker();
        sUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        this.mQuotaTracker = new com.android.server.utils.quota.CountQuotaTracker(context, new com.android.server.utils.quota.Categorizer() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda7
            @Override // com.android.server.utils.quota.Categorizer
            public final com.android.server.utils.quota.Category getCategory(int i2, java.lang.String str, java.lang.String str2) {
                com.android.server.utils.quota.Category lambda$new$2;
                lambda$new$2 = com.android.server.job.JobSchedulerService.this.lambda$new$2(i2, str, str2);
                return lambda$new$2;
            }
        });
        updateQuotaTracker();
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED, 1, 60000L);
        this.mQuotaTracker.setCountLimit(QUOTA_TRACKER_CATEGORY_DISABLED, Integer.MAX_VALUE, 60000L);
        this.mAppStandbyInternal = (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);
        this.mAppStandbyInternal.addListener(this.mStandbyTracker);
        this.mBatteryStatsInternal = (android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class);
        publishLocalService(com.android.server.job.JobSchedulerInternal.class, new com.android.server.job.JobSchedulerService.LocalService());
        this.mJobStoreLoadedLatch = new java.util.concurrent.CountDownLatch(1);
        this.mJobs = com.android.server.job.JobStore.get(this);
        this.mJobs.initAsync(this.mJobStoreLoadedLatch);
        this.mBatteryStateTracker = new com.android.server.job.JobSchedulerService.BatteryStateTracker();
        this.mBatteryStateTracker.startTracking();
        this.mStartControllerTrackingLatch = new java.util.concurrent.CountDownLatch(1);
        this.mControllers = new java.util.ArrayList();
        this.mPrefetchController = new com.android.server.job.controllers.PrefetchController(this);
        this.mControllers.add(this.mPrefetchController);
        com.android.server.job.controllers.FlexibilityController flexibilityController = new com.android.server.job.controllers.FlexibilityController(this, this.mPrefetchController);
        this.mControllers.add(flexibilityController);
        this.mConnectivityController = new com.android.server.job.controllers.ConnectivityController(this, flexibilityController);
        this.mControllers.add(this.mConnectivityController);
        this.mControllers.add(new com.android.server.job.controllers.TimeController(this));
        com.android.server.job.controllers.IdleController idleController = new com.android.server.job.controllers.IdleController(this, flexibilityController);
        this.mControllers.add(idleController);
        com.android.server.job.controllers.BatteryController batteryController = new com.android.server.job.controllers.BatteryController(this, flexibilityController);
        this.mControllers.add(batteryController);
        this.mStorageController = new com.android.server.job.controllers.StorageController(this);
        this.mControllers.add(this.mStorageController);
        com.android.server.job.controllers.BackgroundJobsController backgroundJobsController = new com.android.server.job.controllers.BackgroundJobsController(this);
        this.mControllers.add(backgroundJobsController);
        this.mControllers.add(new com.android.server.job.controllers.ContentObserverController(this));
        this.mDeviceIdleJobsController = new com.android.server.job.controllers.DeviceIdleJobsController(this);
        this.mControllers.add(this.mDeviceIdleJobsController);
        this.mQuotaController = new com.android.server.job.controllers.QuotaController(this, backgroundJobsController, this.mConnectivityController);
        this.mControllers.add(this.mQuotaController);
        this.mControllers.add(new com.android.server.job.controllers.ComponentController(this));
        this.mTareController = new com.android.server.job.controllers.TareController(this, backgroundJobsController, this.mConnectivityController);
        this.mControllers.add(this.mTareController);
        startControllerTrackingAsync();
        this.mRestrictiveControllers = new java.util.ArrayList();
        this.mRestrictiveControllers.add(batteryController);
        this.mRestrictiveControllers.add(this.mConnectivityController);
        this.mRestrictiveControllers.add(idleController);
        this.mJobRestrictions = new java.util.ArrayList();
        this.mJobRestrictions.add(new com.android.server.job.restrictions.ThermalStatusRestriction(this));
        if (!this.mJobs.jobTimesInflatedValid()) {
            android.util.Slog.w(TAG, "!!! RTC not yet good; tracking time updates for job scheduling");
            context.registerReceiver(this.mTimeSetReceiver, new android.content.IntentFilter("android.intent.action.TIME_SET"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.utils.quota.Category lambda$new$2(int i, java.lang.String str, java.lang.String str2) {
        if (QUOTA_TRACKER_TIMEOUT_UIJ_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC) {
                return QUOTA_TRACKER_CATEGORY_TIMEOUT_UIJ;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_TIMEOUT_EJ_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC) {
                return QUOTA_TRACKER_CATEGORY_TIMEOUT_EJ;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_TIMEOUT_REG_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC) {
                return QUOTA_TRACKER_CATEGORY_TIMEOUT_REG;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_TIMEOUT_TOTAL_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC) {
                return QUOTA_TRACKER_CATEGORY_TIMEOUT_TOTAL;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_ANR_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_EXECUTION_SAFEGUARDS_UDC) {
                return QUOTA_TRACKER_CATEGORY_ANR;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG.equals(str2)) {
            if (this.mConstants.ENABLE_API_QUOTAS) {
                return QUOTA_TRACKER_CATEGORY_SCHEDULE_PERSISTED;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        if (QUOTA_TRACKER_SCHEDULE_LOGGED.equals(str2)) {
            if (this.mConstants.ENABLE_API_QUOTAS) {
                return QUOTA_TRACKER_CATEGORY_SCHEDULE_LOGGED;
            }
            return QUOTA_TRACKER_CATEGORY_DISABLED;
        }
        android.util.Slog.wtf(TAG, "Unexpected category tag: " + str2);
        return QUOTA_TRACKER_CATEGORY_DISABLED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        android.os.Process.setThreadPriority(-2);
        java.util.ArrayList<com.android.server.job.controllers.JobStatus> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList<com.android.server.job.controllers.JobStatus> arrayList2 = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            try {
                getJobStore().getRtcCorrectedJobsLocked(arrayList2, arrayList);
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.job.controllers.JobStatus jobStatus = arrayList.get(i);
                    com.android.server.job.controllers.JobStatus jobStatus2 = arrayList2.get(i);
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "  replacing " + jobStatus + " with " + jobStatus2);
                    }
                    cancelJobImplLocked(jobStatus, jobStatus2, 14, 9, "deferred rtc calculation");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("jobscheduler", this.mJobSchedulerStub);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (480 == i) {
            try {
                this.mStartControllerTrackingLatch.await();
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "Couldn't wait on controller tracking start latch");
            }
            try {
                this.mJobStoreLoadedLatch.await();
                return;
            } catch (java.lang.InterruptedException e2) {
                android.util.Slog.e(TAG, "Couldn't wait on job store loading latch");
                return;
            }
        }
        if (500 != i) {
            if (i == 600) {
                synchronized (this.mLock) {
                    this.mReadyToRock = true;
                    this.mLocalDeviceIdleController = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
                    this.mConcurrencyManager.onThirdPartyAppsCanStart();
                    this.mJobs.forEachJob(new java.util.function.Consumer() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.job.JobSchedulerService.this.lambda$onBootPhase$4((com.android.server.job.controllers.JobStatus) obj);
                        }
                    });
                    com.android.server.job.Flags.doNotForceRushExecutionAtBoot();
                    this.mHandler.obtainMessage(1).sendToTarget();
                }
                return;
            }
            return;
        }
        this.mConstantsObserver.start();
        for (int size = this.mControllers.size() - 1; size >= 0; size--) {
            this.mControllers.get(size).onSystemServicesReady();
        }
        com.android.server.AppStateTracker appStateTracker = (com.android.server.AppStateTracker) com.android.server.LocalServices.getService(com.android.server.AppStateTracker.class);
        java.util.Objects.requireNonNull(appStateTracker);
        this.mAppStateTracker = (com.android.server.AppStateTrackerImpl) appStateTracker;
        ((android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class)).registerCloudProviderChangeListener(new com.android.server.job.JobSchedulerService.CloudProviderChangeListener());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter("android.intent.action.UID_REMOVED"), null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        try {
            android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 15, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e3) {
        }
        this.mConcurrencyManager.onSystemReady();
        cancelJobsForNonExistentUsers();
        for (int size2 = this.mJobRestrictions.size() - 1; size2 >= 0; size2--) {
            this.mJobRestrictions.get(size2).onSystemServicesReady();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$4(com.android.server.job.controllers.JobStatus jobStatus) {
        for (int i = 0; i < this.mControllers.size(); i++) {
            this.mControllers.get(i).maybeStartTrackingJobLocked(jobStatus, null);
        }
    }

    private void startControllerTrackingAsync() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.JobSchedulerService.this.lambda$startControllerTrackingAsync$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startControllerTrackingAsync$5() {
        synchronized (this.mLock) {
            try {
                for (int size = this.mControllers.size() - 1; size >= 0; size--) {
                    this.mControllers.get(size).startTrackingLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mStartControllerTrackingLatch.countDown();
    }

    private void startTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (!jobStatus.isPreparedLocked()) {
            android.util.Slog.wtf(TAG, "Not yet prepared when started tracking: " + jobStatus);
        }
        jobStatus.enqueueTime = sElapsedRealtimeClock.millis();
        boolean z = jobStatus2 != null;
        this.mJobs.add(jobStatus);
        resetPendingJobReasonCache(jobStatus);
        if (this.mReadyToRock) {
            for (int i = 0; i < this.mControllers.size(); i++) {
                com.android.server.job.controllers.StateController stateController = this.mControllers.get(i);
                if (z) {
                    stateController.maybeStopTrackingJobLocked(jobStatus, null);
                }
                stateController.maybeStartTrackingJobLocked(jobStatus, jobStatus2);
            }
        }
    }

    private boolean stopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2, boolean z) {
        jobStatus.stopTrackingJobLocked(jobStatus2);
        synchronized (this.mPendingJobReasonCache) {
            try {
                android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) this.mPendingJobReasonCache.get(jobStatus.getUid(), jobStatus.getNamespace());
                if (sparseIntArray != null) {
                    sparseIntArray.delete(jobStatus.getJobId());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        boolean remove = this.mJobs.remove(jobStatus, z);
        if (!remove) {
            android.util.Slog.w(TAG, "Job didn't exist in JobStore: " + jobStatus.toShortString());
        }
        if (this.mReadyToRock) {
            for (int i = 0; i < this.mControllers.size(); i++) {
                this.mControllers.get(i).maybeStopTrackingJobLocked(jobStatus, jobStatus2);
            }
        }
        return remove;
    }

    void resetPendingJobReasonCache(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        synchronized (this.mPendingJobReasonCache) {
            try {
                android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) this.mPendingJobReasonCache.get(jobStatus.getUid(), jobStatus.getNamespace());
                if (sparseIntArray != null) {
                    sparseIntArray.delete(jobStatus.getJobId());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isCurrentlyRunningLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mConcurrencyManager.isJobRunningLocked(jobStatus);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isJobInOvertimeLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mConcurrencyManager.isJobInOvertimeLocked(jobStatus);
    }

    private void noteJobPending(com.android.server.job.controllers.JobStatus jobStatus) {
        this.mJobPackageTracker.notePending(jobStatus);
    }

    void noteJobsPending(android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            noteJobPending(arraySet.valueAt(size));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteJobNonPending(com.android.server.job.controllers.JobStatus jobStatus) {
        this.mJobPackageTracker.noteNonpending(jobStatus);
    }

    private void clearPendingJobQueue() {
        this.mPendingJobQueue.resetIterator();
        while (true) {
            com.android.server.job.controllers.JobStatus next = this.mPendingJobQueue.next();
            if (next != null) {
                noteJobNonPending(next);
            } else {
                this.mPendingJobQueue.clear();
                return;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.job.controllers.JobStatus getRescheduleJobForFailureLocked(com.android.server.job.controllers.JobStatus jobStatus, int i, int i2) {
        int i3;
        int i4;
        long j;
        long min;
        if (i2 == 11 && jobStatus.isUserVisibleJob()) {
            android.util.Slog.i(TAG, "Dropping " + jobStatus.toShortString() + " because of user stop");
            return null;
        }
        long millis = sElapsedRealtimeClock.millis();
        android.app.job.JobInfo job = jobStatus.getJob();
        long initialBackoffMillis = job.getInitialBackoffMillis();
        int numFailures = jobStatus.getNumFailures();
        int numSystemStops = jobStatus.getNumSystemStops();
        if (i2 == 10 || i2 == 3 || i2 == 12 || i == 13) {
            i3 = numFailures + 1;
            i4 = numSystemStops;
        } else {
            i3 = numFailures;
            i4 = numSystemStops + 1;
        }
        int i5 = i3 + (i4 / this.mConstants.SYSTEM_STOP_TO_FAILURE_RATIO);
        if (i5 == 0) {
            min = 0;
        } else {
            switch (job.getBackoffPolicy()) {
                case 0:
                    if (initialBackoffMillis < this.mConstants.MIN_LINEAR_BACKOFF_TIME_MS) {
                        initialBackoffMillis = this.mConstants.MIN_LINEAR_BACKOFF_TIME_MS;
                    }
                    j = initialBackoffMillis * i5;
                    break;
                default:
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "Unrecognised back-off policy, defaulting to exponential.");
                    }
                case 1:
                    if (initialBackoffMillis < this.mConstants.MIN_EXP_BACKOFF_TIME_MS) {
                        initialBackoffMillis = this.mConstants.MIN_EXP_BACKOFF_TIME_MS;
                    }
                    j = (long) java.lang.Math.scalb(initialBackoffMillis, i5 - 1);
                    break;
            }
            min = millis + java.lang.Math.min(j, 18000000L);
        }
        com.android.server.job.controllers.JobStatus jobStatus2 = new com.android.server.job.controllers.JobStatus(jobStatus, min, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, i3, i4, jobStatus.getLastSuccessfulRunTime(), sSystemClock.millis(), jobStatus.getCumulativeExecutionTimeMs());
        if (i == 13) {
            jobStatus2.addInternalFlags(2);
        }
        if (jobStatus2.getCumulativeExecutionTimeMs() >= this.mConstants.RUNTIME_CUMULATIVE_UI_LIMIT_MS && jobStatus2.shouldTreatAsUserInitiatedJob()) {
            jobStatus2.addInternalFlags(4);
        }
        if (job.isPeriodic()) {
            jobStatus2.setOriginalLatestRunTimeElapsed(jobStatus.getOriginalLatestRunTimeElapsed());
        }
        for (int i6 = 0; i6 < this.mControllers.size(); i6++) {
            this.mControllers.get(i6).rescheduleForFailureLocked(jobStatus2, jobStatus);
        }
        return jobStatus2;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.JobStatus getRescheduleJobForPeriodic(com.android.server.job.controllers.JobStatus jobStatus) {
        long j;
        long j2;
        long millis = sElapsedRealtimeClock.millis();
        long max = java.lang.Math.max(android.app.job.JobInfo.getMinPeriodMillis(), java.lang.Math.min(31536000000L, jobStatus.getJob().getIntervalMillis()));
        long max2 = java.lang.Math.max(android.app.job.JobInfo.getMinFlexMillis(), java.lang.Math.min(max, jobStatus.getJob().getFlexMillis()));
        long originalLatestRunTimeElapsed = jobStatus.getOriginalLatestRunTimeElapsed();
        if (originalLatestRunTimeElapsed < 0 || originalLatestRunTimeElapsed == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            android.util.Slog.wtf(TAG, "Invalid periodic job original latest run time: " + originalLatestRunTimeElapsed);
            originalLatestRunTimeElapsed = millis;
        }
        long abs = java.lang.Math.abs(millis - originalLatestRunTimeElapsed);
        if (millis > originalLatestRunTimeElapsed) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "Periodic job ran after its intended window by " + abs + " ms");
            }
            long j3 = (abs / max) + 1;
            if (max != max2 && (max - max2) - (abs % max) <= max2 / 6) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Custom flex job ran too close to next window.");
                }
                j3++;
            }
            j = originalLatestRunTimeElapsed + (j3 * max);
            j2 = 0;
        } else {
            j = originalLatestRunTimeElapsed + max;
            if (abs < 1800000) {
                long j4 = max / 6;
                if (abs < j4) {
                    j2 = java.lang.Math.min(1800000L, j4 - abs);
                }
            }
            j2 = 0;
        }
        if (j < millis) {
            android.util.Slog.wtf(TAG, "Rescheduling calculated latest runtime in the past: " + j);
            long j5 = millis + max;
            return new com.android.server.job.controllers.JobStatus(jobStatus, j5 - max2, j5, 0, 0, sSystemClock.millis(), jobStatus.getLastFailedRunTime(), 0L);
        }
        long min = j - java.lang.Math.min(max2, max - j2);
        if (DEBUG) {
            android.util.Slog.v(TAG, "Rescheduling executed periodic. New execution window [" + (min / 1000) + ", " + (j / 1000) + "]s");
        }
        return new com.android.server.job.controllers.JobStatus(jobStatus, min, j, 0, 0, sSystemClock.millis(), jobStatus.getLastFailedRunTime(), 0L);
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeProcessBuggyJob(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, int i) {
        java.lang.String str;
        boolean z = i == 3;
        if (!z && jobStatus.madeActive > 0) {
            long millis = sUptimeMillisClock.millis() - jobStatus.madeActive;
            if (jobStatus.startedAsUserInitiatedJob) {
                z = millis >= this.mConstants.RUNTIME_MIN_UI_GUARANTEE_MS;
            } else if (jobStatus.startedAsExpeditedJob) {
                z = millis >= this.mConstants.RUNTIME_MIN_EJ_GUARANTEE_MS;
            } else {
                z = millis >= this.mConstants.RUNTIME_MIN_GUARANTEE_MS;
            }
        }
        if (z) {
            int timeoutBlameUserId = jobStatus.getTimeoutBlameUserId();
            java.lang.String timeoutBlamePackageName = jobStatus.getTimeoutBlamePackageName();
            com.android.server.utils.quota.CountQuotaTracker countQuotaTracker = this.mQuotaTracker;
            if (jobStatus.startedAsUserInitiatedJob) {
                str = QUOTA_TRACKER_TIMEOUT_UIJ_TAG;
            } else if (jobStatus.startedAsExpeditedJob) {
                str = QUOTA_TRACKER_TIMEOUT_EJ_TAG;
            } else {
                str = QUOTA_TRACKER_TIMEOUT_REG_TAG;
            }
            countQuotaTracker.noteEvent(timeoutBlameUserId, timeoutBlamePackageName, str);
            if (!this.mQuotaTracker.noteEvent(timeoutBlameUserId, timeoutBlamePackageName, QUOTA_TRACKER_TIMEOUT_TOTAL_TAG)) {
                this.mAppStandbyInternal.restrictApp(timeoutBlamePackageName, timeoutBlameUserId, 4);
            }
        }
        if (i == 12) {
            int userId = jobStatus.getUserId();
            java.lang.String packageName = jobStatus.getServiceComponent().getPackageName();
            if (!this.mQuotaTracker.noteEvent(userId, packageName, QUOTA_TRACKER_ANR_TAG)) {
                this.mAppStandbyInternal.restrictApp(packageName, userId, 4);
            }
        }
    }

    @Override // com.android.server.job.JobCompletedListener
    public void onJobCompletedLocked(com.android.server.job.controllers.JobStatus jobStatus, int i, int i2, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Completed " + jobStatus + ", reason=" + i2 + ", reschedule=" + z);
        }
        this.mLastCompletedJobs[this.mLastCompletedJobIndex] = jobStatus;
        this.mLastCompletedJobTimeElapsed[this.mLastCompletedJobIndex] = sElapsedRealtimeClock.millis();
        this.mLastCompletedJobIndex = (this.mLastCompletedJobIndex + 1) % 20;
        maybeProcessBuggyJob(jobStatus, i2);
        if (i2 == 7 || i2 == 8) {
            jobStatus.unprepareLocked();
            reportActiveLocked();
            return;
        }
        com.android.server.job.controllers.JobStatus rescheduleJobForFailureLocked = z ? getRescheduleJobForFailureLocked(jobStatus, i, i2) : null;
        if (rescheduleJobForFailureLocked != null && !rescheduleJobForFailureLocked.shouldTreatAsUserInitiatedJob() && (i2 == 3 || i2 == 2)) {
            rescheduleJobForFailureLocked.disallowRunInBatterySaverAndDoze();
        }
        if (!stopTrackingJobLocked(jobStatus, rescheduleJobForFailureLocked, !jobStatus.getJob().isPeriodic())) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Could not find job to remove. Was job removed while executing?");
            }
            com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(jobStatus.getUid(), jobStatus.getNamespace(), jobStatus.getJobId());
            if (jobByUidAndJobId != null) {
                this.mHandler.obtainMessage(0, jobByUidAndJobId).sendToTarget();
                return;
            }
            return;
        }
        if (rescheduleJobForFailureLocked != null) {
            try {
                rescheduleJobForFailureLocked.prepareLocked();
            } catch (java.lang.SecurityException e) {
                android.util.Slog.w(TAG, "Unable to regrant job permissions for " + rescheduleJobForFailureLocked);
            }
            startTrackingJobLocked(rescheduleJobForFailureLocked, jobStatus);
        } else if (jobStatus.getJob().isPeriodic()) {
            com.android.server.job.controllers.JobStatus rescheduleJobForPeriodic = getRescheduleJobForPeriodic(jobStatus);
            try {
                rescheduleJobForPeriodic.prepareLocked();
            } catch (java.lang.SecurityException e2) {
                android.util.Slog.w(TAG, "Unable to regrant job permissions for " + rescheduleJobForPeriodic);
            }
            startTrackingJobLocked(rescheduleJobForPeriodic, jobStatus);
        }
        jobStatus.unprepareLocked();
        reportActiveLocked();
    }

    @Override // com.android.server.job.StateChangedListener
    public void onControllerStateChanged(@android.annotation.Nullable android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet) {
        if (arraySet == null) {
            this.mHandler.obtainMessage(1).sendToTarget();
            synchronized (this.mPendingJobReasonCache) {
                this.mPendingJobReasonCache.clear();
            }
            return;
        }
        if (arraySet.size() > 0) {
            synchronized (this.mLock) {
                this.mChangedJobList.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) arraySet);
            }
            this.mHandler.obtainMessage(8).sendToTarget();
            synchronized (this.mPendingJobReasonCache) {
                try {
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        resetPendingJobReasonCache(arraySet.valueAt(size));
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.job.StateChangedListener
    public void onRestrictionStateChanged(@android.annotation.NonNull com.android.server.job.restrictions.JobRestriction jobRestriction, boolean z) {
        this.mHandler.obtainMessage(1).sendToTarget();
        if (z) {
            synchronized (this.mLock) {
                this.mConcurrencyManager.maybeStopOvertimeJobsLocked(jobRestriction);
            }
        }
    }

    @Override // com.android.server.job.StateChangedListener
    public void onRunJobNow(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus == null) {
            this.mHandler.obtainMessage(3).sendToTarget();
        } else {
            this.mHandler.obtainMessage(0, jobStatus).sendToTarget();
        }
    }

    private final class JobHandler extends android.os.Handler {
        public JobHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    if (com.android.server.job.JobSchedulerService.this.mReadyToRock) {
                        boolean z = true;
                        switch (message.what) {
                            case 0:
                                com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) message.obj;
                                if (jobStatus != null) {
                                    if (com.android.server.job.JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus)) {
                                        com.android.server.job.JobSchedulerService.this.mJobPackageTracker.notePending(jobStatus);
                                        com.android.server.job.JobSchedulerService.this.mPendingJobQueue.add(jobStatus);
                                    }
                                    com.android.server.job.JobSchedulerService.this.mChangedJobList.remove(jobStatus);
                                } else {
                                    android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "Given null job to check individually");
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 1:
                                if (com.android.server.job.JobSchedulerService.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "MSG_CHECK_JOB");
                                }
                                if (com.android.server.job.JobSchedulerService.this.mReportedActive) {
                                    com.android.server.job.JobSchedulerService.this.queueReadyJobsForExecutionLocked();
                                } else {
                                    com.android.server.job.JobSchedulerService.this.maybeQueueReadyJobsForExecutionLocked();
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 2:
                                com.android.server.job.JobSchedulerService.this.cancelJobImplLocked((com.android.server.job.controllers.JobStatus) message.obj, null, message.arg1, 1, "app no longer allowed to run");
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 3:
                                if (com.android.server.job.JobSchedulerService.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "MSG_CHECK_JOB_GREEDY");
                                }
                                com.android.server.job.JobSchedulerService.this.queueReadyJobsForExecutionLocked();
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 4:
                                com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                                com.android.server.job.JobSchedulerService.this.updateUidState(someArgs.argi1, someArgs.argi2, someArgs.argi3);
                                someArgs.recycle();
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 5:
                                int i = message.arg1;
                                if (message.arg2 == 0) {
                                    z = false;
                                }
                                com.android.server.job.JobSchedulerService.this.updateUidState(i, 19, 0);
                                if (z) {
                                    com.android.server.job.JobSchedulerService.this.cancelJobsForUid(i, true, 11, 1, "uid gone");
                                }
                                synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                    com.android.server.job.JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i, false);
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 6:
                                int i2 = message.arg1;
                                synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                    com.android.server.job.JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i2, true);
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 7:
                                int i3 = message.arg1;
                                if (message.arg2 == 0) {
                                    z = false;
                                }
                                if (z) {
                                    com.android.server.job.JobSchedulerService.this.cancelJobsForUid(i3, true, 11, 1, "app uid idle");
                                }
                                synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                    com.android.server.job.JobSchedulerService.this.mDeviceIdleJobsController.setUidActiveLocked(i3, false);
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 8:
                                if (com.android.server.job.JobSchedulerService.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "MSG_CHECK_CHANGED_JOB_LIST");
                                }
                                com.android.server.job.JobSchedulerService.this.checkChangedJobListLocked();
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 9:
                                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                                synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                    com.android.server.job.JobSchedulerService.this.updateMediaBackupExemptionLocked(someArgs2.argi1, (java.lang.String) someArgs2.arg1, (java.lang.String) someArgs2.arg2);
                                }
                                someArgs2.recycle();
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 10:
                                android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver = (android.app.job.IUserVisibleJobObserver) message.obj;
                                synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                                    for (int size = com.android.server.job.JobSchedulerService.this.mConcurrencyManager.mActiveServices.size() - 1; size >= 0; size--) {
                                        com.android.server.job.controllers.JobStatus runningJobLocked = com.android.server.job.JobSchedulerService.this.mConcurrencyManager.mActiveServices.get(size).getRunningJobLocked();
                                        if (runningJobLocked != null && runningJobLocked.isUserVisibleJob()) {
                                            try {
                                                iUserVisibleJobObserver.onUserVisibleJobStateChanged(runningJobLocked.getUserVisibleJobSummary(), true);
                                            } catch (android.os.RemoteException e) {
                                            }
                                        }
                                    }
                                }
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            case 11:
                                com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                                android.app.job.UserVisibleJobSummary userVisibleJobSummary = ((com.android.server.job.controllers.JobStatus) someArgs3.arg2).getUserVisibleJobSummary();
                                boolean z2 = someArgs3.argi1 == 1;
                                for (int beginBroadcast = com.android.server.job.JobSchedulerService.this.mUserVisibleJobObservers.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                                    try {
                                        com.android.server.job.JobSchedulerService.this.mUserVisibleJobObservers.getBroadcastItem(beginBroadcast).onUserVisibleJobStateChanged(userVisibleJobSummary, z2);
                                    } catch (android.os.RemoteException e2) {
                                    }
                                }
                                com.android.server.job.JobSchedulerService.this.mUserVisibleJobObservers.finishBroadcast();
                                someArgs3.recycle();
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                            default:
                                com.android.server.job.JobSchedulerService.this.maybeRunPendingJobsLocked();
                                return;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.job.restrictions.JobRestriction checkIfRestricted(com.android.server.job.controllers.JobStatus jobStatus) {
        if (evaluateJobBiasLocked(jobStatus) >= 35) {
            return null;
        }
        for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
            com.android.server.job.restrictions.JobRestriction jobRestriction = this.mJobRestrictions.get(size);
            if (jobRestriction.isJobRestricted(jobStatus)) {
                return jobRestriction;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopNonReadyActiveJobsLocked() {
        this.mConcurrencyManager.stopNonReadyActiveJobsLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void queueReadyJobsForExecutionLocked() {
        this.mHandler.removeMessages(3);
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(8);
        this.mChangedJobList.clear();
        if (DEBUG) {
            android.util.Slog.d(TAG, "queuing all ready jobs for execution:");
        }
        clearPendingJobQueue();
        stopNonReadyActiveJobsLocked();
        this.mJobs.forEachJob(this.mReadyQueueFunctor);
        this.mReadyQueueFunctor.postProcessLocked();
        if (DEBUG) {
            int size = this.mPendingJobQueue.size();
            if (size == 0) {
                android.util.Slog.d(TAG, "No jobs pending.");
                return;
            }
            android.util.Slog.d(TAG, size + " jobs queued.");
        }
    }

    final class ReadyJobQueueFunctor implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
        final android.util.ArraySet<com.android.server.job.controllers.JobStatus> newReadyJobs = new android.util.ArraySet<>();

        ReadyJobQueueFunctor() {
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            if (com.android.server.job.JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus)) {
                if (com.android.server.job.JobSchedulerService.DEBUG) {
                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "    queued " + jobStatus.toShortString());
                }
                this.newReadyJobs.add(jobStatus);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void postProcessLocked() {
            com.android.server.job.JobSchedulerService.this.noteJobsPending(this.newReadyJobs);
            com.android.server.job.JobSchedulerService.this.mPendingJobQueue.addAll(this.newReadyJobs);
            this.newReadyJobs.clear();
        }
    }

    final class MaybeReadyJobQueueFunctor implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {

        @com.android.internal.annotations.VisibleForTesting
        final android.util.ArrayMap<android.net.Network, android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mBatches = new android.util.ArrayMap<>();

        @com.android.internal.annotations.VisibleForTesting
        final java.util.List<com.android.server.job.controllers.JobStatus> runnableJobs = new java.util.ArrayList();
        final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mUnbatchedJobs = new android.util.ArraySet<>();
        final android.util.ArrayMap<android.net.Network, java.lang.Integer> mUnbatchedJobCount = new android.util.ArrayMap<>();

        public MaybeReadyJobQueueFunctor() {
            reset();
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            java.lang.String str;
            boolean z;
            boolean isCurrentlyRunningLocked = com.android.server.job.JobSchedulerService.this.isCurrentlyRunningLocked(jobStatus);
            int i = 6;
            if (com.android.server.job.JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus, false)) {
                if (com.android.server.job.JobSchedulerService.this.mActivityManagerInternal.isAppStartModeDisabled(jobStatus.getUid(), jobStatus.getJob().getService().getPackageName())) {
                    android.util.Slog.w(com.android.server.job.JobSchedulerService.TAG, "Aborting job " + jobStatus.getUid() + ":" + jobStatus.getJob().toString() + " -- package not allowed to start");
                    if (isCurrentlyRunningLocked) {
                        com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(2, 11, 0, jobStatus).sendToTarget();
                        return;
                    } else {
                        if (com.android.server.job.JobSchedulerService.this.mPendingJobQueue.remove(jobStatus)) {
                            com.android.server.job.JobSchedulerService.this.noteJobNonPending(jobStatus);
                            return;
                        }
                        return;
                    }
                }
                if (jobStatus.overrideState > 0) {
                    z = false;
                } else if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.shouldTreatAsUserInitiatedJob()) {
                    z = false;
                } else if (jobStatus.getEffectiveStandbyBucket() == 5) {
                    z = true;
                } else if (jobStatus.getJob().isPrefetch()) {
                    z = com.android.server.job.JobSchedulerService.this.mPrefetchController.getNextEstimatedLaunchTimeLocked(jobStatus) > com.android.server.job.JobSchedulerService.sSystemClock.millis() + com.android.server.job.JobSchedulerService.this.mConstants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS;
                } else if (jobStatus.getNumPreviousAttempts() > 0) {
                    z = false;
                } else {
                    long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    if (jobStatus.hasDeadlineConstraint()) {
                        jobStatus.getLatestRunTimeElapsed();
                    }
                    com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
                    com.android.server.job.Flags.batchActiveBucketJobs();
                    z = (!(com.android.server.job.JobSchedulerService.this.mConstants.MIN_READY_NON_ACTIVE_JOBS_COUNT > 1 && jobStatus.getEffectiveStandbyBucket() != 0) || jobStatus.getEffectiveStandbyBucket() == 6 || ((jobStatus.getFirstForceBatchedTimeElapsed() > 0L ? 1 : (jobStatus.getFirstForceBatchedTimeElapsed() == 0L ? 0 : -1)) > 0 && ((millis - jobStatus.getFirstForceBatchedTimeElapsed()) > com.android.server.job.JobSchedulerService.this.mConstants.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS ? 1 : ((millis - jobStatus.getFirstForceBatchedTimeElapsed()) == com.android.server.job.JobSchedulerService.this.mConstants.MAX_NON_ACTIVE_JOB_BATCH_DELAY_MS ? 0 : -1)) >= 0)) ? false : true;
                }
                com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mBatches.get(null);
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    this.mBatches.put(null, arraySet);
                }
                arraySet.add(jobStatus);
                if (!z) {
                    this.mUnbatchedJobCount.put(null, java.lang.Integer.valueOf(this.mUnbatchedJobCount.getOrDefault(jobStatus.network, 0).intValue() + 1));
                } else if (jobStatus.getFirstForceBatchedTimeElapsed() == 0) {
                    jobStatus.setFirstForceBatchedTimeElapsed(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                }
                if (!isCurrentlyRunningLocked) {
                    this.runnableJobs.add(jobStatus);
                    if (!z) {
                        this.mUnbatchedJobs.add(jobStatus);
                        return;
                    }
                    return;
                }
                return;
            }
            if (!isCurrentlyRunningLocked) {
                if (com.android.server.job.JobSchedulerService.this.mPendingJobQueue.remove(jobStatus)) {
                    com.android.server.job.JobSchedulerService.this.noteJobNonPending(jobStatus);
                    return;
                }
                return;
            }
            if (!jobStatus.isReady()) {
                if (jobStatus.getEffectiveStandbyBucket() == 5 && jobStatus.getStopReason() == 12) {
                    str = "cancelled due to restricted bucket";
                } else {
                    str = "cancelled due to unsatisfied constraints";
                    i = 1;
                }
            } else {
                com.android.server.job.restrictions.JobRestriction checkIfRestricted = com.android.server.job.JobSchedulerService.this.checkIfRestricted(jobStatus);
                if (checkIfRestricted != null) {
                    i = checkIfRestricted.getInternalReason();
                    str = "restricted due to " + android.app.job.JobParameters.getInternalReasonCodeDescription(i);
                } else {
                    i = -1;
                    str = "couldn't figure out why the job should stop running";
                }
            }
            com.android.server.job.JobSchedulerService.this.mConcurrencyManager.stopJobOnServiceContextLocked(jobStatus, jobStatus.getStopReason(), i, str);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @com.android.internal.annotations.VisibleForTesting
        void postProcessLocked() {
            int i;
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mUnbatchedJobs;
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: " + this.mUnbatchedJobs.size() + " unbatched jobs.");
            }
            int i2 = 0;
            for (int size = this.mBatches.size() - 1; size >= 0; size--) {
                android.net.Network keyAt = this.mBatches.keyAt(size);
                java.lang.Integer num = this.mUnbatchedJobCount.get(keyAt);
                if (num != null) {
                    i = num.intValue();
                    i2 += i;
                } else {
                    i = 0;
                }
                if (keyAt != null) {
                    android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mBatches.valueAt(size);
                    if (i > 0) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: piggybacking " + (valueAt.size() - i) + " jobs on " + keyAt + " because of unbatched job");
                        }
                        arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) valueAt);
                    } else {
                        android.net.NetworkCapabilities networkCapabilities = com.android.server.job.JobSchedulerService.this.mConnectivityController.getNetworkCapabilities(keyAt);
                        if (networkCapabilities == null) {
                            android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "Couldn't get NetworkCapabilities for network " + keyAt);
                        } else {
                            int i3 = 1;
                            for (int i4 : networkCapabilities.getTransportTypes()) {
                                i3 = java.lang.Math.max(i3, com.android.server.job.JobSchedulerService.this.mConstants.CONN_TRANSPORT_BATCH_THRESHOLD.get(i4));
                            }
                            if (valueAt.size() >= i3) {
                                if (com.android.server.job.JobSchedulerService.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: " + valueAt.size() + " batched network jobs meet requirement for " + keyAt);
                                }
                                arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) valueAt);
                            }
                        }
                    }
                }
            }
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = this.mBatches.get(null);
            if (arraySet2 != null) {
                com.android.server.job.Flags.batchActiveBucketJobs();
                int i5 = com.android.server.job.JobSchedulerService.this.mConstants.MIN_READY_NON_ACTIVE_JOBS_COUNT;
                if (arraySet.size() > 0) {
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        java.lang.Integer num2 = this.mUnbatchedJobCount.get(null);
                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: piggybacking " + (arraySet2.size() - (num2 == null ? 0 : num2.intValue())) + " non-network jobs");
                    }
                    arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) arraySet2);
                } else if (arraySet2.size() >= i5) {
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: adding " + arraySet2.size() + " batched non-network jobs.");
                    }
                    arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) arraySet2);
                }
            }
            final com.android.server.job.JobSchedulerService jobSchedulerService = com.android.server.job.JobSchedulerService.this;
            arraySet.removeIf(new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$MaybeReadyJobQueueFunctor$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.server.job.JobSchedulerService.this.isCurrentlyRunningLocked((com.android.server.job.controllers.JobStatus) obj);
                }
            });
            if (i2 > 0 || arraySet.size() > 0) {
                if (com.android.server.job.JobSchedulerService.DEBUG) {
                    android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: Running " + arraySet + " jobs.");
                }
                com.android.server.job.JobSchedulerService.this.noteJobsPending(arraySet);
                com.android.server.job.JobSchedulerService.this.mPendingJobQueue.addAll(arraySet);
            } else if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "maybeQueueReadyJobsForExecutionLocked: Not running anything.");
            }
            int size2 = this.runnableJobs.size();
            if (size2 > 0 && size2 != arraySet.size()) {
                synchronized (com.android.server.job.JobSchedulerService.this.mPendingJobReasonCache) {
                    for (int i6 = 0; i6 < size2; i6++) {
                        try {
                            com.android.server.job.controllers.JobStatus jobStatus = this.runnableJobs.get(i6);
                            if (!arraySet.contains(jobStatus)) {
                                android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) com.android.server.job.JobSchedulerService.this.mPendingJobReasonCache.get(jobStatus.getUid(), jobStatus.getNamespace());
                                if (sparseIntArray == null) {
                                    sparseIntArray = new android.util.SparseIntArray();
                                    com.android.server.job.JobSchedulerService.this.mPendingJobReasonCache.add(jobStatus.getUid(), jobStatus.getNamespace(), sparseIntArray);
                                }
                                sparseIntArray.put(jobStatus.getJobId(), 13);
                            }
                        } finally {
                        }
                    }
                }
            }
            reset();
        }

        @com.android.internal.annotations.VisibleForTesting
        void reset() {
            this.runnableJobs.clear();
            this.mBatches.clear();
            this.mUnbatchedJobs.clear();
            this.mUnbatchedJobCount.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeQueueReadyJobsForExecutionLocked() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(8);
        this.mChangedJobList.clear();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Maybe queuing ready jobs...");
        }
        clearPendingJobQueue();
        stopNonReadyActiveJobsLocked();
        this.mJobs.forEachJob(this.mMaybeQueueFunctor);
        this.mMaybeQueueFunctor.postProcessLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void checkChangedJobListLocked() {
        this.mHandler.removeMessages(8);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Check changed jobs...");
        }
        if (this.mChangedJobList.size() == 0) {
            return;
        }
        this.mChangedJobList.forEach(this.mMaybeQueueFunctor);
        this.mMaybeQueueFunctor.postProcessLocked();
        this.mChangedJobList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateMediaBackupExemptionLocked(final int i, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.String str2) {
        this.mJobs.forEachJob(new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateMediaBackupExemptionLocked$6;
                lambda$updateMediaBackupExemptionLocked$6 = com.android.server.job.JobSchedulerService.lambda$updateMediaBackupExemptionLocked$6(i, str, str2, (com.android.server.job.controllers.JobStatus) obj);
                return lambda$updateMediaBackupExemptionLocked$6;
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.JobSchedulerService.this.lambda$updateMediaBackupExemptionLocked$7((com.android.server.job.controllers.JobStatus) obj);
            }
        });
        this.mHandler.sendEmptyMessage(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateMediaBackupExemptionLocked$6(int i, java.lang.String str, java.lang.String str2, com.android.server.job.controllers.JobStatus jobStatus) {
        return jobStatus.getSourceUserId() == i && (jobStatus.getSourcePackageName().equals(str) || jobStatus.getSourcePackageName().equals(str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMediaBackupExemptionLocked$7(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.updateMediaBackupExemptionStatus()) {
            this.mChangedJobList.add(jobStatus);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean areUsersStartedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        boolean contains = com.android.internal.util.jobs.ArrayUtils.contains(this.mStartedUsers, jobStatus.getSourceUserId());
        if (jobStatus.getUserId() == jobStatus.getSourceUserId()) {
            return contains;
        }
        return contains && com.android.internal.util.jobs.ArrayUtils.contains(this.mStartedUsers, jobStatus.getUserId());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isReadyToBeExecutedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return isReadyToBeExecutedLocked(jobStatus, true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isReadyToBeExecutedLocked(com.android.server.job.controllers.JobStatus jobStatus, boolean z) {
        boolean z2 = jobStatus.isReady() || evaluateControllerStatesLocked(jobStatus);
        if (DEBUG) {
            android.util.Slog.v(TAG, "isReadyToBeExecutedLocked: " + jobStatus.toShortString() + " ready=" + z2);
        }
        if (!z2) {
            if (jobStatus.getSourcePackageName().equals("android.jobscheduler.cts.jobtestapp")) {
                android.util.Slog.v(TAG, "    NOT READY: " + jobStatus);
            }
            return false;
        }
        boolean containsJob = this.mJobs.containsJob(jobStatus);
        boolean areUsersStartedLocked = areUsersStartedLocked(jobStatus);
        boolean z3 = this.mBackingUpUids.get(jobStatus.getSourceUid());
        if (DEBUG) {
            android.util.Slog.v(TAG, "isReadyToBeExecutedLocked: " + jobStatus.toShortString() + " exists=" + containsJob + " userStarted=" + areUsersStartedLocked + " backingUp=" + z3);
        }
        if (!containsJob || !areUsersStartedLocked || z3 || checkIfRestricted(jobStatus) != null) {
            return false;
        }
        boolean contains = this.mPendingJobQueue.contains(jobStatus);
        boolean z4 = z && this.mConcurrencyManager.isJobRunningLocked(jobStatus);
        if (DEBUG) {
            android.util.Slog.v(TAG, "isReadyToBeExecutedLocked: " + jobStatus.toShortString() + " pending=" + contains + " active=" + z4);
        }
        if (contains || z4) {
            return false;
        }
        return isComponentUsable(jobStatus);
    }

    private boolean isComponentUsable(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        java.lang.String str = jobStatus.serviceProcessName;
        if (str == null) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "isComponentUsable: " + jobStatus.toShortString() + " component not present");
                return false;
            }
            return false;
        }
        boolean isAppBad = this.mActivityManagerInternal.isAppBad(str, jobStatus.getUid());
        if (DEBUG && isAppBad) {
            android.util.Slog.i(TAG, "App is bad for " + jobStatus.toShortString() + " so not runnable");
        }
        return !isAppBad;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean evaluateControllerStatesLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        for (int size = this.mControllers.size() - 1; size >= 0; size--) {
            this.mControllers.get(size).evaluateStateLocked(jobStatus);
        }
        return jobStatus.isReady();
    }

    public boolean areComponentsInPlaceLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        boolean containsJob = this.mJobs.containsJob(jobStatus);
        boolean areUsersStartedLocked = areUsersStartedLocked(jobStatus);
        boolean z = this.mBackingUpUids.get(jobStatus.getSourceUid());
        if (DEBUG) {
            android.util.Slog.v(TAG, "areComponentsInPlaceLocked: " + jobStatus.toShortString() + " exists=" + containsJob + " userStarted=" + areUsersStartedLocked + " backingUp=" + z);
        }
        if (!containsJob || !areUsersStartedLocked || z) {
            return false;
        }
        com.android.server.job.restrictions.JobRestriction checkIfRestricted = checkIfRestricted(jobStatus);
        if (checkIfRestricted != null) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "areComponentsInPlaceLocked: " + jobStatus.toShortString() + " restricted due to " + checkIfRestricted.getInternalReason());
            }
            return false;
        }
        return isComponentUsable(jobStatus);
    }

    public long getMinJobExecutionGuaranteeMs(com.android.server.job.controllers.JobStatus jobStatus) {
        long min;
        long j;
        synchronized (this.mLock) {
            try {
                if (jobStatus.shouldTreatAsUserInitiatedJob() && checkRunUserInitiatedJobsPermission(jobStatus.getSourceUid(), jobStatus.getSourcePackageName())) {
                    if (this.mQuotaTracker.isWithinQuota(jobStatus.getTimeoutBlameUserId(), jobStatus.getTimeoutBlamePackageName(), QUOTA_TRACKER_TIMEOUT_UIJ_TAG)) {
                        j = this.mConstants.RUNTIME_UI_LIMIT_MS;
                    } else {
                        j = this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
                    }
                    if (jobStatus.getJob().getRequiredNetwork() != null) {
                        if (this.mConstants.RUNTIME_USE_DATA_ESTIMATES_FOR_LIMITS) {
                            long estimatedTransferTimeMs = this.mConnectivityController.getEstimatedTransferTimeMs(jobStatus);
                            if (estimatedTransferTimeMs == -1) {
                                return java.lang.Math.min(j, this.mConstants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS);
                            }
                            return java.lang.Math.min(j, java.lang.Math.max((long) (estimatedTransferTimeMs * this.mConstants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_BUFFER_FACTOR), this.mConstants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS));
                        }
                        return java.lang.Math.min(j, java.lang.Math.max(this.mConstants.RUNTIME_MIN_UI_GUARANTEE_MS, this.mConstants.RUNTIME_MIN_UI_DATA_TRANSFER_GUARANTEE_MS));
                    }
                    return java.lang.Math.min(j, this.mConstants.RUNTIME_MIN_UI_GUARANTEE_MS);
                }
                if (jobStatus.shouldTreatAsExpeditedJob()) {
                    if (jobStatus.getEffectiveStandbyBucket() != 5) {
                        min = this.mConstants.RUNTIME_MIN_EJ_GUARANTEE_MS;
                    } else {
                        min = java.lang.Math.min(this.mConstants.RUNTIME_MIN_EJ_GUARANTEE_MS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                    }
                    return min;
                }
                return this.mConstants.RUNTIME_MIN_GUARANTEE_MS;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getMaxJobExecutionTimeMs(com.android.server.job.controllers.JobStatus jobStatus) {
        long j;
        long maxJobExecutionTimeMsLocked;
        synchronized (this.mLock) {
            try {
                if (jobStatus.shouldTreatAsUserInitiatedJob() && checkRunUserInitiatedJobsPermission(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()) && this.mQuotaTracker.isWithinQuota(jobStatus.getTimeoutBlameUserId(), jobStatus.getTimeoutBlamePackageName(), QUOTA_TRACKER_TIMEOUT_UIJ_TAG)) {
                    return this.mConstants.RUNTIME_UI_LIMIT_MS;
                }
                if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                    return this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
                }
                java.lang.String str = jobStatus.shouldTreatAsExpeditedJob() ? QUOTA_TRACKER_TIMEOUT_EJ_TAG : QUOTA_TRACKER_TIMEOUT_REG_TAG;
                if (jobStatus.shouldTreatAsExpeditedJob()) {
                    j = this.mConstants.RUNTIME_MIN_GUARANTEE_MS;
                } else {
                    j = this.mConstants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
                }
                if (!this.mQuotaTracker.isWithinQuota(jobStatus.getTimeoutBlameUserId(), jobStatus.getTimeoutBlamePackageName(), str)) {
                    j = this.mConstants.RUNTIME_MIN_GUARANTEE_MS;
                }
                if (this.mConstants.USE_TARE_POLICY) {
                    maxJobExecutionTimeMsLocked = this.mTareController.getMaxJobExecutionTimeMsLocked(jobStatus);
                } else {
                    maxJobExecutionTimeMsLocked = this.mQuotaController.getMaxJobExecutionTimeMsLocked(jobStatus);
                }
                return java.lang.Math.min(j, maxJobExecutionTimeMsLocked);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void maybeRunPendingJobsLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "pending queue: " + this.mPendingJobQueue.size() + " jobs.");
        }
        this.mConcurrencyManager.assignJobsToContextsLocked();
        reportActiveLocked();
    }

    private int adjustJobBias(int i, com.android.server.job.controllers.JobStatus jobStatus) {
        if (i < 40) {
            float loadFactor = this.mJobPackageTracker.getLoadFactor(jobStatus);
            if (loadFactor >= this.mConstants.HEAVY_USE_FACTOR) {
                return i - 80;
            }
            if (loadFactor >= this.mConstants.MODERATE_USE_FACTOR) {
                return i - 40;
            }
            return i;
        }
        return i;
    }

    int evaluateJobBiasLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        int bias = jobStatus.getBias();
        if (bias >= 30) {
            return adjustJobBias(bias, jobStatus);
        }
        int i = this.mUidBiasOverride.get(jobStatus.getSourceUid(), 0);
        if (i != 0) {
            return adjustJobBias(i, jobStatus);
        }
        return adjustJobBias(bias, jobStatus);
    }

    void informObserversOfUserVisibleJobChange(com.android.server.job.JobServiceContext jobServiceContext, com.android.server.job.controllers.JobStatus jobStatus, boolean z) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = jobServiceContext;
        obtain.arg2 = jobStatus;
        obtain.argi1 = z ? 1 : 0;
        this.mHandler.obtainMessage(11, obtain).sendToTarget();
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BatteryStateTracker extends android.content.BroadcastReceiver implements android.os.BatteryManagerInternal.ChargingPolicyChangeListener {
        private int mBatteryLevel;
        private boolean mBatteryNotLow;
        private boolean mCharging;
        private int mChargingPolicy;
        private android.content.BroadcastReceiver mMonitor;
        private boolean mPowerConnected;
        private int mLastBatterySeq = -1;
        private final android.os.BatteryManagerInternal mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);

        BatteryStateTracker() {
        }

        public void startTracking() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_LOW");
            intentFilter.addAction("android.intent.action.BATTERY_OKAY");
            intentFilter.addAction("android.os.action.CHARGING");
            intentFilter.addAction("android.os.action.DISCHARGING");
            intentFilter.addAction("android.intent.action.BATTERY_LEVEL_CHANGED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            com.android.server.job.JobSchedulerService.this.getTestableContext().registerReceiver(this, intentFilter);
            this.mBatteryManagerInternal.registerChargingPolicyChangeListener(this);
            this.mBatteryLevel = this.mBatteryManagerInternal.getBatteryLevel();
            this.mBatteryNotLow = !this.mBatteryManagerInternal.getBatteryLevelLow();
            this.mCharging = this.mBatteryManagerInternal.isPowered(15);
            this.mChargingPolicy = this.mBatteryManagerInternal.getChargingPolicy();
        }

        public void setMonitorBatteryLocked(boolean z) {
            if (z) {
                if (this.mMonitor == null) {
                    this.mMonitor = new android.content.BroadcastReceiver() { // from class: com.android.server.job.JobSchedulerService.BatteryStateTracker.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(android.content.Context context, android.content.Intent intent) {
                            com.android.server.job.JobSchedulerService.BatteryStateTracker.this.onReceiveInternal(intent);
                        }
                    };
                    android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                    intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                    com.android.server.job.JobSchedulerService.this.getTestableContext().registerReceiver(this.mMonitor, intentFilter);
                    return;
                }
                return;
            }
            if (this.mMonitor != null) {
                com.android.server.job.JobSchedulerService.this.getTestableContext().unregisterReceiver(this.mMonitor);
                this.mMonitor = null;
            }
        }

        public boolean isCharging() {
            return isConsideredCharging();
        }

        public boolean isBatteryNotLow() {
            return this.mBatteryNotLow;
        }

        public boolean isMonitoring() {
            return this.mMonitor != null;
        }

        public boolean isPowerConnected() {
            return this.mPowerConnected;
        }

        public int getSeq() {
            return this.mLastBatterySeq;
        }

        public void onChargingPolicyChanged(int i) {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    if (this.mChargingPolicy == i) {
                        return;
                    }
                    if (com.android.server.job.JobSchedulerService.DEBUG) {
                        android.util.Slog.i(com.android.server.job.JobSchedulerService.TAG, "Charging policy changed from " + this.mChargingPolicy + " to " + i);
                    }
                    boolean isConsideredCharging = isConsideredCharging();
                    this.mChargingPolicy = i;
                    if (isConsideredCharging() != isConsideredCharging) {
                        for (int size = com.android.server.job.JobSchedulerService.this.mControllers.size() - 1; size >= 0; size--) {
                            com.android.server.job.JobSchedulerService.this.mControllers.get(size).onBatteryStateChangedLocked();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            onReceiveInternal(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onReceiveInternal(android.content.Intent intent) {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    java.lang.String action = intent.getAction();
                    if ("android.intent.action.BATTERY_LOW".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Battery life too low @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (this.mBatteryNotLow) {
                            this.mBatteryNotLow = false;
                            r3 = true;
                        }
                    } else if ("android.intent.action.BATTERY_OKAY".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Battery high enough @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (!this.mBatteryNotLow) {
                            this.mBatteryNotLow = true;
                            r3 = true;
                        }
                    } else if ("android.intent.action.BATTERY_LEVEL_CHANGED".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Battery level changed @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        boolean isConsideredCharging = isConsideredCharging();
                        this.mBatteryLevel = this.mBatteryManagerInternal.getBatteryLevel();
                        r3 = isConsideredCharging() != isConsideredCharging;
                    } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Power connected @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (this.mPowerConnected) {
                            return;
                        }
                        this.mPowerConnected = true;
                        r3 = true;
                    } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Power disconnected @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (!this.mPowerConnected) {
                            return;
                        }
                        this.mPowerConnected = false;
                        r3 = true;
                    } else if ("android.os.action.CHARGING".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Battery charging @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (!this.mCharging) {
                            boolean isConsideredCharging2 = isConsideredCharging();
                            this.mCharging = true;
                            r3 = isConsideredCharging() != isConsideredCharging2;
                        }
                    } else if ("android.os.action.DISCHARGING".equals(action)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Battery discharging @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                        }
                        if (this.mCharging) {
                            boolean isConsideredCharging3 = isConsideredCharging();
                            this.mCharging = false;
                            if (isConsideredCharging() != isConsideredCharging3) {
                                r3 = true;
                            }
                        }
                    }
                    this.mLastBatterySeq = intent.getIntExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mLastBatterySeq);
                    if (r3) {
                        for (int size = com.android.server.job.JobSchedulerService.this.mControllers.size() - 1; size >= 0; size--) {
                            com.android.server.job.JobSchedulerService.this.mControllers.get(size).onBatteryStateChangedLocked();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isConsideredCharging() {
            if (this.mCharging) {
                return true;
            }
            if (this.mPowerConnected && this.mChargingPolicy != Integer.MIN_VALUE) {
                return this.mBatteryLevel >= 70 && android.os.BatteryManager.isAdaptiveChargingPolicy(this.mChargingPolicy);
            }
            return false;
        }
    }

    final class LocalService implements com.android.server.job.JobSchedulerInternal {
        LocalService() {
        }

        public java.util.List<android.app.job.JobInfo> getSystemScheduledOwnJobs(@android.annotation.Nullable final java.lang.String str) {
            final java.util.ArrayList arrayList;
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                arrayList = new java.util.ArrayList();
                com.android.server.job.JobSchedulerService.this.mJobs.forEachJob(1000, new java.util.function.Consumer() { // from class: com.android.server.job.JobSchedulerService$LocalService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.job.JobSchedulerService.LocalService.lambda$getSystemScheduledOwnJobs$0(str, arrayList, (com.android.server.job.controllers.JobStatus) obj);
                    }
                });
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getSystemScheduledOwnJobs$0(java.lang.String str, java.util.List list, com.android.server.job.controllers.JobStatus jobStatus) {
            if (jobStatus.getSourceUid() == 1000 && java.util.Objects.equals(jobStatus.getNamespace(), str) && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(jobStatus.getSourcePackageName())) {
                list.add(jobStatus.getJob());
            }
        }

        public void cancelJobsForUid(int i, boolean z, int i2, int i3, java.lang.String str) {
            com.android.server.job.JobSchedulerService.this.cancelJobsForUid(i, z, i2, i3, str);
        }

        public void addBackingUpUid(int i) {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                com.android.server.job.JobSchedulerService.this.mBackingUpUids.put(i, true);
            }
        }

        public void removeBackingUpUid(int i) {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    com.android.server.job.JobSchedulerService.this.mBackingUpUids.delete(i);
                    if (com.android.server.job.JobSchedulerService.this.mJobs.countJobsForUid(i) > 0) {
                        com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(1).sendToTarget();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void clearAllBackingUpUids() {
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    if (com.android.server.job.JobSchedulerService.this.mBackingUpUids.size() > 0) {
                        com.android.server.job.JobSchedulerService.this.mBackingUpUids.clear();
                        com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(1).sendToTarget();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.lang.String getCloudMediaProviderPackage(int i) {
            return (java.lang.String) com.android.server.job.JobSchedulerService.this.mCloudMediaProviderPackages.get(i);
        }

        public void reportAppUsage(java.lang.String str, int i) {
            com.android.server.job.JobSchedulerService.this.reportAppUsage(str, i);
        }

        public boolean isAppConsideredBuggy(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull java.lang.String str2) {
            return (com.android.server.job.JobSchedulerService.this.mQuotaTracker.isWithinQuota(i, str, com.android.server.job.JobSchedulerService.QUOTA_TRACKER_ANR_TAG) && com.android.server.job.JobSchedulerService.this.mQuotaTracker.isWithinQuota(i, str, com.android.server.job.JobSchedulerService.QUOTA_TRACKER_SCHEDULE_PERSISTED_TAG) && com.android.server.job.JobSchedulerService.this.mQuotaTracker.isWithinQuota(i2, str2, com.android.server.job.JobSchedulerService.QUOTA_TRACKER_TIMEOUT_TOTAL_TAG)) ? false : true;
        }

        public boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            if (str == null) {
                return false;
            }
            return com.android.server.job.JobSchedulerService.this.mConcurrencyManager.isNotificationAssociatedWithAnyUserInitiatedJobs(i, i2, str);
        }

        public boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
            if (str2 == null || str == null) {
                return false;
            }
            return com.android.server.job.JobSchedulerService.this.mConcurrencyManager.isNotificationChannelAssociatedWithAnyUserInitiatedJobs(str, i, str2);
        }

        public boolean hasRunBackupJobsPermission(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.job.JobSchedulerService.this.hasRunBackupJobsPermission(str, i);
        }

        public com.android.server.job.JobSchedulerInternal.JobStorePersistStats getPersistStats() {
            com.android.server.job.JobSchedulerInternal.JobStorePersistStats jobStorePersistStats;
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                jobStorePersistStats = new com.android.server.job.JobSchedulerInternal.JobStorePersistStats(com.android.server.job.JobSchedulerService.this.mJobs.getPersistStats());
            }
            return jobStorePersistStats;
        }
    }

    final class StandbyTracker extends com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener {
        StandbyTracker() {
        }

        public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
        }

        public void onUserInteractionStarted(java.lang.String str, int i) {
            long j;
            int packageUid = com.android.server.job.JobSchedulerService.this.mLocalPM.getPackageUid(str, 8192L, i);
            if (packageUid < 0) {
                return;
            }
            long timeSinceLastJobRun = com.android.server.job.JobSchedulerService.sUsageStatsManagerInternal.getTimeSinceLastJobRun(str, i);
            if (timeSinceLastJobRun <= 172800000) {
                j = timeSinceLastJobRun;
            } else {
                j = 0;
            }
            com.android.server.job.JobSchedulerService.DeferredJobCounter deferredJobCounter = new com.android.server.job.JobSchedulerService.DeferredJobCounter();
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                com.android.server.job.JobSchedulerService.this.mJobs.forEachJobForSourceUid(packageUid, deferredJobCounter);
            }
            if (deferredJobCounter.numDeferred() > 0 || j > 0) {
                com.android.server.job.JobSchedulerService.this.mBatteryStatsInternal.noteJobsDeferred(packageUid, deferredJobCounter.numDeferred(), j);
                com.android.internal.util.FrameworkStatsLog.write_non_chained(85, packageUid, (java.lang.String) null, deferredJobCounter.numDeferred(), j);
            }
        }
    }

    static class DeferredJobCounter implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
        private int mDeferred = 0;

        DeferredJobCounter() {
        }

        public int numDeferred() {
            return this.mDeferred;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            if (jobStatus.getWhenStandbyDeferred() > 0) {
                this.mDeferred++;
            }
        }
    }

    public static int standbyBucketToBucketIndex(int i) {
        if (i == 50) {
            return 4;
        }
        if (i > 40) {
            return 5;
        }
        if (i > 30) {
            return 3;
        }
        if (i > 20) {
            return 2;
        }
        if (i > 10) {
            return 1;
        }
        if (i > 5) {
            return 0;
        }
        return 6;
    }

    public static int standbyBucketForPackage(java.lang.String str, int i, long j) {
        int i2;
        if (sUsageStatsManagerInternal != null) {
            i2 = sUsageStatsManagerInternal.getAppStandbyBucket(str, i, j);
        } else {
            i2 = 0;
        }
        int standbyBucketToBucketIndex = standbyBucketToBucketIndex(i2);
        if (DEBUG_STANDBY) {
            android.util.Slog.v(TAG, str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " standby bucket index: " + standbyBucketToBucketIndex);
        }
        return standbyBucketToBucketIndex;
    }

    static int safelyScaleBytesToKBForHistogram(long j) {
        long j2 = j / 1000;
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (j2 < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j2;
    }

    private class CloudProviderChangeListener implements android.os.storage.StorageManagerInternal.CloudProviderChangeListener {
        private CloudProviderChangeListener() {
        }

        public void onCloudProviderChanged(int i, @android.annotation.Nullable java.lang.String str) {
            android.content.pm.ProviderInfo resolveContentProvider = com.android.server.job.JobSchedulerService.this.getContext().createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().resolveContentProvider(str, android.content.pm.PackageManager.ComponentInfoFlags.of(0L));
            java.lang.String str2 = resolveContentProvider == null ? null : resolveContentProvider.packageName;
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    java.lang.String str3 = (java.lang.String) com.android.server.job.JobSchedulerService.this.mCloudMediaProviderPackages.get(i);
                    if (!java.util.Objects.equals(str3, str2)) {
                        if (com.android.server.job.JobSchedulerService.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Cloud provider of user " + i + " changed from " + str3 + " to " + str2);
                        }
                        com.android.server.job.JobSchedulerService.this.mCloudMediaProviderPackages.put(i, str2);
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.argi1 = i;
                        obtain.arg1 = str3;
                        obtain.arg2 = str2;
                        com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(9, obtain).sendToTarget();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasPermission(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mPermissionCache) {
            try {
                android.util.SparseArrayMap<java.lang.String, java.lang.Boolean> sparseArrayMap = this.mPermissionCache.get(i);
                if (sparseArrayMap == null) {
                    sparseArrayMap = new android.util.SparseArrayMap<>();
                    this.mPermissionCache.put(i, sparseArrayMap);
                }
                java.lang.Boolean bool = (java.lang.Boolean) sparseArrayMap.get(i2, str);
                if (bool != null) {
                    return bool.booleanValue();
                }
                boolean z = getContext().checkPermission(str, i2, i) == 0;
                sparseArrayMap.add(i2, str, java.lang.Boolean.valueOf(z));
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasRunBackupJobsPermission(@android.annotation.NonNull java.lang.String str, int i) {
        if (str != null) {
            return android.content.PermissionChecker.checkPermissionForPreflight(getTestableContext(), "android.permission.RUN_BACKUP_JOBS", -1, i, str) == 0;
        }
        android.util.Slog.wtfStack(TAG, "Expected a non-null package name when calling hasRunBackupJobsPermission");
        return false;
    }

    final class JobSchedulerStub extends android.app.job.IJobScheduler.Stub {
        JobSchedulerStub() {
        }

        private void enforceValidJobRequest(int i, int i2, android.app.job.JobInfo jobInfo) {
            android.content.pm.PackageManager packageManager = com.android.server.job.JobSchedulerService.this.getContext().createContextAsUser(android.os.UserHandle.getUserHandleForUid(i), 0).getPackageManager();
            android.content.ComponentName service = jobInfo.getService();
            try {
                android.content.pm.ServiceInfo serviceInfo = packageManager.getServiceInfo(service, 786432);
                if (serviceInfo == null) {
                    throw new java.lang.IllegalArgumentException("No such service " + service);
                }
                if (serviceInfo.applicationInfo.uid != i) {
                    throw new java.lang.IllegalArgumentException("uid " + i + " cannot schedule job in " + service.getPackageName());
                }
                if (!"android.permission.BIND_JOB_SERVICE".equals(serviceInfo.permission)) {
                    throw new java.lang.IllegalArgumentException("Scheduled service " + service + " does not require android.permission.BIND_JOB_SERVICE permission");
                }
                if (jobInfo.isPersisted() && !canPersistJobs(i2, i)) {
                    throw new java.lang.IllegalArgumentException("Requested job cannot be persisted without holding android.permission.RECEIVE_BOOT_COMPLETED permission");
                }
                if (jobInfo.getRequiredNetwork() != null && android.app.compat.CompatChanges.isChangeEnabled(com.android.server.job.JobSchedulerService.REQUIRE_NETWORK_PERMISSIONS_FOR_CONNECTIVITY_JOBS, i) && !com.android.server.job.JobSchedulerService.this.hasPermission(i, i2, "android.permission.ACCESS_NETWORK_STATE")) {
                    throw new java.lang.SecurityException("android.permission.ACCESS_NETWORK_STATE required for jobs with a connectivity constraint");
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.IllegalArgumentException("Tried to schedule job for non-existent component: " + service);
            }
        }

        private android.app.job.JobInfo enforceBuilderApiPermissions(int i, int i2, android.app.job.JobInfo jobInfo) {
            if (jobInfo.getBias() != 0 && !com.android.server.job.JobSchedulerService.this.hasPermission(i, i2, "android.permission.UPDATE_DEVICE_STATS")) {
                if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.job.JobSchedulerService.THROW_ON_UNSUPPORTED_BIAS_USAGE, i)) {
                    throw new java.lang.SecurityException("Apps may not call setBias()");
                }
                android.util.Slog.w(com.android.server.job.JobSchedulerService.TAG, "Uid " + i + " set bias on its job");
                return new android.app.job.JobInfo.Builder(jobInfo).setBias(0).build(false, false, false, false);
            }
            return jobInfo;
        }

        private boolean canPersistJobs(int i, int i2) {
            return com.android.server.job.JobSchedulerService.this.hasPermission(i2, i, "android.permission.RECEIVE_BOOT_COMPLETED");
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x008a  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00b2 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00a1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int validateJob(@android.annotation.NonNull android.app.job.JobInfo jobInfo, int i, int i2, int i3, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.app.job.JobWorkItem jobWorkItem) {
            int i4;
            java.lang.String packageName;
            boolean z;
            int validateRunUserInitiatedJobsPermission;
            boolean isInStateToScheduleUserInitiatedJobs;
            boolean isChangeEnabled = android.app.compat.CompatChanges.isChangeEnabled(253665015L, i);
            jobInfo.enforceValidity(android.app.compat.CompatChanges.isChangeEnabled(194532703L, i), isChangeEnabled, android.app.compat.CompatChanges.isChangeEnabled(311402873L, i), android.app.compat.CompatChanges.isChangeEnabled(323349338L, i));
            if ((jobInfo.getFlags() & 1) != 0) {
                com.android.server.job.JobSchedulerService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONNECTIVITY_INTERNAL", com.android.server.job.JobSchedulerService.TAG);
            }
            if ((jobInfo.getFlags() & 8) != 0) {
                if (i != 1000) {
                    throw new java.lang.SecurityException("Job has invalid flags");
                }
                if (jobInfo.isPeriodic()) {
                    android.util.Slog.wtf(com.android.server.job.JobSchedulerService.TAG, "Periodic jobs mustn't have FLAG_EXEMPT_FROM_APP_STANDBY. Job=" + jobInfo);
                }
            }
            if (jobInfo.isUserInitiated()) {
                int i5 = -1;
                if (i3 != -1 && str != null) {
                    try {
                        i4 = android.app.AppGlobals.getPackageManager().getPackageUid(str, 0L, i3);
                    } catch (android.os.RemoteException e) {
                    }
                    packageName = jobInfo.getService().getPackageName();
                    if (i4 != -1) {
                        z = false;
                    } else {
                        int validateRunUserInitiatedJobsPermission2 = validateRunUserInitiatedJobsPermission(i4, str);
                        if (validateRunUserInitiatedJobsPermission2 != 1) {
                            return validateRunUserInitiatedJobsPermission2;
                        }
                        if (i == i4 && packageName.equals(str)) {
                            i5 = i2;
                        }
                        z = isInStateToScheduleUserInitiatedJobs(i4, i5, str);
                    }
                    if (i == i4 || !packageName.equals(str)) {
                        validateRunUserInitiatedJobsPermission = validateRunUserInitiatedJobsPermission(i, packageName);
                        if (validateRunUserInitiatedJobsPermission == 1) {
                            return validateRunUserInitiatedJobsPermission;
                        }
                        if (!z) {
                            isInStateToScheduleUserInitiatedJobs = isInStateToScheduleUserInitiatedJobs(i, i2, packageName);
                            if (!z && !isInStateToScheduleUserInitiatedJobs) {
                                android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "Uid(s) " + i4 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " not in a state to schedule user-initiated jobs");
                                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_invalid_state", i);
                                return 0;
                            }
                        }
                    }
                    isInStateToScheduleUserInitiatedJobs = false;
                    if (!z) {
                        android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "Uid(s) " + i4 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " not in a state to schedule user-initiated jobs");
                        com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_invalid_state", i);
                        return 0;
                    }
                }
                i4 = -1;
                packageName = jobInfo.getService().getPackageName();
                if (i4 != -1) {
                }
                if (i == i4) {
                }
                validateRunUserInitiatedJobsPermission = validateRunUserInitiatedJobsPermission(i, packageName);
                if (validateRunUserInitiatedJobsPermission == 1) {
                }
            }
            if (jobWorkItem != null) {
                jobWorkItem.enforceValidity(isChangeEnabled);
                if ((jobWorkItem.getEstimatedNetworkDownloadBytes() != -1 || jobWorkItem.getEstimatedNetworkUploadBytes() != -1 || jobWorkItem.getMinimumNetworkChunkBytes() != -1) && jobInfo.getRequiredNetwork() == null) {
                    if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.job.JobSchedulerService.REQUIRE_NETWORK_CONSTRAINT_FOR_NETWORK_JOB_WORK_ITEMS, i)) {
                        throw new java.lang.IllegalArgumentException("JobWorkItem implies network usage but job doesn't specify a network constraint");
                    }
                    android.util.Slog.e(com.android.server.job.JobSchedulerService.TAG, "JobWorkItem implies network usage but job doesn't specify a network constraint");
                }
                if (jobInfo.isPersisted() && jobWorkItem.getIntent() != null) {
                    throw new java.lang.IllegalArgumentException("Cannot persist JobWorkItems with Intents");
                }
            }
            return 1;
        }

        private java.lang.String validateNamespace(@android.annotation.Nullable java.lang.String str) {
            java.lang.String sanitizeNamespace = android.app.job.JobScheduler.sanitizeNamespace(str);
            if (sanitizeNamespace != null) {
                if (sanitizeNamespace.isEmpty()) {
                    throw new java.lang.IllegalArgumentException("namespace cannot be empty");
                }
                if (sanitizeNamespace.length() > 1000) {
                    throw new java.lang.IllegalArgumentException("namespace cannot be more than 1000 characters");
                }
                return sanitizeNamespace.intern();
            }
            return sanitizeNamespace;
        }

        private int validateRunUserInitiatedJobsPermission(int i, java.lang.String str) {
            int runUserInitiatedJobsPermissionState = com.android.server.job.JobSchedulerService.this.getRunUserInitiatedJobsPermissionState(i, str);
            if (runUserInitiatedJobsPermissionState == 2) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission", i);
                throw new java.lang.SecurityException("android.permission.RUN_USER_INITIATED_JOBS required to schedule user-initiated jobs.");
            }
            if (runUserInitiatedJobsPermissionState != 1) {
                return 1;
            }
            com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission", i);
            return 0;
        }

        private boolean isInStateToScheduleUserInitiatedJobs(int i, int i2, java.lang.String str) {
            int uidProcessState = com.android.server.job.JobSchedulerService.this.mActivityManagerInternal.getUidProcessState(i);
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Uid " + i + " proc state=" + android.app.ActivityManager.procStateToString(uidProcessState));
            }
            if (uidProcessState == 2) {
                return true;
            }
            boolean canScheduleUserInitiatedJobs = com.android.server.job.JobSchedulerService.this.mActivityManagerInternal.canScheduleUserInitiatedJobs(i, i2, str);
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Uid " + i + " AM.canScheduleUserInitiatedJobs= " + canScheduleUserInitiatedJobs);
            }
            return canScheduleUserInitiatedJobs;
        }

        public int schedule(java.lang.String str, android.app.job.JobInfo jobInfo) throws android.os.RemoteException {
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Scheduling job: " + jobInfo.toString());
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            int validateJob = validateJob(jobInfo, callingUid, callingPid, -1, null, null);
            if (validateJob != 1) {
                return validateJob;
            }
            java.lang.String validateNamespace = validateNamespace(str);
            android.app.job.JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.job.JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, null, callingUid, null, userId, validateNamespace, null);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int enqueue(java.lang.String str, android.app.job.JobInfo jobInfo, android.app.job.JobWorkItem jobWorkItem) throws android.os.RemoteException {
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Enqueueing job: " + jobInfo.toString() + " work: " + jobWorkItem);
            }
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            if (jobWorkItem == null) {
                throw new java.lang.NullPointerException("work is null");
            }
            int validateJob = validateJob(jobInfo, callingUid, callingPid, -1, null, jobWorkItem);
            if (validateJob != 1) {
                return validateJob;
            }
            java.lang.String validateNamespace = validateNamespace(str);
            android.app.job.JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.job.JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, jobWorkItem, callingUid, null, userId, validateNamespace, null);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int scheduleAsPackage(java.lang.String str, android.app.job.JobInfo jobInfo, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            if (com.android.server.job.JobSchedulerService.DEBUG) {
                android.util.Slog.d(com.android.server.job.JobSchedulerService.TAG, "Caller uid " + callingUid + " scheduling job: " + jobInfo.toString() + " on behalf of " + str2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            }
            if (str2 == null) {
                throw new java.lang.NullPointerException("Must specify a package for scheduleAsPackage()");
            }
            if (com.android.server.job.JobSchedulerService.this.getContext().checkCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS") != 0) {
                throw new java.lang.SecurityException("Caller uid " + callingUid + " not permitted to schedule jobs for other apps");
            }
            enforceValidJobRequest(callingUid, callingPid, jobInfo);
            int validateJob = validateJob(jobInfo, callingUid, callingPid, i, str2, null);
            if (validateJob != 1) {
                return validateJob;
            }
            java.lang.String validateNamespace = validateNamespace(str);
            android.app.job.JobInfo enforceBuilderApiPermissions = enforceBuilderApiPermissions(callingUid, callingPid, jobInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.job.JobSchedulerService.this.scheduleAsPackage(enforceBuilderApiPermissions, null, callingUid, str2, i, validateNamespace, str3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.Map<java.lang.String, android.content.pm.ParceledListSlice<android.app.job.JobInfo>> getAllPendingJobs() throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.ArrayMap pendingJobs = com.android.server.job.JobSchedulerService.this.getPendingJobs(callingUid);
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                for (int i = 0; i < pendingJobs.size(); i++) {
                    arrayMap.put((java.lang.String) pendingJobs.keyAt(i), new android.content.pm.ParceledListSlice((java.util.List) pendingJobs.valueAt(i)));
                }
                return arrayMap;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.content.pm.ParceledListSlice<android.app.job.JobInfo> getAllPendingJobsInNamespace(java.lang.String str) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return new android.content.pm.ParceledListSlice<>(com.android.server.job.JobSchedulerService.this.getPendingJobsInNamespace(callingUid, validateNamespace(str)));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.app.job.JobInfo getPendingJob(java.lang.String str, int i) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.job.JobSchedulerService.this.getPendingJob(callingUid, validateNamespace(str), i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getPendingJobReason(java.lang.String str, int i) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.job.JobSchedulerService.this.getPendingJobReason(callingUid, validateNamespace(str), i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelAll() throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.job.JobSchedulerService.this.cancelJobsForUid(callingUid, false, 1, 0, "cancelAll() called by app, callingUid=" + callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelAllInNamespace(java.lang.String str) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.job.JobSchedulerService.this.cancelJobsForUid(callingUid, false, true, validateNamespace(str), 1, 0, "cancelAllInNamespace() called by app, callingUid=" + callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancel(java.lang.String str, int i) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.job.JobSchedulerService.this.cancelJob(callingUid, validateNamespace(str), i, callingUid, 1);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean canRunUserInitiatedJobs(@android.annotation.NonNull java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            int packageUid = com.android.server.job.JobSchedulerService.this.mLocalPM.getPackageUid(str, 0L, android.os.UserHandle.getUserId(callingUid));
            if (callingUid != packageUid) {
                throw new java.lang.SecurityException("Uid " + callingUid + " cannot query canRunUserInitiatedJobs for package " + str);
            }
            return com.android.server.job.JobSchedulerService.this.checkRunUserInitiatedJobsPermission(packageUid, str);
        }

        public boolean hasRunUserInitiatedJobsPermission(@android.annotation.NonNull java.lang.String str, int i) {
            int packageUid = com.android.server.job.JobSchedulerService.this.mLocalPM.getPackageUid(str, 0L, i);
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != packageUid && !android.os.UserHandle.isCore(callingUid)) {
                throw new java.lang.SecurityException("Uid " + callingUid + " cannot query hasRunUserInitiatedJobsPermission for package " + str);
            }
            return com.android.server.job.JobSchedulerService.this.checkRunUserInitiatedJobsPermission(packageUid, str);
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.jobs.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.job.JobSchedulerService.this.getContext(), com.android.server.job.JobSchedulerService.TAG, printWriter)) {
                boolean z = false;
                int i = -1;
                if (!com.android.internal.util.jobs.ArrayUtils.isEmpty(strArr)) {
                    int i2 = 0;
                    boolean z2 = false;
                    while (true) {
                        if (i2 >= strArr.length) {
                            break;
                        }
                        java.lang.String str = strArr[i2];
                        if ("-h".equals(str)) {
                            com.android.server.job.JobSchedulerService.dumpHelp(printWriter);
                            return;
                        }
                        if (!"-a".equals(str)) {
                            if ("--proto".equals(str)) {
                                z2 = true;
                            } else if (str.length() > 0 && str.charAt(0) == '-') {
                                printWriter.println("Unknown option: " + str);
                                return;
                            }
                        }
                        i2++;
                    }
                    if (i2 >= strArr.length) {
                        z = z2;
                    } else {
                        java.lang.String str2 = strArr[i2];
                        try {
                            i = com.android.server.job.JobSchedulerService.this.getContext().getPackageManager().getPackageUid(str2, 4194304);
                            z = z2;
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            printWriter.println("Invalid package: " + str2);
                            return;
                        }
                    }
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (z) {
                        com.android.server.job.JobSchedulerService.this.dumpInternalProto(fileDescriptor, i);
                    } else {
                        com.android.server.job.JobSchedulerService.this.dumpInternal(new android.util.IndentingPrintWriter(printWriter, "  "), i);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
            return new com.android.server.job.JobSchedulerShellCommand(com.android.server.job.JobSchedulerService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public java.util.List<android.app.job.JobInfo> getStartedJobs() {
            java.util.ArrayList arrayList;
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("getStartedJobs() is system internal use only.");
            }
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                try {
                    android.util.ArraySet<com.android.server.job.controllers.JobStatus> runningJobsLocked = com.android.server.job.JobSchedulerService.this.mConcurrencyManager.getRunningJobsLocked();
                    arrayList = new java.util.ArrayList(runningJobsLocked.size());
                    for (int size = runningJobsLocked.size() - 1; size >= 0; size--) {
                        com.android.server.job.controllers.JobStatus valueAt = runningJobsLocked.valueAt(size);
                        if (valueAt != null) {
                            arrayList.add(valueAt.getJob());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public android.content.pm.ParceledListSlice<android.app.job.JobSnapshot> getAllJobSnapshots() {
            android.content.pm.ParceledListSlice<android.app.job.JobSnapshot> parceledListSlice;
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("getAllJobSnapshots() is system internal use only.");
            }
            synchronized (com.android.server.job.JobSchedulerService.this.mLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList(com.android.server.job.JobSchedulerService.this.mJobs.size());
                com.android.server.job.JobSchedulerService.this.mJobs.forEachJob(new java.util.function.Consumer() { // from class: com.android.server.job.JobSchedulerService$JobSchedulerStub$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.job.JobSchedulerService.JobSchedulerStub.this.lambda$getAllJobSnapshots$0(arrayList, (com.android.server.job.controllers.JobStatus) obj);
                    }
                });
                parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
            }
            return parceledListSlice;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getAllJobSnapshots$0(java.util.ArrayList arrayList, com.android.server.job.controllers.JobStatus jobStatus) {
            arrayList.add(new android.app.job.JobSnapshot(jobStatus.getJob(), jobStatus.getSatisfiedConstraintFlags(), com.android.server.job.JobSchedulerService.this.isReadyToBeExecutedLocked(jobStatus)));
        }

        @android.annotation.EnforcePermission(allOf = {"android.permission.MANAGE_ACTIVITY_TASKS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
        public void registerUserVisibleJobObserver(@android.annotation.NonNull android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) {
            super.registerUserVisibleJobObserver_enforcePermission();
            if (iUserVisibleJobObserver == null) {
                throw new java.lang.NullPointerException("observer");
            }
            com.android.server.job.JobSchedulerService.this.mUserVisibleJobObservers.register(iUserVisibleJobObserver);
            com.android.server.job.JobSchedulerService.this.mHandler.obtainMessage(10, iUserVisibleJobObserver).sendToTarget();
        }

        @android.annotation.EnforcePermission(allOf = {"android.permission.MANAGE_ACTIVITY_TASKS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
        public void unregisterUserVisibleJobObserver(@android.annotation.NonNull android.app.job.IUserVisibleJobObserver iUserVisibleJobObserver) {
            super.unregisterUserVisibleJobObserver_enforcePermission();
            if (iUserVisibleJobObserver == null) {
                throw new java.lang.NullPointerException("observer");
            }
            com.android.server.job.JobSchedulerService.this.mUserVisibleJobObservers.unregister(iUserVisibleJobObserver);
        }

        @android.annotation.EnforcePermission(allOf = {"android.permission.MANAGE_ACTIVITY_TASKS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
        public void notePendingUserRequestedAppStop(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2) {
            super.notePendingUserRequestedAppStop_enforcePermission();
            if (str == null) {
                throw new java.lang.NullPointerException(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            }
            com.android.server.job.JobSchedulerService.this.notePendingUserRequestedAppStopInternal(str, i, str2);
        }
    }

    int executeRunCommand(java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, int i2, boolean z, boolean z2) {
        int i3;
        android.util.Slog.d(TAG, "executeRunCommand(): " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " " + i2 + " s=" + z + " f=" + z2);
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        try {
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            if (i == -1) {
                i = 0;
            }
            int packageUid = packageManager.getPackageUid(str, 0L, i);
            if (packageUid < 0) {
                return -1000;
            }
            synchronized (this.mLock) {
                try {
                    com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(packageUid, str2, i2);
                    if (jobByUidAndJobId == null) {
                        return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_NO_JOB;
                    }
                    if (z2) {
                        i3 = 3;
                    } else {
                        i3 = z ? 1 : 2;
                    }
                    jobByUidAndJobId.overrideState = i3;
                    for (int size = this.mControllers.size() - 1; size >= 0; size--) {
                        this.mControllers.get(size).evaluateStateLocked(jobByUidAndJobId);
                    }
                    if (!jobByUidAndJobId.isConstraintsSatisfied()) {
                        if (jobByUidAndJobId.hasConnectivityConstraint() && !jobByUidAndJobId.isConstraintSatisfied(268435456) && jobByUidAndJobId.wouldBeReadyWithConstraint(268435456)) {
                            this.mHandler.postDelayed(checkConstraintRunnableForTesting(this.mHandler, jobByUidAndJobId, countDownLatch, 5, 1000L), 1000L);
                        } else {
                            jobByUidAndJobId.overrideState = 0;
                            return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_CONSTRAINTS;
                        }
                    } else {
                        countDownLatch.countDown();
                    }
                    try {
                        countDownLatch.await(7L, java.util.concurrent.TimeUnit.SECONDS);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.e(TAG, "Couldn't wait for asynchronous constraint change", e);
                    }
                    synchronized (this.mLock) {
                        try {
                            if (!jobByUidAndJobId.isConstraintsSatisfied()) {
                                jobByUidAndJobId.overrideState = 0;
                                return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_CONSTRAINTS;
                            }
                            queueReadyJobsForExecutionLocked();
                            maybeRunPendingJobsLocked();
                            return 0;
                        } finally {
                        }
                    }
                } finally {
                }
            }
        } catch (android.os.RemoteException e2) {
            return 0;
        }
    }

    private static java.lang.Runnable checkConstraintRunnableForTesting(@android.annotation.NonNull final android.os.Handler handler, @android.annotation.NonNull final com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull final java.util.concurrent.CountDownLatch countDownLatch, final int i, final long j) {
        return new java.lang.Runnable() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.JobSchedulerService.lambda$checkConstraintRunnableForTesting$8(i, jobStatus, countDownLatch, handler, j);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkConstraintRunnableForTesting$8(int i, com.android.server.job.controllers.JobStatus jobStatus, java.util.concurrent.CountDownLatch countDownLatch, android.os.Handler handler, long j) {
        if (i <= 0 || jobStatus.isConstraintsSatisfied()) {
            countDownLatch.countDown();
        } else {
            handler.postDelayed(checkConstraintRunnableForTesting(handler, jobStatus, countDownLatch, i - 1, j), j);
        }
    }

    int executeStopCommand(java.io.PrintWriter printWriter, java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, boolean z, int i2, int i3, int i4) {
        if (DEBUG) {
            android.util.Slog.v(TAG, "executeStopJobCommand(): " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " " + i2 + ": " + i3 + "(" + android.app.job.JobParameters.getInternalReasonCodeDescription(i4) + ")");
        }
        synchronized (this.mLock) {
            try {
                if (!this.mConcurrencyManager.executeStopCommandLocked(printWriter, str, i, str2, z, i2, i3, i4)) {
                    printWriter.println("No matching executing jobs found.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    int executeCancelCommand(java.io.PrintWriter printWriter, java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, boolean z, int i2) {
        int i3;
        if (DEBUG) {
            android.util.Slog.v(TAG, "executeCancelCommand(): " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " " + i2);
        }
        try {
            i3 = android.app.AppGlobals.getPackageManager().getPackageUid(str, 0L, i);
        } catch (android.os.RemoteException e) {
            i3 = -1;
        }
        int i4 = i3;
        if (i4 < 0) {
            printWriter.println("Package " + str + " not found.");
            return -1000;
        }
        if (!z) {
            printWriter.println("Canceling all jobs for " + str + " in user " + i);
            if (!cancelJobsForUid(i4, false, 13, 0, "cancel shell command for package")) {
                printWriter.println("No matching jobs found.");
                return 0;
            }
            return 0;
        }
        printWriter.println("Canceling job " + str + "/#" + i2 + " in user " + i);
        if (!cancelJob(i4, str2, i2, 2000, 13)) {
            printWriter.println("No matching job found.");
            return 0;
        }
        return 0;
    }

    void setMonitorBattery(boolean z) {
        synchronized (this.mLock) {
            this.mBatteryStateTracker.setMonitorBatteryLocked(z);
        }
    }

    int getBatterySeq() {
        int seq;
        synchronized (this.mLock) {
            seq = this.mBatteryStateTracker.getSeq();
        }
        return seq;
    }

    public boolean isBatteryCharging() {
        boolean isCharging;
        synchronized (this.mLock) {
            isCharging = this.mBatteryStateTracker.isCharging();
        }
        return isCharging;
    }

    public boolean isBatteryNotLow() {
        boolean isBatteryNotLow;
        synchronized (this.mLock) {
            isBatteryNotLow = this.mBatteryStateTracker.isBatteryNotLow();
        }
        return isBatteryNotLow;
    }

    public boolean isPowerConnected() {
        boolean isPowerConnected;
        synchronized (this.mLock) {
            isPowerConnected = this.mBatteryStateTracker.isPowerConnected();
        }
        return isPowerConnected;
    }

    void setCacheConfigChanges(boolean z) {
        synchronized (this.mLock) {
            this.mConstantsObserver.setCacheConfigChangesLocked(z);
        }
    }

    java.lang.String getConfigValue(java.lang.String str) {
        java.lang.String valueLocked;
        synchronized (this.mLock) {
            valueLocked = this.mConstantsObserver.getValueLocked(str);
        }
        return valueLocked;
    }

    int getStorageSeq() {
        int seq;
        synchronized (this.mLock) {
            seq = this.mStorageController.getTracker().getSeq();
        }
        return seq;
    }

    boolean getStorageNotLow() {
        boolean isStorageNotLow;
        synchronized (this.mLock) {
            isStorageNotLow = this.mStorageController.getTracker().isStorageNotLow();
        }
        return isStorageNotLow;
    }

    int getEstimatedNetworkBytes(java.io.PrintWriter printWriter, java.lang.String str, int i, java.lang.String str2, int i2, int i3) {
        int packageUid;
        long j;
        long j2;
        try {
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            if (i == -1) {
                i = 0;
            }
            packageUid = packageManager.getPackageUid(str, 0L, i);
        } catch (android.os.RemoteException e) {
        }
        if (packageUid < 0) {
            printWriter.print("unknown(");
            printWriter.print(str);
            printWriter.println(")");
            return -1000;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(packageUid, str2, i2);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "get-estimated-network-bytes " + packageUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + ": " + jobByUidAndJobId);
                }
                if (jobByUidAndJobId == null) {
                    printWriter.print("unknown(");
                    android.os.UserHandle.formatUid(printWriter, packageUid);
                    printWriter.print("/jid");
                    printWriter.print(i2);
                    printWriter.println(")");
                    return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_NO_JOB;
                }
                android.util.Pair<java.lang.Long, java.lang.Long> estimatedNetworkBytesLocked = this.mConcurrencyManager.getEstimatedNetworkBytesLocked(str, packageUid, str2, i2);
                if (estimatedNetworkBytesLocked == null) {
                    j = jobByUidAndJobId.getEstimatedNetworkDownloadBytes();
                    j2 = jobByUidAndJobId.getEstimatedNetworkUploadBytes();
                } else {
                    long longValue = ((java.lang.Long) estimatedNetworkBytesLocked.first).longValue();
                    long longValue2 = ((java.lang.Long) estimatedNetworkBytesLocked.second).longValue();
                    j = longValue;
                    j2 = longValue2;
                }
                if (i3 == 0) {
                    printWriter.println(j);
                } else {
                    printWriter.println(j2);
                }
                printWriter.println();
                return 0;
            } finally {
            }
        }
    }

    int getTransferredNetworkBytes(java.io.PrintWriter printWriter, java.lang.String str, int i, java.lang.String str2, int i2, int i3) {
        long j;
        int packageUid;
        long longValue;
        try {
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            int i4 = i;
            if (i4 == -1) {
                i4 = 0;
            }
            j = 0;
            packageUid = packageManager.getPackageUid(str, 0L, i4);
        } catch (android.os.RemoteException e) {
        }
        if (packageUid < 0) {
            printWriter.print("unknown(");
            printWriter.print(str);
            printWriter.println(")");
            return -1000;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(packageUid, str2, i2);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "get-transferred-network-bytes " + packageUid + str2 + "//" + i2 + ": " + jobByUidAndJobId);
                }
                if (jobByUidAndJobId == null) {
                    printWriter.print("unknown(");
                    android.os.UserHandle.formatUid(printWriter, packageUid);
                    printWriter.print("/jid");
                    printWriter.print(i2);
                    printWriter.println(")");
                    return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_NO_JOB;
                }
                android.util.Pair<java.lang.Long, java.lang.Long> transferredNetworkBytesLocked = this.mConcurrencyManager.getTransferredNetworkBytesLocked(str, packageUid, str2, i2);
                if (transferredNetworkBytesLocked == null) {
                    longValue = 0;
                } else {
                    longValue = ((java.lang.Long) transferredNetworkBytesLocked.first).longValue();
                    j = ((java.lang.Long) transferredNetworkBytesLocked.second).longValue();
                }
                if (i3 == 0) {
                    printWriter.println(longValue);
                } else {
                    printWriter.println(j);
                }
                printWriter.println();
                return 0;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkRunUserInitiatedJobsPermission(int i, java.lang.String str) {
        return getRunUserInitiatedJobsPermissionState(i, str) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRunUserInitiatedJobsPermissionState(int i, java.lang.String str) {
        return android.content.PermissionChecker.checkPermissionForPreflight(getTestableContext(), "android.permission.RUN_USER_INITIATED_JOBS", -1, i, str);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.job.controllers.ConnectivityController getConnectivityController() {
        return this.mConnectivityController;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.job.controllers.QuotaController getQuotaController() {
        return this.mQuotaController;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.job.controllers.TareController getTareController() {
        return this.mTareController;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void waitOnAsyncLoadingForTesting() throws java.lang.Exception {
        this.mStartControllerTrackingLatch.await();
    }

    int getJobState(java.io.PrintWriter printWriter, java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, int i2) {
        int packageUid;
        boolean z;
        boolean z2;
        try {
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            if (i == -1) {
                i = 0;
            }
            packageUid = packageManager.getPackageUid(str, 0L, i);
        } catch (android.os.RemoteException e) {
        }
        if (packageUid < 0) {
            printWriter.print("unknown(");
            printWriter.print(str);
            printWriter.println(")");
            return -1000;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.job.controllers.JobStatus jobByUidAndJobId = this.mJobs.getJobByUidAndJobId(packageUid, str2, i2);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "get-job-state " + str2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + packageUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + ": " + jobByUidAndJobId);
                }
                if (jobByUidAndJobId == null) {
                    printWriter.print("unknown(");
                    android.os.UserHandle.formatUid(printWriter, packageUid);
                    printWriter.print("/jid");
                    printWriter.print(i2);
                    printWriter.println(")");
                    return com.android.server.job.JobSchedulerShellCommand.CMD_ERR_NO_JOB;
                }
                boolean z3 = true;
                if (!this.mPendingJobQueue.contains(jobByUidAndJobId)) {
                    z = false;
                } else {
                    printWriter.print("pending");
                    z = true;
                }
                if (this.mConcurrencyManager.isJobRunningLocked(jobByUidAndJobId)) {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println(com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE);
                    z = true;
                }
                if (!com.android.internal.util.jobs.ArrayUtils.contains(this.mStartedUsers, jobByUidAndJobId.getUserId())) {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println("user-stopped");
                    z = true;
                }
                if (!com.android.internal.util.jobs.ArrayUtils.contains(this.mStartedUsers, jobByUidAndJobId.getSourceUserId())) {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println("source-user-stopped");
                    z = true;
                }
                if (this.mBackingUpUids.get(jobByUidAndJobId.getSourceUid())) {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println("backing-up");
                    z = true;
                }
                try {
                    z2 = android.app.AppGlobals.getPackageManager().getServiceInfo(jobByUidAndJobId.getServiceComponent(), 268435456L, jobByUidAndJobId.getUserId()) != null;
                } catch (android.os.RemoteException e2) {
                    z2 = false;
                }
                if (!z2) {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println("no-component");
                    z = true;
                }
                if (!jobByUidAndJobId.isReady()) {
                    z3 = z;
                } else {
                    if (z) {
                        printWriter.print(" ");
                    }
                    printWriter.println("ready");
                }
                if (!z3) {
                    printWriter.print("waiting");
                }
                printWriter.println();
                return 0;
            } finally {
            }
        }
    }

    void resetExecutionQuota(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            this.mQuotaController.clearAppStatsLocked(i, str);
        }
    }

    void resetScheduleQuota() {
        this.mQuotaTracker.clear();
    }

    void triggerDockState(boolean z) {
        android.content.Intent intent;
        if (z) {
            intent = new android.content.Intent("android.intent.action.DOCK_IDLE");
        } else {
            intent = new android.content.Intent("android.intent.action.DOCK_ACTIVE");
        }
        intent.setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Job Scheduler (jobscheduler) dump options:");
        printWriter.println("  [-h] [package] ...");
        printWriter.println("    -h: print this help");
        printWriter.println("  [package] is an optional package name to limit the output to.");
    }

    private static void sortJobs(java.util.List<com.android.server.job.controllers.JobStatus> list) {
        java.util.Collections.sort(list, new java.util.Comparator<com.android.server.job.controllers.JobStatus>() { // from class: com.android.server.job.JobSchedulerService.6
            @Override // java.util.Comparator
            public int compare(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
                int uid = jobStatus.getUid();
                int uid2 = jobStatus2.getUid();
                int jobId = jobStatus.getJobId();
                int jobId2 = jobStatus2.getJobId();
                if (uid != uid2) {
                    return uid < uid2 ? -1 : 1;
                }
                if (jobId < jobId2) {
                    return -1;
                }
                return jobId > jobId2 ? 1 : 0;
            }
        });
    }

    @dalvik.annotation.optimization.NeverCompile
    void dumpInternal(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
        long j;
        boolean z;
        java.util.Iterator<com.android.server.job.controllers.JobStatus> it;
        long j2;
        long j3;
        final int appId = android.os.UserHandle.getAppId(i);
        long millis = sSystemClock.millis();
        long millis2 = sElapsedRealtimeClock.millis();
        long millis3 = sUptimeMillisClock.millis();
        java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate = new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$dumpInternal$9;
                lambda$dumpInternal$9 = com.android.server.job.JobSchedulerService.lambda$dumpInternal$9(appId, (com.android.server.job.controllers.JobStatus) obj);
                return lambda$dumpInternal$9;
            }
        };
        synchronized (this.mLock) {
            try {
                this.mConstants.dump(indentingPrintWriter);
                for (com.android.server.job.controllers.StateController stateController : this.mControllers) {
                    indentingPrintWriter.increaseIndent();
                    stateController.dumpConstants(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Aconfig flags:");
                indentingPrintWriter.increaseIndent();
                com.android.server.job.Flags.batchActiveBucketJobs();
                indentingPrintWriter.print(com.android.server.job.Flags.FLAG_BATCH_ACTIVE_BUCKET_JOBS, false);
                indentingPrintWriter.println();
                com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
                indentingPrintWriter.print(com.android.server.job.Flags.FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK, false);
                indentingPrintWriter.println();
                com.android.server.job.Flags.doNotForceRushExecutionAtBoot();
                indentingPrintWriter.print(com.android.server.job.Flags.FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT, false);
                indentingPrintWriter.println();
                indentingPrintWriter.print("android.app.job.backup_jobs_exemption", java.lang.Boolean.valueOf(android.app.job.Flags.backupJobsExemption()));
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                boolean z2 = true;
                for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
                    this.mJobRestrictions.get(size).dumpConstants(indentingPrintWriter);
                }
                indentingPrintWriter.println();
                this.mQuotaTracker.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Power connected: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.isPowerConnected());
                indentingPrintWriter.print("Battery charging: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mCharging);
                indentingPrintWriter.print("Considered charging: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.isConsideredCharging());
                indentingPrintWriter.print("Battery level: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.mBatteryLevel);
                indentingPrintWriter.print("Battery not low: ");
                indentingPrintWriter.println(this.mBatteryStateTracker.isBatteryNotLow());
                if (this.mBatteryStateTracker.isMonitoring()) {
                    indentingPrintWriter.print("MONITORING: seq=");
                    indentingPrintWriter.println(this.mBatteryStateTracker.getSeq());
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Started users: " + java.util.Arrays.toString(this.mStartedUsers));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Media Cloud Providers: ");
                indentingPrintWriter.println(this.mCloudMediaProviderPackages);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Registered ");
                indentingPrintWriter.print(this.mJobs.size());
                indentingPrintWriter.println(" jobs:");
                indentingPrintWriter.increaseIndent();
                if (this.mJobs.size() <= 0) {
                    j = millis;
                    z = false;
                } else {
                    java.util.List<com.android.server.job.controllers.JobStatus> allJobs = this.mJobs.mJobSet.getAllJobs();
                    sortJobs(allJobs);
                    java.util.Iterator<com.android.server.job.controllers.JobStatus> it2 = allJobs.iterator();
                    z = false;
                    while (it2.hasNext()) {
                        com.android.server.job.controllers.JobStatus next = it2.next();
                        if (predicate.test(next)) {
                            indentingPrintWriter.print("JOB ");
                            next.printUniqueId(indentingPrintWriter);
                            indentingPrintWriter.print(": ");
                            indentingPrintWriter.println(next.toShortStringExceptUniqueId());
                            indentingPrintWriter.increaseIndent();
                            next.dump(indentingPrintWriter, z2, millis2);
                            indentingPrintWriter.print("Restricted due to:");
                            boolean z3 = checkIfRestricted(next) != null ? z2 : false;
                            if (z3) {
                                int size2 = this.mJobRestrictions.size() - 1;
                                while (size2 >= 0) {
                                    java.util.Iterator<com.android.server.job.controllers.JobStatus> it3 = it2;
                                    com.android.server.job.restrictions.JobRestriction jobRestriction = this.mJobRestrictions.get(size2);
                                    if (!jobRestriction.isJobRestricted(next)) {
                                        j3 = millis;
                                    } else {
                                        int internalReason = jobRestriction.getInternalReason();
                                        j3 = millis;
                                        indentingPrintWriter.print(" ");
                                        indentingPrintWriter.print(android.app.job.JobParameters.getInternalReasonCodeDescription(internalReason));
                                    }
                                    size2--;
                                    it2 = it3;
                                    millis = j3;
                                }
                                it = it2;
                                j2 = millis;
                            } else {
                                it = it2;
                                j2 = millis;
                                indentingPrintWriter.print(" none");
                            }
                            indentingPrintWriter.println(".");
                            indentingPrintWriter.print("Ready: ");
                            indentingPrintWriter.print(isReadyToBeExecutedLocked(next));
                            indentingPrintWriter.print(" (job=");
                            indentingPrintWriter.print(next.isReady());
                            indentingPrintWriter.print(" user=");
                            indentingPrintWriter.print(areUsersStartedLocked(next));
                            indentingPrintWriter.print(" !restricted=");
                            indentingPrintWriter.print(!z3);
                            indentingPrintWriter.print(" !pending=");
                            indentingPrintWriter.print(!this.mPendingJobQueue.contains(next));
                            indentingPrintWriter.print(" !active=");
                            indentingPrintWriter.print(!this.mConcurrencyManager.isJobRunningLocked(next));
                            indentingPrintWriter.print(" !backingup=");
                            indentingPrintWriter.print(!this.mBackingUpUids.get(next.getSourceUid()));
                            indentingPrintWriter.print(" comp=");
                            indentingPrintWriter.print(isComponentUsable(next));
                            indentingPrintWriter.println(")");
                            indentingPrintWriter.decreaseIndent();
                            it2 = it;
                            millis = j2;
                            z = true;
                            z2 = true;
                        }
                    }
                    j = millis;
                }
                if (!z) {
                    indentingPrintWriter.println("None.");
                }
                indentingPrintWriter.decreaseIndent();
                for (int i2 = 0; i2 < this.mControllers.size(); i2++) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println(this.mControllers.get(i2).getClass().getSimpleName() + ":");
                    indentingPrintWriter.increaseIndent();
                    this.mControllers.get(i2).dumpControllerStateLocked(indentingPrintWriter, predicate);
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z4 = false;
                for (int i3 = 0; i3 < this.mUidProcStates.size(); i3++) {
                    int keyAt = this.mUidProcStates.keyAt(i3);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt)) {
                        if (!z4) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid proc states:");
                            indentingPrintWriter.increaseIndent();
                            z4 = true;
                        }
                        indentingPrintWriter.print(android.os.UserHandle.formatUid(keyAt));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(android.app.ActivityManager.procStateToString(this.mUidProcStates.valueAt(i3)));
                    }
                }
                if (z4) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z5 = false;
                for (int i4 = 0; i4 < this.mUidBiasOverride.size(); i4++) {
                    int keyAt2 = this.mUidBiasOverride.keyAt(i4);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt2)) {
                        if (!z5) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid bias overrides:");
                            indentingPrintWriter.increaseIndent();
                            z5 = true;
                        }
                        indentingPrintWriter.print(android.os.UserHandle.formatUid(keyAt2));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mUidBiasOverride.valueAt(i4));
                    }
                }
                if (z5) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z6 = false;
                for (int i5 = 0; i5 < this.mUidCapabilities.size(); i5++) {
                    int keyAt3 = this.mUidCapabilities.keyAt(i5);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt3)) {
                        if (!z6) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Uid capabilities:");
                            indentingPrintWriter.increaseIndent();
                            z6 = true;
                        }
                        indentingPrintWriter.print(android.os.UserHandle.formatUid(keyAt3));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(android.app.ActivityManager.getCapabilitiesSummary(this.mUidCapabilities.valueAt(i5)));
                    }
                }
                if (z6) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z7 = false;
                for (int i6 = 0; i6 < this.mUidToPackageCache.size(); i6++) {
                    int keyAt4 = this.mUidToPackageCache.keyAt(i6);
                    if (i == -1 || i == keyAt4) {
                        if (!z7) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Cached UID->package map:");
                            indentingPrintWriter.increaseIndent();
                            z7 = true;
                        }
                        indentingPrintWriter.print(keyAt4);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mUidToPackageCache.get(keyAt4));
                    }
                }
                if (z7) {
                    indentingPrintWriter.decreaseIndent();
                }
                boolean z8 = false;
                for (int i7 = 0; i7 < this.mBackingUpUids.size(); i7++) {
                    int keyAt5 = this.mBackingUpUids.keyAt(i7);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt5)) {
                        if (!z8) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Backing up uids:");
                            indentingPrintWriter.increaseIndent();
                            z8 = true;
                        } else {
                            indentingPrintWriter.print(", ");
                        }
                        indentingPrintWriter.print(android.os.UserHandle.formatUid(keyAt5));
                    }
                }
                if (z8) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println();
                this.mJobPackageTracker.dump(indentingPrintWriter, appId);
                indentingPrintWriter.println();
                if (this.mJobPackageTracker.dumpHistory(indentingPrintWriter, appId)) {
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Pending queue:");
                indentingPrintWriter.increaseIndent();
                this.mPendingJobQueue.resetIterator();
                boolean z9 = false;
                int i8 = 0;
                while (true) {
                    com.android.server.job.controllers.JobStatus next2 = this.mPendingJobQueue.next();
                    if (next2 == null) {
                        break;
                    }
                    i8++;
                    if (predicate.test(next2)) {
                        if (!z9) {
                            z9 = true;
                        }
                        indentingPrintWriter.print("Pending #");
                        indentingPrintWriter.print(i8);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(next2.toShortString());
                        indentingPrintWriter.increaseIndent();
                        next2.dump(indentingPrintWriter, false, millis2);
                        int evaluateJobBiasLocked = evaluateJobBiasLocked(next2);
                        indentingPrintWriter.print("Evaluated bias: ");
                        indentingPrintWriter.println(android.app.job.JobInfo.getBiasString(evaluateJobBiasLocked));
                        indentingPrintWriter.print("Enq: ");
                        android.util.TimeUtils.formatDuration(next2.madePending - millis3, indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    }
                }
                if (!z9) {
                    indentingPrintWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                this.mConcurrencyManager.dumpContextInfoLocked(indentingPrintWriter, predicate, millis2, millis3);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Recently completed jobs:");
                indentingPrintWriter.increaseIndent();
                boolean z10 = false;
                for (int i9 = 1; i9 <= 20; i9++) {
                    int i10 = ((this.mLastCompletedJobIndex + 20) - i9) % 20;
                    com.android.server.job.controllers.JobStatus jobStatus = this.mLastCompletedJobs[i10];
                    if (jobStatus != null && predicate.test(jobStatus)) {
                        android.util.TimeUtils.formatDuration(this.mLastCompletedJobTimeElapsed[i10], millis2, indentingPrintWriter);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println(jobStatus.toShortString());
                        jobStatus.dump(indentingPrintWriter, true, millis2);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                        z10 = true;
                    }
                }
                if (!z10) {
                    indentingPrintWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                boolean z11 = false;
                for (int i11 = 1; i11 <= this.mLastCancelledJobs.length; i11++) {
                    int length = ((this.mLastCancelledJobIndex + this.mLastCancelledJobs.length) - i11) % this.mLastCancelledJobs.length;
                    com.android.server.job.controllers.JobStatus jobStatus2 = this.mLastCancelledJobs[length];
                    if (jobStatus2 != null && predicate.test(jobStatus2)) {
                        if (!z11) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.println("Recently cancelled jobs:");
                            indentingPrintWriter.increaseIndent();
                            z11 = true;
                        }
                        android.util.TimeUtils.formatDuration(this.mLastCancelledJobTimeElapsed[length], millis2, indentingPrintWriter);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println(jobStatus2.toShortString());
                        jobStatus2.dump(indentingPrintWriter, true, millis2);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                if (!z11) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (i == -1) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("mReadyToRock=");
                    indentingPrintWriter.println(this.mReadyToRock);
                    indentingPrintWriter.print("mReportedActive=");
                    indentingPrintWriter.println(this.mReportedActive);
                }
                indentingPrintWriter.println();
                this.mConcurrencyManager.dumpLocked(indentingPrintWriter, j, millis2);
                indentingPrintWriter.println();
                indentingPrintWriter.print("PersistStats: ");
                indentingPrintWriter.println(this.mJobs.getPersistStats());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dumpInternal$9(int i, com.android.server.job.controllers.JobStatus jobStatus) {
        return i == -1 || android.os.UserHandle.getAppId(jobStatus.getUid()) == i || android.os.UserHandle.getAppId(jobStatus.getSourceUid()) == i;
    }

    void dumpInternalProto(java.io.FileDescriptor fileDescriptor, int i) {
        java.lang.Object obj;
        java.lang.Object obj2;
        long j;
        java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate;
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        final int appId = android.os.UserHandle.getAppId(i);
        long millis = sSystemClock.millis();
        long millis2 = sElapsedRealtimeClock.millis();
        long millis3 = sUptimeMillisClock.millis();
        java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate2 = new java.util.function.Predicate() { // from class: com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj3) {
                boolean lambda$dumpInternalProto$10;
                lambda$dumpInternalProto$10 = com.android.server.job.JobSchedulerService.lambda$dumpInternalProto$10(appId, (com.android.server.job.controllers.JobStatus) obj3);
                return lambda$dumpInternalProto$10;
            }
        };
        java.lang.Object obj3 = this.mLock;
        synchronized (obj3) {
            try {
                long start = protoOutputStream.start(1146756268033L);
                this.mConstants.dump(protoOutputStream);
                java.util.Iterator<com.android.server.job.controllers.StateController> it = this.mControllers.iterator();
                while (it.hasNext()) {
                    it.next().dumpConstants(protoOutputStream);
                }
                protoOutputStream.end(start);
                for (int size = this.mJobRestrictions.size() - 1; size >= 0; size--) {
                    this.mJobRestrictions.get(size).dumpConstants(protoOutputStream);
                }
                int[] iArr = this.mStartedUsers;
                int length = iArr.length;
                int i2 = 0;
                while (i2 < length) {
                    protoOutputStream.write(2220498092034L, iArr[i2]);
                    i2++;
                    length = length;
                    iArr = iArr;
                }
                this.mQuotaTracker.dump(protoOutputStream, 1146756268054L);
                if (this.mJobs.size() > 0) {
                    java.util.List<com.android.server.job.controllers.JobStatus> allJobs = this.mJobs.mJobSet.getAllJobs();
                    sortJobs(allJobs);
                    for (com.android.server.job.controllers.JobStatus jobStatus : allJobs) {
                        long start2 = protoOutputStream.start(2246267895811L);
                        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                        if (predicate2.test(jobStatus)) {
                            long j2 = millis;
                            java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate3 = predicate2;
                            obj = obj3;
                            try {
                                jobStatus.dump(protoOutputStream, 1146756268034L, true, millis2);
                                protoOutputStream.write(1133871366154L, isReadyToBeExecutedLocked(jobStatus));
                                protoOutputStream.write(1133871366147L, jobStatus.isReady());
                                protoOutputStream.write(1133871366148L, areUsersStartedLocked(jobStatus));
                                protoOutputStream.write(1133871366155L, checkIfRestricted(jobStatus) != null);
                                protoOutputStream.write(1133871366149L, this.mPendingJobQueue.contains(jobStatus));
                                protoOutputStream.write(1133871366150L, this.mConcurrencyManager.isJobRunningLocked(jobStatus));
                                protoOutputStream.write(1133871366151L, this.mBackingUpUids.get(jobStatus.getSourceUid()));
                                protoOutputStream.write(1133871366152L, isComponentUsable(jobStatus));
                                for (com.android.server.job.restrictions.JobRestriction jobRestriction : this.mJobRestrictions) {
                                    long start3 = protoOutputStream.start(2246267895820L);
                                    protoOutputStream.write(1159641169921L, jobRestriction.getInternalReason());
                                    protoOutputStream.write(1133871366146L, jobRestriction.isJobRestricted(jobStatus));
                                    protoOutputStream.end(start3);
                                }
                                protoOutputStream.end(start2);
                                predicate2 = predicate3;
                                obj3 = obj;
                                millis = j2;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                    }
                    obj2 = obj3;
                    j = millis;
                    predicate = predicate2;
                } else {
                    obj2 = obj3;
                    j = millis;
                    predicate = predicate2;
                }
                java.util.Iterator<com.android.server.job.controllers.StateController> it2 = this.mControllers.iterator();
                while (it2.hasNext()) {
                    it2.next().dumpControllerStateLocked(protoOutputStream, 2246267895812L, predicate);
                }
                for (int i3 = 0; i3 < this.mUidBiasOverride.size(); i3++) {
                    int keyAt = this.mUidBiasOverride.keyAt(i3);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt)) {
                        long start4 = protoOutputStream.start(2246267895813L);
                        protoOutputStream.write(1120986464257L, keyAt);
                        protoOutputStream.write(1172526071810L, this.mUidBiasOverride.valueAt(i3));
                        protoOutputStream.end(start4);
                    }
                }
                for (int i4 = 0; i4 < this.mBackingUpUids.size(); i4++) {
                    int keyAt2 = this.mBackingUpUids.keyAt(i4);
                    if (appId == -1 || appId == android.os.UserHandle.getAppId(keyAt2)) {
                        protoOutputStream.write(2220498092038L, keyAt2);
                    }
                }
                this.mJobPackageTracker.dump(protoOutputStream, 1146756268040L, appId);
                this.mJobPackageTracker.dumpHistory(protoOutputStream, 1146756268039L, appId);
                this.mPendingJobQueue.resetIterator();
                while (true) {
                    com.android.server.job.controllers.JobStatus next = this.mPendingJobQueue.next();
                    if (next == null) {
                        break;
                    }
                    long start5 = protoOutputStream.start(2246267895817L);
                    next.writeToShortProto(protoOutputStream, 1146756268033L);
                    next.dump(protoOutputStream, 1146756268034L, false, millis2);
                    protoOutputStream.write(1172526071811L, evaluateJobBiasLocked(next));
                    protoOutputStream.write(1112396529668L, millis3 - next.madePending);
                    protoOutputStream.end(start5);
                }
                if (i == -1) {
                    protoOutputStream.write(1133871366155L, this.mReadyToRock);
                    protoOutputStream.write(1133871366156L, this.mReportedActive);
                }
                this.mConcurrencyManager.dumpProtoLocked(protoOutputStream, 1146756268052L, j, millis2);
                this.mJobs.getPersistStats().dumpDebug(protoOutputStream, 1146756268053L);
                protoOutputStream.flush();
            } catch (java.lang.Throwable th2) {
                th = th2;
                obj = obj3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dumpInternalProto$10(int i, com.android.server.job.controllers.JobStatus jobStatus) {
        return i == -1 || android.os.UserHandle.getAppId(jobStatus.getUid()) == i || android.os.UserHandle.getAppId(jobStatus.getSourceUid()) == i;
    }
}
