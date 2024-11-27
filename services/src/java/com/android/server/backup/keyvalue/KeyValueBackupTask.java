package com.android.server.backup.keyvalue;

/* loaded from: classes.dex */
public class KeyValueBackupTask implements com.android.server.backup.BackupRestoreTask, java.lang.Runnable {
    private static final java.lang.String BLANK_STATE_FILE_NAME = "blank_state";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String NEW_STATE_FILE_SUFFIX = ".new";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String NO_DATA_END_SENTINEL = "@end@";
    private static final java.lang.String PM_PACKAGE = "@pm@";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String STAGING_FILE_SUFFIX = ".data";
    private static final java.lang.String SUCCESS_STATE_SUBDIR = "backing-up";
    private static final java.lang.String TAG = "KVBT";
    private static final java.util.concurrent.atomic.AtomicInteger THREAD_COUNT = new java.util.concurrent.atomic.AtomicInteger();
    private static final int THREAD_PRIORITY = 10;

    @android.annotation.Nullable
    private android.app.IBackupAgent mAgent;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;

    @android.annotation.Nullable
    private android.os.ParcelFileDescriptor mBackupData;

    @android.annotation.Nullable
    private java.io.File mBackupDataFile;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final java.io.File mBlankStateFile;
    private final android.os.ConditionVariable mCancelAcknowledged = new android.os.ConditionVariable(false);
    private volatile boolean mCancelled = false;
    private final int mCurrentOpToken;

    @android.annotation.Nullable
    private android.content.pm.PackageInfo mCurrentPackage;
    private final java.io.File mDataDirectory;

    @android.annotation.Nullable
    private com.android.server.backup.fullbackup.PerformFullTransportBackupTask mFullBackupTask;
    private boolean mHasDataToBackup;

    @android.annotation.Nullable
    private final com.android.server.backup.DataChangedJournal mJournal;

    @android.annotation.Nullable
    private android.os.ParcelFileDescriptor mNewState;

    @android.annotation.Nullable
    private java.io.File mNewStateFile;
    private boolean mNonIncremental;
    private final com.android.server.backup.OperationStorage mOperationStorage;
    private final java.util.List<java.lang.String> mOriginalQueue;
    private final android.content.pm.PackageManager mPackageManager;

    @android.annotation.Nullable
    private volatile com.android.server.backup.remote.RemoteCall mPendingCall;
    private final java.util.List<java.lang.String> mPendingFullBackups;
    private final java.util.List<java.lang.String> mQueue;
    private final java.lang.Object mQueueLock;
    private final com.android.server.backup.keyvalue.KeyValueBackupReporter mReporter;

    @android.annotation.Nullable
    private android.os.ParcelFileDescriptor mSavedState;

    @android.annotation.Nullable
    private java.io.File mSavedStateFile;
    private final java.io.File mStateDirectory;
    private final com.android.server.backup.internal.OnTaskFinishedListener mTaskFinishedListener;
    private final com.android.server.backup.transport.TransportConnection mTransportConnection;
    private final int mUserId;
    private final boolean mUserInitiated;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface StateTransaction {
        public static final int COMMIT_NEW = 0;
        public static final int DISCARD_ALL = 2;
        public static final int DISCARD_NEW = 1;
    }

