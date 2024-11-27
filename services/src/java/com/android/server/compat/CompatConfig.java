package com.android.server.compat;

/* loaded from: classes.dex */
final class CompatConfig {
    private static final java.lang.String APP_COMPAT_DATA_DIR = "/data/misc/appcompat";
    private static final java.lang.String OVERRIDES_FILE = "compat_framework_overrides.xml";
    private static final java.lang.String STATIC_OVERRIDES_PRODUCT_DIR = "/product/etc/appcompat";
    private static final java.lang.String TAG = "CompatConfig";
    private final com.android.internal.compat.AndroidBuildClassifier mAndroidBuildClassifier;

    @com.android.internal.annotations.GuardedBy({"mOverridesFileLock"})
    private java.io.File mBackupOverridesFile;
    private android.content.Context mContext;
    private final com.android.server.compat.OverrideValidatorImpl mOverrideValidator;

    @com.android.internal.annotations.GuardedBy({"mOverridesFileLock"})
    private java.io.File mOverridesFile;
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Long, com.android.server.compat.CompatChange> mChanges = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.lang.Object mOverridesFileLock = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    CompatConfig(com.android.internal.compat.AndroidBuildClassifier androidBuildClassifier, android.content.Context context) {
        this.mOverrideValidator = new com.android.server.compat.OverrideValidatorImpl(androidBuildClassifier, context, this);
        this.mAndroidBuildClassifier = androidBuildClassifier;
        this.mContext = context;
    }

