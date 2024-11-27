package com.android.server.pm;

/* loaded from: classes2.dex */
public final class DefaultCrossProfileResolver extends com.android.server.pm.CrossProfileResolver {
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;

    public DefaultCrossProfileResolver(com.android.server.pm.resolution.ComponentResolverApi componentResolverApi, com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal) {
        super(componentResolverApi, userManagerService);
        this.mDomainVerificationManager = domainVerificationManagerInternal;
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, int i2, long j, java.lang.String str2, java.util.List<com.android.server.pm.CrossProfileIntentFilter> list, boolean z, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        com.android.server.pm.CrossProfileDomainInfo crossProfileDomainInfo;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (str2 != null) {
            return arrayList;
        }
        com.android.server.pm.CrossProfileDomainInfo querySkipCurrentProfileIntents = querySkipCurrentProfileIntents(computer, list, intent, str, j, i, function);
        if (querySkipCurrentProfileIntents != null) {
            arrayList.add(querySkipCurrentProfileIntents);
            return filterIfNotSystemUser(arrayList, i);
        }
        com.android.server.pm.CrossProfileDomainInfo queryCrossProfileIntents = queryCrossProfileIntents(computer, list, intent, str, j, i, z, function);
        if (intent.hasWebURI()) {
            android.content.pm.UserInfo profileParent = getProfileParent(i);
            if (profileParent == null) {
                crossProfileDomainInfo = null;
            } else {
                crossProfileDomainInfo = computer.getCrossProfileDomainPreferredLpr(intent, str, j, i, profileParent.id);
            }
            if (crossProfileDomainInfo != null) {
                queryCrossProfileIntents = crossProfileDomainInfo;
            }
            if (queryCrossProfileIntents != null) {
                arrayList.add(queryCrossProfileIntents);
            }
        } else if (queryCrossProfileIntents != null) {
            arrayList.add(queryCrossProfileIntents);
        }
        return arrayList;
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public java.util.List<com.android.server.pm.CrossProfileDomainInfo> filterResolveInfoWithDomainPreferredActivity(android.content.Intent intent, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list, long j, int i, int i2, int i3) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (list != null && !list.isEmpty()) {
            for (int i4 = 0; i4 < list.size(); i4++) {
                com.android.server.pm.CrossProfileDomainInfo crossProfileDomainInfo = list.get(i4);
                if (crossProfileDomainInfo.mHighestApprovalLevel > i3) {
                    arrayList.add(crossProfileDomainInfo);
                }
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private com.android.server.pm.CrossProfileDomainInfo querySkipCurrentProfileIntents(com.android.server.pm.Computer computer, java.util.List<com.android.server.pm.CrossProfileIntentFilter> list, android.content.Intent intent, java.lang.String str, long j, int i, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        com.android.server.pm.CrossProfileDomainInfo createForwardingResolveInfo;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = list.get(i2);
                if ((crossProfileIntentFilter.getFlags() & 2) != 0 && (createForwardingResolveInfo = createForwardingResolveInfo(computer, crossProfileIntentFilter, intent, str, j, i, function)) != null) {
                    return createForwardingResolveInfo;
                }
            }
            return null;
        }
        return null;
    }

    @android.annotation.Nullable
    private com.android.server.pm.CrossProfileDomainInfo queryCrossProfileIntents(com.android.server.pm.Computer computer, java.util.List<com.android.server.pm.CrossProfileIntentFilter> list, android.content.Intent intent, java.lang.String str, long j, int i, boolean z, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        com.android.server.pm.CrossProfileDomainInfo crossProfileDomainInfo;
        if (list == null) {
            return null;
        }
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        int size = list.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                crossProfileDomainInfo = null;
                break;
            }
            com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = list.get(i2);
            int targetUserId = crossProfileIntentFilter.getTargetUserId();
            boolean z2 = (crossProfileIntentFilter.getFlags() & 2) != 0;
            boolean z3 = (crossProfileIntentFilter.getFlags() & 4) != 0;
            if (!z2 && !sparseBooleanArray.get(targetUserId) && (!z3 || !z)) {
                crossProfileDomainInfo = createForwardingResolveInfo(computer, crossProfileIntentFilter, intent, str, j, i, function);
                if (crossProfileDomainInfo != null) {
                    break;
                }
                sparseBooleanArray.put(targetUserId, true);
            }
            i2++;
        }
        if (crossProfileDomainInfo == null || !isUserEnabled(crossProfileDomainInfo.mResolveInfo.targetUserId) || filterIfNotSystemUser(java.util.Collections.singletonList(crossProfileDomainInfo), i).isEmpty()) {
            return null;
        }
        return crossProfileDomainInfo;
    }

    @android.annotation.Nullable
    protected com.android.server.pm.CrossProfileDomainInfo createForwardingResolveInfo(com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i, @android.annotation.NonNull java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        android.content.pm.ResolveInfo resolveInfo;
        com.android.server.pm.pkg.PackageStateInternal apply;
        int targetUserId = crossProfileIntentFilter.getTargetUserId();
        if (!isUserEnabled(targetUserId)) {
            return null;
        }
        java.util.List<android.content.pm.ResolveInfo> queryActivities = this.mComponentResolver.queryActivities(computer, intent, str, j, targetUserId);
        if (com.android.internal.util.CollectionUtils.isEmpty(queryActivities)) {
            return null;
        }
        int size = queryActivities.size() - 1;
        while (true) {
            if (size < 0) {
                resolveInfo = null;
                break;
            }
            if ((queryActivities.get(size).activityInfo.applicationInfo.flags & 1073741824) == 0) {
                resolveInfo = computer.createForwardingResolveInfoUnchecked(crossProfileIntentFilter, i, targetUserId);
                break;
            }
            size--;
        }
        if (resolveInfo == null) {
            return null;
        }
        int size2 = queryActivities.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size2; i3++) {
            android.content.pm.ResolveInfo resolveInfo2 = queryActivities.get(i3);
            if (!resolveInfo2.handleAllWebDataURI && (apply = function.apply(resolveInfo2.activityInfo.packageName)) != null) {
                i2 = java.lang.Math.max(i2, this.mDomainVerificationManager.approvalLevelForDomain(apply, intent, j, targetUserId));
            }
        }
        return new com.android.server.pm.CrossProfileDomainInfo(resolveInfo, i2, targetUserId);
    }
}
