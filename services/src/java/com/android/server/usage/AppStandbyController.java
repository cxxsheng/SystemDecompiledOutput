package com.android.server.usage;

/* loaded from: classes2.dex */
public class AppStandbyController implements com.android.server.usage.AppStandbyInternal, android.app.usage.UsageStatsManagerInternal.UsageEventListener {
    static final boolean COMPRESS_TIME = false;
    static final boolean DEBUG = false;
    private static final long DEFAULT_PREDICTION_TIMEOUT = 43200000;
    private static final int HEADLESS_APP_CHECK_FLAGS = 1835520;
    static final int MSG_CHECK_IDLE_STATES = 5;
    static final int MSG_CHECK_PACKAGE_IDLE_STATE = 11;
    static final int MSG_FORCE_IDLE_STATE = 4;
    static final int MSG_INFORM_LISTENERS = 3;
    static final int MSG_ONE_TIME_CHECK_IDLE_STATES = 10;
    static final int MSG_PAROLE_STATE_CHANGED = 9;
    static final int MSG_REPORT_CONTENT_PROVIDER_USAGE = 8;
    static final int MSG_REPORT_EXEMPTED_SYNC_START = 13;
    static final int MSG_REPORT_SYNC_SCHEDULED = 12;
    static final int MSG_TRIGGER_LISTENER_QUOTA_BUMP = 7;
    private static final long NETWORK_SCORER_CACHE_DURATION_MILLIS = 5000;
    private static final long NOTIFICATION_SEEN_HOLD_DURATION_FOR_PRE_T_APPS = 43200000;
    private static final int NOTIFICATION_SEEN_PROMOTED_BUCKET_FOR_PRE_T_APPS = 20;
    private static final long ONE_DAY = 86400000;
    private static final long ONE_HOUR = 3600000;
    private static final long ONE_MINUTE = 60000;
    private static final int SYSTEM_PACKAGE_FLAGS = 542908416;
    private static final java.lang.String TAG = "AppStandbyController";
    private static final long WAIT_FOR_ADMIN_DATA_TIMEOUT_MS = 10000;

    @com.android.internal.annotations.GuardedBy({"mActiveAdminApps"})
    private final android.util.SparseArray<java.util.Set<java.lang.String>> mActiveAdminApps;
    private final java.util.concurrent.CountDownLatch mAdminDataAvailableLatch;

    @com.android.internal.annotations.GuardedBy({"mAdminProtectedPackages"})
    private final android.util.SparseArray<java.util.Set<java.lang.String>> mAdminProtectedPackages;
    private volatile boolean mAppIdleEnabled;

    @com.android.internal.annotations.GuardedBy({"mAppIdleLock"})
    private com.android.server.usage.AppIdleHistory mAppIdleHistory;
    private final java.lang.Object mAppIdleLock;
    private android.app.AppOpsManager mAppOpsManager;
    long[] mAppStandbyElapsedThresholds;
    private final java.util.Map<java.lang.String, java.lang.String> mAppStandbyProperties;
    long[] mAppStandbyScreenThresholds;
    private android.appwidget.AppWidgetManager mAppWidgetManager;
    private final android.util.SparseSetArray<java.lang.String> mAppsToRestoreToRare;
    volatile java.lang.String mBroadcastResponseExemptedPermissions;
    volatile java.util.List<java.lang.String> mBroadcastResponseExemptedPermissionsList;
    volatile java.lang.String mBroadcastResponseExemptedRoles;
    volatile java.util.List<java.lang.String> mBroadcastResponseExemptedRolesList;
    volatile int mBroadcastResponseFgThresholdState;
    volatile long mBroadcastResponseWindowDurationMillis;
    volatile long mBroadcastSessionsDurationMs;
    volatile long mBroadcastSessionsWithResponseDurationMs;
    private java.lang.String mCachedDeviceProvisioningPackage;
    private volatile java.lang.String mCachedNetworkScorer;
    private volatile long mCachedNetworkScorerAtMillis;

    @com.android.internal.annotations.GuardedBy({"mCarrierPrivilegedLock"})
    private java.util.List<java.lang.String> mCarrierPrivilegedApps;
    private final java.lang.Object mCarrierPrivilegedLock;
    long mCheckIdleIntervalMillis;
    private final android.content.Context mContext;
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener;
    long mExemptedSyncScheduledDozeTimeoutMillis;
    long mExemptedSyncScheduledNonDozeTimeoutMillis;
    long mExemptedSyncStartTimeoutMillis;
    private final com.android.server.usage.AppStandbyController.AppStandbyHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mCarrierPrivilegedLock"})
    private boolean mHaveCarrierPrivilegedApps;

    @com.android.internal.annotations.GuardedBy({"mHeadlessSystemApps"})
    private final android.util.ArraySet<java.lang.String> mHeadlessSystemApps;
    long mInitialForegroundServiceStartTimeoutMillis;
    com.android.server.usage.AppStandbyController.Injector mInjector;
    private volatile boolean mIsCharging;
    boolean mLinkCrossProfileApps;
    volatile boolean mNoteResponseEventForAllBroadcastSessions;
    int mNotificationSeenPromotedBucket;
    long mNotificationSeenTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mPackageAccessListeners"})
    private final java.util.ArrayList<com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener> mPackageAccessListeners;
    private android.content.pm.PackageManager mPackageManager;

    @com.android.internal.annotations.GuardedBy({"mPendingIdleStateChecks"})
    private final android.util.SparseLongArray mPendingIdleStateChecks;
    private boolean mPendingInitializeDefaults;
    private volatile boolean mPendingOneTimeCheckIdleStates;
    long mPredictionTimeoutMillis;
    boolean mRetainNotificationSeenImpactForPreTApps;
    long mSlicePinnedTimeoutMillis;
    long mStrongUsageTimeoutMillis;
    long mSyncAdapterTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mSystemExemptionAppOpMode"})
    private final android.util.SparseIntArray mSystemExemptionAppOpMode;
    long mSystemInteractionTimeoutMillis;
    private final java.util.ArrayList<java.lang.Integer> mSystemPackagesAppIds;
    private boolean mSystemServicesReady;
    long mSystemUpdateUsageTimeoutMillis;
    private boolean mTriggerQuotaBumpOnNotificationSeen;
    long mUnexemptedSyncScheduledTimeoutMillis;

    @com.android.internal.annotations.VisibleForTesting
    static final long[] DEFAULT_SCREEN_TIME_THRESHOLDS = {0, 0, 3600000, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT, 21600000};

    @com.android.internal.annotations.VisibleForTesting
    static final long[] MINIMUM_SCREEN_TIME_THRESHOLDS = {0, 0, 0, 1800000, 3600000};

    @com.android.internal.annotations.VisibleForTesting
    static final long[] DEFAULT_ELAPSED_TIME_THRESHOLDS = {0, 43200000, 86400000, 172800000, 691200000};

    @com.android.internal.annotations.VisibleForTesting
    static final long[] MINIMUM_ELAPSED_TIME_THRESHOLDS = {0, 3600000, 3600000, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT, 14400000};
    private static final int[] THRESHOLD_BUCKETS = {10, 20, 30, 40, 45};

    static class Lock {
        Lock() {
        }
    }

    private static class Pool<T> {
        private final T[] mArray;
        private int mSize = 0;

        Pool(T[] tArr) {
            this.mArray = tArr;
        }

        @android.annotation.Nullable
        synchronized T obtain() {
            T t;
            if (this.mSize > 0) {
                T[] tArr = this.mArray;
                int i = this.mSize - 1;
                this.mSize = i;
                t = tArr[i];
            } else {
                t = null;
            }
            return t;
        }

        synchronized void recycle(T t) {
            if (this.mSize < this.mArray.length) {
                T[] tArr = this.mArray;
                int i = this.mSize;
                this.mSize = i + 1;
                tArr[i] = t;
            }
        }
    }

    private static class StandbyUpdateRecord {
        private static final com.android.server.usage.AppStandbyController.Pool<com.android.server.usage.AppStandbyController.StandbyUpdateRecord> sPool = new com.android.server.usage.AppStandbyController.Pool<>(new com.android.server.usage.AppStandbyController.StandbyUpdateRecord[10]);
        int bucket;
        boolean isUserInteraction;
        java.lang.String packageName;
        int reason;
        int userId;

        private StandbyUpdateRecord() {
        }

        public static com.android.server.usage.AppStandbyController.StandbyUpdateRecord obtain(java.lang.String str, int i, int i2, int i3, boolean z) {
            com.android.server.usage.AppStandbyController.StandbyUpdateRecord obtain = sPool.obtain();
            if (obtain == null) {
                obtain = new com.android.server.usage.AppStandbyController.StandbyUpdateRecord();
            }
            obtain.packageName = str;
            obtain.userId = i;
            obtain.bucket = i2;
            obtain.reason = i3;
            obtain.isUserInteraction = z;
            return obtain;
        }

        public void recycle() {
            sPool.recycle(this);
        }
    }

    private static class ContentProviderUsageRecord {
        private static final com.android.server.usage.AppStandbyController.Pool<com.android.server.usage.AppStandbyController.ContentProviderUsageRecord> sPool = new com.android.server.usage.AppStandbyController.Pool<>(new com.android.server.usage.AppStandbyController.ContentProviderUsageRecord[10]);
        public java.lang.String name;
        public java.lang.String packageName;
        public int userId;

        private ContentProviderUsageRecord() {
        }

        public static com.android.server.usage.AppStandbyController.ContentProviderUsageRecord obtain(java.lang.String str, java.lang.String str2, int i) {
            com.android.server.usage.AppStandbyController.ContentProviderUsageRecord obtain = sPool.obtain();
            if (obtain == null) {
                obtain = new com.android.server.usage.AppStandbyController.ContentProviderUsageRecord();
            }
            obtain.name = str;
            obtain.packageName = str2;
            obtain.userId = i;
            return obtain;
        }

        public void recycle() {
            sPool.recycle(this);
        }
    }

    public AppStandbyController(android.content.Context context) {
        this(new com.android.server.usage.AppStandbyController.Injector(context, com.android.server.AppSchedulingModuleThread.get().getLooper()));
    }

