package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class PolicyEnforcerCallbacks {
    private static final java.lang.String LOG_TAG = "PolicyEnforcerCallbacks";

    PolicyEnforcerCallbacks() {
    }

    static boolean setAutoTimezoneEnabled(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull final android.content.Context context) {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda8
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setAutoTimezoneEnabled$0;
                lambda$setAutoTimezoneEnabled$0 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setAutoTimezoneEnabled$0(context, bool);
                return lambda$setAutoTimezoneEnabled$0;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setAutoTimezoneEnabled$0(android.content.Context context, java.lang.Boolean bool) throws java.lang.Exception {
        java.util.Objects.requireNonNull(context);
        return java.lang.Boolean.valueOf(android.provider.Settings.Global.putInt(context.getContentResolver(), "auto_time_zone", (bool == null || !bool.booleanValue()) ? 0 : 1));
    }

    static boolean setPermissionGrantState(@android.annotation.Nullable final java.lang.Integer num, @android.annotation.NonNull final android.content.Context context, final int i, @android.annotation.NonNull final android.app.admin.PolicyKey policyKey) {
        return java.lang.Boolean.TRUE.equals(android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda9
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setPermissionGrantState$1;
                lambda$setPermissionGrantState$1 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setPermissionGrantState$1(policyKey, context, num, i);
                return lambda$setPermissionGrantState$1;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setPermissionGrantState$1(android.app.admin.PolicyKey policyKey, android.content.Context context, java.lang.Integer num, int i) throws java.lang.Exception {
        int intValue;
        if (!(policyKey instanceof android.app.admin.PackagePermissionPolicyKey)) {
            throw new java.lang.IllegalArgumentException("policyKey is not of type PermissionGrantStatePolicyKey, passed in policyKey is: " + policyKey);
        }
        android.app.admin.PackagePermissionPolicyKey packagePermissionPolicyKey = (android.app.admin.PackagePermissionPolicyKey) policyKey;
        java.util.Objects.requireNonNull(packagePermissionPolicyKey.getPermissionName());
        java.util.Objects.requireNonNull(packagePermissionPolicyKey.getPackageName());
        java.util.Objects.requireNonNull(context);
        if (num == null) {
            intValue = 0;
        } else {
            intValue = num.intValue();
        }
        final com.android.server.devicepolicy.PolicyEnforcerCallbacks.BlockingCallback blockingCallback = new com.android.server.devicepolicy.PolicyEnforcerCallbacks.BlockingCallback();
        android.permission.AdminPermissionControlParams adminPermissionControlParams = new android.permission.AdminPermissionControlParams(packagePermissionPolicyKey.getPackageName(), packagePermissionPolicyKey.getPermissionName(), intValue, true);
        android.permission.PermissionControllerManager permissionControllerManager = getPermissionControllerManager(context, android.os.UserHandle.of(i));
        java.lang.String packageName = context.getPackageName();
        java.util.concurrent.Executor mainExecutor = context.getMainExecutor();
        java.util.Objects.requireNonNull(blockingCallback);
        permissionControllerManager.setRuntimePermissionGrantStateByDeviceAdmin(packageName, adminPermissionControlParams, mainExecutor, new java.util.function.Consumer() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.BlockingCallback.this.trigger((java.lang.Boolean) obj);
            }
        });
        try {
            return blockingCallback.await(20000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @android.annotation.NonNull
    private static android.permission.PermissionControllerManager getPermissionControllerManager(android.content.Context context, android.os.UserHandle userHandle) {
        if (userHandle.equals(context.getUser())) {
            return (android.permission.PermissionControllerManager) context.getSystemService(android.permission.PermissionControllerManager.class);
        }
        try {
            return (android.permission.PermissionControllerManager) context.createPackageContextAsUser(context.getPackageName(), 0, userHandle).getSystemService(android.permission.PermissionControllerManager.class);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    static boolean enforceSecurityLogging(@android.annotation.Nullable java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.app.admin.PolicyKey policyKey) {
        ((android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class)).enforceSecurityLoggingPolicy(java.lang.Boolean.TRUE.equals(bool));
        return true;
    }

    static boolean enforceAuditLogging(@android.annotation.Nullable java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.app.admin.PolicyKey policyKey) {
        ((android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class)).enforceAuditLoggingPolicy(java.lang.Boolean.TRUE.equals(bool));
        return true;
    }

    static boolean setLockTask(@android.annotation.Nullable android.app.admin.LockTaskPolicy lockTaskPolicy, @android.annotation.NonNull android.content.Context context, int i) {
        int i2;
        java.util.List emptyList = java.util.Collections.emptyList();
        if (lockTaskPolicy == null) {
            i2 = 16;
        } else {
            emptyList = java.util.List.copyOf(lockTaskPolicy.getPackages());
            i2 = lockTaskPolicy.getFlags();
        }
        com.android.server.devicepolicy.DevicePolicyManagerService.updateLockTaskPackagesLocked(context, emptyList, i);
        com.android.server.devicepolicy.DevicePolicyManagerService.updateLockTaskFeaturesLocked(i2, i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BlockingCallback {
        private final java.util.concurrent.CountDownLatch mLatch;
        private final java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> mValue;

        private BlockingCallback() {
            this.mLatch = new java.util.concurrent.CountDownLatch(1);
            this.mValue = new java.util.concurrent.atomic.AtomicReference<>();
        }

        public void trigger(java.lang.Boolean bool) {
            this.mValue.set(bool);
            this.mLatch.countDown();
        }

        public java.lang.Boolean await(long j, java.util.concurrent.TimeUnit timeUnit) throws java.lang.InterruptedException {
            if (!this.mLatch.await(j, timeUnit)) {
                com.android.server.utils.Slogf.e(com.android.server.devicepolicy.PolicyEnforcerCallbacks.LOG_TAG, "Callback was not received");
            }
            return this.mValue.get();
        }
    }

    static boolean setUserControlDisabledPackages(@android.annotation.Nullable final java.util.Set<java.lang.String> set, final int i) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setUserControlDisabledPackages$2(i, set);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setUserControlDisabledPackages$2(int i, java.util.Set set) throws java.lang.Exception {
        ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).setOwnerProtectedPackages(i, set == null ? null : set.stream().toList());
        ((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)).setAdminProtectedPackages(set != null ? new android.util.ArraySet(set) : null, i);
    }

    static boolean addPersistentPreferredActivity(@android.annotation.Nullable final android.content.ComponentName componentName, @android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull final android.app.admin.PolicyKey policyKey) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda12
            public final void runOrThrow() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$addPersistentPreferredActivity$3(policyKey, componentName, i);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addPersistentPreferredActivity$3(android.app.admin.PolicyKey policyKey, android.content.ComponentName componentName, int i) throws java.lang.Exception {
        try {
            if (!(policyKey instanceof android.app.admin.IntentFilterPolicyKey)) {
                throw new java.lang.IllegalArgumentException("policyKey is not of type IntentFilterPolicyKey, passed in policyKey is: " + policyKey);
            }
            android.content.IntentFilter intentFilter = ((android.app.admin.IntentFilterPolicyKey) policyKey).getIntentFilter();
            java.util.Objects.requireNonNull(intentFilter);
            android.content.IntentFilter intentFilter2 = intentFilter;
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            if (componentName != null) {
                packageManager.addPersistentPreferredActivity(intentFilter, componentName, i);
            } else {
                packageManager.clearPersistentPreferredActivity(intentFilter, i);
            }
            packageManager.flushPackageRestrictionsAsUser(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(LOG_TAG, "Error adding/removing persistent preferred activity", e);
        }
    }

    static boolean setUninstallBlocked(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull final android.app.admin.PolicyKey policyKey) {
        return java.lang.Boolean.TRUE.equals(android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda10
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setUninstallBlocked$4;
                lambda$setUninstallBlocked$4 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setUninstallBlocked$4(policyKey, bool, i);
                return lambda$setUninstallBlocked$4;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setUninstallBlocked$4(android.app.admin.PolicyKey policyKey, java.lang.Boolean bool, int i) throws java.lang.Exception {
        if (!(policyKey instanceof android.app.admin.PackagePolicyKey)) {
            throw new java.lang.IllegalArgumentException("policyKey is not of type PackagePolicyKey, passed in policyKey is: " + policyKey);
        }
        java.lang.String packageName = ((android.app.admin.PackagePolicyKey) policyKey).getPackageName();
        java.util.Objects.requireNonNull(packageName);
        com.android.server.devicepolicy.DevicePolicyManagerService.setUninstallBlockedUnchecked(packageName, bool != null && bool.booleanValue(), i);
        return true;
    }

    static boolean setUserRestriction(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull final android.app.admin.PolicyKey policyKey) {
        return java.lang.Boolean.TRUE.equals(android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda5
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setUserRestriction$5;
                lambda$setUserRestriction$5 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setUserRestriction$5(policyKey, i, bool);
                return lambda$setUserRestriction$5;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setUserRestriction$5(android.app.admin.PolicyKey policyKey, int i, java.lang.Boolean bool) throws java.lang.Exception {
        if (!(policyKey instanceof android.app.admin.UserRestrictionPolicyKey)) {
            throw new java.lang.IllegalArgumentException("policyKey is not of type UserRestrictionPolicyKey, passed in policyKey is: " + policyKey);
        }
        ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).setUserRestriction(i, ((android.app.admin.UserRestrictionPolicyKey) policyKey).getRestriction(), bool != null && bool.booleanValue());
        return true;
    }

    static boolean setApplicationHidden(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull final android.app.admin.PolicyKey policyKey) {
        return java.lang.Boolean.TRUE.equals(android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda6
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setApplicationHidden$6;
                lambda$setApplicationHidden$6 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setApplicationHidden$6(policyKey, bool, i);
                return lambda$setApplicationHidden$6;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setApplicationHidden$6(android.app.admin.PolicyKey policyKey, java.lang.Boolean bool, int i) throws java.lang.Exception {
        if (!(policyKey instanceof android.app.admin.PackagePolicyKey)) {
            throw new java.lang.IllegalArgumentException("policyKey is not of type PackagePolicyKey, passed in policyKey is: " + policyKey);
        }
        java.lang.String packageName = ((android.app.admin.PackagePolicyKey) policyKey).getPackageName();
        java.util.Objects.requireNonNull(packageName);
        return java.lang.Boolean.valueOf(android.app.AppGlobals.getPackageManager().setApplicationHiddenSettingAsUser(packageName, bool != null && bool.booleanValue(), i));
    }

    static boolean setScreenCaptureDisabled(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull android.content.Context context, final int i, @android.annotation.NonNull android.app.admin.PolicyKey policyKey) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setScreenCaptureDisabled$7(i, bool);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setScreenCaptureDisabled$7(int i, java.lang.Boolean bool) throws java.lang.Exception {
        android.app.admin.DevicePolicyCache devicePolicyCache = android.app.admin.DevicePolicyCache.getInstance();
        if (devicePolicyCache instanceof com.android.server.devicepolicy.DevicePolicyCacheImpl) {
            ((com.android.server.devicepolicy.DevicePolicyCacheImpl) devicePolicyCache).setScreenCaptureDisallowedUser(i, bool != null && bool.booleanValue());
            updateScreenCaptureDisabled();
        }
    }

    static boolean setContentProtectionPolicy(@android.annotation.Nullable final java.lang.Integer num, @android.annotation.NonNull android.content.Context context, final java.lang.Integer num2, @android.annotation.NonNull android.app.admin.PolicyKey policyKey) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setContentProtectionPolicy$8(num2, num);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setContentProtectionPolicy$8(java.lang.Integer num, java.lang.Integer num2) throws java.lang.Exception {
        android.app.admin.DevicePolicyCache devicePolicyCache = android.app.admin.DevicePolicyCache.getInstance();
        if (devicePolicyCache instanceof com.android.server.devicepolicy.DevicePolicyCacheImpl) {
            ((com.android.server.devicepolicy.DevicePolicyCacheImpl) devicePolicyCache).setContentProtectionPolicy(num.intValue(), num2);
        }
    }

    private static void updateScreenCaptureDisabled() {
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$updateScreenCaptureDisabled$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateScreenCaptureDisabled$9() {
        try {
            android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService("window")).refreshScreenCaptureDisabled();
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.w(LOG_TAG, "Unable to notify WindowManager.", e);
        }
    }

    static boolean setPersonalAppsSuspended(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull final android.content.Context context, final int i, @android.annotation.NonNull android.app.admin.PolicyKey policyKey) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setPersonalAppsSuspended$10(bool, context, i);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setPersonalAppsSuspended$10(java.lang.Boolean bool, android.content.Context context, int i) throws java.lang.Exception {
        if (bool != null && bool.booleanValue()) {
            suspendPersonalAppsInPackageManager(context, i);
        } else {
            ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).unsuspendAdminSuspendedPackages(i);
        }
    }

    private static void suspendPersonalAppsInPackageManager(android.content.Context context, int i) {
        java.lang.String[] packagesSuspendedByAdmin = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).setPackagesSuspendedByAdmin(i, com.android.server.devicepolicy.PersonalAppsSuspensionHelper.forUser(context, i).getPersonalAppsForSuspension(), true);
        if (!com.android.internal.util.ArrayUtils.isEmpty(packagesSuspendedByAdmin)) {
            com.android.server.utils.Slogf.wtf(LOG_TAG, "Failed to suspend apps: " + java.lang.String.join(",", packagesSuspendedByAdmin));
        }
    }

    static boolean setUsbDataSignalingEnabled(@android.annotation.Nullable final java.lang.Boolean bool, @android.annotation.NonNull final android.content.Context context) {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.devicepolicy.PolicyEnforcerCallbacks$$ExternalSyntheticLambda4
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$setUsbDataSignalingEnabled$11;
                lambda$setUsbDataSignalingEnabled$11 = com.android.server.devicepolicy.PolicyEnforcerCallbacks.lambda$setUsbDataSignalingEnabled$11(context, bool);
                return lambda$setUsbDataSignalingEnabled$11;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$setUsbDataSignalingEnabled$11(android.content.Context context, java.lang.Boolean bool) throws java.lang.Exception {
        java.util.Objects.requireNonNull(context);
        com.android.server.devicepolicy.DevicePolicyManagerService.updateUsbDataSignal(context, bool == null || bool.booleanValue());
        return true;
    }
}
