package com.android.server.rollback;

/* loaded from: classes2.dex */
class RollbackManagerServiceImpl extends android.content.rollback.IRollbackManager.Stub implements com.android.server.rollback.RollbackManagerInternal {
    private final com.android.server.rollback.AppDataRollbackHelper mAppDataRollbackHelper;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private final android.os.Handler mHandler;
    private final com.android.server.pm.Installer mInstaller;
    private final com.android.server.rollback.RollbackPackageHealthObserver mPackageHealthObserver;
    private final com.android.server.rollback.RollbackStore mRollbackStore;
    private static final java.lang.String TAG = "RollbackManager";
    private static final boolean LOCAL_LOGV = android.util.Log.isLoggable(TAG, 2);
    private static final long DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS = java.util.concurrent.TimeUnit.DAYS.toMillis(14);
    private static final long HANDLER_THREAD_TIMEOUT_DURATION_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);
    private long mRollbackLifetimeDurationInMillis = DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS;
    private final java.util.Random mRandom = new java.security.SecureRandom();
    private final android.util.SparseBooleanArray mAllocatedRollbackIds = new android.util.SparseBooleanArray();
    private final java.util.List<com.android.server.rollback.Rollback> mRollbacks = new java.util.ArrayList();
    private final java.lang.Runnable mRunExpiration = new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda7
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.rollback.RollbackManagerServiceImpl.this.runExpiration();
        }
    };
    private final android.util.LongArrayQueue mSleepDuration = new android.util.LongArrayQueue();
    private long mRelativeBootTime = calculateRelativeBootTime();

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ExtThread {
    }

    RollbackManagerServiceImpl(final android.content.Context context) {
        this.mContext = context;
        this.mInstaller = new com.android.server.pm.Installer(this.mContext);
        this.mInstaller.onStart();
        this.mRollbackStore = new com.android.server.rollback.RollbackStore(new java.io.File(android.os.Environment.getDataDirectory(), "rollback"), new java.io.File(android.os.Environment.getDataDirectory(), "rollback-history"));
        this.mPackageHealthObserver = new com.android.server.rollback.RollbackPackageHealthObserver(this.mContext);
        this.mAppDataRollbackHelper = new com.android.server.rollback.AppDataRollbackHelper(this.mInstaller);
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("RollbackManagerServiceHandler");
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper());
        com.android.server.Watchdog.getInstance().addThread(getHandler(), HANDLER_THREAD_TIMEOUT_DURATION_MILLIS);
        this.mExecutor = new android.os.HandlerExecutor(getHandler());
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$new$0(context);
            }
        });
        java.util.Iterator it = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getUserHandles(true).iterator();
        while (it.hasNext()) {
            registerUserCallbacks((android.os.UserHandle) it.next());
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ENABLE_ROLLBACK");
        try {
            intentFilter.addDataType("application/vnd.android.package-archive");
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            android.util.Slog.e(TAG, "addDataType", e);
        }
        this.mContext.registerReceiver(new com.android.server.rollback.RollbackManagerServiceImpl.AnonymousClass1(), intentFilter, null, getHandler());
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.CANCEL_ENABLE_ROLLBACK");
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.rollback.RollbackManagerServiceImpl.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
                if ("android.intent.action.CANCEL_ENABLE_ROLLBACK".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_SESSION_ID, -1);
                    if (com.android.server.rollback.RollbackManagerServiceImpl.LOCAL_LOGV) {
                        android.util.Slog.v(com.android.server.rollback.RollbackManagerServiceImpl.TAG, "broadcast=ACTION_CANCEL_ENABLE_ROLLBACK id=" + intExtra);
                    }
                    com.android.server.rollback.Rollback rollbackForSession = com.android.server.rollback.RollbackManagerServiceImpl.this.getRollbackForSession(intExtra);
                    if (rollbackForSession != null && rollbackForSession.isEnabling()) {
                        com.android.server.rollback.RollbackManagerServiceImpl.this.mRollbacks.remove(rollbackForSession);
                        com.android.server.rollback.RollbackManagerServiceImpl.this.deleteRollback(rollbackForSession, "Rollback canceled");
                    }
                }
            }
        }, intentFilter2, null, getHandler());
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.rollback.RollbackManagerServiceImpl.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                int intExtra;
                com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
                if (!"android.intent.action.USER_ADDED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) == -1) {
                    return;
                }
                com.android.server.rollback.RollbackManagerServiceImpl.this.registerUserCallbacks(android.os.UserHandle.of(intExtra));
            }
        }, new android.content.IntentFilter("android.intent.action.USER_ADDED"), null, getHandler());
        registerTimeChangeReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.Context context) {
        this.mRollbacks.addAll(this.mRollbackStore.loadRollbacks());
        if (!context.getPackageManager().isDeviceUpgrading()) {
            java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
            while (it.hasNext()) {
                this.mAllocatedRollbackIds.put(it.next().info.getRollbackId(), true);
            }
            return;
        }
        java.util.Iterator<com.android.server.rollback.Rollback> it2 = this.mRollbacks.iterator();
        while (it2.hasNext()) {
            deleteRollback(it2.next(), "Fingerprint changed");
        }
        this.mRollbacks.clear();
    }

    /* renamed from: com.android.server.rollback.RollbackManagerServiceImpl$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
            if ("android.intent.action.PACKAGE_ENABLE_ROLLBACK".equals(intent.getAction())) {
                final int intExtra = intent.getIntExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_TOKEN, -1);
                final int intExtra2 = intent.getIntExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_SESSION_ID, -1);
                com.android.server.rollback.RollbackManagerServiceImpl.this.queueSleepIfNeeded();
                com.android.server.rollback.RollbackManagerServiceImpl.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackManagerServiceImpl.AnonymousClass1.this.lambda$onReceive$0(intExtra2, intExtra);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(int i, int i2) {
            int i3;
            com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
            if (com.android.server.rollback.RollbackManagerServiceImpl.this.enableRollback(i)) {
                i3 = 1;
            } else {
                i3 = -1;
            }
            ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).setEnableRollbackCode(i2, i3);
        }
    }

    private <U> U awaitResult(java.util.function.Supplier<U> supplier) {
        assertNotInWorkerThread();
        try {
            return (U) java.util.concurrent.CompletableFuture.supplyAsync(supplier, this.mExecutor).get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void awaitResult(java.lang.Runnable runnable) {
        assertNotInWorkerThread();
        try {
            java.util.concurrent.CompletableFuture.runAsync(runnable, this.mExecutor).get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertInWorkerThread() {
        com.android.internal.util.Preconditions.checkState(getHandler().getLooper().isCurrentThread());
    }

    private void assertNotInWorkerThread() {
        com.android.internal.util.Preconditions.checkState(!getHandler().getLooper().isCurrentThread());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerUserCallbacks(android.os.UserHandle userHandle) {
        android.content.Context contextAsUser = getContextAsUser(userHandle);
        if (contextAsUser == null) {
            android.util.Slog.e(TAG, "Unable to register user callbacks for user " + userHandle);
            return;
        }
        contextAsUser.getPackageManager().getPackageInstaller().registerSessionCallback(new com.android.server.rollback.RollbackManagerServiceImpl.SessionCallback(), getHandler());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        contextAsUser.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.rollback.RollbackManagerServiceImpl.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
                java.lang.String action = intent.getAction();
                if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
                    java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (com.android.server.rollback.RollbackManagerServiceImpl.LOCAL_LOGV) {
                        android.util.Slog.v(com.android.server.rollback.RollbackManagerServiceImpl.TAG, "broadcast=ACTION_PACKAGE_REPLACED pkg=" + schemeSpecificPart);
                    }
                    com.android.server.rollback.RollbackManagerServiceImpl.this.onPackageReplaced(schemeSpecificPart);
                }
                if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                    java.lang.String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                    android.util.Slog.i(com.android.server.rollback.RollbackManagerServiceImpl.TAG, "broadcast=ACTION_PACKAGE_FULLY_REMOVED pkg=" + schemeSpecificPart2);
                    com.android.server.rollback.RollbackManagerServiceImpl.this.onPackageFullyRemoved(schemeSpecificPart2);
                }
            }
        }, intentFilter, null, getHandler());
    }

    public android.content.pm.ParceledListSlice getAvailableRollbacks() {
        assertNotInWorkerThread();
        enforceManageRollbacks("getAvailableRollbacks");
        return (android.content.pm.ParceledListSlice) awaitResult(new java.util.function.Supplier() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda13
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.pm.ParceledListSlice lambda$getAvailableRollbacks$1;
                lambda$getAvailableRollbacks$1 = com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$getAvailableRollbacks$1();
                return lambda$getAvailableRollbacks$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ParceledListSlice lambda$getAvailableRollbacks$1() {
        assertInWorkerThread();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mRollbacks.size(); i++) {
            com.android.server.rollback.Rollback rollback = this.mRollbacks.get(i);
            if (rollback.isAvailable()) {
                arrayList.add(rollback.info);
            }
        }
        return new android.content.pm.ParceledListSlice(arrayList);
    }

    public android.content.pm.ParceledListSlice<android.content.rollback.RollbackInfo> getRecentlyCommittedRollbacks() {
        assertNotInWorkerThread();
        enforceManageRollbacks("getRecentlyCommittedRollbacks");
        return (android.content.pm.ParceledListSlice) awaitResult(new java.util.function.Supplier() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.pm.ParceledListSlice lambda$getRecentlyCommittedRollbacks$2;
                lambda$getRecentlyCommittedRollbacks$2 = com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$getRecentlyCommittedRollbacks$2();
                return lambda$getRecentlyCommittedRollbacks$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ParceledListSlice lambda$getRecentlyCommittedRollbacks$2() {
        assertInWorkerThread();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mRollbacks.size(); i++) {
            com.android.server.rollback.Rollback rollback = this.mRollbacks.get(i);
            if (rollback.isCommitted()) {
                arrayList.add(rollback.info);
            }
        }
        return new android.content.pm.ParceledListSlice(arrayList);
    }

    public void commitRollback(final int i, final android.content.pm.ParceledListSlice parceledListSlice, final java.lang.String str, final android.content.IntentSender intentSender) {
        assertNotInWorkerThread();
        enforceManageRollbacks("commitRollback");
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(android.os.Binder.getCallingUid(), str);
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$commitRollback$3(i, parceledListSlice, str, intentSender);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitRollback$3(int i, android.content.pm.ParceledListSlice parceledListSlice, java.lang.String str, android.content.IntentSender intentSender) {
        commitRollbackInternal(i, parceledListSlice.getList(), str, intentSender);
    }

    private void registerTimeChangeReceiver() {
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.rollback.RollbackManagerServiceImpl.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
                long j = com.android.server.rollback.RollbackManagerServiceImpl.this.mRelativeBootTime;
                com.android.server.rollback.RollbackManagerServiceImpl.this.mRelativeBootTime = com.android.server.rollback.RollbackManagerServiceImpl.calculateRelativeBootTime();
                long j2 = com.android.server.rollback.RollbackManagerServiceImpl.this.mRelativeBootTime - j;
                for (com.android.server.rollback.Rollback rollback : com.android.server.rollback.RollbackManagerServiceImpl.this.mRollbacks) {
                    rollback.setTimestamp(rollback.getTimestamp().plusMillis(j2));
                }
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, getHandler());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long calculateRelativeBootTime() {
        return java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime();
    }

    private void commitRollbackInternal(int i, java.util.List<android.content.pm.VersionedPackage> list, java.lang.String str, android.content.IntentSender intentSender) {
        assertInWorkerThread();
        android.util.Slog.i(TAG, "commitRollback id=" + i + " caller=" + str);
        com.android.server.rollback.Rollback rollbackForId = getRollbackForId(i);
        if (rollbackForId == null) {
            sendFailure(this.mContext, intentSender, 2, "Rollback unavailable");
        } else {
            rollbackForId.commit(this.mContext, list, str, intentSender);
        }
    }

    public void reloadPersistedData() {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS", "reloadPersistedData");
        awaitResult(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$reloadPersistedData$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reloadPersistedData$4() {
        assertInWorkerThread();
        this.mRollbacks.clear();
        this.mRollbacks.addAll(this.mRollbackStore.loadRollbacks());
    }

    private void expireRollbackForPackageInternal(java.lang.String str, java.lang.String str2) {
        assertInWorkerThread();
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        while (it.hasNext()) {
            com.android.server.rollback.Rollback next = it.next();
            if (next.includesPackage(str)) {
                it.remove();
                deleteRollback(next, str2);
            }
        }
    }

    public void expireRollbackForPackage(final java.lang.String str) {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS", "expireRollbackForPackage");
        awaitResult(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$expireRollbackForPackage$5(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$expireRollbackForPackage$5(java.lang.String str) {
        expireRollbackForPackageInternal(str, "Expired by API");
    }

    public void blockRollbackManager(final long j) {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS", "blockRollbackManager");
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$blockRollbackManager$6(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$blockRollbackManager$6(long j) {
        assertInWorkerThread();
        this.mSleepDuration.addLast(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueSleepIfNeeded() {
        assertInWorkerThread();
        if (this.mSleepDuration.size() == 0) {
            return;
        }
        final long removeFirst = this.mSleepDuration.removeFirst();
        if (removeFirst <= 0) {
            return;
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$queueSleepIfNeeded$7(removeFirst);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queueSleepIfNeeded$7(long j) {
        assertInWorkerThread();
        try {
            java.lang.Thread.sleep(j);
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.IllegalStateException("RollbackManagerHandlerThread interrupted");
        }
    }

    void onUnlockUser(final int i) {
        assertNotInWorkerThread();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "onUnlockUser id=" + i);
        }
        awaitResult(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$onUnlockUser$8(i);
            }
        });
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$onUnlockUser$9(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnlockUser$8(int i) {
        assertInWorkerThread();
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mRollbacks);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((com.android.server.rollback.Rollback) arrayList.get(i2)).commitPendingBackupAndRestoreForUser(i, this.mAppDataRollbackHelper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: destroyCeSnapshotsForExpiredRollbacks, reason: merged with bridge method [inline-methods] */
    public void lambda$onUnlockUser$9(int i) {
        int size = this.mRollbacks.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = this.mRollbacks.get(i2).info.getRollbackId();
        }
        com.android.server.pm.ApexManager.getInstance().destroyCeSnapshotsNotSpecified(i, iArr);
        try {
            this.mInstaller.destroyCeSnapshotsNotSpecified(i, iArr);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Failed to delete snapshots for user: " + i, e);
        }
    }

    private void updateRollbackLifetimeDurationInMillis() {
        assertInWorkerThread();
        this.mRollbackLifetimeDurationInMillis = android.provider.DeviceConfig.getLong("rollback_boot", "rollback_lifetime_in_millis", DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS);
        if (this.mRollbackLifetimeDurationInMillis < 0) {
            this.mRollbackLifetimeDurationInMillis = DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS;
        }
        android.util.Slog.d(TAG, "mRollbackLifetimeDurationInMillis=" + this.mRollbackLifetimeDurationInMillis);
        runExpiration();
    }

    void onBootCompleted() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("rollback_boot", this.mExecutor, new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda14
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$onBootCompleted$10(properties);
            }
        });
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$onBootCompleted$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$10(android.provider.DeviceConfig.Properties properties) {
        updateRollbackLifetimeDurationInMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$11() {
        assertInWorkerThread();
        updateRollbackLifetimeDurationInMillis();
        runExpiration();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        while (it.hasNext()) {
            com.android.server.rollback.Rollback next = it.next();
            if (next.isStaged()) {
                android.content.pm.PackageInstaller.SessionInfo sessionInfo = this.mContext.getPackageManager().getPackageInstaller().getSessionInfo(next.getOriginalSessionId());
                if (sessionInfo == null || sessionInfo.isStagedSessionFailed()) {
                    if (next.isEnabling()) {
                        it.remove();
                        deleteRollback(next, "Session " + next.getOriginalSessionId() + " not existed or failed");
                    }
                } else {
                    if (sessionInfo.isStagedSessionApplied()) {
                        if (next.isEnabling()) {
                            arrayList.add(next);
                        } else if (next.isRestoreUserDataInProgress()) {
                            arrayList2.add(next);
                        }
                    }
                    hashSet.addAll(next.getApexPackageNames());
                }
            }
        }
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            makeRollbackAvailable((com.android.server.rollback.Rollback) it2.next());
        }
        java.util.Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            ((com.android.server.rollback.Rollback) it3.next()).setRestoreUserDataInProgress(false);
        }
        java.util.Iterator it4 = hashSet.iterator();
        while (it4.hasNext()) {
            onPackageReplaced((java.lang.String) it4.next());
        }
        this.mPackageHealthObserver.onBootCompletedAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageReplaced(java.lang.String str) {
        assertInWorkerThread();
        long installedPackageVersion = getInstalledPackageVersion(str);
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        while (it.hasNext()) {
            com.android.server.rollback.Rollback next = it.next();
            if (next.isAvailable() && next.includesPackageWithDifferentVersion(str, installedPackageVersion)) {
                it.remove();
                deleteRollback(next, "Package " + str + " replaced");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageFullyRemoved(java.lang.String str) {
        assertInWorkerThread();
        expireRollbackForPackageInternal(str, "Package " + str + " removed");
    }

    static void sendFailure(android.content.Context context, android.content.IntentSender intentSender, int i, java.lang.String str) {
        android.util.Slog.e(TAG, str);
        try {
            android.content.Intent intent = new android.content.Intent();
            intent.putExtra("android.content.rollback.extra.STATUS", i);
            intent.putExtra("android.content.rollback.extra.STATUS_MESSAGE", str);
            intentSender.sendIntent(context, 0, intent, null, null);
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runExpiration() {
        if (android.content.pm.Flags.rollbackLifetime()) {
            runExpirationCustomRollbackLifetime();
        } else {
            runExpirationDefaultRollbackLifetime();
        }
    }

    private void runExpirationDefaultRollbackLifetime() {
        getHandler().removeCallbacks(this.mRunExpiration);
        assertInWorkerThread();
        java.time.Instant now = java.time.Instant.now();
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        java.time.Instant instant = null;
        while (it.hasNext()) {
            com.android.server.rollback.Rollback next = it.next();
            if (next.isAvailable() || next.isCommitted()) {
                java.time.Instant timestamp = next.getTimestamp();
                if (!now.isBefore(timestamp.plusMillis(this.mRollbackLifetimeDurationInMillis))) {
                    android.util.Slog.i(TAG, "runExpiration id=" + next.info.getRollbackId());
                    it.remove();
                    deleteRollback(next, "Expired by timeout");
                } else if (instant == null || instant.isAfter(timestamp)) {
                    instant = timestamp;
                }
            }
        }
        if (instant != null) {
            getHandler().postDelayed(this.mRunExpiration, now.until(instant.plusMillis(this.mRollbackLifetimeDurationInMillis), java.time.temporal.ChronoUnit.MILLIS));
        }
    }

    private void runExpirationCustomRollbackLifetime() {
        getHandler().removeCallbacks(this.mRunExpiration);
        assertInWorkerThread();
        java.time.Instant now = java.time.Instant.now();
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        long j = 0;
        while (it.hasNext()) {
            com.android.server.rollback.Rollback next = it.next();
            if (next.isAvailable() || next.isCommitted()) {
                long rollbackLifetimeMillis = next.getRollbackLifetimeMillis();
                if (rollbackLifetimeMillis <= 0) {
                    rollbackLifetimeMillis = this.mRollbackLifetimeDurationInMillis;
                }
                java.time.Instant plusMillis = next.getTimestamp().plusMillis(rollbackLifetimeMillis);
                if (!now.isBefore(plusMillis)) {
                    android.util.Slog.i(TAG, "runExpiration id=" + next.info.getRollbackId());
                    it.remove();
                    deleteRollback(next, "Expired by timeout");
                } else {
                    long until = now.until(plusMillis, java.time.temporal.ChronoUnit.MILLIS);
                    if (j == 0 || until < j) {
                        j = until;
                    }
                }
            }
        }
        if (j != 0) {
            getHandler().postDelayed(this.mRunExpiration, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    private android.content.Context getContextAsUser(android.os.UserHandle userHandle) {
        try {
            return this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean enableRollback(int i) {
        assertInWorkerThread();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "enableRollback sessionId=" + i);
        }
        android.content.pm.PackageInstaller packageInstaller = this.mContext.getPackageManager().getPackageInstaller();
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = packageInstaller.getSessionInfo(i);
        if (sessionInfo == null) {
            android.util.Slog.e(TAG, "Unable to find session for enabled rollback.");
            return false;
        }
        android.content.pm.PackageInstaller.SessionInfo sessionInfo2 = sessionInfo.hasParentSessionId() ? packageInstaller.getSessionInfo(sessionInfo.getParentSessionId()) : sessionInfo;
        if (sessionInfo2 == null) {
            android.util.Slog.e(TAG, "Unable to find parent session for enabled rollback.");
            return false;
        }
        com.android.server.rollback.Rollback rollbackForSession = getRollbackForSession(sessionInfo.getSessionId());
        if (rollbackForSession == null) {
            rollbackForSession = createNewRollback(sessionInfo2);
        }
        if (!enableRollbackForPackageSession(rollbackForSession, sessionInfo)) {
            return false;
        }
        if (rollbackForSession.allPackagesEnabled()) {
            return completeEnableRollback(rollbackForSession);
        }
        return true;
    }

    private int computeRollbackDataPolicy(int i, int i2) {
        assertInWorkerThread();
        if (i2 != 0) {
            return i2;
        }
        return i;
    }

    private boolean enableRollbackForPackageSession(com.android.server.rollback.Rollback rollback, android.content.pm.PackageInstaller.SessionInfo sessionInfo) {
        assertInWorkerThread();
        int i = sessionInfo.installFlags;
        if ((262144 & i) == 0) {
            android.util.Slog.e(TAG, "Rollback is not enabled.");
            return false;
        }
        if ((i & 2048) != 0) {
            android.util.Slog.e(TAG, "Rollbacks not supported for instant app install");
            return false;
        }
        if (sessionInfo.resolvedBaseCodePath == null) {
            android.util.Slog.e(TAG, "Session code path has not been resolved.");
            return false;
        }
        android.content.pm.parsing.result.ParseResult parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), new java.io.File(sessionInfo.resolvedBaseCodePath), 0);
        if (parseApkLite.isError()) {
            android.util.Slog.e(TAG, "Unable to parse new package: " + parseApkLite.getErrorMessage(), parseApkLite.getException());
            return false;
        }
        android.content.pm.parsing.ApkLite apkLite = (android.content.pm.parsing.ApkLite) parseApkLite.getResult();
        java.lang.String packageName = apkLite.getPackageName();
        int computeRollbackDataPolicy = computeRollbackDataPolicy(sessionInfo.rollbackDataPolicy, apkLite.getRollbackDataPolicy());
        if (!sessionInfo.isStaged() && (i & 131072) != 0 && computeRollbackDataPolicy != 2) {
            android.util.Slog.e(TAG, "Only RETAIN is supported for rebootless APEX: " + packageName);
            return false;
        }
        android.util.Slog.i(TAG, "Enabling rollback for install of " + packageName + ", session:" + sessionInfo.sessionId + ", rollbackDataPolicy=" + computeRollbackDataPolicy);
        java.lang.String installerPackageName = sessionInfo.getInstallerPackageName();
        if (!enableRollbackAllowed(installerPackageName, packageName)) {
            android.util.Slog.e(TAG, "Installer " + installerPackageName + " is not allowed to enable rollback on " + packageName);
            return false;
        }
        boolean z = (i & 131072) != 0;
        try {
            android.content.pm.PackageInfo packageInfo = getPackageInfo(packageName);
            if (z) {
                for (java.lang.String str : ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getApksInApex(packageName)) {
                    try {
                        if (!rollback.enableForPackageInApex(str, getPackageInfo(str).getLongVersionCode(), computeRollbackDataPolicy)) {
                            return false;
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.e(TAG, str + " is not installed");
                        return false;
                    }
                }
            }
            android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            return rollback.enableForPackage(packageName, apkLite.getVersionCode(), packageInfo.getLongVersionCode(), z, applicationInfo.sourceDir, applicationInfo.splitSourceDirs, computeRollbackDataPolicy, sessionInfo.rollbackImpactLevel);
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Slog.e(TAG, packageName + " is not installed");
            return false;
        }
    }

    @Override // com.android.server.rollback.RollbackManagerInternal
    public void snapshotAndRestoreUserData(java.lang.String str, java.util.List<android.os.UserHandle> list, int i, long j, java.lang.String str2, int i2) {
        assertNotInWorkerThread();
        snapshotAndRestoreUserData(str, android.os.UserHandle.fromUserHandles(list), i, j, str2, i2);
    }

    public void snapshotAndRestoreUserData(final java.lang.String str, final int[] iArr, final int i, long j, final java.lang.String str2, final int i2) {
        assertNotInWorkerThread();
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("snapshotAndRestoreUserData may only be called by the system.");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$snapshotAndRestoreUserData$12(str, iArr, i, str2, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$snapshotAndRestoreUserData$12(java.lang.String str, int[] iArr, int i, java.lang.String str2, int i2) {
        assertInWorkerThread();
        snapshotUserDataInternal(str, iArr);
        restoreUserDataInternal(str, iArr, i, str2);
        if (i2 > 0) {
            ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).finishPackageInstall(i2, false);
        }
    }

    private void snapshotUserDataInternal(java.lang.String str, int[] iArr) {
        assertInWorkerThread();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "snapshotUserData pkg=" + str + " users=" + java.util.Arrays.toString(iArr));
        }
        for (int i = 0; i < this.mRollbacks.size(); i++) {
            this.mRollbacks.get(i).snapshotUserData(str, iArr, this.mAppDataRollbackHelper);
        }
    }

    private void restoreUserDataInternal(java.lang.String str, int[] iArr, int i, java.lang.String str2) {
        assertInWorkerThread();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "restoreUserData pkg=" + str + " users=" + java.util.Arrays.toString(iArr));
        }
        for (int i2 = 0; i2 < this.mRollbacks.size() && !this.mRollbacks.get(i2).restoreUserDataForPackageIfInProgress(str, iArr, i, str2, this.mAppDataRollbackHelper); i2++) {
        }
    }

    @Override // com.android.server.rollback.RollbackManagerInternal
    public int notifyStagedSession(final int i) {
        assertNotInWorkerThread();
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("notifyStagedSession may only be called by the system.");
        }
        return ((java.lang.Integer) awaitResult(new java.util.function.Supplier() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$notifyStagedSession$13;
                lambda$notifyStagedSession$13 = com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$notifyStagedSession$13(i);
                return lambda$notifyStagedSession$13;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$notifyStagedSession$13(int i) {
        assertInWorkerThread();
        com.android.server.rollback.Rollback rollbackForSession = getRollbackForSession(i);
        return java.lang.Integer.valueOf(rollbackForSession != null ? rollbackForSession.info.getRollbackId() : -1);
    }

    private boolean enableRollbackAllowed(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return false;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        return (isRollbackAllowed(str2) && (packageManager.checkPermission("android.permission.MANAGE_ROLLBACKS", str) == 0)) || (packageManager.checkPermission("android.permission.TEST_MANAGE_ROLLBACKS", str) == 0);
    }

    private boolean isRollbackAllowed(java.lang.String str) {
        return com.android.server.SystemConfig.getInstance().getRollbackWhitelistedPackages().contains(str) || isModule(str);
    }

    private boolean isModule(java.lang.String str) {
        try {
            return this.mContext.getPackageManager().getModuleInfo(str, 0) != null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private long getInstalledPackageVersion(java.lang.String str) {
        try {
            return getPackageInfo(str).getLongVersionCode();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return -1L;
        }
    }

    private android.content.pm.PackageInfo getPackageInfo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(str, 4194304);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return packageManager.getPackageInfo(str, 1073741824);
        }
    }

    private class SessionCallback extends android.content.pm.PackageInstaller.SessionCallback {
        private SessionCallback() {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onCreated(int i) {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onBadgingChanged(int i) {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onActiveChanged(int i, boolean z) {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onProgressChanged(int i, float f) {
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public void onFinished(int i, boolean z) {
            com.android.server.rollback.RollbackManagerServiceImpl.this.assertInWorkerThread();
            if (com.android.server.rollback.RollbackManagerServiceImpl.LOCAL_LOGV) {
                android.util.Slog.v(com.android.server.rollback.RollbackManagerServiceImpl.TAG, "SessionCallback.onFinished id=" + i + " success=" + z);
            }
            com.android.server.rollback.Rollback rollbackForSession = com.android.server.rollback.RollbackManagerServiceImpl.this.getRollbackForSession(i);
            if (rollbackForSession == null || !rollbackForSession.isEnabling() || i != rollbackForSession.getOriginalSessionId()) {
                return;
            }
            if (z) {
                if (!rollbackForSession.isStaged() && com.android.server.rollback.RollbackManagerServiceImpl.this.completeEnableRollback(rollbackForSession)) {
                    com.android.server.rollback.RollbackManagerServiceImpl.this.makeRollbackAvailable(rollbackForSession);
                    return;
                }
                return;
            }
            android.util.Slog.w(com.android.server.rollback.RollbackManagerServiceImpl.TAG, "Delete rollback id=" + rollbackForSession.info.getRollbackId() + " for failed session id=" + i);
            com.android.server.rollback.RollbackManagerServiceImpl.this.mRollbacks.remove(rollbackForSession);
            com.android.server.rollback.RollbackManagerServiceImpl.this.deleteRollback(rollbackForSession, "Session " + i + " failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean completeEnableRollback(com.android.server.rollback.Rollback rollback) {
        assertInWorkerThread();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "completeEnableRollback id=" + rollback.info.getRollbackId());
        }
        if (!rollback.allPackagesEnabled()) {
            android.util.Slog.e(TAG, "Failed to enable rollback for all packages in session.");
            this.mRollbacks.remove(rollback);
            deleteRollback(rollback, "Failed to enable rollback for all packages in session");
            return false;
        }
        rollback.saveRollback();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"rollback.getLock"})
    public void makeRollbackAvailable(com.android.server.rollback.Rollback rollback) {
        assertInWorkerThread();
        android.util.Slog.i(TAG, "makeRollbackAvailable id=" + rollback.info.getRollbackId());
        rollback.makeAvailable();
        this.mPackageHealthObserver.notifyRollbackAvailable(rollback.info);
        if (android.content.pm.Flags.recoverabilityDetection()) {
            if (rollback.info.getRollbackImpactLevel() == 0) {
                this.mPackageHealthObserver.startObservingHealth(rollback.getPackageNames(), this.mRollbackLifetimeDurationInMillis);
            }
        } else {
            this.mPackageHealthObserver.startObservingHealth(rollback.getPackageNames(), this.mRollbackLifetimeDurationInMillis);
        }
        runExpiration();
    }

    private com.android.server.rollback.Rollback getRollbackForId(int i) {
        assertInWorkerThread();
        for (int i2 = 0; i2 < this.mRollbacks.size(); i2++) {
            com.android.server.rollback.Rollback rollback = this.mRollbacks.get(i2);
            if (rollback.info.getRollbackId() == i) {
                return rollback;
            }
        }
        return null;
    }

    private int allocateRollbackId() {
        assertInWorkerThread();
        int i = 0;
        while (true) {
            int nextInt = this.mRandom.nextInt(2147483646) + 1;
            if (!this.mAllocatedRollbackIds.get(nextInt, false)) {
                this.mAllocatedRollbackIds.put(nextInt, true);
                return nextInt;
            }
            int i2 = i + 1;
            if (i >= 32) {
                throw new java.lang.IllegalStateException("Failed to allocate rollback ID");
            }
            i = i2;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        assertNotInWorkerThread();
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            final com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            awaitResult(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.rollback.RollbackManagerServiceImpl.this.lambda$dump$14(indentingPrintWriter);
                }
            });
            com.android.server.PackageWatchdog.getInstance(this.mContext).dump(indentingPrintWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$14(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        assertInWorkerThread();
        java.util.Iterator<com.android.server.rollback.Rollback> it = this.mRollbacks.iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.println();
        java.util.List<com.android.server.rollback.Rollback> loadHistorialRollbacks = this.mRollbackStore.loadHistorialRollbacks();
        if (!loadHistorialRollbacks.isEmpty()) {
            indentingPrintWriter.println("Historical rollbacks:");
            indentingPrintWriter.increaseIndent();
            java.util.Iterator<com.android.server.rollback.Rollback> it2 = loadHistorialRollbacks.iterator();
            while (it2.hasNext()) {
                it2.next().dump(indentingPrintWriter);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
    }

    private void enforceManageRollbacks(@android.annotation.NonNull java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS") != 0) {
            throw new java.lang.SecurityException(str + " requires android.permission.MANAGE_ROLLBACKS or android.permission.TEST_MANAGE_ROLLBACKS");
        }
    }

    private com.android.server.rollback.Rollback createNewRollback(android.content.pm.PackageInstaller.SessionInfo sessionInfo) {
        int identifier;
        int[] iArr;
        com.android.server.rollback.Rollback createNonStagedRollback;
        assertInWorkerThread();
        int allocateRollbackId = allocateRollbackId();
        if (sessionInfo.getUser().equals(android.os.UserHandle.ALL)) {
            identifier = android.os.UserHandle.SYSTEM.getIdentifier();
        } else {
            identifier = sessionInfo.getUser().getIdentifier();
        }
        java.lang.String installerPackageName = sessionInfo.getInstallerPackageName();
        int sessionId = sessionInfo.getSessionId();
        if (LOCAL_LOGV) {
            android.util.Slog.v(TAG, "createNewRollback id=" + allocateRollbackId + " user=" + identifier + " installer=" + installerPackageName);
        }
        if (sessionInfo.isMultiPackage()) {
            iArr = sessionInfo.getChildSessionIds();
        } else {
            iArr = new int[]{sessionId};
        }
        if (sessionInfo.isStaged()) {
            createNonStagedRollback = this.mRollbackStore.createStagedRollback(allocateRollbackId, sessionId, identifier, installerPackageName, iArr, getExtensionVersions());
        } else {
            createNonStagedRollback = this.mRollbackStore.createNonStagedRollback(allocateRollbackId, sessionId, identifier, installerPackageName, iArr, getExtensionVersions());
        }
        if (android.content.pm.Flags.rollbackLifetime()) {
            createNonStagedRollback.setRollbackLifetimeMillis(sessionInfo.rollbackLifetimeMillis);
        }
        this.mRollbacks.add(createNonStagedRollback);
        return createNonStagedRollback;
    }

    private android.util.SparseIntArray getExtensionVersions() {
        java.util.Map<java.lang.Integer, java.lang.Integer> allExtensionVersions = android.os.ext.SdkExtensions.getAllExtensionVersions();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(allExtensionVersions.size());
        java.util.Iterator<java.lang.Integer> it = allExtensionVersions.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            sparseIntArray.put(intValue, allExtensionVersions.get(java.lang.Integer.valueOf(intValue)).intValue());
        }
        return sparseIntArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.rollback.Rollback getRollbackForSession(int i) {
        assertInWorkerThread();
        for (int i2 = 0; i2 < this.mRollbacks.size(); i2++) {
            com.android.server.rollback.Rollback rollback = this.mRollbacks.get(i2);
            if (rollback.getOriginalSessionId() == i || rollback.containsSessionId(i)) {
                return rollback;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteRollback(com.android.server.rollback.Rollback rollback, java.lang.String str) {
        assertInWorkerThread();
        rollback.delete(this.mAppDataRollbackHelper, str);
        this.mRollbackStore.saveRollbackToHistory(rollback);
    }
}
