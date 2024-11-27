package com.android.server.media;

/* loaded from: classes2.dex */
public final class MediaRouterService extends android.media.IMediaRouterService.Stub implements com.android.server.Watchdog.Monitor {
    private static final long CONNECTED_TIMEOUT = 60000;
    private static final long CONNECTING_TIMEOUT = 5000;
    android.bluetooth.BluetoothDevice mActiveBluetoothDevice;
    private final com.android.server.media.AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    private final android.media.IAudioService mAudioService;
    private final java.lang.String mBluetoothA2dpRouteId;
    private final android.content.Context mContext;
    private final java.lang.String mDefaultAudioRouteId;
    private final com.android.server.media.MediaRouter2ServiceImpl mService2;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private static final java.lang.String TAG = "MediaRouterService";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.media.MediaRouterService.UserRecord> mUserRecords = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.media.MediaRouterService.ClientRecord> mAllClientRecords = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentActiveUserId = -1;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final android.util.IntArray mActivePlayerMinPriorityQueue = new android.util.IntArray();
    private final android.util.IntArray mActivePlayerUidMinPriorityQueue = new android.util.IntArray();
    private final android.content.BroadcastReceiver mReceiver = new com.android.server.media.MediaRouterService.MediaRouterServiceBroadcastReceiver();
    int mAudioRouteMainType = 0;
    boolean mGlobalBluetoothA2dpOn = false;

