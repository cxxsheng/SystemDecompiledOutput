package com.android.server.pm.resolution;

/* loaded from: classes2.dex */
public abstract class ComponentResolverBase extends com.android.server.utils.WatchableImpl implements com.android.server.pm.resolution.ComponentResolverApi {

    @android.annotation.NonNull
    protected com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver mActivities;

    @android.annotation.NonNull
    protected com.android.server.pm.resolution.ComponentResolver.ProviderIntentResolver mProviders;

    @android.annotation.NonNull
    protected android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProvider> mProvidersByAuthority;

    @android.annotation.NonNull
    protected com.android.server.pm.resolution.ComponentResolver.ReceiverIntentResolver mReceivers;

    @android.annotation.NonNull
    protected com.android.server.pm.resolution.ComponentResolver.ServiceIntentResolver mServices;

    @android.annotation.NonNull
    protected final com.android.server.pm.UserManagerService mUserManager;

    protected ComponentResolverBase(@android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService) {
        this.mUserManager = userManagerService;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public boolean componentExists(@android.annotation.NonNull android.content.ComponentName componentName) {
        return (this.mActivities.mActivities.get(componentName) == null && this.mReceivers.mActivities.get(componentName) == null && this.mServices.mServices.get(componentName) == null && this.mProviders.mProviders.get(componentName) == null) ? false : true;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedActivity getActivity(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mActivities.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedProvider getProvider(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mProviders.mProviders.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedActivity getReceiver(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mReceivers.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedService getService(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mServices.mServices.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public boolean isActivityDefined(@android.annotation.NonNull android.content.ComponentName componentName) {
        return this.mActivities.mActivities.get(componentName) != null;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        return this.mActivities.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i) {
        return this.mActivities.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public android.content.pm.ProviderInfo queryProvider(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, long j, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault;
        android.content.pm.ApplicationInfo generateApplicationInfo;
        com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = this.mProvidersByAuthority.get(str);
        if (parsedProvider == null || (packageStateInternal = computer.getPackageStateInternal(parsedProvider.getPackageName())) == null || (pkg = packageStateInternal.getPkg()) == null || (generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(pkg, j, (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)), i, packageStateInternal)) == null) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateProviderInfo(pkg, parsedProvider, j, userStateOrDefault, generateApplicationInfo, i, packageStateInternal);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        return this.mProviders.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> list, int i) {
        return this.mProviders.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ProviderInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, long j, int i2) {
        com.android.server.pm.parsing.PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator;
        android.content.pm.ProviderInfo generateProviderInfo;
        com.android.server.pm.parsing.PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator2 = null;
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        java.util.ArrayList arrayList = null;
        for (int size = this.mProviders.mProviders.size() - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ParsedProvider valueAt = this.mProviders.mProviders.valueAt(size);
            if (valueAt.getAuthority() != null) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(valueAt.getPackageName());
                if (packageStateInternal != null) {
                    com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
                    if (pkg != null) {
                        if (str != null) {
                            if (valueAt.getProcessName().equals(str)) {
                                if (!android.os.UserHandle.isSameApp(pkg.getUid(), i)) {
                                }
                            }
                        }
                        if (str2 == null || valueAt.getMetaData().containsKey(str2)) {
                            if (cachedApplicationInfoGenerator2 != null) {
                                cachedApplicationInfoGenerator = cachedApplicationInfoGenerator2;
                            } else {
                                cachedApplicationInfoGenerator = new com.android.server.pm.parsing.PackageInfoUtils.CachedApplicationInfoGenerator();
                            }
                            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i2);
                            android.content.pm.ApplicationInfo generate = cachedApplicationInfoGenerator.generate(pkg, j, userStateOrDefault, i2, packageStateInternal);
                            if (generate == null || (generateProviderInfo = com.android.server.pm.parsing.PackageInfoUtils.generateProviderInfo(pkg, valueAt, j, userStateOrDefault, generate, i2, packageStateInternal)) == null) {
                                cachedApplicationInfoGenerator2 = cachedApplicationInfoGenerator;
                            } else {
                                if (arrayList == null) {
                                    arrayList = new java.util.ArrayList(size + 1);
                                }
                                arrayList.add(generateProviderInfo);
                                cachedApplicationInfoGenerator2 = cachedApplicationInfoGenerator;
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        return this.mReceivers.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i) {
        return this.mReceivers.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i) {
        return this.mServices.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    @android.annotation.Nullable
    public java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedService> list, int i) {
        return this.mServices.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void querySyncProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<android.content.pm.ProviderInfo> list2, boolean z, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        android.content.pm.ProviderInfo generateProviderInfo;
        com.android.server.pm.parsing.PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator = null;
        for (int size = this.mProvidersByAuthority.size() - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ParsedProvider valueAt = this.mProvidersByAuthority.valueAt(size);
            if (valueAt.isSyncable() && (packageStateInternal = computer.getPackageStateInternal(valueAt.getPackageName())) != null && (pkg = packageStateInternal.getPkg()) != null && (!z || packageStateInternal.isSystem())) {
                if (cachedApplicationInfoGenerator == null) {
                    cachedApplicationInfoGenerator = new com.android.server.pm.parsing.PackageInfoUtils.CachedApplicationInfoGenerator();
                }
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                android.content.pm.ApplicationInfo generate = cachedApplicationInfoGenerator.generate(pkg, 0L, userStateOrDefault, i, packageStateInternal);
                if (generate != null && (generateProviderInfo = com.android.server.pm.parsing.PackageInfoUtils.generateProviderInfo(pkg, valueAt, 0L, userStateOrDefault, generate, i, packageStateInternal)) != null) {
                    list.add(this.mProvidersByAuthority.keyAt(size));
                    list2.add(generateProviderInfo);
                }
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpActivityResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        if (this.mActivities.dump(printWriter, dumpState.getTitlePrinted() ? "\nActivity Resolver Table:" : "Activity Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpProviderResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        if (this.mProviders.dump(printWriter, dumpState.getTitlePrinted() ? "\nProvider Resolver Table:" : "Provider Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpReceiverResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        if (this.mReceivers.dump(printWriter, dumpState.getTitlePrinted() ? "\nReceiver Resolver Table:" : "Receiver Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServiceResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        if (this.mServices.dump(printWriter, dumpState.getTitlePrinted() ? "\nService Resolver Table:" : "Service Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpContentProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str) {
        boolean z = false;
        boolean z2 = false;
        for (com.android.internal.pm.pkg.component.ParsedProvider parsedProvider : this.mProviders.mProviders.values()) {
            if (str == null || str.equals(parsedProvider.getPackageName())) {
                if (!z2) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("Registered ContentProviders:");
                    z2 = true;
                }
                printWriter.print("  ");
                android.content.ComponentName.printShortString(printWriter, parsedProvider.getPackageName(), parsedProvider.getName());
                printWriter.println(":");
                printWriter.print("    ");
                printWriter.println(parsedProvider.toString());
            }
        }
        for (java.util.Map.Entry<java.lang.String, com.android.internal.pm.pkg.component.ParsedProvider> entry : this.mProvidersByAuthority.entrySet()) {
            com.android.internal.pm.pkg.component.ParsedProvider value = entry.getValue();
            if (str == null || str.equals(value.getPackageName())) {
                if (!z) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("ContentProvider Authorities:");
                    z = true;
                }
                printWriter.print("  [");
                printWriter.print(entry.getKey());
                printWriter.println("]:");
                printWriter.print("    ");
                printWriter.println(value.toString());
                com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(value.getPackageName());
                if (androidPackage != null) {
                    printWriter.print("      applicationInfo=");
                    printWriter.println(com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
                }
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServicePermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        if (dumpState.onTitlePrinted()) {
            printWriter.println();
        }
        printWriter.println("Service permissions:");
        java.util.Iterator<F> filterIterator = this.mServices.filterIterator();
        while (filterIterator.hasNext()) {
            com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) ((android.util.Pair) filterIterator.next()).first;
            java.lang.String permission = parsedService.getPermission();
            if (permission != null) {
                printWriter.print("    ");
                printWriter.print(parsedService.getComponentName().flattenToShortString());
                printWriter.print(": ");
                printWriter.println(permission);
            }
        }
    }
}
