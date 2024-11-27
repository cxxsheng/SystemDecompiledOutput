package android.permission;

/* loaded from: classes3.dex */
public final class LegacyPermissionManager {
    private final android.permission.ILegacyPermissionManager mLegacyPermissionManager;

    public LegacyPermissionManager() throws android.os.ServiceManager.ServiceNotFoundException {
        this(android.permission.ILegacyPermissionManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.LEGACY_PERMISSION_SERVICE)));
    }

    public LegacyPermissionManager(android.permission.ILegacyPermissionManager iLegacyPermissionManager) {
        this.mLegacyPermissionManager = iLegacyPermissionManager;
    }

    public int checkDeviceIdentifierAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
        try {
            return this.mLegacyPermissionManager.checkDeviceIdentifierAccess(str, str2, str3, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkPhoneNumberAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
        try {
            return this.mLegacyPermissionManager.checkPhoneNumberAccess(str, str2, str3, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToLuiApp(java.lang.String str, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToActiveLuiApp(str, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void revokeDefaultPermissionsFromLuiApps(java.lang.String[] strArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.revokeDefaultPermissionsFromLuiApps(strArr, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledImsServices(java.lang.String[] strArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledImsServices(strArr, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(java.lang.String[] strArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledTelephonyDataServices(strArr, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(java.lang.String[] strArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.revokeDefaultPermissionsFromDisabledTelephonyDataServices(strArr, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledCarrierApps(java.lang.String[] strArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledCarrierApps(strArr, userHandle.getIdentifier());
            executor.execute(new java.lang.Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToCarrierServiceApp(java.lang.String str, int i) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToCarrierServiceApp(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
