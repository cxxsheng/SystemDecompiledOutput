package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class AppsFilterBase implements com.android.server.pm.AppsFilterSnapshot {
    protected static final boolean CACHE_INVALID = false;
    protected static final int CACHE_REBUILD_DELAY_MAX_MS = 10000;
    protected static final int CACHE_REBUILD_DELAY_MIN_MS = 10000;
    protected static final boolean CACHE_VALID = true;
    protected static final boolean DEBUG_ALLOW_ALL = false;
    protected static final boolean DEBUG_LOGGING = false;
    public static final boolean DEBUG_TRACING = false;
    protected static final java.lang.String TAG = "AppsFilter";

    @android.annotation.NonNull
    protected com.android.server.pm.FeatureConfig mFeatureConfig;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedArraySet<java.lang.Integer> mForceQueryable;

    @android.annotation.NonNull
    protected java.lang.String[] mForceQueryableByDevicePackageNames;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArraySet<java.lang.Integer>> mForceQueryableSnapshot;
    protected android.os.Handler mHandler;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mImplicitQueryableSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mImplicitlyQueryable;

    @android.annotation.NonNull
    protected com.android.server.om.OverlayReferenceMapper mOverlayReferenceMapper;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedArraySet<java.lang.String> mProtectedBroadcasts;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArraySet<java.lang.String>> mProtectedBroadcastsSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mQueriesViaComponent;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mQueriesViaComponentSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mQueriesViaPackage;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mQueriesViaPackageSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mQueryableViaUsesLibrary;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mQueryableViaUsesLibrarySnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mQueryableViaUsesPermission;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mQueryableViaUsesPermissionSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> mRetainedImplicitlyQueryable;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseSetArray<java.lang.Integer>> mRetainedImplicitlyQueryableSnapshot;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    protected com.android.server.utils.WatchedSparseBooleanMatrix mShouldFilterCache;

    @android.annotation.NonNull
    protected com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseBooleanMatrix> mShouldFilterCacheSnapshot;

    @android.annotation.NonNull
    protected boolean mSystemAppsQueryable;

    @android.annotation.Nullable
    protected android.content.pm.SigningDetails mSystemSigningDetails;
    protected java.util.concurrent.atomic.AtomicBoolean mQueriesViaComponentRequireRecompute = new java.util.concurrent.atomic.AtomicBoolean(false);
    protected volatile boolean mCacheReady = false;
    protected volatile boolean mCacheEnabled = true;
    protected volatile boolean mNeedToUpdateCacheForImplicitAccess = false;
    protected final java.util.concurrent.atomic.AtomicBoolean mCacheValid = new java.util.concurrent.atomic.AtomicBoolean(false);

    protected interface ToString<T> {
        java.lang.String toString(T t);
    }

    protected boolean isForceQueryable(int i) {
        return this.mForceQueryable.contains(java.lang.Integer.valueOf(i));
    }

    protected boolean isQueryableViaPackage(int i, int i2) {
        return this.mQueriesViaPackage.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isQueryableViaComponent(int i, int i2) {
        return this.mQueriesViaComponent.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isImplicitlyQueryable(int i, int i2) {
        return this.mImplicitlyQueryable.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isRetainedImplicitlyQueryable(int i, int i2) {
        return this.mRetainedImplicitlyQueryable.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isQueryableViaUsesLibrary(int i, int i2) {
        return this.mQueryableViaUsesLibrary.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isQueryableViaUsesPermission(int i, int i2) {
        return this.mQueryableViaUsesPermission.contains(i, java.lang.Integer.valueOf(i2));
    }

    protected boolean isQueryableViaComponentWhenRequireRecompute(android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> arraySet, com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        if (packageStateInternal == null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = arraySet.valueAt(size).getPkg();
                if (pkg != null && com.android.server.pm.AppsFilterUtils.canQueryViaComponents(pkg, androidPackage, this.mProtectedBroadcasts)) {
                    return true;
                }
            }
            return false;
        }
        if (packageStateInternal.getPkg() != null && com.android.server.pm.AppsFilterUtils.canQueryViaComponents(packageStateInternal.getPkg(), androidPackage, this.mProtectedBroadcasts)) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.pm.AppsFilterSnapshot
    @android.annotation.Nullable
    public android.util.SparseArray<int[]> getVisibilityAllowList(com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int[] iArr, android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap) {
        int i;
        int i2;
        int[] iArr2;
        int[] iArr3 = null;
        if (isForceQueryable(packageStateInternal.getAppId())) {
            return null;
        }
        android.util.SparseArray<int[]> sparseArray = new android.util.SparseArray<>(iArr.length);
        int i3 = 0;
        int i4 = 0;
        while (i4 < iArr.length) {
            int i5 = iArr[i4];
            int size = arrayMap.size();
            int[] iArr4 = new int[size];
            int size2 = arrayMap.size() - 1;
            int[] iArr5 = iArr3;
            int i6 = i3;
            while (size2 >= 0) {
                com.android.server.pm.pkg.PackageStateInternal valueAt = arrayMap.valueAt(size2);
                int appId = valueAt.getAppId();
                if (appId < 10000) {
                    i2 = i3;
                    i = i6;
                } else {
                    int binarySearch = java.util.Arrays.binarySearch(iArr4, i3, i6, appId);
                    if (binarySearch >= 0) {
                        i2 = i3;
                        i = i6;
                    } else {
                        i = i6;
                        if (shouldFilterApplication(packageDataSnapshot, android.os.UserHandle.getUid(i5, appId), valueAt, packageStateInternal, i5)) {
                            i2 = 0;
                        } else {
                            if (iArr5 != null) {
                                iArr2 = iArr5;
                            } else {
                                iArr2 = new int[size];
                            }
                            int i7 = ~binarySearch;
                            int i8 = i - i7;
                            i2 = 0;
                            java.lang.System.arraycopy(iArr4, i7, iArr2, 0, i8);
                            iArr4[i7] = appId;
                            java.lang.System.arraycopy(iArr2, 0, iArr4, i7 + 1, i8);
                            i6 = i + 1;
                            iArr5 = iArr2;
                            size2--;
                            i3 = i2;
                        }
                    }
                }
                i6 = i;
                size2--;
                i3 = i2;
            }
            sparseArray.put(i5, java.util.Arrays.copyOf(iArr4, i6));
            i4++;
            i3 = i3;
            iArr3 = null;
        }
        return sparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    @android.annotation.Nullable
    android.util.SparseArray<int[]> getVisibilityAllowList(com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int[] iArr, com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap) {
        return getVisibilityAllowList(packageDataSnapshot, packageStateInternal, iArr, watchedArrayMap.untrackedStorage());
    }

    private static boolean isQueryableBySdkSandbox(int i, int i2) {
        return android.content.pm.Flags.allowSdkSandboxQueryIntentActivities() && i2 == android.os.Process.getAppUidForSdkSandboxUid(i);
    }

    @Override // com.android.server.pm.AppsFilterSnapshot
    public boolean shouldFilterApplication(com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, int i, @android.annotation.Nullable java.lang.Object obj, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i2) {
        try {
            int appId = android.os.UserHandle.getAppId(i);
            if (appId >= 10000 && packageStateInternal.getAppId() >= 10000 && appId != packageStateInternal.getAppId()) {
                if (android.os.Process.isSdkSandboxUid(appId)) {
                    int uid = android.os.UserHandle.getUid(i2, packageStateInternal.getAppId());
                    return (isForceQueryable(packageStateInternal.getAppId()) || isImplicitlyQueryable(i, uid) || isQueryableBySdkSandbox(i, uid)) ? false : true;
                }
                if (this.mCacheReady && this.mCacheEnabled) {
                    if (!shouldFilterApplicationUsingCache(i, packageStateInternal.getAppId(), i2)) {
                        return false;
                    }
                } else if (!shouldFilterApplicationInternal((com.android.server.pm.Computer) packageDataSnapshot, i, obj, packageStateInternal, i2)) {
                    return false;
                }
                if (this.mFeatureConfig.isLoggingEnabled(appId)) {
                    log(obj, packageStateInternal, "BLOCKED");
                }
                return true;
            }
            return false;
        } finally {
        }
    }

    protected boolean shouldFilterApplicationUsingCache(int i, int i2, int i3) {
        int indexOfKey = this.mShouldFilterCache.indexOfKey(i);
        if (indexOfKey < 0) {
            android.util.Slog.wtf(TAG, "Encountered calling uid with no cached rules: " + i);
            return true;
        }
        int uid = android.os.UserHandle.getUid(i3, i2);
        int indexOfKey2 = this.mShouldFilterCache.indexOfKey(uid);
        if (indexOfKey2 < 0) {
            android.util.Slog.w(TAG, "Encountered calling -> target with no cached rules: " + i + " -> " + uid);
            return true;
        }
        return this.mShouldFilterCache.valueAt(indexOfKey, indexOfKey2);
    }

    protected boolean shouldFilterApplicationInternal(com.android.server.pm.Computer computer, int i, java.lang.Object obj, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        try {
            if (!this.mFeatureConfig.isGloballyEnabled()) {
                return false;
            }
            if (obj == null) {
                android.util.Slog.wtf(TAG, "No setting found for non system uid " + i);
                return true;
            }
            int appId = android.os.UserHandle.getAppId(i);
            int appId2 = packageStateInternal.getAppId();
            if (appId == appId2 || appId < 10000 || appId2 < 10000) {
                return false;
            }
            android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> arraySet = new android.util.ArraySet<>();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = null;
            if (obj instanceof com.android.server.pm.pkg.PackageStateInternal) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal4 = (com.android.server.pm.pkg.PackageStateInternal) obj;
                if (packageStateInternal4.hasSharedUser()) {
                    com.android.server.pm.pkg.SharedUserApi sharedUser = computer.getSharedUser(packageStateInternal4.getSharedUserAppId());
                    if (sharedUser != null) {
                        arraySet.addAll(sharedUser.getPackageStates());
                    }
                } else {
                    packageStateInternal3 = packageStateInternal4;
                }
                packageStateInternal2 = packageStateInternal3;
            } else {
                arraySet.addAll(((com.android.server.pm.SharedUserSetting) obj).getPackageStates());
                packageStateInternal2 = null;
            }
            if (packageStateInternal2 == null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    com.android.server.pm.pkg.AndroidPackage pkg = arraySet.valueAt(size).getPkg();
                    if (pkg != null && !this.mFeatureConfig.packageIsEnabled(pkg)) {
                        return false;
                    }
                }
            } else if (packageStateInternal2.getPkg() != null && !this.mFeatureConfig.packageIsEnabled(packageStateInternal2.getPkg())) {
                return false;
            }
            try {
                if (packageStateInternal2 == null) {
                    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg2 = arraySet.valueAt(size2).getPkg();
                        if (pkg2 != null && com.android.server.pm.AppsFilterUtils.requestsQueryAllPackages(pkg2)) {
                            return false;
                        }
                    }
                } else if (packageStateInternal2.getPkg() != null && com.android.server.pm.AppsFilterUtils.requestsQueryAllPackages(packageStateInternal2.getPkg())) {
                    return false;
                }
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg3 = packageStateInternal.getPkg();
                if (pkg3 == null) {
                    return true;
                }
                if (pkg3.isStaticSharedLibrary()) {
                    return false;
                }
                if (isForceQueryable(appId2)) {
                    return false;
                }
                if (isQueryableViaPackage(appId, appId2)) {
                    return false;
                }
                try {
                    if (!this.mQueriesViaComponentRequireRecompute.get()) {
                        if (isQueryableViaComponent(appId, appId2)) {
                            return false;
                        }
                    } else if (isQueryableViaComponentWhenRequireRecompute(computer.getPackageStates(), packageStateInternal2, arraySet, pkg3, appId, appId2)) {
                        return false;
                    }
                    if (isImplicitlyQueryable(i, android.os.UserHandle.getUid(i2, appId2))) {
                        return false;
                    }
                    if (isRetainedImplicitlyQueryable(i, android.os.UserHandle.getUid(i2, appId2))) {
                        return false;
                    }
                    try {
                        java.lang.String packageName = pkg3.getPackageName();
                        if (!arraySet.isEmpty()) {
                            int size3 = arraySet.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                if (this.mOverlayReferenceMapper.isValidActor(packageName, arraySet.valueAt(i3).getPackageName())) {
                                    return false;
                                }
                            }
                        } else if (this.mOverlayReferenceMapper.isValidActor(packageName, packageStateInternal2.getPackageName())) {
                            return false;
                        }
                        if (isQueryableViaUsesLibrary(appId, appId2)) {
                            return false;
                        }
                        if (isQueryableViaUsesPermission(appId, appId2)) {
                            return false;
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.pm.AppsFilterSnapshot
    public boolean canQueryPackage(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
        if (android.os.UserHandle.getAppId(androidPackage.getUid()) >= 10000 && this.mFeatureConfig.packageIsEnabled(androidPackage) && !com.android.server.pm.AppsFilterUtils.requestsQueryAllPackages(androidPackage)) {
            return !androidPackage.getQueriesPackages().isEmpty() && androidPackage.getQueriesPackages().contains(str);
        }
        return true;
    }

    private static void log(java.lang.Object obj, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("interaction: ");
        if (obj == null) {
            obj = "system";
        }
        sb.append(obj);
        sb.append(" -> ");
        sb.append(packageStateInternal);
        sb.append(" ");
        sb.append(str);
        android.util.Slog.i(TAG, sb.toString());
    }

    @Override // com.android.server.pm.AppsFilterSnapshot
    public void dumpQueries(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.DumpState dumpState, final int[] iArr, final com.android.internal.util.function.QuadFunction<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Boolean, java.lang.String[]> quadFunction) {
        final android.util.SparseArray sparseArray = new android.util.SparseArray();
        com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString = new com.android.server.pm.AppsFilterBase.ToString() { // from class: com.android.server.pm.AppsFilterBase$$ExternalSyntheticLambda0
            @Override // com.android.server.pm.AppsFilterBase.ToString
            public final java.lang.String toString(java.lang.Object obj) {
                java.lang.String lambda$dumpQueries$0;
                lambda$dumpQueries$0 = com.android.server.pm.AppsFilterBase.lambda$dumpQueries$0(sparseArray, iArr, quadFunction, (java.lang.Integer) obj);
                return lambda$dumpQueries$0;
            }
        };
        printWriter.println();
        printWriter.println("Queries:");
        dumpState.onTitlePrinted();
        if (!this.mFeatureConfig.isGloballyEnabled()) {
            printWriter.println("  DISABLED");
            return;
        }
        printWriter.println("  system apps queryable: " + this.mSystemAppsQueryable);
        dumpForceQueryable(printWriter, num, toString);
        dumpQueriesViaPackage(printWriter, num, toString);
        dumpQueriesViaComponent(printWriter, num, toString);
        dumpQueriesViaImplicitlyQueryable(printWriter, num, iArr, toString);
        dumpQueriesViaUsesLibrary(printWriter, num, toString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$dumpQueries$0(android.util.SparseArray sparseArray, int[] iArr, com.android.internal.util.function.QuadFunction quadFunction, java.lang.Integer num) {
        java.lang.String str = (java.lang.String) sparseArray.get(num.intValue());
        if (str == null) {
            int callingUid = android.os.Binder.getCallingUid();
            int appId = android.os.UserHandle.getAppId(num.intValue());
            int length = iArr.length;
            java.lang.String[] strArr = null;
            for (int i = 0; strArr == null && i < length; i++) {
                strArr = (java.lang.String[]) quadFunction.apply(java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(iArr[i]), java.lang.Integer.valueOf(appId), false);
            }
            if (strArr == null) {
                str = "[app id " + num + " not installed]";
            } else {
                str = strArr.length == 1 ? strArr[0] : "[" + android.text.TextUtils.join(",", strArr) + "]";
            }
            sparseArray.put(num.intValue(), str);
        }
        return str;
    }

    protected void dumpForceQueryable(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queries via forceQueryable:");
        dumpPackageSet(printWriter, num, this.mForceQueryable.untrackedStorage(), "forceQueryable", "  ", toString);
    }

    protected void dumpQueriesViaPackage(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queries via package name:");
        dumpQueriesMap(printWriter, num, this.mQueriesViaPackage, "    ", toString);
    }

    protected void dumpQueriesViaComponent(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queries via component:");
        dumpQueriesMap(printWriter, num, this.mQueriesViaComponent, "    ", toString);
    }

    protected void dumpQueriesViaImplicitlyQueryable(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, int[] iArr, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queryable via interaction:");
        for (int i : iArr) {
            printWriter.append("    User ").append((java.lang.CharSequence) java.lang.Integer.toString(i)).println(":");
            java.lang.Integer num2 = null;
            dumpQueriesMap(printWriter, num == null ? null : java.lang.Integer.valueOf(android.os.UserHandle.getUid(i, num.intValue())), this.mImplicitlyQueryable, "      ", toString);
            if (num != null) {
                num2 = java.lang.Integer.valueOf(android.os.UserHandle.getUid(i, num.intValue()));
            }
            dumpQueriesMap(printWriter, num2, this.mRetainedImplicitlyQueryable, "      ", toString);
        }
    }

    protected void dumpQueriesViaUsesLibrary(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queryable via uses-library:");
        dumpQueriesMap(printWriter, num, this.mQueryableViaUsesLibrary, "    ", toString);
    }

    protected void dumpQueriesViaUsesPermission(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        printWriter.println("  queryable via uses-permission:");
        dumpQueriesMap(printWriter, num, this.mQueryableViaUsesPermission, "    ", toString);
    }

    private static void dumpQueriesMap(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.utils.WatchedSparseSetArray<java.lang.Integer> watchedSparseSetArray, java.lang.String str, @android.annotation.Nullable com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        java.lang.String toString2;
        java.lang.String toString3;
        for (int i = 0; i < watchedSparseSetArray.size(); i++) {
            java.lang.Integer valueOf = java.lang.Integer.valueOf(watchedSparseSetArray.keyAt(i));
            if (java.util.Objects.equals(valueOf, num)) {
                android.util.ArraySet<java.lang.Integer> arraySet = watchedSparseSetArray.get(valueOf.intValue());
                if (toString == null) {
                    toString3 = valueOf.toString();
                } else {
                    toString3 = toString.toString(valueOf);
                }
                dumpPackageSet(printWriter, null, arraySet, toString3, str, toString);
            } else {
                android.util.ArraySet<java.lang.Integer> arraySet2 = watchedSparseSetArray.get(valueOf.intValue());
                if (toString == null) {
                    toString2 = valueOf.toString();
                } else {
                    toString2 = toString.toString(valueOf);
                }
                dumpPackageSet(printWriter, num, arraySet2, toString2, str, toString);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void dumpPackageSet(java.io.PrintWriter printWriter, @android.annotation.Nullable T t, android.util.ArraySet<T> arraySet, java.lang.String str, java.lang.String str2, @android.annotation.Nullable com.android.server.pm.AppsFilterBase.ToString<T> toString) {
        if (arraySet != null && arraySet.size() > 0) {
            if (t == null || arraySet.contains(t)) {
                printWriter.append((java.lang.CharSequence) str2).append((java.lang.CharSequence) str).println(":");
                java.util.Iterator<T> it = arraySet.iterator();
                while (it.hasNext()) {
                    java.lang.Object next = it.next();
                    if (t == null || java.util.Objects.equals(t, next)) {
                        java.io.PrintWriter append = printWriter.append((java.lang.CharSequence) str2).append("  ");
                        if (toString != 0) {
                            next = toString.toString(next);
                        }
                        append.println(next);
                    }
                }
            }
        }
    }
}
