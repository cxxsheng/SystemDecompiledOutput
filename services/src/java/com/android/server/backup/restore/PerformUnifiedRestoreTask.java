package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class PerformUnifiedRestoreTask implements com.android.server.backup.BackupRestoreTask {
    private com.android.server.backup.UserBackupManagerService backupManagerService;
    private java.util.List<android.content.pm.PackageInfo> mAcceptSet;
    private android.app.IBackupAgent mAgent;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private java.lang.Boolean mAreVToUListsSet;
    private android.os.ParcelFileDescriptor mBackupData;
    private java.io.File mBackupDataName;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private com.android.server.backup.utils.BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    private android.content.pm.PackageInfo mCurrentPackage;
    private boolean mDidLaunch;
    private final int mEphemeralOpToken;
    private boolean mFinished;
    private boolean mIsSystemRestore;
    private final com.android.server.backup.internal.OnTaskFinishedListener mListener;
    private android.os.ParcelFileDescriptor mNewState;
    private java.io.File mNewStateName;
    private android.app.backup.IRestoreObserver mObserver;
    private final com.android.server.backup.OperationStorage mOperationStorage;
    private com.android.server.backup.PackageManagerBackupAgent mPmAgent;
    private int mPmToken;
    private int mRestoreAttemptedAppsCount;
    private android.app.backup.RestoreDescription mRestoreDescription;
    private java.io.File mStageName;
    private long mStartRealtime;
    private com.android.server.backup.restore.UnifiedRestoreState mState;
    private java.io.File mStateDir;
    private int mStatus;
    private android.content.pm.PackageInfo mTargetPackage;
    private long mToken;
    private final com.android.server.backup.transport.TransportConnection mTransportConnection;
    private final com.android.server.backup.TransportManager mTransportManager;
    private final int mUserId;
    private java.util.List<java.lang.String> mVToUAllowlist;
    private java.util.List<java.lang.String> mVToUDenylist;
    private byte[] mWidgetData;

    @com.android.internal.annotations.VisibleForTesting
    PerformUnifiedRestoreTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str, java.lang.String str2) {
        this.mAreVToUListsSet = false;
        this.mListener = null;
        this.mAgentTimeoutParameters = null;
        this.mOperationStorage = null;
        this.mTransportConnection = transportConnection;
        this.mTransportManager = null;
        this.mEphemeralOpToken = 0;
        this.mUserId = 0;
        this.mBackupEligibilityRules = null;
        this.backupManagerService = userBackupManagerService;
        this.mBackupManagerMonitorEventSender = new com.android.server.backup.utils.BackupManagerMonitorEventSender(null);
        this.mVToUAllowlist = createVToUList(str);
        this.mVToUDenylist = createVToUList(str2);
    }

    public PerformUnifiedRestoreTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, @android.annotation.Nullable android.content.pm.PackageInfo packageInfo, int i, boolean z, @android.annotation.Nullable java.lang.String[] strArr, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        java.lang.String[] strArr2;
        this.mAreVToUListsSet = false;
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mUserId = userBackupManagerService.getUserId();
        this.mTransportManager = userBackupManagerService.getTransportManager();
        this.mEphemeralOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mState = com.android.server.backup.restore.UnifiedRestoreState.INITIAL;
        this.mStartRealtime = android.os.SystemClock.elapsedRealtime();
        this.mTransportConnection = transportConnection;
        this.mObserver = iRestoreObserver;
        this.mBackupManagerMonitorEventSender = new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor);
        this.mToken = j;
        this.mPmToken = i;
        this.mTargetPackage = packageInfo;
        this.mIsSystemRestore = z;
        this.mFinished = false;
        this.mDidLaunch = false;
        this.mListener = onTaskFinishedListener;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (packageInfo != null) {
            this.mAcceptSet = new java.util.ArrayList();
            this.mAcceptSet.add(packageInfo);
        } else {
            if (strArr != null) {
                strArr2 = strArr;
            } else {
                java.lang.String[] packagesToNames = packagesToNames(com.android.server.backup.PackageManagerBackupAgent.getStorableApplications(userBackupManagerService.getPackageManager(), this.mUserId, backupEligibilityRules));
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Full restore; asking about " + packagesToNames.length + " apps");
                strArr2 = packagesToNames;
            }
            this.mAcceptSet = new java.util.ArrayList(strArr2.length);
            boolean z2 = false;
            boolean z3 = false;
            for (java.lang.String str : strArr2) {
                try {
                    android.content.pm.PackageInfo packageInfoAsUser = userBackupManagerService.getPackageManager().getPackageInfoAsUser(str, 0, this.mUserId);
                    if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageInfoAsUser.packageName)) {
                        z2 = true;
                    } else if (com.android.server.backup.UserBackupManagerService.SETTINGS_PACKAGE.equals(packageInfoAsUser.packageName)) {
                        z3 = true;
                    } else {
                        android.content.pm.ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
                        if (backupEligibilityRules.appIsEligibleForBackup(applicationInfo) && (!com.android.server.backup.Flags.enableSkippingRestoreLaunchedApps() || backupEligibilityRules.isAppEligibleForRestore(applicationInfo))) {
                            this.mAcceptSet.add(packageInfoAsUser);
                        }
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
            if (z2) {
                try {
                    this.mAcceptSet.add(0, userBackupManagerService.getPackageManager().getPackageInfoAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, this.mUserId));
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
            }
            if (z3) {
                try {
                    this.mAcceptSet.add(userBackupManagerService.getPackageManager().getPackageInfoAsUser(com.android.server.backup.UserBackupManagerService.SETTINGS_PACKAGE, 0, this.mUserId));
                } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                }
            }
        }
        this.mAcceptSet = userBackupManagerService.filterUserFacingPackages(this.mAcceptSet);
    }

    private java.lang.String[] packagesToNames(java.util.List<android.content.pm.PackageInfo> list) {
        int size = list.size();
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = list.get(i).packageName;
        }
        return strArr;
    }

    /* renamed from: com.android.server.backup.restore.PerformUnifiedRestoreTask$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState = new int[com.android.server.backup.restore.UnifiedRestoreState.values().length];

        static {
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.INITIAL.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.RESTORE_KEYVALUE.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FULL.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FINISHED.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[com.android.server.backup.restore.UnifiedRestoreState.FINAL.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
        switch (com.android.server.backup.restore.PerformUnifiedRestoreTask.AnonymousClass1.$SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[this.mState.ordinal()]) {
            case 1:
                startRestore();
                break;
            case 2:
                dispatchNextRestore();
                break;
            case 3:
                restoreKeyValue();
                break;
            case 4:
                restoreFull();
                break;
            case 5:
                restoreFinished();
                break;
            case 6:
                if (!this.mFinished) {
                    finalizeRestore();
                } else {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Duplicate finish");
                }
                this.mFinished = true;
                break;
        }
    }

    private void startRestore() {
        sendStartRestore(this.mAcceptSet.size());
        if (this.mIsSystemRestore) {
            com.android.server.AppWidgetBackupBridge.systemRestoreStarting(this.mUserId);
            this.mBackupManagerMonitorEventSender.monitorEvent(53, null, 3, addRestoreOperationTypeToEvent(null));
        } else {
            this.mBackupManagerMonitorEventSender.monitorEvent(54, null, 3, addRestoreOperationTypeToEvent(null));
        }
        try {
            this.mStateDir = new java.io.File(this.backupManagerService.getBaseStateDir(), this.mTransportManager.getTransportDirName(this.mTransportConnection.getTransportComponent()));
            android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
            packageInfo.packageName = com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL;
            this.mAcceptSet.add(0, packageInfo);
            android.content.pm.PackageInfo[] packageInfoArr = (android.content.pm.PackageInfo[]) this.mAcceptSet.toArray(new android.content.pm.PackageInfo[0]);
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.startRestore()");
            if (this.mBackupManagerMonitorEventSender.getMonitor() == null) {
                this.mBackupManagerMonitorEventSender.setMonitor(connectOrThrow.getBackupManagerMonitor());
            }
            this.mStatus = connectOrThrow.startRestore(this.mToken, packageInfoArr);
            if (this.mStatus != 0) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Transport error " + this.mStatus + "; no restore possible");
                this.mBackupManagerMonitorEventSender.monitorEvent(55, this.mCurrentPackage, 1, addRestoreOperationTypeToEvent(null));
                this.mStatus = -1000;
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
                return;
            }
            android.app.backup.RestoreDescription nextRestorePackage = connectOrThrow.nextRestorePackage();
            if (nextRestorePackage != null) {
                if (!com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL.equals(nextRestorePackage.getPackageName())) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Required package metadata but got " + nextRestorePackage.getPackageName());
                    this.mBackupManagerMonitorEventSender.monitorEvent(23, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    this.mStatus = -1000;
                    executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
                    return;
                }
                this.mCurrentPackage = new android.content.pm.PackageInfo();
                this.mCurrentPackage.packageName = com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL;
                this.mCurrentPackage.applicationInfo = new android.content.pm.ApplicationInfo();
                this.mCurrentPackage.applicationInfo.uid = 1000;
                this.mPmAgent = this.backupManagerService.makeMetadataAgent(null);
                this.mAgent = android.app.IBackupAgent.Stub.asInterface(this.mPmAgent.onBind());
                initiateOneRestore(this.mCurrentPackage, 0L);
                this.backupManagerService.getBackupHandler().removeMessages(18);
                if (!this.mPmAgent.hasMetadata()) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "PM agent has no metadata, so not restoring");
                    this.mBackupManagerMonitorEventSender.monitorEvent(24, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL, "Package manager restore metadata missing");
                    this.mStatus = -1000;
                    this.backupManagerService.getBackupHandler().removeMessages(20, this);
                    executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
                    return;
                }
                return;
            }
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "No restore metadata available; halting");
            this.mBackupManagerMonitorEventSender.monitorEvent(22, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
            this.mStatus = -1000;
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to contact transport for restore: " + e.getMessage());
            this.mBackupManagerMonitorEventSender.monitorEvent(25, null, 1, addRestoreOperationTypeToEvent(null));
            this.mStatus = -1000;
            this.backupManagerService.getBackupHandler().removeMessages(20, this);
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
        }
    }

    private void dispatchNextRestore() {
        com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState;
        java.lang.String packageName;
        com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState2 = com.android.server.backup.restore.UnifiedRestoreState.FINAL;
        try {
            try {
                this.mRestoreDescription = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.dispatchNextRestore()").nextRestorePackage();
                packageName = this.mRestoreDescription != null ? this.mRestoreDescription.getPackageName() : null;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Can't get next restore target from transport; halting: " + e.getMessage());
                this.mBackupManagerMonitorEventSender.monitorEvent(60, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                this.mStatus = -1000;
                unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.FINAL;
            }
            if (packageName == null) {
                this.mBackupManagerMonitorEventSender.monitorEvent(56, null, 1, addRestoreOperationTypeToEvent(null));
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Failure getting next package name");
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
                return;
            }
            if (this.mRestoreDescription == android.app.backup.RestoreDescription.NO_MORE_PACKAGES) {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "No more packages; finishing restore");
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_SUCCESS, java.lang.Integer.valueOf(this.mRestoreAttemptedAppsCount), java.lang.Integer.valueOf((int) (android.os.SystemClock.elapsedRealtime() - this.mStartRealtime)));
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.FINAL);
                return;
            }
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Next restore package: " + this.mRestoreDescription);
            this.mRestoreAttemptedAppsCount = this.mRestoreAttemptedAppsCount + 1;
            sendOnRestorePackage(this.mRestoreAttemptedAppsCount, packageName);
            com.android.server.backup.PackageManagerBackupAgent.Metadata restoredMetadata = this.mPmAgent.getRestoredMetadata(packageName);
            if (restoredMetadata == null) {
                android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
                packageInfo.packageName = packageName;
                this.mBackupManagerMonitorEventSender.monitorEvent(24, packageInfo, 3, addRestoreOperationTypeToEvent(null));
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "No metadata for " + packageName);
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, packageName, "Package metadata missing");
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
                return;
            }
            try {
                this.mCurrentPackage = this.backupManagerService.getPackageManager().getPackageInfoAsUser(packageName, 134217728, this.mUserId);
                this.mBackupManagerMonitorEventSender.monitorEvent(67, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                if (restoredMetadata.versionCode > this.mCurrentPackage.getLongVersionCode()) {
                    if (this.mIsSystemRestore && isVToUDowngrade(this.mPmAgent.getSourceSdk(), android.os.Build.VERSION.SDK_INT)) {
                        if (!this.mAreVToUListsSet.booleanValue()) {
                            this.mVToUAllowlist = createVToUList(android.provider.Settings.Secure.getStringForUser(this.backupManagerService.getContext().getContentResolver(), "v_to_u_restore_allowlist", this.mUserId));
                            this.mVToUDenylist = createVToUList(android.provider.Settings.Secure.getStringForUser(this.backupManagerService.getContext().getContentResolver(), "v_to_u_restore_denylist", this.mUserId));
                            logVToUListsToBMM();
                            this.mAreVToUListsSet = true;
                        }
                        if (!isPackageEligibleForVToURestore(this.mCurrentPackage)) {
                            this.mBackupManagerMonitorEventSender.monitorEvent(71, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, packageName + " : Package not eligible for V to U downgrade scenario");
                            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, packageName, "Package not eligible for V to U downgrade scenario");
                            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
                            return;
                        }
                        this.mBackupManagerMonitorEventSender.monitorEvent(70, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package " + packageName + " is eligible for V to U downgrade scenario");
                    } else {
                        if ((this.mCurrentPackage.applicationInfo.flags & 131072) == 0) {
                            logDowngradeScenario(false, restoredMetadata);
                            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
                            return;
                        }
                        logDowngradeScenario(true, restoredMetadata);
                    }
                }
                this.mWidgetData = null;
                int dataType = this.mRestoreDescription.getDataType();
                if (dataType == 1) {
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RESTORE_KEYVALUE;
                } else if (dataType == 2) {
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FULL;
                } else {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unrecognized restore type " + dataType);
                    this.mBackupManagerMonitorEventSender.monitorEvent(57, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
                }
                executeNextState(unifiedRestoreState);
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Package not present: " + packageName);
                this.mBackupManagerMonitorEventSender.monitorEvent(26, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, packageName, "Package missing on device");
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
            }
        } catch (java.lang.Throwable th) {
            executeNextState(unifiedRestoreState2);
            throw th;
        }
    }

    private void restoreKeyValue() {
        java.lang.String str = this.mCurrentPackage.packageName;
        this.mBackupManagerMonitorEventSender.monitorEvent(58, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
        if (this.mCurrentPackage.applicationInfo.backupAgentName == null || "".equals(this.mCurrentPackage.applicationInfo.backupAgentName)) {
            this.mBackupManagerMonitorEventSender.monitorEvent(28, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, str, "Package has no agent");
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        com.android.server.backup.PackageManagerBackupAgent.Metadata restoredMetadata = this.mPmAgent.getRestoredMetadata(str);
        if (!com.android.server.backup.BackupUtils.signaturesMatch(restoredMetadata.sigHashes, this.mCurrentPackage, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class))) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Signature mismatch restoring " + str);
            this.mBackupManagerMonitorEventSender.monitorEvent(29, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, str, "Signature mismatch");
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        this.mAgent = this.backupManagerService.bindToAgentSynchronous(this.mCurrentPackage.applicationInfo, 2, this.mBackupEligibilityRules.getBackupDestination());
        if (this.mAgent == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Can't find backup agent for " + str);
            this.mBackupManagerMonitorEventSender.monitorEvent(30, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, str, "Restore agent missing");
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        this.mDidLaunch = true;
        try {
            initiateOneRestore(this.mCurrentPackage, restoredMetadata.versionCode);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error when attempting restore: " + e.toString());
            this.mBackupManagerMonitorEventSender.monitorEvent(61, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
            keyValueAgentErrorCleanup(false);
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    private void initiateOneRestore(android.content.pm.PackageInfo packageInfo, long j) {
        com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState;
        java.lang.String str = packageInfo.packageName;
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "initiateOneRestore packageName=" + str);
        this.mBackupDataName = new java.io.File(this.backupManagerService.getDataDir(), str + ".restore");
        this.mStageName = new java.io.File(this.backupManagerService.getDataDir(), str + ".stage");
        this.mNewStateName = new java.io.File(this.mStateDir, str + com.android.server.backup.keyvalue.KeyValueBackupTask.NEW_STATE_FILE_SUFFIX);
        boolean shouldStageBackupData = shouldStageBackupData(str);
        java.io.File file = shouldStageBackupData ? this.mStageName : this.mBackupDataName;
        boolean z = false;
        try {
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.initiateOneRestore()");
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 1006632960);
            if (connectOrThrow.getRestoreData(open) != 0) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error getting restore data for " + str);
                this.mBackupManagerMonitorEventSender.monitorEvent(63, this.mCurrentPackage, 1, addRestoreOperationTypeToEvent(null));
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                open.close();
                file.delete();
                if (com.android.server.backup.BackupAndRestoreFeatureFlags.getUnifiedRestoreContinueAfterTransportFailureInKvRestore()) {
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
                } else {
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.FINAL;
                }
                executeNextState(unifiedRestoreState);
                return;
            }
            if (shouldStageBackupData) {
                open.close();
                open = android.os.ParcelFileDescriptor.open(file, 268435456);
                this.mBackupData = android.os.ParcelFileDescriptor.open(this.mBackupDataName, 1006632960);
                filterExcludedKeys(str, new android.app.backup.BackupDataInput(open.getFileDescriptor()), new android.app.backup.BackupDataOutput(this.mBackupData.getFileDescriptor()));
                this.mBackupData.close();
            }
            open.close();
            this.mBackupData = android.os.ParcelFileDescriptor.open(this.mBackupDataName, 268435456);
            this.mNewState = android.os.ParcelFileDescriptor.open(this.mNewStateName, 1006632960);
            this.backupManagerService.prepareOperationTimeout(this.mEphemeralOpToken, this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(packageInfo.applicationInfo.uid), this, 1);
            try {
                this.mAgent.doRestoreWithExcludedKeys(this.mBackupData, j, this.mNewState, this.mEphemeralOpToken, this.backupManagerService.getBackupManagerBinder(), new java.util.ArrayList(getExcludedKeysForPackage(str)));
            } catch (java.lang.Exception e) {
                e = e;
                z = true;
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to call app for restore: " + str, e);
                this.mBackupManagerMonitorEventSender.monitorEvent(61, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, str, e.toString());
                keyValueAgentErrorCleanup(z);
                executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
            }
        } catch (java.lang.Exception e2) {
            e = e2;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldStageBackupData(java.lang.String str) {
        return (str.equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) && getExcludedKeysForPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).isEmpty()) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.String> getExcludedKeysForPackage(java.lang.String str) {
        return this.backupManagerService.getExcludedRestoreKeys(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    void filterExcludedKeys(java.lang.String str, android.app.backup.BackupDataInput backupDataInput, android.app.backup.BackupDataOutput backupDataOutput) throws java.lang.Exception {
        int i;
        java.util.Set<java.lang.String> excludedKeysForPackage = getExcludedKeysForPackage(str);
        if (!com.android.server.backup.Flags.enableMaxSizeWritesToPipes()) {
            i = 8192;
        } else {
            i = 65536;
        }
        byte[] bArr = new byte[i];
        while (backupDataInput.readNextHeader()) {
            java.lang.String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (excludedKeysForPackage != null && excludedKeysForPackage.contains(key)) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Skipping blocked key " + key);
                backupDataInput.skipEntityData();
            } else if (key.equals(com.android.server.backup.UserBackupManagerService.KEY_WIDGET_STATE)) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Restoring widget state for " + str);
                this.mWidgetData = new byte[dataSize];
                backupDataInput.readEntityData(this.mWidgetData, 0, dataSize);
            } else {
                if (dataSize > bArr.length) {
                    bArr = new byte[dataSize];
                }
                backupDataInput.readEntityData(bArr, 0, dataSize);
                backupDataOutput.writeEntityHeader(key, dataSize);
                backupDataOutput.writeEntityData(bArr, dataSize);
            }
        }
    }

    private void restoreFull() {
        this.mBackupManagerMonitorEventSender.monitorEvent(59, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
        try {
            new java.lang.Thread(new com.android.server.backup.restore.PerformUnifiedRestoreTask.StreamFeederThread(), "unified-stream-feeder").start();
        } catch (java.io.IOException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to construct pipes for stream restore!");
            this.mBackupManagerMonitorEventSender.monitorEvent(64, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    private void restoreFinished() {
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "restoreFinished packageName=" + this.mCurrentPackage.packageName);
        try {
            this.backupManagerService.prepareOperationTimeout(this.mEphemeralOpToken, this.mAgentTimeoutParameters.getRestoreAgentFinishedTimeoutMillis(), this, 1);
            this.mAgent.doRestoreFinished(this.mEphemeralOpToken, this.backupManagerService.getBackupManagerBinder());
        } catch (java.lang.Exception e) {
            java.lang.String str = this.mCurrentPackage.packageName;
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to finalize restore of " + str);
            this.mBackupManagerMonitorEventSender.monitorEvent(69, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, str, e.toString());
            keyValueAgentErrorCleanup(true);
            executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    class StreamFeederThread extends com.android.server.backup.restore.RestoreEngine implements java.lang.Runnable, com.android.server.backup.BackupRestoreTask {
        com.android.server.backup.restore.FullRestoreEngine mEngine;
        com.android.server.backup.restore.FullRestoreEngineThread mEngineThread;
        private final int mEphemeralOpToken;
        final java.lang.String TAG = "StreamFeederThread";
        android.os.ParcelFileDescriptor[] mTransportPipes = android.os.ParcelFileDescriptor.createPipe();
        android.os.ParcelFileDescriptor[] mEnginePipes = android.os.ParcelFileDescriptor.createPipe();

        public StreamFeederThread() throws java.io.IOException {
            this.mEphemeralOpToken = com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService.generateRandomIntegerToken();
            setRunning(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x019c, code lost:
        
            if (r0 == (-1000)) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x01a1, code lost:
        
            r0 = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0301, code lost:
        
            if (r0 != 64536) goto L44;
         */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0333  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x037d  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x0339 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            java.lang.Throwable th;
            char c;
            com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState;
            char c2;
            com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState2;
            int i;
            int i2;
            com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState3 = com.android.server.backup.restore.UnifiedRestoreState.INITIAL;
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.FULL_RESTORE_PACKAGE, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
            this.mEngine = new com.android.server.backup.restore.FullRestoreEngine(com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mOperationStorage, this, null, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupManagerMonitorEventSender.getMonitor(), com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage, false, this.mEphemeralOpToken, false, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupEligibilityRules);
            int i3 = 0;
            this.mEngineThread = new com.android.server.backup.restore.FullRestoreEngineThread(this.mEngine, this.mEnginePipes[0]);
            android.os.ParcelFileDescriptor parcelFileDescriptor = this.mEnginePipes[1];
            android.os.ParcelFileDescriptor parcelFileDescriptor2 = this.mTransportPipes[0];
            android.os.ParcelFileDescriptor parcelFileDescriptor3 = this.mTransportPipes[1];
            int i4 = com.android.server.backup.Flags.enableMaxSizeWritesToPipes() ? 65536 : 32768;
            byte[] bArr = new byte[i4];
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(parcelFileDescriptor2.getFileDescriptor());
            new java.lang.Thread(this.mEngineThread, "unified-restore-engine").start();
            android.os.Bundle bundle = null;
            try {
                try {
                    com.android.server.backup.transport.BackupTransportClient connectOrThrow = com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask$StreamFeederThread.run()");
                    i = 0;
                    while (true) {
                        if (i != 0) {
                            break;
                        }
                        try {
                            int nextFullRestoreDataChunk = connectOrThrow.getNextFullRestoreDataChunk(parcelFileDescriptor3);
                            if (nextFullRestoreDataChunk > 0) {
                                if (nextFullRestoreDataChunk > i4) {
                                    bArr = new byte[nextFullRestoreDataChunk];
                                    i4 = nextFullRestoreDataChunk;
                                }
                                while (nextFullRestoreDataChunk > 0) {
                                    int read = fileInputStream.read(bArr, i3, nextFullRestoreDataChunk);
                                    fileOutputStream.write(bArr, i3, read);
                                    nextFullRestoreDataChunk -= read;
                                }
                            } else {
                                if (nextFullRestoreDataChunk == -1) {
                                    i = i3;
                                    break;
                                }
                                android.util.Slog.e("StreamFeederThread", "Error " + nextFullRestoreDataChunk + " streaming restore for " + com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
                                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupManagerMonitorEventSender.monitorEvent(66, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage, 1, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.addRestoreOperationTypeToEvent(bundle));
                                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                                i = nextFullRestoreDataChunk;
                            }
                            i3 = 0;
                            bundle = null;
                        } catch (java.io.IOException e) {
                            android.util.Slog.e("StreamFeederThread", "Unable to route data for restore");
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupManagerMonitorEventSender.monitorEvent(65, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage, 2, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.addRestoreOperationTypeToEvent(null));
                            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName, "I/O error on pipes");
                            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
                            libcore.io.IoUtils.closeQuietly(this.mTransportPipes[0]);
                            libcore.io.IoUtils.closeQuietly(this.mTransportPipes[1]);
                            this.mEngineThread.waitForResult();
                            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mDidLaunch = this.mEngine.getAgent() != null;
                            try {
                                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask$StreamFeederThread.run()").abortFullRestore();
                                c2 = 64533;
                            } catch (java.lang.Exception e2) {
                                android.util.Slog.e("StreamFeederThread", "Transport threw from abortFullRestore: " + e2.getMessage());
                                c2 = 64536;
                            }
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService.clearApplicationDataAfterRestoreFailure(com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
                        } catch (java.lang.Exception e3) {
                            e = e3;
                            android.util.Slog.e("StreamFeederThread", "Transport failed during restore: " + e.getMessage());
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupManagerMonitorEventSender.monitorEvent(66, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage, 1, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.addRestoreOperationTypeToEvent(null));
                            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
                            libcore.io.IoUtils.closeQuietly(this.mTransportPipes[0]);
                            libcore.io.IoUtils.closeQuietly(this.mTransportPipes[1]);
                            this.mEngineThread.waitForResult();
                            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mDidLaunch = this.mEngine.getAgent() != null;
                            try {
                                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask$StreamFeederThread.run()").abortFullRestore();
                            } catch (java.lang.Exception e4) {
                                android.util.Slog.e("StreamFeederThread", "Transport threw from abortFullRestore: " + e4.getMessage());
                            }
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService.clearApplicationDataAfterRestoreFailure(com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
                            unifiedRestoreState2 = com.android.server.backup.restore.UnifiedRestoreState.FINAL;
                            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.executeNextState(unifiedRestoreState2);
                            setRunning(false);
                            return;
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
                    libcore.io.IoUtils.closeQuietly(this.mTransportPipes[0]);
                    libcore.io.IoUtils.closeQuietly(this.mTransportPipes[1]);
                    this.mEngineThread.waitForResult();
                    libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mDidLaunch = this.mEngine.getAgent() != null;
                } catch (java.io.IOException e5) {
                } catch (java.lang.Exception e6) {
                    e = e6;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
                    libcore.io.IoUtils.closeQuietly(this.mTransportPipes[0]);
                    libcore.io.IoUtils.closeQuietly(this.mTransportPipes[1]);
                    this.mEngineThread.waitForResult();
                    libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mDidLaunch = this.mEngine.getAgent() != null;
                    if (0 == 0) {
                    }
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.executeNextState(unifiedRestoreState);
                    setRunning(false);
                    throw th;
                }
                if (i == 0) {
                    unifiedRestoreState2 = com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FINISHED;
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mAgent = this.mEngine.getAgent();
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mWidgetData = this.mEngine.getWidgetData();
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.executeNextState(unifiedRestoreState2);
                    setRunning(false);
                    return;
                }
                try {
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask$StreamFeederThread.run()").abortFullRestore();
                    i2 = i;
                } catch (java.lang.Exception e7) {
                    android.util.Slog.e("StreamFeederThread", "Transport threw from abortFullRestore: " + e7.getMessage());
                    i2 = -1000;
                }
                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService.clearApplicationDataAfterRestoreFailure(com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
            } catch (java.lang.Throwable th3) {
                th = th3;
                libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
                libcore.io.IoUtils.closeQuietly(this.mTransportPipes[0]);
                libcore.io.IoUtils.closeQuietly(this.mTransportPipes[1]);
                this.mEngineThread.waitForResult();
                libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mDidLaunch = this.mEngine.getAgent() != null;
                if (0 == 0) {
                    try {
                        com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask$StreamFeederThread.run()").abortFullRestore();
                        c = 0;
                    } catch (java.lang.Exception e8) {
                        android.util.Slog.e("StreamFeederThread", "Transport threw from abortFullRestore: " + e8.getMessage());
                        c = 64536;
                    }
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.backupManagerService.clearApplicationDataAfterRestoreFailure(com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage.packageName);
                    unifiedRestoreState = c == 64536 ? com.android.server.backup.restore.UnifiedRestoreState.FINAL : com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
                } else {
                    unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FINISHED;
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mAgent = this.mEngine.getAgent();
                    com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mWidgetData = this.mEngine.getWidgetData();
                }
                com.android.server.backup.restore.PerformUnifiedRestoreTask.this.executeNextState(unifiedRestoreState);
                setRunning(false);
                throw th;
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
            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
            android.util.Slog.w("StreamFeederThread", "Full-data restore target timed out; shutting down");
            com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mBackupManagerMonitorEventSender.monitorEvent(45, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.mCurrentPackage, 2, com.android.server.backup.restore.PerformUnifiedRestoreTask.this.addRestoreOperationTypeToEvent(null));
            this.mEngineThread.handleTimeout();
            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[1]);
            this.mEnginePipes[1] = null;
            libcore.io.IoUtils.closeQuietly(this.mEnginePipes[0]);
            this.mEnginePipes[0] = null;
        }
    }

    private void finalizeRestore() {
        try {
            this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.finalizeRestore()").finishRestore();
        } catch (java.lang.Exception e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error finishing restore", e);
        }
        sendEndRestore();
        this.backupManagerService.getBackupHandler().removeMessages(8);
        if (this.mPmToken > 0) {
            try {
                this.backupManagerService.getPackageManagerBinder().finishPackageInstall(this.mPmToken, this.mDidLaunch);
            } catch (android.os.RemoteException e2) {
            }
        } else {
            this.backupManagerService.getBackupHandler().sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
        }
        if (this.mIsSystemRestore) {
            com.android.server.AppWidgetBackupBridge.systemRestoreFinished(this.mUserId);
        }
        if (this.mIsSystemRestore && this.mPmAgent != null) {
            this.backupManagerService.setAncestralPackages(this.mPmAgent.getRestoredPackages());
            this.backupManagerService.setAncestralToken(this.mToken);
            this.backupManagerService.setAncestralBackupDestination(this.mBackupEligibilityRules.getBackupDestination());
            this.backupManagerService.writeRestoreTokens();
        }
        synchronized (this.backupManagerService.getPendingRestores()) {
            try {
                if (this.backupManagerService.getPendingRestores().size() > 0) {
                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Starting next pending restore.");
                    this.backupManagerService.getBackupHandler().sendMessage(this.backupManagerService.getBackupHandler().obtainMessage(20, this.backupManagerService.getPendingRestores().remove()));
                } else {
                    this.backupManagerService.setRestoreInProgress(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Restore complete.");
        this.mBackupManagerMonitorEventSender.monitorEvent(68, null, 3, addRestoreOperationTypeToEvent(null));
        this.mListener.onFinished("PerformUnifiedRestoreTask.finalizeRestore()");
    }

    void keyValueAgentErrorCleanup(boolean z) {
        if (z) {
            this.backupManagerService.clearApplicationDataAfterRestoreFailure(this.mCurrentPackage.packageName);
        }
        keyValueAgentCleanup();
    }

    void keyValueAgentCleanup() {
        this.mBackupDataName.delete();
        this.mStageName.delete();
        try {
            if (this.mBackupData != null) {
                this.mBackupData.close();
            }
        } catch (java.io.IOException e) {
        }
        try {
            if (this.mNewState != null) {
                this.mNewState.close();
            }
        } catch (java.io.IOException e2) {
        }
        this.mNewState = null;
        this.mBackupData = null;
        this.mNewStateName.delete();
        if (this.mCurrentPackage.applicationInfo != null) {
            try {
                this.backupManagerService.getActivityManager().unbindBackupAgent(this.mCurrentPackage.applicationInfo);
                boolean z = !android.os.UserHandle.isCore(this.mCurrentPackage.applicationInfo.uid) && (this.mRestoreDescription.getDataType() == 2 || (this.mCurrentPackage.applicationInfo.flags & 65536) != 0);
                if (this.mTargetPackage == null && z) {
                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Restore complete, killing host process of " + this.mCurrentPackage.applicationInfo.processName);
                    this.backupManagerService.getActivityManager().killApplicationProcess(this.mCurrentPackage.applicationInfo.processName, this.mCurrentPackage.applicationInfo.uid);
                }
            } catch (android.os.RemoteException e3) {
            }
        }
        this.backupManagerService.getBackupHandler().removeMessages(18, this);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
        com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState;
        this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
        switch (com.android.server.backup.restore.PerformUnifiedRestoreTask.AnonymousClass1.$SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[this.mState.ordinal()]) {
            case 1:
                unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
                break;
            case 2:
            default:
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unexpected restore callback into state " + this.mState);
                keyValueAgentErrorCleanup(true);
                unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.FINAL;
                break;
            case 3:
            case 4:
                unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RESTORE_FINISHED;
                break;
            case 5:
                int length = (int) this.mBackupDataName.length();
                this.mBackupManagerMonitorEventSender.monitorEvent(62, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(null));
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_PACKAGE, this.mCurrentPackage.packageName, java.lang.Integer.valueOf(length));
                this.mBackupManagerMonitorEventSender.monitorAgentLoggingResults(this.mCurrentPackage, this.mAgent);
                keyValueAgentCleanup();
                if (this.mWidgetData != null) {
                    this.backupManagerService.restoreWidgetData(this.mCurrentPackage.packageName, this.mWidgetData);
                }
                unifiedRestoreState = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
                break;
        }
        executeNextState(unifiedRestoreState);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Timeout restoring application " + this.mCurrentPackage.packageName);
        this.mBackupManagerMonitorEventSender.monitorEvent(31, this.mCurrentPackage, 2, addRestoreOperationTypeToEvent(null));
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, this.mCurrentPackage.packageName, "restore timeout");
        keyValueAgentErrorCleanup(true);
        executeNextState(com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE);
    }

    @com.android.internal.annotations.VisibleForTesting
    void executeNextState(com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
        this.backupManagerService.getBackupHandler().sendMessage(this.backupManagerService.getBackupHandler().obtainMessage(20, this));
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.backup.restore.UnifiedRestoreState getCurrentUnifiedRestoreStateForTesting() {
        return this.mState;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setCurrentUnifiedRestoreStateForTesting(com.android.server.backup.restore.UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setStateDirForTesting(java.io.File file) {
        this.mStateDir = file;
    }

    @com.android.internal.annotations.VisibleForTesting
    void initiateOneRestoreForTesting(android.content.pm.PackageInfo packageInfo, long j) {
        initiateOneRestore(packageInfo, j);
    }

    void sendStartRestore(int i) {
        if (this.mObserver != null) {
            try {
                this.mObserver.restoreStarting(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Restore observer went away: startRestore");
                this.mObserver = null;
            }
        }
    }

    void sendOnRestorePackage(int i, java.lang.String str) {
        if (this.mObserver != null) {
            try {
                this.mObserver.onUpdate(i, str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Restore observer died in onUpdate");
                this.mObserver = null;
            }
        }
    }

    void sendEndRestore() {
        if (this.mObserver != null) {
            try {
                this.mObserver.restoreFinished(this.mStatus);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Restore observer went away: endRestore");
                this.mObserver = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Bundle addRestoreOperationTypeToEvent(@android.annotation.Nullable android.os.Bundle bundle) {
        return com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(bundle, "android.app.backup.extra.OPERATION_TYPE", 1);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isVToUDowngrade(int i, int i2) {
        return com.android.server.backup.Flags.enableVToURestoreForSystemComponentsInAllowlist() && i > 34 && i2 == 34;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.List<java.lang.String> createVToUList(@android.annotation.Nullable java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (str != null) {
            return java.util.Arrays.asList(str.split(","));
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isPackageEligibleForVToURestore(android.content.pm.PackageInfo packageInfo) {
        if (this.mVToUDenylist.contains(packageInfo.packageName)) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, packageInfo.packageName + " : Package is in V to U denylist");
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 131072) == 0) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, packageInfo.packageName + " : Package has restoreAnyVersion=false and is in V to U allowlist");
            return this.mVToUAllowlist.contains(packageInfo.packageName);
        }
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, packageInfo.packageName + " : Package has restoreAnyVersion=true and is not in V to U denylist");
        return true;
    }

    private void logDowngradeScenario(boolean z, com.android.server.backup.PackageManagerBackupAgent.Metadata metadata) {
        android.os.Bundle putMonitoringExtra;
        java.lang.String str;
        android.os.Bundle putMonitoringExtra2 = com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_RESTORE_VERSION", metadata.versionCode);
        if (z) {
            putMonitoringExtra = com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(putMonitoringExtra2, "android.app.backup.extra.LOG_RESTORE_ANYWAY", true);
            str = "Source version " + metadata.versionCode + " > installed version " + this.mCurrentPackage.getLongVersionCode() + " but restoreAnyVersion";
        } else {
            putMonitoringExtra = com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra(putMonitoringExtra2, "android.app.backup.extra.LOG_RESTORE_ANYWAY", false);
            str = "Source version " + metadata.versionCode + " > installed version " + this.mCurrentPackage.getLongVersionCode();
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_AGENT_FAILURE, this.mCurrentPackage.packageName, str);
        }
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package " + this.mCurrentPackage.packageName + ": " + str);
        this.mBackupManagerMonitorEventSender.monitorEvent(27, this.mCurrentPackage, 3, addRestoreOperationTypeToEvent(putMonitoringExtra));
    }

    private void logVToUListsToBMM() {
        this.mBackupManagerMonitorEventSender.monitorEvent(72, null, 3, addRestoreOperationTypeToEvent(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.V_TO_U_ALLOWLIST", this.mVToUAllowlist.toString())));
        this.mBackupManagerMonitorEventSender.monitorEvent(72, null, 3, addRestoreOperationTypeToEvent(com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.V_TO_U_DENYLIST", this.mVToUDenylist.toString())));
    }
}
