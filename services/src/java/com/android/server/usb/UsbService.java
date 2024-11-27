package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbService extends android.hardware.usb.IUsbManager.Stub {
    static final int PACKAGE_MONITOR_OPERATION_ID = 1;
    static final int STRONG_AUTH_OPERATION_ID = 2;
    private static final java.lang.String TAG = "UsbService";
    private final com.android.server.usb.UsbAlsaManager mAlsaManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentUserId;
    private com.android.server.usb.UsbDeviceManager mDeviceManager;
    private com.android.server.usb.UsbHostManager mHostManager;
    private final com.android.server.usb.UsbPermissionManager mPermissionManager;
    private com.android.server.usb.UsbPortManager mPortManager;
    private final com.android.server.usb.UsbSettingsManager mSettingsManager;
    private final android.os.UserManager mUserManager;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.Integer>> mUsbDisableRequesters = new android.util.ArrayMap<>();

    public static class Lifecycle extends com.android.server.SystemService {
        private final java.util.concurrent.CompletableFuture<java.lang.Void> mOnActivityManagerPhaseFinished;
        private final java.util.concurrent.CompletableFuture<java.lang.Void> mOnStartFinished;
        private com.android.server.usb.UsbService mUsbService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mOnStartFinished = new java.util.concurrent.CompletableFuture<>();
            this.mOnActivityManagerPhaseFinished = new java.util.concurrent.CompletableFuture<>();
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.usb.UsbService$Lifecycle$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.usb.UsbService.Lifecycle.this.lambda$onStart$0();
                }
            }, "UsbService$Lifecycle#onStart");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStart$0() {
            this.mUsbService = new com.android.server.usb.UsbService(getContext());
            publishBinderService("usb", this.mUsbService);
            this.mOnStartFinished.complete(null);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.usb.UsbService$Lifecycle$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.usb.UsbService.Lifecycle.this.lambda$onBootPhase$1();
                    }
                }, "UsbService$Lifecycle#onBootPhase");
            } else if (i == 1000) {
                this.mOnActivityManagerPhaseFinished.join();
                this.mUsbService.bootCompleted();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBootPhase$1() {
            this.mOnStartFinished.join();
            this.mUsbService.systemReady();
            this.mOnActivityManagerPhaseFinished.complete(null);
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(com.android.server.SystemService.TargetUser targetUser, final com.android.server.SystemService.TargetUser targetUser2) {
            com.android.server.FgThread.getHandler().postAtFrontOfQueue(new java.lang.Runnable() { // from class: com.android.server.usb.UsbService$Lifecycle$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.usb.UsbService.Lifecycle.this.lambda$onUserSwitching$2(targetUser2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserSwitching$2(com.android.server.SystemService.TargetUser targetUser) {
            this.mUsbService.onSwitchUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(com.android.server.SystemService.TargetUser targetUser) {
            this.mUsbService.onStopUser(targetUser.getUserHandle());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(com.android.server.SystemService.TargetUser targetUser) {
            this.mUsbService.onUnlockUser(targetUser.getUserIdentifier());
        }
    }

    com.android.server.usb.UsbUserSettingsManager getSettingsForUser(int i) {
        return this.mSettingsManager.getSettingsForUser(i);
    }

    com.android.server.usb.UsbUserPermissionManager getPermissionsForUser(int i) {
        return this.mPermissionManager.getPermissionsForUser(i);
    }

    public UsbService(android.content.Context context) {
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mSettingsManager = new com.android.server.usb.UsbSettingsManager(context, this);
        this.mPermissionManager = new com.android.server.usb.UsbPermissionManager(context, this);
        this.mAlsaManager = new com.android.server.usb.UsbAlsaManager(context);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.usb.host")) {
            this.mHostManager = new com.android.server.usb.UsbHostManager(context, this.mAlsaManager, this.mPermissionManager);
        }
        if (new java.io.File("/sys/class/android_usb").exists()) {
            this.mDeviceManager = new com.android.server.usb.UsbDeviceManager(context, this.mAlsaManager, this.mSettingsManager, this.mPermissionManager);
        }
        if (this.mHostManager != null || this.mDeviceManager != null) {
            this.mPortManager = new com.android.server.usb.UsbPortManager(context);
        }
        onSwitchUser(0);
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.usb.UsbService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction()) && com.android.server.usb.UsbService.this.mDeviceManager != null) {
                    com.android.server.usb.UsbService.this.mDeviceManager.updateUserRestrictions();
                }
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        this.mContext.registerReceiverAsUser(broadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchUser(int i) {
        synchronized (this.mLock) {
            try {
                this.mCurrentUserId = i;
                com.android.server.usb.UsbProfileGroupSettingsManager settingsForProfileGroup = this.mSettingsManager.getSettingsForProfileGroup(android.os.UserHandle.of(i));
                if (this.mHostManager != null) {
                    this.mHostManager.setCurrentUserSettings(settingsForProfileGroup);
                }
                if (this.mDeviceManager != null) {
                    this.mDeviceManager.setCurrentUser(i, settingsForProfileGroup);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
        this.mSettingsManager.remove(userHandle);
    }

    public void systemReady() {
        this.mAlsaManager.systemReady();
        if (this.mDeviceManager != null) {
            this.mDeviceManager.systemReady();
        }
        if (this.mHostManager != null) {
            this.mHostManager.systemReady();
        }
        if (this.mPortManager != null) {
            this.mPortManager.systemReady();
        }
    }

    public void bootCompleted() {
        if (this.mDeviceManager != null) {
            this.mDeviceManager.bootCompleted();
        }
        if (android.hardware.usb.flags.Flags.enableUsbDataSignalStaking()) {
            new com.android.server.usb.UsbService.PackageUninstallMonitor().register(this.mContext, android.os.UserHandle.ALL, com.android.internal.os.BackgroundThread.getHandler());
            new com.android.internal.widget.LockPatternUtils(this.mContext).registerStrongAuthTracker(new com.android.server.usb.UsbService.StrongAuthTracker(this.mContext, com.android.internal.os.BackgroundThread.getHandler().getLooper()));
        }
    }

    public void onUnlockUser(int i) {
        if (this.mDeviceManager != null) {
            this.mDeviceManager.onUnlockUser(i);
        }
    }

    public void getDeviceList(android.os.Bundle bundle) {
        if (this.mHostManager != null) {
            this.mHostManager.getDeviceList(bundle);
        }
    }

    public android.os.ParcelFileDescriptor openDevice(java.lang.String str, java.lang.String str2) {
        android.os.ParcelFileDescriptor parcelFileDescriptor = null;
        if (this.mHostManager != null && str != null) {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.hardware.usb.IUsbManager.Stub.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    try {
                        if (this.mUserManager.isSameProfileGroup(userId, this.mCurrentUserId)) {
                            parcelFileDescriptor = this.mHostManager.openDevice(str, getPermissionsForUser(userId), str2, callingPid, callingUid);
                        } else {
                            android.util.Slog.w(TAG, "Cannot open " + str + " for user " + userId + " as user is not active.");
                        }
                    } finally {
                    }
                }
            } finally {
                android.hardware.usb.IUsbManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return parcelFileDescriptor;
    }

    public android.hardware.usb.UsbAccessory getCurrentAccessory() {
        if (this.mDeviceManager != null) {
            return this.mDeviceManager.getCurrentAccessory();
        }
        return null;
    }

    public android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory) {
        if (this.mDeviceManager != null) {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.hardware.usb.IUsbManager.Stub.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    if (this.mUserManager.isSameProfileGroup(userId, this.mCurrentUserId)) {
                        return this.mDeviceManager.openAccessory(usbAccessory, getPermissionsForUser(userId), callingPid, callingUid);
                    }
                    android.util.Slog.w(TAG, "Cannot open " + usbAccessory + " for user " + userId + " as user is not active.");
                    return null;
                }
            } finally {
                android.hardware.usb.IUsbManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return null;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_MTP")
    public android.os.ParcelFileDescriptor getControlFd(long j) {
        getControlFd_enforcePermission();
        return this.mDeviceManager.getControlFd(j);
    }

    public void setDevicePackage(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i) {
        java.util.Objects.requireNonNull(usbDevice);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).setDevicePackage(usbDevice, str, of);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAccessoryPackage(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, int i) {
        java.util.Objects.requireNonNull(usbAccessory);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).setAccessoryPackage(usbAccessory, str, of);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addDevicePackagesToPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(usbDevice);
        java.lang.String[] strArr2 = (java.lang.String[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(strArr, com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).addDevicePackagesToDenied(usbDevice, strArr2, userHandle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addAccessoryPackagesToPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(usbAccessory);
        java.lang.String[] strArr2 = (java.lang.String[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(strArr, com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).addAccessoryPackagesToDenied(usbAccessory, strArr2, userHandle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeDevicePackagesFromPreferenceDenied(android.hardware.usb.UsbDevice usbDevice, java.lang.String[] strArr, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(usbDevice);
        java.lang.String[] strArr2 = (java.lang.String[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(strArr, com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).removeDevicePackagesFromDenied(usbDevice, strArr2, userHandle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeAccessoryPackagesFromPreferenceDenied(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String[] strArr, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(usbAccessory);
        java.lang.String[] strArr2 = (java.lang.String[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(strArr, com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(userHandle).removeAccessoryPackagesFromDenied(usbAccessory, strArr2, userHandle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDevicePersistentPermission(android.hardware.usb.UsbDevice usbDevice, int i, android.os.UserHandle userHandle, boolean z) {
        java.util.Objects.requireNonNull(usbDevice);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userHandle).setDevicePersistentPermission(usbDevice, i, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAccessoryPersistentPermission(android.hardware.usb.UsbAccessory usbAccessory, int i, android.os.UserHandle userHandle, boolean z) {
        java.util.Objects.requireNonNull(usbAccessory);
        java.util.Objects.requireNonNull(userHandle);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.getPermissionsForUser(userHandle).setAccessoryPersistentPermission(usbAccessory, i, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean hasDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getPermissionsForUser(userId).hasPermission(usbDevice, str, callingPid, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public boolean hasDevicePermissionWithIdentity(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, int i, int i2) {
        hasDevicePermissionWithIdentity_enforcePermission();
        return getPermissionsForUser(android.os.UserHandle.getUserId(i2)).hasPermission(usbDevice, str, i, i2);
    }

    public boolean hasAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getPermissionsForUser(userId).hasPermission(usbAccessory, callingPid, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public boolean hasAccessoryPermissionWithIdentity(android.hardware.usb.UsbAccessory usbAccessory, int i, int i2) {
        hasAccessoryPermissionWithIdentity_enforcePermission();
        return getPermissionsForUser(android.os.UserHandle.getUserId(i2)).hasPermission(usbAccessory, i, i2);
    }

    public void requestDevicePermission(android.hardware.usb.UsbDevice usbDevice, java.lang.String str, android.app.PendingIntent pendingIntent) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getPermissionsForUser(userId).requestPermission(usbDevice, str, pendingIntent, callingPid, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, java.lang.String str, android.app.PendingIntent pendingIntent) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getPermissionsForUser(userId).requestPermission(usbAccessory, str, pendingIntent, callingPid, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void grantDevicePermission(android.hardware.usb.UsbDevice usbDevice, int i) {
        grantDevicePermission_enforcePermission();
        int userId = android.os.UserHandle.getUserId(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getPermissionsForUser(userId).grantDevicePermission(usbDevice, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void grantAccessoryPermission(android.hardware.usb.UsbAccessory usbAccessory, int i) {
        grantAccessoryPermission_enforcePermission();
        int userId = android.os.UserHandle.getUserId(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getPermissionsForUser(userId).grantAccessoryPermission(usbAccessory, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean hasDefaults(java.lang.String str, int i) {
        java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mSettingsManager.getSettingsForProfileGroup(of).hasDefaults(str2, of);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearDefaults(java.lang.String str, int i) {
        java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettingsManager.getSettingsForProfileGroup(of).clearDefaults(str2, of);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void setCurrentFunctions(long j, int i) {
        setCurrentFunctions_enforcePermission();
        com.android.internal.util.Preconditions.checkArgument(android.hardware.usb.UsbManager.areSettableFunctions(j));
        com.android.internal.util.Preconditions.checkState(this.mDeviceManager != null);
        this.mDeviceManager.setCurrentFunctions(j, i);
    }

    public void setCurrentFunction(java.lang.String str, boolean z, int i) {
        setCurrentFunctions(android.hardware.usb.UsbManager.usbFunctionsFromString(str), i);
    }

    public boolean isFunctionEnabled(java.lang.String str) {
        return (getCurrentFunctions() & android.hardware.usb.UsbManager.usbFunctionsFromString(str)) != 0;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public long getCurrentFunctions() {
        getCurrentFunctions_enforcePermission();
        com.android.internal.util.Preconditions.checkState(this.mDeviceManager != null);
        return this.mDeviceManager.getCurrentFunctions();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void setScreenUnlockedFunctions(long j) {
        setScreenUnlockedFunctions_enforcePermission();
        com.android.internal.util.Preconditions.checkArgument(android.hardware.usb.UsbManager.areSettableFunctions(j));
        com.android.internal.util.Preconditions.checkState(this.mDeviceManager != null);
        this.mDeviceManager.setScreenUnlockedFunctions(j);
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public long getScreenUnlockedFunctions() {
        getScreenUnlockedFunctions_enforcePermission();
        com.android.internal.util.Preconditions.checkState(this.mDeviceManager != null);
        return this.mDeviceManager.getScreenUnlockedFunctions();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public int getCurrentUsbSpeed() {
        getCurrentUsbSpeed_enforcePermission();
        com.android.internal.util.Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDeviceManager.getCurrentUsbSpeed();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public int getGadgetHalVersion() {
        getGadgetHalVersion_enforcePermission();
        com.android.internal.util.Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDeviceManager.getGadgetHalVersion();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void resetUsbGadget() {
        resetUsbGadget_enforcePermission();
        com.android.internal.util.Preconditions.checkNotNull(this.mDeviceManager, "DeviceManager must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceManager.resetUsbGadget();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void resetUsbPort(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str, "resetUsbPort: portId must not be null. opId:" + i);
        java.util.Objects.requireNonNull(iUsbOperationInternal, "resetUsbPort: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.resetUsbPort(str, i, iUsbOperationInternal, null);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "resetUsbPort: Failed to call onOperationComplete", e);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public java.util.List<android.hardware.usb.ParcelableUsbPort> getPorts() {
        getPorts_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager == null) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            android.hardware.usb.UsbPort[] ports = this.mPortManager.getPorts();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.hardware.usb.UsbPort usbPort : ports) {
                arrayList.add(android.hardware.usb.ParcelableUsbPort.of(usbPort));
            }
            return arrayList;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.hardware.usb.UsbPortStatus getPortStatus(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "portId must not be null");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPortManager != null ? this.mPortManager.getPortStatus(str) : null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public boolean isModeChangeSupported(java.lang.String str) {
        isModeChangeSupported_enforcePermission();
        java.util.Objects.requireNonNull(str, "portId must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPortManager != null ? this.mPortManager.isModeChangeSupported(str) : false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setPortRoles(java.lang.String str, int i, int i2) {
        java.util.Objects.requireNonNull(str, "portId must not be null");
        android.hardware.usb.UsbPort.checkRoles(i, i2);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.setPortRoles(str, i, i2, null);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enableLimitPowerTransfer(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str, "portId must not be null. opID:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.enableLimitPowerTransfer(str, z, i, iUsbOperationInternal, null);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "enableLimitPowerTransfer: Failed to call onOperationComplete", e);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enableContaminantDetection(java.lang.String str, boolean z) {
        java.util.Objects.requireNonNull(str, "portId must not be null");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.enableContaminantDetection(str, z, null);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public int getUsbHalVersion() {
        getUsbHalVersion_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                return this.mPortManager.getUsbHalVersion();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean enableUsbData(java.lang.String str, boolean z, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str, "enableUsbData: portId must not be null. opId:" + i);
        java.util.Objects.requireNonNull(iUsbOperationInternal, "enableUsbData: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        boolean z2 = false;
        if (android.hardware.usb.flags.Flags.enableUsbDataSignalStaking() && !shouldUpdateUsbSignaling(str, z, android.os.Binder.getCallingUid())) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                z2 = this.mPortManager.enableUsbData(str, z, i, iUsbOperationInternal, null);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "enableUsbData: Failed to call onOperationComplete", e);
                }
            }
            return z2;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean shouldUpdateUsbSignaling(java.lang.String str, boolean z, int i) {
        synchronized (this.mUsbDisableRequesters) {
            try {
                if (!this.mUsbDisableRequesters.containsKey(str)) {
                    this.mUsbDisableRequesters.put(str, new android.util.ArraySet<>());
                }
                android.util.ArraySet<java.lang.Integer> arraySet = this.mUsbDisableRequesters.get(str);
                if (z) {
                    arraySet.remove(java.lang.Integer.valueOf(i));
                    return arraySet.isEmpty();
                }
                arraySet.add(java.lang.Integer.valueOf(i));
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void enableUsbDataWhileDocked(java.lang.String str, int i, android.hardware.usb.IUsbOperationInternal iUsbOperationInternal) {
        java.util.Objects.requireNonNull(str, "enableUsbDataWhileDocked: portId must not be null. opId:" + i);
        java.util.Objects.requireNonNull(iUsbOperationInternal, "enableUsbDataWhileDocked: callback must not be null. opId:" + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.enableUsbDataWhileDocked(str, i, iUsbOperationInternal, null);
            } else {
                try {
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "enableUsbData: Failed to call onOperationComplete", e);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_USB")
    public void setUsbDeviceConnectionHandler(android.content.ComponentName componentName) {
        setUsbDeviceConnectionHandler_enforcePermission();
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == android.os.UserHandle.getCallingUserId()) {
                    if (this.mHostManager != null) {
                        this.mHostManager.setUsbDeviceConnectionHandler(componentName);
                    }
                } else {
                    throw new java.lang.IllegalArgumentException("Only the current user can register a usb connection handler");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean registerForDisplayPortEvents(@android.annotation.NonNull android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        java.util.Objects.requireNonNull(iDisplayPortAltModeInfoListener, "registerForDisplayPortEvents: listener must not be null.");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                return this.mPortManager.registerForDisplayPortEvents(iDisplayPortAltModeInfoListener);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterForDisplayPortEvents(@android.annotation.NonNull android.hardware.usb.IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        java.util.Objects.requireNonNull(iDisplayPortAltModeInfoListener, "unregisterForDisplayPortEvents: listener must not be null.");
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USB", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mPortManager != null) {
                this.mPortManager.unregisterForDisplayPortEvents(iDisplayPortAltModeInfoListener);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @dalvik.annotation.optimization.NeverCompile
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str;
        com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream;
        java.lang.String str2;
        boolean z;
        int i;
        boolean z2;
        int i2;
        boolean z3;
        int i3;
        char c;
        int i4;
        boolean z4;
        char c2;
        int i5;
        int i6;
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Collections.addAll(arraySet, strArr);
                boolean z5 = arraySet.contains("--proto");
                if (strArr == null || strArr.length == 0 || strArr[0].equals("-a")) {
                    str = "  ";
                } else {
                    if (!z5) {
                        char c3 = 65535;
                        if ("set-port-roles".equals(strArr[0]) && strArr.length == 4) {
                            java.lang.String str3 = strArr[1];
                            java.lang.String str4 = strArr[2];
                            switch (str4.hashCode()) {
                                case -896505829:
                                    if (str4.equals("source")) {
                                        c2 = 0;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case -440560135:
                                    if (str4.equals("no-power")) {
                                        c2 = 2;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 3530387:
                                    if (str4.equals("sink")) {
                                        c2 = 1;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                default:
                                    c2 = 65535;
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    i5 = 1;
                                    break;
                                case 1:
                                    i5 = 2;
                                    break;
                                case 2:
                                    i5 = 0;
                                    break;
                                default:
                                    indentingPrintWriter.println("Invalid power role: " + strArr[2]);
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                            }
                            java.lang.String str5 = strArr[3];
                            switch (str5.hashCode()) {
                                case -1335157162:
                                    if (str5.equals("device")) {
                                        c3 = 1;
                                        break;
                                    }
                                    break;
                                case 3208616:
                                    if (str5.equals("host")) {
                                        c3 = 0;
                                        break;
                                    }
                                    break;
                                case 2063627318:
                                    if (str5.equals("no-data")) {
                                        c3 = 2;
                                        break;
                                    }
                                    break;
                            }
                            switch (c3) {
                                case 0:
                                    i6 = 1;
                                    break;
                                case 1:
                                    i6 = 2;
                                    break;
                                case 2:
                                    i6 = 0;
                                    break;
                                default:
                                    indentingPrintWriter.println("Invalid data role: " + strArr[3]);
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                            }
                            if (this.mPortManager != null) {
                                this.mPortManager.setPortRoles(str3, i5, i6, indentingPrintWriter);
                                indentingPrintWriter.println();
                                this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                            }
                        } else {
                            if ("add-port".equals(strArr[0])) {
                                str2 = "Invalid data role: ";
                                if (strArr.length >= 3) {
                                    java.lang.String str6 = strArr[1];
                                    java.lang.String str7 = strArr[2];
                                    switch (str7.hashCode()) {
                                        case 99374:
                                            if (str7.equals("dfp")) {
                                                c = 1;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 115711:
                                            if (str7.equals("ufp")) {
                                                c = 0;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3094652:
                                            if (str7.equals("dual")) {
                                                c = 2;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        case 3387192:
                                            if (str7.equals("none")) {
                                                c = 3;
                                                break;
                                            }
                                            c = 65535;
                                            break;
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            i4 = 1;
                                            break;
                                        case 1:
                                            i4 = 2;
                                            break;
                                        case 2:
                                            i4 = 3;
                                            break;
                                        case 3:
                                            i4 = 0;
                                            break;
                                        default:
                                            indentingPrintWriter.println("Invalid mode: " + strArr[2]);
                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                            return;
                                    }
                                    boolean z6 = false;
                                    boolean z7 = false;
                                    for (int i7 = 3; i7 < strArr.length; i7++) {
                                        java.lang.String str8 = strArr[i7];
                                        switch (str8.hashCode()) {
                                            case -1384270173:
                                                if (str8.equals("--displayport")) {
                                                    z4 = true;
                                                    break;
                                                }
                                                z4 = -1;
                                                break;
                                            case 407165993:
                                                if (str8.equals("--compliance-warnings")) {
                                                    z4 = false;
                                                    break;
                                                }
                                                z4 = -1;
                                                break;
                                            default:
                                                z4 = -1;
                                                break;
                                        }
                                        switch (z4) {
                                            case false:
                                                z6 = true;
                                                break;
                                            case true:
                                                z7 = true;
                                                break;
                                            default:
                                                indentingPrintWriter.println("Invalid Identifier: " + strArr[i7]);
                                                break;
                                        }
                                    }
                                    if (this.mPortManager != null) {
                                        this.mPortManager.addSimulatedPort(str6, i4, z6, z7, indentingPrintWriter);
                                        indentingPrintWriter.println();
                                        this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                    }
                                }
                            } else {
                                str2 = "Invalid data role: ";
                            }
                            if ("connect-port".equals(strArr[0]) && strArr.length == 5) {
                                java.lang.String str9 = strArr[1];
                                boolean endsWith = strArr[2].endsWith("?");
                                java.lang.String removeLastChar = endsWith ? removeLastChar(strArr[2]) : strArr[2];
                                switch (removeLastChar.hashCode()) {
                                    case 99374:
                                        if (removeLastChar.equals("dfp")) {
                                            z = true;
                                            break;
                                        }
                                        z = -1;
                                        break;
                                    case 115711:
                                        if (removeLastChar.equals("ufp")) {
                                            z = false;
                                            break;
                                        }
                                        z = -1;
                                        break;
                                    default:
                                        z = -1;
                                        break;
                                }
                                switch (z) {
                                    case false:
                                        i = 1;
                                        break;
                                    case true:
                                        i = 2;
                                        break;
                                    default:
                                        indentingPrintWriter.println("Invalid mode: " + strArr[2]);
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return;
                                }
                                boolean endsWith2 = strArr[3].endsWith("?");
                                java.lang.String removeLastChar2 = endsWith2 ? removeLastChar(strArr[3]) : strArr[3];
                                switch (removeLastChar2.hashCode()) {
                                    case -896505829:
                                        if (removeLastChar2.equals("source")) {
                                            z2 = false;
                                            break;
                                        }
                                        z2 = -1;
                                        break;
                                    case 3530387:
                                        if (removeLastChar2.equals("sink")) {
                                            z2 = true;
                                            break;
                                        }
                                        z2 = -1;
                                        break;
                                    default:
                                        z2 = -1;
                                        break;
                                }
                                switch (z2) {
                                    case false:
                                        i2 = 1;
                                        break;
                                    case true:
                                        i2 = 2;
                                        break;
                                    default:
                                        indentingPrintWriter.println("Invalid power role: " + strArr[3]);
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return;
                                }
                                boolean endsWith3 = strArr[4].endsWith("?");
                                java.lang.String removeLastChar3 = endsWith3 ? removeLastChar(strArr[4]) : strArr[4];
                                switch (removeLastChar3.hashCode()) {
                                    case -1335157162:
                                        if (removeLastChar3.equals("device")) {
                                            z3 = true;
                                            break;
                                        }
                                        z3 = -1;
                                        break;
                                    case 3208616:
                                        if (removeLastChar3.equals("host")) {
                                            z3 = false;
                                            break;
                                        }
                                        z3 = -1;
                                        break;
                                    default:
                                        z3 = -1;
                                        break;
                                }
                                switch (z3) {
                                    case false:
                                        i3 = 1;
                                        break;
                                    case true:
                                        i3 = 2;
                                        break;
                                    default:
                                        indentingPrintWriter.println(str2 + strArr[4]);
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return;
                                }
                                if (this.mPortManager != null) {
                                    this.mPortManager.connectSimulatedPort(str9, i, endsWith, i2, endsWith2, i3, endsWith3, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("disconnect-port".equals(strArr[0]) && strArr.length == 2) {
                                java.lang.String str10 = strArr[1];
                                if (this.mPortManager != null) {
                                    this.mPortManager.disconnectSimulatedPort(str10, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("remove-port".equals(strArr[0]) && strArr.length == 2) {
                                java.lang.String str11 = strArr[1];
                                if (this.mPortManager != null) {
                                    this.mPortManager.removeSimulatedPort(str11, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("reset".equals(strArr[0]) && strArr.length == 1) {
                                if (this.mPortManager != null) {
                                    this.mPortManager.resetSimulation(indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("set-contaminant-status".equals(strArr[0]) && strArr.length == 3) {
                                java.lang.String str12 = strArr[1];
                                java.lang.Boolean valueOf = java.lang.Boolean.valueOf(java.lang.Boolean.parseBoolean(strArr[2]));
                                if (this.mPortManager != null) {
                                    this.mPortManager.simulateContaminantStatus(str12, valueOf.booleanValue(), indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("set-compliance-reasons".equals(strArr[0]) && strArr.length == 3) {
                                java.lang.String str13 = strArr[1];
                                java.lang.String str14 = strArr[2];
                                if (this.mPortManager != null) {
                                    this.mPortManager.simulateComplianceWarnings(str13, str14, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("clear-compliance-reasons".equals(strArr[0]) && strArr.length == 2) {
                                java.lang.String str15 = strArr[1];
                                if (this.mPortManager != null) {
                                    this.mPortManager.simulateComplianceWarnings(str15, "", indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("set-displayport-status".equals(strArr[0]) && strArr.length == 7) {
                                java.lang.String str16 = strArr[1];
                                int parseInt = java.lang.Integer.parseInt(strArr[2]);
                                int parseInt2 = java.lang.Integer.parseInt(strArr[3]);
                                int parseInt3 = java.lang.Integer.parseInt(strArr[4]);
                                boolean parseBoolean = java.lang.Boolean.parseBoolean(strArr[5]);
                                int parseInt4 = java.lang.Integer.parseInt(strArr[6]);
                                if (this.mPortManager != null) {
                                    this.mPortManager.simulateDisplayPortAltModeInfo(str16, parseInt, parseInt2, parseInt3, parseBoolean, parseInt4, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("reset-displayport-status".equals(strArr[0]) && strArr.length == 2) {
                                java.lang.String str17 = strArr[1];
                                if (this.mPortManager != null) {
                                    this.mPortManager.simulateDisplayPortAltModeInfo(str17, 0, 0, 0, false, 0, indentingPrintWriter);
                                    indentingPrintWriter.println();
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("enable-usb-data".equals(strArr[0]) && strArr.length == 3) {
                                java.lang.String str18 = strArr[1];
                                boolean parseBoolean2 = java.lang.Boolean.parseBoolean(strArr[2]);
                                if (this.mPortManager != null) {
                                    android.hardware.usb.UsbPort[] ports = this.mPortManager.getPorts();
                                    int length = ports.length;
                                    int i8 = 0;
                                    while (true) {
                                        if (i8 >= length) {
                                            break;
                                        }
                                        android.hardware.usb.UsbPort usbPort = ports[i8];
                                        if (usbPort.getId().equals(str18)) {
                                            android.util.Slog.i(TAG, "enableUsbData " + str18 + " status " + usbPort.enableUsbData(parseBoolean2));
                                            break;
                                        }
                                        i8++;
                                    }
                                }
                            } else if ("ports".equals(strArr[0]) && strArr.length == 1) {
                                if (this.mPortManager != null) {
                                    this.mPortManager.dump(new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  ")), "", 0L);
                                }
                            } else if ("dump-descriptors".equals(strArr[0])) {
                                this.mHostManager.dumpDescriptors(indentingPrintWriter, strArr);
                            } else {
                                indentingPrintWriter.println("Dump current USB state or issue command:");
                                indentingPrintWriter.println("  ports");
                                indentingPrintWriter.println("  set-port-roles <id> <source|sink|no-power> <host|device|no-data>");
                                indentingPrintWriter.println("  add-port <id> <ufp|dfp|dual|none> <optional args>");
                                indentingPrintWriter.println("    <optional args> include:");
                                indentingPrintWriter.println("      --compliance-warnings: enables compliance warnings on port");
                                indentingPrintWriter.println("      --displayport: enables DisplayPort Alt Mode on port");
                                indentingPrintWriter.println("  connect-port <id> <ufp|dfp><?> <source|sink><?> <host|device><?>");
                                indentingPrintWriter.println("    (add ? suffix if mode, power role, or data role can be changed)");
                                indentingPrintWriter.println("  disconnect-port <id>");
                                indentingPrintWriter.println("  remove-port <id>");
                                indentingPrintWriter.println("  reset");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB type C port role switch:");
                                indentingPrintWriter.println("  dumpsys usb set-port-roles \"default\" source device");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB type C port simulation with full capabilities:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" dual --compliance-warnings --displayport");
                                indentingPrintWriter.println("  dumpsys usb connect-port \"matrix\" ufp? sink? device?");
                                indentingPrintWriter.println("  dumpsys usb ports");
                                indentingPrintWriter.println("  dumpsys usb disconnect-port \"matrix\"");
                                indentingPrintWriter.println("  dumpsys usb remove-port \"matrix\"");
                                indentingPrintWriter.println("  dumpsys usb reset");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB type C port where only power role can be changed:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" dual");
                                indentingPrintWriter.println("  dumpsys usb connect-port \"matrix\" dfp source? host");
                                indentingPrintWriter.println("  dumpsys usb reset");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB OTG port where id pin determines function:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" dual");
                                indentingPrintWriter.println("  dumpsys usb connect-port \"matrix\" dfp source host");
                                indentingPrintWriter.println("  dumpsys usb reset");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB device-only port:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" ufp");
                                indentingPrintWriter.println("  dumpsys usb connect-port \"matrix\" ufp sink device");
                                indentingPrintWriter.println("  dumpsys usb reset");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example simulate contaminant status:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" ufp");
                                indentingPrintWriter.println("  dumpsys usb set-contaminant-status \"matrix\" true");
                                indentingPrintWriter.println("  dumpsys usb set-contaminant-status \"matrix\" false");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example simulate compliance warnings:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" dual --compliance-warnings");
                                indentingPrintWriter.println("  dumpsys usb set-compliance-reasons \"matrix\" <reason-list>");
                                indentingPrintWriter.println("  dumpsys usb clear-compliance-reasons \"matrix\"");
                                indentingPrintWriter.println("<reason-list> is expected to be formatted as \"1, ..., N\"");
                                indentingPrintWriter.println("with reasons that need to be simulated.");
                                indentingPrintWriter.println("  1: other");
                                indentingPrintWriter.println("  2: debug accessory");
                                indentingPrintWriter.println("  3: bc12");
                                indentingPrintWriter.println("  4: missing rp");
                                indentingPrintWriter.println("  5: input power limited");
                                indentingPrintWriter.println("  6: missing data lines");
                                indentingPrintWriter.println("  7: enumeration fail");
                                indentingPrintWriter.println("  8: flaky connection");
                                indentingPrintWriter.println("  9: unreliable io");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example simulate DisplayPort Alt Mode Changes:");
                                indentingPrintWriter.println("  dumpsys usb add-port \"matrix\" dual --displayport");
                                indentingPrintWriter.println("  dumpsys usb set-displayport-status \"matrix\" <partner-sink> <cable> <num-lanes> <hpd> <link-training-status>");
                                indentingPrintWriter.println("The required fields are as followed:");
                                indentingPrintWriter.println("    <partner-sink>: type DisplayPortAltModeStatus");
                                indentingPrintWriter.println("    <cable>: type DisplayPortAltModeStatus");
                                indentingPrintWriter.println("    <num-lanes>: type int, expected 0, 2, or 4");
                                indentingPrintWriter.println("    <hpd>: type boolean, expected true or false");
                                indentingPrintWriter.println("    <link-training-status>: type LinkTrainingStatus");
                                indentingPrintWriter.println("  dumpsys usb reset-displayport-status \"matrix\"");
                                indentingPrintWriter.println("reset-displayport-status can also be used in order to set");
                                indentingPrintWriter.println("the DisplayPortInfo to default values.");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example enableUsbData");
                                indentingPrintWriter.println("This dumpsys command functions for both simulated and real ports.");
                                indentingPrintWriter.println("  dumpsys usb enable-usb-data \"matrix\" true");
                                indentingPrintWriter.println("  dumpsys usb enable-usb-data \"matrix\" false");
                                indentingPrintWriter.println();
                                indentingPrintWriter.println("Example USB device descriptors:");
                                indentingPrintWriter.println("  dumpsys usb dump-descriptors -dump-short");
                                indentingPrintWriter.println("  dumpsys usb dump-descriptors -dump-tree");
                                indentingPrintWriter.println("  dumpsys usb dump-descriptors -dump-list");
                                indentingPrintWriter.println("  dumpsys usb dump-descriptors -dump-raw");
                            }
                        }
                    }
                    str = "  ";
                }
                if (z5) {
                    dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(fileDescriptor));
                } else {
                    indentingPrintWriter.println("USB MANAGER STATE (dumpsys usb):");
                    dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, str));
                }
                if (this.mDeviceManager != null) {
                    this.mDeviceManager.dump(dualDumpOutputStream, "device_manager", 1146756268033L);
                }
                if (this.mHostManager != null) {
                    this.mHostManager.dump(dualDumpOutputStream, "host_manager", 1146756268034L);
                }
                if (this.mPortManager != null) {
                    this.mPortManager.dump(dualDumpOutputStream, "port_manager", 1146756268035L);
                }
                this.mAlsaManager.dump(dualDumpOutputStream, "alsa_manager", 1146756268036L);
                this.mSettingsManager.dump(dualDumpOutputStream, "settings_manager", 1146756268037L);
                this.mPermissionManager.dump(dualDumpOutputStream, "permissions_manager", 1146756268038L);
                dualDumpOutputStream.flush();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static java.lang.String removeLastChar(java.lang.String str) {
        return str.substring(0, str.length() - 1);
    }

    private class PackageUninstallMonitor extends com.android.internal.content.PackageMonitor {
        private PackageUninstallMonitor() {
        }

        public void onUidRemoved(int i) {
            synchronized (com.android.server.usb.UsbService.this.mUsbDisableRequesters) {
                try {
                    for (java.lang.String str : com.android.server.usb.UsbService.this.mUsbDisableRequesters.keySet()) {
                        android.util.ArraySet arraySet = (android.util.ArraySet) com.android.server.usb.UsbService.this.mUsbDisableRequesters.get(str);
                        if (arraySet != null) {
                            arraySet.remove(java.lang.Integer.valueOf(i));
                            if (arraySet.isEmpty()) {
                                com.android.server.usb.UsbService.this.enableUsbData(str, true, 1, new android.hardware.usb.IUsbOperationInternal.Default());
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class StrongAuthTracker extends com.android.internal.widget.LockPatternUtils.StrongAuthTracker {
        private boolean mLockdownModeStatus;

        StrongAuthTracker(android.content.Context context, android.os.Looper looper) {
            super(context, looper);
        }

        public synchronized void onStrongAuthRequiredChanged(int i) {
            boolean z = (getStrongAuthForUser(i) & 32) != 0;
            if (this.mLockdownModeStatus == z) {
                return;
            }
            this.mLockdownModeStatus = z;
            for (android.hardware.usb.UsbPort usbPort : com.android.server.usb.UsbService.this.mPortManager.getPorts()) {
                com.android.server.usb.UsbService.this.enableUsbData(usbPort.getId(), !z, 2, new android.hardware.usb.IUsbOperationInternal.Default());
            }
        }
    }
}
