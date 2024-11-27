package com.android.server.media;

/* loaded from: classes2.dex */
class MediaRouter2ServiceImpl {
    private static final long DUMMY_REQUEST_ID = -1;
    private static final int REQUIRED_PACKAGE_IMPORTANCE_FOR_SCANNING = 100;
    final android.app.ActivityManager mActivityManager;
    private final android.content.Context mContext;
    final android.os.PowerManager mPowerManager;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private static final java.lang.String TAG = "MR2ServiceImpl";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.String[] BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING = {"android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"};
    private final java.lang.Object mLock = new java.lang.Object();
    final java.util.concurrent.atomic.AtomicInteger mNextRouterOrManagerId = new java.util.concurrent.atomic.AtomicInteger(1);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.media.MediaRouter2ServiceImpl.UserRecord> mUserRecords = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> mAllRouterRecords = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> mAllManagerRecords = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentActiveUserId = -1;
    private final android.app.ActivityManager.OnUidImportanceListener mOnUidImportanceListener = new android.app.ActivityManager.OnUidImportanceListener() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda13
        public final void onUidImportance(int i, int i2) {
            com.android.server.media.MediaRouter2ServiceImpl.this.lambda$new$0(i, i2);
        }
    };
    private final android.content.BroadcastReceiver mScreenOnOffReceiver = new com.android.server.media.MediaRouter2ServiceImpl.AnonymousClass1();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2) {
        synchronized (this.mLock) {
            try {
                int size = this.mUserRecords.size();
                for (int i3 = 0; i3 < size; i3++) {
                    this.mUserRecords.valueAt(i3).mHandler.maybeUpdateDiscoveryPreferenceForUid(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.media.MediaRouter2ServiceImpl$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.media.MediaRouter2ServiceImpl.this.mLock) {
                try {
                    int size = com.android.server.media.MediaRouter2ServiceImpl.this.mUserRecords.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.media.MediaRouter2ServiceImpl.UserHandler userHandler = ((com.android.server.media.MediaRouter2ServiceImpl.UserRecord) com.android.server.media.MediaRouter2ServiceImpl.this.mUserRecords.valueAt(i)).mHandler;
                        userHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).updateDiscoveryPreferenceOnHandler();
                            }
                        }, userHandler));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS")
    MediaRouter2ServiceImpl(android.content.Context context) {
        this.mContext = context;
        this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
        this.mActivityManager.addOnUidImportanceListener(this.mOnUidImportanceListener, 100);
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        if (!com.android.media.flags.Flags.disableScreenOffBroadcastReceiver()) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.mContext.registerReceiver(this.mScreenOnOffReceiver, intentFilter);
        }
        this.mContext.getPackageManager().addOnPermissionsChangeListener(new android.content.pm.PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda14
            public final void onPermissionsChanged(int i) {
                com.android.server.media.MediaRouter2ServiceImpl.this.onPermissionsChanged(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPermissionsChanged(final int i) {
        synchronized (this.mLock) {
            try {
                java.util.Optional<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> findFirst = this.mAllRouterRecords.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$onPermissionsChanged$1;
                        lambda$onPermissionsChanged$1 = com.android.server.media.MediaRouter2ServiceImpl.lambda$onPermissionsChanged$1(i, (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj);
                        return lambda$onPermissionsChanged$1;
                    }
                }).findFirst();
                if (findFirst.isPresent()) {
                    findFirst.get().maybeUpdateSystemRoutingPermissionLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPermissionsChanged$1(int i, com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
        return routerRecord.mUid == i;
    }

    @android.annotation.NonNull
    public java.util.List<android.media.MediaRoute2Info> getSystemRoutes() {
        java.util.Collection arrayList;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean checkCallerHasSystemRoutingPermissions = checkCallerHasSystemRoutingPermissions(callingPid, callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    com.android.server.media.MediaRouter2ServiceImpl.UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(identifier);
                    if (checkCallerHasSystemRoutingPermissions) {
                        android.media.MediaRoute2ProviderInfo providerInfo = orCreateUserRecordLocked.mHandler.mSystemProvider.getProviderInfo();
                        if (providerInfo != null) {
                            arrayList = providerInfo.getRoutes();
                        } else {
                            arrayList = java.util.Collections.emptyList();
                        }
                    } else {
                        arrayList = new java.util.ArrayList();
                        arrayList.add(orCreateUserRecordLocked.mHandler.mSystemProvider.getDefaultRoute());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return new java.util.ArrayList(arrayList);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("packageName must not be empty");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY") == 0;
        boolean checkCallerHasModifyAudioRoutingPermission = checkCallerHasModifyAudioRoutingPermission(callingPid, callingUid);
        boolean checkMediaRoutingControlPermission = checkMediaRoutingControlPermission(callingUid, callingPid, str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerRouter2Locked(iMediaRouter2, callingUid, callingPid, str, identifier, z, checkCallerHasModifyAudioRoutingPermission, checkMediaRoutingControlPermission);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                unregisterRouter2Locked(iMediaRouter2, false);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateScanningState(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, int i) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        validateScanningStateValue(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                updateScanningStateLocked(iMediaRouter2, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDiscoveryRequestWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
                if (routerRecord == null) {
                    android.util.Slog.w(TAG, "Ignoring updating discoveryRequest of null routerRecord.");
                } else {
                    setDiscoveryRequestWithRouter2Locked(routerRecord, routeDiscoveryPreference);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRouteListingPreference(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.Nullable android.media.RouteListingPreference routeListingPreference) {
        android.content.ComponentName componentName;
        if (routeListingPreference != null) {
            componentName = routeListingPreference.getLinkedItemComponentName();
        } else {
            componentName = null;
        }
        if (componentName != null) {
            com.android.server.media.MediaServerUtils.enforcePackageName(this.mContext, componentName.getPackageName(), android.os.Binder.getCallingUid());
            if (!com.android.server.media.MediaServerUtils.isValidActivityComponentName(this.mContext, componentName, "android.media.action.TRANSFER_MEDIA", android.os.Binder.getCallingUserHandle())) {
                throw new java.lang.IllegalArgumentException("Unable to resolve " + componentName + " to a valid activity for android.media.action.TRANSFER_MEDIA");
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
                if (routerRecord == null) {
                    android.util.Slog.w(TAG, "Ignoring updating route listing of null routerRecord.");
                } else {
                    setRouteListingPreferenceLocked(routerRecord, routeListingPreference);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRouteVolumeWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setRouteVolumeWithRouter2Locked(iMediaRouter2, mediaRoute2Info, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestCreateSessionWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, int i, long j, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, android.os.Bundle bundle, @android.annotation.Nullable android.os.UserHandle userHandle, @android.annotation.Nullable java.lang.String str) {
        java.lang.String packageName;
        android.os.UserHandle userHandle2;
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        synchronized (this.mLock) {
            if (j == 0 || userHandle == null || str == null) {
                try {
                    com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
                    android.os.UserHandle callingUserHandle = android.os.Binder.getCallingUserHandle();
                    if (routerRecord != null) {
                        packageName = routerRecord.mPackageName;
                        userHandle2 = callingUserHandle;
                    } else {
                        packageName = this.mContext.getPackageName();
                        userHandle2 = callingUserHandle;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            } else {
                userHandle2 = userHandle;
                packageName = str;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestCreateSessionWithRouter2Locked(i, j, userHandle2, packageName, iMediaRouter2, routingSessionInfo, mediaRoute2Info, bundle);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void selectRouteWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                selectRouteWithRouter2Locked(iMediaRouter2, str, mediaRoute2Info);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void deselectRouteWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                deselectRouteWithRouter2Locked(iMediaRouter2, str, mediaRoute2Info);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void transferToRouteWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        android.os.UserHandle callingUserHandle = android.os.Binder.getCallingUserHandle();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                transferToRouteWithRouter2Locked(iMediaRouter2, callingUserHandle, str, mediaRoute2Info);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSessionVolumeWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        java.util.Objects.requireNonNull(str, "uniqueSessionId must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setSessionVolumeWithRouter2Locked(iMediaRouter2, str, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void releaseSessionWithRouter2(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(iMediaRouter2, "router must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                releaseSessionWithRouter2Locked(iMediaRouter2, str);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.media.RoutingSessionInfo> getRemoteSessions(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager) {
        java.util.List<android.media.RoutingSessionInfo> remoteSessionsLocked;
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                remoteSessionsLocked = getRemoteSessionsLocked(iMediaRouter2Manager);
            }
            return remoteSessionsLocked;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.RequiresPermission("android.permission.MEDIA_CONTENT_CONTROL")
    public void registerManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("callerPackageName must not be empty");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        android.os.UserHandle callingUserHandle = android.os.Binder.getCallingUserHandle();
        enforcePrivilegedRoutingPermissions(callingUid, callingPid, str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerManagerLocked(iMediaRouter2Manager, callingUid, callingPid, str, null, callingUserHandle);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MEDIA_CONTENT_CONTROL", "android.permission.MEDIA_ROUTING_CONTROL"})
    public void registerProxyRouter(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        java.util.Objects.requireNonNull(userHandle, "targetUser must not be null");
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("targetPackageName must not be empty");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            enforcePrivilegedRoutingPermissions(callingUid, callingPid, str);
            enforceCrossUserPermissions(callingUid, callingPid, userHandle);
            if (!verifyPackageExistsForUser(str2, userHandle)) {
                throw new java.lang.IllegalArgumentException("targetPackageName does not exist: " + str2);
            }
            synchronized (this.mLock) {
                registerManagerLocked(iMediaRouter2Manager, callingUid, callingPid, str, str2, userHandle);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void unregisterManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                unregisterManagerLocked(iMediaRouter2Manager, false);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateScanningState(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        validateScanningStateValue(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                updateScanningStateLocked(iMediaRouter2Manager, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRouteVolumeWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i2) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setRouteVolumeWithManagerLocked(i, iMediaRouter2Manager, mediaRoute2Info, i2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestCreateSessionWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        java.util.Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        java.util.Objects.requireNonNull(userHandle);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestCreateSessionWithManagerLocked(i, iMediaRouter2Manager, routingSessionInfo, mediaRoute2Info, userHandle, str);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void selectRouteWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                selectRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void deselectRouteWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                deselectRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void transferToRouteWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str2) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        java.util.Objects.requireNonNull(userHandle);
        java.util.Objects.requireNonNull(str2);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                transferToRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info, 1, userHandle, str2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSessionVolumeWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull java.lang.String str, int i2) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setSessionVolumeWithManagerLocked(i, iMediaRouter2Manager, str, i2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void releaseSessionWithManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                releaseSessionWithManagerLocked(i, iMediaRouter2Manager, str);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.media.RoutingSessionInfo getSystemSessionInfo(@android.annotation.Nullable java.lang.String str, boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean checkCallerHasSystemRoutingPermissions = checkCallerHasSystemRoutingPermissions(callingPid, callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                com.android.server.media.MediaRouter2ServiceImpl.UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(identifier);
                if (!checkCallerHasSystemRoutingPermissions) {
                    return new android.media.RoutingSessionInfo.Builder(orCreateUserRecordLocked.mHandler.mSystemProvider.getDefaultSessionInfo()).setClientPackageName(str).build();
                }
                if (z) {
                    return orCreateUserRecordLocked.mHandler.mSystemProvider.generateDeviceRouteSelectedSessionInfo(str);
                }
                java.util.List<android.media.RoutingSessionInfo> sessionInfos = orCreateUserRecordLocked.mHandler.mSystemProvider.getSessionInfos();
                if (!sessionInfos.isEmpty()) {
                    return new android.media.RoutingSessionInfo.Builder(sessionInfos.get(0)).setClientPackageName(str).build();
                }
                android.util.Slog.w(TAG, "System provider does not have any session info.");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean checkCallerHasSystemRoutingPermissions(int i, int i2) {
        return checkCallerHasModifyAudioRoutingPermission(i, i2) || checkCallerHasBluetoothPermissions(i, i2);
    }

    private boolean checkCallerHasModifyAudioRoutingPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.MODIFY_AUDIO_ROUTING", i, i2) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCallerHasBluetoothPermissions(int i, int i2) {
        boolean z = true;
        for (java.lang.String str : BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING) {
            z &= this.mContext.checkPermission(str, i, i2) == 0;
        }
        return z;
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MEDIA_ROUTING_CONTROL", "android.permission.MEDIA_CONTENT_CONTROL"})
    private void enforcePrivilegedRoutingPermissions(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        if (this.mContext.checkPermission("android.permission.MEDIA_CONTENT_CONTROL", i2, i) == 0) {
            return;
        }
        if (!com.android.media.flags.Flags.enablePrivilegedRoutingForMediaRoutingControl()) {
            throw new java.lang.SecurityException("Must hold MEDIA_CONTENT_CONTROL");
        }
        if (!checkMediaRoutingControlPermission(i, i2, str)) {
            throw new java.lang.SecurityException("Must hold MEDIA_CONTENT_CONTROL or MEDIA_ROUTING_CONTROL permissions.");
        }
    }

    private boolean checkMediaRoutingControlPermission(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(this.mContext, "android.permission.MEDIA_ROUTING_CONTROL", i2, i, str, (java.lang.String) null, "Checking permissions for registering manager in MediaRouter2ServiceImpl.") == 0;
    }

    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    private boolean verifyPackageExistsForUser(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        try {
            this.mContext.getPackageManager().getPackageInfoAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L), userHandle.getIdentifier());
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void enforceCrossUserPermissions(int i, int i2, @android.annotation.NonNull android.os.UserHandle userHandle) {
        if (userHandle.getIdentifier() != android.os.UserHandle.getUserId(i)) {
            this.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS_FULL", i2, i, "Must hold INTERACT_ACROSS_USERS_FULL to control an app in a different userId.");
        }
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        printWriter.println(str + "MediaRouter2ServiceImpl");
        java.lang.String str2 = str + "  ";
        synchronized (this.mLock) {
            try {
                printWriter.println(str2 + "mNextRouterOrManagerId=" + this.mNextRouterOrManagerId.get());
                printWriter.println(str2 + "mCurrentActiveUserId=" + this.mCurrentActiveUserId);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(str2);
                sb.append("UserRecords:");
                printWriter.println(sb.toString());
                if (this.mUserRecords.size() > 0) {
                    for (int i = 0; i < this.mUserRecords.size(); i++) {
                        this.mUserRecords.valueAt(i).dump(printWriter, str2 + "  ");
                    }
                } else {
                    printWriter.println(str2 + "  <no user records>");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void updateRunningUserAndProfiles(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentActiveUserId != i) {
                    android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("switchUser | user: %d", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
                    this.mCurrentActiveUserId = i;
                    android.util.SparseArray<com.android.server.media.MediaRouter2ServiceImpl.UserRecord> clone = this.mUserRecords.clone();
                    for (int i2 = 0; i2 < clone.size(); i2++) {
                        int keyAt = clone.keyAt(i2);
                        com.android.server.media.MediaRouter2ServiceImpl.UserRecord valueAt = clone.valueAt(i2);
                        if (isUserActiveLocked(keyAt)) {
                            valueAt.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda18(), valueAt.mHandler));
                        } else {
                            valueAt.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda16(), valueAt.mHandler));
                            disposeUserIfNeededLocked(valueAt);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void routerDied(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
        synchronized (this.mLock) {
            unregisterRouter2Locked(routerRecord.mRouter, true);
        }
    }

    void managerDied(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord) {
        synchronized (this.mLock) {
            unregisterManagerLocked(managerRecord.mManager, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUserActiveLocked(int i) {
        return this.mUserManagerInternal.getProfileParentId(i) == this.mCurrentActiveUserId;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, boolean z, boolean z2, boolean z3) {
        android.os.IBinder asBinder = iMediaRouter2.asBinder();
        if (this.mAllRouterRecords.get(asBinder) == null) {
            com.android.server.media.MediaRouter2ServiceImpl.UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(i3);
            com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = new com.android.server.media.MediaRouter2ServiceImpl.RouterRecord(orCreateUserRecordLocked, iMediaRouter2, i, i2, str, z, z2, z3);
            try {
                asBinder.linkToDeath(routerRecord, 0);
                orCreateUserRecordLocked.mRouterRecords.add(routerRecord);
                this.mAllRouterRecords.put(asBinder, routerRecord);
                orCreateUserRecordLocked.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda17
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).notifyRouterRegistered((com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj2);
                    }
                }, orCreateUserRecordLocked.mHandler, routerRecord));
                android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("registerRouter2 | package: %s, uid: %d, pid: %d, router id: %d, hasMediaRoutingControl: %b", new java.lang.Object[]{str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(routerRecord.mRouterId), java.lang.Boolean.valueOf(z3)}));
                return;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("MediaRouter2 died prematurely.", e);
            }
        }
        android.util.Slog.w(TAG, "registerRouter2Locked: Same router already exists. packageName=" + str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unregisterRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, boolean z) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord remove = this.mAllRouterRecords.remove(iMediaRouter2.asBinder());
        if (remove == null) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Ignoring unregistering unknown router: %s, died: %b", new java.lang.Object[]{iMediaRouter2, java.lang.Boolean.valueOf(z)}));
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("unregisterRouter2 | package: %s, router id: %d, died: %b", new java.lang.Object[]{remove.mPackageName, java.lang.Integer.valueOf(remove.mRouterId), java.lang.Boolean.valueOf(z)}));
        com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord = remove.mUserRecord;
        userRecord.mRouterRecords.remove(remove);
        remove.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(), remove.mUserRecord.mHandler, remove.mPackageName, (java.lang.Object) null));
        remove.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(), remove.mUserRecord.mHandler, remove.mPackageName, (java.lang.Object) null));
        userRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(), userRecord.mHandler));
        remove.dispose();
        disposeUserIfNeededLocked(userRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateScanningStateLocked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, int i) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            android.util.Slog.w(TAG, "Router record not found. Ignoring updateScanningState call.");
        } else {
            if (i == 2 && !routerRecord.mHasMediaRoutingControl) {
                throw new java.lang.SecurityException("Screen off scan requires MEDIA_ROUTING_CONTROL");
            }
            android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("updateScanningStateLocked | router: %d, packageName: %s, scanningState: %d", new java.lang.Object[]{java.lang.Integer.valueOf(routerRecord.mRouterId), routerRecord.mPackageName, getScanningStateString(i)}));
            routerRecord.updateScanningState(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setDiscoveryRequestWithRouter2Locked(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        if (routerRecord.mDiscoveryPreference.equals(routeDiscoveryPreference)) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setDiscoveryRequestWithRouter2 | router: %s(id: %d), discovery request: %s", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), routeDiscoveryPreference.toString()}));
        routerRecord.mDiscoveryPreference = routeDiscoveryPreference;
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(), routerRecord.mUserRecord.mHandler, routerRecord.mPackageName, routerRecord.mDiscoveryPreference));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(), routerRecord.mUserRecord.mHandler));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setRouteListingPreferenceLocked(com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.Nullable android.media.RouteListingPreference routeListingPreference) {
        java.lang.String str;
        routerRecord.mRouteListingPreference = routeListingPreference;
        if (routeListingPreference != null) {
            str = (java.lang.String) routeListingPreference.getItems().stream().map(new java.util.function.Function() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.media.RouteListingPreference.Item) obj).getRouteId();
                }
            }).collect(java.util.stream.Collectors.joining(","));
        } else {
            str = null;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setRouteListingPreference | router: %s(id: %d), route listing preference: [%s]", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), str}));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(), routerRecord.mUserRecord.mHandler, routerRecord.mPackageName, routeListingPreference));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setRouteVolumeWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord != null) {
            android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setRouteVolumeWithRouter2 | router: %s(id: %d), volume: %d", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), java.lang.Integer.valueOf(i)}));
            routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(), routerRecord.mUserRecord.mHandler, -1L, mediaRoute2Info, java.lang.Integer.valueOf(i)));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestCreateSessionWithRouter2Locked(int i, long j, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.Nullable android.os.Bundle bundle) {
        android.media.MediaRoute2Info mediaRoute2Info2;
        android.media.MediaRoute2Info mediaRoute2Info3;
        android.media.MediaRoute2Info mediaRoute2Info4;
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("requestCreateSessionWithRouter2 | router: %s(id: %d), old session id: %s, new session's route id: %s, request id: %d", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), routingSessionInfo.getId(), mediaRoute2Info.getId(), java.lang.Integer.valueOf(i)}));
        if (j != 0) {
            com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord findManagerWithId = routerRecord.mUserRecord.mHandler.findManagerWithId(toRequesterId(j));
            if (findManagerWithId == null || findManagerWithId.mLastSessionCreationRequest == null) {
                android.util.Slog.w(TAG, "requestCreateSessionWithRouter2Locked: Ignoring unknown request.");
                routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
            if (!android.text.TextUtils.equals(findManagerWithId.mLastSessionCreationRequest.mOldSession.getId(), routingSessionInfo.getId())) {
                android.util.Slog.w(TAG, "requestCreateSessionWithRouter2Locked: Ignoring unmatched routing session.");
                routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
            if (android.text.TextUtils.equals(findManagerWithId.mLastSessionCreationRequest.mRoute.getId(), mediaRoute2Info.getId())) {
                mediaRoute2Info4 = mediaRoute2Info;
            } else {
                if (routerRecord.hasSystemRoutingPermission() || !findManagerWithId.mLastSessionCreationRequest.mRoute.isSystemRoute() || !mediaRoute2Info.isSystemRoute()) {
                    android.util.Slog.w(TAG, "requestCreateSessionWithRouter2Locked: Ignoring unmatched route.");
                    routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                    return;
                }
                mediaRoute2Info4 = findManagerWithId.mLastSessionCreationRequest.mRoute;
            }
            findManagerWithId.mLastSessionCreationRequest = null;
            mediaRoute2Info3 = mediaRoute2Info4;
        } else {
            if (!mediaRoute2Info.isSystemRoute()) {
                mediaRoute2Info2 = mediaRoute2Info;
            } else if (routerRecord.hasSystemRoutingPermission()) {
                mediaRoute2Info2 = mediaRoute2Info;
            } else if (android.text.TextUtils.equals(mediaRoute2Info.getId(), "DEFAULT_ROUTE")) {
                mediaRoute2Info2 = mediaRoute2Info;
            } else {
                android.util.Slog.w(TAG, "MODIFY_AUDIO_ROUTING permission is required to transfer to" + mediaRoute2Info);
                routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
            mediaRoute2Info3 = mediaRoute2Info2;
        }
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.NonaConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda19
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9) {
                ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).requestCreateSessionWithRouter2OnHandler(((java.lang.Long) obj2).longValue(), ((java.lang.Long) obj3).longValue(), (android.os.UserHandle) obj4, (java.lang.String) obj5, (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj6, (android.media.RoutingSessionInfo) obj7, (android.media.MediaRoute2Info) obj8, (android.os.Bundle) obj9);
            }
        }, routerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(routerRecord.mRouterId, i)), java.lang.Long.valueOf(j), userHandle, str, routerRecord, routingSessionInfo, mediaRoute2Info3, bundle));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void selectRouteWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("selectRouteWithRouter2 | router: %s(id: %d), route: %s", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), mediaRoute2Info.getId()}));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(), routerRecord.mUserRecord.mHandler, -1L, routerRecord, str, mediaRoute2Info));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void deselectRouteWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("deselectRouteWithRouter2 | router: %s(id: %d), route: %s", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), mediaRoute2Info.getId()}));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda2(), routerRecord.mUserRecord.mHandler, -1L, routerRecord, str, mediaRoute2Info));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void transferToRouteWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("transferToRouteWithRouter2 | router: %s(id: %d), route: %s", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), mediaRoute2Info.getId()}));
        if (mediaRoute2Info.isSystemRoute() && !routerRecord.hasSystemRoutingPermission() && !android.text.TextUtils.equals(mediaRoute2Info.getId(), "DEFAULT_ROUTE")) {
            routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda8
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).notifySessionCreationFailedToRouter((com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, routerRecord.mUserRecord.mHandler, routerRecord, java.lang.Integer.valueOf(toOriginalRequestId(-1L))));
        } else {
            routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda9(), routerRecord.mUserRecord.mHandler, -1L, userHandle, routerRecord.mPackageName, routerRecord, str, mediaRoute2Info, 2));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setSessionVolumeWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setSessionVolumeWithRouter2 | router: %s(id: %d), session: %s, volume: %d", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), str, java.lang.Integer.valueOf(i)}));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(), routerRecord.mUserRecord.mHandler, -1L, str, java.lang.Integer.valueOf(i)));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void releaseSessionWithRouter2Locked(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.NonNull java.lang.String str) {
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("releaseSessionWithRouter2 | router: %s(id: %d), session: %s", new java.lang.Object[]{routerRecord.mPackageName, java.lang.Integer.valueOf(routerRecord.mRouterId), str}));
        routerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda6(), routerRecord.mUserRecord.mHandler, -1L, routerRecord, str));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.media.RoutingSessionInfo> getRemoteSessionsLocked(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            android.util.Slog.w(TAG, "getRemoteSessionLocked: Ignoring unknown manager");
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = managerRecord.mUserRecord.mHandler.mRouteProviders.iterator();
        while (it.hasNext()) {
            com.android.server.media.MediaRoute2Provider mediaRoute2Provider = (com.android.server.media.MediaRoute2Provider) it.next();
            if (!mediaRoute2Provider.mIsSystemRouteProvider) {
                arrayList.addAll(mediaRoute2Provider.getSessionInfos());
            }
        }
        return arrayList;
    }

    @android.annotation.RequiresPermission("android.permission.MEDIA_CONTENT_CONTROL")
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerManagerLocked(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull android.os.UserHandle userHandle) {
        android.os.IBinder asBinder = iMediaRouter2Manager.asBinder();
        if (this.mAllManagerRecords.get(asBinder) == null) {
            boolean checkMediaRoutingControlPermission = checkMediaRoutingControlPermission(i, i2, str);
            android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("registerManager | callerUid: %d, callerPid: %d, callerPackage: %s, targetPackageName: %s, targetUserId: %d, hasMediaRoutingControl: %b", new java.lang.Object[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, userHandle, java.lang.Boolean.valueOf(checkMediaRoutingControlPermission)}));
            com.android.server.media.MediaRouter2ServiceImpl.UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(userHandle.getIdentifier());
            com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = new com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord(orCreateUserRecordLocked, iMediaRouter2Manager, i, i2, str, str2, checkMediaRoutingControlPermission);
            try {
                asBinder.linkToDeath(managerRecord, 0);
                orCreateUserRecordLocked.mManagerRecords.add(managerRecord);
                this.mAllManagerRecords.put(asBinder, managerRecord);
                java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = orCreateUserRecordLocked.mRouterRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.media.MediaRouter2ServiceImpl.RouterRecord next = it.next();
                    orCreateUserRecordLocked.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(), next.mUserRecord.mHandler, next.mPackageName, next.mRouteListingPreference));
                    next.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda11
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                            ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).notifyDiscoveryPreferenceChangedToManager((com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj2, (android.media.IMediaRouter2Manager) obj3);
                        }
                    }, next.mUserRecord.mHandler, next, iMediaRouter2Manager));
                }
                orCreateUserRecordLocked.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda12
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).notifyInitialRoutesToManager((android.media.IMediaRouter2Manager) obj2);
                    }
                }, orCreateUserRecordLocked.mHandler, iMediaRouter2Manager));
                return;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Media router manager died prematurely.", e);
            }
        }
        android.util.Slog.w(TAG, "registerManagerLocked: Same manager already exists. callerPackageName=" + str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unregisterManagerLocked(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, boolean z) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord remove = this.mAllManagerRecords.remove(iMediaRouter2Manager.asBinder());
        if (remove == null) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Ignoring unregistering unknown manager: %s, died: %b", new java.lang.Object[]{iMediaRouter2Manager, java.lang.Boolean.valueOf(z)}));
            return;
        }
        com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord = remove.mUserRecord;
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("unregisterManager | package: %s, user: %d, manager: %d, died: %b", new java.lang.Object[]{remove.mOwnerPackageName, java.lang.Integer.valueOf(userRecord.mUserId), java.lang.Integer.valueOf(remove.mManagerId), java.lang.Boolean.valueOf(z)}));
        userRecord.mManagerRecords.remove(remove);
        remove.dispose();
        disposeUserIfNeededLocked(userRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateScanningStateLocked(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            android.util.Slog.w(TAG, "Manager record not found. Ignoring updateScanningState call.");
        } else {
            if (!managerRecord.mHasMediaRoutingControl && i == 2) {
                throw new java.lang.SecurityException("Screen off scan requires MEDIA_ROUTING_CONTROL");
            }
            android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("updateScanningState | manager: %d, ownerPackageName: %s, targetPackageName: %s, scanningState: %d", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), managerRecord.mOwnerPackageName, managerRecord.mTargetPackageName, getScanningStateString(i)}));
            managerRecord.updateScanningState(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setRouteVolumeWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i2) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setRouteVolumeWithManager | manager: %d, route: %s, volume: %d", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), mediaRoute2Info.getId(), java.lang.Integer.valueOf(i2)}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), mediaRoute2Info, java.lang.Integer.valueOf(i2)));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestCreateSessionWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("requestCreateSessionWithManager | manager: %d, route: %s", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), mediaRoute2Info.getId()}));
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord findRouterRecordLocked = managerRecord.mUserRecord.findRouterRecordLocked(routingSessionInfo.getClientPackageName());
        if (findRouterRecordLocked != null) {
            long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
            com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest = managerRecord.mLastSessionCreationRequest;
            if (sessionCreationRequest != null) {
                android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("requestCreateSessionWithManagerLocked: Notifying failure for pending session creation request - oldSession: %s, route: %s", new java.lang.Object[]{sessionCreationRequest.mOldSession, sessionCreationRequest.mRoute}));
                managerRecord.mUserRecord.mHandler.notifyRequestFailedToManager(managerRecord.mManager, toOriginalRequestId(sessionCreationRequest.mManagerRequestId), 0);
            }
            managerRecord.mLastSessionCreationRequest = new com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest(findRouterRecordLocked, 0L, uniqueRequestId, routingSessionInfo, mediaRoute2Info);
            findRouterRecordLocked.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.OctConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda15
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).requestRouterCreateSessionOnHandler(((java.lang.Long) obj2).longValue(), (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj3, (com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord) obj4, (android.media.RoutingSessionInfo) obj5, (android.media.MediaRoute2Info) obj6, (android.os.UserHandle) obj7, (java.lang.String) obj8);
                }
            }, findRouterRecordLocked.mUserRecord.mHandler, java.lang.Long.valueOf(uniqueRequestId), findRouterRecordLocked, managerRecord, routingSessionInfo, mediaRoute2Info, userHandle, str));
            return;
        }
        android.util.Slog.w(TAG, "requestCreateSessionWithManagerLocked: Ignoring session creation for unknown router.");
        try {
            managerRecord.mManager.notifyRequestFailed(i, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "requestCreateSessionWithManagerLocked: Failed to notify failure. Manager probably died.");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void selectRouteWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("selectRouteWithManager | manager: %d, session: %s, route: %s", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str), str, mediaRoute2Info));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void deselectRouteWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("deselectRouteWithManager | manager: %d, session: %s, route: %s", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda2(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str), str, mediaRoute2Info));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void transferToRouteWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i2, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str2) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("transferToRouteWithManager | manager: %d, session: %s, route: %s", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda9(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), userHandle, str2, managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str), str, mediaRoute2Info, java.lang.Integer.valueOf(i2)));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setSessionVolumeWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, int i2) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("setSessionVolumeWithManager | manager: %d, session: %s, volume: %d", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), str, java.lang.Integer.valueOf(i2)}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), str, java.lang.Integer.valueOf(i2)));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void releaseSessionWithManagerLocked(int i, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str) {
        com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord = this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("releaseSessionWithManager | manager: %d, session: %s", new java.lang.Object[]{java.lang.Integer.valueOf(managerRecord.mManagerId), str}));
        managerRecord.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda6(), managerRecord.mUserRecord.mHandler, java.lang.Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)), managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str), str));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.media.MediaRouter2ServiceImpl.UserRecord getOrCreateUserRecordLocked(int i) {
        com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord = this.mUserRecords.get(i);
        if (userRecord == null) {
            userRecord = new com.android.server.media.MediaRouter2ServiceImpl.UserRecord(i);
            this.mUserRecords.put(i, userRecord);
            userRecord.init();
            if (isUserActiveLocked(i)) {
                userRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda18(), userRecord.mHandler));
            }
        }
        return userRecord;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void disposeUserIfNeededLocked(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord) {
        if (!isUserActiveLocked(userRecord.mUserId) && userRecord.mRouterRecords.isEmpty() && userRecord.mManagerRecords.isEmpty()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, userRecord + ": Disposed");
            }
            userRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda16(), userRecord.mHandler));
            this.mUserRecords.remove(userRecord.mUserId);
        }
    }

    static long toUniqueRequestId(int i, int i2) {
        return i2 | (i << 32);
    }

    static int toRequesterId(long j) {
        return (int) (j >> 32);
    }

    static int toOriginalRequestId(long j) {
        return (int) j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getScanningStateString(int i) {
        switch (i) {
            case 0:
                return "NOT_SCANNING";
            case 1:
                return "SCREEN_ON_ONLY";
            case 2:
                return "FULL";
            default:
                return "Invalid scanning state: " + i;
        }
    }

    private static void validateScanningStateValue(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("Scanning state %d is not valid.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
        }
    }

    final class UserRecord {
        final com.android.server.media.MediaRouter2ServiceImpl.UserHandler mHandler;
        public final int mUserId;
        final java.util.ArrayList<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> mRouterRecords = new java.util.ArrayList<>();
        final java.util.ArrayList<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> mManagerRecords = new java.util.ArrayList<>();
        android.media.RouteDiscoveryPreference mCompositeDiscoveryPreference = android.media.RouteDiscoveryPreference.EMPTY;
        java.util.Set<java.lang.String> mActivelyScanningPackages = java.util.Set.of();

        UserRecord(int i) {
            this.mUserId = i;
            this.mHandler = new com.android.server.media.MediaRouter2ServiceImpl.UserHandler(com.android.server.media.MediaRouter2ServiceImpl.this, this);
        }

        void init() {
            this.mHandler.init();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord findRouterRecordLocked(java.lang.String str) {
            java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = this.mRouterRecords.iterator();
            while (it.hasNext()) {
                com.android.server.media.MediaRouter2ServiceImpl.RouterRecord next = it.next();
                if (android.text.TextUtils.equals(next.mPackageName, str)) {
                    return next;
                }
            }
            return null;
        }

        public void dump(@android.annotation.NonNull final java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            printWriter.println(str + "UserRecord");
            final java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mUserId=" + this.mUserId);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str2);
            sb.append("Router Records:");
            printWriter.println(sb.toString());
            if (!this.mRouterRecords.isEmpty()) {
                java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = this.mRouterRecords.iterator();
                while (it.hasNext()) {
                    it.next().dump(printWriter, str2 + "  ");
                }
            } else {
                printWriter.println(str2 + "<no router records>");
            }
            printWriter.println(str2 + "Manager Records:");
            if (!this.mManagerRecords.isEmpty()) {
                java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> it2 = this.mManagerRecords.iterator();
                while (it2.hasNext()) {
                    it2.next().dump(printWriter, str2 + "  ");
                }
            } else {
                printWriter.println(str2 + "<no manager records>");
            }
            printWriter.println(str2 + "Composite discovery preference:");
            this.mCompositeDiscoveryPreference.dump(printWriter, str2 + "  ");
            printWriter.println(str2 + "Packages actively scanning: " + java.lang.String.join(", ", this.mActivelyScanningPackages));
            if (!this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRouter2ServiceImpl.UserRecord.this.lambda$dump$0(printWriter, str2);
                }
            }, 1000L)) {
                printWriter.println(str2 + "<could not dump handler state>");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dump$0(java.io.PrintWriter printWriter, java.lang.String str) {
            this.mHandler.dump(printWriter, str);
        }
    }

    final class RouterRecord implements android.os.IBinder.DeathRecipient {
        public final java.util.concurrent.atomic.AtomicBoolean mHasBluetoothRoutingPermission;
        public final boolean mHasConfigureWifiDisplayPermission;
        public final boolean mHasMediaRoutingControl;
        public final boolean mHasModifyAudioRoutingPermission;
        public final java.lang.String mPackageName;
        public final int mPid;

        @android.annotation.Nullable
        public android.media.RouteListingPreference mRouteListingPreference;
        public final android.media.IMediaRouter2 mRouter;
        public final int mRouterId;
        public final int mUid;
        public final com.android.server.media.MediaRouter2ServiceImpl.UserRecord mUserRecord;
        public int mScanningState = 0;
        public final java.util.List<java.lang.Integer> mSelectRouteSequenceNumbers = new java.util.ArrayList();
        public android.media.RouteDiscoveryPreference mDiscoveryPreference = android.media.RouteDiscoveryPreference.EMPTY;

        RouterRecord(com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord, android.media.IMediaRouter2 iMediaRouter2, int i, int i2, java.lang.String str, boolean z, boolean z2, boolean z3) {
            this.mUserRecord = userRecord;
            this.mPackageName = str;
            this.mRouter = iMediaRouter2;
            this.mUid = i;
            this.mPid = i2;
            this.mHasConfigureWifiDisplayPermission = z;
            this.mHasModifyAudioRoutingPermission = z2;
            this.mHasBluetoothRoutingPermission = new java.util.concurrent.atomic.AtomicBoolean(com.android.server.media.MediaRouter2ServiceImpl.this.checkCallerHasBluetoothPermissions(this.mPid, this.mUid));
            this.mHasMediaRoutingControl = z3;
            this.mRouterId = com.android.server.media.MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
        }

        public boolean hasSystemRoutingPermission() {
            return this.mHasModifyAudioRoutingPermission || this.mHasBluetoothRoutingPermission.get();
        }

        public boolean isActivelyScanning() {
            return this.mScanningState == 1 || this.mScanningState == 2 || this.mDiscoveryPreference.shouldPerformActiveScan();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void maybeUpdateSystemRoutingPermissionLocked() {
            java.util.Map map;
            android.media.RoutingSessionInfo defaultSessionInfo;
            boolean hasSystemRoutingPermission = hasSystemRoutingPermission();
            this.mHasBluetoothRoutingPermission.set(com.android.server.media.MediaRouter2ServiceImpl.this.checkCallerHasBluetoothPermissions(this.mPid, this.mUid));
            boolean hasSystemRoutingPermission2 = hasSystemRoutingPermission();
            if (hasSystemRoutingPermission != hasSystemRoutingPermission2) {
                if (hasSystemRoutingPermission2) {
                    map = this.mUserRecord.mHandler.mLastNotifiedRoutesToPrivilegedRouters;
                } else {
                    map = this.mUserRecord.mHandler.mLastNotifiedRoutesToNonPrivilegedRouters;
                }
                notifyRoutesUpdated(map.values().stream().toList());
                java.util.List<android.media.RoutingSessionInfo> sessionInfos = this.mUserRecord.mHandler.mSystemProvider.getSessionInfos();
                if (hasSystemRoutingPermission2 && !sessionInfos.isEmpty()) {
                    defaultSessionInfo = sessionInfos.get(0);
                } else {
                    defaultSessionInfo = this.mUserRecord.mHandler.mSystemProvider.getDefaultSessionInfo();
                }
                notifySessionInfoChanged(defaultSessionInfo);
            }
        }

        public void dispose() {
            this.mRouter.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.media.MediaRouter2ServiceImpl.this.routerDied(this);
        }

        public void updateScanningState(int i) {
            if (this.mScanningState == i) {
                return;
            }
            this.mScanningState = i;
            this.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$RouterRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).updateDiscoveryPreferenceOnHandler();
                }
            }, this.mUserRecord.mHandler));
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            printWriter.println(str + "RouterRecord");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mPackageName=" + this.mPackageName);
            printWriter.println(str2 + "mSelectRouteSequenceNumbers=" + this.mSelectRouteSequenceNumbers);
            printWriter.println(str2 + "mUid=" + this.mUid);
            printWriter.println(str2 + "mPid=" + this.mPid);
            printWriter.println(str2 + "mHasConfigureWifiDisplayPermission=" + this.mHasConfigureWifiDisplayPermission);
            printWriter.println(str2 + "mHasModifyAudioRoutingPermission=" + this.mHasModifyAudioRoutingPermission);
            printWriter.println(str2 + "mHasBluetoothRoutingPermission=" + this.mHasBluetoothRoutingPermission.get());
            printWriter.println(str2 + "hasSystemRoutingPermission=" + hasSystemRoutingPermission());
            printWriter.println(str2 + "mRouterId=" + this.mRouterId);
            this.mDiscoveryPreference.dump(printWriter, str2);
        }

        public void notifyRegistered(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) {
            try {
                this.mRouter.notifyRouterRegistered(getVisibleRoutes(list), routingSessionInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify router registered. Router probably died.", e);
            }
        }

        public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
            try {
                this.mRouter.notifyRoutesUpdated(getVisibleRoutes(list));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify routes updated. Router probably died.", e);
            }
        }

        public void notifySessionCreated(int i, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            try {
                this.mRouter.notifySessionCreated(i, maybeClearTransferInitiatorIdentity(routingSessionInfo));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify router of the session creation. Router probably died.", e);
            }
        }

        public void notifySessionInfoChanged(android.media.RoutingSessionInfo routingSessionInfo) {
            try {
                this.mRouter.notifySessionInfoChanged(maybeClearTransferInitiatorIdentity(routingSessionInfo));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify session info changed. Router probably died.", e);
            }
        }

        private android.media.RoutingSessionInfo maybeClearTransferInitiatorIdentity(@android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            android.os.UserHandle transferInitiatorUserHandle = routingSessionInfo.getTransferInitiatorUserHandle();
            java.lang.String transferInitiatorPackageName = routingSessionInfo.getTransferInitiatorPackageName();
            if (!java.util.Objects.equals(android.os.UserHandle.of(this.mUserRecord.mUserId), transferInitiatorUserHandle) || !java.util.Objects.equals(this.mPackageName, transferInitiatorPackageName)) {
                return new android.media.RoutingSessionInfo.Builder(routingSessionInfo).setTransferInitiator(null, null).build();
            }
            return routingSessionInfo;
        }

        private java.util.List<android.media.MediaRoute2Info> getVisibleRoutes(@android.annotation.NonNull java.util.List<android.media.MediaRoute2Info> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.media.MediaRoute2Info mediaRoute2Info : list) {
                if (mediaRoute2Info.isVisibleTo(this.mPackageName)) {
                    arrayList.add(mediaRoute2Info);
                }
            }
            return arrayList;
        }
    }

    final class ManagerRecord implements android.os.IBinder.DeathRecipient {
        public final boolean mHasMediaRoutingControl;

        @android.annotation.Nullable
        public com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest mLastSessionCreationRequest;

        @android.annotation.NonNull
        public final android.media.IMediaRouter2Manager mManager;
        public final int mManagerId;

        @android.annotation.NonNull
        public final java.lang.String mOwnerPackageName;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public int mScanningState = 0;

        @android.annotation.Nullable
        public final java.lang.String mTargetPackageName;

        @android.annotation.NonNull
        public final com.android.server.media.MediaRouter2ServiceImpl.UserRecord mUserRecord;

        ManagerRecord(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z) {
            this.mUserRecord = userRecord;
            this.mManager = iMediaRouter2Manager;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
            this.mOwnerPackageName = str;
            this.mTargetPackageName = str2;
            this.mManagerId = com.android.server.media.MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
            this.mHasMediaRoutingControl = z;
        }

        public void dispose() {
            this.mManager.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.media.MediaRouter2ServiceImpl.this.managerDied(this);
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            printWriter.println(str + "ManagerRecord");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mOwnerPackageName=" + this.mOwnerPackageName);
            printWriter.println(str2 + "mTargetPackageName=" + this.mTargetPackageName);
            printWriter.println(str2 + "mManagerId=" + this.mManagerId);
            printWriter.println(str2 + "mOwnerUid=" + this.mOwnerUid);
            printWriter.println(str2 + "mOwnerPid=" + this.mOwnerPid);
            printWriter.println(str2 + "mScanningState=" + com.android.server.media.MediaRouter2ServiceImpl.getScanningStateString(this.mScanningState));
            if (this.mLastSessionCreationRequest != null) {
                this.mLastSessionCreationRequest.dump(printWriter, str2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateScanningState(int i) {
            if (this.mScanningState == i) {
                return;
            }
            this.mScanningState = i;
            this.mUserRecord.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$ManagerRecord$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).updateDiscoveryPreferenceOnHandler();
                }
            }, this.mUserRecord.mHandler));
        }

        public java.lang.String toString() {
            return "Manager " + this.mOwnerPackageName + " (pid " + this.mOwnerPid + ")";
        }
    }

    static final class UserHandler extends android.os.Handler implements com.android.server.media.MediaRoute2ProviderWatcher.Callback, com.android.server.media.MediaRoute2Provider.Callback {
        private final java.util.Map<java.lang.String, android.media.MediaRoute2Info> mLastNotifiedRoutesToNonPrivilegedRouters;
        private final java.util.Map<java.lang.String, android.media.MediaRoute2Info> mLastNotifiedRoutesToPrivilegedRouters;
        private final java.util.List<android.media.MediaRoute2ProviderInfo> mLastProviderInfos;
        private final java.util.ArrayList<com.android.server.media.MediaRoute2Provider> mRouteProviders;
        private boolean mRunning;
        private final java.lang.ref.WeakReference<com.android.server.media.MediaRouter2ServiceImpl> mServiceRef;
        private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest> mSessionCreationRequests;
        private final java.util.Map<java.lang.String, com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> mSessionToRouterMap;
        private final com.android.server.media.SystemMediaRoute2Provider mSystemProvider;
        private final com.android.server.media.MediaRouter2ServiceImpl.UserRecord mUserRecord;
        private final com.android.server.media.MediaRoute2ProviderWatcher mWatcher;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: -$$Nest$mstart, reason: not valid java name */
        public static /* bridge */ /* synthetic */ void m5122$$Nest$mstart(com.android.server.media.MediaRouter2ServiceImpl.UserHandler userHandler) {
            userHandler.start();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: -$$Nest$mstop, reason: not valid java name */
        public static /* bridge */ /* synthetic */ void m5123$$Nest$mstop(com.android.server.media.MediaRouter2ServiceImpl.UserHandler userHandler) {
            userHandler.stop();
        }

        UserHandler(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, @android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.UserRecord userRecord) {
            super(android.os.Looper.getMainLooper(), null, true);
            this.mRouteProviders = new java.util.ArrayList<>();
            this.mLastProviderInfos = new java.util.ArrayList();
            this.mSessionCreationRequests = new java.util.concurrent.CopyOnWriteArrayList<>();
            this.mSessionToRouterMap = new android.util.ArrayMap();
            this.mLastNotifiedRoutesToPrivilegedRouters = new android.util.ArrayMap();
            this.mLastNotifiedRoutesToNonPrivilegedRouters = new android.util.ArrayMap();
            this.mServiceRef = new java.lang.ref.WeakReference<>(mediaRouter2ServiceImpl);
            this.mUserRecord = userRecord;
            this.mSystemProvider = new com.android.server.media.SystemMediaRoute2Provider(mediaRouter2ServiceImpl.mContext, android.os.UserHandle.of(userRecord.mUserId));
            this.mRouteProviders.add(this.mSystemProvider);
            this.mWatcher = new com.android.server.media.MediaRoute2ProviderWatcher(mediaRouter2ServiceImpl.mContext, this, this, this.mUserRecord.mUserId);
        }

        void init() {
            this.mSystemProvider.setCallback(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void start() {
            if (!this.mRunning) {
                this.mRunning = true;
                this.mSystemProvider.start();
                this.mWatcher.start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                this.mWatcher.stop();
                this.mSystemProvider.stop();
            }
        }

        @Override // com.android.server.media.MediaRoute2ProviderWatcher.Callback
        public void onAddProviderService(@android.annotation.NonNull com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy) {
            mediaRoute2ProviderServiceProxy.setCallback(this);
            this.mRouteProviders.add(mediaRoute2ProviderServiceProxy);
            mediaRoute2ProviderServiceProxy.updateDiscoveryPreference(this.mUserRecord.mActivelyScanningPackages, this.mUserRecord.mCompositeDiscoveryPreference);
        }

        @Override // com.android.server.media.MediaRoute2ProviderWatcher.Callback
        public void onRemoveProviderService(@android.annotation.NonNull com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy) {
            this.mRouteProviders.remove(mediaRoute2ProviderServiceProxy);
        }

        @Override // com.android.server.media.MediaRoute2Provider.Callback
        public void onProviderStateChanged(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).onProviderStateChangedOnHandler((com.android.server.media.MediaRoute2Provider) obj2);
                }
            }, this, mediaRoute2Provider));
        }

        @Override // com.android.server.media.MediaRoute2Provider.Callback
        public void onSessionCreated(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).onSessionCreatedOnHandler((com.android.server.media.MediaRoute2Provider) obj2, ((java.lang.Long) obj3).longValue(), (android.media.RoutingSessionInfo) obj4);
                }
            }, this, mediaRoute2Provider, java.lang.Long.valueOf(j), routingSessionInfo));
        }

        @Override // com.android.server.media.MediaRoute2Provider.Callback
        public void onSessionUpdated(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda3
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).onSessionInfoChangedOnHandler((com.android.server.media.MediaRoute2Provider) obj2, (android.media.RoutingSessionInfo) obj3);
                }
            }, this, mediaRoute2Provider, routingSessionInfo));
        }

        @Override // com.android.server.media.MediaRoute2Provider.Callback
        public void onSessionReleased(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda5
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).onSessionReleasedOnHandler((com.android.server.media.MediaRoute2Provider) obj2, (android.media.RoutingSessionInfo) obj3);
                }
            }, this, mediaRoute2Provider, routingSessionInfo));
        }

        @Override // com.android.server.media.MediaRoute2Provider.Callback
        public void onRequestFailed(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, int i) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda10
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).onRequestFailedOnHandler((com.android.server.media.MediaRoute2Provider) obj2, ((java.lang.Long) obj3).longValue(), ((java.lang.Integer) obj4).intValue());
                }
            }, this, mediaRoute2Provider, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i)));
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        public com.android.server.media.MediaRouter2ServiceImpl.RouterRecord findRouterWithSessionLocked(@android.annotation.NonNull java.lang.String str) {
            return this.mSessionToRouterMap.get(str);
        }

        @android.annotation.Nullable
        public com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord findManagerWithId(int i) {
            for (com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord : getManagerRecords()) {
                if (managerRecord.mManagerId == i) {
                    return managerRecord;
                }
            }
            return null;
        }

        public void maybeUpdateDiscoveryPreferenceForUid(final int i) {
            boolean anyMatch;
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                anyMatch = this.mUserRecord.mManagerRecords.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$maybeUpdateDiscoveryPreferenceForUid$1;
                        lambda$maybeUpdateDiscoveryPreferenceForUid$1 = com.android.server.media.MediaRouter2ServiceImpl.UserHandler.lambda$maybeUpdateDiscoveryPreferenceForUid$1(i, (com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord) obj);
                        return lambda$maybeUpdateDiscoveryPreferenceForUid$1;
                    }
                }) | this.mUserRecord.mRouterRecords.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$maybeUpdateDiscoveryPreferenceForUid$0;
                        lambda$maybeUpdateDiscoveryPreferenceForUid$0 = com.android.server.media.MediaRouter2ServiceImpl.UserHandler.lambda$maybeUpdateDiscoveryPreferenceForUid$0(i, (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj);
                        return lambda$maybeUpdateDiscoveryPreferenceForUid$0;
                    }
                });
            }
            if (anyMatch) {
                sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.media.MediaRouter2ServiceImpl.UserHandler) obj).updateDiscoveryPreferenceOnHandler();
                    }
                }, this));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$maybeUpdateDiscoveryPreferenceForUid$0(int i, com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
            return routerRecord.mUid == i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$maybeUpdateDiscoveryPreferenceForUid$1(int i, com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord) {
            return managerRecord.mOwnerUid == i;
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            printWriter.println(str + "UserHandler");
            printWriter.println((str + "  ") + "mRunning=" + this.mRunning);
            this.mSystemProvider.dump(printWriter, str);
            this.mWatcher.dump(printWriter, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onProviderStateChangedOnHandler(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider) {
            java.util.Set emptySet;
            java.util.Collection<android.media.MediaRoute2Info> emptySet2;
            android.media.MediaRoute2ProviderInfo providerInfo = mediaRoute2Provider.getProviderInfo();
            int indexOfRouteProviderInfoByUniqueId = indexOfRouteProviderInfoByUniqueId(mediaRoute2Provider.getUniqueId(), this.mLastProviderInfos);
            android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo = indexOfRouteProviderInfoByUniqueId == -1 ? null : this.mLastProviderInfos.get(indexOfRouteProviderInfoByUniqueId);
            if (mediaRoute2ProviderInfo == providerInfo) {
                return;
            }
            if (providerInfo != null) {
                emptySet2 = providerInfo.getRoutes();
                emptySet = (java.util.Set) emptySet2.stream().map(new java.util.function.Function() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return ((android.media.MediaRoute2Info) obj).getId();
                    }
                }).collect(java.util.stream.Collectors.toSet());
                if (indexOfRouteProviderInfoByUniqueId >= 0) {
                    this.mLastProviderInfos.set(indexOfRouteProviderInfoByUniqueId, providerInfo);
                } else {
                    this.mLastProviderInfos.add(providerInfo);
                }
            } else {
                this.mLastProviderInfos.remove(mediaRoute2ProviderInfo);
                emptySet = java.util.Collections.emptySet();
                emptySet2 = java.util.Collections.emptySet();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            boolean z = false;
            boolean z2 = false;
            for (android.media.MediaRoute2Info mediaRoute2Info : emptySet2) {
                if (mediaRoute2Info.isValid()) {
                    if (!mediaRoute2Provider.mIsSystemRouteProvider) {
                        this.mLastNotifiedRoutesToNonPrivilegedRouters.put(mediaRoute2Info.getId(), mediaRoute2Info);
                    }
                    android.media.MediaRoute2Info put = this.mLastNotifiedRoutesToPrivilegedRouters.put(mediaRoute2Info.getId(), mediaRoute2Info);
                    z2 |= true ^ mediaRoute2Info.equals(put);
                    if (put == null) {
                        arrayList.add(mediaRoute2Info);
                    }
                } else {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "onProviderStateChangedOnHandler: Ignoring invalid route : " + mediaRoute2Info);
                }
            }
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            for (android.media.MediaRoute2Info mediaRoute2Info2 : mediaRoute2ProviderInfo == null ? java.util.Collections.emptyList() : mediaRoute2ProviderInfo.getRoutes()) {
                java.lang.String id = mediaRoute2Info2.getId();
                if (!emptySet.contains(id)) {
                    this.mLastNotifiedRoutesToPrivilegedRouters.remove(id);
                    this.mLastNotifiedRoutesToNonPrivilegedRouters.remove(id);
                    arrayList2.add(mediaRoute2Info2);
                    z = true;
                }
            }
            if (!arrayList.isEmpty()) {
                android.util.Slog.i(com.android.server.media.MediaRouter2ServiceImpl.TAG, toLoggingMessage("addProviderRoutes", providerInfo.getUniqueId(), arrayList));
            }
            if (!arrayList2.isEmpty()) {
                android.util.Slog.i(com.android.server.media.MediaRouter2ServiceImpl.TAG, toLoggingMessage("removeProviderRoutes", mediaRoute2ProviderInfo.getUniqueId(), arrayList2));
            }
            dispatchUpdates(z2, z, mediaRoute2Provider.mIsSystemRouteProvider, this.mSystemProvider.getDefaultRoute());
        }

        private static java.lang.String getPackageNameFromNullableRecord(@android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
            return routerRecord != null ? routerRecord.mPackageName : "<null router record>";
        }

        private static java.lang.String toLoggingMessage(java.lang.String str, java.lang.String str2, java.util.ArrayList<android.media.MediaRoute2Info> arrayList) {
            return android.text.TextUtils.formatSimple("%s | provider: %s, routes: [%s]", new java.lang.Object[]{str, str2, (java.lang.String) arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$toLoggingMessage$2;
                    lambda$toLoggingMessage$2 = com.android.server.media.MediaRouter2ServiceImpl.UserHandler.lambda$toLoggingMessage$2((android.media.MediaRoute2Info) obj);
                    return lambda$toLoggingMessage$2;
                }
            }).collect(java.util.stream.Collectors.joining(", "))});
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.String lambda$toLoggingMessage$2(android.media.MediaRoute2Info mediaRoute2Info) {
            return java.lang.String.format("%s | %s", mediaRoute2Info.getOriginalId(), mediaRoute2Info.getName());
        }

        private void dispatchUpdates(boolean z, boolean z2, boolean z3, android.media.MediaRoute2Info mediaRoute2Info) {
            if (!z && !z2) {
                return;
            }
            java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> routerRecords = getRouterRecords(true);
            java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> routerRecords2 = getRouterRecords(false);
            notifyRoutesUpdatedToManagers(getManagers(), new java.util.ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
            notifyRoutesUpdatedToRouterRecords(routerRecords, new java.util.ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
            if (!z3) {
                notifyRoutesUpdatedToRouterRecords(routerRecords2, new java.util.ArrayList(this.mLastNotifiedRoutesToNonPrivilegedRouters.values()));
            } else if (z) {
                this.mLastNotifiedRoutesToNonPrivilegedRouters.put(mediaRoute2Info.getId(), mediaRoute2Info);
                notifyRoutesUpdatedToRouterRecords(routerRecords2, new java.util.ArrayList(this.mLastNotifiedRoutesToNonPrivilegedRouters.values()));
            }
        }

        private static int indexOfRouteProviderInfoByUniqueId(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.media.MediaRoute2ProviderInfo> list) {
            for (int i = 0; i < list.size(); i++) {
                if (android.text.TextUtils.equals(list.get(i).getUniqueId(), str)) {
                    return i;
                }
            }
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestRouterCreateSessionOnHandler(long j, @android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str) {
            android.media.MediaRoute2Info mediaRoute2Info2;
            try {
                if (mediaRoute2Info.isSystemRoute() && !routerRecord.hasSystemRoutingPermission()) {
                    mediaRoute2Info2 = this.mSystemProvider.getDefaultRoute();
                } else {
                    mediaRoute2Info2 = mediaRoute2Info;
                }
                routerRecord.mRouter.requestCreateSessionByManager(j, routingSessionInfo, mediaRoute2Info2, userHandle, str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "getSessionHintsForCreatingSessionOnHandler: Failed to request. Router probably died.", e);
                notifyRequestFailedToManager(managerRecord.mManager, com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j), 0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestCreateSessionWithRouter2OnHandler(long j, long j2, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.Nullable android.os.Bundle bundle) {
            int i;
            com.android.server.media.MediaRoute2Provider findProvider = findProvider(mediaRoute2Info.getProviderId());
            if (findProvider == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "requestCreateSessionWithRouter2OnHandler: Ignoring session creation request since no provider found for given route=" + mediaRoute2Info);
                notifySessionCreationFailedToRouter(routerRecord, com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j));
                return;
            }
            this.mSessionCreationRequests.add(new com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest(routerRecord, j, j2, routingSessionInfo, mediaRoute2Info));
            if (j2 == 0) {
                i = 2;
            } else {
                i = 1;
            }
            findProvider.requestCreateSession(j, routerRecord.mPackageName, mediaRoute2Info.getOriginalId(), bundle, i, userHandle, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void selectRouteOnHandler(long j, @android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
            com.android.server.media.MediaRoute2Provider findProvider;
            if (!checkArgumentsForSessionControl(routerRecord, str, mediaRoute2Info, "selecting") || (findProvider = findProvider(mediaRoute2Info.getProviderId())) == null) {
                return;
            }
            findProvider.selectRoute(j, android.media.MediaRouter2Utils.getOriginalId(str), mediaRoute2Info.getOriginalId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void deselectRouteOnHandler(long j, @android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
            com.android.server.media.MediaRoute2Provider findProvider;
            if (!checkArgumentsForSessionControl(routerRecord, str, mediaRoute2Info, "deselecting") || (findProvider = findProvider(mediaRoute2Info.getProviderId())) == null) {
                return;
            }
            findProvider.deselectRoute(j, android.media.MediaRouter2Utils.getOriginalId(str), mediaRoute2Info.getOriginalId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void transferToRouteOnHandler(long j, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i) {
            com.android.server.media.MediaRoute2Provider findProvider;
            if (!checkArgumentsForSessionControl(routerRecord, str2, mediaRoute2Info, "transferring to") || (findProvider = findProvider(mediaRoute2Info.getProviderId())) == null) {
                return;
            }
            findProvider.transferToRoute(j, userHandle, str, android.media.MediaRouter2Utils.getOriginalId(str2), mediaRoute2Info.getOriginalId(), i);
        }

        private boolean checkArgumentsForSessionControl(@android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.NonNull java.lang.String str2) {
            if (findProvider(mediaRoute2Info.getProviderId()) == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring " + str2 + " route since no provider found for given route=" + mediaRoute2Info);
                return false;
            }
            if (android.text.TextUtils.equals(android.media.MediaRouter2Utils.getProviderId(str), this.mSystemProvider.getUniqueId())) {
                return true;
            }
            com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord2 = this.mSessionToRouterMap.get(str);
            if (routerRecord2 != routerRecord) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring " + str2 + " route from non-matching router. routerRecordPackageName=" + getPackageNameFromNullableRecord(routerRecord) + " matchingRecordPackageName=" + getPackageNameFromNullableRecord(routerRecord2) + " route=" + mediaRoute2Info);
                return false;
            }
            if (android.media.MediaRouter2Utils.getOriginalId(str) != null) {
                return true;
            }
            android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to get original session id from unique session id. uniqueSessionId=" + str);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRouteVolumeOnHandler(long j, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, int i) {
            com.android.server.media.MediaRoute2Provider findProvider = findProvider(mediaRoute2Info.getProviderId());
            if (findProvider == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "setRouteVolumeOnHandler: Couldn't find provider for route=" + mediaRoute2Info);
                return;
            }
            findProvider.setRouteVolume(j, mediaRoute2Info.getOriginalId(), i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSessionVolumeOnHandler(long j, @android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.media.MediaRoute2Provider findProvider = findProvider(android.media.MediaRouter2Utils.getProviderId(str));
            if (findProvider == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "setSessionVolumeOnHandler: Couldn't find provider for session id=" + str);
                return;
            }
            findProvider.setSessionVolume(j, android.media.MediaRouter2Utils.getOriginalId(str), i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseSessionOnHandler(long j, @android.annotation.Nullable com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull java.lang.String str) {
            com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord2 = this.mSessionToRouterMap.get(str);
            if (routerRecord2 != routerRecord) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring releasing session from non-matching router. routerRecordPackageName=" + getPackageNameFromNullableRecord(routerRecord) + " matchingRecordPackageName=" + getPackageNameFromNullableRecord(routerRecord2) + " uniqueSessionId=" + str);
                return;
            }
            java.lang.String providerId = android.media.MediaRouter2Utils.getProviderId(str);
            if (providerId == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring releasing session with invalid unique session ID. uniqueSessionId=" + str);
                return;
            }
            java.lang.String originalId = android.media.MediaRouter2Utils.getOriginalId(str);
            if (originalId == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring releasing session with invalid unique session ID. uniqueSessionId=" + str + " providerId=" + providerId);
                return;
            }
            com.android.server.media.MediaRoute2Provider findProvider = findProvider(providerId);
            if (findProvider == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring releasing session since no provider found for given providerId=" + providerId);
                return;
            }
            findProvider.releaseSession(j, originalId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionCreatedOnHandler(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest;
            long j2;
            java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest> it = this.mSessionCreationRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    sessionCreationRequest = null;
                    break;
                }
                sessionCreationRequest = it.next();
                if (sessionCreationRequest.mUniqueRequestId == j && android.text.TextUtils.equals(sessionCreationRequest.mRoute.getProviderId(), mediaRoute2Provider.getUniqueId())) {
                    break;
                }
            }
            if (sessionCreationRequest == null) {
                j2 = 0;
            } else {
                j2 = sessionCreationRequest.mManagerRequestId;
            }
            notifySessionCreatedToManagers(j2, routingSessionInfo);
            if (sessionCreationRequest == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Ignoring session creation result for unknown request. uniqueRequestId=" + j + ", sessionInfo=" + routingSessionInfo);
                return;
            }
            this.mSessionCreationRequests.remove(sessionCreationRequest);
            com.android.server.media.MediaRoute2Provider findProvider = findProvider(sessionCreationRequest.mOldSession.getProviderId());
            if (findProvider != null) {
                findProvider.prepareReleaseSession(sessionCreationRequest.mOldSession.getId());
            } else {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "onSessionCreatedOnHandler: Can't find provider for an old session. session=" + sessionCreationRequest.mOldSession);
            }
            this.mSessionToRouterMap.put(routingSessionInfo.getId(), sessionCreationRequest.mRouterRecord);
            if (routingSessionInfo.isSystemSession() && !sessionCreationRequest.mRouterRecord.hasSystemRoutingPermission()) {
                routingSessionInfo = this.mSystemProvider.getDefaultSessionInfo();
            }
            sessionCreationRequest.mRouterRecord.notifySessionCreated(com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j), routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionInfoChangedOnHandler(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            notifySessionUpdatedToManagers(getManagers(), routingSessionInfo);
            if (mediaRoute2Provider == this.mSystemProvider) {
                if (this.mServiceRef.get() == null) {
                    return;
                }
                notifySessionInfoChangedToRouters(getRouterRecords(true), routingSessionInfo);
                notifySessionInfoChangedToRouters(getRouterRecords(false), this.mSystemProvider.getDefaultSessionInfo());
                return;
            }
            com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mSessionToRouterMap.get(routingSessionInfo.getId());
            if (routerRecord == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "onSessionInfoChangedOnHandler: No matching router found for session=" + routingSessionInfo);
                return;
            }
            notifySessionInfoChangedToRouters(java.util.Arrays.asList(routerRecord), routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionReleasedOnHandler(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            notifySessionReleasedToManagers(getManagers(), routingSessionInfo);
            com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord = this.mSessionToRouterMap.get(routingSessionInfo.getId());
            if (routerRecord == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "onSessionReleasedOnHandler: No matching router found for session=" + routingSessionInfo);
                return;
            }
            notifySessionReleasedToRouter(routerRecord, routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestFailedOnHandler(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, int i) {
            if (handleSessionCreationRequestFailed(mediaRoute2Provider, j, i)) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, android.text.TextUtils.formatSimple("onRequestFailedOnHandler | Finished handling session creation request failed for provider: %s, uniqueRequestId: %d, reason: %d", new java.lang.Object[]{mediaRoute2Provider.getUniqueId(), java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i)}));
                return;
            }
            com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord findManagerWithId = findManagerWithId(com.android.server.media.MediaRouter2ServiceImpl.toRequesterId(j));
            if (findManagerWithId != null) {
                notifyRequestFailedToManager(findManagerWithId.mManager, com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j), i);
            }
        }

        private boolean handleSessionCreationRequestFailed(@android.annotation.NonNull com.android.server.media.MediaRoute2Provider mediaRoute2Provider, long j, int i) {
            com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest;
            java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.SessionCreationRequest> it = this.mSessionCreationRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    sessionCreationRequest = null;
                    break;
                }
                sessionCreationRequest = it.next();
                if (sessionCreationRequest.mUniqueRequestId == j && android.text.TextUtils.equals(sessionCreationRequest.mRoute.getProviderId(), mediaRoute2Provider.getUniqueId())) {
                    break;
                }
            }
            if (sessionCreationRequest == null) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, android.text.TextUtils.formatSimple("handleSessionCreationRequestFailed | No matching request found for provider: %s, uniqueRequestId: %d, reason: %d", new java.lang.Object[]{mediaRoute2Provider.getUniqueId(), java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i)}));
                return false;
            }
            this.mSessionCreationRequests.remove(sessionCreationRequest);
            if (sessionCreationRequest.mManagerRequestId == 0) {
                notifySessionCreationFailedToRouter(sessionCreationRequest.mRouterRecord, com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j));
                return true;
            }
            com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord findManagerWithId = findManagerWithId(com.android.server.media.MediaRouter2ServiceImpl.toRequesterId(sessionCreationRequest.mManagerRequestId));
            if (findManagerWithId != null) {
                notifyRequestFailedToManager(findManagerWithId.mManager, com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(sessionCreationRequest.mManagerRequestId), i);
                return true;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySessionCreationFailedToRouter(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, int i) {
            try {
                routerRecord.mRouter.notifySessionCreated(i, (android.media.RoutingSessionInfo) null);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify router of the session creation failure. Router probably died.", e);
            }
        }

        private void notifySessionReleasedToRouter(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            try {
                routerRecord.mRouter.notifySessionReleased(routingSessionInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify router of the session release. Router probably died.", e);
            }
        }

        private java.util.List<android.media.IMediaRouter2Manager> getManagers() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return arrayList;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> it = this.mUserRecord.mManagerRecords.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().mManager);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        private java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> getRouterRecords() {
            java.util.ArrayList arrayList;
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return java.util.Collections.emptyList();
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                arrayList = new java.util.ArrayList(this.mUserRecord.mRouterRecords);
            }
            return arrayList;
        }

        private java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> getRouterRecords(boolean z) {
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (mediaRouter2ServiceImpl == null) {
                return arrayList;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = this.mUserRecord.mRouterRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.media.MediaRouter2ServiceImpl.RouterRecord next = it.next();
                        if (z == next.hasSystemRoutingPermission()) {
                            arrayList.add(next);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        private java.util.List<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> getManagerRecords() {
            java.util.ArrayList arrayList;
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return java.util.Collections.emptyList();
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                arrayList = new java.util.ArrayList(this.mUserRecord.mManagerRecords);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyRouterRegistered(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
            android.media.RoutingSessionInfo defaultSessionInfo;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo = null;
            for (android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo2 : this.mLastProviderInfos) {
                if (android.text.TextUtils.equals(mediaRoute2ProviderInfo2.getUniqueId(), this.mSystemProvider.getUniqueId())) {
                    mediaRoute2ProviderInfo = mediaRoute2ProviderInfo2;
                } else {
                    arrayList.addAll(mediaRoute2ProviderInfo2.getRoutes());
                }
            }
            if (routerRecord.hasSystemRoutingPermission()) {
                if (mediaRoute2ProviderInfo != null) {
                    arrayList.addAll(mediaRoute2ProviderInfo.getRoutes());
                } else {
                    android.util.Slog.wtf(com.android.server.media.MediaRouter2ServiceImpl.TAG, "System route provider not found.");
                }
                defaultSessionInfo = this.mSystemProvider.getSessionInfos().get(0);
            } else {
                arrayList.add(this.mSystemProvider.getDefaultRoute());
                defaultSessionInfo = this.mSystemProvider.getDefaultSessionInfo();
            }
            if (!arrayList.isEmpty()) {
                routerRecord.notifyRegistered(arrayList, defaultSessionInfo);
            }
        }

        private static void notifyRoutesUpdatedToRouterRecords(@android.annotation.NonNull java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> list, @android.annotation.NonNull java.util.List<android.media.MediaRoute2Info> list2) {
            java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = list.iterator();
            while (it.hasNext()) {
                it.next().notifyRoutesUpdated(list2);
            }
        }

        private void notifySessionInfoChangedToRouters(@android.annotation.NonNull java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> list, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> it = list.iterator();
            while (it.hasNext()) {
                it.next().notifySessionInfoChanged(routingSessionInfo);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyInitialRoutesToManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager) {
            if (this.mLastNotifiedRoutesToPrivilegedRouters.isEmpty()) {
                return;
            }
            try {
                iMediaRouter2Manager.notifyRoutesUpdated(new java.util.ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify all routes. Manager probably died.", e);
            }
        }

        private void notifyRoutesUpdatedToManagers(@android.annotation.NonNull java.util.List<android.media.IMediaRouter2Manager> list, @android.annotation.NonNull java.util.List<android.media.MediaRoute2Info> list2) {
            java.util.Iterator<android.media.IMediaRouter2Manager> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().notifyRoutesUpdated(list2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify routes changed. Manager probably died.", e);
                }
            }
        }

        private void notifySessionCreatedToManagers(long j, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            int requesterId = com.android.server.media.MediaRouter2ServiceImpl.toRequesterId(j);
            int originalRequestId = com.android.server.media.MediaRouter2ServiceImpl.toOriginalRequestId(j);
            for (com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord : getManagerRecords()) {
                try {
                    managerRecord.mManager.notifySessionCreated(managerRecord.mManagerId == requesterId ? originalRequestId : 0, routingSessionInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "notifySessionCreatedToManagers: Failed to notify. Manager probably died.", e);
                }
            }
        }

        private void notifySessionUpdatedToManagers(@android.annotation.NonNull java.util.List<android.media.IMediaRouter2Manager> list, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Iterator<android.media.IMediaRouter2Manager> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().notifySessionUpdated(routingSessionInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "notifySessionUpdatedToManagers: Failed to notify. Manager probably died.", e);
                }
            }
        }

        private void notifySessionReleasedToManagers(@android.annotation.NonNull java.util.List<android.media.IMediaRouter2Manager> list, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Iterator<android.media.IMediaRouter2Manager> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().notifySessionReleased(routingSessionInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "notifySessionReleasedToManagers: Failed to notify. Manager probably died.", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDiscoveryPreferenceChangedToManager(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, @android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager) {
            try {
                iMediaRouter2Manager.notifyDiscoveryPreferenceChanged(routerRecord.mPackageName, routerRecord.mDiscoveryPreference);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify preferred features changed. Manager probably died.", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDiscoveryPreferenceChangedToManagers(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> it = this.mUserRecord.mManagerRecords.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().mManager);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            java.util.Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                try {
                    ((android.media.IMediaRouter2Manager) it2.next()).notifyDiscoveryPreferenceChanged(str, routeDiscoveryPreference);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify preferred features changed. Manager probably died.", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyRouteListingPreferenceChangeToManagers(java.lang.String str, @android.annotation.Nullable android.media.RouteListingPreference routeListingPreference) {
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    java.util.Iterator<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> it = this.mUserRecord.mManagerRecords.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().mManager);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            java.util.Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                try {
                    ((android.media.IMediaRouter2Manager) it2.next()).notifyRouteListingPreferenceChange(str, routeListingPreference);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify preferred features changed. Manager probably died.", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyRequestFailedToManager(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, int i2) {
            try {
                iMediaRouter2Manager.notifyRequestFailed(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.media.MediaRouter2ServiceImpl.TAG, "Failed to notify manager of the request failure. Manager probably died.", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateDiscoveryPreferenceOnHandler() {
            com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> routerRecords = getRouterRecords();
            boolean areManagersScanning = areManagersScanning(mediaRouter2ServiceImpl, getManagerRecords());
            if (!areManagersScanning) {
                routerRecords = getIndividuallyActiveRouters(mediaRouter2ServiceImpl, routerRecords);
            }
            updateManagerScanningForProviders(areManagersScanning);
            java.util.HashSet hashSet = new java.util.HashSet();
            android.media.RouteDiscoveryPreference buildCompositeDiscoveryPreference = buildCompositeDiscoveryPreference(routerRecords, areManagersScanning, hashSet);
            android.util.Slog.i(com.android.server.media.MediaRouter2ServiceImpl.TAG, android.text.TextUtils.formatSimple("Updating composite discovery preference | preference: %s, active routers: %s", new java.lang.Object[]{buildCompositeDiscoveryPreference, hashSet}));
            if (updateScanningOnUserRecord(mediaRouter2ServiceImpl, hashSet, buildCompositeDiscoveryPreference)) {
                updateDiscoveryPreferenceForProviders(hashSet);
            }
        }

        private void updateDiscoveryPreferenceForProviders(java.util.Set<java.lang.String> set) {
            java.util.Iterator<com.android.server.media.MediaRoute2Provider> it = this.mRouteProviders.iterator();
            while (it.hasNext()) {
                it.next().updateDiscoveryPreference(set, this.mUserRecord.mCompositeDiscoveryPreference);
            }
        }

        private boolean updateScanningOnUserRecord(com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, java.util.Set<java.lang.String> set, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    if (routeDiscoveryPreference.equals(this.mUserRecord.mCompositeDiscoveryPreference) && set.equals(this.mUserRecord.mActivelyScanningPackages)) {
                        return false;
                    }
                    this.mUserRecord.mCompositeDiscoveryPreference = routeDiscoveryPreference;
                    this.mUserRecord.mActivelyScanningPackages = set;
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.NonNull
        private static android.media.RouteDiscoveryPreference buildCompositeDiscoveryPreference(java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> list, boolean z, java.util.Set<java.lang.String> set) {
            java.util.HashSet hashSet = new java.util.HashSet();
            boolean z2 = false;
            for (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord : list) {
                hashSet.addAll(routerRecord.mDiscoveryPreference.getPreferredFeatures());
                if (routerRecord.isActivelyScanning()) {
                    set.add(routerRecord.mPackageName);
                    z2 = true;
                }
            }
            return new android.media.RouteDiscoveryPreference.Builder(java.util.List.copyOf(hashSet), z2 || z).build();
        }

        private void updateManagerScanningForProviders(boolean z) {
            java.util.Iterator<com.android.server.media.MediaRoute2Provider> it = this.mRouteProviders.iterator();
            while (it.hasNext()) {
                com.android.server.media.MediaRoute2Provider next = it.next();
                if (next instanceof com.android.server.media.MediaRoute2ProviderServiceProxy) {
                    ((com.android.server.media.MediaRoute2ProviderServiceProxy) next).setManagerScanning(z);
                }
            }
        }

        @android.annotation.NonNull
        private static java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> getIndividuallyActiveRouters(final com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, java.util.List<com.android.server.media.MediaRouter2ServiceImpl.RouterRecord> list) {
            if (!com.android.media.flags.Flags.disableScreenOffBroadcastReceiver() && !mediaRouter2ServiceImpl.mPowerManager.isInteractive() && !com.android.media.flags.Flags.enableScreenOffScanning()) {
                return java.util.Collections.emptyList();
            }
            return (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getIndividuallyActiveRouters$3;
                    lambda$getIndividuallyActiveRouters$3 = com.android.server.media.MediaRouter2ServiceImpl.UserHandler.lambda$getIndividuallyActiveRouters$3(com.android.server.media.MediaRouter2ServiceImpl.this, (com.android.server.media.MediaRouter2ServiceImpl.RouterRecord) obj);
                    return lambda$getIndividuallyActiveRouters$3;
                }
            }).collect(java.util.stream.Collectors.toList());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getIndividuallyActiveRouters$3(com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord) {
            return isPackageImportanceSufficientForScanning(mediaRouter2ServiceImpl, routerRecord.mPackageName) || routerRecord.mScanningState == 2;
        }

        private static boolean areManagersScanning(final com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, java.util.List<com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord> list) {
            if (!com.android.media.flags.Flags.disableScreenOffBroadcastReceiver() && !mediaRouter2ServiceImpl.mPowerManager.isInteractive() && !com.android.media.flags.Flags.enableScreenOffScanning()) {
                return false;
            }
            return list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$areManagersScanning$4;
                    lambda$areManagersScanning$4 = com.android.server.media.MediaRouter2ServiceImpl.UserHandler.lambda$areManagersScanning$4(com.android.server.media.MediaRouter2ServiceImpl.this, (com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord) obj);
                    return lambda$areManagersScanning$4;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$areManagersScanning$4(com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, com.android.server.media.MediaRouter2ServiceImpl.ManagerRecord managerRecord) {
            return (managerRecord.mScanningState == 1 && isPackageImportanceSufficientForScanning(mediaRouter2ServiceImpl, managerRecord.mOwnerPackageName)) || managerRecord.mScanningState == 2;
        }

        private static boolean isPackageImportanceSufficientForScanning(com.android.server.media.MediaRouter2ServiceImpl mediaRouter2ServiceImpl, java.lang.String str) {
            return mediaRouter2ServiceImpl.mActivityManager.getPackageImportance(str) <= 100;
        }

        private com.android.server.media.MediaRoute2Provider findProvider(@android.annotation.Nullable java.lang.String str) {
            java.util.Iterator<com.android.server.media.MediaRoute2Provider> it = this.mRouteProviders.iterator();
            while (it.hasNext()) {
                com.android.server.media.MediaRoute2Provider next = it.next();
                if (android.text.TextUtils.equals(next.getUniqueId(), str)) {
                    return next;
                }
            }
            return null;
        }
    }

    static final class SessionCreationRequest {
        public final long mManagerRequestId;
        public final android.media.RoutingSessionInfo mOldSession;
        public final android.media.MediaRoute2Info mRoute;
        public final com.android.server.media.MediaRouter2ServiceImpl.RouterRecord mRouterRecord;
        public final long mUniqueRequestId;

        SessionCreationRequest(@android.annotation.NonNull com.android.server.media.MediaRouter2ServiceImpl.RouterRecord routerRecord, long j, long j2, @android.annotation.NonNull android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info) {
            this.mRouterRecord = routerRecord;
            this.mUniqueRequestId = j;
            this.mManagerRequestId = j2;
            this.mOldSession = routingSessionInfo;
            this.mRoute = mediaRoute2Info;
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            printWriter.println(str + "SessionCreationRequest");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mUniqueRequestId=" + this.mUniqueRequestId);
            printWriter.println(str2 + "mManagerRequestId=" + this.mManagerRequestId);
            this.mOldSession.dump(printWriter, str2);
            this.mRoute.dump(printWriter, str);
        }
    }
}
