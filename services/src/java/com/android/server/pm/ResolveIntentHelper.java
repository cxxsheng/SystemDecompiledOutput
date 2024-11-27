package com.android.server.pm;

/* loaded from: classes2.dex */
final class ResolveIntentHelper {

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.content.pm.ActivityInfo> mInstantAppInstallerActivitySupplier;

    @android.annotation.NonNull
    private final com.android.server.compat.PlatformCompat mPlatformCompat;

    @android.annotation.NonNull
    private final com.android.server.pm.PreferredActivityHelper mPreferredActivityHelper;

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.content.pm.ResolveInfo> mResolveInfoSupplier;

    @android.annotation.NonNull
    private final com.android.server.pm.UserManagerService mUserManager;

    @android.annotation.NonNull
    private final com.android.server.pm.UserNeedsBadgingCache mUserNeedsBadging;

    ResolveIntentHelper(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.PreferredActivityHelper preferredActivityHelper, @android.annotation.NonNull com.android.server.compat.PlatformCompat platformCompat, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache, @android.annotation.NonNull java.util.function.Supplier<android.content.pm.ResolveInfo> supplier, @android.annotation.NonNull java.util.function.Supplier<android.content.pm.ActivityInfo> supplier2, @android.annotation.NonNull android.os.Handler handler) {
        this.mContext = context;
        this.mPreferredActivityHelper = preferredActivityHelper;
        this.mPlatformCompat = platformCompat;
        this.mUserManager = userManagerService;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mUserNeedsBadging = userNeedsBadgingCache;
        this.mResolveInfoSupplier = supplier;
        this.mInstantAppInstallerActivitySupplier = supplier2;
        this.mHandler = handler;
    }

