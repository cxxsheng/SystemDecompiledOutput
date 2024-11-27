package com.android.server.job;

/* loaded from: classes2.dex */
class JobConcurrencyManager {
    private static final int ALL_WORK_TYPES = 127;
    static final java.lang.String CONFIG_KEY_PREFIX_CONCURRENCY = "concurrency_";
    private static final com.android.server.job.JobConcurrencyManager.WorkConfigLimitsPerMemoryTrimLevel CONFIG_LIMITS_SCREEN_OFF;
    private static final com.android.server.job.JobConcurrencyManager.WorkConfigLimitsPerMemoryTrimLevel CONFIG_LIMITS_SCREEN_ON;
    private static final boolean DEBUG = com.android.server.job.JobSchedulerService.DEBUG;
    static final int DEFAULT_CONCURRENCY_LIMIT;
    private static final boolean DEFAULT_ENABLE_MAX_WAIT_TIME_BYPASS = true;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_MAX_WAIT_EJ_MS = 300000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_MAX_WAIT_REGULAR_MS = 1800000;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_MAX_WAIT_UI_MS = 300000;
    private static final int DEFAULT_PKG_CONCURRENCY_LIMIT_EJ = 3;
    private static final int DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR;
    private static final long DEFAULT_SCREEN_OFF_ADJUSTMENT_DELAY_MS = 30000;
    private static final java.lang.String KEY_CONCURRENCY_LIMIT = "concurrency_limit";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_MAX_WAIT_TIME_BYPASS = "concurrency_enable_max_wait_time_bypass";
    private static final java.lang.String KEY_MAX_WAIT_EJ_MS = "concurrency_max_wait_ej_ms";
    private static final java.lang.String KEY_MAX_WAIT_REGULAR_MS = "concurrency_max_wait_regular_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_MAX_WAIT_UI_MS = "concurrency_max_wait_ui_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_PKG_CONCURRENCY_LIMIT_EJ = "concurrency_pkg_concurrency_limit_ej";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_PKG_CONCURRENCY_LIMIT_REGULAR = "concurrency_pkg_concurrency_limit_regular";
    private static final java.lang.String KEY_SCREEN_OFF_ADJUSTMENT_DELAY_MS = "concurrency_screen_off_adjustment_delay_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_CONCURRENCY_LIMIT = 64;
    private static final int MAX_RETAINED_OBJECTS = 96;

    @com.android.internal.annotations.VisibleForTesting
    static final int NUM_WORK_TYPES = 7;
    private static final int PRIVILEGED_STATE_BAL = 2;
    private static final int PRIVILEGED_STATE_NONE = 1;
    private static final int PRIVILEGED_STATE_TOP = 3;
    private static final int PRIVILEGED_STATE_UNDEFINED = 0;
    private static final int SYSTEM_STATE_REFRESH_MIN_INTERVAL = 1000;
    private static final java.lang.String TAG = "JobScheduler.Concurrency";
    static final int WORK_TYPE_BG = 16;
    static final int WORK_TYPE_BGUSER = 64;
    static final int WORK_TYPE_BGUSER_IMPORTANT = 32;
    static final int WORK_TYPE_EJ = 8;
    static final int WORK_TYPE_FGS = 2;
    static final int WORK_TYPE_NONE = 0;
    static final int WORK_TYPE_TOP = 1;
    static final int WORK_TYPE_UI = 4;
    private static final com.android.modules.expresslog.Histogram sConcurrencyHistogramLogger;
    private static final java.util.Comparator<com.android.server.job.JobConcurrencyManager.ContextAssignment> sDeterminationComparator;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.JobConcurrencyManager.PackageStats> mActivePkgStats;
    final java.util.List<com.android.server.job.JobServiceContext> mActiveServices;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.content.Context mContext;
    private final android.util.Pools.Pool<com.android.server.job.JobConcurrencyManager.ContextAssignment> mContextAssignmentPool;
    private boolean mCurrentInteractiveState;
    private boolean mEffectiveInteractiveState;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.JobConcurrencyManager.GracePeriodObserver mGracePeriodObserver;
    private final android.os.Handler mHandler;
    private final android.util.ArraySet<com.android.server.job.JobServiceContext> mIdleContexts;
    private final com.android.server.job.JobConcurrencyManager.Injector mInjector;
    private int mLastMemoryTrimLevel;
    private long mLastScreenOffRealtime;
    private long mLastScreenOnRealtime;
    private final java.lang.Object mLock;
    private long mMaxWaitEjMs;
    private long mMaxWaitRegularMs;
    private boolean mMaxWaitTimeBypassEnabled;
    private long mMaxWaitUIMs;
    private long mNextSystemStateRefreshTime;
    private final com.android.server.job.JobNotificationCoordinator mNotificationCoordinator;
    private int mNumDroppedContexts;
    private final java.util.function.Consumer<com.android.server.job.JobConcurrencyManager.PackageStats> mPackageStatsStagingCountClearer;
    private int mPkgConcurrencyLimitEj;
    private int mPkgConcurrencyLimitRegular;
    private final android.util.Pools.Pool<com.android.server.job.JobConcurrencyManager.PackageStats> mPkgStatsPool;
    private android.os.PowerManager mPowerManager;
    private final java.lang.Runnable mRampUpForScreenOff;
    private final android.content.BroadcastReceiver mReceiver;
    private final com.android.server.job.JobConcurrencyManager.AssignmentInfo mRecycledAssignmentInfo;
    private final android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> mRecycledChanged;
    private final android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> mRecycledIdle;
    private final java.util.ArrayList<com.android.server.job.JobConcurrencyManager.ContextAssignment> mRecycledPreferredUidOnly;
    private final android.util.SparseIntArray mRecycledPrivilegedState;
    private final java.util.ArrayList<com.android.server.job.JobConcurrencyManager.ContextAssignment> mRecycledStoppable;
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mRunningJobs;
    private long mScreenOffAdjustmentDelayMs;
    private final com.android.server.job.JobSchedulerService mService;

    @com.android.internal.annotations.VisibleForTesting
    boolean mShouldRestrictBgUser;
    private final com.android.internal.util.jobs.StatLogger mStatLogger;
    private int mSteadyStateConcurrencyLimit;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private final com.android.server.job.JobConcurrencyManager.WorkCountTracker mWorkCountTracker;
    private com.android.server.job.JobConcurrencyManager.WorkTypeConfig mWorkTypeConfig;

    interface Stats {
        public static final int ASSIGN_JOBS_TO_CONTEXTS = 0;
        public static final int COUNT = 2;
        public static final int REFRESH_SYSTEM_STATE = 1;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WorkType {
    }

