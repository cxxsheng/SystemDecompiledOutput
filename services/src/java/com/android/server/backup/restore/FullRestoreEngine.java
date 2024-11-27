package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class FullRestoreEngine extends com.android.server.backup.restore.RestoreEngine {
    private android.app.IBackupAgent mAgent;
    private java.lang.String mAgentPackage;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    final boolean mAllowApks;
    private long mAppVersion;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    final byte[] mBuffer;
    private final java.util.HashSet<java.lang.String> mClearedPackages;
    private final com.android.server.backup.restore.RestoreDeleteObserver mDeleteObserver;
    final int mEphemeralOpToken;
    private final boolean mIsAdbRestore;
    private final java.util.HashMap<java.lang.String, android.content.pm.Signature[]> mManifestSignatures;
    final android.app.backup.IBackupManagerMonitor mMonitor;
    private final com.android.server.backup.BackupRestoreTask mMonitorTask;
    private com.android.server.backup.fullbackup.FullBackupObbConnection mObbConnection;
    private android.app.backup.IFullBackupRestoreObserver mObserver;
    final android.content.pm.PackageInfo mOnlyPackage;
    private final com.android.server.backup.OperationStorage mOperationStorage;
    private final java.util.HashMap<java.lang.String, java.lang.String> mPackageInstallers;
    private final java.util.HashMap<java.lang.String, com.android.server.backup.restore.RestorePolicy> mPackagePolicies;
    private android.os.ParcelFileDescriptor[] mPipes;

    @com.android.internal.annotations.GuardedBy({"mPipesLock"})
    private boolean mPipesClosed;
    private final java.lang.Object mPipesLock;
    private com.android.server.backup.FileMetadata mReadOnlyParent;
    private android.content.pm.ApplicationInfo mTargetApp;
    private final int mUserId;
    private byte[] mWidgetData;

    public FullRestoreEngine(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, com.android.server.backup.BackupRestoreTask backupRestoreTask, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, android.content.pm.PackageInfo packageInfo, boolean z, int i, boolean z2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mDeleteObserver = new com.android.server.backup.restore.RestoreDeleteObserver();
        this.mObbConnection = null;
        this.mPackagePolicies = new java.util.HashMap<>();
        this.mPackageInstallers = new java.util.HashMap<>();
        this.mManifestSignatures = new java.util.HashMap<>();
        this.mClearedPackages = new java.util.HashSet<>();
        this.mPipes = null;
        this.mPipesLock = new java.lang.Object();
        this.mWidgetData = null;
        this.mReadOnlyParent = null;
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mEphemeralOpToken = i;
        this.mMonitorTask = backupRestoreTask;
        this.mObserver = iFullBackupRestoreObserver;
        this.mMonitor = iBackupManagerMonitor;
        this.mOnlyPackage = packageInfo;
        this.mAllowApks = z;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mIsAdbRestore = z2;
        this.mUserId = userBackupManagerService.getUserId();
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (com.android.server.backup.Flags.enableMaxSizeWritesToPipes()) {
            this.mBuffer = new byte[65536];
        } else {
            this.mBuffer = new byte[32768];
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    FullRestoreEngine() {
        this.mDeleteObserver = new com.android.server.backup.restore.RestoreDeleteObserver();
        this.mObbConnection = null;
        this.mPackagePolicies = new java.util.HashMap<>();
        this.mPackageInstallers = new java.util.HashMap<>();
        this.mManifestSignatures = new java.util.HashMap<>();
        this.mClearedPackages = new java.util.HashSet<>();
        this.mPipes = null;
        this.mPipesLock = new java.lang.Object();
        this.mWidgetData = null;
        this.mReadOnlyParent = null;
        this.mIsAdbRestore = false;
        this.mAllowApks = false;
        this.mEphemeralOpToken = 0;
        this.mUserId = 0;
        this.mBackupEligibilityRules = null;
        this.mAgentTimeoutParameters = null;
        this.mBuffer = null;
        this.mBackupManagerService = null;
        this.mOperationStorage = null;
        this.mMonitor = null;
        this.mMonitorTask = null;
        this.mOnlyPackage = null;
    }

    public android.app.IBackupAgent getAgent() {
        return this.mAgent;
    }

    public byte[] getWidgetData() {
        return this.mWidgetData;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v105 ??, still in use, count: 1, list:
          (r0v105 ?? I:java.lang.Runnable) from 0x03b2: CONSTRUCTOR (r4v21 ?? I:java.lang.Thread) = (r0v105 ?? I:java.lang.Runnable), (r8v15 ?? I:java.lang.String) A[Catch: RemoteException -> 0x036f, IOException -> 0x0372, MD:(java.lang.Runnable, java.lang.String):void (c)] (LINE:489) call: java.lang.Thread.<init>(java.lang.Runnable, java.lang.String):void type: CONSTRUCTOR
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public boolean restoreOneFile(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v105 ??, still in use, count: 1, list:
          (r0v105 ?? I:java.lang.Runnable) from 0x03b2: CONSTRUCTOR (r4v21 ?? I:java.lang.Thread) = (r0v105 ?? I:java.lang.Runnable), (r8v15 ?? I:java.lang.String) A[Catch: RemoteException -> 0x036f, IOException -> 0x0372, MD:(java.lang.Runnable, java.lang.String):void (c)] (LINE:489) call: java.lang.Thread.<init>(java.lang.Runnable, java.lang.String):void type: CONSTRUCTOR
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r38v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        */

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$restoreOneFile$0(long j) {
    }

    /* renamed from: com.android.server.backup.restore.FullRestoreEngine$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$backup$restore$RestorePolicy = new int[com.android.server.backup.restore.RestorePolicy.values().length];

        static {
            try {
                $SwitchMap$com$android$server$backup$restore$RestorePolicy[com.android.server.backup.restore.RestorePolicy.IGNORE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$RestorePolicy[com.android.server.backup.restore.RestorePolicy.ACCEPT_IF_APK.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$RestorePolicy[com.android.server.backup.restore.RestorePolicy.ACCEPT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
        }
    }

    boolean shouldSkipReadOnlyDir(com.android.server.backup.FileMetadata fileMetadata) {
        if (isValidParent(this.mReadOnlyParent, fileMetadata)) {
            return true;
        }
        if (isReadOnlyDir(fileMetadata)) {
            this.mReadOnlyParent = fileMetadata;
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Skipping restore of " + fileMetadata.path + " and its contents as read-only dirs are currently not supported.");
            return true;
        }
        this.mReadOnlyParent = null;
        return false;
    }

    private static boolean isValidParent(com.android.server.backup.FileMetadata fileMetadata, @android.annotation.NonNull com.android.server.backup.FileMetadata fileMetadata2) {
        return fileMetadata != null && fileMetadata2.packageName.equals(fileMetadata.packageName) && fileMetadata2.domain.equals(fileMetadata.domain) && fileMetadata2.path.startsWith(getPathWithTrailingSeparator(fileMetadata.path));
    }

    private static java.lang.String getPathWithTrailingSeparator(java.lang.String str) {
        if (str.endsWith(java.io.File.separator)) {
            return str;
        }
        return str + java.io.File.separator;
    }

    private static boolean isReadOnlyDir(com.android.server.backup.FileMetadata fileMetadata) {
        return fileMetadata.type == 2 && (fileMetadata.mode & ((long) android.system.OsConstants.S_IWUSR)) == 0;
    }

    private void setUpPipes() throws java.io.IOException {
        synchronized (this.mPipesLock) {
            this.mPipes = android.os.ParcelFileDescriptor.createPipe();
            this.mPipesClosed = false;
        }
    }

    private void tearDownPipes() {
        synchronized (this.mPipesLock) {
            if (!this.mPipesClosed && this.mPipes != null) {
                try {
                    this.mPipes[0].close();
                    this.mPipes[1].close();
                    this.mPipesClosed = true;
                } catch (java.io.IOException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Couldn't close agent pipes", e);
                }
            }
        }
    }

    private void tearDownAgent(android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        if (this.mAgent != null) {
            if (z) {
                try {
                    int generateRandomIntegerToken = this.mBackupManagerService.generateRandomIntegerToken();
                    long fullBackupAgentTimeoutMillis = this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis();
                    com.android.server.backup.restore.AdbRestoreFinishedLatch adbRestoreFinishedLatch = new com.android.server.backup.restore.AdbRestoreFinishedLatch(this.mBackupManagerService, this.mOperationStorage, generateRandomIntegerToken);
                    this.mBackupManagerService.prepareOperationTimeout(generateRandomIntegerToken, fullBackupAgentTimeoutMillis, adbRestoreFinishedLatch, 1);
                    if (this.mTargetApp.processName.equals("system")) {
                        new java.lang.Thread(new com.android.server.backup.restore.AdbRestoreFinishedRunnable(this.mAgent, generateRandomIntegerToken, this.mBackupManagerService), "restore-sys-finished-runner").start();
                    } else {
                        this.mAgent.doRestoreFinished(generateRandomIntegerToken, this.mBackupManagerService.getBackupManagerBinder());
                    }
                    adbRestoreFinishedLatch.await();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Lost app trying to shut down");
                }
            }
            this.mBackupManagerService.tearDownAgentAndKill(applicationInfo);
            this.mAgent = null;
        }
    }

    void handleTimeout() {
        tearDownPipes();
        setResult(-2);
        setRunning(false);
    }

    private boolean isRestorableFile(com.android.server.backup.FileMetadata fileMetadata) {
        if (this.mBackupEligibilityRules.getBackupDestination() == 1) {
            return true;
        }
        if ("c".equals(fileMetadata.domain)) {
            return false;
        }
        return (com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD.equals(fileMetadata.domain) && fileMetadata.path.startsWith("no_backup/")) ? false : true;
    }

    private static boolean isCanonicalFilePath(java.lang.String str) {
        if (str.contains("..") || str.contains("//")) {
            return false;
        }
        return true;
    }

    private boolean shouldForceClearAppDataOnFullRestore(java.lang.String str) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mBackupManagerService.getContext().getContentResolver(), "packages_to_clear_data_before_full_restore", this.mUserId);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        return java.util.Arrays.asList(stringForUser.split(";")).contains(str);
    }

    void sendOnRestorePackage(java.lang.String str) {
        if (this.mObserver != null) {
            try {
                this.mObserver.onRestorePackage(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full restore observer went away: restorePackage");
                this.mObserver = null;
            }
        }
    }
}
