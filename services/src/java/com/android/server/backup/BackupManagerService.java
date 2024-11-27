package com.android.server.backup;

/* loaded from: classes.dex */
public class BackupManagerService extends android.app.backup.IBackupManager.Stub {
    private static final java.lang.String BACKUP_ACTIVATED_FILENAME = "backup-activated";
    private static final java.lang.String BACKUP_DISABLE_PROPERTY = "ro.backup.disable";
    private static final java.lang.String BACKUP_SUPPRESS_FILENAME = "backup-suppress";
    private static final java.lang.String BACKUP_THREAD = "backup";
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_SCHEDULING = true;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DUMP_RUNNING_USERS_MESSAGE = "Backup Manager is running for users:";
    public static final boolean MORE_DEBUG = false;
    private static final java.lang.String REMEMBER_ACTIVATED_FILENAME = "backup-remember-activated";
    public static final java.lang.String TAG = "BackupManagerService";
    static com.android.server.backup.BackupManagerService sInstance;
    private final android.content.Context mContext;
    private int mDefaultBackupUserId;
    private final android.os.Handler mHandler;
    private final java.util.Set<android.content.ComponentName> mTransportWhitelist;
    private final android.os.UserManager mUserManager;
    private final android.util.SparseArray<com.android.server.backup.UserBackupManagerService> mUserServices;
    private final java.lang.Object mStateLock = new java.lang.Object();
    private final android.content.BroadcastReceiver mUserRemovedReceiver = new com.android.server.backup.BackupManagerService.AnonymousClass1();
    private boolean mHasFirstUserUnlockedSinceBoot = false;
    private final boolean mGlobalDisable = isBackupDisabled();

    static com.android.server.backup.BackupManagerService getInstance() {
        com.android.server.backup.BackupManagerService backupManagerService = sInstance;
        java.util.Objects.requireNonNull(backupManagerService);
        return backupManagerService;
    }

