package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class ArtManagerService extends android.content.pm.dex.IArtManager.Stub {
    private static final java.lang.String BOOT_IMAGE_ANDROID_PACKAGE = "android";
    private static final java.lang.String BOOT_IMAGE_PROFILE_NAME = "android.prof";
    public static final java.lang.String DEXOPT_REASON_WITH_DEX_METADATA_ANNOTATION = "-dm";
    private static final int TRON_COMPILATION_FILTER_ASSUMED_VERIFIED = 2;
    private static final int TRON_COMPILATION_FILTER_ASSUMED_VERIFIED_IORAP = 15;
    private static final int TRON_COMPILATION_FILTER_ERROR = 0;
    private static final int TRON_COMPILATION_FILTER_EVERYTHING = 11;
    private static final int TRON_COMPILATION_FILTER_EVERYTHING_IORAP = 24;
    private static final int TRON_COMPILATION_FILTER_EVERYTHING_PROFILE = 10;
    private static final int TRON_COMPILATION_FILTER_EVERYTHING_PROFILE_IORAP = 23;
    private static final int TRON_COMPILATION_FILTER_EXTRACT = 3;
    private static final int TRON_COMPILATION_FILTER_EXTRACT_IORAP = 16;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_APK = 12;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_APK_FALLBACK = 13;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_APK_FALLBACK_IORAP = 26;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_APK_IORAP = 25;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_VDEX_FALLBACK = 14;
    private static final int TRON_COMPILATION_FILTER_FAKE_RUN_FROM_VDEX_FALLBACK_IORAP = 27;
    private static final int TRON_COMPILATION_FILTER_QUICKEN = 5;
    private static final int TRON_COMPILATION_FILTER_QUICKEN_IORAP = 18;
    private static final int TRON_COMPILATION_FILTER_SPACE = 7;
    private static final int TRON_COMPILATION_FILTER_SPACE_IORAP = 20;
    private static final int TRON_COMPILATION_FILTER_SPACE_PROFILE = 6;
    private static final int TRON_COMPILATION_FILTER_SPACE_PROFILE_IORAP = 19;
    private static final int TRON_COMPILATION_FILTER_SPEED = 9;
    private static final int TRON_COMPILATION_FILTER_SPEED_IORAP = 22;
    private static final int TRON_COMPILATION_FILTER_SPEED_PROFILE = 8;
    private static final int TRON_COMPILATION_FILTER_SPEED_PROFILE_IORAP = 21;
    private static final int TRON_COMPILATION_FILTER_UNKNOWN = 1;
    private static final int TRON_COMPILATION_FILTER_VERIFY = 4;
    private static final int TRON_COMPILATION_FILTER_VERIFY_IORAP = 17;
    private static final int TRON_COMPILATION_REASON_AB_OTA = 6;
    private static final int TRON_COMPILATION_REASON_BG_DEXOPT = 5;
    private static final int TRON_COMPILATION_REASON_BOOT_AFTER_MAINLINE_UPDATE = 25;
    private static final int TRON_COMPILATION_REASON_BOOT_AFTER_OTA = 20;
    private static final int TRON_COMPILATION_REASON_BOOT_DEPRECATED_SINCE_S = 3;
    private static final int TRON_COMPILATION_REASON_CMDLINE = 22;
    private static final int TRON_COMPILATION_REASON_ERROR = 0;
    private static final int TRON_COMPILATION_REASON_FIRST_BOOT = 2;
    private static final int TRON_COMPILATION_REASON_INACTIVE = 7;
    private static final int TRON_COMPILATION_REASON_INSTALL = 4;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK = 11;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_DOWNGRADED = 13;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_DOWNGRADED_WITH_DM = 18;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_SECONDARY = 12;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_SECONDARY_DOWNGRADED = 14;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_SECONDARY_DOWNGRADED_WITH_DM = 19;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_SECONDARY_WITH_DM = 17;
    private static final int TRON_COMPILATION_REASON_INSTALL_BULK_WITH_DM = 16;
    private static final int TRON_COMPILATION_REASON_INSTALL_FAST = 10;
    private static final int TRON_COMPILATION_REASON_INSTALL_FAST_WITH_DM = 15;
    private static final int TRON_COMPILATION_REASON_INSTALL_WITH_DM = 9;
    private static final int TRON_COMPILATION_REASON_POST_BOOT = 21;
    private static final int TRON_COMPILATION_REASON_PREBUILT = 23;
    private static final int TRON_COMPILATION_REASON_SHARED = 8;
    private static final int TRON_COMPILATION_REASON_UNKNOWN = 1;
    private static final int TRON_COMPILATION_REASON_VDEX = 24;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler = new android.os.Handler(com.android.internal.os.BackgroundThread.getHandler().getLooper());
    private final com.android.server.pm.Installer mInstaller;
    private android.content.pm.IPackageManager mPackageManager;
    private static final java.lang.String TAG = "ArtManagerService";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    static {
        verifyTronLoggingConstants();
    }

    public ArtManagerService(android.content.Context context, com.android.server.pm.Installer installer, java.lang.Object obj) {
        this.mContext = context;
        this.mInstaller = installer;
        com.android.server.LocalServices.addService(android.content.pm.dex.ArtManagerInternal.class, new com.android.server.pm.dex.ArtManagerService.ArtManagerInternalImpl());
    }

    @android.annotation.NonNull
    private android.content.pm.IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        }
        return this.mPackageManager;
    }

    private boolean checkAndroidPermissions(int i, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_RUNTIME_PROFILES", TAG);
        switch (((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).noteOp(43, i, str)) {
            case 0:
                break;
            case 3:
                this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", TAG);
                break;
        }
        return true;
    }

    private boolean checkShellPermissions(int i, java.lang.String str, int i2) {
        android.content.pm.PackageInfo packageInfo;
        if (i2 != 2000) {
            return false;
        }
        if (com.android.internal.os.RoSystemProperties.DEBUGGABLE) {
            return true;
        }
        if (i == 1) {
            return false;
        }
        try {
            packageInfo = getPackageManager().getPackageInfo(str, 0L, 0);
        } catch (android.os.RemoteException e) {
            packageInfo = null;
        }
        return packageInfo != null && (packageInfo.applicationInfo.flags & 2) == 2;
    }

    public void snapshotRuntimeProfile(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, java.lang.String str3) {
        int callingUid = android.os.Binder.getCallingUid();
        if (!checkShellPermissions(i, str, callingUid) && !checkAndroidPermissions(callingUid, str3)) {
            try {
                iSnapshotRuntimeProfileCallback.onError(2);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        java.util.Objects.requireNonNull(iSnapshotRuntimeProfileCallback);
        boolean z = i == 1;
        if (!z) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        }
        if (!isRuntimeProfilingEnabled(i, str3)) {
            throw new java.lang.IllegalStateException("Runtime profiling is not enabled for " + i);
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Requested snapshot for " + str + ":" + str2);
        }
        if (z) {
            snapshotBootImageProfile(iSnapshotRuntimeProfileCallback);
        } else {
            snapshotAppProfile(str, str2, iSnapshotRuntimeProfileCallback);
        }
    }

    private void snapshotAppProfile(java.lang.String str, java.lang.String str2, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        android.content.pm.PackageInfo packageInfo;
        java.lang.String str3 = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(str, 0L, 0);
        } catch (android.os.RemoteException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            postError(iSnapshotRuntimeProfileCallback, str, 0);
            return;
        }
        boolean equals = packageInfo.applicationInfo.getBaseCodePath().equals(str2);
        java.lang.String[] splitCodePaths = packageInfo.applicationInfo.getSplitCodePaths();
        if (!equals && splitCodePaths != null) {
            int length = splitCodePaths.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                if (!splitCodePaths[length].equals(str2)) {
                    length--;
                } else {
                    str3 = packageInfo.applicationInfo.splitNames[length];
                    equals = true;
                    break;
                }
            }
        }
        if (!equals) {
            postError(iSnapshotRuntimeProfileCallback, str, 1);
            return;
        }
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            try {
                try {
                    com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                    try {
                        android.os.ParcelFileDescriptor snapshotAppProfile = com.android.server.pm.DexOptHelper.getArtManagerLocal().snapshotAppProfile(withFilteredSnapshot, str, str3);
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        postSuccess(str, snapshotAppProfile, iSnapshotRuntimeProfileCallback);
                        return;
                    } catch (java.lang.Throwable th) {
                        if (withFilteredSnapshot != null) {
                            try {
                                withFilteredSnapshot.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.lang.IllegalArgumentException e2) {
                    postError(iSnapshotRuntimeProfileCallback, str, 0);
                    return;
                }
            } catch (java.lang.IllegalStateException | com.android.server.art.ArtManagerLocal.SnapshotProfileException e3) {
                postError(iSnapshotRuntimeProfileCallback, str, 2);
                return;
            }
        }
        int appId = android.os.UserHandle.getAppId(packageInfo.applicationInfo.uid);
        if (appId < 0) {
            postError(iSnapshotRuntimeProfileCallback, str, 2);
            android.util.Slog.wtf(TAG, "AppId is -1 for package: " + str);
            return;
        }
        try {
            createProfileSnapshot(str, android.content.pm.dex.ArtManager.getProfileName(str3), str2, appId, iSnapshotRuntimeProfileCallback);
            destroyProfileSnapshot(str, android.content.pm.dex.ArtManager.getProfileName(str3));
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e4) {
            throw new java.lang.RuntimeException(e4);
        }
    }

    private void createProfileSnapshot(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        try {
            if (!this.mInstaller.createProfileSnapshot(i, str, str2, str3)) {
                postError(iSnapshotRuntimeProfileCallback, str, 2);
                return;
            }
            java.io.File profileSnapshotFileForName = android.content.pm.dex.ArtManager.getProfileSnapshotFileForName(str, str2);
            try {
                android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(profileSnapshotFileForName, 268435456);
                if (open == null || !open.getFileDescriptor().valid()) {
                    postError(iSnapshotRuntimeProfileCallback, str, 2);
                } else {
                    postSuccess(str, open, iSnapshotRuntimeProfileCallback);
                }
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(TAG, "Could not open snapshot profile for " + str + ":" + profileSnapshotFileForName, e);
                postError(iSnapshotRuntimeProfileCallback, str, 2);
            }
        } catch (com.android.server.pm.Installer.InstallerException e2) {
            postError(iSnapshotRuntimeProfileCallback, str, 2);
        }
    }

    private void destroyProfileSnapshot(java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Destroying profile snapshot for" + str + ":" + str2);
        }
        try {
            this.mInstaller.destroyProfileSnapshot(str, str2);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Failed to destroy profile snapshot for " + str + ":" + str2, e);
        }
    }

    public boolean isRuntimeProfilingEnabled(int i, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 2000 && !checkAndroidPermissions(callingUid, str)) {
            return false;
        }
        switch (i) {
            case 0:
                return true;
            case 1:
                return (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) && android.os.SystemProperties.getBoolean("persist.device_config.runtime_native_boot.profilebootclasspath", android.os.SystemProperties.getBoolean("dalvik.vm.profilebootclasspath", false));
            default:
                throw new java.lang.IllegalArgumentException("Invalid profile type:" + i);
        }
    }

    private void snapshotBootImageProfile(android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        java.lang.String str;
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            try {
                com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                try {
                    android.os.ParcelFileDescriptor snapshotBootImageProfile = com.android.server.pm.DexOptHelper.getArtManagerLocal().snapshotBootImageProfile(withFilteredSnapshot);
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                    postSuccess("android", snapshotBootImageProfile, iSnapshotRuntimeProfileCallback);
                    return;
                } catch (java.lang.Throwable th) {
                    if (withFilteredSnapshot != null) {
                        try {
                            withFilteredSnapshot.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.IllegalStateException | com.android.server.art.ArtManagerLocal.SnapshotProfileException e) {
                postError(iSnapshotRuntimeProfileCallback, "android", 2);
                return;
            }
        }
        java.lang.String join = java.lang.String.join(":", android.system.Os.getenv("BOOTCLASSPATH"), android.system.Os.getenv("SYSTEMSERVERCLASSPATH"));
        java.lang.String str2 = android.system.Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
        if (str2 != null) {
            str = java.lang.String.join(":", join, str2);
        } else {
            str = join;
        }
        try {
            createProfileSnapshot("android", BOOT_IMAGE_PROFILE_NAME, str, -1, iSnapshotRuntimeProfileCallback);
            destroyProfileSnapshot("android", BOOT_IMAGE_PROFILE_NAME);
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    private void postError(final android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, final java.lang.String str, final int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Failed to snapshot profile for " + str + " with error: " + i);
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.dex.ArtManagerService.lambda$postError$0(iSnapshotRuntimeProfileCallback, i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postError$0(android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, int i, java.lang.String str) {
        try {
            iSnapshotRuntimeProfileCallback.onError(i);
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Failed to callback after profile snapshot for " + str, e);
        }
    }

    private void postSuccess(final java.lang.String str, final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Successfully snapshot profile for " + str);
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.dex.ArtManagerService.lambda$postSuccess$1(parcelFileDescriptor, iSnapshotRuntimeProfileCallback, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postSuccess$1(android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.pm.dex.ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, java.lang.String str) {
        try {
            try {
                if (parcelFileDescriptor.getFileDescriptor().valid()) {
                    iSnapshotRuntimeProfileCallback.onSuccess(parcelFileDescriptor);
                } else {
                    android.util.Slog.wtf(TAG, "The snapshot FD became invalid before posting the result for " + str);
                    iSnapshotRuntimeProfileCallback.onError(2);
                }
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Failed to call onSuccess after profile snapshot for " + str, e);
            }
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
        } catch (java.lang.Throwable th) {
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            throw th;
        }
    }

    public void prepareAppProfiles(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.lang.String str;
        int appId = android.os.UserHandle.getAppId(androidPackage.getUid());
        if (i < 0) {
            android.util.Slog.wtf(TAG, "Invalid user id: " + i);
            return;
        }
        if (appId < 0) {
            android.util.Slog.wtf(TAG, "Invalid app id: " + appId);
            return;
        }
        try {
            android.util.ArrayMap<java.lang.String, java.lang.String> packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                java.lang.String keyAt = packageProfileNames.keyAt(size);
                java.lang.String valueAt = packageProfileNames.valueAt(size);
                if (!z) {
                    str = null;
                } else {
                    java.io.File findDexMetadataForFile = android.content.pm.dex.DexMetadataHelper.findDexMetadataForFile(new java.io.File(keyAt));
                    str = findDexMetadataForFile != null ? findDexMetadataForFile.getAbsolutePath() : null;
                }
                synchronized (this.mInstaller) {
                    try {
                        if (!this.mInstaller.prepareAppProfile(androidPackage.getPackageName(), i, appId, valueAt, keyAt, str)) {
                            android.util.Slog.e(TAG, "Failed to prepare profile for " + androidPackage.getPackageName() + ":" + keyAt);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Failed to prepare profile for " + androidPackage.getPackageName(), e);
        }
    }

    public void prepareAppProfiles(com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        for (int i : iArr) {
            prepareAppProfiles(androidPackage, i, z);
        }
    }

    public void clearAppProfiles(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        try {
            android.util.ArrayMap<java.lang.String, java.lang.String> packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                this.mInstaller.clearAppProfiles(androidPackage.getPackageName(), packageProfileNames.valueAt(size));
            }
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w(TAG, java.lang.String.valueOf(e));
        }
    }

    public void dumpProfiles(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int sharedAppGid = android.os.UserHandle.getSharedAppGid(androidPackage.getUid());
        try {
            android.util.ArrayMap<java.lang.String, java.lang.String> packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                this.mInstaller.dumpProfiles(sharedAppGid, androidPackage.getPackageName(), packageProfileNames.valueAt(size), packageProfileNames.keyAt(size), z);
            }
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w(TAG, "Failed to dump profiles", e);
        }
    }

    private android.util.ArrayMap<java.lang.String, java.lang.String> getPackageProfileNames(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>();
        if (androidPackage.isDeclaredHavingCode()) {
            arrayMap.put(androidPackage.getBaseApkPath(), android.content.pm.dex.ArtManager.getProfileName((java.lang.String) null));
        }
        java.lang.String[] splitCodePaths = androidPackage.getSplitCodePaths();
        int[] splitFlags = androidPackage.getSplitFlags();
        java.lang.String[] splitNames = androidPackage.getSplitNames();
        if (!com.android.internal.util.ArrayUtils.isEmpty(splitCodePaths)) {
            for (int i = 0; i < splitCodePaths.length; i++) {
                if ((splitFlags[i] & 4) != 0) {
                    arrayMap.put(splitCodePaths[i], android.content.pm.dex.ArtManager.getProfileName(splitNames[i]));
                }
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getCompilationReasonTronValue(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1968171580:
                if (str.equals("bg-dexopt")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1836520088:
                if (str.equals("install-fast-dm")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1425983632:
                if (str.equals("ab-ota")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1291894341:
                if (str.equals("prebuilt")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1205769507:
                if (str.equals("boot-after-mainline-update")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1125526357:
                if (str.equals("install-bulk-secondary-dm")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -903566235:
                if (str.equals("shared")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -587828592:
                if (str.equals("boot-after-ota")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -525717262:
                if (str.equals("install-bulk-dm")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -207505425:
                if (str.equals("first-boot")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3614689:
                if (str.equals("vdex")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 17118443:
                if (str.equals("install-bulk-secondary")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 24665195:
                if (str.equals("inactive")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 96784904:
                if (str.equals("error")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 884802606:
                if (str.equals("cmdline")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 900392443:
                if (str.equals("install-dm")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 1558537393:
                if (str.equals("install-bulk-secondary-downgraded")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 1756645502:
                if (str.equals("install-bulk-downgraded-dm")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1791051557:
                if (str.equals("install-bulk-secondary-downgraded-dm")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1956259839:
                if (str.equals("post-boot")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1957569947:
                if (str.equals("install")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1988662788:
                if (str.equals("install-bulk")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1988762958:
                if (str.equals("install-fast")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 2005174776:
                if (str.equals("install-bulk-downgraded")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 22;
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 20;
            case 4:
                return 25;
            case 5:
                return 21;
            case 6:
                return 4;
            case 7:
                return 5;
            case '\b':
                return 6;
            case '\t':
                return 7;
            case '\n':
                return 8;
            case 11:
                return 23;
            case '\f':
                return 24;
            case '\r':
                return 10;
            case 14:
                return 11;
            case 15:
                return 12;
            case 16:
                return 13;
            case 17:
                return 14;
            case 18:
                return 9;
            case 19:
                return 15;
            case 20:
                return 16;
            case 21:
                return 17;
            case 22:
                return 18;
            case 23:
                return 19;
            default:
                return 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getCompilationFilterTronValue(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2111392495:
                if (str.equals("speed-profile-iorap")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1957514039:
                if (str.equals("assume-verified")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1803365233:
                if (str.equals("everything-profile")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1707970841:
                if (str.equals("verify-iorap")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1704485649:
                if (str.equals("extract-iorap")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1305289599:
                if (str.equals("extract")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1129892317:
                if (str.equals("speed-profile")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1079751646:
                if (str.equals("run-from-apk-fallback-iorap")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -902315795:
                if (str.equals("run-from-vdex-fallback")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -819951495:
                if (str.equals("verify")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -701043824:
                if (str.equals("space-profile-iorap")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -284840886:
                if (str.equals("unknown")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -44924837:
                if (str.equals("run-from-vdex-fallback-iorap")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 50732855:
                if (str.equals("assume-verified-iorap")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 96784904:
                if (str.equals("error")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 109637894:
                if (str.equals("space")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 109641799:
                if (str.equals("speed")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 256996201:
                if (str.equals("run-from-apk-iorap")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 348518370:
                if (str.equals("space-profile")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 401590963:
                if (str.equals("everything")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 590454177:
                if (str.equals("everything-iorap")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 658336598:
                if (str.equals("quicken")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 863294077:
                if (str.equals("everything-profile-iorap")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 922064507:
                if (str.equals("run-from-apk")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 979981365:
                if (str.equals("speed-iorap")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1316714932:
                if (str.equals("space-iorap")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1482618884:
                if (str.equals("quicken-iorap")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 1906552308:
                if (str.equals("run-from-apk-fallback")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case '\b':
                return 8;
            case '\t':
                return 9;
            case '\n':
                return 10;
            case 11:
                return 11;
            case '\f':
                return 12;
            case '\r':
                return 13;
            case 14:
                return 14;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            case 21:
                return 21;
            case 22:
                return 22;
            case 23:
                return 23;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            case 27:
                return 27;
            default:
                return 1;
        }
    }

    private static void verifyTronLoggingConstants() {
        for (int i = 0; i < com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS.length; i++) {
            java.lang.String str = com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS[i];
            int compilationReasonTronValue = getCompilationReasonTronValue(str);
            if (compilationReasonTronValue == 0 || compilationReasonTronValue == 1) {
                throw new java.lang.IllegalArgumentException("Compilation reason not configured for TRON logging: " + str);
            }
        }
    }

    private class ArtManagerInternalImpl extends android.content.pm.dex.ArtManagerInternal {
        private static final java.lang.String IORAP_DIR = "/data/misc/iorapd";
        private static final java.lang.String TAG = "ArtManagerInternalImpl";

        private ArtManagerInternalImpl() {
        }

        public android.content.pm.dex.PackageOptimizationInfo getPackageOptimizationInfo(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, java.lang.String str2) {
            java.lang.String str3;
            java.lang.String str4 = "error";
            if (applicationInfo.packageName.equals("android")) {
                return android.content.pm.dex.PackageOptimizationInfo.createWithNoInfo();
            }
            try {
                dalvik.system.DexFile.OptimizationInfo dexFileOptimizationInfo = dalvik.system.DexFile.getDexFileOptimizationInfo(applicationInfo.getBaseCodePath(), dalvik.system.VMRuntime.getInstructionSet(str));
                java.lang.String status = dexFileOptimizationInfo.getStatus();
                str3 = dexFileOptimizationInfo.getReason();
                str4 = status;
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(TAG, "Could not get optimizations status for " + applicationInfo.getBaseCodePath(), e);
                str3 = "error";
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.wtf(TAG, "Requested optimization status for " + applicationInfo.getBaseCodePath() + " due to an invalid abi " + str, e2);
                str3 = "error";
            }
            if (checkIorapCompiledTrace(applicationInfo.packageName, str2, applicationInfo.longVersionCode)) {
                str4 = str4 + "-iorap";
            }
            return new android.content.pm.dex.PackageOptimizationInfo(com.android.server.pm.dex.ArtManagerService.getCompilationFilterTronValue(str4), com.android.server.pm.dex.ArtManagerService.getCompilationReasonTronValue(str3));
        }

        private boolean checkIorapCompiledTrace(java.lang.String str, java.lang.String str2, long j) {
            java.nio.file.Path path = java.nio.file.Paths.get(IORAP_DIR, str, java.lang.Long.toString(j), str2, "compiled_traces", "compiled_trace.pb");
            try {
                boolean exists = java.nio.file.Files.exists(path, new java.nio.file.LinkOption[0]);
                if (com.android.server.pm.dex.ArtManagerService.DEBUG) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(path.toString());
                    sb.append(exists ? " exists" : " doesn't exist");
                    android.util.Log.d(TAG, sb.toString());
                }
                if (exists) {
                    long size = java.nio.file.Files.size(path);
                    if (com.android.server.pm.dex.ArtManagerService.DEBUG) {
                        android.util.Log.d(TAG, path.toString() + " size is " + java.lang.Long.toString(size));
                    }
                    return size > 0;
                }
                return exists;
            } catch (java.io.IOException e) {
                android.util.Log.d(TAG, e.getMessage());
                return false;
            }
        }
    }
}
