package com.android.server.usage;

/* loaded from: classes2.dex */
public class UsageStatsService extends com.android.server.SystemService implements com.android.server.usage.UserUsageStatsService.StatsUpdatedListener {
    static final boolean COMPRESS_TIME = false;
    static final boolean DEBUG = false;
    private static final boolean ENABLE_KERNEL_UPDATES = true;
    private static final long FLUSH_INTERVAL = 1200000;
    private static final java.lang.String GLOBAL_COMPONENT_USAGE_FILE_NAME = "globalcomponentusage";
    private static final int MAX_TEXT_LENGTH = 127;
    static final int MSG_FLUSH_TO_DISK = 1;
    static final int MSG_HANDLE_LAUNCH_TIME_ON_USER_UNLOCK = 8;
    static final int MSG_NOTIFY_ESTIMATED_LAUNCH_TIMES_CHANGED = 9;
    static final int MSG_NOTIFY_USAGE_EVENT_LISTENER = 12;
    static final int MSG_ON_START = 7;
    static final int MSG_PACKAGE_REMOVED = 6;
    static final int MSG_REMOVE_USER = 2;
    static final int MSG_REPORT_EVENT = 0;
    static final int MSG_REPORT_EVENT_TO_ALL_USERID = 4;
    static final int MSG_UID_REMOVED = 10;
    static final int MSG_UID_STATE_CHANGED = 3;
    static final int MSG_UNLOCKED_USER = 5;
    static final int MSG_USER_STARTED = 11;
    private static final long ONE_DAY = 86400000;
    private static final long ONE_WEEK = 604800000;
    private static final long TEN_SECONDS = 10000;
    static final long TIME_CHANGE_THRESHOLD_MILLIS = 2000;
    private static final char TOKEN_DELIMITER = '/';
    private static final long TWENTY_MINUTES = 1200000;
    private static final long UNKNOWN_LAUNCH_TIME_DELAY_MS = 31536000000L;
    android.app.AppOpsManager mAppOps;
    com.android.server.usage.AppStandbyInternal mAppStandby;
    com.android.server.usage.AppTimeLimitController mAppTimeLimit;
    android.app.admin.DevicePolicyManagerInternal mDpmInternal;
    private final java.util.concurrent.CopyOnWriteArraySet<android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener> mEstimatedLaunchTimeChangedListeners;
    private android.os.Handler mHandler;
    private final com.android.server.usage.UsageStatsService.Injector mInjector;
    private android.os.Handler mIoHandler;
    private final android.os.Handler.Callback mIoHandlerCallback;
    private final java.util.Map<java.lang.String, java.lang.Long> mLastTimeComponentUsedGlobal;

    @com.android.internal.annotations.GuardedBy({"mLaunchTimeAlarmQueues"})
    private final android.util.SparseArray<com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue> mLaunchTimeAlarmQueues;
    private final java.lang.Object mLock;
    android.content.pm.PackageManager mPackageManager;
    android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.internal.content.PackageMonitor mPackageMonitor;

    @com.android.internal.annotations.GuardedBy({"mPendingLaunchTimeChangePackages"})
    private final android.util.SparseSetArray<java.lang.String> mPendingLaunchTimeChangePackages;
    private long mRealTimeSnapshot;
    private final android.util.SparseArray<java.util.LinkedList<android.app.usage.UsageEvents.Event>> mReportedEvents;
    private com.android.server.usage.BroadcastResponseStatsTracker mResponseStatsTracker;
    android.content.pm.ShortcutServiceInternal mShortcutServiceInternal;
    private com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener mStandbyChangeListener;
    private long mSystemTimeSnapshot;
    private final android.app.IUidObserver mUidObserver;
    private final android.util.SparseIntArray mUidToKernelCounter;

    @com.android.internal.annotations.GuardedBy({"mUsageEventListeners"})
    private final android.util.ArraySet<android.app.usage.UsageStatsManagerInternal.UsageEventListener> mUsageEventListeners;
    final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mUsageReporters;
    int mUsageSource;
    android.os.UserManager mUserManager;
    private final android.util.SparseArray<com.android.server.usage.UserUsageStatsService> mUserState;
    private final java.util.concurrent.CopyOnWriteArraySet<java.lang.Integer> mUserUnlockedStates;
    final android.util.SparseArray<com.android.server.usage.UsageStatsService.ActivityData> mVisibleActivities;
    public static final boolean ENABLE_TIME_CHANGE_CORRECTION = android.os.SystemProperties.getBoolean("persist.debug.time_correction", true);
    static final java.lang.String TAG = "UsageStatsService";
    static final boolean DEBUG_RESPONSE_STATS = android.util.Log.isLoggable(TAG, 3);
    private static final java.io.File KERNEL_COUNTER_FILE = new java.io.File("/proc/uid_procstat/set");
    private static final java.io.File COMMON_USAGE_STATS_DIR = new java.io.File(android.os.Environment.getDataSystemDirectory(), "usagestats");
    private static final java.io.File LEGACY_USER_USAGE_STATS_DIR = COMMON_USAGE_STATS_DIR;
    private static final java.io.File LEGACY_COMMON_USAGE_STATS_DIR = new java.io.File(android.os.Environment.getDataSystemDeDirectory(), "usagestats");

    private static class ActivityData {
        public int lastEvent;
        private final java.lang.String mTaskRootClass;
        private final java.lang.String mTaskRootPackage;
        private final java.lang.String mUsageSourcePackage;

