package com.android.server.pm;

/* loaded from: classes2.dex */
public class Installer extends com.android.server.SystemService {
    private static final long CONNECT_RETRY_DELAY_MS = 1000;
    private static final long CONNECT_WAIT_MS = 10000;
    public static final int DEXOPT_BOOTCOMPLETE = 8;
    public static final int DEXOPT_DEBUGGABLE = 4;
    public static final int DEXOPT_ENABLE_HIDDEN_API_CHECKS = 1024;
    public static final int DEXOPT_FORCE = 64;
    public static final int DEXOPT_FOR_RESTORE = 8192;
    public static final int DEXOPT_GENERATE_APP_IMAGE = 4096;
    public static final int DEXOPT_GENERATE_COMPACT_DEX = 2048;
    public static final int DEXOPT_IDLE_BACKGROUND_JOB = 512;
    public static final int DEXOPT_PROFILE_GUIDED = 16;
    public static final int DEXOPT_PUBLIC = 2;
    public static final int DEXOPT_SECONDARY_DEX = 32;
    public static final int DEXOPT_STORAGE_CE = 128;
    public static final int DEXOPT_STORAGE_DE = 256;
    public static final int FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES = 131072;
    public static final int FLAG_CLEAR_CACHE_ONLY = 16;
    public static final int FLAG_CLEAR_CODE_CACHE_ONLY = 32;
    public static final int FLAG_FORCE = 8192;
    public static final int FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES = 2048;
    public static final int FLAG_FREE_CACHE_NOOP = 1024;
    public static final int FLAG_FREE_CACHE_V2 = 256;
    public static final int FLAG_FREE_CACHE_V2_DEFY_QUOTA = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_STORAGE_SDK = 8;
    public static final int FLAG_USE_QUOTA = 4096;
    public static final int ODEX_IS_PRIVATE = 2;
    public static final int ODEX_IS_PUBLIC = 1;
    public static final int ODEX_NOT_FOUND = 0;
    public static final int PROFILE_ANALYSIS_DONT_OPTIMIZE_EMPTY_PROFILES = 3;
    public static final int PROFILE_ANALYSIS_DONT_OPTIMIZE_SMALL_DELTA = 2;
    public static final int PROFILE_ANALYSIS_OPTIMIZE = 1;
    private static final java.lang.String TAG = "Installer";
    private volatile boolean mDeferSetFirstBoot;
    private volatile android.os.IInstalld mInstalld;
    private volatile java.util.concurrent.CountDownLatch mInstalldLatch;
    private final boolean mIsolated;
    private volatile java.lang.Object mWarnIfHeld;

    public Installer(android.content.Context context) {
        this(context, false);
    }

    public Installer(android.content.Context context, boolean z) {
        super(context);
        this.mInstalld = null;
        this.mInstalldLatch = new java.util.concurrent.CountDownLatch(1);
        this.mIsolated = z;
    }

    public void setWarnIfHeld(java.lang.Object obj) {
        this.mWarnIfHeld = obj;
    }

