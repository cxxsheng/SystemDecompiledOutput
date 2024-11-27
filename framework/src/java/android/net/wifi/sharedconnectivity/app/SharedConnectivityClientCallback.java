package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public interface SharedConnectivityClientCallback {
    void onHotspotNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus);

    void onHotspotNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.HotspotNetwork> list);

    void onKnownNetworkConnectionStatusChanged(android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus knownNetworkConnectionStatus);

    void onKnownNetworksUpdated(java.util.List<android.net.wifi.sharedconnectivity.app.KnownNetwork> list);

    void onRegisterCallbackFailed(java.lang.Exception exc);

    void onServiceConnected();

    void onServiceDisconnected();

    void onSharedConnectivitySettingsChanged(android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState sharedConnectivitySettingsState);
}
