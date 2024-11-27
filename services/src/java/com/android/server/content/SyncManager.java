package com.android.server.content;

/* loaded from: classes.dex */
public class SyncManager {
    private static final boolean DEBUG_ACCOUNT_ACCESS = false;
    private static final int DELAY_RETRY_SYNC_IN_PROGRESS_IN_SECONDS = 10;
    private static final java.lang.String HANDLE_SYNC_ALARM_WAKE_LOCK = "SyncManagerHandleSyncAlarm";
    private static final long SYNC_DELAY_ON_CONFLICT = 10000;
    private static final java.lang.String SYNC_LOOP_WAKE_LOCK = "SyncLoopWakeLock";
    private static final int SYNC_MONITOR_PROGRESS_THRESHOLD_BYTES = 10;
    private static final long SYNC_MONITOR_WINDOW_LENGTH_MILLIS = 60000;
    private static final int SYNC_OP_STATE_INVALID_NOT_SYNCABLE = 4;
    private static final int SYNC_OP_STATE_INVALID_NO_ACCOUNT = 3;
    private static final int SYNC_OP_STATE_INVALID_NO_ACCOUNT_ACCESS = 2;
    private static final int SYNC_OP_STATE_INVALID_SYNC_DISABLED = 5;
    private static final int SYNC_OP_STATE_VALID = 0;
    private static final java.lang.String SYNC_WAKE_LOCK_PREFIX = "*sync*/";
    static final java.lang.String TAG = "SyncManager";
    private static final boolean USE_WTF_FOR_ACCOUNT_ERROR = true;

