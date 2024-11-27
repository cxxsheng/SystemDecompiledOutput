package android.net.ip;

/* loaded from: classes.dex */
public class IpClientCallbacks {
    public void onIpClientCreated(android.net.ip.IIpClient iIpClient) {
    }

    public void onPreDhcpAction() {
    }

    public void onPostDhcpAction() {
    }

    public void onNewDhcpResults(android.net.DhcpResultsParcelable dhcpResultsParcelable) {
    }

    public void onProvisioningSuccess(android.net.LinkProperties linkProperties) {
    }

    public void onProvisioningFailure(android.net.LinkProperties linkProperties) {
    }

    public void onLinkPropertiesChange(android.net.LinkProperties linkProperties) {
    }

    public void onReachabilityLost(java.lang.String str) {
    }

    public void onQuit() {
    }

    public void installPacketFilter(byte[] bArr) {
    }

    public void startReadPacketFilter() {
    }

    public void setFallbackMulticastFilter(boolean z) {
    }

    public void setNeighborDiscoveryOffload(boolean z) {
    }

    public void onPreconnectionStart(java.util.List<android.net.Layer2PacketParcelable> list) {
    }

    public void onReachabilityFailure(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) {
        onReachabilityLost(reachabilityLossInfoParcelable.message);
    }

    public void setMaxDtimMultiplier(int i) {
    }
}
