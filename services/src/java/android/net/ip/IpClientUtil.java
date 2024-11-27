package android.net.ip;

/* loaded from: classes.dex */
public class IpClientUtil {
    public static final java.lang.String DUMP_ARG = "ipclient";

    public static class WaitForProvisioningCallbacks extends android.net.ip.IpClientCallbacks {
        private final android.os.ConditionVariable mCV = new android.os.ConditionVariable();
        private android.net.LinkProperties mCallbackLinkProperties;

        public android.net.LinkProperties waitForProvisioning() {
            this.mCV.block();
            return this.mCallbackLinkProperties;
        }

        @Override // android.net.ip.IpClientCallbacks
        public void onProvisioningSuccess(android.net.LinkProperties linkProperties) {
            this.mCallbackLinkProperties = linkProperties;
            this.mCV.open();
        }

        @Override // android.net.ip.IpClientCallbacks
        public void onProvisioningFailure(android.net.LinkProperties linkProperties) {
            this.mCallbackLinkProperties = null;
            this.mCV.open();
        }
    }

    public static void makeIpClient(android.content.Context context, java.lang.String str, android.net.ip.IpClientCallbacks ipClientCallbacks) {
        android.net.networkstack.ModuleNetworkStackClient.getInstance(context).makeIpClient(str, new android.net.ip.IpClientUtil.IpClientCallbacksProxy(ipClientCallbacks));
    }

    private static class IpClientCallbacksProxy extends android.net.ip.IIpClientCallbacks.Stub {
        protected final android.net.ip.IpClientCallbacks mCb;

        IpClientCallbacksProxy(android.net.ip.IpClientCallbacks ipClientCallbacks) {
            this.mCb = ipClientCallbacks;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onIpClientCreated(android.net.ip.IIpClient iIpClient) {
            this.mCb.onIpClientCreated(iIpClient);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreDhcpAction() {
            this.mCb.onPreDhcpAction();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPostDhcpAction() {
            this.mCb.onPostDhcpAction();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onNewDhcpResults(android.net.DhcpResultsParcelable dhcpResultsParcelable) {
            this.mCb.onNewDhcpResults(dhcpResultsParcelable);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningSuccess(android.net.LinkProperties linkProperties) {
            this.mCb.onProvisioningSuccess(new android.net.LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningFailure(android.net.LinkProperties linkProperties) {
            this.mCb.onProvisioningFailure(new android.net.LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onLinkPropertiesChange(android.net.LinkProperties linkProperties) {
            this.mCb.onLinkPropertiesChange(new android.net.LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityLost(java.lang.String str) {
            this.mCb.onReachabilityLost(str);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onQuit() {
            this.mCb.onQuit();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void installPacketFilter(byte[] bArr) {
            this.mCb.installPacketFilter(bArr);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void startReadPacketFilter() {
            this.mCb.startReadPacketFilter();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setFallbackMulticastFilter(boolean z) {
            this.mCb.setFallbackMulticastFilter(z);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setNeighborDiscoveryOffload(boolean z) {
            this.mCb.setNeighborDiscoveryOffload(z);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreconnectionStart(java.util.List<android.net.Layer2PacketParcelable> list) {
            this.mCb.onPreconnectionStart(list);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityFailure(android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) {
            this.mCb.onReachabilityFailure(reachabilityLossInfoParcelable);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void setMaxDtimMultiplier(int i) {
            this.mCb.setMaxDtimMultiplier(i);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public int getInterfaceVersion() {
            return 21;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public java.lang.String getInterfaceHash() {
            return "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
        }
    }

    public static void dumpIpClient(android.net.ip.IIpClient iIpClient, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("IpClient logs have moved to dumpsys network_stack");
    }
}
