package com.android.server.pm.permission;

/* loaded from: classes2.dex */
final class DefaultPermissionGrantPolicy {
    private static final java.lang.String ACTION_TRACK = "com.android.fitness.TRACK";
    private static final java.util.Set<java.lang.String> ACTIVITY_RECOGNITION_PERMISSIONS;
    private static final java.util.Set<java.lang.String> ALWAYS_LOCATION_PERMISSIONS;
    private static final java.lang.String ATTR_CERT = "cert";
    private static final java.lang.String ATTR_FIXED = "fixed";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PACKAGE = "package";
    private static final java.lang.String ATTR_WHITELISTED = "whitelisted";
    private static final java.lang.String AUDIO_MIME_TYPE = "audio/mpeg";
    private static final java.util.Set<java.lang.String> CALENDAR_PERMISSIONS;
    private static final java.util.Set<java.lang.String> CAMERA_PERMISSIONS;
    private static final java.util.Set<java.lang.String> COARSE_BACKGROUND_LOCATION_PERMISSIONS;
    private static final java.util.Set<java.lang.String> CONTACTS_PERMISSIONS;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_INTENT_QUERY_FLAGS = 794624;
    private static final int DEFAULT_PACKAGE_INFO_QUERY_FLAGS = 536915968;
    private static final java.util.Set<java.lang.String> FINE_LOCATION_PERMISSIONS;
    private static final java.util.Set<java.lang.String> FOREGROUND_LOCATION_PERMISSIONS;
    private static final java.util.Set<java.lang.String> MICROPHONE_PERMISSIONS;
    private static final int MSG_READ_DEFAULT_PERMISSION_EXCEPTIONS = 1;
    private static final java.util.Set<java.lang.String> NEARBY_DEVICES_PERMISSIONS;
    private static final java.util.Set<java.lang.String> NOTIFICATION_PERMISSIONS;
    private static final java.util.Set<java.lang.String> PHONE_PERMISSIONS = new android.util.ArraySet();
    private static final java.util.Set<java.lang.String> SENSORS_PERMISSIONS;
    private static final java.util.Set<java.lang.String> SMS_PERMISSIONS;
    private static final java.util.Set<java.lang.String> STORAGE_PERMISSIONS;
    private static final java.lang.String TAG = "DefaultPermGrantPolicy";
    private static final java.lang.String TAG_EXCEPTION = "exception";
    private static final java.lang.String TAG_EXCEPTIONS = "exceptions";
    private static final java.lang.String TAG_PERMISSION = "permission";
    private final android.content.Context mContext;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mDialerAppPackagesProvider;
    private android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant>> mGrantExceptions;
    private final android.os.Handler mHandler;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mLocationExtraPackagesProvider;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mLocationPackagesProvider;
    private final android.content.pm.PackageManagerInternal mServiceInternal;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mSimCallManagerPackagesProvider;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mSmsAppPackagesProvider;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider mSyncAdapterPackagesProvider;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mUseOpenWifiAppPackagesProvider;
    private com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider mVoiceInteractionPackagesProvider;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper NO_PM_CACHE = new com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper() { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy.1
        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public int getPermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            return com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPermissionFlags(str, packageInfo.packageName, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void updatePermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, int i, int i2, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().updatePermissionFlags(str, packageInfo.packageName, i, i2, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void grantPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().grantRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void revokePermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().revokeRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public boolean isGranted(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            return com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.createContextAsUser(userHandle, 0).getPackageManager().checkPermission(str, packageInfo.packageName) == 0;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        @android.annotation.Nullable
        public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str) {
            if (str == null) {
                return null;
            }
            try {
                return com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPermissionInfo(str, 0);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(com.android.server.pm.permission.DefaultPermissionGrantPolicy.TAG, "Permission not found: " + str);
                return null;
            }
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        @android.annotation.Nullable
        public android.content.pm.PackageInfo getPackageInfo(@android.annotation.NonNull java.lang.String str) {
            if (str == null) {
                return null;
            }
            try {
                return com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPackageInfo(str, com.android.server.pm.permission.DefaultPermissionGrantPolicy.DEFAULT_PACKAGE_INFO_QUERY_FLAGS);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(com.android.server.pm.permission.DefaultPermissionGrantPolicy.TAG, "Package not found: " + str);
                return null;
            }
        }
    };

    static {
        PHONE_PERMISSIONS.add("android.permission.READ_PHONE_STATE");
        PHONE_PERMISSIONS.add("android.permission.CALL_PHONE");
        PHONE_PERMISSIONS.add("android.permission.READ_CALL_LOG");
        PHONE_PERMISSIONS.add("android.permission.WRITE_CALL_LOG");
        PHONE_PERMISSIONS.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        PHONE_PERMISSIONS.add("android.permission.USE_SIP");
        PHONE_PERMISSIONS.add("android.permission.PROCESS_OUTGOING_CALLS");
        CONTACTS_PERMISSIONS = new android.util.ArraySet();
        CONTACTS_PERMISSIONS.add("android.permission.READ_CONTACTS");
        CONTACTS_PERMISSIONS.add("android.permission.WRITE_CONTACTS");
        CONTACTS_PERMISSIONS.add("android.permission.GET_ACCOUNTS");
        ALWAYS_LOCATION_PERMISSIONS = new android.util.ArraySet();
        ALWAYS_LOCATION_PERMISSIONS.add("android.permission.ACCESS_FINE_LOCATION");
        ALWAYS_LOCATION_PERMISSIONS.add("android.permission.ACCESS_COARSE_LOCATION");
        ALWAYS_LOCATION_PERMISSIONS.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        FOREGROUND_LOCATION_PERMISSIONS = new android.util.ArraySet();
        FOREGROUND_LOCATION_PERMISSIONS.add("android.permission.ACCESS_FINE_LOCATION");
        FOREGROUND_LOCATION_PERMISSIONS.add("android.permission.ACCESS_COARSE_LOCATION");
        COARSE_BACKGROUND_LOCATION_PERMISSIONS = new android.util.ArraySet();
        COARSE_BACKGROUND_LOCATION_PERMISSIONS.add("android.permission.ACCESS_COARSE_LOCATION");
        COARSE_BACKGROUND_LOCATION_PERMISSIONS.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        FINE_LOCATION_PERMISSIONS = new android.util.ArraySet();
        FINE_LOCATION_PERMISSIONS.add("android.permission.ACCESS_FINE_LOCATION");
        ACTIVITY_RECOGNITION_PERMISSIONS = new android.util.ArraySet();
        ACTIVITY_RECOGNITION_PERMISSIONS.add("android.permission.ACTIVITY_RECOGNITION");
        CALENDAR_PERMISSIONS = new android.util.ArraySet();
        CALENDAR_PERMISSIONS.add("android.permission.READ_CALENDAR");
        CALENDAR_PERMISSIONS.add("android.permission.WRITE_CALENDAR");
        SMS_PERMISSIONS = new android.util.ArraySet();
        SMS_PERMISSIONS.add("android.permission.SEND_SMS");
        SMS_PERMISSIONS.add("android.permission.RECEIVE_SMS");
        SMS_PERMISSIONS.add("android.permission.READ_SMS");
        SMS_PERMISSIONS.add("android.permission.RECEIVE_WAP_PUSH");
        SMS_PERMISSIONS.add("android.permission.RECEIVE_MMS");
        SMS_PERMISSIONS.add("android.permission.READ_CELL_BROADCASTS");
        MICROPHONE_PERMISSIONS = new android.util.ArraySet();
        MICROPHONE_PERMISSIONS.add("android.permission.RECORD_AUDIO");
        CAMERA_PERMISSIONS = new android.util.ArraySet();
        CAMERA_PERMISSIONS.add("android.permission.CAMERA");
        SENSORS_PERMISSIONS = new android.util.ArraySet();
        SENSORS_PERMISSIONS.add("android.permission.BODY_SENSORS");
        SENSORS_PERMISSIONS.add("android.permission.BODY_SENSORS_BACKGROUND");
        STORAGE_PERMISSIONS = new android.util.ArraySet();
        STORAGE_PERMISSIONS.add("android.permission.READ_EXTERNAL_STORAGE");
        STORAGE_PERMISSIONS.add("android.permission.WRITE_EXTERNAL_STORAGE");
        STORAGE_PERMISSIONS.add("android.permission.ACCESS_MEDIA_LOCATION");
        STORAGE_PERMISSIONS.add("android.permission.READ_MEDIA_AUDIO");
        STORAGE_PERMISSIONS.add("android.permission.READ_MEDIA_VIDEO");
        STORAGE_PERMISSIONS.add("android.permission.READ_MEDIA_IMAGES");
        STORAGE_PERMISSIONS.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        NEARBY_DEVICES_PERMISSIONS = new android.util.ArraySet();
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_ADVERTISE");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_CONNECT");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_SCAN");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.UWB_RANGING");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.NEARBY_WIFI_DEVICES");
        NOTIFICATION_PERMISSIONS = new android.util.ArraySet();
        NOTIFICATION_PERMISSIONS.add("android.permission.POST_NOTIFICATIONS");
    }

    DefaultPermissionGrantPolicy(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        serviceThread.start();
        this.mHandler = new android.os.Handler(serviceThread.getLooper()) { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy.2
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                if (message.what == 1) {
                    synchronized (com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mLock) {
                        try {
                            if (com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mGrantExceptions == null) {
                                com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mGrantExceptions = com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.readDefaultPermissionExceptionsLocked(com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE);
                            }
                        } finally {
                        }
                    }
                }
            }
        };
        this.mServiceInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
    }

