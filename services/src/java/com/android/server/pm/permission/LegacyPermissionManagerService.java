package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class LegacyPermissionManagerService extends android.permission.ILegacyPermissionManager.Stub {
    private static final java.lang.String TAG = "PermissionManager";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.pm.permission.DefaultPermissionGrantPolicy mDefaultPermissionGrantPolicy;
    private final com.android.server.pm.permission.LegacyPermissionManagerService.Injector mInjector;

    @android.annotation.NonNull
    public static com.android.server.pm.permission.LegacyPermissionManagerInternal create(@android.annotation.NonNull android.content.Context context) {
        com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
        if (legacyPermissionManagerInternal == null) {
            new com.android.server.pm.permission.LegacyPermissionManagerService(context);
            return (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
        }
        return legacyPermissionManagerInternal;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private LegacyPermissionManagerService(@android.annotation.NonNull android.content.Context context) {
        this(context, new com.android.server.pm.permission.LegacyPermissionManagerService.Injector(context));
        com.android.server.LocalServices.addService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class, new com.android.server.pm.permission.LegacyPermissionManagerService.Internal());
        android.os.ServiceManager.addService("legacy_permission", this);
    }

    @com.android.internal.annotations.VisibleForTesting
    LegacyPermissionManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionManagerService.Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mDefaultPermissionGrantPolicy = new com.android.server.pm.permission.DefaultPermissionGrantPolicy(context);
    }

    public int checkDeviceIdentifierAccess(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, int i2) {
        verifyCallerCanCheckAccess(str, str2, i, i2);
        int appId = android.os.UserHandle.getAppId(i2);
        if (appId == 1000 || appId == 0 || this.mInjector.checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", i, i2) == 0) {
            return 0;
        }
        if (str != null) {
            long clearCallingIdentity = this.mInjector.clearCallingIdentity();
            try {
                if (((android.app.AppOpsManager) this.mInjector.getSystemService("appops")).noteOpNoThrow("android:read_device_identifiers", i2, str, str3, str2) == 0) {
                    return 0;
                }
                this.mInjector.restoreCallingIdentity(clearCallingIdentity);
                android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) this.mInjector.getSystemService("device_policy");
                return (devicePolicyManager == null || !devicePolicyManager.hasDeviceIdentifierAccess(str, i, i2)) ? -1 : 0;
            } finally {
                this.mInjector.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return -1;
    }

    public int checkPhoneNumberAccess(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, int i2) {
        boolean z;
        int i3;
        verifyCallerCanCheckAccess(str, str2, i, i2);
        if (this.mInjector.checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", i, i2) == 0) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        try {
            z = this.mInjector.getApplicationInfo(str, i2).targetSdkVersion <= 29;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            z = false;
        }
        if (!z) {
            i3 = -1;
        } else {
            int checkPermissionAndAppop = checkPermissionAndAppop(str, "android.permission.READ_PHONE_STATE", "android:read_phone_state", str3, str2, i, i2);
            if (checkPermissionAndAppop != 0) {
                i3 = checkPermissionAndAppop;
            } else {
                return checkPermissionAndAppop;
            }
        }
        if (checkPermissionAndAppop(str, null, "android:write_sms", str3, str2, i, i2) == 0 || checkPermissionAndAppop(str, "android.permission.READ_PHONE_NUMBERS", "android:read_phone_numbers", str3, str2, i, i2) == 0 || checkPermissionAndAppop(str, "android.permission.READ_SMS", "android:read_sms", str3, str2, i, i2) == 0) {
            return 0;
        }
        return i3;
    }

    private void verifyCallerCanCheckAccess(java.lang.String str, java.lang.String str2, int i, int i2) {
        boolean z;
        int callingUid = this.mInjector.getCallingUid();
        int callingPid = this.mInjector.getCallingPid();
        boolean z2 = true;
        if (android.os.UserHandle.getAppId(callingUid) >= 10000 && (callingUid != i2 || callingPid != i)) {
            z = true;
        } else {
            z = false;
        }
        if (str != null && android.os.UserHandle.getAppId(i2) >= 10000 && i2 != this.mInjector.getPackageUidForUser(str, android.os.UserHandle.getUserId(i2))) {
            android.util.EventLog.writeEvent(1397638484, "193441322", java.lang.Integer.valueOf(android.os.UserHandle.getAppId(callingUid) >= 10000 ? callingUid : i2), "Package uid mismatch");
        } else {
            z2 = z;
        }
        if (z2) {
            java.lang.String format = java.lang.String.format("Calling uid %d, pid %d cannot access for package %s (uid=%d, pid=%d): %s", java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(callingPid), str, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i), str2);
            android.util.Log.w(TAG, format);
            throw new java.lang.SecurityException(format);
        }
    }

    private int checkPermissionAndAppop(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i, int i2) {
        if (str2 != null && this.mInjector.checkPermission(str2, i, i2) != 0) {
            return -1;
        }
        if (((android.app.AppOpsManager) this.mInjector.getSystemService("appops")).noteOpNoThrow(str3, i2, str, str4, str5) != 0) {
            return 1;
        }
        return 0;
    }

    public void grantDefaultPermissionsToCarrierServiceApp(@android.annotation.NonNull final java.lang.String str, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("grantDefaultPermissionsForCarrierServiceApp");
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$grantDefaultPermissionsToCarrierServiceApp$0(str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantDefaultPermissionsToCarrierServiceApp$0(java.lang.String str, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToCarrierServiceApp(str, i);
    }

    public void grantDefaultPermissionsToActiveLuiApp(final java.lang.String str, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("grantDefaultPermissionsToActiveLuiApp", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$grantDefaultPermissionsToActiveLuiApp$1(str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantDefaultPermissionsToActiveLuiApp$1(java.lang.String str, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToActiveLuiApp(str, i);
    }

    public void revokeDefaultPermissionsFromLuiApps(final java.lang.String[] strArr, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("revokeDefaultPermissionsFromLuiApps", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$revokeDefaultPermissionsFromLuiApps$2(strArr, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeDefaultPermissionsFromLuiApps$2(java.lang.String[] strArr, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.revokeDefaultPermissionsFromLuiApps(strArr, i);
    }

    public void grantDefaultPermissionsToEnabledImsServices(final java.lang.String[] strArr, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("grantDefaultPermissionsToEnabledImsServices", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$grantDefaultPermissionsToEnabledImsServices$3(strArr, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantDefaultPermissionsToEnabledImsServices$3(java.lang.String[] strArr, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToEnabledImsServices(strArr, i);
    }

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(final java.lang.String[] strArr, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("grantDefaultPermissionsToEnabledTelephonyDataServices", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$grantDefaultPermissionsToEnabledTelephonyDataServices$4(strArr, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantDefaultPermissionsToEnabledTelephonyDataServices$4(java.lang.String[] strArr, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToEnabledTelephonyDataServices(strArr, i);
    }

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(final java.lang.String[] strArr, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("revokeDefaultPermissionsFromDisabledTelephonyDataServices", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda6
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$revokeDefaultPermissionsFromDisabledTelephonyDataServices$5(strArr, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeDefaultPermissionsFromDisabledTelephonyDataServices$5(java.lang.String[] strArr, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.revokeDefaultPermissionsFromDisabledTelephonyDataServices(strArr, i);
    }

    public void grantDefaultPermissionsToEnabledCarrierApps(final java.lang.String[] strArr, final int i) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrPhoneCaller("grantPermissionsToEnabledCarrierApps", android.os.Binder.getCallingUid());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.lambda$grantDefaultPermissionsToEnabledCarrierApps$6(strArr, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantDefaultPermissionsToEnabledCarrierApps$6(java.lang.String[] strArr, int i) throws java.lang.Exception {
        this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToEnabledCarrierApps(strArr, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Internal implements com.android.server.pm.permission.LegacyPermissionManagerInternal {
        private Internal() {
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void resetRuntimePermissions() {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", "revokeRuntimePermission");
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 1000 && callingUid != 0) {
                com.android.server.pm.permission.LegacyPermissionManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "resetRuntimePermissions");
            }
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            final com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
            for (final int i : com.android.server.pm.UserManagerService.getInstance().getUserIds()) {
                packageManagerInternal.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.LegacyPermissionManagerService$Internal$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.permission.PermissionManagerServiceInternal.this.resetRuntimePermissions((com.android.server.pm.pkg.AndroidPackage) obj, i);
                    }
                });
            }
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setDialerAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setDialerAppPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setLocationExtraPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setLocationExtraPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setLocationPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setLocationPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setSimCallManagerPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setSimCallManagerPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setSmsAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setSmsAppPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setSyncAdapterPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider syncAdapterPackagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setSyncAdapterPackagesProvider(syncAdapterPackagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setUseOpenWifiAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setUseOpenWifiAppPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void setVoiceInteractionPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.setVoiceInteractionPackagesProvider(packagesProvider);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void grantDefaultPermissionsToDefaultSimCallManager(java.lang.String str, int i) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToDefaultSimCallManager(str, i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void grantDefaultPermissionsToDefaultUseOpenWifiApp(java.lang.String str, int i) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.grantDefaultPermissionsToDefaultUseOpenWifiApp(str, i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void grantDefaultPermissions(int i) {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.grantDefaultPermissions(i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public void scheduleReadDefaultPermissionExceptions() {
            com.android.server.pm.permission.LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy.scheduleReadDefaultPermissionExceptions();
        }

        @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal
        public int checkSoundTriggerRecordAudioPermissionForDataDelivery(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
            int checkPermissionForPreflight = android.content.PermissionChecker.checkPermissionForPreflight(com.android.server.pm.permission.LegacyPermissionManagerService.this.mContext, "android.permission.RECORD_AUDIO", -1, i, str);
            if (checkPermissionForPreflight != 0) {
                return checkPermissionForPreflight;
            }
            ((android.app.AppOpsManager) com.android.server.pm.permission.LegacyPermissionManagerService.this.mContext.getSystemService(android.app.AppOpsManager.class)).noteOpNoThrow(120, i, str, str2, str3);
            return checkPermissionForPreflight;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        private final android.content.Context mContext;
        private final android.content.pm.PackageManagerInternal mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);

        public Injector(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        public int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        public int getCallingPid() {
            return android.os.Binder.getCallingPid();
        }

        public int checkPermission(@android.annotation.NonNull java.lang.String str, int i, int i2) {
            return this.mContext.checkPermission(str, i, i2);
        }

        public long clearCallingIdentity() {
            return android.os.Binder.clearCallingIdentity();
        }

        public void restoreCallingIdentity(long j) {
            android.os.Binder.restoreCallingIdentity(j);
        }

        public java.lang.Object getSystemService(@android.annotation.NonNull java.lang.String str) {
            return this.mContext.getSystemService(str);
        }

        public android.content.pm.ApplicationInfo getApplicationInfo(@android.annotation.Nullable java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
            return this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserHandleForUid(i));
        }

        public int getPackageUidForUser(java.lang.String str, int i) {
            return this.mPackageManagerInternal.getPackageUid(str, 0L, i);
        }
    }
}
