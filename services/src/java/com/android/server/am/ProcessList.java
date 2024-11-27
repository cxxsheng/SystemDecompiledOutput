package com.android.server.am;

/* loaded from: classes.dex */
public final class ProcessList {
    static final java.lang.String ANDROID_APP_DATA_ISOLATION_ENABLED_PROPERTY = "persist.zygote.app_data_isolation";
    static final java.lang.String ANDROID_VOLD_APP_DATA_ISOLATION_ENABLED_PROPERTY = "persist.sys.vold_app_data_isolation_enabled";
    private static final java.lang.String APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS = ":isSdkSandboxAudit";
    private static final java.lang.String APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS = ":isSdkSandboxNext";
    private static final long APP_DATA_DIRECTORY_ISOLATION = 143937733;
    public static final int BACKUP_APP_ADJ = 300;
    static final int CACHED_APP_IMPORTANCE_LEVELS = 5;
    public static final int CACHED_APP_LMK_FIRST_ADJ = 950;
    public static final int CACHED_APP_MAX_ADJ = 999;
    public static final int CACHED_APP_MIN_ADJ = 900;
    private static final boolean DEFAULT_APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS = false;
    private static final boolean DEFAULT_APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS = false;
    public static final int FOREGROUND_APP_ADJ = 0;
    static final int FREEZER_CUTOFF_ADJ = 900;
    public static final int HEAVY_WEIGHT_APP_ADJ = 400;
    public static final int HOME_APP_ADJ = 600;
    public static final int INVALID_ADJ = -10000;
    private static final long LMKD_RECONNECT_DELAY_MS = 1000;
    static final int LMK_ASYNC_EVENT_KILL = 0;
    static final int LMK_ASYNC_EVENT_STAT = 1;
    static final byte LMK_GETKILLCNT = 4;
    static final byte LMK_KILL_OCCURRED = 8;
    static final byte LMK_PROCKILL = 6;
    static final byte LMK_PROCPRIO = 1;
    static final byte LMK_PROCPURGE = 3;
    static final byte LMK_PROCREMOVE = 2;
    static final byte LMK_START_MONITORING = 9;
    static final byte LMK_SUBSCRIBE = 5;
    static final byte LMK_TARGET = 0;
    static final byte LMK_UPDATE_PROPS = 7;
    private static final int MAX_ZYGOTE_UNSOLICITED_MESSAGE_SIZE = 16;
    static final int MIN_CACHED_APPS = 2;
    public static final int NATIVE_ADJ = -1000;

    @com.android.internal.annotations.VisibleForTesting
    static final int NETWORK_STATE_BLOCK = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int NETWORK_STATE_NO_CHANGE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int NETWORK_STATE_UNBLOCK = 2;
    public static final int PERCEPTIBLE_APP_ADJ = 200;
    public static final int PERCEPTIBLE_LOW_APP_ADJ = 250;
    public static final int PERCEPTIBLE_MEDIUM_APP_ADJ = 225;
    public static final int PERCEPTIBLE_RECENT_FOREGROUND_APP_ADJ = 50;
    public static final int PERSISTENT_PROC_ADJ = -800;
    public static final int PERSISTENT_SERVICE_ADJ = -700;
    public static final int PREVIOUS_APP_ADJ = 700;
    public static final int PROC_MEM_CACHED = 4;
    public static final int PROC_MEM_IMPORTANT = 2;
    public static final int PROC_MEM_NUM = 5;
    public static final int PROC_MEM_PERSISTENT = 0;
    public static final int PROC_MEM_SERVICE = 3;
    public static final int PROC_MEM_TOP = 1;
    private static final java.lang.String PROPERTY_APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS = "apply_sdk_sandbox_audit_restrictions";
    private static final java.lang.String PROPERTY_APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS = "apply_sdk_sandbox_next_restrictions";
    private static final java.lang.String PROPERTY_USE_APP_IMAGE_STARTUP_CACHE = "persist.device_config.runtime_native.use_app_image_startup_cache";
    public static final int PSS_ALL_INTERVAL = 1200000;
    private static final int PSS_FIRST_ASLEEP_BACKGROUND_INTERVAL = 30000;
    private static final int PSS_FIRST_ASLEEP_CACHED_INTERVAL = 60000;
    private static final int PSS_FIRST_ASLEEP_PERSISTENT_INTERVAL = 60000;
    private static final int PSS_FIRST_ASLEEP_TOP_INTERVAL = 20000;
    private static final int PSS_FIRST_BACKGROUND_INTERVAL = 20000;
    private static final int PSS_FIRST_CACHED_INTERVAL = 20000;
    private static final int PSS_FIRST_PERSISTENT_INTERVAL = 30000;
    private static final int PSS_FIRST_TOP_INTERVAL = 10000;
    public static final int PSS_MAX_INTERVAL = 3600000;
    public static final int PSS_MIN_TIME_FROM_STATE_CHANGE = 15000;
    public static final int PSS_SAFE_TIME_FROM_STATE_CHANGE = 1000;
    private static final int PSS_SAME_CACHED_INTERVAL = 600000;
    private static final int PSS_SAME_IMPORTANT_INTERVAL = 600000;
    private static final int PSS_SAME_PERSISTENT_INTERVAL = 600000;
    private static final int PSS_SAME_SERVICE_INTERVAL = 300000;
    private static final int PSS_SAME_TOP_INTERVAL = 60000;
    private static final int PSS_TEST_FIRST_BACKGROUND_INTERVAL = 5000;
    private static final int PSS_TEST_FIRST_TOP_INTERVAL = 3000;
    public static final int PSS_TEST_MIN_TIME_FROM_STATE_CHANGE = 10000;
    private static final int PSS_TEST_SAME_BACKGROUND_INTERVAL = 15000;
    private static final int PSS_TEST_SAME_IMPORTANT_INTERVAL = 10000;
    static final int SCHED_GROUP_BACKGROUND = 0;
    static final int SCHED_GROUP_DEFAULT = 2;
    static final int SCHED_GROUP_RESTRICTED = 1;
    public static final int SCHED_GROUP_TOP_APP = 3;
    static final int SCHED_GROUP_TOP_APP_BOUND = 4;
    static final int SCHED_GROUP_UNDEFINED = Integer.MIN_VALUE;
    public static final int SERVICE_ADJ = 500;
    public static final int SERVICE_B_ADJ = 800;
    public static final int SYSTEM_ADJ = -900;
    static final java.lang.String TAG = "ActivityManager";
    static final java.lang.String TAG_PROCESS_OBSERVERS = "ActivityManager";
    static final int TRIM_CRITICAL_THRESHOLD = 3;
    static final int TRIM_LOW_THRESHOLD = 5;
    public static final int UNKNOWN_ADJ = 1001;
    private static final java.lang.String UNSOL_ZYGOTE_MSG_SOCKET_PATH = "/data/system/unsolzygotesocket";
    public static final int VISIBLE_APP_ADJ = 100;
    static final int VISIBLE_APP_LAYER_MAX = 99;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    com.android.server.am.ActiveUids mActiveUids;
    private java.util.ArrayList<java.lang.String> mAppDataIsolationAllowlistedApps;
    private long mCachedRestoreLevel;
    private boolean mHaveDisplaySize;
    com.android.server.am.ProcessList.ImperceptibleKillRunner mImperceptibleKillRunner;
    com.android.server.am.ActivityManagerGlobalLock mProcLock;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private com.android.server.am.ProcessList.ProcessListSettingsListener mProcessListSettingsListener;
    private android.net.LocalSocket mSystemServerSocketForZygote;
    private final long mTotalMemMb;
    static final int PAGE_SIZE = (int) android.system.Os.sysconf(android.system.OsConstants._SC_PAGESIZE);
    static com.android.server.am.ProcessList.KillHandler sKillHandler = null;
    static com.android.server.ServiceThread sKillThread = null;
    private static com.android.server.am.LmkdConnection sLmkdConnection = null;
    private static com.android.server.am.OomConnection sOomConnection = null;
    private static final int[] sProcStateToProcMem = {0, 0, 1, 2, 1, 2, 2, 2, 2, 2, 3, 4, 1, 2, 4, 4, 4, 4, 4, 4};
    private static final long[] sFirstAwakePssTimes = {30000, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, 20000, 20000, 20000};
    private static final long[] sSameAwakePssTimes = {600000, 60000, 600000, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, 600000};
    private static final long[] sFirstAsleepPssTimes = {60000, 20000, 30000, 30000, 60000};
    private static final long[] sSameAsleepPssTimes = {600000, 60000, 600000, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, 600000};
    private static final long[] sTestFirstPssTimes = {com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS, 5000, 5000, 5000};
    private static final long[] sTestSamePssTimes = {15000, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, 15000, 15000};
    com.android.server.am.ActivityManagerService mService = null;
    private final int[] mOomAdj = {0, 100, 200, 250, 900, CACHED_APP_LMK_FIRST_ADJ};
    private final int[] mOomMinFreeLow = {12288, 18432, 24576, 36864, 43008, 49152};
    private final int[] mOomMinFreeHigh = {73728, 92160, 110592, 129024, 147456, 184320};
    private final int[] mOomMinFree = new int[this.mOomAdj.length];
    private boolean mOomLevelsSet = false;
    private boolean mAppDataIsolationEnabled = false;
    private boolean mVoldAppDataIsolationEnabled = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    final java.lang.StringBuilder mStringBuilder = new java.lang.StringBuilder(256);

    @com.android.internal.annotations.VisibleForTesting
    volatile long mProcStateSeqCounter = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mProcStartSeqCounter = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    final android.util.LongSparseArray<com.android.server.am.ProcessRecord> mPendingStarts = new android.util.LongSparseArray<>();

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private final java.util.ArrayList<com.android.server.am.ProcessRecord> mLruProcesses = new java.util.ArrayList<>();

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mLruProcessActivityStart = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mLruProcessServiceStart = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mLruSeq = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    final android.util.SparseArray<com.android.server.am.ProcessRecord> mIsolatedProcesses = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mService"})
    final com.android.internal.app.ProcessMap<android.os.AppZygote> mAppZygotes = new com.android.internal.app.ProcessMap<>();

    @com.android.internal.annotations.GuardedBy({"mAppStartInfoTracker"})
    private final com.android.server.am.AppStartInfoTracker mAppStartInfoTracker = new com.android.server.am.AppStartInfoTracker();

    @com.android.internal.annotations.GuardedBy({"mService"})
    final android.util.SparseArray<java.util.ArrayList<com.android.server.am.ProcessRecord>> mSdkSandboxes = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mAppExitInfoTracker"})
    final com.android.server.am.AppExitInfoTracker mAppExitInfoTracker = new com.android.server.am.AppExitInfoTracker();

    @com.android.internal.annotations.GuardedBy({"mService"})
    final android.util.ArrayMap<android.os.AppZygote, java.util.ArrayList<com.android.server.am.ProcessRecord>> mAppZygoteProcesses = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mService"})
    final android.util.ArraySet<com.android.server.am.ProcessRecord> mAppsInBackgroundRestricted = new android.util.ArraySet<>();
    private com.android.server.compat.PlatformCompat mPlatformCompat = null;
    private final byte[] mZygoteUnsolicitedMessage = new byte[16];
    private final int[] mZygoteSigChldMessage = new int[3];

