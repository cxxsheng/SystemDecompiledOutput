package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageDexOptimizer {
    public static final int DEX_OPT_CANCELLED = 2;
    public static final int DEX_OPT_FAILED = -1;
    public static final int DEX_OPT_PERFORMED = 1;
    public static final int DEX_OPT_SKIPPED = 0;
    static final java.lang.String OAT_DIR_NAME = "oat";
    private static final java.lang.String TAG = "PackageDexOptimizer";
    private static final long WAKELOCK_TIMEOUT_MS = 660000;
    private static final java.util.Random sRandom = new java.util.Random();
    private final com.android.server.pm.dex.ArtStatsLogUtils.ArtStatsLogger mArtStatsLogger;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private final android.os.PowerManager.WakeLock mDexoptWakeLock;
    private final com.android.server.pm.PackageDexOptimizer.Injector mInjector;
    private final java.lang.Object mInstallLock;
    private final com.android.server.pm.Installer mInstaller;
    private volatile boolean mSystemReady;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DexOptResult {
    }

    interface Injector {
        com.android.server.apphibernation.AppHibernationManagerInternal getAppHibernationManagerInternal();

        android.os.PowerManager getPowerManager(android.content.Context context);
    }

    PackageDexOptimizer(com.android.server.pm.Installer installer, java.lang.Object obj, android.content.Context context, java.lang.String str) {
        this(new com.android.server.pm.PackageDexOptimizer.Injector() { // from class: com.android.server.pm.PackageDexOptimizer.1
            @Override // com.android.server.pm.PackageDexOptimizer.Injector
            public com.android.server.apphibernation.AppHibernationManagerInternal getAppHibernationManagerInternal() {
                return (com.android.server.apphibernation.AppHibernationManagerInternal) com.android.server.LocalServices.getService(com.android.server.apphibernation.AppHibernationManagerInternal.class);
            }

            @Override // com.android.server.pm.PackageDexOptimizer.Injector
            public android.os.PowerManager getPowerManager(android.content.Context context2) {
                return (android.os.PowerManager) context2.getSystemService(android.os.PowerManager.class);
            }
        }, installer, obj, context, str);
    }

    protected PackageDexOptimizer(com.android.server.pm.PackageDexOptimizer packageDexOptimizer) {
        this.mArtStatsLogger = new com.android.server.pm.dex.ArtStatsLogUtils.ArtStatsLogger();
        this.mContext = packageDexOptimizer.mContext;
        this.mInstaller = packageDexOptimizer.mInstaller;
        this.mInstallLock = packageDexOptimizer.mInstallLock;
        this.mDexoptWakeLock = packageDexOptimizer.mDexoptWakeLock;
        this.mSystemReady = packageDexOptimizer.mSystemReady;
        this.mInjector = packageDexOptimizer.mInjector;
    }

    @com.android.internal.annotations.VisibleForTesting
    PackageDexOptimizer(@android.annotation.NonNull com.android.server.pm.PackageDexOptimizer.Injector injector, com.android.server.pm.Installer installer, java.lang.Object obj, android.content.Context context, java.lang.String str) {
        this.mArtStatsLogger = new com.android.server.pm.dex.ArtStatsLogUtils.ArtStatsLogger();
        this.mContext = context;
        this.mInstaller = installer;
        this.mInstallLock = obj;
        this.mDexoptWakeLock = injector.getPowerManager(context).newWakeLock(1, str);
        this.mInjector = injector;
    }

    boolean canOptimizePackage(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(androidPackage.getPackageName()) || !androidPackage.isDeclaredHavingCode() || androidPackage.isApex()) {
            return false;
        }
        com.android.server.apphibernation.AppHibernationManagerInternal appHibernationManagerInternal = this.mInjector.getAppHibernationManagerInternal();
        return (appHibernationManagerInternal != null && appHibernationManagerInternal.isHibernatingGlobally(androidPackage.getPackageName()) && appHibernationManagerInternal.isOatArtifactDeletionEnabled()) ? false : true;
    }

    int performDexOpt(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String[] strArr, com.android.server.pm.CompilerStats.PackageStats packageStats, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo, com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int performDexOptLI;
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(androidPackage.getPackageName())) {
            throw new java.lang.IllegalArgumentException("System server dexopting should be done via odrefresh");
        }
        if (androidPackage.getUid() == -1) {
            throw new java.lang.IllegalArgumentException("Dexopt for " + androidPackage.getPackageName() + " has invalid uid.");
        }
        if (!canOptimizePackage(androidPackage)) {
            return 0;
        }
        synchronized (this.mInstallLock) {
            try {
                long acquireWakeLockLI = acquireWakeLockLI(androidPackage.getUid());
                try {
                    performDexOptLI = performDexOptLI(androidPackage, packageStateInternal, strArr, packageStats, packageUseInfo, dexoptOptions);
                } finally {
                    releaseWakeLockLI(acquireWakeLockLI);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return performDexOptLI;
    }

    void controlDexOptBlocking(boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        getInstallerWithoutLock().controlDexOptBlocking(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x033b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:118:? A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int performDexOptLI(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String[] strArr, com.android.server.pm.CompilerStats.PackageStats packageStats, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo, com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i;
        int i2;
        java.lang.String[] strArr2;
        boolean[] zArr;
        int i3;
        char c;
        java.util.List<java.lang.String> list;
        java.lang.String[] strArr3;
        com.android.server.pm.CompilerStats.PackageStats packageStats2;
        com.android.server.pm.PackageDexOptimizer packageDexOptimizer;
        java.lang.String absolutePath;
        java.lang.Throwable th;
        java.lang.String str;
        com.android.server.pm.PackageDexOptimizer packageDexOptimizer2;
        java.lang.String str2;
        java.lang.String compilerFilterForReason;
        java.lang.String str3;
        java.lang.String str4;
        int i4;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        com.android.server.pm.PackageDexOptimizer packageDexOptimizer3 = this;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = androidPackage;
        com.android.server.pm.CompilerStats.PackageStats packageStats3 = packageStats;
        java.util.List<android.content.pm.SharedLibraryInfo> nonNativeUsesLibraryInfos = packageStateInternal.getTransientState().getNonNativeUsesLibraryInfos();
        java.lang.String[] dexCodeInstructionSets = com.android.server.pm.InstructionSets.getDexCodeInstructionSets(strArr != null ? strArr : com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi()));
        java.util.List<java.lang.String> allCodePaths = com.android.server.pm.parsing.pkg.AndroidPackageUtils.getAllCodePaths(androidPackage);
        int sharedAppGid = android.os.UserHandle.getSharedAppGid(androidPackage.getUid());
        char c2 = 65535;
        java.lang.String str8 = TAG;
        if (sharedAppGid == -1) {
            android.util.Slog.wtf(TAG, "Well this is awkward; package " + androidPackage.getPackageName() + " had UID " + androidPackage.getUid(), new java.lang.Throwable());
            i = 9999;
        } else {
            i = sharedAppGid;
        }
        boolean[] zArr2 = new boolean[allCodePaths.size()];
        zArr2[0] = androidPackage.isDeclaredHavingCode();
        for (int i5 = 1; i5 < allCodePaths.size(); i5++) {
            zArr2[i5] = (androidPackage.getSplitFlags()[i5 + (-1)] & 4) != 0;
        }
        java.lang.String[] classLoaderContexts = com.android.server.pm.dex.DexoptUtils.getClassLoaderContexts(androidPackage2, nonNativeUsesLibraryInfos, zArr2);
        if (allCodePaths.size() != classLoaderContexts.length) {
            java.lang.String[] splitCodePaths = androidPackage.getSplitCodePaths();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Inconsistent information between AndroidPackage and its ApplicationInfo. pkg.getAllCodePaths=");
            sb.append(allCodePaths);
            sb.append(" pkg.getBaseCodePath=");
            sb.append(androidPackage.getBaseApkPath());
            sb.append(" pkg.getSplitCodePaths=");
            sb.append(splitCodePaths == null ? "null" : java.util.Arrays.toString(splitCodePaths));
            throw new java.lang.IllegalStateException(sb.toString());
        }
        int i6 = 0;
        int i7 = 0;
        while (i7 < allCodePaths.size()) {
            if (zArr2[i7]) {
                if (classLoaderContexts[i7] == null) {
                    int i8 = i7;
                    throw new java.lang.IllegalStateException("Inconsistent information in the package structure. A split is marked to contain code but has no dependency listed. Index=" + i8 + " path=" + allCodePaths.get(i8));
                }
                java.lang.String str9 = allCodePaths.get(i7);
                if (dexoptOptions.getSplitName() == null || dexoptOptions.getSplitName().equals(new java.io.File(str9).getName())) {
                    java.lang.String profileName = android.content.pm.dex.ArtManager.getProfileName(i7 == 0 ? null : androidPackage.getSplitNames()[i7 - 1]);
                    boolean isUsedByOtherApps = dexoptOptions.isDexoptAsSharedLibrary() ? true : com.android.server.pm.DexOptHelper.useArtService() ? false : packageUseInfo.isUsedByOtherApps(str9);
                    java.lang.String realCompilerFilter = packageDexOptimizer3.getRealCompilerFilter(androidPackage2, dexoptOptions.getCompilerFilter());
                    boolean z = dalvik.system.DexFile.isProfileGuidedCompilerFilter(realCompilerFilter) && isUsedByOtherApps && dexoptOptions.getCompilationReason() != 3;
                    if (dexoptOptions.isDexoptInstallWithDexMetadata() || z) {
                        java.io.File findDexMetadataForFile = android.content.pm.dex.DexMetadataHelper.findDexMetadataForFile(new java.io.File(str9));
                        absolutePath = findDexMetadataForFile == null ? null : findDexMetadataForFile.getAbsolutePath();
                    } else {
                        absolutePath = null;
                    }
                    int i9 = i6;
                    int analyseProfiles = dexoptOptions.isCheckForProfileUpdates() ? packageDexOptimizer3.analyseProfiles(androidPackage2, i, profileName, realCompilerFilter) : 2;
                    java.lang.String str10 = "Failed to cleanup cloud profile";
                    if (z) {
                        try {
                            str2 = "cloud-" + profileName;
                            try {
                                if (packageDexOptimizer3.prepareCloudProfile(androidPackage2, str2, str9, absolutePath)) {
                                    str3 = str2;
                                    compilerFilterForReason = realCompilerFilter;
                                } else {
                                    compilerFilterForReason = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(14);
                                    str3 = null;
                                }
                                str4 = str3;
                                i4 = 2;
                                str5 = str2;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                str = "Failed to cleanup cloud profile";
                                packageDexOptimizer2 = packageDexOptimizer3;
                                if (str2 == null) {
                                }
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            str = "Failed to cleanup cloud profile";
                            packageDexOptimizer2 = packageDexOptimizer3;
                            str2 = null;
                        }
                    } else {
                        str4 = profileName;
                        i4 = analyseProfiles;
                        compilerFilterForReason = realCompilerFilter;
                        str5 = null;
                    }
                    java.lang.String str11 = str5;
                    java.lang.String str12 = absolutePath;
                    java.lang.String str13 = str9;
                    int i10 = i7;
                    strArr2 = classLoaderContexts;
                    try {
                        int dexFlags = getDexFlags(androidPackage, packageStateInternal, compilerFilterForReason, z, dexoptOptions);
                        int length = dexCodeInstructionSets.length;
                        int i11 = 0;
                        int i12 = i9;
                        while (i11 < length) {
                            java.lang.String str14 = dexCodeInstructionSets[i11];
                            int i13 = i11;
                            int i14 = i12;
                            boolean[] zArr3 = zArr2;
                            int i15 = i;
                            java.lang.String str15 = str8;
                            int i16 = length;
                            java.util.List<java.lang.String> list2 = allCodePaths;
                            java.lang.String[] strArr4 = dexCodeInstructionSets;
                            java.lang.String str16 = str10;
                            int i17 = i10;
                            try {
                                int dexOptPath = dexOptPath(androidPackage, packageStateInternal, str13, str14, compilerFilterForReason, i4, strArr2[i10], dexFlags, i15, packageStats, dexoptOptions.isDowngrade(), str4, str12, dexoptOptions.getCompilationReason());
                                if (packageStats != null) {
                                    try {
                                        android.os.Trace.traceBegin(16384L, "dex2oat-metrics");
                                        try {
                                            packageDexOptimizer2 = this;
                                            try {
                                                str7 = str13;
                                                com.android.server.pm.dex.ArtStatsLogUtils.writeStatsLog(packageDexOptimizer2.mArtStatsLogger, sRandom.nextLong(), compilerFilterForReason, androidPackage.getUid(), packageStats.getCompileTime(str7), str12, dexoptOptions.getCompilationReason(), dexOptPath, com.android.server.pm.dex.ArtStatsLogUtils.getApkType(str7, androidPackage.getBaseApkPath(), androidPackage.getSplitCodePaths()), str14, str7);
                                                try {
                                                    android.os.Trace.traceEnd(16384L);
                                                } catch (java.lang.Throwable th4) {
                                                    th = th4;
                                                    th = th;
                                                    str2 = str11;
                                                    str8 = str15;
                                                    str = str16;
                                                    if (str2 == null) {
                                                        throw th;
                                                    }
                                                    try {
                                                        packageDexOptimizer2.mInstaller.deleteReferenceProfile(androidPackage.getPackageName(), str2);
                                                        throw th;
                                                    } catch (com.android.server.pm.Installer.InstallerException e) {
                                                        android.util.Slog.w(str8, str, e);
                                                        throw th;
                                                    }
                                                }
                                            } catch (java.lang.Throwable th5) {
                                                th = th5;
                                                android.os.Trace.traceEnd(16384L);
                                                throw th;
                                            }
                                        } catch (java.lang.Throwable th6) {
                                            th = th6;
                                            packageDexOptimizer2 = this;
                                        }
                                    } catch (java.lang.Throwable th7) {
                                        th = th7;
                                        packageDexOptimizer2 = this;
                                    }
                                } else {
                                    packageDexOptimizer2 = this;
                                    str7 = str13;
                                }
                                if (dexOptPath == 2) {
                                    if (i14 == -1) {
                                        java.lang.String str17 = str11;
                                        if (str17 != null) {
                                            try {
                                                packageDexOptimizer2.mInstaller.deleteReferenceProfile(androidPackage.getPackageName(), str17);
                                            } catch (com.android.server.pm.Installer.InstallerException e2) {
                                                android.util.Slog.w(str15, str16, e2);
                                            }
                                        }
                                        return i14;
                                    }
                                    java.lang.String str18 = str11;
                                    if (str18 != null) {
                                        try {
                                            packageDexOptimizer2.mInstaller.deleteReferenceProfile(androidPackage.getPackageName(), str18);
                                        } catch (com.android.server.pm.Installer.InstallerException e3) {
                                            android.util.Slog.w(str15, str16, e3);
                                        }
                                    }
                                    return dexOptPath;
                                }
                                java.lang.String str19 = str11;
                                i12 = i14;
                                str8 = str15;
                                if (i12 != -1 && dexOptPath != 0) {
                                    i12 = dexOptPath;
                                }
                                i11 = i13 + 1;
                                str13 = str7;
                                str11 = str19;
                                str10 = str16;
                                zArr2 = zArr3;
                                i = i15;
                                length = i16;
                                allCodePaths = list2;
                                dexCodeInstructionSets = strArr4;
                                i10 = i17;
                            } catch (java.lang.Throwable th8) {
                                th = th8;
                                packageDexOptimizer2 = this;
                                str6 = str11;
                                str8 = str15;
                                str = str16;
                                th = th;
                                str2 = str6;
                                if (str2 == null) {
                                }
                            }
                        }
                        packageDexOptimizer = this;
                        packageStats2 = packageStats;
                        zArr = zArr2;
                        i3 = i;
                        list = allCodePaths;
                        strArr3 = dexCodeInstructionSets;
                        java.lang.String str20 = str10;
                        i2 = i10;
                        java.lang.String str21 = str11;
                        c = 65535;
                        if (str21 != null) {
                            try {
                                packageDexOptimizer.mInstaller.deleteReferenceProfile(androidPackage.getPackageName(), str21);
                            } catch (com.android.server.pm.Installer.InstallerException e4) {
                                android.util.Slog.w(str8, str20, e4);
                            }
                        }
                        i6 = i12;
                        androidPackage2 = androidPackage;
                        packageStats3 = packageStats2;
                        packageDexOptimizer3 = packageDexOptimizer;
                        c2 = c;
                        classLoaderContexts = strArr2;
                        zArr2 = zArr;
                        i = i3;
                        allCodePaths = list;
                        dexCodeInstructionSets = strArr3;
                        i7 = i2 + 1;
                    } catch (java.lang.Throwable th9) {
                        th = th9;
                        packageDexOptimizer2 = this;
                        str = "Failed to cleanup cloud profile";
                        str6 = str11;
                    }
                }
            }
            i2 = i7;
            strArr2 = classLoaderContexts;
            zArr = zArr2;
            i3 = i;
            c = c2;
            list = allCodePaths;
            strArr3 = dexCodeInstructionSets;
            packageStats2 = packageStats3;
            packageDexOptimizer = packageDexOptimizer3;
            androidPackage2 = androidPackage;
            packageStats3 = packageStats2;
            packageDexOptimizer3 = packageDexOptimizer;
            c2 = c;
            classLoaderContexts = strArr2;
            zArr2 = zArr;
            i = i3;
            allCodePaths = list;
            dexCodeInstructionSets = strArr3;
            i7 = i2 + 1;
        }
        return i6;
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private boolean prepareCloudProfile(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        if (str3 == null) {
            return false;
        }
        if (this.mInstaller.isIsolated()) {
            return true;
        }
        try {
            this.mInstaller.deleteReferenceProfile(androidPackage.getPackageName(), str);
            this.mInstaller.prepareAppProfile(androidPackage.getPackageName(), com.android.server.am.ProcessList.INVALID_ADJ, android.os.UserHandle.getAppId(androidPackage.getUid()), str, str2, str3);
            return true;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w(TAG, "Failed to prepare cloud profile", e);
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private int dexOptPath(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2, int i3, com.android.server.pm.CompilerStats.PackageStats packageStats, boolean z, java.lang.String str5, java.lang.String str6, int i4) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.lang.String str7;
        long currentTimeMillis;
        java.lang.String seInfo;
        com.android.server.pm.Installer installerLI;
        java.lang.String packageName;
        java.lang.String volumeUuid;
        int targetSdkVersion;
        java.lang.String augmentedReasonName;
        java.lang.String packageOatDirIfSupported = getPackageOatDirIfSupported(packageStateInternal, androidPackage);
        int dexoptNeeded = getDexoptNeeded(androidPackage.getPackageName(), str, str2, str3, str4, i, z, i2, packageOatDirIfSupported);
        if (java.lang.Math.abs(dexoptNeeded) == 0) {
            return 0;
        }
        android.util.Log.i(TAG, "Running dexopt (dexoptNeeded=" + dexoptNeeded + ") on: " + str + " pkg=" + androidPackage.getPackageName() + " isa=" + str2 + " dexoptFlags=" + printDexoptFlags(i2) + " targetFilter=" + str3 + " oatDir=" + packageOatDirIfSupported + " classLoaderContext=" + str4);
        try {
            currentTimeMillis = java.lang.System.currentTimeMillis();
            seInfo = packageStateInternal.getSeInfo();
            installerLI = getInstallerLI();
            packageName = androidPackage.getPackageName();
            volumeUuid = androidPackage.getVolumeUuid();
            targetSdkVersion = androidPackage.getTargetSdkVersion();
            augmentedReasonName = getAugmentedReasonName(i4, str6 != null);
            str7 = TAG;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            e = e;
            str7 = TAG;
        }
        try {
            if (!installerLI.dexopt(str, i3, packageName, str2, dexoptNeeded, packageOatDirIfSupported, i2, str3, volumeUuid, str4, seInfo, false, targetSdkVersion, str5, str6, augmentedReasonName)) {
                return 2;
            }
            if (packageStats != null) {
                packageStats.setCompileTime(str, (int) (java.lang.System.currentTimeMillis() - currentTimeMillis));
            }
            if (packageOatDirIfSupported != null) {
                com.android.internal.content.F2fsUtils.releaseCompressedBlocks(this.mContext.getContentResolver(), new java.io.File(packageOatDirIfSupported));
            }
            return 1;
        } catch (com.android.server.pm.Installer.InstallerException e2) {
            e = e2;
            android.util.Slog.w(str7, "Failed to dexopt", e);
            return -1;
        }
    }

    private java.lang.String getAugmentedReasonName(int i, boolean z) {
        return com.android.server.pm.PackageManagerServiceCompilerMapping.getReasonName(i) + (z ? com.android.server.pm.dex.ArtManagerService.DEXOPT_REASON_WITH_DEX_METADATA_ANNOTATION : "");
    }

    public int dexOptSecondaryDexPath(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo, com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int dexOptSecondaryDexPathLI;
        if (applicationInfo.uid == -1) {
            throw new java.lang.IllegalArgumentException("Dexopt for path " + str + " has invalid uid.");
        }
        synchronized (this.mInstallLock) {
            try {
                long acquireWakeLockLI = acquireWakeLockLI(applicationInfo.uid);
                try {
                    dexOptSecondaryDexPathLI = dexOptSecondaryDexPathLI(applicationInfo, str, dexUseInfo, dexoptOptions);
                } finally {
                    releaseWakeLockLI(acquireWakeLockLI);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return dexOptSecondaryDexPathLI;
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private long acquireWakeLockLI(int i) {
        if (!this.mSystemReady) {
            return -1L;
        }
        this.mDexoptWakeLock.setWorkSource(new android.os.WorkSource(i));
        this.mDexoptWakeLock.acquire(WAKELOCK_TIMEOUT_MS);
        return android.os.SystemClock.elapsedRealtime();
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private void releaseWakeLockLI(long j) {
        if (j < 0) {
            return;
        }
        try {
            if (this.mDexoptWakeLock.isHeld()) {
                this.mDexoptWakeLock.release();
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - j;
            if (elapsedRealtime >= WAKELOCK_TIMEOUT_MS) {
                android.util.Slog.wtf(TAG, "WakeLock " + this.mDexoptWakeLock.getTag() + " time out. Operation took " + elapsedRealtime + " ms. Thread: " + java.lang.Thread.currentThread().getName());
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.wtf(TAG, "Error while releasing " + this.mDexoptWakeLock.getTag() + " lock", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private int dexOptSecondaryDexPathLI(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, com.android.server.pm.dex.PackageDexUsage.DexUseInfo dexUseInfo, com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.lang.String str2;
        int i;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.String realCompilerFilter = getRealCompilerFilter(applicationInfo, dexoptOptions.getCompilerFilter(), dexUseInfo.isUsedByOtherApps());
        int dexFlags = getDexFlags(applicationInfo, realCompilerFilter, dexoptOptions) | 32;
        java.lang.String str6 = applicationInfo.deviceProtectedDataDir;
        java.lang.String str7 = TAG;
        if (str6 != null && android.os.FileUtils.contains(applicationInfo.deviceProtectedDataDir, str)) {
            i = dexFlags | 256;
        } else {
            if (applicationInfo.credentialProtectedDataDir == null) {
                str2 = TAG;
            } else if (!android.os.FileUtils.contains(applicationInfo.credentialProtectedDataDir, str)) {
                str2 = TAG;
            } else {
                i = dexFlags | 128;
            }
            android.util.Slog.e(str2, "Could not infer CE/DE storage for package " + applicationInfo.packageName);
            return -1;
        }
        if (dexUseInfo.isUnsupportedClassLoaderContext() || dexUseInfo.isVariableClassLoaderContext()) {
            str3 = "verify";
            str4 = null;
        } else {
            str3 = realCompilerFilter;
            str4 = dexUseInfo.getClassLoaderContext();
        }
        int compilationReason = dexoptOptions.getCompilationReason();
        android.util.Log.d(TAG, "Running dexopt on: " + str + " pkg=" + applicationInfo.packageName + " isa=" + dexUseInfo.getLoaderIsas() + " reason=" + com.android.server.pm.PackageManagerServiceCompilerMapping.getReasonName(compilationReason) + " dexoptFlags=" + printDexoptFlags(i) + " target-filter=" + str3 + " class-loader-context=" + str4);
        try {
            java.util.Iterator<java.lang.String> it = dexUseInfo.getLoaderIsas().iterator();
            while (it.hasNext()) {
                java.lang.String str8 = str4;
                java.lang.String str9 = str3;
                int i2 = i;
                str5 = str7;
                try {
                    if (!getInstallerLI().dexopt(str, applicationInfo.uid, applicationInfo.packageName, it.next(), 0, null, i, str3, applicationInfo.volumeUuid, str8, applicationInfo.seInfo, dexoptOptions.isDowngrade(), applicationInfo.targetSdkVersion, null, null, com.android.server.pm.PackageManagerServiceCompilerMapping.getReasonName(compilationReason))) {
                        return 2;
                    }
                    i = i2;
                    str4 = str8;
                    str3 = str9;
                    str7 = str5;
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    e = e;
                    android.util.Slog.w(str5, "Failed to dexopt", e);
                    return -1;
                }
            }
            return 1;
        } catch (com.android.server.pm.Installer.InstallerException e2) {
            e = e2;
            str5 = str7;
        }
    }

    protected int adjustDexoptNeeded(int i) {
        return i;
    }

    protected int adjustDexoptFlags(int i) {
        return i;
    }

    void dumpDexoptState(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.lang.String[] dexCodeInstructionSets = com.android.server.pm.InstructionSets.getDexCodeInstructionSets(com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi()));
        for (java.lang.String str : com.android.server.pm.parsing.pkg.AndroidPackageUtils.getAllCodePathsExcludingResourceOnly(androidPackage)) {
            indentingPrintWriter.println("path: " + str);
            indentingPrintWriter.increaseIndent();
            for (java.lang.String str2 : dexCodeInstructionSets) {
                try {
                    dalvik.system.DexFile.OptimizationInfo dexFileOptimizationInfo = dalvik.system.DexFile.getDexFileOptimizationInfo(str, str2);
                    indentingPrintWriter.println(str2 + ": [status=" + dexFileOptimizationInfo.getStatus() + "] [reason=" + dexFileOptimizationInfo.getReason() + "]");
                } catch (java.io.IOException e) {
                    indentingPrintWriter.println(str2 + ": [Exception]: " + e.getMessage());
                }
            }
            if (packageUseInfo.isUsedByOtherApps(str)) {
                indentingPrintWriter.println("used by other apps: " + packageUseInfo.getLoadingPackages(str));
            }
            java.util.Map<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> dexUseInfoMap = packageUseInfo.getDexUseInfoMap();
            if (!dexUseInfoMap.isEmpty()) {
                indentingPrintWriter.println("known secondary dex files:");
                indentingPrintWriter.increaseIndent();
                for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> entry : dexUseInfoMap.entrySet()) {
                    java.lang.String key = entry.getKey();
                    com.android.server.pm.dex.PackageDexUsage.DexUseInfo value = entry.getValue();
                    indentingPrintWriter.println(key);
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("class loader context: " + value.getClassLoaderContext());
                    if (value.isUsedByOtherApps()) {
                        indentingPrintWriter.println("used by other apps: " + value.getLoadingPackages());
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    private java.lang.String getRealCompilerFilter(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, boolean z) {
        if (applicationInfo.isEmbeddedDexUsed()) {
            return dalvik.system.DexFile.isOptimizedCompilerFilter(str) ? "verify" : str;
        }
        if (((applicationInfo.flags & 16384) == 0 && (applicationInfo.flags & 2) == 0) ? false : true) {
            return dalvik.system.DexFile.getSafeModeCompilerFilter(str);
        }
        if (dalvik.system.DexFile.isProfileGuidedCompilerFilter(str) && z) {
            return com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(14);
        }
        return str;
    }

    private java.lang.String getRealCompilerFilter(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
        if (androidPackage.isUseEmbeddedDex()) {
            return dalvik.system.DexFile.isOptimizedCompilerFilter(str) ? "verify" : str;
        }
        if (androidPackage.isVmSafeMode() || androidPackage.isDebuggable()) {
            return dalvik.system.DexFile.getSafeModeCompilerFilter(str);
        }
        return str;
    }

    private boolean isAppImageEnabled() {
        return android.os.SystemProperties.get("dalvik.vm.appimageformat", "").length() > 0;
    }

    private int getDexFlags(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        return getDexFlags((applicationInfo.flags & 2) != 0, applicationInfo.getHiddenApiEnforcementPolicy(), applicationInfo.splitDependencies, applicationInfo.requestsIsolatedSplitLoading(), str, false, dexoptOptions);
    }

    private int getDexFlags(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String str, boolean z, com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        return getDexFlags(androidPackage.isDebuggable(), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getHiddenApiEnforcementPolicy(androidPackage, packageStateInternal), androidPackage.getSplitDependencies(), androidPackage.isIsolatedSplitLoading(), str, z, dexoptOptions);
    }

    private int getDexFlags(boolean z, int i, android.util.SparseArray<int[]> sparseArray, boolean z2, java.lang.String str, boolean z3, com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        int i2;
        boolean z4;
        boolean isProfileGuidedCompilerFilter = dalvik.system.DexFile.isProfileGuidedCompilerFilter(str);
        boolean z5 = !isProfileGuidedCompilerFilter || dexoptOptions.isDexoptInstallWithDexMetadata() || z3;
        int i3 = isProfileGuidedCompilerFilter ? 16 : 0;
        if (i == 0) {
            i2 = 0;
        } else {
            i2 = 1024;
        }
        switch (dexoptOptions.getCompilationReason()) {
            case 0:
            case 1:
            case 2:
            case 3:
                z4 = false;
                break;
            default:
                z4 = true;
                break;
        }
        return adjustDexoptFlags((z ? 4 : 0) | (z5 ? 2 : 0) | i3 | (dexoptOptions.isBootComplete() ? 8 : 0) | (dexoptOptions.isDexoptIdleBackgroundJob() ? 512 : 0) | (z4 ? 2048 : 0) | (isProfileGuidedCompilerFilter && ((sparseArray == null || !z2) && isAppImageEnabled()) ? 4096 : 0) | (dexoptOptions.isDexoptInstallForRestore() ? 8192 : 0) | i2);
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private int getDexoptNeeded(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i, boolean z, int i2, java.lang.String str6) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        boolean z2;
        java.lang.String str7;
        if (!this.mInstaller.isIsolated()) {
            com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        }
        boolean z3 = (i2 & 2) != 0;
        boolean z4 = (i2 & 16) != 0;
        boolean z5 = i == 1;
        try {
            if (!z5 && z4 && z3) {
                if (isOdexPrivate(str, str2, str3, str6)) {
                    z2 = true;
                    if (!compilerFilterDependsOnProfiles(str4) && i == 3) {
                        str7 = "verify";
                    } else {
                        str7 = str4;
                    }
                    return adjustDexoptNeeded(dalvik.system.DexFile.getDexOptNeeded(str2, str3, str7, str5, z2, z));
                }
            }
            if (!compilerFilterDependsOnProfiles(str4)) {
            }
            str7 = str4;
            return adjustDexoptNeeded(dalvik.system.DexFile.getDexOptNeeded(str2, str3, str7, str5, z2, z));
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "IOException reading apk: " + str2, e);
            return -1;
        } catch (java.lang.RuntimeException e2) {
            android.util.Slog.wtf(TAG, "Unexpected exception when calling dexoptNeeded on " + str2, e2);
            return -1;
        }
        z2 = z5;
    }

    private boolean compilerFilterDependsOnProfiles(java.lang.String str) {
        return str.endsWith("-profile");
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private boolean isOdexPrivate(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        try {
            return this.mInstaller.getOdexVisibility(str, str2, str3, str4) == 2;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w(TAG, "Failed to get odex visibility for " + str2, e);
            return false;
        }
    }

    private int analyseProfiles(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, java.lang.String str, java.lang.String str2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int mergeProfiles;
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        if (!dalvik.system.DexFile.isProfileGuidedCompilerFilter(str2)) {
            return 2;
        }
        try {
            synchronized (this.mInstallLock) {
                mergeProfiles = getInstallerLI().mergeProfiles(i, androidPackage.getPackageName(), str);
            }
            return mergeProfiles;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.w(TAG, "Failed to merge profiles", e);
            return 2;
        }
    }

    @android.annotation.Nullable
    private java.lang.String getPackageOatDirIfSupported(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (!com.android.server.pm.parsing.pkg.AndroidPackageUtils.canHaveOatDir(packageState, androidPackage)) {
            return null;
        }
        java.io.File file = new java.io.File(androidPackage.getPath());
        if (file.isDirectory()) {
            return getOatDir(file).getAbsolutePath();
        }
        return null;
    }

    public static java.io.File getOatDir(java.io.File file) {
        return new java.io.File(file, OAT_DIR_NAME);
    }

    void systemReady() {
        this.mSystemReady = true;
    }

    private java.lang.String printDexoptFlags(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 8) == 8) {
            arrayList.add("boot_complete");
        }
        if ((i & 4) == 4) {
            arrayList.add("debuggable");
        }
        if ((i & 16) == 16) {
            arrayList.add("profile_guided");
        }
        if ((i & 2) == 2) {
            arrayList.add("public");
        }
        if ((i & 32) == 32) {
            arrayList.add("secondary");
        }
        if ((i & 64) == 64) {
            arrayList.add("force");
        }
        if ((i & 128) == 128) {
            arrayList.add("storage_ce");
        }
        if ((i & 256) == 256) {
            arrayList.add("storage_de");
        }
        if ((i & 512) == 512) {
            arrayList.add("idle_background_job");
        }
        if ((i & 1024) == 1024) {
            arrayList.add("enable_hidden_api_checks");
        }
        return java.lang.String.join(",", arrayList);
    }

    public static class ForcedUpdatePackageDexOptimizer extends com.android.server.pm.PackageDexOptimizer {
        public ForcedUpdatePackageDexOptimizer(com.android.server.pm.Installer installer, java.lang.Object obj, android.content.Context context, java.lang.String str) {
            super(installer, obj, context, str);
        }

        public ForcedUpdatePackageDexOptimizer(com.android.server.pm.PackageDexOptimizer packageDexOptimizer) {
            super(packageDexOptimizer);
        }

        @Override // com.android.server.pm.PackageDexOptimizer
        protected int adjustDexoptNeeded(int i) {
            if (i == 0) {
                return -3;
            }
            return i;
        }

        @Override // com.android.server.pm.PackageDexOptimizer
        protected int adjustDexoptFlags(int i) {
            return i | 64;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private com.android.server.pm.Installer getInstallerLI() {
        return this.mInstaller;
    }

    private com.android.server.pm.Installer getInstallerWithoutLock() {
        return this.mInstaller;
    }
}
