package com.android.server.pm.resolution;

/* loaded from: classes2.dex */
public class ComponentResolver extends com.android.server.pm.resolution.ComponentResolverLocked implements com.android.server.utils.Snappable<com.android.server.pm.resolution.ComponentResolverApi> {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_FILTERS = false;
    private static final boolean DEBUG_SHOW_INFO = false;
    private static final java.util.Set<java.lang.String> PROTECTED_ACTIONS = new android.util.ArraySet();
    public static final java.util.Comparator<android.content.pm.ResolveInfo> RESOLVE_PRIORITY_SORTER;
    private static final java.lang.String TAG = "PackageManager";
    boolean mDeferProtectedFilters;
    java.util.List<android.util.Pair<com.android.internal.pm.pkg.component.ParsedMainComponent, com.android.internal.pm.pkg.component.ParsedIntentInfo>> mProtectedFilters;
    final com.android.server.utils.SnapshotCache<com.android.server.pm.resolution.ComponentResolverApi> mSnapshot;

    private void onChanged() {
        dispatchChange(this);
    }

    static {
        PROTECTED_ACTIONS.add("android.intent.action.SEND");
        PROTECTED_ACTIONS.add("android.intent.action.SENDTO");
        PROTECTED_ACTIONS.add("android.intent.action.SEND_MULTIPLE");
        PROTECTED_ACTIONS.add("android.intent.action.VIEW");
        RESOLVE_PRIORITY_SORTER = new java.util.Comparator() { // from class: com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$static$0;
                lambda$static$0 = com.android.server.pm.resolution.ComponentResolver.lambda$static$0((android.content.pm.ResolveInfo) obj, (android.content.pm.ResolveInfo) obj2);
                return lambda$static$0;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
        int i = resolveInfo.priority;
        int i2 = resolveInfo2.priority;
        if (i != i2) {
            return i > i2 ? -1 : 1;
        }
        int i3 = resolveInfo.preferredOrder;
        int i4 = resolveInfo2.preferredOrder;
        if (i3 != i4) {
            return i3 > i4 ? -1 : 1;
        }
        if (resolveInfo.isDefault != resolveInfo2.isDefault) {
            return resolveInfo.isDefault ? -1 : 1;
        }
        int i5 = resolveInfo.match;
        int i6 = resolveInfo2.match;
        if (i5 != i6) {
            return i5 > i6 ? -1 : 1;
        }
        if (resolveInfo.system != resolveInfo2.system) {
            return resolveInfo.system ? -1 : 1;
        }
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo.packageName.compareTo(resolveInfo2.activityInfo.packageName);
        }
        if (resolveInfo.serviceInfo != null) {
            return resolveInfo.serviceInfo.packageName.compareTo(resolveInfo2.serviceInfo.packageName);
        }
        if (resolveInfo.providerInfo != null) {
            return resolveInfo.providerInfo.packageName.compareTo(resolveInfo2.providerInfo.packageName);
        }
        return 0;
    }

