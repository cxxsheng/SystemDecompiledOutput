package com.android.server;

/* loaded from: classes.dex */
final class HsumBootUserInitializer {
    private static final java.lang.String TAG = com.android.server.HsumBootUserInitializer.class.getSimpleName();
    private final com.android.server.am.ActivityManagerService mAms;
    private final android.content.ContentResolver mContentResolver;
    private final android.database.ContentObserver mDeviceProvisionedObserver = new android.database.ContentObserver(new android.os.Handler(android.os.Looper.getMainLooper())) { // from class: com.android.server.HsumBootUserInitializer.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (com.android.server.HsumBootUserInitializer.this.isDeviceProvisioned()) {
                com.android.server.utils.Slogf.i(com.android.server.HsumBootUserInitializer.TAG, "Marking USER_SETUP_COMPLETE for system user");
                android.provider.Settings.Secure.putInt(com.android.server.HsumBootUserInitializer.this.mContentResolver, "user_setup_complete", 1);
                com.android.server.HsumBootUserInitializer.this.mContentResolver.unregisterContentObserver(com.android.server.HsumBootUserInitializer.this.mDeviceProvisionedObserver);
            }
        }
    };
    private final com.android.server.pm.PackageManagerService mPms;
    private final boolean mShouldAlwaysHaveMainUser;
    private final com.android.server.pm.UserManagerInternal mUmi;

    @android.annotation.Nullable
    public static com.android.server.HsumBootUserInitializer createInstance(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.pm.PackageManagerService packageManagerService, android.content.ContentResolver contentResolver, boolean z) {
        if (!android.os.UserManager.isHeadlessSystemUserMode()) {
            return null;
        }
        return new com.android.server.HsumBootUserInitializer((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class), activityManagerService, packageManagerService, contentResolver, z);
    }

    private HsumBootUserInitializer(com.android.server.pm.UserManagerInternal userManagerInternal, com.android.server.am.ActivityManagerService activityManagerService, com.android.server.pm.PackageManagerService packageManagerService, android.content.ContentResolver contentResolver, boolean z) {
        this.mUmi = userManagerInternal;
        this.mAms = activityManagerService;
        this.mPms = packageManagerService;
        this.mContentResolver = contentResolver;
        this.mShouldAlwaysHaveMainUser = z;
    }

    public void init(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        com.android.server.utils.Slogf.i(TAG, "init())");
        if (this.mShouldAlwaysHaveMainUser) {
            timingsTraceAndSlog.traceBegin("createMainUserIfNeeded");
            createMainUserIfNeeded();
            timingsTraceAndSlog.traceEnd();
        }
    }

    private void createMainUserIfNeeded() {
        int mainUserId = this.mUmi.getMainUserId();
        if (mainUserId != -10000) {
            com.android.server.utils.Slogf.d(TAG, "Found existing MainUser, userId=%d", java.lang.Integer.valueOf(mainUserId));
            return;
        }
        com.android.server.utils.Slogf.d(TAG, "Creating a new MainUser");
        try {
            com.android.server.utils.Slogf.i(TAG, "Successfully created MainUser, userId=%d", java.lang.Integer.valueOf(this.mUmi.createUserEvenWhenDisallowed(null, "android.os.usertype.full.SECONDARY", 16386, null, null).id));
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            com.android.server.utils.Slogf.wtf(TAG, "Initial bootable MainUser creation failed", (java.lang.Throwable) e);
        }
    }

    public void systemRunning(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        observeDeviceProvisioning();
        unlockSystemUser(timingsTraceAndSlog);
        try {
            timingsTraceAndSlog.traceBegin("getBootUser");
            int bootUser = this.mUmi.getBootUser(this.mPms.hasSystemFeature("android.hardware.type.automotive", 0));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("switchToBootUser-" + bootUser);
            switchToBootUser(bootUser);
            timingsTraceAndSlog.traceEnd();
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to switch to boot user since there isn't one.");
        }
    }

    private void observeDeviceProvisioning() {
        if (isDeviceProvisioned()) {
            return;
        }
        this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceProvisioned() {
        try {
            return android.provider.Settings.Global.getInt(this.mContentResolver, "device_provisioned") == 1;
        } catch (java.lang.Exception e) {
            com.android.server.utils.Slogf.wtf(TAG, "DEVICE_PROVISIONED setting not found.", e);
            return false;
        }
    }

    private void unlockSystemUser(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        com.android.server.utils.Slogf.i(TAG, "Unlocking system user");
        timingsTraceAndSlog.traceBegin("unlock-system-user");
        try {
            timingsTraceAndSlog.traceBegin("am.startUser");
            boolean startUserInBackgroundWithListener = this.mAms.startUserInBackgroundWithListener(0, null);
            timingsTraceAndSlog.traceEnd();
            if (!startUserInBackgroundWithListener) {
                com.android.server.utils.Slogf.w(TAG, "could not restart system user in background; trying unlock instead");
                timingsTraceAndSlog.traceBegin("am.unlockUser");
                boolean unlockUser = this.mAms.unlockUser(0, null, null, null);
                timingsTraceAndSlog.traceEnd();
                if (!unlockUser) {
                    com.android.server.utils.Slogf.w(TAG, "could not unlock system user either");
                }
            }
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    private void switchToBootUser(int i) {
        com.android.server.utils.Slogf.i(TAG, "Switching to boot user %d", java.lang.Integer.valueOf(i));
        if (!this.mAms.startUserInForegroundWithListener(i, null)) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to start user %d in foreground", java.lang.Integer.valueOf(i));
        }
    }
}