    public static com.android.server.backup.keyvalue.KeyValueBackupTask start(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str, java.util.List<java.lang.String> list, @android.annotation.Nullable com.android.server.backup.DataChangedJournal dataChangedJournal, android.app.backup.IBackupObserver iBackupObserver, @android.annotation.Nullable android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, java.util.List<java.lang.String> list2, boolean z, boolean z2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        com.android.server.backup.keyvalue.KeyValueBackupTask keyValueBackupTask = new com.android.server.backup.keyvalue.KeyValueBackupTask(userBackupManagerService, operationStorage, transportConnection, str, list, dataChangedJournal, new com.android.server.backup.keyvalue.KeyValueBackupReporter(userBackupManagerService, iBackupObserver, new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor)), onTaskFinishedListener, list2, z, z2, backupEligibilityRules);
        java.lang.Thread thread = new java.lang.Thread(keyValueBackupTask, "key-value-backup-" + THREAD_COUNT.incrementAndGet());
        thread.start();
        com.android.server.backup.keyvalue.KeyValueBackupReporter.onNewThread(thread.getName());
        return keyValueBackupTask;
    }

    @com.android.internal.annotations.VisibleForTesting
    public KeyValueBackupTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str, java.util.List<java.lang.String> list, @android.annotation.Nullable com.android.server.backup.DataChangedJournal dataChangedJournal, com.android.server.backup.keyvalue.KeyValueBackupReporter keyValueBackupReporter, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, java.util.List<java.lang.String> list2, boolean z, boolean z2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mPackageManager = userBackupManagerService.getPackageManager();
        this.mTransportConnection = transportConnection;
        this.mOriginalQueue = list;
        this.mQueue = new java.util.ArrayList(list);
        this.mJournal = dataChangedJournal;
        this.mReporter = keyValueBackupReporter;
        this.mTaskFinishedListener = onTaskFinishedListener;
        this.mPendingFullBackups = list2;
        this.mUserInitiated = z;
        this.mNonIncremental = z2;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mStateDirectory = new java.io.File(userBackupManagerService.getBaseStateDir(), str);
        this.mDataDirectory = this.mBackupManagerService.getDataDir();
        this.mCurrentOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mQueueLock = this.mBackupManagerService.getQueueLock();
        this.mBlankStateFile = new java.io.File(this.mStateDirectory, BLANK_STATE_FILE_NAME);
        this.mUserId = userBackupManagerService.getUserId();
        this.mBackupEligibilityRules = backupEligibilityRules;
    }

    private void registerTask() {
        this.mOperationStorage.registerOperation(this.mCurrentOpToken, 0, this, 2);
    }

    private void unregisterTask() {
        this.mOperationStorage.removeOperation(this.mCurrentOpToken);
    }

    @Override // java.lang.Runnable
    public void run() {
        android.os.Process.setThreadPriority(10);
        int i = 0;
        this.mHasDataToBackup = false;
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            startTask();
            while (!this.mQueue.isEmpty() && !this.mCancelled) {
                java.lang.String remove = this.mQueue.remove(0);
                try {
                    if ("@pm@".equals(remove)) {
                        backupPm();
                    } else {
                        backupPackage(remove);
                    }
                    setSuccessState(remove, true);
                    hashSet.add(remove);
                } catch (com.android.server.backup.keyvalue.AgentException e) {
                    setSuccessState(remove, false);
                    if (e.isTransitory()) {
                        this.mBackupManagerService.dataChangedImpl(remove);
                    }
                }
            }
            informTransportOfUnchangedApps(hashSet);
        } catch (com.android.server.backup.keyvalue.TaskException e2) {
            if (e2.isStateCompromised()) {
                this.mBackupManagerService.resetBackupState(this.mStateDirectory);
            }
            revertTask();
            i = e2.getStatus();
        }
        finishTask(i);
    }

    private void informTransportOfUnchangedApps(java.util.Set<java.lang.String> set) {
        int i;
        java.lang.String[] succeedingPackages = getSucceedingPackages();
        if (succeedingPackages == null) {
            return;
        }
        if (!this.mUserInitiated) {
            i = 8;
        } else {
            i = 9;
        }
        try {
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("KVBT.informTransportOfEmptyBackups()");
            boolean z = false;
            for (java.lang.String str : succeedingPackages) {
                if (set.contains(str)) {
                    android.util.Log.v(TAG, "Skipping package which was backed up this time: " + str);
                } else {
                    try {
                        android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (!isEligibleForNoDataCall(packageInfo)) {
                            clearStatus(str);
                        } else {
                            sendNoDataChangedTo(connectOrThrow, packageInfo, i);
                            z = true;
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        clearStatus(str);
                    }
                }
            }
            if (z) {
                android.content.pm.PackageInfo packageInfo2 = new android.content.pm.PackageInfo();
                packageInfo2.packageName = NO_DATA_END_SENTINEL;
                sendNoDataChangedTo(connectOrThrow, packageInfo2, i);
            }
        } catch (android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException e2) {
            android.util.Log.e(TAG, "Could not inform transport of all unchanged apps", e2);
        }
    }

    private boolean isEligibleForNoDataCall(android.content.pm.PackageInfo packageInfo) {
        return this.mBackupEligibilityRules.appIsKeyValueOnly(packageInfo) && this.mBackupEligibilityRules.appIsRunningAndEligibleForBackupWithTransport(this.mTransportConnection, packageInfo.packageName);
    }

    private void sendNoDataChangedTo(com.android.server.backup.transport.BackupTransportClient backupTransportClient, android.content.pm.PackageInfo packageInfo, int i) throws android.os.RemoteException {
        try {
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(this.mBlankStateFile, android.hardware.audio.common.V2_0.AudioFormat.MP2);
            try {
                int performBackup = backupTransportClient.performBackup(packageInfo, open, i);
                if (performBackup == -1000 || performBackup == -1001) {
                    android.util.Log.w(TAG, "Aborting informing transport of unchanged apps, transport errored");
                } else {
                    backupTransportClient.finishBackup();
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(open);
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.e(TAG, "Unable to find blank state file, aborting unchanged apps signal.");
        }
    }

    private java.lang.String[] getSucceedingPackages() {
        java.io.File topLevelSuccessStateDirectory = getTopLevelSuccessStateDirectory(false);
        if (topLevelSuccessStateDirectory == null) {
            return null;
        }
        return topLevelSuccessStateDirectory.list();
    }

    private void setSuccessState(java.lang.String str, boolean z) {
        java.io.File successStateFileFor = getSuccessStateFileFor(str);
        if (successStateFileFor != null && successStateFileFor.exists() != z) {
            if (!z) {
                clearStatus(str, successStateFileFor);
                return;
            }
            try {
                if (!successStateFileFor.createNewFile()) {
                    android.util.Log.w(TAG, "Unable to permanently record success for " + str);
                }
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Unable to permanently record success for " + str, e);
            }
        }
    }

    private void clearStatus(java.lang.String str) {
        java.io.File successStateFileFor = getSuccessStateFileFor(str);
        if (successStateFileFor == null) {
            return;
        }
        clearStatus(str, successStateFileFor);
    }

    private void clearStatus(java.lang.String str, java.io.File file) {
        if (file.exists() && !file.delete()) {
            android.util.Log.w(TAG, "Unable to remove status file for " + str);
        }
    }

    private java.io.File getSuccessStateFileFor(java.lang.String str) {
        java.io.File topLevelSuccessStateDirectory = getTopLevelSuccessStateDirectory(true);
        if (topLevelSuccessStateDirectory == null) {
            return null;
        }
        return new java.io.File(topLevelSuccessStateDirectory, str);
    }

    private java.io.File getTopLevelSuccessStateDirectory(boolean z) {
        java.io.File file = new java.io.File(this.mStateDirectory, SUCCESS_STATE_SUBDIR);
        if (!file.exists() && z && !file.mkdirs()) {
            android.util.Log.e(TAG, "Unable to create backing-up state directory");
            return null;
        }
        return file;
    }

    private int sendDataToTransport(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        try {
            return sendDataToTransport();
        } catch (java.io.IOException e) {
            this.mReporter.onAgentDataError(packageInfo.packageName, e);
            throw com.android.server.backup.keyvalue.TaskException.causedBy(e);
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
    }

    private void startTask() throws com.android.server.backup.keyvalue.TaskException {
        if (this.mBackupManagerService.isBackupOperationInProgress()) {
            this.mReporter.onSkipBackup();
            throw com.android.server.backup.keyvalue.TaskException.create();
        }
        this.mFullBackupTask = createFullBackupTask(this.mPendingFullBackups);
        registerTask();
        if (this.mQueue.isEmpty() && this.mPendingFullBackups.isEmpty()) {
            this.mReporter.onEmptyQueueAtStart();
            return;
        }
        if (this.mQueue.remove("@pm@") || !this.mNonIncremental) {
            this.mQueue.add(0, "@pm@");
        } else {
            this.mReporter.onSkipPm();
        }
        this.mReporter.onQueueReady(this.mQueue);
        java.io.File file = new java.io.File(this.mStateDirectory, "@pm@");
        try {
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("KVBT.startTask()");
            java.lang.String name = connectOrThrow.name();
            if (name.contains("EncryptedLocalTransport")) {
                this.mNonIncremental = true;
            }
            this.mReporter.onTransportReady(name);
            if (file.length() <= 0) {
                this.mReporter.onInitializeTransport(name);
                this.mBackupManagerService.resetBackupState(this.mStateDirectory);
                int initializeDevice = connectOrThrow.initializeDevice();
                this.mReporter.onTransportInitialized(initializeDevice);
                if (initializeDevice != 0) {
                    throw com.android.server.backup.keyvalue.TaskException.stateCompromised();
                }
            }
        } catch (com.android.server.backup.keyvalue.TaskException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            this.mReporter.onInitializeTransportError(e2);
            throw com.android.server.backup.keyvalue.TaskException.stateCompromised();
        }
    }

    private com.android.server.backup.fullbackup.PerformFullTransportBackupTask createFullBackupTask(java.util.List<java.lang.String> list) {
        return new com.android.server.backup.fullbackup.PerformFullTransportBackupTask(this.mBackupManagerService, this.mOperationStorage, this.mTransportConnection, null, (java.lang.String[]) list.toArray(new java.lang.String[list.size()]), false, null, new java.util.concurrent.CountDownLatch(1), this.mReporter.getObserver(), this.mReporter.getMonitor(), this.mTaskFinishedListener, this.mUserInitiated, this.mBackupEligibilityRules);
    }

    private void backupPm() throws com.android.server.backup.keyvalue.TaskException {
        this.mReporter.onStartPackageBackup("@pm@");
        this.mCurrentPackage = new android.content.pm.PackageInfo();
        this.mCurrentPackage.packageName = "@pm@";
        try {
            try {
                extractPmAgentData(this.mCurrentPackage);
                cleanUpAgentForTransportStatus(sendDataToTransport(this.mCurrentPackage));
            } catch (com.android.server.backup.keyvalue.TaskException e) {
                throw com.android.server.backup.keyvalue.TaskException.stateCompromised(e);
            }
        } catch (com.android.server.backup.keyvalue.AgentException | com.android.server.backup.keyvalue.TaskException e2) {
            this.mReporter.onExtractPmAgentDataError(e2);
            cleanUpAgentForError(e2);
            if (e2 instanceof com.android.server.backup.keyvalue.TaskException) {
                throw ((com.android.server.backup.keyvalue.TaskException) e2);
            }
            throw com.android.server.backup.keyvalue.TaskException.stateCompromised(e2);
        }
    }

    private void backupPackage(java.lang.String str) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        this.mReporter.onStartPackageBackup(str);
        this.mCurrentPackage = getPackageForBackup(str);
        try {
            extractAgentData(this.mCurrentPackage);
            new com.android.server.backup.utils.BackupManagerMonitorEventSender(this.mReporter.getMonitor()).monitorAgentLoggingResults(this.mCurrentPackage, this.mAgent);
            cleanUpAgentForTransportStatus(sendDataToTransport(this.mCurrentPackage));
        } catch (com.android.server.backup.keyvalue.AgentException | com.android.server.backup.keyvalue.TaskException e) {
            cleanUpAgentForError(e);
            throw e;
        }
    }

    private android.content.pm.PackageInfo getPackageForBackup(java.lang.String str) throws com.android.server.backup.keyvalue.AgentException {
        try {
            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str, 134217728, this.mUserId);
            android.content.pm.ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
            if (!this.mBackupEligibilityRules.appIsEligibleForBackup(applicationInfo)) {
                this.mReporter.onPackageNotEligibleForBackup(str);
                throw com.android.server.backup.keyvalue.AgentException.permanent();
            }
            if (this.mBackupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                this.mReporter.onPackageEligibleForFullBackup(str);
                throw com.android.server.backup.keyvalue.AgentException.permanent();
            }
            if (this.mBackupEligibilityRules.appIsStopped(applicationInfo)) {
                this.mReporter.onPackageStopped(str);
                throw com.android.server.backup.keyvalue.AgentException.permanent();
            }
            return packageInfoAsUser;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            this.mReporter.onAgentUnknown(str);
            throw com.android.server.backup.keyvalue.AgentException.permanent(e);
        }
    }

    private android.app.IBackupAgent bindAgent(android.content.pm.PackageInfo packageInfo) throws com.android.server.backup.keyvalue.AgentException {
        java.lang.String str = packageInfo.packageName;
        try {
            android.app.IBackupAgent bindToAgentSynchronous = this.mBackupManagerService.bindToAgentSynchronous(packageInfo.applicationInfo, 0, this.mBackupEligibilityRules.getBackupDestination());
            if (bindToAgentSynchronous == null) {
                this.mReporter.onAgentError(str);
                throw com.android.server.backup.keyvalue.AgentException.transitory();
            }
            return bindToAgentSynchronous;
        } catch (java.lang.SecurityException e) {
            this.mReporter.onBindAgentError(str, e);
            throw com.android.server.backup.keyvalue.AgentException.transitory(e);
        }
    }

    private void finishTask(int i) {
        java.util.Iterator<java.lang.String> it = this.mQueue.iterator();
        while (it.hasNext()) {
            this.mBackupManagerService.dataChangedImpl(it.next());
        }
        if (this.mJournal != null && !this.mJournal.delete()) {
            this.mReporter.onJournalDeleteFailed(this.mJournal);
        }
        long currentToken = this.mBackupManagerService.getCurrentToken();
        java.lang.String str = null;
        if (this.mHasDataToBackup && i == 0 && currentToken == 0) {
            try {
                com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("KVBT.finishTask()");
                str = connectOrThrow.name();
                this.mBackupManagerService.setCurrentToken(connectOrThrow.getCurrentRestoreSet());
                this.mBackupManagerService.writeRestoreTokens();
            } catch (java.lang.Exception e) {
                this.mReporter.onSetCurrentTokenError(e);
            }
        }
        synchronized (this.mQueueLock) {
            this.mBackupManagerService.setBackupRunning(false);
            if (i == -1001) {
                this.mReporter.onTransportNotInitialized(str);
                try {
                    triggerTransportInitializationLocked();
                } catch (java.lang.Exception e2) {
                    this.mReporter.onPendingInitializeTransportError(e2);
                    i = -1000;
                }
            }
        }
        unregisterTask();
        this.mReporter.onTaskFinished();
        if (this.mCancelled) {
            this.mCancelAcknowledged.open();
        }
        if (!this.mCancelled && i == 0 && this.mFullBackupTask != null && !this.mPendingFullBackups.isEmpty()) {
            this.mReporter.onStartFullBackup(this.mPendingFullBackups);
            new java.lang.Thread(this.mFullBackupTask, "full-transport-requested").start();
            return;
        }
        if (this.mFullBackupTask != null) {
            this.mFullBackupTask.unregisterTask();
        }
        this.mTaskFinishedListener.onFinished("KVBT.finishTask()");
        this.mReporter.onBackupFinished(getBackupFinishedStatus(this.mCancelled, i));
        this.mBackupManagerService.getWakelock().release();
    }

    private int getBackupFinishedStatus(boolean z, int i) {
        if (z) {
            return -2003;
        }
        switch (i) {
            case -1005:
            case com.android.server.job.JobSchedulerShellCommand.CMD_ERR_CONSTRAINTS /* -1002 */:
            case 0:
                return 0;
            default:
                return -1000;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mQueueLock"})
    private void triggerTransportInitializationLocked() throws java.lang.Exception {
        this.mBackupManagerService.getPendingInits().add(this.mTransportConnection.connectOrThrow("KVBT.triggerTransportInitializationLocked").name());
        deletePmStateFile();
        this.mBackupManagerService.backupNow();
    }

    private void deletePmStateFile() {
        new java.io.File(this.mStateDirectory, "@pm@").delete();
    }

    private void extractPmAgentData(android.content.pm.PackageInfo packageInfo) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        com.android.internal.util.Preconditions.checkArgument(packageInfo.packageName.equals("@pm@"));
        this.mAgent = android.app.IBackupAgent.Stub.asInterface(this.mBackupManagerService.makeMetadataAgentWithEligibilityRules(this.mBackupEligibilityRules).onBind());
        extractAgentData(packageInfo, this.mAgent);
    }

    private void extractAgentData(android.content.pm.PackageInfo packageInfo) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        this.mBackupManagerService.setWorkSource(new android.os.WorkSource(packageInfo.applicationInfo.uid));
        try {
            this.mAgent = bindAgent(packageInfo);
            extractAgentData(packageInfo, this.mAgent);
        } finally {
            this.mBackupManagerService.setWorkSource(null);
        }
    }

    private void extractAgentData(android.content.pm.PackageInfo packageInfo, final android.app.IBackupAgent iBackupAgent) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        java.lang.String str = packageInfo.packageName;
        this.mReporter.onExtractAgentData(str);
        this.mSavedStateFile = new java.io.File(this.mStateDirectory, str);
        this.mBackupDataFile = new java.io.File(this.mDataDirectory, str + STAGING_FILE_SUFFIX);
        this.mNewStateFile = new java.io.File(this.mStateDirectory, str + NEW_STATE_FILE_SUFFIX);
        this.mReporter.onAgentFilesReady(this.mBackupDataFile);
        boolean z = false;
        try {
            this.mSavedState = android.os.ParcelFileDescriptor.open(this.mNonIncremental ? this.mBlankStateFile : this.mSavedStateFile, android.hardware.audio.common.V2_0.AudioFormat.MP2);
            this.mBackupData = android.os.ParcelFileDescriptor.open(this.mBackupDataFile, 1006632960);
            this.mNewState = android.os.ParcelFileDescriptor.open(this.mNewStateFile, 1006632960);
            if (this.mUserId == 0 && !android.os.SELinux.restorecon(this.mBackupDataFile)) {
                this.mReporter.onRestoreconFailed(this.mBackupDataFile);
            }
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("KVBT.extractAgentData()");
            final long backupQuota = connectOrThrow.getBackupQuota(str, false);
            final int transportFlags = connectOrThrow.getTransportFlags();
            z = true;
            checkAgentResult(packageInfo, remoteCall(new com.android.server.backup.remote.RemoteCallable() { // from class: com.android.server.backup.keyvalue.KeyValueBackupTask$$ExternalSyntheticLambda1
                @Override // com.android.server.backup.remote.RemoteCallable
                public final void call(java.lang.Object obj) {
                    com.android.server.backup.keyvalue.KeyValueBackupTask.this.lambda$extractAgentData$0(iBackupAgent, backupQuota, transportFlags, (android.app.backup.IBackupCallback) obj);
                }
            }, this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis(), "doBackup()"));
        } catch (java.lang.Exception e) {
            this.mReporter.onCallAgentDoBackupError(str, z, e);
            if (z) {
                throw com.android.server.backup.keyvalue.AgentException.transitory(e);
            }
            throw com.android.server.backup.keyvalue.TaskException.create();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$extractAgentData$0(android.app.IBackupAgent iBackupAgent, long j, int i, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException {
        iBackupAgent.doBackup(this.mSavedState, this.mBackupData, this.mNewState, j, iBackupCallback, i);
    }

    private void checkAgentResult(android.content.pm.PackageInfo packageInfo, com.android.server.backup.remote.RemoteResult remoteResult) throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException {
        if (remoteResult == com.android.server.backup.remote.RemoteResult.FAILED_THREAD_INTERRUPTED) {
            this.mCancelled = true;
            this.mReporter.onAgentCancelled(packageInfo);
            throw com.android.server.backup.keyvalue.TaskException.create();
        }
        if (remoteResult == com.android.server.backup.remote.RemoteResult.FAILED_CANCELLED) {
            this.mReporter.onAgentCancelled(packageInfo);
            throw com.android.server.backup.keyvalue.TaskException.create();
        }
        if (remoteResult == com.android.server.backup.remote.RemoteResult.FAILED_TIMED_OUT) {
            this.mReporter.onAgentTimedOut(packageInfo);
            throw com.android.server.backup.keyvalue.AgentException.transitory();
        }
        com.android.internal.util.Preconditions.checkState(remoteResult.isPresent());
        long j = remoteResult.get();
        if (j == -1) {
            this.mReporter.onAgentResultError(packageInfo);
            throw com.android.server.backup.keyvalue.AgentException.transitory();
        }
        com.android.internal.util.Preconditions.checkState(j == 0);
    }

    private void agentFail(android.app.IBackupAgent iBackupAgent, java.lang.String str) {
        try {
            iBackupAgent.fail(str);
        } catch (java.lang.Exception e) {
            this.mReporter.onFailAgentError(this.mCurrentPackage.packageName);
        }
    }

    private java.lang.String SHA1Checksum(byte[] bArr) {
        try {
            byte[] digest = java.security.MessageDigest.getInstance("SHA-1").digest(bArr);
            java.lang.StringBuilder sb = new java.lang.StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(java.lang.Integer.toHexString(b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            this.mReporter.onDigestError(e);
            return "00";
        }
    }

    private void writeWidgetPayloadIfAppropriate(java.io.FileDescriptor fileDescriptor, java.lang.String str) throws java.io.IOException {
        java.lang.String str2;
        byte[] widgetState = com.android.server.AppWidgetBackupBridge.getWidgetState(str, this.mUserId);
        java.io.File file = new java.io.File(this.mStateDirectory, str + "_widget");
        boolean exists = file.exists();
        if (!exists && widgetState == null) {
            return;
        }
        this.mReporter.onWriteWidgetData(exists, widgetState);
        if (widgetState == null) {
            str2 = null;
        } else {
            str2 = SHA1Checksum(widgetState);
            if (exists) {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    java.io.DataInputStream dataInputStream = new java.io.DataInputStream(fileInputStream);
                    try {
                        java.lang.String readUTF = dataInputStream.readUTF();
                        dataInputStream.close();
                        fileInputStream.close();
                        if (java.util.Objects.equals(str2, readUTF)) {
                            return;
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }
        android.app.backup.BackupDataOutput backupDataOutput = new android.app.backup.BackupDataOutput(fileDescriptor);
        if (widgetState == null) {
            backupDataOutput.writeEntityHeader(com.android.server.backup.UserBackupManagerService.KEY_WIDGET_STATE, -1);
            file.delete();
            return;
        }
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        try {
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(fileOutputStream);
            try {
                dataOutputStream.writeUTF(str2);
                dataOutputStream.close();
                fileOutputStream.close();
                backupDataOutput.writeEntityHeader(com.android.server.backup.UserBackupManagerService.KEY_WIDGET_STATE, widgetState.length);
                backupDataOutput.writeEntityData(widgetState, widgetState.length);
            } finally {
            }
        } catch (java.lang.Throwable th3) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    private int sendDataToTransport() throws com.android.server.backup.keyvalue.AgentException, com.android.server.backup.keyvalue.TaskException, java.io.IOException {
        com.android.internal.util.Preconditions.checkState(this.mBackupData != null);
        checkBackupData(this.mCurrentPackage.applicationInfo, this.mBackupDataFile);
        java.lang.String str = this.mCurrentPackage.packageName;
        writeWidgetPayloadIfAppropriate(this.mBackupData.getFileDescriptor(), str);
        int transportPerformBackup = transportPerformBackup(this.mCurrentPackage, this.mBackupDataFile, this.mSavedStateFile.length() == 0);
        handleTransportStatus(transportPerformBackup, str, this.mBackupDataFile.length());
        return transportPerformBackup;
    }

    private int transportPerformBackup(android.content.pm.PackageInfo packageInfo, java.io.File file, boolean z) throws com.android.server.backup.keyvalue.TaskException {
        java.lang.String str = packageInfo.packageName;
        if (file.length() <= 0) {
            this.mReporter.onEmptyData(packageInfo);
            return 0;
        }
        this.mHasDataToBackup = true;
        try {
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
            try {
                com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("KVBT.transportPerformBackup()");
                this.mReporter.onTransportPerformBackup(str);
                int performBackup = connectOrThrow.performBackup(packageInfo, open, getPerformBackupFlags(this.mUserInitiated, z));
                if (performBackup == 0) {
                    performBackup = connectOrThrow.finishBackup();
                } else if (performBackup == -1001) {
                    this.mReporter.onTransportNotInitialized(connectOrThrow.name());
                }
                if (open != null) {
                    open.close();
                }
                if (z && performBackup == -1006) {
                    this.mReporter.onPackageBackupNonIncrementalAndNonIncrementalRequired(str);
                    throw com.android.server.backup.keyvalue.TaskException.create();
                }
                return performBackup;
            } finally {
            }
        } catch (java.lang.Exception e) {
            this.mReporter.onPackageBackupTransportError(str, e);
            throw com.android.server.backup.keyvalue.TaskException.causedBy(e);
        }
    }

    private void handleTransportStatus(int i, java.lang.String str, long j) throws com.android.server.backup.keyvalue.TaskException, com.android.server.backup.keyvalue.AgentException {
        if (i == 0) {
            this.mReporter.onPackageBackupComplete(str, j);
            return;
        }
        if (i == -1006) {
            this.mReporter.onPackageBackupNonIncrementalRequired(this.mCurrentPackage);
            this.mQueue.add(0, str);
        } else {
            if (i == -1002) {
                this.mReporter.onPackageBackupRejected(str);
                throw com.android.server.backup.keyvalue.AgentException.permanent();
            }
            if (i == -1005) {
                this.mReporter.onPackageBackupQuotaExceeded(str);
                agentDoQuotaExceeded(this.mAgent, str, j);
                throw com.android.server.backup.keyvalue.AgentException.permanent();
            }
            this.mReporter.onPackageBackupTransportFailure(str);
            throw com.android.server.backup.keyvalue.TaskException.forStatus(i);
        }
    }

    private void agentDoQuotaExceeded(@android.annotation.Nullable final android.app.IBackupAgent iBackupAgent, java.lang.String str, final long j) {
        if (iBackupAgent != null) {
            try {
                final long backupQuota = this.mTransportConnection.connectOrThrow("KVBT.agentDoQuotaExceeded()").getBackupQuota(str, false);
                remoteCall(new com.android.server.backup.remote.RemoteCallable() { // from class: com.android.server.backup.keyvalue.KeyValueBackupTask$$ExternalSyntheticLambda0
                    @Override // com.android.server.backup.remote.RemoteCallable
                    public final void call(java.lang.Object obj) {
                        iBackupAgent.doQuotaExceeded(j, backupQuota, (android.app.backup.IBackupCallback) obj);
                    }
                }, this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis(), "doQuotaExceeded()");
            } catch (java.lang.Exception e) {
                this.mReporter.onAgentDoQuotaExceededError(e);
            }
        }
    }

    private void checkBackupData(@android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, java.io.File file) throws java.io.IOException, com.android.server.backup.keyvalue.AgentException {
        if (applicationInfo == null || (applicationInfo.flags & 1) != 0) {
            return;
        }
        android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
        try {
            android.app.backup.BackupDataInput backupDataInput = new android.app.backup.BackupDataInput(open.getFileDescriptor());
            while (backupDataInput.readNextHeader()) {
                java.lang.String key = backupDataInput.getKey();
                if (key != null && key.charAt(0) >= 65280) {
                    this.mReporter.onAgentIllegalKey(this.mCurrentPackage, key);
                    agentFail(this.mAgent, "Illegal backup key: " + key);
                    throw com.android.server.backup.keyvalue.AgentException.permanent();
                }
                backupDataInput.skipEntityData();
            }
            open.close();
        } catch (java.lang.Throwable th) {
            if (open != null) {
                try {
                    open.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private int getPerformBackupFlags(boolean z, boolean z2) {
        int i;
        if (z2) {
            i = 4;
        } else {
            i = 2;
        }
        return (z ? 1 : 0) | i;
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        com.android.internal.util.Preconditions.checkArgument(z, "Can't partially cancel a key-value backup task");
        markCancel();
        waitCancel();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void markCancel() {
        this.mReporter.onCancel();
        this.mCancelled = true;
        com.android.server.backup.remote.RemoteCall remoteCall = this.mPendingCall;
        if (remoteCall != null) {
            remoteCall.cancel();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void waitCancel() {
        this.mCancelAcknowledged.block();
    }

    private void revertTask() {
        long j;
        this.mReporter.onRevertTask();
        try {
            j = this.mTransportConnection.connectOrThrow("KVBT.revertTask()").requestBackupTime();
        } catch (java.lang.Exception e) {
            this.mReporter.onTransportRequestBackupTimeError(e);
            j = 0;
        }
        com.android.server.backup.KeyValueBackupJob.schedule(this.mBackupManagerService.getUserId(), this.mBackupManagerService.getContext(), j, this.mBackupManagerService);
        java.util.Iterator<java.lang.String> it = this.mOriginalQueue.iterator();
        while (it.hasNext()) {
            this.mBackupManagerService.dataChangedImpl(it.next());
        }
    }

    private void cleanUpAgentForError(com.android.server.backup.keyvalue.BackupException backupException) {
        cleanUpAgent(1);
    }

    private void cleanUpAgentForTransportStatus(int i) {
        switch (i) {
            case -1006:
                cleanUpAgent(2);
                return;
            case 0:
                cleanUpAgent(0);
                return;
            default:
                throw new java.lang.AssertionError();
        }
    }

    private void cleanUpAgent(int i) {
        applyStateTransaction(i);
        if (this.mBackupDataFile != null) {
            this.mBackupDataFile.delete();
        }
        this.mBlankStateFile.delete();
        this.mSavedStateFile = null;
        this.mBackupDataFile = null;
        this.mNewStateFile = null;
        tryCloseFileDescriptor(this.mSavedState, "old state");
        tryCloseFileDescriptor(this.mBackupData, "backup data");
        tryCloseFileDescriptor(this.mNewState, "new state");
        this.mSavedState = null;
        this.mBackupData = null;
        this.mNewState = null;
        if (this.mCurrentPackage.applicationInfo != null) {
            this.mBackupManagerService.unbindAgent(this.mCurrentPackage.applicationInfo);
        }
        this.mAgent = null;
    }

    private void applyStateTransaction(int i) {
        switch (i) {
            case 0:
                this.mNewStateFile.renameTo(this.mSavedStateFile);
                return;
            case 1:
                if (this.mNewStateFile != null) {
                    this.mNewStateFile.delete();
                    return;
                }
                return;
            case 2:
                this.mSavedStateFile.delete();
                this.mNewStateFile.delete();
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown state transaction " + i);
        }
    }

    private void tryCloseFileDescriptor(@android.annotation.Nullable java.io.Closeable closeable, java.lang.String str) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.io.IOException e) {
                this.mReporter.onCloseFileDescriptorError(str);
            }
        }
    }

    private com.android.server.backup.remote.RemoteResult remoteCall(com.android.server.backup.remote.RemoteCallable<android.app.backup.IBackupCallback> remoteCallable, long j, java.lang.String str) throws android.os.RemoteException {
        this.mPendingCall = new com.android.server.backup.remote.RemoteCall(this.mCancelled, remoteCallable, j);
        com.android.server.backup.remote.RemoteResult call = this.mPendingCall.call();
        this.mReporter.onRemoteCallReturned(call, str);
        this.mPendingCall = null;
        return call;
    }
}
