package com.android.server.compat;

/* loaded from: classes.dex */
public class PlatformCompat extends com.android.internal.compat.IPlatformCompat.Stub {
    private static final java.lang.String TAG = "Compatibility";
    private final com.android.internal.compat.AndroidBuildClassifier mBuildClassifier;
    private final com.android.internal.compat.ChangeReporter mChangeReporter;
    private final com.android.server.compat.CompatConfig mCompatConfig;
    private final android.content.Context mContext;

    public PlatformCompat(android.content.Context context) {
        this.mContext = context;
        this.mChangeReporter = new com.android.internal.compat.ChangeReporter(2);
        this.mBuildClassifier = new com.android.internal.compat.AndroidBuildClassifier();
        this.mCompatConfig = com.android.server.compat.CompatConfig.create(this.mBuildClassifier, this.mContext);
    }

    @com.android.internal.annotations.VisibleForTesting
    PlatformCompat(android.content.Context context, com.android.server.compat.CompatConfig compatConfig, com.android.internal.compat.AndroidBuildClassifier androidBuildClassifier) {
        this.mContext = context;
        this.mChangeReporter = new com.android.internal.compat.ChangeReporter(2);
        this.mCompatConfig = compatConfig;
        this.mBuildClassifier = androidBuildClassifier;
        registerPackageReceiver(context);
    }

    @android.annotation.EnforcePermission("android.permission.LOG_COMPAT_CHANGE")
    public void reportChange(long j, android.content.pm.ApplicationInfo applicationInfo) {
        super.reportChange_enforcePermission();
        reportChangeInternal(j, applicationInfo.uid, 3);
    }

    @android.annotation.EnforcePermission("android.permission.LOG_COMPAT_CHANGE")
    public void reportChangeByPackageName(long j, java.lang.String str, int i) {
        super.reportChangeByPackageName_enforcePermission();
        android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo(str, i);
        if (applicationInfo != null) {
            reportChangeInternal(j, applicationInfo.uid, 3);
        }
    }

    @android.annotation.EnforcePermission("android.permission.LOG_COMPAT_CHANGE")
    public void reportChangeByUid(long j, int i) {
        super.reportChangeByUid_enforcePermission();
        reportChangeInternal(j, i, 3);
    }

    private void reportChangeInternal(long j, int i, int i2) {
        this.mChangeReporter.reportChange(i, j, i2);
    }

