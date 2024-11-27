package android.net.networkstack;

/* loaded from: classes.dex */
public abstract class NetworkStackClientBase {

    @com.android.internal.annotations.GuardedBy({"mPendingNetStackRequests"})
    @android.annotation.Nullable
    private android.net.INetworkStackConnector mConnector;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPendingNetStackRequests"})
    private final java.util.ArrayList<java.util.function.Consumer<android.net.INetworkStackConnector>> mPendingNetStackRequests = new java.util.ArrayList<>();

    public void makeDhcpServer(final java.lang.String str, final android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, final android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks) {
        requestConnector(new java.util.function.Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.net.networkstack.NetworkStackClientBase.lambda$makeDhcpServer$0(str, dhcpServingParamsParcel, iDhcpServerCallbacks, (android.net.INetworkStackConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeDhcpServer$0(java.lang.String str, android.net.dhcp.DhcpServingParamsParcel dhcpServingParamsParcel, android.net.dhcp.IDhcpServerCallbacks iDhcpServerCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeDhcpServer(str, dhcpServingParamsParcel, iDhcpServerCallbacks);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Could not create DhcpServer", e);
        }
    }

    public void makeIpClient(final java.lang.String str, final android.net.ip.IIpClientCallbacks iIpClientCallbacks) {
        requestConnector(new java.util.function.Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.net.networkstack.NetworkStackClientBase.lambda$makeIpClient$1(str, iIpClientCallbacks, (android.net.INetworkStackConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeIpClient$1(java.lang.String str, android.net.ip.IIpClientCallbacks iIpClientCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeIpClient(str, iIpClientCallbacks);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Could not create IpClient", e);
        }
    }

    public void makeNetworkMonitor(final android.net.Network network, final java.lang.String str, final android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        requestConnector(new java.util.function.Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.net.networkstack.NetworkStackClientBase.lambda$makeNetworkMonitor$2(network, str, iNetworkMonitorCallbacks, (android.net.INetworkStackConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeNetworkMonitor$2(android.net.Network network, java.lang.String str, android.net.INetworkMonitorCallbacks iNetworkMonitorCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeNetworkMonitor(network, str, iNetworkMonitorCallbacks);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Could not create NetworkMonitor", e);
        }
    }

    public void fetchIpMemoryStore(final android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) {
        requestConnector(new java.util.function.Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.net.networkstack.NetworkStackClientBase.lambda$fetchIpMemoryStore$3(android.net.IIpMemoryStoreCallbacks.this, (android.net.INetworkStackConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$fetchIpMemoryStore$3(android.net.IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks, android.net.INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.fetchIpMemoryStore(iIpMemoryStoreCallbacks);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Could not fetch IpMemoryStore", e);
        }
    }

    protected void requestConnector(@android.annotation.NonNull java.util.function.Consumer<android.net.INetworkStackConnector> consumer) {
        synchronized (this.mPendingNetStackRequests) {
            try {
                android.net.INetworkStackConnector iNetworkStackConnector = this.mConnector;
                if (iNetworkStackConnector == null) {
                    this.mPendingNetStackRequests.add(consumer);
                } else {
                    consumer.accept(iNetworkStackConnector);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void onNetworkStackConnected(@android.annotation.NonNull android.net.INetworkStackConnector iNetworkStackConnector) {
        java.util.ArrayList arrayList;
        while (true) {
            synchronized (this.mPendingNetStackRequests) {
                arrayList = new java.util.ArrayList(this.mPendingNetStackRequests);
                this.mPendingNetStackRequests.clear();
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((java.util.function.Consumer) it.next()).accept(iNetworkStackConnector);
            }
            synchronized (this.mPendingNetStackRequests) {
                try {
                    if (this.mPendingNetStackRequests.size() == 0) {
                        this.mConnector = iNetworkStackConnector;
                        return;
                    }
                } finally {
                }
            }
        }
    }

    protected int getQueueLength() {
        int size;
        synchronized (this.mPendingNetStackRequests) {
            size = this.mPendingNetStackRequests.size();
        }
        return size;
    }
}
