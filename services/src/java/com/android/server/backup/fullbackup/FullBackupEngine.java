package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class FullBackupEngine {
    private com.android.server.backup.UserBackupManagerService backupManagerService;
    private android.app.IBackupAgent mAgent;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private final com.android.server.backup.utils.BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    private boolean mIncludeApks;
    private final int mOpToken;
    private java.io.OutputStream mOutput;
    private final android.content.pm.PackageInfo mPkg;
    private com.android.server.backup.fullbackup.FullBackupPreflight mPreflightHook;
    private final long mQuota;
    private com.android.server.backup.BackupRestoreTask mTimeoutMonitor;
    private final int mTransportFlags;

    class FullBackupRunner implements java.lang.Runnable {
        private final android.app.IBackupAgent mAgent;
        private final java.io.File mFilesDir;
        private final boolean mIncludeApks;
        private final android.content.pm.PackageInfo mPackage;
        private final android.content.pm.PackageManager mPackageManager;
        private final android.os.ParcelFileDescriptor mPipe;
        private final int mToken;
        private final int mUserId;

        FullBackupRunner(com.android.server.backup.UserBackupManagerService userBackupManagerService, android.content.pm.PackageInfo packageInfo, android.app.IBackupAgent iBackupAgent, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, boolean z) throws java.io.IOException {
            this.mUserId = userBackupManagerService.getUserId();
            this.mPackageManager = com.android.server.backup.fullbackup.FullBackupEngine.this.backupManagerService.getPackageManager();
            this.mPackage = packageInfo;
            this.mAgent = iBackupAgent;
            this.mPipe = android.os.ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
            this.mToken = i;
            this.mIncludeApks = z;
            this.mFilesDir = userBackupManagerService.getDataDir();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    try {
                        com.android.server.backup.fullbackup.AppMetadataBackupWriter appMetadataBackupWriter = new com.android.server.backup.fullbackup.AppMetadataBackupWriter(new android.app.backup.FullBackupDataOutput(this.mPipe, -1L, com.android.server.backup.fullbackup.FullBackupEngine.this.mTransportFlags), this.mPackageManager);
                        java.lang.String str = this.mPackage.packageName;
                        boolean equals = com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE.equals(str);
                        boolean shouldWriteApk = shouldWriteApk(this.mPackage.applicationInfo, this.mIncludeApks, equals);
                        if (!equals) {
                            java.io.File file = new java.io.File(this.mFilesDir, com.android.server.backup.UserBackupManagerService.BACKUP_MANIFEST_FILENAME);
                            appMetadataBackupWriter.backupManifest(this.mPackage, file, this.mFilesDir, shouldWriteApk);
                            file.delete();
                            byte[] widgetState = com.android.server.AppWidgetBackupBridge.getWidgetState(str, this.mUserId);
                            if (widgetState != null && widgetState.length > 0) {
                                java.io.File file2 = new java.io.File(this.mFilesDir, com.android.server.backup.UserBackupManagerService.BACKUP_METADATA_FILENAME);
                                appMetadataBackupWriter.backupWidget(this.mPackage, file2, this.mFilesDir, widgetState);
                                file2.delete();
                            }
                        }
                        if (shouldWriteApk) {
                            appMetadataBackupWriter.backupApk(this.mPackage);
                            appMetadataBackupWriter.backupObb(this.mUserId, this.mPackage);
                        }
                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Calling doFullBackup() on " + str);
                        com.android.server.backup.fullbackup.FullBackupEngine.this.backupManagerService.prepareOperationTimeout(this.mToken, equals ? com.android.server.backup.fullbackup.FullBackupEngine.this.mAgentTimeoutParameters.getSharedBackupAgentTimeoutMillis() : com.android.server.backup.fullbackup.FullBackupEngine.this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), com.android.server.backup.fullbackup.FullBackupEngine.this.mTimeoutMonitor, 0);
                        this.mAgent.doFullBackup(this.mPipe, com.android.server.backup.fullbackup.FullBackupEngine.this.mQuota, this.mToken, com.android.server.backup.fullbackup.FullBackupEngine.this.backupManagerService.getBackupManagerBinder(), com.android.server.backup.fullbackup.FullBackupEngine.this.mTransportFlags);
                        this.mPipe.close();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Remote agent vanished during full backup of " + this.mPackage.packageName, e);
                        this.mPipe.close();
                    } catch (java.io.IOException e2) {
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error running full backup for " + this.mPackage.packageName, e2);
                        this.mPipe.close();
                    }
                } finally {
                }
            } catch (java.io.IOException e3) {
            }
        }

        private boolean shouldWriteApk(android.content.pm.ApplicationInfo applicationInfo, boolean z, boolean z2) {
            return z && !z2 && (!((applicationInfo.flags & 1) != 0) || ((applicationInfo.flags & 128) != 0));
        }
    }

    public FullBackupEngine(com.android.server.backup.UserBackupManagerService userBackupManagerService, java.io.OutputStream outputStream, com.android.server.backup.fullbackup.FullBackupPreflight fullBackupPreflight, android.content.pm.PackageInfo packageInfo, boolean z, com.android.server.backup.BackupRestoreTask backupRestoreTask, long j, int i, int i2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules, com.android.server.backup.utils.BackupManagerMonitorEventSender backupManagerMonitorEventSender) {
        this.backupManagerService = userBackupManagerService;
        this.mOutput = outputStream;
        this.mPreflightHook = fullBackupPreflight;
        this.mPkg = packageInfo;
        this.mIncludeApks = z;
        this.mTimeoutMonitor = backupRestoreTask;
        this.mQuota = j;
        this.mOpToken = i;
        this.mTransportFlags = i2;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mBackupEligibilityRules = backupEligibilityRules;
        this.mBackupManagerMonitorEventSender = backupManagerMonitorEventSender;
    }

    public int preflightCheck() throws android.os.RemoteException {
        if (this.mPreflightHook == null) {
            return 0;
        }
        if (initializeAgent()) {
            int preflightFullBackup = this.mPreflightHook.preflightFullBackup(this.mPkg, this.mAgent);
            this.mAgent.clearBackupRestoreEventLogger();
            return preflightFullBackup;
        }
        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unable to bind to full agent for " + this.mPkg.packageName);
        return -1003;
    }

    public int backupOnePackage() throws android.os.RemoteException {
        java.lang.Throwable th;
        int i;
        int i2 = -1003;
        if (initializeAgent()) {
            android.os.ParcelFileDescriptor[] parcelFileDescriptorArr = null;
            try {
            } catch (java.io.IOException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Error bringing down backup stack");
                i2 = -1000;
            }
            try {
                try {
                    android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                    try {
                        com.android.server.backup.fullbackup.FullBackupEngine.FullBackupRunner fullBackupRunner = new com.android.server.backup.fullbackup.FullBackupEngine.FullBackupRunner(this.backupManagerService, this.mPkg, this.mAgent, createPipe[1], this.mOpToken, this.mIncludeApks);
                        createPipe[1].close();
                        createPipe[1] = null;
                        new java.lang.Thread(fullBackupRunner, "app-data-runner").start();
                        com.android.server.backup.utils.FullBackupUtils.routeSocketDataToOutput(createPipe[0], this.mOutput);
                        if (this.backupManagerService.waitUntilOperationComplete(this.mOpToken)) {
                            i = 0;
                        } else {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Full backup failed on package " + this.mPkg.packageName);
                            i = -1003;
                        }
                        this.mBackupManagerMonitorEventSender.monitorAgentLoggingResults(this.mPkg, this.mAgent);
                        this.mOutput.flush();
                        if (createPipe[0] != null) {
                            createPipe[0].close();
                        }
                        if (createPipe[1] != null) {
                            createPipe[1].close();
                        }
                        i2 = i;
                    } catch (java.io.IOException e2) {
                        e = e2;
                        parcelFileDescriptorArr = createPipe;
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error backing up " + this.mPkg.packageName + ": " + e.getMessage());
                        this.mOutput.flush();
                        if (parcelFileDescriptorArr != null) {
                            if (parcelFileDescriptorArr[0] != null) {
                                parcelFileDescriptorArr[0].close();
                            }
                            if (parcelFileDescriptorArr[1] != null) {
                                parcelFileDescriptorArr[1].close();
                            }
                        }
                        tearDown();
                        return i2;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        parcelFileDescriptorArr = createPipe;
                        try {
                            this.mOutput.flush();
                            if (parcelFileDescriptorArr == null) {
                                throw th;
                            }
                            if (parcelFileDescriptorArr[0] != null) {
                                parcelFileDescriptorArr[0].close();
                            }
                            if (parcelFileDescriptorArr[1] == null) {
                                throw th;
                            }
                            parcelFileDescriptorArr[1].close();
                            throw th;
                        } catch (java.io.IOException e3) {
                            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Error bringing down backup stack");
                            throw th;
                        }
                    }
                } catch (java.io.IOException e4) {
                    e = e4;
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        } else {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unable to bind to full agent for " + this.mPkg.packageName);
        }
        tearDown();
        return i2;
    }

    public void sendQuotaExceeded(final long j, final long j2) {
        if (initializeAgent()) {
            try {
                com.android.server.backup.remote.RemoteCall.execute(new com.android.server.backup.remote.RemoteCallable() { // from class: com.android.server.backup.fullbackup.FullBackupEngine$$ExternalSyntheticLambda0
                    @Override // com.android.server.backup.remote.RemoteCallable
                    public final void call(java.lang.Object obj) {
                        com.android.server.backup.fullbackup.FullBackupEngine.this.lambda$sendQuotaExceeded$0(j, j2, (android.app.backup.IBackupCallback) obj);
                    }
                }, this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis());
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Remote exception while telling agent about quota exceeded");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendQuotaExceeded$0(long j, long j2, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException {
        this.mAgent.doQuotaExceeded(j, j2, iBackupCallback);
    }

    private boolean initializeAgent() {
        if (this.mAgent == null) {
            this.mAgent = this.backupManagerService.bindToAgentSynchronous(this.mPkg.applicationInfo, 1, this.mBackupEligibilityRules.getBackupDestination());
        }
        return this.mAgent != null;
    }

    private void tearDown() {
        if (this.mPkg != null) {
            this.backupManagerService.tearDownAgentAndKill(this.mPkg.applicationInfo);
        }
    }
}
