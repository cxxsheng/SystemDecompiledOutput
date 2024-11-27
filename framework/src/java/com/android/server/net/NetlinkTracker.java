package com.android.server.net;

/* loaded from: classes5.dex */
public class NetlinkTracker extends com.android.server.net.BaseNetworkObserver {
    private static final boolean DBG = false;
    private final java.lang.String TAG;
    private final com.android.server.net.NetlinkTracker.Callback mCallback;
    private com.android.server.net.DnsServerRepository mDnsServerRepository;
    private final java.lang.String mInterfaceName;
    private final android.net.LinkProperties mLinkProperties = new android.net.LinkProperties();

    public interface Callback {
        void update();
    }

    public NetlinkTracker(java.lang.String str, com.android.server.net.NetlinkTracker.Callback callback) {
        this.TAG = "NetlinkTracker/" + str;
        this.mInterfaceName = str;
        this.mCallback = callback;
        this.mLinkProperties.setInterfaceName(this.mInterfaceName);
        this.mDnsServerRepository = new com.android.server.net.DnsServerRepository();
    }

    private void maybeLog(java.lang.String str, java.lang.String str2, android.net.LinkAddress linkAddress) {
    }

    private void maybeLog(java.lang.String str, java.lang.Object obj) {
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void interfaceRemoved(java.lang.String str) {
        maybeLog("interfaceRemoved", str);
        if (this.mInterfaceName.equals(str)) {
            clearLinkProperties();
            this.mCallback.update();
        }
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void addressUpdated(java.lang.String str, android.net.LinkAddress linkAddress) {
        boolean addLinkAddress;
        if (this.mInterfaceName.equals(str)) {
            maybeLog("addressUpdated", str, linkAddress);
            synchronized (this) {
                addLinkAddress = this.mLinkProperties.addLinkAddress(linkAddress);
            }
            if (addLinkAddress) {
                this.mCallback.update();
            }
        }
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void addressRemoved(java.lang.String str, android.net.LinkAddress linkAddress) {
        boolean removeLinkAddress;
        if (this.mInterfaceName.equals(str)) {
            maybeLog("addressRemoved", str, linkAddress);
            synchronized (this) {
                removeLinkAddress = this.mLinkProperties.removeLinkAddress(linkAddress);
            }
            if (removeLinkAddress) {
                this.mCallback.update();
            }
        }
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void routeUpdated(android.net.RouteInfo routeInfo) {
        boolean addRoute;
        if (this.mInterfaceName.equals(routeInfo.getInterface())) {
            maybeLog("routeUpdated", routeInfo);
            synchronized (this) {
                addRoute = this.mLinkProperties.addRoute(routeInfo);
            }
            if (addRoute) {
                this.mCallback.update();
            }
        }
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void routeRemoved(android.net.RouteInfo routeInfo) {
        boolean removeRoute;
        if (this.mInterfaceName.equals(routeInfo.getInterface())) {
            maybeLog("routeRemoved", routeInfo);
            synchronized (this) {
                removeRoute = this.mLinkProperties.removeRoute(routeInfo);
            }
            if (removeRoute) {
                this.mCallback.update();
            }
        }
    }

    @Override // com.android.server.net.BaseNetworkObserver, android.net.INetworkManagementEventObserver
    public void interfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) {
        if (this.mInterfaceName.equals(str)) {
            maybeLog("interfaceDnsServerInfo", java.util.Arrays.toString(strArr));
            if (this.mDnsServerRepository.addServers(j, strArr)) {
                synchronized (this) {
                    this.mDnsServerRepository.setDnsServersOn(this.mLinkProperties);
                }
                this.mCallback.update();
            }
        }
    }

    public synchronized android.net.LinkProperties getLinkProperties() {
        return new android.net.LinkProperties(this.mLinkProperties);
    }

    public synchronized void clearLinkProperties() {
        this.mDnsServerRepository = new com.android.server.net.DnsServerRepository();
        this.mLinkProperties.clear();
        this.mLinkProperties.setInterfaceName(this.mInterfaceName);
    }
}
