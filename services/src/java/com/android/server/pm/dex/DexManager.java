package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class DexManager {
    private static final int DEX_SEARCH_FOUND_PRIMARY = 1;
    private static final int DEX_SEARCH_FOUND_SECONDARY = 3;
    private static final int DEX_SEARCH_FOUND_SPLIT = 2;
    private static final int DEX_SEARCH_NOT_FOUND = 0;
    private static final java.lang.String ISOLATED_PROCESS_PACKAGE_SUFFIX = "..isolated";
    private static final java.lang.String SYSTEM_SERVER_COMPILER_FILTER = "verify";
    private android.os.BatteryManager mBatteryManager;
    private final android.content.Context mContext;
    private final int mCriticalBatteryLevel;
    private final com.android.server.pm.dex.DynamicCodeLogger mDynamicCodeLogger;
    private final java.lang.Object mInstallLock;

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    private final com.android.server.pm.Installer mInstaller;

    @com.android.internal.annotations.GuardedBy({"mPackageCodeLocationsCache"})
    private final java.util.Map<java.lang.String, com.android.server.pm.dex.DexManager.PackageCodeLocations> mPackageCodeLocationsCache;
    private final com.android.server.pm.PackageDexOptimizer mPackageDexOptimizer;
    private final com.android.server.pm.dex.PackageDexUsage mPackageDexUsage;
    private android.content.pm.IPackageManager mPackageManager;
    private android.os.PowerManager mPowerManager;
    private static final java.lang.String TAG = "DexManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public DexManager(android.content.Context context, com.android.server.pm.PackageDexOptimizer packageDexOptimizer, com.android.server.pm.Installer installer, java.lang.Object obj, com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger) {
        this(context, packageDexOptimizer, installer, obj, dynamicCodeLogger, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    public DexManager(android.content.Context context, com.android.server.pm.PackageDexOptimizer packageDexOptimizer, com.android.server.pm.Installer installer, java.lang.Object obj, com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger, @android.annotation.Nullable android.content.pm.IPackageManager iPackageManager) {
        this.mBatteryManager = null;
        this.mPowerManager = null;
        this.mContext = context;
        this.mPackageCodeLocationsCache = new java.util.HashMap();
        this.mPackageDexUsage = new com.android.server.pm.dex.PackageDexUsage();
        this.mPackageDexOptimizer = packageDexOptimizer;
        this.mInstaller = installer;
        this.mInstallLock = obj;
        this.mDynamicCodeLogger = dynamicCodeLogger;
        this.mPackageManager = iPackageManager;
        if (this.mContext != null) {
            this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
            if (this.mPowerManager == null) {
                android.util.Slog.wtf(TAG, "Power Manager is unavailable at time of Dex Manager start");
            }
            this.mCriticalBatteryLevel = this.mContext.getResources().getInteger(android.R.integer.config_chooser_max_targets_per_row);
            return;
        }
        this.mCriticalBatteryLevel = 0;
    }

    @android.annotation.NonNull
    private android.content.pm.IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        }
        return this.mPackageManager;
    }

    public void notifyDexLoad(android.content.pm.ApplicationInfo applicationInfo, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str, int i, boolean z) {
        try {
            notifyDexLoadInternal(applicationInfo, map, str, i, z);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Exception while notifying dex load for package " + applicationInfo.packageName, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d8, code lost:
    
        r16.mDynamicCodeLogger.recordDex(r20, r4, r3.mOwningPackageName, r17.packageName);
     */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void notifyDexLoadInternal(android.content.pm.ApplicationInfo applicationInfo, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str, int i, boolean z) {
        if (map == null) {
            return;
        }
        if (map.isEmpty()) {
            android.util.Slog.wtf(TAG, "Bad call to notifyDexLoad: class loaders list is empty");
            return;
        }
        if (!com.android.server.pm.PackageManagerServiceUtils.checkISA(str)) {
            android.util.Slog.w(TAG, "Loading dex files " + map.keySet() + " in unsupported ISA: " + str + "?");
            return;
        }
        java.lang.String str2 = applicationInfo.packageName;
        java.lang.String str3 = !z ? str2 : str2 + ISOLATED_PROCESS_PACKAGE_SUFFIX;
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.pm.dex.DexManager.DexSearchResult dexPackage = getDexPackage(applicationInfo, key, i);
            if (DEBUG) {
                android.util.Slog.i(TAG, str3 + " loads from " + dexPackage + " : " + i + " : " + key);
            }
            if (dexPackage.mOutcome != 0) {
                boolean z2 = true;
                boolean z3 = !str3.equals(dexPackage.mOwningPackageName);
                if (dexPackage.mOutcome != 1 && dexPackage.mOutcome != 2) {
                    z2 = false;
                }
                boolean z4 = z2;
                if (!z4 || z3 || isPlatformPackage(dexPackage.mOwningPackageName)) {
                    java.lang.String value = entry.getValue();
                    boolean isPlatformPackage = isPlatformPackage(dexPackage.mOwningPackageName);
                    if (value != null && dalvik.system.VMRuntime.isValidClassLoaderContext(value) && this.mPackageDexUsage.record(dexPackage.mOwningPackageName, key, i, str, z4, str3, value, isPlatformPackage)) {
                        this.mPackageDexUsage.maybeWriteAsync();
                    }
                }
            } else if (DEBUG) {
                android.util.Slog.i(TAG, "Could not find owning package for dex file: " + key);
            }
        }
    }

    private boolean isSystemServerDexPathSupportedForOdex(java.lang.String str) {
        java.util.ArrayList orderedPartitions = android.content.pm.PackagePartitions.getOrderedPartitions(java.util.function.Function.identity());
        if (str.startsWith("/apex/")) {
            return true;
        }
        for (int i = 0; i < orderedPartitions.size(); i++) {
            if (((android.content.pm.PackagePartitions.SystemPartition) orderedPartitions.get(i)).containsPath(str)) {
                return true;
            }
        }
        return false;
    }

    public void load(java.util.Map<java.lang.Integer, java.util.List<android.content.pm.PackageInfo>> map) {
        try {
            loadInternal(map);
        } catch (java.lang.RuntimeException e) {
            this.mPackageDexUsage.clear();
            android.util.Slog.w(TAG, "Exception while loading. Starting with a fresh state.", e);
        }
    }

    public void notifyPackageInstalled(android.content.pm.PackageInfo packageInfo, int i) {
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("notifyPackageInstalled called with USER_ALL");
        }
        cachePackageInfo(packageInfo, i);
    }

    public void notifyPackageUpdated(java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        cachePackageCodeLocation(str, str2, strArr, null, -1);
        if (this.mPackageDexUsage.clearUsedByOtherApps(str)) {
            this.mPackageDexUsage.maybeWriteAsync();
        }
    }

    public void notifyPackageDataDestroyed(java.lang.String str, int i) {
        if (i == -1) {
            if (this.mPackageDexUsage.removePackage(str)) {
                this.mPackageDexUsage.maybeWriteAsync();
            }
        } else if (this.mPackageDexUsage.removeUserPackage(str, i)) {
            this.mPackageDexUsage.maybeWriteAsync();
        }
    }

    private void cachePackageInfo(android.content.pm.PackageInfo packageInfo, int i) {
        android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        cachePackageCodeLocation(packageInfo.packageName, applicationInfo.sourceDir, applicationInfo.splitSourceDirs, new java.lang.String[]{applicationInfo.dataDir, applicationInfo.deviceProtectedDataDir, applicationInfo.credentialProtectedDataDir}, i);
    }

    private void cachePackageCodeLocation(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, java.lang.String[] strArr2, int i) {
        synchronized (this.mPackageCodeLocationsCache) {
            try {
                com.android.server.pm.dex.DexManager.PackageCodeLocations packageCodeLocations = (com.android.server.pm.dex.DexManager.PackageCodeLocations) putIfAbsent(this.mPackageCodeLocationsCache, str, new com.android.server.pm.dex.DexManager.PackageCodeLocations(str, str2, strArr));
                packageCodeLocations.updateCodeLocation(str2, strArr);
                if (strArr2 != null) {
                    for (java.lang.String str3 : strArr2) {
                        if (str3 != null) {
                            packageCodeLocations.mergeAppDataDirs(str3, i);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void loadInternal(java.util.Map<java.lang.Integer, java.util.List<android.content.pm.PackageInfo>> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.HashMap hashMap2 = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.Integer, java.util.List<android.content.pm.PackageInfo>> entry : map.entrySet()) {
            java.util.List<android.content.pm.PackageInfo> value = entry.getValue();
            int intValue = entry.getKey().intValue();
            for (android.content.pm.PackageInfo packageInfo : value) {
                cachePackageInfo(packageInfo, intValue);
                ((java.util.Set) putIfAbsent(hashMap, packageInfo.packageName, new java.util.HashSet())).add(java.lang.Integer.valueOf(intValue));
                java.util.Set set = (java.util.Set) putIfAbsent(hashMap2, packageInfo.packageName, new java.util.HashSet());
                set.add(packageInfo.applicationInfo.sourceDir);
                if (packageInfo.applicationInfo.splitSourceDirs != null) {
                    java.util.Collections.addAll(set, packageInfo.applicationInfo.splitSourceDirs);
                }
            }
        }
        try {
            this.mPackageDexUsage.read();
            this.mPackageDexUsage.syncData(hashMap, hashMap2, new java.util.ArrayList());
        } catch (java.lang.RuntimeException e) {
            this.mPackageDexUsage.clear();
            android.util.Slog.w(TAG, "Exception while loading package dex usage. Starting with a fresh state.", e);
        }
    }

    public com.android.server.pm.dex.PackageDexUsage.PackageUseInfo getPackageUseInfoOrDefault(java.lang.String str) {
        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfo = this.mPackageDexUsage.getPackageUseInfo(str);
        return packageUseInfo == null ? new com.android.server.pm.dex.PackageDexUsage.PackageUseInfo(str) : packageUseInfo;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasInfoOnPackage(java.lang.String str) {
        return this.mPackageDexUsage.getPackageUseInfo(str) != null;
    }

    public boolean dexoptSecondaryDex(com.android.server.pm.dex.DexoptOptions dexoptOptions) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        if (isPlatformPackage(dexoptOptions.getPackageName())) {
            android.util.Slog.wtf(TAG, "System server jars should be optimized with dexoptSystemServer");
            return false;
        }
        com.android.server.pm.PackageDexOptimizer packageDexOptimizer = getPackageDexOptimizer(dexoptOptions);
        java.lang.String packageName = dexoptOptions.getPackageName();
        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfoOrDefault = getPackageUseInfoOrDefault(packageName);
        if (packageUseInfoOrDefault.getDexUseInfoMap().isEmpty()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "No secondary dex use for package:" + packageName);
            }
            return true;
        }
        boolean z = true;
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> entry : packageUseInfoOrDefault.getDexUseInfoMap().entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.pm.dex.PackageDexUsage.DexUseInfo value = entry.getValue();
            try {
                android.content.pm.PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, 0L, value.getOwnerUserId());
                if (packageInfo == null) {
                    android.util.Slog.d(TAG, "Could not find package when compiling secondary dex " + packageName + " for user " + value.getOwnerUserId());
                    this.mPackageDexUsage.removeUserPackage(packageName, value.getOwnerUserId());
                } else {
                    z = z && packageDexOptimizer.dexOptSecondaryDexPath(packageInfo.applicationInfo, key, value, dexoptOptions) != -1;
                }
            } catch (android.os.RemoteException e) {
                throw new java.lang.AssertionError(e);
            }
        }
        return z;
    }

    private com.android.server.pm.PackageDexOptimizer getPackageDexOptimizer(com.android.server.pm.dex.DexoptOptions dexoptOptions) {
        if (dexoptOptions.isForce()) {
            return new com.android.server.pm.PackageDexOptimizer.ForcedUpdatePackageDexOptimizer(this.mPackageDexOptimizer);
        }
        return this.mPackageDexOptimizer;
    }

    public void reconcileSecondaryDexFiles(java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        android.content.pm.PackageInfo packageInfo;
        int i;
        boolean z;
        com.android.server.pm.dex.PackageDexUsage.PackageUseInfo packageUseInfoOrDefault = getPackageUseInfoOrDefault(str);
        if (packageUseInfoOrDefault.getDexUseInfoMap().isEmpty()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "No secondary dex use for package:" + str);
                return;
            }
            return;
        }
        boolean z2 = false;
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDexUsage.DexUseInfo> entry : packageUseInfoOrDefault.getDexUseInfoMap().entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.pm.dex.PackageDexUsage.DexUseInfo value = entry.getValue();
            try {
                packageInfo = getPackageManager().getPackageInfo(str, 0L, value.getOwnerUserId());
            } catch (android.os.RemoteException e) {
                packageInfo = null;
            }
            boolean z3 = true;
            if (packageInfo == null) {
                android.util.Slog.d(TAG, "Could not find package when compiling secondary dex " + str + " for user " + value.getOwnerUserId());
                z2 = this.mPackageDexUsage.removeUserPackage(str, value.getOwnerUserId()) || z2;
            } else if (isPlatformPackage(str)) {
                if (!java.nio.file.Files.exists(java.nio.file.Paths.get(key, new java.lang.String[0]), new java.nio.file.LinkOption[0])) {
                    if (DEBUG) {
                        android.util.Slog.w(TAG, "A dex file previously loaded by System Server does not exist  anymore: " + key);
                    }
                    z2 = this.mPackageDexUsage.removeUserPackage(str, value.getOwnerUserId()) || z2;
                }
            } else {
                android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if (applicationInfo.deviceProtectedDataDir != null && android.os.FileUtils.contains(applicationInfo.deviceProtectedDataDir, key)) {
                    i = 1;
                } else if (applicationInfo.credentialProtectedDataDir != null && android.os.FileUtils.contains(applicationInfo.credentialProtectedDataDir, key)) {
                    i = 2;
                } else {
                    android.util.Slog.e(TAG, "Could not infer CE/DE storage for path " + key);
                    z2 = this.mPackageDexUsage.removeDexFile(str, key, value.getOwnerUserId()) || z2;
                }
                synchronized (this.mInstallLock) {
                    try {
                        z = this.mInstaller.reconcileSecondaryDexFile(key, str, applicationInfo.uid, (java.lang.String[]) value.getLoaderIsas().toArray(new java.lang.String[0]), applicationInfo.volumeUuid, i);
                    } catch (com.android.server.pm.Installer.InstallerException e2) {
                        android.util.Slog.e(TAG, "Got InstallerException when reconciling dex " + key + " : " + e2.getMessage());
                        z = true;
                    }
                }
                if (!z) {
                    if (!this.mPackageDexUsage.removeDexFile(str, key, value.getOwnerUserId()) && !z2) {
                        z3 = false;
                    }
                    z2 = z3;
                }
            }
        }
        if (z2) {
            this.mPackageDexUsage.maybeWriteAsync();
        }
    }

    public java.util.Set<java.lang.String> getAllPackagesWithSecondaryDexFiles() {
        return this.mPackageDexUsage.getAllPackagesWithSecondaryDexFiles();
    }

    private com.android.server.pm.dex.DexManager.DexSearchResult getDexPackage(android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, int i) {
        com.android.server.pm.dex.DexManager.PackageCodeLocations packageCodeLocations = new com.android.server.pm.dex.DexManager.PackageCodeLocations(applicationInfo, i);
        int searchDex = packageCodeLocations.searchDex(str, i);
        if (searchDex != 0) {
            return new com.android.server.pm.dex.DexManager.DexSearchResult(packageCodeLocations.mPackageName, searchDex);
        }
        synchronized (this.mPackageCodeLocationsCache) {
            try {
                for (com.android.server.pm.dex.DexManager.PackageCodeLocations packageCodeLocations2 : this.mPackageCodeLocationsCache.values()) {
                    int searchDex2 = packageCodeLocations2.searchDex(str, i);
                    if (searchDex2 != 0) {
                        return new com.android.server.pm.dex.DexManager.DexSearchResult(packageCodeLocations2.mPackageName, searchDex2);
                    }
                }
                if (isPlatformPackage(applicationInfo.packageName)) {
                    if (isSystemServerDexPathSupportedForOdex(str)) {
                        return new com.android.server.pm.dex.DexManager.DexSearchResult(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 3);
                    }
                    android.util.Slog.wtf(TAG, "System server loads dex files outside paths supported for odex: " + str);
                }
                if (DEBUG) {
                    try {
                        java.lang.String realpath = com.android.server.pm.PackageManagerServiceUtils.realpath(new java.io.File(str));
                        if (!str.equals(realpath)) {
                            android.util.Slog.d(TAG, "Dex loaded with symlink. dexPath=" + str + " dexPathReal=" + realpath);
                        }
                    } catch (java.io.IOException e) {
                    }
                }
                return new com.android.server.pm.dex.DexManager.DexSearchResult(null, 0);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean isPlatformPackage(java.lang.String str) {
        return com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> V putIfAbsent(java.util.Map<K, V> map, K k, V v) {
        V putIfAbsent = map.putIfAbsent(k, v);
        return putIfAbsent == null ? v : putIfAbsent;
    }

    public void writePackageDexUsageNow() {
        this.mPackageDexUsage.writeNow();
    }

    public static boolean auditUncompressedDexInApk(java.lang.String str) {
        android.util.jar.StrictJarFile strictJarFile = null;
        try {
            try {
                android.util.jar.StrictJarFile strictJarFile2 = new android.util.jar.StrictJarFile(str, false, false);
                try {
                    java.util.Iterator it = strictJarFile2.iterator();
                    boolean z = true;
                    while (it.hasNext()) {
                        java.util.zip.ZipEntry zipEntry = (java.util.zip.ZipEntry) it.next();
                        if (zipEntry.getName().endsWith(".dex")) {
                            if (zipEntry.getMethod() != 0) {
                                android.util.Slog.w(TAG, "APK " + str + " has compressed dex code " + zipEntry.getName());
                                z = false;
                            } else if ((zipEntry.getDataOffset() & 3) != 0) {
                                android.util.Slog.w(TAG, "APK " + str + " has unaligned dex code " + zipEntry.getName());
                                z = false;
                            }
                        }
                    }
                    try {
                        strictJarFile2.close();
                    } catch (java.io.IOException e) {
                    }
                    return z;
                } catch (java.io.IOException e2) {
                    strictJarFile = strictJarFile2;
                    android.util.Slog.wtf(TAG, "Error when parsing APK " + str);
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    th = th;
                    strictJarFile = strictJarFile2;
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e5) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public int getCompilationReasonForInstallScenario(int i) {
        boolean areBatteryThermalOrMemoryCritical = areBatteryThermalOrMemoryCritical();
        switch (i) {
            case 0:
                return 3;
            case 1:
                return 4;
            case 2:
                if (areBatteryThermalOrMemoryCritical) {
                    return 7;
                }
                return 5;
            case 3:
                if (areBatteryThermalOrMemoryCritical) {
                    return 8;
                }
                return 6;
            default:
                throw new java.lang.IllegalArgumentException("Invalid installation scenario");
        }
    }

    private android.os.BatteryManager getBatteryManager() {
        if (this.mBatteryManager == null && this.mContext != null) {
            this.mBatteryManager = (android.os.BatteryManager) this.mContext.getSystemService(android.os.BatteryManager.class);
        }
        return this.mBatteryManager;
    }

    private boolean areBatteryThermalOrMemoryCritical() {
        android.os.BatteryManager batteryManager = getBatteryManager();
        return (batteryManager != null && batteryManager.getIntProperty(6) == 3 && batteryManager.getIntProperty(4) <= this.mCriticalBatteryLevel) || (this.mPowerManager != null && this.mPowerManager.getCurrentThermalStatus() >= 3);
    }

    public long deleteOptimizedFiles(com.android.server.pm.dex.ArtPackageInfo artPackageInfo) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
        java.lang.String packageName = artPackageInfo.getPackageName();
        long j = 0;
        boolean z = false;
        for (java.lang.String str : artPackageInfo.getCodePaths()) {
            java.util.Iterator<java.lang.String> it = artPackageInfo.getInstructionSets().iterator();
            while (it.hasNext()) {
                try {
                    j += this.mInstaller.deleteOdex(packageName, str, it.next(), artPackageInfo.getOatDir());
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Log.e(TAG, "Failed deleting oat files for " + str, e);
                    z = true;
                }
            }
        }
        if (z) {
            return -1L;
        }
        return j;
    }

    public static class RegisterDexModuleResult {
        public final java.lang.String message;
        public final boolean success;

        public RegisterDexModuleResult() {
            this(false, null);
        }

        public RegisterDexModuleResult(boolean z, java.lang.String str) {
            this.success = z;
            this.message = str;
        }
    }

    private static class PackageCodeLocations {
        private final java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> mAppDataDirs;
        private java.lang.String mBaseCodePath;
        private final java.lang.String mPackageName;
        private final java.util.Set<java.lang.String> mSplitCodePaths;

        public PackageCodeLocations(android.content.pm.ApplicationInfo applicationInfo, int i) {
            this(applicationInfo.packageName, applicationInfo.sourceDir, applicationInfo.splitSourceDirs);
            mergeAppDataDirs(applicationInfo.dataDir, i);
        }

        public PackageCodeLocations(java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
            this.mPackageName = str;
            this.mSplitCodePaths = new java.util.HashSet();
            this.mAppDataDirs = new java.util.HashMap();
            updateCodeLocation(str2, strArr);
        }

        public void updateCodeLocation(java.lang.String str, java.lang.String[] strArr) {
            this.mBaseCodePath = str;
            this.mSplitCodePaths.clear();
            if (strArr != null) {
                for (java.lang.String str2 : strArr) {
                    this.mSplitCodePaths.add(str2);
                }
            }
        }

        public void mergeAppDataDirs(java.lang.String str, int i) {
            ((java.util.Set) com.android.server.pm.dex.DexManager.putIfAbsent(this.mAppDataDirs, java.lang.Integer.valueOf(i), new java.util.HashSet())).add(str);
        }

        public int searchDex(java.lang.String str, int i) {
            java.util.Set<java.lang.String> set = this.mAppDataDirs.get(java.lang.Integer.valueOf(i));
            if (set == null) {
                return 0;
            }
            if (this.mBaseCodePath.equals(str)) {
                return 1;
            }
            if (this.mSplitCodePaths.contains(str)) {
                return 2;
            }
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                if (str.startsWith(it.next())) {
                    return 3;
                }
            }
            return 0;
        }
    }

    private class DexSearchResult {
        private final int mOutcome;
        private final java.lang.String mOwningPackageName;

        public DexSearchResult(java.lang.String str, int i) {
            this.mOwningPackageName = str;
            this.mOutcome = i;
        }

        public java.lang.String toString() {
            return this.mOwningPackageName + "-" + this.mOutcome;
        }
    }
}
