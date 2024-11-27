package android.net;

/* loaded from: classes.dex */
public class NetworkStackClient {
    private static final int NETWORKSTACK_TIMEOUT_MS = 10000;
    private static final java.lang.String TAG = android.net.NetworkStackClient.class.getSimpleName();
    private static android.net.NetworkStackClient sInstance;

    @com.android.internal.annotations.GuardedBy({"mPendingNetStackRequests"})
    @android.annotation.Nullable
    private android.net.INetworkStackConnector mConnector;

    @android.annotation.NonNull
    private final android.net.NetworkStackClient.Dependencies mDependencies;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPendingNetStackRequests"})
    private final java.util.ArrayList<android.net.NetworkStackClient.NetworkStackCallback> mPendingNetStackRequests;
    private volatile boolean mWasSystemServerInitialized;

    @com.android.internal.annotations.VisibleForTesting
    protected interface Dependencies {
        void addToServiceManager(@android.annotation.NonNull android.os.IBinder iBinder);

        void checkCallerUid();

        android.net.ConnectivityModuleConnector getConnectivityModuleConnector();
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface NetworkStackCallback {
        void onNetworkStackConnected(android.net.INetworkStackConnector iNetworkStackConnector);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected NetworkStackClient(@android.annotation.NonNull android.net.NetworkStackClient.Dependencies dependencies) {
        this.mPendingNetStackRequests = new java.util.ArrayList<>();
        this.mWasSystemServerInitialized = false;
        this.mDependencies = dependencies;
    }

    private NetworkStackClient() {
        this(new android.net.NetworkStackClient.DependenciesImpl());
    }

    private static class DependenciesImpl implements android.net.NetworkStackClient.Dependencies {
        private DependenciesImpl() {
        }

        @Override // android.net.NetworkStackClient.Dependencies
        public void addToServiceManager(@android.annotation.NonNull android.os.IBinder iBinder) {
            android.os.ServiceManager.addService("network_stack", iBinder, false, 6);
        }

        @Override // android.net.NetworkStackClient.Dependencies
        public void checkCallerUid() {
            int callingUid = android.os.Binder.getCallingUid();
            if (callingUid != 1000 && callingUid != 1073 && android.os.UserHandle.getAppId(callingUid) != 1002) {
                throw new java.lang.SecurityException("Only the system server should try to bind to the network stack.");
            }
        }

        @Override // android.net.NetworkStackClient.Dependencies
        public android.net.ConnectivityModuleConnector getConnectivityModuleConnector() {
            return android.net.ConnectivityModuleConnector.getInstance();
        }
    }

    public static synchronized android.net.NetworkStackClient getInstance() {
        android.net.NetworkStackClient networkStackClient;
        synchronized (android.net.NetworkStackClient.class) {
            try {
                if (sInstance == null) {
                    sInstance = new android.net.NetworkStackClient();
                }
                networkStackClient = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return networkStackClient;
    }

    public void makeDhcpServer(final java.lang.String str, final android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, final android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks) {
        requestConnector(new android.net.NetworkStackClient.NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda3
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(android.net.INetworkStackConnector iNetworkStackConnector) {
                android.net.NetworkStackClient.lambda$makeDhcpServer$0(str, dhcpServingParamsParcel, iDhcpServerCallbacks, iNetworkStackConnector);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeDhcpServer$0(java.lang.String str, android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeDhcpServer(str, dhcpServingParamsParcel, iDhcpServerCallbacks);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeIpClient(final java.lang.String str, final android.net.ip.IIpClientCallbacks iIpClientCallbacks) {
        requestConnector(new android.net.NetworkStackClient.NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda0
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(android.net.INetworkStackConnector iNetworkStackConnector) {
                android.net.NetworkStackClient.lambda$makeIpClient$1(str, iIpClientCallbacks, iNetworkStackConnector);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeIpClient$1(java.lang.String str, android.net.ip.IIpClientCallbacks iIpClientCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeIpClient(str, iIpClientCallbacks);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeNetworkMonitor(final android.net.Network network, final java.lang.String str, final android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        requestConnector(new android.net.NetworkStackClient.NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda1
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(android.net.INetworkStackConnector iNetworkStackConnector) {
                android.net.NetworkStackClient.lambda$makeNetworkMonitor$2(network, str, iNetworkMonitorCallbacks, iNetworkStackConnector);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeNetworkMonitor$2(android.net.Network network, java.lang.String str, android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeNetworkMonitor(network, str, iNetworkMonitorCallbacks);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void fetchIpMemoryStore(final android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) {
        requestConnector(new android.net.NetworkStackClient.NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda2
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(android.net.INetworkStackConnector iNetworkStackConnector) {
                android.net.NetworkStackClient.lambda$fetchIpMemoryStore$3(android.net.IIpMemoryStoreCallbacks.this, iNetworkStackConnector);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$fetchIpMemoryStore$3(android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.fetchIpMemoryStore(iIpMemoryStoreCallbacks);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private class NetworkStackConnection implements android.net.ConnectivityModuleConnector.ModuleServiceCallback {
        private NetworkStackConnection() {
        }

        @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
        public void onModuleServiceConnected(android.os.IBinder iBinder) {
            android.net.NetworkStackClient.this.logi("Network stack service connected");
            android.net.NetworkStackClient.this.registerNetworkStackService(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerNetworkStackService(@android.annotation.NonNull android.os.IBinder iBinder) {
        java.util.ArrayList arrayList;
        android.net.INetworkStackConnector asInterface = android.net.INetworkStackConnector.Stub.asInterface(iBinder);
        this.mDependencies.addToServiceManager(iBinder);
        log("Network stack service registered");
        synchronized (this.mPendingNetStackRequests) {
            arrayList = new java.util.ArrayList(this.mPendingNetStackRequests);
            this.mPendingNetStackRequests.clear();
            this.mConnector = asInterface;
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((android.net.NetworkStackClient.NetworkStackCallback) it.next()).onNetworkStackConnected(asInterface);
        }
    }

    public void init() {
        log("Network stack init");
        this.mWasSystemServerInitialized = true;
    }

    public void start() {
        this.mDependencies.getConnectivityModuleConnector().startModuleService(android.net.INetworkStackConnector.class.getName(), "android.permission.MAINLINE_NETWORK_STACK", new android.net.NetworkStackClient.NetworkStackConnection());
        log("Network stack service start requested");
    }

    private void log(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.d(TAG, str);
    }

    private void logWtf(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, str);
        android.util.Log.e(TAG, str, th);
    }

    private void loge(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Throwable th) {
        android.util.Log.e(TAG, str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logi(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.i(TAG, str);
    }

    private android.net.INetworkStackConnector getRemoteConnector() {
        try {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            do {
                android.os.IBinder service = android.os.ServiceManager.getService("network_stack");
                if (service == null) {
                    java.lang.Thread.sleep(20L);
                } else {
                    return android.net.INetworkStackConnector.Stub.asInterface(service);
                }
            } while (java.lang.System.currentTimeMillis() - currentTimeMillis <= com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            loge("Timeout waiting for NetworkStack connector", null);
            return null;
        } catch (java.lang.InterruptedException e) {
            loge("Error waiting for NetworkStack connector", e);
            return null;
        }
    }

    private void requestConnector(@android.annotation.NonNull android.net.NetworkStackClient.NetworkStackCallback networkStackCallback) {
        this.mDependencies.checkCallerUid();
        if (!this.mWasSystemServerInitialized) {
            android.net.INetworkStackConnector remoteConnector = getRemoteConnector();
            synchronized (this.mPendingNetStackRequests) {
                this.mConnector = remoteConnector;
            }
            networkStackCallback.onNetworkStackConnected(remoteConnector);
            return;
        }
        synchronized (this.mPendingNetStackRequests) {
            try {
                android.net.INetworkStackConnector iNetworkStackConnector = this.mConnector;
                if (iNetworkStackConnector == null) {
                    this.mPendingNetStackRequests.add(networkStackCallback);
                } else {
                    networkStackCallback.onNetworkStackConnected(iNetworkStackConnector);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
