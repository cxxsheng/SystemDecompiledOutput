package com.android.server.net;

/* loaded from: classes5.dex */
public class BaseNetworkObserver extends android.net.INetworkManagementEventObserver.Stub {
    @Override // android.net.INetworkManagementEventObserver
    public void interfaceStatusChanged(java.lang.String str, boolean z) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void interfaceRemoved(java.lang.String str) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void addressUpdated(java.lang.String str, android.net.LinkAddress linkAddress) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void addressRemoved(java.lang.String str, android.net.LinkAddress linkAddress) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void interfaceLinkStateChanged(java.lang.String str, boolean z) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void interfaceAdded(java.lang.String str) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void limitReached(java.lang.String str, java.lang.String str2) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void interfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void routeUpdated(android.net.RouteInfo routeInfo) {
    }

    @Override // android.net.INetworkManagementEventObserver
    public void routeRemoved(android.net.RouteInfo routeInfo) {
    }
}