        private ActivityData(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.lastEvent = 0;
            this.mTaskRootPackage = str;
            this.mTaskRootClass = str2;
            this.mUsageSourcePackage = str3;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.usage.AppStandbyInternal getAppStandbyController(android.content.Context context) {
            return com.android.server.usage.AppStandbyInternal.newAppStandbyController(com.android.server.usage.UsageStatsService.class.getClassLoader(), context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(android.os.Message message) {
        switch (message.what) {
            case 3:
                int i = message.arg1;
                int i2 = message.arg2 <= 2 ? 0 : 1;
                synchronized (this.mUidToKernelCounter) {
                    if (i2 != this.mUidToKernelCounter.get(i, 0)) {
                        this.mUidToKernelCounter.put(i, i2);
                        try {
                            android.os.FileUtils.stringToFile(KERNEL_COUNTER_FILE, i + " " + i2);
                        } catch (java.io.IOException e) {
                            android.util.Slog.w(TAG, "Failed to update counter set: " + e);
                        }
                    }
                }
                return true;
            case 8:
                int i3 = message.arg1;
                android.os.Trace.traceBegin(524288L, "usageStatsHandleEstimatedLaunchTimesOnUser(" + i3 + ")");
                handleEstimatedLaunchTimesOnUserUnlock(i3);
                android.os.Trace.traceEnd(524288L);
                return true;
            case 12:
                int i4 = message.arg1;
                android.app.usage.UsageEvents.Event event = (android.app.usage.UsageEvents.Event) message.obj;
                synchronized (this.mUsageEventListeners) {
                    try {
                        int size = this.mUsageEventListeners.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            this.mUsageEventListeners.valueAt(i5).onUsageEvent(i4, event);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    public UsageStatsService(android.content.Context context) {
        this(context, new com.android.server.usage.UsageStatsService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    UsageStatsService(android.content.Context context, com.android.server.usage.UsageStatsService.Injector injector) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mUserState = new android.util.SparseArray<>();
        this.mUserUnlockedStates = new java.util.concurrent.CopyOnWriteArraySet<>();
        this.mUidToKernelCounter = new android.util.SparseIntArray();
        this.mLastTimeComponentUsedGlobal = new android.util.ArrayMap();
        this.mPackageMonitor = new com.android.server.usage.UsageStatsService.MyPackageMonitor();
        this.mReportedEvents = new android.util.SparseArray<>();
        this.mUsageReporters = new android.util.SparseArray<>();
        this.mVisibleActivities = new android.util.SparseArray<>();
        this.mLaunchTimeAlarmQueues = new android.util.SparseArray<>();
        this.mUsageEventListeners = new android.util.ArraySet<>();
        this.mEstimatedLaunchTimeChangedListeners = new java.util.concurrent.CopyOnWriteArraySet<>();
        this.mPendingLaunchTimeChangePackages = new android.util.SparseSetArray<>();
        this.mStandbyChangeListener = new com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener() { // from class: com.android.server.usage.UsageStatsService.1
            public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
                android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(11, android.os.SystemClock.elapsedRealtime());
                event.mBucketAndReason = (i2 << 16) | (i3 & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
                event.mPackage = str;
                com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
            }
        };
        this.mIoHandlerCallback = new android.os.Handler.Callback() { // from class: com.android.server.usage.UsageStatsService$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.usage.UsageStatsService.this.lambda$new$0(message);
                return lambda$new$0;
            }
        };
        this.mUidObserver = new android.app.UidObserver() { // from class: com.android.server.usage.UsageStatsService.3
            public void onUidStateChanged(int i, int i2, long j, int i3) {
                com.android.server.usage.UsageStatsService.this.mIoHandler.obtainMessage(3, i, i2).sendToTarget();
            }

            public void onUidGone(int i, boolean z) {
                onUidStateChanged(i, 20, 0L, 0);
            }
        };
        this.mInjector = injector;
    }

    @Override // com.android.server.SystemService
    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void onStart() {
        this.mAppOps = (android.app.AppOpsManager) getContext().getSystemService("appops");
        this.mUserManager = (android.os.UserManager) getContext().getSystemService("user");
        this.mPackageManager = getContext().getPackageManager();
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mHandler = getUsageEventProcessingHandler();
        this.mIoHandler = new android.os.Handler(com.android.server.IoThread.get().getLooper(), this.mIoHandlerCallback);
        this.mAppStandby = this.mInjector.getAppStandbyController(getContext());
        this.mResponseStatsTracker = new com.android.server.usage.BroadcastResponseStatsTracker(this.mAppStandby, getContext());
        this.mAppTimeLimit = new com.android.server.usage.AppTimeLimitController(getContext(), new com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener() { // from class: com.android.server.usage.UsageStatsService.2
            @Override // com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener
            public void onLimitReached(int i, int i2, long j, long j2, android.app.PendingIntent pendingIntent) {
                if (pendingIntent == null) {
                    return;
                }
                android.content.Intent intent = new android.content.Intent();
                intent.putExtra("android.app.usage.extra.OBSERVER_ID", i);
                intent.putExtra("android.app.usage.extra.TIME_LIMIT", j);
                intent.putExtra("android.app.usage.extra.TIME_USED", j2);
                try {
                    pendingIntent.send(com.android.server.usage.UsageStatsService.this.getContext(), 0, intent);
                } catch (android.app.PendingIntent.CanceledException e) {
                    android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Couldn't deliver callback: " + pendingIntent);
                }
            }

            @Override // com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener
            public void onSessionEnd(int i, int i2, long j, android.app.PendingIntent pendingIntent) {
                if (pendingIntent == null) {
                    return;
                }
                android.content.Intent intent = new android.content.Intent();
                intent.putExtra("android.app.usage.extra.OBSERVER_ID", i);
                intent.putExtra("android.app.usage.extra.TIME_USED", j);
                try {
                    pendingIntent.send(com.android.server.usage.UsageStatsService.this.getContext(), 0, intent);
                } catch (android.app.PendingIntent.CanceledException e) {
                    android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Couldn't deliver callback: " + pendingIntent);
                }
            }
        }, this.mHandler.getLooper());
        this.mAppStandby.addListener(this.mStandbyChangeListener);
        byte b = 0;
        byte b2 = 0;
        this.mPackageMonitor.register(getContext(), (android.os.Looper) null, android.os.UserHandle.ALL, true);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        getContext().registerReceiverAsUser(new com.android.server.usage.UsageStatsService.UserActionsReceiver(), android.os.UserHandle.ALL, intentFilter, null, android.app.usage.Flags.useDedicatedHandlerThread() ? this.mHandler : null);
        getContext().registerReceiverAsUser(new com.android.server.usage.UsageStatsService.UidRemovedReceiver(), android.os.UserHandle.ALL, new android.content.IntentFilter("android.intent.action.UID_REMOVED"), null, android.app.usage.Flags.useDedicatedHandlerThread() ? this.mHandler : null);
        this.mRealTimeSnapshot = android.os.SystemClock.elapsedRealtime();
        this.mSystemTimeSnapshot = java.lang.System.currentTimeMillis();
        publishLocalService(android.app.usage.UsageStatsManagerInternal.class, new com.android.server.usage.UsageStatsService.LocalService());
        publishLocalService(com.android.server.usage.AppStandbyInternal.class, this.mAppStandby);
        publishBinderServices();
        this.mHandler.obtainMessage(7).sendToTarget();
    }

    @com.android.internal.annotations.VisibleForTesting
    void publishBinderServices() {
        publishBinderService("usagestats", new com.android.server.usage.UsageStatsService.BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        this.mAppStandby.onBootPhase(i);
        if (i == 500) {
            getDpmInternal();
            getShortcutServiceInternal();
            this.mResponseStatsTracker.onSystemServicesReady(getContext());
            if (KERNEL_COUNTER_FILE.exists()) {
                try {
                    android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (java.lang.String) null);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } else {
                android.util.Slog.w(TAG, "Missing procfs interface: " + KERNEL_COUNTER_FILE);
            }
            readUsageSourceSetting();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mUserState.put(targetUser.getUserIdentifier(), null);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(5, targetUser.getUserIdentifier(), 0).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(userIdentifier))) {
                    persistPendingEventsLocked(userIdentifier);
                    return;
                }
                android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(29, android.os.SystemClock.elapsedRealtime());
                event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
                reportEvent(event, userIdentifier);
                com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(userIdentifier);
                if (userUsageStatsService != null) {
                    userUsageStatsService.userStopped();
                }
                this.mUserUnlockedStates.remove(java.lang.Integer.valueOf(userIdentifier));
                this.mUserState.put(userIdentifier, null);
                synchronized (this.mLaunchTimeAlarmQueues) {
                    try {
                        com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue launchTimeAlarmQueue = this.mLaunchTimeAlarmQueues.get(userIdentifier);
                        if (launchTimeAlarmQueue != null) {
                            launchTimeAlarmQueue.removeAllAlarms();
                            this.mLaunchTimeAlarmQueues.remove(userIdentifier);
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    private android.os.Handler getUsageEventProcessingHandler() {
        if (android.app.usage.Flags.useDedicatedHandlerThread()) {
            return new com.android.server.usage.UsageStatsService.H(com.android.server.usage.UsageStatsHandlerThread.get().getLooper());
        }
        return new com.android.server.usage.UsageStatsService.H(com.android.internal.os.BackgroundThread.get().getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserUnlocked(int i) {
        java.util.HashMap<java.lang.String, java.lang.Long> installedPackages = getInstalledPackages(i);
        com.android.server.usage.UsageStatsIdleService.scheduleUpdateMappingsJob(getContext(), i);
        boolean shouldDeleteObsoleteData = shouldDeleteObsoleteData(android.os.UserHandle.of(i));
        synchronized (this.mLock) {
            try {
                this.mUserUnlockedStates.add(java.lang.Integer.valueOf(i));
                android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(28, android.os.SystemClock.elapsedRealtime());
                event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
                migrateStatsToSystemCeIfNeededLocked(i);
                java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList = new java.util.LinkedList<>();
                android.os.Trace.traceBegin(524288L, "loadPendingEvents");
                loadPendingEventsLocked(i, linkedList);
                android.os.Trace.traceEnd(524288L);
                synchronized (this.mReportedEvents) {
                    try {
                        java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList2 = this.mReportedEvents.get(i);
                        if (linkedList2 != null) {
                            linkedList.addAll(linkedList2);
                            this.mReportedEvents.remove(i);
                        }
                    } finally {
                    }
                }
                boolean z = !linkedList.isEmpty();
                initializeUserUsageStatsServiceLocked(i, java.lang.System.currentTimeMillis(), installedPackages, shouldDeleteObsoleteData);
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    android.util.Slog.i(TAG, "Attempted to unlock stopped or removed user " + i);
                    return;
                }
                while (linkedList.peek() != null) {
                    reportEvent(linkedList.poll(), i);
                }
                reportEvent(event, i);
                deleteRecursively(new java.io.File(android.os.Environment.getDataSystemDeDirectory(i), "usagestats"));
                if (z) {
                    userUsageStatsServiceLocked.persistActiveStats();
                }
                this.mIoHandler.obtainMessage(8, i, 0).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private java.util.HashMap<java.lang.String, java.lang.Long> getInstalledPackages(int i) {
        if (this.mPackageManager == null) {
            return null;
        }
        java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(8192, i);
        java.util.HashMap<java.lang.String, java.lang.Long> hashMap = new java.util.HashMap<>();
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) installedPackagesAsUser.get(size);
            hashMap.put(packageInfo.packageName, java.lang.Long.valueOf(packageInfo.firstInstallTime));
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.admin.DevicePolicyManagerInternal getDpmInternal() {
        if (this.mDpmInternal == null) {
            this.mDpmInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        }
        return this.mDpmInternal;
    }

    private android.content.pm.ShortcutServiceInternal getShortcutServiceInternal() {
        if (this.mShortcutServiceInternal == null) {
            this.mShortcutServiceInternal = (android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class);
        }
        return this.mShortcutServiceInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readUsageSourceSetting() {
        synchronized (this.mLock) {
            this.mUsageSource = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "app_time_limit_usage_source", 1);
        }
    }

    private class LaunchTimeAlarmQueue extends com.android.server.utils.AlarmQueue<java.lang.String> {
        private final int mUserId;

        LaunchTimeAlarmQueue(int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper) {
            super(context, looper, "*usage.launchTime*", "Estimated launch times", true, 30000L);
            this.mUserId = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull java.lang.String str, int i) {
            return this.mUserId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
            if (arraySet.size() > 0) {
                synchronized (com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                    com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages.addAll(this.mUserId, arraySet);
                }
                com.android.server.usage.UsageStatsService.this.mHandler.sendEmptyMessage(9);
            }
        }
    }

    private class UserActionsReceiver extends android.content.BroadcastReceiver {
        private UserActionsReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            java.lang.String action = intent.getAction();
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                if (intExtra >= 0) {
                    com.android.server.usage.UsageStatsService.this.mHandler.obtainMessage(2, intExtra, 0).sendToTarget();
                }
            } else if ("android.intent.action.USER_STARTED".equals(action) && intExtra >= 0) {
                com.android.server.usage.UsageStatsService.this.mHandler.obtainMessage(11, intExtra, 0).sendToTarget();
            }
        }
    }

    private class UidRemovedReceiver extends android.content.BroadcastReceiver {
        private UidRemovedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra == -1) {
                return;
            }
            com.android.server.usage.UsageStatsService.this.mHandler.obtainMessage(10, intExtra, 0).sendToTarget();
        }
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onStatsUpdated() {
        this.mHandler.sendEmptyMessageDelayed(1, 1200000L);
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onStatsReloaded() {
        this.mAppStandby.postOneTimeCheckIdleStates();
    }

    @Override // com.android.server.usage.UserUsageStatsService.StatsUpdatedListener
    public void onNewUpdate(int i) {
        this.mAppStandby.initializeDefaultsForSystemApps(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sameApp(int i, int i2, java.lang.String str) {
        return this.mPackageManagerInternal.getPackageUid(str, 0L, i2) == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInstantApp(java.lang.String str, int i) {
        return this.mPackageManagerInternal.isPackageEphemeral(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldObfuscateInstantAppsForCaller(int i, int i2) {
        return !this.mPackageManagerInternal.canAccessInstantApps(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldHideShortcutInvocationEvents(int i, java.lang.String str, int i2, int i3) {
        if (getShortcutServiceInternal() != null) {
            return !r0.hasShortcutHostPermission(i, str, i2, i3);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldHideLocusIdEvents(int i, int i2) {
        return (i2 == 1000 || getContext().checkPermission("android.permission.ACCESS_LOCUS_ID_USAGE_STATS", i, i2) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldObfuscateNotificationEvents(int i, int i2) {
        return (i2 == 1000 || getContext().checkPermission("android.permission.MANAGE_NOTIFICATIONS", i, i2) == 0) ? false : true;
    }

    private static void deleteRecursively(java.io.File file) {
        java.io.File[] listFiles;
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (java.io.File file2 : listFiles) {
                deleteRecursively(file2);
            }
        }
        if (file.exists() && !file.delete()) {
            android.util.Slog.e(TAG, "Failed to delete " + file);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.usage.UserUsageStatsService getUserUsageStatsServiceLocked(int i) {
        com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(i);
        if (userUsageStatsService == null) {
            android.util.Slog.wtf(TAG, "Failed to fetch usage stats service for user " + i + ". The user might not have been initialized yet.");
        }
        return userUsageStatsService;
    }

    private void initializeUserUsageStatsServiceLocked(int i, long j, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, boolean z) {
        com.android.server.usage.UserUsageStatsService userUsageStatsService = new com.android.server.usage.UserUsageStatsService(getContext(), i, new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), "usagestats"), this);
        try {
            userUsageStatsService.init(j, hashMap, z);
            this.mUserState.put(i, userUsageStatsService);
        } catch (java.lang.Exception e) {
            if (this.mUserManager.isUserUnlocked(i)) {
                android.util.Slog.w(TAG, "Failed to initialized unlocked user " + i);
                throw e;
            }
            android.util.Slog.w(TAG, "Attempted to initialize service for stopped or removed user " + i);
        }
    }

    private void migrateStatsToSystemCeIfNeededLocked(int i) {
        java.io.File file = new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), "usagestats");
        if (!file.mkdirs() && !file.exists()) {
            throw new java.lang.IllegalStateException("Usage stats directory does not exist: " + file.getAbsolutePath());
        }
        java.io.File file2 = new java.io.File(file, "migrated");
        if (file2.exists()) {
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file2));
                try {
                    if (java.lang.Integer.parseInt(bufferedReader.readLine()) >= 4) {
                        deleteLegacyUserDir(i);
                        bufferedReader.close();
                        return;
                    }
                    bufferedReader.close();
                } catch (java.lang.Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException | java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Failed to read migration status file, possibly corrupted.");
                deleteRecursively(file);
                if (file.exists()) {
                    android.util.Slog.e(TAG, "Unable to delete usage stats CE directory.");
                    throw new java.lang.RuntimeException(e);
                }
                if (!file.mkdirs() && !file.exists()) {
                    throw new java.lang.IllegalStateException("Usage stats directory does not exist: " + file.getAbsolutePath());
                }
            }
        }
        android.util.Slog.i(TAG, "Starting migration to system CE for user " + i);
        java.io.File file3 = new java.io.File(LEGACY_USER_USAGE_STATS_DIR, java.lang.Integer.toString(i));
        if (file3.exists()) {
            copyRecursively(file, file3);
        }
        try {
            java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(file2));
            try {
                bufferedWriter.write(java.lang.Integer.toString(4));
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                android.util.Slog.i(TAG, "Finished migration to system CE for user " + i);
                deleteLegacyUserDir(i);
            } finally {
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to write migrated status file");
            throw new java.lang.RuntimeException(e2);
        }
    }

    private static void copyRecursively(java.io.File file, java.io.File file2) {
        java.io.File file3;
        java.io.File[] listFiles = file2.listFiles();
        if (listFiles == null) {
            try {
                java.nio.file.Files.copy(file2.toPath(), new java.io.File(file, file2.getName()).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                return;
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to move usage stats file : " + file2.toString());
                throw new java.lang.RuntimeException(e);
            }
        }
        for (int length = listFiles.length - 1; length >= 0; length--) {
            if (!listFiles[length].isDirectory()) {
                file3 = file;
            } else {
                file3 = new java.io.File(file, listFiles[length].getName());
                if (!file3.mkdirs() && !file3.exists()) {
                    throw new java.lang.IllegalStateException("Failed to create usage stats directory during migration: " + file3.getAbsolutePath());
                }
            }
            copyRecursively(file3, listFiles[length]);
        }
    }

    private void deleteLegacyUserDir(int i) {
        java.io.File file = new java.io.File(LEGACY_USER_USAGE_STATS_DIR, java.lang.Integer.toString(i));
        if (file.exists()) {
            deleteRecursively(file);
            if (file.exists()) {
                android.util.Slog.w(TAG, "Error occurred while attempting to delete legacy usage stats dir for user " + i);
            }
        }
    }

    void shutdown() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(26, android.os.SystemClock.elapsedRealtime());
            event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
            reportEventToAllUserId(event);
            flushToDiskLocked();
            persistGlobalComponentUsageLocked();
        }
        this.mAppStandby.flushToDisk();
    }

    void prepareForPossibleShutdown() {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(26, android.os.SystemClock.elapsedRealtime());
        event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
        this.mHandler.obtainMessage(4, event).sendToTarget();
        this.mHandler.sendEmptyMessage(1);
    }

    private void loadPendingEventsLocked(int i, java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList) {
        java.io.File[] listFiles = new java.io.File(android.os.Environment.getDataSystemDeDirectory(i), "usagestats").listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        java.util.Arrays.sort(listFiles);
        int length = listFiles.length;
        for (int i2 = 0; i2 < length; i2++) {
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(listFiles[i2]);
            java.util.LinkedList linkedList2 = new java.util.LinkedList();
            try {
                java.io.FileInputStream openRead = atomicFile.openRead();
                try {
                    com.android.server.usage.UsageStatsProtoV2.readPendingEvents(openRead, linkedList2);
                    if (openRead != null) {
                        openRead.close();
                    }
                    linkedList.addAll(linkedList2);
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Could not read " + listFiles[i2] + " for user " + i);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock", "mReportedEvents"})
    private void persistPendingEventsLocked(int i) {
        java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList = this.mReportedEvents.get(i);
        if (linkedList == null || linkedList.isEmpty()) {
            return;
        }
        java.io.File dataSystemDeDirectory = android.os.Environment.getDataSystemDeDirectory(i);
        java.io.File file = new java.io.File(dataSystemDeDirectory, "usagestats");
        if (!file.mkdir() && !file.exists()) {
            if (dataSystemDeDirectory.exists()) {
                android.util.Slog.e(TAG, "Failed to create " + file);
                return;
            }
            android.util.Slog.w(TAG, "User " + i + " was already removed! Discarding pending events");
            linkedList.clear();
            return;
        }
        java.io.File file2 = new java.io.File(file, "pendingevents_" + java.lang.System.currentTimeMillis());
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(file2);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                java.io.FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    com.android.server.usage.UsageStatsProtoV2.writePendingEvents(startWrite, linkedList);
                    atomicFile.finishWrite(startWrite);
                    linkedList.clear();
                } catch (java.lang.Exception e) {
                    fileOutputStream = startWrite;
                    android.util.Slog.e(TAG, "Failed to write " + file2.getAbsolutePath() + " for user " + i);
                    atomicFile.failWrite(fileOutputStream);
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.lang.Exception e2) {
        }
        atomicFile.failWrite(fileOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadGlobalComponentUsageLocked() {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(COMMON_USAGE_STATS_DIR, GLOBAL_COMPONENT_USAGE_FILE_NAME));
        if (!atomicFile.exists()) {
            atomicFile = new android.util.AtomicFile(new java.io.File(LEGACY_COMMON_USAGE_STATS_DIR, GLOBAL_COMPONENT_USAGE_FILE_NAME));
            if (!atomicFile.exists()) {
                return;
            } else {
                android.util.Slog.i(TAG, "Reading globalcomponentusage file from old location");
            }
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            try {
                com.android.server.usage.UsageStatsProtoV2.readGlobalComponentUsage(openRead, arrayMap);
                if (openRead != null) {
                    openRead.close();
                }
                java.util.Map.Entry[] entryArr = (java.util.Map.Entry[]) arrayMap.entrySet().toArray();
                int length = entryArr.length;
                for (int i = 0; i < length; i++) {
                    this.mLastTimeComponentUsedGlobal.putIfAbsent((java.lang.String) entryArr[i].getKey(), (java.lang.Long) entryArr[i].getValue());
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Could not read " + atomicFile.getBaseFile());
        }
    }

    private void persistGlobalComponentUsageLocked() {
        if (this.mLastTimeComponentUsedGlobal.isEmpty()) {
            return;
        }
        if (!COMMON_USAGE_STATS_DIR.mkdirs() && !COMMON_USAGE_STATS_DIR.exists()) {
            throw new java.lang.IllegalStateException("Common usage stats directory does not exist: " + COMMON_USAGE_STATS_DIR.getAbsolutePath());
        }
        java.io.File file = new java.io.File(COMMON_USAGE_STATS_DIR, GLOBAL_COMPONENT_USAGE_FILE_NAME);
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(file);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                java.io.FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    com.android.server.usage.UsageStatsProtoV2.writeGlobalComponentUsage(startWrite, this.mLastTimeComponentUsedGlobal);
                    atomicFile.finishWrite(startWrite);
                } catch (java.lang.Exception e) {
                    fileOutputStream = startWrite;
                    android.util.Slog.e(TAG, "Failed to write " + file.getAbsolutePath());
                    atomicFile.failWrite(fileOutputStream);
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.lang.Exception e2) {
        }
        atomicFile.failWrite(fileOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportEventOrAddToQueue(int i, android.app.usage.UsageEvents.Event event) {
        if (this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
            this.mHandler.obtainMessage(0, i, 0, event).sendToTarget();
            return;
        }
        if (android.os.Trace.isTagEnabled(524288L)) {
            android.os.Trace.traceBegin(524288L, "usageStatsQueueEvent(" + i + ") #" + com.android.server.usage.UserUsageStatsService.eventToString(event.mEventType));
        }
        synchronized (this.mReportedEvents) {
            try {
                java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList = this.mReportedEvents.get(i);
                if (linkedList == null) {
                    linkedList = new java.util.LinkedList<>();
                    this.mReportedEvents.put(i, linkedList);
                }
                linkedList.add(event);
                if (linkedList.size() == 1) {
                    this.mHandler.sendEmptyMessageDelayed(1, 1200000L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.Trace.traceEnd(524288L);
    }

    private void convertToSystemTimeLocked(android.app.usage.UsageEvents.Event event) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (ENABLE_TIME_CHANGE_CORRECTION) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = currentTimeMillis - ((elapsedRealtime - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot);
            if (java.lang.Math.abs(j) > TIME_CHANGE_THRESHOLD_MILLIS) {
                android.util.Slog.i(TAG, "Time changed in by " + (j / 1000) + " seconds");
                this.mRealTimeSnapshot = elapsedRealtime;
                this.mSystemTimeSnapshot = currentTimeMillis;
            }
        }
        event.mTimeStamp = java.lang.Math.max(0L, event.mTimeStamp - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0221 A[Catch: all -> 0x0079, DONT_GENERATE, TryCatch #3 {all -> 0x0079, blocks: (B:12:0x0028, B:14:0x0034, B:15:0x0077, B:18:0x007c, B:19:0x0080, B:21:0x021b, B:23:0x0221, B:25:0x0223, B:26:0x0226, B:29:0x0085, B:30:0x0089, B:32:0x0095, B:33:0x00d0, B:35:0x00d2, B:37:0x00d6, B:38:0x00db, B:39:0x00dd, B:44:0x00eb, B:63:0x0125, B:64:0x0126, B:66:0x012a, B:68:0x0136, B:71:0x0143, B:75:0x014e, B:76:0x014f, B:77:0x0158, B:78:0x015e, B:79:0x0170, B:81:0x017c, B:83:0x0180, B:84:0x018f, B:85:0x01a5, B:87:0x01ab, B:90:0x0188, B:91:0x01a0, B:92:0x01b8, B:95:0x01c8, B:97:0x01cc, B:98:0x01db, B:100:0x01fd, B:102:0x0205, B:104:0x0214, B:107:0x01d4, B:46:0x00ec, B:48:0x00f3, B:50:0x00f9, B:53:0x011f, B:55:0x0109, B:58:0x0122, B:41:0x00de, B:42:0x00e8), top: B:11:0x0028, inners: #0, #1, #2, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0223 A[Catch: all -> 0x0079, TryCatch #3 {all -> 0x0079, blocks: (B:12:0x0028, B:14:0x0034, B:15:0x0077, B:18:0x007c, B:19:0x0080, B:21:0x021b, B:23:0x0221, B:25:0x0223, B:26:0x0226, B:29:0x0085, B:30:0x0089, B:32:0x0095, B:33:0x00d0, B:35:0x00d2, B:37:0x00d6, B:38:0x00db, B:39:0x00dd, B:44:0x00eb, B:63:0x0125, B:64:0x0126, B:66:0x012a, B:68:0x0136, B:71:0x0143, B:75:0x014e, B:76:0x014f, B:77:0x0158, B:78:0x015e, B:79:0x0170, B:81:0x017c, B:83:0x0180, B:84:0x018f, B:85:0x01a5, B:87:0x01ab, B:90:0x0188, B:91:0x01a0, B:92:0x01b8, B:95:0x01c8, B:97:0x01cc, B:98:0x01db, B:100:0x01fd, B:102:0x0205, B:104:0x0214, B:107:0x01d4, B:46:0x00ec, B:48:0x00f3, B:50:0x00f9, B:53:0x011f, B:55:0x0109, B:58:0x0122, B:41:0x00de, B:42:0x00e8), top: B:11:0x0028, inners: #0, #1, #2, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0095 A[Catch: all -> 0x0079, TryCatch #3 {all -> 0x0079, blocks: (B:12:0x0028, B:14:0x0034, B:15:0x0077, B:18:0x007c, B:19:0x0080, B:21:0x021b, B:23:0x0221, B:25:0x0223, B:26:0x0226, B:29:0x0085, B:30:0x0089, B:32:0x0095, B:33:0x00d0, B:35:0x00d2, B:37:0x00d6, B:38:0x00db, B:39:0x00dd, B:44:0x00eb, B:63:0x0125, B:64:0x0126, B:66:0x012a, B:68:0x0136, B:71:0x0143, B:75:0x014e, B:76:0x014f, B:77:0x0158, B:78:0x015e, B:79:0x0170, B:81:0x017c, B:83:0x0180, B:84:0x018f, B:85:0x01a5, B:87:0x01ab, B:90:0x0188, B:91:0x01a0, B:92:0x01b8, B:95:0x01c8, B:97:0x01cc, B:98:0x01db, B:100:0x01fd, B:102:0x0205, B:104:0x0214, B:107:0x01d4, B:46:0x00ec, B:48:0x00f3, B:50:0x00f9, B:53:0x011f, B:55:0x0109, B:58:0x0122, B:41:0x00de, B:42:0x00e8), top: B:11:0x0028, inners: #0, #1, #2, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d2 A[Catch: all -> 0x0079, TryCatch #3 {all -> 0x0079, blocks: (B:12:0x0028, B:14:0x0034, B:15:0x0077, B:18:0x007c, B:19:0x0080, B:21:0x021b, B:23:0x0221, B:25:0x0223, B:26:0x0226, B:29:0x0085, B:30:0x0089, B:32:0x0095, B:33:0x00d0, B:35:0x00d2, B:37:0x00d6, B:38:0x00db, B:39:0x00dd, B:44:0x00eb, B:63:0x0125, B:64:0x0126, B:66:0x012a, B:68:0x0136, B:71:0x0143, B:75:0x014e, B:76:0x014f, B:77:0x0158, B:78:0x015e, B:79:0x0170, B:81:0x017c, B:83:0x0180, B:84:0x018f, B:85:0x01a5, B:87:0x01ab, B:90:0x0188, B:91:0x01a0, B:92:0x01b8, B:95:0x01c8, B:97:0x01cc, B:98:0x01db, B:100:0x01fd, B:102:0x0205, B:104:0x0214, B:107:0x01d4, B:46:0x00ec, B:48:0x00f3, B:50:0x00f9, B:53:0x011f, B:55:0x0109, B:58:0x0122, B:41:0x00de, B:42:0x00e8), top: B:11:0x0028, inners: #0, #1, #2, #4, #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void reportEvent(android.app.usage.UsageEvents.Event event, int i) {
        int packageUid;
        com.android.server.usage.UsageStatsService.ActivityData activityData;
        android.util.ArraySet arraySet;
        com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked;
        switch (event.mEventType) {
            case 1:
            case 2:
            case 23:
                packageUid = this.mPackageManagerInternal.getPackageUid(event.mPackage, 0L, i);
                break;
            default:
                packageUid = 0;
                break;
        }
        if (event.mPackage != null && isInstantApp(event.mPackage, i)) {
            event.mFlags |= 1;
        }
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.wtf(TAG, "Failed to report event for locked user " + i + " (" + event.mPackage + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + event.mClass + " eventType:" + event.mEventType + " instanceId:" + event.mInstanceId + ")");
                    return;
                }
                switch (event.mEventType) {
                    case 1:
                        logAppUsageEventReportedAtomLocked(1, packageUid, event.mPackage);
                        if (this.mVisibleActivities.get(event.mInstanceId) == null) {
                            java.lang.String usageSourcePackage = getUsageSourcePackage(event);
                            try {
                                this.mAppTimeLimit.noteUsageStart(usageSourcePackage, i);
                            } catch (java.lang.IllegalArgumentException e) {
                                android.util.Slog.e(TAG, "Failed to note usage start", e);
                            }
                            com.android.server.usage.UsageStatsService.ActivityData activityData2 = new com.android.server.usage.UsageStatsService.ActivityData(event.mTaskRootPackage, event.mTaskRootClass, usageSourcePackage);
                            activityData2.lastEvent = 1;
                            this.mVisibleActivities.put(event.mInstanceId, activityData2);
                            long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(event.mPackage, i);
                            long currentTimeMillis = java.lang.System.currentTimeMillis();
                            if (estimatedLaunchTime < currentTimeMillis || estimatedLaunchTime > currentTimeMillis + 604800000) {
                                this.mAppStandby.setEstimatedLaunchTime(event.mPackage, i, 0L);
                                if (stageChangedEstimatedLaunchTime(i, event.mPackage)) {
                                    this.mHandler.sendEmptyMessage(9);
                                }
                            }
                        }
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                            return;
                        }
                        userUsageStatsServiceLocked.reportEvent(event);
                        this.mIoHandler.obtainMessage(12, i, 0, event).sendToTarget();
                        return;
                    case 2:
                        com.android.server.usage.UsageStatsService.ActivityData activityData3 = this.mVisibleActivities.get(event.mInstanceId);
                        if (activityData3 == null) {
                            java.lang.String usageSourcePackage2 = getUsageSourcePackage(event);
                            try {
                                this.mAppTimeLimit.noteUsageStart(usageSourcePackage2, i);
                            } catch (java.lang.IllegalArgumentException e2) {
                                android.util.Slog.e(TAG, "Failed to note usage start", e2);
                            }
                            activityData3 = new com.android.server.usage.UsageStatsService.ActivityData(event.mTaskRootPackage, event.mTaskRootClass, usageSourcePackage2);
                            this.mVisibleActivities.put(event.mInstanceId, activityData3);
                        } else {
                            logAppUsageEventReportedAtomLocked(2, packageUid, event.mPackage);
                        }
                        activityData3.lastEvent = 2;
                        if (event.mTaskRootPackage == null) {
                            event.mTaskRootPackage = activityData3.mTaskRootPackage;
                            event.mTaskRootClass = activityData3.mTaskRootClass;
                        }
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                    case 7:
                        logAppUsageEventReportedAtomLocked(7, packageUid, event.mPackage);
                        convertToSystemTimeLocked(event);
                        this.mLastTimeComponentUsedGlobal.put(event.mPackage, java.lang.Long.valueOf(event.mTimeStamp));
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                    case 8:
                    case 9:
                    case 11:
                    case 19:
                    case 20:
                        logAppUsageEventReportedAtomLocked(event.mEventType, packageUid, event.mPackage);
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                    case 23:
                        activityData = (com.android.server.usage.UsageStatsService.ActivityData) this.mVisibleActivities.removeReturnOld(event.mInstanceId);
                        if (activityData != null) {
                            android.util.Slog.w(TAG, "Unexpected activity event reported! (" + event.mPackage + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + event.mClass + " event : " + event.mEventType + " instanceId : " + event.mInstanceId + ")");
                            return;
                        }
                        if (activityData.lastEvent != 2) {
                            logAppUsageEventReportedAtomLocked(2, packageUid, event.mPackage);
                        }
                        synchronized (this.mUsageReporters) {
                            arraySet = (android.util.ArraySet) this.mUsageReporters.removeReturnOld(event.mInstanceId);
                        }
                        if (arraySet != null) {
                            synchronized (arraySet) {
                                int size = arraySet.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    try {
                                        this.mAppTimeLimit.noteUsageStop(buildFullToken(event.mPackage, (java.lang.String) arraySet.valueAt(i2)), i);
                                    } catch (java.lang.IllegalArgumentException e3) {
                                        android.util.Slog.w(TAG, "Failed to stop usage for during reporter death: " + e3);
                                    }
                                }
                            }
                        }
                        if (event.mTaskRootPackage == null) {
                            event.mTaskRootPackage = activityData.mTaskRootPackage;
                            event.mTaskRootClass = activityData.mTaskRootClass;
                        }
                        try {
                            this.mAppTimeLimit.noteUsageStop(activityData.mUsageSourcePackage, i);
                        } catch (java.lang.IllegalArgumentException e4) {
                            android.util.Slog.w(TAG, "Failed to note usage stop", e4);
                        }
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                    case 24:
                        event.mEventType = 23;
                        activityData = (com.android.server.usage.UsageStatsService.ActivityData) this.mVisibleActivities.removeReturnOld(event.mInstanceId);
                        if (activityData != null) {
                        }
                        break;
                    case 31:
                        convertToSystemTimeLocked(event);
                        this.mLastTimeComponentUsedGlobal.put(event.mPackage, java.lang.Long.valueOf(event.mTimeStamp));
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                    default:
                        userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                        }
                        break;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void logAppUsageEventReportedAtomLocked(int i, int i2, java.lang.String str) {
        com.android.internal.util.FrameworkStatsLog.write(269, i2, str, "", getAppUsageEventOccurredAtomEventType(i));
    }

    private int getAppUsageEventOccurredAtomEventType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 11:
                return 11;
            case 19:
                return 19;
            case 20:
                return 20;
            default:
                android.util.Slog.w(TAG, "Unsupported usage event logging: " + i);
                return -1;
        }
    }

    private java.lang.String getUsageSourcePackage(android.app.usage.UsageEvents.Event event) {
        switch (this.mUsageSource) {
            case 2:
                return event.mPackage;
            default:
                return event.mTaskRootPackage;
        }
    }

    void reportEventToAllUserId(android.app.usage.UsageEvents.Event event) {
        synchronized (this.mLock) {
            try {
                int size = this.mUserState.size();
                for (int i = 0; i < size; i++) {
                    reportEventOrAddToQueue(this.mUserState.keyAt(i), new android.app.usage.UsageEvents.Event(event));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void flushToDisk() {
        synchronized (this.mLock) {
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(25, android.os.SystemClock.elapsedRealtime());
            event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
            reportEventToAllUserId(event);
            flushToDiskLocked();
        }
        this.mAppStandby.flushToDisk();
    }

    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            android.util.Slog.i(TAG, "Removing user " + i + " and all data.");
            this.mUserState.remove(i);
            this.mAppTimeLimit.onUserRemoved(i);
        }
        synchronized (this.mLaunchTimeAlarmQueues) {
            try {
                com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue launchTimeAlarmQueue = this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue != null) {
                    launchTimeAlarmQueue.removeAllAlarms();
                    this.mLaunchTimeAlarmQueues.remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        synchronized (this.mPendingLaunchTimeChangePackages) {
            this.mPendingLaunchTimeChangePackages.remove(i);
        }
        this.mAppStandby.onUserRemoved(i);
        this.mResponseStatsTracker.onUserRemoved(i);
        com.android.server.usage.UsageStatsIdleService.cancelPruneJob(getContext(), i);
        com.android.server.usage.UsageStatsIdleService.cancelUpdateMappingsJob(getContext(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemoved(int i, java.lang.String str) {
        synchronized (this.mPendingLaunchTimeChangePackages) {
            try {
                android.util.ArraySet arraySet = this.mPendingLaunchTimeChangePackages.get(i);
                if (arraySet != null) {
                    arraySet.remove(str);
                }
            } finally {
            }
        }
        synchronized (this.mLaunchTimeAlarmQueues) {
            try {
                com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue launchTimeAlarmQueue = this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue != null) {
                    launchTimeAlarmQueue.removeAlarmForKey(str);
                }
            } finally {
            }
        }
        synchronized (this.mLock) {
            try {
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                if (this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(i);
                    if (userUsageStatsService == null) {
                        return;
                    }
                    int onPackageRemoved = userUsageStatsService.onPackageRemoved(str, currentTimeMillis);
                    if (onPackageRemoved != -1) {
                        com.android.server.usage.UsageStatsIdleService.schedulePruneJob(getContext(), i);
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pruneUninstalledPackagesData(int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    return false;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(i);
                if (userUsageStatsService == null) {
                    return false;
                }
                return userUsageStatsService.pruneUninstalledPackagesData();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updatePackageMappingsData(int i) {
        if (!shouldDeleteObsoleteData(android.os.UserHandle.of(i))) {
            return true;
        }
        java.util.HashMap<java.lang.String, java.lang.Long> installedPackages = getInstalledPackages(i);
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    return false;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(i);
                if (userUsageStatsService == null) {
                    return false;
                }
                return userUsageStatsService.updatePackageMappingsLocked(installedPackages);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<android.app.usage.UsageStats> queryUsageStats(int i, int i2, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query usage stats for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                java.util.List<android.app.usage.UsageStats> queryUsageStats = userUsageStatsServiceLocked.queryUsageStats(i2, j, j2);
                if (queryUsageStats == null) {
                    return null;
                }
                if (z) {
                    for (int size = queryUsageStats.size() - 1; size >= 0; size--) {
                        android.app.usage.UsageStats usageStats = queryUsageStats.get(size);
                        if (isInstantApp(usageStats.mPackageName, i)) {
                            queryUsageStats.set(size, usageStats.getObfuscatedForInstantApp());
                        }
                    }
                }
                return queryUsageStats;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<android.app.usage.ConfigurationStats> queryConfigurationStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query configuration stats for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryConfigurationStats(i2, j, j2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<android.app.usage.EventStats> queryEventStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query event stats for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEventStats(i2, j, j2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.app.usage.UsageEvents queryEvents(int i, long j, long j2, int i2) {
        return queryEventsWithQueryFilters(i, j, j2, i2, libcore.util.EmptyArray.INT, null);
    }

    android.app.usage.UsageEvents queryEventsWithQueryFilters(int i, long j, long j2, int i2, int[] iArr, android.util.ArraySet<java.lang.String> arraySet) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query events for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEvents(j, j2, i2, iArr, arraySet);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.app.usage.UsageEvents queryEventsForPackage(int i, long j, long j2, java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query package events for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEventsForPackage(j, j2, str, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private android.app.usage.UsageEvents queryEarliestAppEvents(int i, long j, long j2, int i2) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query earliest events for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEarliestAppEvents(j, j2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private android.app.usage.UsageEvents queryEarliestEventsForPackage(int i, long j, long j2, @android.annotation.NonNull java.lang.String str, int i2) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                    android.util.Slog.w(TAG, "Failed to query earliest package events for locked user " + i);
                    return null;
                }
                com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEarliestEventsForPackage(j, j2, str, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long getEstimatedPackageLaunchTime(int i, java.lang.String str) {
        long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(str, i);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (estimatedLaunchTime < currentTimeMillis || estimatedLaunchTime == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            long calculateEstimatedPackageLaunchTime = calculateEstimatedPackageLaunchTime(i, str);
            this.mAppStandby.setEstimatedLaunchTime(str, i, calculateEstimatedPackageLaunchTime);
            getOrCreateLaunchTimeAlarmQueue(i).addAlarm(str, android.os.SystemClock.elapsedRealtime() + (calculateEstimatedPackageLaunchTime - currentTimeMillis));
            return calculateEstimatedPackageLaunchTime;
        }
        return estimatedLaunchTime;
    }

    private com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue getOrCreateLaunchTimeAlarmQueue(int i) {
        com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue launchTimeAlarmQueue;
        synchronized (this.mLaunchTimeAlarmQueues) {
            try {
                launchTimeAlarmQueue = this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue == null) {
                    launchTimeAlarmQueue = new com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue(i, getContext(), this.mHandler.getLooper());
                    this.mLaunchTimeAlarmQueues.put(i, launchTimeAlarmQueue);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return launchTimeAlarmQueue;
    }

    private long calculateEstimatedPackageLaunchTime(int i, java.lang.String str) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j = currentTimeMillis + 31536000000L;
        android.app.usage.UsageEvents queryEarliestEventsForPackage = queryEarliestEventsForPackage(i, currentTimeMillis - 604800000, currentTimeMillis, str, 1);
        if (queryEarliestEventsForPackage == null) {
            return j;
        }
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        if (!queryEarliestEventsForPackage.getNextEvent(event)) {
            return j;
        }
        boolean z = currentTimeMillis - event.getTimeStamp() > 86400000;
        do {
            if (event.getEventType() == 1) {
                long calculateNextLaunchTime = calculateNextLaunchTime(z, event.getTimeStamp());
                if (calculateNextLaunchTime > currentTimeMillis) {
                    return calculateNextLaunchTime;
                }
            }
        } while (queryEarliestEventsForPackage.getNextEvent(event));
        return j;
    }

    private static long calculateNextLaunchTime(boolean z, long j) {
        if (z) {
            return j + 604800000;
        }
        return j + 86400000;
    }

    private void handleEstimatedLaunchTimesOnUserUnlock(int i) {
        long j;
        long calculateNextLaunchTime;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j2 = 604800000;
        android.app.usage.UsageEvents queryEarliestAppEvents = queryEarliestAppEvents(i, currentTimeMillis - 604800000, currentTimeMillis, 1);
        if (queryEarliestAppEvents == null) {
            return;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        com.android.server.usage.UsageStatsService.LaunchTimeAlarmQueue orCreateLaunchTimeAlarmQueue = getOrCreateLaunchTimeAlarmQueue(i);
        boolean nextEvent = queryEarliestAppEvents.getNextEvent(event);
        boolean z = false;
        while (nextEvent) {
            java.lang.String packageName = event.getPackageName();
            if (!arrayMap.containsKey(packageName)) {
                arrayMap.put(packageName, java.lang.Boolean.valueOf(currentTimeMillis - event.getTimeStamp() > 86400000));
            }
            if (event.getEventType() != 1) {
                j = j2;
            } else {
                long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(packageName, i);
                if (estimatedLaunchTime < currentTimeMillis || estimatedLaunchTime == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    calculateNextLaunchTime = calculateNextLaunchTime(((java.lang.Boolean) arrayMap.get(packageName)).booleanValue(), event.getTimeStamp());
                    this.mAppStandby.setEstimatedLaunchTime(packageName, i, calculateNextLaunchTime);
                } else {
                    calculateNextLaunchTime = estimatedLaunchTime;
                }
                j = 604800000;
                if (calculateNextLaunchTime < currentTimeMillis + 604800000) {
                    z |= stageChangedEstimatedLaunchTime(i, packageName);
                }
                orCreateLaunchTimeAlarmQueue.addAlarm(packageName, (calculateNextLaunchTime - currentTimeMillis) + elapsedRealtime);
            }
            nextEvent = queryEarliestAppEvents.getNextEvent(event);
            j2 = j;
        }
        if (z) {
            this.mHandler.sendEmptyMessage(9);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEstimatedLaunchTime(int i, java.lang.String str, long j) {
        if (j > java.lang.System.currentTimeMillis() && j != this.mAppStandby.getEstimatedLaunchTime(str, i)) {
            this.mAppStandby.setEstimatedLaunchTime(str, i, j);
            if (stageChangedEstimatedLaunchTime(i, str)) {
                this.mHandler.sendEmptyMessage(9);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEstimatedLaunchTimes(int i, java.util.List<android.app.usage.AppLaunchEstimateInfo> list) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            android.app.usage.AppLaunchEstimateInfo appLaunchEstimateInfo = list.get(size);
            if (appLaunchEstimateInfo.estimatedLaunchTime > currentTimeMillis) {
                if (appLaunchEstimateInfo.estimatedLaunchTime != this.mAppStandby.getEstimatedLaunchTime(appLaunchEstimateInfo.packageName, i)) {
                    this.mAppStandby.setEstimatedLaunchTime(appLaunchEstimateInfo.packageName, i, appLaunchEstimateInfo.estimatedLaunchTime);
                    z |= stageChangedEstimatedLaunchTime(i, appLaunchEstimateInfo.packageName);
                }
            }
        }
        if (z) {
            this.mHandler.sendEmptyMessage(9);
        }
    }

    private boolean stageChangedEstimatedLaunchTime(int i, java.lang.String str) {
        boolean add;
        synchronized (this.mPendingLaunchTimeChangePackages) {
            add = this.mPendingLaunchTimeChangePackages.add(i, str);
        }
        return add;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.UsageEventListener usageEventListener) {
        synchronized (this.mUsageEventListeners) {
            this.mUsageEventListeners.add(usageEventListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.UsageEventListener usageEventListener) {
        synchronized (this.mUsageEventListeners) {
            this.mUsageEventListeners.remove(usageEventListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerLaunchTimeChangedListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
        this.mEstimatedLaunchTimeChangedListeners.add(estimatedLaunchTimeChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterLaunchTimeChangedListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
        this.mEstimatedLaunchTimeChangedListeners.remove(estimatedLaunchTimeChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldDeleteObsoleteData(android.os.UserHandle userHandle) {
        android.app.admin.DevicePolicyManagerInternal dpmInternal = getDpmInternal();
        return dpmInternal == null || dpmInternal.getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String buildFullToken(java.lang.String str, java.lang.String str2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length() + str2.length() + 1);
        sb.append(str);
        sb.append(TOKEN_DELIMITER);
        sb.append(str2);
        return sb.toString();
    }

    private void flushToDiskLocked() {
        int size = this.mUserState.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUserState.keyAt(i);
            if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(keyAt))) {
                persistPendingEventsLocked(keyAt);
            } else {
                com.android.server.usage.UserUsageStatsService userUsageStatsService = this.mUserState.get(keyAt);
                if (userUsageStatsService != null) {
                    userUsageStatsService.persistActiveStats();
                }
            }
        }
        this.mHandler.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getTrimmedString(java.lang.String str) {
        if (str != null && str.length() > 127) {
            return str.substring(0, 127);
        }
        return str;
    }

    void dump(java.lang.String[] strArr, java.io.PrintWriter printWriter) {
        boolean z;
        boolean z2;
        int[] iArr;
        boolean z3;
        boolean z4;
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z5 = true;
        if (strArr == null) {
            z = false;
            z2 = false;
        } else {
            z = false;
            z2 = false;
            for (int i = 0; i < strArr.length; i++) {
                java.lang.String str = strArr[i];
                if ("--checkin".equals(str)) {
                    z = true;
                } else if ("-c".equals(str)) {
                    z2 = true;
                } else {
                    if ("flush".equals(str)) {
                        synchronized (this.mLock) {
                            flushToDiskLocked();
                        }
                        this.mAppStandby.flushToDisk();
                        printWriter.println("Flushed stats to disk");
                        return;
                    }
                    if ("is-app-standby-enabled".equals(str)) {
                        printWriter.println(this.mAppStandby.isAppIdleEnabled());
                        return;
                    }
                    if ("apptimelimit".equals(str)) {
                        synchronized (this.mLock) {
                            int i2 = i + 1;
                            try {
                                if (i2 >= strArr.length) {
                                    this.mAppTimeLimit.dump(null, printWriter);
                                } else {
                                    this.mAppTimeLimit.dump((java.lang.String[]) java.util.Arrays.copyOfRange(strArr, i2, strArr.length), printWriter);
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("file".equals(str)) {
                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter2 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            try {
                                if (i + 1 >= strArr.length) {
                                    int size = this.mUserState.size();
                                    for (int i3 = 0; i3 < size; i3++) {
                                        int keyAt = this.mUserState.keyAt(i3);
                                        if (this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(keyAt))) {
                                            indentingPrintWriter2.println("user=" + keyAt);
                                            indentingPrintWriter2.increaseIndent();
                                            this.mUserState.valueAt(i3).dumpFile(indentingPrintWriter2, null);
                                            indentingPrintWriter2.decreaseIndent();
                                        }
                                    }
                                } else {
                                    int parseUserIdFromArgs = parseUserIdFromArgs(strArr, i, indentingPrintWriter2);
                                    if (parseUserIdFromArgs != -10000) {
                                        this.mUserState.get(parseUserIdFromArgs).dumpFile(indentingPrintWriter2, (java.lang.String[]) java.util.Arrays.copyOfRange(strArr, i + 2, strArr.length));
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("database-info".equals(str)) {
                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter3 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            try {
                                if (i + 1 >= strArr.length) {
                                    int size2 = this.mUserState.size();
                                    for (int i4 = 0; i4 < size2; i4++) {
                                        int keyAt2 = this.mUserState.keyAt(i4);
                                        if (this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(keyAt2))) {
                                            indentingPrintWriter3.println("user=" + keyAt2);
                                            indentingPrintWriter3.increaseIndent();
                                            this.mUserState.valueAt(i4).dumpDatabaseInfo(indentingPrintWriter3);
                                            indentingPrintWriter3.decreaseIndent();
                                        }
                                    }
                                } else {
                                    int parseUserIdFromArgs2 = parseUserIdFromArgs(strArr, i, indentingPrintWriter3);
                                    if (parseUserIdFromArgs2 != -10000) {
                                        this.mUserState.get(parseUserIdFromArgs2).dumpDatabaseInfo(indentingPrintWriter3);
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("appstandby".equals(str)) {
                        this.mAppStandby.dumpState(strArr, printWriter);
                        return;
                    }
                    if ("stats-directory".equals(str)) {
                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter4 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            try {
                                int parseUserIdFromArgs3 = parseUserIdFromArgs(strArr, i, indentingPrintWriter4);
                                if (parseUserIdFromArgs3 != -10000) {
                                    indentingPrintWriter4.println(new java.io.File(android.os.Environment.getDataSystemCeDirectory(parseUserIdFromArgs3), "usagestats").getAbsolutePath());
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("mappings".equals(str)) {
                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter5 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            try {
                                int parseUserIdFromArgs4 = parseUserIdFromArgs(strArr, i, indentingPrintWriter5);
                                if (parseUserIdFromArgs4 != -10000) {
                                    this.mUserState.get(parseUserIdFromArgs4).dumpMappings(indentingPrintWriter5);
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if ("broadcast-response-stats".equals(str)) {
                        synchronized (this.mLock) {
                            this.mResponseStatsTracker.dump(indentingPrintWriter);
                        }
                        return;
                    }
                    if ("app-component-usage".equals(str)) {
                        com.android.internal.util.IndentingPrintWriter indentingPrintWriter6 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                        synchronized (this.mLock) {
                            try {
                                if (!this.mLastTimeComponentUsedGlobal.isEmpty()) {
                                    indentingPrintWriter6.println("App Component Usages:");
                                    indentingPrintWriter6.increaseIndent();
                                    for (java.lang.String str2 : this.mLastTimeComponentUsedGlobal.keySet()) {
                                        indentingPrintWriter6.println("package=" + str2 + " lastUsed=" + com.android.server.usage.UserUsageStatsService.formatDateTime(this.mLastTimeComponentUsedGlobal.get(str2).longValue(), true));
                                    }
                                    indentingPrintWriter6.decreaseIndent();
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    if (str != null && !str.startsWith("-")) {
                        arrayList.add(str);
                    }
                }
            }
        }
        printWriter.println("Flags:");
        printWriter.println("    android.app.usage.user_interaction_type_api: " + android.app.usage.Flags.userInteractionTypeApi());
        printWriter.println("    android.app.usage.use_parceled_list: " + android.app.usage.Flags.useParceledList());
        printWriter.println("    android.app.usage.filter_based_event_query_api: " + android.app.usage.Flags.filterBasedEventQueryApi());
        synchronized (this.mLock) {
            try {
                int size3 = this.mUserState.size();
                iArr = new int[size3];
                int i5 = 0;
                while (i5 < size3) {
                    int keyAt3 = this.mUserState.keyAt(i5);
                    iArr[i5] = keyAt3;
                    indentingPrintWriter.printPair("user", java.lang.Integer.valueOf(keyAt3));
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                    if (this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(keyAt3))) {
                        if (z) {
                            this.mUserState.valueAt(i5).checkin(indentingPrintWriter);
                            z3 = z;
                            z4 = z5;
                        } else {
                            this.mUserState.valueAt(i5).dump(indentingPrintWriter, arrayList, z2);
                            indentingPrintWriter.println();
                            z3 = z;
                            z4 = z5;
                        }
                    } else {
                        synchronized (this.mReportedEvents) {
                            java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList = this.mReportedEvents.get(keyAt3);
                            if (linkedList == null || linkedList.isEmpty()) {
                                z3 = z;
                                z4 = z5;
                            } else {
                                int size4 = linkedList.size();
                                indentingPrintWriter.println("Pending events: count=" + size4);
                                indentingPrintWriter.increaseIndent();
                                int i6 = 0;
                                while (i6 < size4) {
                                    com.android.server.usage.UserUsageStatsService.printEvent(indentingPrintWriter, linkedList.get(i6), true);
                                    i6++;
                                    z = z;
                                }
                                z3 = z;
                                z4 = true;
                                indentingPrintWriter.decreaseIndent();
                                indentingPrintWriter.println();
                            }
                        }
                    }
                    indentingPrintWriter.decreaseIndent();
                    i5++;
                    z5 = z4;
                    z = z3;
                }
                indentingPrintWriter.println();
                indentingPrintWriter.printPair("Usage Source", android.app.usage.UsageStatsManager.usageSourceToString(this.mUsageSource));
                indentingPrintWriter.println();
                this.mAppTimeLimit.dump(null, printWriter);
                indentingPrintWriter.println();
                this.mResponseStatsTracker.dump(indentingPrintWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            } finally {
            }
        }
        this.mAppStandby.dumpUsers(indentingPrintWriter, iArr, arrayList);
        if (com.android.internal.util.CollectionUtils.isEmpty(arrayList)) {
            printWriter.println();
            this.mAppStandby.dumpState(strArr, printWriter);
        }
    }

    private int parseUserIdFromArgs(java.lang.String[] strArr, int i, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            int parseInt = java.lang.Integer.parseInt(strArr[i + 1]);
            if (this.mUserState.indexOfKey(parseInt) < 0) {
                indentingPrintWriter.println("the specified user does not exist.");
                return com.android.server.am.ProcessList.INVALID_ADJ;
            }
            if (!this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(parseInt))) {
                indentingPrintWriter.println("the specified user is currently in a locked state.");
                return com.android.server.am.ProcessList.INVALID_ADJ;
            }
            return parseInt;
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NumberFormatException e) {
            indentingPrintWriter.println("invalid user specified.");
            return com.android.server.am.ProcessList.INVALID_ADJ;
        }
    }

    class H extends android.os.Handler {
        public H(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int size;
            int keyAt;
            switch (message.what) {
                case 0:
                    com.android.server.usage.UsageStatsService.this.reportEvent((android.app.usage.UsageEvents.Event) message.obj, message.arg1);
                    return;
                case 1:
                    com.android.server.usage.UsageStatsService.this.flushToDisk();
                    return;
                case 2:
                    com.android.server.usage.UsageStatsService.this.onUserRemoved(message.arg1);
                    return;
                case 3:
                case 8:
                default:
                    super.handleMessage(message);
                    return;
                case 4:
                    com.android.server.usage.UsageStatsService.this.reportEventToAllUserId((android.app.usage.UsageEvents.Event) message.obj);
                    return;
                case 5:
                    int i = message.arg1;
                    try {
                        try {
                            android.os.Trace.traceBegin(524288L, "usageStatsHandleUserUnlocked(" + i + ")");
                            com.android.server.usage.UsageStatsService.this.onUserUnlocked(i);
                        } catch (java.lang.Exception e) {
                            if (com.android.server.usage.UsageStatsService.this.mUserManager.isUserUnlocked(i)) {
                                throw e;
                            }
                            android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Attempted to unlock stopped or removed user " + message.arg1);
                        }
                        return;
                    } finally {
                        android.os.Trace.traceEnd(524288L);
                    }
                case 6:
                    com.android.server.usage.UsageStatsService.this.onPackageRemoved(message.arg1, (java.lang.String) message.obj);
                    return;
                case 7:
                    synchronized (com.android.server.usage.UsageStatsService.this.mLock) {
                        com.android.server.usage.UsageStatsService.this.loadGlobalComponentUsageLocked();
                    }
                    return;
                case 9:
                    removeMessages(9);
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    synchronized (com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                        size = com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages.size();
                    }
                    for (int i2 = size - 1; i2 >= 0; i2--) {
                        arraySet.clear();
                        synchronized (com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                            keyAt = com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages.keyAt(i2);
                            arraySet.addAll(com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages.get(keyAt));
                            com.android.server.usage.UsageStatsService.this.mPendingLaunchTimeChangePackages.remove(keyAt);
                        }
                        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                            java.lang.String str = (java.lang.String) arraySet.valueAt(size2);
                            long estimatedPackageLaunchTime = com.android.server.usage.UsageStatsService.this.getEstimatedPackageLaunchTime(keyAt, str);
                            java.util.Iterator it = com.android.server.usage.UsageStatsService.this.mEstimatedLaunchTimeChangedListeners.iterator();
                            while (it.hasNext()) {
                                ((android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener) it.next()).onEstimatedLaunchTimeChanged(keyAt, str, estimatedPackageLaunchTime);
                            }
                        }
                    }
                    return;
                case 10:
                    com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.onUidRemoved(message.arg1);
                    return;
                case 11:
                    com.android.server.usage.UsageStatsService.this.mAppStandby.postCheckIdleStates(message.arg1);
                    return;
            }
        }
    }

    void clearLastUsedTimestamps(@android.annotation.NonNull java.lang.String str, int i) {
        this.mAppStandby.clearLastUsedTimestampsForTest(str, i);
    }

    void deletePackageData(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            this.mUserState.get(i).deleteDataFor(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class BinderService extends android.app.usage.IUsageStatsManager.Stub {
        private BinderService() {
        }

        private boolean hasQueryPermission(java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid == 1000) {
                return true;
            }
            int noteOp = com.android.server.usage.UsageStatsService.this.mAppOps.noteOp(43, callingUid, str);
            return noteOp == 3 ? com.android.server.usage.UsageStatsService.this.getContext().checkCallingPermission("android.permission.PACKAGE_USAGE_STATS") == 0 : noteOp == 0;
        }

        private boolean canReportUsageStats() {
            return isCallingUidSystem() || com.android.server.usage.UsageStatsService.this.getContext().checkCallingPermission("android.permission.REPORT_USAGE_STATS") == 0;
        }

        private boolean hasObserverPermission() {
            int callingUid = android.os.Binder.getCallingUid();
            android.app.admin.DevicePolicyManagerInternal dpmInternal = com.android.server.usage.UsageStatsService.this.getDpmInternal();
            return callingUid == 1000 || (dpmInternal != null && (dpmInternal.isActiveProfileOwner(callingUid) || dpmInternal.isActiveDeviceOwner(callingUid))) || com.android.server.usage.UsageStatsService.this.getContext().checkCallingPermission("android.permission.OBSERVE_APP_USAGE") == 0;
        }

        private boolean hasPermissions(java.lang.String... strArr) {
            if (android.os.Binder.getCallingUid() == 1000) {
                return true;
            }
            android.content.Context context = com.android.server.usage.UsageStatsService.this.getContext();
            boolean z = true;
            for (java.lang.String str : strArr) {
                z = z && context.checkCallingPermission(str) == 0;
            }
            return z;
        }

        private void checkCallerIsSystemOrSameApp(java.lang.String str) {
            if (isCallingUidSystem()) {
                return;
            }
            checkCallerIsSameApp(str);
        }

        private void checkCallerIsSameApp(java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            if (com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(callingUid)) != callingUid) {
                throw new java.lang.SecurityException("Calling uid " + callingUid + " cannot query eventsfor package " + str);
            }
        }

        private boolean isCallingUidSystem() {
            return android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) == 1000;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v8 */
        /* JADX WARN: Type inference failed for: r3v9 */
        private android.app.usage.UsageEvents queryEventsHelper(int i, long j, long j2, java.lang.String str, int[] iArr, android.util.ArraySet<java.lang.String> arraySet) {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, android.os.UserHandle.getCallingUserId());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                boolean shouldHideShortcutInvocationEvents = com.android.server.usage.UsageStatsService.this.shouldHideShortcutInvocationEvents(i, str, callingPid, callingUid);
                boolean shouldHideLocusIdEvents = com.android.server.usage.UsageStatsService.this.shouldHideLocusIdEvents(callingPid, callingUid);
                boolean shouldObfuscateNotificationEvents = com.android.server.usage.UsageStatsService.this.shouldObfuscateNotificationEvents(callingPid, callingUid);
                ?? r3 = shouldObfuscateInstantAppsForCaller;
                if (shouldHideShortcutInvocationEvents) {
                    r3 = (shouldObfuscateInstantAppsForCaller ? 1 : 0) | 2;
                }
                if (shouldHideLocusIdEvents) {
                    r3 = (r3 == true ? 1 : 0) | '\b';
                }
                return com.android.server.usage.UsageStatsService.this.queryEventsWithQueryFilters(i, j, j2, shouldObfuscateNotificationEvents ? r3 | 4 : r3, iArr, arraySet);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void reportUserInteractionInnerHelper(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) {
            if (android.app.usage.Flags.reportUsageStatsPermission()) {
                if (!canReportUsageStats()) {
                    throw new java.lang.SecurityException("Only the system or holders of the REPORT_USAGE_STATS permission are allowed to call reportUserInteraction");
                }
                if (i != android.os.UserHandle.getCallingUserId()) {
                    com.android.server.usage.UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Caller doesn't have INTERACT_ACROSS_USERS_FULL permission");
                }
            } else if (!isCallingUidSystem()) {
                throw new java.lang.SecurityException("Only system is allowed to call reportUserInteraction");
            }
            if (com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, i) < 0) {
                throw new java.lang.IllegalArgumentException("Package " + str + " does not exist!");
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(7, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mExtras = persistableBundle;
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public android.content.pm.ParceledListSlice<android.app.usage.UsageStats> queryUsageStats(int i, long j, long j2, java.lang.String str, int i2) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUid = android.os.Binder.getCallingUid();
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i2, false, true, "queryUsageStats", str);
            boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, android.os.UserHandle.getCallingUserId());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List<android.app.usage.UsageStats> queryUsageStats = com.android.server.usage.UsageStatsService.this.queryUsageStats(handleIncomingUser, i, j, j2, shouldObfuscateInstantAppsForCaller);
                if (queryUsageStats != null) {
                    return new android.content.pm.ParceledListSlice<>(queryUsageStats);
                }
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.content.pm.ParceledListSlice<android.app.usage.ConfigurationStats> queryConfigurationStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List<android.app.usage.ConfigurationStats> queryConfigurationStats = com.android.server.usage.UsageStatsService.this.queryConfigurationStats(callingUserId, i, j, j2);
                if (queryConfigurationStats != null) {
                    return new android.content.pm.ParceledListSlice<>(queryConfigurationStats);
                }
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.content.pm.ParceledListSlice<android.app.usage.EventStats> queryEventStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List<android.app.usage.EventStats> queryEventStats = com.android.server.usage.UsageStatsService.this.queryEventStats(callingUserId, i, j, j2);
                if (queryEventStats != null) {
                    return new android.content.pm.ParceledListSlice<>(queryEventStats);
                }
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.app.usage.UsageEvents queryEvents(long j, long j2, java.lang.String str) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            return queryEventsHelper(android.os.UserHandle.getCallingUserId(), j, j2, str, libcore.util.EmptyArray.INT, null);
        }

        public android.app.usage.UsageEvents queryEventsWithFilter(@android.annotation.NonNull android.app.usage.UsageEventsQuery usageEventsQuery, @android.annotation.NonNull java.lang.String str) {
            int i;
            java.util.Objects.requireNonNull(usageEventsQuery);
            java.util.Objects.requireNonNull(str);
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int userId = usageEventsQuery.getUserId();
            if (userId != -10000) {
                i = userId;
            } else {
                i = callingUserId;
            }
            if (i != callingUserId) {
                com.android.server.usage.UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to query usage stats for user " + i);
            }
            return queryEventsHelper(i, usageEventsQuery.getBeginTimeMillis(), usageEventsQuery.getEndTimeMillis(), str, usageEventsQuery.getEventTypes(), new android.util.ArraySet<>(usageEventsQuery.getPackageNames()));
        }

        public android.app.usage.UsageEvents queryEventsForPackage(long j, long j2, java.lang.String str) {
            int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
            checkCallerIsSameApp(str);
            boolean hasQueryPermission = hasQueryPermission(str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.usage.UsageStatsService.this.queryEventsForPackage(userId, j, j2, str, hasQueryPermission);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.app.usage.UsageEvents queryEventsForUser(long j, long j2, int i, java.lang.String str) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.usage.UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to query usage stats for this user");
            }
            return queryEventsHelper(i, j, j2, str, libcore.util.EmptyArray.INT, null);
        }

        public android.app.usage.UsageEvents queryEventsForPackageForUser(long j, long j2, int i, java.lang.String str, java.lang.String str2) {
            if (!hasQueryPermission(str2)) {
                return null;
            }
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.usage.UsageStatsService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to query usage stats for this user");
            }
            checkCallerIsSystemOrSameApp(str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.usage.UsageStatsService.this.queryEventsForPackage(i, j, j2, str, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isAppStandbyEnabled() {
            return com.android.server.usage.UsageStatsService.this.mAppStandby.isAppIdleEnabled();
        }

        public boolean isAppInactive(java.lang.String str, int i, java.lang.String str2) {
            int callingUid = android.os.Binder.getCallingUid();
            try {
                int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "isAppInactive", (java.lang.String) null);
                if (!str.equals(str2)) {
                    if (!hasQueryPermission(str2)) {
                        return false;
                    }
                } else if (com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str2, 0L, handleIncomingUser) != callingUid) {
                    return false;
                }
                boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.usage.UsageStatsService.this.mAppStandby.isAppIdleFiltered(str, handleIncomingUser, android.os.SystemClock.elapsedRealtime(), shouldObfuscateInstantAppsForCaller);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setAppInactive(java.lang.String str, boolean z, int i) {
            try {
                int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "setAppInactive", (java.lang.String) null);
                com.android.server.usage.UsageStatsService.this.getContext().enforceCallingPermission("android.permission.CHANGE_APP_IDLE_STATE", "No permission to change app idle state");
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (com.android.server.usage.UsageStatsService.this.mAppStandby.getAppId(str) < 0) {
                        return;
                    }
                    com.android.server.usage.UsageStatsService.this.mAppStandby.setAppIdleAsync(str, z, handleIncomingUser);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getAppStandbyBucket(java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            try {
                int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (java.lang.String) null);
                int packageUid = com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, handleIncomingUser);
                boolean z = packageUid == callingUid;
                if (!z && !hasQueryPermission(str2)) {
                    throw new java.lang.SecurityException("Don't have permission to query app standby bucket");
                }
                boolean isInstantApp = com.android.server.usage.UsageStatsService.this.isInstantApp(str, handleIncomingUser);
                boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                if (packageUid < 0 || (!z && isInstantApp && shouldObfuscateInstantAppsForCaller)) {
                    throw new java.lang.IllegalArgumentException("Cannot get standby bucket for non existent package (" + str + ")");
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.usage.UsageStatsService.this.mAppStandby.getAppStandbyBucket(str, handleIncomingUser, android.os.SystemClock.elapsedRealtime(), false);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.EnforcePermission("android.permission.CHANGE_APP_IDLE_STATE")
        public void setAppStandbyBucket(java.lang.String str, int i, int i2) {
            super.setAppStandbyBucket_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.mAppStandby.setAppStandbyBucket(str, i, i2, callingUid, callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.content.pm.ParceledListSlice<android.app.usage.AppStandbyInfo> getAppStandbyBuckets(java.lang.String str, int i) {
            final int callingUid = android.os.Binder.getCallingUid();
            try {
                final int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (java.lang.String) null);
                if (!hasQueryPermission(str)) {
                    throw new java.lang.SecurityException("Don't have permission to query app standby bucket");
                }
                final boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.List appStandbyBuckets = com.android.server.usage.UsageStatsService.this.mAppStandby.getAppStandbyBuckets(handleIncomingUser);
                    if (appStandbyBuckets == null) {
                        return android.content.pm.ParceledListSlice.emptyList();
                    }
                    appStandbyBuckets.removeIf(new java.util.function.Predicate() { // from class: com.android.server.usage.UsageStatsService$BinderService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getAppStandbyBuckets$0;
                            lambda$getAppStandbyBuckets$0 = com.android.server.usage.UsageStatsService.BinderService.this.lambda$getAppStandbyBuckets$0(callingUid, handleIncomingUser, shouldObfuscateInstantAppsForCaller, (android.app.usage.AppStandbyInfo) obj);
                            return lambda$getAppStandbyBuckets$0;
                        }
                    });
                    return new android.content.pm.ParceledListSlice<>(appStandbyBuckets);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$getAppStandbyBuckets$0(int i, int i2, boolean z, android.app.usage.AppStandbyInfo appStandbyInfo) {
            return !com.android.server.usage.UsageStatsService.this.sameApp(i, i2, appStandbyInfo.mPackageName) && com.android.server.usage.UsageStatsService.this.isInstantApp(appStandbyInfo.mPackageName, i2) && z;
        }

        @android.annotation.EnforcePermission("android.permission.CHANGE_APP_IDLE_STATE")
        public void setAppStandbyBuckets(android.content.pm.ParceledListSlice parceledListSlice, int i) {
            super.setAppStandbyBuckets_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.mAppStandby.setAppStandbyBuckets(parceledListSlice.getList(), i, callingUid, callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getAppMinStandbyBucket(java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            try {
                int handleIncomingUser = android.app.ActivityManager.getService().handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "getAppStandbyBucket", (java.lang.String) null);
                int packageUid = com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, handleIncomingUser);
                if (packageUid != callingUid && !hasQueryPermission(str2)) {
                    throw new java.lang.SecurityException("Don't have permission to query min app standby bucket");
                }
                boolean isInstantApp = com.android.server.usage.UsageStatsService.this.isInstantApp(str, handleIncomingUser);
                boolean shouldObfuscateInstantAppsForCaller = com.android.server.usage.UsageStatsService.this.shouldObfuscateInstantAppsForCaller(callingUid, handleIncomingUser);
                if (packageUid < 0 || (isInstantApp && shouldObfuscateInstantAppsForCaller)) {
                    throw new java.lang.IllegalArgumentException("Cannot get min standby bucket for non existent package (" + str + ")");
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.usage.UsageStatsService.this.mAppStandby.getAppMinStandbyBucket(str, android.os.UserHandle.getAppId(packageUid), handleIncomingUser, false);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.EnforcePermission("android.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE")
        public void setEstimatedLaunchTime(java.lang.String str, long j, int i) {
            super.setEstimatedLaunchTime_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.setEstimatedLaunchTime(i, str, j);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE")
        public void setEstimatedLaunchTimes(android.content.pm.ParceledListSlice parceledListSlice, int i) {
            super.setEstimatedLaunchTimes_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.setEstimatedLaunchTimes(i, parceledListSlice.getList());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void onCarrierPrivilegedAppsChanged() {
            com.android.server.usage.UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.BIND_CARRIER_SERVICES", "onCarrierPrivilegedAppsChanged can only be called by privileged apps.");
            com.android.server.usage.UsageStatsService.this.mAppStandby.clearCarrierPrivilegedApps();
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.usage.UsageStatsService.this.getContext(), com.android.server.usage.UsageStatsService.TAG, printWriter)) {
                com.android.server.usage.UsageStatsService.this.dump(strArr, printWriter);
            }
        }

        public void reportChooserSelection(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2, java.lang.String[] strArr, @android.annotation.NonNull java.lang.String str3) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Package selection must not be null.");
            }
            if (str2 == null || str2.isBlank() || str3 == null || str3.isBlank()) {
                return;
            }
            if (android.app.usage.Flags.reportUsageStatsPermission() && !canReportUsageStats()) {
                throw new java.lang.SecurityException("Only the system or holders of the REPORT_USAGE_STATS permission are allowed to call reportChooserSelection");
            }
            if (com.android.server.usage.UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, i) < 0) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Event report user selecting an invalid package");
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(9, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mAction = str3;
            event.mContentType = str2;
            event.mContentAnnotations = strArr;
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public void reportUserInteraction(java.lang.String str, int i) {
            reportUserInteractionInnerHelper(str, i, null);
        }

        public void reportUserInteractionWithBundle(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) {
            java.util.Objects.requireNonNull(str);
            if (persistableBundle == null || persistableBundle.size() == 0) {
                throw new java.lang.IllegalArgumentException("Emtry extras!");
            }
            java.lang.String string = persistableBundle.getString("android.app.usage.extra.EVENT_CATEGORY");
            if (android.text.TextUtils.isEmpty(string)) {
                throw new java.lang.IllegalArgumentException("Empty android.app.usage.extra.EVENT_CATEGORY");
            }
            java.lang.String string2 = persistableBundle.getString("android.app.usage.extra.EVENT_ACTION");
            if (android.text.TextUtils.isEmpty(string2)) {
                throw new java.lang.IllegalArgumentException("Empty android.app.usage.extra.EVENT_ACTION");
            }
            android.os.PersistableBundle persistableBundle2 = new android.os.PersistableBundle();
            persistableBundle2.putString("android.app.usage.extra.EVENT_CATEGORY", com.android.server.usage.UsageStatsService.this.getTrimmedString(string));
            persistableBundle2.putString("android.app.usage.extra.EVENT_ACTION", com.android.server.usage.UsageStatsService.this.getTrimmedString(string2));
            reportUserInteractionInnerHelper(str, i, persistableBundle2);
        }

        public void registerAppUsageObserver(int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, java.lang.String str) {
            if (!hasObserverPermission()) {
                throw new java.lang.SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new java.lang.IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null) {
                throw new java.lang.NullPointerException("callbackIntent can't be null");
            }
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.registerAppUsageObserver(callingUid, i, strArr, j, pendingIntent, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterAppUsageObserver(int i, java.lang.String str) {
            if (!hasObserverPermission()) {
                throw new java.lang.SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.unregisterAppUsageObserver(callingUid, i, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerUsageSessionObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, java.lang.String str) {
            if (!hasObserverPermission()) {
                throw new java.lang.SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new java.lang.IllegalArgumentException("Must specify at least one observed entity");
            }
            if (pendingIntent == null) {
                throw new java.lang.NullPointerException("limitReachedCallbackIntent can't be null");
            }
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.registerUsageSessionObserver(callingUid, i, strArr, j, j2, pendingIntent, pendingIntent2, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterUsageSessionObserver(int i, java.lang.String str) {
            if (!hasObserverPermission()) {
                throw new java.lang.SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.unregisterUsageSessionObserver(callingUid, i, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerAppUsageLimitObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            android.app.admin.DevicePolicyManagerInternal dpmInternal = com.android.server.usage.UsageStatsService.this.getDpmInternal();
            if (!hasPermissions("android.permission.SUSPEND_APPS", "android.permission.OBSERVE_APP_USAGE") && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new java.lang.SecurityException("Caller must be the active supervision app or it must have both SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            if (strArr == null || strArr.length == 0) {
                throw new java.lang.IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null && j2 < j) {
                throw new java.lang.NullPointerException("callbackIntent can't be null");
            }
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.registerAppUsageLimitObserver(callingUid, i, strArr, j, j2, pendingIntent, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterAppUsageLimitObserver(int i, java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            android.app.admin.DevicePolicyManagerInternal dpmInternal = com.android.server.usage.UsageStatsService.this.getDpmInternal();
            if (!hasPermissions("android.permission.SUSPEND_APPS", "android.permission.OBSERVE_APP_USAGE") && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new java.lang.SecurityException("Caller must be the active supervision app or it must have both SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.usage.UsageStatsService.this.unregisterAppUsageLimitObserver(callingUid, i, userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void reportUsageStart(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) {
            reportPastUsageStart(iBinder, str, 0L, str2);
        }

        public void reportPastUsageStart(android.os.IBinder iBinder, java.lang.String str, long j, java.lang.String str2) {
            android.util.ArraySet<java.lang.String> arraySet;
            int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.usage.UsageStatsService.this.mUsageReporters) {
                    try {
                        arraySet = com.android.server.usage.UsageStatsService.this.mUsageReporters.get(iBinder.hashCode());
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet<>();
                            com.android.server.usage.UsageStatsService.this.mUsageReporters.put(iBinder.hashCode(), arraySet);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.add(str)) {
                        throw new java.lang.IllegalArgumentException(str + " for " + str2 + " is already reported as started for this activity");
                    }
                }
                com.android.server.usage.UsageStatsService.this.mAppTimeLimit.noteUsageStart(com.android.server.usage.UsageStatsService.this.buildFullToken(str2, str), userId, j);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void reportUsageStop(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) {
            android.util.ArraySet<java.lang.String> arraySet;
            int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.usage.UsageStatsService.this.mUsageReporters) {
                    arraySet = com.android.server.usage.UsageStatsService.this.mUsageReporters.get(iBinder.hashCode());
                    if (arraySet == null) {
                        throw new java.lang.IllegalArgumentException("Unknown reporter trying to stop token " + str + " for " + str2);
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.remove(str)) {
                        throw new java.lang.IllegalArgumentException(str + " for " + str2 + " is already reported as stopped for this activity");
                    }
                }
                com.android.server.usage.UsageStatsService.this.mAppTimeLimit.noteUsageStop(com.android.server.usage.UsageStatsService.this.buildFullToken(str2, str), userId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getUsageSource() {
            int i;
            if (!hasObserverPermission()) {
                throw new java.lang.SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            synchronized (com.android.server.usage.UsageStatsService.this.mLock) {
                i = com.android.server.usage.UsageStatsService.this.mUsageSource;
            }
            return i;
        }

        public void forceUsageSourceSettingRead() {
            com.android.server.usage.UsageStatsService.this.readUsageSourceSetting();
        }

        public long getLastTimeAnyComponentUsed(java.lang.String str, java.lang.String str2) {
            long longValue;
            if (!hasPermissions("android.permission.INTERACT_ACROSS_USERS")) {
                throw new java.lang.SecurityException("Caller doesn't have INTERACT_ACROSS_USERS permission");
            }
            if (!hasQueryPermission(str2)) {
                throw new java.lang.SecurityException("Don't have permission to query usage stats");
            }
            synchronized (com.android.server.usage.UsageStatsService.this.mLock) {
                longValue = (((java.lang.Long) com.android.server.usage.UsageStatsService.this.mLastTimeComponentUsedGlobal.getOrDefault(str, 0L)).longValue() / java.util.concurrent.TimeUnit.DAYS.toMillis(1L)) * java.util.concurrent.TimeUnit.DAYS.toMillis(1L);
            }
            return longValue;
        }

        @android.annotation.NonNull
        public android.app.usage.BroadcastResponseStatsList queryBroadcastResponseStats(@android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.lang.String str2, int i) {
            java.util.Objects.requireNonNull(str2);
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("id needs to be >=0");
            }
            com.android.server.usage.UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "queryBroadcastResponseStats");
            int callingUid = android.os.Binder.getCallingUid();
            return new android.app.usage.BroadcastResponseStatsList(com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.queryBroadcastResponseStats(callingUid, str, j, android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "queryBroadcastResponseStats", str2)));
        }

        public void clearBroadcastResponseStats(@android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull java.lang.String str2, int i) {
            java.util.Objects.requireNonNull(str2);
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("id needs to be >=0");
            }
            com.android.server.usage.UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "clearBroadcastResponseStats");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.clearBroadcastResponseStats(callingUid, str, j, android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "clearBroadcastResponseStats", str2));
        }

        public void clearBroadcastEvents(@android.annotation.NonNull java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            com.android.server.usage.UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.ACCESS_BROADCAST_RESPONSE_STATS", "clearBroadcastEvents");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.clearBroadcastEvents(callingUid, android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, false, "clearBroadcastResponseStats", str));
        }

        public boolean isPackageExemptedFromBroadcastResponseStats(@android.annotation.NonNull java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            com.android.server.usage.UsageStatsService.this.getContext().enforceCallingOrSelfPermission("android.permission.DUMP", "isPackageExemptedFromBroadcastResponseStats");
            return com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.isPackageExemptedFromBroadcastResponseStats(str, android.os.UserHandle.of(i));
        }

        @android.annotation.Nullable
        public java.lang.String getAppStandbyConstant(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            if (!hasPermissions("android.permission.READ_DEVICE_CONFIG")) {
                throw new java.lang.SecurityException("Caller doesn't have READ_DEVICE_CONFIG permission");
            }
            return com.android.server.usage.UsageStatsService.this.mAppStandby.getAppStandbyConstant(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
            return new com.android.server.usage.UsageStatsShellCommand(com.android.server.usage.UsageStatsService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }
    }

    void registerAppUsageObserver(int i, int i2, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, int i3) {
        this.mAppTimeLimit.addAppUsageObserver(i, i2, strArr, j, pendingIntent, i3);
    }

    void unregisterAppUsageObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeAppUsageObserver(i, i2, i3);
    }

    void registerUsageSessionObserver(int i, int i2, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, int i3) {
        this.mAppTimeLimit.addUsageSessionObserver(i, i2, strArr, j, j2, pendingIntent, pendingIntent2, i3);
    }

    void unregisterUsageSessionObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeUsageSessionObserver(i, i2, i3);
    }

    void registerAppUsageLimitObserver(int i, int i2, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, int i3) {
        this.mAppTimeLimit.addAppUsageLimitObserver(i, i2, strArr, j, j2, pendingIntent, i3);
    }

    void unregisterAppUsageLimitObserver(int i, int i2, int i3) {
        this.mAppTimeLimit.removeAppUsageLimitObserver(i, i2, i3);
    }

    private final class LocalService extends android.app.usage.UsageStatsManagerInternal {
        private LocalService() {
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportEvent(android.content.ComponentName componentName, int i, int i2, int i3, android.content.ComponentName componentName2) {
            if (componentName == null) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Event reported without a component name");
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(i2, android.os.SystemClock.elapsedRealtime());
            event.mPackage = componentName.getPackageName();
            event.mClass = componentName.getClassName();
            event.mInstanceId = i3;
            if (componentName2 == null) {
                event.mTaskRootPackage = null;
                event.mTaskRootClass = null;
            } else {
                event.mTaskRootPackage = componentName2.getPackageName();
                event.mTaskRootClass = componentName2.getClassName();
            }
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportEvent(java.lang.String str, int i, int i2) {
            if (str == null) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Event reported without a package name, eventType:" + i2);
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(i2, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str;
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportConfigurationChange(android.content.res.Configuration configuration, int i) {
            if (configuration == null) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Configuration event reported with a null config");
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(5, android.os.SystemClock.elapsedRealtime());
            event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
            event.mConfiguration = new android.content.res.Configuration(configuration);
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportInterruptiveNotification(java.lang.String str, java.lang.String str2, int i) {
            if (str == null || str2 == null) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Event reported without a package name or a channel ID");
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(12, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str.intern();
            event.mNotificationChannelId = str2.intern();
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportShortcutUsage(java.lang.String str, java.lang.String str2, int i) {
            if (str == null || str2 == null) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Event reported without a package name or a shortcut ID");
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(8, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str.intern();
            event.mShortcutId = str2.intern();
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportLocusUpdate(@android.annotation.NonNull android.content.ComponentName componentName, int i, @android.annotation.Nullable android.content.LocusId locusId, @android.annotation.NonNull android.os.IBinder iBinder) {
            if (locusId == null) {
                return;
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(30, android.os.SystemClock.elapsedRealtime());
            event.mLocusId = locusId.getId();
            event.mPackage = componentName.getPackageName();
            event.mClass = componentName.getClassName();
            event.mInstanceId = iBinder.hashCode();
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportContentProviderUsage(java.lang.String str, java.lang.String str2, int i) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.postReportContentProviderUsage(str, str2, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportUserInteractionEvent(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.PersistableBundle persistableBundle) {
            if (persistableBundle != null && persistableBundle.size() != 0) {
                java.lang.String string = persistableBundle.getString("android.app.usage.extra.EVENT_CATEGORY");
                java.lang.String string2 = persistableBundle.getString("android.app.usage.extra.EVENT_ACTION");
                persistableBundle.putString("android.app.usage.extra.EVENT_CATEGORY", com.android.server.usage.UsageStatsService.this.getTrimmedString(string));
                persistableBundle.putString("android.app.usage.extra.EVENT_ACTION", com.android.server.usage.UsageStatsService.this.getTrimmedString(string2));
            }
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(7, android.os.SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mExtras = persistableBundle;
            com.android.server.usage.UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean isAppIdle(java.lang.String str, int i, int i2) {
            return com.android.server.usage.UsageStatsService.this.mAppStandby.isAppIdleFiltered(str, i, i2, android.os.SystemClock.elapsedRealtime());
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public int getAppStandbyBucket(java.lang.String str, int i, long j) {
            return com.android.server.usage.UsageStatsService.this.mAppStandby.getAppStandbyBucket(str, i, j, false);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public int[] getIdleUidsForUser(int i) {
            return com.android.server.usage.UsageStatsService.this.mAppStandby.getIdleUidsForUser(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void prepareShutdown() {
            com.android.server.usage.UsageStatsService.this.shutdown();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void prepareForPossibleShutdown() {
            com.android.server.usage.UsageStatsService.this.prepareForPossibleShutdown();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public byte[] getBackupPayload(int i, java.lang.String str) {
            if (!com.android.server.usage.UsageStatsService.this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Failed to get backup payload for locked user " + i);
                return null;
            }
            synchronized (com.android.server.usage.UsageStatsService.this.mLock) {
                try {
                    com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = com.android.server.usage.UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                    if (userUsageStatsServiceLocked == null) {
                        return null;
                    }
                    android.util.Slog.i(com.android.server.usage.UsageStatsService.TAG, "Returning backup payload for u=" + i);
                    return userUsageStatsServiceLocked.getBackupPayload(str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void applyRestoredPayload(int i, java.lang.String str, byte[] bArr) {
            synchronized (com.android.server.usage.UsageStatsService.this.mLock) {
                try {
                    if (!com.android.server.usage.UsageStatsService.this.mUserUnlockedStates.contains(java.lang.Integer.valueOf(i))) {
                        android.util.Slog.w(com.android.server.usage.UsageStatsService.TAG, "Failed to apply restored payload for locked user " + i);
                        return;
                    }
                    com.android.server.usage.UserUsageStatsService userUsageStatsServiceLocked = com.android.server.usage.UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                    if (userUsageStatsServiceLocked == null) {
                        return;
                    }
                    com.android.server.usage.UsageStatsService.this.mAppStandby.restoreAppsToRare(userUsageStatsServiceLocked.applyRestoredPayload(str, bArr), i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public java.util.List<android.app.usage.UsageStats> queryUsageStatsForUser(int i, int i2, long j, long j2, boolean z) {
            return com.android.server.usage.UsageStatsService.this.queryUsageStats(i, i2, j, j2, z);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public android.app.usage.UsageEvents queryEventsForUser(int i, long j, long j2, int i2) {
            return com.android.server.usage.UsageStatsService.this.queryEvents(i, j, j2, i2);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setLastJobRunTime(java.lang.String str, int i, long j) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.setLastJobRunTime(str, i, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public long getEstimatedPackageLaunchTime(java.lang.String str, int i) {
            return com.android.server.usage.UsageStatsService.this.getEstimatedPackageLaunchTime(i, str);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public long getTimeSinceLastJobRun(java.lang.String str, int i) {
            return com.android.server.usage.UsageStatsService.this.mAppStandby.getTimeSinceLastJobRun(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportAppJobState(java.lang.String str, int i, int i2, long j) {
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void onActiveAdminAdded(java.lang.String str, int i) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.addActiveDeviceAdmin(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setActiveAdminApps(java.util.Set<java.lang.String> set, int i) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.setActiveAdminApps(set, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void setAdminProtectedPackages(java.util.Set<java.lang.String> set, int i) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.setAdminProtectedPackages(set, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void onAdminDataAvailable() {
            com.android.server.usage.UsageStatsService.this.mAppStandby.onAdminDataAvailable();
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportSyncScheduled(java.lang.String str, int i, boolean z) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.postReportSyncScheduled(str, i, z);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportExemptedSyncStart(java.lang.String str, int i) {
            com.android.server.usage.UsageStatsService.this.mAppStandby.postReportExemptedSyncStart(str, i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public android.app.usage.UsageStatsManagerInternal.AppUsageLimitData getAppUsageLimit(java.lang.String str, android.os.UserHandle userHandle) {
            return com.android.server.usage.UsageStatsService.this.mAppTimeLimit.getAppUsageLimit(str, userHandle);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean pruneUninstalledPackagesData(int i) {
            return com.android.server.usage.UsageStatsService.this.pruneUninstalledPackagesData(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public boolean updatePackageMappingsData(int i) {
            return com.android.server.usage.UsageStatsService.this.updatePackageMappingsData(i);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void registerListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.UsageEventListener usageEventListener) {
            com.android.server.usage.UsageStatsService.this.registerListener(usageEventListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void unregisterListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.UsageEventListener usageEventListener) {
            com.android.server.usage.UsageStatsService.this.unregisterListener(usageEventListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void registerLaunchTimeChangedListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
            com.android.server.usage.UsageStatsService.this.registerLaunchTimeChangedListener(estimatedLaunchTimeChangedListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void unregisterLaunchTimeChangedListener(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal.EstimatedLaunchTimeChangedListener estimatedLaunchTimeChangedListener) {
            com.android.server.usage.UsageStatsService.this.unregisterLaunchTimeChangedListener(estimatedLaunchTimeChangedListener);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportBroadcastDispatched(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, long j, long j2, int i2) {
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.reportBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationPosted(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, long j) {
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.reportNotificationPosted(str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationUpdated(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, long j) {
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.reportNotificationUpdated(str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public void reportNotificationRemoved(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, long j) {
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.reportNotificationCancelled(str, userHandle, j);
        }
    }

    private class MyPackageMonitor extends com.android.internal.content.PackageMonitor {
        private MyPackageMonitor() {
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            int changingUserId = getChangingUserId();
            if (com.android.server.usage.UsageStatsService.this.shouldDeleteObsoleteData(android.os.UserHandle.of(changingUserId))) {
                com.android.server.usage.UsageStatsService.this.mHandler.obtainMessage(6, changingUserId, 0, str).sendToTarget();
            }
            com.android.server.usage.UsageStatsService.this.mResponseStatsTracker.onPackageRemoved(str, android.os.UserHandle.getUserId(i));
            super.onPackageRemoved(str, i);
        }
    }
}
