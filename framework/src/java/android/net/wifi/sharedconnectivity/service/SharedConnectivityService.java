package android.net.wifi.sharedconnectivity.service;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class SharedConnectivityService extends android.app.Service {
    private static final boolean DEBUG = true;
    private static final java.lang.String TAG = android.net.wifi.sharedconnectivity.service.SharedConnectivityService.class.getSimpleName();
    private java.util.concurrent.CountDownLatch mCountDownLatch;
    private android.os.Handler mHandler;
    private final android.os.RemoteCallbackList<android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback> mRemoteCallbackList = new android.os.RemoteCallbackList<>();
    private java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> mHotspotNetworks = java.util.Collections.emptyList();
    private java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> mKnownNetworks = java.util.Collections.emptyList();
    private android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState mSettingsState = null;
    private android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus mHotspotNetworkConnectionStatus = null;
    private android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus mKnownNetworkConnectionStatus = null;

    public abstract void onConnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork);

    public abstract void onConnectKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork);

    public abstract void onDisconnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork);

    public abstract void onForgetKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        android.util.Log.i(TAG, "onBind intent=" + intent);
        this.mHandler = new android.os.Handler(getMainLooper());
        android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1 anonymousClass1 = new android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1();
        onBind();
        return anonymousClass1;
    }

    /* renamed from: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1, reason: invalid class name */
    class AnonymousClass1 extends android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.Stub {
        AnonymousClass1() {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void registerCallback(final android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$registerCallback$0(iSharedConnectivityCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerCallback$0(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onRegisterCallback(iSharedConnectivityCallback);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void unregisterCallback(final android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$unregisterCallback$1(iSharedConnectivityCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unregisterCallback$1(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onUnregisterCallback(iSharedConnectivityCallback);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectHotspotNetwork(final android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$connectHotspotNetwork$2(hotspotNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectHotspotNetwork$2(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onConnectHotspotNetwork(hotspotNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void disconnectHotspotNetwork(final android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$disconnectHotspotNetwork$3(hotspotNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disconnectHotspotNetwork$3(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onDisconnectHotspotNetwork(hotspotNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectKnownNetwork(final android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$connectKnownNetwork$4(knownNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectKnownNetwork$4(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onConnectKnownNetwork(knownNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void forgetKnownNetwork(final android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
            checkPermissions();
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.sharedconnectivity.service.SharedConnectivityService.AnonymousClass1.this.lambda$forgetKnownNetwork$5(knownNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forgetKnownNetwork$5(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
            android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.onForgetKnownNetwork(knownNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> getHotspotNetworks() {
            checkPermissions();
            return android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHotspotNetworks;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> getKnownNetworks() {
            checkPermissions();
            return android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mKnownNetworks;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState getSettingsState() {
            checkPermissions();
            if (android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mSettingsState == null) {
                android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mSettingsState = new android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState.Builder().setInstantTetherEnabled(false).setExtras(android.os.Bundle.EMPTY).build();
            }
            return android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mSettingsState;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() {
            checkPermissions();
            return android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mHotspotNetworkConnectionStatus;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() {
            checkPermissions();
            return android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.mKnownNetworkConnectionStatus;
        }

        private void checkPermissions() {
            if (android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.checkCallingOrSelfPermission(android.Manifest.permission.NETWORK_SETTINGS) != 0 && android.net.wifi.sharedconnectivity.service.SharedConnectivityService.this.checkCallingOrSelfPermission(android.Manifest.permission.NETWORK_SETUP_WIZARD) != 0) {
                throw new java.lang.SecurityException("Calling process must have NETWORK_SETTINGS or NETWORK_SETUP_WIZARD permission");
            }
        }
    }

    public void onBind() {
    }

    public final void setCountdownLatch(java.util.concurrent.CountDownLatch countDownLatch) {
        this.mCountDownLatch = countDownLatch;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRegisterCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
        this.mRemoteCallbackList.register(iSharedConnectivityCallback);
        try {
            iSharedConnectivityCallback.onServiceConnected();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Exception in onRegisterCallback", e);
        }
        if (this.mCountDownLatch != null) {
            this.mCountDownLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnregisterCallback(android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback iSharedConnectivityCallback) {
        this.mRemoteCallbackList.unregister(iSharedConnectivityCallback);
        if (this.mCountDownLatch != null) {
            this.mCountDownLatch.countDown();
        }
    }

    public final void setHotspotNetworks(java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list) {
        this.mHotspotNetworks = list;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onHotspotNetworksUpdated(this.mHotspotNetworks);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Exception in setHotspotNetworks", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void setKnownNetworks(java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list) {
        this.mKnownNetworks = list;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onKnownNetworksUpdated(this.mKnownNetworks);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Exception in setKnownNetworks", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void setSettingsState(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) {
        this.mSettingsState = sharedConnectivitySettingsState;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onSharedConnectivitySettingsChanged(this.mSettingsState);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Exception in setSettingsState", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void updateHotspotNetworkConnectionStatus(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
        this.mHotspotNetworkConnectionStatus = hotspotNetworkConnectionStatus;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onHotspotNetworkConnectionStatusChanged(this.mHotspotNetworkConnectionStatus);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Exception in updateHotspotNetworkConnectionStatus", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void updateKnownNetworkConnectionStatus(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
        this.mKnownNetworkConnectionStatus = knownNetworkConnectionStatus;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onKnownNetworkConnectionStatusChanged(this.mKnownNetworkConnectionStatus);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Exception in updateKnownNetworkConnectionStatus", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public static boolean areHotspotNetworksEnabledForService(android.content.Context context) {
        return java.util.Objects.equals(context.getPackageName(), context.getResources().getString(com.android.internal.R.string.config_sharedConnectivityServicePackage)) && context.getResources().getBoolean(com.android.internal.R.bool.config_hotspotNetworksEnabledForService);
    }

    public static boolean areKnownNetworksEnabledForService(android.content.Context context) {
        return java.util.Objects.equals(context.getPackageName(), context.getResources().getString(com.android.internal.R.string.config_sharedConnectivityServicePackage)) && context.getResources().getBoolean(com.android.internal.R.bool.config_knownNetworksEnabledForService);
    }
}