    static com.android.server.compat.CompatConfig create(com.android.internal.compat.AndroidBuildClassifier androidBuildClassifier, android.content.Context context) {
        com.android.server.compat.CompatConfig compatConfig = new com.android.server.compat.CompatConfig(androidBuildClassifier, context);
        compatConfig.initConfigFromLib(android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{"etc", "compatconfig"}));
        compatConfig.initConfigFromLib(android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{"system_ext", "etc", "compatconfig"}));
        java.util.Iterator<com.android.server.pm.ApexManager.ActiveApexInfo> it = com.android.server.pm.ApexManager.getInstance().getActiveApexInfos().iterator();
        while (it.hasNext()) {
            compatConfig.initConfigFromLib(android.os.Environment.buildPath(it.next().apexDirectory, new java.lang.String[]{"etc", "compatconfig"}));
        }
        compatConfig.initOverrides();
        compatConfig.invalidateCache();
        return compatConfig;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addChange(com.android.server.compat.CompatChange compatChange) {
        this.mChanges.put(java.lang.Long.valueOf(compatChange.getId()), compatChange);
    }

    long[] getDisabledChanges(android.content.pm.ApplicationInfo applicationInfo) {
        android.util.LongArray longArray = new android.util.LongArray();
        for (com.android.server.compat.CompatChange compatChange : this.mChanges.values()) {
            if (!compatChange.isEnabled(applicationInfo, this.mAndroidBuildClassifier)) {
                longArray.add(compatChange.getId());
            }
        }
        long[] array = longArray.toArray();
        java.util.Arrays.sort(array);
        return array;
    }

    long lookupChangeId(java.lang.String str) {
        for (com.android.server.compat.CompatChange compatChange : this.mChanges.values()) {
            if (android.text.TextUtils.equals(compatChange.getName(), str)) {
                return compatChange.getId();
            }
        }
        return -1L;
    }

    boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        if (compatChange == null) {
            return true;
        }
        return compatChange.isEnabled(applicationInfo, this.mAndroidBuildClassifier);
    }

    boolean willChangeBeEnabled(long j, java.lang.String str) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        if (compatChange == null) {
            return true;
        }
        return compatChange.willBeEnabled(str);
    }

    synchronized boolean addOverride(long j, java.lang.String str, boolean z) {
        boolean addOverrideUnsafe;
        addOverrideUnsafe = addOverrideUnsafe(j, str, new android.app.compat.PackageOverride.Builder().setEnabled(z).build());
        saveOverrides();
        invalidateCache();
        return addOverrideUnsafe;
    }

    synchronized void addAllPackageOverrides(com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig, boolean z) {
        try {
            for (java.lang.String str : compatibilityOverridesByPackageConfig.packageNameToOverrides.keySet()) {
                addPackageOverridesWithoutSaving((com.android.internal.compat.CompatibilityOverrideConfig) compatibilityOverridesByPackageConfig.packageNameToOverrides.get(str), str, z);
            }
            saveOverrides();
            invalidateCache();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized void addPackageOverrides(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str, boolean z) {
        addPackageOverridesWithoutSaving(compatibilityOverrideConfig, str, z);
        saveOverrides();
        invalidateCache();
    }

    private void addPackageOverridesWithoutSaving(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str, boolean z) {
        for (java.lang.Long l : compatibilityOverrideConfig.overrides.keySet()) {
            if (z && !isKnownChangeId(l.longValue())) {
                android.util.Slog.w(TAG, "Trying to add overrides for unknown Change ID " + l + ". Skipping Change ID.");
            } else {
                addOverrideUnsafe(l.longValue(), str, (android.app.compat.PackageOverride) compatibilityOverrideConfig.overrides.get(l));
            }
        }
    }

    private boolean addOverrideUnsafe(final long j, java.lang.String str, android.app.compat.PackageOverride packageOverride) {
        java.lang.String str2;
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(true);
        com.android.internal.compat.OverrideAllowedState overrideAllowedState = this.mOverrideValidator.getOverrideAllowedState(j, str);
        overrideAllowedState.enforce(j, str);
        java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
        com.android.server.compat.CompatChange computeIfAbsent = this.mChanges.computeIfAbsent(java.lang.Long.valueOf(j), new java.util.function.Function() { // from class: com.android.server.compat.CompatConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.compat.CompatChange lambda$addOverrideUnsafe$0;
                lambda$addOverrideUnsafe$0 = com.android.server.compat.CompatConfig.lambda$addOverrideUnsafe$0(atomicBoolean, j, (java.lang.Long) obj);
                return lambda$addOverrideUnsafe$0;
            }
        });
        computeIfAbsent.addPackageOverride(str, packageOverride, overrideAllowedState, versionCodeOrNull);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(packageOverride.isEnabled() ? "Enabled" : "Disabled");
        sb.append(" change ");
        sb.append(j);
        if (computeIfAbsent.getName() != null) {
            str2 = " [" + computeIfAbsent.getName() + "]";
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" for ");
        sb.append(str);
        android.util.Slog.d(TAG, sb.toString());
        invalidateCache();
        return atomicBoolean.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.compat.CompatChange lambda$addOverrideUnsafe$0(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, long j, java.lang.Long l) {
        atomicBoolean.set(false);
        return new com.android.server.compat.CompatChange(j);
    }

    boolean isKnownChangeId(long j) {
        return this.mChanges.containsKey(java.lang.Long.valueOf(j));
    }

    int maxTargetSdkForChangeIdOptIn(long j) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        if (compatChange == null || compatChange.getEnableSinceTargetSdk() == -1) {
            return -1;
        }
        return compatChange.getEnableSinceTargetSdk() - 1;
    }

    boolean isLoggingOnly(long j) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        return compatChange != null && compatChange.getLoggingOnly();
    }

    boolean isDisabled(long j) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        return compatChange != null && compatChange.getDisabled();
    }

    boolean isOverridable(long j) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        return compatChange != null && compatChange.getOverridable();
    }

    synchronized boolean removeOverride(long j, java.lang.String str) {
        boolean removeOverrideUnsafe;
        removeOverrideUnsafe = removeOverrideUnsafe(j, str);
        if (removeOverrideUnsafe) {
            saveOverrides();
            invalidateCache();
        }
        return removeOverrideUnsafe;
    }

    private boolean removeOverrideUnsafe(long j, java.lang.String str) {
        java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        if (compatChange != null) {
            return removeOverrideUnsafe(compatChange, str, versionCodeOrNull);
        }
        return false;
    }

    private boolean removeOverrideUnsafe(com.android.server.compat.CompatChange compatChange, java.lang.String str, @android.annotation.Nullable java.lang.Long l) {
        java.lang.String str2;
        long id = compatChange.getId();
        boolean removePackageOverride = compatChange.removePackageOverride(str, this.mOverrideValidator.getOverrideAllowedState(id, str), l);
        if (removePackageOverride) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Reset change ");
            sb.append(id);
            if (compatChange.getName() != null) {
                str2 = " [" + compatChange.getName() + "]";
            } else {
                str2 = "";
            }
            sb.append(str2);
            sb.append(" for ");
            sb.append(str);
            sb.append(" to default value.");
            android.util.Slog.d(TAG, sb.toString());
        }
        return removePackageOverride;
    }

    synchronized void removeAllPackageOverrides(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) {
        try {
            boolean z = false;
            for (java.lang.String str : compatibilityOverridesToRemoveByPackageConfig.packageNameToOverridesToRemove.keySet()) {
                z |= removePackageOverridesWithoutSaving((com.android.internal.compat.CompatibilityOverridesToRemoveConfig) compatibilityOverridesToRemoveByPackageConfig.packageNameToOverridesToRemove.get(str), str);
            }
            if (z) {
                saveOverrides();
                invalidateCache();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized void removePackageOverrides(java.lang.String str) {
        try {
            java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
            java.util.Iterator<com.android.server.compat.CompatChange> it = this.mChanges.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                z |= removeOverrideUnsafe(it.next(), str, versionCodeOrNull);
            }
            if (z) {
                saveOverrides();
                invalidateCache();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized void removePackageOverrides(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) {
        if (removePackageOverridesWithoutSaving(compatibilityOverridesToRemoveConfig, str)) {
            saveOverrides();
            invalidateCache();
        }
    }

    private boolean removePackageOverridesWithoutSaving(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) {
        boolean z = false;
        for (java.lang.Long l : compatibilityOverridesToRemoveConfig.changeIds) {
            if (isKnownChangeId(l.longValue())) {
                z |= removeOverrideUnsafe(l.longValue(), str);
            } else {
                android.util.Slog.w(TAG, "Trying to remove overrides for unknown Change ID " + l + ". Skipping Change ID.");
            }
        }
        return z;
    }

    private long[] getAllowedChangesSinceTargetSdkForPackage(java.lang.String str, int i) {
        android.util.LongArray longArray = new android.util.LongArray();
        for (com.android.server.compat.CompatChange compatChange : this.mChanges.values()) {
            if (compatChange.getEnableSinceTargetSdk() == i && this.mOverrideValidator.getOverrideAllowedState(compatChange.getId(), str).state == 0) {
                longArray.add(compatChange.getId());
            }
        }
        return longArray.toArray();
    }

    int enableTargetSdkChangesForPackage(java.lang.String str, int i) {
        long[] allowedChangesSinceTargetSdkForPackage = getAllowedChangesSinceTargetSdkForPackage(str, i);
        boolean z = false;
        for (long j : allowedChangesSinceTargetSdkForPackage) {
            z |= addOverrideUnsafe(j, str, new android.app.compat.PackageOverride.Builder().setEnabled(true).build());
        }
        if (z) {
            saveOverrides();
            invalidateCache();
        }
        return allowedChangesSinceTargetSdkForPackage.length;
    }

    int disableTargetSdkChangesForPackage(java.lang.String str, int i) {
        long[] allowedChangesSinceTargetSdkForPackage = getAllowedChangesSinceTargetSdkForPackage(str, i);
        boolean z = false;
        for (long j : allowedChangesSinceTargetSdkForPackage) {
            z |= addOverrideUnsafe(j, str, new android.app.compat.PackageOverride.Builder().setEnabled(false).build());
        }
        if (z) {
            saveOverrides();
            invalidateCache();
        }
        return allowedChangesSinceTargetSdkForPackage.length;
    }

    boolean registerListener(final long j, com.android.server.compat.CompatChange.ChangeListener changeListener) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(true);
        this.mChanges.computeIfAbsent(java.lang.Long.valueOf(j), new java.util.function.Function() { // from class: com.android.server.compat.CompatConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.compat.CompatChange lambda$registerListener$1;
                lambda$registerListener$1 = com.android.server.compat.CompatConfig.this.lambda$registerListener$1(atomicBoolean, j, (java.lang.Long) obj);
                return lambda$registerListener$1;
            }
        }).registerListener(changeListener);
        return atomicBoolean.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.compat.CompatChange lambda$registerListener$1(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, long j, java.lang.Long l) {
        atomicBoolean.set(false);
        invalidateCache();
        return new com.android.server.compat.CompatChange(j);
    }

    boolean defaultChangeIdValue(long j) {
        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(j));
        if (compatChange == null) {
            return true;
        }
        return compatChange.defaultValue();
    }

    @com.android.internal.annotations.VisibleForTesting
    void forceNonDebuggableFinalForTest(boolean z) {
        this.mOverrideValidator.forceNonDebuggableFinalForTest(z);
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearChanges() {
        this.mChanges.clear();
    }

    void dumpConfig(java.io.PrintWriter printWriter) {
        if (this.mChanges.size() == 0) {
            printWriter.println("No compat overrides.");
            return;
        }
        java.util.Iterator<com.android.server.compat.CompatChange> it = this.mChanges.values().iterator();
        while (it.hasNext()) {
            printWriter.println(it.next().toString());
        }
    }

    com.android.internal.compat.CompatibilityChangeConfig getAppConfig(android.content.pm.ApplicationInfo applicationInfo) {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.HashSet hashSet2 = new java.util.HashSet();
        for (com.android.server.compat.CompatChange compatChange : this.mChanges.values()) {
            if (compatChange.isEnabled(applicationInfo, this.mAndroidBuildClassifier)) {
                hashSet.add(java.lang.Long.valueOf(compatChange.getId()));
            } else {
                hashSet2.add(java.lang.Long.valueOf(compatChange.getId()));
            }
        }
        return new com.android.internal.compat.CompatibilityChangeConfig(new android.compat.Compatibility.ChangeConfig(hashSet, hashSet2));
    }

    com.android.internal.compat.CompatibilityChangeInfo[] dumpChanges() {
        com.android.internal.compat.CompatibilityChangeInfo[] compatibilityChangeInfoArr = new com.android.internal.compat.CompatibilityChangeInfo[this.mChanges.size()];
        java.util.Iterator<com.android.server.compat.CompatChange> it = this.mChanges.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            compatibilityChangeInfoArr[i] = new com.android.internal.compat.CompatibilityChangeInfo(it.next());
            i++;
        }
        return compatibilityChangeInfoArr;
    }

    void initConfigFromLib(java.io.File file) {
        if (!file.exists() || !file.isDirectory()) {
            android.util.Slog.d(TAG, "No directory " + file + ", skipping");
            return;
        }
        for (java.io.File file2 : file.listFiles()) {
            android.util.Slog.d(TAG, "Found a config file: " + file2.getPath());
            readConfig(file2);
        }
    }

    private void readConfig(java.io.File file) {
        try {
            try {
                java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
                try {
                    for (com.android.server.compat.config.Change change : com.android.server.compat.config.XmlParser.read(bufferedInputStream).getCompatChange()) {
                        android.util.Slog.d(TAG, "Adding: " + change.toString());
                        this.mChanges.put(java.lang.Long.valueOf(change.getId()), new com.android.server.compat.CompatChange(change));
                    }
                    bufferedInputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        bufferedInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "Encountered an error while reading/parsing compat config file", e);
            }
        } finally {
            invalidateCache();
        }
    }

    private void initOverrides() {
        initOverrides(new java.io.File(APP_COMPAT_DATA_DIR, OVERRIDES_FILE), new java.io.File(STATIC_OVERRIDES_PRODUCT_DIR, OVERRIDES_FILE));
    }

    @com.android.internal.annotations.VisibleForTesting
    void initOverrides(java.io.File file, java.io.File file2) {
        java.util.Iterator<com.android.server.compat.CompatChange> it = this.mChanges.values().iterator();
        while (it.hasNext()) {
            it.next().clearOverrides();
        }
        loadOverrides(file2);
        synchronized (this.mOverridesFileLock) {
            try {
                this.mOverridesFile = file;
                this.mBackupOverridesFile = makeBackupFile(file);
                if (this.mBackupOverridesFile.exists()) {
                    this.mOverridesFile.delete();
                    this.mBackupOverridesFile.renameTo(this.mOverridesFile);
                }
                loadOverrides(this.mOverridesFile);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (file2.exists()) {
            saveOverrides();
        }
    }

    private java.io.File makeBackupFile(java.io.File file) {
        return new java.io.File(file.getPath() + ".bak");
    }

    private void loadOverrides(java.io.File file) {
        if (file.exists()) {
            try {
                java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
                try {
                    com.android.server.compat.overrides.Overrides read = com.android.server.compat.overrides.XmlParser.read(bufferedInputStream);
                    if (read == null) {
                        android.util.Slog.w(TAG, "Parsing " + file.getPath() + " failed");
                        bufferedInputStream.close();
                        return;
                    }
                    for (com.android.server.compat.overrides.ChangeOverrides changeOverrides : read.getChangeOverrides()) {
                        long changeId = changeOverrides.getChangeId();
                        com.android.server.compat.CompatChange compatChange = this.mChanges.get(java.lang.Long.valueOf(changeId));
                        if (compatChange == null) {
                            android.util.Slog.w(TAG, "Change ID " + changeId + " not found. Skipping overrides for it.");
                        } else {
                            compatChange.loadOverrides(changeOverrides);
                        }
                    }
                    bufferedInputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        bufferedInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(TAG, "Error processing " + file + " " + e.toString());
            }
        }
    }

    void saveOverrides() {
        synchronized (this.mOverridesFileLock) {
            try {
                if (this.mOverridesFile == null || this.mBackupOverridesFile == null) {
                    return;
                }
                com.android.server.compat.overrides.Overrides overrides = new com.android.server.compat.overrides.Overrides();
                java.util.List<com.android.server.compat.overrides.ChangeOverrides> changeOverrides = overrides.getChangeOverrides();
                java.util.Iterator<com.android.server.compat.CompatChange> it = this.mChanges.values().iterator();
                while (it.hasNext()) {
                    com.android.server.compat.overrides.ChangeOverrides saveOverrides = it.next().saveOverrides();
                    if (saveOverrides != null) {
                        changeOverrides.add(saveOverrides);
                    }
                }
                if (this.mOverridesFile.exists()) {
                    if (this.mBackupOverridesFile.exists()) {
                        this.mOverridesFile.delete();
                    } else if (!this.mOverridesFile.renameTo(this.mBackupOverridesFile)) {
                        android.util.Slog.e(TAG, "Couldn't rename file " + this.mOverridesFile + " to " + this.mBackupOverridesFile);
                        return;
                    }
                }
                try {
                    this.mOverridesFile.createNewFile();
                    try {
                        java.io.PrintWriter printWriter = new java.io.PrintWriter(this.mOverridesFile);
                        try {
                            com.android.server.compat.overrides.XmlWriter.write(new com.android.server.compat.overrides.XmlWriter(printWriter), overrides);
                            printWriter.close();
                        } catch (java.lang.Throwable th) {
                            try {
                                printWriter.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, e.toString());
                    }
                    this.mBackupOverridesFile.delete();
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(TAG, "Could not create override config file: " + e2.toString());
                }
            } finally {
            }
        }
    }

    com.android.internal.compat.IOverrideValidator getOverrideValidator() {
        return this.mOverrideValidator;
    }

    private void invalidateCache() {
        android.app.compat.ChangeIdStateCache.invalidate();
    }

    void recheckOverrides(java.lang.String str) {
        java.lang.Long versionCodeOrNull = getVersionCodeOrNull(str);
        boolean z = false;
        for (com.android.server.compat.CompatChange compatChange : this.mChanges.values()) {
            z |= compatChange.recheckOverride(str, this.mOverrideValidator.getOverrideAllowedStateForRecheck(compatChange.getId(), str), versionCodeOrNull);
        }
        if (z) {
            invalidateCache();
        }
    }

    @android.annotation.Nullable
    private java.lang.Long getVersionCodeOrNull(java.lang.String str) {
        try {
            return java.lang.Long.valueOf(this.mContext.getPackageManager().getApplicationInfo(str, 4194304).longVersionCode);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    void registerContentObserver() {
        this.mOverrideValidator.registerContentObserver();
    }
}
