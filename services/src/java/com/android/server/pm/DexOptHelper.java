package com.android.server.pm;

/* loaded from: classes2.dex */
public final class DexOptHelper {
    private static final long SEVEN_DAYS_IN_MILLISECONDS = 604800000;
    private static boolean sArtManagerLocalIsInitialized = false;
    private volatile long mBootDexoptStartTime;
    private final com.android.server.pm.PackageManagerService mPm;

    DexOptHelper(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    private static java.lang.String getPrebuildProfilePath(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return androidPackage.getBaseApkPath() + ".prof";
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0113 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] performDexOptUpgrade(java.util.List<com.android.server.pm.pkg.PackageStateInternal> list, int i, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        boolean z2;
        int i2;
        int i3;
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        list.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : list) {
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
            if ((this.mPm.isFirstBoot() || this.mPm.isDeviceUpgrading()) && packageStateInternal.isSystem()) {
                java.io.File file = new java.io.File(getPrebuildProfilePath(androidPackage));
                if (file.exists()) {
                    try {
                        if (!this.mPm.mInstaller.copySystemProfile(file.getAbsolutePath(), androidPackage.getUid(), androidPackage.getPackageName(), android.content.pm.dex.ArtManager.getProfileName((java.lang.String) null))) {
                            android.util.Log.e("PackageManager", "Installer failed to copy system profile!");
                        }
                    } catch (com.android.server.pm.Installer.InstallerException | java.lang.RuntimeException e) {
                        android.util.Log.e("PackageManager", "Failed to copy profile " + file.getAbsolutePath() + " ", e);
                    }
                } else {
                    com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(androidPackage.getPackageName());
                    if (disabledSystemPkgLPr != null && disabledSystemPkgLPr.getPkg().isStub()) {
                        java.io.File file2 = new java.io.File(getPrebuildProfilePath(disabledSystemPkgLPr.getPkg()).replace(com.android.server.pm.PackageManagerService.STUB_SUFFIX, ""));
                        if (file2.exists()) {
                            try {
                                if (!this.mPm.mInstaller.copySystemProfile(file2.getAbsolutePath(), androidPackage.getUid(), androidPackage.getPackageName(), android.content.pm.dex.ArtManager.getProfileName((java.lang.String) null))) {
                                    android.util.Log.e("PackageManager", "Failed to copy system profile for stub package!");
                                    z2 = false;
                                } else {
                                    z2 = true;
                                }
                            } catch (com.android.server.pm.Installer.InstallerException | java.lang.RuntimeException e2) {
                                android.util.Log.e("PackageManager", "Failed to copy profile " + file2.getAbsolutePath() + " ", e2);
                            }
                            if (this.mPm.mPackageDexOptimizer.canOptimizePackage(androidPackage)) {
                                i5++;
                            } else {
                                if (!z2) {
                                    i2 = i;
                                } else {
                                    i2 = 9;
                                }
                                int i7 = z ? 4 : 0;
                                java.lang.String compilerFilterForReason = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i2);
                                if (dalvik.system.DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason)) {
                                    i7 |= 1;
                                }
                                if (i != 0) {
                                    i3 = i7;
                                } else {
                                    i3 = i7 | 1024;
                                }
                                int performDexOptTraced = performDexOptTraced(new com.android.server.pm.dex.DexoptOptions(androidPackage.getPackageName(), i2, compilerFilterForReason, null, i3));
                                switch (performDexOptTraced) {
                                    case -1:
                                        i6++;
                                        break;
                                    case 0:
                                        i5++;
                                        break;
                                    case 1:
                                        i4++;
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        android.util.Log.e("PackageManager", "Unexpected dexopt return code " + performDexOptTraced);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            z2 = false;
            if (this.mPm.mPackageDexOptimizer.canOptimizePackage(androidPackage)) {
            }
        }
        return new int[]{i4, i5, i6};
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:26:0x0096
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private void checkAndDexOptSystemUi(int r11) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        /*
            r10 = this;
            com.android.server.pm.PackageManagerService r0 = r10.mPm
            com.android.server.pm.Computer r0 = r0.snapshotComputer()
            com.android.server.pm.PackageManagerService r1 = r10.mPm
            android.content.Context r1 = r1.mContext
            r2 = 17039418(0x104003a, float:2.4244734E-38)
            java.lang.String r1 = r1.getString(r2)
            com.android.server.pm.pkg.AndroidPackage r0 = r0.getPackage(r1)
            if (r0 != 0) goto L33
            java.lang.String r11 = "PackageManager"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "System UI package "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r1 = " is not found for dexopting"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r11, r0)
            return
        L33:
            java.lang.String r2 = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(r11)
            java.lang.String r3 = "dalvik.vm.systemuicompilerfilter"
            java.lang.String r2 = android.os.SystemProperties.get(r3, r2)
            boolean r3 = dalvik.system.DexFile.isProfileGuidedCompilerFilter(r2)
            if (r3 == 0) goto Lb9
            java.lang.String r3 = "verify"
            java.io.File r4 = new java.io.File
            java.lang.String r5 = getPrebuildProfilePath(r0)
            r4.<init>(r5)
            boolean r5 = r4.exists()
            if (r5 == 0) goto Lb7
            com.android.server.pm.PackageManagerService r5 = r10.mPm     // Catch: java.lang.Throwable -> L99
            java.lang.Object r5 = r5.mInstallLock     // Catch: java.lang.Throwable -> L99
            monitor-enter(r5)     // Catch: java.lang.Throwable -> L99
            com.android.server.pm.PackageManagerService r6 = r10.mPm     // Catch: java.lang.Throwable -> L96
            com.android.server.pm.Installer r6 = r6.mInstaller     // Catch: java.lang.Throwable -> L96
            java.lang.String r7 = r4.getAbsolutePath()     // Catch: java.lang.Throwable -> L96
            int r8 = r0.getUid()     // Catch: java.lang.Throwable -> L96
            java.lang.String r0 = r0.getPackageName()     // Catch: java.lang.Throwable -> L96
            r9 = 0
            java.lang.String r9 = android.content.pm.dex.ArtManager.getProfileName(r9)     // Catch: java.lang.Throwable -> L96
            boolean r0 = r6.copySystemProfile(r7, r8, r0, r9)     // Catch: java.lang.Throwable -> L96
            if (r0 == 0) goto L76
            goto L91
        L76:
            java.lang.String r0 = "PackageManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r2.<init>()     // Catch: java.lang.Throwable -> L96
            java.lang.String r6 = "Failed to copy profile "
            r2.append(r6)     // Catch: java.lang.Throwable -> L96
            java.lang.String r6 = r4.getAbsolutePath()     // Catch: java.lang.Throwable -> L96
            r2.append(r6)     // Catch: java.lang.Throwable -> L96
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L96
            android.util.Log.e(r0, r2)     // Catch: java.lang.Throwable -> L96
            r2 = r3
        L91:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L93
            goto Lb8
        L93:
            r0 = move-exception
            r3 = r2
            goto L97
        L96:
            r0 = move-exception
        L97:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L96
            throw r0     // Catch: java.lang.Throwable -> L99 java.lang.Throwable -> L99
        L99:
            r0 = move-exception
            java.lang.String r2 = "PackageManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to copy profile "
            r5.append(r6)
            java.lang.String r4 = r4.getAbsolutePath()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.e(r2, r4, r0)
            r2 = r3
            goto Lb8
        Lb7:
            r2 = r3
        Lb8:
            goto Lba
        Lb9:
        Lba:
            r10.performDexoptPackage(r1, r11, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.DexOptHelper.checkAndDexOptSystemUi(int):void");
    }

    private void dexoptLauncher(int i) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        for (java.lang.String str : ((android.app.role.RoleManager) this.mPm.mContext.getSystemService(android.app.role.RoleManager.class)).getRoleHolders("android.app.role.HOME")) {
            if (snapshotComputer.getPackage(str) == null) {
                android.util.Log.w("PackageManager", "Launcher package " + str + " is not found for dexopting");
            } else {
                performDexoptPackage(str, i, "speed-profile");
            }
        }
    }

    private void performDexoptPackage(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        int i2;
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        if (dalvik.system.DexFile.isProfileGuidedCompilerFilter(str2)) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        performDexOptTraced(new com.android.server.pm.dex.DexoptOptions(str, i, str2, null, i2));
    }

    public void performPackageDexOptUpgradeIfNeeded() {
        int i;
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request package update");
        if (this.mPm.isFirstBoot()) {
            i = 0;
        } else if (this.mPm.isDeviceUpgrading()) {
            i = 1;
        } else if (hasBcpApexesChanged()) {
            i = 13;
        } else {
            return;
        }
        android.util.Log.i("PackageManager", "Starting boot dexopt for reason " + com.android.server.pm.dex.DexoptOptions.convertToArtServiceDexoptReason(i));
        long nanoTime = java.lang.System.nanoTime();
        if (useArtService()) {
            this.mBootDexoptStartTime = nanoTime;
            getArtManagerLocal().onBoot(com.android.server.pm.dex.DexoptOptions.convertToArtServiceDexoptReason(i), (java.util.concurrent.Executor) null, (java.util.function.Consumer) null);
            return;
        }
        try {
            checkAndDexOptSystemUi(i);
            dexoptLauncher(i);
            if (i != 1 && i != 0) {
                return;
            }
            int[] performDexOptUpgrade = performDexOptUpgrade(getPackagesForDexopt(this.mPm.snapshotComputer().getPackageStates().values(), this.mPm), i, false);
            reportBootDexopt(nanoTime, performDexOptUpgrade[0], performDexOptUpgrade[1], performDexOptUpgrade[2]);
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportBootDexopt(long j, int i, int i2, int i3) {
        int seconds = (int) java.util.concurrent.TimeUnit.NANOSECONDS.toSeconds(java.lang.System.nanoTime() - j);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        com.android.internal.logging.MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_dexopted", i);
        com.android.internal.logging.MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_skipped", i2);
        com.android.internal.logging.MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_failed", i3);
        com.android.internal.logging.MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_total", getOptimizablePackages(snapshotComputer).size());
        com.android.internal.logging.MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_time_s", seconds);
    }

    public java.util.List<java.lang.String> getOptimizablePackages(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mPm.forEachPackageState(computer, new java.util.function.Consumer() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.DexOptHelper.this.lambda$getOptimizablePackages$0(arrayList, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOptimizablePackages$0(java.util.ArrayList arrayList, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
        if (pkg != null && this.mPm.mPackageDexOptimizer.canOptimizePackage(pkg)) {
            arrayList.add(packageStateInternal.getPackageName());
        }
    }

    boolean performDexOpt(com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        int performDexOptWithStatus;
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        if (snapshotComputer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null || snapshotComputer.isInstantApp(dexoptOptions.getPackageName(), android.os.UserHandle.getCallingUserId())) {
            return false;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = snapshotComputer.getPackage(dexoptOptions.getPackageName());
        if (androidPackage != null && androidPackage.isApex()) {
            return true;
        }
        if (dexoptOptions.isDexoptOnlySecondaryDex()) {
            if (useArtService()) {
                performDexOptWithStatus = performDexOptWithArtService(dexoptOptions, 0);
            } else {
                try {
                    return this.mPm.getDexManager().dexoptSecondaryDex(dexoptOptions);
                } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
        } else {
            performDexOptWithStatus = performDexOptWithStatus(dexoptOptions);
        }
        return performDexOptWithStatus != -1;
    }

    int performDexOptWithStatus(com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        return performDexOptTraced(dexoptOptions);
    }

    private int performDexOptTraced(com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        android.os.Trace.traceBegin(16384L, "dexopt");
        try {
            return performDexOptInternal(dexoptOptions);
        } finally {
            android.os.Trace.traceEnd(16384L);
        }
    }

    private int performDexOptInternal(com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        if (useArtService()) {
            return performDexOptWithArtService(dexoptOptions, 4);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPm.mPackages.get(dexoptOptions.getPackageName());
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(dexoptOptions.getPackageName());
                if (androidPackage == null || packageLPr == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                if (androidPackage.isApex()) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                this.mPm.getPackageUsage().maybeWriteAsync(this.mPm.mSettings.getPackagesLocked());
                this.mPm.mCompilerStats.maybeWriteAsync();
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    try {
                        return performDexOptInternalWithDependenciesLI(androidPackage, packageLPr, dexoptOptions);
                    } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private int performDexOptWithArtService(com.android.server.pm.dex.DexoptOptions dexoptOptions, int i) {
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            com.android.server.pm.pkg.PackageState packageState = withFilteredSnapshot.getPackageState(dexoptOptions.getPackageName());
            if (packageState != null) {
                if (packageState.getAndroidPackage() != null) {
                    int convertToDexOptResult = convertToDexOptResult(getArtManagerLocal().dexoptPackage(withFilteredSnapshot, dexoptOptions.getPackageName(), dexoptOptions.convertToDexoptParams(i)));
                    withFilteredSnapshot.close();
                    return convertToDexOptResult;
                }
                withFilteredSnapshot.close();
                return -1;
            }
            withFilteredSnapshot.close();
            return -1;
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
    }

    private int performDexOptInternalWithDependenciesLI(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.PackageDexOptimizer packageDexOptimizer;
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(androidPackage.getPackageName())) {
            throw new java.lang.IllegalArgumentException("Cannot dexopt the system server");
        }
        if (dexoptOptions.isForce()) {
            packageDexOptimizer = new com.android.server.pm.PackageDexOptimizer.ForcedUpdatePackageDexOptimizer(this.mPm.mPackageDexOptimizer);
        } else {
            packageDexOptimizer = this.mPm.mPackageDexOptimizer;
        }
        java.util.List<android.content.pm.SharedLibraryInfo> findSharedLibraries = com.android.server.pm.SharedLibraryUtils.findSharedLibraries(packageStateInternal);
        java.lang.String[] appDexInstructionSets = com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi());
        if (!findSharedLibraries.isEmpty()) {
            com.android.server.pm.dex.DexoptOptions dexoptOptions2 = new com.android.server.pm.dex.DexoptOptions(dexoptOptions.getPackageName(), dexoptOptions.getCompilationReason(), dexoptOptions.getCompilerFilter(), dexoptOptions.getSplitName(), dexoptOptions.getFlags() | 64);
            for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : findSharedLibraries) {
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                com.android.server.pm.pkg.AndroidPackage androidPackage2 = snapshotComputer.getPackage(sharedLibraryInfo.getPackageName());
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = snapshotComputer.getPackageStateInternal(sharedLibraryInfo.getPackageName());
                if (androidPackage2 != null && packageStateInternal2 != null) {
                    packageDexOptimizer.performDexOpt(androidPackage2, packageStateInternal2, appDexInstructionSets, this.mPm.getOrCreateCompilerPackageStats(androidPackage2), this.mPm.getDexManager().getPackageUseInfoOrDefault(androidPackage2.getPackageName()), dexoptOptions2);
                }
            }
        }
        return packageDexOptimizer.performDexOpt(androidPackage, packageStateInternal, appDexInstructionSets, this.mPm.getOrCreateCompilerPackageStats(androidPackage), this.mPm.getDexManager().getPackageUseInfoOrDefault(androidPackage.getPackageName()), dexoptOptions);
    }

    @java.lang.Deprecated
    public void forceDexOpt(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("forceDexOpt");
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (packageStateInternal == null || pkg == null) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        if (pkg.isApex()) {
            throw new java.lang.IllegalArgumentException("Can't dexopt APEX package: " + str);
        }
        android.os.Trace.traceBegin(16384L, "dexopt");
        int performDexOptInternalWithDependenciesLI = performDexOptInternalWithDependenciesLI(pkg, packageStateInternal, new com.android.server.pm.dex.DexoptOptions(str, 12, com.android.server.pm.PackageManagerServiceCompilerMapping.getDefaultCompilerFilter(), null, 6));
        android.os.Trace.traceEnd(16384L);
        if (performDexOptInternalWithDependenciesLI != 1) {
            throw new java.lang.IllegalStateException("Failed to dexopt: " + performDexOptInternalWithDependenciesLI);
        }
    }

    public boolean performDexOptMode(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2, boolean z, boolean z2, java.lang.String str3) {
        int i;
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell() && !isCallerInstallerForPackage(computer, str)) {
            throw new java.lang.SecurityException("performDexOptMode");
        }
        int i2 = (z2 ? 4 : 0) | (z ? 2 : 0);
        if (!dalvik.system.DexFile.isProfileGuidedCompilerFilter(str2)) {
            i = i2;
        } else {
            i = i2 | 1;
        }
        return performDexOpt(new com.android.server.pm.dex.DexoptOptions(str, 12, str2, str3, i));
    }

    private boolean isCallerInstallerForPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = computer.getPackageStateInternal(str);
        return (packageStateInternal2 == null || (packageStateInternal = computer.getPackageStateInternal(packageStateInternal2.getInstallSource().mInstallerPackageName)) == null || packageStateInternal.getPkg().getUid() != android.os.Binder.getCallingUid()) ? false : true;
    }

    public boolean performDexOptSecondary(java.lang.String str, java.lang.String str2, boolean z) {
        return performDexOpt(new com.android.server.pm.dex.DexoptOptions(str, 12, str2, null, (z ? 2 : 0) | 13));
    }

    public static java.util.List<com.android.server.pm.pkg.PackageStateInternal> getPackagesForDexopt(java.util.Collection<? extends com.android.server.pm.pkg.PackageStateInternal> collection, com.android.server.pm.PackageManagerService packageManagerService) {
        return getPackagesForDexopt(collection, packageManagerService, false);
    }

    public static java.util.List<com.android.server.pm.pkg.PackageStateInternal> getPackagesForDexopt(java.util.Collection<? extends com.android.server.pm.pkg.PackageStateInternal> collection, com.android.server.pm.PackageManagerService packageManagerService, boolean z) {
        java.util.function.Predicate predicate;
        java.util.function.Predicate predicate2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(collection);
        arrayList2.removeIf(com.android.server.pm.PackageManagerServiceUtils.REMOVE_IF_NULL_PKG);
        arrayList2.removeIf(com.android.server.pm.PackageManagerServiceUtils.REMOVE_IF_APEX_PKG);
        java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayList2.size());
        com.android.server.pm.Computer snapshotComputer = packageManagerService.snapshotComputer();
        applyPackageFilter(snapshotComputer, new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getPackagesForDexopt$1;
                lambda$getPackagesForDexopt$1 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$1((com.android.server.pm.pkg.PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$1;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        final android.util.ArraySet<java.lang.String> packageNamesForIntent = getPackageNamesForIntent(new android.content.Intent("android.intent.action.PRE_BOOT_COMPLETED"), 0);
        applyPackageFilter(snapshotComputer, new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getPackagesForDexopt$2;
                lambda$getPackagesForDexopt$2 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$2(packageNamesForIntent, (com.android.server.pm.pkg.PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$2;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        final com.android.server.pm.dex.DexManager dexManager = packageManagerService.getDexManager();
        applyPackageFilter(snapshotComputer, new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getPackagesForDexopt$3;
                lambda$getPackagesForDexopt$3 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$3(com.android.server.pm.dex.DexManager.this, (com.android.server.pm.pkg.PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$3;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        if (!arrayList2.isEmpty() && packageManagerService.isHistoricalPackageUsageAvailable()) {
            if (z) {
                android.util.Log.i("PackageManager", "Looking at historical package use");
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = (com.android.server.pm.pkg.PackageStateInternal) java.util.Collections.max(arrayList2, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(java.lang.Object obj) {
                    long lambda$getPackagesForDexopt$4;
                    lambda$getPackagesForDexopt$4 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$4((com.android.server.pm.pkg.PackageStateInternal) obj);
                    return lambda$getPackagesForDexopt$4;
                }
            }));
            if (z) {
                android.util.Log.i("PackageManager", "Taking package " + packageStateInternal.getPackageName() + " as reference in time use");
            }
            long latestForegroundPackageUseTimeInMills = packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
            if (latestForegroundPackageUseTimeInMills != 0) {
                final long j = latestForegroundPackageUseTimeInMills - 604800000;
                predicate2 = new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getPackagesForDexopt$5;
                        lambda$getPackagesForDexopt$5 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$5(j, (com.android.server.pm.pkg.PackageStateInternal) obj);
                        return lambda$getPackagesForDexopt$5;
                    }
                };
            } else {
                predicate2 = new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getPackagesForDexopt$6;
                        lambda$getPackagesForDexopt$6 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$6((com.android.server.pm.pkg.PackageStateInternal) obj);
                        return lambda$getPackagesForDexopt$6;
                    }
                };
            }
            sortPackagesByUsageDate(arrayList2, packageManagerService);
            predicate = predicate2;
        } else {
            predicate = new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getPackagesForDexopt$7;
                    lambda$getPackagesForDexopt$7 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$7((com.android.server.pm.pkg.PackageStateInternal) obj);
                    return lambda$getPackagesForDexopt$7;
                }
            };
        }
        applyPackageFilter(snapshotComputer, predicate, arrayList, arrayList2, arrayList3, packageManagerService);
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getPackagesForDexopt$8;
                lambda$getPackagesForDexopt$8 = com.android.server.pm.DexOptHelper.lambda$getPackagesForDexopt$8((com.android.server.pm.pkg.PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$8;
            }
        });
        if (z) {
            android.util.Log.i("PackageManager", "Packages to be dexopted: " + packagesToString(arrayList));
            android.util.Log.i("PackageManager", "Packages skipped from dexopt: " + packagesToString(arrayList2));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$1(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getPkg().isCoreApp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$2(android.util.ArraySet arraySet, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return arraySet.contains(packageStateInternal.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$3(com.android.server.pm.dex.DexManager dexManager, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return dexManager.getPackageUseInfoOrDefault(packageStateInternal.getPackageName()).isAnyCodePathUsedByOtherApps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long lambda$getPackagesForDexopt$4(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$5(long j, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills() >= j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$6(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$7(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPackagesForDexopt$8(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageStateInternal.getPackageName());
    }

    private static void applyPackageFilter(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.function.Predicate<com.android.server.pm.pkg.PackageStateInternal> predicate, java.util.Collection<com.android.server.pm.pkg.PackageStateInternal> collection, java.util.Collection<com.android.server.pm.pkg.PackageStateInternal> collection2, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.PackageStateInternal> list, com.android.server.pm.PackageManagerService packageManagerService) {
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : collection2) {
            if (predicate.test(packageStateInternal)) {
                list.add(packageStateInternal);
            }
        }
        sortPackagesByUsageDate(list, packageManagerService);
        collection2.removeAll(list);
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 : list) {
            collection.add(packageStateInternal2);
            java.util.List<com.android.server.pm.pkg.PackageStateInternal> findSharedNonSystemLibraries = computer.findSharedNonSystemLibraries(packageStateInternal2);
            if (!findSharedNonSystemLibraries.isEmpty()) {
                findSharedNonSystemLibraries.removeAll(collection);
                collection.addAll(findSharedNonSystemLibraries);
                collection2.removeAll(findSharedNonSystemLibraries);
            }
        }
        list.clear();
    }

    private static void sortPackagesByUsageDate(java.util.List<com.android.server.pm.pkg.PackageStateInternal> list, com.android.server.pm.PackageManagerService packageManagerService) {
        if (!packageManagerService.isHistoricalPackageUsageAvailable()) {
            return;
        }
        java.util.Collections.sort(list, new java.util.Comparator() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda9
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$sortPackagesByUsageDate$9;
                lambda$sortPackagesByUsageDate$9 = com.android.server.pm.DexOptHelper.lambda$sortPackagesByUsageDate$9((com.android.server.pm.pkg.PackageStateInternal) obj, (com.android.server.pm.pkg.PackageStateInternal) obj2);
                return lambda$sortPackagesByUsageDate$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortPackagesByUsageDate$9(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.PackageStateInternal packageStateInternal2) {
        return java.lang.Long.compare(packageStateInternal2.getTransientState().getLatestForegroundPackageUseTimeInMills(), packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills());
    }

    private static android.util.ArraySet<java.lang.String> getPackageNamesForIntent(android.content.Intent intent, int i) {
        java.util.List list;
        try {
            list = android.app.AppGlobals.getPackageManager().queryIntentReceivers(intent, (java.lang.String) null, 0L, i).getList();
        } catch (android.os.RemoteException e) {
            list = null;
        }
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        if (list != null) {
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                arraySet.add(((android.content.pm.ResolveInfo) it.next()).activityInfo.packageName);
            }
        }
        return arraySet;
    }

    public static java.lang.String packagesToString(java.util.List<com.android.server.pm.pkg.PackageStateInternal> list) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(list.get(i).getPackageName());
        }
        return sb.toString();
    }

    public static void requestCopyPreoptedFiles() {
        if (android.os.SystemProperties.getInt("ro.cp_system_other_odex", 0) == 1) {
            android.os.SystemProperties.set("sys.cppreopt", "requested");
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j = 100000 + uptimeMillis;
            long j2 = uptimeMillis;
            while (true) {
                if (android.os.SystemProperties.get("sys.cppreopt").equals("finished")) {
                    break;
                }
                try {
                    java.lang.Thread.sleep(100L);
                } catch (java.lang.InterruptedException e) {
                }
                j2 = android.os.SystemClock.uptimeMillis();
                if (j2 > j) {
                    android.os.SystemProperties.set("sys.cppreopt", "timed-out");
                    android.util.Slog.wtf("PackageManager", "cppreopt did not finish!");
                    break;
                }
            }
            android.util.Slog.i("PackageManager", "cppreopts took " + (j2 - uptimeMillis) + " ms");
        }
    }

    void controlDexOptBlocking(boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mPm.mPackageDexOptimizer.controlDexOptBlocking(z);
    }

    public static void dumpDexoptState(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            if (str != null) {
                try {
                    getArtManagerLocal().dumpPackage(indentingPrintWriter, withFilteredSnapshot, str);
                } catch (java.lang.IllegalArgumentException e) {
                    indentingPrintWriter.println(e);
                }
            } else {
                getArtManagerLocal().dump(indentingPrintWriter, withFilteredSnapshot);
            }
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
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
    }

    private static java.util.List<java.lang.String> getBcpApexes() {
        java.lang.String str = java.lang.System.getenv("BOOTCLASSPATH");
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.e("PackageManager", "Unable to get BOOTCLASSPATH");
            return java.util.List.of();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : str.split(":")) {
            java.nio.file.Path path = java.nio.file.Paths.get(str2, new java.lang.String[0]);
            if (path.getNameCount() >= 2 && path.getName(0).toString().equals("apex")) {
                arrayList.add(path.getName(1).toString());
            }
        }
        return arrayList;
    }

    private static boolean hasBcpApexesChanged() {
        java.util.HashSet hashSet = new java.util.HashSet(getBcpApexes());
        for (com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo : com.android.server.pm.ApexManager.getInstance().getActiveApexInfos()) {
            if (hashSet.contains(activeApexInfo.apexModuleName) && activeApexInfo.activeApexChanged) {
                return true;
            }
        }
        return false;
    }

    public static boolean useArtService() {
        return android.os.SystemProperties.getBoolean("dalvik.vm.useartservice", false);
    }

    @android.annotation.Nullable
    public static com.android.server.art.DexUseManagerLocal getDexUseManagerLocal() {
        if (!useArtService()) {
            return null;
        }
        try {
            return (com.android.server.art.DexUseManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.art.DexUseManagerLocal.class);
        } catch (com.android.server.LocalManagerRegistry.ManagerNotFoundException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private class DexoptDoneHandler implements com.android.server.art.ArtManagerLocal.DexoptDoneCallback {
        private DexoptDoneHandler() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onDexoptDone(@android.annotation.NonNull com.android.server.art.model.DexoptResult dexoptResult) {
            char c;
            java.lang.String reason = dexoptResult.getReason();
            switch (reason.hashCode()) {
                case -1205769507:
                    if (reason.equals("boot-after-mainline-update")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -587828592:
                    if (reason.equals("boot-after-ota")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -207505425:
                    if (reason.equals("first-boot")) {
                        c = 0;
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
                case 1:
                case 2:
                    java.util.Iterator it = dexoptResult.getPackageDexoptResults().iterator();
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    while (it.hasNext()) {
                        switch (((com.android.server.art.model.DexoptResult.PackageDexoptResult) it.next()).getStatus()) {
                            case 10:
                                i2++;
                                break;
                            case 20:
                                i++;
                                break;
                            case 30:
                                i3++;
                                break;
                        }
                    }
                    com.android.server.pm.DexOptHelper.this.reportBootDexopt(com.android.server.pm.DexOptHelper.this.mBootDexoptStartTime, i, i2, i3);
                    break;
            }
            for (com.android.server.art.model.DexoptResult.PackageDexoptResult packageDexoptResult : dexoptResult.getPackageDexoptResults()) {
                com.android.server.pm.CompilerStats.PackageStats orCreateCompilerPackageStats = com.android.server.pm.DexOptHelper.this.mPm.getOrCreateCompilerPackageStats(packageDexoptResult.getPackageName());
                for (com.android.server.art.model.DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult : packageDexoptResult.getDexContainerFileDexoptResults()) {
                    orCreateCompilerPackageStats.setCompileTime(dexContainerFileDexoptResult.getDexContainerFile(), dexContainerFileDexoptResult.getDex2oatWallTimeMillis());
                }
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.DexOptHelper.this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.DexOptHelper.this.mPm.getPackageUsage().maybeWriteAsync(com.android.server.pm.DexOptHelper.this.mPm.mSettings.getPackagesLocked());
                    com.android.server.pm.DexOptHelper.this.mPm.mCompilerStats.maybeWriteAsync();
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (dexoptResult.getReason().equals("inactive")) {
                for (com.android.server.art.model.DexoptResult.PackageDexoptResult packageDexoptResult2 : dexoptResult.getPackageDexoptResults()) {
                    if (packageDexoptResult2.getStatus() == 20) {
                        long j = 0;
                        long j2 = 0;
                        for (com.android.server.art.model.DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult2 : packageDexoptResult2.getDexContainerFileDexoptResults()) {
                            long length = new java.io.File(dexContainerFileDexoptResult2.getDexContainerFile()).length();
                            j2 += dexContainerFileDexoptResult2.getSizeBytes() + length;
                            j += dexContainerFileDexoptResult2.getSizeBeforeBytes() + length;
                        }
                        com.android.internal.util.FrameworkStatsLog.write(128, packageDexoptResult2.getPackageName(), j, j2, false);
                    }
                }
            }
            android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
            for (com.android.server.art.model.DexoptResult.PackageDexoptResult packageDexoptResult3 : dexoptResult.getPackageDexoptResults()) {
                if (packageDexoptResult3.hasUpdatedArtifacts()) {
                    arraySet.add(packageDexoptResult3.getPackageName());
                }
            }
            if (!arraySet.isEmpty()) {
                ((com.android.server.PinnerService) com.android.server.LocalServices.getService(com.android.server.PinnerService.class)).update(arraySet, false);
            }
        }
    }

    public static void initializeArtManagerLocal(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.PackageManagerService packageManagerService) {
        if (!useArtService()) {
            return;
        }
        final com.android.server.art.ArtManagerLocal artManagerLocal = new com.android.server.art.ArtManagerLocal(context);
        com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0 systemServerInitThreadPool$$ExternalSyntheticLambda0 = new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0();
        com.android.server.pm.DexOptHelper dexOptHelper = packageManagerService.getDexOptHelper();
        java.util.Objects.requireNonNull(dexOptHelper);
        artManagerLocal.addDexoptDoneCallback(false, systemServerInitThreadPool$$ExternalSyntheticLambda0, new com.android.server.pm.DexOptHelper.DexoptDoneHandler());
        com.android.server.LocalManagerRegistry.addManager(com.android.server.art.ArtManagerLocal.class, artManagerLocal);
        sArtManagerLocalIsInitialized = true;
        context.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.pm.DexOptHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                context2.unregisterReceiver(this);
                artManagerLocal.scheduleBackgroundDexoptJob();
            }
        }, new android.content.IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED"));
    }

    public static boolean artManagerLocalIsInitialized() {
        return sArtManagerLocalIsInitialized;
    }

    @android.annotation.NonNull
    public static com.android.server.art.ArtManagerLocal getArtManagerLocal() {
        try {
            return (com.android.server.art.ArtManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.art.ArtManagerLocal.class);
        } catch (com.android.server.LocalManagerRegistry.ManagerNotFoundException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static int convertToDexOptResult(com.android.server.art.model.DexoptResult dexoptResult) {
        int finalStatus = dexoptResult.getFinalStatus();
        switch (finalStatus) {
            case 10:
                return 0;
            case 20:
                return 1;
            case 30:
                return -1;
            case 40:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("DexoptResult for " + ((com.android.server.art.model.DexoptResult.PackageDexoptResult) dexoptResult.getPackageDexoptResults().get(0)).getPackageName() + " has unsupported status " + finalStatus);
        }
    }

    static com.android.server.pm.dex.DexoptOptions getDexoptOptionsByInstallRequest(com.android.server.pm.InstallRequest installRequest, com.android.server.pm.dex.DexManager dexManager) {
        return new com.android.server.pm.dex.DexoptOptions(installRequest.getScannedPackageSetting().getPackageName(), dexManager.getCompilationReasonForInstallScenario(installRequest.getInstallScenario()), (installRequest.getInstallReason() == 2 || installRequest.getInstallReason() == 3 ? 2048 : 0) | com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_CANCEL);
    }

    static com.android.server.art.model.DexoptResult dexoptPackageUsingArtService(com.android.server.pm.InstallRequest installRequest, com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        java.lang.String packageName = installRequest.getScannedPackageSetting().getPackageName();
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = ((com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.pm.PackageManagerLocal.class)).withFilteredSnapshot();
        try {
            int i = 0;
            if (((installRequest.getInstallFlags() & 268435456) != 0) && android.content.pm.Flags.useArtServiceV2()) {
                i = 128;
            }
            com.android.server.art.model.DexoptResult dexoptPackage = getArtManagerLocal().dexoptPackage(withFilteredSnapshot, packageName, dexoptOptions.convertToDexoptParams(i));
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
            return dexoptPackage;
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
    }

    static boolean shouldPerformDexopt(com.android.server.pm.InstallRequest installRequest, com.android.server.pm.dex.DexoptOptions dexoptOptions, android.content.Context context) {
        boolean z = (installRequest.getScanFlags() & 67108864) != 0;
        boolean z2 = (installRequest.getScanFlags() & 8192) != 0;
        com.android.server.pm.PackageSetting scannedPackageSetting = installRequest.getScannedPackageSetting();
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = scannedPackageSetting.getPkg();
        return ((z2 && android.provider.Settings.Global.getInt(context.getContentResolver(), "instant_app_dexopt_enabled", 0) == 0) || pkg == null || pkg.isDebuggable() || android.os.incremental.IncrementalManager.isIncrementalPath(scannedPackageSetting.getPathString()) || !dexoptOptions.isCompilationEnabled() || z) ? false : true;
    }
}