    @com.android.internal.annotations.GuardedBy({"SyncManager.class"})
    private static com.android.server.content.SyncManager sInstance;
    private final android.accounts.AccountManager mAccountManager;
    private final android.accounts.AccountManagerInternal mAccountManagerInternal;
    private final android.app.ActivityManagerInternal mAmi;
    private final com.android.internal.config.appcloning.AppCloningDeviceConfigHelper mAppCloningDeviceConfigHelper;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private android.net.ConnectivityManager mConnManagerDoNotUseDirectly;
    private final com.android.server.content.SyncManagerConstants mConstants;
    private android.content.Context mContext;
    private android.app.job.JobScheduler mJobScheduler;
    private final com.android.server.content.SyncLogger mLogger;
    private final android.app.NotificationManager mNotificationMgr;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final android.os.PowerManager mPowerManager;
    private volatile boolean mProvisioned;
    protected final android.content.SyncAdaptersCache mSyncAdapters;
    private final com.android.server.content.SyncManager.SyncHandler mSyncHandler;
    private volatile android.os.PowerManager.WakeLock mSyncManagerWakeLock;
    private com.android.server.content.SyncStorageEngine mSyncStorageEngine;
    private final android.os.HandlerThread mThread;
    private final android.os.UserManager mUserManager;
    private static final boolean ENABLE_SUSPICIOUS_CHECK = android.os.Build.IS_DEBUGGABLE;
    private static final long LOCAL_SYNC_DELAY = android.os.SystemProperties.getLong("sync.local_sync_delay", 30000);
    private static final android.content.Context.BindServiceFlags SYNC_ADAPTER_CONNECTION_FLAGS = android.content.Context.BindServiceFlags.of(21);
    private static final android.accounts.AccountAndUser[] INITIAL_ACCOUNTS_ARRAY = new android.accounts.AccountAndUser[0];
    private static final java.util.Comparator<com.android.server.content.SyncOperation> sOpDumpComparator = new java.util.Comparator() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda2
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$static$6;
            lambda$static$6 = com.android.server.content.SyncManager.lambda$static$6((com.android.server.content.SyncOperation) obj, (com.android.server.content.SyncOperation) obj2);
            return lambda$static$6;
        }
    };
    private static final java.util.Comparator<com.android.server.content.SyncOperation> sOpRuntimeComparator = new java.util.Comparator() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda3
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$static$7;
            lambda$static$7 = com.android.server.content.SyncManager.lambda$static$7((com.android.server.content.SyncOperation) obj, (com.android.server.content.SyncOperation) obj2);
            return lambda$static$7;
        }
    };
    private final java.lang.Object mAccountsLock = new java.lang.Object();
    private volatile android.accounts.AccountAndUser[] mRunningAccounts = INITIAL_ACCOUNTS_ARRAY;
    private volatile boolean mDataConnectionIsConnected = false;
    private volatile int mNextJobId = 0;
    protected final java.util.ArrayList<com.android.server.content.SyncManager.ActiveSyncContext> mActiveSyncContexts = com.google.android.collect.Lists.newArrayList();
    private final android.content.BroadcastReceiver mAccountsUpdatedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.SyncManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.content.SyncManager.this.updateRunningAccounts(new com.android.server.content.SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
        }
    };
    private android.content.BroadcastReceiver mConnectivityIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.SyncManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z = com.android.server.content.SyncManager.this.mDataConnectionIsConnected;
            com.android.server.content.SyncManager.this.mDataConnectionIsConnected = com.android.server.content.SyncManager.this.readDataConnectionState();
            if (com.android.server.content.SyncManager.this.mDataConnectionIsConnected && !z) {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                }
                com.android.server.content.SyncManager.this.clearAllBackoffs("network reconnect");
            }
        }
    };
    private android.content.BroadcastReceiver mShutdownIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.SyncManager.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.util.Log.w("SyncManager", "Writing sync state before shutdown...");
            com.android.server.content.SyncManager.this.getSyncStorageEngine().writeAllState();
            com.android.server.content.SyncManager.this.mLogger.log(com.android.server.content.SyncManager.this.getJobStats());
            com.android.server.content.SyncManager.this.mLogger.log("Shutting down.");
        }
    };
    private final android.content.BroadcastReceiver mOtherIntentsReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.SyncManager.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                com.android.server.content.SyncManager.this.mSyncStorageEngine.setClockValid();
            }
        }
    };
    private android.content.BroadcastReceiver mUserIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.SyncManager.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            if (intExtra == -10000) {
                return;
            }
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                com.android.server.content.SyncManager.this.onUserRemoved(intExtra);
            } else if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                com.android.server.content.SyncManager.this.onUserUnlocked(intExtra);
            } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                com.android.server.content.SyncManager.this.onUserStopped(intExtra);
            }
        }
    };

    @com.android.internal.annotations.GuardedBy({"mUnlockedUsers"})
    private final android.util.SparseBooleanArray mUnlockedUsers = new android.util.SparseBooleanArray();

    interface OnReadyCallback {
        void onReady();
    }

    private boolean isJobIdInUseLockedH(int i, java.util.List<android.app.job.JobInfo> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (list.get(i2).getId() == i) {
                return true;
            }
        }
        int size2 = this.mActiveSyncContexts.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (this.mActiveSyncContexts.get(i3).mSyncOperation.jobId == i) {
                return true;
            }
        }
        return false;
    }

    private int getUnusedJobIdH() {
        java.util.List<android.app.job.JobInfo> allPendingJobs = this.mJobScheduler.getAllPendingJobs();
        while (isJobIdInUseLockedH(this.mNextJobId, allPendingJobs)) {
            this.mNextJobId++;
        }
        return this.mNextJobId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<com.android.server.content.SyncOperation> getAllPendingSyncs() {
        verifyJobScheduler();
        java.util.List<android.app.job.JobInfo> allPendingJobs = this.mJobScheduler.getAllPendingJobs();
        int size = allPendingJobs.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            com.android.server.content.SyncOperation maybeCreateFromJobExtras = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(allPendingJobs.get(i).getExtras());
            if (maybeCreateFromJobExtras != null) {
                arrayList.add(maybeCreateFromJobExtras);
            } else {
                android.util.Slog.wtf("SyncManager", "Non-sync job inside of SyncManager's namespace");
            }
        }
        return arrayList;
    }

    private java.util.List<android.content.pm.UserInfo> getAllUsers() {
        return this.mUserManager.getUsers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean containsAccountAndUser(android.accounts.AccountAndUser[] accountAndUserArr, android.accounts.Account account, int i) {
        for (int i2 = 0; i2 < accountAndUserArr.length; i2++) {
            if (accountAndUserArr[i2].userId == i && accountAndUserArr[i2].account.equals(account)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRunningAccounts(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "sending MESSAGE_ACCOUNTS_UPDATED");
        }
        android.os.Message obtainMessage = this.mSyncHandler.obtainMessage(9);
        obtainMessage.obj = endPoint;
        obtainMessage.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeStaleAccounts() {
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getAliveUsers()) {
            if (!userInfo.partial) {
                this.mSyncStorageEngine.removeStaleAccounts(com.android.server.accounts.AccountManagerService.getSingleton().getAccounts(userInfo.id, this.mContext.getOpPackageName()), userInfo.id);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAllBackoffs(java.lang.String str) {
        this.mSyncStorageEngine.clearAllBackoffsLocked();
        rescheduleSyncs(com.android.server.content.SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readDataConnectionState() {
        android.net.NetworkInfo activeNetworkInfo = getConnectivityManager().getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getJobStats() {
        com.android.server.job.JobSchedulerInternal jobSchedulerInternal = (com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("JobStats: ");
        sb.append(jobSchedulerInternal == null ? "(JobSchedulerInternal==null)" : jobSchedulerInternal.getPersistStats().toString());
        return sb.toString();
    }

    private static class PackageMonitorImpl extends com.android.internal.content.PackageMonitor {
        private PackageMonitorImpl() {
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z, android.os.Bundle bundle) {
            if (android.util.Log.isLoggable("SyncManager", 3)) {
                android.util.Log.d("SyncManager", "Package force-stopped: " + java.util.Arrays.toString(strArr) + ", uid: " + i);
                return false;
            }
            return false;
        }

        public void onPackageUnstopped(java.lang.String str, int i, android.os.Bundle bundle) {
            if (android.util.Log.isLoggable("SyncManager", 3)) {
                android.util.Log.d("SyncManager", "Package unstopped: " + str + ", uid: " + i);
            }
        }
    }

    private android.net.ConnectivityManager getConnectivityManager() {
        android.net.ConnectivityManager connectivityManager;
        synchronized (this) {
            try {
                if (this.mConnManagerDoNotUseDirectly == null) {
                    this.mConnManagerDoNotUseDirectly = (android.net.ConnectivityManager) this.mContext.getSystemService("connectivity");
                }
                connectivityManager = this.mConnManagerDoNotUseDirectly;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return connectivityManager;
    }

    private void cleanupJobs() {
        this.mSyncHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: com.android.server.content.SyncManager.6
            @Override // java.lang.Runnable
            public void run() {
                java.util.List<com.android.server.content.SyncOperation> allPendingSyncs = com.android.server.content.SyncManager.this.getAllPendingSyncs();
                java.util.HashSet hashSet = new java.util.HashSet();
                for (com.android.server.content.SyncOperation syncOperation : allPendingSyncs) {
                    if (!hashSet.contains(syncOperation.key)) {
                        hashSet.add(syncOperation.key);
                        for (com.android.server.content.SyncOperation syncOperation2 : allPendingSyncs) {
                            if (syncOperation != syncOperation2 && syncOperation.key.equals(syncOperation2.key)) {
                                com.android.server.content.SyncManager.this.mLogger.log("Removing duplicate sync: ", syncOperation2);
                                com.android.server.content.SyncManager.this.cancelJob(syncOperation2, "cleanupJobs() x=" + syncOperation + " y=" + syncOperation2);
                            }
                        }
                    }
                }
            }
        });
    }

    private void migrateSyncJobNamespaceIfNeeded() {
        boolean isJobNamespaceMigrated = this.mSyncStorageEngine.isJobNamespaceMigrated();
        boolean isJobAttributionFixed = this.mSyncStorageEngine.isJobAttributionFixed();
        if (isJobNamespaceMigrated && isJobAttributionFixed) {
            return;
        }
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) this.mContext.getSystemService(android.app.job.JobScheduler.class);
        boolean z = true;
        if (!isJobNamespaceMigrated) {
            java.util.List<android.app.job.JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
            boolean z2 = true;
            for (int size = allPendingJobs.size() - 1; size >= 0; size--) {
                android.app.job.JobInfo jobInfo = allPendingJobs.get(size);
                com.android.server.content.SyncOperation maybeCreateFromJobExtras = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(jobInfo.getExtras());
                if (maybeCreateFromJobExtras != null) {
                    this.mJobScheduler.scheduleAsPackage(jobInfo, maybeCreateFromJobExtras.owningPackage, maybeCreateFromJobExtras.target.userId, maybeCreateFromJobExtras.wakeLockName());
                    jobScheduler.cancel(jobInfo.getId());
                    z2 = false;
                }
            }
            this.mSyncStorageEngine.setJobNamespaceMigrated(z2);
        }
        java.util.List systemScheduledOwnJobs = ((com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class)).getSystemScheduledOwnJobs(this.mJobScheduler.getNamespace());
        for (int size2 = systemScheduledOwnJobs.size() - 1; size2 >= 0; size2--) {
            android.app.job.JobInfo jobInfo2 = (android.app.job.JobInfo) systemScheduledOwnJobs.get(size2);
            com.android.server.content.SyncOperation maybeCreateFromJobExtras2 = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(jobInfo2.getExtras());
            if (maybeCreateFromJobExtras2 != null) {
                this.mJobScheduler.scheduleAsPackage(jobInfo2, maybeCreateFromJobExtras2.owningPackage, maybeCreateFromJobExtras2.target.userId, maybeCreateFromJobExtras2.wakeLockName());
                z = false;
            }
        }
        this.mSyncStorageEngine.setJobAttributionFixed(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void verifyJobScheduler() {
        if (this.mJobScheduler != null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Log.d("SyncManager", "initializing JobScheduler object.");
            }
            this.mJobScheduler = ((android.app.job.JobScheduler) this.mContext.getSystemService(android.app.job.JobScheduler.class)).forNamespace("SyncManager");
            migrateSyncJobNamespaceIfNeeded();
            java.util.List<android.app.job.JobInfo> allPendingJobs = this.mJobScheduler.getAllPendingJobs();
            java.util.Iterator<android.app.job.JobInfo> it = allPendingJobs.iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                com.android.server.content.SyncOperation maybeCreateFromJobExtras = com.android.server.content.SyncOperation.maybeCreateFromJobExtras(it.next().getExtras());
                if (maybeCreateFromJobExtras != null) {
                    if (maybeCreateFromJobExtras.isPeriodic) {
                        i++;
                    } else {
                        i2++;
                        this.mSyncStorageEngine.markPending(maybeCreateFromJobExtras.target, true);
                    }
                } else {
                    android.util.Slog.wtf("SyncManager", "Non-sync job inside of SyncManager namespace");
                }
            }
            java.lang.String str = "Loaded persisted syncs: " + i + " periodic syncs, " + i2 + " oneshot syncs, " + allPendingJobs.size() + " total system server jobs, " + getJobStats();
            android.util.Slog.i("SyncManager", str);
            this.mLogger.log(str);
            cleanupJobs();
            if (ENABLE_SUSPICIOUS_CHECK && i == 0 && likelyHasPeriodicSyncs()) {
                android.util.Slog.wtf("SyncManager", "Device booted with no persisted periodic syncs: " + str);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean likelyHasPeriodicSyncs() {
        try {
            return this.mSyncStorageEngine.getAuthorityCount() >= 6;
        } catch (java.lang.Throwable th) {
            return false;
        }
    }

    private android.app.job.JobScheduler getJobScheduler() {
        verifyJobScheduler();
        return this.mJobScheduler;
    }

    public SyncManager(android.content.Context context, boolean z) {
        synchronized (com.android.server.content.SyncManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = this;
                } else {
                    android.util.Slog.wtf("SyncManager", "SyncManager instantiated multiple times");
                }
            } finally {
            }
        }
        this.mContext = context;
        this.mLogger = com.android.server.content.SyncLogger.getInstance();
        com.android.server.content.SyncStorageEngine.init(context, com.android.internal.os.BackgroundThread.get().getLooper());
        this.mSyncStorageEngine = com.android.server.content.SyncStorageEngine.getSingleton();
        this.mSyncStorageEngine.setOnSyncRequestListener(new com.android.server.content.SyncStorageEngine.OnSyncRequestListener() { // from class: com.android.server.content.SyncManager.7
            @Override // com.android.server.content.SyncStorageEngine.OnSyncRequestListener
            public void onSyncRequest(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, android.os.Bundle bundle, int i2, int i3, int i4) {
                com.android.server.content.SyncManager.this.scheduleSync(endPoint.account, endPoint.userId, i, endPoint.provider, bundle, -2, i2, i3, i4, null);
            }
        });
        this.mSyncStorageEngine.setPeriodicSyncAddedListener(new com.android.server.content.SyncStorageEngine.PeriodicSyncAddedListener() { // from class: com.android.server.content.SyncManager.8
            @Override // com.android.server.content.SyncStorageEngine.PeriodicSyncAddedListener
            public void onPeriodicSyncAdded(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, long j, long j2) {
                com.android.server.content.SyncManager.this.updateOrAddPeriodicSync(endPoint, j, j2, bundle);
            }
        });
        this.mSyncStorageEngine.setOnAuthorityRemovedListener(new com.android.server.content.SyncStorageEngine.OnAuthorityRemovedListener() { // from class: com.android.server.content.SyncManager.9
            @Override // com.android.server.content.SyncStorageEngine.OnAuthorityRemovedListener
            public void onAuthorityRemoved(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
                com.android.server.content.SyncManager.this.removeSyncsForAuthority(endPoint, "onAuthorityRemoved");
            }
        });
        this.mSyncAdapters = new android.content.SyncAdaptersCache(this.mContext);
        this.mThread = new android.os.HandlerThread("SyncManager", 10);
        this.mThread.start();
        this.mSyncHandler = new com.android.server.content.SyncManager.SyncHandler(this.mThread.getLooper());
        this.mSyncAdapters.setListener(new android.content.pm.RegisteredServicesCacheListener<android.content.SyncAdapterType>() { // from class: com.android.server.content.SyncManager.10
            public void onServiceChanged(android.content.SyncAdapterType syncAdapterType, int i, boolean z2) {
                if (!z2) {
                    com.android.server.content.SyncManager.this.scheduleSync(null, -1, -3, syncAdapterType.authority, null, -2, 0, android.os.Process.myUid(), -1, null);
                }
            }
        }, this.mSyncHandler);
        this.mConstants = new com.android.server.content.SyncManagerConstants(context);
        this.mAppCloningDeviceConfigHelper = com.android.internal.config.appcloning.AppCloningDeviceConfigHelper.getInstance(context);
        context.registerReceiver(this.mConnectivityIntentReceiver, new android.content.IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.setPriority(1000);
        context.registerReceiver(this.mShutdownIntentReceiver, intentFilter);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        this.mContext.registerReceiverAsUser(this.mUserIntentReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        byte b = 0;
        new com.android.server.content.SyncManager.PackageMonitorImpl().register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, false);
        context.registerReceiver(this.mOtherIntentsReceiver, new android.content.IntentFilter("android.intent.action.TIME_SET"));
        if (!z) {
            this.mNotificationMgr = (android.app.NotificationManager) context.getSystemService("notification");
        } else {
            this.mNotificationMgr = null;
        }
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService("user");
        this.mAccountManager = (android.accounts.AccountManager) this.mContext.getSystemService("account");
        this.mAccountManagerInternal = getAccountManagerInternal();
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mAmi = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mAccountManagerInternal.addOnAppPermissionChangeListener(new android.accounts.AccountManagerInternal.OnAppPermissionChangeListener() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda1
            public final void onAppPermissionChanged(android.accounts.Account account, int i) {
                com.android.server.content.SyncManager.this.lambda$new$0(account, i);
            }
        });
        this.mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        this.mSyncManagerWakeLock = this.mPowerManager.newWakeLock(1, SYNC_LOOP_WAKE_LOCK);
        this.mSyncManagerWakeLock.setReferenceCounted(false);
        this.mProvisioned = isDeviceProvisioned();
        if (!this.mProvisioned) {
            final android.content.ContentResolver contentResolver = context.getContentResolver();
            android.database.ContentObserver contentObserver = new android.database.ContentObserver(b == true ? 1 : 0) { // from class: com.android.server.content.SyncManager.11
                @Override // android.database.ContentObserver
                public void onChange(boolean z2) {
                    com.android.server.content.SyncManager.this.mProvisioned |= com.android.server.content.SyncManager.this.isDeviceProvisioned();
                    if (com.android.server.content.SyncManager.this.mProvisioned) {
                        contentResolver.unregisterContentObserver(this);
                    }
                }
            };
            synchronized (this.mSyncHandler) {
                try {
                    contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("device_provisioned"), false, contentObserver);
                    this.mProvisioned |= isDeviceProvisioned();
                    if (this.mProvisioned) {
                        contentResolver.unregisterContentObserver(contentObserver);
                    }
                } finally {
                }
            }
        }
        if (!z) {
            this.mContext.registerReceiverAsUser(this.mAccountsUpdatedReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter("android.accounts.LOGIN_ACCOUNTS_CHANGED"), null, null);
        }
        allowListExistingSyncAdaptersIfNeeded();
        this.mLogger.log("Sync manager initialized: " + android.os.Build.FINGERPRINT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.accounts.Account account, int i) {
        if (this.mAccountManagerInternal.hasAccountAccess(account, i)) {
            scheduleSync(account, android.os.UserHandle.getUserId(i), -2, null, null, 3, 0, android.os.Process.myUid(), -2, null);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.accounts.AccountManagerInternal getAccountManagerInternal() {
        return (android.accounts.AccountManagerInternal) com.android.server.LocalServices.getService(android.accounts.AccountManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStartUser$1(int i) {
        this.mLogger.log("onStartUser: user=", java.lang.Integer.valueOf(i));
    }

    public void onStartUser(final int i) {
        this.mSyncHandler.post(new java.lang.Runnable() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.content.SyncManager.this.lambda$onStartUser$1(i);
            }
        });
    }

    public void onUnlockUser(final int i) {
        synchronized (this.mUnlockedUsers) {
            this.mUnlockedUsers.put(i, true);
        }
        this.mSyncHandler.post(new java.lang.Runnable() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.content.SyncManager.this.lambda$onUnlockUser$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnlockUser$2(int i) {
        this.mLogger.log("onUnlockUser: user=", java.lang.Integer.valueOf(i));
    }

    public void onStopUser(final int i) {
        synchronized (this.mUnlockedUsers) {
            this.mUnlockedUsers.put(i, false);
        }
        this.mSyncHandler.post(new java.lang.Runnable() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.content.SyncManager.this.lambda$onStopUser$3(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStopUser$3(int i) {
        this.mLogger.log("onStopUser: user=", java.lang.Integer.valueOf(i));
    }

    private boolean isUserUnlocked(int i) {
        boolean z;
        synchronized (this.mUnlockedUsers) {
            z = this.mUnlockedUsers.get(i);
        }
        return z;
    }

    public void onBootPhase(int i) {
        switch (i) {
            case 550:
                this.mConstants.start();
                break;
        }
    }

    private void allowListExistingSyncAdaptersIfNeeded() {
        com.android.server.content.SyncManager syncManager = this;
        if (!syncManager.mSyncStorageEngine.shouldGrantSyncAdaptersAccountAccess()) {
            return;
        }
        java.util.List aliveUsers = syncManager.mUserManager.getAliveUsers();
        int size = aliveUsers.size();
        int i = 0;
        while (i < size) {
            android.os.UserHandle userHandle = ((android.content.pm.UserInfo) aliveUsers.get(i)).getUserHandle();
            int identifier = userHandle.getIdentifier();
            for (android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo : syncManager.mSyncAdapters.getAllServices(identifier)) {
                java.lang.String packageName = serviceInfo.componentName.getPackageName();
                android.accounts.Account[] accountsByTypeAsUser = syncManager.mAccountManager.getAccountsByTypeAsUser(((android.content.SyncAdapterType) serviceInfo.type).accountType, userHandle);
                int length = accountsByTypeAsUser.length;
                int i2 = 0;
                while (i2 < length) {
                    android.accounts.Account account = accountsByTypeAsUser[i2];
                    if (!syncManager.canAccessAccount(account, packageName, identifier)) {
                        syncManager.mAccountManager.updateAppPermission(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", serviceInfo.uid, true);
                    }
                    i2++;
                    syncManager = this;
                }
                syncManager = this;
            }
            i++;
            syncManager = this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceProvisioned() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    private long jitterize(long j, long j2) {
        java.util.Random random = new java.util.Random(android.os.SystemClock.elapsedRealtime());
        if (j2 - j > 2147483647L) {
            throw new java.lang.IllegalArgumentException("the difference between the maxValue and the minValue must be less than 2147483647");
        }
        return j + random.nextInt((int) r6);
    }

    public com.android.server.content.SyncStorageEngine getSyncStorageEngine() {
        return this.mSyncStorageEngine;
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private boolean areContactWritesEnabledForUser(android.content.pm.UserInfo userInfo) {
        try {
            return !android.os.UserManager.get(this.mContext).getUserProperties(userInfo.getUserHandle()).getUseParentsContacts();
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w("SyncManager", "Trying to fetch user properties for non-existing/partial user " + userInfo.getUserHandle());
            return false;
        }
    }

    protected boolean isContactSharingAllowedForCloneProfile() {
        return this.mContext.getResources().getBoolean(android.R.bool.config_enableActivityRecognitionHardwareOverlay) && this.mAppCloningDeviceConfigHelper.getEnableAppCloningBuildingBlocks();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean shouldDisableSyncForUser(android.content.pm.UserInfo userInfo, java.lang.String str) {
        return (userInfo == null || str == null || !isContactSharingAllowedForCloneProfile() || !str.equals("com.android.contacts") || areContactWritesEnabledForUser(userInfo)) ? false : true;
    }

    private int getIsSyncable(android.accounts.Account account, int i, java.lang.String str) {
        int isSyncable = this.mSyncStorageEngine.getIsSyncable(account, i, str);
        android.content.pm.UserInfo userInfo = android.os.UserManager.get(this.mContext).getUserInfo(i);
        if (shouldDisableSyncForUser(userInfo, str)) {
            android.util.Log.w("SyncManager", "Account sync is disabled for account: " + account + " userId: " + i + " provider: " + str);
            return 0;
        }
        if (userInfo == null || !userInfo.isRestricted()) {
            return isSyncable;
        }
        android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = this.mSyncAdapters.getServiceInfo(android.content.SyncAdapterType.newKey(str, account.type), i);
        if (serviceInfo == null) {
            return 0;
        }
        try {
            android.content.pm.PackageInfo packageInfo = android.app.AppGlobals.getPackageManager().getPackageInfo(serviceInfo.componentName.getPackageName(), 0L, i);
            if (packageInfo == null || packageInfo.restrictedAccountType == null || !packageInfo.restrictedAccountType.equals(account.type)) {
                return 0;
            }
            return isSyncable;
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAuthorityPendingState(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                getSyncStorageEngine().markPending(endPoint, true);
                return;
            }
        }
        getSyncStorageEngine().markPending(endPoint, false);
    }

    public void scheduleSync(android.accounts.Account account, int i, int i2, java.lang.String str, android.os.Bundle bundle, int i3, int i4, int i5, int i6, java.lang.String str2) {
        scheduleSync(account, i, i2, str, bundle, i3, 0L, true, i4, i5, i6, str2);
    }

    private void scheduleSync(android.accounts.Account account, final int i, final int i2, java.lang.String str, android.os.Bundle bundle, final int i3, final long j, boolean z, final int i4, final int i5, final int i6, final java.lang.String str2) {
        android.os.Bundle bundle2;
        int i7;
        android.os.Bundle bundle3;
        char c;
        boolean z2;
        int i8;
        android.accounts.AccountAndUser[] accountAndUserArr;
        java.lang.String str3;
        int i9;
        com.android.server.content.SyncManager syncManager;
        android.accounts.AccountAndUser accountAndUser;
        int i10;
        char c2;
        android.os.Bundle bundle4;
        com.android.server.content.SyncManager syncManager2;
        android.accounts.AccountAndUser accountAndUser2;
        if (bundle != null) {
            bundle2 = bundle;
        } else {
            bundle2 = new android.os.Bundle();
        }
        bundle2.size();
        if (!android.util.Log.isLoggable("SyncManager", 2)) {
            i7 = 2;
            bundle3 = bundle2;
        } else {
            i7 = 2;
            bundle3 = bundle2;
            this.mLogger.log("scheduleSync: account=", account, " u", java.lang.Integer.valueOf(i), " authority=", str, " reason=", java.lang.Integer.valueOf(i2), " extras=", bundle3, " cuid=", java.lang.Integer.valueOf(i5), " cpid=", java.lang.Integer.valueOf(i6), " cpkg=", str2, " mdm=", java.lang.Long.valueOf(j), " ciar=", java.lang.Boolean.valueOf(z), " sef=", java.lang.Integer.valueOf(i4));
        }
        com.android.server.content.SyncManager syncManager3 = this;
        synchronized (syncManager3.mAccountsLock) {
            c = 65535;
            z2 = false;
            try {
                if (account != null) {
                    i8 = i;
                    if (i8 != -1) {
                        accountAndUserArr = new android.accounts.AccountAndUser[]{new android.accounts.AccountAndUser(account, i8)};
                    } else {
                        android.accounts.AccountAndUser[] accountAndUserArr2 = null;
                        for (android.accounts.AccountAndUser accountAndUser3 : syncManager3.mRunningAccounts) {
                            if (account.equals(accountAndUser3.account)) {
                                accountAndUserArr2 = (android.accounts.AccountAndUser[]) com.android.internal.util.ArrayUtils.appendElement(android.accounts.AccountAndUser.class, accountAndUserArr2, accountAndUser3);
                            }
                        }
                        accountAndUserArr = accountAndUserArr2;
                    }
                } else {
                    i8 = i;
                    accountAndUserArr = syncManager3.mRunningAccounts;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(accountAndUserArr)) {
            return;
        }
        android.os.Bundle bundle5 = bundle3;
        boolean z3 = bundle5.getBoolean("upload", false);
        boolean z4 = bundle5.getBoolean("force", false);
        if (z4) {
            bundle5.putBoolean("ignore_backoff", true);
            bundle5.putBoolean("ignore_settings", true);
        }
        boolean z5 = bundle5.getBoolean("ignore_settings", false);
        int i11 = 3;
        if (z3) {
            str3 = str;
            i9 = 1;
        } else if (z4) {
            str3 = str;
            i9 = 3;
        } else {
            str3 = str;
            if (str3 == null) {
                i9 = i7;
            } else if (bundle5.containsKey("feed")) {
                i9 = 5;
            } else {
                i9 = 0;
            }
        }
        int length = accountAndUserArr.length;
        int i12 = 0;
        while (i12 < length) {
            android.accounts.AccountAndUser accountAndUser4 = accountAndUserArr[i12];
            if (i8 < 0 || accountAndUser4.userId < 0 || i8 == accountAndUser4.userId) {
                java.util.HashSet hashSet = new java.util.HashSet();
                java.util.Iterator it = syncManager3.mSyncAdapters.getAllServices(accountAndUser4.userId).iterator();
                while (it.hasNext()) {
                    hashSet.add(((android.content.SyncAdapterType) ((android.content.pm.RegisteredServicesCache.ServiceInfo) it.next()).type).authority);
                }
                if (str3 != null) {
                    boolean contains = hashSet.contains(str3);
                    hashSet.clear();
                    if (contains) {
                        hashSet.add(str3);
                    }
                }
                java.util.Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    final java.lang.String str4 = (java.lang.String) it2.next();
                    final android.accounts.AccountAndUser accountAndUser5 = accountAndUser4;
                    int i13 = i12;
                    int computeSyncable = computeSyncable(accountAndUser4.account, accountAndUser4.userId, str4, !z, true);
                    if (computeSyncable == 0) {
                        accountAndUser4 = accountAndUser5;
                        i12 = i13;
                        c = 65535;
                    } else {
                        android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = syncManager3.mSyncAdapters.getServiceInfo(android.content.SyncAdapterType.newKey(str4, accountAndUser5.account.type), accountAndUser5.userId);
                        if (serviceInfo == null) {
                            accountAndUser4 = accountAndUser5;
                            i12 = i13;
                            c = 65535;
                        } else {
                            int i14 = serviceInfo.uid;
                            if (computeSyncable == i11) {
                                syncManager3.mLogger.log("scheduleSync: Not scheduling sync operation: isSyncable == SYNCABLE_NO_ACCOUNT_ACCESS");
                                final android.os.Bundle bundle6 = new android.os.Bundle(bundle5);
                                java.lang.String packageName = serviceInfo.componentName.getPackageName();
                                if (syncManager3.wasPackageEverLaunched(packageName, i8)) {
                                    syncManager3.mAccountManagerInternal.requestAccountAccess(accountAndUser5.account, packageName, i, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda6
                                        public final void onResult(android.os.Bundle bundle7) {
                                            com.android.server.content.SyncManager.this.lambda$scheduleSync$4(accountAndUser5, i, i2, str4, bundle6, i3, j, i4, i5, i6, str2, bundle7);
                                        }
                                    }));
                                    c = 65535;
                                    accountAndUser4 = accountAndUser5;
                                    i8 = i;
                                    i12 = i13;
                                    length = length;
                                    bundle5 = bundle5;
                                    i11 = i11;
                                    accountAndUserArr = accountAndUserArr;
                                    z2 = z2;
                                    syncManager3 = this;
                                } else {
                                    accountAndUser4 = accountAndUser5;
                                    i12 = i13;
                                    c = 65535;
                                }
                            } else {
                                int i15 = length;
                                android.os.Bundle bundle7 = bundle5;
                                int i16 = i11;
                                android.accounts.AccountAndUser[] accountAndUserArr3 = accountAndUserArr;
                                int i17 = i8;
                                boolean z6 = z2;
                                boolean allowParallelSyncs = ((android.content.SyncAdapterType) serviceInfo.type).allowParallelSyncs();
                                boolean isAlwaysSyncable = ((android.content.SyncAdapterType) serviceInfo.type).isAlwaysSyncable();
                                if (z || computeSyncable >= 0 || !isAlwaysSyncable) {
                                    syncManager = this;
                                    accountAndUser = accountAndUser5;
                                    i10 = computeSyncable;
                                } else {
                                    syncManager = this;
                                    accountAndUser = accountAndUser5;
                                    syncManager.mSyncStorageEngine.setIsSyncable(accountAndUser.account, accountAndUser.userId, str4, 1, i5, i6);
                                    i10 = 1;
                                }
                                if (i3 != -2 && i3 != i10) {
                                    i8 = i17;
                                    syncManager3 = syncManager;
                                    accountAndUser4 = accountAndUser;
                                    i12 = i13;
                                    length = i15;
                                    bundle5 = bundle7;
                                    i11 = i16;
                                    accountAndUserArr = accountAndUserArr3;
                                    z2 = z6;
                                    c = 65535;
                                } else if (!((android.content.SyncAdapterType) serviceInfo.type).supportsUploading() && z3) {
                                    i8 = i17;
                                    syncManager3 = syncManager;
                                    accountAndUser4 = accountAndUser;
                                    i12 = i13;
                                    length = i15;
                                    bundle5 = bundle7;
                                    i11 = i16;
                                    accountAndUserArr = accountAndUserArr3;
                                    z2 = z6;
                                    c = 65535;
                                } else if (!((i10 < 0 || z5 || (syncManager.mSyncStorageEngine.getMasterSyncAutomatically(accountAndUser.userId) && syncManager.mSyncStorageEngine.getSyncAutomatically(accountAndUser.account, accountAndUser.userId, str4))) ? true : z6)) {
                                    syncManager.mLogger.log("scheduleSync: sync of ", accountAndUser, " ", str4, " is not allowed, dropping request");
                                    i8 = i17;
                                    syncManager3 = syncManager;
                                    accountAndUser4 = accountAndUser;
                                    i12 = i13;
                                    length = i15;
                                    bundle5 = bundle7;
                                    i11 = i16;
                                    accountAndUserArr = accountAndUserArr3;
                                    z2 = z6;
                                    c = 65535;
                                } else {
                                    syncManager.mSyncStorageEngine.getDelayUntilTime(new com.android.server.content.SyncStorageEngine.EndPoint(accountAndUser.account, str4, accountAndUser.userId));
                                    java.lang.String packageName2 = serviceInfo.componentName.getPackageName();
                                    if (i10 == -1) {
                                        if (z) {
                                            final android.os.Bundle bundle8 = new android.os.Bundle(bundle7);
                                            final android.accounts.AccountAndUser accountAndUser6 = accountAndUser;
                                            android.accounts.AccountAndUser accountAndUser7 = accountAndUser;
                                            c2 = 65535;
                                            bundle4 = bundle7;
                                            sendOnUnsyncableAccount(syncManager.mContext, serviceInfo, accountAndUser.userId, new com.android.server.content.SyncManager.OnReadyCallback() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda7
                                                @Override // com.android.server.content.SyncManager.OnReadyCallback
                                                public final void onReady() {
                                                    com.android.server.content.SyncManager.this.lambda$scheduleSync$5(accountAndUser6, i2, str4, bundle8, i3, j, i4, i5, i6, str2);
                                                }
                                            });
                                            syncManager2 = this;
                                            accountAndUser2 = accountAndUser7;
                                        } else {
                                            c2 = 65535;
                                            android.accounts.AccountAndUser accountAndUser8 = accountAndUser;
                                            bundle4 = bundle7;
                                            android.os.Bundle bundle9 = new android.os.Bundle();
                                            bundle9.putBoolean("initialize", true);
                                            syncManager2 = this;
                                            syncManager2.mLogger.log("scheduleSync: schedule initialisation sync ", accountAndUser8, " ", str4);
                                            syncManager2.postScheduleSyncMessage(new com.android.server.content.SyncOperation(accountAndUser8.account, accountAndUser8.userId, i14, packageName2, i2, i9, str4, bundle9, allowParallelSyncs, i4), j);
                                            accountAndUser2 = accountAndUser8;
                                        }
                                    } else {
                                        c2 = 65535;
                                        android.accounts.AccountAndUser accountAndUser9 = accountAndUser;
                                        bundle4 = bundle7;
                                        syncManager2 = syncManager;
                                        if (i3 == -2 || i3 == i10) {
                                            syncManager2.mLogger.log("scheduleSync: scheduling sync ", accountAndUser9, " ", str4);
                                            accountAndUser2 = accountAndUser9;
                                            syncManager2.postScheduleSyncMessage(new com.android.server.content.SyncOperation(accountAndUser9.account, accountAndUser9.userId, i14, packageName2, i2, i9, str4, bundle4, allowParallelSyncs, i4), j);
                                        } else {
                                            syncManager2.mLogger.log("scheduleSync: not handling ", accountAndUser9, " ", str4);
                                            accountAndUser2 = accountAndUser9;
                                        }
                                    }
                                    i8 = i;
                                    syncManager3 = syncManager2;
                                    accountAndUser4 = accountAndUser2;
                                    i12 = i13;
                                    c = c2;
                                    bundle5 = bundle4;
                                    length = i15;
                                    i11 = i16;
                                    accountAndUserArr = accountAndUserArr3;
                                    z2 = z6;
                                }
                            }
                        }
                    }
                }
            }
            i12++;
            i8 = i;
            str3 = str;
            syncManager3 = syncManager3;
            c = c;
            bundle5 = bundle5;
            length = length;
            i11 = i11;
            accountAndUserArr = accountAndUserArr;
            z2 = z2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSync$4(android.accounts.AccountAndUser accountAndUser, int i, int i2, java.lang.String str, android.os.Bundle bundle, int i3, long j, int i4, int i5, int i6, java.lang.String str2, android.os.Bundle bundle2) {
        if (bundle2 != null && bundle2.getBoolean("booleanResult")) {
            scheduleSync(accountAndUser.account, i, i2, str, bundle, i3, j, true, i4, i5, i6, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSync$5(android.accounts.AccountAndUser accountAndUser, int i, java.lang.String str, android.os.Bundle bundle, int i2, long j, int i3, int i4, int i5, java.lang.String str2) {
        scheduleSync(accountAndUser.account, accountAndUser.userId, i, str, bundle, i2, j, false, i3, i4, i5, str2);
    }

    public int computeSyncable(android.accounts.Account account, int i, java.lang.String str, boolean z, boolean z2) {
        int isSyncable = getIsSyncable(account, i, str);
        if (isSyncable == 0) {
            return 0;
        }
        android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = this.mSyncAdapters.getServiceInfo(android.content.SyncAdapterType.newKey(str, account.type), i);
        if (serviceInfo == null) {
            return 0;
        }
        int i2 = serviceInfo.uid;
        java.lang.String packageName = serviceInfo.componentName.getPackageName();
        if (z2 && isPackageStopped(packageName, i)) {
            return 0;
        }
        if (this.mAmi.isAppStartModeDisabled(i2, packageName)) {
            android.util.Slog.w("SyncManager", "Not scheduling job " + serviceInfo.uid + ":" + serviceInfo.componentName + " -- package not allowed to start");
            return 0;
        }
        if (z && !canAccessAccount(account, packageName, i2)) {
            android.util.Log.w("SyncManager", "Access to " + com.android.server.content.SyncLogger.logSafe(account) + " denied for package " + packageName + " in UID " + serviceInfo.uid);
            return 3;
        }
        return isSyncable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageStopped(java.lang.String str, int i) {
        if (android.content.pm.Flags.stayStopped()) {
            try {
                return this.mPackageManagerInternal.isPackageStopped(str, i);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e("SyncManager", "Couldn't determine stopped state for unknown package: " + str);
                return false;
            }
        }
        return false;
    }

    private boolean canAccessAccount(android.accounts.Account account, java.lang.String str, int i) {
        if (this.mAccountManager.hasAccountAccess(account, str, android.os.UserHandle.getUserHandleForUid(i))) {
            return true;
        }
        try {
            this.mContext.getPackageManager().getApplicationInfoAsUser(str, 1048576, android.os.UserHandle.getUserId(i));
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSyncsForAuthority(com.android.server.content.SyncStorageEngine.EndPoint endPoint, java.lang.String str) {
        this.mLogger.log("removeSyncsForAuthority: ", endPoint, str);
        verifyJobScheduler();
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (syncOperation.target.matchesSpec(endPoint)) {
                this.mLogger.log("canceling: ", syncOperation);
                cancelJob(syncOperation, str);
            }
        }
    }

    public void removePeriodicSync(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, java.lang.String str) {
        android.os.Message obtainMessage = this.mSyncHandler.obtainMessage(14, android.util.Pair.create(endPoint, str));
        obtainMessage.setData(bundle);
        obtainMessage.sendToTarget();
    }

    public void updateOrAddPeriodicSync(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j, long j2, android.os.Bundle bundle) {
        this.mSyncHandler.obtainMessage(13, new com.android.server.content.SyncManager.UpdatePeriodicSyncMessagePayload(endPoint, j, j2, bundle)).sendToTarget();
    }

    public java.util.List<android.content.PeriodicSync> getPeriodicSyncs(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        java.util.List<com.android.server.content.SyncOperation> allPendingSyncs = getAllPendingSyncs();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.content.SyncOperation syncOperation : allPendingSyncs) {
            if (syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                arrayList.add(new android.content.PeriodicSync(syncOperation.target.account, syncOperation.target.provider, syncOperation.getClonedExtras(), syncOperation.periodMillis / 1000, syncOperation.flexMillis / 1000));
            }
        }
        return arrayList;
    }

    public void scheduleLocalSync(android.accounts.Account account, int i, int i2, java.lang.String str, int i3, int i4, int i5, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean("upload", true);
        scheduleSync(account, i, i2, str, bundle, -2, LOCAL_SYNC_DELAY, true, i3, i4, i5, str2);
    }

    public android.content.SyncAdapterType[] getSyncAdapterTypes(int i, int i2) {
        java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo> allServices = this.mSyncAdapters.getAllServices(i2);
        java.util.ArrayList arrayList = new java.util.ArrayList(allServices.size());
        for (android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo : allServices) {
            java.lang.String packageName = ((android.content.SyncAdapterType) serviceInfo.type).getPackageName();
            if (android.text.TextUtils.isEmpty(packageName) || !this.mPackageManagerInternal.filterAppAccess(packageName, i, i2)) {
                arrayList.add((android.content.SyncAdapterType) serviceInfo.type);
            }
        }
        return (android.content.SyncAdapterType[]) arrayList.toArray(new android.content.SyncAdapterType[0]);
    }

    public java.lang.String[] getSyncAdapterPackagesForAuthorityAsUser(java.lang.String str, int i, int i2) {
        java.lang.String[] syncAdapterPackagesForAuthority = this.mSyncAdapters.getSyncAdapterPackagesForAuthority(str, i2);
        java.util.ArrayList arrayList = new java.util.ArrayList(syncAdapterPackagesForAuthority.length);
        for (java.lang.String str2 : syncAdapterPackagesForAuthority) {
            if (!android.text.TextUtils.isEmpty(str2) && !this.mPackageManagerInternal.filterAppAccess(str2, i, i2)) {
                arrayList.add(str2);
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    public java.lang.String getSyncAdapterPackageAsUser(java.lang.String str, java.lang.String str2, int i, int i2) {
        android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo;
        if (str == null || str2 == null || (serviceInfo = this.mSyncAdapters.getServiceInfo(android.content.SyncAdapterType.newKey(str2, str), i2)) == null) {
            return null;
        }
        java.lang.String packageName = ((android.content.SyncAdapterType) serviceInfo.type).getPackageName();
        if (android.text.TextUtils.isEmpty(packageName) || this.mPackageManagerInternal.filterAppAccess(packageName, i, i2)) {
            return null;
        }
        return packageName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSyncFinishedOrCanceledMessage(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext, android.content.SyncResult syncResult) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "sending MESSAGE_SYNC_FINISHED");
        }
        android.os.Message obtainMessage = this.mSyncHandler.obtainMessage();
        obtainMessage.what = 1;
        obtainMessage.obj = new com.android.server.content.SyncManager.SyncFinishedOrCancelledMessagePayload(activeSyncContext, syncResult);
        this.mSyncHandler.sendMessage(obtainMessage);
    }

    private void sendCancelSyncsMessage(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, java.lang.String str) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "sending MESSAGE_CANCEL");
        }
        this.mLogger.log("sendCancelSyncsMessage() ep=", endPoint, " why=", str);
        android.os.Message obtainMessage = this.mSyncHandler.obtainMessage();
        obtainMessage.what = 6;
        obtainMessage.setData(bundle);
        obtainMessage.obj = endPoint;
        this.mSyncHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postMonitorSyncProgressMessage(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "posting MESSAGE_SYNC_MONITOR in 60s");
        }
        activeSyncContext.mBytesTransferredAtLastPoll = getTotalBytesTransferredByUid(activeSyncContext.mSyncAdapterUid);
        activeSyncContext.mLastPolledTimeElapsed = android.os.SystemClock.elapsedRealtime();
        this.mSyncHandler.sendMessageDelayed(this.mSyncHandler.obtainMessage(8, activeSyncContext), 60000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postScheduleSyncMessage(com.android.server.content.SyncOperation syncOperation, long j) {
        this.mSyncHandler.obtainMessage(12, new com.android.server.content.SyncManager.ScheduleSyncMessagePayload(syncOperation, j)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getTotalBytesTransferredByUid(int i) {
        return android.net.TrafficStats.getUidRxBytes(i) + android.net.TrafficStats.getUidTxBytes(i);
    }

    private class SyncFinishedOrCancelledMessagePayload {
        public final com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext;
        public final android.content.SyncResult syncResult;

        SyncFinishedOrCancelledMessagePayload(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext, android.content.SyncResult syncResult) {
            this.activeSyncContext = activeSyncContext;
            this.syncResult = syncResult;
        }
    }

    private class UpdatePeriodicSyncMessagePayload {
        public final android.os.Bundle extras;
        public final long flex;
        public final long pollFrequency;
        public final com.android.server.content.SyncStorageEngine.EndPoint target;

        UpdatePeriodicSyncMessagePayload(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j, long j2, android.os.Bundle bundle) {
            this.target = endPoint;
            this.pollFrequency = j;
            this.flex = j2;
            this.extras = bundle;
        }
    }

    private static class ScheduleSyncMessagePayload {
        final long minDelayMillis;
        final com.android.server.content.SyncOperation syncOperation;

        ScheduleSyncMessagePayload(com.android.server.content.SyncOperation syncOperation, long j) {
            this.syncOperation = syncOperation;
            this.minDelayMillis = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBackoffSetting(com.android.server.content.SyncStorageEngine.EndPoint endPoint, java.lang.String str) {
        android.util.Pair<java.lang.Long, java.lang.Long> backoff = this.mSyncStorageEngine.getBackoff(endPoint);
        if (backoff != null && ((java.lang.Long) backoff.first).longValue() == -1 && ((java.lang.Long) backoff.second).longValue() == -1) {
            return;
        }
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "Clearing backoffs for " + endPoint);
        }
        this.mSyncStorageEngine.setBackoff(endPoint, -1L, -1L);
        rescheduleSyncs(endPoint, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void increaseBackoffSetting(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        long j;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        android.util.Pair<java.lang.Long, java.lang.Long> backoff = this.mSyncStorageEngine.getBackoff(endPoint);
        if (backoff == null) {
            j = -1;
        } else if (elapsedRealtime >= ((java.lang.Long) backoff.first).longValue()) {
            j = (long) (((java.lang.Long) backoff.second).longValue() * this.mConstants.getRetryTimeIncreaseFactor());
        } else {
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", "Still in backoff, do not increase it. Remaining: " + ((((java.lang.Long) backoff.first).longValue() - elapsedRealtime) / 1000) + " seconds.");
                return;
            }
            return;
        }
        if (j <= 0) {
            long initialSyncRetryTimeInSeconds = this.mConstants.getInitialSyncRetryTimeInSeconds() * 1000;
            j = jitterize(initialSyncRetryTimeInSeconds, (long) (initialSyncRetryTimeInSeconds * 1.1d));
        }
        long maxSyncRetryTimeInSeconds = this.mConstants.getMaxSyncRetryTimeInSeconds() * 1000;
        if (j > maxSyncRetryTimeInSeconds) {
            j = maxSyncRetryTimeInSeconds;
        }
        long j2 = elapsedRealtime + j;
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "Backoff until: " + j2 + ", delayTime: " + j);
        }
        this.mSyncStorageEngine.setBackoff(endPoint, j2, j);
        rescheduleSyncs(endPoint, "increaseBackoffSetting");
    }

    private void rescheduleSyncs(com.android.server.content.SyncStorageEngine.EndPoint endPoint, java.lang.String str) {
        this.mLogger.log("rescheduleSyncs() ep=", endPoint, " why=", str);
        int i = 0;
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                i++;
                cancelJob(syncOperation, str);
                postScheduleSyncMessage(syncOperation, 0L);
            }
        }
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "Rescheduled " + i + " syncs for " + endPoint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDelayUntilTime(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j) {
        long j2;
        long j3 = j * 1000;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (j3 > currentTimeMillis) {
            j2 = android.os.SystemClock.elapsedRealtime() + (j3 - currentTimeMillis);
        } else {
            j2 = 0;
        }
        this.mSyncStorageEngine.setDelayUntilTime(endPoint, j2);
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "Delay Until time set to " + j2 + " for " + endPoint);
        }
        rescheduleSyncs(endPoint, "delayUntil newDelayUntilTime: " + j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAdapterDelayed(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        android.util.Pair<java.lang.Long, java.lang.Long> backoff = this.mSyncStorageEngine.getBackoff(endPoint);
        if ((backoff != null && ((java.lang.Long) backoff.first).longValue() != -1 && ((java.lang.Long) backoff.first).longValue() > elapsedRealtime) || this.mSyncStorageEngine.getDelayUntilTime(endPoint) > elapsedRealtime) {
            return true;
        }
        return false;
    }

    public void cancelActiveSync(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, java.lang.String str) {
        sendCancelSyncsMessage(endPoint, bundle, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleSyncOperationH(com.android.server.content.SyncOperation syncOperation) {
        scheduleSyncOperationH(syncOperation, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleSyncOperationH(com.android.server.content.SyncOperation syncOperation, long j) {
        long j2;
        long j3;
        boolean z;
        com.android.server.DeviceIdleInternal deviceIdleInternal;
        int i;
        java.lang.String str;
        long j4;
        boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
        if (syncOperation == null) {
            android.util.Slog.e("SyncManager", "Can't schedule null sync operation.");
            return;
        }
        if (syncOperation.hasIgnoreBackoff()) {
            j2 = j;
        } else {
            android.util.Pair<java.lang.Long, java.lang.Long> backoff = this.mSyncStorageEngine.getBackoff(syncOperation.target);
            if (backoff == null) {
                android.util.Slog.e("SyncManager", "Couldn't find backoff values for " + com.android.server.content.SyncLogger.logSafe(syncOperation.target));
                backoff = new android.util.Pair<>(-1L, -1L);
            } else if (((java.lang.Long) backoff.first).longValue() != -1) {
                syncOperation.scheduleEjAsRegularJob = true;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long longValue = ((java.lang.Long) backoff.first).longValue() == -1 ? 0L : ((java.lang.Long) backoff.first).longValue() - elapsedRealtime;
            long delayUntilTime = this.mSyncStorageEngine.getDelayUntilTime(syncOperation.target);
            long j5 = delayUntilTime > elapsedRealtime ? delayUntilTime - elapsedRealtime : 0L;
            if (isLoggable) {
                android.util.Slog.v("SyncManager", "backoff delay:" + longValue + " delayUntil delay:" + j5);
            }
            j2 = java.lang.Math.max(j, java.lang.Math.max(longValue, j5));
        }
        if (j2 < 0) {
            j2 = 0;
        } else if (j2 > 0) {
            syncOperation.scheduleEjAsRegularJob = true;
        }
        if (syncOperation.isPeriodic) {
            j3 = j2;
        } else {
            java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it = this.mActiveSyncContexts.iterator();
            while (it.hasNext()) {
                if (it.next().mSyncOperation.key.equals(syncOperation.key)) {
                    if (isLoggable) {
                        android.util.Log.v("SyncManager", "Duplicate sync is already running. Not scheduling " + syncOperation);
                        return;
                    }
                    return;
                }
            }
            syncOperation.expectedRuntime = android.os.SystemClock.elapsedRealtime() + j2;
            java.util.List<com.android.server.content.SyncOperation> allPendingSyncs = getAllPendingSyncs();
            com.android.server.content.SyncOperation syncOperation2 = syncOperation;
            int i2 = 0;
            for (com.android.server.content.SyncOperation syncOperation3 : allPendingSyncs) {
                if (!syncOperation3.isPeriodic) {
                    if (!syncOperation3.key.equals(syncOperation.key)) {
                        j4 = j2;
                    } else {
                        j4 = j2;
                        if (syncOperation2.expectedRuntime > syncOperation3.expectedRuntime) {
                            syncOperation2 = syncOperation3;
                        }
                        i2++;
                    }
                    j2 = j4;
                }
            }
            j3 = j2;
            if (i2 > 1) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("duplicates found when scheduling a sync operation: owningUid=");
                sb.append(syncOperation.owningUid);
                sb.append("; owningPackage=");
                sb.append(syncOperation.owningPackage);
                sb.append("; source=");
                sb.append(syncOperation.syncSource);
                sb.append("; adapter=");
                if (syncOperation.target != null) {
                    str = syncOperation.target.provider;
                } else {
                    str = "unknown";
                }
                sb.append(str);
                android.util.Slog.wtf("SyncManager", sb.toString());
            }
            if (syncOperation != syncOperation2 && j3 == 0 && syncOperation2.syncExemptionFlag < syncOperation.syncExemptionFlag) {
                syncOperation2 = syncOperation;
                i = java.lang.Math.max(0, syncOperation.syncExemptionFlag);
            } else {
                i = 0;
            }
            for (com.android.server.content.SyncOperation syncOperation4 : allPendingSyncs) {
                if (!syncOperation4.isPeriodic && syncOperation4.key.equals(syncOperation.key) && syncOperation4 != syncOperation2) {
                    if (isLoggable) {
                        android.util.Slog.v("SyncManager", "Cancelling duplicate sync " + syncOperation4);
                    }
                    i = java.lang.Math.max(i, syncOperation4.syncExemptionFlag);
                    cancelJob(syncOperation4, "scheduleSyncOperationH-duplicate");
                }
            }
            if (syncOperation2 != syncOperation) {
                if (isLoggable) {
                    android.util.Slog.v("SyncManager", "Not scheduling because a duplicate exists.");
                    return;
                }
                return;
            } else if (i > 0) {
                syncOperation.syncExemptionFlag = i;
            }
        }
        if (syncOperation.jobId == -1) {
            syncOperation.jobId = getUnusedJobIdH();
        }
        if (isLoggable) {
            android.util.Slog.v("SyncManager", "scheduling sync operation " + syncOperation.toString());
        }
        android.app.job.JobInfo.Builder flags = new android.app.job.JobInfo.Builder(syncOperation.jobId, new android.content.ComponentName(this.mContext, (java.lang.Class<?>) com.android.server.content.SyncJobService.class)).setExtras(syncOperation.toJobInfoExtras()).setRequiredNetworkType(syncOperation.isNotAllowedOnMetered() ? 2 : 1).setRequiresStorageNotLow(true).setPersisted(true).setBias(syncOperation.getJobBias()).setFlags(syncOperation.isAppStandbyExempted() ? 8 : 0);
        if (syncOperation.isPeriodic) {
            flags.setPeriodic(syncOperation.periodMillis, syncOperation.flexMillis);
            z = true;
        } else {
            if (j3 > 0) {
                flags.setMinimumLatency(j3);
            }
            z = true;
            getSyncStorageEngine().markPending(syncOperation.target, true);
        }
        if (syncOperation.hasRequireCharging()) {
            flags.setRequiresCharging(z);
        }
        if (syncOperation.isScheduledAsExpeditedJob() && !syncOperation.scheduleEjAsRegularJob) {
            flags.setExpedited(z);
        }
        if (syncOperation.syncExemptionFlag == 2 && (deviceIdleInternal = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class)) != null) {
            deviceIdleInternal.addPowerSaveTempWhitelistApp(1000, syncOperation.owningPackage, this.mConstants.getKeyExemptionTempWhitelistDurationInSeconds() * 1000, 1, android.os.UserHandle.getUserId(syncOperation.owningUid), false, 306, "sync by top app");
        }
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        if (usageStatsManagerInternal != null) {
            usageStatsManagerInternal.reportSyncScheduled(syncOperation.owningPackage, android.os.UserHandle.getUserId(syncOperation.owningUid), syncOperation.isAppStandbyExempted());
        }
        android.app.job.JobInfo build = flags.build();
        int scheduleAsPackage = getJobScheduler().scheduleAsPackage(build, syncOperation.owningPackage, syncOperation.target.userId, syncOperation.wakeLockName());
        if (scheduleAsPackage == 0 && build.isExpedited()) {
            if (isLoggable) {
                android.util.Slog.i("SyncManager", "Failed to schedule EJ for " + syncOperation.owningPackage + ". Downgrading to regular");
            }
            syncOperation.scheduleEjAsRegularJob = true;
            flags.setExpedited(false).setExtras(syncOperation.toJobInfoExtras());
            scheduleAsPackage = getJobScheduler().scheduleAsPackage(flags.build(), syncOperation.owningPackage, syncOperation.target.userId, syncOperation.wakeLockName());
        }
        if (scheduleAsPackage == 0) {
            android.util.Slog.e("SyncManager", "Failed to schedule job for " + syncOperation.owningPackage);
        }
    }

    public void clearScheduledSyncOperations(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                cancelJob(syncOperation, "clearScheduledSyncOperations");
                getSyncStorageEngine().markPending(syncOperation.target, false);
            }
        }
        this.mSyncStorageEngine.setBackoff(endPoint, -1L, -1L);
    }

    public void cancelScheduledSyncOperation(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle) {
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint) && syncOperation.areExtrasEqual(bundle, false)) {
                cancelJob(syncOperation, "cancelScheduledSyncOperation");
            }
        }
        setAuthorityPendingState(endPoint);
        if (!this.mSyncStorageEngine.isSyncPending(endPoint)) {
            this.mSyncStorageEngine.setBackoff(endPoint, -1L, -1L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeRescheduleSync(android.content.SyncResult syncResult, com.android.server.content.SyncOperation syncOperation) {
        boolean isLoggable = android.util.Log.isLoggable("SyncManager", 3);
        if (isLoggable) {
            android.util.Log.d("SyncManager", "encountered error(s) during the sync: " + syncResult + ", " + syncOperation);
        }
        syncOperation.enableBackoff();
        syncOperation.scheduleEjAsRegularJob = true;
        if (syncOperation.hasDoNotRetry() && !syncResult.syncAlreadyInProgress) {
            if (isLoggable) {
                android.util.Log.d("SyncManager", "not retrying sync operation because SYNC_EXTRAS_DO_NOT_RETRY was specified " + syncOperation);
                return;
            }
            return;
        }
        if (syncOperation.isUpload() && !syncResult.syncAlreadyInProgress) {
            syncOperation.enableTwoWaySync();
            if (isLoggable) {
                android.util.Log.d("SyncManager", "retrying sync operation as a two-way sync because an upload-only sync encountered an error: " + syncOperation);
            }
            scheduleSyncOperationH(syncOperation);
            return;
        }
        if (syncResult.tooManyRetries) {
            if (isLoggable) {
                android.util.Log.d("SyncManager", "not retrying sync operation because it retried too many times: " + syncOperation);
                return;
            }
            return;
        }
        if (syncResult.madeSomeProgress()) {
            if (isLoggable) {
                android.util.Log.d("SyncManager", "retrying sync operation because even though it had an error it achieved some success");
            }
            scheduleSyncOperationH(syncOperation);
            return;
        }
        if (syncResult.syncAlreadyInProgress) {
            if (isLoggable) {
                android.util.Log.d("SyncManager", "retrying sync operation that failed because there was already a sync in progress: " + syncOperation);
            }
            scheduleSyncOperationH(syncOperation, 10000L);
            return;
        }
        if (syncResult.hasSoftError()) {
            if (isLoggable) {
                android.util.Log.d("SyncManager", "retrying sync operation because it encountered a soft error: " + syncOperation);
            }
            scheduleSyncOperationH(syncOperation);
            return;
        }
        android.util.Log.e("SyncManager", "not retrying sync operation because the error is a hard error: " + com.android.server.content.SyncLogger.logSafe(syncOperation));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserUnlocked(int i) {
        com.android.server.accounts.AccountManagerService.getSingleton().validateAccounts(i);
        this.mSyncAdapters.invalidateCache(i);
        updateRunningAccounts(new com.android.server.content.SyncStorageEngine.EndPoint(null, null, i));
        for (android.accounts.Account account : com.android.server.accounts.AccountManagerService.getSingleton().getAccounts(i, this.mContext.getOpPackageName())) {
            scheduleSync(account, i, -8, null, null, -1, 0, android.os.Process.myUid(), -3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStopped(int i) {
        updateRunningAccounts(null);
        cancelActiveSync(new com.android.server.content.SyncStorageEngine.EndPoint(null, null, i), null, "onUserStopped");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserRemoved(int i) {
        this.mLogger.log("onUserRemoved: u", java.lang.Integer.valueOf(i));
        updateRunningAccounts(null);
        this.mSyncStorageEngine.removeStaleAccounts(null, i);
        for (com.android.server.content.SyncOperation syncOperation : getAllPendingSyncs()) {
            if (syncOperation.target.userId == i) {
                cancelJob(syncOperation, "user removed u" + i);
            }
        }
    }

    @android.annotation.NonNull
    static android.content.Intent getAdapterBindIntent(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, int i) {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction("android.content.SyncAdapter");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.client_label", android.R.string.status_bar_no_calling);
        intent.putExtra("android.intent.extra.client_intent", android.app.PendingIntent.getActivityAsUser(context, 0, new android.content.Intent("android.settings.SYNC_SETTINGS"), 67108864, null, android.os.UserHandle.of(i)));
        return intent;
    }

    class ActiveSyncContext extends android.content.ISyncContext.Stub implements android.content.ServiceConnection, android.os.IBinder.DeathRecipient {
        boolean mBound;
        long mBytesTransferredAtLastPoll;
        java.lang.String mEventName;
        final long mHistoryRowId;
        long mLastPolledTimeElapsed;
        final int mSyncAdapterUid;
        android.content.SyncInfo mSyncInfo;
        final com.android.server.content.SyncOperation mSyncOperation;
        final android.os.PowerManager.WakeLock mSyncWakeLock;
        boolean mIsLinkedToDeath = false;
        android.content.ISyncAdapter mSyncAdapter = null;
        final long mStartTime = android.os.SystemClock.elapsedRealtime();
        long mTimeoutStartTime = this.mStartTime;

        public ActiveSyncContext(com.android.server.content.SyncOperation syncOperation, long j, int i) {
            this.mSyncAdapterUid = i;
            this.mSyncOperation = syncOperation;
            this.mHistoryRowId = j;
            this.mSyncWakeLock = com.android.server.content.SyncManager.this.mSyncHandler.getSyncWakeLock(this.mSyncOperation);
            this.mSyncWakeLock.setWorkSource(new android.os.WorkSource(i));
            this.mSyncWakeLock.acquire();
        }

        public void sendHeartbeat() {
        }

        public void onFinished(android.content.SyncResult syncResult) {
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", "onFinished: " + this);
            }
            com.android.server.content.SyncManager.this.mLogger.log("onFinished result=", syncResult, " endpoint=", this.mSyncOperation == null ? "null" : this.mSyncOperation.target);
            com.android.server.content.SyncManager.this.sendSyncFinishedOrCanceledMessage(this, syncResult);
        }

        public void toString(java.lang.StringBuilder sb, boolean z) {
            sb.append("startTime ");
            sb.append(this.mStartTime);
            sb.append(", mTimeoutStartTime ");
            sb.append(this.mTimeoutStartTime);
            sb.append(", mHistoryRowId ");
            sb.append(this.mHistoryRowId);
            sb.append(", syncOperation ");
            sb.append(z ? com.android.server.content.SyncLogger.logSafe(this.mSyncOperation) : this.mSyncOperation);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.os.Message obtainMessage = com.android.server.content.SyncManager.this.mSyncHandler.obtainMessage();
            obtainMessage.what = 4;
            obtainMessage.obj = com.android.server.content.SyncManager.this.new ServiceConnectionData(this, iBinder);
            com.android.server.content.SyncManager.this.mSyncHandler.sendMessage(obtainMessage);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.os.Message obtainMessage = com.android.server.content.SyncManager.this.mSyncHandler.obtainMessage();
            obtainMessage.what = 5;
            obtainMessage.obj = com.android.server.content.SyncManager.this.new ServiceConnectionData(this, null);
            com.android.server.content.SyncManager.this.mSyncHandler.sendMessage(obtainMessage);
        }

        boolean bindToSyncAdapter(android.content.ComponentName componentName, int i) {
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Log.d("SyncManager", "bindToSyncAdapter: " + componentName + ", connection " + this);
            }
            android.content.Intent adapterBindIntent = com.android.server.content.SyncManager.getAdapterBindIntent(com.android.server.content.SyncManager.this.mContext, componentName, i);
            this.mBound = true;
            boolean bindServiceAsUser = com.android.server.content.SyncManager.this.mContext.bindServiceAsUser(adapterBindIntent, this, com.android.server.content.SyncManager.SYNC_ADAPTER_CONNECTION_FLAGS, new android.os.UserHandle(this.mSyncOperation.target.userId));
            com.android.server.content.SyncManager.this.mLogger.log("bindService() returned=", java.lang.Boolean.valueOf(this.mBound), " for ", this);
            if (!bindServiceAsUser) {
                this.mBound = false;
            } else {
                try {
                    this.mEventName = this.mSyncOperation.wakeLockName();
                    com.android.server.content.SyncManager.this.mBatteryStats.noteSyncStart(this.mEventName, this.mSyncAdapterUid);
                } catch (android.os.RemoteException e) {
                }
            }
            return bindServiceAsUser;
        }

        protected void close() {
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Log.d("SyncManager", "unBindFromSyncAdapter: connection " + this);
            }
            if (this.mBound) {
                this.mBound = false;
                com.android.server.content.SyncManager.this.mLogger.log("unbindService for ", this);
                com.android.server.content.SyncManager.this.mContext.unbindService(this);
                try {
                    com.android.server.content.SyncManager.this.mBatteryStats.noteSyncFinish(this.mEventName, this.mSyncAdapterUid);
                } catch (android.os.RemoteException e) {
                }
            }
            this.mSyncWakeLock.release();
            this.mSyncWakeLock.setWorkSource(null);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            toString(sb, false);
            return sb.toString();
        }

        public java.lang.String toSafeString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            toString(sb, true);
            return sb.toString();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.content.SyncManager.this.sendSyncFinishedOrCanceledMessage(this, null);
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, boolean z) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        dumpSyncState(indentingPrintWriter, new com.android.server.content.SyncAdapterStateFetcher());
        this.mConstants.dump(printWriter, "");
        dumpSyncAdapters(indentingPrintWriter);
        if (z) {
            indentingPrintWriter.println("Detailed Sync History");
            this.mLogger.dumpAll(printWriter);
        }
    }

    static java.lang.String formatTime(long j) {
        if (j == 0) {
            return "N/A";
        }
        return android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$6(com.android.server.content.SyncOperation syncOperation, com.android.server.content.SyncOperation syncOperation2) {
        int compare = java.lang.Integer.compare(syncOperation.target.userId, syncOperation2.target.userId);
        if (compare != 0) {
            return compare;
        }
        java.util.Comparator comparator = java.lang.String.CASE_INSENSITIVE_ORDER;
        int compare2 = comparator.compare(syncOperation.target.account.type, syncOperation2.target.account.type);
        if (compare2 != 0) {
            return compare2;
        }
        int compare3 = comparator.compare(syncOperation.target.account.name, syncOperation2.target.account.name);
        if (compare3 != 0) {
            return compare3;
        }
        int compare4 = comparator.compare(syncOperation.target.provider, syncOperation2.target.provider);
        if (compare4 != 0) {
            return compare4;
        }
        int compare5 = java.lang.Integer.compare(syncOperation.reason, syncOperation2.reason);
        if (compare5 != 0) {
            return compare5;
        }
        int compare6 = java.lang.Long.compare(syncOperation.periodMillis, syncOperation2.periodMillis);
        if (compare6 != 0) {
            return compare6;
        }
        int compare7 = java.lang.Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
        if (compare7 != 0) {
            return compare7;
        }
        int compare8 = java.lang.Long.compare(syncOperation.jobId, syncOperation2.jobId);
        if (compare8 != 0) {
            return compare8;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$7(com.android.server.content.SyncOperation syncOperation, com.android.server.content.SyncOperation syncOperation2) {
        int compare = java.lang.Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
        return compare != 0 ? compare : sOpDumpComparator.compare(syncOperation, syncOperation2);
    }

    private static <T> int countIf(java.util.Collection<T> collection, java.util.function.Predicate<T> predicate) {
        java.util.Iterator<T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                i++;
            }
        }
        return i;
    }

    protected void dumpPendingSyncs(java.io.PrintWriter printWriter, com.android.server.content.SyncAdapterStateFetcher syncAdapterStateFetcher) {
        java.util.List<com.android.server.content.SyncOperation> allPendingSyncs = getAllPendingSyncs();
        printWriter.print("Pending Syncs: ");
        printWriter.println(countIf(allPendingSyncs, new java.util.function.Predicate() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$dumpPendingSyncs$8;
                lambda$dumpPendingSyncs$8 = com.android.server.content.SyncManager.lambda$dumpPendingSyncs$8((com.android.server.content.SyncOperation) obj);
                return lambda$dumpPendingSyncs$8;
            }
        }));
        java.util.Collections.sort(allPendingSyncs, sOpRuntimeComparator);
        for (com.android.server.content.SyncOperation syncOperation : allPendingSyncs) {
            if (!syncOperation.isPeriodic) {
                printWriter.println(syncOperation.dump(null, false, syncAdapterStateFetcher, false));
            }
        }
        printWriter.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dumpPendingSyncs$8(com.android.server.content.SyncOperation syncOperation) {
        return !syncOperation.isPeriodic;
    }

    protected void dumpPeriodicSyncs(java.io.PrintWriter printWriter, com.android.server.content.SyncAdapterStateFetcher syncAdapterStateFetcher) {
        java.util.List<com.android.server.content.SyncOperation> allPendingSyncs = getAllPendingSyncs();
        printWriter.print("Periodic Syncs: ");
        printWriter.println(countIf(allPendingSyncs, new java.util.function.Predicate() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean z;
                z = ((com.android.server.content.SyncOperation) obj).isPeriodic;
                return z;
            }
        }));
        java.util.Collections.sort(allPendingSyncs, sOpDumpComparator);
        for (com.android.server.content.SyncOperation syncOperation : allPendingSyncs) {
            if (syncOperation.isPeriodic) {
                printWriter.println(syncOperation.dump(null, false, syncAdapterStateFetcher, false));
            }
        }
        printWriter.println();
    }

    public static java.lang.StringBuilder formatDurationHMS(java.lang.StringBuilder sb, long j) {
        boolean z;
        long j2 = j / 1000;
        if (j2 < 0) {
            sb.append('-');
            j2 = -j2;
        }
        long j3 = j2 % 60;
        long j4 = j2 / 60;
        long j5 = j4 % 60;
        long j6 = j4 / 60;
        long j7 = j6 % 24;
        long j8 = j6 / 24;
        if (j8 <= 0) {
            z = false;
        } else {
            sb.append(j8);
            sb.append('d');
            z = true;
        }
        if (!printTwoDigitNumber(sb, j3, 's', printTwoDigitNumber(sb, j5, 'm', printTwoDigitNumber(sb, j7, 'h', z)))) {
            sb.append("0s");
        }
        return sb;
    }

    private static boolean printTwoDigitNumber(java.lang.StringBuilder sb, long j, char c, boolean z) {
        if (!z && j == 0) {
            return false;
        }
        if (z && j < 10) {
            sb.append('0');
        }
        sb.append(j);
        sb.append(c);
        return true;
    }

    @dalvik.annotation.optimization.NeverCompile
    protected void dumpSyncState(java.io.PrintWriter printWriter, com.android.server.content.SyncAdapterStateFetcher syncAdapterStateFetcher) {
        boolean z;
        final java.lang.StringBuilder sb = new java.lang.StringBuilder();
        printWriter.print("Data connected: ");
        printWriter.println(this.mDataConnectionIsConnected);
        printWriter.print("Battery saver: ");
        int i = 0;
        printWriter.println(this.mPowerManager != null && this.mPowerManager.isPowerSaveMode());
        printWriter.print("Background network restriction: ");
        android.net.ConnectivityManager connectivityManager = getConnectivityManager();
        int restrictBackgroundStatus = connectivityManager == null ? -1 : connectivityManager.getRestrictBackgroundStatus();
        switch (restrictBackgroundStatus) {
            case 1:
                printWriter.println(" disabled");
                break;
            case 2:
                printWriter.println(" whitelisted");
                break;
            case 3:
                printWriter.println(" enabled");
                break;
            default:
                printWriter.print("Unknown(");
                printWriter.print(restrictBackgroundStatus);
                printWriter.println(")");
                break;
        }
        printWriter.print("Auto sync: ");
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers();
        if (allUsers != null) {
            for (android.content.pm.UserInfo userInfo : allUsers) {
                printWriter.print("u" + userInfo.id + "=" + this.mSyncStorageEngine.getMasterSyncAutomatically(userInfo.id) + " ");
            }
            printWriter.println();
        }
        android.content.Intent registerReceiver = this.mContext.registerReceiver(null, new android.content.IntentFilter("android.intent.action.DEVICE_STORAGE_LOW"));
        printWriter.print("Storage low: ");
        printWriter.println(registerReceiver != null);
        printWriter.print("Clock valid: ");
        printWriter.println(this.mSyncStorageEngine.isClockValid());
        android.accounts.AccountAndUser[] allAccountsForSystemProcess = com.android.server.accounts.AccountManagerService.getSingleton().getAllAccountsForSystemProcess();
        printWriter.print("Accounts: ");
        if (allAccountsForSystemProcess != INITIAL_ACCOUNTS_ARRAY) {
            printWriter.println(allAccountsForSystemProcess.length);
        } else {
            printWriter.println("not known yet");
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        printWriter.print("Now: ");
        printWriter.print(elapsedRealtime);
        printWriter.println(" (" + formatTime(java.lang.System.currentTimeMillis()) + ")");
        sb.setLength(0);
        printWriter.print("Uptime: ");
        printWriter.print(formatDurationHMS(sb, elapsedRealtime));
        printWriter.println();
        printWriter.print("Time spent syncing: ");
        sb.setLength(0);
        printWriter.print(formatDurationHMS(sb, this.mSyncHandler.mSyncTimeTracker.timeSpentSyncing()));
        printWriter.print(", sync ");
        printWriter.print(this.mSyncHandler.mSyncTimeTracker.mLastWasSyncing ? "" : "not ");
        printWriter.println("in progress");
        printWriter.println();
        printWriter.println("Active Syncs: " + this.mActiveSyncContexts.size());
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it = this.mActiveSyncContexts.iterator();
        while (it.hasNext()) {
            com.android.server.content.SyncManager.ActiveSyncContext next = it.next();
            long j = elapsedRealtime - next.mStartTime;
            printWriter.print("  ");
            sb.setLength(0);
            printWriter.print(formatDurationHMS(sb, j));
            printWriter.print(" - ");
            printWriter.print(next.mSyncOperation.dump(packageManager, false, syncAdapterStateFetcher, false));
            printWriter.println();
        }
        printWriter.println();
        dumpPendingSyncs(printWriter, syncAdapterStateFetcher);
        dumpPeriodicSyncs(printWriter, syncAdapterStateFetcher);
        printWriter.println("Sync Status");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mSyncStorageEngine.resetTodayStats(false);
        int length = allAccountsForSystemProcess.length;
        int i2 = 0;
        while (i2 < length) {
            android.accounts.AccountAndUser accountAndUser = allAccountsForSystemProcess[i2];
            synchronized (this.mUnlockedUsers) {
                z = this.mUnlockedUsers.get(accountAndUser.userId);
            }
            printWriter.printf("Account %s u%d %s%s\n", accountAndUser.account.name, java.lang.Integer.valueOf(accountAndUser.userId), accountAndUser.account.type, z ? "" : " (locked)");
            printWriter.println("=======================================================================");
            final com.android.server.content.SyncManager.PrintTable printTable = new com.android.server.content.SyncManager.PrintTable(16);
            printTable.set(i, i, "Authority", "Syncable", "Enabled", "Stats", "Loc", "Poll", "Per", "Feed", "User", "Othr", "Tot", "Fail", "Can", "Time", "Last Sync", "Backoff");
            java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
            newArrayList.addAll(this.mSyncAdapters.getAllServices(accountAndUser.userId));
            java.util.Collections.sort(newArrayList, new java.util.Comparator<android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType>>() { // from class: com.android.server.content.SyncManager.12
                @Override // java.util.Comparator
                public int compare(android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> serviceInfo, android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> serviceInfo2) {
                    return ((android.content.SyncAdapterType) serviceInfo.type).authority.compareTo(((android.content.SyncAdapterType) serviceInfo2.type).authority);
                }
            });
            java.util.Iterator it2 = newArrayList.iterator();
            while (it2.hasNext()) {
                android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = (android.content.pm.RegisteredServicesCache.ServiceInfo) it2.next();
                if (((android.content.SyncAdapterType) serviceInfo.type).accountType.equals(accountAndUser.account.type)) {
                    int numRows = printTable.getNumRows();
                    android.accounts.AccountAndUser[] accountAndUserArr = allAccountsForSystemProcess;
                    int i3 = length;
                    android.util.Pair<com.android.server.content.SyncStorageEngine.AuthorityInfo, android.content.SyncStatusInfo> copyOfAuthorityWithSyncStatus = this.mSyncStorageEngine.getCopyOfAuthorityWithSyncStatus(new com.android.server.content.SyncStorageEngine.EndPoint(accountAndUser.account, ((android.content.SyncAdapterType) serviceInfo.type).authority, accountAndUser.userId));
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = (com.android.server.content.SyncStorageEngine.AuthorityInfo) copyOfAuthorityWithSyncStatus.first;
                    android.content.SyncStatusInfo syncStatusInfo = (android.content.SyncStatusInfo) copyOfAuthorityWithSyncStatus.second;
                    arrayList.add(android.util.Pair.create(authorityInfo.target, syncStatusInfo));
                    java.lang.String str = authorityInfo.target.provider;
                    if (str.length() > 50) {
                        str = str.substring(str.length() - 50);
                    }
                    printTable.set(numRows, 0, str, java.lang.Integer.valueOf(authorityInfo.syncable), java.lang.Boolean.valueOf(authorityInfo.enabled));
                    com.android.internal.util.function.QuadConsumer quadConsumer = new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda11
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                            com.android.server.content.SyncManager.lambda$dumpSyncState$10(sb, printTable, (java.lang.String) obj, (android.content.SyncStatusInfo.Stats) obj2, (java.util.function.Function) obj3, (java.lang.Integer) obj4);
                        }
                    };
                    java.lang.StringBuilder sb2 = sb;
                    android.accounts.AccountAndUser accountAndUser2 = accountAndUser;
                    quadConsumer.accept("Total", syncStatusInfo.totalStats, new java.util.function.Function() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda12
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.lang.String lambda$dumpSyncState$11;
                            lambda$dumpSyncState$11 = com.android.server.content.SyncManager.lambda$dumpSyncState$11((java.lang.Integer) obj);
                            return lambda$dumpSyncState$11;
                        }
                    }, java.lang.Integer.valueOf(numRows));
                    int i4 = numRows + 1;
                    java.util.Iterator it3 = it2;
                    quadConsumer.accept("Today", syncStatusInfo.todayStats, new java.util.function.Function() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda13
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.lang.String zeroToEmpty;
                            zeroToEmpty = com.android.server.content.SyncManager.this.zeroToEmpty(((java.lang.Integer) obj).intValue());
                            return zeroToEmpty;
                        }
                    }, java.lang.Integer.valueOf(i4));
                    quadConsumer.accept("Yestr", syncStatusInfo.yesterdayStats, new java.util.function.Function() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda13
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            java.lang.String zeroToEmpty;
                            zeroToEmpty = com.android.server.content.SyncManager.this.zeroToEmpty(((java.lang.Integer) obj).intValue());
                            return zeroToEmpty;
                        }
                    }, java.lang.Integer.valueOf(numRows + 2));
                    if (authorityInfo.delayUntil > elapsedRealtime) {
                        printTable.set(numRows, 15, "D: " + ((authorityInfo.delayUntil - elapsedRealtime) / 1000));
                        if (authorityInfo.backoffTime > elapsedRealtime) {
                            printTable.set(i4, 15, "B: " + ((authorityInfo.backoffTime - elapsedRealtime) / 1000));
                            printTable.set(i4 + 1, 15, java.lang.Long.valueOf(authorityInfo.backoffDelay / 1000));
                        }
                    }
                    if (syncStatusInfo.lastSuccessTime != 0) {
                        printTable.set(numRows, 14, com.android.server.content.SyncStorageEngine.SOURCES[syncStatusInfo.lastSuccessSource] + " SUCCESS");
                        numRows = i4 + 1;
                        printTable.set(i4, 14, formatTime(syncStatusInfo.lastSuccessTime));
                    }
                    if (syncStatusInfo.lastFailureTime != 0) {
                        int i5 = numRows + 1;
                        printTable.set(numRows, 14, com.android.server.content.SyncStorageEngine.SOURCES[syncStatusInfo.lastFailureSource] + " FAILURE");
                        printTable.set(i5, 14, formatTime(syncStatusInfo.lastFailureTime));
                        printTable.set(i5 + 1, 14, syncStatusInfo.lastFailureMesg);
                    }
                    accountAndUser = accountAndUser2;
                    allAccountsForSystemProcess = accountAndUserArr;
                    length = i3;
                    sb = sb2;
                    it2 = it3;
                }
            }
            printTable.writeTo(printWriter);
            i2++;
            i = 0;
        }
        dumpSyncHistory(printWriter);
        printWriter.println();
        printWriter.println("Per Adapter History");
        printWriter.println("(SERVER is now split up to FEED and OTHER)");
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            android.util.Pair pair = (android.util.Pair) arrayList.get(i6);
            printWriter.print("  ");
            printWriter.print(((com.android.server.content.SyncStorageEngine.EndPoint) pair.first).account.name);
            printWriter.print('/');
            printWriter.print(((com.android.server.content.SyncStorageEngine.EndPoint) pair.first).account.type);
            printWriter.print(" u");
            printWriter.print(((com.android.server.content.SyncStorageEngine.EndPoint) pair.first).userId);
            printWriter.print(" [");
            printWriter.print(((com.android.server.content.SyncStorageEngine.EndPoint) pair.first).provider);
            printWriter.print("]");
            printWriter.println();
            printWriter.println("    Per source last syncs:");
            for (int i7 = 0; i7 < com.android.server.content.SyncStorageEngine.SOURCES.length; i7++) {
                printWriter.print("      ");
                printWriter.print(java.lang.String.format("%8s", com.android.server.content.SyncStorageEngine.SOURCES[i7]));
                printWriter.print("  Success: ");
                printWriter.print(formatTime(((android.content.SyncStatusInfo) pair.second).perSourceLastSuccessTimes[i7]));
                printWriter.print("  Failure: ");
                printWriter.println(formatTime(((android.content.SyncStatusInfo) pair.second).perSourceLastFailureTimes[i7]));
            }
            printWriter.println("    Last syncs:");
            for (int i8 = 0; i8 < ((android.content.SyncStatusInfo) pair.second).getEventCount(); i8++) {
                printWriter.print("      ");
                printWriter.print(formatTime(((android.content.SyncStatusInfo) pair.second).getEventTime(i8)));
                printWriter.print(' ');
                printWriter.print(((android.content.SyncStatusInfo) pair.second).getEvent(i8));
                printWriter.println();
            }
            if (((android.content.SyncStatusInfo) pair.second).getEventCount() == 0) {
                printWriter.println("      N/A");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpSyncState$10(java.lang.StringBuilder sb, com.android.server.content.SyncManager.PrintTable printTable, java.lang.String str, android.content.SyncStatusInfo.Stats stats, java.util.function.Function function, java.lang.Integer num) {
        sb.setLength(0);
        printTable.set(num.intValue(), 3, str, function.apply(java.lang.Integer.valueOf(stats.numSourceLocal)), function.apply(java.lang.Integer.valueOf(stats.numSourcePoll)), function.apply(java.lang.Integer.valueOf(stats.numSourcePeriodic)), function.apply(java.lang.Integer.valueOf(stats.numSourceFeed)), function.apply(java.lang.Integer.valueOf(stats.numSourceUser)), function.apply(java.lang.Integer.valueOf(stats.numSourceOther)), function.apply(java.lang.Integer.valueOf(stats.numSyncs)), function.apply(java.lang.Integer.valueOf(stats.numFailures)), function.apply(java.lang.Integer.valueOf(stats.numCancels)), formatDurationHMS(sb, stats.totalElapsedTime));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$dumpSyncState$11(java.lang.Integer num) {
        return java.lang.Integer.toString(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String zeroToEmpty(int i) {
        return i != 0 ? java.lang.Integer.toString(i) : "";
    }

    private void dumpTimeSec(java.io.PrintWriter printWriter, long j) {
        printWriter.print(j / 1000);
        printWriter.print('.');
        printWriter.print((j / 100) % 10);
        printWriter.print('s');
    }

    private void dumpDayStatistic(java.io.PrintWriter printWriter, com.android.server.content.SyncStorageEngine.DayStats dayStats) {
        printWriter.print("Success (");
        printWriter.print(dayStats.successCount);
        if (dayStats.successCount > 0) {
            printWriter.print(" for ");
            dumpTimeSec(printWriter, dayStats.successTime);
            printWriter.print(" avg=");
            dumpTimeSec(printWriter, dayStats.successTime / dayStats.successCount);
        }
        printWriter.print(") Failure (");
        printWriter.print(dayStats.failureCount);
        if (dayStats.failureCount > 0) {
            printWriter.print(" for ");
            dumpTimeSec(printWriter, dayStats.failureTime);
            printWriter.print(" avg=");
            dumpTimeSec(printWriter, dayStats.failureTime / dayStats.failureCount);
        }
        printWriter.println(")");
    }

    protected void dumpSyncHistory(java.io.PrintWriter printWriter) {
        dumpRecentHistory(printWriter);
        dumpDayStatistics(printWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0381, code lost:
    
        if (r9.downstreamActivity == 0) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpRecentHistory(java.io.PrintWriter printWriter) {
        java.lang.String str;
        java.lang.String str2;
        int i;
        java.lang.String str3;
        java.lang.String str4;
        int i2;
        int i3;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9;
        java.lang.String str10;
        java.lang.String str11;
        java.lang.String str12;
        java.lang.String str13;
        java.lang.String format;
        java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> arrayList;
        java.lang.String str14;
        java.lang.String str15;
        com.android.server.content.SyncManager syncManager = this;
        java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> syncHistory = syncManager.mSyncStorageEngine.getSyncHistory();
        if (syncHistory != null && syncHistory.size() > 0) {
            java.util.HashMap newHashMap = com.google.android.collect.Maps.newHashMap();
            int size = syncHistory.size();
            java.util.Iterator<com.android.server.content.SyncStorageEngine.SyncHistoryItem> it = syncHistory.iterator();
            long j = 0;
            long j2 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                str = " u";
                str2 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                if (!hasNext) {
                    break;
                }
                com.android.server.content.SyncStorageEngine.SyncHistoryItem next = it.next();
                java.util.Iterator<com.android.server.content.SyncStorageEngine.SyncHistoryItem> it2 = it;
                com.android.server.content.SyncStorageEngine.AuthorityInfo authority = syncManager.mSyncStorageEngine.getAuthority(next.authorityId);
                if (authority != null) {
                    java.lang.String str16 = authority.target.provider;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    arrayList = syncHistory;
                    sb.append(authority.target.account.name);
                    sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                    sb.append(authority.target.account.type);
                    sb.append(" u");
                    sb.append(authority.target.userId);
                    str15 = sb.toString();
                    str14 = str16;
                } else {
                    arrayList = syncHistory;
                    str14 = "Unknown";
                    str15 = str14;
                }
                int length = str14.length();
                if (length > i4) {
                    i4 = length;
                }
                int length2 = str15.length();
                if (length2 > i5) {
                    i5 = length2;
                }
                long j3 = next.elapsedTime;
                long j4 = j + j3;
                j2++;
                com.android.server.content.SyncManager.AuthoritySyncStats authoritySyncStats = (com.android.server.content.SyncManager.AuthoritySyncStats) newHashMap.get(str14);
                byte b = 0;
                if (authoritySyncStats == null) {
                    authoritySyncStats = new com.android.server.content.SyncManager.AuthoritySyncStats(str14);
                    newHashMap.put(str14, authoritySyncStats);
                }
                authoritySyncStats.elapsedTime += j3;
                authoritySyncStats.times++;
                java.util.Map<java.lang.String, com.android.server.content.SyncManager.AccountSyncStats> map = authoritySyncStats.accountMap;
                com.android.server.content.SyncManager.AccountSyncStats accountSyncStats = map.get(str15);
                if (accountSyncStats == null) {
                    accountSyncStats = new com.android.server.content.SyncManager.AccountSyncStats(str15);
                    map.put(str15, accountSyncStats);
                }
                accountSyncStats.elapsedTime += j3;
                accountSyncStats.times++;
                it = it2;
                syncHistory = arrayList;
                j = j4;
            }
            java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> arrayList2 = syncHistory;
            if (j <= 0) {
                i = size;
                str3 = " u";
                str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                i2 = i4;
            } else {
                printWriter.println();
                printWriter.printf("Detailed Statistics (Recent history):  %d (# of times) %ds (sync time)\n", java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j / 1000));
                java.util.ArrayList arrayList3 = new java.util.ArrayList(newHashMap.values());
                java.util.Collections.sort(arrayList3, new java.util.Comparator<com.android.server.content.SyncManager.AuthoritySyncStats>() { // from class: com.android.server.content.SyncManager.13
                    @Override // java.util.Comparator
                    public int compare(com.android.server.content.SyncManager.AuthoritySyncStats authoritySyncStats2, com.android.server.content.SyncManager.AuthoritySyncStats authoritySyncStats3) {
                        int compare = java.lang.Integer.compare(authoritySyncStats3.times, authoritySyncStats2.times);
                        if (compare == 0) {
                            return java.lang.Long.compare(authoritySyncStats3.elapsedTime, authoritySyncStats2.elapsedTime);
                        }
                        return compare;
                    }
                });
                int max = java.lang.Math.max(i4, i5 + 3);
                char[] cArr = new char[max + 4 + 2 + 10 + 11];
                java.util.Arrays.fill(cArr, '-');
                java.lang.String str17 = new java.lang.String(cArr);
                java.lang.String format2 = java.lang.String.format("  %%-%ds: %%-9s  %%-11s\n", java.lang.Integer.valueOf(max + 2));
                java.lang.String format3 = java.lang.String.format("    %%-%ds:   %%-9s  %%-11s\n", java.lang.Integer.valueOf(max));
                printWriter.println(str17);
                java.util.Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    com.android.server.content.SyncManager.AuthoritySyncStats authoritySyncStats2 = (com.android.server.content.SyncManager.AuthoritySyncStats) it3.next();
                    java.util.Iterator it4 = it3;
                    java.lang.String str18 = authoritySyncStats2.name;
                    java.lang.String str19 = str;
                    java.lang.String str20 = str2;
                    long j5 = authoritySyncStats2.elapsedTime;
                    int i6 = size;
                    int i7 = i4;
                    java.lang.String str21 = "%ds/%d%%";
                    java.lang.String str22 = format3;
                    printWriter.printf(format2, str18, java.lang.String.format("%d/%d%%", java.lang.Integer.valueOf(authoritySyncStats2.times), java.lang.Long.valueOf((r4 * 100) / j2)), java.lang.String.format("%ds/%d%%", java.lang.Long.valueOf(j5 / 1000), java.lang.Long.valueOf((j5 * 100) / j)));
                    java.util.ArrayList arrayList4 = new java.util.ArrayList(authoritySyncStats2.accountMap.values());
                    java.util.Collections.sort(arrayList4, new java.util.Comparator<com.android.server.content.SyncManager.AccountSyncStats>() { // from class: com.android.server.content.SyncManager.14
                        @Override // java.util.Comparator
                        public int compare(com.android.server.content.SyncManager.AccountSyncStats accountSyncStats2, com.android.server.content.SyncManager.AccountSyncStats accountSyncStats3) {
                            int compare = java.lang.Integer.compare(accountSyncStats3.times, accountSyncStats2.times);
                            if (compare == 0) {
                                return java.lang.Long.compare(accountSyncStats3.elapsedTime, accountSyncStats2.elapsedTime);
                            }
                            return compare;
                        }
                    });
                    java.util.Iterator it5 = arrayList4.iterator();
                    while (it5.hasNext()) {
                        com.android.server.content.SyncManager.AccountSyncStats accountSyncStats2 = (com.android.server.content.SyncManager.AccountSyncStats) it5.next();
                        java.lang.String str23 = format2;
                        long j6 = accountSyncStats2.elapsedTime;
                        int i8 = accountSyncStats2.times;
                        java.util.Iterator it6 = it5;
                        printWriter.printf(str22, accountSyncStats2.name, java.lang.String.format("%d/%d%%", java.lang.Integer.valueOf(i8), java.lang.Long.valueOf((i8 * 100) / j2)), java.lang.String.format(str21, java.lang.Long.valueOf(j6 / 1000), java.lang.Long.valueOf((j6 * 100) / j)));
                        format2 = str23;
                        str21 = str21;
                        it5 = it6;
                    }
                    format3 = str22;
                    printWriter.println(str17);
                    it3 = it4;
                    str2 = str20;
                    str = str19;
                    size = i6;
                    i4 = i7;
                }
                i = size;
                str3 = str;
                str4 = str2;
                i2 = i4;
            }
            printWriter.println();
            printWriter.println("Recent Sync History");
            java.lang.String str24 = "(SERVER is now split up to FEED and OTHER)";
            printWriter.println("(SERVER is now split up to FEED and OTHER)");
            java.lang.String str25 = "  %-" + i5 + "s  %-" + i2 + "s %s\n";
            java.util.HashMap newHashMap2 = com.google.android.collect.Maps.newHashMap();
            android.content.pm.PackageManager packageManager = syncManager.mContext.getPackageManager();
            int i9 = 0;
            while (true) {
                i3 = i;
                if (i9 >= i3) {
                    break;
                }
                java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> arrayList5 = arrayList2;
                com.android.server.content.SyncStorageEngine.SyncHistoryItem syncHistoryItem = arrayList5.get(i9);
                com.android.server.content.SyncStorageEngine.AuthorityInfo authority2 = syncManager.mSyncStorageEngine.getAuthority(syncHistoryItem.authorityId);
                if (authority2 != null) {
                    str12 = authority2.target.provider;
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append(authority2.target.account.name);
                    str9 = str4;
                    sb2.append(str9);
                    sb2.append(authority2.target.account.type);
                    str10 = str3;
                    sb2.append(str10);
                    sb2.append(authority2.target.userId);
                    str11 = sb2.toString();
                } else {
                    str9 = str4;
                    str10 = str3;
                    str11 = "Unknown";
                    str12 = str11;
                }
                str3 = str10;
                long j7 = syncHistoryItem.elapsedTime;
                i = i3;
                arrayList2 = arrayList5;
                long j8 = syncHistoryItem.eventTime;
                java.lang.String str26 = str12 + str9 + str11;
                java.lang.Long l = (java.lang.Long) newHashMap2.get(str26);
                if (l == null) {
                    str4 = str9;
                    format = "";
                    str13 = str24;
                } else {
                    long longValue = (l.longValue() - j8) / 1000;
                    if (longValue < 60) {
                        str4 = str9;
                        format = java.lang.String.valueOf(longValue);
                        str13 = str24;
                    } else if (longValue < 3600) {
                        str4 = str9;
                        format = java.lang.String.format("%02d:%02d", java.lang.Long.valueOf(longValue / 60), java.lang.Long.valueOf(longValue % 60));
                        str13 = str24;
                    } else {
                        str4 = str9;
                        long j9 = longValue % 3600;
                        str13 = str24;
                        format = java.lang.String.format("%02d:%02d:%02d", java.lang.Long.valueOf(longValue / 3600), java.lang.Long.valueOf(j9 / 60), java.lang.Long.valueOf(j9 % 60));
                    }
                }
                newHashMap2.put(str26, java.lang.Long.valueOf(j8));
                i9++;
                printWriter.printf("  #%-3d: %s %8s  %5.1fs  %8s", java.lang.Integer.valueOf(i9), formatTime(j8), com.android.server.content.SyncStorageEngine.SOURCES[syncHistoryItem.source], java.lang.Float.valueOf(j7 / 1000.0f), format);
                printWriter.printf(str25, str11, str12, com.android.server.content.SyncOperation.reasonToString(packageManager, syncHistoryItem.reason));
                if (syncHistoryItem.event == 1) {
                    if (syncHistoryItem.upstreamActivity == 0) {
                    }
                }
                printWriter.printf("    event=%d upstreamActivity=%d downstreamActivity=%d\n", java.lang.Integer.valueOf(syncHistoryItem.event), java.lang.Long.valueOf(syncHistoryItem.upstreamActivity), java.lang.Long.valueOf(syncHistoryItem.downstreamActivity));
                if (syncHistoryItem.mesg != null && !com.android.server.content.SyncStorageEngine.MESG_SUCCESS.equals(syncHistoryItem.mesg)) {
                    printWriter.printf("    mesg=%s\n", syncHistoryItem.mesg);
                }
                syncManager = this;
                str24 = str13;
            }
            int i10 = i3;
            printWriter.println();
            printWriter.println("Recent Sync History Extras");
            printWriter.println(str24);
            int i11 = 0;
            while (true) {
                int i12 = i10;
                if (i11 < i12) {
                    java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> arrayList6 = arrayList2;
                    com.android.server.content.SyncStorageEngine.SyncHistoryItem syncHistoryItem2 = arrayList6.get(i11);
                    android.os.Bundle bundle = syncHistoryItem2.extras;
                    if (bundle == null) {
                        str5 = str4;
                        str6 = str3;
                    } else if (bundle.size() == 0) {
                        str5 = str4;
                        str6 = str3;
                    } else {
                        com.android.server.content.SyncStorageEngine.AuthorityInfo authority3 = this.mSyncStorageEngine.getAuthority(syncHistoryItem2.authorityId);
                        if (authority3 != null) {
                            str8 = authority3.target.provider;
                            java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                            sb3.append(authority3.target.account.name);
                            str5 = str4;
                            sb3.append(str5);
                            sb3.append(authority3.target.account.type);
                            str6 = str3;
                            sb3.append(str6);
                            sb3.append(authority3.target.userId);
                            str7 = sb3.toString();
                        } else {
                            str5 = str4;
                            str6 = str3;
                            str7 = "Unknown";
                            str8 = str7;
                        }
                        printWriter.printf("  #%-3d: %s %8s ", java.lang.Integer.valueOf(i11 + 1), formatTime(syncHistoryItem2.eventTime), com.android.server.content.SyncStorageEngine.SOURCES[syncHistoryItem2.source]);
                        printWriter.printf(str25, str7, str8, bundle);
                    }
                    i11++;
                    i10 = i12;
                    arrayList2 = arrayList6;
                    str4 = str5;
                    str3 = str6;
                } else {
                    return;
                }
            }
        }
    }

    private void dumpDayStatistics(java.io.PrintWriter printWriter) {
        com.android.server.content.SyncStorageEngine.DayStats dayStats;
        int i;
        com.android.server.content.SyncStorageEngine.DayStats[] dayStatistics = this.mSyncStorageEngine.getDayStatistics();
        if (dayStatistics != null && dayStatistics[0] != null) {
            printWriter.println();
            printWriter.println("Sync Statistics");
            printWriter.print("  Today:  ");
            dumpDayStatistic(printWriter, dayStatistics[0]);
            int i2 = dayStatistics[0].day;
            int i3 = 1;
            while (i3 <= 6 && i3 < dayStatistics.length && (dayStats = dayStatistics[i3]) != null && (i = i2 - dayStats.day) <= 6) {
                printWriter.print("  Day-");
                printWriter.print(i);
                printWriter.print(":  ");
                dumpDayStatistic(printWriter, dayStats);
                i3++;
            }
            int i4 = i2;
            while (i3 < dayStatistics.length) {
                i4 -= 7;
                com.android.server.content.SyncStorageEngine.DayStats dayStats2 = null;
                while (true) {
                    if (i3 >= dayStatistics.length) {
                        break;
                    }
                    com.android.server.content.SyncStorageEngine.DayStats dayStats3 = dayStatistics[i3];
                    if (dayStats3 == null) {
                        i3 = dayStatistics.length;
                        break;
                    }
                    if (i4 - dayStats3.day > 6) {
                        break;
                    }
                    i3++;
                    if (dayStats2 == null) {
                        dayStats2 = new com.android.server.content.SyncStorageEngine.DayStats(i4);
                    }
                    dayStats2.successCount += dayStats3.successCount;
                    dayStats2.successTime += dayStats3.successTime;
                    dayStats2.failureCount += dayStats3.failureCount;
                    dayStats2.failureTime += dayStats3.failureTime;
                }
                if (dayStats2 != null) {
                    printWriter.print("  Week-");
                    printWriter.print((i2 - i4) / 7);
                    printWriter.print(": ");
                    dumpDayStatistic(printWriter, dayStats2);
                }
            }
        }
    }

    private void dumpSyncAdapters(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers();
        if (allUsers != null) {
            for (android.content.pm.UserInfo userInfo : allUsers) {
                indentingPrintWriter.println("Sync adapters for " + userInfo + ":");
                indentingPrintWriter.increaseIndent();
                java.util.Iterator it = this.mSyncAdapters.getAllServices(userInfo.id).iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println((android.content.pm.RegisteredServicesCache.ServiceInfo) it.next());
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            }
        }
    }

    private static class AuthoritySyncStats {
        java.util.Map<java.lang.String, com.android.server.content.SyncManager.AccountSyncStats> accountMap;
        long elapsedTime;
        java.lang.String name;
        int times;

        private AuthoritySyncStats(java.lang.String str) {
            this.accountMap = com.google.android.collect.Maps.newHashMap();
            this.name = str;
        }
    }

    private static class AccountSyncStats {
        long elapsedTime;
        java.lang.String name;
        int times;

        private AccountSyncStats(java.lang.String str) {
            this.name = str;
        }
    }

    static void sendOnUnsyncableAccount(@android.annotation.NonNull final android.content.Context context, @android.annotation.NonNull android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> serviceInfo, int i, @android.annotation.NonNull com.android.server.content.SyncManager.OnReadyCallback onReadyCallback) {
        final com.android.server.content.SyncManager.OnUnsyncableAccountCheck onUnsyncableAccountCheck = new com.android.server.content.SyncManager.OnUnsyncableAccountCheck(serviceInfo, onReadyCallback);
        if (context.bindServiceAsUser(getAdapterBindIntent(context, serviceInfo.componentName, i), onUnsyncableAccountCheck, SYNC_ADAPTER_CONNECTION_FLAGS, android.os.UserHandle.of(i))) {
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new java.lang.Runnable() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    context.unbindService(onUnsyncableAccountCheck);
                }
            }, 5000L);
        } else {
            onUnsyncableAccountCheck.onReady();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnUnsyncableAccountCheck implements android.content.ServiceConnection {
        static final long SERVICE_BOUND_TIME_MILLIS = 5000;

        @android.annotation.NonNull
        private final com.android.server.content.SyncManager.OnReadyCallback mOnReadyCallback;

        @android.annotation.NonNull
        private final android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> mSyncAdapterInfo;

        OnUnsyncableAccountCheck(@android.annotation.NonNull android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> serviceInfo, @android.annotation.NonNull com.android.server.content.SyncManager.OnReadyCallback onReadyCallback) {
            this.mSyncAdapterInfo = serviceInfo;
            this.mOnReadyCallback = onReadyCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onReady() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mOnReadyCallback.onReady();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            try {
                android.content.ISyncAdapter.Stub.asInterface(iBinder).onUnsyncableAccount(new android.content.ISyncAdapterUnsyncableAccountCallback.Stub() { // from class: com.android.server.content.SyncManager.OnUnsyncableAccountCheck.1
                    public void onUnsyncableAccountDone(boolean z) {
                        if (z) {
                            com.android.server.content.SyncManager.OnUnsyncableAccountCheck.this.onReady();
                        }
                    }
                });
            } catch (android.os.RemoteException e) {
                android.util.Slog.e("SyncManager", "Could not call onUnsyncableAccountDone " + this.mSyncAdapterInfo, e);
                onReady();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
        }
    }

    private class SyncTimeTracker {
        boolean mLastWasSyncing;
        private long mTimeSpentSyncing;
        long mWhenSyncStarted;

        private SyncTimeTracker() {
            this.mLastWasSyncing = false;
            this.mWhenSyncStarted = 0L;
        }

        public synchronized void update() {
            try {
                boolean z = !com.android.server.content.SyncManager.this.mActiveSyncContexts.isEmpty();
                if (z == this.mLastWasSyncing) {
                    return;
                }
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                if (z) {
                    this.mWhenSyncStarted = elapsedRealtime;
                } else {
                    this.mTimeSpentSyncing += elapsedRealtime - this.mWhenSyncStarted;
                }
                this.mLastWasSyncing = z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        public synchronized long timeSpentSyncing() {
            if (!this.mLastWasSyncing) {
                return this.mTimeSpentSyncing;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            return this.mTimeSpentSyncing + (elapsedRealtime - this.mWhenSyncStarted);
        }
    }

    class ServiceConnectionData {
        public final com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext;
        public final android.os.IBinder adapter;

        ServiceConnectionData(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext, android.os.IBinder iBinder) {
            this.activeSyncContext = activeSyncContext;
            this.adapter = iBinder;
        }
    }

    @android.annotation.Nullable
    private static com.android.server.content.SyncManager getInstance() {
        com.android.server.content.SyncManager syncManager;
        synchronized (com.android.server.content.SyncManager.class) {
            try {
                if (sInstance == null) {
                    android.util.Slog.wtf("SyncManager", "sInstance == null");
                }
                syncManager = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return syncManager;
    }

    public static boolean readyToSync(int i) {
        com.android.server.content.SyncManager syncManager = getInstance();
        return syncManager != null && com.android.server.content.SyncJobService.isReady() && syncManager.mProvisioned && syncManager.isUserUnlocked(i);
    }

    public static void sendMessage(android.os.Message message) {
        com.android.server.content.SyncManager syncManager = getInstance();
        if (syncManager != null && syncManager.mSyncHandler != null) {
            syncManager.mSyncHandler.sendMessage(message);
        }
    }

    class SyncHandler extends android.os.Handler {
        private static final int MESSAGE_ACCOUNTS_UPDATED = 9;
        private static final int MESSAGE_CANCEL = 6;
        private static final int MESSAGE_MONITOR_SYNC = 8;
        static final int MESSAGE_REMOVE_PERIODIC_SYNC = 14;
        static final int MESSAGE_SCHEDULE_SYNC = 12;
        private static final int MESSAGE_SERVICE_CONNECTED = 4;
        private static final int MESSAGE_SERVICE_DISCONNECTED = 5;
        static final int MESSAGE_START_SYNC = 10;
        static final int MESSAGE_STOP_SYNC = 11;
        private static final int MESSAGE_SYNC_FINISHED = 1;
        static final int MESSAGE_UPDATE_PERIODIC_SYNC = 13;
        public final com.android.server.content.SyncManager.SyncTimeTracker mSyncTimeTracker;
        private final java.util.HashMap<java.lang.String, android.os.PowerManager.WakeLock> mWakeLocks;

        public SyncHandler(android.os.Looper looper) {
            super(looper);
            this.mSyncTimeTracker = new com.android.server.content.SyncManager.SyncTimeTracker();
            this.mWakeLocks = com.google.android.collect.Maps.newHashMap();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.server.content.SyncManager.this.mSyncManagerWakeLock.acquire();
            try {
                handleSyncMessage(message);
            } finally {
                com.android.server.content.SyncManager.this.mSyncManagerWakeLock.release();
            }
        }

        private void handleSyncMessage(android.os.Message message) {
            boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
            try {
                com.android.server.content.SyncManager.this.mDataConnectionIsConnected = com.android.server.content.SyncManager.this.readDataConnectionState();
                switch (message.what) {
                    case 1:
                        com.android.server.content.SyncManager.SyncFinishedOrCancelledMessagePayload syncFinishedOrCancelledMessagePayload = (com.android.server.content.SyncManager.SyncFinishedOrCancelledMessagePayload) message.obj;
                        if (!com.android.server.content.SyncManager.this.isSyncStillActiveH(syncFinishedOrCancelledMessagePayload.activeSyncContext)) {
                            if (isLoggable) {
                                android.util.Log.d("SyncManager", "handleSyncHandlerMessage: dropping since the sync is no longer active: " + syncFinishedOrCancelledMessagePayload.activeSyncContext);
                            }
                        } else {
                            if (isLoggable) {
                                android.util.Slog.v("SyncManager", "syncFinished" + syncFinishedOrCancelledMessagePayload.activeSyncContext.mSyncOperation);
                            }
                            com.android.server.content.SyncJobService.callJobFinished(syncFinishedOrCancelledMessagePayload.activeSyncContext.mSyncOperation.jobId, false, "sync finished");
                            runSyncFinishedOrCanceledH(syncFinishedOrCancelledMessagePayload.syncResult, syncFinishedOrCancelledMessagePayload.activeSyncContext);
                        }
                        return;
                    case 2:
                    case 3:
                    case 7:
                    default:
                        return;
                    case 4:
                        com.android.server.content.SyncManager.ServiceConnectionData serviceConnectionData = (com.android.server.content.SyncManager.ServiceConnectionData) message.obj;
                        if (isLoggable) {
                            android.util.Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_SERVICE_CONNECTED: " + serviceConnectionData.activeSyncContext);
                        }
                        if (com.android.server.content.SyncManager.this.isSyncStillActiveH(serviceConnectionData.activeSyncContext)) {
                            runBoundToAdapterH(serviceConnectionData.activeSyncContext, serviceConnectionData.adapter);
                        }
                        return;
                    case 5:
                        com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext = ((com.android.server.content.SyncManager.ServiceConnectionData) message.obj).activeSyncContext;
                        if (isLoggable) {
                            android.util.Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_SERVICE_DISCONNECTED: " + activeSyncContext);
                        }
                        if (com.android.server.content.SyncManager.this.isSyncStillActiveH(activeSyncContext)) {
                            try {
                                if (activeSyncContext.mSyncAdapter != null) {
                                    com.android.server.content.SyncManager.this.mLogger.log("Calling cancelSync for SERVICE_DISCONNECTED ", activeSyncContext, " adapter=", activeSyncContext.mSyncAdapter);
                                    activeSyncContext.mSyncAdapter.cancelSync(activeSyncContext);
                                    com.android.server.content.SyncManager.this.mLogger.log("Canceled");
                                }
                            } catch (android.os.RemoteException e) {
                                com.android.server.content.SyncManager.this.mLogger.log("RemoteException ", android.util.Log.getStackTraceString(e));
                            }
                            android.content.SyncResult syncResult = new android.content.SyncResult();
                            syncResult.stats.numIoExceptions++;
                            com.android.server.content.SyncJobService.callJobFinished(activeSyncContext.mSyncOperation.jobId, false, "service disconnected");
                            runSyncFinishedOrCanceledH(syncResult, activeSyncContext);
                        }
                        return;
                    case 6:
                        com.android.server.content.SyncStorageEngine.EndPoint endPoint = (com.android.server.content.SyncStorageEngine.EndPoint) message.obj;
                        android.os.Bundle peekData = message.peekData();
                        if (isLoggable) {
                            android.util.Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_CANCEL: " + endPoint + " bundle: " + peekData);
                        }
                        cancelActiveSyncH(endPoint, peekData, "MESSAGE_CANCEL");
                        return;
                    case 8:
                        com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext2 = (com.android.server.content.SyncManager.ActiveSyncContext) message.obj;
                        if (isLoggable) {
                            android.util.Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_MONITOR_SYNC: " + activeSyncContext2.mSyncOperation.target);
                        }
                        if (isSyncNotUsingNetworkH(activeSyncContext2)) {
                            android.util.Log.w("SyncManager", java.lang.String.format("Detected sync making no progress for %s. cancelling.", com.android.server.content.SyncLogger.logSafe(activeSyncContext2)));
                            com.android.server.content.SyncJobService.callJobFinished(activeSyncContext2.mSyncOperation.jobId, false, "no network activity");
                            runSyncFinishedOrCanceledH(null, activeSyncContext2);
                        } else {
                            com.android.server.content.SyncManager.this.postMonitorSyncProgressMessage(activeSyncContext2);
                        }
                        return;
                    case 9:
                        if (android.util.Log.isLoggable("SyncManager", 2)) {
                            android.util.Slog.v("SyncManager", "handleSyncHandlerMessage: MESSAGE_ACCOUNTS_UPDATED");
                        }
                        updateRunningAccountsH((com.android.server.content.SyncStorageEngine.EndPoint) message.obj);
                        return;
                    case 10:
                        startSyncH((com.android.server.content.SyncOperation) message.obj);
                        return;
                    case 11:
                        com.android.server.content.SyncOperation syncOperation = (com.android.server.content.SyncOperation) message.obj;
                        if (isLoggable) {
                            android.util.Slog.v("SyncManager", "Stop sync received.");
                        }
                        com.android.server.content.SyncManager.ActiveSyncContext findActiveSyncContextH = findActiveSyncContextH(syncOperation.jobId);
                        if (findActiveSyncContextH != null) {
                            runSyncFinishedOrCanceledH(null, findActiveSyncContextH);
                            boolean z = message.arg1 != 0;
                            boolean z2 = message.arg2 != 0;
                            if (isLoggable) {
                                android.util.Slog.v("SyncManager", "Stopping sync. Reschedule: " + z + "Backoff: " + z2);
                            }
                            if (z2) {
                                com.android.server.content.SyncManager.this.increaseBackoffSetting(syncOperation.target);
                            }
                            if (z) {
                                deferStoppedSyncH(syncOperation, 0L);
                            }
                        }
                        return;
                    case 12:
                        com.android.server.content.SyncManager.ScheduleSyncMessagePayload scheduleSyncMessagePayload = (com.android.server.content.SyncManager.ScheduleSyncMessagePayload) message.obj;
                        com.android.server.content.SyncManager.this.scheduleSyncOperationH(scheduleSyncMessagePayload.syncOperation, scheduleSyncMessagePayload.minDelayMillis);
                        return;
                    case 13:
                        com.android.server.content.SyncManager.UpdatePeriodicSyncMessagePayload updatePeriodicSyncMessagePayload = (com.android.server.content.SyncManager.UpdatePeriodicSyncMessagePayload) message.obj;
                        updateOrAddPeriodicSyncH(updatePeriodicSyncMessagePayload.target, updatePeriodicSyncMessagePayload.pollFrequency, updatePeriodicSyncMessagePayload.flex, updatePeriodicSyncMessagePayload.extras);
                        return;
                    case 14:
                        android.util.Pair pair = (android.util.Pair) message.obj;
                        removePeriodicSyncH((com.android.server.content.SyncStorageEngine.EndPoint) pair.first, message.getData(), (java.lang.String) pair.second);
                        return;
                }
            } finally {
                this.mSyncTimeTracker.update();
            }
            this.mSyncTimeTracker.update();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.os.PowerManager.WakeLock getSyncWakeLock(com.android.server.content.SyncOperation syncOperation) {
            java.lang.String wakeLockName = syncOperation.wakeLockName();
            android.os.PowerManager.WakeLock wakeLock = this.mWakeLocks.get(wakeLockName);
            if (wakeLock == null) {
                android.os.PowerManager.WakeLock newWakeLock = com.android.server.content.SyncManager.this.mPowerManager.newWakeLock(1, com.android.server.content.SyncManager.SYNC_WAKE_LOCK_PREFIX + wakeLockName);
                newWakeLock.setReferenceCounted(false);
                this.mWakeLocks.put(wakeLockName, newWakeLock);
                return newWakeLock;
            }
            return wakeLock;
        }

        private void deferSyncH(com.android.server.content.SyncOperation syncOperation, long j, java.lang.String str) {
            com.android.server.content.SyncManager.this.mLogger.log("deferSyncH() ", syncOperation.isPeriodic ? "periodic " : "", "sync.  op=", syncOperation, " delay=", java.lang.Long.valueOf(j), " why=", str);
            com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, str);
            if (syncOperation.isPeriodic) {
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation.createOneTimeSyncOperation(), j);
            } else {
                com.android.server.content.SyncManager.this.cancelJob(syncOperation, "deferSyncH()");
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation, j);
            }
        }

        private void deferStoppedSyncH(com.android.server.content.SyncOperation syncOperation, long j) {
            if (syncOperation.isPeriodic) {
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation.createOneTimeSyncOperation(), j);
            } else {
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation, j);
            }
        }

        private void deferActiveSyncH(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext, java.lang.String str) {
            com.android.server.content.SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            runSyncFinishedOrCanceledH(null, activeSyncContext);
            deferSyncH(syncOperation, 10000L, str);
        }

        private void startSyncH(com.android.server.content.SyncOperation syncOperation) {
            boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
            if (isLoggable) {
                android.util.Slog.v("SyncManager", syncOperation.toString());
            }
            com.android.server.content.SyncManager.this.mSyncStorageEngine.setClockValid();
            com.android.server.content.SyncJobService.markSyncStarted(syncOperation.jobId);
            if (syncOperation.isPeriodic) {
                java.util.Iterator it = com.android.server.content.SyncManager.this.getAllPendingSyncs().iterator();
                while (it.hasNext()) {
                    if (((com.android.server.content.SyncOperation) it.next()).sourcePeriodicId == syncOperation.jobId) {
                        com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, "periodic sync, pending");
                        return;
                    }
                }
                java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it2 = com.android.server.content.SyncManager.this.mActiveSyncContexts.iterator();
                while (it2.hasNext()) {
                    if (it2.next().mSyncOperation.sourcePeriodicId == syncOperation.jobId) {
                        com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, "periodic sync, already running");
                        return;
                    }
                }
                if (com.android.server.content.SyncManager.this.isAdapterDelayed(syncOperation.target)) {
                    deferSyncH(syncOperation, 0L, "backing off");
                    return;
                }
            }
            java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it3 = com.android.server.content.SyncManager.this.mActiveSyncContexts.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                com.android.server.content.SyncManager.ActiveSyncContext next = it3.next();
                if (next.mSyncOperation.isConflict(syncOperation)) {
                    if (next.mSyncOperation.getJobBias() >= syncOperation.getJobBias()) {
                        if (isLoggable) {
                            android.util.Slog.v("SyncManager", "Rescheduling sync due to conflict " + syncOperation.toString());
                        }
                        deferSyncH(syncOperation, 10000L, "delay on conflict");
                        return;
                    }
                    if (isLoggable) {
                        android.util.Slog.v("SyncManager", "Pushing back running sync due to a higher priority sync");
                    }
                    deferActiveSyncH(next, "preempted");
                }
            }
            int computeSyncOpState = computeSyncOpState(syncOperation);
            if (computeSyncOpState != 0) {
                com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, "invalid op state: " + computeSyncOpState);
                return;
            }
            if (!dispatchSyncOperation(syncOperation)) {
                com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, "dispatchSyncOperation() failed");
            }
            com.android.server.content.SyncManager.this.setAuthorityPendingState(syncOperation.target);
        }

        private com.android.server.content.SyncManager.ActiveSyncContext findActiveSyncContextH(int i) {
            java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it = com.android.server.content.SyncManager.this.mActiveSyncContexts.iterator();
            while (it.hasNext()) {
                com.android.server.content.SyncManager.ActiveSyncContext next = it.next();
                com.android.server.content.SyncOperation syncOperation = next.mSyncOperation;
                if (syncOperation != null && syncOperation.jobId == i) {
                    return next;
                }
            }
            return null;
        }

        private void updateRunningAccountsH(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
            int i;
            synchronized (com.android.server.content.SyncManager.this.mAccountsLock) {
                try {
                    android.accounts.AccountAndUser[] accountAndUserArr = com.android.server.content.SyncManager.this.mRunningAccounts;
                    com.android.server.content.SyncManager.this.mRunningAccounts = com.android.server.accounts.AccountManagerService.getSingleton().getRunningAccountsForSystem();
                    if (android.util.Log.isLoggable("SyncManager", 2)) {
                        android.util.Slog.v("SyncManager", "Accounts list: ");
                        for (android.accounts.AccountAndUser accountAndUser : com.android.server.content.SyncManager.this.mRunningAccounts) {
                            android.util.Slog.v("SyncManager", accountAndUser.toString());
                        }
                    }
                    if (com.android.server.content.SyncManager.this.mLogger.enabled()) {
                        com.android.server.content.SyncManager.this.mLogger.log("updateRunningAccountsH: ", java.util.Arrays.toString(com.android.server.content.SyncManager.this.mRunningAccounts));
                    }
                    com.android.server.content.SyncManager.this.removeStaleAccounts();
                    android.accounts.AccountAndUser[] accountAndUserArr2 = com.android.server.content.SyncManager.this.mRunningAccounts;
                    int size = com.android.server.content.SyncManager.this.mActiveSyncContexts.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext = com.android.server.content.SyncManager.this.mActiveSyncContexts.get(i2);
                        if (!com.android.server.content.SyncManager.this.containsAccountAndUser(accountAndUserArr2, activeSyncContext.mSyncOperation.target.account, activeSyncContext.mSyncOperation.target.userId)) {
                            android.util.Log.d("SyncManager", "canceling sync since the account is no longer running");
                            com.android.server.content.SyncManager.this.sendSyncFinishedOrCanceledMessage(activeSyncContext, null);
                        }
                    }
                    if (endPoint != null) {
                        int length = com.android.server.content.SyncManager.this.mRunningAccounts.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length) {
                                break;
                            }
                            android.accounts.AccountAndUser accountAndUser2 = com.android.server.content.SyncManager.this.mRunningAccounts[i3];
                            if (com.android.server.content.SyncManager.this.containsAccountAndUser(accountAndUserArr, accountAndUser2.account, accountAndUser2.userId)) {
                                i3++;
                            } else {
                                if (android.util.Log.isLoggable("SyncManager", 3)) {
                                    android.util.Log.d("SyncManager", "Account " + accountAndUser2.account + " added, checking sync restore data");
                                }
                                com.android.server.backup.AccountSyncSettingsBackupHelper.accountAdded(com.android.server.content.SyncManager.this.mContext, endPoint.userId);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.accounts.AccountAndUser[] allAccountsForSystemProcess = com.android.server.accounts.AccountManagerService.getSingleton().getAllAccountsForSystemProcess();
            java.util.List allPendingSyncs = com.android.server.content.SyncManager.this.getAllPendingSyncs();
            int size2 = allPendingSyncs.size();
            for (i = 0; i < size2; i++) {
                com.android.server.content.SyncOperation syncOperation = (com.android.server.content.SyncOperation) allPendingSyncs.get(i);
                if (!com.android.server.content.SyncManager.this.containsAccountAndUser(allAccountsForSystemProcess, syncOperation.target.account, syncOperation.target.userId)) {
                    com.android.server.content.SyncManager.this.mLogger.log("canceling: ", syncOperation);
                    com.android.server.content.SyncManager.this.cancelJob(syncOperation, "updateRunningAccountsH()");
                }
            }
            if (endPoint != null) {
                com.android.server.content.SyncManager.this.scheduleSync(endPoint.account, endPoint.userId, -2, endPoint.provider, null, -1, 0, android.os.Process.myUid(), -4, null);
            }
        }

        private void maybeUpdateSyncPeriodH(com.android.server.content.SyncOperation syncOperation, long j, long j2) {
            if (j != syncOperation.periodMillis || j2 != syncOperation.flexMillis) {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "updating period " + syncOperation + " to " + j + " and flex to " + j2);
                }
                com.android.server.content.SyncOperation syncOperation2 = new com.android.server.content.SyncOperation(syncOperation, j, j2);
                syncOperation2.jobId = syncOperation.jobId;
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation2);
            }
        }

        private void updateOrAddPeriodicSyncH(final com.android.server.content.SyncStorageEngine.EndPoint endPoint, final long j, final long j2, final android.os.Bundle bundle) {
            boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
            com.android.server.content.SyncManager.this.verifyJobScheduler();
            long j3 = j * 1000;
            long j4 = j2 * 1000;
            if (isLoggable) {
                android.util.Slog.v("SyncManager", "Addition to periodic syncs requested: " + endPoint + " period: " + j + " flexMillis: " + j2 + " extras: " + bundle.toString());
            }
            java.util.Iterator it = com.android.server.content.SyncManager.this.getAllPendingSyncs().iterator();
            while (it.hasNext()) {
                com.android.server.content.SyncOperation syncOperation = (com.android.server.content.SyncOperation) it.next();
                if (syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                    if (syncOperation.areExtrasEqual(bundle, true)) {
                        java.util.Iterator it2 = it;
                        if (com.android.server.content.SyncManager.this.isPackageStopped(syncOperation.owningPackage, endPoint.userId)) {
                            it = it2;
                        } else {
                            maybeUpdateSyncPeriodH(syncOperation, j3, j4);
                            return;
                        }
                    }
                }
                it = it;
            }
            if (isLoggable) {
                android.util.Slog.v("SyncManager", "Adding new periodic sync: " + endPoint + " period: " + j + " flexMillis: " + j2 + " extras: " + bundle.toString());
            }
            android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = com.android.server.content.SyncManager.this.mSyncAdapters.getServiceInfo(android.content.SyncAdapterType.newKey(endPoint.provider, endPoint.account.type), endPoint.userId);
            if (serviceInfo == null) {
                return;
            }
            com.android.server.content.SyncOperation syncOperation2 = new com.android.server.content.SyncOperation(endPoint, serviceInfo.uid, serviceInfo.componentName.getPackageName(), -4, 4, bundle, ((android.content.SyncAdapterType) serviceInfo.type).allowParallelSyncs(), true, -1, j3, j4, 0);
            int computeSyncOpState = computeSyncOpState(syncOperation2);
            if (computeSyncOpState != 2) {
                if (computeSyncOpState == 0) {
                    com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation2);
                    com.android.server.content.SyncManager.this.mSyncStorageEngine.reportChange(1, syncOperation2.owningPackage, endPoint.userId);
                    return;
                } else {
                    com.android.server.content.SyncManager.this.mLogger.log("syncOpState=", java.lang.Integer.valueOf(computeSyncOpState));
                    return;
                }
            }
            java.lang.String str = syncOperation2.owningPackage;
            int userId = android.os.UserHandle.getUserId(syncOperation2.owningUid);
            if (!com.android.server.content.SyncManager.this.wasPackageEverLaunched(str, userId)) {
                return;
            }
            com.android.server.content.SyncManager.this.mLogger.log("requestAccountAccess for SYNC_OP_STATE_INVALID_NO_ACCOUNT_ACCESS");
            com.android.server.content.SyncManager.this.mAccountManagerInternal.requestAccountAccess(syncOperation2.target.account, str, userId, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.content.SyncManager$SyncHandler$$ExternalSyntheticLambda0
                public final void onResult(android.os.Bundle bundle2) {
                    com.android.server.content.SyncManager.SyncHandler.this.lambda$updateOrAddPeriodicSyncH$0(endPoint, j, j2, bundle, bundle2);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateOrAddPeriodicSyncH$0(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j, long j2, android.os.Bundle bundle, android.os.Bundle bundle2) {
            if (bundle2 != null && bundle2.getBoolean("booleanResult")) {
                com.android.server.content.SyncManager.this.updateOrAddPeriodicSync(endPoint, j, j2, bundle);
            }
        }

        private void removePeriodicSyncInternalH(com.android.server.content.SyncOperation syncOperation, java.lang.String str) {
            for (com.android.server.content.SyncOperation syncOperation2 : com.android.server.content.SyncManager.this.getAllPendingSyncs()) {
                if (syncOperation2.sourcePeriodicId == syncOperation.jobId || syncOperation2.jobId == syncOperation.jobId) {
                    com.android.server.content.SyncManager.ActiveSyncContext findActiveSyncContextH = findActiveSyncContextH(syncOperation.jobId);
                    if (findActiveSyncContextH != null) {
                        com.android.server.content.SyncJobService.callJobFinished(syncOperation.jobId, false, "removePeriodicSyncInternalH");
                        runSyncFinishedOrCanceledH(null, findActiveSyncContextH);
                    }
                    com.android.server.content.SyncManager.this.mLogger.log("removePeriodicSyncInternalH-canceling: ", syncOperation2);
                    com.android.server.content.SyncManager.this.cancelJob(syncOperation2, str);
                }
            }
        }

        private void removePeriodicSyncH(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, java.lang.String str) {
            com.android.server.content.SyncManager.this.verifyJobScheduler();
            for (com.android.server.content.SyncOperation syncOperation : com.android.server.content.SyncManager.this.getAllPendingSyncs()) {
                if (syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint) && syncOperation.areExtrasEqual(bundle, true)) {
                    removePeriodicSyncInternalH(syncOperation, str);
                }
            }
        }

        private boolean isSyncNotUsingNetworkH(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
            long totalBytesTransferredByUid = com.android.server.content.SyncManager.this.getTotalBytesTransferredByUid(activeSyncContext.mSyncAdapterUid) - activeSyncContext.mBytesTransferredAtLastPoll;
            if (android.util.Log.isLoggable("SyncManager", 3)) {
                long j = totalBytesTransferredByUid % 1048576;
                android.util.Log.d("SyncManager", java.lang.String.format("Time since last update: %ds. Delta transferred: %dMBs,%dKBs,%dBs", java.lang.Long.valueOf((android.os.SystemClock.elapsedRealtime() - activeSyncContext.mLastPolledTimeElapsed) / 1000), java.lang.Long.valueOf(totalBytesTransferredByUid / 1048576), java.lang.Long.valueOf(j / 1024), java.lang.Long.valueOf(j % 1024)));
            }
            return totalBytesTransferredByUid <= 10;
        }

        private int computeSyncOpState(com.android.server.content.SyncOperation syncOperation) {
            boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
            com.android.server.content.SyncStorageEngine.EndPoint endPoint = syncOperation.target;
            synchronized (com.android.server.content.SyncManager.this.mAccountsLock) {
                try {
                    if (!com.android.server.content.SyncManager.this.containsAccountAndUser(com.android.server.content.SyncManager.this.mRunningAccounts, endPoint.account, endPoint.userId)) {
                        if (isLoggable) {
                            android.util.Slog.v("SyncManager", "    Dropping sync operation: account doesn't exist.");
                        }
                        logAccountError("SYNC_OP_STATE_INVALID: account doesn't exist.");
                        return 3;
                    }
                    int computeSyncable = com.android.server.content.SyncManager.this.computeSyncable(endPoint.account, endPoint.userId, endPoint.provider, true, true);
                    if (computeSyncable == 3) {
                        if (isLoggable) {
                            android.util.Slog.v("SyncManager", "    Dropping sync operation: isSyncable == SYNCABLE_NO_ACCOUNT_ACCESS");
                        }
                        logAccountError("SYNC_OP_STATE_INVALID_NO_ACCOUNT_ACCESS");
                        return 2;
                    }
                    if (computeSyncable == 0) {
                        if (isLoggable) {
                            android.util.Slog.v("SyncManager", "    Dropping sync operation: isSyncable == NOT_SYNCABLE");
                        }
                        logAccountError("SYNC_OP_STATE_INVALID: NOT_SYNCABLE");
                        return 4;
                    }
                    boolean z = true;
                    boolean z2 = com.android.server.content.SyncManager.this.mSyncStorageEngine.getMasterSyncAutomatically(endPoint.userId) && com.android.server.content.SyncManager.this.mSyncStorageEngine.getSyncAutomatically(endPoint.account, endPoint.userId, endPoint.provider);
                    if (!syncOperation.isIgnoreSettings() && computeSyncable >= 0) {
                        z = false;
                    }
                    if (z2 || z) {
                        return 0;
                    }
                    if (isLoggable) {
                        android.util.Slog.v("SyncManager", "    Dropping sync operation: disallowed by settings/network.");
                    }
                    logAccountError("SYNC_OP_STATE_INVALID: disallowed by settings/network");
                    return 5;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void logAccountError(java.lang.String str) {
            android.util.Slog.wtf("SyncManager", str);
        }

        private boolean dispatchSyncOperation(com.android.server.content.SyncOperation syncOperation) {
            android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal;
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", "dispatchSyncOperation: we are going to sync " + syncOperation);
                android.util.Slog.v("SyncManager", "num active syncs: " + com.android.server.content.SyncManager.this.mActiveSyncContexts.size());
                java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it = com.android.server.content.SyncManager.this.mActiveSyncContexts.iterator();
                while (it.hasNext()) {
                    android.util.Slog.v("SyncManager", it.next().toString());
                }
            }
            if (syncOperation.isAppStandbyExempted() && (usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)) != null) {
                usageStatsManagerInternal.reportExemptedSyncStart(syncOperation.owningPackage, android.os.UserHandle.getUserId(syncOperation.owningUid));
            }
            com.android.server.content.SyncStorageEngine.EndPoint endPoint = syncOperation.target;
            android.content.SyncAdapterType newKey = android.content.SyncAdapterType.newKey(endPoint.provider, endPoint.account.type);
            android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo = com.android.server.content.SyncManager.this.mSyncAdapters.getServiceInfo(newKey, endPoint.userId);
            if (serviceInfo == null) {
                com.android.server.content.SyncManager.this.mLogger.log("dispatchSyncOperation() failed: no sync adapter info for ", newKey);
                android.util.Log.d("SyncManager", "can't find a sync adapter for " + newKey + ", removing settings for it");
                com.android.server.content.SyncManager.this.mSyncStorageEngine.removeAuthority(endPoint);
                return false;
            }
            int i = serviceInfo.uid;
            android.content.ComponentName componentName = serviceInfo.componentName;
            com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext = com.android.server.content.SyncManager.this.new ActiveSyncContext(syncOperation, insertStartSyncEvent(syncOperation), i);
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", "dispatchSyncOperation: starting " + activeSyncContext);
            }
            activeSyncContext.mSyncInfo = com.android.server.content.SyncManager.this.mSyncStorageEngine.addActiveSync(activeSyncContext);
            com.android.server.content.SyncManager.this.mActiveSyncContexts.add(activeSyncContext);
            com.android.server.content.SyncManager.this.postMonitorSyncProgressMessage(activeSyncContext);
            if (!activeSyncContext.bindToSyncAdapter(componentName, endPoint.userId)) {
                com.android.server.content.SyncManager.this.mLogger.log("dispatchSyncOperation() failed: bind failed. target: ", componentName);
                android.util.Slog.e("SyncManager", "Bind attempt failed - target: " + componentName);
                closeActiveSyncContext(activeSyncContext);
                return false;
            }
            return true;
        }

        private void runBoundToAdapterH(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext, android.os.IBinder iBinder) {
            com.android.server.content.SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            try {
                activeSyncContext.mIsLinkedToDeath = true;
                iBinder.linkToDeath(activeSyncContext, 0);
                if (com.android.server.content.SyncManager.this.mLogger.enabled()) {
                    com.android.server.content.SyncManager.this.mLogger.log("Sync start: account=" + syncOperation.target.account, " authority=", syncOperation.target.provider, " reason=", com.android.server.content.SyncOperation.reasonToString(null, syncOperation.reason), " extras=", syncOperation.getExtrasAsString(), " adapter=", activeSyncContext.mSyncAdapter);
                }
                activeSyncContext.mSyncAdapter = android.content.ISyncAdapter.Stub.asInterface(iBinder);
                activeSyncContext.mSyncAdapter.startSync(activeSyncContext, syncOperation.target.provider, syncOperation.target.account, syncOperation.getClonedExtras());
                com.android.server.content.SyncManager.this.mLogger.log("Sync is running now...");
            } catch (android.os.RemoteException e) {
                com.android.server.content.SyncManager.this.mLogger.log("Sync failed with RemoteException: ", e.toString());
                android.util.Log.d("SyncManager", "maybeStartNextSync: caught a RemoteException, rescheduling", e);
                closeActiveSyncContext(activeSyncContext);
                com.android.server.content.SyncManager.this.increaseBackoffSetting(syncOperation.target);
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation);
            } catch (java.lang.RuntimeException e2) {
                com.android.server.content.SyncManager.this.mLogger.log("Sync failed with RuntimeException: ", e2.toString());
                closeActiveSyncContext(activeSyncContext);
                android.util.Slog.e("SyncManager", "Caught RuntimeException while starting the sync " + com.android.server.content.SyncLogger.logSafe(syncOperation), e2);
            }
        }

        private void cancelActiveSyncH(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, java.lang.String str) {
            java.util.Iterator it = new java.util.ArrayList(com.android.server.content.SyncManager.this.mActiveSyncContexts).iterator();
            while (it.hasNext()) {
                com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext = (com.android.server.content.SyncManager.ActiveSyncContext) it.next();
                if (activeSyncContext != null && activeSyncContext.mSyncOperation.target.matchesSpec(endPoint) && (bundle == null || activeSyncContext.mSyncOperation.areExtrasEqual(bundle, false))) {
                    com.android.server.content.SyncJobService.callJobFinished(activeSyncContext.mSyncOperation.jobId, false, str);
                    runSyncFinishedOrCanceledH(null, activeSyncContext);
                }
            }
        }

        private void reschedulePeriodicSyncH(com.android.server.content.SyncOperation syncOperation) {
            com.android.server.content.SyncOperation syncOperation2;
            java.util.Iterator it = com.android.server.content.SyncManager.this.getAllPendingSyncs().iterator();
            while (true) {
                if (!it.hasNext()) {
                    syncOperation2 = null;
                    break;
                }
                syncOperation2 = (com.android.server.content.SyncOperation) it.next();
                if (syncOperation2.isPeriodic && syncOperation.matchesPeriodicOperation(syncOperation2)) {
                    break;
                }
            }
            if (syncOperation2 == null) {
                return;
            }
            com.android.server.content.SyncManager.this.scheduleSyncOperationH(syncOperation2);
        }

        private void runSyncFinishedOrCanceledH(android.content.SyncResult syncResult, com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
            java.lang.String str;
            java.lang.String syncErrorToString;
            boolean isLoggable = android.util.Log.isLoggable("SyncManager", 2);
            com.android.server.content.SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            com.android.server.content.SyncStorageEngine.EndPoint endPoint = syncOperation.target;
            if (activeSyncContext.mIsLinkedToDeath) {
                try {
                    activeSyncContext.mSyncAdapter.asBinder().unlinkToDeath(activeSyncContext, 0);
                    activeSyncContext.mIsLinkedToDeath = false;
                } catch (java.util.NoSuchElementException e) {
                    android.util.Slog.wtf("SyncManager", "Failed to unlink active sync adapter to death", e);
                }
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - activeSyncContext.mStartTime;
            com.android.server.content.SyncManager.this.mLogger.log("runSyncFinishedOrCanceledH() op=", syncOperation, " result=", syncResult);
            if (syncResult != null) {
                if (isLoggable) {
                    android.util.Slog.v("SyncManager", "runSyncFinishedOrCanceled [finished]: " + syncOperation + ", result " + syncResult);
                }
                closeActiveSyncContext(activeSyncContext);
                if (!syncOperation.isPeriodic) {
                    com.android.server.content.SyncManager.this.cancelJob(syncOperation, "runSyncFinishedOrCanceledH()-finished");
                }
                if (!syncResult.hasError()) {
                    com.android.server.content.SyncManager.this.clearBackoffSetting(syncOperation.target, "sync success");
                    if (syncOperation.isDerivedFromFailedPeriodicSync()) {
                        reschedulePeriodicSyncH(syncOperation);
                    }
                    syncErrorToString = com.android.server.content.SyncStorageEngine.MESG_SUCCESS;
                } else {
                    android.util.Log.w("SyncManager", "failed sync operation " + com.android.server.content.SyncLogger.logSafe(syncOperation) + ", " + syncResult);
                    syncOperation.retries = syncOperation.retries + 1;
                    if (syncOperation.retries > com.android.server.content.SyncManager.this.mConstants.getMaxRetriesWithAppStandbyExemption()) {
                        syncOperation.syncExemptionFlag = 0;
                    }
                    com.android.server.content.SyncManager.this.increaseBackoffSetting(syncOperation.target);
                    if (!syncOperation.isPeriodic) {
                        com.android.server.content.SyncManager.this.maybeRescheduleSync(syncResult, syncOperation);
                    } else {
                        com.android.server.content.SyncManager.this.postScheduleSyncMessage(syncOperation.createOneTimeSyncOperation(), 0L);
                    }
                    syncErrorToString = android.content.ContentResolver.syncErrorToString(syncResultToErrorNumber(syncResult));
                }
                com.android.server.content.SyncManager.this.setDelayUntilTime(syncOperation.target, syncResult.delayUntil);
                str = syncErrorToString;
            } else {
                if (isLoggable) {
                    android.util.Slog.v("SyncManager", "runSyncFinishedOrCanceled [canceled]: " + syncOperation);
                }
                if (!syncOperation.isPeriodic) {
                    com.android.server.content.SyncManager.this.cancelJob(syncOperation, "runSyncFinishedOrCanceledH()-canceled");
                }
                if (activeSyncContext.mSyncAdapter != null) {
                    try {
                        com.android.server.content.SyncManager.this.mLogger.log("Calling cancelSync for runSyncFinishedOrCanceled ", activeSyncContext, "  adapter=", activeSyncContext.mSyncAdapter);
                        activeSyncContext.mSyncAdapter.cancelSync(activeSyncContext);
                        com.android.server.content.SyncManager.this.mLogger.log("Canceled");
                    } catch (android.os.RemoteException e2) {
                        com.android.server.content.SyncManager.this.mLogger.log("RemoteException ", android.util.Log.getStackTraceString(e2));
                    }
                }
                closeActiveSyncContext(activeSyncContext);
                str = com.android.server.content.SyncStorageEngine.MESG_CANCELED;
            }
            stopSyncEvent(activeSyncContext.mHistoryRowId, syncOperation, str, 0, 0, elapsedRealtime);
            if (syncResult != null && syncResult.tooManyDeletions) {
                installHandleTooManyDeletesNotification(endPoint.account, endPoint.provider, syncResult.stats.numDeletes, endPoint.userId);
            } else {
                com.android.server.content.SyncManager.this.mNotificationMgr.cancelAsUser(java.lang.Integer.toString(endPoint.account.hashCode() ^ endPoint.provider.hashCode()), 18, new android.os.UserHandle(endPoint.userId));
            }
            if (syncResult != null && syncResult.fullSyncRequested) {
                com.android.server.content.SyncManager.this.scheduleSyncOperationH(new com.android.server.content.SyncOperation(endPoint.account, endPoint.userId, syncOperation.owningUid, syncOperation.owningPackage, syncOperation.reason, syncOperation.syncSource, endPoint.provider, new android.os.Bundle(), syncOperation.allowParallelSyncs, syncOperation.syncExemptionFlag));
            }
        }

        private void closeActiveSyncContext(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
            activeSyncContext.close();
            com.android.server.content.SyncManager.this.mActiveSyncContexts.remove(activeSyncContext);
            com.android.server.content.SyncManager.this.mSyncStorageEngine.removeActiveSync(activeSyncContext.mSyncInfo, activeSyncContext.mSyncOperation.target.userId);
            if (android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", "removing all MESSAGE_MONITOR_SYNC & MESSAGE_SYNC_EXPIRED for " + activeSyncContext.toString());
            }
            com.android.server.content.SyncManager.this.mSyncHandler.removeMessages(8, activeSyncContext);
            com.android.server.content.SyncManager.this.mLogger.log("closeActiveSyncContext: ", activeSyncContext);
        }

        private int syncResultToErrorNumber(android.content.SyncResult syncResult) {
            if (syncResult.syncAlreadyInProgress) {
                return 1;
            }
            if (syncResult.stats.numAuthExceptions > 0) {
                return 2;
            }
            if (syncResult.stats.numIoExceptions > 0) {
                return 3;
            }
            if (syncResult.stats.numParseExceptions > 0) {
                return 4;
            }
            if (syncResult.stats.numConflictDetectedExceptions > 0) {
                return 5;
            }
            if (syncResult.tooManyDeletions) {
                return 6;
            }
            if (syncResult.tooManyRetries) {
                return 7;
            }
            if (syncResult.databaseError) {
                return 8;
            }
            throw new java.lang.IllegalStateException("we are not in an error state, " + syncResult);
        }

        private void installHandleTooManyDeletesNotification(android.accounts.Account account, java.lang.String str, long j, int i) {
            android.content.pm.ProviderInfo resolveContentProvider;
            if (com.android.server.content.SyncManager.this.mNotificationMgr == null || (resolveContentProvider = com.android.server.content.SyncManager.this.mContext.getPackageManager().resolveContentProvider(str, 0)) == null) {
                return;
            }
            java.lang.CharSequence loadLabel = resolveContentProvider.loadLabel(com.android.server.content.SyncManager.this.mContext.getPackageManager());
            android.content.Intent intent = new android.content.Intent(com.android.server.content.SyncManager.this.mContext, (java.lang.Class<?>) android.content.SyncActivityTooManyDeletes.class);
            intent.putExtra("account", account);
            intent.putExtra("authority", str);
            intent.putExtra("provider", loadLabel.toString());
            intent.putExtra("numDeletes", j);
            if (!isActivityAvailable(intent)) {
                android.util.Log.w("SyncManager", "No activity found to handle too many deletes.");
                return;
            }
            android.os.UserHandle userHandle = new android.os.UserHandle(i);
            android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(com.android.server.content.SyncManager.this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF, null, userHandle);
            java.lang.CharSequence text = com.android.server.content.SyncManager.this.mContext.getResources().getText(android.R.string.config_work_badge_path_24);
            android.content.Context contextForUser = com.android.server.content.SyncManager.this.getContextForUser(userHandle);
            android.app.Notification build = new android.app.Notification.Builder(contextForUser, com.android.internal.notification.SystemNotificationChannels.ACCOUNT).setSmallIcon(android.R.drawable.stat_notify_rssi_in_range).setTicker(com.android.server.content.SyncManager.this.mContext.getString(android.R.string.config_wlan_network_service_class)).setWhen(java.lang.System.currentTimeMillis()).setColor(contextForUser.getColor(android.R.color.system_notification_accent_color)).setContentTitle(contextForUser.getString(android.R.string.config_wlan_network_service_package)).setContentText(java.lang.String.format(text.toString(), loadLabel)).setContentIntent(activityAsUser).build();
            build.flags |= 2;
            com.android.server.content.SyncManager.this.mNotificationMgr.notifyAsUser(java.lang.Integer.toString(account.hashCode() ^ str.hashCode()), 18, build, userHandle);
        }

        private boolean isActivityAvailable(android.content.Intent intent) {
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = com.android.server.content.SyncManager.this.mContext.getPackageManager().queryIntentActivities(intent, 0);
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if ((queryIntentActivities.get(i).activityInfo.applicationInfo.flags & 1) != 0) {
                    return true;
                }
            }
            return false;
        }

        public long insertStartSyncEvent(com.android.server.content.SyncOperation syncOperation) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.util.EventLog.writeEvent(2720, syncOperation.toEventLog(0));
            return com.android.server.content.SyncManager.this.mSyncStorageEngine.insertStartSyncEvent(syncOperation, currentTimeMillis);
        }

        public void stopSyncEvent(long j, com.android.server.content.SyncOperation syncOperation, java.lang.String str, int i, int i2, long j2) {
            android.util.EventLog.writeEvent(2720, syncOperation.toEventLog(1));
            com.android.server.content.SyncManager.this.mSyncStorageEngine.stopSyncEvent(j, j2, str, i2, i, syncOperation.owningPackage, syncOperation.target.userId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSyncStillActiveH(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
        java.util.Iterator<com.android.server.content.SyncManager.ActiveSyncContext> it = this.mActiveSyncContexts.iterator();
        while (it.hasNext()) {
            if (it.next() == activeSyncContext) {
                return true;
            }
        }
        return false;
    }

    public static boolean syncExtrasEquals(android.os.Bundle bundle, android.os.Bundle bundle2, boolean z) {
        if (bundle == bundle2) {
            return true;
        }
        if (z && bundle.size() != bundle2.size()) {
            return false;
        }
        android.os.Bundle bundle3 = bundle.size() > bundle2.size() ? bundle : bundle2;
        if (bundle.size() > bundle2.size()) {
            bundle = bundle2;
        }
        for (java.lang.String str : bundle3.keySet()) {
            if (z || !isSyncSetting(str)) {
                if (!bundle.containsKey(str) || !java.util.Objects.equals(bundle3.get(str), bundle.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSyncSetting(java.lang.String str) {
        if (str == null) {
            return false;
        }
        return str.equals("expedited") || str.equals("schedule_as_expedited_job") || str.equals("ignore_settings") || str.equals("ignore_backoff") || str.equals("do_not_retry") || str.equals("force") || str.equals("upload") || str.equals("deletions_override") || str.equals("discard_deletions") || str.equals("expected_upload") || str.equals("expected_download") || str.equals("sync_priority") || str.equals("allow_metered") || str.equals("initialize");
    }

    static class PrintTable {
        private final int mCols;
        private java.util.ArrayList<java.lang.String[]> mTable = com.google.android.collect.Lists.newArrayList();

        PrintTable(int i) {
            this.mCols = i;
        }

        void set(int i, int i2, java.lang.Object... objArr) {
            int i3;
            if (objArr.length + i2 > this.mCols) {
                throw new java.lang.IndexOutOfBoundsException("Table only has " + this.mCols + " columns. can't set " + objArr.length + " at column " + i2);
            }
            int size = this.mTable.size();
            while (true) {
                i3 = 0;
                if (size > i) {
                    break;
                }
                java.lang.String[] strArr = new java.lang.String[this.mCols];
                this.mTable.add(strArr);
                while (i3 < this.mCols) {
                    strArr[i3] = "";
                    i3++;
                }
                size++;
            }
            java.lang.String[] strArr2 = this.mTable.get(i);
            while (i3 < objArr.length) {
                java.lang.Object obj = objArr[i3];
                strArr2[i2 + i3] = obj == null ? "" : obj.toString();
                i3++;
            }
        }

        void writeTo(java.io.PrintWriter printWriter) {
            java.lang.String[] strArr = new java.lang.String[this.mCols];
            int i = 0;
            for (int i2 = 0; i2 < this.mCols; i2++) {
                java.util.Iterator<java.lang.String[]> it = this.mTable.iterator();
                int i3 = 0;
                while (it.hasNext()) {
                    int length = it.next()[i2].toString().length();
                    if (length > i3) {
                        i3 = length;
                    }
                }
                i += i3;
                strArr[i2] = java.lang.String.format("%%-%ds", java.lang.Integer.valueOf(i3));
            }
            strArr[this.mCols - 1] = "%s";
            printRow(printWriter, strArr, this.mTable.get(0));
            int i4 = i + ((this.mCols - 1) * 2);
            for (int i5 = 0; i5 < i4; i5++) {
                printWriter.print("-");
            }
            printWriter.println();
            int size = this.mTable.size();
            for (int i6 = 1; i6 < size; i6++) {
                printRow(printWriter, strArr, this.mTable.get(i6));
            }
        }

        private void printRow(java.io.PrintWriter printWriter, java.lang.String[] strArr, java.lang.Object[] objArr) {
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                printWriter.printf(java.lang.String.format(strArr[i], objArr[i].toString()), new java.lang.Object[0]);
                printWriter.print("  ");
            }
            printWriter.println();
        }

        public int getNumRows() {
            return this.mTable.size();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Context getContextForUser(android.os.UserHandle userHandle) {
        try {
            return this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return this.mContext;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelJob(com.android.server.content.SyncOperation syncOperation, java.lang.String str) {
        if (syncOperation == null) {
            android.util.Slog.wtf("SyncManager", "Null sync operation detected.");
            return;
        }
        if (syncOperation.isPeriodic) {
            this.mLogger.log("Removing periodic sync ", syncOperation, " for ", str);
        }
        getJobScheduler().cancel(syncOperation.jobId);
    }

    public void resetTodayStats() {
        this.mSyncStorageEngine.resetTodayStats(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean wasPackageEverLaunched(java.lang.String str, int i) {
        try {
            return this.mPackageManagerInternal.wasPackageEverLaunched(str, i);
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }
}
