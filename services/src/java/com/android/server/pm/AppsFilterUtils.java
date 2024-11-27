package com.android.server.pm;

/* loaded from: classes2.dex */
final class AppsFilterUtils {
    AppsFilterUtils() {
    }

    public static boolean requestsQueryAllPackages(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return androidPackage.getRequestedPermissions().contains("android.permission.QUERY_ALL_PACKAGES");
    }

    public static boolean canQueryViaComponents(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.AndroidPackage androidPackage2, com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        if (!androidPackage.getQueriesIntents().isEmpty()) {
            java.util.Iterator it = androidPackage.getQueriesIntents().iterator();
            while (it.hasNext()) {
                if (matchesPackage((android.content.Intent) it.next(), androidPackage2, watchedArraySet)) {
                    return true;
                }
            }
        }
        return !androidPackage.getQueriesProviders().isEmpty() && matchesProviders(androidPackage.getQueriesProviders(), androidPackage2);
    }

    public static boolean canQueryViaPackage(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        return !androidPackage.getQueriesPackages().isEmpty() && androidPackage.getQueriesPackages().contains(androidPackage2.getPackageName());
    }

    public static boolean canQueryAsInstaller(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.InstallSource installSource = packageStateInternal.getInstallSource();
        if (androidPackage.getPackageName().equals(installSource.mInstallerPackageName)) {
            return true;
        }
        return !installSource.mIsInitiatingPackageUninstalled && androidPackage.getPackageName().equals(installSource.mInitiatingPackageName);
    }

    public static boolean canQueryAsUpdateOwner(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage.getPackageName().equals(packageStateInternal.getInstallSource().mUpdateOwnerPackageName)) {
            return true;
        }
        return false;
    }

    public static boolean canQueryViaUsesLibrary(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        if (androidPackage2.getLibraryNames().isEmpty()) {
            return false;
        }
        java.util.List libraryNames = androidPackage2.getLibraryNames();
        int size = libraryNames.size();
        for (int i = 0; i < size; i++) {
            java.lang.String str = (java.lang.String) libraryNames.get(i);
            if (androidPackage.getUsesLibraries().contains(str) || androidPackage.getUsesOptionalLibraries().contains(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesProviders(java.util.Set<java.lang.String> set, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        for (int size = com.android.internal.util.ArrayUtils.size(androidPackage.getProviders()) - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(size);
            if (parsedProvider.isExported() && parsedProvider.getAuthority() != null) {
                java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(parsedProvider.getAuthority(), ";", false);
                while (stringTokenizer.hasMoreElements()) {
                    if (set.contains(stringTokenizer.nextToken())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean matchesPackage(android.content.Intent intent, com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        return matchesAnyComponents(intent, androidPackage.getServices(), null) || matchesAnyComponents(intent, androidPackage.getActivities(), null) || matchesAnyComponents(intent, androidPackage.getReceivers(), watchedArraySet) || matchesAnyComponents(intent, androidPackage.getProviders(), null);
    }

    private static boolean matchesAnyComponents(android.content.Intent intent, java.util.List<? extends com.android.internal.pm.pkg.component.ParsedMainComponent> list, com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        for (int size = com.android.internal.util.ArrayUtils.size(list) - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent = list.get(size);
            if (parsedMainComponent.isExported() && matchesAnyFilter(intent, parsedMainComponent, watchedArraySet)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesAnyFilter(android.content.Intent intent, com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        java.util.List intents = parsedComponent.getIntents();
        for (int size = com.android.internal.util.ArrayUtils.size(intents) - 1; size >= 0; size--) {
            if (matchesIntentFilter(intent, ((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(size)).getIntentFilter(), watchedArraySet)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesIntentFilter(android.content.Intent intent, android.content.IntentFilter intentFilter, @android.annotation.Nullable com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        return intentFilter.match(intent.getAction(), intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), "AppsFilter", true, watchedArraySet != null ? watchedArraySet.untrackedStorage() : null) > 0;
    }

    public static final class ParallelComputeComponentVisibility {
        private static final int MAX_THREADS = 4;
        private final android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> mExistingSettings;
        private final android.util.ArraySet<java.lang.Integer> mForceQueryable;
        private final com.android.server.utils.WatchedArraySet<java.lang.String> mProtectedBroadcasts;

        ParallelComputeComponentVisibility(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, @android.annotation.NonNull android.util.ArraySet<java.lang.Integer> arraySet, @android.annotation.NonNull com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
            this.mExistingSettings = arrayMap;
            this.mForceQueryable = arraySet;
            this.mProtectedBroadcasts = watchedArraySet;
        }

        @android.annotation.NonNull
        android.util.SparseSetArray<java.lang.Integer> execute() {
            android.util.SparseSetArray<java.lang.Integer> sparseSetArray = new android.util.SparseSetArray<>();
            java.util.concurrent.ExecutorService newFixedThreadPool = com.android.internal.util.ConcurrentUtils.newFixedThreadPool(4, com.android.server.pm.AppsFilterUtils.ParallelComputeComponentVisibility.class.getSimpleName(), 0);
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int size = this.mExistingSettings.size() - 1; size >= 0; size--) {
                    final com.android.server.pm.pkg.PackageStateInternal valueAt = this.mExistingSettings.valueAt(size);
                    com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = valueAt.getPkg();
                    if (pkg != null && !com.android.server.pm.AppsFilterUtils.requestsQueryAllPackages(pkg) && (!pkg.getQueriesIntents().isEmpty() || !pkg.getQueriesProviders().isEmpty())) {
                        arrayList.add(new android.util.Pair(valueAt, newFixedThreadPool.submit(new java.util.concurrent.Callable() { // from class: com.android.server.pm.AppsFilterUtils$ParallelComputeComponentVisibility$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final java.lang.Object call() {
                                android.util.ArraySet lambda$execute$0;
                                lambda$execute$0 = com.android.server.pm.AppsFilterUtils.ParallelComputeComponentVisibility.this.lambda$execute$0(valueAt);
                                return lambda$execute$0;
                            }
                        })));
                    }
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    int appId = ((com.android.server.pm.pkg.PackageState) ((android.util.Pair) arrayList.get(i)).first).getAppId();
                    try {
                        android.util.ArraySet arraySet = (android.util.ArraySet) ((java.util.concurrent.Future) ((android.util.Pair) arrayList.get(i)).second).get();
                        if (arraySet.size() != 0) {
                            sparseSetArray.addAll(appId, arraySet);
                        }
                    } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                        throw new java.lang.IllegalStateException(e);
                    }
                }
                newFixedThreadPool.shutdownNow();
                return sparseSetArray;
            } catch (java.lang.Throwable th) {
                newFixedThreadPool.shutdownNow();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        /* renamed from: getVisibleListOfQueryViaComponents, reason: merged with bridge method [inline-methods] */
        public android.util.ArraySet<java.lang.Integer> lambda$execute$0(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            for (int size = this.mExistingSettings.size() - 1; size >= 0; size--) {
                com.android.server.pm.pkg.PackageStateInternal valueAt = this.mExistingSettings.valueAt(size);
                if (packageStateInternal.getAppId() != valueAt.getAppId() && valueAt.getPkg() != null && !this.mForceQueryable.contains(java.lang.Integer.valueOf(valueAt.getAppId())) && com.android.server.pm.AppsFilterUtils.canQueryViaComponents(packageStateInternal.getPkg(), valueAt.getPkg(), this.mProtectedBroadcasts)) {
                    arraySet.add(java.lang.Integer.valueOf(valueAt.getAppId()));
                }
            }
            return arraySet;
        }
    }
}
