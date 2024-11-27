package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public final class AppsFilterImpl extends com.android.server.pm.AppsFilterLocked implements com.android.server.utils.Watchable, com.android.server.utils.Snappable {

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mQueryableViaUsesPermissionLock"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.Integer>> mPermissionToUids;

    @android.annotation.NonNull
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.AppsFilterSnapshot> mSnapshot;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mQueryableViaUsesPermissionLock"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.Integer>> mUsesPermissionToUids;
    private final com.android.server.utils.WatchableImpl mWatchable = new com.android.server.utils.WatchableImpl();

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChanged() {
        dispatchChange(this);
    }

    private void invalidateCache(java.lang.String str) {
        if (this.mCacheValid.compareAndSet(true, false)) {
            android.util.Slog.i("AppsFilter", "Invalidating cache: " + str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    AppsFilterImpl(com.android.server.pm.FeatureConfig featureConfig, java.lang.String[] strArr, boolean z, @android.annotation.Nullable com.android.server.om.OverlayReferenceMapper.Provider provider, android.os.Handler handler) {
        this.mFeatureConfig = featureConfig;
        this.mForceQueryableByDevicePackageNames = strArr;
        this.mSystemAppsQueryable = z;
        this.mOverlayReferenceMapper = new com.android.server.om.OverlayReferenceMapper(true, provider);
        this.mHandler = handler;
        this.mShouldFilterCache = new com.android.server.utils.WatchedSparseBooleanMatrix();
        this.mShouldFilterCacheSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mShouldFilterCache, this.mShouldFilterCache, "AppsFilter.mShouldFilterCache");
        this.mImplicitlyQueryable = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mImplicitQueryableSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mImplicitlyQueryable, this.mImplicitlyQueryable, "AppsFilter.mImplicitlyQueryable");
        this.mRetainedImplicitlyQueryable = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mRetainedImplicitlyQueryableSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mRetainedImplicitlyQueryable, this.mRetainedImplicitlyQueryable, "AppsFilter.mRetainedImplicitlyQueryable");
        this.mQueriesViaPackage = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mQueriesViaPackageSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mQueriesViaPackage, this.mQueriesViaPackage, "AppsFilter.mQueriesViaPackage");
        this.mQueriesViaComponent = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mQueriesViaComponentSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mQueriesViaComponent, this.mQueriesViaComponent, "AppsFilter.mQueriesViaComponent");
        this.mQueryableViaUsesLibrary = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mQueryableViaUsesLibrarySnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mQueryableViaUsesLibrary, this.mQueryableViaUsesLibrary, "AppsFilter.mQueryableViaUsesLibrary");
        this.mQueryableViaUsesPermission = new com.android.server.utils.WatchedSparseSetArray<>();
        this.mQueryableViaUsesPermissionSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mQueryableViaUsesPermission, this.mQueryableViaUsesPermission, "AppsFilter.mQueryableViaUsesPermission");
        this.mForceQueryable = new com.android.server.utils.WatchedArraySet<>();
        this.mForceQueryableSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mForceQueryable, this.mForceQueryable, "AppsFilter.mForceQueryable");
        this.mProtectedBroadcasts = new com.android.server.utils.WatchedArraySet<>();
        this.mProtectedBroadcastsSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mProtectedBroadcasts, this.mProtectedBroadcasts, "AppsFilter.mProtectedBroadcasts");
        this.mPermissionToUids = new android.util.ArrayMap<>();
        this.mUsesPermissionToUids = new android.util.ArrayMap<>();
        this.mSnapshot = new com.android.server.utils.SnapshotCache<com.android.server.pm.AppsFilterSnapshot>(this, this) { // from class: com.android.server.pm.AppsFilterImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.AppsFilterSnapshot createSnapshot() {
                return new com.android.server.pm.AppsFilterSnapshotImpl(com.android.server.pm.AppsFilterImpl.this);
            }
        };
        readCacheEnabledSysProp();
        android.os.SystemProperties.addChangeCallback(new java.lang.Runnable() { // from class: com.android.server.pm.AppsFilterImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.AppsFilterImpl.this.readCacheEnabledSysProp();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readCacheEnabledSysProp() {
        this.mCacheEnabled = android.os.SystemProperties.getBoolean("debug.pm.use_app_filter_cache", true);
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.AppsFilterSnapshot snapshot() {
        return this.mSnapshot.snapshot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FeatureConfigImpl implements com.android.server.pm.FeatureConfig, com.android.server.compat.CompatChange.ChangeListener {
        private static final java.lang.String FILTERING_ENABLED_NAME = "package_query_filtering_enabled";
        private com.android.server.pm.AppsFilterImpl mAppsFilter;

        @com.android.internal.annotations.GuardedBy({"mDisabledPackages"})
        private final android.util.ArraySet<java.lang.String> mDisabledPackages;
        private volatile boolean mFeatureEnabled;
        private final com.android.server.pm.PackageManagerServiceInjector mInjector;

        @android.annotation.Nullable
        private android.util.SparseBooleanArray mLoggingEnabled;
        private final android.content.pm.PackageManagerInternal mPmInternal;

        private FeatureConfigImpl(android.content.pm.PackageManagerInternal packageManagerInternal, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector) {
            this.mFeatureEnabled = true;
            this.mDisabledPackages = new android.util.ArraySet<>();
            this.mLoggingEnabled = null;
            this.mPmInternal = packageManagerInternal;
            this.mInjector = packageManagerServiceInjector;
        }

        FeatureConfigImpl(com.android.server.pm.AppsFilterImpl.FeatureConfigImpl featureConfigImpl) {
            this.mFeatureEnabled = true;
            this.mDisabledPackages = new android.util.ArraySet<>();
            this.mLoggingEnabled = null;
            this.mInjector = null;
            this.mPmInternal = null;
            this.mFeatureEnabled = featureConfigImpl.mFeatureEnabled;
            synchronized (featureConfigImpl.mDisabledPackages) {
                this.mDisabledPackages.addAll((android.util.ArraySet<? extends java.lang.String>) featureConfigImpl.mDisabledPackages);
            }
            this.mLoggingEnabled = featureConfigImpl.mLoggingEnabled;
        }

        public void setAppsFilter(com.android.server.pm.AppsFilterImpl appsFilterImpl) {
            this.mAppsFilter = appsFilterImpl;
        }

        @Override // com.android.server.pm.FeatureConfig
        public void onSystemReady() {
            this.mFeatureEnabled = android.provider.DeviceConfig.getBoolean("package_manager_service", FILTERING_ENABLED_NAME, true);
            android.provider.DeviceConfig.addOnPropertiesChangedListener("package_manager_service", com.android.server.FgThread.getExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.pm.AppsFilterImpl$FeatureConfigImpl$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.pm.AppsFilterImpl.FeatureConfigImpl.this.lambda$onSystemReady$0(properties);
                }
            });
            this.mInjector.getCompatibility().registerListener(135549675L, this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemReady$0(android.provider.DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains(FILTERING_ENABLED_NAME)) {
                synchronized (this) {
                    this.mFeatureEnabled = properties.getBoolean(FILTERING_ENABLED_NAME, true);
                }
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public boolean isGloballyEnabled() {
            return this.mFeatureEnabled;
        }

        @Override // com.android.server.pm.FeatureConfig
        public boolean packageIsEnabled(com.android.server.pm.pkg.AndroidPackage androidPackage) {
            boolean z;
            synchronized (this.mDisabledPackages) {
                z = !this.mDisabledPackages.contains(androidPackage.getPackageName());
            }
            return z;
        }

        @Override // com.android.server.pm.FeatureConfig
        public boolean isLoggingEnabled(int i) {
            return this.mLoggingEnabled != null && this.mLoggingEnabled.indexOfKey(i) >= 0;
        }

        @Override // com.android.server.pm.FeatureConfig
        public void enableLogging(int i, boolean z) {
            int indexOfKey;
            if (z) {
                if (this.mLoggingEnabled == null) {
                    this.mLoggingEnabled = new android.util.SparseBooleanArray();
                }
                this.mLoggingEnabled.put(i, true);
            } else if (this.mLoggingEnabled != null && (indexOfKey = this.mLoggingEnabled.indexOfKey(i)) >= 0) {
                this.mLoggingEnabled.removeAt(indexOfKey);
                if (this.mLoggingEnabled.size() == 0) {
                    this.mLoggingEnabled = null;
                }
            }
        }

        @Override // com.android.server.compat.CompatChange.ChangeListener
        public void onCompatChange(java.lang.String str) {
            com.android.server.pm.Computer computer = (com.android.server.pm.Computer) this.mPmInternal.snapshot();
            com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(str);
            if (androidPackage == null) {
                return;
            }
            long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
            updateEnabledState(androidPackage);
            this.mAppsFilter.updateShouldFilterCacheForPackage(computer, str);
            this.mAppsFilter.logCacheUpdated(4, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, computer.getUserInfos().length, computer.getPackageStates().size(), androidPackage.getUid());
        }

        private void updateEnabledState(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
            boolean isChangeEnabledInternalNoLogging = this.mInjector.getCompatibility().isChangeEnabledInternalNoLogging(135549675L, com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
            synchronized (this.mDisabledPackages) {
                try {
                    if (isChangeEnabledInternalNoLogging) {
                        this.mDisabledPackages.remove(androidPackage.getPackageName());
                    } else {
                        this.mDisabledPackages.add(androidPackage.getPackageName());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mAppsFilter != null) {
                this.mAppsFilter.onChanged();
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public void updatePackageState(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, boolean z) {
            enableLogging(packageStateInternal.getAppId(), (packageStateInternal.getPkg() == null || z || (!packageStateInternal.getPkg().isTestOnly() && !packageStateInternal.getPkg().isDebuggable())) ? false : true);
            if (z) {
                synchronized (this.mDisabledPackages) {
                    this.mDisabledPackages.remove(packageStateInternal.getPackageName());
                }
                if (this.mAppsFilter != null) {
                    this.mAppsFilter.onChanged();
                    return;
                }
                return;
            }
            if (packageStateInternal.getPkg() != null) {
                updateEnabledState(packageStateInternal.getPkg());
            }
        }

        @Override // com.android.server.pm.FeatureConfig
        public com.android.server.pm.FeatureConfig snapshot() {
            return new com.android.server.pm.AppsFilterImpl.FeatureConfigImpl(this);
        }
    }

    public static com.android.server.pm.AppsFilterImpl create(@android.annotation.NonNull com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, @android.annotation.NonNull android.content.pm.PackageManagerInternal packageManagerInternal) {
        java.lang.String[] strArr;
        boolean z = packageManagerServiceInjector.getContext().getResources().getBoolean(android.R.bool.config_flipToScreenOffEnabled);
        com.android.server.pm.AppsFilterImpl.FeatureConfigImpl featureConfigImpl = new com.android.server.pm.AppsFilterImpl.FeatureConfigImpl(packageManagerInternal, packageManagerServiceInjector);
        if (z) {
            strArr = new java.lang.String[0];
        } else {
            java.lang.String[] stringArray = packageManagerServiceInjector.getContext().getResources().getStringArray(android.R.array.config_foldedDeviceStates);
            for (int i = 0; i < stringArray.length; i++) {
                stringArray[i] = stringArray[i].intern();
            }
            strArr = stringArray;
        }
        com.android.server.pm.AppsFilterImpl appsFilterImpl = new com.android.server.pm.AppsFilterImpl(featureConfigImpl, strArr, z, null, packageManagerServiceInjector.getHandler());
        featureConfigImpl.setAppsFilter(appsFilterImpl);
        return appsFilterImpl;
    }

    public com.android.server.pm.FeatureConfig getFeatureConfig() {
        return this.mFeatureConfig;
    }

    public boolean grantImplicitAccess(int i, int i2, boolean z) {
        boolean add;
        if (i == i2) {
            return false;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (z) {
                    add = this.mRetainedImplicitlyQueryable.add(i, java.lang.Integer.valueOf(i2));
                } else {
                    add = this.mImplicitlyQueryable.add(i, java.lang.Integer.valueOf(i2));
                }
                if (!this.mCacheReady && add) {
                    this.mNeedToUpdateCacheForImplicitAccess = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (this.mCacheReady) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mCacheLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mShouldFilterCache.put(i, i2, false);
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (add) {
            onChanged();
        }
        return add;
    }

    public void onSystemReady(android.content.pm.PackageManagerInternal packageManagerInternal) {
        this.mOverlayReferenceMapper.rebuildIfDeferred();
        this.mFeatureConfig.onSystemReady();
        updateEntireShouldFilterCacheAsync(packageManagerInternal, 1);
    }

    public void addPackage(com.android.server.pm.Computer computer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, boolean z, boolean z2) {
        int i;
        int i2;
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        if (z) {
            i = 3;
        } else {
            i = 1;
        }
        if (z) {
            try {
                removePackageInternal(computer, packageStateInternal, true, z2);
            } catch (java.lang.Throwable th) {
                onChanged();
                throw th;
            }
        }
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        android.content.pm.UserInfo[] userInfos = computer.getUserInfos();
        android.util.ArraySet<java.lang.String> addPackageInternal = addPackageInternal(packageStateInternal, packageStates);
        if (this.mCacheReady) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            try {
                synchronized (packageManagerTracedLock) {
                    try {
                        updateShouldFilterCacheForPackage(computer, null, packageStateInternal, packageStates, userInfos, -1, packageStates.size());
                        if (addPackageInternal != null) {
                            int i3 = 0;
                            while (i3 < addPackageInternal.size()) {
                                com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = packageStates.get(addPackageInternal.valueAt(i3));
                                if (packageStateInternal2 == null) {
                                    i2 = i3;
                                } else {
                                    i2 = i3;
                                    updateShouldFilterCacheForPackage(computer, null, packageStateInternal2, packageStates, userInfos, -1, packageStates.size());
                                }
                                i3 = i2 + 1;
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        logCacheUpdated(i, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, userInfos.length, packageStates.size(), packageStateInternal.getAppId());
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        } else {
            invalidateCache("addPackage: " + packageStateInternal.getPackageName());
        }
        onChanged();
    }

    @android.annotation.Nullable
    private android.util.ArraySet<java.lang.String> addPackageInternal(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap) {
        boolean z;
        boolean contains;
        boolean z2;
        if (java.util.Objects.equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, packageStateInternal.getPackageName())) {
            this.mSystemSigningDetails = packageStateInternal.getSigningDetails();
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 : arrayMap.values()) {
                if (isSystemSigned(this.mSystemSigningDetails, packageStateInternal2)) {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mForceQueryableLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock) {
                        try {
                            this.mForceQueryable.add(java.lang.Integer.valueOf(packageStateInternal2.getAppId()));
                        } finally {
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
        }
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null) {
            return null;
        }
        java.util.List protectedBroadcasts = pkg.getProtectedBroadcasts();
        if (protectedBroadcasts.size() != 0) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mProtectedBroadcastsLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    int size = this.mProtectedBroadcasts.size();
                    this.mProtectedBroadcasts.addAll(protectedBroadcasts);
                    z2 = this.mProtectedBroadcasts.size() != size;
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (z2) {
                this.mQueriesViaComponentRequireRecompute.set(true);
            }
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock3) {
            try {
                if (!this.mForceQueryable.contains(java.lang.Integer.valueOf(packageStateInternal.getAppId())) && !packageStateInternal.isForceQueryableOverride() && ((!pkg.isForceQueryable() || !com.android.server.pm.ComputerEngine.isMicrogSigned(pkg)) && (!packageStateInternal.isSystem() || (!this.mSystemAppsQueryable && !pkg.isForceQueryable() && !com.android.internal.util.ArrayUtils.contains(this.mForceQueryableByDevicePackageNames, pkg.getPackageName()))))) {
                    z = false;
                    if (!z || (this.mSystemSigningDetails != null && isSystemSigned(this.mSystemSigningDetails, packageStateInternal))) {
                        this.mForceQueryable.add(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                    }
                }
                z = true;
                if (!z) {
                }
                this.mForceQueryable.add(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (!pkg.getUsesPermissions().isEmpty()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mQueryableViaUsesPermissionLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock4) {
                try {
                    java.util.Iterator it = pkg.getUsesPermissions().iterator();
                    while (it.hasNext()) {
                        java.lang.String name = ((com.android.internal.pm.pkg.component.ParsedUsesPermission) it.next()).getName();
                        if (this.mPermissionToUids.containsKey(name)) {
                            android.util.ArraySet<java.lang.Integer> arraySet = this.mPermissionToUids.get(name);
                            for (int i = 0; i < arraySet.size(); i++) {
                                int intValue = arraySet.valueAt(i).intValue();
                                if (intValue != packageStateInternal.getAppId()) {
                                    this.mQueryableViaUsesPermission.add(packageStateInternal.getAppId(), java.lang.Integer.valueOf(intValue));
                                }
                            }
                        }
                        if (!this.mUsesPermissionToUids.containsKey(name)) {
                            this.mUsesPermissionToUids.put(name, new android.util.ArraySet<>());
                        }
                        this.mUsesPermissionToUids.get(name).add(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                    }
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (!pkg.getPermissions().isEmpty()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock5 = this.mQueryableViaUsesPermissionLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock5) {
                try {
                    java.util.Iterator it2 = pkg.getPermissions().iterator();
                    while (it2.hasNext()) {
                        java.lang.String name2 = ((com.android.internal.pm.pkg.component.ParsedPermission) it2.next()).getName();
                        if (this.mUsesPermissionToUids.containsKey(name2)) {
                            android.util.ArraySet<java.lang.Integer> arraySet2 = this.mUsesPermissionToUids.get(name2);
                            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                                int intValue2 = arraySet2.valueAt(i2).intValue();
                                if (intValue2 != packageStateInternal.getAppId()) {
                                    this.mQueryableViaUsesPermission.add(intValue2, java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                                }
                            }
                        }
                        if (!this.mPermissionToUids.containsKey(name2)) {
                            this.mPermissionToUids.put(name2, new android.util.ArraySet<>());
                        }
                        this.mPermissionToUids.get(name2).add(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                    }
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = arrayMap.valueAt(size2);
            if (valueAt.getAppId() != packageStateInternal.getAppId() && valueAt.getPkg() != null) {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg2 = valueAt.getPkg();
                if (!z) {
                    if (!this.mQueriesViaComponentRequireRecompute.get() && com.android.server.pm.AppsFilterUtils.canQueryViaComponents(pkg2, pkg, this.mProtectedBroadcasts)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock6 = this.mQueriesViaComponentLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock6) {
                            try {
                                this.mQueriesViaComponent.add(valueAt.getAppId(), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                    if (com.android.server.pm.AppsFilterUtils.canQueryViaPackage(pkg2, pkg) || com.android.server.pm.AppsFilterUtils.canQueryAsInstaller(valueAt, pkg) || com.android.server.pm.AppsFilterUtils.canQueryAsUpdateOwner(valueAt, pkg)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock7 = this.mQueriesViaPackageLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock7) {
                            try {
                                this.mQueriesViaPackage.add(valueAt.getAppId(), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                    if (com.android.server.pm.AppsFilterUtils.canQueryViaUsesLibrary(pkg2, pkg)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock8 = this.mQueryableViaUsesLibraryLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock8) {
                            try {
                                this.mQueryableViaUsesLibrary.add(valueAt.getAppId(), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock9 = this.mForceQueryableLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock9) {
                    try {
                        contains = this.mForceQueryable.contains(java.lang.Integer.valueOf(valueAt.getAppId()));
                    } finally {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (!contains) {
                    if (!this.mQueriesViaComponentRequireRecompute.get() && com.android.server.pm.AppsFilterUtils.canQueryViaComponents(pkg, pkg2, this.mProtectedBroadcasts)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock10 = this.mQueriesViaComponentLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock10) {
                            try {
                                this.mQueriesViaComponent.add(packageStateInternal.getAppId(), java.lang.Integer.valueOf(valueAt.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                    if (com.android.server.pm.AppsFilterUtils.canQueryViaPackage(pkg, pkg2) || com.android.server.pm.AppsFilterUtils.canQueryAsInstaller(packageStateInternal, pkg2) || com.android.server.pm.AppsFilterUtils.canQueryAsUpdateOwner(packageStateInternal, pkg2)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock11 = this.mQueriesViaPackageLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock11) {
                            try {
                                this.mQueriesViaPackage.add(packageStateInternal.getAppId(), java.lang.Integer.valueOf(valueAt.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                    if (com.android.server.pm.AppsFilterUtils.canQueryViaUsesLibrary(pkg, pkg2)) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock12 = this.mQueryableViaUsesLibraryLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock12) {
                            try {
                                this.mQueryableViaUsesLibrary.add(packageStateInternal.getAppId(), java.lang.Integer.valueOf(valueAt.getAppId()));
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                if (packageStateInternal.getPkg() != null && valueAt.getPkg() != null && (pkgInstruments(packageStateInternal.getPkg(), valueAt.getPkg()) || pkgInstruments(valueAt.getPkg(), packageStateInternal.getPkg()))) {
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock13 = this.mQueriesViaPackageLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock13) {
                        try {
                            this.mQueriesViaPackage.add(packageStateInternal.getAppId(), java.lang.Integer.valueOf(valueAt.getAppId()));
                            this.mQueriesViaPackage.add(valueAt.getAppId(), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                        } finally {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
        }
        int size3 = arrayMap.size();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt2 = arrayMap.valueAt(i3);
            if (valueAt2.getPkg() != null) {
                arrayMap2.put(valueAt2.getPackageName(), valueAt2.getPkg());
            }
        }
        android.util.ArraySet<java.lang.String> addPkg = this.mOverlayReferenceMapper.addPkg(packageStateInternal.getPkg(), arrayMap2);
        this.mFeatureConfig.updatePackageState(packageStateInternal, false);
        return addPkg;
    }

    private void removeAppIdFromVisibilityCache(int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            int i2 = 0;
            while (i2 < this.mShouldFilterCache.size()) {
                try {
                    if (android.os.UserHandle.getAppId(this.mShouldFilterCache.keyAt(i2)) == i) {
                        this.mShouldFilterCache.removeAt(i2);
                        i2--;
                    }
                    i2++;
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void updateEntireShouldFilterCache(com.android.server.pm.Computer computer, int i) {
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        android.content.pm.UserInfo[] userInfos = computer.getUserInfos();
        int i2 = 0;
        while (true) {
            if (i2 >= userInfos.length) {
                i = -10000;
                break;
            } else if (i == userInfos[i2].id) {
                break;
            } else {
                i2++;
            }
        }
        if (i == -10000) {
            android.util.Slog.e("AppsFilter", "We encountered a new user that isn't a member of known users, updating the whole cache");
            i = -1;
        }
        updateEntireShouldFilterCacheInner(computer, packageStates, userInfos, i);
        onChanged();
    }

    private void updateEntireShouldFilterCacheInner(com.android.server.pm.Computer computer, android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, android.content.pm.UserInfo[] userInfoArr, int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            if (i == -1) {
                try {
                    this.mShouldFilterCache.clear();
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            this.mShouldFilterCache.setCapacity(userInfoArr.length * arrayMap.size());
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                updateShouldFilterCacheForPackage(computer, null, arrayMap.valueAt(size), arrayMap, userInfoArr, i, size);
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void updateEntireShouldFilterCacheAsync(android.content.pm.PackageManagerInternal packageManagerInternal, int i) {
        updateEntireShouldFilterCacheAsync(packageManagerInternal, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, i);
    }

    private void updateEntireShouldFilterCacheAsync(final android.content.pm.PackageManagerInternal packageManagerInternal, final long j, final int i) {
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.AppsFilterImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.AppsFilterImpl.this.lambda$updateEntireShouldFilterCacheAsync$0(packageManagerInternal, i, j);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEntireShouldFilterCacheAsync$0(android.content.pm.PackageManagerInternal packageManagerInternal, int i, long j) {
        if (!this.mCacheValid.compareAndSet(false, true)) {
            return;
        }
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        com.android.server.pm.Computer computer = (com.android.server.pm.Computer) packageManagerInternal.snapshot();
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        android.content.pm.UserInfo[] userInfos = computer.getUserInfos();
        arrayMap.ensureCapacity(packageStates.size());
        android.content.pm.UserInfo[][] userInfoArr = {userInfos};
        int size = packageStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            arrayMap.put(packageStates.keyAt(i2), packageStates.valueAt(i2).getPkg());
        }
        updateEntireShouldFilterCacheInner(computer, packageStates, userInfoArr[0], -1);
        logCacheRebuilt(i, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, userInfos.length, packageStates.size());
        if (!this.mCacheValid.compareAndSet(true, true)) {
            android.util.Slog.i("AppsFilter", "Cache invalidated while building, retrying.");
            updateEntireShouldFilterCacheAsync(packageManagerInternal, java.lang.Math.min(2 * j, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY), i);
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mNeedToUpdateCacheForImplicitAccess) {
                    updateShouldFilterCacheForImplicitAccess();
                    this.mNeedToUpdateCacheForImplicitAccess = false;
                }
                this.mCacheReady = true;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        onChanged();
    }

    public void onUserCreated(com.android.server.pm.Computer computer, int i) {
        if (!this.mCacheReady) {
            return;
        }
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        updateEntireShouldFilterCache(computer, i);
        logCacheRebuilt(2, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, computer.getUserInfos().length, computer.getPackageStates().size());
    }

    public void onUserDeleted(com.android.server.pm.Computer computer, int i) {
        if (!this.mCacheReady) {
            return;
        }
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        removeShouldFilterCacheForUser(i);
        onChanged();
        logCacheRebuilt(3, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, computer.getUserInfos().length, computer.getPackageStates().size());
    }

    @com.android.internal.annotations.GuardedBy({"mImplicitlyQueryableLock"})
    private void updateShouldFilterCacheForImplicitAccess() {
        updateShouldFilterCacheForImplicitAccess(this.mRetainedImplicitlyQueryable);
        updateShouldFilterCacheForImplicitAccess(this.mImplicitlyQueryable);
    }

    private void updateShouldFilterCacheForImplicitAccess(com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> watchedSparseSetArray) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            for (int i = 0; i < watchedSparseSetArray.size(); i++) {
                try {
                    java.lang.Integer valueOf = java.lang.Integer.valueOf(watchedSparseSetArray.keyAt(i));
                    java.util.Iterator<java.lang.Integer> it = watchedSparseSetArray.get(valueOf.intValue()).iterator();
                    while (it.hasNext()) {
                        this.mShouldFilterCache.put(valueOf.intValue(), it.next().intValue(), false);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShouldFilterCacheForPackage(com.android.server.pm.Computer computer, java.lang.String str) {
        if (!this.mCacheReady) {
            return;
        }
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        android.content.pm.UserInfo[] userInfos = computer.getUserInfos();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                updateShouldFilterCacheForPackage(computer, null, packageStates.get(str), packageStates, userInfos, -1, packageStates.size());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        onChanged();
    }

    @com.android.internal.annotations.GuardedBy({"mCacheLock"})
    private void updateShouldFilterCacheForPackage(com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String str, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, android.content.pm.UserInfo[] userInfoArr, int i, int i2) {
        for (int min = java.lang.Math.min(i2, arrayMap.size() - 1); min >= 0; min--) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = arrayMap.valueAt(min);
            if (packageStateInternal.getAppId() != valueAt.getAppId() && packageStateInternal.getPackageName() != str && valueAt.getPackageName() != str) {
                if (i == -1) {
                    for (android.content.pm.UserInfo userInfo : userInfoArr) {
                        updateShouldFilterCacheForUser(computer, packageStateInternal, userInfoArr, valueAt, userInfo.id);
                    }
                } else {
                    updateShouldFilterCacheForUser(computer, packageStateInternal, userInfoArr, valueAt, i);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mCacheLock"})
    private void updateShouldFilterCacheForUser(com.android.server.pm.Computer computer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.content.pm.UserInfo[] userInfoArr, com.android.server.pm.pkg.PackageStateInternal packageStateInternal2, int i) {
        for (android.content.pm.UserInfo userInfo : userInfoArr) {
            int i2 = userInfo.id;
            int uid = android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
            int uid2 = android.os.UserHandle.getUid(i2, packageStateInternal2.getAppId());
            boolean shouldFilterApplicationInternal = shouldFilterApplicationInternal(computer, uid, packageStateInternal, packageStateInternal2, i2);
            boolean shouldFilterApplicationInternal2 = shouldFilterApplicationInternal(computer, uid2, packageStateInternal2, packageStateInternal, i);
            this.mShouldFilterCache.put(uid, uid2, shouldFilterApplicationInternal);
            this.mShouldFilterCache.put(uid2, uid, shouldFilterApplicationInternal2);
        }
    }

    private void removeShouldFilterCacheForUser(int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int[] keys = this.mShouldFilterCache.keys();
                int length = keys.length;
                int binarySearch = java.util.Arrays.binarySearch(keys, android.os.UserHandle.getUid(i, 0));
                if (binarySearch < 0) {
                    binarySearch = ~binarySearch;
                }
                if (binarySearch < length && android.os.UserHandle.getUserId(keys[binarySearch]) == i) {
                    int binarySearch2 = java.util.Arrays.binarySearch(keys, android.os.UserHandle.getUid(i + 1, 0) - 1);
                    int i2 = binarySearch2 >= 0 ? binarySearch2 + 1 : ~binarySearch2;
                    if (binarySearch >= i2 || android.os.UserHandle.getUserId(keys[i2 - 1]) != i) {
                        android.util.Slog.w("AppsFilter", "Failed to remove should filter cache for user " + i + ", fromIndex=" + binarySearch + ", toIndex=" + i2);
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return;
                    }
                    this.mShouldFilterCache.removeRange(binarySearch, i2);
                    this.mShouldFilterCache.compact();
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                android.util.Slog.w("AppsFilter", "Failed to remove should filter cache for user " + i + ", fromIndex=" + binarySearch);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private static boolean isSystemSigned(@android.annotation.NonNull android.content.pm.SigningDetails signingDetails, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return packageStateInternal.isSystem() && packageStateInternal.getSigningDetails().signaturesMatchExactly(signingDetails);
    }

    private void collectProtectedBroadcasts(android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, @android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mProtectedBroadcastsLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mProtectedBroadcasts.clear();
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    com.android.server.pm.pkg.PackageStateInternal valueAt = arrayMap.valueAt(size);
                    if (valueAt.getPkg() != null && !valueAt.getPkg().getPackageName().equals(str)) {
                        java.util.List protectedBroadcasts = valueAt.getPkg().getProtectedBroadcasts();
                        if (!protectedBroadcasts.isEmpty()) {
                            this.mProtectedBroadcasts.addAll(protectedBroadcasts);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isQueryableViaComponentWhenRequireRecompute(android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> arraySet, com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        recomputeComponentVisibility(arrayMap);
        return isQueryableViaComponent(i, i2);
    }

    private void recomputeComponentVisibility(android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap) {
        com.android.server.utils.WatchedArraySet watchedArraySet;
        android.util.ArraySet arraySet;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mProtectedBroadcastsLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                watchedArraySet = new com.android.server.utils.WatchedArraySet(this.mProtectedBroadcasts);
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                arraySet = new android.util.ArraySet((android.util.ArraySet) this.mForceQueryable.untrackedStorage());
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        android.util.SparseSetArray<java.lang.Integer> execute = new com.android.server.pm.AppsFilterUtils.ParallelComputeComponentVisibility(arrayMap, arraySet, watchedArraySet).execute();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mQueriesViaComponentLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock3) {
            try {
                this.mQueriesViaComponent = new com.android.server.utils.WatchedSparseSetArray<>(execute);
                this.mQueriesViaComponentSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mQueriesViaComponent, this.mQueriesViaComponent, "AppsFilter.mQueriesViaComponent");
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mQueriesViaComponentRequireRecompute.set(false);
        onChanged();
    }

    public void addPackage(com.android.server.pm.Computer computer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        addPackage(computer, packageStateInternal, false, false);
    }

    public void removePackage(com.android.server.pm.Computer computer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        removePackageInternal(computer, packageStateInternal, false, false);
        logCacheUpdated(2, android.os.SystemClock.currentTimeMicro() - currentTimeMicro, computer.getUserInfos().length, computer.getPackageStates().size(), packageStateInternal.getAppId());
    }

    /* JADX WARN: Finally extract failed */
    private void removePackageInternal(com.android.server.pm.Computer computer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, boolean z, boolean z2) {
        boolean z3;
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        android.content.pm.UserInfo[] userInfos = computer.getUserInfos();
        if (!z || !z2) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                for (android.content.pm.UserInfo userInfo : userInfos) {
                    try {
                        int uid = android.os.UserHandle.getUid(userInfo.id, packageStateInternal.getAppId());
                        this.mImplicitlyQueryable.remove(uid);
                        for (int size = this.mImplicitlyQueryable.size() - 1; size >= 0; size--) {
                            this.mImplicitlyQueryable.remove(this.mImplicitlyQueryable.keyAt(size), java.lang.Integer.valueOf(uid));
                        }
                        if (!z) {
                            this.mRetainedImplicitlyQueryable.remove(uid);
                            for (int size2 = this.mRetainedImplicitlyQueryable.size() - 1; size2 >= 0; size2--) {
                                this.mRetainedImplicitlyQueryable.remove(this.mRetainedImplicitlyQueryable.keyAt(size2), java.lang.Integer.valueOf(uid));
                            }
                        }
                    } finally {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (!this.mQueriesViaComponentRequireRecompute.get()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mQueriesViaComponentLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mQueriesViaComponent.remove(packageStateInternal.getAppId());
                    for (int size3 = this.mQueriesViaComponent.size() - 1; size3 >= 0; size3--) {
                        this.mQueriesViaComponent.remove(this.mQueriesViaComponent.keyAt(size3), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mQueriesViaPackageLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock3) {
            try {
                this.mQueriesViaPackage.remove(packageStateInternal.getAppId());
                for (int size4 = this.mQueriesViaPackage.size() - 1; size4 >= 0; size4--) {
                    this.mQueriesViaPackage.remove(this.mQueriesViaPackage.keyAt(size4), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                }
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = this.mQueryableViaUsesLibraryLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock4) {
            try {
                this.mQueryableViaUsesLibrary.remove(packageStateInternal.getAppId());
                for (int size5 = this.mQueryableViaUsesLibrary.size() - 1; size5 >= 0; size5--) {
                    this.mQueryableViaUsesLibrary.remove(this.mQueryableViaUsesLibrary.keyAt(size5), java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                }
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock5 = this.mQueryableViaUsesPermissionLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock5) {
            try {
                if (packageStateInternal.getPkg() != null && !packageStateInternal.getPkg().getPermissions().isEmpty()) {
                    java.util.Iterator it = packageStateInternal.getPkg().getPermissions().iterator();
                    while (it.hasNext()) {
                        java.lang.String name = ((com.android.internal.pm.pkg.component.ParsedPermission) it.next()).getName();
                        if (this.mPermissionToUids.containsKey(name)) {
                            this.mPermissionToUids.get(name).remove(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                            if (this.mPermissionToUids.get(name).isEmpty()) {
                                this.mPermissionToUids.remove(name);
                            }
                        }
                    }
                }
                if (packageStateInternal.getPkg() != null && !packageStateInternal.getPkg().getUsesPermissions().isEmpty()) {
                    java.util.Iterator it2 = packageStateInternal.getPkg().getUsesPermissions().iterator();
                    while (it2.hasNext()) {
                        java.lang.String name2 = ((com.android.internal.pm.pkg.component.ParsedUsesPermission) it2.next()).getName();
                        if (this.mUsesPermissionToUids.containsKey(name2)) {
                            this.mUsesPermissionToUids.get(name2).remove(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
                            if (this.mUsesPermissionToUids.get(name2).isEmpty()) {
                                this.mUsesPermissionToUids.remove(name2);
                            }
                        }
                    }
                }
                this.mQueryableViaUsesPermission.remove(packageStateInternal.getAppId());
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock6 = this.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock6) {
            try {
                this.mForceQueryable.remove(java.lang.Integer.valueOf(packageStateInternal.getAppId()));
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock7 = this.mProtectedBroadcastsLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock7) {
            try {
                if (packageStateInternal.getPkg() != null && !packageStateInternal.getPkg().getProtectedBroadcasts().isEmpty()) {
                    java.lang.String packageName = packageStateInternal.getPkg().getPackageName();
                    java.util.ArrayList arrayList = new java.util.ArrayList(this.mProtectedBroadcasts.untrackedStorage());
                    collectProtectedBroadcasts(packageStates, packageName);
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!this.mProtectedBroadcasts.contains(arrayList.get(i))) {
                            z3 = true;
                            break;
                        }
                    }
                }
                z3 = false;
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (z3) {
            this.mQueriesViaComponentRequireRecompute.set(true);
        }
        android.util.ArraySet<java.lang.String> removePkg = this.mOverlayReferenceMapper.removePkg(packageStateInternal.getPackageName());
        this.mFeatureConfig.updatePackageState(packageStateInternal, true);
        com.android.server.pm.pkg.SharedUserApi sharedUser = packageStateInternal.hasSharedUser() ? computer.getSharedUser(packageStateInternal.getSharedUserAppId()) : null;
        if (sharedUser != null) {
            android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates2 = sharedUser.getPackageStates();
            for (int size6 = packageStates2.size() - 1; size6 >= 0; size6--) {
                if (packageStates2.valueAt(size6) != packageStateInternal) {
                    addPackageInternal(packageStates2.valueAt(size6), packageStates);
                }
            }
        }
        if (this.mCacheReady) {
            removeAppIdFromVisibilityCache(packageStateInternal.getAppId());
            if (sharedUser != null) {
                android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates3 = sharedUser.getPackageStates();
                int size7 = packageStates3.size() - 1;
                while (true) {
                    if (size7 < 0) {
                        break;
                    }
                    com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates3.valueAt(size7);
                    if (valueAt != packageStateInternal) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock8 = this.mCacheLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock8) {
                            try {
                                updateShouldFilterCacheForPackage(computer, packageStateInternal.getPackageName(), valueAt, packageStates, userInfos, -1, packageStates.size());
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        break;
                    }
                    size7--;
                }
            }
            if (removePkg != null) {
                for (int i2 = 0; i2 < removePkg.size(); i2++) {
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = packageStates.get(removePkg.valueAt(i2));
                    if (packageStateInternal2 != null) {
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock9 = this.mCacheLock;
                        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock9) {
                            try {
                                updateShouldFilterCacheForPackage(computer, null, packageStateInternal2, packageStates, userInfos, -1, packageStates.size());
                            } finally {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
            }
        } else {
            invalidateCache("removePackage: " + packageStateInternal.getPackageName());
        }
        onChanged();
    }

    private static boolean pkgInstruments(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        java.lang.String packageName = androidPackage2.getPackageName();
        java.util.List instrumentations = androidPackage.getInstrumentations();
        for (int size = com.android.internal.util.ArrayUtils.size(instrumentations) - 1; size >= 0; size--) {
            if (java.util.Objects.equals(((com.android.internal.pm.pkg.component.ParsedInstrumentation) instrumentations.get(size)).getTargetPackage(), packageName)) {
                return true;
            }
        }
        return false;
    }

    private void logCacheRebuilt(int i, long j, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_BUILD_REPORTED, i, j, i2, i3, this.mShouldFilterCache.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logCacheUpdated(int i, long j, int i2, int i3, int i4) {
        if (!this.mCacheReady) {
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_UPDATE_REPORTED, i, i4, j, i2, i3, this.mShouldFilterCache.size());
    }
}