    @com.android.internal.annotations.GuardedBy({"mService"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.ProcessList.IsolatedUidRange mGlobalIsolatedUids = new com.android.server.am.ProcessList.IsolatedUidRange(99000, 99999);

    @com.android.internal.annotations.GuardedBy({"mService"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.ProcessList.IsolatedUidRangeAllocator mAppIsolatedUidRangeAllocator = new com.android.server.am.ProcessList.IsolatedUidRangeAllocator(90000, 98999, 100);

    @com.android.internal.annotations.GuardedBy({"mService"})
    final java.util.ArrayList<com.android.server.am.ProcessRecord> mRemovedProcesses = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mService"})
    final com.android.internal.app.ProcessMap<com.android.server.am.ProcessRecord> mDyingProcesses = new com.android.internal.app.ProcessMap<>();
    private final android.os.RemoteCallbackList<android.app.IProcessObserver> mProcessObservers = new android.os.RemoteCallbackList<>();
    private com.android.server.am.ActivityManagerService.ProcessChangeItem[] mActiveProcessChanges = new com.android.server.am.ActivityManagerService.ProcessChangeItem[5];

    @com.android.internal.annotations.GuardedBy({"mProcessChangeLock"})
    private final java.util.ArrayList<com.android.server.am.ActivityManagerService.ProcessChangeItem> mPendingProcessChanges = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mProcessChangeLock"})
    final java.util.ArrayList<com.android.server.am.ActivityManagerService.ProcessChangeItem> mAvailProcessChanges = new java.util.ArrayList<>();
    private final java.lang.Object mProcessChangeLock = new java.lang.Object();

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private final com.android.server.am.ProcessList.MyProcessMap mProcessNames = new com.android.server.am.ProcessList.MyProcessMap();

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.am.ProcessList.ProcessListSettingsListener getProcessListSettingsListener() {
        com.android.server.am.ProcessList.ProcessListSettingsListener processListSettingsListener;
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (this.mProcessListSettingsListener == null) {
                    this.mProcessListSettingsListener = new com.android.server.am.ProcessList.ProcessListSettingsListener(this.mService.mContext);
                    this.mProcessListSettingsListener.registerObserver();
                }
                processListSettingsListener = this.mProcessListSettingsListener;
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return processListSettingsListener;
    }

    static class ProcessListSettingsListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private final android.content.Context mContext;
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mSdkSandboxApplyRestrictionsAudit = android.provider.DeviceConfig.getBoolean("adservices", com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS, false);

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mSdkSandboxApplyRestrictionsNext = android.provider.DeviceConfig.getBoolean("adservices", com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS, false);

        ProcessListSettingsListener(android.content.Context context) {
            this.mContext = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerObserver() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("adservices", this.mContext.getMainExecutor(), this);
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        void unregisterObserver() {
            android.provider.DeviceConfig.removeOnPropertiesChangedListener(this);
        }

        boolean applySdkSandboxRestrictionsAudit() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mSdkSandboxApplyRestrictionsAudit;
            }
            return z;
        }

        boolean applySdkSandboxRestrictionsNext() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mSdkSandboxApplyRestrictionsNext;
            }
            return z;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            char c;
            synchronized (this.mLock) {
                try {
                    for (java.lang.String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -460166235:
                                    if (str.equals(com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS)) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1346273945:
                                    if (str.equals(com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS)) {
                                        c = 0;
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
                                    this.mSdkSandboxApplyRestrictionsAudit = properties.getBoolean(com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS, false);
                                    break;
                                case 1:
                                    this.mSdkSandboxApplyRestrictionsNext = properties.getBoolean(com.android.server.am.ProcessList.PROPERTY_APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS, false);
                                    break;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class IsolatedUidRange {

        @com.android.internal.annotations.VisibleForTesting
        public final int mFirstUid;

        @com.android.internal.annotations.VisibleForTesting
        public final int mLastUid;

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        private int mNextUid;

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        private final android.util.SparseBooleanArray mUidUsed = new android.util.SparseBooleanArray();

        IsolatedUidRange(int i, int i2) {
            this.mFirstUid = i;
            this.mLastUid = i2;
            this.mNextUid = i;
        }

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        int allocateIsolatedUidLocked(int i) {
            int i2 = (this.mLastUid - this.mFirstUid) + 1;
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.mNextUid < this.mFirstUid || this.mNextUid > this.mLastUid) {
                    this.mNextUid = this.mFirstUid;
                }
                int uid = android.os.UserHandle.getUid(i, this.mNextUid);
                this.mNextUid++;
                if (!this.mUidUsed.get(uid, false)) {
                    this.mUidUsed.put(uid, true);
                    return uid;
                }
            }
            return -1;
        }

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        void freeIsolatedUidLocked(int i) {
            this.mUidUsed.delete(i);
        }
    }

    final class IsolatedUidRangeAllocator {

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        private final com.android.internal.app.ProcessMap<com.android.server.am.ProcessList.IsolatedUidRange> mAppRanges = new com.android.internal.app.ProcessMap<>();

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        private final java.util.BitSet mAvailableUidRanges;
        private final int mFirstUid;
        private final int mNumUidRanges;
        private final int mNumUidsPerRange;

        IsolatedUidRangeAllocator(int i, int i2, int i3) {
            this.mFirstUid = i;
            this.mNumUidsPerRange = i3;
            this.mNumUidRanges = ((i2 - i) + 1) / i3;
            this.mAvailableUidRanges = new java.util.BitSet(this.mNumUidRanges);
            this.mAvailableUidRanges.set(0, this.mNumUidRanges);
        }

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        com.android.server.am.ProcessList.IsolatedUidRange getIsolatedUidRangeLocked(java.lang.String str, int i) {
            return (com.android.server.am.ProcessList.IsolatedUidRange) this.mAppRanges.get(str, i);
        }

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        com.android.server.am.ProcessList.IsolatedUidRange getOrCreateIsolatedUidRangeLocked(java.lang.String str, int i) {
            com.android.server.am.ProcessList.IsolatedUidRange isolatedUidRangeLocked = getIsolatedUidRangeLocked(str, i);
            if (isolatedUidRangeLocked == null) {
                int nextSetBit = this.mAvailableUidRanges.nextSetBit(0);
                if (nextSetBit < 0) {
                    return null;
                }
                this.mAvailableUidRanges.clear(nextSetBit);
                com.android.server.am.ProcessList.IsolatedUidRange isolatedUidRange = com.android.server.am.ProcessList.this.new IsolatedUidRange(this.mFirstUid + (nextSetBit * this.mNumUidsPerRange), (this.mNumUidsPerRange + r1) - 1);
                this.mAppRanges.put(str, i, isolatedUidRange);
                return isolatedUidRange;
            }
            return isolatedUidRangeLocked;
        }

        @com.android.internal.annotations.GuardedBy({"ProcessList.this.mService"})
        void freeUidRangeLocked(android.content.pm.ApplicationInfo applicationInfo) {
            com.android.server.am.ProcessList.IsolatedUidRange isolatedUidRange = (com.android.server.am.ProcessList.IsolatedUidRange) this.mAppRanges.get(applicationInfo.processName, applicationInfo.uid);
            if (isolatedUidRange != null) {
                this.mAvailableUidRanges.set((isolatedUidRange.mFirstUid - this.mFirstUid) / this.mNumUidsPerRange);
                this.mAppRanges.remove(applicationInfo.processName, applicationInfo.uid);
            }
        }
    }

    final class MyProcessMap extends com.android.internal.app.ProcessMap<com.android.server.am.ProcessRecord> {
        MyProcessMap() {
        }

        public com.android.server.am.ProcessRecord put(java.lang.String str, int i, com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) super.put(str, i, processRecord);
            com.android.server.am.ProcessList.this.mService.mAtmInternal.onProcessAdded(processRecord2.getWindowProcessController());
            return processRecord2;
        }

        /* renamed from: remove, reason: merged with bridge method [inline-methods] */
        public com.android.server.am.ProcessRecord m1496remove(java.lang.String str, int i) {
            com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) super.remove(str, i);
            com.android.server.am.ProcessList.this.mService.mAtmInternal.onProcessRemoved(str, i);
            return processRecord;
        }
    }

    final class KillHandler extends android.os.Handler {
        static final int KILL_PROCESS_GROUP_MSG = 4000;
        static final int LMKD_RECONNECT_MSG = 4001;

        public KillHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case KILL_PROCESS_GROUP_MSG /* 4000 */:
                    android.os.Trace.traceBegin(64L, "killProcessGroup");
                    android.os.Process.killProcessGroup(message.arg1, message.arg2);
                    android.os.Trace.traceEnd(64L);
                    break;
                case LMKD_RECONNECT_MSG /* 4001 */:
                    if (!com.android.server.am.ProcessList.sLmkdConnection.connect()) {
                        android.util.Slog.i("ActivityManager", "Failed to connect to lmkd, retry after 1000 ms");
                        com.android.server.am.ProcessList.sKillHandler.sendMessageDelayed(com.android.server.am.ProcessList.sKillHandler.obtainMessage(LMKD_RECONNECT_MSG), 1000L);
                        break;
                    }
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    ProcessList() {
        com.android.internal.util.MemInfoReader memInfoReader = new com.android.internal.util.MemInfoReader();
        memInfoReader.readMemInfo();
        this.mTotalMemMb = memInfoReader.getTotalSize() / 1048576;
        updateOomLevels(0, 0, false);
    }

    void init(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.ActiveUids activeUids, com.android.server.compat.PlatformCompat platformCompat) {
        this.mService = activityManagerService;
        this.mActiveUids = activeUids;
        this.mPlatformCompat = platformCompat;
        this.mProcLock = activityManagerService.mProcLock;
        this.mAppDataIsolationEnabled = android.os.SystemProperties.getBoolean(ANDROID_APP_DATA_ISOLATION_ENABLED_PROPERTY, true);
        this.mVoldAppDataIsolationEnabled = android.os.SystemProperties.getBoolean(ANDROID_VOLD_APP_DATA_ISOLATION_ENABLED_PROPERTY, false);
        this.mAppDataIsolationAllowlistedApps = new java.util.ArrayList<>(com.android.server.SystemConfig.getInstance().getAppDataIsolationWhitelistedApps());
        if (sKillHandler == null) {
            sKillThread = new com.android.server.ServiceThread("ActivityManager:kill", 10, true);
            sKillThread.start();
            sKillHandler = new com.android.server.am.ProcessList.KillHandler(sKillThread.getLooper());
            sOomConnection = new com.android.server.am.OomConnection(new com.android.server.am.OomConnection.OomConnectionListener() { // from class: com.android.server.am.ProcessList.1
                @Override // com.android.server.am.OomConnection.OomConnectionListener
                public void handleOomEvent(android.os.OomKillRecord[] oomKillRecordArr) {
                    for (android.os.OomKillRecord oomKillRecord : oomKillRecordArr) {
                        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.ProcessList.this.mProcLock;
                        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                        synchronized (activityManagerGlobalLock) {
                            try {
                                com.android.server.am.ProcessList.this.noteAppKill(oomKillRecord.getPid(), oomKillRecord.getUid(), 3, 30, "oom");
                                oomKillRecord.logKillOccurred();
                            } catch (java.lang.Throwable th) {
                                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                throw th;
                            }
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
            });
            sLmkdConnection = new com.android.server.am.LmkdConnection(sKillThread.getLooper().getQueue(), new com.android.server.am.LmkdConnection.LmkdConnectionListener() { // from class: com.android.server.am.ProcessList.2
                @Override // com.android.server.am.LmkdConnection.LmkdConnectionListener
                public boolean onConnect(java.io.OutputStream outputStream) {
                    android.util.Slog.i("ActivityManager", "Connection with lmkd established");
                    return com.android.server.am.ProcessList.this.onLmkdConnect(outputStream);
                }

                @Override // com.android.server.am.LmkdConnection.LmkdConnectionListener
                public void onDisconnect() {
                    android.util.Slog.w("ActivityManager", "Lost connection to lmkd");
                    com.android.server.am.ProcessList.sKillHandler.sendMessageDelayed(com.android.server.am.ProcessList.sKillHandler.obtainMessage(4001), 1000L);
                }

                @Override // com.android.server.am.LmkdConnection.LmkdConnectionListener
                public boolean isReplyExpected(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2, int i) {
                    return i == byteBuffer.array().length && byteBuffer2.getInt(0) == byteBuffer.getInt(0);
                }

                @Override // com.android.server.am.LmkdConnection.LmkdConnectionListener
                public boolean handleUnsolicitedMessage(java.io.DataInputStream dataInputStream, int i) {
                    if (i < 4) {
                        return false;
                    }
                    try {
                        switch (dataInputStream.readInt()) {
                            case 6:
                                if (i == 12) {
                                    com.android.server.am.ProcessList.this.mAppExitInfoTracker.scheduleNoteLmkdProcKilled(dataInputStream.readInt(), dataInputStream.readInt());
                                    break;
                                }
                                break;
                            case 8:
                                if (i >= 80) {
                                    android.util.Pair<java.lang.Integer, java.lang.Integer> pair = com.android.server.am.ActiveServices.sNumForegroundServices.get();
                                    com.android.server.am.LmkdStatsReporter.logKillOccurred(dataInputStream, ((java.lang.Integer) pair.first).intValue(), ((java.lang.Integer) pair.second).intValue());
                                    break;
                                }
                                break;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e("ActivityManager", "Invalid buffer data. Failed to log LMK_KILL_OCCURRED");
                        return false;
                    }
                    return false;
                }
            });
            this.mSystemServerSocketForZygote = createSystemServerSocketForZygote();
            if (this.mSystemServerSocketForZygote != null) {
                sKillHandler.getLooper().getQueue().addOnFileDescriptorEventListener(this.mSystemServerSocketForZygote.getFileDescriptor(), 1, new android.os.MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda6
                    @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                    public final int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor, int i) {
                        int handleZygoteMessages;
                        handleZygoteMessages = com.android.server.am.ProcessList.this.handleZygoteMessages(fileDescriptor, i);
                        return handleZygoteMessages;
                    }
                });
            }
            this.mAppStartInfoTracker.init(this.mService);
            this.mAppExitInfoTracker.init(this.mService);
            this.mImperceptibleKillRunner = new com.android.server.am.ProcessList.ImperceptibleKillRunner(sKillThread.getLooper());
        }
    }

    void onSystemReady() {
        this.mAppStartInfoTracker.onSystemReady();
        this.mAppExitInfoTracker.onSystemReady();
    }

    void applyDisplaySize(com.android.server.wm.WindowManagerService windowManagerService) {
        if (!this.mHaveDisplaySize) {
            android.graphics.Point point = new android.graphics.Point();
            windowManagerService.getBaseDisplaySize(0, point);
            if (point.x != 0 && point.y != 0) {
                updateOomLevels(point.x, point.y, true);
                this.mHaveDisplaySize = true;
            }
        }
    }

    java.util.Map<java.lang.Integer, java.lang.String> getProcessesWithPendingBindMounts(int i) {
        java.util.HashMap hashMap = new java.util.HashMap();
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                    com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
                    if (processRecord.userId == i && processRecord.isBindMountPending()) {
                        int pid = processRecord.getPid();
                        if (pid == 0) {
                            throw new java.lang.IllegalStateException("Pending process is not started yet,retry later");
                        }
                        hashMap.put(java.lang.Integer.valueOf(pid), processRecord.info.packageName);
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return hashMap;
    }

    private void updateOomLevels(int i, int i2, boolean z) {
        float f = (this.mTotalMemMb - 350) / 350.0f;
        int i3 = i * i2;
        float f2 = (i3 - 384000) / 640000;
        if (f <= f2) {
            f = f2;
        }
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        int integer = android.content.res.Resources.getSystem().getInteger(android.R.integer.config_lowBatteryAutoTriggerDefaultLevel);
        int integer2 = android.content.res.Resources.getSystem().getInteger(android.R.integer.config_longPressOnStemPrimaryBehavior);
        boolean z2 = android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0;
        for (int i4 = 0; i4 < this.mOomAdj.length; i4++) {
            int i5 = this.mOomMinFreeLow[i4];
            int i6 = this.mOomMinFreeHigh[i4];
            if (z2) {
                if (i4 == 4) {
                    i6 = (i6 * 3) / 2;
                } else if (i4 == 5) {
                    i6 = (i6 * 7) / 4;
                }
            }
            this.mOomMinFree[i4] = (int) (i5 + ((i6 - i5) * f));
        }
        if (integer2 >= 0) {
            for (int i7 = 0; i7 < this.mOomAdj.length; i7++) {
                this.mOomMinFree[i7] = (int) ((integer2 * this.mOomMinFree[i7]) / this.mOomMinFree[this.mOomAdj.length - 1]);
            }
        }
        if (integer != 0) {
            for (int i8 = 0; i8 < this.mOomAdj.length; i8++) {
                int[] iArr = this.mOomMinFree;
                iArr[i8] = iArr[i8] + ((int) ((integer * this.mOomMinFree[i8]) / this.mOomMinFree[this.mOomAdj.length - 1]));
                if (this.mOomMinFree[i8] < 0) {
                    this.mOomMinFree[i8] = 0;
                }
            }
        }
        this.mCachedRestoreLevel = (getMemLevel(999) / 1024) / 3;
        int i9 = ((i3 * 4) * 3) / 1024;
        int integer3 = android.content.res.Resources.getSystem().getInteger(android.R.integer.config_externalDisplayPeakHeight);
        int integer4 = android.content.res.Resources.getSystem().getInteger(android.R.integer.config_esim_bootstrap_data_limit_bytes);
        if (integer4 >= 0) {
            i9 = integer4;
        }
        if (integer3 != 0 && (i9 = i9 + integer3) < 0) {
            i9 = 0;
        }
        if (z) {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(((this.mOomAdj.length * 2) + 1) * 4);
            allocate.putInt(0);
            for (int i10 = 0; i10 < this.mOomAdj.length; i10++) {
                allocate.putInt((this.mOomMinFree[i10] * 1024) / PAGE_SIZE);
                allocate.putInt(this.mOomAdj[i10]);
            }
            writeLmkd(allocate, null);
            android.os.SystemProperties.set("sys.sysctl.extra_free_kbytes", java.lang.Integer.toString(i9));
            this.mOomLevelsSet = true;
        }
    }

    public static int computeEmptyProcessLimit(int i) {
        return i / 2;
    }

    private static java.lang.String buildOomTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, boolean z) {
        int i3 = i - i2;
        if (i3 == 0) {
            if (z) {
                return str2;
            }
            if (str3 == null) {
                return str;
            }
            return str + str3;
        }
        if (i3 < 10) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append(z ? "+" : "+ ");
            sb.append(java.lang.Integer.toString(i3));
            return sb.toString();
        }
        return str + "+" + java.lang.Integer.toString(i3);
    }

    public static java.lang.String makeOomAdjString(int i, boolean z) {
        if (i >= 900) {
            return buildOomTag("cch", "cch", "   ", i, 900, z);
        }
        if (i >= 800) {
            return buildOomTag("svcb  ", "svcb", null, i, SERVICE_B_ADJ, z);
        }
        if (i >= 700) {
            return buildOomTag("prev  ", "prev", null, i, PREVIOUS_APP_ADJ, z);
        }
        if (i >= 600) {
            return buildOomTag("home  ", "home", null, i, 600, z);
        }
        if (i >= 500) {
            return buildOomTag("svc   ", "svc", null, i, 500, z);
        }
        if (i >= 400) {
            return buildOomTag("hvy   ", "hvy", null, i, 400, z);
        }
        if (i >= 300) {
            return buildOomTag("bkup  ", "bkup", null, i, 300, z);
        }
        if (i >= 250) {
            return buildOomTag("prcl  ", "prcl", null, i, 250, z);
        }
        if (i >= 225) {
            return buildOomTag("prcm  ", "prcm", null, i, PERCEPTIBLE_MEDIUM_APP_ADJ, z);
        }
        if (i >= 200) {
            return buildOomTag("prcp  ", "prcp", null, i, 200, z);
        }
        if (i >= 100) {
            return buildOomTag("vis", "vis", "   ", i, 100, z);
        }
        if (i >= 0) {
            return buildOomTag("fg ", "fg ", "   ", i, 0, z);
        }
        if (i >= -700) {
            return buildOomTag("psvc  ", "psvc", null, i, PERSISTENT_SERVICE_ADJ, z);
        }
        if (i >= -800) {
            return buildOomTag("pers  ", "pers", null, i, PERSISTENT_PROC_ADJ, z);
        }
        if (i >= -900) {
            return buildOomTag("sys   ", "sys", null, i, SYSTEM_ADJ, z);
        }
        if (i >= -1000) {
            return buildOomTag("ntv  ", "ntv", null, i, -1000, z);
        }
        return java.lang.Integer.toString(i);
    }

    public static java.lang.String makeProcStateString(int i) {
        return android.app.ActivityManager.procStateToString(i);
    }

    public static int makeProcStateProtoEnum(int i) {
        switch (i) {
            case -1:
                return 999;
            case 0:
                return 1000;
            case 1:
                return 1001;
            case 2:
                return 1002;
            case 3:
                return 1020;
            case 4:
                return 1003;
            case 5:
                return 1004;
            case 6:
                return 1005;
            case 7:
                return 1006;
            case 8:
                return 1007;
            case 9:
                return 1008;
            case 10:
                return 1009;
            case 11:
                return 1010;
            case 12:
                return 1011;
            case 13:
                return 1012;
            case 14:
                return 1013;
            case 15:
                return 1014;
            case 16:
                return 1015;
            case 17:
                return 1016;
            case 18:
                return 1017;
            case 19:
                return 1018;
            case 20:
                return 1019;
            default:
                return 998;
        }
    }

    public static void appendRamKb(java.lang.StringBuilder sb, long j) {
        int i = 0;
        int i2 = 10;
        while (i < 6) {
            if (j < i2) {
                sb.append(' ');
            }
            i++;
            i2 *= 10;
        }
        sb.append(j);
    }

    public static final class ProcStateMemTracker {
        int mPendingHighestMemState;
        int mPendingMemState;
        float mPendingScalingFactor;
        final int[] mHighestMem = new int[5];
        final float[] mScalingFactor = new float[5];
        int mTotalHighestMem = 4;

        public ProcStateMemTracker() {
            for (int i = 0; i < 5; i++) {
                this.mHighestMem[i] = 5;
                this.mScalingFactor[i] = 1.0f;
            }
            this.mPendingMemState = -1;
        }

        public void dumpLine(java.io.PrintWriter printWriter) {
            printWriter.print("best=");
            printWriter.print(this.mTotalHighestMem);
            printWriter.print(" (");
            boolean z = false;
            for (int i = 0; i < 5; i++) {
                if (this.mHighestMem[i] < 5) {
                    if (z) {
                        printWriter.print(", ");
                    }
                    printWriter.print(i);
                    printWriter.print("=");
                    printWriter.print(this.mHighestMem[i]);
                    printWriter.print(" ");
                    printWriter.print(this.mScalingFactor[i]);
                    printWriter.print("x");
                    z = true;
                }
            }
            printWriter.print(")");
            if (this.mPendingMemState >= 0) {
                printWriter.print(" / pending state=");
                printWriter.print(this.mPendingMemState);
                printWriter.print(" highest=");
                printWriter.print(this.mPendingHighestMemState);
                printWriter.print(" ");
                printWriter.print(this.mPendingScalingFactor);
                printWriter.print("x");
            }
            printWriter.println();
        }
    }

    public static boolean procStatesDifferForMem(int i, int i2) {
        return sProcStateToProcMem[i] != sProcStateToProcMem[i2];
    }

    public static long minTimeFromStateChange(boolean z) {
        if (z) {
            return com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        }
        return 15000L;
    }

    public static long computeNextPssTime(int i, com.android.server.am.ProcessList.ProcStateMemTracker procStateMemTracker, boolean z, boolean z2, long j, long j2) {
        long[] jArr;
        int i2 = sProcStateToProcMem[i];
        float f = 1.0f;
        if (procStateMemTracker != null) {
            int i3 = i2 < procStateMemTracker.mTotalHighestMem ? i2 : procStateMemTracker.mTotalHighestMem;
            r1 = i3 < procStateMemTracker.mHighestMem[i2];
            procStateMemTracker.mPendingMemState = i2;
            procStateMemTracker.mPendingHighestMemState = i3;
            if (r1) {
                procStateMemTracker.mPendingScalingFactor = 1.0f;
            } else {
                f = procStateMemTracker.mScalingFactor[i2];
                procStateMemTracker.mPendingScalingFactor = 1.5f * f;
            }
        }
        if (z) {
            if (r1) {
                jArr = sTestFirstPssTimes;
            } else {
                jArr = sTestSamePssTimes;
            }
        } else if (r1) {
            jArr = z2 ? sFirstAsleepPssTimes : sFirstAwakePssTimes;
        } else {
            jArr = z2 ? sSameAsleepPssTimes : sSameAwakePssTimes;
        }
        long j3 = (long) (jArr[i2] * f);
        if (j3 > 3600000) {
            j3 = 3600000;
        }
        return java.lang.Math.max(j + j3, j2);
    }

    long getMemLevel(int i) {
        for (int i2 = 0; i2 < this.mOomAdj.length; i2++) {
            if (i <= this.mOomAdj[i2]) {
                return this.mOomMinFree[i2] * 1024;
            }
        }
        return this.mOomMinFree[this.mOomAdj.length - 1] * 1024;
    }

    long getCachedRestoreThresholdKb() {
        return this.mCachedRestoreLevel;
    }

    com.android.server.am.AppStartInfoTracker getAppStartInfoTracker() {
        return this.mAppStartInfoTracker;
    }

    public static void setOomAdj(int i, int i2, int i3) {
        if (i <= 0 || i3 == 1001) {
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(16);
        allocate.putInt(1);
        allocate.putInt(i);
        allocate.putInt(i2);
        allocate.putInt(i3);
        writeLmkd(allocate, null);
        long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
        if (elapsedRealtime2 > 250) {
            android.util.Slog.w("ActivityManager", "SLOW OOM ADJ: " + elapsedRealtime2 + "ms for pid " + i + " = " + i3);
        }
    }

    public static final void remove(int i) {
        if (i <= 0) {
            return;
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(8);
        allocate.putInt(2);
        allocate.putInt(i);
        writeLmkd(allocate, null);
    }

    public static final java.lang.Integer getLmkdKillCount(int i, int i2) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(12);
        java.nio.ByteBuffer allocate2 = java.nio.ByteBuffer.allocate(8);
        allocate.putInt(4);
        allocate.putInt(i);
        allocate.putInt(i2);
        allocate2.putInt(4);
        allocate2.rewind();
        if (writeLmkd(allocate, allocate2) && allocate2.getInt() == 4) {
            return new java.lang.Integer(allocate2.getInt());
        }
        return null;
    }

    public boolean onLmkdConnect(java.io.OutputStream outputStream) {
        try {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(4);
            allocate.putInt(3);
            outputStream.write(allocate.array(), 0, allocate.position());
            if (this.mOomLevelsSet) {
                java.nio.ByteBuffer allocate2 = java.nio.ByteBuffer.allocate(((this.mOomAdj.length * 2) + 1) * 4);
                allocate2.putInt(0);
                for (int i = 0; i < this.mOomAdj.length; i++) {
                    allocate2.putInt((this.mOomMinFree[i] * 1024) / PAGE_SIZE);
                    allocate2.putInt(this.mOomAdj[i]);
                }
                outputStream.write(allocate2.array(), 0, allocate2.position());
            }
            java.nio.ByteBuffer allocate3 = java.nio.ByteBuffer.allocate(8);
            allocate3.putInt(5);
            allocate3.putInt(0);
            outputStream.write(allocate3.array(), 0, allocate3.position());
            java.nio.ByteBuffer allocate4 = java.nio.ByteBuffer.allocate(8);
            allocate4.putInt(5);
            allocate4.putInt(1);
            outputStream.write(allocate4.array(), 0, allocate4.position());
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    public static void startPsiMonitoringAfterBoot() {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(4);
        allocate.putInt(9);
        writeLmkd(allocate, null);
    }

    private static boolean writeLmkd(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2) {
        if (!sLmkdConnection.isConnected()) {
            sKillHandler.sendMessage(sKillHandler.obtainMessage(4001));
            if (!sLmkdConnection.waitForConnection(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS)) {
                return false;
            }
        }
        return sLmkdConnection.exchange(byteBuffer, byteBuffer2);
    }

    static void killProcessGroup(int i, int i2) {
        if (sKillHandler != null) {
            sKillHandler.sendMessage(sKillHandler.obtainMessage(4000, i, i2));
        } else {
            android.util.Slog.w("ActivityManager", "Asked to kill process group before system bringup!");
            android.os.Process.killProcessGroup(i, i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ProcessRecord getProcessRecordLocked(java.lang.String str, int i) {
        if (i == 1000) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().get(str);
            if (sparseArray == null) {
                return null;
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                if (android.os.UserHandle.isCore(keyAt) && android.os.UserHandle.isSameUser(keyAt, i)) {
                    return (com.android.server.am.ProcessRecord) sparseArray.valueAt(i2);
                }
            }
        }
        return (com.android.server.am.ProcessRecord) this.mProcessNames.get(str, i);
    }

    void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryInfo) {
        long memLevel = getMemLevel(600);
        long memLevel2 = getMemLevel(900);
        memoryInfo.advertisedMem = android.os.Process.getAdvertisedMem();
        memoryInfo.availMem = android.os.Process.getFreeMemory();
        memoryInfo.totalMem = android.os.Process.getTotalMemory();
        memoryInfo.threshold = memLevel;
        memoryInfo.lowMemory = memoryInfo.availMem < memLevel + ((memLevel2 - memLevel) / 2);
        memoryInfo.hiddenAppThreshold = memLevel2;
        memoryInfo.secondaryServerThreshold = getMemLevel(500);
        memoryInfo.visibleAppThreshold = getMemLevel(100);
        memoryInfo.foregroundAppThreshold = getMemLevel(0);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessRecord findAppProcessLOSP(android.os.IBinder iBinder, java.lang.String str) {
        int size = this.mProcessNames.getMap().size();
        for (int i = 0; i < size; i++) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().valueAt(i);
            int size2 = sparseArray.size();
            for (int i2 = 0; i2 < size2; i2++) {
                com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i2);
                android.app.IApplicationThread thread = processRecord.getThread();
                if (thread != null && thread.asBinder() == iBinder) {
                    return processRecord;
                }
            }
        }
        android.util.Slog.w("ActivityManager", "Can't find mystery application for " + str + " from pid=" + android.os.Binder.getCallingPid() + " uid=" + android.os.Binder.getCallingUid() + ": " + iBinder);
        return null;
    }

    private void checkSlow(long j, java.lang.String str) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis() - j;
        if (uptimeMillis > 50) {
            android.util.Slog.w("ActivityManager", "Slow operation: " + uptimeMillis + "ms so far, now at " + str);
        }
    }

    private int[] computeGidsForProcess(int i, int i2, int[] iArr, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList(iArr.length + 5);
        int sharedAppGid = android.os.UserHandle.getSharedAppGid(android.os.UserHandle.getAppId(i2));
        int cacheAppGid = android.os.UserHandle.getCacheAppGid(android.os.UserHandle.getAppId(i2));
        int userGid = android.os.UserHandle.getUserGid(android.os.UserHandle.getUserId(i2));
        for (int i3 : iArr) {
            arrayList.add(java.lang.Integer.valueOf(i3));
        }
        if (sharedAppGid != -1) {
            arrayList.add(java.lang.Integer.valueOf(sharedAppGid));
        }
        if (cacheAppGid != -1) {
            arrayList.add(java.lang.Integer.valueOf(cacheAppGid));
        }
        if (userGid != -1) {
            arrayList.add(java.lang.Integer.valueOf(userGid));
        }
        if (i == 4 || i == 3) {
            arrayList.add(java.lang.Integer.valueOf(android.os.UserHandle.getUid(android.os.UserHandle.getUserId(i2), 1015)));
            arrayList.add(1078);
            arrayList.add(1079);
        }
        if (i == 2) {
            arrayList.add(1079);
        }
        if (i == 3) {
            arrayList.add(1023);
        }
        if (z) {
            arrayList.add(1077);
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i4 = 0; i4 < size; i4++) {
            iArr2[i4] = ((java.lang.Integer) arrayList.get(i4)).intValue();
        }
        return iArr2;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean startProcessLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.HostingRecord hostingRecord, int i, boolean z, boolean z2, java.lang.String str) {
        java.lang.String str2;
        boolean z3;
        int[] computeGidsForProcess;
        int i2;
        int i3;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        android.content.pm.ApplicationInfo applicationInfo;
        android.content.pm.ApplicationInfo clientInfoForSdkSandbox;
        if (processRecord.isPendingStart()) {
            return true;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        boolean z4 = false;
        if (processRecord.getPid() > 0 && processRecord.getPid() != com.android.server.am.ActivityManagerService.MY_PID) {
            checkSlow(uptimeMillis, "startProcess: removing from pids map");
            this.mService.removePidLocked(processRecord.getPid(), processRecord);
            processRecord.setBindMountPending(false);
            this.mService.mHandler.removeMessages(20, processRecord);
            checkSlow(uptimeMillis, "startProcess: done removing from pids map");
            processRecord.setPid(0);
            processRecord.setStartSeq(0L);
        }
        processRecord.unlinkDeathRecipient();
        processRecord.setDyingPid(0);
        this.mService.mProcessesOnHold.remove(processRecord);
        checkSlow(uptimeMillis, "startProcess: starting to update cpu stats");
        this.mService.updateCpuStats();
        checkSlow(uptimeMillis, "startProcess: done updating cpu stats");
        try {
            int userId = android.os.UserHandle.getUserId(processRecord.uid);
            try {
                try {
                    android.app.AppGlobals.getPackageManager().checkPackageStartable(processRecord.info.packageName, userId);
                    int i4 = processRecord.uid;
                    if (processRecord.isolated) {
                        computeGidsForProcess = null;
                        i2 = 0;
                    } else {
                        try {
                            try {
                                checkSlow(uptimeMillis, "startProcess: getting gids from package manager");
                                android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
                                int[] packageGids = packageManager.getPackageGids(processRecord.info.packageName, 268435456L, processRecord.userId);
                                android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
                                int externalStorageMountMode = storageManagerInternal.getExternalStorageMountMode(i4, processRecord.info.packageName);
                                boolean hasExternalStorageAccess = storageManagerInternal.hasExternalStorageAccess(i4, processRecord.info.packageName);
                                if (this.mService.isAppFreezerExemptInstPkg() && packageManager.checkPermission("android.permission.INSTALL_PACKAGES", processRecord.info.packageName, userId) == 0) {
                                    android.util.Slog.i("ActivityManager", processRecord.info.packageName + " is exempt from freezer");
                                    processRecord.mOptRecord.setFreezeExempt(true);
                                }
                                if (processRecord.processInfo != null && processRecord.processInfo.deniedPermissions != null) {
                                    for (int size = processRecord.processInfo.deniedPermissions.size() - 1; size >= 0; size--) {
                                        int[] permissionGids = this.mService.mPackageManagerInt.getPermissionGids((java.lang.String) processRecord.processInfo.deniedPermissions.valueAt(size), processRecord.userId);
                                        if (permissionGids != null) {
                                            for (int i5 : permissionGids) {
                                                packageGids = com.android.internal.util.ArrayUtils.removeInt(packageGids, i5);
                                            }
                                        }
                                    }
                                }
                                computeGidsForProcess = computeGidsForProcess(externalStorageMountMode, i4, packageGids, hasExternalStorageAccess);
                                i2 = externalStorageMountMode;
                            } catch (java.lang.RuntimeException e) {
                                e = e;
                                str2 = "ActivityManager";
                                z3 = false;
                                android.util.Slog.e(str2, "Failure starting process " + processRecord.processName, e);
                                this.mService.forceStopPackageLocked(processRecord.info.packageName, android.os.UserHandle.getAppId(processRecord.uid), false, false, true, false, false, false, processRecord.userId, "start failure");
                                return z3;
                            }
                        } catch (android.os.RemoteException e2) {
                            throw e2.rethrowAsRuntimeException();
                        }
                    }
                    processRecord.setMountMode(i2);
                    checkSlow(uptimeMillis, "startProcess: building args");
                    int i6 = processRecord.getWindowProcessController().isFactoryTestProcess() ? 0 : i4;
                    boolean z5 = (processRecord.info.flags & 2) != 0;
                    boolean isProfileableByShell = processRecord.info.isProfileableByShell();
                    boolean isProfileable = processRecord.info.isProfileable();
                    if (processRecord.isSdkSandbox && (clientInfoForSdkSandbox = processRecord.getClientInfoForSdkSandbox()) != null) {
                        z5 |= (clientInfoForSdkSandbox.flags & 2) != 0;
                        isProfileableByShell |= clientInfoForSdkSandbox.isProfileableByShell();
                        isProfileable |= clientInfoForSdkSandbox.isProfileable();
                    }
                    if (!z5) {
                        i3 = 0;
                    } else if (android.provider.Settings.Global.getInt(this.mService.mContext.getContentResolver(), "art_verifier_verify_debuggable", 1) == 0) {
                        android.util.Slog.w("ActivityManager", processRecord + ": ART verification disabled");
                        i3 = 33555203;
                    } else {
                        i3 = 33554691;
                    }
                    if ((processRecord.info.flags & 16384) != 0 || this.mService.mSafeMode) {
                        i3 |= 8;
                    }
                    if (isProfileableByShell) {
                        i3 |= 32768;
                    }
                    if (isProfileable) {
                        i3 |= 16777216;
                    }
                    if ("1".equals(android.os.SystemProperties.get("debug.checkjni"))) {
                        i3 |= 2;
                    }
                    java.lang.String str6 = android.os.SystemProperties.get("debug.generate-debug-info");
                    if ("1".equals(str6) || "true".equals(str6)) {
                        i3 |= 32;
                    }
                    java.lang.String str7 = android.os.SystemProperties.get("dalvik.vm.minidebuginfo");
                    if ("1".equals(str7) || "true".equals(str7)) {
                        i3 |= 2048;
                    }
                    if ("1".equals(android.os.SystemProperties.get("debug.jni.logging"))) {
                        i3 |= 16;
                    }
                    if ("1".equals(android.os.SystemProperties.get("debug.assert"))) {
                        i3 |= 4;
                    }
                    if ("1".equals(android.os.SystemProperties.get("debug.ignoreappsignalhandler"))) {
                        i3 |= 131072;
                    }
                    if (this.mService.mNativeDebuggingApp == null) {
                        str3 = null;
                    } else if (this.mService.mNativeDebuggingApp.equals(processRecord.processName)) {
                        i3 = i3 | 64 | 32 | 128;
                        str3 = null;
                        this.mService.mNativeDebuggingApp = null;
                    } else {
                        str3 = null;
                    }
                    if (processRecord.info.isEmbeddedDexUsed() || (processRecord.processInfo != null && processRecord.processInfo.useEmbeddedDex)) {
                        i3 |= 1024;
                    }
                    if (!z && !this.mService.mHiddenApiBlacklist.isDisabled()) {
                        processRecord.info.maybeUpdateHiddenApiEnforcementPolicy(this.mService.mHiddenApiBlacklist.getPolicy());
                        int hiddenApiEnforcementPolicy = processRecord.info.getHiddenApiEnforcementPolicy();
                        int i7 = hiddenApiEnforcementPolicy << com.android.internal.os.Zygote.API_ENFORCEMENT_POLICY_SHIFT;
                        if ((i7 & 12288) != i7) {
                            throw new java.lang.IllegalStateException("Invalid API policy: " + hiddenApiEnforcementPolicy);
                        }
                        i3 |= i7;
                        if (z2) {
                            i3 |= 262144;
                        }
                    }
                    java.lang.String str8 = android.os.SystemProperties.get(PROPERTY_USE_APP_IMAGE_STARTUP_CACHE, "");
                    if (!android.text.TextUtils.isEmpty(str8) && !str8.equals("false")) {
                        i3 |= 65536;
                    }
                    if (z5) {
                        java.lang.String str9 = processRecord.info.nativeLibraryDir + "/wrap.sh";
                        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
                        try {
                            str4 = new java.io.File(str9).exists() ? "/system/bin/logwrapper " + str9 : str3;
                            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
                        } catch (java.lang.Throwable th) {
                            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
                            throw th;
                        }
                    } else {
                        str4 = str3;
                    }
                    java.lang.String str10 = str != null ? str : processRecord.info.primaryCpuAbi;
                    if (str10 == null) {
                        try {
                            z4 = false;
                            str5 = android.os.Build.SUPPORTED_ABIS[0];
                        } catch (java.lang.RuntimeException e3) {
                            e = e3;
                            z4 = false;
                            z3 = z4;
                            str2 = "ActivityManager";
                            android.util.Slog.e(str2, "Failure starting process " + processRecord.processName, e);
                            this.mService.forceStopPackageLocked(processRecord.info.packageName, android.os.UserHandle.getAppId(processRecord.uid), false, false, true, false, false, false, processRecord.userId, "start failure");
                            return z3;
                        }
                    } else {
                        z4 = false;
                        str5 = str10;
                    }
                    java.lang.String instructionSet = processRecord.info.primaryCpuAbi != null ? dalvik.system.VMRuntime.getInstructionSet(str5) : str3;
                    processRecord.setGids(computeGidsForProcess);
                    processRecord.setRequiredAbi(str5);
                    processRecord.setInstructionSet(instructionSet);
                    if (hostingRecord.getDefiningPackageName() != null) {
                        applicationInfo = new android.content.pm.ApplicationInfo(processRecord.info);
                        applicationInfo.packageName = hostingRecord.getDefiningPackageName();
                        applicationInfo.uid = i6;
                    } else {
                        applicationInfo = processRecord.info;
                    }
                    int memorySafetyRuntimeFlags = i3 | com.android.internal.os.Zygote.getMemorySafetyRuntimeFlags(applicationInfo, processRecord.processInfo, instructionSet, this.mPlatformCompat);
                    if (android.text.TextUtils.isEmpty(processRecord.info.seInfoUser)) {
                        android.util.Slog.wtf("ActivityManager", "SELinux tag not defined", new java.lang.IllegalStateException("SELinux tag not defined for " + processRecord.info.packageName + " (uid " + processRecord.uid + ")"));
                    }
                    return startProcessLocked(hostingRecord, "android.app.ActivityThread", processRecord, i6, computeGidsForProcess, memorySafetyRuntimeFlags, i, i2, updateSeInfo(processRecord), str5, instructionSet, str4, uptimeMillis, elapsedRealtime);
                } catch (java.lang.RuntimeException e4) {
                    e = e4;
                }
            } catch (android.os.RemoteException e5) {
                throw e5.rethrowAsRuntimeException();
            }
        } catch (java.lang.RuntimeException e6) {
            e = e6;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003f  */
    @com.android.internal.annotations.GuardedBy({"mService"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    java.lang.String updateSeInfo(com.android.server.am.ProcessRecord processRecord) {
        java.lang.String str;
        if (processRecord.isSdkSandbox) {
            if (getProcessListSettingsListener().applySdkSandboxRestrictionsNext()) {
                str = APPLY_SDK_SANDBOX_NEXT_RESTRICTIONS;
            } else if (com.android.sdksandbox.flags.Flags.selinuxSdkSandboxAudit() && getProcessListSettingsListener().applySdkSandboxRestrictionsAudit()) {
                str = APPLY_SDK_SANDBOX_AUDIT_RESTRICTIONS;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(processRecord.info.seInfo);
            sb.append(android.text.TextUtils.isEmpty(processRecord.info.seInfoUser) ? "" : processRecord.info.seInfoUser);
            sb.append(str);
            return sb.toString();
        }
        str = "";
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(processRecord.info.seInfo);
        sb2.append(android.text.TextUtils.isEmpty(processRecord.info.seInfoUser) ? "" : processRecord.info.seInfoUser);
        sb2.append(str);
        return sb2.toString();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean startProcessLocked(com.android.server.am.HostingRecord hostingRecord, final java.lang.String str, final com.android.server.am.ProcessRecord processRecord, int i, final int[] iArr, final int i2, final int i3, final int i4, java.lang.String str2, final java.lang.String str3, final java.lang.String str4, final java.lang.String str5, long j, long j2) {
        processRecord.setPendingStart(true);
        processRecord.setRemoved(false);
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                processRecord.setKilledByAm(false);
                processRecord.setKilled(false);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (processRecord.getStartSeq() != 0) {
            android.util.Slog.wtf("ActivityManager", "startProcessLocked processName:" + processRecord.processName + " with non-zero startSeq:" + processRecord.getStartSeq());
        }
        if (processRecord.getPid() != 0) {
            android.util.Slog.wtf("ActivityManager", "startProcessLocked processName:" + processRecord.processName + " with non-zero pid:" + processRecord.getPid());
        }
        processRecord.setDisabledCompatChanges(null);
        if (this.mPlatformCompat != null) {
            processRecord.setDisabledCompatChanges(this.mPlatformCompat.getDisabledChanges(processRecord.info));
        }
        final long j3 = this.mProcStartSeqCounter + 1;
        this.mProcStartSeqCounter = j3;
        processRecord.setStartSeq(j3);
        processRecord.setStartParams(i, hostingRecord, str2, j, j2);
        processRecord.setUsingWrapper((str5 == null && com.android.internal.os.Zygote.getWrapProperty(processRecord.processName) == null) ? false : true);
        this.mPendingStarts.put(j3, processRecord);
        if (this.mService.mConstants.FLAG_PROCESS_START_ASYNC) {
            this.mService.mProcStartHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.ProcessList.this.lambda$startProcessLocked$0(processRecord, str, iArr, i2, i3, i4, str3, str4, str5, j3);
                }
            });
            return true;
        }
        try {
            android.os.Process.ProcessStartResult startProcess = startProcess(hostingRecord, str, processRecord, i, iArr, i2, i3, i4, str2, str3, str4, str5, j);
            handleProcessStartedLocked(processRecord, startProcess.pid, startProcess.usingWrapper, j3, false);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e("ActivityManager", "Failure starting process " + processRecord.processName, e);
            processRecord.setPendingStart(false);
            this.mService.forceStopPackageLocked(processRecord.info.packageName, android.os.UserHandle.getAppId(processRecord.uid), false, false, true, false, false, false, processRecord.userId, "start failure");
        }
        return processRecord.getPid() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleProcessStart, reason: merged with bridge method [inline-methods] */
    public void lambda$startProcessLocked$0(final com.android.server.am.ProcessRecord processRecord, final java.lang.String str, final int[] iArr, final int i, final int i2, final int i3, final java.lang.String str2, final java.lang.String str3, final java.lang.String str4, final long j) {
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ProcessList.this.lambda$handleProcessStart$1(processRecord, str, iArr, i, i2, i3, str2, str3, str4, j);
            }
        };
        com.android.server.am.ProcessRecord processRecord2 = processRecord.mPredecessor;
        if (processRecord2 == null || processRecord2.getDyingPid() <= 0) {
            runnable.run();
        } else {
            handleProcessStartWithPredecessor(processRecord2, runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$handleProcessStart$1(com.android.server.am.ProcessRecord processRecord, java.lang.String str, int[] iArr, int i, int i2, int i3, java.lang.String str2, java.lang.String str3, java.lang.String str4, long j) {
        long j2;
        com.android.server.am.ProcessList processList;
        com.android.server.am.ProcessRecord processRecord2;
        com.android.server.am.ActivityManagerService activityManagerService;
        try {
            try {
                android.os.Process.ProcessStartResult startProcess = startProcess(processRecord.getHostingRecord(), str, processRecord, processRecord.getStartUid(), iArr, i, i2, i3, processRecord.getSeInfo(), str2, str3, str4, processRecord.getStartTime());
                processList = this;
                try {
                    com.android.server.am.ActivityManagerService activityManagerService2 = processList.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        processRecord2 = processRecord;
                        j2 = j;
                        try {
                            try {
                                processList.handleProcessStartedLocked(processRecord2, startProcess, j2);
                            } finally {
                            }
                        } catch (java.lang.RuntimeException e) {
                            e = e;
                            activityManagerService = processList.mService;
                            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService) {
                                try {
                                    android.util.Slog.e("ActivityManager", "Failure starting process " + processRecord2.processName, e);
                                    processList.mPendingStarts.remove(j2);
                                    processRecord2.setPendingStart(false);
                                    processList.mService.forceStopPackageLocked(processRecord2.info.packageName, android.os.UserHandle.getAppId(processRecord2.uid), false, false, true, false, false, false, processRecord2.userId, "start failure");
                                    processRecord.doEarlyCleanupIfNecessaryLocked();
                                } finally {
                                }
                            }
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.RuntimeException e2) {
                    e = e2;
                    processRecord2 = processRecord;
                    j2 = j;
                    activityManagerService = processList.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                    }
                }
            } catch (java.lang.RuntimeException e3) {
                e = e3;
                processList = this;
            }
        } catch (java.lang.RuntimeException e4) {
            e = e4;
            j2 = j;
            processList = this;
            processRecord2 = processRecord;
        }
    }

    private void handleProcessStartWithPredecessor(com.android.server.am.ProcessRecord processRecord, java.lang.Runnable runnable) {
        if (processRecord.mSuccessorStartRunnable != null) {
            android.util.Slog.wtf("ActivityManager", "We've been watching for the death of " + processRecord);
            return;
        }
        processRecord.mSuccessorStartRunnable = runnable;
        this.mService.mProcStartHandler.sendMessageDelayed(this.mService.mProcStartHandler.obtainMessage(2, processRecord), this.mService.mConstants.mProcessKillTimeoutMs);
    }

    static final class ProcStartHandler extends android.os.Handler {
        static final int MSG_PROCESS_DIED = 1;
        static final int MSG_PROCESS_KILL_TIMEOUT = 2;
        private final com.android.server.am.ActivityManagerService mService;

        ProcStartHandler(com.android.server.am.ActivityManagerService activityManagerService, android.os.Looper looper) {
            super(looper);
            this.mService = activityManagerService;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    this.mService.mProcessList.handlePredecessorProcDied((com.android.server.am.ProcessRecord) message.obj);
                    return;
                case 2:
                    com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            this.mService.handleProcessStartOrKillTimeoutLocked((com.android.server.am.ProcessRecord) message.obj, true);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePredecessorProcDied(com.android.server.am.ProcessRecord processRecord) {
        java.lang.Runnable runnable = processRecord.mSuccessorStartRunnable;
        if (runnable != null) {
            processRecord.mSuccessorStartRunnable = null;
            runnable.run();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    public void killAppZygoteIfNeededLocked(android.os.AppZygote appZygote, boolean z) {
        android.content.pm.ApplicationInfo appInfo = appZygote.getAppInfo();
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mAppZygoteProcesses.get(appZygote);
        if (arrayList != null) {
            if (z || arrayList.size() == 0) {
                this.mAppZygotes.remove(appInfo.processName, appInfo.uid);
                this.mAppZygoteProcesses.remove(appZygote);
                this.mAppIsolatedUidRangeAllocator.freeUidRangeLocked(appInfo);
                appZygote.stopZygote();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void removeProcessFromAppZygoteLocked(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ProcessList.IsolatedUidRange isolatedUidRangeLocked = this.mAppIsolatedUidRangeAllocator.getIsolatedUidRangeLocked(processRecord.info.processName, processRecord.getHostingRecord().getDefiningUid());
        if (isolatedUidRangeLocked != null) {
            isolatedUidRangeLocked.freeIsolatedUidLocked(processRecord.uid);
        }
        android.os.AppZygote appZygote = (android.os.AppZygote) this.mAppZygotes.get(processRecord.info.processName, processRecord.getHostingRecord().getDefiningUid());
        if (appZygote != null) {
            java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mAppZygoteProcesses.get(appZygote);
            arrayList.remove(processRecord);
            if (arrayList.size() == 0) {
                this.mService.mHandler.removeMessages(71);
                if (processRecord.isRemoved()) {
                    killAppZygoteIfNeededLocked(appZygote, false);
                    return;
                }
                android.os.Message obtainMessage = this.mService.mHandler.obtainMessage(71);
                obtainMessage.obj = appZygote;
                this.mService.mHandler.sendMessageDelayed(obtainMessage, 5000L);
            }
        }
    }

    private android.os.AppZygote createAppZygoteForProcessIfNeeded(com.android.server.am.ProcessRecord processRecord) {
        android.os.AppZygote appZygote;
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList;
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int definingUid = processRecord.getHostingRecord().getDefiningUid();
                appZygote = (android.os.AppZygote) this.mAppZygotes.get(processRecord.info.processName, definingUid);
                if (appZygote == null) {
                    com.android.server.am.ProcessList.IsolatedUidRange isolatedUidRangeLocked = this.mAppIsolatedUidRangeAllocator.getIsolatedUidRangeLocked(processRecord.info.processName, processRecord.getHostingRecord().getDefiningUid());
                    int userId = android.os.UserHandle.getUserId(definingUid);
                    int uid = android.os.UserHandle.getUid(userId, isolatedUidRangeLocked.mFirstUid);
                    int uid2 = android.os.UserHandle.getUid(userId, isolatedUidRangeLocked.mLastUid);
                    android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo(processRecord.info);
                    applicationInfo.packageName = processRecord.getHostingRecord().getDefiningPackageName();
                    applicationInfo.uid = definingUid;
                    android.os.AppZygote appZygote2 = new android.os.AppZygote(applicationInfo, processRecord.processInfo, definingUid, uid, uid2);
                    this.mAppZygotes.put(processRecord.info.processName, definingUid, appZygote2);
                    arrayList = new java.util.ArrayList<>();
                    this.mAppZygoteProcesses.put(appZygote2, arrayList);
                    appZygote = appZygote2;
                } else {
                    this.mService.mHandler.removeMessages(71, appZygote);
                    arrayList = this.mAppZygoteProcesses.get(appZygote);
                }
                arrayList.add(processRecord);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return appZygote;
    }

    private java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> getPackageAppDataInfoMap(android.content.pm.PackageManagerInternal packageManagerInternal, java.lang.String[] strArr, int i) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(strArr.length);
        int userId = android.os.UserHandle.getUserId(i);
        for (java.lang.String str : strArr) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = packageManagerInternal.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                android.util.Slog.w("ActivityManager", "Unknown package:" + str);
            } else {
                java.lang.String volumeUuid = packageStateInternal.getVolumeUuid();
                long ceDataInode = packageStateInternal.getUserStateOrDefault(userId).getCeDataInode();
                if (ceDataInode == 0) {
                    android.util.Slog.w("ActivityManager", str + " inode == 0 (b/152760674)");
                    return null;
                }
                arrayMap.put(str, android.util.Pair.create(volumeUuid, java.lang.Long.valueOf(ceDataInode)));
            }
        }
        return arrayMap;
    }

    private boolean needsStorageDataIsolation(android.os.storage.StorageManagerInternal storageManagerInternal, com.android.server.am.ProcessRecord processRecord) {
        int mountMode = processRecord.getMountMode();
        return (!this.mVoldAppDataIsolationEnabled || !android.os.UserHandle.isApp(processRecord.uid) || storageManagerInternal.isExternalStorageService(processRecord.uid) || mountMode == 4 || mountMode == 3 || mountMode == 2 || mountMode == 0) ? false : true;
    }

    private android.os.Process.ProcessStartResult startProcess(com.android.server.am.HostingRecord hostingRecord, java.lang.String str, com.android.server.am.ProcessRecord processRecord, int i, int[] iArr, int i2, int i3, int i4, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, long j) {
        long j2;
        java.lang.String[] sharedUserPackagesForPackage;
        boolean z;
        boolean z2;
        java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map;
        java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map2;
        boolean z3;
        android.os.storage.StorageManagerInternal storageManagerInternal;
        int i5;
        android.os.Process.ProcessStartResult start;
        boolean z4;
        boolean z5;
        try {
            android.os.Trace.traceBegin(64L, "Start proc: " + processRecord.processName);
            checkSlow(j, "startProcess: asking zygote to start proc");
            boolean isTopApp = hostingRecord.isTopApp();
            if (isTopApp) {
                processRecord.mState.setHasForegroundActivities(true);
            }
            boolean z6 = this.mAppDataIsolationEnabled && (android.os.UserHandle.isApp(processRecord.uid) || android.os.UserHandle.isIsolated(processRecord.uid) || processRecord.isSdkSandbox) && this.mPlatformCompat.isChangeEnabled(APP_DATA_DIRECTORY_ISOLATION, processRecord.info);
            android.content.pm.PackageManagerInternal packageManagerInternal = this.mService.getPackageManagerInternal();
            if (processRecord.isSdkSandbox) {
                sharedUserPackagesForPackage = new java.lang.String[]{processRecord.sdkSandboxClientAppPackage};
            } else {
                sharedUserPackagesForPackage = packageManagerInternal.getSharedUserPackagesForPackage(processRecord.info.packageName, processRecord.userId);
                if (sharedUserPackagesForPackage.length == 0) {
                    sharedUserPackagesForPackage = new java.lang.String[]{processRecord.info.packageName};
                }
            }
            boolean hasAppStorage = hasAppStorage(packageManagerInternal, processRecord.info.packageName);
            java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> packageAppDataInfoMap = getPackageAppDataInfoMap(packageManagerInternal, sharedUserPackagesForPackage, i);
            if (packageAppDataInfoMap == null) {
                z6 = false;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet(this.mAppDataIsolationAllowlistedApps);
            for (java.lang.String str6 : sharedUserPackagesForPackage) {
                try {
                    arraySet.remove(str6);
                } catch (java.lang.Throwable th) {
                    th = th;
                    j2 = 64;
                    android.os.Trace.traceEnd(j2);
                    throw th;
                }
            }
            java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> packageAppDataInfoMap2 = getPackageAppDataInfoMap(packageManagerInternal, (java.lang.String[]) arraySet.toArray(new java.lang.String[0]), i);
            if (packageAppDataInfoMap2 == null) {
                z6 = false;
            }
            if (hasAppStorage || processRecord.isSdkSandbox) {
                z = z6;
            } else {
                packageAppDataInfoMap2 = null;
                packageAppDataInfoMap = null;
                z = false;
            }
            int userId = android.os.UserHandle.getUserId(i);
            android.os.storage.StorageManagerInternal storageManagerInternal2 = (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
            if (!needsStorageDataIsolation(storageManagerInternal2, processRecord)) {
                z2 = false;
            } else if (packageAppDataInfoMap == null || !storageManagerInternal2.isFuseMounted(userId)) {
                processRecord.setBindMountPending(true);
                z2 = false;
            } else {
                z2 = true;
            }
            if (processRecord.isolated) {
                map = null;
                map2 = null;
            } else {
                map = packageAppDataInfoMap2;
                map2 = packageAppDataInfoMap;
            }
            java.lang.String[] split = android.provider.DeviceConfig.getString("app_compat", "appcompat_sysprop_override_pkgs", "").split(",");
            java.lang.String[] packageList = processRecord.getPackageList();
            int i6 = 0;
            while (true) {
                if (i6 >= packageList.length) {
                    z3 = false;
                    break;
                }
                if (com.android.internal.util.ArrayUtils.contains(split, packageList[i6])) {
                    z3 = true;
                    break;
                }
                i6++;
            }
            com.android.server.AppStateTracker appStateTracker = this.mService.mServices.mAppStateTracker;
            if (appStateTracker != null) {
                boolean isAppBackgroundRestricted = appStateTracker.isAppBackgroundRestricted(processRecord.info.uid, processRecord.info.packageName);
                if (isAppBackgroundRestricted) {
                    com.android.server.am.ActivityManagerService activityManagerService = this.mService;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            this.mAppsInBackgroundRestricted.add(processRecord);
                        } catch (java.lang.Throwable th2) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
                processRecord.mState.setBackgroundRestricted(isAppBackgroundRestricted);
            }
            processRecord.mProcessGroupCreated = false;
            processRecord.mSkipProcessGroupCreation = false;
            try {
                if (hostingRecord.usesWebviewZygote()) {
                    storageManagerInternal = storageManagerInternal2;
                    j2 = 64;
                    i5 = userId;
                    start = android.os.Process.startWebView(str, processRecord.processName, i, i, iArr, i2, i4, processRecord.info.targetSdkVersion, str2, str3, str4, processRecord.info.dataDir, null, processRecord.info.packageName, processRecord.getDisabledCompatChanges(), new java.lang.String[]{"seq=" + processRecord.getStartSeq()});
                    z5 = false;
                    z4 = true;
                } else {
                    storageManagerInternal = storageManagerInternal2;
                    i5 = userId;
                    j2 = 64;
                    if (hostingRecord.usesAppZygote()) {
                        start = createAppZygoteForProcessIfNeeded(processRecord).getProcess().start(str, processRecord.processName, i, i, iArr, i2, i4, processRecord.info.targetSdkVersion, str2, str3, str4, processRecord.info.dataDir, (java.lang.String) null, processRecord.info.packageName, 0, isTopApp, processRecord.getDisabledCompatChanges(), map2, map, false, false, false, new java.lang.String[]{"seq=" + processRecord.getStartSeq()});
                        z5 = false;
                        z4 = true;
                    } else {
                        start = android.os.Process.start(str, processRecord.processName, i, i, iArr, i2, i4, processRecord.info.targetSdkVersion, str2, str3, str4, processRecord.info.dataDir, str5, processRecord.info.packageName, i3, isTopApp, processRecord.getDisabledCompatChanges(), map2, map, z, z2, z3, new java.lang.String[]{"seq=" + processRecord.getStartSeq()});
                        z4 = true;
                        processRecord.mProcessGroupCreated = true;
                        z5 = true;
                    }
                }
                if (!z5) {
                    synchronized (processRecord) {
                        try {
                            if (!processRecord.mSkipProcessGroupCreation) {
                                int createProcessGroup = android.os.Process.createProcessGroup(i, start.pid);
                                if (createProcessGroup >= 0) {
                                    processRecord.mProcessGroupCreated = z4;
                                } else {
                                    if (createProcessGroup != (-android.system.OsConstants.ESRCH)) {
                                        throw new java.lang.AssertionError("Unable to create process group for " + processRecord.processName + " (" + start.pid + ")");
                                    }
                                    android.util.Slog.e("ActivityManager", "Unable to create process group for " + processRecord.processName + " (" + start.pid + ")");
                                }
                            }
                        } finally {
                        }
                    }
                }
                if (z2) {
                    storageManagerInternal.prepareStorageDirs(i5, map2.keySet(), processRecord.processName);
                }
                checkSlow(j, "startProcess: returned from zygote!");
                android.os.Trace.traceEnd(j2);
                return start;
            } catch (java.lang.Throwable th3) {
                th = th3;
                android.os.Trace.traceEnd(j2);
                throw th;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
            j2 = 64;
        }
    }

    private boolean hasAppStorage(android.content.pm.PackageManagerInternal packageManagerInternal, java.lang.String str) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getPackage(str);
        if (androidPackage == null) {
            android.util.Slog.w("ActivityManager", "Unknown package " + str);
            return false;
        }
        android.content.pm.PackageManager.Property property = (android.content.pm.PackageManager.Property) androidPackage.getProperties().get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        return property == null || !property.getBoolean();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void startProcessLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.HostingRecord hostingRecord, int i) {
        startProcessLocked(processRecord, hostingRecord, i, null);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean startProcessLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.HostingRecord hostingRecord, int i, java.lang.String str) {
        return startProcessLocked(processRecord, hostingRecord, i, false, false, str);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ProcessRecord startProcessLocked(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, boolean z, int i, com.android.server.am.HostingRecord hostingRecord, int i2, boolean z2, boolean z3, int i3, boolean z4, int i4, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String[] strArr, java.lang.Runnable runnable) {
        com.android.server.am.ProcessRecord processRecord;
        com.android.server.am.ProcessRecord processRecord2;
        com.android.server.am.ProcessRecord processRecord3;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.os.SystemClock.elapsedRealtimeNanos();
        if (!z3) {
            processRecord = getProcessRecordLocked(str, applicationInfo.uid);
            checkSlow(uptimeMillis, "startProcess: after getProcessRecord");
            if ((i & 4) != 0) {
                if (this.mService.mAppErrors.isBadProcess(str, applicationInfo.uid)) {
                    return null;
                }
            } else {
                this.mService.mAppErrors.resetProcessCrashTime(str, applicationInfo.uid);
                if (this.mService.mAppErrors.isBadProcess(str, applicationInfo.uid)) {
                    android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_PROC_GOOD, java.lang.Integer.valueOf(android.os.UserHandle.getUserId(applicationInfo.uid)), java.lang.Integer.valueOf(applicationInfo.uid), applicationInfo.processName);
                    this.mService.mAppErrors.clearBadProcess(str, applicationInfo.uid);
                    if (processRecord != null) {
                        processRecord.mErrorState.setBad(false);
                    }
                }
            }
        } else {
            processRecord = null;
        }
        if (processRecord != null && processRecord.getPid() > 0) {
            if ((z || processRecord.isKilled()) && processRecord.getThread() != null) {
                checkSlow(uptimeMillis, "startProcess: bad proc running, killing");
                killProcessGroup(processRecord.uid, processRecord.getPid());
                checkSlow(uptimeMillis, "startProcess: done killing old proc");
                if (!processRecord.isKilled()) {
                    android.util.Slog.wtf("ActivityManager", processRecord.toString() + " is attached to a previous process");
                } else {
                    android.util.Slog.w("ActivityManager", processRecord.toString() + " is attached to a previous process");
                }
                processRecord2 = processRecord;
                processRecord = null;
            } else {
                processRecord.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mService.mProcessStats);
                checkSlow(uptimeMillis, "startProcess: done, added package to proc");
                return processRecord;
            }
        } else if (z3) {
            processRecord2 = null;
        } else {
            com.android.server.am.ProcessRecord processRecord4 = (com.android.server.am.ProcessRecord) this.mDyingProcesses.get(str, applicationInfo.uid);
            if (processRecord4 == null) {
                processRecord2 = processRecord4;
            } else {
                if (processRecord != null && processRecord != processRecord4) {
                    processRecord.mPredecessor = processRecord4;
                    processRecord4.mSuccessor = processRecord;
                } else {
                    processRecord = null;
                }
                android.util.Slog.w("ActivityManager", processRecord4.toString() + " is attached to a previous process " + processRecord4.getDyingPid());
                processRecord2 = processRecord4;
            }
        }
        if (processRecord == null) {
            checkSlow(uptimeMillis, "startProcess: creating new process record");
            com.android.server.am.ProcessRecord processRecord5 = processRecord2;
            processRecord = newProcessRecordLocked(applicationInfo, str, z3, i3, z4, i4, str2, hostingRecord);
            if (processRecord == null) {
                android.util.Slog.w("ActivityManager", "Failed making new process record for " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + applicationInfo.uid + " isolated=" + z3);
                return null;
            }
            processRecord3 = null;
            processRecord.mErrorState.setCrashHandler(runnable);
            processRecord.setIsolatedEntryPoint(str4);
            processRecord.setIsolatedEntryPointArgs(strArr);
            if (processRecord5 != null) {
                processRecord.mPredecessor = processRecord5;
                processRecord5.mSuccessor = processRecord;
            }
            checkSlow(uptimeMillis, "startProcess: done creating new process record");
        } else {
            processRecord3 = null;
            processRecord.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mService.mProcessStats);
            checkSlow(uptimeMillis, "startProcess: added package to existing proc");
        }
        if (this.mService.mProcessesReady || this.mService.isAllowedWhileBooting(applicationInfo) || z2) {
            checkSlow(uptimeMillis, "startProcess: stepping in to startProcess");
            boolean startProcessLocked = startProcessLocked(processRecord, hostingRecord, i2, str3);
            checkSlow(uptimeMillis, "startProcess: done starting proc!");
            return startProcessLocked ? processRecord : processRecord3;
        }
        if (!this.mService.mProcessesOnHold.contains(processRecord)) {
            this.mService.mProcessesOnHold.add(processRecord);
        }
        checkSlow(uptimeMillis, "startProcess: returning with proc on hold");
        return processRecord;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String isProcStartValidLocked(com.android.server.am.ProcessRecord processRecord, long j) {
        java.lang.StringBuilder sb;
        if (!processRecord.isKilledByAm()) {
            sb = null;
        } else {
            sb = new java.lang.StringBuilder();
            sb.append("killedByAm=true;");
        }
        if (this.mProcessNames.get(processRecord.processName, processRecord.uid) != processRecord) {
            if (sb == null) {
                sb = new java.lang.StringBuilder();
            }
            sb.append("No entry in mProcessNames;");
        }
        if (!processRecord.isPendingStart()) {
            if (sb == null) {
                sb = new java.lang.StringBuilder();
            }
            sb.append("pendingStart=false;");
        }
        if (processRecord.getStartSeq() > j) {
            if (sb == null) {
                sb = new java.lang.StringBuilder();
            }
            sb.append("seq=" + processRecord.getStartSeq() + ",expected=" + j + ";");
        }
        try {
            android.app.AppGlobals.getPackageManager().checkPackageStartable(processRecord.info.packageName, processRecord.userId);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.SecurityException e2) {
            if (this.mService.mConstants.FLAG_PROCESS_START_ASYNC) {
                if (sb == null) {
                    sb = new java.lang.StringBuilder();
                }
                sb.append("Package is frozen;");
            } else {
                throw e2;
            }
        }
        if (sb == null) {
            return null;
        }
        return sb.toString();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean handleProcessStartedLocked(com.android.server.am.ProcessRecord processRecord, android.os.Process.ProcessStartResult processStartResult, long j) {
        if (this.mPendingStarts.get(j) == null) {
            if (processRecord.getPid() == processStartResult.pid) {
                processRecord.setUsingWrapper(processStartResult.usingWrapper);
                return false;
            }
            return false;
        }
        return handleProcessStartedLocked(processRecord, processStartResult.pid, processStartResult.usingWrapper, j, false);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean handleProcessStartedLocked(com.android.server.am.ProcessRecord processRecord, int i, boolean z, long j, boolean z2) {
        com.android.server.am.ProcessRecord processRecord2;
        this.mPendingStarts.remove(j);
        java.lang.String isProcStartValidLocked = isProcStartValidLocked(processRecord, j);
        if (isProcStartValidLocked != null) {
            android.util.Slog.w("ActivityManager", processRecord + " start not valid, killing pid=" + i + ", " + isProcStartValidLocked);
            processRecord.setPendingStart(false);
            android.os.Process.killProcessQuiet(i);
            int pid = processRecord.getPid();
            if (pid != 0) {
                android.os.Process.killProcessGroup(processRecord.uid, pid);
            }
            noteAppKill(processRecord, 13, 13, isProcStartValidLocked);
            processRecord.doEarlyCleanupIfNecessaryLocked();
            return false;
        }
        this.mService.mBatteryStatsService.noteProcessStart(processRecord.processName, processRecord.info.uid);
        checkSlow(processRecord.getStartTime(), "startProcess: done updating battery stats");
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_PROC_START, java.lang.Integer.valueOf(android.os.UserHandle.getUserId(processRecord.getStartUid())), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(processRecord.getStartUid()), processRecord.processName, processRecord.getHostingRecord().getType(), processRecord.getHostingRecord().getName() != null ? processRecord.getHostingRecord().getName() : "");
        try {
            android.app.AppGlobals.getPackageManager().logAppProcessStartIfNeeded(processRecord.info.packageName, processRecord.processName, processRecord.uid, processRecord.getSeInfo(), processRecord.info.sourceDir, i);
        } catch (android.os.RemoteException e) {
        }
        com.android.server.Watchdog.getInstance().processStarted(processRecord.processName, i);
        checkSlow(processRecord.getStartTime(), "startProcess: building log message");
        java.lang.StringBuilder sb = this.mStringBuilder;
        sb.setLength(0);
        sb.append("Start proc ");
        sb.append(i);
        sb.append(':');
        sb.append(processRecord.processName);
        sb.append('/');
        android.os.UserHandle.formatUid(sb, processRecord.getStartUid());
        if (processRecord.getIsolatedEntryPoint() != null) {
            sb.append(" [");
            sb.append(processRecord.getIsolatedEntryPoint());
            sb.append("]");
        }
        sb.append(" for ");
        sb.append(processRecord.getHostingRecord().getType());
        if (processRecord.getHostingRecord().getName() != null) {
            sb.append(" ");
            sb.append(processRecord.getHostingRecord().getName());
        }
        this.mService.reportUidInfoMessageLocked("ActivityManager", sb.toString(), processRecord.getStartUid());
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                processRecord.setPid(i);
                processRecord.setUsingWrapper(z);
                processRecord.setPendingStart(false);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        checkSlow(processRecord.getStartTime(), "startProcess: starting to update pids map");
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord2 = this.mService.mPidsSelfLocked.get(i);
        }
        if (processRecord2 != null && !processRecord.isolated) {
            android.util.Slog.wtf("ActivityManager", "handleProcessStartedLocked process:" + processRecord.processName + " startSeq:" + processRecord.getStartSeq() + " pid:" + i + " belongs to another existing app:" + processRecord2.processName + " startSeq:" + processRecord2.getStartSeq());
            this.mService.cleanUpApplicationRecordLocked(processRecord2, i, false, false, -1, true, false);
        }
        this.mService.addPidLocked(processRecord);
        synchronized (this.mService.mPidsSelfLocked) {
            if (!z2) {
                try {
                    android.os.Message obtainMessage = this.mService.mHandler.obtainMessage(20);
                    obtainMessage.obj = processRecord;
                    this.mService.mHandler.sendMessageDelayed(obtainMessage, z ? 1200000L : com.android.server.am.ActivityManagerService.PROC_START_TIMEOUT);
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }
        dispatchProcessStarted(processRecord, i);
        checkSlow(processRecord.getStartTime(), "startProcess: done updating pids map");
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void removeLruProcessLocked(com.android.server.am.ProcessRecord processRecord) {
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (lastIndexOf >= 0) {
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (!processRecord.isKilled()) {
                        if (processRecord.isPersistent()) {
                            android.util.Slog.w("ActivityManager", "Removing persistent process that hasn't been killed: " + processRecord);
                        } else {
                            android.util.Slog.wtfStack("ActivityManager", "Removing process that hasn't been killed: " + processRecord);
                            if (processRecord.getPid() > 0) {
                                android.os.Process.killProcessQuiet(processRecord.getPid());
                                killProcessGroup(processRecord.uid, processRecord.getPid());
                                noteAppKill(processRecord, 13, 16, "hasn't been killed");
                            } else {
                                processRecord.setPendingStart(false);
                            }
                        }
                    }
                    if (lastIndexOf < this.mLruProcessActivityStart) {
                        this.mLruProcessActivityStart--;
                    }
                    if (lastIndexOf < this.mLruProcessServiceStart) {
                        this.mLruProcessServiceStart--;
                    }
                    this.mLruProcesses.remove(lastIndexOf);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
        this.mService.removeOomAdjTargetLocked(processRecord, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean killPackageProcessesLSP(java.lang.String str, int i, int i2, int i3, int i4, int i5, java.lang.String str2) {
        return killPackageProcessesLSP(str, i, i2, i3, false, true, true, false, false, false, i4, i5, str2);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killAppZygotesLocked(java.lang.String str, int i, int i2, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.SparseArray sparseArray : this.mAppZygotes.getMap().values()) {
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                int keyAt = sparseArray.keyAt(i3);
                if ((i2 == -1 || android.os.UserHandle.getUserId(keyAt) == i2) && (i < 0 || android.os.UserHandle.getAppId(keyAt) == i)) {
                    android.os.AppZygote appZygote = (android.os.AppZygote) sparseArray.valueAt(i3);
                    if (str == null || str.equals(appZygote.getAppInfo().packageName)) {
                        arrayList.add(appZygote);
                    }
                }
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            killAppZygoteIfNeededLocked((android.os.AppZygote) it.next(), z);
        }
    }

    private static boolean freezePackageCgroup(int i, boolean z) {
        try {
            android.os.Process.freezeCgroupUid(i, z);
            return true;
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e("ActivityManager", "Unable to " + (z ? "freeze" : "unfreeze") + " cgroup uid: " + i + ": " + e);
            return false;
        }
    }

    private static boolean unfreezePackageCgroup(int i) {
        return freezePackageCgroup(i, false);
    }

    private static void freezeBinderAndPackageCgroup(java.util.List<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean>> list, int i) {
        int freezeBinder;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            int pid = ((com.android.server.am.ProcessRecord) list.get(i2).first).getPid();
            if (pid > 0) {
                int i3 = 0;
                while (true) {
                    try {
                        freezeBinder = com.android.server.am.CachedAppOptimizer.freezeBinder(pid, true, 10);
                        if (freezeBinder != (-android.system.OsConstants.EAGAIN)) {
                            break;
                        }
                        int i4 = i3 + 1;
                        if (i3 >= 1) {
                            break;
                        } else {
                            i3 = i4;
                        }
                    } catch (java.lang.RuntimeException e) {
                        android.util.Slog.e("ActivityManager", "Unable to freeze binder for " + pid + ": " + e);
                    }
                }
                if (freezeBinder != 0) {
                    android.util.Slog.e("ActivityManager", "Unable to freeze binder for " + pid + ": " + freezeBinder);
                }
            }
        }
        freezePackageCgroup(i, true);
    }

    private static java.util.List<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean>> getUIDSublist(java.util.List<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean>> list, int i) {
        int i2 = ((com.android.server.am.ProcessRecord) list.get(i).first).uid;
        int i3 = i + 1;
        while (i3 < list.size() && ((com.android.server.am.ProcessRecord) list.get(i3).first).uid == i2) {
            i3++;
        }
        return list.subList(i, i3);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean killPackageProcessesLSP(java.lang.String str, int i, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i4, int i5, java.lang.String str2) {
        boolean containsKey;
        boolean z7;
        boolean z8;
        android.content.pm.PackageManagerInternal packageManagerInternal = this.mService.getPackageManagerInternal();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mProcessNames.getMap().size();
        for (int i6 = 0; i6 < size; i6++) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().valueAt(i6);
            int size2 = sparseArray.size();
            for (int i7 = 0; i7 < size2; i7++) {
                com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i7);
                if (!processRecord.isPersistent() || z4) {
                    if (processRecord.isRemoved()) {
                        if (z3) {
                            if (!z6 && str != null) {
                                z8 = (processRecord.getPkgList().containsKey(str) || processRecord.getPkgDeps() == null || !processRecord.getPkgDeps().contains(str) || processRecord.info == null || packageManagerInternal.isPackageFrozen(processRecord.info.packageName, processRecord.uid, processRecord.userId)) ? false : true;
                            } else {
                                z8 = false;
                            }
                            arrayList.add(new android.util.Pair(processRecord, java.lang.Boolean.valueOf(z8)));
                        }
                    } else if (processRecord.mState.getSetAdj() >= i3) {
                        if (str == null) {
                            if (i2 != -1) {
                                if (processRecord.userId != i2) {
                                }
                            }
                            if (i >= 0 && android.os.UserHandle.getAppId(processRecord.uid) != i) {
                            }
                            z7 = false;
                        } else {
                            boolean z9 = processRecord.getPkgDeps() != null && processRecord.getPkgDeps().contains(str);
                            if ((z9 || android.os.UserHandle.getAppId(processRecord.uid) == i) && ((i2 == -1 || processRecord.userId == i2) && ((containsKey = processRecord.getPkgList().containsKey(str)) || z9))) {
                                if (!containsKey && z9 && !z6 && processRecord.info != null && !packageManagerInternal.isPackageFrozen(processRecord.info.packageName, processRecord.uid, processRecord.userId)) {
                                    z7 = true;
                                }
                                z7 = false;
                            }
                        }
                        if (!z3) {
                            return true;
                        }
                        if (z5) {
                            processRecord.setRemoved(true);
                        }
                        arrayList.add(new android.util.Pair(processRecord, java.lang.Boolean.valueOf(z7)));
                    }
                }
            }
        }
        boolean z10 = i >= 10000 && i <= 19999;
        if (z10) {
            arrayList.sort(new java.util.Comparator() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$killPackageProcessesLSP$2;
                    lambda$killPackageProcessesLSP$2 = com.android.server.am.ProcessList.lambda$killPackageProcessesLSP$2((android.util.Pair) obj, (android.util.Pair) obj2);
                    return lambda$killPackageProcessesLSP$2;
                }
            });
        }
        int i8 = 0;
        while (i8 < arrayList.size()) {
            java.util.List<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean>> uIDSublist = getUIDSublist(arrayList, i8);
            int i9 = ((com.android.server.am.ProcessRecord) uIDSublist.get(0).first).uid;
            boolean z11 = z10 && android.os.UserHandle.getAppId(i9) == i;
            if (z11) {
                freezeBinderAndPackageCgroup(uIDSublist, i9);
            }
            for (android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean> pair : uIDSublist) {
                removeProcessLocked((com.android.server.am.ProcessRecord) pair.first, z, z2 || ((java.lang.Boolean) pair.second).booleanValue(), i4, i5, str2, !z11);
                i9 = i9;
                uIDSublist = uIDSublist;
            }
            int i10 = i9;
            java.util.List<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Boolean>> list = uIDSublist;
            killAppZygotesLocked(str, i, i2, false);
            if (z11) {
                unfreezePackageCgroup(i10);
            }
            i8 += list.size();
        }
        this.mService.updateOomAdjLocked(12);
        if (arrayList.size() > 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$killPackageProcessesLSP$2(android.util.Pair pair, android.util.Pair pair2) {
        return java.lang.Integer.compare(((com.android.server.am.ProcessRecord) pair.first).uid, ((com.android.server.am.ProcessRecord) pair2.first).uid);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean removeProcessLocked(com.android.server.am.ProcessRecord processRecord, boolean z, boolean z2, int i, java.lang.String str) {
        return removeProcessLocked(processRecord, z, z2, i, 0, str, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean removeProcessLocked(com.android.server.am.ProcessRecord processRecord, boolean z, boolean z2, int i, int i2, java.lang.String str) {
        return removeProcessLocked(processRecord, z, z2, i, i2, str, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean removeProcessLocked(com.android.server.am.ProcessRecord processRecord, boolean z, boolean z2, int i, int i2, java.lang.String str, boolean z3) {
        boolean z4;
        boolean z5;
        java.lang.String str2 = processRecord.processName;
        int i3 = processRecord.uid;
        if (((com.android.server.am.ProcessRecord) this.mProcessNames.get(str2, i3)) != processRecord) {
            android.util.Slog.w("ActivityManager", "Ignoring remove of inactive process: " + processRecord);
            return false;
        }
        removeProcessNameLocked(str2, i3);
        this.mService.mAtmInternal.clearHeavyWeightProcessIfEquals(processRecord.getWindowProcessController());
        int pid = processRecord.getPid();
        if ((pid > 0 && pid != com.android.server.am.ActivityManagerService.MY_PID) || (pid == 0 && processRecord.isPendingStart())) {
            if (pid > 0) {
                this.mService.removePidLocked(pid, processRecord);
                processRecord.setBindMountPending(false);
                this.mService.mHandler.removeMessages(20, processRecord);
                this.mService.mBatteryStatsService.noteProcessFinish(processRecord.processName, processRecord.info.uid);
                if (processRecord.isolated) {
                    this.mService.mBatteryStatsService.removeIsolatedUid(processRecord.uid, processRecord.info.uid);
                    this.mService.getPackageManagerInternal().removeIsolatedUid(processRecord.uid);
                }
            }
            if (!processRecord.isPersistent() || processRecord.isolated) {
                z4 = false;
                z5 = false;
            } else if (!z) {
                z4 = true;
                z5 = false;
            } else {
                z5 = true;
                z4 = false;
            }
            processRecord.killLocked(str, i, i2, true, z3);
            this.mService.handleAppDiedLocked(processRecord, pid, z4, z2, false);
            if (z4) {
                removeLruProcessLocked(processRecord);
                this.mService.addAppLocked(processRecord.info, null, false, null, 0);
            }
            return z5;
        }
        this.mRemovedProcesses.add(processRecord);
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void addProcessNameLocked(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                com.android.server.am.ProcessRecord removeProcessNameLocked = removeProcessNameLocked(processRecord.processName, processRecord.uid);
                if (removeProcessNameLocked == processRecord && processRecord.isPersistent()) {
                    android.util.Slog.w("ActivityManager", "Re-adding persistent process " + processRecord);
                    processRecord.resetCrashingOnRestart();
                } else if (removeProcessNameLocked != null) {
                    if (removeProcessNameLocked.isKilled()) {
                        android.util.Slog.w("ActivityManager", "Existing proc " + removeProcessNameLocked + " was killed " + (android.os.SystemClock.uptimeMillis() - removeProcessNameLocked.getKillTime()) + "ms ago when adding " + processRecord);
                    } else {
                        android.util.Slog.wtf("ActivityManager", "Already have existing proc " + removeProcessNameLocked + " when adding " + processRecord);
                    }
                }
                com.android.server.am.UidRecord uidRecord = this.mActiveUids.get(processRecord.uid);
                if (uidRecord == null) {
                    uidRecord = new com.android.server.am.UidRecord(processRecord.uid, this.mService);
                    if (java.util.Arrays.binarySearch(this.mService.mDeviceIdleTempAllowlist, android.os.UserHandle.getAppId(processRecord.uid)) >= 0 || this.mService.mPendingTempAllowlist.indexOfKey(processRecord.uid) >= 0) {
                        uidRecord.setCurAllowListed(true);
                        uidRecord.setSetAllowListed(true);
                    }
                    uidRecord.updateHasInternetPermission();
                    this.mActiveUids.put(processRecord.uid, uidRecord);
                    com.android.server.am.EventLogTags.writeAmUidRunning(uidRecord.getUid());
                    this.mService.noteUidProcessState(uidRecord.getUid(), uidRecord.getCurProcState(), uidRecord.getCurCapability());
                }
                processRecord.setUidRecord(uidRecord);
                uidRecord.addProcess(processRecord);
                processRecord.setRenderThreadTid(0);
                this.mProcessNames.put(processRecord.processName, processRecord.uid, processRecord);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (processRecord.isolated) {
            this.mIsolatedProcesses.put(processRecord.uid, processRecord);
        }
        if (processRecord.isSdkSandbox) {
            java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mSdkSandboxes.get(processRecord.uid);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
            }
            arrayList.add(processRecord);
            this.mSdkSandboxes.put(android.os.Process.getAppUidForSdkSandboxUid(processRecord.uid), arrayList);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private com.android.server.am.ProcessList.IsolatedUidRange getOrCreateIsolatedUidRangeLocked(android.content.pm.ApplicationInfo applicationInfo, com.android.server.am.HostingRecord hostingRecord) {
        if (hostingRecord == null || !hostingRecord.usesAppZygote()) {
            return this.mGlobalIsolatedUids;
        }
        return this.mAppIsolatedUidRangeAllocator.getOrCreateIsolatedUidRangeLocked(applicationInfo.processName, hostingRecord.getDefiningUid());
    }

    com.android.server.am.ProcessRecord getSharedIsolatedProcess(java.lang.String str, int i, java.lang.String str2) {
        int size = this.mIsolatedProcesses.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.am.ProcessRecord valueAt = this.mIsolatedProcesses.valueAt(i2);
            if (valueAt.info.uid == i && valueAt.info.packageName.equals(str2) && valueAt.processName.equals(str)) {
                return valueAt;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    @android.annotation.Nullable
    java.util.List<java.lang.Integer> getIsolatedProcessesLocked(int i) {
        int size = this.mIsolatedProcesses.size();
        java.util.ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.am.ProcessRecord valueAt = this.mIsolatedProcesses.valueAt(i2);
            if (valueAt.info.uid == i) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(java.lang.Integer.valueOf(valueAt.getPid()));
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    @android.annotation.Nullable
    java.util.List<com.android.server.am.ProcessRecord> getSdkSandboxProcessesForAppLocked(int i) {
        return this.mSdkSandboxes.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ProcessRecord newProcessRecordLocked(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, boolean z, int i, boolean z2, int i2, java.lang.String str2, com.android.server.am.HostingRecord hostingRecord) {
        int i3;
        int i4;
        java.lang.String str3 = str != null ? str : applicationInfo.processName;
        int userId = android.os.UserHandle.getUserId(applicationInfo.uid);
        int i5 = applicationInfo.uid;
        if (z2) {
            i5 = i2;
        }
        if (android.os.Process.isSdkSandboxUid(i5) && (!z2 || str2 == null)) {
            android.util.Slog.e("ActivityManager", "Abort creating new sandbox process as required parameters are missing.");
            return null;
        }
        if (z) {
            if (i == 0) {
                com.android.server.am.ProcessList.IsolatedUidRange orCreateIsolatedUidRangeLocked = getOrCreateIsolatedUidRangeLocked(applicationInfo, hostingRecord);
                if (orCreateIsolatedUidRangeLocked == null || (i4 = orCreateIsolatedUidRangeLocked.allocateIsolatedUidLocked(userId)) == -1) {
                    return null;
                }
            } else {
                i4 = i;
            }
            this.mAppExitInfoTracker.mIsolatedUidRecords.addIsolatedUid(i4, applicationInfo.uid);
            this.mService.getPackageManagerInternal().addIsolatedUid(i4, applicationInfo.uid);
            this.mService.mBatteryStatsService.addIsolatedUid(i4, applicationInfo.uid);
            i3 = i4;
        } else {
            i3 = i5;
        }
        com.android.server.am.ProcessRecord processRecord = new com.android.server.am.ProcessRecord(this.mService, applicationInfo, str3, i3, str2, hostingRecord.getDefiningUid(), hostingRecord.getDefiningProcessName());
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if ((processRecord.getApplicationInfo().flags & 2097152) != 0) {
            try {
                if (this.mService.getPackageManagerInternal().wasPackageEverLaunched(processRecord.getApplicationInfo().packageName, processRecord.userId)) {
                    processRecord.setWasForceStopped(true);
                }
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        if (!z && !z2 && userId == 0 && (applicationInfo.flags & 9) == 9 && android.text.TextUtils.equals(str3, applicationInfo.processName)) {
            processStateRecord.setCurrentSchedulingGroup(2);
            processStateRecord.setSetSchedGroup(2);
            processRecord.setPersistent(true);
            processStateRecord.setMaxAdj(PERSISTENT_PROC_ADJ);
        }
        if (z && i != 0) {
            processStateRecord.setMaxAdj(PERSISTENT_SERVICE_ADJ);
        }
        addProcessNameLocked(processRecord);
        return processRecord;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ProcessRecord removeProcessNameLocked(java.lang.String str, int i) {
        return removeProcessNameLocked(str, i, null);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ProcessRecord removeProcessNameLocked(java.lang.String str, int i, com.android.server.am.ProcessRecord processRecord) {
        int appUidForSdkSandboxUid;
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList;
        com.android.server.am.UidRecord uidRecord;
        com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) this.mProcessNames.get(str, i);
        com.android.server.am.ProcessRecord processRecord3 = processRecord != null ? processRecord : processRecord2;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            if (processRecord == null || processRecord2 == processRecord) {
                try {
                    this.mProcessNames.m1496remove(str, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            if (processRecord3 != null && (uidRecord = processRecord3.getUidRecord()) != null) {
                uidRecord.removeProcess(processRecord3);
                if (uidRecord.getNumOfProcs() == 0) {
                    this.mService.enqueueUidChangeLocked(uidRecord, -1, -2147483647);
                    com.android.server.am.EventLogTags.writeAmUidStopped(i);
                    this.mActiveUids.remove(i);
                    this.mService.mFgsStartTempAllowList.removeUid(processRecord3.info.uid);
                    this.mService.noteUidProcessState(i, 20, 0);
                }
                processRecord3.setUidRecord(null);
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        this.mIsolatedProcesses.remove(i);
        this.mGlobalIsolatedUids.freeIsolatedUidLocked(i);
        if (processRecord3 != null && processRecord3.appZygote) {
            removeProcessFromAppZygoteLocked(processRecord3);
        }
        if (processRecord3 != null && processRecord3.isSdkSandbox && (arrayList = this.mSdkSandboxes.get((appUidForSdkSandboxUid = android.os.Process.getAppUidForSdkSandboxUid(i)))) != null) {
            arrayList.remove(processRecord3);
            if (arrayList.size() == 0) {
                this.mSdkSandboxes.remove(appUidForSdkSandboxUid);
            }
        }
        this.mAppsInBackgroundRestricted.remove(processRecord3);
        return processRecord2;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void updateCoreSettingsLOSP(android.os.Bundle bundle) {
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            android.app.IApplicationThread thread = this.mLruProcesses.get(size).getThread();
            if (thread != null) {
                try {
                    thread.setCoreSettings(bundle);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void killAllBackgroundProcessesExceptLSP(int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mProcessNames.getMap().size();
        for (int i3 = 0; i3 < size; i3++) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().valueAt(i3);
            int size2 = sparseArray.size();
            for (int i4 = 0; i4 < size2; i4++) {
                com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i4);
                if (processRecord.isRemoved() || ((i < 0 || processRecord.info.targetSdkVersion < i) && (i2 < 0 || processRecord.mState.getSetProcState() > i2))) {
                    arrayList.add(processRecord);
                }
            }
        }
        int size3 = arrayList.size();
        for (int i5 = 0; i5 < size3; i5++) {
            removeProcessLocked((com.android.server.am.ProcessRecord) arrayList.get(i5), false, true, 13, 10, "kill all background except");
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void updateAllTimePrefsLOSP(int i) {
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null) {
                try {
                    thread.updateTimePrefs(i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityManager", "Failed to update preferences for: " + processRecord.info.processName);
                }
            }
        }
    }

    void setAllHttpProxy() {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                    com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
                    android.app.IApplicationThread thread = processRecord.getThread();
                    if (processRecord.getPid() != com.android.server.am.ActivityManagerService.MY_PID && thread != null && !processRecord.isolated) {
                        try {
                            thread.updateHttpProxy();
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w("ActivityManager", "Failed to update http proxy for: " + processRecord.info.processName);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        android.app.ActivityThread.updateHttpProxy(this.mService.mContext);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void clearAllDnsCacheLOSP() {
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null) {
                try {
                    thread.clearDnsCache();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityManager", "Failed to clear dns cache for: " + processRecord.info.processName);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void handleAllTrustStorageUpdateLOSP() {
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null) {
                try {
                    thread.handleTrustStorageUpdate();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityManager", "Failed to handle trust storage update for: " + processRecord.info.processName);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private int updateLruProcessInternalLSP(com.android.server.am.ProcessRecord processRecord, long j, int i, int i2, java.lang.String str, java.lang.Object obj, com.android.server.am.ProcessRecord processRecord2) {
        processRecord.setLastActivityTime(j);
        if (processRecord.hasActivitiesOrRecentTasks()) {
            return i;
        }
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (lastIndexOf < 0) {
            android.util.Slog.wtf("ActivityManager", "Adding dependent process " + processRecord + " not on LRU list: " + str + " " + obj + " from " + processRecord2);
            return i;
        }
        if (lastIndexOf >= i) {
            return i;
        }
        if (lastIndexOf >= this.mLruProcessActivityStart && i < this.mLruProcessActivityStart) {
            return i;
        }
        this.mLruProcesses.remove(lastIndexOf);
        if (i > 0) {
            i--;
        }
        this.mLruProcesses.add(i, processRecord);
        processRecord.setLruSeq(i2);
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void updateClientActivitiesOrderingLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2, int i3) {
        int connectionGroup;
        boolean z;
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        if (processRecord.hasActivitiesOrRecentTasks() || processServiceRecord.isTreatedLikeActivity() || !processServiceRecord.hasClientActivities()) {
            return;
        }
        int i4 = processRecord.info.uid;
        int connectionGroup2 = processServiceRecord.getConnectionGroup();
        if (connectionGroup2 > 0) {
            int connectionImportance = processServiceRecord.getConnectionImportance();
            int i5 = i3;
            while (i3 >= i2) {
                com.android.server.am.ProcessRecord processRecord2 = this.mLruProcesses.get(i3);
                com.android.server.am.ProcessServiceRecord processServiceRecord2 = processRecord2.mServices;
                int connectionGroup3 = processServiceRecord2.getConnectionGroup();
                int connectionImportance2 = processServiceRecord2.getConnectionImportance();
                if (processRecord2.info.uid == i4 && connectionGroup3 == connectionGroup2) {
                    if (i3 == i5 && connectionImportance2 >= connectionImportance) {
                        i5--;
                        connectionImportance = connectionImportance2;
                    } else {
                        int i6 = i;
                        while (true) {
                            if (i6 <= i5) {
                                z = false;
                                break;
                            } else if (connectionImportance2 > this.mLruProcesses.get(i6).mServices.getConnectionImportance()) {
                                i6--;
                            } else {
                                this.mLruProcesses.remove(i3);
                                this.mLruProcesses.add(i6, processRecord2);
                                i5--;
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            this.mLruProcesses.remove(i3);
                            this.mLruProcesses.add(i5, processRecord2);
                            i5--;
                            connectionImportance = connectionImportance2;
                        }
                    }
                }
                i3--;
            }
            i3 = i5;
        }
        int i7 = i3;
        while (i3 >= i2) {
            com.android.server.am.ProcessRecord processRecord3 = this.mLruProcesses.get(i3);
            com.android.server.am.ProcessServiceRecord processServiceRecord3 = processRecord3.mServices;
            int connectionGroup4 = processServiceRecord3.getConnectionGroup();
            if (processRecord3.info.uid != i4) {
                if (i3 < i7) {
                    boolean z2 = false;
                    int i8 = 0;
                    int i9 = 0;
                    while (i3 >= i2) {
                        this.mLruProcesses.remove(i3);
                        this.mLruProcesses.add(i7, processRecord3);
                        i3--;
                        if (i3 < i2) {
                            break;
                        }
                        processRecord3 = this.mLruProcesses.get(i3);
                        if (processRecord3.hasActivitiesOrRecentTasks() || processServiceRecord3.isTreatedLikeActivity()) {
                            if (z2) {
                                break;
                            }
                            z2 = true;
                            i7--;
                        } else {
                            if (!processServiceRecord3.hasClientActivities()) {
                                continue;
                            } else if (!z2) {
                                i8 = processRecord3.info.uid;
                                z2 = true;
                                i9 = connectionGroup4;
                            } else if (i8 == 0 || i8 != processRecord3.info.uid || i9 == 0 || i9 != connectionGroup4) {
                                break;
                            }
                            i7--;
                        }
                    }
                }
                do {
                    i7--;
                    if (i7 < i2) {
                        break;
                    }
                } while (this.mLruProcesses.get(i7).info.uid != i4);
                if (i7 >= i2) {
                    int connectionGroup5 = this.mLruProcesses.get(i7).mServices.getConnectionGroup();
                    do {
                        i7--;
                        if (i7 < i2) {
                            break;
                        }
                        com.android.server.am.ProcessRecord processRecord4 = this.mLruProcesses.get(i7);
                        connectionGroup = processRecord4.mServices.getConnectionGroup();
                        if (processRecord4.info.uid != i4) {
                            break;
                        }
                    } while (connectionGroup == connectionGroup5);
                }
                i3 = i7;
            } else {
                i3--;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateLruProcessLocked(com.android.server.am.ProcessRecord processRecord, boolean z, com.android.server.am.ProcessRecord processRecord2) {
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        boolean z2 = processRecord.hasActivitiesOrRecentTasks() || processServiceRecord.hasClientActivities() || processServiceRecord.isTreatedLikeActivity();
        if (!z && z2) {
            return;
        }
        if (processRecord.getPid() == 0 && !processRecord.isPendingStart()) {
            return;
        }
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                updateLruProcessLSP(processRecord, processRecord2, z2, false);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void updateLruProcessLSP(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        this.mLruSeq++;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        processRecord.setLastActivityTime(uptimeMillis);
        if (z) {
            int size = this.mLruProcesses.size();
            if (size > 0 && this.mLruProcesses.get(size - 1) == processRecord) {
                return;
            }
        } else if (this.mLruProcessServiceStart > 0 && this.mLruProcesses.get(this.mLruProcessServiceStart - 1) == processRecord) {
            return;
        }
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (processRecord.isPersistent() && lastIndexOf >= 0) {
            return;
        }
        if (lastIndexOf >= 0) {
            if (lastIndexOf < this.mLruProcessActivityStart) {
                this.mLruProcessActivityStart--;
            }
            if (lastIndexOf < this.mLruProcessServiceStart) {
                this.mLruProcessServiceStart--;
            }
            this.mLruProcesses.remove(lastIndexOf);
        }
        if (z) {
            int size2 = this.mLruProcesses.size();
            i2 = this.mLruProcessServiceStart;
            if (!processRecord.hasActivitiesOrRecentTasks() && !processServiceRecord.isTreatedLikeActivity() && this.mLruProcessActivityStart < (i3 = size2 - 1)) {
                while (i3 > this.mLruProcessActivityStart && this.mLruProcesses.get(i3).info.uid != processRecord.info.uid) {
                    i3--;
                }
                this.mLruProcesses.add(i3, processRecord);
                i = i3 - 1;
                if (i < this.mLruProcessActivityStart) {
                    i = this.mLruProcessActivityStart;
                }
                updateClientActivitiesOrderingLSP(processRecord, i3, this.mLruProcessActivityStart, i);
            } else {
                this.mLruProcesses.add(processRecord);
                i = this.mLruProcesses.size() - 1;
            }
        } else {
            i = -1;
            if (z2) {
                this.mLruProcesses.add(this.mLruProcessActivityStart, processRecord);
                i2 = this.mLruProcessServiceStart;
                this.mLruProcessActivityStart++;
            } else {
                int i4 = this.mLruProcessServiceStart;
                if (processRecord2 != null) {
                    int lastIndexOf2 = this.mLruProcesses.lastIndexOf(processRecord2);
                    if (lastIndexOf2 > lastIndexOf) {
                        lastIndexOf = lastIndexOf2;
                    }
                    if (lastIndexOf >= 0 && i4 > lastIndexOf) {
                        i4 = lastIndexOf;
                    }
                }
                this.mLruProcesses.add(i4, processRecord);
                i2 = i4 - 1;
                this.mLruProcessActivityStart++;
                this.mLruProcessServiceStart++;
                if (i4 > 1) {
                    updateClientActivitiesOrderingLSP(processRecord, this.mLruProcessServiceStart - 1, 0, i2);
                }
            }
        }
        processRecord.setLruSeq(this.mLruSeq);
        int i5 = i2;
        int i6 = i;
        for (int numberOfConnections = processServiceRecord.numberOfConnections() - 1; numberOfConnections >= 0; numberOfConnections--) {
            com.android.server.am.ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(numberOfConnections);
            if (connectionAt.binding != null && !connectionAt.serviceDead && connectionAt.binding.service != null && connectionAt.binding.service.app != null && connectionAt.binding.service.app.getLruSeq() != this.mLruSeq && connectionAt.notHasFlag(1073742128) && !connectionAt.binding.service.app.isPersistent()) {
                if (!connectionAt.binding.service.app.mServices.hasClientActivities()) {
                    i5 = updateLruProcessInternalLSP(connectionAt.binding.service.app, uptimeMillis, i5, this.mLruSeq, "service connection", connectionAt, processRecord);
                } else if (i6 >= 0) {
                    i6 = updateLruProcessInternalLSP(connectionAt.binding.service.app, uptimeMillis, i6, this.mLruSeq, "service connection", connectionAt, processRecord);
                }
            }
        }
        com.android.server.am.ProcessProviderRecord processProviderRecord = processRecord.mProviders;
        int i7 = i5;
        for (int numberOfProviderConnections = processProviderRecord.numberOfProviderConnections() - 1; numberOfProviderConnections >= 0; numberOfProviderConnections--) {
            com.android.server.am.ContentProviderRecord contentProviderRecord = processProviderRecord.getProviderConnectionAt(numberOfProviderConnections).provider;
            if (contentProviderRecord.proc != null && contentProviderRecord.proc.getLruSeq() != this.mLruSeq && !contentProviderRecord.proc.isPersistent()) {
                i7 = updateLruProcessInternalLSP(contentProviderRecord.proc, uptimeMillis, i7, this.mLruSeq, "provider reference", contentProviderRecord, processRecord);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessRecord getLRURecordForAppLOSP(android.app.IApplicationThread iApplicationThread) {
        if (iApplicationThread == null) {
            return null;
        }
        return getLRURecordForAppLOSP(iApplicationThread.asBinder());
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessRecord getLRURecordForAppLOSP(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null && thread.asBinder() == iBinder) {
                return processRecord;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean haveBackgroundProcessLOSP() {
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            if (processRecord.getThread() != null && processRecord.mState.getSetProcState() >= 16) {
                return true;
            }
        }
        return false;
    }

    private static int procStateToImportance(int i, int i2, android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i3) {
        int procStateToImportanceForTargetSdk = android.app.ActivityManager.RunningAppProcessInfo.procStateToImportanceForTargetSdk(i, i3);
        if (procStateToImportanceForTargetSdk == 400) {
            runningAppProcessInfo.lru = i2;
        } else {
            runningAppProcessInfo.lru = 0;
        }
        return procStateToImportanceForTargetSdk;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void fillInProcMemInfoLOSP(com.android.server.am.ProcessRecord processRecord, android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i) {
        runningAppProcessInfo.pid = processRecord.getPid();
        runningAppProcessInfo.uid = processRecord.info.uid;
        if (processRecord.getWindowProcessController().isHeavyWeightProcess()) {
            runningAppProcessInfo.flags |= 1;
        }
        if (processRecord.isPersistent()) {
            runningAppProcessInfo.flags |= 2;
        }
        if (processRecord.hasActivities()) {
            runningAppProcessInfo.flags |= 4;
        }
        runningAppProcessInfo.lastTrimLevel = processRecord.mProfile.getTrimMemoryLevel();
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        int curAdj = processStateRecord.getCurAdj();
        int curProcState = processStateRecord.getCurProcState();
        runningAppProcessInfo.importance = procStateToImportance(curProcState, curAdj, runningAppProcessInfo, i);
        runningAppProcessInfo.importanceReasonCode = processStateRecord.getAdjTypeCode();
        runningAppProcessInfo.processState = curProcState;
        runningAppProcessInfo.isFocused = processRecord == this.mService.getTopApp();
        runningAppProcessInfo.lastActivityTime = processRecord.getLastActivityTime();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcessesLOSP(boolean z, int i, boolean z2, int i2, int i3) {
        int activityPid;
        java.util.ArrayList arrayList = null;
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
            com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
            if ((z || processRecord.userId == i) && ((z2 || processRecord.uid == i2) && processRecord.getThread() != null && !processErrorStateRecord.isCrashing() && !processErrorStateRecord.isNotResponding())) {
                android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new android.app.ActivityManager.RunningAppProcessInfo(processRecord.processName, processRecord.getPid(), processRecord.getPackageList());
                if (processRecord.getPkgDeps() != null) {
                    runningAppProcessInfo.pkgDeps = (java.lang.String[]) processRecord.getPkgDeps().toArray(new java.lang.String[processRecord.getPkgDeps().size()]);
                }
                fillInProcMemInfoLOSP(processRecord, runningAppProcessInfo, i3);
                if (processStateRecord.getAdjSource() instanceof com.android.server.am.ProcessRecord) {
                    runningAppProcessInfo.importanceReasonPid = ((com.android.server.am.ProcessRecord) processStateRecord.getAdjSource()).getPid();
                    runningAppProcessInfo.importanceReasonImportance = android.app.ActivityManager.RunningAppProcessInfo.procStateToImportance(processStateRecord.getAdjSourceProcState());
                } else if ((processStateRecord.getAdjSource() instanceof com.android.server.wm.ActivityServiceConnectionsHolder) && (activityPid = ((com.android.server.wm.ActivityServiceConnectionsHolder) processStateRecord.getAdjSource()).getActivityPid()) != -1) {
                    runningAppProcessInfo.importanceReasonPid = activityPid;
                }
                if (processStateRecord.getAdjTarget() instanceof android.content.ComponentName) {
                    runningAppProcessInfo.importanceReasonComponent = (android.content.ComponentName) processStateRecord.getAdjTarget();
                }
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(runningAppProcessInfo);
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getLruSizeLOSP() {
        return this.mLruProcesses.size();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.util.ArrayList<com.android.server.am.ProcessRecord> getLruProcessesLOSP() {
        return this.mLruProcesses;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    java.util.ArrayList<com.android.server.am.ProcessRecord> getLruProcessesLSP() {
        return this.mLruProcesses;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    @com.android.internal.annotations.VisibleForTesting
    void setLruProcessServiceStartLSP(int i) {
        this.mLruProcessServiceStart = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getLruProcessServiceStartLOSP() {
        return this.mLruProcessServiceStart;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void forEachLruProcessesLOSP(boolean z, @android.annotation.NonNull java.util.function.Consumer<com.android.server.am.ProcessRecord> consumer) {
        if (z) {
            int size = this.mLruProcesses.size();
            for (int i = 0; i < size; i++) {
                consumer.accept(this.mLruProcesses.get(i));
            }
            return;
        }
        for (int size2 = this.mLruProcesses.size() - 1; size2 >= 0; size2--) {
            consumer.accept(this.mLruProcesses.get(size2));
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    <R> R searchEachLruProcessesLOSP(boolean z, @android.annotation.NonNull java.util.function.Function<com.android.server.am.ProcessRecord, R> function) {
        if (z) {
            int size = this.mLruProcesses.size();
            for (int i = 0; i < size; i++) {
                R apply = function.apply(this.mLruProcesses.get(i));
                if (apply != null) {
                    return apply;
                }
            }
            return null;
        }
        for (int size2 = this.mLruProcesses.size() - 1; size2 >= 0; size2--) {
            R apply2 = function.apply(this.mLruProcesses.get(size2));
            if (apply2 != null) {
                return apply2;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isInLruListLOSP(com.android.server.am.ProcessRecord processRecord) {
        return this.mLruProcesses.contains(processRecord);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getLruSeqLOSP() {
        return this.mLruSeq;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ProcessList.MyProcessMap getProcessNamesLOSP() {
        return this.mProcessNames;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void dumpLruListHeaderLocked(java.io.PrintWriter printWriter) {
        printWriter.print("  Process LRU list (sorted by oom_adj, ");
        printWriter.print(this.mLruProcesses.size());
        printWriter.print(" total, non-act at ");
        printWriter.print(this.mLruProcesses.size() - this.mLruProcessActivityStart);
        printWriter.print(", non-svc at ");
        printWriter.print(this.mLruProcesses.size() - this.mLruProcessServiceStart);
        printWriter.println("):");
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void dumpLruEntryLocked(java.io.PrintWriter printWriter, int i, com.android.server.am.ProcessRecord processRecord, java.lang.String str) {
        printWriter.print(str);
        printWriter.print('#');
        if (i < 10) {
            printWriter.print(' ');
        }
        printWriter.print(i);
        printWriter.print(": ");
        boolean z = false;
        printWriter.print(makeOomAdjString(processRecord.mState.getSetAdj(), false));
        printWriter.print(' ');
        printWriter.print(makeProcStateString(processRecord.mState.getCurProcState()));
        printWriter.print(' ');
        android.app.ActivityManager.printCapabilitiesSummary(printWriter, processRecord.mState.getCurCapability());
        printWriter.print(' ');
        printWriter.print(processRecord.toShortString());
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        if (processRecord.hasActivitiesOrRecentTasks() || processServiceRecord.hasClientActivities() || processServiceRecord.isTreatedLikeActivity()) {
            printWriter.print(" act:");
            boolean z2 = true;
            if (processRecord.hasActivities()) {
                printWriter.print(com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_CMD);
                z = true;
            }
            if (processRecord.hasRecentTasks()) {
                if (z) {
                    printWriter.print("|");
                }
                printWriter.print(com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_CMD);
                z = true;
            }
            if (!processServiceRecord.hasClientActivities()) {
                z2 = z;
            } else {
                if (z) {
                    printWriter.print("|");
                }
                printWriter.print("client");
            }
            if (processServiceRecord.isTreatedLikeActivity()) {
                if (z2) {
                    printWriter.print("|");
                }
                printWriter.print("treated");
            }
        }
        printWriter.println();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean dumpLruLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        boolean z;
        int size = this.mLruProcesses.size();
        java.lang.String str3 = "  ";
        if (str2 == null) {
            printWriter.println("ACTIVITY MANAGER LRU PROCESSES (dumpsys activity lru)");
        } else {
            for (int i = size - 1; i >= 0; i--) {
                com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(i);
                if (str == null || processRecord.getPkgList().containsKey(str)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (!z) {
                return false;
            }
            printWriter.print(str2);
            printWriter.println("Raw LRU list (dumpsys activity lru):");
            str3 = str2 + "  ";
        }
        int i2 = size - 1;
        boolean z2 = true;
        while (i2 >= this.mLruProcessActivityStart) {
            com.android.server.am.ProcessRecord processRecord2 = this.mLruProcesses.get(i2);
            if (str == null || processRecord2.getPkgList().containsKey(str)) {
                if (z2) {
                    printWriter.print(str3);
                    printWriter.println("Activities:");
                    z2 = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord2, str3);
            }
            i2--;
        }
        boolean z3 = true;
        while (i2 >= this.mLruProcessServiceStart) {
            com.android.server.am.ProcessRecord processRecord3 = this.mLruProcesses.get(i2);
            if (str == null || processRecord3.getPkgList().containsKey(str)) {
                if (z3) {
                    printWriter.print(str3);
                    printWriter.println("Services:");
                    z3 = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord3, str3);
            }
            i2--;
        }
        boolean z4 = true;
        while (i2 >= 0) {
            com.android.server.am.ProcessRecord processRecord4 = this.mLruProcesses.get(i2);
            if (str == null || processRecord4.getPkgList().containsKey(str)) {
                if (z4) {
                    printWriter.print(str3);
                    printWriter.println("Other:");
                    z4 = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord4, str3);
            }
            i2--;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void dumpProcessesLSP(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, java.lang.String str, int i2) {
        boolean z2;
        int i3;
        boolean z3;
        printWriter.println("ACTIVITY MANAGER RUNNING PROCESSES (dumpsys activity processes)");
        if (z || str != null) {
            int size = this.mProcessNames.getMap().size();
            z2 = false;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().valueAt(i5);
                int size2 = sparseArray.size();
                for (int i6 = 0; i6 < size2; i6++) {
                    com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i6);
                    if (str == null || processRecord.getPkgList().containsKey(str)) {
                        if (!z2) {
                            printWriter.println("  All known processes:");
                            z2 = true;
                        }
                        printWriter.print(processRecord.isPersistent() ? "  *PERS*" : "  *APP*");
                        printWriter.print(" UID ");
                        printWriter.print(sparseArray.keyAt(i6));
                        printWriter.print(" ");
                        printWriter.println(processRecord);
                        processRecord.dump(printWriter, "    ");
                        if (processRecord.isPersistent()) {
                            i4++;
                        }
                    }
                }
            }
            i3 = i4;
        } else {
            z2 = false;
            i3 = 0;
        }
        if (this.mIsolatedProcesses.size() > 0) {
            int size3 = this.mIsolatedProcesses.size();
            boolean z4 = false;
            for (int i7 = 0; i7 < size3; i7++) {
                com.android.server.am.ProcessRecord valueAt = this.mIsolatedProcesses.valueAt(i7);
                if (str == null || valueAt.getPkgList().containsKey(str)) {
                    if (!z4) {
                        if (z2) {
                            printWriter.println();
                        }
                        printWriter.println("  Isolated process list (sorted by uid):");
                        z4 = true;
                        z2 = true;
                    }
                    printWriter.print("    Isolated #");
                    printWriter.print(i7);
                    printWriter.print(": ");
                    printWriter.println(valueAt);
                }
            }
        }
        boolean dumpActiveInstruments = this.mService.dumpActiveInstruments(printWriter, str, z2);
        if (dumpOomLocked(fileDescriptor, printWriter, dumpActiveInstruments, strArr, i, z, str, false)) {
            dumpActiveInstruments = true;
        }
        if (this.mActiveUids.size() > 0) {
            dumpActiveInstruments |= this.mActiveUids.dump(printWriter, str, i2, "UID states:", dumpActiveInstruments);
        }
        if (z) {
            dumpActiveInstruments |= this.mService.mUidObserverController.dumpValidateUids(printWriter, str, i2, "UID validation:", dumpActiveInstruments);
        }
        if (dumpActiveInstruments) {
            printWriter.println();
        }
        if (dumpLruLocked(printWriter, str, "  ")) {
            dumpActiveInstruments = true;
        }
        if (getLruSizeLOSP() <= 0) {
            z3 = dumpActiveInstruments;
        } else {
            if (dumpActiveInstruments) {
                printWriter.println();
            }
            dumpLruListHeaderLocked(printWriter);
            dumpProcessOomList(printWriter, this.mService, this.mLruProcesses, "    ", "Proc", "PERS", false, str);
            z3 = true;
        }
        this.mService.dumpOtherProcessesInfoLSP(fileDescriptor, printWriter, z, str, i2, i3, z3);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void writeProcessesToProtoLSP(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str) {
        int size = this.mProcessNames.getMap().size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().valueAt(i2);
            int size2 = sparseArray.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sparseArray.valueAt(i3);
                if (str == null || processRecord.getPkgList().containsKey(str)) {
                    processRecord.dumpDebug(protoOutputStream, 2246267895809L, this.mLruProcesses.indexOf(processRecord));
                    if (processRecord.isPersistent()) {
                        i++;
                    }
                }
            }
        }
        int size3 = this.mIsolatedProcesses.size();
        for (int i4 = 0; i4 < size3; i4++) {
            com.android.server.am.ProcessRecord valueAt = this.mIsolatedProcesses.valueAt(i4);
            if (str == null || valueAt.getPkgList().containsKey(str)) {
                valueAt.dumpDebug(protoOutputStream, 2246267895810L, this.mLruProcesses.indexOf(valueAt));
            }
        }
        int appId = this.mService.getAppId(str);
        this.mActiveUids.dumpProto(protoOutputStream, str, appId, 2246267895812L);
        if (getLruSizeLOSP() > 0) {
            long start = protoOutputStream.start(1146756268038L);
            int lruSizeLOSP = getLruSizeLOSP();
            protoOutputStream.write(1120986464257L, lruSizeLOSP);
            protoOutputStream.write(1120986464258L, lruSizeLOSP - this.mLruProcessActivityStart);
            protoOutputStream.write(1120986464259L, lruSizeLOSP - this.mLruProcessServiceStart);
            writeProcessOomListToProto(protoOutputStream, 2246267895812L, this.mService, this.mLruProcesses, true, str);
            protoOutputStream.end(start);
        }
        this.mService.writeOtherProcessesInfoToProtoLSP(protoOutputStream, str, appId, i);
    }

    private static java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> sortProcessOomList(java.util.List<com.android.server.am.ProcessRecord> list, java.lang.String str) {
        java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> arrayList = new java.util.ArrayList<>(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.ProcessRecord processRecord = list.get(i);
            if (str == null || processRecord.getPkgList().containsKey(str)) {
                arrayList.add(new android.util.Pair<>(list.get(i), java.lang.Integer.valueOf(i)));
            }
        }
        java.util.Collections.sort(arrayList, new java.util.Comparator<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>>() { // from class: com.android.server.am.ProcessList.3
            @Override // java.util.Comparator
            public int compare(android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer> pair, android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer> pair2) {
                int setAdj = ((com.android.server.am.ProcessRecord) pair2.first).mState.getSetAdj() - ((com.android.server.am.ProcessRecord) pair.first).mState.getSetAdj();
                if (setAdj != 0) {
                    return setAdj;
                }
                int setProcState = ((com.android.server.am.ProcessRecord) pair2.first).mState.getSetProcState() - ((com.android.server.am.ProcessRecord) pair.first).mState.getSetProcState();
                if (setProcState != 0) {
                    return setProcState;
                }
                int intValue = ((java.lang.Integer) pair2.second).intValue() - ((java.lang.Integer) pair.second).intValue();
                if (intValue != 0) {
                    return intValue;
                }
                return 0;
            }
        });
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v2 */
    private static boolean writeProcessOomListToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, com.android.server.am.ActivityManagerService activityManagerService, java.util.List<com.android.server.am.ProcessRecord> list, boolean z, java.lang.String str) {
        int i;
        long j2;
        java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> arrayList;
        java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> sortProcessOomList = sortProcessOomList(list, str);
        if (sortProcessOomList.isEmpty()) {
            return false;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        ?? r7 = 1;
        int size = sortProcessOomList.size() - 1;
        while (size >= 0) {
            com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sortProcessOomList.get(size).first;
            com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
            com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
            long start = protoOutputStream.start(j);
            java.lang.String makeOomAdjString = makeOomAdjString(processStateRecord.getSetAdj(), r7);
            protoOutputStream.write(1133871366145L, processRecord.isPersistent());
            protoOutputStream.write(1120986464258L, (list.size() - r7) - ((java.lang.Integer) sortProcessOomList.get(size).second).intValue());
            protoOutputStream.write(1138166333443L, makeOomAdjString);
            switch (processStateRecord.getSetSchedGroup()) {
                case 0:
                    i = 0;
                    break;
                case 1:
                default:
                    i = -1;
                    break;
                case 2:
                    i = r7;
                    break;
                case 3:
                    i = 2;
                    break;
                case 4:
                    i = 3;
                    break;
            }
            if (i != -1) {
                protoOutputStream.write(1159641169924L, i);
            }
            if (processStateRecord.hasForegroundActivities()) {
                protoOutputStream.write(1133871366149L, (boolean) r7);
            } else if (processServiceRecord.hasForegroundServices()) {
                protoOutputStream.write(1133871366150L, (boolean) r7);
            }
            protoOutputStream.write(1159641169927L, makeProcStateProtoEnum(processStateRecord.getCurProcState()));
            protoOutputStream.write(1120986464264L, processRecord.mProfile.getTrimMemoryLevel());
            processRecord.dumpDebug(protoOutputStream, 1146756268041L);
            protoOutputStream.write(1138166333450L, processStateRecord.getAdjType());
            if (processStateRecord.getAdjSource() != null || processStateRecord.getAdjTarget() != null) {
                if (processStateRecord.getAdjTarget() instanceof android.content.ComponentName) {
                    ((android.content.ComponentName) processStateRecord.getAdjTarget()).dumpDebug(protoOutputStream, 1146756268043L);
                } else if (processStateRecord.getAdjTarget() != null) {
                    protoOutputStream.write(1138166333452L, processStateRecord.getAdjTarget().toString());
                }
                if (processStateRecord.getAdjSource() instanceof com.android.server.am.ProcessRecord) {
                    ((com.android.server.am.ProcessRecord) processStateRecord.getAdjSource()).dumpDebug(protoOutputStream, 1146756268045L);
                } else if (processStateRecord.getAdjSource() != null) {
                    protoOutputStream.write(1138166333454L, processStateRecord.getAdjSource().toString());
                }
            }
            if (z) {
                long start2 = protoOutputStream.start(1146756268047L);
                protoOutputStream.write(1120986464257L, processStateRecord.getMaxAdj());
                protoOutputStream.write(1120986464258L, processStateRecord.getCurRawAdj());
                protoOutputStream.write(1120986464259L, processStateRecord.getSetRawAdj());
                protoOutputStream.write(1120986464260L, processStateRecord.getCurAdj());
                protoOutputStream.write(1120986464261L, processStateRecord.getSetAdj());
                protoOutputStream.write(1159641169927L, makeProcStateProtoEnum(processStateRecord.getCurProcState()));
                protoOutputStream.write(1159641169928L, makeProcStateProtoEnum(processStateRecord.getSetProcState()));
                protoOutputStream.write(1138166333449L, android.util.DebugUtils.sizeValueToString(processRecord.mProfile.getLastPss() * 1024, new java.lang.StringBuilder()));
                protoOutputStream.write(1138166333450L, android.util.DebugUtils.sizeValueToString(processRecord.mProfile.getLastSwapPss() * 1024, new java.lang.StringBuilder()));
                protoOutputStream.write(1138166333451L, android.util.DebugUtils.sizeValueToString(processRecord.mProfile.getLastCachedPss() * 1024, new java.lang.StringBuilder()));
                protoOutputStream.write(1133871366156L, processStateRecord.isCached());
                protoOutputStream.write(1133871366157L, processStateRecord.isEmpty());
                protoOutputStream.write(1133871366158L, processServiceRecord.hasAboveClient());
                if (processStateRecord.getSetProcState() >= 10) {
                    long j3 = processRecord.mProfile.mLastCpuTime.get();
                    long j4 = uptimeMillis - activityManagerService.mLastPowerCheckUptime;
                    if (j3 == 0 || j4 <= 0) {
                        j2 = uptimeMillis;
                        arrayList = sortProcessOomList;
                    } else {
                        long j5 = processRecord.mProfile.mCurCpuTime.get() - j3;
                        long start3 = protoOutputStream.start(1146756268047L);
                        j2 = uptimeMillis;
                        arrayList = sortProcessOomList;
                        protoOutputStream.write(1112396529665L, j4);
                        protoOutputStream.write(1112396529666L, j5);
                        protoOutputStream.write(1108101562371L, (j5 * 100.0d) / j4);
                        protoOutputStream.end(start3);
                    }
                } else {
                    j2 = uptimeMillis;
                    arrayList = sortProcessOomList;
                }
                protoOutputStream.end(start2);
            } else {
                j2 = uptimeMillis;
                arrayList = sortProcessOomList;
            }
            protoOutputStream.end(start);
            size--;
            sortProcessOomList = arrayList;
            uptimeMillis = j2;
            r7 = 1;
        }
        return true;
    }

    private static boolean dumpProcessOomList(java.io.PrintWriter printWriter, com.android.server.am.ActivityManagerService activityManagerService, java.util.List<com.android.server.am.ProcessRecord> list, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4) {
        char c;
        char c2;
        java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> arrayList;
        int i;
        java.util.ArrayList<android.util.Pair<com.android.server.am.ProcessRecord, java.lang.Integer>> sortProcessOomList = sortProcessOomList(list, str4);
        boolean z2 = false;
        if (sortProcessOomList.isEmpty()) {
            return false;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis() - activityManagerService.mLastPowerCheckUptime;
        int size = sortProcessOomList.size() - 1;
        while (size >= 0) {
            com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) sortProcessOomList.get(size).first;
            com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
            com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
            java.lang.String makeOomAdjString = makeOomAdjString(processStateRecord.getSetAdj(), z2);
            switch (processStateRecord.getSetSchedGroup()) {
                case 0:
                    c = 'b';
                    break;
                case 1:
                    c = 'R';
                    break;
                case 2:
                    c = 'F';
                    break;
                case 3:
                    c = 'T';
                    break;
                case 4:
                    c = 'B';
                    break;
                default:
                    c = '?';
                    break;
            }
            if (processStateRecord.hasForegroundActivities()) {
                c2 = 'A';
            } else if (processServiceRecord.hasForegroundServices()) {
                c2 = 'S';
            } else {
                c2 = ' ';
            }
            java.lang.String makeProcStateString = makeProcStateString(processStateRecord.getCurProcState());
            printWriter.print(str);
            printWriter.print(processRecord.isPersistent() ? str3 : str2);
            printWriter.print(" #");
            int size2 = (list.size() - 1) - ((java.lang.Integer) sortProcessOomList.get(size).second).intValue();
            if (size2 < 10) {
                printWriter.print(' ');
            }
            printWriter.print(size2);
            printWriter.print(": ");
            printWriter.print(makeOomAdjString);
            printWriter.print(' ');
            printWriter.print(c);
            printWriter.print('/');
            printWriter.print(c2);
            printWriter.print('/');
            printWriter.print(makeProcStateString);
            printWriter.print(' ');
            android.app.ActivityManager.printCapabilitiesSummary(printWriter, processStateRecord.getCurCapability());
            printWriter.print(' ');
            printWriter.print(" t:");
            if (processRecord.mProfile.getTrimMemoryLevel() < 10) {
                printWriter.print(' ');
            }
            printWriter.print(processRecord.mProfile.getTrimMemoryLevel());
            printWriter.print(' ');
            printWriter.print(processRecord.toShortString());
            printWriter.print(" (");
            printWriter.print(processStateRecord.getAdjType());
            printWriter.println(')');
            if (processStateRecord.getAdjSource() != null || processStateRecord.getAdjTarget() != null) {
                printWriter.print(str);
                printWriter.print("    ");
                if (processStateRecord.getAdjTarget() instanceof android.content.ComponentName) {
                    printWriter.print(((android.content.ComponentName) processStateRecord.getAdjTarget()).flattenToShortString());
                } else if (processStateRecord.getAdjTarget() != null) {
                    printWriter.print(processStateRecord.getAdjTarget().toString());
                } else {
                    printWriter.print("{null}");
                }
                printWriter.print("<=");
                if (processStateRecord.getAdjSource() instanceof com.android.server.am.ProcessRecord) {
                    printWriter.print("Proc{");
                    printWriter.print(((com.android.server.am.ProcessRecord) processStateRecord.getAdjSource()).toShortString());
                    printWriter.println("}");
                } else if (processStateRecord.getAdjSource() != null) {
                    printWriter.println(processStateRecord.getAdjSource().toString());
                } else {
                    printWriter.println("{null}");
                }
            }
            if (!z) {
                arrayList = sortProcessOomList;
                i = size;
            } else {
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("oom: max=");
                printWriter.print(processStateRecord.getMaxAdj());
                printWriter.print(" curRaw=");
                printWriter.print(processStateRecord.getCurRawAdj());
                printWriter.print(" setRaw=");
                printWriter.print(processStateRecord.getSetRawAdj());
                printWriter.print(" cur=");
                printWriter.print(processStateRecord.getCurAdj());
                printWriter.print(" set=");
                printWriter.println(processStateRecord.getSetAdj());
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("state: cur=");
                printWriter.print(makeProcStateString(processStateRecord.getCurProcState()));
                printWriter.print(" set=");
                printWriter.print(makeProcStateString(processStateRecord.getSetProcState()));
                if (activityManagerService.mAppProfiler.isProfilingPss()) {
                    printWriter.print(" lastPss=");
                    arrayList = sortProcessOomList;
                    i = size;
                    android.util.DebugUtils.printSizeValue(printWriter, processRecord.mProfile.getLastPss() * 1024);
                    printWriter.print(" lastSwapPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, processRecord.mProfile.getLastSwapPss() * 1024);
                    printWriter.print(" lastCachedPss=");
                    android.util.DebugUtils.printSizeValue(printWriter, processRecord.mProfile.getLastCachedPss() * 1024);
                } else {
                    arrayList = sortProcessOomList;
                    i = size;
                    printWriter.print(" lastRss=");
                    android.util.DebugUtils.printSizeValue(printWriter, processRecord.mProfile.getLastRss() * 1024);
                    printWriter.print(" lastCachedRss=");
                    android.util.DebugUtils.printSizeValue(printWriter, processRecord.mProfile.getLastCachedRss() * 1024);
                }
                printWriter.println();
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("cached=");
                printWriter.print(processStateRecord.isCached());
                printWriter.print(" empty=");
                printWriter.print(processStateRecord.isEmpty());
                printWriter.print(" hasAboveClient=");
                printWriter.println(processServiceRecord.hasAboveClient());
                if (processStateRecord.getSetProcState() >= 10) {
                    long j = processRecord.mProfile.mLastCpuTime.get();
                    if (j != 0 && uptimeMillis > 0) {
                        long j2 = processRecord.mProfile.mCurCpuTime.get() - j;
                        printWriter.print(str);
                        printWriter.print("    ");
                        printWriter.print("run cpu over ");
                        android.util.TimeUtils.formatDuration(uptimeMillis, printWriter);
                        printWriter.print(" used ");
                        android.util.TimeUtils.formatDuration(j2, printWriter);
                        printWriter.print(" (");
                        printWriter.print((j2 * 100) / uptimeMillis);
                        printWriter.println("%)");
                    }
                }
            }
            size = i - 1;
            sortProcessOomList = arrayList;
            z2 = false;
        }
        return true;
    }

    private void printOomLevel(java.io.PrintWriter printWriter, java.lang.String str, int i) {
        printWriter.print("    ");
        if (i >= 0) {
            printWriter.print(' ');
            if (i < 10) {
                printWriter.print(' ');
            }
        } else if (i > -10) {
            printWriter.print(' ');
        }
        printWriter.print(i);
        printWriter.print(": ");
        printWriter.print(str);
        printWriter.print(" (");
        printWriter.print(com.android.server.am.ActivityManagerService.stringifySize(getMemLevel(i), 1024));
        printWriter.println(")");
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean dumpOomLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, boolean z, java.lang.String[] strArr, int i, boolean z2, java.lang.String str, boolean z3) {
        boolean z4;
        if (getLruSizeLOSP() <= 0) {
            z4 = z;
        } else {
            if (z) {
                printWriter.println();
            }
            printWriter.println("  OOM levels:");
            printOomLevel(printWriter, "SYSTEM_ADJ", SYSTEM_ADJ);
            printOomLevel(printWriter, "PERSISTENT_PROC_ADJ", PERSISTENT_PROC_ADJ);
            printOomLevel(printWriter, "PERSISTENT_SERVICE_ADJ", PERSISTENT_SERVICE_ADJ);
            printOomLevel(printWriter, "FOREGROUND_APP_ADJ", 0);
            printOomLevel(printWriter, "VISIBLE_APP_ADJ", 100);
            printOomLevel(printWriter, "PERCEPTIBLE_APP_ADJ", 200);
            printOomLevel(printWriter, "PERCEPTIBLE_MEDIUM_APP_ADJ", PERCEPTIBLE_MEDIUM_APP_ADJ);
            printOomLevel(printWriter, "PERCEPTIBLE_LOW_APP_ADJ", 250);
            printOomLevel(printWriter, "BACKUP_APP_ADJ", 300);
            printOomLevel(printWriter, "HEAVY_WEIGHT_APP_ADJ", 400);
            printOomLevel(printWriter, "SERVICE_ADJ", 500);
            printOomLevel(printWriter, "HOME_APP_ADJ", 600);
            printOomLevel(printWriter, "PREVIOUS_APP_ADJ", PREVIOUS_APP_ADJ);
            printOomLevel(printWriter, "SERVICE_B_ADJ", SERVICE_B_ADJ);
            printOomLevel(printWriter, "CACHED_APP_MIN_ADJ", 900);
            printOomLevel(printWriter, "CACHED_APP_MAX_ADJ", 999);
            printWriter.println();
            printWriter.print("  Process OOM control (");
            printWriter.print(getLruSizeLOSP());
            printWriter.print(" total, non-act at ");
            printWriter.print(getLruSizeLOSP() - this.mLruProcessActivityStart);
            printWriter.print(", non-svc at ");
            printWriter.print(getLruSizeLOSP() - this.mLruProcessServiceStart);
            printWriter.println("):");
            dumpProcessOomList(printWriter, this.mService, this.mLruProcesses, "    ", "Proc", "PERS", true, str);
            z4 = true;
        }
        synchronized (this.mService.mAppProfiler.mProfilerLock) {
            this.mService.mAppProfiler.dumpProcessesToGc(printWriter, z4, str);
        }
        printWriter.println();
        this.mService.mAtmInternal.dumpForOom(printWriter);
        return true;
    }

    void registerProcessObserver(android.app.IProcessObserver iProcessObserver) {
        this.mProcessObservers.register(iProcessObserver);
    }

    void unregisterProcessObserver(android.app.IProcessObserver iProcessObserver) {
        this.mProcessObservers.unregister(iProcessObserver);
    }

    void dispatchProcessesChanged() {
        int size;
        int i;
        synchronized (this.mProcessChangeLock) {
            try {
                size = this.mPendingProcessChanges.size();
                if (this.mActiveProcessChanges.length < size) {
                    this.mActiveProcessChanges = new com.android.server.am.ActivityManagerService.ProcessChangeItem[size];
                }
                this.mPendingProcessChanges.toArray(this.mActiveProcessChanges);
                this.mPendingProcessChanges.clear();
            } finally {
            }
        }
        int beginBroadcast = this.mProcessObservers.beginBroadcast();
        while (true) {
            i = 0;
            if (beginBroadcast <= 0) {
                break;
            }
            beginBroadcast--;
            android.app.IProcessObserver broadcastItem = this.mProcessObservers.getBroadcastItem(beginBroadcast);
            if (broadcastItem != null) {
                while (i < size) {
                    try {
                        com.android.server.am.ActivityManagerService.ProcessChangeItem processChangeItem = this.mActiveProcessChanges[i];
                        if ((processChangeItem.changes & 1) != 0) {
                            broadcastItem.onForegroundActivitiesChanged(processChangeItem.pid, processChangeItem.uid, processChangeItem.foregroundActivities);
                        }
                        if ((processChangeItem.changes & 2) != 0) {
                            broadcastItem.onForegroundServicesChanged(processChangeItem.pid, processChangeItem.uid, processChangeItem.foregroundServiceTypes);
                        }
                        i++;
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
        this.mProcessObservers.finishBroadcast();
        synchronized (this.mProcessChangeLock) {
            while (i < size) {
                try {
                    this.mAvailProcessChanges.add(this.mActiveProcessChanges[i]);
                    i++;
                } finally {
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    com.android.server.am.ActivityManagerService.ProcessChangeItem enqueueProcessChangeItemLocked(int i, int i2) {
        com.android.server.am.ActivityManagerService.ProcessChangeItem processChangeItem;
        synchronized (this.mProcessChangeLock) {
            try {
                int size = this.mPendingProcessChanges.size() - 1;
                processChangeItem = null;
                while (size >= 0) {
                    processChangeItem = this.mPendingProcessChanges.get(size);
                    if (processChangeItem.pid != i) {
                        size--;
                    }
                }
                if (size < 0) {
                    int size2 = this.mAvailProcessChanges.size();
                    if (size2 > 0) {
                        processChangeItem = this.mAvailProcessChanges.remove(size2 - 1);
                    } else {
                        processChangeItem = new com.android.server.am.ActivityManagerService.ProcessChangeItem();
                    }
                    processChangeItem.changes = 0;
                    processChangeItem.pid = i;
                    processChangeItem.uid = i2;
                    if (this.mPendingProcessChanges.size() == 0) {
                        this.mService.mUiHandler.obtainMessage(31).sendToTarget();
                    }
                    this.mPendingProcessChanges.add(processChangeItem);
                }
            } finally {
            }
        }
        return processChangeItem;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void scheduleDispatchProcessDiedLocked(int i, int i2) {
        synchronized (this.mProcessChangeLock) {
            try {
                for (int size = this.mPendingProcessChanges.size() - 1; size >= 0; size--) {
                    com.android.server.am.ActivityManagerService.ProcessChangeItem processChangeItem = this.mPendingProcessChanges.get(size);
                    if (i > 0 && processChangeItem.pid == i) {
                        this.mPendingProcessChanges.remove(size);
                        this.mAvailProcessChanges.add(processChangeItem);
                    }
                }
                this.mService.mUiHandler.obtainMessage(32, i, i2, null).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dispatchProcessStarted(com.android.server.am.ProcessRecord processRecord, int i) {
    }

    void dispatchProcessDied(int i, int i2) {
        int beginBroadcast = this.mProcessObservers.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            android.app.IProcessObserver broadcastItem = this.mProcessObservers.getBroadcastItem(beginBroadcast);
            if (broadcastItem != null) {
                try {
                    broadcastItem.onProcessDied(i, i2);
                } catch (android.os.RemoteException e) {
                }
            }
        }
        this.mProcessObservers.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.util.ArrayList<com.android.server.am.ProcessRecord> collectProcessesLOSP(int i, boolean z, java.lang.String[] strArr) {
        int i2;
        if (strArr != null && strArr.length > i && strArr[i].charAt(0) != '-') {
            java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = new java.util.ArrayList<>();
            try {
                i2 = java.lang.Integer.parseInt(strArr[i]);
            } catch (java.lang.NumberFormatException e) {
                i2 = -1;
            }
            for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
                if (processRecord.getPid() > 0 && processRecord.getPid() == i2) {
                    arrayList.add(processRecord);
                } else if (z && processRecord.getPkgList() != null && processRecord.getPkgList().containsKey(strArr[i])) {
                    arrayList.add(processRecord);
                } else if (processRecord.processName.equals(strArr[i])) {
                    arrayList.add(processRecord);
                }
            }
            if (arrayList.size() <= 0) {
                return null;
            }
            return arrayList;
        }
        return new java.util.ArrayList<>(this.mLruProcesses);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    void updateApplicationInfoLOSP(final java.util.List<java.lang.String> list, int i, final boolean z) {
        final android.util.ArrayMap<java.lang.String, android.content.pm.ApplicationInfo> arrayMap = new android.util.ArrayMap<>();
        for (int size = list.size() - 1; size >= 0; size--) {
            java.lang.String str = list.get(size);
            android.content.pm.ApplicationInfo applicationInfo = this.mService.getPackageManagerInternal().getApplicationInfo(str, 1024L, 1000, i);
            if (applicationInfo != null) {
                arrayMap.put(str, applicationInfo);
            }
        }
        this.mService.mActivityTaskManager.updateActivityApplicationInfo(i, arrayMap);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int size2 = this.mLruProcesses.size() - 1; size2 >= 0; size2--) {
            final com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size2);
            if (processRecord.getThread() != null && (i == -1 || processRecord.userId == i)) {
                processRecord.getPkgList().forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.am.ProcessList.lambda$updateApplicationInfoLOSP$3(z, list, arrayMap, processRecord, arrayList, (java.lang.String) obj);
                    }
                });
            }
        }
        this.mService.mActivityTaskManager.updateAssetConfiguration(arrayList, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateApplicationInfoLOSP$3(boolean z, java.util.List list, android.util.ArrayMap arrayMap, com.android.server.am.ProcessRecord processRecord, java.util.ArrayList arrayList, java.lang.String str) {
        if (z || list.contains(str)) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) arrayMap.get(str);
                if (applicationInfo != null) {
                    if (applicationInfo.packageName.equals(processRecord.info.packageName)) {
                        processRecord.info = applicationInfo;
                        com.android.server.am.PlatformCompatCache.getInstance().onApplicationInfoChanged(applicationInfo);
                    }
                    processRecord.getThread().scheduleApplicationInfoChanged(applicationInfo);
                    arrayList.add(processRecord.getWindowProcessController());
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("ActivityManager", java.lang.String.format("Failed to update %s ApplicationInfo for %s", str, processRecord));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void sendPackageBroadcastLocked(int i, java.lang.String[] strArr, int i2) {
        boolean z = false;
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size);
            android.app.IApplicationThread thread = processRecord.getThread();
            if (thread != null && (i2 == -1 || processRecord.userId == i2)) {
                try {
                    for (int length = strArr.length - 1; length >= 0 && !z; length--) {
                        if (strArr[length].equals(processRecord.info.packageName)) {
                            z = true;
                        }
                    }
                    thread.dispatchPackageBroadcast(i, strArr);
                } catch (android.os.RemoteException e) {
                }
            }
        }
        if (!z) {
            try {
                android.app.AppGlobals.getPackageManager().notifyPackagesReplacedReceived(strArr);
            } catch (android.os.RemoteException e2) {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getUidProcStateLOSP(int i) {
        com.android.server.am.UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord == null) {
            return 20;
        }
        return uidRecord.getCurProcState();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getUidProcessCapabilityLOSP(int i) {
        com.android.server.am.UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord == null) {
            return 0;
        }
        return uidRecord.getCurCapability();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.UidRecord getUidRecordLOSP(int i) {
        return this.mActiveUids.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void doStopUidForIdleUidsLocked() {
        int size = this.mActiveUids.size();
        for (int i = 0; i < size; i++) {
            if (!android.os.UserHandle.isCore(this.mActiveUids.keyAt(i))) {
                com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i);
                if (valueAt.isIdle()) {
                    this.mService.doStopUidLocked(valueAt.getUid(), valueAt);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getBlockStateForUid(com.android.server.am.UidRecord uidRecord) {
        boolean z = android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidRecord.getCurProcState(), uidRecord.getCurCapability()) || android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidRecord.getCurProcState(), uidRecord.getCurCapability());
        boolean z2 = android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidRecord.getSetProcState(), uidRecord.getSetCapability()) || android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidRecord.getSetProcState(), uidRecord.getSetCapability());
        if (z2 || !z) {
            return (!z2 || z) ? 0 : 2;
        }
        return 1;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    @com.android.internal.annotations.VisibleForTesting
    void incrementProcStateSeqAndNotifyAppsLOSP(com.android.server.am.ActiveUids activeUids) {
        com.android.server.am.UidRecord uidRecordLOSP;
        int blockStateForUid;
        for (int size = activeUids.size() - 1; size >= 0; size--) {
            activeUids.valueAt(size).curProcStateSeq = getNextProcStateSeq();
        }
        if (this.mService.mConstants.mNetworkAccessTimeoutMs <= 0) {
            return;
        }
        java.util.ArrayList arrayList = null;
        for (int size2 = activeUids.size() - 1; size2 >= 0; size2--) {
            com.android.server.am.UidRecord valueAt = activeUids.valueAt(size2);
            if (this.mService.mInjector.isNetworkRestrictedForUid(valueAt.getUid()) && android.os.UserHandle.isApp(valueAt.getUid()) && valueAt.hasInternetPermission && ((valueAt.getSetProcState() != valueAt.getCurProcState() || valueAt.getSetCapability() != valueAt.getCurCapability()) && (blockStateForUid = getBlockStateForUid(valueAt)) != 0)) {
                synchronized (valueAt.networkStateLock) {
                    if (blockStateForUid == 1) {
                        if (arrayList == null) {
                            try {
                                arrayList = new java.util.ArrayList();
                            } finally {
                            }
                        }
                        arrayList.add(java.lang.Integer.valueOf(valueAt.getUid()));
                    } else if (valueAt.procStateSeqWaitingForNetwork != 0) {
                        valueAt.networkStateLock.notifyAll();
                    }
                }
            }
        }
        if (arrayList == null) {
            return;
        }
        for (int size3 = this.mLruProcesses.size() - 1; size3 >= 0; size3--) {
            com.android.server.am.ProcessRecord processRecord = this.mLruProcesses.get(size3);
            if (arrayList.contains(java.lang.Integer.valueOf(processRecord.uid))) {
                android.app.IApplicationThread thread = processRecord.getThread();
                if (!processRecord.isKilledByAm() && thread != null && (uidRecordLOSP = getUidRecordLOSP(processRecord.uid)) != null) {
                    try {
                        thread.setNetworkBlockSeq(uidRecordLOSP.curProcStateSeq);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
    }

    long getNextProcStateSeq() {
        long j = this.mProcStateSeqCounter + 1;
        this.mProcStateSeqCounter = j;
        return j;
    }

    private android.net.LocalSocket createSystemServerSocketForZygote() {
        android.net.LocalSocket localSocket;
        java.io.File file = new java.io.File(UNSOL_ZYGOTE_MSG_SOCKET_PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            localSocket = new android.net.LocalSocket(1);
            try {
                localSocket.bind(new android.net.LocalSocketAddress(UNSOL_ZYGOTE_MSG_SOCKET_PATH, android.net.LocalSocketAddress.Namespace.FILESYSTEM));
                android.system.Os.chmod(UNSOL_ZYGOTE_MSG_SOCKET_PATH, 438);
                return localSocket;
            } catch (java.lang.Exception e) {
                if (localSocket == null) {
                    return localSocket;
                }
                try {
                    localSocket.close();
                    return null;
                } catch (java.io.IOException e2) {
                    return null;
                }
            }
        } catch (java.lang.Exception e3) {
            localSocket = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleZygoteMessages(java.io.FileDescriptor fileDescriptor, int i) {
        fileDescriptor.getInt$();
        if ((i & 1) != 0) {
            try {
                int read = android.system.Os.read(fileDescriptor, this.mZygoteUnsolicitedMessage, 0, this.mZygoteUnsolicitedMessage.length);
                if (read > 0 && this.mZygoteSigChldMessage.length == com.android.internal.os.Zygote.nativeParseSigChld(this.mZygoteUnsolicitedMessage, read, this.mZygoteSigChldMessage)) {
                    this.mAppExitInfoTracker.handleZygoteSigChld(this.mZygoteSigChldMessage[0], this.mZygoteSigChldMessage[1], this.mZygoteSigChldMessage[2]);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityManager", "Exception in reading unsolicited zygote message: " + e);
            }
        }
        return 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean handleDyingAppDeathLocked(com.android.server.am.ProcessRecord processRecord, int i) {
        if (this.mProcessNames.get(processRecord.processName, processRecord.uid) == processRecord || this.mDyingProcesses.get(processRecord.processName, processRecord.uid) != processRecord) {
            return false;
        }
        android.util.Slog.v("ActivityManager", "Got obituary of " + i + ":" + processRecord.processName);
        processRecord.unlinkDeathRecipient();
        this.mDyingProcesses.remove(processRecord.processName, processRecord.uid);
        processRecord.setDyingPid(0);
        handlePrecedingAppDiedLocked(processRecord);
        removeLruProcessLocked(processRecord);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean handlePrecedingAppDiedLocked(com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.mSuccessor == null) {
            return true;
        }
        if (processRecord.isPersistent() && !processRecord.isRemoved() && this.mService.mPersistentStartingProcesses.indexOf(processRecord.mSuccessor) < 0) {
            this.mService.mPersistentStartingProcesses.add(processRecord.mSuccessor);
        }
        processRecord.mSuccessor.mPredecessor = null;
        processRecord.mSuccessor = null;
        this.mService.mProcStartHandler.removeMessages(2, processRecord);
        this.mService.mProcStartHandler.obtainMessage(1, processRecord).sendToTarget();
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateBackgroundRestrictedForUidPackageLocked(int i, final java.lang.String str, final boolean z) {
        com.android.server.am.UidRecord uidRecordLOSP = getUidRecordLOSP(i);
        if (uidRecordLOSP != null) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            uidRecordLOSP.forEachProcess(new java.util.function.Consumer() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.ProcessList.this.lambda$updateBackgroundRestrictedForUidPackageLocked$4(str, z, elapsedRealtime, (com.android.server.am.ProcessRecord) obj);
                }
            });
            this.mService.updateOomAdjPendingTargetsLocked(21);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBackgroundRestrictedForUidPackageLocked$4(java.lang.String str, boolean z, long j, com.android.server.am.ProcessRecord processRecord) {
        if (android.text.TextUtils.equals(processRecord.info.packageName, str)) {
            processRecord.mState.setBackgroundRestricted(z);
            if (z) {
                this.mAppsInBackgroundRestricted.add(processRecord);
                long lambda$killAppIfBgRestrictedAndCachedIdleLocked$5 = lambda$killAppIfBgRestrictedAndCachedIdleLocked$5(processRecord, j);
                if (lambda$killAppIfBgRestrictedAndCachedIdleLocked$5 > 0 && (this.mService.mDeterministicUidIdle || !this.mService.mHandler.hasMessages(58))) {
                    this.mService.mHandler.sendEmptyMessageDelayed(58, lambda$killAppIfBgRestrictedAndCachedIdleLocked$5 - j);
                }
            } else {
                this.mAppsInBackgroundRestricted.remove(processRecord);
            }
            if (!processRecord.isKilledByAm()) {
                this.mService.enqueueOomAdjTargetLocked(processRecord);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.GuardedBy({"mService"})
    /* renamed from: killAppIfBgRestrictedAndCachedIdleLocked, reason: merged with bridge method [inline-methods] */
    public long lambda$killAppIfBgRestrictedAndCachedIdleLocked$5(com.android.server.am.ProcessRecord processRecord, long j) {
        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
        long lastCanKillOnBgRestrictedAndIdleTime = processRecord.mState.getLastCanKillOnBgRestrictedAndIdleTime();
        if (!this.mService.mConstants.mKillBgRestrictedAndCachedIdle || processRecord.isKilled() || processRecord.getThread() == null || uidRecord == null || !uidRecord.isIdle() || !processRecord.isCached() || processRecord.mState.shouldNotKillOnBgRestrictedAndIdle() || !processRecord.mState.isBackgroundRestricted() || lastCanKillOnBgRestrictedAndIdleTime == 0) {
            return 0L;
        }
        long j2 = lastCanKillOnBgRestrictedAndIdleTime + this.mService.mConstants.mKillBgRestrictedAndCachedIdleSettleTimeMs;
        if (j2 <= j) {
            processRecord.killLocked("cached idle & background restricted", 13, 18, true);
            return 0L;
        }
        return j2;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killAppIfBgRestrictedAndCachedIdleLocked(com.android.server.am.UidRecord uidRecord) {
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        uidRecord.forEachProcess(new java.util.function.Consumer() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.ProcessList.this.lambda$killAppIfBgRestrictedAndCachedIdleLocked$5(elapsedRealtime, (com.android.server.am.ProcessRecord) obj);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void noteProcessDiedLocked(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.Watchdog.getInstance().processDied(processRecord.processName, processRecord.getPid());
        if (processRecord.getDeathRecipient() == null && this.mDyingProcesses.get(processRecord.processName, processRecord.uid) == processRecord) {
            this.mDyingProcesses.remove(processRecord.processName, processRecord.uid);
            processRecord.setDyingPid(0);
        }
        this.mAppExitInfoTracker.scheduleNoteProcessDied(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void noteAppRecoverableCrash(com.android.server.am.ProcessRecord processRecord) {
        this.mAppExitInfoTracker.scheduleNoteAppRecoverableCrash(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void noteAppKill(com.android.server.am.ProcessRecord processRecord, int i, int i2, java.lang.String str) {
        if (processRecord.getPid() > 0 && !processRecord.isolated && processRecord.getDeathRecipient() != null) {
            this.mDyingProcesses.put(processRecord.processName, processRecord.uid, processRecord);
            processRecord.setDyingPid(processRecord.getPid());
        }
        this.mAppExitInfoTracker.scheduleNoteAppKill(processRecord, i, i2, str);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void noteAppKill(int i, int i2, int i3, int i4, java.lang.String str) {
        com.android.server.am.ProcessRecord processRecord;
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord = this.mService.mPidsSelfLocked.get(i);
        }
        if (processRecord != null && processRecord.uid == i2 && !processRecord.isolated && processRecord.getDeathRecipient() != null) {
            this.mDyingProcesses.put(processRecord.processName, i2, processRecord);
            processRecord.setDyingPid(processRecord.getPid());
        }
        this.mAppExitInfoTracker.scheduleNoteAppKill(i, i2, i3, i4, str);
    }

    void killProcessesWhenImperceptible(int[] iArr, java.lang.String str, int i) {
        com.android.server.am.ProcessRecord processRecord;
        if (com.android.internal.util.ArrayUtils.isEmpty(iArr)) {
            return;
        }
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            for (int i2 : iArr) {
                try {
                    synchronized (this.mService.mPidsSelfLocked) {
                        processRecord = this.mService.mPidsSelfLocked.get(i2);
                    }
                    if (processRecord != null) {
                        this.mImperceptibleKillRunner.enqueueLocked(processRecord, str, i);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    android.util.Pair<java.lang.Integer, java.lang.Integer> getNumForegroundServices() {
        int i;
        int i2;
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int size = this.mLruProcesses.size();
                i = 0;
                i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    int numForegroundServices = this.mLruProcesses.get(i3).mServices.getNumForegroundServices();
                    if (numForegroundServices > 0) {
                        i += numForegroundServices;
                        i2++;
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ImperceptibleKillRunner extends android.app.UidObserver {
        private static final java.lang.String DROPBOX_TAG_IMPERCEPTIBLE_KILL = "imperceptible_app_kill";
        private static final java.lang.String EXTRA_PID = "pid";
        private static final java.lang.String EXTRA_REASON = "reason";
        private static final java.lang.String EXTRA_REQUESTER = "requester";
        private static final java.lang.String EXTRA_TIMESTAMP = "timestamp";
        private static final java.lang.String EXTRA_UID = "uid";
        private static final boolean LOG_TO_DROPBOX = false;
        private android.os.Handler mHandler;
        private volatile boolean mIdle;
        private com.android.server.am.ProcessList.ImperceptibleKillRunner.IdlenessReceiver mReceiver;
        private boolean mUidObserverEnabled;
        private android.util.SparseArray<java.util.List<android.os.Bundle>> mWorkItems = new android.util.SparseArray<>();
        private com.android.internal.app.ProcessMap<java.lang.Long> mLastProcessKillTimes = new com.android.internal.app.ProcessMap<>();

        private final class H extends android.os.Handler {
            static final int MSG_DEVICE_IDLE = 0;
            static final int MSG_UID_GONE = 1;
            static final int MSG_UID_STATE_CHANGED = 2;

            H(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 0:
                        com.android.server.am.ProcessList.ImperceptibleKillRunner.this.handleDeviceIdle();
                        break;
                    case 1:
                        com.android.server.am.ProcessList.ImperceptibleKillRunner.this.handleUidGone(message.arg1);
                        break;
                    case 2:
                        com.android.server.am.ProcessList.ImperceptibleKillRunner.this.handleUidStateChanged(message.arg1, message.arg2);
                        break;
                }
            }
        }

        private final class IdlenessReceiver extends android.content.BroadcastReceiver {
            private IdlenessReceiver() {
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                android.os.PowerManager powerManager = (android.os.PowerManager) com.android.server.am.ProcessList.this.mService.mContext.getSystemService(android.os.PowerManager.class);
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case 498807504:
                        if (action.equals("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 870701415:
                        if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
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
                        com.android.server.am.ProcessList.ImperceptibleKillRunner.this.notifyDeviceIdleness(powerManager.isLightDeviceIdleMode());
                        break;
                    case 1:
                        com.android.server.am.ProcessList.ImperceptibleKillRunner.this.notifyDeviceIdleness(powerManager.isDeviceIdleMode());
                        break;
                }
            }
        }

        ImperceptibleKillRunner(android.os.Looper looper) {
            this.mHandler = new com.android.server.am.ProcessList.ImperceptibleKillRunner.H(looper);
        }

        @com.android.internal.annotations.GuardedBy({"mService"})
        boolean enqueueLocked(com.android.server.am.ProcessRecord processRecord, java.lang.String str, int i) {
            java.lang.Long l = processRecord.isolated ? null : (java.lang.Long) this.mLastProcessKillTimes.get(processRecord.processName, processRecord.uid);
            if (l != null && android.os.SystemClock.uptimeMillis() < l.longValue() + com.android.server.am.ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                return false;
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(EXTRA_PID, processRecord.getPid());
            bundle.putInt("uid", processRecord.uid);
            bundle.putLong("timestamp", processRecord.getStartTime());
            bundle.putString("reason", str);
            bundle.putInt(EXTRA_REQUESTER, i);
            java.util.List<android.os.Bundle> list = this.mWorkItems.get(processRecord.uid);
            if (list == null) {
                list = new java.util.ArrayList<>();
                this.mWorkItems.put(processRecord.uid, list);
            }
            list.add(bundle);
            if (this.mReceiver == null) {
                this.mReceiver = new com.android.server.am.ProcessList.ImperceptibleKillRunner.IdlenessReceiver();
                android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
                intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
                com.android.server.am.ProcessList.this.mService.mContext.registerReceiver(this.mReceiver, intentFilter);
                return true;
            }
            return true;
        }

        void notifyDeviceIdleness(boolean z) {
            boolean z2 = this.mIdle != z;
            this.mIdle = z;
            if (z2 && z) {
                com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ProcessList.this.mService;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        if (this.mWorkItems.size() > 0) {
                            this.mHandler.sendEmptyMessage(0);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleDeviceIdle() {
            android.os.DropBoxManager dropBoxManager = (android.os.DropBoxManager) com.android.server.am.ProcessList.this.mService.mContext.getSystemService(android.os.DropBoxManager.class);
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ProcessList.this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    for (int size = this.mWorkItems.size() - 1; this.mIdle && size >= 0; size--) {
                        java.util.List<android.os.Bundle> valueAt = this.mWorkItems.valueAt(size);
                        for (int size2 = valueAt.size() - 1; this.mIdle && size2 >= 0; size2--) {
                            android.os.Bundle bundle = valueAt.get(size2);
                            if (killProcessLocked(bundle.getInt(EXTRA_PID), bundle.getInt("uid"), bundle.getLong("timestamp"), bundle.getString("reason"), bundle.getInt(EXTRA_REQUESTER), dropBoxManager, false)) {
                                valueAt.remove(size2);
                            }
                        }
                        if (valueAt.size() == 0) {
                            this.mWorkItems.removeAt(size);
                        }
                    }
                    registerUidObserverIfNecessaryLocked();
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        @com.android.internal.annotations.GuardedBy({"mService"})
        private void registerUidObserverIfNecessaryLocked() {
            if (!this.mUidObserverEnabled && this.mWorkItems.size() > 0) {
                this.mUidObserverEnabled = true;
                com.android.server.am.ProcessList.this.mService.registerUidObserver(this, 3, -1, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
            } else if (this.mUidObserverEnabled && this.mWorkItems.size() == 0) {
                this.mUidObserverEnabled = false;
                com.android.server.am.ProcessList.this.mService.unregisterUidObserver(this);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mService"})
        private boolean killProcessLocked(int i, int i2, long j, java.lang.String str, int i3, android.os.DropBoxManager dropBoxManager, boolean z) {
            com.android.server.am.ProcessRecord processRecord;
            synchronized (com.android.server.am.ProcessList.this.mService.mPidsSelfLocked) {
                processRecord = com.android.server.am.ProcessList.this.mService.mPidsSelfLocked.get(i);
            }
            if (processRecord == null || processRecord.getPid() != i || processRecord.uid != i2 || processRecord.getStartTime() != j || processRecord.getPkgList().searchEachPackage(new java.util.function.Function() { // from class: com.android.server.am.ProcessList$ImperceptibleKillRunner$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Boolean lambda$killProcessLocked$0;
                    lambda$killProcessLocked$0 = com.android.server.am.ProcessList.ImperceptibleKillRunner.this.lambda$killProcessLocked$0((java.lang.String) obj);
                    return lambda$killProcessLocked$0;
                }
            }) != null) {
                return true;
            }
            if (!com.android.server.am.ProcessList.this.mService.mConstants.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES.contains(java.lang.Integer.valueOf(processRecord.mState.getReportedProcState()))) {
                processRecord.killLocked(str, 13, 15, true);
                if (!processRecord.isolated) {
                    this.mLastProcessKillTimes.put(processRecord.processName, processRecord.uid, java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis()));
                }
                if (z) {
                    android.os.SystemClock.elapsedRealtime();
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    com.android.server.am.ProcessList.this.mService.appendDropBoxProcessHeaders(processRecord, processRecord.processName, null, sb);
                    sb.append("Reason: " + str);
                    sb.append("\n");
                    sb.append("Requester UID: " + i3);
                    sb.append("\n");
                    dropBoxManager.addText(DROPBOX_TAG_IMPERCEPTIBLE_KILL, sb.toString());
                }
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$killProcessLocked$0(java.lang.String str) {
            if (com.android.server.am.ProcessList.this.mService.mConstants.IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES.contains(str)) {
                return java.lang.Boolean.TRUE;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleUidStateChanged(int i, int i2) {
            java.util.List<android.os.Bundle> list;
            android.os.DropBoxManager dropBoxManager = (android.os.DropBoxManager) com.android.server.am.ProcessList.this.mService.mContext.getSystemService(android.os.DropBoxManager.class);
            boolean z = dropBoxManager != null && dropBoxManager.isTagEnabled(DROPBOX_TAG_IMPERCEPTIBLE_KILL);
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ProcessList.this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (this.mIdle && !com.android.server.am.ProcessList.this.mService.mConstants.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES.contains(java.lang.Integer.valueOf(i2)) && (list = this.mWorkItems.get(i)) != null) {
                        for (int size = list.size() - 1; this.mIdle && size >= 0; size--) {
                            android.os.Bundle bundle = list.get(size);
                            if (killProcessLocked(bundle.getInt(EXTRA_PID), bundle.getInt("uid"), bundle.getLong("timestamp"), bundle.getString("reason"), bundle.getInt(EXTRA_REQUESTER), dropBoxManager, z)) {
                                list.remove(size);
                            }
                        }
                        if (list.size() == 0) {
                            this.mWorkItems.remove(i);
                        }
                        registerUidObserverIfNecessaryLocked();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleUidGone(int i) {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ProcessList.this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    this.mWorkItems.remove(i);
                    registerUidObserverIfNecessaryLocked();
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public void onUidGone(int i, boolean z) {
            this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            this.mHandler.obtainMessage(2, i, i2).sendToTarget();
        }
    }
}
