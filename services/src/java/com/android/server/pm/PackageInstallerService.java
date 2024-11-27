package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageInstallerService extends android.content.pm.IPackageInstaller.Stub implements com.android.server.pm.PackageSessionProvider {
    private static final int ADB_DEV_MODE = 36;
    private static final int HISTORICAL_CLEAR_SIZE = 400;
    private static final int HISTORICAL_SESSIONS_THRESHOLD = 500;
    private static final long MAX_ACTIVE_SESSIONS_NO_PERMISSION = 50;
    private static final long MAX_ACTIVE_SESSIONS_WITH_PERMISSION = 1024;
    private static final long MAX_AGE_MILLIS = 259200000;
    private static final long MAX_HISTORICAL_SESSIONS = 1048576;
    private static final long MAX_INSTALL_CONSTRAINTS_TIMEOUT_MILLIS = 604800000;
    private static final long MAX_SESSION_AGE_ON_LOW_STORAGE_MILLIS = 28800000;
    private static final long MAX_TIME_SINCE_UPDATE_MILLIS = 1814400000;
    private static final java.lang.String TAG_SESSIONS = "sessions";
    private final com.android.server.pm.ApexManager mApexManager;
    private android.app.AppOpsManager mAppOps;
    private final com.android.server.pm.PackageInstallerService.Callbacks mCallbacks;
    private final android.content.Context mContext;
    private final com.android.server.pm.GentleUpdateHelper mGentleUpdateHelper;
    private final android.os.Handler mInstallHandler;
    final com.android.server.pm.PackageArchiver mPackageArchiver;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.PackageSessionVerifier mSessionVerifier;
    private final java.io.File mSessionsDir;
    private final android.util.AtomicFile mSessionsFile;
    private final com.android.server.pm.StagingManager mStagingManager;
    private static final java.lang.String TAG = "PackageInstaller";
    private static final boolean LOGD = android.util.Log.isLoggable(TAG, 3);
    private static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;
    public static final java.util.Set<java.lang.String> INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS = java.util.Set.of("android.permission.USE_FULL_SCREEN_INTENT");
    private static final java.io.FilenameFilter sStageFilter = new java.io.FilenameFilter() { // from class: com.android.server.pm.PackageInstallerService.1
        @Override // java.io.FilenameFilter
        public boolean accept(java.io.File file, java.lang.String str) {
            return com.android.server.pm.PackageInstallerService.isStageName(str);
        }
    };
    private volatile boolean mOkToSendBroadcasts = false;
    private volatile boolean mBypassNextStagedInstallerCheck = false;
    private volatile boolean mBypassNextAllowedApexUpdateCheck = false;
    private volatile int mDisableVerificationForUid = -1;
    private final com.android.server.pm.PackageInstallerService.InternalCallback mInternalCallback = new com.android.server.pm.PackageInstallerService.InternalCallback();
    private final java.util.Random mRandom = new java.security.SecureRandom();

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private final android.util.SparseBooleanArray mAllocatedSessions = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private final android.util.SparseArray<com.android.server.pm.PackageInstallerSession> mSessions = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private final java.util.List<com.android.server.pm.PackageInstallerHistoricalSession> mHistoricalSessions = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private final android.util.SparseIntArray mHistoricalSessionsByInstaller = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private final android.util.SparseBooleanArray mLegacySessions = new android.util.SparseBooleanArray();
    private final com.android.server.pm.SilentUpdatePolicy mSilentUpdatePolicy = new com.android.server.pm.SilentUpdatePolicy();

    @android.annotation.NonNull
    private final com.android.server.pm.utils.RequestThrottle mSettingsWriteRequest = new com.android.server.pm.utils.RequestThrottle(com.android.server.IoThread.getHandler(), new java.util.function.Supplier() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda7
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            java.lang.Boolean lambda$new$0;
            lambda$new$0 = com.android.server.pm.PackageInstallerService.this.lambda$new$0();
            return lambda$new$0;
        }
    });
    private final android.os.HandlerThread mInstallThread = new android.os.HandlerThread(TAG);

    private static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.pm.PackageInstallerService mPackageInstallerService;

        Lifecycle(android.content.Context context, com.android.server.pm.PackageInstallerService packageInstallerService) {
            super(context);
            this.mPackageInstallerService = packageInstallerService;
        }

        @Override // com.android.server.SystemService
        public void onStart() {
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mPackageInstallerService.onBroadcastReady();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$new$0() {
        return java.lang.Boolean.valueOf(writeSessions());
    }

    public PackageInstallerService(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService, java.util.function.Supplier<com.android.internal.pm.parsing.PackageParser2> supplier) {
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mInstallThread.start();
        this.mInstallHandler = new android.os.Handler(this.mInstallThread.getLooper());
        this.mCallbacks = new com.android.server.pm.PackageInstallerService.Callbacks(this.mInstallThread.getLooper());
        this.mSessionsFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "install_sessions.xml"), "package-session");
        this.mSessionsDir = new java.io.File(android.os.Environment.getDataSystemDirectory(), "install_sessions");
        this.mSessionsDir.mkdirs();
        this.mApexManager = com.android.server.pm.ApexManager.getInstance();
        this.mStagingManager = new com.android.server.pm.StagingManager(context);
        this.mSessionVerifier = new com.android.server.pm.PackageSessionVerifier(context, this.mPm, this.mApexManager, supplier, this.mInstallThread.getLooper());
        this.mGentleUpdateHelper = new com.android.server.pm.GentleUpdateHelper(context, this.mInstallThread.getLooper(), new com.android.server.pm.AppStateHelper(context));
        this.mPackageArchiver = new com.android.server.pm.PackageArchiver(this.mContext, this.mPm);
        ((com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class)).startService(new com.android.server.pm.PackageInstallerService.Lifecycle(context, this));
    }

    com.android.server.pm.StagingManager getStagingManager() {
        return this.mStagingManager;
    }

    boolean okToSendBroadcasts() {
        return this.mOkToSendBroadcasts;
    }

    public void systemReady() {
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mStagingManager.systemReady();
        this.mGentleUpdateHelper.systemReady();
        synchronized (this.mSessions) {
            try {
                readSessionsLocked();
                expireSessionsLocked();
                reconcileStagesLocked(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
                android.util.ArraySet newArraySet = newArraySet(this.mSessionsDir.listFiles());
                for (int i = 0; i < this.mSessions.size(); i++) {
                    newArraySet.remove(buildAppIconFile(this.mSessions.valueAt(i).sessionId));
                }
                java.util.Iterator it = newArraySet.iterator();
                while (it.hasNext()) {
                    java.io.File file = (java.io.File) it.next();
                    android.util.Slog.w(TAG, "Deleting orphan icon " + file);
                    file.delete();
                }
                this.mSettingsWriteRequest.runNow();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBroadcastReady() {
        this.mOkToSendBroadcasts = true;
    }

    void restoreAndApplyStagedSessionIfNeeded() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i);
                    if (valueAt.isStaged()) {
                        com.android.server.pm.PackageInstallerSession.StagedSession stagedSession = valueAt.mStagedSession;
                        if (!stagedSession.isInTerminalState() && stagedSession.hasParentSessionId() && getSession(stagedSession.getParentSessionId()) == null) {
                            stagedSession.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "An orphan staged session " + stagedSession.sessionId() + " is found, parent " + stagedSession.getParentSessionId() + " is missing");
                        } else if (!stagedSession.hasParentSessionId() && stagedSession.isCommitted() && !stagedSession.isInTerminalState()) {
                            arrayList.add(stagedSession);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        this.mStagingManager.restoreSessions(arrayList, this.mPm.isDeviceUpgrading());
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private void reconcileStagesLocked(java.lang.String str) {
        android.util.ArraySet<java.io.File> stagingDirsOnVolume = getStagingDirsOnVolume(str);
        for (int i = 0; i < this.mSessions.size(); i++) {
            stagingDirsOnVolume.remove(this.mSessions.valueAt(i).stageDir);
        }
        removeStagingDirs(stagingDirsOnVolume);
    }

    private android.util.ArraySet<java.io.File> getStagingDirsOnVolume(java.lang.String str) {
        android.util.ArraySet<java.io.File> newArraySet = newArraySet(getTmpSessionDir(str).listFiles(sStageFilter));
        newArraySet.addAll(newArraySet(android.os.Environment.getDataStagingDirectory(str).listFiles()));
        return newArraySet;
    }

    private void removeStagingDirs(android.util.ArraySet<java.io.File> arraySet) {
        java.util.Iterator<java.io.File> it = arraySet.iterator();
        while (it.hasNext()) {
            java.io.File next = it.next();
            android.util.Slog.w(TAG, "Deleting orphan stage " + next);
            this.mPm.removeCodePath(next);
        }
    }

    public void onPrivateVolumeMounted(java.lang.String str) {
        synchronized (this.mSessions) {
            reconcileStagesLocked(str);
        }
    }

    public void freeStageDirs(java.lang.String str) {
        android.util.ArraySet<java.io.File> stagingDirsOnVolume = getStagingDirsOnVolume(str);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i);
                    if (stagingDirsOnVolume.contains(valueAt.stageDir)) {
                        if (currentTimeMillis - valueAt.createdMillis >= MAX_SESSION_AGE_ON_LOW_STORAGE_MILLIS) {
                            com.android.server.pm.PackageInstallerSession packageInstallerSession = !valueAt.hasParentSessionId() ? valueAt : this.mSessions.get(valueAt.getParentSessionId());
                            if (packageInstallerSession == null) {
                                android.util.Slog.e(TAG, "freeStageDirs: found an orphaned session: " + valueAt.sessionId + " parent=" + valueAt.getParentSessionId());
                            } else if (!packageInstallerSession.isDestroyed()) {
                                packageInstallerSession.abandon();
                            }
                        } else {
                            stagingDirsOnVolume.remove(valueAt.stageDir);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        removeStagingDirs(stagingDirsOnVolume);
    }

    @java.lang.Deprecated
    public java.io.File allocateStageDirLegacy(java.lang.String str, boolean z) throws java.io.IOException {
        java.io.File buildTmpSessionDir;
        synchronized (this.mSessions) {
            try {
                try {
                    int allocateSessionIdLocked = allocateSessionIdLocked();
                    this.mLegacySessions.put(allocateSessionIdLocked, true);
                    buildTmpSessionDir = buildTmpSessionDir(allocateSessionIdLocked, str);
                    prepareStageDir(buildTmpSessionDir);
                } catch (java.lang.IllegalStateException e) {
                    throw new java.io.IOException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return buildTmpSessionDir;
    }

    @java.lang.Deprecated
    public java.lang.String allocateExternalStageCidLegacy() {
        java.lang.String str;
        synchronized (this.mSessions) {
            int allocateSessionIdLocked = allocateSessionIdLocked();
            this.mLegacySessions.put(allocateSessionIdLocked, true);
            str = "smdl" + allocateSessionIdLocked + ".tmp";
        }
        return str;
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private void readSessionsLocked() {
        if (LOGD) {
            android.util.Slog.v(TAG, "readSessionsLocked()");
        }
        this.mSessions.clear();
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStream = this.mSessionsFile.openRead();
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            break;
                        }
                        if (next == 2 && "session".equals(resolvePullParser.getName())) {
                            try {
                                com.android.server.pm.PackageInstallerSession readFromXml = com.android.server.pm.PackageInstallerSession.readFromXml(resolvePullParser, this.mInternalCallback, this.mContext, this.mPm, this.mInstallThread.getLooper(), this.mStagingManager, this.mSessionsDir, this, this.mSilentUpdatePolicy);
                                this.mSessions.put(readFromXml.sessionId, readFromXml);
                                this.mAllocatedSessions.put(readFromXml.sessionId, true);
                            } catch (java.lang.Exception e) {
                                android.util.Slog.e(TAG, "Could not read session", e);
                            }
                        }
                    }
                } catch (java.io.IOException | java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e2) {
                    android.util.Slog.wtf(TAG, "Failed reading install sessions", e2);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        } catch (java.io.FileNotFoundException e3) {
        }
        for (int i = 0; i < this.mSessions.size(); i++) {
            this.mSessions.valueAt(i).onAfterSessionRead(this.mSessions);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private void expireSessionsLocked() {
        android.util.SparseArray<com.android.server.pm.PackageInstallerSession> clone = this.mSessions.clone();
        int size = clone.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.PackageInstallerSession valueAt = clone.valueAt(i);
            if (!valueAt.hasParentSessionId()) {
                long currentTimeMillis = java.lang.System.currentTimeMillis() - valueAt.createdMillis;
                long currentTimeMillis2 = java.lang.System.currentTimeMillis() - valueAt.getUpdatedMillis();
                boolean z = true;
                if (valueAt.isStaged()) {
                    if (valueAt.isStagedAndInTerminalState() && currentTimeMillis2 >= MAX_TIME_SINCE_UPDATE_MILLIS) {
                        z = false;
                    }
                } else if (currentTimeMillis >= MAX_AGE_MILLIS) {
                    android.util.Slog.w(TAG, "Abandoning old session created at " + valueAt.createdMillis);
                    z = false;
                }
                if (!z) {
                    android.util.Slog.w(TAG, "Remove old session: " + valueAt.sessionId);
                    removeActiveSession(valueAt);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mSessions"})
    public void removeActiveSession(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        this.mSessions.remove(packageInstallerSession.sessionId);
        addHistoricalSessionLocked(packageInstallerSession);
        for (com.android.server.pm.PackageInstallerSession packageInstallerSession2 : packageInstallerSession.getChildSessions()) {
            this.mSessions.remove(packageInstallerSession2.sessionId);
            addHistoricalSessionLocked(packageInstallerSession2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private void addHistoricalSessionLocked(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        if (this.mHistoricalSessions.size() > 500) {
            android.util.Slog.d(TAG, "Historical sessions size reaches threshold, clear the oldest");
            this.mHistoricalSessions.subList(0, 400).clear();
        }
        this.mHistoricalSessions.add(packageInstallerSession.createHistoricalSession());
        int installerUid = packageInstallerSession.getInstallerUid();
        this.mHistoricalSessionsByInstaller.put(installerUid, this.mHistoricalSessionsByInstaller.get(installerUid) + 1);
    }

    private boolean writeSessions() {
        int size;
        com.android.server.pm.PackageInstallerSession[] packageInstallerSessionArr;
        if (LOGD) {
            android.util.Slog.v(TAG, "writeSessions()");
        }
        synchronized (this.mSessions) {
            try {
                size = this.mSessions.size();
                packageInstallerSessionArr = new com.android.server.pm.PackageInstallerSession[size];
                for (int i = 0; i < size; i++) {
                    packageInstallerSessionArr[i] = this.mSessions.valueAt(i);
                }
            } finally {
            }
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = this.mSessionsFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, TAG_SESSIONS);
                for (int i2 = 0; i2 < size; i2++) {
                    packageInstallerSessionArr[i2].write(resolveSerializer, this.mSessionsDir);
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_SESSIONS);
                resolveSerializer.endDocument();
                this.mSessionsFile.finishWrite(startWrite);
                return true;
            } catch (java.io.IOException e) {
                fileOutputStream = startWrite;
                if (fileOutputStream != null) {
                    this.mSessionsFile.failWrite(fileOutputStream);
                }
                return false;
            }
        } catch (java.io.IOException e2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.io.File buildAppIconFile(int i) {
        return new java.io.File(this.mSessionsDir, "app_icon." + i + ".png");
    }

    public int createSession(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, java.lang.String str2, int i) {
        try {
            if (sessionParams.dataLoaderParams != null && this.mContext.checkCallingOrSelfPermission("com.android.permission.USE_INSTALLER_V2") != 0) {
                throw new java.lang.SecurityException("You need the com.android.permission.USE_INSTALLER_V2 permission to use a data loader");
            }
            sessionParams.installFlags &= -536870913;
            return createSessionInternal(sessionParams, str, str2, android.os.Binder.getCallingUid(), i);
        } catch (java.io.IOException e) {
            throw android.util.ExceptionUtils.wrap(e);
        }
    }

    int createSessionInternal(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, java.lang.String str2, int i, int i2) throws java.io.IOException {
        java.lang.String str3;
        java.lang.String str4;
        com.android.server.pm.Computer computer;
        int i3;
        java.lang.String str5;
        int allocateSessionIdLocked;
        java.io.File file;
        java.lang.String str6;
        int launcherLargeIconSize;
        int launcherLargeIconSize2;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        java.lang.String[] packagesForUid;
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(i, i2, true, true, "createSession");
        if (this.mPm.isUserRestricted(i2, "no_install_apps")) {
            throw new java.lang.SecurityException("User restriction prevents installing");
        }
        if (sessionParams.installReason == 5 && this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS") != 0) {
            throw new java.lang.SecurityException("INSTALL_REASON_ROLLBACK requires the MANAGE_ROLLBACKS permission or the TEST_MANAGE_ROLLBACKS permission");
        }
        if (sessionParams.appPackageName != null && !isValidPackageName(sessionParams.appPackageName)) {
            sessionParams.appPackageName = null;
        }
        sessionParams.appLabel = (java.lang.String) android.text.TextUtils.trimToSize(sessionParams.appLabel, 1000);
        if (sessionParams.installerPackageName != null && !isValidPackageName(sessionParams.installerPackageName)) {
            sessionParams.installerPackageName = null;
        }
        java.lang.String str7 = sessionParams.installerPackageName != null ? sessionParams.installerPackageName : str;
        if (com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(i) || com.android.server.pm.PackageInstallerSession.isSystemDataLoaderInstallation(sessionParams) || com.android.server.pm.PackageManagerServiceUtils.isAdoptedShell(i, this.mContext)) {
            sessionParams.installFlags |= 32;
            str3 = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME;
        } else {
            if (i != 1000) {
                this.mAppOps.checkPackage(i, str);
            }
            if (!android.text.TextUtils.equals(str7, str) && this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                this.mAppOps.checkPackage(i, str7);
            }
            sessionParams.installFlags &= -33;
            sessionParams.installFlags &= -65;
            sessionParams.installFlags &= -134217729;
            sessionParams.installFlags |= 2;
            if ((sessionParams.installFlags & 65536) != 0 && !this.mPm.isCallerVerifier(snapshotComputer, i)) {
                sessionParams.installFlags &= -65537;
            }
            if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_TEST_ONLY_PACKAGE") != 0) {
                sessionParams.installFlags &= -5;
            }
            sessionParams.developmentInstallFlags = 0;
            str3 = str;
        }
        if (sessionParams.originatingUid != -1 && sessionParams.originatingUid != i && (packagesForUid = snapshotComputer.getPackagesForUid(sessionParams.originatingUid)) != null && packagesForUid.length > 0) {
            str4 = packagesForUid[0];
        } else {
            str4 = null;
        }
        if (android.os.Build.IS_DEBUGGABLE || com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(i)) {
            sessionParams.installFlags |= 1048576;
        } else {
            sessionParams.installFlags &= -1048577;
        }
        if (this.mDisableVerificationForUid != -1) {
            if (i == this.mDisableVerificationForUid) {
                sessionParams.installFlags |= 524288;
            } else {
                sessionParams.installFlags &= -524289;
            }
            this.mDisableVerificationForUid = -1;
        } else if ((sessionParams.installFlags & 36) != 36) {
            sessionParams.installFlags &= -524289;
        }
        if (!android.content.pm.Flags.rollbackLifetime()) {
            computer = snapshotComputer;
        } else {
            computer = snapshotComputer;
            if (sessionParams.rollbackLifetimeMillis > 0) {
                if ((sessionParams.installFlags & 262144) == 0) {
                    throw new java.lang.IllegalArgumentException("Can't set rollbackLifetimeMillis when rollback is not enabled");
                }
                if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0) {
                    throw new java.lang.SecurityException("Setting rollback lifetime requires the MANAGE_ROLLBACKS permission");
                }
            } else if (sessionParams.rollbackLifetimeMillis < 0) {
                throw new java.lang.IllegalArgumentException("rollbackLifetimeMillis can't be negative.");
            }
        }
        if (android.content.pm.Flags.recoverabilityDetection()) {
            if (sessionParams.rollbackImpactLevel == 1 || sessionParams.rollbackImpactLevel == 2) {
                if ((sessionParams.installFlags & 262144) == 0) {
                    throw new java.lang.IllegalArgumentException("Can't set rollbackImpactLevel when rollback is not enabled");
                }
                if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0) {
                    throw new java.lang.SecurityException("Setting rollbackImpactLevel requires the MANAGE_ROLLBACKS permission");
                }
            } else if (sessionParams.rollbackImpactLevel < 0) {
                throw new java.lang.IllegalArgumentException("rollbackImpactLevel can't be negative.");
            }
        }
        boolean z = (sessionParams.installFlags & 131072) != 0;
        if (z) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGE_UPDATES") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == -1) {
                throw new java.lang.SecurityException("Not allowed to perform APEX updates");
            }
        } else if (sessionParams.isStaged) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", TAG);
        }
        if (z) {
            if (!this.mApexManager.isApexSupported()) {
                throw new java.lang.IllegalArgumentException("This device doesn't support the installation of APEX files");
            }
            if (sessionParams.isMultiPackage) {
                throw new java.lang.IllegalArgumentException("A multi-session can't be set as APEX.");
            }
            if (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i) || this.mBypassNextAllowedApexUpdateCheck) {
                sessionParams.installFlags |= 8388608;
            } else {
                sessionParams.installFlags &= -8388609;
            }
        }
        if ((sessionParams.installFlags & 16777216) != 0 && !com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i) && !android.os.Build.IS_DEBUGGABLE) {
            sessionParams.installFlags &= -16777217;
        }
        sessionParams.installFlags &= -1073741825;
        if (com.android.server.pm.PackageArchiver.isArchivingEnabled() && sessionParams.appPackageName != null && (packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(sessionParams.appPackageName, 1000)) != null && com.android.server.pm.PackageArchiver.isArchived(packageStateInternal.getUserStateOrDefault(i2)) && com.android.server.pm.PackageArchiver.getResponsibleInstallerPackage(packageStateInternal).equals(str7)) {
            sessionParams.installFlags |= 1073741824;
        }
        if ((sessionParams.installFlags & 2048) != 0 && !com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i) && (computer.getFlagsForUid(i) & 1) == 0) {
            throw new java.lang.SecurityException("Only system apps could use the PackageManager.INSTALL_INSTANT_APP flag.");
        }
        if (sessionParams.isStaged && !com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i) && !this.mBypassNextStagedInstallerCheck && !isStagedInstallerAllowed(str7)) {
            throw new java.lang.SecurityException("Installer not allowed to commit staged install");
        }
        if (!z || com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i) || this.mBypassNextStagedInstallerCheck || isStagedInstallerAllowed(str7)) {
            this.mBypassNextStagedInstallerCheck = false;
            this.mBypassNextAllowedApexUpdateCheck = false;
            if (!sessionParams.isMultiPackage) {
                boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS") == 0;
                if ((sessionParams.installFlags & 256) != 0 && !z2) {
                    throw new java.lang.SecurityException("You need the android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS permission to use the PackageManager.INSTALL_GRANT_ALL_REQUESTED_PERMISSIONS flag");
                }
                android.util.ArrayMap permissionStates = sessionParams.getPermissionStates();
                if (!permissionStates.isEmpty() && !z2) {
                    for (int i4 = 0; i4 < permissionStates.size(); i4++) {
                        if (!INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS.contains((java.lang.String) permissionStates.keyAt(i4))) {
                            throw new java.lang.SecurityException("You need the android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS permission to grant runtime permissions for a session");
                        }
                    }
                }
                if (sessionParams.appIcon != null && (sessionParams.appIcon.getWidth() > (launcherLargeIconSize2 = (launcherLargeIconSize = ((android.app.ActivityManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY)).getLauncherLargeIconSize()) * 2) || sessionParams.appIcon.getHeight() > launcherLargeIconSize2)) {
                    sessionParams.appIcon = android.graphics.Bitmap.createScaledBitmap(sessionParams.appIcon, launcherLargeIconSize, launcherLargeIconSize, true);
                }
                switch (sessionParams.mode) {
                    case 1:
                    case 2:
                        if ((sessionParams.installFlags & 16) != 0) {
                            if (!com.android.internal.content.InstallLocationUtils.fitsOnInternal(this.mContext, sessionParams)) {
                                throw new java.io.IOException("No suitable internal storage available");
                            }
                        } else if ((sessionParams.installFlags & 512) != 0) {
                            sessionParams.installFlags |= 16;
                            break;
                        } else {
                            sessionParams.installFlags |= 16;
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                sessionParams.volumeUuid = com.android.internal.content.InstallLocationUtils.resolveInstallVolume(this.mContext, sessionParams);
                                break;
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Invalid install mode: " + sessionParams.mode);
                }
            }
            if (str7 == null) {
                i3 = -1;
            } else {
                i3 = computer.getPackageUid(str7, 0L, i2);
            }
            if (i3 != -1) {
                str5 = str7;
            } else {
                str5 = null;
            }
            synchronized (this.mSessions) {
                try {
                    int sessionCount = getSessionCount(this.mSessions, i);
                    if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0) {
                        if (sessionCount >= MAX_ACTIVE_SESSIONS_WITH_PERMISSION) {
                            throw new java.lang.IllegalStateException("Too many active sessions for UID " + i);
                        }
                    } else if (sessionCount >= MAX_ACTIVE_SESSIONS_NO_PERMISSION) {
                        throw new java.lang.IllegalStateException("Too many active sessions for UID " + i);
                    }
                    if (this.mHistoricalSessionsByInstaller.get(i) >= MAX_HISTORICAL_SESSIONS) {
                        throw new java.lang.IllegalStateException("Too many historical sessions for UID " + i);
                    }
                    int existingDraftSessionId = getExistingDraftSessionId(i3, sessionParams, i2);
                    allocateSessionIdLocked = existingDraftSessionId != -1 ? existingDraftSessionId : allocateSessionIdLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            if (sessionParams.isMultiPackage) {
                file = null;
                str6 = null;
            } else if ((sessionParams.installFlags & 16) != 0) {
                file = buildSessionDir(allocateSessionIdLocked, sessionParams);
                str6 = null;
            } else {
                str6 = buildExternalStageCid(allocateSessionIdLocked);
                file = null;
            }
            if (sessionParams.forceQueryableOverride && !com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(i)) {
                sessionParams.forceQueryableOverride = false;
            }
            android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
            if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i2)) {
                sessionParams.installFlags |= 67108864;
            }
            if (z || this.mContext.checkCallingOrSelfPermission("android.permission.ENFORCE_UPDATE_OWNERSHIP") == -1) {
                sessionParams.installFlags &= -33554433;
            }
            int i5 = allocateSessionIdLocked;
            com.android.server.pm.PackageInstallerSession packageInstallerSession = new com.android.server.pm.PackageInstallerSession(this.mInternalCallback, this.mContext, this.mPm, this, this.mSilentUpdatePolicy, this.mInstallThread.getLooper(), this.mStagingManager, allocateSessionIdLocked, i2, i, com.android.server.pm.InstallSource.create(str3, str4, str5, i3, str5, str2, sessionParams.packageSource), sessionParams, currentTimeMillis, 0L, file, str6, null, null, false, false, false, false, null, -1, false, false, false, 0, "", null);
            synchronized (this.mSessions) {
                this.mSessions.put(i5, packageInstallerSession);
            }
            this.mPm.addInstallerPackageName(packageInstallerSession.getInstallSource());
            this.mCallbacks.notifySessionCreated(packageInstallerSession.sessionId, packageInstallerSession.userId);
            this.mSettingsWriteRequest.schedule();
            if (LOGD) {
                android.util.Slog.d(TAG, "Created session id=" + i5 + " staged=" + sessionParams.isStaged);
            }
            return i5;
        }
        throw new java.lang.SecurityException("Installer not allowed to commit non-staged APEX install");
    }

    int getExistingDraftSessionId(int i, @android.annotation.NonNull android.content.pm.PackageInstaller.SessionParams sessionParams, int i2) {
        int existingDraftSessionIdInternal;
        synchronized (this.mSessions) {
            existingDraftSessionIdInternal = getExistingDraftSessionIdInternal(i, sessionParams, i2);
        }
        return existingDraftSessionIdInternal;
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private int getExistingDraftSessionIdInternal(int i, android.content.pm.PackageInstaller.SessionParams sessionParams, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        java.lang.String str = sessionParams.appPackageName;
        if (!com.android.server.pm.PackageArchiver.isArchivingEnabled() || i == -1 || str == null || (packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str, 1000)) == null || !com.android.server.pm.PackageArchiver.isArchived(packageStateInternal.getUserStateOrDefault(i2))) {
            return -1;
        }
        if (sessionParams.unarchiveId > 0) {
            com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(sessionParams.unarchiveId);
            if (packageInstallerSession == null || !isValidDraftSession(packageInstallerSession, str, i, i2)) {
                return -1;
            }
            return packageInstallerSession.sessionId;
        }
        for (int i3 = 0; i3 < this.mSessions.size(); i3++) {
            com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i3);
            if (valueAt != null && isValidDraftSession(valueAt, str, i, i2)) {
                return valueAt.sessionId;
            }
        }
        return -1;
    }

    private boolean isValidDraftSession(@android.annotation.NonNull com.android.server.pm.PackageInstallerSession packageInstallerSession, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        return (packageInstallerSession.getInstallFlags() & 536870912) != 0 && str.equals(packageInstallerSession.params.appPackageName) && packageInstallerSession.userId == i2 && i == packageInstallerSession.getInstallerUid();
    }

    void cleanupDraftIfUnclaimed(int i) {
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession session = this.mPm.mInstallerService.getSession(i);
                if (session != null && (session.getInstallFlags() & 536870912) != 0) {
                    session.abandon();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isStagedInstallerAllowed(java.lang.String str) {
        return com.android.server.SystemConfig.getInstance().getWhitelistedStagedInstallers().contains(str);
    }

    public void updateSessionAppIcon(int i, android.graphics.Bitmap bitmap) {
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                    throw new java.lang.SecurityException("Caller has no access to session " + i);
                }
                if (bitmap != null) {
                    int launcherLargeIconSize = ((android.app.ActivityManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY)).getLauncherLargeIconSize();
                    int i2 = launcherLargeIconSize * 2;
                    if (bitmap.getWidth() <= i2) {
                        if (bitmap.getHeight() > i2) {
                        }
                    }
                    bitmap = android.graphics.Bitmap.createScaledBitmap(bitmap, launcherLargeIconSize, launcherLargeIconSize, true);
                }
                packageInstallerSession.params.appIcon = bitmap;
                packageInstallerSession.params.appIconLastModified = -1L;
                this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateSessionAppLabel(int i, java.lang.String str) {
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                    throw new java.lang.SecurityException("Caller has no access to session " + i);
                }
                if (!str.equals(packageInstallerSession.params.appLabel)) {
                    packageInstallerSession.params.appLabel = str;
                    this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void abandonSession(int i) {
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                    throw new java.lang.SecurityException("Caller has no access to session " + i);
                }
                packageInstallerSession.abandon();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.pm.IPackageInstallerSession openSession(int i) {
        try {
            return openSessionInternal(i);
        } catch (java.io.IOException e) {
            throw android.util.ExceptionUtils.wrap(e);
        }
    }

    private boolean checkOpenSessionAccess(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        if (packageInstallerSession == null || (packageInstallerSession.getInstallFlags() & 536870912) != 0) {
            return false;
        }
        if (isCallingUidOwner(packageInstallerSession)) {
            return true;
        }
        return packageInstallerSession.isSealed() && this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT") == 0;
    }

    private com.android.server.pm.PackageInstallerSession openSessionInternal(int i) throws java.io.IOException {
        com.android.server.pm.PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            try {
                packageInstallerSession = this.mSessions.get(i);
                if (!checkOpenSessionAccess(packageInstallerSession)) {
                    throw new java.lang.SecurityException("Caller has no access to session " + i);
                }
                packageInstallerSession.open();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return packageInstallerSession;
    }

    @com.android.internal.annotations.GuardedBy({"mSessions"})
    private int allocateSessionIdLocked() {
        int i = 0;
        while (true) {
            int nextInt = this.mRandom.nextInt(2147483646) + 1;
            if (!this.mAllocatedSessions.get(nextInt, false)) {
                this.mAllocatedSessions.put(nextInt, true);
                return nextInt;
            }
            int i2 = i + 1;
            if (i >= 32) {
                throw new java.lang.IllegalStateException("Failed to allocate session ID");
            }
            i = i2;
        }
    }

    static boolean isStageName(java.lang.String str) {
        return (str.startsWith("vmdl") && str.endsWith(".tmp")) || (str.startsWith("smdl") && str.endsWith(".tmp")) || str.startsWith("smdl2tmp");
    }

    static int tryParseSessionId(@android.annotation.NonNull java.lang.String str) throws java.lang.IllegalArgumentException {
        if (!str.startsWith("vmdl") || !str.endsWith(".tmp")) {
            throw new java.lang.IllegalArgumentException("Not a temporary session directory");
        }
        return java.lang.Integer.parseInt(str.substring("vmdl".length(), str.length() - ".tmp".length()));
    }

    private static boolean isValidPackageName(@android.annotation.NonNull java.lang.String str) {
        return str.length() <= 255 && android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(str, false, true) == null;
    }

    private java.io.File getTmpSessionDir(java.lang.String str) {
        return android.os.Environment.getDataAppDirectory(str);
    }

    private java.io.File buildTmpSessionDir(int i, java.lang.String str) {
        return new java.io.File(getTmpSessionDir(str), "vmdl" + i + ".tmp");
    }

    private java.io.File buildSessionDir(int i, android.content.pm.PackageInstaller.SessionParams sessionParams) {
        if (sessionParams.isStaged || (sessionParams.installFlags & 131072) != 0) {
            return new java.io.File(android.os.Environment.getDataStagingDirectory(sessionParams.volumeUuid), "session_" + i);
        }
        java.io.File buildTmpSessionDir = buildTmpSessionDir(i, sessionParams.volumeUuid);
        if (DEBUG && !java.util.Objects.equals(java.lang.Integer.valueOf(tryParseSessionId(buildTmpSessionDir.getName())), java.lang.Integer.valueOf(i))) {
            throw new java.lang.RuntimeException("session folder format is off: " + buildTmpSessionDir.getName() + " (" + i + ")");
        }
        return buildTmpSessionDir;
    }

    static void prepareStageDir(java.io.File file) throws java.io.IOException {
        if (file.exists()) {
            throw new java.io.IOException("Session dir already exists: " + file);
        }
        try {
            android.system.Os.mkdir(file.getAbsolutePath(), 509);
            android.system.Os.chmod(file.getAbsolutePath(), 509);
            if (!android.os.SELinux.restorecon(file)) {
                java.lang.String canonicalPath = file.getCanonicalPath();
                java.lang.String fileSelabelLookup = android.os.SELinux.fileSelabelLookup(canonicalPath);
                boolean fileContext = android.os.SELinux.setFileContext(canonicalPath, fileSelabelLookup);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Failed to SELinux.restorecon session dir, path: [");
                sb.append(canonicalPath);
                sb.append("], ctx: [");
                sb.append(fileSelabelLookup);
                sb.append("]. Retrying via SELinux.fileSelabelLookup/SELinux.setFileContext: ");
                sb.append(fileContext ? "SUCCESS" : "FAILURE");
                android.util.Slog.e(TAG, sb.toString());
                if (!fileContext) {
                    throw new java.io.IOException("Failed to restorecon session dir: " + file);
                }
            }
        } catch (android.system.ErrnoException e) {
            throw new java.io.IOException("Failed to prepare session dir: " + file, e);
        }
    }

    private java.lang.String buildExternalStageCid(int i) {
        return "smdl" + i + ".tmp";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: shouldFilterSession, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean lambda$getStagedSessions$1(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, android.content.pm.PackageInstaller.SessionInfo sessionInfo) {
        return (sessionInfo == null || i == sessionInfo.getInstallerUid() || computer.canQueryPackage(i, sessionInfo.getAppPackageName())) ? false : true;
    }

    public android.content.pm.PackageInstaller.SessionInfo getSessionInfo(int i) {
        android.content.pm.PackageInstaller.SessionInfo sessionInfo;
        int callingUid = android.os.Binder.getCallingUid();
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession != null && (!packageInstallerSession.isStaged() || !packageInstallerSession.isDestroyed())) {
                    sessionInfo = packageInstallerSession.generateInfoForCaller(true, callingUid);
                } else {
                    sessionInfo = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (lambda$getStagedSessions$1(this.mPm.snapshotComputer(), callingUid, sessionInfo)) {
            return null;
        }
        return sessionInfo;
    }

    public android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getStagedSessions() {
        final int callingUid = android.os.Binder.getCallingUid();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i);
                    if (valueAt.isStaged() && !valueAt.isDestroyed()) {
                        arrayList.add(valueAt.generateInfoForCaller(false, callingUid));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        final com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getStagedSessions$1;
                lambda$getStagedSessions$1 = com.android.server.pm.PackageInstallerService.this.lambda$getStagedSessions$1(snapshotComputer, callingUid, (android.content.pm.PackageInstaller.SessionInfo) obj);
                return lambda$getStagedSessions$1;
            }
        });
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getAllSessions(int i) {
        final int callingUid = android.os.Binder.getCallingUid();
        final com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "getAllSessions");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i2);
                    if (valueAt.userId == i && !valueAt.hasParentSessionId() && (!valueAt.isStaged() || !valueAt.isDestroyed())) {
                        arrayList.add(valueAt.generateInfoForCaller(false, callingUid));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getAllSessions$2;
                lambda$getAllSessions$2 = com.android.server.pm.PackageInstallerService.this.lambda$getAllSessions$2(snapshotComputer, callingUid, (android.content.pm.PackageInstaller.SessionInfo) obj);
                return lambda$getAllSessions$2;
            }
        });
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getMySessions(java.lang.String str, int i) {
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = android.os.Binder.getCallingUid();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "getMySessions");
        this.mAppOps.checkPackage(callingUid, str);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i2);
                    android.content.pm.PackageInstaller.SessionInfo generateInfoForCaller = valueAt.generateInfoForCaller(false, 1000);
                    if (java.util.Objects.equals(generateInfoForCaller.getInstallerPackageName(), str) && valueAt.userId == i && !valueAt.hasParentSessionId() && isCallingUidOwner(valueAt) && (valueAt.getInstallFlags() & 536870912) == 0) {
                        arrayList.add(generateInfoForCaller);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getHistoricalSessions(int i) {
        final int callingUid = android.os.Binder.getCallingUid();
        final com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "getAllSessions");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mHistoricalSessions.size(); i2++) {
                try {
                    com.android.server.pm.PackageInstallerHistoricalSession packageInstallerHistoricalSession = this.mHistoricalSessions.get(i2);
                    if (i == -1 || packageInstallerHistoricalSession.userId == i) {
                        arrayList.add(packageInstallerHistoricalSession.generateInfo());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getHistoricalSessions$3;
                lambda$getHistoricalSessions$3 = com.android.server.pm.PackageInstallerService.this.lambda$getHistoricalSessions$3(snapshotComputer, callingUid, (android.content.pm.PackageInstaller.SessionInfo) obj);
                return lambda$getHistoricalSessions$3;
            }
        });
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public void uninstall(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, int i, android.content.IntentSender intentSender, int i2) {
        uninstall(versionedPackage, str, i, intentSender, i2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
    }

    void uninstall(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, int i, android.content.IntentSender intentSender, int i2, int i3, int i4) {
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(i3, i2, true, true, "uninstall");
        if (!com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(i3)) {
            this.mAppOps.checkPackage(i3, str);
        }
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        boolean z = devicePolicyManagerInternal != null && devicePolicyManagerInternal.canSilentlyInstallPackage(str, i3);
        com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter packageDeleteObserverAdapter = new com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter(this.mContext, intentSender, versionedPackage.getPackageName(), z, i2, this.mPackageArchiver, i);
        if (this.mContext.checkPermission("android.permission.DELETE_PACKAGES", i4, i3) == 0) {
            this.mPm.deletePackageVersioned(versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i);
            return;
        }
        if (z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mPm.deletePackageVersioned(versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.app.admin.DevicePolicyEventLogger.createEvent(113).setAdmin(str).write();
                return;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (snapshotComputer.getApplicationInfo(str, 0L, i2).targetSdkVersion >= 28) {
            this.mContext.enforcePermission("android.permission.REQUEST_DELETE_PACKAGES", i4, i3, null);
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.setData(android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, versionedPackage.getPackageName(), null));
        intent.putExtra("android.content.pm.extra.CALLBACK", (android.os.Parcelable) new android.content.pm.PackageManager.UninstallCompleteCallback(packageDeleteObserverAdapter.getBinder().asBinder()));
        if ((i & 16) != 0) {
            intent.putExtra("android.content.pm.extra.DELETE_FLAGS", i);
        }
        packageDeleteObserverAdapter.onUserActionRequired(intent);
    }

    public void uninstallExistingPackage(android.content.pm.VersionedPackage versionedPackage, java.lang.String str, android.content.IntentSender intentSender, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        this.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        this.mPm.snapshotComputer().enforceCrossUserPermission(callingUid, i, true, true, "uninstall");
        if (!com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(callingUid)) {
            this.mAppOps.checkPackage(callingUid, str);
        }
        this.mPm.deleteExistingPackageAsUser(versionedPackage, new com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter(this.mContext, intentSender, versionedPackage.getPackageName(), false, i).getBinder(), i);
    }

    public void installExistingPackage(java.lang.String str, int i, int i2, android.content.IntentSender intentSender, int i3, java.util.List<java.lang.String> list) {
        android.util.Pair<java.lang.Integer, android.content.IntentSender> installExistingPackageAsUser = this.mPm.installExistingPackageAsUser(str, i3, i, i2, list, intentSender);
        int intValue = ((java.lang.Integer) installExistingPackageAsUser.first).intValue();
        android.content.IntentSender intentSender2 = (android.content.IntentSender) installExistingPackageAsUser.second;
        if (intentSender2 != null) {
            com.android.server.pm.InstallPackageHelper.onInstallComplete(intValue, this.mContext, intentSender2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.INSTALL_PACKAGES")
    public void setPermissionsResult(int i, boolean z) {
        setPermissionsResult_enforcePermission();
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession != null) {
                    packageInstallerSession.setPermissionsResult(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isValidForInstallConstraints(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String str, int i, java.lang.String str2) {
        return android.text.TextUtils.equals(packageStateInternal.getInstallSource().mInstallerPackageName, str) || android.text.TextUtils.equals(packageStateInternal.getInstallSource().mUpdateOwnerPackageName, str) || ((this.mPm.snapshotComputer().checkUidPermission("android.permission.INSTALL_SELF_UPDATES", i) == 0) && android.text.TextUtils.equals(str2, str));
    }

    private java.util.concurrent.CompletableFuture<android.content.pm.PackageInstaller.InstallConstraintsResult> checkInstallConstraintsInternal(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, long j) {
        java.util.Objects.requireNonNull(list);
        java.util.Objects.requireNonNull(installConstraints);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = android.os.Binder.getCallingUid();
        if (!android.text.TextUtils.equals(snapshotComputer.getNameForUid(callingUid), str)) {
            throw new java.lang.SecurityException("The installerPackageName set by the caller doesn't match the caller's own package name.");
        }
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)) {
            for (java.lang.String str2 : list) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
                if (packageStateInternal == null || !isValidForInstallConstraints(packageStateInternal, str, callingUid, str2)) {
                    throw new java.lang.SecurityException("Caller has no access to package " + str2);
                }
            }
        }
        return this.mGentleUpdateHelper.checkInstallConstraints(list, installConstraints, j);
    }

    public void checkInstallConstraints(java.lang.String str, java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, final android.os.RemoteCallback remoteCallback) {
        java.util.Objects.requireNonNull(remoteCallback);
        checkInstallConstraintsInternal(str, list, installConstraints, 0L).thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.PackageInstallerService.lambda$checkInstallConstraints$4(remoteCallback, (android.content.pm.PackageInstaller.InstallConstraintsResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkInstallConstraints$4(android.os.RemoteCallback remoteCallback, android.content.pm.PackageInstaller.InstallConstraintsResult installConstraintsResult) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("result", installConstraintsResult);
        remoteCallback.sendResult(bundle);
    }

    public void waitForInstallConstraints(java.lang.String str, final java.util.List<java.lang.String> list, final android.content.pm.PackageInstaller.InstallConstraints installConstraints, final android.content.IntentSender intentSender, long j) {
        java.util.Objects.requireNonNull(intentSender);
        if (j < 0 || j > 604800000) {
            throw new java.lang.IllegalArgumentException("Invalid timeoutMillis=" + j);
        }
        checkInstallConstraintsInternal(str, list, installConstraints, j).thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.PackageInstallerService.this.lambda$waitForInstallConstraints$5(list, installConstraints, intentSender, (android.content.pm.PackageInstaller.InstallConstraintsResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitForInstallConstraints$5(java.util.List list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.content.IntentSender intentSender, android.content.pm.PackageInstaller.InstallConstraintsResult installConstraintsResult) {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.intent.extra.PACKAGES", (java.lang.String[]) list.toArray(new java.lang.String[0]));
        intent.putExtra("android.content.pm.extra.INSTALL_CONSTRAINTS", installConstraints);
        intent.putExtra("android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT", installConstraintsResult);
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    public void registerCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, final int i) {
        this.mPm.snapshotComputer().enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "registerCallback");
        registerCallback(iPackageInstallerCallback, new java.util.function.IntPredicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda5
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                boolean lambda$registerCallback$6;
                lambda$registerCallback$6 = com.android.server.pm.PackageInstallerService.lambda$registerCallback$6(i, i2);
                return lambda$registerCallback$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerCallback$6(int i, int i2) {
        return i == i2;
    }

    public void registerCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, java.util.function.IntPredicate intPredicate) {
        this.mCallbacks.register(iPackageInstallerCallback, new com.android.server.pm.PackageInstallerService.BroadcastCookie(android.os.Binder.getCallingUid(), intPredicate));
    }

    public void unregisterCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) {
        this.mCallbacks.unregister(iPackageInstallerCallback);
    }

    @Override // com.android.server.pm.PackageSessionProvider
    public com.android.server.pm.PackageInstallerSession getSession(int i) {
        com.android.server.pm.PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            packageInstallerSession = this.mSessions.get(i);
        }
        return packageInstallerSession;
    }

    @Override // com.android.server.pm.PackageSessionProvider
    public com.android.server.pm.PackageSessionVerifier getSessionVerifier() {
        return this.mSessionVerifier;
    }

    @Override // com.android.server.pm.PackageSessionProvider
    public com.android.server.pm.GentleUpdateHelper getGentleUpdateHelper() {
        return this.mGentleUpdateHelper;
    }

    public void bypassNextStagedInstallerCheck(boolean z) {
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Caller not allowed to bypass staged installer check");
        }
        this.mBypassNextStagedInstallerCheck = z;
    }

    public void bypassNextAllowedApexUpdateCheck(boolean z) {
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Caller not allowed to bypass allowed apex update check");
        }
        this.mBypassNextAllowedApexUpdateCheck = z;
    }

    public void disableVerificationForUid(int i) {
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Operation not allowed for caller");
        }
        this.mDisableVerificationForUid = i;
    }

    public void setAllowUnlimitedSilentUpdates(@android.annotation.Nullable java.lang.String str) {
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Caller not allowed to unlimite silent updates");
        }
        this.mSilentUpdatePolicy.setAllowUnlimitedSilentUpdates(str);
    }

    public void setSilentUpdatesThrottleTime(long j) {
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Caller not allowed to set silent updates throttle time");
        }
        this.mSilentUpdatePolicy.setSilentUpdatesThrottleTime(j);
    }

    public void requestArchive(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, @android.annotation.NonNull android.content.IntentSender intentSender, @android.annotation.NonNull android.os.UserHandle userHandle) {
        this.mPackageArchiver.requestArchive(str, str2, i, intentSender, userHandle);
    }

    public void requestUnarchive(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.content.IntentSender intentSender, @android.annotation.NonNull android.os.UserHandle userHandle) {
        this.mPackageArchiver.requestUnarchive(str, str2, intentSender, userHandle);
    }

    public void installPackageArchived(@android.annotation.NonNull final android.content.pm.ArchivedPackageParcel archivedPackageParcel, @android.annotation.NonNull final android.content.pm.PackageInstaller.SessionParams sessionParams, @android.annotation.NonNull final android.content.IntentSender intentSender, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(sessionParams);
        java.util.Objects.requireNonNull(archivedPackageParcel);
        java.util.Objects.requireNonNull(intentSender);
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(userHandle);
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("Requested archived install of package %s for user %s.", new java.lang.Object[]{archivedPackageParcel.packageName, java.lang.Integer.valueOf(userHandle.getIdentifier())}));
        int callingUid = android.os.Binder.getCallingUid();
        final int identifier = userHandle.getIdentifier();
        this.mPm.snapshotComputer().enforceCrossUserPermission(callingUid, identifier, true, true, "installPackageArchived");
        if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
            throw new java.lang.SecurityException("You need the com.android.permission.INSTALL_PACKAGES permission to request archived package install");
        }
        sessionParams.installFlags |= 134217728;
        if (sessionParams.dataLoaderParams != null) {
            throw new java.lang.IllegalArgumentException("Incompatible session param: dataLoaderParams has to be null");
        }
        sessionParams.setDataLoaderParams(com.android.server.pm.PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(null));
        final com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forArchived = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forArchived(archivedPackageParcel);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.pm.PackageInstallerService.this.lambda$installPackageArchived$7(sessionParams, str, identifier, forArchived, intentSender, archivedPackageParcel);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$installPackageArchived$7(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, int i, com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata metadata, android.content.IntentSender intentSender, android.content.pm.ArchivedPackageParcel archivedPackageParcel) throws java.lang.Exception {
        com.android.server.pm.PackageInstallerSession packageInstallerSession = null;
        try {
            try {
                packageInstallerSession = openSessionInternal(createSessionInternal(sessionParams, str, null, android.os.Binder.getCallingUid(), i));
                packageInstallerSession.addFile(0, "base", 0L, metadata.toByteArray(), null);
                packageInstallerSession.commit(intentSender, false);
                android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("Installed archived app %s.", new java.lang.Object[]{archivedPackageParcel.packageName}));
                packageInstallerSession.close();
            } catch (java.io.IOException e) {
                throw android.util.ExceptionUtils.wrap(e);
            }
        } catch (java.lang.Throwable th) {
            if (packageInstallerSession != null) {
                packageInstallerSession.close();
            }
            throw th;
        }
    }

    public void reportUnarchivalStatus(int i, int i2, long j, @android.annotation.Nullable android.app.PendingIntent pendingIntent, @android.annotation.NonNull android.os.UserHandle userHandle) {
        verifyReportUnarchiveStatusInput(i2, j, pendingIntent, userHandle);
        int identifier = userHandle.getIdentifier();
        int callingUid = android.os.Binder.getCallingUid();
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerSession packageInstallerSession = this.mSessions.get(i);
                if (packageInstallerSession == null || packageInstallerSession.userId != identifier || packageInstallerSession.params.appPackageName == null) {
                    throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("No valid session with unarchival ID %s found for user %s.", new java.lang.Object[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(identifier)})));
                }
                if (!isCallingUidOwner(packageInstallerSession)) {
                    throw new java.lang.SecurityException(android.text.TextUtils.formatSimple("The caller UID %s does not have access to the session with unarchiveId %d.", new java.lang.Object[]{java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(i)}));
                }
                packageInstallerSession.reportUnarchivalStatus(i2, i, j, pendingIntent);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void verifyReportUnarchiveStatusInput(int i, long j, @android.annotation.Nullable android.app.PendingIntent pendingIntent, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle);
        if (i == 1) {
            java.util.Objects.requireNonNull(pendingIntent);
        }
        if (i == 2 && j <= 0) {
            throw new java.lang.IllegalStateException("Insufficient storage error set, but requiredStorageBytes unspecified.");
        }
        if (i != 2 && j > 0) {
            throw new java.lang.IllegalStateException(android.text.TextUtils.formatSimple("requiredStorageBytes set, but error is %s.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
        }
        if (!java.util.List.of(0, 1, 2, 3, 4, 5, 100).contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalStateException("Invalid status code passed " + i);
        }
    }

    private static int getSessionCount(android.util.SparseArray<com.android.server.pm.PackageInstallerSession> sparseArray, int i) {
        int size = sparseArray.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (sparseArray.valueAt(i3).getInstallerUid() == i) {
                i2++;
            }
        }
        return i2;
    }

    private boolean isCallingUidOwner(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 0) {
            return true;
        }
        return packageInstallerSession != null && callingUid == packageInstallerSession.getInstallerUid();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldFilterSession(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        com.android.server.pm.PackageInstallerSession session = getSession(i2);
        return (session == null || i == session.getInstallerUid() || computer.canQueryPackage(i, session.getPackageName())) ? false : true;
    }

    static class PackageDeleteObserverAdapter extends android.app.PackageDeleteObserver {
        private final android.content.Context mContext;
        private final int mFlags;
        private final android.app.Notification mNotification;

        @android.annotation.Nullable
        private final com.android.server.pm.PackageArchiver mPackageArchiver;
        private final java.lang.String mPackageName;
        private final android.content.IntentSender mTarget;
        private final int mUserId;

        PackageDeleteObserverAdapter(android.content.Context context, android.content.IntentSender intentSender, java.lang.String str, boolean z, int i) {
            this(context, intentSender, str, z, i, null, 0);
        }

        PackageDeleteObserverAdapter(android.content.Context context, android.content.IntentSender intentSender, java.lang.String str, boolean z, int i, com.android.server.pm.PackageArchiver packageArchiver, int i2) {
            this.mContext = context;
            this.mTarget = intentSender;
            this.mPackageName = str;
            if (z) {
                this.mNotification = com.android.server.pm.PackageInstallerService.buildSuccessNotification(this.mContext, getDeviceOwnerDeletedPackageMsg(), str, i);
            } else {
                this.mNotification = null;
            }
            this.mUserId = i;
            this.mPackageArchiver = packageArchiver;
            this.mFlags = i2;
        }

        private java.lang.String getDeviceOwnerDeletedPackageMsg() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString("Core.PACKAGE_DELETED_BY_DO", new java.util.function.Supplier() { // from class: com.android.server.pm.PackageInstallerService$PackageDeleteObserverAdapter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.String lambda$getDeviceOwnerDeletedPackageMsg$0;
                        lambda$getDeviceOwnerDeletedPackageMsg$0 = com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter.this.lambda$getDeviceOwnerDeletedPackageMsg$0();
                        return lambda$getDeviceOwnerDeletedPackageMsg$0;
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getDeviceOwnerDeletedPackageMsg$0() {
            return this.mContext.getString(android.R.string.notification_history_title_placeholder);
        }

        public void onUserActionRequired(android.content.Intent intent) {
            if (this.mTarget == null) {
                return;
            }
            android.content.Intent intent2 = new android.content.Intent();
            intent2.putExtra("android.content.pm.extra.PACKAGE_NAME", this.mPackageName);
            intent2.putExtra("android.content.pm.extra.STATUS", -1);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            try {
                android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                this.mTarget.sendIntent(this.mContext, 0, intent2, null, null, null, makeBasic.toBundle());
            } catch (android.content.IntentSender.SendIntentException e) {
            }
        }

        public void onPackageDeleted(java.lang.String str, int i, java.lang.String str2) {
            if (1 == i && this.mNotification != null) {
                ((android.app.NotificationManager) this.mContext.getSystemService("notification")).notify(str, 21, this.mNotification);
            }
            if (this.mPackageArchiver != null && 1 != i && (this.mFlags & 16) != 0) {
                this.mPackageArchiver.clearArchiveState(this.mPackageName, this.mUserId);
            }
            if (this.mTarget == null) {
                return;
            }
            android.content.Intent intent = new android.content.Intent();
            intent.putExtra("android.content.pm.extra.PACKAGE_NAME", this.mPackageName);
            intent.putExtra("android.content.pm.extra.STATUS", android.content.pm.PackageManager.deleteStatusToPublicStatus(i));
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", android.content.pm.PackageManager.deleteStatusToString(i, str2));
            intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i);
            try {
                android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                this.mTarget.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
            } catch (android.content.IntentSender.SendIntentException e) {
            }
        }
    }

    static android.app.Notification buildSuccessNotification(android.content.Context context, java.lang.String str, java.lang.String str2, int i) {
        android.content.pm.PackageInfo packageInfo;
        try {
            packageInfo = android.app.AppGlobals.getPackageManager().getPackageInfo(str2, 67108864L, i);
        } catch (android.os.RemoteException e) {
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            android.util.Slog.w(TAG, "Notification not built for package: " + str2);
            return null;
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        return new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(android.R.drawable.ic_camera_blocked).setColor(context.getResources().getColor(android.R.color.system_notification_accent_color)).setContentTitle(packageInfo.applicationInfo.loadLabel(packageManager)).setContentText(str).setStyle(new android.app.Notification.BigTextStyle().bigText(str)).setLargeIcon(com.android.internal.util.ImageUtils.buildScaledBitmap(packageInfo.applicationInfo.loadIcon(packageManager), context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width), context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height))).build();
    }

    public static <E> android.util.ArraySet<E> newArraySet(E... eArr) {
        android.util.ArraySet<E> arraySet = new android.util.ArraySet<>();
        if (eArr != null) {
            arraySet.ensureCapacity(eArr.length);
            java.util.Collections.addAll(arraySet, eArr);
        }
        return arraySet;
    }

    private static final class BroadcastCookie {
        public final int callingUid;
        public final java.util.function.IntPredicate userCheck;

        BroadcastCookie(int i, java.util.function.IntPredicate intPredicate) {
            this.callingUid = i;
            this.userCheck = intPredicate;
        }
    }

    private class Callbacks extends android.os.Handler {
        private static final int MSG_SESSION_ACTIVE_CHANGED = 3;
        private static final int MSG_SESSION_BADGING_CHANGED = 2;
        private static final int MSG_SESSION_CREATED = 1;
        private static final int MSG_SESSION_FINISHED = 5;
        private static final int MSG_SESSION_PROGRESS_CHANGED = 4;
        private final android.os.RemoteCallbackList<android.content.pm.IPackageInstallerCallback> mCallbacks;

        public Callbacks(android.os.Looper looper) {
            super(looper);
            this.mCallbacks = new android.os.RemoteCallbackList<>();
        }

        public void register(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, com.android.server.pm.PackageInstallerService.BroadcastCookie broadcastCookie) {
            this.mCallbacks.register(iPackageInstallerCallback, broadcastCookie);
        }

        public void unregister(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) {
            this.mCallbacks.unregister(iPackageInstallerCallback);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.arg1;
            int i2 = message.arg2;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageInstallerService.this.mPm.snapshotComputer();
            for (int i3 = 0; i3 < beginBroadcast; i3++) {
                android.content.pm.IPackageInstallerCallback broadcastItem = this.mCallbacks.getBroadcastItem(i3);
                com.android.server.pm.PackageInstallerService.BroadcastCookie broadcastCookie = (com.android.server.pm.PackageInstallerService.BroadcastCookie) this.mCallbacks.getBroadcastCookie(i3);
                if (broadcastCookie.userCheck.test(i2) && !com.android.server.pm.PackageInstallerService.this.shouldFilterSession(snapshotComputer, broadcastCookie.callingUid, i)) {
                    try {
                        invokeCallback(broadcastItem, message);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
            this.mCallbacks.finishBroadcast();
        }

        private void invokeCallback(android.content.pm.IPackageInstallerCallback iPackageInstallerCallback, android.os.Message message) throws android.os.RemoteException {
            int i = message.arg1;
            switch (message.what) {
                case 1:
                    iPackageInstallerCallback.onSessionCreated(i);
                    break;
                case 2:
                    iPackageInstallerCallback.onSessionBadgingChanged(i);
                    break;
                case 3:
                    iPackageInstallerCallback.onSessionActiveChanged(i, ((java.lang.Boolean) message.obj).booleanValue());
                    break;
                case 4:
                    iPackageInstallerCallback.onSessionProgressChanged(i, ((java.lang.Float) message.obj).floatValue());
                    break;
                case 5:
                    iPackageInstallerCallback.onSessionFinished(i, ((java.lang.Boolean) message.obj).booleanValue());
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySessionCreated(int i, int i2) {
            obtainMessage(1, i, i2).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySessionBadgingChanged(int i, int i2) {
            obtainMessage(2, i, i2).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySessionActiveChanged(int i, int i2, boolean z) {
            obtainMessage(3, i, i2, java.lang.Boolean.valueOf(z)).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySessionProgressChanged(int i, int i2, float f) {
            obtainMessage(4, i, i2, java.lang.Float.valueOf(f)).sendToTarget();
        }

        public void notifySessionFinished(int i, int i2, boolean z) {
            obtainMessage(5, i, i2, java.lang.Boolean.valueOf(z)).sendToTarget();
        }
    }

    static class ParentChildSessionMap {
        private final java.util.Comparator<com.android.server.pm.PackageInstallerSession> mSessionCreationComparator = java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.pm.PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                long lambda$new$0;
                lambda$new$0 = com.android.server.pm.PackageInstallerService.ParentChildSessionMap.lambda$new$0((com.android.server.pm.PackageInstallerSession) obj);
                return lambda$new$0;
            }
        }).thenComparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.pm.PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int lambda$new$1;
                lambda$new$1 = com.android.server.pm.PackageInstallerService.ParentChildSessionMap.lambda$new$1((com.android.server.pm.PackageInstallerSession) obj);
                return lambda$new$1;
            }
        });
        private final java.util.TreeMap<com.android.server.pm.PackageInstallerSession, java.util.TreeSet<com.android.server.pm.PackageInstallerSession>> mSessionMap = new java.util.TreeMap<>(this.mSessionCreationComparator);

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ long lambda$new$0(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            if (packageInstallerSession != null) {
                return packageInstallerSession.createdMillis;
            }
            return -1L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$new$1(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            if (packageInstallerSession != null) {
                return packageInstallerSession.sessionId;
            }
            return -1;
        }

        ParentChildSessionMap() {
        }

        boolean containsSession() {
            return !this.mSessionMap.isEmpty();
        }

        private void addParentSession(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            if (!this.mSessionMap.containsKey(packageInstallerSession)) {
                this.mSessionMap.put(packageInstallerSession, new java.util.TreeSet<>(this.mSessionCreationComparator));
            }
        }

        private void addChildSession(com.android.server.pm.PackageInstallerSession packageInstallerSession, com.android.server.pm.PackageInstallerSession packageInstallerSession2) {
            addParentSession(packageInstallerSession2);
            this.mSessionMap.get(packageInstallerSession2).add(packageInstallerSession);
        }

        void addSession(com.android.server.pm.PackageInstallerSession packageInstallerSession, com.android.server.pm.PackageInstallerSession packageInstallerSession2) {
            if (packageInstallerSession.hasParentSessionId()) {
                addChildSession(packageInstallerSession, packageInstallerSession2);
            } else {
                addParentSession(packageInstallerSession);
            }
        }

        void dump(java.lang.String str, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(str + " install sessions:");
            indentingPrintWriter.increaseIndent();
            for (java.util.Map.Entry<com.android.server.pm.PackageInstallerSession, java.util.TreeSet<com.android.server.pm.PackageInstallerSession>> entry : this.mSessionMap.entrySet()) {
                com.android.server.pm.PackageInstallerSession key = entry.getKey();
                if (key != null) {
                    indentingPrintWriter.print(str + " ");
                    key.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                }
                java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = entry.getValue().iterator();
                while (it.hasNext()) {
                    com.android.server.pm.PackageInstallerSession next = it.next();
                    indentingPrintWriter.print(str + " Child ");
                    next.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        com.android.server.pm.PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            try {
                com.android.server.pm.PackageInstallerService.ParentChildSessionMap parentChildSessionMap = new com.android.server.pm.PackageInstallerService.ParentChildSessionMap();
                com.android.server.pm.PackageInstallerService.ParentChildSessionMap parentChildSessionMap2 = new com.android.server.pm.PackageInstallerService.ParentChildSessionMap();
                com.android.server.pm.PackageInstallerService.ParentChildSessionMap parentChildSessionMap3 = new com.android.server.pm.PackageInstallerService.ParentChildSessionMap();
                int size = this.mSessions.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i);
                    if (valueAt.hasParentSessionId()) {
                        packageInstallerSession = getSession(valueAt.getParentSessionId());
                    } else {
                        packageInstallerSession = valueAt;
                    }
                    if (packageInstallerSession == null) {
                        parentChildSessionMap2.addSession(valueAt, packageInstallerSession);
                    } else if (packageInstallerSession.isStagedAndInTerminalState()) {
                        parentChildSessionMap3.addSession(valueAt, packageInstallerSession);
                    } else {
                        parentChildSessionMap.addSession(valueAt, packageInstallerSession);
                    }
                }
                parentChildSessionMap.dump("Active", indentingPrintWriter);
                if (parentChildSessionMap2.containsSession()) {
                    parentChildSessionMap2.dump("Orphaned", indentingPrintWriter);
                }
                parentChildSessionMap3.dump("Finalized", indentingPrintWriter);
                indentingPrintWriter.println("Historical install sessions:");
                indentingPrintWriter.increaseIndent();
                int size2 = this.mHistoricalSessions.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    this.mHistoricalSessions.get(i2).dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Legacy install sessions:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(this.mLegacySessions.toString());
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mSilentUpdatePolicy.dump(indentingPrintWriter);
        this.mGentleUpdateHelper.dump(indentingPrintWriter);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public class InternalCallback {
        public InternalCallback() {
        }

        public void onSessionBadgingChanged(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            com.android.server.pm.PackageInstallerService.this.mCallbacks.notifySessionBadgingChanged(packageInstallerSession.sessionId, packageInstallerSession.userId);
            com.android.server.pm.PackageInstallerService.this.mSettingsWriteRequest.schedule();
        }

        public void onSessionActiveChanged(com.android.server.pm.PackageInstallerSession packageInstallerSession, boolean z) {
            com.android.server.pm.PackageInstallerService.this.mCallbacks.notifySessionActiveChanged(packageInstallerSession.sessionId, packageInstallerSession.userId, z);
        }

        public void onSessionProgressChanged(com.android.server.pm.PackageInstallerSession packageInstallerSession, float f) {
            com.android.server.pm.PackageInstallerService.this.mCallbacks.notifySessionProgressChanged(packageInstallerSession.sessionId, packageInstallerSession.userId, f);
        }

        public void onSessionChanged(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            packageInstallerSession.markUpdated();
            com.android.server.pm.PackageInstallerService.this.mSettingsWriteRequest.schedule();
            if (com.android.server.pm.PackageInstallerService.this.mOkToSendBroadcasts && !packageInstallerSession.isDestroyed() && packageInstallerSession.isStaged()) {
                com.android.server.pm.PackageInstallerService.this.sendSessionUpdatedBroadcast(packageInstallerSession.generateInfoForCaller(false, 1000), packageInstallerSession.userId);
            }
        }

        public void onSessionFinished(final com.android.server.pm.PackageInstallerSession packageInstallerSession, final boolean z) {
            if (z) {
                com.android.server.pm.PackageInstallerService.this.mCallbacks.notifySessionFinished(packageInstallerSession.sessionId, packageInstallerSession.userId, z);
            }
            com.android.server.pm.PackageInstallerService.this.mInstallHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageInstallerService.InternalCallback.1
                /* JADX WARN: Removed duplicated region for block: B:20:0x004c A[Catch: all -> 0x0047, TryCatch #0 {all -> 0x0047, blocks: (B:9:0x0024, B:11:0x002c, B:13:0x0034, B:15:0x003c, B:20:0x004c, B:22:0x0055, B:24:0x0067, B:25:0x006a, B:26:0x0075), top: B:8:0x0024 }] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    boolean z2;
                    if (packageInstallerSession.isStaged() && !z) {
                        com.android.server.pm.PackageInstallerService.this.mStagingManager.abortSession(packageInstallerSession.mStagedSession);
                    }
                    synchronized (com.android.server.pm.PackageInstallerService.this.mSessions) {
                        try {
                            if (!packageInstallerSession.hasParentSessionId()) {
                                if (packageInstallerSession.isStaged() && !packageInstallerSession.isDestroyed() && packageInstallerSession.isCommitted()) {
                                    z2 = false;
                                    if (z2) {
                                        com.android.server.pm.PackageInstallerService.this.removeActiveSession(packageInstallerSession);
                                    }
                                }
                                z2 = true;
                                if (z2) {
                                }
                            }
                            java.io.File buildAppIconFile = com.android.server.pm.PackageInstallerService.this.buildAppIconFile(packageInstallerSession.sessionId);
                            if (buildAppIconFile.exists()) {
                                buildAppIconFile.delete();
                            }
                            com.android.server.pm.PackageInstallerService.this.mSettingsWriteRequest.runNow();
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    if (!z) {
                        com.android.server.pm.PackageInstallerService.this.mCallbacks.notifySessionFinished(packageInstallerSession.sessionId, packageInstallerSession.userId, z);
                    }
                }
            });
        }

        public void onSessionPrepared(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            com.android.server.pm.PackageInstallerService.this.mSettingsWriteRequest.schedule();
        }

        public void onSessionSealedBlocking(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            com.android.server.pm.PackageInstallerService.this.mSettingsWriteRequest.runNow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSessionUpdatedBroadcast(android.content.pm.PackageInstaller.SessionInfo sessionInfo, int i) {
        if (android.text.TextUtils.isEmpty(sessionInfo.installerPackageName)) {
            return;
        }
        this.mContext.sendBroadcastAsUser(new android.content.Intent("android.content.pm.action.SESSION_UPDATED").putExtra("android.content.pm.extra.SESSION", sessionInfo).setPackage(sessionInfo.installerPackageName), android.os.UserHandle.of(i));
    }

    void onInstallerPackageDeleted(int i, int i2) {
        synchronized (this.mSessions) {
            for (int i3 = 0; i3 < this.mSessions.size(); i3++) {
                try {
                    com.android.server.pm.PackageInstallerSession valueAt = this.mSessions.valueAt(i3);
                    if (matchesInstaller(valueAt, i, i2)) {
                        if (valueAt.hasParentSessionId()) {
                            valueAt = this.mSessions.get(valueAt.getParentSessionId());
                        }
                        if (valueAt != null && matchesInstaller(valueAt, i, i2) && !valueAt.isDestroyed()) {
                            valueAt.abandon();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private boolean matchesInstaller(com.android.server.pm.PackageInstallerSession packageInstallerSession, int i, int i2) {
        int installerUid = packageInstallerSession.getInstallerUid();
        return i == -1 ? android.os.UserHandle.getAppId(installerUid) == i : android.os.UserHandle.getUid(i2, i) == installerUid;
    }
}