    /* renamed from: com.android.server.backup.BackupManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            final int intExtra;
            if ("android.intent.action.USER_REMOVED".equals(intent.getAction()) && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ)) > 0) {
                com.android.server.backup.BackupManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.BackupManagerService$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.backup.BackupManagerService.AnonymousClass1.this.lambda$onReceive$0(intExtra);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(int i) {
            com.android.server.backup.BackupManagerService.this.onRemovedNonSystemUser(i);
        }
    }

    public BackupManagerService(android.content.Context context) {
        this.mContext = context;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("backup", 10);
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper());
        this.mUserManager = android.os.UserManager.get(context);
        this.mUserServices = new android.util.SparseArray<>();
        java.util.Set<android.content.ComponentName> backupTransportWhitelist = com.android.server.SystemConfig.getInstance().getBackupTransportWhitelist();
        this.mTransportWhitelist = backupTransportWhitelist == null ? java.util.Collections.emptySet() : backupTransportWhitelist;
        this.mContext.registerReceiver(this.mUserRemovedReceiver, new android.content.IntentFilter("android.intent.action.USER_REMOVED"));
        android.os.UserHandle mainUser = getUserManager().getMainUser();
        this.mDefaultBackupUserId = mainUser != null ? mainUser.getIdentifier() : 0;
        android.util.Slog.d(TAG, "Default backup user id = " + this.mDefaultBackupUserId);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getBackupHandler() {
        return this.mHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isBackupDisabled() {
        return android.os.SystemProperties.getBoolean(BACKUP_DISABLE_PROPERTY, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int binderGetCallingUserId() {
        return android.os.Binder.getCallingUserHandle().getIdentifier();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int binderGetCallingUid() {
        return android.os.Binder.getCallingUid();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getSuppressFileForUser(int i) {
        return new java.io.File(com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(i), BACKUP_SUPPRESS_FILENAME);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getRememberActivatedFileForNonSystemUser(int i) {
        return com.android.server.backup.UserBackupManagerFiles.getStateFileInSystemDir(REMEMBER_ACTIVATED_FILENAME, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getActivatedFileForUser(int i) {
        return com.android.server.backup.UserBackupManagerFiles.getStateFileInSystemDir(BACKUP_ACTIVATED_FILENAME, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemovedNonSystemUser(int i) {
        android.util.Slog.i(TAG, "Removing state for non system user " + i);
        if (!android.os.FileUtils.deleteContentsAndDir(com.android.server.backup.UserBackupManagerFiles.getStateDirInSystemDir(i))) {
            android.util.Slog.w(TAG, "Failed to delete state dir for removed user: " + i);
        }
    }

    private void createFile(java.io.File file) throws java.io.IOException {
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (!file.createNewFile()) {
            android.util.Slog.w(TAG, "Failed to create file " + file.getPath());
        }
    }

    private void deleteFile(java.io.File file) {
        if (file.exists() && !file.delete()) {
            android.util.Slog.w(TAG, "Failed to delete file " + file.getPath());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private void deactivateBackupForUserLocked(int i) throws java.io.IOException {
        if (i == 0 || i == this.mDefaultBackupUserId) {
            createFile(getSuppressFileForUser(i));
        } else {
            deleteFile(getActivatedFileForUser(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private void activateBackupForUserLocked(int i) throws java.io.IOException {
        if (i == 0 || i == this.mDefaultBackupUserId) {
            deleteFile(getSuppressFileForUser(i));
        } else {
            createFile(getActivatedFileForUser(i));
        }
    }

    public boolean isUserReadyForBackup(int i) {
        enforceCallingPermissionOnUserId(i, "isUserReadyForBackup()");
        return this.mUserServices.get(i) != null;
    }

    private boolean isBackupActivatedForUser(int i) {
        if (getSuppressFileForUser(0).exists()) {
            return false;
        }
        boolean z = i == this.mDefaultBackupUserId;
        if (i == 0 && !z) {
            return false;
        }
        if (z && getSuppressFileForUser(i).exists()) {
            return false;
        }
        return z || getActivatedFileForUser(i).exists();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.content.Context getContext() {
        return this.mContext;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.os.UserManager getUserManager() {
        return this.mUserManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void postToHandler(java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }

    @com.android.internal.annotations.VisibleForTesting
    void startServiceForUser(int i) {
        if (this.mGlobalDisable) {
            android.util.Slog.i(TAG, "Backup service not supported");
            return;
        }
        if (!isBackupActivatedForUser(i)) {
            android.util.Slog.i(TAG, "Backup not activated for user " + i);
            return;
        }
        if (this.mUserServices.get(i) != null) {
            android.util.Slog.i(TAG, "userId " + i + " already started, so not starting again");
            return;
        }
        android.util.Slog.i(TAG, "Starting service for user: " + i);
        startServiceForUser(i, com.android.server.backup.UserBackupManagerService.createAndInitializeService(i, this.mContext, this, this.mTransportWhitelist));
    }

    @com.android.internal.annotations.VisibleForTesting
    void startServiceForUser(int i, com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        this.mUserServices.put(i, userBackupManagerService);
        android.os.Trace.traceBegin(64L, "backup enable");
        userBackupManagerService.initializeBackupEnableState();
        android.os.Trace.traceEnd(64L);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void stopServiceForUser(int i) {
        com.android.server.backup.UserBackupManagerService userBackupManagerService = (com.android.server.backup.UserBackupManagerService) this.mUserServices.removeReturnOld(i);
        if (userBackupManagerService != null) {
            userBackupManagerService.tearDownService();
            com.android.server.backup.KeyValueBackupJob.cancel(i, this.mContext);
            com.android.server.backup.FullBackupJob.cancel(i, this.mContext);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseArray<com.android.server.backup.UserBackupManagerService> getUserServices() {
        return this.mUserServices;
    }

    void onStopUser(final int i) {
        postToHandler(new java.lang.Runnable() { // from class: com.android.server.backup.BackupManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.backup.BackupManagerService.this.lambda$onStopUser$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStopUser$0(int i) {
        if (!this.mGlobalDisable) {
            android.util.Slog.i(TAG, "Stopping service for user: " + i);
            stopServiceForUser(i);
        }
    }

    @android.annotation.Nullable
    public com.android.server.backup.UserBackupManagerService getUserService(int i) {
        return this.mUserServices.get(i);
    }

    private void enforcePermissionsOnUser(int i) throws java.lang.SecurityException {
        if (!(i == 0 || getUserManager().getUserInfo(i).isManagedProfile())) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "No permission to configure backup activity");
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No permission to configure backup activity");
        } else {
            int binderGetCallingUid = binderGetCallingUid();
            if (binderGetCallingUid != 1000 && binderGetCallingUid != 0) {
                throw new java.lang.SecurityException("No permission to configure backup activity");
            }
        }
    }

    public void setBackupServiceActive(int i, boolean z) {
        enforcePermissionsOnUser(i);
        if (i != 0) {
            try {
                java.io.File rememberActivatedFileForNonSystemUser = getRememberActivatedFileForNonSystemUser(i);
                createFile(rememberActivatedFileForNonSystemUser);
                com.android.server.backup.utils.RandomAccessFileUtils.writeBoolean(rememberActivatedFileForNonSystemUser, z);
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Unable to persist backup service activity", e);
            }
        }
        if (this.mGlobalDisable) {
            android.util.Slog.i(TAG, "Backup service not supported");
            return;
        }
        synchronized (this.mStateLock) {
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Making backup ");
                sb.append(z ? "" : "in");
                sb.append(com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE);
                android.util.Slog.i(TAG, sb.toString());
                if (z) {
                    try {
                        activateBackupForUserLocked(i);
                    } catch (java.io.IOException e2) {
                        android.util.Slog.e(TAG, "Unable to persist backup service activity");
                    }
                    if (getUserManager().isUserUnlocked(i)) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            startServiceForUser(i);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                } else {
                    try {
                        deactivateBackupForUserLocked(i);
                    } catch (java.io.IOException e3) {
                        android.util.Slog.e(TAG, "Unable to persist backup service inactivity");
                    }
                    onStopUser(i);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public boolean isBackupServiceActive(int i) {
        boolean z;
        if (android.app.compat.CompatChanges.isChangeEnabled(158482162L, android.os.Binder.getCallingUid())) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupServiceActive");
        }
        synchronized (this.mStateLock) {
            try {
                z = !this.mGlobalDisable && isBackupActivatedForUser(i);
            } finally {
            }
        }
        return z;
    }

    public void dataChangedForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            dataChanged(i, str);
        }
    }

    public void dataChanged(java.lang.String str) throws android.os.RemoteException {
        dataChangedForUser(binderGetCallingUserId(), str);
    }

    public void dataChanged(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "dataChanged()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.dataChanged(str);
        }
    }

    public void initializeTransportsForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            initializeTransports(i, strArr, iBackupObserver);
        }
    }

    public void initializeTransports(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "initializeTransports()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.initializeTransports(strArr, iBackupObserver);
        }
    }

    public void clearBackupDataForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            clearBackupData(i, str, str2);
        }
    }

    public void clearBackupData(int i, java.lang.String str, java.lang.String str2) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "clearBackupData()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.clearBackupData(str, str2);
        }
    }

    public void clearBackupData(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        clearBackupDataForUser(binderGetCallingUserId(), str, str2);
    }

    public void agentConnectedForUser(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            agentConnected(i, str, iBinder);
        }
    }

    public void agentConnected(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        agentConnectedForUser(binderGetCallingUserId(), str, iBinder);
    }

    public void agentConnected(int i, java.lang.String str, android.os.IBinder iBinder) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "agentConnected()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.agentConnected(str, iBinder);
        }
    }

    public void agentDisconnectedForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            agentDisconnected(i, str);
        }
    }

    public void agentDisconnected(java.lang.String str) throws android.os.RemoteException {
        agentDisconnectedForUser(binderGetCallingUserId(), str);
    }

    public void agentDisconnected(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "agentDisconnected()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.agentDisconnected(str);
        }
    }

    public void restoreAtInstallForUser(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            restoreAtInstall(i, str, i2);
        }
    }

    public void restoreAtInstall(java.lang.String str, int i) throws android.os.RemoteException {
        restoreAtInstallForUser(binderGetCallingUserId(), str, i);
    }

    public void restoreAtInstall(int i, java.lang.String str, int i2) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "restoreAtInstall()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.restoreAtInstall(str, i2);
        }
    }

    public void setFrameworkSchedulingEnabledForUser(int i, boolean z) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "setFrameworkSchedulingEnabledForUser()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.setFrameworkSchedulingEnabled(z);
        }
    }

    public void setBackupEnabledForUser(int i, boolean z) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            setBackupEnabled(i, z);
        }
    }

    public void setBackupEnabled(boolean z) throws android.os.RemoteException {
        setBackupEnabledForUser(binderGetCallingUserId(), z);
    }

    public void setBackupEnabled(int i, boolean z) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "setBackupEnabled()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.setBackupEnabled(z);
        }
    }

    public void setAutoRestoreForUser(int i, boolean z) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            setAutoRestore(i, z);
        }
    }

    public void setAutoRestore(boolean z) throws android.os.RemoteException {
        setAutoRestoreForUser(binderGetCallingUserId(), z);
    }

    public void setAutoRestore(int i, boolean z) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "setAutoRestore()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.setAutoRestore(z);
        }
    }

    public boolean isBackupEnabledForUser(int i) throws android.os.RemoteException {
        return isUserReadyForBackup(i) && isBackupEnabled(i);
    }

    public boolean isBackupEnabled() throws android.os.RemoteException {
        return isBackupEnabledForUser(binderGetCallingUserId());
    }

    public boolean isBackupEnabled(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "isBackupEnabled()");
        return serviceForUserIfCallerHasPermission != null && serviceForUserIfCallerHasPermission.isBackupEnabled();
    }

    public boolean setBackupPassword(java.lang.String str, java.lang.String str2) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        return isUserReadyForBackup(binderGetCallingUserId()) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(0, "setBackupPassword()")) != null && serviceForUserIfCallerHasPermission.setBackupPassword(str, str2);
    }

    public boolean hasBackupPassword() throws android.os.RemoteException {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        return isUserReadyForBackup(binderGetCallingUserId()) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(0, "hasBackupPassword()")) != null && serviceForUserIfCallerHasPermission.hasBackupPassword();
    }

    public void backupNowForUser(int i) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            backupNow(i);
        }
    }

    public void backupNow() throws android.os.RemoteException {
        backupNowForUser(binderGetCallingUserId());
    }

    public void backupNow(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "backupNow()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.backupNow();
        }
    }

    public void adbBackup(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "adbBackup()")) != null) {
            serviceForUserIfCallerHasPermission.adbBackup(parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z8, strArr);
        }
    }

    public void fullTransportBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            fullTransportBackup(i, strArr);
        }
    }

    public void fullTransportBackup(int i, java.lang.String[] strArr) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "fullTransportBackup()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.fullTransportBackup(strArr);
        }
    }

    public void adbRestore(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "adbRestore()")) != null) {
            serviceForUserIfCallerHasPermission.adbRestore(parcelFileDescriptor);
        }
    }

    public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            acknowledgeAdbBackupOrRestore(i, i2, z, str, str2, iFullBackupRestoreObserver);
        }
    }

    public void acknowledgeAdbBackupOrRestore(int i, int i2, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "acknowledgeAdbBackupOrRestore()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.acknowledgeAdbBackupOrRestore(i2, z, str, str2, iFullBackupRestoreObserver);
        }
    }

    public void acknowledgeFullBackupOrRestore(int i, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
        acknowledgeFullBackupOrRestoreForUser(binderGetCallingUserId(), i, z, str, str2, iFullBackupRestoreObserver);
    }

    public java.lang.String getCurrentTransportForUser(int i) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return getCurrentTransport(i);
        }
        return null;
    }

    public java.lang.String getCurrentTransport() throws android.os.RemoteException {
        return getCurrentTransportForUser(binderGetCallingUserId());
    }

    @android.annotation.Nullable
    public java.lang.String getCurrentTransport(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getCurrentTransport()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getCurrentTransport();
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCurrentTransportComponentForUser(int i) {
        if (isUserReadyForBackup(i)) {
            return getCurrentTransportComponent(i);
        }
        return null;
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCurrentTransportComponent(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getCurrentTransportComponent()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getCurrentTransportComponent();
    }

    public java.lang.String[] listAllTransportsForUser(int i) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return listAllTransports(i);
        }
        return null;
    }

    @android.annotation.Nullable
    public java.lang.String[] listAllTransports(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "listAllTransports()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.listAllTransports();
    }

    public java.lang.String[] listAllTransports() throws android.os.RemoteException {
        return listAllTransportsForUser(binderGetCallingUserId());
    }

    public android.content.ComponentName[] listAllTransportComponentsForUser(int i) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return listAllTransportComponents(i);
        }
        return null;
    }

    @android.annotation.Nullable
    public android.content.ComponentName[] listAllTransportComponents(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "listAllTransportComponents()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.listAllTransportComponents();
    }

    public java.lang.String[] getTransportWhitelist() {
        if (!isUserReadyForBackup(binderGetCallingUserId())) {
            return null;
        }
        java.lang.String[] strArr = new java.lang.String[this.mTransportWhitelist.size()];
        java.util.Iterator<android.content.ComponentName> it = this.mTransportWhitelist.iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = it.next().flattenToShortString();
            i++;
        }
        return strArr;
    }

    public void updateTransportAttributesForUser(int i, android.content.ComponentName componentName, java.lang.String str, @android.annotation.Nullable android.content.Intent intent, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent2, java.lang.CharSequence charSequence) {
        if (isUserReadyForBackup(i)) {
            updateTransportAttributes(i, componentName, str, intent, str2, intent2, charSequence);
        }
    }

    public void updateTransportAttributes(int i, android.content.ComponentName componentName, java.lang.String str, @android.annotation.Nullable android.content.Intent intent, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent2, java.lang.CharSequence charSequence) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "updateTransportAttributes()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.updateTransportAttributes(componentName, str, intent, str2, intent2, charSequence);
        }
    }

    public java.lang.String selectBackupTransportForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return selectBackupTransport(i, str);
        }
        return null;
    }

    public java.lang.String selectBackupTransport(java.lang.String str) throws android.os.RemoteException {
        return selectBackupTransportForUser(binderGetCallingUserId(), str);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public java.lang.String selectBackupTransport(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "selectBackupTransport()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.selectBackupTransport(str);
    }

    public void selectBackupTransportAsyncForUser(int i, android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            selectBackupTransportAsync(i, componentName, iSelectBackupTransportCallback);
        } else if (iSelectBackupTransportCallback != null) {
            try {
                iSelectBackupTransportCallback.onFailure(-2001);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void selectBackupTransportAsync(int i, android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "selectBackupTransportAsync()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.selectBackupTransportAsync(componentName, iSelectBackupTransportCallback);
        }
    }

    public android.content.Intent getConfigurationIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return getConfigurationIntent(i, str);
        }
        return null;
    }

    public android.content.Intent getConfigurationIntent(java.lang.String str) throws android.os.RemoteException {
        return getConfigurationIntentForUser(binderGetCallingUserId(), str);
    }

    @android.annotation.Nullable
    public android.content.Intent getConfigurationIntent(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getConfigurationIntent()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getConfigurationIntent(str);
    }

    public java.lang.String getDestinationStringForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return getDestinationString(i, str);
        }
        return null;
    }

    public java.lang.String getDestinationString(java.lang.String str) throws android.os.RemoteException {
        return getDestinationStringForUser(binderGetCallingUserId(), str);
    }

    @android.annotation.Nullable
    public java.lang.String getDestinationString(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getDestinationString()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getDestinationString(str);
    }

    public android.content.Intent getDataManagementIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return getDataManagementIntent(i, str);
        }
        return null;
    }

    public android.content.Intent getDataManagementIntent(java.lang.String str) throws android.os.RemoteException {
        return getDataManagementIntentForUser(binderGetCallingUserId(), str);
    }

    @android.annotation.Nullable
    public android.content.Intent getDataManagementIntent(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getDataManagementIntent()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getDataManagementIntent(str);
    }

    public java.lang.CharSequence getDataManagementLabelForUser(int i, java.lang.String str) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return getDataManagementLabel(i, str);
        }
        return null;
    }

    @android.annotation.Nullable
    public java.lang.CharSequence getDataManagementLabel(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getDataManagementLabel()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.getDataManagementLabel(str);
    }

    public android.app.backup.IRestoreSession beginRestoreSessionForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            return beginRestoreSession(i, str, str2);
        }
        return null;
    }

    @android.annotation.Nullable
    public android.app.backup.IRestoreSession beginRestoreSession(int i, java.lang.String str, java.lang.String str2) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "beginRestoreSession()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.beginRestoreSession(str, str2);
    }

    public void opCompleteForUser(int i, int i2, long j) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            opComplete(i, i2, j);
        }
    }

    public void opComplete(int i, long j) throws android.os.RemoteException {
        opCompleteForUser(binderGetCallingUserId(), i, j);
    }

    public void opComplete(int i, int i2, long j) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "opComplete()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.opComplete(i2, j);
        }
    }

    public long getAvailableRestoreTokenForUser(int i, java.lang.String str) {
        if (isUserReadyForBackup(i)) {
            return getAvailableRestoreToken(i, str);
        }
        return 0L;
    }

    public long getAvailableRestoreToken(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "getAvailableRestoreToken()");
        if (serviceForUserIfCallerHasPermission == null) {
            return 0L;
        }
        return serviceForUserIfCallerHasPermission.getAvailableRestoreToken(str);
    }

    public boolean isAppEligibleForBackupForUser(int i, java.lang.String str) {
        return isUserReadyForBackup(i) && isAppEligibleForBackup(i, str);
    }

    public boolean isAppEligibleForBackup(int i, java.lang.String str) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "isAppEligibleForBackup()");
        return serviceForUserIfCallerHasPermission != null && serviceForUserIfCallerHasPermission.isAppEligibleForBackup(str);
    }

    public java.lang.String[] filterAppsEligibleForBackupForUser(int i, java.lang.String[] strArr) {
        if (isUserReadyForBackup(i)) {
            return filterAppsEligibleForBackup(i, strArr);
        }
        return null;
    }

    @android.annotation.Nullable
    public java.lang.String[] filterAppsEligibleForBackup(int i, java.lang.String[] strArr) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "filterAppsEligibleForBackup()");
        if (serviceForUserIfCallerHasPermission == null) {
            return null;
        }
        return serviceForUserIfCallerHasPermission.filterAppsEligibleForBackup(strArr);
    }

    public int requestBackupForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i2) throws android.os.RemoteException {
        if (!isUserReadyForBackup(i)) {
            return -2001;
        }
        return requestBackup(i, strArr, iBackupObserver, iBackupManagerMonitor, i2);
    }

    public int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i) throws android.os.RemoteException {
        return requestBackup(binderGetCallingUserId(), strArr, iBackupObserver, iBackupManagerMonitor, i);
    }

    public int requestBackup(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i2) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "requestBackup()");
        if (serviceForUserIfCallerHasPermission == null) {
            return -2001;
        }
        return serviceForUserIfCallerHasPermission.requestBackup(strArr, iBackupObserver, iBackupManagerMonitor, i2);
    }

    public void cancelBackupsForUser(int i) throws android.os.RemoteException {
        if (isUserReadyForBackup(i)) {
            cancelBackups(i);
        }
    }

    public void cancelBackups() throws android.os.RemoteException {
        cancelBackupsForUser(binderGetCallingUserId());
    }

    public void cancelBackups(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "cancelBackups()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.cancelBackups();
        }
    }

    @android.annotation.Nullable
    public android.os.UserHandle getUserForAncestralSerialNumber(long j) {
        if (this.mGlobalDisable) {
            return null;
        }
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int[] profileIds = getUserManager().getProfileIds(identifier, false);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            for (int i : profileIds) {
                com.android.server.backup.UserBackupManagerService userBackupManagerService = this.mUserServices.get(i);
                if (userBackupManagerService != null && userBackupManagerService.getAncestralSerialNumber() == j) {
                    return android.os.UserHandle.of(i);
                }
            }
            return null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void setAncestralSerialNumber(long j) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (!this.mGlobalDisable && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(android.os.Binder.getCallingUserHandle().getIdentifier(), "setAncestralSerialNumber()")) != null) {
            serviceForUserIfCallerHasPermission.setAncestralSerialNumber(j);
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            return;
        }
        dumpWithoutCheckingPermission(fileDescriptor, printWriter, strArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    void dumpWithoutCheckingPermission(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!isUserReadyForBackup(binderGetCallingUserId())) {
            printWriter.println("Inactive");
            return;
        }
        int i = 0;
        if (strArr != null) {
            for (java.lang.String str : strArr) {
                if ("-h".equals(str)) {
                    printWriter.println("'dumpsys backup' optional arguments:");
                    printWriter.println("  -h       : this help text");
                    printWriter.println("  a[gents] : dump information about defined backup agents");
                    printWriter.println("  transportclients : dump information about transport clients");
                    printWriter.println("  transportstats : dump transport statts");
                    printWriter.println("  users    : dump the list of users for which backup service is running");
                    return;
                }
                if (com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS.equals(str.toLowerCase())) {
                    printWriter.print(DUMP_RUNNING_USERS_MESSAGE);
                    while (i < this.mUserServices.size()) {
                        printWriter.print(" " + this.mUserServices.keyAt(i));
                        i++;
                    }
                    printWriter.println();
                    return;
                }
            }
        }
        while (i < this.mUserServices.size()) {
            com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(this.mUserServices.keyAt(i), "dump()");
            if (serviceForUserIfCallerHasPermission != null) {
                serviceForUserIfCallerHasPermission.dump(fileDescriptor, printWriter, strArr);
            }
            i++;
        }
    }

    public boolean beginFullBackup(int i, com.android.server.backup.FullBackupJob fullBackupJob) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        return isUserReadyForBackup(i) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "beginFullBackup()")) != null && serviceForUserIfCallerHasPermission.beginFullBackup(fullBackupJob);
    }

    public void endFullBackup(int i) {
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission;
        if (isUserReadyForBackup(i) && (serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(i, "endFullBackup()")) != null) {
            serviceForUserIfCallerHasPermission.endFullBackup();
        }
    }

    public void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) {
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        if (!isUserReadyForBackup(identifier)) {
            android.util.Slog.w(TAG, "Returning from excludeKeysFromRestore as backup for user" + identifier + " is not initialized yet");
            return;
        }
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(identifier, "excludeKeysFromRestore()");
        if (serviceForUserIfCallerHasPermission != null) {
            serviceForUserIfCallerHasPermission.excludeKeysFromRestore(str, list);
        }
    }

    public void reportDelayedRestoreResult(java.lang.String str, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list) {
        int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
        if (!isUserReadyForBackup(identifier)) {
            android.util.Slog.w(TAG, "Returning from reportDelayedRestoreResult as backup for user" + identifier + " is not initialized yet");
            return;
        }
        com.android.server.backup.UserBackupManagerService serviceForUserIfCallerHasPermission = getServiceForUserIfCallerHasPermission(identifier, "reportDelayedRestoreResult()");
        if (serviceForUserIfCallerHasPermission != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                serviceForUserIfCallerHasPermission.reportDelayedRestoreResult(str, list);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.backup.UserBackupManagerService getServiceForUserIfCallerHasPermission(int i, java.lang.String str) {
        enforceCallingPermissionOnUserId(i, str);
        com.android.server.backup.UserBackupManagerService userBackupManagerService = this.mUserServices.get(i);
        if (userBackupManagerService == null) {
            android.util.Slog.w(TAG, "Called " + str + " for unknown user: " + i);
        }
        return userBackupManagerService;
    }

    void enforceCallingPermissionOnUserId(int i, java.lang.String str) {
        if (android.os.Binder.getCallingUserHandle().getIdentifier() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", str);
        }
    }

    public static class Lifecycle extends com.android.server.SystemService {
        public Lifecycle(android.content.Context context) {
            this(context, new com.android.server.backup.BackupManagerService(context));
        }

        @com.android.internal.annotations.VisibleForTesting
        Lifecycle(android.content.Context context, com.android.server.backup.BackupManagerService backupManagerService) {
            super(context);
            com.android.server.backup.BackupManagerService.sInstance = backupManagerService;
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishService("backup", com.android.server.backup.BackupManagerService.sInstance);
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull final com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.backup.BackupManagerService.sInstance.postToHandler(new java.lang.Runnable() { // from class: com.android.server.backup.BackupManagerService$Lifecycle$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.backup.BackupManagerService.Lifecycle.lambda$onUserUnlocking$0(com.android.server.SystemService.TargetUser.this);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onUserUnlocking$0(com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.backup.BackupManagerService.sInstance.updateDefaultBackupUserIdIfNeeded();
            com.android.server.backup.BackupManagerService.sInstance.startServiceForUser(targetUser.getUserIdentifier());
            com.android.server.backup.BackupManagerService.sInstance.mHasFirstUserUnlockedSinceBoot = true;
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.backup.BackupManagerService.sInstance.onStopUser(targetUser.getUserIdentifier());
        }

        @com.android.internal.annotations.VisibleForTesting
        void publishService(java.lang.String str, android.os.IBinder iBinder) {
            publishBinderService(str, iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDefaultBackupUserIdIfNeeded() {
        android.os.UserHandle mainUser;
        if (!this.mHasFirstUserUnlockedSinceBoot && this.mDefaultBackupUserId == 0 && (mainUser = getUserManager().getMainUser()) != null && this.mDefaultBackupUserId != mainUser.getIdentifier()) {
            int i = this.mDefaultBackupUserId;
            this.mDefaultBackupUserId = mainUser.getIdentifier();
            if (!isBackupActivatedForUser(i)) {
                stopServiceForUser(i);
            }
            android.util.Slog.i(TAG, "Default backup user changed from " + i + " to " + this.mDefaultBackupUserId);
        }
    }
}
