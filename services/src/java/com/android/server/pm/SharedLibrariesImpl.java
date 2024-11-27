package com.android.server.pm;

/* loaded from: classes2.dex */
public final class SharedLibrariesImpl implements com.android.server.pm.SharedLibrariesRead, com.android.server.utils.Watchable, com.android.server.utils.Snappable {
    private static final boolean DEBUG_SHARED_LIBRARIES = false;
    private static final long ENFORCE_NATIVE_SHARED_LIBRARY_DEPENDENCIES = 142191088;
    private static final java.lang.String LIBRARY_TYPE_SDK = "sdk";
    private com.android.server.pm.DeletePackageHelper mDeletePackageHelper;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final com.android.server.utils.Watcher mObserver;
    private final com.android.server.pm.PackageManagerService mPm;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> mSharedLibraries;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>>> mSharedLibrariesSnapshot;
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.SharedLibrariesImpl> mSnapshot;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> mStaticLibsByDeclaringPackage;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>>> mStaticLibsByDeclaringPackageSnapshot;
    private final com.android.server.utils.WatchableImpl mWatchable;

    private com.android.server.utils.SnapshotCache<com.android.server.pm.SharedLibrariesImpl> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.SharedLibrariesImpl>(this, this) { // from class: com.android.server.pm.SharedLibrariesImpl.2
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.SharedLibrariesImpl createSnapshot() {
                com.android.server.pm.SharedLibrariesImpl sharedLibrariesImpl = new com.android.server.pm.SharedLibrariesImpl();
                sharedLibrariesImpl.mWatchable.seal();
                return sharedLibrariesImpl;
            }
        };
    }

    SharedLibrariesImpl(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector) {
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.SharedLibrariesImpl.this.dispatchChange(watchable);
            }
        };
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mSharedLibraries = new com.android.server.utils.WatchedArrayMap<>();
        this.mSharedLibrariesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mSharedLibraries, this.mSharedLibraries, "SharedLibrariesImpl.mSharedLibraries");
        this.mStaticLibsByDeclaringPackage = new com.android.server.utils.WatchedArrayMap<>();
        this.mStaticLibsByDeclaringPackageSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mStaticLibsByDeclaringPackage, this.mStaticLibsByDeclaringPackage, "SharedLibrariesImpl.mStaticLibsByDeclaringPackage");
        registerObservers();
        com.android.server.utils.Watchable.verifyWatchedAttributes(this, this.mObserver);
        this.mSnapshot = makeCache();
    }

    void setDeletePackageHelper(com.android.server.pm.DeletePackageHelper deletePackageHelper) {
        this.mDeletePackageHelper = deletePackageHelper;
    }

    private void registerObservers() {
        this.mSharedLibraries.registerObserver(this.mObserver);
        this.mStaticLibsByDeclaringPackage.registerObserver(this.mObserver);
    }

    private SharedLibrariesImpl(com.android.server.pm.SharedLibrariesImpl sharedLibrariesImpl) {
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.SharedLibrariesImpl.this.dispatchChange(watchable);
            }
        };
        this.mPm = sharedLibrariesImpl.mPm;
        this.mInjector = sharedLibrariesImpl.mInjector;
        this.mSharedLibraries = sharedLibrariesImpl.mSharedLibrariesSnapshot.snapshot();
        this.mSharedLibrariesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mStaticLibsByDeclaringPackage = sharedLibrariesImpl.mStaticLibsByDeclaringPackageSnapshot.snapshot();
        this.mStaticLibsByDeclaringPackageSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

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

    @Override // com.android.server.utils.Snappable
    @android.annotation.NonNull
    public com.android.server.pm.SharedLibrariesRead snapshot() {
        return this.mSnapshot.snapshot();
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> getAll() {
        return this.mSharedLibraries;
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @android.annotation.NonNull
    public com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> getSharedLibraryInfos(@android.annotation.NonNull java.lang.String str) {
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                watchedLongSparseArray = this.mSharedLibraries.get(str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return watchedLongSparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> getSharedLibraries() {
        return this.mSharedLibraries;
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    @android.annotation.Nullable
    public android.content.pm.SharedLibraryInfo getSharedLibraryInfo(@android.annotation.NonNull java.lang.String str, long j) {
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(str);
        if (watchedLongSparseArray == null) {
            return null;
        }
        return watchedLongSparseArray.get(j);
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> getStaticLibraryInfos(@android.annotation.NonNull java.lang.String str) {
        return this.mStaticLibsByDeclaringPackage.get(str);
    }

    @android.annotation.Nullable
    private com.android.server.pm.pkg.PackageStateInternal getLibraryPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        android.content.pm.VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
        if (sharedLibraryInfo.isStatic()) {
            return computer.getPackageStateInternal(computer.resolveInternalPackageName(declaringPackage.getPackageName(), declaringPackage.getLongVersionCode()));
        }
        if (sharedLibraryInfo.isSdk()) {
            return computer.getPackageStateInternal(declaringPackage.getPackageName());
        }
        return null;
    }

    boolean pruneUnusedStaticSharedLibraries(@android.annotation.NonNull com.android.server.pm.Computer computer, long j, long j2) throws java.io.IOException {
        int i;
        java.io.File findPathForUuid = ((android.os.storage.StorageManager) this.mInjector.getSystemService(android.os.storage.StorageManager.class)).findPathForUuid(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> sharedLibraries = computer.getSharedLibraries();
        int size = sharedLibraries.size();
        int i2 = 0;
        while (i2 < size) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> valueAt = sharedLibraries.valueAt(i2);
            if (valueAt != null) {
                int size2 = valueAt.size();
                int i3 = 0;
                while (i3 < size2) {
                    android.content.pm.SharedLibraryInfo valueAt2 = valueAt.valueAt(i3);
                    com.android.server.pm.pkg.PackageStateInternal libraryPackage = getLibraryPackage(computer, valueAt2);
                    if (libraryPackage == null) {
                        i = i2;
                    } else if (currentTimeMillis - libraryPackage.getLastUpdateTime() < j2) {
                        i = i2;
                    } else if (libraryPackage.isSystem()) {
                        i = i2;
                    } else {
                        i = i2;
                        arrayList.add(new android.content.pm.VersionedPackage(libraryPackage.getPkg().getPackageName(), valueAt2.getDeclaringPackage().getLongVersionCode()));
                    }
                    i3++;
                    i2 = i;
                }
            }
            i2++;
        }
        int size3 = arrayList.size();
        for (int i4 = 0; i4 < size3; i4++) {
            android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) arrayList.get(i4);
            if (this.mDeletePackageHelper.deletePackageX(versionedPackage.getPackageName(), versionedPackage.getLongVersionCode(), 0, 2, true) == 1 && findPathForUuid.getUsableSpace() >= j) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.Nullable
    android.content.pm.SharedLibraryInfo getLatestStaticSharedLibraVersion(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.content.pm.SharedLibraryInfo latestStaticSharedLibraVersionLPr;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                latestStaticSharedLibraVersionLPr = getLatestStaticSharedLibraVersionLPr(androidPackage);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return latestStaticSharedLibraVersionLPr;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    @android.annotation.Nullable
    private android.content.pm.SharedLibraryInfo getLatestStaticSharedLibraVersionLPr(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(androidPackage.getStaticSharedLibraryName());
        if (watchedLongSparseArray == null) {
            return null;
        }
        int size = watchedLongSparseArray.size();
        long j = -1;
        for (int i = 0; i < size; i++) {
            long keyAt = watchedLongSparseArray.keyAt(i);
            if (keyAt < androidPackage.getStaticSharedLibraryVersion()) {
                j = java.lang.Math.max(j, keyAt);
            }
        }
        if (j < 0) {
            return null;
        }
        return watchedLongSparseArray.get(j);
    }

    @android.annotation.Nullable
    com.android.server.pm.PackageSetting getStaticSharedLibLatestVersionSetting(@android.annotation.NonNull com.android.server.pm.InstallRequest installRequest) {
        com.android.server.pm.PackageSetting packageLPr;
        if (installRequest.getParsedPackage() == null) {
            return null;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                android.content.pm.SharedLibraryInfo latestStaticSharedLibraVersionLPr = getLatestStaticSharedLibraVersionLPr(installRequest.getParsedPackage());
                packageLPr = latestStaticSharedLibraVersionLPr != null ? this.mPm.mSettings.getPackageLPr(latestStaticSharedLibraVersionLPr.getPackageName()) : null;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return packageLPr;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void applyDefiningSharedLibraryUpdateLPr(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable android.content.pm.SharedLibraryInfo sharedLibraryInfo, @android.annotation.NonNull java.util.function.BiConsumer<android.content.pm.SharedLibraryInfo, android.content.pm.SharedLibraryInfo> biConsumer) {
        if (com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(androidPackage)) {
            if (androidPackage.getSdkLibraryName() != null) {
                android.content.pm.SharedLibraryInfo sharedLibraryInfo2 = getSharedLibraryInfo(androidPackage.getSdkLibraryName(), androidPackage.getSdkLibVersionMajor());
                if (sharedLibraryInfo2 != null) {
                    biConsumer.accept(sharedLibraryInfo2, sharedLibraryInfo);
                    return;
                }
                return;
            }
            if (androidPackage.getStaticSharedLibraryName() != null) {
                android.content.pm.SharedLibraryInfo sharedLibraryInfo3 = getSharedLibraryInfo(androidPackage.getStaticSharedLibraryName(), androidPackage.getStaticSharedLibraryVersion());
                if (sharedLibraryInfo3 != null) {
                    biConsumer.accept(sharedLibraryInfo3, sharedLibraryInfo);
                    return;
                }
                return;
            }
            java.util.Iterator it = androidPackage.getLibraryNames().iterator();
            while (it.hasNext()) {
                android.content.pm.SharedLibraryInfo sharedLibraryInfo4 = getSharedLibraryInfo((java.lang.String) it.next(), -1L);
                if (sharedLibraryInfo4 != null) {
                    biConsumer.accept(sharedLibraryInfo4, sharedLibraryInfo);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void addSharedLibraryLPr(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting) {
        if (sharedLibraryInfo.getPath() != null) {
            set.add(sharedLibraryInfo.getPath());
            return;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage3 = this.mPm.mPackages.get(sharedLibraryInfo.getPackageName());
        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(sharedLibraryInfo.getPackageName());
        if (androidPackage2 == null || !androidPackage2.getPackageName().equals(sharedLibraryInfo.getPackageName()) || (androidPackage3 != null && !androidPackage3.getPackageName().equals(androidPackage2.getPackageName()))) {
            androidPackage2 = androidPackage3;
            packageSetting = packageLPr;
        }
        if (androidPackage2 != null) {
            set.addAll(com.android.server.pm.parsing.pkg.AndroidPackageUtils.getAllCodePaths(androidPackage2));
            applyDefiningSharedLibraryUpdateLPr(androidPackage, sharedLibraryInfo, new java.util.function.BiConsumer() { // from class: com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.content.pm.SharedLibraryInfo) obj).addDependency((android.content.pm.SharedLibraryInfo) obj2);
                }
            });
            if (packageSetting != null) {
                set.addAll(packageSetting.getPkgState().getUsesLibraryFiles());
            }
        }
    }

    void updateSharedLibraries(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map) throws com.android.server.pm.PackageManagerException {
        java.util.ArrayList<android.content.pm.SharedLibraryInfo> collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage, map, null);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                executeSharedLibrariesUpdateLPw(androidPackage, packageSetting, androidPackage2, packageSetting2, collectSharedLibraryInfos, this.mPm.mUserManager.getUserIds());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    void executeSharedLibrariesUpdate(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList, int[] iArr) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                executeSharedLibrariesUpdateLPw(androidPackage, packageSetting, androidPackage2, packageSetting2, arrayList, iArr);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void executeSharedLibrariesUpdateLPw(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList, int[] iArr) {
        applyDefiningSharedLibraryUpdateLPr(androidPackage, null, new java.util.function.BiConsumer() { // from class: com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((android.content.pm.SharedLibraryInfo) obj).clearDependencies();
            }
        });
        if (arrayList != null) {
            packageSetting.getPkgState().setUsesLibraryInfos(arrayList);
            java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
            java.util.Iterator<android.content.pm.SharedLibraryInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                addSharedLibraryLPr(androidPackage, linkedHashSet, it.next(), androidPackage2, packageSetting2);
            }
            packageSetting.setPkgStateLibraryFiles(linkedHashSet);
            int[] iArr2 = new int[iArr.length];
            int i = 0;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (packageSetting.getInstalled(iArr[i2])) {
                    iArr2[i] = iArr[i2];
                    i++;
                }
            }
            java.util.Iterator<android.content.pm.SharedLibraryInfo> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                android.content.pm.SharedLibraryInfo next = it2.next();
                if (next.isStatic()) {
                    com.android.server.pm.PackageSetting packageSettingForMutation = this.mPm.getPackageSettingForMutation(next.getPackageName());
                    if (packageSettingForMutation == null) {
                        android.util.Slog.wtf("PackageManager", "Shared lib without setting: " + next);
                    } else {
                        for (int i3 = 0; i3 < i; i3++) {
                            packageSettingForMutation.setInstalled(true, iArr2[i3]);
                        }
                    }
                }
            }
            return;
        }
        packageSetting.getPkgState().setUsesLibraryInfos(java.util.Collections.emptyList()).setUsesLibraryFiles(java.util.Collections.emptyList());
    }

    private static boolean hasString(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        if (list == null || list2 == null) {
            return false;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                if (list2.get(size2).equals(list.get(size))) {
                    return true;
                }
            }
        }
        return false;
    }

    java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> commitSharedLibraryChanges(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, java.util.List<android.content.pm.SharedLibraryInfo> list, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, int i) {
        if (com.android.internal.util.ArrayUtils.isEmpty(list)) {
            return null;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.Iterator<android.content.pm.SharedLibraryInfo> it = list.iterator();
                while (it.hasNext()) {
                    commitSharedLibraryInfoLPw(it.next());
                }
                try {
                    updateSharedLibraries(androidPackage, packageSetting, null, null, map);
                } catch (com.android.server.pm.PackageManagerException e) {
                    android.util.Slog.e("PackageManager", "updateSharedLibraries failed: ", e);
                }
                if ((i & 16) != 0) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> updateAllSharedLibrariesLPw = updateAllSharedLibrariesLPw(androidPackage, packageSetting, map);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return updateAllSharedLibrariesLPw;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    @android.annotation.Nullable
    java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> updateAllSharedLibrariesLPw(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map) {
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList;
        android.util.ArraySet arraySet;
        java.util.ArrayList arrayList2;
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList3;
        android.util.ArraySet arraySet2;
        if (androidPackage != null && packageSetting != null) {
            java.util.ArrayList arrayList4 = new java.util.ArrayList(1);
            arrayList4.add(android.util.Pair.create(androidPackage, packageSetting));
            arrayList2 = arrayList4;
            arrayList = null;
            arraySet = null;
        } else {
            arrayList = null;
            arraySet = null;
            arrayList2 = null;
        }
        do {
            android.util.Pair pair = arrayList2 == null ? null : (android.util.Pair) arrayList2.remove(0);
            com.android.server.pm.pkg.AndroidPackage androidPackage2 = pair != null ? (com.android.server.pm.pkg.AndroidPackage) pair.first : null;
            com.android.server.pm.PackageSetting packageSetting2 = pair != null ? (com.android.server.pm.PackageSetting) pair.second : null;
            for (int size = this.mPm.mPackages.size() - 1; size >= 0; size--) {
                com.android.server.pm.pkg.AndroidPackage valueAt = this.mPm.mPackages.valueAt(size);
                com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(valueAt.getPackageName());
                if (androidPackage2 == null || hasString(valueAt.getUsesLibraries(), androidPackage2.getLibraryNames()) || hasString(valueAt.getUsesOptionalLibraries(), androidPackage2.getLibraryNames()) || com.android.internal.util.ArrayUtils.contains(valueAt.getUsesStaticLibraries(), androidPackage2.getStaticSharedLibraryName()) || com.android.internal.util.ArrayUtils.contains(valueAt.getUsesSdkLibraries(), androidPackage2.getSdkLibraryName())) {
                    if (arrayList != null) {
                        arrayList3 = arrayList;
                    } else {
                        arrayList3 = new java.util.ArrayList<>();
                    }
                    arrayList3.add(valueAt);
                    if (androidPackage2 == null) {
                        arraySet2 = arraySet;
                    } else {
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet();
                        }
                        if (!arraySet.contains(valueAt.getPackageName())) {
                            arraySet.add(valueAt.getPackageName());
                            arrayList2.add(android.util.Pair.create(valueAt, packageLPr));
                        }
                        arraySet2 = arraySet;
                    }
                    java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList5 = arrayList3;
                    try {
                        updateSharedLibraries(valueAt, packageLPr, androidPackage2, packageSetting2, map);
                    } catch (com.android.server.pm.PackageManagerException e) {
                        if (!packageLPr.isSystem() || packageLPr.isUpdatedSystemApp()) {
                            int i = packageLPr.isUpdatedSystemApp() ? 1 : 0;
                            synchronized (this.mPm.mInstallLock) {
                                this.mDeletePackageHelper.deletePackageLIF(valueAt.getPackageName(), null, true, this.mPm.mUserManager.getUserIds(), i, new com.android.server.pm.PackageRemovedInfo(), true);
                            }
                        }
                        android.util.Slog.e("PackageManager", "updateAllSharedLibrariesLPw failed: " + e.getMessage());
                    }
                    arraySet = arraySet2;
                    arrayList = arrayList5;
                }
            }
            if (arrayList2 == null) {
                break;
            }
        } while (arrayList2.size() > 0);
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    void addBuiltInSharedLibraryLPw(@android.annotation.NonNull com.android.server.SystemConfig.SharedLibraryEntry sharedLibraryEntry) {
        if (getSharedLibraryInfo(sharedLibraryEntry.name, -1L) != null) {
            return;
        }
        commitSharedLibraryInfoLPw(new android.content.pm.SharedLibraryInfo(sharedLibraryEntry.filename, null, null, sharedLibraryEntry.name, -1L, 0, new android.content.pm.VersionedPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0L), null, null, sharedLibraryEntry.isNative));
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void commitSharedLibraryInfoLPw(@android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        java.lang.String name = sharedLibraryInfo.getName();
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(name);
        if (watchedLongSparseArray == null) {
            watchedLongSparseArray = new com.android.server.utils.WatchedLongSparseArray<>();
            this.mSharedLibraries.put(name, watchedLongSparseArray);
        }
        java.lang.String packageName = sharedLibraryInfo.getDeclaringPackage().getPackageName();
        if (sharedLibraryInfo.getType() == 2) {
            this.mStaticLibsByDeclaringPackage.put(packageName, watchedLongSparseArray);
        }
        watchedLongSparseArray.put(sharedLibraryInfo.getLongVersion(), sharedLibraryInfo);
    }

    boolean removeSharedLibrary(@android.annotation.NonNull java.lang.String str, long j) {
        int i;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(str);
                int i2 = 0;
                if (watchedLongSparseArray == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                int indexOfKey = watchedLongSparseArray.indexOfKey(j);
                if (indexOfKey < 0) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                android.content.pm.SharedLibraryInfo valueAt = watchedLongSparseArray.valueAt(indexOfKey);
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                int[] userIds = this.mPm.mUserManager.getUserIds();
                int length = userIds.length;
                while (i2 < length) {
                    int i3 = userIds[i2];
                    int i4 = length;
                    java.util.List list = (java.util.List) snapshotComputer.getPackagesUsingSharedLibrary(valueAt, 0L, 1000, i3).first;
                    if (list != null) {
                        java.util.Iterator it = list.iterator();
                        while (it.hasNext()) {
                            com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(((android.content.pm.VersionedPackage) it.next()).getPackageName());
                            if (packageLPr == null) {
                                i = i3;
                            } else {
                                i = i3;
                                packageLPr.setOverlayPathsForLibrary(valueAt.getName(), null, i);
                            }
                            i3 = i;
                        }
                    }
                    i2++;
                    length = i4;
                }
                watchedLongSparseArray.remove(j);
                if (watchedLongSparseArray.size() <= 0) {
                    this.mSharedLibraries.remove(str);
                    if (valueAt.getType() == 2) {
                        this.mStaticLibsByDeclaringPackage.remove(valueAt.getDeclaringPackage().getPackageName());
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return true;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    java.util.List<android.content.pm.SharedLibraryInfo> getAllowedSharedLibInfos(com.android.server.pm.InstallRequest installRequest) {
        com.android.server.pm.PackageSetting packageSetting;
        com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage = installRequest.getParsedPackage();
        if (installRequest.getSdkSharedLibraryInfo() == null && installRequest.getStaticSharedLibraryInfo() == null && installRequest.getDynamicSharedLibraryInfos() == null) {
            return null;
        }
        if (installRequest.getSdkSharedLibraryInfo() != null) {
            return java.util.Collections.singletonList(installRequest.getSdkSharedLibraryInfo());
        }
        if (installRequest.getStaticSharedLibraryInfo() != null) {
            return java.util.Collections.singletonList(installRequest.getStaticSharedLibraryInfo());
        }
        boolean z = false;
        if (!((parsedPackage == null || !(installRequest.getScannedPackageSetting() != null && installRequest.getScannedPackageSetting().isSystem()) || installRequest.getDynamicSharedLibraryInfos() == null) ? false : true)) {
            return null;
        }
        if (installRequest.getScannedPackageSetting() != null && installRequest.getScannedPackageSetting().isUpdatedSystemApp()) {
            z = true;
        }
        if (z) {
            if (installRequest.getScanRequestDisabledPackageSetting() == null) {
                packageSetting = installRequest.getScanRequestOldPackageSetting();
            } else {
                packageSetting = installRequest.getScanRequestDisabledPackageSetting();
            }
        } else {
            packageSetting = null;
        }
        if (z && (packageSetting.getPkg() == null || packageSetting.getPkg().getLibraryNames() == null)) {
            android.util.Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares libraries that are not declared on the system image; skipping");
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(installRequest.getDynamicSharedLibraryInfos().size());
        for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : installRequest.getDynamicSharedLibraryInfos()) {
            java.lang.String name = sharedLibraryInfo.getName();
            if (z && !packageSetting.getPkg().getLibraryNames().contains(name)) {
                android.util.Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares library " + name + " that is not declared on system image; skipping");
            } else {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        if (getSharedLibraryInfo(name, -1L) != null) {
                            android.util.Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares library " + name + " that already exists; skipping");
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            arrayList.add(sharedLibraryInfo);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            }
        }
        return arrayList;
    }

    java.util.ArrayList<android.content.pm.SharedLibraryInfo> collectSharedLibraryInfos(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, @android.annotation.Nullable java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map2) throws com.android.server.pm.PackageManagerException {
        java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList;
        if (androidPackage == null) {
            return null;
        }
        com.android.server.compat.PlatformCompat compatibility = this.mInjector.getCompatibility();
        if (androidPackage.getUsesLibraries().isEmpty()) {
            arrayList = null;
        } else {
            arrayList = collectSharedLibraryInfos(androidPackage.getUsesLibraries(), null, null, null, androidPackage.getPackageName(), "shared", true, androidPackage.getTargetSdkVersion(), null, map, map2);
        }
        if (!androidPackage.getUsesStaticLibraries().isEmpty()) {
            arrayList = collectSharedLibraryInfos(androidPackage.getUsesStaticLibraries(), androidPackage.getUsesStaticLibrariesVersions(), androidPackage.getUsesStaticLibrariesCertDigests(), null, androidPackage.getPackageName(), "static shared", true, androidPackage.getTargetSdkVersion(), arrayList, map, map2);
        }
        if (!androidPackage.getUsesOptionalLibraries().isEmpty()) {
            arrayList = collectSharedLibraryInfos(androidPackage.getUsesOptionalLibraries(), null, null, null, androidPackage.getPackageName(), "shared", false, androidPackage.getTargetSdkVersion(), arrayList, map, map2);
        }
        if (compatibility.isChangeEnabledInternal(ENFORCE_NATIVE_SHARED_LIBRARY_DEPENDENCIES, androidPackage.getPackageName(), androidPackage.getTargetSdkVersion())) {
            if (!androidPackage.getUsesNativeLibraries().isEmpty()) {
                arrayList = collectSharedLibraryInfos(androidPackage.getUsesNativeLibraries(), null, null, null, androidPackage.getPackageName(), "native shared", true, androidPackage.getTargetSdkVersion(), arrayList, map, map2);
            }
            if (!androidPackage.getUsesOptionalNativeLibraries().isEmpty()) {
                arrayList = collectSharedLibraryInfos(androidPackage.getUsesOptionalNativeLibraries(), null, null, null, androidPackage.getPackageName(), "native shared", false, androidPackage.getTargetSdkVersion(), arrayList, map, map2);
            }
        }
        if (!androidPackage.getUsesSdkLibraries().isEmpty()) {
            return collectSharedLibraryInfos(androidPackage.getUsesSdkLibraries(), androidPackage.getUsesSdkLibrariesVersionsMajor(), androidPackage.getUsesSdkLibrariesCertDigests(), androidPackage.getUsesSdkLibrariesOptional(), androidPackage.getPackageName(), LIBRARY_TYPE_SDK, !android.content.pm.Flags.sdkLibIndependence(), androidPackage.getTargetSdkVersion(), arrayList, map, map2);
        }
        return arrayList;
    }

    private java.util.ArrayList<android.content.pm.SharedLibraryInfo> collectSharedLibraryInfos(@android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.Nullable long[] jArr, @android.annotation.Nullable java.lang.String[][] strArr, @android.annotation.Nullable boolean[] zArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z, int i, @android.annotation.Nullable java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, @android.annotation.Nullable java.util.Map<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> map2) throws com.android.server.pm.PackageManagerException {
        android.content.pm.SharedLibraryInfo sharedLibraryInfo;
        int i2;
        java.lang.String[] computeSignaturesSha256Digests;
        com.android.server.pm.SharedLibrariesImpl sharedLibrariesImpl = this;
        int size = list.size();
        int i3 = 0;
        java.util.ArrayList<android.content.pm.SharedLibraryInfo> arrayList2 = arrayList;
        int i4 = 0;
        while (i4 < size) {
            java.lang.String str3 = list.get(i4);
            long j = jArr != null ? jArr[i4] : -1L;
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = sharedLibrariesImpl.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    sharedLibraryInfo = com.android.server.pm.SharedLibraryUtils.getSharedLibraryInfo(str3, j, sharedLibrariesImpl.mSharedLibraries, map2);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (sharedLibraryInfo == null) {
                if (z || !(!LIBRARY_TYPE_SDK.equals(str2) || zArr == null || zArr[i4])) {
                    throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires unavailable " + str2 + " library " + str3 + "; failing!");
                }
                i2 = i3;
            } else {
                if (jArr == null || strArr == null) {
                    i2 = i3;
                } else {
                    if (sharedLibraryInfo.getLongVersion() != jArr[i4]) {
                        throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires unavailable " + str2 + " library " + str3 + " version " + sharedLibraryInfo.getLongVersion() + "; failing!");
                    }
                    com.android.server.pm.pkg.AndroidPackage androidPackage = map.get(sharedLibraryInfo.getPackageName());
                    android.content.pm.SigningDetails signingDetails = androidPackage == null ? null : androidPackage.getSigningDetails();
                    if (signingDetails == null) {
                        throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires unavailable " + str2 + " library; failing!");
                    }
                    java.lang.String[] strArr2 = strArr[i4];
                    if (strArr2.length > 1) {
                        if (i >= 27) {
                            computeSignaturesSha256Digests = android.util.PackageUtils.computeSignaturesSha256Digests(signingDetails.getSignatures());
                        } else {
                            android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[1];
                            signatureArr[i3] = signingDetails.getSignatures()[i3];
                            computeSignaturesSha256Digests = android.util.PackageUtils.computeSignaturesSha256Digests(signatureArr);
                        }
                        if (strArr2.length != computeSignaturesSha256Digests.length) {
                            throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires differently signed " + str2 + " library; failing!");
                        }
                        java.util.Arrays.sort(computeSignaturesSha256Digests);
                        java.util.Arrays.sort(strArr2);
                        int length = computeSignaturesSha256Digests.length;
                        for (int i5 = i3; i5 < length; i5++) {
                            if (!computeSignaturesSha256Digests[i5].equalsIgnoreCase(strArr2[i5])) {
                                throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires differently signed " + str2 + " library; failing!");
                            }
                        }
                        i2 = 0;
                    } else {
                        i2 = 0;
                        try {
                            if (!signingDetails.hasSha256Certificate(libcore.util.HexEncoding.decode(strArr2[0], false))) {
                                throw new com.android.server.pm.PackageManagerException(-9, "Package " + str + " requires differently signed " + str2 + " library; failing!");
                            }
                        } catch (java.lang.IllegalArgumentException e) {
                            throw new com.android.server.pm.PackageManagerException(-130, "Package " + str + " declares bad certificate digest for " + str2 + " library " + str3 + "; failing!");
                        }
                    }
                }
                if (arrayList2 == null) {
                    arrayList2 = new java.util.ArrayList<>();
                }
                arrayList2.add(sharedLibraryInfo);
            }
            i4++;
            i3 = i2;
            sharedLibrariesImpl = this;
        }
        return arrayList2;
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        boolean isCheckIn = dumpState.isCheckIn();
        int size = this.mSharedLibraries.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(this.mSharedLibraries.keyAt(i));
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    android.content.pm.SharedLibraryInfo valueAt = watchedLongSparseArray.valueAt(i2);
                    if (!isCheckIn) {
                        if (!z) {
                            if (dumpState.onTitlePrinted()) {
                                printWriter.println();
                            }
                            printWriter.println("Libraries:");
                            z = true;
                        }
                        printWriter.print("  ");
                    } else {
                        printWriter.print("lib,");
                    }
                    printWriter.print(valueAt.getName());
                    if (valueAt.isStatic()) {
                        printWriter.print(" version=" + valueAt.getLongVersion());
                    }
                    if (!isCheckIn) {
                        printWriter.print(" -> ");
                    }
                    if (valueAt.getPath() != null) {
                        if (valueAt.isNative()) {
                            printWriter.print(" (so) ");
                        } else {
                            printWriter.print(" (jar) ");
                        }
                        printWriter.print(valueAt.getPath());
                    } else {
                        printWriter.print(" (apk) ");
                        printWriter.print(valueAt.getPackageName());
                    }
                    printWriter.println();
                }
            }
        }
    }

    @Override // com.android.server.pm.SharedLibrariesRead
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    public void dumpProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        int size = this.mSharedLibraries.size();
        for (int i = 0; i < size; i++) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = this.mSharedLibraries.get(this.mSharedLibraries.keyAt(i));
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    android.content.pm.SharedLibraryInfo valueAt = watchedLongSparseArray.valueAt(i2);
                    long start = protoOutputStream.start(2246267895811L);
                    protoOutputStream.write(1138166333441L, valueAt.getName());
                    boolean z = valueAt.getPath() != null;
                    protoOutputStream.write(1133871366146L, z);
                    if (z) {
                        protoOutputStream.write(1138166333443L, valueAt.getPath());
                    } else {
                        protoOutputStream.write(1138166333444L, valueAt.getPackageName());
                    }
                    protoOutputStream.end(start);
                }
            }
        }
    }
}
