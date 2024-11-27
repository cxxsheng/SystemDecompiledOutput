package com.android.server.am;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ComponentAliasResolver {
    private static final java.lang.String ALIAS_FILTER_ACTION = "com.android.intent.action.EXPERIMENTAL_IS_ALIAS";
    private static final java.lang.String ALIAS_FILTER_ACTION_ALT = "android.intent.action.EXPERIMENTAL_IS_ALIAS";
    private static final boolean DEBUG = true;
    private static final java.lang.String META_DATA_ALIAS_TARGET = "alias_target";
    private static final java.lang.String OPT_IN_PROPERTY = "com.android.EXPERIMENTAL_COMPONENT_ALIAS_OPT_IN";
    private static final int PACKAGE_QUERY_FLAGS = 4989056;
    private static final java.lang.String TAG = "ComponentAliasResolver";
    public static final long USE_EXPERIMENTAL_COMPONENT_ALIAS = 196254758;
    private final com.android.server.am.ActivityManagerService mAm;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEnabledByDeviceConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mOverrideString;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.compat.PlatformCompat mPlatformCompat;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.content.ComponentName, android.content.ComponentName> mFromTo = new android.util.ArrayMap<>();
    final com.android.internal.content.PackageMonitor mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.am.ComponentAliasResolver.1
        public void onPackageModified(java.lang.String str) {
            com.android.server.am.ComponentAliasResolver.this.refresh();
        }

        public void onPackageAdded(java.lang.String str, int i) {
            com.android.server.am.ComponentAliasResolver.this.refresh();
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            com.android.server.am.ComponentAliasResolver.this.refresh();
        }
    };

    public ComponentAliasResolver(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
        this.mContext = activityManagerService.mContext;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        return z;
    }

    public void onSystemReady(boolean z, java.lang.String str) {
        synchronized (this.mLock) {
            this.mPlatformCompat = (com.android.server.compat.PlatformCompat) android.os.ServiceManager.getService("platform_compat");
        }
        android.util.Slog.d(TAG, "Compat listener set.");
        update(z, str);
    }

    public void update(boolean z, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (this.mPlatformCompat == null) {
                    return;
                }
                if (this.mEnabled) {
                    android.util.Slog.i(TAG, "Disabling component aliases...");
                    com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.ComponentAliasResolver.this.lambda$update$0();
                        }
                    });
                }
                this.mEnabled = false;
                this.mEnabledByDeviceConfig = z;
                this.mOverrideString = str;
                if (this.mEnabled) {
                    refreshLocked();
                } else {
                    this.mFromTo.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$update$0() {
        this.mPackageMonitor.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        synchronized (this.mLock) {
            update(this.mEnabledByDeviceConfig, this.mOverrideString);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void refreshLocked() {
        android.util.Slog.d(TAG, "Refreshing aliases...");
        this.mFromTo.clear();
        loadFromMetadataLocked();
        loadOverridesLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadFromMetadataLocked() {
        android.util.Slog.d(TAG, "Scanning service aliases...");
        loadFromMetadataLockedInner(new android.content.Intent(ALIAS_FILTER_ACTION_ALT));
        loadFromMetadataLockedInner(new android.content.Intent(ALIAS_FILTER_ACTION));
    }

    private void loadFromMetadataLockedInner(android.content.Intent intent) {
        extractAliasesLocked(this.mContext.getPackageManager().queryIntentServicesAsUser(intent, PACKAGE_QUERY_FLAGS, 0));
        android.util.Slog.d(TAG, "Scanning receiver aliases...");
        extractAliasesLocked(this.mContext.getPackageManager().queryBroadcastReceiversAsUser(intent, PACKAGE_QUERY_FLAGS, 0));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isEnabledForPackageLocked(java.lang.String str) {
        boolean z;
        try {
            z = this.mContext.getPackageManager().getProperty(OPT_IN_PROPERTY, str).getBoolean();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            z = false;
        }
        if (!z) {
            android.util.Slog.w(TAG, "USE_EXPERIMENTAL_COMPONENT_ALIAS not enabled for " + str);
        }
        return z;
    }

    private static boolean validateAlias(android.content.ComponentName componentName, android.content.ComponentName componentName2) {
        java.lang.String packageName = componentName.getPackageName();
        java.lang.String packageName2 = componentName2.getPackageName();
        if (java.util.Objects.equals(packageName, packageName2)) {
            return true;
        }
        if (packageName2.startsWith(packageName + ".")) {
            return true;
        }
        android.util.Slog.w(TAG, "Invalid alias: " + componentName.flattenToShortString() + " -> " + componentName2.flattenToShortString());
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void validateAndAddAliasLocked(android.content.ComponentName componentName, android.content.ComponentName componentName2) {
        android.util.Slog.d(TAG, "" + componentName.flattenToShortString() + " -> " + componentName2.flattenToShortString());
        if (!validateAlias(componentName, componentName2) || !isEnabledForPackageLocked(componentName.getPackageName()) || !isEnabledForPackageLocked(componentName2.getPackageName())) {
            return;
        }
        this.mFromTo.put(componentName, componentName2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void extractAliasesLocked(java.util.List<android.content.pm.ResolveInfo> list) {
        java.util.Iterator<android.content.pm.ResolveInfo> it = list.iterator();
        while (it.hasNext()) {
            android.content.pm.ComponentInfo componentInfo = it.next().getComponentInfo();
            android.content.ComponentName componentName = componentInfo.getComponentName();
            android.content.ComponentName unflatten = unflatten(componentInfo.metaData.getString(META_DATA_ALIAS_TARGET));
            if (unflatten != null) {
                validateAndAddAliasLocked(componentName, unflatten);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadOverridesLocked() {
        android.content.ComponentName unflatten;
        android.util.Slog.d(TAG, "Loading aliases overrides ...");
        for (java.lang.String str : this.mOverrideString.split("\\,+")) {
            java.lang.String[] split = str.split("\\:+", 2);
            if (!android.text.TextUtils.isEmpty(split[0]) && (unflatten = unflatten(split[0])) != null) {
                if (split.length == 1) {
                    android.util.Slog.d(TAG, "" + unflatten.flattenToShortString() + " [removed]");
                    this.mFromTo.remove(unflatten);
                } else {
                    android.content.ComponentName unflatten2 = unflatten(split[1]);
                    if (unflatten2 != null) {
                        validateAndAddAliasLocked(unflatten, unflatten2);
                    }
                }
            }
        }
    }

    private static android.content.ComponentName unflatten(java.lang.String str) {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return unflattenFromString;
        }
        android.util.Slog.e(TAG, "Invalid component name detected: " + str);
        return null;
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("ACTIVITY MANAGER COMPONENT-ALIAS (dumpsys activity component-alias)");
                printWriter.print("  Enabled: ");
                printWriter.println(this.mEnabled);
                printWriter.println("  Aliases:");
                for (int i = 0; i < this.mFromTo.size(); i++) {
                    android.content.ComponentName keyAt = this.mFromTo.keyAt(i);
                    android.content.ComponentName valueAt = this.mFromTo.valueAt(i);
                    printWriter.print("    ");
                    printWriter.print(keyAt.flattenToShortString());
                    printWriter.print(" -> ");
                    printWriter.print(valueAt.flattenToShortString());
                    printWriter.println();
                }
                printWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public static class Resolution<T> {

        @android.annotation.Nullable
        public final T resolved;

        @android.annotation.Nullable
        public final T source;

        public Resolution(T t, T t2) {
            this.source = t;
            this.resolved = t2;
        }

        @android.annotation.Nullable
        public boolean isAlias() {
            return this.resolved != null;
        }

        @android.annotation.Nullable
        public T getAlias() {
            if (isAlias()) {
                return this.source;
            }
            return null;
        }

        @android.annotation.Nullable
        public T getTarget() {
            if (isAlias()) {
                return this.resolved;
            }
            return null;
        }
    }

    @android.annotation.NonNull
    public com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolveComponentAlias(@android.annotation.NonNull java.util.function.Supplier<android.content.ComponentName> supplier) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!this.mEnabled) {
                    return new com.android.server.am.ComponentAliasResolver.Resolution<>(null, null);
                }
                android.content.ComponentName componentName = supplier.get();
                android.content.ComponentName componentName2 = this.mFromTo.get(componentName);
                if (componentName2 != null) {
                    android.util.Slog.d(TAG, "Alias resolved: " + componentName.flattenToShortString() + " -> " + componentName2.flattenToShortString(), android.util.Log.isLoggable(TAG, 2) ? new java.lang.RuntimeException("STACKTRACE") : null);
                }
                return new com.android.server.am.ComponentAliasResolver.Resolution<>(componentName, componentName2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolveService(@android.annotation.NonNull final android.content.Intent intent, @android.annotation.Nullable final java.lang.String str, final int i, final int i2, final int i3) {
        com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolveComponentAlias = resolveComponentAlias(new java.util.function.Supplier() { // from class: com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.ComponentName lambda$resolveService$1;
                lambda$resolveService$1 = com.android.server.am.ComponentAliasResolver.lambda$resolveService$1(intent, str, i, i2, i3);
                return lambda$resolveService$1;
            }
        });
        if (resolveComponentAlias != null && resolveComponentAlias.isAlias()) {
            intent.setOriginalIntent(new android.content.Intent(intent));
            intent.setPackage(null);
            intent.setComponent(resolveComponentAlias.getTarget());
        }
        return resolveComponentAlias;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.content.ComponentName lambda$resolveService$1(android.content.Intent intent, java.lang.String str, int i, int i2, int i3) {
        android.content.pm.ResolveInfo resolveService = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).resolveService(intent, str, i, i2, i3);
        android.content.pm.ServiceInfo serviceInfo = resolveService != null ? resolveService.serviceInfo : null;
        if (serviceInfo == null) {
            return null;
        }
        return new android.content.ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name);
    }

    @android.annotation.Nullable
    public com.android.server.am.ComponentAliasResolver.Resolution<android.content.pm.ResolveInfo> resolveReceiver(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull final android.content.pm.ResolveInfo resolveInfo, @android.annotation.Nullable java.lang.String str, long j, int i, int i2, boolean z) {
        com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolveComponentAlias = resolveComponentAlias(new java.util.function.Supplier() { // from class: com.android.server.am.ComponentAliasResolver$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.ComponentName lambda$resolveReceiver$2;
                lambda$resolveReceiver$2 = com.android.server.am.ComponentAliasResolver.lambda$resolveReceiver$2(resolveInfo);
                return lambda$resolveReceiver$2;
            }
        });
        android.content.ComponentName target = resolveComponentAlias.getTarget();
        if (target == null) {
            return new com.android.server.am.ComponentAliasResolver.Resolution<>(resolveInfo, null);
        }
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setPackage(null);
        intent2.setComponent(resolveComponentAlias.getTarget());
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers = packageManagerInternal.queryIntentReceivers(intent2, str, j, i2, i, z);
        if (queryIntentReceivers == null || queryIntentReceivers.size() == 0) {
            android.util.Slog.w(TAG, "Alias target " + target.flattenToShortString() + " not found");
            return null;
        }
        return new com.android.server.am.ComponentAliasResolver.Resolution<>(resolveInfo, queryIntentReceivers.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.content.ComponentName lambda$resolveReceiver$2(android.content.pm.ResolveInfo resolveInfo) {
        return resolveInfo.activityInfo.getComponentName();
    }
}
