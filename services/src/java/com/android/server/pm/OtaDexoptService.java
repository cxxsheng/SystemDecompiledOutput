package com.android.server.pm;

/* loaded from: classes2.dex */
public class OtaDexoptService extends android.content.pm.IOtaDexopt.Stub {
    private static final long BULK_DELETE_THRESHOLD = 1073741824;
    private static final boolean DEBUG_DEXOPT = true;
    private static final java.lang.String TAG = "OTADexopt";
    private long availableSpaceAfterBulkDelete;
    private long availableSpaceAfterDexopt;
    private long availableSpaceBefore;
    private int completeSize;
    private int dexoptCommandCountExecuted;
    private int dexoptCommandCountTotal;
    private int importantPackageCount;
    private final android.content.Context mContext;
    private java.util.List<java.lang.String> mDexoptCommands;
    private final com.android.server.pm.PackageManagerService mPackageManagerService;
    private final com.android.internal.logging.MetricsLogger metricsLogger = new com.android.internal.logging.MetricsLogger();
    private long otaDexoptTimeStart;
    private int otherPackageCount;

    public OtaDexoptService(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mContext = context;
        this.mPackageManagerService = packageManagerService;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.pm.OtaDexoptService] */
    public static com.android.server.pm.OtaDexoptService main(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService) {
        ?? otaDexoptService = new com.android.server.pm.OtaDexoptService(context, packageManagerService);
        android.os.ServiceManager.addService("otadexopt", (android.os.IBinder) otaDexoptService);
        otaDexoptService.moveAbArtifacts(packageManagerService.mInstaller);
        return otaDexoptService;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.pm.OtaDexoptShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public synchronized void prepare() throws android.os.RemoteException {
        try {
            if (this.mDexoptCommands != null) {
                throw new java.lang.IllegalStateException("already called prepare()");
            }
            java.util.function.Predicate<? super com.android.server.pm.pkg.PackageStateInternal> predicate = new java.util.function.Predicate() { // from class: com.android.server.pm.OtaDexoptService$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$prepare$0;
                    lambda$prepare$0 = com.android.server.pm.OtaDexoptService.lambda$prepare$0((com.android.server.pm.pkg.PackageStateInternal) obj);
                    return lambda$prepare$0;
                }
            };
            com.android.server.pm.Computer snapshotComputer = this.mPackageManagerService.snapshotComputer();
            java.util.Collection<? extends com.android.server.pm.pkg.PackageStateInternal> values = snapshotComputer.getPackageStates().values();
            java.util.List<com.android.server.pm.pkg.PackageStateInternal> packagesForDexopt = com.android.server.pm.DexOptHelper.getPackagesForDexopt(values, this.mPackageManagerService, true);
            packagesForDexopt.removeIf(predicate);
            java.util.ArrayList<com.android.server.pm.pkg.PackageStateInternal> arrayList = new java.util.ArrayList(values);
            arrayList.removeAll(packagesForDexopt);
            arrayList.removeIf(com.android.server.pm.PackageManagerServiceUtils.REMOVE_IF_NULL_PKG);
            arrayList.removeIf(com.android.server.pm.PackageManagerServiceUtils.REMOVE_IF_APEX_PKG);
            arrayList.removeIf(predicate);
            this.mDexoptCommands = new java.util.ArrayList((values.size() * 3) / 2);
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : packagesForDexopt) {
                this.mDexoptCommands.addAll(generatePackageDexopts(packageStateInternal.getPkg(), packageStateInternal, 10));
            }
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 : arrayList) {
                if (packageStateInternal2.getPkg().isCoreApp()) {
                    throw new java.lang.IllegalStateException("Found a core app that's not important");
                }
                this.mDexoptCommands.addAll(generatePackageDexopts(packageStateInternal2.getPkg(), packageStateInternal2, 0));
            }
            this.completeSize = this.mDexoptCommands.size();
            long availableSpace = getAvailableSpace();
            if (availableSpace < BULK_DELETE_THRESHOLD) {
                android.util.Log.i(TAG, "Low on space, deleting oat files in an attempt to free up space: " + com.android.server.pm.DexOptHelper.packagesToString(arrayList));
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    this.mPackageManagerService.deleteOatArtifactsOfPackage(snapshotComputer, ((com.android.server.pm.pkg.PackageStateInternal) it.next()).getPackageName());
                }
            }
            prepareMetricsLogging(packagesForDexopt.size(), arrayList.size(), availableSpace, getAvailableSpace());
            try {
                android.util.Log.d(TAG, "A/B OTA: lastUsed time = " + ((com.android.server.pm.pkg.PackageStateInternal) java.util.Collections.max(packagesForDexopt, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.pm.OtaDexoptService$$ExternalSyntheticLambda1
                    @Override // java.util.function.ToLongFunction
                    public final long applyAsLong(java.lang.Object obj) {
                        long lambda$prepare$1;
                        lambda$prepare$1 = com.android.server.pm.OtaDexoptService.lambda$prepare$1((com.android.server.pm.pkg.PackageStateInternal) obj);
                        return lambda$prepare$1;
                    }
                }))).getTransientState().getLatestForegroundPackageUseTimeInMills());
                android.util.Log.d(TAG, "A/B OTA: deprioritized packages:");
                for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 : arrayList) {
                    android.util.Log.d(TAG, "  " + packageStateInternal3.getPackageName() + " - " + packageStateInternal3.getTransientState().getLatestForegroundPackageUseTimeInMills());
                }
            } catch (java.lang.RuntimeException e) {
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$prepare$0(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageStateInternal.getPkg().getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long lambda$prepare$1(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
    }

    public synchronized void cleanup() throws android.os.RemoteException {
        android.util.Log.i(TAG, "Cleaning up OTA Dexopt state.");
        this.mDexoptCommands = null;
        this.availableSpaceAfterDexopt = getAvailableSpace();
        performMetricsLogging();
    }

    public synchronized boolean isDone() throws android.os.RemoteException {
        if (this.mDexoptCommands == null) {
            throw new java.lang.IllegalStateException("done() called before prepare()");
        }
        return this.mDexoptCommands.isEmpty();
    }

    public synchronized float getProgress() throws android.os.RemoteException {
        if (this.completeSize == 0) {
            return 1.0f;
        }
        return (this.completeSize - this.mDexoptCommands.size()) / this.completeSize;
    }

    public synchronized java.lang.String nextDexoptCommand() throws android.os.RemoteException {
        if (this.mDexoptCommands == null) {
            throw new java.lang.IllegalStateException("dexoptNextPackage() called before prepare()");
        }
        if (this.mDexoptCommands.isEmpty()) {
            return "(all done)";
        }
        java.lang.String remove = this.mDexoptCommands.remove(0);
        if (getAvailableSpace() > 0) {
            this.dexoptCommandCountExecuted++;
            android.util.Log.d(TAG, "Next command: " + remove);
            return remove;
        }
        android.util.Log.w(TAG, "Not enough space for OTA dexopt, stopping with " + (this.mDexoptCommands.size() + 1) + " commands left.");
        this.mDexoptCommands.clear();
        return "(no free space)";
    }

    private long getMainLowSpaceThreshold() {
        long storageLowBytes = android.os.storage.StorageManager.from(this.mContext).getStorageLowBytes(android.os.Environment.getDataDirectory());
        if (storageLowBytes == 0) {
            throw new java.lang.IllegalStateException("Invalid low memory threshold");
        }
        return storageLowBytes;
    }

    private long getAvailableSpace() {
        return android.os.Environment.getDataDirectory().getUsableSpace() - getMainLowSpaceThreshold();
    }

    private synchronized java.util.List<java.lang.String> generatePackageDexopts(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        final java.util.ArrayList arrayList;
        arrayList = new java.util.ArrayList();
        try {
            new com.android.server.pm.OtaDexoptService.OTADexoptPackageDexOptimizer(new com.android.server.pm.Installer(this.mContext, true) { // from class: com.android.server.pm.OtaDexoptService.1
                @Override // com.android.server.pm.Installer
                public boolean dexopt(java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, java.lang.String str3, int i3, @android.annotation.Nullable java.lang.String str4, int i4, java.lang.String str5, @android.annotation.Nullable java.lang.String str6, @android.annotation.Nullable java.lang.String str7, @android.annotation.Nullable java.lang.String str8, boolean z, int i5, @android.annotation.Nullable java.lang.String str9, @android.annotation.Nullable java.lang.String str10, @android.annotation.Nullable java.lang.String str11) throws com.android.server.pm.Installer.InstallerException {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    if (com.android.server.pm.DexOptHelper.useArtService() && (i4 & 32) != 0) {
                        throw new java.lang.IllegalArgumentException("Invalid OTA dexopt call for secondary dex");
                    }
                    sb.append("10 ");
                    sb.append("dexopt");
                    encodeParameter(sb, str);
                    encodeParameter(sb, java.lang.Integer.valueOf(i2));
                    encodeParameter(sb, str2);
                    encodeParameter(sb, str3);
                    encodeParameter(sb, java.lang.Integer.valueOf(i3));
                    encodeParameter(sb, str4);
                    encodeParameter(sb, java.lang.Integer.valueOf(i4));
                    encodeParameter(sb, str5);
                    encodeParameter(sb, str6);
                    encodeParameter(sb, str7);
                    encodeParameter(sb, str8);
                    encodeParameter(sb, java.lang.Boolean.valueOf(z));
                    encodeParameter(sb, java.lang.Integer.valueOf(i5));
                    encodeParameter(sb, str9);
                    encodeParameter(sb, str10);
                    encodeParameter(sb, str11);
                    arrayList.add(sb.toString());
                    return true;
                }

                private void encodeParameter(java.lang.StringBuilder sb, java.lang.Object obj) {
                    sb.append(' ');
                    if (obj == null) {
                        sb.append('!');
                        return;
                    }
                    java.lang.String valueOf = java.lang.String.valueOf(obj);
                    if (valueOf.indexOf(0) != -1 || valueOf.indexOf(32) != -1 || "!".equals(valueOf)) {
                        throw new java.lang.IllegalArgumentException("Invalid argument while executing " + obj);
                    }
                    sb.append(valueOf);
                }
            }, this.mPackageManagerService.mInstallLock, this.mContext).performDexOpt(androidPackage, packageStateInternal, null, null, this.mPackageManagerService.getDexManager().getPackageUseInfoOrDefault(androidPackage.getPackageName()), new com.android.server.pm.dex.DexoptOptions(androidPackage.getPackageName(), i, 4));
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
            android.util.Slog.wtf(TAG, e);
        }
        return arrayList;
    }

    public synchronized void dexoptNextPackage() throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException();
    }

    private void moveAbArtifacts(com.android.server.pm.Installer installer) {
        com.android.server.pm.OtaDexoptService otaDexoptService = this;
        if (otaDexoptService.mDexoptCommands != null) {
            throw new java.lang.IllegalStateException("Should not be ota-dexopting when trying to move.");
        }
        if (!otaDexoptService.mPackageManagerService.isDeviceUpgrading()) {
            android.util.Slog.d(TAG, "No upgrade, skipping A/B artifacts check.");
            return;
        }
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageStates();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < packageStates.size()) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i2);
            com.android.server.pm.pkg.AndroidPackage pkg = valueAt.getPkg();
            if (pkg != null && otaDexoptService.mPackageManagerService.mPackageDexOptimizer.canOptimizePackage(pkg)) {
                if (pkg.getPath() == null) {
                    android.util.Slog.w(TAG, "Package " + pkg + " can be optimized but has null codePath");
                } else if (!pkg.getPath().startsWith("/system") && !pkg.getPath().startsWith("/vendor") && !pkg.getPath().startsWith("/product") && !pkg.getPath().startsWith("/system_ext")) {
                    java.lang.String[] appDexInstructionSets = com.android.server.pm.InstructionSets.getAppDexInstructionSets(valueAt.getPrimaryCpuAbi(), valueAt.getSecondaryCpuAbi());
                    java.util.List<java.lang.String> allCodePathsExcludingResourceOnly = com.android.server.pm.parsing.pkg.AndroidPackageUtils.getAllCodePathsExcludingResourceOnly(pkg);
                    java.lang.String[] dexCodeInstructionSets = com.android.server.pm.InstructionSets.getDexCodeInstructionSets(appDexInstructionSets);
                    java.lang.String packageName = pkg.getPackageName();
                    int length = dexCodeInstructionSets.length;
                    int i4 = 0;
                    while (i4 < length) {
                        java.lang.String str = dexCodeInstructionSets[i4];
                        java.util.Iterator<java.lang.String> it = allCodePathsExcludingResourceOnly.iterator();
                        int i5 = i3;
                        int i6 = i;
                        int i7 = i5;
                        while (it.hasNext()) {
                            int i8 = i7 + 1;
                            android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap = packageStates;
                            try {
                                installer.moveAb(packageName, it.next(), str, com.android.server.pm.PackageDexOptimizer.getOatDir(new java.io.File(pkg.getPath())).getAbsolutePath());
                                i6++;
                            } catch (com.android.server.pm.Installer.InstallerException e) {
                            }
                            i7 = i8;
                            packageStates = arrayMap;
                        }
                        i4++;
                        packageStates = packageStates;
                        int i9 = i6;
                        i3 = i7;
                        i = i9;
                    }
                }
            }
            i2++;
            otaDexoptService = this;
            packageStates = packageStates;
        }
        android.util.Slog.i(TAG, "Moved " + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i3);
    }

    private void prepareMetricsLogging(int i, int i2, long j, long j2) {
        this.availableSpaceBefore = j;
        this.availableSpaceAfterBulkDelete = j2;
        this.availableSpaceAfterDexopt = 0L;
        this.importantPackageCount = i;
        this.otherPackageCount = i2;
        this.dexoptCommandCountTotal = this.mDexoptCommands.size();
        this.dexoptCommandCountExecuted = 0;
        this.otaDexoptTimeStart = java.lang.System.nanoTime();
    }

    private static int inMegabytes(long j) {
        long j2 = j / 1048576;
        if (j2 > 2147483647L) {
            android.util.Log.w(TAG, "Recording " + j2 + "MB of free space, overflowing range");
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    private void performMetricsLogging() {
        long nanoTime = java.lang.System.nanoTime();
        this.metricsLogger.histogram("ota_dexopt_available_space_before_mb", inMegabytes(this.availableSpaceBefore));
        this.metricsLogger.histogram("ota_dexopt_available_space_after_bulk_delete_mb", inMegabytes(this.availableSpaceAfterBulkDelete));
        this.metricsLogger.histogram("ota_dexopt_available_space_after_dexopt_mb", inMegabytes(this.availableSpaceAfterDexopt));
        this.metricsLogger.histogram("ota_dexopt_num_important_packages", this.importantPackageCount);
        this.metricsLogger.histogram("ota_dexopt_num_other_packages", this.otherPackageCount);
        this.metricsLogger.histogram("ota_dexopt_num_commands", this.dexoptCommandCountTotal);
        this.metricsLogger.histogram("ota_dexopt_num_commands_executed", this.dexoptCommandCountExecuted);
        this.metricsLogger.histogram("ota_dexopt_time_s", (int) java.util.concurrent.TimeUnit.NANOSECONDS.toSeconds(nanoTime - this.otaDexoptTimeStart));
    }

    private static class OTADexoptPackageDexOptimizer extends com.android.server.pm.PackageDexOptimizer.ForcedUpdatePackageDexOptimizer {
        public OTADexoptPackageDexOptimizer(com.android.server.pm.Installer installer, java.lang.Object obj, android.content.Context context) {
            super(installer, obj, context, "*otadexopt*");
        }
    }
}