    public boolean isIsolated() {
        return this.mIsolated;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (this.mIsolated) {
            this.mInstalld = null;
            this.mInstalldLatch.countDown();
        } else {
            connect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect() {
        android.os.IBinder service = android.os.ServiceManager.getService("installd");
        if (service != null) {
            try {
                service.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.pm.Installer$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.pm.Installer.this.lambda$connect$0();
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                service = null;
            }
        }
        if (service != null) {
            this.mInstalld = android.os.IInstalld.Stub.asInterface(service);
            this.mInstalldLatch.countDown();
            try {
                invalidateMounts();
                executeDeferredActions();
                return;
            } catch (com.android.server.pm.Installer.InstallerException e2) {
                return;
            }
        }
        android.util.Slog.w(TAG, "installd not found; trying again");
        com.android.internal.os.BackgroundThread.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.Installer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.Installer.this.connect();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connect$0() {
        android.util.Slog.w(TAG, "installd died; reconnecting");
        this.mInstalldLatch = new java.util.concurrent.CountDownLatch(1);
        connect();
    }

    private void executeDeferredActions() throws com.android.server.pm.Installer.InstallerException {
        if (this.mDeferSetFirstBoot) {
            setFirstBoot();
        }
    }

    private boolean checkBeforeRemote() throws com.android.server.pm.Installer.InstallerException {
        if (this.mWarnIfHeld != null && java.lang.Thread.holdsLock(this.mWarnIfHeld)) {
            android.util.Slog.wtf(TAG, "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding 0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mWarnIfHeld)), new java.lang.Throwable());
        }
        if (this.mIsolated) {
            android.util.Slog.i(TAG, "Ignoring request because this installer is isolated");
            return false;
        }
        try {
            if (!this.mInstalldLatch.await(10000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new com.android.server.pm.Installer.InstallerException("time out waiting for the installer to be ready");
            }
            return true;
        } catch (java.lang.InterruptedException e) {
            return true;
        }
    }

    static android.os.CreateAppDataArgs buildCreateAppDataArgs(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int i4, boolean z) {
        android.os.CreateAppDataArgs createAppDataArgs = new android.os.CreateAppDataArgs();
        createAppDataArgs.uuid = str;
        createAppDataArgs.packageName = str2;
        createAppDataArgs.userId = i;
        createAppDataArgs.flags = i2;
        if (z) {
            createAppDataArgs.flags |= 8;
        }
        createAppDataArgs.appId = i3;
        createAppDataArgs.seInfo = str3;
        createAppDataArgs.targetSdkVersion = i4;
        return createAppDataArgs;
    }

    private static android.os.CreateAppDataResult buildPlaceholderCreateAppDataResult() {
        android.os.CreateAppDataResult createAppDataResult = new android.os.CreateAppDataResult();
        createAppDataResult.ceDataInode = -1L;
        createAppDataResult.deDataInode = -1L;
        createAppDataResult.exceptionCode = 0;
        createAppDataResult.exceptionMessage = null;
        return createAppDataResult;
    }

    static android.os.ReconcileSdkDataArgs buildReconcileSdkDataArgs(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i, int i2, java.lang.String str3, int i3) {
        android.os.ReconcileSdkDataArgs reconcileSdkDataArgs = new android.os.ReconcileSdkDataArgs();
        reconcileSdkDataArgs.uuid = str;
        reconcileSdkDataArgs.packageName = str2;
        reconcileSdkDataArgs.subDirNames = list;
        reconcileSdkDataArgs.userId = i;
        reconcileSdkDataArgs.appId = i2;
        reconcileSdkDataArgs.previousAppId = 0;
        reconcileSdkDataArgs.seInfo = str3;
        reconcileSdkDataArgs.flags = i3;
        return reconcileSdkDataArgs;
    }

    @android.annotation.NonNull
    public android.os.CreateAppDataResult createAppData(@android.annotation.NonNull android.os.CreateAppDataArgs createAppDataArgs) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return buildPlaceholderCreateAppDataResult();
        }
        createAppDataArgs.previousAppId = 0;
        try {
            return this.mInstalld.createAppData(createAppDataArgs);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    @android.annotation.NonNull
    public android.os.CreateAppDataResult[] createAppDataBatched(@android.annotation.NonNull android.os.CreateAppDataArgs[] createAppDataArgsArr) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            android.os.CreateAppDataResult[] createAppDataResultArr = new android.os.CreateAppDataResult[createAppDataArgsArr.length];
            java.util.Arrays.fill(createAppDataResultArr, buildPlaceholderCreateAppDataResult());
            return createAppDataResultArr;
        }
        for (android.os.CreateAppDataArgs createAppDataArgs : createAppDataArgsArr) {
            createAppDataArgs.previousAppId = 0;
        }
        try {
            return this.mInstalld.createAppDataBatched(createAppDataArgsArr);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    void reconcileSdkData(@android.annotation.NonNull android.os.ReconcileSdkDataArgs reconcileSdkDataArgs) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return;
        }
        try {
            this.mInstalld.reconcileSdkData(reconcileSdkDataArgs);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void setFirstBoot() throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return;
        }
        try {
            if (this.mInstalld != null) {
                this.mInstalld.setFirstBoot();
            } else {
                this.mDeferSetFirstBoot = true;
            }
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public static class Batch {
        private static final int CREATE_APP_DATA_BATCH_SIZE = 256;
        private boolean mExecuted;
        private final java.util.List<android.os.CreateAppDataArgs> mArgs = new java.util.ArrayList();
        private final java.util.List<java.util.concurrent.CompletableFuture<android.os.CreateAppDataResult>> mFutures = new java.util.ArrayList();

        @android.annotation.NonNull
        public synchronized java.util.concurrent.CompletableFuture<android.os.CreateAppDataResult> createAppData(android.os.CreateAppDataArgs createAppDataArgs) {
            java.util.concurrent.CompletableFuture<android.os.CreateAppDataResult> completableFuture;
            if (this.mExecuted) {
                throw new java.lang.IllegalStateException();
            }
            completableFuture = new java.util.concurrent.CompletableFuture<>();
            this.mArgs.add(createAppDataArgs);
            this.mFutures.add(completableFuture);
            return completableFuture;
        }

        public synchronized void execute(@android.annotation.NonNull com.android.server.pm.Installer installer) throws com.android.server.pm.Installer.InstallerException {
            try {
                if (this.mExecuted) {
                    throw new java.lang.IllegalStateException();
                }
                this.mExecuted = true;
                int size = this.mArgs.size();
                for (int i = 0; i < size; i += 256) {
                    int min = java.lang.Math.min(size - i, 256);
                    android.os.CreateAppDataArgs[] createAppDataArgsArr = new android.os.CreateAppDataArgs[min];
                    for (int i2 = 0; i2 < min; i2++) {
                        createAppDataArgsArr[i2] = this.mArgs.get(i + i2);
                    }
                    android.os.CreateAppDataResult[] createAppDataBatched = installer.createAppDataBatched(createAppDataArgsArr);
                    for (int i3 = 0; i3 < createAppDataBatched.length; i3++) {
                        android.os.CreateAppDataResult createAppDataResult = createAppDataBatched[i3];
                        java.util.concurrent.CompletableFuture<android.os.CreateAppDataResult> completableFuture = this.mFutures.get(i + i3);
                        if (createAppDataResult.exceptionCode == 0) {
                            completableFuture.complete(createAppDataResult);
                        } else {
                            completableFuture.completeExceptionally(new com.android.server.pm.Installer.InstallerException(createAppDataResult.exceptionMessage));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void restoreconAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.restoreconAppData(str, str2, i, i2, i3, str3);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void migrateAppData(java.lang.String str, java.lang.String str2, int i, int i2) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.migrateAppData(str, str2, i, i2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void clearAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.clearAppData(str, str2, i, i2, j);
                java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.INSTALLER_CLEAR_APP_DATA_CALLER, java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), str2, java.lang.Integer.valueOf(i2));
                for (int i3 = 2; i3 < stackTrace.length; i3++) {
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.INSTALLER_CLEAR_APP_DATA_CALL_STACK, stackTrace[i3].getMethodName(), stackTrace[i3].getClassName(), stackTrace[i3].getFileName(), java.lang.Integer.valueOf(stackTrace[i3].getLineNumber()));
                }
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void destroyAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.destroyAppData(str, str2, i, i2, j);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void fixupAppData(java.lang.String str, int i) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.fixupAppData(str, i);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void cleanupInvalidPackageDirs(java.lang.String str, int i, int i2) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.cleanupInvalidPackageDirs(str, i, i2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void moveCompleteApp(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2, java.lang.String str5) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.moveCompleteApp(str, str2, str3, i, str4, i2, str5);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void getAppSize(java.lang.String str, java.lang.String[] strArr, int i, int i2, int i3, long[] jArr, java.lang.String[] strArr2, android.content.pm.PackageStats packageStats) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            if (strArr2 != null) {
                for (java.lang.String str2 : strArr2) {
                    dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str2);
                }
            }
            try {
                long[] appSize = this.mInstalld.getAppSize(str, strArr, i, i2, i3, jArr, strArr2);
                packageStats.codeSize += appSize[0];
                packageStats.dataSize += appSize[1];
                packageStats.cacheSize += appSize[2];
                packageStats.externalCodeSize += appSize[3];
                packageStats.externalDataSize += appSize[4];
                packageStats.externalCacheSize += appSize[5];
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void getUserSize(java.lang.String str, int i, int i2, int[] iArr, android.content.pm.PackageStats packageStats) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                long[] userSize = this.mInstalld.getUserSize(str, i, i2, iArr);
                packageStats.codeSize += userSize[0];
                packageStats.dataSize += userSize[1];
                packageStats.cacheSize += userSize[2];
                packageStats.externalCodeSize += userSize[3];
                packageStats.externalDataSize += userSize[4];
                packageStats.externalCacheSize += userSize[5];
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public long[] getExternalSize(java.lang.String str, int i, int i2, int[] iArr) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return new long[6];
        }
        try {
            return this.mInstalld.getExternalSize(str, i, i2, iArr);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    @android.annotation.Nullable
    public android.os.storage.CrateMetadata[] getAppCrates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String[] strArr, int i) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return null;
        }
        try {
            return this.mInstalld.getAppCrates(str, strArr, i);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    @android.annotation.Nullable
    public android.os.storage.CrateMetadata[] getUserCrates(java.lang.String str, int i) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return null;
        }
        try {
            return this.mInstalld.getUserCrates(str, i);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void setAppQuota(java.lang.String str, int i, int i2, long j) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.setAppQuota(str, i, i2, j);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public boolean dexopt(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, @android.annotation.Nullable java.lang.String str4, int i3, java.lang.String str5, @android.annotation.Nullable java.lang.String str6, @android.annotation.Nullable java.lang.String str7, @android.annotation.Nullable java.lang.String str8, boolean z, int i4, @android.annotation.Nullable java.lang.String str9, @android.annotation.Nullable java.lang.String str10, @android.annotation.Nullable java.lang.String str11) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        assertValidInstructionSet(str3);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str10);
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            return this.mInstalld.dexopt(str, i, str2, str3, i2, str4, i3, str5, str6, str7, str8, z, i4, str9, str10, str11);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void controlDexOptBlocking(boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        try {
            this.mInstalld.controlDexOptBlocking(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "blockDexOpt failed", e);
        }
    }

    public int mergeProfiles(int i, java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return 2;
        }
        try {
            return this.mInstalld.mergeProfiles(i, str, str2);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean dumpProfiles(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return false;
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str3);
        try {
            return this.mInstalld.dumpProfiles(i, str, str2, str3, z);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean copySystemProfile(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            return this.mInstalld.copySystemProfile(str, i, str2, str3);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void rmdex(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        assertValidInstructionSet(str2);
        if (checkBeforeRemote()) {
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str);
            try {
                this.mInstalld.rmdex(str, str2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void rmPackageDir(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str2);
            try {
                this.mInstalld.rmPackageDir(str, str2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void clearAppProfiles(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.clearAppProfiles(str, str2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void destroyAppProfiles(java.lang.String str) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.destroyAppProfiles(str);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void deleteReferenceProfile(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.deleteReferenceProfile(str, str2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void createUserData(java.lang.String str, int i, int i2, int i3) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.createUserData(str, i, i2, i3);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void destroyUserData(java.lang.String str, int i, int i2) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.destroyUserData(str, i, i2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void freeCache(java.lang.String str, long j, int i) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.freeCache(str, j, i);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void linkNativeLibraryDirectory(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str3);
            try {
                this.mInstalld.linkNativeLibraryDirectory(str, str2, str3, i);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void createOatDir(java.lang.String str, java.lang.String str2, java.lang.String str3) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.createOatDir(str, str2, str3);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void linkFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str3);
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
            try {
                this.mInstalld.linkFile(str, str2, str3, str4);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void moveAb(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str2);
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
            try {
                this.mInstalld.moveAb(str, str2, str3, str4);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public long deleteOdex(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return -1L;
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str2);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
        try {
            return this.mInstalld.deleteOdex(str, str2, str3, str4);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean reconcileSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str3, int i2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        for (java.lang.String str4 : strArr) {
            assertValidInstructionSet(str4);
        }
        if (!checkBeforeRemote()) {
            return false;
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str);
        try {
            return this.mInstalld.reconcileSecondaryDexFile(str, str2, i, strArr, str3, i2);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public byte[] hashSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, @android.annotation.Nullable java.lang.String str3, int i2) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return new byte[0];
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str);
        try {
            return this.mInstalld.hashSecondaryDexFile(str, str2, i, str3, i2);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean createProfileSnapshot(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            return this.mInstalld.createProfileSnapshot(i, str, str2, str3);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void destroyProfileSnapshot(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.destroyProfileSnapshot(str, str2);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void invalidateMounts() throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.invalidateMounts();
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public boolean isQuotaSupported(java.lang.String str) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            return this.mInstalld.isQuotaSupported(str);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public void tryMountDataMirror(java.lang.String str) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.tryMountDataMirror(str);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public void onPrivateVolumeRemoved(java.lang.String str) throws com.android.server.pm.Installer.InstallerException {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.onPrivateVolumeRemoved(str);
            } catch (java.lang.Exception e) {
                throw com.android.server.pm.Installer.InstallerException.from(e);
            }
        }
    }

    public boolean prepareAppProfile(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return false;
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str3);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
        try {
            return this.mInstalld.prepareAppProfile(str, i, i2, str2, str3, str4);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean snapshotAppData(java.lang.String str, int i, int i2, int i3) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            this.mInstalld.snapshotAppData(null, str, i, i2, i3);
            return true;
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean restoreAppDataSnapshot(java.lang.String str, int i, java.lang.String str2, int i2, int i3, int i4) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            this.mInstalld.restoreAppDataSnapshot(null, str, i, str2, i2, i3, i4);
            return true;
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean destroyAppDataSnapshot(java.lang.String str, int i, int i2, int i3) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            this.mInstalld.destroyAppDataSnapshot(null, str, i, 0L, i2, i3);
            return true;
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean destroyCeSnapshotsNotSpecified(int i, int[] iArr) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            this.mInstalld.destroyCeSnapshotsNotSpecified(null, i, iArr);
            return true;
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public boolean migrateLegacyObbData() throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return false;
        }
        try {
            this.mInstalld.migrateLegacyObbData();
            return true;
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    private static void assertValidInstructionSet(java.lang.String str) throws com.android.server.pm.Installer.InstallerException {
        for (java.lang.String str2 : android.os.Build.SUPPORTED_ABIS) {
            if (dalvik.system.VMRuntime.getInstructionSet(str2).equals(str)) {
                return;
            }
        }
        throw new com.android.server.pm.Installer.InstallerException("Invalid instruction set: " + str);
    }

    public int getOdexVisibility(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.InstallerException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        checkLegacyDexoptDisabled();
        if (!checkBeforeRemote()) {
            return -1;
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str2);
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str4);
        try {
            return this.mInstalld.getOdexVisibility(str, str2, str3, str4);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            return null;
        }
        try {
            return this.mInstalld.createFsveritySetupAuthToken(parcelFileDescriptor, i);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.InstallerException {
        if (!checkBeforeRemote()) {
            throw new com.android.server.pm.Installer.InstallerException("fs-verity wasn't enabled with an isolated installer");
        }
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(str);
        try {
            return this.mInstalld.enableFsverity(iFsveritySetupAuthToken, str, str2);
        } catch (java.lang.Exception e) {
            throw com.android.server.pm.Installer.InstallerException.from(e);
        }
    }

    public static class InstallerException extends java.lang.Exception {
        public InstallerException(java.lang.String str) {
            super(str);
        }

        public static com.android.server.pm.Installer.InstallerException from(java.lang.Exception exc) throws com.android.server.pm.Installer.InstallerException {
            throw new com.android.server.pm.Installer.InstallerException(exc.toString());
        }
    }

    public static class LegacyDexoptDisabledException extends java.lang.Exception {
        public LegacyDexoptDisabledException() {
            super("Invalid call to legacy dexopt method while ART Service is in use.");
        }
    }

    public static void checkLegacyDexoptDisabled() throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            throw new com.android.server.pm.Installer.LegacyDexoptDisabledException();
        }
    }
}
