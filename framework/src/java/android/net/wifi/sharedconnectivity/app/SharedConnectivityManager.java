package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class SharedConnectivityManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.class.getSimpleName();
    private final android.content.Context mContext;
    private final java.lang.String mIntentAction;
    private android.net.wifi.sharedconnectivity.service.ISharedConnectivityService mService;
    private android.content.ServiceConnection mServiceConnection;
    private final java.lang.String mServicePackageName;
    private android.os.UserManager mUserManager;
    private final java.util.Map<android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback, android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy> mProxyMap = new java.util.HashMap();
    private final java.util.Map<android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback, android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy> mCallbackProxyCache = new java.util.HashMap();
    private final java.lang.Object mProxyDataLock = new java.lang.Object();
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            context.unregisterReceiver(android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mBroadcastReceiver);
            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.bind();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    static final class SharedConnectivityCallbackProxy extends android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback.Stub {
        private final android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        SharedConnectivityCallbackProxy(java.util.concurrent.Executor executor, android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback sharedConnectivityClientCallback) {
            this.mExecutor = executor;
            this.mCallback = sharedConnectivityClientCallback;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onServiceConnected$0();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0() {
            this.mCallback.onServiceConnected();
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(final java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworksUpdated$1(list);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworksUpdated$1(java.util.List list) {
            this.mCallback.onHotspotNetworksUpdated(list);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(final java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworksUpdated$2(list);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworksUpdated$2(java.util.List list) {
            this.mCallback.onKnownNetworksUpdated(list);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(final android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onSharedConnectivitySettingsChanged$3(sharedConnectivitySettingsState);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedConnectivitySettingsChanged$3(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState) {
            this.mCallback.onSharedConnectivitySettingsChanged(sharedConnectivitySettingsState);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(final android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworkConnectionStatusChanged$4(hotspotNetworkConnectionStatus);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworkConnectionStatusChanged$4(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
            this.mCallback.onHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(final android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworkConnectionStatusChanged$5(knownNetworkConnectionStatus);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworkConnectionStatusChanged$5(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
            this.mCallback.onKnownNetworkConnectionStatusChanged(knownNetworkConnectionStatus);
        }
    }

    public static android.net.wifi.sharedconnectivity.app.SharedConnectivityManager create(android.content.Context context) {
        android.content.res.Resources resources = context.getResources();
        try {
            java.lang.String string = resources.getString(com.android.internal.R.string.config_sharedConnectivityServicePackage);
            java.lang.String string2 = resources.getString(com.android.internal.R.string.config_sharedConnectivityServiceIntentAction);
            if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2)) {
                return new android.net.wifi.sharedconnectivity.app.SharedConnectivityManager(context, string, string2);
            }
            android.util.Log.e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must not be empty");
            return null;
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must be defined");
            return null;
        }
    }

    public static android.net.wifi.sharedconnectivity.app.SharedConnectivityManager create(android.content.Context context, java.lang.String str, java.lang.String str2) {
        return new android.net.wifi.sharedconnectivity.app.SharedConnectivityManager(context, str, str2);
    }

    private SharedConnectivityManager(android.content.Context context, java.lang.String str, java.lang.String str2) {
        this.mContext = context;
        this.mServicePackageName = str;
        this.mIntentAction = str2;
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
    }

    /* renamed from: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1, reason: invalid class name */
    class AnonymousClass1 implements android.content.ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mService = android.net.wifi.sharedconnectivity.service.ISharedConnectivityService.Stub.asInterface(iBinder);
            synchronized (android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mProxyDataLock) {
                if (!android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.keySet().forEach(new java.util.function.Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.AnonymousClass1.this.lambda$onServiceConnected$0((android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback) obj);
                        }
                    });
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback sharedConnectivityClientCallback) {
            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.registerCallbackInternal(sharedConnectivityClientCallback, (android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy) android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.get(sharedConnectivityClientCallback));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mService = null;
            synchronized (android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mProxyDataLock) {
                if (!android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.keySet().forEach(new java.util.function.Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback) obj).onServiceDisconnected();
                        }
                    });
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
                if (!android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mProxyMap.isEmpty()) {
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mProxyMap.keySet().forEach(new java.util.function.Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback) obj).onServiceDisconnected();
                        }
                    });
                    android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.this.mProxyMap.clear();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bind() {
        this.mServiceConnection = new android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.AnonymousClass1();
        if (!this.mContext.bindService(new android.content.Intent().setPackage(this.mServicePackageName).setAction(this.mIntentAction), this.mServiceConnection, 1)) {
            this.mServiceConnection = null;
            if (this.mUserManager != null && !this.mUserManager.isUserUnlocked()) {
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction(android.content.Intent.ACTION_USER_UNLOCKED);
                this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
            } else {
                synchronized (this.mProxyDataLock) {
                    if (!this.mCallbackProxyCache.isEmpty()) {
                        this.mCallbackProxyCache.keySet().forEach(new java.util.function.Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                ((android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback) obj).onRegisterCallbackFailed(new java.lang.IllegalStateException("Failed to bind after user unlock"));
                            }
                        });
                        this.mCallbackProxyCache.clear();
                    }
                }
            }
        }
    }

    public android.content.BroadcastReceiver getBroadcastReceiver() {
        return this.mBroadcastReceiver;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCallbackInternal(android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback sharedConnectivityClientCallback, android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy sharedConnectivityCallbackProxy) {
        try {
            this.mService.registerCallback(sharedConnectivityCallbackProxy);
            synchronized (this.mProxyDataLock) {
                this.mProxyMap.put(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in registerCallback", e);
            sharedConnectivityClientCallback.onRegisterCallbackFailed(e);
        }
    }

    public void setService(android.os.IInterface iInterface) {
        this.mService = (android.net.wifi.sharedconnectivity.service.ISharedConnectivityService) iInterface;
    }

    public android.content.ServiceConnection getServiceConnection() {
        return this.mServiceConnection;
    }

    private void unbind() {
        if (this.mServiceConnection != null) {
            this.mContext.unbindService(this.mServiceConnection);
            this.mServiceConnection = null;
            this.mService = null;
        }
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback sharedConnectivityClientCallback) {
        boolean z;
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(sharedConnectivityClientCallback, "callback cannot be null");
        if (this.mProxyMap.containsKey(sharedConnectivityClientCallback) || this.mCallbackProxyCache.containsKey(sharedConnectivityClientCallback)) {
            android.util.Log.e(TAG, "Callback already registered");
            sharedConnectivityClientCallback.onRegisterCallbackFailed(new java.lang.IllegalStateException("Callback already registered"));
            return;
        }
        android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy sharedConnectivityCallbackProxy = new android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.SharedConnectivityCallbackProxy(executor, sharedConnectivityClientCallback);
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                z = this.mCallbackProxyCache.size() == 0;
                this.mCallbackProxyCache.put(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
            }
            if (z) {
                bind();
                return;
            }
            return;
        }
        registerCallbackInternal(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
    }

    public boolean unregisterCallback(android.net.wifi.sharedconnectivity.app.SharedConnectivityClientCallback sharedConnectivityClientCallback) {
        boolean isEmpty;
        boolean isEmpty2;
        java.util.Objects.requireNonNull(sharedConnectivityClientCallback, "callback cannot be null");
        if (!this.mProxyMap.containsKey(sharedConnectivityClientCallback) && !this.mCallbackProxyCache.containsKey(sharedConnectivityClientCallback)) {
            android.util.Log.e(TAG, "Callback not found, cannot unregister");
            return false;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (java.lang.IllegalArgumentException e) {
        }
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                this.mCallbackProxyCache.remove(sharedConnectivityClientCallback);
                isEmpty2 = this.mCallbackProxyCache.isEmpty();
            }
            if (isEmpty2) {
                unbind();
            }
            return true;
        }
        try {
            synchronized (this.mProxyDataLock) {
                this.mService.unregisterCallback(this.mProxyMap.get(sharedConnectivityClientCallback));
                this.mProxyMap.remove(sharedConnectivityClientCallback);
                isEmpty = this.mProxyMap.isEmpty();
            }
            if (isEmpty) {
                unbind();
            }
            return true;
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Exception in unregisterCallback", e2);
            return false;
        }
    }

    public boolean connectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
        java.util.Objects.requireNonNull(hotspotNetwork, "Hotspot network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.connectHotspotNetwork(hotspotNetwork);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in connectHotspotNetwork", e);
            return false;
        }
    }

    public boolean disconnectHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.disconnectHotspotNetwork(hotspotNetwork);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in disconnectHotspotNetwork", e);
            return false;
        }
    }

    public boolean connectKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
        java.util.Objects.requireNonNull(knownNetwork, "Known network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.connectKnownNetwork(knownNetwork);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in connectKnownNetwork", e);
            return false;
        }
    }

    public boolean forgetKnownNetwork(android.net.wifi.sharedconnectivity.app.KnownNetwork knownNetwork) {
        java.util.Objects.requireNonNull(knownNetwork, "Known network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.forgetKnownNetwork(knownNetwork);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in forgetKnownNetwork", e);
            return false;
        }
    }

    public java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> getHotspotNetworks() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getHotspotNetworks();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in getHotspotNetworks", e);
            return null;
        }
    }

    public java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> getKnownNetworks() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getKnownNetworks();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in getKnownNetworks", e);
            return null;
        }
    }

    public android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState getSettingsState() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getSettingsState();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in getSettingsState", e);
            return null;
        }
    }

    public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getHotspotNetworkConnectionStatus();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in getHotspotNetworkConnectionStatus", e);
            return null;
        }
    }

    public android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getKnownNetworkConnectionStatus();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in getKnownNetworkConnectionStatus", e);
            return null;
        }
    }
}