    public ComponentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull final com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
        super(userManagerService);
        this.mDeferProtectedFilters = true;
        this.mActivities = new com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver(userManagerService, userNeedsBadgingCache);
        this.mProviders = new com.android.server.pm.resolution.ComponentResolver.ProviderIntentResolver(userManagerService);
        this.mReceivers = new com.android.server.pm.resolution.ComponentResolver.ReceiverIntentResolver(userManagerService, userNeedsBadgingCache);
        this.mServices = new com.android.server.pm.resolution.ComponentResolver.ServiceIntentResolver(userManagerService);
        this.mProvidersByAuthority = new android.util.ArrayMap<>();
        this.mDeferProtectedFilters = true;
        this.mSnapshot = new com.android.server.utils.SnapshotCache<com.android.server.pm.resolution.ComponentResolverApi>(this, this) { // from class: com.android.server.pm.resolution.ComponentResolver.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.resolution.ComponentResolverApi createSnapshot() {
                com.android.server.pm.resolution.ComponentResolverSnapshot componentResolverSnapshot;
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.resolution.ComponentResolver.this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        componentResolverSnapshot = new com.android.server.pm.resolution.ComponentResolverSnapshot(com.android.server.pm.resolution.ComponentResolver.this, userNeedsBadgingCache);
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return componentResolverSnapshot;
            }
        };
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.resolution.ComponentResolverApi snapshot() {
        return this.mSnapshot.snapshot();
    }

    public void addAllComponents(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                addActivitiesLocked(computer, androidPackage, arrayList, z);
                addReceiversLocked(computer, androidPackage, z);
                addProvidersLocked(computer, androidPackage, z);
                addServicesLocked(computer, androidPackage, z);
                onChanged();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            android.util.Pair pair = (android.util.Pair) arrayList.get(size);
            com.android.server.pm.pkg.PackageStateInternal disabledSystemPackage = computer.getDisabledSystemPackage(((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getPackageName());
            java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list = null;
            com.android.server.pm.pkg.AndroidPackage pkg = disabledSystemPackage == null ? null : disabledSystemPackage.getPkg();
            if (pkg != null) {
                list = pkg.getActivities();
            }
            adjustPriority(computer, list, (com.android.internal.pm.pkg.component.ParsedActivity) pair.first, (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second, str);
            onChanged();
        }
    }

    public void removeAllComponents(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                removeAllComponentsLocked(androidPackage, z);
                onChanged();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void fixProtectedFilterPriorities(@android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (!this.mDeferProtectedFilters) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                this.mDeferProtectedFilters = false;
                if (this.mProtectedFilters == null || this.mProtectedFilters.size() == 0) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                java.util.List<android.util.Pair<com.android.internal.pm.pkg.component.ParsedMainComponent, com.android.internal.pm.pkg.component.ParsedIntentInfo>> list = this.mProtectedFilters;
                this.mProtectedFilters = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    android.util.Pair<com.android.internal.pm.pkg.component.ParsedMainComponent, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair = list.get(size);
                    com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent = (com.android.internal.pm.pkg.component.ParsedMainComponent) pair.first;
                    android.content.IntentFilter intentFilter = ((com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second).getIntentFilter();
                    java.lang.String packageName = parsedMainComponent.getPackageName();
                    parsedMainComponent.getClassName();
                    if (!packageName.equals(str)) {
                        intentFilter.setPriority(0);
                    }
                }
                onChanged();
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addActivitiesLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.List<android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>> list, boolean z) {
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getActivities());
        for (int i = 0; i < size; i++) {
            this.mActivities.addActivity(computer, (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getActivities().get(i), com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY, list);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addProvidersLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        android.content.ComponentName componentName;
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getProviders());
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(i);
            this.mProviders.addProvider(computer, parsedProvider);
            if (parsedProvider.getAuthority() != null) {
                java.lang.String[] split = parsedProvider.getAuthority().split(";");
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setAuthority(parsedProvider, (java.lang.String) null);
                for (int i2 = 0; i2 < split.length; i2++) {
                    if (i2 == 1 && parsedProvider.isSyncable()) {
                        com.android.internal.pm.pkg.component.ParsedProvider parsedProviderImpl = new com.android.internal.pm.pkg.component.ParsedProviderImpl(parsedProvider);
                        com.android.internal.pm.pkg.component.ComponentMutateUtils.setSyncable(parsedProviderImpl, false);
                        parsedProvider = parsedProviderImpl;
                    }
                    if (!this.mProvidersByAuthority.containsKey(split[i2])) {
                        this.mProvidersByAuthority.put(split[i2], parsedProvider);
                        if (parsedProvider.getAuthority() == null) {
                            com.android.internal.pm.pkg.component.ComponentMutateUtils.setAuthority(parsedProvider, split[i2]);
                        } else {
                            com.android.internal.pm.pkg.component.ComponentMutateUtils.setAuthority(parsedProvider, parsedProvider.getAuthority() + ";" + split[i2]);
                        }
                    } else {
                        com.android.internal.pm.pkg.component.ParsedProvider parsedProvider2 = this.mProvidersByAuthority.get(split[i2]);
                        if (parsedProvider2 == null || parsedProvider2.getComponentName() == null) {
                            componentName = null;
                        } else {
                            componentName = parsedProvider2.getComponentName();
                        }
                        android.util.Slog.w(TAG, "Skipping provider name " + split[i2] + " (in package " + androidPackage.getPackageName() + "): name already used by " + (componentName != null ? componentName.getPackageName() : "?"));
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addReceiversLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getReceivers());
        for (int i = 0; i < size; i++) {
            this.mReceivers.addActivity(computer, (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getReceivers().get(i), "receiver", null);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addServicesLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getServices());
        for (int i = 0; i < size; i++) {
            this.mServices.addService(computer, (com.android.internal.pm.pkg.component.ParsedService) androidPackage.getServices().get(i));
        }
    }

    private static <T> void getIntentListSubset(java.util.List<com.android.internal.pm.pkg.component.ParsedIntentInfo> list, java.util.function.Function<android.content.IntentFilter, java.util.Iterator<T>> function, java.util.Iterator<T> it) {
        boolean z;
        while (it.hasNext() && list.size() != 0) {
            T next = it.next();
            java.util.Iterator<com.android.internal.pm.pkg.component.ParsedIntentInfo> it2 = list.iterator();
            while (it2.hasNext()) {
                java.util.Iterator<T> apply = function.apply(it2.next().getIntentFilter());
                while (apply != null && apply.hasNext()) {
                    T next2 = apply.next();
                    if (next2 != null && next2.equals(next)) {
                        z = true;
                        break;
                    }
                }
                z = false;
                if (!z) {
                    it2.remove();
                }
            }
        }
    }

    private static boolean isProtectedAction(android.content.IntentFilter intentFilter) {
        java.util.Iterator<java.lang.String> actionsIterator = intentFilter.actionsIterator();
        while (actionsIterator != null && actionsIterator.hasNext()) {
            if (PROTECTED_ACTIONS.contains(actionsIterator.next())) {
                return true;
            }
        }
        return false;
    }

    private static com.android.internal.pm.pkg.component.ParsedActivity findMatchingActivity(java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity) {
        for (com.android.internal.pm.pkg.component.ParsedActivity parsedActivity2 : list) {
            if (parsedActivity2.getName().equals(parsedActivity.getName())) {
                return parsedActivity2;
            }
            if (parsedActivity2.getName().equals(parsedActivity.getTargetActivity())) {
                return parsedActivity2;
            }
            if (parsedActivity2.getTargetActivity() != null) {
                if (parsedActivity2.getTargetActivity().equals(parsedActivity.getName())) {
                    return parsedActivity2;
                }
                if (parsedActivity2.getTargetActivity().equals(parsedActivity.getTargetActivity())) {
                    return parsedActivity2;
                }
            }
        }
        return null;
    }

    private void adjustPriority(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo, java.lang.String str) {
        android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        if (intentFilter.getPriority() <= 0) {
            return;
        }
        java.lang.String packageName = parsedActivity.getPackageName();
        boolean isPrivileged = computer.getPackageStateInternal(packageName).isPrivileged();
        parsedActivity.getClassName();
        int i = 0;
        if (!isPrivileged) {
            intentFilter.setPriority(0);
            return;
        }
        if (isProtectedAction(intentFilter)) {
            if (this.mDeferProtectedFilters) {
                if (this.mProtectedFilters == null) {
                    this.mProtectedFilters = new java.util.ArrayList();
                }
                this.mProtectedFilters.add(android.util.Pair.create(parsedActivity, parsedIntentInfo));
                return;
            } else {
                if (packageName.equals(str)) {
                    return;
                }
                intentFilter.setPriority(0);
                return;
            }
        }
        if (list == null) {
            return;
        }
        com.android.internal.pm.pkg.component.ParsedActivity findMatchingActivity = findMatchingActivity(list, parsedActivity);
        if (findMatchingActivity == null) {
            intentFilter.setPriority(0);
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(findMatchingActivity.getIntents());
        java.util.Iterator<java.lang.String> actionsIterator = intentFilter.actionsIterator();
        if (actionsIterator != null) {
            getIntentListSubset(arrayList, new java.util.function.Function() { // from class: com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.IntentFilter) obj).actionsIterator();
                }
            }, actionsIterator);
            if (arrayList.size() == 0) {
                intentFilter.setPriority(0);
                return;
            }
        }
        java.util.Iterator<java.lang.String> categoriesIterator = intentFilter.categoriesIterator();
        if (categoriesIterator != null) {
            getIntentListSubset(arrayList, new java.util.function.Function() { // from class: com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.IntentFilter) obj).categoriesIterator();
                }
            }, categoriesIterator);
            if (arrayList.size() == 0) {
                intentFilter.setPriority(0);
                return;
            }
        }
        java.util.Iterator<java.lang.String> schemesIterator = intentFilter.schemesIterator();
        if (schemesIterator != null) {
            getIntentListSubset(arrayList, new java.util.function.Function() { // from class: com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.IntentFilter) obj).schemesIterator();
                }
            }, schemesIterator);
            if (arrayList.size() == 0) {
                intentFilter.setPriority(0);
                return;
            }
        }
        java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator = intentFilter.authoritiesIterator();
        if (authoritiesIterator != null) {
            getIntentListSubset(arrayList, new java.util.function.Function() { // from class: com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.IntentFilter) obj).authoritiesIterator();
                }
            }, authoritiesIterator);
            if (arrayList.size() == 0) {
                intentFilter.setPriority(0);
                return;
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            i = java.lang.Math.max(i, ((com.android.internal.pm.pkg.component.ParsedIntentInfo) arrayList.get(size)).getIntentFilter().getPriority());
        }
        if (intentFilter.getPriority() > i) {
            intentFilter.setPriority(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeAllComponentsLocked(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getActivities());
        for (int i = 0; i < size; i++) {
            this.mActivities.removeActivity((com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getActivities().get(i), com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        }
        int size2 = com.android.internal.util.ArrayUtils.size(androidPackage.getProviders());
        for (int i2 = 0; i2 < size2; i2++) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(i2);
            this.mProviders.removeProvider(parsedProvider);
            if (parsedProvider.getAuthority() != null) {
                java.lang.String[] split = parsedProvider.getAuthority().split(";");
                for (int i3 = 0; i3 < split.length; i3++) {
                    if (this.mProvidersByAuthority.get(split[i3]) == parsedProvider) {
                        this.mProvidersByAuthority.remove(split[i3]);
                    }
                }
            }
        }
        int size3 = com.android.internal.util.ArrayUtils.size(androidPackage.getReceivers());
        for (int i4 = 0; i4 < size3; i4++) {
            this.mReceivers.removeActivity((com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getReceivers().get(i4), "receiver");
        }
        int size4 = com.android.internal.util.ArrayUtils.size(androidPackage.getServices());
        for (int i5 = 0; i5 < size4; i5++) {
            this.mServices.removeService((com.android.internal.pm.pkg.component.ParsedService) androidPackage.getServices().get(i5));
        }
    }

    public void assertProvidersNotDefined(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int size = com.android.internal.util.ArrayUtils.size(androidPackage.getProviders());
                for (int i = 0; i < size; i++) {
                    com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(i);
                    if (parsedProvider.getAuthority() != null) {
                        java.lang.String[] split = parsedProvider.getAuthority().split(";");
                        for (int i2 = 0; i2 < split.length; i2++) {
                            if (this.mProvidersByAuthority.containsKey(split[i2])) {
                                com.android.internal.pm.pkg.component.ParsedProvider parsedProvider2 = this.mProvidersByAuthority.get(split[i2]);
                                java.lang.String packageName = (parsedProvider2 == null || parsedProvider2.getComponentName() == null) ? "?" : parsedProvider2.getComponentName().getPackageName();
                                if (!packageName.equals(androidPackage.getPackageName())) {
                                    throw new com.android.server.pm.PackageManagerException(-13, "Can't install because provider name " + split[i2] + " (in package " + androidPackage.getPackageName() + ") is already used by " + packageName);
                                }
                            }
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

    private static abstract class MimeGroupsAwareIntentResolver<F extends android.util.Pair<? extends com.android.internal.pm.pkg.component.ParsedComponent, com.android.internal.pm.pkg.component.ParsedIntentInfo>, R> extends com.android.server.IntentResolver<F, R> {
        private boolean mIsUpdatingMimeGroup;
        private final android.util.ArrayMap<java.lang.String, F[]> mMimeGroupToFilter;

        @android.annotation.NonNull
        protected final com.android.server.pm.UserManagerService mUserManager;

        MimeGroupsAwareIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            this.mMimeGroupToFilter = new android.util.ArrayMap<>();
            this.mIsUpdatingMimeGroup = false;
            this.mUserManager = userManagerService;
        }

        MimeGroupsAwareIntentResolver(com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver<F, R> mimeGroupsAwareIntentResolver, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            this.mMimeGroupToFilter = new android.util.ArrayMap<>();
            this.mIsUpdatingMimeGroup = false;
            this.mUserManager = userManagerService;
            copyFrom(mimeGroupsAwareIntentResolver);
            copyInto(this.mMimeGroupToFilter, mimeGroupsAwareIntentResolver.mMimeGroupToFilter);
            this.mIsUpdatingMimeGroup = mimeGroupsAwareIntentResolver.mIsUpdatingMimeGroup;
        }

        @Override // com.android.server.IntentResolver
        public void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, F f) {
            android.content.IntentFilter intentFilter = getIntentFilter(f);
            applyMimeGroups((com.android.server.pm.Computer) packageDataSnapshot, f);
            super.addFilter(packageDataSnapshot, (com.android.server.pm.snapshot.PackageDataSnapshot) f);
            if (!this.mIsUpdatingMimeGroup) {
                register_intent_filter(f, intentFilter.mimeGroupsIterator(), this.mMimeGroupToFilter, "      MimeGroup: ");
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public void removeFilterInternal(F f) {
            android.content.IntentFilter intentFilter = getIntentFilter(f);
            if (!this.mIsUpdatingMimeGroup) {
                unregister_intent_filter(f, intentFilter.mimeGroupsIterator(), this.mMimeGroupToFilter, "      MimeGroup: ");
            }
            super.removeFilterInternal((com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver<F, R>) f);
            intentFilter.clearDynamicDataTypes();
        }

        public boolean updateMimeGroup(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
            F[] fArr = this.mMimeGroupToFilter.get(str2);
            int length = fArr != null ? fArr.length : 0;
            this.mIsUpdatingMimeGroup = true;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                F f = fArr[i];
                if (f == null) {
                    break;
                }
                if (isPackageForFilter(str, f)) {
                    z |= updateFilter(computer, f);
                }
            }
            this.mIsUpdatingMimeGroup = false;
            return z;
        }

        private boolean updateFilter(@android.annotation.NonNull com.android.server.pm.Computer computer, F f) {
            java.util.List dataTypes = getIntentFilter(f).dataTypes();
            removeFilter(f);
            addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) computer, (com.android.server.pm.Computer) f);
            return !equalLists(dataTypes, r0.dataTypes());
        }

        private boolean equalLists(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
            if (list == null) {
                return list2 == null;
            }
            if (list2 == null || list.size() != list2.size()) {
                return false;
            }
            java.util.Collections.sort(list);
            java.util.Collections.sort(list2);
            return list.equals(list2);
        }

        private void applyMimeGroups(@android.annotation.NonNull com.android.server.pm.Computer computer, F f) {
            java.util.Set<java.lang.String> set;
            android.content.IntentFilter intentFilter = getIntentFilter(f);
            for (int countMimeGroups = intentFilter.countMimeGroups() - 1; countMimeGroups >= 0; countMimeGroups--) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(((com.android.internal.pm.pkg.component.ParsedComponent) ((android.util.Pair) f).first).getPackageName());
                if (packageStateInternal == null) {
                    set = java.util.Collections.emptyList();
                } else {
                    set = packageStateInternal.getMimeGroups().get(intentFilter.getMimeGroup(countMimeGroups));
                }
                java.util.Iterator it = set.iterator();
                while (it.hasNext()) {
                    try {
                        intentFilter.addDynamicDataType((java.lang.String) it.next());
                    } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isFilterStopped(@android.annotation.NonNull com.android.server.pm.Computer computer, F f, int i) {
            if (!this.mUserManager.exists(i)) {
                return true;
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(((com.android.internal.pm.pkg.component.ParsedComponent) ((android.util.Pair) f).first).getPackageName());
            if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
                return false;
            }
            if (packageStateInternal.isSystem()) {
                return packageStateInternal.isScannedAsStoppedSystemApp() && packageStateInternal.getUserStateOrDefault(i).isStopped();
            }
            return packageStateInternal.getUserStateOrDefault(i).isStopped();
        }
    }

    public static class ActivityIntentResolver extends com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver<android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>, android.content.pm.ResolveInfo> {
        protected final android.util.ArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedActivity> mActivities;

        @android.annotation.NonNull
        private final com.android.server.pm.UserNeedsBadgingCache mUserNeedsBadging;

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            super.addFilter(packageDataSnapshot, (com.android.server.pm.snapshot.PackageDataSnapshot) pair);
        }

        @Override // com.android.server.IntentResolver
        protected /* bridge */ /* synthetic */ boolean allowFilterResult(java.lang.Object obj, java.util.List list) {
            return allowFilterResult((android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>) obj, (java.util.List<android.content.pm.ResolveInfo>) list);
        }

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ boolean updateMimeGroup(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
            return super.updateMimeGroup(computer, str, str2);
        }

        ActivityIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
            super(userManagerService);
            this.mActivities = new android.util.ArrayMap<>();
            this.mUserNeedsBadging = userNeedsBadgingCache;
        }

        ActivityIntentResolver(@android.annotation.NonNull com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver activityIntentResolver, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
            super(activityIntentResolver, userManagerService);
            this.mActivities = new android.util.ArrayMap<>();
            this.mActivities.putAll((android.util.ArrayMap<? extends android.content.ComponentName, ? extends com.android.internal.pm.pkg.component.ParsedActivity>) activityIntentResolver.mActivities);
            this.mUserNeedsBadging = userNeedsBadgingCache;
        }

        @Override // com.android.server.IntentResolver
        public java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.content.Intent intent, java.lang.String str, boolean z, int i) {
            if (this.mUserManager.exists(i)) {
                return super.queryIntent(packageDataSnapshot, intent, str, z, i, z ? 65536 : 0);
            }
            return null;
        }

        java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i) {
            if (this.mUserManager.exists(i)) {
                return super.queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
            }
            return null;
        }

        java.util.List<android.content.pm.ResolveInfo> queryIntentForPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i) {
            if (!this.mUserManager.exists(i)) {
                return null;
            }
            if (list == null) {
                return java.util.Collections.emptyList();
            }
            boolean z = (j & 65536) != 0;
            int size = list.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = list.get(i2);
                java.util.List intents = parsedActivity.getIntents();
                if (!intents.isEmpty()) {
                    android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray = newArray(intents.size());
                    for (int i3 = 0; i3 < intents.size(); i3++) {
                        newArray[i3] = android.util.Pair.create(parsedActivity, (com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i3));
                    }
                    arrayList.add(newArray);
                }
            }
            return super.queryIntentFromList(computer, intent, str, z, arrayList, i, j);
        }

        protected void addActivity(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, java.lang.String str, java.util.List<android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>> list) {
            this.mActivities.put(parsedActivity.getComponentName(), parsedActivity);
            int size = parsedActivity.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedActivity.getIntents().get(i);
                android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
                if (list != null && com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY.equals(str)) {
                    list.add(android.util.Pair.create(parsedActivity, parsedIntentInfo));
                }
                if (!intentFilter.debugCheck()) {
                    android.util.Log.w(com.android.server.pm.resolution.ComponentResolver.TAG, "==> For Activity " + parsedActivity.getName());
                }
                addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) computer, android.util.Pair.create(parsedActivity, parsedIntentInfo));
            }
        }

        protected void removeActivity(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, java.lang.String str) {
            this.mActivities.remove(parsedActivity.getComponentName());
            int size = parsedActivity.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedActivity.getIntents().get(i);
                parsedIntentInfo.getIntentFilter();
                removeFilter(android.util.Pair.create(parsedActivity, parsedIntentInfo));
            }
        }

        protected boolean allowFilterResult(android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, java.util.List<android.content.pm.ResolveInfo> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.content.pm.ActivityInfo activityInfo = list.get(size).activityInfo;
                if (java.util.Objects.equals(activityInfo.name, ((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getName()) && java.util.Objects.equals(activityInfo.packageName, ((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getPackageName())) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray(int i) {
            return new android.util.Pair[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isPackageForFilter(java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return str.equals(((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getPackageName());
        }

        private void log(java.lang.String str, com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo, int i, int i2) {
            android.util.Slog.w(com.android.server.pm.resolution.ComponentResolver.TAG, str + "; match: " + android.util.DebugUtils.flagsToString(android.content.IntentFilter.class, "MATCH_", i) + "; userId: " + i2 + "; intent info: " + parsedIntentInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.pm.ResolveInfo newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, int i, int i2, long j) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
            if (!this.mUserManager.exists(i2) || (packageStateInternal = computer.getPackageStateInternal(parsedActivity.getPackageName())) == null || packageStateInternal.getPkg() == null || !com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, (com.android.internal.pm.pkg.component.ParsedMainComponent) parsedActivity, j, i2)) {
                return null;
            }
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i2);
            android.content.pm.ActivityInfo generateActivityInfo = com.android.server.pm.parsing.PackageInfoUtils.generateActivityInfo(packageStateInternal.getPkg(), parsedActivity, j, userStateOrDefault, i2, packageStateInternal);
            if (generateActivityInfo == null) {
                return null;
            }
            boolean z = (33554432 & j) != 0;
            boolean z2 = (j & 16777216) != 0;
            boolean z3 = z2 && intentFilter.isVisibleToInstantApp() && (!z || intentFilter.isExplicitlyVisibleToInstantApp());
            boolean z4 = (j & 8388608) != 0;
            if (z2 && !z3 && !userStateOrDefault.isInstantApp()) {
                return null;
            }
            if (!z4 && userStateOrDefault.isInstantApp()) {
                return null;
            }
            if (userStateOrDefault.isInstantApp() && packageStateInternal.isUpdateAvailable()) {
                return null;
            }
            android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo(intentFilter.hasCategory("android.intent.category.BROWSABLE"));
            resolveInfo.activityInfo = generateActivityInfo;
            if ((j & 64) != 0) {
                resolveInfo.filter = intentFilter;
            }
            resolveInfo.handleAllWebDataURI = intentFilter.handleAllWebDataURI();
            resolveInfo.priority = intentFilter.getPriority();
            resolveInfo.match = i;
            resolveInfo.isDefault = parsedIntentInfo.isHasDefault();
            resolveInfo.labelRes = parsedIntentInfo.getLabelRes();
            resolveInfo.nonLocalizedLabel = parsedIntentInfo.getNonLocalizedLabel();
            if (this.mUserNeedsBadging.get(i2)) {
                resolveInfo.noResourceId = true;
            } else {
                resolveInfo.icon = parsedIntentInfo.getIcon();
            }
            resolveInfo.iconResourceId = parsedIntentInfo.getIcon();
            resolveInfo.system = resolveInfo.activityInfo.applicationInfo.isSystemApp();
            resolveInfo.isInstantAppAvailable = userStateOrDefault.isInstantApp();
            resolveInfo.userHandle = android.os.UserHandle.of(i2);
            return resolveInfo;
        }

        @Override // com.android.server.IntentResolver
        protected void sortResults(java.util.List<android.content.pm.ResolveInfo> list) {
            list.sort(com.android.server.pm.resolution.ComponentResolver.RESOLVE_PRIORITY_SORTER);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public void dumpFilter(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedActivity)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, parsedActivity.getPackageName(), parsedActivity.getClassName());
            printWriter.print(" filter ");
            printWriter.println(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedIntentInfo)));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public java.lang.Object filterToLabel(android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return pair;
        }

        @Override // com.android.server.IntentResolver
        protected void dumpFilterLabel(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object obj, int i) {
            android.util.Pair pair = (android.util.Pair) obj;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(pair.first)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, ((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getPackageName(), ((com.android.internal.pm.pkg.component.ParsedActivity) pair.first).getClassName());
            if (i > 1) {
                printWriter.print(" (");
                printWriter.print(i);
                printWriter.print(" filters)");
            }
            printWriter.println();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull android.util.Pair<com.android.internal.pm.pkg.component.ParsedActivity, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return ((com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second).getIntentFilter();
        }

        protected java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getResolveList(com.android.server.pm.pkg.AndroidPackage androidPackage) {
            return androidPackage.getActivities();
        }
    }

    public static final class ReceiverIntentResolver extends com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver {
        ReceiverIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
            super(userManagerService, userNeedsBadgingCache);
        }

        ReceiverIntentResolver(@android.annotation.NonNull com.android.server.pm.resolution.ComponentResolver.ReceiverIntentResolver receiverIntentResolver, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
            super(receiverIntentResolver, userManagerService, userNeedsBadgingCache);
        }

        @Override // com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver
        protected java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getResolveList(com.android.server.pm.pkg.AndroidPackage androidPackage) {
            return androidPackage.getReceivers();
        }
    }

    public static final class ProviderIntentResolver extends com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver<android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo>, android.content.pm.ResolveInfo> {
        final android.util.ArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedProvider> mProviders;

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            super.addFilter(packageDataSnapshot, (com.android.server.pm.snapshot.PackageDataSnapshot) pair);
        }

        @Override // com.android.server.IntentResolver
        protected /* bridge */ /* synthetic */ boolean allowFilterResult(java.lang.Object obj, java.util.List list) {
            return allowFilterResult((android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo>) obj, (java.util.List<android.content.pm.ResolveInfo>) list);
        }

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ boolean updateMimeGroup(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
            return super.updateMimeGroup(computer, str, str2);
        }

        ProviderIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            super(userManagerService);
            this.mProviders = new android.util.ArrayMap<>();
        }

        ProviderIntentResolver(@android.annotation.NonNull com.android.server.pm.resolution.ComponentResolver.ProviderIntentResolver providerIntentResolver, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            super(providerIntentResolver, userManagerService);
            this.mProviders = new android.util.ArrayMap<>();
            this.mProviders.putAll((android.util.ArrayMap<? extends android.content.ComponentName, ? extends com.android.internal.pm.pkg.component.ParsedProvider>) providerIntentResolver.mProviders);
        }

        @Override // com.android.server.IntentResolver
        public java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.content.Intent intent, java.lang.String str, boolean z, int i) {
            if (!this.mUserManager.exists(i)) {
                return null;
            }
            return super.queryIntent(packageDataSnapshot, intent, str, z, i, z ? 65536L : 0L);
        }

        @android.annotation.Nullable
        java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i) {
            if (this.mUserManager.exists(i)) {
                return super.queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
            }
            return null;
        }

        @android.annotation.Nullable
        java.util.List<android.content.pm.ResolveInfo> queryIntentForPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> list, int i) {
            if (!this.mUserManager.exists(i)) {
                return null;
            }
            if (list == null) {
                return java.util.Collections.emptyList();
            }
            boolean z = (j & 65536) != 0;
            int size = list.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = list.get(i2);
                java.util.List intents = parsedProvider.getIntents();
                if (!intents.isEmpty()) {
                    android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray = newArray(intents.size());
                    for (int i3 = 0; i3 < intents.size(); i3++) {
                        newArray[i3] = android.util.Pair.create(parsedProvider, (com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i3));
                    }
                    arrayList.add(newArray);
                }
            }
            return super.queryIntentFromList(computer, intent, str, z, arrayList, i, j);
        }

        void addProvider(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.internal.pm.pkg.component.ParsedProvider parsedProvider) {
            if (this.mProviders.containsKey(parsedProvider.getComponentName())) {
                android.util.Slog.w(com.android.server.pm.resolution.ComponentResolver.TAG, "Provider " + parsedProvider.getComponentName() + " already defined; ignoring");
                return;
            }
            this.mProviders.put(parsedProvider.getComponentName(), parsedProvider);
            int size = parsedProvider.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedProvider.getIntents().get(i);
                if (!parsedIntentInfo.getIntentFilter().debugCheck()) {
                    android.util.Log.w(com.android.server.pm.resolution.ComponentResolver.TAG, "==> For Provider " + parsedProvider.getName());
                }
                addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) computer, android.util.Pair.create(parsedProvider, parsedIntentInfo));
            }
        }

        void removeProvider(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider) {
            this.mProviders.remove(parsedProvider.getComponentName());
            int size = parsedProvider.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedProvider.getIntents().get(i);
                parsedIntentInfo.getIntentFilter();
                removeFilter(android.util.Pair.create(parsedProvider, parsedIntentInfo));
            }
        }

        protected boolean allowFilterResult(android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, java.util.List<android.content.pm.ResolveInfo> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.content.pm.ProviderInfo providerInfo = list.get(size).providerInfo;
                if (java.util.Objects.equals(providerInfo.name, ((com.android.internal.pm.pkg.component.ParsedProvider) pair.first).getClassName()) && java.util.Objects.equals(providerInfo.packageName, ((com.android.internal.pm.pkg.component.ParsedProvider) pair.first).getPackageName())) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray(int i) {
            return new android.util.Pair[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isPackageForFilter(java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return str.equals(((com.android.internal.pm.pkg.component.ParsedProvider) pair.first).getPackageName());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.pm.ResolveInfo newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, int i, int i2, long j) {
            android.content.pm.ApplicationInfo generateApplicationInfo;
            android.content.pm.ProviderInfo generateProviderInfo;
            if (!this.mUserManager.exists(i2)) {
                return null;
            }
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(parsedProvider.getPackageName());
            if (packageStateInternal == null || packageStateInternal.getPkg() == null || !com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, (com.android.internal.pm.pkg.component.ParsedMainComponent) parsedProvider, j, i2)) {
                return null;
            }
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i2);
            boolean z = (16777216 & j) != 0;
            boolean z2 = (8388608 & j) != 0;
            if (z && !intentFilter.isVisibleToInstantApp() && !userStateOrDefault.isInstantApp()) {
                return null;
            }
            if (!z2 && userStateOrDefault.isInstantApp()) {
                return null;
            }
            if ((userStateOrDefault.isInstantApp() && packageStateInternal.isUpdateAvailable()) || (generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j, userStateOrDefault, i2, packageStateInternal)) == null || (generateProviderInfo = com.android.server.pm.parsing.PackageInfoUtils.generateProviderInfo(packageStateInternal.getPkg(), parsedProvider, j, userStateOrDefault, generateApplicationInfo, i2, packageStateInternal)) == null) {
                return null;
            }
            android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
            resolveInfo.providerInfo = generateProviderInfo;
            if ((64 & j) != 0) {
                resolveInfo.filter = intentFilter;
            }
            resolveInfo.priority = intentFilter.getPriority();
            resolveInfo.match = i;
            resolveInfo.isDefault = parsedIntentInfo.isHasDefault();
            resolveInfo.labelRes = parsedIntentInfo.getLabelRes();
            resolveInfo.nonLocalizedLabel = parsedIntentInfo.getNonLocalizedLabel();
            resolveInfo.icon = parsedIntentInfo.getIcon();
            resolveInfo.system = resolveInfo.providerInfo.applicationInfo.isSystemApp();
            return resolveInfo;
        }

        @Override // com.android.server.IntentResolver
        protected void sortResults(java.util.List<android.content.pm.ResolveInfo> list) {
            list.sort(com.android.server.pm.resolution.ComponentResolver.RESOLVE_PRIORITY_SORTER);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public void dumpFilter(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedProvider)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, parsedProvider.getPackageName(), parsedProvider.getClassName());
            printWriter.print(" filter ");
            printWriter.println(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedIntentInfo)));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public java.lang.Object filterToLabel(android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return pair;
        }

        @Override // com.android.server.IntentResolver
        protected void dumpFilterLabel(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object obj, int i) {
            android.util.Pair pair = (android.util.Pair) obj;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(pair.first)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, ((com.android.internal.pm.pkg.component.ParsedProvider) pair.first).getPackageName(), ((com.android.internal.pm.pkg.component.ParsedProvider) pair.first).getClassName());
            if (i > 1) {
                printWriter.print(" (");
                printWriter.print(i);
                printWriter.print(" filters)");
            }
            printWriter.println();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull android.util.Pair<com.android.internal.pm.pkg.component.ParsedProvider, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return ((com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second).getIntentFilter();
        }
    }

    public static final class ServiceIntentResolver extends com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver<android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo>, android.content.pm.ResolveInfo> {
        final android.util.ArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedService> mServices;

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            super.addFilter(packageDataSnapshot, (com.android.server.pm.snapshot.PackageDataSnapshot) pair);
        }

        @Override // com.android.server.IntentResolver
        protected /* bridge */ /* synthetic */ boolean allowFilterResult(java.lang.Object obj, java.util.List list) {
            return allowFilterResult((android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo>) obj, (java.util.List<android.content.pm.ResolveInfo>) list);
        }

        @Override // com.android.server.pm.resolution.ComponentResolver.MimeGroupsAwareIntentResolver
        public /* bridge */ /* synthetic */ boolean updateMimeGroup(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
            return super.updateMimeGroup(computer, str, str2);
        }

        ServiceIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            super(userManagerService);
            this.mServices = new android.util.ArrayMap<>();
        }

        ServiceIntentResolver(@android.annotation.NonNull com.android.server.pm.resolution.ComponentResolver.ServiceIntentResolver serviceIntentResolver, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            super(serviceIntentResolver, userManagerService);
            this.mServices = new android.util.ArrayMap<>();
            this.mServices.putAll((android.util.ArrayMap<? extends android.content.ComponentName, ? extends com.android.internal.pm.pkg.component.ParsedService>) serviceIntentResolver.mServices);
        }

        @Override // com.android.server.IntentResolver
        public java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.content.Intent intent, java.lang.String str, boolean z, int i) {
            if (!this.mUserManager.exists(i)) {
                return null;
            }
            return super.queryIntent(packageDataSnapshot, intent, str, z, i, z ? 65536L : 0L);
        }

        java.util.List<android.content.pm.ResolveInfo> queryIntent(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i) {
            if (this.mUserManager.exists(i)) {
                return super.queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
            }
            return null;
        }

        java.util.List<android.content.pm.ResolveInfo> queryIntentForPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, java.util.List<com.android.internal.pm.pkg.component.ParsedService> list, int i) {
            if (!this.mUserManager.exists(i)) {
                return null;
            }
            if (list == null) {
                return java.util.Collections.emptyList();
            }
            boolean z = (j & 65536) != 0;
            int size = list.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.pm.pkg.component.ParsedService parsedService = list.get(i2);
                java.util.List intents = parsedService.getIntents();
                if (intents.size() > 0) {
                    android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray = newArray(intents.size());
                    for (int i3 = 0; i3 < intents.size(); i3++) {
                        newArray[i3] = android.util.Pair.create(parsedService, (com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i3));
                    }
                    arrayList.add(newArray);
                }
            }
            return super.queryIntentFromList(computer, intent, str, z, arrayList, i, j);
        }

        void addService(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.internal.pm.pkg.component.ParsedService parsedService) {
            this.mServices.put(parsedService.getComponentName(), parsedService);
            int size = parsedService.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedService.getIntents().get(i);
                if (!parsedIntentInfo.getIntentFilter().debugCheck()) {
                    android.util.Log.w(com.android.server.pm.resolution.ComponentResolver.TAG, "==> For Service " + parsedService.getName());
                }
                addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) computer, android.util.Pair.create(parsedService, parsedIntentInfo));
            }
        }

        void removeService(com.android.internal.pm.pkg.component.ParsedService parsedService) {
            this.mServices.remove(parsedService.getComponentName());
            int size = parsedService.getIntents().size();
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) parsedService.getIntents().get(i);
                parsedIntentInfo.getIntentFilter();
                removeFilter(android.util.Pair.create(parsedService, parsedIntentInfo));
            }
        }

        protected boolean allowFilterResult(android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, java.util.List<android.content.pm.ResolveInfo> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.content.pm.ServiceInfo serviceInfo = list.get(size).serviceInfo;
                if (java.util.Objects.equals(serviceInfo.name, ((com.android.internal.pm.pkg.component.ParsedService) pair.first).getClassName()) && java.util.Objects.equals(serviceInfo.packageName, ((com.android.internal.pm.pkg.component.ParsedService) pair.first).getPackageName())) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo>[] newArray(int i) {
            return new android.util.Pair[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isPackageForFilter(java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return str.equals(((com.android.internal.pm.pkg.component.ParsedService) pair.first).getPackageName());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.pm.ResolveInfo newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair, int i, int i2, long j) {
            if (!this.mUserManager.exists(i2)) {
                return null;
            }
            com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(parsedService.getPackageName());
            if (packageStateInternal == null || packageStateInternal.getPkg() == null || !com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, (com.android.internal.pm.pkg.component.ParsedMainComponent) parsedService, j, i2)) {
                return null;
            }
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i2);
            android.content.pm.ServiceInfo generateServiceInfo = com.android.server.pm.parsing.PackageInfoUtils.generateServiceInfo(packageStateInternal.getPkg(), parsedService, j, userStateOrDefault, i2, packageStateInternal);
            if (generateServiceInfo == null) {
                return null;
            }
            boolean z = (16777216 & j) != 0;
            boolean z2 = (8388608 & j) != 0;
            if (z && !intentFilter.isVisibleToInstantApp() && !userStateOrDefault.isInstantApp()) {
                return null;
            }
            if (!z2 && userStateOrDefault.isInstantApp()) {
                return null;
            }
            if (userStateOrDefault.isInstantApp() && packageStateInternal.isUpdateAvailable()) {
                return null;
            }
            android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
            resolveInfo.serviceInfo = generateServiceInfo;
            if ((64 & j) != 0) {
                resolveInfo.filter = intentFilter;
            }
            resolveInfo.priority = intentFilter.getPriority();
            resolveInfo.match = i;
            resolveInfo.isDefault = parsedIntentInfo.isHasDefault();
            resolveInfo.labelRes = parsedIntentInfo.getLabelRes();
            resolveInfo.nonLocalizedLabel = parsedIntentInfo.getNonLocalizedLabel();
            resolveInfo.icon = parsedIntentInfo.getIcon();
            resolveInfo.system = resolveInfo.serviceInfo.applicationInfo.isSystemApp();
            return resolveInfo;
        }

        @Override // com.android.server.IntentResolver
        protected void sortResults(java.util.List<android.content.pm.ResolveInfo> list) {
            list.sort(com.android.server.pm.resolution.ComponentResolver.RESOLVE_PRIORITY_SORTER);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public void dumpFilter(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) pair.first;
            com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo = (com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedService)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, parsedService.getPackageName(), parsedService.getClassName());
            printWriter.print(" filter ");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(parsedIntentInfo)));
            if (parsedService.getPermission() != null) {
                printWriter.print(" permission ");
                printWriter.println(parsedService.getPermission());
            } else {
                printWriter.println();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public java.lang.Object filterToLabel(android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return pair;
        }

        @Override // com.android.server.IntentResolver
        protected void dumpFilterLabel(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object obj, int i) {
            android.util.Pair pair = (android.util.Pair) obj;
            printWriter.print(str);
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(pair.first)));
            printWriter.print(' ');
            android.content.ComponentName.printShortString(printWriter, ((com.android.internal.pm.pkg.component.ParsedService) pair.first).getPackageName(), ((com.android.internal.pm.pkg.component.ParsedService) pair.first).getClassName());
            if (i > 1) {
                printWriter.print(" (");
                printWriter.print(i);
                printWriter.print(" filters)");
            }
            printWriter.println();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull android.util.Pair<com.android.internal.pm.pkg.component.ParsedService, com.android.internal.pm.pkg.component.ParsedIntentInfo> pair) {
            return ((com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second).getIntentFilter();
        }
    }

    public static final class InstantAppIntentResolver extends com.android.server.IntentResolver<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter, android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> {
        final android.util.ArrayMap<java.lang.String, android.util.Pair<java.lang.Integer, android.content.pm.InstantAppResolveInfo>> mOrderResult = new android.util.ArrayMap<>();

        @android.annotation.NonNull
        private final com.android.server.pm.UserManagerService mUserManager;

        public InstantAppIntentResolver(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
            this.mUserManager = userManagerService;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.IntentResolver
        public android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter[] newArray(int i) {
            return new android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isPackageForFilter(java.lang.String str, android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter, int i, int i2, long j) {
            if (!this.mUserManager.exists(i2)) {
                return null;
            }
            java.lang.String packageName = auxiliaryFilter.resolveInfo.getPackageName();
            java.lang.Integer valueOf = java.lang.Integer.valueOf(auxiliaryFilter.getOrder());
            android.util.Pair<java.lang.Integer, android.content.pm.InstantAppResolveInfo> pair = this.mOrderResult.get(packageName);
            if (pair != null && ((java.lang.Integer) pair.first).intValue() >= valueOf.intValue()) {
                return null;
            }
            android.content.pm.InstantAppResolveInfo instantAppResolveInfo = auxiliaryFilter.resolveInfo;
            if (valueOf.intValue() > 0) {
                this.mOrderResult.put(packageName, new android.util.Pair<>(valueOf, instantAppResolveInfo));
            }
            return auxiliaryFilter;
        }

        @Override // com.android.server.IntentResolver
        protected void filterResults(java.util.List<android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter> list) {
            if (this.mOrderResult.size() == 0) {
                return;
            }
            int size = list.size();
            int i = 0;
            while (i < size) {
                android.content.pm.InstantAppResolveInfo instantAppResolveInfo = list.get(i).resolveInfo;
                java.lang.String packageName = instantAppResolveInfo.getPackageName();
                android.util.Pair<java.lang.Integer, android.content.pm.InstantAppResolveInfo> pair = this.mOrderResult.get(packageName);
                if (pair != null) {
                    if (pair.second == instantAppResolveInfo) {
                        this.mOrderResult.remove(packageName);
                        if (this.mOrderResult.size() == 0) {
                            return;
                        }
                    } else {
                        list.remove(i);
                        size--;
                        i--;
                    }
                }
                i++;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull android.content.pm.AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter) {
            return auxiliaryFilter;
        }
    }

    public boolean updateMimeGroup(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
        boolean updateMimeGroup;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                updateMimeGroup = this.mServices.updateMimeGroup(computer, str, str2) | this.mActivities.updateMimeGroup(computer, str, str2) | false | this.mProviders.updateMimeGroup(computer, str, str2) | this.mReceivers.updateMimeGroup(computer, str, str2);
                if (updateMimeGroup) {
                    onChanged();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return updateMimeGroup;
    }
}
