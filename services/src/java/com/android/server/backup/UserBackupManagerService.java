package com.android.server.backup;

/* loaded from: classes.dex */
public class UserBackupManagerService {
    public static final java.lang.String BACKUP_FILE_HEADER_MAGIC = "ANDROID BACKUP\n";
    public static final int BACKUP_FILE_VERSION = 5;
    private static final java.lang.String BACKUP_FINISHED_ACTION = "android.intent.action.BACKUP_FINISHED";
    private static final java.lang.String BACKUP_FINISHED_PACKAGE_EXTRA = "packageName";
    public static final java.lang.String BACKUP_MANIFEST_FILENAME = "_manifest";
    public static final int BACKUP_MANIFEST_VERSION = 1;
    public static final java.lang.String BACKUP_METADATA_FILENAME = "_meta";
    public static final int BACKUP_METADATA_VERSION = 1;
    public static final int BACKUP_WIDGET_METADATA_TOKEN = 33549569;
    private static final long BIND_TIMEOUT_INTERVAL = 10000;
    private static final int BUSY_BACKOFF_FUZZ = 7200000;
    private static final long BUSY_BACKOFF_MIN_MILLIS = 3600000;
    private static final long CLEAR_DATA_TIMEOUT_INTERVAL = 30000;
    private static final int CURRENT_ANCESTRAL_RECORD_VERSION = 1;
    private static final long INITIALIZATION_DELAY_MILLIS = 3000;
    private static final java.lang.String INIT_SENTINEL_FILE_NAME = "_need_init_";
    public static final java.lang.String KEY_WIDGET_STATE = "￭￭widget";
    public static final java.lang.String PACKAGE_MANAGER_SENTINEL = "@pm@";
    public static final java.lang.String RUN_INITIALIZE_ACTION = "android.app.backup.intent.INIT";
    private static final int SCHEDULE_FILE_VERSION = 1;
    private static final java.lang.String SERIAL_ID_FILE = "serial_id";
    public static final java.lang.String SETTINGS_PACKAGE = "com.android.providers.settings";
    public static final java.lang.String SHARED_BACKUP_AGENT_PACKAGE = "com.android.sharedstoragebackup";
    private static final java.lang.String SKIP_USER_FACING_PACKAGES = "backup_skip_user_facing_packages";
    private static final long TIMEOUT_FULL_CONFIRMATION = 60000;
    private static final long TRANSPORT_RETRY_INTERVAL = 3600000;
    public static final java.lang.String WALLPAPER_PACKAGE = "com.android.wallpaperbackup";
    private com.android.server.backup.restore.ActiveRestoreSession mActiveRestoreSession;
    private final android.app.IActivityManager mActivityManager;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.util.SparseArray<com.android.server.backup.params.AdbParams> mAdbBackupRestoreConfirmations;
    private final java.lang.Object mAgentConnectLock;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private final android.app.AlarmManager mAlarmManager;
    private volatile long mAncestralBackupDestination;
    private java.util.Set<java.lang.String> mAncestralPackages;

    @android.annotation.Nullable
    private java.io.File mAncestralSerialNumberFile;
    private long mAncestralToken;
    private boolean mAutoRestore;
    private final com.android.server.backup.internal.BackupHandler mBackupHandler;
    private final android.app.backup.IBackupManager mBackupManagerBinder;
    private final android.util.SparseArray<java.util.HashSet<java.lang.String>> mBackupParticipants;
    private final com.android.server.backup.BackupPasswordManager mBackupPasswordManager;
    private final com.android.server.backup.UserBackupPreferences mBackupPreferences;
    private volatile boolean mBackupRunning;
    private final java.io.File mBaseStateDir;
    private final java.lang.Object mClearDataLock;
    private volatile boolean mClearingData;
    private android.app.IBackupAgent mConnectedAgent;
    private volatile boolean mConnecting;
    private final com.android.server.backup.BackupManagerConstants mConstants;
    private final android.content.Context mContext;
    private long mCurrentToken;
    private final java.io.File mDataDir;
    private boolean mEnabled;

    @com.android.internal.annotations.GuardedBy({"mQueueLock"})
    private java.util.ArrayList<com.android.server.backup.fullbackup.FullBackupEntry> mFullBackupQueue;
    private final java.io.File mFullBackupScheduleFile;
    private java.lang.Runnable mFullBackupScheduleWriter;

    @com.android.internal.annotations.GuardedBy({"mPendingRestores"})
    private boolean mIsRestoreInProgress;

    @android.annotation.Nullable
    private com.android.server.backup.DataChangedJournal mJournal;
    private final java.io.File mJournalDir;
    private volatile long mLastBackupPass;
    private final java.util.concurrent.atomic.AtomicInteger mNextToken;
    private final com.android.server.backup.internal.LifecycleOperationStorage mOperationStorage;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.pm.IPackageManager mPackageManagerBinder;
    private android.content.BroadcastReceiver mPackageTrackingReceiver;
    private final java.util.HashMap<java.lang.String, com.android.server.backup.keyvalue.BackupRequest> mPendingBackups;
    private final android.util.ArraySet<java.lang.String> mPendingInits;

    @com.android.internal.annotations.GuardedBy({"mPendingRestores"})
    private final java.util.Queue<com.android.server.backup.restore.PerformUnifiedRestoreTask> mPendingRestores;
    private android.os.PowerManager mPowerManager;
    private com.android.server.backup.ProcessedPackagesJournal mProcessedPackagesJournal;
    private final java.lang.Object mQueueLock;
    private final long mRegisterTransportsRequestedTime;
    private final java.security.SecureRandom mRng;
    private final android.app.PendingIntent mRunInitIntent;
    private final android.content.BroadcastReceiver mRunInitReceiver;

    @com.android.internal.annotations.GuardedBy({"mQueueLock"})
    private com.android.server.backup.fullbackup.PerformFullTransportBackupTask mRunningFullBackupTask;
    private final com.android.server.backup.utils.BackupEligibilityRules mScheduledBackupEligibility;
    private boolean mSetupComplete;
    private final android.database.ContentObserver mSetupObserver;
    private java.io.File mTokenFile;
    private final java.util.Random mTokenGenerator;
    private final com.android.server.backup.TransportManager mTransportManager;
    private final int mUserId;
    private final com.android.server.backup.UserBackupManagerService.BackupWakeLock mWakelock;

    public static class BackupWakeLock {
        private boolean mHasQuit = false;
        private final android.os.PowerManager.WakeLock mPowerManagerWakeLock;
        private int mUserId;

        public BackupWakeLock(android.os.PowerManager.WakeLock wakeLock, int i) {
            this.mPowerManagerWakeLock = wakeLock;
            this.mUserId = i;
        }

