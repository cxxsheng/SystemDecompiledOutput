package com.android.server.pm;

/* loaded from: classes2.dex */
public class CrossProfileIntentResolverEngine {
    private com.android.internal.config.appcloning.AppCloningDeviceConfigHelper mAppCloningDeviceConfigHelper;
    private final android.content.Context mContext;
    private final com.android.server.pm.DefaultAppProvider mDefaultAppProvider;
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;
    private final com.android.server.pm.UserManagerService mUserManager;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);

    public CrossProfileIntentResolverEngine(com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, com.android.server.pm.DefaultAppProvider defaultAppProvider, android.content.Context context) {
        this.mUserManager = userManagerService;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mDefaultAppProvider = defaultAppProvider;
        this.mContext = context;
    }

    public java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, long j, java.lang.String str2, boolean z, boolean z2, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        return resolveIntentInternal(computer, intent, str, i, i, j, str2, z, z2, function, null);
    }

    private java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntentInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, int i2, long j, java.lang.String str2, boolean z, boolean z2, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function, java.util.Set<java.lang.Integer> set) {
        android.content.pm.UserInfo profileParent;
        com.android.server.pm.CrossProfileDomainInfo crossProfileDomainPreferredLpr;
        java.util.Set<java.lang.Integer> set2;
        int i3;
        java.util.Set<java.lang.Integer> set3;
        android.util.SparseArray sparseArray;
        boolean z3;
        if (set != null) {
            set.add(java.lang.Integer.valueOf(i2));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<com.android.server.pm.CrossProfileIntentFilter> matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i2);
        if (matchingCrossProfileIntentFilters == null || matchingCrossProfileIntentFilters.isEmpty()) {
            if (i == i2 && intent.hasWebURI() && (profileParent = computer.getProfileParent(i2)) != null && (crossProfileDomainPreferredLpr = computer.getCrossProfileDomainPreferredLpr(intent, str, j, i2, profileParent.id)) != null) {
                arrayList.add(crossProfileDomainPreferredLpr);
            }
            return arrayList;
        }
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        for (int i4 = 0; i4 < matchingCrossProfileIntentFilters.size(); i4++) {
            com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = matchingCrossProfileIntentFilters.get(i4);
            if (!sparseArray2.contains(crossProfileIntentFilter.mTargetUserId)) {
                sparseArray2.put(crossProfileIntentFilter.mTargetUserId, new java.util.ArrayList());
            }
            ((java.util.List) sparseArray2.get(crossProfileIntentFilter.mTargetUserId)).add(crossProfileIntentFilter);
        }
        if (set != null) {
            set2 = set;
        } else {
            java.util.HashSet hashSet = new java.util.HashSet();
            hashSet.add(java.lang.Integer.valueOf(i2));
            set2 = hashSet;
        }
        int i5 = 0;
        while (i5 < sparseArray2.size()) {
            int keyAt = sparseArray2.keyAt(i5);
            if (set2.contains(java.lang.Integer.valueOf(keyAt))) {
                i3 = i5;
                set3 = set2;
                sparseArray = sparseArray2;
            } else {
                com.android.server.pm.CrossProfileResolver chooseCrossProfileResolver = chooseCrossProfileResolver(computer, i2, keyAt, z2, j);
                if (chooseCrossProfileResolver == null) {
                    i3 = i5;
                    set3 = set2;
                    sparseArray = sparseArray2;
                } else {
                    i3 = i5;
                    set3 = set2;
                    android.util.SparseArray sparseArray3 = sparseArray2;
                    java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent = chooseCrossProfileResolver.resolveIntent(computer, intent, str, i2, keyAt, j, str2, (java.util.List) sparseArray2.valueAt(i5), z, function);
                    arrayList.addAll(resolveIntent);
                    set3.add(java.lang.Integer.valueOf(keyAt));
                    int i6 = 0;
                    while (true) {
                        if (i6 >= ((java.util.List) sparseArray3.valueAt(i3)).size()) {
                            z3 = false;
                            break;
                        }
                        if ((((com.android.server.pm.CrossProfileIntentFilter) ((java.util.List) sparseArray3.valueAt(i3)).get(i6)).mFlags & 16) == 0) {
                            i6++;
                        } else {
                            z3 = true;
                            break;
                        }
                    }
                    if (!z3) {
                        sparseArray = sparseArray3;
                    } else {
                        sparseArray = sparseArray3;
                        arrayList.addAll(resolveIntentInternal(computer, intent, str, i, keyAt, j, str2, hasNonNegativePriority(resolveIntent), z2, function, set3));
                    }
                }
            }
            i5 = i3 + 1;
            set2 = set3;
            sparseArray2 = sparseArray;
        }
        return arrayList;
    }

    private com.android.server.pm.CrossProfileResolver chooseCrossProfileResolver(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2, boolean z, long j) {
        if (shouldUseNoFilteringResolver(i, i2)) {
            if (this.mAppCloningDeviceConfigHelper == null) {
                this.mAppCloningDeviceConfigHelper = com.android.internal.config.appcloning.AppCloningDeviceConfigHelper.getInstance(this.mContext);
            }
            if (com.android.server.pm.NoFilteringResolver.isIntentRedirectionAllowed(this.mContext, this.mAppCloningDeviceConfigHelper, z, j)) {
                return new com.android.server.pm.NoFilteringResolver(computer.getComponentResolver(), this.mUserManager);
            }
            return null;
        }
        return new com.android.server.pm.DefaultCrossProfileResolver(computer.getComponentResolver(), this.mUserManager, this.mDomainVerificationManager);
    }

    public boolean canReachTo(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        return canReachToInternal(computer, intent, str, i, i2, new java.util.HashSet());
    }

    private boolean canReachToInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2, java.util.Set<java.lang.Integer> set) {
        if (i == i2) {
            return true;
        }
        set.add(java.lang.Integer.valueOf(i));
        java.util.List<com.android.server.pm.CrossProfileIntentFilter> matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i);
        if (matchingCrossProfileIntentFilters != null) {
            for (int i3 = 0; i3 < matchingCrossProfileIntentFilters.size(); i3++) {
                com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = matchingCrossProfileIntentFilters.get(i3);
                if (crossProfileIntentFilter.mTargetUserId == i2) {
                    return true;
                }
                if (!set.contains(java.lang.Integer.valueOf(crossProfileIntentFilter.mTargetUserId)) && (crossProfileIntentFilter.mFlags & 16) != 0) {
                    set.add(java.lang.Integer.valueOf(crossProfileIntentFilter.mTargetUserId));
                    if (canReachToInternal(computer, intent, str, crossProfileIntentFilter.mTargetUserId, i2, set)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean shouldSkipCurrentProfile(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i) {
        java.util.List<com.android.server.pm.CrossProfileIntentFilter> matchingCrossProfileIntentFilters = computer.getMatchingCrossProfileIntentFilters(intent, str, i);
        if (matchingCrossProfileIntentFilters != null) {
            for (int i2 = 0; i2 < matchingCrossProfileIntentFilters.size(); i2++) {
                if ((matchingCrossProfileIntentFilters.get(i2).getFlags() & 2) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public com.android.server.pm.QueryIntentActivitiesResult combineFilterAndCreateQueryActivitiesResponse(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, long j, int i, int i2, boolean z2, java.util.List<android.content.pm.ResolveInfo> list, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list2, boolean z3, boolean z4, boolean z5, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        java.util.List<android.content.pm.ResolveInfo> list3;
        if (shouldSkipCurrentProfile(computer, intent, str, i)) {
            return new com.android.server.pm.QueryIntentActivitiesResult(computer.applyPostResolutionFilter(resolveInfoFromCrossProfileDomainInfo(list2), str2, z, i2, z2, i, intent));
        }
        if (str3 == null && intent.hasWebURI()) {
            if (!z4 && ((list.size() <= 1 && list2.isEmpty()) || (list.isEmpty() && !list2.isEmpty()))) {
                list.addAll(resolveInfoFromCrossProfileDomainInfo(list2));
                return new com.android.server.pm.QueryIntentActivitiesResult(computer.applyPostResolutionFilter(list, str2, z, i2, z2, i, intent));
            }
            list3 = filterCandidatesWithDomainPreferredActivitiesLPr(computer, intent, j, list, list2, i, z3, z2, function);
        } else {
            list.addAll(resolveInfoFromCrossProfileDomainInfo(list2));
            list3 = list;
        }
        if (list3.size() == 1 && !android.os.UserHandle.of(i).equals(list3.get(0).userHandle) && isNoFilteringPropertyConfiguredForUser(i)) {
            intent.prepareToLeaveUser(i);
        }
        return new com.android.server.pm.QueryIntentActivitiesResult(z5, z4, list3);
    }

    private java.util.List<android.content.pm.ResolveInfo> filterCandidatesWithDomainPreferredActivitiesLPr(com.android.server.pm.Computer computer, android.content.Intent intent, long j, java.util.List<android.content.pm.ResolveInfo> list, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list2, int i, boolean z, boolean z2, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        return filterCandidatesWithDomainPreferredActivitiesLPrBody(computer, intent, j, list, list2, i, z, (intent.getFlags() & 8) != 0, z2, function);
    }

    private java.util.List<android.content.pm.ResolveInfo> filterCandidatesWithDomainPreferredActivitiesLPrBody(com.android.server.pm.Computer computer, android.content.Intent intent, long j, java.util.List<android.content.pm.ResolveInfo> list, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list2, int i, boolean z, boolean z2, boolean z3, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        boolean z4 = true;
        boolean z5 = intent.isWebIntent() && z;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo = list.get(i2);
            if (z5) {
                if (!resolveInfo.isInstantAppAvailable) {
                    if (computer.isInstantAppInternal(resolveInfo.activityInfo.packageName, i, 1000)) {
                    }
                }
            }
            if (resolveInfo.handleAllWebDataURI) {
                arrayList2.add(resolveInfo);
            } else {
                arrayList3.add(resolveInfo);
            }
        }
        android.util.SparseArray<java.util.List<com.android.server.pm.CrossProfileDomainInfo>> sparseArray = new android.util.SparseArray<>();
        if (list2 != null && !list2.isEmpty()) {
            for (int i3 = 0; i3 < list2.size(); i3++) {
                com.android.server.pm.CrossProfileDomainInfo crossProfileDomainInfo = list2.get(i3);
                if (!sparseArray.contains(crossProfileDomainInfo.mTargetUserId)) {
                    sparseArray.put(crossProfileDomainInfo.mTargetUserId, new java.util.ArrayList());
                }
                sparseArray.get(crossProfileDomainInfo.mTargetUserId).add(crossProfileDomainInfo);
            }
        }
        if (!com.android.server.pm.verify.domain.DomainVerificationUtils.isDomainVerificationIntent(intent, j)) {
            arrayList.addAll(arrayList3);
            arrayList.addAll(filterCrossProfileCandidatesWithDomainPreferredActivities(computer, intent, j, sparseArray, i, 0, z3));
        } else {
            android.util.Pair<java.util.List<android.content.pm.ResolveInfo>, java.lang.Integer> filterToApprovedApp = this.mDomainVerificationManager.filterToApprovedApp(intent, arrayList3, i, function);
            java.util.List list3 = (java.util.List) filterToApprovedApp.first;
            java.lang.Integer num = (java.lang.Integer) filterToApprovedApp.second;
            if (list3.isEmpty()) {
                arrayList.addAll(filterCrossProfileCandidatesWithDomainPreferredActivities(computer, intent, j, sparseArray, i, 0, z3));
            } else {
                arrayList.addAll(list3);
                arrayList.addAll(filterCrossProfileCandidatesWithDomainPreferredActivities(computer, intent, j, sparseArray, i, num.intValue(), z3));
                z4 = false;
            }
        }
        if (z4) {
            if ((j & 131072) != 0) {
                arrayList.addAll(arrayList2);
            } else {
                java.lang.String defaultBrowser = this.mDefaultAppProvider.getDefaultBrowser(i);
                int size2 = arrayList2.size();
                android.content.pm.ResolveInfo resolveInfo2 = null;
                int i4 = 0;
                for (int i5 = 0; i5 < size2; i5++) {
                    android.content.pm.ResolveInfo resolveInfo3 = (android.content.pm.ResolveInfo) arrayList2.get(i5);
                    if (resolveInfo3.priority > i4) {
                        i4 = resolveInfo3.priority;
                    }
                    if (resolveInfo3.activityInfo.packageName.equals(defaultBrowser) && (resolveInfo2 == null || resolveInfo2.priority < resolveInfo3.priority)) {
                        if (z2) {
                            android.util.Slog.v("PackageManager", "Considering default browser match " + resolveInfo3);
                        }
                        resolveInfo2 = resolveInfo3;
                    }
                }
                if (resolveInfo2 != null && resolveInfo2.priority >= i4 && !android.text.TextUtils.isEmpty(defaultBrowser)) {
                    if (z2) {
                        android.util.Slog.v("PackageManager", "Default browser match " + resolveInfo2);
                    }
                    arrayList.add(resolveInfo2);
                } else {
                    arrayList.addAll(arrayList2);
                }
            }
            if (arrayList.size() == 0) {
                arrayList.addAll(list);
            }
        }
        return arrayList;
    }

    private java.util.List<android.content.pm.ResolveInfo> filterCrossProfileCandidatesWithDomainPreferredActivities(com.android.server.pm.Computer computer, android.content.Intent intent, long j, android.util.SparseArray<java.util.List<com.android.server.pm.CrossProfileDomainInfo>> sparseArray, int i, int i2, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
            if (sparseArray.keyAt(i3) == -2) {
                arrayList.addAll(sparseArray.valueAt(i3));
            } else {
                com.android.server.pm.CrossProfileResolver chooseCrossProfileResolver = chooseCrossProfileResolver(computer, i, sparseArray.keyAt(i3), z, j);
                if (chooseCrossProfileResolver != null) {
                    arrayList.addAll(chooseCrossProfileResolver.filterResolveInfoWithDomainPreferredActivity(intent, sparseArray.valueAt(i3), j, i, sparseArray.keyAt(i3), i2));
                } else {
                    arrayList.addAll(sparseArray.valueAt(i3));
                }
            }
        }
        return resolveInfoFromCrossProfileDomainInfo(arrayList);
    }

    private java.util.List<android.content.pm.ResolveInfo> resolveInfoFromCrossProfileDomainInfo(java.util.List<com.android.server.pm.CrossProfileDomainInfo> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i).mResolveInfo);
        }
        return arrayList;
    }

    private boolean hasNonNegativePriority(java.util.List<com.android.server.pm.CrossProfileDomainInfo> list) {
        return list.size() > 0 && list.get(0).mResolveInfo != null && list.get(0).mResolveInfo.priority >= 0;
    }

    private boolean shouldUseNoFilteringResolver(int i, int i2) {
        return isNoFilteringPropertyConfiguredForUser(i) || isNoFilteringPropertyConfiguredForUser(i2);
    }

    private boolean isNoFilteringPropertyConfiguredForUser(int i) {
        android.content.pm.UserProperties userProperties;
        return this.mUserManager.isProfile(i) && (userProperties = this.mUserManagerInternal.getUserProperties(i)) != null && userProperties.getCrossProfileIntentResolutionStrategy() == 1;
    }
}