    @android.annotation.RequiresPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS")
    public MediaRouterService(android.content.Context context) {
        this.mService2 = new com.android.server.media.MediaRouter2ServiceImpl(context);
        this.mContext = context;
        com.android.server.Watchdog.getInstance().addMonitor(this);
        android.content.res.Resources resources = context.getResources();
        this.mDefaultAudioRouteId = resources.getString(android.R.string.date_picker_next_month_button);
        this.mBluetoothA2dpRouteId = resources.getString(android.R.string.biometric_error_device_not_secured);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mAudioService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        this.mAudioPlayerStateMonitor = com.android.server.media.AudioPlayerStateMonitor.getInstance(context);
        byte b = 0;
        this.mAudioPlayerStateMonitor.registerListener(new com.android.server.media.MediaRouterService.AudioPlayerActiveStateChangedListenerImpl(), this.mHandler);
        try {
            this.mAudioService.startWatchingRoutes(new com.android.server.media.MediaRouterService.AudioRoutesObserverImpl());
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException in the audio service.");
        }
        context.registerReceiverAsUser(this.mReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED"), null, null);
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
    public void systemRunning() throws android.os.RemoteException {
        android.app.ActivityManager.getService().registerUserSwitchObserver(new android.app.UserSwitchObserver() { // from class: com.android.server.media.MediaRouterService.1
            public void onUserSwitchComplete(int i) {
                com.android.server.media.MediaRouterService.this.updateRunningUserAndProfiles(i);
            }
        }, TAG);
        updateRunningUserAndProfiles(android.app.ActivityManager.getCurrentUser());
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    public void registerClientAsUser(android.media.IMediaRouterClient iMediaRouterClient, @android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (!validatePackageName(callingUid, str)) {
            throw new java.lang.SecurityException("packageName must match the calling uid");
        }
        int callingPid = android.os.Binder.getCallingPid();
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(callingPid, callingUid, i, false, true, "registerClientAsUser", str);
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY") == 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientLocked(iMediaRouterClient, callingUid, callingPid, str, handleIncomingUser, z);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerClientGroupId(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY") != 0) {
            android.util.Log.w(TAG, "Ignoring client group request because the client doesn't have the CONFIGURE_WIFI_DISPLAY permission.");
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientGroupIdLocked(iMediaRouterClient, str);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterClient(android.media.IMediaRouterClient iMediaRouterClient) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                unregisterClientLocked(iMediaRouterClient, false);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean showMediaOutputSwitcher(java.lang.String str) {
        if (!validatePackageName(android.os.Binder.getCallingUid(), str)) {
            throw new java.lang.SecurityException("packageName must match the calling identity");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getPackageImportance(str) > 100) {
                android.util.Slog.w(TAG, "showMediaOutputSwitcher only works when called from foreground");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            synchronized (this.mLock) {
                ((com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class)).showMediaOutputSwitcher(str);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.media.MediaRouterClientState getState(android.media.IMediaRouterClient iMediaRouterClient) {
        android.media.MediaRouterClientState stateLocked;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                stateLocked = getStateLocked(iMediaRouterClient);
            }
            return stateLocked;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isPlaybackActive(android.media.IMediaRouterClient iMediaRouterClient) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
            }
            if (clientRecord != null) {
                return this.mAudioPlayerStateMonitor.isPlaybackActive(clientRecord.mUid);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setBluetoothA2dpOn(android.media.IMediaRouterClient iMediaRouterClient, boolean z) {
        if (iMediaRouterClient == null) {
            throw new java.lang.IllegalArgumentException("client must not be null");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mAudioService.setBluetoothA2dpOn(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "RemoteException while calling setBluetoothA2dpOn. on=" + z);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDiscoveryRequest(android.media.IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setDiscoveryRequestLocked(iMediaRouterClient, i, z);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSelectedRoute(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setSelectedRouteLocked(iMediaRouterClient, str, z);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestSetVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestSetVolumeLocked(iMediaRouterClient, str, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestUpdateVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestUpdateVolumeLocked(iMediaRouterClient, str, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            printWriter.println("MEDIA ROUTER SERVICE (dumpsys media_router)");
            printWriter.println();
            printWriter.println("Global state");
            printWriter.println("  mCurrentUserId=" + this.mCurrentActiveUserId);
            synchronized (this.mLock) {
                try {
                    int size = this.mUserRecords.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.media.MediaRouterService.UserRecord valueAt = this.mUserRecords.valueAt(i);
                        printWriter.println();
                        valueAt.dump(printWriter, "");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            printWriter.println();
            this.mService2.dump(printWriter, "");
        }
    }

    public java.util.List<android.media.MediaRoute2Info> getSystemRoutes() {
        return this.mService2.getSystemRoutes();
    }

    public android.media.RoutingSessionInfo getSystemSessionInfo() {
        return this.mService2.getSystemSessionInfo(null, false);
    }

    public void registerRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) {
        if (!validatePackageName(android.os.Binder.getCallingUid(), str)) {
            throw new java.lang.SecurityException("packageName must match the calling uid");
        }
        this.mService2.registerRouter2(iMediaRouter2, str);
    }

    public void unregisterRouter2(android.media.IMediaRouter2 iMediaRouter2) {
        this.mService2.unregisterRouter2(iMediaRouter2);
    }

    public void updateScanningStateWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i) {
        this.mService2.updateScanningState(iMediaRouter2, i);
    }

    public void setDiscoveryRequestWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        this.mService2.setDiscoveryRequestWithRouter2(iMediaRouter2, routeDiscoveryPreference);
    }

    public void setRouteListingPreference(@android.annotation.NonNull android.media.IMediaRouter2 iMediaRouter2, @android.annotation.Nullable android.media.RouteListingPreference routeListingPreference) {
        this.mService2.setRouteListingPreference(iMediaRouter2, routeListingPreference);
    }

    public void setRouteVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.MediaRoute2Info mediaRoute2Info, int i) {
        this.mService2.setRouteVolumeWithRouter2(iMediaRouter2, mediaRoute2Info, i);
    }

    public void requestCreateSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i, long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.Bundle bundle, @android.annotation.Nullable android.os.UserHandle userHandle, @android.annotation.Nullable java.lang.String str) {
        this.mService2.requestCreateSessionWithRouter2(iMediaRouter2, i, j, routingSessionInfo, mediaRoute2Info, bundle, userHandle, str);
    }

    public void selectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mService2.selectRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void deselectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mService2.deselectRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void transferToRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mService2.transferToRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void setSessionVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, int i) {
        this.mService2.setSessionVolumeWithRouter2(iMediaRouter2, str, i);
    }

    public void releaseSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) {
        this.mService2.releaseSessionWithRouter2(iMediaRouter2, str);
    }

    public java.util.List<android.media.RoutingSessionInfo> getRemoteSessions(android.media.IMediaRouter2Manager iMediaRouter2Manager) {
        return this.mService2.getRemoteSessions(iMediaRouter2Manager);
    }

    public android.media.RoutingSessionInfo getSystemSessionInfoForPackage(@android.annotation.Nullable java.lang.String str) {
        boolean z;
        int identifier = android.os.UserHandle.getUserHandleForUid(android.os.Binder.getCallingUid()).getIdentifier();
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaRouterService.UserRecord userRecord = this.mUserRecords.get(identifier);
                java.util.Iterator it = (userRecord != null ? userRecord.mClientRecords : java.util.Collections.emptyList()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    com.android.server.media.MediaRouterService.ClientRecord clientRecord = (com.android.server.media.MediaRouterService.ClientRecord) it.next();
                    if (android.text.TextUtils.equals(clientRecord.mPackageName, str) && this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId)) {
                        z = true;
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mService2.getSystemSessionInfo(str, z);
    }

    @android.annotation.RequiresPermission("android.permission.MEDIA_CONTENT_CONTROL")
    public void registerManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str) {
        if (!validatePackageName(android.os.Binder.getCallingUid(), str)) {
            throw new java.lang.SecurityException("callerPackageName must match the calling uid");
        }
        this.mService2.registerManager(iMediaRouter2Manager, str);
    }

    public void registerProxyRouter(@android.annotation.NonNull android.media.IMediaRouter2Manager iMediaRouter2Manager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.os.UserHandle userHandle) {
        if (!validatePackageName(android.os.Binder.getCallingUid(), str)) {
            throw new java.lang.SecurityException("callerPackageName must match the calling uid");
        }
        this.mService2.registerProxyRouter(iMediaRouter2Manager, str, str2, userHandle);
    }

    public void unregisterManager(android.media.IMediaRouter2Manager iMediaRouter2Manager) {
        this.mService2.unregisterManager(iMediaRouter2Manager);
    }

    public void updateScanningState(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) {
        this.mService2.updateScanningState(iMediaRouter2Manager, i);
    }

    public void setRouteVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.MediaRoute2Info mediaRoute2Info, int i2) {
        this.mService2.setRouteVolumeWithManager(iMediaRouter2Manager, i, mediaRoute2Info, i2);
    }

    public void requestCreateSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        this.mService2.requestCreateSessionWithManager(iMediaRouter2Manager, i, routingSessionInfo, mediaRoute2Info, userHandle, str);
    }

    public void selectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mService2.selectRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info);
    }

    public void deselectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mService2.deselectRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info);
    }

    public void transferToRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str2) {
        this.mService2.transferToRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info, userHandle, str2);
    }

    public void setSessionVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, int i2) {
        this.mService2.setSessionVolumeWithManager(iMediaRouter2Manager, i, str, i2);
    }

    public void releaseSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str) {
        this.mService2.releaseSessionWithManager(iMediaRouter2Manager, i, str);
    }

    void restoreBluetoothA2dp() {
        boolean z;
        android.bluetooth.BluetoothDevice bluetoothDevice;
        try {
            synchronized (this.mLock) {
                z = this.mGlobalBluetoothA2dpOn;
                bluetoothDevice = this.mActiveBluetoothDevice;
            }
            if (bluetoothDevice != null) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "restoreBluetoothA2dp(" + z + ")");
                }
                this.mAudioService.setBluetoothA2dpOn(z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException while calling setBluetoothA2dpOn.");
        }
    }

    void restoreRoute(int i) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord;
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaRouterService.UserRecord userRecord = this.mUserRecords.get(android.os.UserHandle.getUserHandleForUid(i).getIdentifier());
                if (userRecord != null && userRecord.mClientRecords != null) {
                    java.util.Iterator<com.android.server.media.MediaRouterService.ClientRecord> it = userRecord.mClientRecords.iterator();
                    while (it.hasNext()) {
                        clientRecord = it.next();
                        if (validatePackageName(i, clientRecord.mPackageName)) {
                            break;
                        }
                    }
                }
                clientRecord = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (clientRecord != null) {
            try {
                clientRecord.mClient.onRestoreRoute();
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to call onRestoreRoute. Client probably died.");
                return;
            }
        }
        restoreBluetoothA2dp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRunningUserAndProfiles(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentActiveUserId != i) {
                    this.mCurrentActiveUserId = i;
                    android.util.SparseArray<com.android.server.media.MediaRouterService.UserRecord> clone = this.mUserRecords.clone();
                    for (int i2 = 0; i2 < clone.size(); i2++) {
                        int keyAt = clone.keyAt(i2);
                        com.android.server.media.MediaRouterService.UserRecord valueAt = clone.valueAt(i2);
                        if (isUserActiveLocked(keyAt)) {
                            valueAt.mHandler.sendEmptyMessage(1);
                        } else {
                            valueAt.mHandler.sendEmptyMessage(2);
                            disposeUserIfNeededLocked(valueAt);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mService2.updateRunningUserAndProfiles(i);
    }

    void clientDied(com.android.server.media.MediaRouterService.ClientRecord clientRecord) {
        synchronized (this.mLock) {
            unregisterClientLocked(clientRecord.mClient, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerClientLocked(android.media.IMediaRouterClient iMediaRouterClient, int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, boolean z) {
        com.android.server.media.MediaRouterService.UserRecord userRecord;
        boolean z2;
        android.os.IBinder asBinder = iMediaRouterClient.asBinder();
        if (this.mAllClientRecords.get(asBinder) == null) {
            com.android.server.media.MediaRouterService.UserRecord userRecord2 = this.mUserRecords.get(i3);
            if (userRecord2 != null) {
                userRecord = userRecord2;
                z2 = false;
            } else {
                userRecord = new com.android.server.media.MediaRouterService.UserRecord(i3);
                z2 = true;
            }
            com.android.server.media.MediaRouterService.ClientRecord clientRecord = new com.android.server.media.MediaRouterService.ClientRecord(userRecord, iMediaRouterClient, i, i2, str, z);
            try {
                asBinder.linkToDeath(clientRecord, 0);
                if (z2) {
                    this.mUserRecords.put(i3, userRecord);
                    initializeUserLocked(userRecord);
                }
                userRecord.mClientRecords.add(clientRecord);
                this.mAllClientRecords.put(asBinder, clientRecord);
                initializeClientLocked(clientRecord);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Media router client died prematurely.", e);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerClientGroupIdLocked(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord == null) {
            android.util.Log.w(TAG, "Ignoring group id register request of a unregistered client.");
            return;
        }
        if (android.text.TextUtils.equals(clientRecord.mGroupId, str)) {
            return;
        }
        com.android.server.media.MediaRouterService.UserRecord userRecord = clientRecord.mUserRecord;
        if (clientRecord.mGroupId != null) {
            userRecord.removeFromGroup(clientRecord.mGroupId, clientRecord);
        }
        clientRecord.mGroupId = str;
        if (str != null) {
            userRecord.addToGroup(str, clientRecord);
            userRecord.mHandler.obtainMessage(10, str).sendToTarget();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unregisterClientLocked(android.media.IMediaRouterClient iMediaRouterClient, boolean z) {
        com.android.server.media.MediaRouterService.ClientRecord remove = this.mAllClientRecords.remove(iMediaRouterClient.asBinder());
        if (remove != null) {
            com.android.server.media.MediaRouterService.UserRecord userRecord = remove.mUserRecord;
            userRecord.mClientRecords.remove(remove);
            if (remove.mGroupId != null) {
                userRecord.removeFromGroup(remove.mGroupId, remove);
                remove.mGroupId = null;
            }
            disposeClientLocked(remove, z);
            disposeUserIfNeededLocked(userRecord);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.MediaRouterClientState getStateLocked(android.media.IMediaRouterClient iMediaRouterClient) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            return clientRecord.getState();
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setDiscoveryRequestLocked(android.media.IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            if (!clientRecord.mTrusted) {
                i &= -5;
            }
            if (clientRecord.mRouteTypes != i || clientRecord.mActiveScan != z) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, clientRecord + ": Set discovery request, routeTypes=0x" + java.lang.Integer.toHexString(i) + ", activeScan=" + z);
                }
                clientRecord.mRouteTypes = i;
                clientRecord.mActiveScan = z;
                clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setSelectedRouteLocked(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, boolean z) {
        com.android.server.media.MediaRouterService.ClientGroup clientGroup;
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            java.lang.String str2 = (this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId) || this.mBluetoothA2dpRouteId.equals(clientRecord.mSelectedRouteId)) ? null : clientRecord.mSelectedRouteId;
            clientRecord.mSelectedRouteId = str;
            if (this.mDefaultAudioRouteId.equals(str) || this.mBluetoothA2dpRouteId.equals(str)) {
                str = null;
            }
            if (!java.util.Objects.equals(str, str2)) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, clientRecord + ": Set selected route, routeId=" + str + ", oldRouteId=" + str2 + ", explicit=" + z);
                }
                if (z && clientRecord.mTrusted) {
                    if (str2 != null) {
                        clientRecord.mUserRecord.mHandler.obtainMessage(5, str2).sendToTarget();
                    }
                    if (str != null) {
                        clientRecord.mUserRecord.mHandler.obtainMessage(4, str).sendToTarget();
                    }
                    if (clientRecord.mGroupId != null && (clientGroup = (com.android.server.media.MediaRouterService.ClientGroup) clientRecord.mUserRecord.mClientGroupMap.get(clientRecord.mGroupId)) != null) {
                        clientGroup.mSelectedRouteId = str;
                        clientRecord.mUserRecord.mHandler.obtainMessage(10, clientRecord.mGroupId).sendToTarget();
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestSetVolumeLocked(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            clientRecord.mUserRecord.mHandler.obtainMessage(6, i, 0, str).sendToTarget();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void requestUpdateVolumeLocked(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) {
        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            clientRecord.mUserRecord.mHandler.obtainMessage(7, i, 0, str).sendToTarget();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initializeUserLocked(com.android.server.media.MediaRouterService.UserRecord userRecord) {
        if (DEBUG) {
            android.util.Slog.d(TAG, userRecord + ": Initialized");
        }
        if (isUserActiveLocked(userRecord.mUserId)) {
            userRecord.mHandler.sendEmptyMessage(1);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void disposeUserIfNeededLocked(com.android.server.media.MediaRouterService.UserRecord userRecord) {
        if (!isUserActiveLocked(userRecord.mUserId) && userRecord.mClientRecords.isEmpty()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, userRecord + ": Disposed");
            }
            this.mUserRecords.remove(userRecord.mUserId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUserActiveLocked(int i) {
        return this.mUserManagerInternal.getProfileParentId(i) == this.mCurrentActiveUserId;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initializeClientLocked(com.android.server.media.MediaRouterService.ClientRecord clientRecord) {
        if (DEBUG) {
            android.util.Slog.d(TAG, clientRecord + ": Registered");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void disposeClientLocked(com.android.server.media.MediaRouterService.ClientRecord clientRecord, boolean z) {
        if (DEBUG) {
            if (z) {
                android.util.Slog.d(TAG, clientRecord + ": Died!");
            } else {
                android.util.Slog.d(TAG, clientRecord + ": Unregistered");
            }
        }
        if (clientRecord.mRouteTypes != 0 || clientRecord.mActiveScan) {
            clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
        }
        clientRecord.dispose();
    }

    private boolean validatePackageName(int i, java.lang.String str) {
        java.lang.String[] packagesForUid;
        if (str != null && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null) {
            for (java.lang.String str2 : packagesForUid) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    final class MediaRouterServiceBroadcastReceiver extends android.content.BroadcastReceiver {
        MediaRouterServiceBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
                synchronized (com.android.server.media.MediaRouterService.this.mLock) {
                    com.android.server.media.MediaRouterService.this.mActiveBluetoothDevice = bluetoothDevice;
                    com.android.server.media.MediaRouterService.this.mGlobalBluetoothA2dpOn = bluetoothDevice != null;
                }
            }
        }
    }

    final class ClientRecord implements android.os.IBinder.DeathRecipient {
        public boolean mActiveScan;
        public final android.media.IMediaRouterClient mClient;
        public java.util.List<java.lang.String> mControlCategories;
        public java.lang.String mGroupId;
        public final java.lang.String mPackageName;
        public final int mPid;
        public int mRouteTypes;
        public java.lang.String mSelectedRouteId;
        public final boolean mTrusted;
        public final int mUid;
        public final com.android.server.media.MediaRouterService.UserRecord mUserRecord;

        ClientRecord(com.android.server.media.MediaRouterService.UserRecord userRecord, android.media.IMediaRouterClient iMediaRouterClient, int i, int i2, @android.annotation.NonNull java.lang.String str, boolean z) {
            this.mUserRecord = userRecord;
            this.mClient = iMediaRouterClient;
            this.mUid = i;
            this.mPid = i2;
            this.mPackageName = str;
            this.mTrusted = z;
        }

        public void dispose() {
            this.mClient.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.media.MediaRouterService.this.clientDied(this);
        }

        android.media.MediaRouterClientState getState() {
            if (this.mTrusted) {
                return this.mUserRecord.mRouterState;
            }
            return null;
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + this);
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mTrusted=" + this.mTrusted);
            printWriter.println(str2 + "mRouteTypes=0x" + java.lang.Integer.toHexString(this.mRouteTypes));
            printWriter.println(str2 + "mActiveScan=" + this.mActiveScan);
            printWriter.println(str2 + "mSelectedRouteId=" + this.mSelectedRouteId);
        }

        public java.lang.String toString() {
            return "Client " + this.mPackageName + " (pid " + this.mPid + ")";
        }
    }

    final class ClientGroup {
        public final java.util.List<com.android.server.media.MediaRouterService.ClientRecord> mClientRecords = new java.util.ArrayList();
        public java.lang.String mSelectedRouteId;

        ClientGroup() {
        }
    }

    final class UserRecord {
        public final com.android.server.media.MediaRouterService.UserHandler mHandler;
        public android.media.MediaRouterClientState mRouterState;
        public final int mUserId;
        public final java.util.ArrayList<com.android.server.media.MediaRouterService.ClientRecord> mClientRecords = new java.util.ArrayList<>();
        private final android.util.ArrayMap<java.lang.String, com.android.server.media.MediaRouterService.ClientGroup> mClientGroupMap = new android.util.ArrayMap<>();

        public UserRecord(int i) {
            this.mUserId = i;
            this.mHandler = new com.android.server.media.MediaRouterService.UserHandler(com.android.server.media.MediaRouterService.this, this);
        }

        public void dump(final java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + this);
            final java.lang.String str2 = str + "  ";
            int size = this.mClientRecords.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    this.mClientRecords.get(i).dump(printWriter, str2);
                }
            } else {
                printWriter.println(str2 + "<no clients>");
            }
            printWriter.println(str2 + "State");
            printWriter.println(str2 + "mRouterState=" + this.mRouterState);
            if (!this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.media.MediaRouterService.UserRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.media.MediaRouterService.UserRecord.this.mHandler.dump(printWriter, str2);
                }
            }, 1000L)) {
                printWriter.println(str2 + "<could not dump handler state>");
            }
        }

        public void addToGroup(java.lang.String str, com.android.server.media.MediaRouterService.ClientRecord clientRecord) {
            com.android.server.media.MediaRouterService.ClientGroup clientGroup = this.mClientGroupMap.get(str);
            if (clientGroup == null) {
                clientGroup = com.android.server.media.MediaRouterService.this.new ClientGroup();
                this.mClientGroupMap.put(str, clientGroup);
            }
            clientGroup.mClientRecords.add(clientRecord);
        }

        public void removeFromGroup(java.lang.String str, com.android.server.media.MediaRouterService.ClientRecord clientRecord) {
            com.android.server.media.MediaRouterService.ClientGroup clientGroup = this.mClientGroupMap.get(str);
            if (clientGroup != null) {
                clientGroup.mClientRecords.remove(clientRecord);
                if (clientGroup.mClientRecords.size() == 0) {
                    this.mClientGroupMap.remove(str);
                }
            }
        }

        public java.lang.String toString() {
            return "User " + this.mUserId;
        }
    }

    static final class UserHandler extends android.os.Handler implements com.android.server.media.RemoteDisplayProviderWatcher.Callback, com.android.server.media.RemoteDisplayProviderProxy.Callback {
        private static final int MSG_CONNECTION_TIMED_OUT = 9;
        private static final int MSG_NOTIFY_GROUP_ROUTE_SELECTED = 10;
        public static final int MSG_REQUEST_SET_VOLUME = 6;
        public static final int MSG_REQUEST_UPDATE_VOLUME = 7;
        public static final int MSG_SELECT_ROUTE = 4;
        public static final int MSG_START = 1;
        public static final int MSG_STOP = 2;
        public static final int MSG_UNSELECT_ROUTE = 5;
        private static final int MSG_UPDATE_CLIENT_STATE = 8;
        public static final int MSG_UPDATE_DISCOVERY_REQUEST = 3;
        private static final int PHASE_CONNECTED = 2;
        private static final int PHASE_CONNECTING = 1;
        private static final int PHASE_NOT_AVAILABLE = -1;
        private static final int PHASE_NOT_CONNECTED = 0;
        private static final int TIMEOUT_REASON_CONNECTION_LOST = 2;
        private static final int TIMEOUT_REASON_NOT_AVAILABLE = 1;
        private static final int TIMEOUT_REASON_WAITING_FOR_CONNECTED = 4;
        private static final int TIMEOUT_REASON_WAITING_FOR_CONNECTING = 3;
        private boolean mClientStateUpdateScheduled;
        private int mConnectionPhase;
        private int mConnectionTimeoutReason;
        private long mConnectionTimeoutStartTime;
        private int mDiscoveryMode;
        private final java.util.ArrayList<com.android.server.media.MediaRouterService.UserHandler.ProviderRecord> mProviderRecords;
        private boolean mRunning;
        private com.android.server.media.MediaRouterService.UserHandler.RouteRecord mSelectedRouteRecord;
        private final com.android.server.media.MediaRouterService mService;
        private final java.util.ArrayList<android.media.IMediaRouterClient> mTempClients;
        private final com.android.server.media.MediaRouterService.UserRecord mUserRecord;
        private final com.android.server.media.RemoteDisplayProviderWatcher mWatcher;

        public UserHandler(com.android.server.media.MediaRouterService mediaRouterService, com.android.server.media.MediaRouterService.UserRecord userRecord) {
            super(android.os.Looper.getMainLooper(), null, true);
            this.mProviderRecords = new java.util.ArrayList<>();
            this.mTempClients = new java.util.ArrayList<>();
            this.mDiscoveryMode = 0;
            this.mConnectionPhase = -1;
            this.mService = mediaRouterService;
            this.mUserRecord = userRecord;
            this.mWatcher = new com.android.server.media.RemoteDisplayProviderWatcher(mediaRouterService.mContext, this, this, this.mUserRecord.mUserId);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    start();
                    break;
                case 2:
                    stop();
                    break;
                case 3:
                    updateDiscoveryRequest();
                    break;
                case 4:
                    selectRoute((java.lang.String) message.obj);
                    break;
                case 5:
                    unselectRoute((java.lang.String) message.obj);
                    break;
                case 6:
                    requestSetVolume((java.lang.String) message.obj, message.arg1);
                    break;
                case 7:
                    requestUpdateVolume((java.lang.String) message.obj, message.arg1);
                    break;
                case 8:
                    updateClientState();
                    break;
                case 9:
                    connectionTimedOut();
                    break;
                case 10:
                    notifyGroupRouteSelected((java.lang.String) message.obj);
                    break;
            }
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "Handler");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mRunning=" + this.mRunning);
            printWriter.println(str2 + "mDiscoveryMode=" + this.mDiscoveryMode);
            printWriter.println(str2 + "mSelectedRouteRecord=" + this.mSelectedRouteRecord);
            printWriter.println(str2 + "mConnectionPhase=" + this.mConnectionPhase);
            printWriter.println(str2 + "mConnectionTimeoutReason=" + this.mConnectionTimeoutReason);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str2);
            sb.append("mConnectionTimeoutStartTime=");
            sb.append(this.mConnectionTimeoutReason != 0 ? android.util.TimeUtils.formatUptime(this.mConnectionTimeoutStartTime) : "<n/a>");
            printWriter.println(sb.toString());
            this.mWatcher.dump(printWriter, str);
            int size = this.mProviderRecords.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    this.mProviderRecords.get(i).dump(printWriter, str);
                }
                return;
            }
            printWriter.println(str2 + "<no providers>");
        }

        private void start() {
            if (!this.mRunning) {
                this.mRunning = true;
                this.mWatcher.start();
            }
        }

        private void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                unselectSelectedRoute();
                this.mWatcher.stop();
            }
        }

        private void updateDiscoveryRequest() {
            int i;
            int i2;
            boolean z;
            int i3;
            synchronized (this.mService.mLock) {
                try {
                    int size = this.mUserRecord.mClientRecords.size();
                    i2 = 0;
                    z = false;
                    for (int i4 = 0; i4 < size; i4++) {
                        com.android.server.media.MediaRouterService.ClientRecord clientRecord = this.mUserRecord.mClientRecords.get(i4);
                        i2 |= clientRecord.mRouteTypes;
                        z |= clientRecord.mActiveScan;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if ((i2 & 4) != 0) {
                if (z) {
                    i3 = 2;
                } else {
                    i3 = 1;
                }
            } else {
                i3 = 0;
            }
            if (this.mDiscoveryMode != i3) {
                this.mDiscoveryMode = i3;
                int size2 = this.mProviderRecords.size();
                for (i = 0; i < size2; i++) {
                    this.mProviderRecords.get(i).getProvider().setDiscoveryMode(this.mDiscoveryMode);
                }
            }
        }

        private void selectRoute(java.lang.String str) {
            com.android.server.media.MediaRouterService.UserHandler.RouteRecord findRouteRecord;
            if (str != null) {
                if ((this.mSelectedRouteRecord == null || !str.equals(this.mSelectedRouteRecord.getUniqueId())) && (findRouteRecord = findRouteRecord(str)) != null) {
                    unselectSelectedRoute();
                    android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Selected route:" + findRouteRecord);
                    this.mSelectedRouteRecord = findRouteRecord;
                    checkSelectedRouteState();
                    findRouteRecord.getProvider().setSelectedDisplay(findRouteRecord.getDescriptorId());
                    scheduleUpdateClientState();
                }
            }
        }

        private void unselectRoute(java.lang.String str) {
            if (str != null && this.mSelectedRouteRecord != null && str.equals(this.mSelectedRouteRecord.getUniqueId())) {
                unselectSelectedRoute();
            }
        }

        private void unselectSelectedRoute() {
            if (this.mSelectedRouteRecord != null) {
                android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Unselected route:" + this.mSelectedRouteRecord);
                this.mSelectedRouteRecord.getProvider().setSelectedDisplay(null);
                this.mSelectedRouteRecord = null;
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        private void requestSetVolume(java.lang.String str, int i) {
            if (this.mSelectedRouteRecord != null && str.equals(this.mSelectedRouteRecord.getUniqueId())) {
                this.mSelectedRouteRecord.getProvider().setDisplayVolume(i);
            }
        }

        private void requestUpdateVolume(java.lang.String str, int i) {
            if (this.mSelectedRouteRecord != null && str.equals(this.mSelectedRouteRecord.getUniqueId())) {
                this.mSelectedRouteRecord.getProvider().adjustDisplayVolume(i);
            }
        }

        @Override // com.android.server.media.RemoteDisplayProviderWatcher.Callback
        public void addProvider(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            remoteDisplayProviderProxy.setCallback(this);
            remoteDisplayProviderProxy.setDiscoveryMode(this.mDiscoveryMode);
            remoteDisplayProviderProxy.setSelectedDisplay(null);
            com.android.server.media.MediaRouterService.UserHandler.ProviderRecord providerRecord = new com.android.server.media.MediaRouterService.UserHandler.ProviderRecord(remoteDisplayProviderProxy);
            this.mProviderRecords.add(providerRecord);
            providerRecord.updateDescriptor(remoteDisplayProviderProxy.getDisplayState());
            scheduleUpdateClientState();
        }

        @Override // com.android.server.media.RemoteDisplayProviderWatcher.Callback
        public void removeProvider(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            int findProviderRecord = findProviderRecord(remoteDisplayProviderProxy);
            if (findProviderRecord >= 0) {
                this.mProviderRecords.remove(findProviderRecord).updateDescriptor(null);
                remoteDisplayProviderProxy.setCallback(null);
                remoteDisplayProviderProxy.setDiscoveryMode(0);
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        @Override // com.android.server.media.RemoteDisplayProviderProxy.Callback
        public void onDisplayStateChanged(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy, android.media.RemoteDisplayState remoteDisplayState) {
            updateProvider(remoteDisplayProviderProxy, remoteDisplayState);
        }

        private void updateProvider(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy, android.media.RemoteDisplayState remoteDisplayState) {
            int findProviderRecord = findProviderRecord(remoteDisplayProviderProxy);
            if (findProviderRecord >= 0 && this.mProviderRecords.get(findProviderRecord).updateDescriptor(remoteDisplayState)) {
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        private void checkSelectedRouteState() {
            if (this.mSelectedRouteRecord == null) {
                this.mConnectionPhase = -1;
                updateConnectionTimeout(0);
            }
            if (!this.mSelectedRouteRecord.isValid() || !this.mSelectedRouteRecord.isEnabled()) {
                updateConnectionTimeout(1);
                return;
            }
            int i = this.mConnectionPhase;
            this.mConnectionPhase = getConnectionPhase(this.mSelectedRouteRecord.getStatus());
            if (i >= 1 && this.mConnectionPhase < 1) {
                updateConnectionTimeout(2);
                return;
            }
            switch (this.mConnectionPhase) {
                case 0:
                    updateConnectionTimeout(3);
                    break;
                case 1:
                    if (i != 1) {
                        android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Connecting to route: " + this.mSelectedRouteRecord);
                    }
                    updateConnectionTimeout(4);
                    break;
                case 2:
                    if (i != 2) {
                        android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Connected to route: " + this.mSelectedRouteRecord);
                    }
                    updateConnectionTimeout(0);
                    break;
                default:
                    updateConnectionTimeout(1);
                    break;
            }
        }

        private void updateConnectionTimeout(int i) {
            if (i != this.mConnectionTimeoutReason) {
                if (this.mConnectionTimeoutReason != 0) {
                    removeMessages(9);
                }
                this.mConnectionTimeoutReason = i;
                this.mConnectionTimeoutStartTime = android.os.SystemClock.uptimeMillis();
                switch (i) {
                    case 1:
                    case 2:
                        sendEmptyMessage(9);
                        break;
                    case 3:
                        sendEmptyMessageDelayed(9, com.android.server.media.MediaRouterService.CONNECTING_TIMEOUT);
                        break;
                    case 4:
                        sendEmptyMessageDelayed(9, 60000L);
                        break;
                }
            }
        }

        private void connectionTimedOut() {
            if (this.mConnectionTimeoutReason == 0 || this.mSelectedRouteRecord == null) {
                android.util.Log.wtf(com.android.server.media.MediaRouterService.TAG, "Handled connection timeout for no reason.");
                return;
            }
            switch (this.mConnectionTimeoutReason) {
                case 1:
                    android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Selected route no longer available: " + this.mSelectedRouteRecord);
                    break;
                case 2:
                    android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Selected route connection lost: " + this.mSelectedRouteRecord);
                    break;
                case 3:
                    android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Selected route timed out while waiting for connection attempt to begin after " + (android.os.SystemClock.uptimeMillis() - this.mConnectionTimeoutStartTime) + " ms: " + this.mSelectedRouteRecord);
                    break;
                case 4:
                    android.util.Slog.i(com.android.server.media.MediaRouterService.TAG, "Selected route timed out while connecting after " + (android.os.SystemClock.uptimeMillis() - this.mConnectionTimeoutStartTime) + " ms: " + this.mSelectedRouteRecord);
                    break;
            }
            this.mConnectionTimeoutReason = 0;
            unselectSelectedRoute();
        }

        private void scheduleUpdateClientState() {
            if (!this.mClientStateUpdateScheduled) {
                this.mClientStateUpdateScheduled = true;
                sendEmptyMessage(8);
            }
        }

        private void updateClientState() {
            this.mClientStateUpdateScheduled = false;
            android.media.MediaRouterClientState mediaRouterClientState = new android.media.MediaRouterClientState();
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                this.mProviderRecords.get(i).appendClientState(mediaRouterClientState);
            }
            try {
                synchronized (this.mService.mLock) {
                    try {
                        this.mUserRecord.mRouterState = mediaRouterClientState;
                        int size2 = this.mUserRecord.mClientRecords.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            this.mTempClients.add(this.mUserRecord.mClientRecords.get(i2).mClient);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                int size3 = this.mTempClients.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    try {
                        this.mTempClients.get(i3).onStateChanged();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.media.MediaRouterService.TAG, "Failed to call onStateChanged. Client probably died.");
                    }
                }
            } finally {
                this.mTempClients.clear();
            }
        }

        private void notifyGroupRouteSelected(java.lang.String str) {
            try {
                synchronized (this.mService.mLock) {
                    com.android.server.media.MediaRouterService.ClientGroup clientGroup = (com.android.server.media.MediaRouterService.ClientGroup) this.mUserRecord.mClientGroupMap.get(str);
                    if (clientGroup == null) {
                        return;
                    }
                    java.lang.String str2 = clientGroup.mSelectedRouteId;
                    int size = clientGroup.mClientRecords.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.media.MediaRouterService.ClientRecord clientRecord = clientGroup.mClientRecords.get(i);
                        if (!android.text.TextUtils.equals(str2, clientRecord.mSelectedRouteId)) {
                            this.mTempClients.add(clientRecord.mClient);
                        }
                    }
                    int size2 = this.mTempClients.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        try {
                            this.mTempClients.get(i2).onGroupRouteSelected(str2);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w(com.android.server.media.MediaRouterService.TAG, "Failed to call onSelectedRouteChanged. Client probably died.");
                        }
                    }
                }
            } finally {
                this.mTempClients.clear();
            }
        }

        private int findProviderRecord(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mProviderRecords.get(i).getProvider() == remoteDisplayProviderProxy) {
                    return i;
                }
            }
            return -1;
        }

        private com.android.server.media.MediaRouterService.UserHandler.RouteRecord findRouteRecord(java.lang.String str) {
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                com.android.server.media.MediaRouterService.UserHandler.RouteRecord findRouteByUniqueId = this.mProviderRecords.get(i).findRouteByUniqueId(str);
                if (findRouteByUniqueId != null) {
                    return findRouteByUniqueId;
                }
            }
            return null;
        }

        private static int getConnectionPhase(int i) {
            switch (i) {
                case 0:
                case 6:
                    return 2;
                case 1:
                case 3:
                    return 0;
                case 2:
                    return 1;
                case 4:
                case 5:
                default:
                    return -1;
            }
        }

        static final class ProviderRecord {
            private android.media.RemoteDisplayState mDescriptor;
            private final com.android.server.media.RemoteDisplayProviderProxy mProvider;
            private final java.util.ArrayList<com.android.server.media.MediaRouterService.UserHandler.RouteRecord> mRoutes = new java.util.ArrayList<>();
            private final java.lang.String mUniquePrefix;

            public ProviderRecord(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
                this.mProvider = remoteDisplayProviderProxy;
                this.mUniquePrefix = remoteDisplayProviderProxy.getFlattenedComponentName() + ":";
            }

            public com.android.server.media.RemoteDisplayProviderProxy getProvider() {
                return this.mProvider;
            }

            public java.lang.String getUniquePrefix() {
                return this.mUniquePrefix;
            }

            /* JADX WARN: Removed duplicated region for block: B:23:0x0099 A[LOOP:1: B:22:0x0097->B:23:0x0099, LOOP_END] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public boolean updateDescriptor(android.media.RemoteDisplayState remoteDisplayState) {
                int i;
                int size;
                boolean z = false;
                z = false;
                if (this.mDescriptor != remoteDisplayState) {
                    this.mDescriptor = remoteDisplayState;
                    if (remoteDisplayState != null) {
                        if (remoteDisplayState.isValid()) {
                            java.util.ArrayList arrayList = remoteDisplayState.displays;
                            int size2 = arrayList.size();
                            boolean z2 = false;
                            i = 0;
                            for (int i2 = 0; i2 < size2; i2++) {
                                android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo = (android.media.RemoteDisplayState.RemoteDisplayInfo) arrayList.get(i2);
                                java.lang.String str = remoteDisplayInfo.id;
                                int findRouteByDescriptorId = findRouteByDescriptorId(str);
                                if (findRouteByDescriptorId < 0) {
                                    com.android.server.media.MediaRouterService.UserHandler.RouteRecord routeRecord = new com.android.server.media.MediaRouterService.UserHandler.RouteRecord(this, str, assignRouteUniqueId(str));
                                    this.mRoutes.add(i, routeRecord);
                                    routeRecord.updateDescriptor(remoteDisplayInfo);
                                    z2 = true;
                                    i++;
                                } else if (findRouteByDescriptorId < i) {
                                    android.util.Slog.w(com.android.server.media.MediaRouterService.TAG, "Ignoring route descriptor with duplicate id: " + remoteDisplayInfo);
                                } else {
                                    com.android.server.media.MediaRouterService.UserHandler.RouteRecord routeRecord2 = this.mRoutes.get(findRouteByDescriptorId);
                                    java.util.Collections.swap(this.mRoutes, findRouteByDescriptorId, i);
                                    z2 |= routeRecord2.updateDescriptor(remoteDisplayInfo);
                                    i++;
                                }
                            }
                            z = z2;
                            size = this.mRoutes.size() - 1;
                            while (size >= i) {
                                this.mRoutes.remove(size).updateDescriptor(null);
                                size--;
                                z = true;
                            }
                        } else {
                            android.util.Slog.w(com.android.server.media.MediaRouterService.TAG, "Ignoring invalid descriptor from media route provider: " + this.mProvider.getFlattenedComponentName());
                        }
                    }
                    i = 0;
                    size = this.mRoutes.size() - 1;
                    while (size >= i) {
                    }
                }
                return z;
            }

            public void appendClientState(android.media.MediaRouterClientState mediaRouterClientState) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    mediaRouterClientState.routes.add(this.mRoutes.get(i).getInfo());
                }
            }

            public com.android.server.media.MediaRouterService.UserHandler.RouteRecord findRouteByUniqueId(java.lang.String str) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.media.MediaRouterService.UserHandler.RouteRecord routeRecord = this.mRoutes.get(i);
                    if (routeRecord.getUniqueId().equals(str)) {
                        return routeRecord;
                    }
                }
                return null;
            }

            private int findRouteByDescriptorId(java.lang.String str) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    if (this.mRoutes.get(i).getDescriptorId().equals(str)) {
                        return i;
                    }
                }
                return -1;
            }

            public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                printWriter.println(str + this);
                java.lang.String str2 = str + "  ";
                this.mProvider.dump(printWriter, str2);
                int size = this.mRoutes.size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        this.mRoutes.get(i).dump(printWriter, str2);
                    }
                    return;
                }
                printWriter.println(str2 + "<no routes>");
            }

            public java.lang.String toString() {
                return "Provider " + this.mProvider.getFlattenedComponentName();
            }

            private java.lang.String assignRouteUniqueId(java.lang.String str) {
                return this.mUniquePrefix + str;
            }
        }

        static final class RouteRecord {
            private android.media.RemoteDisplayState.RemoteDisplayInfo mDescriptor;
            private final java.lang.String mDescriptorId;
            private android.media.MediaRouterClientState.RouteInfo mImmutableInfo;
            private final android.media.MediaRouterClientState.RouteInfo mMutableInfo;
            private final com.android.server.media.MediaRouterService.UserHandler.ProviderRecord mProviderRecord;

            public RouteRecord(com.android.server.media.MediaRouterService.UserHandler.ProviderRecord providerRecord, java.lang.String str, java.lang.String str2) {
                this.mProviderRecord = providerRecord;
                this.mDescriptorId = str;
                this.mMutableInfo = new android.media.MediaRouterClientState.RouteInfo(str2);
            }

            public com.android.server.media.RemoteDisplayProviderProxy getProvider() {
                return this.mProviderRecord.getProvider();
            }

            public com.android.server.media.MediaRouterService.UserHandler.ProviderRecord getProviderRecord() {
                return this.mProviderRecord;
            }

            public java.lang.String getDescriptorId() {
                return this.mDescriptorId;
            }

            public java.lang.String getUniqueId() {
                return this.mMutableInfo.id;
            }

            public android.media.MediaRouterClientState.RouteInfo getInfo() {
                if (this.mImmutableInfo == null) {
                    this.mImmutableInfo = new android.media.MediaRouterClientState.RouteInfo(this.mMutableInfo);
                }
                return this.mImmutableInfo;
            }

            public boolean isValid() {
                return this.mDescriptor != null;
            }

            public boolean isEnabled() {
                return this.mMutableInfo.enabled;
            }

            public int getStatus() {
                return this.mMutableInfo.statusCode;
            }

            public boolean updateDescriptor(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                boolean z = false;
                if (this.mDescriptor != remoteDisplayInfo) {
                    this.mDescriptor = remoteDisplayInfo;
                    if (remoteDisplayInfo != null) {
                        java.lang.String computeName = computeName(remoteDisplayInfo);
                        if (!java.util.Objects.equals(this.mMutableInfo.name, computeName)) {
                            this.mMutableInfo.name = computeName;
                            z = true;
                        }
                        java.lang.String computeDescription = computeDescription(remoteDisplayInfo);
                        if (!java.util.Objects.equals(this.mMutableInfo.description, computeDescription)) {
                            this.mMutableInfo.description = computeDescription;
                            z = true;
                        }
                        int computeSupportedTypes = computeSupportedTypes(remoteDisplayInfo);
                        if (this.mMutableInfo.supportedTypes != computeSupportedTypes) {
                            this.mMutableInfo.supportedTypes = computeSupportedTypes;
                            z = true;
                        }
                        boolean computeEnabled = computeEnabled(remoteDisplayInfo);
                        if (this.mMutableInfo.enabled != computeEnabled) {
                            this.mMutableInfo.enabled = computeEnabled;
                            z = true;
                        }
                        int computeStatusCode = computeStatusCode(remoteDisplayInfo);
                        if (this.mMutableInfo.statusCode != computeStatusCode) {
                            this.mMutableInfo.statusCode = computeStatusCode;
                            z = true;
                        }
                        int computePlaybackType = computePlaybackType(remoteDisplayInfo);
                        if (this.mMutableInfo.playbackType != computePlaybackType) {
                            this.mMutableInfo.playbackType = computePlaybackType;
                            z = true;
                        }
                        int computePlaybackStream = computePlaybackStream(remoteDisplayInfo);
                        if (this.mMutableInfo.playbackStream != computePlaybackStream) {
                            this.mMutableInfo.playbackStream = computePlaybackStream;
                            z = true;
                        }
                        int computeVolume = computeVolume(remoteDisplayInfo);
                        if (this.mMutableInfo.volume != computeVolume) {
                            this.mMutableInfo.volume = computeVolume;
                            z = true;
                        }
                        int computeVolumeMax = computeVolumeMax(remoteDisplayInfo);
                        if (this.mMutableInfo.volumeMax != computeVolumeMax) {
                            this.mMutableInfo.volumeMax = computeVolumeMax;
                            z = true;
                        }
                        int computeVolumeHandling = computeVolumeHandling(remoteDisplayInfo);
                        if (this.mMutableInfo.volumeHandling != computeVolumeHandling) {
                            this.mMutableInfo.volumeHandling = computeVolumeHandling;
                            z = true;
                        }
                        int computePresentationDisplayId = computePresentationDisplayId(remoteDisplayInfo);
                        if (this.mMutableInfo.presentationDisplayId != computePresentationDisplayId) {
                            this.mMutableInfo.presentationDisplayId = computePresentationDisplayId;
                            z = true;
                        }
                    }
                }
                if (z) {
                    this.mImmutableInfo = null;
                }
                return z;
            }

            public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                printWriter.println(str + this);
                java.lang.String str2 = str + "  ";
                printWriter.println(str2 + "mMutableInfo=" + this.mMutableInfo);
                printWriter.println(str2 + "mDescriptorId=" + this.mDescriptorId);
                printWriter.println(str2 + "mDescriptor=" + this.mDescriptor);
            }

            public java.lang.String toString() {
                return "Route " + this.mMutableInfo.name + " (" + this.mMutableInfo.id + ")";
            }

            private static java.lang.String computeName(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return remoteDisplayInfo.name;
            }

            private static java.lang.String computeDescription(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                java.lang.String str = remoteDisplayInfo.description;
                if (android.text.TextUtils.isEmpty(str)) {
                    return null;
                }
                return str;
            }

            private static int computeSupportedTypes(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 7;
            }

            private static boolean computeEnabled(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                switch (remoteDisplayInfo.status) {
                    case 2:
                    case 3:
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }

            private static int computeStatusCode(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                switch (remoteDisplayInfo.status) {
                    case 0:
                        return 4;
                    case 1:
                        return 5;
                    case 2:
                        return 3;
                    case 3:
                        return 2;
                    case 4:
                        return 6;
                    default:
                        return 0;
                }
            }

            private static int computePlaybackType(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 1;
            }

            private static int computePlaybackStream(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 3;
            }

            private static int computeVolume(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.volume;
                int i2 = remoteDisplayInfo.volumeMax;
                if (i < 0) {
                    return 0;
                }
                if (i > i2) {
                    return i2;
                }
                return i;
            }

            private static int computeVolumeMax(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.volumeMax;
                if (i > 0) {
                    return i;
                }
                return 0;
            }

            private static int computeVolumeHandling(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                switch (remoteDisplayInfo.volumeHandling) {
                    case 1:
                        return 1;
                    default:
                        return 0;
                }
            }

            private static int computePresentationDisplayId(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.presentationDisplayId;
                if (i < 0) {
                    return -1;
                }
                return i;
            }
        }
    }

    private class AudioPlayerActiveStateChangedListenerImpl implements com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener {
        private static final long WAIT_MS = 500;
        private final java.lang.Runnable mRestoreBluetoothA2dpRunnable;

        private AudioPlayerActiveStateChangedListenerImpl() {
            final com.android.server.media.MediaRouterService mediaRouterService = com.android.server.media.MediaRouterService.this;
            this.mRestoreBluetoothA2dpRunnable = new java.lang.Runnable() { // from class: com.android.server.media.MediaRouterService$AudioPlayerActiveStateChangedListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRouterService.this.restoreBluetoothA2dp();
                }
            };
        }

        @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
        public void onAudioPlayerActiveStateChanged(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            int i;
            boolean z2 = !z && audioPlaybackConfiguration.isActive();
            int clientUid = audioPlaybackConfiguration.getClientUid();
            int indexOf = com.android.server.media.MediaRouterService.this.mActivePlayerMinPriorityQueue.indexOf(audioPlaybackConfiguration.getPlayerInterfaceId());
            if (indexOf >= 0) {
                com.android.server.media.MediaRouterService.this.mActivePlayerMinPriorityQueue.remove(indexOf);
                com.android.server.media.MediaRouterService.this.mActivePlayerUidMinPriorityQueue.remove(indexOf);
            }
            if (z2) {
                com.android.server.media.MediaRouterService.this.mActivePlayerMinPriorityQueue.add(audioPlaybackConfiguration.getPlayerInterfaceId());
                com.android.server.media.MediaRouterService.this.mActivePlayerUidMinPriorityQueue.add(clientUid);
                i = clientUid;
            } else if (com.android.server.media.MediaRouterService.this.mActivePlayerUidMinPriorityQueue.size() <= 0) {
                i = -1;
            } else {
                i = com.android.server.media.MediaRouterService.this.mActivePlayerUidMinPriorityQueue.get(com.android.server.media.MediaRouterService.this.mActivePlayerUidMinPriorityQueue.size() - 1);
            }
            com.android.server.media.MediaRouterService.this.mHandler.removeCallbacks(this.mRestoreBluetoothA2dpRunnable);
            if (i >= 0) {
                com.android.server.media.MediaRouterService.this.restoreRoute(i);
                if (com.android.server.media.MediaRouterService.DEBUG) {
                    android.util.Slog.d(com.android.server.media.MediaRouterService.TAG, "onAudioPlayerActiveStateChanged: uid=" + clientUid + ", active=" + z2 + ", restoreUid=" + i);
                    return;
                }
                return;
            }
            com.android.server.media.MediaRouterService.this.mHandler.postDelayed(this.mRestoreBluetoothA2dpRunnable, 500L);
            if (com.android.server.media.MediaRouterService.DEBUG) {
                android.util.Slog.d(com.android.server.media.MediaRouterService.TAG, "onAudioPlayerActiveStateChanged: uid=" + clientUid + ", active=" + z2 + ", delaying");
            }
        }
    }

    private class AudioRoutesObserverImpl extends android.media.IAudioRoutesObserver.Stub {
        private static final int HEADSET_FLAGS = 19;

        private AudioRoutesObserverImpl() {
        }

        public void dispatchAudioRoutesChanged(android.media.AudioRoutesInfo audioRoutesInfo) {
            synchronized (com.android.server.media.MediaRouterService.this.mLock) {
                try {
                    if (audioRoutesInfo.mainType != com.android.server.media.MediaRouterService.this.mAudioRouteMainType) {
                        if ((audioRoutesInfo.mainType & 19) == 0) {
                            com.android.server.media.MediaRouterService.this.mGlobalBluetoothA2dpOn = (audioRoutesInfo.bluetoothName == null && com.android.server.media.MediaRouterService.this.mActiveBluetoothDevice == null) ? false : true;
                        } else {
                            com.android.server.media.MediaRouterService.this.mGlobalBluetoothA2dpOn = false;
                        }
                        com.android.server.media.MediaRouterService.this.mAudioRouteMainType = audioRoutesInfo.mainType;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