        public synchronized void acquire() {
            if (this.mHasQuit) {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Ignore wakelock acquire after quit: " + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            this.mPowerManagerWakeLock.acquire();
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Acquired wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }

        public synchronized void release() {
            if (this.mHasQuit) {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Ignore wakelock release after quit: " + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            this.mPowerManagerWakeLock.release();
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Released wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }

        public synchronized boolean isHeld() {
            return this.mPowerManagerWakeLock.isHeld();
        }

        public synchronized void quit() {
            while (this.mPowerManagerWakeLock.isHeld()) {
                try {
                    android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Releasing wakelock: " + this.mPowerManagerWakeLock.getTag()));
                    this.mPowerManagerWakeLock.release();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mHasQuit = true;
        }
    }

    static com.android.server.backup.UserBackupManagerService createAndInitializeService(int i, android.content.Context context, com.android.server.backup.BackupManagerService backupManagerService, java.util.Set<android.content.ComponentName> set) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), "backup_transport", i);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            stringForUser = null;
        }
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(i, "Starting with transport " + stringForUser));
        com.android.server.backup.TransportManager transportManager = new com.android.server.backup.TransportManager(i, context, set, stringForUser);
        java.io.File baseStateDir = com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(i);
        java.io.File dataDir = com.android.server.backup.UserBackupManagerFiles.getDataDir(i);
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("backup-" + i, 10);
        handlerThread.start();
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(i, "Started thread " + handlerThread.getName()));
        return createAndInitializeService(i, context, backupManagerService, handlerThread, baseStateDir, dataDir, transportManager);
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.backup.UserBackupManagerService createAndInitializeService(int i, android.content.Context context, com.android.server.backup.BackupManagerService backupManagerService, android.os.HandlerThread handlerThread, java.io.File file, java.io.File file2, com.android.server.backup.TransportManager transportManager) {
        if (new com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils().deleteExpiredBMMEvents()) {
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "BMM Events recorded for dumpsys have expired");
        }
        return new com.android.server.backup.UserBackupManagerService(i, context, backupManagerService, handlerThread, file, file2, transportManager);
    }

    public static boolean getSetupCompleteSettingForUser(android.content.Context context, int i) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, i) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    UserBackupManagerService(android.content.Context context, android.content.pm.PackageManager packageManager, com.android.server.backup.internal.LifecycleOperationStorage lifecycleOperationStorage, com.android.server.backup.TransportManager transportManager, com.android.server.backup.internal.BackupHandler backupHandler, com.android.server.backup.BackupManagerConstants backupManagerConstants) {
        this.mPendingInits = new android.util.ArraySet<>();
        this.mBackupParticipants = new android.util.SparseArray<>();
        this.mPendingBackups = new java.util.HashMap<>();
        this.mQueueLock = new java.lang.Object();
        this.mAgentConnectLock = new java.lang.Object();
        this.mClearDataLock = new java.lang.Object();
        this.mAdbBackupRestoreConfirmations = new android.util.SparseArray<>();
        this.mRng = new java.security.SecureRandom();
        this.mPendingRestores = new java.util.ArrayDeque();
        this.mTokenGenerator = new java.util.Random();
        this.mNextToken = new java.util.concurrent.atomic.AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mFullBackupScheduleWriter = new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.backup.UserBackupManagerService.this.mQueueLock) {
                    try {
                        try {
                            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(4096);
                            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
                            dataOutputStream.writeInt(1);
                            int size = com.android.server.backup.UserBackupManagerService.this.mFullBackupQueue.size();
                            dataOutputStream.writeInt(size);
                            for (int i = 0; i < size; i++) {
                                com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry = (com.android.server.backup.fullbackup.FullBackupEntry) com.android.server.backup.UserBackupManagerService.this.mFullBackupQueue.get(i);
                                dataOutputStream.writeUTF(fullBackupEntry.packageName);
                                dataOutputStream.writeLong(fullBackupEntry.lastBackup);
                            }
                            dataOutputStream.flush();
                            android.util.AtomicFile atomicFile = new android.util.AtomicFile(com.android.server.backup.UserBackupManagerService.this.mFullBackupScheduleFile);
                            java.io.FileOutputStream startWrite = atomicFile.startWrite();
                            startWrite.write(byteArrayOutputStream.toByteArray());
                            atomicFile.finishWrite(startWrite);
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(com.android.server.backup.UserBackupManagerService.this.mUserId, "Unable to write backup schedule!"), e);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mPackageTrackingReceiver = new com.android.server.backup.UserBackupManagerService.AnonymousClass2();
        this.mContext = context;
        this.mUserId = 0;
        this.mRegisterTransportsRequestedTime = 0L;
        this.mPackageManager = packageManager;
        this.mOperationStorage = lifecycleOperationStorage;
        this.mTransportManager = transportManager;
        this.mFullBackupQueue = new java.util.ArrayList<>();
        this.mBackupHandler = backupHandler;
        this.mConstants = backupManagerConstants;
        this.mBaseStateDir = null;
        this.mDataDir = null;
        this.mJournalDir = null;
        this.mFullBackupScheduleFile = null;
        this.mSetupObserver = null;
        this.mRunInitReceiver = null;
        this.mRunInitIntent = null;
        this.mAgentTimeoutParameters = null;
        this.mActivityManagerInternal = null;
        this.mAlarmManager = null;
        this.mWakelock = null;
        this.mBackupPreferences = null;
        this.mBackupPasswordManager = null;
        this.mPackageManagerBinder = null;
        this.mActivityManager = null;
        this.mBackupManagerBinder = null;
        this.mScheduledBackupEligibility = null;
    }

    private UserBackupManagerService(int i, android.content.Context context, com.android.server.backup.BackupManagerService backupManagerService, android.os.HandlerThread handlerThread, java.io.File file, java.io.File file2, com.android.server.backup.TransportManager transportManager) {
        this.mPendingInits = new android.util.ArraySet<>();
        this.mBackupParticipants = new android.util.SparseArray<>();
        this.mPendingBackups = new java.util.HashMap<>();
        this.mQueueLock = new java.lang.Object();
        this.mAgentConnectLock = new java.lang.Object();
        this.mClearDataLock = new java.lang.Object();
        this.mAdbBackupRestoreConfirmations = new android.util.SparseArray<>();
        this.mRng = new java.security.SecureRandom();
        this.mPendingRestores = new java.util.ArrayDeque();
        this.mTokenGenerator = new java.util.Random();
        this.mNextToken = new java.util.concurrent.atomic.AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mFullBackupScheduleWriter = new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.backup.UserBackupManagerService.this.mQueueLock) {
                    try {
                        try {
                            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(4096);
                            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
                            dataOutputStream.writeInt(1);
                            int size = com.android.server.backup.UserBackupManagerService.this.mFullBackupQueue.size();
                            dataOutputStream.writeInt(size);
                            for (int i2 = 0; i2 < size; i2++) {
                                com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry = (com.android.server.backup.fullbackup.FullBackupEntry) com.android.server.backup.UserBackupManagerService.this.mFullBackupQueue.get(i2);
                                dataOutputStream.writeUTF(fullBackupEntry.packageName);
                                dataOutputStream.writeLong(fullBackupEntry.lastBackup);
                            }
                            dataOutputStream.flush();
                            android.util.AtomicFile atomicFile = new android.util.AtomicFile(com.android.server.backup.UserBackupManagerService.this.mFullBackupScheduleFile);
                            java.io.FileOutputStream startWrite = atomicFile.startWrite();
                            startWrite.write(byteArrayOutputStream.toByteArray());
                            atomicFile.finishWrite(startWrite);
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(com.android.server.backup.UserBackupManagerService.this.mUserId, "Unable to write backup schedule!"), e);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mPackageTrackingReceiver = new com.android.server.backup.UserBackupManagerService.AnonymousClass2();
        this.mUserId = i;
        java.util.Objects.requireNonNull(context, "context cannot be null");
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mPackageManagerBinder = android.app.AppGlobals.getPackageManager();
        this.mActivityManager = android.app.ActivityManager.getService();
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mScheduledBackupEligibility = getEligibilityRules(this.mPackageManager, i, this.mContext, 0);
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        java.util.Objects.requireNonNull(backupManagerService, "parent cannot be null");
        this.mBackupManagerBinder = android.app.backup.IBackupManager.Stub.asInterface(backupManagerService.asBinder());
        this.mAgentTimeoutParameters = new com.android.server.backup.BackupAgentTimeoutParameters(android.os.Handler.getMain(), this.mContext.getContentResolver());
        this.mAgentTimeoutParameters.start();
        this.mOperationStorage = new com.android.server.backup.internal.LifecycleOperationStorage(this.mUserId);
        java.util.Objects.requireNonNull(handlerThread, "userBackupThread cannot be null");
        this.mBackupHandler = new com.android.server.backup.internal.BackupHandler(this, this.mOperationStorage, handlerThread);
        android.content.ContentResolver contentResolver = context.getContentResolver();
        this.mSetupComplete = getSetupCompleteSettingForUser(context, i);
        this.mAutoRestore = android.provider.Settings.Secure.getIntForUser(contentResolver, "backup_auto_restore", 1, i) != 0;
        this.mSetupObserver = new com.android.server.backup.internal.SetupObserver(this, this.mBackupHandler);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("user_setup_complete"), false, this.mSetupObserver, this.mUserId);
        java.util.Objects.requireNonNull(file, "baseStateDir cannot be null");
        this.mBaseStateDir = file;
        if (i == 0) {
            this.mBaseStateDir.mkdirs();
            if (!android.os.SELinux.restorecon(this.mBaseStateDir)) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(i, "SELinux restorecon failed on " + this.mBaseStateDir));
            }
        }
        java.util.Objects.requireNonNull(file2, "dataDir cannot be null");
        this.mDataDir = file2;
        this.mBackupPasswordManager = new com.android.server.backup.BackupPasswordManager(this.mContext, this.mBaseStateDir, this.mRng);
        this.mRunInitReceiver = new com.android.server.backup.internal.RunInitializeReceiver(this);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(RUN_INITIALIZE_ACTION);
        context.registerReceiverAsUser(this.mRunInitReceiver, android.os.UserHandle.of(i), intentFilter, "android.permission.BACKUP", null);
        android.content.Intent intent = new android.content.Intent(RUN_INITIALIZE_ACTION);
        intent.addFlags(1073741824);
        this.mRunInitIntent = android.app.PendingIntent.getBroadcastAsUser(context, 0, intent, 67108864, android.os.UserHandle.of(i));
        this.mJournalDir = new java.io.File(this.mBaseStateDir, "pending");
        this.mJournalDir.mkdirs();
        this.mJournal = null;
        this.mConstants = new com.android.server.backup.BackupManagerConstants(this.mBackupHandler, this.mContext.getContentResolver());
        this.mConstants.start();
        synchronized (this.mBackupParticipants) {
            addPackageParticipantsLocked(null);
        }
        java.util.Objects.requireNonNull(transportManager, "transportManager cannot be null");
        this.mTransportManager = transportManager;
        this.mTransportManager.setOnTransportRegisteredListener(new com.android.server.backup.transport.OnTransportRegisteredListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda1
            @Override // com.android.server.backup.transport.OnTransportRegisteredListener
            public final void onTransportRegistered(java.lang.String str, java.lang.String str2) {
                com.android.server.backup.UserBackupManagerService.this.onTransportRegistered(str, str2);
            }
        });
        this.mRegisterTransportsRequestedTime = android.os.SystemClock.elapsedRealtime();
        com.android.server.backup.internal.BackupHandler backupHandler = this.mBackupHandler;
        final com.android.server.backup.TransportManager transportManager2 = this.mTransportManager;
        java.util.Objects.requireNonNull(transportManager2);
        backupHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.backup.TransportManager.this.registerTransports();
            }
        }, 3000L);
        this.mBackupHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.backup.UserBackupManagerService.this.parseLeftoverJournals();
            }
        }, 3000L);
        final com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils = new com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils();
        com.android.server.backup.internal.BackupHandler backupHandler2 = this.mBackupHandler;
        java.util.Objects.requireNonNull(backupManagerMonitorDumpsysUtils);
        backupHandler2.postDelayed(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils.this.deleteExpiredBMMEvents();
            }
        }, 3000L);
        this.mBackupPreferences = new com.android.server.backup.UserBackupPreferences(this.mContext, this.mBaseStateDir);
        this.mWakelock = new com.android.server.backup.UserBackupManagerService.BackupWakeLock(this.mPowerManager.newWakeLock(1, "*backup*-" + i + "-" + handlerThread.getThreadId()), i);
        this.mFullBackupScheduleFile = new java.io.File(this.mBaseStateDir, "fb-schedule");
        initPackageTracking();
    }

    @com.android.internal.annotations.VisibleForTesting
    void initializeBackupEnableState() {
        setBackupEnabled(readEnabledState(), false);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void tearDownService() {
        this.mAgentTimeoutParameters.stop();
        this.mConstants.stop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSetupObserver);
        this.mContext.unregisterReceiver(this.mRunInitReceiver);
        this.mContext.unregisterReceiver(this.mPackageTrackingReceiver);
        this.mBackupHandler.stop();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public com.android.server.backup.BackupManagerConstants getConstants() {
        return this.mConstants;
    }

    public com.android.server.backup.BackupAgentTimeoutParameters getAgentTimeoutParameters() {
        return this.mAgentTimeoutParameters;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public android.content.pm.PackageManager getPackageManager() {
        return this.mPackageManager;
    }

    public android.content.pm.IPackageManager getPackageManagerBinder() {
        return this.mPackageManagerBinder;
    }

    public android.app.IActivityManager getActivityManager() {
        return this.mActivityManager;
    }

    public android.app.AlarmManager getAlarmManager() {
        return this.mAlarmManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPowerManager(android.os.PowerManager powerManager) {
        this.mPowerManager = powerManager;
    }

    public com.android.server.backup.TransportManager getTransportManager() {
        return this.mTransportManager;
    }

    public com.android.server.backup.OperationStorage getOperationStorage() {
        return this.mOperationStorage;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public boolean isSetupComplete() {
        return this.mSetupComplete;
    }

    public void setSetupComplete(boolean z) {
        this.mSetupComplete = z;
    }

    public com.android.server.backup.UserBackupManagerService.BackupWakeLock getWakelock() {
        return this.mWakelock;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setWorkSource(@android.annotation.Nullable android.os.WorkSource workSource) {
        this.mWakelock.mPowerManagerWakeLock.setWorkSource(workSource);
    }

    public android.os.Handler getBackupHandler() {
        return this.mBackupHandler;
    }

    public android.app.PendingIntent getRunInitIntent() {
        return this.mRunInitIntent;
    }

    public java.util.HashMap<java.lang.String, com.android.server.backup.keyvalue.BackupRequest> getPendingBackups() {
        return this.mPendingBackups;
    }

    public java.lang.Object getQueueLock() {
        return this.mQueueLock;
    }

    public boolean isBackupRunning() {
        return this.mBackupRunning;
    }

    public void setBackupRunning(boolean z) {
        this.mBackupRunning = z;
    }

    public void setLastBackupPass(long j) {
        this.mLastBackupPass = j;
    }

    public java.lang.Object getClearDataLock() {
        return this.mClearDataLock;
    }

    public void setClearingData(boolean z) {
        this.mClearingData = z;
    }

    public boolean isRestoreInProgress() {
        return this.mIsRestoreInProgress;
    }

    public void setRestoreInProgress(boolean z) {
        this.mIsRestoreInProgress = z;
    }

    public java.util.Queue<com.android.server.backup.restore.PerformUnifiedRestoreTask> getPendingRestores() {
        return this.mPendingRestores;
    }

    public com.android.server.backup.restore.ActiveRestoreSession getActiveRestoreSession() {
        return this.mActiveRestoreSession;
    }

    public android.util.SparseArray<com.android.server.backup.params.AdbParams> getAdbBackupRestoreConfirmations() {
        return this.mAdbBackupRestoreConfirmations;
    }

    public java.io.File getBaseStateDir() {
        return this.mBaseStateDir;
    }

    public java.io.File getDataDir() {
        return this.mDataDir;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.BroadcastReceiver getPackageTrackingReceiver() {
        return this.mPackageTrackingReceiver;
    }

    @android.annotation.Nullable
    public com.android.server.backup.DataChangedJournal getJournal() {
        return this.mJournal;
    }

    public void setJournal(@android.annotation.Nullable com.android.server.backup.DataChangedJournal dataChangedJournal) {
        this.mJournal = dataChangedJournal;
    }

    public java.security.SecureRandom getRng() {
        return this.mRng;
    }

    public void setAncestralPackages(java.util.Set<java.lang.String> set) {
        this.mAncestralPackages = set;
    }

    public void setAncestralToken(long j) {
        this.mAncestralToken = j;
    }

    public void setAncestralBackupDestination(int i) {
        this.mAncestralBackupDestination = i;
    }

    public long getCurrentToken() {
        return this.mCurrentToken;
    }

    public void setCurrentToken(long j) {
        this.mCurrentToken = j;
    }

    public android.util.ArraySet<java.lang.String> getPendingInits() {
        return this.mPendingInits;
    }

    public void clearPendingInits() {
        this.mPendingInits.clear();
    }

    public void setRunningFullBackupTask(com.android.server.backup.fullbackup.PerformFullTransportBackupTask performFullTransportBackupTask) {
        this.mRunningFullBackupTask = performFullTransportBackupTask;
    }

    public int generateRandomIntegerToken() {
        int nextInt = this.mTokenGenerator.nextInt();
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return (nextInt & (-256)) | (this.mNextToken.incrementAndGet() & 255);
    }

    public android.app.backup.BackupAgent makeMetadataAgent() {
        return makeMetadataAgentWithEligibilityRules(this.mScheduledBackupEligibility);
    }

    public android.app.backup.BackupAgent makeMetadataAgentWithEligibilityRules(com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        com.android.server.backup.PackageManagerBackupAgent packageManagerBackupAgent = new com.android.server.backup.PackageManagerBackupAgent(this.mPackageManager, this.mUserId, backupEligibilityRules);
        packageManagerBackupAgent.attach(this.mContext);
        packageManagerBackupAgent.onCreate(android.os.UserHandle.of(this.mUserId));
        return packageManagerBackupAgent;
    }

    public com.android.server.backup.PackageManagerBackupAgent makeMetadataAgent(java.util.List<android.content.pm.PackageInfo> list) {
        com.android.server.backup.PackageManagerBackupAgent packageManagerBackupAgent = new com.android.server.backup.PackageManagerBackupAgent(this.mPackageManager, list, this.mUserId);
        packageManagerBackupAgent.attach(this.mContext);
        packageManagerBackupAgent.onCreate(android.os.UserHandle.of(this.mUserId));
        return packageManagerBackupAgent;
    }

    private void initPackageTracking() {
        java.io.DataInputStream dataInputStream;
        this.mTokenFile = new java.io.File(this.mBaseStateDir, "ancestral");
        try {
            dataInputStream = new java.io.DataInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(this.mTokenFile)));
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "No ancestral data"));
        } catch (java.io.IOException e2) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to read token file"), e2);
        }
        try {
            if (dataInputStream.readInt() == 1) {
                this.mAncestralToken = dataInputStream.readLong();
                this.mCurrentToken = dataInputStream.readLong();
                int readInt = dataInputStream.readInt();
                if (readInt >= 0) {
                    this.mAncestralPackages = new java.util.HashSet();
                    for (int i = 0; i < readInt; i++) {
                        this.mAncestralPackages.add(dataInputStream.readUTF());
                    }
                }
            }
            dataInputStream.close();
            this.mProcessedPackagesJournal = new com.android.server.backup.ProcessedPackagesJournal(this.mBaseStateDir);
            this.mProcessedPackagesJournal.init();
            synchronized (this.mQueueLock) {
                this.mFullBackupQueue = readFullBackupSchedule();
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            this.mContext.registerReceiverAsUser(this.mPackageTrackingReceiver, android.os.UserHandle.of(this.mUserId), intentFilter, null, null);
            android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            this.mContext.registerReceiverAsUser(this.mPackageTrackingReceiver, android.os.UserHandle.of(this.mUserId), intentFilter2, null, null);
        } catch (java.lang.Throwable th) {
            try {
                dataInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0172  */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.util.ArrayList<com.android.server.backup.fullbackup.FullBackupEntry> readFullBackupSchedule() {
        boolean z;
        java.util.ArrayList<com.android.server.backup.fullbackup.FullBackupEntry> arrayList;
        java.lang.Throwable th;
        java.lang.Throwable th2;
        java.lang.Throwable th3;
        int readInt;
        int i;
        boolean z2;
        java.util.List<android.content.pm.PackageInfo> storableApplications = com.android.server.backup.PackageManagerBackupAgent.getStorableApplications(this.mPackageManager, this.mUserId, this.mScheduledBackupEligibility);
        if (this.mFullBackupScheduleFile.exists()) {
            try {
                try {
                    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(this.mFullBackupScheduleFile);
                    try {
                        try {
                            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(fileInputStream);
                            try {
                                try {
                                    java.io.DataInputStream dataInputStream = new java.io.DataInputStream(bufferedInputStream);
                                    try {
                                        readInt = dataInputStream.readInt();
                                    } catch (java.lang.Throwable th4) {
                                        th = th4;
                                    }
                                    try {
                                        if (readInt != 1) {
                                            throw new java.lang.IllegalArgumentException("Unknown backup schedule version " + readInt);
                                        }
                                        int readInt2 = dataInputStream.readInt();
                                        java.util.ArrayList<com.android.server.backup.fullbackup.FullBackupEntry> arrayList2 = new java.util.ArrayList<>(readInt2);
                                        java.util.HashSet hashSet = new java.util.HashSet(readInt2);
                                        int i2 = 0;
                                        while (i2 < readInt2) {
                                            java.lang.String readUTF = dataInputStream.readUTF();
                                            long readLong = dataInputStream.readLong();
                                            hashSet.add(readUTF);
                                            try {
                                                i = readInt2;
                                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                                i = readInt2;
                                            }
                                            try {
                                                android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(readUTF, 0, this.mUserId);
                                                if (this.mScheduledBackupEligibility.appGetsFullBackup(packageInfoAsUser) && this.mScheduledBackupEligibility.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                                                    arrayList2.add(new com.android.server.backup.fullbackup.FullBackupEntry(readUTF, readLong));
                                                } else {
                                                    android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Package " + readUTF + " no longer eligible for full backup"));
                                                }
                                            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Package " + readUTF + " not installed; dropping from full backup"));
                                                i2++;
                                                readInt2 = i;
                                            }
                                            i2++;
                                            readInt2 = i;
                                        }
                                        z = false;
                                        for (android.content.pm.PackageInfo packageInfo : storableApplications) {
                                            try {
                                                if (this.mScheduledBackupEligibility.appGetsFullBackup(packageInfo) && this.mScheduledBackupEligibility.appIsEligibleForBackup(packageInfo.applicationInfo) && !hashSet.contains(packageInfo.packageName)) {
                                                    arrayList2.add(new com.android.server.backup.fullbackup.FullBackupEntry(packageInfo.packageName, 0L));
                                                    z = true;
                                                }
                                            } catch (java.lang.Throwable th5) {
                                                th3 = th5;
                                                try {
                                                    dataInputStream.close();
                                                    throw th3;
                                                } catch (java.lang.Throwable th6) {
                                                    th3.addSuppressed(th6);
                                                    throw th3;
                                                }
                                            }
                                        }
                                        java.util.Collections.sort(arrayList2);
                                        dataInputStream.close();
                                        bufferedInputStream.close();
                                        fileInputStream.close();
                                        arrayList = arrayList2;
                                    } catch (java.lang.Throwable th7) {
                                        th = th7;
                                        th3 = th;
                                        z = false;
                                        dataInputStream.close();
                                        throw th3;
                                    }
                                } catch (java.lang.Throwable th8) {
                                    th2 = th8;
                                    z = false;
                                    try {
                                        bufferedInputStream.close();
                                        throw th2;
                                    } catch (java.lang.Throwable th9) {
                                        th2.addSuppressed(th9);
                                        throw th2;
                                    }
                                }
                            } catch (java.lang.Throwable th10) {
                                th2 = th10;
                                bufferedInputStream.close();
                                throw th2;
                            }
                        } catch (java.lang.Throwable th11) {
                            th = th11;
                            try {
                                fileInputStream.close();
                                throw th;
                            } catch (java.lang.Throwable th12) {
                                th.addSuppressed(th12);
                                throw th;
                            }
                        }
                    } catch (java.lang.Throwable th13) {
                        th = th13;
                        z = false;
                        fileInputStream.close();
                        throw th;
                    }
                } catch (java.lang.Exception e3) {
                    e = e3;
                    z = false;
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to read backup schedule"), e);
                    this.mFullBackupScheduleFile.delete();
                    arrayList = null;
                    if (arrayList == null) {
                    }
                    if (z2) {
                    }
                    return arrayList;
                }
            } catch (java.lang.Exception e4) {
                e = e4;
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to read backup schedule"), e);
                this.mFullBackupScheduleFile.delete();
                arrayList = null;
                if (arrayList == null) {
                }
                if (z2) {
                }
                return arrayList;
            }
        } else {
            z = false;
            arrayList = null;
        }
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>(storableApplications.size());
            for (android.content.pm.PackageInfo packageInfo2 : storableApplications) {
                if (this.mScheduledBackupEligibility.appGetsFullBackup(packageInfo2) && this.mScheduledBackupEligibility.appIsEligibleForBackup(packageInfo2.applicationInfo)) {
                    arrayList.add(new com.android.server.backup.fullbackup.FullBackupEntry(packageInfo2.packageName, 0L));
                }
            }
            z2 = true;
        } else {
            z2 = z;
        }
        if (z2) {
            writeFullBackupScheduleAsync();
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeFullBackupScheduleAsync() {
        this.mBackupHandler.removeCallbacks(this.mFullBackupScheduleWriter);
        this.mBackupHandler.post(this.mFullBackupScheduleWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseLeftoverJournals() {
        java.util.ArrayList<com.android.server.backup.DataChangedJournal> listJournals = com.android.server.backup.DataChangedJournal.listJournals(this.mJournalDir);
        listJournals.removeAll(java.util.Collections.singletonList(this.mJournal));
        if (!listJournals.isEmpty()) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Found " + listJournals.size() + " stale backup journal(s), scheduling."));
        }
        final java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        java.util.Iterator<com.android.server.backup.DataChangedJournal> it = listJournals.iterator();
        while (it.hasNext()) {
            com.android.server.backup.DataChangedJournal next = it.next();
            try {
                next.forEach(new java.util.function.Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.backup.UserBackupManagerService.this.lambda$parseLeftoverJournals$0(linkedHashSet, (java.lang.String) obj);
                    }
                });
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Can't read " + next), e);
            }
        }
        if (!linkedHashSet.isEmpty()) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Stale backup journals: Scheduled " + linkedHashSet.size() + " package(s) total"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$parseLeftoverJournals$0(java.util.Set set, java.lang.String str) {
        if (set.add(str)) {
            dataChangedImpl(str);
        }
    }

    public java.util.Set<java.lang.String> getExcludedRestoreKeys(java.lang.String str) {
        return this.mBackupPreferences.getExcludedRestoreKeysForPackage(str);
    }

    public byte[] randomBytes(int i) {
        byte[] bArr = new byte[i / 8];
        this.mRng.nextBytes(bArr);
        return bArr;
    }

    public boolean setBackupPassword(java.lang.String str, java.lang.String str2) {
        return this.mBackupPasswordManager.setBackupPassword(str, str2);
    }

    public boolean hasBackupPassword() {
        return this.mBackupPasswordManager.hasBackupPassword();
    }

    public boolean backupPasswordMatches(java.lang.String str) {
        return this.mBackupPasswordManager.backupPasswordMatches(str);
    }

    public void recordInitPending(boolean z, java.lang.String str, java.lang.String str2) {
        synchronized (this.mQueueLock) {
            java.io.File file = new java.io.File(new java.io.File(this.mBaseStateDir, str2), INIT_SENTINEL_FILE_NAME);
            if (z) {
                this.mPendingInits.add(str);
                try {
                    new java.io.FileOutputStream(file).close();
                } catch (java.io.IOException e) {
                }
            } else {
                file.delete();
                this.mPendingInits.remove(str);
            }
        }
    }

    public void resetBackupState(java.io.File file) {
        int i;
        synchronized (this.mQueueLock) {
            try {
                this.mProcessedPackagesJournal.reset();
                this.mCurrentToken = 0L;
                writeRestoreTokens();
                for (java.io.File file2 : file.listFiles()) {
                    if (!file2.getName().equals(INIT_SENTINEL_FILE_NAME)) {
                        file2.delete();
                    }
                }
            } finally {
            }
        }
        synchronized (this.mBackupParticipants) {
            try {
                int size = this.mBackupParticipants.size();
                for (i = 0; i < size; i++) {
                    java.util.HashSet<java.lang.String> valueAt = this.mBackupParticipants.valueAt(i);
                    if (valueAt != null) {
                        java.util.Iterator<java.lang.String> it = valueAt.iterator();
                        while (it.hasNext()) {
                            dataChangedImpl(it.next());
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransportRegistered(java.lang.String str, java.lang.String str2) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mRegisterTransportsRequestedTime;
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " registered " + elapsedRealtime + "ms after first request (delay = 3000ms)"));
        java.io.File file = new java.io.File(this.mBaseStateDir, str2);
        file.mkdirs();
        if (new java.io.File(file, INIT_SENTINEL_FILE_NAME).exists()) {
            synchronized (this.mQueueLock) {
                this.mPendingInits.add(str);
                this.mAlarmManager.set(0, java.lang.System.currentTimeMillis() + 60000, this.mRunInitIntent);
            }
        }
    }

    /* renamed from: com.android.server.backup.UserBackupManagerService$2, reason: invalid class name */
    class AnonymousClass2 extends android.content.BroadcastReceiver {
        AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z;
            java.lang.String action = intent.getAction();
            android.os.Bundle extras = intent.getExtras();
            boolean z2 = true;
            if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
                android.net.Uri data = intent.getData();
                if (data == null) {
                    return;
                }
                final java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
                r4 = schemeSpecificPart != null ? new java.lang.String[]{schemeSpecificPart} : null;
                if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                    final java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                    com.android.server.backup.UserBackupManagerService.this.mBackupHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.backup.UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$0(schemeSpecificPart, stringArrayExtra);
                        }
                    });
                    return;
                } else {
                    z2 = "android.intent.action.PACKAGE_ADDED".equals(action);
                    z = extras.getBoolean("android.intent.extra.REPLACING", false);
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                r4 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z = false;
            } else if (!"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                z = false;
                z2 = false;
            } else {
                r4 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z = false;
                z2 = false;
            }
            if (r4 == null || r4.length == 0) {
                return;
            }
            int i = extras.getInt("android.intent.extra.UID");
            if (z2) {
                synchronized (com.android.server.backup.UserBackupManagerService.this.mBackupParticipants) {
                    if (z) {
                        try {
                            com.android.server.backup.UserBackupManagerService.this.removePackageParticipantsLocked(r4, i);
                        } finally {
                        }
                    }
                    com.android.server.backup.UserBackupManagerService.this.addPackageParticipantsLocked(r4);
                }
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                for (final java.lang.String str : r4) {
                    try {
                        android.content.pm.PackageInfo packageInfoAsUser = com.android.server.backup.UserBackupManagerService.this.mPackageManager.getPackageInfoAsUser(str, 0, com.android.server.backup.UserBackupManagerService.this.mUserId);
                        if (com.android.server.backup.UserBackupManagerService.this.mScheduledBackupEligibility.appGetsFullBackup(packageInfoAsUser) && com.android.server.backup.UserBackupManagerService.this.mScheduledBackupEligibility.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                            com.android.server.backup.UserBackupManagerService.this.enqueueFullBackup(str, currentTimeMillis);
                            com.android.server.backup.UserBackupManagerService.this.scheduleNextFullBackupJob(0L);
                        } else {
                            synchronized (com.android.server.backup.UserBackupManagerService.this.mQueueLock) {
                                com.android.server.backup.UserBackupManagerService.this.dequeueFullBackupLocked(str);
                            }
                            com.android.server.backup.UserBackupManagerService.this.writeFullBackupScheduleAsync();
                        }
                        com.android.server.backup.UserBackupManagerService.this.mBackupHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.backup.UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$1(str);
                            }
                        });
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(com.android.server.backup.UserBackupManagerService.this.mUserId, "Can't resolve new app " + str));
                    }
                }
                com.android.server.backup.UserBackupManagerService.this.dataChangedImpl(com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL);
                return;
            }
            if (!z) {
                synchronized (com.android.server.backup.UserBackupManagerService.this.mBackupParticipants) {
                    com.android.server.backup.UserBackupManagerService.this.removePackageParticipantsLocked(r4, i);
                }
            }
            for (final java.lang.String str2 : r4) {
                com.android.server.backup.UserBackupManagerService.this.mBackupHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.backup.UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$2(str2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(java.lang.String str, java.lang.String[] strArr) {
            com.android.server.backup.UserBackupManagerService.this.mTransportManager.onPackageChanged(str, strArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(java.lang.String str) {
            com.android.server.backup.UserBackupManagerService.this.mTransportManager.onPackageAdded(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2(java.lang.String str) {
            com.android.server.backup.UserBackupManagerService.this.mTransportManager.onPackageRemoved(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addPackageParticipantsLocked(java.lang.String[] strArr) {
        java.util.List<android.content.pm.PackageInfo> allAgentPackages = allAgentPackages();
        if (strArr != null) {
            for (java.lang.String str : strArr) {
                addPackageParticipantsLockedInner(str, allAgentPackages);
            }
            return;
        }
        addPackageParticipantsLockedInner(null, allAgentPackages);
    }

    private void addPackageParticipantsLockedInner(java.lang.String str, java.util.List<android.content.pm.PackageInfo> list) {
        for (android.content.pm.PackageInfo packageInfo : list) {
            if (str == null || packageInfo.packageName.equals(str)) {
                int i = packageInfo.applicationInfo.uid;
                java.util.HashSet<java.lang.String> hashSet = this.mBackupParticipants.get(i);
                if (hashSet == null) {
                    hashSet = new java.util.HashSet<>();
                    this.mBackupParticipants.put(i, hashSet);
                }
                hashSet.add(packageInfo.packageName);
                this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(16, packageInfo.packageName));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removePackageParticipantsLocked(java.lang.String[] strArr, int i) {
        if (strArr == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "removePackageParticipants with null list"));
            return;
        }
        for (java.lang.String str : strArr) {
            java.util.HashSet<java.lang.String> hashSet = this.mBackupParticipants.get(i);
            if (hashSet != null && hashSet.contains(str)) {
                removePackageFromSetLocked(hashSet, str);
                if (hashSet.isEmpty()) {
                    this.mBackupParticipants.remove(i);
                }
            }
        }
    }

    private void removePackageFromSetLocked(java.util.HashSet<java.lang.String> hashSet, java.lang.String str) {
        if (hashSet.contains(str)) {
            hashSet.remove(str);
            this.mPendingBackups.remove(str);
        }
    }

    private java.util.List<android.content.pm.PackageInfo> allAgentPackages() {
        java.util.List<android.content.pm.PackageInfo> installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(134217728, this.mUserId);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            android.content.pm.PackageInfo packageInfo = installedPackagesAsUser.get(size);
            try {
                android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if ((applicationInfo.flags & 32768) == 0 || applicationInfo.backupAgentName == null || (applicationInfo.flags & 67108864) != 0) {
                    installedPackagesAsUser.remove(size);
                } else {
                    android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(packageInfo.packageName, 1024, this.mUserId);
                    packageInfo.applicationInfo.sharedLibraryFiles = applicationInfoAsUser.sharedLibraryFiles;
                    packageInfo.applicationInfo.sharedLibraryInfos = applicationInfoAsUser.sharedLibraryInfos;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                installedPackagesAsUser.remove(size);
            }
        }
        return installedPackagesAsUser;
    }

    public void logBackupComplete(java.lang.String str) {
        if (str.equals(PACKAGE_MANAGER_SENTINEL)) {
            return;
        }
        for (java.lang.String str2 : this.mConstants.getBackupFinishedNotificationReceivers()) {
            android.content.Intent intent = new android.content.Intent();
            intent.setAction(BACKUP_FINISHED_ACTION);
            intent.setPackage(str2);
            intent.addFlags(268435488);
            intent.putExtra("packageName", str);
            this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.of(this.mUserId));
        }
        this.mProcessedPackagesJournal.addPackage(str);
    }

    public void writeRestoreTokens() {
        try {
            java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(this.mTokenFile, "rwd");
            try {
                randomAccessFile.writeInt(1);
                randomAccessFile.writeLong(this.mAncestralToken);
                randomAccessFile.writeLong(this.mCurrentToken);
                if (this.mAncestralPackages == null) {
                    randomAccessFile.writeInt(-1);
                } else {
                    randomAccessFile.writeInt(this.mAncestralPackages.size());
                    android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Ancestral packages:  " + this.mAncestralPackages.size()));
                    java.util.Iterator<java.lang.String> it = this.mAncestralPackages.iterator();
                    while (it.hasNext()) {
                        randomAccessFile.writeUTF(it.next());
                    }
                }
                randomAccessFile.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to write token file:"), e);
        }
    }

    @android.annotation.Nullable
    public android.app.IBackupAgent bindToAgentSynchronous(android.content.pm.ApplicationInfo applicationInfo, int i, int i2) {
        android.app.IBackupAgent iBackupAgent;
        synchronized (this.mAgentConnectLock) {
            this.mConnecting = true;
            iBackupAgent = null;
            this.mConnectedAgent = null;
            try {
                if (this.mActivityManager.bindBackupAgent(applicationInfo.packageName, i, this.mUserId, i2)) {
                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "awaiting agent for " + applicationInfo));
                    long currentTimeMillis = java.lang.System.currentTimeMillis() + 10000;
                    while (this.mConnecting && this.mConnectedAgent == null && java.lang.System.currentTimeMillis() < currentTimeMillis) {
                        try {
                            this.mAgentConnectLock.wait(5000L);
                        } catch (java.lang.InterruptedException e) {
                            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Interrupted: " + e));
                            this.mConnecting = false;
                            this.mConnectedAgent = null;
                        }
                    }
                    if (this.mConnecting) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Timeout waiting for agent " + applicationInfo));
                        this.mConnectedAgent = null;
                    }
                    android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "got agent " + this.mConnectedAgent));
                    iBackupAgent = this.mConnectedAgent;
                }
            } catch (android.os.RemoteException e2) {
            }
        }
        if (iBackupAgent == null) {
            this.mActivityManagerInternal.clearPendingBackup(this.mUserId);
        }
        return iBackupAgent;
    }

    public void unbindAgent(android.content.pm.ApplicationInfo applicationInfo) {
        try {
            this.mActivityManager.unbindBackupAgent(applicationInfo);
        } catch (android.os.RemoteException e) {
        }
    }

    public void clearApplicationDataAfterRestoreFailure(java.lang.String str) {
        clearApplicationDataSynchronous(str, true, false);
    }

    public void clearApplicationDataBeforeRestore(java.lang.String str) {
        clearApplicationDataSynchronous(str, false, true);
    }

    private void clearApplicationDataSynchronous(java.lang.String str, boolean z, boolean z2) {
        boolean z3;
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId).applicationInfo;
            if (z && applicationInfo.targetSdkVersion >= 29) {
                z3 = (applicationInfo.privateFlags & 67108864) != 0;
            } else {
                z3 = (applicationInfo.flags & 64) != 0;
            }
            if (!z3) {
                return;
            }
            com.android.server.backup.internal.ClearDataObserver clearDataObserver = new com.android.server.backup.internal.ClearDataObserver(this);
            synchronized (this.mClearDataLock) {
                this.mClearingData = true;
                this.mActivityManagerInternal.clearApplicationUserData(str, z2, true, clearDataObserver, this.mUserId);
                long currentTimeMillis = java.lang.System.currentTimeMillis() + 30000;
                while (this.mClearingData && java.lang.System.currentTimeMillis() < currentTimeMillis) {
                    try {
                        this.mClearDataLock.wait(5000L);
                    } catch (java.lang.InterruptedException e) {
                        this.mClearingData = false;
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Interrupted while waiting for " + str + " data to be cleared"), e);
                    }
                }
                if (this.mClearingData) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Clearing app data for " + str + " timed out"));
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Tried to clear data for " + str + " but not found"));
        }
    }

    private com.android.server.backup.utils.BackupEligibilityRules getEligibilityRulesForRestoreAtInstall(long j) {
        if (this.mAncestralBackupDestination == 1 && j == this.mAncestralToken) {
            return getEligibilityRulesForOperation(1);
        }
        return this.mScheduledBackupEligibility;
    }

    public long getAvailableRestoreToken(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getAvailableRestoreToken");
        long j = this.mAncestralToken;
        synchronized (this.mQueueLock) {
            try {
                if (this.mCurrentToken != 0 && this.mProcessedPackagesJournal.hasBeenProcessed(str)) {
                    j = this.mCurrentToken;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return j;
    }

    public int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, int i) {
        return requestBackup(strArr, iBackupObserver, null, i);
    }

    public int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i) {
        int i2;
        com.android.server.backup.utils.BackupManagerMonitorEventSender bMMEventSender = getBMMEventSender(iBackupManagerMonitor);
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "requestBackup");
        if (strArr == null || strArr.length < 1) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "No packages named for backup request"));
            com.android.server.backup.utils.BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            bMMEventSender.monitorEvent(49, null, 1, null);
            throw new java.lang.IllegalArgumentException("No packages are provided for backup");
        }
        if (!this.mEnabled || !this.mSetupComplete) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup requested but enabled=" + this.mEnabled + " setupComplete=" + this.mSetupComplete));
            com.android.server.backup.utils.BackupObserverUtils.sendBackupFinished(iBackupObserver, -2001);
            if (this.mSetupComplete) {
                i2 = 13;
            } else {
                i2 = 14;
            }
            bMMEventSender.monitorEvent(i2, null, 3, null);
            return -2001;
        }
        try {
            java.lang.String transportDirName = this.mTransportManager.getTransportDirName(this.mTransportManager.getCurrentTransportName());
            final com.android.server.backup.transport.TransportConnection currentTransportClientOrThrow = this.mTransportManager.getCurrentTransportClientOrThrow("BMS.requestBackup()");
            int backupDestinationFromTransport = getBackupDestinationFromTransport(currentTransportClientOrThrow);
            com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener = new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda5
                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                public final void onFinished(java.lang.String str) {
                    com.android.server.backup.UserBackupManagerService.this.lambda$requestBackup$1(currentTransportClientOrThrow, str);
                }
            };
            com.android.server.backup.utils.BackupEligibilityRules eligibilityRulesForOperation = getEligibilityRulesForOperation(backupDestinationFromTransport);
            android.os.Message obtainMessage = this.mBackupHandler.obtainMessage(15);
            obtainMessage.obj = getRequestBackupParams(strArr, iBackupObserver, iBackupManagerMonitor, i, eligibilityRulesForOperation, currentTransportClientOrThrow, transportDirName, onTaskFinishedListener);
            this.mBackupHandler.sendMessage(obtainMessage);
            return 0;
        } catch (android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException | com.android.server.backup.transport.TransportNotRegisteredException e) {
            com.android.server.backup.utils.BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            bMMEventSender.monitorEvent(50, null, 1, null);
            return -1000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestBackup$1(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.backup.params.BackupParams getRequestBackupParams(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules, com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (java.lang.String str2 : strArr) {
            if (!PACKAGE_MANAGER_SENTINEL.equals(str2)) {
                try {
                    android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str2, 134217728, this.mUserId);
                    if (!backupEligibilityRules.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2001);
                    } else if (backupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                        arrayList.add(packageInfoAsUser.packageName);
                    } else {
                        arrayList2.add(packageInfoAsUser.packageName);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2002);
                }
            } else {
                arrayList2.add(str2);
            }
        }
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_REQUESTED, java.lang.Integer.valueOf(strArr.length), java.lang.Integer.valueOf(arrayList2.size()), java.lang.Integer.valueOf(arrayList.size()));
        return new com.android.server.backup.params.BackupParams(transportConnection, str, arrayList2, arrayList, iBackupObserver, iBackupManagerMonitor, onTaskFinishedListener, true, (i & 1) != 0, backupEligibilityRules);
    }

    public void cancelBackups() {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "cancelBackups");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Iterator<java.lang.Integer> it = this.mOperationStorage.operationTokensForOpType(2).iterator();
            while (it.hasNext()) {
                this.mOperationStorage.cancelOperation(it.next().intValue(), true, new java.util.function.IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda15
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        com.android.server.backup.UserBackupManagerService.lambda$cancelBackups$2(i);
                    }
                });
            }
            com.android.server.backup.KeyValueBackupJob.schedule(this.mUserId, this.mContext, 3600000L, this);
            com.android.server.backup.FullBackupJob.schedule(this.mUserId, this.mContext, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT, this);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cancelBackups$2(int i) {
    }

    public void prepareOperationTimeout(int i, long j, com.android.server.backup.BackupRestoreTask backupRestoreTask, int i2) {
        if (i2 != 0 && i2 != 1) {
            android.util.Slog.wtf(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "prepareOperationTimeout() doesn't support operation " + java.lang.Integer.toHexString(i) + " of type " + i2));
            return;
        }
        this.mOperationStorage.registerOperation(i, 0, backupRestoreTask, i2);
        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(getMessageIdForOperationType(i2), i, 0, backupRestoreTask), j);
    }

    private int getMessageIdForOperationType(int i) {
        switch (i) {
            case 0:
                return 17;
            case 1:
                return 18;
            default:
                android.util.Slog.wtf(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "getMessageIdForOperationType called on invalid operation type: " + i));
                return -1;
        }
    }

    public boolean waitUntilOperationComplete(int i) {
        return this.mOperationStorage.waitUntilOperationComplete(i, new java.util.function.IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda13
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                com.android.server.backup.UserBackupManagerService.this.lambda$waitUntilOperationComplete$3(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitUntilOperationComplete$3(int i) {
        this.mBackupHandler.removeMessages(getMessageIdForOperationType(i));
    }

    public void handleCancel(int i, boolean z) {
        this.mOperationStorage.cancelOperation(i, z, new java.util.function.IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                com.android.server.backup.UserBackupManagerService.this.lambda$handleCancel$4(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleCancel$4(int i) {
        if (i == 0 || i == 1) {
            this.mBackupHandler.removeMessages(getMessageIdForOperationType(i));
        }
    }

    public boolean isBackupOperationInProgress() {
        return this.mOperationStorage.isBackupOperationInProgress();
    }

    public void tearDownAgentAndKill(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return;
        }
        try {
            this.mActivityManager.unbindBackupAgent(applicationInfo);
            if (!android.os.UserHandle.isCore(applicationInfo.uid) && !applicationInfo.packageName.equals("com.android.backupconfirm")) {
                this.mActivityManager.killApplicationProcess(applicationInfo.processName, applicationInfo.uid);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Lost app trying to shut down"));
        }
    }

    public void scheduleNextFullBackupJob(long j) {
        synchronized (this.mQueueLock) {
            try {
                if (this.mFullBackupQueue.size() > 0) {
                    long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mFullBackupQueue.get(0).lastBackup;
                    long fullBackupIntervalMilliseconds = this.mConstants.getFullBackupIntervalMilliseconds();
                    com.android.server.backup.FullBackupJob.schedule(this.mUserId, this.mContext, java.lang.Math.max(j, currentTimeMillis < fullBackupIntervalMilliseconds ? fullBackupIntervalMilliseconds - currentTimeMillis : 0L), this);
                } else {
                    android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Full backup queue empty; not scheduling"));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mQueueLock"})
    public void dequeueFullBackupLocked(java.lang.String str) {
        for (int size = this.mFullBackupQueue.size() - 1; size >= 0; size--) {
            if (str.equals(this.mFullBackupQueue.get(size).packageName)) {
                this.mFullBackupQueue.remove(size);
            }
        }
    }

    public void enqueueFullBackup(java.lang.String str, long j) {
        int i;
        com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry = new com.android.server.backup.fullbackup.FullBackupEntry(str, j);
        synchronized (this.mQueueLock) {
            try {
                dequeueFullBackupLocked(str);
                if (j <= 0) {
                    i = -1;
                } else {
                    i = this.mFullBackupQueue.size() - 1;
                    while (true) {
                        if (i < 0) {
                            break;
                        }
                        if (this.mFullBackupQueue.get(i).lastBackup > j) {
                            i--;
                        } else {
                            this.mFullBackupQueue.add(i + 1, fullBackupEntry);
                            break;
                        }
                    }
                }
                if (i < 0) {
                    this.mFullBackupQueue.add(0, fullBackupEntry);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        writeFullBackupScheduleAsync();
    }

    private boolean fullBackupAllowable(java.lang.String str) {
        if (!this.mTransportManager.isTransportRegistered(str)) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Transport not registered; full data backup not performed"));
            return false;
        }
        try {
            if (new java.io.File(new java.io.File(this.mBaseStateDir, this.mTransportManager.getTransportDirName(str)), PACKAGE_MANAGER_SENTINEL).length() <= 0) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Full backup requested but dataset not yet initialized"));
                return false;
            }
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to get transport name: " + e.getMessage()));
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0216 A[LOOP:0: B:25:0x006e->B:49:0x0216, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0187 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01d8 A[Catch: all -> 0x01bd, TryCatch #9 {all -> 0x01bd, blocks: (B:58:0x01b6, B:60:0x01d8, B:61:0x01fc, B:63:0x01ff, B:64:0x0214, B:112:0x0221, B:68:0x01c8), top: B:17:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01ff A[Catch: all -> 0x01bd, TryCatch #9 {all -> 0x01bd, blocks: (B:58:0x01b6, B:60:0x01d8, B:61:0x01fc, B:63:0x01ff, B:64:0x0214, B:112:0x0221, B:68:0x01c8), top: B:17:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0179  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean beginFullBackup(com.android.server.backup.FullBackupJob fullBackupJob) {
        long fullBackupIntervalMilliseconds;
        long keyValueBackupIntervalMilliseconds;
        com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry;
        long j;
        int i;
        java.lang.Object obj;
        boolean z;
        long j2;
        long j3;
        long j4;
        int i2;
        int i3;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        synchronized (this.mConstants) {
            fullBackupIntervalMilliseconds = this.mConstants.getFullBackupIntervalMilliseconds();
            keyValueBackupIntervalMilliseconds = this.mConstants.getKeyValueBackupIntervalMilliseconds();
        }
        int i4 = 0;
        if (!this.mEnabled || !this.mSetupComplete) {
            return false;
        }
        if (this.mPowerManager.getPowerSaveState(4).batterySaverEnabled) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Deferring scheduled full backups in battery saver mode"));
            com.android.server.backup.FullBackupJob.schedule(this.mUserId, this.mContext, keyValueBackupIntervalMilliseconds, this);
            return false;
        }
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Beginning scheduled full backup operation"));
        java.lang.Object obj2 = this.mQueueLock;
        synchronized (obj2) {
            try {
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                if (this.mRunningFullBackupTask != null) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup triggered but one already/still running!"));
                    return false;
                }
                com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry2 = null;
                long j5 = fullBackupIntervalMilliseconds;
                int i5 = 1;
                while (true) {
                    if (this.mFullBackupQueue.size() == 0) {
                        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup queue empty; doing nothing"));
                        fullBackupEntry = fullBackupEntry2;
                        j = j5;
                        i = i4;
                        break;
                    }
                    if (!fullBackupAllowable(this.mTransportManager.getCurrentTransportName())) {
                        j5 = keyValueBackupIntervalMilliseconds;
                        i5 = i4;
                    }
                    if (i5 != 0) {
                        fullBackupEntry = this.mFullBackupQueue.get(i4);
                        long j6 = currentTimeMillis - fullBackupEntry.lastBackup;
                        i = j6 >= fullBackupIntervalMilliseconds ? 1 : i4;
                        if (i == 0) {
                            j = fullBackupIntervalMilliseconds - j6;
                            break;
                        }
                        try {
                            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(fullBackupEntry.packageName, i4, this.mUserId);
                            if (this.mScheduledBackupEligibility.appGetsFullBackup(packageInfoAsUser)) {
                                i2 = ((packageInfoAsUser.applicationInfo.privateFlags & 8192) == 0 && this.mActivityManagerInternal.isAppForeground(packageInfoAsUser.applicationInfo.uid)) ? 1 : i4;
                                if (i2 != 0) {
                                    try {
                                        i3 = i;
                                        j3 = currentTimeMillis;
                                        long currentTimeMillis2 = java.lang.System.currentTimeMillis() + 3600000 + this.mTokenGenerator.nextInt(BUSY_BACKOFF_FUZZ);
                                        try {
                                            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            int i6 = this.mUserId;
                                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                            j4 = keyValueBackupIntervalMilliseconds;
                                            try {
                                                sb.append("Full backup time but ");
                                                sb.append(fullBackupEntry.packageName);
                                                sb.append(" is busy; deferring to ");
                                                sb.append(simpleDateFormat.format(new java.util.Date(currentTimeMillis2)));
                                                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(i6, sb.toString()));
                                                enqueueFullBackup(fullBackupEntry.packageName, currentTimeMillis2 - fullBackupIntervalMilliseconds);
                                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                                i5 = this.mFullBackupQueue.size() > 1 ? 1 : 0;
                                                fullBackupEntry2 = fullBackupEntry;
                                                if (i2 != 0) {
                                                }
                                            }
                                        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                            j4 = keyValueBackupIntervalMilliseconds;
                                            i5 = this.mFullBackupQueue.size() > 1 ? 1 : 0;
                                            fullBackupEntry2 = fullBackupEntry;
                                            if (i2 != 0) {
                                            }
                                        }
                                    } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                                        j3 = currentTimeMillis;
                                    }
                                } else {
                                    i3 = i;
                                    j3 = currentTimeMillis;
                                    j4 = keyValueBackupIntervalMilliseconds;
                                }
                                fullBackupEntry2 = fullBackupEntry;
                                i5 = i3;
                            } else {
                                try {
                                    this.mFullBackupQueue.remove(i4);
                                    j3 = currentTimeMillis;
                                    j4 = keyValueBackupIntervalMilliseconds;
                                    i2 = 1;
                                    i5 = i;
                                    fullBackupEntry2 = fullBackupEntry;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e4) {
                                    j3 = currentTimeMillis;
                                    j4 = keyValueBackupIntervalMilliseconds;
                                    i2 = i4;
                                    i5 = this.mFullBackupQueue.size() > 1 ? 1 : 0;
                                    fullBackupEntry2 = fullBackupEntry;
                                    if (i2 != 0) {
                                    }
                                }
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e5) {
                            j3 = currentTimeMillis;
                            j4 = keyValueBackupIntervalMilliseconds;
                            i2 = 0;
                        }
                    } else {
                        j3 = currentTimeMillis;
                        j4 = keyValueBackupIntervalMilliseconds;
                        i2 = 0;
                    }
                    if (i2 != 0) {
                        j = j5;
                        int i7 = i5;
                        fullBackupEntry = fullBackupEntry2;
                        i = i7;
                        break;
                    }
                    currentTimeMillis = j3;
                    keyValueBackupIntervalMilliseconds = j4;
                    i4 = 0;
                }
                if (i != 0) {
                    try {
                        obj = obj2;
                        j2 = j;
                        z = true;
                    } catch (java.lang.IllegalStateException e6) {
                        e = e6;
                        z = true;
                        obj = obj2;
                        j2 = j;
                    }
                    try {
                        this.mRunningFullBackupTask = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.newWithCurrentTransport(this, this.mOperationStorage, null, new java.lang.String[]{fullBackupEntry.packageName}, true, fullBackupJob, new java.util.concurrent.CountDownLatch(1), null, null, false, "BMS.beginFullBackup()", getEligibilityRulesForOperation(0));
                    } catch (java.lang.IllegalStateException e7) {
                        e = e7;
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to start backup", e);
                        i = 0;
                        if (i == 0) {
                        }
                    }
                } else {
                    obj = obj2;
                    z = true;
                    j2 = j;
                }
                if (i == 0) {
                    this.mFullBackupQueue.remove(0);
                    this.mWakelock.acquire();
                    new java.lang.Thread(this.mRunningFullBackupTask).start();
                    return z;
                }
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Nothing pending full backup or failed to start the operation; rescheduling +" + j2));
                com.android.server.backup.FullBackupJob.schedule(this.mUserId, this.mContext, j2, this);
                return false;
            } catch (java.lang.Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void endFullBackup() {
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.backup.fullbackup.PerformFullTransportBackupTask performFullTransportBackupTask;
                synchronized (com.android.server.backup.UserBackupManagerService.this.mQueueLock) {
                    try {
                        if (com.android.server.backup.UserBackupManagerService.this.mRunningFullBackupTask == null) {
                            performFullTransportBackupTask = null;
                        } else {
                            performFullTransportBackupTask = com.android.server.backup.UserBackupManagerService.this.mRunningFullBackupTask;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (performFullTransportBackupTask != null) {
                    android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, com.android.server.backup.UserBackupManagerService.addUserIdToLogMessage(com.android.server.backup.UserBackupManagerService.this.mUserId, "Telling running backup to stop"));
                    performFullTransportBackupTask.handleCancel(true);
                }
            }
        }, "end-full-backup").start();
    }

    public void restoreWidgetData(java.lang.String str, byte[] bArr) {
        com.android.server.AppWidgetBackupBridge.restoreWidgetState(str, bArr, this.mUserId);
    }

    public void dataChangedImpl(java.lang.String str) {
        dataChangedImpl(str, dataChangedTargets(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dataChangedImpl(java.lang.String str, java.util.HashSet<java.lang.String> hashSet) {
        if (hashSet == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "dataChanged but no participant pkg='" + str + "' uid=" + android.os.Binder.getCallingUid()));
            return;
        }
        synchronized (this.mQueueLock) {
            try {
                if (hashSet.contains(str)) {
                    if (this.mPendingBackups.put(str, new com.android.server.backup.keyvalue.BackupRequest(str)) == null) {
                        writeToJournalLocked(str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.server.backup.KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
    }

    private java.util.HashSet<java.lang.String> dataChangedTargets(java.lang.String str) {
        java.util.HashSet<java.lang.String> union;
        java.util.HashSet<java.lang.String> hashSet;
        if (this.mContext.checkPermission("android.permission.BACKUP", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == -1) {
            synchronized (this.mBackupParticipants) {
                hashSet = this.mBackupParticipants.get(android.os.Binder.getCallingUid());
            }
            return hashSet;
        }
        if (PACKAGE_MANAGER_SENTINEL.equals(str)) {
            return com.google.android.collect.Sets.newHashSet(new java.lang.String[]{PACKAGE_MANAGER_SENTINEL});
        }
        synchronized (this.mBackupParticipants) {
            union = com.android.server.backup.utils.SparseArrayUtils.union(this.mBackupParticipants);
        }
        return union;
    }

    private void writeToJournalLocked(java.lang.String str) {
        try {
            if (this.mJournal == null) {
                this.mJournal = com.android.server.backup.DataChangedJournal.newJournal(this.mJournalDir);
            }
            this.mJournal.addPackage(str);
        } catch (java.io.IOException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Can't write " + str + " to backup journal"), e);
            this.mJournal = null;
        }
    }

    public void dataChanged(final java.lang.String str) {
        final java.util.HashSet<java.lang.String> dataChangedTargets = dataChangedTargets(str);
        if (dataChangedTargets == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "dataChanged but no participant pkg='" + str + "' uid=" + android.os.Binder.getCallingUid()));
            return;
        }
        this.mBackupHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.backup.UserBackupManagerService.this.dataChangedImpl(str, dataChangedTargets);
            }
        });
    }

    public void initializeTransports(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "initializeTransport");
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "initializeTransport(): " + java.util.Arrays.asList(strArr)));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mWakelock.acquire();
            this.mBackupHandler.post(new com.android.server.backup.internal.PerformInitializeTask(this, strArr, iBackupObserver, new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda12
                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                public final void onFinished(java.lang.String str) {
                    com.android.server.backup.UserBackupManagerService.this.lambda$initializeTransports$5(str);
                }
            }));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeTransports$5(java.lang.String str) {
        this.mWakelock.release();
    }

    public void setAncestralSerialNumber(long j) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "setAncestralSerialNumber");
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Setting ancestral work profile id to " + j));
        try {
            java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(getAncestralSerialNumberFile(), "rwd");
            try {
                randomAccessFile.writeLong(j);
                randomAccessFile.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to write to work profile serial mapping file:"), e);
        }
    }

    public long getAncestralSerialNumber() {
        try {
            java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(getAncestralSerialNumberFile(), com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            try {
                long readLong = randomAccessFile.readLong();
                randomAccessFile.close();
                return readLong;
            } finally {
            }
        } catch (java.io.FileNotFoundException e) {
            return -1L;
        } catch (java.io.IOException e2) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to read work profile serial number file:"), e2);
            return -1L;
        }
    }

    private java.io.File getAncestralSerialNumberFile() {
        if (this.mAncestralSerialNumberFile == null) {
            this.mAncestralSerialNumberFile = new java.io.File(com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(getUserId()), SERIAL_ID_FILE);
        }
        return this.mAncestralSerialNumberFile;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAncestralSerialNumberFile(java.io.File file) {
        this.mAncestralSerialNumberFile = file;
    }

    public void clearBackupData(java.lang.String str, java.lang.String str2) {
        java.util.HashSet<java.lang.String> packagesCopy;
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "clearBackupData() of " + str2 + " on " + str));
        try {
            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str2, 134217728, this.mUserId);
            if (this.mContext.checkPermission("android.permission.BACKUP", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == -1) {
                packagesCopy = this.mBackupParticipants.get(android.os.Binder.getCallingUid());
            } else {
                packagesCopy = this.mProcessedPackagesJournal.getPackagesCopy();
            }
            if (packagesCopy.contains(str2)) {
                this.mBackupHandler.removeMessages(12);
                synchronized (this.mQueueLock) {
                    try {
                        final com.android.server.backup.transport.TransportConnection transportClient = this.mTransportManager.getTransportClient(str, "BMS.clearBackupData()");
                        if (transportClient == null) {
                            this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(12, new com.android.server.backup.params.ClearRetryParams(str, str2)), 3600000L);
                            return;
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener = new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda8
                                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                                public final void onFinished(java.lang.String str3) {
                                    com.android.server.backup.UserBackupManagerService.this.lambda$clearBackupData$6(transportClient, str3);
                                }
                            };
                            this.mWakelock.acquire();
                            this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(4, new com.android.server.backup.params.ClearParams(transportClient, packageInfoAsUser, onTaskFinishedListener)));
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } finally {
                    }
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "No such package '" + str2 + "' - not clearing backup data"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearBackupData$6(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
    }

    public void backupNow() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "backupNow");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPowerManager.getPowerSaveState(5).batterySaverEnabled) {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Not running backup while in battery save mode"));
                com.android.server.backup.KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
            } else {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Scheduling immediate backup pass"));
                synchronized (getQueueLock()) {
                    if (getPendingInits().size() > 0) {
                        try {
                            getAlarmManager().cancel(this.mRunInitIntent);
                            this.mRunInitIntent.send();
                        } catch (android.app.PendingIntent.CanceledException e) {
                            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Run init intent cancelled"));
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    if (!isEnabled() || !isSetupComplete()) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup pass but enabled=" + isEnabled() + " setupComplete=" + isSetupComplete()));
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(1));
                    com.android.server.backup.KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void adbBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbBackup");
        if (android.os.UserHandle.getCallingUserId() != 0) {
            throw new java.lang.IllegalStateException("Backup supported only for the device owner");
        }
        if (!z5 && !z3 && (strArr == null || strArr.length == 0)) {
            throw new java.lang.IllegalArgumentException("Backup requested but neither shared nor any apps named");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!this.mSetupComplete) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup not supported before setup"));
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e.getMessage()));
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                return;
            }
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Requesting backup: apks=" + z + " obb=" + z2 + " shared=" + z3 + " all=" + z5 + " system=" + z6 + " includekeyvalue=" + z8 + " pkgs=" + java.util.Arrays.toString(strArr)));
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Beginning adb backup..."));
            com.android.server.backup.params.AdbBackupParams adbBackupParams = new com.android.server.backup.params.AdbBackupParams(parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z8, strArr, getEligibilityRulesForOperation(2));
            int generateRandomIntegerToken = generateRandomIntegerToken();
            synchronized (this.mAdbBackupRestoreConfirmations) {
                this.mAdbBackupRestoreConfirmations.put(generateRandomIntegerToken, adbBackupParams);
            }
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Starting backup confirmation UI"));
            if (!startConfirmationUi(generateRandomIntegerToken, "fullback")) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to launch backup confirmation UI"));
                this.mAdbBackupRestoreConfirmations.delete(generateRandomIntegerToken);
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e2.getMessage()));
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                return;
            }
            this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 0, 0);
            startConfirmationTimeout(generateRandomIntegerToken, adbBackupParams);
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Waiting for backup completion..."));
            waitForCompletion(adbBackupParams);
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e3) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e3.getMessage()));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
        } catch (java.lang.Throwable th) {
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e4) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e4.getMessage()));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
            throw th;
        }
    }

    public void fullTransportBackup(java.lang.String[] strArr) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "fullTransportBackup");
        if (android.os.UserHandle.getCallingUserId() != 0) {
            throw new java.lang.IllegalStateException("Restore supported only for the device owner");
        }
        if (fullBackupAllowable(this.mTransportManager.getCurrentTransportName())) {
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "fullTransportBackup()"));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                com.android.server.backup.fullbackup.PerformFullTransportBackupTask newWithCurrentTransport = com.android.server.backup.fullbackup.PerformFullTransportBackupTask.newWithCurrentTransport(this, this.mOperationStorage, null, strArr, false, null, countDownLatch, null, null, false, "BMS.fullTransportBackup()", getEligibilityRulesForOperation(0));
                this.mWakelock.acquire();
                new java.lang.Thread(newWithCurrentTransport, "full-transport-master").start();
                while (true) {
                    try {
                        countDownLatch.await();
                        break;
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                for (java.lang.String str : strArr) {
                    enqueueFullBackup(str, currentTimeMillis);
                }
            } catch (java.lang.IllegalStateException e2) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to start backup: ", e2);
                return;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Full backup not currently possible -- key/value backup not yet run?"));
        }
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Done with full transport backup."));
    }

    public void adbRestore(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbRestore");
        if (android.os.UserHandle.getCallingUserId() != 0) {
            throw new java.lang.IllegalStateException("Restore supported only for the device owner");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!this.mSetupComplete) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Full restore not permitted before setup"));
                return;
            }
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Beginning restore..."));
            com.android.server.backup.params.AdbRestoreParams adbRestoreParams = new com.android.server.backup.params.AdbRestoreParams(parcelFileDescriptor);
            int generateRandomIntegerToken = generateRandomIntegerToken();
            synchronized (this.mAdbBackupRestoreConfirmations) {
                this.mAdbBackupRestoreConfirmations.put(generateRandomIntegerToken, adbRestoreParams);
            }
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Starting restore confirmation UI, token=" + generateRandomIntegerToken));
            if (!startConfirmationUi(generateRandomIntegerToken, "fullrest")) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to launch restore confirmation"));
                this.mAdbBackupRestoreConfirmations.delete(generateRandomIntegerToken);
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e));
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
                return;
            }
            this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 0, 0);
            startConfirmationTimeout(generateRandomIntegerToken, adbRestoreParams);
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Waiting for restore completion..."));
            waitForCompletion(adbRestoreParams);
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e2) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e2));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
        } finally {
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e3) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e3));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
        }
    }

    public void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "excludeKeysFromRestore");
        this.mBackupPreferences.addExcludedKeys(str, list);
    }

    public void reportDelayedRestoreResult(java.lang.String str, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list) {
        java.lang.String currentTransportName = this.mTransportManager.getCurrentTransportName();
        if (currentTransportName == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to send delayed restore logs as no transport selected");
            return;
        }
        com.android.server.backup.transport.TransportConnection transportConnection = null;
        try {
            try {
                android.content.pm.PackageInfo packageInfoAsUser = getPackageManager().getPackageInfoAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L), getUserId());
                transportConnection = this.mTransportManager.getTransportClientOrThrow(currentTransportName, "BMS.reportDelayedRestoreResult");
                getBMMEventSender(transportConnection.connectOrThrow("BMS.reportDelayedRestoreResult").getBackupManagerMonitor()).sendAgentLoggingResults(packageInfoAsUser, list, 1);
            } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException | com.android.server.backup.transport.TransportNotRegisteredException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to send delayed restore logs: " + e);
                if (transportConnection == null) {
                    return;
                }
            }
            this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.reportDelayedRestoreResult");
        } catch (java.lang.Throwable th) {
            if (transportConnection != null) {
                this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.reportDelayedRestoreResult");
            }
            throw th;
        }
    }

    private boolean startConfirmationUi(int i, java.lang.String str) {
        try {
            android.content.Intent intent = new android.content.Intent(str);
            intent.setClassName("com.android.backupconfirm", "com.android.backupconfirm.BackupRestoreConfirmation");
            intent.putExtra("conftoken", i);
            intent.addFlags(536870912);
            this.mContext.startActivityAsUser(intent, android.os.UserHandle.SYSTEM);
            return true;
        } catch (android.content.ActivityNotFoundException e) {
            return false;
        }
    }

    private void startConfirmationTimeout(int i, com.android.server.backup.params.AdbParams adbParams) {
        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(9, i, 0, adbParams), 60000L);
    }

    private void waitForCompletion(com.android.server.backup.params.AdbParams adbParams) {
        synchronized (adbParams.latch) {
            while (!adbParams.latch.get()) {
                try {
                    adbParams.latch.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public void signalAdbBackupRestoreCompletion(com.android.server.backup.params.AdbParams adbParams) {
        synchronized (adbParams.latch) {
            adbParams.latch.set(true);
            adbParams.latch.notifyAll();
        }
    }

    public void acknowledgeAdbBackupOrRestore(int i, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        int i2;
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "acknowledgeAdbBackupOrRestore : token=" + i + " allow=" + z));
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "acknowledgeAdbBackupOrRestore");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mAdbBackupRestoreConfirmations) {
                try {
                    com.android.server.backup.params.AdbParams adbParams = this.mAdbBackupRestoreConfirmations.get(i);
                    if (adbParams != null) {
                        this.mBackupHandler.removeMessages(9, adbParams);
                        this.mAdbBackupRestoreConfirmations.delete(i);
                        if (z) {
                            if (adbParams instanceof com.android.server.backup.params.AdbBackupParams) {
                                i2 = 2;
                            } else {
                                i2 = 10;
                            }
                            adbParams.observer = iFullBackupRestoreObserver;
                            adbParams.curPassword = str;
                            adbParams.encryptPassword = str2;
                            this.mWakelock.acquire();
                            this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(i2, adbParams));
                        } else {
                            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "User rejected full backup/restore operation"));
                            signalAdbBackupRestoreCompletion(adbParams);
                        }
                    } else {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Attempted to ack full backup/restore with invalid token"));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.backup.utils.BackupManagerMonitorEventSender getBMMEventSender(android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        return new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor);
    }

    public void setBackupEnabled(boolean z) {
        setBackupEnabled(z, true);
    }

    private void setBackupEnabled(boolean z, boolean z2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupEnabled");
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Backup enabled => " + z));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean z3 = this.mEnabled;
            synchronized (this) {
                if (z2) {
                    try {
                        writeEnabledState(z);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                this.mEnabled = z;
            }
            updateStateOnBackupEnabled(z3, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    synchronized void setFrameworkSchedulingEnabled(boolean z) {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setFrameworkSchedulingEnabled");
            if (isFrameworkSchedulingEnabled() == z) {
                return;
            }
            int i = this.mUserId;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(z ? "Enabling" : "Disabling");
            sb.append(" backup scheduling");
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(i, sb.toString()));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "backup_scheduling_enabled", z ? 1 : 0, this.mUserId);
                if (!z) {
                    com.android.server.backup.KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                    com.android.server.backup.FullBackupJob.cancel(this.mUserId, this.mContext);
                } else {
                    com.android.server.backup.KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                    scheduleNextFullBackupJob(0L);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            throw th2;
        }
    }

    synchronized boolean isFrameworkSchedulingEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "backup_scheduling_enabled", 1, this.mUserId) == 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateStateOnBackupEnabled(boolean z, boolean z2) {
        synchronized (this.mQueueLock) {
            if (z2 && !z) {
                try {
                    if (this.mSetupComplete) {
                        com.android.server.backup.KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                        scheduleNextFullBackupJob(0L);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!z2) {
                com.android.server.backup.KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                if (z && this.mSetupComplete) {
                    final java.util.ArrayList arrayList = new java.util.ArrayList();
                    final java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    this.mTransportManager.forEachRegisteredTransport(new java.util.function.Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda9
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.backup.UserBackupManagerService.this.lambda$updateStateOnBackupEnabled$7(arrayList, arrayList2, (java.lang.String) obj);
                        }
                    });
                    for (int i = 0; i < arrayList.size(); i++) {
                        recordInitPending(true, (java.lang.String) arrayList.get(i), (java.lang.String) arrayList2.get(i));
                    }
                    this.mAlarmManager.set(0, java.lang.System.currentTimeMillis(), this.mRunInitIntent);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStateOnBackupEnabled$7(java.util.List list, java.util.List list2, java.lang.String str) {
        try {
            java.lang.String transportDirName = this.mTransportManager.getTransportDirName(str);
            list.add(str);
            list2.add(transportDirName);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unexpected unregistered transport"), e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeEnabledState(boolean z) {
        com.android.server.backup.UserBackupManagerFilePersistedSettings.writeBackupEnableState(this.mUserId, z);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean readEnabledState() {
        return com.android.server.backup.UserBackupManagerFilePersistedSettings.readBackupEnableState(this.mUserId);
    }

    public void setAutoRestore(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setAutoRestore");
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Auto restore => " + z));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "backup_auto_restore", z ? 1 : 0, this.mUserId);
                this.mAutoRestore = z;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isBackupEnabled() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupEnabled");
        return this.mEnabled;
    }

    public java.lang.String getCurrentTransport() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getCurrentTransport");
        return this.mTransportManager.getCurrentTransportName();
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCurrentTransportComponent() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getCurrentTransportComponent");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.ComponentName currentTransportComponent = this.mTransportManager.getCurrentTransportComponent();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return currentTransportComponent;
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public java.lang.String[] listAllTransports() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "listAllTransports");
        return this.mTransportManager.getRegisteredTransportNames();
    }

    public android.content.ComponentName[] listAllTransportComponents() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "listAllTransportComponents");
        return this.mTransportManager.getRegisteredTransportComponents();
    }

    public void updateTransportAttributes(android.content.ComponentName componentName, java.lang.String str, @android.annotation.Nullable android.content.Intent intent, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent2, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        updateTransportAttributes(android.os.Binder.getCallingUid(), componentName, str, intent, str2, intent2, charSequence);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateTransportAttributes(int i, android.content.ComponentName componentName, java.lang.String str, @android.annotation.Nullable android.content.Intent intent, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent2, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "updateTransportAttributes");
        java.util.Objects.requireNonNull(componentName, "transportComponent can't be null");
        java.util.Objects.requireNonNull(str, "name can't be null");
        java.util.Objects.requireNonNull(str2, "currentDestinationString can't be null");
        com.android.internal.util.Preconditions.checkArgument((intent2 == null) == (charSequence == null), "dataManagementLabel should be null iff dataManagementIntent is null");
        try {
            if (i != this.mContext.getPackageManager().getPackageUidAsUser(componentName.getPackageName(), 0, this.mUserId)) {
                throw new java.lang.SecurityException("Only the transport can change its description");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mTransportManager.updateTransportAttributes(componentName, str, intent, str2, intent2, charSequence);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.SecurityException("Transport package not found", e);
        }
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public java.lang.String selectBackupTransport(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "selectBackupTransport");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!this.mTransportManager.isTransportRegistered(str)) {
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Could not select transport " + str + ", as the transport is not registered."));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            java.lang.String selectTransport = this.mTransportManager.selectTransport(str);
            updateStateForTransport(str);
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "selectBackupTransport(transport = " + str + "): previous transport = " + selectTransport));
            return selectTransport;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void selectBackupTransportAsync(final android.content.ComponentName componentName, final android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "selectBackupTransportAsync");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String flattenToShortString = componentName.flattenToShortString();
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "selectBackupTransportAsync(transport = " + flattenToShortString + ")"));
            this.mBackupHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.backup.UserBackupManagerService.this.lambda$selectBackupTransportAsync$8(componentName, iSelectBackupTransportCallback);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectBackupTransportAsync$8(android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        int registerAndSelectTransport = this.mTransportManager.registerAndSelectTransport(componentName);
        java.lang.String str = null;
        if (registerAndSelectTransport == 0) {
            try {
                str = this.mTransportManager.getTransportName(componentName);
                updateStateForTransport(str);
            } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Transport got unregistered"));
                registerAndSelectTransport = -1;
            }
        }
        try {
            if (str != null) {
                iSelectBackupTransportCallback.onSuccess(str);
            } else {
                iSelectBackupTransportCallback.onFailure(registerAndSelectTransport);
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "ISelectBackupTransportCallback listener not available"));
        }
    }

    public java.util.List<android.content.pm.PackageInfo> filterUserFacingPackages(java.util.List<android.content.pm.PackageInfo> list) {
        if (!shouldSkipUserFacingData()) {
            return list;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        for (android.content.pm.PackageInfo packageInfo : list) {
            if (shouldSkipPackage(packageInfo.packageName)) {
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Will skip backup/restore for " + packageInfo.packageName);
            } else {
                arrayList.add(packageInfo);
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean shouldSkipUserFacingData() {
        return android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), SKIP_USER_FACING_PACKAGES, 0) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean shouldSkipPackage(java.lang.String str) {
        return WALLPAPER_PACKAGE.equals(str);
    }

    private void updateStateForTransport(java.lang.String str) {
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "backup_transport", str, this.mUserId);
        com.android.server.backup.transport.TransportConnection transportClient = this.mTransportManager.getTransportClient(str, "BMS.updateStateForTransport()");
        if (transportClient != null) {
            try {
                this.mCurrentToken = transportClient.connectOrThrow("BMS.updateStateForTransport()").getCurrentRestoreSet();
            } catch (java.lang.Exception e) {
                this.mCurrentToken = 0L;
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " not available: current token = 0"));
            }
            this.mTransportManager.disposeOfTransportClient(transportClient, "BMS.updateStateForTransport()");
            return;
        }
        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered: current token = 0"));
        this.mCurrentToken = 0L;
    }

    public android.content.Intent getConfigurationIntent(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getConfigurationIntent");
        try {
            return this.mTransportManager.getTransportConfigurationIntent(str);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to get configuration intent from transport: " + e.getMessage()));
            return null;
        }
    }

    public java.lang.String getDestinationString(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDestinationString");
        try {
            return this.mTransportManager.getTransportCurrentDestinationString(str);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to get destination string from transport: " + e.getMessage()));
            return null;
        }
    }

    public android.content.Intent getDataManagementIntent(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDataManagementIntent");
        try {
            return this.mTransportManager.getTransportDataManagementIntent(str);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to get management intent from transport: " + e.getMessage()));
            return null;
        }
    }

    public java.lang.CharSequence getDataManagementLabel(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDataManagementLabel");
        try {
            return this.mTransportManager.getTransportDataManagementLabel(str);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to get management label from transport: " + e.getMessage()));
            return null;
        }
    }

    public void agentConnected(java.lang.String str, android.os.IBinder iBinder) {
        synchronized (this.mAgentConnectLock) {
            try {
                if (android.os.Binder.getCallingUid() == 1000) {
                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "agentConnected pkg=" + str + " agent=" + iBinder));
                    this.mConnectedAgent = android.app.IBackupAgent.Stub.asInterface(iBinder);
                    this.mConnecting = false;
                } else {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + android.os.Binder.getCallingUid() + " claiming agent connected"));
                }
                this.mAgentConnectLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void agentDisconnected(final java.lang.String str) {
        synchronized (this.mAgentConnectLock) {
            try {
                if (android.os.Binder.getCallingUid() == 1000) {
                    this.mConnectedAgent = null;
                    this.mConnecting = false;
                } else {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + android.os.Binder.getCallingUid() + " claiming agent disconnected"));
                }
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "agentDisconnected: the backup agent for " + str + " died: cancel current operations");
                getThreadForAsyncOperation("agent-disconnected", new java.lang.Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.backup.UserBackupManagerService.this.lambda$agentDisconnected$9(str);
                    }
                }).start();
                this.mAgentConnectLock.notifyAll();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$agentDisconnected$9(java.lang.String str) {
        java.util.Iterator<java.lang.Integer> it = this.mOperationStorage.operationTokensForPackage(str).iterator();
        while (it.hasNext()) {
            handleCancel(it.next().intValue(), true);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.Thread getThreadForAsyncOperation(java.lang.String str, java.lang.Runnable runnable) {
        return new java.lang.Thread(runnable, str);
    }

    public void restoreAtInstall(java.lang.String str, int i) {
        boolean z;
        boolean z2;
        if (android.os.Binder.getCallingUid() != 1000) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + android.os.Binder.getCallingUid() + " attemping install-time restore"));
            return;
        }
        long availableRestoreToken = getAvailableRestoreToken(str);
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "restoreAtInstall pkg=" + str + " token=" + java.lang.Integer.toHexString(i) + " restoreSet=" + java.lang.Long.toHexString(availableRestoreToken)));
        if (availableRestoreToken != 0) {
            z = false;
        } else {
            z = true;
        }
        final com.android.server.backup.transport.TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.restoreAtInstall()");
        if (currentTransportClient == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "No transport client"));
            z = true;
        }
        if (!this.mAutoRestore) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Non-restorable state: auto=" + this.mAutoRestore));
            z = true;
        }
        if (!z) {
            try {
                this.mWakelock.acquire();
                com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener = new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda0
                    @Override // com.android.server.backup.internal.OnTaskFinishedListener
                    public final void onFinished(java.lang.String str2) {
                        com.android.server.backup.UserBackupManagerService.this.lambda$restoreAtInstall$10(currentTransportClient, str2);
                    }
                };
                android.os.Message obtainMessage = this.mBackupHandler.obtainMessage(3);
                obtainMessage.obj = com.android.server.backup.params.RestoreParams.createForRestoreAtInstall(currentTransportClient, null, null, availableRestoreToken, str, i, onTaskFinishedListener, getEligibilityRulesForRestoreAtInstall(availableRestoreToken));
                this.mBackupHandler.sendMessage(obtainMessage);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Unable to contact transport: " + e.getMessage()));
                z2 = true;
            }
        }
        z2 = z;
        if (z2) {
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.restoreAtInstall()");
            }
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Finishing install immediately"));
            try {
                this.mPackageManagerBinder.finishPackageInstall(i, false);
            } catch (android.os.RemoteException e2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreAtInstall$10(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
        this.mWakelock.release();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.backup.IRestoreSession beginRestoreSession(java.lang.String str, java.lang.String str2) {
        java.lang.String addUserIdToLogMessage;
        com.android.server.backup.transport.TransportConnection transportConnection;
        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "beginRestoreSession: pkg=" + str + " transport=" + str2));
        boolean z = true;
        if (str2 == null) {
            str2 = this.mTransportManager.getCurrentTransportName();
            if (str != null) {
                try {
                    if (this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId).applicationInfo.uid == android.os.Binder.getCallingUid()) {
                        z = false;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Asked to restore nonexistent pkg " + str));
                    throw new java.lang.IllegalArgumentException("Package " + str + " not found");
                }
            }
        }
        if (z) {
            addUserIdToLogMessage = "android.permission.BACKUP";
            this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "beginRestoreSession");
        } else {
            addUserIdToLogMessage = addUserIdToLogMessage(this.mUserId, "restoring self on current transport; no permission needed");
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage);
        }
        com.android.server.backup.transport.TransportConnection transportConnection2 = 0;
        try {
            try {
                transportConnection = this.mTransportManager.getTransportClientOrThrow(str2, "BMS.beginRestoreSession");
            } catch (android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException | com.android.server.backup.transport.TransportNotRegisteredException e2) {
                e = e2;
                transportConnection = null;
            } catch (java.lang.Throwable th) {
                th = th;
                if (transportConnection2 != 0) {
                }
                throw th;
            }
            try {
                int backupDestinationFromTransport = getBackupDestinationFromTransport(transportConnection);
                if (transportConnection != null) {
                    this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.beginRestoreSession");
                }
                synchronized (this) {
                    try {
                        if (this.mActiveRestoreSession != null) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Restore session requested but one already active"));
                            return null;
                        }
                        if (this.mBackupRunning) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Restore session requested but currently running backups"));
                            return null;
                        }
                        this.mActiveRestoreSession = new com.android.server.backup.restore.ActiveRestoreSession(this, str, str2, getEligibilityRulesForOperation(backupDestinationFromTransport));
                        this.mBackupHandler.sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                        return this.mActiveRestoreSession;
                    } catch (java.lang.Throwable th2) {
                        throw th2;
                    }
                }
            } catch (android.os.RemoteException | com.android.server.backup.transport.TransportNotAvailableException | com.android.server.backup.transport.TransportNotRegisteredException e3) {
                e = e3;
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to get operation type from transport: " + e);
                if (transportConnection != null) {
                    this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.beginRestoreSession");
                }
                return null;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            transportConnection2 = addUserIdToLogMessage;
            if (transportConnection2 != 0) {
                this.mTransportManager.disposeOfTransportClient(transportConnection2, "BMS.beginRestoreSession");
            }
            throw th;
        }
    }

    public void clearRestoreSession(com.android.server.backup.restore.ActiveRestoreSession activeRestoreSession) {
        synchronized (this) {
            try {
                if (activeRestoreSession != this.mActiveRestoreSession) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "ending non-current restore session"));
                } else {
                    android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Clearing restore session and halting timeout"));
                    this.mActiveRestoreSession = null;
                    this.mBackupHandler.removeMessages(8);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void opComplete(int i, final long j) {
        this.mOperationStorage.onOperationComplete(i, j, new java.util.function.Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.backup.UserBackupManagerService.this.lambda$opComplete$11(j, (com.android.server.backup.BackupRestoreTask) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$opComplete$11(long j, com.android.server.backup.BackupRestoreTask backupRestoreTask) {
        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(21, android.util.Pair.create(backupRestoreTask, java.lang.Long.valueOf(j))));
    }

    public boolean isAppEligibleForBackup(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isAppEligibleForBackup");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.backup.transport.TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.isAppEligibleForBackup");
            boolean appIsRunningAndEligibleForBackupWithTransport = this.mScheduledBackupEligibility.appIsRunningAndEligibleForBackupWithTransport(currentTransportClient, str);
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.isAppEligibleForBackup");
            }
            return appIsRunningAndEligibleForBackupWithTransport;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.lang.String[] filterAppsEligibleForBackup(java.lang.String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "filterAppsEligibleForBackup");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.backup.transport.TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.filterAppsEligibleForBackup");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : strArr) {
                if (this.mScheduledBackupEligibility.appIsRunningAndEligibleForBackupWithTransport(currentTransportClient, str)) {
                    arrayList.add(str);
                }
            }
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.filterAppsEligibleForBackup");
            }
            java.lang.String[] strArr2 = (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return strArr2;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public com.android.server.backup.utils.BackupEligibilityRules getEligibilityRulesForOperation(int i) {
        return getEligibilityRules(this.mPackageManager, this.mUserId, this.mContext, i);
    }

    private static com.android.server.backup.utils.BackupEligibilityRules getEligibilityRules(android.content.pm.PackageManager packageManager, int i, android.content.Context context, int i2) {
        return new com.android.server.backup.utils.BackupEligibilityRules(packageManager, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class), i, context, i2);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (strArr != null) {
            try {
                for (java.lang.String str : strArr) {
                    if ("agents".startsWith(str)) {
                        dumpAgents(printWriter);
                        return;
                    } else if ("transportclients".equals(str.toLowerCase())) {
                        this.mTransportManager.dumpTransportClients(printWriter);
                        return;
                    } else {
                        if ("transportstats".equals(str.toLowerCase())) {
                            this.mTransportManager.dumpTransportStats(printWriter);
                            return;
                        }
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        dumpInternal(printWriter);
        dumpBMMEvents(printWriter);
    }

    private void dumpAgents(java.io.PrintWriter printWriter) {
        java.util.List<android.content.pm.PackageInfo> allAgentPackages = allAgentPackages();
        printWriter.println("Defined backup agents:");
        for (android.content.pm.PackageInfo packageInfo : allAgentPackages) {
            printWriter.print("  ");
            printWriter.print(packageInfo.packageName);
            printWriter.println(':');
            printWriter.print("      ");
            printWriter.println(packageInfo.applicationInfo.backupAgentName);
        }
    }

    private void dumpBMMEvents(java.io.PrintWriter printWriter) {
        com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils = new com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils();
        if (backupManagerMonitorDumpsysUtils.deleteExpiredBMMEvents()) {
            printWriter.println("BACKUP MANAGER MONITOR EVENTS HAVE EXPIRED");
            return;
        }
        java.io.File bMMEventsFile = backupManagerMonitorDumpsysUtils.getBMMEventsFile();
        if (bMMEventsFile.length() == 0) {
            printWriter.println("NO BACKUP MANAGER MONITOR EVENTS");
            return;
        }
        if (backupManagerMonitorDumpsysUtils.isFileLargerThanSizeLimit(bMMEventsFile)) {
            printWriter.println("BACKUP MANAGER MONITOR EVENTS FILE OVER SIZE LIMIT - future events will not be recorded");
        }
        printWriter.println("START OF BACKUP MANAGER MONITOR EVENTS");
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(bMMEventsFile));
            while (true) {
                try {
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else {
                        printWriter.println(readLine);
                    }
                } finally {
                }
            }
            bufferedReader.close();
        } catch (java.io.IOException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO Exception when reading BMM events from file: " + e);
            printWriter.println("IO Exception when reading BMM events from file");
        }
        printWriter.println("END OF BACKUP MANAGER MONITOR EVENTS");
    }

    @dalvik.annotation.optimization.NeverCompile
    private void dumpInternal(java.io.PrintWriter printWriter) {
        java.lang.String str = this.mUserId == 0 ? "" : "User " + this.mUserId + ":";
        synchronized (this.mQueueLock) {
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(str);
                sb.append("Backup Manager is ");
                sb.append(this.mEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                sb.append(" / ");
                sb.append(!this.mSetupComplete ? "not " : "");
                sb.append("setup complete / ");
                sb.append(this.mPendingInits.size() == 0 ? "not " : "");
                sb.append("pending init");
                printWriter.println(sb.toString());
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("Auto-restore is ");
                sb2.append(this.mAutoRestore ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                printWriter.println(sb2.toString());
                if (this.mBackupRunning) {
                    printWriter.println("Backup currently running");
                }
                printWriter.println(isBackupOperationInProgress() ? "Backup in progress" : "No backups running");
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                sb3.append("Framework scheduling is ");
                sb3.append(isFrameworkSchedulingEnabled() ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                printWriter.println(sb3.toString());
                printWriter.println("Last backup pass started: " + this.mLastBackupPass + " (now = " + java.lang.System.currentTimeMillis() + ')');
                java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                sb4.append("  next scheduled: ");
                sb4.append(com.android.server.backup.KeyValueBackupJob.nextScheduled(this.mUserId));
                printWriter.println(sb4.toString());
                printWriter.println(str + "Transport whitelist:");
                for (android.content.ComponentName componentName : this.mTransportManager.getTransportWhitelist()) {
                    printWriter.print("    ");
                    printWriter.println(componentName.flattenToShortString());
                }
                printWriter.println(str + "Available transports:");
                java.lang.String[] listAllTransports = listAllTransports();
                if (listAllTransports != null) {
                    for (java.lang.String str2 : listAllTransports) {
                        java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
                        sb5.append(str2.equals(this.mTransportManager.getCurrentTransportName()) ? "  * " : "    ");
                        sb5.append(str2);
                        printWriter.println(sb5.toString());
                        try {
                            java.io.File file = new java.io.File(this.mBaseStateDir, this.mTransportManager.getTransportDirName(str2));
                            printWriter.println("       destination: " + this.mTransportManager.getTransportCurrentDestinationString(str2));
                            printWriter.println("       intent: " + this.mTransportManager.getTransportConfigurationIntent(str2));
                            java.io.File[] listFiles = file.listFiles();
                            int length = listFiles.length;
                            for (int i = 0; i < length; i++) {
                                java.io.File file2 = listFiles[i];
                                printWriter.println("       " + file2.getName() + " - " + file2.length() + " state bytes");
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, addUserIdToLogMessage(this.mUserId, "Error in transport"), e);
                            printWriter.println("        Error: " + e);
                        }
                    }
                }
                this.mTransportManager.dumpTransportClients(printWriter);
                printWriter.println(str + "Pending init: " + this.mPendingInits.size());
                java.util.Iterator<java.lang.String> it = this.mPendingInits.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + it.next());
                }
                printWriter.print(str + "Ancestral: ");
                printWriter.println(java.lang.Long.toHexString(this.mAncestralToken));
                printWriter.print(str + "Current:   ");
                printWriter.println(java.lang.Long.toHexString(this.mCurrentToken));
                int size = this.mBackupParticipants.size();
                printWriter.println(str + "Participants:");
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mBackupParticipants.keyAt(i2);
                    printWriter.print("  uid: ");
                    printWriter.println(keyAt);
                    java.util.Iterator<java.lang.String> it2 = this.mBackupParticipants.valueAt(i2).iterator();
                    while (it2.hasNext()) {
                        printWriter.println("    " + it2.next());
                    }
                }
                java.lang.StringBuilder sb6 = new java.lang.StringBuilder();
                sb6.append(str);
                sb6.append("Ancestral packages: ");
                sb6.append(this.mAncestralPackages == null ? "none" : java.lang.Integer.valueOf(this.mAncestralPackages.size()));
                printWriter.println(sb6.toString());
                if (this.mAncestralPackages != null) {
                    java.util.Iterator<java.lang.String> it3 = this.mAncestralPackages.iterator();
                    while (it3.hasNext()) {
                        printWriter.println("    " + it3.next());
                    }
                }
                java.util.Set<java.lang.String> packagesCopy = this.mProcessedPackagesJournal.getPackagesCopy();
                printWriter.println(str + "Ever backed up: " + packagesCopy.size());
                java.util.Iterator<java.lang.String> it4 = packagesCopy.iterator();
                while (it4.hasNext()) {
                    printWriter.println("    " + it4.next());
                }
                printWriter.println(str + "Pending key/value backup: " + this.mPendingBackups.size());
                java.util.Iterator<com.android.server.backup.keyvalue.BackupRequest> it5 = this.mPendingBackups.values().iterator();
                while (it5.hasNext()) {
                    printWriter.println("    " + it5.next());
                }
                printWriter.println(str + "Full backup queue:" + this.mFullBackupQueue.size());
                java.util.Iterator<com.android.server.backup.fullbackup.FullBackupEntry> it6 = this.mFullBackupQueue.iterator();
                while (it6.hasNext()) {
                    com.android.server.backup.fullbackup.FullBackupEntry next = it6.next();
                    printWriter.print("    ");
                    printWriter.print(next.lastBackup);
                    printWriter.print(" : ");
                    printWriter.println(next.packageName);
                }
                printWriter.println(str + "Agent timeouts:");
                printWriter.println("    KvBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis());
                printWriter.println("    FullBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis());
                printWriter.println("    SharedBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getSharedBackupAgentTimeoutMillis());
                printWriter.println("    RestoreAgentTimeoutMillis (system): " + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(9999));
                printWriter.println("    RestoreAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(10000));
                printWriter.println("    RestoreAgentFinishedTimeoutMillis: " + this.mAgentTimeoutParameters.getRestoreAgentFinishedTimeoutMillis());
                printWriter.println("    QuotaExceededTimeoutMillis: " + this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getBackupDestinationFromTransport(com.android.server.backup.transport.TransportConnection transportConnection) throws com.android.server.backup.transport.TransportNotAvailableException, android.os.RemoteException {
        if (!shouldUseNewBackupEligibilityRules()) {
            return 0;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if ((transportConnection.connectOrThrow("BMS.getBackupDestinationFromTransport").getTransportFlags() & 2) == 0) {
                return 0;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldUseNewBackupEligibilityRules() {
        return android.util.FeatureFlagUtils.isEnabled(this.mContext, "settings_use_new_backup_eligibility_rules");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String addUserIdToLogMessage(int i, java.lang.String str) {
        return "[UserID:" + i + "] " + str;
    }

    public android.app.backup.IBackupManager getBackupManagerBinder() {
        return this.mBackupManagerBinder;
    }
}
