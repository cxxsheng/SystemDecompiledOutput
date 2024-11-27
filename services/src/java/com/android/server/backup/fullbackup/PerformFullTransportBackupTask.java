package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class PerformFullTransportBackupTask extends com.android.server.backup.fullbackup.FullBackupTask implements com.android.server.backup.BackupRestoreTask {
    private static final java.lang.String TAG = "PFTBT";
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private com.android.server.backup.utils.BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    android.app.backup.IBackupObserver mBackupObserver;
    com.android.server.backup.fullbackup.PerformFullTransportBackupTask.SinglePackageBackupRunner mBackupRunner;
    private final int mBackupRunnerOpToken;
    private volatile boolean mCancelAll;
    private final java.lang.Object mCancelLock;
    private final int mCurrentOpToken;
    android.content.pm.PackageInfo mCurrentPackage;
    private volatile boolean mIsDoingBackup;
    com.android.server.backup.FullBackupJob mJob;
    java.util.concurrent.CountDownLatch mLatch;
    private final com.android.server.backup.internal.OnTaskFinishedListener mListener;
    com.android.server.backup.OperationStorage mOperationStorage;
    java.util.List<android.content.pm.PackageInfo> mPackages;
    private final com.android.server.backup.transport.TransportConnection mTransportConnection;
    boolean mUpdateSchedule;
    private com.android.server.backup.UserBackupManagerService mUserBackupManagerService;
    private final int mUserId;
    boolean mUserInitiated;

    public static com.android.server.backup.fullbackup.PerformFullTransportBackupTask newWithCurrentTransport(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, java.lang.String[] strArr, boolean z, com.android.server.backup.FullBackupJob fullBackupJob, java.util.concurrent.CountDownLatch countDownLatch, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, boolean z2, java.lang.String str, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        final com.android.server.backup.TransportManager transportManager = userBackupManagerService.getTransportManager();
        final com.android.server.backup.transport.TransportConnection currentTransportClient = transportManager.getCurrentTransportClient(str);
        if (currentTransportClient == null) {
            throw new java.lang.IllegalStateException("No TransportConnection available");
        }
        return new com.android.server.backup.fullbackup.PerformFullTransportBackupTask(userBackupManagerService, operationStorage, currentTransportClient, iFullBackupRestoreObserver, strArr, z, fullBackupJob, countDownLatch, iBackupObserver, iBackupManagerMonitor, new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.fullbackup.PerformFullTransportBackupTask$$ExternalSyntheticLambda0
            @Override // com.android.server.backup.internal.OnTaskFinishedListener
            public final void onFinished(java.lang.String str2) {
                com.android.server.backup.TransportManager.this.disposeOfTransportClient(currentTransportClient, str2);
            }
        }, z2, backupEligibilityRules);
    }

    public PerformFullTransportBackupTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, java.lang.String[] strArr, boolean z, com.android.server.backup.FullBackupJob fullBackupJob, java.util.concurrent.CountDownLatch countDownLatch, android.app.backup.IBackupObserver iBackupObserver, @android.annotation.Nullable android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, @android.annotation.Nullable com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, boolean z2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        super(iFullBackupRestoreObserver);
        this.mCancelLock = new java.lang.Object();
        this.mUserBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mTransportConnection = transportConnection;
        this.mUpdateSchedule = z;
        this.mLatch = countDownLatch;
        this.mJob = fullBackupJob;
        this.mPackages = new java.util.ArrayList(strArr.length);
        this.mBackupObserver = iBackupObserver;
        this.mListener = onTaskFinishedListener == null ? com.android.server.backup.internal.OnTaskFinishedListener.NOP : onTaskFinishedListener;
        this.mUserInitiated = z2;
        this.mCurrentOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mBackupRunnerOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mBackupManagerMonitorEventSender = new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor);
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mUserId = userBackupManagerService.getUserId();
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (userBackupManagerService.isBackupOperationInProgress()) {
            android.util.Slog.d(TAG, "Skipping full backup. A backup is already in progress.");
            this.mCancelAll = true;
            return;
        }
        for (java.lang.String str : strArr) {
            try {
                android.content.pm.PackageInfo packageInfoAsUser = userBackupManagerService.getPackageManager().getPackageInfoAsUser(str, 134217728, this.mUserId);
                this.mCurrentPackage = packageInfoAsUser;
                if (!this.mBackupEligibilityRules.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                    this.mBackupManagerMonitorEventSender.monitorEvent(9, this.mCurrentPackage, 3, null);
                    com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mBackupObserver, str, -2001);
                } else if (!this.mBackupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                    this.mBackupManagerMonitorEventSender.monitorEvent(10, this.mCurrentPackage, 3, null);
                    com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mBackupObserver, str, -2001);
                } else if (this.mBackupEligibilityRules.appIsStopped(packageInfoAsUser.applicationInfo)) {
                    this.mBackupManagerMonitorEventSender.monitorEvent(11, this.mCurrentPackage, 3, null);
                    com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mBackupObserver, str, -2001);
                } else {
                    this.mPackages.add(packageInfoAsUser);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.i(TAG, "Requested package " + str + " not found; ignoring");
                this.mBackupManagerMonitorEventSender.monitorEvent(12, this.mCurrentPackage, 3, null);
            }
        }
        this.mPackages = userBackupManagerService.filterUserFacingPackages(this.mPackages);
        java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet();
        java.util.Iterator<android.content.pm.PackageInfo> it = this.mPackages.iterator();
        while (it.hasNext()) {
            newHashSet.add(it.next().packageName);
        }
        android.util.Slog.d(TAG, "backupmanager pftbt token=" + java.lang.Integer.toHexString(this.mCurrentOpToken));
        this.mOperationStorage.registerOperationForPackages(this.mCurrentOpToken, 0, newHashSet, this, 2);
    }

    public void unregisterTask() {
        this.mOperationStorage.removeOperation(this.mCurrentOpToken);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        synchronized (this.mCancelLock) {
            if (!z) {
                try {
                    android.util.Slog.wtf(TAG, "Expected cancelAll to be true.");
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mCancelAll) {
                android.util.Slog.d(TAG, "Ignoring duplicate cancel call.");
                return;
            }
            this.mCancelAll = true;
            if (this.mIsDoingBackup) {
                this.mUserBackupManagerService.handleCancel(this.mBackupRunnerOpToken, z);
                try {
                    this.mTransportConnection.getConnectedTransport("PFTBT.handleCancel()").cancelFullBackup();
                } catch (android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException e) {
                    android.util.Slog.w(TAG, "Error calling cancelFullBackup() on transport: " + e);
                }
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:343:0x058f
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x07b7  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x07c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x078e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x072c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0757  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0765 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x072e  */
    /* JADX WARN: Type inference failed for: r11v19 */
    /* JADX WARN: Type inference failed for: r11v22 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v55 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.android.server.backup.transport.BackupTransportClient] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        /*
            Method dump skipped, instructions count: 2038
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.fullbackup.PerformFullTransportBackupTask.run():void");
    }

    void cleanUpPipes(android.os.ParcelFileDescriptor[] parcelFileDescriptorArr) {
        if (parcelFileDescriptorArr != null) {
            if (parcelFileDescriptorArr[0] != null) {
                android.os.ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptorArr[0];
                parcelFileDescriptorArr[0] = null;
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Unable to close pipe!");
                }
            }
            if (parcelFileDescriptorArr[1] != null) {
                android.os.ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptorArr[1];
                parcelFileDescriptorArr[1] = null;
                try {
                    parcelFileDescriptor2.close();
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(TAG, "Unable to close pipe!");
                }
            }
        }
    }

    class SinglePackageBackupPreflight implements com.android.server.backup.BackupRestoreTask, com.android.server.backup.fullbackup.FullBackupPreflight {
        private final int mCurrentOpToken;
        final long mQuota;
        final com.android.server.backup.transport.TransportConnection mTransportConnection;
        private final int mTransportFlags;
        final java.util.concurrent.atomic.AtomicLong mResult = new java.util.concurrent.atomic.AtomicLong(-1003);
        final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);

        SinglePackageBackupPreflight(com.android.server.backup.transport.TransportConnection transportConnection, long j, int i, int i2) {
            this.mTransportConnection = transportConnection;
            this.mQuota = j;
            this.mCurrentOpToken = i;
            this.mTransportFlags = i2;
        }

        @Override // com.android.server.backup.fullbackup.FullBackupPreflight
        public int preflightFullBackup(android.content.pm.PackageInfo packageInfo, final android.app.IBackupAgent iBackupAgent) {
            long fullBackupAgentTimeoutMillis = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis();
            try {
                com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService.prepareOperationTimeout(this.mCurrentOpToken, fullBackupAgentTimeoutMillis, this, 0);
                iBackupAgent.doMeasureFullBackup(this.mQuota, this.mCurrentOpToken, com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService.getBackupManagerBinder(), this.mTransportFlags);
                this.mLatch.await(fullBackupAgentTimeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
                final long j = this.mResult.get();
                if (j < 0) {
                    return (int) j;
                }
                int checkFullBackupSize = this.mTransportConnection.connectOrThrow("PFTBT$SPBP.preflightFullBackup()").checkFullBackupSize(j);
                if (checkFullBackupSize == -1005) {
                    com.android.server.backup.remote.RemoteCall.execute(new com.android.server.backup.remote.RemoteCallable() { // from class: com.android.server.backup.fullbackup.PerformFullTransportBackupTask$SinglePackageBackupPreflight$$ExternalSyntheticLambda0
                        @Override // com.android.server.backup.remote.RemoteCallable
                        public final void call(java.lang.Object obj) {
                            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.SinglePackageBackupPreflight.this.lambda$preflightFullBackup$0(iBackupAgent, j, (android.app.backup.IBackupCallback) obj);
                        }
                    }, com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis());
                    return checkFullBackupSize;
                }
                return checkFullBackupSize;
            } catch (java.lang.Exception e) {
                android.util.Slog.w(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG, "Exception preflighting " + packageInfo.packageName + ": " + e.getMessage());
                return -1003;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$preflightFullBackup$0(android.app.IBackupAgent iBackupAgent, long j, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException {
            iBackupAgent.doQuotaExceeded(j, this.mQuota, iBackupCallback);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void execute() {
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void operationComplete(long j) {
            this.mResult.set(j);
            this.mLatch.countDown();
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mOperationStorage.removeOperation(this.mCurrentOpToken);
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void handleCancel(boolean z) {
            this.mResult.set(-1003L);
            this.mLatch.countDown();
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mOperationStorage.removeOperation(this.mCurrentOpToken);
        }

        @Override // com.android.server.backup.fullbackup.FullBackupPreflight
        public long getExpectedSizeOrErrorCode() {
            try {
                this.mLatch.await(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                return this.mResult.get();
            } catch (java.lang.InterruptedException e) {
                return -1L;
            }
        }
    }

    class SinglePackageBackupRunner implements java.lang.Runnable, com.android.server.backup.BackupRestoreTask {
        private final int mCurrentOpToken;
        private com.android.server.backup.fullbackup.FullBackupEngine mEngine;
        private final int mEphemeralToken;
        private volatile boolean mIsCancelled;
        final android.os.ParcelFileDescriptor mOutput;
        final com.android.server.backup.fullbackup.PerformFullTransportBackupTask.SinglePackageBackupPreflight mPreflight;
        private final long mQuota;
        final android.content.pm.PackageInfo mTarget;
        private final int mTransportFlags;
        final java.util.concurrent.CountDownLatch mPreflightLatch = new java.util.concurrent.CountDownLatch(1);
        final java.util.concurrent.CountDownLatch mBackupLatch = new java.util.concurrent.CountDownLatch(1);
        private volatile int mPreflightResult = -1003;
        private volatile int mBackupResult = -1003;

        SinglePackageBackupRunner(android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.pm.PackageInfo packageInfo, com.android.server.backup.transport.TransportConnection transportConnection, long j, int i, int i2) throws java.io.IOException {
            this.mOutput = android.os.ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
            this.mTarget = packageInfo;
            this.mCurrentOpToken = i;
            this.mEphemeralToken = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService.generateRandomIntegerToken();
            this.mPreflight = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.new SinglePackageBackupPreflight(transportConnection, j, this.mEphemeralToken, i2);
            this.mQuota = j;
            this.mTransportFlags = i2;
            registerTask(packageInfo.packageName);
        }

        void registerTask(java.lang.String str) {
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mOperationStorage.registerOperationForPackages(this.mCurrentOpToken, 0, com.google.android.collect.Sets.newHashSet(new java.lang.String[]{str}), this, 0);
        }

        void unregisterTask() {
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mOperationStorage.removeOperation(this.mCurrentOpToken);
        }

        @Override // java.lang.Runnable
        public void run() {
            java.lang.String str;
            java.lang.Throwable th;
            this.mEngine = new com.android.server.backup.fullbackup.FullBackupEngine(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService, new java.io.FileOutputStream(this.mOutput.getFileDescriptor()), this.mPreflight, this.mTarget, false, this, this.mQuota, this.mCurrentOpToken, this.mTransportFlags, com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mBackupEligibilityRules, com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mBackupManagerMonitorEventSender);
            try {
                if (!this.mIsCancelled) {
                    this.mPreflightResult = this.mEngine.preflightCheck();
                }
                try {
                    try {
                        this.mPreflightLatch.countDown();
                        if (this.mPreflightResult == 0) {
                            try {
                                if (!this.mIsCancelled) {
                                    this.mBackupResult = this.mEngine.backupOnePackage();
                                }
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                str = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG;
                                unregisterTask();
                                this.mBackupLatch.countDown();
                                try {
                                    this.mOutput.close();
                                    throw th;
                                } catch (java.io.IOException e) {
                                    android.util.Slog.w(str, "Error closing transport pipe in runner");
                                    throw th;
                                }
                            }
                        }
                        unregisterTask();
                        this.mBackupLatch.countDown();
                        try {
                            this.mOutput.close();
                        } catch (java.io.IOException e2) {
                            str = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG;
                            android.util.Slog.w(str, "Error closing transport pipe in runner");
                        }
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        str = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG;
                        android.util.Slog.w(str, "Exception during full package backup of " + this.mTarget.packageName, e);
                        unregisterTask();
                        this.mBackupLatch.countDown();
                        try {
                            this.mOutput.close();
                        } catch (java.io.IOException e4) {
                            android.util.Slog.w(str, "Error closing transport pipe in runner");
                        }
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    str = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG;
                    th = th;
                    unregisterTask();
                    this.mBackupLatch.countDown();
                    this.mOutput.close();
                    throw th;
                }
            } catch (java.lang.Throwable th4) {
                str = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG;
                try {
                    try {
                        this.mPreflightLatch.countDown();
                        throw th4;
                    } catch (java.lang.Throwable th5) {
                        th = th5;
                        th = th;
                        unregisterTask();
                        this.mBackupLatch.countDown();
                        this.mOutput.close();
                        throw th;
                    }
                } catch (java.lang.Exception e5) {
                    e = e5;
                    android.util.Slog.w(str, "Exception during full package backup of " + this.mTarget.packageName, e);
                    unregisterTask();
                    this.mBackupLatch.countDown();
                    this.mOutput.close();
                }
            }
        }

        public void sendQuotaExceeded(long j, long j2) {
            this.mEngine.sendQuotaExceeded(j, j2);
        }

        long getPreflightResultBlocking() {
            try {
                this.mPreflightLatch.await(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                if (this.mIsCancelled) {
                    return -2003L;
                }
                if (this.mPreflightResult == 0) {
                    return this.mPreflight.getExpectedSizeOrErrorCode();
                }
                return this.mPreflightResult;
            } catch (java.lang.InterruptedException e) {
                return -1003L;
            }
        }

        int getBackupResultBlocking() {
            try {
                this.mBackupLatch.await(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                if (this.mIsCancelled) {
                    return -2003;
                }
                return this.mBackupResult;
            } catch (java.lang.InterruptedException e) {
                return -1003;
            }
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void execute() {
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void operationComplete(long j) {
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void handleCancel(boolean z) {
            android.util.Slog.w(com.android.server.backup.fullbackup.PerformFullTransportBackupTask.TAG, "Full backup cancel of " + this.mTarget.packageName);
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mBackupManagerMonitorEventSender.monitorEvent(4, com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mCurrentPackage, 2, null);
            this.mIsCancelled = true;
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService.handleCancel(this.mEphemeralToken, z);
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mUserBackupManagerService.tearDownAgentAndKill(this.mTarget.applicationInfo);
            this.mPreflightLatch.countDown();
            this.mBackupLatch.countDown();
            com.android.server.backup.fullbackup.PerformFullTransportBackupTask.this.mOperationStorage.removeOperation(this.mCurrentOpToken);
        }
    }
}