    private static void filterNonExportedComponents(final android.content.Intent intent, int i, final int i2, java.util.List<android.content.pm.ResolveInfo> list, com.android.server.compat.PlatformCompat platformCompat, java.lang.String str, com.android.server.pm.Computer computer, android.os.Handler handler) {
        if (list == null || intent.getPackage() != null || intent.getComponent() != null || android.app.ActivityManager.canAccessUnexportedComponents(i)) {
            return;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(i);
        if (androidPackage != null) {
            androidPackage.getPackageName();
        }
        final android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        final android.app.IUnsafeIntentStrictModeCallback registeredStrictModeCallback = activityManagerInternal.getRegisteredStrictModeCallback(i2);
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!list.get(size).getComponentInfo().exported) {
                boolean isChangeEnabledByUid = platformCompat.isChangeEnabledByUid(com.android.server.am.ActivityManagerService.IMPLICIT_INTENTS_ONLY_MATCH_EXPORTED_COMPONENTS, i);
                com.android.server.am.ActivityManagerUtils.logUnsafeIntentEvent(2, i, intent, str, isChangeEnabledByUid);
                if (registeredStrictModeCallback != null) {
                    handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.ResolveIntentHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.ResolveIntentHelper.lambda$filterNonExportedComponents$0(registeredStrictModeCallback, intent, activityManagerInternal, i2);
                        }
                    });
                }
                if (!isChangeEnabledByUid) {
                    return;
                } else {
                    list.remove(size);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$filterNonExportedComponents$0(android.app.IUnsafeIntentStrictModeCallback iUnsafeIntentStrictModeCallback, android.content.Intent intent, android.app.ActivityManagerInternal activityManagerInternal, int i) {
        try {
            iUnsafeIntentStrictModeCallback.onImplicitIntentMatchedInternalComponent(intent.cloneFilter());
        } catch (android.os.RemoteException e) {
            activityManagerInternal.unregisterStrictModeCallback(i);
        }
    }

    public android.content.pm.ResolveInfo resolveIntentInternal(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2) {
        return resolveIntentInternal(computer, intent, str, j, j2, i, z, i2, false, 0);
    }

    public android.content.pm.ResolveInfo resolveIntentInternal(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, long j2, int i, boolean z, int i2, boolean z2, int i3) {
        try {
            android.os.Trace.traceBegin(262144L, "resolveIntent");
            if (!this.mUserManager.exists(i)) {
                android.os.Trace.traceEnd(262144L);
                return null;
            }
            int callingUid = android.os.Binder.getCallingUid();
            long updateFlagsForResolve = computer.updateFlagsForResolve(j, i, i2, z, computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
            computer.enforceCrossUserPermission(callingUid, i, false, false, "resolve intent");
            android.os.Trace.traceBegin(262144L, "queryIntentActivities");
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, str, updateFlagsForResolve, j2, i2, i, z, true);
            if (z2) {
                filterNonExportedComponents(intent, i2, i3, queryIntentActivitiesInternal, this.mPlatformCompat, str, computer, this.mHandler);
            }
            android.os.Trace.traceEnd(262144L);
            android.content.pm.ResolveInfo chooseBestActivity = chooseBestActivity(computer, intent, str, updateFlagsForResolve, j2, queryIntentActivitiesInternal, i, android.os.UserHandle.getAppId(i2) >= 10000 && !z);
            if (((j2 & 1) != 0) && chooseBestActivity != null) {
                if (chooseBestActivity.handleAllWebDataURI) {
                    android.os.Trace.traceEnd(262144L);
                    return null;
                }
            }
            android.os.Trace.traceEnd(262144L);
            return chooseBestActivity;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(262144L);
            throw th;
        }
    }

    private android.content.pm.ResolveInfo chooseBestActivity(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, long j2, java.util.List<android.content.pm.ResolveInfo> list, int i, boolean z) {
        int i2;
        boolean z2;
        int i3;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        if (list != null) {
            int size = list.size();
            if (size == 1) {
                return list.get(0);
            }
            if (size > 1) {
                boolean z3 = (intent.getFlags() & 8) != 0;
                android.content.pm.ResolveInfo resolveInfo = list.get(0);
                android.content.pm.ResolveInfo resolveInfo2 = list.get(1);
                if (z3) {
                    android.util.Slog.v("PackageManager", resolveInfo.activityInfo.name + "=" + resolveInfo.priority + " vs " + resolveInfo2.activityInfo.name + "=" + resolveInfo2.priority);
                }
                if (resolveInfo.priority != resolveInfo2.priority || resolveInfo.preferredOrder != resolveInfo2.preferredOrder) {
                    i2 = 0;
                } else if (resolveInfo.isDefault != resolveInfo2.isDefault) {
                    i2 = 0;
                } else {
                    android.content.pm.ResolveInfo findPreferredActivityNotLocked = this.mPreferredActivityHelper.findPreferredActivityNotLocked(computer, intent, str, j, list, true, false, z3, i, z);
                    if (findPreferredActivityNotLocked != null) {
                        return findPreferredActivityNotLocked;
                    }
                    int i4 = 0;
                    int i5 = 0;
                    while (i5 < size) {
                        android.content.pm.ResolveInfo resolveInfo3 = list.get(i5);
                        if (!resolveInfo3.handleAllWebDataURI) {
                            i3 = i4;
                        } else {
                            i3 = i4 + 1;
                        }
                        if (resolveInfo3.activityInfo.applicationInfo.isInstantApp() && (packageStateInternal = computer.getPackageStateInternal(resolveInfo3.activityInfo.packageName)) != null && com.android.server.pm.PackageManagerServiceUtils.hasAnyDomainApproval(this.mDomainVerificationManager, packageStateInternal, intent, j, i)) {
                            return resolveInfo3;
                        }
                        i5++;
                        i4 = i3;
                    }
                    if ((j2 & 2) != 0) {
                        return null;
                    }
                    android.content.pm.ResolveInfo resolveInfo4 = new android.content.pm.ResolveInfo(this.mResolveInfoSupplier.get());
                    resolveInfo4.handleAllWebDataURI = i4 == size;
                    resolveInfo4.activityInfo = new android.content.pm.ActivityInfo(resolveInfo4.activityInfo);
                    resolveInfo4.activityInfo.labelRes = com.android.internal.app.ResolverActivity.getLabelRes(intent.getAction());
                    if (resolveInfo4.userHandle == null) {
                        resolveInfo4.userHandle = android.os.UserHandle.of(i);
                    }
                    java.lang.String str2 = intent.getPackage();
                    if (android.text.TextUtils.isEmpty(str2) || !allHavePackage(list, str2)) {
                        z2 = true;
                    } else {
                        android.content.pm.ApplicationInfo applicationInfo = list.get(0).activityInfo.applicationInfo;
                        resolveInfo4.resolvePackageName = str2;
                        if (this.mUserNeedsBadging.get(i)) {
                            z2 = true;
                            resolveInfo4.noResourceId = true;
                        } else {
                            z2 = true;
                            resolveInfo4.icon = applicationInfo.icon;
                        }
                        resolveInfo4.iconResourceId = applicationInfo.icon;
                        resolveInfo4.labelRes = applicationInfo.labelRes;
                    }
                    resolveInfo4.activityInfo.applicationInfo = new android.content.pm.ApplicationInfo(resolveInfo4.activityInfo.applicationInfo);
                    if (i != 0) {
                        resolveInfo4.activityInfo.applicationInfo.uid = android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(resolveInfo4.activityInfo.applicationInfo.uid));
                    }
                    if (resolveInfo4.activityInfo.metaData == null) {
                        resolveInfo4.activityInfo.metaData = new android.os.Bundle();
                    }
                    resolveInfo4.activityInfo.metaData.putBoolean("android.dock_home", z2);
                    return resolveInfo4;
                }
                return list.get(i2);
            }
            return null;
        }
        return null;
    }

    private boolean allHavePackage(java.util.List<android.content.pm.ResolveInfo> list, java.lang.String str) {
        if (com.android.internal.util.ArrayUtils.isEmpty(list)) {
            return false;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = list.get(i);
            android.content.pm.ActivityInfo activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
            if (activityInfo == null || !str.equals(activityInfo.packageName)) {
                return false;
            }
        }
        return true;
    }

    public android.content.IntentSender getLaunchIntentSenderForPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(str);
        int callingUid = android.os.Binder.getCallingUid();
        computer.enforceCrossUserPermission(callingUid, i, false, false, "get launch intent sender for package");
        if (!android.os.UserHandle.isSameApp(callingUid, computer.getPackageUid(str2, 0L, i))) {
            throw new java.lang.SecurityException("getLaunchIntentSenderForPackage() from calling uid: " + callingUid + " does not own package: " + str2);
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.INFO");
        intent.setPackage(str);
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(contentResolver);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, resolveTypeIfNeeded, 0L, 0L, callingUid, i, true, false);
        if (queryIntentActivitiesInternal == null || queryIntentActivitiesInternal.size() <= 0) {
            intent.removeCategory("android.intent.category.INFO");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            resolveTypeIfNeeded = intent.resolveTypeIfNeeded(contentResolver);
            queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, resolveTypeIfNeeded, 0L, 0L, callingUid, i, true, false);
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setFlags(268435456);
        if (queryIntentActivitiesInternal != null && !queryIntentActivitiesInternal.isEmpty()) {
            intent2.setPackage(null);
            intent2.setClassName(queryIntentActivitiesInternal.get(0).activityInfo.packageName, queryIntentActivitiesInternal.get(0).activityInfo.name);
        }
        return new android.content.IntentSender(android.app.ActivityManager.getService().getIntentSenderWithFeature(2, str2, str3, (android.os.IBinder) null, (java.lang.String) null, 1, new android.content.Intent[]{intent2}, resolveTypeIfNeeded != null ? new java.lang.String[]{resolveTypeIfNeeded} : null, 67108864, (android.os.Bundle) null, i));
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.ResolveInfo> queryIntentReceiversInternal(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        return queryIntentReceiversInternal(computer, intent, str, j, i, i2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x013e, code lost:
    
        if (r1 != null) goto L77;
     */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.List<android.content.pm.ResolveInfo> queryIntentReceiversInternal(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z) {
        android.content.Intent intent2;
        android.content.Intent intent3;
        java.util.List<android.content.pm.ResolveInfo> list;
        java.util.List<android.content.pm.ResolveInfo> list2;
        java.util.List<android.content.pm.ResolveInfo> queryReceivers;
        if (!this.mUserManager.exists(i)) {
            return java.util.Collections.emptyList();
        }
        int i3 = z ? 1000 : i2;
        computer.enforceCrossUserPermission(i3, i, false, false, "query intent receivers");
        java.lang.String instantAppPackageName = computer.getInstantAppPackageName(i3);
        long updateFlagsForResolve = computer.updateFlagsForResolve(j, i, i3, false, computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
        android.content.ComponentName component = intent.getComponent();
        if (component == null && intent.getSelector() != null) {
            android.content.Intent selector = intent.getSelector();
            intent3 = intent;
            intent2 = selector;
            component = selector.getComponent();
        } else {
            intent2 = intent;
            intent3 = null;
        }
        com.android.server.pm.resolution.ComponentResolverApi componentResolver = computer.getComponentResolver();
        java.util.List<android.content.pm.ResolveInfo> emptyList = java.util.Collections.emptyList();
        if (component != null) {
            android.content.pm.ActivityInfo receiverInfo = computer.getReceiverInfo(component, updateFlagsForResolve, i);
            if (receiverInfo != null) {
                boolean z2 = (8388608 & updateFlagsForResolve) != 0;
                boolean z3 = (updateFlagsForResolve & 16777216) != 0;
                boolean z4 = (updateFlagsForResolve & 33554432) != 0;
                boolean z5 = instantAppPackageName != null;
                boolean equals = component.getPackageName().equals(instantAppPackageName);
                boolean z6 = (receiverInfo.applicationInfo.privateFlags & 128) != 0;
                boolean z7 = (receiverInfo.flags & 1048576) != 0;
                if (!(!equals && (!(z2 || z5 || !z6) || (z3 && z5 && (!z7 || (z4 && !(z7 && (receiverInfo.flags & 2097152) == 0))))))) {
                    android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
                    resolveInfo.activityInfo = receiverInfo;
                    java.util.ArrayList arrayList = new java.util.ArrayList(1);
                    arrayList.add(resolveInfo);
                    com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mPlatformCompat, componentResolver, arrayList, true, intent2, str, i2);
                    emptyList = arrayList;
                }
            }
            list2 = emptyList;
        } else {
            java.lang.String str2 = intent2.getPackage();
            if (str2 == null && (queryReceivers = componentResolver.queryReceivers(computer, intent2, str, updateFlagsForResolve, i)) != null) {
                list = queryReceivers;
            } else {
                list = emptyList;
            }
            com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(str2);
            if (androidPackage != null) {
                list2 = componentResolver.queryReceivers(computer, intent2, str, updateFlagsForResolve, androidPackage.getReceivers(), i);
            }
            list2 = list;
        }
        if (intent3 != null) {
            com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mPlatformCompat, componentResolver, list2, true, intent3, str, i2);
        }
        return computer.applyPostResolutionFilter(list2, instantAppPackageName, false, i3, false, i, intent2);
    }

    public android.content.pm.ResolveInfo resolveServiceInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternal;
        if (this.mUserManager.exists(i) && (queryIntentServicesInternal = computer.queryIntentServicesInternal(intent, str, computer.updateFlagsForResolve(j, i, i2, false, false), i, i2, false)) != null && queryIntentServicesInternal.size() >= 1) {
            return queryIntentServicesInternal.get(0);
        }
        return null;
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i) {
        android.content.Intent intent2;
        if (!this.mUserManager.exists(i)) {
            return java.util.Collections.emptyList();
        }
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String instantAppPackageName = computer.getInstantAppPackageName(callingUid);
        long updateFlagsForResolve = computer.updateFlagsForResolve(j, i, callingUid, false, false);
        android.content.ComponentName component = intent.getComponent();
        if (component == null && intent.getSelector() != null) {
            android.content.Intent selector = intent.getSelector();
            intent2 = selector;
            component = selector.getComponent();
        } else {
            intent2 = intent;
        }
        if (component != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            android.content.pm.ProviderInfo providerInfo = computer.getProviderInfo(component, updateFlagsForResolve, i);
            if (providerInfo != null) {
                boolean z = (8388608 & updateFlagsForResolve) != 0;
                boolean z2 = (updateFlagsForResolve & 16777216) != 0;
                boolean z3 = instantAppPackageName != null;
                boolean equals = component.getPackageName().equals(instantAppPackageName);
                boolean z4 = (providerInfo.applicationInfo.privateFlags & 128) != 0;
                boolean z5 = !equals && (!(z || z3 || !z4) || (z2 && z3 && ((providerInfo.flags & 1048576) == 0)));
                boolean z6 = (z4 || z3 || !computer.shouldFilterApplication(computer.getPackageStateInternal(providerInfo.applicationInfo.packageName, 1000), callingUid, i)) ? false : true;
                if (!z5 && !z6) {
                    android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
                    resolveInfo.providerInfo = providerInfo;
                    arrayList.add(resolveInfo);
                }
            }
            return arrayList;
        }
        com.android.server.pm.resolution.ComponentResolverApi componentResolver = computer.getComponentResolver();
        java.lang.String str2 = intent2.getPackage();
        if (str2 == null) {
            java.util.List<android.content.pm.ResolveInfo> queryProviders = componentResolver.queryProviders(computer, intent2, str, updateFlagsForResolve, i);
            if (queryProviders == null) {
                return java.util.Collections.emptyList();
            }
            return applyPostContentProviderResolutionFilter(computer, queryProviders, instantAppPackageName, i, callingUid);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(str2);
        if (androidPackage != null) {
            java.util.List<android.content.pm.ResolveInfo> queryProviders2 = componentResolver.queryProviders(computer, intent2, str, updateFlagsForResolve, androidPackage.getProviders(), i);
            if (queryProviders2 == null) {
                return java.util.Collections.emptyList();
            }
            return applyPostContentProviderResolutionFilter(computer, queryProviders2, instantAppPackageName, i, callingUid);
        }
        return java.util.Collections.emptyList();
    }

    private java.util.List<android.content.pm.ResolveInfo> applyPostContentProviderResolutionFilter(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.List<android.content.pm.ResolveInfo> list, java.lang.String str, int i, int i2) {
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ResolveInfo resolveInfo = list.get(size);
            if (str != null || computer.shouldFilterApplication(computer.getPackageStateInternal(resolveInfo.providerInfo.packageName, 0), i2, i)) {
                boolean isInstantApp = resolveInfo.providerInfo.applicationInfo.isInstantApp();
                if (isInstantApp && str.equals(resolveInfo.providerInfo.packageName)) {
                    if (resolveInfo.providerInfo.splitName != null && !com.android.internal.util.ArrayUtils.contains(resolveInfo.providerInfo.applicationInfo.splitNames, resolveInfo.providerInfo.splitName)) {
                        if (this.mInstantAppInstallerActivitySupplier.get() == null) {
                            if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                                android.util.Slog.v("PackageManager", "No installer - not adding it to the ResolveInfo list");
                            }
                            list.remove(size);
                        } else {
                            if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                                android.util.Slog.v("PackageManager", "Adding ephemeral installer to the ResolveInfo list");
                            }
                            android.content.pm.ResolveInfo resolveInfo2 = new android.content.pm.ResolveInfo(computer.getInstantAppInstallerInfo());
                            resolveInfo2.auxiliaryInfo = new android.content.pm.AuxiliaryResolveInfo((android.content.ComponentName) null, resolveInfo.providerInfo.packageName, resolveInfo.providerInfo.applicationInfo.longVersionCode, resolveInfo.providerInfo.splitName);
                            resolveInfo2.filter = new android.content.IntentFilter();
                            resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                            list.set(size, resolveInfo2);
                        }
                    }
                } else if (isInstantApp || (resolveInfo.providerInfo.flags & 1048576) == 0) {
                    list.remove(size);
                }
            }
        }
        return list;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x013a  */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptionsInternal(com.android.server.pm.Computer computer, android.content.ComponentName componentName, android.content.Intent[] intentArr, java.lang.String[] strArr, android.content.Intent intent, java.lang.String str, long j, int i) {
        java.util.List<android.content.pm.ResolveInfo> list;
        java.lang.Object obj;
        int i2;
        java.util.Iterator<java.lang.String> actionsIterator;
        java.lang.String str2;
        java.lang.String str3;
        int i3;
        int i4;
        java.util.List<android.content.pm.ResolveInfo> list2;
        java.lang.String str4;
        android.content.pm.ResolveInfo resolveInfo;
        android.content.ComponentName componentName2;
        android.content.pm.ActivityInfo activityInfo;
        int size;
        int i5;
        java.util.List<android.content.pm.ResolveInfo> list3;
        int i6;
        java.lang.String str5;
        android.content.Intent[] intentArr2 = intentArr;
        if (!this.mUserManager.exists(i)) {
            return java.util.Collections.emptyList();
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForResolve = computer.updateFlagsForResolve(j, i, callingUid, false, computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
        computer.enforceCrossUserPermission(callingUid, i, false, false, "query intent activity options");
        java.lang.String action = intent.getAction();
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, str, updateFlagsForResolve | 64, i);
        java.lang.String str6 = null;
        if (intentArr2 == null) {
            list = queryIntentActivitiesInternal;
            obj = action;
            i2 = 0;
        } else {
            int i7 = 0;
            i2 = 0;
            while (i7 < intentArr2.length) {
                android.content.Intent intent2 = intentArr2[i7];
                if (intent2 == null) {
                    i3 = i7;
                    i4 = i2;
                    list2 = queryIntentActivitiesInternal;
                    str4 = action;
                } else {
                    java.lang.String action2 = intent2.getAction();
                    if (action != null && action.equals(action2)) {
                        str2 = str6;
                    } else {
                        str2 = action2;
                    }
                    android.content.ComponentName component = intent2.getComponent();
                    if (component == null) {
                        str3 = str2;
                        i3 = i7;
                        i4 = i2;
                        list2 = queryIntentActivitiesInternal;
                        str4 = action;
                        resolveInfo = resolveIntentInternal(computer, intent2, strArr != null ? strArr[i7] : str6, updateFlagsForResolve, 0L, i, false, android.os.Binder.getCallingUid());
                        if (resolveInfo != null) {
                            this.mResolveInfoSupplier.get();
                            activityInfo = resolveInfo.activityInfo;
                            componentName2 = new android.content.ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                            size = list2.size();
                            i5 = i4;
                            while (i5 < size) {
                                java.util.List<android.content.pm.ResolveInfo> list4 = list2;
                                android.content.pm.ResolveInfo resolveInfo2 = list4.get(i5);
                                if (resolveInfo2.activityInfo.name.equals(componentName2.getClassName()) && resolveInfo2.activityInfo.applicationInfo.packageName.equals(componentName2.getPackageName())) {
                                    str5 = str3;
                                } else {
                                    str5 = str3;
                                    if (str5 != null) {
                                        if (!resolveInfo2.filter.matchAction(str5)) {
                                        }
                                    }
                                    i5++;
                                    list2 = list4;
                                    str3 = str5;
                                }
                                list4.remove(i5);
                                if (resolveInfo == null) {
                                    resolveInfo = resolveInfo2;
                                }
                                i5--;
                                size--;
                                i5++;
                                list2 = list4;
                                str3 = str5;
                            }
                            list3 = list2;
                            if (resolveInfo == null) {
                                resolveInfo = new android.content.pm.ResolveInfo();
                                resolveInfo.activityInfo = activityInfo;
                            }
                            int i8 = i4;
                            list3.add(i8, resolveInfo);
                            i6 = i3;
                            resolveInfo.specificIndex = i6;
                            i2 = i8 + 1;
                        }
                    } else {
                        str3 = str2;
                        i3 = i7;
                        i4 = i2;
                        list2 = queryIntentActivitiesInternal;
                        str4 = action;
                        android.content.pm.ActivityInfo activityInfo2 = computer.getActivityInfo(component, updateFlagsForResolve, i);
                        if (activityInfo2 != null) {
                            resolveInfo = null;
                            componentName2 = component;
                            activityInfo = activityInfo2;
                            size = list2.size();
                            i5 = i4;
                            while (i5 < size) {
                            }
                            list3 = list2;
                            if (resolveInfo == null) {
                            }
                            int i82 = i4;
                            list3.add(i82, resolveInfo);
                            i6 = i3;
                            resolveInfo.specificIndex = i6;
                            i2 = i82 + 1;
                        }
                    }
                    i7 = i6 + 1;
                    queryIntentActivitiesInternal = list3;
                    action = str4;
                    str6 = null;
                    intentArr2 = intentArr;
                }
                list3 = list2;
                i2 = i4;
                i6 = i3;
                i7 = i6 + 1;
                queryIntentActivitiesInternal = list3;
                action = str4;
                str6 = null;
                intentArr2 = intentArr;
            }
            list = queryIntentActivitiesInternal;
            obj = action;
        }
        int size2 = list.size();
        while (i2 < size2 - 1) {
            android.content.pm.ResolveInfo resolveInfo3 = list.get(i2);
            if (resolveInfo3.filter != null && (actionsIterator = resolveInfo3.filter.actionsIterator()) != null) {
                while (actionsIterator.hasNext()) {
                    java.lang.String next = actionsIterator.next();
                    if (obj == null || !obj.equals(next)) {
                        int i9 = i2 + 1;
                        while (i9 < size2) {
                            android.content.pm.ResolveInfo resolveInfo4 = list.get(i9);
                            if (resolveInfo4.filter != null && resolveInfo4.filter.hasAction(next)) {
                                list.remove(i9);
                                i9--;
                                size2--;
                            }
                            i9++;
                        }
                    }
                }
                if ((updateFlagsForResolve & 64) == 0) {
                    resolveInfo3.filter = null;
                }
            }
            i2++;
        }
        if (componentName != null) {
            int size3 = list.size();
            int i10 = 0;
            while (true) {
                if (i10 >= size3) {
                    break;
                }
                android.content.pm.ActivityInfo activityInfo3 = list.get(i10).activityInfo;
                if (!componentName.getPackageName().equals(activityInfo3.applicationInfo.packageName) || !componentName.getClassName().equals(activityInfo3.name)) {
                    i10++;
                } else {
                    list.remove(i10);
                    break;
                }
            }
        }
        if ((updateFlagsForResolve & 64) == 0) {
            int size4 = list.size();
            for (int i11 = 0; i11 < size4; i11++) {
                list.get(i11).filter = null;
            }
        }
        return list;
    }
}