    public void setLocationPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mLocationPackagesProvider = packagesProvider;
        }
    }

    public void setLocationExtraPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mLocationExtraPackagesProvider = packagesProvider;
        }
    }

    public void setVoiceInteractionPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mVoiceInteractionPackagesProvider = packagesProvider;
        }
    }

    public void setSmsAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mSmsAppPackagesProvider = packagesProvider;
        }
    }

    public void setDialerAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mDialerAppPackagesProvider = packagesProvider;
        }
    }

    public void setSimCallManagerPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mSimCallManagerPackagesProvider = packagesProvider;
        }
    }

    public void setUseOpenWifiAppPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mUseOpenWifiAppPackagesProvider = packagesProvider;
        }
    }

    public void setSyncAdapterPackagesProvider(com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider syncAdapterPackagesProvider) {
        synchronized (this.mLock) {
            this.mSyncAdapterPackagesProvider = syncAdapterPackagesProvider;
        }
    }

    public void grantDefaultPermissions(int i) {
        com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache delayingPackageManagerCache = new com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache();
        grantPermissionsToSysComponentsAndPrivApps(delayingPackageManagerCache, i);
        grantDefaultSystemHandlerPermissions(delayingPackageManagerCache, i);
        grantSignatureAppsNotificationPermissions(delayingPackageManagerCache, i);
        grantDefaultPermissionExceptions(delayingPackageManagerCache, i);
        delayingPackageManagerCache.apply();
    }

    private void grantSignatureAppsNotificationPermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, int i) {
        android.util.Log.i(TAG, "Granting Notification permissions to platform signature apps for user " + i);
        for (android.content.pm.PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackagesAsUser(DEFAULT_PACKAGE_INFO_QUERY_FLAGS, 0)) {
            if (packageInfo != null && packageInfo.applicationInfo.isSystemApp() && packageInfo.applicationInfo.isSignedWithPlatformKey()) {
                grantRuntimePermissionsForSystemPackage(packageManagerWrapper, i, packageInfo, NOTIFICATION_PERMISSIONS);
            }
        }
    }

    private void grantRuntimePermissionsForSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, int i, android.content.pm.PackageInfo packageInfo) {
        grantRuntimePermissionsForSystemPackage(packageManagerWrapper, i, packageInfo, null);
    }

    private void grantRuntimePermissionsForSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, int i, android.content.pm.PackageInfo packageInfo, java.util.Set<java.lang.String> set) {
        if (com.android.internal.util.ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str : packageInfo.requestedPermissions) {
            android.content.pm.PermissionInfo permissionInfo = packageManagerWrapper.getPermissionInfo(str);
            if (permissionInfo != null && ((set == null || set.contains(str)) && permissionInfo.isRuntime())) {
                arraySet.add(str);
            }
        }
        if (!arraySet.isEmpty()) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo, arraySet, true, i);
        }
    }

    public void scheduleReadDefaultPermissionExceptions() {
        this.mHandler.sendEmptyMessage(1);
    }

    private void grantPermissionsToSysComponentsAndPrivApps(com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache delayingPackageManagerCache, int i) {
        android.util.Log.i(TAG, "Granting permissions to platform components for user " + i);
        java.util.List<android.content.pm.PackageInfo> installedPackagesAsUser = this.mContext.getPackageManager().getInstalledPackagesAsUser(DEFAULT_PACKAGE_INFO_QUERY_FLAGS, 0);
        for (android.content.pm.PackageInfo packageInfo : installedPackagesAsUser) {
            if (packageInfo != null) {
                delayingPackageManagerCache.addPackageInfo(packageInfo.packageName, packageInfo);
                if (delayingPackageManagerCache.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo) && doesPackageSupportRuntimePermissions(packageInfo) && !com.android.internal.util.ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
                    grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache, i, packageInfo);
                }
            }
        }
        for (android.content.pm.PackageInfo packageInfo2 : installedPackagesAsUser) {
            if (packageInfo2 != null && doesPackageSupportRuntimePermissions(packageInfo2) && !com.android.internal.util.ArrayUtils.isEmpty(packageInfo2.requestedPermissions) && delayingPackageManagerCache.isGranted("android.permission.READ_PRIVILEGED_PHONE_STATE", packageInfo2, android.os.UserHandle.of(i)) && delayingPackageManagerCache.isGranted("android.permission.READ_PHONE_STATE", packageInfo2, android.os.UserHandle.of(i)) && !delayingPackageManagerCache.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo2)) {
                delayingPackageManagerCache.updatePermissionFlags("android.permission.READ_PHONE_STATE", packageInfo2, 16, 0, android.os.UserHandle.of(i));
            }
        }
    }

    @java.lang.SafeVarargs
    private final void grantIgnoringSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i, java.util.Set<java.lang.String>... setArr) {
        grantPermissionsToPackage(packageManagerWrapper, str, i, true, true, setArr);
    }

    @java.lang.SafeVarargs
    private final void grantSystemFixedPermissionsToSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i, java.util.Set<java.lang.String>... setArr) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, true, setArr);
    }

    @java.lang.SafeVarargs
    private final void grantPermissionsToSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i, java.util.Set<java.lang.String>... setArr) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, false, setArr);
    }

    @java.lang.SafeVarargs
    private final void grantPermissionsToSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i, boolean z, java.util.Set<java.lang.String>... setArr) {
        if (!packageManagerWrapper.isSystemPackage(str)) {
            return;
        }
        grantPermissionsToPackage(packageManagerWrapper, packageManagerWrapper.getSystemPackageInfo(str), i, z, false, true, setArr);
    }

    @java.lang.SafeVarargs
    private final void grantPermissionsToPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i, boolean z, boolean z2, java.util.Set<java.lang.String>... setArr) {
        grantPermissionsToPackage(packageManagerWrapper, packageManagerWrapper.getPackageInfo(str), i, false, z, z2, setArr);
    }

    @java.lang.SafeVarargs
    private final void grantPermissionsToPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, android.content.pm.PackageInfo packageInfo, int i, boolean z, boolean z2, boolean z3, java.util.Set<java.lang.String>... setArr) {
        if (packageInfo != null && doesPackageSupportRuntimePermissions(packageInfo)) {
            for (java.util.Set<java.lang.String> set : setArr) {
                grantRuntimePermissions(packageManagerWrapper, packageInfo, set, z, z2, z3, i);
            }
        }
    }

    private void grantDefaultSystemHandlerPermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, int i) {
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider2;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider3;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider4;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider5;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider6;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider packagesProvider7;
        com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider syncAdapterPackagesProvider;
        java.lang.String str;
        android.util.Log.i(TAG, "Granting permissions to default platform handlers for user " + i);
        synchronized (this.mLock) {
            packagesProvider = this.mLocationPackagesProvider;
            packagesProvider2 = this.mLocationExtraPackagesProvider;
            packagesProvider3 = this.mVoiceInteractionPackagesProvider;
            packagesProvider4 = this.mSmsAppPackagesProvider;
            packagesProvider5 = this.mDialerAppPackagesProvider;
            packagesProvider6 = this.mSimCallManagerPackagesProvider;
            packagesProvider7 = this.mUseOpenWifiAppPackagesProvider;
            syncAdapterPackagesProvider = this.mSyncAdapterPackagesProvider;
        }
        java.lang.String[] packages = packagesProvider3 != null ? packagesProvider3.getPackages(i) : null;
        java.lang.String[] packages2 = packagesProvider != null ? packagesProvider.getPackages(i) : null;
        java.lang.String[] packages3 = packagesProvider2 != null ? packagesProvider2.getPackages(i) : null;
        java.lang.String[] packages4 = packagesProvider4 != null ? packagesProvider4.getPackages(i) : null;
        java.lang.String[] packages5 = packagesProvider5 != null ? packagesProvider5.getPackages(i) : null;
        java.lang.String[] packages6 = packagesProvider6 != null ? packagesProvider6.getPackages(i) : null;
        java.lang.String[] packages7 = packagesProvider7 != null ? packagesProvider7.getPackages(i) : null;
        java.lang.String[] packages8 = syncAdapterPackagesProvider != null ? syncAdapterPackagesProvider.getPackages("com.android.contacts", i) : null;
        java.lang.String[] packages9 = syncAdapterPackagesProvider != null ? syncAdapterPackagesProvider.getPackages("com.android.calendar", i) : null;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, this.mContext.getPackageManager().getPermissionControllerPackageName(), i, NOTIFICATION_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(getKnownPackages(2, i)), i, STORAGE_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        java.lang.String str2 = (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(getKnownPackages(4, i));
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str2, i, STORAGE_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, str2, i, PHONE_PERMISSIONS, SMS_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        java.lang.String str3 = (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(getKnownPackages(1, i));
        grantPermissionsToSystemPackage(packageManagerWrapper, str3, i, PHONE_PERMISSIONS, CONTACTS_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, CAMERA_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str3, i, NOTIFICATION_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSearchSelectorPackage(), i, NOTIFICATION_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultCaptivePortalLoginPackage(), i, NOTIFICATION_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultDockManagerPackage(), i, NOTIFICATION_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.media.action.IMAGE_CAPTURE", i), i, CAMERA_PERMISSIONS, MICROPHONE_PERMISSIONS, STORAGE_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.provider.MediaStore.RECORD_SOUND", i), i, MICROPHONE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("media", i), i, STORAGE_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("downloads", i), i, STORAGE_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.VIEW_DOWNLOADS", i), i, STORAGE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("com.android.externalstorage.documents", i), i, STORAGE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.credentials.INSTALL", i), i, STORAGE_PERMISSIONS);
        if (packages5 == null) {
            grantDefaultPermissionsToDefaultSystemDialerApp(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.DIAL", i), i);
        } else {
            for (java.lang.String str4 : packages5) {
                grantDefaultPermissionsToDefaultSystemDialerApp(packageManagerWrapper, str4, i);
            }
        }
        if (packages6 != null) {
            for (java.lang.String str5 : packages6) {
                grantDefaultPermissionsToDefaultSystemSimCallManager(packageManagerWrapper, str5, i);
            }
        }
        if (packages7 != null) {
            for (java.lang.String str6 : packages7) {
                grantDefaultPermissionsToDefaultSystemUseOpenWifiApp(packageManagerWrapper, str6, i);
            }
        }
        if (packages4 == null) {
            grantDefaultPermissionsToDefaultSystemSmsApp(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_MESSAGING", i), i);
        } else {
            for (java.lang.String str7 : packages4) {
                grantDefaultPermissionsToDefaultSystemSmsApp(packageManagerWrapper, str7, i);
            }
        }
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.provider.Telephony.SMS_CB_RECEIVED", i), i, SMS_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, "android.provider.Telephony.SMS_CARRIER_PROVISION", i), i, SMS_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_CALENDAR", i), i, CALENDAR_PERMISSIONS, CONTACTS_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        java.lang.String defaultProviderAuthorityPackage = getDefaultProviderAuthorityPackage("com.android.calendar", i);
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage, i, CONTACTS_PERMISSIONS, STORAGE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage, i, CALENDAR_PERMISSIONS);
        if (packages9 != null) {
            grantPermissionToEachSystemPackage(packageManagerWrapper, getHeadlessSyncAdapterPackages(packageManagerWrapper, packages9, i), i, CALENDAR_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_CONTACTS", i), i, CONTACTS_PERMISSIONS, PHONE_PERMISSIONS);
        if (packages8 != null) {
            grantPermissionToEachSystemPackage(packageManagerWrapper, getHeadlessSyncAdapterPackages(packageManagerWrapper, packages8, i), i, CONTACTS_PERMISSIONS);
        }
        java.lang.String defaultProviderAuthorityPackage2 = getDefaultProviderAuthorityPackage("com.android.contacts", i);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage2, i, CONTACTS_PERMISSIONS, PHONE_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage2, i, STORAGE_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.app.action.PROVISION_MANAGED_DEVICE", i), i, CONTACTS_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_MAPS", i), i, FOREGROUND_LOCATION_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_EMAIL", i), i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS);
        java.lang.String str8 = (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(getKnownPackages(5, i));
        if (str8 == null) {
            java.lang.String defaultSystemHandlerActivityPackageForCategory = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_BROWSER", i);
            if (packageManagerWrapper.isSystemPackage(defaultSystemHandlerActivityPackageForCategory)) {
                str = defaultSystemHandlerActivityPackageForCategory;
            } else {
                str = null;
            }
        } else {
            str = str8;
        }
        grantPermissionsToPackage(packageManagerWrapper, str, i, false, true, FOREGROUND_LOCATION_PERMISSIONS);
        if (packages != null) {
            for (java.lang.String str9 : packages) {
                grantPermissionsToSystemPackage(packageManagerWrapper, str9, i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS, MICROPHONE_PERMISSIONS, PHONE_PERMISSIONS, SMS_PERMISSIONS, COARSE_BACKGROUND_LOCATION_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS, NOTIFICATION_PERMISSIONS);
                revokeRuntimePermissions(packageManagerWrapper, str9, FINE_LOCATION_PERMISSIONS, false, i);
            }
        }
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.search.action.GLOBAL_SEARCH", i), i, MICROPHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, NOTIFICATION_PERMISSIONS, PHONE_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, new android.content.Intent("android.speech.RecognitionService").addCategory("android.intent.category.DEFAULT"), i), i, MICROPHONE_PERMISSIONS);
        if (packages2 != null) {
            for (java.lang.String str10 : packages2) {
                grantPermissionsToSystemPackage(packageManagerWrapper, str10, i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS, MICROPHONE_PERMISSIONS, PHONE_PERMISSIONS, SMS_PERMISSIONS, CAMERA_PERMISSIONS, SENSORS_PERMISSIONS, STORAGE_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS, NOTIFICATION_PERMISSIONS);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str10, i, ALWAYS_LOCATION_PERMISSIONS, ACTIVITY_RECOGNITION_PERMISSIONS);
            }
        }
        if (packages3 != null) {
            for (java.lang.String str11 : packages3) {
                grantPermissionsToSystemPackage(packageManagerWrapper, str11, i, ALWAYS_LOCATION_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str11, i, ACTIVITY_RECOGNITION_PERMISSIONS);
            }
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new android.content.Intent("android.intent.action.VIEW").addCategory("android.intent.category.DEFAULT").setDataAndType(android.net.Uri.fromFile(new java.io.File("foo.mp3")), AUDIO_MIME_TYPE), i), i, STORAGE_PERMISSIONS);
        char c = 0;
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").addCategory("android.intent.category.LAUNCHER_APP"), i), i, ALWAYS_LOCATION_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
            java.lang.String defaultSystemHandlerActivityPackageForCategory2 = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.HOME_MAIN", i);
            grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory2, i, CONTACTS_PERMISSIONS, MICROPHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS);
            grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory2, i, PHONE_PERMISSIONS, ACTIVITY_RECOGNITION_PERMISSIONS);
            if (this.mContext.getResources().getBoolean(android.R.bool.config_textShareSupported)) {
                android.util.Log.d(TAG, "Wear: Skipping permission grant for Default fitness tracker app : " + defaultSystemHandlerActivityPackageForCategory2);
                c = 0;
            } else {
                c = 0;
                grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, ACTION_TRACK, i), i, SENSORS_PERMISSIONS);
            }
        }
        java.util.Set<java.lang.String>[] setArr = new java.util.Set[2];
        setArr[c] = ALWAYS_LOCATION_PERMISSIONS;
        setArr[1] = NOTIFICATION_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.printspooler", i, setArr);
        java.lang.String defaultSystemHandlerActivityPackage = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.telephony.action.EMERGENCY_ASSISTANCE", i);
        java.util.Set<java.lang.String>[] setArr2 = new java.util.Set[2];
        setArr2[c] = CONTACTS_PERMISSIONS;
        setArr2[1] = PHONE_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage, i, setArr2);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new android.content.Intent("android.intent.action.VIEW").setType("vnd.android.cursor.item/ndef_msg"), i), i, CONTACTS_PERMISSIONS, PHONE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.os.storage.action.MANAGE_STORAGE", i), i, STORAGE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultCompanionDeviceManagerPackage(), i, ALWAYS_LOCATION_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.RINGTONE_PICKER", i), i, STORAGE_PERMISSIONS);
        for (java.lang.String str12 : getKnownPackages(6, i)) {
            grantPermissionsToSystemPackage(packageManagerWrapper, str12, i, COARSE_BACKGROUND_LOCATION_PERMISSIONS, CONTACTS_PERMISSIONS);
        }
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE, i, STORAGE_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.bluetoothmidiservice", i, NEARBY_DEVICES_PERMISSIONS);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, "android.adservices.AD_SERVICES_COMMON_SERVICE", i), i, NOTIFICATION_PERMISSIONS);
    }

    private java.lang.String getDefaultSystemHandlerActivityPackageForCategory(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        return getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new android.content.Intent("android.intent.action.MAIN").addCategory(str), i);
    }

    private java.lang.String getDefaultSearchSelectorPackage() {
        return this.mContext.getString(android.R.string.config_defaultNetworkScorerPackageName);
    }

    private java.lang.String getDefaultCaptivePortalLoginPackage() {
        return this.mContext.getString(android.R.string.config_defaultAmbientContextDetectionService);
    }

    private java.lang.String getDefaultDockManagerPackage() {
        return this.mContext.getString(android.R.string.config_defaultContextualSearchKey);
    }

    private java.lang.String getDefaultCompanionDeviceManagerPackage() {
        return this.mContext.getString(android.R.string.config_cameraLaunchGestureSensorStringType);
    }

    @java.lang.SafeVarargs
    private final void grantPermissionToEachSystemPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.util.ArrayList<java.lang.String> arrayList, int i, java.util.Set<java.lang.String>... setArr) {
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            grantPermissionsToSystemPackage(packageManagerWrapper, arrayList.get(i2), i, setArr);
        }
    }

    @android.annotation.NonNull
    private java.lang.String[] getKnownPackages(int i, int i2) {
        return this.mServiceInternal.getKnownPackageNames(i, i2);
    }

    private void grantDefaultPermissionsToDefaultSystemDialerApp(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        if (str == null) {
            return;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
            grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        } else {
            grantPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, CONTACTS_PERMISSIONS, SMS_PERMISSIONS, MICROPHONE_PERMISSIONS, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(packageManagerWrapper, str, i, NEARBY_DEVICES_PERMISSIONS);
        }
    }

    private void grantDefaultPermissionsToDefaultSystemSmsApp(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS, CONTACTS_PERMISSIONS, SMS_PERMISSIONS, STORAGE_PERMISSIONS, MICROPHONE_PERMISSIONS, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
    }

    private void grantDefaultPermissionsToDefaultSystemUseOpenWifiApp(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, ALWAYS_LOCATION_PERMISSIONS);
    }

    public void grantDefaultPermissionsToDefaultUseOpenWifiApp(java.lang.String str, int i) {
        android.util.Log.i(TAG, "Granting permissions to default Use Open WiFi app for user:" + i);
        grantIgnoringSystemPackage(this.NO_PM_CACHE, str, i, ALWAYS_LOCATION_PERMISSIONS);
    }

    public void grantDefaultPermissionsToDefaultSimCallManager(java.lang.String str, int i) {
        grantDefaultPermissionsToDefaultSimCallManager(this.NO_PM_CACHE, str, i);
    }

    private void grantDefaultPermissionsToDefaultSimCallManager(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        if (str == null) {
            return;
        }
        android.util.Log.i(TAG, "Granting permissions to sim call manager for user:" + i);
        grantPermissionsToPackage(packageManagerWrapper, str, i, false, true, PHONE_PERMISSIONS, MICROPHONE_PERMISSIONS);
    }

    private void grantDefaultPermissionsToDefaultSystemSimCallManager(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        if (packageManagerWrapper.isSystemPackage(str)) {
            grantDefaultPermissionsToDefaultSimCallManager(packageManagerWrapper, str, i);
        }
    }

    public void grantDefaultPermissionsToEnabledCarrierApps(java.lang.String[] strArr, int i) {
        android.util.Log.i(TAG, "Granting permissions to enabled carrier apps for user:" + i);
        if (strArr == null) {
            return;
        }
        for (java.lang.String str : strArr) {
            grantPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, SMS_PERMISSIONS);
        }
    }

    public void grantDefaultPermissionsToEnabledImsServices(java.lang.String[] strArr, int i) {
        android.util.Log.i(TAG, "Granting permissions to enabled ImsServices for user:" + i);
        if (strArr == null) {
            return;
        }
        for (java.lang.String str : strArr) {
            grantPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, MICROPHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, CAMERA_PERMISSIONS, CONTACTS_PERMISSIONS);
        }
    }

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(java.lang.String[] strArr, int i) {
        android.util.Log.i(TAG, "Granting permissions to enabled data services for user:" + i);
        if (strArr == null) {
            return;
        }
        for (java.lang.String str : strArr) {
            grantSystemFixedPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS);
        }
    }

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(java.lang.String[] strArr, int i) {
        android.util.Log.i(TAG, "Revoking permissions from disabled data services for user:" + i);
        if (strArr == null) {
            return;
        }
        for (java.lang.String str : strArr) {
            android.content.pm.PackageInfo systemPackageInfo = this.NO_PM_CACHE.getSystemPackageInfo(str);
            if (this.NO_PM_CACHE.isSystemPackage(systemPackageInfo) && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                revokeRuntimePermissions(this.NO_PM_CACHE, str, PHONE_PERMISSIONS, true, i);
                revokeRuntimePermissions(this.NO_PM_CACHE, str, ALWAYS_LOCATION_PERMISSIONS, true, i);
            }
        }
    }

    public void grantDefaultPermissionsToActiveLuiApp(java.lang.String str, int i) {
        android.util.Log.i(TAG, "Granting permissions to active LUI app for user:" + i);
        grantSystemFixedPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
    }

    public void revokeDefaultPermissionsFromLuiApps(java.lang.String[] strArr, int i) {
        android.util.Log.i(TAG, "Revoke permissions from LUI apps for user:" + i);
        if (strArr == null) {
            return;
        }
        for (java.lang.String str : strArr) {
            android.content.pm.PackageInfo systemPackageInfo = this.NO_PM_CACHE.getSystemPackageInfo(str);
            if (this.NO_PM_CACHE.isSystemPackage(systemPackageInfo) && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                revokeRuntimePermissions(this.NO_PM_CACHE, str, CAMERA_PERMISSIONS, true, i);
            }
        }
    }

    public void grantDefaultPermissionsToCarrierServiceApp(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(TAG, "Grant permissions to Carrier Service app " + str + " for user:" + i);
        grantPermissionsToPackage(this.NO_PM_CACHE, str, i, false, true, NOTIFICATION_PERMISSIONS);
    }

    private java.lang.String getDefaultSystemHandlerActivityPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        return getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new android.content.Intent(str), i);
    }

    private java.lang.String getDefaultSystemHandlerActivityPackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, android.content.Intent intent, int i) {
        android.content.pm.ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent, DEFAULT_INTENT_QUERY_FLAGS, i);
        if (resolveActivityAsUser == null || resolveActivityAsUser.activityInfo == null || this.mServiceInternal.isResolveActivityComponent(resolveActivityAsUser.activityInfo)) {
            return null;
        }
        java.lang.String str = resolveActivityAsUser.activityInfo.packageName;
        if (packageManagerWrapper.isSystemPackage(str)) {
            return str;
        }
        return null;
    }

    private java.lang.String getDefaultSystemHandlerServicePackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, int i) {
        return getDefaultSystemHandlerServicePackage(packageManagerWrapper, new android.content.Intent(str), i);
    }

    private java.lang.String getDefaultSystemHandlerServicePackage(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, android.content.Intent intent, int i) {
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(intent, DEFAULT_INTENT_QUERY_FLAGS, i);
        if (queryIntentServicesAsUser == null) {
            return null;
        }
        int size = queryIntentServicesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.String str = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo.packageName;
            if (packageManagerWrapper.isSystemPackage(str)) {
                return str;
            }
        }
        return null;
    }

    private java.util.ArrayList<java.lang.String> getHeadlessSyncAdapterPackages(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String[] strArr, int i) {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        android.content.Intent addCategory = new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        for (java.lang.String str : strArr) {
            addCategory.setPackage(str);
            if (this.mContext.getPackageManager().resolveActivityAsUser(addCategory, DEFAULT_INTENT_QUERY_FLAGS, i) == null && packageManagerWrapper.isSystemPackage(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private java.lang.String getDefaultProviderAuthorityPackage(java.lang.String str, int i) {
        android.content.pm.ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(str, DEFAULT_INTENT_QUERY_FLAGS, i);
        if (resolveContentProviderAsUser != null) {
            return resolveContentProviderAsUser.packageName;
        }
        return null;
    }

    private void grantRuntimePermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, android.content.pm.PackageInfo packageInfo, java.util.Set<java.lang.String> set, boolean z, int i) {
        grantRuntimePermissions(packageManagerWrapper, packageInfo, set, z, false, true, i);
    }

    private void revokeRuntimePermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, java.lang.String str, java.util.Set<java.lang.String> set, boolean z, int i) {
        android.content.pm.PackageInfo systemPackageInfo = packageManagerWrapper.getSystemPackageInfo(str);
        if (systemPackageInfo == null || com.android.internal.util.ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(java.util.Arrays.asList(systemPackageInfo.requestedPermissions));
        for (java.lang.String str2 : set) {
            if (arraySet.contains(str2)) {
                android.os.UserHandle of = android.os.UserHandle.of(i);
                int permissionFlags = packageManagerWrapper.getPermissionFlags(str2, packageManagerWrapper.getPackageInfo(str), of);
                if ((permissionFlags & 32) != 0 && (permissionFlags & 4) == 0 && ((permissionFlags & 16) == 0 || z)) {
                    packageManagerWrapper.revokePermission(str2, systemPackageInfo, of);
                    packageManagerWrapper.updatePermissionFlags(str2, systemPackageInfo, 32, 0, of);
                }
            }
        }
    }

    private boolean isFixedOrUserSet(int i) {
        return (i & 23) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void grantRuntimePermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, android.content.pm.PackageInfo packageInfo, java.util.Set<java.lang.String> set, boolean z, boolean z2, boolean z3, int i) {
        int i2;
        java.lang.String[] strArr;
        android.util.ArraySet arraySet;
        int i3;
        int i4;
        java.lang.String str;
        java.lang.String str2;
        android.content.pm.PackageInfo systemPackageInfo;
        android.os.UserHandle of = android.os.UserHandle.of(i);
        if (packageInfo == null) {
            return;
        }
        java.lang.String[] strArr2 = packageInfo.requestedPermissions;
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr2)) {
            return;
        }
        java.lang.String[] strArr3 = packageManagerWrapper.getPackageInfo(packageInfo.packageName).requestedPermissions;
        int length = strArr2.length;
        for (int i5 = 0; i5 < length; i5++) {
            if (!com.android.internal.util.ArrayUtils.contains(strArr3, strArr2[i5])) {
                strArr2[i5] = null;
            }
        }
        java.lang.String[] strArr4 = (java.lang.String[]) com.android.internal.util.ArrayUtils.filterNotNull(strArr2, new java.util.function.IntFunction() { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i6) {
                java.lang.String[] lambda$grantRuntimePermissions$0;
                lambda$grantRuntimePermissions$0 = com.android.server.pm.permission.DefaultPermissionGrantPolicy.lambda$grantRuntimePermissions$0(i6);
                return lambda$grantRuntimePermissions$0;
            }
        });
        android.util.ArraySet arraySet2 = new android.util.ArraySet(set);
        android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (!z) {
            i2 = 32;
        } else {
            i2 = 48;
        }
        java.util.List splitPermissions = ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).getSplitPermissions();
        int size = splitPermissions.size();
        for (int i6 = 0; i6 < size; i6++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = (android.permission.PermissionManager.SplitPermissionInfo) splitPermissions.get(i6);
            if (applicationInfo != null && applicationInfo.targetSdkVersion < splitPermissionInfo.getTargetSdk() && set.contains(splitPermissionInfo.getSplitPermission())) {
                arraySet2.addAll(splitPermissionInfo.getNewPermissions());
            }
        }
        if (!z2 && applicationInfo != null && applicationInfo.isUpdatedSystemApp() && (systemPackageInfo = packageManagerWrapper.getSystemPackageInfo(this.mServiceInternal.getDisabledSystemPackageName(packageInfo.packageName))) != null) {
            if (com.android.internal.util.ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
                return;
            }
            if (!java.util.Arrays.equals(strArr4, systemPackageInfo.requestedPermissions)) {
                android.util.ArraySet arraySet3 = new android.util.ArraySet(java.util.Arrays.asList(strArr4));
                strArr = systemPackageInfo.requestedPermissions;
                arraySet = arraySet3;
                int length2 = strArr.length;
                java.lang.String[] strArr5 = new java.lang.String[length2];
                int i7 = 0;
                int i8 = 0;
                for (java.lang.String str3 : strArr) {
                    if (packageManagerWrapper.getBackgroundPermission(str3) != null) {
                        strArr5[i8] = str3;
                        i8++;
                    } else {
                        strArr5[(length2 - 1) - i7] = str3;
                        i7++;
                    }
                }
                for (java.lang.String str4 : strArr) {
                    if ((arraySet == null || arraySet.contains(str4)) && arraySet2.contains(str4)) {
                        int permissionFlags = packageManagerWrapper.getPermissionFlags(str4, packageInfo, of);
                        boolean z4 = z && (permissionFlags & 16) != 0;
                        if (isFixedOrUserSet(permissionFlags) && !z2 && !z4) {
                            i3 = i2;
                            i4 = permissionFlags;
                            str2 = str4;
                        } else if ((permissionFlags & 4) == 0) {
                            i3 = i2 | (permissionFlags & 14336);
                            if (!z3 || !packageManagerWrapper.isPermissionRestricted(str4)) {
                                i4 = permissionFlags;
                                str = str4;
                            } else {
                                i4 = permissionFlags;
                                str = str4;
                                packageManagerWrapper.updatePermissionFlags(str4, packageInfo, 4096, 4096, of);
                            }
                            if (z4) {
                                packageManagerWrapper.updatePermissionFlags(str, packageInfo, i4, i4 & (-17), of);
                            }
                            java.lang.String str5 = str;
                            if (!packageManagerWrapper.isGranted(str5, packageInfo, of)) {
                                packageManagerWrapper.grantPermission(str5, packageInfo, of);
                            }
                            str2 = str5;
                            packageManagerWrapper.updatePermissionFlags(str5, packageInfo, i3 | 64, i3, of);
                        }
                        if ((i4 & 32) != 0 && (i4 & 16) != 0 && !z) {
                            packageManagerWrapper.updatePermissionFlags(str2, packageInfo, 16, 0, of);
                        }
                        i2 = i3;
                    }
                }
            }
        }
        strArr = strArr4;
        arraySet = null;
        int length22 = strArr.length;
        java.lang.String[] strArr52 = new java.lang.String[length22];
        int i72 = 0;
        int i82 = 0;
        while (r2 < length22) {
        }
        while (r15 < length22) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String[] lambda$grantRuntimePermissions$0(int i) {
        return new java.lang.String[i];
    }

    private void grantDefaultPermissionExceptions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, int i) {
        android.util.ArraySet arraySet;
        int i2;
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            try {
                if (this.mGrantExceptions == null) {
                    this.mGrantExceptions = readDefaultPermissionExceptionsLocked(packageManagerWrapper);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size = this.mGrantExceptions.size();
        android.util.ArraySet arraySet2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.PackageInfo packageInfo = packageManagerWrapper.getPackageInfo(this.mGrantExceptions.keyAt(i3));
            java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant> valueAt = this.mGrantExceptions.valueAt(i3);
            int size2 = valueAt.size();
            int i4 = 0;
            while (i4 < size2) {
                com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant defaultPermissionGrant = valueAt.get(i4);
                if (!packageManagerWrapper.isPermissionDangerous(defaultPermissionGrant.name)) {
                    android.util.Log.w(TAG, "Ignoring permission " + defaultPermissionGrant.name + " which isn't dangerous");
                    i2 = i4;
                } else {
                    if (arraySet2 == null) {
                        arraySet = new android.util.ArraySet();
                    } else {
                        arraySet2.clear();
                        arraySet = arraySet2;
                    }
                    arraySet.add(defaultPermissionGrant.name);
                    i2 = i4;
                    grantRuntimePermissions(packageManagerWrapper, packageInfo, arraySet, defaultPermissionGrant.fixed, defaultPermissionGrant.whitelisted, true, i);
                    arraySet2 = arraySet;
                }
                i4 = i2 + 1;
            }
        }
    }

    private java.io.File[] getDefaultPermissionFiles() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.File file = new java.io.File(android.os.Environment.getRootDirectory(), "etc/default-permissions");
        if (file.isDirectory() && file.canRead()) {
            java.util.Collections.addAll(arrayList, file.listFiles());
        }
        java.io.File file2 = new java.io.File(android.os.Environment.getVendorDirectory(), "etc/default-permissions");
        if (file2.isDirectory() && file2.canRead()) {
            java.util.Collections.addAll(arrayList, file2.listFiles());
        }
        java.io.File file3 = new java.io.File(android.os.Environment.getOdmDirectory(), "etc/default-permissions");
        if (file3.isDirectory() && file3.canRead()) {
            java.util.Collections.addAll(arrayList, file3.listFiles());
        }
        java.io.File file4 = new java.io.File(android.os.Environment.getProductDirectory(), "etc/default-permissions");
        if (file4.isDirectory() && file4.canRead()) {
            java.util.Collections.addAll(arrayList, file4.listFiles());
        }
        java.io.File file5 = new java.io.File(android.os.Environment.getSystemExtDirectory(), "etc/default-permissions");
        if (file5.isDirectory() && file5.canRead()) {
            java.util.Collections.addAll(arrayList, file5.listFiles());
        }
        java.io.File file6 = new java.io.File(android.os.Environment.getOemDirectory(), "etc/default-permissions");
        if (file6.isDirectory() && file6.canRead()) {
            java.util.Collections.addAll(arrayList, file6.listFiles());
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (java.io.File[]) arrayList.toArray(new java.io.File[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant>> readDefaultPermissionExceptionsLocked(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper) {
        java.io.File[] defaultPermissionFiles = getDefaultPermissionFiles();
        if (defaultPermissionFiles == null) {
            return new android.util.ArrayMap<>(0);
        }
        android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant>> arrayMap = new android.util.ArrayMap<>();
        for (java.io.File file : defaultPermissionFiles) {
            if (!file.getPath().endsWith(".xml")) {
                android.util.Slog.i(TAG, "Non-xml file " + file + " in " + file.getParent() + " directory, ignoring");
            } else if (file.canRead()) {
                try {
                    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                    try {
                        parse(packageManagerWrapper, android.util.Xml.resolvePullParser(fileInputStream), arrayMap);
                        fileInputStream.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.w(TAG, "Error reading default permissions file " + file, e);
                }
            } else {
                android.util.Slog.w(TAG, "Default permissions file " + file + " cannot be read");
            }
        }
        return arrayMap;
    }

    private void parse(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.Map<java.lang.String, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant>> map) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (TAG_EXCEPTIONS.equals(typedXmlPullParser.getName())) {
                        parseExceptions(packageManagerWrapper, typedXmlPullParser, map);
                    } else {
                        android.util.Log.e(TAG, "Unknown tag " + typedXmlPullParser.getName());
                    }
                }
            } else {
                return;
            }
        }
    }

    private void parseExceptions(com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper packageManagerWrapper, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.Map<java.lang.String, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant>> map) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (TAG_EXCEPTION.equals(typedXmlPullParser.getName())) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "package");
                        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_CERT);
                        java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant> list = map.get(attributeValue);
                        if (list == null) {
                            android.content.pm.PackageInfo packageInfo = packageManagerWrapper.getPackageInfo(attributeValue);
                            if (packageInfo == null) {
                                android.util.Log.w(TAG, "No such package:" + attributeValue);
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                            } else if (!isSystemOrCertificateMatchingPackage(packageInfo, attributeValue2)) {
                                android.util.Log.w(TAG, "Not system or certificate-matching package: " + attributeValue);
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                            } else if (!doesPackageSupportRuntimePermissions(packageInfo)) {
                                android.util.Log.w(TAG, "Skipping non supporting runtime permissions package:" + attributeValue);
                                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                            } else {
                                list = new java.util.ArrayList<>();
                                map.put(attributeValue, list);
                            }
                        }
                        parsePermission(typedXmlPullParser, list);
                    } else {
                        android.util.Log.e(TAG, "Unknown tag " + typedXmlPullParser.getName() + "under <exceptions>");
                    }
                }
            } else {
                return;
            }
        }
    }

    private void parsePermission(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.List<com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant> list) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (TAG_PERMISSION.contains(typedXmlPullParser.getName())) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                        if (attributeValue == null) {
                            android.util.Log.w(TAG, "Mandatory name attribute missing for permission tag");
                            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                        } else {
                            list.add(new com.android.server.pm.permission.DefaultPermissionGrantPolicy.DefaultPermissionGrant(attributeValue, typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_FIXED, false), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_WHITELISTED, false)));
                        }
                    } else {
                        android.util.Log.e(TAG, "Unknown tag " + typedXmlPullParser.getName() + "under <exception>");
                    }
                }
            } else {
                return;
            }
        }
    }

    private boolean isSystemOrCertificateMatchingPackage(android.content.pm.PackageInfo packageInfo, java.lang.String str) {
        if (str == null) {
            return packageInfo.applicationInfo.isSystemApp();
        }
        return this.mContext.getPackageManager().hasSigningCertificate(packageInfo.packageName, libcore.util.HexEncoding.decode(str.replace(":", "")), 1);
    }

    private static boolean doesPackageSupportRuntimePermissions(android.content.pm.PackageInfo packageInfo) {
        return packageInfo.applicationInfo != null && packageInfo.applicationInfo.targetSdkVersion > 22;
    }

    private abstract class PackageManagerWrapper {
        @android.annotation.Nullable
        abstract android.content.pm.PackageInfo getPackageInfo(@android.annotation.NonNull java.lang.String str);

        abstract int getPermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle);

        @android.annotation.Nullable
        abstract android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str);

        abstract void grantPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle);

        abstract boolean isGranted(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle);

        abstract void revokePermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle);

        abstract void updatePermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, int i, int i2, @android.annotation.NonNull android.os.UserHandle userHandle);

        private PackageManagerWrapper() {
        }

        @android.annotation.Nullable
        android.content.pm.PackageInfo getSystemPackageInfo(@android.annotation.NonNull java.lang.String str) {
            android.content.pm.PackageInfo packageInfo = getPackageInfo(str);
            if (packageInfo == null || !packageInfo.applicationInfo.isSystemApp()) {
                return null;
            }
            return packageInfo;
        }

        boolean isPermissionRestricted(@android.annotation.NonNull java.lang.String str) {
            android.content.pm.PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return false;
            }
            return permissionInfo.isRestricted();
        }

        boolean isPermissionDangerous(@android.annotation.NonNull java.lang.String str) {
            android.content.pm.PermissionInfo permissionInfo = getPermissionInfo(str);
            return permissionInfo != null && permissionInfo.getProtection() == 1;
        }

        @android.annotation.Nullable
        java.lang.String getBackgroundPermission(@android.annotation.NonNull java.lang.String str) {
            android.content.pm.PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return null;
            }
            return permissionInfo.backgroundPermission;
        }

        boolean isSystemPackage(@android.annotation.Nullable java.lang.String str) {
            return isSystemPackage(getPackageInfo(str));
        }

        boolean isSystemPackage(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
            return (packageInfo == null || !packageInfo.applicationInfo.isSystemApp() || isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo)) ? false : true;
        }

        boolean isSysComponentOrPersistentPlatformSignedPrivApp(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo) {
            if (android.os.UserHandle.getAppId(packageInfo.applicationInfo.uid) < 10000) {
                return true;
            }
            if (!packageInfo.applicationInfo.isPrivilegedApp()) {
                return false;
            }
            android.content.pm.PackageInfo systemPackageInfo = getSystemPackageInfo(com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mServiceInternal.getDisabledSystemPackageName(packageInfo.applicationInfo.packageName));
            if (systemPackageInfo != null) {
                android.content.pm.ApplicationInfo applicationInfo = systemPackageInfo.applicationInfo;
                if (applicationInfo != null && (applicationInfo.flags & 8) == 0) {
                    return false;
                }
            } else if ((packageInfo.applicationInfo.flags & 8) == 0) {
                return false;
            }
            return com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mServiceInternal.isPlatformSigned(packageInfo.packageName);
        }
    }

    private class DelayingPackageManagerCache extends com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper {
        private final android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState>> mDelayedPermissionState;
        private final android.util.ArrayMap<java.lang.String, android.content.pm.PackageInfo> mPackageInfos;
        private final android.util.ArrayMap<java.lang.String, android.content.pm.PermissionInfo> mPermissionInfos;
        private final android.util.SparseArray<android.content.Context> mUserContexts;

        private DelayingPackageManagerCache() {
            super();
            this.mDelayedPermissionState = new android.util.SparseArray<>();
            this.mUserContexts = new android.util.SparseArray<>();
            this.mPermissionInfos = new android.util.ArrayMap<>();
            this.mPackageInfos = new android.util.ArrayMap<>();
        }

        void apply() {
            android.content.pm.PackageManager.corkPackageInfoCache();
            for (int i = 0; i < this.mDelayedPermissionState.size(); i++) {
                for (int i2 = 0; i2 < this.mDelayedPermissionState.valueAt(i).size(); i2++) {
                    try {
                        this.mDelayedPermissionState.valueAt(i).valueAt(i2).apply();
                    } catch (java.lang.IllegalArgumentException e) {
                        android.util.Slog.w(com.android.server.pm.permission.DefaultPermissionGrantPolicy.TAG, "Cannot set permission " + this.mDelayedPermissionState.valueAt(i).keyAt(i2) + " of uid " + this.mDelayedPermissionState.keyAt(i), e);
                    }
                }
            }
            android.content.pm.PackageManager.uncorkPackageInfoCache();
        }

        void addPackageInfo(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo) {
            this.mPackageInfos.put(str, packageInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public android.content.Context createContextAsUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
            int indexOfKey = this.mUserContexts.indexOfKey(userHandle.getIdentifier());
            if (indexOfKey >= 0) {
                return this.mUserContexts.valueAt(indexOfKey);
            }
            android.content.Context createContextAsUser = com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.mContext.createContextAsUser(userHandle, 0);
            this.mUserContexts.put(userHandle.getIdentifier(), createContextAsUser);
            return createContextAsUser;
        }

        @android.annotation.NonNull
        private com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState getPermissionState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState> arrayMap;
            int uid = android.os.UserHandle.getUid(userHandle.getIdentifier(), android.os.UserHandle.getAppId(packageInfo.applicationInfo.uid));
            int indexOfKey = this.mDelayedPermissionState.indexOfKey(uid);
            if (indexOfKey >= 0) {
                arrayMap = this.mDelayedPermissionState.valueAt(indexOfKey);
            } else {
                android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState> arrayMap2 = new android.util.ArrayMap<>();
                this.mDelayedPermissionState.put(uid, arrayMap2);
                arrayMap = arrayMap2;
            }
            int indexOfKey2 = arrayMap.indexOfKey(str);
            if (indexOfKey2 >= 0) {
                com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState valueAt = arrayMap.valueAt(indexOfKey2);
                if (!com.android.internal.util.ArrayUtils.contains(valueAt.mPkgRequestingPerm.requestedPermissions, str) && com.android.internal.util.ArrayUtils.contains(packageInfo.requestedPermissions, str)) {
                    valueAt.mPkgRequestingPerm = packageInfo;
                    return valueAt;
                }
                return valueAt;
            }
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = new com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState(str, packageInfo, userHandle);
            arrayMap.put(str, permissionState);
            return permissionState;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public int getPermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            return permissionState.newFlags.intValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void updatePermissionFlags(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, int i, int i2, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            permissionState.newFlags = java.lang.Integer.valueOf((permissionState.newFlags.intValue() & (~i)) | (i & i2));
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void grantPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = true;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void revokePermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = false;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public boolean isGranted(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            return permissionState.newGranted.booleanValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        @android.annotation.Nullable
        public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str) {
            int indexOfKey = this.mPermissionInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return this.mPermissionInfos.valueAt(indexOfKey);
            }
            android.content.pm.PermissionInfo permissionInfo = com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPermissionInfo(str);
            this.mPermissionInfos.put(str, permissionInfo);
            return permissionInfo;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        @android.annotation.Nullable
        public android.content.pm.PackageInfo getPackageInfo(@android.annotation.NonNull java.lang.String str) {
            int indexOfKey = this.mPackageInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return this.mPackageInfos.valueAt(indexOfKey);
            }
            android.content.pm.PackageInfo packageInfo = com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPackageInfo(str);
            this.mPackageInfos.put(str, packageInfo);
            return packageInfo;
        }

        private class PermissionState {

            @android.annotation.Nullable
            private java.lang.Integer mOriginalFlags;

            @android.annotation.Nullable
            private java.lang.Boolean mOriginalGranted;

            @android.annotation.NonNull
            private final java.lang.String mPermission;

            @android.annotation.NonNull
            private android.content.pm.PackageInfo mPkgRequestingPerm;

            @android.annotation.NonNull
            private final android.os.UserHandle mUser;

            @android.annotation.Nullable
            java.lang.Integer newFlags;

            @android.annotation.Nullable
            java.lang.Boolean newGranted;

            private PermissionState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
                this.mPermission = str;
                this.mPkgRequestingPerm = packageInfo;
                this.mUser = userHandle;
            }

            void apply() {
                int i;
                int i2;
                if (this.newFlags == null) {
                    i = 0;
                    i2 = 0;
                } else {
                    i = this.newFlags.intValue() & (~this.mOriginalFlags.intValue());
                    i2 = this.mOriginalFlags.intValue() & (~this.newFlags.intValue());
                }
                if (i2 != 0) {
                    com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i2, 0, this.mUser);
                }
                int i3 = i & 14336;
                if (i3 != 0) {
                    com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i3, -1, this.mUser);
                }
                if (this.newGranted != null && !java.util.Objects.equals(this.newGranted, this.mOriginalGranted)) {
                    if (this.newGranted.booleanValue()) {
                        com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.grantPermission(this.mPermission, this.mPkgRequestingPerm, this.mUser);
                    } else {
                        com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.revokePermission(this.mPermission, this.mPkgRequestingPerm, this.mUser);
                    }
                }
                int i4 = i & (-14337);
                if (i4 != 0) {
                    com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i4, -1, this.mUser);
                }
            }

            void initFlags() {
                if (this.newFlags == null) {
                    this.mOriginalFlags = java.lang.Integer.valueOf(com.android.server.pm.permission.DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPermissionFlags(this.mPermission, this.mPkgRequestingPerm, this.mUser));
                    this.newFlags = this.mOriginalFlags;
                }
            }

            void initGranted() {
                if (this.newGranted == null) {
                    this.mOriginalGranted = java.lang.Boolean.valueOf(com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache.this.createContextAsUser(this.mUser).getPackageManager().checkPermission(this.mPermission, this.mPkgRequestingPerm.packageName) == 0);
                    this.newGranted = this.mOriginalGranted;
                }
            }
        }
    }

    private static final class DefaultPermissionGrant {
        final boolean fixed;
        final java.lang.String name;
        final boolean whitelisted;

        public DefaultPermissionGrant(java.lang.String str, boolean z, boolean z2) {
            this.name = str;
            this.fixed = z;
            this.whitelisted = z2;
        }
    }
}