    static {
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            DEFAULT_CONCURRENCY_LIMIT = 8;
        } else {
            long totalSize = new com.android.internal.util.MemInfoReader().getTotalSize();
            if (totalSize <= android.util.DataUnit.GIGABYTES.toBytes(6L)) {
                DEFAULT_CONCURRENCY_LIMIT = 16;
            } else if (totalSize <= android.util.DataUnit.GIGABYTES.toBytes(8L)) {
                DEFAULT_CONCURRENCY_LIMIT = 20;
            } else if (totalSize <= android.util.DataUnit.GIGABYTES.toBytes(12L)) {
                DEFAULT_CONCURRENCY_LIMIT = 32;
            } else {
                DEFAULT_CONCURRENCY_LIMIT = 40;
            }
        }
        DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR = DEFAULT_CONCURRENCY_LIMIT / 2;
        CONFIG_LIMITS_SCREEN_ON = new com.android.server.job.JobConcurrencyManager.WorkConfigLimitsPerMemoryTrimLevel(new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_on_normal", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 3) / 4, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.4f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(16, java.lang.Float.valueOf(0.05f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.5f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.25f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.2f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_on_moderate", DEFAULT_CONCURRENCY_LIMIT, DEFAULT_CONCURRENCY_LIMIT / 2, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.4f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(16, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.1f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.4f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.1f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_on_low", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 4) / 10, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.6f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.1f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.33333334f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.16666667f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.16666667f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_on_critical", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 4) / 10, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.7f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.16666667f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.16666667f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.16666667f)))));
        CONFIG_LIMITS_SCREEN_OFF = new com.android.server.job.JobConcurrencyManager.WorkConfigLimitsPerMemoryTrimLevel(new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_off_normal", DEFAULT_CONCURRENCY_LIMIT, DEFAULT_CONCURRENCY_LIMIT, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.3f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.15f)), android.util.Pair.create(16, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.6f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.2f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_off_moderate", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 9) / 10, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.3f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.2f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.15f)), android.util.Pair.create(16, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.5f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.1f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_off_low", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 6) / 10, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.3f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.15f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.15f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(16, java.lang.Float.valueOf(0.05f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.25f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.1f)))), new com.android.server.job.JobConcurrencyManager.WorkTypeConfig("screen_off_critical", DEFAULT_CONCURRENCY_LIMIT, (DEFAULT_CONCURRENCY_LIMIT * 4) / 10, java.util.List.of(android.util.Pair.create(1, java.lang.Float.valueOf(0.3f)), android.util.Pair.create(2, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(4, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(8, java.lang.Float.valueOf(0.05f))), java.util.List.of(android.util.Pair.create(16, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(32, java.lang.Float.valueOf(0.1f)), android.util.Pair.create(64, java.lang.Float.valueOf(0.1f)))));
        sDeterminationComparator = new java.util.Comparator() { // from class: com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$static$0;
                lambda$static$0 = com.android.server.job.JobConcurrencyManager.lambda$static$0((com.android.server.job.JobConcurrencyManager.ContextAssignment) obj, (com.android.server.job.JobConcurrencyManager.ContextAssignment) obj2);
                return lambda$static$0;
            }
        };
        sConcurrencyHistogramLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_job_concurrency", new com.android.modules.expresslog.Histogram.UniformOptions(100, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 99.0f));
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String workTypeToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "TOP";
            case 2:
                return "FGS";
            case 4:
                return "UI";
            case 8:
                return "EJ";
            case 16:
                return "BG";
            case 32:
                return "BGUSER_IMPORTANT";
            case 64:
                return "BGUSER";
            default:
                return "WORK(" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment, com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment2) {
        if (contextAssignment == contextAssignment2) {
            return 0;
        }
        com.android.server.job.controllers.JobStatus runningJobLocked = contextAssignment.context.getRunningJobLocked();
        com.android.server.job.controllers.JobStatus runningJobLocked2 = contextAssignment2.context.getRunningJobLocked();
        if (runningJobLocked == null) {
            if (runningJobLocked2 == null) {
                return 0;
            }
            return 1;
        }
        if (runningJobLocked2 == null) {
            return -1;
        }
        if (runningJobLocked.lastEvaluatedBias == 40) {
            if (runningJobLocked2.lastEvaluatedBias != 40) {
                return -1;
            }
        } else if (runningJobLocked2.lastEvaluatedBias == 40) {
            return 1;
        }
        return java.lang.Long.compare(contextAssignment2.context.getExecutionStartTimeElapsed(), contextAssignment.context.getExecutionStartTimeElapsed());
    }

    JobConcurrencyManager(com.android.server.job.JobSchedulerService jobSchedulerService) {
        this(jobSchedulerService, new com.android.server.job.JobConcurrencyManager.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    JobConcurrencyManager(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.JobConcurrencyManager.Injector injector) {
        this.mRecycledChanged = new android.util.ArraySet<>();
        this.mRecycledIdle = new android.util.ArraySet<>();
        this.mRecycledPreferredUidOnly = new java.util.ArrayList<>();
        this.mRecycledStoppable = new java.util.ArrayList<>();
        this.mRecycledAssignmentInfo = new com.android.server.job.JobConcurrencyManager.AssignmentInfo();
        this.mRecycledPrivilegedState = new android.util.SparseIntArray();
        this.mContextAssignmentPool = new android.util.Pools.SimplePool(96);
        this.mActiveServices = new java.util.ArrayList();
        this.mIdleContexts = new android.util.ArraySet<>();
        this.mNumDroppedContexts = 0;
        this.mRunningJobs = new android.util.ArraySet<>();
        this.mWorkCountTracker = new com.android.server.job.JobConcurrencyManager.WorkCountTracker();
        this.mPkgStatsPool = new android.util.Pools.SimplePool(96);
        this.mActivePkgStats = new android.util.SparseArrayMap<>();
        this.mWorkTypeConfig = CONFIG_LIMITS_SCREEN_OFF.normal;
        this.mScreenOffAdjustmentDelayMs = 30000L;
        this.mSteadyStateConcurrencyLimit = DEFAULT_CONCURRENCY_LIMIT;
        this.mPkgConcurrencyLimitEj = 3;
        this.mPkgConcurrencyLimitRegular = DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR;
        this.mMaxWaitTimeBypassEnabled = true;
        this.mMaxWaitUIMs = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        this.mMaxWaitEjMs = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        this.mMaxWaitRegularMs = 1800000L;
        this.mPackageStatsStagingCountClearer = new java.util.function.Consumer() { // from class: com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.job.JobConcurrencyManager.PackageStats) obj).resetStagedCount();
            }
        };
        this.mStatLogger = new com.android.internal.util.jobs.StatLogger(new java.lang.String[]{"assignJobsToContexts", "refreshSystemState"});
        this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.JobConcurrencyManager.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1454123155:
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 870701415:
                        if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1779291251:
                        if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                            c = 3;
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
                        com.android.server.job.JobConcurrencyManager.this.onInteractiveStateChanged(true);
                        return;
                    case 1:
                        com.android.server.job.JobConcurrencyManager.this.onInteractiveStateChanged(false);
                        return;
                    case 2:
                        if (com.android.server.job.JobConcurrencyManager.this.mPowerManager != null && com.android.server.job.JobConcurrencyManager.this.mPowerManager.isDeviceIdleMode()) {
                            synchronized (com.android.server.job.JobConcurrencyManager.this.mLock) {
                                com.android.server.job.JobConcurrencyManager.this.stopUnexemptedJobsForDoze();
                                com.android.server.job.JobConcurrencyManager.this.stopOvertimeJobsLocked("deep doze");
                            }
                            return;
                        }
                        return;
                    case 3:
                        if (com.android.server.job.JobConcurrencyManager.this.mPowerManager != null && com.android.server.job.JobConcurrencyManager.this.mPowerManager.isPowerSaveMode()) {
                            synchronized (com.android.server.job.JobConcurrencyManager.this.mLock) {
                                com.android.server.job.JobConcurrencyManager.this.stopOvertimeJobsLocked("battery saver");
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mRampUpForScreenOff = new java.lang.Runnable() { // from class: com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.job.JobConcurrencyManager.this.rampUpForScreenOff();
            }
        };
        this.mService = jobSchedulerService;
        this.mLock = this.mService.getLock();
        this.mContext = jobSchedulerService.getTestableContext();
        this.mInjector = injector;
        this.mNotificationCoordinator = new com.android.server.job.JobNotificationCoordinator();
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mHandler = com.android.server.AppSchedulingModuleThread.getHandler();
        this.mGracePeriodObserver = new com.android.server.job.JobConcurrencyManager.GracePeriodObserver(this.mContext);
        this.mShouldRestrictBgUser = this.mContext.getResources().getBoolean(android.R.bool.config_isMainUserPermanentAdmin);
    }

    public void onSystemReady() {
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        try {
            android.app.ActivityManager.getService().registerUserSwitchObserver(this.mGracePeriodObserver, TAG);
        } catch (android.os.RemoteException e) {
        }
        onInteractiveStateChanged(this.mPowerManager.isInteractive());
    }

    void onThirdPartyAppsCanStart() {
        com.android.internal.app.IBatteryStats asInterface = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        for (int i = 0; i < this.mSteadyStateConcurrencyLimit; i++) {
            this.mIdleContexts.add(this.mInjector.createJobServiceContext(this.mService, this, this.mNotificationCoordinator, asInterface, this.mService.mJobPackageTracker, com.android.server.AppSchedulingModuleThread.get().getLooper()));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppRemovedLocked(java.lang.String str, int i) {
        com.android.server.job.JobConcurrencyManager.PackageStats packageStats = (com.android.server.job.JobConcurrencyManager.PackageStats) this.mActivePkgStats.get(android.os.UserHandle.getUserId(i), str);
        if (packageStats != null) {
            if (packageStats.numRunningEj > 0 || packageStats.numRunningRegular > 0) {
                android.util.Slog.w(TAG, str + "(" + i + ") marked as removed before jobs stopped running");
                return;
            }
            this.mActivePkgStats.delete(android.os.UserHandle.getUserId(i), str);
        }
    }

    void onUserRemoved(int i) {
        this.mGracePeriodObserver.onUserRemoved(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInteractiveStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentInteractiveState == z) {
                    return;
                }
                this.mCurrentInteractiveState = z;
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Interactive: " + z);
                }
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                if (z) {
                    this.mLastScreenOnRealtime = millis;
                    this.mEffectiveInteractiveState = true;
                    this.mHandler.removeCallbacks(this.mRampUpForScreenOff);
                } else {
                    this.mLastScreenOffRealtime = millis;
                    this.mHandler.postDelayed(this.mRampUpForScreenOff, this.mScreenOffAdjustmentDelayMs);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rampUpForScreenOff() {
        synchronized (this.mLock) {
            try {
                if (this.mEffectiveInteractiveState) {
                    if (this.mLastScreenOnRealtime > this.mLastScreenOffRealtime) {
                        return;
                    }
                    if (this.mLastScreenOffRealtime + this.mScreenOffAdjustmentDelayMs > com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis()) {
                        return;
                    }
                    this.mEffectiveInteractiveState = false;
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Ramping up concurrency");
                    }
                    this.mService.maybeRunPendingJobsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.util.ArraySet<com.android.server.job.controllers.JobStatus> getRunningJobsLocked() {
        return this.mRunningJobs;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isJobRunningLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mRunningJobs.contains(jobStatus);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isJobInOvertimeLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mRunningJobs.contains(jobStatus)) {
            return false;
        }
        for (int size = this.mActiveServices.size() - 1; size >= 0; size--) {
            if (this.mActiveServices.get(size).getRunningJobLocked() == jobStatus) {
                return !r2.isWithinExecutionGuaranteeTime();
            }
        }
        android.util.Slog.wtf(TAG, "Couldn't find long running job on a context");
        this.mRunningJobs.remove(jobStatus);
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isSimilarJobRunningLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        for (int size = this.mRunningJobs.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = this.mRunningJobs.valueAt(size);
            if (jobStatus.matches(valueAt.getUid(), valueAt.getNamespace(), valueAt.getJobId())) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean refreshSystemStateLocked() {
        long millis = com.android.server.job.JobSchedulerService.sUptimeMillisClock.millis();
        if (millis < this.mNextSystemStateRefreshTime) {
            return false;
        }
        long time = this.mStatLogger.getTime();
        this.mNextSystemStateRefreshTime = millis + 1000;
        this.mLastMemoryTrimLevel = 0;
        try {
            this.mLastMemoryTrimLevel = android.app.ActivityManager.getService().getMemoryTrimLevel();
        } catch (android.os.RemoteException e) {
        }
        this.mStatLogger.logDurationStat(1, time);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateCounterConfigLocked() {
        if (!refreshSystemStateLocked()) {
            return;
        }
        com.android.server.job.JobConcurrencyManager.WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel = this.mEffectiveInteractiveState ? CONFIG_LIMITS_SCREEN_ON : CONFIG_LIMITS_SCREEN_OFF;
        switch (this.mLastMemoryTrimLevel) {
            case 1:
                this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.moderate;
                break;
            case 2:
                this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.low;
                break;
            case 3:
                this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.critical;
                break;
            default:
                this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.normal;
                break;
        }
        this.mWorkCountTracker.setConfig(this.mWorkTypeConfig);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void assignJobsToContextsLocked() {
        long time = this.mStatLogger.getTime();
        assignJobsToContextsInternalLocked();
        this.mStatLogger.logDurationStat(0, time);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assignJobsToContextsInternalLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, printPendingQueueLocked());
        }
        if (this.mService.getPendingJobQueue().size() == 0) {
            return;
        }
        prepareForAssignmentDeterminationLocked(this.mRecycledIdle, this.mRecycledPreferredUidOnly, this.mRecycledStoppable, this.mRecycledAssignmentInfo);
        if (DEBUG) {
            android.util.Slog.d(TAG, printAssignments("running jobs initial", this.mRecycledStoppable, this.mRecycledPreferredUidOnly));
        }
        determineAssignmentsLocked(this.mRecycledChanged, this.mRecycledIdle, this.mRecycledPreferredUidOnly, this.mRecycledStoppable, this.mRecycledAssignmentInfo);
        if (DEBUG) {
            android.util.Slog.d(TAG, printAssignments("running jobs final", this.mRecycledStoppable, this.mRecycledPreferredUidOnly, this.mRecycledChanged));
            android.util.Slog.d(TAG, "work count results: " + this.mWorkCountTracker);
        }
        carryOutAssignmentChangesLocked(this.mRecycledChanged);
        cleanUpAfterAssignmentChangesLocked(this.mRecycledChanged, this.mRecycledIdle, this.mRecycledPreferredUidOnly, this.mRecycledStoppable, this.mRecycledAssignmentInfo, this.mRecycledPrivilegedState);
        noteConcurrency(true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void prepareForAssignmentDeterminationLocked(android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list2, com.android.server.job.JobConcurrencyManager.AssignmentInfo assignmentInfo) {
        com.android.server.job.JobServiceContext createNewJobServiceContext;
        int i;
        com.android.server.job.PendingJobQueue pendingJobQueue = this.mService.getPendingJobQueue();
        java.util.List<com.android.server.job.JobServiceContext> list3 = this.mActiveServices;
        updateCounterConfigLocked();
        this.mWorkCountTracker.resetCounts();
        int i2 = 1;
        updateNonRunningPrioritiesLocked(pendingJobQueue, true);
        int size = list3.size();
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        int i3 = 0;
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (i3 < size) {
            com.android.server.job.JobServiceContext jobServiceContext = list3.get(i3);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment = (com.android.server.job.JobConcurrencyManager.ContextAssignment) this.mContextAssignmentPool.acquire();
            if (contextAssignment == null) {
                contextAssignment = new com.android.server.job.JobConcurrencyManager.ContextAssignment();
            }
            contextAssignment.context = jobServiceContext;
            if (runningJobLocked == null) {
                i = i2;
            } else {
                this.mWorkCountTracker.incrementRunningJobCount(jobServiceContext.getRunningJobWorkType());
                contextAssignment.workType = jobServiceContext.getRunningJobWorkType();
                if (!runningJobLocked.startedWithImmediacyPrivilege) {
                    i = 1;
                } else {
                    i = 1;
                    assignmentInfo.numRunningImmediacyPrivileged++;
                }
                if (runningJobLocked.shouldTreatAsUserInitiatedJob()) {
                    assignmentInfo.numRunningUi += i;
                } else if (runningJobLocked.startedAsExpeditedJob) {
                    assignmentInfo.numRunningEj += i;
                } else {
                    assignmentInfo.numRunningReg += i;
                }
            }
            contextAssignment.preferredUid = jobServiceContext.getPreferredUid();
            java.lang.String shouldStopRunningJobLocked = shouldStopRunningJobLocked(jobServiceContext);
            contextAssignment.shouldStopJobReason = shouldStopRunningJobLocked;
            if (shouldStopRunningJobLocked != null) {
                list2.add(contextAssignment);
            } else {
                contextAssignment.timeUntilStoppableMs = jobServiceContext.getRemainingGuaranteedTimeMs(millis);
                j = java.lang.Math.min(j, contextAssignment.timeUntilStoppableMs);
                list.add(contextAssignment);
            }
            i3++;
            i2 = i;
        }
        list.sort(sDeterminationComparator);
        list2.sort(sDeterminationComparator);
        while (size < this.mSteadyStateConcurrencyLimit) {
            int size2 = this.mIdleContexts.size();
            if (size2 > 0) {
                createNewJobServiceContext = this.mIdleContexts.removeAt(size2 - 1);
            } else {
                android.util.Slog.w(TAG, "Had fewer than " + this.mSteadyStateConcurrencyLimit + " in existence");
                createNewJobServiceContext = createNewJobServiceContext();
            }
            com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment2 = (com.android.server.job.JobConcurrencyManager.ContextAssignment) this.mContextAssignmentPool.acquire();
            if (contextAssignment2 == null) {
                contextAssignment2 = new com.android.server.job.JobConcurrencyManager.ContextAssignment();
            }
            contextAssignment2.context = createNewJobServiceContext;
            arraySet.add(contextAssignment2);
            size++;
        }
        this.mWorkCountTracker.onCountDone();
        if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            j = 0;
        }
        assignmentInfo.minPreferredUidOnlyWaitingTimeMs = j;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x03c5  */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void determineAssignmentsLocked(android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet, android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet2, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list2, @android.annotation.NonNull com.android.server.job.JobConcurrencyManager.AssignmentInfo assignmentInfo) {
        boolean z;
        com.android.server.job.PendingJobQueue pendingJobQueue;
        int i;
        java.lang.String str;
        boolean z2;
        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment;
        boolean z3;
        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment2;
        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment3;
        boolean z4;
        long j;
        android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet3;
        int i2;
        boolean z5;
        com.android.server.job.JobServiceContext createNewJobServiceContext;
        long j2;
        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment4;
        com.android.server.job.JobServiceContext createNewJobServiceContext2;
        int i3;
        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment5;
        boolean z6;
        int canJobStart;
        android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet4 = arraySet2;
        java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list3 = list2;
        com.android.server.job.JobConcurrencyManager.AssignmentInfo assignmentInfo2 = assignmentInfo;
        com.android.server.job.PendingJobQueue pendingJobQueue2 = this.mService.getPendingJobQueue();
        java.util.List<com.android.server.job.JobServiceContext> list4 = this.mActiveServices;
        pendingJobQueue2.resetIterator();
        int size = list4.size();
        boolean z7 = assignmentInfo2.numRunningUi == 0;
        boolean z8 = assignmentInfo2.numRunningEj == 0;
        boolean z9 = assignmentInfo2.numRunningReg == 0;
        long j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (true) {
            com.android.server.job.controllers.JobStatus next = pendingJobQueue2.next();
            if (next != null) {
                if (this.mRunningJobs.contains(next)) {
                    android.util.Slog.wtf(TAG, "Pending queue contained a running job");
                    if (!DEBUG) {
                        z = z9;
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        z = z9;
                        sb.append("Pending+running job: ");
                        sb.append(next);
                        android.util.Slog.e(TAG, sb.toString());
                    }
                    pendingJobQueue2.remove(next);
                    z9 = z;
                } else {
                    boolean z10 = z9;
                    boolean hasImmediacyPrivilegeLocked = hasImmediacyPrivilegeLocked(next, this.mRecycledPrivilegedState);
                    if (!DEBUG || !isSimilarJobRunningLocked(next)) {
                        pendingJobQueue = pendingJobQueue2;
                    } else {
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        pendingJobQueue = pendingJobQueue2;
                        sb2.append("Already running similar job to: ");
                        sb2.append(next);
                        android.util.Slog.w(TAG, sb2.toString());
                    }
                    boolean z11 = z8;
                    long min = java.lang.Math.min(assignmentInfo2.minPreferredUidOnlyWaitingTimeMs, j3);
                    long j4 = j3;
                    int jobWorkTypes = getJobWorkTypes(next);
                    boolean z12 = !isPkgConcurrencyLimitedLocked(next);
                    boolean z13 = size > this.mSteadyStateConcurrencyLimit;
                    boolean z14 = z7;
                    if (arraySet2.size() <= 0) {
                        i = size;
                        str = TAG;
                    } else {
                        int size2 = arraySet2.size() - 1;
                        com.android.server.job.JobConcurrencyManager.ContextAssignment valueAt = arraySet4.valueAt(size2);
                        str = TAG;
                        contextAssignment = valueAt;
                        i = size;
                        boolean z15 = contextAssignment.preferredUid == next.getUid() || contextAssignment.preferredUid == -1;
                        int canJobStart2 = this.mWorkCountTracker.canJobStart(jobWorkTypes);
                        if (z15 && z12 && canJobStart2 != 0) {
                            arraySet4.removeAt(size2);
                            contextAssignment.newJob = next;
                            contextAssignment.newWorkType = canJobStart2;
                            z2 = true;
                            if (contextAssignment == null || list2.size() <= 0) {
                                z3 = z2;
                                contextAssignment2 = contextAssignment;
                            } else {
                                int size3 = list2.size() - 1;
                                while (size3 >= 0) {
                                    com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment6 = list3.get(size3);
                                    com.android.server.job.controllers.JobStatus runningJobLocked = contextAssignment6.context.getRunningJobLocked();
                                    if (hasImmediacyPrivilegeLocked || z13) {
                                        contextAssignment5 = contextAssignment;
                                        z6 = hasImmediacyPrivilegeLocked;
                                    } else {
                                        contextAssignment5 = contextAssignment;
                                        z6 = runningJobLocked.lastEvaluatedBias < 40 || this.mService.evaluateJobBiasLocked(runningJobLocked) < 40 || assignmentInfo2.numRunningImmediacyPrivileged > this.mWorkTypeConfig.getMaxTotal() / 2;
                                    }
                                    if (z6 || !this.mMaxWaitTimeBypassEnabled) {
                                        z3 = z2;
                                    } else if (next.shouldTreatAsUserInitiatedJob()) {
                                        z3 = z2;
                                        z6 = min >= this.mMaxWaitUIMs;
                                    } else {
                                        z3 = z2;
                                        if (next.shouldTreatAsExpeditedJob()) {
                                            z6 = min >= this.mMaxWaitEjMs;
                                        } else {
                                            z6 = min >= this.mMaxWaitRegularMs;
                                        }
                                    }
                                    if (!z6 || (canJobStart = this.mWorkCountTracker.canJobStart(jobWorkTypes, contextAssignment6.context.getRunningJobWorkType())) == 0) {
                                        size3--;
                                        z2 = z3;
                                        contextAssignment = contextAssignment5;
                                    } else {
                                        contextAssignment6.preemptReason = contextAssignment6.shouldStopJobReason;
                                        contextAssignment6.preemptReasonCode = 4;
                                        list3.remove(size3);
                                        contextAssignment6.newJob = next;
                                        contextAssignment6.newWorkType = canJobStart;
                                        contextAssignment3 = contextAssignment6;
                                        break;
                                    }
                                }
                                z3 = z2;
                                contextAssignment2 = contextAssignment;
                            }
                            contextAssignment3 = contextAssignment2;
                            if (contextAssignment3 == null) {
                                z4 = z3;
                                j = min;
                            } else if (!z13 || hasImmediacyPrivilegeLocked) {
                                int size4 = list.size() - 1;
                                int i4 = Integer.MAX_VALUE;
                                boolean z16 = z3;
                                long j5 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                                while (size4 >= 0) {
                                    com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment7 = list.get(size4);
                                    com.android.server.job.controllers.JobStatus runningJobLocked2 = contextAssignment7.context.getRunningJobLocked();
                                    boolean z17 = z16;
                                    long j6 = min;
                                    if (runningJobLocked2.getUid() != next.getUid() || (i3 = this.mService.evaluateJobBiasLocked(runningJobLocked2)) >= next.lastEvaluatedBias) {
                                        i3 = i4;
                                    } else if (contextAssignment3 == null || i4 > i3) {
                                        if (contextAssignment3 != null) {
                                            j5 = java.lang.Math.min(j5, contextAssignment3.timeUntilStoppableMs);
                                        }
                                        contextAssignment7.preemptReason = "higher bias job found";
                                        contextAssignment7.preemptReasonCode = 2;
                                        contextAssignment3 = contextAssignment7;
                                    } else {
                                        j5 = java.lang.Math.min(j5, contextAssignment7.timeUntilStoppableMs);
                                        i3 = i4;
                                    }
                                    size4--;
                                    i4 = i3;
                                    z16 = z17;
                                    min = j6;
                                }
                                z4 = z16;
                                j = min;
                                if (contextAssignment3 != null) {
                                    contextAssignment3.newJob = next;
                                    list.remove(contextAssignment3);
                                    assignmentInfo2.minPreferredUidOnlyWaitingTimeMs = j5;
                                }
                            } else {
                                z4 = z3;
                                j = min;
                            }
                            if (!hasImmediacyPrivilegeLocked) {
                                if (contextAssignment3 == null) {
                                    arraySet3 = arraySet;
                                } else if (contextAssignment3.context.getRunningJobLocked() == null) {
                                    arraySet3 = arraySet;
                                } else {
                                    arraySet3 = arraySet;
                                    arraySet3.add(contextAssignment3);
                                    i2 = i - 1;
                                    contextAssignment3.newJob = null;
                                    contextAssignment3.newWorkType = 0;
                                    contextAssignment4 = null;
                                    if (contextAssignment4 == null) {
                                        contextAssignment3 = contextAssignment4;
                                        z9 = z10;
                                        z8 = z11;
                                        z7 = z14;
                                    } else {
                                        if (DEBUG) {
                                            android.util.Slog.d(str, "Allowing additional context because EJ would wait too long");
                                        }
                                        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment8 = (com.android.server.job.JobConcurrencyManager.ContextAssignment) this.mContextAssignmentPool.acquire();
                                        if (contextAssignment8 != null) {
                                            contextAssignment3 = contextAssignment8;
                                        } else {
                                            contextAssignment3 = new com.android.server.job.JobConcurrencyManager.ContextAssignment();
                                        }
                                        if (this.mIdleContexts.size() > 0) {
                                            createNewJobServiceContext2 = this.mIdleContexts.removeAt(this.mIdleContexts.size() - 1);
                                        } else {
                                            createNewJobServiceContext2 = createNewJobServiceContext();
                                        }
                                        contextAssignment3.context = createNewJobServiceContext2;
                                        contextAssignment3.newJob = next;
                                        int canJobStart3 = this.mWorkCountTracker.canJobStart(jobWorkTypes);
                                        if (canJobStart3 == 0) {
                                            canJobStart3 = 1;
                                        }
                                        contextAssignment3.newWorkType = canJobStart3;
                                        z9 = z10;
                                        z8 = z11;
                                        z7 = z14;
                                    }
                                }
                                contextAssignment4 = contextAssignment3;
                                i2 = i;
                                if (contextAssignment4 == null) {
                                }
                            } else {
                                arraySet3 = arraySet;
                                java.lang.String str2 = str;
                                if (contextAssignment3 == null && this.mMaxWaitTimeBypassEnabled) {
                                    if (next.shouldTreatAsUserInitiatedJob() && z14) {
                                        z5 = j >= this.mMaxWaitUIMs;
                                        z7 = !z5;
                                        z9 = z10;
                                        z8 = z11;
                                    } else if (next.shouldTreatAsExpeditedJob() && z11) {
                                        z5 = j >= this.mMaxWaitEjMs;
                                        z8 = !z5;
                                        z9 = z10;
                                        z7 = z14;
                                    } else if (z10) {
                                        z5 = j >= this.mMaxWaitRegularMs;
                                        z9 = !z5;
                                        z8 = z11;
                                        z7 = z14;
                                    } else {
                                        z5 = false;
                                        z9 = z10;
                                        z8 = z11;
                                        z7 = z14;
                                    }
                                    if (!z5) {
                                        i2 = i;
                                    } else {
                                        if (DEBUG) {
                                            android.util.Slog.d(str2, "Allowing additional context because job would wait too long");
                                        }
                                        com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment9 = (com.android.server.job.JobConcurrencyManager.ContextAssignment) this.mContextAssignmentPool.acquire();
                                        if (contextAssignment9 != null) {
                                            contextAssignment3 = contextAssignment9;
                                        } else {
                                            contextAssignment3 = new com.android.server.job.JobConcurrencyManager.ContextAssignment();
                                        }
                                        if (this.mIdleContexts.size() > 0) {
                                            createNewJobServiceContext = this.mIdleContexts.removeAt(this.mIdleContexts.size() - 1);
                                        } else {
                                            createNewJobServiceContext = createNewJobServiceContext();
                                        }
                                        contextAssignment3.context = createNewJobServiceContext;
                                        contextAssignment3.newJob = next;
                                        int canJobStart4 = this.mWorkCountTracker.canJobStart(jobWorkTypes);
                                        if (canJobStart4 != 0) {
                                            contextAssignment3.newWorkType = canJobStart4;
                                        } else {
                                            int i5 = 1;
                                            while (true) {
                                                if (i5 > 127) {
                                                    break;
                                                }
                                                if ((i5 & jobWorkTypes) == 0) {
                                                    i5 <<= 1;
                                                } else {
                                                    contextAssignment3.newWorkType = i5;
                                                    break;
                                                }
                                            }
                                        }
                                        i2 = i;
                                    }
                                } else {
                                    z9 = z10;
                                    z8 = z11;
                                    i2 = i;
                                    z7 = z14;
                                }
                            }
                            com.android.server.job.JobConcurrencyManager.PackageStats pkgStatsLocked = getPkgStatsLocked(next.getSourceUserId(), next.getSourcePackageName());
                            if (contextAssignment3 != null) {
                                j2 = j4;
                                size = i2;
                            } else {
                                arraySet3.add(contextAssignment3);
                                if (contextAssignment3.context.getRunningJobLocked() != null) {
                                    i2--;
                                }
                                if (contextAssignment3.newJob == null) {
                                    size = i2;
                                    j2 = j4;
                                } else {
                                    contextAssignment3.newJob.startedWithImmediacyPrivilege = hasImmediacyPrivilegeLocked;
                                    j2 = java.lang.Math.min(j4, this.mService.getMinJobExecutionGuaranteeMs(contextAssignment3.newJob));
                                    size = i2 + 1;
                                }
                                pkgStatsLocked.adjustStagedCount(true, next.shouldTreatAsExpeditedJob());
                            }
                            if (z4) {
                                this.mWorkCountTracker.stageJob(contextAssignment3.newWorkType, jobWorkTypes);
                                this.mActivePkgStats.add(next.getSourceUserId(), next.getSourcePackageName(), pkgStatsLocked);
                            }
                            arraySet4 = arraySet2;
                            j3 = j2;
                            pendingJobQueue2 = pendingJobQueue;
                            list3 = list2;
                            assignmentInfo2 = assignmentInfo;
                        }
                    }
                    z2 = false;
                    contextAssignment = null;
                    if (contextAssignment == null) {
                    }
                    z3 = z2;
                    contextAssignment2 = contextAssignment;
                    contextAssignment3 = contextAssignment2;
                    if (contextAssignment3 == null) {
                    }
                    if (!hasImmediacyPrivilegeLocked) {
                    }
                    com.android.server.job.JobConcurrencyManager.PackageStats pkgStatsLocked2 = getPkgStatsLocked(next.getSourceUserId(), next.getSourcePackageName());
                    if (contextAssignment3 != null) {
                    }
                    if (z4) {
                    }
                    arraySet4 = arraySet2;
                    j3 = j2;
                    pendingJobQueue2 = pendingJobQueue;
                    list3 = list2;
                    assignmentInfo2 = assignmentInfo;
                }
            } else {
                return;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void carryOutAssignmentChangesLocked(android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.job.JobConcurrencyManager.ContextAssignment valueAt = arraySet.valueAt(size);
            com.android.server.job.controllers.JobStatus runningJobLocked = valueAt.context.getRunningJobLocked();
            if (runningJobLocked != null) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "preempting job: " + runningJobLocked);
                }
                valueAt.context.cancelExecutingJobLocked(valueAt.preemptReasonCode, 2, valueAt.preemptReason);
            } else {
                com.android.server.job.controllers.JobStatus jobStatus = valueAt.newJob;
                if (DEBUG) {
                    android.util.Slog.d(TAG, "About to run job on context " + valueAt.context.getId() + ", job: " + jobStatus);
                }
                startJobLocked(valueAt.context, jobStatus, valueAt.newWorkType);
            }
            valueAt.clear();
            this.mContextAssignmentPool.release(valueAt);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cleanUpAfterAssignmentChangesLocked(android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet, android.util.ArraySet<com.android.server.job.JobConcurrencyManager.ContextAssignment> arraySet2, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list, java.util.List<com.android.server.job.JobConcurrencyManager.ContextAssignment> list2, com.android.server.job.JobConcurrencyManager.AssignmentInfo assignmentInfo, android.util.SparseIntArray sparseIntArray) {
        for (int size = list2.size() - 1; size >= 0; size--) {
            com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment = list2.get(size);
            contextAssignment.clear();
            this.mContextAssignmentPool.release(contextAssignment);
        }
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment2 = list.get(size2);
            contextAssignment2.clear();
            this.mContextAssignmentPool.release(contextAssignment2);
        }
        for (int size3 = arraySet2.size() - 1; size3 >= 0; size3--) {
            com.android.server.job.JobConcurrencyManager.ContextAssignment valueAt = arraySet2.valueAt(size3);
            this.mIdleContexts.add(valueAt.context);
            valueAt.clear();
            this.mContextAssignmentPool.release(valueAt);
        }
        arraySet.clear();
        arraySet2.clear();
        list2.clear();
        list.clear();
        assignmentInfo.clear();
        sparseIntArray.clear();
        this.mWorkCountTracker.resetStagingCount();
        this.mActivePkgStats.forEach(this.mPackageStatsStagingCountClearer);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean hasImmediacyPrivilegeLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull android.util.SparseIntArray sparseIntArray) {
        if (!jobStatus.shouldTreatAsExpeditedJob() && !jobStatus.shouldTreatAsUserInitiatedJob()) {
            return false;
        }
        if (jobStatus.lastEvaluatedBias == 40) {
            return true;
        }
        int sourceUid = jobStatus.getSourceUid();
        switch (sparseIntArray.get(sourceUid, 0)) {
            case 1:
                return false;
            case 2:
                return jobStatus.shouldTreatAsUserInitiatedJob();
            case 3:
                return true;
            default:
                if (this.mActivityManagerInternal.getUidProcessState(sourceUid) == 2) {
                    sparseIntArray.put(sourceUid, 3);
                    return true;
                }
                if (jobStatus.shouldTreatAsExpeditedJob()) {
                    return false;
                }
                android.app.BackgroundStartPrivileges backgroundStartPrivileges = this.mActivityManagerInternal.getBackgroundStartPrivileges(sourceUid);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Job " + jobStatus.toShortString() + " bsp state: " + backgroundStartPrivileges);
                }
                boolean allowsBackgroundActivityStarts = backgroundStartPrivileges.allowsBackgroundActivityStarts();
                sparseIntArray.put(sourceUid, allowsBackgroundActivityStarts ? 2 : 1);
                return allowsBackgroundActivityStarts;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onUidBiasChangedLocked(int i, int i2) {
        if ((i != 40 && i2 != 40) || this.mService.getPendingJobQueue().size() == 0) {
            return;
        }
        assignJobsToContextsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.job.JobServiceContext getRunningJobServiceContextLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mRunningJobs.contains(jobStatus)) {
            return null;
        }
        for (int i = 0; i < this.mActiveServices.size(); i++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i);
            if (jobServiceContext.getRunningJobLocked() == jobStatus) {
                return jobServiceContext;
            }
        }
        android.util.Slog.wtf(TAG, "Couldn't find running job on a context");
        this.mRunningJobs.remove(jobStatus);
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean stopJobOnServiceContextLocked(com.android.server.job.controllers.JobStatus jobStatus, int i, int i2, java.lang.String str) {
        if (!this.mRunningJobs.contains(jobStatus)) {
            return false;
        }
        for (int i3 = 0; i3 < this.mActiveServices.size(); i3++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i3);
            if (jobServiceContext.getRunningJobLocked() == jobStatus) {
                jobServiceContext.cancelExecutingJobLocked(i, i2, str);
                return true;
            }
        }
        android.util.Slog.wtf(TAG, "Couldn't find running job on a context");
        this.mRunningJobs.remove(jobStatus);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void stopUnexemptedJobsForDoze() {
        for (int i = 0; i < this.mActiveServices.size(); i++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null && !runningJobLocked.canRunInDoze()) {
                jobServiceContext.cancelExecutingJobLocked(4, 4, "cancelled due to doze");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void stopOvertimeJobsLocked(@android.annotation.NonNull java.lang.String str) {
        for (int i = 0; i < this.mActiveServices.size(); i++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i);
            if (jobServiceContext.getRunningJobLocked() != null && !jobServiceContext.isWithinExecutionGuaranteeTime()) {
                jobServiceContext.cancelExecutingJobLocked(4, 3, str);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void maybeStopOvertimeJobsLocked(@android.annotation.NonNull com.android.server.job.restrictions.JobRestriction jobRestriction) {
        for (int size = this.mActiveServices.size() - 1; size >= 0; size--) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(size);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null && !jobServiceContext.isWithinExecutionGuaranteeTime() && jobRestriction.isJobRestricted(runningJobLocked)) {
                jobServiceContext.cancelExecutingJobLocked(jobRestriction.getStopReason(), jobRestriction.getInternalReason(), android.app.job.JobParameters.getInternalReasonCodeDescription(jobRestriction.getInternalReason()));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void markJobsForUserStopLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        for (int size = this.mActiveServices.size() - 1; size >= 0; size--) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(size);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null && i == runningJobLocked.getUserId() && runningJobLocked.getServiceComponent().getPackageName().equals(str)) {
                jobServiceContext.markForProcessDeathLocked(13, 11, str2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void stopNonReadyActiveJobsLocked() {
        for (int i = 0; i < this.mActiveServices.size(); i++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null) {
                if (!runningJobLocked.isReady()) {
                    if (runningJobLocked.getEffectiveStandbyBucket() == 5 && runningJobLocked.getStopReason() == 12) {
                        jobServiceContext.cancelExecutingJobLocked(runningJobLocked.getStopReason(), 6, "cancelled due to restricted bucket");
                    } else {
                        jobServiceContext.cancelExecutingJobLocked(runningJobLocked.getStopReason(), 1, "cancelled due to unsatisfied constraints");
                    }
                } else {
                    com.android.server.job.restrictions.JobRestriction checkIfRestricted = this.mService.checkIfRestricted(runningJobLocked);
                    if (checkIfRestricted != null) {
                        int internalReason = checkIfRestricted.getInternalReason();
                        jobServiceContext.cancelExecutingJobLocked(checkIfRestricted.getStopReason(), internalReason, "restricted due to " + android.app.job.JobParameters.getInternalReasonCodeDescription(internalReason));
                    }
                }
            }
        }
    }

    private void noteConcurrency(boolean z) {
        this.mService.mJobPackageTracker.noteConcurrency(this.mRunningJobs.size(), this.mWorkCountTracker.getRunningJobCount(1));
        if (z) {
            sConcurrencyHistogramLogger.logSample(this.mActiveServices.size());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateNonRunningPrioritiesLocked(@android.annotation.NonNull com.android.server.job.PendingJobQueue pendingJobQueue, boolean z) {
        pendingJobQueue.resetIterator();
        while (true) {
            com.android.server.job.controllers.JobStatus next = pendingJobQueue.next();
            if (next != null) {
                if (!this.mRunningJobs.contains(next)) {
                    next.lastEvaluatedBias = this.mService.evaluateJobBiasLocked(next);
                    if (z) {
                        this.mWorkCountTracker.incrementPendingJobCount(getJobWorkTypes(next));
                    }
                }
            } else {
                return;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.job.JobConcurrencyManager.PackageStats getPkgStatsLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.JobConcurrencyManager.PackageStats packageStats = (com.android.server.job.JobConcurrencyManager.PackageStats) this.mActivePkgStats.get(i, str);
        if (packageStats == null) {
            packageStats = (com.android.server.job.JobConcurrencyManager.PackageStats) this.mPkgStatsPool.acquire();
            if (packageStats == null) {
                packageStats = new com.android.server.job.JobConcurrencyManager.PackageStats();
            }
            packageStats.setPackage(i, str);
        }
        return packageStats;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isPkgConcurrencyLimitedLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.JobConcurrencyManager.PackageStats packageStats;
        if (jobStatus.lastEvaluatedBias < 40 && this.mService.getPendingJobQueue().size() + this.mRunningJobs.size() >= this.mWorkTypeConfig.getMaxTotal() && (packageStats = (com.android.server.job.JobConcurrencyManager.PackageStats) this.mActivePkgStats.get(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName())) != null) {
            return jobStatus.shouldTreatAsExpeditedJob() ? packageStats.numRunningEj + packageStats.numStagedEj >= this.mPkgConcurrencyLimitEj : packageStats.numRunningRegular + packageStats.numStagedRegular >= this.mPkgConcurrencyLimitRegular;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startJobLocked(@android.annotation.NonNull com.android.server.job.JobServiceContext jobServiceContext, @android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, int i) {
        java.util.List<com.android.server.job.controllers.StateController> list = this.mService.mControllers;
        int size = list.size();
        android.os.PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, jobStatus.getWakelockTag());
        newWakeLock.setWorkSource(this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()));
        newWakeLock.setReferenceCounted(false);
        newWakeLock.acquire();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                list.get(i2).prepareForExecutionLocked(jobStatus);
            } catch (java.lang.Throwable th) {
                newWakeLock.release();
                throw th;
            }
        }
        com.android.server.job.JobConcurrencyManager.PackageStats pkgStatsLocked = getPkgStatsLocked(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
        pkgStatsLocked.adjustStagedCount(false, jobStatus.shouldTreatAsExpeditedJob());
        if (!jobServiceContext.executeRunnableJob(jobStatus, i)) {
            android.util.Slog.e(TAG, "Error executing " + jobStatus);
            this.mWorkCountTracker.onStagedJobFailed(i);
            for (int i3 = 0; i3 < size; i3++) {
                list.get(i3).unprepareFromExecutionLocked(jobStatus);
            }
        } else {
            this.mRunningJobs.add(jobStatus);
            this.mActiveServices.add(jobServiceContext);
            this.mIdleContexts.remove(jobServiceContext);
            this.mWorkCountTracker.onJobStarted(i);
            pkgStatsLocked.adjustRunningCount(true, jobStatus.shouldTreatAsExpeditedJob());
            this.mActivePkgStats.add(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), pkgStatsLocked);
            this.mService.resetPendingJobReasonCache(jobStatus);
        }
        if (this.mService.getPendingJobQueue().remove(jobStatus)) {
            this.mService.mJobPackageTracker.noteNonpending(jobStatus);
        }
        newWakeLock.release();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onJobCompletedLocked(@android.annotation.NonNull com.android.server.job.JobServiceContext jobServiceContext, @android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, int i) {
        java.lang.String str;
        int jobWorkTypes;
        int canJobStart;
        java.lang.String str2;
        boolean z;
        this.mWorkCountTracker.onJobFinished(i);
        this.mRunningJobs.remove(jobStatus);
        this.mActiveServices.remove(jobServiceContext);
        boolean z2 = true;
        if (this.mIdleContexts.size() >= 96) {
            this.mNumDroppedContexts++;
        } else {
            this.mIdleContexts.add(jobServiceContext);
        }
        com.android.server.job.JobConcurrencyManager.PackageStats packageStats = (com.android.server.job.JobConcurrencyManager.PackageStats) this.mActivePkgStats.get(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName());
        if (packageStats != null) {
            packageStats.adjustRunningCount(false, jobStatus.startedAsExpeditedJob);
            if (packageStats.numRunningEj <= 0 && packageStats.numRunningRegular <= 0) {
                this.mActivePkgStats.delete(packageStats.userId, packageStats.packageName);
                this.mPkgStatsPool.release(packageStats);
            }
        } else {
            android.util.Slog.wtf(TAG, "Running job didn't have an active PackageStats object");
        }
        com.android.server.job.PendingJobQueue pendingJobQueue = this.mService.getPendingJobQueue();
        if (pendingJobQueue.size() != 0) {
            if (this.mActiveServices.size() >= this.mSteadyStateConcurrencyLimit) {
                if (this.mMaxWaitTimeBypassEnabled) {
                    long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    for (int size = this.mActiveServices.size() - 1; size >= 0; size--) {
                        j = java.lang.Math.min(j, this.mActiveServices.get(size).getRemainingGuaranteedTimeMs(millis));
                    }
                    if (this.mWorkCountTracker.getPendingJobCount(4) > 0) {
                        z = j >= this.mMaxWaitUIMs;
                    } else {
                        z = this.mWorkCountTracker.getPendingJobCount(8) > 0 ? j >= this.mMaxWaitEjMs : j >= this.mMaxWaitRegularMs;
                    }
                    z2 = true ^ z;
                }
                if (z2) {
                    jobServiceContext.clearPreferredUid();
                    noteConcurrency(false);
                    return;
                }
            }
            java.lang.String str3 = "Already running similar job to: ";
            com.android.server.job.controllers.JobStatus jobStatus2 = null;
            if (jobServiceContext.getPreferredUid() != -1) {
                updateCounterConfigLocked();
                updateNonRunningPrioritiesLocked(pendingJobQueue, false);
                pendingJobQueue.resetIterator();
                int i2 = i;
                int i3 = i2;
                int i4 = 0;
                int i5 = 0;
                com.android.server.job.controllers.JobStatus jobStatus3 = null;
                while (true) {
                    com.android.server.job.controllers.JobStatus next = pendingJobQueue.next();
                    if (next == null) {
                        break;
                    }
                    if (this.mRunningJobs.contains(next)) {
                        android.util.Slog.wtf(TAG, "Pending queue contained a running job");
                        if (DEBUG) {
                            android.util.Slog.e(TAG, "Pending+running job: " + next);
                        }
                        pendingJobQueue.remove(next);
                        str2 = str3;
                    } else {
                        if (DEBUG && isSimilarJobRunningLocked(next)) {
                            android.util.Slog.w(TAG, str3 + next);
                        }
                        str2 = str3;
                        if (jobServiceContext.getPreferredUid() != next.getUid()) {
                            if (jobStatus3 == null && !isPkgConcurrencyLimitedLocked(next)) {
                                int jobWorkTypes2 = getJobWorkTypes(next);
                                int canJobStart2 = this.mWorkCountTracker.canJobStart(jobWorkTypes2);
                                if (canJobStart2 != 0) {
                                    i5 = jobWorkTypes2;
                                    jobStatus3 = next;
                                    i4 = canJobStart2;
                                }
                                str3 = str2;
                            }
                        } else if ((next.lastEvaluatedBias > jobStatus.lastEvaluatedBias || !isPkgConcurrencyLimitedLocked(next)) && (jobStatus2 == null || jobStatus2.lastEvaluatedBias < next.lastEvaluatedBias)) {
                            i3 = getJobWorkTypes(next);
                            int canJobStart3 = this.mWorkCountTracker.canJobStart(i3);
                            if (canJobStart3 == 0) {
                                i2 = i;
                            } else {
                                i2 = canJobStart3;
                            }
                            jobStatus2 = next;
                            str3 = str2;
                        }
                    }
                    str3 = str2;
                }
                if (jobStatus2 != null) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Running job " + jobStatus2 + " as preemption");
                    }
                    this.mWorkCountTracker.stageJob(i2, i3);
                    startJobLocked(jobServiceContext, jobStatus2, i2);
                } else {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Couldn't find preemption job for uid " + jobServiceContext.getPreferredUid());
                    }
                    jobServiceContext.clearPreferredUid();
                    if (jobStatus3 != null) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Running job " + jobStatus3 + " instead");
                        }
                        this.mWorkCountTracker.stageJob(i4, i5);
                        startJobLocked(jobServiceContext, jobStatus3, i4);
                    }
                }
            } else {
                java.lang.String str4 = "Already running similar job to: ";
                if (pendingJobQueue.size() > 0) {
                    updateCounterConfigLocked();
                    updateNonRunningPrioritiesLocked(pendingJobQueue, false);
                    pendingJobQueue.resetIterator();
                    int i6 = i;
                    int i7 = i6;
                    while (true) {
                        com.android.server.job.controllers.JobStatus next2 = pendingJobQueue.next();
                        if (next2 == null) {
                            break;
                        }
                        if (this.mRunningJobs.contains(next2)) {
                            android.util.Slog.wtf(TAG, "Pending queue contained a running job");
                            if (DEBUG) {
                                android.util.Slog.e(TAG, "Pending+running job: " + next2);
                            }
                            pendingJobQueue.remove(next2);
                            str = str4;
                        } else {
                            if (!DEBUG || !isSimilarJobRunningLocked(next2)) {
                                str = str4;
                            } else {
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                str = str4;
                                sb.append(str);
                                sb.append(next2);
                                android.util.Slog.w(TAG, sb.toString());
                            }
                            if (!isPkgConcurrencyLimitedLocked(next2) && (canJobStart = this.mWorkCountTracker.canJobStart((jobWorkTypes = getJobWorkTypes(next2)))) != 0) {
                                if (jobStatus2 == null || jobStatus2.lastEvaluatedBias < next2.lastEvaluatedBias) {
                                    jobStatus2 = next2;
                                    i7 = jobWorkTypes;
                                    i6 = canJobStart;
                                }
                                str4 = str;
                            }
                        }
                        str4 = str;
                    }
                    if (jobStatus2 != null) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "About to run job: " + jobStatus2);
                        }
                        this.mWorkCountTracker.stageJob(i6, i7);
                        startJobLocked(jobServiceContext, jobStatus2, i6);
                    }
                }
            }
            noteConcurrency(false);
            return;
        }
        jobServiceContext.clearPreferredUid();
        noteConcurrency(false);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    java.lang.String shouldStopRunningJobLocked(@android.annotation.NonNull com.android.server.job.JobServiceContext jobServiceContext) {
        com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
        if (runningJobLocked == null || jobServiceContext.isWithinExecutionGuaranteeTime()) {
            return null;
        }
        if (this.mPowerManager.isPowerSaveMode()) {
            return "battery saver";
        }
        if (this.mPowerManager.isDeviceIdleMode()) {
            return "deep doze";
        }
        com.android.server.job.restrictions.JobRestriction checkIfRestricted = this.mService.checkIfRestricted(runningJobLocked);
        if (checkIfRestricted != null) {
            return "restriction:" + android.app.job.JobParameters.getInternalReasonCodeDescription(checkIfRestricted.getInternalReason());
        }
        updateCounterConfigLocked();
        int runningJobWorkType = jobServiceContext.getRunningJobWorkType();
        if (this.mRunningJobs.size() > this.mWorkTypeConfig.getMaxTotal() || this.mWorkCountTracker.isOverTypeLimit(runningJobWorkType)) {
            return "too many jobs running";
        }
        com.android.server.job.PendingJobQueue pendingJobQueue = this.mService.getPendingJobQueue();
        if (pendingJobQueue.size() == 0) {
            return null;
        }
        if (runningJobLocked.shouldTreatAsExpeditedJob() || runningJobLocked.startedAsExpeditedJob) {
            if (runningJobWorkType == 32 || runningJobWorkType == 64) {
                if (this.mWorkCountTracker.getPendingJobCount(32) > 0) {
                    return "blocking " + workTypeToString(32) + " queue";
                }
                if (this.mWorkCountTracker.getPendingJobCount(8) > 0 && this.mWorkCountTracker.canJobStart(8, runningJobWorkType) != 0) {
                    return "blocking " + workTypeToString(8) + " queue";
                }
            } else {
                if (this.mWorkCountTracker.getPendingJobCount(8) > 0) {
                    return "blocking " + workTypeToString(8) + " queue";
                }
                if (runningJobLocked.startedWithImmediacyPrivilege) {
                    int i = 0;
                    for (int size = this.mRunningJobs.size() - 1; size >= 0; size--) {
                        if (this.mRunningJobs.valueAt(size).startedWithImmediacyPrivilege) {
                            i++;
                        }
                    }
                    if (i > this.mWorkTypeConfig.getMaxTotal() / 2) {
                        return "prevent immediacy privilege dominance";
                    }
                }
            }
            return null;
        }
        if (this.mWorkCountTracker.getPendingJobCount(runningJobWorkType) > 0) {
            return "blocking " + workTypeToString(runningJobWorkType) + " queue";
        }
        pendingJobQueue.resetIterator();
        int i2 = 127;
        do {
            com.android.server.job.controllers.JobStatus next = pendingJobQueue.next();
            if (next == null) {
                break;
            }
            int jobWorkTypes = getJobWorkTypes(next);
            if ((jobWorkTypes & i2) > 0 && this.mWorkCountTracker.canJobStart(jobWorkTypes, runningJobWorkType) != 0) {
                return "blocking other pending jobs";
            }
            i2 &= ~jobWorkTypes;
        } while (i2 != 0);
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean executeStopCommandLocked(java.io.PrintWriter printWriter, java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, boolean z, int i2, int i3, int i4) {
        boolean z2 = false;
        for (int i5 = 0; i5 < this.mActiveServices.size(); i5++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i5);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (jobServiceContext.stopIfExecutingLocked(str, i, str2, z, i2, i3, i4)) {
                printWriter.print("Stopping job: ");
                runningJobLocked.printUniqueId(printWriter);
                printWriter.print(" ");
                printWriter.println(runningJobLocked.getServiceComponent().flattenToShortString());
                z2 = true;
            }
        }
        return z2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.util.Pair<java.lang.Long, java.lang.Long> getEstimatedNetworkBytesLocked(java.lang.String str, int i, java.lang.String str2, int i2) {
        for (int i3 = 0; i3 < this.mActiveServices.size(); i3++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i3);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null && runningJobLocked.matches(i, str2, i2) && runningJobLocked.getSourcePackageName().equals(str)) {
                return jobServiceContext.getEstimatedNetworkBytes();
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.util.Pair<java.lang.Long, java.lang.Long> getTransferredNetworkBytesLocked(java.lang.String str, int i, java.lang.String str2, int i2) {
        for (int i3 = 0; i3 < this.mActiveServices.size(); i3++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i3);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked != null && runningJobLocked.matches(i, str2, i2) && runningJobLocked.getSourcePackageName().equals(str)) {
                return jobServiceContext.getTransferredNetworkBytes();
            }
        }
        return null;
    }

    boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        return this.mNotificationCoordinator.isNotificationAssociatedWithAnyUserInitiatedJobs(i, i2, str);
    }

    boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        return this.mNotificationCoordinator.isNotificationChannelAssociatedWithAnyUserInitiatedJobs(str, i, str2);
    }

    @android.annotation.NonNull
    private com.android.server.job.JobServiceContext createNewJobServiceContext() {
        return this.mInjector.createJobServiceContext(this.mService, this, this.mNotificationCoordinator, com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats")), this.mService.mJobPackageTracker, com.android.server.AppSchedulingModuleThread.get().getLooper());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String printPendingQueueLocked() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Pending queue: ");
        com.android.server.job.PendingJobQueue pendingJobQueue = this.mService.getPendingJobQueue();
        pendingJobQueue.resetIterator();
        while (true) {
            com.android.server.job.controllers.JobStatus next = pendingJobQueue.next();
            if (next != null) {
                sb.append("(");
                sb.append("{");
                sb.append(next.getNamespace());
                sb.append("} ");
                sb.append(next.getJob().getId());
                sb.append(", ");
                sb.append(next.getUid());
                sb.append(") ");
            } else {
                return sb.toString();
            }
        }
    }

    private static java.lang.String printAssignments(java.lang.String str, java.util.Collection<com.android.server.job.JobConcurrencyManager.ContextAssignment>... collectionArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str + ": ");
        for (int i = 0; i < collectionArr.length; i++) {
            int i2 = 0;
            for (com.android.server.job.JobConcurrencyManager.ContextAssignment contextAssignment : collectionArr[i]) {
                com.android.server.job.controllers.JobStatus runningJobLocked = contextAssignment.newJob == null ? contextAssignment.context.getRunningJobLocked() : contextAssignment.newJob;
                if (i > 0 || i2 > 0) {
                    sb.append(" ");
                }
                sb.append("(");
                sb.append(contextAssignment.context.getId());
                sb.append("=");
                if (runningJobLocked == null) {
                    sb.append("nothing");
                } else {
                    if (runningJobLocked.getNamespace() != null) {
                        sb.append(runningJobLocked.getNamespace());
                        sb.append(":");
                    }
                    sb.append(runningJobLocked.getJobId());
                    sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                    sb.append(runningJobLocked.getUid());
                }
                sb.append(")");
                i2++;
            }
        }
        return sb.toString();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateConfigLocked() {
        android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("jobscheduler", new java.lang.String[0]);
        this.mSteadyStateConcurrencyLimit = java.lang.Math.max(8, java.lang.Math.min(64, properties.getInt(KEY_CONCURRENCY_LIMIT, DEFAULT_CONCURRENCY_LIMIT)));
        this.mScreenOffAdjustmentDelayMs = properties.getLong(KEY_SCREEN_OFF_ADJUSTMENT_DELAY_MS, 30000L);
        CONFIG_LIMITS_SCREEN_ON.normal.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_ON.moderate.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_ON.low.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_ON.critical.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_OFF.normal.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_OFF.moderate.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_OFF.low.update(properties, this.mSteadyStateConcurrencyLimit);
        CONFIG_LIMITS_SCREEN_OFF.critical.update(properties, this.mSteadyStateConcurrencyLimit);
        this.mPkgConcurrencyLimitEj = java.lang.Math.max(1, java.lang.Math.min(this.mSteadyStateConcurrencyLimit, properties.getInt(KEY_PKG_CONCURRENCY_LIMIT_EJ, 3)));
        this.mPkgConcurrencyLimitRegular = java.lang.Math.max(1, java.lang.Math.min(this.mSteadyStateConcurrencyLimit, properties.getInt(KEY_PKG_CONCURRENCY_LIMIT_REGULAR, DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR)));
        this.mMaxWaitTimeBypassEnabled = properties.getBoolean(KEY_ENABLE_MAX_WAIT_TIME_BYPASS, true);
        this.mMaxWaitUIMs = java.lang.Math.max(0L, properties.getLong(KEY_MAX_WAIT_UI_MS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS));
        this.mMaxWaitEjMs = java.lang.Math.max(this.mMaxWaitUIMs, properties.getLong(KEY_MAX_WAIT_EJ_MS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS));
        this.mMaxWaitRegularMs = java.lang.Math.max(this.mMaxWaitEjMs, properties.getLong(KEY_MAX_WAIT_REGULAR_MS, 1800000L));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpLocked(final android.util.IndentingPrintWriter indentingPrintWriter, long j, long j2) {
        indentingPrintWriter.println("Concurrency:");
        indentingPrintWriter.increaseIndent();
        try {
            indentingPrintWriter.println("Configuration:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_CONCURRENCY_LIMIT, java.lang.Integer.valueOf(this.mSteadyStateConcurrencyLimit)).println();
            indentingPrintWriter.print(KEY_SCREEN_OFF_ADJUSTMENT_DELAY_MS, java.lang.Long.valueOf(this.mScreenOffAdjustmentDelayMs)).println();
            indentingPrintWriter.print(KEY_PKG_CONCURRENCY_LIMIT_EJ, java.lang.Integer.valueOf(this.mPkgConcurrencyLimitEj)).println();
            indentingPrintWriter.print(KEY_PKG_CONCURRENCY_LIMIT_REGULAR, java.lang.Integer.valueOf(this.mPkgConcurrencyLimitRegular)).println();
            indentingPrintWriter.print(KEY_ENABLE_MAX_WAIT_TIME_BYPASS, java.lang.Boolean.valueOf(this.mMaxWaitTimeBypassEnabled)).println();
            indentingPrintWriter.print(KEY_MAX_WAIT_UI_MS, java.lang.Long.valueOf(this.mMaxWaitUIMs)).println();
            indentingPrintWriter.print(KEY_MAX_WAIT_EJ_MS, java.lang.Long.valueOf(this.mMaxWaitEjMs)).println();
            indentingPrintWriter.print(KEY_MAX_WAIT_REGULAR_MS, java.lang.Long.valueOf(this.mMaxWaitRegularMs)).println();
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_ON.normal.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_ON.moderate.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_ON.low.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_ON.critical.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_OFF.normal.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_OFF.moderate.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_OFF.low.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            CONFIG_LIMITS_SCREEN_OFF.critical.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.print("Screen state: current ");
            indentingPrintWriter.print(this.mCurrentInteractiveState ? "ON" : "OFF");
            indentingPrintWriter.print("  effective ");
            indentingPrintWriter.print(this.mEffectiveInteractiveState ? "ON" : "OFF");
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last screen ON: ");
            long j3 = j - j2;
            android.util.TimeUtils.dumpTimeWithDelta(indentingPrintWriter, this.mLastScreenOnRealtime + j3, j);
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last screen OFF: ");
            android.util.TimeUtils.dumpTimeWithDelta(indentingPrintWriter, j3 + this.mLastScreenOffRealtime, j);
            indentingPrintWriter.println();
            indentingPrintWriter.println();
            indentingPrintWriter.print("Current work counts: ");
            indentingPrintWriter.println(this.mWorkCountTracker);
            indentingPrintWriter.println();
            indentingPrintWriter.print("mLastMemoryTrimLevel: ");
            indentingPrintWriter.println(this.mLastMemoryTrimLevel);
            indentingPrintWriter.println();
            indentingPrintWriter.println("Active Package stats:");
            indentingPrintWriter.increaseIndent();
            this.mActivePkgStats.forEach(new java.util.function.Consumer() { // from class: com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.job.JobConcurrencyManager.PackageStats.m4413$$Nest$mdumpLocked((com.android.server.job.JobConcurrencyManager.PackageStats) obj, indentingPrintWriter);
                }
            });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("User Grace Period: ");
            indentingPrintWriter.println(this.mGracePeriodObserver.mGracePeriodExpiration);
            indentingPrintWriter.println();
            this.mStatLogger.dump(indentingPrintWriter);
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dumpContextInfoLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate, long j, long j2) {
        indentingPrintWriter.println("Active jobs:");
        indentingPrintWriter.increaseIndent();
        if (this.mActiveServices.size() == 0) {
            indentingPrintWriter.println("N/A");
        }
        for (int i = 0; i < this.mActiveServices.size(); i++) {
            com.android.server.job.JobServiceContext jobServiceContext = this.mActiveServices.get(i);
            com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
            if (runningJobLocked == null || predicate.test(runningJobLocked)) {
                indentingPrintWriter.print("Slot #");
                indentingPrintWriter.print(i);
                indentingPrintWriter.print("(ID=");
                indentingPrintWriter.print(jobServiceContext.getId());
                indentingPrintWriter.print("): ");
                jobServiceContext.dumpLocked(indentingPrintWriter, j);
                if (runningJobLocked != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.increaseIndent();
                    runningJobLocked.dump(indentingPrintWriter, false, j);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.print("Evaluated bias: ");
                    indentingPrintWriter.println(android.app.job.JobInfo.getBiasString(runningJobLocked.lastEvaluatedBias));
                    indentingPrintWriter.print("Active at ");
                    android.util.TimeUtils.formatDuration(runningJobLocked.madeActive - j2, indentingPrintWriter);
                    indentingPrintWriter.print(", pending for ");
                    android.util.TimeUtils.formatDuration(runningJobLocked.madeActive - runningJobLocked.madePending, indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.print("Idle contexts (");
        indentingPrintWriter.print(this.mIdleContexts.size());
        indentingPrintWriter.println("):");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mIdleContexts.size(); i2++) {
            com.android.server.job.JobServiceContext valueAt = this.mIdleContexts.valueAt(i2);
            indentingPrintWriter.print("ID=");
            indentingPrintWriter.print(valueAt.getId());
            indentingPrintWriter.print(": ");
            valueAt.dumpLocked(indentingPrintWriter, j);
        }
        indentingPrintWriter.decreaseIndent();
        if (this.mNumDroppedContexts > 0) {
            indentingPrintWriter.println();
            indentingPrintWriter.print("Dropped ");
            indentingPrintWriter.print(this.mNumDroppedContexts);
            indentingPrintWriter.println(" contexts");
        }
    }

    public void dumpProtoLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mCurrentInteractiveState);
        protoOutputStream.write(1133871366146L, this.mEffectiveInteractiveState);
        protoOutputStream.write(1112396529667L, j3 - this.mLastScreenOnRealtime);
        protoOutputStream.write(1112396529668L, j3 - this.mLastScreenOffRealtime);
        protoOutputStream.write(1120986464262L, this.mLastMemoryTrimLevel);
        this.mStatLogger.dumpProto(protoOutputStream, 1146756268039L);
        protoOutputStream.end(start);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldRunAsFgUserJob(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!this.mShouldRestrictBgUser) {
            return true;
        }
        int sourceUserId = jobStatus.getSourceUserId();
        android.content.pm.UserInfo userInfo = this.mUserManagerInternal.getUserInfo(sourceUserId);
        if (userInfo.profileGroupId != -10000 && userInfo.profileGroupId != sourceUserId) {
            sourceUserId = userInfo.profileGroupId;
            userInfo = this.mUserManagerInternal.getUserInfo(sourceUserId);
        }
        return this.mActivityManagerInternal.getCurrentUserId() == sourceUserId || userInfo.isPrimary() || this.mGracePeriodObserver.isWithinGracePeriodForUser(sourceUserId);
    }

    int getJobWorkTypes(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        int i;
        if (shouldRunAsFgUserJob(jobStatus)) {
            if (jobStatus.lastEvaluatedBias >= 40) {
                i = 1;
            } else if (jobStatus.lastEvaluatedBias >= 35) {
                i = 2;
            } else {
                i = 16;
            }
            if (jobStatus.shouldTreatAsExpeditedJob()) {
                return i | 8;
            }
            if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                return i | 4;
            }
            return i;
        }
        return ((jobStatus.lastEvaluatedBias >= 35 || jobStatus.shouldTreatAsExpeditedJob() || jobStatus.shouldTreatAsUserInitiatedJob()) ? 32 : 0) | 64;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class WorkTypeConfig {
        private static final java.lang.String KEY_PREFIX_MAX = "concurrency_max_";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_PREFIX_MAX_RATIO = "concurrency_max_ratio_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_BG = "concurrency_max_ratio_bg_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_BGUSER = "concurrency_max_ratio_bguser_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_BGUSER_IMPORTANT = "concurrency_max_ratio_bguser_important_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_EJ = "concurrency_max_ratio_ej_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_FGS = "concurrency_max_ratio_fgs_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_TOP = "concurrency_max_ratio_top_";
        private static final java.lang.String KEY_PREFIX_MAX_RATIO_UI = "concurrency_max_ratio_ui_";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_PREFIX_MAX_TOTAL = "concurrency_max_total_";
        private static final java.lang.String KEY_PREFIX_MIN = "concurrency_min_";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_PREFIX_MIN_RATIO = "concurrency_min_ratio_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_BG = "concurrency_min_ratio_bg_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_BGUSER = "concurrency_min_ratio_bguser_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_BGUSER_IMPORTANT = "concurrency_min_ratio_bguser_important_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_EJ = "concurrency_min_ratio_ej_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_FGS = "concurrency_min_ratio_fgs_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_TOP = "concurrency_min_ratio_top_";
        private static final java.lang.String KEY_PREFIX_MIN_RATIO_UI = "concurrency_min_ratio_ui_";
        private final java.lang.String mConfigIdentifier;
        private final int mDefaultMaxTotal;
        private int mMaxTotal;
        private final android.util.SparseIntArray mMinReservedSlots = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mMaxAllowedSlots = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mDefaultMinReservedSlotsRatio = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mDefaultMaxAllowedSlotsRatio = new android.util.SparseIntArray(7);

        WorkTypeConfig(@android.annotation.NonNull java.lang.String str, int i, int i2, java.util.List<android.util.Pair<java.lang.Integer, java.lang.Float>> list, java.util.List<android.util.Pair<java.lang.Integer, java.lang.Float>> list2) {
            this.mConfigIdentifier = str;
            int min = java.lang.Math.min(i2, i);
            this.mMaxTotal = min;
            this.mDefaultMaxTotal = min;
            int i3 = 0;
            for (int size = list.size() - 1; size >= 0; size--) {
                float floatValue = ((java.lang.Float) list.get(size).second).floatValue();
                int intValue = ((java.lang.Integer) list.get(size).first).intValue();
                if (floatValue < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || 1.0f <= floatValue) {
                    throw new java.lang.IllegalArgumentException("Invalid default min ratio: wt=" + intValue + " minRatio=" + floatValue);
                }
                this.mDefaultMinReservedSlotsRatio.put(intValue, java.lang.Float.floatToRawIntBits(floatValue));
                i3 = (int) (i3 + (this.mMaxTotal * floatValue));
            }
            if (this.mDefaultMaxTotal < 0 || i3 > this.mDefaultMaxTotal) {
                throw new java.lang.IllegalArgumentException("Invalid default config: t=" + i2 + " min=" + list + " max=" + list2);
            }
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                float floatValue2 = ((java.lang.Float) list2.get(size2).second).floatValue();
                int intValue2 = ((java.lang.Integer) list2.get(size2).first).intValue();
                if (floatValue2 < java.lang.Float.intBitsToFloat(this.mDefaultMinReservedSlotsRatio.get(intValue2, 0)) || floatValue2 <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    throw new java.lang.IllegalArgumentException("Invalid default config: t=" + i2 + " min=" + list + " max=" + list2);
                }
                this.mDefaultMaxAllowedSlotsRatio.put(intValue2, java.lang.Float.floatToRawIntBits(floatValue2));
            }
            update(new android.provider.DeviceConfig.Properties.Builder("jobscheduler").build(), i);
        }

        void update(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, int i) {
            this.mMaxTotal = java.lang.Math.max(1, java.lang.Math.min(i, properties.getInt(KEY_PREFIX_MAX_TOTAL + this.mConfigIdentifier, this.mDefaultMaxTotal)));
            int floatToIntBits = java.lang.Float.floatToIntBits(1.0f);
            this.mMaxAllowedSlots.clear();
            int maxValue = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_TOP + this.mConfigIdentifier, 1, floatToIntBits);
            this.mMaxAllowedSlots.put(1, maxValue);
            int maxValue2 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_FGS + this.mConfigIdentifier, 2, floatToIntBits);
            this.mMaxAllowedSlots.put(2, maxValue2);
            int maxValue3 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_UI + this.mConfigIdentifier, 4, floatToIntBits);
            this.mMaxAllowedSlots.put(4, maxValue3);
            int maxValue4 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_EJ + this.mConfigIdentifier, 8, floatToIntBits);
            this.mMaxAllowedSlots.put(8, maxValue4);
            int maxValue5 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_BG + this.mConfigIdentifier, 16, floatToIntBits);
            this.mMaxAllowedSlots.put(16, maxValue5);
            int maxValue6 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_BGUSER_IMPORTANT + this.mConfigIdentifier, 32, floatToIntBits);
            this.mMaxAllowedSlots.put(32, maxValue6);
            int maxValue7 = getMaxValue(properties, KEY_PREFIX_MAX_RATIO_BGUSER + this.mConfigIdentifier, 64, floatToIntBits);
            this.mMaxAllowedSlots.put(64, maxValue7);
            int i2 = this.mMaxTotal;
            this.mMinReservedSlots.clear();
            int minValue = getMinValue(properties, KEY_PREFIX_MIN_RATIO_TOP + this.mConfigIdentifier, 1, 1, java.lang.Math.min(maxValue, this.mMaxTotal));
            this.mMinReservedSlots.put(1, minValue);
            int i3 = i2 - minValue;
            int minValue2 = getMinValue(properties, KEY_PREFIX_MIN_RATIO_FGS + this.mConfigIdentifier, 2, 0, java.lang.Math.min(maxValue2, i3));
            this.mMinReservedSlots.put(2, minValue2);
            int i4 = i3 - minValue2;
            int minValue3 = getMinValue(properties, KEY_PREFIX_MIN_RATIO_UI + this.mConfigIdentifier, 4, 0, java.lang.Math.min(maxValue3, i4));
            this.mMinReservedSlots.put(4, minValue3);
            int i5 = i4 - minValue3;
            int minValue4 = getMinValue(properties, KEY_PREFIX_MIN_RATIO_EJ + this.mConfigIdentifier, 8, 0, java.lang.Math.min(maxValue4, i5));
            this.mMinReservedSlots.put(8, minValue4);
            int i6 = i5 - minValue4;
            int minValue5 = getMinValue(properties, KEY_PREFIX_MIN_RATIO_BG + this.mConfigIdentifier, 16, 0, java.lang.Math.min(maxValue5, i6));
            this.mMinReservedSlots.put(16, minValue5);
            int i7 = i6 - minValue5;
            int minValue6 = getMinValue(properties, KEY_PREFIX_MIN_RATIO_BGUSER_IMPORTANT + this.mConfigIdentifier, 32, 0, java.lang.Math.min(maxValue6, i7));
            this.mMinReservedSlots.put(32, minValue6);
            this.mMinReservedSlots.put(64, getMinValue(properties, KEY_PREFIX_MIN_RATIO_BGUSER + this.mConfigIdentifier, 64, 0, java.lang.Math.min(maxValue7, i7 - minValue6)));
        }

        private int getMaxValue(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str, int i, int i2) {
            return java.lang.Math.max(1, (int) (this.mMaxTotal * java.lang.Math.min(1.0f, properties.getFloat(str, java.lang.Float.intBitsToFloat(this.mDefaultMaxAllowedSlotsRatio.get(i, i2))))));
        }

        private int getMinValue(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
            return java.lang.Math.max(i2, java.lang.Math.min(i3, (int) (this.mMaxTotal * java.lang.Math.min(1.0f, properties.getFloat(str, java.lang.Float.intBitsToFloat(this.mDefaultMinReservedSlotsRatio.get(i)))))));
        }

        int getMaxTotal() {
            return this.mMaxTotal;
        }

        int getMax(int i) {
            return this.mMaxAllowedSlots.get(i, this.mMaxTotal);
        }

        int getMinReserved(int i) {
            return this.mMinReservedSlots.get(i);
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print(KEY_PREFIX_MAX_TOTAL + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxTotal)).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_TOP + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(1))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_TOP + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(1))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_FGS + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(2))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_FGS + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(2))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_UI + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(4))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_UI + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(4))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_EJ + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(8))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_EJ + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(8))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_BG + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(16))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_BG + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(16))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_BGUSER + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(32))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_BGUSER + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(32))).println();
            indentingPrintWriter.print(KEY_PREFIX_MIN_RATIO_BGUSER + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMinReservedSlots.get(64))).println();
            indentingPrintWriter.print(KEY_PREFIX_MAX_RATIO_BGUSER + this.mConfigIdentifier, java.lang.Integer.valueOf(this.mMaxAllowedSlots.get(64))).println();
        }
    }

    static class WorkConfigLimitsPerMemoryTrimLevel {
        public final com.android.server.job.JobConcurrencyManager.WorkTypeConfig critical;
        public final com.android.server.job.JobConcurrencyManager.WorkTypeConfig low;
        public final com.android.server.job.JobConcurrencyManager.WorkTypeConfig moderate;
        public final com.android.server.job.JobConcurrencyManager.WorkTypeConfig normal;

        WorkConfigLimitsPerMemoryTrimLevel(com.android.server.job.JobConcurrencyManager.WorkTypeConfig workTypeConfig, com.android.server.job.JobConcurrencyManager.WorkTypeConfig workTypeConfig2, com.android.server.job.JobConcurrencyManager.WorkTypeConfig workTypeConfig3, com.android.server.job.JobConcurrencyManager.WorkTypeConfig workTypeConfig4) {
            this.normal = workTypeConfig;
            this.moderate = workTypeConfig2;
            this.low = workTypeConfig3;
            this.critical = workTypeConfig4;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class GracePeriodObserver extends android.app.UserSwitchObserver {

        @com.android.internal.annotations.VisibleForTesting
        int mGracePeriod;

        @com.android.internal.annotations.VisibleForTesting
        final android.util.SparseLongArray mGracePeriodExpiration = new android.util.SparseLongArray();
        final java.lang.Object mLock = new java.lang.Object();
        private int mCurrentUserId = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentUserId();
        private final com.android.server.pm.UserManagerInternal mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);

        GracePeriodObserver(android.content.Context context) {
            this.mGracePeriod = java.lang.Math.max(0, context.getResources().getInteger(android.R.integer.config_immersive_mode_confirmation_panic));
        }

        public void onUserSwitchComplete(int i) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() + this.mGracePeriod;
            synchronized (this.mLock) {
                try {
                    if (this.mCurrentUserId != -10000 && this.mUserManagerInternal.exists(this.mCurrentUserId)) {
                        this.mGracePeriodExpiration.append(this.mCurrentUserId, millis);
                    }
                    this.mGracePeriodExpiration.delete(i);
                    this.mCurrentUserId = i;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void onUserRemoved(int i) {
            synchronized (this.mLock) {
                this.mGracePeriodExpiration.delete(i);
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean isWithinGracePeriodForUser(int i) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    z = i == this.mCurrentUserId || com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() < this.mGracePeriodExpiration.get(i, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
                } finally {
                }
            }
            return z;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class WorkCountTracker {
        private int mConfigMaxTotal;
        private final android.util.SparseIntArray mConfigNumReservedSlots = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mConfigAbsoluteMaxSlots = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mRecycledReserved = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mNumActuallyReservedSlots = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mNumPendingJobs = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mNumRunningJobs = new android.util.SparseIntArray(7);
        private final android.util.SparseIntArray mNumStartingJobs = new android.util.SparseIntArray(7);
        private int mNumUnspecializedRemaining = 0;

        WorkCountTracker() {
        }

        void setConfig(@android.annotation.NonNull com.android.server.job.JobConcurrencyManager.WorkTypeConfig workTypeConfig) {
            this.mConfigMaxTotal = workTypeConfig.getMaxTotal();
            for (int i = 1; i < 127; i <<= 1) {
                this.mConfigNumReservedSlots.put(i, workTypeConfig.getMinReserved(i));
                this.mConfigAbsoluteMaxSlots.put(i, workTypeConfig.getMax(i));
            }
            this.mNumUnspecializedRemaining = this.mConfigMaxTotal;
            for (int size = this.mNumRunningJobs.size() - 1; size >= 0; size--) {
                this.mNumUnspecializedRemaining -= java.lang.Math.max(this.mNumRunningJobs.valueAt(size), this.mConfigNumReservedSlots.get(this.mNumRunningJobs.keyAt(size)));
            }
        }

        void resetCounts() {
            this.mNumActuallyReservedSlots.clear();
            this.mNumPendingJobs.clear();
            this.mNumRunningJobs.clear();
            resetStagingCount();
        }

        void resetStagingCount() {
            this.mNumStartingJobs.clear();
        }

        void incrementRunningJobCount(int i) {
            this.mNumRunningJobs.put(i, this.mNumRunningJobs.get(i) + 1);
        }

        void incrementPendingJobCount(int i) {
            adjustPendingJobCount(i, true);
        }

        void decrementPendingJobCount(int i) {
            if (adjustPendingJobCount(i, false) > 1) {
                for (int i2 = 1; i2 <= i; i2 <<= 1) {
                    if ((i2 & i) == i2) {
                        maybeAdjustReservations(i2);
                    }
                }
            }
        }

        private int adjustPendingJobCount(int i, boolean z) {
            int i2 = z ? 1 : -1;
            int i3 = 0;
            for (int i4 = 1; i4 <= i; i4 <<= 1) {
                if ((i & i4) == i4) {
                    this.mNumPendingJobs.put(i4, this.mNumPendingJobs.get(i4) + i2);
                    i3++;
                }
            }
            return i3;
        }

        void stageJob(int i, int i2) {
            int i3 = this.mNumStartingJobs.get(i) + 1;
            this.mNumStartingJobs.put(i, i3);
            decrementPendingJobCount(i2);
            if (i3 + this.mNumRunningJobs.get(i) > this.mNumActuallyReservedSlots.get(i)) {
                this.mNumUnspecializedRemaining--;
            }
        }

        void onStagedJobFailed(int i) {
            int i2 = this.mNumStartingJobs.get(i);
            if (i2 == 0) {
                android.util.Slog.e(com.android.server.job.JobConcurrencyManager.TAG, "# staged jobs for " + i + " went negative.");
                return;
            }
            this.mNumStartingJobs.put(i, i2 - 1);
            maybeAdjustReservations(i);
        }

        private void maybeAdjustReservations(int i) {
            int max = java.lang.Math.max(this.mConfigNumReservedSlots.get(i), this.mNumRunningJobs.get(i) + this.mNumStartingJobs.get(i) + this.mNumPendingJobs.get(i));
            if (max < this.mNumActuallyReservedSlots.get(i)) {
                this.mNumActuallyReservedSlots.put(i, max);
                int i2 = 0;
                for (int i3 = 0; i3 < this.mNumActuallyReservedSlots.size(); i3++) {
                    int keyAt = this.mNumActuallyReservedSlots.keyAt(i3);
                    if (i2 == 0 || keyAt < i2) {
                        int i4 = this.mNumRunningJobs.get(keyAt) + this.mNumStartingJobs.get(keyAt) + this.mNumPendingJobs.get(keyAt);
                        if (this.mNumActuallyReservedSlots.valueAt(i3) < this.mConfigAbsoluteMaxSlots.get(keyAt) && i4 > this.mNumActuallyReservedSlots.valueAt(i3)) {
                            i2 = keyAt;
                        }
                    }
                }
                if (i2 != 0) {
                    this.mNumActuallyReservedSlots.put(i2, this.mNumActuallyReservedSlots.get(i2) + 1);
                } else {
                    this.mNumUnspecializedRemaining++;
                }
            }
        }

        void onJobStarted(int i) {
            this.mNumRunningJobs.put(i, this.mNumRunningJobs.get(i) + 1);
            int i2 = this.mNumStartingJobs.get(i);
            if (i2 == 0) {
                android.util.Slog.e(com.android.server.job.JobConcurrencyManager.TAG, "# stated jobs for " + i + " went negative.");
                return;
            }
            this.mNumStartingJobs.put(i, i2 - 1);
        }

        void onJobFinished(int i) {
            int i2 = this.mNumRunningJobs.get(i) - 1;
            if (i2 < 0) {
                android.util.Slog.e(com.android.server.job.JobConcurrencyManager.TAG, "# running jobs for " + i + " went negative.");
                return;
            }
            this.mNumRunningJobs.put(i, i2);
            maybeAdjustReservations(i);
        }

        void onCountDone() {
            this.mNumUnspecializedRemaining = this.mConfigMaxTotal;
            for (int i = 1; i < 127; i <<= 1) {
                int i2 = this.mNumRunningJobs.get(i);
                this.mRecycledReserved.put(i, i2);
                this.mNumUnspecializedRemaining -= i2;
            }
            for (int i3 = 1; i3 < 127; i3 <<= 1) {
                int i4 = this.mNumRunningJobs.get(i3) + this.mNumPendingJobs.get(i3);
                int i5 = this.mRecycledReserved.get(i3);
                int max = java.lang.Math.max(0, java.lang.Math.min(this.mNumUnspecializedRemaining, java.lang.Math.min(i4, this.mConfigNumReservedSlots.get(i3) - i5)));
                this.mRecycledReserved.put(i3, i5 + max);
                this.mNumUnspecializedRemaining -= max;
            }
            for (int i6 = 1; i6 < 127; i6 <<= 1) {
                int i7 = this.mNumRunningJobs.get(i6) + this.mNumPendingJobs.get(i6);
                int i8 = this.mRecycledReserved.get(i6);
                int max2 = java.lang.Math.max(0, java.lang.Math.min(this.mNumUnspecializedRemaining, java.lang.Math.min(this.mConfigAbsoluteMaxSlots.get(i6), i7) - i8));
                this.mNumActuallyReservedSlots.put(i6, i8 + max2);
                this.mNumUnspecializedRemaining -= max2;
            }
        }

        int canJobStart(int i) {
            for (int i2 = 1; i2 <= i; i2 <<= 1) {
                if ((i & i2) == i2) {
                    if (this.mNumRunningJobs.get(i2) + this.mNumStartingJobs.get(i2) < java.lang.Math.min(this.mConfigAbsoluteMaxSlots.get(i2), this.mNumActuallyReservedSlots.get(i2) + this.mNumUnspecializedRemaining)) {
                        return i2;
                    }
                }
            }
            return 0;
        }

        int canJobStart(int i, int i2) {
            boolean z;
            int i3 = this.mNumRunningJobs.get(i2);
            if (i2 != 0 && i3 > 0) {
                this.mNumRunningJobs.put(i2, i3 - 1);
                this.mNumUnspecializedRemaining++;
                z = true;
            } else {
                z = false;
            }
            int canJobStart = canJobStart(i);
            if (z) {
                this.mNumRunningJobs.put(i2, i3);
                this.mNumUnspecializedRemaining--;
            }
            return canJobStart;
        }

        int getPendingJobCount(int i) {
            return this.mNumPendingJobs.get(i, 0);
        }

        int getRunningJobCount(int i) {
            return this.mNumRunningJobs.get(i, 0);
        }

        boolean isOverTypeLimit(int i) {
            return getRunningJobCount(i) > this.mConfigAbsoluteMaxSlots.get(i);
        }

        public java.lang.String toString() {
            return "Config={tot=" + this.mConfigMaxTotal + " mins=" + this.mConfigNumReservedSlots + " maxs=" + this.mConfigAbsoluteMaxSlots + "}, act res=" + this.mNumActuallyReservedSlots + ", Pending=" + this.mNumPendingJobs + ", Running=" + this.mNumRunningJobs + ", Staged=" + this.mNumStartingJobs + ", # unspecialized remaining=" + this.mNumUnspecializedRemaining;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class PackageStats {
        public int numRunningEj;
        public int numRunningRegular;
        public int numStagedEj;
        public int numStagedRegular;
        public java.lang.String packageName;
        public int userId;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: -$$Nest$mdumpLocked, reason: not valid java name */
        public static /* bridge */ /* synthetic */ void m4413$$Nest$mdumpLocked(com.android.server.job.JobConcurrencyManager.PackageStats packageStats, android.util.IndentingPrintWriter indentingPrintWriter) {
            packageStats.dumpLocked(indentingPrintWriter);
        }

        PackageStats() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPackage(int i, @android.annotation.NonNull java.lang.String str) {
            this.userId = i;
            this.packageName = str;
            this.numRunningRegular = 0;
            this.numRunningEj = 0;
            resetStagedCount();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resetStagedCount() {
            this.numStagedRegular = 0;
            this.numStagedEj = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void adjustRunningCount(boolean z, boolean z2) {
            if (z2) {
                this.numRunningEj = java.lang.Math.max(0, this.numRunningEj + (z ? 1 : -1));
            } else {
                this.numRunningRegular = java.lang.Math.max(0, this.numRunningRegular + (z ? 1 : -1));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void adjustStagedCount(boolean z, boolean z2) {
            if (z2) {
                this.numStagedEj = java.lang.Math.max(0, this.numStagedEj + (z ? 1 : -1));
            } else {
                this.numStagedRegular = java.lang.Math.max(0, this.numStagedRegular + (z ? 1 : -1));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print("PackageStats{");
            indentingPrintWriter.print(this.userId);
            indentingPrintWriter.print("-");
            indentingPrintWriter.print(this.packageName);
            indentingPrintWriter.print("#runEJ", java.lang.Integer.valueOf(this.numRunningEj));
            indentingPrintWriter.print("#runReg", java.lang.Integer.valueOf(this.numRunningRegular));
            indentingPrintWriter.print("#stagedEJ", java.lang.Integer.valueOf(this.numStagedEj));
            indentingPrintWriter.print("#stagedReg", java.lang.Integer.valueOf(this.numStagedRegular));
            indentingPrintWriter.println("}");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class ContextAssignment {
        public com.android.server.job.JobServiceContext context;
        public com.android.server.job.controllers.JobStatus newJob;
        public java.lang.String preemptReason;
        public java.lang.String shouldStopJobReason;
        public long timeUntilStoppableMs;
        public int preferredUid = -1;
        public int workType = 0;
        public int preemptReasonCode = 0;
        public int newWorkType = 0;

        ContextAssignment() {
        }

        void clear() {
            this.context = null;
            this.preferredUid = -1;
            this.workType = 0;
            this.preemptReason = null;
            this.preemptReasonCode = 0;
            this.timeUntilStoppableMs = 0L;
            this.shouldStopJobReason = null;
            this.newJob = null;
            this.newWorkType = 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class AssignmentInfo {
        public long minPreferredUidOnlyWaitingTimeMs;
        public int numRunningEj;
        public int numRunningImmediacyPrivileged;
        public int numRunningReg;
        public int numRunningUi;

        AssignmentInfo() {
        }

        void clear() {
            this.minPreferredUidOnlyWaitingTimeMs = 0L;
            this.numRunningImmediacyPrivileged = 0;
            this.numRunningUi = 0;
            this.numRunningEj = 0;
            this.numRunningReg = 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addRunningJobForTesting(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.JobServiceContext createNewJobServiceContext;
        this.mRunningJobs.add(jobStatus);
        getPackageStatsForTesting(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName()).adjustRunningCount(true, jobStatus.shouldTreatAsExpeditedJob());
        if (this.mIdleContexts.size() > 0) {
            createNewJobServiceContext = this.mIdleContexts.removeAt(this.mIdleContexts.size() - 1);
        } else {
            createNewJobServiceContext = createNewJobServiceContext();
        }
        createNewJobServiceContext.executeRunnableJob(jobStatus, this.mWorkCountTracker.canJobStart(getJobWorkTypes(jobStatus)));
        this.mActiveServices.add(createNewJobServiceContext);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getPackageConcurrencyLimitEj() {
        return this.mPkgConcurrencyLimitEj;
    }

    int getPackageConcurrencyLimitRegular() {
        return this.mPkgConcurrencyLimitRegular;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.JobConcurrencyManager.PackageStats getPackageStatsForTesting(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.job.JobConcurrencyManager.PackageStats pkgStatsLocked = getPkgStatsLocked(i, str);
        this.mActivePkgStats.add(i, str, pkgStatsLocked);
        return pkgStatsLocked;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        @android.annotation.NonNull
        com.android.server.job.JobServiceContext createJobServiceContext(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.JobConcurrencyManager jobConcurrencyManager, com.android.server.job.JobNotificationCoordinator jobNotificationCoordinator, com.android.internal.app.IBatteryStats iBatteryStats, com.android.server.job.JobPackageTracker jobPackageTracker, android.os.Looper looper) {
            return new com.android.server.job.JobServiceContext(jobSchedulerService, jobConcurrencyManager, jobNotificationCoordinator, iBatteryStats, jobPackageTracker, looper);
        }
    }
}