    AppStandbyController(com.android.server.usage.AppStandbyController.Injector injector) {
        this.mAppIdleLock = new com.android.server.usage.AppStandbyController.Lock();
        this.mPackageAccessListeners = new java.util.ArrayList<>();
        this.mCarrierPrivilegedLock = new com.android.server.usage.AppStandbyController.Lock();
        this.mActiveAdminApps = new android.util.SparseArray<>();
        this.mAdminProtectedPackages = new android.util.SparseArray<>();
        this.mHeadlessSystemApps = new android.util.ArraySet<>();
        this.mAdminDataAvailableLatch = new java.util.concurrent.CountDownLatch(1);
        this.mPendingIdleStateChecks = new android.util.SparseLongArray();
        this.mSystemExemptionAppOpMode = new android.util.SparseIntArray();
        byte b = 0;
        this.mCachedNetworkScorer = null;
        this.mCachedNetworkScorerAtMillis = 0L;
        this.mCachedDeviceProvisioningPackage = null;
        this.mCheckIdleIntervalMillis = java.lang.Math.min(DEFAULT_ELAPSED_TIME_THRESHOLDS[1] / 4, 14400000L);
        this.mAppStandbyScreenThresholds = DEFAULT_SCREEN_TIME_THRESHOLDS;
        this.mAppStandbyElapsedThresholds = DEFAULT_ELAPSED_TIME_THRESHOLDS;
        this.mStrongUsageTimeoutMillis = 3600000L;
        this.mNotificationSeenTimeoutMillis = 43200000L;
        this.mSlicePinnedTimeoutMillis = 43200000L;
        this.mNotificationSeenPromotedBucket = 20;
        this.mTriggerQuotaBumpOnNotificationSeen = false;
        this.mRetainNotificationSeenImpactForPreTApps = false;
        this.mSystemUpdateUsageTimeoutMillis = com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT;
        this.mPredictionTimeoutMillis = 43200000L;
        this.mSyncAdapterTimeoutMillis = 600000L;
        this.mExemptedSyncScheduledNonDozeTimeoutMillis = 600000L;
        this.mExemptedSyncScheduledDozeTimeoutMillis = 14400000L;
        this.mExemptedSyncStartTimeoutMillis = 600000L;
        this.mUnexemptedSyncScheduledTimeoutMillis = 600000L;
        this.mSystemInteractionTimeoutMillis = 600000L;
        this.mInitialForegroundServiceStartTimeoutMillis = 1800000L;
        this.mLinkCrossProfileApps = true;
        this.mBroadcastResponseWindowDurationMillis = 120000L;
        this.mBroadcastResponseFgThresholdState = 2;
        this.mBroadcastSessionsDurationMs = 120000L;
        this.mBroadcastSessionsWithResponseDurationMs = 120000L;
        this.mNoteResponseEventForAllBroadcastSessions = true;
        this.mBroadcastResponseExemptedRoles = "";
        this.mBroadcastResponseExemptedRolesList = java.util.Collections.EMPTY_LIST;
        this.mBroadcastResponseExemptedPermissions = "";
        this.mBroadcastResponseExemptedPermissionsList = java.util.Collections.EMPTY_LIST;
        this.mAppStandbyProperties = new android.util.ArrayMap();
        this.mAppsToRestoreToRare = new android.util.SparseSetArray<>();
        this.mSystemPackagesAppIds = new java.util.ArrayList<>();
        this.mSystemServicesReady = false;
        this.mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.usage.AppStandbyController.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                if (i == 0) {
                    boolean isDisplayOn = com.android.server.usage.AppStandbyController.this.isDisplayOn();
                    synchronized (com.android.server.usage.AppStandbyController.this.mAppIdleLock) {
                        com.android.server.usage.AppStandbyController.this.mAppIdleHistory.updateDisplay(isDisplayOn, com.android.server.usage.AppStandbyController.this.mInjector.elapsedRealtime());
                    }
                }
            }
        };
        this.mInjector = injector;
        this.mContext = this.mInjector.getContext();
        this.mHandler = new com.android.server.usage.AppStandbyController.AppStandbyHandler(this.mInjector.getLooper());
        this.mPackageManager = this.mContext.getPackageManager();
        com.android.server.usage.AppStandbyController.DeviceStateReceiver deviceStateReceiver = new com.android.server.usage.AppStandbyController.DeviceStateReceiver();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.os.action.CHARGING");
        intentFilter.addAction("android.os.action.DISCHARGING");
        intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
        this.mContext.registerReceiver(deviceStateReceiver, intentFilter);
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory = new com.android.server.usage.AppIdleHistory(this.mInjector.getDataSystemDirectory(), this.mInjector.elapsedRealtime());
        }
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(new com.android.server.usage.AppStandbyController.PackageReceiver(), android.os.UserHandle.ALL, intentFilter2, null, this.mHandler);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAppIdleEnabled(boolean z) {
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        if (z) {
            usageStatsManagerInternal.registerListener(this);
        } else {
            usageStatsManagerInternal.unregisterListener(this);
        }
        synchronized (this.mAppIdleLock) {
            try {
                if (this.mAppIdleEnabled != z) {
                    boolean isInParole = isInParole();
                    this.mAppIdleEnabled = z;
                    if (isInParole() != isInParole) {
                        postParoleStateChanged();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAppIdleEnabled() {
        return this.mAppIdleEnabled;
    }

    public void onBootPhase(int i) {
        int i2;
        boolean userFileExists;
        this.mInjector.onBootPhase(i);
        if (i == 500) {
            android.util.Slog.d(TAG, "Setting app idle enabled state");
            if (this.mAppIdleEnabled) {
                ((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)).registerListener(this);
            }
            new com.android.server.usage.AppStandbyController.ConstantsObserver(this.mHandler).start();
            this.mAppWidgetManager = (android.appwidget.AppWidgetManager) this.mContext.getSystemService(android.appwidget.AppWidgetManager.class);
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            try {
                this.mInjector.getAppOpsService().startWatchingMode(128, (java.lang.String) null, new com.android.internal.app.IAppOpsCallback.Stub() { // from class: com.android.server.usage.AppStandbyController.1
                    public void opChanged(int i3, int i4, java.lang.String str, java.lang.String str2) {
                        int userId = android.os.UserHandle.getUserId(i4);
                        synchronized (com.android.server.usage.AppStandbyController.this.mSystemExemptionAppOpMode) {
                            com.android.server.usage.AppStandbyController.this.mSystemExemptionAppOpMode.delete(i4);
                        }
                        com.android.server.usage.AppStandbyController.this.mHandler.obtainMessage(11, userId, i4, str).sendToTarget();
                    }
                });
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "Failed start watching for app op", e);
            }
            this.mInjector.registerDisplayListener(this.mDisplayListener, this.mHandler);
            synchronized (this.mAppIdleLock) {
                this.mAppIdleHistory.updateDisplay(isDisplayOn(), this.mInjector.elapsedRealtime());
            }
            this.mSystemServicesReady = true;
            synchronized (this.mAppIdleLock) {
                userFileExists = this.mAppIdleHistory.userFileExists(0);
            }
            if (this.mPendingInitializeDefaults || !userFileExists) {
                initializeDefaultsForSystemApps(0);
            }
            if (this.mPendingOneTimeCheckIdleStates) {
                postOneTimeCheckIdleStates();
            }
            java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mPackageManager.getInstalledApplications(SYSTEM_PACKAGE_FLAGS);
            int size = installedApplications.size();
            for (i2 = 0; i2 < size; i2++) {
                this.mSystemPackagesAppIds.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(installedApplications.get(i2).uid)));
            }
            return;
        }
        if (i == 1000) {
            setChargingState(this.mInjector.isCharging());
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.usage.AppStandbyController.this.updatePowerWhitelistCache();
                }
            });
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.usage.AppStandbyController.this.loadHeadlessSystemAppCache();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:? -> B:22:0x006b). Please report as a decompilation issue!!! */
    public void reportContentProviderUsage(java.lang.String str, java.lang.String str2, int i) {
        int i2;
        int i3;
        java.lang.Object obj;
        if (this.mAppIdleEnabled) {
            java.lang.String[] syncAdapterPackagesForAuthorityAsUser = android.content.ContentResolver.getSyncAdapterPackagesForAuthorityAsUser(str, i);
            android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            int length = syncAdapterPackagesForAuthorityAsUser.length;
            int i4 = 0;
            while (i4 < length) {
                java.lang.String str3 = syncAdapterPackagesForAuthorityAsUser[i4];
                if (str3.equals(str2)) {
                    i2 = i4;
                    i3 = length;
                } else {
                    if (!this.mSystemPackagesAppIds.contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageManagerInternal.getPackageUid(str3, 0L, i))))) {
                        i2 = i4;
                        i3 = length;
                    } else {
                        java.util.List<android.os.UserHandle> crossProfileTargets = getCrossProfileTargets(str3, i);
                        java.lang.Object obj2 = this.mAppIdleLock;
                        synchronized (obj2) {
                            try {
                                obj = obj2;
                                i2 = i4;
                                i3 = length;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                obj = obj2;
                                throw th;
                            }
                            try {
                                reportNoninteractiveUsageCrossUserLocked(str3, i, 10, 8, elapsedRealtime, this.mSyncAdapterTimeoutMillis, crossProfileTargets);
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        }
                    }
                }
                i4 = i2 + 1;
                length = i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportExemptedSyncScheduled(java.lang.String str, int i) {
        long j;
        int i2;
        int i3;
        if (this.mAppIdleEnabled) {
            if (!this.mInjector.isDeviceIdleMode()) {
                j = this.mExemptedSyncScheduledNonDozeTimeoutMillis;
                i2 = 11;
                i3 = 10;
            } else {
                j = this.mExemptedSyncScheduledDozeTimeoutMillis;
                i2 = 12;
                i3 = 20;
            }
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            java.util.List<android.os.UserHandle> crossProfileTargets = getCrossProfileTargets(str, i);
            synchronized (this.mAppIdleLock) {
                reportNoninteractiveUsageCrossUserLocked(str, i, i3, i2, elapsedRealtime, j, crossProfileTargets);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportUnexemptedSyncScheduled(java.lang.String str, int i) {
        if (this.mAppIdleEnabled) {
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            synchronized (this.mAppIdleLock) {
                try {
                    if (this.mAppIdleHistory.getAppStandbyBucket(str, i, elapsedRealtime) == 50) {
                        reportNoninteractiveUsageCrossUserLocked(str, i, 20, 14, elapsedRealtime, this.mUnexemptedSyncScheduledTimeoutMillis, getCrossProfileTargets(str, i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportExemptedSyncStart(java.lang.String str, int i) {
        if (this.mAppIdleEnabled) {
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            java.util.List<android.os.UserHandle> crossProfileTargets = getCrossProfileTargets(str, i);
            synchronized (this.mAppIdleLock) {
                reportNoninteractiveUsageCrossUserLocked(str, i, 10, 13, elapsedRealtime, this.mExemptedSyncStartTimeoutMillis, crossProfileTargets);
            }
        }
    }

    private void reportNoninteractiveUsageCrossUserLocked(java.lang.String str, int i, int i2, int i3, long j, long j2, java.util.List<android.os.UserHandle> list) {
        reportNoninteractiveUsageLocked(str, i, i2, i3, j, j2);
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            reportNoninteractiveUsageLocked(str, list.get(i4).getIdentifier(), i2, i3, j, j2);
        }
    }

    private void reportNoninteractiveUsageLocked(java.lang.String str, int i, int i2, int i3, long j, long j2) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory reportUsage = this.mAppIdleHistory.reportUsage(str, i, i2, i3, 0L, j + j2);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11, i, -1, str), j2);
        maybeInformListeners(str, i, j, reportUsage.currentBucket, reportUsage.bucketingReason, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerListenerQuotaBump(java.lang.String str, int i) {
        if (this.mAppIdleEnabled) {
            synchronized (this.mPackageAccessListeners) {
                try {
                    java.util.Iterator<com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener> it = this.mPackageAccessListeners.iterator();
                    while (it.hasNext()) {
                        it.next().triggerTemporaryQuotaBump(str, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setChargingState(boolean z) {
        if (this.mIsCharging != z) {
            this.mIsCharging = z;
            postParoleStateChanged();
        }
    }

    public boolean isInParole() {
        return !this.mAppIdleEnabled || this.mIsCharging;
    }

    private void postParoleStateChanged() {
        this.mHandler.removeMessages(9);
        this.mHandler.sendEmptyMessage(9);
    }

    public void postCheckIdleStates(int i) {
        if (i == -1) {
            postOneTimeCheckIdleStates();
            return;
        }
        synchronized (this.mPendingIdleStateChecks) {
            this.mPendingIdleStateChecks.put(i, this.mInjector.elapsedRealtime());
        }
        this.mHandler.obtainMessage(5).sendToTarget();
    }

    public void postOneTimeCheckIdleStates() {
        if (this.mInjector.getBootPhase() < 500) {
            this.mPendingOneTimeCheckIdleStates = true;
        } else {
            this.mHandler.sendEmptyMessage(10);
            this.mPendingOneTimeCheckIdleStates = false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean checkIdleStates(int i) {
        if (!this.mAppIdleEnabled) {
            return false;
        }
        try {
            int[] runningUserIds = this.mInjector.getRunningUserIds();
            if (i != -1) {
                if (!com.android.internal.util.jobs.ArrayUtils.contains(runningUserIds, i)) {
                    return false;
                }
            }
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            for (int i2 : runningUserIds) {
                if (i == -1 || i == i2) {
                    java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i2);
                    int i3 = 0;
                    for (int size = installedPackagesAsUser.size(); i3 < size; size = size) {
                        android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) installedPackagesAsUser.get(i3);
                        checkAndUpdateStandbyState(packageInfo.packageName, i2, packageInfo.applicationInfo.uid, elapsedRealtime);
                        i3++;
                    }
                }
            }
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndUpdateStandbyState(java.lang.String str, int i, int i2, long j) {
        int packageUidAsUser;
        int i3;
        int i4;
        java.lang.String str2;
        int i5;
        boolean isIdle;
        boolean z;
        boolean isIdle2;
        if (i2 > 0) {
            packageUidAsUser = i2;
        } else {
            try {
                packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, i);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return;
            }
        }
        int appMinBucket = getAppMinBucket(str, android.os.UserHandle.getAppId(packageUidAsUser), i);
        if (appMinBucket <= 10) {
            synchronized (this.mAppIdleLock) {
                isIdle2 = this.mAppIdleHistory.isIdle(str, i, j);
                this.mAppIdleHistory.setAppStandbyBucket(str, i, j, appMinBucket, 256);
                isIdle = this.mAppIdleHistory.isIdle(str, i, j);
            }
            maybeInformListeners(str, i, j, appMinBucket, 256, false);
            z = isIdle2;
            i5 = i;
            str2 = str;
        } else {
            synchronized (this.mAppIdleLock) {
                try {
                    boolean isIdle3 = this.mAppIdleHistory.isIdle(str, i, j);
                    com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = this.mAppIdleHistory.getAppUsageHistory(str, i, j);
                    int i6 = appUsageHistory.bucketingReason;
                    int i7 = 65280 & i6;
                    if (i7 == 1024) {
                        return;
                    }
                    int i8 = appUsageHistory.currentBucket;
                    if (i8 == 50) {
                        return;
                    }
                    int max = java.lang.Math.max(i8, 10);
                    boolean predictionTimedOut = predictionTimedOut(appUsageHistory, j);
                    if (i7 == 256 || i7 == 768 || i7 == 512 || predictionTimedOut) {
                        if (!predictionTimedOut && appUsageHistory.lastPredictedBucket >= 10 && appUsageHistory.lastPredictedBucket <= 40) {
                            max = appUsageHistory.lastPredictedBucket;
                            i6 = com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_TELE_PHONELINE;
                        } else if (i7 != 256 || (appUsageHistory.bucketingReason & 255) != 2) {
                            max = getBucketForLocked(str, i, j);
                            i6 = 512;
                        }
                    }
                    int i9 = i6;
                    long elapsedTime = this.mAppIdleHistory.getElapsedTime(j);
                    int minBucketWithValidExpiryTime = getMinBucketWithValidExpiryTime(appUsageHistory, max, elapsedTime);
                    int i10 = max;
                    if (minBucketWithValidExpiryTime == -1) {
                        i3 = i9;
                        minBucketWithValidExpiryTime = i10;
                    } else if (minBucketWithValidExpiryTime == 10 || appUsageHistory.currentBucket == minBucketWithValidExpiryTime) {
                        i3 = appUsageHistory.bucketingReason;
                    } else {
                        i3 = 775;
                    }
                    if (appUsageHistory.lastUsedByUserElapsedTime >= 0 && appUsageHistory.lastRestrictAttemptElapsedTime > appUsageHistory.lastUsedByUserElapsedTime && elapsedTime - appUsageHistory.lastUsedByUserElapsedTime >= this.mInjector.getAutoRestrictedBucketDelayMs()) {
                        i4 = appUsageHistory.lastRestrictReason;
                        minBucketWithValidExpiryTime = 45;
                    } else {
                        i4 = i3;
                    }
                    if (minBucketWithValidExpiryTime <= appMinBucket) {
                        appMinBucket = minBucketWithValidExpiryTime;
                    }
                    if (i8 != appMinBucket || predictionTimedOut) {
                        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, appMinBucket, i4);
                        str2 = str;
                        i5 = i;
                        isIdle = this.mAppIdleHistory.isIdle(str2, i5, j);
                        maybeInformListeners(str, i, j, appMinBucket, i4, false);
                    } else {
                        str2 = str;
                        i5 = i;
                        isIdle = isIdle3;
                    }
                    z = isIdle3;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (z != isIdle) {
            notifyBatteryStats(str2, i5, isIdle);
        }
    }

    private boolean predictionTimedOut(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, long j) {
        return appUsageHistory.lastPredictedTime > 0 && this.mAppIdleHistory.getElapsedTime(j) - appUsageHistory.lastPredictedTime > this.mPredictionTimeoutMillis;
    }

    private void maybeInformListeners(java.lang.String str, int i, long j, int i2, int i3, boolean z) {
        synchronized (this.mAppIdleLock) {
            try {
                if (this.mAppIdleHistory.shouldInformListeners(str, i, j, i2)) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(3, com.android.server.usage.AppStandbyController.StandbyUpdateRecord.obtain(str, i, i2, i3, z)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAppIdleLock"})
    private int getBucketForLocked(java.lang.String str, int i, long j) {
        int thresholdIndex = this.mAppIdleHistory.getThresholdIndex(str, i, j, this.mAppStandbyScreenThresholds, this.mAppStandbyElapsedThresholds);
        if (thresholdIndex >= 0) {
            return THRESHOLD_BUCKETS[thresholdIndex];
        }
        return 50;
    }

    private void notifyBatteryStats(java.lang.String str, int i, boolean z) {
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, 8192, i);
            if (z) {
                this.mInjector.noteEvent(15, str, packageUidAsUser);
            } else {
                this.mInjector.noteEvent(16, str, packageUidAsUser);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException e) {
        }
    }

    @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
    public void onUsageEvent(int i, @android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
        if (this.mAppIdleEnabled) {
            int eventType = event.getEventType();
            if (eventType == 1 || eventType == 2 || eventType == 6 || eventType == 7 || eventType == 10 || eventType == 14 || eventType == 13 || eventType == 19) {
                java.lang.String packageName = event.getPackageName();
                java.util.List<android.os.UserHandle> crossProfileTargets = getCrossProfileTargets(packageName, i);
                synchronized (this.mAppIdleLock) {
                    try {
                        long elapsedRealtime = this.mInjector.elapsedRealtime();
                        reportEventLocked(packageName, eventType, elapsedRealtime, i);
                        int size = crossProfileTargets.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            reportEventLocked(packageName, eventType, elapsedRealtime, crossProfileTargets.get(i2).getIdentifier());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAppIdleLock"})
    private void reportEventLocked(java.lang.String str, int i, long j, int i2) {
        int i3;
        int i4;
        long j2;
        com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory;
        boolean z;
        int i5;
        java.lang.String str2;
        int i6;
        long j3;
        boolean isIdle = this.mAppIdleHistory.isIdle(str, i2, j);
        com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory2 = this.mAppIdleHistory.getAppUsageHistory(str, i2, j);
        int i7 = appUsageHistory2.currentBucket;
        int i8 = appUsageHistory2.bucketingReason;
        int usageEventToSubReason = usageEventToSubReason(i);
        int i9 = usageEventToSubReason | 768;
        if (i == 10) {
            if (this.mRetainNotificationSeenImpactForPreTApps && getTargetSdkVersion(str) < 33) {
                i6 = 20;
                j3 = 43200000;
            } else {
                if (this.mTriggerQuotaBumpOnNotificationSeen) {
                    this.mHandler.obtainMessage(7, i2, -1, str).sendToTarget();
                }
                i6 = this.mNotificationSeenPromotedBucket;
                j3 = this.mNotificationSeenTimeoutMillis;
            }
            i3 = i9;
            i4 = i8;
            this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, i6, usageEventToSubReason, 0L, j + j3);
            j2 = j3;
        } else {
            i3 = i9;
            i4 = i8;
            if (i == 14) {
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 20, usageEventToSubReason, 0L, j + this.mSlicePinnedTimeoutMillis);
                j2 = this.mSlicePinnedTimeoutMillis;
            } else if (i == 6) {
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, usageEventToSubReason, 0L, j + this.mSystemInteractionTimeoutMillis);
                j2 = this.mSystemInteractionTimeoutMillis;
            } else if (i != 19) {
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, usageEventToSubReason, j, j + this.mStrongUsageTimeoutMillis);
                j2 = this.mStrongUsageTimeoutMillis;
            } else {
                if (i7 != 50) {
                    return;
                }
                this.mAppIdleHistory.reportUsage(appUsageHistory2, str, i2, 10, usageEventToSubReason, 0L, j + this.mInitialForegroundServiceStartTimeoutMillis);
                j2 = this.mInitialForegroundServiceStartTimeoutMillis;
            }
        }
        if (appUsageHistory2.currentBucket == i7) {
            appUsageHistory = appUsageHistory2;
            z = isIdle;
            i5 = i2;
            str2 = str;
        } else {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11, i2, -1, str), j2);
            appUsageHistory = appUsageHistory2;
            z = isIdle;
            i5 = i2;
            str2 = str;
            maybeInformListeners(str, i2, j, appUsageHistory2.currentBucket, i3, appUsageHistory2.currentBucket == 10 && (i4 & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) != 768);
        }
        boolean z2 = appUsageHistory.currentBucket >= 40;
        if (z != z2) {
            notifyBatteryStats(str2, i5, z2);
        }
    }

    private int getTargetSdkVersion(java.lang.String str) {
        return this.mInjector.getPackageManagerInternal().getPackageTargetSdkVersion(str);
    }

    private int getMinBucketWithValidExpiryTime(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, int i, long j) {
        if (appUsageHistory.bucketExpiryTimesMs == null) {
            return -1;
        }
        int size = appUsageHistory.bucketExpiryTimesMs.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = appUsageHistory.bucketExpiryTimesMs.keyAt(i2);
            if (i <= keyAt) {
                break;
            }
            if (appUsageHistory.bucketExpiryTimesMs.valueAt(i2) > j) {
                return keyAt;
            }
        }
        return -1;
    }

    @android.annotation.NonNull
    private java.util.List<android.os.UserHandle> getCrossProfileTargets(java.lang.String str, int i) {
        synchronized (this.mAppIdleLock) {
            try {
                if (this.mLinkCrossProfileApps) {
                    return this.mInjector.getValidCrossProfileTargets(str, i);
                }
                return java.util.Collections.emptyList();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int usageEventToSubReason(int i) {
        switch (i) {
            case 1:
                return 4;
            case 2:
                return 5;
            case 6:
                return 1;
            case 7:
                return 3;
            case 10:
                return 2;
            case 13:
                return 10;
            case 14:
                return 9;
            case 19:
                return 15;
            default:
                return 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void forceIdleState(java.lang.String str, int i, boolean z) {
        int appId;
        int idle;
        if (this.mAppIdleEnabled && (appId = getAppId(str)) >= 0) {
            int appMinBucket = getAppMinBucket(str, appId, i);
            if (z && appMinBucket < 40) {
                android.util.Slog.e(TAG, "Tried to force an app to be idle when its min bucket is " + android.app.usage.UsageStatsManager.standbyBucketToString(appMinBucket));
                return;
            }
            long elapsedRealtime = this.mInjector.elapsedRealtime();
            boolean isAppIdleFiltered = isAppIdleFiltered(str, appId, i, elapsedRealtime);
            synchronized (this.mAppIdleLock) {
                idle = this.mAppIdleHistory.setIdle(str, i, z, elapsedRealtime);
            }
            boolean isAppIdleFiltered2 = isAppIdleFiltered(str, appId, i, elapsedRealtime);
            maybeInformListeners(str, i, elapsedRealtime, idle, 1024, false);
            if (isAppIdleFiltered != isAppIdleFiltered2) {
                notifyBatteryStats(str, i, isAppIdleFiltered2);
            }
        }
    }

    public void setLastJobRunTime(java.lang.String str, int i, long j) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.setLastJobRunTime(str, i, j);
        }
    }

    public long getTimeSinceLastJobRun(java.lang.String str, int i) {
        long timeSinceLastJobRun;
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            timeSinceLastJobRun = this.mAppIdleHistory.getTimeSinceLastJobRun(str, i, elapsedRealtime);
        }
        return timeSinceLastJobRun;
    }

    public void setEstimatedLaunchTime(java.lang.String str, int i, long j) {
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.setEstimatedLaunchTime(str, i, elapsedRealtime, j);
        }
    }

    public long getEstimatedLaunchTime(java.lang.String str, int i) {
        long estimatedLaunchTime;
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            estimatedLaunchTime = this.mAppIdleHistory.getEstimatedLaunchTime(str, i, elapsedRealtime);
        }
        return estimatedLaunchTime;
    }

    public long getTimeSinceLastUsedByUser(java.lang.String str, int i) {
        long timeSinceLastUsedByUser;
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        synchronized (this.mAppIdleLock) {
            timeSinceLastUsedByUser = this.mAppIdleHistory.getTimeSinceLastUsedByUser(str, i, elapsedRealtime);
        }
        return timeSinceLastUsedByUser;
    }

    public void onUserRemoved(int i) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.onUserRemoved(i);
            synchronized (this.mActiveAdminApps) {
                this.mActiveAdminApps.remove(i);
            }
            synchronized (this.mAdminProtectedPackages) {
                this.mAdminProtectedPackages.remove(i);
            }
        }
    }

    private boolean isAppIdleUnfiltered(java.lang.String str, int i, long j) {
        boolean isIdle;
        synchronized (this.mAppIdleLock) {
            isIdle = this.mAppIdleHistory.isIdle(str, i, j);
        }
        return isIdle;
    }

    public void addListener(com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
        synchronized (this.mPackageAccessListeners) {
            try {
                if (!this.mPackageAccessListeners.contains(appIdleStateChangeListener)) {
                    this.mPackageAccessListeners.add(appIdleStateChangeListener);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeListener(com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener) {
        synchronized (this.mPackageAccessListeners) {
            this.mPackageAccessListeners.remove(appIdleStateChangeListener);
        }
    }

    public int getAppId(java.lang.String str) {
        try {
            return this.mPackageManager.getApplicationInfo(str, 4194816).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public boolean isAppIdleFiltered(java.lang.String str, int i, long j, boolean z) {
        if (z && this.mInjector.isPackageEphemeral(i, str)) {
            return false;
        }
        return isAppIdleFiltered(str, getAppId(str), i, j);
    }

    private int getAppMinBucket(java.lang.String str, int i) {
        try {
            return getAppMinBucket(str, android.os.UserHandle.getAppId(this.mPackageManager.getPackageUidAsUser(str, i)), i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 50;
        }
    }

    private int getAppMinBucket(java.lang.String str, int i, int i2) {
        if (str == null) {
            return 50;
        }
        if (!this.mAppIdleEnabled || i < 10000 || str.equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            return 5;
        }
        if (this.mSystemServicesReady) {
            if (this.mInjector.isNonIdleWhitelisted(str) || isActiveDeviceAdmin(str, i2) || isAdminProtectedPackages(str, i2) || isActiveNetworkScorer(str)) {
                return 5;
            }
            int uid = android.os.UserHandle.getUid(i2, i);
            synchronized (this.mSystemExemptionAppOpMode) {
                try {
                    if (this.mSystemExemptionAppOpMode.indexOfKey(uid) >= 0) {
                        if (this.mSystemExemptionAppOpMode.get(uid) == 0) {
                            return 5;
                        }
                    } else {
                        int checkOpNoThrow = this.mAppOpsManager.checkOpNoThrow(128, uid, str);
                        this.mSystemExemptionAppOpMode.put(uid, checkOpNoThrow);
                        if (checkOpNoThrow == 0) {
                            return 5;
                        }
                    }
                    if (this.mAppWidgetManager != null && this.mInjector.isBoundWidgetPackage(this.mAppWidgetManager, str, i2)) {
                        return 10;
                    }
                    if (isDeviceProvisioningPackage(str)) {
                        return 5;
                    }
                    if (this.mInjector.isWellbeingPackage(str) || this.mInjector.shouldGetExactAlarmBucketElevation(str, android.os.UserHandle.getUid(i2, i))) {
                        return 20;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (isCarrierApp(str)) {
            return 5;
        }
        if (isHeadlessSystemApp(str)) {
            return 10;
        }
        if (this.mPackageManager.checkPermission("android.permission.ACCESS_BACKGROUND_LOCATION", str) != 0) {
            return 50;
        }
        return 30;
    }

    private boolean isHeadlessSystemApp(java.lang.String str) {
        boolean contains;
        synchronized (this.mHeadlessSystemApps) {
            contains = this.mHeadlessSystemApps.contains(str);
        }
        return contains;
    }

    public boolean isAppIdleFiltered(java.lang.String str, int i, int i2, long j) {
        return this.mAppIdleEnabled && !this.mIsCharging && isAppIdleUnfiltered(str, i2, j) && getAppMinBucket(str, i, i2) >= 40;
    }

    static boolean isUserUsage(int i) {
        if ((65280 & i) != 768) {
            return false;
        }
        int i2 = i & 255;
        return i2 == 3 || i2 == 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] getIdleUidsForUser(int i) {
        int i2;
        int i3;
        android.content.pm.ApplicationInfo applicationInfo;
        boolean z;
        if (!this.mAppIdleEnabled) {
            return libcore.util.EmptyArray.INT;
        }
        android.os.Trace.traceBegin(64L, "getIdleUidsForUser");
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mInjector.getPackageManagerInternal().getInstalledApplications(0L, i, android.os.Process.myUid());
        if (installedApplications == null) {
            return libcore.util.EmptyArray.INT;
        }
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        int size = installedApplications.size() - 1;
        int i4 = 0;
        while (size >= 0) {
            android.content.pm.ApplicationInfo applicationInfo2 = installedApplications.get(size);
            int indexOfKey = sparseBooleanArray.indexOfKey(applicationInfo2.uid);
            boolean valueAt = indexOfKey < 0 ? true : sparseBooleanArray.valueAt(indexOfKey);
            if (valueAt) {
                i2 = indexOfKey;
                i3 = size;
                applicationInfo = applicationInfo2;
                if (isAppIdleFiltered(applicationInfo2.packageName, android.os.UserHandle.getAppId(applicationInfo2.uid), i, elapsedRealtime)) {
                    z = true;
                    if (valueAt && !z) {
                        i4++;
                    }
                    if (i2 >= 0) {
                        sparseBooleanArray.put(applicationInfo.uid, z);
                    } else {
                        sparseBooleanArray.setValueAt(i2, z);
                    }
                    size = i3 - 1;
                }
            } else {
                i2 = indexOfKey;
                i3 = size;
                applicationInfo = applicationInfo2;
            }
            z = false;
            if (valueAt) {
                i4++;
            }
            if (i2 >= 0) {
            }
            size = i3 - 1;
        }
        int size2 = sparseBooleanArray.size() - i4;
        int[] iArr = new int[size2];
        for (int size3 = sparseBooleanArray.size() - 1; size3 >= 0; size3--) {
            if (sparseBooleanArray.valueAt(size3)) {
                size2--;
                iArr[size2] = sparseBooleanArray.keyAt(size3);
            }
        }
        android.os.Trace.traceEnd(64L);
        return iArr;
    }

    public void setAppIdleAsync(java.lang.String str, boolean z, int i) {
        if (str == null || !this.mAppIdleEnabled) {
            return;
        }
        this.mHandler.obtainMessage(4, i, z ? 1 : 0, str).sendToTarget();
    }

    public int getAppStandbyBucket(java.lang.String str, int i, long j, boolean z) {
        int appStandbyBucket;
        if (!this.mAppIdleEnabled) {
            return 5;
        }
        if (z && this.mInjector.isPackageEphemeral(i, str)) {
            return 10;
        }
        synchronized (this.mAppIdleLock) {
            appStandbyBucket = this.mAppIdleHistory.getAppStandbyBucket(str, i, j);
        }
        return appStandbyBucket;
    }

    public int getAppStandbyBucketReason(java.lang.String str, int i, long j) {
        int appStandbyReason;
        synchronized (this.mAppIdleLock) {
            appStandbyReason = this.mAppIdleHistory.getAppStandbyReason(str, i, j);
        }
        return appStandbyReason;
    }

    public java.util.List<android.app.usage.AppStandbyInfo> getAppStandbyBuckets(int i) {
        java.util.ArrayList<android.app.usage.AppStandbyInfo> appStandbyBuckets;
        synchronized (this.mAppIdleLock) {
            appStandbyBuckets = this.mAppIdleHistory.getAppStandbyBuckets(i, this.mAppIdleEnabled);
        }
        return appStandbyBuckets;
    }

    public int getAppMinStandbyBucket(java.lang.String str, int i, int i2, boolean z) {
        int appMinBucket;
        if (z && this.mInjector.isPackageEphemeral(i2, str)) {
            return 50;
        }
        synchronized (this.mAppIdleLock) {
            appMinBucket = getAppMinBucket(str, i, i2);
        }
        return appMinBucket;
    }

    public void restrictApp(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        restrictApp(str, i, 1536, i2);
    }

    public void restrictApp(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        if (i2 != 1536 && i2 != 1024) {
            android.util.Slog.e(TAG, "Tried to restrict app " + str + " for an unsupported reason");
            return;
        }
        if (!this.mInjector.isPackageInstalled(str, 0, i)) {
            android.util.Slog.e(TAG, "Tried to restrict uninstalled app: " + str);
            return;
        }
        setAppStandbyBucket(str, i, 45, (i2 & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) | (i3 & 255), this.mInjector.elapsedRealtime(), false);
    }

    public void restoreAppsToRare(java.util.Set<java.lang.String> set, final int i) {
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        for (java.lang.String str : set) {
            if (!this.mInjector.isPackageInstalled(str, 0, i)) {
                android.util.Slog.i(TAG, "Tried to restore bucket for uninstalled app: " + str);
                this.mAppsToRestoreToRare.add(i, str);
            } else {
                restoreAppToRare(str, i, elapsedRealtime, 258);
            }
        }
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usage.AppStandbyController.this.lambda$restoreAppsToRare$0(i);
            }
        }, 28800000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreAppsToRare$0(int i) {
        this.mAppsToRestoreToRare.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreAppToRare(java.lang.String str, int i, long j, int i2) {
        if (getAppStandbyBucket(str, i, j, false) == 50) {
            setAppStandbyBucket(str, i, 40, i2, j, false);
        }
    }

    public void setAppStandbyBucket(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3, int i4) {
        setAppStandbyBuckets(java.util.Collections.singletonList(new android.app.usage.AppStandbyInfo(str, i)), i2, i3, i4);
    }

    public void setAppStandbyBuckets(@android.annotation.NonNull java.util.List<android.app.usage.AppStandbyInfo> list, int i, int i2, int i3) {
        int i4;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(i3, i2, i, false, true, "setAppStandbyBucket", null);
        boolean z = i2 == 0 || i2 == 2000;
        if ((android.os.UserHandle.isSameApp(i2, 1000) && i3 != android.os.Process.myPid()) || z) {
            i4 = 1024;
        } else if (android.os.UserHandle.isCore(i2)) {
            i4 = 1536;
        } else {
            i4 = 1280;
        }
        int size = list.size();
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        for (int i5 = 0; i5 < size; i5++) {
            android.app.usage.AppStandbyInfo appStandbyInfo = list.get(i5);
            java.lang.String str = appStandbyInfo.mPackageName;
            int i6 = appStandbyInfo.mStandbyBucket;
            if (i6 < 10 || i6 > 50) {
                throw new java.lang.IllegalArgumentException("Cannot set the standby bucket to " + i6);
            }
            int packageUid = this.mInjector.getPackageManagerInternal().getPackageUid(str, 4980736L, handleIncomingUser);
            if (packageUid == i2) {
                throw new java.lang.IllegalArgumentException("Cannot set your own standby bucket");
            }
            if (packageUid < 0) {
                throw new java.lang.IllegalArgumentException("Cannot set standby bucket for non existent package (" + str + ")");
            }
            setAppStandbyBucket(str, handleIncomingUser, i6, i4, elapsedRealtime, z);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAppStandbyBucket(java.lang.String str, int i, int i2, int i3) {
        setAppStandbyBucket(str, i, i2, i3, this.mInjector.elapsedRealtime(), false);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01fc A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:8:0x0014, B:10:0x001d, B:11:0x0033, B:13:0x0038, B:16:0x004e, B:18:0x0054, B:20:0x0056, B:23:0x0062, B:28:0x0071, B:31:0x0078, B:35:0x0080, B:41:0x008c, B:42:0x009f, B:45:0x00b2, B:49:0x00cb, B:50:0x00ce, B:57:0x00d6, B:59:0x00de, B:64:0x00e9, B:66:0x00eb, B:69:0x00f3, B:73:0x00fa, B:75:0x010c, B:77:0x0110, B:79:0x0115, B:81:0x0193, B:85:0x01ac, B:88:0x01cf, B:91:0x01e1, B:95:0x01fc, B:96:0x01ff, B:102:0x01b6, B:105:0x01bf, B:110:0x0137, B:111:0x014f, B:113:0x0160, B:114:0x018b, B:121:0x0060), top: B:7:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setAppStandbyBucket(java.lang.String str, int i, int i2, int i3, long j, boolean z) {
        int i4;
        int i5;
        boolean z2;
        int i6;
        int i7;
        boolean z3;
        boolean z4;
        int i8;
        if (this.mAppIdleEnabled) {
            synchronized (this.mAppIdleLock) {
                try {
                    if (!this.mInjector.isPackageInstalled(str, 0, i)) {
                        android.util.Slog.e(TAG, "Tried to set bucket of uninstalled app: " + str);
                        return;
                    }
                    com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = this.mAppIdleHistory.getAppUsageHistory(str, i, j);
                    int i9 = i3 & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK;
                    boolean z5 = i9 == 1280;
                    if (appUsageHistory.currentBucket < 10) {
                        return;
                    }
                    if ((appUsageHistory.currentBucket == 50 || i2 == 50) && z5) {
                        return;
                    }
                    boolean z6 = (appUsageHistory.bucketingReason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) == 1536;
                    if (z5 && ((appUsageHistory.bucketingReason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) == 1024 || z6)) {
                        return;
                    }
                    boolean z7 = i9 == 1536;
                    if (appUsageHistory.currentBucket == i2 && z6 && z7) {
                        if (i2 != 45) {
                            i8 = 1536;
                        } else {
                            i8 = 1536;
                            this.mAppIdleHistory.noteRestrictionAttempt(str, i, j, i3);
                        }
                        int i10 = (appUsageHistory.bucketingReason & 255) | i8 | (i3 & 255);
                        boolean z8 = appUsageHistory.currentBucket >= 40;
                        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, i2, i10, z);
                        boolean z9 = i2 >= 40;
                        if (z8 != z9) {
                            notifyBatteryStats(str, i, z9);
                        }
                        return;
                    }
                    boolean z10 = i9 == 1024;
                    if (appUsageHistory.currentBucket == 45) {
                        if ((65280 & appUsageHistory.bucketingReason) == 512) {
                            if (z5 && i2 >= 40) {
                                return;
                            }
                        } else if (!isUserUsage(i3) && !z10) {
                            return;
                        }
                    }
                    if (i2 == 45) {
                        i4 = -1;
                        i5 = 512;
                        this.mAppIdleHistory.noteRestrictionAttempt(str, i, j, i3);
                        if (z10) {
                            if (!android.os.Build.IS_DEBUGGABLE || (i3 & 255) == 2) {
                                z2 = false;
                                android.util.Slog.i(TAG, str + " restricted by user");
                            } else {
                                z2 = false;
                                android.widget.Toast.makeText(this.mContext, this.mHandler.getLooper(), this.mContext.getResources().getString(android.R.string.app_streaming_blocked_title_for_settings_dialog, str), 0).show();
                            }
                        } else {
                            z2 = false;
                            long autoRestrictedBucketDelayMs = (appUsageHistory.lastUsedByUserElapsedTime + this.mInjector.getAutoRestrictedBucketDelayMs()) - j;
                            if (autoRestrictedBucketDelayMs > 0) {
                                android.util.Slog.w(TAG, "Tried to restrict recently used app: " + str + " due to " + i3);
                                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(11, i, -1, str), autoRestrictedBucketDelayMs);
                                return;
                            }
                        }
                    } else {
                        i4 = -1;
                        i5 = 512;
                        z2 = false;
                    }
                    if (!z5) {
                        i6 = i2;
                    } else {
                        long elapsedTime = this.mAppIdleHistory.getElapsedTime(j);
                        i6 = i2;
                        int i11 = i4;
                        this.mAppIdleHistory.updateLastPrediction(appUsageHistory, elapsedTime, i6);
                        i7 = getMinBucketWithValidExpiryTime(appUsageHistory, i6, elapsedTime);
                        if (i7 != i11) {
                            if (i7 == 10 || appUsageHistory.currentBucket == i7) {
                                i5 = appUsageHistory.bucketingReason;
                            } else {
                                i5 = 775;
                            }
                        } else if (i6 == 40 && getBucketForLocked(str, i, j) == 45) {
                            i7 = 45;
                        }
                        int min = java.lang.Math.min(i7, getAppMinBucket(str, i));
                        z3 = appUsageHistory.currentBucket < 40 ? true : z2;
                        this.mAppIdleHistory.setAppStandbyBucket(str, i, j, min, i5, z);
                        z4 = min < 40;
                        if (z3 != z4) {
                            notifyBatteryStats(str, i, z4);
                        }
                        maybeInformListeners(str, i, j, min, i5, false);
                    }
                    i7 = i6;
                    i5 = i3;
                    int min2 = java.lang.Math.min(i7, getAppMinBucket(str, i));
                    z3 = appUsageHistory.currentBucket < 40 ? true : z2;
                    this.mAppIdleHistory.setAppStandbyBucket(str, i, j, min2, i5, z);
                    if (min2 < 40) {
                    }
                    if (z3 != z4) {
                    }
                    maybeInformListeners(str, i, j, min2, i5, false);
                } finally {
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isActiveDeviceAdmin(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mActiveAdminApps) {
            try {
                java.util.Set<java.lang.String> set = this.mActiveAdminApps.get(i);
                z = set != null && set.contains(str);
            } finally {
            }
        }
        return z;
    }

    private boolean isAdminProtectedPackages(java.lang.String str, int i) {
        synchronized (this.mAdminProtectedPackages) {
            try {
                boolean z = true;
                if (this.mAdminProtectedPackages.contains(-1) && this.mAdminProtectedPackages.get(-1).contains(str)) {
                    return true;
                }
                if (!this.mAdminProtectedPackages.contains(i) || !this.mAdminProtectedPackages.get(i).contains(str)) {
                    z = false;
                }
                return z;
            } finally {
            }
        }
    }

    public void addActiveDeviceAdmin(java.lang.String str, int i) {
        synchronized (this.mActiveAdminApps) {
            try {
                java.util.Set<java.lang.String> set = this.mActiveAdminApps.get(i);
                if (set == null) {
                    set = new android.util.ArraySet<>();
                    this.mActiveAdminApps.put(i, set);
                }
                set.add(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setActiveAdminApps(java.util.Set<java.lang.String> set, int i) {
        synchronized (this.mActiveAdminApps) {
            try {
                if (set == null) {
                    this.mActiveAdminApps.remove(i);
                } else {
                    this.mActiveAdminApps.put(i, set);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAdminProtectedPackages(java.util.Set<java.lang.String> set, int i) {
        synchronized (this.mAdminProtectedPackages) {
            if (set != null) {
                try {
                    if (!set.isEmpty()) {
                        this.mAdminProtectedPackages.put(i, set);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mAdminProtectedPackages.remove(i);
        }
    }

    public void onAdminDataAvailable() {
        this.mAdminDataAvailableLatch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForAdminData() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.software.device_admin")) {
            com.android.internal.util.jobs.ConcurrentUtils.waitForCountDownNoInterrupt(this.mAdminDataAvailableLatch, 10000L, "Wait for admin data");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.String> getActiveAdminAppsForTest(int i) {
        java.util.Set<java.lang.String> set;
        synchronized (this.mActiveAdminApps) {
            set = this.mActiveAdminApps.get(i);
        }
        return set;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.String> getAdminProtectedPackagesForTest(int i) {
        java.util.Set<java.lang.String> set;
        synchronized (this.mAdminProtectedPackages) {
            set = this.mAdminProtectedPackages.get(i);
        }
        return set;
    }

    private boolean isDeviceProvisioningPackage(java.lang.String str) {
        if (this.mCachedDeviceProvisioningPackage == null) {
            this.mCachedDeviceProvisioningPackage = this.mContext.getResources().getString(android.R.string.config_defaultTranslationService);
        }
        return this.mCachedDeviceProvisioningPackage.equals(str);
    }

    private boolean isCarrierApp(java.lang.String str) {
        synchronized (this.mCarrierPrivilegedLock) {
            try {
                if (!this.mHaveCarrierPrivilegedApps) {
                    fetchCarrierPrivilegedAppsCPL();
                }
                if (this.mCarrierPrivilegedApps == null) {
                    return false;
                }
                return this.mCarrierPrivilegedApps.contains(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void clearCarrierPrivilegedApps() {
        synchronized (this.mCarrierPrivilegedLock) {
            this.mHaveCarrierPrivilegedApps = false;
            this.mCarrierPrivilegedApps = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mCarrierPrivilegedLock"})
    private void fetchCarrierPrivilegedAppsCPL() {
        this.mCarrierPrivilegedApps = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).getCarrierPrivilegedPackagesForAllActiveSubscriptions();
        this.mHaveCarrierPrivilegedApps = true;
    }

    private boolean isActiveNetworkScorer(@android.annotation.NonNull java.lang.String str) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.mCachedNetworkScorer == null || this.mCachedNetworkScorerAtMillis < elapsedRealtime - NETWORK_SCORER_CACHE_DURATION_MILLIS) {
            this.mCachedNetworkScorer = this.mInjector.getActiveNetworkScorer();
            this.mCachedNetworkScorerAtMillis = elapsedRealtime;
        }
        return str.equals(this.mCachedNetworkScorer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void informListeners(java.lang.String str, int i, int i2, int i3, boolean z) {
        boolean z2 = i2 >= 40;
        synchronized (this.mPackageAccessListeners) {
            try {
                java.util.Iterator<com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener> it = this.mPackageAccessListeners.iterator();
                while (it.hasNext()) {
                    com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener next = it.next();
                    next.onAppIdleStateChanged(str, i, z2, i2, i3);
                    if (z) {
                        next.onUserInteractionStarted(str, i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void informParoleStateChanged() {
        boolean isInParole = isInParole();
        synchronized (this.mPackageAccessListeners) {
            try {
                java.util.Iterator<com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener> it = this.mPackageAccessListeners.iterator();
                while (it.hasNext()) {
                    it.next().onParoleStateChanged(isInParole);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getBroadcastResponseWindowDurationMs() {
        return this.mBroadcastResponseWindowDurationMillis;
    }

    public int getBroadcastResponseFgThresholdState() {
        return this.mBroadcastResponseFgThresholdState;
    }

    public long getBroadcastSessionsDurationMs() {
        return this.mBroadcastSessionsDurationMs;
    }

    public long getBroadcastSessionsWithResponseDurationMs() {
        return this.mBroadcastSessionsWithResponseDurationMs;
    }

    public boolean shouldNoteResponseEventForAllBroadcastSessions() {
        return this.mNoteResponseEventForAllBroadcastSessions;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getBroadcastResponseExemptedRoles() {
        return this.mBroadcastResponseExemptedRolesList;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getBroadcastResponseExemptedPermissions() {
        return this.mBroadcastResponseExemptedPermissionsList;
    }

    @android.annotation.Nullable
    public java.lang.String getAppStandbyConstant(@android.annotation.NonNull java.lang.String str) {
        return this.mAppStandbyProperties.get(str);
    }

    public void clearLastUsedTimestampsForTest(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.clearLastUsedTimestamps(str, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$flushHandler$1() {
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean flushHandler(long j) {
        return this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.usage.AppStandbyController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.usage.AppStandbyController.lambda$flushHandler$1();
            }
        }, j);
    }

    public void flushToDisk() {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.writeAppIdleTimes(this.mInjector.elapsedRealtime());
            this.mAppIdleHistory.writeAppIdleDurations();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDisplayOn() {
        return this.mInjector.isDefaultDisplayOn();
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearAppIdleForPackage(java.lang.String str, int i) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.clearUsage(str, i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeUnrestrictBuggyApp(@android.annotation.NonNull java.lang.String str, int i) {
        maybeUnrestrictApp(str, i, 1536, 4, 256, 1);
    }

    public void maybeUnrestrictApp(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3, int i4, int i5) {
        int i6;
        synchronized (this.mAppIdleLock) {
            try {
                long elapsedRealtime = this.mInjector.elapsedRealtime();
                com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = this.mAppIdleHistory.getAppUsageHistory(str, i, elapsedRealtime);
                int i7 = 45;
                if (appUsageHistory.currentBucket == 45 && (appUsageHistory.bucketingReason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) == i2) {
                    if ((appUsageHistory.bucketingReason & 255) == i3) {
                        i6 = i4 | i5;
                        i7 = 40;
                    } else {
                        i6 = (~i3) & appUsageHistory.bucketingReason;
                    }
                    this.mAppIdleHistory.setAppStandbyBucket(str, i, elapsedRealtime, i7, i6);
                    maybeInformListeners(str, i, elapsedRealtime, i7, i6, false);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePowerWhitelistCache() {
        if (this.mInjector.getBootPhase() < 500) {
            return;
        }
        this.mInjector.updatePowerWhitelistCache();
        postCheckIdleStates(-1);
    }

    private class PackageReceiver extends android.content.BroadcastReceiver {
        private PackageReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            int sendingUserId = getSendingUserId();
            if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
                java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                if (stringArrayExtra == null || (stringArrayExtra.length == 1 && schemeSpecificPart.equals(stringArrayExtra[0]))) {
                    com.android.server.usage.AppStandbyController.this.clearCarrierPrivilegedApps();
                    com.android.server.usage.AppStandbyController.this.evaluateSystemAppException(schemeSpecificPart, sendingUserId);
                }
                if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                    com.android.server.usage.AppStandbyController.this.mHandler.obtainMessage(11, sendingUserId, -1, schemeSpecificPart).sendToTarget();
                }
            }
            if ("android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_ADDED".equals(action)) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    com.android.server.usage.AppStandbyController.this.maybeUnrestrictBuggyApp(schemeSpecificPart, sendingUserId);
                } else if (!"android.intent.action.PACKAGE_ADDED".equals(action)) {
                    com.android.server.usage.AppStandbyController.this.clearAppIdleForPackage(schemeSpecificPart, sendingUserId);
                } else if (com.android.server.usage.AppStandbyController.this.mAppsToRestoreToRare.contains(sendingUserId, schemeSpecificPart)) {
                    com.android.server.usage.AppStandbyController.this.restoreAppToRare(schemeSpecificPart, sendingUserId, com.android.server.usage.AppStandbyController.this.mInjector.elapsedRealtime(), 258);
                    com.android.server.usage.AppStandbyController.this.mAppsToRestoreToRare.remove(sendingUserId, schemeSpecificPart);
                }
            }
            synchronized (com.android.server.usage.AppStandbyController.this.mSystemExemptionAppOpMode) {
                try {
                    if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        com.android.server.usage.AppStandbyController.this.mSystemExemptionAppOpMode.delete(intent.getIntExtra("android.intent.extra.UID", -1));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evaluateSystemAppException(java.lang.String str, int i) {
        if (!this.mSystemServicesReady) {
            return;
        }
        try {
            maybeUpdateHeadlessSystemAppCache(this.mPackageManager.getPackageInfoAsUser(str, HEADLESS_APP_CHECK_FLAGS, i));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            synchronized (this.mHeadlessSystemApps) {
                this.mHeadlessSystemApps.remove(str);
            }
        }
    }

    private boolean maybeUpdateHeadlessSystemAppCache(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        if (packageInfo == null || packageInfo.applicationInfo == null || (!packageInfo.applicationInfo.isSystemApp() && !packageInfo.applicationInfo.isUpdatedSystemApp())) {
            return false;
        }
        return updateHeadlessSystemAppCache(packageInfo.packageName, com.android.internal.util.jobs.ArrayUtils.isEmpty(this.mPackageManager.queryIntentActivitiesAsUser(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(packageInfo.packageName), HEADLESS_APP_CHECK_FLAGS, 0)));
    }

    private boolean updateHeadlessSystemAppCache(java.lang.String str, boolean z) {
        synchronized (this.mHeadlessSystemApps) {
            try {
                if (z) {
                    return this.mHeadlessSystemApps.add(str);
                }
                return this.mHeadlessSystemApps.remove(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void initializeDefaultsForSystemApps(int i) {
        int i2;
        if (!this.mSystemServicesReady) {
            this.mPendingInitializeDefaults = true;
            return;
        }
        android.util.Slog.d(TAG, "Initializing defaults for system apps on user " + i + ", appIdleEnabled=" + this.mAppIdleEnabled);
        long elapsedRealtime = this.mInjector.elapsedRealtime();
        java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(512, i);
        int size = installedPackagesAsUser.size();
        synchronized (this.mAppIdleLock) {
            int i3 = 0;
            while (i3 < size) {
                try {
                    android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) installedPackagesAsUser.get(i3);
                    java.lang.String str = packageInfo.packageName;
                    if (packageInfo.applicationInfo != null && packageInfo.applicationInfo.isSystemApp()) {
                        i2 = i3;
                        this.mAppIdleHistory.reportUsage(str, i, 10, 6, 0L, elapsedRealtime + this.mSystemUpdateUsageTimeoutMillis);
                    } else {
                        i2 = i3;
                    }
                    i3 = i2 + 1;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mAppIdleHistory.writeAppIdleTimes(i, elapsedRealtime);
        }
    }

    private java.util.Set<java.lang.String> getSystemPackagesWithLauncherActivities() {
        java.util.List queryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER"), HEADLESS_APP_CHECK_FLAGS, 0);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator it = queryIntentActivitiesAsUser.iterator();
        while (it.hasNext()) {
            arraySet.add(((android.content.pm.ResolveInfo) it.next()).activityInfo.packageName);
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadHeadlessSystemAppCache() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(HEADLESS_APP_CHECK_FLAGS, 0);
        java.util.Set<java.lang.String> systemPackagesWithLauncherActivities = getSystemPackagesWithLauncherActivities();
        int size = installedPackagesAsUser.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) installedPackagesAsUser.get(i);
            if (packageInfo != null) {
                java.lang.String str = packageInfo.packageName;
                if (updateHeadlessSystemAppCache(str, !systemPackagesWithLauncherActivities.contains(str))) {
                    this.mHandler.obtainMessage(11, 0, -1, str).sendToTarget();
                }
            }
        }
        android.util.Slog.d(TAG, "Loaded headless system app cache in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + " ms: appIdleEnabled=" + this.mAppIdleEnabled);
    }

    public void postReportContentProviderUsage(java.lang.String str, java.lang.String str2, int i) {
        this.mHandler.obtainMessage(8, com.android.server.usage.AppStandbyController.ContentProviderUsageRecord.obtain(str, str2, i)).sendToTarget();
    }

    public void postReportSyncScheduled(java.lang.String str, int i, boolean z) {
        this.mHandler.obtainMessage(12, i, z ? 1 : 0, str).sendToTarget();
    }

    public void postReportExemptedSyncStart(java.lang.String str, int i) {
        this.mHandler.obtainMessage(13, i, 0, str).sendToTarget();
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.usage.AppIdleHistory getAppIdleHistoryForTest() {
        com.android.server.usage.AppIdleHistory appIdleHistory;
        synchronized (this.mAppIdleLock) {
            appIdleHistory = this.mAppIdleHistory;
        }
        return appIdleHistory;
    }

    public void dumpUsers(android.util.IndentingPrintWriter indentingPrintWriter, int[] iArr, java.util.List<java.lang.String> list) {
        synchronized (this.mAppIdleLock) {
            this.mAppIdleHistory.dumpUsers(indentingPrintWriter, iArr, list);
        }
    }

    public void dumpState(java.lang.String[] strArr, java.io.PrintWriter printWriter) {
        int i;
        synchronized (this.mCarrierPrivilegedLock) {
            printWriter.println("Carrier privileged apps (have=" + this.mHaveCarrierPrivilegedApps + "): " + this.mCarrierPrivilegedApps);
        }
        printWriter.println();
        printWriter.println("Settings:");
        printWriter.print("  mCheckIdleIntervalMillis=");
        android.util.TimeUtils.formatDuration(this.mCheckIdleIntervalMillis, printWriter);
        printWriter.println();
        printWriter.print("  mStrongUsageTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mStrongUsageTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mNotificationSeenTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mNotificationSeenTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mNotificationSeenPromotedBucket=");
        printWriter.print(android.app.usage.UsageStatsManager.standbyBucketToString(this.mNotificationSeenPromotedBucket));
        printWriter.println();
        printWriter.print("  mTriggerQuotaBumpOnNotificationSeen=");
        printWriter.print(this.mTriggerQuotaBumpOnNotificationSeen);
        printWriter.println();
        printWriter.print("  mRetainNotificationSeenImpactForPreTApps=");
        printWriter.print(this.mRetainNotificationSeenImpactForPreTApps);
        printWriter.println();
        printWriter.print("  mSlicePinnedTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mSlicePinnedTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSyncAdapterTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mSyncAdapterTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSystemInteractionTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mSystemInteractionTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mInitialForegroundServiceStartTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mInitialForegroundServiceStartTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mPredictionTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mPredictionTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncScheduledNonDozeTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mExemptedSyncScheduledNonDozeTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncScheduledDozeTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mExemptedSyncScheduledDozeTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mExemptedSyncStartTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mExemptedSyncStartTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mUnexemptedSyncScheduledTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mUnexemptedSyncScheduledTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mSystemUpdateUsageTimeoutMillis=");
        android.util.TimeUtils.formatDuration(this.mSystemUpdateUsageTimeoutMillis, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastResponseWindowDurationMillis=");
        android.util.TimeUtils.formatDuration(this.mBroadcastResponseWindowDurationMillis, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastResponseFgThresholdState=");
        printWriter.print(android.app.ActivityManager.procStateToString(this.mBroadcastResponseFgThresholdState));
        printWriter.println();
        printWriter.print("  mBroadcastSessionsDurationMs=");
        android.util.TimeUtils.formatDuration(this.mBroadcastSessionsDurationMs, printWriter);
        printWriter.println();
        printWriter.print("  mBroadcastSessionsWithResponseDurationMs=");
        android.util.TimeUtils.formatDuration(this.mBroadcastSessionsWithResponseDurationMs, printWriter);
        printWriter.println();
        printWriter.print("  mNoteResponseEventForAllBroadcastSessions=");
        printWriter.print(this.mNoteResponseEventForAllBroadcastSessions);
        printWriter.println();
        printWriter.print("  mBroadcastResponseExemptedRoles=");
        printWriter.print(this.mBroadcastResponseExemptedRoles);
        printWriter.println();
        printWriter.print("  mBroadcastResponseExemptedPermissions=");
        printWriter.print(this.mBroadcastResponseExemptedPermissions);
        printWriter.println();
        printWriter.println();
        printWriter.print("mAppIdleEnabled=");
        printWriter.print(this.mAppIdleEnabled);
        printWriter.print(" mIsCharging=");
        printWriter.print(this.mIsCharging);
        printWriter.println();
        printWriter.print("mScreenThresholds=");
        printWriter.println(java.util.Arrays.toString(this.mAppStandbyScreenThresholds));
        printWriter.print("mElapsedThresholds=");
        printWriter.println(java.util.Arrays.toString(this.mAppStandbyElapsedThresholds));
        printWriter.println();
        printWriter.println("mHeadlessSystemApps=[");
        synchronized (this.mHeadlessSystemApps) {
            try {
                for (int size = this.mHeadlessSystemApps.size() - 1; size >= 0; size--) {
                    printWriter.print("  ");
                    printWriter.print(this.mHeadlessSystemApps.valueAt(size));
                    if (size != 0) {
                        printWriter.println(",");
                    }
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mSystemPackagesAppIds=[");
        synchronized (this.mSystemPackagesAppIds) {
            try {
                for (int size2 = this.mSystemPackagesAppIds.size() - 1; size2 >= 0; size2--) {
                    printWriter.print("  ");
                    printWriter.print(this.mSystemPackagesAppIds.get(size2));
                    if (size2 != 0) {
                        printWriter.println(",");
                    }
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mActiveAdminApps=[");
        synchronized (this.mActiveAdminApps) {
            try {
                int size3 = this.mActiveAdminApps.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    int keyAt = this.mActiveAdminApps.keyAt(i2);
                    printWriter.print(" ");
                    printWriter.print(keyAt);
                    printWriter.print(": ");
                    printWriter.print(this.mActiveAdminApps.valueAt(i2));
                    if (i2 != size3 - 1) {
                        printWriter.print(",");
                    }
                    printWriter.println();
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        printWriter.println("mAdminProtectedPackages=[");
        synchronized (this.mAdminProtectedPackages) {
            try {
                int size4 = this.mAdminProtectedPackages.size();
                for (i = 0; i < size4; i++) {
                    int keyAt2 = this.mAdminProtectedPackages.keyAt(i);
                    printWriter.print(" ");
                    printWriter.print(keyAt2);
                    printWriter.print(": ");
                    printWriter.print(this.mAdminProtectedPackages.valueAt(i));
                    if (i != size4 - 1) {
                        printWriter.print(",");
                    }
                    printWriter.println();
                }
            } finally {
            }
        }
        printWriter.println("]");
        printWriter.println();
        this.mInjector.dump(printWriter);
    }

    static class Injector {
        private com.android.server.AlarmManagerInternal mAlarmManagerInternal;
        private android.os.BatteryManager mBatteryManager;
        private com.android.internal.app.IBatteryStats mBatteryStats;
        int mBootPhase;
        private final android.content.Context mContext;
        private android.content.pm.CrossProfileAppsInternal mCrossProfileAppsInternal;
        private android.os.IDeviceIdleController mDeviceIdleController;
        private android.hardware.display.DisplayManager mDisplayManager;
        private final android.os.Looper mLooper;
        private android.content.pm.PackageManagerInternal mPackageManagerInternal;
        private android.os.PowerManager mPowerManager;
        long mAutoRestrictedBucketDelayMs = 3600000;

        @com.android.internal.annotations.GuardedBy({"mPowerWhitelistedApps"})
        private final android.util.ArraySet<java.lang.String> mPowerWhitelistedApps = new android.util.ArraySet<>();
        private java.lang.String mWellbeingApp = null;

        Injector(android.content.Context context, android.os.Looper looper) {
            this.mContext = context;
            this.mLooper = looper;
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        android.os.Looper getLooper() {
            return this.mLooper;
        }

        void onBootPhase(int i) {
            if (i == 500) {
                this.mDeviceIdleController = android.os.IDeviceIdleController.Stub.asInterface(android.os.ServiceManager.getService("deviceidle"));
                this.mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
                this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService("display");
                this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
                this.mBatteryManager = (android.os.BatteryManager) this.mContext.getSystemService(android.os.BatteryManager.class);
                this.mCrossProfileAppsInternal = (android.content.pm.CrossProfileAppsInternal) com.android.server.LocalServices.getService(android.content.pm.CrossProfileAppsInternal.class);
                this.mAlarmManagerInternal = (com.android.server.AlarmManagerInternal) com.android.server.LocalServices.getService(com.android.server.AlarmManagerInternal.class);
                if (((android.app.ActivityManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY)).isLowRamDevice() || android.app.ActivityManager.isSmallBatteryDevice()) {
                    this.mAutoRestrictedBucketDelayMs = 43200000L;
                }
            } else if (i == 1000) {
                this.mWellbeingApp = this.mContext.getPackageManager().getWellbeingPackageName();
            }
            this.mBootPhase = i;
        }

        int getBootPhase() {
            return this.mBootPhase;
        }

        long elapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        boolean isAppIdleEnabled() {
            return this.mContext.getResources().getBoolean(android.R.bool.config_enableAppWidgetService) && (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "app_standby_enabled", 1) == 1 && android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "adaptive_battery_management_enabled", 1) == 1);
        }

        boolean isCharging() {
            return this.mBatteryManager.isCharging();
        }

        boolean isNonIdleWhitelisted(java.lang.String str) {
            boolean contains;
            if (this.mBootPhase < 500) {
                return false;
            }
            synchronized (this.mPowerWhitelistedApps) {
                contains = this.mPowerWhitelistedApps.contains(str);
            }
            return contains;
        }

        com.android.internal.app.IAppOpsService getAppOpsService() {
            return com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        }

        boolean isWellbeingPackage(@android.annotation.NonNull java.lang.String str) {
            return str.equals(this.mWellbeingApp);
        }

        boolean shouldGetExactAlarmBucketElevation(java.lang.String str, int i) {
            return this.mAlarmManagerInternal.shouldGetBucketElevation(str, i);
        }

        void updatePowerWhitelistCache() {
            try {
                java.lang.String[] fullPowerWhitelistExceptIdle = this.mDeviceIdleController.getFullPowerWhitelistExceptIdle();
                synchronized (this.mPowerWhitelistedApps) {
                    try {
                        this.mPowerWhitelistedApps.clear();
                        for (java.lang.String str : fullPowerWhitelistExceptIdle) {
                            this.mPowerWhitelistedApps.add(str);
                        }
                    } finally {
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(com.android.server.usage.AppStandbyController.TAG, "Failed to get power whitelist", e);
            }
        }

        java.io.File getDataSystemDirectory() {
            return android.os.Environment.getDataSystemDirectory();
        }

        long getAutoRestrictedBucketDelayMs() {
            return this.mAutoRestrictedBucketDelayMs;
        }

        void noteEvent(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            if (this.mBatteryStats != null) {
                this.mBatteryStats.noteEvent(i, str, i2);
            }
        }

        android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return this.mPackageManagerInternal;
        }

        boolean isPackageEphemeral(int i, java.lang.String str) {
            return this.mPackageManagerInternal.isPackageEphemeral(i, str);
        }

        boolean isPackageInstalled(java.lang.String str, int i, int i2) {
            return this.mPackageManagerInternal.getPackageUid(str, (long) i, i2) >= 0;
        }

        int[] getRunningUserIds() throws android.os.RemoteException {
            return android.app.ActivityManager.getService().getRunningUserIds();
        }

        boolean isDefaultDisplayOn() {
            return this.mDisplayManager.getDisplay(0).getState() == 2;
        }

        void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler) {
            this.mDisplayManager.registerDisplayListener(displayListener, handler);
        }

        java.lang.String getActiveNetworkScorer() {
            return ((android.net.NetworkScoreManager) this.mContext.getSystemService("network_score")).getActiveScorerPackage();
        }

        public boolean isBoundWidgetPackage(android.appwidget.AppWidgetManager appWidgetManager, java.lang.String str, int i) {
            return appWidgetManager.isBoundWidgetPackage(str, i);
        }

        @android.annotation.NonNull
        android.provider.DeviceConfig.Properties getDeviceConfigProperties(java.lang.String... strArr) {
            return android.provider.DeviceConfig.getProperties("app_standby", strArr);
        }

        public boolean isDeviceIdleMode() {
            return this.mPowerManager.isDeviceIdleMode();
        }

        public java.util.List<android.os.UserHandle> getValidCrossProfileTargets(java.lang.String str, int i) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, i);
            com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(packageUid);
            if (packageUid < 0 || androidPackage == null || !androidPackage.isCrossProfile() || !this.mCrossProfileAppsInternal.verifyUidHasInteractAcrossProfilePermission(str, packageUid)) {
                if (packageUid >= 0 && androidPackage == null) {
                    android.util.Slog.wtf(com.android.server.usage.AppStandbyController.TAG, "Null package retrieved for UID " + packageUid);
                }
                return java.util.Collections.emptyList();
            }
            return this.mCrossProfileAppsInternal.getTargetUserProfiles(str, i);
        }

        void registerDeviceConfigPropertiesChangedListener(@android.annotation.NonNull android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("app_standby", com.android.server.AppSchedulingModuleThread.getExecutor(), onPropertiesChangedListener);
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("mPowerWhitelistedApps=[");
            synchronized (this.mPowerWhitelistedApps) {
                try {
                    for (int size = this.mPowerWhitelistedApps.size() - 1; size >= 0; size--) {
                        printWriter.print("  ");
                        printWriter.print(this.mPowerWhitelistedApps.valueAt(size));
                        printWriter.println(",");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            printWriter.println("]");
            printWriter.println();
        }
    }

    class AppStandbyHandler extends android.os.Handler {
        AppStandbyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            long j;
            switch (message.what) {
                case 3:
                    com.android.server.usage.AppStandbyController.StandbyUpdateRecord standbyUpdateRecord = (com.android.server.usage.AppStandbyController.StandbyUpdateRecord) message.obj;
                    com.android.server.usage.AppStandbyController.this.informListeners(standbyUpdateRecord.packageName, standbyUpdateRecord.userId, standbyUpdateRecord.bucket, standbyUpdateRecord.reason, standbyUpdateRecord.isUserInteraction);
                    standbyUpdateRecord.recycle();
                    return;
                case 4:
                    com.android.server.usage.AppStandbyController.this.forceIdleState((java.lang.String) message.obj, message.arg1, message.arg2 == 1);
                    return;
                case 5:
                    removeMessages(5);
                    long elapsedRealtime = com.android.server.usage.AppStandbyController.this.mInjector.elapsedRealtime();
                    synchronized (com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks) {
                        try {
                            j = Long.MAX_VALUE;
                            for (int size = com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks.size() - 1; size >= 0; size--) {
                                long valueAt = com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks.valueAt(size);
                                if (valueAt <= elapsedRealtime) {
                                    int keyAt = com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks.keyAt(size);
                                    if (com.android.server.usage.AppStandbyController.this.checkIdleStates(keyAt) && com.android.server.usage.AppStandbyController.this.mAppIdleEnabled) {
                                        valueAt = com.android.server.usage.AppStandbyController.this.mCheckIdleIntervalMillis + elapsedRealtime;
                                        com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks.put(keyAt, valueAt);
                                    } else {
                                        com.android.server.usage.AppStandbyController.this.mPendingIdleStateChecks.removeAt(size);
                                    }
                                }
                                j = java.lang.Math.min(j, valueAt);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    if (j != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                        com.android.server.usage.AppStandbyController.this.mHandler.sendMessageDelayed(com.android.server.usage.AppStandbyController.this.mHandler.obtainMessage(5), j - elapsedRealtime);
                        return;
                    }
                    return;
                case 6:
                default:
                    super.handleMessage(message);
                    return;
                case 7:
                    com.android.server.usage.AppStandbyController.this.triggerListenerQuotaBump((java.lang.String) message.obj, message.arg1);
                    return;
                case 8:
                    com.android.server.usage.AppStandbyController.ContentProviderUsageRecord contentProviderUsageRecord = (com.android.server.usage.AppStandbyController.ContentProviderUsageRecord) message.obj;
                    com.android.server.usage.AppStandbyController.this.reportContentProviderUsage(contentProviderUsageRecord.name, contentProviderUsageRecord.packageName, contentProviderUsageRecord.userId);
                    contentProviderUsageRecord.recycle();
                    return;
                case 9:
                    com.android.server.usage.AppStandbyController.this.informParoleStateChanged();
                    return;
                case 10:
                    com.android.server.usage.AppStandbyController.this.mHandler.removeMessages(10);
                    com.android.server.usage.AppStandbyController.this.waitForAdminData();
                    com.android.server.usage.AppStandbyController.this.checkIdleStates(-1);
                    return;
                case 11:
                    com.android.server.usage.AppStandbyController.this.checkAndUpdateStandbyState((java.lang.String) message.obj, message.arg1, message.arg2, com.android.server.usage.AppStandbyController.this.mInjector.elapsedRealtime());
                    return;
                case 12:
                    if (message.arg2 > 0) {
                        com.android.server.usage.AppStandbyController.this.reportExemptedSyncScheduled((java.lang.String) message.obj, message.arg1);
                        return;
                    } else {
                        com.android.server.usage.AppStandbyController.this.reportUnexemptedSyncScheduled((java.lang.String) message.obj, message.arg1);
                        return;
                    }
                case 13:
                    com.android.server.usage.AppStandbyController.this.reportExemptedSyncStart((java.lang.String) message.obj, message.arg1);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeviceStateReceiver extends android.content.BroadcastReceiver {
        private DeviceStateReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -65633567:
                    if (action.equals("android.os.action.POWER_SAVE_WHITELIST_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -54942926:
                    if (action.equals("android.os.action.DISCHARGING")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 948344062:
                    if (action.equals("android.os.action.CHARGING")) {
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
                    com.android.server.usage.AppStandbyController.this.setChargingState(true);
                    break;
                case 1:
                    com.android.server.usage.AppStandbyController.this.setChargingState(false);
                    break;
                case 2:
                    if (com.android.server.usage.AppStandbyController.this.mSystemServicesReady) {
                        com.android.server.usage.AppStandbyController.AppStandbyHandler appStandbyHandler = com.android.server.usage.AppStandbyController.this.mHandler;
                        final com.android.server.usage.AppStandbyController appStandbyController = com.android.server.usage.AppStandbyController.this;
                        appStandbyHandler.post(new java.lang.Runnable() { // from class: com.android.server.usage.AppStandbyController$DeviceStateReceiver$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.usage.AppStandbyController.this.updatePowerWhitelistCache();
                            }
                        });
                        break;
                    }
                    break;
            }
        }
    }

    private class ConstantsObserver extends android.database.ContentObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        public static final long DEFAULT_AUTO_RESTRICTED_BUCKET_DELAY_MS = 3600000;
        private static final java.lang.String DEFAULT_BROADCAST_RESPONSE_EXEMPTED_PERMISSIONS = "";
        private static final java.lang.String DEFAULT_BROADCAST_RESPONSE_EXEMPTED_ROLES = "";
        public static final int DEFAULT_BROADCAST_RESPONSE_FG_THRESHOLD_STATE = 2;
        public static final long DEFAULT_BROADCAST_RESPONSE_WINDOW_DURATION_MS = 120000;
        public static final long DEFAULT_BROADCAST_SESSIONS_DURATION_MS = 120000;
        public static final long DEFAULT_BROADCAST_SESSIONS_WITH_RESPONSE_DURATION_MS = 120000;
        public static final long DEFAULT_CHECK_IDLE_INTERVAL_MS = 14400000;
        public static final boolean DEFAULT_CROSS_PROFILE_APPS_SHARE_STANDBY_BUCKETS = true;
        public static final long DEFAULT_EXEMPTED_SYNC_SCHEDULED_DOZE_TIMEOUT = 14400000;
        public static final long DEFAULT_EXEMPTED_SYNC_SCHEDULED_NON_DOZE_TIMEOUT = 600000;
        public static final long DEFAULT_EXEMPTED_SYNC_START_TIMEOUT = 600000;
        public static final long DEFAULT_INITIAL_FOREGROUND_SERVICE_START_TIMEOUT = 1800000;
        public static final boolean DEFAULT_NOTE_RESPONSE_EVENT_FOR_ALL_BROADCAST_SESSIONS = true;
        public static final int DEFAULT_NOTIFICATION_SEEN_PROMOTED_BUCKET = 20;
        public static final long DEFAULT_NOTIFICATION_TIMEOUT = 43200000;
        public static final boolean DEFAULT_RETAIN_NOTIFICATION_SEEN_IMPACT_FOR_PRE_T_APPS = false;
        public static final long DEFAULT_SLICE_PINNED_TIMEOUT = 43200000;
        public static final long DEFAULT_STRONG_USAGE_TIMEOUT = 3600000;
        public static final long DEFAULT_SYNC_ADAPTER_TIMEOUT = 600000;
        public static final long DEFAULT_SYSTEM_INTERACTION_TIMEOUT = 600000;
        public static final long DEFAULT_SYSTEM_UPDATE_TIMEOUT = 7200000;
        public static final boolean DEFAULT_TRIGGER_QUOTA_BUMP_ON_NOTIFICATION_SEEN = false;
        public static final long DEFAULT_UNEXEMPTED_SYNC_SCHEDULED_TIMEOUT = 600000;
        private static final java.lang.String KEY_AUTO_RESTRICTED_BUCKET_DELAY_MS = "auto_restricted_bucket_delay_ms";
        private static final java.lang.String KEY_BROADCAST_RESPONSE_EXEMPTED_PERMISSIONS = "brodacast_response_exempted_permissions";
        private static final java.lang.String KEY_BROADCAST_RESPONSE_EXEMPTED_ROLES = "brodacast_response_exempted_roles";
        private static final java.lang.String KEY_BROADCAST_RESPONSE_FG_THRESHOLD_STATE = "broadcast_response_fg_threshold_state";
        private static final java.lang.String KEY_BROADCAST_RESPONSE_WINDOW_DURATION_MS = "broadcast_response_window_timeout_ms";
        private static final java.lang.String KEY_BROADCAST_SESSIONS_DURATION_MS = "broadcast_sessions_duration_ms";
        private static final java.lang.String KEY_BROADCAST_SESSIONS_WITH_RESPONSE_DURATION_MS = "broadcast_sessions_with_response_duration_ms";
        private static final java.lang.String KEY_CROSS_PROFILE_APPS_SHARE_STANDBY_BUCKETS = "cross_profile_apps_share_standby_buckets";
        private static final java.lang.String KEY_EXEMPTED_SYNC_SCHEDULED_DOZE_HOLD_DURATION = "exempted_sync_scheduled_d_duration";
        private static final java.lang.String KEY_EXEMPTED_SYNC_SCHEDULED_NON_DOZE_HOLD_DURATION = "exempted_sync_scheduled_nd_duration";
        private static final java.lang.String KEY_EXEMPTED_SYNC_START_HOLD_DURATION = "exempted_sync_start_duration";
        private static final java.lang.String KEY_INITIAL_FOREGROUND_SERVICE_START_HOLD_DURATION = "initial_foreground_service_start_duration";
        private static final java.lang.String KEY_NOTE_RESPONSE_EVENT_FOR_ALL_BROADCAST_SESSIONS = "note_response_event_for_all_broadcast_sessions";
        private static final java.lang.String KEY_NOTIFICATION_SEEN_HOLD_DURATION = "notification_seen_duration";
        private static final java.lang.String KEY_NOTIFICATION_SEEN_PROMOTED_BUCKET = "notification_seen_promoted_bucket";
        private static final java.lang.String KEY_PREDICTION_TIMEOUT = "prediction_timeout";
        private static final java.lang.String KEY_PREFIX_ELAPSED_TIME_THRESHOLD = "elapsed_threshold_";
        private static final java.lang.String KEY_PREFIX_SCREEN_TIME_THRESHOLD = "screen_threshold_";
        private static final java.lang.String KEY_RETAIN_NOTIFICATION_SEEN_IMPACT_FOR_PRE_T_APPS = "retain_notification_seen_impact_for_pre_t_apps";
        private static final java.lang.String KEY_SLICE_PINNED_HOLD_DURATION = "slice_pinned_duration";
        private static final java.lang.String KEY_STRONG_USAGE_HOLD_DURATION = "strong_usage_duration";
        private static final java.lang.String KEY_SYNC_ADAPTER_HOLD_DURATION = "sync_adapter_duration";
        private static final java.lang.String KEY_SYSTEM_INTERACTION_HOLD_DURATION = "system_interaction_duration";
        private static final java.lang.String KEY_SYSTEM_UPDATE_HOLD_DURATION = "system_update_usage_duration";
        private static final java.lang.String KEY_TRIGGER_QUOTA_BUMP_ON_NOTIFICATION_SEEN = "trigger_quota_bump_on_notification_seen";
        private static final java.lang.String KEY_UNEXEMPTED_SYNC_SCHEDULED_HOLD_DURATION = "unexempted_sync_scheduled_duration";
        private final java.lang.String[] KEYS_ELAPSED_TIME_THRESHOLDS;
        private final java.lang.String[] KEYS_SCREEN_TIME_THRESHOLDS;
        private final android.text.TextUtils.SimpleStringSplitter mStringPipeSplitter;

        ConstantsObserver(android.os.Handler handler) {
            super(handler);
            this.KEYS_SCREEN_TIME_THRESHOLDS = new java.lang.String[]{"screen_threshold_active", "screen_threshold_working_set", "screen_threshold_frequent", "screen_threshold_rare", "screen_threshold_restricted"};
            this.KEYS_ELAPSED_TIME_THRESHOLDS = new java.lang.String[]{"elapsed_threshold_active", "elapsed_threshold_working_set", "elapsed_threshold_frequent", "elapsed_threshold_rare", "elapsed_threshold_restricted"};
            this.mStringPipeSplitter = new android.text.TextUtils.SimpleStringSplitter('|');
        }

        public void start() {
            android.content.ContentResolver contentResolver = com.android.server.usage.AppStandbyController.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("app_standby_enabled"), false, this);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("adaptive_battery_management_enabled"), false, this);
            com.android.server.usage.AppStandbyController.this.mInjector.registerDeviceConfigPropertiesChangedListener(this);
            processProperties(com.android.server.usage.AppStandbyController.this.mInjector.getDeviceConfigProperties(new java.lang.String[0]));
            updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            updateSettings();
            com.android.server.usage.AppStandbyController.this.postOneTimeCheckIdleStates();
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            processProperties(properties);
            com.android.server.usage.AppStandbyController.this.postOneTimeCheckIdleStates();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void processProperties(android.provider.DeviceConfig.Properties properties) {
            char c;
            synchronized (com.android.server.usage.AppStandbyController.this.mAppIdleLock) {
                try {
                    boolean z = false;
                    for (java.lang.String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -1991469656:
                                    if (str.equals(KEY_SYNC_ADAPTER_HOLD_DURATION)) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1963219299:
                                    if (str.equals(KEY_BROADCAST_RESPONSE_EXEMPTED_PERMISSIONS)) {
                                        c = 23;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1794959158:
                                    if (str.equals(KEY_TRIGGER_QUOTA_BUMP_ON_NOTIFICATION_SEEN)) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1610671326:
                                    if (str.equals(KEY_UNEXEMPTED_SYNC_SCHEDULED_HOLD_DURATION)) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1525033432:
                                    if (str.equals(KEY_BROADCAST_SESSIONS_WITH_RESPONSE_DURATION_MS)) {
                                        c = 20;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1063555730:
                                    if (str.equals(KEY_SLICE_PINNED_HOLD_DURATION)) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -973233853:
                                    if (str.equals(KEY_AUTO_RESTRICTED_BUCKET_DELAY_MS)) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -695619964:
                                    if (str.equals(KEY_NOTIFICATION_SEEN_HOLD_DURATION)) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -654339791:
                                    if (str.equals(KEY_SYSTEM_INTERACTION_HOLD_DURATION)) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -641750299:
                                    if (str.equals(KEY_NOTE_RESPONSE_EVENT_FOR_ALL_BROADCAST_SESSIONS)) {
                                        c = 21;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -557676904:
                                    if (str.equals(KEY_SYSTEM_UPDATE_HOLD_DURATION)) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -294320234:
                                    if (str.equals(KEY_BROADCAST_RESPONSE_EXEMPTED_ROLES)) {
                                        c = 22;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -129077581:
                                    if (str.equals(KEY_BROADCAST_RESPONSE_WINDOW_DURATION_MS)) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -57661244:
                                    if (str.equals(KEY_EXEMPTED_SYNC_SCHEDULED_DOZE_HOLD_DURATION)) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 276460958:
                                    if (str.equals(KEY_RETAIN_NOTIFICATION_SEEN_IMPACT_FOR_PRE_T_APPS)) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 456604392:
                                    if (str.equals(KEY_EXEMPTED_SYNC_SCHEDULED_NON_DOZE_HOLD_DURATION)) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 742365823:
                                    if (str.equals(KEY_BROADCAST_RESPONSE_FG_THRESHOLD_STATE)) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 938381045:
                                    if (str.equals(KEY_NOTIFICATION_SEEN_PROMOTED_BUCKET)) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 992238669:
                                    if (str.equals(KEY_BROADCAST_SESSIONS_DURATION_MS)) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1105744372:
                                    if (str.equals(KEY_EXEMPTED_SYNC_START_HOLD_DURATION)) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1288386175:
                                    if (str.equals(KEY_CROSS_PROFILE_APPS_SHARE_STANDBY_BUCKETS)) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1378352561:
                                    if (str.equals(KEY_PREDICTION_TIMEOUT)) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1400233242:
                                    if (str.equals(KEY_STRONG_USAGE_HOLD_DURATION)) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1915246556:
                                    if (str.equals(KEY_INITIAL_FOREGROUND_SERVICE_START_HOLD_DURATION)) {
                                        c = 2;
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
                                    com.android.server.usage.AppStandbyController.this.mInjector.mAutoRestrictedBucketDelayMs = java.lang.Math.max(14400000L, properties.getLong(KEY_AUTO_RESTRICTED_BUCKET_DELAY_MS, 3600000L));
                                    break;
                                case 1:
                                    com.android.server.usage.AppStandbyController.this.mLinkCrossProfileApps = properties.getBoolean(KEY_CROSS_PROFILE_APPS_SHARE_STANDBY_BUCKETS, true);
                                    break;
                                case 2:
                                    com.android.server.usage.AppStandbyController.this.mInitialForegroundServiceStartTimeoutMillis = properties.getLong(KEY_INITIAL_FOREGROUND_SERVICE_START_HOLD_DURATION, 1800000L);
                                    break;
                                case 3:
                                    com.android.server.usage.AppStandbyController.this.mNotificationSeenTimeoutMillis = properties.getLong(KEY_NOTIFICATION_SEEN_HOLD_DURATION, 43200000L);
                                    break;
                                case 4:
                                    com.android.server.usage.AppStandbyController.this.mNotificationSeenPromotedBucket = properties.getInt(KEY_NOTIFICATION_SEEN_PROMOTED_BUCKET, 20);
                                    break;
                                case 5:
                                    com.android.server.usage.AppStandbyController.this.mRetainNotificationSeenImpactForPreTApps = properties.getBoolean(KEY_RETAIN_NOTIFICATION_SEEN_IMPACT_FOR_PRE_T_APPS, false);
                                    break;
                                case 6:
                                    com.android.server.usage.AppStandbyController.this.mTriggerQuotaBumpOnNotificationSeen = properties.getBoolean(KEY_TRIGGER_QUOTA_BUMP_ON_NOTIFICATION_SEEN, false);
                                    break;
                                case 7:
                                    com.android.server.usage.AppStandbyController.this.mSlicePinnedTimeoutMillis = properties.getLong(KEY_SLICE_PINNED_HOLD_DURATION, 43200000L);
                                    break;
                                case '\b':
                                    com.android.server.usage.AppStandbyController.this.mStrongUsageTimeoutMillis = properties.getLong(KEY_STRONG_USAGE_HOLD_DURATION, 3600000L);
                                    break;
                                case '\t':
                                    com.android.server.usage.AppStandbyController.this.mPredictionTimeoutMillis = properties.getLong(KEY_PREDICTION_TIMEOUT, 43200000L);
                                    break;
                                case '\n':
                                    com.android.server.usage.AppStandbyController.this.mSystemInteractionTimeoutMillis = properties.getLong(KEY_SYSTEM_INTERACTION_HOLD_DURATION, 600000L);
                                    break;
                                case 11:
                                    com.android.server.usage.AppStandbyController.this.mSystemUpdateUsageTimeoutMillis = properties.getLong(KEY_SYSTEM_UPDATE_HOLD_DURATION, DEFAULT_SYSTEM_UPDATE_TIMEOUT);
                                    break;
                                case '\f':
                                    com.android.server.usage.AppStandbyController.this.mSyncAdapterTimeoutMillis = properties.getLong(KEY_SYNC_ADAPTER_HOLD_DURATION, 600000L);
                                    break;
                                case '\r':
                                    com.android.server.usage.AppStandbyController.this.mExemptedSyncScheduledDozeTimeoutMillis = properties.getLong(KEY_EXEMPTED_SYNC_SCHEDULED_DOZE_HOLD_DURATION, 14400000L);
                                    break;
                                case 14:
                                    com.android.server.usage.AppStandbyController.this.mExemptedSyncScheduledNonDozeTimeoutMillis = properties.getLong(KEY_EXEMPTED_SYNC_SCHEDULED_NON_DOZE_HOLD_DURATION, 600000L);
                                    break;
                                case 15:
                                    com.android.server.usage.AppStandbyController.this.mExemptedSyncStartTimeoutMillis = properties.getLong(KEY_EXEMPTED_SYNC_START_HOLD_DURATION, 600000L);
                                    break;
                                case 16:
                                    com.android.server.usage.AppStandbyController.this.mUnexemptedSyncScheduledTimeoutMillis = properties.getLong(KEY_UNEXEMPTED_SYNC_SCHEDULED_HOLD_DURATION, 600000L);
                                    break;
                                case 17:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseWindowDurationMillis = properties.getLong(KEY_BROADCAST_RESPONSE_WINDOW_DURATION_MS, 120000L);
                                    break;
                                case 18:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseFgThresholdState = properties.getInt(KEY_BROADCAST_RESPONSE_FG_THRESHOLD_STATE, 2);
                                    break;
                                case 19:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastSessionsDurationMs = properties.getLong(KEY_BROADCAST_SESSIONS_DURATION_MS, 120000L);
                                    break;
                                case 20:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastSessionsWithResponseDurationMs = properties.getLong(KEY_BROADCAST_SESSIONS_WITH_RESPONSE_DURATION_MS, 120000L);
                                    break;
                                case 21:
                                    com.android.server.usage.AppStandbyController.this.mNoteResponseEventForAllBroadcastSessions = properties.getBoolean(KEY_NOTE_RESPONSE_EVENT_FOR_ALL_BROADCAST_SESSIONS, true);
                                    break;
                                case 22:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedRoles = properties.getString(KEY_BROADCAST_RESPONSE_EXEMPTED_ROLES, "");
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedRolesList = splitPipeSeparatedString(com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedRoles);
                                    break;
                                case 23:
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedPermissions = properties.getString(KEY_BROADCAST_RESPONSE_EXEMPTED_PERMISSIONS, "");
                                    com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedPermissionsList = splitPipeSeparatedString(com.android.server.usage.AppStandbyController.this.mBroadcastResponseExemptedPermissions);
                                    break;
                                default:
                                    if (!z && (str.startsWith(KEY_PREFIX_SCREEN_TIME_THRESHOLD) || str.startsWith(KEY_PREFIX_ELAPSED_TIME_THRESHOLD))) {
                                        updateTimeThresholds();
                                        z = true;
                                        break;
                                    }
                                    break;
                            }
                            com.android.server.usage.AppStandbyController.this.mAppStandbyProperties.put(str, properties.getString(str, (java.lang.String) null));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private java.util.List<java.lang.String> splitPipeSeparatedString(java.lang.String str) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            this.mStringPipeSplitter.setString(str);
            while (this.mStringPipeSplitter.hasNext()) {
                arrayList.add(this.mStringPipeSplitter.next());
            }
            return arrayList;
        }

        private void updateTimeThresholds() {
            android.provider.DeviceConfig.Properties deviceConfigProperties = com.android.server.usage.AppStandbyController.this.mInjector.getDeviceConfigProperties(this.KEYS_SCREEN_TIME_THRESHOLDS);
            android.provider.DeviceConfig.Properties deviceConfigProperties2 = com.android.server.usage.AppStandbyController.this.mInjector.getDeviceConfigProperties(this.KEYS_ELAPSED_TIME_THRESHOLDS);
            com.android.server.usage.AppStandbyController.this.mAppStandbyScreenThresholds = generateThresholdArray(deviceConfigProperties, this.KEYS_SCREEN_TIME_THRESHOLDS, com.android.server.usage.AppStandbyController.DEFAULT_SCREEN_TIME_THRESHOLDS, com.android.server.usage.AppStandbyController.MINIMUM_SCREEN_TIME_THRESHOLDS);
            com.android.server.usage.AppStandbyController.this.mAppStandbyElapsedThresholds = generateThresholdArray(deviceConfigProperties2, this.KEYS_ELAPSED_TIME_THRESHOLDS, com.android.server.usage.AppStandbyController.DEFAULT_ELAPSED_TIME_THRESHOLDS, com.android.server.usage.AppStandbyController.MINIMUM_ELAPSED_TIME_THRESHOLDS);
            com.android.server.usage.AppStandbyController.this.mCheckIdleIntervalMillis = java.lang.Math.min(com.android.server.usage.AppStandbyController.this.mAppStandbyElapsedThresholds[1] / 4, 14400000L);
        }

        void updateSettings() {
            com.android.server.usage.AppStandbyController.this.setAppIdleEnabled(com.android.server.usage.AppStandbyController.this.mInjector.isAppIdleEnabled());
        }

        long[] generateThresholdArray(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String[] strArr, long[] jArr, long[] jArr2) {
            if (properties.getKeyset().isEmpty()) {
                return jArr;
            }
            if (strArr.length != com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length) {
                throw new java.lang.IllegalStateException("# keys (" + strArr.length + ") != # buckets (" + com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length + ")");
            }
            if (jArr.length != com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length) {
                throw new java.lang.IllegalStateException("# defaults (" + jArr.length + ") != # buckets (" + com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length + ")");
            }
            if (jArr2.length != com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length) {
                android.util.Slog.wtf(com.android.server.usage.AppStandbyController.TAG, "minValues array is the wrong size");
                jArr2 = new long[com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length];
            }
            long[] jArr3 = new long[com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length];
            for (int i = 0; i < com.android.server.usage.AppStandbyController.THRESHOLD_BUCKETS.length; i++) {
                jArr3[i] = java.lang.Math.max(jArr2[i], properties.getLong(strArr[i], jArr[i]));
            }
            return jArr3;
        }
    }
}
