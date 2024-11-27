package com.android.server.oemlock;

/* loaded from: classes2.dex */
public class OemLockService extends com.android.server.SystemService {
    private static final java.lang.String FLASH_LOCK_PROP = "ro.boot.flash.locked";
    private static final java.lang.String FLASH_LOCK_UNLOCKED = "0";
    private static final java.lang.String TAG = "OemLock";
    private android.content.Context mContext;
    private com.android.server.oemlock.OemLock mOemLock;
    private final android.os.IBinder mService;
    private final com.android.server.pm.UserManagerInternal.UserRestrictionsListener mUserRestrictionsListener;

    public static boolean isHalPresent() {
        return (com.android.server.oemlock.VendorLockHidl.getOemLockHalService() == null && com.android.server.oemlock.VendorLockAidl.getOemLockHalService() == null) ? false : true;
    }

    private static com.android.server.oemlock.OemLock getOemLock(android.content.Context context) {
        if (com.android.server.oemlock.VendorLockAidl.getOemLockHalService() != null) {
            android.util.Slog.i(TAG, "Using vendor lock via the HAL(aidl)");
            return new com.android.server.oemlock.VendorLockAidl(context);
        }
        if (com.android.server.oemlock.VendorLockHidl.getOemLockHalService() != null) {
            android.util.Slog.i(TAG, "Using vendor lock via the HAL(hidl)");
            return new com.android.server.oemlock.VendorLockHidl(context);
        }
        android.util.Slog.i(TAG, "Using persistent data block based lock");
        return new com.android.server.oemlock.PersistentDataBlockLock(context);
    }

    public OemLockService(android.content.Context context) {
        this(context, getOemLock(context));
    }

    OemLockService(android.content.Context context, com.android.server.oemlock.OemLock oemLock) {
        super(context);
        this.mUserRestrictionsListener = new com.android.server.pm.UserManagerInternal.UserRestrictionsListener() { // from class: com.android.server.oemlock.OemLockService.1
            @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
            public void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
                if (com.android.server.pm.UserRestrictionsUtils.restrictionsChanged(bundle2, bundle, "no_factory_reset") && !(!bundle.getBoolean("no_factory_reset"))) {
                    com.android.server.oemlock.OemLockService.this.mOemLock.setOemUnlockAllowedByDevice(false);
                    com.android.server.oemlock.OemLockService.this.setPersistentDataBlockOemUnlockAllowedBit(false);
                }
            }
        };
        this.mService = new android.service.oemlock.IOemLockService.Stub() { // from class: com.android.server.oemlock.OemLockService.2
            @android.annotation.EnforcePermission("android.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE")
            @android.annotation.Nullable
            public java.lang.String getLockName() {
                super.getLockName_enforcePermission();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.oemlock.OemLockService.this.mOemLock.getLockName();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.EnforcePermission("android.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE")
            public void setOemUnlockAllowedByCarrier(boolean z, @android.annotation.Nullable byte[] bArr) {
                super.setOemUnlockAllowedByCarrier_enforcePermission();
                com.android.server.oemlock.OemLockService.this.enforceUserIsAdmin();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.oemlock.OemLockService.this.mOemLock.setOemUnlockAllowedByCarrier(z, bArr);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.EnforcePermission("android.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE")
            public boolean isOemUnlockAllowedByCarrier() {
                super.isOemUnlockAllowedByCarrier_enforcePermission();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.oemlock.OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.EnforcePermission("android.permission.MANAGE_USER_OEM_UNLOCK_STATE")
            public void setOemUnlockAllowedByUser(boolean z) {
                super.setOemUnlockAllowedByUser_enforcePermission();
                if (android.app.ActivityManager.isUserAMonkey()) {
                    return;
                }
                com.android.server.oemlock.OemLockService.this.enforceUserIsAdmin();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.oemlock.OemLockService.this.isOemUnlockAllowedByAdmin()) {
                        throw new java.lang.SecurityException("Admin does not allow OEM unlock");
                    }
                    if (!com.android.server.oemlock.OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier()) {
                        throw new java.lang.SecurityException("Carrier does not allow OEM unlock");
                    }
                    com.android.server.oemlock.OemLockService.this.mOemLock.setOemUnlockAllowedByDevice(z);
                    com.android.server.oemlock.OemLockService.this.setPersistentDataBlockOemUnlockAllowedBit(z);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.EnforcePermission("android.permission.MANAGE_USER_OEM_UNLOCK_STATE")
            public boolean isOemUnlockAllowedByUser() {
                super.isOemUnlockAllowedByUser_enforcePermission();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.oemlock.OemLockService.this.mOemLock.isOemUnlockAllowedByDevice();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.EnforcePermission(anyOf = {"android.permission.READ_OEM_UNLOCK_STATE", "android.permission.OEM_UNLOCK_STATE"})
            public boolean isOemUnlockAllowed() {
                super.isOemUnlockAllowed_enforcePermission();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    boolean z = com.android.server.oemlock.OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier() && com.android.server.oemlock.OemLockService.this.mOemLock.isOemUnlockAllowedByDevice();
                    com.android.server.oemlock.OemLockService.this.setPersistentDataBlockOemUnlockAllowedBit(z);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }

            @android.annotation.EnforcePermission(anyOf = {"android.permission.READ_OEM_UNLOCK_STATE", "android.permission.OEM_UNLOCK_STATE"})
            public boolean isDeviceOemUnlocked() {
                char c;
                super.isDeviceOemUnlocked_enforcePermission();
                java.lang.String str = android.os.SystemProperties.get(com.android.server.oemlock.OemLockService.FLASH_LOCK_PROP);
                switch (str.hashCode()) {
                    case 48:
                        if (str.equals(com.android.server.oemlock.OemLockService.FLASH_LOCK_UNLOCKED)) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        return true;
                    default:
                        return false;
                }
            }
        };
        this.mContext = context;
        this.mOemLock = oemLock;
        ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).addUserRestrictionsListener(this.mUserRestrictionsListener);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("oem_lock", this.mService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPersistentDataBlockOemUnlockAllowedBit(boolean z) {
        com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlockManagerInternal = (com.android.server.pdb.PersistentDataBlockManagerInternal) com.android.server.LocalServices.getService(com.android.server.pdb.PersistentDataBlockManagerInternal.class);
        if (persistentDataBlockManagerInternal != null && !(this.mOemLock instanceof com.android.server.oemlock.PersistentDataBlockLock)) {
            android.util.Slog.i(TAG, "Update OEM Unlock bit in pst partition to " + z);
            persistentDataBlockManagerInternal.forceOemUnlockEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOemUnlockAllowedByAdmin() {
        return !android.os.UserManager.get(this.mContext).hasUserRestriction("no_factory_reset", android.os.UserHandle.SYSTEM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceUserIsAdmin() {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!android.os.UserManager.get(this.mContext).isUserAdmin(callingUserId)) {
                throw new java.lang.SecurityException("Must be an admin user");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