    @android.annotation.EnforcePermission(allOf = {"android.permission.LOG_COMPAT_CHANGE", "android.permission.READ_COMPAT_CHANGE_CONFIG"})
    public boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) {
        super.isChangeEnabled_enforcePermission();
        return isChangeEnabledInternal(j, applicationInfo);
    }

    @android.annotation.EnforcePermission(allOf = {"android.permission.LOG_COMPAT_CHANGE", "android.permission.READ_COMPAT_CHANGE_CONFIG"})
    public boolean isChangeEnabledByPackageName(long j, java.lang.String str, int i) {
        super.isChangeEnabledByPackageName_enforcePermission();
        android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo(str, i);
        if (applicationInfo == null) {
            return this.mCompatConfig.willChangeBeEnabled(j, str);
        }
        return isChangeEnabledInternal(j, applicationInfo);
    }

    @android.annotation.EnforcePermission(allOf = {"android.permission.LOG_COMPAT_CHANGE", "android.permission.READ_COMPAT_CHANGE_CONFIG"})
    public boolean isChangeEnabledByUid(long j, int i) {
        super.isChangeEnabledByUid_enforcePermission();
        return isChangeEnabledByUidInternal(j, i);
    }

    public boolean isChangeEnabledInternalNoLogging(long j, android.content.pm.ApplicationInfo applicationInfo) {
        return this.mCompatConfig.isChangeEnabled(j, applicationInfo);
    }

    public boolean isChangeEnabledInternal(long j, android.content.pm.ApplicationInfo applicationInfo) {
        boolean isChangeEnabledInternalNoLogging = isChangeEnabledInternalNoLogging(j, applicationInfo);
        if (applicationInfo != null) {
            reportChangeInternal(j, applicationInfo.uid, isChangeEnabledInternalNoLogging ? 1 : 2);
        }
        return isChangeEnabledInternalNoLogging;
    }

    public boolean isChangeEnabledInternal(long j, java.lang.String str, int i) {
        if (this.mCompatConfig.willChangeBeEnabled(j, str)) {
            android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
            applicationInfo.packageName = str;
            applicationInfo.targetSdkVersion = i;
            return isChangeEnabledInternalNoLogging(j, applicationInfo);
        }
        return false;
    }

    public boolean isChangeEnabledByUidInternal(long j, int i) {
        java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            return this.mCompatConfig.defaultChangeIdValue(j);
        }
        int userId = android.os.UserHandle.getUserId(i);
        boolean z = true;
        for (java.lang.String str : packagesForUid) {
            z &= isChangeEnabledInternal(j, getApplicationInfo(str, userId));
        }
        return z;
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public void setOverrides(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) {
        super.setOverrides_enforcePermission();
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator it = compatibilityChangeConfig.enabledChanges().iterator();
        while (it.hasNext()) {
            hashMap.put(java.lang.Long.valueOf(((java.lang.Long) it.next()).longValue()), new android.app.compat.PackageOverride.Builder().setEnabled(true).build());
        }
        java.util.Iterator it2 = compatibilityChangeConfig.disabledChanges().iterator();
        while (it2.hasNext()) {
            hashMap.put(java.lang.Long.valueOf(((java.lang.Long) it2.next()).longValue()), new android.app.compat.PackageOverride.Builder().setEnabled(false).build());
        }
        this.mCompatConfig.addPackageOverrides(new com.android.internal.compat.CompatibilityOverrideConfig(hashMap), str, false);
        killPackage(str);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public void setOverridesForTest(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) {
        super.setOverridesForTest_enforcePermission();
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator it = compatibilityChangeConfig.enabledChanges().iterator();
        while (it.hasNext()) {
            hashMap.put(java.lang.Long.valueOf(((java.lang.Long) it.next()).longValue()), new android.app.compat.PackageOverride.Builder().setEnabled(true).build());
        }
        java.util.Iterator it2 = compatibilityChangeConfig.disabledChanges().iterator();
        while (it2.hasNext()) {
            hashMap.put(java.lang.Long.valueOf(((java.lang.Long) it2.next()).longValue()), new android.app.compat.PackageOverride.Builder().setEnabled(false).build());
        }
        this.mCompatConfig.addPackageOverrides(new com.android.internal.compat.CompatibilityOverrideConfig(hashMap), str, false);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD")
    public void putAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) {
        super.putAllOverridesOnReleaseBuilds_enforcePermission();
        java.util.Iterator it = compatibilityOverridesByPackageConfig.packageNameToOverrides.values().iterator();
        while (it.hasNext()) {
            checkAllCompatOverridesAreOverridable(((com.android.internal.compat.CompatibilityOverrideConfig) it.next()).overrides.keySet());
        }
        this.mCompatConfig.addAllPackageOverrides(compatibilityOverridesByPackageConfig, true);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD")
    public void putOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str) {
        super.putOverridesOnReleaseBuilds_enforcePermission();
        checkAllCompatOverridesAreOverridable(compatibilityOverrideConfig.overrides.keySet());
        this.mCompatConfig.addPackageOverrides(compatibilityOverrideConfig, str, true);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public int enableTargetSdkChanges(java.lang.String str, int i) {
        super.enableTargetSdkChanges_enforcePermission();
        int enableTargetSdkChangesForPackage = this.mCompatConfig.enableTargetSdkChangesForPackage(str, i);
        killPackage(str);
        return enableTargetSdkChangesForPackage;
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public int disableTargetSdkChanges(java.lang.String str, int i) {
        super.disableTargetSdkChanges_enforcePermission();
        int disableTargetSdkChangesForPackage = this.mCompatConfig.disableTargetSdkChangesForPackage(str, i);
        killPackage(str);
        return disableTargetSdkChangesForPackage;
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public void clearOverrides(java.lang.String str) {
        super.clearOverrides_enforcePermission();
        this.mCompatConfig.removePackageOverrides(str);
        killPackage(str);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public void clearOverridesForTest(java.lang.String str) {
        super.clearOverridesForTest_enforcePermission();
        this.mCompatConfig.removePackageOverrides(str);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public boolean clearOverride(long j, java.lang.String str) {
        super.clearOverride_enforcePermission();
        boolean removeOverride = this.mCompatConfig.removeOverride(j, str);
        killPackage(str);
        return removeOverride;
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG")
    public boolean clearOverrideForTest(long j, java.lang.String str) {
        super.clearOverrideForTest_enforcePermission();
        return this.mCompatConfig.removeOverride(j, str);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD")
    public void removeAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) {
        super.removeAllOverridesOnReleaseBuilds_enforcePermission();
        java.util.Iterator it = compatibilityOverridesToRemoveByPackageConfig.packageNameToOverridesToRemove.values().iterator();
        while (it.hasNext()) {
            checkAllCompatOverridesAreOverridable(((com.android.internal.compat.CompatibilityOverridesToRemoveConfig) it.next()).changeIds);
        }
        this.mCompatConfig.removeAllPackageOverrides(compatibilityOverridesToRemoveByPackageConfig);
    }

    @android.annotation.EnforcePermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD")
    public void removeOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) {
        super.removeOverridesOnReleaseBuilds_enforcePermission();
        checkAllCompatOverridesAreOverridable(compatibilityOverridesToRemoveConfig.changeIds);
        this.mCompatConfig.removePackageOverrides(compatibilityOverridesToRemoveConfig, str);
    }

    @android.annotation.EnforcePermission(allOf = {"android.permission.LOG_COMPAT_CHANGE", "android.permission.READ_COMPAT_CHANGE_CONFIG"})
    public com.android.internal.compat.CompatibilityChangeConfig getAppConfig(android.content.pm.ApplicationInfo applicationInfo) {
        super.getAppConfig_enforcePermission();
        return this.mCompatConfig.getAppConfig(applicationInfo);
    }

    @android.annotation.EnforcePermission("android.permission.READ_COMPAT_CHANGE_CONFIG")
    public com.android.internal.compat.CompatibilityChangeInfo[] listAllChanges() {
        super.listAllChanges_enforcePermission();
        return this.mCompatConfig.dumpChanges();
    }

    @android.annotation.RequiresNoPermission
    public com.android.internal.compat.CompatibilityChangeInfo[] listUIChanges() {
        return (com.android.internal.compat.CompatibilityChangeInfo[]) java.util.Arrays.stream(listAllChanges()).filter(new java.util.function.Predicate() { // from class: com.android.server.compat.PlatformCompat$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isShownInUI;
                isShownInUI = com.android.server.compat.PlatformCompat.this.isShownInUI((com.android.internal.compat.CompatibilityChangeInfo) obj);
                return isShownInUI;
            }
        }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.compat.PlatformCompat$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                com.android.internal.compat.CompatibilityChangeInfo[] lambda$listUIChanges$0;
                lambda$listUIChanges$0 = com.android.server.compat.PlatformCompat.lambda$listUIChanges$0(i);
                return lambda$listUIChanges$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.compat.CompatibilityChangeInfo[] lambda$listUIChanges$0(int i) {
        return new com.android.internal.compat.CompatibilityChangeInfo[i];
    }

    public boolean isKnownChangeId(long j) {
        return this.mCompatConfig.isKnownChangeId(j);
    }

    public long[] getDisabledChanges(android.content.pm.ApplicationInfo applicationInfo) {
        return this.mCompatConfig.getDisabledChanges(applicationInfo);
    }

    public long lookupChangeId(java.lang.String str) {
        return this.mCompatConfig.lookupChangeId(str);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, "platform_compat", printWriter)) {
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_COMPAT_CHANGE_CONFIG", "Cannot read compat change");
        this.mContext.enforceCallingOrSelfPermission("android.permission.LOG_COMPAT_CHANGE", "Cannot read log compat change usage");
        this.mCompatConfig.dumpConfig(printWriter);
    }

    @android.annotation.RequiresNoPermission
    public com.android.internal.compat.IOverrideValidator getOverrideValidator() {
        return this.mCompatConfig.getOverrideValidator();
    }

    public void resetReporting(android.content.pm.ApplicationInfo applicationInfo) {
        this.mChangeReporter.resetReportedChanges(applicationInfo.uid);
    }

    private android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i) {
        return ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getApplicationInfo(str, 0L, android.os.Process.myUid(), i);
    }

    private void killPackage(java.lang.String str) {
        int packageUid = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageUid(str, 0L, android.os.UserHandle.myUserId());
        if (packageUid < 0) {
            android.util.Slog.w(TAG, "Didn't find package " + str + " on device.");
            return;
        }
        android.util.Slog.d(TAG, "Killing package " + str + " (UID " + packageUid + ").");
        killUid(android.os.UserHandle.getAppId(packageUid));
    }

    private void killUid(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            if (service != null) {
                service.killUid(i, -1, "PlatformCompat overrides");
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    private void checkAllCompatOverridesAreOverridable(java.util.Collection<java.lang.Long> collection) {
        for (java.lang.Long l : collection) {
            if (isKnownChangeId(l.longValue()) && !this.mCompatConfig.isOverridable(l.longValue())) {
                throw new java.lang.SecurityException("Only change ids marked as Overridable can be overridden.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShownInUI(com.android.internal.compat.CompatibilityChangeInfo compatibilityChangeInfo) {
        if (compatibilityChangeInfo.getLoggingOnly() || compatibilityChangeInfo.getId() == 149391281) {
            return false;
        }
        if (compatibilityChangeInfo.getEnableSinceTargetSdk() > 0) {
            return compatibilityChangeInfo.getEnableSinceTargetSdk() >= 29 && compatibilityChangeInfo.getEnableSinceTargetSdk() <= this.mBuildClassifier.platformTargetSdk();
        }
        return true;
    }

    public boolean registerListener(long j, com.android.server.compat.CompatChange.ChangeListener changeListener) {
        return this.mCompatConfig.registerListener(j, changeListener);
    }

    public void registerPackageReceiver(android.content.Context context) {
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.compat.PlatformCompat.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                android.net.Uri data;
                java.lang.String schemeSpecificPart;
                if (intent == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                    return;
                }
                com.android.server.compat.PlatformCompat.this.mCompatConfig.recheckOverrides(schemeSpecificPart);
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null);
    }

    public void registerContentObserver() {
        this.mCompatConfig.registerContentObserver();
    }
}
